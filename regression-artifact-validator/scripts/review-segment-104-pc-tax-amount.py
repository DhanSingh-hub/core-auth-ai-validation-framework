#!/usr/bin/env python3
"""Audit linked AI PC Tax Amount artifacts against ATL105 Element 74 format only."""

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
RUN_ROOT = ATL105 / "test-input" / "ai-solution" / "runs" / "2026-10-16" / "Run4"
SOURCE_DIR = Path("test_data_10_oct/test_data_10_oct/candidates/qe_shaped_test_data")
CASE_IDS = ("TC-019804", "TC-019805", "TC-019806")
OUTPUT = ATL105 / "test-output" / "test-solution-independent-review" / "pc-tax-amount-chain-draft-v2"
ELEMENT_74_PATTERN = re.compile(r"[0-9]{1,7}")


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for chunk in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(chunk)
    return digest.hexdigest()


def normalized(value: str) -> str:
    return re.sub(r"[^a-z0-9]", "", value.lower())


def is_element_74_label(value: str) -> bool:
    label = normalized(value)
    return label in {"74", "element74", "pctaxamount", "purchcardtaxamount"}


def classify(value: Any) -> str:
    if not isinstance(value, str):
        return "INVALID_REPRESENTATION"
    if value == "":
        return "PRESENCE_REVIEW_REQUIRED"
    return "SOURCE_FORMAT_VALID" if ELEMENT_74_PATTERN.fullmatch(value) else "INVALID_FORMAT"


def find_payload_fields(node: Any, path: str = "") -> list[dict[str, Any]]:
    found: list[dict[str, Any]] = []
    if isinstance(node, dict):
        for key, value in node.items():
            child_path = f"{path}/{key}"
            if is_element_74_label(str(key)):
                found.append({"path": child_path, "value": value})
            found.extend(find_payload_fields(value, child_path))
    elif isinstance(node, list):
        for index, value in enumerate(node):
            found.extend(find_payload_fields(value, f"{path}[{index}]"))
    return found


def metadata_element_74_rows(metadata: Any) -> list[dict[str, Any]]:
    fields = metadata.get("fields", []) if isinstance(metadata, dict) else []
    if not isinstance(fields, list):
        return []
    return [
        field for field in fields
        if isinstance(field, dict)
        and is_element_74_label(" ".join(str(field.get(key, "")) for key in ("element", "specElementName", "name")))
    ]


def declared_unavailable_element_74(metadata: Any) -> bool:
    fields = metadata.get("unavailableFields", []) if isinstance(metadata, dict) else []
    if not isinstance(fields, list):
        return False
    for field in fields:
        label = " ".join(str(value) for value in field.values()) if isinstance(field, dict) else str(field)
        if is_element_74_label(label):
            return True
    return False


def read_json(path: Path) -> tuple[Any | None, str | None]:
    try:
        return json.loads(path.read_text(encoding="utf-8-sig")), None
    except (OSError, UnicodeError, json.JSONDecodeError) as error:
        return None, type(error).__name__


def validate_domain(atl105_root: Path) -> tuple[str, str]:
    domain_path = atl105_root / "docs" / "specs" / "kb" / "elements" / "value-domains.json"
    source_path = atl105_root / "docs" / "specs" / "extracted_text.txt"
    domains = json.loads(domain_path.read_text(encoding="utf-8-sig"))["domains"]
    matches = [item for item in domains if item.get("element") == "74"]
    if len(matches) != 1:
        raise ValueError("Element 74 must have exactly one source-backed format domain")
    domain = matches[0]
    source = source_path.read_text(encoding="utf-8-sig")
    start = source.find("Number: 74 Name: PC Tax Amount")
    end = source.find("Number: 75 Name:", start + 1)
    definition = source[start:end] if start >= 0 and end > start else ""
    evidence = domain.get("sourceEvidence", "")
    if domain.get("kind") != "pattern" or domain.get("pattern") != ELEMENT_74_PATTERN.pattern:
        raise ValueError("Element 74 must use the source-limited numeric/seven-digit format predicate")
    normalized_definition = " ".join(definition.split())
    normalized_evidence = " ".join(evidence.split())
    if not normalized_evidence or normalized_evidence not in normalized_definition or "Valid Codes/Values:" not in normalized_definition:
        raise ValueError("Element 74 format predicate lacks local source evidence")
    return sha256(source_path), sha256(domain_path)


def audit_case(case_id: str, run_root: Path) -> dict[str, Any]:
    directory = run_root / SOURCE_DIR
    payload_path = directory / f"{case_id}.json"
    sidecar_path = directory / f"{case_id}.meta.json"
    payload, payload_error = read_json(payload_path)
    metadata, metadata_error = read_json(sidecar_path)
    payload_fields = find_payload_fields(payload) if payload_error is None else []
    metadata_fields = metadata_element_74_rows(metadata) if metadata_error is None else []
    payload_values = [field["value"] for field in payload_fields]
    metadata_values = [field.get("value") for field in metadata_fields]
    if payload_fields and metadata_fields:
        sidecar_matches_payload: bool | None = payload_values == metadata_values
    elif payload_fields or metadata_fields:
        sidecar_matches_payload = False
    else:
        sidecar_matches_payload = None
    return {
        "caseId": case_id,
        "sourceKind": "10_OCT_CANDIDATE_COPY_ONLY",
        "payloadPath": str(payload_path.relative_to(run_root)).replace("\\", "/"),
        "sidecarPath": str(sidecar_path.relative_to(run_root)).replace("\\", "/"),
        "payloadSha256": sha256(payload_path) if payload_path.is_file() else None,
        "sidecarSha256": sha256(sidecar_path) if sidecar_path.is_file() else None,
        "messageFamily": next(iter(payload), None) if isinstance(payload, dict) else None,
        "payloadParseError": payload_error,
        "sidecarParseError": metadata_error,
        "sidecarCaseIdMatches": metadata.get("testCaseId") == case_id if isinstance(metadata, dict) else False,
        "sourceBatchSha256Present": bool(metadata.get("sourceBatchSha256")) if isinstance(metadata, dict) else False,
        "element74PayloadFieldPaths": [field["path"] for field in payload_fields],
        "element74PayloadFieldResults": [classify(field["value"]) for field in payload_fields],
        "element74PayloadFieldCount": len(payload_fields),
        "element74SidecarFieldCount": len(metadata_fields),
        "element74UnavailableDeclared": declared_unavailable_element_74(metadata),
        "sidecarMatchesPayload": sidecar_matches_payload,
        "sidecarOrigins": [field.get("dataSource") for field in metadata_fields],
        "sidecarProvisionalFlags": [field.get("provisional") for field in metadata_fields],
        "rawElement74ValuesWritten": False,
        "approvedPayloadStatus": "NOT_ESTABLISHED_BY_CANDIDATE_COPY",
    }


def self_test() -> None:
    cases = {
        "1": "SOURCE_FORMAT_VALID",
        "1234567": "SOURCE_FORMAT_VALID",
        "0": "SOURCE_FORMAT_VALID",
        "12345678": "INVALID_FORMAT",
        "12A45": "INVALID_FORMAT",
        "1.23": "INVALID_FORMAT",
        "": "PRESENCE_REVIEW_REQUIRED",
        12345: "INVALID_REPRESENTATION",
    }
    for value, expected in cases.items():
        if classify(value) != expected:
            raise AssertionError(f"Unexpected Element 74 status for {type(value).__name__}")
    if len(CASE_IDS) != 3 or len(set(CASE_IDS)) != 3:
        raise AssertionError("PC Tax review must bind exactly three distinct linked cases")
    validate_domain(ATL105)
    print(f"PASS: {len(cases)} Element 74 format/presence controls, source anchor, and {len(CASE_IDS)} unique case IDs; no values emitted.")


def build_report(run_root: Path, atl105_root: Path) -> dict[str, Any]:
    source_hash, domain_hash = validate_domain(atl105_root)
    cases = [audit_case(case_id, run_root) for case_id in CASE_IDS]
    return {
        "artifact": "SEG104_PC_TAX_AMOUNT_AI_ARTIFACT_AUTOMATED_REVIEW",
        "reviewId": "ATL105-SEG104-PC-TAX-AMOUNT-REVIEW-002",
        "reviewDate": "2026-10-16",
        "status": "REVIEW_REQUIRED",
        "source": {
            "specification": "ATL105",
            "version": "2026-3",
            "element": "74",
            "sourceRuleId": "ENT-ELEM-74",
            "formatPredicate": ELEMENT_74_PATTERN.pattern,
            "assumedDecimalPlaces": 2,
            "validAmountRangeOrPresenceRuleAsserted": False,
            "sourceTextSha256": source_hash,
            "valueDomainCatalogSha256": domain_hash,
        },
        "inputRunRoot": str(run_root),
        "linkedCaseCount": len(cases),
        "cases": cases,
        "automatedObservationSummary": {
            "casesWithPayloadParseFailure": [case["caseId"] for case in cases if case["payloadParseError"]],
            "casesWithSidecarParseFailure": [case["caseId"] for case in cases if case["sidecarParseError"]],
            "casesWithExactElement74PayloadField": [case["caseId"] for case in cases if case["element74PayloadFieldCount"]],
            "casesWithExactElement74SidecarField": [case["caseId"] for case in cases if case["element74SidecarFieldCount"]],
            "casesWithElement74DeclaredUnavailable": [case["caseId"] for case in cases if case["element74UnavailableDeclared"]],
            "casesWithPayloadSidecarMismatch": [case["caseId"] for case in cases if case["sidecarMatchesPayload"] is False],
            "rawElement74ValuesWritten": False,
        },
        "guardrails": {
            "producerResponseOracleAccepted": False,
            "presenceInferred": False,
            "segment104ApplicabilityInferred": False,
            "element99ConflatedWithElement74": False,
            "approvedArtifactStatusPromoted": False,
            "semanticMatchesConfirmed": 0,
            "coverageCredit": 0,
            "testSolutionExecutionCertified": False,
        },
    }


def write_manifest(report: dict[str, Any], report_path: Path, script_path: Path,
                   atl105_root: Path) -> dict[str, Any]:
    source_path = atl105_root / "docs" / "specs" / "extracted_text.txt"
    domain_path = atl105_root / "docs" / "specs" / "kb" / "elements" / "value-domains.json"
    files = [
        {"path": str(report_path), "sha256": sha256(report_path), "role": "automated-review-report"},
        {"path": str(script_path), "sha256": sha256(script_path), "role": "review-runner"},
        {"path": str(source_path), "sha256": sha256(source_path), "role": "source-text"},
        {"path": str(domain_path), "sha256": sha256(domain_path), "role": "source-domain-catalog"},
    ]
    readme_path = report_path.with_name("README.md")
    if readme_path.is_file():
        files.append({"path": str(readme_path), "sha256": sha256(readme_path), "role": "review-summary"})
    inputs = []
    for case in report["cases"]:
        for kind in ("payload", "sidecar"):
            path_key, hash_key = f"{kind}Path", f"{kind}Sha256"
            if case[hash_key]:
                inputs.append({"caseId": case["caseId"], "path": case[path_key], "sha256": case[hash_key], "role": f"ai-{kind}-candidate"})
    return {
        "manifestId": "ATL105-SEG104-PC-TAX-AMOUNT-REVIEW-002-MANIFEST",
        "reviewDate": "2026-10-16",
        "status": "REVIEW_REQUIRED",
        "artifacts": files,
        "candidateInputs": inputs,
        "guardrails": report["guardrails"],
    }


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--run-root", type=Path, default=RUN_ROOT)
    parser.add_argument("--atl105-root", type=Path, default=ATL105)
    parser.add_argument("--output", type=Path, default=OUTPUT / "pc-tax-amount-review-v2.json")
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
    manifest = write_manifest(report, args.output, Path(__file__).resolve(), args.atl105_root)
    manifest_path.parent.mkdir(parents=True, exist_ok=True)
    manifest_path.write_text(json.dumps(manifest, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    print(json.dumps({
        "report": str(args.output),
        "manifest": str(manifest_path),
        "linkedCaseCount": len(report["cases"]),
        "element74ObservedCount": len(report["automatedObservationSummary"]["casesWithExactElement74PayloadField"]),
        "coverageCredit": report["guardrails"]["coverageCredit"],
    }, ensure_ascii=True))
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except (OSError, ValueError, json.JSONDecodeError) as error:
        print(f"ERROR: {type(error).__name__}: {error}", file=sys.stderr)
        raise SystemExit(2)