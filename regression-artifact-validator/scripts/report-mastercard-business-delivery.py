"""Audit business-layer Mastercard deliveries without promoting links to BR coverage."""

from __future__ import annotations

import argparse
import csv
import hashlib
import json
from collections import Counter, defaultdict
from datetime import datetime, timezone
from pathlib import Path

import delivery_gates as gates

ROOT = Path(__file__).resolve().parents[1]
ATL = ROOT / "specifications" / "ATL105"
PACKAGES = ("mastercard_credit", "mastercard_fleet", "maestro_direct_debit")


def parse(text):
    return gates.parse_json(text, "Mastercard coverage input")


def read(path):
    return gates.load(path)


def digest(path):
    with path.open("rb") as stream:
        return hashlib.file_digest(stream, "sha256").hexdigest()


def index(records):
    result = {}
    for record in records:
        identity = record["id"]
        if identity in result:
            raise ValueError(f"Duplicate artifact ID: {identity}")
        result[identity] = record
    return result


def ids(value):
    if value is None:
        return set()
    if isinstance(value, str):
        return {value} if value else set()
    if not isinstance(value, list) or not all(isinstance(item, str) for item in value):
        raise ValueError("Expected string or array of string IDs")
    return set(value)


def br_links(case):
    return ids(case.get("requirement_ids")) | ids(case.get("requirement_id"))


def declarations(case):
    return sorted(ids(case.get("test_data_file")) | ids(case.get("test_data_leg_files")))


def resolve(root, declaration):
    return gates.resolve_reference(root, declaration)


def physical_observation(payload):
    if not isinstance(payload, dict) or len(payload) != 1:
        return {"status": "UNSUPPORTED_ENVELOPE", "segments": [], "countMatches": None}
    body = next(iter(payload.values()))
    if not isinstance(body, dict):
        return {"status": "UNSUPPORTED_BODY", "segments": [], "countMatches": None}
    segments = [str(value["SegmentType"]) for value in body.values()
                if isinstance(value, dict) and "SegmentType" in value]
    declared = body.get("NumberofSegments")
    matches = None
    if isinstance(declared, str) and len(declared) == 2 and declared.isascii() and declared.isdigit():
        matches = int(declared) == len(segments)
    return {"status": "JSON_SEGMENT_OBJECT_OBSERVATION", "segments": segments,
            "countMatches": matches, "duplicateSegmentIds": len(segments) != len(set(segments))}


def pct(numerator, denominator):
    return round(100 * numerator / denominator, 2) if denominator else None


def audit_package(root):
    br_doc = read(root / "requirements_scenarios" / "business_requirements.json")
    scenario_doc = read(root / "requirements_scenarios" / "business_scenarios.json")
    supporting = read(root / "requirements_scenarios" / "scenario_supporting_rules.json")
    matrix = read(root / "reports" / "traceability_matrix_full.json")
    brs = index(br_doc["business_requirements"])
    scenarios = index(scenario_doc["scenarios"])
    rules = supporting["rules"]
    if not brs or not scenarios or not rules:
        raise ValueError(f"Empty BR/TS/supporting-rule population: {root.name}")
    for rule_id, rule in rules.items():
        if rule["rule_id"] != rule_id:
            raise ValueError(f"Supporting-rule key mismatch: {rule_id}")
    cases = gates.read_cases(root)
    case_index = index(cases)
    scenario_brs = defaultdict(set)
    issues = []
    for br_id, br in brs.items():
        for scenario_id in ids(br.get("scenario_ids")):
            if scenario_id not in scenarios:
                issues.append({"kind": "BR_TS_TARGET_MISSING", "id": br_id, "target": scenario_id})
            else:
                scenario_brs[scenario_id].add(br_id)
    by_scenario = supporting["by_scenario"]
    for scenario_id, rule_ids in by_scenario.items():
        if scenario_id not in scenarios:
            issues.append({"kind": "RULE_TS_TARGET_MISSING", "id": scenario_id, "target": ""})
        for rule_id in ids(rule_ids):
            if rule_id not in rules:
                issues.append({"kind": "UNKNOWN_SUPPORTING_RULE", "id": scenario_id, "target": rule_id})
    references = defaultdict(set)
    physical = {}
    for case in cases:
        for declaration in declarations(case):
            references[declaration].add(case["id"])
    for declaration in sorted(references):
        path = resolve(root, declaration)
        observation = physical_observation(read(path)) if path.is_file() else {
            "status": "MISSING", "segments": [], "countMatches": None}
        physical[declaration] = {"path": declaration, "caseIds": sorted(references[declaration]),
                                 "sha256": digest(path) if path.is_file() else None, **observation}
    ts_cases = defaultdict(set)
    ts_physical = defaultdict(set)
    br_chain = defaultdict(set)
    rule_ts = defaultdict(set)
    rule_cases = defaultdict(set)
    rule_physical = defaultdict(set)
    rule_predicates = defaultdict(set)
    rule_response = defaultdict(set)
    unknown_predicates = set()
    case_rows = []
    for scenario_id, rule_ids in by_scenario.items():
        if scenario_id in scenarios:
            for rule_id in ids(rule_ids) & rules.keys():
                rule_ts[rule_id].add(scenario_id)
    for case in cases:
        case_id, scenario_id = case["id"], case["scenario_id"]
        linked = scenario_id in scenarios and bool(scenario_brs[scenario_id] & br_links(case))
        if scenario_id not in scenarios:
            issues.append({"kind": "TC_TS_TARGET_MISSING", "id": case_id, "target": scenario_id})
        elif not linked:
            issues.append({"kind": "TC_BR_EDGE_MISMATCH", "id": case_id, "target": scenario_id})
        paths = declarations(case)
        complete = bool(paths) and all(physical[path]["status"] == "JSON_SEGMENT_OBJECT_OBSERVATION"
                                      for path in paths)
        if linked:
            ts_cases[scenario_id].add(case_id)
            if complete:
                ts_physical[scenario_id].add(case_id)
                for br_id in scenario_brs[scenario_id] & br_links(case):
                    br_chain[br_id].add(case_id)
            for rule_id in ids(by_scenario.get(scenario_id)) & rules.keys():
                rule_cases[rule_id].add(case_id)
                if complete:
                    rule_physical[rule_id].add(case_id)
        declared_predicates = set()
        for check in case.get("rule_checks") or []:
            declared_predicates.update(ids(check.get("kb_rule_ids")))
        unknown_predicates.update(declared_predicates - rules.keys())
        for rule_id in declared_predicates & rules.keys():
            if linked and rule_id in ids(by_scenario.get(scenario_id)) and complete:
                rule_predicates[rule_id].add(case_id)
        for check in case.get("response_checks") or []:
            rule_id = check.get("rule_id")
            if linked and rule_id in rules:
                rule_response[rule_id].add(case_id)
        case_rows.append({"package": root.name, "caseId": case_id, "scenarioId": scenario_id,
                          "status": case.get("status"), "polarity": case.get("polarity"),
                          "type": case.get("tc_type"), "directBrTsEdge": linked,
                          "declaredTdFiles": len(paths), "allDeclaredTdJsonPresent": complete,
                          "explicitKbRuleIds": sorted(declared_predicates),
                          "producerVerification": (case.get("verification") or {}).get("status")})
    rule_rows = [{"package": root.name, "ruleId": rule_id,
                  "sourceId": rule.get("source_id"), "page": rule.get("page"),
                  "producerRequiresReview": rule.get("requires_review"),
                  "scenarioCount": len(rule_ts[rule_id]), "caseCount": len(rule_cases[rule_id]),
                  "casesWithAllDeclaredTd": len(rule_physical[rule_id]),
                  "casesWithExplicitPredicateAndTd": len(rule_predicates[rule_id]),
                  "casesWithResponseCheckDeclaration": len(rule_response[rule_id]),
                  "semanticDisposition": "NOT_ASSESSED"}
                 for rule_id, rule in sorted(rules.items())]
    scenario_rows = [{"package": root.name, "scenarioId": identity,
                      "category": scenario.get("dimensions", {}).get("txn_category"),
                      "entryMode": scenario.get("dimensions", {}).get("entry_mode"),
                      "producerStatus": scenario.get("status"),
                      "caseCount": len(ts_cases[identity]),
                      "casesWithAllDeclaredTd": len(ts_physical[identity])}
                     for identity, scenario in sorted(scenarios.items())]
    actual_td = {path.resolve() for path in (root / "test_data").rglob("*.json")}
    referenced_td = {resolve(root, path) for path in references}
    statuses = Counter(row["status"] for row in case_rows)
    counters = {
        "umbrellaBrs": len(brs), "umbrellaBrsWithPhysicalChain": len(br_chain),
        "scenarios": len(scenarios), "scenariosWithCases": sum(bool(value) for value in ts_cases.values()),
        "scenariosWithPhysicalChain": sum(bool(value) for value in ts_physical.values()), "testCases": len(case_index),
        "casesWithAllDeclaredTd": sum(row["allDeclaredTdJsonPresent"] for row in case_rows),
        "casesWithoutTdDeclaration": sum(not row["declaredTdFiles"] for row in case_rows),
        "distinctTdReferences": len(references), "physicalTdFiles": len(actual_td),
        "unreferencedTdFiles": len(actual_td - referenced_td),
        "missingTdReferences": sum(row["status"] == "MISSING" for row in physical.values()),
        "segmentObjectCountMismatches": sum(row["countMatches"] is False for row in physical.values()),
        "segmentObjectCountUnassessed": sum(row["countMatches"] is None for row in physical.values()),
        "duplicateSegmentIds": sum(row.get("duplicateSegmentIds", False) for row in physical.values()),
        "supportingRules": len(rules), "rulesLinkedToScenarios": sum(bool(value) for value in rule_ts.values()),
        "rulesWithCaseLineage": sum(bool(value) for value in rule_cases.values()),
        "rulesWithPhysicalLineage": sum(bool(value) for value in rule_physical.values()),
        "rulesWithExplicitPredicateAndTd": sum(bool(value) for value in rule_predicates.values()),
        "rulesWithResponseCheckDeclaration": sum(bool(value) for value in rule_response.values()),
        "supportingRulesRequiringReview": sum(rule.get("requires_review") is True for rule in rules.values()),
    }
    return {
        "package": root.name, "meta": br_doc["meta"], "counts": counters,
        "caseStatuses": dict(sorted(statuses.items())),
        "scenarioStatuses": dict(Counter(row["producerStatus"] for row in scenario_rows)),
        "supportingRuleSourceInventory": dict(Counter(rule.get("source_id") for rule in rules.values())),
        "casesWithoutTdByTypeAndStatus": dict(Counter(
            f"{row['type']} / {row['status']}" for row in case_rows if not row["declaredTdFiles"])),
        "predicateOperations": dict(Counter(check["op"] for case in cases
                                           for check in case.get("rule_checks") or [])),
        "producerMatrixSummary": matrix["summary"],
        "unknownExplicitPredicateRuleIds": sorted(unknown_predicates),
        "rulePhysicalLineagePercent": pct(counters["rulesWithPhysicalLineage"], len(rules)),
        "ruleExplicitPredicateLineagePercent": pct(counters["rulesWithExplicitPredicateAndTd"], len(rules)),
        "confirmedRequirementCoveragePercent": None,
        "independentRuleDenominator": None, "semanticAssessmentStatus": "NOT_ASSESSED",
        "rules": rule_rows, "cases": case_rows, "scenariosLedger": scenario_rows,
        "physicalTdLedger": list(physical.values()), "issues": issues,
    }


def csv_write(path, rows):
    with path.open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=list(rows[0]) if rows else ["record"])
        writer.writeheader()
        for row in rows:
            writer.writerow({key: json.dumps(value, ensure_ascii=True) if isinstance(value, (list, dict))
                             else value for key, value in row.items()})


def render(results):
    lines = ["# Mastercard BR Coverage Review - 31 October 2026 AI Delivery", "",
             "## Scope and verdict", "",
             "Mastercard Credit and Mastercard Fleet are the primary scope. Maestro Direct Debit is a",
             "separate companion section and is never added to the Mastercard totals.",
             "Confirmed independent ATL105 BR coverage: **NOT CALCULABLE / NOT ASSESSED**.",
             "The delivery declares ATL105 2026-4; no release-qualified independent denominator or",
             "delivery-bound semantic crosswalk is established by this audit. The historical",
             "2026-3 Mastercard Purchase 168/189 training result is not AI-delivery coverage.",
             "Named native-gate outcomes are shown below; execution remains BLOCKED.", "",
             "## Recomputed inventory and structural reach", "",
             "| Package | Umbrella BRs | TS | TC | TC with all declared TD | Physical TD | Supporting rules | Rule physical lineage | Explicit predicate + TD lineage |",
             "|---|---:|---:|---:|---:|---:|---:|---:|---:|"]
    for result in results:
        c = result["counts"]
        lines.append(f"| {result['package']} | {c['umbrellaBrs']} | {c['scenarios']} | "
                     f"{c['testCases']} | {c['casesWithAllDeclaredTd']} | {c['physicalTdFiles']} | "
                     f"{c['supportingRules']} | {c['rulesWithPhysicalLineage']}/{c['supportingRules']} "
                     f"({result['rulePhysicalLineagePercent']}%) | "
                     f"{c['rulesWithExplicitPredicateAndTd']}/{c['supportingRules']} "
                     f"({result['ruleExplicitPredicateLineagePercent']}%) |")
    lines += ["", "Supporting-rule IDs are a producer inventory, not an independent or approved atomic",
              "denominator. Scenario rule lists broadcast lineage to linked TCs; lineage does not",
              "prove that a TC tests each rule. Explicit predicate lineage requires a TC rule_checks",
              "kb_rule_ids reference, the matching scenario-rule link, and all declared TD JSON files.",
              "Predicates have NOT been executed, and their source applicability has NOT been certified.",
              "Generic named checks without kb_rule_ids are excluded from this narrow ID-lineage metric;",
              "a low percentage is not proof that the other rules have no tests or that those tests fail.",
              "Response-check declarations are retained separately; FLAG/ASSERT labels are not observed responses.",
              "Shared rules across packages are not added into a unique rule denominator.", ""]
    primary = [result for result in results if result["package"] in PACKAGES[:2]]
    lines += [f"Primary Mastercard totals: **{sum(r['counts']['scenarios'] for r in primary)} TS, "
              f"{sum(r['counts']['testCases'] for r in primary)} TC, "
              f"{sum(r['counts']['casesWithAllDeclaredTd'] for r in primary)} TC with all declared TD, "
              f"{sum(r['counts']['physicalTdFiles'] for r in primary)} physical TD files**.",
              "These totals are package-qualified artifact counts, not a deduplicated BR denominator.", ""]
    for result in results:
        c = result["counts"]
        lines += [f"## {result['package']}", "",
                  f"- Declared build time: `{result['meta'].get('built_at')}`.",
                  f"- Declared release: `{result['meta'].get('spec_version')}`.",
                  f"- TC status inventory (producer declarations): `{result['caseStatuses']}`.",
                  f"- Scenario status inventory (producer declarations): `{result['scenarioStatuses']}`.",
                  f"- Supporting rules marked requires_review by producer: {c['supportingRulesRequiringReview']}.",
                  f"- Supporting rules without explicit predicate + TD lineage: "
                  f"{c['supportingRules'] - c['rulesWithExplicitPredicateAndTd']}.",
                  f"- Supporting rules with response-check declarations: {c['rulesWithResponseCheckDeclaration']}.",
                  f"- TC without TD declarations: {c['casesWithoutTdDeclaration']}; "
                  f"missing declared TD: {c['missingTdReferences']}; unreferenced TD: {c['unreferencedTdFiles']}.",
                  f"- Missing-declaration dispositions: `{result['casesWithoutTdByTypeAndStatus']}`.",
                  f"- Supporting-rule source inventory: `{result['supportingRuleSourceInventory']}`.",
                  f"- JSON segment-object count disagreements: {c['segmentObjectCountMismatches']}; "
                  f"unassessed counts: {c['segmentObjectCountUnassessed']}; "
                  f"duplicate segment IDs: {c['duplicateSegmentIds']}.",
                  f"- Direct-link findings: {len(result['issues'])}.",
                  f"- Producer matrix TC total: {result['producerMatrixSummary'].get('total_test_cases')}; "
                  f"recomputed TC total: {c['testCases']}.", ""]
        if "nativeGates" in result:
            gate = result["nativeGates"]
            lines += [f"- Existing bounded native-delivery gate result: **{gate['gate']}**.",
                      f"- Gate finding counts: `{gate['issue_counts']}`.",
                      "- Gate details: [native-gates.json](native-gates.json). Execution readiness remains BLOCKED.", ""]
    lines += ["## Key validation findings", "",
              "| Finding | Credit | Fleet | Interpretation |",
              "|---|---:|---:|---|"]
    if all("nativeGates" in result for result in primary):
        for code, meaning in [
            ("ELEMENT_188_WIDTH", "EMV Element 188 does not meet the source-pinned three-byte representation"),
            ("ELEMENT_188_TAG", "Element 188 does not agree with EMV tag 5F34"),
            ("READY_OPEN_ITEMS", "Producer marks TC ready despite open execution prerequisites; review required"),
            ("TRACE_PATH", "Trace pointers do not resolve from the delivered package root"),
            ("STALE_ARCHIVE", "Embedded ZIP TC build/population differs from the authoritative JSONL"),
            ("STALE_README", "README does not identify the authoritative build"),
        ]:
            counts = [result["nativeGates"]["issue_counts"].get(code, 0) for result in primary]
            lines.append(f"| {code} | {counts[0]} | {counts[1]} | {meaning} |")
    lines += ["", "Counts above are emitted finding occurrences, not unique defective TCs; trace surfaces",
              "can repeat the same TC. Trace-path failures do not mean the authoritative TC-declared",
              "TD files are missing: those declarations all resolve. EMV width/tag findings are",
              "bounded source-predicate violations, not observed issuer declines.", ""]
    lines += ["## Verification boundaries and next actions", "",
              "1. Freeze inputs with SHA-256; input-manifest.csv includes every file in the three",
              "   selected packages, including unreferenced TD and archives. Hashes are rechecked",
              "   before writing this report. Source/reference hashes are in summary.json.",
              "2. Establish an independently derived, release-pinned 2026-4 denominator for Credit",
              "   and Fleet separately. Do not transfer matches by reused producer BR IDs.",
              "3. Review supporting-rule applicability, including cross-program and companion-source",
              "   rules, then map objectives and source-backed assertions to independent BRs.",
              "4. Validate required/conditional companions using independent transaction context.",
              "   The supplied compatibility SME/TBA note is a learning note, not approval evidence.",
              "   JSON segment-object counts are NOT serialized Element 63 validation; no order,",
              "   segment-length, wire framing, token, or host-acceptance validation is claimed.",
              "   Existing source-pinned native gates add only their named EMV/export predicates;",
              "   their findings do not establish complete message validity or BR semantics.",
              "5. Bind runtime placeholders/client inputs, establish response oracles, and execute",
              "   positive controls and isolated negative mutations before readiness or coverage promotion.",
              "6. Package README/contract counts can be stale; use recomputed JSONL/physical inventories.",
              "   Missing TD on device-only/blocked cases is reported, not automatically a defect.", "",
              "## Evidence files", "",
              "- [Summary and input/source identity](summary.json)",
              "- [Input SHA-256 inventory](input-manifest.csv)",
              "- [Supporting-rule ledger](supporting-rule-ledger.csv)",
              "- [Scenario ledger](scenario-ledger.csv)",
              "- [Case/TD linkage ledger](case-ledger.csv)",
              "- [Physical TD observations](physical-td-ledger.csv)",
              "- [Direct-link findings](link-findings.csv)", ""]
    return "\n".join(lines)


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--input", required=True, type=Path)
    parser.add_argument("--output", required=True, type=Path)
    args = parser.parse_args()
    if args.output.exists():
        raise ValueError("Output must be new; refusing to overwrite historical evidence")
    if args.output.resolve().is_relative_to(args.input.resolve()):
        raise ValueError("Output must be outside the source delivery")
    gates.trusted_source()
    files = sorted(path for package in PACKAGES for path in (args.input / package).rglob("*")
                   if path.is_file())
    manifest = [{"path": str(path.relative_to(args.input)), "bytes": path.stat().st_size,
                 "sha256": digest(path)} for path in files]
    results = [audit_package(args.input / package) for package in PACKAGES]
    for result in results:
        result["nativeGates"] = gates.validate_package(args.input / result["package"])
    references = [
        ATL / "docs" / "specs" / "BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4.pdf",
        ATL / "test-output" / "test-solution-independent-review" / "mc-credit-purchase-br-coverage.json",
        ATL / "docs" / "specs" / "kb" / "segment-100" / "companion-compatibility" /
        "companion-segment-compatibility-sme-tba-note.md",
        gates.SOURCE,
        Path(gates.__file__),
    ]
    reference_hashes = [{"path": str(path.relative_to(ROOT)), "sha256": digest(path)}
                        for path in references]
    if any(digest(args.input / row["path"]) != row["sha256"] for row in manifest):
        raise ValueError("Delivery changed during assessment")
    if set(files) != {path for package in PACKAGES for path in (args.input / package).rglob("*")
                      if path.is_file()}:
        raise ValueError("Delivery file population changed during assessment")
    args.output.mkdir(parents=True)
    csv_write(args.output / "input-manifest.csv", manifest)
    for filename, key in [("supporting-rule-ledger.csv", "rules"), ("scenario-ledger.csv", "scenariosLedger"),
                          ("case-ledger.csv", "cases"), ("physical-td-ledger.csv", "physicalTdLedger")]:
        rows = [{**row, "package": result["package"]} for result in results for row in result[key]]
        csv_write(args.output / filename, rows)
    csv_write(args.output / "link-findings.csv",
              [{**row, "package": result["package"]} for result in results for row in result["issues"]])
    (args.output / "native-gates.json").write_text(
        json.dumps({"sourceSha256": gates.SOURCE_SHA256,
                    "packages": [result["nativeGates"] for result in results]}, indent=2) + "\n",
        encoding="utf-8")
    package_summaries = []
    for result in results:
        package_summary = {key: value for key, value in result.items()
                           if key not in {"rules", "cases", "scenariosLedger", "physicalTdLedger", "nativeGates"}}
        package_summary["nativeGates"] = {key: value for key, value in result["nativeGates"].items()
                                         if key != "issues"}
        package_summaries.append(package_summary)
    summary = {"generatedAt": datetime.now(timezone.utc).isoformat(),
               "inputRoot": str(args.input.resolve()), "inputFiles": len(manifest),
               "sourceReferenceHashes": reference_hashes,
               "assessmentImplementationSha256": digest(Path(__file__)),
               "primaryPackages": list(PACKAGES[:2]), "separateCompanionPackage": PACKAGES[2],
               "packages": package_summaries}
    (args.output / "summary.json").write_text(json.dumps(summary, indent=2, ensure_ascii=True) + "\n",
                                            encoding="utf-8")
    (args.output / "REPORT.md").write_text(render(results), encoding="utf-8")
    print(json.dumps([{"package": result["package"], **result["counts"]} for result in results], indent=2))


if __name__ == "__main__":
    main()
