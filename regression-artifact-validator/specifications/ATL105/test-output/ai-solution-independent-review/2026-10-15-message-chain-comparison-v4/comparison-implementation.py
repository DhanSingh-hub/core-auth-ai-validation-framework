"""Compare Message BR chains without granting semantic or execution approval."""

import argparse
import collections
import csv
import gzip
import hashlib
import json
import re
from pathlib import Path

import ijson


STOP = set("the a an is are be shall must should of for to and or in on with by as its it per data segment message field required length type".split())
SUBJECTS = {
    "segment_length": r"segment length|length.{0,35}segment",
    "segment_identity": r"segment type|segment (?:id|identity|identifier)",
    "segment_presence": r"required exactly once|segment.{0,60}(?:required|mandatory|present|omit|duplicate)|(?:requires|include).{0,40}segment",
    "field_order": r"field order|ordering|separator|delimiter|field sequence",
    "message_format": r"message format|message type|message identifier",
    "transport": r"tpdu|tcp|transport|framing|header|network byte",
    "encoding": r"encoding|encoded|ascii|binary|ebcdic|byte order",
    "response": r"response|reply",
}


def sha256(path):
    digest = hashlib.sha256()
    with Path(path).open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def read_json(path):
    return json.loads(Path(path).read_text(encoding="utf-8-sig"))


def csv_rows(path):
    with Path(path).open(encoding="utf-8-sig", newline="") as stream:
        return list(csv.DictReader(stream))


def jsonl(path):
    with Path(path).open(encoding="utf-8") as stream:
        for line in stream:
            if line.strip():
                yield json.loads(line)


def array_rows(path, key):
    with Path(path).open("rb") as stream:
        yield from ijson.items(stream, key + ".item", use_float=True)


def refs(row, plural, singular=None):
    values = row.get(plural) or []
    values = [values] if isinstance(values, str) else list(values)
    if singular and row.get(singular):
        values.append(row[singular])
    return set(values)


def features(text, anchors, ai=False):
    normalized = re.sub(r"\s+", " ", text.lower())
    tokens = set(re.findall(r"[a-z][a-z0-9]+", normalized)) - STOP
    segments = set(re.findall(r"\bsegment\s+(\d{3}|dl[12])\b", normalized, re.I))
    elements = set()
    if ai:
        entities = anchors.get("related_entity_ids") or []
        for entity in entities:
            match = re.fullmatch(r"ENT-SEG-(\d{3}|DL[12])", str(entity), re.I)
            if match:
                segments.add(match[1].lower())
            match = re.fullmatch(r"ENT-ELEM-(\d+)", str(entity), re.I)
            if match:
                elements.add(match[1])
        for value in [anchors.get("segment_number"), *(anchors.get("segment_ids") or [])]:
            segments.update(re.findall(r"\b(?:\d{3}|DL[12])\b", str(value or ""), re.I))
    else:
        for anchor in anchors:
            segments.update(re.findall(r"\b(?:\d{3}|DL[12])\b", str(anchor.get("segment") or ""), re.I))
            elements.update(re.findall(r"\b\d+\b", str(anchor.get("element") or "")))
    fixed_types = set(re.findall(r"fixed(?: value)?(?:\s+is|\s+of)?\s*['\"]?(\d{3})\b", normalized)) if "segment type" in normalized else set()
    length_obligation = ("CAPACITY" if re.search(r"maximum length|max(?:imum)? segment length", normalized)
                         else "FORMAT_WIDTH" if "segment length" in normalized and re.search(r"digits?|zero.padded", normalized)
                         else "ENCODED_CONTENT" if "segment length" in normalized and re.search(r"encoded|content|separator", normalized)
                         else None)
    return {"tokens": tokens, "segments": {value.lower() for value in segments},
            "elements": elements,
            "fixedSegmentTypes": fixed_types, "lengthObligation": length_obligation,
            "subjects": {name for name, pattern in SUBJECTS.items() if re.search(pattern, normalized)}}


def comparison(ai_features, test_features):
    shared_segments = ai_features["segments"] & test_features["segments"]
    shared_subjects = ai_features["subjects"] & test_features["subjects"]
    shared_elements = ai_features["elements"] & test_features["elements"]
    union = ai_features["tokens"] | test_features["tokens"]
    similarity = len(ai_features["tokens"] & test_features["tokens"]) / len(union) if union else 0
    segment_conflict = bool(ai_features["segments"] and test_features["segments"] and not shared_segments)
    fixed_type_conflict = bool(ai_features["fixedSegmentTypes"] and test_features["fixedSegmentTypes"]
                              and not ai_features["fixedSegmentTypes"] & test_features["fixedSegmentTypes"])
    length_scope_gap = bool(ai_features["lengthObligation"] and test_features["lengthObligation"]
                           and "CAPACITY" in (ai_features["lengthObligation"], test_features["lengthObligation"])
                           and ai_features["lengthObligation"] != test_features["lengthObligation"])
    lead = bool(not segment_conflict and not fixed_type_conflict and not length_scope_gap and shared_segments and shared_subjects
                and (similarity >= 0.15 or shared_elements))
    return {"sharedSegments": sorted(shared_segments), "sharedSubjects": sorted(shared_subjects),
            "sharedElements": sorted(shared_elements), "tokenJaccard": round(similarity, 4),
            "explicitSegmentConflict": segment_conflict,
            "aiFixedSegmentTypes": sorted(ai_features["fixedSegmentTypes"]),
            "testFixedSegmentTypes": sorted(test_features["fixedSegmentTypes"]),
            "fixedSegmentTypeConflict": fixed_type_conflict,
            "aiLengthObligation": ai_features["lengthObligation"],
            "testLengthObligation": test_features["lengthObligation"],
            "formatVersusCapacityScopeGap": length_scope_gap,
            "contextLead": lead,
            "retrievalScore": round(4 * bool(shared_segments) + 2 * bool(shared_subjects)
                                    + bool(shared_elements) + similarity - 5 * segment_conflict, 4)}


def artifact_chain(package, br_id):
    brs = [row for row in package["businessRequirements"] if row["id"] == br_id]
    if len(brs) != 1:
        raise ValueError("Expected one independent package BR: " + br_id)
    scenarios = [row for row in package["testScenarios"] if br_id in refs(row, "requirementIds")]
    scenario_ids = {row["id"] for row in scenarios}
    cases = [row for row in package["testCases"] if scenario_ids & refs(row, "scenarioIds")]
    case_ids = {row["id"] for row in cases}
    data = [row for row in package["testData"] if case_ids & refs(row, "testCaseIds")]
    data_by_id = {row["id"]: row for row in package["testData"]}
    missing_forward_ids = sorted({identifier for row in cases for identifier in refs(row, "testDataIds")
                                  if identifier not in data_by_id})
    placeholders = [row["id"] for row in [*scenarios, *cases, *data] if "-REVIEW-" in row["id"]]
    executable = [row["id"] for row in data if row.get("readiness") == "EXECUTABLE"
                  and row.get("payload") is not None]
    return {"br": brs[0], "scenarios": scenarios, "cases": cases, "data": data,
            "structuralChainPresent": bool(scenarios and cases and data and not missing_forward_ids),
            "placeholderIds": placeholders, "missingDeclaredDataIds": missing_forward_ids,
            "executablePayloadDataIds": executable,
            "chainStatus": "REVIEW_PLACEHOLDER_CHAIN" if placeholders else "DRAFT_CHAIN_REQUIRES_REVIEW"}


def test_chain(package, rule_id):
    return artifact_chain(package, "BR-RULE-" + rule_id)


def anchor_signature(anchor):
    return tuple(str(anchor.get(key) or "") for key in ("specification", "version", "section", "segment", "element", "rule"))


def write_csv(path, rows, fields):
    with path.open("w", encoding="utf-8", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=fields, extrasaction="ignore")
        writer.writeheader()
        for row in rows:
            writer.writerow({key: json.dumps(value, ensure_ascii=True) if isinstance(value, (list, dict)) else value
                             for key, value in row.items() if key in fields})


def write_jsonl(path, rows):
    opener = gzip.open(path, "wt", encoding="utf-8", newline="\n") if path.suffix == ".gz" else path.open("w", encoding="utf-8", newline="\n")
    with opener as stream:
        for row in rows:
            stream.write(json.dumps(row, ensure_ascii=True, sort_keys=True) + "\n")


def physical_observations(value, location=""):
    result = []
    if isinstance(value, dict):
        for name, child in value.items():
            child_path = location + "/" + name
            if re.sub(r"[^a-z]", "", name.lower()) in {"segmenttype", "segmentlength", "numsegments", "numberofsegments"} and not isinstance(child, (dict, list)):
                result.append({"path": child_path, "value": child})
            result.extend(physical_observations(child, child_path))
    elif isinstance(value, list):
        for index, child in enumerate(value):
            result.extend(physical_observations(child, location + "/" + str(index)))
    return result


def run(root, review, package_path, output):
    if output.exists():
        raise ValueError("Refusing to overwrite output directory")
    category_path = review / "2026-10-15-ai-test-br-category-groups-v1/categorized-business-requirements.csv"
    pairs_path = review / "2026-10-14-ai-test-br-crosswalk-v3/ai-test-br-candidate-pairs.csv"
    audit_dir = review / "2026-10-14-delivery-wide-assessment-v6"
    summary_path = audit_dir / "summary.json"
    inventory_path = audit_dir / "file-inventory.jsonl"
    assessment_path = audit_dir / "br-assessment.jsonl"
    physical_path = audit_dir / "tc-physical-assessment.jsonl"
    requirement_path = root / "step5_requirements/approved/requirement_catalog.json"
    scenario_path = root / "scenarios/candidates/candidate_scenarios.json"
    case_paths = [root / folder / "candidates/test_case_candidates.json" for folder in
                  ("test_generation/test_generation", "test_data_10_oct/test_data_10_oct")]
    inputs = [category_path, pairs_path, summary_path, inventory_path, assessment_path,
              physical_path, package_path, requirement_path, scenario_path, *case_paths, Path(__file__)]
    hashes = {str(path.resolve()): sha256(path) for path in inputs}
    summary = read_json(summary_path)
    if sha256(inventory_path) != summary["fileInventorySha256"]:
        raise ValueError("Frozen inventory hash differs")
    for path in (assessment_path, physical_path):
        if sha256(path) != summary["outputHashes"][path.name]:
            raise ValueError("Audit ledger hash differs: " + path.name)
    inventory = {row["path"]: row for row in jsonl(inventory_path)}
    for path in (requirement_path, scenario_path, *case_paths):
        relative = path.relative_to(root).as_posix()
        if sha256(path) != inventory[relative]["sha256"]:
            raise ValueError("Source catalog differs from frozen delivery: " + relative)
    package = read_json(package_path)
    if package["manifest"].get("authority") != "INDEPENDENT_TEST_SOLUTION" or package["manifest"].get("aiArtifactsIncluded") is not False:
        raise ValueError("Independent Test package required")
    categories = csv_rows(category_path)
    ai_categories = {row["brId"]: row for row in categories if row["recordType"] == "AI_BR"}
    test_categories = {row["brId"]: row for row in categories if row["recordType"] == "TEST_BR"}
    ai_message = {identifier for identifier, row in ai_categories.items() if row["sharedCategoryLabel"] == "Message"}
    test_message = {identifier for identifier, row in test_categories.items() if row["sharedCategoryLabel"] == "Message"}
    ai_rows = {row["id"]: row for row in array_rows(requirement_path, "requirements")}
    if set(ai_rows) != set(ai_categories):
        raise ValueError("AI category population differs from catalog")
    chains = {identifier: test_chain(package, identifier) for identifier in test_categories}
    if len(chains) != package["summary"]["independentRuleDenominator"]:
        raise ValueError("Test category population differs from package")
    authored_by_anchor = collections.defaultdict(set)
    for br in package["businessRequirements"]:
        if not br["id"].startswith("BR-RULE-"):
            for anchor in br.get("sourceAnchors") or []:
                authored_by_anchor[anchor_signature(anchor)].add(br["id"])
    for chain in chains.values():
        alternatives = sorted({identifier for anchor in chain["br"].get("sourceAnchors") or []
                               for identifier in authored_by_anchor[anchor_signature(anchor)]})
        chain["alternativeAuthoredChains"] = [artifact_chain(package, identifier) for identifier in alternatives]
    assessments = {row["id"]: row for row in jsonl(assessment_path)}
    existing = {(row["aiBrId"], row["testBrId"]): row for row in csv_rows(pairs_path)}
    ai_features = {identifier: features(row["statement"], row, True) for identifier, row in ai_rows.items()}
    test_features = {identifier: features(chain["br"]["title"], chain["br"].get("sourceAnchors") or [])
                     for identifier, chain in chains.items()}
    pair_rows, matrix = [], []
    for ai_id, ai_feature in ai_features.items():
        targets = test_categories if ai_id in ai_message else test_message
        for test_id in targets:
            result = comparison(ai_feature, test_features[test_id])
            in_crosswalk = (ai_id, test_id) in existing
            row = {"aiBrId": ai_id, "testBrId": test_id,
                   "aiCategory": ai_categories[ai_id]["sharedCategoryLabel"],
                   "testCategory": test_categories[test_id]["sharedCategoryLabel"],
                   "aiStatement": ai_rows[ai_id]["statement"], "testTitle": chains[test_id]["br"]["title"],
                   "basis": "EXISTING_V3_ANCHOR_CANDIDATE" if in_crosswalk else "ADDITIONAL_CONTEXT_REVIEW_LEAD",
                   "crosswalkDisposition": existing.get((ai_id, test_id), {}).get("candidateDisposition"),
                   **result, "aiPhysicalCompleteCaseIds": assessments[ai_id]["physicalCompleteCaseIds"],
                   "testChainStatus": chains[test_id]["chainStatus"],
                   "testPlaceholderIds": chains[test_id]["placeholderIds"],
                   "testAlternativeAuthoredBrIds": [alternative["br"]["id"] for alternative in chains[test_id]["alternativeAuthoredChains"]],
                   "testAlternativeExecutablePayloadDataIds": sorted({identifier for alternative in chains[test_id]["alternativeAuthoredChains"] for identifier in alternative["executablePayloadDataIds"]}),
                   "brEquivalence": "REVIEW_REQUIRED", "scenarioEquivalence": "NOT_ASSESSED",
                   "caseOracleEquivalence": "NOT_ASSESSED", "testDataEquivalence": "NOT_ASSESSED",
                   "semanticApproved": False, "hostExecuted": False}
            if ai_id in ai_message and test_id in test_message:
                matrix.append({**row, "retrievalOnly": True})
            if in_crosswalk or result["contextLead"]:
                pair_rows.append(row)
    relevant_ai = ai_message | {row["aiBrId"] for row in pair_rows}
    relevant_cases = {identifier for ai_id in relevant_ai for identifier in assessments[ai_id]["testCaseIds"]}
    scenarios = {row["id"]: row for row in array_rows(scenario_path, "scenarios")
                 if row["id"] in {identifier for ai_id in relevant_ai for identifier in assessments[ai_id]["scenarioIds"]}}
    cases = collections.defaultdict(list)
    for path in case_paths:
        for row in array_rows(path, "test_cases"):
            if row["id"] in relevant_cases:
                cases[row["id"]].append({"catalog": path.relative_to(root).as_posix(), "record": row})
    physical = collections.defaultdict(list)
    for row in jsonl(physical_path):
        if row["id"] in relevant_cases:
            physical[row["id"]].append(row)
    ai_chains = []
    verified_physical = {}
    for ai_id in sorted(relevant_ai):
        assessment = assessments[ai_id]
        case_rows = []
        for case_id in assessment["testCaseIds"]:
            routes = physical[case_id]
            paths = sorted({path for route in routes for path in route["paths"]})
            evidence = []
            for relative in paths:
                payload_path = root / relative
                metadata_path = payload_path.with_suffix(".meta.json")
                meta_relative = metadata_path.relative_to(root).as_posix()
                pair_verified = True
                for selected, key in ((payload_path, relative), (metadata_path, meta_relative)):
                    expected = inventory.get(key)
                    if not expected or not selected.is_file() or sha256(selected) != expected["sha256"]:
                        pair_verified = False
                    if expected and selected.is_file() and sha256(selected) != expected["sha256"]:
                        raise ValueError("Physical evidence differs from frozen inventory: " + key)
                verified_physical[relative] = pair_verified
                evidence.append({"payload": relative, "metadata": meta_relative,
                                 "frozenHashesVerified": pair_verified,
                                 "messageStructureObservations": physical_observations(read_json(payload_path)) if pair_verified else [],
                                 "payloadSha256": inventory.get(relative, {}).get("sha256"),
                                 "metadataSha256": inventory.get(meta_relative, {}).get("sha256")})
            case_rows.append({"testCaseId": case_id, "catalogOccurrences": cases[case_id],
                              "physicalRoutes": routes, "physicalEvidence": evidence})
        ai_chains.append({"aiBrId": ai_id, "category": ai_categories[ai_id]["sharedCategoryLabel"],
                          "br": ai_rows[ai_id], "assessment": assessment,
                          "scenarios": [scenarios[identifier] for identifier in assessment["scenarioIds"]],
                          "cases": case_rows})
    for path, digest in hashes.items():
        if sha256(path) != digest:
            raise ValueError("Input changed during comparison: " + path)
    pair_rows.sort(key=lambda row: (row["basis"] != "EXISTING_V3_ANCHOR_CANDIDATE", -row["retrievalScore"], row["testBrId"], row["aiBrId"]))
    counts = {"aiMessageBrs": len(ai_message), "testMessageBrs": len(test_message),
              "messageToMessageMatrixRows": len(matrix),
              "existingCandidatePairsTouchingMessage": sum(row["basis"] == "EXISTING_V3_ANCHOR_CANDIDATE" for row in pair_rows),
              "additionalContextReviewLeads": sum(row["basis"] == "ADDITIONAL_CONTEXT_REVIEW_LEAD" for row in pair_rows),
              "testMessageBrsWithExistingCandidates": len({row["testBrId"] for row in pair_rows if row["testBrId"] in test_message and row["basis"] == "EXISTING_V3_ANCHOR_CANDIDATE"}),
              "testMessageBrsWithAnyReviewLead": len({row["testBrId"] for row in pair_rows if row["testBrId"] in test_message}),
              "aiMessageBrsWithAnyReviewLead": len({row["aiBrId"] for row in pair_rows if row["aiBrId"] in ai_message}),
              "aiMessageBrsWithCleanPhysicalChain": sum(bool(assessments[identifier]["physicalCompleteCaseIds"]) for identifier in ai_message),
              "testMessageBrsWithStructuralChain": sum(chains[identifier]["structuralChainPresent"] for identifier in test_message),
              "testMessageBrsWithPlaceholders": sum(bool(chains[identifier]["placeholderIds"]) for identifier in test_message),
              "testMessageBrsWithExecutablePayloadData": sum(bool(chains[identifier]["executablePayloadDataIds"]) for identifier in test_message),
              "testMessageBrsWithAlternativeExactAnchorChains": sum(bool(chains[identifier]["alternativeAuthoredChains"]) for identifier in test_message),
              "testMessageBrsWithAlternativeExecutablePayloadData": sum(any(alternative["executablePayloadDataIds"] for alternative in chains[identifier]["alternativeAuthoredChains"]) for identifier in test_message),
              "candidatePairsWithFixedTypeConflict": sum(row["fixedSegmentTypeConflict"] for row in pair_rows),
              "candidatePairsWithFormatCapacityGap": sum(row["formatVersusCapacityScopeGap"] for row in pair_rows),
              "physicalPayloadMetadataPairsHashVerified": sum(verified_physical.values()),
              "physicalPayloadMetadataPairsMissingOrIncomplete": sum(not value for value in verified_physical.values()),
              "semanticApprovedPairs": 0, "certifiedCompleteChainMatches": 0}
    output.mkdir(parents=True)
    pair_fields = list(pair_rows[0]) if pair_rows else ["aiBrId", "testBrId"]
    write_csv(output / "message-candidate-pairs.csv", pair_rows, pair_fields)
    write_csv(output / "message-to-message-all-pairs.csv", matrix, [*pair_fields, "retrievalOnly"])
    write_jsonl(output / "ai-chain-evidence.jsonl.gz", ai_chains)
    test_scope = test_message | {row["testBrId"] for row in pair_rows}
    write_jsonl(output / "test-chain-evidence.jsonl", ({"testBrId": identifier,
                "category": test_categories[identifier]["sharedCategoryLabel"], **chains[identifier]} for identifier in sorted(test_scope)))
    for population, name, side in ((ai_message, "ai-message-br-review-register.csv", "aiBrId"),
                                   (test_message, "test-message-br-review-register.csv", "testBrId")):
        rows = []
        for identifier in sorted(population):
            candidates = [row for row in pair_rows if row[side] == identifier]
            rows.append({"brId": identifier, "title": ai_rows[identifier]["statement"] if side == "aiBrId" else chains[identifier]["br"]["title"],
                         "existingCandidates": [row["testBrId" if side == "aiBrId" else "aiBrId"] for row in candidates if row["basis"] == "EXISTING_V3_ANCHOR_CANDIDATE"],
                         "additionalReviewLeads": [row["testBrId" if side == "aiBrId" else "aiBrId"] for row in candidates if row["basis"] == "ADDITIONAL_CONTEXT_REVIEW_LEAD"],
                         "reviewStatus": "PENDING_AUTHORIZED_REVIEW", "semanticApproved": False})
        write_csv(output / name, rows, list(rows[0]))
    (output / "summary.json").write_text(json.dumps({"population": counts, "inputSha256": hashes,
        "scenarioCatalog": "scenarios/candidates/candidate_scenarios.json",
        "aiScenarioApprovalRequired": False,
        "policy": "Candidate retrieval only. AI scenario producer approval is not required for crosswalk inclusion; scenario links remain source evidence, not semantic validation. Exhaustive 97x243 Message matrix; cross-category search included. Shared subject plus explicit segment context generates leads, not matches. Placeholder Test chains cannot establish complete-chain equivalence."}, indent=2), encoding="utf-8")
    report = "# Message BR and Chain Comparison\n\n"
    report += "| Measure | Count |\n|---|---:|\n" + "".join(f"| {key} | {value:,} |\n" for key, value in counts.items())
    report += "\n## Interpretation\n\nAI scenario producer approval is not required for crosswalk inclusion; the candidate scenario catalog is used, and scenario links remain source evidence rather than semantic validation. Existing anchor links and additional context leads are separate review queues, not semantic matches. Retrieval scores only prioritize review; they are not coverage percentages or confidence. All Message BRs are retained, including rows with no lead. AI and Test complete chains are recorded separately with IDs and original records. Physical AI requests/metadata are checked against the frozen inventory. Test placeholders, unset case oracles and missing executable payloads block complete-chain certification. No host execution or SME approval is inferred.\n\n"
    report += "The maximum reported here is reviewable candidate reach, not a maximum approved match count. Compare the BR obligations, transaction/segment applicability, scenario intent, independent expected outcome and data before adjudicating any pair. Cross-category links are retained because categories alone do not prove or disprove equivalence.\n\n"
    report += "Known example: AI :1907 concerns four-digit Segment Length. Some anchor candidates instead concern maximum segment capacity; shared element 84 alone does not establish the same obligation. Its SC-7281 chain has clean cases TC-009116 through TC-009119; TC-009120 is not credited by the clean v6 route set. Test counterparts are review-placeholder chains.\n"
    report += "\nFixed Segment Type contradictions and format-versus-capacity gaps are flagged on existing candidates; new leads with these issues are excluded. Missing/incomplete physical pairs are retained as gaps, not credited as verified. Actual scalar structure values are observations only, not wire-length or independent expected-outcome validation.\n"
    report += "\nAlternative authored Test BR chains are included only when the full source anchor (specification, version, section, segment, element and rule) is identical. The zero executable-data count applies to primary BR-RULE chains only; alternative fixture availability is reported separately. An identical anchor is provenance/context evidence, not semantic approval or proof that the alternative completely implements the catalogued obligation.\n"
    (output / "REPORT.md").write_text(report, encoding="utf-8")
    (output / "comparison-implementation.py").write_bytes(Path(__file__).read_bytes())
    manifest = {path.name: sha256(path) for path in output.iterdir() if path.is_file()}
    (output / "manifest.json").write_text(json.dumps({"algorithm": "SHA-256", "artifacts": manifest}, indent=2), encoding="utf-8")
    print(json.dumps(counts, indent=2))


def self_test():
    ai = features("Segment Length is four digits for Segment 130", {"related_entity_ids": ["ENT-ELEM-84", "ENT-SEG-130"]}, True)
    same = features("Segment Length is 4 digits", [{"segment": "130", "element": "84"}])
    other = features("Segment Length is 4 digits", [{"segment": "100", "element": "84"}])
    assert comparison(ai, same)["contextLead"]
    assert not comparison(ai, other)["contextLead"]
    assert comparison(ai, other)["explicitSegmentConflict"]
    identity_ai = features("Segment Type fixed value is 119", {"related_entity_ids": ["ENT-SEG-110", "ENT-ELEM-85"]}, True)
    identity_test = features("Segment Type is fixed value 110", [{"segment": "110", "element": "85"}])
    assert comparison(identity_ai, identity_test)["fixedSegmentTypeConflict"]
    assert not comparison(identity_ai, identity_test)["contextLead"]
    short_identity = features("Segment Type fixed 155; no source listed", [{"segment": "155", "element": "85"}])
    assert comparison(identity_ai, short_identity)["fixedSegmentTypeConflict"]
    capacity = features("Segment 130 maximum length is 3043", [{"segment": "130", "element": "84"}])
    assert comparison(ai, capacity)["formatVersusCapacityScopeGap"]
    assert not comparison(ai, capacity)["contextLead"]
    pkg = {"businessRequirements": [{"id": "BR-RULE-SEG100-R-001"}],
           "testScenarios": [{"id": "TS-REVIEW-1", "requirementIds": ["BR-RULE-SEG100-R-001"]}],
           "testCases": [{"id": "TC-REVIEW-1", "scenarioIds": ["TS-REVIEW-1"]}],
           "testData": [{"id": "TD-REVIEW-1", "testCaseIds": ["TC-REVIEW-1"], "readiness": "REVIEW_REQUIRED"}]}
    chain = test_chain(pkg, "SEG100-R-001")
    assert chain["structuralChainPresent"] and chain["placeholderIds"] and not chain["executablePayloadDataIds"]
    assert artifact_chain(pkg, "BR-RULE-SEG100-R-001")["structuralChainPresent"]
    assert anchor_signature({"segment": "100", "rule": "count"}) != anchor_signature({"segment": "100", "rule": "order"})
    print("PASS: context leads require shared segment/subject; disjoint scope rejected; placeholders never become executable")


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--delivery-root", type=Path)
    parser.add_argument("--review-root", type=Path)
    parser.add_argument("--test-package", type=Path)
    parser.add_argument("--output-dir", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
    elif all((args.delivery_root, args.review_root, args.test_package, args.output_dir)):
        run(args.delivery_root, args.review_root, args.test_package, args.output_dir)
    else:
        parser.error("Supply delivery root, review root, independent Test package and new output directory")


if __name__ == "__main__":
    main()