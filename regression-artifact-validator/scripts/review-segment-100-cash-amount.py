#!/usr/bin/env python3
"""Audit linked AI Cash Amount payloads against ATL105's source-local Element 17 domain."""

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
RUN_ROOT = ATL105 / "test-input" / "ai-solution" / "runs" / "2026-10-16" / "Run3"
OUTPUT = ATL105 / "test-output" / "test-solution-independent-review" / "segment-100-cash-amount-format-draft-v2"
TEN_OCT_PRIMARY = Path("test_data_10_oct/test_data_10_oct/candidates/qe_shaped_test_data")
TEN_OCT_DUPLICATE = Path("test_generation/test_generation/candidates/qe_shaped_test_data")
FIVE_OCT = Path("Pipeline_artifacts_5_oct/pipeline_run_artifacts/qe_shaped_test_data")

CASE_GROUPS = {
    "length_exceeded": [
        "TC-002208", "TC-002329", "TC-002450", "TC-002756", "TC-003128", "TC-003761", "TC-003866",
    ],
    "charset_invalid": [
        "TC-002253", "TC-002374", "TC-002556", "TC-002799", "TC-003266", "TC-003799", "TC-003904",
    ],
    "empty_value": [
        "TC-002254", "TC-002375", "TC-002557", "TC-002800", "TC-003267", "TC-003800", "TC-003905",
    ],
    "boundary_at_limit": [
        "TC-004736", "TC-004761", "TC-004786", "TC-004845", "TC-004927", "TC-005061", "TC-005085",
    ],
    "multileg_lifecycle": ["TC-004376", "TC-004408"],
}


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for chunk in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(chunk)
    return digest.hexdigest()


def normalized(value: str) -> str:
    return re.sub(r"[^a-z0-9]", "", value.lower())


def classify_cash_amount(value: Any, minimum: int, maximum: int) -> dict[str, Any]:
    result: dict[str, Any] = {"rawValueOmitted": True}
    if not isinstance(value, str):
        result.update({"status": "INVALID_REPRESENTATION", "valueType": type(value).__name__})
        return result
    result["characterCount"] = len(value)
    if value == "":
        result["status"] = "REVIEW_REQUIRED_PRESENCE_UNRESOLVED"
        return result
    if not value.isascii() or not value.isdigit():
        result["status"] = "INVALID_NONNUMERIC"
        return result
    numeric = int(value)
    if len(value) > 8:
        result["status"] = "INVALID_WIDTH"
    elif numeric < minimum or numeric > maximum:
        result["status"] = "INVALID_RANGE"
    else:
        result["status"] = "SOURCE_DOMAIN_VALID"
    return result


def cash_fields(node: Any, path: str = "") -> list[dict[str, Any]]:
    found: list[dict[str, Any]] = []
    if isinstance(node, dict):
        for key, value in node.items():
            child_path = f"{path}/{key}"
            if normalized(str(key)) in {"cashamount", "cashbackamount"}:
                found.append({"path": child_path, "value": value})
            found.extend(cash_fields(value, child_path))
    elif isinstance(node, list):
        for index, value in enumerate(node):
            found.extend(cash_fields(value, f"{path}[{index}]"))
    return found


def sidecar_cash_fields(metadata: Any) -> list[dict[str, Any]]:
    fields = metadata.get("fields", []) if isinstance(metadata, dict) else []
    if not isinstance(fields, list):
        return []
    found = []
    for field in fields:
        if not isinstance(field, dict):
            continue
        label = " ".join(str(field.get(key, "")) for key in ("element", "specElementName", "name"))
        if "cashamount" in normalized(label):
            found.append(field)
    return found


def read_json(path: Path) -> tuple[Any | None, str | None]:
    try:
        return json.loads(path.read_text(encoding="utf-8-sig")), None
    except (OSError, UnicodeError, json.JSONDecodeError) as error:
        return None, type(error).__name__


def validate_source_domain(atl105_root: Path) -> tuple[int, int, str]:
    domain_path = atl105_root / "docs" / "specs" / "kb" / "elements" / "value-domains.json"
    source_path = atl105_root / "docs" / "specs" / "extracted_text.txt"
    domains = json.loads(domain_path.read_text(encoding="utf-8-sig"))["domains"]
    element = [item for item in domains if item.get("element") == "17"]
    if len(element) != 1 or element[0].get("kind") != "range":
        raise ValueError("Element 17 must have exactly one source-backed range domain")
    minimum, maximum = int(element[0]["min"]), int(element[0]["max"])
    evidence = element[0].get("sourceEvidence", "")
    source = source_path.read_text(encoding="utf-8-sig")
    start = source.find("Number: 17 Name: Cash Amount")
    end = source.find("Number: 18 Name: Clerk ID", start + 1)
    definition = source[start:end] if start >= 0 and end > start else ""
    if ((minimum, maximum) != (1, 99999999) or not evidence or evidence not in definition
            or not re.search(r"Valid Codes/Values:\s*1\s*[-\u2013\u2014]\s*99999999", definition)
            or not re.search(r"Maximum Length:\s*8 bytes", definition)):
        raise ValueError("Element 17 domain does not match its source-anchored ATL105 catalog entry")
    return minimum, maximum, sha256(source_path)


def payload_roots(run_root: Path, case_id: str) -> list[tuple[str, Path]]:
    if case_id in CASE_GROUPS["multileg_lifecycle"]:
        return [("5_oct_candidate", run_root / FIVE_OCT)]
    return [
        ("10_oct_test_data_candidate", run_root / TEN_OCT_PRIMARY),
        ("10_oct_test_generation_candidate", run_root / TEN_OCT_DUPLICATE),
    ]


def audit_case(case_id: str, group: str, run_root: Path, minimum: int, maximum: int) -> dict[str, Any]:
    copies = []
    for source_kind, directory in payload_roots(run_root, case_id):
        payload_path = directory / f"{case_id}.json"
        sidecar_path = directory / f"{case_id}.meta.json"
        payload, payload_error = read_json(payload_path)
        metadata, sidecar_error = read_json(sidecar_path)
        payload_observations = cash_fields(payload) if payload_error is None else []
        sidecar_observations = sidecar_cash_fields(metadata) if sidecar_error is None else []
        results = [classify_cash_amount(item["value"], minimum, maximum) for item in payload_observations]
        payload_values = [item["value"] for item in payload_observations]
        sidecar_values = [item.get("value") for item in sidecar_observations]
        sidecar_matches_payload = None
        if payload_observations and sidecar_observations:
            sidecar_matches_payload = payload_values == sidecar_values
        elif payload_observations or sidecar_observations:
            sidecar_matches_payload = False
        copies.append({
            "sourceKind": source_kind,
            "messageFamily": next(iter(payload), None) if isinstance(payload, dict) else None,
            "payloadPath": str(payload_path.relative_to(run_root)).replace("\\", "/"),
            "sidecarPath": str(sidecar_path.relative_to(run_root)).replace("\\", "/"),
            "payloadSha256": sha256(payload_path) if payload_path.is_file() else None,
            "sidecarSha256": sha256(sidecar_path) if sidecar_path.is_file() else None,
            "payloadParseError": payload_error,
            "sidecarParseError": sidecar_error,
            "sidecarCaseIdMatches": metadata.get("testCaseId") == case_id if isinstance(metadata, dict) else False,
            "sourceBatchSha256Present": bool(metadata.get("sourceBatchSha256")) if isinstance(metadata, dict) else False,
            "cashFieldPaths": [item["path"] for item in payload_observations],
            "cashFieldResults": results,
            "cashFieldCount": len(payload_observations),
            "sidecarCashFieldCount": len(sidecar_observations),
            "sidecarMatchesPayload": sidecar_matches_payload,
            "sidecarOrigins": [item.get("dataSource") for item in sidecar_observations],
            "sidecarProvisionalFlags": [item.get("provisional") for item in sidecar_observations],
        })
    hashes = [(copy["payloadSha256"], copy["sidecarSha256"]) for copy in copies]
    copies_match = len(hashes) == 1 or len(hashes) == 2 and hashes[0] == hashes[1]
    lifecycle_unbound = group == "multileg_lifecycle"
    return {
        "caseId": case_id,
        "reviewGroup": group,
        "approvedArtifactStatus": "NOT_ESTABLISHED_BY_CANDIDATE_COPIES",
        "candidateCopiesByteIdentical": copies_match,
        "lifecyclePerStepBinding": "REVIEW_REQUIRED" if lifecycle_unbound else "NOT_APPLICABLE",
        "copies": copies,
    }


def build_report(run_root: Path, atl105_root: Path) -> dict[str, Any]:
    minimum, maximum, source_hash = validate_source_domain(atl105_root)
    cases = [
        audit_case(case_id, group, run_root, minimum, maximum)
        for group, case_ids in CASE_GROUPS.items()
        for case_id in case_ids
    ]
    logical_statuses: dict[str, int] = {}
    physical_statuses: dict[str, int] = {}
    missing_payloads = []
    cases_without_cash = []
    sidecar_mismatches = []
    case_id_mismatches = []
    duplicate_copy_mismatches = []
    for case in cases:
        if all(copy["payloadParseError"] is not None for copy in case["copies"]):
            missing_payloads.append(case["caseId"])
        primary = case["copies"][0]
        if primary["cashFieldCount"] == 0:
            cases_without_cash.append(case["caseId"])
        if any(copy["sidecarMatchesPayload"] is False for copy in case["copies"]):
            sidecar_mismatches.append(case["caseId"])
        if any(not copy["sidecarCaseIdMatches"] for copy in case["copies"]):
            case_id_mismatches.append(case["caseId"])
        if not case["candidateCopiesByteIdentical"]:
            duplicate_copy_mismatches.append(case["caseId"])
        for result in primary["cashFieldResults"]:
            status = result["status"]
            logical_statuses[status] = logical_statuses.get(status, 0) + 1
        for copy in case["copies"]:
            for result in copy["cashFieldResults"]:
                status = result["status"]
                physical_statuses[status] = physical_statuses.get(status, 0) + 1
    return {
        "artifact": "SEG100_CASH_AMOUNT_AI_ARTIFACT_AUTOMATED_REVIEW",
        "reviewId": "ATL105-SEG100-CASH-AMOUNT-FORMAT-REVIEW-002",
        "reviewDate": "2026-10-16",
        "status": "REVIEW_REQUIRED",
        "source": {
            "sourceSpecification": "ATL105",
            "sourceVersion": "2026-3",
            "sourceRuleId": "ENT-ELEM-17",
            "element": "17",
            "minimum": minimum,
            "maximum": maximum,
            "sourceTextSha256": source_hash,
        },
        "inputRunRoot": str(run_root),
        "linkedCaseCount": len(cases),
        "cases": cases,
        "automatedObservationSummary": {
            "logicalCaseCashFieldResultsByStatus": logical_statuses,
            "physicalCopyCashFieldResultsByStatus": physical_statuses,
            "casesWithoutReadablePayload": missing_payloads,
            "casesWithoutExactCashAmountField": cases_without_cash,
            "casesWithSidecarPayloadMismatch": sidecar_mismatches,
            "casesWithSidecarIdentityMismatch": case_id_mismatches,
            "casesWithDifferentCandidateCopies": duplicate_copy_mismatches,
            "lifecycleCasesRequiringPerStepBinding": [case["caseId"] for case in cases if case["reviewGroup"] == "multileg_lifecycle"],
            "approvedPayloadFilesAbsentFromFrozenInventory": [
                "TC-003761", "TC-003799", "TC-003800", "TC-003866", "TC-003904", "TC-003905", "TC-005061", "TC-005085",
            ],
            "rawCashAmountValuesWritten": False,
        },
        "guardrails": {
            "producerSidecarIsNotValueOracle": True,
            "producerResponseIsNotExpectedOutcomeOracle": True,
            "fieldPresenceIsNotInferred": True,
            "lifecycleLegAssociationIsNotInferred": True,
            "approvedArtifactStatusPromoted": False,
            "semanticMatchesConfirmed": 0,
            "coverageCredit": 0,
            "testSolutionExecutionCertified": False,
        },
    }


def self_test() -> None:
    checks = {
        "1": "SOURCE_DOMAIN_VALID",
        "99999999": "SOURCE_DOMAIN_VALID",
        "00000000": "INVALID_RANGE",
        "000000000": "INVALID_WIDTH",
        "0000000#": "INVALID_NONNUMERIC",
        "": "REVIEW_REQUIRED_PRESENCE_UNRESOLVED",
        1: "INVALID_REPRESENTATION",
    }
    for value, expected in checks.items():
        actual = classify_cash_amount(value, 1, 99999999)["status"]
        if actual != expected:
            raise AssertionError(f"Cash Amount classification mismatch for {type(value).__name__}: {actual}")
    all_case_ids = [case_id for case_ids in CASE_GROUPS.values() for case_id in case_ids]
    if len(all_case_ids) != 30 or len(set(all_case_ids)) != 30:
        raise AssertionError("Cash Amount cohort must contain 30 unique linked cases")
    minimum, maximum, _ = validate_source_domain(ATL105)
    if (minimum, maximum) != (1, 99999999):
        raise AssertionError("Source-backed Element 17 domain changed unexpectedly")
    print(f"PASS: {len(checks)} Element 17 classification controls, 30 unique case IDs; no field values emitted.")


def build_manifest(report: dict[str, Any], report_path: Path, script_path: Path,
                   atl105_root: Path) -> dict[str, Any]:
    inputs = []
    for case in report["cases"]:
        for copy in case["copies"]:
            for kind in ("payload", "sidecar"):
                path_key = f"{kind}Path"
                hash_key = f"{kind}Sha256"
                if copy[hash_key] is not None:
                    inputs.append({
                        "caseId": case["caseId"],
                        "sourceKind": copy["sourceKind"],
                        "path": copy[path_key],
                        "sha256": copy[hash_key],
                        "role": f"ai-{kind}-candidate",
                    })
    source_text = atl105_root / "docs" / "specs" / "extracted_text.txt"
    value_domains = atl105_root / "docs" / "specs" / "kb" / "elements" / "value-domains.json"
    artifacts = [
        {"path": str(report_path), "sha256": sha256(report_path), "role": "automated-review-report"},
        {"path": str(script_path), "sha256": sha256(script_path), "role": "audit-runner"},
        {"path": str(source_text), "sha256": sha256(source_text), "role": "source-text"},
        {"path": str(value_domains), "sha256": sha256(value_domains), "role": "source-domain-catalog"},
    ]
    return {
        "manifestId": "ATL105-SEG100-CASH-AMOUNT-FORMAT-REVIEW-002-MANIFEST",
        "reviewDate": "2026-10-16",
        "status": "REVIEW_REQUIRED",
        "linkedCaseCount": len(report["cases"]),
        "artifacts": artifacts,
        "candidateInputs": inputs,
        "guardrails": report["guardrails"],
    }


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--run-root", type=Path, default=RUN_ROOT)
    parser.add_argument("--atl105-root", type=Path, default=ATL105)
    parser.add_argument("--output", type=Path, default=OUTPUT / "segment-100-cash-amount-review-v2.json")
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
    manifest = build_manifest(report, args.output, Path(__file__).resolve(), args.atl105_root)
    manifest_path.parent.mkdir(parents=True, exist_ok=True)
    manifest_path.write_text(json.dumps(manifest, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    summary = report["automatedObservationSummary"]
    print(json.dumps({
        "report": str(args.output),
        "manifest": str(manifest_path),
        "linkedCaseCount": report["linkedCaseCount"],
        "casesWithoutReadablePayload": len(summary["casesWithoutReadablePayload"]),
        "logicalCaseCashFieldResultsByStatus": summary["logicalCaseCashFieldResultsByStatus"],
        "casesWithoutExactCashAmountField": summary["casesWithoutExactCashAmountField"],
        "coverageCredit": report["guardrails"]["coverageCredit"],
    }, ensure_ascii=True))
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except (OSError, ValueError, json.JSONDecodeError) as error:
        print(f"ERROR: {type(error).__name__}: {error}", file=sys.stderr)
        raise SystemExit(2)