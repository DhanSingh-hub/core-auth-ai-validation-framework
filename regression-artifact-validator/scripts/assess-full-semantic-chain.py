import argparse
import collections
import csv
import hashlib
import json
import os
import re
import runpy
from html import escape
from pathlib import Path

import ijson


MODULE = Path(__file__).resolve().parents[1]
HELPERS = runpy.run_path(str(Path(__file__).with_name("assess-ai-semantic-batch.py")))
TRACE = runpy.run_path(str(Path(__file__).with_name("assess-late-traceability-matrix.py")))


def read(file):
    return json.loads(file.read_text(encoding="utf-8-sig"))


def digest(file):
    with file.open("rb") as stream:
        return hashlib.file_digest(stream, "sha256").hexdigest()


def native(file):
    file = Path(os.path.normpath(str(file)))
    return Path("\\\\?\\" + str(file.resolve())) if os.name == "nt" and not str(file).startswith("\\\\?\\") else file


class Queries:
    def __init__(self):
        self.items = {}

    def add(self, kind, subject, question, evidence, stage):
        key = kind + "|" + subject
        query_id = "SME-SEM-" + hashlib.sha256(key.encode()).hexdigest()[:12].upper()
        self.items.setdefault(query_id, {"queryId": query_id, "kind": kind, "subjectId": subject,
            "stage": stage, "question": question, "evidence": evidence, "status": "OPEN_REVIEW_DEFERRED",
            "reviewOwner": "ATL105-SME", "approvalGranted": False})
        return query_id


def stage_disposition(has_br, aligned_ids, objective, response, files, supported_checks, queries):
    reasons = list(queries)
    if not has_br:
        status = "UNATTRIBUTED_BR_CHAIN"
    elif not aligned_ids:
        status = "ARTIFACT_LINK_SCOPE_MISMATCH"
    elif not files or not all(item["present"] for item in files):
        status = "INCOMPLETE_PHYSICAL_CHAIN"
    elif not objective or not response:
        status = "INCOMPLETE_ASSERTION_EVIDENCE"
    elif not supported_checks:
        status = "UNSUPPORTED_SEMANTIC_RULE_SCOPE"
    else:
        status = "PARTIAL_SOURCE_CHECKS_FULL_MEANING_UNPROVEN"
    return {"status": status, "semanticAlignmentConfirmed": False, "smeApproval": "DEFERRED",
            "queryIds": reasons, "executionCertified": False}


def chain_stages(case_links, scenario_links, files, declaration_issues, legs, objective, response):
    if not case_links:
        structure = "UNATTRIBUTED_BR_CHAIN"
    elif case_links != scenario_links:
        structure = "BR_LINK_SET_DIVERGENCE"
    elif declaration_issues:
        structure = "BROKEN_TD_DECLARATION"
    elif (not files or not all(item["present"] for item in files)
          or any(leg.get("status") == "PHYSICAL_FILE_OR_METADATA_MISSING" for leg in legs)):
        structure = "TC_WITHOUT_COMPLETE_PHYSICAL_TD"
    else:
        structure = "STRUCTURAL_BR_TS_TC_TD_LINKED"
    predicates = [check for leg in legs for check in leg.get("fieldChecks", [])]
    provisional_fields = sum(leg.get("provisionalFieldCount", 0) for leg in legs)
    unresolved_segments = sum(leg.get("unresolvedSegmentCount", 0) for leg in legs)
    unavailable_fields = sum(leg.get("unavailableFieldCount", 0) for leg in legs)
    return {"structuralChainStatus": structure,
            "brMeaningStatus": "NOT_VALIDATED_COMPLETE_INDEPENDENT_INTERPRETATION_REQUIRED",
            "tsIntentStatus": "NOT_VALIDATED_BR_CONDITION_AND_OUTCOME_NOT_PROVEN",
            "tcAssertionStatus": "DECLARED_NOT_SEMANTICALLY_VALIDATED" if objective else "EXPLICIT_ASSERTION_MISSING",
            "tcExpectedOutcomeStatus": "DECLARED_NOT_ORACLE_VALIDATED" if response else "EXPECTED_RESPONSE_MISSING",
            "tdPhysicalStatus": "ALL_DECLARED_FILES_PRESENT" if files and all(item["present"] for item in files)
                and not any(leg.get("status") == "PHYSICAL_FILE_OR_METADATA_MISSING" for leg in legs) else "PHYSICAL_DATA_INCOMPLETE",
            "tdPredicateStatus": "PARTIAL_BOUNDED_PREDICATES_ONLY" if predicates else "NOT_ASSESSED_NO_SUPPORTED_PREDICATE",
            "tdDependencyStatus": "PROVISIONAL_OR_UNRESOLVED_METADATA_PRESENT" if provisional_fields or unresolved_segments or unavailable_fields else "NO_LISTED_PROVISIONAL_METADATA"}


def self_test():
    assert stage_disposition(True, True, True, False, [{"present": True}], True, [])['status'] == "INCOMPLETE_ASSERTION_EVIDENCE"
    assert stage_disposition(True, True, True, True, [{"present": True}], True, [])['semanticAlignmentConfirmed'] is False
    assert stage_disposition(False, True, True, True, [{"present": True}], True, [])['status'] == "UNATTRIBUTED_BR_CHAIN"
    stages = chain_stages({"BR-1"}, {"BR-1"}, [{"present": True}], [], [{"fieldChecks": [{"predicate": "PASS"}]}], False, False)
    assert stages["structuralChainStatus"] == "STRUCTURAL_BR_TS_TC_TD_LINKED"
    assert stages["tcExpectedOutcomeStatus"] == "EXPECTED_RESPONSE_MISSING"
    assert stages["tdPredicateStatus"] == "PARTIAL_BOUNDED_PREDICATES_ONLY"
    queries = Queries()
    first = queries.add("ORACLE", "TC-1", "Question", {}, "TC")
    assert first == queries.add("ORACLE", "TC-1", "Question", {}, "TC") and len(queries.items) == 1
    assert queries.items[first]["approvalGranted"] is False
    print("PASS: missing outcome refusal, full-chain nonpromotion, orphan handling and stable deferred SME query IDs")


def assess(review, output):
    output = native(output)
    if output.exists():
        raise ValueError("A new assessment directory is required; refusing overwrite")
    run = read(review / "complete-handoff-analysis.json")
    archive = Path(run["archive"])
    pipeline = archive / "pipeline_run_artifacts"
    catalogs = archive / "scenerio_req_5_oct/scenerio_req_5_oct"
    requirement_file = catalogs / "step5_requirements/approved/requirement_catalog.json"
    scenario_file = catalogs / "scenarios/approved/approved_scenarios.json"
    case_file = pipeline / "test_case_candidates.json"
    source_file = MODULE / "specifications/ATL105/docs/specs/extracted_text.txt"
    gate_file = MODULE / "specifications/ATL105/test-output/test-solution-independent-review/atl105-source-backed-rule-gate.json"
    source_inventory_file = MODULE / "specifications/ATL105/test-output/test-solution-independent-review/source-derived-requirement-inventory.json"
    requirements = {item["id"]: item for item in read(requirement_file)["requirements"]}
    scenarios = {item["id"]: item for item in read(scenario_file)["scenarios"]}
    source = source_file.read_text(encoding="utf-8-sig")
    pages = HELPERS["index_source_pages"](source)
    gate = read(gate_file)
    source_inventory = read(source_inventory_file)
    if digest(source_file) != gate["specificationFingerprintSha256"]:
        raise ValueError("Curated source gate no longer matches the source fingerprint")
    with (review / "intake-file-hashes.csv").open(encoding="utf-8-sig", newline="") as stream:
        manifest = {item["path"]: item for item in csv.DictReader(stream)}
    for file in [requirement_file, scenario_file, case_file]:
        if digest(file) != manifest[file.relative_to(archive).as_posix()]["sha256"]:
            raise ValueError("Frozen catalog hash changed: " + file.name)
    queries = Queries()
    curated_rule_ids = {item["ruleId"] for item in gate.get("assertions", [])}
    independent_rules = []
    for rule in source_inventory["businessRequirements"]:
        rule_id = rule["ruleId"]
        curated = rule_id in curated_rule_ids
        query = queries.add("SOURCE_RULE_SCOPE_REVIEW" if curated else "INDEPENDENT_BR_MEANING",
            rule_id,
            ("Review the partial source-backed assertions; confirm complete applicability, conditions, obligations, exceptions and cross-section scope. Source-backed assertion is not semantic approval."
             if curated else
             "Derive and independently approve the atomic BR interpretation from the ATL105 source: applicability, trigger, obligations, permitted values, exceptions and expected behavior; do not inherit AI wording."),
            {"title": rule.get("title"), "sourceAnchor": rule.get("sourceAnchor"), "sourceCatalog": rule.get("sourceCatalog"),
             "sourceEvidenceResolution": rule.get("sourceEvidenceResolution"), "hasCuratedAssertion": curated}, "INDEPENDENT_BR_BASELINE")
        independent_rules.append({**rule, "semanticMeaningStatus": "SOURCE_BACKED_CANDIDATE_REVIEW_REQUIRED" if curated else "SOURCE_SEMANTICS_NOT_CURATED",
                                  "smeQueryIds": [query], "approved": False})
    br_rows = []
    for br_id, requirement in requirements.items():
        evidence = HELPERS["source_excerpt"](requirement, pages)
        query = queries.add("BR_FULL_MEANING", br_id,
            "Provide a complete independent interpretation of applicability, triggers, obligations, exceptions and expected outcomes; page/entity linkage alone is not equivalence.",
            {"sourceRuleId": requirement.get("source_rule_id"), "sourcePage": requirement.get("source_page"),
             "statement": requirement.get("statement"), "pageLocatorStatus": evidence["pageLocatorStatus"]}, "BR")
        br_rows.append({"requirementId": br_id, "statement": requirement.get("statement"),
                       "sourceRuleId": requirement.get("source_rule_id"), "sourcePage": requirement.get("source_page"),
                       "sourceLocatorStatus": evidence["pageLocatorStatus"], "meaningStatus": "COMPLETE_INTERPRETATION_NOT_SUPPLIED",
                       "semanticCoverageConfirmed": False, "smeQueryIds": [query]})
    ts_rows = []
    for scenario_id, scenario in scenarios.items():
        query = queries.add("TS_BR_INTENT", scenario_id,
            "Confirm the scenario exercises the complete linked BR conditions and expected behavior, rather than only shared fields or a generated title.",
            {"requirementIds": sorted(HELPERS["links"](scenario)), "scenarioClaim": scenario.get("name"),
             "expectedResponseCode": scenario.get("expected_response_code"), "targetTransaction": scenario.get("target_transaction")}, "TS")
        ts_rows.append({"scenarioId": scenario_id, "requirementIds": sorted(HELPERS["links"](scenario)),
                        "intentStatus": "FULL_BR_OBJECTIVE_NOT_MACHINE_VERIFIED", "smeQueryIds": [query]})
    case_rows = []
    checks_count = collections.Counter()
    metadata_count = collections.Counter()
    methods_count = collections.Counter()
    seen_files = {}
    cases_per_scenario = collections.Counter()
    cases_per_br = collections.Counter()
    dispositions = collections.Counter()
    with case_file.open("rb") as stream:
        for case in ijson.items(stream, "test_cases.item", use_float=True):
            case_id = case["id"]
            scenario_id = case["scenario_id"]
            if scenario_id not in scenarios:
                raise ValueError("Unknown TC scenario reference: " + case_id)
            scenario = scenarios[scenario_id]
            case_links = HELPERS["links"](case)
            scenario_links = HELPERS["links"](scenario)
            cases_per_scenario[scenario_id] += 1
            cases_per_br.update(case_links)
            files, declaration_issues = TRACE["declared_case_files"](case, pipeline / "qe_shaped_test_data")
            row_queries = []
            objective = case.get("business_rule_assertion")
            if not isinstance(objective, str) or not objective.strip():
                row_queries.append(queries.add("TC_OBJECTIVE", case_id,
                    "Supply explicit TC assertions for every linked BR obligation; producer intent/type and field values do not establish the full objective.",
                    {"scenarioId": scenario_id, "requirementIds": sorted(case_links)}, "TC"))
            if not case.get("expected_response"):
                row_queries.append(queries.add("TC_OUTCOME", case_id,
                    "Supply source-grounded expected assertions or explicitly declare request-only scope with complete applicable predicates; no processor outcome may be inferred.",
                    {"scenarioExpectedResponseCode": scenario.get("expected_response_code"), "caseExpectedResponse": case.get("expected_response")}, "TC"))
            if not case_links or case_links != scenario_links:
                row_queries.append(queries.add("CASE_BR_ATTRIBUTION", case_id,
                    "Resolve missing or divergent BR attribution between scenario and case; do not match by keywords or reused IDs.",
                    {"caseRequirementIds": sorted(case_links), "scenarioRequirementIds": sorted(scenario_links)}, "BR_TS_TC"))
            legs = []
            for item in files:
                filename = item["file"].rsplit("/", 1)[-1]
                payload_file = pipeline / item["file"]
                meta_file = payload_file.with_name(payload_file.stem + ".meta.json")
                if not item["present"] or not meta_file.is_file():
                    legs.append({"file": filename, "status": "PHYSICAL_FILE_OR_METADATA_MISSING", "fieldChecks": [], "dataMethods": []})
                    row_queries.append(queries.add("TD_MISSING", case_id + "/" + filename,
                        "Restore the declared payload/metadata pair or justify an explicit unavailable/excluded state without counting execution readiness.", {}, "TD"))
                    continue
                hashes = {}
                for file in [payload_file, meta_file]:
                    hash_value = digest(file)
                    if hash_value != manifest[file.relative_to(archive).as_posix()]["sha256"]:
                        raise ValueError("Physical artifact hash mismatch: " + filename)
                    hashes[file.name] = hash_value
                    seen_files[file.name] = hash_value
                metadata = read(meta_file)
                if metadata.get("testCaseId") != case_id or metadata.get("scenarioId") != scenario_id:
                    raise ValueError("Physical metadata join mismatch: " + filename)
                payload = read(payload_file)
                evaluated = HELPERS["evaluate_leg"](case, payload, metadata)
                metadata_fields = metadata.get("fields", [])
                data_sources = collections.Counter(str(field.get("dataSource", "NOT_DECLARED")) for field in metadata_fields)
                provisional_fields = [field for field in metadata_fields if field.get("provisional") is True]
                unavailable_fields = metadata.get("unavailableFields", [])
                unresolved_segments = metadata.get("dataSections", {}).get("unresolved", {}).get("segments", [])
                evaluated.update({"file": filename, "role": metadata.get("role"), "step": metadata.get("step"), "inputSha256": hashes})
                evaluated["dataMethods"] = sorted(data_sources)
                evaluated["provisionalFieldCount"] = len(provisional_fields)
                evaluated["provisionalFieldSamples"] = [{"segment": field.get("segment"), "element": field.get("element"),
                    "specElementName": field.get("specElementName"), "dataSource": field.get("dataSource")} for field in provisional_fields[:10]]
                evaluated["unavailableFieldCount"] = len(unavailable_fields) if isinstance(unavailable_fields, list) else 0
                evaluated["unresolvedSegmentCount"] = len(unresolved_segments) if isinstance(unresolved_segments, list) else 0
                evaluated["unresolvedSegmentSamples"] = unresolved_segments[:10] if isinstance(unresolved_segments, list) else []
                legs.append(evaluated)
                metadata_count[evaluated["interpretation"]] += 1
                for check in evaluated["fieldChecks"]:
                    checks_count[check["predicate"]] += 1
                if not evaluated["fieldChecks"] or any(check["predicate"] != "PASS" for check in evaluated["fieldChecks"]):
                    row_queries.append(queries.add("TD_PREDICATE_SCOPE", case_id + "/" + filename,
                        "Adjudicate unsupported or failing source predicates; distinguish intended negative violations from unrelated defects and full-message validity.",
                        {"fieldChecks": evaluated["fieldChecks"], "intent": case.get("scenario_type")}, "TD"))
            methods = collections.Counter(str(item.get("method")) for _, item in HELPERS["descriptors"](case.get("request", {})))
            unresolved = {key: count for key, count in methods.items() if key in {"placeholder", "awaiting_client_value", "spec_unspecified_code", "derived_at_wire_encoding"}}
            methods_count.update(unresolved)
            metadata_dependencies = [{"file": leg.get("file"), "provisionalFieldCount": leg.get("provisionalFieldCount", 0),
                "unavailableFieldCount": leg.get("unavailableFieldCount", 0), "unresolvedSegmentCount": leg.get("unresolvedSegmentCount", 0),
                "provisionalFieldSamples": leg.get("provisionalFieldSamples", []), "unresolvedSegmentSamples": leg.get("unresolvedSegmentSamples", [])}
                for leg in legs if leg.get("provisionalFieldCount", 0) or leg.get("unavailableFieldCount", 0) or leg.get("unresolvedSegmentCount", 0)]
            if unresolved or metadata_dependencies:
                row_queries.append(queries.add("TD_DEPENDENCIES", case_id,
                    "Review AI request data methods and physical TD metadata provenance, provisional fields, unavailable fields and unresolved companion segments; verify required values and cross-field/context dependencies.",
                    {"caseRequestMethods": unresolved, "physicalLegs": metadata_dependencies}, "TD"))
            if case.get("scenario_type") == "negative":
                row_queries.append(queries.add("NEGATIVE_EFFECTIVENESS", case_id,
                    "Supply a valid complete control, intended single mutation and authoritative rejection assertion; preservation alone does not prove effectiveness.",
                    {"negativeClass": case.get("negative_class"), "target": case.get("violated_element_name")}, "TC_TD"))
            if case.get("is_flow"):
                row_queries.append(queries.add("LIFECYCLE_CONTEXT", case_id,
                    "Confirm transaction roles, original/follow-up correlation and source applicability; equal sequence values alone do not prove the entire flow.",
                    HELPERS["lifecycle_observation"](legs), "TS_TC_TD"))
            outcome = stage_disposition(bool(case_links), case_links == scenario_links, bool(objective), bool(case.get("expected_response")),
                                        files, any(leg.get("fieldChecks") for leg in legs), row_queries)
            dispositions[outcome["status"]] += 1
            stages = chain_stages(case_links, scenario_links, files, declaration_issues, legs, bool(objective), bool(case.get("expected_response")))
            case_rows.append({"caseId": case_id, "scenarioId": scenario_id, "intent": case.get("scenario_type"),
                "transactionLabel": case.get("transaction_type"), "requirementIds": sorted(case_links),
                "scenarioRequirementIds": sorted(scenario_links), "objectiveClaim": objective,
                "expectedResponse": case.get("expected_response"), "scenarioExpectedCode": scenario.get("expected_response_code"),
                "physicalLegs": legs, "declaredFileIssues": declaration_issues, "provisionalMethods": unresolved,
                "sourcePredicateScope": "Identity/six-digit representation and existing bounded mutation checks only",
                "stageStatuses": stages,
                **outcome})
    for row in ts_rows:
        if not cases_per_scenario[row["scenarioId"]]:
            row["smeQueryIds"].append(queries.add("TS_NO_CASE", row["scenarioId"],
                "Provide cases/assertions for this scenario or a source-backed scope decision; a generation skip is not an approved coverage exclusion.", {}, "TS_TC"))
    for row in br_rows:
        row["caseCount"] = cases_per_br[row["requirementId"]]
    output.mkdir(parents=True)
    summary = {"assessmentDate": "2026-10-07", "assessmentStatus": "WHOLE_DELIVERY_ASSESSED_WITH_EXPLICIT_EVIDENCE_GAPS",
               "scope": "Full-population stage assessment; not a claim that unsupported business meaning is validated",
               "businessRequirements": len(br_rows), "scenarios": len(ts_rows), "testCases": len(case_rows),
               "physicalPayloadLegsAssessed": sum(len(row["physicalLegs"]) for row in case_rows),
               "physicalFilesHashVerified": len(seen_files), "caseDispositionCounts": dict(dispositions),
               "structuralChainStatusCounts": dict(collections.Counter(row["stageStatuses"]["structuralChainStatus"] for row in case_rows)),
               "stageStatusCounts": {key: dict(collections.Counter(row["stageStatuses"][key] for row in case_rows)) for key in
                   ["brMeaningStatus", "tsIntentStatus", "tcAssertionStatus", "tcExpectedOutcomeStatus", "tdPhysicalStatus", "tdPredicateStatus", "tdDependencyStatus"]},
               "sourcePredicateCounts": dict(checks_count), "metadataInterpretations": dict(metadata_count),
               "caseRequestMethodOccurrences": dict(methods_count), "physicalTdProvisionalFields": sum(leg.get("provisionalFieldCount", 0) for row in case_rows for leg in row["physicalLegs"]),
               "physicalTdUnavailableFieldOccurrences": sum(leg.get("unavailableFieldCount", 0) for row in case_rows for leg in row["physicalLegs"]),
               "physicalTdUnresolvedSegmentOccurrences": sum(leg.get("unresolvedSegmentCount", 0) for row in case_rows for leg in row["physicalLegs"]),
               "smeQueryCount": len(queries.items),
               "smeQueryKinds": dict(collections.Counter(item["kind"] for item in queries.items.values())),
               "smeReview": "DEFERRED_AT_USER_REQUEST", "semanticAlignmentConfirmed": 0,
               "semanticCoverage": "NOT_CALCULABLE_NO_COMPLETE_INDEPENDENT_RULE_INTERPRETATIONS", "executionCertified": False,
               "sourceGate": {"curatedRules": gate["summary"]["curatedRuleCount"], "unCuratedRules": gate["summary"]["rulesWithoutCuratedAssertions"],
                              "rawSpecificationSha256": gate["specificationFingerprintSha256"],
                              "assertionSourceFingerprint": gate["sourceFingerprintSha256"]},
               "independentSourceRules": len(independent_rules),
               "independentSourceRulesCuratedCandidates": len(curated_rule_ids),
               "independentSourceRulesWithoutCuratedAssertions": len(independent_rules) - len(curated_rule_ids),
               "inputSha256": {str(file): digest(file) for file in [requirement_file, scenario_file, case_file, source_file, gate_file, source_inventory_file]},
               "queryPolicy": "Every unresolved stage gets stable query IDs; reviewer deferral is never implicit approval"}
    for name, value in [("semantic-chain-summary.json", summary), ("semantic-independent-source-rule-assessment.json", independent_rules),
                        ("semantic-br-assessment.json", br_rows),
                        ("semantic-ts-assessment.json", ts_rows), ("semantic-tc-td-assessment.json", case_rows),
                        ("semantic-sme-query-register.json", list(queries.items.values()))]:
        (output / name).write_text(json.dumps(value, indent=2, ensure_ascii=True), encoding="utf-8")
    with (output / "semantic-independent-source-rule-assessment.csv").open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=["ruleId", "title", "class", "severity", "canonicalAnchor", "sourceCatalog", "sourceEvidenceResolution", "semanticMeaningStatus", "smeQueryIds", "approved"])
        writer.writeheader()
        for item in independent_rules:
            writer.writerow({key: json.dumps(item[key], ensure_ascii=True) if isinstance(item[key], (dict, list)) else item.get(key) for key in writer.fieldnames})
    with (output / "semantic-sme-query-register.csv").open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=["queryId", "kind", "subjectId", "stage", "question", "status", "reviewOwner", "approvalGranted", "evidence"])
        writer.writeheader()
        for item in queries.items.values():
            writer.writerow({**item, "evidence": json.dumps(item["evidence"], ensure_ascii=True)})
    print(json.dumps(summary, indent=2))


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("review_directory", nargs="?", type=Path)
    parser.add_argument("output_directory", nargs="?", type=Path)
    parser.add_argument("--self-test", action="store_true")
    parser.add_argument("--render", action="store_true")
    parser.add_argument("--validate", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
    elif args.render and args.output_directory:
        render(args.output_directory)
    elif args.validate and args.output_directory:
        validate(args.output_directory)
    elif args.review_directory and args.output_directory:
        assess(args.review_directory, args.output_directory)
    else:
        parser.error("Review and new output directories are required")


def render(output):
    output = native(output)
    summary = read(output / "semantic-chain-summary.json")
    independent_rules = read(output / "semantic-independent-source-rule-assessment.json")
    brs = read(output / "semantic-br-assessment.json")
    scenarios = read(output / "semantic-ts-assessment.json")
    cases = read(output / "semantic-tc-td-assessment.json")
    queries = read(output / "semantic-sme-query-register.json")
    data = {"summary": summary,
            "independentRules": independent_rules,
            "brs": brs,
            "scenarios": scenarios,
            "cases": [{key: row[key] for key in ["caseId", "scenarioId", "intent", "transactionLabel", "requirementIds", "status", "queryIds", "stageStatuses"]} for row in cases],
            "queries": [{key: row[key] for key in ["queryId", "kind", "subjectId", "stage", "question", "status"]} for row in queries]}
    encoded = json.dumps(data, ensure_ascii=True, separators=(",", ":")).replace("</", "<\\/")
    html = '''<!doctype html><html lang="en"><head><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>ATL105 | Full Semantic Chain Assessment</title>
<style>body{margin:0;background:#f4f6f5;color:#202526;font:14px 'Segoe UI',sans-serif;letter-spacing:0;overflow-wrap:anywhere}header{padding:24px;background:white;border-top:5px solid #07665e}h1{font:700 26px Georgia,serif}h2{font-size:20px;color:#07665e}main{max-width:1400px;margin:auto;padding:24px}section{padding:20px 0;border-bottom:1px solid #ccd8d4}.notice{padding:14px;background:#fff6df;border-left:4px solid #9b6510}table{border-collapse:collapse;width:100%;background:white}th,td{padding:9px;border-bottom:1px solid #ccd8d4;text-align:left;vertical-align:top}th{background:#e4eeeb}.table-wrap{overflow:auto}input,select,button{font:inherit;padding:9px;border:1px solid #9caba6;border-radius:4px;background:white}button{cursor:pointer}a{color:#07665e}.filters{display:flex;gap:12px;flex-wrap:wrap;margin:14px 0}.pager{display:flex;gap:12px;align-items:center;margin-top:12px}.scope{color:#606c68}@media(max-width:650px){header,main{padding:16px}table{min-width:760px}input{max-width:calc(100vw - 60px)}}@media print{.filters,.pager{display:none}section{break-inside:auto}}</style></head>
<body><header><h1>ATL105 | Full Semantic Chain Assessment</h1><p>October 7, 2026 | October 5 Run1 | SME review deferred</p><a href="../AI-ARTIFACT-FILTER-VIEW.html">Run assessment</a> | <a href="../SEMANTIC-FIRST-BATCH-REPORT.html">Earlier bounded assessment</a></header><main>
<section><h2>Assessment scope and conclusion</h2><div class="notice">The entire delivery was assessed for available evidence. Structural link completeness is reported separately from BR meaning and semantic chain alignment. SME review is deferred, not approved. Execution certification is not granted.</div><table><thead><tr><th>Measure</th><th>Count/status</th></tr></thead><tbody id="summary"></tbody></table><p class="scope">Supported physical checks remain bounded to existing source predicates. A structurally complete chain is not a semantic-equivalence finding. Curated source assertions cover only a subset of the independent rule catalog.</p></section>
<section><h2>Complete BR stage register</h2><div class="filters"><input id="br-search" type="search" placeholder="BR ID or source rule" aria-label="Search BR register"></div><p id="br-count"></p><div class="table-wrap"><table><thead><tr><th>BR</th><th>Source rule</th><th>Statement</th><th>Cases linked</th><th>Meaning status</th><th>SME query</th></tr></thead><tbody id="br-body"></tbody></table></div><div class="pager"><button id="br-prev" aria-label="Previous BR page">&#8592;</button><span id="br-page"></span><button id="br-next" aria-label="Next BR page">&#8594;</button></div></section>
<section><h2>Complete TS stage register</h2><div class="filters"><input id="ts-search" type="search" placeholder="TS or BR ID" aria-label="Search TS register"></div><p id="ts-count"></p><div class="table-wrap"><table><thead><tr><th>Scenario</th><th>BR links</th><th>Intent status</th><th>SME query</th></tr></thead><tbody id="ts-body"></tbody></table></div><div class="pager"><button id="ts-prev" aria-label="Previous TS page">&#8592;</button><span id="ts-page"></span><button id="ts-next" aria-label="Next TS page">&#8594;</button></div></section>
<section><h2>Complete TC/TD stage register</h2><div class="filters"><input id="case-search" type="search" placeholder="TC, scenario or BR" aria-label="Search case register"><select id="case-status" aria-label="Structural chain status"></select></div><p id="case-count"></p><div class="table-wrap"><table><thead><tr><th>Case</th><th>Scenario</th><th>Intent</th><th>Structure</th><th>BR meaning</th><th>TC assertions/outcome</th><th>TD physical/predicate</th><th>Query IDs</th></tr></thead><tbody id="case-body"></tbody></table></div><div class="pager"><button id="case-prev" title="Previous case page" aria-label="Previous case page">&#8592;</button><span id="case-page"></span><button id="case-next" title="Next case page" aria-label="Next case page">&#8594;</button></div></section>
<section><h2>All deferred SME queries</h2><div class="filters"><input id="query-search" type="search" placeholder="Query ID, artifact or question" aria-label="Search SME queries"><select id="query-kind" aria-label="SME query kind"></select></div><p id="query-count"></p><div class="table-wrap"><table><thead><tr><th>Query</th><th>Kind/stage</th><th>Subject</th><th>Question</th><th>Status</th></tr></thead><tbody id="query-body"></tbody></table></div><div class="pager"><button id="query-prev" title="Previous query page" aria-label="Previous query page">&#8592;</button><span id="query-page"></span><button id="query-next" title="Next query page" aria-label="Next query page">&#8594;</button></div></section>
<section><h2>Complete evidence exports</h2><p><a href="semantic-independent-source-rule-assessment.json">All independent source BRs</a> | <a href="semantic-independent-source-rule-assessment.csv">Source BR CSV</a> | <a href="semantic-br-assessment.json">All AI BR assessments</a> | <a href="semantic-ts-assessment.json">All TS stage assessments</a> | <a href="semantic-tc-td-assessment.json">All TC/TD evidence</a> | <a href="semantic-chain-summary.json">Summary and source hashes</a> | <a href="semantic-sme-query-register.json">SME query JSON</a> | <a href="semantic-sme-query-register.csv">SME query CSV</a></p></section></main>
<script id="data" type="application/json">__DATA__</script><script>
'use strict';const data=JSON.parse(document.getElementById('data').textContent),$=id=>document.getElementById(id);let casePage=0,queryPage=0;const pageSize=50;
function row(target,values){const tr=document.createElement('tr');for(const value of values){const td=document.createElement('td');td.textContent=value;tr.append(td)}$(target).append(tr)}
for(const [label,value] of [['BRs assessed',data.summary.businessRequirements],['Scenarios assessed',data.summary.scenarios],['Cases assessed',data.summary.testCases],['Physical legs assessed',data.summary.physicalPayloadLegsAssessed],['Hash-verified payload/metadata files',data.summary.physicalFilesHashVerified],['Structural BR→TS→TC→TD states',JSON.stringify(data.summary.structuralChainStatusCounts)],['BR meaning states',JSON.stringify(data.summary.stageStatusCounts.brMeaningStatus)],['TS intent states',JSON.stringify(data.summary.stageStatusCounts.tsIntentStatus)],['TC assertion states',JSON.stringify(data.summary.stageStatusCounts.tcAssertionStatus)],['TC expected-outcome states',JSON.stringify(data.summary.stageStatusCounts.tcExpectedOutcomeStatus)],['TD physical states',JSON.stringify(data.summary.stageStatusCounts.tdPhysicalStatus)],['TD predicate states',JSON.stringify(data.summary.stageStatusCounts.tdPredicateStatus)],['TD dependency states',JSON.stringify(data.summary.stageStatusCounts.tdDependencyStatus)],['Source predicates',JSON.stringify(data.summary.sourcePredicateCounts)],['Curated/un-curated source rules',JSON.stringify(data.summary.sourceGate)],['Recorded SME queries',data.summary.smeQueryCount],['Confirmed complete semantic alignments',data.summary.semanticAlignmentConfirmed],['Semantic coverage',data.summary.semanticCoverage],['SME review',data.summary.smeReview]])row('summary',[label,value]);
$('case-status').replaceChildren(new Option('All structural states',''),...Object.keys(data.summary.structuralChainStatusCounts).map(value=>new Option(value,value)));$('query-kind').replaceChildren(new Option('All SME query kinds',''),...Object.keys(data.summary.smeQueryKinds).map(value=>new Option(value,value)));
let brPage=0,tsPage=0;function listPage(items,search,body,count,pageLabel,prev,next,page,fields){const filtered=items.filter(item=>!search||fields(item).toLowerCase().includes(search));const pages=Math.max(1,Math.ceil(filtered.length/pageSize));page.value=Math.min(page.value,pages-1);$(body).replaceChildren();for(const item of filtered.slice(page.value*pageSize,(page.value+1)*pageSize))row(body,fields(item).split('\t'));$(count).textContent=filtered.length.toLocaleString()+' selected / '+items.length.toLocaleString();$(pageLabel).textContent='Page '+(page.value+1)+' / '+pages;$(prev).disabled=page.value===0;$(next).disabled=page.value===pages-1}function brList(){listPage(data.brs,$('br-search').value.toLowerCase(),'br-body','br-count','br-page','br-prev','br-next',{get value(){return brPage},set value(v){brPage=v}},item=>[item.requirementId,item.sourceRuleId||'Not declared',item.statement||'Not declared',item.caseCount,item.meaningStatus,item.smeQueryIds.join(', ')].join('\t'))}function tsList(){listPage(data.scenarios,$('ts-search').value.toLowerCase(),'ts-body','ts-count','ts-page','ts-prev','ts-next',{get value(){return tsPage},set value(v){tsPage=v}},item=>[item.scenarioId,item.requirementIds.join(', ')||'None',item.intentStatus,item.smeQueryIds.join(', ')].join('\t'))}
function cases(){const search=$('case-search').value.toLowerCase(),status=$('case-status').value;const filtered=data.cases.filter(item=>(!status||item.stageStatuses.structuralChainStatus===status)&&(!search||[item.caseId,item.scenarioId,...item.requirementIds].join(' ').toLowerCase().includes(search)));const pages=Math.max(1,Math.ceil(filtered.length/pageSize));casePage=Math.min(casePage,pages-1);$('case-body').replaceChildren();for(const item of filtered.slice(casePage*pageSize,(casePage+1)*pageSize)){const stage=item.stageStatuses;row('case-body',[item.caseId,item.scenarioId,item.intent,stage.structuralChainStatus,stage.brMeaningStatus,stage.tcAssertionStatus+' / '+stage.tcExpectedOutcomeStatus,stage.tdPhysicalStatus+' / '+stage.tdPredicateStatus,item.queryIds.join(', ')])};$('case-count').textContent=filtered.length.toLocaleString()+' selected / '+data.cases.length.toLocaleString()+' assessed cases';$('case-page').textContent='Page '+(casePage+1)+' / '+pages;$('case-prev').disabled=casePage===0;$('case-next').disabled=casePage===pages-1}
function queries(){const search=$('query-search').value.toLowerCase(),kind=$('query-kind').value;const filtered=data.queries.filter(item=>(!kind||item.kind===kind)&&(!search||[item.queryId,item.subjectId,item.question].join(' ').toLowerCase().includes(search)));const pages=Math.max(1,Math.ceil(filtered.length/pageSize));queryPage=Math.min(queryPage,pages-1);$('query-body').replaceChildren();for(const item of filtered.slice(queryPage*pageSize,(queryPage+1)*pageSize))row('query-body',[item.queryId,item.kind+' / '+item.stage,item.subjectId,item.question,item.status]);$('query-count').textContent=filtered.length.toLocaleString()+' selected / '+data.queries.length.toLocaleString()+' SME queries';$('query-page').textContent='Page '+(queryPage+1)+' / '+pages;$('query-prev').disabled=queryPage===0;$('query-next').disabled=queryPage===pages-1}
for(const id of ['br-search','ts-search'])$(id).addEventListener('input',()=>{if(id==='br-search'){brPage=0;brList()}else{tsPage=0;tsList()}});$('br-prev').onclick=()=>{brPage--;brList()};$('br-next').onclick=()=>{brPage++;brList()};$('ts-prev').onclick=()=>{tsPage--;tsList()};$('ts-next').onclick=()=>{tsPage++;tsList()};for(const id of ['case-search','case-status'])$(id).addEventListener(id==='case-search'?'input':'change',()=>{casePage=0;cases()});for(const id of ['query-search','query-kind'])$(id).addEventListener(id==='query-search'?'input':'change',()=>{queryPage=0;queries()});$('case-prev').onclick=()=>{casePage--;cases()};$('case-next').onclick=()=>{casePage++;cases()};$('query-prev').onclick=()=>{queryPage--;queries()};$('query-next').onclick=()=>{queryPage++;queries()};brList();tsList();cases();queries();
</script></body></html>'''.replace("__DATA__", encoded)
    (output / "FULL-SEMANTIC-CHAIN-REPORT.html").write_text(html, encoding="utf-8")
    text = ["# Whole-Delivery Semantic Chain Assessment", "", "SME review is deferred at user request, not approved. All unresolved questions remain OPEN_REVIEW_DEFERRED.", "", "| Measure | Result |", "|---|---|"]
    text.extend(f"| {key} | {summary[key]} |" for key in ["businessRequirements", "scenarios", "testCases", "physicalPayloadLegsAssessed", "smeQueryCount", "semanticAlignmentConfirmed", "semanticCoverage"])
    text.extend(["", "All populations were assessed for available evidence. Complete interpretation, assertions, dependencies and outcomes remain required for confirmed semantic alignment.", "", "[Filterable report](FULL-SEMANTIC-CHAIN-REPORT.html) | [All SME queries CSV](semantic-sme-query-register.csv) | [Full TC/TD evidence](semantic-tc-td-assessment.json)"])
    (output / "FULL-SEMANTIC-CHAIN-ASSESSMENT.md").write_text("\n".join(text), encoding="utf-8")
    print("Rendered full-population semantic and SME-query report")


def validate(output):
    output = native(output)
    summary = read(output / "semantic-chain-summary.json")
    cases = read(output / "semantic-tc-td-assessment.json")
    brs = read(output / "semantic-br-assessment.json")
    scenarios = read(output / "semantic-ts-assessment.json")
    queries = read(output / "semantic-sme-query-register.json")
    assert len(cases) == summary["testCases"] == 21123
    assert len(brs) == summary["businessRequirements"] == 6887
    assert len(scenarios) == summary["scenarios"] == 12679
    assert len(queries) == summary["smeQueryCount"]
    ids = {item["queryId"] for item in queries}
    assert len(ids) == len(queries) and all(not item["approvalGranted"] for item in queries)
    assert all(set(item["queryIds"]) <= ids and not item["semanticAlignmentConfirmed"] for item in cases)
    assert all(set(item["smeQueryIds"]) <= ids for item in [*brs, *scenarios])
    assert sum(summary["caseDispositionCounts"].values()) == len(cases)
    assert summary["semanticAlignmentConfirmed"] == 0 and not summary["executionCertified"]
    assert all(item["status"] == "OPEN_REVIEW_DEFERRED" for item in queries)
    print(f"PASS: all {len(cases)} case dispositions, {len(brs)} BRs, {len(scenarios)} scenarios and {len(queries)} query references; no deferred approval promoted")


if __name__ == "__main__":
    main()