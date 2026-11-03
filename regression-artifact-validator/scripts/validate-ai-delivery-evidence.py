"""Reconcile AI matrices and account for every TD without inventing semantic passes."""

import argparse
import collections
import json
import runpy
import tempfile
from pathlib import Path

import ijson


AUDIT = runpy.run_path(str(Path(__file__).with_name("audit-ai-delivery-coverage.py")))
QE = runpy.run_path(str(Path(__file__).with_name("apply-qe-feedback-rules.py")))
read_json = AUDIT["read_json"]
file_hash = AUDIT["file_hash"]
canonical = AUDIT["canonical"]
references = AUDIT["references"]
safe_path = AUDIT["safe_path"]


def rows(path):
    with path.open(encoding="utf-8") as stream:
        for line in stream:
            if line.strip():
                yield json.loads(line, object_pairs_hook=AUDIT["strict_object"])


def items(path, prefix):
    with path.open("rb") as stream:
        yield from ijson.items(stream, prefix + ".item", use_float=True, map_type=AUDIT["UniqueKeys"])


def declaration_paths(case):
    result = []
    if case.get("test_data_file"):
        result.append(case["test_data_file"])
    legs = case.get("test_data_leg_files") or []
    if isinstance(legs, dict):
        legs = list(legs.values())
    if not isinstance(legs, list) or any(not isinstance(value, str) for value in legs):
        raise ValueError("Unsupported matrix/catalog TD declaration")
    result.extend(legs)
    if any(not isinstance(value, str) or not value.strip() for value in result):
        raise ValueError("TD declaration must be a non-empty string")
    return {value.replace("\\", "/") for value in result}


def catalog_graph(root, catalogs):
    identities = {kind: set() for kind in ("BR", "TS", "TC")}
    edges = {kind: set() for kind in ("BR_TS", "TS_TC", "BR_TC", "TC_TD")}
    cases = collections.defaultdict(list)
    for relative, catalog in catalogs.items():
        kind = catalog["kind"]
        array = AUDIT["CATALOGS"][Path(relative).name][1]
        for row in items(safe_path(root, root / relative), array):
            identifier = row.get("id")
            if not isinstance(identifier, str) or not identifier.strip():
                continue  # The audit retains and blocks these occurrences separately.
            identities[kind].add(identifier)
            if kind == "TS":
                edges["BR_TS"].update((br, identifier) for br in references(row, "requirement_ids", "requirement_id"))
            elif kind == "TC":
                cases[identifier].append(QE["extract_tc"](row))
                if row.get("scenario_id"):
                    edges["TS_TC"].add((row["scenario_id"], identifier))
                edges["BR_TC"].update((br, identifier) for br in references(row, "requirement_ids", "requirement_id"))
                edges["TC_TD"].update((identifier, path) for path in declaration_paths(row))
    return (identities, edges), cases


def matrix_graph(path, flat=False):
    identities = {kind: set() for kind in ("BR", "TS", "TC")}
    edges = {kind: set() for kind in ("BR_TS", "TS_TC", "BR_TC", "TC_TD")}
    prefix = "flat_rows" if flat else "requirements"
    seen = False

    def add(br, scenario, case=None):
        if br:
            identities["BR"].add(br)
        sid = scenario.get("scenario_id")
        if sid:
            identities["TS"].add(sid)
            if br:
                edges["BR_TS"].add((br, sid))
        if case is None:
            return
        cid = case.get("test_case_id")
        if cid:
            identities["TC"].add(cid)
            if sid:
                edges["TS_TC"].add((sid, cid))
            if br:
                edges["BR_TC"].add((br, cid))
            edges["TC_TD"].update((cid, declaration) for declaration in declaration_paths(case))
            nested_data = case.get("test_data")
            if nested_data is not None:
                if not isinstance(nested_data, dict):
                    raise ValueError("Unsupported nested matrix test_data shape")
                edges["TC_TD"].update((cid, declaration) for declaration in declaration_paths(nested_data))

    for row in items(path, prefix):
        seen = True
        if flat:
            add(row.get("requirement_id"), row, row)
        else:
            br = row.get("requirement_id")
            if br:
                identities["BR"].add(br)
            for scenario in row.get("scenarios") or []:
                add(br, scenario)
                for case in scenario.get("test_cases") or []:
                    add(br, scenario, case)
    if not seen:
        raise ValueError("Matrix has no supported rows at " + prefix)
    return identities, edges


def graph_discrepancies(left, right, representation):
    left_ids, left_edges = left
    right_ids, right_edges = right
    for kind in left_ids:
        for identifier in sorted(left_ids[kind] - right_ids[kind]):
            yield {"representation": representation, "kind": kind, "status": "CATALOG_ONLY", "id": identifier}
        for identifier in sorted(right_ids[kind] - left_ids[kind]):
            yield {"representation": representation, "kind": kind, "status": "MATRIX_ONLY", "id": identifier}
    for kind in left_edges:
        for source, target in sorted(left_edges[kind] - right_edges[kind]):
            yield {"representation": representation, "kind": kind, "status": "CATALOG_ONLY",
                   "source": source, "target": target}
        for source, target in sorted(right_edges[kind] - left_edges[kind]):
            yield {"representation": representation, "kind": kind, "status": "MATRIX_ONLY",
                   "source": source, "target": target}


def load_element_evidence(path, files):
    if path is None:
        return {}
    report = read_json(path)
    if report.get("artifact") != "ATL105-AI-RUN-ELEMENT-VALIDATION" or report.get("chapter13Semantics") is not True:
        raise ValueError("Require an existing Chapter 13 semantic intake report, not a field-only report")
    evidence = {}
    for execution in report.get("executions", []):
        payload = execution.get("payloadFile", "").replace("\\", "/")
        metadata = execution.get("metadataFile", "").replace("\\", "/")
        if payload not in files or metadata not in files:
            raise ValueError("Element report paths must be relative to this exact delivery root")
        if (execution.get("payloadSha256") != files[payload]["sha256"]
                or execution.get("metadataSha256") != files[metadata]["sha256"]):
            raise ValueError("Stale element evidence: " + payload)
        if execution.get("status") not in {"CHECKS_PASSED", "INVALID", "REVIEW_REQUIRED"}:
            raise ValueError("Unsupported element execution status: " + payload)
        if files[payload]["kind"] != "PAYLOAD" or files[metadata]["kind"] != "METADATA":
            raise ValueError("Element evidence must bind payload and metadata file kinds")
        if metadata != payload[:-len(".json")] + ".meta.json":
            raise ValueError("Element evidence does not bind the payload's own metadata")
        if execution.get("testCaseId") != files[metadata].get("testCaseId"):
            raise ValueError("Element evidence test-case identity mismatch")
        if payload in evidence:
            raise ValueError("Duplicate element execution for payload: " + payload)
        evidence[payload] = execution
    return evidence


def td_disposition(info, metadata, claims, execution):
    defects = []
    if info.get("jsonStatus") != "OBJECT":
        defects.append("PAYLOAD_INVALID_OR_EMPTY")
    if metadata is None or metadata.get("jsonStatus") != "OBJECT":
        defects.append("SIDECAR_MISSING_OR_INVALID")
    if not claims:
        defects.append("UNREFERENCED_PHYSICAL_TD")
    elif metadata:
        if not any(claim["id"] == metadata.get("testCaseId")
                   and claim.get("scenarioId") == metadata.get("scenarioId") for claim in claims):
            defects.append("SIDECAR_CATALOG_IDENTITY_MISMATCH")
    return {
        "status": "INTEGRITY_DEFECT" if defects else "REVIEW_REQUIRED",
        "integrityDefects": defects,
        "sourcePredicateStatus": execution["status"] if execution else "NOT_ASSESSED",
        "valueSemantics": "BOUNDED_PREDICATES_ONLY" if execution else "NOT_ASSESSED",
        "conditionalParameters": "NOT_ASSESSED_COMPLETE_APPLICABILITY_REQUIRED",
        "crossFieldAndSegmentConflicts": "NOT_ASSESSED_COMPLETE_DEPENDENCIES_REQUIRED",
        "negativeTestObjective": "NOT_ASSESSED_INDEPENDENT_CASE_ORACLE_REQUIRED",
        "requestResponseAndLifecycle": "NOT_ASSESSED_CONTEXT_AND_STATE_REQUIRED",
        "fullySemanticallyValidated": False,
    }


def feedback_checks(payload, metadata, cases, payload_name, widths, rules):
    if not isinstance(payload, dict) or not isinstance(metadata, dict):
        return {"status": "NOT_ASSESSED_INVALID_JSON_SHAPE", "findings": [], "unevaluatedRuleIds": sorted(rules)}
    unique_cases = {AUDIT["fingerprint"](case): case for case in cases}
    if len(unique_cases) != 1:
        return {"status": "NOT_ASSESSED_UNIQUE_CASE_REQUIRED", "findings": [], "unevaluatedRuleIds": sorted(rules)}
    case = next(iter(unique_cases.values()))
    legs = [leg for leg in case["legs"] if leg.get("file") == payload_name]
    if len(legs) != 1:
        return {"status": "NOT_ASSESSED_UNAMBIGUOUS_LEG_REQUIRED", "findings": [], "unevaluatedRuleIds": sorted(rules)}
    try:
        observations = QE["check_leg"](case, legs[0], payload, metadata, widths)
    except (KeyError, TypeError, ValueError, AttributeError) as error:
        return {"status": "NOT_ASSESSED_UNSUPPORTED_PRODUCER_SHAPE", "errorType": type(error).__name__,
                "findings": [], "unevaluatedRuleIds": sorted(rules)}
    findings = [{"ruleId": rule_id, "status": status, "sourceConflict": rules[rule_id].get("sourceConflict")}
                for rule_id, status, _detail in observations]
    evaluated = {finding["ruleId"] for finding in findings}
    return {"status": "FEEDBACK_FINDINGS" if any(row["status"] != "PASS" for row in findings)
            else "BOUNDED_FEEDBACK_CHECKS_PASSED",
            "findings": findings, "unevaluatedRuleIds": sorted(set(rules) - evaluated)}


def assess(audit_directory, output, element_report=None):
    audit_directory, output = audit_directory.resolve(), output.resolve()
    summary_path = audit_directory / "summary.json"
    audit = read_json(summary_path)
    if audit.get("artifact") != "ATL105-DELIVERY-WIDE-ASSESSMENT":
        raise ValueError("Unsupported audit summary")
    root = Path(audit["runRoot"]).resolve()
    if output.exists() or output.is_relative_to(root):
        raise ValueError("Output must be new and outside the producer delivery")
    required = ("file-inventory.jsonl", "tc-physical-assessment.jsonl", "artifact-occurrences.jsonl",
                "content-conflicts.jsonl")
    input_hashes = {str(summary_path): file_hash(summary_path), str(Path(__file__)): file_hash(Path(__file__)),
                    str(Path(__file__).with_name("audit-ai-delivery-coverage.py")):
                        file_hash(Path(__file__).with_name("audit-ai-delivery-coverage.py"))}
    qe_files = (Path(__file__).with_name("apply-qe-feedback-rules.py"), QE["CATALOG"], QE["WIDTH_CONTRACT"])
    for path in qe_files:
        input_hashes[str(path)] = file_hash(path)
    rule_catalog = read_json(QE["CATALOG"])
    if (rule_catalog.get("specification") != "ATL105"
            or rule_catalog.get("specificationVersion") != audit.get("baselineVersion")):
        raise ValueError("QE feedback catalog must match the independent audit's specification release")
    feedback_rules = {rule["ruleId"]: rule for rule in rule_catalog["rules"]
                      if rule["level"] == "PAYLOAD"}
    widths = {segment: 4 for segment in read_json(QE["WIDTH_CONTRACT"])["fourDigitSegments"]}
    for name in required:
        path = audit_directory / name
        actual = file_hash(path)
        if audit.get("outputHashes", {}).get(name) != actual:
            raise ValueError("Audit ledger hash mismatch: " + name)
        input_hashes[str(path)] = actual
    files = {}
    for record in rows(audit_directory / "file-inventory.jsonl"):
        relative = record["path"]
        if relative in files:
            raise ValueError("Duplicate inventory path: " + relative)
        files[relative] = {key: record[key] for key in
                          ("path", "kind", "sha256", "jsonStatus", "testCaseId", "scenarioId") if key in record}
    if len(files) != audit["fileCount"]:
        raise ValueError("Inventory count does not match the completed audit")
    matrix_relative = "traceability_matrix_full.json"
    if matrix_relative not in files:
        raise ValueError("Supported JSON traceability matrix is absent")
    matrix = safe_path(root, root / matrix_relative)
    element_evidence = load_element_evidence(element_report, files)
    if element_report:
        input_hashes[str(element_report.resolve())] = file_hash(element_report)
    if {p.relative_to(root).as_posix() for p in root.rglob("*") if p.is_file()} != set(files):
        raise ValueError("Delivery population differs from the audited snapshot")
    for relative, info in files.items():
        if file_hash(safe_path(root, root / relative)) != info["sha256"]:
            raise ValueError("Delivery changed since audit: " + relative)

    graph, cases = catalog_graph(root, audit["catalogs"])
    blocked_cases = {row["id"] for row in rows(audit_directory / "content-conflicts.jsonl") if row["kind"] == "TC"}
    blocked_cases.update(row["id"] for row in rows(audit_directory / "artifact-occurrences.jsonl")
                         if row["kind"] == "TC" and row["duplicateIdWithinCatalog"])
    discrepancies = []
    for flat in (False, True):
        discrepancies.extend(graph_discrepancies(graph, matrix_graph(matrix, flat),
                                                  "flat_rows" if flat else "requirements"))
    claims = collections.defaultdict(list)
    for chain in rows(audit_directory / "tc-physical-assessment.jsonl"):
        for payload in chain["paths"]:
            claims[payload].append({"id": chain["id"], "scenarioId": chain.get("scenarioId")})
    output.mkdir(parents=True)
    AUDIT["write_rows"](output / "matrix-discrepancies.jsonl", discrepancies)
    td_counts = collections.Counter()
    predicate_counts = collections.Counter()
    feedback_counts = collections.Counter()
    feedback_status_counts = collections.Counter()
    td_count = 0
    expected_td_count = audit["physicalFileCountsByKind"].get("PAYLOAD", 0)
    with (output / "test-data-validation.jsonl").open("w", encoding="utf-8") as stream:
        for relative, info in sorted(files.items()):
            if info["kind"] != "PAYLOAD":
                continue
            metadata_path = relative[:-len(".json")] + ".meta.json"
            metadata = files.get(metadata_path)
            execution = element_evidence.get(relative)
            disposition = td_disposition(info, metadata, claims[relative], execution)
            selected_cases = [case for claim in claims[relative] for case in cases.get(claim["id"], [])
                              if claim["id"] not in blocked_cases
                              and metadata and case.get("scenario_id") == metadata.get("scenarioId")
                              and case.get("id") == metadata.get("testCaseId")]
            if info.get("jsonStatus") == "OBJECT" and metadata and metadata.get("jsonStatus") == "OBJECT":
                feedback = feedback_checks(read_json(root / relative), read_json(root / metadata_path),
                                           selected_cases, Path(relative).name, widths, feedback_rules)
                if (file_hash(root / relative) != info["sha256"]
                        or file_hash(root / metadata_path) != metadata["sha256"]):
                    raise ValueError("TD changed while feedback checks were running: " + relative)
            else:
                feedback = {"status": "NOT_ASSESSED_INVALID_JSON_SHAPE", "findings": [],
                            "unevaluatedRuleIds": sorted(feedback_rules)}
            feedback_status_counts[feedback["status"]] += 1
            feedback_counts.update(finding["ruleId"] + ":" + finding["status"]
                                   for finding in feedback["findings"])
            td_counts[disposition["status"]] += 1
            predicate_counts[disposition["sourcePredicateStatus"]] += 1
            td_count += 1
            stream.write(canonical({"payloadPath": relative, "payloadSha256": info["sha256"],
                                    "metadataPath": metadata_path,
                                    "metadataSha256": metadata["sha256"] if metadata else None,
                                    "testCaseIds": sorted({claim["id"] for claim in claims[relative]}),
                                    **disposition, "feedbackConformance": feedback,
                                    "fieldValuesExported": False}) + "\n")
            if td_count % 1000 == 0:
                print("TD assessed=" + str(td_count) + "/" + str(expected_td_count), flush=True)
    if td_count != expected_td_count:
        raise ValueError("TD ledger does not reconcile to the complete audited population")
    # Recheck the inputs used to derive relationships after streaming large catalogs.
    for relative in [*audit["catalogs"], matrix_relative]:
        if file_hash(root / relative) != files[relative]["sha256"]:
            raise ValueError("Catalog/matrix changed during reconciliation: " + relative)
    for path, expected in input_hashes.items():
        if file_hash(Path(path)) != expected:
            raise ValueError("Assessment dependency changed during validation: " + path)
    if {p.relative_to(root).as_posix() for p in root.rglob("*") if p.is_file()} != set(files):
        raise ValueError("Delivery population changed during validation")
    result = {
        "artifact": "ATL105-AI-DELIVERY-EVIDENCE-GATE",
        "runRoot": str(root), "overallStatus": "BLOCKED_UNPROVEN_SEMANTIC_CORRECTNESS",
        "inputHashes": input_hashes, "artifactCounts": audit["uniqueIdsByKind"],
        "matrixStatus": "DISCREPANCIES_FOUND" if discrepancies else "STRUCTURAL_RELATIONSHIPS_CONSISTENT",
        "matrixDiscrepancyCount": len(discrepancies),
        "matrixDiscrepanciesByRepresentationKindAndStatus": dict(collections.Counter(
            row["representation"] + ":" + row["kind"] + ":" + row["status"] for row in discrepancies)),
        "physicalTestDataCount": td_count, "testDataLedgerReconciled": True,
        "testDataStatuses": dict(td_counts), "sourcePredicateStatuses": dict(predicate_counts),
        "feedbackAssessmentStatuses": dict(feedback_status_counts),
        "feedbackRuleStatusCounts": dict(feedback_counts),
        "semanticallyValidatedTestDataCount": 0,
        "businessRequirementSemanticStatus": "NOT_ASSESSED_SOURCE_MEANING_REQUIRED",
        "scenarioSemanticStatus": "NOT_ASSESSED_BR_INTENT_AND_APPLICABILITY_REQUIRED",
        "testCaseSemanticStatus": "NOT_ASSESSED_PRECONDITIONS_AND_OUTCOME_ORACLES_REQUIRED",
        "fullDeliveryValidated": False,
        "limitations": [
            "Catalog/matrix consistency and JSON integrity are not semantic correctness.",
            "Optional existing element results prove bounded source predicates only; they cannot grant full TD acceptance.",
            "Negative TD may deliberately violate a rule; predicate INVALID alone is not an AI test defect.",
            "QE feedback checks reuse check_leg as written; feedback/source conflicts remain attached and are not ATL105 semantic defects.",
            "Complete conditions, allowed-value applicability, dependencies, conflicts and lifecycle remain unproven.",
            "Archives and Markdown/auxiliary matrices retain the audit's separate unassessed dispositions.",
        ],
    }
    (output / "summary.json").write_text(json.dumps(result, indent=2), encoding="utf-8")
    manifest = {"inputHashes": input_hashes,
                "outputHashes": {p.name: file_hash(p) for p in output.iterdir() if p.is_file()}}
    (output / "manifest.json").write_text(json.dumps(manifest, indent=2), encoding="utf-8")
    print(json.dumps({key: result[key] for key in
                      ("overallStatus", "physicalTestDataCount", "matrixDiscrepancyCount", "testDataStatuses")}))
    return result


def self_test():
    payload = {"kind": "PAYLOAD", "jsonStatus": "OBJECT", "sha256": "payload"}
    metadata = {"kind": "METADATA", "jsonStatus": "OBJECT", "sha256": "metadata",
                "testCaseId": "TC1", "scenarioId": "TS1"}
    claims = [{"id": "TC1", "scenarioId": "TS1"}]
    passed = td_disposition(payload, metadata, claims, {"status": "CHECKS_PASSED"})
    assert passed["status"] == "REVIEW_REQUIRED" and not passed["fullySemanticallyValidated"]
    assert td_disposition(payload, metadata, [], None)["status"] == "INTEGRITY_DEFECT"
    assert td_disposition(payload, None, claims, None)["status"] == "INTEGRITY_DEFECT"
    assert td_disposition(payload, metadata, [{"id": "WRONG"}], None)["status"] == "INTEGRITY_DEFECT"
    negative = td_disposition(payload, metadata, claims, {"status": "INVALID"})
    assert negative["status"] == "REVIEW_REQUIRED" and negative["sourcePredicateStatus"] == "INVALID"
    feedback_rules = {rule["ruleId"]: rule for rule in read_json(QE["CATALOG"])["rules"]
                      if rule["level"] == "PAYLOAD"}
    case = QE["extract_tc"]({"id": "TC1", "scenario_id": "TS1", "scenario_type": "positive",
                             "test_data_file": "TD1.json"})
    checked = feedback_checks({"Financial Transaction Request": {"NumSegments": "1",
                                "Standard Message Data Segment": {"SegmentType": "100", "SegmentLength": "001",
                                                                 "PromptCode": "0000"}}},
                              {}, [case], "TD1.json", {}, feedback_rules)
    assert any(finding["ruleId"] == "QEF-017" and finding["status"] == "FAIL"
               and finding["sourceConflict"] for finding in checked["findings"])
    graph = ({"BR": {"BR1"}, "TS": {"TS1"}, "TC": {"TC1"}},
             {"BR_TS": {("BR1", "TS1")}, "TS_TC": {("TS1", "TC1")},
              "BR_TC": {("BR1", "TC1")}, "TC_TD": {("TC1", "data/TD1.json")}})
    with tempfile.TemporaryDirectory() as temporary:
        path = Path(temporary) / "matrix.json"
        matrix = {"requirements": [{"requirement_id": "BR1", "scenarios": [
            {"scenario_id": "TS1", "test_cases": [
                {"test_case_id": "TC1", "test_data_file": "data/TD1.json"}]}]}],
            "flat_rows": [{"requirement_id": "BR1", "scenario_id": "TS1",
                           "test_case_id": "TC1", "test_data_file": "data/TD1.json"}]}
        path.write_text(json.dumps(matrix), encoding="utf-8")
        assert not list(graph_discrepancies(graph, matrix_graph(path), "requirements"))
        assert not list(graph_discrepancies(graph, matrix_graph(path, True), "flat_rows"))
        matrix["flat_rows"][0]["test_data_file"] = "data/WRONG.json"
        path.write_text(json.dumps(matrix), encoding="utf-8")
        assert len(list(graph_discrepancies(graph, matrix_graph(path, True), "flat_rows"))) == 2
        evidence = {"artifact": "ATL105-AI-RUN-ELEMENT-VALIDATION", "chapter13Semantics": True,
                    "executions": [{"payloadFile": "TD1.json", "metadataFile": "TD1.meta.json",
                                    "payloadSha256": "payload", "metadataSha256": "metadata",
                                    "testCaseId": "TC1", "status": "CHECKS_PASSED"}]}
        path.write_text(json.dumps(evidence), encoding="utf-8")
        assert len(load_element_evidence(path, {"TD1.json": payload, "TD1.meta.json": metadata})) == 1
        evidence["executions"][0]["payloadSha256"] = "stale"
        path.write_text(json.dumps(evidence), encoding="utf-8")
        try:
            load_element_evidence(path, {"TD1.json": payload, "TD1.meta.json": metadata})
        except ValueError as error:
            assert "Stale" in str(error)
        else:
            raise AssertionError("Stale semantic evidence must be rejected")
        baseline = Path(temporary) / "baseline"
        baseline.mkdir()
        rule = {"ruleId": "R1", "atomicity": "ATOMIC",
                "sourceAnchor": {"segment": "100", "element": "55", "version": "2026-3"}}
        (baseline / "source-derived-requirement-inventory.json").write_text(json.dumps({
            "authority": "INDEPENDENT_TEST_SOLUTION", "aiArtifactsIncluded": False,
            "specificationVersion": "2026-3", "ruleCount": 1, "businessRequirements": [rule]}), encoding="utf-8")
        (baseline / "atomic-composite-rule-classification.json").write_text(
            json.dumps({"rules": [rule]}), encoding="utf-8")
        source = Path(temporary) / "source.txt"
        source.write_text("--- PAGE 1 ---\nIndependent synthetic source.\n", encoding="utf-8")
        delivery = Path(temporary) / "delivery"
        documents = {
            "requirement_candidates.json": {"requirements": [{"id": "BR1"}]},
            "approved_scenarios.json": {"scenarios": [{"id": "TS1", "requirement_ids": ["BR1"]}]},
            "test_case_candidates.json": {"test_cases": [
                {"id": "TC1", "scenario_id": "TS1", "requirement_ids": ["BR1"],
                 "test_data_file": "qe_shaped_test_data/TD1.json"}]},
            "traceability_matrix_full.json": {
                "requirements": [{"requirement_id": "BR1", "scenarios": [
                    {"scenario_id": "TS1", "test_cases": [
                        {"test_case_id": "TC1", "test_data": {
                            "test_data_file": "qe_shaped_test_data/TD1.json"}}]}]}],
                "flat_rows": [{"requirement_id": "BR1", "scenario_id": "TS1", "test_case_id": "TC1",
                               "test_data_file": "qe_shaped_test_data/TD1.json"}]},
            "qe_shaped_test_data/TD1.json": {"Financial Transaction Request": {"NumSegments": "1",
                "Standard Message Data Segment": {"SegmentType": "100", "SegmentLength": "001",
                                                 "Canary": "NEVER_EXPORT_THIS_VALUE"}}},
            "qe_shaped_test_data/TD1.meta.json": {"testCaseId": "TC1", "scenarioId": "TS1", "fields": []},
            "qe_shaped_test_data/ORPHAN.json": {"Orphan": {}},
        }
        for relative, document in documents.items():
            destination = delivery / relative
            destination.parent.mkdir(parents=True, exist_ok=True)
            destination.write_text(json.dumps(document), encoding="utf-8")
        audit_directory = Path(temporary) / "audit"
        AUDIT["audit"](delivery, baseline, source, audit_directory)
        result = assess(audit_directory, Path(temporary) / "result")
        assert result["physicalTestDataCount"] == 2 and result["testDataLedgerReconciled"]
        assert result["matrixDiscrepancyCount"] == 0
        assert result["testDataStatuses"] == {"INTEGRITY_DEFECT": 1, "REVIEW_REQUIRED": 1}
        assert not result["fullDeliveryValidated"] and result["semanticallyValidatedTestDataCount"] == 0
        ledger = (Path(temporary) / "result" / "test-data-validation.jsonl").read_text(encoding="utf-8")
        assert "NEVER_EXPORT_THIS_VALUE" not in ledger
        assert len(ledger.splitlines()) == 2
        (delivery / "qe_shaped_test_data" / "TD1.json").write_text("{}", encoding="utf-8")
        try:
            assess(audit_directory, Path(temporary) / "stale-result")
        except ValueError:
            pass
        else:
            raise AssertionError("Delivery input changes must reject stale audit evidence")
    print("PASS: exact matrix edges, TC/TD mismatches, every-TD disposition, stale evidence and semantic nonpromotion")


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--audit-directory", type=Path)
    parser.add_argument("--output-directory", type=Path)
    parser.add_argument("--element-report", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
    elif args.audit_directory and args.output_directory:
        assess(args.audit_directory, args.output_directory, args.element_report)
    else:
        parser.error("--audit-directory and --output-directory are required")


if __name__ == "__main__":
    main()
