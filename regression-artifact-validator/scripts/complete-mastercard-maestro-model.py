"""Reconcile legacy sources and extend the bounded Mastercard/Maestro draft model without approval."""

import argparse
import copy
import importlib.util
import json
from collections import Counter, defaultdict
from pathlib import Path

import mastercard_maestro_offline as offline
import mastercard_maestro_reconciliation as reconciliation

SPEC = importlib.util.spec_from_file_location(
    "mm_v1", Path(__file__).with_name("build-mastercard-maestro-model.py"))
base = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(base)
ADDITIONAL = base.MODEL.with_name("additional-rules.json")
OUTPUT = base.REVIEW / "mc-maestro-model-v2"
V1 = base.DEFAULT_OUTPUT


def expanded_document():
    document = copy.deepcopy(base.gates.load(base.MODEL))
    added = base.gates.load(ADDITIONAL)
    if (added["schemaVersion"] != 1 or added["specificationVersion"] != "2026-4"
            or added["status"] != "DRAFT_REVIEW_REQUIRED"):
        raise ValueError("Additional rules must preserve draft release scope")
    document["rules"].extend(added["rules"])
    base.unique(document["rules"])
    return document


def delivery_bindings(document, delivery, crosswalk):
    """Keep actual TS/TC/file identities for each candidate; no semantic promotion."""
    for context, package in base.CONTEXT_PACKAGE.items():
        root = delivery / package
        support = base.gates.load(root / "requirements_scenarios" / "scenario_supporting_rules.json")
        cases = base.gates.read_cases(root)
        base.unique(cases)
        for row in (item for item in crosswalk if item["context"] == context):
            for candidate in row["candidates"]:
                identity = candidate["aiRuleId"]
                rule = support["rules"][identity]
                scenarios = {ts for ts, rules in support["by_scenario"].items() if identity in rules}
                case_ids, explicit_ids, td = [], [], set()
                for case in cases:
                    if case["scenario_id"] not in scenarios:
                        continue
                    case_ids.append(case["id"])
                    if any(identity in check.get("kb_rule_ids", []) for check in case.get("rule_checks") or []):
                        explicit_ids.append(case["id"])
                    declarations = set(case.get("test_data_leg_files") or [])
                    if case.get("test_data_file"):
                        declarations.add(case["test_data_file"])
                    for declaration in declarations:
                        path = base.gates.resolve_reference(root, declaration)
                        if not path.is_file():
                            raise ValueError("Candidate physical declaration missing from frozen delivery")
                        td.add(declaration)
                if len(case_ids) != candidate["linkedCaseCount"] or len(scenarios) != candidate["linkedScenarioCount"]:
                    raise ValueError("Frozen candidate counts differ from actual delivery bindings")
                candidate.update(scenarioIds=sorted(scenarios), caseIds=sorted(case_ids),
                                 explicitPredicateCaseIds=sorted(explicit_ids),
                                 physicalTdPaths=sorted(td),
                                 aiCondition=rule.get("condition"), aiConstraint=rule.get("constraint"),
                                 relatedEntityIds=rule.get("related_entity_ids"),
                                 independentCondition=next(item["condition"] for item in document["rules"]
                                     if "BR-" + item["id"] == row["independentBrId"]),
                                 conditionAgreement="UNASSESSED_NO_SEMANTIC_PROMOTION",
                                 assessment="IDENTITY_BOUND_CANDIDATE_NOT_SEMANTICALLY_CONFIRMED")
    return crosswalk


def physical_inventory(delivery, frozen):
    rows = []
    package_context = {value: key for key, value in base.CONTEXT_PACKAGE.items()}
    for identity in frozen:
        relative = Path(identity["path"])
        if len(relative.parts) < 3 or relative.parts[1] != "test_data" or relative.suffix != ".json":
            continue
        package = relative.parts[0]
        if package not in package_context:
            raise ValueError("Unknown frozen network package")
        path = base.gates.resolve_reference(delivery, identity["path"])
        if base.digest(path) != identity["sha256"]:
            raise ValueError("Physical observation is not bound to frozen bytes")
        result = offline.field_observations(base.gates.load(path), package_context[package])
        rows.append({"package": package, "path": str(relative),
                     "sha256": identity["sha256"], **result,
                     "scope": "PRODUCER_JSON_FIELD_OBSERVATION_NOT_WIRE_OR_HOST",
                     "ruleIds": ["MM4-015", "MM4-017", {"MC_CREDIT": "MM4-019",
                                 "MC_FLEET": "MM4-020", "MAESTRO_DEBIT": "MM4-021"}[package_context[package]]]
                     if result["assessment"] != "NOT_APPLICABLE_NO_SEGMENT100" else []})
    return rows


def scope_matrix(document, source_units):
    """Persist all unresolved context dimensions rather than filling them from AI labels."""
    rows = []
    for context in base.CONTEXT_PACKAGE:
        for category in ("PURCHASE", "AUTHORIZATION", "RETURN", "REVERSAL", "COMPLETION",
                         "PARTIAL_APPROVAL", "TOR", "BALANCE_INQUIRY", "TOTALS"):
            rows.append({"context": context, "transactionCategory": category,
                         "sourceUnitIdsForReview": [unit["sourceUnitId"] for unit in source_units
                             if unit["networkMention"] and category.replace("_", " ").lower() in unit["quote"].lower()],
                         "overlayRuleIds": [rule["id"] for rule in document["rules"]
                                            if context in rule["contexts"]],
                         "ruleIdMeaning": "Context rule inventory; not category-specific applicability",
                         "disposition": "SOURCE_SCOPE_REVIEW_REQUIRED",
                         "entryMethods": ["MAGSTRIPE", "MANUAL", "EMV_CONTACT", "EMV_CONTACTLESS",
                                          "COF", "FALLBACK", "CONTACTLESS_MOBILE"],
                         "entryMethodMeaning": "Review axes only, not a permitted combination Cartesian product",
                         "excludedFromConfirmedDenominator": True})
    return rows


def canonical_gate_report(result):
    # Native uncovered-scenario findings come from a set; retain duplicates, not iteration order.
    return {**result, "issues": sorted(result["issues"], key=lambda row: json.dumps(row, sort_keys=True))}


def report(summary):
    c = summary["counts"]
    return "\n".join([
        "# Mastercard / Maestro model v2 - pending-work implementation", "",
        "## Implemented repository-side work",
        f"- **{c['overlayRules']}** page/hash-pinned draft BRs, **{c['contextRows']}** applicability rows,",
        f"  **{c['scenarios']} TS / {c['cases']} TC / {c['data']} TD** logical control/mutant chains.",
        f"- Every **{c['legacyRules']}** legacy BR retained with per-anchor old/new block identity,",
        "  changed/missing/ambiguous locator findings, original status and three context decisions.",
        f"- **{c['overlapCandidates']}** legacy/overlay overlap proposals retained; no automatic merges.",
        f"- All **{c['sourcePages']}** source pages (including front matter) scanned; **{c['sourceUnits']}** source units",
        "  retained, including nonnormative/table-like text. Modal language is only a triage label.",
        f"- **{c['scopeRows']}** context/category review rows retain entry-method and product scope boundaries.",
        f"- All **{c['aiRuleReviewRows']}** package-qualified AI supporting rules retained; crosswalk",
        f"  has **{c['crosswalkRows']}** rows, **{c['rowsWithCandidates']}** with review candidates and exact TS/TC/TD identities.",
        f"- **{c['wireFixtures']}** synthetic ASCII Segment 100 controls/mutants executed against a bounded",
        "  framing/length/sequence/card-code checker. These are not complete ATL105 messages.",
        f"- **{c['physicalTd']}** delivered TD JSON files independently observed for bounded field",
        "  representations; no payload account/PIN data is copied into the ledgers.",
        f"- Physical observation dispositions: `{summary['physicalDispositions']}`.",
        f"- Existing native-gate status: `{summary['nativeGateStatuses']}`; readiness remains BLOCKED.", "",
        "## What remains external or review-bound",
        "An exact normalized source block match proves only block-text identity, not legacy BR",
        "equivalence, atomicity or applicability. Changed blocks and overlap proposals need",
        "independent semantic adjudication; lexical candidates are never confirmed.",
        "The complete source-unit population is now mechanically accounted for, but is NOT a",
        "complete classified Mastercard/Maestro BR denominator. Source units can span multiple",
        "obligations or contain information only. No hidden threshold makes them atomic rules.",
        "Fleet inheritance, Maestro savings/ACH eligibility, Signature Debit/Common AID, MOTO",
        "completion, credit/FSA TOR conflicts and issuer product subtypes remain explicit decisions.",
        "Client credentials, cryptograms, actual device procedures, response captures and host",
        "execution were not supplied. No synthetic data substitutes for that evidence.",
        "Approved denominator and confirmed coverage remain **null / NOT_ASSESSED**.", "",
        "## Fixture boundaries",
        "The ASCII fixture is Segment 100 only, with deliberately synthetic non-card/terminal",
        "values and fields through Sequence Number. It checks just declared field separation,",
        "length, Segment Type, six-digit sequence and contextual card-code mapping. It is not",
        "a positive full-message fixture or proof of all required/conditional fields.",
        "Logical companion and lifecycle checks require explicitly supplied preconditions.",
        "JSON object presence/count is never treated as serialized-wire evidence.", "",
        "## Persistent evidence",
        "- [Summary and input identities](summary.json)",
        "- [Expanded independent draft BR/TS/TC/TD](model-package.json)",
        "- [Applicability/denominator register](denominator-register.csv)",
        "- [Legacy release reconciliation](legacy-release-register.csv)",
        "- [Overlap reconciliation proposals](overlap-register.csv)",
        "- [All retained source units](source-unit-register.csv)",
        "- [Page scan completeness](source-page-register.csv)",
        "- [Context/category scope review](scope-register.csv)",
        "- [Delivery-bound candidate crosswalk](delivery-crosswalk.csv)",
        "- [Every AI rule, including unmatched](ai-rule-review-register.csv)",
        "- [Executed bounded physical fixture suite](wire-fixtures.json)",
        "- [Delivered physical TD observations](physical-observations.csv)",
        "- [BA/SME decisions and dependencies](decision-register.csv)",
        "- [Existing native gates](native-gates.json)",
        "- [Input manifest](input-manifest.json)", "",
    ])


def verify_generated(output, outputs, tables, report_text):
    expected_names = set(outputs) | set(tables) | {"REPORT.md"}
    if {path.name for path in output.iterdir()} != expected_names:
        raise ValueError("Generated evidence file population changed")
    for name, value in outputs.items():
        if base.gates.load(output / name) != value:
            raise ValueError(f"Stale generated evidence: {name}")
    for name, rows in tables.items():
        expected = [{key: json.dumps(value, ensure_ascii=True) if isinstance(value, (list, dict))
                     else str(value) if value is not None else "" for key, value in row.items()}
                    for row in rows]
        if base.read_csv(output / name) != expected:
            raise ValueError(f"Stale generated table: {name}")
    if (output / "REPORT.md").read_text(encoding="utf-8") != report_text:
        raise ValueError("Stale generated report")


def run(delivery, evidence, output, check=False):
    if output.resolve().is_relative_to(delivery.resolve()):
        raise ValueError("Output must be outside delivery")
    if not check and output.exists():
        raise ValueError("New output directory required")
    # Verify the original package and all frozen delivery evidence, preserving v1 byte-for-byte.
    base.run(delivery, evidence, V1, check=True)
    input_paths = [
        base.MODEL, ADDITIONAL, base.LEGACY, base.gates.SOURCE, base.OLD_SOURCE,
        Path(base.__file__), Path(__file__), Path(offline.__file__), Path(reconciliation.__file__),
        Path(base.gates.__file__), evidence / "summary.json", evidence / "input-manifest.csv",
        evidence / "supporting-rule-ledger.csv", V1 / "summary.json",
        base.FLEET_COMPANION / "training-package.json", base.FLEET_COMPANION / "chain-package.json",
    ]
    for package in base.CONTEXT_PACKAGE.values():
        input_paths.extend([delivery / package / "requirements_scenarios" / "scenario_supporting_rules.json",
                            delivery / package / "test_cases" / "test_cases.jsonl"])
    manifest = [{"path": str(path.resolve()), "sha256": base.digest(path)} for path in input_paths]
    document = expanded_document()
    old_text = base.OLD_SOURCE.read_text(encoding="utf-8-sig")
    new_text = base.gates.SOURCE.read_text(encoding="utf-8-sig")
    package, denominator = base.build_overlay(document, new_text, old_text)
    legacy = base.legacy_register(base.gates.load(base.LEGACY))
    old_index, _ = reconciliation.source_index(old_text)
    new_index, page_rows = reconciliation.source_index(new_text)
    release_rows, overlaps = reconciliation.reconcile_legacy(legacy, document["rules"], old_index, new_index)
    source_units, page_scan = reconciliation.obligation_inventory(page_rows, document["rules"])
    scopes = scope_matrix(document, source_units)
    crosswalk, retained_ai, _ = base.build_crosswalk(document, delivery,
                                                   base.read_csv(evidence / "supporting-rule-ledger.csv"))
    delivery_bindings(document, delivery, crosswalk)
    frozen = base.read_csv(evidence / "input-manifest.csv")
    physical = physical_inventory(delivery, frozen)
    wires = offline.fixture_suite()
    native = [canonical_gate_report(base.gates.validate_package(delivery / package_name))
              for package_name in base.CONTEXT_PACKAGE.values()]
    decision_rows = []
    for decision in document["blockedDecisions"]:
        decision_rows.append({**decision, "status": "EXTERNAL_OR_SEMANTIC_REVIEW_REQUIRED",
                              "repositoryEvidence": {
                                  "MM-Q01": "Source-page/unit inventory, legacy release and overlap registers",
                                  "MM-Q02": "Explicit context conditions; no silent credit-to-Fleet inheritance",
                                  "MM-Q03": "Generic code predicates retained; Maestro 012/013 not promoted",
                                  "MM-Q04": "Source and scope registers; no routing/program permissions invented",
                                  "MM-Q05": "Debit TOR approved-amount rule separated; credit/FSA conflict retained",
                                  "MM-Q06": "Mail/Phone completion explicitly not certified",
                                  "MM-Q07": "Logical and bounded ASCII fixtures; physical TD observations and gate findings",
                                  "MM-Q08": "Scope axes retained without inventing subtype fixtures",
                              }[decision["id"]],
                              "approved": False})
    overlap_by_rule = defaultdict(list)
    for row in overlaps:
        overlap_by_rule[row["overlayBrId"]].append(row["legacyBrId"])
    for row in denominator:
        row["legacyOverlap"] = overlap_by_rule[row["independentBrId"]]
        row["legacyOverlapDisposition"] = "CANDIDATES_RETAINED_NO_MERGE" if row["legacyOverlap"] else "NO_CANDIDATE_NOT_PROOF_OF_NO_OVERLAP"
    counts = {"overlayRules": len(document["rules"]), "contextRows": len(denominator),
              "scenarios": len(package["testScenarios"]), "cases": len(package["testCases"]),
              "data": len(package["testData"]), "legacyRules": len(release_rows),
              "overlapCandidates": len(overlaps), "sourcePages": len(page_scan),
              "sourceUnits": len(source_units), "scopeRows": len(scopes), "aiRuleReviewRows": len(retained_ai),
              "crosswalkRows": len(crosswalk), "rowsWithCandidates": sum(row["candidateCount"] > 0 for row in crosswalk),
              "wireFixtures": len(wires), "physicalTd": len(physical)}
    summary = {"artifact": "MASTERCARD_MAESTRO_MODEL_V2", "specificationVersion": "2026-4",
               "counts": counts, "confirmedRequirementCoveragePercent": None, "approvedDenominator": None,
               "semanticAssessmentStatus": "NOT_ASSESSED", "readiness": "BLOCKED",
               "legacyReleaseDispositions": dict(Counter(row["releaseEquivalence"] for row in release_rows)),
               "physicalDispositions": dict(Counter(row["assessment"] for row in physical)),
               "nativeGateStatuses": {row["package"]: row["gate"] for row in native},
               "sourcePolicy": "Complete page/paragraph scan is not semantic BR completeness",
               "inputs": manifest}
    outputs = {"summary.json": summary, "model-package.json": package, "wire-fixtures.json": wires,
               "native-gates.json": native, "input-manifest.json": manifest,
               "companion-evidence.json": base.companion_inventory()}
    tables = {"denominator-register.csv": denominator, "legacy-release-register.csv": release_rows,
              "overlap-register.csv": overlaps, "source-unit-register.csv": source_units,
              "source-page-register.csv": page_scan, "scope-register.csv": scopes,
              "delivery-crosswalk.csv": crosswalk, "ai-rule-review-register.csv": retained_ai,
              "physical-observations.csv": physical, "decision-register.csv": decision_rows}
    for row in manifest:
        if base.digest(Path(row["path"])) != row["sha256"]:
            raise ValueError("Model/source/delivery input changed during assessment")
    for row in frozen:
        if base.digest(base.gates.resolve_reference(delivery, row["path"])) != row["sha256"]:
            raise ValueError("Frozen AI file changed during assessment")
    actual_files = {path.resolve() for package_name in base.CONTEXT_PACKAGE.values()
                    for path in (delivery / package_name).rglob("*") if path.is_file()}
    if actual_files != {base.gates.resolve_reference(delivery, row["path"]) for row in frozen}:
        raise ValueError("Delivery file population changed")
    if check:
        verify_generated(output, outputs, tables, report(summary))
    else:
        output.mkdir(parents=True)
        for name, value in outputs.items():
            (output / name).write_text(json.dumps(value, ensure_ascii=True, indent=2) + "\n", encoding="utf-8")
        for name, rows in tables.items():
            base.write_csv(output / name, rows)
        (output / "REPORT.md").write_text(report(summary), encoding="utf-8")
    print(json.dumps({key: value for key, value in summary.items() if key != "inputs"}, indent=2))


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--delivery", required=True, type=Path)
    parser.add_argument("--evidence", type=Path, default=base.DEFAULT_EVIDENCE)
    parser.add_argument("--output", type=Path, default=OUTPUT)
    parser.add_argument("--check", action="store_true")
    args = parser.parse_args()
    run(args.delivery, args.evidence, args.output, args.check)


if __name__ == "__main__":
    main()
