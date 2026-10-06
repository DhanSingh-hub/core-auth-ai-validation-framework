import argparse
import collections
import csv
import hashlib
import json
import re
import runpy
from pathlib import Path
from xml.sax.saxutils import escape


def build_coverage(root, run):
    import ijson
    archive = Path(run["archive"])
    catalogs = archive / "scenerio_req_5_oct" / "scenerio_req_5_oct"
    scenario_file = catalogs / "scenarios" / "approved" / "approved_scenarios.json"
    requirement_file = catalogs / "step5_requirements" / "approved" / "requirement_catalog.json"
    case_file = archive / "pipeline_run_artifacts" / "test_case_candidates.json"
    def read(name):
        return json.loads(name.read_text(encoding="utf-8-sig"))
    scenarios = {row["id"]: row for row in read(scenario_file)["scenarios"]}
    requirements = {row["id"]: row for row in read(requirement_file)["requirements"]}
    module = Path(__file__).resolve().parents[1]
    matrix_file = module / "specifications/ATL105/docs/specs/kb/element-83-response-code/coverage/element-83-response-code-br-validation-matrix.json"
    matrix = read(matrix_file)
    catalog_counts = {}
    catalog_files = sorted((module / "specifications/ATL105/docs/specs/kb").glob("segment-*/coverage/segment-*-rule-catalog.json"))
    for file in catalog_files:
        segment = file.name.removeprefix("segment-").removesuffix("-rule-catalog.json")
        catalog_counts[segment.upper()] = len(read(file).get("rules", []))
    def segments(row):
        values = list(row.get("segment_ids") or [])
        if row.get("segment_number"):
            values.extend(re.split(r"[,;|]", str(row["segment_number"])))
        result = set()
        for value in values:
            value = str(value).strip().upper().removeprefix("ENT-SEG-")
            result.add(value if re.fullmatch(r"\d+|DL[1-8]", value) else "UNRESOLVED")
        return result or {"UNASSIGNED"}
    def br_links(row):
        values = row.get("requirement_ids") or []
        if isinstance(values, str):
            values = [values]
        return set(values) | ({row["requirement_id"]} if row.get("requirement_id") else set())
    def percent(numerator, denominator):
        return round(100 * numerator / denominator, 2) if denominator else None
    br_segments = {key: segments(row) for key, row in requirements.items()}
    scenario_segments = {}
    for key, row in scenarios.items():
        scenario_segments[key] = set().union(*(br_segments.get(br, {"UNASSIGNED"}) for br in br_links(row))) or {"UNASSIGNED"}
    cases_by_scenario = collections.Counter()
    cases_by_br = collections.Counter()
    cases_by_segment = collections.defaultdict(set)
    templates = collections.Counter()
    expected_response_present = 0
    case_ids = set()
    with case_file.open("rb") as stream:
        for case in ijson.items(stream, "test_cases.item", use_float=True):
            case_id = case["id"]
            if case_id in case_ids:
                raise ValueError("Duplicate TC ID in coverage inputs")
            case_ids.add(case_id)
            cases_by_scenario[case["scenario_id"]] += 1
            links = br_links(case)
            cases_by_br.update(links)
            selected_segments = set().union(*(br_segments.get(br, {"UNASSIGNED"}) for br in links)) or {"UNASSIGNED"}
            for segment in selected_segments:
                cases_by_segment[segment].add(case_id)
            templates[case.get("transaction_type") or "NOT_DECLARED"] += 1
            expected_response_present += bool(case.get("expected_response"))
    if set(cases_by_scenario) - set(scenarios) or set(cases_by_br) - set(requirements):
        raise ValueError("Unknown scenario or BR in coverage input")
    segment_rows = []
    segment_names = set(catalog_counts) | set().union(*br_segments.values()) | set(cases_by_segment)
    for segment in sorted(segment_names, key=lambda value: (not value.isdigit(), int(value) if value.isdigit() else value)):
        br_ids = {key for key, values in br_segments.items() if segment in values}
        scenario_ids = {key for key, values in scenario_segments.items() if segment in values}
        linked_br = sum(bool(cases_by_br[key]) for key in br_ids)
        linked_ts = sum(bool(cases_by_scenario[key]) for key in scenario_ids)
        segment_rows.append({"segment": segment, "catalogRules": catalog_counts.get(segment),
            "aiBrs": len(br_ids), "brsWithTc": linked_br, "brLinkPercent": percent(linked_br, len(br_ids)),
            "scenarios": len(scenario_ids), "scenariosWithTc": linked_ts,
            "scenarioLinkPercent": percent(linked_ts, len(scenario_ids)),
            "tcCandidates": len(cases_by_segment[segment]), "independentCoverage": "NOT_CALCULABLE"})
    def scenario_groups(field):
        grouped = collections.defaultdict(list)
        for row in scenarios.values():
            value = row.get(field)
            grouped[str(value) if value is not None and str(value).strip() else "NOT_DECLARED"].append(row["id"])
        return grouped
    transaction_rows = []
    for target, ids in sorted(scenario_groups("target_transaction").items()):
        linked = sum(bool(cases_by_scenario[key]) for key in ids)
        transaction_rows.append({"target": target, "scenarios": len(ids), "scenariosWithTc": linked,
            "missingTc": len(ids) - linked, "linkPercent": percent(linked, len(ids)),
            "tcCandidates": sum(cases_by_scenario[key] for key in ids)})
    expected_groups = scenario_groups("expected_response_code")
    baseline_codes = {str(row["code"]) for row in matrix["allowedCodeFamilyMeanings"]}
    response_rows = []
    for code in sorted(baseline_codes | set(expected_groups)):
        ids = expected_groups.get(code, [])
        linked = sum(bool(cases_by_scenario[key]) for key in ids)
        response_rows.append({"code": code, "sourceFamilies": sorted({row["family"] for row in matrix["allowedCodeFamilyMeanings"] if str(row["code"]) == code}),
            "scenariosDeclaringCode": len(ids), "scenariosWithTc": linked,
            "tcLinkedToDeclarations": sum(cases_by_scenario[key] for key in ids),
            "validatedCodeFamilyCoverage": "NOT_ASSESSED"})
    covered_br = sum(bool(cases_by_br[key]) for key in requirements)
    covered_ts = sum(bool(cases_by_scenario[key]) for key in scenarios)
    totals = [
        {"metric": "AI BRs directly referenced by TCs", "numerator": covered_br, "denominator": len(requirements), "percent": percent(covered_br, len(requirements)), "meaning": "Producer-internal BR linkage"},
        {"metric": "Scenarios with at least one TC", "numerator": covered_ts, "denominator": len(scenarios), "percent": percent(covered_ts, len(scenarios)), "meaning": "Producer-internal scenario linkage"},
        {"metric": "TC candidates with physical outputs", "numerator": run["producerPayloadTraceability"]["totalTestCases"], "denominator": len(case_ids), "percent": percent(run["producerPayloadTraceability"]["totalTestCases"], len(case_ids)), "meaning": "Physical output presence, including flow legs"},
        {"metric": "TCs with nonempty expected response", "numerator": expected_response_present, "denominator": len(case_ids), "percent": percent(expected_response_present, len(case_ids)), "meaning": "Expected-response presence only"},
        {"metric": "Source code values declared as scenario expectations", "numerator": len(set(expected_groups) & baseline_codes), "denominator": len(baseline_codes), "percent": percent(len(set(expected_groups) & baseline_codes), len(baseline_codes)), "meaning": "Unique-code declaration inventory, not family/semantic coverage"},
    ]
    hashes = {}
    for file in (scenario_file, requirement_file, case_file, matrix_file, *catalog_files):
        with file.open("rb") as stream:
            hashes[file.name] = hashlib.file_digest(stream, "sha256").hexdigest()
    result = {"scope": "Producer-internal linkage and declaration inventory; not semantic or execution coverage",
        "inputSha256": hashes, "independentRuleCoverage": "NOT_CALCULABLE", "executionCertified": False,
        "catalogRuleInventory": sum(catalog_counts.values()), "catalogSegments": len(catalog_counts),
        "totals": totals, "segments": segment_rows, "transactionTargets": transaction_rows,
        "tcTemplateLabels": dict(templates), "responseCodes": response_rows,
        "responseCodeBaseline": {"uniqueCodes": len(baseline_codes), "allowedFamilyMeanings": len(matrix["allowedCodeFamilyMeanings"]), "status": matrix["status"]},
        "scenarioTypes": [{"type": kind, "scenarios": len(ids), "scenariosWithTc": sum(bool(cases_by_scenario[key]) for key in ids), "tcCandidates": sum(cases_by_scenario[key] for key in ids)} for kind, ids in sorted(scenario_groups("type").items())]}
    assert sum(row["scenarios"] for row in transaction_rows) == len(scenarios)
    assert sum(row["scenariosWithTc"] for row in transaction_rows) == covered_ts
    assert sum(row["scenariosDeclaringCode"] for row in response_rows) == len(scenarios)
    assert covered_br == run["caseReconciliation"]["linkedDistinctRequirements"]
    assert covered_ts == run["caseReconciliation"]["linkedDistinctScenarios"]
    (root / "coverage-view-assessment.json").write_text(json.dumps(result, indent=2, ensure_ascii=True), encoding="utf-8")
    return result


def export_pdf(root, data):
    from reportlab.lib import colors
    from reportlab.lib.enums import TA_LEFT
    from reportlab.lib.pagesizes import A4, landscape
    from reportlab.lib.styles import ParagraphStyle
    from reportlab.pdfbase import pdfmetrics
    from reportlab.pdfbase.ttfonts import TTFont
    from reportlab.platypus import SimpleDocTemplate, Paragraph, Spacer, Table, LongTable, TableStyle, PageBreak

    pdfmetrics.registerFont(TTFont("Assessment", "C:/Windows/Fonts/segoeui.ttf"))
    pdfmetrics.registerFont(TTFont("AssessmentBold", "C:/Windows/Fonts/segoeuib.ttf"))
    teal = colors.HexColor("#07665e")
    ink = colors.HexColor("#202526")
    line = colors.HexColor("#d8dede")
    heading = ParagraphStyle("Heading", fontName="AssessmentBold", fontSize=19, leading=24, textColor=teal, spaceAfter=12)
    body = ParagraphStyle("Body", fontName="Assessment", fontSize=9, leading=13, textColor=ink, spaceAfter=8)
    small = ParagraphStyle("Small", parent=body, fontSize=7, leading=10, spaceAfter=0)
    table_body = ParagraphStyle("TableBody", parent=body, fontSize=6.7, leading=8.8, spaceAfter=0, alignment=TA_LEFT)
    table_heading = ParagraphStyle("TableHeading", parent=table_body, fontName="AssessmentBold")
    story = []
    def paragraph(text, style=body):
        return Paragraph(escape(str(text)).replace("\n", "<br/>"), style)
    def heading_block(title):
        story.append(paragraph(title, heading))
    def table(headers, rows, widths, exhaustive=False):
        values = [[paragraph(value, table_heading) for value in headers]]
        values += [[paragraph(value, table_body) for value in row] for row in rows]
        result = (LongTable if exhaustive else Table)(values, colWidths=widths, repeatRows=1, hAlign="LEFT")
        result.setStyle(TableStyle([
            ("BACKGROUND", (0, 0), (-1, 0), colors.HexColor("#e9efec")),
            ("LINEBELOW", (0, 0), (-1, 0), 1, teal),
            ("LINEBELOW", (0, 1), (-1, -1), 0.25, line),
            ("VALIGN", (0, 0), (-1, -1), "TOP"),
            ("LEFTPADDING", (0, 0), (-1, -1), 5),
            ("RIGHTPADDING", (0, 0), (-1, -1), 5),
            ("TOPPADDING", (0, 0), (-1, -1), 5),
            ("BOTTOMPADDING", (0, 0), (-1, -1), 5),
        ]))
        story.append(result)
        story.append(Spacer(1, 12))
    heading_block("ATL105 | AI Artifact Assessment")
    story.append(paragraph("October 5 Run1 supplement | Assessed October 6, 2026 | REVIEW REQUIRED"))
    story.append(paragraph("Static snapshot: run summary, producer signals, all 3,732 no-TC scenarios and 27 failed writes. Interactive HTML filters do not alter this PDF's population."))
    table(["Measure", "Value"], [
        ["Supplied scenario inventory", "12,679"], ["Scenarios without TC", "3,732 (29.43%)"],
        ["Transaction unresolved", "2,026"], ["Response-side deferred", "1,706"],
        ["Overlap / unexplained gaps", "0 / 0"], ["TC candidates / logical cases with output", "21,123 / 21,096"],
        ["Physical payload / metadata pairs", "21,210"], ["Separate failed payload writes", "27"],
        ["Affected BRs / BRs without direct TC", "3,298 / 3,228"],
        ["Additional scenarios with TCs and deferred response variants", "580"],
        ["Hash-verified archived files", "42,436"], ["Execution certification", "Not established"],
        ["Independent coverage", "Not calculable"],
    ], [560, 210])
    story.append(paragraph("Recorded generation causes are not approved scope exclusions. All no-TC scenarios remain NOT_ASSESSED. A producer PASS, COMPLETE label, confidence score or parseable payload does not establish semantic validity or execution readiness."))
    story.append(PageBreak())
    heading_block("Work queues and quality signals")
    table(["Queue", "Evidence scope", "Next gate"], [
        ["Generation backlog", "Complete 3,732-row register", "Source-grounded transaction resolution or response scope/oracle"],
        ["Partial chain", "27 failed writes; 580 partial-response scenarios counted separately", "Repair TD writes and disposition response variants"],
        ["Producer review / source context", "Row-level filtering within the no-TC population", "Independent source assessment; not automatic rejection"],
        ["Technical validation", "Inventory and parsing only", "Supported producer adapter and applicable semantic validators"],
        ["Execution candidates", "No acceptance-qualified population established", "Validated behavior, expected outcomes, readiness and approval evidence"],
    ], [145, 285, 340])
    audit = data["run"]["producerAudit"]
    table(["Producer finding", "Findings", "Distinct cases"],
          [[key.replace("ERROR:", ""), str(count), str(audit["distinctCasesByFinding"][key])]
           for key, count in audit["findingCounts"].items()], [500, 135, 135])
    story.append(paragraph("225,091 self-audit findings across 21,063 cases. Populations overlap; these are producer signals, not independent business-defect decisions. All 21,123 candidate cases have null/empty expected responses. The composer reports exit 1."))
    story.append(paragraph("Scope limitation: this report does not execute host/converter messages or independently certify every source interpretation. Filters select records; they do not grant acceptance or silently reduce the original denominator."))
    story.append(PageBreak())
    if data.get("lateMatrix"):
        matrix = data["lateMatrix"]
        heading_block("Late requirement matrix | October 6 update")
        story.append(paragraph("Matrix received and independently reconciled: REVIEW_REQUIRED. This structural export does not confirm business equivalence or execution readiness."))
        story.append(paragraph(escape(" ".join(matrix["producer"]["limitations"])) + " The complete JSON companion was not supplied."))
        table(["Measure", "Previous", "AI matrix", "Frozen recount", "Current status"],
              [[row[key] for key in ["metric", "previous", "producer", "current", "status"]] for row in matrix["comparison"]],
              [175, 95, 150, 95, 180])
        independent = matrix["independent"]
        story.append(paragraph(f"Flat excerpt: {independent['leafRows']:,} rows; {independent['presentDeclaredDataPaths']:,} / {independent['uniqueDeclaredDataPaths']:,} declared TD paths present; {len(matrix['issues']):,} review issues. FULLY_TRACED on a no-TC/no-TD leaf is not accepted as a complete chain. Unrepresented rows are unavailable evidence, not identified missing links."))
        story.append(paragraph(escape("Gap-complement additional BRs: " + ", ".join(independent["matrixOnlyTracedBrIds"])) + f". The declared 3,661 / 6,887 equals {independent['producerNumeratorPercent']:.2f}%, not the printed 53.13%. Full-chain 53.2% remains a producer claim."))
        story.append(paragraph("Preserved matrix SHA-256: " + matrix["intake"]["sha256"]))
        if matrix.get("reconstruction"):
            rebuilt = matrix["reconstruction"]
            heading_block("Complete independent matrix reconstruction")
            table(["Measure", "Independent reconstruction"], [
                ["Frozen BRs represented", rebuilt["requirementsRepresented"]],
                ["Reconstructed BR-chain leaves", rebuilt["leafRows"]],
                ["Producer-declared rows / difference", f"{rebuilt['producerFlatRowsDeclared']:,} / {rebuilt['rowCountDelta']:+,}"],
                ["Physical-data-complete structural leaves", rebuilt["leafStatusCounts"].get("FULLY_TRACED", 0)],
                ["Scenario-only leaves", rebuilt["leafStatusCounts"].get("SCENARIO_ONLY", 0)],
                ["TC leaves missing data", rebuilt["leafStatusCounts"].get("TEST_CASE_NO_DATA", 0)],
                ["Detailed requirement statuses agreeing", f"{rebuilt['detailStatusAgreementCount']} / {len(rebuilt['detailStatusComparison'])}"],
                ["Leaf status disagreements", len(rebuilt["flatExcerptStatusDisagreements"])],
            ], [340, 350])
            story.append(paragraph("The 75 leaf differences are requirement-level FULLY_TRACED labels propagated onto independently SCENARIO_ONLY leaves, not 75 new missing-TD cases. The complete independent JSON/CSV reconstruct the frozen graph; they are not the omitted producer JSON and do not certify business meaning or execution."))
            story.append(paragraph("The 63-row difference and 63 fewer declared orphans are arithmetically consistent with additional attribution, but the missing producer edges are not supplied. The two disputed BR mappings remain review-required."))
        heading_block("Assessment history | Preserved before this update")
        story.append(paragraph(f"All {len(matrix['history']['files'])} previous assessment files were copied unchanged and hash-verified before this update. Prior run/semantic HTML reports, PDF, source registers and JSON evidence remain available under history/2026-10-06-before-late-matrix/."))
        story.append(paragraph("Independent coverage remains NOT_CALCULABLE; all previous semantic/host/SME blockers remain unresolved. See LATE-MATRIX-UPDATE.md and late-traceability-matrix-assessment.json for the status transition, intake provenance, discrepancies and snapshot hashes."))
        if matrix.get("supplementHistory"):
            story.append(paragraph(f"The earlier truncated-matrix-era assessment is separately preserved: {len(matrix['supplementHistory']['files'])} unchanged files under history/pre-reconciliation-20261006/. Both assessment history points retain recorded hashes."))

    heading_block("Coverage | Overall linkage and inventory")
    coverage = data["coverage"]
    def percentage(value):
        return "N/A" if value is None else f"{value:.2f}%"
    story.append(paragraph("Independent ATL105 rule coverage: NOT_CALCULABLE. Execution certification: not established. The percentages below measure producer-internal links or declared values only, not validated behavior."))
    table(["Metric", "Numerator", "Denominator", "Percent", "Meaning"],
          [[row["metric"], row["numerator"], row["denominator"], percentage(row["percent"]), row["meaning"]] for row in coverage["totals"]],
          [245, 70, 80, 75, 300])
    story.append(paragraph(f"Independent catalog inventory: {coverage['catalogRuleInventory']} rules across {coverage['catalogSegments']} segments. Catalog count is not an approved AI comparison denominator. Response baseline: {coverage['responseCodeBaseline']['uniqueCodes']} unique code values and {coverage['responseCodeBaseline']['allowedFamilyMeanings']} code/family meanings; status {coverage['responseCodeBaseline']['status']}."))
    heading_block("Coverage | Segment-attributed linkage")
    story.append(paragraph("Segments follow producer BR attribution. Scenarios inherit the union of linked BR segment labels; TCs are grouped by their direct BR references. Counts overlap across segments and must not be summed into a run total. Unassigned/unresolved records are retained. Catalog rules are a separate inventory, not the percentage denominator."))
    table(["Segment", "Catalog rules", "AI BRs", "BRs with TC", "BR link %", "Scenarios", "With TC", "TS link %", "TCs"],
          [[row["segment"], row["catalogRules"] if row["catalogRules"] is not None else "N/A", row["aiBrs"], row["brsWithTc"], percentage(row["brLinkPercent"]), row["scenarios"], row["scenariosWithTc"], percentage(row["scenarioLinkPercent"]), row["tcCandidates"]] for row in coverage["segments"]],
          [95, 85, 75, 85, 90, 85, 80, 90, 85], exhaustive=True)
    story.append(PageBreak())
    heading_block("Coverage | Transaction-target linkage")
    story.append(paragraph("Targets are exact producer scenario labels, not normalized ATL105 transaction codes. Case counts join by scenario ID. Naming variants remain separate until an evidence-backed alias mapping exists. NOT_DECLARED is retained; its scenarios are not discarded."))
    table(["Producer scenario target", "Scenarios", "With TC", "Without TC", "Link %", "TC candidates"],
          [[row["target"], row["scenarios"], row["scenariosWithTc"], row["missingTc"], percentage(row["linkPercent"]), row["tcCandidates"]] for row in coverage["transactionTargets"]],
          [355, 80, 80, 85, 80, 90], exhaustive=True)
    story.append(PageBreak())
    heading_block("Coverage | Response-code declarations")
    story.append(paragraph("Counts use scenario expected_response_code declarations, not keyword mentions or validated response payloads. Code 1 has different meanings across financial and communications-test families. A declared code does not establish the corresponding code/family coverage. All rows remain NOT_ASSESSED; absent declarations are not a semantic failure verdict."))
    table(["Code", "Source-derived families", "Scenario declarations", "With TC", "Linked TC candidates", "Validated coverage"],
          [[row["code"], ", ".join(row["sourceFamilies"]) or "Not applicable", row["scenariosDeclaringCode"], row["scenariosWithTc"], row["tcLinkedToDeclarations"], row["validatedCodeFamilyCoverage"]] for row in coverage["responseCodes"]],
          [80, 240, 115, 85, 125, 125], exhaustive=True)
    heading_block("Coverage | Scenario-type inventory")
    table(["Scenario type", "Scenarios", "With TC", "TC candidates"],
          [[row["type"], row["scenarios"], row["scenariosWithTc"], row["tcCandidates"]] for row in coverage["scenarioTypes"]],
          [400, 125, 120, 125])
    heading_block("Coverage input hashes")
    for name, digest in coverage["inputSha256"].items():
        story.append(paragraph(name + " | SHA-256 " + digest, small))
        story.append(Spacer(1, 4))
    story.append(PageBreak())
    heading_block("Appendix A | Complete no-TC register")
    story.append(paragraph("All 3,732 records. Independent disposition: NOT_ASSESSED. Full flags, response pairs and recommended actions remain available in the HTML evidence panel and original CSV/JSON registers."))
    table(["Scenario", "Generation cause", "Description / type", "BR IDs", "Source rule / page", "Producer verdict"],
          [[row["scenarioId"], "Transaction unresolved" if row["category"] == "NO_CONSISTENT_TRANSACTION" else "Response deferred",
            row["name"] + " / " + row["scenarioType"], "\n".join(row["requirementIds"]),
            (row["sourceRuleId"] or "No rule") + " / " + str(row["sourcePage"] if row["sourcePage"] is not None else "No page"),
            row["producerVerdict"]] for row in data["scenarios"]], [55, 80, 300, 145, 130, 60], exhaustive=True)
    story.append(PageBreak())
    heading_block("Appendix B | Failed negative-case payload writes")
    story.append(paragraph("27 candidate TCs without a base payload or metadata. The 89 flow candidates have step-specific files and are not included here."))
    table(["Test case", "Scenario", "Type", "Payload", "Metadata"],
          [[row["caseId"], row["scenarioId"], row["scenarioType"], "Missing", "Missing"] for row in data["failures"]],
          [155, 155, 150, 155, 155])
    heading_block("Evidence binding")
    for name, digest in data["summary"]["inputSha256"].items():
        story.append(paragraph(name + " | SHA-256 " + digest, small))
        story.append(Spacer(1, 4))
    def footer(canvas, document):
        canvas.saveState()
        canvas.setFont("Assessment", 8)
        canvas.setFillColor(colors.HexColor("#626b6c"))
        canvas.drawString(36, 18, "ATL105 Run1 | REVIEW REQUIRED | Independent coverage not established")
        canvas.drawRightString(806, 18, str(document.page))
        canvas.restoreState()
    target = root / "AI-ARTIFACT-FILTER-VIEW.pdf"
    document = SimpleDocTemplate(str(target), pagesize=landscape(A4), rightMargin=36, leftMargin=36,
                                 topMargin=32, bottomMargin=32, title="ATL105 AI Artifact Assessment", author="Test Solution")
    document.build(story, onFirstPage=footer, onLaterPages=footer)
    print(f"Generated {target}")


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("review_directory", type=Path)
    args = parser.parse_args()
    root = args.review_directory
    def load(name):
        return json.loads((root / name).read_text(encoding="utf-8"))
    with (root / "case-file-gaps.csv").open(encoding="utf-8", newline="") as stream:
        failures = [row for row in csv.DictReader(stream) if row["scenarioType"] != "flow"]
    data = {"summary": load("scenario-no-tc-assessment.json"),
            "run": load("complete-handoff-analysis.json"),
            "scenarios": load("scenario-no-tc-register.json"), "failures": failures}
    data["coverage"] = build_coverage(root, data["run"])
    matrix_file = root / "late-traceability-matrix-assessment.json"
    matrix_section = ""
    if matrix_file.is_file():
        matrix = load(matrix_file.name)
        data["lateMatrix"] = {key: matrix[key] for key in ["intake", "producer", "independent", "history", "issues", "comparison", "requiredFollowUp", "disposition", "reconstruction", "supplementHistory"] if key in matrix}
        matrix_section = runpy.run_path(str(Path(__file__).with_name("assess-late-traceability-matrix.py")))["report_section"](matrix)
    assert len(data["scenarios"]) == data["summary"]["scenariosWithoutTc"] == 3732
    assert len(failures) == 27
    encoded = json.dumps(data, ensure_ascii=True, separators=(",", ":")).replace("</", "<\\/")
    html = TEMPLATE.replace("__DATA__", encoded)
    if matrix_section:
        html = html.replace("<main>", '<main><section id="matrix-history" role="tabpanel" hidden>' + matrix_section + "</section>", 1)
        html = html.replace("</nav>", '<button role="tab" aria-selected="false" aria-controls="matrix-history" data-tab="matrix-history">Matrix &amp; history</button></nav>', 1)
    target = root / "AI-ARTIFACT-FILTER-VIEW.html"
    target.write_text(html, encoding="utf-8")
    print(f"Generated {target}: {len(data['scenarios'])} scenarios, {len(failures)} failed writes")
    export_pdf(root, data)


TEMPLATE = r'''<!doctype html>
<html lang="en"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1">
<title>ATL105 | AI Artifact Assessment</title>
<style>
:root{--ink:#202526;--muted:#626b6c;--line:#d8dede;--paper:#fff;--wash:#f3f5f4;--teal:#07665e;--amber:#9b6510;--red:#a43d35;--blue:#256e9d}
*{box-sizing:border-box}body{margin:0;background:repeating-linear-gradient(0deg,#f3f5f4 0,#f3f5f4 23px,#edf0ef 24px);color:var(--ink);font:14px Bahnschrift,"Segoe UI",sans-serif;letter-spacing:0}button,input,select{font:inherit}button,a,input,select{outline-offset:3px}header{padding:22px 28px 18px;background:var(--paper);border-top:5px solid var(--teal);border-bottom:1px solid var(--line)}.headline{display:flex;justify-content:space-between;gap:18px;align-items:center}h1{font:700 27px Georgia,serif;margin:4px 0 8px}h2{font-size:19px;margin:0 0 14px}h3{font-size:15px;margin:12px 0}.metadata{color:var(--muted);font-size:13px;line-height:1.7}.badge{display:inline-block;padding:5px 8px;border:1px solid #d1a862;background:#fff7e7;color:#825200;font-size:12px;font-weight:700;white-space:nowrap}.actions{display:flex;gap:8px;align-items:center}.icon{width:36px;height:36px;border:1px solid var(--line);border-radius:4px;background:white;font-size:20px;cursor:pointer}.icon:hover{background:#edf6f3}.command{padding:8px 12px;border:1px solid var(--line);border-radius:4px;background:white;cursor:pointer}.command:hover{background:#edf6f3}nav{display:flex;gap:0;padding:0 28px;background:var(--paper);border-bottom:1px solid var(--line);overflow:auto}nav button{padding:14px 16px;border:0;border-bottom:3px solid transparent;background:transparent;color:var(--muted);white-space:nowrap;cursor:pointer}nav button[aria-selected=true]{border-bottom-color:var(--teal);color:var(--teal);font-weight:700}main{max-width:1500px;margin:auto;padding:24px 28px}.metrics{display:grid;grid-template-columns:repeat(4,minmax(0,1fr));gap:0;background:white;border-block:1px solid var(--line);margin-bottom:24px}.metric{padding:18px;border-right:1px solid var(--line)}.metric:last-child{border:0}.metric strong{display:block;font-size:30px;margin:4px 0}.metric span{color:var(--muted);font-size:12px}.two-col{display:grid;grid-template-columns:1.25fr 1fr;gap:30px}.band{padding:20px 0;border-bottom:1px solid var(--line)}.plain-table{width:100%;border-collapse:collapse;background:white}.plain-table th{text-align:left;font-size:12px;color:var(--muted);font-weight:600;background:#edf1ef}.plain-table td,.plain-table th{padding:11px 12px;border-bottom:1px solid var(--line);vertical-align:top}.number{text-align:right!important;font-variant-numeric:tabular-nums}.bar{height:16px;background:#e3e9e7;width:100%;margin:8px 0 18px;display:flex}.bar .unresolved{background:var(--amber);width:54.29%}.bar .response{background:var(--blue);width:45.71%}.legend{display:flex;gap:20px;flex-wrap:wrap}.dot{display:inline-block;width:10px;height:10px;margin-right:6px}.notice{border-left:3px solid var(--amber);padding:12px 15px;background:#fff9ed;line-height:1.6;margin:16px 0}.scope{color:var(--muted);line-height:1.6;font-size:13px}.filters{display:grid;grid-template-columns:minmax(210px,1.7fr) repeat(3,minmax(150px,1fr)) auto;gap:12px;align-items:end;margin:18px 0}.field label{display:block;color:var(--muted);font-size:12px;margin-bottom:5px}.field input,.field select{width:100%;min-height:36px;border:1px solid #b9c3bf;border-radius:4px;background:white;padding:7px 9px;color:var(--ink)}.results-heading{display:flex;justify-content:space-between;gap:12px;align-items:center;margin:14px 0}.table-wrap{overflow:auto;border-block:1px solid var(--line);background:white}.register{width:100%;min-width:940px;border-collapse:collapse;table-layout:fixed}.register th{background:#e9efec;color:#4b5552;text-align:left;font-size:12px;position:sticky;top:0}.register td,.register th{padding:10px 12px;border-bottom:1px solid var(--line);vertical-align:top;overflow-wrap:anywhere}.register tr:hover td{background:#f0f7f4}.register th:nth-child(1){width:115px}.register th:nth-child(2){width:125px}.register th:nth-child(4){width:100px}.register th:nth-child(5){width:140px}.register th:nth-child(6){width:100px}.row-link{border:0;padding:0;background:none;color:var(--teal);font-weight:700;text-decoration:underline;cursor:pointer;text-align:left}.sub{display:block;color:var(--muted);font-size:12px;margin-top:5px}.tag{font-size:11px;font-weight:700}.tag.fail{color:var(--red)}.pager{display:flex;align-items:center;gap:12px;justify-content:flex-end;margin:14px 0}.pager button:disabled{opacity:.4;cursor:default}.evidence{margin-top:20px;padding:20px 0;border-top:2px solid var(--teal)}.evidence dl{display:grid;grid-template-columns:180px 1fr;gap:10px;margin:12px 0}.evidence dt{color:var(--muted)}.evidence dd{margin:0;overflow-wrap:anywhere;white-space:pre-wrap}.evidence ul{padding-left:20px;line-height:1.5}.empty{padding:35px;text-align:center;color:var(--muted)}footer{padding:20px 28px;color:var(--muted);font-size:12px;border-top:1px solid var(--line);background:white}a{color:var(--teal)}[hidden]{display:none!important}.print-only{display:none}
@media(max-width:850px){header,main,footer{padding-inline:16px}nav{padding-inline:0}.headline{align-items:flex-start;flex-wrap:wrap}h1{font-size:23px}.metrics{grid-template-columns:repeat(2,minmax(0,1fr))}.metric{border-bottom:1px solid var(--line)}.two-col{grid-template-columns:1fr;gap:15px}.filters{grid-template-columns:repeat(2,minmax(0,1fr))}.filters .field:first-child{grid-column:1/-1}.results-heading{align-items:flex-start;flex-wrap:wrap}.evidence dl{grid-template-columns:1fr}.evidence dd{margin-bottom:12px}}
@media(max-width:500px){.filters{grid-template-columns:minmax(0,1fr)}.filters .field:first-child{grid-column:auto}}
@media print{#coverage{display:block!important;break-before:page}#coverage .field{display:none}[data-coverage-panel]{display:block!important;margin-top:14px}}
@media print{@page{size:A4 landscape;margin:12mm}body{background:white;font-size:10px}header{padding:10px 0;border-top:3px solid var(--teal)}h1{font-size:22px}main{padding:14px 0;max-width:none}nav,.actions,.screen-only,footer,#missing,#writes,#quality{display:none!important}#overview{display:block!important}.metrics{margin-bottom:12px}.metric{padding:10px}.metric strong{font-size:24px}.two-col{gap:20px}.plain-table td,.plain-table th{padding:6px}.print-only{display:block;page-break-before:always}.print-table{width:100%;border-collapse:collapse;table-layout:fixed;font-size:8px}.print-table td,.print-table th{padding:5px;border-bottom:1px solid #bbb;text-align:left;vertical-align:top;overflow-wrap:anywhere}.print-table th{background:#e9efec}.print-table th:first-child{width:8%}.print-table th:nth-child(2){width:13%}.print-table th:nth-child(4){width:20%}.print-table th:nth-child(5){width:14%}.print-table th:nth-child(6){width:8%}thead{display:table-header-group}tr{break-inside:avoid}.notice{break-inside:avoid}}
</style></head><body>
<header><div class="headline"><div><div class="metadata">CORE AUTH / ATL105 2026-3</div><h1>AI Artifact Assessment</h1><div class="metadata">October 5 Run1 supplement &middot; Assessed October 6, 2026 &middot; <span class="badge">REVIEW REQUIRED</span></div></div><div class="actions"><button class="icon" id="print" title="Print assessment" aria-label="Print assessment">&#9113;</button><a class="command" href="AI-ARTIFACT-FILTER-VIEW.pdf">PDF snapshot</a></div></div></header>
<nav role="tablist" aria-label="Assessment views"><button role="tab" aria-selected="true" aria-controls="overview" data-tab="overview">Run summary</button><button role="tab" aria-selected="false" aria-controls="missing" data-tab="missing">No-TC scenarios</button><button role="tab" aria-selected="false" aria-controls="writes" data-tab="writes">Failed payload writes</button><button role="tab" aria-selected="false" aria-controls="quality" data-tab="quality">Quality signals</button></nav>
<main>
<section id="coverage" role="tabpanel" hidden>
<h2>Coverage and traceability</h2><div class="notice">Independent ATL105 rule coverage: <b>NOT_CALCULABLE</b>. Execution certification: <b>not established</b>. Percentages below measure internal linkage or declaration inventory, not validated semantic behavior.</div>
<div class="field" style="max-width:380px;margin:18px 0"><label for="coverage-mode">Coverage dimension</label><select id="coverage-mode"></select></div>
<div data-coverage-panel="total"><h3>Overall linkage</h3><div class="table-wrap"><table class="plain-table"><thead><tr><th>Metric</th><th>Numerator</th><th>Denominator</th><th>Percent</th><th>Meaning</th></tr></thead><tbody id="coverage-total"></tbody></table></div><p class="scope" id="coverage-baseline"></p></div>
<div data-coverage-panel="segments" hidden><h3>Segment-attributed linkage</h3><p class="scope">Producer BR attribution, not canonical semantic matching. Scenario grouping uses linked BRs; TC grouping uses direct BR references. Counts overlap and must not be summed. Catalog rules are a separate, unapproved inventory.</p><div class="table-wrap"><table class="plain-table"><thead><tr><th>Segment</th><th>Catalog rules</th><th>AI BRs</th><th>BRs with TC</th><th>BR link %</th><th>Scenarios</th><th>With TC</th><th>TS link %</th><th>TCs</th></tr></thead><tbody id="coverage-segments"></tbody></table></div></div>
<div data-coverage-panel="transactions" hidden><h3>Transaction-target linkage</h3><p class="scope">Exact producer scenario labels, not normalized transaction codes. Case counts join by scenario ID. Aliases are not silently merged; NOT_DECLARED records remain included.</p><div class="table-wrap"><table class="plain-table"><thead><tr><th>Producer scenario target</th><th>Scenarios</th><th>With TC</th><th>Without TC</th><th>Link %</th><th>TC candidates</th></tr></thead><tbody id="coverage-transactions"></tbody></table></div></div>
<div data-coverage-panel="responses" hidden><h3>Response-code declarations</h3><p class="scope">Scenario expected-code declarations only, not keyword hits or validated responses. The source-derived baseline has 31 code values / 35 family meanings. Code 1 is context-dependent. Absent declarations do not prove missing semantic behavior.</p><div class="table-wrap"><table class="plain-table"><thead><tr><th>Code</th><th>Source families</th><th>Scenario declarations</th><th>With TC</th><th>Linked TC candidates</th><th>Validated coverage</th></tr></thead><tbody id="coverage-responses"></tbody></table></div></div>
<div data-coverage-panel="types" hidden><h3>Scenario-type inventory</h3><div class="table-wrap"><table class="plain-table"><thead><tr><th>Scenario type</th><th>Scenarios</th><th>With TC</th><th>TC candidates</th></tr></thead><tbody id="coverage-types"></tbody></table></div></div>
</section>
<section id="overview" role="tabpanel"><div class="metrics"><div class="metric"><span>SCENARIO INVENTORY</span><strong>12,679</strong><span>Original denominator retained</span></div><div class="metric"><span>WITHOUT A TEST CASE</span><strong>3,732</strong><span>29.43% of supplied scenarios</span></div><div class="metric"><span>TC CANDIDATES</span><strong>21,123</strong><span>21,096 with physical outputs</span></div><div class="metric"><span>EXECUTION CERTIFICATION</span><strong style="color:var(--amber);font-size:23px">Not established</strong><span>Independent coverage: not calculable</span></div></div>
<div class="two-col"><div><h2>Generation gaps</h2><div class="legend"><span><i class="dot" style="background:var(--amber)"></i>Transaction unresolved: 2,026</span><span><i class="dot" style="background:var(--blue)"></i>Response deferred: 1,706</span></div><div class="bar" role="img" aria-label="Missing scenarios: 54.29 percent transaction unresolved, 45.71 percent response deferred"><span class="unresolved"></span><span class="response"></span></div><table class="plain-table"><thead><tr><th>Population</th><th class="number">Count</th></tr></thead><tbody><tr><td>Missing scenarios with a recorded generation cause</td><td class="number">3,732</td></tr><tr><td>Unexplained omissions or overlap</td><td class="number">0</td></tr><tr><td>BRs referenced by missing scenarios</td><td class="number">3,298</td></tr><tr><td>Affected BRs with no direct TC reference</td><td class="number">3,228</td></tr><tr><td>Other scenarios with TCs but deferred response variants</td><td class="number">580</td></tr><tr><td>Separate failed negative-case payload writes</td><td class="number">27</td></tr></tbody></table></div><div><h2>Assurance boundary</h2><div class="notice">Recorded generation causes are not approved coverage exclusions. All 3,732 scenarios remain independently <b>NOT_ASSESSED</b>. Producer confidence, PASS, and COMPLETE labels do not grant acceptance.</div><p class="scope">Selection changes the displayed population, never the original denominator. The 27 failed writes already have TC candidates and are separate from the no-TC population. All 89 flow cases have step-specific payloads; they are not failed base-file writes.</p><table class="plain-table"><tbody><tr><td>Archive files hash-verified against source</td><td class="number">42,436</td></tr><tr><td>Physical request payloads / metadata pairs</td><td class="number">21,210</td></tr><tr><td>Malformed payload / metadata JSON</td><td class="number">0</td></tr><tr><td>Producer pipeline status</td><td>Composer exit 1</td></tr></tbody></table></div></div>
<div class="band"><h2>Work-queue disposition</h2><table class="plain-table"><thead><tr><th>Queue</th><th>Evidence scope</th><th>Next gate</th></tr></thead><tbody><tr><td>Generation backlog</td><td>Complete 3,732-row register</td><td>Source-grounded transaction resolution or response scope/oracle</td></tr><tr><td>Partial-chain backlog</td><td>27 missing writes; 580 partially composed scenarios counted separately</td><td>Restore missing TDs and assess deferred response variants</td></tr><tr><td>Producer review / source context</td><td>Filterable within the no-TC register only</td><td>Independent source assessment, not automatic rejection</td></tr><tr><td>Technical validation candidates</td><td>Inventory and parsing results only</td><td>Supported adapter and applicable semantic validators</td></tr><tr><td>Execution candidates</td><td>No acceptance-qualified selection established</td><td>Validated behavior, expected outcomes, readiness and approval evidence</td></tr></tbody></table></div></section>
<section id="missing" role="tabpanel" hidden><h2>Scenarios without a test case</h2><p class="scope">Source register: all 3,732 zero-TC scenarios. Review and source filters apply only to this population.</p><div class="filters"><div class="field"><label for="search">Scenario / BR / rule / description</label><input id="search" type="search" placeholder="SC-5340, ENT-ELEM-86..."></div><div class="field"><label for="category">Generation cause</label><select id="category"><option value="">All causes</option value="NO_CONSISTENT_TRANSACTION">Transaction unresolved</option><option value="RESPONSE_SIDE_DEFERRED">Response deferred</option></select></div><div class="field"><label for="verdict">Producer verdict</label><select id="verdict"><option value="">All verdicts</option><option>FAIL</option><option>PASS</option><option>NEEDS_REVIEW</option><option>NOT_DECLARED</option></select></div><div class="field"><label for="source">Evidence condition</label><select id="source"><option value="">All conditions</option value="page">Missing source page</option><option value="rule">Missing source rule</option><option value="external">External behavior required</option><option value="target">Missing target transaction</option><option value="low">Low confidence</option><option value="flags">Producer flags present</option></select></div><button class="icon" id="reset" aria-label="Reset filters" title="Reset filters">&#8634;</button></div><div class="results-heading"><div id="result-count" aria-live="polite"></div><button class="command" id="csv">&#8595; Selected CSV</button></div><div class="table-wrap"><table class="register"><thead><tr><th>Scenario</th><th>Cause</th><th>Description / type</th><th>Producer verdict</th><th>Source evidence</th><th>BR links</th></tr></thead><tbody id="scenario-body"></tbody></table></div><div class="pager"><button id="previous" class="icon" title="Previous page" aria-label="Previous page">&#8592;</button><span id="page-label"></span><button id="next" class="icon" title="Next page" aria-label="Next page">&#8594;</button></div><section id="evidence" class="evidence" hidden aria-label="Selected scenario evidence"></section></section>
<section id="writes" role="tabpanel" hidden><h2>Failed negative-case payload writes</h2><p class="scope">27 TC candidates exist but have no base payload or metadata. The 89 flow candidates are excluded from this failed-write register.</p><div class="results-heading"><span>27 records &middot; candidate TC count unchanged</span><a href="case-file-gaps.csv">Original file-gap CSV (including flows)</a></div><table class="plain-table"><thead><tr><th>Test case</th><th>Scenario</th><th>Type</th><th>Payload</th><th>Metadata</th></tr></thead><tbody id="write-body"></tbody></table></section>
<section id="quality" role="tabpanel" hidden><h2>Producer quality signals</h2><p class="scope">Aggregate distributions across the full TC inventory. Affected populations overlap; these are producer claims/signals, not independent business-defect verdicts.</p><div class="two-col"><div><h3>Producer self-audit</h3><table class="plain-table"><thead><tr><th>Finding</th><th class="number">Findings</th><th class="number">Cases</th></tr></thead><tbody id="audit-body"></tbody></table></div><div><h3>TC readiness prerequisites</h3><table class="plain-table"><tbody id="quality-body"></tbody></table><div class="notice">Null expected responses do not support end-to-end response assertions. Request-only checks require a declared narrower scope. Source semantic validation and host execution have not been performed for this report.</div></div></div></section>
<section class="print-only"><h2>Appendix A: Complete no-TC register</h2><p>Snapshot: October 6, 2026. All 3,732 records; screen filters do not alter this PDF appendix. Independent disposition: NOT_ASSESSED.</p><table class="print-table"><thead><tr><th>Scenario</th><th>Generation cause</th><th>Description / type</th><th>BR IDs</th><th>Source rule / page</th><th>Producer verdict</th></tr></thead><tbody id="print-scenarios"></tbody></table></section>
<section class="print-only"><h2>Appendix B: Failed payload writes</h2><p>27 negative TC candidates; not part of the missing-TC population.</p><table class="plain-table"><thead><tr><th>Test case</th><th>Scenario</th><th>Type</th><th>Payload</th><th>Metadata</th></tr></thead><tbody id="print-writes"></tbody></table></section>
</main><footer>Preserved AI Run1 supplement &middot; No producer files or approvals modified &middot; <a href="SCENARIOS-WITHOUT-TC-ASSESSMENT.md">Detailed scenario assessment</a> &middot; <a href="DETAILED-REVIEW.md">Handoff review</a> &middot; <a href="scenario-no-tc-assessment.json">Hashed evidence summary</a></footer>
<script type="application/json" id="data">__DATA__</script><script>
'use strict';
const data=JSON.parse(document.getElementById('data').textContent), $=id=>document.getElementById(id);
$('category').replaceChildren(new Option('All causes',''),new Option('Transaction unresolved','NO_CONSISTENT_TRANSACTION'),new Option('Response deferred','RESPONSE_SIDE_DEFERRED'));
const coverageTab=document.createElement('button');coverageTab.setAttribute('role','tab');coverageTab.setAttribute('aria-selected','false');coverageTab.setAttribute('aria-controls','coverage');coverageTab.dataset.tab='coverage';coverageTab.textContent='Coverage';document.querySelector('nav').append(coverageTab);
$('coverage-mode').replaceChildren(...[['total','Overall linkage'],['segments','Segment-wise linkage'],['transactions','Transaction targets'],['responses','Response codes'],['types','Scenario types']].map(([value,text])=>new Option(text,value)));
$('coverage-mode').onchange=()=>document.querySelectorAll('[data-coverage-panel]').forEach(panel=>panel.hidden=panel.dataset.coveragePanel!==$('coverage-mode').value);
const percent=value=>value==null?'N/A':value.toFixed(2)+'%';
function coverageRow(target,values){const tr=document.createElement('tr');values.forEach(value=>cell(tr,value));$(target).append(tr)}
let pageIndex=0, selected=[], pageSize=50;
const label=r=>r.category==='NO_CONSISTENT_TRANSACTION'?'Transaction unresolved':'Response deferred';
function cell(row,text,cls){const node=document.createElement('td');node.textContent=text??'Not declared';if(cls)node.className=cls;row.append(node);return node}
function sourceText(r){return (r.sourceRuleId||'No rule')+' / '+(r.sourcePage??'No page')}
for(const r of data.coverage.totals)coverageRow('coverage-total',[r.metric,r.numerator.toLocaleString(),r.denominator.toLocaleString(),percent(r.percent),r.meaning]);
$('coverage-baseline').textContent='Independent catalog inventory: '+data.coverage.catalogRuleInventory+' rules / '+data.coverage.catalogSegments+' segments. Response baseline status: '+data.coverage.responseCodeBaseline.status+'. Catalog counts are not certified coverage denominators.';
for(const r of data.coverage.segments)coverageRow('coverage-segments',[r.segment,r.catalogRules??'N/A',r.aiBrs,r.brsWithTc,percent(r.brLinkPercent),r.scenarios,r.scenariosWithTc,percent(r.scenarioLinkPercent),r.tcCandidates]);
for(const r of data.coverage.transactionTargets)coverageRow('coverage-transactions',[r.target,r.scenarios,r.scenariosWithTc,r.missingTc,percent(r.linkPercent),r.tcCandidates]);
for(const r of data.coverage.responseCodes)coverageRow('coverage-responses',[r.code,r.sourceFamilies.join(', ')||'Not applicable',r.scenariosDeclaringCode,r.scenariosWithTc,r.tcLinkedToDeclarations,r.validatedCodeFamilyCoverage]);
for(const r of data.coverage.scenarioTypes)coverageRow('coverage-types',[r.type,r.scenarios,r.scenariosWithTc,r.tcCandidates]);
function filterRows(){const query=$('search').value.trim().toLowerCase(),category=$('category').value,verdict=$('verdict').value,condition=$('source').value;return data.scenarios.filter(r=>{const text=[r.scenarioId,r.name,r.sourceRuleId,...r.requirementIds,...r.flags].join(' ').toLowerCase();const match=!condition||condition==='page'&&r.sourcePage==null||condition==='rule'&&!r.sourceRuleId||condition==='external'&&r.requiresExternalBehaviorEvidence||condition==='target'&&!r.targetTransaction||condition==='low'&&r.confidenceBand==='LOW'||condition==='flags'&&r.flags.length>0;return (!query||text.includes(query))&&(!category||r.category===category)&&(!verdict||r.producerVerdict===verdict)&&match})}
function render(){selected=filterRows();const pages=Math.max(1,Math.ceil(selected.length/pageSize));pageIndex=Math.min(pageIndex,pages-1);$('result-count').textContent=selected.length.toLocaleString()+' selected / 3,732 no-TC scenarios / 12,679 supplied scenarios';$('page-label').textContent='Page '+(pageIndex+1)+' of '+pages;$('previous').disabled=pageIndex===0;$('next').disabled=pageIndex>=pages-1;$('csv').disabled=selected.length===0;const body=$('scenario-body');body.replaceChildren();for(const r of selected.slice(pageIndex*pageSize,(pageIndex+1)*pageSize)){const tr=document.createElement('tr'),td=cell(tr,''),button=document.createElement('button');button.className='row-link';button.textContent=r.scenarioId;button.onclick=()=>showEvidence(r);td.append(button);cell(tr,label(r));const name=cell(tr,r.name);const type=document.createElement('span');type.className='sub';type.textContent=r.scenarioType;name.append(type);cell(tr,r.producerVerdict,'tag '+(r.producerVerdict==='FAIL'?'fail':''));cell(tr,sourceText(r));cell(tr,r.requirementIds.length);body.append(tr)}if(!selected.length){const tr=document.createElement('tr'),td=cell(tr,'No scenarios match the selected criteria','empty');td.colSpan=6;body.append(tr)}}
function showEvidence(r){const panel=$('evidence');panel.replaceChildren();panel.hidden=false;const heading=document.createElement('h3');heading.textContent=r.scenarioId+' / '+label(r);panel.append(heading);const dl=document.createElement('dl');const fields={'Description':r.name,'Independent disposition':r.independentDisposition,'Source rule / page':sourceText(r),'Target transaction':r.targetTransaction||'Not declared','Producer verdict / confidence':r.producerVerdict+' / '+r.confidenceBand,'BR IDs':r.requirementIds.join(', '),'Expected response code':r.expectedResponseCode??'Not declared','Oracle authority':r.oracleAuthority||'Not declared','Resolution flags':r.resolutionFlags.join('\n')||'None recorded','Deferred response pairs':JSON.stringify(r.deferredResponsePairs,null,2),'Next action':r.nextAction};for(const [key,value]of Object.entries(fields)){const dt=document.createElement('dt');dt.textContent=key;const dd=document.createElement('dd');dd.textContent=value;dl.append(dt,dd)}panel.append(dl);const flags=document.createElement('h3');flags.textContent='Producer flags';panel.append(flags);const list=document.createElement('ul');for(const flag of r.flags){const li=document.createElement('li');li.textContent=flag;list.append(li)}panel.append(list);panel.scrollIntoView({behavior:'smooth',block:'start'})}
for(const id of ['search','category','verdict','source'])$(id).addEventListener(id==='search'?'input':'change',()=>{pageIndex=0;$('evidence').hidden=true;render()});$('previous').onclick=()=>{pageIndex--;render()};$('next').onclick=()=>{pageIndex++;render()};$('reset').onclick=()=>{for(const id of ['search','category','verdict','source'])$(id).value='';pageIndex=0;$('evidence').hidden=true;render()};
document.querySelectorAll('[data-tab]').forEach(button=>button.onclick=()=>{document.querySelectorAll('[data-tab]').forEach(b=>{b.setAttribute('aria-selected',String(b===button));$(b.dataset.tab).hidden=b!==button})});
$('print').onclick=()=>window.print();
$('csv').onclick=()=>{const fields=['scenarioId','category','name','scenarioType','producerVerdict','sourceRuleId','sourcePage','requirementIds','independentDisposition','nextAction'];const quote=value=>{let text=Array.isArray(value)?value.join('; '):String(value??'');if(/^[=+@-]/.test(text))text="'"+text;return '"'+text.replaceAll('"','""')+'"'};const content='\ufeff'+[fields.map(quote).join(','),...selected.map(row=>fields.map(key=>quote(row[key])).join(','))].join('\r\n');const url=URL.createObjectURL(new Blob([content],{type:'text/csv;charset=utf-8'}));const link=document.createElement('a');link.href=url;link.download='selected-no-tc-scenarios.csv';link.click();setTimeout(()=>URL.revokeObjectURL(url),1000)};
for(const r of data.failures){const tr=document.createElement('tr');for(const value of [r.caseId,r.scenarioId,r.scenarioType,'Missing','Missing'])cell(tr,value);$('write-body').append(tr);$('print-writes').append(tr.cloneNode(true))}
for(const [key,count]of Object.entries(data.run.producerAudit.findingCounts)){const tr=document.createElement('tr');cell(tr,key.replace('ERROR:',''));cell(tr,count.toLocaleString(),'number');cell(tr,data.run.producerAudit.distinctCasesByFinding[key].toLocaleString(),'number');$('audit-body').append(tr)}
for(const [name,count]of [['Null/empty expected responses',data.run.caseQuality.emptyExpectedResponse],['Cases with placeholder fields',data.run.caseQuality['casesWithMethod:placeholder']],['Cases awaiting client values',data.run.caseQuality['casesWithMethod:awaiting_client_value']],['Cases with unspecified codes',data.run.caseQuality['casesWithMethod:spec_unspecified_code']],['Metadata with unresolved data sections',data.run.metadataSourceFlags.dataSectionUnresolved]]){const tr=document.createElement('tr');cell(tr,name);cell(tr,count.toLocaleString(),'number');$('quality-body').append(tr)}
for(const r of data.scenarios){const tr=document.createElement('tr');for(const value of [r.scenarioId,label(r),r.name+' / '+r.scenarioType,r.requirementIds.join(', '),sourceText(r),r.producerVerdict])cell(tr,value);$('print-scenarios').append(tr)}
render();
</script></body></html>'''


if __name__ == "__main__":
    main()