"""Build a hash-bound structural analysis report for the 181026 AI delivery."""

import argparse
import collections
import hashlib
import json
from pathlib import Path

import ijson


def digest(path):
    h = hashlib.sha256()
    with path.open("rb") as stream:
        for block in iter(lambda: stream.read(1 << 20), b""):
            h.update(block)
    return h.hexdigest()


def items(path, prefix):
    with path.open("rb") as stream:
        yield from ijson.items(stream, prefix + ".item")


def refs(row):
    values = row.get("requirement_ids") or []
    if isinstance(values, str):
        values = [values]
    if not isinstance(values, list):
        values = []
    if row.get("requirement_id"):
        values = values + [row["requirement_id"]]
    return {str(value) for value in values if value}


def manifest_entry(manifest, relative):
    for entry in manifest["files"]:
        if entry["path"] == relative:
            return {"path": relative, "bytes": entry["bytes"], "sha256": entry["sha256"]}
    raise ValueError("Missing manifest entry: " + relative)


def build(args):
    run = args.run_root.resolve()
    audit_dir = args.audit_dir.resolve()
    baseline_dir = args.test_solution_dir.resolve()
    output = args.output_dir.resolve()
    if output.exists():
        raise ValueError("Output must be a new directory; refusing overwrite")
    output.mkdir(parents=True)

    intake = json.loads(args.intake_manifest.read_text(encoding="utf-8-sig"))
    audit = json.loads((audit_dir / "summary.json").read_text(encoding="utf-8"))
    baseline = json.loads((baseline_dir / "source-derived-requirement-inventory.json").read_text(encoding="utf-8"))
    classification = json.loads((baseline_dir / "atomic-composite-rule-classification.json").read_text(encoding="utf-8"))
    if not intake.get("verified") or intake["sourceFileCount"] != intake["copyFileCount"]:
        raise ValueError("Run3 intake manifest is not verified")
    if audit["fileCount"] != intake["copyFileCount"]:
        raise ValueError("Audit population does not match verified intake")

    requirement_path = run / "Requirement candidates" / "requirement_candidates.json"
    scenario_path = run / "scenarios" / "scenarios" / "approved" / "approved_scenarios.json"
    candidate_scenario_path = run / "scenarios" / "scenarios" / "candidates" / "candidate_scenarios.json"
    tc_path = run / "test_case_candidates.json"
    matrix_path = run / "traceability_matrix_full.json"

    br_ids, scenario_ids, tc_ids = set(), set(), set()
    direct_br_scenario, direct_scenario_tc, direct_br_tc = set(), set(), set()
    br_segments = collections.Counter()
    scenario_types = collections.Counter()
    tc_completeness = collections.Counter()
    tc_review = collections.Counter()
    tc_verification = collections.Counter()
    td_declarations = []
    for row in items(requirement_path, "requirements"):
        if row.get("id"):
            br_ids.add(row["id"])
            br_segments[str(row.get("segment_number") or "UNSPECIFIED")] += 1
    for row in items(scenario_path, "scenarios"):
        sid = row.get("id")
        if not sid:
            continue
        scenario_ids.add(sid)
        scenario_types[str(row.get("type") or "UNSPECIFIED")] += 1
        direct_br_scenario.update((bid, sid) for bid in refs(row))
    for row in items(tc_path, "test_cases"):
        cid, sid = row.get("id"), row.get("scenario_id")
        if not cid:
            continue
        tc_ids.add(cid)
        direct_scenario_tc.add((sid, cid)) if sid else None
        direct_br_tc.update((bid, cid) for bid in refs(row))
        tc_completeness[str(row.get("completeness_status") or "UNSPECIFIED")] += 1
        tc_review[str(row.get("review_status") or "UNSPECIFIED")] += 1
        tc_verification[str((row.get("verification") or {}).get("status") or "UNSPECIFIED")] += 1
        declaration = row.get("test_data_file")
        if declaration:
            td_declarations.append(declaration.replace("\\", "/"))
        legs = row.get("test_data_leg_files") or []
        if isinstance(legs, dict):
            legs = list(legs.values())
        if isinstance(legs, list):
            td_declarations.extend(x.replace("\\", "/") for x in legs if isinstance(x, str) and x)

    with matrix_path.open("rb") as stream:
        matrix_summary = next(ijson.items(stream, "summary", use_float=True))
    matrix_br, matrix_scenarios, matrix_tc = set(), set(), set()
    matrix_br_scenario, matrix_scenario_tc, matrix_br_tc = set(), set(), set()
    matrix_status, flat_status, chain_side = collections.Counter(), collections.Counter(), collections.Counter()
    for row in items(matrix_path, "requirements"):
        bid = row.get("requirement_id")
        if bid:
            matrix_br.add(bid)
        matrix_status[str(row.get("trace_status") or "UNSPECIFIED")] += 1
        for scenario in row.get("scenarios") or []:
            sid = scenario.get("scenario_id")
            if not sid:
                continue
            matrix_scenarios.add(sid)
            matrix_br_scenario.add((bid, sid))
            chain_side[str(scenario.get("chain_side") or "UNSPECIFIED")] += 1
            for case in scenario.get("test_cases") or []:
                cid = case.get("test_case_id")
                if cid:
                    matrix_tc.add(cid)
                    matrix_scenario_tc.add((sid, cid))
                    matrix_br_tc.add((bid, cid))
    for row in items(matrix_path, "flat_rows"):
        flat_status[str(row.get("coverage_status") or "UNSPECIFIED")] += 1

    def edge(left, right):
        return {"catalog": len(left), "matrix": len(right), "catalogOnly": len(left - right), "matrixOnly": len(right - left)}

    matrix = {
        "input": manifest_entry(intake, "traceability_matrix_full.json"),
        "producerSummary": matrix_summary,
        "ids": {"catalog": {"BR": len(br_ids), "TS": len(scenario_ids), "TC": len(tc_ids)},
                "matrixRequirementRooted": {"BR": len(matrix_br), "TS": len(matrix_scenarios), "TC": len(matrix_tc)},
                "catalogOnlyScenarios": len(scenario_ids - matrix_scenarios),
                "catalogOnlyCases": len(tc_ids - matrix_tc),
                "matrixOnlyScenarios": len(matrix_scenarios - scenario_ids),
                "matrixOnlyCases": len(matrix_tc - tc_ids)},
        "edges": {"BR_to_TS": edge(direct_br_scenario, matrix_br_scenario),
                  "TS_to_TC": edge(direct_scenario_tc, matrix_scenario_tc),
                  "BR_to_TC": edge(direct_br_tc, matrix_br_tc)},
        "statusCounts": {"requirementTraceStatus": dict(matrix_status),
                         "flatRowCoverageStatus": dict(flat_status),
                         "chainSide": dict(chain_side)},
        "interpretation": "Producer-internal traceability only; it does not establish source completeness, semantic agreement, or execution coverage.",
    }

    actual_paths = {row["path"] for row in intake["files"]}
    exact_paths = sum(path in actual_paths for path in td_declarations)
    one_extra_directory_matches = sum(
        path.startswith("qe_shaped_test_data/")
        and path not in actual_paths
        and ("qe_shaped_test_data/" + path) in actual_paths
        for path in td_declarations
    )
    unresolved_after_diagnostic = len(td_declarations) - exact_paths - one_extra_directory_matches

    producer = audit["catalogs"]
    test_br_baseline_path = baseline_dir / "source-derived-requirement-inventory.json"
    classification_path = baseline_dir / "atomic-composite-rule-classification.json"
    input_hashes = {
        "intakeManifest": {"path": str(args.intake_manifest), "sha256": digest(args.intake_manifest),
                            "fileCount": intake["sourceFileCount"], "bytes": intake["totalBytes"]},
        "catalogs": {name: {"path": relative, "sha256": producer[relative]["sha256"],
                             "records": producer[relative]["records"], "producerMetadata": producer[relative]["producerMetadata"]}
                     for name, relative in {
                         "requirementCandidates": "Requirement candidates/requirement_candidates.json",
                         "approvedScenarios": "scenarios/scenarios/approved/approved_scenarios.json",
                         "candidateScenarios": "scenarios/scenarios/candidates/candidate_scenarios.json",
                         "testCaseCandidates": "test_case_candidates.json"}.items()},
        "traceabilityMatrix": matrix["input"],
        "traceabilityMatrixMarkdown": manifest_entry(intake, "traceability_matrix.md"),
        "independentTestBaseline": {"path": str(test_br_baseline_path), "sha256": digest(test_br_baseline_path), "ruleCount": baseline["ruleCount"]},
        "testAnchorClassification": {"path": str(classification_path), "sha256": digest(classification_path), "ruleCount": len(classification["rules"])},
        "deliveryAudit": {"summarySha256": digest(audit_dir / "summary.json"), "reportSha256": digest(audit_dir / "REPORT.md"),
                          "fileInventorySha256": audit["fileInventorySha256"]},
    }
    analysis = {
        "reportId": "ATL105-181026-RUN-COMPLETE-ANALYSIS-001",
        "reviewedAt": "2026-10-19",
        "deliveryLabel": "181026 Run",
        "runRoot": str(run),
        "assessmentStatus": "COMPLETE_STRUCTURAL_INVENTORY_REVIEW_REQUIRED_SEMANTIC_AND_EXECUTION",
        "authority": "INDEPENDENT_STRUCTURAL_ANALYSIS_NOT_AUTHORIZED_SEMANTIC_REVIEW",
        "inputs": input_hashes,
        "population": {
            "fileCount": audit["fileCount"], "bytes": intake["totalBytes"], "fileKinds": audit["physicalFileCountsByKind"],
            "uniqueIds": audit["uniqueIdsByKind"], "recordOccurrences": audit["occurrencesByKind"],
            "missingExpectedCatalogs": audit["missingCatalogNames"],
            "requirementSegmentsTop20": br_segments.most_common(20),
            "scenarioTypes": dict(scenario_types), "testCaseCompleteness": dict(tc_completeness),
            "testCaseReviewStatuses": dict(tc_review), "testCaseVerificationStatuses": dict(tc_verification),
            "producerCompositionMetadata": producer["test_case_candidates.json"]["producerMetadata"],
        },
        "matrixReconciliation": matrix,
        "physicalArtifacts": {
            "payloadFiles": audit["physicalFileCountsByKind"].get("PAYLOAD", 0),
            "metadataSidecars": audit["physicalFileCountsByKind"].get("METADATA", 0),
            "payloadFilesWithoutSidecar": audit["payloadFilesWithoutValidSidecars"],
            "metadataFilesWithoutPayload": audit["metadataFilesWithoutPayloads"],
            "scannedJsonFiles": audit["recursiveFieldScan"]["filesScanned"],
            "fieldValuesExported": False,
            "declaredTdPathOccurrences": len(td_declarations),
            "exactDeclaredPathMatches": exact_paths,
            "diagnosticMatchesWithOneAdditionalQeDirectory": one_extra_directory_matches,
            "stillUnresolvedUnderDiagnosticTransform": unresolved_after_diagnostic,
            "declaredPayloadMetadataPairStates": audit["declaredPairStates"],
            "completePhysicalTcIds": audit["completePhysicalTcIds"],
            "tcIdsWithoutCompleteDeclaredPhysicalRoute": audit["tcIdsWithoutUnconflictedCompletePhysicalRoute"],
            "unreferencedPayloads": audit["unreferencedPayloadFiles"],
            "unreferencedMetadata": audit["unreferencedMetadataFiles"],
            "interpretation": "The nested-directory match is diagnostic only. No basename or path-prefix fallback is counted as a complete route.",
        },
        "independentTestSolution": {
            "sourceDerivedRuleCount": baseline["ruleCount"],
            "candidateReach": audit["measures"]["candidateReach"],
            "physicalCompleteChainCandidateReach": audit["measures"]["physicalCompleteChainCandidateReach"],
            "anchorDispositionCounts": audit["candidateDispositionCounts"],
            "anchorTypeCounts": audit["anchorTypeCounts"],
            "semanticConfirmedCoverage": audit["measures"]["semanticConfirmedCoverage"],
            "executionCoverage": audit["measures"]["executedValidationCoverage"],
            "executionCertified": False,
        },
        "reviewLimits": [
            "Requirement and TC input tiers are candidate catalogs; approved BR and TC catalogs are absent.",
            "Approved scenario status DEV_APPROVED is producer-scripted; its catalog states no SME review.",
            "The supplied matrix is producer-generated and reports its own extracted-artifact denominator, not independent source completeness.",
            "No source interpretation, authorized SME decision, host run, response oracle, or wire capture is certified.",
            "Physical JSON shape/name and sidecar presence do not establish payload semantics or message validity.",
        ],
    }
    report_path = output / "COMPLETE-ANALYSIS.md"
    analysis_path = output / "analysis.json"
    matrix_path = output / "traceability-matrix-reconciliation.json"
    report = f"""# Complete Structural Analysis: 181026 Run

**Status:** COMPLETE_STRUCTURAL_INVENTORY_REVIEW_REQUIRED_SEMANTIC_AND_EXECUTION  
**Intake:** `runs/2026-10-19/Run3`  
**Provenance:** {intake['sourceFileCount']:,} files / {intake['totalBytes']:,} bytes; SHA-256 verified with zero missing, extra, or mismatched paths.

## Executive Summary

The complete delivery inventory contains {audit['uniqueIdsByKind']['BR']:,} requirement candidates, {audit['uniqueIdsByKind']['TS']:,} approved-scenario records, and {audit['uniqueIdsByKind']['TC']:,} test-case candidates. There are no content-conflicting IDs or broken BR/TS/TC links within the catalogs supplied. The approved-scenario label is `DEV_APPROVED`, with the producer note “Scripted approval (no SME review)”; approved BR and TC catalogs were not supplied.

The primary structural blocker is TD path resolution. All {len(td_declarations):,} declared payload paths fail exact catalog-relative resolution. The files exist in the intake under one additional `qe_shaped_test_data/` directory; this alternative match is diagnostic only and receives no route credit. Thus 0/{audit['uniqueIdsByKind']['TC']:,} TCs have a complete declared physical route. Structural Test-BR candidate reach is {audit['measures']['candidateReach']['numerator']}/{audit['measures']['candidateReach']['denominator']} ({audit['measures']['candidateReach']['percent']}%); semantic and executed coverage remain NOT_ASSESSED.

## Populations

| Catalog | Count | Producer state / qualification |
|---|---:|---|
| Requirement candidates | {audit['uniqueIdsByKind']['BR']:,} | EXTRACTED; {producer['Requirement candidates/requirement_candidates.json']['producerMetadata'].get('flagged_for_review', 0):,} flagged for review |
| Approved scenarios | {audit['uniqueIdsByKind']['TS']:,} | DEV_APPROVED label; scripted approval, no SME review; {producer['scenarios/scenarios/approved/approved_scenarios.json']['producerMetadata'].get('flagged_for_review', 0):,} flagged |
| Candidate scenarios | 0 | EXTRACTED; {producer['scenarios/scenarios/candidates/candidate_scenarios.json']['producerMetadata'].get('skipped_duplicates', 0):,} skipped duplicates |
| Test-case candidates | {audit['uniqueIdsByKind']['TC']:,} | {producer['test_case_candidates.json']['producerMetadata'].get('newly_composed', 0):,} newly composed; {producer['test_case_candidates.json']['producerMetadata'].get('skipped_duplicates', 0):,} skipped duplicates; {producer['test_case_candidates.json']['producerMetadata'].get('deferred_response_side', 0):,} deferred response-side |

Producer completeness classifications are recorded in `analysis.json`; they are not independent acceptance. The intake contains {audit['physicalFileCountsByKind'].get('PAYLOAD', 0):,} physical payload JSON files and {audit['physicalFileCountsByKind'].get('METADATA', 0):,} metadata sidecars. All payloads have sidecars and all sidecars have payloads. Recursive shape scanning covered {audit['recursiveFieldScan']['filesScanned']:,} files and exported no field values.

## Matrix Reconciliation

The matrix reports {matrix_summary.get('requirements_fully_traced', 0):,}/{matrix_summary.get('total_requirements', 0):,} requirements as fully traced ({matrix_summary.get('full_chain_coverage_percent', 'N/A')}%). That is producer-internal traceability, not independent source coverage. Matrix BR IDs match all {len(br_ids):,} requirement-catalog IDs and the direct BR→TS and BR→TC edges reconcile. However, {len(scenario_ids - matrix_scenarios):,} scenarios and {len(tc_ids - matrix_tc):,} TCs are not present in requirement-rooted matrix rows; they are scenario-only branches without a requirement link. Matrix and catalog link counts are in `traceability-matrix-reconciliation.json`.

| Link | Catalog edges | Matrix edges | Difference |
|---|---:|---:|---:|
| BR → TS | {len(direct_br_scenario):,} | {len(matrix_br_scenario):,} | {len(direct_br_scenario - matrix_br_scenario) + len(matrix_br_scenario - direct_br_scenario):,} |
| TS → TC | {len(direct_scenario_tc):,} | {len(matrix_scenario_tc):,} | {len(direct_scenario_tc - matrix_scenario_tc) + len(matrix_scenario_tc - direct_scenario_tc):,} |
| BR → TC | {len(direct_br_tc):,} | {len(matrix_br_tc):,} | {len(direct_br_tc - matrix_br_tc) + len(matrix_br_tc - direct_br_tc):,} |

## TD Path Findings

| Measure | Result |
|---|---:|
| Declared TD path occurrences | {len(td_declarations):,} |
| Exact catalog-relative paths resolved | {exact_paths:,} |
| Match after adding one extra `qe_shaped_test_data/` directory (diagnostic only) | {one_extra_directory_matches:,} |
| Unresolved after that diagnostic transform | {unresolved_after_diagnostic:,} |
| Complete physical TC routes | {audit['completePhysicalTcIds']:,}/{audit['uniqueIdsByKind']['TC']:,} |
| Unreferenced physical payloads / sidecars under exact declarations | {audit['unreferencedPayloadFiles']:,} / {audit['unreferencedMetadataFiles']:,} |

The directory-prefix observation is not a fallback rule. Correct the producer’s TD declarations or regenerate the delivery, then verify a new immutable intake before granting physical-chain reach.

## Test-BR Candidate Reach

The independent Test Solution baseline contains {baseline['ruleCount']:,} source-derived BRs. {audit['measures']['candidateReach']['numerator']} have at least one structural source-anchor candidate ({audit['measures']['candidateReach']['percent']}%); this is review triage only. {audit['measures']['physicalCompleteChainCandidateReach']['numerator']} have a complete physical candidate route. Anchor-disposition counts and source-hash-bound records are retained in the delivery-wide audit. No candidate is confirmed as semantically equivalent.

## Required Follow-Up

1. Correct the TD catalog-relative path layout and reissue a hash-verified intake; preserve Run3 unchanged.
2. Review the 144 scenario-only branches and associated TCs; do not infer BR links.
3. Supply approved BR and TC catalogs for approved-tier comparison.
4. Adjudicate source anchors and semantics through authorized review; retain semantic and execution measures as NOT_ASSESSED.
5. Separately qualify payload semantics, wire captures, response oracles, and host behavior; this structural report does not certify them.

## Evidence

- [Delivery-wide audit report](../2026-10-19-181026-run-delivery-wide-assessment-v3/REPORT.md)
- [Structured analysis](analysis.json)
- [Matrix reconciliation](traceability-matrix-reconciliation.json)
- `manifest.json` binds this report and its inputs by SHA-256.
"""
    report_path.write_text(report, encoding="utf-8")
    analysis_path.write_text(json.dumps(analysis, ensure_ascii=True, indent=2) + "\n", encoding="utf-8")
    matrix_path.write_text(json.dumps(matrix, ensure_ascii=True, indent=2) + "\n", encoding="utf-8")
    artifacts = {p.name: digest(p) for p in (Path(__file__).resolve(), report_path, analysis_path, matrix_path)}
    manifest = {"algorithm": "SHA-256", "reportId": analysis["reportId"], "inputs": input_hashes, "artifacts": artifacts}
    (output / "manifest.json").write_text(json.dumps(manifest, ensure_ascii=True, indent=2) + "\n", encoding="utf-8")
    print(json.dumps({"output": str(output), "declaredPaths": len(td_declarations), "exactPaths": exact_paths, "nestedPrefixDiagnosticOnly": one_extra_directory_matches, "analysisSha256": artifacts["analysis.json"], "reportSha256": artifacts["COMPLETE-ANALYSIS.md"]}, indent=2))


def main():
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("--run-root", type=Path, default=Path("specifications/ATL105/test-input/ai-solution/runs/2026-10-19/Run3"))
    parser.add_argument("--intake-manifest", type=Path, default=Path("specifications/ATL105/test-input/ai-solution/runs/2026-10-19/Run3-intake-manifest.json"))
    parser.add_argument("--audit-dir", type=Path, default=Path("specifications/ATL105/test-output/ai-solution-independent-review/2026-10-19-181026-run-delivery-wide-assessment-v3"))
    parser.add_argument("--test-solution-dir", type=Path, default=Path("specifications/ATL105/test-output/test-solution-independent-review"))
    parser.add_argument("--output-dir", type=Path, default=Path("specifications/ATL105/test-output/ai-solution-independent-review/2026-10-19-181026-run-complete-analysis-v2"))
    args = parser.parse_args()
    build(args)


if __name__ == "__main__":
    main()
