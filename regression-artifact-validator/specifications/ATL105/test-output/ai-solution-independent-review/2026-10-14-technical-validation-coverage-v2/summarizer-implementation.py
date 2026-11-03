"""Summarize byte-bound offline predicate execution, not SME or host approval."""

import argparse
import collections
import hashlib
import json
import tempfile
from pathlib import Path

import ijson


BOUND = {"PROFILE_BACKED_LIMITED_EXECUTION", "PARTIAL_EXECUTOR_REVIEW_REQUIRED_FOR_UNCHECKED_BRANCHES"}
DECISIVE = {"PASS", "FAIL"}


def sha256(path):
    digest = hashlib.sha256()
    with Path(path).open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def read_json(path):
    return json.loads(Path(path).read_text(encoding="utf-8-sig"))


def array_rows(path, key):
    with Path(path).open("rb") as stream:
        yield from ijson.items(stream, key + ".item", use_float=True)


def jsonl(path):
    with Path(path).open(encoding="utf-8") as stream:
        for line in stream:
            if line.strip():
                yield json.loads(line)


def write_rows(path, rows):
    with path.open("w", encoding="utf-8", newline="\n") as stream:
        for row in rows:
            stream.write(json.dumps(row, sort_keys=True, ensure_ascii=True) + "\n")


def header(path):
    scalars = {}
    with Path(path).open("rb") as stream:
        for prefix, event, value in ijson.parse(stream, use_float=True):
            if prefix in {"inputMessages", "ruleCoverage", "evaluations"} and event == "start_array":
                break
            if prefix and "." not in prefix and event in {"string", "number", "boolean", "null"}:
                scalars[prefix] = value
            if prefix.startswith("inputSha256.") and event == "string":
                scalars[prefix] = value
    return scalars


def measured(numerator, denominator, meaning):
    return {"numerator": numerator, "denominator": denominator,
            "percent": round(100 * numerator / denominator, 2) if denominator else None,
            "meaning": meaning}


def aggregate(baseline, bindings, inputs, evaluations, br_rows, physical_rows, conflicts):
    rules = baseline.get("businessRequirements") or []
    rule_by_id = {row["ruleId"]: row for row in rules}
    if not rules or len(rule_by_id) != len(rules) or baseline.get("ruleCount") != len(rules):
        raise ValueError("Independent baseline is empty or has duplicate/count-mismatched rules")
    if baseline.get("authority") != "INDEPENDENT_TEST_SOLUTION" or baseline.get("aiArtifactsIncluded") is not False:
        raise ValueError("Independent Test Solution baseline required")
    binding_by_id = {row["ruleId"]: row for row in bindings}
    if len(binding_by_id) != len(bindings) or not set(binding_by_id).issubset(rule_by_id):
        raise ValueError("Executor bindings contain duplicate or out-of-baseline rule IDs")
    messages = {row["messageId"]: row for row in inputs}
    if len(messages) != len(inputs):
        raise ValueError("Duplicate input message identity")
    outcomes, failure_dispositions = collections.defaultdict(collections.Counter), collections.Counter()
    by_rule_cases, by_message_rules = collections.defaultdict(set), collections.defaultdict(set)
    rows_by_rule = collections.Counter()
    for evaluation in evaluations:
        rule_id, message_id = evaluation["ruleId"], evaluation["messageId"]
        if rule_id not in binding_by_id or binding_by_id[rule_id]["executionStatus"] not in BOUND:
            raise ValueError("Evaluation has no bound executor: " + rule_id)
        if message_id not in messages or evaluation["testCaseId"] != messages[message_id]["testCaseId"]:
            raise ValueError("Evaluation has no matching byte-bound message")
        status = evaluation["status"]
        if status not in DECISIVE | {"REVIEW_REQUIRED"}:
            raise ValueError("Unsupported result status: " + status)
        outcomes[rule_id][status] += 1
        rows_by_rule[rule_id] += 1
        if status in DECISIVE:
            by_rule_cases[rule_id].add(evaluation["testCaseId"])
            by_message_rules[message_id].add(rule_id)
        if status == "FAIL":
            failure_dispositions[evaluation.get("failureDisposition", "UNSPECIFIED")] += 1
    rows = []
    bound_ids = set()
    for rule in rules:
        rule_id = rule["ruleId"]
        binding = binding_by_id.get(rule_id, {})
        implementation = binding.get("executionStatus", "OUTSIDE_THIS_RUNNER_SCOPE")
        if implementation in BOUND:
            bound_ids.add(rule_id)
        if int(binding.get("evaluatedCaseCount", 0)) != rows_by_rule[rule_id]:
            raise ValueError("Reported evaluation count differs: " + rule_id)
        if by_rule_cases[rule_id]:
            run_state = "DECISIVE_PREDICATE_RESULT_OBSERVED"
        elif outcomes[rule_id]:
            run_state = "REVIEW_ONLY_NO_DECISIVE_RESULT"
        elif implementation in BOUND:
            run_state = "BOUND_NOT_EXERCISED"
        else:
            run_state = "NO_RESULT_FROM_THIS_RUNNER"
        rows.append({"testBrId": rule_id, "title": rule.get("title"), "class": rule.get("class"),
                     "sourceAnchor": rule.get("sourceAnchor"), "implementation": implementation,
                     "evaluator": binding.get("evaluator"), "profileIds": binding.get("profileIds", []),
                     "runState": run_state, "outcomeRowCounts": dict(outcomes[rule_id]),
                     "evaluationRowCount": rows_by_rule[rule_id], "distinctDecisiveCaseCount": len(by_rule_cases[rule_id]),
                     "semanticStatus": "NOT_ASSESSED", "hostExecutionStatus": "NOT_EXECUTED"})
    blocked = {row["id"] for row in conflicts if row["kind"] == "TC"}
    routes_by_case = collections.defaultdict(set)
    for route in physical_rows:
        if route["status"] == "COMPLETE_PHYSICAL_DECLARATIONS" and route["id"] not in blocked:
            routes_by_case[route["id"]].update(route["paths"])
    message_by_case = collections.defaultdict(list)
    for message_id, message in messages.items():
        message_by_case[message["testCaseId"]].append((message_id, message))
    ai_observations, ai_ids = [], set()
    seen_brs = set()
    for br in br_rows:
        if br["id"] in seen_brs:
            raise ValueError("AI BR variants require a disambiguated assessment")
        seen_brs.add(br["id"])
        if not br.get("eligibleForStructuralMetrics"):
            continue
        for case_id in br.get("physicalCompleteCaseIds") or []:
            if case_id in blocked:
                continue
            for message_id, message in message_by_case[case_id]:
                if message["payloadRelativePath"] not in routes_by_case[case_id]:
                    continue
                candidate_results = set(br.get("testBrCandidates") or []) & by_message_rules[message_id]
                for rule_id in sorted(candidate_results):
                    ai_ids.add(br["id"])
                    ai_observations.append({"aiBrId": br["id"], "testBrId": rule_id, "testCaseId": case_id,
                                            "messageId": message_id, "payloadRelativePath": message["payloadRelativePath"],
                                            "status": "LINKED_CANDIDATE_PREDICATE_OBSERVED_NOT_SEMANTIC_CONFIRMATION"})
    decisive_rules = set(by_rule_cases) - {rule for rule, cases in by_rule_cases.items() if not cases}
    decisive_cases = {case for cases in by_rule_cases.values() for case in cases}
    total_outcomes = collections.Counter()
    for counts in outcomes.values():
        total_outcomes.update(counts)
    summary = {"population": {"independentTestRules": len(rules), "runnerRuleScope": len(bindings),
                              "rulesOutsideRunnerScope": len(rules) - len(bindings), "messagesProcessed": len(inputs),
                              "distinctCaseIdsProcessed": len(message_by_case), "decisiveCaseIds": len(decisive_cases),
                              "aiBrIds": len(seen_brs), "conflictingCaseIdsExcludedFromAiLinks": len(blocked)},
               "measures": {
                   "limitedOrPartialExecutorBoundCoverage": measured(len(bound_ids), len(rules), "Bound limited predicates, not complete BR validators."),
                   "decisivePredicateRuleCoverage": measured(len(decisive_rules), len(rules), "Rule produced PASS or FAIL; REVIEW_REQUIRED alone is not execution credit."),
                   "runnerScopeDecisiveRuleCoverage": measured(len(decisive_rules), len(bindings), "Decisive rules among this runner's field/structure slice."),
                   "caseAnyDecisivePredicateCoverage": measured(len(decisive_cases), len(message_by_case), "At least one bounded result, not a complete-case pass."),
                   "aiBrLinkedCandidatePredicateCoverage": measured(len(ai_ids), len(seen_brs), "Clean direct-chain payload has an observed candidate rule; not semantic agreement."),
                   "semanticConfirmedCoverage": {"percent": None, "status": "NOT_ASSESSED_AUTHORIZED_DECISIONS_REQUIRED"},
                   "hostExecutionCoverage": {"percent": None, "status": "NOT_EXECUTED"}},
               "outcomeRows": dict(total_outcomes), "failureDispositions": dict(failure_dispositions),
               "executionCertified": False}
    return summary, rows, ai_observations


def run(report_path, baseline_path, delivery_path, output):
    report_path, baseline_path, delivery_path, output = map(Path, (report_path, baseline_path, delivery_path, output))
    if output.exists():
        raise ValueError("Output directory must be new")
    delivery = read_json(delivery_path)
    baseline = read_json(baseline_path)
    report_header = header(report_path)
    if report_header.get("artifact") != "ATL105-FIELD-STRUCTURE-RULE-EXECUTION" or report_header.get("provenanceVersion") != 1 or report_header.get("inputStabilityVerified") is not True:
        raise ValueError("Byte-bound stable field runner report required")
    if baseline["specificationVersion"] != report_header["specificationVersion"] or delivery["baselineVersion"] != baseline["specificationVersion"]:
        raise ValueError("Specification release mismatch")
    if report_header.get("inputSha256.sourceRuleInventory") != sha256(baseline_path):
        raise ValueError("Runner did not use this exact independent baseline")
    root = Path(delivery["runRoot"]).resolve()
    inventory_path = delivery_path.parent / "file-inventory.jsonl"
    if sha256(inventory_path) != delivery["fileInventorySha256"]:
        raise ValueError("Delivery file inventory hash mismatch")
    manifest = {row["path"]: row for row in jsonl(inventory_path)}
    def verified_ledger(name):
        path = delivery_path.parent / name
        if sha256(path) != delivery["outputHashes"].get(name):
            raise ValueError("Delivery ledger hash mismatch: " + name)
        return path
    ledger_paths = {name: verified_ledger(name) for name in ("br-assessment.jsonl", "tc-physical-assessment.jsonl", "content-conflicts.jsonl")}
    inputs = list(array_rows(report_path, "inputMessages"))
    input_paths = [report_path, baseline_path, delivery_path, inventory_path, *ledger_paths.values(), Path(__file__)]
    hashes = {str(path.resolve()): sha256(path) for path in input_paths}
    for message in inputs:
        for key, digest_key in (("metadataPath", "metadataSha256"), ("payloadPath", "payloadSha256")):
            path = Path(message[key]).resolve()
            if not path.is_relative_to(root):
                raise ValueError("Runner input escapes selected delivery")
            relative = path.relative_to(root).as_posix()
            if key == "payloadPath":
                message["payloadRelativePath"] = relative
            expected = manifest.get(relative)
            if message[digest_key]:
                if not expected or expected["sha256"] != message[digest_key]:
                    raise ValueError("Runner bytes differ from frozen delivery: " + relative)
            elif expected:
                raise ValueError("Runner omitted a payload present in the frozen inventory")
    if len(inputs) != report_header["messagesAssessed"]:
        raise ValueError("Input-message population mismatch")
    bindings = list(array_rows(report_path, "ruleCoverage"))
    if len(bindings) != report_header["inventoryRuleCount"]:
        raise ValueError("Runner scope population mismatch")
    summary, rules, ai_links = aggregate(baseline, bindings, inputs, array_rows(report_path, "evaluations"),
        jsonl(ledger_paths["br-assessment.jsonl"]), jsonl(ledger_paths["tc-physical-assessment.jsonl"]), jsonl(ledger_paths["content-conflicts.jsonl"]))
    summary.update({"artifact": "ATL105-OFFLINE-TECHNICAL-VALIDATION-COVERAGE", "runId": report_header["runId"],
                    "specificationVersion": baseline["specificationVersion"], "observationSource": report_header["observationSource"],
                    "inputSha256": hashes, "deliveryFileInventorySha256": delivery["fileInventorySha256"],
                    "limitations": ["Field values are metadata-derived; physical envelope/structure checks are bounded. No direct-field or wire certification.",
                                    "Candidate input directory only; other artifact tiers/directories are not executed by this runner.",
                                    "Source-profile binding can implement only part of a composite BR.",
                                    "FAIL may be the intended negative target. No independent whole-case expected-outcome agreement is inferred.",
                                    "Source interpretations, authorized matches and host execution remain separate gates."]})
    for path, digest in hashes.items():
        if sha256(path) != digest:
            raise ValueError("Evidence changed during summary: " + path)
    output.mkdir(parents=True)
    write_rows(output / "technical-validation-by-test-br.jsonl", rules)
    write_rows(output / "ai-br-candidate-rule-observations.jsonl", ai_links)
    (output / "summarizer-implementation.py").write_bytes(Path(__file__).read_bytes())
    text = "# Offline Technical Validation Coverage\n\nOnly bounded predicates are counted; source matching and host execution remain unassessed.\n\n"
    text += "| Measure | Numerator | Denominator | Percent |\n|---|---:|---:|---:|\n"
    for name, metric in summary["measures"].items():
        text += f"| {name} | {metric.get('numerator', 'N/A')} | {metric.get('denominator', 'N/A')} | {metric.get('percent') if metric.get('percent') is not None else metric['status']} |\n"
    text += "\nObservation source: " + summary["observationSource"] + ".\n\n" + "\n".join("- " + item for item in summary["limitations"]) + "\n"
    (output / "REPORT.md").write_text(text, encoding="utf-8")
    summary["outputSha256"] = {path.name: sha256(path) for path in output.iterdir() if path.is_file()}
    (output / "technical-validation-coverage.json").write_text(json.dumps(summary, indent=2), encoding="utf-8")
    return summary


def self_test():
    baseline = {"authority": "INDEPENDENT_TEST_SOLUTION", "aiArtifactsIncluded": False, "ruleCount": 3,
                "businessRequirements": [{"ruleId": "R1"}, {"ruleId": "R2"}, {"ruleId": "R3"}]}
    bindings = [{"ruleId": "R1", "executionStatus": "PROFILE_BACKED_LIMITED_EXECUTION", "evaluatedCaseCount": 2},
                {"ruleId": "R2", "executionStatus": "PARTIAL_EXECUTOR_REVIEW_REQUIRED_FOR_UNCHECKED_BRANCHES", "evaluatedCaseCount": 1}]
    inputs = [{"messageId": "M1", "testCaseId": "TC1", "payloadRelativePath": "data/TC1.json"},
              {"messageId": "M2", "testCaseId": "TC2", "payloadRelativePath": "data/TC2.json"}]
    evaluations = [{"messageId": "M1", "testCaseId": "TC1", "ruleId": "R1", "status": "PASS"},
                   {"messageId": "M1", "testCaseId": "TC1", "ruleId": "R1", "status": "FAIL"},
                   {"messageId": "M2", "testCaseId": "TC2", "ruleId": "R2", "status": "REVIEW_REQUIRED"}]
    brs = [{"id": "BR1", "eligibleForStructuralMetrics": True, "testBrCandidates": ["R1"], "physicalCompleteCaseIds": ["TC1"]}]
    routes = [{"id": "TC1", "status": "COMPLETE_PHYSICAL_DECLARATIONS", "paths": ["data/TC1.json"]}]
    summary, _, links = aggregate(baseline, bindings, inputs, evaluations, brs, routes, [])
    assert summary["measures"]["limitedOrPartialExecutorBoundCoverage"]["numerator"] == 2
    assert summary["measures"]["decisivePredicateRuleCoverage"]["numerator"] == 1
    assert summary["measures"]["caseAnyDecisivePredicateCoverage"]["numerator"] == 1
    assert len(links) == 1 and summary["measures"]["semanticConfirmedCoverage"]["percent"] is None
    blocked, _, links = aggregate(baseline, bindings, inputs, evaluations, brs, routes, [{"kind": "TC", "id": "TC1"}])
    assert not links and blocked["measures"]["aiBrLinkedCandidatePredicateCoverage"]["numerator"] == 0
    wrong_routes = [{"id": "TC1", "status": "COMPLETE_PHYSICAL_DECLARATIONS", "paths": ["other/TC1.json"]}]
    assert not aggregate(baseline, bindings, inputs, evaluations, brs, wrong_routes, [])[2]
    print("PASS: bound/decisive/review-only counts, exact physical routes and conflict exclusions remain separate")


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--field-structure-report", type=Path)
    parser.add_argument("--baseline", type=Path)
    parser.add_argument("--delivery-summary", type=Path)
    parser.add_argument("--output-dir", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
    elif all((args.field_structure_report, args.baseline, args.delivery_summary, args.output_dir)):
        result = run(args.field_structure_report, args.baseline, args.delivery_summary, args.output_dir)
        print(json.dumps(result["measures"], indent=2))
    else:
        parser.error("Supply byte-bound runner report, independent baseline, delivery summary and new output directory")


if __name__ == "__main__":
    main()