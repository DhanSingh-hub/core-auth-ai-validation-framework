import argparse
import collections
import csv
import hashlib
import json
import os
import shutil
from html import escape
from pathlib import Path

import ijson


MODULE = Path(__file__).resolve().parents[1]


def read(path):
    return json.loads(path.read_text(encoding="utf-8-sig"))


def digest(path):
    with path.open("rb") as stream:
        return hashlib.file_digest(stream, "sha256").hexdigest()


def native(path):
    path = Path(os.path.normpath(str(path)))
    return Path("\\\\?\\" + str(path.resolve())) if os.name == "nt" and not str(path).startswith("\\\\?\\") else path


def native(path):
    path = Path(os.path.normpath(str(path)))
    return Path("\\\\?\\" + str(path.resolve())) if os.name == "nt" and not str(path).startswith("\\\\?\\") else path


def make_query(kind, subject, stage, question, evidence):
    query_id = "SME-SEM-" + hashlib.sha256((kind + "|" + subject).encode()).hexdigest()[:12].upper()
    return {"queryId": query_id, "kind": kind, "subjectId": subject, "stage": stage,
            "question": question, "evidence": evidence, "status": "OPEN_REVIEW_DEFERRED",
            "reviewOwner": "ATL105-SME", "approvalGranted": False}


def self_test():
    first = make_query("SOURCE", "SEG100-R-001", "BR", "Review", {})
    second = make_query("SOURCE", "SEG100-R-001", "BR", "Review", {})
    assert first == second and not first["approvalGranted"]
    assert native(Path("a/../b")).name == "b"
    print("PASS: stable deferred source-query IDs and path normalization")


def finalize(review, base, output):
    review = native(review)
    base = native(base)
    output = native(output)
    if output.exists():
        raise ValueError("Output must be a new finalization directory; refusing overwrite")
    summary = read(base / "semantic-chain-summary.json")
    brs = read(base / "semantic-br-assessment.json")
    scenarios = read(base / "semantic-ts-assessment.json")
    cases = read(base / "semantic-tc-td-assessment.json")
    queries = read(base / "semantic-sme-query-register.json")
    run = read(review / "complete-handoff-analysis.json")
    archive = Path(run["archive"])
    payload_root = archive / "pipeline_run_artifacts/qe_shaped_test_data"
    with (review / "intake-file-hashes.csv").open(encoding="utf-8-sig", newline="") as stream:
        manifest = {row["path"]: row for row in csv.DictReader(stream)}
    query_by_id = {item["queryId"]: item for item in queries}
    case_by_id = {item["caseId"]: item for item in cases}
    provisional_sources = collections.Counter()
    provisional_fields = unavailable_fields = unresolved_segments = verified_metadata = 0
    for case in cases:
        physical_queries = []
        has_dependency = False
        for leg in case["physicalLegs"]:
            filename = leg["file"]
            payload = payload_root / filename
            metadata_file = payload.with_name(payload.stem + ".meta.json")
            if not payload.is_file() or not metadata_file.is_file():
                leg["metadataEvidenceStatus"] = "PAYLOAD_OR_METADATA_MISSING"
                leg.update({"provisionalFieldCount": 0, "unavailableFieldCount": 0, "unresolvedSegmentCount": 0,
                            "dataSourceCounts": {}})
                continue
            metadata_hash = digest(metadata_file)
            expected_meta_hash = leg.get("inputSha256", {}).get(metadata_file.name)
            original_meta_hash = manifest.get(metadata_file.relative_to(archive).as_posix(), {}).get("sha256")
            if metadata_hash != expected_meta_hash or metadata_hash != original_meta_hash:
                raise ValueError("TD metadata changed from full-scan/source-verified hash: " + metadata_file.name)
            metadata = read(metadata_file)
            if metadata.get("testCaseId") != case["caseId"] or metadata.get("scenarioId") != case["scenarioId"]:
                raise ValueError("TD metadata-to-TC/TS link mismatch: " + filename)
            fields = metadata.get("fields", [])
            sources = collections.Counter(str(field.get("dataSource", "NOT_DECLARED")) for field in fields)
            provisional = [field for field in fields if field.get("provisional") is True]
            unavailable = metadata.get("unavailableFields", [])
            unresolved = metadata.get("dataSections", {}).get("unresolved", {}).get("segments", [])
            provisional_fields += len(provisional)
            unavailable_fields += len(unavailable) if isinstance(unavailable, list) else 0
            unresolved_segments += len(unresolved) if isinstance(unresolved, list) else 0
            provisional_sources.update(sources)
            verified_metadata += 1
            leg.update({"metadataEvidenceStatus": "HASH_VERIFIED_AND_LINKED", "metadataSha256": metadata_hash,
                        "provisionalFieldCount": len(provisional), "provisionalFieldSamples": [
                            {key: field.get(key) for key in ["segment", "element", "specElementName", "dataSource"]} for field in provisional[:8]],
                        "unavailableFieldCount": len(unavailable) if isinstance(unavailable, list) else 0,
                        "unavailableFieldSamples": unavailable[:8] if isinstance(unavailable, list) else [],
                        "unresolvedSegmentCount": len(unresolved) if isinstance(unresolved, list) else 0,
                        "unresolvedSegmentSamples": unresolved[:8] if isinstance(unresolved, list) else [],
                        "dataSourceCounts": dict(sources)})
            if provisional or unavailable or unresolved:
                has_dependency = True
                physical_queries.append({"file": filename, "provisional": leg["provisionalFieldSamples"],
                    "unavailable": leg["unavailableFieldSamples"], "unresolvedSegments": leg["unresolvedSegmentSamples"]})
        dep_query = make_query("TD_METADATA_DEPENDENCY", case["caseId"], "TD",
            "Review physical TD dataSource/provisional metadata, unavailable fields and unresolved companion segments; determine applicability and cross-field dependencies before claiming chain readiness.",
            {"physicalLegs": physical_queries}) if has_dependency else None
        if dep_query:
            query_by_id.setdefault(dep_query["queryId"], dep_query)
            if dep_query["queryId"] not in case["queryIds"]:
                case["queryIds"].append(dep_query["queryId"])
            case["stageStatuses"]["tdDependencyStatus"] = "PROVISIONAL_OR_UNRESOLVED_METADATA_PRESENT"
    source_inventory_file = MODULE / "specifications/ATL105/test-output/test-solution-independent-review/source-derived-requirement-inventory.json"
    source_gate_file = MODULE / "specifications/ATL105/test-output/test-solution-independent-review/atl105-source-backed-rule-gate.json"
    source_file = MODULE / "specifications/ATL105/docs/specs/extracted_text.txt"
    source_inventory = read(source_inventory_file)
    source_gate = read(source_gate_file)
    if digest(source_file) != source_gate["specificationFingerprintSha256"]:
        raise ValueError("Raw ATL105 source no longer matches reviewed source gate fingerprint")
    curated = {item["ruleId"] for item in source_gate.get("assertions", [])}
    independent_rules = []
    for rule in source_inventory["businessRequirements"]:
        is_curated = rule["ruleId"] in curated
        query = make_query("SOURCE_RULE_SCOPE_REVIEW" if is_curated else "INDEPENDENT_BR_MEANING", rule["ruleId"], "INDEPENDENT_BR_BASELINE",
            "Review the partial source-backed assertions; confirm applicability, conditions, obligations, exceptions and cross-section scope. Source-backed candidate is not SME approval."
            if is_curated else
            "Derive and approve the independent atomic BR meaning from the ATL105 source, including applicability, trigger, obligation, permitted values, exceptions and expected behavior. Do not inherit AI wording.",
            {"title": rule.get("title"), "sourceAnchor": rule.get("sourceAnchor"), "sourceCatalog": rule.get("sourceCatalog"),
             "sourceEvidenceResolution": rule.get("sourceEvidenceResolution"), "curatedAssertionCandidate": is_curated})
        query_by_id.setdefault(query["queryId"], query)
        independent_rules.append({**rule, "semanticMeaningStatus": "SOURCE_BACKED_CANDIDATE_REVIEW_REQUIRED" if is_curated else "SOURCE_SEMANTICS_NOT_CURATED",
                                  "smeQueryIds": [query["queryId"]], "approved": False})
    for case in cases:
        case["queryIds"] = sorted(set(case["queryIds"]))
    queries = sorted(query_by_id.values(), key=lambda item: (item["kind"], item["subjectId"], item["queryId"]))
    summary["independentSourceRules"] = len(independent_rules)
    summary["independentSourceRulesCuratedCandidates"] = len(curated)
    summary["independentSourceRulesWithoutCuratedAssertions"] = len(independent_rules) - len(curated)
    summary["physicalMetadataPairsVerifiedInFinalization"] = verified_metadata
    summary["physicalTdProvisionalFields"] = provisional_fields
    summary["physicalTdUnavailableFieldOccurrences"] = unavailable_fields
    summary["physicalTdUnresolvedSegmentOccurrences"] = unresolved_segments
    summary["physicalTdDataSourceCounts"] = dict(provisional_sources)
    summary["smeQueryCount"] = len(queries)
    summary["smeQueryKinds"] = dict(collections.Counter(item["kind"] for item in queries))
    summary["finalizationStatus"] = "FULL_STAGE_RUN_SUPPLEMENTED_WITH_INDEPENDENT_SOURCE_BASELINE_AND_PHYSICAL_METADATA"
    output.mkdir(parents=True)
    (output / "semantic-chain-summary.json").write_text(json.dumps(summary, indent=2, ensure_ascii=True), encoding="utf-8")
    artifacts = [("semantic-independent-source-rule-assessment.json", independent_rules), ("semantic-br-assessment.json", brs),
                 ("semantic-ts-assessment.json", scenarios), ("semantic-tc-td-assessment.json", cases),
                 ("semantic-sme-query-register.json", queries)]
    for name, rows in artifacts:
        (output / name).write_text(json.dumps(rows, indent=2, ensure_ascii=True), encoding="utf-8")
    with (output / "semantic-independent-source-rule-assessment.csv").open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=["ruleId", "title", "class", "severity", "canonicalAnchor", "sourceCatalog", "sourceEvidenceResolution", "semanticMeaningStatus", "smeQueryIds", "approved"])
        writer.writeheader()
        for item in independent_rules:
            writer.writerow({key: json.dumps(item.get(key), ensure_ascii=True) if isinstance(item.get(key), (dict, list)) else item.get(key) for key in writer.fieldnames})
    with (output / "semantic-sme-query-register.csv").open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=["queryId", "kind", "subjectId", "stage", "question", "status", "reviewOwner", "approvalGranted", "evidence"])
        writer.writeheader()
        for item in queries:
            writer.writerow({**item, "evidence": json.dumps(item["evidence"], ensure_ascii=True)})
    (output / "semantic-finalization-inputs.json").write_text(json.dumps({"baseAssessment": str(base),
        "baseSummarySha256": digest(base / "semantic-chain-summary.json"), "independentInventorySha256": digest(source_inventory_file),
        "sourceGateSha256": digest(source_gate_file), "sourceSha256": digest(source_file),
        "metadataFilesVerified": verified_metadata, "reviewStatus": "DEFERRED_NOT_APPROVED"}, indent=2), encoding="utf-8")
    write_report(output, summary, independent_rules, brs, scenarios, cases, queries)
    print(json.dumps({key: summary[key] for key in ["businessRequirements", "scenarios", "testCases", "physicalPayloadLegsAssessed",
        "physicalFilesHashVerified", "structuralChainStatusCounts", "stageStatusCounts", "independentSourceRules",
        "independentSourceRulesCuratedCandidates", "independentSourceRulesWithoutCuratedAssertions", "physicalMetadataPairsVerifiedInFinalization",
        "smeQueryCount", "smeQueryKinds", "semanticAlignmentConfirmed", "semanticCoverage", "executionCertified"]}, indent=2))


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("review_directory", nargs="?", type=Path)
    parser.add_argument("base_assessment", nargs="?", type=Path)
    parser.add_argument("output_directory", nargs="?", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
    elif args.review_directory and args.base_assessment and args.output_directory:
        finalize(args.review_directory, args.base_assessment, args.output_directory)
    else:
        parser.error("review_directory, base_assessment and new output_directory required")


if __name__ == "__main__":
    main()def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("review_directory", nargs="?", type=Path)
    parser.add_argument("base_assessment", nargs="?", type=Path)
    parser.add_argument("output_directory", nargs="?", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
    elif args.review_directory and args.base_assessment and args.output_directory:
        finalize(args.review_directory, args.base_assessment, args.output_directory)
    else:
        parser.error("review_directory, base_assessment and new output_directory required")


if __name__ == "__main__":
    main()