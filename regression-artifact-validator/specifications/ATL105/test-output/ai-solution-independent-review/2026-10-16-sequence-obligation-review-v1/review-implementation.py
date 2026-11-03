import argparse
import collections
import csv
import gzip
import hashlib
import json
import os
import re
import runpy
from pathlib import Path

import ijson


SCRIPT = Path(__file__).resolve()
ROOT = SCRIPT.parents[1]
REVIEW = ROOT / "specifications/ATL105/test-output/ai-solution-independent-review"
AUDIT = REVIEW / "2026-10-14-delivery-wide-assessment-v6"
QUEUE = REVIEW / "2026-10-16-all-ai-br-chain-test-br-review-v2"
SPEC = ROOT / "specifications/ATL105/docs/specs/extracted_text.txt"
PACKAGE = ROOT / "specifications/ATL105/test-output/test-solution-independent-review/complete-all-test-solution-br-ts-tc-td-package.json"
HELPER_PATH = SCRIPT.with_name("build-account-card-br-crossmatch-review.py")
HELPERS = runpy.run_path(str(HELPER_PATH))
SELECTED = {"REQ-SRC-ATL105-PDF-001:3251": "PRESENCE",
            "REQ-SRC-ATL105-PDF-001:6098": "FORMAT_AND_LIFETIME",
            "REQ-SRC-ATL105-PDF-001:1937": "LIFECYCLE_PERSISTENCE"}


def native(path):
    resolved = str(Path(path).resolve())
    if os.name == "nt" and not resolved.startswith("\\\\?\\"):
        resolved = "\\\\?\\" + resolved
    return Path(resolved)


def sha256(path):
    return HELPERS["sha256"](native(path))


def read_json(path):
    return json.loads(native(path).read_text(encoding="utf-8-sig"))


def read_lines(path):
    opener = gzip.open if path.suffix == ".gz" else open
    with opener(native(path), "rt", encoding="utf-8") as stream:
        for line in stream:
            if line.strip():
                yield json.loads(line)


def references(row, plural, singular):
    values = row.get(plural) or []
    if isinstance(values, str):
        values = [values]
    return set(values) | ({row[singular]} if row.get(singular) else set())


def record_hash(record):
    return hashlib.sha256(json.dumps(record, sort_keys=True, ensure_ascii=True, separators=(",", ":")).encode()).hexdigest()


def sequence_fields(value, location="", segment=""):
    result = []
    if isinstance(value, dict):
        segment = str(value.get("SegmentType") or segment)
        for key, child in value.items():
            path = location + "/" + key
            normalized = re.sub(r"[^a-z]", "", key.lower())
            if normalized == "sequencenumber":
                scalar = child.get("value") if isinstance(child, dict) else child
                context = str((child.get("data_ref") or {}).get("segment") or segment) if isinstance(child, dict) else segment
                result.append({"path": path, "segment": context, "value": scalar,
                    "sixAsciiDigitsObserved": isinstance(scalar, str) and bool(re.fullmatch(r"[0-9]{6}", scalar)),
                    "generalRangeObserved": isinstance(scalar, str) and bool(re.fullmatch(r"[0-9]{6}", scalar)) and "000001" <= scalar <= "999999"})
            else:
                result.extend(sequence_fields(child, path, segment))
    elif isinstance(value, list):
        for index, child in enumerate(value):
            result.extend(sequence_fields(child, location + "/" + str(index), segment))
    return result


def write_jsonl(path, rows):
    with native(path).open("w", encoding="utf-8", newline="\n") as stream:
        for row in rows:
            stream.write(json.dumps(row, ensure_ascii=True, sort_keys=True) + "\n")


def self_test():
    observations = sequence_fields({"Standard Segment": {"SegmentType": "100", "SequenceNumber": "000001"},
                                    "EMV": {"EMVCardSequenceNumber": "25"}})
    assert len(observations) == 1 and observations[0]["sixAsciiDigitsObserved"]
    assert observations[0]["segment"] == "100"
    assert not sequence_fields({"SequenceNumber": "000000"})[0]["generalRangeObserved"]
    assert not sequence_fields({"SequenceNumber": "12345"})[0]["sixAsciiDigitsObserved"]
    assert not sequence_fields({"SequenceNumber": None})[0]["sixAsciiDigitsObserved"]
    assert references({"requirement_id": "BR1"}, "requirement_ids", "requirement_id") == {"BR1"}
    print("PASS: six-digit/range observations, omitted fields, distinct EMV card sequence and singular BR links")


def run(output):
    if native(output).exists():
        raise ValueError("Refusing to overwrite review output")
    audit_summary = read_json(AUDIT / "summary.json")
    if sha256(AUDIT / "file-inventory.jsonl") != audit_summary["fileInventorySha256"]:
        raise ValueError("Frozen inventory hash mismatch")
    inventory = {row["path"]: row for row in read_lines(AUDIT / "file-inventory.jsonl")}
    if len(inventory) != audit_summary["fileCount"]:
        raise ValueError("Frozen artifact inventory population mismatch")
    root = Path(audit_summary["runRoot"])
    queue_summary = read_json(QUEUE / "summary.json")
    if sha256(PACKAGE) != queue_summary["inputSha256"][str(PACKAGE)]:
        raise ValueError("Independent Test package changed")
    queue_manifest = read_json(QUEUE / "manifest.json")
    chain_path = QUEUE / "ai-br-chain-evidence.jsonl.gz"
    if sha256(chain_path) != queue_manifest["artifacts"][chain_path.name]:
        raise ValueError("Frozen AI chain evidence changed")
    chains = {row["aiBrId"]: row for row in read_lines(chain_path) if row["aiBrId"] in SELECTED}
    if set(chains) != set(SELECTED):
        raise ValueError("Starter group omitted a selected BR")
    scenario_ids = {identifier for row in chains.values() for identifier in row["scenarioIds"]}
    case_ids = {identifier for row in chains.values() for identifier in row["caseIds"]}
    inputs = [SCRIPT, HELPER_PATH, SPEC, PACKAGE, AUDIT / "summary.json", AUDIT / "file-inventory.jsonl",
              AUDIT / "tc-physical-assessment.jsonl", AUDIT / "artifact-occurrences.jsonl", chain_path]
    input_hashes = {str(path): sha256(path) for path in inputs}
    for name in ["tc-physical-assessment.jsonl", "artifact-occurrences.jsonl"]:
        if sha256(AUDIT / name) != audit_summary["outputHashes"][name]:
            raise ValueError("Frozen ledger hash mismatch: " + name)
    occurrences = []
    checked_paths = {}
    catalogs = {relative: item for relative, item in inventory.items() if item["kind"] == "CATALOG"}
    for relative in sorted(catalogs):
        path = root / relative
        digest = sha256(path)
        if digest != catalogs[relative]["sha256"]:
            raise ValueError("Source catalog changed: " + relative)
        checked_paths[relative] = "CURRENT_HASH_VERIFIED_CATALOG_RECORDS_INSPECTED_FOR_STARTER_GROUP"
        kind = audit_summary["catalogs"][relative]["kind"]
        prefix = {"BR": "requirements.item", "TS": "scenarios.item", "TC": "test_cases.item"}[kind]
        wanted = set(SELECTED) if kind == "BR" else scenario_ids if kind == "TS" else case_ids
        with native(path).open("rb") as stream:
            for row in ijson.items(stream, prefix, use_float=True):
                if row.get("id") not in wanted:
                    continue
                evidence = {"kind": kind, "id": row["id"], "catalog": relative, "catalogSha256": digest,
                            "recordSha256": record_hash(row), "fullSemanticReviewStatus": "REVIEW_REQUIRED"}
                if kind == "BR":
                    evidence.update({key: row.get(key) for key in ["statement", "source_rule_id", "source_page", "expected_behavior", "flags"]})
                elif kind == "TS":
                    evidence.update({key: row.get(key) for key in ["name", "type", "target_transaction", "expected_response_code", "expected_decline_code", "oracle_authority", "flags"]})
                    evidence["requirementIds"] = sorted(references(row, "requirement_ids", "requirement_id"))
                else:
                    expected = row.get("expected_response") or {}
                    response = expected.get("response_code") or {} if isinstance(expected, dict) else {}
                    evidence.update({"scenarioId": row.get("scenario_id"), "scenarioType": row.get("scenario_type"),
                        "transactionType": row.get("transaction_type"), "negativeClass": row.get("negative_class"),
                        "violatedElement": row.get("violated_element_name"), "sequenceFields": sequence_fields(row.get("request") or {}),
                        "declaredResponseCode": response.get("value"), "declaredAcceptableResponseCodes": response.get("acceptable_values"),
                        "producerOracleTier": response.get("tier"), "producerOracleFlags": response.get("flags"),
                        "responseTemplateAvailable": (expected.get("response_message") or {}).get("available") if isinstance(expected, dict) else None,
                        "independentExpectedOutcomeValidated": False, "flags": row.get("flags") or []})
                occurrences.append(evidence)
    physical_rows = [row for row in read_lines(AUDIT / "tc-physical-assessment.jsonl") if row["id"] in case_ids]
    paths = {relative for row in physical_rows for relative in row.get("paths", [])}
    paths.update(str(Path(relative).with_suffix(".meta.json")).replace("\\", "/") for relative in list(paths))
    payload_evidence = []
    for relative in sorted(paths):
        frozen = inventory.get(relative)
        if not frozen:
            payload_evidence.append({"path": relative, "state": "ABSENT_FROM_FROZEN_INVENTORY_NOT_CREDITED"})
            continue
        path = root / relative
        if not native(path).is_file():
            payload_evidence.append({"path": relative, "state": "CURRENT_FILE_UNAVAILABLE"})
            continue
        digest = sha256(path)
        if digest != frozen["sha256"]:
            raise ValueError("Source artifact changed: " + relative)
        value = read_json(path)
        observations = sequence_fields(value)
        evidence = {"path": relative, "kind": frozen["kind"], "sha256": digest,
                    "state": "CURRENT_HASH_VERIFIED_SEQUENCE_FIELDS_INSPECTED",
                    "sequenceFields": observations, "fullSemanticReviewStatus": "REVIEW_REQUIRED"}
        if frozen["kind"] == "METADATA":
            evidence["rootKeys"] = sorted(value) if isinstance(value, dict) else []
            evidence["identityAndPayloadCorrespondence"] = "NOT_ADJUDICATED"
        payload_evidence.append(evidence)
        checked_paths[relative] = evidence["state"]
    selected_cases = [row for row in occurrences if row["kind"] == "TC"]
    if {row["id"] for row in selected_cases} != case_ids:
        raise ValueError("Some linked cases lack catalog evidence")
    package = read_json(PACKAGE)
    targets = []
    for br in package["businessRequirements"]:
        if any("86" in re.split(r"[,|]", str(anchor.get("element") or "")) for anchor in br.get("sourceAnchors", [])):
            targets.append(HELPERS["chain_for"](package, br))
    source_lines = native(SPEC).read_text(encoding="utf-8-sig").splitlines()
    start = next(index for index, line in enumerate(source_lines) if "Number: 86 Name: Sequence Number" in line)
    source = {"source": str(SPEC), "sha256": sha256(SPEC), "startLine": start + 1, "endLine": start + 36,
              "quote": "\n".join(source_lines[start:start + 36]), "interpretationStatus": "PRELIMINARY_NOT_APPROVED"}
    decisions = [
        {"aiBrId": identifier, "obligation": obligation, "brAlignment": "PARTIAL_CLAUSE_OVERLAP_PROPOSED",
         "testBrCandidates": ["BR-RULE-SEG100-R-019", "BR-RULE-SEG100-R-037"],
         "chainAdequacy": "PARTIAL" if obligation != "LIFECYCLE_PERSISTENCE" else "MISSING_LINKED_STEP_COMPARISON",
         "rationale": "Presence cases do not establish format or lifecycle obligations." if obligation == "PRESENCE" else
                      "Six-digit examples do not establish all boundaries, allowed range, or transaction-lifetime behavior." if obligation == "FORMAT_AND_LIFETIME" else
                      "Standalone cases sharing a literal sequence do not demonstrate authorization-to-follow-up persistence.",
         "scenarioCount": len(chains[identifier]["scenarioIds"]), "caseCount": len(chains[identifier]["caseIds"]),
         "reviewStatus": "OPEN_REVIEW_REQUIRED", "semanticApprovalGranted": False}
        for identifier, obligation in SELECTED.items()]
    for path, digest in input_hashes.items():
        if sha256(Path(path)) != digest:
            raise ValueError("Review input changed during inspection: " + path)
    native(output).mkdir(parents=True)
    write_jsonl(output / "catalog-record-evidence.jsonl", occurrences)
    write_jsonl(output / "payload-metadata-evidence.jsonl", payload_evidence)
    write_jsonl(output / "ai-chain-evidence.jsonl", chains.values())
    write_jsonl(output / "test-chain-evidence.jsonl", targets)
    write_jsonl(output / "source-evidence.jsonl", [source])
    write_jsonl(output / "obligation-review.jsonl", decisions)
    write_jsonl(output / "conflicting-case-occurrences.jsonl", (
        row for row in read_lines(AUDIT / "artifact-occurrences.jsonl") if row["kind"] == "TC" and row["id"] in case_ids))
    with native(output / "all-artifact-ledger.csv").open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=["path", "kind", "bytes", "frozenSha256", "currentInspection", "fullSemanticReviewStatus"])
        writer.writeheader()
        for relative, item in inventory.items():
            writer.writerow({"path": relative, "kind": item["kind"], "bytes": item["bytes"], "frozenSha256": item["sha256"],
                "currentInspection": checked_paths.get(relative, "INVENTORIED_NOT_INDIVIDUALLY_INSPECTED"),
                "fullSemanticReviewStatus": "PENDING_REVIEW"})
    summary = {"scope": "ALL_AI_ARTIFACTS_IN_FROZEN_DELIVERY", "inventoriedFiles": len(inventory),
        "inventoryByKind": dict(collections.Counter(row["kind"] for row in inventory.values())),
        "catalogPopulation": audit_summary["uniqueIdsByKind"], "starterBrCount": len(decisions),
        "starterUniqueScenarios": len(scenario_ids), "starterUniqueCases": len(case_ids),
        "starterCatalogOccurrences": len(occurrences), "starterPayloadMetadataStates": dict(collections.Counter(row["state"] for row in payload_evidence)),
        "starterHashVerifiedFiles": len(checked_paths), "filesNotIndividuallyInspected": len(inventory) - len(checked_paths),
        "fullSemanticReviewsCompleted": 0, "aiScenarioApprovalRequired": False, "inputSha256": input_hashes,
        "limitations": ["Starter review includes every catalog occurrence and declared payload/metadata path for three BR chains; it does not exhaust the delivery.",
            "All 79,040 files stay pending semantic review, including files whose hashes or field values were inspected.",
            "All catalog states and conflicting case occurrences remain visible; approved producer labels are not inclusion gates.",
            "Source interpretation, complete scenario intent, independent outcomes, metadata correspondence and multileg state remain open.",
            "The external authorization attachment was not ingested or modified; its values are not treated as independent expected outcomes."]}
    native(output / "summary.json").write_text(json.dumps(summary, indent=2), encoding="utf-8")
    report = "# All-Artifact Review: Start Here\n\n"
    report += "Scope: all 79,040 intake files and all BR/TS/TC records, including unlinked and conflicting artifacts. Nothing is approved by sampling.\n\n"
    report += "## First Group: Sequence Number\n\n| AI BR | Obligation | Provisional Chain Finding |\n|---|---|---|\n"
    report += "| :3251 | Presence | Presence/omission cases; format and lifecycle remain separate |\n"
    report += "| :6098 | Six-digit format and lifetime | Positive values alone do not demonstrate boundaries or lifetime |\n"
    report += "| :1937 | Persistence | Linked initial/follow-up comparison not demonstrated |\n\n"
    report += "Candidate Test clauses: SEG100-R-019 / SEG100-R-037. Full matching Test chains, source quotes and separate BR/chain decisions are retained. All decisions remain open.\n\n"
    report += "## Continue\n\n1. Compare each distinct BR claim with source and Test obligations.\n2. Review every linked scenario and case, not only representatives.\n3. Inspect every declared payload/metadata path and expected outcome; keep conflicts and unlinked artifacts as exceptions.\n\n"
    report += "Use [all-artifact-ledger.csv](all-artifact-ledger.csv) to prevent skipped files, [obligation-review.jsonl](obligation-review.jsonl) for the first decisions and [summary.json](summary.json) for exact progress. Hash-checked is not semantically reviewed. The ledger tracks file scope; record-level scenario/case evidence must also be reviewed.\n"
    native(output / "START.md").write_text(report, encoding="utf-8")
    native(output / "review-implementation.py").write_bytes(SCRIPT.read_bytes())
    artifacts = {path.name: sha256(path) for path in native(output).iterdir() if path.is_file()}
    native(output / "manifest.json").write_text(json.dumps({"algorithm": "SHA-256", "artifacts": artifacts}, indent=2), encoding="utf-8")
    print(json.dumps({key: value for key, value in summary.items() if key not in {"inputSha256", "limitations"}}, indent=2))


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--output", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
    elif args.output:
        run(args.output.resolve())
    else:
        parser.error("Supply a new output directory or --self-test")


if __name__ == "__main__":
    main()