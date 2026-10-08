import argparse
import csv
import hashlib
import json
import os
import zipfile
from html import escape
from pathlib import Path


def native(path):
    path = Path(os.path.normpath(str(path)))
    return Path("\\\\?\\" + str(path.resolve())) if os.name == "nt" and not str(path).startswith("\\\\?\\") else path


def read(path):
    return json.loads(path.read_text(encoding="utf-8-sig"))


def section_10_2_reuse_text(chain, baseline=False):
    if baseline:
        label = f'{chain["testSolutionRuleId"]}: {chain["businessRequirementId"]}'
    else:
        label = f'{chain["topicKey"]}: {chain["draftBrId"]}'
    return (f'{label} / TS={",".join(chain["linkedScenarioIds"])} / '
            f'TC={",".join(chain["linkedTestCaseIds"])} / TD={",".join(chain["linkedTestDataIds"])}')


def render(root):
    root = native(root)
    summary = read(root / "LATEST-RUN-ANALYSIS.json")
    cases = read(root / "case-register.json")
    files = read(root / "physical-file-register.json")
    element_report = read(root.parent / "independent-element-validation.json")
    test_solution_root = native(root.parents[2] / "test-solution-independent-review")
    independent_inventory_path = test_solution_root / "source-derived-requirement-inventory.json"
    historical_decisions_path = test_solution_root / "semantic-br-decision-register.json"
    independent_inventory = read(independent_inventory_path)
    historical_decisions = read(historical_decisions_path)
    historical_status_counts = {}
    for decision in historical_decisions["decisions"]:
        status = decision.get("decision", "NOT_DECLARED")
        historical_status_counts[status] = historical_status_counts.get(status, 0) + 1
    chain_summary_path = root / "run4-independent-br-crosswalk-v2/run4-ai-br-chain-crosswalk-summary.json"
    chain_summary = read(chain_summary_path)
    semantic_review_dir = root / "run4-semantic-review-v2"
    semantic_review_summary = read(semantic_review_dir / "run4-semantic-review-summary.json")
    semantic_decision_register = read(semantic_review_dir / "run4-semantic-decision-register.json")
    manual_search_findings = read(semantic_review_dir / "run4-manual-search-findings.json")
    section_10_1_dir = test_solution_root / "section-10-1-credit-card-processing-review-v4"
    section_10_1_assessment = read(section_10_1_dir / "section-10-1-coverage-assessment.json")
    section_10_2_dir = test_solution_root / "section-10-2-debit-card-processing-review-v2"
    section_10_2_assessment = read(section_10_2_dir / "section-10-2-coverage-assessment.json")
    team_interpretation = {
        "artifact": "RUN4-TEST-TEAM-SEMANTIC-INTERPRETATION",
        "recordedDate": "2026-10-08",
        "authority": "TEST_TEAM_INTERPRETATION_SUPPLIED_BY_USER",
        "aiRequirementId": "REQ-SRC-ATL105-PDF-001:004",
        "testSolutionRuleId": "SEGDL1-R-010",
        "sourceAnchor": {"specification": "ATL105", "version": "2026-3", "section": "13.2", "segment": "DL1", "element": "4", "rule": "address-line-2-positional-layout"},
        "testTeamInterpretation": "Both BRs are correct and semantically aligned. The AI BR states the alphanumeric character type and 21-byte maximum; the Test BR adds the city/state/ZIP positional layout, which is compatible specificity, not a contradiction.",
        "sourceEvidence": "Element 4 is Address Line 2; Section 13.2 describes 21 alphanumeric positions: 1-12 city, 13 space, 14-15 alphabetic State Code, 16 space, 17-21 ZIP Code/ZIP+4.",
        "aiRequirementStatement": "Address Line 2 must be alphanumeric and must not exceed 21 bytes.",
        "formalPairDecisionStatus": "PENDING",
        "formalReviewerIdentity": None,
        "coverageCredit": False,
        "limitation": "This Test Team interpretation is explanatory evidence for this pair. It does not append an authorized reviewer decision or certify the linked TS/TC/TD execution chain.",
    }
    (root / "test-team-interpretation-2026-10-08.json").write_text(json.dumps(team_interpretation, indent=2, ensure_ascii=True), encoding="utf-8")
    archive = native(Path(summary["intakeVerification"]["archive"]))
    zip_path = archive / "logs/run_current/multi_leg_test_data.zip"
    manifest = read(archive / "logs/run_current/multi_leg_test_data_manifest.json")
    with zipfile.ZipFile(zip_path) as package:
        names = package.namelist()
        crc_failure = package.testzip()
        unsafe = [name for name in names if name.startswith("/") or ".." in Path(name).parts]
        differing = []
        missing = []
        for name in names:
            original = archive / "src/pipeline/test_generation/candidates/qe_shaped_test_data" / name
            if not original.is_file():
                missing.append(name)
            elif hashlib.sha256(package.read(name)).hexdigest() != hashlib.sha256(original.read_bytes()).hexdigest():
                differing.append(name)
    package_audit = {"members": len(names), "manifestFiles": manifest["file_count"], "manifestCases": manifest["multi_leg_test_case_count"],
                     "crcFailure": crc_failure, "unsafePaths": unsafe, "missingCandidateMembers": missing,
                     "differingCandidateMembers": differing, "executionCertified": False}
    (root / "multileg-package-audit.json").write_text(json.dumps(package_audit, indent=2), encoding="utf-8")
    matrix = summary["matrix"]["independentGraphAudit"]
    direct_br_count = summary["brsWithDirectTc"]
    matrix_br_count = matrix["brsLinkedByMatrixRows"]
    ai_internal_linkage = {
        "denominator": summary["BR"],
        "directLinkedBrs": direct_br_count,
        "directCoveragePercent": round(100 * direct_br_count / summary["BR"], 2),
        "matrixLinkedBrs": matrix_br_count,
        "matrixCoveragePercent": round(100 * matrix_br_count / summary["BR"], 2),
        "proposedRelinkEdges": matrix["matrixBrEdgeNotDirectInCase"],
        "matrixOnlyBrIds": matrix["matrixOnlyLinkedBrIds"],
    }
    test_solution_br_count = independent_inventory["ruleCount"]
    test_solution_coverage = {
        "metric": "CONFIRMED_TEST_SOLUTION_RULES_OVER_INDEPENDENT_TEST_SOLUTION_RULES",
        "numeratorConfirmedCommonTestBrs": 0,
        "denominatorIndependentTestBrs": test_solution_br_count,
        "confirmedEvidencePercent": 0.0,
        "assessmentStatus": "NOT_ASSESSED",
        "unassessedTestBrs": test_solution_br_count,
        "aiOnlyCount": None,
        "run4SpecificCandidateCrosswalkPresent": True,
        "run4SpecificConfirmedSemanticCrosswalkPresent": False,
        "allAiBrsIncludedRegardlessOfApproval": chain_summary["aiBusinessRequirementCount"],
        "aiBrSourceAnchorCandidateCounts": chain_summary["matchDispositionCounts"],
        "aiBrsWithSourceAnchorCandidates": chain_summary["aiBrsWithSourceAnchorCandidates"],
        "uniqueTestSolutionBrsWithCandidates": chain_summary["uniqueTestSolutionRulesWithSourceAnchorCandidates"],
        "aiBrChainCounts": chain_summary["directCatalogChainCounts"],
        "matrixAndChainAudit": chain_summary["matrixAndChainAudit"],
        "crosswalkSummaryPath": "run4-independent-br-crosswalk-v2/run4-ai-br-chain-crosswalk-summary.json",
        "historicalDecisionRegisterStatusCounts": historical_status_counts,
        "historicalDecisionRegisterApplied": False,
        "historicalCrosswalkSource": "test-input/ai-solution/runs/2026-09-23/Run2/traceability_matrix_full.json",
        "interpretation": "All AI BRs were included regardless of approval in a Run4-specific structural source-anchor candidate crosswalk. Candidate links are not semantic matches. No Run4 semantic match decisions are confirmed; all Test Solution BRs remain NOT_ASSESSED, not AI_ONLY, pending independent review. Zero confirmed matches does not establish that no equivalent AI BRs exist.",
        "testSolutionInventoryRuleCount": independent_inventory["ruleCount"],
        "testSolutionInventorySha256": hashlib.sha256(independent_inventory_path.read_bytes()).hexdigest(),
    }
    br_coverage = {"testSolutionIndependent": test_solution_coverage, "aiInternalGraphLinkage": ai_internal_linkage}
    (root / "test-solution-br-coverage.json").write_text(json.dumps(test_solution_coverage, indent=2), encoding="utf-8")
    findings = [
        ("HIGH", "TD_EMPTY", "764 empty request bodies counted across physical copies", "This is a candidate/reporting copy count, not 764 distinct TCs. A present file is not a populated executable request; TC-000001 is an Auth Completion (0220) empty object.", "Resolve message-family/template grounding or classify blocked; do not call empty bodies real executable data."),
        ("HIGH", "TD_MISSING", "24 declared request-data files are missing", "Catalog declares physical files not found; complete affected-case list is exported.", "Producer supplies corrected payload/metadata pairs with declared case links."),
        ("HIGH", "ADAPTER_SCOPE", "Existing independent Java adapter cannot map current payload roots", "21,621 metadata records were read; element observations and field mappings are empty. All records remain REVIEW_REQUIRED.", "Review a run-versioned root-family crosswalk and exact field identities before rerunning value rules; no implicit alias promotion."),
        ("HIGH", "APPROVAL_PROVENANCE", "1,650 producer approvals are automated-batch-nonSME", "Approval audit count/reviewer identity verified; source report also cautions that approval is demo/sample rather than real SME approval.", "Do not claim SME-approved or execution-ready suite; retain reviewer provenance and review required status."),
        ("HIGH", "ID_DRIFT", "Reused IDs contain materially changed content", "5,141 of 5,153 reused BR statements changed; all 21,123 reused TC fingerprints changed compared with October 5.", "Do not migrate older matches by ID; remap by version/source/context and business meaning."),
        ("MEDIUM", "AUDIT_ZERO", "TC audit reports zero cases", "The actual catalog contains 21,531 TCs; the TC audit summary is not a reliable coverage gate.", "Regenerate the audit from the final case catalog and pin hashes/timestamps."),
        ("MEDIUM", "ASSERT_COUNTS", "ASSERT figures use conflicting or unexplained counting units", "Run report says 516; TD audit says 3,476; 8,637 cases contain ASSERT-tier nodes, with 37,497 ASSERT nodes and 6,710 cases carrying explicit assertion/value evidence under the analyzer's disclosed criterion.", "Separate node counts, case counts and grounded/qualified assertions; do not sum overlapping tiers or treat ASSERT labels as certified oracles."),
        ("MEDIUM", "MATRIX_RELINK", "Matrix proposes 62 BR edges absent from direct TC links", "Full JSON has 65,989 leaves; 3,720 matrix-linked BRs versus 3,719 direct-linked BRs. Matrix-only BR is :748; relinks remain producer candidates.", "Review each source-rule relink against underlying BR/scenario/context; preserve direct graph and proposed graph separately."),
        ("MEDIUM", "MATRIX_LABEL", "28 FULLY_TRACED leaves have no TD declaration", "Status may be propagated from a BR with another complete chain; individual leaves are not proven complete.", "Emit leaf-level and BR-level trace states separately; no automatic semantic or readiness pass."),
        ("MEDIUM", "SCENARIO_BACKLOG", "1,437 scenarios have no TC", "Generation backlog improves from 3,732 but remains in the denominator; no approved exclusion is established.", "Use exported backlog for grounded regeneration or explicit reviewed scope decisions."),
        ("MEDIUM", "UNMAPPED_BR", "193 cases have no direct BR attribution", "Matrix reports 131 orphan scenarios and documents 62 source-rule relinks; this is a proposed attribution distinction, not confirmed semantic matching.", "Validate source/context justification for proposed edges and disclose residual unattributed artifacts."),
        ("HIGH", "SEMANTIC_HOST", "Business equivalence and host readiness remain unqualified", "Expected-response structures now exist but contain INFER/GROUNDED_SECONDARY/FLAG claims; Judge bridge caveat, partial templates and root-mapping limitations remain.", "Independently qualify rule interpretations, assertions, applicable data/wire and authorized host/state/response evidence; record SME questions without granting approval."),
    ]
    query_rows = [{"queryId": "SME-RUN4-" + kind, "priority": severity, "subject": title,
                   "evidence": evidence, "question": action, "status": "OPEN_REVIEW_REQUIRED", "approvalGranted": False}
                  for severity, kind, title, evidence, action in findings]
    write = lambda name, value: (root / name).write_text(json.dumps(value, indent=2, ensure_ascii=True), encoding="utf-8")
    write("latest-run-findings-and-sme-queries.json", query_rows)
    with (root / "latest-run-findings-and-sme-queries.csv").open("w", encoding="utf-8-sig", newline="") as stream:
        writer = csv.DictWriter(stream, fieldnames=list(query_rows[0]))
        writer.writeheader()
        writer.writerows(query_rows)
    markdown = ["# October 7 Run4 | Independent Extensive Review", "", "Disposition: REVIEW_REQUIRED. This is graph, physical-file, metadata and producer-claim reconciliation; not semantic coverage or host certification.", "",
        "## Verified Intake", "", "129,764 files / 3,158,902,761 bytes; all source/archive relative-path SHA-256 hashes agree. Original Downloads source and prior runs remain unchanged.", "",
        "## Inventory and graph", "", "| Measure | Latest verified count |", "|---|---:|"]
    statistics = [("AI BRs", summary["BR"]), ("AI TSs", summary["TS"]), ("AI TC candidates", summary["TC"]),
        ("AI BRs directly linked to candidate TCs (internal graph)", summary["brsWithDirectTc"]),
        ("TSs with TC", summary["scenariosWithTc"]), ("TSs without TC", summary["scenariosWithoutTc"]),
        ("Unique physical request filenames", summary["physicalUniqueBasenames"]), ("Physical request copies across candidate/reporting locations", summary["physicalRequestFilesAcrossLocations"]),
        ("Logical TCs with verified physical metadata", summary["logicalCasesWithVerifiedPhysicalMetadata"]),
        ("Missing declared TD files", summary["declaredMissingFileCount"]), ("Empty request bodies (physical copies)", summary["emptyMessageBodyFilesAcrossLocations"])]
    markdown.extend(f"| {label} | {value:,} |" for label, value in statistics)
    markdown.extend(["", "## Independent Test Solution BR coverage", "", f"Formula: confirmed common Test Solution BRs / independent Test Solution BRs. Run4 has 0/{test_solution_br_count:,} confirmed common matches recorded (0.00% confirmed evidence); status: NOT_ASSESSED. All {test_solution_br_count:,} Test Solution BRs remain unassessed because no Run4-specific evidence-backed crosswalk exists. This is not evidence that no equivalent AI BRs exist.", "", f"The {sum(historical_status_counts.values()):,}-entry decision register is tied to September Run2 and contains {historical_status_counts.get('PENDING', 0):,} pending decisions; it is not applied to Run4. The old crosswalk cannot transfer by reused BR ID.", "", f"Separate AI-internal diagnostic: {direct_br_count:,}/{summary['BR']:,} AI BRs directly linked to candidate TCs ({ai_internal_linkage['directCoveragePercent']:.2f}%). The producer matrix shows {matrix_br_count:,}/{summary['BR']:,} ({ai_internal_linkage['matrixCoveragePercent']:.2f}%) with {matrix['matrixBrEdgeNotDirectInCase']} proposed, unconfirmed relink edges. These are not Test Solution BR coverage.", "", "[Independent Test Solution BR inventory](../../../test-solution-independent-review/source-derived-requirement-inventory.json) | [Run4 coverage evidence](test-solution-br-coverage.json)", "", "## AI-internal BR-to-TC linkage (not Test Solution coverage)", "", f"Direct structural linkage: {direct_br_count:,}/{summary['BR']:,} AI BRs ({ai_internal_linkage['directCoveragePercent']:.2f}%). The matrix claims {matrix_br_count:,}/{summary['BR']:,} ({ai_internal_linkage['matrixCoveragePercent']:.2f}%), including {matrix['matrixBrEdgeNotDirectInCase']} proposed source-rule relink edges that are not confirmed direct TC links. The matrix has one additional unique BR ID ({', '.join(matrix['matrixOnlyLinkedBrIds'])}); validate before accepting it. These are structural rates only: semantic coverage remains NOT_CALCULABLE.", "", "| Measure | AI BRs | Rate | Status |", "|---|---:|---:|---|", f"| Direct TC-linked BRs | {direct_br_count:,}/{summary['BR']:,} | {ai_internal_linkage['directCoveragePercent']:.2f}% | Recounted internal links |", f"| Matrix-linked BRs | {matrix_br_count:,}/{summary['BR']:,} | {ai_internal_linkage['matrixCoveragePercent']:.2f}% | Includes proposed links; review required |", "", "## Multi-leg package audit", "", f"ZIP: {len(names)} members; manifest: {manifest['multi_leg_test_case_count']} logical flow cases / {manifest['file_count']} files. CRC failure: {crc_failure}; unsafe paths: {len(unsafe)}; missing candidate members: {len(missing)}; content differences: {len(differing)}. This verifies package/file integrity, not lifecycle behavior or host outcomes."])
    markdown.extend(["", "## October 5 comparison", "", "| Metric | October 5 | October 7 Run4 | Delta |", "|---|---:|---:|---:|"])
    markdown.extend(f"| {row['metric']} | {row['previous']:,} | {row['latest']:,} | {row['delta']:+,} |" for row in summary["comparisonWithOctober5"])
    markdown.extend(["", "## Expected-response evidence", "", "Counts overlap across tiers within a TC; presence of a tier is not a qualified outcome oracle.", "", "| Tier | Distinct cases | Nodes |", "|---|---:|---:|"])
    markdown.extend(f"| {tier} | {count:,} | {summary['expectedTierNodeCounts'][tier]:,} |" for tier, count in summary["expectedTierCaseCounts"].items())
    markdown.extend(["", "## Payload/metadata agreement scope", "", "Direct scalar correspondence was audited separately from the existing Java adapter. Matching metadata values to actual JSON values does not validate source business rules, field presence, wire bytes or expected host outcomes. Composite values and fields without direct physical paths remain unassessed.", "", "```json", json.dumps(summary["payloadMetadataValueAgreement"], indent=2), "```", "", f"Disagreements: {summary['valueDisagreementCount']}; replica content conflicts: {summary['replicaConflictCount']}; audited JSON errors: {summary['jsonErrorCount']}.", "", "## Full matrix audit", "", "The complete JSON contains 5,153 requirement records and 65,989 flat leaves. Producer test_cases_with_data_file=64,528 is a leaf-occurrence count, not a unique TC count. The direct and proposed attribution graphs remain distinct.", "", "```json", json.dumps(matrix, indent=2), "```"])
    markdown.extend(["", "Changes in BR count are not missing-rule conclusions: derive by source/business identity, not local ID. Reused IDs changed extensively.", "", "## Detailed Findings and SME Questions", ""])
    for query in query_rows:
        markdown.extend(["### " + query["queryId"] + " | " + query["subject"], "", query["evidence"], "", "Required action: " + query["question"], "", "Status: OPEN_REVIEW_REQUIRED; approval not granted.", ""])
    markdown.extend(["## Assurance boundaries", "", "- Structural linkage percentages do not measure independent semantic coverage.",
        "- Physical copy counts include reporting duplicates; unique request filenames include flow legs, not one-to-one TC artifacts.",
        "- Expected response tiers overlap within a case. ASSERT presence is not proof of a source-backed outcome oracle.",
        "- A negative invalid value is not automatically a defective test; valid controls and isolated intended-rule detection are required.",
        "- Raw R/O/C template labels are not adjudicated mandatory/conditional/optional business logic.",
        "- The existing Java report records unsupported input mappings, not successful field checks.",
        "- Confirmed semantic coverage remains NOT_CALCULABLE; execution certification false.", "", "[Filterable HTML](LATEST-RUN-INDEPENDENT-REVIEW.html) | [Case CSV](case-register.csv) | [Physical CSV](physical-file-register.csv) | [SME queries](latest-run-findings-and-sme-queries.csv)"])
    markdown_text = "\n".join(markdown)
    markdown_text = markdown_text.replace(
        f"All {test_solution_br_count:,} Test Solution BRs remain unassessed because no Run4-specific evidence-backed crosswalk exists.",
        f"A Run4-specific source-anchor candidate crosswalk now covers all {chain_summary['aiBusinessRequirementCount']:,} AI BRs; its candidates are not semantic confirmations. All {test_solution_br_count:,} Test Solution BRs remain NOT_ASSESSED pending review.")
    chain_audit = chain_summary["matrixAndChainAudit"]
    chain_markdown = ["", "## Complete Run4 AI BR Chain and Test Solution Candidate Crosswalk", "",
        f"All {chain_summary['aiBusinessRequirementCount']:,} AI BRs were included without an approval filter. The full matrix contains {chain_audit['matrixRows']:,} flat leaves: {chain_audit['matrixRowsWithTc']:,} with TC, {chain_audit['matrixRowsWithTdDeclaration']:,} with a TD declaration, and {chain_audit['matrixFullyTracedWithoutTdRows']:,} producer-labeled FULLY_TRACED without a TD declaration. Matrix-to-catalog validation found {chain_audit['matrixBrTsUnjoinedRows']:,} BR-to-TS rows not present as direct scenario requirement links and {chain_audit['testCaseScenarioMismatchRows']:,} TS-to-TC scenario disagreements.",
        f"Composed from direct AI catalogs, {chain_summary['directCatalogChainCounts']['brsWithDirectTs']:,} BRs link to TS; {chain_summary['directCatalogChainCounts']['brsWithTcViaDirectTs']:,} reach a TC through those TSs; {chain_summary['directCatalogChainCounts']['brsWithPhysicalTdViaDirectChain']:,} reach a physically present candidate TD. TD metadata joins are reported separately per BR in the complete export.",
        "", "| Test Solution source-anchor candidate disposition | AI BR count |", "|---|---:|"]
    chain_markdown.extend(f"| {status} | {count:,} |" for status, count in chain_summary["matchDispositionCounts"].items())
    chain_markdown.extend(["", f"{chain_summary['aiBrsWithSourceAnchorCandidates']:,} AI BRs have source-anchor candidates covering {chain_summary['uniqueTestSolutionRulesWithSourceAnchorCandidates']:,} distinct Test Solution BRs. All candidates remain REVIEW_REQUIRED; no title or text-similarity matching is used. `NO_EXACT_SOURCE_ANCHOR_CANDIDATE_REVIEW_REQUIRED`, unresolved segment, and unsupported element statuses are not `AI_ONLY` findings.", "", "- [Complete one-row-per-AI-BR CSV](run4-independent-br-crosswalk-v2/run4-ai-br-chain-crosswalk.csv)", "- [Structured one-row-per-AI-BR JSONL](run4-independent-br-crosswalk-v2/run4-ai-br-chain-crosswalk.jsonl)", "- [Crosswalk and chain summary JSON](run4-independent-br-crosswalk-v2/run4-ai-br-chain-crosswalk-summary.json)"])
    chain_markdown.extend(["", "## Semantic confirmation workflow", "", f"The source-backed review packet contains {semantic_review_summary['candidatePairCount']:,} AI/Test BR candidate pairs across {semantic_review_summary['independentTestRulesWithAnchorCandidates']:,} independent Test BRs; {semantic_review_summary['independentTestRulesRequiringManualAiCorpusSearch']:,} Test BRs require manual search across all 5,153 AI BRs. The separate pair decision register contains {sum(item.get('decision') == 'PENDING' for item in semantic_decision_register['decisions']):,} PENDING events and no reviewer decisions. No coverage credit is issued.", "", "Example review evidence (not a decision): AI `REQ-SRC-ATL105-PDF-001:004` states Address Line 2 is alphanumeric and at most 21 bytes. Candidate Test BR `SEGDL1-R-010` and the cited Section 13.2 source quote specify the 21-position city/space/state/space/ZIP layout. The reviewer must decide whether the AI statement is equivalent, partial, or otherwise; the shared element anchor is insufficient by itself.", "", "- [Reviewer instructions and Java recorder commands](run4-semantic-review-v2/README.md)", "- [One row per independent Test BR](run4-semantic-review-v2/run4-test-br-semantic-review.csv)", "- [Source-quoted AI/Test BR candidate pairs](run4-semantic-review-v2/run4-ai-test-br-semantic-review-pairs.jsonl)", "- [Run4-only pending decision register](run4-semantic-review-v2/run4-semantic-decision-register.json)", "- [Review packet summary](run4-semantic-review-v2/run4-semantic-review-summary.json)"])
    chain_markdown.extend(["", "## Test Team Interpretation - Element 4", "", team_interpretation["testTeamInterpretation"], "", "Element 4 is Address Line 2, character type AN, fixed width 21 bytes, carrying city/state/ZIP content. The ATL105 Section 13.2 positional detail is compatible specificity, not a contradiction. Formal pair decision remains PENDING until an authorized reviewer records it; this interpretation alone issues no coverage credit.", "", "[Dated interpretation JSON](test-team-interpretation-2026-10-08.json)"])
    chain_markdown.extend(["", "## Section 10.1 Independent Test Solution Draft Chains", "", f"The independent Test Solution source catalog has one BR with a primary source anchor inside Section 10.1; its existing complete-chain package also has 10 unique direct Section 10.1 rule anchors plus 3 related partial-approval chains. The additive review supplement reuses those references and adds {section_10_1_assessment['newDraftBRCount']} source-quoted draft BRs, each linked to one TS, TC, and TD design file.", f"New draft chains: {section_10_1_assessment['newDraftBRCount']} BR / {section_10_1_assessment['newDraftScenarioCount']} TS / {section_10_1_assessment['newDraftTestCaseCount']} TC / {section_10_1_assessment['newDraftTestDataDesignCount']} TD design records. All remain REVIEW_REQUIRED; TDs are non-converter-ready placeholders; official 601-rule baseline unchanged; coverage credit 0; execution certification false.", "", "- [Section 10.1 draft chain package](../../../test-solution-independent-review/section-10-1-credit-card-processing-review-v2/section-10-1-br-ts-tc-td-draft-package.json)", "- [Topic-level coverage assessment and reused rules](../../../test-solution-independent-review/section-10-1-credit-card-processing-review-v2/section-10-1-coverage-assessment.json)", "- [Review instructions](../../../test-solution-independent-review/section-10-1-credit-card-processing-review-v2/README.md)"])
    chain_markdown.extend(["", "## Section 10.2 Debit Card Processing Draft Chains", "", f"The additive independent Test Solution supplement contains {section_10_2_assessment['newDraftBRCount']} debit-specific source-quoted draft BRs with matching TS, TC, and TD design records, plus {section_10_2_assessment['reusedSection10_1RuleReferences']} unique references to shared Section 10.1 review drafts and {section_10_2_assessment['reusedExistingBaselineRuleReferences']} existing independent-baseline chains. Every parsed source heading is mapped below to a draft/reused chain or an explicit child grouping. All references remain REVIEW_REQUIRED; TDs are non-executable designs. The official 601-rule baseline remains unchanged, with no coverage credit or execution certification.", "", "| Source heading | Debit draft BR IDs | Reused Section 10.1 chains (BR / TS / TC / TD) | Existing baseline chains (BR / TS / TC / TD) | Grouped children |", "|---|---|---|---|---|"])
    for entry in section_10_2_assessment["subsectionCoverageMap"]:
        draft_ids = ", ".join(chain["businessRequirementId"] for chain in entry["draftChains"]) or "-"
        reused_ids = "; ".join(section_10_2_reuse_text(chain) for chain in entry["reusedSection10_1Chains"]) or "-"
        baseline_ids = "; ".join(section_10_2_reuse_text(chain, baseline=True) for chain in entry["reusedIndependentBaselineChains"]) or "-"
        children = ", ".join(entry["groupedChildSections"]) or "-"
        chain_markdown.append(f"| {entry['section']} | {draft_ids} | {reused_ids} | {baseline_ids} | {children} |")
    chain_markdown.extend(["", "- [Section 10.2 debit draft package](../../../test-solution-independent-review/section-10-2-debit-card-processing-review-v2/section-10-2-br-ts-tc-td-draft-package.json)", "- [Complete heading-to-chain assessment](../../../test-solution-independent-review/section-10-2-debit-card-processing-review-v2/section-10-2-coverage-assessment.json)", "- [Debit review gates](../../../test-solution-independent-review/section-10-2-debit-card-processing-review-v2/README.md)"])
    chain_markdown.extend(["", "## Manual corpus search findings", "", f"{len(manual_search_findings['findings'])} manual-search finding(s) are recorded. `NO_EQUIVALENT_AI_BR_FOUND` documents a completed Run4 search only; it is feedback for the AI team, not a global absence claim. Formal status remains PENDING_SEMANTIC_REVIEW, no authorized SME decision is recorded, and no coverage credit is issued.", "", "| Test Solution BR | Finding | Formal status | Credit | AI team feedback |", "|---|---|---|---|---|"])
    chain_markdown.extend(f"| {item['testRuleId']} | {item['finding']} | {item['formalReviewStatus']} | None | {item['aiTeamFeedback']['status']} ({item['aiTeamFeedback']['runDate']}): {item['aiTeamFeedback']['message']} |" for item in manual_search_findings["findings"])
    chain_markdown.extend(["", "[Manual-search findings JSON](run4-semantic-review-v2/run4-manual-search-findings.json)"])
    markdown_text += "\n" + "\n".join(chain_markdown)
    markdown_text = markdown_text.replace("section-10-1-credit-card-processing-review-v2", "section-10-1-credit-card-processing-review-v4")
    markdown_text = markdown_text.replace("section-10-2-debit-card-processing-review-v1", "section-10-2-debit-card-processing-review-v2")
    (root / "DETAILED-LATEST-RUN-REVIEW.md").write_text(markdown_text, encoding="utf-8")
    compact_cases = [{key: row[key] for key in ["caseId", "scenarioId", "intent", "transactionLabel", "requirementIds", "expectedResponseTiers", "hasSpecificAssertEvidence", "responseTemplateAvailable", "approvedInProducerCatalog", "verificationStatus", "physicalOutputPresent", "structuralStatus"]} for row in cases]
    data = {"summary": summary, "brCoverage": br_coverage, "teamInterpretation": team_interpretation,
            "section10_1DraftSupplement": section_10_1_assessment,
            "section10_2DraftSupplement": section_10_2_assessment,
            "manualSearchFindings": manual_search_findings,
            "semanticReview": {"summary": semantic_review_summary,
                               "pendingPairDecisions": sum(item.get("decision") == "PENDING" for item in semantic_decision_register["decisions"])},
            "cases": compact_cases, "files": files, "queries": query_rows, "statistics": statistics,
            "multiLegPackage": package_audit,
            "independentValueCheck": element_report}
    manual_search_rows_html = "".join(
        f"<tr><td>{escape(item['testRuleId'])}</td><td>{escape(item['finding'])}</td><td>{escape(item['rationale'])}</td><td>{escape(item['formalReviewStatus'])}</td><td>None</td><td>{escape(item['aiTeamFeedback']['status'])} ({escape(item['aiTeamFeedback']['runDate'])}): {escape(item['aiTeamFeedback']['message'])}</td></tr>"
        for item in manual_search_findings["findings"])
    manual_search_html = (f'<section><h2>Manual corpus search findings</h2><p>{len(manual_search_findings["findings"])} completed manual-search finding(s) are recorded. NO_EQUIVALENT_AI_BR_FOUND is a Run4 search result for AI-team feedback, not a global absence claim; formal status remains PENDING_SEMANTIC_REVIEW, no authorized SME decision is recorded, and no coverage credit is issued.</p><div class="wrap"><table><thead><tr><th>Test Solution BR</th><th>Finding</th><th>Rationale</th><th>Formal status</th><th>Coverage credit</th><th>AI team feedback</th></tr></thead><tbody>{manual_search_rows_html}</tbody></table></div><a href="run4-semantic-review-v2/run4-manual-search-findings.json">Manual-search findings JSON</a></section>')
    embedded = json.dumps(data, ensure_ascii=True, separators=(",", ":")).replace("</", "<\\/")
    html = TEMPLATE.replace("__DATA__", embedded)
    html = html.replace('<section><h2>Complete candidate TC register', manual_search_html + '<section><h2>Complete candidate TC register')
    html = html.replace("for(const idof [", "for(const idof of [")
    html = html.replace('<section><h2>October 5 versus latest Run4', f'<section><h2>Independent Test Solution BR coverage</h2><p><strong>Confirmed common Test Solution BRs: 0/{test_solution_br_count:,} (0.00% confirmed evidence); status: NOT_ASSESSED.</strong> No Run4-specific evidence-backed crosswalk exists; all {test_solution_br_count:,} independent rules remain unassessed. Zero confirmed matches is not evidence that no equivalent AI BRs exist. The {sum(historical_status_counts.values()):,}-entry decision register is from September Run2; its pending decisions are not reused.</p><p>Separate AI-internal diagnostic: {direct_br_count:,}/{summary["BR"]:,} AI BRs directly linked to candidate TCs ({ai_internal_linkage["directCoveragePercent"]:.2f}%). The matrix reports {matrix_br_count:,}/{summary["BR"]:,} ({ai_internal_linkage["matrixCoveragePercent"]:.2f}%) including {matrix["matrixBrEdgeNotDirectInCase"]} proposed, unconfirmed edges. Neither is Test Solution BR coverage.</p><a href="test-solution-br-coverage.json">Coverage evidence JSON</a> | <a href="../../../test-solution-independent-review/source-derived-requirement-inventory.json">Independent 601-rule baseline</a></section><section><h2>October 5 versus latest Run4')
    html = html.replace(
        f"No Run4-specific evidence-backed crosswalk exists; all {test_solution_br_count:,} independent rules remain unassessed.",
        f"A source-anchor candidate crosswalk now covers all {chain_summary['aiBusinessRequirementCount']:,} AI BRs; candidates are not semantic matches. All {test_solution_br_count:,} independent rules remain NOT_ASSESSED pending review.")
    chain_html = (f'<section><h2>All AI BR chain and Test Solution candidates</h2><p>Approval-independent population: {chain_summary["aiBusinessRequirementCount"]:,} AI BRs. Matrix leaves: {chain_audit["matrixRows"]:,}; TC rows: {chain_audit["matrixRowsWithTc"]:,}; declared-TD rows: {chain_audit["matrixRowsWithTdDeclaration"]:,}; FULLY_TRACED leaves without TD: {chain_audit["matrixFullyTracedWithoutTdRows"]:,}; BR-to-TS rows not joined to direct scenario requirements: {chain_audit["matrixBrTsUnjoinedRows"]:,}; TS-to-TC scenario disagreements: {chain_audit["testCaseScenarioMismatchRows"]:,}.</p><p>Direct catalogs compose BR&gt;TS for {chain_summary["directCatalogChainCounts"]["brsWithDirectTs"]:,} BRs, BR&gt;TS&gt;TC for {chain_summary["directCatalogChainCounts"]["brsWithTcViaDirectTs"]:,}, and through physically present TD for {chain_summary["directCatalogChainCounts"]["brsWithPhysicalTdViaDirectChain"]:,}. Test BR candidates: {chain_summary["aiBrsWithSourceAnchorCandidates"]:,} AI BRs across {chain_summary["uniqueTestSolutionRulesWithSourceAnchorCandidates"]:,} distinct Test BRs. Candidate status is review-required, never confirmed.</p><a href="run4-independent-br-crosswalk-v2/run4-ai-br-chain-crosswalk.csv">Complete AI BR chain/match CSV</a> | <a href="run4-independent-br-crosswalk-v2/run4-ai-br-chain-crosswalk.jsonl">Complete structured JSONL</a> | <a href="run4-independent-br-crosswalk-v2/run4-ai-br-chain-crosswalk-summary.json">Chain summary JSON</a></section>')
    semantic_review_html = (f'<section><h2>Source-backed semantic confirmation workflow</h2><p>All {semantic_review_summary["independentTestSolutionBrCount"]:,} independent Test BRs are in a reviewer queue. {semantic_review_summary["candidatePairCount"]:,} source-anchor candidate pairs cover {semantic_review_summary["independentTestRulesWithAnchorCandidates"]:,} Test BRs; {semantic_review_summary["independentTestRulesRequiringManualAiCorpusSearch"]:,} Test BRs require manual search across the complete AI BR population. The Run4-specific register contains {sum(item.get("decision") == "PENDING" for item in semantic_decision_register["decisions"]):,} PENDING pair decisions; none are confirmed and no coverage credit is issued.</p><p>Reviewers receive exact ATL105 2026-3 source excerpts, Test Rule catalog records, AI statements and per-BR chain evidence. Example: AI statement for Element 4 names alphanumeric/21-byte form; Test Rule SEGDL1-R-010 and Section 13.2 source also specify positional city/state/ZIP fields. Shared anchors alone do not establish equivalence.</p><a href="run4-semantic-review-v2/README.md">Reviewer instructions and recorder</a> | <a href="run4-semantic-review-v2/run4-test-br-semantic-review.csv">601-rule review queue</a> | <a href="run4-semantic-review-v2/run4-ai-test-br-semantic-review-pairs.jsonl">Source-quoted candidate pairs</a> | <a href="run4-semantic-review-v2/run4-semantic-decision-register.json">Run4-only pending decisions</a></section>')
    interpretation_html = (f'<section><h2>Test Team Interpretation: Element 4</h2><p><strong>{escape(team_interpretation["testTeamInterpretation"])}</strong></p><p>Element 4 is Address Line 2, character type AN, fixed width 21 bytes, carrying city/state/ZIP content. The Section 13.2 positional detail is compatible specificity, not a contradiction. Formal pair decision remains PENDING until an authorized reviewer records it; no coverage credit is issued.</p><a href="test-team-interpretation-2026-10-08.json">Dated interpretation evidence</a></section>')
    section_10_1_html = (f'<section><h2>Section 10.1 Independent Test Solution Draft Chains</h2><p>Existing Test Solution rules are reused by reference; the add-on creates {section_10_1_assessment["newDraftBRCount"]} source-quoted draft BRs, each with a review TS, TC and TD design. All are REVIEW_REQUIRED; TD designs are non-executable placeholders. The existing 601-rule baseline is unchanged, coverage credit is zero, and execution certification is false.</p><a href="../../../test-solution-independent-review/section-10-1-credit-card-processing-review-v2/section-10-1-br-ts-tc-td-draft-package.json">46 Section 10.1 draft chains</a> | <a href="../../../test-solution-independent-review/section-10-1-credit-card-processing-review-v2/section-10-1-coverage-assessment.json">Topic coverage and reused rule assessment</a> | <a href="../../../test-solution-independent-review/section-10-1-credit-card-processing-review-v2/README.md">Review gates and instructions</a></section>')
    section_10_2_rows_html = []
    for entry in section_10_2_assessment["subsectionCoverageMap"]:
        draft_ids = ", ".join(chain["businessRequirementId"] for chain in entry["draftChains"]) or "-"
        reused_ids = "; ".join(section_10_2_reuse_text(chain) for chain in entry["reusedSection10_1Chains"]) or "-"
        baseline_ids = "; ".join(section_10_2_reuse_text(chain, baseline=True) for chain in entry["reusedIndependentBaselineChains"]) or "-"
        children = ", ".join(entry["groupedChildSections"]) or "-"
        section_10_2_rows_html.append("<tr>" + "".join(f"<td>{escape(value)}</td>" for value in
            [entry["section"], draft_ids, reused_ids, baseline_ids, children]) + "</tr>")
    section_10_2_html = (f'<section><h2>Section 10.2 Debit Card Processing Draft Chains</h2><p>The debit supplement adds {section_10_2_assessment["newDraftBRCount"]} source-quoted debit-specific BR/TS/TC/TD design chains, {section_10_2_assessment["reusedSection10_1RuleReferences"]} unique Section 10.1 draft references, and {section_10_2_assessment["reusedExistingBaselineRuleReferences"]} existing independent-baseline references. The map includes all {len(section_10_2_assessment["sourceSectionHeadings"])} source headings; all references remain REVIEW_REQUIRED, TDs are non-executable, and the 601-rule baseline is unchanged with zero coverage credit.</p><div class="wrap"><table><thead><tr><th>Source heading</th><th>Debit draft BR IDs</th><th>Reused Section 10.1 chains (BR / TS / TC / TD)</th><th>Existing baseline chains (BR / TS / TC / TD)</th><th>Grouped children</th></tr></thead><tbody>{"".join(section_10_2_rows_html)}</tbody></table></div><p><a href="../../../test-solution-independent-review/section-10-2-debit-card-processing-review-v2/section-10-2-br-ts-tc-td-draft-package.json">Debit draft chain package</a> | <a href="../../../test-solution-independent-review/section-10-2-debit-card-processing-review-v2/section-10-2-coverage-assessment.json">Heading-to-chain assessment</a> | <a href="../../../test-solution-independent-review/section-10-2-debit-card-processing-review-v2/README.md">Debit review gates</a></p></section>')
    html = html.replace('<section><h2>Complete candidate TC register', chain_html + semantic_review_html + interpretation_html + section_10_1_html + section_10_2_html + '<section><h2>Complete candidate TC register')
    html = html.replace('<section><h2>Full matrix', f'<section><h2>Multi-leg package audit</h2><p>{len(names)} ZIP members; {manifest["multi_leg_test_case_count"]} flow cases; CRC failure: {crc_failure}; {len(unsafe)} unsafe paths; {len(missing)} missing candidate members; {len(differing)} content differences. Package integrity does not certify lifecycle or host behavior.</p><a href="multileg-package-audit.json">Package audit JSON</a></section><section><h2>Full matrix')
    html = html.replace("section-10-1-credit-card-processing-review-v2", "section-10-1-credit-card-processing-review-v4")
    html = html.replace("section-10-2-debit-card-processing-review-v1", "section-10-2-debit-card-processing-review-v2")
    html = html.replace(">46 Section 10.1 draft chains<", f">{section_10_1_assessment['newDraftBRCount']} Section 10.1 draft chains<")
    (root / "LATEST-RUN-INDEPENDENT-REVIEW.html").write_text(html, encoding="utf-8")
    from reportlab.lib import colors
    from reportlab.lib.styles import ParagraphStyle
    from reportlab.lib.pagesizes import A4
    from reportlab.platypus import SimpleDocTemplate, Paragraph, Spacer, Table, TableStyle
    body = ParagraphStyle("Body", fontName="Helvetica", fontSize=9, leading=13, spaceAfter=8)
    heading = ParagraphStyle("Heading", parent=body, fontName="Helvetica-Bold", fontSize=16, leading=20, spaceAfter=12)
    story = [Paragraph("ATL105 | October 7 Run4 Independent Review", heading), Paragraph("REVIEW REQUIRED. Verified structural evidence is not semantic approval or host certification.", body)]
    table = Table([[Paragraph(escape(str(value)), body) for value in row] for row in [["Measure", "Recount"], *statistics]], colWidths=[360, 150])
    table.setStyle(TableStyle([("BACKGROUND", (0, 0), (-1, 0), colors.HexColor("#e5efec")), ("GRID", (0, 0), (-1, -1), .3, colors.lightgrey), ("VALIGN", (0, 0), (-1, -1), "TOP")]))
    story.extend([table, Spacer(1, 16)])
    story.append(Paragraph(escape(f"Manual corpus search findings: {len(manual_search_findings['findings'])}; NO_EQUIVALENT_AI_BR_FOUND is Run4 feedback for the AI team, not a global absence claim. All formal reviews remain PENDING_SEMANTIC_REVIEW, with no authorized SME decision or coverage credit."), body))
    story.append(Paragraph('<link href="run4-semantic-review-v2/run4-manual-search-findings.json" color="blue">Manual-search findings JSON</link>', body))
    story.append(Paragraph(escape(f"Independent Test Solution BR coverage: 0/{test_solution_br_count:,} confirmed Run4 matches (0.00% confirmed evidence), status NOT_ASSESSED; all rules remain unassessed. A complete source-anchor candidate crosswalk covers all {chain_summary['aiBusinessRequirementCount']:,} AI BRs: {chain_summary['aiBrsWithSourceAnchorCandidates']:,} AI BRs have candidate links to {chain_summary['uniqueTestSolutionRulesWithSourceAnchorCandidates']:,} distinct Test BRs, but candidates are not semantic confirmations. The matrix has {chain_audit['matrixRows']:,} leaves; direct BR>TS>TC>physical-TD paths reach {chain_summary['directCatalogChainCounts']['brsWithPhysicalTdViaDirectChain']:,} BRs. Zero confirmed matches do not prove no equivalent AI BRs exist. The former {direct_br_count:,}/{summary['BR']:,} ({ai_internal_linkage['directCoveragePercent']:.2f}%) is AI-internal linkage only; full semantic coverage remains NOT_CALCULABLE."), body))
    story.append(Paragraph(escape(f"Source-backed review workflow: {semantic_review_summary['candidatePairCount']:,} candidate pairs across {semantic_review_summary['independentTestRulesWithAnchorCandidates']:,} independent Test BRs; {semantic_review_summary['independentTestRulesRequiringManualAiCorpusSearch']:,} require manual corpus search. All {sum(item.get('decision') == 'PENDING' for item in semantic_decision_register['decisions']):,} Run4 pair decisions remain PENDING. No semantic confirmations or coverage credit have been granted."), body))
    story.append(Paragraph(escape("Test Team interpretation for Element 4: both BRs are correct and semantically aligned. Element 4 is Address Line 2, character type AN, fixed width 21 bytes, carrying city/state/ZIP content; the Test BR's positional detail is compatible specificity, not a contradiction. The formal pair decision remains PENDING until an authorized reviewer records it; no coverage credit is issued."), body))
    story.append(Paragraph('<link href="test-team-interpretation-2026-10-08.json" color="blue">Dated Test Team interpretation addendum</link>', body))
    story.append(Paragraph(escape(f"Section 10.1 independent Test Solution draft supplement: {section_10_1_assessment['newDraftBRCount']} BR -> TS -> TC -> TD design chains and {section_10_1_assessment['reusedExistingRuleReferenceCount']} existing rule references. All draft chains remain REVIEW_REQUIRED; TD designs are non-executable; baseline unchanged; no coverage credit."), body))
    story.append(Paragraph('<link href="../../../test-solution-independent-review/section-10-1-credit-card-processing-review-v4/section-10-1-coverage-assessment.json" color="blue">Section 10.1 chain assessment</link>', body))
    story.append(Paragraph(escape(f"Section 10.2 debit draft supplement: {section_10_2_assessment['newDraftBRCount']} debit-specific BR -> TS -> TC -> TD chains; {section_10_2_assessment['reusedSection10_1RuleReferences']} unique Section 10.1 references; {section_10_2_assessment['reusedExistingBaselineRuleReferences']} existing baseline references. All {len(section_10_2_assessment['sourceSectionHeadings'])} source headings are structurally mapped; every reference remains review-required, with no coverage credit or execution certification."), body))
    story.append(Paragraph('<link href="../../../test-solution-independent-review/section-10-2-debit-card-processing-review-v2/section-10-2-coverage-assessment.json" color="blue">Section 10.2 heading-to-chain assessment with IDs</link>', body))
    story.append(Paragraph(f"Multi-leg ZIP: {len(names)} files / {manifest['multi_leg_test_case_count']} cases; CRC failure {crc_failure}; {len(missing)} missing and {len(differing)} differing candidate members. Package integrity is not flow or host certification.", body))
    for query in query_rows:
        story.extend([Paragraph(escape(query["priority"] + " | " + query["subject"]), heading), Paragraph(escape(query["evidence"]), body), Paragraph("Required action: " + escape(query["question"]), body)])
    SimpleDocTemplate(str(root / "LATEST-RUN-INDEPENDENT-REVIEW.pdf"), pagesize=A4, leftMargin=40, rightMargin=40).build(story)
    assert len(cases) == summary["TC"] and len(files) == summary["physicalRequestFilesAcrossLocations"]
    assert all(not query["approvalGranted"] for query in query_rows)
    assert crc_failure is None and not unsafe and len(names) == manifest["file_count"]
    assert direct_br_count == 3719 and matrix_br_count == 3720 and len(matrix["matrixOnlyLinkedBrIds"]) == 1
    print("Generated extensive Markdown, PDF, filtered HTML and source/SME finding registers")


TEMPLATE = '''<!doctype html><html lang="en"><meta charset="utf-8"><meta name="viewport" content="width=device-width,initial-scale=1"><title>ATL105 | October 7 Run4 Independent Review</title><style>body{margin:0;background:#f4f6f5;color:#202526;font:14px 'Segoe UI',sans-serif;letter-spacing:0;overflow-wrap:anywhere}header{padding:24px;background:white;border-top:5px solid #07665e}h1{font:700 26px Georgia,serif}h2{font-size:20px;color:#07665e}main{max-width:1450px;margin:auto;padding:24px}section{padding:20px 0;border-bottom:1px solid #cdd8d4}.notice{padding:14px;border-left:4px solid #a06b12;background:#fff6df}.filters{display:flex;gap:10px;flex-wrap:wrap;margin:14px 0}input,select,button{padding:9px;font:inherit;border:1px solid #aab5b0;background:white;border-radius:4px}table{width:100%;border-collapse:collapse;background:white}td,th{padding:9px;border-bottom:1px solid #d6dfdb;text-align:left;vertical-align:top}th{background:#e5efec}.wrap{overflow:auto}.pager{display:flex;gap:12px;margin:12px 0;align-items:center}a{color:#07665e}@media(max-width:650px){header,main{padding:16px}.wrap table{min-width:850px}}@media print{.filters,.pager{display:none}}</style><header><h1>ATL105 | Latest AI Delivery Independent Review</h1><p>October 7 Run4 | Pinned archive / original preserved | REVIEW REQUIRED</p><a href="LATEST-RUN-INDEPENDENT-REVIEW.pdf">Executive PDF</a> | <a href="DETAILED-LATEST-RUN-REVIEW.md">Detailed review</a> | <a href="../../2026-10-06-complete-handoff/AI-ARTIFACT-FILTER-VIEW.html">October 5 history</a></header><main>
<section><h2>Verified inventory and assurance</h2><div class="notice">All 129,764 files are SHA-verified. Structural linkage, producer ASSERT tiers and automated approvals are separate from confirmed business equivalence and actual host outcomes. Semantic coverage: NOT_CALCULABLE. Execution certification: NOT GRANTED.</div><div class="wrap"><table><thead><tr><th>Metric</th><th>Recount</th></tr></thead><tbody id="statistics"></tbody></table></div></section>
<section><h2>October 5 versus latest Run4</h2><div class="wrap"><table><thead><tr><th>Metric</th><th>October 5</th><th>Latest</th><th>Delta</th></tr></thead><tbody id="comparison"></tbody></table></div><p>Changing inventories and reused IDs require source/content remapping. A higher producer linkage ratio is not a semantic-coverage improvement.</p></section>
<section><h2>Expected outcomes and approval provenance</h2><div class="wrap"><table><thead><tr><th>Producer tier</th><th>Case count</th><th>Node count</th></tr></thead><tbody id="tiers"></tbody></table></div><p id="approval"></p><p id="adapter"></p></section>
<section><h2>Complete candidate TC register</h2><div class="filters"><input id="case-search" aria-label="Search TC, TS or BR" placeholder="TC / TS / BR"><select id="case-filter" aria-label="Case evidence state"></select></div><p id="case-count"></p><div class="wrap"><table><thead><tr><th>TC/TS</th><th>Intent/transaction</th><th>BRs</th><th>Physical data</th><th>Expected tiers</th><th>Producer approved/verified</th></tr></thead><tbody id="case-body"></tbody></table></div><div class="pager"><button id="case-prev" aria-label="Previous cases">&#8592;</button><span id="case-page"></span><button id="case-next" aria-label="Next cases">&#8594;</button></div><a href="case-register.csv">Complete case CSV</a></section>
<section><h2>Physical request files and metadata</h2><div class="filters"><input id="file-search" aria-label="Search physical files" placeholder="TC filename"><select id="file-filter" aria-label="Physical evidence state"></select></div><p id="file-count"></p><div class="wrap"><table><thead><tr><th>File/location</th><th>Metadata join</th><th>Rendered fields</th><th>Empty body</th><th>Provisional/unavailable/unresolved</th></tr></thead><tbody id="file-body"></tbody></table></div><div class="pager"><button id="file-prev" aria-label="Previous files">&#8592;</button><span id="file-page"></span><button id="file-next" aria-label="Next files">&#8594;</button></div><a href="physical-file-register.csv">Complete physical CSV</a></section>
<section><h2>Findings and SME/source follow-up</h2><div class="wrap"><table><thead><tr><th>ID / priority</th><th>Finding</th><th>Evidence</th><th>Required action</th><th>Status</th></tr></thead><tbody id="queries"></tbody></table></div><a href="latest-run-findings-and-sme-queries.csv">Finding/query CSV</a></section>
<section><h2>Full matrix and supporting exports</h2><p id="matrix"></p><p><a href="LATEST-RUN-ANALYSIS.json">Complete analysis JSON/hashes</a> | <a href="matrix-proposed-relinks.csv">62 proposed source-rule relinks</a> | <a href="matrix-missing-td-rows.csv">Matrix missing TD rows</a> | <a href="scenario-without-case.csv">1,437 no-TC scenarios</a> | <a href="declared-missing-data-files.json">24 missing TD declarations</a> | <a href="report-discrepancies.json">Report contradictions</a> | <a href="../intake-verification/intake-verification.json">Intake hash verification</a> | <a href="../independent-element-validation.json">Independent adapter result</a></p></section></main>
<script id="data" type="application/json">__DATA__</script><script>'use strict';const d=JSON.parse(document.getElementById('data').textContent),$=id=>document.getElementById(id);let cp=0,fp=0;function row(id,vals){const tr=document.createElement('tr');for(const val of vals){const td=document.createElement('td');td.textContent=val??'';tr.append(td)}$(id).append(tr)}for(const r of d.statistics)row('statistics',r);for(const r of d.summary.comparisonWithOctober5)row('comparison',[r.metric,r.previous,r.latest,r.delta]);for(const [tier,count]of Object.entries(d.summary.expectedTierCaseCounts))row('tiers',[tier,count,d.summary.expectedTierNodeCounts[tier]]);$('approval').textContent='Producer approval reviewers: '+JSON.stringify(d.summary.approvalReviewerCounts)+'. Approval is automated/non-SME, not business acceptance.';$('adapter').textContent='Independent adapter read '+d.independentValueCheck.messagesAssessed+' metadata records; observations: '+JSON.stringify(d.independentValueCheck.observationsByStatus)+'. Unsupported roots require reviewed adapter mapping; REVIEW_REQUIRED is not PASS.';for(const q of d.queries)row('queries',[q.queryId+' / '+q.priority,q.subject,q.evidence,q.question,q.status]);$('matrix').textContent='Matrix flat rows: '+d.summary.matrix.recordCounts.flat_rows.records+'; independent graph audit: '+JSON.stringify(d.summary.matrix.independentGraphAudit);$('case-filter').replaceChildren(...[['','All cases'],['missing','No verified physical TD'],['approved','Producer approved'],['assert','ASSERT tier present'],['no-br','No direct BR attribution'],['reject','Producer rejected final']].map(([v,l])=>new Option(l,v)));$('file-filter').replaceChildren(...[['','All physical copies'],['empty','Empty message body'],['zero','No metadata fields'],['join','Invalid/missing metadata join'],['provisional','Provisional fields']].map(([v,l])=>new Option(l,v)));function cases(){const s=$('case-search').value.toLowerCase(),f=$('case-filter').value,a=d.cases.filter(x=>(!s||[x.caseId,x.scenarioId,...x.requirementIds].join(' ').toLowerCase().includes(s))&&(!f||f==='missing'&&!x.physicalOutputPresent||f==='approved'&&x.approvedInProducerCatalog||f==='assert'&&x.expectedResponseTiers.includes('ASSERT')||f==='no-br'&&!x.requirementIds.length||f==='reject'&&x.verificationStatus==='REJECTED_FINAL'));const n=Math.max(1,Math.ceil(a.length/50));cp=Math.min(cp,n-1);$('case-body').replaceChildren();for(const x of a.slice(cp*50,(cp+1)*50))row('case-body',[x.caseId+' / '+x.scenarioId,x.intent+' / '+x.transactionLabel,x.requirementIds.join(', '),x.physicalOutputPresent?'Present':'Missing',x.expectedResponseTiers.join(', '),x.approvedInProducerCatalog+' / '+x.verificationStatus]);$('case-count').textContent=a.length.toLocaleString()+' selected / '+d.cases.length.toLocaleString();$('case-page').textContent='Page '+(cp+1)+' / '+n;$('case-prev').disabled=cp===0;$('case-next').disabled=cp===n-1}function files(){const s=$('file-search').value.toLowerCase(),f=$('file-filter').value,a=d.files.filter(x=>(!s||x.file.toLowerCase().includes(s))&&(!f||f==='empty'&&x.emptyMessageBody||f==='zero'&&x.metadataFields===0||f==='join'&&!x.joinValid||f==='provisional'&&x.provisionalFields>0));const n=Math.max(1,Math.ceil(a.length/50));fp=Math.min(fp,n-1);$('file-body').replaceChildren();for(const x of a.slice(fp*50,(fp+1)*50))row('file-body',[x.location,x.joinValid,x.metadataFields,x.emptyMessageBody,x.provisionalFields+' / '+x.unavailableFields+' / '+x.unresolvedSegments]);$('file-count').textContent=a.length.toLocaleString()+' selected / '+d.files.length.toLocaleString();$('file-page').textContent='Page '+(fp+1)+' / '+n;$('file-prev').disabled=fp===0;$('file-next').disabled=fp===n-1}for(const idof ['case-search','case-filter'])$(idof).addEventListener(idof==='case-search'?'input':'change',()=>{cp=0;cases()});for(const idof ['file-search','file-filter'])$(idof).addEventListener(idof==='file-search'?'input':'change',()=>{fp=0;files()});$('case-prev').onclick=()=>{cp--;cases()};$('case-next').onclick=()=>{cp++;cases()};$('file-prev').onclick=()=>{fp--;files()};$('file-next').onclick=()=>{fp++;files()};cases();files();</script></html>'''


def main():
    parser = argparse.ArgumentParser()
    parser.add_argument("analysis_directory", type=Path)
    args = parser.parse_args()
    render(args.analysis_directory)


if __name__ == "__main__":
    main()