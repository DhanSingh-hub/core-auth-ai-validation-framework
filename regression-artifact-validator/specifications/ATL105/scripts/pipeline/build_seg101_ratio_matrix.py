"""Build Segment 101 Coverage Ratio matrix (JSON) and refreshed HTML report.

Inputs
------
- Test-solution rule catalog (26 rules) + AI coverage report:
    docs/specs/kb/segment-101/coverage/segment-101-ai-coverage-report.json
- AI requirement catalog:
    C:/.../src_Harit_Latest_AI_Sol/src/pipeline/step5_requirements/approved/requirement_catalog.json
- AI approved scenarios:
    C:/.../src_Harit_Latest_AI_Sol/src/pipeline/scenarios/approved/approved_scenarios.json
- AI approved test-case catalog (~467 MB, streamed with ijson):
    C:/.../src_Harit_Latest_AI_Sol/src/pipeline/test_generation/approved/test_case_catalog.json

Outputs
-------
- test-output/ai-artifacts/coverage-reports/POC-Segment-101-Coverage-Ratio-Matrix.json  (BR -> scenarios -> test cases -> test data)
- test-output/ai-artifacts/coverage-reports/POC-Segment-101-Coverage-Ratio-AI-vs-Test-Solution.html  (re-titled visual report)
"""
from __future__ import annotations

import html
import json
import sys
from datetime import datetime, timezone, timedelta
from decimal import Decimal
from pathlib import Path

import ijson


class _DecimalEncoder(json.JSONEncoder):
    """ijson emits Decimal for numeric leaves; keep them as JSON numbers."""

    def default(self, o):
        if isinstance(o, Decimal):
            return float(o)
        return super().default(o)

BASE = Path(r"c:\Users\F7OKAGW\OneDrive - Fiserv Corp\Desktop\core-auth-ai-validation-framework\regression-artifact-validator")
AI_PIPELINE = Path(r"C:\Users\F7OKAGW\Downloads\src_Harit_Latest_AI_Sol\src\pipeline")

COVERAGE_JSON = BASE / "docs" / "specs" / "kb" / "segment-101" / "coverage" / "segment-101-ai-coverage-report.json"
REQ_CATALOG_JSON = AI_PIPELINE / "step5_requirements" / "approved" / "requirement_catalog.json"
SCENARIO_CATALOG_JSON = AI_PIPELINE / "scenarios" / "approved" / "approved_scenarios.json"
TESTCASE_CATALOG_JSON = AI_PIPELINE / "test_generation" / "approved" / "test_case_catalog.json"

OUT_JSON = BASE / "test-output" / "ai-artifacts" / "coverage-reports" / "POC-Segment-101-Coverage-Ratio-Matrix.json"
OUT_HTML = BASE / "test-output" / "ai-artifacts" / "coverage-reports" / "POC-Segment-101-Coverage-Ratio-AI-vs-Test-Solution.html"

IST = timezone(timedelta(hours=5, minutes=30))


def load_json(path: Path):
    with path.open("r", encoding="utf-8") as f:
        return json.load(f)


def collect_wanted_ids(coverage: dict) -> tuple[set[str], set[str], set[str]]:
    wanted_reqs: set[str] = set()
    wanted_scenarios: set[str] = set()
    wanted_test_cases: set[str] = set()
    for rule in coverage["ruleCoverage"]:
        ev = rule.get("aiEvidence", {})
        wanted_reqs.update(ev.get("requirementIds", []))
        wanted_scenarios.update(ev.get("scenarioIds", []))
        wanted_test_cases.update(ev.get("testCaseIdsSample", []))
    for r in coverage.get("unmappedAiRequirements", []):
        wanted_reqs.add(r["id"])
    return wanted_reqs, wanted_scenarios, wanted_test_cases


def build_requirement_lookup(wanted_reqs: set[str]) -> dict[str, dict]:
    catalog = load_json(REQ_CATALOG_JSON)
    lookup: dict[str, dict] = {}
    for req in catalog["requirements"]:
        if req["id"] in wanted_reqs:
            lookup[req["id"]] = {
                "id": req["id"],
                "statement": req.get("statement"),
                "sourceRuleId": req.get("source_rule_id"),
                "sourcePage": req.get("source_page"),
                "derivationMethod": req.get("derivation_method"),
                "confidenceScore": req.get("confidence_score"),
                "requiresReview": req.get("requires_review", False),
                "flags": req.get("flags", []),
            }
    return lookup


def build_scenario_lookup(wanted_scenarios: set[str]) -> dict[str, dict]:
    catalog = load_json(SCENARIO_CATALOG_JSON)
    lookup: dict[str, dict] = {}
    for sc in catalog["scenarios"]:
        if sc["id"] in wanted_scenarios:
            name_parts = (sc.get("name") or "").splitlines()
            display = " ".join(part.strip() for part in name_parts if part.strip())
            lookup[sc["id"]] = {
                "id": sc["id"],
                "displayName": display[:400] + ("..." if len(display) > 400 else ""),
                "type": sc.get("type"),
                "requirementId": sc.get("requirement_id"),
                "expectedResponseCode": sc.get("expected_response_code"),
                "expectedDeclineCode": sc.get("expected_decline_code"),
                "oracleAuthority": sc.get("oracle_authority"),
                "source": sc.get("source"),
                "flags": sc.get("flags", []),
            }
    return lookup


def stream_test_cases(wanted_test_cases: set[str]) -> dict[str, dict]:
    """Stream the 467MB test_case_catalog and pull only the ones we care about."""
    lookup: dict[str, dict] = {}
    remaining = set(wanted_test_cases)
    with TESTCASE_CATALOG_JSON.open("rb") as f:
        items = ijson.items(f, "test_cases.item")
        for tc in items:
            tid = tc.get("id")
            if tid in remaining:
                lookup[tid] = extract_test_case(tc)
                remaining.discard(tid)
                if not remaining:
                    break
    return lookup


def extract_test_case(tc: dict) -> dict:
    """Compact test-case record with the Fleet Data Segment slice retained."""
    request = tc.get("request") or {}
    fleet_segment = request.get("Fleet Data Segment") or {}
    fleet_fields = {}
    for name, meta in fleet_segment.items():
        if not isinstance(meta, dict):
            continue
        fleet_fields[name] = {
            "elementNo": meta.get("element_no"),
            "value": meta.get("value"),
            "method": meta.get("method"),
            "flags": meta.get("flags", []),
        }
    return {
        "id": tc.get("id"),
        "scenarioId": tc.get("scenario_id"),
        "scenarioType": tc.get("scenario_type"),
        "transactionType": tc.get("transaction_type"),
        "priority": tc.get("priority"),
        "priorityScore": tc.get("priority_score"),
        "requirementId": tc.get("requirement_id"),
        "violatedElementName": tc.get("violated_element_name"),
        "testData": {
            "fleetDataSegment": fleet_fields,
            "requestSegments": list(request.keys()),
        },
    }


def build_matrix(coverage: dict, reqs: dict, scenarios: dict, test_cases: dict) -> dict:
    matrix_rows = []
    for rule in coverage["ruleCoverage"]:
        rule_id = rule["ruleId"]
        ev = rule.get("aiEvidence", {})

        tcs_by_scenario: dict[str, list[dict]] = {}
        for tid in ev.get("testCaseIdsSample", []):
            tc = test_cases.get(tid)
            if not tc:
                continue
            tcs_by_scenario.setdefault(tc.get("scenarioId") or "UNKNOWN", []).append(tc)

        ai_requirements = []
        for rid in ev.get("requirementIds", []):
            req = reqs.get(rid)
            if not req:
                continue
            related_scenarios = []
            for sid in ev.get("scenarioIds", []):
                sc = scenarios.get(sid)
                if not sc:
                    continue
                if sc.get("requirementId") != rid:
                    continue
                related_scenarios.append(build_scenario_entry(sc, tcs_by_scenario.get(sid, [])))
            ai_requirements.append({
                "aiBrId": req["id"],
                "aiBrSource": req.get("sourceRuleId"),
                "statement": req.get("statement"),
                "sourcePage": req.get("sourcePage"),
                "confidenceScore": req.get("confidenceScore"),
                "requiresReview": req.get("requiresReview"),
                "flags": req.get("flags", []),
                "testScenarios": related_scenarios,
                "testScenarioCount": len(related_scenarios),
            })

        assigned_scenarios = {s["scenarioId"] for r in ai_requirements for s in r["testScenarios"]}
        unattached = []
        for sid in ev.get("scenarioIds", []):
            if sid in assigned_scenarios:
                continue
            sc = scenarios.get(sid)
            if not sc:
                continue
            unattached.append(build_scenario_entry(sc, tcs_by_scenario.get(sid, [])))

        matrix_rows.append({
            "testSolutionBR": {
                "id": rule_id,
                "title": rule.get("title"),
                "class": rule.get("class"),
                "severity": rule.get("severity"),
                "sourceAnchor": rule.get("sourceAnchor", {}),
                "provisionalItems": rule.get("provisionalItems", []),
                "status": rule.get("status"),
            },
            "aiCoverageCounts": {
                "aiRequirements": ev.get("requirementCount", 0),
                "aiScenarios": ev.get("scenarioCount", 0),
                "aiTestCases": ev.get("testCaseCount", 0),
                "aiPositiveTestCases": ev.get("positiveTestCases", 0),
                "aiNegativeTestCases": ev.get("negativeTestCases", 0),
                "aiTestCaseSampleSize": len(ev.get("testCaseIdsSample", [])),
            },
            "aiBusinessRequirements": ai_requirements,
            "unattachedAiScenarios": unattached,
        })
    return {"matrix": matrix_rows}


def build_scenario_entry(sc: dict, tcs: list) -> dict:
    return {
        "scenarioId": sc["id"],
        "type": sc.get("type"),
        "displayName": sc.get("displayName"),
        "expectedResponseCode": sc.get("expectedResponseCode"),
        "expectedDeclineCode": sc.get("expectedDeclineCode"),
        "source": sc.get("source"),
        "oracleAuthority": sc.get("oracleAuthority"),
        "testCases": [{
            "testCaseId": tc["id"],
            "priority": tc.get("priority"),
            "priorityScore": tc.get("priorityScore"),
            "transactionType": tc.get("transactionType"),
            "scenarioType": tc.get("scenarioType"),
            "violatedElementName": tc.get("violatedElementName"),
            "testData": tc.get("testData"),
        } for tc in tcs],
        "testCaseCount": len(tcs),
    }


def compute_ratio(coverage: dict) -> dict:
    ai_totals = coverage["aiCatalogTotals"]
    cov = coverage["coverageSummary"]
    covered = cov["covered"] + cov["partiallyCovered"] + cov["reviewRequired"]
    total = cov["totalRules"]
    ai_br = ai_totals["seg101RequirementCount"]
    ts_br = total
    ratio_val = round(ai_br / ts_br, 3) if ts_br else None
    return {
        "aiSolution": {
            "totalBusinessRequirements": ai_br,
            "totalScenarios": ai_totals["seg101ScenarioCount"],
            "totalTestCases": ai_totals["seg101TestCaseCount"],
            "positiveTestCases": ai_totals["seg101TestCasesPositive"],
            "negativeTestCases": ai_totals["seg101TestCasesNegative"],
            "testCasesByPriority": ai_totals["seg101TestCasesByPriority"],
        },
        "testSolution": {
            "totalBusinessRequirements": ts_br,
            "coveredRules": cov["covered"],
            "partiallyCoveredRules": cov["partiallyCovered"],
            "reviewRequiredRules": cov["reviewRequired"],
            "missingRules": cov["missing"],
            "rulesWithAnyAiEvidence": covered,
            "rulesWithoutAiEvidence": total - covered,
        },
        "ratios": {
            "aiToTestBrRatio": f"{ai_br}:{ts_br}",
            "aiToTestBrRatioNumeric": ratio_val,
            "aiPerTestBr": f"{ratio_val}:1" if ratio_val else None,
            "testBrCoverageByAiPercent": round(covered * 100.0 / total, 2) if total else None,
            "testBrGapPercent": round((total - covered) * 100.0 / total, 2) if total else None,
        },
    }


MATCH_LABELS = {
    "COVERED": ("Fully covered", "#2e8b57"),
    "PARTIALLY_COVERED": ("Partially covered", "#087e8b"),
    "REVIEW_REQUIRED": ("Review required", "#bd7a00"),
    "MISSING": ("Missing", "#b74242"),
}


def _esc(s):
    if s is None:
        return ""
    return html.escape(str(s))


def render_html(coverage: dict, matrix: dict, ratio: dict, json_filename: str) -> str:
    ts = datetime.now(IST).strftime("%Y-%m-%dT%H:%M:%S%z")
    ts = ts[:-2] + ":" + ts[-2:]

    ai = ratio["aiSolution"]
    ts_b = ratio["testSolution"]
    r = ratio["ratios"]

    rows_html = "\n".join(render_matrix_row(row) for row in matrix["matrix"])
    unmapped_html = "".join(render_unmapped_row(u) for u in coverage.get("unmappedAiRequirements", []))

    return f"""<!doctype html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Segment 101 Coverage Ratio &mdash; AI Solution vs. Test Solution</title>
<style>
:root {{ --ink:#132238; --muted:#5b6777; --paper:#f6f3eb; --surface:#ffffff; --line:#d8d0c1;
        --teal:#087e8b; --green:#2e8b57; --gold:#bd7a00; --red:#b74242; --navy:#235789; }}
* {{ box-sizing:border-box; }}
body {{ margin:0; background:var(--paper); color:var(--ink); font:15px Georgia,'Times New Roman',serif; line-height:1.45; }}
header {{ padding:42px max(24px, calc((100vw - 1280px)/2)); background:var(--ink); color:#fff; border-bottom:7px solid var(--teal); }}
header h1 {{ font-family:Impact,Haettenschweiler,'Arial Narrow Bold',sans-serif; font-weight:400; margin:0; font-size:44px; line-height:1; }}
header h1 small {{ display:block; font-size:16px; letter-spacing:1px; color:#a4d3d8; margin-bottom:6px; }}
header p {{ max-width:960px; color:#d6e6ea; margin:14px 0 0; }}
main {{ max-width:1280px; margin:0 auto; padding:28px 24px 60px; }}
h2 {{ font-family:Impact,sans-serif; font-weight:400; font-size:26px; margin:0 0 12px; }}
h3 {{ font-family:Impact,sans-serif; font-weight:400; font-size:20px; margin:10px 0 6px; color:var(--navy); }}
.notice {{ padding:14px 17px; border-left:5px solid var(--gold); background:#fff7e6; margin-bottom:24px; }}
.stats {{ display:grid; grid-template-columns:repeat(4,minmax(160px,1fr)); gap:12px; margin-bottom:28px; }}
.stat {{ background:var(--surface); border:1px solid var(--line); padding:16px; min-height:110px; }}
.stat strong {{ display:block; font:38px/1 Impact,sans-serif; color:var(--navy); }}
.stat span {{ color:var(--muted); font-size:13px; }}
.stat.ai strong {{ color:var(--teal); }}
.stat.test strong {{ color:var(--green); }}
.stat.warn strong {{ color:var(--gold); }}
.stat.danger strong {{ color:var(--red); }}
.panel {{ background:var(--surface); border:1px solid var(--line); padding:20px; margin:24px 0; }}
.ratio-grid {{ display:grid; grid-template-columns:repeat(3,minmax(200px,1fr)); gap:14px; margin:12px 0 8px; }}
.ratio {{ padding:14px; border:1px solid var(--line); background:#fbfaf7; }}
.ratio b {{ display:block; font:34px/1 Impact,sans-serif; color:var(--navy); }}
.ratio span {{ display:block; color:var(--muted); font-size:12px; margin-top:6px; }}
.bar {{ height:22px; background:#e8e2d7; overflow:hidden; margin:8px 0; }}
.bar span {{ display:inline-block; height:100%; }}
.confirmed {{ background:var(--green); }} .review {{ background:var(--gold); }} .missing {{ background:var(--red); }}
.link {{ display:inline-block; margin-top:12px; padding:9px 14px; background:var(--teal); color:#fff; text-decoration:none; font:13px Consolas,monospace; }}
.link:hover {{ background:var(--navy); }}
table.matrix {{ width:100%; border-collapse:collapse; margin-top:12px; background:#fff; }}
table.matrix th {{ background:#e8eee9; text-align:left; font-size:12px; padding:9px; position:sticky; top:0; z-index:1; }}
table.matrix th, table.matrix td {{ border-bottom:1px solid #e6e0d6; padding:9px; vertical-align:top; font-size:13px; }}
table.matrix tr:nth-child(even) td {{ background:#fbfaf7; }}
code {{ display:block; color:#174a6e; font:12px Consolas,monospace; }}
small {{ display:block; color:var(--muted); margin-top:4px; }}
.badge {{ display:inline-block; font:11px Consolas,monospace; padding:3px 5px; color:#fff; margin-right:4px; }}
details {{ background:#fbfaf7; border-left:3px solid var(--teal); padding:8px 12px; margin:6px 0; }}
details summary {{ cursor:pointer; font-weight:bold; color:var(--navy); }}
.filters {{ display:flex; gap:10px; margin:12px 0; flex-wrap:wrap; }}
input,select {{ font:inherit; padding:8px; border:1px solid var(--line); background:#fff; }}
input {{ min-width:280px; }}
.legend {{ color:var(--muted); font-size:12px; display:flex; gap:14px; flex-wrap:wrap; }}
.legend i {{ display:inline-block; width:12px; height:12px; margin-right:5px; vertical-align:middle; }}
</style>
</head>
<body>
<header>
  <h1><small>POC AI VALIDATION FRAMEWORK &mdash; ATL105 / SEGMENT 101 (FLEET DATA)</small>Segment 101 Coverage Ratio &mdash; AI Solution vs. Test Solution</h1>
  <p>Compares the AI-generated business-requirement (BR) inventory for Segment 101 against the Test Solution's canonical
  26-rule catalog. Highlights the mapping ratio, the traceability from BR &rarr; test scenarios &rarr; test cases &rarr; test
  data, and where the AI evidence falls short. Generated {ts}.</p>
</header>
<main>
<div class="notice"><strong>Interpretation:</strong> AI and Test Solution use independent BR IDs. The AI solution emitted
<b>{ai['totalBusinessRequirements']}</b> BR statements and <b>{ai['totalTestCases']}</b> test cases for Segment 101 while the Test Solution
maintains a curated set of <b>{ts_b['totalBusinessRequirements']}</b> rules. This report projects the AI's requirements / scenarios / test-cases
onto each Test Solution rule and computes the coverage ratio.</div>

<section class="panel">
  <h2>Coverage Ratio</h2>
  <div class="ratio-grid">
    <div class="ratio"><b>{r['aiToTestBrRatio']}</b><span>AI BRs&nbsp;:&nbsp;Test Solution BRs</span></div>
    <div class="ratio"><b>{r['aiPerTestBr']}</b><span>Average AI BRs per Test Solution BR</span></div>
    <div class="ratio"><b>{r['testBrCoverageByAiPercent']}%</b><span>Test Solution BRs with AI evidence</span></div>
  </div>
  <div class="bar">
    <span class="confirmed" style="width:{r['testBrCoverageByAiPercent']}%"></span>
    <span class="missing" style="width:{r['testBrGapPercent']}%"></span>
  </div>
  <div class="legend">
    <span><i class="confirmed"></i>Test BRs with AI evidence ({ts_b['rulesWithAnyAiEvidence']} of {ts_b['totalBusinessRequirements']})</span>
    <span><i class="missing"></i>Test BRs without AI evidence ({ts_b['rulesWithoutAiEvidence']})</span>
  </div>
  <a class="link" href="./{json_filename}" download>Download BR &rarr; Scenario &rarr; Test Case &rarr; Test Data matrix (JSON)</a>
</section>

<section class="panel">
  <h2>Volume Snapshot</h2>
  <div class="stats">
    <div class="stat ai"><strong>{ai['totalBusinessRequirements']}</strong><span>AI BRs (Segment 101)</span></div>
    <div class="stat ai"><strong>{ai['totalScenarios']}</strong><span>AI test scenarios</span></div>
    <div class="stat ai"><strong>{ai['totalTestCases']}</strong><span>AI test cases (all P3)</span></div>
    <div class="stat danger"><strong>{ai['negativeTestCases']}</strong><span>AI negative test cases</span></div>
    <div class="stat test"><strong>{ts_b['totalBusinessRequirements']}</strong><span>Test Solution BRs</span></div>
    <div class="stat test"><strong>{ts_b['coveredRules']}</strong><span>Fully covered</span></div>
    <div class="stat warn"><strong>{ts_b['partiallyCoveredRules'] + ts_b['reviewRequiredRules']}</strong><span>Partial / review-required</span></div>
    <div class="stat danger"><strong>{ts_b['missingRules']}</strong><span>Missing (no AI evidence)</span></div>
  </div>
</section>

<section class="panel">
  <h2>BR &rarr; Test Scenario &rarr; Test Case &rarr; Test Data Matrix</h2>
  <div class="filters">
    <input id="search" placeholder="Search BR ID, rule, scenario, test case, or requirement text">
    <select id="statusFilter">
      <option value="">All coverage statuses</option>
      <option value="COVERED">Fully covered</option>
      <option value="PARTIALLY_COVERED">Partially covered</option>
      <option value="REVIEW_REQUIRED">Review required</option>
      <option value="MISSING">Missing</option>
    </select>
  </div>
  <div class="legend">
    <span><i class="confirmed"></i>fully covered</span>
    <span><i class="review"></i>review / partial</span>
    <span><i class="missing"></i>missing</span>
  </div>
  <table class="matrix" id="matrixTable">
    <thead><tr>
      <th style="width:22%">Test Solution BR</th>
      <th style="width:14%">AI evidence counts</th>
      <th>AI Business Requirement &rarr; Scenario &rarr; Test Case &rarr; Test Data</th>
    </tr></thead>
    <tbody>
{rows_html}
    </tbody>
  </table>
</section>

<section class="panel">
  <h2>Unmapped AI BRs (no Test Solution rule matched)</h2>
  <p>These AI-generated business requirements did not map to any of the 26 Test Solution rules. They should be triaged as
  either (a) legitimate coverage gaps in the Test Solution, or (b) AI noise/mis-derivation that must be discarded.</p>
  <table class="matrix">
    <thead><tr><th>AI BR ID</th><th>Source Rule</th><th>Confidence</th><th>Statement</th></tr></thead>
    <tbody>
      {unmapped_html}
    </tbody>
  </table>
</section>

</main>
<script>
const rows = Array.from(document.querySelectorAll('#matrixTable tbody tr'));
const q = document.getElementById('search');
const sf = document.getElementById('statusFilter');
function apply() {{
  const term = (q.value || '').toLowerCase();
  const status = sf.value;
  rows.forEach(r => {{
    const matchesText = !term || r.innerText.toLowerCase().includes(term);
    const matchesStatus = !status || r.dataset.status === status;
    r.style.display = (matchesText && matchesStatus) ? '' : 'none';
  }});
}}
q.addEventListener('input', apply);
sf.addEventListener('change', apply);
</script>
</body>
</html>
"""


def render_matrix_row(row: dict) -> str:
    ts = row["testSolutionBR"]
    counts = row["aiCoverageCounts"]
    status = ts["status"]
    label, colour = MATCH_LABELS.get(status, (status, "#5b6777"))
    provisional = ", ".join(ts.get("provisionalItems", []))
    anchor = ts.get("sourceAnchor", {})
    anchor_text = f"section {anchor.get('section','')} - element {anchor.get('element','n/a')}"
    ai_reqs_html = "".join(render_ai_requirement(r) for r in row["aiBusinessRequirements"])
    unattached_html = ""
    if row["unattachedAiScenarios"]:
        unattached_html = "<details><summary>Additional AI scenarios (not tied to a specific AI BR)</summary>"
        for sc in row["unattachedAiScenarios"]:
            unattached_html += render_scenario_block(sc)
        unattached_html += "</details>"
    if not ai_reqs_html and not unattached_html:
        ai_reqs_html = "<em>No AI evidence produced for this rule.</em>"
    return f"""<tr data-status='{_esc(status)}'>
  <td>
    <code>{_esc(ts['id'])}</code>
    <small><b>{_esc(ts['title'])}</b></small>
    <small>{_esc(ts['class'])} - {_esc(ts['severity'])} - {_esc(anchor_text)}</small>
    <small>Provisional: {_esc(provisional or 'none')}</small>
    <span class='badge' style='background:{colour}'>{_esc(label)}</span>
  </td>
  <td>
    <small>AI BRs: <b>{counts['aiRequirements']}</b></small>
    <small>AI scenarios: <b>{counts['aiScenarios']}</b></small>
    <small>AI test cases: <b>{counts['aiTestCases']}</b> (pos {counts['aiPositiveTestCases']} / neg {counts['aiNegativeTestCases']})</small>
    <small>Sampled test data: <b>{counts['aiTestCaseSampleSize']}</b></small>
  </td>
  <td>{ai_reqs_html}{unattached_html}</td>
</tr>"""


def render_ai_requirement(req: dict) -> str:
    scen_html = "".join(render_scenario_block(s) for s in req["testScenarios"])
    if not scen_html:
        scen_html = "<small><em>No direct scenario mapping recorded.</em></small>"
    confidence = req.get("confidenceScore")
    conf_str = f"conf {confidence}" if confidence is not None else "conf n/a"
    review_flag = "- review-required" if req.get("requiresReview") else ""
    return f"""<details>
  <summary><code>{_esc(req['aiBrId'])}</code> - {_esc(req.get('aiBrSource') or '')} - {_esc(conf_str)} {_esc(review_flag)}</summary>
  <small>{_esc(req.get('statement'))}</small>
  {scen_html}
</details>"""


def render_scenario_block(sc: dict) -> str:
    tcs = sc.get("testCases", [])
    if tcs:
        tc_html = "<table style='margin-top:4px;width:100%;font-size:12px;border-collapse:collapse'>"
        tc_html += "<tr><th style='text-align:left;background:#eef2ee;padding:4px'>Test case</th><th style='text-align:left;background:#eef2ee;padding:4px'>Priority</th><th style='text-align:left;background:#eef2ee;padding:4px'>Transaction</th><th style='text-align:left;background:#eef2ee;padding:4px'>Fleet-segment test data</th></tr>"
        for tc in tcs:
            data = tc.get("testData", {}) or {}
            fleet = data.get("fleetDataSegment") or {}
            if fleet:
                slices = []
                for name, val in fleet.items():
                    v = val.get("value")
                    v_str = "<em>omitted</em>" if v is None else _esc(str(v)[:40])
                    slices.append(f"<div><code>{_esc(name)}</code> = {v_str}</div>")
                fleet_html = "".join(slices)
            else:
                fleet_html = "<em>no Segment 101 payload in this test case</em>"
            tc_html += f"""<tr>
  <td style='padding:4px;border-top:1px solid #e6e0d6;vertical-align:top'><code>{_esc(tc['testCaseId'])}</code></td>
  <td style='padding:4px;border-top:1px solid #e6e0d6;vertical-align:top'>{_esc(tc.get('priority'))}<br><small>{_esc(tc.get('scenarioType'))}</small></td>
  <td style='padding:4px;border-top:1px solid #e6e0d6;vertical-align:top'>{_esc(tc.get('transactionType'))}</td>
  <td style='padding:4px;border-top:1px solid #e6e0d6;vertical-align:top'>{fleet_html}</td>
</tr>"""
        tc_html += "</table>"
    else:
        tc_html = "<small><em>No sampled test cases retained for this scenario.</em></small>"
    return f"""<div style='padding:6px 0 6px 12px;border-left:2px solid #ccd6d9;margin:6px 0'>
  <small><b>Scenario <code>{_esc(sc['scenarioId'])}</code></b> - {_esc(sc.get('type'))} - response <code>{_esc(sc.get('expectedResponseCode'))}</code> - decline <code>{_esc(sc.get('expectedDeclineCode'))}</code></small>
  <small>{_esc(sc.get('displayName'))}</small>
  {tc_html}
</div>"""


def render_unmapped_row(u: dict) -> str:
    return f"""<tr>
  <td><code>{_esc(u.get('id'))}</code></td>
  <td>{_esc(u.get('source_rule_id'))}</td>
  <td>{_esc(u.get('confidence_score'))}<br><small>{'review' if u.get('requires_review') else ''}</small></td>
  <td>{_esc(u.get('statement'))}<br><small>{_esc(', '.join(u.get('flags', []) or []))}</small></td>
</tr>"""


def main() -> int:
    print("Loading coverage report...")
    coverage = load_json(COVERAGE_JSON)
    wanted_reqs, wanted_scenarios, wanted_test_cases = collect_wanted_ids(coverage)
    print(f"  requirements: {len(wanted_reqs)}, scenarios: {len(wanted_scenarios)}, test cases: {len(wanted_test_cases)}")

    print("Loading requirement catalog...")
    reqs = build_requirement_lookup(wanted_reqs)
    print(f"  matched requirements: {len(reqs)}")

    print("Loading scenario catalog...")
    scenarios = build_scenario_lookup(wanted_scenarios)
    print(f"  matched scenarios: {len(scenarios)}")

    print("Streaming test-case catalog (this can take a minute)...")
    test_cases = stream_test_cases(wanted_test_cases)
    print(f"  matched test cases: {len(test_cases)}")

    ratio = compute_ratio(coverage)
    matrix = build_matrix(coverage, reqs, scenarios, test_cases)

    output = {
        "reportMetadata": {
            "title": "Segment 101 Coverage Ratio - AI Solution vs. Test Solution",
            "specification": coverage["specification"],
            "specificationVersion": coverage["specificationVersion"],
            "segment": coverage["segment"],
            "segmentName": coverage["segmentName"],
            "generatedAt": datetime.now(IST).isoformat(),
            "sources": coverage["generatedFrom"],
        },
        "coverageRatio": ratio,
        "matrix": matrix["matrix"],
        "unmappedAiRequirements": coverage.get("unmappedAiRequirements", []),
        "notes": coverage.get("notes", []),
    }

    print(f"Writing {OUT_JSON}...")
    with OUT_JSON.open("w", encoding="utf-8") as f:
        json.dump(output, f, indent=2, ensure_ascii=False, cls=_DecimalEncoder)

    print(f"Rendering {OUT_HTML}...")
    html_doc = render_html(coverage, matrix, ratio, OUT_JSON.name)
    with OUT_HTML.open("w", encoding="utf-8") as f:
        f.write(html_doc)

    print("Done.")
    return 0


if __name__ == "__main__":
    sys.exit(main())
