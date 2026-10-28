"""Review the 32 Amount Test BR gaps against the full AI BR catalog."""

import argparse
import collections
import csv
import gzip
import hashlib
import json
import os
from pathlib import Path


SCRIPT = Path(__file__).resolve()
ROOT = SCRIPT.parents[1]
REVIEW = ROOT / "specifications/ATL105/test-output/ai-solution-independent-review"
GAP_DIR = REVIEW / "2026-10-15-unmatched-ai-test-br-category-v1"
QUEUE = REVIEW / "2026-10-16-all-ai-br-chain-test-br-review-v2"
PACKAGE_PATH = ROOT / "specifications/ATL105/test-output/test-solution-independent-review/complete-all-test-solution-br-ts-tc-td-package.json"
SPEC_PATH = ROOT / "specifications/ATL105/docs/specs/extracted_text.txt"
OUTPUT = ROOT / "specifications/ATL105/test-output/ai-solution-independent-review/2026-10-16-amount-test-br-gaps-v1"


RELATIONS = {
    "SEG100-R-065": ("PARTIAL_SCOPE_OVERLAP", ["4726", "502", "791"], "AI Response Code F clauses identify a partial approval, but do not by themselves prove that the response carries the approved amount field."),
    "SEG102-R-020": ("CLAUSE_MATCH_PROPOSED", ["1200", "1571"], "AI clauses state that a Product Data Field Delimiter follows Quantity and Unit Price; this is the same serialization obligation."),
    "SEG102-R-023": ("CLAUSE_MATCH_PROPOSED", ["1912"], "AI :1912 states Segment 102 data length 001-381, matching the Test segment maximum. It does not establish full wire serialization."),
    "SEG102-R-025": ("PARTIAL_SCOPE_OVERLAP", ["3535", "3536", "3537"], "AI leads establish Segment 143 fields/count in isolation, not that its order and count mirror Segment 102 when both are present."),
    "SEG103-R-006": ("PARTIAL_SCOPE_OVERLAP", ["2160", "2161"], "AI covers WIC Discount Amount presence/length only; it does not establish the seven-field Segment 103 order."),
    "SEG105-R-017": ("POLICY_COUNTERPART_NOT_ESTABLISHED", [], "This is a certification policy gate for selecting Segment 119, not an ATL105 runtime rule. Preserve it as a policy blocker, not an AI semantic NOT_MATCH."),
    "SEG105-R-018": ("PARTIAL_SCOPE_OVERLAP", ["1034", "1040"], "AI :1034 identifies a Totals Data Segment in Data Section 3; :1040 repeats the erroneous 116 label. The source layout says Data Sections 1 and 3, no Section 2, and identifies Segment 119; no full AI match."),
    "SEG105-R-019": ("CLAUSE_MATCH_PROPOSED", ["5880"], "AI :5880 states Totals Data Segment appears in Data Section 3, Field 3. Confirm the Test rule's sole-segment clause separately from placement."),
    "SEG105-R-022": ("CLAUSE_MATCH_PROPOSED", ["4795"], "AI :4795 states the Totals Request includes Segment 105. The source defines that segment as required; AI :3698's conditional wording needs separate correction review."),
    "SEG111-R-006": ("CLAUSE_MATCH_PROPOSED", ["1247", "1919"], "AI :1247/:1919 state the Variable Information Segment maximum is 999 alphanumeric characters, matching the Test maximum."),
    "SEG112-R-006": ("CLAUSE_MATCH_PROPOSED", ["1254"], "AI :1254 states the Additional Information section repeats by indicator with a 990-byte maximum, matching Segment 112. Do not transfer Segment 130/131 caps."),
    "SEG115-R-012": ("UNSUPPORTED_TEST_HYPOTHESIS_REVIEW_REQUIRED", ["1149", "1019"], "The Test title itself says 'plausibly' and infers Segment 115 from a shared version trigger. AI chain leads do not establish source authority for this wire-format vehicle; hold until the source/layout and SME interpretation settle it."),
    "SEG116-R-009": ("SOURCE_CONFLICT_RESOLVED_BY_AUTHORITATIVE_LAYOUT", ["1040", "1924"], "Section 11.4.1.2 labels the request as Segment 116, but its layout/table and Section 12.17 identify Segment 119; Element 85 identifies 116 as TransArmor. Under source precedence, correct the misleading label and retain the conflict note; AI :1040's 116 claim is not coverage."),
    "SEG118-R-001": ("CLAUSE_MATCH_PROPOSED", ["5895", "1272", "1273"], "AI clauses describe proprietary data load as request/capture and split host-pushed vs device-pushed data categories, matching the Test purpose at clause level."),
    "SEG118-R-030": ("CLAUSE_MATCH_PROPOSED", ["1904", "5033", "5034"], "AI clauses state Response Code V/W meaning for pending Custom Receipt Text; this directly overlaps the Test trigger, while the full response family remains separate."),
    "SEG119-R-001": ("PARTIAL_SCOPE_OVERLAP_SOURCE_LABEL_CONFLICT", ["1040", "1043"], "AI :1043 covers Segment 119 placement in Data Section 3 Field 3; AI :1040 repeats the conflicting 116 label. The source supports Sections 1 and 3/no Section 2 but not the erroneous 116 identity."),
    "SEG119-R-002": ("CLAUSE_MATCH_PROPOSED", ["1044"], "AI :1044 states Segment 119 is sent only when totals data is required, matching the Test condition."),
    "SEG119-R-030": ("CLAUSE_MATCH_PROPOSED", ["1328", "440"], "AI :1328 states that asterisk-marked non-financial card categories are excluded from Grand Total; :440 summarizes non-financial exclusions."),
    "SEG119-R-034": ("POLICY_COUNTERPART_NOT_ESTABLISHED", [], "Retry/duplicate behavior is an open Segment 119 policy/SME gate; generic transaction retry rules are not an approved totals policy."),
    "SEG120-R-004": ("PARTIAL_SCOPE_OVERLAP", ["1927", "178"], "AI :1927 states Segment 120 total length 001-1009, covering the segment cap. The separate 999-character Print Data cap is not established by that clause; :178 has a different unresolved field scope."),
    "SEG130-R-011": ("CLAUSE_MATCH_PROPOSED", ["1349"], "AI :1349 states Segment 130 EMV Additional Information repeats by indicator with a 2,000-byte cap; keep this distinct from Segment 112/131."),
    "SEG131-R-009": ("CLAUSE_MATCH_PROPOSED", ["1354"], "AI :1354 states Segment 131 EMV Additional Information repeats by indicator with a 2,800-byte cap, distinct from Segment 130."),
    "SEG139-R-001": ("CLAUSE_MATCH_PROPOSED", ["1371"], "AI :1371 states Segment 139 retrieves previous-period debit totals for the Segment 141 day-end batch close, matching the purpose clause."),
    "SEG140-R-001": ("CLAUSE_MATCH_PROPOSED", ["1373", "1377"], "AI clauses state that Segment 140 repeats the first six request fields, then Moneris text and debit total; same response composition."),
    "SEG143-R-008": ("CLAUSE_MATCH_PROPOSED", ["1399", "1400"], "AI clauses distinguish the tax delimiter within one product from the Field Separator between products/end of segment."),
    "SEG143-R-009": ("CLAUSE_MATCH_PROPOSED", ["1381"], "AI :1381 states fewer than three taxes may terminate early with a Field Separator, matching the Test clause."),
    "SEG149-R-001": ("CLAUSE_MATCH_PROPOSED", ["3563", "2351"], "AI clauses identify Segment 149 Price Data as required and contain Comdata fuel-price values, matching the authorizer-specific data purpose."),
    "SEG149-R-002": ("CLAUSE_MATCH_PROPOSED", ["1475", "1476"], "AI clauses state the three separators between fields 1-4 and that field 4 internal values are not Field-Separator-delimited; same serialization rule."),
    "SEG150-R-001": ("CLAUSE_MATCH_PROPOSED", ["5968"], "AI :5968 states the Fuel Price Update Response indicates success or failure of the request, matching the Test outcome clause."),
    "SEG156-R-004": ("CLAUSE_MATCH_PROPOSED", ["1535", "1541", "5993", "5996"], "AI clauses state the hhmmss format and bounds for the named EV time tables; test each table independently."),
    "SEG157-R-006": ("CLAUSE_MATCH_PROPOSED", ["1564", "1586"], "AI clauses state tax/discount/coupon values are included in adjusted product amounts and excluded as separate products."),
    "SEG157-R-008": ("CLAUSE_MATCH_PROPOSED", ["1569", "1572", "1200", "1571"], "AI clauses identify the Field Separator vs Product Data Field Delimiter by product position and field, matching the dual-delimiter scheme."),
}


SOURCE_RANGES = {
    "FINANCIAL_RESPONSE_CODE": (21186, 21214),
    "PRODUCT_SEGMENT_102": (11562, 11612),
    "TOTALS_REQUEST_LAYOUT": (8339, 8385),
    "TOTALS_SEGMENT_105": (11923, 11980),
    "SEGMENT_111": (12569, 12585),
    "SEGMENT_112": (12635, 12680),
    "SEGMENT_115": (12911, 12955),
    "SEGMENT_116": (12965, 13005),
    "SEGMENT_118": (12982, 13035),
    "SEGMENT_119": (13549, 13690),
    "SEGMENT_120": (13756, 13800),
    "SEGMENT_130": (14012, 14100),
    "SEGMENT_131": (14145, 14225),
    "MONERIS_139_140": (14522, 14727),
    "TAX_BY_PRODUCT_143": (14935, 15025),
    "FUEL_PRICE_149_150": (16066, 16175),
    "EV_TIME_TABLES": (16635, 16735),
    "ADJUSTED_PRODUCT_157": (16904, 17005),
    "ELEMENT_85_SEGMENT_IDS": (21399, 21440),
}


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


def read_csv(path):
    csv.field_size_limit(100_000_000)
    with native(path).open(encoding="utf-8-sig", newline="") as stream:
        return list(csv.DictReader(stream))


def read_lines(path):
    opener = gzip.open if path.suffix == ".gz" else open
    with opener(native(path), "rt", encoding="utf-8") as stream:
        for line in stream:
            if line.strip():
                yield json.loads(line)


def refs(row, plural, singular):
    values = row.get(plural) or []
    if isinstance(values, str):
        values = [values]
    result = set(values)
    if row.get(singular):
        result.add(row[singular])
    return result


def test_chain(package, br):
    scenarios = [row for row in package["testScenarios"] if br["id"] in refs(row, "requirementIds", "requirementId")]
    scenario_ids = {row["id"] for row in scenarios}
    cases = [row for row in package["testCases"] if refs(row, "scenarioIds", "scenarioId") & scenario_ids]
    case_ids = {row["id"] for row in cases}
    data = [row for row in package["testData"] if refs(row, "testCaseIds", "testCaseId") & case_ids]
    return {"businessRequirement": {"id": br["id"], "title": br["title"], "status": br.get("status"),
                                     "sourceAnchors": br.get("sourceAnchors") or []},
            "scenarioIds": [row["id"] for row in scenarios], "caseIds": [row["id"] for row in cases],
            "testDataIds": [row["id"] for row in data],
            "testDataReadiness": [row.get("readiness") for row in data], "payloadValuesExported": False}


def write_csv(path, rows):
    rows = list(rows)
    if not rows:
        raise ValueError("Cannot write an empty CSV")
    fields = list(dict.fromkeys(key for row in rows for key in row))
    with native(path).open("w", encoding="utf-8", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=fields)
        writer.writeheader()
        writer.writerows({key: json.dumps(value, ensure_ascii=True) if isinstance(value, (dict, list)) else value
                          for key, value in row.items()} for row in rows)


def write_jsonl(path, rows):
    with native(path).open("w", encoding="utf-8", newline="\n") as stream:
        for row in rows:
            stream.write(json.dumps(row, sort_keys=True, ensure_ascii=True) + "\n")


def self_test():
    assert len(RELATIONS) == 32
    assert len([v for v in RELATIONS.values() if v[0] == "CLAUSE_MATCH_PROPOSED"]) == 22
    assert RELATIONS["SEG105-R-017"][0] == "POLICY_COUNTERPART_NOT_ESTABLISHED"
    assert RELATIONS["SEG116-R-009"][0] == "SOURCE_CONFLICT_RESOLVED_BY_AUTHORITATIVE_LAYOUT"
    assert RELATIONS["SEG115-R-012"][0] == "UNSUPPORTED_TEST_HYPOTHESIS_REVIEW_REQUIRED"
    assert RELATIONS["SEG102-R-023"][1] == ["1912"]
    assert RELATIONS["SEG111-R-006"][1] == ["1247", "1919"]
    print("PASS: 32 Amount targets with direct, partial, policy, hypothesis and source-conflict dispositions")


def run(output):
    if native(output).exists():
        raise ValueError("Refusing to overwrite output directory")
    gap_manifest = read_json(GAP_DIR / "manifest.json")
    queue_manifest = read_json(QUEUE / "manifest.json")
    screen_manifest = read_json(QUEUE / "automated-chain-screen-v2/manifest.json")
    for path, digest in [
        (GAP_DIR / "test-brs-without-ai-br-candidates.csv", gap_manifest["outputSha256"]["test-brs-without-ai-br-candidates.csv"]),
        (QUEUE / "all-ai-br-review-register.csv", queue_manifest["artifacts"]["all-ai-br-review-register.csv"]),
        (QUEUE / "ai-test-br-ranked-review-leads.csv", queue_manifest["artifacts"]["ai-test-br-ranked-review-leads.csv"]),
        (QUEUE / "ai-br-chain-evidence.jsonl.gz", queue_manifest["artifacts"]["ai-br-chain-evidence.jsonl.gz"]),
        (QUEUE / "automated-chain-screen-v2/all-br-chain-screen.csv", screen_manifest["artifacts"]["all-br-chain-screen.csv"]),
    ]:
        if sha256(path) != digest:
            raise ValueError("Frozen review input hash mismatch: " + path.name)
    queue_summary = read_json(QUEUE / "summary.json")
    if sha256(PACKAGE_PATH) != queue_summary["inputSha256"][str(PACKAGE_PATH)]:
        raise ValueError("Independent Test package differs from frozen retrieval package")
    gaps = [row for row in read_csv(GAP_DIR / "test-brs-without-ai-br-candidates.csv") if row["Category"] == "Amount"]
    if len(gaps) != 32 or {row["Test_BR_ID"] for row in gaps} != set(RELATIONS):
        raise ValueError("Amount category population differs from the frozen 32-row list")
    package = read_json(PACKAGE_PATH)
    test_by_id = {row["id"]: row for row in package["businessRequirements"]}
    ai_register = {row["aiBrId"]: row for row in read_csv(QUEUE / "all-ai-br-review-register.csv")}
    ai_by_suffix = {identifier.rsplit(":", 1)[-1]: identifier for identifier in ai_register}
    ai_chains = {row["aiBrId"]: row for row in read_lines(QUEUE / "ai-br-chain-evidence.jsonl.gz")}
    screens = {row["aiBrId"]: row for row in read_csv(QUEUE / "automated-chain-screen-v2/all-br-chain-screen.csv")}
    candidates = [row for row in read_csv(QUEUE / "ai-test-br-ranked-review-leads.csv") if row["testBrId"] in RELATIONS]
    leads = collections.defaultdict(list)
    lead_by_pair = {}
    for row in candidates:
        leads[row["testBrId"]].append(row)
        lead_by_pair[(row["testBrId"], row["aiBrId"])] = row
    for items in leads.values():
        items.sort(key=lambda row: int(row["candidateRank"]))
    test_chains = {rule_id: test_chain(package, test_by_id["BR-RULE-" + rule_id]) for rule_id in RELATIONS}
    relations = []
    review_rows = []
    selected_ai_ids = set()
    for gap in gaps:
        rule_id = gap["Test_BR_ID"]
        disposition, suffixes, rationale = RELATIONS[rule_id]
        selected = []
        for suffix in suffixes:
            ai_id = ai_by_suffix.get(suffix)
            if ai_id is None:
                raise ValueError("Curated AI BR missing from complete register: " + suffix)
            selected_ai_ids.add(ai_id)
            ai = ai_register[ai_id]
            chain = ai_chains[ai_id]
            screen = screens[ai_id]
            ranked = lead_by_pair.get((rule_id, ai_id))
            record = {"aiBrId": ai_id, "aiStatement": ai["aiStatement"],
                      "rank": ranked.get("candidateRank") if ranked else "NOT_RETAINED_IN_TOP_K",
                      "retrievalScore": ranked.get("retrievalScore") if ranked else "",
                      "scenarioCount": len(chain["scenarios"]), "caseCount": len(chain["cases"]),
                      "physicalCompleteCaseCount": len(chain["physicalCompleteCaseIdsFromV6"]),
                      "artifactFlags": screen["reviewFlags"]}
            selected.append(record)
            relations.append({"testBrId": rule_id, "aiBrId": ai_id, "relation": disposition,
                              "aiStatement": ai["aiStatement"], "rationale": rationale,
                              "rank": record["rank"], "retrievalScore": record["retrievalScore"],
                              "aiCaseCount": record["caseCount"], "aiPhysicalCompleteCaseCount": record["physicalCompleteCaseCount"],
                              "aiArtifactFlags": record["artifactFlags"],
                              "testStatus": test_by_id["BR-RULE-" + rule_id].get("status"),
                              "semanticStatus": "NOT_ASSESSED", "executionStatus": "NOT_EXECUTED"})
        test_br = test_by_id["BR-RULE-" + rule_id]
        review_rows.append({**gap, "disposition": disposition, "selectedAiBrs": selected,
                            "retainedLeadCount": len(leads[rule_id]), "rationale": rationale,
                            "testStatus": test_br.get("status"), "testScenarioCount": len(test_chains[rule_id]["scenarioIds"]),
                            "testCaseCount": len(test_chains[rule_id]["caseIds"]),
                            "testDataCount": len(test_chains[rule_id]["testDataIds"]),
                            "semanticStatus": "NOT_ASSESSED", "executionStatus": "NOT_EXECUTED"})
    top_ai = {row["aiBrId"] for rule_id in RELATIONS for row in leads[rule_id][:3]}
    evidence_ai = selected_ai_ids | top_ai
    source_lines = native(SPEC_PATH).read_text(encoding="utf-8-sig").splitlines()
    excerpts = [{"evidenceId": evidence_id, "source": str(SPEC_PATH), "sha256": sha256(SPEC_PATH),
                 "startLine": start, "endLine": end, "quote": "\n".join(source_lines[start - 1:end]),
                 "interpretationStatus": "REVIEW_REQUIRED"}
                for evidence_id, (start, end) in SOURCE_RANGES.items()]
    output.mkdir(parents=True)
    write_csv(output / "amount-test-brs.csv", review_rows)
    write_csv(output / "proposed-relations.csv", relations)
    write_csv(output / "retained-ranked-leads.csv", candidates)
    write_jsonl(output / "test-chain-evidence.jsonl", (test_chains[key] for key in sorted(test_chains)))
    write_jsonl(output / "ai-chain-evidence.jsonl", (ai_chains[key] for key in sorted(evidence_ai)))
    write_jsonl(output / "source-excerpts.jsonl", excerpts)
    native(output / ".gitattributes").write_text("* -text whitespace=cr-at-eol\n", encoding="ascii")
    counts = collections.Counter(row["disposition"] for row in review_rows)
    summary = {"category": "Amount", "populationMeaning": "32 independent Test BRs absent from the v3 exact-anchor candidate index",
               "aiBrsSearched": len(ai_register), "testBrsSearched": len(test_by_id), "testGapRows": len(review_rows),
               "retainedRankedLeadPairs": len(candidates), "targetsWithRetainedLeads": sum(bool(leads[key]) for key in RELATIONS),
               "selectedRelationRows": len(relations), "selectedDistinctAiBrs": len(selected_ai_ids),
               "dispositions": dict(counts), "testChainsDraft": sum(test_by_id["BR-RULE-"+key].get("status")=="DRAFT_REVIEW_REQUIRED" for key in RELATIONS),
               "semanticApprovals": 0, "testExecutions": 0,
               "inputSha256": {str(path): sha256(path) for path in [SCRIPT, GAP_DIR / "test-brs-without-ai-br-candidates.csv",
                   QUEUE / "all-ai-br-review-register.csv", QUEUE / "ai-test-br-ranked-review-leads.csv",
                   QUEUE / "ai-br-chain-evidence.jsonl.gz", PACKAGE_PATH, SPEC_PATH]},
               "limitations": ["v3 candidate absence is an exact-anchor index fact, not semantic non-match.",
                               "Top-K candidate ranks are retrieval cues; directly searched clauses outside the shortlist are marked.",
                               "All Test BR chains are draft; no fixtures or host execution were performed.",
                               "Totals selection and retry policies remain SME/policy items; the Segment 116/119 label conflict follows the authoritative Section 12.17 and Element 85 table."]}
    native(output / "summary.json").write_text(json.dumps(summary, indent=2) + "\n", encoding="utf-8")
    report = ["# Amount Category: 32 Test BR Gap Review", "",
              "All 6,880 AI BRs and 1,330 independent Test BRs were available to the retrieval queue. The 32 Test BRs below lack a v3 exact-anchor candidate; that is not proof of semantic non-match.",
              "", "## Dispositions", "", "| Disposition | Test BRs |", "|---|---:|"]
    for key, count in counts.items():
        report.append("| " + key + " | " + str(count) + " |")
    report += ["", "Direct proposals are clause-scoped, not full-rule certification. Partial and conflict rows retain their unsupported claims explicitly. All Test chains remain draft.", ""]
    for row in review_rows:
        report += ["## " + row["Test_BR_ID"], row["Test_BR_Title"], "", "Disposition: **" + row["disposition"] + "**", "", row["rationale"], ""]
        for candidate in row["selectedAiBrs"]:
            report.append("- AI :" + candidate["aiBrId"].split(":")[-1] + ": " + candidate["aiStatement"].replace("\n", " ")
                          + " (rank " + str(candidate["rank"]) + "; AI cases " + str(candidate["caseCount"])
                          + "; physical-complete " + str(candidate["physicalCompleteCaseCount"])
                          + "; flags " + candidate["artifactFlags"] + ")")
        if not row["selectedAiBrs"]:
            report.append("No defensible AI counterpart selected; retained leads remain retrieval-only.")
        report += ["", "Test chain: " + str(row["testStatus"]) + "; TS/TC/TD " + str(row["testScenarioCount"]) + "/"
                   + str(row["testCaseCount"]) + "/" + str(row["testDataCount"]) + "; execution NOT_EXECUTED.", ""]
    report += ["## Key Source Resolution", "",
        "Section 11.4.1.2 contains a `Data Segment No. 116` label, but Section 12.17 describes Segment 119 and Element 85 identifies 116 as TransArmor. Per the source-precedence rule, the authoritative layout/table resolves the request segment as 119; AI :1040's 116 use is not counted as a match.",
        "", "The attached Authorization request is not evidence for Totals, PDL, Moneris, EMV response, Tax-by-Product, Fuel Price Update or Adjusted Product Code flows. No card/track values were ingested.",
        "", "All proposed relations remain `NOT_ASSESSED`; the Test solution is independent and all 32 Test BRs remain `DRAFT_REVIEW_REQUIRED`. No SME approval or execution is claimed.", ""]
    native(output / "REPORT.md").write_text("\n".join(report), encoding="utf-8")
    artifacts = {path.name: sha256(path) for path in native(output).iterdir() if path.is_file()}
    native(output / "manifest.json").write_text(json.dumps({"algorithm": "SHA-256", "artifacts": artifacts}, indent=2) + "\n", encoding="utf-8")
    print(json.dumps({key: value for key, value in summary.items() if key not in ("inputSha256", "limitations")}, indent=2))


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--self-test", action="store_true")
    parser.add_argument("--output", type=Path, default=OUTPUT)
    args = parser.parse_args()
    if args.self_test:
        self_test()
    else:
        run(args.output)