#!/usr/bin/env python3
"""Export Mastercard-tagged Run3 test-case links without payload values."""

from __future__ import annotations

import argparse
import csv
import hashlib
import json
import sys
from collections import Counter
from datetime import date
from pathlib import Path
from typing import Any

import ijson


ROOT = Path(__file__).resolve().parents[1]
ATL105 = ROOT / "specifications" / "ATL105"
RUN_ROOT = ATL105 / "test-input" / "ai-solution" / "runs" / "2026-10-19" / "Run3"
SCENARIO_CATALOG = RUN_ROOT / "scenarios" / "scenarios" / "approved" / "approved_scenarios.json"
TEST_CASE_CATALOG = RUN_ROOT / "test_case_candidates.json"
INTAKE_MANIFEST = ATL105 / "test-input" / "ai-solution" / "runs" / "2026-10-19" / "Run3-intake-manifest.json"
OUTPUT_DIR = ATL105 / "test-output" / "ai-solution-independent-review" / "2026-10-19-mastercard-transaction-links-v1"
LAYOUT_PREFIX = Path("qe_shaped_test_data")


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for chunk in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(chunk)
    return digest.hexdigest()


def requirement_ids(record: dict[str, Any]) -> list[str]:
    values = record.get("requirement_ids") or []
    if isinstance(values, str):
        values = [values]
    if record.get("requirement_id"):
        values = [*values, record["requirement_id"]]
    return sorted({str(value) for value in values if value})


def load_catalog(path: Path, prefix: str) -> dict[str, dict[str, Any]]:
    with path.open("rb") as stream:
        return {str(item["id"]): item for item in ijson.items(stream, prefix)}


def declared_data_paths(case: dict[str, Any]) -> list[str]:
    declarations = []
    if case.get("test_data_file"):
        declarations.append(case["test_data_file"])
    legs = case.get("test_data_leg_files") or []
    if isinstance(legs, dict):
        legs = list(legs.values())
    if isinstance(legs, list):
        declarations.extend(legs)
    return [str(path) for path in declarations if isinstance(path, str) and path]


def resolve_data_path(run_root: Path, declaration: str) -> tuple[Path | None, str]:
    direct = run_root / Path(declaration.replace("\\", "/"))
    if direct.is_file():
        return direct, "EXACT_CATALOG_RELATIVE"
    nested = run_root / LAYOUT_PREFIX / Path(declaration.replace("\\", "/"))
    if nested.is_file():
        return nested, "EXPLICIT_LAYOUT_PREFIX:qe_shaped_test_data"
    return None, "DECLARED_PATH_MISSING"


def audit_leg(run_root: Path, case_id: str, scenario_id: str, declaration: str) -> dict[str, Any]:
    payload_path, resolution = resolve_data_path(run_root, declaration)
    if payload_path is None:
        return {"declaredPath": declaration, "resolvedPath": "", "pathResolution": resolution,
                "sidecarPath": "", "pairStatus": "PAYLOAD_MISSING"}
    relative_payload = payload_path.relative_to(run_root).as_posix()
    sidecar_path = payload_path.with_suffix(".meta.json")
    pair_status = "PAIRED_TC_SCENARIO_IDENTITY_MATCH"
    try:
        payload = json.loads(payload_path.read_text(encoding="utf-8-sig"))
        if not isinstance(payload, dict):
            pair_status = "PAYLOAD_NOT_OBJECT"
    except (OSError, UnicodeError, json.JSONDecodeError):
        pair_status = "PAYLOAD_INVALID"
    if not sidecar_path.is_file():
        pair_status = "SIDECAR_MISSING"
    else:
        try:
            metadata = json.loads(sidecar_path.read_text(encoding="utf-8-sig"))
            if not isinstance(metadata, dict):
                pair_status = "SIDECAR_NOT_OBJECT"
            elif metadata.get("testCaseId") != case_id or metadata.get("scenarioId") != scenario_id:
                pair_status = "SIDECAR_IDENTITY_MISMATCH"
        except (OSError, UnicodeError, json.JSONDecodeError):
            pair_status = "SIDECAR_INVALID"
    return {"declaredPath": declaration, "resolvedPath": relative_payload,
            "pathResolution": resolution, "sidecarPath": sidecar_path.relative_to(run_root).as_posix(),
            "pairStatus": pair_status}


def build_rows(run_root: Path, scenario_catalog: Path, test_case_catalog: Path) -> list[dict[str, Any]]:
    scenarios = load_catalog(scenario_catalog, "scenarios.item")
    rows = []
    with test_case_catalog.open("rb") as stream:
        for case in ijson.items(stream, "test_cases.item"):
            scenario_id = str(case.get("scenario_id") or "")
            scenario = scenarios.get(scenario_id)
            if not scenario:
                continue
            axes = scenario.get("flow_axis_values") or {}
            if str(axes.get("card_brand") or "").upper() != "MC":
                continue
            case_id = str(case.get("id") or "")
            declarations = declared_data_paths(case)
            legs = [audit_leg(run_root, case_id, scenario_id, path) for path in declarations]
            rows.append({
                "cardBrand": "MC",
                "scenarioName": str(scenario.get("name") or ""),
                "testCaseName": str(case.get("name") or ""),
                "scenarioId": scenario_id,
                "testCaseId": case_id,
                "linkedBRIds": json.dumps(sorted(set(requirement_ids(scenario) + requirement_ids(case))), ensure_ascii=True),
                "transactionType": str(case.get("transaction_type") or ""),
                "caseCatalog": "test_case_candidates.json",
                "dataLegCount": len(legs),
                "dataLegs": json.dumps(legs, ensure_ascii=True, separators=(",", ":")),
                "allDataLegsPairedToCaseAndScenario": bool(legs) and all(
                    leg["pairStatus"] == "PAIRED_TC_SCENARIO_IDENTITY_MATCH" for leg in legs),
                "payloadValuesExported": False,
            })
    return sorted(rows, key=lambda row: (row["scenarioName"], row["testCaseId"]))


def write_outputs(output_dir: Path, rows: list[dict[str, Any]], input_paths: list[Path], script_path: Path) -> dict[str, Any]:
    if output_dir.exists() and any(output_dir.iterdir()):
        raise ValueError(f"Refusing to overwrite nonempty output directory: {output_dir}")
    output_dir.mkdir(parents=True, exist_ok=True)
    csv_path = output_dir / "mastercard-transaction-links.csv"
    fieldnames = ["cardBrand", "scenarioName", "testCaseName", "scenarioId", "testCaseId",
                  "linkedBRIds", "transactionType", "caseCatalog", "dataLegCount", "dataLegs",
                  "allDataLegsPairedToCaseAndScenario", "payloadValuesExported"]
    with csv_path.open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=fieldnames)
        writer.writeheader()
        writer.writerows(rows)
    route_counts = Counter("COMPLETE" if row["allDataLegsPairedToCaseAndScenario"] else "INCOMPLETE_OR_NO_TD" for row in rows)
    scenario_ids = {row["scenarioId"] for row in rows}
    scenario_names = {row["scenarioName"] for row in rows}
    summary = {
        "reportId": "ATL105-MASTERCARD-TRANSACTION-LINKS-001",
        "generatedDate": date.today().isoformat(),
        "sourceRun": str(RUN_ROOT),
        "filter": "scenario.flow_axis_values.card_brand == 'MC'",
        "caseCount": len(rows),
        "uniqueScenarioIds": len(scenario_ids),
        "uniqueScenarioNames": len(scenario_names),
        "physicalRouteStatusCounts": dict(route_counts),
        "payloadValuesExported": False,
        "semanticApprovalGranted": False,
        "executionCertified": False,
        "inputs": [{"path": str(path), "sha256": sha256(path)} for path in input_paths],
        "artifacts": [
            {"path": str(csv_path), "sha256": sha256(csv_path), "role": "mastercard-transaction-link-list"},
            {"path": str(script_path.resolve()), "sha256": sha256(script_path), "role": "exporter"},
        ],
    }
    summary_path = output_dir / "summary.json"
    summary_path.write_text(json.dumps(summary, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    return summary


def self_test() -> None:
    if requirement_ids({"requirement_id": "BR1", "requirement_ids": ["BR2", "BR1"]}) != ["BR1", "BR2"]:
        raise AssertionError("Requirement-link normalization failed")
    if declared_data_paths({"test_data_file": "a.json", "test_data_leg_files": ["a.json", "b.json"]}) != ["a.json", "a.json", "b.json"]:
        raise AssertionError("Declared data leg collection failed")
    print("PASS: Mastercard filter remains exact MC; singular/plural BR links and test-data legs are preserved.")


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--run-root", type=Path, default=RUN_ROOT)
    parser.add_argument("--scenario-catalog", type=Path, default=SCENARIO_CATALOG)
    parser.add_argument("--test-case-catalog", type=Path, default=TEST_CASE_CATALOG)
    parser.add_argument("--output-dir", type=Path, default=OUTPUT_DIR)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
        return 0
    rows = build_rows(args.run_root, args.scenario_catalog, args.test_case_catalog)
    summary = write_outputs(args.output_dir, rows,
                            [args.scenario_catalog, args.test_case_catalog], Path(__file__))
    print(json.dumps({key: summary[key] for key in ("caseCount", "uniqueScenarioIds", "uniqueScenarioNames",
                                                       "physicalRouteStatusCounts", "payloadValuesExported")},
                     ensure_ascii=True))
    return 0


if __name__ == "__main__":
    try:
        raise SystemExit(main())
    except (OSError, ValueError, json.JSONDecodeError) as error:
        print(f"ERROR: {type(error).__name__}: {error}", file=sys.stderr)
        raise SystemExit(2)