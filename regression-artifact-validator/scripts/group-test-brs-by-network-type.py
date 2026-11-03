#!/usr/bin/env python3
"""Group every Test Solution BR by explicitly declared Section 5 network type."""

import argparse
import hashlib
import json
from collections import Counter
from pathlib import Path


NETWORK_TYPES = {
    "ACH_PREAUTHORIZED_DEBIT": "ACH",
    "ATM_PIN_DEBIT": "ATM_DEBIT",
    "ATM_PINLESS_DEBIT": "ATM_DEBIT",
    "CHECK_GUARANTEE": "CHECK",
    "CHECK_VERIFICATION_BUYPASS": "CHECK",
    "CHECK_VERIFICATION_EXTERNAL": "CHECK",
    "CREDIT_NONPROPRIETARY": "CREDIT",
    "CREDIT_PROPRIETARY": "CREDIT",
    "EBT_FOOD": "EBT",
    "EBT_CASH": "EBT",
    "EBT_EWIC": "EBT",
    "PROPRIETARY_CARD": "PROPRIETARY",
    "PETROLEUM_PROPRIETARY": "PETROLEUM_PROPRIETARY",
    "FLEET": "FLEET",
    "STORED_VALUE": "STORED_VALUE",
    "LOYALTY": "LOYALTY",
}
NETWORK_BR_ID = "BR-SEG100-SEC5-NETWORK"
CONTEXT_BR_ID = "BR-SEG100-SEC5-CONTEXT"


def sha256(path: Path) -> str:
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def classify(requirement: dict) -> dict:
    br_id = str(requirement.get("id") or "")
    network_type = requirement.get("networkType")
    if network_type is not None:
        if network_type not in NETWORK_TYPES:
            return {"networkGroup": "UNKNOWN_NETWORK_TYPE_REVIEW_REQUIRED", "networkType": network_type,
                    "parentCategory": requirement.get("parentCategory"),
                    "classification": "UNRECOGNIZED_EXPLICIT_NETWORK_TYPE"}
        return {"networkGroup": network_type, "networkType": network_type,
                "parentCategory": requirement.get("parentCategory") or NETWORK_TYPES[network_type],
                "classification": "EXPLICIT_SECTION5_LEAF"}
    if br_id == NETWORK_BR_ID:
        return {"networkGroup": "NETWORK_TAXONOMY_GENERAL", "networkType": "",
                "parentCategory": "", "classification": "GENERAL_SECTION5_ALLOWLIST"}
    if br_id == CONTEXT_BR_ID or requirement.get("requirementScope") == "DERIVED_CROSS_CONTEXT_RULE":
        return {"networkGroup": "NETWORK_CROSS_CONTEXT_REVIEW_REQUIRED", "networkType": "",
                "parentCategory": "", "classification": "UNRESOLVED_CROSS_CONTEXT_RULE"}
    return {"networkGroup": "NO_NETWORK_TYPE_DECLARED", "networkType": "",
            "parentCategory": "", "classification": "NOT_ASSERTED_BY_THIS_BR"}


def self_test() -> None:
    assert classify({"id": "N1", "networkType": "VISA"})["networkGroup"] == "UNKNOWN_NETWORK_TYPE_REVIEW_REQUIRED"
    assert classify({"id": "N2", "networkType": "CREDIT_NONPROPRIETARY"})["parentCategory"] == "CREDIT"
    assert classify({"id": NETWORK_BR_ID})["networkGroup"] == "NETWORK_TAXONOMY_GENERAL"
    assert classify({"id": CONTEXT_BR_ID})["networkGroup"] == "NETWORK_CROSS_CONTEXT_REVIEW_REQUIRED"
    assert classify({"id": "OTHER", "requirementScope": "DERIVED_CROSS_CONTEXT_RULE"})["networkGroup"] == "NETWORK_CROSS_CONTEXT_REVIEW_REQUIRED"
    assert classify({"id": "SEG100-R-001", "title": "Visa purchase rule"})["networkGroup"] == "NO_NETWORK_TYPE_DECLARED"
    print("PASS: explicit networkType fields are grouped; prose and card-brand mentions are not inferred")


def build(test_package_path: Path, network_matrix_path: Path, output_dir: Path) -> dict:
    test_package_path, network_matrix_path, output_dir = map(
        lambda value: Path(value).resolve(), (test_package_path, network_matrix_path, output_dir)
    )
    if output_dir.exists():
        raise ValueError(f"Refusing to overwrite existing output directory: {output_dir}")
    package = json.loads(test_package_path.read_text(encoding="utf-8-sig"))
    manifest = package.get("manifest") or {}
    if manifest.get("authority") != "INDEPENDENT_TEST_SOLUTION" or manifest.get("aiArtifactsIncluded") is not False:
        raise ValueError("Network grouping requires the AI-excluded independent Test Solution package")
    requirements = package.get("businessRequirements") or []
    ids = [str(row.get("id") or "") for row in requirements]
    if any(not value for value in ids) or len(set(ids)) != len(ids):
        raise ValueError("Test Solution BR IDs are missing or duplicated")
    network_matrix = json.loads(network_matrix_path.read_text(encoding="utf-8-sig"))
    matrix_requirements = network_matrix.get("businessRequirements") or []
    matrix_ids = [str(row.get("id") or "") for row in matrix_requirements]
    if any(not value for value in matrix_ids) or len(set(matrix_ids)) != len(matrix_ids):
        raise ValueError("Section 5 network BR matrix has missing or duplicate IDs")
    package_ids = set(ids)
    supplemental = [row for row in matrix_requirements if str(row["id"]) not in package_ids]
    requirements = list(requirements) + supplemental

    rows = []
    counts = Counter()
    unknowns = []
    for requirement in requirements:
        assignment = classify(requirement)
        row = {
            "networkGroup": assignment["networkGroup"],
            "networkType": assignment["networkType"],
            "parentCategory": assignment["parentCategory"],
            "brId": requirement["id"],
            "title": requirement.get("title") or "",
            "status": requirement.get("status") or "UNSPECIFIED",
            "requirementScope": requirement.get("requirementScope") or "",
            "classification": assignment["classification"],
            "sourceAnchors": requirement.get("sourceAnchors") or requirement.get("sourceAnchor") or [],
        }
        rows.append(row)
        counts[row["networkGroup"]] += 1
        if row["classification"] == "UNRECOGNIZED_EXPLICIT_NETWORK_TYPE":
            unknowns.append(row["brId"])

    expected_network_types = set(NETWORK_TYPES)
    present_network_types = {row["networkType"] for row in rows if row["classification"] == "EXPLICIT_SECTION5_LEAF"}
    summary = {
        "artifact": "ATL105-INDEPENDENT-TEST-BR-NETWORK-TYPE-GROUPING",
        "classificationStatus": "EXPLICIT_SECTION5_NETWORK_TYPE_ONLY",
        "policy": "Only the Test BR networkType field is used. The general Section 5 allowlist and unresolved cross-context BRs have separate groups. The active canonical package is supplemented by Section 5 network BRs absent from it. Other BRs are not assigned a network type from titles, source prose, card type, or AI matches.",
        "testRequirementCount": len(requirements),
        "canonicalPackageBrCount": len(ids),
        "section5NetworkMatrixBrCount": len(matrix_requirements),
        "section5BrsSupplementedOutsideActivePackage": len(supplemental),
        "explicitNetworkTypeBrCount": sum(counts[key] for key in expected_network_types),
        "networkTypesWithoutBr": sorted(expected_network_types - present_network_types),
        "unknownExplicitNetworkTypeBrIds": unknowns,
        "groupCounts": dict(sorted(counts.items())),
        "inputSha256": {str(test_package_path): sha256(test_package_path),
                str(network_matrix_path): sha256(network_matrix_path)},
        "files": ["test-brs-by-network-type.csv"],
    }
    if sha256(test_package_path) != summary["inputSha256"][str(test_package_path)]:
        raise ValueError("Test Solution package changed while grouping BRs")

    output_dir.mkdir(parents=True)
    with (output_dir / "test-brs-by-network-type.csv").open("w", encoding="utf-8-sig", newline="") as stream:
        import csv
        fields = ["networkGroup", "networkType", "parentCategory", "brId", "title", "status",
                  "requirementScope", "classification", "sourceAnchors"]
        writer = csv.DictWriter(stream, fieldnames=fields)
        writer.writeheader()
        for row in sorted(rows, key=lambda item: (item["networkGroup"], item["brId"])):
            writer.writerow({**row, "sourceAnchors": json.dumps(row["sourceAnchors"], ensure_ascii=True, sort_keys=True)})
    (output_dir / "network-type-grouping-summary.json").write_text(
        json.dumps(summary, indent=2, ensure_ascii=True) + "\n", encoding="utf-8"
    )
    write_markdown(output_dir / "network-type-grouping-summary.md", summary)
    (output_dir / "grouping-implementation.py").write_bytes(Path(__file__).read_bytes())
    artifacts = {path.name: sha256(path) for path in sorted(output_dir.iterdir())
                 if path.is_file() and path.name != "manifest.json"}
    (output_dir / "manifest.json").write_text(
        json.dumps({"algorithm": "SHA-256", "manifestExcludedFromItsOwnArtifacts": True,
                    "artifacts": artifacts}, indent=2) + "\n", encoding="utf-8"
    )
    return summary


def write_markdown(path: Path, summary: dict) -> None:
    lines = [
        "# Independent Test BRs by Network Type", "",
        "Groups use only explicit `networkType` fields in the independent Test Solution package. Unassigned BRs are not inferred to be network-specific.", "",
        f"- Test BRs in package: {summary['testRequirementCount']:,}",
        f"- BRs with explicit Section 5 leaf type: {summary['explicitNetworkTypeBrCount']:,}", "",
        "| Network group | Test BRs |", "|---|---:|",
    ]
    for group, count in summary["groupCounts"].items():
        lines.append(f"| {group} | {count:,} |")
    lines.extend(["", "An explicit Section 5 `networkType` is a training classification, not a wire field or a mapping to Visa/Mastercard/card-type codes. The cross-context rule remains review-required."])
    path.write_text("\n".join(lines) + "\n", encoding="utf-8")


def main() -> None:
    parser = argparse.ArgumentParser()
    parser.add_argument("--test-package", type=Path)
    parser.add_argument("--network-type-matrix", type=Path)
    parser.add_argument("--output-directory", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
        return
    if not args.test_package or not args.network_type_matrix or not args.output_directory:
        parser.error("--test-package, --network-type-matrix, and --output-directory are required")
    print(json.dumps(build(args.test_package, args.network_type_matrix, args.output_directory), indent=2))


if __name__ == "__main__":
    main()