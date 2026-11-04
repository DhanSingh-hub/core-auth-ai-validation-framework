"""Reconcile every retained fleet companion proposition within explicit source and program boundaries."""

import argparse
import contextlib
import importlib.util
import io
import json
from collections import Counter
from pathlib import Path

SPEC = importlib.util.spec_from_file_location(
    "nonmaster_petroleum_parent", Path(__file__).with_name("review-nonmaster-semantics.py"))
parent = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(parent)
completion, base = parent.completion, parent.base
DECISIONS = completion.MODEL.with_name("nonmaster-petroleum-semantic-decisions.json")
OUTPUT = base.REVIEW / "nonmaster-petroleum-semantic-review-v1"
CURRENT_RELATIONS = {"RELATED_DIFFERENT_CONTEXT", "RELATED_DISTINCT_ASSERTION"}


def locator(anchor):
    return {key: value for key, value in anchor.items() if key != "quote"} | {
        "quoteSha256": completion.fleet.sha(anchor["quote"].encode("utf-8"))}


def reconcile(doc, companions, current, legacy, configurations, sources):
    if doc["schemaVersion"] != 1 or doc["status"] != "DRAFT_REVIEW_REQUIRED":
        raise ValueError("Reconciliation must remain draft schema 1")
    if not doc["comparisonBoundary"].strip():
        raise ValueError("Explicit non-exhaustive comparison boundary required")
    decisions = base.unique(doc["rules"])
    current_rules = base.unique(current["businessRequirements"])
    historical = {row["legacyBrId"]: row for row in legacy}
    if len(historical) != len(legacy):
        raise ValueError("Duplicate legacy ID")
    program_rules = {}
    for context, package in companions.items():
        if context not in completion.COMPANIONS:
            raise ValueError("Unknown Petroleum companion context")
        for identity in base.unique(package["businessRequirements"]):
            if identity in program_rules:
                raise ValueError("Duplicate companion rule across contexts")
            program_rules[identity] = context
    if set(companions) != set(completion.COMPANIONS) or set(decisions) != set(program_rules):
        raise ValueError("Every companion BR needs exactly one authored decision")
    reviews, questions, links = [], {}, []
    for context, package in companions.items():
        cfg = configurations[context]
        if (cfg["contextId"] != context or cfg["sourceVersion"] != "2025-1"
                or cfg["status"] != "DRAFT_REVIEW_REQUIRED"
                or package["manifest"]["authority"] !=
                "SOURCE_DERIVED_DRAFT_AUTHORED_BY_COPILOT_NOT_TEST_SOLUTION_TRUTH"):
            raise ValueError("Petroleum program authority must not be relabeled")
        comparisons = base.unique(cfg["reconciliation"])
        inventory = base.unique(cfg["inventory"])
        comparison_records = {}
        for ref, comparison in comparisons.items():
            if comparison["disposition"] not in completion.fleet.DISPOSITIONS:
                raise ValueError("Unknown source comparison disposition")
            atl = []
            for anchor in comparison.get("atl105", []):
                source = sources["atl105"][anchor["version"]]
                quote = base.norm(anchor["quote"])
                if not quote or quote not in source["pages"].get(anchor["extractedPage"], ""):
                    raise ValueError("ATL105 comparison quote/page mismatch")
                atl.append({
                    "version": anchor["version"], "page": anchor["extractedPage"],
                    "sourceSha256": source["sha256"],
                    "quoteSha256": completion.fleet.sha(anchor["quote"].encode("utf-8"))})
            record = {
                "id": ref, "disposition": comparison["disposition"],
                "topic": comparison["topic"], "note": comparison.get("note", ""),
                "question": comparison.get("question", ""), "atl105Evidence": atl,
                "inheritedAbsenceClaims": comparison.get("atl105Absent", []),
                "absenceClaimMeaning": "HISTORICAL_SCAN_CLAIM_NOT_PROOF_OF_SEMANTIC_ABSENCE",
                "reviewRequired": comparison["reviewRequired"],
                "unresolved": bool(comparison["reviewRequired"] or comparison.get("question")),
            }
            comparison_records[ref] = record
            if record["unresolved"]:
                questions[(context, ref)] = {"context": context, **record, "impactedRuleIds": []}
        scenarios = base.unique(package["testScenarios"])
        cases = base.unique(package["testCases"])
        data = base.unique(package["testData"])
        for case in cases.values():
            if not case["scenarioIds"] or set(case["scenarioIds"]) - scenarios.keys():
                raise ValueError("Companion case has unresolved scenario binding")
        for item in data.values():
            if not item["testCaseIds"] or set(item["testCaseIds"]) - cases.keys():
                raise ValueError("Companion TD has unresolved case binding")
        for rule in package["businessRequirements"]:
            decision = decisions[rule["id"]]
            for key in ("kind", "when", "assessment"):
                if not isinstance(decision[key], str) or not decision[key].strip():
                    raise ValueError("Authored kind, precondition and assessment required")
            obligations = decision["obligations"]
            if (not obligations or len(set(obligations)) != len(obligations)
                    or any(not isinstance(item, str) or not item.strip() for item in obligations)):
                raise ValueError("Nonempty unique draft obligations required")
            if not rule["sourceAnchors"] or not rule["reconciliationRefs"]:
                raise ValueError("Companion BR requires source and reconciliation evidence")
            for anchor in rule["sourceAnchors"]:
                page = sources["pages"].get(anchor["pdfPage"])
                entry = inventory.get(anchor["inventoryId"])
                if (page is None or entry is None or entry["pdfPage"] != anchor["pdfPage"]
                        or base.norm(entry["quote"]) != base.norm(anchor["quote"])
                        or anchor["pageId"] != page["id"]
                        or anchor["textSha256"] != page["textSha256"]
                        or len(base.norm(anchor["quote"])) < 15
                        or base.norm(anchor["quote"]) not in base.norm(page["text"])):
                    raise ValueError("Companion source quote/page/hash mismatch")
            decisions_for_rule = []
            for ref in rule["reconciliationRefs"]:
                if ref not in comparisons:
                    raise ValueError("Unresolved source comparison reference")
                record = comparison_records[ref]
                decisions_for_rule.append(record)
                if record["unresolved"]:
                    questions[(context, ref)]["impactedRuleIds"].append(rule["id"])
            named_current = []
            for relation in decision["current"]:
                identity = relation["id"]
                if identity not in current_rules or relation["relation"] not in CURRENT_RELATIONS:
                    raise ValueError("Unknown current-rule relation endpoint or type")
                if identity in named_current:
                    raise ValueError("Duplicate named current counterpart")
                named_current.append(identity)
                target = current_rules[identity]
                links.append({
                    "companionBrId": rule["id"], "targetBrId": identity,
                    "relation": relation["relation"], "targetContexts": target["contexts"],
                    "targetSourceEvidence": target["sourceEvidence"],
                    "targetSourceAnchors": target.get("sourceAnchors", []),
                    "targetCondition": target["condition"], "automaticMerge": False})
            if len(set(decision["legacy"])) != len(decision["legacy"]):
                raise ValueError("Duplicate named legacy counterpart")
            for identity in decision["legacy"]:
                if identity not in historical:
                    raise ValueError("Unknown legacy counterpart")
                target = historical[identity]
                links.append({
                    "companionBrId": rule["id"], "targetBrId": identity,
                    "relation": "RELATED_ASSERTIONS_RETAIN_PROGRAM_AND_RELEASE_CONDITIONS",
                    "targetTitle": target["title"],
                    "targetSourceAnchors": json.loads(target["sourceAnchors"]),
                    "releaseEquivalence": target["releaseEquivalence"], "automaticMerge": False})
            scenario_ids = sorted(s["id"] for s in scenarios.values() if rule["id"] in s["requirementIds"])
            case_ids = sorted(case["id"] for case in cases.values()
                              if set(case["scenarioIds"]) & set(scenario_ids))
            td_ids = sorted(item["id"] for item in data.values()
                            if set(item["testCaseIds"]) & set(case_ids))
            if not scenario_ids or not case_ids or not td_ids:
                raise ValueError("Companion BR lacks concrete TS/TC/TD binding")
            reviews.append({
                "brId": rule["id"], "context": context, "kind": decision["kind"],
                "condition": decision["when"], "draftObligations": [
                    {"id": f"{rule['id']}-O{number:02}", "statement": statement,
                     "status": "DRAFT_NOT_ATOMICITY_APPROVED"}
                    for number, statement in enumerate(obligations, 1)],
                "assessment": decision["assessment"], "sourceVersion": "PETROLEUM_2025_1",
                "petroleumPdfSha256": sources["document"]["source"]["sha256"],
                "sourceAnchors": [locator(anchor) for anchor in rule["sourceAnchors"]],
                "inheritedInterpretation": rule["interpretation"],
                "sourceComparisons": decisions_for_rule,
                "unresolvedSourceRefs": sorted(row["id"] for row in decisions_for_rule if row["unresolved"]),
                "namedCurrentCounterparts": named_current, "namedLegacyCounterparts": decision["legacy"],
                "otherCounterparts": "NOT_ADJUDICATED_NO_ABSENCE_INFERENCE",
                "applicability": "CONDITIONAL_ON_EXPLICIT_PROGRAM_AND_PRECONDITION",
                "otherPrograms": "NO_AUTOMATIC_INHERITANCE",
                "scenarioIds": scenario_ids, "caseIds": case_ids, "dataIds": td_ids,
                "logicalChainMeaning": "LINKED_RULE_NOT_COMPLETE_OBLIGATION_OR_HOST_EXECUTION_PROOF",
                "status": "DRAFT_RECONCILIATION_RECORDED_WITH_OPEN_SOURCE_DECISIONS",
                "automaticMerge": False, "approvedDenominatorDelta": None, "executionReady": False})
    for row in questions.values():
        row["impactScope"] = ("EXPLICITLY_LINKED_COMPANION_BRS" if row["impactedRuleIds"]
                              else "CONTEXT_LEVEL_DECISION_NO_EXPLICIT_BR_BINDING")
    return reviews, list(questions.values()), links


def report(summary):
    counts = summary["counts"]
    return "\n".join([
        "# Petroleum companion semantic reconciliation", "",
        "All **61 retained companion BRs** have authored, source-bound reconciliation entries.",
        "This completes the bounded companion register, not whole-Petroleum obligation derivation,",
        "exhaustive legacy overlap, an approved denominator or complete test/host validity.", "",
        f"- {counts['companionRules']} BRs: 26 Visa chapter 8 and 35 Voyager chapter 9.",
        f"- {counts['draftObligations']} explicitly separated draft propositions (not an approved denominator).",
        f"- {counts['namedCounterpartRelations']} named current/legacy counterpart relationships; none automatically merged.",
        f"- {counts['unresolvedSourceComparisons']} source comparison decisions remain open.",
        f"- {counts['rulesWithOpenSourceDecisions']} BRs depend on those open decisions.",
        "- Petroleum remains **2025-1**; ATL105 2026-3 and 2026-4 comparisons keep their own hashes.",
        "- No logical control/mutant PASS proves every clause in a compound BR.", "",
        "## Important retained boundaries", "",
        "- Visa chapter 8 is not Visa Fleet 2.0 EMV or generic Visa credit.",
        "- Table Load 020, BIN precedence, short ID acceptance, maintenance definition,",
        "  reserved enhancement values, product uniqueness scope and product accounting remain draft.",
        "- Generic product-code validity is not a card-specific product permission.",
        "- Physical track images are not the transmitted ATL105 account/discretionary representation.",
        "- Voyager card removal means removal before the terminal requests **card removal**,",
        "  not necessarily before authorization; an already received authorization requires a reversal.",
        "- Voyager manual ISO/account boundaries, Mod 10 variants and required terminal appendix",
        "  values cannot be closed by the existing synthetic flags or common Luhn oracle.",
        "- Magstripe restrictions, chip Tag 9F6E prompting and host Prompt Table encoding remain distinct.", "",
        "## Evidence", "",
        "- [Per-BR reconciliations](reconciliations.json)",
        "- [Named counterpart relationships](counterpart-relations.json)",
        "- [Open source decisions with every impacted BR](source-decisions.json)",
        "- [Summary](summary.json)",
        "- [Frozen input identities](input-manifest.json)", "",
        "## Still unfinished outside this bounded register", "",
        "- Full overlap/applicability adjudication for the previously unreviewed 1,326 legacy BRs.",
        "- Whole-spec obligation derivation across 2,936 retained ATL105 source units.",
        "- Exhaustive counterpart search beyond the explicitly named relationships.",
        "- Full conditional-message oracles, external approvals and device/crypto/host execution.", "",
    ])


def run(delivery, output=OUTPUT, check=False):
    delivery, output = delivery.resolve(), output.resolve()
    if output.is_relative_to(delivery) or delivery.is_relative_to(output):
        raise ValueError("Output must be disjoint from original delivery")
    if not check and output.exists():
        raise ValueError("New output directory required")
    frozen_delivery = completion.v1.freeze_delivery(delivery)
    with contextlib.redirect_stdout(io.StringIO()):
        parent.run(delivery, parent.OUTPUT, check=True)
    paths = [DECISIONS, Path(__file__), Path(parent.__file__), Path(completion.__file__),
             Path(completion.fleet.__file__), base.LEGACY, completion.fleet.PAGES,
             completion.fleet.INDEX, completion.fleet.MANIFEST,
             completion.fleet.STATUS_FILE, completion.fleet.PLAN,
             base.OLD_SOURCE, base.gates.SOURCE, base.gates.__file__,
             Path(completion.fleet.petroleum_visa_fleet.__file__),
             Path(completion.fleet.petroleum_voyager.__file__)]
    authority = base.gates.load(completion.fleet.MANIFEST)
    petroleum_pdf = (base.ATL / authority["source"]["path"]).resolve()
    if not petroleum_pdf.is_relative_to(base.ATL.resolve()):
        raise ValueError("Petroleum source path escapes specification pack")
    paths.append(petroleum_pdf)
    paths = [Path(path) for path in paths]
    paths.extend(path for folder in (parent.OUTPUT, parent.v1.OUTPUT, completion.OUTPUT)
                 for path in sorted(folder.iterdir()) if path.is_file())
    paths.extend(completion.fleet.KB / folder / name for folder in completion.COMPANIONS.values()
                 for name in ("context-input.json", "chain-input.json"))
    manifest = [{"path": str(path.resolve()), "sha256": base.digest(path)}
                for path in dict.fromkeys(paths)]
    doc = base.gates.load(DECISIONS)
    companion_path = completion.OUTPUT / "fleet-companion-chains.json"
    if base.digest(companion_path) != doc["companionChainsSha256"]:
        raise ValueError("Reviewed companion population changed; re-adjudicate decisions")
    sources = completion.fleet.load_sources()
    companions = base.gates.load(companion_path)
    configurations = {
        context: base.gates.load(completion.fleet.KB / folder / "context-input.json")
        for context, folder in completion.COMPANIONS.items()}
    reviews, questions, links = reconcile(
        doc, companions, base.gates.load(completion.OUTPUT / "model-package.json"),
        parent.read_csv(parent.OUTPUT / "legacy-semantic-register.csv"), configurations, sources)
    summary = {
        "status": "BOUNDED_COMPANION_RECONCILIATION_RECORDED_NOT_WHOLE_SPEC_COMPLETION",
        "counts": {"companionRules": len(reviews),
                   "draftObligations": sum(len(row["draftObligations"]) for row in reviews),
                   "namedCounterpartRelations": len(links), "unresolvedSourceComparisons": len(questions),
                   "rulesWithOpenSourceDecisions": sum(bool(row["unresolvedSourceRefs"]) for row in reviews)},
        "contexts": dict(sorted(Counter(row["context"] for row in reviews).items())),
        "allRetainedCompanionRulesHaveDecisions": True, "exhaustiveLegacyOverlapComplete": False,
        "wholeSpecSemanticsComplete": False, "approvedDenominator": None,
        "confirmedRequirementCoveragePercent": None, "baselineChanged": False, "executionReady": False,
    }
    outputs = {"summary.json": summary, "reconciliations.json": reviews,
               "source-decisions.json": questions, "counterpart-relations.json": links,
               "input-manifest.json": manifest}
    expected = {name: (json.dumps(value, ensure_ascii=True, indent=2) + "\n").encode("utf-8")
                for name, value in outputs.items()}
    expected["REPORT.md"] = report(summary).encode("utf-8")
    if (completion.v1.freeze_delivery(delivery) != frozen_delivery
            or any(base.digest(Path(row["path"])) != row["sha256"] for row in manifest)):
        raise ValueError("Petroleum reconciliation inputs changed during processing")
    if check:
        parent.v1.verify_outputs(output, expected)
    else:
        output.mkdir(parents=True)
        for name, content in expected.items():
            (output / name).write_bytes(content)
    print(json.dumps(summary, indent=2))
    return summary


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--delivery", type=Path, required=True)
    parser.add_argument("--output", type=Path, default=OUTPUT)
    parser.add_argument("--check", action="store_true")
    args = parser.parse_args()
    run(args.delivery, args.output, args.check)


if __name__ == "__main__":
    main()
