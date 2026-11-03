"""Repair deterministic export defects in a NEW copy; never approve or invent runtime data."""

import argparse
import csv
import json
import shutil
import sys
from collections import Counter
from pathlib import Path

import delivery_gates as gates


def write_json(path: Path, value) -> None:
    path.write_text(json.dumps(value, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")


def repair_emv(segment: dict) -> bool:
    # This producer's single Additional Information section layout: ATL105 12.20.
    required = {
        "SegmentType", "SegmentLength", "CAPublicKeyFileChecksum", "EMVCardSequenceNumber",
        "EMVChipDataLength", "EMVChipData", "EMVAdditionalInformationIndicator",
        "EMVAdditionalInformationLength", "EMVAdditionalInformation",
    }
    if set(segment) != required:
        raise ValueError("Unsupported Segment 130 layout; no speculative length repair")
    tags = gates.emv_tags(segment["EMVChipData"])
    card_sequence = tags.get("5F34")
    if card_sequence is not None and (len(card_sequence) != 2 or not card_sequence.isdigit()):
        raise ValueError("Tag 5F34 must be one packed numeric byte")
    expected = str(int(card_sequence)).zfill(3) if card_sequence is not None else "   "
    old = segment["EMVCardSequenceNumber"]
    if old == expected:
        return False
    if card_sequence is not None and (
        not isinstance(old, str) or not old.isascii() or not old.isdigit()
        or int(old) != int(expected)
    ):
        raise ValueError("Conflicting card sequence: cannot infer correct card data")
    if card_sequence is None and old.strip():
        raise ValueError("Card sequence populated but tag absent; requires card evidence")
    chip_length = len(bytes.fromhex(segment["EMVChipData"]))
    if int(segment["EMVChipDataLength"]) != chip_length:
        raise ValueError("Chip length mismatch; refuse unrelated mutation")
    additional = segment["EMVAdditionalInformation"]
    if int(segment["EMVAdditionalInformationLength"]) != len(additional.encode("ascii")):
        raise ValueError("Additional Information length mismatch")
    # Seven FS bytes; the binary chip data occupies half its JSON hex-string width.
    old_length = sum(len(v.encode("ascii")) for k, v in segment.items()
                     if k != "EMVChipData") + chip_length + 7
    if int(segment["SegmentLength"]) != old_length:
        raise ValueError("Existing Segment 130 length does not match supported layout")
    segment["EMVCardSequenceNumber"] = expected
    segment["SegmentLength"] = str(old_length + len(expected) - len(old)).zfill(4)
    return True


def rebuild_trace(root: Path, cases: list[dict], run: dict) -> None:
    br = gates.load(root / "requirements_scenarios" / "business_requirements.json")
    scenarios = gates.load(root / "requirements_scenarios" / "business_scenarios.json")["scenarios"]
    by_id = {r["id"]: r for r in run["index"]}
    old = gates.load(root / "reports" / "traceability_matrix_full.json")
    flat, requirements = [], []
    for requirement in br["business_requirements"]:
        requirement_scenarios = []
        for scenario in scenarios:
            if scenario["business_requirement_id"] != requirement["id"]:
                continue
            children = []
            for tc in cases:
                if tc["scenario_id"] != scenario["id"]:
                    continue
                files = ["test_data/" + name for name in by_id[tc["id"]]["test_data_files"]]
                pointer = files[0] if files else None
                children.append({
                    "test_case_id": tc["id"], "qualified_id": tc["qualified_id"], "name": tc["name"],
                    "status": tc["status"], "review_status": "pending_review",
                    "test_data": {"test_data_file": pointer, "test_data_files": files,
                                  "file_exists": bool(pointer) and gates.resolve_reference(root, pointer).is_file()},
                })
                flat.append({
                    "requirement_id": requirement["id"], "scenario_id": scenario["id"],
                    "test_case_id": tc["id"], "qualified_id": tc["qualified_id"],
                    "test_case_name": tc["name"], "test_data_file": pointer,
                    "test_data_files": files, "trace_status": "FILES_LINKED" if files else "NO_PHYSICAL_REQUEST",
                    "semantic_status": "NOT_CERTIFIED",
                })
            requirement_scenarios.append({
                "scenario_id": scenario["id"], "name": scenario["name"], "test_cases": children,
            })
        requirements.append({
            "requirement_id": requirement["id"], "statement": requirement["name"],
            "review_status": "pending_review", "scenarios": requirement_scenarios,
        })
    covered = {tc["scenario_id"] for tc in cases}
    old.update({
        "requirements": requirements, "flat_rows": flat,
        "coverage_basis": {
            "scenario_link": "Scenarios with at least one TC / delivered scenarios",
            "physical_file_link": "TCs with final TD files / TCs requiring physical request legs",
            "semantic_coverage_percent": None, "execution_certified": False,
        },
        "summary": {
            "total_requirements": len(requirements), "total_scenarios": len(scenarios),
            "scenarios_with_test_cases": len(covered), "total_test_cases": len(cases),
            "test_cases_with_data_file": sum(bool(r["test_data_file"]) for r in flat),
            "physical_test_data_files": run["files"],
            "scenario_link_coverage_percent": round(100 * len(covered) / len(scenarios), 2) if scenarios else None,
            "full_chain_coverage_percent": None, "semantic_coverage_percent": None,
            "execution_readiness": "BLOCKED",
        },
        "caveats": [
            "Regenerated from final export references; source input is retained in the repair manifest.",
            "Scope BR and scenario linkage is not semantic approval or specification-rule coverage.",
            "Device and blocked cases may have no physical TD; runtime and source decisions remain open.",
        ],
        "orphans": {"scenarios_without_test_cases": sorted({s["id"] for s in scenarios} - covered)},
    })
    write_json(root / "reports" / "traceability_matrix_full.json", old)
    lines = [
        "# Traceability: final rendered export", "",
        f"Build: `{br['meta']['build_id']}`", "",
        "Semantic status: NOT_CERTIFIED. Execution readiness: BLOCKED.", "",
        f"Scenarios with TCs: {len(covered)}/{len(scenarios)}. "
        f"Physical TD files: {run['files']}.", "",
        "| BR | Scenario | TC | Final TD files |", "|---|---|---|---|",
    ]
    for row in flat:
        lines.append("| " + " | ".join([
            row["requirement_id"], row["scenario_id"], row["test_case_id"],
            ", ".join(row["test_data_files"]) or "No physical request",
        ]) + " |")
    content = "\n".join(lines) + "\n"
    (root / "TRACEABILITY.md").write_text(content, encoding="utf-8")
    (root / "reports" / "traceability_matrix.md").write_text(content, encoding="utf-8")


def repair_package(root: Path) -> dict:
    cases = gates.read_cases(root)
    run = gates.load(root / "run_set.json")
    build = gates.load(root / "requirements_scenarios" / "business_requirements.json")["meta"]["build_id"]
    rmap = {r["id"]: r for r in run["index"]}
    changed = []
    repaired_files = set()
    for tc in cases:
        tc["qualified_id"] = build + ":" + tc["id"]
        row = rmap[tc["id"]]
        row["qualified_id"] = tc["qualified_id"]
        steps = tc.get("steps") or ([tc] if tc.get("tx_legs") else [])
        files = row["test_data_files"]
        if len(steps) != len(files):
            raise ValueError("Cannot repair ambiguous run-set leg mapping")
        for step, filename in zip(steps, files):
            path = gates.resolve_reference(root, "test_data/" + filename)
            wire = gates.load(path)
            body = next(iter(wire.values()))
            emv = body.get("EMV Request Data Segment")
            if emv:
                if repair_emv(emv):
                    write_json(path, wire)
                    repaired_files.add(filename)
                inline = step["request"]["EMV Request Data Segment"]
                inline["EMV Card Sequence Number"]["value"] = emv["EMVCardSequenceNumber"]
                inline["Segment Length"]["value"] = emv["SegmentLength"]
                if step is not tc and step is steps[0]:
                    tc["request"]["EMV Request Data Segment"]["EMV Card Sequence Number"]["value"] = emv["EMVCardSequenceNumber"]
                    tc["request"]["EMV Request Data Segment"]["Segment Length"]["value"] = emv["SegmentLength"]
            # Reconcile the observed renderer's return-sequence transformation, not lifecycle policy.
            if step.get("role") == "return":
                standard = body["Standard Message Data Segment"]
                step["request"]["Standard Message Data Segment"]["Sequence Number"]["value"] = standard["SequenceNumber"]
        tc["test_data_file"] = "test_data/" + files[0] if files else None
        if len(files) > 1:
            tc["test_data_leg_files"] = ["test_data/" + f for f in files]
        # A previous hash recipe is not available; never relabel its digest as verified.
        for key in ["review_hash", "fingerprint", "instance_key"]:
            if key in tc:
                tc.setdefault("producer_original_hashes", {}).setdefault(key, tc[key])
                tc[key] = None
        tc["repair_review_status"] = "pending_review"
        tc.setdefault("producer_original_verification", tc.get("verification"))
        tc["verification"] = {
            "status": "NOT_ASSESSED",
            "reasons": ["Export repaired; producer validation does not certify modified artifacts"],
        }
        changed.append(tc)
    (root / "test_cases" / "test_cases.jsonl").write_text(
        "".join(json.dumps(tc, ensure_ascii=True) + "\n" for tc in changed), encoding="utf-8")
    write_json(root / "run_set.json", run)
    rebuild_trace(root, cases, run)
    csv_path = root / "test_cases" / "test_cases.csv"
    with csv_path.open(encoding="utf-8-sig", newline="") as stream:
        reader = csv.DictReader(stream)
        fields, rows = reader.fieldnames, list(reader)
    if fields is None:
        raise ValueError("TC CSV missing header")
    if "qualified_id" not in fields:
        fields.append("qualified_id")
    cmap = {t["id"]: t for t in cases}
    for row in rows:
        row["qualified_id"] = cmap[row["tc_id"]]["qualified_id"]
        row["verification"] = "NOT_ASSESSED"
    with csv_path.open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=fields)
        writer.writeheader()
        writer.writerows(rows)
    for archive in root.glob("*.zip"):
        # Preserve evidence outside the deliverable rather than deleting or hiding it.
        quarantine = root.parent / "repair-evidence" / root.name
        quarantine.mkdir(parents=True, exist_ok=True)
        shutil.move(str(archive), str(quarantine / archive.name))
    for folder, filename in [("test_cases", "test_case_validation.json"),
                             ("requirements_scenarios", "validation_report.json")]:
        path = root / folder / filename
        if path.exists():
            document = gates.load(path)
            document["producer_original_validation"] = {
                "valid": document.get("valid"), "findings": document.get("findings"),
            }
            document["valid"] = None
            document["status"] = "NOT_ASSESSED_AFTER_EXPORT_REPAIR"
            document["findings"] = ["Independent revalidation and review required"]
            write_json(path, document)
    summary = (
        f"# Corrected export\n\nBuild: `{build}`\n\n"
        f"TCs: {len(cases)}. Request TD files: {run['files']}.\n\n"
        "This is a deterministic export repair, not a producer rebuild or semantic approval.\n"
        "See reports/traceability_matrix_full.json for final references. "
        "Runtime tokens, client inputs and EMV simulator requirements remain unresolved.\n"
        "Execution readiness: BLOCKED. Review status: pending_review.\n"
        "Producer hashes are retained as producer_original_hashes, not promoted as current digests.\n"
    )
    (root / "README.md").write_text(summary, encoding="utf-8")
    (root / "DELIVERY_CONTRACT.md").write_text(
        summary + "\nReferences are package-root-relative. Final TD files are request JSON templates, "
        "not observed transport bytes. Multi-leg order is in run_set.json and test_data_leg_files.\n"
        "Approval is format/hierarchy only. Required inputs and open items must be resolved "
        "before execution. Original excluded archives remain in repair-evidence outside this package.\n",
        encoding="utf-8")
    return {"package": root.name, "emv_files_repaired": len(repaired_files), "cases": len(cases)}


def main() -> int:
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--input", type=Path, required=True)
    parser.add_argument("--output", type=Path, required=True)
    args = parser.parse_args()
    gates.trusted_source()
    source, destination = args.input.resolve(), args.output.resolve()
    if destination.exists() or destination.is_relative_to(source) or source.is_relative_to(destination):
        raise ValueError("Repair output must be new, separate and non-overlapping")
    before = gates.inventory(source)
    source_packages = gates.packages(source)
    destination.mkdir(parents=True)
    results = []
    for package in source_packages:
        target = destination / package.name
        shutil.copytree(package, target)
        results.append(repair_package(target))
    if before != gates.inventory(source):
        raise RuntimeError("Original delivery changed during repair")
    write_json(destination / "repair-manifest.json", {
        "source_sha256": gates.SOURCE_SHA256, "original_files": before,
        "repairs": results, "output_files": gates.inventory(destination),
        "status": "EXPORT_REPAIRED_PENDING_REVIEW", "execution_readiness": "BLOCKED",
        "producer_source_repaired": False,
    })
    print(json.dumps(results, indent=2))
    return 0


if __name__ == "__main__":
    sys.exit(main())
