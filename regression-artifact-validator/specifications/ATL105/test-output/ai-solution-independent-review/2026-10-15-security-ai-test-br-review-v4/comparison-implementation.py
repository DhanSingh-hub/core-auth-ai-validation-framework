"""Build an evidence-linked Security category BR crossmatch and chain review."""

import argparse
import collections
import csv
import hashlib
import json
from pathlib import Path


DIRECT_RELATIONS = {
    ("389", "SEG100-R-043"): ("PARTIAL_FIELD_AND_CONTEXT_OVERLAP", "AI states PIN-block followed by KSN; Test covers DUKPT PIN-block representation when PIN applies. KSN ordering and exact field boundary need source review."),
    ("1701", "SEG100-R-035"): ("RELATED_DIFFERENT_OBLIGATION", "AI says Expiration Date corresponds to PAN/Token in Account Number; Test requires explicit tokenized/TransArmor account representation."),
    ("1935", "SEG116-R-003"): ("PARTIAL_SEGMENT_IDENTITY_OVERLAP", "AI says Segment Type identifies TransArmor key/key-ID content; Test requires fixed 116 and Required in the load request. Test adds obligation/scope."),
    ("2343", "SEG123-R-010"): ("PARTIAL_FIELD_PRESENCE_AND_FORMAT_OVERLAP", "AI says TAVV appears in Segment 123; Test adds 28-byte/base64 and request-only constraints."),
    ("2343", "SEG123-R-011"): ("COMPOSITE_CONTEXT_OVERLAP", "AI identifies the TAVV field; Test distinguishes TAVV from UCAF/AAV when both cryptograms are present."),
    ("2346", "SEG123-R-012"): ("PARTIAL_COMPOSITE_WIDTH_OVERLAP", "AI identifies TAVV Result Code; Test bundles widths for Token Requestor ID, SafeKey Response, and TAVV Result Code."),
    ("2592", "SEG123-R-010"): ("PARTIAL_FIELD_AND_FORMAT_OVERLAP", "AI directs DSRP cryptograms to TAVV instead of UCAF; Test constrains TAVV encoding and request-only placement."),
    ("2592", "SEG123-R-011"): ("COMPOSITE_CONTEXT_OVERLAP", "AI directs DSRP TAVV instead of UCAF; Test separates DSRP TAVV from 3DS AAV when both appear."),
    ("3131", "SEG123-R-010"): ("PARTIAL_FIELD_AND_FORMAT_OVERLAP", "AI directs Token/DSRP cryptograms to TAVV; Test adds field length, alphabet, and request-only placement."),
    ("3131", "SEG123-R-011"): ("COMPOSITE_CONTEXT_OVERLAP", "AI directs Token/DSRP cryptograms to TAVV; Test adds simultaneous TAVV/UCAF mapping constraints."),
}


def sha256(path):
    digest = hashlib.sha256()
    with Path(path).open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def jsonl(path):
    with Path(path).open(encoding="utf-8") as stream:
        for line in stream:
            if line.strip():
                yield json.loads(line)


def csv_rows(path):
    csv.field_size_limit(100_000_000)
    with Path(path).open(encoding="utf-8-sig", newline="") as stream:
        return list(csv.DictReader(stream))


def read_json(path):
    return json.loads(Path(path).read_text(encoding="utf-8-sig"))


def refs(row, field):
    value = row.get(field) or []
    return {value} if isinstance(value, str) else set(value)


def list_field(value):
    if not value:
        return []
    try:
        parsed = json.loads(value)
        return parsed if isinstance(parsed, list) else [parsed]
    except (TypeError, json.JSONDecodeError):
        return [part for part in str(value).split(";") if part]


def anchor_signature(anchor):
    return tuple(str(anchor.get(key) or "") for key in ("specification", "version", "section", "segment", "element", "rule"))


def chain_for(package, br):
    br_id = br["id"]
    scenarios = [row for row in package["testScenarios"] if br_id in refs(row, "requirementIds")]
    scenario_ids = {row["id"] for row in scenarios}
    cases = [row for row in package["testCases"] if scenario_ids & refs(row, "scenarioIds")]
    case_ids = {row["id"] for row in cases}
    data = [row for row in package["testData"] if case_ids & refs(row, "testCaseIds")]
    return {"br": br, "scenarios": scenarios, "cases": cases, "data": data,
            "placeholderIds": [row["id"] for row in [*scenarios, *cases, *data] if "-REVIEW-" in row["id"]],
            "executablePayloadDataIds": [row["id"] for row in data
                                          if row.get("readiness") == "EXECUTABLE" and row.get("payload") is not None]}


def alternative_chains(package, rule):
    target_anchors = {anchor_signature(anchor) for anchor in rule.get("sourceAnchors") or []}
    alternatives = []
    if not target_anchors:
        return alternatives
    for br in package["businessRequirements"]:
        if br["id"].startswith("BR-RULE-"):
            continue
        anchors = {anchor_signature(anchor) for anchor in br.get("sourceAnchors") or []}
        if target_anchors & anchors:
            alternatives.append(chain_for(package, br))
    return alternatives


def write_csv(path, rows, fields=None):
    rows = list(rows)
    if fields is None:
        fields = list(rows[0]) if rows else []
    with Path(path).open("w", encoding="utf-8", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=fields, extrasaction="ignore")
        writer.writeheader()
        writer.writerows(rows)


def main_review_fields(row):
    return {key: row.get(key) for key in (
        "aiBrId", "aiStatement", "aiExpectedBehavior", "aiSourcePage", "aiSourceRuleId",
        "aiSegments", "aiElements", "aiScenarioIds", "aiScenarioCount", "aiCaseIds",
        "aiCaseCount", "aiPhysicalCompleteCaseIds", "chainStatus", "v6ExactAnchorTestBrCandidates",
        "rankedTestBrCandidates", "rankedCandidateCount", "reviewDisposition", "semanticMatchStatus")}


def run(review, package_path, runner_path, output):
    review, package_path, runner_path, output = map(Path, (review, package_path, runner_path, output))
    if output.exists():
        raise ValueError("Refusing to overwrite output directory")
    category_path = review / "2026-10-15-ai-test-br-category-groups-v1/categorized-business-requirements.csv"
    crosswalk_path = review / "2026-10-14-ai-test-br-crosswalk-v3/ai-test-br-candidate-pairs.csv"
    ranked_path = review / "2026-10-15-all-ai-br-chain-test-br-review-v1/ai-test-br-ranked-review-leads.csv"
    ai_register_path = review / "2026-10-15-all-ai-br-chain-test-br-review-v1/all-ai-br-review-register.csv"
    complete_unmatched_path = review / "2026-10-15-complete-chain-unmatched-ai-br-v1/complete-chain-ai-brs-without-test-br-candidates.csv"
    unmatched_test_path = review / "2026-10-15-unmatched-ai-test-br-category-v1/test-brs-without-ai-br-candidates.csv"
    test_index_path = review / "2026-10-14-ai-test-br-crosswalk-v3/test-br-ai-br-index.csv"
    source_files = [category_path, crosswalk_path, ranked_path, ai_register_path, complete_unmatched_path,
                    unmatched_test_path, test_index_path, package_path, runner_path]
    source_hashes = {path.name: sha256(path) for path in source_files}

    categories = csv_rows(category_path)
    ai_categories = {row["brId"]: row for row in categories if row["recordType"] == "AI_BR"}
    test_categories = {row["brId"]: row for row in categories if row["recordType"] == "TEST_BR"}
    ai_security = {identifier for identifier, row in ai_categories.items() if row["sharedCategoryLabel"] == "Security"}
    test_security = {identifier for identifier, row in test_categories.items() if row["sharedCategoryLabel"] == "Security"}
    all_ai = {row["aiBrId"]: row for row in csv_rows(ai_register_path)}
    ai_unmatched_complete = {row["AI_BR_ID"] for row in csv_rows(complete_unmatched_path)} & ai_security
    test_candidates = {row["testBrId"]: row for row in csv_rows(test_index_path)}
    test_security_unmatched = {identifier for identifier in test_security
                               if identifier not in test_candidates}
    candidates = csv_rows(crosswalk_path)
    touching = [row for row in candidates if row["aiBrId"] in ai_security or row["testBrId"] in test_security]
    same_category = [row for row in touching if row["aiBrId"] in ai_security and row["testBrId"] in test_security]
    ranked = [row for row in csv_rows(ranked_path) if row["aiBrId"] in ai_security]
    from_ai = ranked
    ranked_security_to_security = [row for row in ranked if row["testBrId"] in test_security]
    runner = {row["testBrId"]: row for row in jsonl(runner_path)}
    package = read_json(package_path)
    test_rules = {row["id"][len("BR-RULE-"):]: row for row in package["businessRequirements"]
                  if row["id"].startswith("BR-RULE-")}
    test_security_chains = {identifier: chain_for(package, test_rules[identifier])
                            for identifier in test_security if identifier in test_rules}
    alternatives = {identifier: alternative_chains(package, test_rules[identifier])
                    for identifier in test_security if identifier in test_rules}
    if len(ai_security) != 387 or len(test_security) != 23 or len(ai_unmatched_complete) != 247 or len(test_security_unmatched) != 8:
        raise ValueError("Security population counts do not match the published category populations")

    output.mkdir(parents=True)
    ai_rows = []
    for identifier in sorted(ai_security):
        record = dict(main_review_fields(all_ai[identifier]))
        record["sharedCategoryLabel"] = ai_categories[identifier]["sharedCategoryLabel"]
        record["v3NoCandidate"] = not any(row["aiBrId"] == identifier for row in candidates)
        record["completePhysicalChainNoCandidate"] = identifier in ai_unmatched_complete
        record["retrievalLeadCount"] = sum(row["aiBrId"] == identifier for row in ranked)
        record["semanticStatus"] = "NOT_ASSESSED"
        ai_rows.append(record)
    write_csv(output / "ai-security-br-review-register.csv", ai_rows)

    test_rows = []
    for identifier in sorted(test_security):
        category_row = test_categories[identifier]
        rule = test_rules.get(identifier)
        chain = test_security_chains.get(identifier, {"br": {}, "scenarios": [], "cases": [], "data": [], "placeholderIds": [], "executablePayloadDataIds": []})
        execution = runner.get(identifier, {})
        test_rows.append({"testBrId": identifier, "title": category_row["titleOrStatement"],
            "categoryReviewStatus": category_row["categoryReviewStatus"],
            "hasV3AiCandidate": identifier in test_candidates,
            "aiCandidateIds": category_row["candidateBrIds"],
            "packageBrId": rule.get("id") if rule else None,
            "packageBrStatus": rule.get("status") if rule else None,
            "scenarioCount": len(chain["scenarios"]), "caseCount": len(chain["cases"]), "testDataCount": len(chain["data"]),
            "placeholderIds": ";".join(chain["placeholderIds"]),
            "alternativeExactAnchorBrIds": ";".join(alt["br"]["id"] for alt in alternatives.get(identifier, [])),
            "alternativeTestDataCount": sum(len(alt["data"]) for alt in alternatives.get(identifier, [])),
            "alternativeTestDataReadiness": ";".join(sorted({str(data.get("readiness") or "UNSET")
                for alt in alternatives.get(identifier, []) for data in alt["data"]})),
            "executablePayloadDataIds": ";".join(chain["executablePayloadDataIds"]),
            "runnerImplementation": execution.get("implementation", "MISSING_FROM_601_RULE_BASELINE"),
            "runnerRunState": execution.get("runState", "NO_RESULT"),
            "runnerDistinctDecisiveCases": execution.get("distinctDecisiveCaseCount", 0),
            "runnerOutcomeRows": json.dumps(execution.get("outcomeRowCounts", {}), sort_keys=True),
            "semanticStatus": "NOT_ASSESSED", "hostExecution": "NOT_EXECUTED"})
    write_csv(output / "test-security-br-review-register.csv", test_rows)

    pairs = []
    existing_keys = {(row["aiBrId"], row["testBrId"]) for row in touching}
    ranked_lookup = {(row["aiBrId"], row["testBrId"]): row for row in ranked}
    for row in touching:
        ai_id, test_id = row["aiBrId"], row["testBrId"]
        test_rule = test_rules.get(test_id, {})
        relation, rationale = DIRECT_RELATIONS.get((ai_id.split(":")[-1], test_id),
            ("OPEN_PAIR_REVIEW", "Source-anchor candidate only; compare full security mechanism, applicability, field identity and lifecycle context."))
        run = runner.get(test_id, {})
        pairs.append({"basis": "V3_SOURCE_ANCHOR_CANDIDATE", "preliminaryRelation": relation,
            "preliminaryRationale": rationale, "semanticDecision": "OPEN_REVIEW_REQUIRED",
            "aiBrId": ai_id, "aiCategory": ai_categories[ai_id]["sharedCategoryLabel"],
            "aiStatement": row["aiBrStatement"], "aiSourcePage": row.get("aiSourcePage"),
            "aiSegment": row.get("aiCandidateSegments"), "aiElements": row.get("aiCandidateElements"),
            "testBrId": test_id, "testCategory": test_categories[test_id]["sharedCategoryLabel"],
            "testTitle": row["testBrTitle"], "testCandidateDisposition": row["candidateDisposition"],
            "testBrStatus": test_rule.get("status"),
            "testPrimaryPlaceholderCount": len(test_security_chains.get(test_id, {}).get("placeholderIds", [])),
            "testAlternativeExactAnchorBrIds": ";".join(alt["br"]["id"] for alt in alternatives.get(test_id, [])),
            "testAlternativeDataReadiness": ";".join(sorted({str(data.get("readiness") or "UNSET")
                for alt in alternatives.get(test_id, []) for data in alt["data"]})),
            "runnerImplementation": run.get("implementation", "MISSING_FROM_601_RULE_BASELINE"),
            "runnerRunState": run.get("runState", "NO_RESULT"),
            "runnerDistinctDecisiveCases": run.get("distinctDecisiveCaseCount", 0),
            "runnerOutcomeRowsOnAiPayloads": json.dumps(run.get("outcomeRowCounts", {}), sort_keys=True),
            "hostExecution": "NOT_EXECUTED"})
    for (ai_id, test_id), row in ranked_lookup.items():
        if ai_id not in ai_security or test_id not in test_security or (ai_id, test_id) in existing_keys:
            continue
        pairs.append({"basis": "RANKED_RETRIEVAL_LEAD_NOT_ANCHOR_MATCH", "preliminaryRelation": "UNREVIEWED_RETRIEVAL_LEAD",
            "preliminaryRationale": ";".join(json.loads(row["retrievalReasons"])), "semanticDecision": "OPEN_REVIEW_REQUIRED",
            "aiBrId": ai_id, "aiCategory": "Security", "aiStatement": row["aiStatement"],
            "aiSourcePage": row["aiSourcePage"], "aiSegment": row["sharedSegments"], "aiElements": row["sharedElements"],
            "testBrId": test_id, "testCategory": "Security", "testTitle": row["testBrTitle"],
            "testCandidateDisposition": "RANKED_RETRIEVAL_ONLY", "testBrStatus": test_rules.get(test_id, {}).get("status"),
            "retrievalRank": row["candidateRank"], "retrievalScore": row["retrievalScore"],
            "testPrimaryPlaceholderCount": len(test_security_chains.get(test_id, {}).get("placeholderIds", [])),
            "runnerImplementation": runner.get(test_id, {}).get("implementation", "MISSING_FROM_601_RULE_BASELINE"),
            "runnerRunState": runner.get(test_id, {}).get("runState", "NO_RESULT"),
            "runnerDistinctDecisiveCases": runner.get(test_id, {}).get("distinctDecisiveCaseCount", 0),
            "runnerOutcomeRowsOnAiPayloads": json.dumps(runner.get(test_id, {}).get("outcomeRowCounts", {}), sort_keys=True),
            "hostExecution": "NOT_EXECUTED"})
    pairs.sort(key=lambda row: (row["basis"] != "V3_SOURCE_ANCHOR_CANDIDATE",
                                int(row.get("retrievalRank") or 0), row["aiBrId"], row["testBrId"]))
    write_csv(output / "security-pair-review.csv", pairs)

    same_category = [row for row in touching if row["aiBrId"] in ai_security and row["testBrId"] in test_security]
    relation_counts = collections.Counter(DIRECT_RELATIONS.get((row["aiBrId"].split(":")[-1], row["testBrId"]),
        ("OPEN_PAIR_REVIEW", ""))[0] for row in same_category)
    runner_security = [runner[identifier] for identifier in test_security if identifier in runner]
    counts = {"aiSecurityBrs": len(ai_security), "testSecurityBrs": len(test_security),
        "completeChainAiSecurityBrsWithoutV3Candidate": len(ai_unmatched_complete),
        "testSecurityBrsWithoutV3AiCandidate": len(test_security_unmatched),
        "existingCandidatePairsTouchingSecurity": len(touching), "sameCategorySecurityPairs": len(same_category),
        "aiSecurityBrsWithV3Candidate": len({row["aiBrId"] for row in touching if row["aiBrId"] in ai_security}),
        "testSecurityBrsWithV3Candidate": len({row["testBrId"] for row in touching if row["testBrId"] in test_security}),
        "rankedAiSecurityLeads": len(from_ai), "rankedAiToTestSecurityLeads": len(ranked_security_to_security),
        "aiSecurityWithCaseChains": sum(int(all_ai[x]["aiCaseCount"] or 0) > 0 for x in ai_security),
        "aiSecurityWithPhysicalCompleteCases": sum(bool(list_field(all_ai[x]["aiPhysicalCompleteCaseIds"])) for x in ai_security),
        "testSecurityRulesInRunnerBaseline": len(runner_security),
        "testSecurityRulesWithDecisivePredicateResults": sum(row["distinctDecisiveCaseCount"] > 0 for row in runner_security),
        "testSecurityRulesWithPrimaryExecutablePayloadData": sum(bool(test_security_chains[x]["executablePayloadDataIds"]) for x in test_security if x in test_security_chains),
        "testSecurityRulesWithExactAnchorAlternatives": sum(bool(alternatives.get(x)) for x in test_security),
        "testSecurityRulesWithExecutableAlternativePayloadData": sum(any(alt["executablePayloadDataIds"] for alt in alternatives.get(x, [])) for x in test_security),
        "sameCategoryPreliminaryRelationCounts": dict(relation_counts),
        "semanticFinalDecisions": 0, "testFixtureExecutions": 0, "hostExecutions": 0}
    if len(touching) != 73 or len(same_category) != 10:
        raise ValueError("Security source-anchor candidate population changed")
    for path in source_files:
        if sha256(path) != source_hashes[path.name]:
            raise ValueError("Security source changed during report generation: " + str(path))
    report = "# Security AI-to-Test BR Crossmatch and Chain Review\n\n"
    report += "This review covers the full 387 AI Security BR and 23 Test Security BR populations. Existing source-anchor candidates, ranked cross-category leads, physical AI chains, Test Solution primary and alternative chains, and offline Test-BR predicate results are kept distinct. All relation classifications are preliminary; no SME decision is recorded.\n\n"
    report += "| Measure | Count |\n|---|---:|\n" + "".join(f"| {key} | {value} |\n" for key, value in counts.items() if not isinstance(value, dict))
    report += "\n## Direct Security-to-Security Candidates\n\n| AI BR | Test BR | Preliminary relation |\n|---|---|---|\n"
    for row in same_category:
        relation, _ = DIRECT_RELATIONS.get((row["aiBrId"].split(":")[-1], row["testBrId"]), ("OPEN_PAIR_REVIEW", ""))
        report += f"| {row['aiBrId']} | {row['testBrId']} | {relation} |\n"
    report += "\n## Validation and Limits\n\n"
    report += f"The complete-chain/no-candidate list contains {len(ai_unmatched_complete)} AI Security BRs; the v3 crosswalk has {len(test_security_unmatched)} Test Security BRs with no AI candidate. These are candidate gaps, not authorized NOT_MATCH decisions. Source-anchor candidates are not equivalence. Ranked leads use retrieval prioritization only.\n\n"
    report += f"The offline field/structure runner has rows for {len(runner_security)} of 23 Security Test BRs, with {sum(row['distinctDecisiveCaseCount'] > 0 for row in runner_security)} having bounded decisive results on AI payloads. These results validate only the bound predicate and are not Test fixture executions or whole-case outcomes.\n\n"
    report += "Test chains and exact-anchor alternatives retain their review statuses. Draft expected outcomes and fixture payloads are not run as an approved Test harness. Semantic approval, wire outcomes and host execution remain NOT_ASSESSED/NOT_EXECUTED.\n"
    (output / "REPORT.md").write_text(report, encoding="utf-8")
    (output / "summary.json").write_text(json.dumps({"population": counts,
        "sourceSha256": dict(source_hashes),
        "relationPolicy": "Preliminary only; exact candidate anchors and ranked leads are not semantic decisions.",
        "reviewStatus": "ALL_OPEN_REVIEW_REQUIRED", "executionStatus": "AI_PAYLOAD_BOUNDED_PREDICATES_ONLY_TEST_FIXTURES_NOT_EXECUTED"}, indent=2), encoding="utf-8")
    (output / "comparison-implementation.py").write_bytes(Path(__file__).read_bytes())
    manifest = {path.name: sha256(path) for path in output.iterdir() if path.is_file()}
    (output / "manifest.json").write_text(json.dumps({"algorithm": "SHA-256", "artifacts": manifest}, indent=2), encoding="utf-8")
    print(json.dumps(counts, indent=2))


def self_test():
    assert list_field("[]") == []
    assert list_field('["TC-1"]') == ["TC-1"]
    assert DIRECT_RELATIONS[("389", "SEG100-R-043")][0] == "PARTIAL_FIELD_AND_CONTEXT_OVERLAP"
    assert DIRECT_RELATIONS[("2592", "SEG123-R-011")][0] == "COMPOSITE_CONTEXT_OVERLAP"
    print("PASS: Security relation labels preserve mechanism, applicability and composite scope differences")


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("--review-root", type=Path)
    parser.add_argument("--package", type=Path)
    parser.add_argument("--runner-ledger", type=Path)
    parser.add_argument("--output-dir", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
    elif all((args.review_root, args.package, args.runner_ledger, args.output_dir)):
        run(args.review_root, args.package, args.runner_ledger, args.output_dir)
    else:
        parser.error("Supply review root, independent Test package, runner ledger and new output directory")


if __name__ == "__main__":
    main()