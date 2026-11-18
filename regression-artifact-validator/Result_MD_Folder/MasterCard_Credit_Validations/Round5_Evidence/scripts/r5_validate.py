"""Round 5 catalog-wide ATL105 validation of the MasterCard Credit delivery (plan v12).

Check IDs follow the Round 4 scripted validator so results are comparable. Severity:
DEFECT = breaks an ATL105 rule or the TC cannot prove its objective; REVIEW = SME/convention question;
NEG = the intended mutation of a negative test (informational).
"""
import csv
import json
import os
import re
import sys
from collections import Counter, defaultdict

ROOT = r"C:\Users\F5QGZOJ\Downloads\mastercard_credit_output_1611\mastercard_credit_new"
HERE = os.path.dirname(os.path.abspath(__file__))
PKG_DIRS = {
    "network": ROOT,
    "data_element": os.path.join(ROOT, "data_element_validation"),
    "operations": os.path.join(ROOT, "terminal_and_host_operations"),
}
RUN_YYMM = "2611"

SEG_NO = {
    "Standard Message Data Segment": "100", "Fleet Data Segment": "101", "Product Code Data Segment": "102",
    "Purchase Card Data Segment": "104", "Totals Data Segment": "105", "Loyalty Card Data Segment": "108",
    "ECA/ TeleCheck Data Segment Format ®": "113", "Variable Information Data Segment": "111",
    "SKU Data Segment": "114", "NFC Payment Tokenization Data Segment": "123", "EMV Request Data Segment": "130",
    "Enhanced Fleet Request": "145", "Network Token Data Request": "153",
}
FINANCIAL = {"Financial Transaction Request", "EMV Financial Transaction Request"}
SECTION3_ALLOWED = {"101", "102", "103", "104", "111", "123", "135", "143", "145", "146", "151", "152", "153", "130"}
FLEET_ONLY = {"101", "145"}
SEG100_ORDER = ["SegmentType", "SegmentLength", "InformationByte", "TerminalIdentifier", "PromptCode", "AccountNumber",
                "CardDiscretionaryBlockData", "EncryptedPINBlockData", "PumpLaneNumber", "FuelPurchaseAmount",
                "NonfuelAmount", "TaxAmount", "CashAmount", "SequenceNumber", "ApprovalNumber",
                "LocalDateandLocalTime", "PartialApprovalIndicator"]
ENTRY_OF_DIM = {
    "EMV_CONTACT": {"05", "95"}, "EMV_CONTACTLESS": {"07", "86"}, "MAGSTRIPE": {"02", "90"},
    "CONTACTLESS_MAGSTRIPE": {"91"}, "MANUAL": {"01"}, "EMV_FALLBACK": {"79", "80"},
    "CREDENTIAL_ON_FILE": {"10"}, "CONTACTLESS_MOBILE": {"82"},
}
CHIP_READ = {"05", "07", "95", "86"}
KEYED = {"01", "10", "79"}
TRACK_READ = {"02", "90", "91", "80", "05", "07", "95", "86", "82"}
FOLLOW_ROLES = {"reversal", "timeout_reversal", "cancellation", "void_return", "moto_reversal", "completion"}
UNATTENDED_CTX = {"PETROLEUM-UNATTENDED", "MCC_5542", "GENERAL_RETAIL-UNATTENDED", "HOSPITALITY-UNATTENDED",
                  "SUPERMARKET-UNATTENDED", "UTILITIES-UNATTENDED"}
CTX_T030 = {
    "CARD_NOT_PRESENT-0100": "0100100004", "CARD_NOT_PRESENT-2100": "0102100004",
    "PETROLEUM-UNATTENDED": "1000000007", "MCC_5542": "1000000007",
    "GENERAL_RETAIL-UNATTENDED": "1000000001", "HOSPITALITY-UNATTENDED": "1000000001",
    "SUPERMARKET-UNATTENDED": "1000000001", "UTILITIES-UNATTENDED": "1000000001",
}
T031_CODES = {"00", "01", "02", "03", "04", "05", "06", "07", "09", "12"}
TOKEN_RE = re.compile(r"<[^<>]+>")
AWAIT_RE = re.compile(r"<AWAITING:[^<>]*>")


def is_token(v):
    return isinstance(v, str) and bool(TOKEN_RE.fullmatch(v.strip())) if v else False


def luhn(pan):
    s = 0
    for i, ch in enumerate(reversed(pan)):
        d = int(ch)
        if i % 2:
            d *= 2
            if d > 9:
                d -= 9
        s += d
    return s % 10 == 0


def mc_iin(pan):
    return bool(re.match(r"5[1-5]", pan)) or (len(pan) >= 4 and 2221 <= int(pan[:4]) <= 2720)


def parse_tlv(hexstr):
    """Parse BER-TLV (hex). Returns dict tag->value hex, or None if malformed."""
    out = {}
    i = 0
    h = hexstr.upper()
    n = len(h)
    try:
        while i < n:
            tag = h[i:i + 2]
            i += 2
            if int(tag, 16) & 0x1F == 0x1F:
                while True:
                    b = h[i:i + 2]
                    tag += b
                    i += 2
                    if not int(b, 16) & 0x80:
                        break
            lb = int(h[i:i + 2], 16)
            i += 2
            if lb & 0x80:
                nb = lb & 0x7F
                lb = int(h[i:i + 2 * nb], 16)
                i += 2 * nb
            val = h[i:i + 2 * lb]
            if len(val) != 2 * lb:
                return None
            i += 2 * lb
            out[tag] = val
        return out
    except Exception:
        return None


class Finder:
    def __init__(self):
        self.rows = []

    def add(self, pkg, check, sev, tc, leg, detail, element=""):
        self.rows.append({
            "package": pkg, "check_id": check, "severity": sev, "test_case": tc["id"],
            "display_id": tc.get("display_id") or "", "test_type": tc.get("test_type"), "polarity": tc.get("polarity"),
            "status": tc.get("status"), "context": tc.get("acceptance_context") or "",
            "txn": (tc.get("dimensions") or {}).get("txn_category", ""),
            "entry_mode": (tc.get("dimensions") or {}).get("entry_mode", ""),
            "test_data_file": leg or "", "element": element, "detail": detail,
        })


def norm(s):
    return re.sub(r"[^a-z0-9]", "", (s or "").lower())


def target_element(tc):
    """Element(s) the negative test is meant to break (normalized)."""
    names = set()
    v = tc.get("violated_element_name")
    if v:
        names.add(norm(v))
    wm = tc.get("wire_mutation")
    if isinstance(wm, dict):
        for k in ("element", "element_name", "field"):
            if wm.get(k):
                names.add(norm(str(wm[k])))
    return names


ELEMENT_ALIASES = {
    "segmentlength": {"segmentlength", "segmentlengthindicator"},
    "cardiscretionaryblockdata": {"carddiscretionaryblockdata", "expirationdate"},
}


TXN_LEVEL_CHECKS = {"S111-T005", "MSG-03", "S100-TT-LEG", "S100-TT-CAT", "S111-T030-CTX", "S100-APPR", "S100-LDT"}


def sev_for(tc, element_norms, default="DEFECT", check=None):
    """NEG if this negative test targets this element (by violated element or by name); else default (collateral)."""
    if tc.get("polarity") in ("Negative",) or tc.get("is_negative"):
        tgt = target_element(tc)
        name = norm(tc.get("name"))
        for e in element_norms:
            if e in tgt or any(e and (e in t or t in e) for t in tgt if len(t) > 6):
                return "NEG"
            for k, al in ELEMENT_ALIASES.items():
                if e in al and tgt & al:
                    return "NEG"
            if e and len(e) > 8 and e in name:
                return "NEG"
        if not tgt and check in TXN_LEVEL_CHECKS and tc.get("test_type") in (
                "invalid_transition", "single_rule_broken", "idempotency_retry", "ordering", "time_based", "fault_recovery"):
            return "NEG"
    return default


def leg_role_from_file(fn):
    m = re.match(r"TD-TC-\d+\.step(\d+)\.([a-z_\-]+)\.json$", os.path.basename(fn))
    if m:
        return int(m.group(1)), m.group(2).replace("-", "_")
    return 1, None


def first_msg(d):
    for k, v in d.items():
        if isinstance(v, dict):
            return k, v
    return None, None


def tables_of(msg):
    out = []
    for sec in (msg.get("Variable Information Data Segment") or {}).get("VariableInformationSection", []) or []:
        out.append((sec.get("VariableInformationIndicator"), sec.get("VariableInformationLength"),
                    sec.get("VariableInformation")))
    return out


def s100_amount_total(s):
    tot = 0
    any_amt = False
    for k in ("FuelPurchaseAmount", "NonfuelAmount", "TaxAmount", "CashAmount"):
        v = s.get(k) or ""
        if re.fullmatch(r"\d+", v):
            tot += int(v)
            any_amt = True
    return tot, any_amt


# ------------------------------------------------------------------------------------------------ leg checks

def check_seg100(F, pkg, tc, legf, s, ctx):
    pc = s.get("PromptCode")
    if isinstance(pc, dict):
        tt, ct = pc.get("TransactionType", ""), pc.get("CardType", "")
    else:
        tt, ct = (pc or "")[:1], (pc or "")[1:]
    ctx["tt"] = tt
    vals = {k: (s.get(k) if not isinstance(s.get(k), dict) else (tt + ct)) for k in SEG100_ORDER}
    # E84 segment length
    last = max((i for i, k in enumerate(SEG100_ORDER) if vals.get(k)), default=-1)
    comp = 0
    runtime = []
    for i in range(last + 1):
        v = vals.get(SEG100_ORDER[i]) or ""
        if AWAIT_RE.fullmatch(v.strip()):
            w = 0
        elif is_token(v):
            if "ApprovalNumber" in v or SEG100_ORDER[i] == "ApprovalNumber":
                w = 6
            else:
                runtime.append(SEG100_ORDER[i])
                w = 0
        else:
            w = len(v)
        comp += w + 1
    decl = s.get("SegmentLength") or ""
    comp_full = sum(len((vals.get(k) or "") if not is_token(vals.get(k) or "") else "") + 1 for k in SEG100_ORDER)
    if runtime:
        if re.fullmatch(r"\d{3}", decl):
            implied = int(decl) - comp
            F.add(pkg, "S100-LEN-RT", "REVIEW", tc, legf,
                  f"SegmentLength={decl} precomputed with runtime token(s) {runtime}; implies runtime width {implied}"
                  " - contract does not say lengths are recomputed after substitution (CD-07)", "Segment Length")
    elif re.fullmatch(r"\d{3}", decl) and int(decl) == comp_full and comp_full != comp:
        # trailing empty field(s) still delimited: correct for 'sent empty', wrong for 'omitted'
        if tc.get("test_type") == "N-C1" or "omitted" in (tc.get("name") or ""):
            F.add(pkg, "TC-NC1-NOT-OMITTED", "DEFECT", tc, legf,
                  f"'{tc.get('violated_element_name')}' is claimed omitted but its Field Separator is still sent (SegmentLength {decl}); the wire is identical to the 'sent empty' (N-C4) test",
                  "Segment Length")
    elif not re.fullmatch(r"\d{3}", decl) or int(decl) != comp:
        F.add(pkg, "S100-LEN", sev_for(tc, {"segmentlength"}), tc, legf,
              f"SegmentLength={decl} computed={comp}", "Segment Length")
    ib = s.get("InformationByte")
    if ib not in ("0", "1", "?"):
        F.add(pkg, "S100-IB", sev_for(tc, {"informationbyte"}), tc, legf, f"InformationByte={ib!r} (E44: ?,0,1)",
              "Information Byte")
    tid = s.get("TerminalIdentifier") or ""
    ctx["tid"] = tid
    if not re.fullmatch(r"[A-Za-z0-9+*]{13,22}", tid):
        F.add(pkg, "S100-TID", sev_for(tc, {"terminalidentifier"}), tc, legf, f"TerminalIdentifier={tid!r}",
              "Terminal Identifier")
    if ct != "020":
        F.add(pkg, "S100-PC", sev_for(tc, {"promptcode", "cardtype"}), tc, legf, f"CardType={ct!r} (expected 020)",
              "Prompt Code")
    exp_tt = ctx.get("leg_code")
    if exp_tt and tt != exp_tt:
        F.add(pkg, "S100-TT-LEG", sev_for(tc, {"promptcode", "transactiontype"}, check="S100-TT-LEG"), tc, legf,
              f"TransactionType={tt!r} but TC leg ({ctx.get('role')}) expects {exp_tt!r}", "Prompt Code")
    actx = tc.get("acceptance_context") or ""
    rc_class = next((rc.get("allowed") for rc in tc.get("rule_checks") or [] if rc.get("id") == "CTX-TERMINAL-CLASS"), None)
    ctx_mismatch = bool(rc_class) and ((actx in UNATTENDED_CTX) != ("100" in rc_class or "101" in rc_class))
    if pkg == "network" and ctx_mismatch and not ctx.get("ctx_mismatch_reported"):
        ctx["ctx_mismatch_reported"] = True
        F.add(pkg, "TC-CTX-MISMATCH", "DEFECT", tc, legf,
              f"TC filed under context {actx} ({tc.get('context_label')}) but its own CTX rule check and TD use terminal class {rc_class} / TT {tt} (merchant {(tc.get('merchant') or {}).get('name')})",
              "Acceptance context")
    elif pkg == "network" and tt in ("0", "3") and actx in UNATTENDED_CTX:
        F.add(pkg, "S100-TT-CAT", sev_for(tc, {"promptcode", "transactiontype"}, check="S100-TT-CAT"), tc, legf,
              f"TT {tt} (attended POS) in unattended context {actx}; App. G: 4/5 = customer-activated", "Prompt Code")
    elif pkg == "network" and tt in ("4", "5") and actx and actx not in UNATTENDED_CTX:
        F.add(pkg, "S100-TT-CAT", sev_for(tc, {"promptcode", "transactiontype"}, check="S100-TT-CAT"), tc, legf,
              f"TT {tt} (customer-activated) in attended context {actx}", "Prompt Code")
    ctx["ctx_mismatch"] = ctx_mismatch
    pan = s.get("AccountNumber") or ""
    ctx["pan"] = pan
    if AWAIT_RE.fullmatch(pan):
        pass
    elif pan == "TOTALS REQUEST":
        F.add(pkg, "S100-PAN", sev_for(tc, {"accountnumber"}), tc, legf,
              "AccountNumber='TOTALS REQUEST' (loyalty totals literal, E2 p.366) on a financial request", "Account Number")
    elif re.fullmatch(r"B\d{12,19}", pan) and "^" in (s.get("CardDiscretionaryBlockData") or ""):
        pass  # Track 1 split: format code + PAN before the first '^' (E2 p.365)
    elif not re.fullmatch(r"\d{12,19}", pan):
        F.add(pkg, "S100-PAN", sev_for(tc, {"accountnumber"}), tc, legf, f"AccountNumber={pan!r} not 12-19 digits",
              "Account Number")
    else:
        if not luhn(pan):
            F.add(pkg, "S100-PAN", sev_for(tc, {"accountnumber"}), tc, legf, f"PAN {pan} fails Luhn", "Account Number")
        if pkg == "network" and not mc_iin(pan):
            F.add(pkg, "S100-PAN", sev_for(tc, {"accountnumber"}), tc, legf, f"PAN {pan} not a MasterCard IIN",
                  "Account Number")
    # E12
    cd = s.get("CardDiscretionaryBlockData") or ""
    ctx["cdbd"] = cd
    role = ctx.get("role")
    entry = ctx.get("entry")
    cdsev = sev_for(tc, {"carddiscretionaryblockdata"})
    if role in FOLLOW_ROLES:
        if len(cd) > 4:
            F.add(pkg, "S100-CDBD", cdsev, tc, legf,
                  f"follow-on ({role}) carries {len(cd)}-char card data {cd[:12]}...; p.94-95: PAN + YYMM only",
                  "Card Discretionary Block Data")
        elif cd and not re.fullmatch(r"\d{2}(0[1-9]|1[0-2])", cd):
            F.add(pkg, "S100-CDBD", "REVIEW", tc, legf, f"follow-on expiry {cd} not a valid YYMM (RV-01 convention)",
                  "Card Discretionary Block Data")
    elif entry in KEYED:
        if not re.fullmatch(r"(0[1-9]|1[0-2])\d{2}", cd):
            F.add(pkg, "S100-CDBD", cdsev, tc, legf, f"keyed/COF entry {entry}: E12 {cd!r} not MMYY (E12 p.372)",
                  "Card Discretionary Block Data")
        elif tc.get("polarity") == "Positive" and cd[2:] + cd[:2] < RUN_YYMM:
            F.add(pkg, "S100-EXPIRED", "REVIEW", tc, legf, f"expiry {cd} (MMYY) already expired vs {RUN_YYMM}",
                  "Card Discretionary Block Data")
    elif entry in TRACK_READ:
        if "^" in cd:
            pass  # Track 1
        elif re.fullmatch(r"\d{4}", cd):
            sev = "REVIEW" if entry == "02" else cdsev
            F.add(pkg, "S100-CDBD", sev, tc, legf,
                  f"read entry {entry}: E12 is expiry-only {cd!r} - a chip/track read sends Track-2 data (YYMM+service code+discretionary, E2/E12 p.364-371)",
                  "Card Discretionary Block Data")
        elif not re.fullmatch(r"\d{7,}", cd):
            F.add(pkg, "S100-CDBD", cdsev, tc, legf, f"track data {cd!r} not Track-2 format", "Card Discretionary Block Data")
        else:
            yymm = cd[:4]
            if not re.fullmatch(r"\d{2}(0[1-9]|1[0-2])", yymm):
                F.add(pkg, "S100-CDBD", cdsev, tc, legf, f"Track-2 expiry {yymm} invalid month", "Card Discretionary Block Data")
            elif tc.get("polarity") == "Positive" and yymm < RUN_YYMM:
                F.add(pkg, "S100-EXPIRED", "REVIEW", tc, legf, f"Track-2 expiry {yymm} expired vs {RUN_YYMM}",
                      "Card Discretionary Block Data")
            svc = cd[4:7]
            if entry in CHIP_READ | {"80"} and svc[:1] not in ("2", "6"):
                F.add(pkg, "S100-CDBD", cdsev, tc, legf, f"service code {svc} on chip entry {entry} (chip card = 2xx/6xx)",
                      "Card Discretionary Block Data")
            if re.fullmatch(r"\d+", pan) and len(pan) + 1 + len(cd) > 37:
                F.add(pkg, "S100-CDBD", cdsev, tc, legf, f"PAN+=+CDBD = {len(pan) + 1 + len(cd)} > 37 (Track 2)",
                      "Card Discretionary Block Data")
    # E33
    pin = s.get("EncryptedPINBlockData") or ""
    ctx["pin"] = pin
    if pin and not AWAIT_RE.fullmatch(pin) and not re.fullmatch(r"[0-9A-Fa-f]{32,36}", pin):
        F.add(pkg, "S100-PIN", sev_for(tc, {"encryptedpinblockdata"}), tc, legf,
              f"EncryptedPINBlockData={pin!r} (E33: KSN 16-20 + 16-byte PIN block)", "Encrypted PIN Block Data")
    # amounts
    for k, el in (("FuelPurchaseAmount", "fuelpurchaseamount"), ("NonfuelAmount", "nonfuelamount"),
                  ("TaxAmount", "taxamount"), ("CashAmount", "cashamount")):
        v = s.get(k) or ""
        if not v or is_token(v):
            continue
        if not re.fullmatch(r"\d{1,7}", v) or int(v) == 0:
            cid = "S100-AMT-ZERO" if re.fullmatch(r"0+", v) and k == "NonfuelAmount" and (s.get("FuelPurchaseAmount") or "") else "S100-AMT"
            F.add(pkg, cid, sev_for(tc, {el}), tc, legf,
                  f"{k}={v!r} (E58/E41/E99/E17 valid 1-99999999, max 7 digits for MasterCard; omit the field when there is no such amount)", k)
    tot, any_amt = s100_amount_total(s)
    ctx["total"] = tot
    if (s.get("CashAmount") or "") and pkg == "network":
        F.add(pkg, "S100-CASH", "REVIEW", tc, legf,
              f"CashAmount={s.get('CashAmount')} on MasterCard credit - p.160: cashback not supported for MasterCard credit",
              "Cash Amount")
    if pkg == "network" and (s.get("FuelPurchaseAmount") or s.get("PumpLaneNumber")) and \
            not (tc.get("acceptance_context") or "").startswith(("PETROLEUM", "MCC_5542")):
        F.add(pkg, "S100-FUEL-CTX", "REVIEW", tc, legf,
              f"fuel fields (Pump {s.get('PumpLaneNumber')!r}, Fuel {s.get('FuelPurchaseAmount')!r}) at non-fuel context {tc.get('acceptance_context')}",
              "Fuel Purchase Amount")
    if tt == "E" and any_amt:
        F.add(pkg, "S100-AMT", sev_for(tc, {"nonfuelamount"}), tc, legf, "Balance Inquiry carries an amount", "Nonfuel Amount")
    if tt not in ("E", "9") and not any_amt and not any(is_token(s.get(k) or "") for k in ("NonfuelAmount", "FuelPurchaseAmount")):
        F.add(pkg, "S100-AMT", sev_for(tc, {"nonfuelamount"}), tc, legf, f"TT {tt}: no transaction amount", "Nonfuel Amount")
    seq = s.get("SequenceNumber") or ""
    ctx["seq"] = seq
    if not re.fullmatch(r"\d{6}", seq) or seq == "000000":
        F.add(pkg, "S100-SEQ", sev_for(tc, {"sequencenumber"}), tc, legf, f"SequenceNumber={seq!r} (000001-999999)",
              "Sequence Number")
    appr = s.get("ApprovalNumber") or ""
    ctx["appr"] = appr
    needs_appr = role in ("reversal", "cancellation", "void_return", "moto_reversal", "completion")
    if appr and role not in FOLLOW_ROLES and role != "timeout_reversal":
        F.add(pkg, "S100-APPR", sev_for(tc, {"approvalnumber"}, check="S100-APPR"), tc, legf,
              f"ApprovalNumber={appr!r} on original {role} leg (E5 p.368: makes a purchase preauthorized - host does not authorize)",
              "Approval Number")
    if needs_appr and not appr:
        F.add(pkg, "S100-APPR", sev_for(tc, {"approvalnumber"}, check="S100-APPR"), tc, legf,
              f"{role} leg without Approval Number (E5 p.368: required on reversals / matching)", "Approval Number")
    ldt = s.get("LocalDateandLocalTime") or ""
    if ldt and role not in ("completion", "timeout_reversal"):
        F.add(pkg, "S100-LDT", sev_for(tc, {"localdateandlocaltime"}, check="S100-LDT"), tc, legf,
              f"LocalDateandLocalTime={ldt!r} on {role} leg (E49 p.388: only POS/CAT Capture)", "Local Date and Local Time")
    pai = s.get("PartialApprovalIndicator")
    if pai not in ("0", "1"):
        F.add(pkg, "S100-PAI", sev_for(tc, {"partialapprovalindicator"}), tc, legf,
              f"PartialApprovalIndicator={pai!r} (E121: 0/1; 5 Amex prepaid only)", "Partial Approval Indicator")


def check_seg111(F, pkg, tc, legf, seg, ctx, msgname):
    secs = seg.get("VariableInformationSection") or []
    body = 0
    provisional = False
    tables = {}
    for sec in secs:
        ind = sec.get("VariableInformationIndicator") or ""
        ln = sec.get("VariableInformationLength") or ""
        data = sec.get("VariableInformation") or ""
        tables[ind] = data
        if AWAIT_RE.search(data) or AWAIT_RE.search(ln):
            provisional = True
            body += len(ind) + (0 if AWAIT_RE.search(ln) else len(ln))
            continue
        body += len(ind) + len(ln) + len(data)
        if not re.fullmatch(r"\d{3}", ln) or int(ln) != len(data):
            F.add(pkg, "S111-TLEN", sev_for(tc, {"variableinformationlength"}), tc, legf,
                  f"Table {ind}: VariableInformationLength={ln!r} vs data length {len(data)}", "Variable Information Length")
        if not re.fullmatch(r"\d{3}", ind):
            F.add(pkg, "S111-TID", sev_for(tc, {"variableinformationindicator"}), tc, legf,
                  f"Table ID {ind!r} not N3", "Variable Information Indicator")
    comp = 3 + 1 + 3 + 1 + body + (1 if secs else 0)
    decl = seg.get("SegmentLength") or ""
    if provisional:
        ctx["awaiting_tables"] = True
        F.add(pkg, "S111-AWAIT-LEN", "REVIEW", tc, legf,
              f"Seg 111 length {decl} provisional: awaiting table(s) counted as 0 incl. the 3-digit E112 (R4-10)",
              "Segment Length")
    elif not re.fullmatch(r"\d{3}", decl) or int(decl) != comp:
        F.add(pkg, "S111-LEN", sev_for(tc, {"segmentlength"}), tc, legf, f"Seg 111 SegmentLength={decl} computed={comp}",
              "Segment Length")
    ctx["tables"] = tables
    t005 = tables.get("005")
    role = ctx.get("role")
    if t005 is None:
        if msgname in FINANCIAL:
            F.add(pkg, "S111-T005", sev_for(tc, {"variableinformation", "posentrymode"}, check="S111-T005"), tc, legf,
                  "Table 005 (POS Entry Mode) missing", "Table 005")
    else:
        entry = t005[:2]
        ctx["entry"] = entry
        if not re.fullmatch(r"\d{2}[0123468]", t005 or ""):
            F.add(pkg, "S111-T005", sev_for(tc, {"variableinformation", "posentrymode"}, check="S111-T005"), tc, legf,
                  f"Table 005 {t005!r} invalid (App. J p.638-639)", "Table 005")
        exp = ctx.get("leg_entry")
        if exp and entry != exp:
            F.add(pkg, "S111-T005", sev_for(tc, {"variableinformation", "posentrymode"}, check="S111-T005"), tc, legf,
                  f"Table 005 entry {entry} but TC leg expects {exp}", "Table 005")
        dim = (tc.get("dimensions") or {}).get("entry_mode")
        if pkg == "network" and dim in ENTRY_OF_DIM and entry not in ENTRY_OF_DIM[dim] and not (entry == "02" and "90" in ENTRY_OF_DIM[dim]):
            F.add(pkg, "S111-T005", sev_for(tc, {"variableinformation", "posentrymode"}, check="S111-T005"), tc, legf,
                  f"Table 005 entry {entry} inconsistent with scenario entry mode {dim}", "Table 005")
    t030 = tables.get("030")
    entry = ctx.get("entry")
    if msgname in FINANCIAL:
        need030 = (entry in CHIP_READ or entry in ("10", "82") or msgname == "EMV Financial Transaction Request")
        if t030 is None and need030:
            F.add(pkg, "S111-T030", sev_for(tc, {"variableinformation", "posconditioncode"}), tc, legf,
                  f"Table 030 missing on entry {entry} (App. I p.568: required for EMV, mobile/internet and COF)", "Table 030")
        if t030 is not None and not AWAIT_RE.search(t030):
            if not re.fullmatch(r"[01][0-2][01][0-28][0-28][0-2][0-5][0-3]\d{2}", t030):
                F.add(pkg, "S111-T030", sev_for(tc, {"variableinformation", "posconditioncode"}), tc, legf,
                      f"Table 030 {t030!r} not decodable per App. Q", "Table 030")
            actx = tc.get("acceptance_context") or ""
            rc_class = next((rc.get("allowed") for rc in tc.get("rule_checks") or [] if rc.get("id") == "CTX-TERMINAL-CLASS"), None)
            rc_value = next((rc.get("allowed") for rc in tc.get("rule_checks") or [] if rc.get("id") == "TABLE-030-VALUE"), None) or []
            mism = bool(rc_class) and ((actx in UNATTENDED_CTX) != ("100" in rc_class or "101" in rc_class))
            if pkg == "network" and actx and not mism and t030 not in rc_value:
                exp030 = CTX_T030.get(actx, "0100000004")
                if t030 != exp030:
                    F.add(pkg, "S111-T030-CTX", sev_for(tc, {"variableinformation", "posconditioncode"}, check="S111-T030-CTX"), tc, legf,
                          f"Table 030 {t030} vs context {actx} profile {exp030}", "Table 030")
        if "031" not in tables:
            F.add(pkg, "S111-T031", sev_for(tc, {"variableinformation", "terminalcapability"}), tc, legf,
                  "Table 031 (terminal capability) missing (App. I p.569: all transactions)", "Table 031")
        elif tables["031"] not in T031_CODES:
            F.add(pkg, "S111-T031", sev_for(tc, {"variableinformation", "terminalcapability"}), tc, legf,
                  f"Table 031 {tables['031']!r} invalid", "Table 031")
        elif tables["031"] == "00" and entry in CHIP_READ:
            F.add(pkg, "S111-T031-CAP", "REVIEW", tc, legf,
                  f"Table 031 '00' (unknown) on a chip/contactless read (entry {entry}); App. I p.569 expects the certified capability (05/06/12)",
                  "Table 031")
        if entry == "10" and role not in FOLLOW_ROLES:
            missing = [t for t in ("032", "049", "056") if t not in tables]
            if missing:
                F.add(pkg, "S111-COF", sev_for(tc, {"variableinformation", "credentialonfile"}), tc, legf,
                      f"COF leg without Table(s) {missing} (CD-04)", "COF tables")
    for ind, data in tables.items():
        if AWAIT_RE.search(data or ""):
            ctx.setdefault("awaiting_list", []).append("T" + ind)


def check_seg130(F, pkg, tc, legf, seg, ctx, s100):
    fixed = ["SegmentType", "SegmentLength", "CAPublicKeyFileChecksum", "EMVCardSequenceNumber", "EMVChipDataLength",
             "EMVChipData"]
    chip = seg.get("EMVChipData") or ""
    chip_bytes = len(chip) // 2
    comp = 0
    for k in fixed:
        v = seg.get(k) or ""
        comp += (chip_bytes if k == "EMVChipData" else len(v)) + 1
    ind = seg.get("EMVAdditionalInformationIndicator") or ""
    alen = seg.get("EMVAdditionalInformationLength") or ""
    info = seg.get("EMVAdditionalInformation") or ""
    if ind:
        comp += len(ind) + len(alen) + len(info) + 1
    decl = seg.get("SegmentLength") or ""
    if not re.fullmatch(r"\d{4}", decl) or int(decl) != comp:
        F.add(pkg, "S130-LEN", sev_for(tc, {"segmentlength"}), tc, legf, f"Seg 130 SegmentLength={decl} computed={comp}",
              "Segment Length")
    cdl = seg.get("EMVChipDataLength") or ""
    if not re.fullmatch(r"\d{3}", cdl) or int(cdl) != chip_bytes:
        F.add(pkg, "S130-CHIPLEN", sev_for(tc, {"emvchipdatalength"}), tc, legf,
              f"EMVChipDataLength={cdl!r} vs chip bytes {chip_bytes}", "EMV Chip Data Length")
    if not re.fullmatch(r"([0-9A-Fa-f]{2})+", chip or "x"):
        F.add(pkg, "S130-TLV", sev_for(tc, {"emvchipdata"}), tc, legf, "EMVChipData not hex", "EMV Chip Data")
        return
    body = chip
    if re.fullmatch(r"\d{4}", chip[:4]) and int(chip[:4]) == chip_bytes - 2:
        body = chip[4:]
    tlv = parse_tlv(body)
    if tlv is None:
        F.add(pkg, "S130-TLV", sev_for(tc, {"emvchipdata"}), tc, legf, "EMV chip data TLV malformed", "EMV Chip Data")
        return
    ctx["tlv"] = tlv
    csn = seg.get("EMVCardSequenceNumber") or ""
    if not re.fullmatch(r"\d{3}", csn):
        F.add(pkg, "S130-CSN", sev_for(tc, {"emvcardsequencenumber"}), tc, legf, f"E188={csn!r} not 3 digits (CD-01)",
              "EMV Card Sequence Number")
    elif "5F34" in tlv and int(tlv["5F34"], 16) != int(csn):
        F.add(pkg, "S130-CSN", sev_for(tc, {"emvcardsequencenumber"}), tc, legf, f"E188={csn} vs 5F34={tlv['5F34']}",
              "EMV Card Sequence Number")
    a9f02 = tlv.get("9F02")
    nf = s100.get("NonfuelAmount") or ""
    if is_token(nf):
        F.add(pkg, "S130-9F02", sev_for(tc, {"emvchipdata", "amountauthorized"}), tc, legf,
              f"9F02={a9f02} while NonfuelAmount is runtime token {nf[:40]} - chip amount must equal the step amount (CD-08)",
              "Tag 9F02")
    elif a9f02 is not None:
        tot = ctx.get("total", 0)
        if ctx.get("tt") == "E":
            if int(a9f02 or "0") != 0:
                F.add(pkg, "S130-9F02", sev_for(tc, {"emvchipdata"}), tc, legf, f"Balance Inquiry 9F02={a9f02} not zero", "Tag 9F02")
        elif not re.fullmatch(r"\d{12}", a9f02) or int(a9f02) != tot:
            F.add(pkg, "S130-9F02", sev_for(tc, {"emvchipdata", "nonfuelamount", "amountauthorized"}), tc, legf,
                  f"9F02={a9f02} vs Seg 100 total {tot}", "Tag 9F02")
    tt = ctx.get("tt")
    exp9c = {"7": {"20"}, "E": {"30", "31"}}.get(tt, {"00"})
    if (s100.get("CashAmount") or "") and tt in ("0", "4"):
        exp9c = {"09"}
    if "9C" in tlv and tlv["9C"] not in exp9c:
        F.add(pkg, "S130-9C", sev_for(tc, {"emvchipdata"}), tc, legf, f"9C={tlv['9C']} vs TT {tt} (expected {sorted(exp9c)})", "Tag 9C")
    cvm = tlv.get("9F34", "")
    pin = ctx.get("pin") or ""
    if cvm[:2] in ("02", "42") and not pin:
        F.add(pkg, "S130-CVM", sev_for(tc, {"encryptedpinblockdata", "cvm"}), tc, legf,
              f"9F34={cvm} (online PIN) but E33 is empty (CD-13)", "Tag 9F34")
    if pin and cvm[:2] not in ("02", "42") and not AWAIT_RE.fullmatch(pin):
        F.add(pkg, "S130-CVM", sev_for(tc, {"encryptedpinblockdata", "cvm"}), tc, legf,
              f"PIN block sent but 9F34={cvm} is not online PIN", "Tag 9F34")
    tvr = tlv.get("95", "")
    if len(tvr) == 10 and int(tvr[4:6], 16) & 0x04 and not pin:
        F.add(pkg, "S130-TVR", "REVIEW", tc, legf, f"TVR 95={tvr}: byte 3 'online PIN entered' but no PIN sent (RV-06)", "Tag 95")
    if ctx.get("entry") in ("07", "86") and not any(t in tlv for t in ("9F6E", "9F66", "9F6D", "9F7C")):
        F.add(pkg, "S130-CTLS", "REVIEW", tc, legf, "contactless leg without contactless tags 9F6E/9F66/9F6D/9F7C (RV-13)",
              "EMV Chip Data")
    if ind != "001" or alen != "006" or info not in ("EMVYES", "EMVNOT"):
        F.add(pkg, "S130-ADDL", sev_for(tc, {"emvadditionalinformation", "emvadditionalinformationindicator",
                                             "emvadditionalinformationlength"}), tc, legf,
              f"E191/E192/E118 = {ind!r}/{alen!r}/{info[:30]!r}{'...' if len(info) > 30 else ''} (expected 001/006/EMVYES)",
              "EMV Additional Information")
    if (seg.get("CAPublicKeyFileChecksum") or "") == "2012091700000100000102171":
        ctx["qe_capk"] = True


def check_seg102(F, pkg, tc, legf, seg, ctx, s100):
    sl = seg.get("ServiceLevel") or ""
    np_ = seg.get("NumberofProducts") or ""
    code = seg.get("ProductCode") or ""
    uom = seg.get("UnitofMeasure") or ""
    qty = seg.get("Quantity") or ""
    price = seg.get("UnitPrice") or ""
    amt = seg.get("ProductAmount") or ""
    if any(is_token(x) for x in (price, amt, qty)):
        F.add(pkg, "S102-RT", "REVIEW", tc, legf, f"Seg 102 values are runtime tokens ({price[:30]} / {amt[:30]})",
              "Product Code Data Segment")
        return
    comp = 3 + 1 + 3 + 1 + len(sl) + len(np_) + len(code) + len(uom) + len(qty) + 1 + len(price) + 1 + len(amt) + 1
    decl = seg.get("SegmentLength") or ""
    if not re.fullmatch(r"\d{3}", decl) or int(decl) != comp:
        F.add(pkg, "S102-LEN", sev_for(tc, {"segmentlength"}), tc, legf, f"Seg 102 SegmentLength={decl} computed={comp}",
              "Segment Length")
    if sl not in ("F", "S", "N", "X", "H", "O"):
        F.add(pkg, "S102-E87", sev_for(tc, {"servicelevel"}), tc, legf, f"ServiceLevel={sl!r} (E87)", "Service Level")
    if np_ != "01":
        F.add(pkg, "S102-E62", sev_for(tc, {"numberofproducts"}), tc, legf, f"NumberofProducts={np_!r} with one product",
              "Number of Products")
    if re.fullmatch(r"\d+", amt) and ctx.get("total") is not None and int(amt) != ctx.get("total"):
        F.add(pkg, "S102-SUM", sev_for(tc, {"productamount"}), tc, legf,
              f"ProductAmount {amt} vs Seg 100 total {ctx.get('total')} (12.3 p.226)", "Product Amount")


def check_optional(F, pkg, tc, legf, msgname, msg, ctx):
    for sname, seg in msg.items():
        if not isinstance(seg, dict):
            continue
        no = SEG_NO.get(sname)
        if msgname in FINANCIAL and pkg == "network":
            if no and no not in SECTION3_ALLOWED and no != "100":
                F.add(pkg, "SEG-OPT-APPL", sev_for(tc, {norm(sname)}), tc, legf,
                      f"Seg {no} {sname} not permitted in a {msgname} (11.1.1 p.166-169 / 11.8.1 p.206-207)", sname)
            elif no in FLEET_ONLY:
                F.add(pkg, "SEG-OPT-APPL", sev_for(tc, {norm(sname)}), tc, legf,
                      f"Seg {no} {sname} on MasterCard credit (fleet card data only; 12.2 p.226 / 12.31)", sname)
        tot = ctx.get("total") or 0
        if no == "104":
            amts = [seg.get(k) or "" for k in ("PCTaxAmount", "PCFreightAmount", "PCDutyAmount")]
            big = [a for a in amts if re.fullmatch(r"\d+", a) and int(a) > max(tot, 1)]
            if big or re.search(r"[A-Z]", seg.get("ShiptoPostalCode") or ""):
                F.add(pkg, "SEG-OPT-RAND", sev_for(tc, {norm(sname)}), tc, legf,
                      f"Seg 104 synthetic content: amounts {amts} vs sale {tot}; postal {seg.get('ShiptoPostalCode')!r}; country {seg.get('ShiptoCountryCode')!r}",
                      sname)
        if no == "108":
            ca = seg.get("CouponAmount") or ""
            if (re.fullmatch(r"\d+", ca) and int(ca) > max(tot, 1)) or (seg.get("PaymentTenderType") not in ("", "MC", None)):
                F.add(pkg, "SEG-OPT-RAND", sev_for(tc, {norm(sname)}), tc, legf,
                      f"Seg 108 CouponAmount {ca} on sale {tot}; PaymentTenderType {seg.get('PaymentTenderType')!r}", sname)
        if no == "145":
            d = seg.get("EnhancedFleetData") or ""
            if len(d) >= 200 and re.fullmatch(r"[A-Z0-9]+", d):
                F.add(pkg, "SEG-OPT-RAND", sev_for(tc, {norm(sname)}), tc, legf,
                      f"Seg 145 EnhancedFleetData = {len(d)} random A-Z/0-9 chars (12.31 TLV prompt data expected)", sname)
        if no == "123":
            if (seg.get("SafeKeyData") or "") or not re.fullmatch(r"\d{4}", seg.get("TokenPANSuffix") or "0000"):
                F.add(pkg, "SEG-OPT-RAND", sev_for(tc, {norm(sname)}), tc, legf,
                      f"Seg 123 synthetic token data on entry {ctx.get('entry')} (Amex SafeKeyData {bool(seg.get('SafeKeyData'))}, TokenPANSuffix {seg.get('TokenPANSuffix')!r}); 12.19 p.267 tokenized only",
                      sname)


HOST_TID13 = {"Table Load Request", "Phone Load Request", "Date and Time Load Request", "Software Load Request"}


def check_host(F, pkg, tc, legf, msgname, msg):
    """R4-08/R4-09 host-message identity checks (data element + operations packages)."""
    segs = {k: v for k, v in msg.items() if isinstance(v, dict)}
    for sname, seg in segs.items():
        tid = seg.get("TerminalIdentifier")
        if tid is None:
            continue
        if msgname == "CA Public Key File Load Request":
            if not (len(tid) == 13 and tid.startswith("+*")):
                F.add(pkg, "H-TID-CAPK", sev_for(tc, {"terminalidentifier"}), tc, legf,
                      f"CAPK load TID {tid!r}: 13 bytes, Device Type '+*' (E48 p.388; Seg 132)", "Terminal Identifier")
            sl = seg.get("SegmentLength") or ""
            if re.fullmatch(r"\d+", sl) and int(sl) > 77:
                F.add(pkg, "H-SEGMAX", sev_for(tc, {"segmentlength"}), tc, legf, f"Seg 132 length {sl} > max 77", "Segment Length")
        elif msgname == "TransArmor Key Load Request":
            if not tid.startswith("++"):
                F.add(pkg, "H-TID-TA", sev_for(tc, {"terminalidentifier"}), tc, legf,
                      f"TransArmor load TID {tid!r}: Device Type '++' (E102 p.417)", "Terminal Identifier")
        elif msgname in HOST_TID13:
            if len(tid) != 13:
                F.add(pkg, "H-TID13", sev_for(tc, {"terminalidentifier"}), tc, legf,
                      f"{msgname} TID {tid!r} ({len(tid)} bytes; E102 note: 13)", "Terminal Identifier")
        for k in ("HardwareVersion", "SoftwareVersion", "FirmwareVersion"):
            v = seg.get(k)
            if v and re.fullmatch(r"(?=.*[A-Z])(?=.*\d)[A-Z0-9]{8}", v):
                F.add(pkg, "H-SYNTH-VER", "REVIEW", tc, legf, f"{k}={v!r} random synthetic device version (R4-09)", k)
                break
        if sname == "Totals Data Segment" or sname == "Totals with Proprietary Data Load Data Segment":
            lab = seg.get("CardLabel")
            if lab is not None and lab != "CC  ":
                F.add(pkg, "S105-LABEL", sev_for(tc, {"cardlabel"}), tc, legf, f"CardLabel={lab!r} (E13: 'CC  ')", "Card Label")
            seq = seg.get("SequenceNumber") or ""
            if not re.fullmatch(r"\d{6}", seq) or seq == "000000":
                F.add(pkg, "H-SEQ", sev_for(tc, {"sequencenumber"}), tc, legf, f"SequenceNumber={seq!r}", "Sequence Number")
            ib = seg.get("InformationByte")
            if ib not in ("0", "1"):
                F.add(pkg, "H-IB", sev_for(tc, {"informationbyte"}), tc, legf, f"InformationByte={ib!r} (Totals: 0/1)",
                      "Information Byte")
        if msgname == "Proprietary Data Load Request":
            pass
    if msgname == "Proprietary Data Load Request":
        # p.200: Number of Segments "Fixed value of 1"; E63 p.395 "variable length of up to two digits" -> "1" is valid
        ns = msg.get("NumberofSegments")
        if ns is not None and ns not in ("1", "01"):
            F.add(pkg, "H-NUMSEG", sev_for(tc, {"numberofsegments"}), tc, legf, f"NumberofSegments={ns!r} (p.200: fixed value 1)",
                  "Number of Segments")
    if msgname == "CA Public Key File Load Request":
        seg = msg.get("CA Public Key File Data Segment") or {}
        hw = seg.get("HardwareVersion") or ""
        if len(hw) > 4:
            F.add(pkg, "H-CAPK-HW", sev_for(tc, {"hardwareversion"}), tc, legf,
                  f"Seg 132 HardwareVersion={hw!r} ({len(hw)} bytes); Seg 132 field 6 max length 4 (p.275)", "Hardware Version")


def check_message(F, pkg, tc, legf, d, ctx):
    msgname, msg = first_msg(d)
    ctx["msg"] = msgname
    if msg is None:
        F.add(pkg, "MSG-01", "DEFECT", tc, legf, "no message object")
        return
    segs = [k for k, v in msg.items() if isinstance(v, dict)]
    ctx["segs"] = [SEG_NO.get(s, s) for s in segs]
    ns = msg.get("NumberofSegments")
    if msgname in FINANCIAL or msgname == "Totals Request":
        if ns is None or not re.fullmatch(r"\d{2}", ns) or int(ns) != len(segs):
            F.add(pkg, "MSG-02", sev_for(tc, {"numberofsegments"}), tc, legf, f"NumberofSegments={ns!r} vs {len(segs)} segments",
                  "Number of Segments")
    if msg.get("MessageFormatVersionIdentifier", "ATL105") != "ATL105":
        F.add(pkg, "MSG-04", sev_for(tc, {"messageformatversionidentifier"}), tc, legf,
              f"E55={msg.get('MessageFormatVersionIdentifier')!r}", "Message Format Version Identifier")
    if msgname in FINANCIAL:
        s100 = msg.get("Standard Message Data Segment")
        if s100 is None:
            F.add(pkg, "MSG-03", sev_for(tc, {"standardmessagedatasegment"}), tc, legf, "Seg 100 missing", "Seg 100")
            return
        seg111 = msg.get("Variable Information Data Segment")
        if seg111 is not None:
            check_seg111(F, pkg, tc, legf, seg111, ctx, msgname)
        elif pkg == "network":
            F.add(pkg, "S111-MISSING", sev_for(tc, {"variableinformationdatasegment"}), tc, legf,
                  "Seg 111 missing (Table 031 is required on all transactions)", "Seg 111")
        if "entry" not in ctx:
            ctx["entry"] = ctx.get("leg_entry")
        check_seg100(F, pkg, tc, legf, s100, ctx)
        entry = ctx.get("entry")
        has130 = "EMV Request Data Segment" in msg
        role = ctx.get("role")
        if entry in CHIP_READ and role not in FOLLOW_ROLES and not has130:
            F.add(pkg, "MSG-03", sev_for(tc, {"emvrequestdatasegment"}, check="MSG-03"), tc, legf,
                  f"chip entry {entry} original leg without Seg 130 (11.8.1)", "Seg 130")
        if has130 and entry not in CHIP_READ and role not in FOLLOW_ROLES:
            F.add(pkg, "MSG-03", sev_for(tc, {"emvrequestdatasegment"}, check="MSG-03"), tc, legf,
                  f"Seg 130 on non-chip entry {entry}", "Seg 130")
        if has130 and msgname != "EMV Financial Transaction Request":
            F.add(pkg, "MSG-03", sev_for(tc, {"emvrequestdatasegment"}, check="MSG-03"), tc, legf, f"Seg 130 inside {msgname}", "Seg 130")
        if has130:
            check_seg130(F, pkg, tc, legf, msg["EMV Request Data Segment"], ctx, s100)
        if "Product Code Data Segment" in msg:
            check_seg102(F, pkg, tc, legf, msg["Product Code Data Segment"], ctx, s100)
        check_optional(F, pkg, tc, legf, msgname, msg, ctx)
    else:
        check_host(F, pkg, tc, legf, msgname, msg)


# ------------------------------------------------------------------------------------------------ TC level

def load_json(p):
    try:
        with open(p, encoding="utf-8") as f:
            return json.load(f), None
    except FileNotFoundError:
        return None, "missing"
    except Exception as e:
        return None, f"parse error {e}"


def leg_info(tc, step):
    for leg in tc.get("tx_legs") or []:
        if leg.get("step") == step:
            return leg
    return None


ROLE_MAP = {"auth_only": "auth_only", "purchase": "purchase", "return": "return", "balance_inquiry": "balance_inquiry",
            "moto_purchase": "moto_purchase", "moto_auth_only": "moto_auth_only", "reversal": "reversal",
            "timeout_reversal": "timeout_reversal", "cancellation": "cancellation", "void_return": "void_return",
            "moto_reversal": "moto_reversal", "totals": "totals", "split_tender": "split_tender"}


def validate_tc(F, pkg, tc, base):
    files = tc.get("test_data_leg_files") or ([tc["test_data_file"]] if tc.get("test_data_file") else [])
    legs = []
    for f in files:
        p = os.path.join(base, f.replace("/", os.sep))
        d, err = load_json(p)
        step, frole = leg_role_from_file(f)
        if err:
            F.add(pkg, "TD-FILE", "DEFECT", tc, f, f"test data file {err}")
            continue
        li = leg_info(tc, step) or {}
        role = ROLE_MAP.get((li.get("role") or frole or "").replace("-", "_"), li.get("role") or frole)
        if role is None:
            role = ROLE_MAP.get(((tc.get("dimensions") or {}).get("txn_category") or "").lower(), "original")
        legs.append({"file": f, "step": step, "role": role, "leg": li, "data": d})
    # completion: a purchase leg that follows an auth-only leg of the same flow
    roles = [l["role"] for l in legs]
    for i, l in enumerate(legs):
        if l["role"] == "purchase" and i > 0 and any(r in ("auth_only",) for r in roles[:i]) and (l["leg"].get("follow_on") or l["leg"].get("of")):
            l["role"] = "completion"
    ctxs = []
    for l in legs:
        li = l["leg"]
        ctx = {"role": l["role"], "leg_code": li.get("code"), "leg_entry": li.get("entry_code")}
        if l["data"] is not None:
            check_message(F, pkg, tc, l["file"], l["data"], ctx)
        ctxs.append(ctx)
        l["ctx"] = ctx
    if pkg == "network" and len(legs) > 1:
        check_flow(F, pkg, tc, legs)
    check_tc_claims(F, pkg, tc, legs)
    return legs


def check_flow(F, pkg, tc, legs):
    pos = tc.get("polarity") == "Positive"
    fin = [l for l in legs if l["ctx"].get("msg") in FINANCIAL]
    if not fin:
        return
    orig = fin[0]
    for l in fin[1:]:
        c, o = l["ctx"], orig["ctx"]
        li = l["leg"]
        of = li.get("of")
        if of:
            cand = [x for x in fin if x["step"] == of]
            if cand:
                o = cand[0]["ctx"]
        if l["role"] == "split_tender":
            continue
        if l["role"] in FOLLOW_ROLES:
            if c.get("seq") and o.get("seq") and c["seq"] != o["seq"]:
                F.add(pkg, "FO-SEQ", "DEFECT" if pos else "NEG", tc, l["file"],
                      f"{l['role']} Sequence {c['seq']} vs original {o['seq']} (E86: one number for the transaction's life)",
                      "Sequence Number")
            if c.get("tid") and o.get("tid") and c["tid"] != o["tid"]:
                F.add(pkg, "FO-TID", "DEFECT" if pos else "NEG", tc, l["file"], f"TID {c['tid']} vs original {o['tid']}",
                      "Terminal Identifier")
            if c.get("pan") and o.get("pan") and c["pan"] != o["pan"] and not AWAIT_RE.search(c["pan"] + o["pan"]):
                F.add(pkg, "FO-PAN", "DEFECT" if pos else "NEG", tc, l["file"], f"PAN {c['pan']} vs original {o['pan']}",
                      "Account Number")
            if c.get("entry") and o.get("entry") and c["entry"] != o["entry"] and not (o["entry"] == "90" and c["entry"] == "02"):
                F.add(pkg, "FO-ENTRY", "DEFECT" if pos else "NEG", tc, l["file"],
                      f"follow-on entry {c['entry']} vs original {o['entry']} (App. J p.638: persists; 90->02)", "Table 005")
            if l["role"] == "timeout_reversal" and c.get("total") != o.get("total") and o.get("total"):
                F.add(pkg, "FO-AMT", "DEFECT" if pos else "NEG", tc, l["file"],
                      f"TOR amount {c.get('total')} vs original {o.get('total')} (p.84: TOR = original amount)", "Nonfuel Amount")
            oc, cc = o.get("cdbd") or "", c.get("cdbd") or ""
            if len(cc) == 4 and oc:
                o_yymm = None
                if o.get("entry") in KEYED and re.fullmatch(r"\d{4}", oc):
                    o_yymm = oc[2:] + oc[:2]
                elif re.fullmatch(r"\d{4,}", oc):
                    o_yymm = oc[:4]
                if o_yymm and cc != o_yymm and cc != oc:
                    F.add(pkg, "FO-EXP", "DEFECT" if pos else "NEG", tc, l["file"],
                          f"follow-on expiry {cc} vs original {oc} (YYMM {o_yymm})", "Card Discretionary Block Data")
            if l["role"] == "timeout_reversal" and o.get("tt") in ("3", "5", "B"):
                F.add(pkg, "FO-TOR-AUTH", "DEFECT" if pos else "NEG", tc, l["file"],
                      f"TOR of an Authorization Only (TT {o.get('tt')}); 2.3.4 p.47: to remove an auth-only hold the device must send a cancellation, not a TOR",
                      "Transaction Type")


DT_PARAM = re.compile(r"with (.+?) = (.+?) → ")


def check_tc_claims(F, pkg, tc, legs):
    name = tc.get("name") or ""
    obj = tc.get("test_objective") or ""
    datas = [l["data"] for l in legs if l["data"] is not None]
    blob = json.dumps(datas)
    has_await = bool(AWAIT_RE.search(blob))
    status = tc.get("status")
    aw = tc.get("awaiting_inputs") or []
    # R4-10 contract: awaiting placeholders <-> status
    if has_await and status not in ("needs_client_input",):
        F.add(pkg, "TC-AWAIT-STATUS", "DEFECT", tc, "", f"TD carries <AWAITING:...> but status is {status} (contract: needs_client_input)")
    if status == "needs_client_input" and not has_await and datas:
        if not aw:
            F.add(pkg, "TC-AWAIT-EMPTY", "DEFECT", tc, "",
                  "status needs_client_input but TD has no <AWAITING:...> placeholder and awaiting_inputs is empty - nothing for the client to supply")
        else:
            present = set()
            for d in datas:
                _, msg = first_msg(d)
                present |= {SEG_NO.get(k, k) for k, v in (msg or {}).items() if isinstance(v, dict)}
            asked = set()
            other = False
            for a in aw:
                a = a if isinstance(a, str) else json.dumps(a)
                m2 = re.search(r"segment (\d{3}) PENDING_(CLIENT_CAPABILITY|SME)", a)
                if m2:
                    asked.add(m2.group(1))
                else:
                    other = True
            if asked and not other and not (asked & present):
                F.add(pkg, "TC-STATUS-INFLATED", "DEFECT", tc, "",
                      f"status needs_client_input only because of capability questions for segment(s) {sorted(asked)} that this TD does not send; TD is complete (no <AWAITING>)")
    # pairwise / named CVM claims
    if datas:
        _, msg0 = first_msg(datas[0])
        s100_0 = (msg0 or {}).get("Standard Message Data Segment") or {}
        entry0 = legs[0]["ctx"].get("entry")
        if re.search(r"with offline pin", name, re.I) and entry0 not in CHIP_READ:
            F.add(pkg, "TC-CLAIM-CVM", "DEFECT", tc, "",
                  f"'with offline pin' on entry {entry0} - offline PIN is verified by the chip (10.14.3 p.162); impossible on this entry mode")
        if re.search(r"with online pin", name, re.I):
            pin0 = s100_0.get("EncryptedPINBlockData") or ""
            if not pin0:
                F.add(pkg, "TC-CLAIM-CVM", "DEFECT", tc, "",
                      "'with online pin' but E33 is empty - no PIN block and no <AWAITING:EncryptedPINBlockData> (contract: never an empty field; CD-13)")
            if entry0 not in CHIP_READ:
                F.add(pkg, "TC-CLAIM-CVM", "REVIEW", tc, "",
                      f"online PIN on non-EMV credit entry {entry0} (10.14.3 describes credit online PIN for EMV; E33 'required for debit and EBT')")
    # claims: "sending Table NNN"
    m = re.search(r"[Ss]ending Table (\d{3})", obj + " " + name)
    if m and datas:
        tid = m.group(1)
        sent = set()
        for d in datas:
            _, msg = first_msg(d)
            if msg:
                sent |= {t[0] for t in tables_of(msg)}
        if tid not in sent:
            F.add(pkg, "TC-CLAIM-TABLE", "DEFECT", tc, "",
                  f"objective '{obj[:90]}' but Table {tid} is not in the TD (tables sent: {sorted(sent)}); awaiting_inputs={len(aw)}")
    # decision-table parameters that must be visible on the wire
    if datas and tc.get("test_type") == "decision_table":
        _, msg = first_msg(datas[0])
        s100 = (msg or {}).get("Standard Message Data Segment") or {}
        segnames = set(k for k, v in (msg or {}).items() if isinstance(v, dict))
        tlv = legs[0]["ctx"].get("tlv") or {}
        entry = legs[0]["ctx"].get("entry")
        pm = DT_PARAM.search(name)
        if pm:
            p, v = pm.group(1).strip(), pm.group(2).strip()
            if p == "Tax Type 1" and not any("Tax" in s for s in segnames):
                F.add(pkg, "TC-CLAIM-PARAM", "DEFECT", tc, "", f"'{p} = {v}' but no tax segment/field in the TD (segments {sorted(segnames)})")
            if p == "track" and v == "1" and "^" not in (s100.get("CardDiscretionaryBlockData") or ""):
                F.add(pkg, "TC-CLAIM-PARAM", "DEFECT", tc, "",
                      f"'track = 1' but E12 has no Track-1 data (entry {entry}, E12 {s100.get('CardDiscretionaryBlockData')!r})")
            if p == "cvm" and v == "online_pin" and not (s100.get("EncryptedPINBlockData") or ""):
                F.add(pkg, "TC-CLAIM-PARAM", "DEFECT", tc, "",
                      f"'cvm = online_pin' but E33 is empty (no PIN block, no <AWAITING> placeholder) - CD-13")
            if p == "cvm" and v == "offline_pin":
                if entry not in CHIP_READ:
                    F.add(pkg, "TC-CLAIM-PARAM", "DEFECT", tc, "", f"'cvm = offline_pin' on non-chip entry {entry} (10.14.3 p.162: chip only)")
                elif tlv.get("9F34", "")[:2] not in ("01", "41", "03", "43", "04", "44", "05", "45"):
                    F.add(pkg, "TC-CLAIM-PARAM", "DEFECT", tc, "", f"'cvm = offline_pin' but 9F34={tlv.get('9F34')} is not an offline-PIN CVM")
            if p == "Encrypted PIN Block Data" and v == "PIN":
                pass  # reported by S100-PIN
    # name vs expected outcome (R4-14)
    rc = ((tc.get("expected") or {}).get("response_code") or {}).get("value")
    # expected-result (oracle) checks against E83 Response Code (p.404) and the response message layouts
    if re.search(r"avs = true|AVS or NVS or Both Information returned", name) and rc in ("0", "2"):
        F.add(pkg, "TC-EXP-AVS", "DEFECT", tc, "",
              f"AVS test expects response code {rc!r}; E83 p.404 defines '3' Approved-Authorization only with AVS / '4' Approved-Purchase/Capture with AVS")
    for leg in tc.get("tx_legs") or []:
        e = leg.get("expect") or {}
        if "partially approved" in (e.get("note") or "") and e.get("response_code") not in ("F",):
            F.add(pkg, "TC-EXP-PARTIAL", "DEFECT", tc, "",
                  f"step {leg.get('step')} ({leg.get('role')}) is the partial approval but expects response code {e.get('response_code')!r}; E83 p.404: 'F' = Approved-Partial approval")
    mdl = re.search(r"\((Merchant Data|Store and Forward Data|Dial String Data|Date and Time Data) Segment returned\)", name)
    if mdl:
        F.add(pkg, "TC-EXP-DLSEG", "DEFECT", tc, "",
              f"expects the {mdl.group(1)} Segment in a financial response; it is a download segment (DL1/DL2/DL3/DL6) returned only in Download responses (11.7.1.2 p.191-192, 11.7.2.2 p.194)")
    if "(Transaction Attributes Segment returned)" in name:
        F.add(pkg, "TC-EXP-T134", "REVIEW", tc, "",
              "expects Seg 134 Transaction Attributes; it is conditional (Common AID / settlement type, p.109, p.161, p.211) and the test card is a MasterCard credit AID - precondition missing")
    if "→ declined" in name and rc in ("0", "2") and tc.get("polarity") != "Negative":
        F.add(pkg, "TC-NAME-EXP", "DEFECT", tc, "", f"name says declined but expected response code {rc}")
    if "→ approved" in name and rc not in (None, "0", "2", "") and tc.get("polarity") == "Positive":
        F.add(pkg, "TC-NAME-EXP", "REVIEW", tc, "", f"name says approved but expected response code {rc}")


VOLATILE = {"SequenceNumber", "AccountNumber", "CardDiscretionaryBlockData", "EMVChipData", "SegmentLength"}


def fingerprint(legs):
    def strip(o):
        if isinstance(o, dict):
            return {k: ("~" if k in VOLATILE else strip(v)) for k, v in o.items()}
        if isinstance(o, list):
            return [strip(x) for x in o]
        return o
    return json.dumps([strip(l["data"]) for l in legs if l["data"] is not None], sort_keys=True)


def main():
    F = Finder()
    stats = {}
    dup_groups = []
    for pkg, base in PKG_DIRS.items():
        tcs = json.load(open(os.path.join(HERE, f"tc_index_{pkg}.json"), encoding="utf-8"))
        n_leg = 0
        n_tc_td = 0
        fps = defaultdict(list)
        for tc in tcs:
            legs = validate_tc(F, pkg, tc, base)
            if legs:
                n_tc_td += 1
                n_leg += len(legs)
                fps[(tc.get("scenario_id"), tc.get("acceptance_context"), fingerprint(legs))].append(tc)
        dup_tcs = 0
        for key, group in fps.items():
            if len(group) > 1:
                dup_tcs += len(group) - 1
                dup_groups.append({"package": pkg, "scenario": key[0], "context": key[1], "size": len(group),
                                   "tcs": [g["id"] for g in group], "names": [g["name"][-110:] for g in group],
                                   "polarities": [g["polarity"] for g in group],
                                   "expected": [((g.get("expected") or {}).get("response_code") or {}).get("value") for g in group]})
                approved = [g for g in group if g.get("polarity") == "Positive"
                            and ((g.get("expected") or {}).get("response_code") or {}).get("value") in ("0", "2")]
                if approved and pkg == "network":
                    for g in group:
                        if g.get("polarity") != "Negative":
                            continue
                        harness = any(l.get("harness") or l.get("harness_directive") for l in g.get("tx_legs") or [])
                        F.add(pkg, "TC-DUP-CONFLICT", "REVIEW" if harness else "DEFECT", g, g.get("test_data_file") or "",
                              f"request identical (PAN/expiry/sequence/chip data normalized) to approved positive {approved[0]['id']} "
                              f"('{approved[0]['name'][-60:]}') but expects decline{' - outcome depends on harness directive' if harness else ' - the claimed violation / decline trigger is not on the wire'}")
        stats[pkg] = {"tcs": len(tcs), "tcs_with_td": n_tc_td, "legs": n_leg, "redundant_td_tcs": dup_tcs}
    json.dump(dup_groups, open(os.path.join(HERE, "duplicate_td_groups.json"), "w", encoding="utf-8"), indent=1, ensure_ascii=False)
    out_csv = os.path.join(HERE, "MC_Credit_Round5_Findings.csv")
    cols = ["package", "check_id", "severity", "test_case", "display_id", "test_type", "polarity", "status", "context",
            "txn", "entry_mode", "test_data_file", "element", "detail"]
    with open(out_csv, "w", newline="", encoding="utf-8") as f:
        w = csv.DictWriter(f, fieldnames=cols)
        w.writeheader()
        for r in F.rows:
            w.writerow(r)
    summ = Counter((r["package"], r["check_id"], r["severity"]) for r in F.rows)
    tcs_by = defaultdict(set)
    for r in F.rows:
        tcs_by[(r["package"], r["check_id"], r["severity"])].add(r["test_case"])
    print(json.dumps(stats, indent=1))
    for k in sorted(summ):
        print(f"{k[0]:13s} {k[1]:18s} {k[2]:7s} rows={summ[k]:5d} tcs={len(tcs_by[k]):5d}")
    print("TOTAL rows", len(F.rows), Counter(r["severity"] for r in F.rows))


if __name__ == "__main__":
    sys.exit(main())
