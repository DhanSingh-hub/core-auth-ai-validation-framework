#!/usr/bin/env python3
"""Audit AI Element 31 chains without exporting Driver/Identification values."""

from __future__ import annotations

import argparse
import csv
import gzip
import hashlib
import json
import re
import sys
from datetime import date
from pathlib import Path
from typing import Any


ROOT = Path(__file__).resolve().parents[1]
ATL105 = ROOT / "specifications" / "ATL105"
RUN_ROOT = ATL105 / "test-input" / "ai-solution" / "runs" / "2026-10-19" / "Run4"
REVIEW_V3 = ATL105 / "test-output" / "ai-solution-independent-review" / "2026-10-19-all-ai-br-chain-test-br-review-v3"
TEST_PACKAGE = ATL105 / "test-output" / "test-solution-independent-review" / "complete-all-test-solution-br-ts-tc-td-package.json"
OUTPUT = ATL105 / "test-output" / "test-solution-independent-review" / "2026-10-19-full-corpus-review-batch-011"
DEFAULT_AI_BR_ID = "REQ-SRC-ATL105-PDF-001:038"
DEFAULT_SOURCE_TREES = (
    Path("test_data_10_oct/test_data_10_oct/candidates/qe_shaped_test_data"),
    Path("test_generation/test_generation/candidates/qe_shaped_test_data"),
)
SOURCE_EVIDENCE = (
    (20208, "Number: 31 Name: Driver/Identification Number"),
    (20210, "Character Type: N Maximum Length: 10 bytes"),
    (20212, "Representation: Variable length of up to 10 digits"),
    (20218, "If required by the issuer, this number must be an unencrypted Driver/Identification Number."),
    (20220, "If required by the issuer, this element is found in the Fleet Data Segment (Segment No. 101)."),
    (20222, "Valid Codes/Values: Any numeric representation"),
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


def classify(value: Any) -> str:
    if not isinstance(value, str):
        return "INVALID_REPRESENTATION"
    if not value:
        return "INVALID_EMPTY_NUMERIC_REPRESENTATION"
    if not value.isascii() or not value.isdigit():
        return "INVALID_NONNUMERIC"
    if len(value) > 10:
        return "INVALID_WIDTH"
    return "SOURCE_FORMAT_VALID"


def is_target_name(value: Any) -> bool:
    name = normalized(value)
    return name in {"driveridentificationnumber", "drivernumber", "driveridnumber", "element31"}


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


def target_sidecar_rows(metadata: Any) -> list[dict[str, Any]]:
    fields = metadata.get("fields", []) if isinstance(metadata, dict) else []
    if not isinstance(fields, list):
        return []
    result = []
    for field in fields:
        if not isinstance(field, dict):
            continue
        labels = [field.get(key, "") for key in ("element", "specElementName", "name", "friendly")]
        if str(field.get("element", "")) == "31" or any(is_target_name(label) for label in labels):
            result.append(field)
    return result


def unavailable_target_rows(metadata: Any) -> list[dict[str, Any]]:
    fields = metadata.get("unavailableFields", []) if isinstance(metadata, dict) else []
    if not isinstance(fields, list):
        return []
    return [field for field in fields if isinstance(field, dict) and (
        str(field.get("element", "")) == "31"
        or any(is_target_name(field.get(key, "")) for key in ("element", "friendly", "name"))
    )]


def unavailable_segment_101(metadata: Any) -> list[dict[str, Any]]:
    segments = metadata.get("unavailableSegments", []) if isinstance(metadata, dict) else []
    if not isinstance(segments, list):
        return []
    return [item for item in segments if isinstance(item, dict) and str(item.get("segment", "")) == "101"]


def presence_unknown_101(metadata: Any) -> list[dict[str, Any]]:
    segments = metadata.get("presenceUnknownSegments", []) if isinstance(metadata, dict) else []
    if not isinstance(segments, list):
        return []
    return [item for item in segments if isinstance(item, dict) and str(item.get("segment", "")) == "101"]


def source_rule(atl105_root: Path) -> tuple[list[dict[str, Any]], str]:
    source_path = atl105_root / "docs" / "specs" / "extracted_text.txt"
    lines = source_path.read_text(encoding="utf-8-sig").splitlines()
    whole = re.sub(r"\s+", " ", "\n".join(lines)).strip()
    for line_number, quote in SOURCE_EVIDENCE:
        if line_number > len(lines) or re.sub(r"\s+", " ", quote).strip() not in whole:
            raise ValueError(f"Element 31 source excerpt not found at line {line_number}")
        prefix = " ".join(re.sub(r"\s+", " ", quote).split()[:4])
        if prefix not in re.sub(r"\s+", " ", lines[line_number - 1]):
            raise ValueError(f"Element 31 source line anchor mismatch at line {line_number}")
    definition = whole[whole.find("Number: 31 Name: Driver/Identification Number"):]
    definition = definition[:definition.find("Number: 32 Name: Employee Number")]
    required_terms = ("Character Type: N Maximum Length: 10 bytes", "Representation: Variable length of up to 10 digits",
                      "Valid Codes/Values: Any numeric representation", "Segment No. 101")
    if not definition or any(term not in definition for term in required_terms):
        raise ValueError("Element 31 numeric/variable-width/source-scope definition changed")
    evidence = [{"sourcePage": 374, "startLine": line_number, "quote": quote}
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
    return {
        "aiBrId": ai_br_id,
        "statement": row.get("aiStatement"),
        "scenarioIds": parse("aiScenarioIds"),
        "caseIds": parse("aiCaseIds"),
        "segments": parse("aiSegments"),
        "elements": parse("aiElements"),
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
        "chainStatus": record.get("chainStatus"),
        "businessRequirementStatus": record.get("br", {}).get("status"),
        "scenarioIds": [item.get("id") for item in record.get("scenarios", [])],
        "scenarioStatuses": [item.get("status") for item in record.get("scenarios", [])],
        "caseIds": [item.get("id") for item in record.get("cases", [])],
        "caseStatuses": [item.get("status") for item in record.get("cases", [])],
        "testDataIds": [item.get("id") for item in record.get("data", [])],
        "testDataReadiness": [item.get("readiness") for item in record.get("data", [])],
        "testDataExpectedValidation": [item.get("expectedValidation") for item in record.get("data", [])],
    }


def test_candidate_summaries(package: dict[str, Any], candidates: list[str], chains: dict[str, Any]) -> list[dict[str, Any]]:
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
            "chain": chain_summary(chains.get(candidate)),
        })
    return results


def audit_copy(case_id: str, directory: Path, source_kind: str, run_root: Path) -> dict[str, Any]:
    payload_path = directory / f"{case_id}.json"
    sidecar_path = directory / f"{case_id}.meta.json"
    payload, payload_error = read_json(payload_path)
    metadata, sidecar_error = read_json(sidecar_path)
    targets, segment_markers = payload_observations(payload) if payload_error is None else ([], [])
    sidecar_targets = target_sidecar_rows(metadata) if sidecar_error is None else []
    unavailable_targets = unavailable_target_rows(metadata) if sidecar_error is None else []
    unavailable_segments = unavailable_segment_101(metadata) if sidecar_error is None else []
    unknown_segments = presence_unknown_101(metadata) if sidecar_error is None else []
    observed_segments = {str(item.get("segment")) for item in targets if item.get("segment") is not None}
    observed_segments.update(str(item["segment"]) for item in segment_markers)
    if targets and sidecar_targets:
        sidecar_match = [item.get("value") for item in targets] == [item.get("value") for item in sidecar_targets]
    elif targets or sidecar_targets:
        sidecar_match = False
    else:
        sidecar_match = None
    return {
        "caseId": case_id,
        "sourceKind": source_kind,
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
        "observedSegmentTypes": sorted(observed_segments),
        "targetFieldOccurrences": [{"path": item["path"], "segment": item["segment"],
                                    "classification": classify(item["value"]), "rawValueWritten": False}
                                   for item in targets],
        "sidecarTargetFieldCount": len(sidecar_targets),
        "sidecarTargetValuesMatchPayload": sidecar_match,
        "unavailableElement31": [{"element": item.get("element"), "friendly": item.get("friendly"),
                                   "reason": item.get("reason"), "dataSource": item.get("dataSource")}
                                  for item in unavailable_targets],
        "unavailableSegment101Count": len(unavailable_segments),
        "presenceUnknownSegment101Count": len(unknown_segments),
        "rawTargetValuesWritten": False,
    }


def build_report(run_root: Path, atl105_root: Path, review_v3: Path, test_package_path: Path,
                 ai_br_id: str, source_trees: tuple[Path, ...]) -> dict[str, Any]:
    register_path = review_v3 / "all-ai-br-review-register.csv"
    roster = case_roster(register_path, ai_br_id)
    if not roster["caseIds"]:
        raise ValueError("The selected AI BR has no linked cases to audit")
    evidence, source_hash = source_rule(atl105_root)
    ai_chain_path = review_v3 / "ai-br-chain-evidence.jsonl.gz"
    test_chain_path = review_v3 / "test-chain-evidence.jsonl.gz"
    ai_records = find_chain(ai_chain_path, "aiBrId", {ai_br_id})
    if ai_br_id not in ai_records:
        raise ValueError(f"AI chain evidence is missing {ai_br_id}")
    candidates = list(dict.fromkeys(roster["rankedTestCandidates"]))
    test_records = find_chain(test_chain_path, "testBrId", set(candidates))
    package = json.loads(test_package_path.read_text(encoding="utf-8-sig"))
    cases = []
    for case_id in roster["caseIds"]:
        copies = []
        for tree in source_trees:
            copies.append(audit_copy(case_id, run_root / tree, tree.as_posix().upper(), run_root))
        hashes = [(copy["payloadSha256"], copy["sidecarSha256"]) for copy in copies]
        cases.append({"caseId": case_id, "copies": copies,
                      "duplicateCopiesByteIdentical": len(hashes) > 1 and len(set(hashes)) == 1,
                      "element31Observed": any(copy["targetFieldOccurrences"] for copy in copies)})
    ai_chain = ai_records[ai_br_id]
    summary = {
        "caseCount": len(cases),
        "caseIdsWithElement31": [case["caseId"] for case in cases if case["element31Observed"]],
        "caseIdsWithoutElement31": [case["caseId"] for case in cases if not case["element31Observed"]],
        "allDuplicateCandidateCopiesIdentical": all(case["duplicateCopiesByteIdentical"] for case in cases),
        "segment101BoundCaseIds": sorted({copy["caseId"] for case in cases for copy in case["copies"]
                                           if "101" in copy["observedSegmentTypes"]}),
        "coverageCredit": 0,
        "semanticMatchesConfirmed": 0,
        "executionPerformed": False,
    }
    direct_test_candidate = roster["exactTestCandidates"]
    return {
        "reviewId": "ATL105-FULL-CORPUS-BATCH-011-DRIVER-IDENTIFICATION",
        "reviewDate": date.today().isoformat(),
        "authority": "PRELIMINARY_ANALYST_REVIEW_NOT_AUTHORIZED_APPROVAL",
        "status": "REVIEW_REQUIRED",
        "aiBusinessRequirement": {"id": ai_br_id, "statement": roster["statement"],
                                   "scenarioIds": roster["scenarioIds"], "caseIds": roster["caseIds"],
                                   "segments": roster["segments"], "elements": roster["elements"],
                                   "chainStatus": ai_chain.get("chainStatus"),
                                   "physicalPayloadPathCount": len(ai_chain.get("physicalPayloadPaths", []))},
        "sourceRule": {
            "id": "ENT-ELEM-31",
            "element": "31",
            "name": "Driver/Identification Number",
            "characterType": "N",
            "maximumLengthBytes": 10,
            "representation": "Variable length of up to 10 digits",
            "validValues": "Any numeric representation",
            "applicability": "Issuer-dependent; if required, found in Segment 101",
            "alignment": "AI numeric / maximum-10-byte claim is source-aligned; no fixed-width or mandatory-presence inference",
            "sourceEvidence": evidence,
            "sourceTextSha256": source_hash,
        },
        "testCrosswalk": {
            "exactAnchorCandidates": direct_test_candidate,
            "rankedCandidatesAreLeadsOnly": True,
            "rankedCandidates": test_candidate_summaries(package, candidates, test_records),
            "directRuleDisposition": "SOURCE_TEST_CHARACTER_CLASS_CONFLICT" if direct_test_candidate else "NO_EXACT_CANDIDATE",
            "directRuleConflict": "ATL105 specifies numeric (N); BR-RULE-SEG101-R-012 says alphanumeric. Its applicability is conditional ('when populated'), but its chain remains draft/review-required.",
        },
        "cases": cases,
        "summary": summary,
        "analystDisposition": {
            "sourceAlignment": "AI_FORMAT_CLAIM_ALIGNED_TO_ELEMENT31",
            "caseEvidence": "THREE_LINKED_CASES_DO_NOT_EXERCISE_ELEMENT31; sidecars mark Element 31 omitted by composer",
            "testCounterpart": "DIRECT_TEST_CANDIDATE_CONFLICTS_ON_CHARACTER_CLASS_AND_IS_NOT_APPROVED",
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
            "rawDriverIdentificationValuesWritten": False,
            "executionPerformed": False,
            "semanticMatchesConfirmed": 0,
            "coverageCredit": 0,
        },
    }


def self_test() -> None:
    expected = {"0": "SOURCE_FORMAT_VALID", "123": "SOURCE_FORMAT_VALID", "1234567890": "SOURCE_FORMAT_VALID",
                "12345678901": "INVALID_WIDTH", "12A": "INVALID_NONNUMERIC", "": "INVALID_EMPTY_NUMERIC_REPRESENTATION",
                123: "INVALID_REPRESENTATION", "１２": "INVALID_NONNUMERIC"}
    for value, result in expected.items():
        if classify(value) != result:
            raise AssertionError(f"Element 31 classification mismatch for {type(value).__name__}")
    print("PASS: 8 Element 31 representation/width controls; output classifications redact raw values.")


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--run-root", type=Path, default=RUN_ROOT)
    parser.add_argument("--atl105-root", type=Path, default=ATL105)
    parser.add_argument("--review-v3", type=Path, default=REVIEW_V3)
    parser.add_argument("--test-package", type=Path, default=TEST_PACKAGE)
    parser.add_argument("--ai-br-id", default=DEFAULT_AI_BR_ID)
    parser.add_argument("--source-tree", type=Path, action="append")
    parser.add_argument("--output", type=Path, default=OUTPUT / "driver-identification-review.json")
    parser.add_argument("--manifest", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
        return 0
    source_trees = tuple(args.source_tree or DEFAULT_SOURCE_TREES)
    report = build_report(args.run_root, args.atl105_root, args.review_v3, args.test_package,
                          args.ai_br_id, source_trees)
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
    manifest = {"manifestId": "ATL105-FULL-CORPUS-BATCH-011-DRIVER-IDENTIFICATION",
                "reviewDate": date.today().isoformat(), "status": "REVIEW_REQUIRED",
                "artifacts": artifacts, "inputs": inputs, "guardrails": report["guardrails"]}
    manifest_path.parent.mkdir(parents=True, exist_ok=True)
    manifest_path.write_text(json.dumps(manifest, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    print(json.dumps({"report": str(args.output), "manifest": str(manifest_path),
                      "caseCount": report["summary"]["caseCount"],
                      "caseIdsWithElement31": report["summary"]["caseIdsWithElement31"],
                      "caseIdsWithoutElement31": report["summary"]["caseIdsWithoutElement31"],
                      "segment101BoundCaseIds": report["summary"]["segment101BoundCaseIds"],
                      "coverageCredit": 0}, ensure_ascii=True))
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except (OSError, ValueError, json.JSONDecodeError) as error:
        print(f"ERROR: {type(error).__name__}: {error}", file=sys.stderr)
        raise SystemExit(2)