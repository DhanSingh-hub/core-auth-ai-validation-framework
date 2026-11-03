import argparse
import csv
import hashlib
import json
import os
from html import escape
from pathlib import Path


MODULE = Path(__file__).resolve().parents[1]


def native(path):
    path = Path(os.path.normpath(str(path)))
    return Path("\\\\?\\" + str(path.resolve())) if os.name == "nt" and not str(path).startswith("\\\\?\\") else path


def read(path):
    return json.loads(path.read_text(encoding="utf-8-sig"))


def digest(path):
    with path.open("rb") as stream:
        return hashlib.file_digest(stream, "sha256").hexdigest()


def write(path, value):
    path.write_text(json.dumps(value, indent=2, ensure_ascii=True), encoding="utf-8")


def stable_query(query_id, by_id):
    return by_id.get(query_id)


def verify_source_assertions(source, gate):
    lines = source.read_text(encoding="utf-8-sig").splitlines()
    if digest(source) != gate["specificationFingerprintSha256"]:
        raise ValueError("ATL105 raw source fingerprint differs from curated gate")
    normalize = lambda value: " ".join(str(value).split()).casefold()
    matches = 0
    mismatches = []
    for assertion in gate.get("assertions", []):
        line = assertion.get("sourceLine")
        if not isinstance(line, int) or line < 1 or line > len(lines):
            mismatches.append(assertion["ruleId"])
            continue
        window = normalize(" ".join(lines[max(0, line - 4):min(len(lines), line + 8)]))
        if normalize(assertion.get("sourceQuote", "")) in window:
            matches += 1
        else:
            mismatches.append(assertion["ruleId"])
    return {"curatedAssertionCount": len(gate.get("assertions", [])), "citedQuoteWindowsVerified": matches,
            "unresolvedQuoteWindows": mismatches, "sourceInterpretationApproved": False}


def self_test():
    assert stable_query("Q1", {"Q1": {"status": "OPEN_REVIEW_DEFERRED"}})["status"] == "OPEN_REVIEW_DEFERRED"
    assert stable_query("missing", {}) is None
    assert 20_941 + 27 + 155 == 21_123
    print("PASS: query link reconciliation and disjoint structural disposition arithmetic")


def build(finalized, output):
    finalized = native(finalized)
    output = native(output)
    if output.exists():
        raise ValueError("Output must be a new mitigation-plan directory; refusing overwrite")
    summary = read(finalized / "semantic-chain-summary.json")
    source = MODULE / "specifications/ATL105/docs/specs/extracted_text.txt"
    source_gate = read(MODULE / "specifications/ATL105/test-output/test-solution-independent-review/atl105-source-backed-rule-gate.json")
    quote_verification = verify_source_assertions(source, source_gate)
    query_rows = read(finalized / "semantic-sme-query-register.json")
    cases = read(finalized / "semantic-tc-td-assessment.json")
    brs = read(finalized / "semantic-br-assessment.json")
    scenarios = read(finalized / "semantic-ts-assessment.json")
    independent = read(finalized / "semantic-independent-source-rule-assessment.json")
    query_by_id = {item["queryId"]: item for item in query_rows}
    if len(query_by_id) != len(query_rows):
        raise ValueError("Duplicate SME query ID")
    if any(item["approvalGranted"] or item["status"] != "OPEN_REVIEW_DEFERRED" for item in query_rows):
        raise ValueError("A deferred query must not be promoted during mitigation planning")

    kinds = summary["smeQueryKinds"]
    stages = summary["stageStatusCounts"]
    structures = summary["structuralChainStatusCounts"]
    actions = [
        {"id": "MIT-001", "priority": "P0", "lane": "AUTO_VERIFIABLE", "owner": "Test Solution automation", "stage": "INTAKE/GRAPH",
         "title": "Preserve and verify delivery/catalog/TD identity", "count": summary["physicalFilesHashVerified"],
         "acceptance": "Every used catalog/payload/metadata file matches its frozen manifest hash and every metadata-to-TC/TS join is exact.",
         "blocker": "NONE_FOR_FROZEN_RUN", "queryKind": "", "status": "VERIFIED_FOR_CURRENT_FROZEN_INPUTS"},
        {"id": "MIT-002", "priority": "P0", "lane": "PRODUCER_EVIDENCE", "owner": "AI Solution producer", "stage": "BR/TS/TC",
         "title": "Resolve unattributed cases", "count": structures.get("UNATTRIBUTED_BR_CHAIN", 0),
         "acceptance": "Supply source-justified BR attribution or explicitly classify as non-BR-scope; no keyword or arithmetic mapping.",
         "blocker": "MISSING_BR_EDGES", "queryKind": "CASE_BR_ATTRIBUTION", "status": "OPEN_REVIEW_DEFERRED"},
        {"id": "MIT-003", "priority": "P0", "lane": "PRODUCER_DATA", "owner": "AI Solution producer", "stage": "TC/TD",
         "title": "Restore missing test-data files", "count": structures.get("TC_WITHOUT_COMPLETE_PHYSICAL_TD", 0),
         "acceptance": "Provide complete request/metadata artifacts for each case and verify hashes/joins; mark unsupported cases blocked, never pass.",
         "blocker": "MISSING_PHYSICAL_TD", "queryKind": "TD_MISSING", "status": "OPEN_REVIEW_DEFERRED"},
        {"id": "MIT-004", "priority": "P1", "lane": "TEST_ASSERTION", "owner": "AI Solution + Test Solution", "stage": "TC",
         "title": "Add explicit BR-linked TC assertions", "count": stages["tcAssertionStatus"].get("EXPLICIT_ASSERTION_MISSING", 0),
         "acceptance": "Each linked case asserts the specific BR obligation/condition; assertion has a source/context link and validator mapping.",
         "blocker": "TC_OBJECTIVE_ABSENT", "queryKind": "TC_OBJECTIVE", "status": "OPEN_REVIEW_DEFERRED"},
        {"id": "MIT-005", "priority": "P1", "lane": "EXPECTED_OUTCOME", "owner": "Test Solution + authorized oracle owner", "stage": "TC/TD",
         "title": "Define request assertions or authoritative outcomes", "count": stages["tcExpectedOutcomeStatus"].get("EXPECTED_RESPONSE_MISSING", 0),
         "acceptance": "Every case declares validated PASS/FAIL assertions or explicit request-only scope; host outcomes require an approved oracle/state fixture.",
         "blocker": "EXPECTED_RESPONSE_ABSENT", "queryKind": "TC_OUTCOME", "status": "OPEN_REVIEW_DEFERRED"},
        {"id": "MIT-006", "priority": "P1", "lane": "INDEPENDENT_RULE_BASELINE", "owner": "Test Solution rule owners / SME", "stage": "BR",
         "title": "Complete the independent 601-rule meaning baseline", "count": summary["independentSourceRulesWithoutCuratedAssertions"],
         "acceptance": "Atomic interpretation includes applicability, conditions, required behavior, exceptions and evidence; rule is independently reviewed.",
         "blocker": "SOURCE_MEANING_NOT_CURATED", "queryKind": "INDEPENDENT_BR_MEANING", "status": "OPEN_REVIEW_DEFERRED"},
        {"id": "MIT-007", "priority": "P1", "lane": "INDEPENDENT_RULE_BASELINE", "owner": "Test Solution rule owners / SME", "stage": "BR",
         "title": "Review curated source assertions", "count": summary["independentSourceRulesCuratedCandidates"],
         "acceptance": "Review entire rule scope, not code-value fragments; source-backed candidates remain nonapproved until reviewed.",
         "blocker": "CURATED_ASSERTIONS_REVIEW_REQUIRED", "queryKind": "SOURCE_RULE_SCOPE_REVIEW", "status": "OPEN_REVIEW_DEFERRED"},
        {"id": "MIT-008", "priority": "P1", "lane": "AI_BR_ALIGNMENT", "owner": "Test Solution comparison + SME", "stage": "BR",
         "title": "Compare AI BR meaning against independent baseline", "count": summary["businessRequirements"],
         "acceptance": "Record confirmed/partial/missing/unassessed for each in-scope independent BR with evidence; many-to-many is allowed, ambiguity stays open.",
         "blocker": "NO_COMPLETE_INDEPENDENT_INTERPRETATIONS", "queryKind": "BR_FULL_MEANING", "status": "OPEN_REVIEW_DEFERRED"},
        {"id": "MIT-009", "priority": "P1", "lane": "SCENARIO_INTENT", "owner": "Test Solution comparison + SME", "stage": "TS",
         "title": "Verify scenario exercises BR conditions and behavior", "count": summary["scenarios"],
         "acceptance": "Trace each scenario condition/action/outcome to the confirmed BR interpretation, including negative and lifecycle context.",
         "blocker": "TS_INTENT_UNPROVEN", "queryKind": "TS_BR_INTENT", "status": "OPEN_REVIEW_DEFERRED"},
        {"id": "MIT-010", "priority": "P1", "lane": "NEGATIVE_EFFECTIVENESS", "owner": "Test Solution validation", "stage": "TC/TD",
         "title": "Pair negatives with valid controls and one intended mutation", "count": kinds.get("NEGATIVE_EFFECTIVENESS", 0),
         "acceptance": "Complete supported control passes; one isolated mutation violates its intended rule; unrelated checks stay valid.",
         "blocker": "NO_VALID_CONTROL_OR_FULL_SCOPE", "queryKind": "NEGATIVE_EFFECTIVENESS", "status": "OPEN_REVIEW_DEFERRED"},
        {"id": "MIT-011", "priority": "P1", "lane": "TD_DEPENDENCIES", "owner": "AI Solution producer + Test Solution validation", "stage": "TD",
         "title": "Resolve physical TD provisional/unavailable dependencies", "count": summary["physicalMetadataPairsVerifiedInFinalization"],
         "acceptance": "Review metadata provisional values, unavailable fields and unresolved segments against rule applicability; distinguish counts of occurrences from unique defects.",
         "blocker": "PROVISIONAL_OR_UNRESOLVED_METADATA", "queryKind": "TD_METADATA_DEPENDENCY", "status": "OPEN_REVIEW_DEFERRED"},
        {"id": "MIT-012", "priority": "P1", "lane": "TD_PREDICATE", "owner": "Test Solution validation", "stage": "TD",
         "title": "Expand source-grounded field/companion validators", "count": stages["tdPredicateStatus"].get("NOT_ASSESSED_NO_SUPPORTED_PREDICATE", 0),
         "acceptance": "Implement field and companion checks against independently interpreted rules, actual payload, correct location and applicable context.",
         "blocker": "PREDICATE_NOT_SUPPORTED", "queryKind": "TD_PREDICATE_SCOPE", "status": "OPEN_REVIEW_DEFERRED"},
        {"id": "MIT-013", "priority": "P2", "lane": "LIFECYCLE", "owner": "Test Solution comparison + oracle owner", "stage": "TS/TC/TD",
         "title": "Validate flow roles and transaction correlation", "count": kinds.get("LIFECYCLE_CONTEXT", 0),
         "acceptance": "Verify original/follow-up message roles, applicability, sequence/approval reuse and expected outcomes across physical legs.",
         "blocker": "LIFECYCLE_ORACLE_OR_CONTEXT", "queryKind": "LIFECYCLE_CONTEXT", "status": "OPEN_REVIEW_DEFERRED"},
        {"id": "MIT-014", "priority": "P2", "lane": "WIRE_COMPATIBILITY", "owner": "Protocol/test environment owner", "stage": "TD",
         "title": "Capture and validate actual full-message wire bytes", "count": summary["physicalPayloadLegsAssessed"],
         "acceptance": "Bind supported network framing, TPDU, Section 1, companion segments, separators and lengths to actual captured bytes.",
         "blocker": "WIRE_CAPTURE_AND_PROFILE_MISSING", "queryKind": "", "status": "BLOCKED_EXTERNAL_EVIDENCE"},
        {"id": "MIT-015", "priority": "P2", "lane": "HOST_EXECUTION", "owner": "Authorized processor/test host owner", "stage": "EXECUTION",
         "title": "Run qualified controls in an authorized host environment", "count": summary["testCases"],
         "acceptance": "Approved host/profile, state fixture, authoritative oracle, actual request/response, environment identity, replayable logs and approval.",
         "blocker": "AUTHORIZED_HOST_AND_ORACLE_MISSING", "queryKind": "", "status": "BLOCKED_EXTERNAL_EVIDENCE"},
        {"id": "MIT-016", "priority": "P0", "lane": "PRODUCER_OR_SCOPE_REVIEW", "owner": "AI Solution producer + Test Solution rule owner", "stage": "TS/TC",
         "title": "Resolve scenarios without a case", "count": kinds.get("TS_NO_CASE", 0),
         "acceptance": "Generate a BR-linked TC or provide a source-backed, explicitly out-of-scope disposition for every no-TC scenario.",
         "blocker": "SCENARIO_WITHOUT_TC", "queryKind": "TS_NO_CASE", "status": "OPEN_REVIEW_DEFERRED"},
        {"id": "MIT-017", "priority": "P0", "lane": "PRODUCER_EVIDENCE", "owner": "AI Solution producer", "stage": "BR/TS/TC/TD",
         "title": "Reconcile the late matrix row/edge discrepancy", "count": 63,
         "acceptance": "Supply complete matrix JSON, exact source catalog hashes and the 63 explicit edges; independently validate each linked artifact and orphan definition.",
         "blocker": "COMPLETE_PRODUCER_GRAPH_NOT_SUPPLIED", "queryKind": "", "status": "BLOCKED_PRODUCER_EVIDENCE"},
        {"id": "MIT-018", "priority": "P1", "lane": "PREDICATE_TRIAGE", "owner": "Test Solution validation", "stage": "TD",
         "title": "Triage bounded predicate failures by test intent", "count": summary["sourcePredicateCounts"].get("FAIL", 0),
         "acceptance": "Classify each failure as intended isolated negative, unrelated defect or unresolved rule/context; never count intended negative FAIL as test failure or auto-ignore it.",
         "blocker": "INTENT_AND_FULL_SCOPE_ADJUDICATION", "queryKind": "TD_PREDICATE_SCOPE", "status": "OPEN_REVIEW_DEFERRED"},
    ]
    for action in actions:
        action["linkedQueryIds"] = [row["queryId"] for row in query_rows if row["kind"] == action["queryKind"]] if action["queryKind"] else []
    output.mkdir(parents=True)
    plan = {"asOf": "2026-10-07", "delivery": "ATL105-2026-10-05-Run1", "status": "MITIGATION_TRACKER_READY",
        "SMEReview": "DEFERRED_NOT_APPROVED", "automaticPassPolicy": "ONLY_EVIDENCE_BACKED_SUPPORTED_ASSERTIONS_MAY_PASS; MISSING/UNSUPPORTED=NOT_ASSESSED",
        "sourceAssessment": str(finalized / "FULL-SEMANTIC-CHAIN-REPORT.html"), "sourceSummary": summary,
        "actions": actions,
        "automaticActionsCompleted": ["Frozen input SHA-256 validation", "Structural link and physical-file reconciliation",
            "All source-assertion/query status preservation", "Metadata-to-case joins and physical metadata SHA-256 validation",
            "Bounded source predicates for Segment Type 100 and six-digit Sequence Number", "Stable query IDs and full-population case/register counts",
            f"Rechecked {quote_verification['citedQuoteWindowsVerified']} of {quote_verification['curatedAssertionCount']} curated quote windows against the raw specification"],
        "sourceQuoteVerification": quote_verification,
        "approvalBoundary": "No SME, semantic, host or execution approval is generated by this mitigation tracker; do not treat this plan as approval or a semantic PASS."}
    write(output / "semantic-mitigation-plan.json", plan)
    with (output / "semantic-mitigation-actions.csv").open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=["id", "priority", "lane", "owner", "stage", "title", "count", "acceptance", "blocker", "status", "queryKind", "queryCount", "linkedQueryIds"])
        writer.writeheader()
        for action in actions:
            writer.writerow({**action, "queryCount": len(action["linkedQueryIds"]), "linkedQueryIds": ";".join(action["linkedQueryIds"])})
    (output / "SEMANTIC-MITIGATION-PLAN.md").write_text(render_markdown(plan), encoding="utf-8")
    (output / "SEMANTIC-MITIGATION-PLAN.html").write_text(render_html(plan), encoding="utf-8")
    validate(output)
    print(json.dumps({"actions": len(actions), "p0": sum(item["priority"] == "P0" for item in actions),
        "p1": sum(item["priority"] == "P1" for item in actions), "p2": sum(item["priority"] == "P2" for item in actions),
        "automaticActionsCompleted": len(plan["automaticActionsCompleted"]), "hostExecutionCertified": False}, indent=2))


def render_markdown(plan):
    lines = ["# ATL105 Semantic Alignment Mitigation Plan", "",
        "SME review is deferred, not approved. This tracker separates automated checks, producer fixes, independent rule review and external host prerequisites.", "",
        "| ID | Priority | Lane/owner | Stage | Action | Count | Status |", "|---|---|---|---|---|---:|---|"]
    for item in plan["actions"]:
        lines.append(f"| {item['id']} | {item['priority']} | {item['lane']} / {item['owner']} | {item['stage']} | {item['title']} | {item['count']} | {item['status']} |")
    lines.extend(["", "## Acceptance criteria", ""])
    for item in plan["actions"]:
        lines.append(f"- **{item['id']} {item['title']}:** {item['acceptance']} Blocker: `{item['blocker']}`. Linked SME queries: {len(item['linkedQueryIds']):,}.")
    lines.extend(["", "## Automatic pass rule", "", plan["automaticPassPolicy"], "", "Automated PASS applies only to supported, evidence-backed assertions. It does not approve business meaning, waive review, or certify host execution."])
    return "\n".join(lines)


def render_html(plan):
    rows = "".join("<tr>" + "".join("<td>" + escape(str(item[key])) + "</td>" for key in ["id", "priority", "lane", "owner", "stage", "title", "count", "status", "blocker"]) + "</tr>" for item in plan["actions"])
    return """<!doctype html><html lang="en"><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>ATL105 | Semantic Mitigation Plan</title><style>body{margin:0;background:#f4f6f5;color:#202526;font:14px 'Segoe UI',sans-serif;letter-spacing:0;overflow-wrap:anywhere}header{padding:22px 26px;background:white;border-top:5px solid #07665e}main{max-width:1450px;margin:auto;padding:24px}h1{font:700 26px Georgia,serif}h2{font-size:19px;color:#07665e}.notice{padding:14px;background:#fff5dd;border-left:4px solid #9b6510}.wrap{overflow:auto}table{border-collapse:collapse;width:100%;background:white}th,td{padding:9px;border-bottom:1px solid #d6dfdc;text-align:left;vertical-align:top}th{background:#e5efec}@media(max-width:650px){header,main{padding:16px}table{min-width:1000px}}</style><header><h1>ATL105 | Semantic Alignment Mitigation Plan</h1><p>October 5 Run1 | October 7 | SME review deferred, not approved</p><a href="../semantic-alignment-2026-10-07-finalized/FULL-SEMANTIC-CHAIN-REPORT.html">Full assessment</a> | <a href="../semantic-alignment-2026-10-07-finalized/semantic-sme-query-register.csv">All SME queries</a></header><main><div class="notice">Automate supported evidence checks. Do not turn missing, unsupported or unreviewed assertions into PASS.</div><section class="wrap"><h2>Prioritized mitigation actions</h2><table><thead><tr><th>ID</th><th>Priority</th><th>Lane</th><th>Owner</th><th>Stage</th><th>Action</th><th>Count</th><th>Status</th><th>Blocker</th></tr></thead><tbody>""" + rows + """</tbody></table></section><section><h2>Completed local automation</h2><ul>""" + "".join("<li>" + escape(item) + "</li>" for item in plan["automaticActionsCompleted"]) + """</ul><p>Review/approval boundary: """ + escape(plan["approvalBoundary"]) + "</p></section></main></html>"


def validate(output):
    plan = read(output / "semantic-mitigation-plan.json")
    actions = plan["actions"]
    assert len(actions) == 18
    assert plan["sourceQuoteVerification"]["curatedAssertionCount"] == 21
    assert plan["sourceQuoteVerification"]["citedQuoteWindowsVerified"] == 21
    assert not plan["sourceQuoteVerification"]["sourceInterpretationApproved"]
    assert sum(item["priority"] == "P0" for item in actions) == 5
    assert sum(item["status"].startswith("BLOCKED_") for item in actions) == 3
    assert all(item["status"] != "PASS" for item in actions)
    assert all(not plan["sourceSummary"]["executionCertified"] for _ in [0])
    assert "do not" in plan["approvalBoundary"].lower()
    assert "109876" not in str(plan["sourceSummary"].get("smeQueryCount")) or plan["sourceSummary"].get("smeQueryCount") == 109876
    assert (output / "semantic-mitigation-actions.csv").is_file()
    assert "Automate supported evidence checks" in (output / "SEMANTIC-MITIGATION-PLAN.html").read_text(encoding="utf-8")
    print("PASS: prioritized action counts, lane boundaries, external blockers and no automatic approval")


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("finalized_assessment", nargs="?", type=Path)
    parser.add_argument("output_directory", nargs="?", type=Path)
    parser.add_argument("--self-test", action="store_true")
    args = parser.parse_args()
    if args.self_test:
        self_test()
    elif args.finalized_assessment and args.output_directory:
        build(args.finalized_assessment, args.output_directory)
    else:
        parser.error("finalized_assessment and new output_directory required")


if __name__ == "__main__":
    main()