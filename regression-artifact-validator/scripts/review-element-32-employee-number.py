#!/usr/bin/env python3
"""Audit AI Element 32 chains against ATL105 without exporting employee numbers."""

from __future__ import annotations

import argparse
import csv
import gzip
import hashlib
import json
import re
import sys
from datetime import date
from pathlib import Path, PurePosixPath
from typing import Any


ROOT = Path(__file__).resolve().parents[1]
ATL105 = ROOT / "specifications" / "ATL105"
RUN_ROOT = ATL105 / "test-input" / "ai-solution" / "runs" / "2026-10-19" / "Run5"
REVIEW_V3 = ATL105 / "test-output" / "ai-solution-independent-review" / "2026-10-19-all-ai-br-chain-test-br-review-v3"
TEST_PACKAGE = ATL105 / "test-output" / "test-solution-independent-review" / "complete-all-test-solution-br-ts-tc-td-package.json"
OUTPUT = ATL105 / "test-output" / "test-solution-independent-review" / "2026-10-19-full-corpus-review-batch-012"
DEFAULT_AI_BR_ID = "REQ-SRC-ATL105-PDF-001:039"
SOURCE_EVIDENCE = (
    (20225, "Number: 32 Name: Employee Number"),
    (20226, "Character Type: N Maximum Length: 4 bytes"),
    (20228, "Representation: Fixed length of four digits"),
    (20229, "Purpose: Identifies the employee who performs a utility transaction."),
    (20230, "Note: Used for reporting purposes only."),
    (20232, "Valid Codes/Values: Valid employee numbers."),
    (20233, "Note: Use 1111 if no employee numbers are assigned."),
)
TEST_CANDIDATE_NOTES = {
    "SEG105-R-006": "Authorization-policy applicability when present; not an Element 32 N4 format oracle.",
    "SEG109-R-013": "Numeric maximum-four format candidate; fixed four-digit representation remains omitted.",
    "SEG119-R-013": "Conditional-presence candidate; does not establish Element 32 numeric format or valid values.",
}


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for chunk in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(chunk)
    return digest.hexdigest()


def normalized(value: Any) -> str:
    return re.sub(r"[^a-z0-9]", "", str(value).lower())


def read_json(path: Path) -> tuple[Any | None, str | None]:
    try:
        return json.loads(path.read_text(encoding="utf-8-sig")), None
    except (OSError, UnicodeError, json.JSONDecodeError) as error:
        return None, type(error).__name__


def is_employee_number_name(value: Any) -> bool:
    return normalized(value) in {"employeenumber", "employeenum", "element32"}


def classify(value: Any) -> str:
    if not isinstance(value, str):
        return "INVALID_REPRESENTATION"
    if not value:
        return "INVALID_EMPTY_WHEN_PRESENT"
    if not value.isascii() or not value.isdigit():
        return "INVALID_NONNUMERIC"
    if len(value) != 4:
        return "INVALID_FIXED_WIDTH"
    return "SOURCE_FORMAT_VALID_SEMANTIC_VALUE_UNVERIFIED"


def payload_observations(node: Any, path: tuple[str, ...] = (), segment: str | None = None) -> tuple[list[dict[str, Any]], list[dict[str, Any]]]:
    targets: list[dict[str, Any]] = []
    segments: list[dict[str, Any]] = []
    if isinstance(node, dict):
        if node.get("SegmentType") is not None:
            segment = str(node["SegmentType"])
            segments.append({"path": "/" + "/".join(path + ("SegmentType",)), "segment": segment})
        for key, value in node.items():
            child = path + (str(key),)
            if is_employee_number_name(key):
                targets.append({"path": "/" + "/".join(child), "segment": segment, "value": value})
            nested_targets, nested_segments = payload_observations(value, child, segment)
            targets.extend(nested_targets)
            segments.extend(nested_segments)
    elif isinstance(node, list):
        for index, value in enumerate(node):
            nested_targets, nested_segments = payload_observations(value, path + (f"[{index}]",), segment)
            targets.extend(nested_targets)
            segments.extend(nested_segments)
    return targets, segments


def sidecar_target_rows(metadata: Any, key: str) -> list[dict[str, Any]]:
    fields = metadata.get(key, []) if isinstance(metadata, dict) else []
    if not isinstance(fields, list):
        return []
    result = []
    for field in fields:
        if not isinstance(field, dict):
            continue
        labels = [field.get(name, "") for name in ("element", "specElementName", "name", "friendly")]
        if str(field.get("element", "")) == "32" or any(is_employee_number_name(label) for label in labels):
            result.append(field)
    return result


def source_rule(atl105_root: Path) -> tuple[list[dict[str, Any]], str]:
    source_path = atl105_root / "docs" / "specs" / "extracted_text.txt"
    lines = source_path.read_text(encoding="utf-8-sig").splitlines()
    whole = re.sub(r"\s+", " ", "\n".join(lines)).strip()
    for line_number, quote in SOURCE_EVIDENCE:
        if line_number > len(lines) or re.sub(r"\s+", " ", quote).strip() not in whole:
            raise ValueError(f"Element 32 source excerpt not found at line {line_number}")
        prefix = " ".join(re.sub(r"\s+", " ", quote).split()[:4])
        if prefix not in re.sub(r"\s+", " ", lines[line_number - 1]):
            raise ValueError(f"Element 32 source line anchor mismatch at line {line_number}")
    start = whole.find("Number: 32 Name: Employee Number")
    end = whole.find("Number: 33 Name: Encrypted PIN Block Data", start + 1)
    definition = whole[start:end] if start >= 0 and end > start else ""
    required = ("Character Type: N Maximum Length: 4 bytes", "Representation: Fixed length of four digits",
                "Valid Codes/Values: Valid employee numbers.", "Use 1111 if no employee numbers are assigned.")
    if not definition or any(term not in definition for term in required):
        raise ValueError("Element 32 N4/fixed-width/source-value notes changed")
    return ([{"sourcePage": 374, "startLine": line_number, "quote": quote}
             for line_number, quote in SOURCE_EVIDENCE], sha256(source_path))


def case_roster(register_path: Path, ai_br_id: str) -> dict[str, Any]:
    csv.field_size_limit(100000000)
    with register_path.open(encoding="utf-8-sig", newline="") as stream:
        rows = [row for row in csv.DictReader(stream) if row.get("aiBrId") == ai_br_id]
    if len(rows) != 1:
        raise ValueError(f"Expected exactly one AI BR row for {ai_br_id}, found {len(rows)}")
    row = rows[0]
    parse = lambda key: json.loads(row.get(key) or "[]")
    paths = parse("aiPayloadPaths")
    candidate_paths = [PurePosixPath(path) for path in paths if "candidates" in PurePosixPath(path).parts]
    trees = sorted({path.parent.as_posix() for path in candidate_paths})
    return {
        "aiBrId": ai_br_id,
        "statement": row.get("aiStatement"),
        "scenarioIds": parse("aiScenarioIds"),
        "caseIds": parse("aiCaseIds"),
        "physicalCompleteCaseIds": parse("aiPhysicalCompleteCaseIds"),
        "segments": parse("aiSegments"),
        "elements": parse("aiElements"),
        "registeredPayloadPaths": paths,
        "candidateTrees": trees,
        "exactTestCandidates": parse("v6ExactAnchorTestBrCandidates"),
        "rankedTestCandidates": parse("rankedTestBrCandidates"),
        "chainStatus": row.get("chainStatus"),
        "reviewDisposition": row.get("reviewDisposition"),
        "semanticMatchStatus": row.get("semanticMatchStatus"),
    }


def find_chain(path: Path, key: str, wanted: set[str]) -> dict[str, Any]:
    found = {}
    with gzip.open(path, "rt", encoding="utf-8") as stream:
        for line in stream:
            record = json.loads(line)
            identity = record.get(key)
            if identity in wanted:
                found[identity] = record
    return found


def chain_summary(record: dict[str, Any] | None) -> dict[str, Any] | None:
    if record is None:
        return None
    return {
        "businessRequirementStatus": record.get("br", {}).get("status"),
        "scenarioIds": [item.get("id") for item in record.get("scenarios", [])],
        "scenarioStatuses": [item.get("status") for item in record.get("scenarios", [])],
        "caseIds": [item.get("id") for item in record.get("cases", [])],
        "caseStatuses": [item.get("status") for item in record.get("cases", [])],
        "testDataIds": [item.get("id") for item in record.get("data", [])],
        "testDataReadiness": [item.get("readiness") for item in record.get("data", [])],
        "testDataExpectedValidation": [item.get("expectedValidation") for item in record.get("data", [])],
    }


def test_candidates(package: dict[str, Any], candidates: list[str], chains: dict[str, Any]) -> list[dict[str, Any]]:
    requirements = {item.get("id"): item for item in package.get("businessRequirements", [])}
    results = []
    for candidate in candidates:
        package_id = candidate if candidate.startswith("BR-") else "BR-RULE-" + candidate
        requirement = requirements.get(package_id)
        results.append({
            "candidateId": candidate,
            "packageId": package_id if requirement else None,
            "title": requirement.get("title") if requirement else None,
            "status": requirement.get("status") if requirement else None,
            "sourceAnchors": requirement.get("sourceAnchors", []) if requirement else [],
            "disposition": TEST_CANDIDATE_NOTES.get(candidate, "Ranked candidate lead only; scope requires independent review."),
            "chain": chain_summary(chains.get(candidate)),
        })
    return results


def audit_copy(case_id: str, directory: Path, run_root: Path) -> dict[str, Any]:
    payload_path = directory / f"{case_id}.json"
    sidecar_path = directory / f"{case_id}.meta.json"
    payload, payload_error = read_json(payload_path)
    metadata, sidecar_error = read_json(sidecar_path)
    targets, segment_markers = payload_observations(payload) if payload_error is None else ([], [])
    sidecar_targets = sidecar_target_rows(metadata, "fields") if sidecar_error is None else []
    unavailable_targets = sidecar_target_rows(metadata, "unavailableFields") if sidecar_error is None else []
    payload_values = [(str(item.get("segment")), item.get("value")) for item in targets]
    sidecar_values = [(str(item.get("segment")), item.get("value")) for item in sidecar_targets]
    sidecar_match = payload_values == sidecar_values if targets or sidecar_targets else None
    segment_types = sorted({str(item["segment"]) for item in segment_markers})
    return {
        "sourceTree": directory.relative_to(run_root).as_posix(),
        "payloadPath": payload_path.relative_to(run_root).as_posix(),
        "sidecarPath": sidecar_path.relative_to(run_root).as_posix(),
        "payloadSha256": sha256(payload_path) if payload_path.is_file() else None,
        "sidecarSha256": sha256(sidecar_path) if sidecar_path.is_file() else None,
        "messageFamily": next(iter(payload), None) if isinstance(payload, dict) else None,
        "payloadParseError": payload_error,
        "sidecarParseError": sidecar_error,
        "sidecarCaseIdMatches": metadata.get("testCaseId") == case_id if isinstance(metadata, dict) else False,
        "sourceBatchSha256Present": bool(metadata.get("sourceBatchSha256")) if isinstance(metadata, dict) else False,
        "segmentMarkers": segment_markers,
        "targetOccurrences": [{"path": item["path"], "segment": item["segment"],
                               "classification": classify(item["value"]),
                               "characterCount": len(item["value"]) if isinstance(item["value"], str) else None,
                               "rawValueWritten": False} for item in targets],
        "sidecarTargetCount": len(sidecar_targets),
        "sidecarTargetValuesMatchPayload": sidecar_match,
        "unavailableEmployeeNumber": [{"element": item.get("element"), "friendly": item.get("friendly"),
                                        "reason": item.get("reason"), "dataSource": item.get("dataSource")}
                                       for item in unavailable_targets],
        "producerDeclaredIntent": {
            "scenarioType": metadata.get("scenarioType") if isinstance(metadata, dict) else None,
            "negativeClass": metadata.get("negativeClass") if isinstance(metadata, dict) else None,
            "priority": metadata.get("priority") if isinstance(metadata, dict) else None,
            "violatedElementName": metadata.get("violatedElementName") if isinstance(metadata, dict) else None,
        },
        "unavailableSegments": sorted(str(item.get("segment")) for item in metadata.get("unavailableSegments", [])
                                       if isinstance(item, dict)) if isinstance(metadata, dict) else [],
        "presenceUnknownSegments": sorted(str(item.get("segment")) for item in metadata.get("presenceUnknownSegments", [])
                                           if isinstance(item, dict)) if isinstance(metadata, dict) else [],
        "observedSegmentTypes": segment_types,
        "rawEmployeeNumberWritten": False,
    }


def build_report(run_root: Path, atl105_root: Path, review_v3: Path, test_package_path: Path,
                 ai_br_id: str) -> dict[str, Any]:
    register_path = review_v3 / "all-ai-br-review-register.csv"
    roster = case_roster(register_path, ai_br_id)
    evidence, source_hash = source_rule(atl105_root)
    intake_path = run_root / "intake-manifest.json"
    intake = json.loads(intake_path.read_text(encoding="utf-8-sig"))
    if intake.get("aiBrId") != ai_br_id:
        raise ValueError("Run intake manifest belongs to a different AI BR")
    ai_chain_path = review_v3 / "ai-br-chain-evidence.jsonl.gz"
    test_chain_path = review_v3 / "test-chain-evidence.jsonl.gz"
    ai_records = find_chain(ai_chain_path, "aiBrId", {ai_br_id})
    if ai_br_id not in ai_records:
        raise ValueError(f"AI chain evidence is missing {ai_br_id}")
    candidate_chains = find_chain(test_chain_path, "testBrId", set(roster["rankedTestCandidates"]))
    package = json.loads(test_package_path.read_text(encoding="utf-8-sig"))
    copy_dirs_by_case: dict[str, set[Path]] = {case_id: set() for case_id in roster["caseIds"]}
    for relative in roster["registeredPayloadPaths"]:
        path = PurePosixPath(relative)
        if "candidates" in path.parts and path.name.endswith(".json"):
            case_id = path.stem
            if case_id in copy_dirs_by_case:
                copy_dirs_by_case[case_id].add(Path(path.parent.as_posix()))
    cases = []
    for case_id in roster["caseIds"]:
        copies = [audit_copy(case_id, run_root / tree, run_root)
                  for tree in sorted(copy_dirs_by_case.get(case_id, set()), key=lambda item: item.as_posix())]
        copy_hashes = [(copy["payloadSha256"], copy["sidecarSha256"]) for copy in copies]
        occurrences = [item for copy in copies for item in copy["targetOccurrences"]]
        cases.append({
            "caseId": case_id,
            "copies": copies,
            "duplicateCopiesByteIdentical": len(copy_hashes) > 1 and len(set(copy_hashes)) == 1,
            "employeeNumberObserved": bool(occurrences),
            "observedClassifications": [item["classification"] for item in occurrences],
            "producerDeclaredIntent": copies[0]["producerDeclaredIntent"] if copies else None,
        })
    ai_chain = ai_records[ai_br_id]
    unique_observations = []
    for case in cases:
        selected_copies = case["copies"][:1] if case["duplicateCopiesByteIdentical"] else case["copies"]
        unique_observations.extend(item for copy in selected_copies for item in copy["targetOccurrences"])
    class_counts: dict[str, int] = {}
    class_counts_by_segment: dict[str, dict[str, int]] = {}
    for item in unique_observations:
        classification = item["classification"]
        segment = str(item.get("segment"))
        class_counts[classification] = class_counts.get(classification, 0) + 1
        segment_counts = class_counts_by_segment.setdefault(segment, {})
        segment_counts[classification] = segment_counts.get(classification, 0) + 1
    producer_intent_counts: dict[str, int] = {}
    for case in cases:
        intent = case.get("producerDeclaredIntent") or {}
        label = str(intent.get("negativeClass") or intent.get("scenarioType") or "UNDECLARED")
        producer_intent_counts[label] = producer_intent_counts.get(label, 0) + 1
    summary = {
        "caseCount": len(cases),
        "physicalCompleteCaseCountInRoster": len(roster["physicalCompleteCaseIds"]),
        "caseIdsWithEmployeeNumber": [case["caseId"] for case in cases if case["employeeNumberObserved"]],
        "caseIdsWithoutEmployeeNumber": [case["caseId"] for case in cases if not case["employeeNumberObserved"]],
        "candidateCopyCount": sum(len(case["copies"]) for case in cases),
        "allDuplicateCandidateCopiesIdentical": all(case["duplicateCopiesByteIdentical"] for case in cases),
        "uniqueFormatObservationCount": len(unique_observations),
        "formatClassCounts": class_counts,
        "formatClassCountsBySegment": class_counts_by_segment,
        "producerDeclaredIntentCounts": producer_intent_counts,
        "segmentBoundCaseCounts": {segment: sum(any(segment in copy["observedSegmentTypes"] for copy in case["copies"])
                                                   for case in cases) for segment in roster["segments"]},
        "missingApprovedReferences": len(intake.get("missingUnselectedRegisteredPaths", [])),
        "coverageCredit": 0,
        "semanticMatchesConfirmed": 0,
        "executionPerformed": False,
    }
    direct_candidates = roster["exactTestCandidates"]
    return {
        "reviewId": "ATL105-FULL-CORPUS-BATCH-012-EMPLOYEE-NUMBER",
        "reviewDate": date.today().isoformat(),
        "authority": "PRELIMINARY_ANALYST_REVIEW_NOT_AUTHORIZED_APPROVAL",
        "status": "REVIEW_REQUIRED",
        "aiBusinessRequirement": {
            "id": ai_br_id, "statement": roster["statement"], "scenarioIds": roster["scenarioIds"],
            "caseIds": roster["caseIds"], "physicalCompleteCaseIds": roster["physicalCompleteCaseIds"],
            "segments": roster["segments"], "elements": roster["elements"], "chainStatus": ai_chain.get("chainStatus"),
            "physicalPayloadPathCount": len(ai_chain.get("physicalPayloadPaths", [])),
        },
        "registeredArtifactScope": {
            "payloadPathCount": len(roster["registeredPayloadPaths"]),
            "candidatePathCount": sum("candidates" in PurePosixPath(path).parts for path in roster["registeredPayloadPaths"]),
            "approvedPathCount": sum("approved" in PurePosixPath(path).parts for path in roster["registeredPayloadPaths"]),
            "missingApprovedPathsRecordedInIntake": len(intake.get("missingUnselectedRegisteredPaths", [])),
            "labelCaveat": "Folder labels 'approved' and 'candidates' are source-corpus states, not semantic approval.",
        },
        "sourceRule": {
            "id": "ENT-ELEM-32", "element": "32", "name": "Employee Number", "characterType": "N",
            "maximumLengthBytes": 4, "representation": "Fixed length of four digits",
            "validValues": "Valid employee numbers; exhaustive value domain is not defined here",
            "specialSourceNote": "Use 1111 if no employee numbers are assigned.",
            "purpose": "Identifies the employee who performs a utility transaction; reporting purposes only.",
            "alignment": "AI numeric/maximum-four claim is partially aligned but omits fixed N4 representation and value-domain qualification.",
            "sourceEvidence": evidence, "sourceTextSha256": source_hash,
        },
        "testCrosswalk": {
            "exactAnchorCandidates": direct_candidates,
            "rankedCandidatesAreLeadsOnly": True,
            "rankedCandidates": test_candidates(package, roster["rankedTestCandidates"], candidate_chains),
            "segmentCandidateDispositions": TEST_CANDIDATE_NOTES,
        },
        "cases": cases,
        "summary": summary,
        "analystDisposition": {
            "sourceAlignment": "PARTIAL_FORMAT_ALIGNMENT_FIXED_N4_DETAIL_OMITTED",
            "caseEvidence": "Candidate payloads and sidecars were inspected; approved-path references absent from the frozen source were not substituted.",
            "testCounterparts": "SEG109-R-013 is the closest format lead but omits fixed width; SEG105-R-006 and SEG119-R-013 concern policy/applicability. All three chains remain REVIEW_REQUIRED.",
            "overall": "PRELIMINARY_REVIEW; NO_MATCH_GRANTED",
            "authorizedReviewerDecision": "PENDING",
            "semanticMatchStatus": "NOT_ASSESSED",
            "coverageCredit": 0,
        },
        "guardrails": {
            "officialTestBaselineModified": False,
            "currentTestPackageModified": False,
            "sourceAiArtifactsModified": False,
            "externalAuthorizationAttachmentUsed": False,
            "rawEmployeeNumberValuesWritten": False,
            "executionPerformed": False,
            "semanticMatchesConfirmed": 0,
            "coverageCredit": 0,
        },
    }


def self_test() -> None:
    expected = {"0000": "SOURCE_FORMAT_VALID_SEMANTIC_VALUE_UNVERIFIED",
                "1111": "SOURCE_FORMAT_VALID_SEMANTIC_VALUE_UNVERIFIED",
                "123": "INVALID_FIXED_WIDTH", "12345": "INVALID_FIXED_WIDTH",
                "12A4": "INVALID_NONNUMERIC", "１２３４": "INVALID_NONNUMERIC",
                "": "INVALID_EMPTY_WHEN_PRESENT", 1234: "INVALID_REPRESENTATION"}
    for value, result in expected.items():
        if classify(value) != result:
            raise AssertionError(f"Element 32 classification mismatch for {type(value).__name__}")
    print("PASS: 8 N4 representation controls; no employee-number values are included in output.")


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--run-root", type=Path, default=RUN_ROOT)
    parser.add_argument("--atl105-root", type=Path, default=ATL105)
    parser.add_argument("--review-v3", type=Path, default=REVIEW_V3)
    parser.add_argument("--test-package", type=Path, default=TEST_PACKAGE)
    parser.add_argument("--ai-br-id", default=DEFAULT_AI_BR_ID)
    parser.add_argument("--output", type=Path, default=OUTPUT / "employee-number-review.json")
    parser.add_argument("--manifest", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
        return 0
    report = build_report(args.run_root, args.atl105_root, args.review_v3, args.test_package, args.ai_br_id)
    args.output.parent.mkdir(parents=True, exist_ok=True)
    args.output.write_text(json.dumps(report, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    manifest_path = args.manifest or args.output.with_name("manifest.json")
    readme = args.output.with_name("README.md")
    artifacts = [{"path": str(path), "sha256": sha256(path), "role": role}
                 for path, role in ((args.output, "category-review-report"),
                                    (Path(__file__).resolve(), "category-review-runner"))]
    if readme.is_file():
        artifacts.append({"path": str(readme), "sha256": sha256(readme), "role": "review-summary"})
    register_path = args.review_v3 / "all-ai-br-review-register.csv"
    inputs = [{"path": str(path), "sha256": sha256(path), "role": role}
              for path, role in ((args.atl105_root / "docs" / "specs" / "extracted_text.txt", "ATL105-source-text"),
                                 (register_path, "AI-BR-roster"),
                                 (args.review_v3 / "ai-br-chain-evidence.jsonl.gz", "AI-chain-evidence"),
                                 (args.review_v3 / "test-chain-evidence.jsonl.gz", "Test-chain-evidence"),
                                 (args.test_package, "Test-package"),
                                 (args.run_root / "intake-manifest.json", "immutable-AI-intake-manifest"))]
    for case in report["cases"]:
        for copy in case["copies"]:
            for key, role in (("payloadPath", "AI-candidate-payload"), ("sidecarPath", "AI-candidate-sidecar")):
                path = args.run_root / copy[key]
                inputs.append({"caseId": case["caseId"], "path": str(path),
                               "sha256": copy["payloadSha256"] if key == "payloadPath" else copy["sidecarSha256"],
                               "role": role})
    manifest = {"manifestId": "ATL105-FULL-CORPUS-BATCH-012-EMPLOYEE-NUMBER",
                "reviewDate": date.today().isoformat(), "status": "REVIEW_REQUIRED",
                "artifacts": artifacts, "inputs": inputs, "guardrails": report["guardrails"]}
    manifest_path.parent.mkdir(parents=True, exist_ok=True)
    manifest_path.write_text(json.dumps(manifest, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    print(json.dumps({"report": str(args.output), "manifest": str(manifest_path),
                      "caseCount": report["summary"]["caseCount"],
                      "candidateCopyCount": report["summary"]["candidateCopyCount"],
                      "caseIdsWithEmployeeNumber": report["summary"]["caseIdsWithEmployeeNumber"],
                      "missingApprovedReferences": report["summary"]["missingApprovedReferences"],
                      "coverageCredit": 0}, ensure_ascii=True))
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except (OSError, ValueError, json.JSONDecodeError) as error:
        print(f"ERROR: {type(error).__name__}: {error}", file=sys.stderr)
        raise SystemExit(2)