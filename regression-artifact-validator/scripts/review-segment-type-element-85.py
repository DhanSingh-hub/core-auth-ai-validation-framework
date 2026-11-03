#!/usr/bin/env python3
"""Bind linked AI Element 85 observations to payload containers and source-backed fixed values."""

from __future__ import annotations

import argparse
import hashlib
import json
import re
import sys
from pathlib import Path
from typing import Any

ROOT = Path(__file__).resolve().parents[1]
ATL105 = ROOT / "specifications" / "ATL105"
RUN_ROOT = ATL105 / "test-input" / "ai-solution" / "runs" / "2026-10-19" / "Run1"
OUTPUT = ATL105 / "test-output" / "test-solution-independent-review" / "segment-type-element-85-review-v2"
PRIMARY = Path("test_data_10_oct/test_data_10_oct/candidates/qe_shaped_test_data")
DUPLICATE = Path("test_generation/test_generation/candidates/qe_shaped_test_data")
V1_REVIEW = ATL105 / "test-output" / "test-solution-independent-review" / "segment-type-element-85-review-v1.json"


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for chunk in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(chunk)
    return digest.hexdigest()


def norm(value: Any) -> str:
    return re.sub(r"[^a-z0-9]", "", str(value).lower())


def read_json(path: Path) -> tuple[Any | None, str | None]:
    try:
        return json.loads(path.read_text(encoding="utf-8-sig")), None
    except (OSError, UnicodeError, json.JSONDecodeError) as error:
        return None, type(error).__name__


def source_values(atl105_root: Path) -> tuple[dict[str, str], str, str, str]:
    gate_path = atl105_root / "test-output" / "test-solution-independent-review" / "atl105-fixed-segment-type-gate.json"
    source_path = atl105_root / "docs" / "specs" / "extracted_text.txt"
    gate, error = read_json(gate_path)
    if error or not isinstance(gate, dict) or gate.get("status") != "SOURCE_DERIVED_REVIEW_REQUIRED":
        raise ValueError("Source-derived fixed Segment Type gate missing or invalid")
    fixed = {
        str(item.get("segment")): str(item.get("value"))
        for item in gate.get("assertions", [])
        if item.get("element") == "85" and item.get("status") == "PARTIAL_SOURCE_BACKED_REVIEW_REQUIRED"
    }
    if len(fixed) != gate.get("summary", {}).get("partiallyBackedRuleCount"):
        raise ValueError("Source-backed fixed-value count does not match gate summary")
    return fixed, sha256(gate_path), sha256(source_path), gate.get("specificationVersion", "")


def payload_segment_types(node: Any, path: tuple[str, ...] = ()) -> list[dict[str, Any]]:
    found: list[dict[str, Any]] = []
    if isinstance(node, dict):
        for key, value in node.items():
            child = path + (str(key),)
            if norm(key) in {"segmenttype", "segmenttypeelement85", "85"}:
                found.append({"pathParts": child, "value": value})
            found.extend(payload_segment_types(value, child))
    elif isinstance(node, list):
        for index, value in enumerate(node):
            found.extend(payload_segment_types(value, path + (f"[{index}]",)))
    return found


def sidecar_segment_types(metadata: Any) -> list[dict[str, Any]]:
    fields = metadata.get("fields", []) if isinstance(metadata, dict) else []
    if not isinstance(fields, list):
        return []
    rows = []
    for field in fields:
        if not isinstance(field, dict):
            continue
        element, spec_name = norm(field.get("element", "")), norm(field.get("specElementName", ""))
        if element in {"segmenttype", "85"} or spec_name in {"segmenttype", "element85segmenttype"}:
            rows.append(field)
    return rows


def bind_row(row: dict[str, Any], payload_fields: list[dict[str, Any]], fixed_values: dict[str, str]) -> dict[str, Any]:
    segment = str(row.get("segment", ""))
    friendly = norm(row.get("segmentFriendlyName", ""))
    matches = [field for field in payload_fields if friendly and any(norm(parent) == friendly for parent in field["pathParts"][:-1])]
    if len(matches) == 1:
        matched = matches[0]
        value = matched["value"]
        binding = "UNIQUE_CONTAINER_BIND"
        path = "/" + "/".join(matched["pathParts"])
        sidecar_match = value == row.get("value")
    else:
        value, path, sidecar_match = None, None, None
        binding = "CONTAINER_NOT_FOUND" if not matches else "AMBIGUOUS_CONTAINER_BIND"
    format_ok = isinstance(value, str) and bool(re.fullmatch(r"[0-9]{3}", value))
    expected = fixed_values.get(segment)
    return {
        "segment": segment,
        "friendlyName": row.get("segmentFriendlyName"),
        "dataSource": row.get("dataSource"),
        "nameSource": row.get("nameSource"),
        "bindingStatus": binding,
        "payloadPath": path,
        "sidecarValueMatchesPayload": sidecar_match,
        "rawValueOmitted": True,
        "observedLength": len(value) if isinstance(value, str) else None,
        "numericThreeDigitFormat": format_ok if value is not None else None,
        "sourceFixedValueAvailable": expected is not None,
        "matchesSourceFixedValue": value == expected if expected is not None and value is not None else None,
    }


def audit_copy(case_id: str, directory: Path, source_kind: str, run_root: Path,
               fixed_values: dict[str, str]) -> dict[str, Any]:
    payload_path, sidecar_path = directory / f"{case_id}.json", directory / f"{case_id}.meta.json"
    payload, payload_error = read_json(payload_path)
    metadata, metadata_error = read_json(sidecar_path)
    payload_rows = payload_segment_types(payload) if payload_error is None else []
    sidecar_rows = sidecar_segment_types(metadata) if metadata_error is None else []
    bound_rows = [bind_row(row, payload_rows, fixed_values) for row in sidecar_rows]
    targets = [row for row in bound_rows if row["dataSource"] == "deliberate_violation"]
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
        "payloadOccurrenceCount": len(payload_rows),
        "sidecarRowCount": len(sidecar_rows),
        "boundRows": bound_rows,
        "explicitTargetRowCount": len(targets),
        "negativeClass": metadata.get("negativeClass") if isinstance(metadata, dict) else None,
        "scenarioType": metadata.get("scenarioType") if isinstance(metadata, dict) else None,
        "producerLabelIsNotExpectedOutcome": True,
    }


def audit_case(case_id: str, run_root: Path, fixed_values: dict[str, str]) -> dict[str, Any]:
    copies = [
        audit_copy(case_id, run_root / PRIMARY, "10_OCT_TEST_DATA_CANDIDATE", run_root, fixed_values),
        audit_copy(case_id, run_root / DUPLICATE, "10_OCT_TEST_GENERATION_CANDIDATE", run_root, fixed_values),
    ]
    hashes = [(copy["payloadSha256"], copy["sidecarSha256"]) for copy in copies]
    primary = copies[0]
    targets = [row for row in primary["boundRows"] if row["dataSource"] == "deliberate_violation"]
    if len(targets) == 1 and targets[0]["bindingStatus"] == "UNIQUE_CONTAINER_BIND":
        target_status = "UNIQUE_TARGET_BOUND"
    elif not targets:
        target_status = "NO_EXPLICIT_SINGLE_TARGET"
    else:
        target_status = "TARGET_BINDING_REQUIRES_REVIEW"
    return {
        "caseId": case_id,
        "candidateCopiesByteIdentical": hashes[0] == hashes[1],
        "payloadSidecarCaseIdsMatch": all(copy["sidecarCaseIdMatches"] for copy in copies),
        "targetBindingStatus": target_status,
        "targetRow": targets[0] if len(targets) == 1 else None,
        "negativeClass": primary["negativeClass"],
        "copies": copies,
    }


def build_report(run_root: Path, atl105_root: Path) -> dict[str, Any]:
    roster, error = read_json(V1_REVIEW)
    if error or not isinstance(roster, dict):
        raise ValueError("Element 85 v1 linked-case roster missing or invalid")
    fixed_values, gate_hash, source_hash, version = source_values(atl105_root)
    cases = [audit_case(case_id, run_root, fixed_values) for case_id in roster["aiCasePopulation"]["allLinkedCaseIds"]]
    rows = [row for case in cases for row in case["copies"][0]["boundRows"]]
    targets = [case["targetRow"] for case in cases if case["targetRow"] is not None]
    return {
        "artifact": "SEGMENT_TYPE_ELEMENT_85_FULL_PAYLOAD_CONTAINER_BINDING_REVIEW",
        "reviewId": "ATL105-SEGMENT-TYPE-E85-REVIEW-002",
        "supersedesReviewId": "ATL105-SEGMENT-TYPE-E85-REVIEW-001",
        "reviewDate": "2026-10-19",
        "status": "REVIEW_REQUIRED",
        "source": {
            "specification": "ATL105",
            "version": version,
            "element": "85",
            "format": "exactly three numeric digits",
            "sourceBackedFixedSegmentCount": len(fixed_values),
            "fixedValueGateSha256": gate_hash,
            "sourceTextSha256": source_hash,
            "sourceGateMismatchRuleCountNotPromoted": 2,
        },
        "runRoot": str(run_root),
        "cases": cases,
        "summary": {
            "linkedCaseCount": len(cases),
            "payloadParseFailures": [c["caseId"] for c in cases if any(x["payloadParseError"] for x in c["copies"])],
            "sidecarParseFailures": [c["caseId"] for c in cases if any(x["sidecarParseError"] for x in c["copies"])],
            "duplicateCopyMismatches": [c["caseId"] for c in cases if not c["candidateCopiesByteIdentical"]],
            "caseIdentityMismatches": [c["caseId"] for c in cases if not c["payloadSidecarCaseIdsMatch"]],
            "payloadSegmentTypeOccurrences": sum(x["payloadOccurrenceCount"] for c in cases for x in c["copies"][:1]),
            "sidecarSegmentTypeRows": sum(x["sidecarRowCount"] for c in cases for x in c["copies"][:1]),
            "uniqueContainerBindings": sum(r["bindingStatus"] == "UNIQUE_CONTAINER_BIND" for r in rows),
            "unboundRows": sum(r["bindingStatus"] == "CONTAINER_NOT_FOUND" for r in rows),
            "ambiguousRows": sum(r["bindingStatus"] == "AMBIGUOUS_CONTAINER_BIND" for r in rows),
            "sidecarPayloadMismatches": sum(r["sidecarValueMatchesPayload"] is False for r in rows),
            "rowsWithSourceFixedValue": sum(r["sourceFixedValueAvailable"] for r in rows),
            "rowsMatchingSourceFixedValue": sum(r["matchesSourceFixedValue"] is True for r in rows),
            "rowsDifferingFromSourceFixedValue": sum(r["matchesSourceFixedValue"] is False for r in rows),
            "explicitTargetCases": len(targets),
            "explicitTargetsWithValidN3Format": sum(r["numericThreeDigitFormat"] is True for r in targets),
            "explicitTargetsWithInvalidN3Format": sum(r["numericThreeDigitFormat"] is False for r in targets),
            "explicitTargetsWithBackedFixedValue": sum(r["sourceFixedValueAvailable"] for r in targets),
            "explicitTargetsDifferingFromBackedFixedValue": sum(r["matchesSourceFixedValue"] is False for r in targets),
            "boundaryCasesWithoutExplicitTarget": [c["caseId"] for c in cases if c["negativeClass"] == "boundary_at_limit" and c["targetBindingStatus"] == "NO_EXPLICIT_SINGLE_TARGET"],
            "sidecarsWithoutSourceBatchSha256": [c["caseId"] for c in cases if any(not x["sourceBatchSha256Present"] for x in c["copies"])],
            "rawSegmentTypeValuesWritten": False,
        },
        "guardrails": {
            "sidecarTargetUsedAsExpectedOutcome": False,
            "scenarioClassUsedAsExpectedOutcome": False,
            "clientOrSpecDerivedValuesUsedAsSourceOracle": False,
            "sourceMismatchGateValuesPromoted": False,
            "semanticMatchesConfirmed": 0,
            "coverageCredit": 0,
            "testSolutionExecutionCertified": False,
            "officialTestBaselineModified": False,
        },
    }


def self_test() -> None:
    unique_fields = payload_segment_types({"Financial Request": {"Standard Segment": {"SegmentType": "100"}}})
    repeated_fields = payload_segment_types({"Financial Request": {"Standard Segment": {"SegmentType": "100"},
                                                                    "Nested": {"Standard Segment": {"SegmentType": "100"}}}})
    target = {"segment": "100", "segmentFriendlyName": "Standard Segment", "value": "100", "dataSource": "deliberate_violation"}
    unique = bind_row(target, unique_fields, {"100": "100"})
    ambiguous = bind_row(target, repeated_fields, {"100": "100"})
    if unique["bindingStatus"] != "UNIQUE_CONTAINER_BIND" or unique["matchesSourceFixedValue"] is not True:
        raise AssertionError("Unique container/source comparison failed")
    if ambiguous["bindingStatus"] != "AMBIGUOUS_CONTAINER_BIND":
        raise AssertionError("Repeated friendly container must remain ambiguous")
    malformed = payload_segment_types({"Financial Request": {"Standard Segment": {"SegmentType": "1000"}}})
    if bind_row(target, malformed, {"100": "100"})["numericThreeDigitFormat"]:
        raise AssertionError("Four-digit value must fail N3 format")
    if bind_row(target, unique_fields, {})["sourceFixedValueAvailable"]:
        raise AssertionError("Unbacked segment fixed value must remain unavailable")
    print("PASS: unique/ambiguous container, fixed-value, N3 and unbacked-source controls; values omitted.")


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--run-root", type=Path, default=RUN_ROOT)
    parser.add_argument("--atl105-root", type=Path, default=ATL105)
    parser.add_argument("--output", type=Path, default=OUTPUT / "segment-type-element-85-review-v2.json")
    parser.add_argument("--manifest", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
        return 0
    report = build_report(args.run_root, args.atl105_root)
    args.output.parent.mkdir(parents=True, exist_ok=True)
    args.output.write_text(json.dumps(report, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    manifest_path = args.manifest or args.output.with_name("manifest-v2.json")
    manifest_path.parent.mkdir(parents=True, exist_ok=True)
    roster = args.atl105_root / "test-output" / "test-solution-independent-review" / "segment-type-element-85-review-v1.json"
    gate = args.atl105_root / "test-output" / "test-solution-independent-review" / "atl105-fixed-segment-type-gate.json"
    source = args.atl105_root / "docs" / "specs" / "extracted_text.txt"
    readme = args.output.with_name("README.md")
    artifacts = [
        {"path": str(args.output), "sha256": sha256(args.output), "role": "automated-review-report"},
        {"path": str(Path(__file__).resolve()), "sha256": sha256(Path(__file__).resolve()), "role": "review-runner"},
        {"path": str(roster), "sha256": sha256(roster), "role": "prior-case-roster"},
        {"path": str(gate), "sha256": sha256(gate), "role": "source-backed-fixed-value-gate"},
        {"path": str(source), "sha256": sha256(source), "role": "source-text"},
        {"path": str(readme), "sha256": sha256(readme), "role": "review-summary"},
    ]
    inputs = [
        {"caseId": case["caseId"], "sourceKind": copy["sourceKind"], "path": path, "sha256": copy[hash_key], "role": "ai-candidate-input"}
        for case in report["cases"] for copy in case["copies"]
        for path, hash_key in ((copy["payloadPath"], "payloadSha256"), (copy["sidecarPath"], "sidecarSha256"))
        if copy[hash_key]
    ]
    manifest = {"manifestId": "ATL105-SEGMENT-TYPE-E85-REVIEW-002-MANIFEST", "reviewDate": "2026-10-19",
                "status": "REVIEW_REQUIRED", "artifacts": artifacts, "candidateInputs": inputs,
                "guardrails": report["guardrails"]}
    manifest_path.write_text(json.dumps(manifest, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    print(json.dumps({"report": str(args.output), "manifest": str(manifest_path), **report["summary"]}, ensure_ascii=True))
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except (OSError, ValueError, json.JSONDecodeError) as error:
        print(f"ERROR: {type(error).__name__}: {error}", file=sys.stderr)
        raise SystemExit(2)