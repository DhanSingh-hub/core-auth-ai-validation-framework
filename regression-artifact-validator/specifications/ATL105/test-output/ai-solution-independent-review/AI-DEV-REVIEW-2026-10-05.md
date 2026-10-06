# AI-DEV Review: ATL105 BR and Scenario Delivery

**2026-10-06 supplement notice:** A fuller handoff of the same Run1 has now been received and archived unchanged. Its four BR/TS catalog hashes match this original delivery; it adds 21,123 TC candidates and 21,210 physical payload/metadata pairs. Statements below about absent TC/TD refer to the original received subset, not the supplement. The composer failed its own generation gates; independent coverage remains unestablished. See the [detailed supplement review](2026-10-06-complete-handoff/DETAILED-REVIEW.md). Original findings below are retained as dated evidence.

**Review date:** 2026-10-05
**Delivery:** `SRC-ATL105-PDF-001`, generated 2026-10-01 and received 2026-10-05
**Disposition:** `REVIEW_REQUIRED`
**Coverage eligible:** No
**Audience:** AI-DEV team

## Executive Summary

This review treats the four JSON catalogs and two Excel workbooks as **one AI pipeline delivery**. The Excel files are candidate-catalog exports from the same run; they are not a separate run and do not add downstream test artifacts.

The delivery contains 6,887 business requirements (BRs) and 12,679 test scenarios (TSs). Internal TS-to-BR references are mostly resolvable: 48,868 references were checked, every referenced BR exists, and all 6,887 BRs are referenced. However, 155 scenarios have no BR link. The delivery contains no test cases (TCs), no test data (TDs), no canonical source anchors, and no independent Test Solution crosswalk. It therefore cannot support full-chain traceability or a coverage claim.

The claimed `APPROVED` state is not supported by human review evidence. Both approval audits identify `automated-batch-nonSME`; both approved catalogs contain exactly the same records as their candidate catalogs. The scenario catalog includes 3,198 `FAIL` verdicts and 10,559 flagged scenarios. The delivery must remain `REVIEW_REQUIRED` until the findings below are dispositioned.

## Scope and Evidence

The combined review scope uses the four JSON catalogs from `scenerio_req_5_oct` and the two Excel candidate exports from `Re__Run_Artifacts_from_latest_pipeline_run_`. They share the same source ID, catalog contents, and generation timestamps; they are treated as one pipeline delivery:

| Artifact | Records / content | SHA-256 |
|---|---:|---|
| `step5_requirements/approved/requirement_catalog.json` | 6,887 BRs | `28F5E1360BC79A0E4CE39C7C68226B6DA2CBE55719F35359B656601DC75FEE35` |
| `step5_requirements/candidates/requirement_candidates.json` | 6,887 BRs | `0511FC9EB478142E8E0951216AD0462242ABAA1FAE60AF942F3434C4D6776B25` |
| `scenarios/approved/approved_scenarios.json` | 12,679 TSs | `43977811953D1A3A2DDF0043C8D2AFFEC86E3B7AF4116441F54BAFD200E20C78` |
| `scenarios/candidates/candidate_scenarios.json` | 12,679 TSs | `A0BA82A7F1B114949F21A9BBC1CECA0EF48B384C4134E461CF3E4BD4F8EE598F` |
| `requirement_candidates.xlsx` | Summary and Requirements sheets | `B341944093CAB3F14683C5CF0B3DBA35D860C18E389DD850A4E588D304910059` |
| `candidate_scenarios.xlsx` | Summary and Scenarios sheets | `E8EAB91D52D203AACC64D03A1AC126F54AF5FFBD4D2D7C5F5120C25031741747` |

The files are preserved together in `specifications/ATL105/test-input/ai-solution/runs/2026-10-05/Run1/`. The workbook SHA-256 values match the OneDrive originals. Workbook row counts and IDs match the corresponding candidate JSON catalogs. Approved and candidate JSON records are identical, record for record. This review did not modify the source artifacts.

The existing machine-readable intake result is `specifications/ATL105/test-output/ai-solution-independent-review/run1-br-ts-catalog-validation.json`. It records `REVIEW_REQUIRED`, `coverageEligible=false`, `fullChainEligible=false`, and 81,271 canonical traceability errors, including repeated per-BR source-anchor errors. This is a validator error count, not a count of 81,271 independent business defects.

## What Passed

- All four JSON files parse and their declared BR/TS counts match the actual record counts.
- Both Excel workbooks open and have the expected Summary plus Requirements or Scenarios sheet.
- There are no duplicate BR IDs or TS IDs within the catalogs.
- The approved and candidate catalogs have equal counts and identical record contents.
- All 48,868 scenario-to-requirement references resolve to a BR in this delivery; 6,887 distinct BR IDs are referenced.
- The scenario summary's type and transaction totals each sum to 12,679.
- Every scenario record has `spec_version=2026-3`; the BR catalog itself does not declare a specification version in its manifest.

These are structural and producer-internal consistency checks. They do not establish that the requirements or scenarios are correct against ATL105.

## Findings

### Blockers

**F-01: Automated approval is represented as human approval.** Both approved catalogs say `Scripted approval (no SME review)`. Both audits say `reviewed_by=automated-batch-nonSME`. The approved and candidate record arrays are identical: 6,887/6,887 BRs and 12,679/12,679 TSs. Scenario approval marks all 12,679 records as newly added. The current `APPROVED` status is therefore not evidence of SME/business approval.

**Required action:** Use a non-approved state until a named human reviewer records a decision and evidence. Preserve candidate and approved artifacts separately, but include a reproducible disposition log. Do not require arbitrary content changes; require evidence that the approval decision actually occurred.

**F-02: The approved scenario set contains explicit failures and a high review burden.** There are 3,198 `llm_verdict=FAIL` records (25.2% of all scenarios), 10,559 flagged scenarios (83.3%), and 4,976 low-confidence scenarios (39.2%). The first examples have `TRACEBACK_MISMATCH` findings, 15% confidence, and `suggested_action=REJECT`, while remaining in the approved catalog.

**Required action:** Reconcile every `FAIL` and review flag. Exclude rejected items from an accepted baseline, or retain them under an explicit rejected/review status with their source evidence and resolution. Recompute state and summaries after disposition.

**F-03: Full-chain and independent coverage validation cannot run on this delivery.** This delivery has BRs and TSs only: zero TCs and zero TDs. It also has no canonical `sourceAnchors` and no independently owned Test Solution crosswalk. The internal AI `requirement_id(s)` links are not an AI-to-Test Solution mapping.

**Required action:** Do not report BR coverage, executable coverage, or full-chain coverage from these files. For full validation, provide linked TC and TD catalogs and map BR/TS records to the independent Test Solution rule baseline through versioned canonical source anchors.

**F-04: Source identity is incomplete for independent matching.** The BR catalog has `source_id=SRC-ATL105-PDF-001` but no manifest specification version. BR records use page/entity references rather than canonical anchors. The scenario records declare version `2026-3`, but are missing source rules on 3,416 scenarios (26.9%). The package does not include the source PDF or its source-document SHA-256. The BR field `approved_source_hash` equals the SHA-256 of the candidate BR JSON, not the ATL105 source document; it binds an artifact copy, not specification provenance.

**Required action:** Include a delivery manifest with ATL105 version, source document identity and SHA-256, generator/build identity, and artifact hashes. Carry canonical anchors (specification, version, section, segment, element, rule) on every BR and TS, with evidence references that resolve to the pinned source.

### Major Findings

**F-05: Some scenarios are orphaned; many are weakly attributed.** There are 155 TSs with no BR link (1.2%). Another 6,442 scenarios have no `target_transaction` (50.8%), and 3,416 lack `source_rule_id` (26.9%); 3,372 of the scenarios missing a source rule are also flagged. These may include valid cross-transaction or general scenarios, but the current catalog does not make that scope explicit enough to verify.

**Required action:** Link each in-scope scenario to one or more BRs, or explicitly disposition it as an approved standalone/out-of-scope scenario. Replace implicit missing attribution with a documented global scope or an explicit unresolved status and reason.

**F-06: BR assignment and review states need reconciliation.** Of 6,887 BRs, 3,817 are flagged for review (55.4%), 1,679 have `requires_review`, 1,487 are below the confidence gate, and 1,628 have unresolved segment assignment (23.6%). A further 875 use `llm_proposed` segment assignment. These are overlapping populations and should not be added together.

**Required action:** Resolve or explicitly retain these records as provisional. For LLM-proposed segment assignments, record the evidence and independent disposition. Do not treat confidence as a substitute for source evidence or approval.

**F-07: BR confidence summary does not match the records.** The catalog summary reports HIGH/MEDIUM/LOW as 3,451/2,548/888. Recounting the 6,887 BR records gives 3,531/2,492/864. The differences are +80 HIGH, -56 MEDIUM, and -24 LOW. Scenario confidence summaries do reconcile with their records.

**Required action:** Generate summary metrics from the final serialized rows and add a consistency test that fails generation when any summary disagrees with the records.

**F-08: Provenance declarations indicate substantial non-independent scenario oracles.** The scenario records identify 5,788 cases as `Level C (chatbot-sourced)` and say to validate them against ATL105 Chapter 13 before trusting them. Another 6,582 use a confidence-gated proxy for SME approval; 150 are LLM-phrased from Chapter 13 and require SME validation. These labels are useful provenance disclosures, but they are not source verification.

**Required action:** Preserve oracle provenance per scenario, link it to the exact source evidence, and route chatbot-derived, proxy-approved, and LLM-phrased claims to source review. Keep processor behavior not specified by ATL105 as an explicit external dependency rather than inventing expected outcomes.

## AI-DEV Actions and Acceptance Criteria

1. **Correct the approval state and audit.** Publish honest states such as `GENERATED`, `READY_FOR_REVIEW`, `REVIEW_REQUIRED`, `REJECTED`, and `SME_APPROVED`. The approver identity must be a human, with timestamp, scope, decision, and evidence. Automated processing may prepare a review batch but must not assert SME approval.
2. **Disposition all scenario failures and flags.** Include item ID, finding, source citation, owner, disposition, and rationale. No `FAIL` item may be represented as approved without an explicit, documented override and accountable human decision.
3. **Close the 155 missing TS→BR links.** Every remaining orphan must have a documented reason and scope disposition. Continue to verify that all referenced BR IDs resolve.
4. **Make source provenance reproducible.** Pin source specification version and document hash in the manifest; add complete canonical anchors and resolvable source citations on BR and TS artifacts.
5. **Fix attribution and BR summaries.** Resolve segment assignments or mark them provisional; provide an explicit transaction/scope disposition for unattributed scenarios; compute all summary counts from records and verify them during generation.
6. **Provide downstream artifacts before claiming coverage.** Supply linked TCs, TDs, expected outcomes, and their anchors. Then run the independent Test Solution crosswalk, chain validator, and segment payload checks. Do not use the producer's own mapping or coverage values as the independent baseline.
7. **Deliver a single-run package.** Keep JSON and Excel exports under one delivery ID, include a manifest tying each workbook to its source JSON and hash, and state clearly that the workbooks are exports rather than separate pipeline runs.

Acceptance for the next review: IDs and summaries reconcile; all TSs are linked or explicitly dispositioned; no artifact carries an unsupported approval status; source version/hash and canonical anchors resolve; all flagged/failed items have accountable decisions; and coverage remains unreported until the required independent baseline and complete artifact chain are present.

## Limitations

This review verified file integrity, workbook/JSON structure, record counts, duplicate IDs, catalog parity, producer-internal BR/TS references, approval metadata, and selected provenance/quality fields. It did not independently re-derive all 6,887 requirements from the ATL105 PDF, decide business semantics, confirm processor-specific behavior, or validate execution behavior. Those require the pinned source document, independent Test Solution rules, and appropriate SME evidence. No full Maven test suite was run for this data-only review.