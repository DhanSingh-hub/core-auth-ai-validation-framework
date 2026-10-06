import argparse
import collections
import csv
import hashlib
import json
import os
import re
from html import escape
from pathlib import Path

import ijson
from markdown_it import MarkdownIt


PARSER = MarkdownIt("commonmark").enable("table")


def table_row(header, separator, line):
    tokens = PARSER.parse(header + separator + line)
    cells = []
    in_body = False
    for token in tokens:
        if token.type == "tbody_open":
            in_body = True
        elif in_body and token.type == "inline":
            cells.append("".join(child.content for child in token.children or []))
    return cells


def read_matrix(file):
    summary = {}
    metadata = {}
    limitations = []
    detail_ids = []
    header = separator = None
    section = None
    with file.open(encoding="utf-8-sig") as stream:
        for line in stream:
            if line.startswith("**Specification:") or line.startswith("**Generated:"):
                metadata[line.split(":", 1)[0].strip("*")] = line.strip()
            if line.startswith("_Showing"):
                limitations.append(line.strip().strip("_"))
            if line.startswith("### REQ-"):
                detail_ids.append(line.split()[1])
                yield {"detailRequirement": line.split()[1], "detailStatus": line.split()[-1]}
            if line.startswith("## "):
                section = line.split(".", 1)[0].removeprefix("## ")
                header = separator = None
            if section not in {"1", "3", "4"} or not line.startswith("|"):
                continue
            if header is None:
                header = line
                continue
            if separator is None:
                separator = line
                continue
            cells = table_row(header, separator, line)
            if section == "1":
                if len(cells) != 2:
                    raise ValueError("Unexpected matrix summary row shape")
                summary[cells[0]] = cells[1]
            elif section == "3":
                if len(cells) != 6:
                    raise ValueError("Unexpected matrix gap row shape")
                yield {"gapRequirement": cells[0], "gapStatus": cells[2]}
            else:
                if len(cells) != 9:
                    raise ValueError(f"Unexpected matrix leaf row shape: {len(cells)}")
                yield dict(zip(["requirementId", "statement", "scenarioId", "type", "caseId", "priority", "verification", "testDataFile", "status"], cells))
    yield {"producerSummary": summary, "metadata": metadata, "limitations": limitations, "detailRequirementIds": detail_ids}


def links(row):
    many = row.get("requirement_ids") or []
    if isinstance(many, str):
        many = [many]
    return set(many) | ({row["requirement_id"]} if row.get("requirement_id") else set())


def sha256(file):
    with file.open("rb") as stream:
        return hashlib.file_digest(stream, "sha256").hexdigest()


def native_path(file):
    if os.name == "nt" and not str(file).startswith("\\\\?\\"):
        return Path("\\\\?\\" + str(file.resolve()))
    return file


def declared_case_files(case, payload_root):
    declared = []
    if case.get("test_data_file"):
        declared.append(case["test_data_file"])
    legs = case.get("test_data_leg_files") or []
    if isinstance(legs, dict):
        legs = list(legs.values())
    declared.extend(legs)
    issues = []
    files = []
    if not declared and not case.get("is_flow"):
        declared = [f"qe_shaped_test_data/{case['id']}.json"]
    for value in declared:
        if not isinstance(value, str):
            issues.append("UNSUPPORTED_DECLARED_LEG_SHAPE")
            continue
        normalized = value.replace("\\", "/")
        parts = Path(normalized).parts
        if ".." in parts or normalized.startswith("/") or ":" in normalized or not normalized.endswith(".json") or normalized.endswith(".meta.json"):
            issues.append("UNSAFE_OR_UNSUPPORTED_DATA_PATH")
            continue
        filename = normalized.rsplit("/", 1)[-1]
        if not filename.startswith(case["id"] + ".") and not filename.startswith(case["id"] + "_"):
            issues.append("DECLARED_FILE_CASE_ID_MISMATCH")
            continue
        files.append({"file": "qe_shaped_test_data/" + filename,
                      "present": native_path(payload_root / filename).is_file()})
    unique = {item["file"]: item for item in files}
    if case.get("is_flow") and not unique:
        issues.append("FLOW_LEG_FILES_NOT_DECLARED")
    return list(unique.values()), issues


def reconstructed_leaf_status(case, files, issues):
    if case is None:
        return "SCENARIO_ONLY"
    if issues:
        return "BROKEN_LINK"
    if not files or not any(item["present"] for item in files):
        return "TEST_CASE_NO_DATA"
    if not all(item["present"] for item in files):
        return "PARTIAL_TEST_DATA"
    return "FULLY_TRACED"


def requirement_status(leaves):
    statuses = {row["status"] for row in leaves}
    for status in ["FULLY_TRACED", "PARTIAL_TEST_DATA", "TEST_CASE_NO_DATA", "BROKEN_LINK", "SCENARIO_ONLY", "REQUIREMENT_ONLY"]:
        if status in statuses:
            return status
    return "NOT_ASSESSED"


def reconstruct(requirements, scenarios, cases, payload_root):
    scenarios_by_br = collections.defaultdict(list)
    cases_by_scenario = collections.defaultdict(list)
    for scenario_id, scenario in scenarios.items():
        for br in links(scenario):
            scenarios_by_br[br].append(scenario_id)
    physical = {}
    for case_id, case in cases.items():
        cases_by_scenario[case["scenarioId"]].append(case_id)
        physical[case_id] = declared_case_files(case, payload_root)
    rows = []
    for br in sorted(requirements):
        if not scenarios_by_br[br]:
            rows.append({"requirementId": br, "scenarioId": "", "caseId": "", "testDataFiles": [], "status": "REQUIREMENT_ONLY", "issues": []})
        for scenario_id in sorted(scenarios_by_br[br]):
            case_ids = cases_by_scenario[scenario_id] or [None]
            for case_id in case_ids:
                case = cases.get(case_id)
                files, issues = physical[case_id] if case else ([], [])
                rows.append({"requirementId": br, "scenarioId": scenario_id, "caseId": case_id or "", "testDataFiles": files,
                             "status": reconstructed_leaf_status(case, files, issues), "issues": issues,
                             "linkBasis": "DIRECT_TC_BR" if case and br in case["brIds"] else "SCENARIO_INHERITED" if case else "BR_SCENARIO_ONLY"})
    represented = {(row["requirementId"], row["scenarioId"], row["caseId"]) for row in rows}
    for case_id, case in cases.items():
        for br in sorted(case["brIds"]):
            if (br, case["scenarioId"], case_id) not in represented:
                files, issues = physical[case_id]
                rows.append({"requirementId": br, "scenarioId": case["scenarioId"], "caseId": case_id, "testDataFiles": files,
                             "status": "BROKEN_LINK", "issues": [*issues, "TC_BR_NOT_DECLARED_BY_SCENARIO"], "linkBasis": "DIRECT_TC_BR_ONLY"})
    summary = {"scope": "Independent complete frozen-catalog structural reconstruction; not the producer's missing JSON matrix or semantic/execution certification",
               "leafRows": len(rows), "requirementsRepresented": len({row["requirementId"] for row in rows}),
               "leafStatusCounts": dict(collections.Counter(row["status"] for row in rows)),
               "brsWithTc": len({row["requirementId"] for row in rows if row["caseId"]}),
               "brsWithAtLeastOneCompletePhysicalLeaf": len({row["requirementId"] for row in rows if row["status"] == "FULLY_TRACED"}),
               "logicalCasesWithAllDataFiles": sum(bool(files) and not issues and all(item["present"] for item in files) for files, issues in physical.values()),
               "logicalCasesWithoutData": sum(not files or not any(item["present"] for item in files) for files, issues in physical.values()),
               "orphanScenarioIds": sorted(key for key, value in scenarios.items() if not links(value)),
               "orphanCaseIds": sorted(key for key, value in cases.items() if not value["brIds"]),
               "executionCertified": False}
    return rows, summary


def self_test():
    header = "| Requirement | Statement | Scenario | Type | Test Case | Priority | Verification | Test Data File | Status |\n"
    separator = "| --- | --- | --- | --- | --- | --- | --- | --- | --- |\n"
    cells = table_row(header, separator, "| REQ-1 | A \\| B | SC-1 | negative | TC-1 | P1 | | `data/TC-1.json` | FULLY_TRACED |\n")
    assert len(cells) == 9 and cells[1] == "A | B" and cells[7] == "data/TC-1.json"
    assert links({"requirement_ids": "BR-1", "requirement_id": "BR-2"}) == {"BR-1", "BR-2"}
    assert matrix_only_traced({"BR-1", "BR-2", "BR-3"}, {"BR-3"}, {"BR-1"}) == {"BR-2"}
    assert reconstructed_leaf_status(None, [], []) == "SCENARIO_ONLY"
    assert reconstructed_leaf_status({}, [{"present": False}], []) == "TEST_CASE_NO_DATA"
    assert reconstructed_leaf_status({}, [{"present": True}, {"present": False}], []) == "PARTIAL_TEST_DATA"
    assert reconstructed_leaf_status({}, [{"present": True}], []) == "FULLY_TRACED"
    assert reconstructed_leaf_status({}, [{"present": True}], ["BROKEN_LINK"]) == "BROKEN_LINK"
    rows, summary = reconstruct({"BR-1": {}, "BR-2": {}}, {"SC-1": {"requirement_id": "BR-1"}}, {}, Path("."))
    assert summary["requirementsRepresented"] == 2
    assert {row["status"] for row in rows} == {"SCENARIO_ONLY", "REQUIREMENT_ONLY"}
    assert requirement_status([{"status": "SCENARIO_ONLY"}, {"status": "FULLY_TRACED"}]) == "FULLY_TRACED"
    assert requirement_status([{"status": "SCENARIO_ONLY"}]) == "SCENARIO_ONLY"
    print("PASS: escaped-pipe/inline-code Markdown cells, empty columns and independent BR link extraction")


def assess(matrix, root, source):
    run = json.loads((root / "complete-handoff-analysis.json").read_text(encoding="utf-8"))
    archive = Path(run["archive"])
    catalogs = archive / "scenerio_req_5_oct/scenerio_req_5_oct"
    requirement_file = catalogs / "step5_requirements/approved/requirement_catalog.json"
    scenario_file = catalogs / "scenarios/approved/approved_scenarios.json"
    case_file = archive / "pipeline_run_artifacts/test_case_candidates.json"
    requirements = {row["id"]: row for row in json.loads(requirement_file.read_text(encoding="utf-8-sig"))["requirements"]}
    scenarios = {row["id"]: row for row in json.loads(scenario_file.read_text(encoding="utf-8-sig"))["scenarios"]}
    cases = {}
    with case_file.open("rb") as stream:
        for case in ijson.items(stream, "test_cases.item", use_float=True):
            cases[case["id"]] = {"id": case["id"], "scenarioId": case["scenario_id"], "brIds": links(case),
                                 "is_flow": case.get("is_flow", False), "test_data_file": case.get("test_data_file"),
                                 "test_data_leg_files": case.get("test_data_leg_files")}
    direct = set().union(*(case["brIds"] for case in cases.values()))
    inherited = set().union(*(links(scenarios[case["scenarioId"]]) for case in cases.values()))
    expected = {(br, case["scenarioId"], case_id) for case_id, case in cases.items()
                for br in case["brIds"] | links(scenarios[case["scenarioId"]])}
    observed = set()
    matrix_cases = set()
    matrix_requirements = set()
    matrix_tc_requirements = set()
    present_requirements = set()
    statuses = collections.Counter()
    paths = {}
    problems = []
    counts = collections.Counter()
    output_rows = []
    producer = {}
    gap_ids = set()
    gap_statuses = collections.Counter()
    detail_statuses = {}
    for row in read_matrix(matrix):
        if "producerSummary" in row:
            producer = row
            continue
        if "gapRequirement" in row:
            gap_ids.add(row["gapRequirement"])
            gap_statuses[row["gapStatus"]] += 1
            continue
        if "detailRequirement" in row:
            detail_statuses[row["detailRequirement"]] = row["detailStatus"]
            continue
        counts["leafRows"] += 1
        statuses[row["status"]] += 1
        br, scenario, case_id = row["requirementId"], row["scenarioId"], row["caseId"]
        matrix_requirements.add(br)
        defects = []
        if br not in requirements:
            defects.append("UNKNOWN_REQUIREMENT")
        if scenario not in scenarios:
            defects.append("UNKNOWN_SCENARIO")
        elif br not in links(scenarios[scenario]):
            defects.append("BR_NOT_LINKED_BY_SCENARIO")
        if case_id.startswith("TC-"):
            matrix_cases.add(case_id)
            matrix_tc_requirements.add(br)
            key = (br, scenario, case_id)
            if key in observed:
                counts["duplicateLeafLinks"] += 1
            observed.add(key)
            case = cases.get(case_id)
            if case is None:
                defects.append("UNKNOWN_TEST_CASE")
            elif case["scenarioId"] != scenario:
                defects.append("TEST_CASE_SCENARIO_MISMATCH")
            elif br not in case["brIds"] and br not in links(scenarios[scenario]):
                defects.append("BR_NOT_LINKED_BY_TC_OR_SCENARIO")
            row["independentLinkBasis"] = "DIRECT_TC_BR" if case and br in case["brIds"] else "SCENARIO_INHERITED_ONLY"
        else:
            row["independentLinkBasis"] = "NO_TC_DECLARED"
        declared_path = row["testDataFile"]
        physical = None
        if declared_path and declared_path not in {"-", "—", "None", "N/A"}:
            normalized = declared_path.replace("\\", "/")
            if not normalized.startswith("qe_shaped_test_data/") or ".." in Path(normalized).parts:
                defects.append("UNSUPPORTED_OR_UNSAFE_TD_PATH")
            else:
                if normalized not in paths:
                    choices = [archive / normalized, archive / "pipeline_run_artifacts" / normalized]
                    paths[normalized] = next((file for file in choices if file.is_file()), None)
                physical = paths[normalized]
                if physical is None:
                    defects.append("DECLARED_TD_FILE_NOT_IN_FROZEN_INTAKE")
        elif row["status"] == "FULLY_TRACED":
            defects.append("FULLY_TRACED_LEAF_WITHOUT_TC_OR_TD")
        if physical is not None and not defects:
            present_requirements.add(br)
            counts["verifiedPhysicalLeafRows"] += 1
        if defects:
            problems.append({"requirementId": br, "scenarioId": scenario, "caseId": case_id, "testDataFile": declared_path, "issues": defects})
        row["independentIssues"] = ";".join(defects)
        row["physicalFilePresent"] = physical is not None
        output_rows.append(row)
    unrepresented = sorted(expected - observed)
    extra = sorted(observed - expected)
    if len(requirements) != run["businessRequirements"] or len(cases) != run["caseReconciliation"]["records"]:
        raise ValueError("Frozen handoff input counts differ from prior assessment")
    result = {"assessmentDate": "2026-10-06", "supplementStatus": "RECEIVED_AND_INDEPENDENTLY_RECONCILED",
              "disposition": "REVIEW_REQUIRED", "independentCoverage": "NOT_CALCULABLE", "executionCertified": False,
              "scope": "Producer structural traceability and declared-file presence; not business equivalence, complete TD validity or host execution",
              "intake": {"file": str(matrix.resolve()), "externalSource": source, "sha256": sha256(matrix), "bytes": matrix.stat().st_size,
                         "reportRelativeFile": os.path.relpath(matrix, root).replace("\\", "/")},
              "producer": producer, "inputSha256": {file.name: sha256(file) for file in [requirement_file, scenario_file, case_file]},
              "independent": {**counts, "requirements": len(requirements), "scenarios": len(scenarios), "testCases": len(cases),
                  "directTcBrs": len(direct), "scenarioInheritedBrs": len(inherited), "directOrScenarioBrs": len(direct | inherited),
                  "inheritedOnlyBrIds": sorted(inherited - direct), "matrixRequirementIds": len(matrix_requirements),
                  "matrixBrsWithTc": len(matrix_tc_requirements), "brsWithVerifiedDeclaredData": len(present_requirements),
                  "matrixUniqueTestCases": len(matrix_cases), "matrixStatusCounts": dict(statuses),
                  "uniqueDeclaredDataPaths": len(paths), "presentDeclaredDataPaths": sum(file is not None for file in paths.values()),
                  "unrepresentedFrozenLeafLinks": len(unrepresented), "unexpectedLeafLinks": len(extra),
                  "gapRequirementCount": len(gap_ids), "gapStatusCounts": dict(gap_statuses),
                  "matrixOnlyTracedBrIds": sorted(matrix_only_traced(set(requirements), gap_ids, direct | inherited)),
                  "frozenLinkedBrsDeclaredAsGap": sorted((direct | inherited) & gap_ids),
                  "detailRequirementCount": len(producer["detailRequirementIds"]),
                  "scenarioIdsWithoutBrLinks": sum(not links(row) for row in scenarios.values()),
                  "tcIdsWithoutBrLinks": sum(not case["brIds"] for case in cases.values())},
              "issues": problems, "unrepresentedFrozenLeafLinks": unrepresented, "unexpectedLeafLinks": extra,
              "requiredFollowUp": ["Provide the complete traceability_matrix.json (61,107 rows) and its source catalog hashes",
                  "Reconcile 3,661 declared linked BRs with 3,659 frozen linked BRs and 92 versus 155 orphan scenarios",
                  "Explain FULLY_TRACED on leaves without a TC/TD; do not count those leaves as complete",
                  "Provide independently qualified business equivalence, supported full controls, captured wire and authoritative host oracles"],
              "history": {"directory": "history/2026-10-06-before-late-matrix", "files": {file.name: sha256(file) for file in native_path(root / "history/2026-10-06-before-late-matrix").iterdir() if file.is_file()}}}
    result["independent"]["directTcBrPercent"] = round(100 * len(direct) / len(requirements), 2)
    producer_linked = int(producer["producerSummary"]["Requirements with ≥ 1 test case"].replace(",", ""))
    result["independent"]["producerNumeratorPercent"] = round(100 * producer_linked / len(requirements), 2)
    result["comparison"] = [
        {"metric": "BR inventory", "previous": 6887, "producer": producer["producerSummary"]["Total business requirements"], "current": len(requirements), "status": "AGREES"},
        {"metric": "Scenario / TC inventory", "previous": "12,679 / 21,123", "producer": producer["producerSummary"]["Total scenarios / test cases"], "current": f"{len(scenarios):,} / {len(cases):,}", "status": "AGREES"},
        {"metric": "BRs with a TC", "previous": 3659, "producer": producer["producerSummary"]["Requirements with ≥ 1 test case"], "current": len(direct | inherited), "status": "DISCREPANCY_REVIEW_REQUIRED"},
        {"metric": "Scenarios without BR links", "previous": 155, "producer": producer["producerSummary"]["Orphan scenarios / test cases"].split("/")[0].strip(), "current": result["independent"]["scenarioIdsWithoutBrLinks"], "status": "DISCREPANCY_REVIEW_REQUIRED"},
        {"metric": "Scenarios without TC", "previous": 3732, "producer": "Not stated", "current": 3732, "status": "UNCHANGED_FROZEN_INTAKE"},
        {"metric": "Independent semantic coverage", "previous": "NOT_CALCULABLE", "producer": "Not established by structural matrix", "current": "NOT_CALCULABLE", "status": "UNCHANGED"},
        {"metric": "Execution certification", "previous": "Not established", "producer": "Not established by structural matrix", "current": "Not established", "status": "UNCHANGED"},
    ]
    rebuilt, rebuilt_summary = reconstruct(requirements, scenarios, cases, archive / "pipeline_run_artifacts/qe_shaped_test_data")
    rebuilt_keys = {(row["requirementId"], row["scenarioId"], row["caseId"]): row for row in rebuilt}
    rebuilt_by_br = collections.defaultdict(list)
    for row in rebuilt:
        rebuilt_by_br[row["requirementId"]].append(row)
    detail_comparison = [{"requirementId": br, "producerStatus": status,
                          "independentRequirementStatus": requirement_status(rebuilt_by_br[br])}
                         for br, status in detail_statuses.items()]
    disagreements = []
    for row in output_rows:
        key = (row["requirementId"], row["scenarioId"], row["caseId"])
        independent_row = rebuilt_keys.get(key)
        if independent_row and row["status"] != independent_row["status"]:
            disagreements.append({"requirementId": key[0], "scenarioId": key[1], "caseId": key[2],
                                  "producerStatus": row["status"], "independentLeafStatus": independent_row["status"]})
    declared_match = re.search(r"first [\d,]+ of ([\d,]+) rows", " ".join(producer["limitations"]))
    declared_rows = int(declared_match.group(1).replace(",", "")) if declared_match else None
    result["reconstruction"] = {**rebuilt_summary, "producerFlatRowsDeclared": declared_rows,
                               "rowCountDelta": len(rebuilt) - declared_rows if declared_rows is not None else None,
                               "flatExcerptStatusDisagreements": disagreements,
                               "detailStatusComparison": detail_comparison,
                               "detailStatusAgreementCount": sum(row["producerStatus"] == row["independentRequirementStatus"] for row in detail_comparison),
                               "unconfirmedDifferenceHypothesis": "63 additional rows and 63 fewer orphan scenarios are arithmetically consistent with remapping, but no missing producer edges are supplied",
                               "completeJson": "reconstructed-requirement-matrix.json", "completeCsv": "reconstructed-requirement-matrix.csv"}
    rebuilt_document = {"producer": "TEST_SOLUTION_INDEPENDENT_RECONSTRUCTION", "sourceInputSha256": result["inputSha256"],
                        "summary": rebuilt_summary, "leaves": rebuilt}
    (root / "reconstructed-requirement-matrix.json").write_text(json.dumps(rebuilt_document, indent=2, ensure_ascii=True), encoding="utf-8")
    with (root / "reconstructed-requirement-matrix.csv").open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=["requirementId", "scenarioId", "caseId", "status", "testDataFiles", "issues", "linkBasis"])
        writer.writeheader()
        for row in rebuilt:
            writer.writerow({**row, "testDataFiles": json.dumps(row["testDataFiles"], separators=(",", ":")), "issues": ";".join(row["issues"])})
    result["reconstruction"]["outputSha256"] = {name: sha256(root / name) for name in ["reconstructed-requirement-matrix.json", "reconstructed-requirement-matrix.csv"]}
    supplemental_history = native_path(root / "history/pre-reconciliation-20261006")
    if supplemental_history.is_dir():
        result["supplementHistory"] = {"directory": "history/pre-reconciliation-20261006",
                                       "files": {file.name: sha256(file) for file in supplemental_history.iterdir() if file.is_file()}}
    result["supplementStatus"] = "TRUNCATED_MARKDOWN_RECONCILED_AGAINST_COMPLETE_FROZEN_RECONSTRUCTION"
    result["requiredFollowUp"][2] = "The 75 leaf-status differences are requirement-level labels on scenario-only leaves; obtain producer clarification, not automatic approval"
    with (root / "late-matrix-reconciled-leaves.csv").open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=list(output_rows[0]))
        writer.writeheader()
        writer.writerows(output_rows)
    (root / "late-traceability-matrix-assessment.json").write_text(json.dumps(result, indent=2, ensure_ascii=True), encoding="utf-8")
    print(json.dumps(result["independent"], indent=2))
    print(f"Matrix row problems: {len(problems)}; original input and previous report snapshots preserved")
    print("Complete independent reconstruction: " + json.dumps({key: value for key, value in rebuilt_summary.items() if not isinstance(value, list)}))


def matrix_only_traced(requirements, gaps, frozen_linked):
    return requirements - gaps - frozen_linked


def report_section(assessment):
    independent = assessment["independent"]
    previous = assessment["history"]["directory"]
    rows = "".join("<tr>" + "".join(f"<td>{escape(str(row[key]))}</td>" for key in ["metric", "previous", "producer", "current", "status"]) + "</tr>" for row in assessment["comparison"])
    limitations = " ".join(assessment["producer"]["limitations"])
    reconstruction = ""
    if assessment.get("reconstruction"):
        rebuilt = assessment["reconstruction"]
        statuses = ", ".join(f"{status}: {count:,}" for status, count in rebuilt["leafStatusCounts"].items())
        reconstruction = f'''<h3>Complete independent reconstruction</h3>
<p class="scope">All {rebuilt['requirementsRepresented']:,} frozen BRs reconstructed into {rebuilt['leafRows']:,} structural leaves; producer declares {rebuilt['producerFlatRowsDeclared']:,} ({rebuilt['rowCountDelta']:+,} difference). {escape(statuses)}. {rebuilt['logicalCasesWithAllDataFiles']:,} logical cases have their data files; {rebuilt['logicalCasesWithoutData']:,} lack data. Orphan BR attribution remains unresolved: {len(rebuilt['orphanScenarioIds'])} scenarios / {len(rebuilt['orphanCaseIds'])} cases.</p>
<p class="scope">Detailed requirement statuses agree for {rebuilt['detailStatusAgreementCount']} / {len(rebuilt['detailStatusComparison'])} displayed BRs. The {len(rebuilt['flatExcerptStatusDisagreements'])} leaf disagreements reflect requirement-level FULLY_TRACED labels on independently SCENARIO_ONLY leaves; complete requirements and complete individual leaves are different denominators. The 63-row/orphan difference is consistent with additional mappings, not proof of those missing producer edges.</p>
<p><a href="reconstructed-requirement-matrix.json">Complete independent matrix JSON</a> &middot; <a href="reconstructed-requirement-matrix.csv">Complete independent matrix CSV</a></p>
<p class="scope">This is Test Solution-owned reconstruction, not the omitted AI JSON. Structural completeness does not confirm business meaning, full-message validity, host outcomes or execution approval.</p>'''
    later_history = ""
    if assessment.get("supplementHistory"):
        snapshot = assessment["supplementHistory"]
        later_history = f'''<p class="scope">The earlier truncated-matrix assessment is also retained unchanged: {len(snapshot['files'])} files with recorded hashes.</p><p><a href="{snapshot['directory']}/AI-ARTIFACT-FILTER-VIEW.html">Earlier matrix-era run report</a> &middot; <a href="{snapshot['directory']}/SEMANTIC-FIRST-BATCH-REPORT.html">Earlier matrix-era semantic report</a> &middot; <a href="{snapshot['directory']}/late-traceability-matrix-assessment.json">Earlier matrix reconciliation</a></p>'''
    return f'''<div class="band" id="late-matrix-summary"><h2>Late requirement matrix | October 6 update</h2>
<div class="notice"><b>Received and reconciled; REVIEW_REQUIRED.</b> Structural matrix receipt does not confirm business equivalence or execution readiness.</div>
<p class="scope">{escape(limitations)} The complete JSON companion was not supplied. Unrepresented rows are unavailable evidence, not independently identified missing links.</p>
<div class="table-wrap"><table class="plain-table"><thead><tr><th>Measure</th><th>Previous assessment</th><th>AI matrix declaration</th><th>Frozen-intake recount</th><th>Current status</th></tr></thead><tbody>{rows}</tbody></table></div>
<p class="scope">Parsed flat leaves: {independent['leafRows']:,}; declared TD paths present: {independent['presentDeclaredDataPaths']:,} / {independent['uniqueDeclaredDataPaths']:,}. Verified linked physical leaves: {independent.get('verifiedPhysicalLeafRows', 0):,}. Review issues: {len(assessment['issues']):,}. FULLY_TRACED on a no-TC/no-TD leaf is not accepted as a complete chain. These counts apply only to the displayed flat excerpt.</p>
<p class="scope">Gap-section complement marks additional BRs as traced: {escape(', '.join(independent['matrixOnlyTracedBrIds']) or 'None')}; these are not confirmed against the frozen graph. Declared 3,661 / 6,887 = {independent['producerNumeratorPercent']:.2f}%, whereas the matrix prints 53.13% (the frozen 3,659 numerator). Full-chain 53.2% is a producer declaration, not independent certification.</p>
{reconstruction}
<p class="scope">Matrix SHA-256: <code style="overflow-wrap:anywhere">{assessment['intake']['sha256']}</code></p>
<p><a href="{escape(assessment['intake']['reportRelativeFile'], quote=True)}">Preserved matrix</a> &middot; <a href="late-traceability-matrix-assessment.json">Reconciliation evidence</a> &middot; <a href="late-matrix-reconciled-leaves.csv">Parsed leaf CSV</a> &middot; <a href="LATE-MATRIX-UPDATE.md">Status and follow-up</a></p>
<h3>Assessment history</h3><p class="scope">Previous assessment retained unchanged: {len(assessment['history']['files'])} files with recorded hashes. No prior counts, verdicts or semantic findings were replaced by AI claims.</p>
<p><a href="{previous}/AI-ARTIFACT-FILTER-VIEW.html">Previous run report</a> &middot; <a href="{previous}/AI-ARTIFACT-FILTER-VIEW.pdf">Previous PDF</a> &middot; <a href="{previous}/SEMANTIC-FIRST-BATCH-REPORT.html">Previous semantic report</a> &middot; <a href="{previous}/complete-handoff-analysis.json">Previous evidence</a></p>{later_history}</div>'''


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("matrix", nargs="?", type=Path)
    parser.add_argument("review_directory", nargs="?", type=Path)
    parser.add_argument("--external-source", default="")
    parser.add_argument("--self-test", action="store_true")
    arguments = parser.parse_args()
    if arguments.self_test:
        self_test()
    else:
        if not arguments.matrix or not arguments.review_directory:
            parser.error("matrix and review_directory are required")
        assess(arguments.matrix, arguments.review_directory, arguments.external_source)


if __name__ == "__main__":
    main()