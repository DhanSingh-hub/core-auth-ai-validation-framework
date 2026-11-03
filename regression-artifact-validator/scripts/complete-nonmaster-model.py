"""Extend frozen non-Mastercard v1 with program rules and complete application-body shape checks."""

import argparse
import contextlib
import importlib.util
import io
import json
import re
from collections import Counter
from pathlib import Path

import nonmaster_application as application


def load_module(name, filename):
    spec = importlib.util.spec_from_file_location(name, Path(__file__).with_name(filename))
    module = importlib.util.module_from_spec(spec)
    spec.loader.exec_module(module)
    return module


v1 = load_module("nonmaster_v1", "build-nonmaster-model.py")
fleet = load_module("nonmaster_fleet", "train-petroleum-context.py")
base = v1.base
MODEL = v1.MODEL.with_name("nonmaster-program-rules.json")
OUTPUT = base.REVIEW / "nonmaster-model-v2"
COMPANIONS = {"VISA_FLEET": "visa-fleet", "VOYAGER_FLEET": "voyager-fleet"}


def input_manifest():
    authority = base.gates.load(fleet.KB / "ingestion-manifest.json")
    source_pdf = (base.ATL / authority["source"]["path"]).resolve()
    if not source_pdf.is_relative_to(base.ATL.resolve()):
        raise ValueError("Petroleum source path escapes specification pack")
    paths = [
        MODEL, Path(__file__), Path(application.__file__), Path(v1.__file__), Path(base.__file__),
        Path(base.gates.__file__), Path(fleet.__file__), fleet.PLAN, fleet.PAGES, fleet.INDEX,
        fleet.MANIFEST, base.gates.SOURCE, base.OLD_SOURCE, base.LEGACY, v1.MODEL,
        source_pdf,
        Path(fleet.petroleum_chain.__file__), Path(fleet.petroleum_visa_fleet.__file__),
        Path(fleet.petroleum_voyager.__file__),
    ]
    paths.extend(path for path in sorted(v1.OUTPUT.iterdir()) if path.is_file())
    for folder in COMPANIONS.values():
        paths.extend(path for path in sorted((fleet.KB / folder).iterdir()) if path.is_file())
    return [{"path": str(path.resolve()), "sha256": base.digest(path)}
            for path in dict.fromkeys(paths)]


def source_catalog():
    base.gates.trusted_source()
    manifest_path = fleet.KB / "ingestion-manifest.json"
    manifest = base.gates.load(manifest_path)
    pages_path = fleet.KB / "pages.json"
    if base.digest(pages_path) != manifest["outputs"]["pages.json"]:
        raise ValueError("Petroleum page population/hash changed")
    pdf = (base.ATL / manifest["source"]["path"]).resolve()
    if not pdf.is_relative_to(base.ATL.resolve()) or base.digest(pdf) != manifest["source"]["sha256"]:
        raise ValueError("Petroleum source authority/hash mismatch")
    pages = base.gates.load(pages_path)
    return {
        "ATL105": {"version": "2026-4", "sha256": base.gates.SOURCE_SHA256,
                   "pages": base.source_pages(base.gates.SOURCE.read_text(encoding="utf-8-sig"))},
        "PETROLEUM": {"version": "2025-1", "sha256": manifest["source"]["sha256"],
                      "pageInventorySha256": manifest["outputs"]["pages.json"],
                      "pages": {r["pdfPage"]: base.norm(r["text"]) for r in pages["pages"]}},
    }, [manifest_path, pages_path, pdf]


def program_document():
    doc = base.gates.load(MODEL)
    if (doc["schemaVersion"] != 1 or doc["status"] != "DRAFT_REVIEW_REQUIRED"
            or doc["baselineChanged"] is not False):
        raise ValueError("Program rules cannot promote baseline or approval")
    base.unique(doc["rules"])
    if set(doc["contexts"]) & v1.CONTEXTS:
        raise ValueError("Program context collides with frozen network scope")
    return doc


def build_program_chains(doc, sources):
    if doc["status"] != "DRAFT_REVIEW_REQUIRED" or doc["baselineChanged"] is not False:
        raise ValueError("Cannot promote draft program chains")
    base.unique(doc["rules"])
    package = {name: [] for name in ("businessRequirements", "testScenarios", "testCases", "testData")}
    register = []
    for rule in doc["rules"]:
        contexts = rule["contexts"]
        if (not contexts or len(contexts) != len(set(contexts))
                or set(contexts) - doc["contexts"].keys() or not rule["condition"].strip()):
            raise ValueError("Explicit unique program contexts and conditions required")
        source = sources[rule["source"]]
        pages = [rule["page"]] + rule.get("additionalPages", [])
        if len(base.norm(rule["quote"])) < 20 or any(
                base.norm(rule["quote"]) not in source["pages"].get(page, "") for page in pages):
            raise ValueError(f"Program source quote/page mismatch: {rule['id']}")
        if not v1.evaluate(rule["predicate"], rule["valid"]) or v1.evaluate(rule["predicate"], rule["invalid"]):
            raise ValueError(f"Program control/mutant mismatch: {rule['id']}")
        changed = {key for key in rule["valid"].keys() | rule["invalid"].keys()
                   if rule["valid"].get(key) != rule["invalid"].get(key)}
        if len(changed) != 1:
            raise ValueError("Program mutant must isolate one supplied observation")
        br = "BR-" + rule["id"]
        evidence = {"source": rule["source"], "version": source["version"],
                    "sha256": source["sha256"], "pages": pages, "quote": rule["quote"]}
        package["businessRequirements"].append({
            "id": br, "statement": rule["statement"], "sourceEvidence": evidence,
            "contexts": contexts, "condition": rule["condition"], "status": "REVIEW_REQUIRED",
            "atomicity": "DRAFT_NOT_CERTIFIED"})
        for context in contexts:
            ts = f"TS-{rule['id']}-{context}"
            package["testScenarios"].append({
                "id": ts, "requirementIds": [br], "context": context,
                "preconditions": [rule["condition"]], "status": "REVIEW_REQUIRED"})
            register.append({
                "independentBrId": br, "context": context, "condition": rule["condition"],
                "source": rule["source"], "sourceVersion": source["version"], "sourcePages": pages,
                "sourceQuote": rule["quote"], "sourceSha256": source["sha256"],
                "approval": "REVIEW_REQUIRED", "denominatorEligibility": "BLOCKED_PENDING_SCOPE_AND_COMPLETENESS"})
            for label, facts, expected in (("CONTROL", rule["valid"], "PASS"),
                                           ("MUTANT", rule["invalid"], "FAIL")):
                tc, td = f"TC-{rule['id']}-{context}-{label}", f"TD-{rule['id']}-{context}-{label}"
                package["testCases"].append({
                    "id": tc, "scenarioIds": [ts], "testDataIds": [td],
                    "assertions": [{"requirementId": br, "predicate": rule["predicate"]}],
                    "expectedOutcome": expected, "status": "REVIEW_REQUIRED"})
                package["testData"].append({
                    "id": td, "testCaseIds": [tc], "payload": facts, "expectedValidation": expected,
                    "observedValidation": "PASS" if v1.evaluate(rule["predicate"], facts) else "FAIL",
                    "kind": "SYNTHETIC_LOGICAL_NOT_HOST_MESSAGE", "readiness": "REVIEW_REQUIRED"})
    return package, register


def companion_chains():
    evidence, chains, inputs = [], {}, []
    for context, folder in COMPANIONS.items():
        # Re-execute existing source checks and mutation validators; no authority promotion.
        outputs = fleet.build(folder)
        fleet.check_outputs(outputs, fleet.KB / folder)
        training = json.loads(outputs["training-package.json"])
        chain = json.loads(outputs["chain-package.json"])
        if training["certified"] or training["executionReady"] or training["baselineChanged"]:
            raise ValueError("Fleet approval/status changed; reconcile before reuse")
        for name in ("businessRequirements", "testScenarios", "testCases", "testData"):
            base.unique(chain[name])
        evidence.append({
            "context": context, "source": "PETROLEUM_2025_1", "status": training["status"],
            "authority": chain["manifest"]["authority"],
            "counts": {key: len(chain[key]) for key in
                       ("businessRequirements", "testScenarios", "testCases", "testData")},
            "mutationSuite": chain["mutationSuite"],
            "serialization": chain["atl105Serialization"],
            "disposition": "EXISTING_SOURCE_REVERIFIED_DRAFT_NOT_MERGED_WITH_DENOMINATOR"})
        chains[context] = chain
        inputs.extend((fleet.KB / folder / name) for name in outputs)
        inputs.extend(fleet.KB / folder / name for name in ("context-input.json", "chain-input.json"))
    return evidence, chains, inputs


def physical_observations(delivery, frozen):
    profiles = {"american_express_credit": "AMERICAN_EXPRESS", "interlink_debit": "INTERLINK"}
    bindings = {}
    for package in profiles:
        root = delivery / package
        cases = base.unique(base.gates.read_cases(root))
        run = base.gates.load(root / "run_set.json")
        for row in run["index"]:
            case = cases[row["id"]]
            for name in row.get("test_data_files", []):
                path = base.gates.resolve_reference(root, "test_data\\" + name)
                identity = str(path.relative_to(delivery))
                bindings.setdefault(identity, []).append({
                    "caseId": case["id"], "scenarioId": case["scenario_id"],
                    "polarity": case.get("polarity"), "negativeClass": case.get("negative_class"),
                    "violatedElementName": case.get("violated_element_name")})
    rows = []
    for identity in frozen:
        path = Path(identity["path"])
        if path.parent.name != "test_data" or path.suffix != ".json":
            continue
        result = application.observe_json(
            base.gates.load(base.gates.resolve_reference(delivery, identity["path"])),
            profiles[path.parts[0]])
        rows.append({**identity, "package": path.parts[0], **result,
                     "caseIntents": bindings.get(identity["path"], []),
                     "defectClassification": "OBSERVATION_REQUIRES_OBJECTIVE_AND_SOURCE_REVIEW",
                     "scope": "OBSERVED_JSON_APPLICATION_FIELDS_NOT_SERIALIZED_WIRE",
                     "executionReady": False})
    return rows


def work_register():
    return [
        {"id": "NM-W01", "work": "Named-network source-pinned control/mutant chains",
         "disposition": "IMPLEMENTED_BOUNDED", "evidence": "model-package.json"},
        {"id": "NM-W02", "work": "PINless, benefit, stored-value, loyalty, check and petroleum program chains",
         "disposition": "IMPLEMENTED_BOUNDED", "evidence": "model-package.json; fleet-companion-chains.json",
         "limits": "Representative source obligations, not every clause/profile combination."},
        {"id": "NM-W03", "work": "Complete Standard Segment 100 and scoped financial application-body parsing",
         "disposition": "IMPLEMENTED_SCOPED", "evidence": "application-fixtures.json; application-observations.csv",
         "limits": "All 17 fields, measured length, header/count, segment boundaries; not all companion contents or transport."},
        {"id": "NM-W04", "work": "Whole-spec semantic obligation classification and legacy overlap adjudication",
         "disposition": "REPOSITORY_SEMANTIC_WORK_REMAINS", "evidence": "scope-register.json",
         "limits": "Page scan and bounded draft rules are not a complete denominator. No approval can substitute for unfinished derivation."},
        {"id": "NM-W05", "work": "Producer-compatible serializers/oracles for every conditional segment and message family",
         "disposition": "REPOSITORY_IMPLEMENTATION_REMAINS", "evidence": "application-fixtures.json",
         "limits": "Opaque companion content is review-required; no complete serializer is claimed."},
        {"id": "NM-W06", "work": "Supply remaining AI network/program deliveries and versioned routing/merchant contracts",
         "disposition": "EXTERNAL_EVIDENCE_REQUIRED", "evidence": "scope-register.json"},
        {"id": "NM-W07", "work": "Approve atomicity, release applicability, source precedence and denominator",
         "disposition": "EXTERNAL_APPROVAL_REQUIRED", "evidence": "decision-register.csv"},
        {"id": "NM-W08", "work": "Execute real device, crypto, simulator/host transactions and capture responses",
         "disposition": "EXTERNAL_EXECUTION_REQUIRED", "evidence": "native-gates.json"},
    ]


def report(summary):
    c = summary["counts"]
    lines = ["# Non-Mastercard v2 program and application-body continuation", "",
             f"- {c['draftRules']} combined draft BRs, {c['scenarios']} TS, {c['cases']} TC / {c['data']} logical TD.",
             f"- {c['programContexts']} new processing/program contexts beyond the seven named v1 contexts.",
             f"- Reverified Visa/Voyager companions: {c['companionRules']} draft BRs / {c['companionCases']} TC/TD, kept separate.",
             f"- {c['applicationFixtures']} executed complete-Standard/application-body shape controls and isolated mutants.",
             f"- {c['physicalTd']} delivered JSON messages independently observed without copying account/PIN payloads.",
             "- v1 is preserved and reverified; no original AI delivery was changed.",
             "- Approved denominator / confirmed semantic coverage: **null / NOT_ASSESSED**.",
             "- Execution readiness: **BLOCKED**.", "", "## Physical observations"]
    for outcome, count in summary["applicationDispositions"].items():
        lines.append(f"- {outcome}: {count}")
    lines += ["", "## Scope boundaries",
              application.LIMITS,
              "The new parser checks all 17 standard fields, explicit interior blanks, measured ASCII lengths,",
              "message identifier, segment population and length-delimited segment boundaries. The synthetic",
              "positive is one non-TransArmor financial application body, not an authorized terminal/account",
              "or network-positive transaction. Companion bodies remain review-required without their oracle.",
              "JSON declarations are not byte-level observations. Missing serialized producer output remains open.",
               "Field failures are not automatically AI defects: testcase polarity, negative class and intended",
               "violated element are retained for review; intentional negative data must not be repaired as a defect.",
              "Petroleum rules retain their 2025-1 authority; they are not relabelled ATL105 2026-4 rules.",
              "WEX fleet SAF recovery, COMDATA card SAF prohibition, COMDATA fuel-price retry and WEX Money",
              "Code manual entry are deliberately separate. Generic PINless/EBT/check programs are not brands.",
              "", "## Still unfinished",
              "Whole-spec semantic classification, legacy overlap adjudication and complete conditional-segment",
              "serializers/oracles remain repository work, not merely external approvals. This bounded package",
              "does not claim to finish every network/program requirement. See the completion register for",
              "implemented, repository-pending and external-pending items. Existing native failures are not repaired.",
              "", "## Evidence"]
    for name in ("summary.json", "model-package.json", "program-denominator-register.csv",
                 "scope-register.json", "fleet-companion-evidence.json", "fleet-companion-chains.json",
                 "application-fixtures.json", "application-observations.csv", "native-gates.json",
                 "completion-register.csv", "decision-register.csv", "input-manifest.json",
                 "delivery-manifest.csv"):
        lines.append(f"- [{name}]({name})")
    return "\n".join(lines) + "\n"


def run(delivery, output=OUTPUT, check=False):
    delivery, output = delivery.resolve(), output.resolve()
    if output.is_relative_to(delivery) or delivery.is_relative_to(output):
        raise ValueError("Output and delivery must be disjoint")
    if not check and output.exists():
        raise ValueError("New output directory required")
    with contextlib.redirect_stdout(io.StringIO()):
        v1.run(delivery, v1.OUTPUT, check=True)
    manifest = input_manifest()
    sources, _ = source_catalog()
    for pin in application.SOURCE_PINS:
        if base.norm(pin["quote"]) not in sources["ATL105"]["pages"].get(pin["page"], ""):
            raise ValueError("Application layout source/page pin changed")
    doc = program_document()
    program, denominator = build_program_chains(doc, sources)
    original = base.gates.load(v1.OUTPUT / "model-package.json")
    model = {key: original[key] + program[key] for key in original}
    for records in model.values():
        base.unique(records)
    companions, companion_packages, _ = companion_chains()
    frozen = base.read_csv(v1.OUTPUT / "delivery-manifest.csv")
    if v1.freeze_delivery(delivery) != frozen:
        raise ValueError("Frozen delivery file population or hashes changed")
    physical = physical_observations(delivery, frozen)
    app_fixtures = application.fixture_suite()
    scopes = base.gates.load(v1.OUTPUT / "scope-register.json")
    for context, profile in doc["contexts"].items():
        scopes.append({"context": context, **profile,
                       "ruleIds": [r["id"] for r in doc["rules"] if context in r["contexts"]],
                       "deliveryStatus": "NOT_SUPPLIED",
                       "disposition": "SOURCE_BOUND_PARTIAL_DRAFT"})
    for row in companions:
        scopes.append({"context": row["context"], "kind": "FLEET_PROGRAM", "deliveryStatus": "NOT_SUPPLIED",
                       "disposition": row["disposition"], "counts": row["counts"]})
    native = base.gates.load(v1.OUTPUT / "native-gates.json")
    summary = {
        "artifact": "NONMASTER_PROGRAM_APPLICATION_V2", "baselineChanged": False,
        "counts": {"draftRules": len(model["businessRequirements"]), "scenarios": len(model["testScenarios"]),
                   "cases": len(model["testCases"]), "data": len(model["testData"]),
                   "programContexts": len(doc["contexts"]), "programRules": len(doc["rules"]),
                   "companionRules": sum(r["counts"]["businessRequirements"] for r in companions),
                   "companionCases": sum(r["counts"]["testCases"] for r in companions),
                   "physicalTd": len(physical), "applicationFixtures": len(app_fixtures)},
        "contextCounts": dict(sorted(Counter(r["context"] for r in model["testScenarios"]).items())),
        "applicationDispositions": dict(sorted(Counter(r["assessment"] for r in physical).items())),
        "applicationFailureCounts": dict(sorted(Counter(code for r in physical for code in r["failures"]).items())),
        "applicationReviewCounts": dict(sorted(Counter(code for r in physical for code in r["review"]).items())),
        "nativeGateStatuses": {r["package"]: r["gate"] for r in native},
        "approvedDenominator": None, "confirmedRequirementCoveragePercent": None,
        "semanticAssessmentStatus": "NOT_ASSESSED", "readiness": "BLOCKED",
        "remainingRepositoryWork": True}
    decisions = base.gates.load(v1.MODEL)["decisions"] + [{
        "id": "NM-Q07", "owner": "BA/SME",
        "question": "Approve latest Petroleum authority and per-program conditional fields; do not inherit generic credit or another fleet program.",
        "status": "REVIEW_REQUIRED"}]
    outputs = {"summary.json": summary, "model-package.json": model, "scope-register.json": scopes,
               "fleet-companion-evidence.json": companions, "fleet-companion-chains.json": companion_packages,
               "application-fixtures.json": app_fixtures, "native-gates.json": native,
               "input-manifest.json": manifest}
    tables = {"program-denominator-register.csv": denominator, "application-observations.csv": physical,
              "completion-register.csv": work_register(), "decision-register.csv": decisions,
              "delivery-manifest.csv": frozen}
    expected = {name: (json.dumps(value, ensure_ascii=True, indent=2) + "\n").encode("utf-8")
                for name, value in outputs.items()}
    expected.update({name: v1.csv_text(rows).encode("utf-8-sig") for name, rows in tables.items()})
    expected["REPORT.md"] = report(summary).encode("utf-8")
    if v1.freeze_delivery(delivery) != frozen or input_manifest() != manifest:
        raise ValueError("Input population or hashes changed during assessment")
    if check:
        v1.verify_outputs(output, expected)
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
