"""Build a review-only 2026-4 independent overlay and delivery-bound candidate crosswalk."""

from __future__ import annotations

import argparse
import csv
import hashlib
import json
import re
from collections import Counter, defaultdict
from pathlib import Path

import delivery_gates as gates

ROOT = Path(__file__).resolve().parents[1]
ATL = ROOT / "specifications" / "ATL105"
REVIEW = ATL / "test-output" / "test-solution-independent-review"
MODEL = ATL / "docs" / "specs" / "kb" / "segment-100" / "mastercard-maestro-2026-4-model" / "rules.json"
LEGACY = REVIEW / "complete-all-test-solution-br-ts-tc-td-package.json"
OLD_SOURCE = ATL / "docs" / "specs" / "extracted_text.txt"
OLD_HASH = "6d03bf09f25b9126975b584aef83c21d1b7b081cabccfb9af3b89d46a26d06aa"
DEFAULT_EVIDENCE = ATL / "test-output" / "ai-solution-independent-review" / "2026-10-31-mastercard-business-br-coverage-v1"
DEFAULT_OUTPUT = REVIEW / "mc-maestro-model-v1"
FLEET_COMPANION = ATL / "docs" / "specs" / "kb" / "petroleum-2025-1" / "mastercard-fleet"
CONTEXT_PACKAGE = {"MC_CREDIT": "mastercard_credit", "MC_FLEET": "mastercard_fleet",
                   "MAESTRO_DEBIT": "maestro_direct_debit"}
STOP = {"the", "a", "an", "of", "in", "on", "for", "to", "and", "or", "is", "be", "must",
        "should", "with", "by", "as", "this", "that", "it", "no", "all", "only", "when"}


def digest(path):
    with path.open("rb") as stream:
        return hashlib.file_digest(stream, "sha256").hexdigest()


def norm(text):
    return re.sub(r"\s+", " ", text).strip()


def tokens(text):
    return set(re.findall(r"[a-z0-9]+", text.lower())) - STOP


def unique(records):
    result = {}
    for row in records:
        if row["id"] in result:
            raise ValueError(f"Duplicate artifact ID: {row['id']}")
        result[row["id"]] = row
    return result


def ids(value):
    if value is None:
        return set()
    if isinstance(value, str):
        return {value}
    if not isinstance(value, list) or not all(isinstance(item, str) for item in value):
        raise ValueError("Invalid link ID shape")
    return set(value)


def evaluate(predicate, facts):
    """Evaluate one supplied logical observation, never infer missing context."""
    op, field = predicate["op"], predicate["field"]
    if field not in facts:
        raise ValueError(f"Missing logical observation: {field}")
    value = facts[field]
    if op == "equals":
        expected = predicate["expected"]
        if type(value) is not type(expected):
            raise ValueError(f"Wrong observation type: {field}")
        return value == expected
    if op == "same":
        other = predicate["other"]
        if other not in facts or type(value) is not type(facts[other]):
            raise ValueError(f"Missing or incompatible comparison: {other}")
        if value in ("", None) or facts[other] in ("", None):
            raise ValueError("Empty observations cannot establish lifecycle agreement")
        return value == facts[other]
    if op in {"digits", "asciiWidth"}:
        if not isinstance(value, str):
            raise ValueError(f"Expected string observation: {field}")
        return (value.isascii() and len(value) == predicate["width"]
                and (op != "digits" or value.isdigit()))
    if op in {"contains", "count"}:
        if not isinstance(value, list) or not all(isinstance(item, str) for item in value):
            raise ValueError(f"Expected segment list: {field}")
        if op == "contains":
            return predicate["expected"] in value
        other = predicate["other"]
        if other not in facts or type(facts[other]) is not int:
            raise ValueError("Count observation must be an integer")
        return len(value) == facts[other]
    raise ValueError(f"Unsupported predicate: {op}")


def source_pages(text):
    parts = re.split(r"--- PAGE (\d+) ---", text)
    pages = {}
    for offset in range(1, len(parts), 2):
        page = int(parts[offset])
        if page in pages:
            raise ValueError(f"Duplicate source page: {page}")
        pages[page] = norm(parts[offset + 1])
    return pages


def build_overlay(document, source, old_source):
    if (document["schemaVersion"] != 1 or document["specificationVersion"] != "2026-4"
            or document["status"] != "DRAFT_REVIEW_REQUIRED" or document["baselineChanged"] is not False):
        raise ValueError("Overlay cannot change or approve the baseline")
    if set(document["contexts"]) != set(CONTEXT_PACKAGE):
        raise ValueError("Network/program contexts are incomplete")
    unique(document["rules"])
    unique(document["blockedDecisions"])
    pages = source_pages(source)
    old_normalized = norm(old_source)
    package = {"businessRequirements": [], "testScenarios": [], "testCases": [], "testData": []}
    register = []
    for rule in document["rules"]:
        quote = norm(rule["quote"])
        if len(quote) < 15 or quote not in pages.get(rule["page"], ""):
            raise ValueError(f"Source quote/page mismatch: {rule['id']}")
        if (not rule["condition"].strip() or not rule["contexts"]
                or set(rule["contexts"]) - CONTEXT_PACKAGE.keys()):
            raise ValueError(f"Missing or unknown applicability: {rule['id']}")
        if not evaluate(rule["predicate"], rule["valid"]) or evaluate(rule["predicate"], rule["invalid"]):
            raise ValueError(f"Control/mutant mismatch: {rule['id']}")
        br_id = "BR-" + rule["id"]
        anchor = {"specification": "ATL105", "version": "2026-4", "section": rule["section"],
                  "page": rule["page"], "rule": rule["id"]}
        evidence = {"sourcePath": str(gates.SOURCE.relative_to(ROOT)),
                    "sha256": gates.SOURCE_SHA256, "page": rule["page"], "quote": rule["quote"]}
        package["businessRequirements"].append({
            "id": br_id, "statement": rule["statement"], "title": rule["statement"],
            "status": "REVIEW_REQUIRED", "atomicity": "DRAFT_ATOMIC",
            "sourceAnchors": [anchor], "sourceEvidence": evidence,
            "applicability": {"contexts": rule["contexts"], "condition": rule["condition"],
                              "decision": "REVIEW_REQUIRED" if rule.get("applicabilityReviewRequired")
                              else "SOURCE_BOUND_DRAFT"},
        })
        for context in rule["contexts"]:
            ts_id = f"TS-{rule['id']}-{context}"
            package["testScenarios"].append({
                "id": ts_id, "requirementIds": [br_id], "status": "REVIEW_REQUIRED",
                "intent": rule["statement"], "preconditions": [rule["condition"]],
                "context": context, "sourceAnchors": [anchor]})
            register.append({
                "independentBrId": br_id, "context": context, "condition": rule["condition"],
                "sourcePage": rule["page"], "sourceSection": rule["section"],
                "sourceSha256": gates.SOURCE_SHA256, "sourceQuote": rule["quote"],
                "applicability": "REVIEW_REQUIRED" if rule.get("applicabilityReviewRequired")
                else "SOURCE_BOUND_DRAFT", "approval": "REVIEW_REQUIRED",
                "denominatorEligibility": "BLOCKED_PENDING_REVIEW_AND_COMPLETENESS",
                "releaseComparison": "CLAUSE_PRESENT_IN_BOTH_RELEASES" if quote in old_normalized
                else "OLD_CLAUSE_NOT_IDENTICAL_REVIEW_REQUIRED",
                "legacyOverlap": "NOT_RECONCILED_DO_NOT_ADD_TO_LEGACY_DENOMINATOR",
            })
            for label, facts, expected in (("CONTROL", rule["valid"], "PASS"),
                                           ("MUTANT", rule["invalid"], "FAIL")):
                tc_id = f"TC-{rule['id']}-{context}-{label}"
                td_id = f"TD-{rule['id']}-{context}-{label}"
                package["testCases"].append({
                    "id": tc_id, "scenarioIds": [ts_id], "status": "REVIEW_REQUIRED",
                    "expectedOutcome": expected, "sourceAnchors": [anchor],
                    "assertions": [{"requirementId": br_id, "predicate": rule["predicate"],
                                    "expectedValidation": expected, "sourceAnchors": [anchor]}],
                    "observationBoundary": "SUPPLIED_LOGICAL_FACTS_NOT_DEVICE_OR_HOST_EXECUTION"})
                package["testData"].append({
                    "id": td_id, "testCaseIds": [tc_id], "readiness": "REVIEW_REQUIRED",
                    "fixtureKind": "LOGICAL_PREDICATE_VECTOR", "payload": facts,
                    "expectedValidation": expected, "sourceAnchors": [anchor]})
    package["manifest"] = {"specificationVersion": "2026-4", "status": "DRAFT_REVIEW_REQUIRED",
                           "baselineChanged": False, "sourceSha256": gates.SOURCE_SHA256,
                           "authority": "SOURCE_DERIVED_DRAFT_NOT_CERTIFIED_TEST_SOLUTION_TRUTH",
                           "scope": "BOUNDED_OVERLAY_NOT_COMPLETE_NETWORK_DENOMINATOR"}
    return package, register


def legacy_register(model):
    brs = unique(model["businessRequirements"])
    scenarios = unique(model["testScenarios"])
    cases = unique(model["testCases"])
    unique(model["testData"])
    by_br, by_ts, by_tc = defaultdict(set), defaultdict(set), defaultdict(set)
    for scenario_id, scenario in scenarios.items():
        for br_id in ids(scenario.get("requirementIds")):
            if br_id in brs:
                by_br[br_id].add(scenario_id)
    for case_id, case in cases.items():
        for ts_id in ids(case.get("scenarioIds")):
            by_ts[ts_id].add(case_id)
    for datum in model["testData"]:
        if datum.get("payload") is not None:
            for case_id in ids(datum.get("testCaseIds")):
                by_tc[case_id].add(datum["id"])
    rows = []
    for identity, br in brs.items():
        ts = by_br[identity]
        tc = set().union(*(by_ts[item] for item in ts))
        td = set().union(*(by_tc[item] for item in tc))
        anchors = br.get("sourceAnchors") or ([br["sourceAnchor"]] if br.get("sourceAnchor") else [])
        rows.append({"legacyBrId": identity, "title": br.get("title", br.get("statement", "")),
                     "originalStatus": br.get("status"), "sourceAnchors": anchors,
                     "linkedScenarios": len(ts), "linkedCases": len(tc), "linkedPayloadTd": len(td),
                     "structuralChainOnly": bool(td),
                     "releaseEquivalence": "NOT_REQUALIFIED",
                     "contextDecisions": {context: "REVIEW_REQUIRED" for context in CONTEXT_PACKAGE},
                     "denominatorEligibility": "BLOCKED",
                     "reason": "Requires 2026-4 clause equivalence, applicability, atomicity and overlay-overlap review"})
    return rows


def candidate_match(rule, ai):
    """A same-page lexical candidate is deliberately never a semantic confirmation."""
    if ai.get("source_id") != "SRC-ATL105-PDF-001" or ai.get("page") != rule["page"]:
        return None
    statement = norm(ai.get("statement", ""))
    quote = norm(rule["quote"])
    if len(statement) >= 30 and statement.casefold() in quote.casefold():
        return "SAME_SOURCE_PAGE_LITERAL_STATEMENT_CANDIDATE"
    left = tokens(rule["statement"] + " " + quote)
    right = tokens(statement)
    common = left & right
    if len(common) >= 4 and len(common) / max(len(right), 1) >= 0.6:
        return "SAME_SOURCE_PAGE_LEXICAL_CANDIDATE"
    return None


def build_crosswalk(document, delivery, ledger):
    lookup = {(row["package"], row["ruleId"]): row for row in ledger}
    rules_by_context = {}
    input_files = []
    for context, package in CONTEXT_PACKAGE.items():
        path = delivery / package / "requirements_scenarios" / "scenario_supporting_rules.json"
        doc = gates.load(path)
        if doc["meta"]["spec_version"] != "2026-4":
            raise ValueError(f"Unexpected AI source release: {package}")
        for identity, rule in doc["rules"].items():
            if identity != rule["rule_id"]:
                raise ValueError("AI rule identity mismatch")
        rules_by_context[context] = doc["rules"]
        input_files.append(path)
    rows = []
    for rule in document["rules"]:
        for context in rule["contexts"]:
            candidates = []
            for identity, ai in rules_by_context[context].items():
                reason = candidate_match(rule, ai)
                if reason:
                    evidence = lookup.get((CONTEXT_PACKAGE[context], identity))
                    if evidence is None:
                        raise ValueError("AI rule missing from frozen evidence ledger")
                    candidates.append({
                        "aiRuleId": identity, "reason": reason,
                        "aiStatement": ai["statement"],
                        "linkedScenarioCount": int(evidence["scenarioCount"]),
                        "linkedCaseCount": int(evidence["caseCount"]),
                        "casesWithAllDeclaredTd": int(evidence["casesWithAllDeclaredTd"]),
                        "casesWithExplicitPredicateAndTd": int(evidence["casesWithExplicitPredicateAndTd"]),
                        "assessment": "REVIEW_REQUIRED_NOT_SEMANTICALLY_CONFIRMED"})
            rows.append({"independentBrId": "BR-" + rule["id"], "context": context,
                         "deliveryPackage": CONTEXT_PACKAGE[context],
                         "candidateCount": len(candidates), "candidates": candidates,
                         "disposition": "CANDIDATE_REVIEW_REQUIRED" if candidates else "NOT_ASSESSED",
                         "confirmed": False, "reason": "Same primary source ID/page and lexical evidence only; "
                         "conditions, objectives and assertions require independent review"})
    reverse = defaultdict(set)
    for row in rows:
        for candidate in row["candidates"]:
            reverse[(row["deliveryPackage"], candidate["aiRuleId"])].add(row["independentBrId"])
    ai_register = []
    for context, rules in rules_by_context.items():
        for identity in rules:
            package = CONTEXT_PACKAGE[context]
            ai_register.append({
                "deliveryPackage": package, "aiRuleId": identity,
                "candidateIndependentBrIds": sorted(reverse[(package, identity)]),
                "disposition": "CANDIDATE_REVIEW_REQUIRED" if reverse[(package, identity)] else "NOT_ASSESSED",
                "semanticConfirmation": False,
            })
    return rows, ai_register, input_files


def companion_inventory():
    training = gates.load(FLEET_COMPANION / "training-package.json")
    chain = gates.load(FLEET_COMPANION / "chain-package.json")
    if training["certified"] or training["executionReady"] or training["baselineChanged"]:
        raise ValueError("Existing companion status changed; reconcile before reuse")
    return {
        "context": "MC_FLEET", "source": "Petroleum 2025-1",
        "trainingStatus": training["status"], "authority": chain["manifest"]["authority"],
        "businessRequirements": len(unique(chain["businessRequirements"])),
        "scenarios": len(unique(chain["testScenarios"])),
        "cases": len(unique(chain["testCases"])), "data": len(unique(chain["testData"])),
        "disposition": "EXISTING_DRAFT_INDEXED_NOT_ADDED_TO_DENOMINATOR",
        "reason": "ATL105/source reconciliation and overlap review required; no certification inherited",
    }


def read_csv(path):
    with path.open(encoding="utf-8-sig", newline="") as stream:
        return list(csv.DictReader(stream))


def write_csv(path, rows):
    with path.open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=list(rows[0]) if rows else ["record"])
        writer.writeheader()
        for row in rows:
            writer.writerow({key: json.dumps(value, ensure_ascii=True) if isinstance(value, (list, dict))
                             else value for key, value in row.items()})


def report(summary):
    return "\n".join([
        "# Mastercard / Maestro 2026-4 Test Model Improvement", "",
        "## Outcome",
        f"- Independent, source-pinned atomic draft rules: **{summary['overlayRules']}**.",
        f"- Rule/context applicability rows: **{summary['contextRows']}**.",
        f"- Logical scenarios / positive and negative TC/TD: **{summary['scenarios']} / {summary['cases']} / {summary['data']}**.",
        f"- Legacy BRs retained for release/applicability review: **{summary['legacyRules']}**.",
        f"- Candidate crosswalk rows / rows with AI candidates: **{summary['crosswalkRows']} / {summary['rowsWithCandidates']}**.",
        "- Confirmed independent BR coverage: **NOT CALCULABLE / NOT ASSESSED**.",
        "- Approved denominator: **not established**; the bounded draft overlay is not a complete denominator.",
        "- Execution readiness: **BLOCKED**. No legacy package or AI delivery was modified.", "",
        "## What improved",
        "1. A distinct 2026-4 overlay pins every literal quote to its PDF page and text SHA-256.",
        "2. Each rule has explicit network/program context and transaction preconditions; generic credit",
        "   rules are not silently inherited by Fleet, and debit rules exclude Signature Debit as Credit.",
        "3. Every context gets source-linked positive/control and single-predicate negative/mutant vectors.",
        "   Logical observations are synthetic, not wire messages, device measurements or issuer outcomes.",
        "4. Every legacy BR is retained with existing structural chain counts, source anchors and three",
        "   unresolved context decisions. No heuristic exclusion or 2026-4 relabelling occurs.",
        "5. A delivery-hash-bound crosswalk records same-page lexical candidates and physical-chain",
        "   evidence separately. Missing candidates mean NOT_ASSESSED, never AI_ONLY or proven absence.",
        "6. Eight persisted BA/SME decision groups cover completeness/overlap, Fleet inheritance,",
        "   savings/ACH, Signature Debit/Common AID, TOR conflicts, MOTO completion, fixtures and subtypes.", "",
        "## Existing companion evidence preserved",
        f"The existing Petroleum 2025-1 Mastercard Fleet draft has {summary['companionCounts']['businessRequirements']} BRs "
        f"and {summary['companionCounts']['scenarios']}/{summary['companionCounts']['cases']}/{summary['companionCounts']['data']} TS/TC/TD.",
        "It is indexed and hash-bound in companion-evidence.json, not copied or silently approved.",
        "The companion's source-release and overlap decisions remain separate from this ATL105 overlay.",
        "The AI review register retains every supplied supporting rule, including those without a candidate.", "",
        "## Boundaries",
        "Clause presence in both releases is an exact normalized text observation, not whole-rule equivalence.",
        "The draft atomicity/context choices need independent review; source quotation does not certify them.",
        f"Do not add this overlay to the {summary['legacyRules']}-rule legacy population: overlap is unresolved.",
        "Do not interpret candidate counts or logical fixture passes as confirmed semantic coverage.",
        "Full payload execution and simulator/host response checks remain outside this builder.", "",
        "## Evidence",
        "- [Summary and hashes](summary.json)",
        "- [Independent BR/TS/TC/TD overlay](model-package.json)",
        "- [Applicability and denominator register](denominator-register.csv)",
        "- [Legacy carry-forward review register](legacy-review-register.csv)",
        "- [Delivery-bound AI candidate crosswalk](delivery-crosswalk.csv)",
        "- [Complete AI supporting-rule review register](ai-rule-review-register.csv)",
        "- [Existing Fleet companion evidence](companion-evidence.json)",
        "- [BA/SME decisions](ba-sme-decisions.csv)",
        "- [Input identity manifest](input-manifest.json)", "",
    ])


def run(delivery, evidence, output, check=False):
    if output.resolve().is_relative_to(delivery.resolve()):
        raise ValueError("Output must be outside AI delivery")
    if not check and output.exists():
        raise ValueError("New output directory required; use --check to verify existing output")
    gates.trusted_source()
    if digest(OLD_SOURCE) != OLD_HASH:
        raise ValueError("2026-3 source identity mismatch")
    ai_files = [delivery / package / "requirements_scenarios" / "scenario_supporting_rules.json"
                for package in CONTEXT_PACKAGE.values()]
    input_paths = [MODEL, LEGACY, gates.SOURCE, OLD_SOURCE, Path(__file__), Path(gates.__file__),
                   evidence / "summary.json", evidence / "input-manifest.csv",
                   evidence / "supporting-rule-ledger.csv",
                   FLEET_COMPANION / "training-package.json",
                   FLEET_COMPANION / "chain-package.json", *ai_files]
    manifest = [{"path": str(path.resolve()), "sha256": digest(path)} for path in input_paths]
    document = gates.load(MODEL)
    existing_summary = gates.load(evidence / "summary.json")
    if Path(existing_summary["inputRoot"]).resolve() != delivery.resolve():
        raise ValueError("Delivery root differs from frozen assessment")
    frozen_inventory = read_csv(evidence / "input-manifest.csv")
    current_files = {path.resolve() for package in CONTEXT_PACKAGE.values()
                     for path in (delivery / package).rglob("*") if path.is_file()}
    if current_files != {gates.resolve_reference(delivery, row["path"]) for row in frozen_inventory}:
        raise ValueError("Delivery population differs from frozen assessment")
    for row in frozen_inventory:
        if digest(gates.resolve_reference(delivery, row["path"])) != row["sha256"]:
            raise ValueError("Frozen AI delivery hash mismatch")
    package, register = build_overlay(document, gates.SOURCE.read_text(encoding="utf-8-sig"),
                                      OLD_SOURCE.read_text(encoding="utf-8-sig"))
    legacy = legacy_register(gates.load(LEGACY))
    crosswalk, ai_register, _ = build_crosswalk(
        document, delivery, read_csv(evidence / "supporting-rule-ledger.csv"))
    companion = companion_inventory()
    summary = {"artifact": "MASTERCARD_MAESTRO_2026_4_DRAFT_MODEL",
               "overlayRules": len(package["businessRequirements"]), "contextRows": len(register),
               "scenarios": len(package["testScenarios"]), "cases": len(package["testCases"]),
               "data": len(package["testData"]), "legacyRules": len(legacy),
               "crosswalkRows": len(crosswalk),
               "rowsWithCandidates": sum(row["candidateCount"] > 0 for row in crosswalk),
               "aiRuleReviewRows": len(ai_register),
               "companionCounts": {key: companion[key] for key in
                                   ("businessRequirements", "scenarios", "cases", "data")},
               "contextCounts": dict(Counter(row["context"] for row in register)),
               "confirmedRequirementCoveragePercent": None, "approvedDenominator": None,
               "semanticAssessmentStatus": "NOT_ASSESSED", "readiness": "BLOCKED",
               "frozenDeliveryFileCount": len(frozen_inventory), "inputs": manifest}
    outputs = {"model-package.json": package, "summary.json": summary, "input-manifest.json": manifest,
               "companion-evidence.json": companion}
    csv_outputs = {"denominator-register.csv": register, "legacy-review-register.csv": legacy,
                   "delivery-crosswalk.csv": crosswalk, "ba-sme-decisions.csv": document["blockedDecisions"],
                   "ai-rule-review-register.csv": ai_register}
    # Recheck source/evidence identities and delivery after derivation, before publication.
    for row in manifest:
        if digest(Path(row["path"])) != row["sha256"]:
            raise ValueError("Assessment input changed during model build")
    for row in frozen_inventory:
        if digest(gates.resolve_reference(delivery, row["path"])) != row["sha256"]:
            raise ValueError("Delivery changed during model build")
    if current_files != {path.resolve() for package_name in CONTEXT_PACKAGE.values()
                         for path in (delivery / package_name).rglob("*") if path.is_file()}:
        raise ValueError("Delivery population changed during model build")
    if check:
        for name, value in outputs.items():
            if gates.load(output / name) != value:
                raise ValueError(f"Stale generated evidence: {name}")
        if (output / "REPORT.md").read_text(encoding="utf-8") != report(summary):
            raise ValueError("Stale generated report")
        for name, rows in csv_outputs.items():
            expected = [{key: json.dumps(value, ensure_ascii=True) if isinstance(value, (list, dict))
                         else str(value) if value is not None else "" for key, value in row.items()}
                        for row in rows]
            if read_csv(output / name) != expected:
                raise ValueError(f"Stale generated register: {name}")
    else:
        output.mkdir(parents=True)
        for name, value in outputs.items():
            (output / name).write_text(json.dumps(value, indent=2, ensure_ascii=True) + "\n", encoding="utf-8")
        for name, rows in csv_outputs.items():
            write_csv(output / name, rows)
        (output / "REPORT.md").write_text(report(summary), encoding="utf-8")
    print(json.dumps({key: value for key, value in summary.items() if key != "inputs"}, indent=2))


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--delivery", required=True, type=Path)
    parser.add_argument("--evidence", type=Path, default=DEFAULT_EVIDENCE)
    parser.add_argument("--output", type=Path, default=DEFAULT_OUTPUT)
    parser.add_argument("--check", action="store_true")
    args = parser.parse_args()
    run(args.delivery, args.evidence, args.output, args.check)


if __name__ == "__main__":
    main()
