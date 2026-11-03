"""Reformat the canonical Test Solution BR/TS/TC/TD package into the approved Mastercard FR Matrix layout.

Nothing is generated: every ID, link and text value is read from
specifications/ATL105/test-output/test-solution-independent-review/complete-all-test-solution-br-ts-tc-td-package.json
(BR wording missing there is looked up by ruleId in the segment rule catalogs).
"""
import copy
import csv
import json
import re
from collections import OrderedDict, defaultdict
from pathlib import Path

import openpyxl

ROOT = Path(__file__).resolve().parents[1]
ATL = ROOT / "specifications" / "ATL105"
PACKAGE = ATL / "test-output" / "test-solution-independent-review" / "complete-all-test-solution-br-ts-tc-td-package.json"
CATALOG_GLOB = "docs/specs/kb/*/coverage/*-rule-catalog.json"
TEMPLATE = ROOT.parent / "Mastercard_Credit_Purchase_FR_Matrix (1) 1.xlsx"
OUT_DIR = ATL / "test-output" / "test-solution-fr-matrix"
OUT_XLSX = OUT_DIR / "ATL105_Test_Solution_FR_Matrix.xlsx"
OUT_CSV = OUT_DIR / "TestData.csv"

NO_TITLE = "[Title not in source]"
NO_TEXT = "[Not provided in source]"
NO_BR = "[No BR link in source]"
ILLEGAL = re.compile(r"[\x00-\x08\x0b\x0c\x0e-\x1f]")


def clean(value):
    return ILLEGAL.sub("", value) if isinstance(value, str) else value


def anchors(item):
    found = list(item.get("sourceAnchors") or [])
    if item.get("sourceAnchor"):
        found.append(item["sourceAnchor"])
    return found


def anchor_text(a, with_section=True):
    parts = [f"§{a['section']}"] if with_section and a.get("section") else []
    if a.get("segment"):
        parts.append(f"Segment {a['segment']}")
    if a.get("element"):
        parts.append(f"Element {a['element']}")
    if a.get("rule"):
        parts.append(a["rule"])
    return " · ".join(parts)


def rule_catalog_titles():
    titles = {}
    for f in ATL.glob(CATALOG_GLOB):
        for rule in json.loads(f.read_text(encoding="utf-8")).get("rules") or []:
            if rule.get("ruleId") and rule.get("title"):
                titles[rule["ruleId"]] = (rule["title"], f.relative_to(ROOT).as_posix())
    return titles


def ts_name(ts, br_text):
    if ts.get("title"):
        return ts["title"], "source title"
    for br_id in ts.get("requirementIds") or []:
        text = br_text.get(br_id, (NO_TEXT,))[0]
        if text != NO_TEXT:
            return f"Verify {text.strip().rstrip('.')}", f"derived from BR {br_id}"
    rules = [a["rule"] for a in anchors(ts) if a.get("rule")]
    if rules:
        return f"Verify {rules[0].replace('-', ' ')}", "derived from AC rule"
    return NO_TITLE, "none"


def tc_text(tc):
    if tc.get("title"):
        return tc["title"]
    if tc.get("given") or tc.get("then"):
        return f"Given {tc.get('given', '')} When {tc.get('when', '')} Then {tc.get('then', '')}".strip()
    for key in ("positive", "assertionScope", "testedContext", "scope"):
        if tc.get(key):
            return tc[key]
    return ""


def load():
    pkg = json.loads(PACKAGE.read_text(encoding="utf-8"))
    brs, tss, tcs, tds = (pkg[k] for k in ("businessRequirements", "testScenarios", "testCases", "testData"))
    for name, items in (("BR", brs), ("TS", tss), ("TC", tcs), ("TD", tds)):
        ids = [i["id"] for i in items]
        assert len(ids) == len(set(ids)), f"duplicate {name} IDs in source"

    catalog = rule_catalog_titles()
    br_text = {}
    for br in brs:
        if br.get("title"):
            br_text[br["id"]] = (br["title"], "package.title")
        elif br.get("ruleId") in catalog:
            br_text[br["id"]] = (catalog[br["ruleId"]][0], f"rule-catalog:{catalog[br['ruleId']][1]}#{br['ruleId']}")
        else:
            br_text[br["id"]] = (NO_TEXT, "none")

    ts_by_br = defaultdict(list)
    for ts in tss:
        for br_id in ts.get("requirementIds") or [NO_BR]:
            ts_by_br[br_id].append(ts)

    tc_by_ts = defaultdict(list)
    for tc in tcs:
        for ts_id in OrderedDict.fromkeys((tc.get("scenarioIds") or []) + ([tc["scenarioId"]] if tc.get("scenarioId") else [])):
            tc_by_ts[ts_id].append(tc)

    td_index = {td["id"]: td for td in tds}
    td_by_tc = defaultdict(list)
    for td in tds:
        for tc_id in td.get("testCaseIds") or []:
            td_by_tc[tc_id].append(td)
    for tc in tcs:
        for td_id in tc.get("testDataIds") or []:
            if td_id in td_index and td_index[td_id] not in td_by_tc[tc["id"]]:
                td_by_tc[tc["id"]].append(td_index[td_id])

    return pkg, brs, tss, tcs, tds, br_text, ts_by_br, tc_by_ts, td_by_tc


def build_rows(brs, br_text, ts_by_br, tc_by_ts, td_by_tc):
    matrix, trace = [], []
    br_list = [(br["id"], br) for br in brs]
    if ts_by_br.get(NO_BR):
        br_list.append((NO_BR, {}))
    for br_id, br in br_list:
        text, text_source = br_text.get(br_id, (NO_TEXT, "none"))
        br_anchors = anchors(br)
        fr_id = "\n".join(OrderedDict.fromkeys(f"§{a['section']}" for a in br_anchors if a.get("section"))) or NO_TEXT
        fr_text = "\n".join(OrderedDict.fromkeys(anchor_text(a, with_section=False) for a in br_anchors)) or NO_TEXT
        first_in_br = True
        for ts in ts_by_br.get(br_id, []):
            acceptance = "\n".join(anchor_text(a) for a in anchors(ts)) or NO_TEXT
            name, name_source = ts_name(ts, br_text)
            tc_list = tc_by_ts.get(ts["id"], [])
            for i, tc in enumerate(tc_list or [None]):
                row = [None] * 9
                if first_in_br:
                    row[0:4] = [br_id, text, fr_id, fr_text]
                    first_in_br = False
                if i == 0:
                    row[4:7] = [ts["id"], name, acceptance]
                if tc:
                    detail = tc_text(tc)
                    row[7] = f"{tc['id']}: {detail}" if detail else tc["id"]
                    row[8] = tc.get("expectedOutcome") or NO_TEXT
                matrix.append([clean(v) for v in row])

                for td in (td_by_tc.get(tc["id"], []) if tc else []) or [None]:
                    a = (anchors(td) if td else []) or anchors(tc or {}) or anchors(ts)
                    trace.append(OrderedDict([
                        ("BR_ID", br_id),
                        ("BR_Status", br.get("status") or ""),
                        ("BR_Text_Source", text_source),
                        ("TS_ID", ts["id"]),
                        ("TS_Name", name),
                        ("TS_Name_Source", name_source),
                        ("TS_Status", ts.get("status") or ""),
                        ("TS_Training_Status", ts.get("trainingStatus") or ""),
                        ("TC_ID", tc["id"] if tc else ""),
                        ("TC_Status", (tc or {}).get("status") or ""),
                        ("TC_Training_Status", (tc or {}).get("trainingStatus") or ""),
                        ("TC_Expected_Outcome", (tc or {}).get("expectedOutcome") or ""),
                        ("TC_Priority", (tc or {}).get("priority") or ""),
                        ("TD_ID", td["id"] if td else ""),
                        ("TD_Readiness", (td or {}).get("readiness") or ""),
                        ("TD_Expected_Validation", (td or {}).get("expectedValidation") or ""),
                        ("TD_Status", (td or {}).get("status") or ""),
                        ("TD_Training_Status", (td or {}).get("trainingStatus") or ""),
                        ("TD_Producer", (td or {}).get("producer") or ""),
                        ("TD_Origin", (td or {}).get("origin") or ""),
                        ("TD_Fixture_Kind", (td or {}).get("fixtureKind") or ""),
                        ("TD_Covers_BR", (td or {}).get("coversBr") or ""),
                        ("TD_Fixture_File", (td or {}).get("fixtureFile") or (td or {}).get("fileName") or ""),
                        ("Source_Section", "; ".join(str(x.get("section", "")) for x in a)),
                        ("Source_Segment", "; ".join(str(x.get("segment", "")) for x in a)),
                        ("Source_Element", "; ".join(str(x.get("element", "")) for x in a)),
                        ("Source_Rule", "; ".join(str(x.get("rule", "")) for x in a)),
                        ("TD_Expected", json.dumps(td["expected"], ensure_ascii=False) if td and td.get("expected") else ""),
                        ("TD_Payload", json.dumps(td["payload"], ensure_ascii=False) if td and "payload" in td else ""),
                    ]))
    return matrix, trace


def write_workbook(matrix, trace, pkg):
    wb = openpyxl.load_workbook(TEMPLATE)
    ws = wb.worksheets[0]
    header_style = copy.copy(ws.cell(2, 1)._style)
    body_style = [copy.copy(ws.cell(3, c)._style) for c in range(1, 10)]
    ws.delete_rows(3, ws.max_row)
    for r, row in enumerate(matrix, start=3):
        for c, value in enumerate(row, start=1):
            ws.cell(r, c, value)._style = copy.copy(body_style[c - 1])

    trace_cols = [k for k in trace[0] if k not in ("TD_Payload", "TD_Expected")]
    sheet = wb.create_sheet("BR-TS-TC-TD Traceability")
    for c, name in enumerate(trace_cols, start=1):
        sheet.cell(1, c, name)._style = copy.copy(header_style)
    for r, row in enumerate(trace, start=2):
        for c, name in enumerate(trace_cols, start=1):
            sheet.cell(r, c, clean(row[name]))._style = copy.copy(body_style[0])
    sheet.auto_filter.ref = sheet.dimensions
    sheet.freeze_panes = "A2"
    for c in range(1, len(trace_cols) + 1):
        sheet.column_dimensions[openpyxl.utils.get_column_letter(c)].width = 30

    unlinked = (pkg.get("provenance") or {}).get("unlinkedTestDataCandidates") or []
    if unlinked:
        u = wb.create_sheet("Unlinked TD (source)")
        cols = ["sourceArtifactId", "disposition", "reason", "candidateTestCaseIds", "candidateResolution", "sourceFile"]
        for c, name in enumerate(cols, start=1):
            u.cell(1, c, name)._style = copy.copy(header_style)
        for r, item in enumerate(unlinked, start=2):
            for c, name in enumerate(cols, start=1):
                v = item.get(name)
                u.cell(r, c, clean("; ".join(v) if isinstance(v, list) else v))._style = copy.copy(body_style[0])
        u.freeze_panes = "A2"

    OUT_DIR.mkdir(parents=True, exist_ok=True)
    wb.save(OUT_XLSX)


def write_csv(trace):
    with open(OUT_CSV, "w", newline="", encoding="utf-8-sig") as fh:
        writer = csv.DictWriter(fh, fieldnames=list(trace[0].keys()))
        writer.writeheader()
        writer.writerows(r for r in trace if r["TD_ID"])


def validate(brs, tss, tcs, tds, trace):
    template_header = [c.value for c in openpyxl.load_workbook(TEMPLATE).worksheets[0][2]]
    ws = openpyxl.load_workbook(OUT_XLSX, read_only=True).worksheets[0]
    rows = list(ws.iter_rows(min_row=2, values_only=True))
    assert list(rows[0]) == template_header, "header differs from template"
    out_br = {r[0] for r in rows[1:] if r[0]} - {NO_BR}
    out_ts = {r[4] for r in rows[1:] if r[4]}
    out_tc = {r[7].split(":", 1)[0] for r in rows[1:] if r[7]}
    assert out_br == {b["id"] for b in brs}, "BR set mismatch"
    assert out_ts == {t["id"] for t in tss}, "TS set mismatch"
    assert out_tc == {t["id"] for t in tcs}, "TC set mismatch"
    td_out = {r["TD_ID"] for r in trace if r["TD_ID"]}
    assert td_out == {t["id"] for t in tds}, "TD set mismatch"
    print(f"OK: BR {len(out_br)} | TS {len(out_ts)} | TC {len(out_tc)} | TD {len(td_out)} "
          f"| matrix rows {len(rows) - 1} | trace rows {len(trace)} | header matches template")


def main():
    pkg, brs, tss, tcs, tds, br_text, ts_by_br, tc_by_ts, td_by_tc = load()
    matrix, trace = build_rows(brs, br_text, ts_by_br, tc_by_ts, td_by_tc)
    write_workbook(matrix, trace, pkg)
    write_csv(trace)
    validate(brs, tss, tcs, tds, trace)
    names = [ts_name(t, br_text)[1] for t in tss]
    print(f"BR text from rule catalog: {sum(1 for v in br_text.values() if v[1].startswith('rule-catalog'))}; "
          f"BR without text: {sum(1 for v in br_text.values() if v[1] == 'none')}; "
          f"TS names: source {names.count('source title')}, from BR {sum(n.startswith('derived from BR') for n in names)}, "
          f"from AC rule {names.count('derived from AC rule')}, none {names.count('none')}")
    print(f"Wrote {OUT_XLSX}\nWrote {OUT_CSV}")


if __name__ == "__main__":
    main()
