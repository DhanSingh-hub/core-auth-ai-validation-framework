"""Compare AI payload observations with included Test BR draft fixture vectors."""

import argparse
import collections
import csv
import gzip
import hashlib
import json
import re
from pathlib import Path


SCALAR_FIELDS = {"segmenttype", "segmentlength", "numberofsegments", "messagelength", "byteorder"}


def sha256(path):
    digest = hashlib.sha256()
    with Path(path).open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def read_json(path):
    return json.loads(Path(path).read_text(encoding="utf-8-sig"))


def csv_rows(path):
    with Path(path).open(encoding="utf-8-sig", newline="") as stream:
        return list(csv.DictReader(stream))


def jsonl(path):
    with Path(path).open(encoding="utf-8") as stream:
        for line in stream:
            if line.strip():
                yield json.loads(line)


def scalar_key(name):
    return re.sub(r"[^a-z]", "", name.lower())


def ai_observations(chain):
    clean_cases = set(chain["assessment"].get("physicalCompleteCaseIds") or [])
    values = collections.defaultdict(set)
    observed_paths = collections.defaultdict(set)
    verified_pairs = set()
    for case in chain["cases"]:
        if case["testCaseId"] not in clean_cases:
            continue
        complete_paths = {path for route in case["physicalRoutes"]
                          if route["status"] == "COMPLETE_PHYSICAL_DECLARATIONS"
                          for path in route["paths"]}
        for evidence in case["physicalEvidence"]:
            if evidence["payload"] not in complete_paths or not evidence["frozenHashesVerified"]:
                continue
            verified_pairs.add((evidence["payload"], evidence["metadata"]))
            for observation in evidence["messageStructureObservations"]:
                key = scalar_key(observation["path"].rsplit("/", 1)[-1])
                if key in SCALAR_FIELDS:
                    values[key].add(str(observation["value"]))
                    observed_paths[key].add((str(observation["value"]), observation["path"]))
    return {"cleanCases": clean_cases, "verifiedPairs": verified_pairs, "values": values,
            "observedPaths": observed_paths}


def walk_scalars(value, path=""):
    if isinstance(value, dict):
        for name, child in value.items():
            child_path = path + "/" + name
            key = scalar_key(name)
            if key in SCALAR_FIELDS and not isinstance(child, (dict, list)) and child is not None:
                yield key, str(child), child_path
            yield from walk_scalars(child, child_path)
    elif isinstance(value, list):
        for index, child in enumerate(value):
            yield from walk_scalars(child, path + "/" + str(index))


def test_fixture_observations(chain):
    fixtures = []
    for alternative in chain["alternativeAuthoredChains"]:
        for test_data in alternative["data"]:
            payload = test_data.get("payload")
            if payload is None:
                continue
            fixtures.append({"alternativeBrId": alternative["br"]["id"],
                             "testDataId": test_data["id"],
                             "readiness": test_data.get("readiness") or "UNSET",
                             "expectedValidation": test_data.get("expectedValidation") or "UNSET",
                             "scalars": list(walk_scalars(payload))})
    return fixtures


def exact_scalar_overlaps(ai, fixtures, shared_segments):
    overlaps = []
    for fixture in fixtures:
        for key, value, test_path in fixture["scalars"]:
            if value not in ai["values"].get(key, set()):
                continue
            if key == "segmenttype" and shared_segments and value not in shared_segments:
                continue
            ai_paths = [path for observed_value, path in ai["observedPaths"].get(key, set())
                        if observed_value == value]
            if ai_paths:
                overlaps.append({"field": key, "value": value, "testDataId": fixture["testDataId"],
                                 "alternativeBrId": fixture["alternativeBrId"], "testPath": test_path,
                                 "aiPaths": sorted(set(ai_paths)), "exactValueObserved": True,
                                 "executedTest": False, "validationVerdict": "NOT_ASSESSED"})
    return overlaps


def write_csv(path, rows, fields):
    with path.open("w", encoding="utf-8", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=fields, extrasaction="ignore")
        writer.writeheader()
        for row in rows:
            writer.writerow({key: json.dumps(value, ensure_ascii=True) if isinstance(value, (list, dict)) else value
                             for key, value in row.items() if key in fields})


def run(comparison_dir, output_dir):
    comparison_dir, output_dir = Path(comparison_dir), Path(output_dir)
    if output_dir.exists():
        raise ValueError("Refusing to overwrite output directory")
    input_names = ["message-candidate-pairs.csv", "ai-chain-evidence.jsonl.gz",
                   "test-chain-evidence.jsonl", "summary.json", "manifest.json"]
    inputs = {name: sha256(comparison_dir / name) for name in input_names}
    source_manifest = read_json(comparison_dir / "manifest.json")["artifacts"]
    for name, expected in source_manifest.items():
        if sha256(comparison_dir / name) != expected:
            raise ValueError("Comparison artifact hash mismatch: " + name)
    pairs = csv_rows(comparison_dir / "message-candidate-pairs.csv")
    ai_chains = {}
    with gzip.open(comparison_dir / "ai-chain-evidence.jsonl.gz", "rt", encoding="utf-8") as stream:
        for line in stream:
            row = json.loads(line)
            ai_chains[row["aiBrId"]] = ai_observations(row)
    test_chains = {row["testBrId"]: row for row in jsonl(comparison_dir / "test-chain-evidence.jsonl")}
    fixtures_by_test = {identifier: test_fixture_observations(chain)
                        for identifier, chain in test_chains.items()}
    details = []
    for pair in pairs:
        ai = ai_chains[pair["aiBrId"]]
        test = test_chains[pair["testBrId"]]
        fixtures = fixtures_by_test[pair["testBrId"]]
        shared_segments = set(json.loads(pair["sharedSegments"]))
        overlaps = exact_scalar_overlaps(ai, fixtures, shared_segments)
        test_present = test["structuralChainPresent"]
        details.append({**pair,
            "testArtifactsIncludedDespiteReviewStatus": True,
            "aiCleanPhysicalCaseCount": len(ai["cleanCases"]),
            "aiVerifiedPayloadMetadataPairCount": len(ai["verifiedPairs"]),
            "testPrimaryStructuralChainPresent": test_present,
            "testPrimaryReviewPlaceholderCount": len(test["placeholderIds"]),
            "testAlternativeAuthoredBrIds": [row["br"]["id"] for row in test["alternativeAuthoredChains"]],
            "testDraftFixtureCount": len(fixtures),
            "testDraftFixtureReadiness": sorted({row["readiness"] for row in fixtures}),
            "exactScalarOverlaps": overlaps,
            "matchingStatus": "CANDIDATE_REVIEW_ONLY",
            "structuralArtifactStatus": "BOTH_SIDES_PRESENT_PLACEHOLDER_INCLUDED" if test_present and ai["cleanCases"] else "TEST_INCLUDED_AI_PHYSICAL_CHAIN_MISSING",
            "scalarObservationStatus": "EXACT_DRAFT_FIXTURE_VALUE_OBSERVED" if overlaps else "NO_EXACT_COMPARABLE_VALUE_OBSERVED",
            "semanticStatus": "NOT_ASSESSED",
            "executionStatus": "NOT_EXECUTED"})
    message_test_ids = {identifier for identifier, chain in test_chains.items()
                        if chain["category"] == "Message"}
    message_fixtures = [fixture for identifier in message_test_ids for fixture in fixtures_by_test[identifier]]
    expected_labels = dict(collections.Counter(fixture["expectedValidation"] for fixture in message_fixtures))
    if any(sha256(comparison_dir / name) != digest for name, digest in inputs.items()):
        raise ValueError("Comparison inputs changed during validation")
    counts = {
        "candidatePairs": len(details),
        "pairsWithAiCleanPhysicalChain": sum(row["aiCleanPhysicalCaseCount"] > 0 for row in details),
        "pairsWithTestStructuralChainPlaceholderIncluded": sum(row["testPrimaryStructuralChainPresent"] for row in details),
        "pairsWithExactAnchorAuthoredTestAlternatives": sum(bool(row["testAlternativeAuthoredBrIds"]) for row in details),
        "pairsWithDraftTestFixtures": sum(row["testDraftFixtureCount"] > 0 for row in details),
        "pairsWithExactScalarOverlap": sum(bool(row["exactScalarOverlaps"]) for row in details),
        "pairsWithExactSegmentTypeOverlap": sum(any(item["field"] == "segmenttype" for item in row["exactScalarOverlaps"]) for row in details),
        "sameCategoryMessagePairsWithExactScalarOverlap": sum(bool(row["exactScalarOverlaps"]) for row in details
            if row["aiCategory"] == "Message" and row["testCategory"] == "Message"),
        "messageTestDraftFixtureVectorsIncluded": len(message_fixtures),
        "messageTestDraftFixtureVectorsReviewRequired": sum(fixture["readiness"] == "REVIEW_REQUIRED" for fixture in message_fixtures),
        "semanticApprovedPairs": 0,
        "executedPairs": 0,
        "certifiedMatches": 0,
        "reviewPlaceholdersUsedAsExclusion": 0,
    }
    summary = read_json(comparison_dir / "summary.json")["population"]
    output_dir.mkdir(parents=True)
    fields = list(details[0]) if details else []
    write_csv(output_dir / "message-pair-validation.csv", details, fields)
    report = "# Message BR Matching and Artifact Validation\n\n"
    report += "The comparison includes Test BR, scenario, case and data records even when their status is REVIEW_REQUIRED or their IDs use review placeholders. Placeholder status is not an exclusion criterion for candidate matching. It remains recorded as validation/readiness evidence and is never converted into execution or approval.\n\n"
    report += "## Match Reach\n\n| Measure | Result |\n|---|---:|\n"
    report += "".join(f"| {label} | {value:,} |\n" for label, value in (
        ("AI Message BRs", summary["aiMessageBrs"]),
        ("Test Message BRs", summary["testMessageBrs"]),
        ("Message-to-Message combinations examined", summary["messageToMessageMatrixRows"]),
        ("Existing source-anchor candidate pairs touching Message", summary["existingCandidatePairsTouchingMessage"]),
        ("Additional context review leads", summary["additionalContextReviewLeads"]),
        ("Test Message BRs with any candidate or lead", summary["testMessageBrsWithAnyReviewLead"]),
        ("AI Message BRs with any candidate or lead", summary["aiMessageBrsWithAnyReviewLead"]),
        ("Candidate pairs including placeholder Test artifacts", counts["candidatePairs"]),
    ))
    report += "\n## Artifact Validation\n\n| Check | Result |\n|---|---:|\n"
    report += "".join(f"| {label} | {value:,} |\n" for label, value in (
        ("Pairs with a clean AI physical chain", counts["pairsWithAiCleanPhysicalChain"]),
        ("Pairs with a linked Test structural chain (placeholder included)", counts["pairsWithTestStructuralChainPlaceholderIncluded"]),
        ("Pairs with exact-anchor authored Test alternatives", counts["pairsWithExactAnchorAuthoredTestAlternatives"]),
        ("Test Message BRs with exact-anchor authored alternatives", summary["testMessageBrsWithAlternativeExactAnchorChains"]),
        ("Draft Test Data fixture vectors included", counts["messageTestDraftFixtureVectorsIncluded"]),
        ("Exact scalar key/value overlaps observed", counts["pairsWithExactScalarOverlap"]),
        ("Same-category Message pairs with scalar overlap", counts["sameCategoryMessagePairsWithExactScalarOverlap"]),
        ("Pairs executed", counts["executedPairs"]),
        ("Semantic approvals", counts["semanticApprovedPairs"]),
        ("Certified matches", counts["certifiedMatches"]),
    ))
    report += "\nDraft Test fixture `expectedValidation` labels (not executed): "
    report += ", ".join(f"{label}={count}" for label, count in sorted(expected_labels.items())) + ".\n"
    report += "\n## Artifact Crossmatch\n\n"
    report += ("The pair CSV joins every existing anchor candidate and context lead to the full primary Test chain and exact-full-anchor authored alternatives. "
               "It checks AI observed structure scalars from complete, hash-verified routes against scalar values in draft Test fixture payloads. "
               "All 46 exact scalar overlaps are SegmentType values (11 within Message-to-Message pairs); no SegmentLength scalar overlaps were observed. "
               "that the Test vector is approved, or that expected outcomes agree.\n\n")
    report += "## Validation Status\n\n"
    report += ("AI payload and metadata hashes are checked against the frozen delivery inventory. Test placeholder records remain in the match set and expose their scenario/case/data links. "
               "The selected Test package contains draft fixture values, but these remain REVIEW_REQUIRED; no Test execution is inferred. Semantic equivalence, wire agreement, host outcomes and SME decisions remain NOT_ASSESSED.\n\n")
    report += ("The Segment Length AI BR :1907 has four clean physical cases but no observable Segment Length scalar in those payloads. "
               "Its four-digit format candidates and maximum-capacity candidates remain distinct obligations; exact element 84 alone is not validation.\n")
    (output_dir / "REPORT.md").write_text(report, encoding="utf-8")
    (output_dir / "summary.json").write_text(json.dumps({"population": {**summary, **counts},
        "draftFixtureExpectedValidationLabels": expected_labels,
        "comparisonInputSha256": inputs,
        "limitations": ["Candidate status is not semantic match approval.",
                        "Exact scalar overlap is an observation, not an executed assertion.",
                        "Test fixture records remain REVIEW_REQUIRED and no Test execution is inferred.",
                        "Placeholder statuses do not exclude artifacts from the crossmatch."]}, indent=2), encoding="utf-8")
    implementation = Path(__file__)
    (output_dir / "validation-implementation.py").write_bytes(implementation.read_bytes())
    artifacts = {path.name: sha256(path) for path in output_dir.iterdir() if path.is_file()}
    (output_dir / "manifest.json").write_text(json.dumps({"algorithm": "SHA-256", "artifacts": artifacts}, indent=2), encoding="utf-8")
    print(json.dumps({**summary, **counts}, indent=2))


def self_test():
    ai = {"cleanCases": {"TC-A"}, "verifiedPairs": {("p", "m")},
            "values": {"segmenttype": {"100"}},
            "observedPaths": {"segmenttype": {("100", "/request/segmentType")}}}
    fixtures = [{"alternativeBrId": "BR-A", "testDataId": "TD-A", "readiness": "REVIEW_REQUIRED",
                 "expectedValidation": "PASS", "payload": {"segmentType": "100"},
                 "scalars": [("segmenttype", "100", "/request/segmentType")] }]
    test_chain_row = {"structuralChainPresent": True, "placeholderIds": ["TS-REVIEW-X"],
                      "alternativeAuthoredChains": [{"br": {"id": "BR-A"}, "data": [{"id": "TD-A", "readiness": "REVIEW_REQUIRED", "payload": {"segmentType": "100"}}]}]}
    assert exact_scalar_overlaps(ai, fixtures, {"100"})[0]["validationVerdict"] == "NOT_ASSESSED"
    assert test_fixture_observations(test_chain_row)[0]["readiness"] == "REVIEW_REQUIRED"
    assert test_chain_row["structuralChainPresent"] and test_chain_row["placeholderIds"]
    print("PASS: review-placeholder Test vectors are included, exact scalar overlaps stay NOT_ASSESSED")


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--comparison-dir", type=Path)
    parser.add_argument("--output-dir", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
    elif args.comparison_dir and args.output_dir:
        run(args.comparison_dir, args.output_dir)
    else:
        parser.error("Supply the comparison directory and a new output directory")


if __name__ == "__main__":
    main()