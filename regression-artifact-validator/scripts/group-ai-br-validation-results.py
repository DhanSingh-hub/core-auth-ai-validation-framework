#!/usr/bin/env python3
"""Group AI BRs by chain completeness and attach bounded Run4 rule outcomes."""

import argparse
import collections
import hashlib
import json
from pathlib import Path


PRIMARY_SCOPE = "PRIMARY_AI_CATALOG"
EXPECTED_NEGATIVE_DISPOSITION = "EXPECTED_DECLARED_NEGATIVE_TARGET_DETECTION"


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


def chain_group(row: dict) -> str:
    if row.get("recordScope") != PRIMARY_SCOPE:
        return "SUPPLEMENTAL_RUN1_SUITE"
    chain_status = (row.get("aiChain") or {}).get("chainStatus")
    prefix = (
        "FULL_CHAIN" if chain_status == "COMPLETE_DIRECT_BR_TS_TC_TD_CHAIN"
        else "PARTIAL_CHAIN" if chain_status == "BR_TS_CHAIN_WITHOUT_DIRECT_TC"
        else "NO_DIRECT_CHAIN"
    )
    suffix = "WITH_TEST_BR_CANDIDATE" if row.get("officialIndependentTestBrCandidates") else "NO_TEST_BR_CANDIDATE"
    return prefix + "_" + suffix


def outcome_group(case_ids: list[str], evaluations: list[dict]) -> str:
    if not case_ids:
        return "NO_DIRECT_TC_LINK"
    if not evaluations:
        return "NO_MATCHING_FIELD_RULE_EVALUATION"
    outcomes = collections.Counter(row.get("status") for row in evaluations)
    dispositions = {row.get("failureDisposition") for row in evaluations if row.get("status") == "FAIL"}
    if outcomes["FAIL"] and dispositions - {EXPECTED_NEGATIVE_DISPOSITION}:
        return "FAILURE_CANDIDATES_REQUIRE_REVIEW"
    if outcomes["FAIL"] and outcomes["REVIEW_REQUIRED"]:
        return "EXPECTED_NEGATIVE_DETECTION_WITH_REVIEW_REQUIRED"
    if outcomes["FAIL"]:
        return "EXPECTED_NEGATIVE_DETECTIONS_NO_UNEXPECTED_FAILURE"
    if outcomes["REVIEW_REQUIRED"] and outcomes["PASS"]:
        return "BOUNDED_PASS_WITH_REVIEW_REQUIRED"
    if outcomes["REVIEW_REQUIRED"]:
        return "REVIEW_REQUIRED_ONLY"
    if outcomes["PASS"]:
        return "BOUNDED_CHECKS_PASS_ONLY"
    return "NO_CLASSIFIABLE_RULE_OUTCOME"


def case_evidence(case_ids: list[str], evaluations_by_case: dict[str, list[dict]]) -> dict:
    linked_cases = sorted(set(case_ids))
    matched_cases = [case_id for case_id in linked_cases if evaluations_by_case.get(case_id)]
    rows = [row for case_id in linked_cases for row in evaluations_by_case.get(case_id, [])]
    outcomes = collections.Counter(str(row.get("status") or "UNKNOWN") for row in rows)
    dispositions = collections.Counter(
        str(row.get("failureDisposition") or "UNKNOWN")
        for row in rows if row.get("status") == "FAIL"
    )
    return {
        "linkedDirectTestCaseIds": linked_cases,
        "testCaseIdsWithBoundedRuleEvaluations": matched_cases,
        "testCaseIdsWithoutMatchingEvaluationRows": sorted(set(linked_cases) - set(matched_cases)),
        "evaluationRowCountAttributedToBr": len(rows),
        "outcomes": dict(sorted(outcomes.items())),
        "failureDispositions": dict(sorted(dispositions.items())),
        "evaluatedRuleIds": sorted({str(row.get("ruleId")) for row in rows if row.get("ruleId")}),
        "validationOutcomeGroup": outcome_group(linked_cases, rows),
    }


def self_test() -> None:
    assert chain_group({
        "recordScope": PRIMARY_SCOPE,
        "aiChain": {"chainStatus": "COMPLETE_DIRECT_BR_TS_TC_TD_CHAIN"},
        "officialIndependentTestBrCandidates": [{"ruleId": "SEG100-R-001"}],
    }) == "FULL_CHAIN_WITH_TEST_BR_CANDIDATE"
    assert outcome_group(["TC-1"], [{"status": "FAIL", "failureDisposition": EXPECTED_NEGATIVE_DISPOSITION}]) == "EXPECTED_NEGATIVE_DETECTIONS_NO_UNEXPECTED_FAILURE"
    assert outcome_group(["TC-1"], [
        {"status": "FAIL", "failureDisposition": EXPECTED_NEGATIVE_DISPOSITION},
        {"status": "REVIEW_REQUIRED", "failureDisposition": "NOT_A_FAILURE"},
    ]) == "EXPECTED_NEGATIVE_DETECTION_WITH_REVIEW_REQUIRED"
    assert outcome_group(["TC-1"], [{"status": "FAIL", "failureDisposition": "NON_NEGATIVE_VIOLATION_CANDIDATE"}]) == "FAILURE_CANDIDATES_REQUIRE_REVIEW"
    assert outcome_group(["TC-1"], []) == "NO_MATCHING_FIELD_RULE_EVALUATION"
    print("PASS: chain grouping and bounded failure dispositions remain distinct")


def build(chain_audit_path: Path, field_report_path: Path, module_root: Path, output_dir: Path) -> dict:
    module_root = Path(module_root)
    source_inventory = module_root / "specifications/ATL105/test-output/test-solution-independent-review/source-derived-requirement-inventory.json"
    validation_profiles = module_root / "specifications/ATL105/docs/specs/kb/elements/validation-profiles.json"
    for path in (chain_audit_path, field_report_path, source_inventory, validation_profiles):
        if not path.is_file():
            raise FileNotFoundError(path)
    if output_dir.exists():
        raise ValueError(f"Refusing to overwrite existing output directory: {output_dir}")

    chain_rows = read_jsonl(chain_audit_path)
    primary_rows = [row for row in chain_rows if row.get("recordScope") == PRIMARY_SCOPE]
    if len({row.get("aiRequirementId") for row in primary_rows}) != len(primary_rows):
        raise ValueError("Chain audit contains duplicate primary AI BR IDs")
    report = read_json(field_report_path)
    expected_hashes = {
        "sourceRuleInventory": sha256(source_inventory),
        "validationProfiles": sha256(validation_profiles),
    }
    if report.get("inputSha256") != expected_hashes:
        raise ValueError("Run4 field-structure report does not match current source inventory/profiles")
    if report.get("runId") != "Run4-2026-10-07":
        raise ValueError(f"Unexpected field-structure validation run: {report.get('runId')}")
    evaluations_by_case = collections.defaultdict(list)
    for evaluation in report.get("evaluations") or []:
        case_id = str(evaluation.get("testCaseId") or "")
        if case_id:
            evaluations_by_case[case_id].append(evaluation)

    rows = []
    group_stats = collections.defaultdict(lambda: {
        "businessRequirementCount": 0,
        "outcomeGroups": collections.Counter(),
        "attributedOutcomes": collections.Counter(),
        "attributedFailureDispositions": collections.Counter(),
        "linkedTestCaseIds": set(),
        "testCaseIdsWithEvaluationRows": set(),
        "attributedEvaluationRowCount": 0,
    })
    for source in chain_rows:
        chain = source.get("aiChain") or {}
        evidence = case_evidence(chain.get("testCaseIds") or [], evaluations_by_case)
        group = chain_group(source)
        row = {
            "recordType": "AI_BR_GROUPED_VALIDATION_RESULT",
            "aiRequirementId": source.get("aiRequirementId"),
            "recordScope": source.get("recordScope"),
            "aiRecordCohort": source.get("aiRecordCohort"),
            "chainGroup": group,
            "aiChainStatus": chain.get("chainStatus"),
            "aiTestScenarioIds": chain.get("testScenarioIds") or [],
            "aiTestCaseIds": chain.get("testCaseIds") or [],
            "aiTestDataPathsOrIds": chain.get("testDataPaths") or chain.get("testDataIds") or [],
            "officialTestBrCandidateIds": sorted({
                str(candidate.get("ruleId"))
                for candidate in source.get("officialIndependentTestBrCandidates") or []
                if candidate.get("ruleId")
            }),
            "generatedTestBrChainCandidateIds": sorted({
                str(candidate.get("testBrId"))
                for candidate in source.get("generatedTestBrChainCandidates") or []
                if candidate.get("testBrId")
            }),
            "boundedValidationEvidence": evidence,
            "interpretation": "Only source-profile checks represented in the Run4 field-structure report are counted. PASS is bounded to that check; FAIL is a defect/detection candidate classified by disposition; no row-level evaluation is not evidence that the case was never inspected.",
        }
        rows.append(row)
        stats = group_stats[group]
        stats["businessRequirementCount"] += 1
        stats["outcomeGroups"][evidence["validationOutcomeGroup"]] += 1
        stats["attributedOutcomes"].update(evidence["outcomes"])
        stats["attributedFailureDispositions"].update(evidence["failureDispositions"])
        stats["linkedTestCaseIds"].update(evidence["linkedDirectTestCaseIds"])
        stats["testCaseIdsWithEvaluationRows"].update(evidence["testCaseIdsWithBoundedRuleEvaluations"])
        stats["attributedEvaluationRowCount"] += evidence["evaluationRowCountAttributedToBr"]

    primary_count = len(primary_rows)
    group_summary = {}
    for name, stats in sorted(group_stats.items()):
        group_summary[name] = {
            "businessRequirementCount": stats["businessRequirementCount"],
            "validationOutcomeGroups": dict(sorted(stats["outcomeGroups"].items())),
            "distinctDirectTestCaseIdsAttributed": len(stats["linkedTestCaseIds"]),
            "distinctTestCaseIdsWithBoundedEvaluationRows": len(stats["testCaseIdsWithEvaluationRows"]),
            "brAttributedEvaluationRowCountMayRepeatAcrossGroups": stats["attributedEvaluationRowCount"],
            "brAttributedBoundedOutcomesMayRepeatAcrossGroups": dict(sorted(stats["attributedOutcomes"].items())),
            "brAttributedFailureDispositionsMayRepeatAcrossGroups": dict(sorted(stats["attributedFailureDispositions"].items())),
        }
    primary_linked_cases = {
        case_id for row in rows if row.get("recordScope") == PRIMARY_SCOPE for case_id in row["aiTestCaseIds"]
    }
    primary_cases_with_eval = primary_linked_cases & set(evaluations_by_case)
    summary = {
        "artifact": "ATL105-AI-BR-GROUPED-VALIDATION-RESULTS",
        "status": "BOUNDED_RUN4_VALIDATION_GROUPS_GENERATED",
        "groupingPolicy": "Primary AI BRs are grouped by chain completeness and presence of an official independent Test BR source-anchor candidate. Supplemental Run1 suite BRs remain separate.",
        "primaryAiBrCount": primary_count,
        "supplementalRun1SuiteBrCount": len(rows) - primary_count,
        "sourceReportHashes": {
            "chainAuditSha256": sha256(chain_audit_path),
            "run4FieldStructureReportSha256": sha256(field_report_path),
        },
        "primaryGroupCountCheck": sum(group_summary[name]["businessRequirementCount"] for name in group_summary if not name.startswith("SUPPLEMENTAL")),
        "groups": group_summary,
        "run4FieldStructureValidation": {
            "runId": report.get("runId"),
            "sourceReportSha256": sha256(field_report_path),
            "status": report.get("status"),
            "messagesAssessed": report.get("messagesAssessed"),
            "inventoryRuleCount": report.get("inventoryRuleCount"),
            "executableProfileRuleCount": report.get("executableProfileRuleCount"),
            "partialStructureRuleCount": report.get("partialStructureRuleCount"),
            "outcomesByClass": report.get("outcomesByClass"),
            "failuresByDisposition": report.get("failuresByDisposition"),
            "distinctAiLinkedTestCaseIds": len(primary_linked_cases),
            "distinctAiLinkedTestCaseIdsWithBoundedEvaluationRows": len(primary_cases_with_eval),
            "distinctAiLinkedTestCaseIdsWithoutMatchingEvaluationRows": len(primary_linked_cases - primary_cases_with_eval),
            "inputHashesVerified": expected_hashes,
        },
        "limitations": [
            "Run4 field-structure PASS/FAIL states are bounded profile checks, not whole-test outcomes or semantic approval.",
            "FAIL on a declared negative target is recorded as a detection candidate; unrelated or non-negative failures remain review candidates.",
            "No matching evaluation row means this field-structure report had no matching rule-evaluation row for that linked TC; it does not prove the TC was never inspected.",
            "Run1 legacy BRs without direct TS/TC links cannot receive per-chain Run4 field outcomes; they remain in explicit no-chain groups.",
            "Group-level attributed evaluation counts can repeat when one TC is linked to multiple BRs; distinct TC counts are provided separately.",
        ],
    }
    if primary_count != 11393 or summary["primaryGroupCountCheck"] != primary_count:
        raise ValueError("Grouped primary AI BR total does not reconcile to the chain audit")

    output_dir.mkdir(parents=True, exist_ok=False)
    with (output_dir / "ai-br-grouped-validation-results.jsonl").open("w", encoding="utf-8", newline="\n") as stream:
        for row in rows:
            stream.write(json.dumps(row, ensure_ascii=True, sort_keys=True) + "\n")
    (output_dir / "ai-br-grouped-validation-summary.json").write_text(
        json.dumps(summary, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    return summary


def main() -> None:
    parser = argparse.ArgumentParser(description="Group AI BRs by chain completeness and bounded validation outcomes.")
    parser.add_argument("--chain-audit", type=Path)
    parser.add_argument("--field-structure-report", type=Path)
    parser.add_argument("--module-root", type=Path)
    parser.add_argument("--output-dir", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
        return
    required = (args.chain_audit, args.field_structure_report, args.module_root, args.output_dir)
    if not all(required):
        parser.error("all report paths and --output-dir are required unless --self-test is used")
    summary = build(*required)
    print(json.dumps({
        "outputDirectory": str(args.output_dir),
        "primaryAiBrCount": summary["primaryAiBrCount"],
        "groups": {name: value["businessRequirementCount"] for name, value in summary["groups"].items()},
        "fieldStructureRun": summary["run4FieldStructureValidation"]["runId"],
    }, indent=2))


if __name__ == "__main__":
    main()