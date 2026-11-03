import argparse
import collections
import csv
import hashlib
import ijson
import json
import os
import re
import runpy
from pathlib import Path


MODULE = Path(__file__).resolve().parents[1]
REVIEW_DEFAULT = MODULE / "specifications/ATL105/test-output/ai-solution-independent-review/2026-10-06-complete-handoff"
ASSESSMENT_DEFAULT = REVIEW_DEFAULT / "semantic-alignment-2026-10-07-finalized"
OUTPUT_DEFAULT = REVIEW_DEFAULT / "ai-delivery-exhaustive-feedback-2026-10-07"
MFI_NAMES = {"messageformatversionidentifier", "messageformatindicator"}
MFI_EXPECTED = "ATL105"
NUMBER_OF_SEGMENTS_NAMES = {"numberofsegments"}
SECTION_1_NAMES = {"datasection1", "section1", "datasection01", "section01"}
PLACEHOLDER_VALUES = {"unknown", "tbd", "todo", "depends", "placeholder", "replace_me", "replace_with_value"}


def native(path):
    path = Path(os.path.normpath(str(path)))
    return Path("\\\\?\\" + str(path.resolve())) if os.name == "nt" and not str(path).startswith("\\\\?\\") else path


def read_json(path):
    return json.loads(path.read_text(encoding="utf-8-sig"))


class DuplicateJsonKeyError(ValueError):
    pass


def unique_object(pairs):
    result = {}
    for key, value in pairs:
        if key in result:
            raise DuplicateJsonKeyError(f"Duplicate JSON object key: {key}")
        result[key] = value
    return result


def read_json_bytes_safe(path):
    raw = path.read_bytes()
    try:
        return raw, json.loads(raw.decode("utf-8-sig"), object_pairs_hook=unique_object)
    except (UnicodeDecodeError, json.JSONDecodeError, DuplicateJsonKeyError) as error:
        return raw, error


def digest_bytes(raw):
    return hashlib.sha256(raw).hexdigest()


def digest_file(path):
    with path.open("rb") as stream:
        return hashlib.file_digest(stream, "sha256").hexdigest()


def normalize_key(value):
    return re.sub(r"[^a-z0-9]", "", str(value).casefold())


def scan_keys(node, path=(), in_section_1=False, output=None):
    if output is None:
        output = {"mfi": [], "numberOfSegments": [], "section1": []}
    if isinstance(node, dict):
        for key, value in node.items():
            normalized = normalize_key(key)
            current = path + (str(key),)
            section_1 = in_section_1 or normalized in SECTION_1_NAMES
            if normalized in SECTION_1_NAMES:
                output["section1"].append({"path": list(current), "valueType": type(value).__name__})
            if normalized in MFI_NAMES or (section_1 and normalized in {"55", "element55"}):
                output["mfi"].append({"path": list(current), "value": value})
            if normalized in NUMBER_OF_SEGMENTS_NAMES or (section_1 and normalized in {"63", "element63"}):
                output["numberOfSegments"].append({"path": list(current), "value": value})
            scan_keys(value, current, section_1, output)
    elif isinstance(node, list):
        for index, value in enumerate(node):
            scan_keys(value, path + (str(index),), in_section_1, output)
    return output


def scalar_leaves(node, path=()):
    if isinstance(node, dict):
        for key, value in node.items():
            yield from scalar_leaves(value, path + (str(key),))
    elif isinstance(node, list):
        for index, value in enumerate(node):
            yield from scalar_leaves(value, path + (str(index),))
    else:
        yield path, node


def count_segment_objects(body):
    if not isinstance(body, dict):
        return None
    count = 0
    for value in body.values():
        candidates = value if isinstance(value, list) else [value]
        for candidate in candidates:
            if isinstance(candidate, dict) and any(normalize_key(key) == "segmenttype" for key in candidate):
                count += 1
    return count or None


def normalized_lookup(mapping, wanted):
    if not isinstance(mapping, dict) or not isinstance(wanted, str):
        return None, None
    if wanted in mapping:
        return wanted, mapping[wanted]
    normalized = normalize_key(wanted)
    matches = [(key, value) for key, value in mapping.items() if normalize_key(key) == normalized]
    return matches[0] if len(matches) == 1 else (None, None)


def field_consistency(body, metadata):
    fields = metadata.get("fields", [])
    result = {"expectedScalarFields": 0, "presentAndEqual": 0, "missingFromPayload": 0,
              "valueMismatch": 0, "compositeUnassessed": 0, "mergedValuePresent": 0,
              "mergedValueUnverified": 0, "reshapedValuePresent": 0,
              "reshapedValueUnverified": 0, "mappingUnresolved": 0, "unavailableValueSkipped": 0,
              "issues": []}
    if not isinstance(fields, list) or not isinstance(body, dict):
        result["metadataFieldShape"] = "UNSUPPORTED"
        return result
    result["metadataFieldShape"] = "LIST"
    for field in fields:
        if not isinstance(field, dict):
            continue
        expected = field.get("value")
        if expected is None:
            result["unavailableValueSkipped"] += 1
            continue
        result["expectedScalarFields"] += 1
        segment_name = field.get("segmentFriendlyName")
        element_name = field.get("element")
        if str(field.get("dataSection")) == "1" and normalize_key(segment_name or "") in {
            "datasection1", "datasectionno1", "section1", "sectionno1"}:
            _, segment = normalized_lookup(body, "Data Section 1")
            if segment is None:
                _, segment = normalized_lookup(body, "Section 1")
        else:
            _, segment = normalized_lookup(body, segment_name)
        path = [segment_name, element_name]
        actual_key, actual = normalized_lookup(segment, element_name)
        if actual_key is None:
            merged_into = field.get("mergedInto")
            reshaped_into = field.get("reshapedInto")
            transform_target = merged_into or reshaped_into
            if transform_target:
                _, transformed = normalized_lookup(segment, transform_target)
                represented = isinstance(transformed, str) and str(expected) in transformed
                if merged_into:
                    result["mergedValuePresent" if represented else "mergedValueUnverified"] += 1
                else:
                    result["reshapedValuePresent" if represented else "reshapedValueUnverified"] += 1
                if transformed is None:
                    result["mappingUnresolved"] += 1
                    result["issues"].append({"kind": "METADATA_TRANSFORM_TARGET_MISSING", "path": path,
                        "transformTarget": transform_target, "expected": expected, "dataSource": field.get("dataSource")})
                continue
            result["missingFromPayload"] += 1
            result["issues"].append({"kind": "METADATA_VALUE_FIELD_MISSING", "path": path,
                "elementName": field.get("specElementName"), "expected": expected, "dataSource": field.get("dataSource")})
            continue
        if isinstance(actual, (dict, list)) or isinstance(expected, (dict, list)):
            result["compositeUnassessed"] += 1
            continue
        if actual == expected:
            result["presentAndEqual"] += 1
        else:
            result["valueMismatch"] += 1
            result["issues"].append({"kind": "METADATA_VALUE_MISMATCH", "path": path,
                "elementName": field.get("specElementName"), "expected": expected, "actual": actual,
                "dataSource": field.get("dataSource")})
    return result


def add_issue(issues, filename, references, kind, severity, detail):
    issues.append({"file": filename, "testCaseIds": sorted(references["testCaseIds"]),
        "scenarioIds": sorted(references["scenarioIds"]),
        "kind": kind, "severity": severity, "detail": detail})


def self_test():
    present = {"Data Section 1": {"Message Format Version Identifier": "ATL105", "Number of Segments": "01"}}
    absent = {"NumberofSegments": "01", "Standard Segment": {"SegmentType": "100"}}
    numeric = {"dataSection1": {"55": "ATL105", "63": "01"}}
    assert scan_keys(present)["mfi"][0]["value"] == "ATL105"
    assert scan_keys(absent)["mfi"] == []
    assert scan_keys(numeric)["mfi"][0]["value"] == "ATL105"
    assert scan_keys(present)["numberOfSegments"][0]["value"] == "01"
    assert count_segment_objects(absent) == 1
    composite = {"Data Section 1": {"Message Format Version Identifier": "ATL105"},
        "Standard Segment": {"TrackData": "411111:0625"}}
    metadata = {"fields": [
        {"segment": "DATA-SECTION-1", "segmentFriendlyName": "Data Section No. 1",
         "dataSection": "1", "element": "MessageFormatVersionIdentifier",
         "specElementName": "Message Format Version Identifier", "value": "ATL105"},
        {"segment": "100", "segmentFriendlyName": "Standard Segment", "element": "AccountNumber",
         "specElementName": "Account Number", "value": "411111", "mergedInto": "TrackData"}]}
    checks = field_consistency(composite, metadata)
    assert checks["presentAndEqual"] == 1 and checks["mergedValuePresent"] == 1
    print("PASS: Element 55 aliases, Section 1 / Element 63 detection, and segment counting")


def audit(review, assessment, output, limit=None):
    review = native(review)
    assessment = native(assessment)
    output = native(output)
    if output.exists():
        raise ValueError("Output directory already exists; choose a new audit destination")
    review_state = read_json(review / "complete-handoff-analysis.json")
    archive = native(review_state["archive"])
    pipeline = archive / "pipeline_run_artifacts"
    payload_root = pipeline / "qe_shaped_test_data"
    catalog_root = archive / "scenerio_req_5_oct/scenerio_req_5_oct"
    requirement_path = catalog_root / "step5_requirements/approved/requirement_catalog.json"
    requirements = {row["id"]: row for row in read_json(requirement_path)["requirements"]}
    br_rows = read_json(assessment / "semantic-br-assessment.json")
    ts_rows = read_json(assessment / "semantic-ts-assessment.json")
    case_assessment_path = assessment / "semantic-tc-td-assessment.json"
    summary = read_json(assessment / "semantic-chain-summary.json")
    scenarios = {row["scenarioId"]: row for row in ts_rows}
    requirement_assessment_ids = [row["requirementId"] for row in br_rows]
    if len(set(requirement_assessment_ids)) != len(requirement_assessment_ids) or set(requirement_assessment_ids) != set(requirements):
        raise ValueError("BR assessment population does not exactly match the source requirement catalog")
    if len(scenarios) != len(ts_rows):
        raise ValueError("Scenario assessment contains duplicate IDs")
    expected_cases = int(summary["testCases"])
    expected_legs = int(summary["physicalPayloadLegsAssessed"])
    with (review / "intake-file-hashes.csv").open(encoding="utf-8-sig", newline="") as stream:
        manifest = {row["path"]: row for row in csv.DictReader(stream)}
    for source_path in [requirement_path, catalog_root / "scenarios/approved/approved_scenarios.json"]:
        relative = source_path.relative_to(archive).as_posix()
        expected_hash = manifest.get(relative, {}).get("sha256")
        if not expected_hash or digest_file(source_path) != expected_hash:
            raise ValueError("Frozen source catalog hash mismatch: " + source_path.name)
    if len(requirements) != int(summary["businessRequirements"]) or len(scenarios) != int(summary["scenarios"]):
        raise ValueError("Source catalog populations differ from the frozen assessment summary")

    br_scenarios = collections.defaultdict(set)
    br_cases = collections.defaultdict(set)
    br_file_refs = collections.defaultdict(set)
    br_issue_counts = collections.defaultdict(collections.Counter)
    scenario_cases = collections.defaultdict(set)
    scenario_case_requirements = collections.defaultdict(lambda: collections.defaultdict(set))
    file_refs = {}
    case_case_divergent = set()
    case_ids_seen = set()
    case_rows_written = 0
    physical_legs = 0
    unrecognized_br_ids = set()
    case_link_divergence_count = 0
    case_missing_assertion_count = 0
    case_missing_outcome_count = 0
    output.mkdir(parents=True)

    for scenario_id, scenario in scenarios.items():
        for requirement_id in scenario.get("requirementIds", []):
            if requirement_id in requirements:
                br_scenarios[requirement_id].add(scenario_id)
            else:
                unrecognized_br_ids.add(requirement_id)

    case_feedback_path = output / "case-feedback.jsonl"
    with case_assessment_path.open("rb") as source, case_feedback_path.open("w", encoding="utf-8") as case_out:
        for case in ijson.items(source, "item", use_float=True):
            if limit is not None and case_rows_written >= limit:
                break
            case_id = case["caseId"]
            scenario_id = case["scenarioId"]
            if case_id in case_ids_seen:
                raise ValueError("Duplicate TC ID in assessed case population: " + case_id)
            case_ids_seen.add(case_id)
            if scenario_id not in scenarios:
                raise ValueError("TC references unknown TS ID: " + case_id + " -> " + scenario_id)
            direct_ids = set(case.get("requirementIds") or [])
            scenario_ids = set(case.get("scenarioRequirementIds") or [])
            scenario_cases[scenario_id].add(case_id)
            divergence = direct_ids.symmetric_difference(scenario_ids)
            if divergence:
                case_link_divergence_count += 1
                case_case_divergent.add(case_id)
            if not case.get("objectiveClaim"):
                case_missing_assertion_count += 1
            if not case.get("expectedResponse"):
                case_missing_outcome_count += 1
            for requirement_id in direct_ids | scenario_ids:
                if requirement_id not in requirements:
                    unrecognized_br_ids.add(requirement_id)
            legs = case.get("physicalLegs") or []
            physical_legs += len(legs)
            leg_summaries = []
            for leg in legs:
                filename = Path(str(leg.get("file", ""))).name
                if not filename:
                    continue
                ref = file_refs.setdefault(filename, {"testCaseIds": set(), "scenarioIds": set(),
                    "requirementIds": set(), "assessmentStatuses": set(), "referenceCount": 0})
                ref["testCaseIds"].add(case_id)
                ref["scenarioIds"].add(scenario_id)
                ref["requirementIds"].update(direct_ids)
                ref["referenceCount"] += 1
                ref["assessmentStatuses"].add(leg.get("status", "NOT_RECORDED"))
                for requirement_id in direct_ids:
                    if requirement_id in requirements:
                        br_file_refs[requirement_id].add(filename)
                leg_summaries.append({"file": filename, "assessmentStatus": leg.get("status"),
                    "fieldChecks": leg.get("fieldChecks", []), "metadataEvidenceStatus": leg.get("metadataEvidenceStatus")})
            for requirement_id in direct_ids:
                if requirement_id in requirements:
                    br_cases[requirement_id].add(case_id)
                    scenario_case_requirements[scenario_id][case_id].add(requirement_id)
            case_out.write(json.dumps({"caseId": case_id, "scenarioId": scenario_id,
                "intent": case.get("intent"), "transactionLabel": case.get("transactionLabel"),
                "requirementIds": sorted(direct_ids), "scenarioRequirementIds": sorted(scenario_ids),
                "requirementLinkDivergence": sorted(divergence),
                "objectiveClaimPresent": bool(case.get("objectiveClaim")),
                "expectedResponsePresent": bool(case.get("expectedResponse")),
                "declaredTdLegs": leg_summaries}, ensure_ascii=True) + "\n")
            case_rows_written += 1

    if limit is None and case_rows_written != expected_cases:
        raise ValueError(f"Case coverage mismatch: read {case_rows_written}, expected {expected_cases}")
    if limit is None and physical_legs != expected_legs:
        raise ValueError(f"Physical leg count mismatch: read {physical_legs}, expected {expected_legs}")

    td_rows = []
    issues = []
    json_valid_count = 0
    mfi_present_count = mfi_missing_count = mfi_wrong_value_count = 0
    root_mismatch_count = metadata_missing_count = payload_missing_count = 0
    metadata_hash_mismatch_count = payload_hash_mismatch_count = metadata_join_mismatch_count = 0
    section1_present_count = e63_present_count = segment_count_mismatch_count = 0
    metadata_field_missing_count = metadata_field_value_mismatch_count = placeholder_candidate_count = 0
    metadata_field_mapping_unresolved_count = 0
    for filename, references in sorted(file_refs.items()):
        payload_path = payload_root / filename
        metadata_path = payload_path.with_name(payload_path.stem + ".meta.json")
        payload_manifest_key = "pipeline_run_artifacts/qe_shaped_test_data/" + filename
        metadata_manifest_key = "pipeline_run_artifacts/qe_shaped_test_data/" + metadata_path.name
        payload_exists = payload_path.is_file()
        metadata_exists = metadata_path.is_file()
        if not payload_exists:
            payload_missing_count += 1
            add_issue(issues, filename, references, "TD_PAYLOAD_MISSING", "ERROR", "Declared physical TD JSON file is absent")
        if not metadata_exists:
            metadata_missing_count += 1
            add_issue(issues, filename, references, "TD_METADATA_MISSING", "ERROR", "Declared TD metadata sidecar is absent")
        payload_hash_status = metadata_hash_status = "NOT_CHECKED"
        payload = metadata = None
        parse_status = "MISSING"
        if payload_exists:
            raw, parsed = read_json_bytes_safe(payload_path)
            expected_hash = manifest.get(payload_manifest_key, {}).get("sha256")
            actual_hash = digest_bytes(raw)
            payload_hash_status = "MATCH" if expected_hash == actual_hash else "NO_MANIFEST" if not expected_hash else "MISMATCH"
            if payload_hash_status == "MISMATCH":
                payload_hash_mismatch_count += 1
                add_issue(issues, filename, references, "TD_PAYLOAD_HASH_MISMATCH", "ERROR",
                    f"Current SHA-256 {actual_hash} differs from intake manifest {expected_hash}")
            elif payload_hash_status == "NO_MANIFEST":
                add_issue(issues, filename, references, "TD_PAYLOAD_HASH_NOT_IN_MANIFEST", "REVIEW", "No frozen intake hash was found")
            if isinstance(parsed, Exception):
                parse_status = "INVALID_JSON"
                add_issue(issues, filename, references, "TD_JSON_PARSE_ERROR", "ERROR", str(parsed))
            else:
                payload = parsed
                parse_status = "VALID_JSON"
                json_valid_count += 1
        if metadata_exists:
            raw_meta, parsed_meta = read_json_bytes_safe(metadata_path)
            expected_meta_hash = manifest.get(metadata_manifest_key, {}).get("sha256")
            actual_meta_hash = digest_bytes(raw_meta)
            metadata_hash_status = "MATCH" if expected_meta_hash == actual_meta_hash else "NO_MANIFEST" if not expected_meta_hash else "MISMATCH"
            if metadata_hash_status == "MISMATCH":
                metadata_hash_mismatch_count += 1
                add_issue(issues, filename, references, "TD_METADATA_HASH_MISMATCH", "ERROR",
                    f"Current SHA-256 {actual_meta_hash} differs from intake manifest {expected_meta_hash}")
            if isinstance(parsed_meta, Exception):
                add_issue(issues, filename, references, "TD_METADATA_PARSE_ERROR", "ERROR", str(parsed_meta))
            else:
                metadata = parsed_meta

        family = metadata.get("messageFamily") if isinstance(metadata, dict) else None
        if isinstance(metadata, dict) and (metadata.get("testCaseId") not in references["testCaseIds"]
                or metadata.get("scenarioId") not in references["scenarioIds"]):
            metadata_join_mismatch_count += 1
            add_issue(issues, filename, references, "TD_METADATA_JOIN_MISMATCH", "ERROR",
                f"Metadata declares TC={metadata.get('testCaseId')} TS={metadata.get('scenarioId')}")
        payload_roots = sorted(payload.keys()) if isinstance(payload, dict) else []
        body = None
        if payload is not None and not isinstance(payload, dict):
            root_status = "ROOT_NOT_OBJECT"
            root_mismatch_count += 1
            add_issue(issues, filename, references, "TD_ROOT_NOT_OBJECT", "ERROR", f"Root type is {type(payload).__name__}")
        elif payload is not None and family not in payload:
            root_status = "MESSAGE_FAMILY_ROOT_MISSING"
            root_mismatch_count += 1
            add_issue(issues, filename, references, "TD_MESSAGE_FAMILY_ROOT_MISSING", "ERROR",
                f"Metadata messageFamily={family!r}; payload roots={payload_roots}")
        elif payload is not None:
            body = payload[family]
            root_status = "MESSAGE_FAMILY_ROOT_PRESENT" if isinstance(body, dict) else "MESSAGE_FAMILY_BODY_NOT_OBJECT"
            if not isinstance(body, dict):
                root_mismatch_count += 1
                add_issue(issues, filename, references, "TD_MESSAGE_FAMILY_BODY_NOT_OBJECT", "ERROR",
                    f"Message body type is {type(body).__name__}")
        else:
            root_status = "NOT_ASSESSED"

        scanned = scan_keys(body) if body is not None else {"mfi": [], "numberOfSegments": [], "section1": []}
        mfi_nodes, section1_nodes, e63_nodes = scanned["mfi"], scanned["section1"], scanned["numberOfSegments"]
        if body is not None and section1_nodes:
            section1_present_count += 1
        if body is not None and e63_nodes:
            e63_present_count += 1
        if body is None:
            mfi_status = "NOT_ASSESSED"
            mfi_values = []
            mfi_value_status = "NOT_ASSESSED"
        elif mfi_nodes:
            mfi_status = "PRESENT"
            mfi_present_count += 1
            mfi_values = [node["value"] for node in mfi_nodes]
            wrong_values = [value for value in mfi_values if not isinstance(value, str) or value != MFI_EXPECTED]
            if wrong_values:
                mfi_value_status = "NOT_ATL105"
                mfi_wrong_value_count += 1
                add_issue(issues, filename, references, "MFI_VALUE_NOT_ATL105", "REVIEW",
                    f"Element 55 value(s)={wrong_values!r}; independent package default is {MFI_EXPECTED!r}")
            else:
                mfi_value_status = "ATL105"
        else:
            mfi_status = "MISSING"
            mfi_values = []
            mfi_value_status = "NOT_APPLICABLE"
            mfi_missing_count += 1
            add_issue(issues, filename, references, "MFI_MISSING_ELEMENT_55", "ERROR",
                "No Message Format Version Identifier / Message Format Indicator / Section 1 Element 55 field was found in the physical JSON payload")
        if body is not None and not section1_nodes:
            add_issue(issues, filename, references, "DATA_SECTION_1_CONTAINER_NOT_FOUND", "REVIEW",
                "No Data Section 1 container was found in this JSON representation")
        if body is not None and not e63_nodes:
            add_issue(issues, filename, references, "NUMBER_OF_SEGMENTS_MISSING_ELEMENT_63", "REVIEW",
                "No Number of Segments / Section 1 Element 63 field was found in this JSON representation")

        declared_segment_count = e63_nodes[0]["value"] if e63_nodes else None
        rendered_segment_count = count_segment_objects(body)
        segment_count_status = "NOT_ASSESSED"
        if declared_segment_count is not None and rendered_segment_count is not None:
            try:
                segment_count_status = "MATCH" if int(str(declared_segment_count)) == rendered_segment_count else "MISMATCH"
            except (TypeError, ValueError):
                segment_count_status = "DECLARED_VALUE_NOT_INTEGER"
            if segment_count_status != "MATCH":
                segment_count_mismatch_count += 1
                add_issue(issues, filename, references, "NUMBER_OF_SEGMENTS_COUNT_MISMATCH", "REVIEW",
                    f"Declared={declared_segment_count!r}; direct rendered segment objects={rendered_segment_count}")
        leaves = list(scalar_leaves(body)) if body is not None else []
        non_string_scalar_count = sum(value is not None and not isinstance(value, str) for _, value in leaves)
        placeholders = [{"path": list(path), "value": value} for path, value in leaves
            if isinstance(value, str) and value.strip().casefold() in PLACEHOLDER_VALUES]
        if placeholders:
            placeholder_candidate_count += 1
            add_issue(issues, filename, references, "PLACEHOLDER_VALUE_CANDIDATE", "REVIEW", json.dumps(placeholders[:10], ensure_ascii=True))
        consistency = field_consistency(body, metadata) if body is not None and isinstance(metadata, dict) else {
            "expectedScalarFields": 0, "presentAndEqual": 0, "missingFromPayload": 0,
            "valueMismatch": 0, "compositeUnassessed": 0, "mergedValuePresent": 0,
            "mergedValueUnverified": 0, "reshapedValuePresent": 0, "reshapedValueUnverified": 0,
            "mappingUnresolved": 0, "unavailableValueSkipped": 0, "issues": []}
        metadata_field_missing_count += consistency["missingFromPayload"]
        metadata_field_value_mismatch_count += consistency["valueMismatch"]
        metadata_field_mapping_unresolved_count += consistency["mappingUnresolved"] + consistency["mergedValueUnverified"] + consistency["reshapedValueUnverified"]
        if consistency["issues"]:
            add_issue(issues, filename, references, "METADATA_FIELD_DIVERGENCE", "REVIEW",
                json.dumps(consistency["issues"], ensure_ascii=True))
        metadata_fields = metadata.get("fields", []) if isinstance(metadata, dict) else []
        mfi_metadata_values = [field.get("value") for field in metadata_fields if isinstance(field, dict)
              and (normalize_key(field.get("specElementName", "")) in MFI_NAMES or
                  (str(field.get("element_no", field.get("element", ""))) == "55" and str(field.get("dataSection", "")) == "1"))]
        if mfi_metadata_values and body is not None and not mfi_nodes:
            add_issue(issues, filename, references, "MFI_METADATA_PAYLOAD_DIVERGENCE", "ERROR",
                f"Metadata expects Element 55 value(s) {mfi_metadata_values!r}, but payload has no matching field")
        sources = collections.Counter(str(field.get("dataSource", "NOT_DECLARED")) for field in metadata_fields if isinstance(field, dict))
        provisional_count = sum(field.get("provisional") is True for field in metadata_fields if isinstance(field, dict))
        unavailable = metadata.get("unavailableFields", []) if isinstance(metadata, dict) else []
        unresolved = metadata.get("dataSections", {}).get("unresolved", {}).get("segments", []) if isinstance(metadata, dict) else []
        td_rows.append({"file": filename, "payloadPath": str(payload_path), "metadataPath": str(metadata_path),
            "referenceCount": references["referenceCount"], "testCaseIds": sorted(references["testCaseIds"]),
            "scenarioIds": sorted(references["scenarioIds"]), "requirementIds": sorted(references["requirementIds"]),
            "assessmentStatuses": sorted(references["assessmentStatuses"]), "payloadExists": payload_exists,
            "metadataExists": metadata_exists, "payloadHashStatus": payload_hash_status,
            "metadataHashStatus": metadata_hash_status, "jsonParseStatus": parse_status, "rootStatus": root_status,
            "messageFamily": family, "payloadRoots": payload_roots, "dataSection1ContainerPresent": bool(section1_nodes),
            "element55Status": mfi_status, "element55Values": mfi_values,
            "element55Paths": [node["path"] for node in mfi_nodes], "element55ValueStatus": mfi_value_status,
            "element63Status": "PRESENT" if e63_nodes else "MISSING", "element63Values": [node["value"] for node in e63_nodes],
            "declaredSegmentCount": declared_segment_count, "renderedSegmentObjectCount": rendered_segment_count,
            "segmentCountStatus": segment_count_status,
            "metadataFieldCheck": {key: value for key, value in consistency.items() if key != "issues"},
            "metadataElement55ExpectedValues": mfi_metadata_values, "metadataDataSourceCounts": dict(sources),
            "metadataProvisionalFieldCount": provisional_count,
            "metadataUnavailableFieldCount": len(unavailable) if isinstance(unavailable, list) else 0,
            "metadataUnresolvedSegmentCount": len(unresolved) if isinstance(unresolved, list) else 0,
            "nonStringScalarLeafCount": non_string_scalar_count, "placeholderValueCandidates": placeholders[:10]})

    if limit is None and physical_legs != expected_legs:
        raise ValueError("Physical payload leg count changed during TD audit")
    if limit is None and len(td_rows) != len(file_refs):
        raise ValueError("Unique physical TD file coverage mismatch")

    declared_files = set(file_refs)
    unreferenced_artifacts = []
    for payload_path in sorted(payload_root.glob("*.json")):
        if payload_path.name.endswith(".meta.json") or payload_path.name in declared_files:
            continue
        raw, parsed = read_json_bytes_safe(payload_path)
        relative = "pipeline_run_artifacts/qe_shaped_test_data/" + payload_path.name
        expected_hash = manifest.get(relative, {}).get("sha256")
        actual_hash = digest_bytes(raw)
        unreferenced_artifacts.append({"file": payload_path.name,
            "classification": "NON_TD_TRACEABILITY_ARTIFACT_CANDIDATE" if payload_path.name == "traceability.json" else "UNREFERENCED_JSON_REQUIRES_TRIAGE",
            "jsonParseStatus": "INVALID_JSON" if isinstance(parsed, Exception) else "VALID_JSON",
            "hashStatus": "MATCH" if expected_hash == actual_hash else "NO_MANIFEST" if not expected_hash else "MISMATCH",
            "sha256": actual_hash, "sizeBytes": len(raw)})

    file_feedback = {row["file"]: row for row in td_rows}
    for requirement_id, filenames in br_file_refs.items():
        for filename in filenames:
            row = file_feedback.get(filename)
            if row and row["element55Status"] == "MISSING":
                br_issue_counts[requirement_id]["MFI_MISSING_TD"] += 1
            if row and row["jsonParseStatus"] != "VALID_JSON":
                br_issue_counts[requirement_id]["JSON_INVALID_OR_MISSING"] += 1
            if row and row["rootStatus"] != "MESSAGE_FAMILY_ROOT_PRESENT":
                br_issue_counts[requirement_id]["ROOT_INVALID_OR_MISSING"] += 1
            if row and (row["metadataFieldCheck"]["missingFromPayload"] or row["metadataFieldCheck"]["valueMismatch"]):
                br_issue_counts[requirement_id]["METADATA_VALUE_DIVERGENCE"] += 1

    br_feedback_rows = []
    for br in br_rows:
        requirement_id = br["requirementId"]
        scenario_ids = sorted(br_scenarios.get(requirement_id, set()))
        case_ids = sorted(br_cases.get(requirement_id, set()))
        filenames = sorted(br_file_refs.get(requirement_id, set()))
        linked_payloads = [file_feedback[name] for name in filenames if name in file_feedback]
        statuses = []
        if not scenario_ids:
            statuses.append("NO_LINKED_SCENARIO")
        if not case_ids:
            statuses.append("NO_DIRECTLY_LINKED_CASE")
        if not filenames:
            statuses.append("NO_DECLARED_TD")
        mfi_missing = sum(row["element55Status"] == "MISSING" for row in linked_payloads)
        mfi_present = sum(row["element55Status"] == "PRESENT" for row in linked_payloads)
        invalid_json = sum(row["jsonParseStatus"] != "VALID_JSON" for row in linked_payloads)
        root_bad = sum(row["rootStatus"] != "MESSAGE_FAMILY_ROOT_PRESENT" for row in linked_payloads)
        if mfi_missing:
            statuses.append("ELEMENT_55_MISSING_IN_LINKED_TD")
        if invalid_json:
            statuses.append("INVALID_OR_MISSING_JSON")
        if root_bad:
            statuses.append("MESSAGE_ROOT_MISSING_OR_INVALID")
        if br_issue_counts[requirement_id]["METADATA_VALUE_DIVERGENCE"]:
            statuses.append("METADATA_TO_PAYLOAD_DIVERGENCE_REVIEW")
        if any(scenario_id not in scenario_cases for scenario_id in scenario_ids):
            statuses.append("SCENARIO_WITHOUT_CASE")
        if any(case_id in case_case_divergent for case_id in case_ids):
            statuses.append("TC_SCENARIO_BR_LINK_DIVERGENCE")
        if br.get("sourceLocatorStatus") not in {"SOURCE_PAGE_AND_ELEMENT_ANCHOR_FOUND", "SOURCE_PAGE_FOUND_ELEMENT_ANCHOR_NOT_LOCATED"}:
            statuses.append("SOURCE_LOCATOR_UNRESOLVED")
        if not statuses:
            statuses.append("STRUCTURAL_TRACE_PRESENT_REQUIRES_SEMANTIC_REVIEW")
        br_feedback_rows.append({"requirementId": requirement_id, "statement": br.get("statement"),
            "sourceRuleId": br.get("sourceRuleId"), "sourcePage": br.get("sourcePage"),
            "sourceLocatorStatus": br.get("sourceLocatorStatus"), "sourceMeaningStatus": br.get("meaningStatus"),
            "linkedScenarioCount": len(scenario_ids), "linkedScenarioIds": scenario_ids,
            "directCaseCount": len(case_ids), "directCaseIds": case_ids,
            "uniqueTdJsonCount": len(filenames),
            "tdReferenceCount": sum(file_feedback.get(name, {}).get("referenceCount", 0) for name in filenames),
            "validJsonTdCount": sum(row["jsonParseStatus"] == "VALID_JSON" for row in linked_payloads),
            "invalidOrMissingJsonTdCount": invalid_json, "element55PresentTdCount": mfi_present,
            "element55MissingTdCount": mfi_missing, "rootInvalidOrMissingTdCount": root_bad,
            "metadataDivergenceTdCount": br_issue_counts[requirement_id]["METADATA_VALUE_DIVERGENCE"],
            "feedbackCodes": statuses,
            "feedback": feedback_text(statuses, len(scenario_ids), len(case_ids), len(filenames), mfi_missing),
            "semanticApproval": "NOT_GRANTED_COMPLETE_INDEPENDENT_INTERPRETATION_REQUIRED"})

    scenario_feedback_rows = []
    for scenario in ts_rows:
        scenario_id = scenario["scenarioId"]
        linked_case_ids = sorted(scenario_cases.get(scenario_id, set()))
        linked_requirements = set(scenario.get("requirementIds") or [])
        case_requirement_union = set().union(*(scenario_case_requirements[scenario_id].values())) if linked_case_ids else set()
        unrepresented_requirements = sorted(linked_requirements - case_requirement_union)
        extra_case_requirements = sorted(case_requirement_union - linked_requirements)
        codes = []
        if limit is None and not linked_case_ids:
            codes.append("SCENARIO_WITHOUT_CASE")
        if unrepresented_requirements:
            codes.append("SCENARIO_BR_NOT_CARRIED_BY_ANY_CASE")
        if extra_case_requirements:
            codes.append("CASE_HAS_REQUIREMENTS_OUTSIDE_SCENARIO")
        if not codes:
            codes.append("STRUCTURAL_TRACE_PRESENT_REQUIRES_SEMANTIC_REVIEW")
        scenario_feedback_rows.append({"scenarioId": scenario_id,
            "requirementIds": sorted(linked_requirements), "linkedCaseCount": len(linked_case_ids),
            "linkedCaseIds": linked_case_ids, "unrepresentedRequirementIds": unrepresented_requirements,
            "extraCaseRequirementIds": extra_case_requirements, "feedbackCodes": codes,
            "feedback": "Structural TS/TC links are recorded; scenario intent and full BR behavior still require independent semantic review.",
            "semanticApproval": "NOT_GRANTED_BR_CONDITION_AND_OUTCOME_NOT_PROVEN"})

    write_csv(output / "br-feedback.csv", br_feedback_rows)
    write_jsonl(output / "br-feedback.jsonl", br_feedback_rows)
    write_csv(output / "scenario-feedback.csv", scenario_feedback_rows)
    write_jsonl(output / "scenario-feedback.jsonl", scenario_feedback_rows)
    write_jsonl(output / "td-json-audit.jsonl", td_rows)
    write_csv(output / "td-json-audit.csv", td_rows)
    write_jsonl(output / "unreferenced-json-artifacts.jsonl", unreferenced_artifacts)
    write_csv(output / "unreferenced-json-artifacts.csv", unreferenced_artifacts)
    write_jsonl(output / "td-json-issues.jsonl", issues)
    write_csv(output / "td-json-issues.csv", issues)
    write_rollup(output, summary, len(requirements), br_feedback_rows, case_rows_written, physical_legs,
        file_refs, td_rows, scenario_feedback_rows, unreferenced_artifacts, json_valid_count, mfi_present_count, mfi_missing_count, mfi_wrong_value_count,
        root_mismatch_count, metadata_missing_count, payload_missing_count, metadata_hash_mismatch_count,
        payload_hash_mismatch_count, metadata_join_mismatch_count, section1_present_count, e63_present_count,
        segment_count_mismatch_count, metadata_field_missing_count, metadata_field_value_mismatch_count,
        metadata_field_mapping_unresolved_count,
        placeholder_candidate_count, case_link_divergence_count, case_missing_assertion_count,
        case_missing_outcome_count, unrecognized_br_ids, issues, limit)
    print(json.dumps({"businessRequirements": len(br_feedback_rows), "testCasesAudited": case_rows_written,
        "physicalTdReferences": physical_legs, "uniqueTdJsonAudited": len(td_rows), "validJson": json_valid_count,
        "element55Present": mfi_present_count, "element55Missing": mfi_missing_count,
        "issues": len(issues), "completePopulation": limit is None, "output": str(output)}, indent=2))


def feedback_text(codes, scenarios, cases, tds, mfi_missing):
    feedback = f"Structural trace: {scenarios} TS, {cases} directly linked TC, {tds} unique TD JSON."
    if mfi_missing:
        feedback += f" Element 55 is absent in {mfi_missing} linked physical TD JSON file(s); review message-family applicability."
    if "NO_DIRECTLY_LINKED_CASE" in codes:
        feedback += " No TC directly links this BR."
    if "NO_LINKED_SCENARIO" in codes:
        feedback += " No TS links this BR."
    return feedback + " Complete BR meaning and equivalence remain unapproved."


def write_csv(path, rows):
    rows = list(rows)
    if not rows:
        path.write_text("", encoding="utf-8-sig")
        return
    with path.open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=list(rows[0].keys()), extrasaction="ignore")
        writer.writeheader()
        for row in rows:
            writer.writerow({key: json.dumps(value, ensure_ascii=True) if isinstance(value, (list, dict)) else value
                for key, value in row.items()})


def write_jsonl(path, rows):
    with path.open("w", encoding="utf-8") as stream:
        for row in rows:
            stream.write(json.dumps(row, ensure_ascii=True) + "\n")


def write_rollup(output, baseline, requirement_count, br_rows, cases, physical_legs, file_refs, td_rows, scenario_rows, unreferenced_artifacts,
    json_valid, mfi_present, mfi_missing, mfi_wrong, root_bad, metadata_missing, payload_missing,
    meta_hash_mismatch, payload_hash_mismatch, metadata_join_mismatch, section1_present, e63_present,
    segment_count_mismatch, field_missing, field_mismatch, field_mapping_unresolved, placeholder_files, link_divergence,
    missing_assertion, missing_outcome, unknown_br_ids, issues, limit):
    issue_counts = collections.Counter(row["kind"] for row in issues)
    feedback_counts = collections.Counter(code for row in br_rows for code in row["feedbackCodes"])
    scenario_feedback_counts = collections.Counter(code for row in scenario_rows for code in row["feedbackCodes"])
    complete = limit is None
    lines = ["# ATL105 AI Delivery Exhaustive Feedback Audit", "", "## Audit Boundary", "",
        f"AI delivery: October 5 Run1. Scope: {'complete frozen population' if complete else f'limited diagnostic sample ({limit} TCs)'}.",
        "This report covers structure, traceability, physical JSON, and metadata consistency. It does not approve BR meaning, infer business equivalence, or certify processor execution.", "",
        "## Population", "",
        f"- AI BRs / feedback rows: {requirement_count:,} / {len(br_rows):,}",
        f"- AI scenarios / feedback rows: {len(scenario_rows):,} / {len(scenario_rows):,}",
        f"- TCs audited: {cases:,} (frozen baseline: {baseline['testCases']:,})",
        f"- Declared physical TD references: {physical_legs:,} (frozen baseline: {baseline['physicalPayloadLegsAssessed']:,})",
        f"- Unique declared TD JSON paths audited: {len(td_rows):,}",
        f"- Unreferenced JSON artifacts inventoried separately: {len(unreferenced_artifacts):,}",
        f"- Unknown BR IDs encountered: {len(unknown_br_ids):,}",
        f"- TC/scenario BR-link divergence cases: {link_divergence:,}",
        f"- Missing explicit TC assertion claims: {missing_assertion:,}",
        f"- Missing expected-response/outcome claims: {missing_outcome:,}", "",
        "## TD JSON Findings", "",
        f"- Parseable existing physical TD JSON: {json_valid:,} / {max(0, len(td_rows) - payload_missing):,}",
        f"- Element 55 / Message Format Version Identifier present: {mfi_present:,}",
        f"- Element 55 missing from parseable TD JSON: {mfi_missing:,}",
        f"- Element 55 not assessed because payload was missing/unparseable: {len(td_rows) - json_valid:,}",
        f"- Element 55 value differs from ATL105: {mfi_wrong:,}",
        f"- Message-family root invalid or missing: {root_bad:,}",
        f"- Data Section 1 container present: {section1_present:,}",
        f"- Element 63 / Number of Segments present: {e63_present:,}",
        f"- Direct segment-count mismatch/unparseable: {segment_count_mismatch:,}",
        f"- TD payload missing: {payload_missing:,}; metadata sidecar missing: {metadata_missing:,}",
        f"- Payload/metadata hash mismatches: {payload_hash_mismatch:,} / {meta_hash_mismatch:,}",
        f"- Metadata-to-TC/TS join mismatches: {metadata_join_mismatch:,}",
        f"- Direct metadata field omissions: {field_missing:,}; value mismatches: {field_mismatch:,}; composite/path mappings needing review: {field_mapping_unresolved:,}",
        f"- Files with exact placeholder-value candidates: {placeholder_files:,}", "",
        "Element 55 recognizes the formal `Message Format Version Identifier`, the user wording `Message Format Indicator`, and Element 55 when represented inside Data Section 1. Metadata alone does not count as physical payload presence. ATL105 is used as the default value comparison; other values are reported for review.",
        "Metadata field paths are producer-specific. Fields explicitly marked `mergedInto` or `reshapedInto`, and Data Section 1 aliases, are evaluated as transformed/unresolved mappings rather than automatically counted as missing direct fields.", "",
        "## BR Feedback", "",
        "`br-feedback.csv` and `br-feedback.jsonl` contain one row per BR with linked TS/TC/TD counts, source locator, and file-derived findings.", "",
        "## Scenario Feedback", "",
        "`scenario-feedback.csv` and `scenario-feedback.jsonl` contain one row per AI TS, its directly linked cases, and BRs not carried by any linked case.", "",
        "Feedback code counts:"]
    lines.extend(f"- `{key}`: {value:,}" for key, value in sorted(feedback_counts.items()))
    lines.extend(["", "Scenario feedback code counts:"])
    lines.extend(f"- `{key}`: {value:,}" for key, value in sorted(scenario_feedback_counts.items()))
    lines.extend(["", "TD issue-register counts:"])
    lines.extend(f"- `{key}`: {value:,}" for key, value in sorted(issue_counts.items()))
    lines.extend(["", "## Producer Feedback", "", "AI Solution improvements:",
        "- Include Data Section 1 Element 55 in every applicable request JSON, use a source-grounded version value, and test missing/wrong-value cases.",
        "- Keep BR attribution atomic. Large inherited requirement lists can imply coverage when a case value/assertion does not exercise that BR.",
        "- Supply explicit BR-linked assertions and expected outcomes; resolve provisional, unavailable, and unresolved metadata values.",
        "- Keep bounded field checks separate from complete-message validity and host behavior claims.", "",
        "Independent Test Solution improvements:",
        "- Extend the existing Element 55 isolated package checks into integrated request serialization for applicable message families, including Element 63 and separators.",
        "- Pair positive controls with targeted missing/wrong-field cases; unsupported scope should remain NOT_ASSESSED, not PASS.",
        "- Complete semantic review of the independent 601-rule baseline before making semantic coverage claims.", "",
        "## Output Files", "",
        "- `br-feedback.csv` / `br-feedback.jsonl`: one record for each AI BR.",
        "- `scenario-feedback.csv` / `scenario-feedback.jsonl`: one record per AI TS and linked TC coverage.",
        "- `case-feedback.jsonl`: one record for each TC and its declared TD legs.",
        "- `td-json-audit.csv` / `td-json-audit.jsonl`: one record per unique physical TD JSON.",
        "- `td-json-issues.csv` / `td-json-issues.jsonl`: every detected file-level issue; join by `file` to the TD audit row for complete BR links and payload paths.", "",
        "- `unreferenced-json-artifacts.csv` / `unreferenced-json-artifacts.jsonl`: JSON files in the AI data directory not declared by any TC; these are not treated as TD fixtures.", "",
        "Semantic approval: not granted. Execution certification: false.", ""])
    (output / "AI-DELIVERY-FEEDBACK-REPORT.md").write_text("\n".join(lines), encoding="utf-8")
    audit_summary = {"auditScope": "FULL" if complete else "LIMITED_DIAGNOSTIC", "businessRequirements": requirement_count,
        "brFeedbackRows": len(br_rows), "testCasesAudited": cases, "physicalTdReferencesAudited": physical_legs,
        "uniqueDeclaredTdJsonPaths": len(td_rows), "existingTdJsonFiles": len(td_rows) - payload_missing,
        "unreferencedJsonArtifacts": len(unreferenced_artifacts), "validJson": json_valid, "element55Present": mfi_present,
        "element55Missing": mfi_missing, "element55WrongValue": mfi_wrong, "rootMismatch": root_bad,
        "section1Present": section1_present, "element63Present": e63_present,
        "segmentCountMismatch": segment_count_mismatch, "payloadMissing": payload_missing,
        "metadataMissing": metadata_missing, "payloadHashMismatch": payload_hash_mismatch,
        "metadataHashMismatch": meta_hash_mismatch, "metadataJoinMismatch": metadata_join_mismatch,
        "metadataFieldMissing": field_missing, "metadataFieldMismatch": field_mismatch,
        "metadataFieldMappingUnresolved": field_mapping_unresolved,
        "placeholderCandidateFiles": placeholder_files, "tcScenarioLinkDivergence": link_divergence,
        "missingTcAssertion": missing_assertion, "missingExpectedOutcome": missing_outcome,
        "unknownRequirementIds": sorted(unknown_br_ids), "brFeedbackCodes": dict(feedback_counts),
        "tdIssueCounts": dict(issue_counts), "semanticApproval": False, "executionCertified": False}
    (output / "audit-summary.json").write_text(json.dumps(audit_summary, indent=2, ensure_ascii=True), encoding="utf-8")


def main():
    parser = argparse.ArgumentParser(description="Exhaustive AI BR/TS/TC/TD and physical TD JSON audit")
    parser.add_argument("--review", type=Path, default=REVIEW_DEFAULT)
    parser.add_argument("--assessment", type=Path, default=ASSESSMENT_DEFAULT)
    parser.add_argument("--output", type=Path, default=OUTPUT_DEFAULT)
    parser.add_argument("--limit", type=int, default=None, help="Diagnostic TC limit; output is marked incomplete")
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
    else:
        audit(args.review, args.assessment, args.output, args.limit)


if __name__ == "__main__":
    main()