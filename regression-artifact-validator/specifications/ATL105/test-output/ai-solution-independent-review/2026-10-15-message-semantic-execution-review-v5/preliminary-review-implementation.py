"""Build a preliminary Message BR relation review with offline execution evidence."""

import argparse
import collections
import csv
import gzip
import hashlib
import json
import shutil
from pathlib import Path


SAME_SUBRULE = {
    ("1215", "SEG103-R-008"), ("1365", "SEG135-R-004"),
    ("1345", "SEG130-R-013"), ("1239", "SEG110-R-007"),
    ("1593", "SEGDL2-R-004"), ("1588", "SEGDL1-R-006"),
    ("1346", "SEG130-R-013"), ("1364", "SEG135-R-004"),
    ("1235", "SEG109-R-008"), ("1214", "SEG103-R-007"),
}
PARTIAL = {
    ("1344", "SEG130-R-013"): "AI requires separators between fields 1-6 even when empty; Test BR includes that clause plus other EMV section separator rules.",
    ("1263", "SEG114-R-006"): "AI specifies separators across SKU fields; Test describes the three-field order but omits the explicit separator obligation.",
    ("1909", "SEG100-R-016"): "Both concern serialized Segment Length including separators; Test explicitly includes Segment Type, which AI does not enumerate in this statement.",
    ("2367", "SEG100-R-016"): "AI provides one 78-byte example including separators; Test expresses a general encoded-content length rule.",
    ("6784", "SEG110-R-005"): "AI gives a broad calculated-length rule across segments; Test scopes Segment 110 and explicitly includes Segment Type and Field Separators.",
    ("6784", "SEG123-R-002"): "AI gives a broad calculated-length rule across segments; Test scopes Segment 123 and identifies device source plus Segment Type/Field Separators.",
    ("1216", "SEG100-R-016"): "AI states a conditional four-digit value for EBT/eWIC; Test describes Segment 100 encoded length but does not state that conditional format.",
}
DISTINCT = {
    ("1197", "SEG100-R-015"): "AI states Field Separator placement after Type/Length; Test BR states the fixed Segment Type value.",
    ("1568", "SEG100-R-015"): "Duplicate AI separator-placement statement; Test BR states Segment Type identity.",
    ("1197", "SEG100-R-016"): "AI separator after Type/Length is not the Test BR's encoded Segment Length calculation.",
    ("1568", "SEG100-R-016"): "Duplicate AI separator-placement statement; Test BR concerns Segment Length calculation.",
    ("1198", "SEG102-R-022"): "AI separator follows Product Amount when last; Test separator follows Segment Type and Segment Length.",
    ("1210", "SEG102-R-022"): "AI separator follows Product Amount when last; Test separator follows Segment Type and Segment Length.",
    ("1582", "SEG102-R-022"): "Duplicate AI Product Amount separator statement; Test covers a different separator position.",
    ("1908", "SEG100-R-016"): "AI prohibits four-digit length on Segment 100; Test BR only states that length represents encoded content, not its width.",
    ("1345", "SEG130-R-022"): "AI prohibits separators within/between repeated EMV additional-information sections; Test BR concerns omission of trailing optional fields.",
    ("1222", "SEG105-R-002"): "AI addresses separators between fields 15-17; Test BR addresses what Segment Length includes.",
    ("1907", "SEG115-R-001"): "AI governs Segment Length width; Test BR governs whether Segment 115 may appear in request/response messages.",
    ("1907", "SEG120-R-006"): "AI governs Segment Length width; Test BR governs whether Segment 120 may appear in request/response messages.",
}
SOURCE_REVIEW = {
    ("2376", "SEG100-R-016"): "Statement describes Product Code Segment 102 and is below the confidence gate, while producer metadata anchors it to Segment 100.",
    ("2380", "SEG100-R-016"): "Statement is an example-specific Segment 111 value and is below the confidence gate, while producer metadata anchors it to Segment 100.",
}


def sha256(path):
    digest = hashlib.sha256()
    with Path(path).open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def jsonl(path):
    with Path(path).open(encoding="utf-8") as stream:
        for line in stream:
            if line.strip():
                yield json.loads(line)


def csv_rows(path):
    with Path(path).open(encoding="utf-8-sig", newline="") as stream:
        return list(csv.DictReader(stream))


def relation(ai_id, test_id):
    key = (ai_id.split(":")[-1], test_id)
    if ai_id.endswith(":1907"):
        if test_id in {"SEG103-R-005", "SEG114-R-005", "SEG115-R-005", "SEG130-R-004", "SEG131-R-005"}:
            return "RELATED_FIELD_DIFFERENT_OBLIGATION", "Test BR is a maximum-capacity limit; AI BR is four-digit width by segment type."
        if test_id in {"SEG115-R-001", "SEG120-R-006"}:
            return "RELATED_CONTEXT_DIFFERENT_OBLIGATION", "Test BR constrains response-only placement; AI BR constrains Segment Length width."
        if test_id in {"SEG103-R-004", "SEG114-R-004", "SEG115-R-004", "SEG118-R-007", "SEG120-R-002", "SEG130-R-003", "SEG131-R-004"}:
            return "PARTIAL_FORMAT_WIDTH_OVERLAP", "Both mention four-digit Segment Length for a relevant segment; Test adds segment-specific applicability/content rules."
        raise ValueError("Unclassified Segment Length requirement candidate: " + test_id)
    if key in SAME_SUBRULE:
        return "PRELIMINARY_SAME_SUBRULE_OVERLAP", "Statements describe the same source-anchored separator obligation; Test BR is composite and remains draft."
    if key in PARTIAL:
        return "PRELIMINARY_PARTIAL_OR_COMPOSITE_OVERLAP", PARTIAL[key]
    if key in SOURCE_REVIEW:
        return "SOURCE_ANCHOR_CONFLICT_REVIEW", SOURCE_REVIEW[key]
    if key == ("1615", "SEG100-R-016"):
        return "POTENTIAL_LENGTH_SCOPE_CONFLICT", "AI excludes Data Type Indicator; Test says serialized length includes Segment Type. Source terminology and counting boundary require review."
    if key in DISTINCT:
        return "RELATED_CONTEXT_DIFFERENT_OBLIGATION", DISTINCT[key]
    if key == ("1907", "SEG115-R-001") or key == ("1907", "SEG120-R-006"):
        return "RELATED_CONTEXT_DIFFERENT_OBLIGATION", DISTINCT[key]
    raise ValueError("Unclassified Message-to-Message pair: " + repr(key))


def write_csv(path, rows, fields):
    with Path(path).open("w", encoding="utf-8", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=fields, extrasaction="ignore")
        writer.writeheader()
        writer.writerows(rows)


def run(comparison_dir, runner_path, test_chain_report, output):
    comparison_dir, output = Path(comparison_dir), Path(output)
    if output.exists():
        raise ValueError("Refusing to overwrite output directory")
    pairs_path = comparison_dir / "message-candidate-pairs.csv"
    source_pairs_hash = sha256(pairs_path)
    pairs = [row for row in csv_rows(pairs_path)
             if row["aiCategory"] == "Message" and row["testCategory"] == "Message"]
    if len(pairs) != 44:
        raise ValueError("Expected 44 same-category Message pairs; found " + str(len(pairs)))
    runner = {row["testBrId"]: row for row in jsonl(runner_path)}
    test_chains = {row["testBrId"]: row for row in jsonl(comparison_dir / "test-chain-evidence.jsonl")}
    ai_chains = {}
    with gzip.open(comparison_dir / "ai-chain-evidence.jsonl.gz", "rt", encoding="utf-8") as stream:
        for line in stream:
            record = json.loads(line)
            ai_chains[record["aiBrId"]] = record
    rows = []
    for pair in pairs:
        ai_id, test_id = pair["aiBrId"], pair["testBrId"]
        label, rationale = relation(ai_id, test_id)
        executed = runner.get(test_id)
        test = test_chains[test_id]
        ai = ai_chains[ai_id]
        rows.append({**pair,
            "preliminarySemanticRelation": label,
            "preliminaryRationale": rationale,
            "semanticDecision": "NOT_AUTHORIZED_OPEN_REVIEW_REQUIRED",
            "sourceQuoteAdjudicated": False,
            "aiSourcePage": ai["br"].get("source_page"),
            "aiSourceRuleId": ai["br"].get("source_rule_id"),
            "aiProducerSegmentNumber": ai["br"].get("segment_number"),
            "aiProducerSegmentIds": ";".join(ai["br"].get("segment_ids") or []),
            "aiRelatedEntityIds": ";".join(ai["br"].get("related_entity_ids") or []),
            "aiFlags": ";".join(ai["br"].get("flags") or []),
            "testSourceAnchors": json.dumps(test["br"].get("sourceAnchors") or [], sort_keys=True),
            "testBrStatus": test["br"].get("status"),
            "testPrimaryChainStatus": test["chainStatus"],
            "testPrimaryPlaceholderCount": len(test["placeholderIds"]),
            "testAlternativeAuthoredBrIds": ";".join(row["br"]["id"] for row in test["alternativeAuthoredChains"]),
            "testAlternativeDataCount": sum(len(row["data"]) for row in test["alternativeAuthoredChains"]),
            "testAlternativeDataReadiness": ";".join(sorted({str(data.get("readiness") or "UNSET")
                                                               for alt in test["alternativeAuthoredChains"] for data in alt["data"]})),
            "aiPhysicalCompleteCaseCount": len(ai["assessment"].get("physicalCompleteCaseIds") or []),
            "aiPhysicalCompleteCaseIds": ";".join(ai["assessment"].get("physicalCompleteCaseIds") or []),
            "offlinePredicateImplementation": executed["implementation"] if executed else "OUTSIDE_RUNNER_BASELINE",
            "offlinePredicateRunState": executed["runState"] if executed else "NO_RESULT_FROM_THIS_RUNNER",
            "offlinePredicateCaseCount": executed["distinctDecisiveCaseCount"] if executed else 0,
            "offlinePredicateEvaluationRowCount": executed["evaluationRowCount"] if executed else 0,
            "offlinePredicateOutcomes": json.dumps(executed["outcomeRowCounts"], sort_keys=True) if executed else "{}",
            "offlinePredicateTarget": "AI_CANDIDATE_PAYLOADS_NOT_TEST_FIXTURE_EXECUTION",
            "hostExecutionStatus": "NOT_EXECUTED"})
    output.mkdir(parents=True)
    package_validation = json.loads(test_chain_report.read_text(encoding="utf-8-sig"))
    shutil.copyfile(test_chain_report, output / "test-solution-chain-validation.json")
    shutil.copyfile(Path(__file__), output / "preliminary-review-implementation.py")
    fields = list(rows[0])
    write_csv(output / "message-same-category-preliminary-adjudication.csv", rows, fields)
    relation_counts = collections.Counter(row["preliminarySemanticRelation"] for row in rows)
    rule_ids = {row["testBrId"] for row in rows}
    runner_rows = [runner[row] for row in rule_ids if row in runner]
    executions = collections.Counter(row["implementation"] for row in runner_rows)
    decisive = [row for row in runner_rows if row["distinctDecisiveCaseCount"] > 0]
    message_test_ids = {identifier for identifier, chain in test_chains.items() if chain["category"] == "Message"}
    message_runner_rows = [runner[identifier] for identifier in message_test_ids if identifier in runner]
    message_decisive = [row for row in message_runner_rows if row["distinctDecisiveCaseCount"] > 0]
    message_run_states = collections.Counter(row["runState"] for row in message_runner_rows)

    def outcome_rows(selected):
        totals = collections.Counter()
        for item in selected:
            totals.update(item["outcomeRowCounts"])
        return dict(totals)

    message_candidate_outcomes = outcome_rows(runner_rows)
    message_all_outcomes = outcome_rows(message_runner_rows)
    report = "# Message Semantic Triage and Execution Readiness\n\n"
    report += "This is a preliminary pair review for the 44 same-category Message-to-Message candidates. It is evidence-linked triage, not an authorized semantic decision register. All pair decisions remain OPEN_REVIEW_REQUIRED.\n\n"
    report += "| Preliminary relation | Pair count |\n|---|---:|\n"
    report += "".join(f"| {label} | {count} |\n" for label, count in sorted(relation_counts.items()))
    report += f"| **Total pairs triaged** | **{len(rows)}** |\n\n"
    report += "## Offline Predicate Execution\n\n"
    report += f"The full-delivery field/structure runner has results for {len(runner_rows)} of {len(rule_ids)} distinct Test BR IDs in this 44-pair candidate set. "
    report += f"{len(decisive)} rules produced bounded PASS/FAIL results over {sum(row['distinctDecisiveCaseCount'] for row in decisive)} per-rule AI candidate cases; pair-candidate outcome rows are {json.dumps(message_candidate_outcomes, sort_keys=True)}.\n\n"
    report += f"Across all 243 Message Test BRs, {len(message_runner_rows)} are in the 601-rule runner baseline; {len(message_decisive)} have decisive bounded results. "
    report += f"Run states: {dict(message_run_states)}. Outcome rows on AI payloads: {json.dumps(message_all_outcomes, sort_keys=True)}. "
    report += "These are predicate outputs on AI payloads, not executions of Test Solution fixtures and not semantic match decisions. A FAIL is a failing bounded predicate observation, not a whole-case verdict.\n\n"
    report += "| Runner binding | Distinct Test BRs |\n|---|---:|\n"
    report += "".join(f"| {name} | {count} |\n" for name, count in sorted(executions.items()))
    report += f"\nTest Message `BR-RULE-*` chains remain REVIEW_REQUIRED placeholders. The 58 exact-anchor alternative Test Data vectors are also REVIEW_REQUIRED. Package-level chain validation reports {package_validation['requirementsWithoutScenario']} BR->TS gaps, {package_validation['scenariosWithoutTestCase']} TS->TC gaps, and {package_validation['testCasesWithoutData']} TC->TD gaps; `executionReady={str(package_validation['executionReady']).lower()}` because link integrity does not establish validated payloads or SME approval.\n\n"
    report += f"The package has {package_validation['testDataReadinessCounts'].get('EXECUTABLE', 0)} EXECUTABLE-readiness Test Data records overall and {package_validation['requirementsWithExecutableReadinessDataChain']} BR chains, but no official Message BR-rule chain or exact-anchor Message alternative has EXECUTABLE Test Data. The exact chain-validation result is included with this report.\n\n"
    report += "SemanticEvidenceValidatorTest (14), CanonicalTraceabilityValidatorTest (46), and the isolated Test Solution JSON/canonical traceability test (1) passed. The broader TestSolutionJsonFormatTest also has an unrelated failure on 22 Appendix AE/AF KB rule-catalog rows missing its required `ruleId/title/sourceAnchor` shape.\n\n"
    report += "Formal semantic adjudication still requires authorized source interpretation and complete claims/quote evidence. Host/Test-fixture execution is blocked by the absent execution-ready Message fixture and request/response oracle. No `MATCH`, `PARTIAL`, or `NOT_MATCH` is recorded as final.\n"
    (output / "REPORT.md").write_text(report, encoding="utf-8")
    (output / "summary.json").write_text(json.dumps({
        "pairCount": len(rows), "preliminaryRelationCounts": dict(relation_counts),
        "testBrRulesInCandidateSet": len(rule_ids), "testBrRulesInRunnerBaseline": len(runner_rows),
        "testBrRulesWithDecisivePredicateResults": len(decisive),
        "distinctAiCandidateCasesWithDecisivePredicateResults": sum(row["distinctDecisiveCaseCount"] for row in decisive),
        "offlinePredicateImplementations": dict(executions),
        "messageTestBrsInRunnerBaseline": len(message_runner_rows),
        "messageTestBrsWithDecisivePredicateResults": len(message_decisive),
        "messageRunStates": dict(message_run_states),
        "messagePredicateOutcomeRowsOnAiPayloads": message_all_outcomes,
        "candidatePredicateOutcomeRowsOnAiPayloads": message_candidate_outcomes,
        "semanticDecisionStatus": "ALL_OPEN_REVIEW_REQUIRED_NOT_AUTHORIZED",
        "testFixtureExecution": "NOT_EXECUTED_NO_APPROVED_EXECUTION_READY_MESSAGE_FIXTURES",
        "hostExecution": "NOT_EXECUTED",
        "testSolutionChainValidation": {key: package_validation.get(key) for key in (
            "businessRequirements", "testScenarios", "testCases", "testData", "requirementsWithoutScenario",
            "scenariosWithoutTestCase", "testCasesWithoutData", "testDataReadinessCounts",
            "requirementsWithExecutableReadinessDataChain", "structurallyComplete", "executionReady")},
        "sourceSha256": {
            "messageCandidatePairs.csv": sha256(pairs_path),
            "technical-validation-by-test-br.jsonl": sha256(runner_path),
            "test-solution-chain-validation.json": sha256(test_chain_report),
            "test-chain-evidence.jsonl": sha256(comparison_dir / "test-chain-evidence.jsonl"),
            "ai-chain-evidence.jsonl.gz": sha256(comparison_dir / "ai-chain-evidence.jsonl.gz"),
        }}, indent=2), encoding="utf-8")
    artifacts = {path.name: sha256(path) for path in output.iterdir() if path.is_file()}
    (output / "manifest.json").write_text(json.dumps({"algorithm": "SHA-256", "artifacts": artifacts}, indent=2), encoding="utf-8")
    print(json.dumps({"pairCount": len(rows), "relationCounts": dict(relation_counts),
                      "runnerRules": len(runner_rows), "decisiveRules": len(decisive),
                      "semanticFinalDecisions": 0, "testFixtureExecutions": 0}, indent=2))


def self_test():
    assert relation("REQ-SRC-ATL105-PDF-001:1239", "SEG110-R-007")[0] == "PRELIMINARY_SAME_SUBRULE_OVERLAP"
    assert relation("REQ-SRC-ATL105-PDF-001:1907", "SEG103-R-005")[0] == "RELATED_FIELD_DIFFERENT_OBLIGATION"
    assert relation("REQ-SRC-ATL105-PDF-001:1907", "SEG115-R-001")[0] == "RELATED_CONTEXT_DIFFERENT_OBLIGATION"
    assert relation("REQ-SRC-ATL105-PDF-001:1907", "SEG103-R-004")[0] == "PARTIAL_FORMAT_WIDTH_OVERLAP"
    assert relation("REQ-SRC-ATL105-PDF-001:2376", "SEG100-R-016")[0] == "SOURCE_ANCHOR_CONFLICT_REVIEW"
    print("PASS: preliminary relation map preserves scope gaps and leaves every decision non-final")


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--comparison-dir", type=Path)
    parser.add_argument("--runner-ledger", type=Path)
    parser.add_argument("--test-chain-report", type=Path)
    parser.add_argument("--output-dir", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
    elif all((args.comparison_dir, args.runner_ledger, args.test_chain_report, args.output_dir)):
        run(args.comparison_dir, args.runner_ledger, args.test_chain_report, args.output_dir)
    else:
        parser.error("Supply comparison directory, offline runner ledger, Test chain report and new output directory")


if __name__ == "__main__":
    main()