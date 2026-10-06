import argparse
import collections
import csv
import hashlib
import json
import re
from pathlib import Path

import ijson


def read(path):
    return json.loads(path.read_text(encoding="utf-8-sig"))


def hash_file(path):
    with path.open("rb") as stream:
        return hashlib.file_digest(stream, "sha256").hexdigest()


def normalize(value):
    return " ".join(str(value).split()).casefold()


def links(row):
    values = row.get("requirement_ids") or []
    if isinstance(values, str):
        values = [values]
    return set(values) | ({row["requirement_id"]} if row.get("requirement_id") else set())


def descriptors(node, path=()):
    if isinstance(node, dict):
        if "value" in node and isinstance(node.get("data_ref"), dict):
            yield path, node
        else:
            for key, value in node.items():
                yield from descriptors(value, path + (key,))
    elif isinstance(node, list):
        for index, value in enumerate(node):
            yield from descriptors(value, path + (str(index),))


def source_predicate(element, value):
    if element == "85":
        return value == "100"
    if element == "86":
        return isinstance(value, str) and re.fullmatch(r"[0-9]{6}", value) is not None
    return None


def evaluate_leg(case, payload, metadata):
    family = metadata.get("messageFamily")
    body = payload.get(family) if isinstance(payload, dict) else None
    if not isinstance(body, dict):
        return {"interpretation": "UNSUPPORTED_MESSAGE_ROOT", "fieldChecks": [], "negativeMutation": "NOT_ASSESSED"}
    field_checks = []
    for field in metadata.get("fields", []):
        if str(field.get("segment")) != "100":
            continue
        spec_name = normalize(field.get("specElementName", ""))
        element = {"segment type": "85", "sequence number": "86"}.get(spec_name)
        if element is None:
            continue
        segment = body.get(field.get("segmentFriendlyName"))
        if not isinstance(segment, dict) or field.get("element") not in segment:
            field_checks.append({"element": element, "predicate": "NOT_ASSESSED", "reason": "Explicit metadata path unavailable"})
            continue
        value = segment[field["element"]]
        field_checks.append({"element": element, "predicate": "PASS" if source_predicate(element, value) else "FAIL",
                             "payloadPath": [family, field["segmentFriendlyName"], field["element"]],
                             "value": value, "metadataValuePreserved": value == field.get("value"),
                             "scope": "Segment 100 fixed identity / six-digit format only"})
    mutation = "NOT_APPLICABLE" if case["scenario_type"] != "negative" else "NOT_ASSESSED"
    mutation_checks = []
    target = case.get("violated_element_name")
    if case["scenario_type"] == "negative" and target:
        matching = [field for field in metadata.get("fields", []) if normalize(field.get("specElementName", "")) == normalize(target)]
        for field in matching:
            segment = body.get(field.get("segmentFriendlyName"))
            if not isinstance(segment, dict) or field.get("element") not in segment:
                continue
            actual = segment[field["element"]]
            declared = [descriptor for _, descriptor in descriptors(case.get("request", {}))
                        if normalize(descriptor.get("data_ref", {}).get("element", "")) == normalize(target)
                        and str(descriptor.get("data_ref", {}).get("segment")) == str(field.get("segment"))]
            if len(declared) != 1:
                continue
            descriptor = declared[0]
            method = str(descriptor.get("method", ""))
            if not method.startswith("deliberate_violation_"):
                continue
            preserved = actual == descriptor.get("value")
            mutation_checks.append({"target": target, "segment": field.get("segment"), "method": method,
                                    "valuePreserved": preserved, "value": actual,
                                    "physicalPredicate": "SOURCE_NOT_YET_ADJUDICATED"})
        if mutation_checks:
            mutation = "DECLARED_VALUE_PRESERVED" if all(row["valuePreserved"] for row in mutation_checks) else "DECLARED_VALUE_CHANGED"
    for check in field_checks:
        check["intendedNegativeTarget"] = case["scenario_type"] == "negative" and normalize(target) == {
            "85": "segment type", "86": "sequence number"}.get(check["element"])
    blocker = None
    if mutation == "NOT_ASSESSED":
        blocker = ("ABSENCE_ASSERTION_NOT_IMPLEMENTED" if case.get("negative_class") == "missing_mandatory" else
                   "NO_SOURCE_GROUNDED_PROCESSOR_OUTCOME_ORACLE" if case.get("negative_class") == "orphan_followon" else
                   "NO_UNAMBIGUOUS_EXPLICIT_RENDERED_TARGET_MAPPING")
    return {"interpretation": "EXPLICIT_METADATA_PATHS_ONLY", "fieldChecks": field_checks,
            "negativeMutation": mutation, "mutationChecks": mutation_checks, "unassessedReason": blocker}


def self_test():
    assert source_predicate("85", "100") and not source_predicate("85", "101")
    assert source_predicate("86", "123456") and not source_predicate("86", "12345")
    assert not source_predicate("86", "１２３４５６")
    assert source_predicate("2", "anything") is None
    case = {"scenario_type": "negative", "violated_element_name": "Sequence Number",
            "request": {"Standard": {"Sequence Number": {"value": "12345", "method": "deliberate_violation_length",
                           "data_ref": {"element": "Sequence Number", "segment": "100"}}}}}
    metadata = {"messageFamily": "Financial Transaction Request", "fields": [
        {"segment": "100", "specElementName": "Sequence Number", "segmentFriendlyName": "Standard",
         "element": "SequenceNumber", "value": "12345"}]}
    payload = {"Financial Transaction Request": {"Standard": {"SequenceNumber": "12345"}}}
    result = evaluate_leg(case, payload, metadata)
    assert result["negativeMutation"] == "DECLARED_VALUE_PRESERVED"
    assert result["fieldChecks"][0]["predicate"] == "FAIL"
    payload["Financial Transaction Request"]["Standard"]["SequenceNumber"] = "123456"
    assert evaluate_leg(case, payload, metadata)["negativeMutation"] == "DECLARED_VALUE_CHANGED"
    assert evaluate_leg(case, {}, metadata)["interpretation"] == "UNSUPPORTED_MESSAGE_ROOT"
    print("PASS: positive/negative predicates, mutation preservation, unsupported roots and ASCII digit boundary")


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("review", type=Path, nargs="?")
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
        return
    root = args.review
    run = read(root / "complete-handoff-analysis.json")
    archive = Path(run["archive"])
    pipeline = archive / "pipeline_run_artifacts"
    catalogs = archive / "scenerio_req_5_oct/scenerio_req_5_oct"
    scenario_file = catalogs / "scenarios/approved/approved_scenarios.json"
    requirement_file = catalogs / "step5_requirements/approved/requirement_catalog.json"
    case_file = pipeline / "test_case_candidates.json"
    scenarios = {row["id"]: row for row in read(scenario_file)["scenarios"]}
    requirements = {row["id"]: row for row in read(requirement_file)["requirements"]}
    source_file = Path(__file__).resolve().parents[1] / "specifications/ATL105/docs/specs/extracted_text.txt"
    source = source_file.read_text(encoding="utf-8-sig")
    clauses = {"85": "Fixed value:  100", "86": "Representation: Fixed length of six digits"}
    section100 = source.index("12.1 Standard Message Data Segment", source.index("--- PAGE", 1000))
    actual_section100 = source.find("This section describes Data Segment No. 100", section100)
    if actual_section100 < 0 or clauses["85"] not in source[actual_section100:actual_section100 + 6000]:
        raise RuntimeError("Segment 100 source predicate not supported in bounded section")
    sequence_start = source.index("Number: 86 Name: Sequence Number")
    if clauses["86"] not in source[sequence_start:sequence_start + 1000]:
        raise RuntimeError("Sequence Number source predicate changed")
    selected = []
    strata = collections.Counter()
    negative_classes = collections.Counter()
    corpus = collections.Counter()
    expected_code_one = 0
    code_one_rows = []
    with case_file.open("rb") as stream:
        for case in ijson.items(stream, "test_cases.item", use_float=True):
            corpus["records"] += 1
            corpus["emptyExpectedResponse"] += not case.get("expected_response")
            scenario = scenarios.get(case.get("scenario_id"))
            if scenario is None:
                raise RuntimeError("Unknown scenario reference")
            if str(scenario.get("expected_response_code")) == "1":
                expected_code_one += 1
                code_one_rows.append({"caseId": case["id"], "scenarioId": case["scenario_id"],
                    "intent": case.get("scenario_type"), "transactionLabel": case.get("transaction_type"),
                    "requirementIds": sorted(links(case)), "scenarioExpectedCode": "1",
                    "caseExpectedResponse": case.get("expected_response"),
                    "sourceRuleId": scenario.get("source_rule_id"), "sourcePage": scenario.get("source_page"),
                    "familyDisposition": "NOT_VERIFIED_DO_NOT_INFER_CODE_MEANING_FROM_LABEL",
                    "expectedOutcomeDisposition": "MISSING_TC_EXPECTED_RESPONSE" if not case.get("expected_response") else "REVIEW_REQUIRED",
                    "independentDisposition": "NOT_ASSESSED"})
            corpus["noSharedBrWithScenario"] += not (links(case) & links(scenario))
            kind = case.get("scenario_type")
            if kind not in {"positive", "negative", "boundary", "flow"} or strata[kind] >= 25:
                continue
            negative_class = str(case.get("negative_class"))
            if kind == "negative" and negative_classes[negative_class] >= 4:
                continue
            selected.append(case)
            strata[kind] += 1
            if kind == "negative":
                negative_classes[negative_class] += 1
    hashes = {file.name: hash_file(file) for file in (case_file, scenario_file, requirement_file, source_file)}
    rows = []
    for case in selected:
        scenario = scenarios[case["scenario_id"]]
        declared_paths = case.get("test_data_leg_files") or ([case["test_data_file"]] if case.get("test_data_file") else [])
        legs = []
        for declared in declared_paths:
            filename = str(declared).replace("\\", "/").rsplit("/", 1)[-1]
            payload_file = pipeline / "qe_shaped_test_data" / filename
            metadata_file = payload_file.with_name(payload_file.stem + ".meta.json")
            if not payload_file.is_file() or not metadata_file.is_file():
                legs.append({"file": filename, "interpretation": "MISSING_PHYSICAL_FILE", "fieldChecks": [], "negativeMutation": "NOT_ASSESSED"})
                continue
            metadata = read(metadata_file)
            if metadata.get("testCaseId") != case["id"] or metadata.get("scenarioId") != case["scenario_id"]:
                raise RuntimeError("Payload metadata join mismatch for " + filename)
            leg = evaluate_leg(case, read(payload_file), metadata)
            leg["file"] = filename
            leg["payloadSha256"] = hash_file(payload_file)
            leg["metadataSha256"] = hash_file(metadata_file)
            legs.append(leg)
        checks = [check for leg in legs for check in leg["fieldChecks"]]
        methods = collections.Counter(str(node.get("method")) for _, node in descriptors(case.get("request", {})))
        row = {"caseId": case["id"], "scenarioId": case["scenario_id"], "intent": case["scenario_type"],
               "transactionLabel": case.get("transaction_type"), "negativeClass": case.get("negative_class"),
               "violatedElement": case.get("violated_element_name"), "scenarioName": scenario.get("name"),
               "caseBrIds": sorted(links(case)), "scenarioBrIds": sorted(links(scenario)),
               "sharedBrIds": sorted(links(case) & links(scenario)),
               "scenarioBrsNotReferencedByCase": sorted(links(scenario) - links(case)),
               "caseBrsNotDeclaredByScenario": sorted(links(case) - links(scenario)),
               "linkedBrStatements": [{"id": br, "statement": requirements[br].get("statement"),
                    "sourceRule": requirements[br].get("source_rule_id"), "sourcePage": requirements[br].get("source_page")}
                    for br in sorted(links(case)) if br in requirements],
               "scenarioExpectedCode": scenario.get("expected_response_code"),
               "caseExpectedResponse": case.get("expected_response"), "physicalLegs": legs,
               "sourcePredicateFailures": sum(check["predicate"] == "FAIL" for check in checks),
               "sourcePredicatePasses": sum(check["predicate"] == "PASS" for check in checks),
               "negativeEffectiveness": "NOT_CERTIFIED_NO_ISOLATED_BASELINE_OR_OUTCOME_ORACLE" if case["scenario_type"] == "negative" else "NOT_APPLICABLE",
               "potentialConfounders": {method: count for method, count in methods.items() if method in {
                   "placeholder", "awaiting_client_value", "spec_unspecified_code", "derived_at_wire_encoding"}},
               "semanticAlignment": "NOT_PROVEN_SHARED_IDS_ARE_NOT_BEHAVIOR_EQUIVALENCE",
               "overallDisposition": "REVIEW_REQUIRED", "executionCertified": False}
        rows.append(row)
    summary = {"scope": "Bounded first semantic batch; source predicates do not certify full transaction validity",
               "selection": "First 25 positive, boundary and flow cases; first four per negative class, capped at 25 negatives",
               "representativeRandomSample": False, "inputSha256": hashes, "batchSize": len(rows),
               "strata": dict(strata), "negativeClasses": dict(negative_classes), "corpusInterpretation": dict(corpus),
               "casesLinkedToScenarioExpectedCode1": expected_code_one,
               "sourcePredicates": [{"element": key, "quote": value, "section": "12.1" if key == "85" else "13.2 Element 86",
                    "scope": "fixed Segment 100 identity" if key == "85" else "six ASCII digits only, not lifecycle or full numeric range"} for key, value in clauses.items()],
               "predicatePasses": sum(row["sourcePredicatePasses"] for row in rows),
               "predicateFailures": sum(row["sourcePredicateFailures"] for row in rows),
               "casesWithPredicateFailures": sum(row["sourcePredicateFailures"] > 0 for row in rows),
            "intendedNegativePredicateFailures": sum(check["predicate"] == "FAIL" and check.get("intendedNegativeTarget", False)
                for row in rows for leg in row["physicalLegs"] for check in leg["fieldChecks"]),
            "unexpectedPredicateFailures": sum(check["predicate"] == "FAIL" and not check.get("intendedNegativeTarget", False)
                for row in rows for leg in row["physicalLegs"] for check in leg["fieldChecks"]),
               "legInterpretations": dict(collections.Counter(leg["interpretation"] for row in rows for leg in row["physicalLegs"])),
               "negativeMutationStates": dict(collections.Counter(leg["negativeMutation"] for row in rows if row["intent"] == "negative" for leg in row["physicalLegs"])),
               "unassessedNegativeReasons": dict(collections.Counter(leg["unassessedReason"] for row in rows if row["intent"] == "negative" for leg in row["physicalLegs"] if leg.get("unassessedReason"))),
               "batchCasesWithSharedBrIds": sum(bool(row["sharedBrIds"]) for row in rows),
               "batchConfounderCases": dict(collections.Counter(method for row in rows for method in row["potentialConfounders"])),
               "code1QueueTransactionLabels": dict(collections.Counter(row["transactionLabel"] for row in code_one_rows)),
               "executionCertified": False, "independentCoverage": "NOT_CALCULABLE"}
    assert len(rows) == 100 and all(strata[kind] == 25 for kind in ("positive", "negative", "boundary", "flow"))
    (root / "semantic-first-batch-summary.json").write_text(json.dumps(summary, indent=2, ensure_ascii=True), encoding="utf-8")
    (root / "semantic-first-batch-register.json").write_text(json.dumps(rows, indent=2, ensure_ascii=True), encoding="utf-8")
    (root / "expected-code-1-review-register.json").write_text(json.dumps(code_one_rows, indent=2, ensure_ascii=True), encoding="utf-8")
    with (root / "expected-code-1-review-register.csv").open("w", newline="", encoding="utf-8") as stream:
        writer = csv.DictWriter(stream, fieldnames=list(code_one_rows[0]))
        writer.writeheader()
        writer.writerows({key: json.dumps(value, ensure_ascii=True) if isinstance(value, (list, dict)) else value for key, value in row.items()} for row in code_one_rows)
    with (root / "semantic-first-batch-register.csv").open("w", newline="", encoding="utf-8") as stream:
        writer = csv.DictWriter(stream, fieldnames=list(rows[0]))
        writer.writeheader()
        writer.writerows({key: json.dumps(value, ensure_ascii=True) if isinstance(value, (list, dict)) else value for key, value in row.items()} for row in rows)
    print(json.dumps(summary, indent=2))


if __name__ == "__main__":
    main()