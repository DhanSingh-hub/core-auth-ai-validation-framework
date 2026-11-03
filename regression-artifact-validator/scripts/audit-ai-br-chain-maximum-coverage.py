#!/usr/bin/env python3
"""Audit every combined AI BR against Test BRs and both sides' chain evidence."""

import argparse
import collections
import hashlib
import json
from pathlib import Path


RUN1_COHORT = "Run1-legacy-requirement-catalog"
SHARED_COHORT = "Run2-Run4-byte-identical-canonical-catalog"
PRIMARY_RECORD = "AI_BUSINESS_REQUIREMENT"
SUPPLEMENTAL_RECORD = "RUN1_SUPPLEMENTAL_SEGMENT100_SUITE_BUSINESS_REQUIREMENT"


def read_json(path: Path):
    return json.loads(path.read_text(encoding="utf-8-sig"))


def read_jsonl(path: Path) -> list[dict]:
    return [json.loads(line) for line in path.read_text(encoding="utf-8").splitlines() if line]


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def write_jsonl(path: Path, rows: list[dict]) -> None:
    with path.open("w", encoding="utf-8", newline="\n") as stream:
        for row in rows:
            stream.write(json.dumps(row, ensure_ascii=True, sort_keys=True) + "\n")


def anchor_pair(segment, element):
    segment = str(segment or "").strip().upper()
    element = str(element or "").strip()
    if not segment or not element.isdigit():
        return None
    return segment, str(int(element))


def record_anchor_pairs(record: dict) -> set[tuple[str, str]]:
    pairs = set()
    for anchor in record.get("sourceAnchors") or []:
        pair = anchor_pair(anchor.get("segment"), anchor.get("element"))
        if pair:
            pairs.add(pair)
    return pairs


def build_test_chain_index(package: dict) -> dict[str, dict]:
    requirements = package.get("businessRequirements") or []
    scenarios = package.get("testScenarios") or []
    cases = package.get("testCases") or []
    test_data = package.get("testData") or []
    if any(not row.get("id") for row in scenarios + cases + test_data):
        raise ValueError("Test BR chain package contains a missing scenario, case, or test-data ID")
    case_by_id = {str(row["id"]): row for row in cases if row.get("id")}
    data_by_id = {str(row["id"]): row for row in test_data if row.get("id")}
    if len(case_by_id) != len(cases) or len(data_by_id) != len(test_data):
        raise ValueError("Test BR chain package contains duplicate case or test-data IDs")

    scenarios_by_requirement = collections.defaultdict(set)
    cases_by_scenario = collections.defaultdict(set)
    data_by_case = collections.defaultdict(set)
    reverse_data_by_case = collections.defaultdict(set)
    for scenario in scenarios:
        for requirement_id in scenario.get("requirementIds") or []:
            scenarios_by_requirement[str(requirement_id)].add(str(scenario["id"]))
    for case in cases:
        case_id = str(case["id"])
        for scenario_id in case.get("scenarioIds") or []:
            cases_by_scenario[str(scenario_id)].add(case_id)
        for data_id in case.get("testDataIds") or []:
            if str(data_id) in data_by_id:
                data_by_case[case_id].add(str(data_id))
    for data in test_data:
        for case_id in data.get("testCaseIds") or []:
            if str(case_id) in case_by_id:
                reverse_data_by_case[str(case_id)].add(str(data["id"]))

    result = {}
    for requirement in requirements:
        requirement_id = str(requirement.get("id") or "")
        scenario_ids = sorted(scenarios_by_requirement.get(requirement_id, set()))
        case_ids = sorted({case_id for scenario_id in scenario_ids for case_id in cases_by_scenario[scenario_id]})
        data_ids = sorted({data_id for case_id in case_ids for data_id in data_by_case[case_id]})
        reverse_data_ids = sorted({data_id for case_id in case_ids for data_id in reverse_data_by_case[case_id]})
        routes = [
            {"testScenarioId": scenario_id, "testCaseId": case_id, "testDataId": data_id}
            for scenario_id in scenario_ids
            for case_id in sorted(cases_by_scenario[scenario_id])
            for data_id in sorted(data_by_case[case_id])
        ]
        if routes:
            status = "COMPLETE_BR_TS_TC_TD_CHAIN"
        elif reverse_data_ids:
            status = "BR_TS_TC_WITH_REVERSE_TD_REFERENCE_ONLY"
        elif case_ids:
            status = "BR_TS_TC_CHAIN_WITHOUT_LINKED_TD"
        elif scenario_ids:
            status = "BR_TS_CHAIN_WITHOUT_LINKED_TC"
        else:
            status = "NO_LINKED_TEST_SCENARIO"
        result[requirement_id] = {
            "testBrId": requirement_id,
            "title": requirement.get("title"),
            "status": requirement.get("status"),
            "testScenarioIds": scenario_ids,
            "testCaseIds": case_ids,
            "testDataIds": data_ids,
            "reverseLinkedTestDataIds": reverse_data_ids,
            "completeRoutes": routes,
            "chainStatus": status,
        }
    return result


def tc_td_link_audit(package: dict) -> dict:
    case_ids = {str(row.get("id")) for row in package.get("testCases", []) if row.get("id")}
    test_data_ids = {str(row.get("id")) for row in package.get("testData", []) if row.get("id")}
    forward = {
        (str(case["id"]), str(data_id))
        for case in package.get("testCases", []) for data_id in case.get("testDataIds", [])
        if str(data_id) in test_data_ids
    }
    reverse = {
        (str(case_id), str(data["id"]))
        for data in package.get("testData", []) for case_id in data.get("testCaseIds", [])
        if str(case_id) in case_ids
    }
    return {
        "testCaseDeclaredEdges": len(forward),
        "testDataReverseEdges": len(reverse),
        "reciprocalEdges": len(forward & reverse),
        "caseOnlyEdges": len(forward - reverse),
        "testDataOnlyEdges": len(reverse - forward),
        "completeChainPolicy": "Credit TC→TD only when TC.testDataIds resolves to a package TD; reverse-only TD.testCaseIds are retained but do not qualify a complete chain.",
    }


def ai_anchor_pairs(row: dict) -> set[tuple[str, str]]:
    pairs = set()
    for anchor in row.get("sourceAnchors") or []:
        pair = anchor_pair(anchor.get("segment"), anchor.get("element"))
        if pair:
            pairs.add(pair)
    segments = row.get("candidateSegments") or []
    elements = set(row.get("candidateElements") or [])
    if row.get("inferredElement"):
        elements.add(row["inferredElement"])
    for segment in segments:
        for element in elements:
            pair = anchor_pair(segment, element)
            if pair:
                pairs.add(pair)
    return pairs


def ai_chain_record(row: dict, run4_row: dict | None, suite_chain: dict | None) -> dict:
    if row.get("recordType") == SUPPLEMENTAL_RECORD:
        chain = suite_chain or {}
        return {
            "chainSource": "Run1 Segment 100 suite catalog",
            "chainStatus": chain.get("chainStatus", "NO_LINKED_TEST_SCENARIO"),
            "testScenarioIds": chain.get("testScenarioIds", []),
            "testCaseIds": chain.get("testCaseIds", []),
            "testDataIds": chain.get("testDataIds", []),
            "completeRoutes": chain.get("completeRoutes", []),
        }
    if row.get("cohortId") == RUN1_COHORT:
        return {
            "chainSource": "Run1 legacy prioritized-scenario and matrix catalogs",
            "chainStatus": "NO_DIRECT_CHAIN_FOR_RUN1_LEGACY_BR",
            "testScenarioIds": [],
            "testCaseIds": [],
            "testDataPaths": [],
            "completeRoutes": [],
            "limitation": "The supplied Run1 prioritized scenarios reference the Run2–Run4 BR catalog, not this legacy BR catalog.",
        }

    if not run4_row:
        raise ValueError(f"No Run2–Run4 chain source found for {row.get('aiRequirementId')}")
    links = run4_row.get("matrixAndDirectChain") or {}
    scenario_ids = links.get("directScenarioIds") or []
    case_ids = links.get("directTestCaseIdsViaScenario") or []
    td_paths = links.get("directTcTdPaths") or []
    complete_routes = links.get("directCompleteRoutes") or []
    if complete_routes:
        status = "COMPLETE_DIRECT_BR_TS_TC_TD_CHAIN"
    elif scenario_ids and case_ids:
        status = "BR_TS_TC_CHAIN_WITHOUT_COMPLETE_DIRECT_TD_ROUTE"
    elif scenario_ids:
        status = "BR_TS_CHAIN_WITHOUT_DIRECT_TC"
    else:
        status = "NO_DIRECT_SCENARIO_LINK"
    return {
        "chainSource": "Run4 direct catalog crosswalk; canonical BR/TS/TC/matrix catalogs are byte-identical across Run2–Run4",
        "chainStatus": status,
        "testScenarioIds": scenario_ids,
        "testCaseIds": case_ids,
        "testDataPaths": td_paths,
        "candidateTestDataPaths": links.get("directCandidateTdPaths") or [],
        "metadataJoinedTestDataPaths": links.get("directMetadataJoinedTdPaths") or [],
        "completeRoutes": complete_routes,
        "matrixEvidence": {
            key: links.get(key, 0) for key in (
                "matrixRows", "matrixRowsWithTc", "matrixRowsWithTdDeclaration",
                "matrixFullyTracedRowsWithoutTd", "matrixProposedOrBrokenRows",
            )
        },
    }


def self_test() -> None:
    package = {
        "businessRequirements": [{"id": "BR-1", "title": "Test", "sourceAnchors": []}],
        "testScenarios": [{"id": "TS-1", "requirementIds": ["BR-1"]}],
        "testCases": [{"id": "TC-1", "scenarioIds": ["TS-1"], "testDataIds": ["TD-1"]}],
        "testData": [{"id": "TD-1", "testCaseIds": ["TC-1"]}],
    }
    chain = build_test_chain_index(package)["BR-1"]
    assert chain["chainStatus"] == "COMPLETE_BR_TS_TC_TD_CHAIN"
    assert chain["completeRoutes"] == [{"testScenarioId": "TS-1", "testCaseId": "TC-1", "testDataId": "TD-1"}]
    reverse_only = build_test_chain_index({
        "businessRequirements": [{"id": "BR-2"}],
        "testScenarios": [{"id": "TS-2", "requirementIds": ["BR-2"]}],
        "testCases": [{"id": "TC-2", "scenarioIds": ["TS-2"], "testDataIds": []}],
        "testData": [{"id": "TD-2", "testCaseIds": ["TC-2"]}],
    })["BR-2"]
    assert reverse_only["chainStatus"] == "BR_TS_TC_WITH_REVERSE_TD_REFERENCE_ONLY"
    assert reverse_only["testDataIds"] == [] and reverse_only["reverseLinkedTestDataIds"] == ["TD-2"]
    assert ai_anchor_pairs({"candidateSegments": ["100"], "candidateElements": ["0085"]}) == {("100", "85")}
    print("PASS: chain joins resolve BR→TS→TC→TD and element anchors normalize")


def build(combined_crosswalk: Path, run4_crosswalk: Path, run1_requirements_path: Path,
          run1_suite_path: Path, test_solution_dir: Path, output_dir: Path) -> dict:
    inputs = [combined_crosswalk, run4_crosswalk, run1_requirements_path, run1_suite_path,
              test_solution_dir / "source-derived-requirement-inventory.json",
              test_solution_dir / "all-test-solution-br-ts-tc-td-training-package.json"]
    for path in inputs:
        if not path.is_file():
            raise FileNotFoundError(path)
    if output_dir.exists():
        raise ValueError(f"Refusing to overwrite existing output directory: {output_dir}")

    combined_rows = read_jsonl(combined_crosswalk)
    primary_rows = [row for row in combined_rows if row.get("recordType") == PRIMARY_RECORD]
    supplemental_rows = [row for row in combined_rows if row.get("recordType") == SUPPLEMENTAL_RECORD]
    all_ai_rows = primary_rows + supplemental_rows
    if len({str(row.get("aiRequirementId")) for row in primary_rows}) != len(primary_rows):
        raise ValueError("Primary AI BR crosswalk has duplicate IDs")

    run4_rows = read_jsonl(run4_crosswalk)
    run4_by_id = {str(row["aiRequirementId"]): row for row in run4_rows}
    if len(run4_by_id) != len(run4_rows):
        raise ValueError("Run4 chain crosswalk has duplicate AI BR IDs")
    run1_data = read_json(run1_requirements_path)
    run1_by_id = {str(row["id"]): row for row in run1_data.get("requirements", []) if row.get("id")}
    if len(run1_by_id) != len(run1_data.get("requirements", [])):
        raise ValueError("Run1 legacy requirement catalog has duplicate or missing IDs")
    run1_suite = read_json(run1_suite_path)
    suite_chain_by_id = build_test_chain_index(run1_suite)

    test_solution_dir = Path(test_solution_dir)
    baseline = read_json(test_solution_dir / "source-derived-requirement-inventory.json")
    if baseline.get("authority") != "INDEPENDENT_TEST_SOLUTION" or baseline.get("aiArtifactsIncluded") is not False:
        raise ValueError("Official Test BR baseline is not independent of AI artifacts")
    baseline_rows = baseline.get("businessRequirements") or []
    baseline_by_id = {str(row["ruleId"]): row for row in baseline_rows if row.get("ruleId")}
    if len(baseline_by_id) != baseline.get("ruleCount"):
        raise ValueError("Official Test BR baseline has duplicate or missing rules")

    training_package = read_json(test_solution_dir / "all-test-solution-br-ts-tc-td-training-package.json")
    training_chains = build_test_chain_index(training_package)
    training_brs = {str(row["id"]): row for row in training_package.get("businessRequirements", []) if row.get("id")}
    if len(training_brs) != len(training_package.get("businessRequirements", [])):
        raise ValueError("Generated Test BR training package has duplicate or missing BR IDs")
    training_anchor_index = collections.defaultdict(set)
    training_anchor_pairs = {}
    for test_br_id, test_br in training_brs.items():
        pairs = record_anchor_pairs(test_br)
        training_anchor_pairs[test_br_id] = pairs
        for pair in pairs:
            training_anchor_index[pair].add(test_br_id)

    rows = []
    primary_ids = set()
    for source_row in all_ai_rows:
        requirement_id = str(source_row.get("aiRequirementId") or "")
        cohort_id = source_row.get("cohortId")
        if source_row.get("recordType") == PRIMARY_RECORD:
            primary_ids.add(requirement_id)
        if cohort_id == RUN1_COHORT:
            source_requirement = run1_by_id.get(requirement_id)
            if not source_requirement:
                raise ValueError(f"Run1 source BR not found: {requirement_id}")
            statement = source_requirement.get("statement")
        elif source_row.get("recordType") == SUPPLEMENTAL_RECORD:
            statement = source_row.get("statement")
        else:
            statement = run4_by_id.get(requirement_id, {}).get("aiStatement")
        if cohort_id == SHARED_COHORT and requirement_id not in run4_by_id:
            raise ValueError(f"Run2–Run4 chain source not found: {requirement_id}")

        source_pairs = ai_anchor_pairs(source_row)
        training_ids = set().union(*(training_anchor_index.get(pair, set()) for pair in source_pairs)) if source_pairs else set()
        generated_candidates = []
        for test_br_id in sorted(training_ids):
            test_br = training_brs[test_br_id]
            chain = training_chains[test_br_id]
            generated_candidates.append({
                "testBrId": test_br_id,
                "title": test_br.get("title"),
                "status": test_br.get("status"),
                "matchedAnchorPairs": [list(pair) for pair in sorted(source_pairs & training_anchor_pairs[test_br_id])],
                "chain": chain,
            })

        official_candidates = source_row.get("candidateTestSolutionRules") or []
        if source_row.get("recordType") == SUPPLEMENTAL_RECORD:
            official_candidate_ids = set()
            for test_br in baseline_rows:
                test_anchor = anchor_pair(
                    (test_br.get("sourceAnchor") or {}).get("segment"),
                    (test_br.get("sourceAnchor") or {}).get("element"),
                )
                if test_anchor and test_anchor in source_pairs:
                    official_candidate_ids.add(str(test_br["ruleId"]))
            official_candidates = [
                {
                    "ruleId": rule_id,
                    "title": baseline_by_id[rule_id].get("title"),
                    "atomicity": None,
                    "sourceAnchor": baseline_by_id[rule_id].get("sourceAnchor"),
                }
                for rule_id in sorted(official_candidate_ids)
            ]

        chain = ai_chain_record(
            source_row,
            run4_by_id.get(requirement_id),
            suite_chain_by_id.get(requirement_id) if source_row.get("recordType") == SUPPLEMENTAL_RECORD else None,
        )
        rows.append({
            "recordType": "AI_BR_CHAIN_AND_MAXIMUM_COVERAGE_AUDIT",
            "aiRequirementId": requirement_id,
            "aiRecordCohort": cohort_id,
            "recordScope": "PRIMARY_AI_CATALOG" if source_row.get("recordType") == PRIMARY_RECORD else "SUPPLEMENTAL_RUN1_SUITE_CATALOG",
            "sourceRunIds": source_row.get("sourceRunIds") or ["Run1"],
            "aiStatement": statement,
            "aiSourceRuleId": source_row.get("aiSourceRuleId", source_row.get("sourceRuleId")),
            "aiSourceAnchorCandidateStatus": source_row.get("sourceAnchorCandidateStatus"),
            "aiSourceAnchorPairs": [list(pair) for pair in sorted(source_pairs)],
            "aiChain": chain,
            "officialIndependentTestBrCandidates": official_candidates,
            "generatedTestBrChainCandidates": generated_candidates,
            "matchBasis": "Exact normalized source segment + numeric element anchor; all matches remain candidates, not semantic confirmations.",
            "semanticMatchStatus": "NOT_ASSESSED",
        })

    if len(primary_ids) != len(primary_rows):
        raise ValueError("Primary AI BR IDs did not reconcile to output audit rows")
    run4_primary = {str(row["aiRequirementId"]) for row in primary_rows if row.get("cohortId") == SHARED_COHORT}
    if run4_primary != set(run4_by_id):
        raise ValueError("Run2–Run4 AI BRs do not reconcile to the direct-chain crosswalk")

    primary_audit = [row for row in rows if row["recordScope"] == "PRIMARY_AI_CATALOG"]
    anchor_matched = [row for row in primary_audit if row["officialIndependentTestBrCandidates"]]
    complete_chain = [row for row in primary_audit if row["aiChain"]["chainStatus"] == "COMPLETE_DIRECT_BR_TS_TC_TD_CHAIN"]
    complete_chain_matches = [row for row in anchor_matched if row["aiChain"]["chainStatus"] == "COMPLETE_DIRECT_BR_TS_TC_TD_CHAIN"]
    all_official_target_ids = {
        candidate["ruleId"] for row in anchor_matched for candidate in row["officialIndependentTestBrCandidates"]
    }
    complete_chain_official_target_ids = {
        candidate["ruleId"] for row in complete_chain_matches for candidate in row["officialIndependentTestBrCandidates"]
    }
    all_training_target_ids = {
        candidate["testBrId"] for row in primary_audit for candidate in row["generatedTestBrChainCandidates"]
    }
    complete_chain_training_target_ids = {
        candidate["testBrId"] for row in primary_audit
        if row["aiChain"]["chainStatus"] == "COMPLETE_DIRECT_BR_TS_TC_TD_CHAIN"
        for candidate in row["generatedTestBrChainCandidates"]
    }
    complete_test_chain_ids = {
        test_br_id for test_br_id, chain in training_chains.items()
        if chain["chainStatus"] == "COMPLETE_BR_TS_TC_TD_CHAIN"
    }
    both_sides_complete_target_ids = complete_chain_training_target_ids & complete_test_chain_ids
    if not all_official_target_ids <= set(baseline_by_id):
        raise ValueError("A matched official Test BR is outside the 601-rule baseline")

    primary_count = len(primary_audit)
    baseline_count = int(baseline.get("ruleCount", 0))
    summary = {
        "artifact": "ATL105-AI-BR-CHAIN-MAXIMUM-COVERAGE-AUDIT",
        "status": "ALL_AI_BRS_CHAIN_AUDITED_SOURCE_ANCHOR_CANDIDATE_COVERAGE",
        "method": "Enumerate every exact normalized segment+numeric-element source-anchor candidate. Preserve all candidate matches (including one-to-many) and the direct AI BR→TS→TC→TD chain. Candidate reach is an upper-bound source-anchor assessment, not semantic equivalence or final test approval.",
        "smeApprovalRequiredForCoverageCalculation": False,
        "primaryAiCatalog": {
            "aiBrCount": primary_count,
            "Run1LegacyBrCount": sum(row.get("aiRecordCohort") == RUN1_COHORT for row in primary_audit),
            "Run2Run4UniqueBrCount": sum(row.get("aiRecordCohort") == SHARED_COHORT for row in primary_audit),
            "aiBrsWithOfficialTestBrCandidates": len(anchor_matched),
            "aiBrCandidatePercent": round(100 * len(anchor_matched) / primary_count, 2) if primary_count else None,
            "aiBrsWithCompleteDirectChain": len(complete_chain),
            "candidateAiBrsWithCompleteDirectChain": len(complete_chain_matches),
            "aiBrsWithGeneratedTestBrChainCandidates": sum(bool(row["generatedTestBrChainCandidates"]) for row in primary_audit),
        },
        "officialIndependentTestBrBaseline": {
            "ruleCount": baseline_count,
            "testBrsReachedByAnyAiCandidate": len(all_official_target_ids),
            "maximumSourceAnchorCandidateCoveragePercent": round(100 * len(all_official_target_ids) / baseline_count, 2) if baseline_count else None,
            "testBrsReachedByAiWithCompleteDirectChain": len(complete_chain_official_target_ids),
            "completeChainQualifiedCandidateCoveragePercent": round(100 * len(complete_chain_official_target_ids) / baseline_count, 2) if baseline_count else None,
        },
        "generatedTestBrChainPackage": {
            "businessRequirementCount": len(training_brs),
            "testScenarioCount": len(training_package.get("testScenarios") or []),
            "testCaseCount": len(training_package.get("testCases") or []),
            "testDataCount": len(training_package.get("testData") or []),
            "tcTdLinkAudit": tc_td_link_audit(training_package),
            "testBrsWithCompleteOwnChain": sum(chain["chainStatus"] == "COMPLETE_BR_TS_TC_TD_CHAIN" for chain in training_chains.values()),
            "testBrsReachedByAiSourceAnchorCandidates": len(all_training_target_ids),
            "maximumSourceAnchorCandidateCoveragePercent": round(100 * len(all_training_target_ids) / len(training_brs), 2) if training_brs else None,
            "testBrsReachedByAiWithCompleteDirectChain": len(complete_chain_training_target_ids),
            "testBrsReachedByAiWithCompleteDirectChainAndCompleteOwnChain": len(both_sides_complete_target_ids),
            "bothSidesCompleteChainCoveragePercent": round(100 * len(both_sides_complete_target_ids) / len(training_brs), 2) if training_brs else None,
        },
        "supplementalRun1SuiteBrRows": len(rows) - len(primary_audit),
        "semanticEquivalence": {"confirmedMatches": 0, "status": "NOT_ASSESSED"},
        "sourceHashes": {str(path.name): sha256(path) for path in inputs},
        "limitations": [
            "Source-anchor candidate matches may be composite or semantically different; all candidate links are retained without confirmation.",
            "Run1's 6,240 legacy BRs have no direct TS/TC/TD links in the supplied Run1 prioritized-scenario/matrix join; the Run1 scenario IDs resolve to the Run2–Run4 catalog.",
            "The 725-row generated Test BR chain package is a separate training artifact with mixed statuses; its candidates are not merged into the official 601-rule denominator.",
            "A structural BR→TS→TC→TD route does not establish that the test validates the complete AI BR meaning or that the test passed.",
        ],
    }

    output_dir.mkdir(parents=True, exist_ok=False)
    write_jsonl(output_dir / "ai-br-chain-maximum-coverage-crosswalk.jsonl", rows)
    (output_dir / "ai-br-chain-maximum-coverage-summary.json").write_text(
        json.dumps(summary, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    return summary


def main() -> None:
    parser = argparse.ArgumentParser(description="Audit every AI BR chain and maximum source-anchor Test BR coverage.")
    parser.add_argument("--combined-crosswalk", type=Path)
    parser.add_argument("--run4-crosswalk", type=Path)
    parser.add_argument("--run1-requirements", type=Path)
    parser.add_argument("--run1-suite", type=Path)
    parser.add_argument("--test-solution-dir", type=Path)
    parser.add_argument("--output-dir", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
        return
    required = (args.combined_crosswalk, args.run4_crosswalk, args.run1_requirements,
                args.run1_suite, args.test_solution_dir, args.output_dir)
    if not all(required):
        parser.error("all input paths and --output-dir are required unless --self-test is used")
    summary = build(*required)
    print(json.dumps({
        "outputDirectory": str(args.output_dir),
        "primaryAiBrCount": summary["primaryAiCatalog"]["aiBrCount"],
        "officialTestBrAnchorCoveragePercent": summary["officialIndependentTestBrBaseline"]["maximumSourceAnchorCandidateCoveragePercent"],
        "completeChainQualifiedCoveragePercent": summary["officialIndependentTestBrBaseline"]["completeChainQualifiedCandidateCoveragePercent"],
        "bothSidesCompleteTrainingChainCoveragePercent": summary["generatedTestBrChainPackage"]["bothSidesCompleteChainCoveragePercent"],
        "semanticEquivalence": summary["semanticEquivalence"],
    }, indent=2))


if __name__ == "__main__":
    main()