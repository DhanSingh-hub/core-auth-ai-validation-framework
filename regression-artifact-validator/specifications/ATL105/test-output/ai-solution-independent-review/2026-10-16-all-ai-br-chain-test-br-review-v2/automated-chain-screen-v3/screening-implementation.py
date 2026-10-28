import argparse
import collections
import csv
import gzip
import hashlib
import json
import os
import re
from pathlib import Path

import ijson


def sha256(path):
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def read_json(path):
    return json.loads(path.read_text(encoding="utf-8-sig"))


def jsonl(path):
    with path.open(encoding="utf-8") as stream:
        for line in stream:
            if line.strip():
                yield json.loads(line)


def normalize(value):
    return re.sub(r"[^a-z0-9]", "", str(value).lower())


def metadata_claims(value, location=""):
    claims = []
    if isinstance(value, dict):
        for name, child in value.items():
            child_path = location + "/" + name
            if isinstance(child, dict) and "element_no" in child:
                reference = child.get("data_ref") or {}
                element = str(child.get("element_no") or "")
                if element.isdigit():
                    element = str(int(element))
                claims.append({"element": element, "segment": str(reference.get("segment") or "").upper(),
                               "field": name, "path": child_path, "value": child.get("value")})
            else:
                claims.extend(metadata_claims(child, child_path))
    elif isinstance(value, list):
        for index, child in enumerate(value):
            claims.extend(metadata_claims(child, location + "/" + str(index)))
    return claims


def physical_fields(value, location="", segment=""):
    fields = []
    if isinstance(value, dict):
        for name, child in value.items():
            if normalize(name) == "segmenttype" and not isinstance(child, (dict, list)):
                segment = str(child).upper()
        for name, child in value.items():
            child_path = location + "/" + name
            if isinstance(child, (dict, list)):
                fields.extend(physical_fields(child, child_path, segment))
            else:
                fields.append({"segment": segment, "field": name, "value": child, "path": child_path})
    elif isinstance(value, list):
        for index, child in enumerate(value):
            fields.extend(physical_fields(child, location + "/" + str(index), segment))
    return fields


def compare_claims(claims, fields):
    lookup = collections.defaultdict(list)
    for field in fields:
        lookup[(field["segment"], normalize(field["field"]))].append(field)
    observations = []
    for claim in claims:
        matches = lookup.get((claim["segment"], normalize(claim["field"])), []) if claim["segment"] else []
        if normalize(claim["field"]) == "segmenttype":
            state = "IDENTITY_FIELD_REQUIRES_CONTAINER_BINDING"
        elif isinstance(claim["value"], (dict, list)):
            state = "COMPOSITE_METADATA_NOT_COMPARED"
        elif len(matches) > 1:
            state = "AMBIGUOUS_PHYSICAL_FIELD_SCOPE"
        elif not matches:
            state = "DECLARED_OMISSION_NO_EXACT_FIELD" if claim["value"] is None else "UNRESOLVED_FIELD_OR_ALIAS"
        elif any(type(field["value"]) is type(claim["value"]) and field["value"] == claim["value"] for field in matches):
            state = "EXACT_VALUE_OBSERVED"
        else:
            state = "VALUE_DISAGREEMENT_REVIEW_REQUIRED"
        observations.append({**claim, "state": state, "physicalCandidates": matches})
    return observations


def write_csv(path, rows):
    with path.open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=list(rows[0]))
        writer.writeheader()
        for row in rows:
            writer.writerow({key: json.dumps(value, ensure_ascii=True) if isinstance(value, (list, dict)) else value
                             for key, value in row.items()})


def self_test():
    claims = metadata_claims({"Standard Segment": {"Sequence Number": {
        "element_no": "086", "value": "000001", "data_ref": {"segment": "100"}}}})
    fields = physical_fields({"Standard Segment": {"SegmentType": "100", "SequenceNumber": "000001"}})
    assert claims[0]["element"] == "86"
    assert compare_claims(claims, fields)[0]["state"] == "EXACT_VALUE_OBSERVED"
    fields[1]["value"] = "000002"
    assert compare_claims(claims, fields)[0]["state"] == "VALUE_DISAGREEMENT_REVIEW_REQUIRED"
    assert compare_claims(claims, [dict(fields[1], segment="109")])[0]["state"] == "UNRESOLVED_FIELD_OR_ALIAS"
    assert compare_claims([dict(claims[0], value=None)], [])[0]["state"] == "DECLARED_OMISSION_NO_EXACT_FIELD"
    assert compare_claims([dict(claims[0], value=1)], [dict(fields[1], value="1")])[0]["state"] == "VALUE_DISAGREEMENT_REVIEW_REQUIRED"
    assert compare_claims(claims, [fields[1], fields[1]])[0]["state"] == "AMBIGUOUS_PHYSICAL_FIELD_SCOPE"
    assert compare_claims([dict(claims[0], field="Segment Type")], fields)[0]["state"] == "IDENTITY_FIELD_REQUIRES_CONTAINER_BINDING"
    print("PASS: exact values, strict scalar types, segment isolation, omission, repeated-container and identity guards")


def run(review_dir, audit_dir, output_dir):
    csv.field_size_limit(100000000)
    if os.name == "nt" and not str(output_dir).startswith("\\\\?\\"):
        output_dir = Path("\\\\?\\" + str(output_dir.resolve()))
    if output_dir.exists():
        raise ValueError("Refusing to overwrite output directory")
    summary_path = review_dir / "summary.json"
    register_path = review_dir / "all-ai-br-review-register.csv"
    pairs_path = review_dir / "ai-test-br-ranked-review-leads.csv"
    review_manifest = read_json(review_dir / "manifest.json")
    for name in ["summary.json", "all-ai-br-review-register.csv", "ai-test-br-ranked-review-leads.csv", "review-progress.csv"]:
        if sha256(review_dir / name) != review_manifest["artifacts"][name]:
            raise ValueError("Review artifact hash mismatch: " + name)
    review_summary = read_json(summary_path)
    frozen_summary_path = audit_dir / "summary.json"
    frozen_summary = read_json(frozen_summary_path)
    root = Path(frozen_summary["runRoot"])
    inventory_path = audit_dir / "file-inventory.jsonl"
    physical_path = audit_dir / "tc-physical-assessment.jsonl"
    case_path = root / "test_generation/test_generation/candidates/test_case_candidates.json"
    paths = [summary_path, register_path, pairs_path, review_dir / "manifest.json",
             review_dir / "review-progress.csv", frozen_summary_path, inventory_path, physical_path,
             case_path, Path(__file__).resolve()]
    hashes = {str(path.resolve()): sha256(path) for path in paths}
    if sha256(inventory_path) != frozen_summary["fileInventorySha256"]:
        raise ValueError("Frozen file inventory hash mismatch")
    if sha256(physical_path) != frozen_summary["outputHashes"][physical_path.name]:
        raise ValueError("Frozen physical ledger hash mismatch")
    inventory = {row["path"]: row for row in jsonl(inventory_path)}
    if sha256(case_path) != inventory[case_path.relative_to(root).as_posix()]["sha256"]:
        raise ValueError("Candidate case catalog differs from frozen inventory")
    with register_path.open(encoding="utf-8-sig", newline="") as stream:
        register = list(csv.DictReader(stream))
    if len(register) != 6880 or len({row["aiBrId"] for row in register}) != 6880:
        raise ValueError("Expected one row for every AI BR")
    relevant_cases = {identifier for row in register for identifier in json.loads(row["aiCaseIds"])}
    metadata = {}
    with case_path.open("rb") as stream:
        for case in ijson.items(stream, "test_cases.item", use_float=True):
            if case["id"] in relevant_cases:
                expected = case.get("expected_response")
                response_code = expected.get("response_code", {}) if isinstance(expected, dict) else {}
                metadata[case["id"]] = {"claims": metadata_claims(case.get("request") or {}),
                    "scenarioId": case.get("scenario_id"), "scenarioType": case.get("scenario_type"),
                    "violatedElementName": case.get("violated_element_name"),
                    "negativeClass": case.get("negative_class"),
                    "declaredResponseCode": response_code.get("value"),
                    "declaredOracleTier": response_code.get("tier"),
                    "expectedResponsePresent": expected is not None}
    routes = collections.defaultdict(list)
    for row in jsonl(physical_path):
        if row["id"] in relevant_cases:
            routes[row["id"]].append(row)
    output_dir.mkdir(parents=True)
    counts = collections.Counter()
    case_results = {}
    with gzip.open(output_dir / "case-field-observations.jsonl.gz", "wt", encoding="utf-8") as stream:
        for identifier in sorted(relevant_cases):
            case = metadata.get(identifier)
            path_results = []
            if case is not None:
                payload_paths = sorted({path for route in routes[identifier] for path in route.get("paths", [])})
                for relative in payload_paths:
                    frozen = inventory.get(relative)
                    if not frozen:
                        counts["pathsAbsentFromFrozenInventory"] += 1
                        path_results.append({"path": relative, "state": "ABSENT_FROM_FROZEN_INVENTORY"})
                        continue
                    path = root / relative
                    if not path.is_file():
                        counts["frozenPathsCurrentlyUnavailable"] += 1
                        path_results.append({"path": relative, "state": "FROZEN_PATH_CURRENTLY_UNAVAILABLE"})
                        continue
                    digest = sha256(path)
                    if digest != frozen["sha256"]:
                        raise ValueError("Physical input changed: " + relative)
                    observations = compare_claims(case["claims"], physical_fields(read_json(path)))
                    counts["physicalPayloadsHashVerified"] += 1
                    counts.update("field_" + observation["state"] for observation in observations)
                    states = collections.Counter(observation["state"] for observation in observations)
                    path_results.append({"path": relative, "sha256": digest, "state": "HASH_VERIFIED_OBSERVATIONS_ONLY",
                                         "fieldStates": dict(states), "observations": observations})
            case_results[identifier] = {"metadata": case, "paths": [
                {key: value for key, value in row.items() if key != "observations"} for row in path_results]}
            stream.write(json.dumps({"caseId": identifier, "metadata": case, "physicalRoutes": routes[identifier],
                                     "payloadObservations": path_results, "semanticStatus": "NOT_ASSESSED"}, ensure_ascii=True) + "\n")
    br_rows = []
    for row in register:
        elements = set(json.loads(row["aiElements"]))
        case_ids = json.loads(row["aiCaseIds"])
        bindings = collections.Counter()
        review_flags = []
        for identifier in case_ids:
            case = case_results[identifier]["metadata"]
            if case is None:
                bindings["caseMetadataUnavailable"] += 1
            elif not elements:
                bindings["elementScopeUnresolved"] += 1
            elif any(claim["element"] in elements for claim in case["claims"]):
                bindings["casesWithClaimedElementInMetadata"] += 1
            else:
                bindings["casesWithoutClaimedElementInMetadata"] += 1
        if not case_ids:
            review_flags.append("NO_LINKED_CASE")
        if not elements:
            review_flags.append("ELEMENT_SCOPE_UNRESOLVED")
        if bindings["casesWithoutClaimedElementInMetadata"]:
            review_flags.append("CLAIMED_ELEMENT_NOT_IN_CASE_REQUEST_METADATA")
        if not json.loads(row["aiSegments"]):
            review_flags.append("PRODUCER_SEGMENT_UNRESOLVED")
        counts.update("br_" + flag for flag in review_flags)
        br_rows.append({"aiBrId": row["aiBrId"], "statement": row["aiStatement"], "linkedCaseCount": len(case_ids),
                        "elementBindingObservations": dict(bindings), "reviewFlags": review_flags,
                        "automatedScreeningStatus": "COMPLETED_BOUNDED_CHECKS",
                        "fullChainReviewStatus": "NOT_COMPLETED", "semanticStatus": "NOT_ASSESSED"})
    write_csv(output_dir / "all-br-chain-screen.csv", br_rows)
    flags_by_br = {row["aiBrId"]: row["reviewFlags"] for row in br_rows}
    test_ids = set()
    pair_count = 0
    with pairs_path.open(encoding="utf-8-sig", newline="") as source:
        with (output_dir / "candidate-pair-screen.csv").open("w", encoding="utf-8-sig", newline="") as target:
            writer = csv.DictWriter(target, fieldnames=["aiBrId", "testBrId", "candidateRank", "anchorRelation",
                "aiChainReviewFlags", "testDataReadiness", "reviewStatus", "semanticStatus"])
            writer.writeheader()
            for pair in csv.DictReader(source):
                anchor = "SHARED_SEGMENT_AND_ELEMENT" if json.loads(pair["sharedSegments"]) and json.loads(pair["sharedElements"]) else "PARTIAL_OR_TEXT_ONLY_LEAD"
                writer.writerow({"aiBrId": pair["aiBrId"], "testBrId": pair["testBrId"], "candidateRank": pair["candidateRank"],
                    "anchorRelation": anchor, "aiChainReviewFlags": json.dumps(flags_by_br[pair["aiBrId"]]),
                    "testDataReadiness": pair["testDataReadiness"], "reviewStatus": "PENDING_OBLIGATION_COMPARISON",
                    "semanticStatus": "NOT_ASSESSED"})
                pair_count += 1
                test_ids.add(pair["testBrId"])
    for path, digest in hashes.items():
        if sha256(Path(path)) != digest:
            raise ValueError("Input changed during screening: " + path)
    summary = {"artifact": "ALL_AI_BR_BOUNDED_CHAIN_SCREEN", "aiBrsScreened": len(br_rows),
               "uniqueLinkedCasesScreened": len(case_results), "candidatePairsTriaged": pair_count,
               "testBrRecordsInCandidateQueue": len(test_ids), "targetTestBrRecords": review_summary["population"]["testBusinessRequirements"],
               "preliminaryHumanStyleScreenings": review_summary["population"]["preliminaryScreenedAiBrs"],
               "fullChainReviewsCompleted": 0, "semanticMatchesConfirmed": 0, "hostExecuted": False,
               "observations": dict(counts), "inputSha256": hashes,
               "limitations": ["All BR rows receive bounded automated checks, not full obligation/source review.",
                  "Exact field names normalized within explicit segment context only; aliases and ambiguous mappings remain unresolved.",
                  "Claimed-element absence concerns request metadata, not proof of a broken chain: response-only and lifecycle rules require separate interpretation.",
                  "Negative or boundary probes may deliberately violate constraints; exact metadata agreement does not validate a test.",
                  "Only linked candidate-catalog cases and declared frozen payload paths are inspected; no undeclared file fallback.",
                  "Payload hashes checked; metadata sidecar values, wire encoding, host state and independent expected outcomes are not validated here.",
                  "Top-K plus old anchor leads is retrieval triage, not exhaustive semantic pair comparison."]}
    (output_dir / "summary.json").write_text(json.dumps(summary, indent=2), encoding="utf-8")
    (output_dir / "screening-implementation.py").write_bytes(Path(__file__).read_bytes())
    manifest = {path.name: sha256(path) for path in output_dir.iterdir() if path.is_file()}
    (output_dir / "manifest.json").write_text(json.dumps({"algorithm": "SHA-256", "artifacts": manifest}, indent=2), encoding="utf-8")
    print(json.dumps({key: value for key, value in summary.items() if key not in {"inputSha256", "limitations"}}, indent=2))


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--review-dir", type=Path)
    parser.add_argument("--audit-dir", type=Path)
    parser.add_argument("--output-dir", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
    elif all((args.review_dir, args.audit_dir, args.output_dir)):
        run(args.review_dir.resolve(), args.audit_dir.resolve(), args.output_dir.resolve())
    else:
        parser.error("Supply review directory, frozen audit directory and a new output directory")


if __name__ == "__main__":
    main()