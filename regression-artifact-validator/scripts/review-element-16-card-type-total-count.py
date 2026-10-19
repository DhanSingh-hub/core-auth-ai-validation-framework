#!/usr/bin/env python3
"""Audit AI Element 16 count artifacts against ATL105 and segment-specific Test rules."""

from __future__ import annotations

import argparse
import csv
import gzip
import hashlib
import json
import re
import sys
from collections import Counter
from datetime import date
from pathlib import Path
from typing import Any


ROOT = Path(__file__).resolve().parents[1]
ATL105 = ROOT / "specifications" / "ATL105"
RUN_ROOT = ATL105 / "test-input" / "ai-solution" / "runs" / "2026-10-19" / "Run2"
REVIEW_V3 = ATL105 / "test-output" / "ai-solution-independent-review" / "2026-10-19-all-ai-br-chain-test-br-review-v3"
TEST_PACKAGE = ATL105 / "test-output" / "test-solution-independent-review" / "complete-all-test-solution-br-ts-tc-td-package.json"
OUTPUT = ATL105 / "test-output" / "test-solution-independent-review" / "2026-10-19-full-corpus-review-batch-010"
SOURCE_TREES = (
    (Path("test_data_10_oct/test_data_10_oct/candidates/qe_shaped_test_data"), "10_OCT_TEST_DATA_CANDIDATE"),
    (Path("test_generation/test_generation/candidates/qe_shaped_test_data"), "10_OCT_TEST_GENERATION_CANDIDATE"),
)
DEFAULT_AI_BR_ID = "REQ-SRC-ATL105-PDF-001:019"
TEST_RULE_BY_SEGMENT = {"105": "SEG105-R-015", "119": "SEG119-R-025"}


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for chunk in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(chunk)
    return digest.hexdigest()


def normalize(value: Any) -> str:
    return re.sub(r"[^a-z0-9]", "", str(value).lower())


def read_json(path: Path) -> tuple[Any | None, str | None]:
    try:
        return json.loads(path.read_text(encoding="utf-8-sig")), None
    except (OSError, UnicodeError, json.JSONDecodeError) as error:
        return None, type(error).__name__


def source_domain(atl105_root: Path) -> tuple[int, int, str, str]:
    domain_path = atl105_root / "docs" / "specs" / "kb" / "elements" / "value-domains.json"
    source_path = atl105_root / "docs" / "specs" / "extracted_text.txt"
    domains = json.loads(domain_path.read_text(encoding="utf-8-sig"))["domains"]
    entries = [entry for entry in domains if entry.get("element") == "16"]
    if len(entries) != 1 or entries[0].get("kind") != "range":
        raise ValueError("Element 16 must have one source-backed range entry")
    minimum, maximum = int(entries[0]["min"]), int(entries[0]["max"])
    source = " ".join(source_path.read_text(encoding="utf-8-sig").split())
    start = source.find("Number: 16 Name: Card Type Total Count")
    end = source.find("Number: 17 Name:", start + 1)
    definition = source[start:end] if start >= 0 and end > start else ""
    if (minimum, maximum) != (1, 99999) or "Fixed length of five digits" not in definition or "00001–99999" not in definition:
        raise ValueError("Element 16 N5/range rule does not match its source definition")
    return minimum, maximum, sha256(source_path), sha256(domain_path)


def classify(value: Any, minimum: int, maximum: int) -> str:
    if not isinstance(value, str):
        return "INVALID_REPRESENTATION"
    if value == "":
        return "INVALID_EMPTY_REQUIRED_BUCKET_COUNT"
    if not value.isascii() or not value.isdigit():
        return "INVALID_NONNUMERIC"
    if len(value) != 5:
        return "INVALID_WIDTH"
    number = int(value)
    if not minimum <= number <= maximum:
        return "INVALID_RANGE"
    return "SOURCE_DOMAIN_VALID"


def bucket_counts(payload: Any, path: tuple[str, ...] = (), segment: str | None = None) -> list[dict[str, Any]]:
    rows: list[dict[str, Any]] = []
    if isinstance(payload, dict):
        local_segment = segment
        if isinstance(payload.get("SegmentType"), str):
            local_segment = payload["SegmentType"]
        for key, value in payload.items():
            child = path + (str(key),)
            if normalize(key) == "cardtypetotalcount":
                rows.append({"path": child, "segment": local_segment, "value": value})
            rows.extend(bucket_counts(value, child, local_segment))
    elif isinstance(payload, list):
        for index, value in enumerate(payload):
            rows.extend(bucket_counts(value, path + (f"[{index}]",), segment))
    return rows


def sidecar_count_rows(metadata: Any) -> list[dict[str, Any]]:
    fields = metadata.get("fields", []) if isinstance(metadata, dict) else []
    if not isinstance(fields, list):
        return []
    return [field for field in fields if isinstance(field, dict) and "cardtypetotalcount" in normalize(
        " ".join(str(field.get(key, "")) for key in ("element", "specElementName", "name")))]


def bind_counts(payload_rows: list[dict[str, Any]], metadata_rows: list[dict[str, Any]],
                minimum: int, maximum: int) -> list[dict[str, Any]]:
    output = []
    segments = {str(row.get("segment")) for row in metadata_rows if row.get("segment") is not None}
    for segment in sorted(segments):
        physical = [row for row in payload_rows if str(row.get("segment")) == segment]
        metadata = [row for row in metadata_rows if str(row.get("segment")) == segment]
        if len(physical) != len(metadata):
            output.append({
                "segment": segment,
                "payloadOccurrences": len(physical),
                "sidecarOccurrences": len(metadata),
                "bindingStatus": "OCCURRENCE_COUNT_MISMATCH",
                "observedResults": [],
                "sidecarValuesMatchPayload": None,
            })
            continue
        output.append({
            "segment": segment,
            "payloadOccurrences": len(physical),
            "sidecarOccurrences": len(metadata),
            "bindingStatus": "BOUND_BY_SEGMENT_AND_ORDER" if physical else "NO_PAYLOAD_BUCKET",
            "observedResults": [classify(item["value"], minimum, maximum) for item in physical],
            "sidecarValuesMatchPayload": [item["value"] for item in physical] == [item.get("value") for item in metadata],
            "sourceRuleCandidate": TEST_RULE_BY_SEGMENT.get(segment),
            "payloadPaths": ["/" + "/".join(item["path"]) for item in physical],
            "sidecarOrigins": [item.get("dataSource") for item in metadata],
        })
    return output


def case_roster(register_path: Path, ai_br_id: str) -> dict[str, Any]:
    csv.field_size_limit(100000000)
    with register_path.open(encoding="utf-8-sig", newline="") as stream:
        rows = [row for row in csv.DictReader(stream) if row.get("aiBrId") == ai_br_id]
    if len(rows) != 1:
        raise ValueError(f"Expected exactly one AI BR row for {ai_br_id}, found {len(rows)}")
    row = rows[0]
    return {"aiBrId": ai_br_id, "statement": row.get("aiStatement"),
            "caseIds": json.loads(row.get("aiCaseIds") or "[]"),
            "scenarioIds": json.loads(row.get("aiScenarioIds") or "[]")}


def chain_context(chain_path: Path, ai_br_id: str, cases_path: Path, test_chain_path: Path) -> dict[str, Any]:
    ai_record = None
    with gzip.open(chain_path, "rt", encoding="utf-8") as stream:
        for line in stream:
            record = json.loads(line)
            if record.get("aiBrId") == ai_br_id:
                ai_record = record
                break
    if ai_record is None:
        raise ValueError(f"AI chain evidence is missing {ai_br_id}")
    test_records = {}
    wanted = set(TEST_RULE_BY_SEGMENT.values())
    with gzip.open(test_chain_path, "rt", encoding="utf-8") as stream:
        for line in stream:
            record = json.loads(line)
            if record.get("testBrId") in wanted:
                test_records[record["testBrId"]] = record
    return {
        "aiChainStatus": ai_record.get("chainStatus"),
        "aiScenarioIds": [item.get("id") for item in ai_record.get("scenarios", [])],
        "aiCaseIds": [item.get("id") for item in ai_record.get("cases", [])],
        "physicalPayloadPathCount": len(ai_record.get("physicalPayloadPaths", [])),
        "testRuleCandidates": {
            rule: {
                "businessRequirementStatus": test_records.get(rule, {}).get("br", {}).get("status"),
                "scenarioIds": [item.get("id") for item in test_records.get(rule, {}).get("scenarios", [])],
                "scenarioStatuses": [item.get("status") for item in test_records.get(rule, {}).get("scenarios", [])],
                "caseIds": [item.get("id") for item in test_records.get(rule, {}).get("cases", [])],
                "caseStatuses": [item.get("status") for item in test_records.get(rule, {}).get("cases", [])],
                "testDataIds": [item.get("id") for item in test_records.get(rule, {}).get("data", [])],
                "testDataReadiness": [item.get("readiness") for item in test_records.get(rule, {}).get("data", [])],
                "testDataExpectedValidation": [item.get("expectedValidation") for item in test_records.get(rule, {}).get("data", [])],
                "allRecordsReviewRequired": (
                    "REVIEW_REQUIRED" in str(test_records.get(rule, {}).get("br", {}).get("status", ""))
                    and all(item.get("status") == "REVIEW_REQUIRED" for item in test_records.get(rule, {}).get("scenarios", [])
                            + test_records.get(rule, {}).get("cases", []))
                    and all(item.get("readiness") == "REVIEW_REQUIRED" and item.get("expectedValidation") == "REVIEW_REQUIRED"
                            for item in test_records.get(rule, {}).get("data", []))
                ),
            } for rule in sorted(wanted)
        },
    }


def audit_copy(case_id: str, directory: Path, source_kind: str, run_root: Path,
               minimum: int, maximum: int) -> dict[str, Any]:
    payload_path, sidecar_path = directory / f"{case_id}.json", directory / f"{case_id}.meta.json"
    payload, payload_error = read_json(payload_path)
    metadata, metadata_error = read_json(sidecar_path)
    payload_rows = bucket_counts(payload) if payload_error is None else []
    metadata_rows = sidecar_count_rows(metadata) if metadata_error is None else []
    return {
        "sourceKind": source_kind,
        "payloadPath": str(payload_path.relative_to(run_root)).replace("\\", "/"),
        "sidecarPath": str(sidecar_path.relative_to(run_root)).replace("\\", "/"),
        "payloadSha256": sha256(payload_path) if payload_path.is_file() else None,
        "sidecarSha256": sha256(sidecar_path) if sidecar_path.is_file() else None,
        "messageFamily": next(iter(payload), None) if isinstance(payload, dict) else None,
        "payloadParseError": payload_error,
        "sidecarParseError": metadata_error,
        "sidecarCaseIdMatches": metadata.get("testCaseId") == case_id if isinstance(metadata, dict) else False,
        "sourceBatchSha256Present": bool(metadata.get("sourceBatchSha256")) if isinstance(metadata, dict) else False,
        "quantityCountFieldOccurrences": len(payload_rows),
        "sidecarElement16Rows": len(metadata_rows),
        "segmentResults": bind_counts(payload_rows, metadata_rows, minimum, maximum),
        "sidecarTargetOrigins": [row.get("dataSource") for row in metadata_rows],
        "rawCountValuesWritten": False,
    }


def audit_case(case_id: str, run_root: Path, minimum: int, maximum: int) -> dict[str, Any]:
    copies = [audit_copy(case_id, run_root / path, kind, run_root, minimum, maximum)
              for path, kind in SOURCE_TREES]
    hashes = [(copy["payloadSha256"], copy["sidecarSha256"]) for copy in copies]
    observed_segments = sorted({item["segment"] for item in copies[0]["segmentResults"]})
    scope_status = "SEGMENT_105_OR_119" if observed_segments and set(observed_segments) <= {"105", "119"} else "SCOPE_REVIEW_REQUIRED"
    actual_segments = sorted({row["segment"] for copy in copies[:1] for row in copy["segmentResults"]})
    return {
        "caseId": case_id,
        "observedPayloadSegments": actual_segments,
        "scopeBindingStatus": scope_status,
        "candidateCopiesByteIdentical": hashes[0] == hashes[1],
        "sidecarCaseIdsMatch": all(copy["sidecarCaseIdMatches"] for copy in copies),
        "copies": copies,
    }


def build_report(run_root: Path, atl105_root: Path, review_v3: Path, ai_br_id: str) -> dict[str, Any]:
    register = case_roster(review_v3 / "all-ai-br-review-register.csv", ai_br_id)
    minimum, maximum, source_hash, domain_hash = source_domain(atl105_root)
    if not register["caseIds"] or len(register["caseIds"]) != len(set(register["caseIds"])):
        raise ValueError("AI BR case roster is empty or contains duplicate case IDs")
    chain = chain_context(review_v3 / "ai-br-chain-evidence.jsonl.gz", ai_br_id,
                          review_v3 / "all-ai-br-review-register.csv", review_v3 / "test-chain-evidence.jsonl.gz")
    cases = [audit_case(case_id, run_root, minimum, maximum) for case_id in register["caseIds"]]
    results = [segment for case in cases for copy in case["copies"][:1] for segment in copy["segmentResults"]]
    result_counts = {
        segment: dict(Counter(status for item in results if item["segment"] == segment for status in item["observedResults"]))
        for segment in ("105", "119")
    }
    return {
        "artifact": "ELEMENT16_CARD_TYPE_TOTAL_COUNT_FULL_CHAIN_REVIEW",
        "reviewId": "ATL105-ELEMENT16-CARD-TYPE-TOTAL-COUNT-REVIEW-001",
        "reviewDate": date.today().isoformat(),
        "status": "REVIEW_REQUIRED",
        "aiBusinessRequirement": {"id": ai_br_id, "statement": register["statement"], "caseCount": len(register["caseIds"])},
        "source": {
            "specification": "ATL105",
            "version": "2026-3",
            "element": "16",
            "characterType": "N",
            "representation": "fixed five digits",
            "minimum": minimum,
            "maximum": maximum,
            "sourceTextSha256": source_hash,
            "valueDomainSha256": domain_hash,
            "sourceRule": "Card Type Total Count in the Totals Response for a card type",
        },
        "testCrosswalkCandidates": {
            "105": {"ruleId": "SEG105-R-015", "scope": "Totals Data Segment bucket count; compound rule also includes Grand Total, label and amount"},
            "119": {"ruleId": "SEG119-R-025", "scope": "Card Type Total Count required per Segment 119 bucket"},
            "semanticMappingApproved": False,
        },
        "chainEvidence": chain,
        "cases": cases,
        "summary": {
            "caseCount": len(cases),
            "casesWithSegment105": [case["caseId"] for case in cases if "105" in case["observedPayloadSegments"]],
            "casesWithSegment119": [case["caseId"] for case in cases if "119" in case["observedPayloadSegments"]],
            "casesOutsideExpectedTotalsSegments": [case["caseId"] for case in cases if case["scopeBindingStatus"] == "SCOPE_REVIEW_REQUIRED"],
            "duplicateCopyMismatches": [case["caseId"] for case in cases if not case["candidateCopiesByteIdentical"]],
            "caseIdMismatches": [case["caseId"] for case in cases if not case["sidecarCaseIdsMatch"]],
            "payloadParseFailures": [case["caseId"] for case in cases if any(copy["payloadParseError"] for copy in case["copies"])],
            "sidecarParseFailures": [case["caseId"] for case in cases if any(copy["sidecarParseError"] for copy in case["copies"])],
            "bucketOccurrences": sum(item["payloadOccurrences"] for item in results),
            "observedResultsBySegment": result_counts,
            "sourceDomainValid": sum(status == "SOURCE_DOMAIN_VALID" for item in results for status in item["observedResults"]),
            "invalidByFormatOrRange": sum(status.startswith("INVALID_") for item in results for status in item["observedResults"]),
            "sidecarPayloadMismatches": sum(item["sidecarValuesMatchPayload"] is False for item in results),
            "sidecarsWithoutSourceBatchDigest": [case["caseId"] for case in cases if any(not copy["sourceBatchSha256Present"] for copy in case["copies"])],
            "rawElement16ValuesWritten": False,
        },
        "guardrails": {
            "producerLabelUsedAsExpectedOutcome": False,
            "producerResponseOracleAccepted": False,
            "Segment105And119Conflated": False,
            "presenceOrApplicabilityPromoted": False,
            "semanticMatchConfirmed": False,
            "coverageCredit": 0,
            "testSolutionExecutionCertified": False,
            "officialTestBaselineModified": False,
        },
    }


def self_test() -> None:
    tests = {"00001": "SOURCE_DOMAIN_VALID", "99999": "SOURCE_DOMAIN_VALID", "00000": "INVALID_RANGE",
             "100000": "INVALID_WIDTH", "12A45": "INVALID_NONNUMERIC", "": "INVALID_EMPTY_REQUIRED_BUCKET_COUNT", 12345: "INVALID_REPRESENTATION"}
    for value, expected in tests.items():
        if classify(value, 1, 99999) != expected:
            raise AssertionError(f"Element 16 status mismatch for {type(value).__name__}")
    if classify("00001", 1, 99999) != "SOURCE_DOMAIN_VALID" or classify("99999", 1, 99999) != "SOURCE_DOMAIN_VALID":
        raise AssertionError("Element 16 source boundaries changed unexpectedly")
    print("PASS: 7 source-domain controls, lower/upper valid boundaries; case roster remains input-driven.")


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--run-root", type=Path, default=RUN_ROOT)
    parser.add_argument("--atl105-root", type=Path, default=ATL105)
    parser.add_argument("--review-v3", type=Path, default=REVIEW_V3)
    parser.add_argument("--ai-br-id", default=DEFAULT_AI_BR_ID)
    parser.add_argument("--output", type=Path, default=OUTPUT / "element-16-card-type-total-count-review.json")
    parser.add_argument("--manifest", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
        return 0
    report = build_report(args.run_root, args.atl105_root, args.review_v3, args.ai_br_id)
    args.output.parent.mkdir(parents=True, exist_ok=True)
    args.output.write_text(json.dumps(report, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    manifest_path = args.manifest or args.output.with_name("manifest.json")
    manifest_path.parent.mkdir(parents=True, exist_ok=True)
    source_path = args.atl105_root / "docs" / "specs" / "extracted_text.txt"
    domain_path = args.atl105_root / "docs" / "specs" / "kb" / "elements" / "value-domains.json"
    review_register = args.review_v3 / "all-ai-br-review-register.csv"
    ai_evidence = args.review_v3 / "ai-br-chain-evidence.jsonl.gz"
    test_evidence = args.review_v3 / "test-chain-evidence.jsonl.gz"
    readme = args.output.with_name("README.md")
    artifacts = [
        {"path": str(args.output), "sha256": sha256(args.output), "role": "category-review-report"},
        {"path": str(Path(__file__).resolve()), "sha256": sha256(Path(__file__).resolve()), "role": "category-review-runner"},
        {"path": str(readme), "sha256": sha256(readme), "role": "review-summary"},
        {"path": str(source_path), "sha256": sha256(source_path), "role": "source-text"},
        {"path": str(domain_path), "sha256": sha256(domain_path), "role": "source-domain-catalog"},
    ]
    inputs = [
        {"path": str(path), "sha256": sha256(path), "role": role}
        for path, role in ((review_register, "AI-BR-roster"), (ai_evidence, "AI-chain-evidence"),
                           (test_evidence, "Test-chain-evidence"), (TEST_PACKAGE, "Test-package"),
                              )
    ]
    for case in report["cases"]:
        for copy in case["copies"]:
            for key, role in (("payloadPath", "AI-candidate-payload"), ("sidecarPath", "AI-candidate-sidecar")):
                path = args.run_root / copy[key]
                inputs.append({"caseId": case["caseId"], "path": str(path), "sha256": copy["payloadSha256"] if key == "payloadPath" else copy["sidecarSha256"], "role": role})
    manifest = {"manifestId": "ATL105-ELEMENT16-CARD-TYPE-COUNT-REVIEW-" + args.ai_br_id.replace(":", "-"), "reviewDate": date.today().isoformat(),
                "status": "REVIEW_REQUIRED", "artifacts": artifacts, "inputs": inputs, "guardrails": report["guardrails"]}
    manifest_path.write_text(json.dumps(manifest, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    summary = report["summary"]
    print(json.dumps({"report": str(args.output), "manifest": str(manifest_path), "cases": summary["caseCount"],
                      "segment105Cases": len(summary["casesWithSegment105"]), "segment119Cases": len(summary["casesWithSegment119"]),
                      "invalidCounts": summary["invalidByFormatOrRange"],
                      "coverageCredit": 0}, ensure_ascii=True))
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except (OSError, ValueError, json.JSONDecodeError) as error:
        print(f"ERROR: {type(error).__name__}: {error}", file=sys.stderr)
        raise SystemExit(2)