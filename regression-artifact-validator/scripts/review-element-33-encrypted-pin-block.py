#!/usr/bin/env python3
"""Audit AI Element 33 claims without exporting Encrypted PIN Block values."""

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
RUN_ROOT = ATL105 / "test-input" / "ai-solution" / "runs" / "2026-10-19" / "Run6"
REVIEW_V3 = ATL105 / "test-output" / "ai-solution-independent-review" / "2026-10-19-all-ai-br-chain-test-br-review-v3"
TEST_PACKAGE = ATL105 / "test-output" / "test-solution-independent-review" / "complete-all-test-solution-br-ts-tc-td-package.json"
OUTPUT = ATL105 / "test-output" / "test-solution-independent-review" / "2026-10-19-full-corpus-review-batch-013"
DEFAULT_AI_BR_ID = "REQ-SRC-ATL105-PDF-001:040"
SOURCE_EVIDENCE = (
    (20247, "Number: 33 Name: Encrypted PIN Block Data"),
    (20249, "Character Type: AN Maximum Length: 36 bytes"),
    (20251, "Representation: For DUKPT PIN encryption, this is a 16- to 20-byte Key Serial Number (KSN) plus a 16-byte PIN block."),
    (20253, "Purpose: Required for debit and EBT."),
    (20254, "It is not required for a completion."),
    (20257, "Processing Rules: Customer-keyed, must be encrypted using DUKPT."),
    (20262, "Valid Codes/Values: PIN block composition is validated by hardware PIN encryption devices."),
)
TEST_CANDIDATE_NOTES = {
    "SEG100-R-043": "Direct Element 33 DUKPT representation lead; draft and not an approved cryptographic oracle.",
    "BR-SEG100-GAP-PIN": "Related source-defined PIN/DUKPT gap requirement; conditional applicability and outcomes remain review-required.",
    "BR-SEG100-APPO-CRYPTOGRAM-BLOCK-SPLIT": "Payment-token cryptogram block split is a distinct data construct, not the PIN-block composition rule.",
    "BR-SEG100-APPO-CRYPTOGRAM": "In-app payment-token cryptogram format is distinct from Encrypted PIN Block Data.",
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


def is_target_name(value: Any) -> bool:
    return normalized(value) in {"encryptedpinblockdata", "encryptedpinblock", "element33"}


def classify(value: Any) -> tuple[str, int | None]:
    if not isinstance(value, str):
        return "INVALID_REPRESENTATION", None
    byte_length = len(value.encode("utf-8"))
    if not value:
        return "EMPTY_PRESENT_VALUE", byte_length
    if byte_length > 36:
        return "OVER_MAXIMUM_BYTE_LENGTH", byte_length
    if not value.isascii():
        return "NON_ASCII_FORMAT_REVIEW_REQUIRED", byte_length
    if not value.isalnum():
        return "ASCII_NON_ALPHANUMERIC_REVIEW_REQUIRED", byte_length
    return "ALPHANUMERIC_WITHIN_MAXIMUM_CONTEXT_UNVERIFIED", byte_length


def payload_observations(node: Any, path: tuple[str, ...] = (), segment: str | None = None) -> tuple[list[dict[str, Any]], list[dict[str, Any]]]:
    targets: list[dict[str, Any]] = []
    segments: list[dict[str, Any]] = []
    if isinstance(node, dict):
        if node.get("SegmentType") is not None:
            segment = str(node["SegmentType"])
            segments.append({"path": "/" + "/".join(path + ("SegmentType",)), "segment": segment})
        for key, value in node.items():
            child = path + (str(key),)
            if is_target_name(key):
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


def sidecar_rows(metadata: Any, key: str) -> list[dict[str, Any]]:
    fields = metadata.get(key, []) if isinstance(metadata, dict) else []
    if not isinstance(fields, list):
        return []
    result = []
    for field in fields:
        if not isinstance(field, dict):
            continue
        labels = [field.get(name, "") for name in ("element", "specElementName", "name", "friendly")]
        if str(field.get("element", "")) == "33" or any(is_target_name(label) for label in labels):
            result.append(field)
    return result


def source_rule(atl105_root: Path) -> tuple[list[dict[str, Any]], str]:
    source_path = atl105_root / "docs" / "specs" / "extracted_text.txt"
    lines = source_path.read_text(encoding="utf-8-sig").splitlines()
    whole = re.sub(r"\s+", " ", "\n".join(lines)).strip()
    for line_number, quote in SOURCE_EVIDENCE:
        if line_number > len(lines) or re.sub(r"\s+", " ", quote).strip() not in whole:
            raise ValueError(f"Element 33 source excerpt not found at line {line_number}")
        prefix = " ".join(re.sub(r"\s+", " ", quote).split()[:4])
        if prefix not in re.sub(r"\s+", " ", lines[line_number - 1]):
            raise ValueError(f"Element 33 source line anchor mismatch at line {line_number}")
    start = whole.find("Number: 33 Name: Encrypted PIN Block Data")
    end = whole.find("Number: 34 Name: End-of-Data Indicator", start + 1)
    definition = whole[start:end] if start >= 0 and end > start else ""
    required = ("Character Type: AN Maximum Length: 36 bytes", "16- to 20-byte Key Serial Number (KSN) plus a 16-byte PIN block",
                "Required for debit and EBT.", "It is not required for a completion.",
                "must be encrypted using DUKPT", "PIN block composition is validated by hardware PIN encryption devices.")
    if not definition or any(term not in definition for term in required):
        raise ValueError("Element 33 source definition or DUKPT/context notes changed")
    evidence = [{"sourcePage": 375, "startLine": line_number, "quote": quote}
                for line_number, quote in SOURCE_EVIDENCE]
    return evidence, sha256(source_path)


def case_roster(register_path: Path, ai_br_id: str) -> dict[str, Any]:
    csv.field_size_limit(100000000)
    with register_path.open(encoding="utf-8-sig", newline="") as stream:
        rows = [row for row in csv.DictReader(stream) if row.get("aiBrId") == ai_br_id]
    if len(rows) != 1:
        raise ValueError(f"Expected exactly one AI BR row for {ai_br_id}, found {len(rows)}")
    row = rows[0]
    parse = lambda key: json.loads(row.get(key) or "[]")
    paths = parse("aiPayloadPaths")
    candidates = [PurePosixPath(path) for path in paths if "candidates" in PurePosixPath(path).parts]
    return {
        "aiBrId": ai_br_id,
        "statement": row.get("aiStatement"),
        "scenarioIds": parse("aiScenarioIds"),
        "caseIds": parse("aiCaseIds"),
        "physicalCompleteCaseIds": parse("aiPhysicalCompleteCaseIds"),
        "segments": parse("aiSegments"),
        "elements": parse("aiElements"),
        "registeredPayloadPaths": paths,
        "candidateTrees": sorted({path.parent.as_posix() for path in candidates}),
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
            "disposition": TEST_CANDIDATE_NOTES.get(candidate, "Ranked lead only; scope requires independent review."),
            "chain": chain_summary(chains.get(candidate)),
        })
    return results


def audit_copy(case_id: str, directory: Path, run_root: Path) -> dict[str, Any]:
    payload_path = directory / f"{case_id}.json"
    sidecar_path = directory / f"{case_id}.meta.json"
    payload, payload_error = read_json(payload_path)
    metadata, sidecar_error = read_json(sidecar_path)
    targets, segment_markers = payload_observations(payload) if payload_error is None else ([], [])
    sidecar_targets = sidecar_rows(metadata, "fields") if sidecar_error is None else []
    unavailable_targets = sidecar_rows(metadata, "unavailableFields") if sidecar_error is None else []
    payload_values = [(str(item.get("segment")), item.get("value")) for item in targets]
    sidecar_values = [(str(item.get("segment")), item.get("value")) for item in sidecar_targets]
    sidecar_match = payload_values == sidecar_values if targets or sidecar_targets else None
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
        "targetOccurrences": [{
            "path": item["path"], "segment": item["segment"], "formatClassification": classify(item["value"])[0],
            "jsonUtf8ByteLength": classify(item["value"])[1], "wireEncodingVerified": False,
            "cryptographicValidityVerified": False, "rawEncryptedValueWritten": False,
        } for item in targets],
        "sidecarTargetCount": len(sidecar_targets),
        "sidecarTargetValuesMatchPayload": sidecar_match,
        "unavailableElement33": [{"element": item.get("element"), "friendly": item.get("friendly"),
                                  "reason": item.get("reason"), "dataSource": item.get("dataSource")}
                                 for item in unavailable_targets],
        "producerDeclaredIntent": {
            "scenarioType": metadata.get("scenarioType") if isinstance(metadata, dict) else None,
            "negativeClass": metadata.get("negativeClass") if isinstance(metadata, dict) else None,
            "violatedElementName": metadata.get("violatedElementName") if isinstance(metadata, dict) else None,
        },
        "rawEncryptedValueWritten": False,
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
            "element33Observed": bool(occurrences),
            "formatClassifications": [item["formatClassification"] for item in occurrences],
            "producerDeclaredIntent": copies[0]["producerDeclaredIntent"] if copies else None,
        })
    unique_observations = []
    for case in cases:
        selected_copies = case["copies"][:1] if case["duplicateCopiesByteIdentical"] else case["copies"]
        unique_observations.extend(item for copy in selected_copies for item in copy["targetOccurrences"])
    format_counts: dict[str, int] = {}
    for item in unique_observations:
        status = item["formatClassification"]
        format_counts[status] = format_counts.get(status, 0) + 1
    ai_chain = ai_records[ai_br_id]
    summary = {
        "caseCount": len(cases),
        "physicalCompleteCaseCountInRoster": len(roster["physicalCompleteCaseIds"]),
        "caseIdsWithElement33": [case["caseId"] for case in cases if case["element33Observed"]],
        "caseIdsWithoutElement33": [case["caseId"] for case in cases if not case["element33Observed"]],
        "candidateCopyCount": sum(len(case["copies"]) for case in cases),
        "allDuplicateCandidateCopiesIdentical": all(case["duplicateCopiesByteIdentical"] for case in cases),
        "uniqueFormatObservationCount": len(unique_observations),
        "formatClassCounts": format_counts,
        "segmentBoundCaseCounts": {segment: sum(any(any(marker["segment"] == segment for marker in copy["segmentMarkers"])
                                                       for copy in case["copies"]) for case in cases)
                                   for segment in roster["segments"]},
        "missingApprovedReferences": len(intake.get("missingUnselectedRegisteredPaths", [])),
        "coverageCredit": 0,
        "semanticMatchesConfirmed": 0,
        "executionPerformed": False,
    }
    return {
        "reviewId": "ATL105-FULL-CORPUS-BATCH-013-ENCRYPTED-PIN-BLOCK",
        "reviewDate": date.today().isoformat(),
        "authority": "PRELIMINARY_ANALYST_REVIEW_NOT_AUTHORIZED_APPROVAL",
        "status": "REVIEW_REQUIRED",
        "aiBusinessRequirement": {
            "id": ai_br_id, "statement": roster["statement"], "scenarioIds": roster["scenarioIds"],
            "caseIds": roster["caseIds"], "physicalCompleteCaseIds": roster["physicalCompleteCaseIds"],
            "segments": roster["segments"], "elements": roster["elements"],
            "chainStatus": ai_chain.get("chainStatus"),
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
            "id": "ENT-ELEM-33", "element": "33", "name": "Encrypted PIN Block Data",
            "characterType": "AN", "maximumLengthBytes": 36,
            "dukptRepresentation": "16- to 20-byte KSN plus 16-byte PIN block",
            "applicability": "Required for debit and EBT; not required for a completion.",
            "processing": "Customer-keyed PIN block must be encrypted using DUKPT; omitted request field uses a Field Separator.",
            "emvOfflinePinContext": "Source gives a distinct 16-byte PIN block rule for EMV offline PIN-validated debit.",
            "compositionOracle": "Composition is validated by hardware PIN encryption devices; not established by this static format audit.",
            "alignment": "AI alphanumeric/maximum-36 claim is source-aligned but omits DUKPT composition, context and hardware validation.",
            "sourceEvidence": evidence, "sourceTextSha256": source_hash,
        },
        "testCrosswalk": {
            "exactAnchorCandidates": roster["exactTestCandidates"],
            "rankedCandidatesAreLeadsOnly": True,
            "rankedCandidates": test_candidates(package, roster["rankedTestCandidates"], candidate_chains),
            "directRuleDisposition": "SOURCE_SCOPED_DUKPT_CANDIDATE_CHAIN_REVIEW_REQUIRED",
        },
        "cases": cases,
        "summary": summary,
        "analystDisposition": {
            "sourceAlignment": "ALIGNED_BUT_UNDERSPECIFIED",
            "caseEvidence": "No Element 33 value was present in the candidate payloads; all seven sidecars mark it omitted by composer. JSON byte length is not wire encoding, and no cryptographic validation was attempted.",
            "testCounterparts": "SEG100-R-043 and BR-SEG100-GAP-PIN are relevant DUKPT candidates; token-cryptogram rules are distinct. All direct chains remain review-required.",
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
            "rawEncryptedPinBlockValuesWritten": False,
            "wireEncodingVerified": False,
            "cryptographicValidityVerified": False,
            "executionPerformed": False,
            "semanticMatchesConfirmed": 0,
            "coverageCredit": 0,
        },
    }


def self_test() -> None:
    expected = {
        "AB12": ("ALPHANUMERIC_WITHIN_MAXIMUM_CONTEXT_UNVERIFIED", 4),
        "" : ("EMPTY_PRESENT_VALUE", 0),
        "A" * 36: ("ALPHANUMERIC_WITHIN_MAXIMUM_CONTEXT_UNVERIFIED", 36),
        "A" * 37: ("OVER_MAXIMUM_BYTE_LENGTH", 37),
        "AA-B": ("ASCII_NON_ALPHANUMERIC_REVIEW_REQUIRED", 4),
        "é": ("NON_ASCII_FORMAT_REVIEW_REQUIRED", 2),
        123: ("INVALID_REPRESENTATION", None),
    }
    for value, result in expected.items():
        if classify(value) != result:
            raise AssertionError(f"Element 33 classification mismatch for {type(value).__name__}")
    print("PASS: 7 redacted Element 33 type/byte-length/character-class controls; no PIN-block values emitted.")


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--run-root", type=Path, default=RUN_ROOT)
    parser.add_argument("--atl105-root", type=Path, default=ATL105)
    parser.add_argument("--review-v3", type=Path, default=REVIEW_V3)
    parser.add_argument("--test-package", type=Path, default=TEST_PACKAGE)
    parser.add_argument("--ai-br-id", default=DEFAULT_AI_BR_ID)
    parser.add_argument("--output", type=Path, default=OUTPUT / "encrypted-pin-block-review.json")
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
    manifest = {"manifestId": "ATL105-FULL-CORPUS-BATCH-013-ENCRYPTED-PIN-BLOCK",
                "reviewDate": date.today().isoformat(), "status": "REVIEW_REQUIRED",
                "artifacts": artifacts, "inputs": inputs, "guardrails": report["guardrails"]}
    manifest_path.parent.mkdir(parents=True, exist_ok=True)
    manifest_path.write_text(json.dumps(manifest, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    print(json.dumps({"report": str(args.output), "manifest": str(manifest_path),
                      "caseCount": report["summary"]["caseCount"],
                      "candidateCopyCount": report["summary"]["candidateCopyCount"],
                      "caseIdsWithElement33": report["summary"]["caseIdsWithElement33"],
                      "missingApprovedReferences": report["summary"]["missingApprovedReferences"],
                      "coverageCredit": 0}, ensure_ascii=True))
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except (OSError, ValueError, json.JSONDecodeError) as error:
        print(f"ERROR: {type(error).__name__}: {error}", file=sys.stderr)
        raise SystemExit(2)