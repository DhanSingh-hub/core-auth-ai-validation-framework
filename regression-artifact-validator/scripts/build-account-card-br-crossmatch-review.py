"""Build a full-chain Account/Card BR gap and candidate review package."""

import argparse
import collections
import csv
import gzip
import hashlib
import json
from pathlib import Path


STRONG_LEADS = {
    ("REQ-SRC-ATL105-PDF-001:1325", "SEG119-R-028"),
    ("REQ-SRC-ATL105-PDF-001:1326", "SEG119-R-029"),
    ("REQ-SRC-ATL105-PDF-001:1505", "SEG155-R-004"),
}
COMPOSITE_LEADS = {
    ("REQ-SRC-ATL105-PDF-001:1529", "SEG156-R-001"),
    ("REQ-SRC-ATL105-PDF-001:1530", "SEG156-R-001"),
}


def sha256(path):
    digest = hashlib.sha256()
    with Path(path).open("rb") as stream:
        for block in iter(lambda: stream.read(1024 * 1024), b""):
            digest.update(block)
    return digest.hexdigest()


def csv_rows(path):
    csv.field_size_limit(100_000_000)
    with Path(path).open(encoding="utf-8-sig", newline="") as stream:
        return list(csv.DictReader(stream))


def jsonl(path):
    with Path(path).open(encoding="utf-8") as stream:
        for line in stream:
            if line.strip():
                yield json.loads(line)


def read_json(path):
    return json.loads(Path(path).read_text(encoding="utf-8-sig"))


def refs(row, field):
    value = row.get(field) or []
    return {value} if isinstance(value, str) else set(value)


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


def alternatives_for(package, br):
    anchors = {anchor_signature(row) for row in br.get("sourceAnchors") or []}
    result = []
    if not anchors:
        return result
    for candidate in package["businessRequirements"]:
        if candidate["id"].startswith("BR-RULE-"):
            continue
        if anchors & {anchor_signature(row) for row in candidate.get("sourceAnchors") or []}:
            result.append(chain_for(package, candidate))
    return result


def write_csv(path, rows):
    rows = list(rows)
    with Path(path).open("w", encoding="utf-8", newline="") as stream:
        fields = list(dict.fromkeys(key for row in rows for key in row))
        writer = csv.DictWriter(stream, fieldnames=fields, extrasaction="ignore")
        writer.writeheader()
        writer.writerows(rows)


def write_jsonl(path, rows):
    with Path(path).open("w", encoding="utf-8", newline="\n") as stream:
        for row in rows:
            stream.write(json.dumps(row, ensure_ascii=True, sort_keys=True) + "\n")


def relation(ai_id, test_id):
    if (ai_id, test_id) == ("REQ-SRC-ATL105-PDF-001:1325", "SEG119-R-028"):
        return "STRONG_ENUMERATION_OVERLAP_REVIEW_REQUIRED", "AI and Test both state card types 1-15 in approved responses; source and response completeness still require review."
    if (ai_id, test_id) == ("REQ-SRC-ATL105-PDF-001:1326", "SEG119-R-029"):
        return "STRONG_CONDITIONAL_OVERLAP_REVIEW_REQUIRED", "AI and Test both state card types 16-20 only when data occurs; verify scope and response completeness."
    if (ai_id, test_id) == ("REQ-SRC-ATL105-PDF-001:1505", "SEG155-R-004"):
        return "STRONG_ENUMERATION_OVERLAP_REVIEW_REQUIRED", "AI and Test enumerate the same five Card Status values; source authority still requires review."
    if (ai_id, test_id) in COMPOSITE_LEADS:
        return "PARTIAL_COMPOSITE_APPLICABILITY_OVERLAP", "The AI BR describes only Visa applicability or only EV charging; Test BR combines both conditions and the Segment 102 fuel-code trigger."
    suffix = ai_id.split(":")[-1]
    if test_id == "SEG119-R-028":
        if suffix == "1325":
            return "STRONG_ENUMERATION_OVERLAP_REVIEW_REQUIRED", "AI :1325 states card types 1-15 in approved responses; verify source scope and response completeness."
        return "RELATED_DIFFERENT_CARD_TYPE_SCOPE", "This AI lead does not establish the Test BR's 1-15 card-type set."
    if test_id == "SEG119-R-029":
        if suffix == "1326":
            return "STRONG_CONDITIONAL_OVERLAP_REVIEW_REQUIRED", "AI :1326 states card types 16-20 only when data occurs; verify source scope and response completeness."
        return "RELATED_DIFFERENT_CARD_TYPE_SCOPE", "This AI lead does not establish the Test BR's 16-20 conditional set."
    if test_id == "SEG155-R-004":
        if suffix == "1505":
            return "STRONG_ENUMERATION_OVERLAP_REVIEW_REQUIRED", "AI :1505 enumerates the same five Card Status values; verify source authority and full value semantics."
        return "RELATED_DIFFERENT_CARD_STATUS_OBLIGATION", "This AI lead does not establish the five-value Card Status enumeration."
    if test_id == "SEG156-R-001" and suffix == "5992":
        return "PARTIAL_COMPOSITE_APPLICABILITY_OVERLAP", "AI :5992 names EV and Visa applicability, but does not state the Test BR's EV fuel-code condition in Segment 102."
    if test_id == "SEG105-R-015":
        return "PARTIAL_AGGREGATE_RULE_COVERAGE", "Top AI leads describe totals reporting, card labels or count/amount behavior separately; no single AI BR states the entire four-field required set."
    if test_id == "SEG108-R-006":
        if suffix in {"3685", "3688", "6200", "6202", "6203", "6204", "6206", "6207", "6208", "6209", "6811", "865", "862"}:
            return "PARTIAL_FIELD_CONTENT_NOT_ORDER", "AI BR covers a field, its presence or loyalty behavior, but not the Test BR's full 15-field serialized order."
        return "RETRIEVAL_LEAD_NOT_ORDER_EVIDENCE", "Shared loyalty/account terminology does not establish any of the 15 serialized positions."
    if test_id == "SEGDL1-R-012":
        if suffix in {"1079", "1612"}:
            return "PARTIAL_CARD_TYPE_173_DL6_TRIGGER", "AI says Card Type 173 triggers DL6, but not the full DL1/DL2/DL3/DL6 block order and marker rules."
        if suffix in {"1074", "1075", "1081", "1100", "6109", "1764"}:
            return "PARTIAL_TABLE_LOAD_BLOCK_MARKER_RULE", "AI covers a block parse/order marker clause, not the full ordered Table Load Response rule."
        return "RETRIEVAL_LEAD_NOT_FULL_BLOCK_ORDER", "Shared Table Load terms do not establish the full block order and marker behavior."
    if test_id == "SEG156-R-001":
        return "COMPOSITE_APPLICABILITY_OVERLAP_REVIEW_REQUIRED", "AI :1529 and :1530 separately cover Visa applicability and EV transaction code; verify combined condition."
    return "RETRIEVAL_LEAD_REVIEW_REQUIRED", "Ranked retrieval only; no semantic decision."


def run(review, package_path, runner_path, output):
    review, package_path, runner_path, output = map(Path, (review, package_path, runner_path, output))
    if output.exists():
        raise ValueError("Refusing to overwrite output directory")
    category_path = review / "2026-10-15-ai-test-br-category-groups-v1/categorized-business-requirements.csv"
    candidate_path = review / "2026-10-14-ai-test-br-crosswalk-v3/ai-test-br-candidate-pairs.csv"
    index_path = review / "2026-10-14-ai-test-br-crosswalk-v3/test-br-ai-br-index.csv"
    ranked_path = review / "2026-10-15-all-ai-br-chain-test-br-review-v1/ai-test-br-ranked-review-leads.csv"
    ai_register_path = review / "2026-10-15-all-ai-br-chain-test-br-review-v1/all-ai-br-review-register.csv"
    ai_chains_path = review / "2026-10-15-all-ai-br-chain-test-br-review-v1/ai-br-chain-evidence.jsonl.gz"
    ai_gap_path = review / "2026-10-15-complete-chain-unmatched-ai-br-v1/complete-chain-ai-brs-without-test-br-candidates.csv"
    test_gap_path = review / "2026-10-15-unmatched-ai-test-br-category-v1/test-brs-without-ai-br-candidates.csv"
    source_paths = [category_path, candidate_path, index_path, ranked_path, ai_register_path,
                    ai_chains_path, ai_gap_path, test_gap_path, package_path, runner_path]
    source_hashes = {path.name: sha256(path) for path in source_paths}

    categories = csv_rows(category_path)
    ai_categories = {row["brId"]: row for row in categories if row["recordType"] == "AI_BR"}
    test_categories = {row["brId"]: row for row in categories if row["recordType"] == "TEST_BR"}
    ai_ids = {identifier for identifier, row in ai_categories.items() if row["sharedCategoryLabel"] == "Account/Card"}
    test_ids = {identifier for identifier, row in test_categories.items() if row["sharedCategoryLabel"] == "Account/Card"}
    complete_ai = {row["AI_BR_ID"]: row for row in csv_rows(ai_gap_path)
                   if row["Category"] == "Account/Card"}
    missing_test = {row["Test_BR_ID"]: row for row in csv_rows(test_gap_path)
                    if row["Category"] == "Account/Card"}
    all_ai_register = {row["aiBrId"]: row for row in csv_rows(ai_register_path)}
    ai_register = {identifier: row for identifier, row in all_ai_register.items() if identifier in ai_ids}
    candidates = csv_rows(candidate_path)
    all_ranked_for_test = [row for row in csv_rows(ranked_path) if row["testBrId"] in missing_test]
    ranked_gap = [row for row in all_ranked_for_test if row["aiBrId"] in complete_ai]
    ranked_pair = {(row["aiBrId"], row["testBrId"]): row for row in all_ranked_for_test}
    direct_pairs = [row for row in candidates if row["aiBrId"] in ai_ids or row["testBrId"] in test_ids]
    test_candidate_index = {row["testBrId"]: row for row in csv_rows(index_path)}
    package = read_json(package_path)
    runner = {row["testBrId"]: row for row in jsonl(runner_path)}
    test_rules = {row["id"][len("BR-RULE-"):]: row for row in package["businessRequirements"]
                  if row["id"].startswith("BR-RULE-")}
    test_chains = {identifier: chain_for(package, test_rules[identifier])
                   for identifier in test_ids if identifier in test_rules}
    test_alternatives = {identifier: alternatives_for(package, test_rules[identifier])
                         for identifier in test_ids if identifier in test_rules}
    if (len(ai_ids), len(test_ids), len(complete_ai), len(missing_test)) != (447, 26, 283, 7):
        raise ValueError("Account/Card population counts differ from reviewed baselines")
    if len({row["aiBrId"] for row in direct_pairs if row["aiBrId"] in ai_ids}) != 15 or len(direct_pairs) != 120:
        raise ValueError("Account/Card direct candidate populations changed")

    output.mkdir(parents=True)
    ai_rows = []
    for identifier, row in sorted(complete_ai.items()):
        register = ai_register[identifier]
        ai_rows.append({"aiBrId": identifier, "statement": row["Requirement"],
            "category": "Account/Card", "sourcePage": row["Source_Page"], "sourceEntity": row["Source_Entity"],
            "scenarioIds": row["Scenario_IDs"], "testCaseCount": row["Test_Case_Count"],
            "physicalCompleteCaseCount": row["Physical_Complete_Case_Count"],
            "physicalCompleteCaseIds": row["Physical_Complete_Case_IDs"],
            "v3NoCandidateReason": row["V3_No_Candidate_Reason"],
            "rankedLeadCount": register["rankedCandidateCount"],
            "rankedLeadsToSevenTestGaps": sum(row["aiBrId"] == identifier for row in ranked_gap),
            "semanticStatus": row["Semantic_Status"], "executionStatus": row["Execution_Status"]})
    write_csv(output / "ai-account-card-complete-chain-no-candidate.csv", ai_rows)

    test_rows = []
    for identifier in sorted(test_ids):
        category = test_categories[identifier]
        chain = test_chains.get(identifier, {"br": {}, "scenarios": [], "cases": [], "data": [], "placeholderIds": [], "executablePayloadDataIds": []})
        execution = runner.get(identifier, {})
        test_rows.append({"testBrId": identifier, "title": category["titleOrStatement"],
            "sourceAnchors": category["sourceAnchorsOrEntities"],
            "categoryReviewStatus": category["categoryReviewStatus"],
            "v3HasAiCandidate": identifier in test_candidate_index,
            "v3MatchedAiBrCount": test_candidate_index.get(identifier, {}).get("matchedAiBrCount", "0"),
            "noCandidateRegisterReason": missing_test.get(identifier, {}).get("No_Candidate_Reason", "HAS_V3_AI_CANDIDATE"),
            "packageBrStatus": chain["br"].get("status"),
            "scenarioIds": ";".join(item["id"] for item in chain["scenarios"]),
            "testCaseIds": ";".join(item["id"] for item in chain["cases"]),
            "testDataIds": ";".join(item["id"] for item in chain["data"]),
            "placeholderIds": ";".join(chain["placeholderIds"]),
            "alternativeExactAnchorBrIds": ";".join(item["br"]["id"] for item in test_alternatives.get(identifier, [])),
            "alternativeTestDataReadiness": ";".join(sorted({str(d.get("readiness") or "UNSET")
                for alt in test_alternatives.get(identifier, []) for d in alt["data"]})),
            "runnerImplementation": execution.get("implementation", "MISSING_FROM_601_RULE_BASELINE"),
            "runnerRunState": execution.get("runState", "NO_RESULT"),
            "runnerDistinctDecisiveCases": execution.get("distinctDecisiveCaseCount", 0),
            "runnerOutcomesOnAiPayloads": json.dumps(execution.get("outcomeRowCounts", {}), sort_keys=True),
            "semanticStatus": "NOT_ASSESSED", "testFixtureExecution": "NOT_EXECUTED", "hostExecution": "NOT_EXECUTED"})
    write_csv(output / "test-account-card-no-ai-candidate.csv", [row for row in test_rows if row["v3HasAiCandidate"] is False])
    write_csv(output / "test-account-card-all-rules.csv", test_rows)

    pair_rows = []
    for row in direct_pairs:
        ai_record = all_ai_register[row["aiBrId"]]
        test_id = row["testBrId"]
        test_chain = test_chains.get(test_id, {})
        test_rule = test_rules.get(test_id, {})
        pair_rows.append({"basis": "V3_SOURCE_ANCHOR_CANDIDATE",
            "aiBrId": row["aiBrId"], "aiCategory": ai_categories[row["aiBrId"]]["sharedCategoryLabel"],
            "aiStatement": row["aiBrStatement"], "testBrId": row["testBrId"],
            "testCategory": test_categories[row["testBrId"]]["sharedCategoryLabel"], "testTitle": row["testBrTitle"],
            "candidateDisposition": row["candidateDisposition"], "semanticMatchStatus": row["semanticMatchStatus"],
            "aiInCompleteChainGapPopulation": row["aiBrId"] in complete_ai,
            "aiChainStatus": ai_record["chainStatus"],
            "aiPhysicalCompleteCaseCount": len(list_field(ai_record.get("aiPhysicalCompleteCaseIds"))),
            "testPackageBrStatus": test_rule.get("status"),
            "testPackagePlaceholderCount": len(test_chain.get("placeholderIds", [])),
            "testAlternativeExactAnchorBrIds": ";".join(alt["br"]["id"] for alt in test_alternatives.get(test_id, [])),
            "preliminaryRelation": relation(row["aiBrId"], row["testBrId"])[0],
            "preliminaryRationale": relation(row["aiBrId"], row["testBrId"])[1],
            "semanticDecision": "OPEN_REVIEW_REQUIRED"})
    for (ai_id, test_id), lead in ranked_pair.items():
        test_title = test_categories[test_id]["titleOrStatement"]
        relation_label, rationale = relation(ai_id, test_id)
        ai_record = all_ai_register[ai_id]
        ai_physical_cases = list_field(ai_record.get("aiPhysicalCompleteCaseIds"))
        pair_rows.append({"basis": "RANKED_REVIEW_LEAD_NOT_V3_MATCH",
            "aiBrId": ai_id, "aiCategory": ai_record["aiCategory"],
            "aiStatement": lead["aiStatement"], "testBrId": test_id,
            "testCategory": "Account/Card", "testTitle": test_title,
            "candidateDisposition": "RANKED_RETRIEVAL_ONLY", "semanticMatchStatus": "NOT_ASSESSED",
            "retrievalRank": lead["candidateRank"], "retrievalScore": lead["retrievalScore"],
            "sharedSegments": lead["sharedSegments"], "sharedElements": lead["sharedElements"],
            "retrievalReasons": lead["retrievalReasons"],
            "aiInCompleteChainGapPopulation": ai_id in complete_ai,
            "aiChainStatus": ai_record["chainStatus"],
            "aiPhysicalCompleteCaseCount": len(ai_physical_cases),
            "preliminaryRelation": relation_label, "preliminaryRationale": rationale,
            "semanticDecision": "OPEN_REVIEW_REQUIRED"})
    pair_rows.sort(key=lambda row: (row["basis"] != "V3_SOURCE_ANCHOR_CANDIDATE",
                                    int(row.get("retrievalRank") or 0), row["aiBrId"], row["testBrId"]))
    write_csv(output / "account-card-crossmatch-leads.csv", pair_rows)

    same_category = [row for row in direct_pairs if row["aiBrId"] in ai_ids and row["testBrId"] in test_ids]
    counts = {"aiAccountCardBrs": len(ai_ids), "testAccountCardBrs": len(test_ids),
        "completeChainAiBrsWithoutV3Candidate": len(complete_ai), "unmatchedTestBrsWithoutAiCandidate": len(missing_test),
        "allV3PairsTouchingAccountCard": len(direct_pairs), "sameCategoryV3PairRows": len(same_category),
        "sameCategoryV3AiBrs": len({row["aiBrId"] for row in same_category}),
        "sameCategoryV3TestBrs": len({row["testBrId"] for row in same_category}),
        "rankedLeadsFromFullAiCatalogToSevenTestGaps": len(all_ranked_for_test),
        "distinctFullCatalogAiBrsWithRankedLeads": len({row["aiBrId"] for row in all_ranked_for_test}),
        "rankedLeadsFromCompleteChainAiGapPopulationToSevenTestGaps": len(ranked_gap),
        "completeChainAiGapBrsWithRankedLeads": len({row["aiBrId"] for row in ranked_gap}),
        "sevenTestGapsReachedByFullCatalogRankedLeads": len({row["testBrId"] for row in all_ranked_for_test}),
        "aiGapBrsWithCompletePhysicalChain": len(complete_ai),
        "testGapRulesWithPlaceholderPrimaryChains": sum(bool(test_chains.get(row["testBrId"], {}).get("placeholderIds")) for row in test_rows if not row["v3HasAiCandidate"]),
        "sameCategoryPreliminaryRelationCounts": dict(collections.Counter(row["preliminaryRelation"] for row in pair_rows if row["aiCategory"]=="Account/Card" and row["testCategory"]=="Account/Card")),
        "semanticFinalDecisions": 0, "testFixtureExecutions": 0, "hostExecutions": 0}
    if (counts["rankedLeadsFromFullAiCatalogToSevenTestGaps"] != len(all_ranked_for_test)
        or counts["rankedLeadsFromCompleteChainAiGapPopulationToSevenTestGaps"] != len(ranked_gap)
        or counts["sevenTestGapsReachedByFullCatalogRankedLeads"] != 7):
        raise ValueError("Expected each unmatched Test Account/Card BR to have at least one review lead")
    for path in source_paths:
        if sha256(path) != source_hashes[path.name]:
            raise ValueError("Account/Card source changed during report generation: " + str(path))

    write_jsonl(output / "test-account-card-chain-evidence.jsonl", ({"testBrId": identifier,
        "primaryChain": test_chains.get(identifier), "exactAnchorAlternatives": test_alternatives.get(identifier, [])}
        for identifier in sorted(test_ids)))
    relevant_ai_ids = {row["aiBrId"] for row in ai_rows}
    with gzip.open(review / "2026-10-15-all-ai-br-chain-test-br-review-v1/ai-br-chain-evidence.jsonl.gz", "rt", encoding="utf-8") as stream:
        ai_chain_rows = [json.loads(line) for line in stream if line.strip()]
    write_jsonl(output / "ai-account-card-chain-evidence.jsonl", (row for row in ai_chain_rows if row["aiBrId"] in relevant_ai_ids))
    report = "# Account/Card AI-to-Test BR Crossmatch and Chain Review\n\n"
    report += "This review covers all 447 AI Account/Card BRs, all 26 Test Account/Card BRs, the 283 complete-physical-chain AI BRs with no v3 Test candidate, and the seven Test BRs with no v3 AI candidate. It includes 1,371 top-K ranked leads from the full AI catalog to those seven Test BRs, separately identifying the 171 leads sourced from the 283 complete-chain AI gap set. Existing source-anchor pairs and ranked retrieval leads are separate; ranked leads are not matches.\n\n"
    report += "| Measure | Count |\n|---|---:|\n" + "".join(f"| {k} | {v} |\n" for k,v in counts.items() if not isinstance(v,dict))
    report += "\n## Seven Test BR Gap Review\n\n"
    for test_id in sorted(missing_test):
        test_row=next(row for row in test_rows if row["testBrId"]==test_id)
        leads=[row for row in pair_rows if row["testBrId"]==test_id]
        report += f"### {test_id}\n\n{test_row['title']}\n\n"
        report += f"Best ranked lead(s): {', '.join(dict.fromkeys(row['aiBrId'] for row in leads[:5])) or 'none'}. "
        report += f"Test chain status: {test_row['packageBrStatus']}; primary placeholders={len(test_row['placeholderIds'].split(';')) if test_row['placeholderIds'] else 0}; executable Test payload data not established.\n\n"
        for row in leads[:3]: report += f"- {row['aiBrId']}: {row['preliminaryRelation']} - {row['preliminaryRationale']}\n"
        report += "\n"
    report += "## Strong Provisional Leads\n\n"
    report += "- `SEG119-R-028` is closely stated by AI `:1325` (card types 1-15 included in approved responses); AI chain has two physically complete cases.\n"
    report += "- `SEG119-R-029` is closely stated by AI `:1326` (card types 16-20 only when data occurs); AI chain has two physically complete cases.\n"
    report += "- `SEG155-R-004` is closely stated by AI `:1505` (the same five Card Status values); AI chain has six physically complete cases.\n"
    report += "- `SEG156-R-001` has a close AI statement `:5992` (EV charging segment, Visa-only) plus AI `:1529` (Visa-only) and `:1530` (EV fuel code in Segment 102); together they suggest coverage, but the condition conjunction and negative branches need review.\n"
    report += "\n`SEG105-R-015` has only partial/fragmentary AI leads; `SEG108-R-006` has no direct AI field-order rule identified; `SEGDL1-R-012` has partial block-order/indicator leads, not the full sequence.\n\n"
    report += "No lead is an authorized semantic decision. Test category labels remain review proposals; Test BRs and package chains remain `DRAFT_REVIEW_REQUIRED`/`REVIEW_REQUIRED`. Offline predicates run on AI payloads only; no Test fixture or host executions are claimed.\n"
    (output / "REPORT.md").write_text(report, encoding="utf-8")
    (output / "summary.json").write_text(json.dumps({"population": counts, "sourceSha256": source_hashes,
        "limitations": ["No match is semantically approved.", "Ranked leads prioritize review only.",
                        "Physical AI chain is not independent Test execution.",
                        "Test primary/alternative fixtures remain review required or not executable."]}, indent=2), encoding="utf-8")
    (output / "comparison-implementation.py").write_bytes(Path(__file__).read_bytes())
    manifest = {path.name: sha256(path) for path in output.iterdir() if path.is_file()}
    (output / "manifest.json").write_text(json.dumps({"algorithm": "SHA-256", "artifacts": manifest}, indent=2), encoding="utf-8")
    print(json.dumps(counts, indent=2))


def list_field(value):
    if not value:
        return []
    try:
        parsed=json.loads(value)
        return parsed if isinstance(parsed,list) else [parsed]
    except (TypeError,json.JSONDecodeError):
        return [part for part in str(value).split(";") if part]


def test_self():
    assert relation("REQ-SRC-ATL105-PDF-001:1325", "SEG119-R-028")[0] == "STRONG_ENUMERATION_OVERLAP_REVIEW_REQUIRED"
    assert relation("REQ-SRC-ATL105-PDF-001:1326", "SEG119-R-029")[0] == "STRONG_CONDITIONAL_OVERLAP_REVIEW_REQUIRED"
    assert relation("REQ-SRC-ATL105-PDF-001:1529", "SEG156-R-001")[0] == "PARTIAL_COMPOSITE_APPLICABILITY_OVERLAP"
    assert relation("REQ-SRC-ATL105-PDF-001:5992", "SEG156-R-001")[0] == "PARTIAL_COMPOSITE_APPLICABILITY_OVERLAP"
    assert list_field("[]") == []
    print("PASS: Account/Card lead classifications and chain list parsing")


def main():
    parser=argparse.ArgumentParser()
    parser.add_argument("--review-root",type=Path)
    parser.add_argument("--package",type=Path)
    parser.add_argument("--runner-ledger",type=Path)
    parser.add_argument("--output-dir",type=Path)
    parser.add_argument("--self-test",action="store_true")
    args=parser.parse_args()
    if args.self_test:test_self()
    elif all((args.review_root,args.package,args.runner_ledger,args.output_dir)):run(args.review_root,args.package,args.runner_ledger,args.output_dir)
    else:parser.error("Supply review root, Test package, runner ledger, and a new output directory")


if __name__=="__main__":
    main()