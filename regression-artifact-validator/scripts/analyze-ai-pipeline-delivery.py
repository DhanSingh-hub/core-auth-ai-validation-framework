import argparse
import collections
import csv
import hashlib
import json
import os
import zipfile
from pathlib import Path

import ijson


def native(path):
    path = Path(os.path.normpath(str(path)))
    return Path("\\\\?\\" + str(path.resolve())) if os.name == "nt" and not str(path).startswith("\\\\?\\") else path


def read(path):
    return json.loads(path.read_text(encoding="utf-8-sig"))


def digest(path):
    with path.open("rb") as stream:
        return hashlib.file_digest(stream, "sha256").hexdigest()


def items(path, array):
    with path.open("rb") as stream:
        yield from ijson.items(stream, array + ".item", use_float=True)


def root_arrays(path):
    arrays = []
    with path.open("rb") as stream:
        for prefix, event, value in ijson.parse(stream, use_float=True):
            if event == "start_array" and prefix and "." not in prefix:
                arrays.append(prefix)
    return arrays


def links(record):
    values = record.get("requirement_ids") or []
    if isinstance(values, str):
        values = [values]
    return set(values) | ({record["requirement_id"]} if record.get("requirement_id") else set())


def walk(node):
    if isinstance(node, dict):
        yield node
        for value in node.values():
            yield from walk(value)
    elif isinstance(node, list):
        for value in node:
            yield from walk(value)


def expectation_evidence(expected):
    tiers = collections.Counter()
    assertion_count = 0
    response_templates = 0
    for node in walk(expected):
        if isinstance(node.get("tier"), str):
            tiers[node["tier"]] += 1
            if node["tier"] == "ASSERT" and (node.get("assertions") or node.get("value") is not None):
                assertion_count += 1
        if node.get("response_transaction_type") is not None and node.get("available") is True:
            response_templates += 1
    return {"tiers": dict(tiers), "assertEvidence": assertion_count, "responseTemplatesAvailable": response_templates}


def self_test():
    assert links({"requirement_ids": "BR-1", "requirement_id": "BR-2"}) == {"BR-1", "BR-2"}
    assert expectation_evidence({"rule": {"tier": "INFER", "assertions": ["Well formed"]}})["assertEvidence"] == 0
    assert expectation_evidence({"rule": {"tier": "ASSERT", "assertions": ["Length 6"]}})["assertEvidence"] == 1
    assert expectation_evidence({"rule": {"tier": "ASSERT", "value": None}})["assertEvidence"] == 0
    print("PASS: BR edge union, nonoverlapping evidence meanings, and INFER/empty-ASSERT refusal")


def csv_write(path, rows, fields=None):
    with path.open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=fields or list(rows[0]) if rows else fields or ["record"])
        writer.writeheader()
        for row in rows:
            writer.writerow({key: json.dumps(value, ensure_ascii=True) if isinstance(value, (dict, list, set)) else value for key, value in row.items()})


def analyze(archive, output, verification, previous):
    archive = native(archive)
    output = native(output)
    verification = native(verification)
    if not read(verification / "intake-verification.json").get("complete"):
        raise ValueError("Complete source/archive SHA-256 verification is required")
    if output.exists():
        raise ValueError("Analysis output must be new; refusing overwrite")
    pipeline = archive / "src/pipeline"
    reporting = pipeline / "reporting/output"
    candidates = pipeline / "test_generation/candidates"
    requirement_file = pipeline / "step5_requirements/approved/requirement_catalog.json"
    scenario_file = pipeline / "scenarios/approved/approved_scenarios.json"
    case_file = candidates / "test_case_candidates.json"
    approved_file = pipeline / "test_generation/approved/test_case_catalog.json"
    matrix_file = reporting / "traceability_matrix_full.json"
    report_file = reporting / "run_report.json"
    requirements = list(items(requirement_file, "requirements"))
    scenarios = list(items(scenario_file, "scenarios"))
    if not requirements or not scenarios:
        raise ValueError("Unexpected primary BR/TS array shape")
    br_index = {row["id"]: row for row in requirements}
    br_ids_set = set(br_index)
    ts_index = {row["id"]: row for row in scenarios}
    case_index = {}
    counters = collections.Counter()
    types = collections.Counter()
    transaction_labels = collections.Counter()
    tiers = collections.Counter()
    tier_case_ids = collections.defaultdict(set)
    verification_statuses = collections.Counter()
    method_counts = collections.Counter()
    case_flags = collections.Counter()
    scenario_cases = collections.Counter()
    br_cases = collections.Counter()
    approved_ids = {row["id"] for row in items(approved_file, "test_cases")}
    declarations = collections.defaultdict(set)
    case_rows = []
    problems = []
    for case in items(case_file, "test_cases"):
        case_id = case["id"]
        if case_id in case_index:
            raise ValueError("Duplicate TC ID: " + case_id)
        scenario_id = case.get("scenario_id")
        br_ids = links(case)
        scenario_cases[scenario_id] += 1
        br_cases.update(br_ids)
        types[case.get("scenario_type", "NOT_DECLARED")] += 1
        transaction_labels[case.get("transaction_type", "NOT_DECLARED")] += 1
        verification_statuses[(case.get("verification") or {}).get("status", "NOT_DECLARED")] += 1
        counters["emptyExpectedResponse"] += not bool(case.get("expected_response"))
        expectation = expectation_evidence(case.get("expected_response"))
        tiers.update(expectation["tiers"])
        for tier in expectation["tiers"]:
            tier_case_ids[tier].add(case_id)
        counters["casesWithAssertEvidence"] += expectation["assertEvidence"] > 0
        counters["casesWithAvailableResponseTemplate"] += expectation["responseTemplatesAvailable"] > 0
        for node in walk(case.get("request", {})):
            if "method" in node:
                method_counts[str(node["method"])] += 1
        case_flags.update(set(str(value).split(":", 1)[0] for value in case.get("flags", [])))
        counters["casesWithoutBrLinks"] += not br_ids
        counters["unknownScenario"] += scenario_id not in ts_index
        unknown_brs = sorted(br_ids - br_ids_set)
        counters["unknownBrReferences"] += len(unknown_brs)
        leg_files = case.get("test_data_leg_files") or []
        if isinstance(leg_files, dict):
            leg_files = list(leg_files.values())
        requested = ([case["test_data_file"]] if case.get("test_data_file") else []) + leg_files
        if not requested and not case.get("is_flow"):
            requested = ["qe_shaped_test_data/" + case_id + ".json"]
        for requested_file in requested:
            if isinstance(requested_file, str):
                name = requested_file.replace("\\", "/").rsplit("/", 1)[-1]
                declarations[name].add(case_id)
        row = {"caseId": case_id, "scenarioId": scenario_id, "intent": case.get("scenario_type"),
               "transactionLabel": case.get("transaction_type"), "requirementIds": sorted(br_ids),
               "approvedInProducerCatalog": case_id in approved_ids,
               "verificationStatus": (case.get("verification") or {}).get("status"),
               "expectedResponseTiers": sorted(expectation["tiers"]), "hasNonemptyExpectedResponse": bool(case.get("expected_response")),
               "hasSpecificAssertEvidence": expectation["assertEvidence"] > 0,
               "responseTemplateAvailable": expectation["responseTemplatesAvailable"] > 0,
               "declaredDataFiles": sorted({str(value).replace("\\", "/").rsplit("/", 1)[-1] for value in requested}),
               "isFlow": bool(case.get("is_flow")), "producerFlagKinds": sorted(set(str(value).split(":", 1)[0] for value in case.get("flags", []))),
               "producerFingerprint": case.get("fingerprint"),
               "fullSemanticConfirmed": False, "executionCertified": False}
        case_rows.append(row)
        case_index[case_id] = row
        if unknown_brs or scenario_id not in ts_index:
            problems.append({"context": "CASE_GRAPH", "id": case_id, "issue": "DANGLING_REFERENCE", "evidence": {"unknownBrs": unknown_brs, "scenario": scenario_id}})
    if approved_ids - set(case_index):
        problems.append({"context": "APPROVAL", "id": "APPROVED_CATALOG", "issue": "APPROVED_CASE_NOT_IN_CANDIDATE_CATALOG", "evidence": sorted(approved_ids - set(case_index))})
    physical_rows = []
    physical_cases = set()
    provenance_counts = collections.Counter()
    json_errors = []
    value_agreement = collections.Counter()
    value_disagreement_rows = []
    replicas = collections.defaultdict(dict)
    for location in [candidates / "qe_shaped_test_data", reporting / "qe_shaped_test_data"]:
        for file in sorted(location.glob("*.json")):
            if file.name == "traceability.json" or file.name.endswith(".meta.json"):
                continue
            row = {"file": file.name, "location": file.relative_to(archive).as_posix(), "bytes": file.stat().st_size,
                   "declaredByCases": sorted(declarations.get(file.name, [])), "payloadSha256": digest(file), "metadataPresent": False,
                   "parsed": False, "metadataCaseId": None, "metadataScenarioId": None,
                   "provisionalFields": 0, "unavailableFields": 0, "unresolvedSegments": 0, "joinValid": False,
                   "payloadRoots": [], "metadataSha256": None, "metadataFields": 0, "emptyMessageBody": False}
            try:
                payload = read(file)
                if not isinstance(payload, dict):
                    raise ValueError("Payload must be object")
                row["parsed"] = True
                row["payloadRoots"] = sorted(payload)
                row["emptyMessageBody"] = not payload or all(isinstance(body, dict) and not body for body in payload.values())
                metadata_file = file.with_name(file.stem + ".meta.json")
                row["metadataPresent"] = metadata_file.is_file()
                if metadata_file.is_file():
                    metadata = read(metadata_file)
                    row["metadataSha256"] = digest(metadata_file)
                    row["metadataCaseId"] = metadata.get("testCaseId")
                    row["metadataScenarioId"] = metadata.get("scenarioId")
                    mapped = case_index.get(metadata.get("testCaseId"))
                    row["joinValid"] = bool(mapped and mapped["scenarioId"] == metadata.get("scenarioId"))
                    if row["joinValid"]:
                        physical_cases.add(metadata["testCaseId"])
                    fields = metadata.get("fields", [])
                    row["metadataFields"] = len(fields)
                    row["provisionalFields"] = sum(field.get("provisional") is True for field in fields)
                    row["unavailableFields"] = len(metadata.get("unavailableFields", []))
                    row["unresolvedSegments"] = len(metadata.get("dataSections", {}).get("unresolved", {}).get("segments", []))
                    if location == candidates / "qe_shaped_test_data":
                        provenance_counts.update(str(field.get("dataSource", "NOT_DECLARED")) for field in fields)
                        body = payload.get(metadata.get("messageFamily"))
                        for field in fields:
                            container = body.get(field.get("segmentFriendlyName")) if isinstance(body, dict) else None
                            key = field.get("element")
                            if not isinstance(container, dict) or key not in container:
                                value_agreement["NO_DIRECT_PHYSICAL_PATH_UNASSESSED"] += 1
                            elif isinstance(container[key], (dict, list)):
                                value_agreement["COMPOSITE_VALUE_UNASSESSED"] += 1
                            elif container[key] == field.get("value"):
                                value_agreement["DIRECT_VALUE_AGREEMENT"] += 1
                            else:
                                value_agreement["DIRECT_VALUE_DISAGREEMENT"] += 1
                                value_disagreement_rows.append({"file": file.name, "caseId": metadata.get("testCaseId"),
                                    "segment": field.get("segment"), "element": key, "specElementName": field.get("specElementName"),
                                    "scope": "Scalar mismatch only; values intentionally withheld"})
            except Exception as exception:
                json_errors.append({"file": row["location"], "error": str(exception)})
            physical_rows.append(row)
            replicas[file.name][str(location.relative_to(archive))] = row["payloadSha256"]
    replica_conflicts = [name for name, values in replicas.items() if len(set(values.values())) > 1]
    declared_missing = sorted(set(declarations) - set(replicas))
    unreferenced_files = sorted(set(replicas) - set(declarations))
    missing_case_ids = sorted(set(case_index) - physical_cases)
    for row in case_rows:
        row["physicalOutputPresent"] = row["caseId"] in physical_cases
        row["structuralStatus"] = "BR_TS_TC_TD_LINKED" if row["physicalOutputPresent"] and row["requirementIds"] else "TC_WITHOUT_VERIFIED_TD" if not row["physicalOutputPresent"] else "NO_BR_ATTRIBUTION"
    audits = {}
    for file in sorted((archive / "logs/run_current").glob("*audit_report.json")):
        audits[file.name] = read(file)
    producer = read(report_file)
    if audits.get("test_case_audit_report.json", {}).get("coverage", {}).get("total_test_cases") != len(case_index):
        problems.append({"context": "PRODUCER_REPORT", "id": "test_case_audit_report.json", "issue": "AUDIT_COUNT_DISAGREES_WITH_ACTUAL_TC_COUNT", "evidence": {"claimed": audits.get("test_case_audit_report.json", {}).get("coverage", {}).get("total_test_cases"), "recounted": len(case_index)}})
    producer_assert = producer.get("test_cases", {}).get("expected_response_tiers", {}).get("ASSERT")
    audit_assert = audits.get("test_data_audit_report.json", {}).get("coverage", {}).get("tier_counts", {}).get("ASSERT")
    if producer_assert != audit_assert:
        problems.append({"context": "PRODUCER_REPORT", "id": "ASSERT_TIER", "issue": "CONFLICTING_PRODUCER_SUMMARIES", "evidence": {"runReport": producer_assert, "tdAudit": audit_assert, "actualCaseCount": len(tier_case_ids["ASSERT"]), "actualAssertionNodes": tiers["ASSERT"]}})
    approval_audit = []
    audit_file = pipeline / "test_generation/audit_log.jsonl"
    with audit_file.open(encoding="utf-8-sig") as stream:
        for line_number, line in enumerate(stream, 1):
            if line.strip():
                try:
                    approval_audit.append(json.loads(line))
                except Exception as exception:
                    json_errors.append({"file": audit_file.relative_to(archive).as_posix(), "line": line_number, "error": str(exception)})
    reviewers = collections.Counter(str(row.get("reviewed_by", row.get("reviewer", "NOT_DECLARED"))) for row in approval_audit)
    matrix_arrays = root_arrays(matrix_file)
    matrix_data = {"file": matrix_file.relative_to(archive).as_posix(), "rootArrays": matrix_arrays, "sha256": digest(matrix_file)}
    matrix_summaries = {}
    with matrix_file.open("rb") as stream:
        matrix_summaries = next(ijson.items(stream, "summary", use_float=True), {})
    matrix_data["producerSummary"] = matrix_summaries
    matrix_counts = {}
    matrix_graph = collections.Counter()
    matrix_linked_brs = set()
    matrix_proposed_only_brs = set()
    matrix_proposed_rows = []
    matrix_missing_td_rows = []
    matrix_unique_cases_with_path = set()
    for array in matrix_arrays:
        if array == "caveats":
            continue
        count = 0
        first_keys = None
        statuses = collections.Counter()
        for record in items(matrix_file, array):
            count += 1
            if isinstance(record, dict):
                if first_keys is None:
                    first_keys = list(record)
                if record.get("trace_status") or record.get("status"):
                    statuses[record.get("trace_status", record.get("status"))] += 1
                if array == "flat_rows":
                    br = record.get("requirement_id")
                    scenario_id = record.get("scenario_id")
                    case_id = record.get("test_case_id")
                    declared = record.get("test_data_file")
                    case = case_index.get(case_id)
                    if br not in br_index:
                        matrix_graph["unknownRequirementRows"] += 1
                    if scenario_id and scenario_id not in ts_index:
                        matrix_graph["unknownScenarioRows"] += 1
                    if case_id:
                        matrix_graph["rowsWithTestCase"] += 1
                        matrix_linked_brs.add(br)
                        if case is None:
                            matrix_graph["unknownTestCaseRows"] += 1
                        elif case["scenarioId"] != scenario_id:
                            matrix_graph["testCaseScenarioDisagreementRows"] += 1
                        elif br not in case["requirementIds"]:
                            matrix_graph["matrixBrEdgeNotDirectInCase"] += 1
                            matrix_proposed_only_brs.add(br)
                            matrix_proposed_rows.append({"requirementId": br, "scenarioId": scenario_id, "caseId": case_id,
                                "traceabilityNote": record.get("traceability_note"), "stage": "PRODUCER_PROPOSED_RELINK_NOT_SEMANTICALLY_CONFIRMED"})
                    if declared:
                        name = str(declared).replace("\\", "/").rsplit("/", 1)[-1]
                        matrix_graph["rowsDeclaringTd"] += 1
                        matrix_unique_cases_with_path.add(case_id)
                        if name not in replicas:
                            matrix_graph["rowsDeclaringMissingTd"] += 1
                            matrix_missing_td_rows.append({"requirementId": br, "scenarioId": scenario_id, "caseId": case_id,
                                "file": name, "producerTraceStatus": record.get("trace_status")})
                    elif record.get("trace_status") == "FULLY_TRACED":
                        matrix_graph["fullyTracedRowsWithoutTdDeclaration"] += 1
        matrix_counts[array] = {"records": count, "firstRecordKeys": first_keys, "statuses": dict(statuses)}
    matrix_data["recordCounts"] = matrix_counts
    matrix_data["independentGraphAudit"] = {**matrix_graph, "brsLinkedByMatrixRows": len(matrix_linked_brs),
        "uniqueCasesDeclaringDataPath": len(matrix_unique_cases_with_path),
        "matrixOnlyLinkedBrIds": sorted(matrix_linked_brs - {br for br in br_index if br_cases[br]}),
        "proposedRelinkUniqueCases": len({row["caseId"] for row in matrix_proposed_rows}),
        "matrixMissingDataUniqueCases": len({row["caseId"] for row in matrix_missing_td_rows})}
    old = read(previous / "complete-handoff-analysis.json") if previous else None
    comparison = []
    identifier_drift = {}
    if old:
        for metric, old_count, new_count in [("BR inventory", old["businessRequirements"], len(requirements)),
            ("TS inventory", old["scenarios"], len(scenarios)), ("TC candidates", old["caseReconciliation"]["records"], len(case_index)),
            ("Scenarios without TC", old["caseReconciliation"]["scenariosWithoutCase"], sum(not scenario_cases[key] for key in ts_index)),
            ("Cases with empty expected response", old["caseQuality"]["emptyExpectedResponse"], counters["emptyExpectedResponse"]),
            ("Logical cases with physical outputs", old["producerPayloadTraceability"]["totalTestCases"], len(physical_cases))]:
            comparison.append({"metric": metric, "previous": old_count, "latest": new_count, "delta": new_count - old_count})
        old_archive = Path(old["archive"])
        old_requirement_file = old_archive / "scenerio_req_5_oct/scenerio_req_5_oct/step5_requirements/approved/requirement_catalog.json"
        old_requirements = {row["id"]: row for row in items(old_requirement_file, "requirements")}
        shared_brs = set(br_index) & set(old_requirements)
        changed_brs = [br for br in shared_brs if br_index[br].get("statement") != old_requirements[br].get("statement")]
        shared_cases = changed_case_fingerprints = 0
        for old_case in items(old_archive / "pipeline_run_artifacts/test_case_candidates.json", "test_cases"):
            if old_case["id"] in case_index:
                shared_cases += 1
                changed_case_fingerprints += old_case.get("fingerprint") != case_index[old_case["id"]]["producerFingerprint"]
        identifier_drift = {"sameLocalBrIds": len(shared_brs), "sameLocalBrIdsWithChangedStatements": len(changed_brs),
                            "changedBrIdSamples": sorted(changed_brs)[:50], "sameLocalTcIds": shared_cases,
                            "sameLocalTcIdsWithChangedProducerFingerprints": changed_case_fingerprints,
                            "policy": "Shared IDs are not cross-run identity; fingerprint/statement changes require remapping"}
    summary = {"delivery": "ATL105-2026-10-07-Run4", "scope": "Exhaustive producer graph/file/metadata/tier/report reconciliation; not semantic approval or host certification",
        "intakeVerification": read(verification / "intake-verification.json"), "BR": len(requirements), "TS": len(scenarios), "TC": len(case_index), "producerApprovedTC": len(approved_ids),
        "brDuplicateIds": len(requirements) - len(br_index), "scenarioDuplicateIds": len(scenarios) - len(ts_index), "graphCounters": dict(counters),
        "brsWithDirectTc": sum(bool(br_cases[key]) for key in br_index), "scenariosWithTc": sum(bool(scenario_cases[key]) for key in ts_index),
        "scenariosWithoutTc": sum(not scenario_cases[key] for key in ts_index), "caseTypes": dict(types), "transactionLabels": dict(transaction_labels),
        "expectedTierNodeCounts": dict(tiers), "expectedTierCaseCounts": {tier: len(ids) for tier, ids in tier_case_ids.items()},
        "verificationStatuses": dict(verification_statuses), "fillMethodOccurrences": dict(method_counts), "producerFlagCaseCounts": dict(case_flags),
        "physicalRequestFilesAcrossLocations": len(physical_rows), "physicalUniqueBasenames": len(replicas), "logicalCasesWithVerifiedPhysicalMetadata": len(physical_cases),
        "emptyMessageBodyFilesAcrossLocations": sum(row["emptyMessageBody"] for row in physical_rows),
        "zeroMetadataFieldFilesAcrossLocations": sum(row["metadataPresent"] and row["metadataFields"] == 0 for row in physical_rows),
        "physicalDataSourceCountsCandidateLocation": dict(provenance_counts), "jsonErrorCount": len(json_errors),
        "payloadMetadataValueAgreement": dict(value_agreement), "valueDisagreementCount": len(value_disagreement_rows),
        "replicaConflictCount": len(replica_conflicts), "declaredMissingFileCount": len(declared_missing), "unreferencedPhysicalFileCount": len(unreferenced_files),
        "casesWithoutVerifiedPhysicalMetadata": len(missing_case_ids), "approvalAuditEntries": len(approval_audit), "approvalReviewerCounts": dict(reviewers),
        "matrix": matrix_data, "producerReport": producer, "auditReports": audits, "discrepancyCount": len(problems),
        "comparisonWithOctober5": comparison, "crossRunIdentifierDrift": identifier_drift,
        "semanticCoverage": "NOT_CALCULABLE_UNQUALIFIED_CROSS_PRODUCER_MAPPING", "executionCertified": False,
        "inputSha256": {file.relative_to(archive).as_posix(): digest(file) for file in [requirement_file, scenario_file, case_file, approved_file, matrix_file, report_file, audit_file]}}
    output.mkdir(parents=True)
    for name, value in [("LATEST-RUN-ANALYSIS.json", summary), ("case-register.json", case_rows), ("physical-file-register.json", physical_rows),
        ("report-discrepancies.json", problems), ("json-errors.json", json_errors), ("missing-physical-case-ids.json", missing_case_ids),
        ("declared-missing-data-files.json", declared_missing), ("unreferenced-data-files.json", unreferenced_files), ("replica-conflicts.json", replica_conflicts)]:
        (output / name).write_text(json.dumps(value, indent=2, ensure_ascii=True), encoding="utf-8")
    csv_write(output / "case-register.csv", case_rows)
    csv_write(output / "matrix-proposed-relinks.csv", matrix_proposed_rows,
              ["requirementId", "scenarioId", "caseId", "traceabilityNote", "stage"])
    csv_write(output / "matrix-missing-td-rows.csv", matrix_missing_td_rows,
              ["requirementId", "scenarioId", "caseId", "file", "producerTraceStatus"])
    csv_write(output / "payload-metadata-disagreements.csv", value_disagreement_rows,
              ["file", "caseId", "segment", "element", "specElementName", "scope"])
    csv_write(output / "physical-file-register.csv", physical_rows)
    csv_write(output / "scenario-without-case.csv", [{"scenarioId": key, "type": row.get("type"), "requirementIds": sorted(links(row)), "name": row.get("name")} for key, row in ts_index.items() if not scenario_cases[key]])
    print(json.dumps({key: summary[key] for key in ["BR", "TS", "TC", "producerApprovedTC", "brsWithDirectTc", "scenariosWithTc", "scenariosWithoutTc", "physicalRequestFilesAcrossLocations", "physicalUniqueBasenames", "logicalCasesWithVerifiedPhysicalMetadata", "jsonErrorCount", "replicaConflictCount", "declaredMissingFileCount", "unreferencedPhysicalFileCount", "expectedTierNodeCounts", "expectedTierCaseCounts", "approvalReviewerCounts", "discrepancyCount"]}, indent=2))


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("archive", nargs="?", type=Path)
    parser.add_argument("new_output", nargs="?", type=Path)
    parser.add_argument("--verification", type=Path)
    parser.add_argument("--previous", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
    elif args.archive and args.new_output and args.verification:
        analyze(args.archive, args.new_output, args.verification, args.previous)
    else:
        parser.error("Archive, new output and --verification are required")


if __name__ == "__main__":
    main()