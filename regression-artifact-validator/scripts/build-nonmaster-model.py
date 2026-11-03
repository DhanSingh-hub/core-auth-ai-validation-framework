"""Build source-bound non-Mastercard draft chains and read-only delivery evidence."""

import argparse
import importlib.util
import io
import csv
import json
import re
from collections import Counter, defaultdict
from pathlib import Path

import mastercard_maestro_offline as wire
import mastercard_maestro_reconciliation as reconciliation

SPEC = importlib.util.spec_from_file_location(
    "nonmaster_shared", Path(__file__).with_name("build-mastercard-maestro-model.py"))
base = importlib.util.module_from_spec(SPEC)
SPEC.loader.exec_module(base)
MODEL = base.MODEL.parent.parent / "nonmaster-2026-4-rules.json"
OUTPUT = base.REVIEW / "nonmaster-model-v1"
NETWORK_REVIEW = base.REVIEW / "all-network-requirements-review-v1"
CONTEXTS = {"AMEX_CREDIT", "VISA_CREDIT", "DISCOVER_CREDIT", "DINERS_CREDIT",
            "JCB_CREDIT", "INTERLINK_DEBIT", "STAR_SIGNATURE"}
DELIVERIES = {"AMEX_CREDIT": "american_express_credit", "INTERLINK_DEBIT": "interlink_debit"}
NETWORK_PATTERNS = {
    "AMERICAN_EXPRESS": r"\b(?:amex|american express)\b", "VISA": r"\bvisa\b",
    "DISCOVER": r"\bdiscover\b", "DINERS_CLUB": r"\bdiners club\b", "JCB": r"\bjcb\b",
    "INTERLINK": r"\binterlink\b", "STAR": r"\bstar\b",
}
LIMITS = ("Draft logical observations and bounded framing checks only; not complete wire messages, "
          "device evidence, cryptographic proof, host execution or a complete semantic denominator.")


def evaluate(predicate, facts):
    op, field = predicate["op"], predicate["field"]
    if op not in {"precededBy", "closedAfter", "maxDays"}:
        return base.evaluate(predicate, facts)
    if field not in facts:
        raise ValueError(f"Missing logical observation: {field}")
    value = facts[field]
    if op == "maxDays":
        if type(value) is not int or value < 0:
            raise ValueError("Elapsed days must be a nonnegative integer")
        return value <= predicate["maximum"]
    if not isinstance(value, list) or not value or any(not isinstance(x, str) or not x for x in value):
        raise ValueError("Ordered nonempty logical roles required")
    subsequent = set(predicate["subsequent"])
    allowed = subsequent | ({predicate["initial"]} if op == "precededBy"
                            else {predicate["closing"], "ORIGINAL_AUTH"})
    if set(value) - allowed:
        raise ValueError("Unknown lifecycle role")
    if op == "precededBy":
        if not set(value) & subsequent:
            raise ValueError("Applicable subsequent transaction observation required")
        seen = False
        for role in value:
            if role == predicate["initial"]:
                seen = True
            elif role in subsequent and not seen:
                return False
        return True
    if predicate["closing"] not in value:
        raise ValueError("Received completion observation required")
    closed = False
    for role in value:
        if closed and role in subsequent:
            return False
        if role == predicate["closing"]:
            closed = True
    return True


def document():
    doc = base.gates.load(MODEL)
    if (doc["schemaVersion"] != 1 or doc["specificationVersion"] != "2026-4"
            or doc["status"] != "DRAFT_REVIEW_REQUIRED" or doc["baselineChanged"] is not False
            or set(doc["contexts"]) != CONTEXTS):
        raise ValueError("Invalid draft release/context contract")
    actual = {key: value["deliveryPackage"] for key, value in doc["contexts"].items()
              if value["deliveryPackage"] is not None}
    if actual != DELIVERIES:
        raise ValueError("Unexpected delivery scope")
    base.unique(doc["rules"])
    base.unique(doc["decisions"])
    for rule in doc["rules"]:
        if ("contexts" in rule) == ("group" in rule):
            raise ValueError("Exactly one rule context selector required")
        rule["contexts"] = list(doc["groups"][rule.pop("group")]) if "group" in rule else rule["contexts"]
        if not rule["contexts"] or set(rule["contexts"]) - CONTEXTS:
            raise ValueError("Unknown rule context")
    return doc


def build_model(doc, source, old):
    if (doc["status"] != "DRAFT_REVIEW_REQUIRED" or doc["baselineChanged"] is not False
            or doc["specificationVersion"] != "2026-4" or set(doc["contexts"]) != CONTEXTS):
        raise ValueError("Cannot promote draft model or change release/context scope")
    base.unique(doc["rules"])
    pages = base.source_pages(source)
    model = {name: [] for name in ("businessRequirements", "testScenarios", "testCases", "testData")}
    register = []
    for rule in doc["rules"]:
        if not rule["contexts"] or len(set(rule["contexts"])) != len(rule["contexts"]) or set(rule["contexts"]) - CONTEXTS:
            raise ValueError("Missing, duplicate or unknown rule context")
        quote = base.norm(rule["quote"])
        if len(quote) < 20 or quote not in pages.get(rule["page"], ""):
            raise ValueError(f"Source quote/page mismatch: {rule['id']}")
        if not rule["condition"].strip():
            raise ValueError("Explicit applicability required")
        if not evaluate(rule["predicate"], rule["valid"]) or evaluate(rule["predicate"], rule["invalid"]):
            raise ValueError(f"Control/mutant mismatch: {rule['id']}")
        changed = {key for key in rule["valid"].keys() | rule["invalid"].keys()
                   if rule["valid"].get(key) != rule["invalid"].get(key)}
        if len(changed) != 1:
            raise ValueError("One isolated mutated observation required")
        br = "BR-" + rule["id"]
        anchor = {"specification": "ATL105", "version": "2026-4",
                  "section": rule["section"], "page": rule["page"]}
        model["businessRequirements"].append({
            "id": br, "statement": rule["statement"], "sourceAnchors": [anchor],
            "sourceEvidence": {"sha256": base.gates.SOURCE_SHA256, "quote": rule["quote"]},
            "condition": rule["condition"], "contexts": rule["contexts"],
            "atomicity": "DRAFT_NOT_CERTIFIED", "status": "REVIEW_REQUIRED"})
        for context in rule["contexts"]:
            ts = f"TS-{rule['id']}-{context}"
            model["testScenarios"].append({
                "id": ts, "requirementIds": [br], "context": context,
                "preconditions": [rule["condition"]], "status": "REVIEW_REQUIRED"})
            register.append({
                "independentBrId": br, "context": context, "condition": rule["condition"],
                "sourcePage": rule["page"], "sourceQuote": rule["quote"],
                "sourceSha256": base.gates.SOURCE_SHA256, "approval": "REVIEW_REQUIRED",
                "releaseComparison": "CLAUSE_PRESENT_IN_BOTH_RELEASES" if quote in base.norm(old)
                else "OLD_CLAUSE_NOT_IDENTICAL_REVIEW_REQUIRED",
                "denominatorEligibility": "BLOCKED_PENDING_COMPLETENESS_SCOPE_AND_APPROVAL"})
            for label, facts, expected in (("CONTROL", rule["valid"], "PASS"),
                                           ("MUTANT", rule["invalid"], "FAIL")):
                tc, td = f"TC-{rule['id']}-{context}-{label}", f"TD-{rule['id']}-{context}-{label}"
                model["testCases"].append({
                    "id": tc, "scenarioIds": [ts], "testDataIds": [td],
                    "assertions": [{"requirementId": br, "predicate": rule["predicate"]}],
                    "expectedOutcome": expected, "status": "REVIEW_REQUIRED"})
                model["testData"].append({
                    "id": td, "testCaseIds": [tc], "payload": facts, "expectedValidation": expected,
                    "observedValidation": "PASS" if evaluate(rule["predicate"], facts) else "FAIL",
                    "kind": "SYNTHETIC_LOGICAL_NOT_HOST_MESSAGE", "readiness": "REVIEW_REQUIRED"})
    return model, register


def validate_frame(raw):
    """Validate shared framing only; Appendix E card code cannot identify a network."""
    if not isinstance(raw, bytes):
        raise ValueError("Wire observation must be bytes")
    if not raw.isascii() or any(x < 32 and x != 28 for x in raw):
        return ["ASCII_OR_CONTROL"]
    if not raw.endswith(wire.FS) or len(raw[:-1].split(wire.FS)) != len(wire.FIELD_NAMES):
        return ["FRAMING"]
    fields = dict(zip(wire.FIELD_NAMES, (x.decode("ascii") for x in raw[:-1].split(wire.FS))))
    issues = []
    if fields["SegmentType"] != "100":
        issues.append("SEGMENT_TYPE")
    if not re.fullmatch(r"[0-9]{3}", fields["SegmentLength"]) or int(fields["SegmentLength"]) != len(raw):
        issues.append("LENGTH")
    if not re.fullmatch(r"[0-9]{6}", fields["SequenceNumber"]):
        issues.append("SEQUENCE")
    return issues


def fixtures():
    fields = dict.fromkeys(set(wire.FIELD_NAMES) - {"SegmentLength"}, "")
    fields.update(SegmentType="100", InformationByte="0", TerminalIdentifier="SYNTHETIC",
                  PromptCode="0020", AccountNumber="SYNTHETIC-NOT-A-CARD",
                  SequenceNumber="123456")
    raw = wire.serialize(fields)
    parts = raw[:-1].split(wire.FS)
    parts[1] = f"{len(raw) - 1:03}".encode("ascii")
    vectors = [
        ("CONTROL", raw, []),
        ("LENGTH", wire.FS.join(parts) + wire.FS, ["LENGTH"]),
        ("FRAMING", raw.replace(wire.FS, b"|", 1), ["FRAMING"]),
        ("SEQUENCE", wire.serialize({**fields, "SequenceNumber": "12345"}), ["SEQUENCE"]),
    ]
    result = []
    for label, payload, expected in vectors:
        actual = validate_frame(payload)
        if actual != expected:
            raise ValueError("Bounded wire fixture mismatch")
        result.append({"id": "NM-WIRE-" + label, "wireHex": payload.hex(),
                       "expectedIssues": expected, "observedIssues": actual,
                       "sourcePages": [223, 224, 409], "readiness": "REVIEW_REQUIRED",
                       "limits": "Shared framing only. Synthetic generic credit code 020 is NOT network identity. " + LIMITS})
    return result


def freeze_delivery(delivery):
    files = [path for name in DELIVERIES.values() for path in (delivery / name).rglob("*") if path.is_file()]
    for name in DELIVERIES.values():
        if not (delivery / name).is_dir():
            raise ValueError(f"Required delivery package missing: {name}")
    return [{"path": str(path.relative_to(delivery)), "sha256": base.digest(path)}
            for path in sorted(files)]


def assess_deliveries(doc, delivery, frozen):
    native, physical, inventories, bindings = [], [], {}, {}
    for context, name in DELIVERIES.items():
        root = delivery / name
        result = base.gates.validate_package(root)
        result["issues"] = sorted(result["issues"], key=lambda x: json.dumps(x, sort_keys=True))
        native.append(result)
        support = base.gates.load(root / "requirements_scenarios" / "scenario_supporting_rules.json")
        if support["meta"]["spec_version"] != "2026-4":
            raise ValueError("AI source release mismatch")
        cases = base.gates.read_cases(root)
        base.unique(cases)
        inventories[context], bindings[context] = support["rules"], defaultdict(list)
        for identity, ai in support["rules"].items():
            if identity != ai["rule_id"]:
                raise ValueError("Supporting-rule identity mismatch")
        for case in cases:
            files = sorted(set((case.get("test_data_leg_files") or [])
                               + ([case["test_data_file"]] if case.get("test_data_file") else [])))
            resolved = []
            missing = []
            for reference in files:
                path = base.gates.resolve_reference(root, reference)
                (resolved if path.is_file() else missing).append(reference)
            for identity in support["by_scenario"].get(case["scenario_id"], []):
                if identity not in support["rules"]:
                    raise ValueError("Scenario references unknown supporting rule")
                bindings[context][identity].append({
                    "caseId": case["id"], "scenarioId": case["scenario_id"],
                    "physicalTdPaths": resolved, "missingTdPaths": missing,
                    "hasDeclaredTd": bool(files),
                    "explicitPredicate": any(identity in check.get("kb_rule_ids", [])
                                             for check in case.get("rule_checks") or [])})
    for row in frozen:
        path = Path(row["path"])
        if path.parent.name != "test_data" or path.suffix != ".json":
            continue
        payload = base.gates.load(base.gates.resolve_reference(delivery, row["path"]))
        segments = list(wire.walk_segments(payload))
        emv = [s for s in segments if s["SegmentType"] == "130"]
        # Native gates provide the source-bound width/tag checks; retain file identities here.
        bad = any(not isinstance(s.get("EMVCardSequenceNumber"), str)
                  or not s["EMVCardSequenceNumber"].isascii()
                  or len(s["EMVCardSequenceNumber"]) != 3 for s in emv)
        physical.append({**row, "package": path.parts[0], "emvSegmentCount": len(emv),
                         "assessment": "FAIL_ELEMENT188_WIDTH" if bad else "NO_WIDTH_FAILURE_OBSERVED",
                         "limits": "Element 188 JSON width only; absence or passing width is not message validity."})
    crosswalk, retained = [], []
    for rule in doc["rules"]:
        for context in rule["contexts"]:
            candidates = []
            if context in inventories:
                for identity, ai in inventories[context].items():
                    reason = base.candidate_match(rule, ai)
                    if reason:
                        candidates.append({"aiRuleId": identity, "reason": reason,
                                           "aiCondition": ai.get("condition"), "aiConstraint": ai.get("constraint"),
                                           "bindings": bindings[context][identity],
                                           "semanticConfirmation": False})
            crosswalk.append({
                "independentBrId": "BR-" + rule["id"], "context": context,
                "deliveryPackage": DELIVERIES.get(context), "independentCondition": rule["condition"],
                "candidates": candidates, "confirmed": False,
                "disposition": ("DELIVERY_NOT_SUPPLIED" if context not in inventories else
                                "CANDIDATE_REVIEW_REQUIRED" if candidates else "NOT_ASSESSED")})
    for context, rules in inventories.items():
        for identity, ai in rules.items():
            retained.append({
                "package": DELIVERIES[context], "aiRuleId": identity, "statement": ai["statement"],
                "sourceId": ai.get("source_id"), "page": ai.get("page"),
                "candidateBrIds": [row["independentBrId"] for row in crosswalk if row["context"] == context
                                   and any(x["aiRuleId"] == identity for x in row["candidates"])],
                "semanticConfirmation": False})
    return native, physical, crosswalk, retained


def scope_register(doc, legacy_review):
    rows = []
    for context, profile in doc["contexts"].items():
        rows.append({"context": context, **profile, "kind": "NETWORK_PROGRAM",
                     "independentRuleIds": [rule["id"] for rule in doc["rules"] if context in rule["contexts"]],
                     "deliveryStatus": "SUPPLIED" if context in DELIVERIES else "NOT_SUPPLIED",
                     "disposition": "PARTIAL_DRAFT_NOT_COMPLETE_DENOMINATOR"})
    for row in legacy_review["requirements"]:
        if not row["id"].startswith("NET-REQ-TAX-"):
            continue
        rows.append({"context": row["id"].removeprefix("NET-REQ-TAX-"), "kind": "SECTION5_CATEGORY_NOT_A_BRAND",
                     "description": row["name"], "deliveryStatus": "NOT_CLASSIFIED",
                     "disposition": "REVIEW_REQUIRED_NO_AUTOMATIC_NETWORK_MAPPING"})
    for row in legacy_review["financialCardTypes"]:
        if row["id"] == "080":
            continue
        rows.append({"context": "CARD-" + row["id"], "kind": "FINANCIAL_CARD_CODE_NOT_A_NETWORK",
                     "description": row["name"], "deliveryStatus": "NOT_CLASSIFIED",
                     "disposition": "REVIEW_REQUIRED_PROGRAM_CHAIN_NOT_ASSERTED"})
    return rows


def csv_text(rows):
    stream = io.StringIO(newline="")
    writer = csv.DictWriter(stream, fieldnames=list(dict.fromkeys(key for row in rows for key in row)) or ["record"])
    writer.writeheader()
    for row in rows:
        writer.writerow({key: json.dumps(value, ensure_ascii=True) if isinstance(value, (dict, list))
                         else value for key, value in row.items()})
    return stream.getvalue()


def report(summary):
    c = summary["counts"]
    lines = ["# Non-Mastercard 2026-4 independent draft model", "",
             f"- {c['rules']} source-pinned BRs; {c['scenarios']} TS; {c['cases']} TC / {c['data']} logical TD.",
             f"- {c['legacyRules']} legacy BRs release-compared; no automatic applicability or overlap approval.",
             f"- {c['sourcePages']} pages / {c['sourceUnits']} source units retained, not a classified denominator.",
             f"- {c['physicalTd']} delivered physical TD files observed; {c['physicalWidthFailures']} fail Element 188 width.",
             "- Original Amex/Interlink delivery is hash-bound and never repaired in place.",
             "- Confirmed semantic coverage and approved denominator: **null / NOT_ASSESSED**.",
             "- Execution readiness: **BLOCKED**.", "", "## Network evidence", "",
             "| Context | Draft TS | Delivery | Native gate |", "|---|---:|---|---|"]
    for context, count in summary["contextCounts"].items():
        name = DELIVERIES.get(context)
        lines.append(f"| {context} | {count} | {name or 'NOT_SUPPLIED'} | "
                     f"{summary['nativeGateStatuses'].get(name, 'NOT_ASSESSED')} |")
    lines.extend(["", "## Boundaries and pending work",
                  LIMITS,
                  "Visa AE and Discover AF have separate source-bound lifecycle predicates. Visa windows are",
                  "not applied to Discover; generic PIN-debit restrictions are not applied to STAR Signature Debit.",
                  "Credit track/partial/reversal rules remain conditional; Amex track precedence, credit TOR",
                  "wording and approval-field naming remain review decisions. Conditions are supplied preconditions,",
                  "not independently validated device/merchant eligibility.",
                  "Diners and JCB reuse explicitly applicable electronic-credit clauses, not Discover IA rules.",
                  "Section 5 categories and financial card codes are retained as separate dimensions.",
                  "Fleet, EBT, PINless, proprietary, stored-value, loyalty and check programs require their own",
                  "source-qualified chains; their inventory is not completion. No missing delivery is called a defect.",
                  "Full-message serialization, runtime crypto, response capture, routing, complete source classification",
                  "and semantic adjudication remain pending. A fixture PASS never makes a delivery execution-ready.",
                  "", "## Evidence"])
    for name in ("summary.json", "model-package.json", "denominator-register.csv", "legacy-release-register.csv",
                 "overlap-register.csv", "source-page-register.csv", "source-unit-register.csv",
                 "scope-register.json", "shared-review-register.json", "delivery-crosswalk.json", "ai-rule-review-register.csv",
                 "physical-observations.csv", "native-gates.json", "wire-fixtures.json",
                 "decision-register.csv", "input-manifest.json", "delivery-manifest.csv"):
        lines.append(f"- [{name}]({name})")
    return "\n".join(lines) + "\n"


def verify_outputs(output, expected):
    if not output.is_dir() or {p.name for p in output.iterdir()} != set(expected):
        raise ValueError("Generated evidence file population changed")
    for name, value in expected.items():
        if (output / name).read_bytes() != value:
            raise ValueError(f"Stale generated evidence: {name}")


def run(delivery, output=OUTPUT, check=False):
    delivery, output = delivery.resolve(), output.resolve()
    if output.is_relative_to(delivery) or delivery.is_relative_to(output):
        raise ValueError("Output and delivery must be disjoint")
    if not check and output.exists():
        raise ValueError("New output directory required")
    base.gates.trusted_source()
    if base.digest(base.OLD_SOURCE) != base.OLD_HASH:
        raise ValueError("Old source hash mismatch")
    doc = document()
    frozen = freeze_delivery(delivery)
    previous_path = NETWORK_REVIEW / "review.json"
    previous = base.gates.load(previous_path)
    inputs = [MODEL, base.OLD_SOURCE, base.gates.SOURCE, base.LEGACY, previous_path,
              Path(__file__), Path(base.__file__), Path(base.gates.__file__),
              Path(wire.__file__), Path(reconciliation.__file__)]
    manifest = [{"path": str(p.resolve()), "sha256": base.digest(p)} for p in inputs]
    source, old = (p.read_text(encoding="utf-8-sig") for p in (base.gates.SOURCE, base.OLD_SOURCE))
    model, denominator = build_model(doc, source, old)
    old_index, _ = reconciliation.source_index(old)
    new_index, page_rows = reconciliation.source_index(source)
    legacy = base.legacy_register(base.gates.load(base.LEGACY))
    for row in legacy:
        row["contextDecisions"] = dict.fromkeys(doc["contexts"], "REVIEW_REQUIRED")
    release, overlaps = reconciliation.reconcile_legacy(legacy, doc["rules"], old_index, new_index)
    units, pages = reconciliation.obligation_inventory(page_rows, doc["rules"])
    for row in units:
        row["sourceUnitId"] = row["sourceUnitId"].replace("MM4-SRC-", "NM4-SRC-", 1)
        del row["networkMention"]
        row["networkMentions"] = [name for name, pattern in NETWORK_PATTERNS.items()
                                 if re.search(pattern, row["quote"], re.I)]
    native, physical, crosswalk, ai = assess_deliveries(doc, delivery, frozen)
    scopes = scope_register(doc, previous)
    summary = {
        "artifact": "NONMASTER_2026_4_DRAFT_MODEL", "specificationVersion": "2026-4",
        "counts": {"rules": len(doc["rules"]), "scenarios": len(denominator),
                   "cases": len(model["testCases"]), "data": len(model["testData"]),
                   "legacyRules": len(release), "overlapCandidates": len(overlaps),
                   "sourcePages": len(pages), "sourceUnits": len(units), "aiRules": len(ai),
                   "physicalTd": len(physical), "physicalWidthFailures": sum(
                       r["assessment"] == "FAIL_ELEMENT188_WIDTH" for r in physical),
                   "sharedWireFixtures": len(fixtures()), "deliveryFiles": len(frozen)},
        "contextCounts": dict(sorted(Counter(r["context"] for r in denominator).items())),
        "nativeGateStatuses": {r["package"]: r["gate"] for r in native},
        "nativeIssueCounts": {r["package"]: dict(sorted(Counter(x["code"] for x in r["issues"]).items()))
                              for r in native},
        "confirmedRequirementCoveragePercent": None, "approvedDenominator": None,
        "semanticAssessmentStatus": "NOT_ASSESSED", "readiness": "BLOCKED", "limits": LIMITS}
    outputs = {"summary.json": summary, "model-package.json": model, "scope-register.json": scopes,
               "shared-review-register.json": {
                   "status": "HISTORICAL_2026_3_REVIEW_NOT_ADOPTED_AS_2026_4",
                   "requirements": previous["requirements"], "flows": previous["flows"],
                   "meaning": "Retained whole shared library; not additional approved BRs or network permissions."},
               "delivery-crosswalk.json": crosswalk, "native-gates.json": native,
               "wire-fixtures.json": fixtures(), "input-manifest.json": manifest}
    tables = {"denominator-register.csv": denominator, "legacy-release-register.csv": release,
              "overlap-register.csv": overlaps, "source-page-register.csv": pages,
              "source-unit-register.csv": units, "ai-rule-review-register.csv": ai,
              "physical-observations.csv": physical, "decision-register.csv": doc["decisions"],
              "delivery-manifest.csv": frozen}
    expected = {name: (json.dumps(value, ensure_ascii=True, indent=2) + "\n").encode("utf-8")
                for name, value in outputs.items()}
    expected.update({name: csv_text(rows).encode("utf-8-sig") for name, rows in tables.items()})
    expected["REPORT.md"] = report(summary).encode("utf-8")
    if freeze_delivery(delivery) != frozen or any(base.digest(Path(r["path"])) != r["sha256"] for r in manifest):
        raise ValueError("Input population or hashes changed during assessment")
    if check:
        verify_outputs(output, expected)
    else:
        output.mkdir(parents=True)
        for name, value in expected.items():
            (output / name).write_bytes(value)
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
