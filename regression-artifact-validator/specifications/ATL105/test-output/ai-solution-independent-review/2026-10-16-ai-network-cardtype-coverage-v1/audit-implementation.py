#!/usr/bin/env python3
"""Audit AI test-case coverage for declared card-brand axes and Prompt Code card types."""

import argparse
import collections
import csv
import hashlib
import json
import re
from pathlib import Path

import ijson


BRAND_LABELS = {
    "VISA": "VISA",
    "MC": "MASTERCARD",
    "AX": "AMERICAN_EXPRESS",
    "DS": "DISCOVER",
}
BRAND_FIELD = "flow_axis_values.card_brand"
DATA_ROOTS = (
    Path("test_data_10_oct/test_data_10_oct/candidates/qe_shaped_test_data"),
    Path("test_generation/test_generation/candidates/qe_shaped_test_data"),
)


def sha256_bytes(data: bytes) -> str:
    return hashlib.sha256(data).hexdigest()


def sha256_file(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def jsonl(path: Path):
    with path.open(encoding="utf-8") as stream:
        for line in stream:
            if line.strip():
                yield json.loads(line)


def array_rows(path: Path, key: str):
    with path.open("rb") as stream:
        yield from ijson.items(stream, key + ".item", use_float=True)


def oracle_card_types(path: Path) -> dict[str, str]:
    source = path.read_text(encoding="utf-8")
    pairs = re.findall(r'codes\.put\("([0-9]{3})",\s*"([^"]+)"\);', source)
    result = dict(pairs)
    if len(result) != 36 or len(pairs) != 36:
        raise ValueError("Segment100CardTypeOracle must define exactly 36 unique financial card codes")
    return result


def prompt_codes(value):
    if isinstance(value, dict):
        for key, child in value.items():
            normalized = re.sub(r"[^a-z0-9]", "", str(key).casefold())
            if normalized == "promptcode" and isinstance(child, dict):
                transaction_type = child.get("TransactionType")
                card_type = child.get("CardType")
                if isinstance(transaction_type, dict):
                    transaction_type = transaction_type.get("value")
                if isinstance(card_type, dict):
                    card_type = card_type.get("value")
                yield str(transaction_type or ""), str(card_type or "")
            yield from prompt_codes(child)
    elif isinstance(value, list):
        for child in value:
            yield from prompt_codes(child)


def self_test():
    payload = {"Financial Request": {"Standard Segment": {
        "PromptCode": {"TransactionType": "0", "CardType": "020"}}}}
    assert list(prompt_codes(payload)) == [("0", "020")]
    assert BRAND_LABELS["MC"] == "MASTERCARD"
    assert BRAND_LABELS["AX"] == "AMERICAN_EXPRESS"
    assert BRAND_LABELS["DS"] == "DISCOVER"
    assert len(oracle_card_types(Path(__file__).parents[1] / "src/main/java/com/coreauth/validator/canonical/Segment100CardTypeOracle.java")) == 36
    print("PASS: explicit brand axes and Prompt Code card values remain separate; oracle denominator is 36")


def build(delivery_summary_path: Path, card_oracle_path: Path, output_dir: Path) -> dict:
    delivery_summary_path, card_oracle_path, output_dir = map(
        lambda value: Path(value).resolve(), (delivery_summary_path, card_oracle_path, output_dir)
    )
    if output_dir.exists():
        raise ValueError(f"Refusing to overwrite existing output directory: {output_dir}")
    delivery_dir = delivery_summary_path.parent
    delivery = json.loads(delivery_summary_path.read_text(encoding="utf-8-sig"))
    run_root = Path(delivery["runRoot"]).resolve()
    inventory_path = delivery_dir / "file-inventory.jsonl"
    if sha256_file(inventory_path) != delivery.get("fileInventorySha256"):
        raise ValueError("Delivery file inventory hash mismatch")
    inventory = {row["path"]: row["sha256"] for row in jsonl(inventory_path)}

    approved_path = run_root / "scenarios/approved/approved_scenarios.json"
    candidate_scenarios_path = run_root / "scenarios/candidates/candidate_scenarios.json"
    candidate_cases_path = run_root / "test_generation/test_generation/candidates/test_case_candidates.json"
    for relative, path in (("scenarios/approved/approved_scenarios.json", approved_path),
                           ("scenarios/candidates/candidate_scenarios.json", candidate_scenarios_path),
                           ("test_generation/test_generation/candidates/test_case_candidates.json", candidate_cases_path)):
        catalog = delivery.get("catalogs", {}).get(relative, {})
        if not catalog or sha256_file(path) != catalog.get("sha256"):
            raise ValueError("Source catalog hash differs from frozen delivery: " + relative)

    scenarios = {str(row["id"]): row for row in array_rows(approved_path, "scenarios")}
    candidate_scenarios = {str(row["id"]): row for row in array_rows(candidate_scenarios_path, "scenarios")}
    if scenarios.keys() != candidate_scenarios.keys():
        raise ValueError("Approved and candidate scenario populations differ")
    for scenario_id, row in scenarios.items():
        if (row.get("flow_axis_values") or {}).get("card_brand") != (
                candidate_scenarios[scenario_id].get("flow_axis_values") or {}).get("card_brand"):
            raise ValueError("Approved/candidate card-brand axis differs: " + scenario_id)

    cases = {str(row["id"]): row for row in array_rows(candidate_cases_path, "test_cases")}
    if len(cases) != delivery["catalogs"]["test_generation/test_generation/candidates/test_case_candidates.json"]["uniqueIds"]:
        raise ValueError("Candidate Test Case IDs are missing or duplicated")
    case_scenario = {case_id: str(row.get("scenario_id") or "") for case_id, row in cases.items()}
    case_brand = {}
    for case_id, scenario_id in case_scenario.items():
        scenario = scenarios.get(scenario_id)
        if scenario is None:
            raise ValueError("Candidate Test Case references unknown scenario: " + case_id)
        case_brand[case_id] = str((scenario.get("flow_axis_values") or {}).get("card_brand") or "").strip().upper()

    card_types = oracle_card_types(card_oracle_path)
    conflict_path = delivery_dir / "content-conflicts.jsonl"
    if sha256_file(conflict_path) != delivery.get("outputHashes", {}).get("content-conflicts.jsonl"):
        raise ValueError("TC content-conflict ledger hash mismatch")
    conflicted_cases = {row["id"] for row in jsonl(conflict_path) if row.get("kind") == "TC"}

    brand_scenarios = collections.defaultdict(set)
    for scenario_id, scenario in scenarios.items():
        raw = str((scenario.get("flow_axis_values") or {}).get("card_brand") or "").strip().upper()
        if raw in BRAND_LABELS:
            brand_scenarios[raw].add(scenario_id)
    brand_cases = collections.defaultdict(set)
    for case_id, scenario_id in case_scenario.items():
        raw = str((scenarios[scenario_id].get("flow_axis_values") or {}).get("card_brand") or "").strip().upper()
        if raw in BRAND_LABELS and case_id not in conflicted_cases:
            brand_cases[raw].add(case_id)

    card_case_ids = collections.defaultdict(set)
    card_payload_counts = collections.Counter()
    observed_combinations = collections.defaultdict(set)
    unknown_card_values = collections.Counter()
    scanned_payloads = paired_payloads = unpaired_payloads = 0
    metadata_mismatches = []
    checked_input_hashes = {str(path.resolve()): sha256_file(path) for path in
                            (delivery_summary_path, inventory_path, conflict_path, approved_path,
                             candidate_scenarios_path, candidate_cases_path, card_oracle_path)}

    for relative_root in DATA_ROOTS:
        input_root = run_root / relative_root
        if not input_root.is_dir():
            raise ValueError("Expected QE-shaped candidate input directory is missing: " + str(relative_root))
        for payload_path in sorted(input_root.glob("*.json")):
            if payload_path.name.endswith(".meta.json"):
                continue
            scanned_payloads += 1
            metadata_path = payload_path.with_name(payload_path.stem + ".meta.json")
            payload_relative = payload_path.relative_to(run_root).as_posix()
            expected_payload_hash = inventory.get(payload_relative)
            payload_bytes = payload_path.read_bytes()
            if not expected_payload_hash or sha256_bytes(payload_bytes) != expected_payload_hash:
                raise ValueError("Payload bytes differ from frozen delivery inventory: " + payload_relative)
            if not metadata_path.is_file():
                unpaired_payloads += 1
                continue
            metadata_relative = metadata_path.relative_to(run_root).as_posix()
            expected_metadata_hash = inventory.get(metadata_relative)
            metadata_bytes = metadata_path.read_bytes()
            if not expected_metadata_hash or sha256_bytes(metadata_bytes) != expected_metadata_hash:
                raise ValueError("Metadata bytes differ from frozen delivery inventory: " + metadata_relative)
            paired_payloads += 1
            metadata = json.loads(metadata_bytes.decode("utf-8-sig"))
            case_id = str(metadata.get("testCaseId") or "")
            scenario_id = str(metadata.get("scenarioId") or "")
            if case_id not in cases or case_scenario[case_id] != scenario_id:
                metadata_mismatches.append({"payload": payload_relative, "testCaseId": case_id,
                                            "scenarioId": scenario_id})
                continue
            if case_id in conflicted_cases:
                continue
            payload = json.loads(payload_bytes.decode("utf-8-sig"))
            for transaction_type, card_type in prompt_codes(payload):
                if not card_type:
                    continue
                if card_type not in card_types:
                    unknown_card_values[card_type] += 1
                    continue
                card_case_ids[card_type].add(case_id)
                card_payload_counts[card_type] += 1
                brand = case_brand[case_id]
                if brand in BRAND_LABELS:
                    observed_combinations[(brand, card_type, transaction_type)].add(case_id)

    if metadata_mismatches:
        raise ValueError(f"{len(metadata_mismatches)} physical payloads have mismatched case/scenario metadata")

    network_rows = []
    for raw, label in BRAND_LABELS.items():
        network_rows.append({"rawCardBrand": raw, "networkLabel": label,
                             "scenarioCount": len(brand_scenarios[raw]),
                             "nonConflictedTestCaseCount": len(brand_cases[raw]),
                             "status": "CASES_PRESENT" if brand_cases[raw] else "MISSING_TEST_CASES"})
    card_rows = []
    for code, description in card_types.items():
        card_rows.append({"cardTypeCode": code, "description": description,
                          "nonConflictedTestCaseCount": len(card_case_ids[code]),
                          "payloadOccurrenceCount": card_payload_counts[code],
                          "status": "CASES_PRESENT" if card_case_ids[code] else "MISSING_TEST_CASES"})
    combo_rows = [{"rawCardBrand": brand, "networkLabel": BRAND_LABELS[brand],
                   "cardTypeCode": card, "transactionType": transaction,
                   "nonConflictedTestCaseCount": len(case_ids)}
                  for (brand, card, transaction), case_ids in sorted(observed_combinations.items())]
    missing_card_types = [code for code in card_types if not card_case_ids[code]]
    summary = {
        "artifact": "ATL105-AI-NETWORK-BRAND-AND-CARD-TYPE-COVERAGE-AUDIT",
        "coveragePolicy": "Network/card-brand and financial Prompt Code card-type coverage are separate dimensions. The audit does not require or infer a network x card-type Cartesian product; only observed combinations are listed.",
        "networkAxisField": BRAND_FIELD,
        "networkAxisCompleteness": "OBSERVED_DECLARED_BRAND_SET_ONLY_NO_INDEPENDENT_BRAND_DENOMINATOR",
        "expectedCardTypeCount": len(card_types),
        "coveredCardTypeCount": sum(bool(card_case_ids[code]) for code in card_types),
        "missingCardTypeCodes": missing_card_types,
        "unknownCardTypeValueOccurrences": dict(sorted(unknown_card_values.items())),
        "conflictedTestCaseIdsExcluded": len(conflicted_cases),
        "candidateTestCaseCount": len(cases),
        "scannedPhysicalPayloads": scanned_payloads,
        "pairedPhysicalPayloads": paired_payloads,
        "unpairedPhysicalPayloads": unpaired_payloads,
        "networkBrandRows": len(network_rows),
        "networkCardTypeCombinationRows": len(combo_rows),
        "networkCardTypeCombinationDenominator": None,
        "status": "INCOMPLETE_CARD_TYPE_COVERAGE" if missing_card_types else "CARD_TYPE_AXIS_COVERED",
        "networkCoverage": network_rows,
        "cardTypeCoverage": card_rows,
        "observedCombinations": combo_rows,
        "inputSha256": checked_input_hashes,
    }
    output_dir.mkdir(parents=True)
    write_csv(output_dir / "network-brand-coverage.csv", network_rows)
    write_csv(output_dir / "financial-card-type-coverage.csv", card_rows)
    write_csv(output_dir / "observed-network-cardtype-combinations.csv", combo_rows)
    (output_dir / "coverage-summary.json").write_text(json.dumps(summary, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
    write_markdown(output_dir / "REPORT.md", summary)
    (output_dir / "audit-implementation.py").write_bytes(Path(__file__).read_bytes())
    artifacts = {path.name: sha256_file(path) for path in sorted(output_dir.iterdir())
                 if path.is_file() and path.name != "manifest.json"}
    (output_dir / "manifest.json").write_text(json.dumps({"algorithm": "SHA-256",
        "manifestExcludedFromItsOwnArtifacts": True, "artifacts": artifacts}, indent=2) + "\n", encoding="utf-8")
    return summary


def write_csv(path: Path, rows):
    rows = list(rows)
    fields = list(rows[0]) if rows else []
    with path.open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=fields)
        writer.writeheader()
        writer.writerows(rows)


def write_markdown(path: Path, summary: dict):
    lines = [
        "# AI Network/Brand and Card-Type Coverage Audit", "",
        "Network brand and card-type coverage are measured separately. No network × card-code cross-product is assumed.", "",
        f"- Candidate Test Cases: {summary['candidateTestCaseCount']:,}",
        f"- Explicit brand-axis rows: {summary['networkBrandRows']:,}",
        f"- Financial card-code denominator: {summary['expectedCardTypeCount']}",
        f"- Card codes with cases: {summary['coveredCardTypeCount']}",
        f"- Card codes missing: {len(summary['missingCardTypeCodes'])}",
        f"- Status: `{summary['status']}`", "",
        "This audit verifies case presence only. It does not certify business semantics, approve network/card compatibility, or validate host execution.",
    ]
    path.write_text("\n".join(lines) + "\n", encoding="utf-8")


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--delivery-summary", type=Path, required=False)
    parser.add_argument("--card-type-oracle-source", type=Path, required=False)
    parser.add_argument("--output-directory", type=Path, required=False)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        assert len(oracle_card_types(Path(__file__).parents[1] / "src/main/java/com/coreauth/validator/canonical/Segment100CardTypeOracle.java")) == 36
        assert BRAND_LABELS == {"VISA": "VISA", "MC": "MASTERCARD", "AX": "AMERICAN_EXPRESS", "DS": "DISCOVER"}
        print("PASS: separate card-brand/card-code axes and 36-code oracle source")
        return
    if not all((args.delivery_summary, args.card_type_oracle_source, args.output_directory)):
        parser.error("--delivery-summary, --card-type-oracle-source, and --output-directory are required")
    summary = build(args.delivery_summary, args.card_type_oracle_source, args.output_directory)
    print(json.dumps({k: summary[k] for k in ("status", "candidateTestCaseCount", "expectedCardTypeCount",
          "coveredCardTypeCount", "missingCardTypeCodes", "networkCoverage", "conflictedTestCaseIdsExcluded")}, indent=2))


if __name__ == "__main__":
    main()