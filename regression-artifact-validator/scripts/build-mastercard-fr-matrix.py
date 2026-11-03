"""Mastercard Credit Purchase view of the canonical Test Solution in the approved FR Matrix layout,
plus a conformance check of every template TS against the Test Solution.

Main sheet (one continuous matrix in the template layout):
  * Column A/B hold only the template business BR (BR-01 Mastercard credit financial purchase transaction processing).
  * Each template FR keeps its template TS rows unchanged (columns A-I), followed by new scenarios TS-038 onwards -
    one per Mastercard-applicable Test Solution BR not already mapped to a template TS (Mastercard-named, or a
    Segment 100 / financial-response / 111 / 123 / 130 / 131 / Section 5 credit BR not specific to another product
    or brand), with scenario name, acceptance criteria, one validation row per Test Solution TC and expected result.
  * Columns J-P carry the Test Solution coverage/trace (status, BR/TS/TC/TD IDs, outcomes, note).

Every BR/TS/TC/TD ID written is an existing record from complete-all-test-solution-br-ts-tc-td-package.json.
"""
import copy
import csv
import importlib.util
import json
import re
from collections import OrderedDict
from pathlib import Path

import openpyxl

HERE = Path(__file__).resolve().parent
_spec = importlib.util.spec_from_file_location("ts_fr", HERE / "build-test-solution-fr-matrix.py")
ts_fr = importlib.util.module_from_spec(_spec)
_spec.loader.exec_module(ts_fr)

OUT_XLSX = ts_fr.OUT_DIR / "Mastercard_Credit_Purchase_Test_Solution_FR_Matrix.xlsx"
OUT_CSV = ts_fr.OUT_DIR / "Mastercard_Credit_Purchase_TestData.csv"
MASTERCARD = re.compile(r"master\s*card", re.I)

# Template TS -> existing Test Solution BR IDs (topic/element match), coverage, note.
TEMPLATE_MAP = {
    "TS-001": (["BR-SEG100-GAP-ACCOUNT", "BR-RULE-SEG100-R-041", "BR-SEG100-APPO-TOKEN-ACCOUNT", "BR-SEG100-APPO-TOKEN-LUHN", "BR-SEG100-APPO-TOKEN-NOT-PAN"],
               "PARTIAL", "PAN presence covered; Luhn only for payment tokens; no Mastercard BIN-range or card-status BR."),
    "TS-002": (["BR-SEG100-GAP-DISCRETIONARY", "BR-RULE-SEG100-R-042", "BR-SEG100-APPO-TOKEN-EXPIRATION"],
               "PARTIAL", "Template cites Element 51; ATL105 carries card expiry in Element 12 (Card Discretionary Block Data). No 'not past current date' BR."),
    "TS-003": ([], "NOT IN TEST SOLUTION", "No card blocked/lost/stolen/expired status BR (issuer-side behaviour)."),
    "TS-004": (["BR-SEG100-E78-001", "BR-SEG100-E78-002", "BR-RULE-SEG100-R-018", "BR-RULE-SEG100-R-033", "BR-SEG100-APPE-PROMPT-COMPOSITION", "BR-SEG100-ANN-G", "BR-APPG-CODE-0"],
               "MAPPED", "Template 'Processing Code 020' corresponds to ATL105 Element 78 Prompt Code (transaction type 0 + card type)."),
    "TS-005": (["BR-SEG100-E2-001", "BR-RULE-SEG100-R-034", "BR-SEG100-EMV-001"],
               "PARTIAL", "Entry-method consistency and EMV Segment 130 covered; no allow-list BR for Swipe/Contactless/Manual/E-Commerce."),
    "TS-006": (["BR-SEG100-E102-001", "BR-SEG100-E102-003", "BR-RULE-SEG100-R-017", "BR-RULE-SEG100-R-031"],
               "PARTIAL", "Terminal Identifier (Element 102) covered; no Merchant-active or MCC BR."),
    "TS-007": (["BR-SEG100-E55-001"], "MAPPED", "Template cites Element 55 = ATL105 Message Format Version Identifier."),
    "TS-008": (["BR-SEG100-E63-001", "BR-RULE-SEG100-R-007"], "MAPPED", ""),
    "TS-009": (["BR-RULE-SEG100-R-001", "BR-SEG100-ID-001", "BR-RULE-SEG100-R-015"], "MAPPED", ""),
    "TS-010": (["BR-SEG100-E2-002", "BR-RULE-SEG100-R-035"],
               "PARTIAL", "Template cites Element 24; ATL105 Account Number is Element 2. No explicit numeric/no-delimiter/no-control-char BR."),
    "TS-011": (["BR-SEG100-APPR-CROSS-FIELD"], "PARTIAL", "Only EMV authorized-amount cross-field agreement; no generic purchase-amount >0/currency/precision BR."),
    "TS-012": (["BR-SEG100-GAP-FUEL-AMOUNT", "BR-RULE-SEG100-R-045"], "MAPPED", ""),
    "TS-013": (["BR-SEG100-GAP-NONFUEL-AMOUNT", "BR-RULE-SEG100-R-046"], "MAPPED", ""),
    "TS-014": (["BR-SEG100-GAP-TAX-AMOUNT", "BR-RULE-SEG100-R-047"], "MAPPED", ""),
    "TS-015": (["BR-SEG100-GAP-CASH-AMOUNT", "BR-RULE-SEG100-R-048"], "MAPPED", ""),
    "TS-016": (["BR-SEG100-E86-001", "BR-RULE-SEG100-R-019"], "MAPPED", "Template cites Element 80; ATL105 Sequence Number is Element 86. Uniqueness not covered."),
    "TS-017": (["BR-SEG100-E86-002", "BR-RULE-SEG100-R-037", "BR-SEG100-LIFE-001", "BR-SEG100-LIFE-AUTH-COMPLETE", "BR-SEG100-LIFE-SALE-VOID", "BR-SEG100-LIFE-AUTH-VOID", "BR-SEG100-LIFE-REFUND-VOID"],
               "MAPPED", "Settlement retention not covered."),
    "TS-018": (["BR-SEG100-APPE-FINANCIAL-CARD-TYPES", "BR-SEG100-SEC5-LEAF-CREDIT-NONPROPRIETARY"],
               "PARTIAL", "Card-type allow-list and credit classification exist; no BR that names the Mastercard card-type code."),
    "TS-019": ([], "NOT IN TEST SOLUTION", "No network routing BR."),
    "TS-020": (["BR-APPG-CODE-3"], "PARTIAL", "Authorization transaction type recognised; no 'all mandatory elements present' BR for the request."),
    "TS-021": (["BR-SEG100-ANN-H"], "PARTIAL", "Decline-code interpretation only; no response-format BR."),
    "TS-022": (["BR-SEG100-GAP-APPROVAL", "BR-RULE-SEG100-R-049"], "MAPPED", "Template cites Element 6; ATL105 Approval Number is Element 5."),
    "TS-023": (["BR-SEG100-LIFE-001"], "PARTIAL", "Reuse in lifecycle follow-ups; settlement use not covered."),
    "TS-024": (["BR-SEG100-RC-APPROVED-PURCHASE", "BR-SEG100-RC-APPROVED-AUTH", "BR-RULE-SEG100-R-063", "BR-RULE-SEG100-R-064"], "MAPPED", ""),
    "TS-025": (["BR-SEG100-RC-DECLINED", "BR-RULE-SEG100-R-066"], "MAPPED", ""),
    "TS-026": (["BR-SEG100-E121-001", "BR-RULE-SEG100-R-020"], "PARTIAL", "Indicator value covered; merchant-enabled condition not covered."),
    "TS-027": (["BR-SEG100-RC-PARTIAL", "BR-RULE-SEG100-R-065"], "PARTIAL", "Approved amount carried; customer balance prompt not covered."),
    "TS-028": ([], "NOT IN TEST SOLUTION", "No 30-second timer BR."),
    "TS-029": ([], "NOT IN TEST SOLUTION", "No timeout-detection BR."),
    "TS-030": (["BR-SEG100-LIFE-TIMEOUT-TOR"], "PARTIAL", "TOR sequence reuse only; generation trigger not covered."),
    "TS-031": (["BR-SEG100-LIFE-TIMEOUT-TOR"], "MAPPED", ""),
    "TS-032": ([], "NOT IN TEST SOLUTION", "No TOR-before-next-financial-message BR."),
    "TS-033": (["BR-SEG100-RECEIPT-CARDTYPE-MASTERCARD"], "PARTIAL", "Receipt Card Type ID MC only."),
    "TS-034": ([], "NOT IN TEST SOLUTION", "No clearing BR."),
    "TS-035": ([], "NOT IN TEST SOLUTION", "No settlement preparation BR."),
    "TS-036": ([], "NOT IN TEST SOLUTION", "No settlement reporting BR."),
    "TS-037": ([], "NOT IN TEST SOLUTION", "No audit logging BR."),
}
# Existing Mastercard-named purchase BRs with no template TS; listed under FR-002 Request Construction.
EXTRA_PURCHASE = ["BR-SEG100-APPY-MASTERCARD-UCAF-DSRP", "BR-SEG100-APPY-ECI-CRYPTOGRAM", "BR-RULE-SEG123-R-005", "BR-RULE-SEG123-R-011"]
EXTRA_FR = "FR-002"

# Detail-section scope: messages a Mastercard credit purchase uses, minus BRs specific to other products/brands.
MC_SEGMENTS = {"100", "FINANCIAL-RESPONSE", "111", "123", "130", "131"}
MC_SECTION5 = {"BR-SEG100-SEC5-NETWORK", "BR-SEG100-SEC5-LEAF-CREDIT-NONPROPRIETARY"}
EXCLUDE_ID = re.compile(r"^BR-(TX9-|TXV-|TX-[0-9A-Z]-|SEG100-EWIC-|SEG100-SEC5-)")
EXCLUDE_TEXT = re.compile(r"\be?WIC\b|\bEBT\b|Conoco|\bAmex\b|American Express|SafeKey|\bVisa\b|\bTAVV\b|MICR"
                          r"|TeleCheck|ECA/|recognized as SPECIALIZED|\bfleet\b|loyalty", re.I)
# FR for detail BRs not mapped to a template TS: first keyword match wins, otherwise FR-002.
FR_RULES = [
    ("FR-002", re.compile(r"POS Entry Mode|state code", re.I)),
    ("FR-001", re.compile(r"^EMV Card Sequence Number", re.I)),
    ("FR-007", re.compile(r"time-?out|\bTOR\b", re.I)),
    ("FR-006", re.compile(r"partial approval", re.I)),
    ("FR-008", re.compile(r"receipt", re.I)),
    ("FR-005", re.compile(r"response|declin|approv|Segment 131|originates at BUYPASS", re.I)),
    ("FR-004", re.compile(r"sequence number|lifecycle|\bvoid\b|completion|reversal|credit (card|type)|leaf values", re.I)),
    ("FR-003", re.compile(r"amount|currency", re.I)),
    ("FR-001", re.compile(r"account number|\bPAN\b|payment token|expiration|discretionary|card number|LUHN", re.I)),
]
OUTCOME_TEXT = {
    "PASS": "Validation passes - message accepted",
    "FAIL": "Validation fails - message rejected",
    "PASS_AND_FAIL": "Valid variant accepted; invalid variant rejected",
    "PASS_OR_REVIEW": "Message accepted, or flagged for review",
    "PASS_WITH_REVIEW": "Message accepted, with review",
    "FAIL_OR_REVIEW": "Message rejected, or flagged for review",
    "REVIEW_REQUIRED": "Expected outcome pending SME review",
    "SOURCE_BEHAVIOR_REVIEW_REQUIRED": "Expected outcome pending source-behaviour review",
    "REVIEW": "Expected outcome pending review",
    "COVERED": "Covered by source evidence",
}
POLARITY = {"PASS": "Positive", "FAIL": "Negative", "PASS_AND_FAIL": "Positive and negative"}
EXTRA_HEADER = ["Coverage Status", "Test Solution BR IDs", "Test Solution TS IDs", "Test Solution TC IDs",
                "Test Solution TD IDs", "TC Expected Outcome", "Note"]
EXTRA_WIDTHS = [22, 34, 38, 44, 44, 22, 60]
ACRONYMS = {"emv": "EMV", "pan": "PAN", "tlv": "TLV", "nfc": "NFC", "ucaf": "UCAF", "dsrp": "DSRP", "aav": "AAV",
            "eci": "ECI", "pin": "PIN", "dukpt": "DUKPT", "ksn": "KSN", "pos": "POS", "bin": "BIN", "tor": "TOR",
            "ansi": "ANSI", "aid": "AID", "3ds": "3DS", "luhn": "LUHN", "mc": "MC", "rc": "RC", "id": "ID",
            "ca": "CA", "tx": "TX", "mastercard": "Mastercard", "tavv": "TAVV", "cavv": "CAVV", "us": "US"}
SMALL_WORDS = {"and", "or", "of", "to", "in", "on", "for", "is", "the", "by", "per", "not", "with", "when", "vs"}
NO_TS = "[No TS in Test Solution]"
NO_TC = "[No TC in Test Solution]"


def read_template():
    ws = openpyxl.load_workbook(ts_fr.TEMPLATE).worksheets[0]
    items, fr_id, fr_name, ts = [], None, None, None
    for row in ws.iter_rows(min_row=3, values_only=True):
        if row[2]:
            fr_id, fr_name = row[2].strip(), row[3].strip()
        if row[4]:
            ts = {"fr_id": fr_id, "fr": fr_name, "ts_id": row[4].strip(), "ts": row[5].strip(),
                  "ac": (row[6] or "").strip(), "validations": [], "expected": (row[8] or "").strip()}
            items.append(ts)
        if row[7] and ts:
            ts["validations"].append(str(row[7]).strip())
    return items


def chain_rows(br_ids, br_by_id, br_text, ts_by_br, tc_by_ts, td_by_tc):
    brs = [br_by_id[i] for i in br_ids]
    linked = {k: v for k, v in ts_by_br.items() if k != ts_fr.NO_BR}
    return ts_fr.build_rows(brs, br_text, linked, tc_by_ts, td_by_tc)


def in_mc_scope(br, text, mapped):
    if br["id"] in mapped or MASTERCARD.search(json.dumps(br, ensure_ascii=False)):
        return True
    if br["id"] in MC_SECTION5:
        return True
    if EXCLUDE_ID.search(br["id"]) or EXCLUDE_TEXT.search(text):
        return False
    return bool({str(a.get("segment")) for a in ts_fr.anchors(br)} & MC_SEGMENTS)


def classify_fr(text):
    for fr_id, rx in FR_RULES:
        m = rx.search(text)
        if m:
            return fr_id, f"keyword '{m.group(0)}'"
    return "FR-002", "message construction (no other FR keyword)"


def payload_sample(td, limit=3):
    leaves = []

    def walk(node, path):
        if len(leaves) >= limit:
            return
        if isinstance(node, dict):
            for k, v in node.items():
                walk(v, k)
        elif isinstance(node, list):
            for v in node[:1]:
                walk(v, path)
        elif path and not re.search(r"status|reason|note", path, re.I):
            v = str(node)
            leaves.append(f"{path}='{v[:40]}{'...' if len(v) > 40 else ''}'")

    walk((td or {}).get("payload"), "")
    return ", ".join(leaves)


def validation_text(tc, tds):
    detail = ts_fr.tc_text(tc)
    if detail:
        return f"{tc['id']}: {detail}"
    outcome = tc.get("expectedOutcome") or ""
    a = ts_fr.anchors(tc)
    scope = ts_fr.anchor_text(a[0], with_section=False) if a else ""
    parts = [POLARITY.get(outcome, "Review"), scope]
    sample = payload_sample(tds[0]) if tds else ""
    text = " - ".join(p for p in parts if p)
    return f"{tc['id']}: {text}" + (f" ({sample})" if sample else "")


def expected_text(tc):
    outcome = tc.get("expectedOutcome") or ""
    base = OUTCOME_TEXT.get(outcome, outcome or ts_fr.NO_TEXT)
    return f"{base}: {tc['then']}" if tc.get("then") else base


def outcome_summary(tcs):
    counts = OrderedDict()
    for tc in tcs:
        o = tc.get("expectedOutcome") or "?"
        counts[o] = counts.get(o, 0) + 1
    return "\n".join(f"{k} x{v}" for k, v in counts.items())


def scenario_title(br, text):
    """Template-style scenario name ('Account Number Validation') from the BR's source rule, else its wording."""
    anchors = ts_fr.anchors(br)
    rules = [a["rule"] for a in anchors if a.get("rule")]
    if rules:
        parts = [w for w in rules[0].split("-") if w]
        words = [ACRONYMS.get(w.lower()) or (w.lower() if i and w.lower() in SMALL_WORDS else w[:1].upper() + w[1:])
                 for i, w in enumerate(parts)]
        name = " ".join(words)
        seg = next((s for a in anchors for s in str(a.get("segment") or "").split(",") if s.isdigit()), None)
        if seg and seg != "100" and seg not in name.split():
            name = f"Segment {seg} {name[len('Segment '):]}" if name.startswith("Segment ") else f"Segment {seg} {name}"
        return name if re.search(r"validation$", name, re.I) else f"{name} Validation"
    short = re.split(r";|\s-\s|: ", text.strip())[0].rstrip(".")
    return short if len(short) <= 100 else short[:97].rsplit(" ", 1)[0] + "..."


def coverage_cells(ids, ts_by_br, tc_by_ts, td_by_tc, status, note):
    ts_list = list(OrderedDict((t["id"], t) for i in ids for t in ts_by_br.get(i, [])).values())
    tc_list = list(OrderedDict((tc["id"], tc) for t in ts_list for tc in tc_by_ts.get(t["id"], [])).values())
    td_ids = list(OrderedDict.fromkeys(td["id"] for tc in tc_list for td in td_by_tc.get(tc["id"], [])))
    return [status, "\n".join(ids), "\n".join(t["id"] for t in ts_list), "\n".join(tc["id"] for tc in tc_list),
            "\n".join(td_ids), outcome_summary(tc_list), note]


def new_ts_rows(ts_no, name, br_ids, br_by_id, br_text, ts_by_br, tc_by_ts, td_by_tc, fr_basis):
    """One template-style scenario per scenario name (Test Solution BRs sharing a source rule are merged);
    one validation row per Test Solution TC."""
    texts = list(OrderedDict.fromkeys(br_text[b][0].strip() for b in br_ids))
    ts_list = list(OrderedDict((t["id"], t) for b in br_ids for t in ts_by_br.get(b, [])).values())
    tcs = list(OrderedDict((tc["id"], tc) for t in ts_list for tc in tc_by_ts.get(t["id"], [])).values())
    cites = list(OrderedDict.fromkeys(ts_fr.anchor_text(a) for t in ts_list for a in ts_fr.anchors(t))) \
        or list(OrderedDict.fromkeys(ts_fr.anchor_text(a) for b in br_ids for a in ts_fr.anchors(br_by_id[b])))
    ac = "\n".join(texts) + (f"\n(ATL105 {'; '.join(cites)})" if cites else "")
    statuses = ", ".join(OrderedDict.fromkeys(t["status"] for t in ts_list if t.get("status")))
    basis = "; ".join(OrderedDict.fromkeys(fr_basis[b] for b in br_ids))
    note = f"FR basis: {basis}" + (f"; TS status: {statuses}" if statuses else "")
    rows = []
    for i, tc in enumerate(tcs or [None]):
        tds = td_by_tc.get(tc["id"], []) if tc else []
        row = [None] * (9 + len(EXTRA_HEADER))
        if i == 0:
            row[4:7] = [f"TS-{ts_no:03d}", name, ac]
            row[9:12] = ["TEST SOLUTION ONLY", "\n".join(br_ids), "\n".join(t["id"] for t in ts_list) or NO_TS]
            row[15] = note
        row[7] = validation_text(tc, tds) if tc else NO_TC
        row[8] = expected_text(tc) if tc else ts_fr.NO_TEXT
        row[12:15] = [tc["id"] if tc else "", "\n".join(td["id"] for td in tds), (tc or {}).get("expectedOutcome") or ""]
        rows.append(row)
    return rows


def build_main_rows(template_body, new_by_fr, br_by_id, br_text, ts_by_br, tc_by_ts, td_by_tc, fr_basis):
    """Template rows unchanged (A-I) + coverage (J-P); each FR's template TSs are followed by its new TSs."""
    out, is_template, fr_id = [], [], None
    ts_no = max(int(r[4].strip().split("-")[1]) for r in template_body if r[4])

    def flush(fr):
        nonlocal ts_no
        by_name = OrderedDict()
        for br_id in new_by_fr.get(fr, []):
            by_name.setdefault(scenario_title(br_by_id[br_id], br_text[br_id][0]), []).append(br_id)
        for name, br_ids in by_name.items():
            ts_no += 1
            for row in new_ts_rows(ts_no, name, br_ids, br_by_id, br_text, ts_by_br, tc_by_ts, td_by_tc, fr_basis):
                out.append(row)
                is_template.append(False)

    for r in template_body:
        if r[2]:
            if fr_id:
                flush(fr_id)
            fr_id = r[2].strip()
        extra = [None] * len(EXTRA_HEADER)
        if r[4]:
            ids, status, note = TEMPLATE_MAP[r[4].strip()]
            extra = coverage_cells(ids, ts_by_br, tc_by_ts, td_by_tc, status, note)
        out.append(list(r) + extra)
        is_template.append(True)
    flush(fr_id)
    return [[ts_fr.clean(v) if v != "" else None for v in row] for row in out], is_template


def write_table(wb, title, header, rows, header_style, body_style, widths):
    ws = wb.create_sheet(title)
    for c, name in enumerate(header, start=1):
        ws.cell(1, c, name)._style = copy.copy(header_style)
    for r, row in enumerate(rows, start=2):
        for c, v in enumerate(row, start=1):
            ws.cell(r, c, ts_fr.clean(v))._style = copy.copy(body_style)
    ws.freeze_panes = "A2"
    ws.auto_filter.ref = ws.dimensions
    for c, w in enumerate(widths, start=1):
        ws.column_dimensions[openpyxl.utils.get_column_letter(c)].width = w
    return ws


def build_model():
    """Selects the Mastercard Credit Purchase scope and builds the FR-matrix rows without writing any file."""
    pkg, brs, tss, tcs, tds, br_text, ts_by_br, tc_by_ts, td_by_tc = ts_fr.load()
    # A TS may list the same BR more than once in requirementIds; keep each TS once per BR.
    for k, v in ts_by_br.items():
        ts_by_br[k] = list(OrderedDict((t["id"], t) for t in v).values())
    br_by_id = {b["id"]: b for b in brs}
    template = read_template()
    assert [t["ts_id"] for t in template] == sorted(TEMPLATE_MAP), "template TS list changed"
    for ids, _, _ in TEMPLATE_MAP.values():
        missing = [i for i in ids if i not in br_by_id]
        assert not missing, f"BR not in Test Solution: {missing}"
    assert all(i in br_by_id for i in EXTRA_PURCHASE)

    # FR groups in template order; each BR placed once, under the first template TS that maps to it.
    placed, groups = set(), OrderedDict()
    for t in template:
        grp = groups.setdefault(t["fr_id"], (t["fr"], []))
        for br_id in TEMPLATE_MAP[t["ts_id"]][0]:
            if br_id not in placed:
                placed.add(br_id)
                grp[1].append(br_id)
        if t["fr_id"] == EXTRA_FR:
            for br_id in EXTRA_PURCHASE:
                if br_id not in placed:
                    placed.add(br_id)
                    grp[1].append(br_id)
    group_list = [(k, v[0], v[1]) for k, v in groups.items() if v[1]]
    other_mc = [b["id"] for b in brs if b["id"] not in placed and MASTERCARD.search(br_text[b["id"]][0])]

    # Detail section: template-mapped BRs keep their template FR; other in-scope BRs get a keyword-derived FR.
    template_refs = OrderedDict()
    for t in template:
        for br_id in TEMPLATE_MAP[t["ts_id"]][0]:
            template_refs.setdefault(br_id, []).append(t["ts_id"])
    fr_names = OrderedDict((t["fr_id"], t["fr"]) for t in template)
    fr_basis = {}
    detail = OrderedDict((fr_id, []) for fr_id in fr_names)
    for fr_id, _, br_ids in group_list:
        for br_id in br_ids:
            detail[fr_id].append(br_id)
            fr_basis[br_id] = (f"template TS {', '.join(template_refs[br_id])}" if br_id in template_refs
                               else "Mastercard-specific purchase BR (no template TS)")
    for b in brs:
        if b["id"] not in placed and in_mc_scope(b, br_text[b["id"]][0], placed):
            fr_id, fr_basis[b["id"]] = classify_fr(br_text[b["id"]][0])
            detail[fr_id].append(b["id"])
    detail_groups = [(k, fr_names[k], v) for k, v in detail.items() if v]
    detail_ids = [i for _, _, ids in detail_groups for i in ids]

    template_ws = openpyxl.load_workbook(ts_fr.TEMPLATE).worksheets[0]
    template_body = [list(r) for r in template_ws.iter_rows(min_row=3, max_col=9, values_only=True)
                     if any(v not in (None, "") for v in r)]
    new_by_fr = OrderedDict((k, [b for b in v if b not in template_refs]) for k, v in detail.items())
    rows, is_template = build_main_rows(template_body, new_by_fr, br_by_id, br_text, ts_by_br, tc_by_ts, td_by_tc, fr_basis)
    return {
        "pkg": pkg, "brs": brs, "br_text": br_text, "br_by_id": br_by_id,
        "ts_by_br": ts_by_br, "tc_by_ts": tc_by_ts, "td_by_tc": td_by_tc,
        "template": template, "template_body": template_body, "template_refs": template_refs,
        "groups": groups, "placed": placed, "other_mc": other_mc, "fr_names": fr_names, "fr_basis": fr_basis,
        "detail": detail, "detail_ids": detail_ids, "new_by_fr": new_by_fr, "rows": rows, "is_template": is_template,
    }


def main():
    m = build_model()
    br_by_id, br_text, ts_by_br, tc_by_ts, td_by_tc = (m[k] for k in ("br_by_id", "br_text", "ts_by_br", "tc_by_ts", "td_by_tc"))
    template, template_body, groups, placed, other_mc = (m[k] for k in ("template", "template_body", "groups", "placed", "other_mc"))
    detail, detail_ids, new_by_fr, rows, is_template = (m[k] for k in ("detail", "detail_ids", "new_by_fr", "rows", "is_template"))

    wb = openpyxl.load_workbook(ts_fr.TEMPLATE)
    ws = wb.worksheets[0]
    header_style = copy.copy(ws.cell(2, 1)._style)
    body_style = [copy.copy(ws.cell(3, c)._style) for c in range(1, 10)]
    template_header = [c.value for c in ws[2]]

    ws.delete_rows(3, ws.max_row)
    for c, name in enumerate(EXTRA_HEADER, start=10):
        ws.cell(2, c, name)._style = copy.copy(header_style)
    for r, row in enumerate(rows, start=3):
        for c, v in enumerate(row, start=1):
            ws.cell(r, c, v)._style = copy.copy(body_style[c - 1] if c <= 9 else body_style[0])
    for c, w in enumerate(EXTRA_WIDTHS, start=10):
        ws.column_dimensions[openpyxl.utils.get_column_letter(c)].width = w
    _, trace = chain_rows(detail_ids, br_by_id, br_text, ts_by_br, tc_by_ts, td_by_tc)

    # Conformance check: every template TS against the Test Solution.
    check_rows = []
    for t in template:
        ids, status, note = TEMPLATE_MAP[t["ts_id"]]
        ts_ids = list(OrderedDict.fromkeys(ts["id"] for i in ids for ts in ts_by_br.get(i, [])))
        tc_ids = list(OrderedDict.fromkeys(tc["id"] for s in ts_ids for tc in tc_by_ts.get(s, [])))
        check_rows.append([t["fr_id"], t["fr"], t["ts_id"], t["ts"], t["ac"], "\n".join(t["validations"]), t["expected"],
                           status, "\n".join(ids), "\n".join(ts_ids), "\n".join(tc_ids), note])
    for br_id in EXTRA_PURCHASE:
        ts_ids = [ts["id"] for ts in ts_by_br.get(br_id, [])]
        tc_ids = [tc["id"] for s in ts_ids for tc in tc_by_ts.get(s, [])]
        check_rows.append([EXTRA_FR, groups[EXTRA_FR][0], "-", "-", "-", "-", "-", "IN TEST SOLUTION ONLY",
                           br_id, "\n".join(ts_ids), "\n".join(tc_ids), "Mastercard-specific BR with no template TS."])
    write_table(wb, "Template Conformance Check",
                ["Template FR ID", "Template FR", "Template TS ID", "Template Test Scenario", "Template Acceptance Criteria",
                 "Template Validations", "Template Expected Result", "Coverage Status", "Test Solution BR IDs",
                 "Test Solution TS IDs", "Test Solution TC IDs", "Note"],
                check_rows, header_style, body_style[0], [9, 18, 9, 30, 36, 40, 28, 22, 40, 44, 50, 60])

    trace_cols = [k for k in trace[0] if k not in ("TD_Payload", "TD_Expected")]
    write_table(wb, "BR-TS-TC-TD Traceability", trace_cols, [[r[k] for k in trace_cols] for r in trace],
                header_style, body_style[0], [30] * len(trace_cols))

    if other_mc:
        other = wb.copy_worksheet(ws)
        other.title = "Other Mastercard BRs"
        other.delete_rows(3, other.max_row)
        other.delete_cols(10, len(EXTRA_HEADER))
        matrix, _ = chain_rows(other_mc, br_by_id, br_text, ts_by_br, tc_by_ts, td_by_tc)
        for r, row in enumerate(matrix, start=3):
            for c, v in enumerate(row, start=1):
                other.cell(r, c, v)._style = copy.copy(body_style[c - 1])

    wb.save(OUT_XLSX)
    with open(OUT_CSV, "w", newline="", encoding="utf-8-sig") as fh:
        writer = csv.DictWriter(fh, fieldnames=list(trace[0].keys()))
        writer.writeheader()
        writer.writerows(r for r in trace if r["TD_ID"])

    out = openpyxl.load_workbook(OUT_XLSX, read_only=True)
    out_rows = [list(r) for r in out.worksheets[0].iter_rows(min_row=2, values_only=True)][:1 + len(rows)]
    assert out_rows[0][:9] == template_header, "header differs from template"
    body = out_rows[1:]
    assert [r[:9] for r, t in zip(body, is_template) if t] == template_body, "template rows changed"
    assert [r[0] for r in body if r[0]] == [template_body[0][0]], "BR column must hold only the template business BR"
    covered = {i for r in body if r[10] for i in r[10].split("\n")}
    assert covered >= set(detail_ids), f"BRs missing from matrix: {set(detail_ids) - covered}"
    for r, t in zip(body, is_template):
        if not t:
            if r[9]:
                assert r[4] and r[5] and r[6], f"TS/name/AC incomplete for {r[10]}"
            assert r[7] and r[8], f"validation/expected missing near {r[12]}"
    statuses = [row[7] for row in check_rows]
    new_ts = sum(1 for r, t in zip(body, is_template) if not t and r[4])
    per_fr = {k: len(v) for k, v in new_by_fr.items()}
    print(f"Matrix rows {len(body)} | template TS 37 + new TS {new_ts} (TS-038..TS-{37 + new_ts:03d}) "
          f"| Test Solution BRs covered {len(detail_ids)} | TS {len({r['TS_ID'] for r in trace})} "
          f"| TC {len({r['TC_ID'] for r in trace if r['TC_ID']})} | TD {len({r['TD_ID'] for r in trace if r['TD_ID']})} "
          f"| other Mastercard BRs {len(other_mc)}")
    print("Test Solution BRs added as new TS, per FR:", per_fr)
    print("Template TS coverage:", {s: statuses.count(s) for s in OrderedDict.fromkeys(statuses)})
    print(f"Wrote {OUT_XLSX}\nWrote {OUT_CSV}")


if __name__ == "__main__":
    main()
