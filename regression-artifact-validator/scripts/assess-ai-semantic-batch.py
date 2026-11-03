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


def index_source_pages(source):
    markers = list(re.finditer(r"(?m)^--- PAGE (\d+) ---[ \t]*$", source))
    pages = {}
    for index, marker in enumerate(markers):
        end = markers[index + 1].start() if index + 1 < len(markers) else len(source)
        pages[int(marker.group(1))] = source[marker.end():end].strip()
    return pages


def source_excerpt(requirement, pages):
    raw_page = requirement.get("source_page")
    try:
        page_number = int(raw_page)
    except (TypeError, ValueError):
        page_number = None
    page = pages.get(page_number) if page_number is not None else None
    if page is None:
        return {"pageLocatorStatus": "SOURCE_PAGE_NOT_FOUND", "excerptLocator": "NONE", "excerpt": None}

    entity_ids = requirement.get("related_entity_ids") or []
    if isinstance(entity_ids, str):
        entity_ids = [entity_ids]
    entity_ids = list(entity_ids) + [requirement.get("source_rule_id", "")]
    for entity_id in entity_ids:
        match = re.search(r"ENT-ELEM-(\d+)", str(entity_id))
        if not match:
            continue
        element = match.group(1)
        for pattern in (rf"Number:\s*{element}\s+Name:", rf"\b\d+\s+{element}\s+[A-Za-z]"):
            marker = re.search(pattern, page, re.IGNORECASE)
            if marker:
                start = max(0, marker.start() - 120)
                return {"pageLocatorStatus": "SOURCE_PAGE_AND_ELEMENT_ANCHOR_FOUND",
                        "excerptLocator": "ELEMENT_" + element,
                        "excerpt": page[start:start + 900]}
    return {"pageLocatorStatus": "SOURCE_PAGE_FOUND_ELEMENT_ANCHOR_NOT_LOCATED",
            "excerptLocator": "PAGE_START_ONLY", "excerpt": page[:600]}


def objective_evidence(case, scenario, requirements, pages):
    case_ids = links(case)
    scenario_ids = links(scenario)
    shared_ids = case_ids & scenario_ids
    references = []
    for requirement_id in sorted(case_ids | scenario_ids):
        requirement = requirements.get(requirement_id)
        if requirement is None:
            references.append({"requirementId": requirement_id, "linkedToCase": requirement_id in case_ids,
                               "linkedToScenario": requirement_id in scenario_ids,
                               "sourceStatus": "REQUIREMENT_RECORD_NOT_FOUND"})
            continue
        evidence = source_excerpt(requirement, pages)
        references.append({"requirementId": requirement_id, "linkedToCase": requirement_id in case_ids,
            "linkedToScenario": requirement_id in scenario_ids, "shared": requirement_id in shared_ids,
            "statement": requirement.get("statement"), "sourceRuleId": requirement.get("source_rule_id"),
            "sourcePage": requirement.get("source_page"), **evidence})
    return {"caseObjectiveClaim": case.get("business_rule_assertion"),
            "scenarioObjectiveClaim": scenario.get("name"),
            "caseRequirementIds": sorted(case_ids), "scenarioRequirementIds": sorted(scenario_ids),
            "sharedRequirementIds": sorted(shared_ids),
            "caseOnlyRequirementIds": sorted(case_ids - scenario_ids),
            "scenarioOnlyRequirementIds": sorted(scenario_ids - case_ids),
            "sourceReferences": references,
            "alignmentStatus": "NO_SHARED_BR_IDS_REVIEW_REQUIRED" if not shared_ids else
                "SOURCE_CLAIMS_AND_PAGE_EVIDENCE_ATTACHED_SEMANTIC_EQUIVALENCE_REVIEW_REQUIRED",
            "semanticApproval": "NOT_ADJUDICATED"}


def negative_blocker_assessment(rows):
    targets = {"TC-002855", "TC-002856", "TC-002950", "TC-002951", "TC-002952", "TC-002953", "TC-004858"}
    by_id = {row["caseId"]: row for row in rows}
    results = []
    for case_id in sorted(targets):
        row = by_id.get(case_id)
        mutations = [mutation for leg in (row or {}).get("physicalLegs", []) for mutation in leg.get("mutationChecks", [])]
        source_predicates = [mutation.get("physicalPredicate") for mutation in mutations]
        if case_id == "TC-004858":
            status = "EXTERNAL_PROCESSOR_OUTCOME_ORACLE_REQUIRED"
        elif "EXCEEDS_SOURCE_MAX_51" in source_predicates:
            status = "SOURCE_MAXIMUM_VIOLATION_OBSERVED_FULL_NEGATIVE_EFFECTIVENESS_UNCERTIFIED"
        elif "REQUIRED_FIELD_ABSENT" in source_predicates:
            status = "SOURCE_REQUIRED_ABSENCE_OBSERVED_FULL_NEGATIVE_EFFECTIVENESS_UNCERTIFIED"
        elif "SOURCE_NOT_YET_ADJUDICATED" in source_predicates:
            status = "COMPOSITE_TARGET_MAPPED_ENUM_RULE_CONTEXT_REVIEW_REQUIRED"
        else:
            status = "TARGET_MAPPING_OR_PHYSICAL_EVIDENCE_UNRESOLVED"
        results.append({"caseId": case_id, "status": status, "physicalMutationChecks": len(mutations),
                        "physicalPredicates": source_predicates, "negativeEffectiveness": "NOT_CERTIFIED"})
    counts = collections.Counter(result["status"] for result in results)
    return {"targetCaseCount": len(targets), "cases": results, "statusCounts": dict(counts),
            "sourceBackedAbsentRequiredFieldsObserved": counts["SOURCE_REQUIRED_ABSENCE_OBSERVED_FULL_NEGATIVE_EFFECTIVENESS_UNCERTIFIED"],
            "sourceMaximumViolationsObserved": counts["SOURCE_MAXIMUM_VIOLATION_OBSERVED_FULL_NEGATIVE_EFFECTIVENESS_UNCERTIFIED"],
            "applicationDependentEnumCasesStillReviewRequired": counts["COMPOSITE_TARGET_MAPPED_ENUM_RULE_CONTEXT_REVIEW_REQUIRED"],
            "externalProcessorOracleCasesStillBlocked": counts["EXTERNAL_PROCESSOR_OUTCOME_ORACLE_REQUIRED"],
            "effectivenessCertified": 0, "executionCertified": False}


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


def lifecycle_observation(legs):
    authorization = [leg for leg in legs if leg.get("role") == "authorization"]
    completion = [leg for leg in legs if leg.get("role") == "completion"]
    if len(authorization) != 1 or len(completion) != 1:
        return {"status": "NO_UNIQUE_AUTHORIZATION_COMPLETION_PAIR", "certified": False}
    values = []
    for leg in (authorization[0], completion[0]):
        sequence = [check.get("value") for check in leg["fieldChecks"] if check["element"] == "86" and check["predicate"] == "PASS"]
        if len(sequence) != 1:
            return {"status": "SEQUENCE_VALUE_NOT_UNAMBIGUOUS", "certified": False}
        values.append(sequence[0])
    return {"status": "SAME_SEQUENCE_OBSERVED" if values[0] == values[1] else "DIFFERENT_SEQUENCE_OBSERVED",
            "sourceContext": "Element 86 preauthorization/completion clause; metadata roles are producer claims",
            "applicability": "CONTEXT_CANDIDATE_NOT_CANONICAL_MATCH", "certified": False}


def bounded_control_pairs():
    pairs = []
    for element, control, mutation in (("85", "100", "101"), ("86", "123456", "12345")):
        control_result = source_predicate(element, control)
        mutation_result = source_predicate(element, mutation)
        assert control_result is True and mutation_result is False
        pairs.append({"predicate": "ELEMENT_" + element, "control": control, "mutation": mutation,
                      "controlResult": "PASS", "mutationResult": "FAIL", "scope": "FIELD_PREDICATE_ONLY_NOT_FULL_MESSAGE",
                      "executionCertified": False})
    pairs.append({"predicate": "ELEMENT_12_MAX_51_ONLY", "control": "A" * 51, "mutation": "A" * 52,
                  "controlResult": "PASS" if len("A" * 51) <= 51 else "FAIL",
                  "mutationResult": "FAIL" if len("A" * 52) > 51 else "PASS",
                  "scope": "MAXIMUM_LENGTH_ONLY_NOT_APPLICATION_FORMAT", "executionCertified": False})
    return pairs


def label_context(label, matrix=None):
    exact_labels = {"Financial Transaction Request": "financial", "EMV Financial Transaction Request": "financial",
                    "Totals Request": "totals", "Electronic Mail Request": "electronic-mail",
                    "Proprietary Data Load Request": "proprietary-load", "Communications Test Request": "communications-test"}
    family = exact_labels.get(label)
    allowed = {row["family"] for row in (matrix or {}).get("allowedCodeFamilyMeanings", []) if str(row["code"]) == "1"}
    return {"status": "EXACT_SOURCE_HEADING_CONTEXT_CANDIDATE" if family else "PRODUCER_ALIAS_CONTEXT_UNRESOLVED",
            "family": family,
            "reviewMatrixPairStatus": "NOT_LISTED_IN_REVIEW_MATRIX_SOURCE_ADJUDICATION_REQUIRED" if family and family not in allowed else "LISTED_PAIR_NOT_SEMANTIC_APPROVAL" if family else "FAMILY_UNRESOLVED",
            "meaning": "Approval" if family == "communications-test" else "Decline candidate for non-communications context" if family else "NOT_ASSESSED"}


def code_one_context(case, pipeline, matrix=None):
    file = case.get("test_data_file")
    if not file:
        return {"status": "NO_PHYSICAL_OUTPUT_REFERENCE", "family": None}
    name = str(file).replace("\\", "/").rsplit("/", 1)[-1]
    path = pipeline / "qe_shaped_test_data" / name
    metadata_path = path.with_name(path.stem + ".meta.json")
    if not path.is_file() or not metadata_path.is_file():
        return {"status": "MISSING_PHYSICAL_OUTPUT", "family": None}
    metadata = read(metadata_path)
    payload = read(path)
    label = metadata.get("messageFamily")
    if metadata.get("testCaseId") != case["id"] or metadata.get("scenarioId") != case["scenario_id"] or not isinstance(payload.get(label), dict):
        return {"status": "METADATA_ROOT_JOIN_UNSUPPORTED", "family": None}
    return {**label_context(label, matrix), "label": label, "payloadSha256": hash_file(path), "metadataSha256": hash_file(metadata_path),
            "outcomeGate": "MISSING_TC_ORACLE_NO_HOST_RESULT", "certified": False}


def assess_required_absence(case, body, metadata):
    target = normalize(case.get("violated_element_name"))
    required = {"segment type": "85", "segment length": "84", "information byte": "44", "terminal identifier": "102"}
    if case.get("negative_class") != "missing_mandatory" or target not in required:
        return None
    declared = [node for _, node in descriptors(case.get("request", {}))
                if str(node["data_ref"].get("segment")) == "100"
                and normalize(node["data_ref"].get("element")) == target
                and node.get("method") == "deliberate_violation_missing_mandatory"]
    unavailable = [field for field in metadata.get("unavailableFields", [])
                   if str(field.get("segment")) == "100" and normalize(field.get("element")) == target]
    segment_names = {field.get("segmentFriendlyName") for field in metadata.get("fields", [])
                     if str(field.get("segment")) == "100" and field.get("segmentFriendlyName")}
    if len(declared) != 1 or declared[0].get("value") is not None or len(unavailable) != 1 or len(segment_names) != 1:
        return None
    segment_name = next(iter(segment_names))
    friendly = unavailable[0].get("friendly")
    segment = body.get(segment_name)
    if not friendly or not isinstance(segment, dict):
        return None
    absent = friendly not in segment
    return {"target": case.get("violated_element_name"), "segment": "100", "element": required[target],
            "method": "deliberate_violation_missing_mandatory", "physicalAbsent": absent,
            "payloadPath": [metadata["messageFamily"], segment_name, friendly],
            "valuePreserved": absent, "physicalPredicate": "REQUIRED_FIELD_ABSENT" if absent else "REQUIRED_FIELD_PRESENT",
            "sourceRequiredness": "Section 12.1 Standard Message Data Segment required field; not host-response behavior"}


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
        absence = assess_required_absence(case, body, metadata)
        if absence is not None:
            mutation_checks.append(absence)
        matching = [field for field in metadata.get("fields", []) if normalize(field.get("specElementName", "")) == normalize(target)]
        for field in matching:
            segment = body.get(field.get("segmentFriendlyName"))
            if not isinstance(segment, dict):
                continue
            composite = False
            if field.get("element") in segment:
                actual = segment[field["element"]]
            elif str(field.get("segment")) == "100" and normalize(target) == "card discretionary block data":
                account_fields = [account for account in metadata.get("fields", [])
                                  if str(account.get("segment")) == "100"
                                  and account.get("segmentFriendlyName") == field.get("segmentFriendlyName")
                                  and normalize(account.get("specElementName", "")) == "account number"]
                if len(account_fields) != 1 or not isinstance(field.get("value"), str) or not isinstance(account_fields[0].get("value"), str):
                    continue
                if segment.get("TrackData") != account_fields[0]["value"] + ":" + field["value"]:
                    continue
                actual = field["value"]
                composite = True
            else:
                continue
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
                                    "renderingEvidence": "EXACT_ACCOUNT_COLON_BLOCK_AGREEMENT" if composite else "DIRECT_METADATA_FIELD",
                                    "physicalPredicate": "EXCEEDS_SOURCE_MAX_51" if composite and case.get("negative_class") == "length_exceeded" and len(actual) > 51 else "SOURCE_NOT_YET_ADJUDICATED"})
        if mutation_checks:
            if absence is not None:
                mutation = "DECLARED_ABSENCE_PRESERVED" if absence["physicalAbsent"] else "DECLARED_ABSENCE_NOT_PRESERVED"
            else:
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
    required_fields = (("Segment Type", "85", "SegmentType", "100"),
                       ("Segment Length", "84", "SegmentLength", "100"),
                       ("Information Byte", "44", "InformationByte", "0"),
                       ("Terminal Identifier", "102", "TerminalID", "123456"))
    for target, element, friendly, present_value in required_fields:
        omission_case = {"scenario_type": "negative", "negative_class": "missing_mandatory", "violated_element_name": target,
            "request": {"Standard": {target: {"value": None, "method": "deliberate_violation_missing_mandatory",
                "data_ref": {"element": target, "segment": "100"}}}}}
        omission_meta = {"messageFamily": "Financial Transaction Request", "fields": [
            {"segment": "100", "segmentFriendlyName": "Standard", "specElementName": "Sequence Number",
             "element": "SequenceNumber", "value": "000001"}],
            "unavailableFields": [{"segment": "100", "element": target, "friendly": friendly}]}
        omission_payload = {"Financial Transaction Request": {"Standard": {"SequenceNumber": "000001"}}}
        preserved = evaluate_leg(omission_case, omission_payload, omission_meta)
        assert preserved["negativeMutation"] == "DECLARED_ABSENCE_PRESERVED"
        assert preserved["mutationChecks"][0]["element"] == element
        omission_payload["Financial Transaction Request"]["Standard"][friendly] = present_value
        assert evaluate_leg(omission_case, omission_payload, omission_meta)["negativeMutation"] == "DECLARED_ABSENCE_NOT_PRESERVED"
        omission_meta["unavailableFields"] = []
        assert evaluate_leg(omission_case, omission_payload, omission_meta)["negativeMutation"] == "NOT_ASSESSED"
    composite_case = {"scenario_type": "negative", "negative_class": "length_exceeded", "violated_element_name": "Card Discretionary Block Data",
                      "request": {"Standard": {"Block": {"value": "A" * 52, "method": "deliberate_violation_length",
                                  "data_ref": {"element": "Card Discretionary Block Data", "segment": "100"}}}}}
    composite_meta = {"messageFamily": "Financial Transaction Request", "fields": [
        {"segment": "100", "segmentFriendlyName": "Standard", "specElementName": "Account Number", "element": "AccountNumber", "value": "SYNTHETIC"},
        {"segment": "100", "segmentFriendlyName": "Standard", "specElementName": "Card Discretionary Block Data", "element": "Block", "value": "A" * 52}]}
    composite_payload = {"Financial Transaction Request": {"Standard": {"TrackData": "SYNTHETIC:" + "A" * 52}}}
    composite_result = evaluate_leg(composite_case, composite_payload, composite_meta)
    assert composite_result["negativeMutation"] == "DECLARED_VALUE_PRESERVED"
    assert composite_result["mutationChecks"][0]["physicalPredicate"] == "EXCEEDS_SOURCE_MAX_51"
    enum_case = {"scenario_type": "negative", "negative_class": "enum_invalid", "violated_element_name": "Card Discretionary Block Data",
                 "request": {"Standard": {"Block": {"value": "A" * 51, "method": "deliberate_violation_enum",
                     "data_ref": {"element": "Card Discretionary Block Data", "segment": "100"}}}}}
    enum_meta = {"messageFamily": "Financial Transaction Request", "fields": [
        {"segment": "100", "segmentFriendlyName": "Standard", "specElementName": "Account Number", "element": "AccountNumber", "value": "SYNTHETIC"},
        {"segment": "100", "segmentFriendlyName": "Standard", "specElementName": "Card Discretionary Block Data", "element": "Block", "value": "A" * 51}]}
    enum_payload = {"Financial Transaction Request": {"Standard": {"TrackData": "SYNTHETIC:" + "A" * 51}}}
    enum_result = evaluate_leg(enum_case, enum_payload, enum_meta)
    assert enum_result["negativeMutation"] == "DECLARED_VALUE_PRESERVED"
    assert enum_result["mutationChecks"][0]["physicalPredicate"] == "SOURCE_NOT_YET_ADJUDICATED"
    composite_payload["Financial Transaction Request"]["Standard"]["TrackData"] = "DIFFERENT:" + "A" * 52
    assert evaluate_leg(composite_case, composite_payload, composite_meta)["negativeMutation"] == "NOT_ASSESSED"
    objective = objective_evidence(
        {"requirement_ids": ["BR-SHARED", "BR-CASE"], "business_rule_assertion": "Case objective"},
        {"requirement_ids": ["BR-SHARED", "BR-SCENARIO"], "name": "Scenario objective"},
        {"BR-SHARED": {"id": "BR-SHARED", "statement": "Source claim", "source_rule_id": "ENT-ELEM-12",
                        "source_page": 364, "related_entity_ids": ["ENT-ELEM-12"]}},
        {364: "\\n".join(("--- PAGE 364 ---", "Number: 12 Name: Card Discretionary Block Data", "The format varies by application."))})
    assert objective["sharedRequirementIds"] == ["BR-SHARED"]
    assert objective["caseOnlyRequirementIds"] == ["BR-CASE"]
    assert objective["scenarioOnlyRequirementIds"] == ["BR-SCENARIO"]
    assert objective["semanticApproval"] == "NOT_ADJUDICATED"
    shared_reference = next(reference for reference in objective["sourceReferences"] if reference["requirementId"] == "BR-SHARED")
    assert shared_reference["pageLocatorStatus"] == "SOURCE_PAGE_AND_ELEMENT_ANCHOR_FOUND"
    legs = [{"role": "authorization", "fieldChecks": [{"element": "86", "predicate": "PASS", "value": "123456"}]},
            {"role": "completion", "fieldChecks": [{"element": "86", "predicate": "PASS", "value": "123456"}]}]
    assert lifecycle_observation(legs)["status"] == "SAME_SEQUENCE_OBSERVED"
    legs[1]["fieldChecks"][0]["value"] = "654321"
    assert lifecycle_observation(legs)["status"] == "DIFFERENT_SEQUENCE_OBSERVED"
    assert not lifecycle_observation(legs)["certified"]
    assert lifecycle_observation([])["status"] == "NO_UNIQUE_AUTHORIZATION_COMPLETION_PAIR"
    assert len(bounded_control_pairs()) == 3
    matrix = {"allowedCodeFamilyMeanings": [{"code": "1", "family": "financial"}, {"code": "1", "family": "communications-test"}]}
    assert label_context("Communications Test Request", matrix)["meaning"] == "Approval"
    assert label_context("Financial Transaction Request", matrix)["meaning"].startswith("Decline candidate")
    assert label_context("Financial Transactions", matrix)["status"] == "PRODUCER_ALIAS_CONTEXT_UNRESOLVED"
    assert label_context("Totals Request", matrix)["reviewMatrixPairStatus"] == "NOT_LISTED_IN_REVIEW_MATRIX_SOURCE_ADJUDICATION_REQUIRED"
    synthetic_rows = [
        {"caseId": "TC-002855", "physicalLegs": [{"mutationChecks": [{"physicalPredicate": "EXCEEDS_SOURCE_MAX_51"}]}]},
        {"caseId": "TC-002856", "physicalLegs": [{"mutationChecks": [{"physicalPredicate": "SOURCE_NOT_YET_ADJUDICATED"}]}]},
        *[{"caseId": case_id, "physicalLegs": [{"mutationChecks": [{"physicalPredicate": "REQUIRED_FIELD_ABSENT"}]}]}
          for case_id in ("TC-002950", "TC-002951", "TC-002952", "TC-002953")],
        {"caseId": "TC-004858", "physicalLegs": [{"mutationChecks": []}]}
    ]
    blockers = negative_blocker_assessment(synthetic_rows)
    assert blockers["targetCaseCount"] == 7 and blockers["effectivenessCertified"] == 0
    assert blockers["sourceBackedAbsentRequiredFieldsObserved"] == 4
    assert blockers["sourceMaximumViolationsObserved"] == 1
    assert blockers["applicationDependentEnumCasesStillReviewRequired"] == 1
    assert blockers["externalProcessorOracleCasesStillBlocked"] == 1
    print("PASS: required absences, composite mutations, source-page BR evidence, review-only alignment, and seven blocker states")


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
    matrix_file = Path(__file__).resolve().parents[1] / "specifications/ATL105/docs/specs/kb/element-83-response-code/coverage/element-83-response-code-br-validation-matrix.json"
    matrix = read(matrix_file)
    source = source_file.read_text(encoding="utf-8-sig")
    source_pages = index_source_pages(source)
    clauses = {"85": "Fixed value:  100", "86": "Representation: Fixed length of six digits"}
    section100 = source.index("12.1 Standard Message Data Segment", source.index("--- PAGE", 1000))
    actual_section100 = source.find("This section describes Data Segment No. 100", section100)
    if actual_section100 < 0 or clauses["85"] not in source[actual_section100:actual_section100 + 6000]:
        raise RuntimeError("Segment 100 source predicate not supported in bounded section")
    standard_layout = normalize(source[actual_section100:actual_section100 + 6000])
    mandatory_rows = ["1 85 segment type 3 r", "2 84 segment length 3 r", "3 44 information byte 1 r", "4 102 terminal identifier var. r"]
    if not all(value in standard_layout for value in mandatory_rows):
        raise RuntimeError("Standard Segment required-field source rows changed")
    if "7 12 card discretionary block data 51 c" not in standard_layout:
        raise RuntimeError("Card Discretionary Block Data source maximum changed")
    sequence_start = source.index("Number: 86 Name: Sequence Number")
    if clauses["86"] not in source[sequence_start:sequence_start + 1000]:
        raise RuntimeError("Sequence Number source predicate changed")
    if "in the case of a preauthorized transaction, the sequence number in the purchase/capture is the same sequence number" not in normalize(source[sequence_start:sequence_start + 4000]):
        raise RuntimeError("Preauthorization/completion source clause changed")
    block_start = source.index("Number: 12 Name: Card Discretionary")
    if "The format varies, depending on the application." not in source[block_start:block_start + 4000]:
        raise RuntimeError("Element 12 application-context clause changed")
    required_headings = ["Financial Transaction Request", "EMV Financial Transaction Request", "Totals Request",
                         "Electronic Mail Request", "Proprietary Data Load Request", "Communications Test Request"]
    if not all(heading in source for heading in required_headings):
        raise RuntimeError("Response context candidate headings unavailable")
    response_start = source.index("Number: 83 Name: Response Code")
    response_context = normalize(source[response_start:response_start + 5000])
    if "approved—communications test" not in response_context or "declined—all other transactions" not in response_context:
        raise RuntimeError("Source code-1 meaning clauses changed")
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
                    "familyDisposition": "CONTEXT_CANDIDATE_ONLY_NOT_SEMANTIC_APPROVAL",
                    "physicalContextEvidence": code_one_context(case, pipeline, matrix),
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
    hashes = {file.name: hash_file(file) for file in (case_file, scenario_file, requirement_file, source_file, matrix_file)}
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
            leg["role"] = metadata.get("role")
            leg["step"] = metadata.get("step")
            leg["file"] = filename
            leg["payloadSha256"] = hash_file(payload_file)
            leg["metadataSha256"] = hash_file(metadata_file)
            legs.append(leg)
        checks = [check for leg in legs for check in leg["fieldChecks"]]
        methods = collections.Counter(str(node.get("method")) for _, node in descriptors(case.get("request", {})))
        objective = objective_evidence(case, scenario, requirements, source_pages)
        row = {"caseId": case["id"], "scenarioId": case["scenario_id"], "intent": case["scenario_type"],
               "transactionLabel": case.get("transaction_type"), "negativeClass": case.get("negative_class"),
               "violatedElement": case.get("violated_element_name"), "scenarioName": scenario.get("name"),
               "caseBrIds": sorted(links(case)), "scenarioBrIds": sorted(links(scenario)),
               "sharedBrIds": sorted(links(case) & links(scenario)),
               "scenarioBrsNotReferencedByCase": sorted(links(scenario) - links(case)),
               "caseBrsNotDeclaredByScenario": sorted(links(case) - links(scenario)),
            "linkedBrStatements": [{"id": reference["requirementId"],
                "statement": reference.get("statement"), "sourceRule": reference.get("sourceRuleId"),
                "sourcePage": reference.get("sourcePage"),
                "linkedToCase": reference.get("linkedToCase", False),
                "linkedToScenario": reference.get("linkedToScenario", False),
                "pageLocatorStatus": reference.get("pageLocatorStatus", reference.get("sourceStatus")),
                "excerptLocator": reference.get("excerptLocator"),
                "sourceExcerpt": reference.get("excerpt")}
                for reference in objective["sourceReferences"]],
            "objectiveEvidence": objective,
               "scenarioExpectedCode": scenario.get("expected_response_code"),
               "caseExpectedResponse": case.get("expected_response"), "physicalLegs": legs,
               "sourcePredicateFailures": sum(check["predicate"] == "FAIL" for check in checks),
               "sourcePredicatePasses": sum(check["predicate"] == "PASS" for check in checks),
               "negativeEffectiveness": "NOT_CERTIFIED_NO_ISOLATED_BASELINE_OR_OUTCOME_ORACLE" if case["scenario_type"] == "negative" else "NOT_APPLICABLE",
               "potentialConfounders": {method: count for method, count in methods.items() if method in {
                   "placeholder", "awaiting_client_value", "spec_unspecified_code", "derived_at_wire_encoding"}},
               "semanticAlignment": "NOT_PROVEN_SHARED_IDS_ARE_NOT_BEHAVIOR_EQUIVALENCE",
            "sourceContextCandidates": [{"requirementId": br, "element": requirements[br].get("source_rule_id"),
                "status": "SOURCE_FIELD_REFERENCE_CANDIDATE_NOT_FULL_BR_PROOF"}
                for br in sorted(links(case)) if br in requirements and requirements[br].get("source_rule_id") in {"ENT-ELEM-85", "ENT-ELEM-86", "ENT-ELEM-12"}],
            "flowSequenceEvidence": lifecycle_observation(legs) if case["scenario_type"] == "flow" else None,
                "objectiveGate": objective["alignmentStatus"],
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
            "code1PhysicalContextStates": dict(collections.Counter(row["physicalContextEvidence"]["status"] for row in code_one_rows)),
            "code1ContextCandidateFamilies": dict(collections.Counter(str(row["physicalContextEvidence"]["family"]) for row in code_one_rows)),
               "code1ReviewMatrixConcerns": sum(row["physicalContextEvidence"].get("reviewMatrixPairStatus") == "NOT_LISTED_IN_REVIEW_MATRIX_SOURCE_ADJUDICATION_REQUIRED" for row in code_one_rows),
               "responseMatrixAuthority": matrix["status"],
            "flowSequenceStates": dict(collections.Counter(row["flowSequenceEvidence"]["status"] for row in rows if row["flowSequenceEvidence"])),
            "brObjectiveEvidence": {
                "casesWithSharedRequirementIds": sum(bool(row["objectiveEvidence"]["sharedRequirementIds"]) for row in rows),
                "casesWithoutSharedRequirementIds": sum(not row["objectiveEvidence"]["sharedRequirementIds"] for row in rows),
                "casesWithExactRequirementSetMatch": sum(row["objectiveEvidence"]["caseRequirementIds"] == row["objectiveEvidence"]["scenarioRequirementIds"] for row in rows),
                "casesWithDivergentRequirementSets": sum(row["objectiveEvidence"]["caseRequirementIds"] != row["objectiveEvidence"]["scenarioRequirementIds"] for row in rows),
                "casesWithBothRequirementSetsEmpty": sum(not row["objectiveEvidence"]["caseRequirementIds"] and not row["objectiveEvidence"]["scenarioRequirementIds"] for row in rows),
                "casesWithExplicitTestcaseObjectiveText": sum(bool(row["objectiveEvidence"]["caseObjectiveClaim"]) for row in rows),
                "casesWithoutExplicitTestcaseObjectiveText": sum(not row["objectiveEvidence"]["caseObjectiveClaim"] for row in rows),
                "requirementReferences": sum(len(row["objectiveEvidence"]["sourceReferences"]) for row in rows),
                "sourcePageMarkersResolved": sum(reference.get("pageLocatorStatus", "").startswith("SOURCE_PAGE")
                    and reference.get("pageLocatorStatus") != "SOURCE_PAGE_NOT_FOUND"
                    for row in rows for reference in row["objectiveEvidence"]["sourceReferences"]),
                "sourceElementAnchorsLocated": sum(reference.get("pageLocatorStatus") == "SOURCE_PAGE_AND_ELEMENT_ANCHOR_FOUND"
                    for row in rows for reference in row["objectiveEvidence"]["sourceReferences"]),
                "semanticEquivalenceConfirmed": 0,
                "policy": "Shared IDs and page excerpts are trace evidence only; objective equivalence remains REVIEW_REQUIRED."
            },
            "remainingGates": {"semanticBrAlignment": "REVIEW_REQUIRED", "negativeIsolation": "REVIEW_REQUIRED",
                "processorOracle": "EXTERNAL_EVIDENCE_REQUIRED", "wireSerialization": "NOT_IMPLEMENTED_FOR_THIS_DELIVERY",
                "fullJavaPayloadValidation": "ADAPTER_CONTRACT_REQUIRED"},
            "expandedSourceGates": {"requiredFields": mandatory_rows, "cardDiscretionaryMaximum": 51,
                "cardDiscretionaryEnumeration": "NO_UNIVERSAL_ENUM_ADJUDICATED_APPLICATION_CONTEXT_REQUIRED",
                "responseCode1": "COMMUNICATIONS_APPROVAL_ELSE_DECLINE_CANDIDATE_NO_HOST_ORACLE"},
            "controlScope": "VALID_WITHIN_PREDICATE_ONLY_NOT_VALID_FULL_MESSAGE_FIXTURES",
               "executionCertified": False, "independentCoverage": "NOT_CALCULABLE"}
    summary["negativeBlockerAssessment"] = negative_blocker_assessment(rows)
    assert len(rows) == 100 and all(strata[kind] == 25 for kind in ("positive", "negative", "boundary", "flow"))
    (root / "semantic-first-batch-summary.json").write_text(json.dumps(summary, indent=2, ensure_ascii=True), encoding="utf-8")
    (root / "semantic-first-batch-register.json").write_text(json.dumps(rows, indent=2, ensure_ascii=True), encoding="utf-8")
    (root / "expected-code-1-review-register.json").write_text(json.dumps(code_one_rows, indent=2, ensure_ascii=True), encoding="utf-8")
    (root / "semantic-bounded-control-pairs.json").write_text(json.dumps({"sourceSha256": hashes[source_file.name],
        "controls": bounded_control_pairs(), "pairedAbsencePackingLifecycleTests": "EXECUTED_BY_SELF_TEST",
        "fullMessageValidity": "NOT_CERTIFIED", "executionCertified": False}, indent=2), encoding="utf-8")
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