#!/usr/bin/env python3
"""Audit AI Element 36 format evidence without exporting Extract Date values."""

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
RUN_ROOT = ATL105 / "test-input" / "ai-solution" / "runs" / "2026-10-19" / "Run7"
REVIEW_V3 = ATL105 / "test-output" / "ai-solution-independent-review" / "2026-10-19-all-ai-br-chain-test-br-review-v3"
TEST_PACKAGE = ATL105 / "test-output" / "test-solution-independent-review" / "complete-all-test-solution-br-ts-tc-td-package.json"
OUTPUT = ATL105 / "test-output" / "test-solution-independent-review" / "2026-10-19-full-corpus-review-batch-016"
DEFAULT_AI_BR_ID = "REQ-SRC-ATL105-PDF-001:043"
SOURCE_EVIDENCE = (
    (20301, "Number: 36 Name: Extract Date"),
    (20302, "Character Type: N Maximum Length: 6 bytes"),
    (20304, "Representation: Fixed length of six digits (MMDDYY)"),
    (20305, "Purpose: Identifies the date the information was extracted for an Electronic"),
    (20308, "Valid Codes/Values: Any valid date"),
)


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


def read_records(path: Path, key: str, wanted: set[str]) -> dict[str, Any]:
    found = {}
    with gzip.open(path, "rt", encoding="utf-8") as stream:
        for line in stream:
            record = json.loads(line)
            identity = record.get(key)
            if identity in wanted:
                found[identity] = record
    return found


def is_extract_date_name(value: Any) -> bool:
    return normalized(value) in {"extractdate", "element36"}


def classify(value: Any) -> dict[str, Any]:
    result: dict[str, Any] = {"rawValueWritten": False, "calendarValidityEvaluated": False}
    if not isinstance(value, str):
        result.update({"classification": "INVALID_REPRESENTATION", "jsonValueType": type(value).__name__,
                       "characterCount": None, "asciiByteCount": None})
        return result
    result["jsonValueType"] = "string"
    result["characterCount"] = len(value)
    result["asciiByteCount"] = len(value) if value.isascii() else None
    if value == "":
        result["classification"] = "EMPTY_PRESENCE_UNRESOLVED"
    elif not value.isascii() or not value.isdigit():
        result["classification"] = "INVALID_NONNUMERIC_OR_NONASCII"
    elif len(value) != 6:
        result["classification"] = "INVALID_FIXED_N6_WIDTH"
    else:
        result["classification"] = "N6_MMDDYY_SHAPE_ONLY_CALENDAR_UNASSESSED"
    return result


def payload_observations(node: Any, path: tuple[str, ...] = (), segment: str | None = None) -> tuple[list[dict[str, Any]], list[dict[str, Any]]]:
    targets: list[dict[str, Any]] = []
    markers: list[dict[str, Any]] = []
    if isinstance(node, dict):
        if node.get("SegmentType") is not None:
            segment = str(node["SegmentType"])
            markers.append({"path": "/" + "/".join(path + ("SegmentType",)), "segment": segment})
        for key, value in node.items():
            child = path + (str(key),)
            if is_extract_date_name(key):
                targets.append({"path": "/" + "/".join(child), "segment": segment, "value": value})
            nested_targets, nested_markers = payload_observations(value, child, segment)
            targets.extend(nested_targets)
            markers.extend(nested_markers)
    elif isinstance(node, list):
        for index, value in enumerate(node):
            nested_targets, nested_markers = payload_observations(value, path + (f"[{index}]",), segment)
            targets.extend(nested_targets)
            markers.extend(nested_markers)
    return targets, markers


def target_sidecar_rows(metadata: Any, key: str) -> list[dict[str, Any]]:
    fields = metadata.get(key, []) if isinstance(metadata, dict) else []
    if not isinstance(fields, list):
        return []
    output = []
    for field in fields:
        if not isinstance(field, dict):
            continue
        labels = [field.get(name, "") for name in ("element", "specElementName", "name", "friendly")]
        if str(field.get("element", "")) == "36" or any(is_extract_date_name(label) for label in labels):
            output.append(field)
    return output


def verify_source(atl105_root: Path) -> tuple[list[dict[str, Any]], str]:
    source_path = atl105_root / "docs" / "specs" / "extracted_text.txt"
    lines = source_path.read_text(encoding="utf-8-sig").splitlines()
    whole = re.sub(r"\s+", " ", "\n".join(lines)).strip()
    evidence = []
    for line_number, quote in SOURCE_EVIDENCE:
        if line_number > len(lines) or re.sub(r"\s+", " ", quote).strip() not in whole:
            raise ValueError(f"Element 36 source excerpt not found at line {line_number}")
        prefix = " ".join(re.sub(r"\s+", " ", quote).split()[:4])
        if prefix not in re.sub(r"\s+", " ", lines[line_number - 1]):
            raise ValueError(f"Element 36 source line anchor mismatch at line {line_number}")
        evidence.append({"startLine": line_number, "quote": quote})
    start = whole.find("Number: 36 Name: Extract Date")
    end = whole.find("Number: 37 Name: Extract Time", start + 1)
    definition = whole[start:end] if start >= 0 and end > start else ""
    required = (
        "Character Type: N Maximum Length: 6 bytes",
        "Representation: Fixed length of six digits (MMDDYY)",
        "Purpose: Identifies the date the information was extracted for an Electronic Mail Data Segment.",
        "Valid Codes/Values: Any valid date",
    )
    if not definition or any(term not in definition for term in required):
        raise ValueError("Element 36 N6/MMDDYY/purpose/source-domain definition changed")
    return evidence, sha256(source_path)


def roster(register_path: Path, ai_br_id: str) -> dict[str, Any]:
    csv.field_size_limit(100000000)
    with register_path.open(encoding="utf-8-sig", newline="") as stream:
        rows = [row for row in csv.DictReader(stream) if row.get("aiBrId") == ai_br_id]
    if len(rows) != 1:
        raise ValueError(f"Expected exactly one AI BR row for {ai_br_id}, found {len(rows)}")
    row = rows[0]
    parse = lambda key: json.loads(row.get(key) or "[]")
    paths = parse("aiPayloadPaths")
    trees = sorted({PurePosixPath(path).parent.as_posix() for path in paths
                    if "candidates" in PurePosixPath(path).parts})
    return {
        "aiBrId": ai_br_id,
        "statement": row.get("aiStatement"),
        "scenarioIds": parse("aiScenarioIds"),
        "caseIds": parse("aiCaseIds"),
        "physicalCompleteCaseIds": parse("aiPhysicalCompleteCaseIds"),
        "segments": parse("aiSegments"),
        "elements": parse("aiElements"),
        "payloadPaths": paths,
        "candidateTrees": trees,
        "exactTestCandidates": parse("v6ExactAnchorTestBrCandidates"),
        "rankedTestCandidates": parse("rankedTestBrCandidates"),
        "chainStatus": row.get("chainStatus"),
    }


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


def test_candidates(package: dict[str, Any], candidate_ids: list[str], chains: dict[str, Any]) -> list[dict[str, Any]]:
    requirements = {item.get("id"): item for item in package.get("businessRequirements", [])}
    output = []
    for candidate in candidate_ids:
        package_id = candidate if candidate.startswith("BR-") else "BR-RULE-" + candidate
        requirement = requirements.get(package_id)
        output.append({
            "candidateId": candidate,
            "packageId": package_id if requirement else None,
            "title": requirement.get("title") if requirement else None,
            "status": requirement.get("status") if requirement else None,
            "sourceAnchors": requirement.get("sourceAnchors", []) if requirement else [],
            "directExtractDateRule": candidate == "SEG109-R-016",
            "disposition": "Compound conditional rule across Local Time, Extract Date and Extract Time; not an approved standalone Element 36 oracle."
            if candidate == "SEG109-R-016" else "Ranked candidate lead only; scope requires independent review.",
            "chain": chain_summary(chains.get(candidate)),
        })
    return output


def audit_copy(case_id: str, directory: Path, run_root: Path) -> dict[str, Any]:
    payload_path = directory / f"{case_id}.json"
    sidecar_path = directory / f"{case_id}.meta.json"
    payload, payload_error = read_json(payload_path)
    metadata, sidecar_error = read_json(sidecar_path)
    targets, markers = payload_observations(payload) if payload_error is None else ([], [])
    sidecar_targets = target_sidecar_rows(metadata, "fields") if sidecar_error is None else []
    unavailable_targets = target_sidecar_rows(metadata, "unavailableFields") if sidecar_error is None else []
    payload_binding = [(str(item.get("segment")), item.get("value")) for item in targets]
    sidecar_binding = [(str(item.get("segment")), item.get("value")) for item in sidecar_targets]
    if targets and sidecar_targets:
        sidecar_match = payload_binding == sidecar_binding
    elif targets or sidecar_targets:
        sidecar_match = False
    else:
        sidecar_match = None
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
        "segmentMarkers": markers,
        "targetOccurrences": [{"path": item["path"], "segment": item["segment"], **classify(item["value"])}
                              for item in targets],
        "sidecarTargetCount": len(sidecar_targets),
        "sidecarTargetValuesMatchPayload": sidecar_match,
        "unavailableElement36Count": len(unavailable_targets),
        "producerDeclaredIntent": {
            "scenarioType": metadata.get("scenarioType") if isinstance(metadata, dict) else None,
            "negativeClass": metadata.get("negativeClass") if isinstance(metadata, dict) else None,
            "violatedElementName": metadata.get("violatedElementName") if isinstance(metadata, dict) else None,
        },
        "rawDateValueWritten": False,
    }


def build_report(run_root: Path, atl105_root: Path, review_v3: Path, test_package_path: Path,
                 ai_br_id: str) -> dict[str, Any]:
    register_path = review_v3 / "all-ai-br-review-register.csv"
    ai = roster(register_path, ai_br_id)
    evidence, source_hash = verify_source(atl105_root)
    intake = json.loads((run_root / "intake-manifest.json").read_text(encoding="utf-8-sig"))
    if intake.get("aiBrId") != ai_br_id:
        raise ValueError("Run intake manifest belongs to a different AI BR")
    ai_chains = read_records(review_v3 / "ai-br-chain-evidence.jsonl.gz", "aiBrId", {ai_br_id})
    if ai_br_id not in ai_chains:
        raise ValueError(f"AI chain evidence missing {ai_br_id}")
    test_chains = read_records(review_v3 / "test-chain-evidence.jsonl.gz", "testBrId",
                               set(ai["rankedTestCandidates"]))
    package = json.loads(test_package_path.read_text(encoding="utf-8-sig"))
    dirs_by_case: dict[str, set[Path]] = {case_id: set() for case_id in ai["caseIds"]}
    for relative in ai["payloadPaths"]:
        path = PurePosixPath(relative)
        if "candidates" in path.parts and path.name.endswith(".json") and path.stem in dirs_by_case:
            dirs_by_case[path.stem].add(Path(path.parent.as_posix()))
    cases = []
    for case_id in ai["caseIds"]:
        copies = [audit_copy(case_id, run_root / tree, run_root)
                  for tree in sorted(dirs_by_case[case_id], key=lambda item: item.as_posix())]
        hashes = [(copy["payloadSha256"], copy["sidecarSha256"]) for copy in copies]
        observations = [item for copy in copies for item in copy["targetOccurrences"]]
        cases.append({"caseId": case_id, "copies": copies,
                      "duplicateCopiesByteIdentical": len(hashes) > 1 and len(set(hashes)) == 1,
                      "extractDateObserved": bool(observations),
                      "classifications": [item["classification"] for item in observations],
                      "producerDeclaredIntent": copies[0]["producerDeclaredIntent"] if copies else None})
    unique_observations = []
    for case in cases:
        selected = case["copies"][:1] if case["duplicateCopiesByteIdentical"] else case["copies"]
        unique_observations.extend(item for copy in selected for item in copy["targetOccurrences"])
    class_counts: dict[str, int] = {}
    class_counts_by_segment: dict[str, dict[str, int]] = {}
    for item in unique_observations:
        classification = item["classification"]
        segment = str(item.get("segment"))
        class_counts[classification] = class_counts.get(classification, 0) + 1
        segment_counts = class_counts_by_segment.setdefault(segment, {})
        segment_counts[classification] = segment_counts.get(classification, 0) + 1
    intent_counts: dict[str, int] = {}
    for case in cases:
        intent = case.get("producerDeclaredIntent") or {}
        label = str(intent.get("negativeClass") or intent.get("scenarioType") or "UNDECLARED")
        intent_counts[label] = intent_counts.get(label, 0) + 1
    ai_chain = ai_chains[ai_br_id]
    summary = {
        "caseCount": len(cases),
        "physicalCompleteCaseCountInRoster": len(ai["physicalCompleteCaseIds"]),
        "caseIdsWithExtractDate": [case["caseId"] for case in cases if case["extractDateObserved"]],
        "caseIdsWithoutExtractDate": [case["caseId"] for case in cases if not case["extractDateObserved"]],
        "candidateCopyCount": sum(len(case["copies"]) for case in cases),
        "allDuplicateCandidateCopiesIdentical": all(case["duplicateCopiesByteIdentical"] for case in cases),
        "uniqueFormatObservationCount": len(unique_observations),
        "formatClassCounts": class_counts,
        "formatClassCountsBySegment": class_counts_by_segment,
        "producerDeclaredIntentCounts": intent_counts,
        "segmentBoundCaseCounts": {segment: sum(any(any(marker["segment"] == segment for marker in copy["segmentMarkers"])
                                                     for copy in case["copies"]) for case in cases)
                                   for segment in ai["segments"]},
        "missingApprovedReferences": len(intake.get("missingUnselectedRegisteredPaths", [])),
        "coverageCredit": 0, "semanticMatchesConfirmed": 0, "executionPerformed": False,
    }
    return {
        "reviewId": "ATL105-FULL-CORPUS-BATCH-016-EXTRACT-DATE",
        "reviewDate": date.today().isoformat(),
        "authority": "PRELIMINARY_ANALYST_REVIEW_NOT_AUTHORIZED_APPROVAL",
        "status": "REVIEW_REQUIRED",
        "aiBusinessRequirement": {
            "id": ai_br_id, "statement": ai["statement"], "scenarioIds": ai["scenarioIds"],
            "caseIds": ai["caseIds"], "physicalCompleteCaseIds": ai["physicalCompleteCaseIds"],
            "segments": ai["segments"], "elements": ai["elements"], "chainStatus": ai_chain.get("chainStatus"),
            "physicalPayloadPathCount": len(ai_chain.get("physicalPayloadPaths", [])),
        },
        "registeredArtifactScope": {
            "payloadPathCount": len(ai["payloadPaths"]),
            "candidatePathCount": sum("candidates" in PurePosixPath(path).parts for path in ai["payloadPaths"]),
            "approvedPathCount": sum("approved" in PurePosixPath(path).parts for path in ai["payloadPaths"]),
            "missingApprovedPathsRecordedInIntake": len(intake.get("missingUnselectedRegisteredPaths", [])),
            "labelCaveat": "Folder labels are source-corpus states, not semantic approvals.",
        },
        "sourceRule": {
            "id": "ENT-ELEM-36", "element": "36", "name": "Extract Date", "characterType": "N",
            "maximumLengthBytes": 6, "representation": "Fixed length of six digits (MMDDYY)",
            "validValues": "Any valid date", "purpose": "Identifies the date information was extracted for an Electronic Mail Data Segment.",
            "alignment": "AI numeric/maximum-six claim is partially aligned but omits fixed N6, MMDDYY and valid-date semantics.",
            "calendarAssessment": "Not performed: source does not define a two-digit-year century pivot; N6/ASCII/field-shape checks are not a calendar oracle.",
            "presenceAssessment": "Not inferred from this value-format rule.",
            "sourceEvidence": evidence, "sourceTextSha256": source_hash,
        },
        "testCrosswalk": {
            "exactAnchorCandidates": ai["exactTestCandidates"],
            "rankedCandidatesAreLeadsOnly": True,
            "rankedCandidates": test_candidates(package, ai["rankedTestCandidates"], test_chains),
            "closestTestRule": "SEG109-R-016 is compound and conditional, covering Local Time, Extract Date and Extract Time; its chain is review-required.",
        },
        "cases": cases,
        "summary": summary,
        "analystDisposition": {
            "formatAlignment": "PARTIAL_FORMAT_ALIGNMENT_FIXED_N6_MMDDYY_AND_VALID_DATE_DETAIL_OMITTED",
            "caseEvidence": "Candidate value strings were classified in memory only; no raw dates are written. Producer-declared classes are not independent oracles.",
            "testCounterpart": "SEG109-R-016 is a compound conditional-format candidate, not an approved standalone Element 36 rule.",
            "overall": "PRELIMINARY_REVIEW; NO_MATCH_GRANTED",
            "authorizedReviewerDecision": "PENDING", "semanticMatchStatus": "NOT_ASSESSED", "coverageCredit": 0,
        },
        "guardrails": {
            "officialTestBaselineModified": False, "currentTestPackageModified": False,
            "sourceAiArtifactsModified": False, "externalAuthorizationAttachmentUsed": False,
            "rawExtractDateValuesWritten": False, "calendarValidityCertified": False,
            "executionPerformed": False, "semanticMatchesConfirmed": 0, "coverageCredit": 0,
        },
    }


def self_test() -> None:
    expected = {
        "010126": "N6_MMDDYY_SHAPE_ONLY_CALENDAR_UNASSESSED",
        "123199": "N6_MMDDYY_SHAPE_ONLY_CALENDAR_UNASSESSED",
        "12345": "INVALID_FIXED_N6_WIDTH",
        "1234567": "INVALID_FIXED_N6_WIDTH",
        "12A456": "INVALID_NONNUMERIC_OR_NONASCII",
        "１２３４５６": "INVALID_NONNUMERIC_OR_NONASCII",
        "": "EMPTY_PRESENCE_UNRESOLVED",
    }
    for value, classification in expected.items():
        result = classify(value)
        if result["classification"] != classification or result["rawValueWritten"]:
            raise AssertionError("Element 36 classification/redaction control changed")
    print("PASS: 7 N6/ASCII/empty controls; no calendar pivot or raw date values emitted.")


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--run-root", type=Path, default=RUN_ROOT)
    parser.add_argument("--atl105-root", type=Path, default=ATL105)
    parser.add_argument("--review-v3", type=Path, default=REVIEW_V3)
    parser.add_argument("--test-package", type=Path, default=TEST_PACKAGE)
    parser.add_argument("--ai-br-id", default=DEFAULT_AI_BR_ID)
    parser.add_argument("--output", type=Path, default=OUTPUT / "extract-date-review.json")
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
    manifest = {"manifestId": "ATL105-FULL-CORPUS-BATCH-016-EXTRACT-DATE",
                "reviewDate": date.today().isoformat(), "status": "REVIEW_REQUIRED",
                "artifacts": artifacts, "inputs": inputs, "guardrails": report["guardrails"]}
    manifest_path.parent.mkdir(parents=True, exist_ok=True)
    manifest_path.write_text(json.dumps(manifest, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    print(json.dumps({"report": str(args.output), "manifest": str(manifest_path),
                      "caseCount": report["summary"]["caseCount"],
                      "candidateCopyCount": report["summary"]["candidateCopyCount"],
                      "caseIdsWithExtractDate": report["summary"]["caseIdsWithExtractDate"],
                      "missingApprovedReferences": report["summary"]["missingApprovedReferences"],
                      "coverageCredit": 0}, ensure_ascii=True))
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except (OSError, ValueError, json.JSONDecodeError) as error:
        print(f"ERROR: {type(error).__name__}: {error}", file=sys.stderr)
        raise SystemExit(2)