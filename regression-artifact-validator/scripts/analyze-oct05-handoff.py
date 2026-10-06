import argparse
import collections
import csv
import hashlib
import json
import os
from pathlib import Path

import ijson


def load(path):
    with path.open(encoding="utf-8-sig") as stream:
        return json.load(stream)


def digest(path):
    with path.open("rb") as stream:
        return hashlib.file_digest(stream, "sha256").hexdigest()


def records(path, key):
    with path.open("rb") as stream:
        yield from ijson.items(stream, key + ".item", use_float=True)


def walk(node):
    if isinstance(node, dict):
        yield node
        for value in node.values():
            yield from walk(value)
    elif isinstance(node, list):
        for value in node:
            yield from walk(value)


def linked_requirements(row):
    values = row.get("requirement_ids") or []
    if isinstance(values, str):
        values = [values]
    if row.get("requirement_id"):
        values = list(values) + [row["requirement_id"]]
    return sorted(set(values))


def assess_missing_scenarios(archive, output):
    pipeline = archive / "pipeline_run_artifacts"
    catalogs = archive / "scenerio_req_5_oct" / "scenerio_req_5_oct"
    scenario_file = catalogs / "scenarios" / "approved" / "approved_scenarios.json"
    requirement_file = catalogs / "step5_requirements" / "approved" / "requirement_catalog.json"
    case_file = pipeline / "test_case_candidates.json"
    skips = load(pipeline / "composer_skips.json")
    unresolved = load(pipeline / "unresolved_scenarios.json")
    failures = load(pipeline / "write_failures.json")
    scenarios = {row["id"]: row for row in records(scenario_file, "scenarios")}
    requirements = {row["id"]: row for row in records(requirement_file, "requirements")}
    case_counts = collections.Counter()
    requirement_case_counts = collections.Counter()
    case_ids = set()
    for case in records(case_file, "test_cases"):
        case_counts[case["scenario_id"]] += 1
        requirement_case_counts.update(linked_requirements(case))
        if case["id"] in case_ids:
            raise RuntimeError("Duplicate testcase ID " + case["id"])
        case_ids.add(case["id"])
    if set(case_counts) - set(scenarios):
        raise RuntimeError("TC catalog contains unknown scenario IDs")
    missing = set(scenarios) - set(case_counts)
    unresolved_ids = set(skips["skippedNoConsistentTransaction"])
    unresolved_records = {row["id"]: row for row in unresolved["scenarios"]}
    unresolved_details = {row["scenario_id"]: row for row in skips["noConsistentTransactionDetails"]}
    deferred = collections.defaultdict(list)
    for row in skips["deferredResponseSide"]:
        deferred[row["scenario_id"]].append(row)
    other_skip_ids = set(skips.get("skippedIncompleteFlow", [])) | set(skips.get("skippedDuplicateFingerprint", []))
    affected_requirements = set()
    groups = collections.defaultdict(list)
    rows = []
    for scenario_id in sorted(missing):
        source = scenarios[scenario_id]
        prioritized = unresolved_records.get(scenario_id, {})
        requirement_ids = linked_requirements(source)
        if scenario_id in unresolved_ids and scenario_id in deferred:
            category = "UNRESOLVED_AND_RESPONSE_DEFERRED"
        elif scenario_id in unresolved_ids:
            category = "NO_CONSISTENT_TRANSACTION"
        elif scenario_id in deferred:
            category = "RESPONSE_SIDE_DEFERRED"
        elif scenario_id in other_skip_ids:
            category = "OTHER_DECLARED_SKIP"
        else:
            category = "UNEXPLAINED"
        flags = source.get("flags") or []
        verdict = source.get("llm_verdict") or {}
        if isinstance(verdict, dict):
            verdict = verdict.get("verdict", "NOT_DECLARED")
        detail = unresolved_details.get(scenario_id, {})
        unresolved_names = detail.get("unresolved_parameters") or []
        affected_requirements.update(requirement_ids)
        row = {
            "scenarioId": scenario_id,
            "category": category,
            "testCaseCount": 0,
            "name": source.get("name", ""),
            "scenarioType": source.get("type", source.get("scenario_type", "NOT_DECLARED")),
            "requirementIds": requirement_ids,
            "unknownRequirementIds": sorted(set(requirement_ids) - set(requirements)),
            "requirementIdsWithoutAnyDirectTc": [value for value in requirement_ids if not requirement_case_counts[value]],
            "targetTransaction": source.get("target_transaction"),
            "sourceRuleId": source.get("source_rule_id"),
            "sourcePage": source.get("source_page"),
            "confidenceBand": source.get("confidence_band", "NOT_DECLARED"),
            "confidencePercent": source.get("confidence_pct"),
            "producerVerdict": verdict,
            "producerSuggestedAction": (source.get("review_package") or {}).get("suggested_action"),
            "flags": flags,
            "expectedResponseCode": source.get("expected_response_code"),
            "expectedDeclineCode": source.get("expected_decline_code"),
            "producerAssertion": source.get("business_rule_assertion"),
            "oracleAuthority": source.get("oracle_authority"),
            "priority": prioritized.get("priority", source.get("priority")),
            "resolutionFlags": detail.get("flags", []),
            "discriminatingParameters": detail.get("discriminating_parameters", []),
            "ubiquitousParameters": detail.get("ubiquitous_parameters", []),
            "unresolvedParameters": unresolved_names,
            "deferredResponsePairs": deferred.get(scenario_id, []),
            "requiresExternalBehaviorEvidence": any("AUTHORIZER" in str(flag) or "PROCESSOR" in str(flag) for flag in flags),
            "independentDisposition": "NOT_ASSESSED",
            "nextAction": ("Ground message/transaction applicability in source; resolve unknown parameters; rerun composer without invented behavior"
                           if category == "NO_CONSISTENT_TRANSACTION" else
                           "Declare response-generation scope; author source-grounded response TC and oracle or retain explicit deferred disposition"
                           if category == "RESPONSE_SIDE_DEFERRED" else
                           "Investigate and independently disposition generation evidence"),
        }
        rows.append(row)
        groups[category].append(row)
    breakdowns = {}
    for category, group in groups.items():
        flag_counts = collections.Counter()
        deferred_families = collections.Counter()
        segments = collections.Counter()
        for row in group:
            flag_counts.update(set(str(flag).split(":", 1)[0] for flag in row["flags"]))
            deferred_families.update(pair["transaction_type"] for pair in row["deferredResponsePairs"])
            segment_values = set()
            for requirement_id in row["requirementIds"]:
                requirement = requirements.get(requirement_id, {})
                segment = requirement.get("segment_number")
                if segment:
                    segment_values.add(str(segment))
                segment_values.update(str(value) for value in requirement.get("segment_ids", []) if value)
            segments.update(segment_values or {"UNASSIGNED"})
        breakdowns[category] = {
            "count": len(group),
            "scenarioTypes": dict(collections.Counter(row["scenarioType"] for row in group)),
            "confidenceBands": dict(collections.Counter(row["confidenceBand"] for row in group)),
            "producerVerdicts": dict(collections.Counter(str(row["producerVerdict"]) for row in group)),
            "topFlagCounts": dict(flag_counts.most_common(15)),
            "missingSourcePage": sum(row["sourcePage"] is None for row in group),
            "missingSourceRuleId": sum(not row["sourceRuleId"] for row in group),
            "noBrLinks": sum(not row["requirementIds"] for row in group),
            "unknownBrLinks": sum(bool(row["unknownRequirementIds"]) for row in group),
            "noTargetTransaction": sum(not row["targetTransaction"] for row in group),
            "externalBehaviorEvidenceRequired": sum(row["requiresExternalBehaviorEvidence"] for row in group),
            "emptyDiscriminatingParameters": sum(not row["discriminatingParameters"] for row in group),
            "withUnresolvedParameters": sum(bool(row["unresolvedParameters"]) for row in group),
            "emptyExpectedResponseCode": sum(row["expectedResponseCode"] is None for row in group),
            "deferredPairCount": sum(len(row["deferredResponsePairs"]) for row in group),
            "deferredFamiliesPairCounts": dict(deferred_families),
            "deferredDirectionBasisPairCounts": dict(collections.Counter(
                pair.get("direction_basis", "NOT_DECLARED") for row in group for pair in row["deferredResponsePairs"])),
            "brDeclaredSegmentCountsNonAdditive": dict(segments),
            "examples": [{key: row[key] for key in ("scenarioId", "name", "scenarioType", "sourceRuleId", "sourcePage", "producerVerdict", "nextAction")}
                         for row in group[:5]],
        }
    failure_ids = {row["test_case_id"] for row in failures["composition_failures"]}
    failure_scenarios = {row["scenario_id"] for row in failures["composition_failures"]}
    summary = {
        "scope": "Missing TC generation causes, not independent semantic acceptance or executable coverage",
        "inputSha256": {file.name: digest(file) for file in (scenario_file, requirement_file, case_file,
                            pipeline / "composer_skips.json", pipeline / "unresolved_scenarios.json", pipeline / "write_failures.json")},
        "scenarioCount": len(scenarios), "testCaseCount": len(case_ids),
        "scenariosWithTc": len(case_counts), "scenariosWithoutTc": len(rows),
        "missingPercent": round(100 * len(rows) / len(scenarios), 2),
        "categories": {category: len(group) for category, group in groups.items()},
        "allMissingExplainedByProducer": set(groups) <= {"NO_CONSISTENT_TRANSACTION", "RESPONSE_SIDE_DEFERRED"},
        "unresolvedResolverBypassed": unresolved.get("llm_transaction_resolver_bypassed"),
        "unresolvedIdsMatchPrioritizedRecords": unresolved_ids == set(unresolved_records),
        "totalDeferredPairs": len(skips["deferredResponseSide"]),
        "totalDistinctDeferredScenarios": len(deferred),
        "deferredScenariosAlreadyWithTc": len(set(deferred) & set(case_counts)),
        "writeFailureCaseCount": len(failure_ids),
        "writeFailureCasesPresentInCandidateCatalog": len(failure_ids & case_ids),
        "writeFailureScenariosWithoutTc": len(failure_scenarios & missing),
        "affectedDistinctBrIds": len(affected_requirements),
        "affectedBrIdsWithOtherDirectTc": sum(bool(requirement_case_counts[value]) for value in affected_requirements),
        "affectedBrIdsWithoutAnyDirectTc": sum(not requirement_case_counts[value] for value in affected_requirements),
        "independentDisposition": "NOT_ASSESSED", "executionCertified": False,
        "groupDetails": breakdowns,
    }
    output.mkdir(parents=True, exist_ok=True)
    (output / "scenario-no-tc-assessment.json").write_text(json.dumps(summary, indent=2, ensure_ascii=True), encoding="utf-8")
    (output / "scenario-no-tc-register.json").write_text(json.dumps(rows, indent=2, ensure_ascii=True), encoding="utf-8")
    with (output / "scenario-no-tc-register.csv").open("w", newline="", encoding="utf-8") as stream:
        fields = list(rows[0]) if rows else ["scenarioId", "category"]
        writer = csv.DictWriter(stream, fieldnames=fields)
        writer.writeheader()
        for row in rows:
            writer.writerow({key: json.dumps(value, ensure_ascii=True) if isinstance(value, (dict, list)) else value
                             for key, value in row.items()})
    if len(rows) != 3732 or summary["categories"] != {"NO_CONSISTENT_TRANSACTION": 2026, "RESPONSE_SIDE_DEFERRED": 1706}:
        raise RuntimeError("Known handoff missing-TC partition changed; inspect evidence before publishing")
    assert summary["unresolvedIdsMatchPrioritizedRecords"]
    assert summary["writeFailureScenariosWithoutTc"] == 0
    assert sum(summary["categories"].values()) == summary["scenariosWithoutTc"]
    print(json.dumps(summary, indent=2))


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("archive", type=Path)
    parser.add_argument("source", type=Path)
    parser.add_argument("output", type=Path)
    parser.add_argument("--missing-scenarios-only", action="store_true")
    args = parser.parse_args()
    if os.name == "nt":
        for field in ("archive", "source", "output"):
            path = getattr(args, field).resolve()
            if not str(path).startswith("\\\\?\\"):
                setattr(args, field, Path("\\\\?\\" + str(path)))
    if args.missing_scenarios_only:
        assess_missing_scenarios(args.archive, args.output)
        return
    args.output.mkdir(parents=True, exist_ok=True)
    pipeline = args.archive / "pipeline_run_artifacts"
    catalogs = args.archive / "scenerio_req_5_oct" / "scenerio_req_5_oct"
    payload_root = pipeline / "qe_shaped_test_data"
    catalog = pipeline / "test_case_candidates.json"

    inventory = []
    mismatches = []
    for file in sorted(args.archive.rglob("*")):
        if not file.is_file():
            continue
        relative = file.relative_to(args.archive)
        original = args.source / relative
        archived_hash = digest(file)
        matches = original.is_file() and digest(original) == archived_hash
        if not matches:
            mismatches.append(relative.as_posix())
        inventory.append({"path": relative.as_posix(), "bytes": file.stat().st_size,
                          "sha256": archived_hash, "sourceCopyVerified": matches})
    original_paths = {file.relative_to(args.source).as_posix() for file in args.source.rglob("*") if file.is_file()}
    archived_paths = {row["path"] for row in inventory}
    mismatches.extend(sorted(original_paths - archived_paths))
    with (args.output / "intake-file-hashes.csv").open("w", newline="", encoding="utf-8") as stream:
        writer = csv.DictWriter(stream, fieldnames=["path", "bytes", "sha256", "sourceCopyVerified"])
        writer.writeheader()
        writer.writerows(inventory)
    if mismatches:
        raise RuntimeError("Archive verification failed: " + str(mismatches[:10]))

    requirements = list(records(catalogs / "step5_requirements" / "approved" / "requirement_catalog.json", "requirements"))
    scenarios = list(records(catalogs / "scenarios" / "approved" / "approved_scenarios.json", "scenarios"))
    requirement_ids = {row.get("id") or row.get("requirement_id") for row in requirements}
    scenario_ids = {row.get("id") or row.get("scenario_id") for row in scenarios}
    if not requirements or not scenarios:
        raise RuntimeError("Unexpected BR/TS catalog shape")

    summary = {"analysisScope": "Physical intake, JSON parsing, producer graph and quality metadata; not semantic acceptance",
               "disposition": "REVIEW_REQUIRED", "executionCertified": False,
               "independentCoverage": "NOT_CALCULABLE_NO_SAME_RUN_EVIDENCE_QUALIFIED_CROSSWALK",
               "archive": str(args.archive), "source": str(args.source),
               "intake": {"files": len(inventory), "bytes": sum(row["bytes"] for row in inventory),
                          "allSourceCopyHashesVerified": True,
                          "duplicateCandidateCatalogHashesEqual": digest(catalog) == digest(args.archive / "test_case_candidates.json")},
               "businessRequirements": len(requirements), "scenarios": len(scenarios)}
    with catalog.open("rb") as stream:
        header = {}
        for prefix, event, value in ijson.parse(stream, use_float=True):
            if prefix == "test_cases" and event == "start_array":
                break
            if prefix and "." not in prefix and event in ("string", "number", "boolean", "null"):
                header[prefix] = value
    summary["producerCaseHeader"] = header
    case_ids = set()
    covered_scenarios = set()
    covered_requirements = set()
    counters = collections.Counter()
    types = collections.Counter()
    transactions = collections.Counter()
    key_presence = collections.Counter()
    method_counts = collections.Counter()
    field_flags = collections.Counter()
    quality = collections.Counter()
    confidence = collections.Counter()
    completeness = collections.Counter()
    requirement_case_counts = collections.Counter()
    scenario_case_counts = collections.Counter()
    declared_files = set()
    missing_declared_files = []
    missing_rows = []
    first_case = None
    for case in records(catalog, "test_cases"):
        if first_case is None:
            first_case = case
        counters["records"] += 1
        case_id = case.get("id")
        counters["duplicateCaseIds"] += case_id in case_ids
        case_ids.add(case_id)
        scenario_id = case.get("scenario_id")
        covered_scenarios.add(scenario_id)
        scenario_case_counts[scenario_id] += 1
        counters["missingScenarioLinks"] += scenario_id not in scenario_ids
        br_ids = case.get("requirement_ids") or ([case["requirement_id"]] if case.get("requirement_id") else [])
        counters["casesWithoutBrLinks"] += not br_ids
        for br_id in br_ids:
            covered_requirements.add(br_id)
            requirement_case_counts[br_id] += 1
            counters["danglingBrReferences"] += br_id not in requirement_ids
        types[str(case.get("scenario_type"))] += 1
        transactions[str(case.get("transaction_type"))] += 1
        key_presence.update(case.keys())
        completeness[str(case.get("completeness_status"))] += 1
        confidence[str(case.get("confidence_band"))] += 1
        quality["emptyExpectedResponse"] += not case.get("expected_response")
        quality["missingCaseSourceRuleId"] += not case.get("source_rule_id")
        quality["missingCaseSourcePage"] += case.get("source_page") is None
        quality["casesWithFlags"] += bool(case.get("flags"))
        quality["casesWithCompletenessFindings"] += bool(case.get("completeness_findings"))
        case_methods = set()
        legs = case.get("test_data_leg_files") or []
        if isinstance(legs, dict):
            legs = list(legs.values())
        requested_files = []
        if case.get("test_data_file"):
            requested_files.append(case["test_data_file"])
        requested_files.extend(leg for leg in legs if isinstance(leg, str))
        quality["flowCases"] += bool(case.get("is_flow"))
        for requested in requested_files:
            normalized = requested.replace("\\", "/")
            filename = normalized.rsplit("/", 1)[-1]
            declared_files.add(filename)
            if not (payload_root / filename).is_file():
                missing_declared_files.append({"caseId": case_id, "file": requested})
        payload_exists = (payload_root / (str(case_id) + ".json")).is_file()
        meta_exists = (payload_root / (str(case_id) + ".meta.json")).is_file()
        counters["casesWithBasePayloadFile"] += payload_exists
        counters["casesWithBaseMetadataFile"] += meta_exists
        if not payload_exists or not meta_exists:
            missing_rows.append({"caseId": case_id, "scenarioId": scenario_id,
                                 "scenarioType": case.get("scenario_type"), "payloadExists": payload_exists,
                                 "metadataExists": meta_exists})
        for node in walk(case):
            if "method" in node:
                method_counts[str(node["method"])] += 1
                case_methods.add(str(node["method"]))
            for key in ("sourceAnchors", "sourceAnchor", "expected_response", "verification", "response", "flow_steps"):
                if key in node:
                    field_flags[key] += 1
        for method in ("placeholder", "awaiting_client_value", "spec_unspecified_code", "llm_plausible_value", "derived_at_wire_encoding"):
            quality["casesWithMethod:" + method] += method in case_methods
    summary["caseReconciliation"] = dict(counters)
    summary["caseReconciliation"].update({"uniqueIds": len(case_ids),
        "linkedDistinctScenarios": len(covered_scenarios & scenario_ids),
        "scenariosWithoutCase": len(scenario_ids - covered_scenarios),
        "linkedDistinctRequirements": len(covered_requirements & requirement_ids),
        "requirementsWithoutCase": len(requirement_ids - covered_requirements)})
    summary["scenarioTypes"] = dict(types)
    summary["transactions"] = dict(transactions)
    summary["caseFieldPresence"] = dict(key_presence)
    summary["nestedFieldPresence"] = dict(field_flags)
    summary["fieldFillMethods"] = dict(method_counts)
    summary["caseQuality"] = dict(quality)
    summary["caseConfidenceBands"] = dict(confidence)
    summary["caseCompletenessStatuses"] = dict(completeness)
    summary["declaredFileReferences"] = {"uniqueFiles": len(declared_files), "missingReferences": missing_declared_files}
    for filename, values, fieldnames in (
        ("br-downstream-inventory.csv", [{"requirementId": row.get("id") or row.get("requirement_id"),
           "testCaseCount": requirement_case_counts[row.get("id") or row.get("requirement_id")],
           "independentDisposition": "NOT_ASSESSED"} for row in requirements],
         ["requirementId", "testCaseCount", "independentDisposition"]),
        ("scenario-downstream-inventory.csv", [{"scenarioId": row.get("id") or row.get("scenario_id"),
           "testCaseCount": scenario_case_counts[row.get("id") or row.get("scenario_id")]} for row in scenarios],
         ["scenarioId", "testCaseCount"])):
        with (args.output / filename).open("w", newline="", encoding="utf-8") as stream:
            writer = csv.DictWriter(stream, fieldnames=fieldnames)
            writer.writeheader()
            writer.writerows(values)
    with (args.output / "case-file-gaps.csv").open("w", newline="", encoding="utf-8") as stream:
        writer = csv.DictWriter(stream, fieldnames=["caseId", "scenarioId", "scenarioType", "payloadExists", "metadataExists"])
        writer.writeheader()
        writer.writerows(missing_rows)
    (args.output / "first-case-structure.json").write_text(json.dumps(first_case, indent=2, ensure_ascii=True), encoding="utf-8")

    audit_counts = collections.Counter()
    audit_cases = collections.defaultdict(set)
    for finding in records(pipeline / "test_case_audit_report.json", "findings"):
        key = str(finding.get("level")) + ":" + str(finding.get("code"))
        audit_counts[key] += 1
        if finding.get("test_case_id"):
            audit_cases[key].add(finding["test_case_id"])
    summary["producerAudit"] = {"findingCounts": dict(audit_counts), "totalFindings": sum(audit_counts.values()),
        "distinctCasesWithFindings": len(set().union(*audit_cases.values())) if audit_cases else 0,
        "distinctCasesByFinding": {key: len(value) for key, value in audit_cases.items()},
        "authority": "Producer self-audit, not independent semantic verdict"}
    failures = load(pipeline / "write_failures.json")
    skips = load(pipeline / "composer_skips.json")
    unresolved = load(pipeline / "unresolved_scenarios.json")
    summary["writeFailures"] = {key: len(value) if isinstance(value, list) else value
                                for key, value in failures.items()}
    summary["composerSkips"] = {key: len(value) if isinstance(value, list) else value
                                for key, value in skips.items()}
    summary["unresolvedShape"] = {key: len(value) if isinstance(value, (list, dict)) else value
                                  for key, value in unresolved.items()}
    traceability = load(payload_root / "traceability.json")
    summary["producerPayloadTraceability"] = {key: value for key, value in traceability.items()
                                             if key not in ("files", "test_cases", "testCases", "entries", "traceability")}
    trace_rows = traceability.get("testCases", [])
    trace_ids = {row.get("testCaseId") for row in trace_rows}
    trace_files = {name for row in trace_rows for name in row.get("dataFiles", [])}
    summary["traceabilityReconciliation"] = {"caseRows": len(trace_rows), "uniqueCaseIds": len(trace_ids),
        "casesAbsentFromTraceability": sorted(case_ids - trace_ids),
        "unknownTraceabilityCaseIds": sorted(trace_ids - case_ids),
        "uniquePayloadPaths": len(trace_files),
        "missingPayloadPaths": sorted(name for name in trace_files if not (payload_root / name).is_file())}

    payload_counts = collections.Counter()
    roots = collections.Counter()
    sources = collections.Counter()
    metadata_flags = collections.Counter()
    segment_counts = collections.Counter()
    malformed = []
    for path in sorted(payload_root.glob("*.json")):
        if path.name == "traceability.json":
            continue
        try:
            data = load(path)
            if path.name.endswith(".meta.json"):
                payload_counts["metadataFiles"] += 1
                metadata_flags["missingSourceRuleId"] += not data.get("sourceRuleId")
                metadata_flags["missingSourcePage"] += data.get("sourcePage") is None
                metadata_flags["missingScenarioId"] += not data.get("scenarioId")
                metadata_flags["dataSectionUnresolved"] += bool(data.get("dataSections", {}).get("unresolved"))
                for field in data.get("fields", []):
                    sources[str(field.get("dataSource"))] += 1
            else:
                payload_counts["payloadFiles"] += 1
                if isinstance(data, dict):
                    roots.update(data.keys())
                    for node in walk(data):
                        segment = node.get("SegmentType", node.get("Segment Type"))
                        if segment is not None:
                            segment_counts[str(segment)] += 1
                payload_counts["baseCasePayloads"] += path.stem in case_ids
                payload_counts["nonBasePayloads"] += path.stem not in case_ids
        except (ValueError, UnicodeError, TypeError, AttributeError) as error:
            malformed.append({"file": path.name, "error": str(error)})
    summary["physicalPayloads"] = dict(payload_counts)
    summary["physicalPayloads"]["malformedJsonFiles"] = malformed
    summary["payloadRootFamilies"] = dict(roots)
    summary["payloadSegments"] = dict(segment_counts)
    summary["metadataSourceFlags"] = dict(metadata_flags)
    summary["metadataDataSources"] = dict(sources)
    summary["pipelineStatus"] = (pipeline / "remaining_pipeline_status.txt").read_text(encoding="utf-8-sig").strip()
    (args.output / "complete-handoff-analysis.json").write_text(json.dumps(summary, indent=2, ensure_ascii=True, default=str), encoding="utf-8")
    print(json.dumps({key: summary[key] for key in ("intake", "businessRequirements", "scenarios", "caseReconciliation",
          "physicalPayloads", "metadataSourceFlags", "producerAudit", "pipelineStatus")}, indent=2))


if __name__ == "__main__":
    main()