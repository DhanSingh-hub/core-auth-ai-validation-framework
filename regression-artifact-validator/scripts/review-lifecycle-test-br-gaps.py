"""Review Lifecycle Test BR gaps against the full AI catalog and source."""

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
GAPS = REVIEW / "2026-10-15-unmatched-ai-test-br-category-v1"
QUEUE = REVIEW / "2026-10-16-all-ai-br-chain-test-br-review-v2"
SPEC_PATH = ROOT / "specifications/ATL105/docs/specs/extracted_text.txt"
OUTPUT = REVIEW / "2026-10-16-lifecycle-test-br-gaps-v1"


RELATIONS = {
    "SEG100-R-023": ("PARTIAL_SCOPE_OVERLAP", ["517", "518", "607", "608", "1679", "1939", "1940"], "AI clauses cover same-value persistence for Approval Number, Sequence Number, Prompt Code, and specific reversal/completion contexts. Test R-023 is a broader lifecycle rule; exact applicability and the complete required-reference set are not established by one atomic AI rule."),
    "SEG100-R-063": ("CLAUSE_MATCH_PROPOSED", ["1873", "1877"], "AI :1873 and :1877 state approved Purchase/Capture response codes 0 and 4. These are the two source-defined approved Purchase/Capture values; Response Code family context remains explicit."),
    "SEG100-R-064": ("CLAUSE_MATCH_PROPOSED", ["1875", "1876"], "AI :1875 and :1876 state approved Authorization Only values 2 and 3 (with AVS). This is clause-level enumeration overlap, not response execution."),
    "SEG101-R-007": ("PARTIAL_SCOPE_OVERLAP", ["1182", "1183", "5877"], "AI states Fleet Tag fields 14-18 are Auth Completion-only and gives the tag/data shape, but does not establish the complete Section 12.2 order of fields 1-13 followed by fields 14-18."),
    "SEG101-R-020": ("CLAUSE_MATCH_PROPOSED", ["1182", "1183", "5877"], "AI :1182 and :1183 state Fleet Tags are sent only in Auth Completion (0220); :1183 adds the source condition that Host Prompts are supported."),
    "SEG103-R-024": ("PARTIAL_SCOPE_OVERLAP", ["741", "726", "752"], "AI :741 supports the eWIC Return prohibition, and :726 concerns Food Stamp Electronic Voucher. These do not establish the full Required/Optional/Prohibited Segment 103 applicability matrix across all listed EBT/eWIC flows."),
    "SEG130-R-001": ("PARTIAL_SCOPE_OVERLAP", ["1133", "1348", "979"], "AI :1133 states EMV request Segment 130 requirement and :979 states EMV data is not required on reversals. Together they cover the major obligation/exception, but Test's authorization-class applicability and exact Section 11.8.1 context still need clause-level review."),
    "SEG130-R-017": ("CLAUSE_MATCH_PROPOSED", ["979"], "AI :979 states EMV data is not required on Reversal transactions, matching the source-defined reversal exception."),
    "SEG130-R-020": ("PARTIAL_SCOPE_OVERLAP", ["3086", "3087", "3088", "5836", "5837"], "AI clauses cover EMVYES/EMVNOT request use and response echo. The reviewed AI clauses do not establish the complete Table ID 001 / fixed six-character length assertion, so keep the composite rule partial."),
    "SEG130-R-023": ("PARTIAL_SCOPE_OVERLAP", ["1351", "1352", "1354", "3455"], "AI covers Segment 131 max length 3834, no separators, 2800-byte additional-information cap, and required CA checksum. The Test rule's explicit no-EMV-Card-Sequence-Number assertion and whole-parser non-reuse conclusion are not fully matched."),
}


SOURCE_RANGES = {
    "RESPONSE_CODE_83": (21186, 21262),
    "FLEET_DATA_ORDER_AND_TAGS": (11396, 11555),
    "EBT_FOOD_VOUCHER_CONTEXT": (5140, 5225),
    "EWIC_RETURN_AND_COMPLETION": (5280, 5345),
    "EMV_REQUEST_LAYOUT": (9960, 10015),
    "EMV_REVERSAL_EXCEPTION": (7335, 7355),
    "EMV_REQUEST_SEGMENT_130": (14012, 14115),
    "EMV_RESPONSE_SEGMENT_131": (14145, 14230),
    "APPENDIX_T_EMV_TABLE_DATA": (35562, 35598),
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
    result = set(row.get(plural) or [])
    if isinstance(row.get(plural), str):
        result = {row[plural]}
    if row.get(singular):
        result.add(row[singular])
    return result


def test_chain(package, br):
    scenarios = [row for row in package["testScenarios"] if br["id"] in refs(row, "requirementIds", "requirementId")]
    scenario_ids = {row["id"] for row in scenarios}
    cases = [row for row in package["testCases"] if refs(row, "scenarioIds", "scenarioId") & scenario_ids]
    case_ids = {row["id"] for row in cases}
    data = [row for row in package["testData"] if refs(row, "testCaseIds", "testCaseId") & case_ids]
    return {"brId": br["id"], "status": br.get("status"), "scenarioIds": [row["id"] for row in scenarios],
            "scenarioStatuses": [row.get("status") for row in scenarios], "caseIds": [row["id"] for row in cases],
            "caseStatuses": [row.get("status") for row in cases], "testDataIds": [row["id"] for row in data],
            "testDataReadiness": [row.get("readiness") for row in data], "payloadValuesExported": False}


def draft_chain_count(chains):
    return sum(chain["br"].get("status") == "DRAFT_REVIEW_REQUIRED" for chain in chains.values())


def write_csv(path, rows):
    rows = list(rows)
    if not rows:
        raise ValueError("Cannot write empty CSV")
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
    assert len(RELATIONS) == 10
    assert len([value for value in RELATIONS.values() if value[0] == "CLAUSE_MATCH_PROPOSED"]) == 4
    assert RELATIONS["SEG130-R-017"][1] == ["979"]
    assert RELATIONS["SEG101-R-007"][0] == "PARTIAL_SCOPE_OVERLAP"
    assert draft_chain_count({"a": {"br": {"status": "DRAFT_REVIEW_REQUIRED"}},
                              "b": {"br": {"status": "REVIEW_REQUIRED"}}}) == 1
    print("PASS: ten Lifecycle targets keep direct and partial relations separated")


def run(output):
    if native(output).exists():
        raise ValueError("Refusing to overwrite existing Lifecycle review")
    gap_manifest = read_json(GAPS / "manifest.json")
    queue_manifest = read_json(QUEUE / "manifest.json")
    screen_manifest = read_json(QUEUE / "automated-chain-screen-v2/manifest.json")
    inputs_to_check = [
        (GAPS / "test-brs-without-ai-br-candidates.csv", gap_manifest["outputSha256"]["test-brs-without-ai-br-candidates.csv"]),
        (QUEUE / "all-ai-br-review-register.csv", queue_manifest["artifacts"]["all-ai-br-review-register.csv"]),
        (QUEUE / "ai-test-br-ranked-review-leads.csv", queue_manifest["artifacts"]["ai-test-br-ranked-review-leads.csv"]),
        (QUEUE / "ai-br-chain-evidence.jsonl.gz", queue_manifest["artifacts"]["ai-br-chain-evidence.jsonl.gz"]),
        (QUEUE / "test-chain-evidence.jsonl.gz", queue_manifest["artifacts"]["test-chain-evidence.jsonl.gz"]),
        (QUEUE / "automated-chain-screen-v2/all-br-chain-screen.csv", screen_manifest["artifacts"]["all-br-chain-screen.csv"]),
    ]
    for path, expected in inputs_to_check:
        if sha256(path) != expected:
            raise ValueError("Frozen review input hash mismatch: " + path.name)
    summary = read_json(QUEUE / "summary.json")
    gaps = [row for row in read_csv(GAPS / "test-brs-without-ai-br-candidates.csv") if row["Category"] == "Lifecycle"]
    if len(gaps) != 10 or {row["Test_BR_ID"] for row in gaps} != set(RELATIONS):
        raise ValueError("Lifecycle population differs from the frozen 10-row list")
    ai_brs = {row["aiBrId"]: row for row in read_csv(QUEUE / "all-ai-br-review-register.csv")}
    ai_by_suffix = {key.rsplit(":", 1)[-1]: key for key in ai_brs}
    ai_chains = {row["aiBrId"]: row for row in read_lines(QUEUE / "ai-br-chain-evidence.jsonl.gz")}
    screens = {row["aiBrId"]: row for row in read_csv(QUEUE / "automated-chain-screen-v2/all-br-chain-screen.csv")}
    candidates = [row for row in read_csv(QUEUE / "ai-test-br-ranked-review-leads.csv") if row["testBrId"] in RELATIONS]
    leads = collections.defaultdict(list)
    by_pair = {}
    for row in candidates:
        leads[row["testBrId"]].append(row)
        by_pair[(row["testBrId"], row["aiBrId"])] = row
    for items in leads.values():
        items.sort(key=lambda row: int(row["candidateRank"]))
    test_chains = {row["testBrId"].removeprefix("BR-RULE-"): row for row in
                   read_lines(QUEUE / "test-chain-evidence.jsonl.gz") if row["testBrId"].removeprefix("BR-RULE-") in RELATIONS}
    if set(test_chains) != set(RELATIONS):
        raise ValueError("Frozen Test-chain evidence does not cover all Lifecycle gap BRs")
    review_rows, relation_rows, selected_ids = [], [], set()
    for gap in gaps:
        rule = gap["Test_BR_ID"]
        disposition, suffixes, rationale = RELATIONS[rule]
        selected = []
        for suffix in suffixes:
            ai_id = ai_by_suffix.get(suffix)
            if not ai_id:
                raise ValueError("AI BR is absent from the full register: " + suffix)
            selected_ids.add(ai_id)
            ai, chain, screen = ai_brs[ai_id], ai_chains[ai_id], screens[ai_id]
            candidate = by_pair.get((rule, ai_id))
            selected.append({"aiBrId": ai_id, "statement": ai["aiStatement"],
                             "rank": candidate.get("candidateRank") if candidate else "NOT_RETAINED_IN_TOP_K",
                             "retrievalScore": candidate.get("retrievalScore") if candidate else "",
                             "scenarioCount": len(chain["scenarios"]), "caseCount": len(chain["cases"]),
                             "physicalCompleteCaseCount": len(chain["physicalCompleteCaseIdsFromV6"]),
                             "artifactFlags": screen["reviewFlags"]})
            relation_rows.append({"testBrId": rule, "aiBrId": ai_id, "relation": disposition,
                                  "aiStatement": ai["aiStatement"], "rationale": rationale,
                                  "rank": selected[-1]["rank"], "retrievalScore": selected[-1]["retrievalScore"],
                                  "aiCaseCount": selected[-1]["caseCount"],
                                  "aiPhysicalCompleteCaseCount": selected[-1]["physicalCompleteCaseCount"],
                                  "aiArtifactFlags": selected[-1]["artifactFlags"],
                                  "testStatus": test_chains[rule]["br"].get("status"),
                                  "semanticStatus": "NOT_ASSESSED", "executionStatus": "NOT_EXECUTED"})
        chain = test_chains[rule]
        review_rows.append({**gap, "disposition": disposition, "selectedAiBrs": selected,
                            "retainedLeadCount": len(leads[rule]), "rationale": rationale,
                    "testStatus": chain["br"].get("status"), "scenarioCount": len(chain["scenarios"]),
                    "caseCount": len(chain["cases"]), "testDataCount": len(chain["data"]),
                            "semanticStatus": "NOT_ASSESSED", "executionStatus": "NOT_EXECUTED"})
    top_ids = {row["aiBrId"] for rule in RELATIONS for row in leads[rule][:3]}
    source_lines = native(SPEC_PATH).read_text(encoding="utf-8-sig").splitlines()
    source_hash = sha256(SPEC_PATH)
    excerpts = [{"evidenceId": key, "source": str(SPEC_PATH), "sha256": source_hash,
                 "startLine": start, "endLine": end, "quote": "\n".join(source_lines[start - 1:end]),
                 "interpretationStatus": "REVIEW_REQUIRED"}
                for key, (start, end) in SOURCE_RANGES.items()]
    output.mkdir(parents=True)
    write_csv(output / "lifecycle-test-brs.csv", review_rows)
    write_csv(output / "proposed-relations.csv", relation_rows)
    write_csv(output / "retained-ranked-leads.csv", candidates)
    write_jsonl(output / "test-chain-evidence.jsonl", (test_chains[key] for key in sorted(test_chains)))
    write_jsonl(output / "ai-chain-evidence.jsonl", (ai_chains[key] for key in sorted(selected_ids | top_ids)))
    write_jsonl(output / "source-excerpts.jsonl", excerpts)
    native(output / ".gitattributes").write_text("* -text whitespace=cr-at-eol\n", encoding="ascii")
    counts = collections.Counter(row["disposition"] for row in review_rows)
    report = ["# Lifecycle Category: 10 Test BR Gap Review", "",
              "All 6,880 AI BRs and 1,330 independent Test BRs were searched. These ten Test BRs lack a v3 exact-anchor candidate; absence is not semantic non-match.",
              "", "## Dispositions", "", "| Disposition | Test BRs |", "|---|---:|"]
    for name, count in counts.items():
        report.append("| " + name + " | " + str(count) + " |")
    report += ["", "## Rule Review", ""]
    for row in review_rows:
        report += ["### " + row["Test_BR_ID"], row["Test_BR_Title"], "", "Disposition: **" + row["disposition"] + "**", "", row["rationale"], ""]
        for candidate in row["selectedAiBrs"]:
            report.append("- AI :" + candidate["aiBrId"].rsplit(":", 1)[-1] + ": "
                          + candidate["statement"].replace("\n", " ") + " (rank " + str(candidate["rank"])
                          + "; cases " + str(candidate["caseCount"]) + "; physical complete "
                          + str(candidate["physicalCompleteCaseCount"]) + "; flags " + candidate["artifactFlags"] + ")")
        if not row["selectedAiBrs"]:
            report.append("No AI clause selected as a defensible counterpart; leads remain retrieval-only.")
        report += ["", "Test chain: " + str(row["testStatus"]) + "; TS/TC/TD " + str(row["scenarioCount"])
                   + "/" + str(row["caseCount"]) + "/" + str(row["testDataCount"]) + "; execution NOT_EXECUTED.", ""]
    report += ["## Source Notes", "",
        "Section 11.4.1.2 narrative says Segment 116, but its layout table says 119; Section 12.17 and Element 85 identify 119 as Totals-with-Proprietary-Data and 116 as TransArmor. Source precedence resolves the runtime segment as 119 while retaining the editorial conflict.",
        "", "The EMV Table Data case is partial because the AI clauses cover values/echo but not the full Table ID/length claim. The Segment 131 response rule is partial because no reviewed AI statement covers the absence of an EMV Card Sequence Number field.",
        "", "All relations remain NOT_ASSESSED; Test BRs and their placeholder chains remain DRAFT_REVIEW_REQUIRED. No SME decisions, fixtures, wire checks or host execution are claimed.", ""]
    native(output / "REPORT.md").write_text("\n".join(report), encoding="utf-8")
    summary = {"category": "Lifecycle", "populationMeaning": "10 independent Test BRs absent from v3 source-anchor index",
               "aiBrsSearched": len(ai_brs), "testBrsSearched": summary["population"]["testBusinessRequirements"], "testGapRows": len(review_rows),
               "retainedRankedLeadPairs": len(candidates), "targetsWithRetainedLeads": sum(bool(leads[key]) for key in RELATIONS),
               "selectedRelationRows": len(relation_rows), "selectedDistinctAiBrs": len(selected_ids),
               "dispositions": dict(counts), "testChainsDraft": draft_chain_count(test_chains),
               "semanticApprovals": 0, "testExecutions": 0,
               "inputSha256": {str(path): sha256(path) for path in [SCRIPT, GAPS / "test-brs-without-ai-br-candidates.csv",
                   QUEUE / "all-ai-br-review-register.csv", QUEUE / "ai-test-br-ranked-review-leads.csv",
                   QUEUE / "ai-br-chain-evidence.jsonl.gz", QUEUE / "test-chain-evidence.jsonl.gz", SPEC_PATH]},
               "limitations": ["Candidate-index absence is not semantic non-match.",
                               "Ranked pairs are retrieval aids; selected links are clause proposals, not full BR coverage.",
                               "All Test chains remain drafts and no execution occurred.",
                               "Response-family, message-context, and lifecycle source interpretation remains separately reviewable."]}
    native(output / "summary.json").write_text(json.dumps(summary, indent=2) + "\n", encoding="utf-8")
    artifacts = {path.name: sha256(path) for path in native(output).iterdir() if path.is_file()}
    native(output / "manifest.json").write_text(json.dumps({"algorithm": "SHA-256", "artifacts": artifacts}, indent=2) + "\n", encoding="utf-8")
    print(json.dumps({key: value for key, value in summary.items() if key not in {"inputSha256", "limitations"}}, indent=2))


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--self-test", action="store_true")
    parser.add_argument("--output", type=Path, default=OUTPUT)
    args = parser.parse_args()
    if args.self_test:
        self_test()
    else:
        run(args.output)