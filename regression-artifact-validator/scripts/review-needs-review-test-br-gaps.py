"""Review the 16 Needs review Test BR gaps against the full AI BR catalog."""

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
SOURCE_PATH = ROOT / "specifications/ATL105/docs/specs/extracted_text.txt"
OUTPUT_DEFAULT = REVIEW / "2026-10-16-needs-review-test-br-gaps-v1"


RELATIONS = {
    "SEG100-R-012": ("CLAUSE_MATCH_PROPOSED", ["2358"], "AI :2358 states the same fixed TPDU Protocol ID 0x60. Exact value agrees with Appendix A; source and Test records remain review-required."),
    "SEG100-R-024": ("CLAUSE_MATCH_PROPOSED", ["2361"], "AI :2361 states that all four TPDU bytes are 0x00. This directly covers the reserved destination/source address clause."),
    "SEG100-R-066": ("CLAUSE_MATCH_PROPOSED", ["1867", "1900"], "AI :1867 states Response Code identifies financial approval/decline; :1900 gives S = Declined. This is Response Code 83, not Decline Code 26."),
    "SEG101-R-022": ("CLAUSE_MATCH_PROPOSED", ["1182"], "AI :1182 states the Auth Completion Fleet Tag is a 3-byte tag plus up to 31 data bytes; same format clause."),
    "SEG101-R-023": ("PARTIAL_SCOPE_OVERLAP", ["1183", "4847", "4848", "4849", "4850", "4851", "4852"], "AI :1183 gives the Auth Completion/Host Prompts condition, while six AI code entries cover only a subset of the source's 17 valid tags. The full enumeration is not matched."),
    "SEG101-R-024": ("PARTIAL_SCOPE_OVERLAP", ["1182", "4847", "4848", "4849", "4850", "4851", "4852"], "AI :1182 gives the shared generic 3+31 format; six per-code AI entries give partial payload maxima. Eleven source-listed tags and complete per-code formats are not established."),
    "SEG102-R-017": ("CLAUSE_MATCH_PROPOSED", ["1194"], "AI :1194 states all fuel merchants must send fuel and nonfuel product data, matching the core rule. :1565 adds an 'if applicable' qualifier and is not used as sole match evidence."),
    "SEG103-R-020": ("CLAUSE_MATCH_PROPOSED", ["741"], "AI :741 states eWIC specifications do not support Return transactions, matching the absolute prohibition."),
    "SEG109-R-019": ("CLAUSE_MATCH_PROPOSED", ["4755", "4756", "883"], "Composite AI clauses state retrieval Prompt Code 981, proprietary-data retrieval Prompt Code 996, and up to 750 bytes of print data per request. This direct composite was not retained in the target's top-10 list."),
    "SEG109-R-020": ("CLAUSE_MATCH_PROPOSED", ["4757", "885", "886"], "Composite AI clauses state submission Prompt Code 995, up to 217 bytes, and confirmation response. Prompt Code :4757 falls outside the retained top-10 list."),
    "SEG119-R-035": ("COUNTERPART_NOT_ESTABLISHED", [], "AI leads describe operational cut times and settlement timing, not approval of a merchant settlement cutoff/time-zone policy before certification. Keep this as an open policy prerequisite; not an adjudicated NOT_MATCH."),
    "SEG145-R-004": ("CLAUSE_MATCH_PROPOSED_CONTEXT_REVIEW", ["1409", "2352", "2663"], "AI statements mirror the June 2026 availability notice. This is a specification-era statement; current production enablement after June 2026 must be confirmed before applying it operationally."),
    "SEG155-R-005": ("CLAUSE_MATCH_PROPOSED", [str(value) for value in range(1510, 1529)], "AI :1510-:1513 cover Table ID/length, response gating and Visa scope; :1514-:1528 enumerate VAU001-014 and VAU016. The source omits VAU015; all listed values are represented."),
    "SEG156-R-006": ("CLAUSE_MATCH_PROPOSED", [str(value) for value in range(4982, 4990)], "Eight AI entries enumerate all source connector values 001-003, 100-103 and 200."),
    "SEGDL3-R-008": ("PARTIAL_SCOPE_OVERLAP", ["1089", "1090", "1091"], "AI clauses cover load type D, device initiation/no merchant flag, and timezone/DST adjustment. The explicit '?' + Terminal Identifier + 'D' request serialization is not established by these AI BRs."),
    "SEGDL4-R-007": ("PARTIAL_SCOPE_OVERLAP", ["873", "874", "875", "876", "879", "1095", "1101", "1102"], "AI clauses cover profile flag/download indicator, table-load response segments, stored settings, scheduled full load and failure message. The complete post-download table-load sequence without the profile bit and retry bound are not all independently stated."),
}


SOURCE_RANGES = {
    "TPDU_HEADER": (25525, 25565),
    "FLEET_TAGS": (11490, 11555),
    "FUEL_NONFUEL": (11575, 11594),
    "EWIC_NO_RETURN": (5280, 5295),
    "EMAIL_RETRIEVAL_SUBMISSION": (6205, 6238),
    "SOFTWARE_UPDATE_SEQUENCE": (6160, 6200),
    "MASTER_CARD_AVAILABILITY": (15198, 15212),
    "ACCOUNT_UPDATER_RESULT_ENUMERATION": (16570, 16623),
    "EV_CONNECTOR_ENUMERATION": (16860, 16892),
    "DATE_TIME_LOAD": (9345, 9390),
    "RESPONSE_CODE_83": (21183, 21215),
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
    return {
        "businessRequirement": {"id": br["id"], "title": br["title"], "status": br.get("status"),
                                "sourceAnchors": br.get("sourceAnchors") or []},
        "scenarios": [{key: row.get(key) for key in ("id", "requirementIds", "sourceAnchors", "status")} for row in scenarios],
        "cases": [{key: row.get(key) for key in ("id", "scenarioIds", "testDataIds", "testDataId", "sourceAnchors", "status")} for row in cases],
        "testData": [{key: row.get(key) for key in ("id", "testCaseIds", "testCaseId", "readiness", "fixtureKind", "sourceAnchors")} for row in data],
        "payloadValuesExported": False,
    }


def write_csv(path, rows):
    rows = list(rows)
    if not rows:
        raise ValueError("Refusing to write an empty CSV: " + path.name)
    fields = list(dict.fromkeys(key for row in rows for key in row))
    with native(path).open("w", encoding="utf-8", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=fields)
        writer.writeheader()
        for row in rows:
            writer.writerow({key: json.dumps(value, ensure_ascii=True) if isinstance(value, (list, dict)) else value
                             for key, value in row.items()})


def write_jsonl(path, rows):
    with native(path).open("w", encoding="utf-8", newline="\n") as stream:
        for row in rows:
            stream.write(json.dumps(row, ensure_ascii=True, sort_keys=True) + "\n")


def source_excerpts():
    lines = native(SOURCE_PATH).read_text(encoding="utf-8-sig").splitlines()
    result = []
    for identifier, (start, end) in SOURCE_RANGES.items():
        result.append({"evidenceId": identifier, "source": str(SOURCE_PATH), "startLine": start,
                       "endLine": end, "quote": "\n".join(lines[start - 1:end]),
                       "interpretationStatus": "REVIEW_REQUIRED"})
    return result


def verify_inputs():
    queue_summary = read_json(QUEUE / "summary.json")
    queue_manifest = read_json(QUEUE / "manifest.json")
    screen_manifest = read_json(QUEUE / "automated-chain-screen-v2/manifest.json")
    gap_manifest = read_json(GAP_DIR / "manifest.json")
    paths = [
        (QUEUE / name, queue_manifest["artifacts"][name])
        for name in ("all-ai-br-review-register.csv", "ai-test-br-ranked-review-leads.csv", "ai-br-chain-evidence.jsonl.gz")
    ]
    paths += [
        (QUEUE / "automated-chain-screen-v2/all-br-chain-screen.csv", screen_manifest["artifacts"]["all-br-chain-screen.csv"]),
        (GAP_DIR / "test-brs-without-ai-br-candidates.csv", gap_manifest["outputSha256"]["test-brs-without-ai-br-candidates.csv"]),
    ]
    for path, expected in paths:
        if sha256(path) != expected:
            raise ValueError("Input artifact hash mismatch: " + path.name)
    if sha256(PACKAGE_PATH) != queue_summary["inputSha256"][str(PACKAGE_PATH)]:
        raise ValueError("Current Test package differs from the frozen retrieval input")


def self_test():
    targets = {"SEG100-R-012", "SEG100-R-024", "SEG100-R-066", "SEG101-R-022", "SEG101-R-023", "SEG101-R-024",
               "SEG102-R-017", "SEG103-R-020", "SEG109-R-019", "SEG109-R-020", "SEG119-R-035", "SEG145-R-004",
               "SEG155-R-005", "SEG156-R-006", "SEGDL3-R-008", "SEGDL4-R-007"}
    assert set(RELATIONS) == targets and len(RELATIONS) == 16
    assert len({identifier for _, identifiers, _ in RELATIONS.values() for identifier in identifiers}) >= 60
    assert RELATIONS["SEG119-R-035"][0] == "COUNTERPART_NOT_ESTABLISHED"
    assert RELATIONS["SEG101-R-023"][0] == "PARTIAL_SCOPE_OVERLAP"
    assert len([row for row in RELATIONS.values() if row[0] == "CLAUSE_MATCH_PROPOSED"]) == 10
    assert len([row for row in RELATIONS.values() if row[0] == "CLAUSE_MATCH_PROPOSED_CONTEXT_REVIEW"]) == 1
    print("PASS: all 16 Test gaps have explicit match/partial/open dispositions")


def run(output):
    if native(output).exists():
        raise ValueError("Refusing to overwrite output directory")
    verify_inputs()
    missing_rows = [row for row in read_csv(GAP_DIR / "test-brs-without-ai-br-candidates.csv") if row["Category"] == "Needs review"]
    if len(missing_rows) != 16 or {row["Test_BR_ID"] for row in missing_rows} != set(RELATIONS):
        raise ValueError("Needs review category population differs from the frozen 16-row cohort")
    package = read_json(PACKAGE_PATH)
    business_requirements = {row["id"]: row for row in package["businessRequirements"]}
    if len(business_requirements) != 1330:
        raise ValueError("Expected the full 1,330-record independent Test package")
    test_chains = {test_id: test_chain(package, business_requirements["BR-RULE-" + test_id]) for test_id in RELATIONS}
    register = {row["aiBrId"]: row for row in read_csv(QUEUE / "all-ai-br-review-register.csv")}
    if len(register) != 6880:
        raise ValueError("Expected 6,880 unique AI BR records")
    by_suffix = {identifier.rsplit(":", 1)[-1]: identifier for identifier in register}
    ai_chains = {row["aiBrId"]: row for row in read_lines(QUEUE / "ai-br-chain-evidence.jsonl.gz")}
    if len(ai_chains) != 6880:
        raise ValueError("Expected AI chain evidence for all 6,880 requirements")
    screens = {row["aiBrId"]: row for row in read_csv(QUEUE / "automated-chain-screen-v2/all-br-chain-screen.csv")}
    candidates = [row for row in read_csv(QUEUE / "ai-test-br-ranked-review-leads.csv") if row["testBrId"] in RELATIONS]
    leads_by_test = collections.defaultdict(list)
    lead_by_pair = {}
    for candidate in candidates:
        leads_by_test[candidate["testBrId"]].append(candidate)
        lead_by_pair[(candidate["testBrId"], candidate["aiBrId"])]=candidate
    for rows in leads_by_test.values():
        rows.sort(key=lambda row: int(row["candidateRank"]))

    disposition_rows = []
    relation_rows = []
    selected_ai_ids = set()
    for gap in missing_rows:
        test_id = gap["Test_BR_ID"]
        disposition, suffixes, rationale = RELATIONS[test_id]
        test_br = business_requirements["BR-RULE-" + test_id]
        selected = []
        for suffix in suffixes:
            ai_id = by_suffix.get(suffix)
            if ai_id is None:
                raise ValueError("Selected AI BR is not in the full register: " + suffix)
            selected_ai_ids.add(ai_id)
            ai = register[ai_id]
            chain = ai_chains[ai_id]
            screen = screens[ai_id]
            lead = lead_by_pair.get((test_id, ai_id))
            selected.append({"aiBrId": ai_id, "statement": ai["aiStatement"],
                             "retainedTopKRank": lead.get("candidateRank") if lead else "NOT_RETAINED_IN_TOP_K",
                             "retrievalScore": lead.get("retrievalScore") if lead else "",
                             "chainStatus": chain["chainStatus"], "scenarioCount": len(chain["scenarios"]),
                             "caseCount": len(chain["cases"]), "physicalCompleteCaseCount": len(chain["physicalCompleteCaseIdsFromV6"]),
                             "artifactScreenFlags": screen["reviewFlags"], "artifactScreenStatus": screen["automatedScreeningStatus"]})
            relation_rows.append({"testBrId": test_id, "testPackageBrId": test_br["id"], "aiBrId": ai_id,
                                  "aiStatement": ai["aiStatement"], "relation": disposition, "rationale": rationale,
                                  "retainedTopKRank": lead.get("candidateRank") if lead else "NOT_RETAINED_IN_TOP_K",
                                  "retrievalScore": lead.get("retrievalScore") if lead else "",
                                  "aiCaseCount": len(chain["cases"]), "aiPhysicalCompleteCaseCount": len(chain["physicalCompleteCaseIdsFromV6"]),
                                  "aiArtifactScreenFlags": screen["reviewFlags"], "testStatus": test_br.get("status"),
                                  "semanticStatus": "NOT_ASSESSED", "testExecution": "NOT_EXECUTED"})
        disposition_rows.append({**gap, "disposition": disposition, "proposedAiBrs": selected,
                                 "retainedRankedLeadCount": len(leads_by_test[test_id]),
                                 "testStatus": test_br.get("status"), "testScenarioCount": len(test_chains[test_id]["scenarios"]),
                                 "testCaseCount": len(test_chains[test_id]["cases"]), "testDataCount": len(test_chains[test_id]["testData"]),
                                 "rationale": rationale, "semanticStatus": "NOT_ASSESSED", "testExecution": "NOT_EXECUTED"})

    target_ids = set(RELATIONS)
    top_ai_ids = {row["aiBrId"] for test_id in target_ids for row in leads_by_test[test_id][:3]}
    evidence_ids = selected_ai_ids | top_ai_ids
    output.mkdir(parents=True)
    write_csv(output / "needs-review-test-brs.csv", disposition_rows)
    write_csv(output / "proposed-relations.csv", relation_rows)
    write_csv(output / "retained-ranked-leads.csv", candidates)
    write_jsonl(output / "test-chain-evidence.jsonl", (test_chains[test_id] for test_id in sorted(test_chains)))
    write_jsonl(output / "ai-chain-evidence.jsonl", (ai_chains[ai_id] for ai_id in sorted(evidence_ids)))
    write_jsonl(output / "source-excerpts.jsonl", source_excerpts())
    (native(output / ".gitattributes")).write_text("* -text whitespace=cr-at-eol\n", encoding="ascii")

    statuses = collections.Counter(row["disposition"] for row in disposition_rows)
    summary = {
        "category": "Needs review",
        "populationMeaning": "0 AI BRs with v3 source-anchor candidates; 16 independent Test BRs absent from that candidate index",
        "aiBusinessRequirementsSearched": len(register), "testBusinessRequirementsSearched": len(business_requirements),
        "testGapRows": len(disposition_rows), "retainedRankedLeadPairs": len(candidates),
        "testTargetsWithRetainedLeads": sum(bool(leads_by_test[test_id]) for test_id in target_ids),
        "proposedRelations": len(relation_rows), "distinctProposedAiBrs": len(selected_ai_ids),
        "dispositions": dict(statuses),
        "proposedClauseMatchTestBrs": sum(row["disposition"] == "CLAUSE_MATCH_PROPOSED" for row in disposition_rows),
        "partialTestBrs": sum(row["disposition"] == "PARTIAL_SCOPE_OVERLAP" for row in disposition_rows),
        "unresolvedTestBrs": sum(row["disposition"] == "COUNTERPART_NOT_ESTABLISHED" for row in disposition_rows),
        "testChainsWithDraftStatus": sum(row["testStatus"] == "DRAFT_REVIEW_REQUIRED" for row in disposition_rows),
        "confirmedSemanticMatches": 0, "testExecutions": 0,
        "inputSha256": {str(path): sha256(path) for path in [SCRIPT, GAP_DIR / "test-brs-without-ai-br-candidates.csv",
            QUEUE / "all-ai-br-review-register.csv", QUEUE / "ai-test-br-ranked-review-leads.csv",
            QUEUE / "ai-br-chain-evidence.jsonl.gz", QUEUE / "automated-chain-screen-v2/all-br-chain-screen.csv",
            PACKAGE_PATH, SOURCE_PATH]},
        "limitations": [
            "v3 candidate absence is an exact-anchor-index result, not proof of semantic non-match.",
            "Retained leads are top-K retrieval only; direct phrase matches outside top-K are labeled separately.",
            "Composite Test BRs may be matched by multiple narrower AI BRs; no full-coverage credit is assigned.",
            "Test chains are structural and draft; no Test fixture, wire, or host execution occurred.",
            "The attached Authorization request is a single Financial Request sample and was not used to adjudicate unrelated email, eWIC, header, settlement, or load behavior.",
        ],
    }
    native(output / "summary.json").write_text(json.dumps(summary, indent=2) + "\n", encoding="utf-8")
    report = ["# Needs Review: 16 Test BR Gap Review", "",
        "The category count `0 / 16` means zero AI BRs had a v3 exact-anchor candidate and 16 Test BRs had no AI candidate in that index. This review searches the full 6,880-BR AI register and the full 1,330-record independent Test package.",
        "", "## Disposition Summary", "", "| Result | Test BRs |", "|---|---:|"]
    for status, count in statuses.items():
        report.append("| " + status + " | " + str(count) + " |")
    report += ["", "A proposed clause match is not a confirmed full-BR match. Partial relations state what is still missing. Candidates outside top-10 were found by direct full-catalog wording review and are labeled as such.", ""]
    for row in disposition_rows:
        report += ["## " + row["Test_BR_ID"], row["Test_BR_Title"], "", "Disposition: **" + row["disposition"] + "**", "", row["rationale"], ""]
        if row["proposedAiBrs"]:
            for candidate in row["proposedAiBrs"]:
                report.append("- AI " + candidate["aiBrId"].split(":")[-1] + ": " + candidate["statement"].replace("\n", " ")
                              + " (retained rank " + str(candidate["retainedTopKRank"]) + ", cases " + str(candidate["caseCount"])
                              + ", physical-complete " + str(candidate["physicalCompleteCaseCount"])
                              + ", artifact flags " + candidate["artifactScreenFlags"] + ")")
        else:
            report.append("No AI BR selected as a defensible counterpart; ranked leads remain retrieval cues.")
        report += ["", "Test chain: status " + str(row["testStatus"]) + "; scenarios/cases/data "
                   + str(row["testScenarioCount"]) + "/" + str(row["testCaseCount"]) + "/" + str(row["testDataCount"])
                   + ". Test execution: NOT_EXECUTED.", ""]
    report += ["## Source and Review Notes", "",
        "Source excerpts in source-excerpts.jsonl are line-bound to ATL105 2026-3. The June 2026 availability clause is historical; current production enablement is not established. Settlement cutoff approval is a policy prerequisite, not an AI-described runtime rule.",
        "", "The provided Authorization.json is one transaction request. It does not carry the TCP/IP TPDU header or response, and cannot prove eWIC return prohibition, electronic mail limits, EV connector code coverage, settlement policy, or software/table-load lifecycle.",
        "", "All Test BRs remain DRAFT_REVIEW_REQUIRED. Source interpretation, full scenario/case intent, independent expected outcomes, fixture execution, wire checks and host execution remain separate gates. No semantic approval or execution is inferred.",
        "", "## Evidence Files", "",
        "- `needs-review-test-brs.csv`: all 16 Test gaps and dispositions.",
        "- `proposed-relations.csv`: each proposed AI/Test relation, retained rank, chain and artifact flags.",
        "- `retained-ranked-leads.csv`: every retained top-K pair for the 16 targets.",
        "- `ai-chain-evidence.jsonl`: selected candidate chains and top-three retrieval chains.",
        "- `test-chain-evidence.jsonl`: sanitized Test chains; payload values excluded.",
        "- `source-excerpts.jsonl`: source windows for direct comparison.", ""]
    native(output / "REPORT.md").write_text("\n".join(report), encoding="utf-8")
    artifact_hashes = {path.name: sha256(path) for path in native(output).iterdir() if path.is_file()}
    native(output / "manifest.json").write_text(json.dumps({"algorithm": "SHA-256", "artifacts": artifact_hashes}, indent=2) + "\n", encoding="utf-8")
    print(json.dumps({key: value for key, value in summary.items() if key not in ("inputSha256", "limitations")}, indent=2))


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--self-test", action="store_true")
    parser.add_argument("--output", type=Path, default=OUTPUT_DEFAULT)
    args = parser.parse_args()
    if args.self_test:
        self_test()
    else:
        run(args.output)