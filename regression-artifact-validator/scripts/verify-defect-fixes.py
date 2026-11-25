#!/usr/bin/env python3
"""Defect fix verification: re-check every defect logged for the previous AI test data run against a new run.

Previous defects ('Defect MD file/'): CD-xx of the consolidated log (American Express Credit, MasterCard Credit, MasterCard
Fleet; source IDs A-xx, D-xx, FL-xx) and D-xx of the Interlink Debit report. Every defect rule is run on every test data file of
the previous run (it must reproduce the logged counts) and of the new run. Test case IDs are renumbered between runs, so test
cases are matched by transaction category + entry mode + template, and flow steps by step number and role.

Writes <out>/README.md, <out>/<Program>_Fix_Verification.md and <out>/Defect_Fix_Verification.xlsx.
"""

import argparse
import importlib.util
import json
import re
import sys
import zipfile
from collections import Counter, defaultdict
from pathlib import Path

ROOT = Path(__file__).resolve().parents[1]
_spec = importlib.util.spec_from_file_location("bemvr", ROOT / "scripts" / "build-entry-mode-validation-report.py")
bld = importlib.util.module_from_spec(_spec)
_spec.loader.exec_module(bld)
tdv, tcv = bld.tdv, bld.tcv

OLD_DEFAULT = Path.home() / "Downloads" / "31-10-out"
NEW_DEFAULT = ROOT / "5-11-out"
OUT_DEFAULT = ROOT / "Defect_Fix_Verification_5-11"
RUN_MONTH = "2611"  # expiry checks: run month November 2026 (as in the consolidated log)
FOLLOW = set(tcv.SAME_SEQ_ROLES)
TRACK = re.compile(r"\d{4}\d{3}\S+")
CVM_OFFLINE = {0x01, 0x03, 0x04, 0x05}
SEGN = {"100": "Standard Message Data Segment", "111": "Variable Information Data Segment", "130": "EMV Request Data Segment",
        "105": "Totals Data Segment", "101": "Fleet Data Segment", "102": "Product Code Data Segment"}
TD_NAME = {key: name for _, _, key, name, _ in tdv.SEG100}

PROGRAMS = [
    dict(key="american_express_credit", name="American Express Credit", file="AMEX_Credit", engine="rules", mc=False, amex=True,
         logs="Consolidated_Defect_Log_AMEX-Credit_MC-Credit_MC-Fleet.md (CD-xx) and AMEX_Credit_Defect_Log.md (A-xx)",
         old_link=None),
    dict(key="mastercard_credit", name="MasterCard Credit", file="MasterCard_Credit", engine="rules", mc=True, amex=False,
         logs="Consolidated_Defect_Log_AMEX-Credit_MC-Credit_MC-Fleet.md (CD-xx) and MasterCard_Credit_Consolidated_Defect_Report.md "
              "(D-xx)", old_link="../MasterCard%20Credit/mastercard_credit/test_data/"),
    dict(key="mastercard_fleet", name="MasterCard Fleet", file="MasterCard_Fleet", engine="rules", mc=True, amex=False,
         logs="Consolidated_Defect_Log_AMEX-Credit_MC-Credit_MC-Fleet.md (CD-xx) and MasterCard_Fleet_Consolidated_Defects.md "
              "(FL-xx)", old_link=None),
    dict(key="interlink_debit", name="Interlink Debit", file="Interlink_Debit", engine="validator", mc=False, amex=False,
         logs="Interlink_Debit_Consolidated_Defect_Report.md (D-xx)", old_link="../interlink_debit/test_data/"),
    dict(key="maestro_direct_debit", name="Maestro Direct Debit", file="Maestro_Direct_Debit", engine="validator", mc=False,
         amex=False, missed=True, old_link="../maestro_direct_debit/test_data/",
         logs="none – Maestro was missed in the previous logs; its defects were found now in the previous run: "
              "[maestro_direct_debit/Manual Validation Report/Consolidated_Defect_Report.md]"
              "(../maestro_direct_debit/Manual%20Validation%20Report/Consolidated_Defect_Report.md) (D-xx)"),
]
# defect logs: counts (test cases, test data files) found in the previous run
LOGGED = {
    "CD-01": {"american_express_credit": (94, 116), "mastercard_credit": (104, 126), "mastercard_fleet": (60, 78)},
    "CD-02": {"american_express_credit": (94, 116), "mastercard_credit": (104, 126), "mastercard_fleet": (60, 78)},
    "CD-03": {"american_express_credit": (140, 204), "mastercard_credit": (158, 223), "mastercard_fleet": (90, 139)},
    "CD-04": {"american_express_credit": (46, 88), "mastercard_credit": (54, 97), "mastercard_fleet": (30, 61)},
    "CD-05": {"american_express_credit": (44, 54), "mastercard_credit": (47, 57), "mastercard_fleet": (30, 39)},
    "CD-06": {"american_express_credit": (5, 5), "mastercard_credit": (5, 5), "mastercard_fleet": (5, 5)},
    "CD-07": {"american_express_credit": (31, 31), "mastercard_credit": (31, 31)},
    "CD-08": {"american_express_credit": (4, 4), "mastercard_credit": (4, 4)},
    "CD-09": {"american_express_credit": (6, 6), "mastercard_credit": (6, 6), "mastercard_fleet": (249, 497)},
    "CD-10": {"american_express_credit": (72, 72), "mastercard_credit": (72, 72), "mastercard_fleet": (72, 72)},
    "CD-11": {"american_express_credit": (8, 8), "mastercard_credit": (8, 8), "mastercard_fleet": (8, 8)},
    "CD-12": {"american_express_credit": (2, 2), "mastercard_credit": (2, 2)},
    "CD-13": {"mastercard_credit": (4, 4)},
    "CD-14": {"american_express_credit": (2, 2), "mastercard_credit": (2, 2)},
    "CD-15": {"mastercard_credit": (2, 2)},
    "CD-16": {"mastercard_credit": (4, 4), "mastercard_fleet": (2, 2)},
    "CD-17": {"american_express_credit": (2, 2), "mastercard_credit": (2, 2)},
    "CD-18": {"american_express_credit": (8, 8), "mastercard_credit": (8, 8)},
    "D-01": {"interlink_debit": (72, 90)}, "D-02": {"interlink_debit": (72, 90)},
    "D-09": {"interlink_debit": (35, 67)},
    "D-12": {"interlink_debit": (107, 157)}, "D-14": {"interlink_debit": (7, 7)}, "D-15": {"interlink_debit": (249, 473)},
    "D-16": {"interlink_debit": (242, 305)}, "D-17": {"interlink_debit": (34, 43)}, "D-18": {"interlink_debit": (4, 4)},
    "D-19": {"interlink_debit": (63, 63)}, "D-20": {"interlink_debit": (63, 63)}, "D-21": {"interlink_debit": (14, 14)},
    "D-24": {"interlink_debit": (14, 14)},
}
# IDs of the same defect in the source logs
SOURCE = {
    "american_express_credit": {"CD-01": "A-01", "CD-02": "A-02", "CD-05": "A-03, A-03b", "CD-07": "A-06", "CD-08": "A-07",
                                "CD-10": "A-05", "CD-12": "A-08", "CD-14": "A-09", "CD-17": "A-10", "RV-01": "A-04"},
    "mastercard_credit": {"CD-01": "D-01", "CD-02": "D-02", "CD-03": "D-12", "CD-04": "D-09", "CD-05": "D-08", "CD-06": "D-13",
                          "CD-07": "D-05, D-14", "CD-08": "D-05", "CD-12": "D-03", "CD-13": "D-04", "CD-14": "D-11", "CD-16": "D-07",
                          "CD-17": "D-06", "CD-18": "D-10", "CD-09": "S2 D-08", "CD-10": "S2 D-06", "CD-11": "S2 D-10",
                          "CD-15": "S2 D-09", "CD-19": "S2 O31-01", "CD-20": "S2 O31-03"},
    "mastercard_fleet": {"CD-01": "FL-03", "CD-02": "FL-04", "CD-09": "FL-02", "CD-19": "O31-01", "RV-02": "FL-01"},
    "interlink_debit": {"D-01": "= CD-01", "D-02": "= CD-02", "D-09": "= CD-04", "D-12": "= CD-03", "D-14": "= CD-07",
                        "D-19": "= CD-10", "D-18": "= CD-15"},
}
SOURCE["maestro_direct_debit"] = SOURCE["interlink_debit"]
FIXED, AWAIT, NOT, CHANGED, REMOVED = "FIXED", "AWAITING CLIENT INPUT", "NOT FIXED", "CHANGED", "REMOVED"


def not_logged(prog):
    return " (missed – not in the previous logs)" if prog.get("missed") else " (not in the log)"


def short(s, n=60):
    s = str(s)
    return s if len(s) <= n else f"{s[:n - 14]}…{s[-10:]} ({len(s)} chars)"


def md(s):
    return re.sub(r"\s*[\r\n]+\s*", " ", str(s).replace("|", "\\|"))


def pl(k, word):
    return f"{k} {word}{'' if k == 1 else 's'}"


def r(state, field, line, value, expected="", note=""):
    return dict(state=state, field=field, line=line, value=str(value), expected=expected, note=note)


# ---------------------------------------------------------------- catalog and test data
class Leg:
    def __init__(self, tc, step, role, code, of, name):
        self.tc, self.step, self.role, self.code, self.of, self.name = tc, step, role or "", code, of, name
        self.path = tc.run.td_dir / name
        text = self.path.read_text(encoding="utf-8") if self.path.exists() else ""
        self.lines = text.splitlines()
        data = json.loads(text) if text.strip() else {}
        self.root = next(iter(data), "")
        msg = data.get(self.root) or {}
        self.totals = self.root == tdv.TOTALS_ROOT
        self.s100 = msg.get(SEGN["100"]) or {}
        self.v = tdv.td_seg100_values(self.s100) if self.s100 else {}
        vi = (msg.get(SEGN["111"]) or {}).get("VariableInformationSection") or []
        self.vi = [(str(x.get("VariableInformationIndicator", "")), str(x.get("VariableInformationLength", "")),
                    str(x.get("VariableInformation", ""))) for x in vi]
        self.tables = {}
        for i, (ind, ln, val) in enumerate(self.vi):
            self.tables.setdefault(ind, (i, ln, val))
        self.s130 = msg.get(SEGN["130"])
        self.tags = {t: x for t, _, x in tdv.parse_tlv(str(self.s130.get("EMVChipData", "")))[1]} if self.s130 else {}
        self.s105 = msg.get(SEGN["105"]) or {}
        self.s101, self.s102 = msg.get(SEGN["101"]), msg.get(SEGN["102"])
        t005 = self.tables.get("005", (0, "", ""))[2]
        self.entry, self.pincap = t005[:2], t005[2:3]
        self.follow = self.role in FOLLOW

    @property
    def request(self):
        return bool(self.s100) and not self.totals

    def line(self, key, nth=1):
        k = 0
        for i, s in enumerate(self.lines, 1):
            if f'"{key}":' in s:
                k += 1
                if k == nth:
                    return i
        return None

    def line_after(self, anchor, key):
        start = self.line(anchor) or 0
        return next((i for i, s in enumerate(self.lines, 1) if i > start and f'"{key}":' in s), None)

    def vi_line(self, ind):
        t = self.tables.get(ind)
        return self.line("VariableInformation", t[0] + 1) if t else self.line("VariableInformationSection")

    def orig(self):
        of = self.of or 1
        return next((x for x in self.tc.legs if x.step == of and x is not self), None)

    def attr(self, seg, name):
        return self.tc.attrs.get((self.step, SEGN[seg], name), (None, None))

    def awaiting(self, seg, name, words=()):
        if self.attr(seg, name)[1] == "awaiting_client_value":
            return True
        own = [s for s in self.tc.awaiting if f"step {self.step}" in s.lower() or "step " not in s.lower()]
        return bool(words) and any(all(w.lower() in s.lower() for w in words) for s in own)

    def vi_awaiting(self, ind):
        t = self.tables.get(ind)
        name = "Variable Information" + ("" if not t or t[0] == 0 else f" [{t[0] + 1}]")
        return self.awaiting("111", name, (f"Table {ind}",))

    def tables_text(self):
        return ", ".join(f"{ind}={val!r}" if not val else f"{ind}={val}" for ind, _, val in self.vi) or "none"


class TC:
    def __init__(self, run, rec, line):
        d = rec.get("dimensions") or {}
        self.run, self.line, self.id, self.disp = run, line, rec["id"], rec.get("display_id", "")
        self.tpl, self.typ, self.status = rec.get("template_id", ""), rec.get("tc_type", ""), rec.get("status", "")
        self.cat, self.mode = d.get("txn_category", ""), d.get("entry_mode", "")
        self.key = (self.cat, self.mode, self.tpl)
        self.build = rec.get("build_id")
        self.awaiting = [str(x) for x in rec.get("awaiting_inputs") or []]
        legs = {lg["step"]: lg for lg in rec.get("tx_legs") or []}
        files = [Path(p).name for p in rec.get("test_data_leg_files") or ([rec["test_data_file"]] if rec.get("test_data_file") else [])]
        raw = rec.get("steps") or ([dict(step=1, request=rec.get("request") or {})] if rec.get("request") else [])
        self.attrs, self.legs = {}, []
        self.expect = {}
        for i, s in enumerate(raw):
            n = s.get("step", i + 1)
            lexp = (legs.get(n) or {}).get("expect") or {}
            rc = (s.get("expected_response") or {}).get("response_code") or {}
            self.expect[n] = dict(rc=rc.get("value") or lexp.get("response_code"), none="none" in lexp,
                                  note=lexp.get("note") or rc.get("note"))
            for seg, fields in (s.get("request") or {}).items():
                for k, a in (fields.items() if isinstance(fields, dict) else []):
                    if isinstance(a, dict):
                        self.attrs[(n, seg, k)] = (a.get("value"), a.get("method"))
            if legs and i < len(files):
                lg = legs.get(n, {})
                self.legs.append(Leg(self, n, s.get("role") or lg.get("role"), lg.get("code"), lg.get("of"), files[i]))
        self.first = self.legs[0].step if self.legs else None
        self.rep = self.ctx = None


class Run:
    def __init__(self, label, base, prog, validate=None):
        self.label, self.base, self.prog = label, base, prog
        self.td_dir = base / prog["key"] / "test_data"
        self.jsonl = base / prog["key"] / "test_cases" / "test_cases.jsonl"
        self.tcs, self.bad_lines = [], []
        with self.jsonl.open(encoding="utf-8") as fh:
            for i, line in enumerate(fh, 1):
                if not line.strip():
                    continue
                try:
                    rec = json.loads(line)
                except json.JSONDecodeError:
                    self.bad_lines.append(i)
                    continue
                tc = TC(self, rec, i)
                if validate and tc.legs and validate[0](rec):
                    tconv, meta = bld.convert(rec)
                    tc.rep, tc.ctx = tdv.validate_tc(tconv, meta, self.td_dir, *validate[1])
                    tc.conv = tconv
                self.tcs.append(tc)
        self.by_key = {tc.key: tc for tc in self.tcs}
        self.files = sorted(p.name for p in self.td_dir.glob("*.json")) if self.td_dir.exists() else []


# ---------------------------------------------------------------- defect rules (consolidated log CD-xx)
def cd01(L):
    if not L.s130:
        return None
    v, t = str(L.s130.get("EMVCardSequenceNumber", "")), L.tags.get("5F34")
    return r("ok" if len(v) == 3 else "bad", "EMVCardSequenceNumber (E188)", L.line("EMVCardSequenceNumber"), v,
             f"{t.zfill(3) if t else '   '} (Tag 5F34 zero-padded to 3)")


def cd02(L):
    if not L.s130 or str(L.s130.get("EMVAdditionalInformationIndicator", "")) != "001":
        return None
    info, ln = str(L.s130.get("EMVAdditionalInformation", "")), str(L.s130.get("EMVAdditionalInformationLength", ""))
    ok = info in ("EMVYES", "EMVNOT") and ln == f"{len(info):03d}"
    return r("ok" if ok else "bad", "EMVAdditionalInformation (E118) / Length (E192)", L.line("EMVAdditionalInformation"),
             f"{short(info, 30)} / length {ln}", "EMVYES or EMVNOT / length 006")


def cd03(L):
    if not L.request or not (L.s130 or L.entry == "10"):
        return None
    t = L.tables.get("030")
    if t is None:
        return r("bad", "Table 030 (Segment 111)", L.line("VariableInformationSection"), f"not sent (tables: {L.tables_text()})",
                 "Table 030 with the 10-digit POS Condition Code")
    i, ln, val = t
    if re.fullmatch(r"\d{10}", val):
        return r("ok", "Table 030 (Segment 111)", L.vi_line("030"), f"030 = {val} (length {ln})", "10 digits")
    if not val and L.vi_awaiting("030"):
        return r("await", "Table 030 (Segment 111)", L.vi_line("030"), f"030 sent, length {ln}, value empty",
                 "10 digits from the client's certified terminal",
                 f"value listed in awaiting_inputs; length {ln} is declared for the value to come, so the file cannot be sent "
                 "until the code is filled in")
    return r("bad", "Table 030 (Segment 111)", L.vi_line("030"), f"030 = '{val}' (length {ln})", "10 digits (Appendix Q)")


def cd04(L):
    if not L.request or L.entry != "10":
        return None
    need = [("032", lambda x: "08001C" in x or "08001I" in x, "032-08 C/I"), ("049", lambda x: x[:2] == "02", "049-02")]
    if L.tc.run.prog["mc"]:
        need.append(("056", lambda x: "09" in x, "056-09"))
    states, parts = [], []
    for ind, ok, label in need:
        t = L.tables.get(ind)
        if t is not None and ok(t[2]):
            states.append("ok")
            parts.append(f"{label} {t[2]}")
        elif t is not None and not t[2] and L.vi_awaiting(ind):
            states.append("await")
            parts.append(f"{label} empty (awaiting client)")
        else:
            states.append("bad")
            parts.append(f"{label} missing" if t is None else f"{label} = '{t[2]}'")
    state = "bad" if "bad" in states else "await" if "await" in states else "ok"
    return r(state, "Tables 032 / 049" + (" / 056" if L.tc.run.prog["mc"] else "") + " (Segment 111)",
             L.vi_line(need[0][0]), "; ".join(parts),
             "032-08 = C (I on the storing step), 049-02 = C/M" + (", 056-09 MIT/CIT category" if L.tc.run.prog["mc"] else ""))


def cd05(L):
    # an unmatched time-out reversal has no original step, so the log counts it with the purchases
    if not L.request or L.entry not in ("79", "01") or (L.follow and L.orig() is not None):
        return None
    cdb = L.v["CardDiscretionaryBlockData"]
    if TRACK.fullmatch(cdb):
        return r("bad", "CardDiscretionaryBlockData (E12) with Table 005", L.line("CardDiscretionaryBlockData"),
                 f"{cdb} with entry mode {L.entry}", f"{cdb[2:4]}{cdb[:2]} (expiry MMYY only), or entry mode 80 with the track data")
    return r("ok", "CardDiscretionaryBlockData (E12) with Table 005", L.line("CardDiscretionaryBlockData"),
             f"{cdb} with entry mode {L.entry}", "expiry MMYY only")


def cd05_alt(L):
    if L.request and not L.follow and L.entry == "80":
        return r("ok", "Table 005 / CardDiscretionaryBlockData", L.vi_line("005"),
                 f"entry mode now 80 (fallback to swipe) with {L.v['CardDiscretionaryBlockData']}", "80 + track data is the agreed fix")
    return None


def cd06(L):
    if not L.request or not L.follow or (L.entry in ("79", "01") and L.orig() is None):
        return None
    cdb = L.v["CardDiscretionaryBlockData"]
    if not cdb or tdv.PLACEHOLDER.match(cdb):
        return None
    return r("bad" if TRACK.fullmatch(cdb) else "ok", "CardDiscretionaryBlockData (E12)", L.line("CardDiscretionaryBlockData"),
             cdb, f"{cdb[:4]} (expiry YYMM only)")


def cd07(L):
    if not L.request or not tdv.PLACEHOLDER.match(L.v.get("NonfuelAmount", "")):
        return None
    o = L.orig()
    orig = o.v.get("NonfuelAmount", "") if o else ""
    seglen = L.v["SegmentLength"]
    base = tdv.seg100_length(L.v, skip=("Nonfuel Amount",))
    implied = int(seglen) - base if seglen.isdigit() else None
    prov = str(L.attr("100", "Nonfuel Amount")[0] or "")
    maxlen = len(str(int(orig))) if orig.isdigit() else 7
    bad = []
    if implied is not None and implied > maxlen:
        bad.append(f"Segment Length {seglen} only fits a {implied}-digit amount")
    if prov.isdigit() and orig.isdigit() and int(prov) > int(orig):
        bad.append(f"provisional amount {prov} is larger than the {orig} sale")
    if prov.isdigit() and len(prov) > tdv.NON_AMEX_AMOUNT_MAX and not L.tc.run.prog["amex"]:
        bad.append(f"provisional amount has {len(prov)} digits (max 7)")
    return r("bad" if bad else "ok", "SegmentLength (Segment 100) / TC provisional Nonfuel Amount", L.line("SegmentLength"),
             f"SegmentLength {seglen}; provisional amount {prov or '-'}",
             f"Segment Length for an amount of at most {maxlen} digits ({base + 1:03d}–{base + maxlen:03d}) or computed at run "
             f"time; provisional amount ≤ {orig or 'the original'}", "; ".join(bad))


def cd08(L):
    if L.role != "split_tender" or not L.s130 or not str(L.tags.get("9F02", "")).isdigit():
        return None
    o = L.orig()
    orig = o.v.get("NonfuelAmount", "") if o else ""
    if not orig.isdigit():
        return None
    a = L.tags["9F02"]
    return r("bad" if int(a) >= int(orig) else "ok", "EMVChipData Tag 9F02", L.line("EMVChipData"), f"9F02 = {a}",
             f"card B's remainder (< {int(orig):012d})")


def cd09(L):
    s = L.s102
    keys = ("ServiceLevel", "NumberofProducts", "ProductCode", "UnitofMeasure", "Quantity", "UnitPrice", "ProductAmount")
    if not s or not all(k in s for k in keys):
        return None
    sl = str(s.get("SegmentLength", ""))
    calc = 8 + sum(len(str(s[k])) for k in keys) + 3
    return r("ok" if sl == f"{calc:03d}" else "bad", "Segment 102 SegmentLength", L.line_after(SEGN["102"], "SegmentLength"), sl,
             f"{calc:03d} (12.3: FS after type/length and the last amount, '\\' after quantity and unit price)")


def cd10(L):
    lab = str(L.s105.get("CardLabel", "")) if L.totals else ""
    if not lab:
        return None
    return r("ok" if len(lab) == 4 else "bad", "CardLabel (E13)", L.line("CardLabel"), f"'{lab}'", f"'{lab.strip().ljust(4)}'")


def cd11(L):
    if not L.totals:
        return None
    lab, cnt = str(L.s105.get("CardLabel", "")), str(L.s105.get("CardTypeTotalCount", ""))
    if lab and cnt == "00000":
        return r("bad", "Card-type category (Fields 15-17)", L.line("CardTypeTotalCount"),
                 f"Card Label '{lab}', count {cnt}, amount {L.s105.get('CardTypeTotalAmount', '')}", "category not sent (no data)")
    return r("ok", "Card-type category (Fields 15-17)", L.line("CardTypeTotalCount") or L.line("GrandTotal"),
             f"Card Label '{lab}', count {cnt}" if lab else "category not sent", "category only when data occurs")


def cd12(L):
    if "EXPIRED" not in L.tc.tpl or L.step != L.tc.first or not L.request:
        return None
    cdb = L.v["CardDiscretionaryBlockData"]
    if not cdb or tdv.PLACEHOLDER.match(cdb):
        return r("await" if L.awaiting("100", "Card Discretionary Block Data", ("expired",)) else "bad", "CardDiscretionaryBlockData",
                 L.line("CardDiscretionaryBlockData"), cdb or "(empty)", f"expiry before {RUN_MONTH} (YYMM)")
    yymm = cdb[2:] + cdb[:2] if L.entry in ("01", "79") and len(cdb) == 4 else cdb[:4]
    ok = bool(re.fullmatch(r"\d{4}", yymm)) and yymm < RUN_MONTH
    chip = ""
    if L.s130:
        tvr = L.tags.get("95", "")
        bit = len(tvr) >= 4 and bool(int(tvr[2:4], 16) & 0x40)
        ok = ok and L.tags.get("5F24", "")[:4] == yymm and bit
        chip = f"; Tag 5F24 {L.tags.get('5F24', 'absent')}; TVR {tvr or '-'} (expired bit {'set' if bit else 'not set'})"
    state = "ok" if ok else "await" if L.awaiting("100", "Card Discretionary Block Data", ("expired",)) else "bad"
    return r(state, "CardDiscretionaryBlockData (expiry)", L.line("CardDiscretionaryBlockData"), f"{cdb} (expiry YYMM {yymm}){chip}",
             f"expiry before {RUN_MONTH} (YYMM)" + ("; Tag 5F24 = same expiry; TVR byte 2 bit 7 set" if L.s130 else ""))


def cvm_code(L):
    cvm = L.tags.get("9F34", "")
    return cvm, (int(cvm[:2], 16) & 0x3F if len(cvm) >= 2 else None)


def cd13(L):
    if "ONLINE-PIN" not in L.tc.tpl or L.step != L.tc.first or not L.request:
        return None
    pin = L.v["EncryptedPINBlockData"]
    cvm, code = cvm_code(L)
    if pin and not tdv.PLACEHOLDER.match(pin):
        pstate = "ok"
    elif L.awaiting("100", "Encrypted PIN Block Data", ("Encrypted PIN Block",)) or pin.startswith("<AWAITING"):
        pstate = "await"
    else:
        pstate = "bad"
    state = "bad" if code != 0x02 or pstate == "bad" else pstate
    return r(state, "EncryptedPINBlockData (E33) / Tag 9F34", L.line("EncryptedPINBlockData"), f"E33 '{pin}' / 9F34 {cvm or '-'}",
             "DUKPT KSN + PIN block / 9F34 CVM code 02 (e.g. 420300)",
             "PIN block listed in awaiting_inputs" if pstate == "await" else "")


def cd14(L):
    if "OFFLINE-PIN" not in L.tc.tpl or not L.s130:
        return None
    cvm, code = cvm_code(L)
    return r("ok" if code in CVM_OFFLINE else "bad", "EMVChipData Tag 9F34", L.line("EMVChipData"), f"9F34 = {cvm or '-'}",
             "CVM code 01 or 04 (e.g. 410302 / 440302)")


def cd15(L):
    cash = L.v.get("CashAmount", "")
    if not L.s130 or not cash.isdigit() or not int(cash):
        return None
    total = sum(int(L.v[k]) for k in ("NonfuelAmount", "CashAmount", "FuelPurchaseAmount", "TaxAmount") if L.v.get(k, "").isdigit())
    a02, a03, c9 = L.tags.get("9F02", ""), L.tags.get("9F03", ""), L.tags.get("9C", "")
    ok = a02 == f"{total:012d}" and a03 == f"{int(cash):012d}" and c9 == "09"
    return r("ok" if ok else "bad", "EMVChipData Tags 9F02 / 9F03 / 9C", L.line("EMVChipData"),
             f"9F02 {a02}, 9F03 {a03}, 9C {c9} (Cash Amount {cash})", f"9F02 {total:012d}, 9F03 {int(cash):012d}, 9C 09")


def cd16(L):
    if "TRACK1" not in L.tc.tpl or L.step != L.tc.first or not L.request:
        return None
    an, cdb = L.v["AccountNumber"], L.v["CardDiscretionaryBlockData"]
    ok = an.startswith("B") and "^" in cdb and len(an) + 1 + len(cdb) <= 76
    return r("ok" if ok else "bad", "AccountNumber / CardDiscretionaryBlockData",
             L.line("AccountNumber"), f"{an} / {cdb}", "B<PAN> / NAME^YYMM<service code><discretionary data> (Track 1, at most 76)")


def cd17(L):
    t005, t031 = L.tables.get("005"), L.tables.get("031")
    if not L.request or not t005 or not t031:
        return None
    bad = t005[2][2:3] == "8" and t031[2] in tdv.CHIP_CAPABLE_031
    return r("bad" if bad else "ok", "Tables 005 / 031", L.vi_line("031"), f"005 = {t005[2]}, 031 = {t031[2]}",
             "capability 8 only with 031 = 07 (no chip)")


def cd18(L):
    if "-AVS" not in L.tc.tpl or not L.request or L.follow:
        return None
    t = L.tables.get("003")
    if t is not None and t[2]:
        return r("ok", "Table 003 (AVS)", L.vi_line("003"), f"003 = {t[2]}", "AVS address data")
    if t is not None and L.vi_awaiting("003"):
        return r("await", "Table 003 (AVS)", L.vi_line("003"), "003 sent, value empty", "AVS address data from the client",
                 "value listed in awaiting_inputs")
    return r("bad", "Table 003 (AVS)", L.line("VariableInformationSection"), f"not sent (tables: {L.tables_text()})",
             "Table 003 with the street address / ZIP Code")


CD = {
    "CD-01": ("EMV Card Sequence Number (Element 188) is 2 bytes instead of 3", "E188, Seg 130", cd01),
    "CD-02": ("EMV Additional Information (Element 118) is not EMVYES / EMVNOT", "E191/E192/E118, Seg 130", cd02),
    "CD-03": ("Table 030 (POS Condition Code) missing on EMV and Credential-on-File requests", "E113 Table 030, Seg 111", cd03),
    "CD-04": ("Credential-on-File indicators (Tables 032, 049, 056) missing", "E113 Tables 032/049/056, Seg 111", cd04),
    "CD-05": ("Chip fallback entry mode 79 (fallback to manual) sent with swiped Track 2 data", "E12 + Table 005", cd05),
    "CD-06": ("Unmatched time-out reversal carries full track data", "E12", cd06),
    "CD-07": ("Runtime amount after a partial approval: impossible provisional value, Segment Length calculated from it",
              "E58 / E84, Seg 100", cd07),
    "CD-08": ("Split-tender card B chip amount (Tag 9F02) is the full original amount", "Tag 9F02, Seg 130", cd08),
    "CD-09": ("Product Code Data Segment (Segment 102) length 033, spec arithmetic gives 029", "E84, Seg 102", cd09),
    "CD-10": ("Totals Card Label (Element 13) is 2 bytes instead of 4", "E13, Seg 105", cd10),
    "CD-11": ("Totals card-type category sent with count 00000", "E16, Seg 105", cd11),
    "CD-12": ("Expired-card test uses a card that is not expired", "E12 (+ Tag 5F24, TVR)", cd12),
    "CD-13": ("Online-PIN test has no PIN block and a signature CVM", "E33, Tag 9F34", cd13),
    "CD-14": ("Offline-PIN test has a signature CVM", "Tag 9F34", cd14),
    "CD-15": ("Cashback test: chip data ignores the cash amount", "Tags 9C, 9F02, 9F03", cd15),
    "CD-16": ("'Track 1' test carries Track 2 data", "E2 / E12", cd16),
    "CD-17": ("Terminal described two ways: Table 005 capability 8 with Table 031 12", "Seg 111 Tables 005/031", cd17),
    "CD-18": ("AVS test sends no address data (Table 003)", "E113 Table 003, Seg 111", cd18),
}
ALT = {"CD-05": cd05_alt}


def na_note(did, L):
    """Why a rule no longer applies to the new step."""
    if did in ("CD-01", "CD-02", "CD-08", "CD-14", "CD-15") and not L.s130:
        return "Segment 130 is no longer sent on this step"
    if did == "CD-03" and not L.s130 and L.entry != "10":
        return "Segment 130 is no longer sent on this step (no longer an EMV request)"
    if did == "CD-10" and L.totals and not L.s105.get("CardLabel"):
        return "no card-type category is sent any more (nothing occurred for the card type)"
    return "the rule no longer applies to this step"


# ---------------------------------------------------------------- Interlink Debit (validator D-xx)
def ev_card(L):
    an, cdb = L.v.get("AccountNumber", ""), L.v.get("CardDiscretionaryBlockData", "")
    st = "await" if "<AWAITING" in an + cdb else "ok" if an and cdb else "bad"
    return r(st, "AccountNumber / CardDiscretionaryBlockData", L.line("AccountNumber"), f"{an or '(empty)'} / {cdb or '(empty)'}",
             "card data (or an explicit placeholder while awaiting the client card)")


def ev_pin(L):
    pin = L.v.get("EncryptedPINBlockData", "")
    st = "await" if pin.startswith("<AWAITING") else "ok" if pin else "bad"
    return r(st, "EncryptedPINBlockData (E33)", L.line("EncryptedPINBlockData"), pin or "(empty)",
             "DUKPT KSN + PIN block (or an explicit placeholder while awaiting the client key)")


def ev_t005(L):
    t = L.tables.get("005")
    return r("bad" if L.entry in ("01", "79") else "ok", "Table 005 (entry mode)", L.vi_line("005"), t[2] if t else "-",
             "a card-read entry mode (e.g. 80 for chip fallback to swipe)")


def ev_s105len(L):
    v = str(L.s105.get("SegmentLength", ""))
    head = sum(len(str(L.s105.get(k, ""))) for _, _, k, *_ in tdv.S105[:14])
    lab = str(L.s105.get("CardLabel", ""))
    calc = head + 14 + ((4 + len(str(L.s105.get("CardTypeTotalCount", ""))) + len(str(L.s105.get("CardTypeTotalAmount", ""))))
                        if lab else 0)
    return r("ok" if v == f"{calc:03d}" else "bad", "Segment 105 SegmentLength", L.line_after(SEGN["105"], "SegmentLength"),
             f"{v} (Card Label '{lab}')" if lab else f"{v} (no card-type category)", f"{calc:03d}")


def ev_totals(L):
    s = L.s105
    if s.get("CardLabel"):
        val = f"count {s.get('CardTypeTotalCount', '')}, amount {s.get('CardTypeTotalAmount', '')}, grand total {s.get('GrandTotal', '')}"
    else:
        val = f"no card-type category, grand total {s.get('GrandTotal', '')}"
    return r("", "Card Type Total Count / Amount, Grand Total", L.line("CardTypeTotalCount") or L.line("GrandTotal"), val,
             "totals of the approved steps (Section 9.2)")


def ev_field(key):
    def ev(L):
        val = L.s100.get(key, L.s105.get(key, "")) if L.s100 or L.s105 else ""
        name = TD_NAME.get(key) or next((nm for _, _, k, nm, *_ in tdv.S105 if k == key), key)
        seg = "100" if L.s100 else "105"
        tcval = L.attr(seg, name)[0]
        return r("ok" if tcval is None or str(tcval) == str(val) else "bad", key, L.line(key), f"{val} (test case: {tcval})",
                 "test data value = test case value")
    return ev


def ev_amount(L):
    return r("ok", "SegmentLength / NonfuelAmount (Segment 100)", L.line("SegmentLength"),
             f"SegmentLength {L.v.get('SegmentLength', '')}; NonfuelAmount {L.v.get('NonfuelAmount', '')}", "")


IL_EVIDENCE = {"D-01": cd01, "D-02": cd02, "D-09": cd04, "D-12": cd03, "D-14": lambda L: cd07(L) or ev_amount(L),
               "D-15": ev_card, "D-16": ev_pin, "D-17": ev_t005, "D-18": cd15, "D-19": cd10, "D-20": ev_s105len,
               "D-21": ev_totals}
IL_NA = {"D-01": "CD-01", "D-02": "CD-02", "D-12": "CD-03", "D-18": "CD-15", "D-19": "CD-10"}
IL_REVIEW = ["TOTALS_ZERO_BUCKET", "TOTALS_AO_BUCKET", "TOTALS_VERSION_PLACEHOLDER", "DEBIT_COF_PIN"]


def il_hits(tc, d):
    if not tc.rep:
        return []
    roles = d.get("roles")
    steps = {s["step"]: s["role"] for s in tc.conv["steps"]}
    return [f for f in tc.rep.findings if f["severity"] == "FAIL" and f["rule"] in d["rules"]
            and (roles is None or (f["step"] and steps.get(f["step"]) in roles))]


def field_key(f):
    key = str(f.get("field", "")).split("->")[-1].strip()
    return key if re.fullmatch(r"[A-Za-z0-9]+", key) else None


# ---------------------------------------------------------------- matching previous -> new
def match_leg(ntc, leg):
    return (next((x for x in ntc.legs if x.step == leg.step and x.role == leg.role), None)
            or next((x for x in ntc.legs if x.role == leg.role), None))


class Defect:
    """One defect of one program: previous hits, new hits and one row per previously failing test data file."""

    def __init__(self, prog, did, title, element, old, new, rows, new_only, applicable, awaiting=()):
        self.prog, self.did, self.title, self.element = prog, did, title, element
        self.old, self.new, self.rows, self.new_only, self.applicable = old, new, rows, new_only, applicable
        self.awaiting = list(awaiting)
        c = Counter(x["status"] for x in rows)
        self.counts = c
        bad_new = len(new)
        if not rows:
            self.status = "NEW IN THIS RUN"
        elif bad_new == 0 and c[NOT] == 0:
            if not (c[FIXED] or c[CHANGED] or c[AWAIT]):
                self.status = "CLOSED – test cases removed"
            elif c[AWAIT]:
                self.status = "FIXED – value awaiting client input"
            else:
                self.status = "FIXED"
        elif c[FIXED] or c[AWAIT] or c[CHANGED]:
            self.status = "PARTIALLY FIXED"
        else:
            self.status = "NOT FIXED"

    @property
    def source(self):
        return SOURCE.get(self.prog["key"], {}).get(self.did, "")

    def tcs_files(self, hits):
        return len({h[0].id for h in hits}), len({(h[0].id, h[1].step) for h in hits})


def build_rows(old_hits, newrun, evaluate):
    rows = []
    for tc, leg, res in old_hits:
        row = dict(old_tc=tc, old_leg=leg, old=res, new_tc=None, new_leg=None, new=None)
        ntc = newrun.by_key.get(tc.key)
        if ntc is None:
            row.update(status=REMOVED, note="template / entry mode no longer in the catalog")
        else:
            nleg = match_leg(ntc, leg)
            row.update(new_tc=ntc, new_leg=nleg)
            if nleg is None:
                row.update(status=CHANGED, note=f"step {leg.step} ({leg.role}) is no longer in the flow")
            else:
                state, nres, note = evaluate(ntc, nleg, res)
                row.update(new=nres, status={"bad": NOT, "ok": FIXED, "await": AWAIT}.get(state, CHANGED), note=note)
        rows.append(row)
    return rows


def rules_defects(prog, old, new):
    out = []
    for did, (title, element, fn) in CD.items():
        oh = [(tc, L, x) for tc in old.tcs for L in tc.legs for x in [fn(L)] if x and x["state"] == "bad"]
        nh = [(tc, L, x) for tc in new.tcs for L in tc.legs for x in [fn(L)] if x and x["state"] == "bad"]
        applicable = sum(1 for tc in new.tcs for L in tc.legs if fn(L))
        if not oh and not nh:
            continue

        def evaluate(ntc, nleg, old_res=None, fn=fn, did=did):
            x = fn(nleg)
            if x is None and did in ALT:
                x = ALT[did](nleg)
            if x is None:
                return "changed", r("", "-", None, na_note(did, nleg)), na_note(did, nleg)
            return x["state"], x, x.get("note", "")
        rows = build_rows(oh, new, evaluate)
        mapped = {(rw["new_tc"].id, rw["new_leg"].step) for rw in rows if rw["new_leg"]}
        new_only = [h for h in nh if (h[0].id, h[1].step) not in mapped]
        aw = [(tc, L, x) for tc in new.tcs for L in tc.legs for x in [fn(L)] if x and x["state"] == "await"]
        out.append(Defect(prog, did, title, element, oh, nh, rows, new_only, applicable, aw))
    return out


def validator_defects(prog, old, new):
    out = []
    for d in bld.DEFECTS:
        did = d["id"]

        def hits(run, d=d, did=did):
            res = []
            for tc in run.tcs:
                for f in il_hits(tc, d):
                    leg = next((x for x in tc.legs if x.step == f["step"]), None)
                    if leg is None:
                        continue
                    ev = IL_EVIDENCE.get(did) or ev_field(field_key(f) or "")
                    x = ev(leg) or r("bad", f["label"], None, f["value"])
                    res.append((tc, leg, dict(x, state="bad", value=x["value"], why=f["why"], fix=f["fix"], fkey=field_key(f),
                                              expected=f["fix"] or x.get("expected", ""))))
            return res
        oh, nh = hits(old), hits(new)
        if not oh and not nh:
            continue
        uniq_old = list({(tc.id, L.step): (tc, L, x) for tc, L, x in oh}.values())
        bad_new = {(tc.id, L.step) for tc, L, _ in nh}

        def evaluate(ntc, nleg, old_res=None, did=did):
            ev = IL_EVIDENCE.get(did) or (ev_field(old_res["fkey"]) if old_res and old_res.get("fkey") else None)
            x = ev(nleg) if ev else None
            if (ntc.id, nleg.step) in bad_new:
                y = next(z for t, L, z in nh if t.id == ntc.id and L.step == nleg.step)
                return "bad", y, ""
            if x is None:
                if did in IL_NA:
                    note = na_note(IL_NA[did], nleg)
                    return "changed", r("", "-", None, note), note
                return "ok", r("ok", "-", None, "no finding on this step"), ""
            return ("await" if x["state"] == "await" else "ok"), x, x.get("note", "")
        rows = build_rows(uniq_old, new, evaluate)
        mapped = {(rw["new_tc"].id, rw["new_leg"].step) for rw in rows if rw["new_leg"]}
        nh_u = list({(tc.id, L.step): (tc, L, x) for tc, L, x in nh}.values())
        new_only = [h for h in nh_u if (h[0].id, h[1].step) not in mapped]
        title = d["title"]
        ev = IL_EVIDENCE.get(did) if did in ("D-12", "D-15", "D-16") else None
        aw = [(tc, L, x) for tc in new.tcs for L in tc.legs for x in [ev(L) if ev else None] if x and x["state"] == "await"]
        out.append(Defect(prog, did, title, bld.DEFECT_DOC.get(did, {}).get("field", ""), uniq_old, nh_u, rows, new_only,
                          sum(1 for tc in new.tcs for _ in tc.legs), aw))
    return out


# ---------------------------------------------------------------- package checks and review items
def walk(o):
    if isinstance(o, dict):
        yield o
        for v in o.values():
            yield from walk(v)
    elif isinstance(o, list):
        for v in o:
            yield from walk(v)


def trace_check(base):
    p = base / "reports" / "traceability_matrix_full.json"
    if not p.exists():
        return None
    raw = p.read_text(encoding="utf-8")
    data = json.loads(raw)
    rows = data.get("flat_rows") or []
    delivered = {f.name for f in (base / "test_data").glob("*.json")}
    paths = set(re.findall(r'"([^"]*TD-TC-[^"]*\.json)"', raw))
    entries = [d for d in walk(data) if isinstance(d.get("test_data_file"), str) and d["test_data_file"]]
    missing = [d for d in entries if not (base / d["test_data_file"]).exists()]
    claimed = [d for d in missing if d.get("file_exists") is True]
    flat_missing = [x for x in rows if x.get("test_data_file") and not (base / x["test_data_file"]).exists()]
    referenced = {Path(x).name for x in paths}
    untraced = sorted(delivered - referenced)
    flagged_false = [d for d in walk(data) if d.get("file_exists") is False]
    return dict(rows=len(rows), with_td=len(entries), missing=len(flat_missing), claimed=len(claimed), entries_missing=len(missing),
                delivered=len(delivered), untraced=len(untraced), untraced_ex=untraced[:3],
                folders=sorted({str(Path(x).parent) for x in paths}),
                false_rows=[(d.get("test_case_id") or d.get("id") or "?", d.get("test_data_file") or "(no test data)")
                            for d in flagged_false],
                missing_ex=[x["test_data_file"] for x in flat_missing[:2]])


def package_check(base, run):
    zips = []
    for z in sorted(base.glob("*.zip")):
        with zipfile.ZipFile(z) as zf:
            names = [n for n in zf.namelist() if n.endswith("test_cases.jsonl")]
            count = sum(1 for n in names for line in zf.read(n).decode("utf-8", "replace").splitlines() if line.strip())
        zips.append((z.name, count))
    man = base / "MANIFEST.json"
    manifest = json.loads(man.read_text(encoding="utf-8")) if man.exists() else None
    return dict(zips=zips, readme=(base / "README.md").exists(), manifest=manifest, tcs=len(run.tcs), files=len(run.files),
                builds=Counter(tc.build for tc in run.tcs), bad_lines=run.bad_lines)


def rv01(L):
    if not L.request or not L.follow:
        return None
    o = L.orig()
    while o is not None and o.follow and o.of:
        o = o.orig()
    if not o or o.follow or o.entry not in ("01", "10"):
        return None
    oc, fc = o.v["CardDiscretionaryBlockData"], L.v["CardDiscretionaryBlockData"]
    if not re.fullmatch(r"\d{4}", oc):
        return None
    return "YYMM" if fc == oc[2:] + oc[:2] and fc != oc else "same MMYY" if fc == oc else f"other ({fc})"


def review_items(prog, old, new):
    items = []
    if prog["engine"] == "rules":
        co = Counter(x for tc in old.tcs for L in tc.legs for x in [rv01(L)] if x)
        cn = Counter(x for tc in new.tcs for L in tc.legs for x in [rv01(L)] if x)
        ex = next(((tc, L, L.line("CardDiscretionaryBlockData")) for tc in new.tcs for L in tc.legs if rv01(L) == "YYMM"), None)
        items.append(dict(id="RV-01", title="Keyed / Credential-on-File follow-on expiry sent as YYMM while the original is MMYY",
                          old=dict(co), new=dict(cn), example=ex,
                          result=("Unchanged – follow-ons still send YYMM (still a review item for BUYPASS / SME)" if cn.get("YYMM")
                                  else "Changed – follow-ons no longer convert MMYY to YYMM" if cn else "No keyed/COF follow-ons")))
    if prog["key"] == "mastercard_fleet":
        co = Counter(str(L.s101.get("SegmentLength")) for tc in old.tcs for L in tc.legs if L.s101)
        cn = Counter(str(L.s101.get("SegmentLength")) for tc in new.tcs for L in tc.legs if L.s101)
        ex = next(((tc, L, L.line_after(SEGN["101"], "SegmentLength")) for tc in new.tcs for L in tc.legs if L.s101), None)
        items.append(dict(id="RV-02", title="Fleet Data Segment (Segment 101) length 035 (021 / 030 / 035 depend on the spec reading)",
                          old=dict(co), new=dict(cn), example=ex,
                          result="Unchanged – still a review item for SME / BUYPASS" if cn == co or cn.get("035") else "Changed"))
    return items


def flow_totals(run):
    """Totals Request vs flow (Section 9.2) for programs checked with the CD rules: validator run on the T8 test cases."""
    return [(tc, f) for tc in run.tcs if tc.rep for f in tc.rep.findings if f["rule"] == "TOTALS_FLOW_MISMATCH"]


# ---------------------------------------------------------------- report writer
class Writer:
    def __init__(self, out, old_base, new_base):
        self.out, self.old_base, self.new_base = out, old_base, new_base

    def new_ref(self, prog, leg, line):
        if leg is None:
            return "–"
        rel = Path("..") / self.new_base.relative_to(ROOT) if self.new_base.is_relative_to(ROOT) else self.new_base
        url = f"{rel.as_posix()}/{prog['key']}/test_data/{leg.name}".replace(" ", "%20")
        return f"[{leg.name}]({url}#L{line}) line {line}" if line else f"[{leg.name}]({url})"

    def old_ref(self, prog, leg, line):
        if leg is None:
            return "–"
        if prog["old_link"]:
            return f"[{leg.name}]({prog['old_link']}{leg.name}#L{line}) line {line}" if line else f"[{leg.name}]({prog['old_link']}{leg.name})"
        return f"`{leg.name}` line {line}" if line else f"`{leg.name}`"

    @staticmethod
    def changed(dfx):
        """Most common new values of the previously failing files (what the generator does now)."""
        vals = Counter(rw["new"]["value"] for rw in dfx.rows if rw.get("new") and rw["status"] in (FIXED, AWAIT, CHANGED))
        return "; ".join(f"`{md(short(v, 70))}` ({pl(k, 'file')})" for v, k in vals.most_common(3))

    def example(self, dfx):
        """(previous row, new row) to check by hand: a still-failing row first, else a fixed one."""
        rows = sorted(dfx.rows, key=lambda x: [NOT, AWAIT, FIXED, CHANGED, REMOVED].index(x["status"]))
        return rows[0] if rows else None

    def check_text(self, dfx, rw):
        prog = dfx.prog
        if rw is None:
            return "–"
        o = rw["old"]
        s = (f"Previous: {self.old_ref(prog, rw['old_leg'], o['line'])} → `{md(short(o['value'], 50))}`. ")
        if rw["new_leg"] is not None and rw["new"]:
            n = rw["new"]
            s += f"New ({rw['new_tc'].id}): {self.new_ref(prog, rw['new_leg'], n['line'])} → `{md(short(n['value'], 50))}`"
        else:
            s += f"New: {rw.get('note', '–')}"
        return s

    def program(self, prog, old, new, defects, extra, pkg, trace, reviews, flows):
        L = [f"# {prog['name']} – defect fix verification (previous run 31-10-out → new run 5-11-out)", "",
             "| Item | Value |", "|---|---|",
             f"| Previous run | `{(self.old_base / prog['key']).as_posix()}` – {len(old.tcs)} test cases, {len(old.files)} test data files"
             + (f" (identical copy in the workspace: `{prog['old_link'].replace('%20', ' ').strip('./').replace('/test_data/', '')}`)"
                if prog["old_link"] else "") + " |",
             f"| New run | `5-11-out/{prog['key']}` – {len(new.tcs)} test cases, {len(new.files)} test data files |",
             f"| Defect log(s) | {prog['logs'] if prog.get('missed') else '`Defect MD file/` ' + prog['logs']} |",
             "| Test case matching | IDs are renumbered in the new run; test cases are matched by transaction category + entry mode + "
             "template (unique in both runs), flow steps by step number and role. "
             f"{sum(1 for k in old.by_key if k in new.by_key)} of {len(old.tcs)} previous test cases have a match; "
             f"{sum(1 for k in old.by_key if k not in new.by_key)} were removed, {sum(1 for k in new.by_key if k not in old.by_key)} "
             "are new |", "",
             "Status: **FIXED** – every previously failing file now passes the rule · **FIXED – value awaiting client input** – "
             "the generator now builds the field correctly, but its value can only come from the client (listed in the test "
             "case's `awaiting_inputs`, status `needs_client_input`) · **PARTIALLY FIXED** – some files pass, some still fail · "
             "**NOT FIXED** – still failing · **CLOSED – test cases removed** – the affected test cases are no longer in the "
             "catalog.", "",
             "## 1. Summary", "",
             "| Defect | Source ID | What was wrong | Previous run: TCs / files (logged) | New run: TCs / files still failing | "
             "Status |", "|---|---|---|---|---|---|"]
        for dfx in defects:
            logged = LOGGED.get(dfx.did, {}).get(prog["key"])
            ot, of = dfx.tcs_files(dfx.old)
            nt, nf = dfx.tcs_files(dfx.new)
            L.append(f"| [{dfx.did}](#{dfx.did.lower()}) | {dfx.source or '–'} | {md(dfx.title)} | {ot} / {of}"
                     + (f" ({logged[0]} / {logged[1]})" if logged else not_logged(prog)) + f" | {nt} / {nf} | **{dfx.status}** |")
        for p in extra:
            L.append(f"| [{p['id']}](#{p['id'].lower()}) | {p['source'] or '–'} | {md(p['title'])} | {p['old_text']} | {p['new_text']} | "
                     f"**{p['status']}** |")
        L += ["", "## 2. Defect details", ""]
        for dfx in defects:
            L += self.defect_md(dfx)
        for p in extra:
            L += [f'<a id="{p["id"].lower()}"></a>', "", f"### {p['id']} – {p['title']} – {p['status']}", ""] + p["detail"] + ["", "---", ""]
        if reviews:
            L += ["## 3. Review items of the previous log (not defects)", "", "| ID | Item | Previous run | New run | Result |",
                  "|---|---|---|---|---|"]
            for x in reviews:
                ex = x["example"]
                L.append(f"| {x['id']} | {md(x['title'])} | {', '.join(f'{k}: {v}' for k, v in x['old'].items()) or '–'} | "
                         f"{', '.join(f'{k}: {v}' for k, v in x['new'].items()) or '–'} | {x['result']}"
                         + (f" – e.g. {self.new_ref(prog, ex[1], ex[2])}" if ex else "") + " |")
            L += [""]
        if flows is not None:
            fo, fn = flows
            L += ["## 4. Seen while re-checking (not in the previous log)", "",
                  "The Totals Request of two settlement flows does not match the transactions before it (Section 9.2, p.72; the "
                  "same rule is defect D-21 of the Interlink Debit report). It was not in the previous log for this program.", "",
                  f"Previous run: {pl(len({tc.id for tc, _ in fo}), 'test case')}; new run: {pl(len({tc.id for tc, _ in fn}), 'test case')}.",
                  "", "| New TC | Template | Totals file | Actual | Expected |", "|---|---|---|---|---|"]
            for tc, f in fn:
                leg = next((x for x in tc.legs if x.step == f["step"]), None)
                L.append(f"| {tc.id} | {tc.tpl} | {self.new_ref(prog, leg, leg.line('CardTypeTotalCount') if leg else None)} | "
                         f"{md(f['value'])} | {md(f['fix'])} |")
            L += [""] + self.totals_examples(prog, [(tc, next((x for x in tc.legs if x.step == f["step"]), None)) for tc, f in fn])
        L += ["Re-run (from `regression-artifact-validator`): `python scripts/verify-defect-fixes.py`", ""]
        (self.out / f"{prog['file']}_Fix_Verification.md").write_text("\n".join(L) + "\n", encoding="utf-8")

    def totals_examples(self, prog, pairs):
        """Worked examples of a wrong Totals Request: one per template."""
        L, done = [], set()
        for tc, leg in pairs:
            if tc is None or leg is None or tc.tpl in done or not tc.ctx:
                continue
            done.add(tc.tpl)
            L += self.totals_story(prog, tc, leg)
        return (["#### Explained step by step (new run)", ""] + L) if L else []

    def totals_story(self, prog, tc, leg):
        exp = tdv.expected_totals(tc.conv, tc.ctx, leg.step)
        if not exp:
            return []
        cnt, amt, _, story = exp
        s, sent = leg.s105, (cnt, amt) != (0, 0)
        before = [x for x in tc.legs if x.step < leg.step and not x.totals]
        idx = [x.step for x in tc.legs].index(leg.step)
        label = str(s.get("CardLabel", ""))
        bucket = label.strip() or tdv.profile(tc.conv)["label"]
        head = sum(len(str(s.get(k, ""))) for _, _, k, *_ in tdv.S105[:14])
        L = [f"**{tc.id} – {tc.tpl} ({bld.ENTRY_MODES.get(tc.mode, (None, tc.mode))[1]})**, `test_cases.jsonl` line {tc.line}", "",
             "| Step | Test data file : line | Message | Amount | The test case expects | Effect on the device totals (Section 9.2) |",
             "|---|---|---|---|---|---|"]
        for x, text in zip(before, story):
            e = tc.expect.get(x.step, {})
            resp = f"Response Code `{e['rc']}`" if e.get("rc") else "no response (time-out)" if e.get("none") else "not stated"
            L.append(f"| {x.step} | {self.new_ref(prog, x, x.line('NonfuelAmount'))} | {x.role.replace('_', ' ')} (Transaction Type "
                     f"`{x.v.get('PromptCode', '')[:1]}`) | `{x.v.get('NonfuelAmount', '')}` = {tdv.amount(x.v.get('NonfuelAmount', ''))} | "
                     f"{resp} | {md(text.split(': ', 1)[-1])} |")
        L += ["", f"So the {bucket} bucket holds **count {cnt}, amount {tdv.amount(str(max(amt, 0)))}**"
              + (" – nothing occurred for the card type, so Fields 15–17 are not sent (p.237)." if not sent else "."), "",
              f"Totals Request {self.new_ref(prog, leg, None)}:", "",
              "| Field | Line | Actual (file) | Expected | Result |", "|---|---|---|---|---|"]
        fields = [("CardLabel", "Card Label (Element 13)", f"'{bucket.ljust(4)}'" if sent else "not sent"),
                  ("CardTypeTotalCount", "Card Type Total Count (Element 16)", f"{cnt:05d}" if sent else "not sent"),
                  ("CardTypeTotalAmount", "Card Type Total Amount (Element 15)", f"{max(amt, 0):08d}" if sent else "not sent"),
                  ("GrandTotal", "Grand Total (Element 42)", f"{max(amt, 0):08d}"),
                  ("SegmentLength", "Segment Length (Element 84)", f"{head + 14 + (17 if sent else 0):03d}")]
        for key, name, want in fields:
            have = str(s[key]) if key in s else "not sent"
            shown = f"'{have}'" if key == "CardLabel" and key in s else have
            L.append(f"| {name} | {leg.line(key) or '–'} | `{shown}` | `{want}` | {'OK' if shown == want else '**WRONG**'} |")
        note = tc.expect.get(leg.step, {}).get("note")
        src = [(k, tc.attrs.get((leg.step, SEGN["105"], k), (None, None))) for k in
               ("Grand Total", "Card Type Total Count", "Card Type Total Amount")]
        L += [""] + ([f"The test case itself says for this step: *\"{md(note)}\"* – the generated values contradict it.", ""]
                     if note else []) + [
            f"Where the wrong values come from: `test_cases.jsonl` line {tc.line} → `steps[{idx}].request` → `\"Totals Data Segment\"` → "
            + ", ".join(f"`\"{k}\"` = `{v}` (`method: {m}`)" for k, (v, m) in src)
            + ". The generator copied a fixed example for this template instead of computing the totals from the steps "
            "(the settlement templates that pass use `method: spec_rule_value`).", ""]
        return L

    def defect_md(self, dfx, cap=25):
        prog = dfx.prog
        ot, of = dfx.tcs_files(dfx.old)
        nt, nf = dfx.tcs_files(dfx.new)
        logged = LOGGED.get(dfx.did, {}).get(prog["key"])
        c = dfx.counts
        L = [f'<a id="{dfx.did.lower()}"></a>', "", f"### {dfx.did} – {dfx.title} – {dfx.status}", "",
             "| | |", "|---|---|",
             f"| Field | {md(dfx.element)}" + (f" (source ID {dfx.source})" if dfx.source else "") + " |",
             f"| Previous run | {pl(ot, 'test case')} / {pl(of, 'file')} failing"
             + (f" (log: {logged[0]} / {logged[1]})" if logged else not_logged(prog)) + " |",
             f"| Previously failing files in the new run | " + ", ".join(f"{c[k]} {k.lower()}" for k in (FIXED, AWAIT, NOT, CHANGED, REMOVED)
                                                                 if c[k]) + " |",
             f"| New run, all test cases | {pl(nt, 'test case')} / {pl(nf, 'file')} still failing"
             + (f" ({len(dfx.new_only)} of them in test cases or steps that had no finding before)" if dfx.new_only else "") + " |"]
        ch = self.changed(dfx)
        if ch:
            L.append(f"| What the new files contain | {ch} |")
        notes = Counter(rw.get("note") for rw in dfx.rows if rw.get("note") and rw["status"] != REMOVED)
        if notes:
            L.append("| Notes | " + "; ".join(f"{md(k)} ({v})" for k, v in notes.most_common(3)) + " |")
        modes = Counter(bld.ENTRY_MODES.get(rw["old_tc"].mode, (None, rw["old_tc"].mode))[1] for rw in dfx.rows if rw["status"] == REMOVED)
        if modes:
            L.append(f"| Not in the new catalog | {pl(c[REMOVED], 'file')} of test cases that were removed: "
                     + ", ".join(f"{m} ({k})" for m, k in modes.most_common()) + " |")
        rw = self.example(dfx)
        exp = (rw["new"] or rw["old"]).get("expected", "") if rw else ""
        L += [f"| Check it yourself | {self.check_text(dfx, rw)}" + (f"<br>Expected: {md(exp)}" if exp else "") + " |", ""]
        if dfx.did == "D-21":
            L += self.totals_examples(prog, [(x["new_tc"], x["new_leg"]) for x in dfx.rows if x["status"] == NOT])
        order = [NOT, AWAIT, CHANGED, FIXED, REMOVED]
        rows = sorted(dfx.rows, key=lambda x: (order.index(x["status"]), x["old_tc"].id, x["old_leg"].step))
        L += ["| # | Previous TC (step) | Previous file : line | Previous value | New TC | New file : line | New value | Status |",
              "|---|---|---|---|---|---|---|---|"]
        for i, x in enumerate(rows[:cap], 1):
            n = x.get("new")
            L.append(f"| {i} | {x['old_tc'].id} ({x['old_leg'].step} {x['old_leg'].role.replace('_', ' ')}) | "
                     f"{self.old_ref(prog, x['old_leg'], x['old']['line'])} | `{md(short(x['old']['value'], 60))}` | "
                     f"{x['new_tc'].id if x['new_tc'] else '–'} | {self.new_ref(prog, x['new_leg'], n['line'] if n else None)} | "
                     + (f"`{md(short(n['value'], 60))}`" if n else md(x.get("note", "–"))) + f" | {x['status']} |")
        if len(rows) > cap:
            L.append(f"\n… {len(rows) - cap} more rows: sheet *Rows* of `Defect_Fix_Verification.xlsx` (filter Program / Defect).")
        for tc, leg, x in dfx.new_only[:5]:
            L.append(f"\nAlso failing in the new run: {tc.id} ({tc.tpl}) step {leg.step} – {self.new_ref(prog, leg, x['line'])} "
                     f"`{md(short(x['value'], 45))}`")
        return L + ["", "---", ""]


def sheet_book(path, sheets):
    from openpyxl import Workbook
    from openpyxl.cell.cell import ILLEGAL_CHARACTERS_RE
    from openpyxl.styles import Alignment, Font, PatternFill
    from openpyxl.utils import get_column_letter
    wb = Workbook()
    for i, (title, cols, rows) in enumerate(sheets):
        ws = wb.active if i == 0 else wb.create_sheet()
        ws.title = title
        ws.append([c for c, _ in cols])
        for row in rows:
            ws.append([v if isinstance(v, (int, float)) else ILLEGAL_CHARACTERS_RE.sub("", str(v if v is not None else ""))[:32000]
                       for v in row])
        for cell in ws[1]:
            cell.font, cell.fill = Font(bold=True, color="FFFFFF"), PatternFill("solid", fgColor="1F4E78")
            cell.alignment = Alignment(wrap_text=True, vertical="center")
        for j, (_, width) in enumerate(cols, 1):
            ws.column_dimensions[get_column_letter(j)].width = width
        for row in ws.iter_rows(min_row=2):
            for cell in row:
                cell.alignment = Alignment(wrap_text=True, vertical="top")
                if cell.data_type == "f":  # a value starting with '=' is data, never a formula
                    cell.data_type = "s"
        ws.freeze_panes = "A2"
        if rows:
            ws.auto_filter.ref = ws.dimensions
    wb.save(path)


# ---------------------------------------------------------------- main
def main():
    sys.stdout.reconfigure(encoding="utf-8")
    ap = argparse.ArgumentParser(description=__doc__.splitlines()[0])
    ap.add_argument("--old", default=str(OLD_DEFAULT), help="previous AI delivery (folder with the program folders)")
    ap.add_argument("--new", default=str(NEW_DEFAULT), help="new AI delivery")
    ap.add_argument("--out", default=str(OUT_DEFAULT))
    a = ap.parse_args()
    old_base, new_base, out = Path(a.old), Path(a.new).resolve(), Path(a.out)
    out.mkdir(parents=True, exist_ok=True)

    spec = tdv.Spec(Path(tcv.DEFAULT_SPEC))
    prior = tdv.Spec(Path(tcv.PRIOR_SPEC)) if Path(tcv.PRIOR_SPEC).exists() else None
    states = spec.state_codes()
    refs = tdv.Refs(spec, prior, states)
    ref_json = json.loads(tcv.ELEMENT_REF.read_text(encoding="utf-8"))
    ref = {e["element"]: e for e in ref_json["elements"]}
    qv = tcv.Validator(spec.sq, {}, ref_json)
    vargs = (ref, refs, qv, states)

    w = Writer(out, old_base, new_base)
    register, all_rows, still, mapping, packages, seen, reviews_all, facts = [], [], [], [], [], [], [], {}
    totals_methods = Counter()
    for prog in PROGRAMS:
        if prog["engine"] == "validator":
            pick = (lambda rec: True, vargs)
        else:
            pick = (lambda rec: str(rec.get("template_id", "")).startswith("T8-"), vargs)
        old, new = Run("previous", old_base, prog, pick), Run("new", new_base, prog, pick)
        defects = validator_defects(prog, old, new) if prog["engine"] == "validator" else rules_defects(prog, old, new)
        for k in sorted(set(old.by_key) | set(new.by_key), key=lambda k: tuple(map(str, k))):
            o, n = old.by_key.get(k), new.by_key.get(k)
            mapping.append((prog["name"], *k, o.id if o else "", n.id if n else "",
                            "matched" if o and n else "removed in the new run" if o else "new in the new run"))

        to, tn = trace_check(old_base / prog["key"]), trace_check(new_base / prog["key"])
        extra = []
        if to and tn:
            fixed = tn["entries_missing"] == 0 and tn["untraced"] == 0
            detail = ["| Check | Previous run | New run |", "|---|---|---|",
                      f"| Trace rows (`reports/traceability_matrix_full.json` → `flat_rows`) | {to['rows']} | {tn['rows']} |",
                      f"| Folders the test data paths point to | {', '.join(f'`{x}/`' for x in to['folders'])} | "
                      f"{', '.join(f'`{x}/`' for x in tn['folders'])} |",
                      f"| `flat_rows` whose test data path does not exist | {to['missing']} | {tn['missing']} |",
                      f"| Trace entries (all levels) with a missing path / of them marked `file_exists: true` | "
                      f"{to['entries_missing']} / {to['claimed']} | {tn['entries_missing']} / {tn['claimed']} |",
                      f"| Delivered test data files never referenced by the trace | {to['untraced']} of {to['delivered']} | "
                      f"{tn['untraced']} of {tn['delivered']} |",
                      f"| Entries with `file_exists: false` | {len(to['false_rows'])} | {len(tn['false_rows'])}"
                      + (" – " + ", ".join(f"{t} ({f})" for t, f in tn["false_rows"][:10]) if tn["false_rows"] else "") + " |",
                      "", f"Check it yourself: open `5-11-out/{prog['key']}/reports/traceability_matrix_full.json` and search "
                      "`\"test_data_file\"` – the paths now start with `test_data/` (previously `qe_shaped_test_data/`, a folder "
                      "that was never delivered), and every leg file of a flow is listed."]
            extra.append(dict(id="CD-19", source=SOURCE.get(prog["key"], {}).get("CD-19", ""),
                              title="Traceability matrix points to a folder that is not delivered; follow-on legs never traced",
                              old_text=f"{to['missing']} trace rows with a missing path; {to['untraced']} files untraced",
                              new_text=f"{tn['missing']} trace rows with a missing path; {tn['untraced']} files untraced",
                              status="FIXED" if fixed else "PARTIALLY FIXED" if tn["entries_missing"] < to["entries_missing"]
                              else "NOT FIXED", detail=detail))
            packages.append((prog["name"], "CD-19", f"{to['missing']} trace rows with missing path; {to['untraced']} of "
                             f"{to['delivered']} files untraced", f"{tn['missing']} trace rows with missing path; {tn['untraced']} of "
                             f"{tn['delivered']} files untraced; {len(tn['false_rows'])} entries file_exists=false", extra[-1]["status"]))
        if prog["key"] == "mastercard_credit":
            po, pn = package_check(old_base / prog["key"], old), package_check(new_base / prog["key"], new)
            ok = not pn["zips"] and not pn["readme"] and len(pn["builds"]) == 1 and pn["manifest"] and \
                pn["manifest"].get("test_cases") == pn["tcs"] and pn["manifest"].get("test_data_files") == pn["files"]
            detail = ["| Check | Previous run | New run |", "|---|---|---|",
                      f"| `test_cases.jsonl` test cases / `test_data` files | {po['tcs']} / {po['files']} | {pn['tcs']} / {pn['files']} |",
                      f"| Embedded archives (test cases inside) | {', '.join(f'`{z}` ({k})' for z, k in po['zips']) or 'none'} | "
                      f"{', '.join(f'`{z}` ({k})' for z, k in pn['zips']) or 'none'} |",
                      f"| `README.md` of an older build | {'present' if po['readme'] else 'none'} | {'present' if pn['readme'] else 'none'} |",
                      f"| Build IDs in the test cases | {', '.join(f'`{b}` ({k})' for b, k in po['builds'].items())} | "
                      f"{', '.join(f'`{b}` ({k})' for b, k in pn['builds'].items())} |",
                      f"| `MANIFEST.json` | {'present' if po['manifest'] else 'none'} | "
                      + (f"single_build = {pn['manifest'].get('single_build')}, test_cases = {pn['manifest'].get('test_cases')}, "
                         f"test_data_files = {pn['manifest'].get('test_data_files')}, foreign_archives = "
                         f"{pn['manifest'].get('foreign_archives')}" if pn["manifest"] else "none") + " |",
                      f"| Test case lines that are not valid JSON | {len(po['bad_lines'])} | {len(pn['bad_lines'])} |"]
            extra.append(dict(id="CD-20", source=SOURCE[prog["key"]].get("CD-20", ""), title="MC Credit package mixes two builds",
                              old_text=f"{po['tcs']} TCs + archive with {po['zips'][0][1] if po['zips'] else '?'} TCs",
                              new_text=f"{pn['tcs']} TCs, one build, MANIFEST", status="FIXED" if ok else "NOT FIXED", detail=detail))
            packages.append((prog["name"], "CD-20", extra[-1]["old_text"], extra[-1]["new_text"], extra[-1]["status"]))
        reviews = review_items(prog, old, new)
        if reviews:
            reviews_all.append((prog, reviews))
        if prog.get("missed"):
            facts[prog["key"]] = {lbl: dict(aid=Counter(L.tags.get("84", "-") for tc in run.tcs for L in tc.legs if L.s130),
                                            card=Counter(L.v["PromptCode"][1:] for tc in run.tcs for L in tc.legs if L.request),
                                            label=Counter(str(L.s105.get("CardLabel", "")) for tc in run.tcs for L in tc.legs
                                                          if L.totals and L.s105.get("CardLabel")))
                                   for lbl, run in (("previous", old), ("new", new))}
        flows = (flow_totals(old), flow_totals(new)) if prog["engine"] == "rules" else None
        if flows:
            seen.append((prog, flows))
        failing = ({(tc.id, f["step"]) for tc, f in flows[1]} if flows else
                   {(tc.id, L.step) for d in defects if d.did == "D-21" for tc, L, _ in d.new})
        totals_methods.update(("failing" if (tc.id, L.step) in failing else "passing",
                               tc.attrs.get((L.step, SEGN["105"], "Grand Total"), (None, None))[1] or "-")
                              for tc in new.tcs for L in tc.legs if L.totals)
        w.program(prog, old, new, defects, extra, None, None, reviews, flows)

        for dfx in defects:
            ot, of = dfx.tcs_files(dfx.old)
            nt, nf = dfx.tcs_files(dfx.new)
            logged = LOGGED.get(dfx.did, {}).get(prog["key"])
            if logged and logged != (ot, of):
                print(f"  ! {prog['name']} {dfx.did}: previous run re-check {ot}/{of} differs from the log {logged[0]}/{logged[1]}")
            ex = w.example(dfx)
            register.append(dict(prog=prog, dfx=dfx, old=(ot, of), logged=logged, new=(nt, nf), example=ex))
            for x in dfx.rows:
                n = x.get("new") or {}
                all_rows.append((prog["name"], dfx.did, dfx.source, x["status"], x["old_tc"].id, x["old_tc"].disp, x["old_tc"].tpl,
                                 x["old_tc"].cat, x["old_tc"].mode, x["old_leg"].step, x["old_leg"].role, x["old_leg"].name,
                                 x["old"]["line"] or "", x["old"]["value"], x["new_tc"].id if x["new_tc"] else "",
                                 x["new_leg"].name if x["new_leg"] else "", n.get("line") or "", n.get("value", ""),
                                 (n or x["old"]).get("expected", ""), x.get("note", "")))
            for tc, leg, x in dfx.new:
                still.append((prog["name"], dfx.did, tc.id, tc.disp, tc.tpl, leg.step, leg.role, leg.name, x["line"] or "", x["value"],
                              x.get("expected", ""), "yes" if any(rw["new_leg"] is leg for rw in dfx.rows) else "no"))
        for p in extra:
            register.append(dict(prog=prog, extra=p))
        print(f"{prog['name']}: previous {len(old.tcs)} TCs / {len(old.files)} files, new {len(new.tcs)} / {len(new.files)}")
        for dfx in defects:
            print(f"  {dfx.did:6} {dfx.status:36} prev {dfx.tcs_files(dfx.old)} new {dfx.tcs_files(dfx.new)} rows {dict(dfx.counts)}")
        for p in extra:
            print(f"  {p['id']:6} {p['status']:36} {p['old_text']} -> {p['new_text']}")
        for x in reviews:
            print(f"  {x['id']:6} {x['result']} {x['old']} -> {x['new']}")
        if flows:
            print(f"  totals flow mismatch (not in the log): previous {len({tc.id for tc, _ in flows[0]})}, new {len({tc.id for tc, _ in flows[1]})}")

    write_readme(out, register)
    write_consolidated(out, w, register, seen, reviews_all, facts, totals_methods)
    sheet_book(out / "Defect_Fix_Verification.xlsx", [
        ("Register", [("Program", 20), ("Defect", 8), ("Source ID", 12), ("What was wrong", 50), ("Field", 26), ("Prev TCs", 9),
                      ("Prev files", 9), ("Logged TCs / files", 12), ("New TCs still failing", 11), ("New files still failing", 11),
                      ("Status", 22), ("What the new files contain", 60)],
         [(x["prog"]["name"], x["dfx"].did, x["dfx"].source, x["dfx"].title, x["dfx"].element, x["old"][0], x["old"][1],
           f"{x['logged'][0]} / {x['logged'][1]}" if x["logged"] else "", x["new"][0], x["new"][1], x["dfx"].status,
           Writer.changed(x["dfx"]).replace("`", "")) if "dfx" in x else
          (x["prog"]["name"], x["extra"]["id"], x["extra"]["source"], x["extra"]["title"], "package", "", "", "", "", "",
           x["extra"]["status"], f"{x['extra']['old_text']} -> {x['extra']['new_text']}") for x in register]),
        ("Rows", [("Program", 18), ("Defect", 8), ("Source ID", 10), ("Status", 14), ("Prev TC", 11), ("Prev display ID", 16),
                  ("Template", 26), ("Category", 14), ("Entry mode", 18), ("Step", 6), ("Role", 14), ("Prev file", 36), ("Prev line", 8),
                  ("Prev value", 40), ("New TC", 11), ("New file", 36), ("New line", 8), ("New value", 40), ("Expected", 40),
                  ("Note", 40)], all_rows),
        ("Still failing (new run)", [("Program", 18), ("Defect", 8), ("New TC", 11), ("Display ID", 16), ("Template", 26),
                                     ("Step", 6), ("Role", 14), ("File", 36), ("Line", 8), ("Value", 40), ("Expected", 40),
                                     ("Failed before (same file)", 12)], still),
        ("Package", [("Program", 20), ("Defect", 8), ("Previous run", 60), ("New run", 60), ("Status", 18)], packages),
        ("TC mapping", [("Program", 20), ("Category", 16), ("Entry mode", 20), ("Template", 30), ("Previous TC", 12), ("New TC", 12),
                        ("Match", 22)], mapping)])
    print(f"Written: {out}")


CONSOLIDATED = "Consolidated_Report_All_Programs.md"
MAESTRO_REPORT = "../maestro_direct_debit/Manual%20Validation%20Report/"
# fields the generator now sends empty / as <AWAITING:...> because only the client can supply the value
AWAIT_FIELD = {"CD-03": "Table 030 POS Condition Code (10 digits, Appendix Q) of the certified terminal",
               "D-12": "Table 030 POS Condition Code (10 digits, Appendix Q) of the certified terminal",
               "CD-04": "Table 056 sub-table 09 MIT/CIT category of the Credential-on-File use case (MasterCard)",
               "CD-13": "Element 33 PIN block of the online-PIN test card (DUKPT key)",
               "CD-18": "Table 003 AVS address / ZIP Code of the AVS test card",
               "D-15": "Debit card data: Account Number and Element 12 of the client's debit test card",
               "D-16": "Element 33 debit PIN block (client's test PIN and DUKPT key)"}


def programs_of(register):
    progs = []
    for x in register:
        if x["prog"] not in progs:
            progs.append(x["prog"])
    return progs


def status_counts(register, prog):
    return Counter((x["dfx"].status if "dfx" in x else x["extra"]["status"]) for x in register if x["prog"] is prog)


def status_table(register, link_reports=True):
    L = ["| Program | Defects re-checked | FIXED | FIXED – awaiting client input | NOT FIXED | CLOSED – test cases removed | "
         "Report |", "|---|---|---|---|---|---|---|"]
    for prog in programs_of(register):
        st = status_counts(register, prog)
        nf = st["NOT FIXED"] + st["PARTIALLY FIXED"] + st["NEW IN THIS RUN"]
        L.append(f"| {prog['name']}" + (" (missed in the previous logs)" if prog.get("missed") else "") + f" | {sum(st.values())} | "
                 f"{st['FIXED']} | {st['FIXED – value awaiting client input']} | {'**' + str(nf) + '**' if nf else 0} | "
                 f"{st['CLOSED – test cases removed']} | [{prog['file']}_Fix_Verification.md](./{prog['file']}_Fix_Verification.md) |")
    return L


def write_readme(out, register):
    L = ["# Defect fix verification – previous AI run (31-10-out) vs new AI run (5-11-out)", "",
         f"**Start here:** [{CONSOLIDATED}](./{CONSOLIDATED}) – one report for all five programs: what is fixed, what is still open, "
         "what waits for client input, the Maestro Direct Debit defects that the previous logs missed, and the full register.", "",
         "## Result by program", ""] + status_table(register) + [
         "", "## Files", "",
         f"- [{CONSOLIDATED}](./{CONSOLIDATED}) – consolidated report, all programs.",
         "- `<Program>_Fix_Verification.md` – per program: every defect with its previous and new counts, what the new files "
         "contain, a worked example (previous file and line → new file and line) and every previously failing file.",
         "- `Defect_Fix_Verification.xlsx` – *Register*, *Rows* (every previously failing file → new file and line), *Still failing "
         "(new run)*, *Package*, *TC mapping* (previous TC ID → new TC ID).",
         f"- [Maestro Direct Debit validation of the previous run]({MAESTRO_REPORT}README.md) – the full ATL105 report (per "
         "scenario and per test case) that found the Maestro defects; its defect list: "
         f"[Consolidated_Defect_Report.md]({MAESTRO_REPORT}Consolidated_Defect_Report.md).", "",
         "Re-run (from `regression-artifact-validator`):", "", "```powershell", "$env:PYTHONUTF8 = '1'",
         "python scripts/verify-defect-fixes.py", "```", ""]
    (out / "README.md").write_text("\n".join(L) + "\n", encoding="utf-8")


def write_consolidated(out, w, register, seen, reviews_all, facts, totals_methods):
    progs = programs_of(register)
    defects = [x for x in register if "dfx" in x]
    total = Counter((x["dfx"].status if "dfx" in x else x["extra"]["status"]) for x in register)
    still = [x for x in defects if x["dfx"].status in ("NOT FIXED", "PARTIALLY FIXED", "NEW IN THIS RUN")]
    flow_tcs = sum(len({tc.id for tc, _ in fn}) for _, (_, fn) in seen) + sum(x["new"][0] for x in still if x["dfx"].did == "D-21")
    maestro = next((p for p in progs if p.get("missed")), None)
    L = ["# ATL105 AI Test Data – Consolidated Defect Report, all programs (previous run 31-10-out → new run 5-11-out)", "",
         "| Item | Value |", "|---|---|",
         "| Programs | " + ", ".join(p["name"] for p in progs) + " |",
         f"| Previous run | `{w.old_base.as_posix()}` |",
         "| New run | `5-11-out` |",
         "| Previous defect logs | `Defect MD file/` – Consolidated_Defect_Log_AMEX-Credit_MC-Credit_MC-Fleet.md (CD-xx) with "
         "AMEX_Credit_Defect_Log.md (A-xx), MasterCard_Credit_Consolidated_Defect_Report.md (D-xx), "
         "MasterCard_Fleet_Consolidated_Defects.md (FL-xx); Interlink_Debit_Consolidated_Defect_Report.md (D-xx). "
         + (f"**{maestro['name']} had no log** – its defects were found now (§4)." if maestro else "") + " |",
         "| Specification | BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026) |",
         "| Method | Every defect rule was run on every test data file of both runs; the previous-run counts reproduce the logs. "
         "Test case IDs are renumbered in the new run, so every previously failing file was traced to its new file by "
         "transaction category + entry mode + template and step. |",
         f"| Date | {tdv.TODAY.isoformat()} |",
         f"| Overall result | {sum(total.values())} defect entries re-checked: **{total['FIXED']} fixed**, "
         f"**{total['FIXED – value awaiting client input']} fixed – value awaiting client input**, "
         f"**{total['NOT FIXED'] + total['PARTIALLY FIXED'] + total['NEW IN THIS RUN']} not fixed**, "
         f"{total['CLOSED – test cases removed']} closed (test cases removed). One generator rule is still wrong in every "
         f"program: the settlement totals ({flow_tcs} test cases, §2). |", "",
         "Status: **FIXED** – every previously failing file now passes · **FIXED – value awaiting client input** – the generator "
         "builds the field correctly, but the value can only come from the client (listed in the test case's `awaiting_inputs`, "
         "status `needs_client_input`) · **NOT FIXED** · **CLOSED – test cases removed** – the affected test cases are no longer "
         "in the catalog.", "", "---", "", "## 1. Status by program", ""] + status_table(register) + ["", "---", "",
         "## 2. Still open – the generator must change", "",
         "One rule fails in all five programs: the **Totals Request does not match the transactions of its flow** (ATL105 "
         "Section 9.2, p.72: a Purchase/Capture increases the count and amount, a Reversal decreases both, an Authorization Only "
         "goes to the AO bucket, which the grand total leaves out). Two settlement flows are affected in every program:", "",
         "- *Purchase timed out → Time-out Reversal → Settlement*: the purchase never got a response, so nothing is in the "
         "totals, but the file sends the card bucket with count `00001` and amount `00000000`. Expected: no card-type "
         "category, Grand Total `00000000`.",
         "- *Authorization Only → Completion → Settlement*: the completion captured $15.00, but the file sends amount and grand "
         "total `00000000`. Expected: count `00001`, amount `00001500`, grand total `00001500`.", "",
         "**Root cause:** how the test cases produce Grand Total, Card Type Total Count and Card Type Total Amount "
         "(`test_cases.jsonl` → the totals step → `\"Totals Data Segment\"` → `method`), over all Totals Requests of the new "
         "run: failing " + ", ".join(f"`{m}` {k}" for (g, m), k in sorted(totals_methods.items()) if g == "failing")
         + "; passing " + ", ".join(f"`{m}` {k}" for (g, m), k in sorted(totals_methods.items()) if g == "passing")
         + ". For these two templates the generator copies a fixed example instead of computing the totals from the steps "
         "of the flow, as it already does for every other settlement template. The test cases even describe the right result "
         "in their own notes (*\"the timed-out purchase is not in the totals\"*; *\"the completion is in the totals; the "
         "authorization only is not\"*). Worked examples with every step, file and line: D-21 in the Interlink and Maestro "
         "reports, §4 in the other program reports.", "",
         "| Program | Logged as | Previous run TCs | New run TCs | Check it yourself (new run) |", "|---|---|---|---|---|"]
    for x in still:
        dfx = x["dfx"]
        L.append(f"| {x['prog']['name']} | [{dfx.did}](./{x['prog']['file']}_Fix_Verification.md#{dfx.did.lower()}) "
                 f"{dfx.status}" + (" (missed in the previous logs, found now)" if x["prog"].get("missed") else "")
                 + f" | {x['old'][0]} | {x['new'][0]} | {w.check_text(dfx, x['example'])} |")
    for prog, (fo, fn) in seen:
        ex = next(((tc, f) for tc, f in fn), None)
        leg = next((y for y in ex[0].legs if y.step == ex[1]["step"]), None) if ex else None
        L.append(f"| {prog['name']} | not in the previous log (found now) | {len({tc.id for tc, _ in fo})} | "
                 f"{len({tc.id for tc, _ in fn})} | "
                 + (f"{ex[0].id} {ex[0].tpl}: {w.new_ref(prog, leg, leg.line('CardTypeTotalCount') if leg else None)} → "
                    f"`{md(ex[1]['value'])}`; expected: {md(ex[1]['fix'])}" if ex else "–") + " |")
    L += ["", "Every affected file: sheet *Still failing (new run)* of `Defect_Fix_Verification.xlsx` and §4 of each program "
          "report.", "", "---", "",
          "## 3. Waiting for client input – generator fixed, value still to be supplied", "",
          "The new run builds these fields and lists them in each test case's `awaiting_inputs` (test case status "
          "`needs_client_input`). The files cannot be sent until the client supplies the value; this is the fix the previous logs "
          "asked for. Note: Table 030 is sent with length `010` and an empty value – the length is for the value to come.", "",
          "| Value awaited from the client | Program (defect): new-run files waiting | Example (new run) |", "|---|---|---|"]
    groups = {}
    for x in defects:
        dfx = x["dfx"]
        if dfx.did in AWAIT_FIELD and dfx.awaiting:
            groups.setdefault(AWAIT_FIELD[dfx.did], []).append(x)
    for field, xs in groups.items():
        tc, leg, res = xs[0]["dfx"].awaiting[0]
        L.append(f"| {field} | " + "<br>".join(f"{y['prog']['name']} ({y['dfx'].did}): {pl(len(y['dfx'].awaiting), 'file')}"
                                               for y in xs)
                 + f" | {xs[0]['prog']['name']} {tc.id}: {w.new_ref(xs[0]['prog'], leg, res['line'])} → `{md(short(res['value'], 50))}` |")
    L += ["", "---", ""]
    if maestro:
        mx = [x for x in defects if x["prog"] is maestro]
        f = facts.get(maestro["key"], {})
        old_n, new_n = f.get("previous", {}), f.get("new", {})
        L += ["## 4. Maestro Direct Debit – the defects missed in the previous logs", "",
              "Maestro Direct Debit was part of the previous delivery (`31-10-out/maestro_direct_debit`, 278 test cases, 536 test "
              "data files), but no defect log covered it – it appears only in the counts of CD-01 and CD-19 of the consolidated "
              "log. It has now been validated field by field with the same ATL105 rules as Interlink Debit (both are PIN debit "
              "programs with the same templates), in the previous run and in the new run.", "",
              f"- Full validation of the previous run (per scenario, per test case, every finding with its ATL105 text): "
              f"[Maestro Manual Validation Report]({MAESTRO_REPORT}README.md); defect list: "
              f"[Consolidated_Defect_Report.md]({MAESTRO_REPORT}Consolidated_Defect_Report.md) (same D-IDs as Interlink).",
              f"- Previous run vs new run: [{maestro['file']}_Fix_Verification.md](./{maestro['file']}_Fix_Verification.md).", "",
              "| Defect | What was wrong (previous run) | Previous run TCs / files | New run TCs / files failing | Status in the new run |",
              "|---|---|---|---|---|"]
        for x in mx:
            dfx = x["dfx"]
            L.append(f"| [{dfx.did}]({MAESTRO_REPORT}Consolidated_Defect_Report.md#{dfx.did.lower()}) | {md(dfx.title)} | "
                     f"{x['old'][0]} / {x['old'][1]} | {x['new'][0]} / {x['new'][1]} | "
                     f"[{dfx.status}](./{maestro['file']}_Fix_Verification.md#{dfx.did.lower()}) |")
        for x in register:
            if x["prog"] is maestro and "extra" in x:
                p = x["extra"]
                L.append(f"| {p['id']} | {md(p['title'])} | {p['old_text']} | {p['new_text']} | {p['status']} |")
        aid = lambda c: ", ".join(f"`{k}` ({v} files)" for k, v in c.items()) or "–"  # noqa: E731
        L += ["", "Maestro-specific checks that pass in both runs: card type in the Prompt Code "
              f"{aid(new_n.get('card', {}))} = Appendix E `011` Debit checking; chip application identifier (Tags 84 / 9F06) "
              f"{aid(new_n.get('aid', {}))} = the Maestro application (previous run: {aid(old_n.get('aid', {}))}); totals card "
              f"bucket {aid(new_n.get('label', {}))} = Element 13 `DB` Debit; ATL105 lists Maestro as a supported EMV debit card "
              "(Section 10.14.2.3, p.161) and as authorizer 40 *Maestro® Direct Debit* (Appendix C, p.512).", "", "---", ""]
    L += ["## 5. Full defect register (all programs)", "",
          "| Program | Defect | Source ID | What was wrong | Previous run TCs / files (log) | New run TCs / files failing | Status |",
          "|---|---|---|---|---|---|---|"]
    for x in register:
        prog = x["prog"]
        if "dfx" in x:
            dfx = x["dfx"]
            L.append(f"| {prog['name']} | [{dfx.did}](./{prog['file']}_Fix_Verification.md#{dfx.did.lower()}) | "
                     f"{dfx.source or '–'} | {md(dfx.title)} | {x['old'][0]} / {x['old'][1]}"
                     + (f" ({x['logged'][0]} / {x['logged'][1]})" if x["logged"] else " (missed)" if prog.get("missed") else "")
                     + f" | {x['new'][0]} / {x['new'][1]} | **{dfx.status}** |")
        else:
            p = x["extra"]
            L.append(f"| {prog['name']} | [{p['id']}](./{prog['file']}_Fix_Verification.md#{p['id'].lower()}) | "
                     f"{p['source'] or '–'} | {md(p['title'])} | {p['old_text']} | {p['new_text']} | **{p['status']}** |")
    L += ["", "---", "", "## 6. Review items of the previous logs (not defects)", "",
          "| Program | ID | Item | Previous run | New run | Result |", "|---|---|---|---|---|---|"]
    for prog, items in reviews_all:
        for y in items:
            L.append(f"| {prog['name']} | {y['id']} | {md(y['title'])} | {', '.join(f'{k}: {v}' for k, v in y['old'].items()) or '–'} | "
                     f"{', '.join(f'{k}: {v}' for k, v in y['new'].items()) or '–'} | {y['result']} |")
    L += ["", "The other review items of the consolidated log (RV-03 – RV-22: framing of Element 118, placeholder terminal, sample "
          "CA checksum and cryptograms, citations, contactless chip data, …) are questions for BUYPASS / SME or the client and "
          "were not re-checked.", "", "---", "",
          "## 7. How to check any result by hand", "",
          "1. Open the program report linked in §1 and go to the defect (the links in §5 jump there).",
          "2. Its *Check it yourself* row gives the previous file and line and the new file and line with the value found; the "
          "table below it lists every previously failing file with its new file, line, value and status.",
          "3. New-run files link directly into `5-11-out/<program>/test_data/`. Previous-run files of MasterCard Credit, "
          "Interlink Debit and Maestro Direct Debit link to identical workspace copies (`MasterCard Credit/mastercard_credit/`, "
          "`interlink_debit/`, `maestro_direct_debit/`); AMEX Credit and MasterCard Fleet previous files are in "
          f"`{w.old_base.as_posix()}`.",
          "4. Test case IDs changed between the runs: the *TC mapping* sheet of `Defect_Fix_Verification.xlsx` gives the new ID of "
          "every previous test case.", "",
          "Re-run (from `regression-artifact-validator`):", "", "```powershell", "$env:PYTHONUTF8 = '1'",
          "python scripts/verify-defect-fixes.py",
          "python scripts/build-entry-mode-validation-report.py --data maestro_direct_debit --all   # Maestro previous-run report",
          "```", ""]
    (out / CONSOLIDATED).write_text("\n".join(L) + "\n", encoding="utf-8")


if __name__ == "__main__":
    main()
