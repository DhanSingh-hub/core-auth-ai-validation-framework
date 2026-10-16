#!/usr/bin/env python3
"""Group AI scenarios by explicitly declared card-brand network proxy."""

import argparse
import csv
import hashlib
import json
import tempfile
from collections import Counter, defaultdict
from pathlib import Path

import ijson


NETWORK_BY_CARD_BRAND = {
    "VISA": "VISA",
    "MC": "MASTERCARD",
    "AX": "AMERICAN_EXPRESS",
    "DS": "DISCOVER",
}
CSV_FIELDS = [
    "scenarioId",
    "scenarioType",
    "title",
    "producerStatus",
    "networkType",
    "rawCardBrand",
    "classificationBasis",
    "classificationStatus",
    "linkedTestCaseCount",
    "linkedTestCaseIds",
]


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def array_rows(path: Path, key: str):
    with path.open("rb") as stream:
        yield from ijson.items(stream, key + ".item", use_float=True)


def classify_card_brand(value):
    if value is None or value == "":
        return "NETWORK_NOT_DECLARED", "NOT_DECLARED", "NO_EXPLICIT_CARD_BRAND_AXIS"
    if isinstance(value, list):
        values = sorted({str(item).strip().upper() for item in value if str(item).strip()})
        if not values:
            return "NETWORK_NOT_DECLARED", "NOT_DECLARED", "NO_EXPLICIT_CARD_BRAND_AXIS"
        mapped = {NETWORK_BY_CARD_BRAND.get(item) for item in values}
        if None in mapped:
            return "UNMAPPED_CARD_BRAND", "REVIEW_REQUIRED", "UNMAPPED_EXPLICIT_CARD_BRAND_AXIS"
        if len(mapped) > 1:
            return "MULTI_NETWORK", "REVIEW_REQUIRED", "MULTIPLE_EXPLICIT_CARD_BRANDS"
        return next(iter(mapped)), "DECLARED_CARD_BRAND", "EXPLICIT_CARD_BRAND_NETWORK_PROXY"
    raw = str(value).strip().upper()
    if not raw:
        return "NETWORK_NOT_DECLARED", "NOT_DECLARED", "NO_EXPLICIT_CARD_BRAND_AXIS"
    network = NETWORK_BY_CARD_BRAND.get(raw)
    if network is None:
        return "UNMAPPED_CARD_BRAND", "REVIEW_REQUIRED", "UNMAPPED_EXPLICIT_CARD_BRAND_AXIS"
    return network, "DECLARED_CARD_BRAND", "EXPLICIT_CARD_BRAND_NETWORK_PROXY"


def self_test() -> None:
    assert classify_card_brand("VISA") == ("VISA", "DECLARED_CARD_BRAND", "EXPLICIT_CARD_BRAND_NETWORK_PROXY")
    assert classify_card_brand("MC")[0] == "MASTERCARD"
    assert classify_card_brand("AX")[0] == "AMERICAN_EXPRESS"
    assert classify_card_brand("DS")[0] == "DISCOVER"
    assert classify_card_brand(None)[0] == "NETWORK_NOT_DECLARED"
    assert classify_card_brand("AMEX")[1] == "REVIEW_REQUIRED"
    assert classify_card_brand(["VISA", "MC"])[0] == "MULTI_NETWORK"
    print("PASS: explicit card-brand network proxies are normalized; undeclared and unknown brands stay distinct")


def build(approved_path: Path, candidate_path: Path, test_case_path: Path, output_dir: Path) -> dict:
    approved_path, candidate_path, test_case_path, output_dir = map(
        lambda value: Path(value).resolve(), (approved_path, candidate_path, test_case_path, output_dir)
    )
    if output_dir.exists():
        raise ValueError(f"Refusing to overwrite existing output directory: {output_dir}")

    approved = list(array_rows(approved_path, "scenarios"))
    candidates = list(array_rows(candidate_path, "scenarios"))
    test_cases = list(array_rows(test_case_path, "test_cases"))
    approved_by_id = {str(row.get("id") or ""): row for row in approved}
    candidate_by_id = {str(row.get("id") or ""): row for row in candidates}
    if len(approved_by_id) != len(approved) or any(not key for key in approved_by_id):
        raise ValueError("Approved scenario catalog has missing or duplicate IDs")
    if len(candidate_by_id) != len(candidates) or any(not key for key in candidate_by_id):
        raise ValueError("Candidate scenario catalog has missing or duplicate IDs")
    if approved_by_id.keys() != candidate_by_id.keys():
        raise ValueError("Approved and candidate scenario ID populations differ")
    for scenario_id, scenario in approved_by_id.items():
        approved_brand = (scenario.get("flow_axis_values") or {}).get("card_brand")
        candidate_brand = (candidate_by_id[scenario_id].get("flow_axis_values") or {}).get("card_brand")
        if approved_brand != candidate_brand:
            raise ValueError("Approved/candidate card_brand differs for scenario: " + scenario_id)

    test_cases_by_scenario = defaultdict(list)
    test_case_ids = set()
    for test_case in test_cases:
        test_case_id = str(test_case.get("id") or "")
        scenario_id = str(test_case.get("scenario_id") or "")
        if not test_case_id or test_case_id in test_case_ids:
            raise ValueError("Test-case catalog has missing or duplicate IDs")
        if scenario_id not in approved_by_id:
            raise ValueError("Test case references an unknown approved scenario: " + test_case_id)
        test_case_ids.add(test_case_id)
        test_brand = (test_case.get("flow_axis_values") or {}).get("card_brand")
        scenario_brand = (approved_by_id[scenario_id].get("flow_axis_values") or {}).get("card_brand")
        if test_brand is not None and test_brand != scenario_brand:
            raise ValueError("Test-case card_brand differs from linked scenario: " + test_case_id)
        test_cases_by_scenario[scenario_id].append(test_case_id)

    rows = []
    scenario_counts, case_counts = Counter(), Counter()
    rows_by_network = defaultdict(list)
    for scenario_id, scenario in sorted(approved_by_id.items()):
        raw_brand = (scenario.get("flow_axis_values") or {}).get("card_brand")
        network, status, basis = classify_card_brand(raw_brand)
        linked_cases = sorted(test_cases_by_scenario[scenario_id])
        row = {
            "scenarioId": scenario_id,
            "scenarioType": scenario.get("type") or "",
            "title": scenario.get("name") or scenario.get("title") or "",
            "producerStatus": scenario.get("status") or "APPROVED_CATALOG_ENTRY",
            "networkType": network,
            "rawCardBrand": json.dumps(raw_brand, ensure_ascii=True, sort_keys=True) if raw_brand is not None else "",
            "classificationBasis": basis,
            "classificationStatus": status,
            "linkedTestCaseCount": len(linked_cases),
            "linkedTestCaseIds": linked_cases,
        }
        rows.append(row)
        scenario_counts[network] += 1
        case_counts[network] += len(linked_cases)
        rows_by_network[network].append(row)

    inputs = [approved_path, candidate_path, test_case_path, Path(__file__).resolve()]
    input_hashes = {str(path): sha256(path) for path in inputs}
    summary = {
        "artifact": "ATL105-AI-SCENARIO-NETWORK-GROUPING",
        "classificationStatus": "EXPLICIT_CARD_BRAND_PROXY_WITH_UNDECLARED_AND_UNKNOWN_SEPARATE",
        "policy": "Only flow_axis_values.card_brand is used. No network is inferred from titles, cardType prose, transaction codes, or payload contents. VISA/MC/AX/DS are preserved as raw values and normalized to display groups; normalization is a card-brand proxy, not a direct networkType assertion.",
        "approvedScenarioCount": len(approved),
        "candidateScenarioCount": len(candidates),
        "candidateTestCaseCount": len(test_cases),
        "scenarioGroupCounts": dict(sorted(scenario_counts.items())),
        "linkedTestCaseGroupCounts": dict(sorted(case_counts.items())),
        "inputSha256": input_hashes,
        "files": ["scenario-network-groups.csv"],
    }

    for path, digest in input_hashes.items():
        if sha256(Path(path)) != digest:
            raise ValueError("Input changed during scenario grouping: " + path)

    output_dir.mkdir(parents=True)
    with (output_dir / "scenario-network-groups.csv").open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=CSV_FIELDS)
        writer.writeheader()
        for row in rows:
            writer.writerow({**row, "linkedTestCaseIds": ";".join(row["linkedTestCaseIds"])})
    (output_dir / "category-coverage-summary.json").write_text(
        json.dumps(summary, indent=2, ensure_ascii=True) + "\n", encoding="utf-8"
    )
    write_summary_markdown(output_dir / "category-coverage-summary.md", summary, rows_by_network, case_counts)
    (output_dir / "categorization-implementation.py").write_bytes(Path(__file__).read_bytes())
    artifacts = {
        path.name: sha256(path)
        for path in sorted(output_dir.iterdir())
        if path.is_file() and path.name != "manifest.json"
    }
    (output_dir / "manifest.json").write_text(
        json.dumps({"algorithm": "SHA-256", "manifestExcludedFromItsOwnArtifacts": True,
                    "artifacts": artifacts}, indent=2) + "\n", encoding="utf-8"
    )
    return summary


def write_summary_markdown(path: Path, summary: dict, rows_by_network: dict, case_counts: Counter) -> None:
    lines = [
        "# AI Test Scenarios by Card-Brand Network Proxy",
        "",
        "Grouping uses only the explicit `flow_axis_values.card_brand` field. The normalized network label is a display proxy; it is not a direct ATL105 networkType assertion.",
        "",
        f"- Approved scenarios: {summary['approvedScenarioCount']:,}",
        f"- Candidate test cases: {summary['candidateTestCaseCount']:,}",
        "",
        "| Network group | Scenarios | Linked test cases | Status |",
        "|---|---:|---:|---|",
    ]
    for network, rows in sorted(rows_by_network.items()):
        cases = case_counts[network]
        status = "REVIEW_REQUIRED" if network in {"MULTI_NETWORK", "UNMAPPED_CARD_BRAND"} else "EXPLICIT_CARD_BRAND" if network != "NETWORK_NOT_DECLARED" else "NOT_DECLARED"
        lines.append(f"| {network} | {len(rows):,} | {cases:,} | {status} |")
    lines.extend(["", "The CSV preserves each source card-brand value and classification basis. Unknown, multiple, and undeclared brands are not assigned a guessed network."])
    path.write_text("\n".join(lines) + "\n", encoding="utf-8")


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("--approved-scenarios", type=Path)
    parser.add_argument("--candidate-scenarios", type=Path)
    parser.add_argument("--test-cases", type=Path)
    parser.add_argument("--output-directory", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
        return
    if not all((args.approved_scenarios, args.candidate_scenarios, args.test_cases, args.output_directory)):
        parser.error("--approved-scenarios, --candidate-scenarios, --test-cases, and --output-directory are required")
    summary = build(args.approved_scenarios, args.candidate_scenarios, args.test_cases, args.output_directory)
    print(json.dumps(summary, indent=2, ensure_ascii=True))


if __name__ == "__main__":
    main()