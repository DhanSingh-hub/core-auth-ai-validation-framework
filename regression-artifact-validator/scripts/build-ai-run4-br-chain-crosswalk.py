import argparse
import csv
import hashlib
import json
import os
import re
from collections import Counter, defaultdict
from pathlib import Path

import ijson


def native(path):
    path = Path(os.path.normpath(str(path)))
    if os.name == "nt" and not str(path).startswith("\\\\?\\"):
        return Path("\\\\?\\" + str(path.resolve()))
    return path


def read(path):
    return json.loads(path.read_text(encoding="utf-8-sig"))


def sha256(path):
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def ids(record, plural, singular):
    values = record.get(plural) or []
    if isinstance(values, str):
        values = [values]
    result = {value for value in values if isinstance(value, str) and value}
    value = record.get(singular)
    if isinstance(value, str) and value:
        result.add(value)
    return result


def basename(value):
    return str(value or "").replace("\\", "/").rsplit("/", 1)[-1]


def segment_values(requirement):
    value = requirement.get("segment_number")
    if value is not None and str(value).strip():
        return {str(value).strip().upper()}
    result = set()
    for entity in requirement.get("segment_ids") or []:
        match = re.fullmatch(r"ENT-SEG-(.+)", str(entity).strip(), re.IGNORECASE)
        if match:
            result.add(match.group(1).strip().upper())
    return result


def inferred_element(source_rule_id):
    match = re.fullmatch(r"ENT-ELEM-(\d+)", str(source_rule_id or "").strip(), re.IGNORECASE)
    return str(int(match.group(1))) if match else None


def normalized_element(value):
    value = str(value or "").strip()
    return str(int(value)) if re.fullmatch(r"\d+", value) else value


def rule_index(rules):
    result = defaultdict(list)
    for rule in rules:
        anchor = rule.get("sourceAnchor") or {}
        element = normalized_element(anchor.get("element"))
        if not element:
            continue
        segments = str(anchor.get("segment") or "").split(",")
        for segment in segments:
            segment = segment.strip().upper()
            if segment:
                result[(segment, element)].append(rule)
    return result


def match_candidates(requirement, index):
    segments = segment_values(requirement)
    element = inferred_element(requirement.get("source_rule_id"))
    candidates = []
    for segment in sorted(segments):
        if element:
            candidates.extend(index.get((segment, element), []))
    unique = {rule["ruleId"]: rule for rule in candidates}
    candidates = [unique[key] for key in sorted(unique)]
    if candidates:
        if len(candidates) == 1 and candidates[0].get("atomicity") == "ATOMIC":
            status = "REVIEW_REQUIRED_EXACT_SOURCE_ANCHOR_CANDIDATE"
        else:
            status = "REVIEW_REQUIRED_AMBIGUOUS_OR_COMPOSITE_CANDIDATE"
    elif not segments:
        status = "NOT_MATCHABLE_SEGMENT_UNRESOLVED"
    elif not element:
        status = "NOT_MATCHABLE_NO_NUMERIC_ELEMENT_ANCHOR"
    else:
        status = "NO_EXACT_SOURCE_ANCHOR_CANDIDATE_REVIEW_REQUIRED"
    return status, sorted(segments), element, candidates


def empty_links():
    return {
        "matrixRows": 0,
        "matrixRowsWithTc": 0,
        "matrixRowsWithTdDeclaration": 0,
        "matrixFullyTracedRowsWithoutTd": 0,
        "matrixBrTsDirectRows": 0,
        "matrixTsTcDirectRows": 0,
        "matrixTcTdDeclaredRows": 0,
        "matrixCandidatePayloadPresentRows": 0,
        "matrixMetadataJoinValidRows": 0,
        "matrixProposedOrBrokenRows": 0,
        "matrixScenarioIds": set(),
        "matrixTestCaseIds": set(),
        "matrixTdPaths": set(),
        "matrixCandidateTdPaths": set(),
        "matrixMetadataJoinedTdPaths": set(),
        "matrixDirectCompleteRoutes": set(),
        "directScenarioIds": set(),
        "directTestCaseIdsViaScenario": set(),
        "directTestCaseIdsByBrReference": set(),
        "directTcTdPaths": set(),
        "directCandidateTdPaths": set(),
        "directMetadataJoinedTdPaths": set(),
        "directCompleteRoutes": set(),
    }


def strings(values):
    return sorted(values)


def serializable(value):
    if isinstance(value, set):
        return strings(value)
    if isinstance(value, dict):
        return {key: serializable(item) for key, item in value.items()}
    if isinstance(value, list):
        return [serializable(item) for item in value]
    if isinstance(value, tuple):
        return list(value)
    return value


def audit(archive, analysis_dir, test_solution_dir):
    archive = native(archive)
    analysis_dir = native(analysis_dir)
    test_solution_dir = native(test_solution_dir)
    analysis = read(analysis_dir / "LATEST-RUN-ANALYSIS.json")
    verification = read(analysis_dir.parent / "intake-verification" / "intake-verification.json")
    if not verification.get("complete"):
        raise ValueError("A complete immutable intake verification is required")

    pipeline = archive / "src/pipeline"
    ai_catalog_path = pipeline / "step5_requirements/approved/requirement_catalog.json"
    ai_scenario_path = pipeline / "scenarios/approved/approved_scenarios.json"
    matrix_path = pipeline / "reporting/output/traceability_matrix_full.json"
    ai_catalog = read(ai_catalog_path)
    requirements = ai_catalog["requirements"]
    scenarios = read(ai_scenario_path)["scenarios"]
    scenario_by_id = {scenario["id"]: scenario for scenario in scenarios}
    scenario_requirements = {
        scenario_id: ids(scenario, "requirement_ids", "requirement_id")
        for scenario_id, scenario in scenario_by_id.items()
    }

    cases = read(analysis_dir / "case-register.json")
    case_by_id = {case["caseId"]: case for case in cases}
    case_requirements = {case_id: set(case.get("requirementIds") or []) for case_id, case in case_by_id.items()}
    case_scenario = {case_id: case.get("scenarioId") for case_id, case in case_by_id.items()}
    case_files = {
        case_id: {basename(path) for path in case.get("declaredDataFiles", []) if basename(path)}
        for case_id, case in case_by_id.items()
    }
    cases_by_scenario = defaultdict(set)
    direct_tc_ids_by_br = defaultdict(set)
    for case_id, scenario_id in case_scenario.items():
        if scenario_id:
            cases_by_scenario[scenario_id].add(case_id)
        for br_id in case_requirements[case_id]:
            direct_tc_ids_by_br[br_id].add(case_id)

    physical = read(analysis_dir / "physical-file-register.json")
    physical_by_case_file = defaultdict(list)
    for item in physical:
        location = str(item.get("location", "")).replace("\\", "/")
        is_candidate = "/test_generation/candidates/qe_shaped_test_data/" in location
        record = {
            "file": item.get("file"),
            "location": item.get("location"),
            "candidateLocation": is_candidate,
            "metadataJoinValid": bool(item.get("metadataPresent") and item.get("parsed")
                                       and item.get("joinValid")),
        }
        for case_id in item.get("declaredByCases", []):
            physical_by_case_file[(case_id, basename(item.get("file")))].append(record)

    baseline = read(test_solution_dir / "source-derived-requirement-inventory.json")
    classified = read(test_solution_dir / "atomic-composite-rule-classification.json")
    if baseline.get("authority") != "INDEPENDENT_TEST_SOLUTION" or baseline.get("aiArtifactsIncluded") is not False:
        raise ValueError("Independent Test Solution inventory provenance is invalid")
    if baseline.get("ruleCount") != len(baseline.get("businessRequirements", [])):
        raise ValueError("Test Solution baseline count disagrees with its rule rows")
    if baseline["ruleCount"] != len(classified.get("rules", [])):
        raise ValueError("Atomicity inventory and independent baseline counts differ")
    test_rule_by_id = {rule["ruleId"]: rule for rule in baseline["businessRequirements"]}
    for rule in classified["rules"]:
        if rule["ruleId"] not in test_rule_by_id:
            raise ValueError("Atomicity classification references an unknown Test Solution rule")
    candidates_by_anchor = rule_index(classified["rules"])

    requirement_by_id = {requirement["id"]: requirement for requirement in requirements}
    if len(requirement_by_id) != len(requirements):
        raise ValueError("Duplicate AI BR IDs in full requirement catalog")
    per_br = {br_id: empty_links() for br_id in requirement_by_id}
    global_counts = Counter()
    matrix_br_scenarios = defaultdict(set)
    matrix_br_tcs = defaultdict(set)
    matrix_br_tdpaths = defaultdict(set)
    test_case_scenario_mismatch = 0

    with matrix_path.open("rb") as stream:
        for row in ijson.items(stream, "flat_rows.item"):
            global_counts["matrixRows"] += 1
            br_id = row.get("requirement_id")
            scenario_id = row.get("scenario_id")
            case_id = row.get("test_case_id")
            td_path = row.get("test_data_file")
            if br_id not in per_br:
                global_counts["unknownMatrixBrRows"] += 1
                continue
            links = per_br[br_id]
            links["matrixRows"] += 1
            if scenario_id:
                links["matrixScenarioIds"].add(scenario_id)
                matrix_br_scenarios[br_id].add(scenario_id)
            direct_br_ts = br_id in scenario_requirements.get(scenario_id, set())
            if direct_br_ts:
                links["matrixBrTsDirectRows"] += 1
            else:
                global_counts["matrixBrTsUnjoinedRows"] += 1
            case = case_by_id.get(case_id)
            if case_id:
                links["matrixRowsWithTc"] += 1
                global_counts["matrixRowsWithTc"] += 1
                links["matrixTestCaseIds"].add(case_id)
                matrix_br_tcs[br_id].add(case_id)
                if case is None:
                    global_counts["unknownMatrixTcRows"] += 1
                elif case_scenario.get(case_id) != scenario_id:
                    test_case_scenario_mismatch += 1
                    global_counts["matrixTsTcMismatchRows"] += 1
                else:
                    links["matrixTsTcDirectRows"] += 1
                if case_id in direct_tc_ids_by_br.get(br_id, set()):
                    links["directTestCaseIdsByBrReference"].add(case_id)
            if td_path:
                name = basename(td_path)
                links["matrixRowsWithTdDeclaration"] += 1
                global_counts["matrixRowsWithTdDeclaration"] += 1
                links["matrixTdPaths"].add(str(td_path))
                matrix_br_tdpaths[br_id].add(str(td_path))
                if case_id and name in case_files.get(case_id, set()):
                    links["matrixTcTdDeclaredRows"] += 1
                    records = physical_by_case_file.get((case_id, name), [])
                    candidate_records = [record for record in records if record["candidateLocation"]]
                    if candidate_records:
                        links["matrixCandidatePayloadPresentRows"] += 1
                        links["matrixCandidateTdPaths"].add(str(td_path))
                        if scenario_id in scenario_requirements and br_id in scenario_requirements[scenario_id] and case_scenario.get(case_id) == scenario_id:
                            links["matrixDirectCompleteRoutes"].add((scenario_id, case_id, str(td_path)))
                        if any(record["metadataJoinValid"] for record in candidate_records):
                            links["matrixMetadataJoinValidRows"] += 1
                            links["matrixMetadataJoinedTdPaths"].add(str(td_path))
                    else:
                        global_counts["matrixTdMissingCandidatePayloadRows"] += 1
                else:
                    links["matrixProposedOrBrokenRows"] += 1
                    global_counts["matrixTdNotDeclaredByTcRows"] += 1
            elif row.get("trace_status") == "FULLY_TRACED":
                links["matrixFullyTracedRowsWithoutTd"] += 1
                global_counts["matrixFullyTracedWithoutTdRows"] += 1

    for scenario_id, br_ids in scenario_requirements.items():
        for br_id in br_ids:
            if br_id not in per_br:
                global_counts["unknownScenarioBrLinks"] += 1
                continue
            per_br[br_id]["directScenarioIds"].add(scenario_id)
            for case_id in cases_by_scenario.get(scenario_id, set()):
                links = per_br[br_id]
                links["directTestCaseIdsViaScenario"].add(case_id)
                if case_id in direct_tc_ids_by_br.get(br_id, set()):
                    links["directTestCaseIdsByBrReference"].add(case_id)
                for name in case_files.get(case_id, set()):
                    links["directTcTdPaths"].add(f"{case_id}/{name}")
                    records = physical_by_case_file.get((case_id, name), [])
                    candidate_records = [record for record in records if record["candidateLocation"]]
                    if candidate_records:
                        links["directCandidateTdPaths"].add(f"{case_id}/{name}")
                        links["directCompleteRoutes"].add((scenario_id, case_id, name))
                        if any(record["metadataJoinValid"] for record in candidate_records):
                            links["directMetadataJoinedTdPaths"].add(f"{case_id}/{name}")

    match_counts = Counter()
    chain_counts = Counter()
    candidate_test_rule_ids = set()
    ai_brs_with_candidates = 0
    rows = []
    for br_id, requirement in requirement_by_id.items():
        match_status, segments, element, candidates = match_candidates(requirement, candidates_by_anchor)
        match_counts[match_status] += 1
        if candidates:
            ai_brs_with_candidates += 1
            candidate_test_rule_ids.update(rule["ruleId"] for rule in candidates)
        links = per_br[br_id]
        if links["directScenarioIds"]:
            chain_counts["brsWithDirectTs"] += 1
        if links["directTestCaseIdsViaScenario"]:
            chain_counts["brsWithTcViaDirectTs"] += 1
        if links["directTcTdPaths"]:
            chain_counts["brsWithDeclaredTdViaDirectTc"] += 1
        if links["directCandidateTdPaths"]:
            chain_counts["brsWithPhysicalTdViaDirectChain"] += 1
        rows.append({
            "aiRequirementId": br_id,
            "aiStatement": requirement.get("statement", ""),
            "aiSourceRuleId": requirement.get("source_rule_id"),
            "aiSourcePage": requirement.get("source_page"),
            "aiSegmentNumber": requirement.get("segment_number"),
            "aiSegmentIds": requirement.get("segment_ids") or [],
            "aiSegmentAssignmentMethod": requirement.get("segment_assignment_method"),
            "aiDerivationMethod": requirement.get("derivation_method"),
            "approvalFiltered": False,
            "sourceAnchorCandidateStatus": match_status,
            "candidateSegments": segments,
            "inferredElement": element,
            "testSolutionRuleCandidates": [{
                "ruleId": rule["ruleId"], "title": rule.get("title"), "class": rule.get("class"),
                "atomicity": rule.get("atomicity"), "canonicalAnchor": rule.get("canonicalAnchor"),
                "sourceAnchor": rule.get("sourceAnchor"), "sourceCatalog": rule.get("sourceCatalog"),
                "sourceEvidenceResolution": rule.get("sourceEvidenceResolution"),
            } for rule in candidates],
            "matrixAndDirectChain": serializable(links),
        })

    expected_matrix = analysis["matrix"]
    expected_graph = expected_matrix["independentGraphAudit"]
    if len(rows) != analysis["BR"] or len(rows) != 5153:
        raise AssertionError(f"Expected one row per AI BR ({analysis['BR']}), got {len(rows)}")
    if global_counts["matrixRows"] != expected_matrix["recordCounts"]["flat_rows"]["records"]:
        raise AssertionError("Streamed matrix leaf count does not match completed analyzer")
    if global_counts["matrixRowsWithTc"] != expected_graph["rowsWithTestCase"]:
        raise AssertionError("Matrix TC-link count disagrees with completed analyzer")
    if global_counts["matrixRowsWithTdDeclaration"] != expected_graph["rowsDeclaringTd"]:
        raise AssertionError("Matrix TD declaration count disagrees with completed analyzer")
    if global_counts["matrixFullyTracedWithoutTdRows"] != expected_graph["fullyTracedRowsWithoutTdDeclaration"]:
        raise AssertionError("Missing-TD row count disagrees with completed analyzer")
    if not match_counts or sum(match_counts.values()) != 5153:
        raise AssertionError("Every AI BR must receive exactly one matching disposition")

    summary = {
        "artifact": "ATL105-RUN4-AI-BR-CHAIN-TEST-SOLUTION-CROSSWALK",
        "delivery": analysis["delivery"],
        "scope": "All AI BR catalog rows, regardless of producer or SME approval; matrix BR-TS-TC-TD chain and exact source-anchor Test Solution candidates.",
        "approvalFilterApplied": False,
        "aiBusinessRequirementCount": len(rows),
        "independentTestSolutionBrCount": baseline["ruleCount"],
        "matchingPolicy": "Exact normalized segment plus numeric ENT-ELEM source ID is a candidate only. No title/text similarity is used. Candidates require independent semantic/source review and do not count as confirmed matches.",
        "matrixSource": "src/pipeline/reporting/output/traceability_matrix_full.json",
        "matrixSha256": expected_matrix["sha256"],
        "aiRequirementCatalogSha256": sha256(ai_catalog_path),
        "independentBaselineSha256": sha256(test_solution_dir / "source-derived-requirement-inventory.json"),
        "atomicityClassificationSha256": sha256(test_solution_dir / "atomic-composite-rule-classification.json"),
        "aiSourceState": ai_catalog.get("state"),
        "testSolutionAuthority": baseline.get("authority"),
        "testSolutionAiArtifactsIncluded": baseline.get("aiArtifactsIncluded"),
        "testSolutionRuleCount": baseline["ruleCount"],
        "matchDispositionCounts": dict(sorted(match_counts.items())),
        "aiBrsWithSourceAnchorCandidates": ai_brs_with_candidates,
        "uniqueTestSolutionRulesWithSourceAnchorCandidates": len(candidate_test_rule_ids),
        "run4ConfirmedSemanticMatches": 0,
        "confirmedMatchStatus": "NOT_ASSESSED",
        "matrixAndChainAudit": {
            **dict(sorted(global_counts.items())),
            "uniqueMatrixLinkedBrs": len(matrix_br_scenarios),
            "uniqueBrsWithMatrixTc": len(matrix_br_tcs),
            "uniqueBrsWithMatrixTdDeclaration": len(matrix_br_tdpaths),
            "testCaseScenarioMismatchRows": test_case_scenario_mismatch,
        },
        "directCatalogChainCounts": dict(sorted(chain_counts.items())),
        "aiBrsWithDirectScenarioLinks": sum(bool(data["directScenarioIds"]) for data in per_br.values()),
        "aiBrsWithMatrixScenarioRows": sum(bool(data["matrixScenarioIds"]) for data in per_br.values()),
        "aiBrsWithNoScenarioLink": sum(not data["directScenarioIds"] for data in per_br.values()),
        "limitations": [
            "Producer matrix paths, candidate catalog edges, and physical file presence are separate evidence layers.",
            "Matrix-only/proposed edges are retained and flagged; they are not silently promoted to direct catalog links.",
            "Exact source-anchor candidates are not semantic approval; unresolved candidates remain review-required.",
            "No AI BR is excluded based on approval state.",
        ],
    }
    output_dir = analysis_dir / "run4-independent-br-crosswalk-v2"
    if output_dir.exists():
        raise ValueError(f"Refusing to overwrite existing output directory: {output_dir}")
    output_dir.mkdir()
    (output_dir / "run4-ai-br-chain-crosswalk.jsonl").write_text(
        "".join(json.dumps(row, ensure_ascii=True) + "\n" for row in rows), encoding="utf-8")
    fields = ["aiRequirementId", "aiStatement", "aiSourceRuleId", "aiSourcePage", "aiSegmentNumber",
              "aiSegmentIds", "aiSegmentAssignmentMethod", "aiDerivationMethod", "approvalFiltered",
              "sourceAnchorCandidateStatus", "candidateSegments", "inferredElement",
              "testSolutionRuleCandidates", "matrixAndDirectChain"]
    with (output_dir / "run4-ai-br-chain-crosswalk.csv").open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=fields)
        writer.writeheader()
        for row in rows:
            writer.writerow({key: json.dumps(row[key], ensure_ascii=True, separators=(",", ":"))
                             if isinstance(row[key], (dict, list)) else row[key] for key in fields})
    (output_dir / "run4-ai-br-chain-crosswalk-summary.json").write_text(
        json.dumps(summary, indent=2, ensure_ascii=True), encoding="utf-8")
    print(json.dumps({"output": str(output_dir), "aiBrs": len(rows),
                      "matchDispositions": summary["matchDispositionCounts"],
                      "matrixAndChainAudit": summary["matrixAndChainAudit"],
                      "directCatalogChainCounts": summary["directCatalogChainCounts"]}, indent=2))


def self_test():
    rules = [{"ruleId": "SEG100-R-1", "title": "rule", "class": "field", "atomicity": "ATOMIC",
              "canonicalAnchor": "ATL105|2026-3|11.2|100|001|rule",
              "sourceAnchor": {"segment": "100", "element": "001"}}]
    index = rule_index(rules)
    status, segments, element, candidates = match_candidates(
        {"segment_number": 100, "source_rule_id": "ENT-ELEM-001"}, index)
    assert status == "REVIEW_REQUIRED_EXACT_SOURCE_ANCHOR_CANDIDATE"
    assert segments == ["100"] and element == "1" and candidates == rules
    status, _, _, candidates = match_candidates(
        {"segment_number": None, "segment_ids": [], "source_rule_id": "ENT-ELEM-1"}, index)
    assert status == "NOT_MATCHABLE_SEGMENT_UNRESOLVED" and not candidates
    status, _, _, candidates = match_candidates(
        {"segment_number": 100, "source_rule_id": "REL-ENT-1"}, index)
    assert status == "NOT_MATCHABLE_NO_NUMERIC_ELEMENT_ANCHOR" and not candidates
    assert ids({"requirement_ids": ["BR1"], "requirement_id": "BR2"},
               "requirement_ids", "requirement_id") == {"BR1", "BR2"}
    assert basename("qe_shaped_test_data\\TC-1.json") == "TC-1.json"
    print("PASS: exact anchor candidates, unresolved anchors, unsupported source IDs and link/file normalization")


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--archive", type=Path)
    parser.add_argument("--analysis-directory", type=Path)
    parser.add_argument("--test-solution-directory", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
        return
    if not all((args.archive, args.analysis_directory, args.test_solution_directory)):
        parser.error("--archive, --analysis-directory, and --test-solution-directory are required")
    audit(args.archive, args.analysis_directory, args.test_solution_directory)


if __name__ == "__main__":
    main()