#!/usr/bin/env python3
"""Audit linked AI Quantity payloads and sidecars without inferring decimal context."""

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
RUN_ROOT = ATL105 / "test-input" / "ai-solution" / "runs" / "2026-10-16" / "Run5"
OUTPUT = ATL105 / "test-output" / "test-solution-independent-review" / "quantity-assumed-decimal-chain-draft-v2"
PRIMARY = Path("test_data_10_oct/test_data_10_oct/candidates/qe_shaped_test_data")
DUPLICATE = Path("test_generation/test_generation/candidates/qe_shaped_test_data")
CASE_SCOPES = {
    "TC-007281": "102",
    "TC-007282": "102",
    "TC-007283": "102",
    "TC-007284": "102",
    "TC-008093": "157",
    "TC-008094": "157",
    "TC-008095": "157",
    "TC-008096": "157",
}


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for chunk in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(chunk)
    return digest.hexdigest()


def normalized(value: str) -> str:
    return re.sub(r"[^a-z0-9]", "", value.lower())


def value_shape(value: Any) -> dict[str, Any]:
    if not isinstance(value, str):
        return {"type": type(value).__name__, "present": value is not None}
    return {
        "type": "string",
        "present": value != "",
        "characterCount": len(value),
        "numericText": value.isascii() and value.isdigit(),
        "rawValueOmitted": True,
    }


def find_quantity_segments(node: Any, path: str = "") -> list[dict[str, Any]]:
    found: list[dict[str, Any]] = []
    if isinstance(node, dict):
        segment_id = node.get("SegmentType")
        if str(segment_id) in {"102", "157"}:
            products = node.get("Products")
            quantities = []
            if isinstance(products, list):
                for index, product in enumerate(products):
                    if isinstance(product, dict) and "Quantity" in product:
                        quantities.append({
                            "path": f"{path}/Products[{index}]/Quantity",
                            "shape": value_shape(product.get("Quantity")),
                            "_value": product.get("Quantity"),
                            "unitOfMeasurePresent": isinstance(product.get("UnitOfMeasure"), str),
                        })
            if not isinstance(products, list) and "Quantity" in node:
                quantities.append({"path": f"{path}/Quantity", "shape": value_shape(node.get("Quantity")),
                                   "_value": node.get("Quantity")})
            found.append({"path": path or "/", "segmentId": str(segment_id), "quantityObservations": quantities})
        for key, value in node.items():
            found.extend(find_quantity_segments(value, f"{path}/{key}"))
    elif isinstance(node, list):
        for index, value in enumerate(node):
            found.extend(find_quantity_segments(value, f"{path}[{index}]"))
    return found


def quantity_sidecar_rows(metadata: Any) -> list[dict[str, Any]]:
    fields = metadata.get("fields", []) if isinstance(metadata, dict) else []
    if not isinstance(fields, list):
        return []
    rows = []
    for field in fields:
        if not isinstance(field, dict):
            continue
        label = " ".join(str(field.get(key, "")) for key in ("element", "specElementName", "name"))
        normalized_label = normalized(label)
        if "quantity" in normalized_label or "element81" in normalized_label or normalized_label == "81":
            rows.append({
                "segment": field.get("segment"),
                "label": label,
                "valueShape": value_shape(field.get("value")),
                "_value": field.get("value"),
                "dataSource": field.get("dataSource"),
                "provisional": field.get("provisional"),
            })
    return rows


def read_json(path: Path) -> tuple[Any | None, str | None]:
    try:
        return json.loads(path.read_text(encoding="utf-8-sig")), None
    except (OSError, UnicodeError, json.JSONDecodeError) as error:
        return None, type(error).__name__


def audit_copy(case_id: str, source_kind: str, directory: Path, run_root: Path) -> dict[str, Any]:
    payload_path, sidecar_path = directory / f"{case_id}.json", directory / f"{case_id}.meta.json"
    payload, payload_error = read_json(payload_path)
    metadata, metadata_error = read_json(sidecar_path)
    segments = find_quantity_segments(payload) if payload_error is None else []
    sidecar_rows = quantity_sidecar_rows(metadata) if metadata_error is None else []
    payload_values = [quantity.get("_value") for segment in segments for quantity in segment["quantityObservations"]]
    sidecar_values = [row.get("_value") for row in sidecar_rows]
    if payload_values and sidecar_rows:
        sidecar_matches_payload: bool | None = payload_values == sidecar_values
    elif payload_values or sidecar_rows:
        sidecar_matches_payload = False
    else:
        sidecar_matches_payload = None
    observed_scoped_values = [
        observation
        for segment in segments
        if segment["segmentId"] == CASE_SCOPES[case_id]
        for observation in segment["quantityObservations"]
    ]
    has_decimal_context = False
    if isinstance(metadata, dict):
        context_text = json.dumps(metadata.get("context", {}), ensure_ascii=True).lower()
        has_decimal_context = "assumeddecimal" in context_text or "decimalplaces" in context_text
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
        "observedProductSegments": [
            {**segment, "quantityObservations": [
                {key: value for key, value in quantity.items() if key != "_value"}
                for quantity in segment["quantityObservations"]
            ]}
            for segment in segments
        ],
        "sidecarQuantityRows": [
            {key: value for key, value in row.items() if key != "_value"}
            for row in sidecar_rows
        ],
        "sidecarMatchesPayload": sidecar_matches_payload,
        "linkedScopeQuantityCount": len(observed_scoped_values),
        "decimalPlacesExplicitlyObserved": has_decimal_context,
        "rawQuantityValuesWritten": False,
    }


def audit_case(case_id: str, scope: str, run_root: Path) -> dict[str, Any]:
    copies = [
        audit_copy(case_id, "10_OCT_TEST_DATA_CANDIDATE", run_root / PRIMARY, run_root),
        audit_copy(case_id, "10_OCT_TEST_GENERATION_CANDIDATE", run_root / DUPLICATE, run_root),
    ]
    hashes = [(copy["payloadSha256"], copy["sidecarSha256"]) for copy in copies]
    actual_ids = sorted({segment["segmentId"] for segment in copies[0]["observedProductSegments"]})
    return {
        "caseId": case_id,
        "linkedSourceScope": scope,
        "candidateCopiesByteIdentical": hashes[0] == hashes[1],
        "actualObservedProductSegmentIds": actual_ids,
        "linkedScopeQuantityObserved": any(copy["linkedScopeQuantityCount"] > 0 for copy in copies),
        "linkedScopeStatus": "OBSERVED_BUT_DECIMAL_CONTEXT_UNRESOLVED" if scope in actual_ids else "SOURCE_SCOPE_MISMATCH",
        "approvedArtifactStatus": "NOT_ESTABLISHED_BY_CANDIDATE_COPIES",
        "copies": copies,
    }


def validate_source(atl105_root: Path) -> tuple[str, str]:
    source_path = atl105_root / "docs" / "specs" / "extracted_text.txt"
    source = " ".join(source_path.read_text(encoding="utf-8-sig").split())
    source_102 = "Quantity value must contain a digit for every position of assumed decimal places. Example: If the quantity is 0.05 and the number of assumed decimal places is 2, then this should be represented as ‘205’. ‘25’ will be considered invalid."
    source_157 = source_102
    if source.count(source_102) < 2:
        raise ValueError("Element 81 assumed-decimal example is not present in both source segment definitions")
    return sha256(source_path), source_102


def self_test(atl105_root: Path) -> None:
    source_hash, evidence = validate_source(atl105_root)
    if len(source_hash) != 64 or "205" not in evidence or "25" not in evidence:
        raise AssertionError("Source example verification failed")
    if len(CASE_SCOPES) != 8 or sum(scope == "102" for scope in CASE_SCOPES.values()) != 4:
        raise AssertionError("Quantity cohort scope bindings changed")
    print("PASS: source example, separate 4+4 case scopes, and 8 unique candidate bindings; no values emitted.")


def build_report(run_root: Path, atl105_root: Path) -> dict[str, Any]:
    source_hash, _ = validate_source(atl105_root)
    cases = [audit_case(case_id, scope, run_root) for case_id, scope in CASE_SCOPES.items()]
    return {
        "artifact": "QUANTITY_ASSUMED_DECIMAL_AI_CANDIDATE_REVIEW",
        "reviewId": "ATL105-QUANTITY-ASSUMED-DECIMAL-REVIEW-002",
        "supersedesReviewId": "ATL105-QUANTITY-ASSUMED-DECIMAL-REVIEW-001",
        "reviewDate": "2026-10-16",
        "status": "REVIEW_REQUIRED",
        "source": {
            "specification": "ATL105",
            "version": "2026-3",
            "element": "81",
            "example": "0.05 with two assumed decimal places is represented as 205; 25 is invalid",
            "sourceTextSha256": source_hash,
            "scopeNote": "This explicit example establishes the tested encoding pair; it does not establish all Unit of Measure decimal contexts or amount semantics.",
        },
        "inputRunRoot": str(run_root),
        "linkedCaseCount": len(cases),
        "cases": cases,
        "automatedSummary": {
            "segment102LinkedCases": sum(case["linkedSourceScope"] == "102" for case in cases),
            "segment157LinkedCases": sum(case["linkedSourceScope"] == "157" for case in cases),
            "casesWithoutQuantityInLinkedSegmentScope": [case["caseId"] for case in cases if not case["linkedScopeQuantityObserved"]],
            "casesWhoseLinkedScopeIsObserved": [case["caseId"] for case in cases if case["linkedScopeStatus"] != "SOURCE_SCOPE_MISMATCH"],
            "casesWithMissingSidecarSourceBatchDigest": [case["caseId"] for case in cases if any(not copy["sourceBatchSha256Present"] for copy in case["copies"])],
            "rawQuantityValuesWritten": False,
        },
        "guardrails": {
            "sidecarUsedAsDecimalOracle": False,
            "producerResponseOracleAccepted": False,
            "segment157InferredFromSegment102": False,
            "semanticMatchesConfirmed": 0,
            "coverageCredit": 0,
            "testSolutionExecutionCertified": False,
            "officialBaselineModified": False,
        },
    }


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--run-root", type=Path, default=RUN_ROOT)
    parser.add_argument("--atl105-root", type=Path, default=ATL105)
    parser.add_argument("--output", type=Path, default=OUTPUT / "quantity-assumed-decimal-review-v2.json")
    parser.add_argument("--manifest", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test(args.atl105_root)
        return 0
    report = build_report(args.run_root, args.atl105_root)
    args.output.parent.mkdir(parents=True, exist_ok=True)
    args.output.write_text(json.dumps(report, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    manifest_path = args.manifest or args.output.with_name("manifest-v2.json")
    manifest_path.parent.mkdir(parents=True, exist_ok=True)
    manifest = {
        "manifestId": "ATL105-QUANTITY-ASSUMED-DECIMAL-REVIEW-002-MANIFEST",
        "reviewDate": "2026-10-16",
        "status": "REVIEW_REQUIRED",
        "artifacts": [
                {"path": str(args.output), "sha256": sha256(args.output), "role": "automated-review-report"},
                {"path": str(Path(__file__).resolve()), "sha256": sha256(Path(__file__).resolve()), "role": "review-runner"},
                {"path": str(args.atl105_root / "docs" / "specs" / "extracted_text.txt"), "sha256": sha256(args.atl105_root / "docs" / "specs" / "extracted_text.txt"), "role": "source-text"},
            {"path": str(args.output.with_name("README.md")), "sha256": sha256(args.output.with_name("README.md")), "role": "review-summary"},
        ],
        "candidateInputs": [
            {"caseId": case["caseId"], "sourceKind": copy["sourceKind"], "path": path_key, "sha256": copy[hash_key], "role": "ai-candidate-input"}
            for case in report["cases"] for copy in case["copies"]
            for path_key, hash_key in ((copy["payloadPath"], "payloadSha256"), (copy["sidecarPath"], "sidecarSha256"))
            if copy[hash_key]
        ],
        "guardrails": report["guardrails"],
    }
    manifest_path.write_text(json.dumps(manifest, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    print(json.dumps({"report": str(args.output), "manifest": str(manifest_path), "linkedCaseCount": len(report["cases"]),
                      "casesWithoutLinkedScopeObservation": len(report["automatedSummary"]["casesWithoutQuantityInLinkedSegmentScope"]),
                      "coverageCredit": 0}, ensure_ascii=True))
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except (OSError, ValueError, json.JSONDecodeError) as error:
        print(f"ERROR: {type(error).__name__}: {error}", file=sys.stderr)
        raise SystemExit(2)