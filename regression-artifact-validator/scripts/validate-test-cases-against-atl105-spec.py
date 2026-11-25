#!/usr/bin/env python3
"""Validate AI-generated test_cases.jsonl against the BUYPASS ATL105 Message Format Specification.

Spec facts come from the extracted text of the ATL105 PDF (2026-4 by default) and the
Chapter 13 element reference. Every finding cites the spec page / element it is based on.
"""

import argparse
import csv
import json
import re
import unicodedata
from collections import Counter, defaultdict
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
ATL = ROOT / "specifications" / "ATL105"
DEFAULT_SPEC = ATL / "docs" / "specs" / "extracted_text_new.txt"
PRIOR_SPEC = ATL / "docs" / "specs" / "extracted_text.txt"
ELEMENT_REF = ATL / "test-output" / "test-solution-independent-review" / "atl105-all-elements-reference.json"

# Appendix G p.545-546
TXN_TYPES = {"0": "POS Purchase/Capture", "3": "POS Authorization Only", "4": "Customer-activated Purchase/Capture",
             "5": "Customer-activated Authorization Only", "6": "Mail/Phone Purchase", "7": "Merchandise return",
             "8": "Purchase reversal", "9": "Special Transactions", "A": "Account Verification",
             "B": "Mail/Phone Authorization Only", "C": "Mail/Phone Reversal", "D": "RBC Loyalty Lookup",
             "E": "Balance Inquiry", "K": "Activate", "L": "Deactivate", "M": "Balance Merge", "N": "Replace Card",
             "Q": "Recharge Card", "S": "Cancellation", "T": "Token Registration", "U": "Void of a Merchandise Return",
             "V": "Loyalty advice function", "Z": "Time-out Reversal"}
# Appendix E p.521-522 financial / special card types
SPECIAL_CARD_TYPES = {"900", "901", "902", "903", "904", "905", "981", "990", "995", "996", "997"}
MASTERCARD_CREDIT_CARD_TYPE = "020"  # Appendix E p.521: 020 Credit
# Appendix J p.638-639
PAN_ENTRY_MODES = {"00", "01", "02", "04", "05", "07", "10", "79", "80", "82", "86", "90", "91", "95"}
PIN_CAPABILITY = {"0", "1", "2", "3", "6", "8"}
EMV_ENTRY_MODES = {"05", "07", "86", "95"}
EXPECTED_ENTRY_MODE = {"EMV_CONTACT": {"05"}, "EMV_CONTACTLESS": {"07", "86"}, "MAGSTRIPE": {"90"},
                       "MANUAL": {"01"}, "CREDENTIAL_ON_FILE": {"10", "01"}, "CONTACTLESS_MAGSTRIPE": {"91"},
                       "EMV_FALLBACK": {"80", "79"}, "CONTACTLESS_MOBILE": {"82", "07"}}
# Element 83 p.404-405
RESPONSE_CODES = set("0123456789BCDEFGHJKLMNOPSTUVWXY")
APPROVED_CODES_BY_ROLE = {
    "purchase": {"0", "4", "F"}, "moto_purchase": {"0", "4", "F"}, "split_tender": {"0", "4", "F"},
    "next_transaction": {"0", "4", "F"}, "subsequent": {"0", "4", "F"}, "completion": {"0", "4"},
    "completion_resubmitted": {"0", "4"}, "return": {"0"},
    "auth_only": {"2", "3", "F"}, "moto_auth_only": {"2", "3", "F"}, "account_verification": {"2", "3", "0"},
}
TOP_ROLE = {"PURCHASE": "purchase", "MOTO_PURCHASE": "moto_purchase", "AUTH_ONLY": "auth_only",
            "MOTO_AUTH_ONLY": "moto_auth_only", "RETURN": "return"}
PROMPT_TXN_BY_ROLE = {
    "purchase": {"0", "4"}, "split_tender": {"0", "4"}, "next_transaction": {"0", "4"}, "subsequent": {"0", "4"},
    "completion": {"0", "4"}, "completion_resubmitted": {"0", "4"}, "auth_only": {"3", "5"},
    "moto_purchase": {"6"}, "moto_auth_only": {"B"}, "moto_reversal": {"C"}, "return": {"7"},
    "reversal": {"8"}, "void_return": {"U"}, "cancellation": {"S"}, "timeout_reversal": {"Z"},
    "tor_retry": {"Z"}, "account_verification": {"A"}, "totals": {"9"},
}
TOP_PROMPT_TXN = {"PURCHASE": {"0", "4"}, "AUTH_ONLY": {"3", "5"}, "RETURN": {"7"}, "MOTO_PURCHASE": {"6"},
                  "MOTO_AUTH_ONLY": {"B"}, "BALANCE_INQUIRY": {"E"}}
# Element 86 p.~410: reversal/TOR/completion/cancel carry the original Sequence Number
SAME_SEQ_ROLES = {"reversal", "timeout_reversal", "tor_retry", "completion", "completion_resubmitted",
                  "cancellation", "moto_reversal", "void_return"}
NEW_SEQ_ROLES = {"split_tender", "next_transaction", "subsequent"}
CHECK_SEGMENTS = {"110", "113"}  # Check / ECA-TeleCheck: card type 041/045/046 only


def norm(s):
    s = unicodedata.normalize("NFKC", s or "")
    for a, b in (("\u2013", "-"), ("\u2014", "-"), ("\u2018", "'"), ("\u2019", "'"), ("\u201c", '"'),
                 ("\u201d", '"'), ("\u00ae", ""), ("\u2122", ""), ("\u2120", ""), ("Â", "")):
        s = s.replace(a, b)
    return re.sub(r"\s+", " ", s).strip().lower()


def squash(s):
    return re.sub(r"[^a-z0-9]", "", norm(s))


def load_pages(path):
    pages, cur, buf = {}, None, []
    for line in path.read_text(encoding="utf-8", errors="replace").splitlines():
        m = re.match(r"^--- PAGE (\d+) ---", line)
        if m:
            if cur is not None:
                pages[cur] = "\n".join(buf)
            cur, buf = int(m.group(1)), []
        else:
            buf.append(line)
    if cur is not None:
        pages[cur] = "\n".join(buf)
    return {p: squash(t) for p, t in pages.items()}


def luhn_ok(pan):
    total = 0
    for i, ch in enumerate(reversed(pan)):
        d = int(ch)
        if i % 2:
            d = d * 2 - 9 if d > 4 else d * 2
        total += d
    return total % 10 == 0


def is_mastercard_bin(pan):
    return pan[:2] in {"51", "52", "53", "54", "55"} or 2221 <= int(pan[:4] or 0) <= 2720


CHAR_RX = {"N": r"[0-9]+", "A": r"[A-Za-z ]+", "AN": r"[A-Za-z0-9 ]+", "ANS": r"[\x20-\x7E]+"}


class Validator:
    def __init__(self, pages, prior_pages, ref):
        self.pages, self.prior_pages = pages, prior_pages
        self.ref = {e["element"]: e for e in ref["elements"]}
        self.findings = []

    def add(self, tc, step, severity, rule, location, detail, basis):
        self.findings.append({"tc_id": tc["id"], "display_id": tc.get("display_id"), "template_id": tc.get("template_id"),
                              "entry_mode": tc["dimensions"].get("entry_mode"), "step": step, "severity": severity,
                              "rule": rule, "location": location, "detail": detail, "spec_basis": basis})

    # ---------- field-level ----------
    def check_field(self, tc, step, seg_no, seg_name, fname, f, network):
        val, en = f.get("value"), str(f.get("element_no") or "")
        loc = f"{seg_no} {seg_name} / {fname} (el {en})"
        if val in (None, ""):
            return
        val = str(val)
        if fname == "Segment Type" and val != seg_no:
            self.add(tc, step, "FAIL", "SEGMENT_TYPE_MISMATCH", loc, f"value {val} in segment {seg_no}", "Element 85")
        e = self.ref.get(en)
        if not e:
            return
        d = e["definitions"][0]
        base = re.sub(r"\s*\[\d+\]$", "", fname)
        if squash(base) != squash(d["name"]) and squash(base) not in {squash(n) for n in e.get("sourceNames", [])}:
            self.add(tc, step, "INFO", "ELEMENT_NO_COLLISION", loc,
                     f"element {en} in Chapter 13 is '{d['name']}', not '{base}'; constraints not applied",
                     f"Chapter 13 Element {en}")
            return
        c = d["constraints"]
        ctype, mx, mode = c.get("characterType"), c.get("maxLength"), c.get("lengthMode")
        rep = c.get("representation") or ""
        if en == "2":
            mx, mode = 24, "VARIABLE"  # Element 2 request: variable up to 24
        if "7 bytes is allowed for all other card types" in rep and network != "AMEX":
            mx = 7
        basis = f"Chapter 13 Element {en} ({d['name']}): {ctype} {mode} {mx}"
        rx = CHAR_RX.get(ctype)
        if rx and not re.fullmatch(rx, val):
            self.add(tc, step, "FAIL", "CHARACTER_TYPE", loc, f"'{val[:40]}' is not {ctype}", basis)
        if mx:
            if mode == "FIXED" and len(val) != mx:
                self.add(tc, step, "FAIL", "FIXED_LENGTH", loc, f"length {len(val)} != {mx} ('{val[:40]}')", basis)
            elif mode == "VARIABLE" and len(val) > mx:
                self.add(tc, step, "FAIL", "MAX_LENGTH", loc, f"length {len(val)} > {mx}", basis)

    # ---------- message-level ----------
    def check_request(self, tc, step, req, role, network, txn_category):
        flat = {}
        segs_with_data, empty_segs = set(), []
        for seg_name, fields in req.items():
            seg_no = next((v["data_ref"]["segment"] for v in fields.values()
                           if isinstance(v, dict) and v.get("data_ref")), "?")
            data = False
            for fname, f in fields.items():
                if not isinstance(f, dict):
                    continue
                self.check_field(tc, step, seg_no, seg_name, fname, f, network)
                flat[(seg_no, fname)] = f.get("value")
                if fname not in ("Segment Type", "Segment Length", "Segment Length Indicator") and f.get("value") not in (None, ""):
                    data = True
            (segs_with_data.add(seg_no) if data else empty_segs.append(f"{seg_no} {seg_name}"))
        if empty_segs:
            self.add(tc, step, "REVIEW", "EMPTY_SEGMENT_EMITTED", ", ".join(empty_segs),
                     "segment carries only Segment Type/Length - no data elements", "Chapter 12 segment layouts")

        g = lambda s, n: flat.get((s, n))
        prompt = g("100", "Prompt Code")
        if prompt:
            p = str(prompt)
            if len(p) == 4:
                if p[0] not in TXN_TYPES:
                    self.add(tc, step, "FAIL", "PROMPT_TXN_TYPE", "100/Prompt Code", f"pos 1 '{p[0]}' not in Appendix G", "Appendix G p.545-546")
                if p[0] != "9" and p[1:] != MASTERCARD_CREDIT_CARD_TYPE:
                    self.add(tc, step, "FAIL", "PROMPT_CARD_TYPE", "100/Prompt Code", f"card type '{p[1:]}' != 020 (Credit)", "Appendix E p.521")
                leg_code = (tc.get("tx_legs") or [{}])[0].get("code") if not role else None
                exp = PROMPT_TXN_BY_ROLE.get(role) if role else ({leg_code} if leg_code else TOP_PROMPT_TXN.get(txn_category))
                if exp and p[0] not in exp:
                    self.add(tc, step, "FAIL", "PROMPT_TXN_VS_ROLE", "100/Prompt Code",
                             f"'{p}' -> '{TXN_TYPES.get(p[0])}' but leg is {role or txn_category} (expected {sorted(exp)})", "Appendix G p.545-546")
                if p[0] == "9":
                    self.add(tc, step, "REVIEW", "SPECIAL_PROMPT_FORMAT", "100/Prompt Code",
                             f"'{p}': special transactions use the 3-char Card Type only (e.g. 990 Totals)", "Appendix E p.522 / Appendix G p.545")
            elif len(p) == 3 and p not in SPECIAL_CARD_TYPES:
                self.add(tc, step, "FAIL", "PROMPT_SPECIAL", "100/Prompt Code", f"'{p}' not a special Card Type", "Appendix E p.522")

        pan = g("100", "Account Number")
        if pan and str(pan).isdigit():
            pan = str(pan)
            if not luhn_ok(pan):
                self.add(tc, step, "FAIL", "PAN_LUHN", "100/Account Number", f"{pan[:6]}...{pan[-4:]} fails Luhn", "ISO/IEC 7812 (IIN manual ref. Element 2)")
            if not is_mastercard_bin(pan):
                self.add(tc, step, "FAIL", "PAN_NOT_MASTERCARD", "100/Account Number", f"BIN {pan[:6]}", "Element 2 -> IIN Reference Manual")

        # Variable Information Table 005 (Appendix J)
        vi = [(g("111", f"Variable Information Indicator{s}"), g("111", f"Variable Information Length{s}"), g("111", f"Variable Information{s}"))
              for s in ("", " [2]", " [3]", " [4]")]
        entry = None
        for ind, ln, v in vi:
            if ind is None:
                continue
            if v is not None and ln is not None and str(ln).isdigit() and int(ln) != len(str(v)):
                self.add(tc, step, "FAIL", "VARINFO_LENGTH", f"111/Table {ind}", f"length {ln} != len('{v}')={len(str(v))}", "Element 112")
            if ind == "005" and v:
                entry = str(v)[:2]
                if entry not in PAN_ENTRY_MODES:
                    self.add(tc, step, "FAIL", "ENTRY_MODE_CODE", "111/Table 005", f"PAN entry '{entry}'", "Appendix J p.638")
                if len(str(v)) > 2 and str(v)[2] not in PIN_CAPABILITY:
                    self.add(tc, step, "FAIL", "PIN_CAPABILITY_CODE", "111/Table 005", f"pos 3 '{str(v)[2]}'", "Appendix J p.639")
                if str(v)[2:3] == "8" and entry in EMV_ENTRY_MODES:
                    self.add(tc, step, "FAIL", "PIN_CAPABILITY_EMV", "111/Table 005", f"'{v}': EMV must use 1 not 8", "Appendix J p.639 note 1")
        if "031" not in {ind for ind, _, _ in vi} and role not in ("totals",):
            self.add(tc, step, "FAIL", "TABLE_031_MISSING", "111", "Table 031 must be populated for all transactions", "Table 031 p.569")

        emv_seg = "130" in segs_with_data
        if entry in EMV_ENTRY_MODES and not emv_seg and role not in SAME_SEQ_ROLES:
            self.add(tc, step, "FAIL", "EMV_SEGMENT_MISSING", "130", f"entry mode {entry} but no Segment 130", "Segment 130 p.203/206")
        if emv_seg and entry and entry not in EMV_ENTRY_MODES:
            self.add(tc, step, "FAIL", "EMV_SEGMENT_ON_NON_EMV", "130", f"Segment 130 sent with entry mode {entry}", "Segment 130 p.203/206")

        if CHECK_SEGMENTS & segs_with_data and prompt and str(prompt)[1:] == "020":
            self.add(tc, step, "FAIL", "CHECK_SEGMENT_ON_CREDIT", ",".join(sorted(CHECK_SEGMENTS & segs_with_data)),
                     "Check/ECA-TeleCheck data sent on a Credit (020) prompt code", "Appendix E p.521 (041/045/046 = Check)")

        # Element 12: swiped track data vs MMYY
        cdb = g("100", "Card Discretionary Block Data")
        if cdb is not None:
            if entry in {"90", "91", "80"} and re.fullmatch(r"\d{4}", str(cdb)):
                self.add(tc, step, "FAIL", "TRACK_DATA_NOT_SENT", "100/Card Discretionary Block Data",
                         f"entry {entry} (track read) but value is MMYY '{cdb}' - must be all track data after '='",
                         "Element 12 p.371")
            elif entry in {"01", "10", "79"} and re.fullmatch(r"\d{4}", str(cdb)) and not 1 <= int(str(cdb)[:2]) <= 12:
                self.add(tc, step, "FAIL", "EXPIRY_MMYY", "100/Card Discretionary Block Data", f"'{cdb}' month invalid", "Element 12 p.372")

        # EMV segment internal consistency
        csn = g("130", "EMV Card Sequence Number")
        if csn is not None and not re.fullmatch(r"\d{3}|\s{3}", str(csn)):
            self.add(tc, step, "FAIL", "EMV_CSN_FORMAT", "130/EMV Card Sequence Number",
                     f"'{csn}' must be 3 bytes right-aligned zero-padded (e.g. '0{csn}')", "Element 188")
        chip, chip_len = g("130", "EMV Chip Data"), g("130", "EMV Chip Data Length")
        if chip and chip_len:
            n = len(str(chip))
            if int(chip_len) not in (n, n // 2):
                self.add(tc, step, "FAIL", "EMV_CHIP_LENGTH", "130/EMV Chip Data Length", f"{chip_len} vs data {n} chars ({n//2} bytes)", "Element 189/190")
            amt = g("100", "Nonfuel Amount") or g("100", "Fuel Purchase Amount")
            m = re.search(r"9F0206(\d{12})", str(chip))
            if m and amt and int(m.group(1)) != int(amt):
                self.add(tc, step, "REVIEW", "EMV_AMOUNT_MISMATCH", "130/EMV Chip Data 9F02",
                         f"9F02={int(m.group(1))} vs request amount {int(amt)}", "EMV Guide / Element 190")

        # Loyalty tender vs network
        tender = g("108", "Payment Tender Type")
        if tender and network == "MASTERCARD" and tender != "MC":
            self.add(tc, step, "FAIL", "TENDER_TYPE_VS_CARD", "108/Payment Tender Type", f"'{tender}' on a MasterCard transaction", "Element 148")
        lt2 = g("108", "Loyalty Track 2 Data")
        if lt2 and pan and str(lt2).startswith(str(pan)):
            self.add(tc, step, "REVIEW", "LOYALTY_TRACK_IS_PAYMENT_PAN", "108/Loyalty Track 2 Data",
                     "loyalty track built from the payment PAN (no '=' separator)", "Element 147 / Element 12 track rules")

        # Product segment arithmetic
        qty, up, pa, pc = g("102", "Quantity"), g("102", "Unit Price"), g("102", "Product Amount"), g("102", "Product Code")
        nonfuel, fuel = g("100", "Nonfuel Amount"), g("100", "Fuel Purchase Amount")
        if pa is not None and nonfuel and not fuel and int(pa) != int(nonfuel):
            self.add(tc, step, "REVIEW", "PRODUCT_AMOUNT_VS_TOTAL", "102/Product Amount", f"Product Amount {pa} vs Nonfuel Amount {nonfuel}", "Segment 102 / Element 58,76")
        if pc and str(pc).isdigit() and 1 <= int(pc) <= 99 and nonfuel and not fuel:
            self.add(tc, step, "REVIEW", "FUEL_PRODUCT_AS_NONFUEL", "102/Product Code", f"motor-fuel code {pc} but only Nonfuel Amount sent", "Appendix F (001-099 motor fuel) / Element 41,58")
        if qty and up is not None and pa is not None and int(up) == 0 and int(pa) != 0:
            self.add(tc, step, "REVIEW", "PRODUCT_PRICE_ZERO", "102", f"Unit Price 0 x Qty {qty} != Product Amount {pa}", "Element 76/81/107")
        return flat, entry

    # ---------- spec_basis quotes ----------
    def check_quotes(self, tc):
        for sb in tc.get("spec_basis") or []:
            q, page = sb.get("quote") or "", sb.get("page")
            if not q or page is None or (sb.get("source_id") or "SRC-ATL105-PDF-001") != "SRC-ATL105-PDF-001":
                continue
            parts = [squash(p) for p in re.split(r"\.\.\.|…", q) if len(squash(p)) >= 8]
            if not parts:
                continue
            def found(pages, pg):
                return all(p in pages.get(pg, "") for p in parts)
            if found(self.pages, page):
                continue
            near = [p for p in (page - 1, page + 1) if found(self.pages, p)]
            if near:
                self.add(tc, None, "REVIEW", "QUOTE_PAGE_DRIFT", f"spec_basis p.{page}", f"'{q[:80]}' found on p.{near[0]}", "2026-4 PDF")
                continue
            anywhere = [p for p in self.pages if found(self.pages, p)]
            if anywhere:
                self.add(tc, None, "REVIEW", "QUOTE_WRONG_PAGE", f"spec_basis p.{page}", f"'{q[:80]}' found on p.{anywhere[:3]}", "2026-4 PDF")
            elif self.prior_pages and any(found(self.prior_pages, p) for p in self.prior_pages):
                self.add(tc, None, "REVIEW", "QUOTE_ONLY_IN_2026_3", f"spec_basis p.{page}", f"'{q[:80]}'", "2026-3 vs 2026-4 PDF")
            elif all(squash(w) in self.pages.get(page, "") for w in re.findall(r"[A-Za-z0-9]{2,}", q)):
                self.add(tc, None, "REVIEW", "QUOTE_PARAPHRASED", f"spec_basis p.{page}",
                         f"'{q[:80]}' is not verbatim; all its words occur on p.{page}", "2026-4 PDF")
            else:
                self.add(tc, None, "FAIL", "QUOTE_NOT_IN_SPEC", f"spec_basis p.{page}", f"'{q[:100]}' not found in spec", "2026-4 PDF")

    def check_response(self, tc, step, er, role):
        rc = ((er or {}).get("response_code") or {})
        v = rc.get("value")
        if v is None:
            return
        if v not in RESPONSE_CODES:
            self.add(tc, step, "FAIL", "RESPONSE_CODE_INVALID", "expected Response Code", f"'{v}'", "Element 83 p.404-405")
            return
        ok = APPROVED_CODES_BY_ROLE.get(role)
        if ok and tc.get("polarity") == "Positive" and rc.get("tier") == "ASSERT" and v not in ok:
            self.add(tc, step, "FAIL", "RESPONSE_CODE_VS_TXN", "expected Response Code",
                     f"'{v}' asserted for {role}; spec approval codes {sorted(ok)}", "Element 83 p.404")

    # ---------- test case ----------
    def check(self, tc):
        net = tc["dimensions"].get("network")
        cat = tc["dimensions"].get("txn_category")
        self.check_quotes(tc)
        steps = tc.get("steps") or []
        if not steps:
            if tc.get("request"):
                _, entry = self.check_request(tc, None, tc["request"], None, net, cat)
                exp = EXPECTED_ENTRY_MODE.get(tc["dimensions"].get("entry_mode"))
                if entry and exp and entry not in exp:
                    self.add(tc, None, "FAIL", "ENTRY_MODE_VS_SCENARIO", "111/Table 005", f"{entry} for {tc['dimensions'].get('entry_mode')}", "Appendix J p.638")
            self.check_response(tc, None, tc.get("expected_response"), TOP_ROLE.get(cat))
        else:
            flats, entries = {}, {}
            for s in steps:
                flats[s["step"]], entries[s["step"]] = self.check_request(tc, s["step"], s.get("request") or {}, s.get("role"), net, cat)
                self.check_response(tc, s["step"], s.get("expected_response"), s.get("role"))
            first = steps[0]["step"]
            exp = EXPECTED_ENTRY_MODE.get(tc["dimensions"].get("entry_mode"))
            if entries.get(first) and exp and entries[first] not in exp:
                self.add(tc, first, "FAIL", "ENTRY_MODE_VS_SCENARIO", "111/Table 005", f"{entries[first]} for {tc['dimensions'].get('entry_mode')}", "Appendix J p.638")
            if tc["dimensions"].get("entry_mode") == "CREDENTIAL_ON_FILE" and "10" not in entries.values():
                self.add(tc, None, "FAIL", "COF_ENTRY_MODE_10_MISSING", "111/Table 005",
                         f"no leg uses entry mode 10 (legs: {entries})", "Appendix J p.638 note 2")
            legs = {l["step"]: l for l in tc.get("tx_legs") or []}
            # negative lifecycle tests break linkage on purpose
            for s in ([] if tc.get("polarity") == "Negative" else steps[1:]):
                st, role = s["step"], s.get("role")
                of = (legs.get(st) or {}).get("of") or first
                a, b = flats.get(of, {}), flats.get(st, {})
                sa, sb = a.get(("100", "Sequence Number")), b.get(("100", "Sequence Number"))
                if role in SAME_SEQ_ROLES and sa and sb and sa != sb:
                    sev = "REVIEW" if role == "completion_resubmitted" else "FAIL"
                    self.add(tc, st, sev, "SEQ_NOT_PERSISTED", "100/Sequence Number",
                             f"{role}: {sb} != original {sa} (Element 86: completion keeps the Auth Only number; p.40: each forwarded request gets a new one)",
                             "Element 86 / p.40")
                if role in NEW_SEQ_ROLES and sa and sb and sa == sb:
                    self.add(tc, st, "FAIL", "SEQ_NOT_NEW", "100/Sequence Number", f"{role} reuses {sa}", "p.40 'new Sequence Number'")
                ta, tb = a.get(("100", "Terminal Identifier")), b.get(("100", "Terminal Identifier"))
                if role in ("timeout_reversal", "tor_retry") and ta and tb and ta != tb:
                    self.add(tc, st, "FAIL", "TOR_DEVICE", "100/Terminal Identifier", "TOR from a different device", "p.40")
                pa_, pb_ = a.get(("100", "Account Number")), b.get(("100", "Account Number"))
                if role in SAME_SEQ_ROLES and pa_ and pb_ and pa_ != pb_:
                    self.add(tc, st, "FAIL", "PAN_NOT_PERSISTED", "100/Account Number", "follow-on uses a different PAN", "p.40 matching keys")
                ea, eb = entries.get(of), entries.get(st)
                if role in SAME_SEQ_ROLES and ea and eb and eb != ea and not (ea == "90" and eb == "02"):
                    self.add(tc, st, "FAIL", "ENTRY_MODE_NOT_PERSISTED", "111/Table 005", f"{eb} vs original {ea}", "Appendix J p.638 note 1")
                if role in SAME_SEQ_ROLES and ea == "90" and eb == "90":
                    self.add(tc, st, "FAIL", "ENTRY_MODE_90_FOLLOW_ON", "111/Table 005", "follow-on of a 90 original must use 02", "Appendix J p.638 note 1")
        placeholders = [f for f in tc.get("flags") or [] if f.startswith(("AWAITING_CLIENT_VALUE", "MERCHANT_PLACEHOLDER", "NEEDS_CLIENT_INPUT"))]
        if tc.get("status") == "ready" and placeholders:
            self.add(tc, None, "REVIEW", "READY_WITH_PLACEHOLDERS", "status", "; ".join(p.split(":")[0] for p in placeholders), "Test-data readiness")


def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--test-cases", default=str(ROOT.parent / "test_cases.jsonl"))
    ap.add_argument("--spec-text", default=str(DEFAULT_SPEC))
    ap.add_argument("--out", default=str(ROOT / "test-output" / "test-cases-spec-validation"))
    a = ap.parse_args()

    rows = [json.loads(l) for l in open(a.test_cases, encoding="utf-8") if l.strip()]
    v = Validator(load_pages(Path(a.spec_text)), load_pages(PRIOR_SPEC) if PRIOR_SPEC.exists() else {},
                  json.loads(ELEMENT_REF.read_text(encoding="utf-8")))
    for tc in rows:
        v.check(tc)

    out = Path(a.out)
    out.mkdir(parents=True, exist_ok=True)
    with open(out / "findings.csv", "w", newline="", encoding="utf-8") as fh:
        w = csv.DictWriter(fh, fieldnames=list(v.findings[0].keys()) if v.findings else ["tc_id"])
        w.writeheader()
        w.writerows(v.findings)

    by_rule = defaultdict(lambda: {"severity": None, "occurrences": 0, "tcs": set(), "example": None, "spec_basis": None})
    for f in v.findings:
        r = by_rule[f["rule"]]
        r["severity"], r["spec_basis"] = f["severity"], f["spec_basis"]
        r["occurrences"] += 1
        r["tcs"].add(f["tc_id"])
        r["example"] = r["example"] or f"{f['tc_id']} step={f['step']} {f['location']}: {f['detail']}"
    tc_sev = defaultdict(set)
    for f in v.findings:
        tc_sev[f["tc_id"]].add(f["severity"])
    verdict = Counter("FAIL" if "FAIL" in tc_sev[t["id"]] else "REVIEW" if "REVIEW" in tc_sev[t["id"]] else "PASS" for t in rows)
    summary = {"test_cases": len(rows), "spec_text": a.spec_text, "verdict": dict(verdict),
               "rules": {k: {**r, "tcs": len(r["tcs"])} for k, r in sorted(by_rule.items(), key=lambda x: (x[1]["severity"], -len(x[1]["tcs"])))}}
    (out / "summary.json").write_text(json.dumps(summary, indent=2, ensure_ascii=False), encoding="utf-8")
    print(f"TCs: {len(rows)}  verdict: {dict(verdict)}")
    for k, r in summary["rules"].items():
        print(f"[{r['severity']:6}] {k:30} tcs={r['tcs']:4} occ={r['occurrences']:5} | {r['spec_basis']}\n          e.g. {r['example'][:220]}")


if __name__ == "__main__":
    main()
