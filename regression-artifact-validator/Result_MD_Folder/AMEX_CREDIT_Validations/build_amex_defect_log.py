"""Builds one consolidated ATL105 defect log for every American Express Credit test data file.

Usage:  python build_amex_defect_log.py [<delivery folder>]
Output: AMEX_Credit_Defect_Log.md and AMEX_Credit_Defect_Log.csv next to this script.
"""
import csv
import json
import sys
from collections import Counter, defaultdict
from datetime import date
from pathlib import Path

DELIVERY = Path(sys.argv[1] if len(sys.argv) > 1 else r"C:\Users\F5QGZOJ\Downloads\31-10-out\american_express_credit")
OUT = Path(__file__).resolve().parent
RUN_YYMM = date.today().strftime("%y%m")

SEG100, SEG111, SEG130, SEG105 = ("Standard Message Data Segment", "Variable Information Data Segment",
                                  "EMV Request Data Segment", "Totals Data Segment")
SEG100_KEYS = ["SegmentType", "SegmentLength", "InformationByte", "TerminalIdentifier", "PromptCode", "AccountNumber",
               "CardDiscretionaryBlockData", "EncryptedPINBlockData", "PumpLaneNumber", "FuelPurchaseAmount",
               "NonfuelAmount", "TaxAmount", "CashAmount", "SequenceNumber", "ApprovalNumber", "LocalDateandLocalTime",
               "PartialApprovalIndicator"]
KEYED = {"01", "10", "79"}
CHIP_READ = {"05", "07", "86"}
REVERSAL = {"8", "Z", "S", "U"}
ENTRY_ORDER = ["EMV_CONTACT", "EMV_CONTACTLESS", "MAGSTRIPE", "CONTACTLESS_MAGSTRIPE", "MANUAL", "EMV_FALLBACK",
               "CREDENTIAL_ON_FILE", "CONTACTLESS_MOBILE"]

DEFECTS = {
    "A-01": ("EMV Card Sequence Number (Element 188) is not 3 characters",
             "Ch. 13 Element 188 - search the spec PDF for \"EMV Card Sequence Number\": \"Fixed length of three bytes, "
             "right-aligned and padded with zeroes ... 000-099\""),
    "A-02": ("EMV Additional Information (Element 118) is not EMVYES / EMVNOT",
             "Element 191 (001 = EMV table data) -> Element 118 -> Appendix T-1 \"EMV Table Data\": request values EMVYES / EMVNOT"),
    "A-03": ("Chip fallback entry mode 79 (\"fallback to manual\") sent with swiped Track 2 data",
             "Appendix J-1: 79 = \"Contact and Contactless chip fallback to manual\"; 80 = \"Chip card capable - unaltered "
             "track data read (used for contact EMV fall back to swiped)\". Element 12: keyed expiry is MMYY"),
    "A-03b": ("Follow-on of a 79 transaction carries a YYMM expiry (knock-on of A-03)",
              "Element 12: keyed expiry is MMYY; follow-ons use the same credentials as the original"),
    "A-04": ("Keyed / Credential-on-File follow-on expiry differs from the original (MMYY changed to YYMM)",
             "Element 12: \"For a card whose number has been manually entered, the expiration date is in MMYY format\"; "
             "\"In the event of a 'Retry' of a 'Follow-on' transaction ... use the same credentials\""),
    "A-05": ("Totals Card Label (Element 13) is not 4 characters",
             "Ch. 13 Element 13 - search \"Card Label\": fixed length of four characters, left-aligned, space-filled"),
    "A-06": ("Split-tender step 2 Segment Length calculated with an impossible amount",
             "Ch. 12.1 Field 2 (Segment Length = all fields + one separator each); p.84 remaining balance = requested - approved"),
    "A-07": ("Split-tender card B chip amount (Tag 9F02) is the full original amount",
             "SEG130-R-009 (chip amount = request amount); p.84 split tender remainder"),
    "A-08": ("Expired-card test uses a card that is not expired",
             "Test objective; ATL105 p.160; Element 12 \"merchants must send a value even for expired cards\""),
    "A-09": ("PIN test does not carry the PIN verification it claims",
             "EMV Book 3 Annex C3 (CVM codes: 01/04 offline PIN, 02 online PIN, 1E signature); Element 33"),
    "A-10": ("Terminal capability 8 used on a chip-capable terminal (Table 031 = 12)",
             "Appendix J-2 footnote 1: code 8 only for a contactless MSR with no chip capability; EMV devices use 1"),
}


def luhn(pan):
    total = 0
    for i, ch in enumerate(reversed(pan)):
        d = int(ch)
        if i % 2:
            d = d * 2 - 9 if d > 4 else d * 2
        total += d
    return total % 10 == 0


def tlv(hexstr):
    """Decodes EMV TLV after the 2-byte length prefix (e.g. '0143...')."""
    out, i, s = {}, 4, hexstr.upper()
    try:
        while i < len(s):
            tag = s[i:i + 2]
            i += 2
            if int(tag, 16) & 0x1F == 0x1F:
                while True:
                    nxt = s[i:i + 2]
                    tag += nxt
                    i += 2
                    if not int(nxt, 16) & 0x80:
                        break
            ln = int(s[i:i + 2], 16)
            i += 2
            if ln & 0x80:
                n = ln & 0x7F
                ln = int(s[i:i + 2 * n], 16)
                i += 2 * n
            out[tag] = s[i:i + 2 * ln]
            i += 2 * ln
    except ValueError:
        pass
    return out


def find_line(lines, anchors):
    start, found = 0, -1
    for a in anchors:
        found = -1
        for k in range(start, len(lines)):
            if a in lines[k]:
                found = k
                break
        if found < 0:
            return ""
        start = found + 1
    return str(found + 1)


def is_runtime(v):
    return isinstance(v, str) and v.startswith("<")


def seg100_flat(s):
    pc = s.get("PromptCode", "")
    flat = {k: s.get(k, "") for k in SEG100_KEYS}
    flat["PromptCode"] = (pc.get("TransactionType", "") + pc.get("CardType", "")) if isinstance(pc, dict) else pc
    return flat


def seg100_length(flat):
    last = max((i for i, k in enumerate(SEG100_KEYS) if flat[k]), default=-1)
    return sum(len(flat[SEG100_KEYS[i]]) + 1 for i in range(last + 1))


def month_name(yymm):
    import calendar
    m = int(yymm[2:])
    return f"{calendar.month_name[m]} 20{yymm[:2]}" if 1 <= m <= 12 else f"invalid month {yymm[2:]}"


rows = []
checks = Counter()
check_fail = Counter()
observations = Counter()


def check(name, ok):
    checks[name] += 1
    if not ok:
        check_fail[name] += 1
    return ok


def defect(did, ctx, path, anchors, actual, expected, note=""):
    rows.append({"Defect": did, "TC": ctx["tc"], "EntryMode": ctx["em"], "Template": ctx["tpl"], "TCStatus": ctx["status"],
                 "File": ctx["file"], "Line": find_line(ctx["lines"], anchors), "Field": path, "Actual": actual,
                 "Expected": expected, "Note": note})


tcs = [json.loads(line) for line in open(DELIVERY / "test_cases" / "test_cases.jsonl", encoding="utf-8")]
em_stats = defaultdict(Counter)
for tc in tcs:
    em = tc["dimensions"]["entry_mode"]
    em_stats[em]["tcs"] += 1
    files = tc.get("test_data_leg_files") or ([tc["test_data_file"]] if tc.get("test_data_file") else [])
    if files:
        em_stats[em]["with_td"] += 1
    legs_def = tc.get("tx_legs") or []
    legs = []
    for idx, rel in enumerate(files):
        em_stats[em]["files"] += 1
        path = DELIVERY / rel
        text = path.read_text(encoding="utf-8")
        td = json.loads(text)
        root_key = next(iter(td))
        m = td[root_key]
        ld = legs_def[idx] if idx < len(legs_def) else {}
        ctx = {"tc": tc["id"], "em": em, "tpl": tc.get("template_id", ""), "status": tc.get("status", ""),
               "file": path.name, "lines": text.splitlines()}
        leg = {"root": root_key, "def": ld, "flat": {}, "t005": "", "t031": "", "tlv": {}, "ctx": ctx}
        legs.append(leg)
        check("E55 = ATL105", m.get("MessageFormatVersionIdentifier") == "ATL105")
        segs = [k for k, v in m.items() if isinstance(v, dict)]
        check("E63 = segments present", m.get("NumberofSegments", "").isdigit() and int(m["NumberofSegments"]) == len(segs))

        if root_key == "Totals Request":
            t = m[SEG105]
            check("Totals Prompt Code 990", t.get("PromptCode") == "990")
            label = t.get("CardLabel", "")
            check("Totals Card Label value TE", label.strip() == "TE")
            if not check("Totals Card Label 4 characters (A-05)", len(label) == 4):
                defect("A-05", ctx, f"{SEG105} > CardLabel", [f'"{SEG105}"', '"CardLabel"'], f"'{label}' ({len(label)} chars)",
                       f"'{label.strip():<4}' (4 chars: {label.strip()} + {4 - len(label.strip())} spaces)")
            continue

        flat = seg100_flat(m[SEG100])
        leg["flat"] = flat
        t111 = {x["VariableInformationIndicator"]: x["VariableInformation"]
                for x in m.get(SEG111, {}).get("VariableInformationSection", [])}
        leg["t005"], leg["t031"] = t111.get("005", ""), t111.get("031", "")
        code = ld.get("code") or ""
        entry = ld.get("entry_code") or ""
        pan, cdbd, e005 = flat["AccountNumber"], flat["CardDiscretionaryBlockData"], leg["t005"]

        # ---- generic structural checks (logged as G-xx if they ever fail)
        has_rt = any(is_runtime(v) for v in flat.values())
        if not has_rt and not check("Seg 100 SegmentLength recomputed", flat["SegmentLength"] == f"{seg100_length(flat):03d}"):
            defect("G-01", ctx, f"{SEG100} > SegmentLength", [f'"{SEG100}"', '"SegmentLength"'], flat["SegmentLength"],
                   f"{seg100_length(flat):03d}")
        body = sum(len(x["VariableInformationIndicator"]) + len(x["VariableInformationLength"]) + len(x["VariableInformation"])
                   for x in m.get(SEG111, {}).get("VariableInformationSection", []))
        exp111 = f"{3 + 1 + 3 + 1 + body + 1:03d}"
        if not check("Seg 111 SegmentLength recomputed", m.get(SEG111, {}).get("SegmentLength") == exp111):
            defect("G-02", ctx, f"{SEG111} > SegmentLength", [f'"{SEG111}"', '"SegmentLength"'],
                   m.get(SEG111, {}).get("SegmentLength"), exp111)
        if not check("PAN = Amex (34/37, 15 digits, Luhn)", pan.isdigit() and len(pan) == 15 and pan[:2] in ("34", "37") and luhn(pan)):
            defect("G-03", ctx, f"{SEG100} > AccountNumber", [f'"{SEG100}"', '"AccountNumber"'], pan, "15-digit Amex PAN 34/37, Luhn-valid")
        if not check("Prompt Code card type 020 (Credit)", flat["PromptCode"][1:] == "020"):
            defect("G-04", ctx, f"{SEG100} > PromptCode.CardType", [f'"{SEG100}"', '"CardType"'], flat["PromptCode"][1:], "020")
        if code and not check("Prompt Code transaction type = TC leg", flat["PromptCode"][:1] == code):
            defect("G-05", ctx, f"{SEG100} > PromptCode.TransactionType", [f'"{SEG100}"', '"TransactionType"'],
                   flat["PromptCode"][:1], code)
        if entry and not check("Table 005 entry mode = TC leg entry_code", e005[:2] == entry):
            defect("G-06", ctx, f"{SEG111} > Table 005", [f'"{SEG111}"', '"005"', '"VariableInformation"'], e005, entry + "x")
        amt = flat["NonfuelAmount"]
        if amt and not is_runtime(amt) and not check("E58 amount 1-8 digits > 0", amt.isdigit() and len(amt) <= 8 and int(amt) > 0):
            defect("G-07", ctx, f"{SEG100} > NonfuelAmount", [f'"{SEG100}"', '"NonfuelAmount"'], amt, "1-8 digits, > 0")
        seq = flat["SequenceNumber"]
        if not check("Sequence Number 6 digits", seq.isdigit() and len(seq) == 6 and seq != "000000"):
            defect("G-08", ctx, f"{SEG100} > SequenceNumber", [f'"{SEG100}"', '"SequenceNumber"'], seq, "000001-999999")
        if not check("Partial Approval Indicator 0/1/5", flat["PartialApprovalIndicator"] in ("0", "1", "5")):
            defect("G-09", ctx, f"{SEG100} > PartialApprovalIndicator", [f'"{SEG100}"', '"PartialApprovalIndicator"'],
                   flat["PartialApprovalIndicator"], "0, 1 or 5")
        if not check("Table 031 present", bool(leg["t031"])):
            defect("G-10", ctx, f"{SEG111} > Table 031", [f'"{SEG111}"'], "absent", "Table 031 present")
        is_emv = root_key == "EMV Financial Transaction Request"
        if not check("Segment 130 only on chip-read EMV requests", is_emv == (SEG130 in m) and (not is_emv or e005[:2] in CHIP_READ)):
            defect("G-11", ctx, "Segment 130", [f'"{root_key}"'], f"root={root_key}, 130={'yes' if SEG130 in m else 'no'}, 005={e005}",
                   "Segment 130 only in EMV requests with entry 05/07/86")
        if code == "0" and not is_runtime(flat["ApprovalNumber"]):
            if not check("Approval Number empty on purchase", flat["ApprovalNumber"] == ""):
                defect("G-12", ctx, f"{SEG100} > ApprovalNumber", [f'"{SEG100}"', '"ApprovalNumber"'], flat["ApprovalNumber"], "(empty)")
        if cdbd and not is_runtime(cdbd):
            mmyy = e005[:2] in KEYED and len(cdbd) == 4
            mm = cdbd[:2] if mmyy else cdbd[2:4]
            if len(cdbd) > 4:
                check("Track 2 service code present", cdbd[4:7].isdigit())
            if not mmyy and not (e005[:2] == "79" and len(cdbd) == 4):
                if not check("Element 12 expiry month valid (keyed failures = A-04 + A-03b rows)", mm.isdigit() and 1 <= int(mm) <= 12):
                    defect("G-13", ctx, f"{SEG100} > CardDiscretionaryBlockData", [f'"{SEG100}"', '"CardDiscretionaryBlockData"'],
                           cdbd, "valid month")
            elif mmyy:
                check("Element 12 expiry month valid (keyed failures = A-04 + A-03b rows)", mm.isdigit() and 1 <= int(mm) <= 12)
        if e005.startswith("82"):
            observations["Entry mode 82 (mobile) request without Segment 130 - SME open question"] += 1

        # ---- EMV defects
        if is_emv:
            e = m[SEG130]
            leg["tlv"] = tlv(e.get("EMVChipData", ""))
            csn = e.get("EMVCardSequenceNumber", "")
            if not check("E188 3 characters (A-01)", len(csn) == 3):
                defect("A-01", ctx, f"{SEG130} > EMVCardSequenceNumber", [f'"{SEG130}"', '"EMVCardSequenceNumber"'],
                       f"'{csn}' ({len(csn)} chars)", f"'{csn.zfill(3)}'")
            info = e.get("EMVAdditionalInformation", "")
            if e.get("EMVAdditionalInformationIndicator") == "001" and not check("E118 = EMVYES/EMVNOT (A-02)", info in ("EMVYES", "EMVNOT")):
                defect("A-02", ctx, f"{SEG130} > EMVAdditionalInformation", [f'"{SEG130}"', '"EMVAdditionalInformation"'],
                       f"{info[:20]}... ({len(info)} chars, length field {e.get('EMVAdditionalInformationLength')})",
                       "EMVYES or EMVNOT (length 006)")

        # ---- entry-mode defects
        if e005[:2] == "79" and len(cdbd) > 4 and not check("79 carries keyed expiry only (A-03)", False):
            defect("A-03", ctx, f"{SEG111} > Table 005 + {SEG100} > CardDiscretionaryBlockData",
                   [f'"{SEG111}"', '"005"', '"VariableInformation"'],
                   f"005={e005}; E12={cdbd} (expiry {cdbd[:4]} + service code {cdbd[4:7]} + discretionary)",
                   f"swiped fallback: 005=80{e005[2:]} + keep Track 2  OR  keyed fallback: 005={e005} + E12={cdbd[2:4]}{cdbd[:2]}")
        elif e005[:2] == "79":
            check("79 carries keyed expiry only (A-03)", True)
        if e005.endswith("8") and not check("Capability 8 not on chip terminal (A-10)", leg["t031"] != "12"):
            defect("A-10", ctx, f"{SEG111} > Table 005", [f'"{SEG111}"', '"005"', '"VariableInformation"'],
                   f"005={e005}, 031={leg['t031']}", f"005={e005[:2]}1 (chip-capable terminal) or change Table 031")

    # ---- cross-leg defects
    for i, leg in enumerate(legs):
        ld, ctx = leg["def"], leg["ctx"]
        if not leg["flat"] or i == 0:
            continue
        follow = ld.get("follow_on") or (ld.get("code") in REVERSAL)
        of = (ld.get("of") or 1) - 1
        seen = set()
        while 0 < of < len(legs) and of not in seen and (legs[of]["def"].get("follow_on") or legs[of]["def"].get("code") in REVERSAL):
            seen.add(of)
            of = (legs[of]["def"].get("of") or 1) - 1
        if follow and 0 <= of < len(legs) and legs[of]["flat"]:
            o = legs[of]
            oe, fe = o["flat"]["CardDiscretionaryBlockData"], leg["flat"]["CardDiscretionaryBlockData"]
            if o["t005"][:2] in ("01", "10") and len(oe) == 4:
                if not check("Keyed follow-on expiry = original (A-04)", fe == oe):
                    defect("A-04", ctx, f"{SEG100} > CardDiscretionaryBlockData", [f'"{SEG100}"', '"CardDiscretionaryBlockData"'],
                           f"{fe} (original {o['ctx']['file']}: {oe})", f"{oe} (same MMYY as original)")
            elif o["t005"][:2] == "79" and len(oe) > 4 and len(fe) == 4:
                check("Keyed follow-on expiry = original (A-04)", False)
                defect("A-03b", ctx, f"{SEG100} > CardDiscretionaryBlockData", [f'"{SEG100}"', '"CardDiscretionaryBlockData"'],
                       f"{fe} (YYMM) with 005={leg['t005']}",
                       f"fix together with A-03 on {o['ctx']['file']}: keyed -> {fe[2:]}{fe[:2]} (MMYY); swiped -> keep {fe} and 005=80x")
        if ld.get("role") == "split_tender" and is_runtime(leg["flat"]["NonfuelAmount"]):
            a1 = legs[0]["flat"].get("NonfuelAmount", "")
            if a1.isdigit():
                f = leg["flat"]
                fixed = sum(len(f[k]) + 1 for k in SEG100_KEYS if k != "NonfuelAmount") + 1
                implied = int(f["SegmentLength"]) - fixed
                maxw = len(str(int(a1) - 1))
                if not check("Split step 2 length fits a real remainder (A-06)", 1 <= implied <= maxw):
                    defect("A-06", ctx, f"{SEG100} > SegmentLength", [f'"{SEG100}"', '"SegmentLength"'],
                           f"{f['SegmentLength']} (= {implied}-digit amount; other fields + separators = {fixed})",
                           f"{fixed + 1:03d}-{fixed + maxw:03d} (remainder of {a1} has 1-{maxw} digits)")
            if leg["tlv"]:
                a9f02 = leg["tlv"].get("9F02", "")
                same = a9f02.isdigit() and int(a9f02) == int(a1)
                if not check("Split card B 9F02 = remainder (A-07)", not same):
                    defect("A-07", ctx, f"{SEG130} > EMVChipData > Tag 9F02", [f'"{SEG130}"', '"EMVChipData"'],
                           f"9F02={a9f02} (= step 1 amount {a1})", "card B remainder, 12 digits (e.g. 000000000500 for $5.00)")

    first = legs[0] if legs else None
    tpl = tc.get("template_id", "")
    if first and first["flat"]:
        ctx = first["ctx"]
        cdbd = first["flat"]["CardDiscretionaryBlockData"]
        if tpl.startswith("T2-EXPIRED"):
            yymm = cdbd[2:4] + cdbd[:2] if first["t005"][:2] in KEYED and len(cdbd) == 4 else cdbd[:4]
            if not check("Expired-card test uses expired card (A-08)", yymm < RUN_YYMM):
                defect("A-08", ctx, f"{SEG100} > CardDiscretionaryBlockData", [f'"{SEG100}"', '"CardDiscretionaryBlockData"'],
                       f"expiry {yymm} (YYMM) = {month_name(yymm)}", f"expiry before {RUN_YYMM} ({month_name(RUN_YYMM)})")
        cvm = first["tlv"].get("9F34", "")
        cvm_code = (int(cvm[:2], 16) & 0x3F) if cvm else None
        if tpl == "T3-OFFLINE-PIN" and not check("Offline PIN test has offline PIN CVM (A-09)", cvm_code in (1, 3, 4, 5)):
            defect("A-09", ctx, f"{SEG130} > EMVChipData > Tag 9F34", [f'"{SEG130}"', '"EMVChipData"'],
                   f"9F34={cvm} (CVM code {cvm_code:02X} = {'signature' if cvm_code == 0x1E else 'not offline PIN'})",
                   "9F34 CVM code 01 or 04 (offline PIN), e.g. 440302")
        if tpl == "T3-ONLINE-PIN" and not check("Online PIN test has PIN block (A-09)", bool(first["flat"]["EncryptedPINBlockData"])):
            defect("A-09", ctx, f"{SEG100} > EncryptedPINBlockData", [f'"{SEG100}"', '"EncryptedPINBlockData"'],
                   f"(empty); 9F34={cvm}", "DUKPT KSN + PIN block (36 chars) and 9F34 CVM code 02")

# ------------------------------------------------------------------ output
rows.sort(key=lambda r: (r["Defect"], ENTRY_ORDER.index(r["EntryMode"]) if r["EntryMode"] in ENTRY_ORDER else 99, r["TC"], r["File"]))
cols = ["Defect", "TC", "EntryMode", "Template", "TCStatus", "File", "Line", "Field", "Actual", "Expected", "Note"]
with open(OUT / "AMEX_Credit_Defect_Log.csv", "w", newline="", encoding="utf-8-sig") as fh:
    w = csv.DictWriter(fh, fieldnames=cols)
    w.writeheader()
    w.writerows(rows)

by_def = defaultdict(list)
for r in rows:
    by_def[r["Defect"]].append(r)
matrix = defaultdict(Counter)
for r in rows:
    matrix[r["Defect"]][r["EntryMode"]] += 1


GUIDE = r"""
## 4. How to verify each defect manually

### 4.1 Setup (once)

1. Open the delivery folder `american_express_credit\test_data` in VS Code.
2. Open this log or the CSV side by side. Choose 2–3 rows per defect type and verify them; every row of a type follows the same pattern.
3. For each row: open the **File** (`Ctrl+P`, type the file name), press `Ctrl+G`, type the **Line** number. The cursor lands on the **Field**.
4. **Counting characters:** double-click inside the quotes to select the value. The VS Code status bar (bottom right) shows `(N selected)` – that is the length.
5. **Spec:** open `regression-artifact-validator\specifications\ATL105\docs\specs\BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf` and use `Ctrl+F` with the search text given below. Searching by text is more reliable than by page number.

### 4.2 Per-defect steps

**A-01 – EMV Card Sequence Number (E188)**
1. Go to the line; the field is `"EMVCardSequenceNumber": "01"`.
2. Select the value and check the length in the status bar: **2**.
3. Spec: search `EMV Card Sequence Number`, open the Chapter 13 definition (Element 188): *"Fixed length of three bytes, right-aligned and padded with zeroes"*.
4. Defect confirmed if the length is not 3. The correct value is `001`.

**A-02 – EMV Additional Information (E118)**
1. Go to the line; the field above it shows `"EMVAdditionalInformationIndicator": "001"` and `"EMVAdditionalInformationLength": "984"`.
2. The value is a long random string (984 characters), not `EMVYES` or `EMVNOT`.
3. Spec: search `EMV Additional Information Indicator` (001 = EMV table data), then search `EMVYES` (Appendix T-1, EMV Table Data: request values EMVYES / EMVNOT).
4. Defect confirmed if the value is anything other than `EMVYES` / `EMVNOT`.

**A-03 – Chip fallback `79` with swiped data**
1. Go to the line; it is the Table 005 entry: `"VariableInformationIndicator": "005"` → `"VariableInformation": "791"`.
2. Scroll up to `"CardDiscretionaryBlockData"` in the same file: it is 15 digits (e.g. `310720162917830`).
3. Decode it: digits 1–4 = expiry YYMM (`3107`), digits 5–7 = service code (`201`), and the rest is discretionary data. A service code and discretionary data exist only when the card's magnetic stripe is read (swiped).
4. Spec: search `fallback to manual` (Appendix J-1): `79` = fallback to **manual** (keyed); next row `80` = *"Chip card capable – unaltered track data read (used for contact EMV fall back to swiped)"* (search `unaltered track data read`). Then search `manually entered` (Element 12): keyed expiry is MMYY only.
5. Defect confirmed: the code says "keyed" but the data is swiped. Fix: either `801` + keep the track data, or keep `791` with E12 = 4-digit MMYY (e.g. `0731`).

**A-03b – Follow-on of a `79` transaction**
1. Go to the line in the follow-on file (reversal / time-out reversal); E12 is 4 digits in YYMM order (e.g. `2809`), and Table 005 is `79x`.
2. Read as keyed MMYY, the month `28` is impossible. This row is resolved automatically when A-03 is fixed on the original (see the Expected column).

**A-04 – Keyed / COF follow-on expiry**
1. Open the original purchase file (same TC, `step1`), find `"CardDiscretionaryBlockData"`: e.g. `1231`.
2. Open the follow-on file named in the row, go to the line: e.g. `3112`.
3. The same date has month and year swapped. Read as MMYY (the keyed format), `31` is not a month.
4. Spec: search `manually entered` (Element 12: keyed expiry MMYY) and `same credentials` (follow-ons/retries use the same credentials as the original).
5. Defect confirmed if the follow-on value is different from the original value.

**A-05 – Totals Card Label (E13)**
1. Go to the line in the `*.totals.json` file: `"CardLabel": "TE"`.
2. Select it: the length is **2**.
3. Spec: search `Card Label` (Chapter 13 Element 13, fixed length 4, space-filled). `TE` is the correct American Express bucket; only the width is wrong.
4. Correct value: `"TE  "` (TE + 2 spaces).

**A-06 – Split-tender step 2 Segment Length**
1. Open the `*.step2.split-tender.json` file, go to the line: `"SegmentLength": "092"`.
2. `NonfuelAmount` in the same segment is a placeholder (`<RemainingBalance …>`), filled at run time.
3. Recompute by hand: count the characters of every other Segment 100 field and add 1 separator per field (17 fields). Example: `100`(3) + `092`(3) + `0`(1) + `JX72948216050257001`(19) + `0020`(4) + PAN(15) + Track 2(15) + `SequenceNumber`(6) + `1`(1) = 67, plus 17 separators = **84**. So `092` − 84 = **8 digits** reserved for the amount.
4. The step 1 amount is `1500` ($15.00). A remainder is less than that – at most `1499` (4 digits) – so the length must be between 085 and 088 (the Expected column shows the exact range for each file).
5. The 8-digit value comes from the test case file `test_cases.jsonl` → this TC → `steps[1].request` → `Nonfuel Amount` → `value` (e.g. `74141657` = $741,416.57).

**A-07 – Split-tender card B chip amount (Tag 9F02)**
1. In the `*.step2.split-tender.json` file, go to the line (`"EMVChipData"`).
2. Press `Ctrl+F` and search `9F0206`. The 12 digits after it are the amount: `000000001500` = $15.00.
3. That is the step 1 full amount. Card B pays only the remainder, so 9F02 must equal the remainder (SEG130-R-009).

**A-08 – Expired-card test**
1. Go to the line (`"CardDiscretionaryBlockData"`); the first 4 digits are the expiry YYMM, e.g. `2808` = August 2028.
2. The test objective (`test_cases.jsonl` → `test_objective`) is "Expired EMV card". The card is expired only if YYMM is earlier than the run month ({RUN_YYMM}).
3. Defect confirmed if the expiry is in the future.

**A-09 – PIN test without PIN verification**
1. Go to `"EMVChipData"` and search `9F3403`. The next 6 characters are CVM Results, e.g. `5E0302`.
2. Take the first byte `5E` and drop the top bit pair (`5E` AND `3F` = `1E`). CVM code `1E` = **signature**; `01`/`04` = offline PIN; `02` = online PIN (EMV Book 3, Annex C3).
3. An "offline PIN" test with code `1E` does not test a PIN. For an online-PIN test also check `"EncryptedPINBlockData"`: it must not be empty.

**A-10 – Terminal capability 8**
1. Go to the line: Table 005 = `908` (3rd digit `8`). In the same segment, Table 031 = `12` (hybrid contact + contactless chip terminal).
2. Spec: search `Contactless Magnetic Stripe Read Capability` (Appendix J-2). The footnote says code 8 applies only to a contactless MSR with **no chip capability**; for EMV-capable devices the code is `1`.
3. Defect confirmed if 3rd digit = 8 while Table 031 says the terminal has chip capability.

### 4.3 Verifying that the passing checks really pass (section 2)

Pick any one file and recompute:
- **Segment 100 length:** add the character count of every field from `SegmentType` to the last non-empty field (empty fields count 0), plus 1 per field for the separator. Compare with `SegmentLength`.
- **Segment 111 length:** 3 + 1 + 3 + 1 + (for each section: indicator 3 + length 3 + value) + 1. Example: 3+1+3+1+(3+3+3)+(3+3+2)+1 = **26**.
- **Amex card number:** 15 digits, starts with 34 or 37, passes the Luhn check (any online Luhn checker).
- **Prompt Code:** `TransactionType` = `0` purchase, `8` reversal, `Z` time-out reversal (Appendix G); `CardType` = `020` Credit (Appendix E).
- **Table 005 for follow-ons of a swiped `90` purchase:** `02x` is **correct** (Appendix J-1 footnote 1 – search `PAN Entry Mode code 02 when referencing`).

### 4.4 Re-running the log

From this folder: `python build_amex_defect_log.py` (optionally pass another delivery folder as the first argument). It rewrites this file and the CSV only; no per-test-case reports are created.
"""


def md(s):
    return str(s).replace("|", "\\|")


total_files = sum(s["files"] for s in em_stats.values())
L = []
L.append("# American Express Credit – Consolidated Test Data Defect Log\n")
L.append("| Item | Value |\n|---|---|")
L.append(f"| Delivery | `{DELIVERY}` |")
L.append("| Specification | BUYPASS Platform ATL105 Message Format Specifications 2026-3 |")
L.append(f"| Scope | **All** {len(tcs)} test cases, **{total_files}** test data files (every leg) |")
L.append(f"| Run date | {date.today().isoformat()} (expiry checks use {RUN_YYMM}) |")
L.append(f"| Defect rows | **{len(rows)}** in {len(by_def)} defect types; affected TCs: **{len({r['TC'] for r in rows})}** |")
L.append("| Generated by | `build_amex_defect_log.py` (this folder). Re-run: `python build_amex_defect_log.py` |")
L.append("| Same rows as spreadsheet | [AMEX_Credit_Defect_Log.csv](./AMEX_Credit_Defect_Log.csv) (open in Excel, filter by Defect / EntryMode) |\n")
L.append("Manual verification guide: see **section 4**. Every defect row gives the file, the line number and the field, "
         "so you can open the file in VS Code, press `Ctrl+G`, type the line number and compare **Actual** with **Expected**.\n")

L.append("## 1. Summary – defects by entry mode\n")
L.append("| Defect | Description | " + " | ".join(ENTRY_ORDER) + " | Total |")
L.append("|---|---|" + "---|" * len(ENTRY_ORDER) + "---|")
for d in DEFECTS:
    if d in by_def:
        L.append(f"| {d} | {md(DEFECTS[d][0])} | " + " | ".join(str(matrix[d][e] or "") for e in ENTRY_ORDER) + f" | **{len(by_def[d])}** |")
for d in sorted(k for k in by_def if k not in DEFECTS):
    L.append(f"| {d} | generic structural defect | " + " | ".join(str(matrix[d][e] or "") for e in ENTRY_ORDER) + f" | **{len(by_def[d])}** |")
L.append("\nEntry mode inventory:\n")
L.append("| Entry mode | Test cases | With test data | Test data files |\n|---|---|---|---|")
for e in ENTRY_ORDER:
    s = em_stats[e]
    L.append(f"| {e} | {s['tcs']} | {s['with_td']} | {s['files']} |")
L.append(f"| **Total** | **{len(tcs)}** | **{sum(s['with_td'] for s in em_stats.values())}** | **{total_files}** |\n")

L.append("## 2. Checks performed on every file\n")
L.append("A check with 0 failures means the generated data is correct for that rule in all files.\n")
L.append("| Check | Files/legs checked | Failures |\n|---|---|---|")
for name, n in checks.items():
    L.append(f"| {md(name)} | {n} | {check_fail[name] or 0} |")
if observations:
    L.append("\nObservations (not defects, open SME questions):\n")
    for k, v in observations.items():
        L.append(f"- {k}: {v} leg(s)")

L.append("\n## 3. Defect log\n")
for d in list(DEFECTS) + sorted(k for k in by_def if k not in DEFECTS):
    if d not in by_def:
        continue
    title, spec = DEFECTS.get(d, ("Generic structural defect", "see Expected"))
    L.append(f"### {d} – {md(title)} ({len(by_def[d])} rows)\n")
    L.append(f"Spec: {md(spec)}\n")
    L.append("| # | TC | Entry mode | Template | File | Line | Field | Actual | Expected |\n|---|---|---|---|---|---|---|---|---|")
    for i, r in enumerate(by_def[d], 1):
        L.append(f"| {i} | {r['TC']} | {r['EntryMode']} | {r['Template']} | `{r['File']}` | {r['Line']} | {md(r['Field'])} | "
                 f"`{md(r['Actual'])}` | `{md(r['Expected'])}` |")
    L.append("")

(OUT / "AMEX_Credit_Defect_Log.md").write_text("\n".join(L) + "\n" + GUIDE.replace("{RUN_YYMM}", RUN_YYMM), encoding="utf-8")
print(f"rows={len(rows)} types={dict(Counter(r['Defect'] for r in rows))} files={total_files}")

