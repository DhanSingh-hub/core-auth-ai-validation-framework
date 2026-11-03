"""Build a client-shareable inventory for the 181026 Run delivery."""

import argparse
import csv
import hashlib
import json
from collections import Counter
from pathlib import Path


ROOT = Path("specifications/ATL105/test-input/ai-solution/runs/2026-10-19/Run3")
INTAKE_MANIFEST = Path("specifications/ATL105/test-input/ai-solution/runs/2026-10-19/Run3-intake-manifest.json")
AUDIT_DIR = Path("specifications/ATL105/test-output/ai-solution-independent-review/2026-10-19-181026-run-delivery-wide-assessment-v3")
DEFAULT_OUTPUT = Path("specifications/ATL105/test-output/ai-solution-independent-review/2026-10-19-181026-run-client-artifact-inventory-v2")


def sha256(path):
    digest = hashlib.sha256()
    with path.open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def classify(path, kind):
    if kind == "PAYLOAD":
        return (
            "Test-data payload",
            "JSON",
            "QE-shaped test-data fixture for a test case; step-named files represent transaction legs.",
            "Inventory describes file role only; it does not validate data values, message correctness, or execution.",
        )
    if kind == "METADATA":
        return (
            "Test-data metadata sidecar",
            "JSON",
            "Companion metadata for a test-data payload, used for case identity and field/element traceability.",
            "No metadata field values are included in this inventory; semantic correctness is not assessed.",
        )
    if path == "Requirement candidates/requirement_candidates.json":
        return (
            "Business-requirement catalog",
            "JSON",
            "AI-derived candidate BR records used as inputs to scenario and test-case generation.",
            "Candidate catalog, not an approved requirement baseline.",
        )
    if path == "scenarios/scenarios/approved/approved_scenarios.json":
        return (
            "Scenario catalog",
            "JSON",
            "Scenario records linked to requirement candidates and used to organize test generation.",
            "Producer state is DEV_APPROVED; the catalog notes this was scripted approval without SME review.",
        )
    if path == "scenarios/scenarios/candidates/candidate_scenarios.json":
        return (
            "Candidate-scenario catalog",
            "JSON",
            "Candidate scenario output tier for scenario-generation workflow.",
            "The supplied catalog contains zero records; producer metadata reports skipped duplicates.",
        )
    if path == "test_case_candidates.json":
        return (
            "Test-case catalog",
            "JSON",
            "Candidate test cases, their requirement/scenario references, and test-data declarations.",
            "Candidate cases and producer completeness labels are not independent acceptance or execution evidence.",
        )
    if path == "traceability_matrix.md":
        return (
            "Traceability matrix (readable) ",
            "Markdown",
            "Human-readable producer matrix for BR, scenario, test-case, and data relationships.",
            "Producer-internal traceability view; not proof of source completeness or semantic coverage.",
        )
    if path == "traceability_matrix_full.json":
        return (
            "Traceability matrix (machine-readable)",
            "JSON",
            "Structured producer matrix for BR, scenario, test-case, and data relationships.",
            "Producer-internal traceability view; not proof of source completeness or semantic coverage.",
        )
    if path.endswith("/traceability.json"):
        return (
            "Pipeline traceability support",
            "JSON",
            "Additional producer traceability artifact found alongside test data.",
            "Auxiliary format was not independently interpreted by the delivery inventory audit.",
        )
    if path.endswith(".phrasing_cache.json"):
        return (
            "Generation cache",
            "JSON",
            "Cached phrasing support used by the requirement-generation workflow.",
            "Workflow support file, not a business requirement, test case, or test-data fixture.",
        )
    if "/__pycache__/" in path and path.endswith(".pyc"):
        return (
            "Python bytecode cache",
            "PYC",
            "Interpreter-generated cache for scenario-generation support code.",
            "Not a source file or a consumer-facing test artifact.",
        )
    if path.startswith("scenarios/scenarios/") and path.endswith(".py"):
        return (
            "Scenario-generation support code",
            "Python",
            "Source code for scenario construction, flow expansion, and rule resolution.",
            "Supporting implementation, not generated scenario/test output; not runtime-certified by this inventory.",
        )
    return (
        "Other auxiliary artifact",
        Path(path).suffix.lstrip(".").upper() or "FILE",
        "Supporting artifact included in the delivery.",
        "Purpose requires format-specific review; no semantic or execution credit is assigned.",
    )


def build(output_dir):
    output_dir = output_dir.resolve()
    if output_dir.exists():
        raise ValueError("Output directory already exists; refusing to overwrite")

    manifest = json.loads(INTAKE_MANIFEST.read_text(encoding="utf-8-sig"))
    if not manifest.get("verified") or manifest.get("missingFromCopy") or manifest.get("extraInCopy") or manifest.get("hashMismatches"):
        raise ValueError("Run3 intake manifest is not verified")

    manifest_files = {entry["path"]: entry for entry in manifest["files"]}
    inventory_path = AUDIT_DIR / "file-inventory.jsonl"
    inventory_rows = []
    for line in inventory_path.open(encoding="utf-8"):
        row = json.loads(line)
        source = manifest_files.get(row["path"])
        if source is None or source["sha256"] != row["sha256"] or source["bytes"] != row["bytes"]:
            raise ValueError("Audit inventory differs from verified intake manifest: " + row["path"])
        group, file_format, usage, caveat = classify(row["path"], row["kind"])
        inventory_rows.append({
            "relative_path": row["path"],
            "artifact_group": group.strip(),
            "format": file_format,
            "bytes": row["bytes"],
            "sha256": row["sha256"],
            "intended_usage": usage,
            "client_note": caveat,
        })
    if len(inventory_rows) != manifest["copyFileCount"]:
        raise ValueError("Audit inventory does not cover every verified intake file")

    output_dir.mkdir(parents=True)
    csv_path = output_dir / "181026-RUN-FILE-INVENTORY.csv"
    fields = ["relative_path", "artifact_group", "format", "bytes", "sha256", "intended_usage", "client_note"]
    with csv_path.open("w", newline="", encoding="utf-8-sig") as stream:
        writer = csv.DictWriter(stream, fieldnames=fields)
        writer.writeheader()
        writer.writerows(sorted(inventory_rows, key=lambda item: item["relative_path"].casefold()))

    groups = Counter(row["artifact_group"] for row in inventory_rows)
    root_counts = Counter(row["relative_path"].split("/", 1)[0] for row in inventory_rows)
    total_bytes = sum(row["bytes"] for row in inventory_rows)
    summary = {
        "title": "181026 Run client artifact inventory",
        "assessment_date": "2026-10-19",
        "intake_id": "Run3",
        "intake_verified": manifest["verified"],
        "file_count": len(inventory_rows),
        "total_bytes": total_bytes,
        "source_copy_bytes": manifest["totalBytes"],
        "files_by_artifact_group": dict(sorted(groups.items())),
        "files_by_delivery_root": dict(sorted(root_counts.items())),
        "source_intake_manifest_sha256": sha256(INTAKE_MANIFEST),
        "complete_v3_file_inventory_sha256": sha256(inventory_path),
        "payload_values_exported": False,
        "classification_basis": "Artifact directory, filename, and v3 structural file kind; intended usage descriptions are descriptive, not semantic approval.",
    }
    summary_path = output_dir / "summary.json"
    summary_path.write_text(json.dumps(summary, ensure_ascii=True, indent=2) + "\n", encoding="utf-8")

    group_rows = "\n".join(f"| {name} | {count:,} |" for name, count in sorted(groups.items()))
    root_rows = "\n".join(f"| `{name}/` | {count:,} |" for name, count in sorted(root_counts.items()))
    report = f"""# 181026 Run: Client Artifact Inventory

**Intake:** Run3, captured from the `181026 Run` delivery  
**Inventory date:** 2026-10-19  
**Status:** File-level inventory complete; file hashes reconciled to the verified intake manifest.

## Executive Summary

The delivery contains **{len(inventory_rows):,} files** totaling **{total_bytes:,} bytes**. The Run3 intake manifest records {manifest['sourceFileCount']:,} source files and {manifest['copyFileCount']:,} copied files, with no missing paths, extra paths, or hash mismatches. This inventory lists relative paths, file classes, sizes, SHA-256 hashes, and a plain-language description of intended use. It does not include payload or metadata field values.

The delivery is organized around candidate business requirements, scenario records, test-case records, test-data payloads and sidecars, plus traceability and generation-support files. “Intended use” is inferred from the directory, filename, catalog role, and structural type; it is not a claim of business approval or test certification.

## File Groups

| Artifact group | Files |
|---|---:|
{group_rows}

## Delivery Roots

| Relative root | Files |
|---|---:|
{root_rows}

## What the Main Artifacts Are For

| Relative path | Purpose | Qualification |
|---|---|---|
| `Requirement candidates/requirement_candidates.json` | Candidate BR statements used by downstream scenario/test generation | Candidate tier; not an approved BR catalog |
| `scenarios/scenarios/approved/approved_scenarios.json` | Scenario records associated with the candidate requirements | Producer label is `DEV_APPROVED`; producer note says scripted approval without SME review |
| `scenarios/scenarios/candidates/candidate_scenarios.json` | Candidate scenario output tier | Empty catalog in this delivery |
| `test_case_candidates.json` | Candidate test cases, links, and declared test-data references | Candidate records and producer completeness labels are not independent acceptance |
| `traceability_matrix.md` | Readable BR-to-scenario-to-test relationship view | Producer-generated traceability; does not establish independent semantic coverage |
| `traceability_matrix_full.json` | Machine-readable form of the producer traceability matrix | Same limitation as the Markdown matrix |
| `qe_shaped_test_data/qe_shaped_test_data/TD-TC-*.json` | Test-data payloads, including step-named transaction-leg payloads | Payload values were not exported or semantically certified in this inventory |
| `qe_shaped_test_data/qe_shaped_test_data/TD-TC-*.meta.json` | Sidecar metadata accompanying payload files | Metadata field values were not exported or semantically certified |
| `scenarios/scenarios/*.py` | Scenario generation and flow-expansion support code | Source tooling, not generated test output |
| `scenarios/scenarios/__pycache__/*.pyc` | Python interpreter bytecode caches | Incidental support files; not intended test inputs |

The CSV contains an entry for **every inventoried file**, including exact relative path, byte size, SHA-256, artifact group, intended use, and client note. It deliberately excludes contents and field values.

## Client-Use Boundaries

- Candidate BRs, scenarios, and test cases should be presented as generated artifacts, not approved requirements or certified tests.
- The scenario catalog's `DEV_APPROVED` producer state does not represent SME sign-off.
- Traceability files are navigation aids from the producer; their presence does not prove source completeness or semantic equivalence.
- Payload and sidecar files are inventoried by name and hash only. This report does not certify data correctness, protocol/wire validity, host behavior, or test execution.
- The file inventory is based on the complete v3 structural inventory cross-checked against the verified Run3 intake manifest. No payload values are included.

## Evidence

- [Complete file-level CSV inventory](181026-RUN-FILE-INVENTORY.csv)
- [Machine-readable summary](summary.json)
- [Run3 intake manifest](../../../test-input/ai-solution/runs/2026-10-19/Run3-intake-manifest.json)
- [Delivery-wide structural audit](../2026-10-19-181026-run-delivery-wide-assessment-v3/REPORT.md)

**Use limitation:** This is a file inventory and intended-use guide for client orientation, not a semantic-coverage report, SME approval, execution report, or certification.
"""
    report_path = output_dir / "CLIENT-ARTIFACT-INVENTORY.md"
    report_path.write_text(report, encoding="utf-8")

    artifacts = {path.name: sha256(path) for path in (csv_path, summary_path, report_path, Path(__file__).resolve())}
    output_manifest = {
        "algorithm": "SHA-256",
        "intakeManifestSha256": summary["source_intake_manifest_sha256"],
        "completeV3FileInventorySha256": summary["complete_v3_file_inventory_sha256"],
        "fileCount": len(inventory_rows),
        "artifacts": artifacts,
    }
    (output_dir / "manifest.json").write_text(json.dumps(output_manifest, ensure_ascii=True, indent=2) + "\n", encoding="utf-8")
    print(json.dumps({"output": str(output_dir), "fileCount": len(inventory_rows), "bytes": total_bytes, "groups": dict(groups), "artifacts": artifacts}, indent=2))


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--output-dir", type=Path, default=DEFAULT_OUTPUT)
    args = parser.parse_args()
    build(args.output_dir)


if __name__ == "__main__":
    main()
