import argparse
import collections
import gzip
import hashlib
import json
import os
import re
from pathlib import Path


SCRIPT = Path(__file__).resolve()
ROOT = SCRIPT.parents[1]
REVIEW = ROOT / "specifications/ATL105/test-output/ai-solution-independent-review"
AUDIT = REVIEW / "2026-10-14-delivery-wide-assessment-v6"
STARTER = REVIEW / "2026-10-16-sequence-obligation-review-v1"
PACKAGE_DIR = ROOT / "specifications/ATL105/test-output/test-solution-independent-review"
PACKAGE = PACKAGE_DIR / "complete-all-test-solution-br-ts-tc-td-package.json"
SPEC = ROOT / "specifications/ATL105/docs/specs/extracted_text.txt"
SELECTED = {"REQ-SRC-ATL105-PDF-001:3251", "REQ-SRC-ATL105-PDF-001:6098", "REQ-SRC-ATL105-PDF-001:1937"}


def native(path):
    resolved = str(Path(path).resolve())
    if os.name == "nt" and not resolved.startswith("\\\\?\\"):
        resolved = "\\\\?\\" + resolved
    return Path(resolved)


def sha256(path):
    digest = hashlib.sha256()
    with native(path).open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def read_json(path):
    return json.loads(native(path).read_text(encoding="utf-8-sig"))


def jsonl(path):
    with gzip.open(native(path), "rt", encoding="utf-8") if path.suffix == ".gz" else native(path).open(encoding="utf-8") as stream:
        for line in stream:
            if line.strip():
                yield json.loads(line)


def normalize(value):
    return re.sub(r"[^a-z0-9]", "", str(value or "").lower())


def sequence_field_entries(value, location=""):
    result = []
    if isinstance(value, dict):
        for name, child in value.items():
            path = location + "/" + name
            if normalize(name) == "sequencenumber":
                scalar = child.get("value") if isinstance(child, dict) else child
                reference = child.get("data_ref") or {} if isinstance(child, dict) else {}
                result.append({"field": name, "path": path, "value": scalar,
                               "segment": str(reference.get("segment") or "").upper()})
            result.extend(sequence_field_entries(child, path))
    elif isinstance(value, list):
        for index, child in enumerate(value):
            result.extend(sequence_field_entries(child, location + "/" + str(index)))
    return result


def metadata_sequence_entries(value):
    result = []
    for field in value.get("fields", []) if isinstance(value, dict) else []:
        if normalize(field.get("specElementName")) == "sequencenumber" or normalize(field.get("element")) == "sequencenumber":
            result.append({"segment": str(field.get("segment") or "").upper(),
                           "element": field.get("element"), "specElementName": field.get("specElementName"),
                           "value": field.get("value"), "dataSource": field.get("dataSource"),
                           "provisional": field.get("provisional")})
    unavailable = []
    for field in value.get("unavailableFields", []) if isinstance(value, dict) else []:
        if normalize(field.get("element")) == "sequencenumber" or normalize(field.get("friendly")) == "sequencenumber":
            unavailable.append({"segment": str(field.get("segment") or "").upper(),
                                "element": field.get("element"), "friendly": field.get("friendly"),
                                "reason": field.get("reason")})
    return result, unavailable


def self_test():
    payload = {"Standard Message Data Segment": {"Segment Type": "100", "Sequence Number": "000001"}}
    sidecar = {"testCaseId": "TC-1", "scenarioId": "SC-1",
               "fields": [{"segment": "100", "element": "SequenceNumber", "specElementName": "Sequence Number", "value": "000001"}]}
    values = sequence_field_entries(payload)
    fields, missing = metadata_sequence_entries(sidecar)
    assert values[0]["value"] == fields[0]["value"] and not missing
    sidecar["fields"][0]["value"] = "000002"
    fields, _ = metadata_sequence_entries(sidecar)
    assert values[0]["value"] != fields[0]["value"]
    print("PASS: normalized payload/sidecar names, segment context and mismatch distinction")


def bind(output_dir):
    output_dir = native(output_dir)
    if output_dir.exists():
        raise ValueError("Refusing to overwrite output directory")
    for folder, files in [(STARTER, ["manifest.json", "ai-chain-evidence.jsonl", "catalog-record-evidence.jsonl", "payload-metadata-evidence.jsonl"]),
                          (AUDIT, ["summary.json", "file-inventory.jsonl", "tc-physical-assessment.jsonl", "artifact-occurrences.jsonl"])]:
        manifest = read_json(folder / "manifest.json") if (folder / "manifest.json").exists() else None
        if manifest:
            for name in files[1:]:
                if name in manifest.get("artifacts", {}) and sha256(folder / name) != manifest["artifacts"][name]:
                    raise ValueError("Input artifact hash mismatch: " + name)
    audit_summary = read_json(AUDIT / "summary.json")
    inventory_path = AUDIT / "file-inventory.jsonl"
    if sha256(inventory_path) != audit_summary["fileInventorySha256"]:
        raise ValueError("Frozen inventory changed")
    inventory = {row["path"]: row for row in jsonl(inventory_path)}
    if sha256(PACKAGE) != read_json(REVIEW / "2026-10-16-all-ai-br-chain-test-br-review-v2/summary.json")["inputSha256"][str(PACKAGE)]:
        raise ValueError("Independent Test package changed")

    chains = {row["aiBrId"]: row for row in jsonl(STARTER / "ai-chain-evidence.jsonl") if row["aiBrId"] in SELECTED}
    catalog_by_case = collections.defaultdict(list)
    catalog_by_scenario = collections.defaultdict(list)
    for row in jsonl(STARTER / "catalog-record-evidence.jsonl"):
        if row["kind"] == "TC":
            catalog_by_case[row["id"]].append(row)
        elif row["kind"] == "TS":
            catalog_by_scenario[row["id"]].append(row)
    payload_by_path = {row["path"]: row for row in jsonl(STARTER / "payload-metadata-evidence.jsonl")}
    route_by_case = collections.defaultdict(list)
    for row in jsonl(AUDIT / "tc-physical-assessment.jsonl"):
        route_by_case[row["id"]].append(row)

    occurrences = []
    unique_case_rows = {}
    output_dir.mkdir(parents=True)
    payload_path = output_dir / "sequence-number-case-payload-bindings.jsonl"
    summary_counts = collections.Counter()
    with native(payload_path).open("w", encoding="utf-8", newline="\n") as stream:
        for br_id, chain in sorted(chains.items()):
            for case in chain["cases"]:
                case_id = case["id"]
                case_catalogs = catalog_by_case.get(case_id, [])
                case_fingerprints = {row["recordSha256"] for row in case_catalogs}
                case_requirement_ids = {req for row in case_catalogs for req in row.get("requirementIds", [])}
                linked_scenario_id = case.get("scenarioId")
                case_scenarios = catalog_by_scenario.get(linked_scenario_id, [])
                metadata_hashes = []
                for catalog_case in case_catalogs:
                    metadata_hashes.append({"catalog": catalog_case["catalog"], "recordSha256": catalog_case["recordSha256"],
                                            "scenarioId": catalog_case.get("scenarioId"),
                                            "requirementIds": catalog_case.get("requirementIds", []),
                                            "sequenceFields": catalog_case.get("sequenceFields", []),
                                            "scenarioCatalogOccurrences": case_scenarios})
                payload_bindings = []
                for route in route_by_case.get(case_id, []):
                    for relative in route.get("paths", []):
                        frozen = inventory.get(relative)
                        evidence = payload_by_path.get(relative)
                        if not frozen:
                            state = "ABSENT_FROM_FROZEN_INVENTORY_NOT_CREDITED"
                        elif evidence is None:
                            state = "FROZEN_OCCURRENCE_WITHOUT_STARTER_PAYLOAD_OBSERVATION"
                        elif evidence.get("sha256") and evidence["sha256"] != frozen["sha256"]:
                            raise ValueError("Payload observation and frozen inventory SHA disagree: " + relative)
                        else:
                            state = evidence["state"]
                        if frozen:
                            path = native(Path(audit_summary["runRoot"]) / relative)
                            if path.is_file() and sha256(path) != frozen["sha256"]:
                                raise ValueError("Current payload differs from frozen inventory: " + relative)
                        sidecar_relative = str(Path(relative).with_suffix(".meta.json")).replace("\\", "/")
                        sidecar_evidence = payload_by_path.get(sidecar_relative)
                        sidecar_record = None
                        if sidecar_evidence and sidecar_evidence.get("state") == "CURRENT_HASH_VERIFIED_SEQUENCE_FIELDS_INSPECTED":
                            sidecar_path = native(Path(audit_summary["runRoot"]) / sidecar_relative)
                            sidecar = read_json(sidecar_path)
                            sidecar_values, unavailable = metadata_sequence_entries(sidecar)
                            sidecar_record = {"path": sidecar_relative, "sha256": sidecar_evidence.get("sha256"),
                                "caseId": sidecar.get("testCaseId"), "scenarioId": sidecar.get("scenarioId"),
                                "requirementId": sidecar.get("requirementId"), "negativeClass": sidecar.get("negativeClass"),
                                "sequenceFields": sidecar_values, "unavailableSequenceFields": unavailable,
                                "identityMatchesCase": sidecar.get("testCaseId") == case_id,
                                "identityMatchesScenario": sidecar.get("scenarioId") == linked_scenario_id}
                        payload_bindings.append({"path": relative, "frozenSha256": frozen.get("sha256") if frozen else None,
                            "payloadState": state, "sequenceFields": evidence.get("sequenceFields", []) if evidence else [],
                            "sidecar": sidecar_record, "semanticStatus": "NOT_ASSESSED"})
                        summary_counts[state] += 1
                metadata_values = [field for row in case_catalogs for field in row.get("sequenceFields", [])]
                payload_values = [field for binding in payload_bindings for field in binding["sequenceFields"]]
                declared_omission = any(field.get("value") is None for field in metadata_values)
                valid_payload_values = [field for field in payload_values if field.get("segment") == "100" and field.get("value") is not None]
                sidecars = [binding["sidecar"] for binding in payload_bindings if binding.get("sidecar")]
                correspondence = []
                for sidecar in sidecars:
                    side_values = [field for field in sidecar["sequenceFields"] if field["segment"] == "100"]
                    for metadata in side_values:
                        for payload in valid_payload_values:
                            if metadata["value"] == payload["value"]:
                                correspondence.append({"value": payload["value"], "metadataCaseIdMatches": sidecar["identityMatchesCase"],
                                                       "metadataScenarioIdMatches": sidecar["identityMatchesScenario"],
                                                       "dataSource": metadata.get("dataSource"), "provisional": metadata.get("provisional")})
                row = {"aiBrId": br_id, "caseId": case_id, "scenarioId": linked_scenario_id,
                    "caseCatalogOccurrences": metadata_hashes, "distinctCaseRecordHashes": sorted(case_fingerprints),
                    "caseContentConflict": len(case_fingerprints) > 1,
                    "caseRequirementLinks": sorted(case_requirement_ids), "caseLinkedToThisBr": br_id in case_requirement_ids,
                    "transactionType": case.get("transactionType"), "scenarioType": case.get("scenarioType"),
                    "negativeClass": case.get("negativeClass"), "violatedElement": case.get("violatedElementName"),
                    "declaredExpectedResponse": [{"code": r.get("declaredResponseCode"), "tier": r.get("producerOracleTier"),
                        "flags": r.get("producerOracleFlags"), "responseTemplateAvailable": r.get("responseTemplateAvailable"),
                        "independentlyValidated": False} for r in case_catalogs],
                    "sequenceMetadataOccurrences": metadata_values, "sequencePayloadValues": payload_values,
                    "declaredSequenceOmission": declared_omission, "verifiedMetadataPayloadCorrespondence": correspondence,
                    "physicalRoutes": route_by_case.get(case_id, []), "payloadBindings": payload_bindings,
                    "caseLifecycleComparison": "NO_LINKED_MULTI_TRANSACTION_ASSERTION_IN_CASE_RECORD",
                    "semanticStatus": "NOT_ASSESSED"}
                occurrences.append(row)
                unique_case_rows[case_id] = row
                stream.write(json.dumps(row, ensure_ascii=True, sort_keys=True) + "\n")

    decision_rows = []
    for br_id, obligation in [("REQ-SRC-ATL105-PDF-001:3251", "PRESENCE"),
                              ("REQ-SRC-ATL105-PDF-001:6098", "FORMAT_AND_LIFETIME"),
                              ("REQ-SRC-ATL105-PDF-001:1937", "LIFECYCLE_PERSISTENCE")]:
        chain = chains[br_id]
        rows = [unique_case_rows[cid] for cid in chain["caseIds"]]
        values = sorted({str(field.get("value")) for row in rows for field in row["sequencePayloadValues"]
                         if field.get("segment") == "100" and field.get("value") is not None})
        omission_cases = [row["caseId"] for row in rows if row["declaredSequenceOmission"]]
        correspondence_values = sorted({item["value"] for row in rows for item in row["verifiedMetadataPayloadCorrespondence"]
                                        if item["metadataCaseIdMatches"] and item["metadataScenarioIdMatches"]})
        decision_rows.append({"aiBrId": br_id, "obligation": obligation, "linkedCaseCount": len(rows),
            "sequenceValuesObservedInSegment100Payload": values, "caseRecordDeclaredOmissionIds": omission_cases,
            "caseMetadataPayloadMatchingValues": correspondence_values,
            "completeInitialFollowupCaseCount": 0,
            "preliminaryFinding": "PRESENCE_MUTATIONS_OBSERVED_BUT_RESPONSE_ORACLE_NOT_INDEPENDENT" if obligation == "PRESENCE" else
                "SIX_DIGIT_EXAMPLE_ONLY_BOUNDARY_AND_LIFETIME_NOT_DEMONSTRATED" if obligation == "FORMAT_AND_LIFETIME" else
                "LIFECYCLE_CLAIM_NOT_DEMONSTRATED_BY_SINGLE_CASE_RECORDS",
            "testBrCandidates": ["BR-RULE-SEG100-R-019", "BR-RULE-SEG100-R-037"],
            "testChainStatus": "DRAFT_REVIEW_REQUIRED_PLACEHOLDER_CHAIN; DRAFT SUPPLEMENT EXISTS BUT NOT PROMOTED",
            "semanticStatus": "NOT_ASSESSED",
            "independentExpectedOutcomeStatus": "NOT_PROVIDED_OR_NOT_VALIDATED",
            "authorizedReviewStatus": "OPEN_REVIEW_REQUIRED"})

    result = {"artifact": "SEQUENCE_NUMBER_AI_CASE_TO_PAYLOAD_REVIEW",
        "scope": "All linked cases and physical route occurrences for three Sequence Number AI BRs; includes approved/candidate catalog records when they share a case ID.",
        "caseCount": len(unique_case_rows), "caseOccurrenceCount": len(occurrences),
        "payloadOccurrenceStates": dict(collections.Counter(summary_counts)),
        "uniqueCaseContentConflicts": sum(row["caseContentConflict"] for row in unique_case_rows.values()),
        "metadataPayloadCorrespondenceOccurrences": sum(len(row["verifiedMetadataPayloadCorrespondence"]) for row in unique_case_rows.values()),
        "semanticMatchesConfirmed": 0, "executionPerformed": False,
        "findings": decision_rows,
        "limitations": ["Repeated approved/candidate records with different record hashes are retained; do not deduplicate conflicts into one case.",
            "Case metadata/payload exact agreement is a structural observation, not semantic or outcome validation.",
            "Approved catalog payload paths missing from the frozen delivery remain explicit gaps; candidate routes may still exist.",
            "No distinct six-digit boundary data suite is present in the AI cases; observed repeated value 000001 is not boundary coverage.",
            "Six individual cases with the same value do not assert equality across linked transactions.",
            "Independent Test package existing chains remain placeholders; the draft supplement is not promoted or executed."]}
    (output_dir / "sequence-number-review-findings.json").write_text(json.dumps(result, indent=2, ensure_ascii=True), encoding="utf-8")
    (output_dir / "test-chain-draft-reference.json").write_text(json.dumps({
        "draftPackage": "test-solution-independent-review/sequence-number-chain-draft-v1/sequence-number-test-chain-package.json",
        "status": "DRAFT_REVIEW_REQUIRED", "officialBaselineModified": False, "coverageCredit": 0,
        "executionCertified": False, "sourceQuoteVerification": "UTF8_EXACT_QUOTES_PASS",
        "baselineSha256": sha256(PACKAGE_DIR / "source-derived-requirement-inventory.json"),
        "chainPackageSha256": sha256(PACKAGE_DIR / "sequence-number-chain-draft-v1/sequence-number-test-chain-package.json")
    }, indent=2), encoding="utf-8")
    hashes = {str(path): sha256(path) for path in [SCRIPT, STARTER / "manifest.json", STARTER / "ai-chain-evidence.jsonl",
        STARTER / "catalog-record-evidence.jsonl", STARTER / "payload-metadata-evidence.jsonl", AUDIT / "file-inventory.jsonl",
        AUDIT / "tc-physical-assessment.jsonl", AUDIT / "artifact-occurrences.jsonl", PACKAGE, SPEC,
        PACKAGE_DIR / "source-derived-requirement-inventory.json",
        PACKAGE_DIR / "sequence-number-chain-draft-v1/sequence-number-independent-chain-draft.json",
        PACKAGE_DIR / "sequence-number-chain-draft-v1/sequence-number-test-chain-package.json"]}
    (output_dir / "input-hashes.json").write_text(json.dumps(hashes, indent=2), encoding="utf-8")
    artifacts = {path.name: sha256(path) for path in output_dir.iterdir() if path.is_file()}
    (output_dir / "manifest.json").write_text(json.dumps({"algorithm": "SHA-256", "artifacts": artifacts}, indent=2), encoding="utf-8")
    print(json.dumps({"cases": len(unique_case_rows), "caseOccurrences": len(occurrences),
        "metadataPayloadCorrespondenceOccurrences": result["metadataPayloadCorrespondenceOccurrences"],
        "caseContentConflicts": result["uniqueCaseContentConflicts"], "semanticMatches": 0}, indent=2))


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--output-dir", type=Path, required=True)
    args = parser.parse_args()
    bind(args.output_dir)


if __name__ == "__main__":
    main()
