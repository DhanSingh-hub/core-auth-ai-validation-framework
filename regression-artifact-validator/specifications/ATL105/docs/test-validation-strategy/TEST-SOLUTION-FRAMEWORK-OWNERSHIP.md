# Test Solution Framework Ownership

## Boundaries

| Area | Owner | Input | Output / decision |
|---|---|---|---|
| Source knowledge | `docs/specs/kb/segment-*/coverage/*-rule-catalog.json` | ATL105 source evidence | Rule candidates, not approvals |
| Training governance | `training-status.json` and `GenerateAtl105TrainingReport` | Segment catalogs and reviewed gates | Reconciled training report; no inferred certification |
| Source-backed assertions | `contract/element-83-source-assertions.json` and `GenerateAtl105SourceBackedRuleGate` | Versioned Chapter 13 source, BR coverage and approval matrix | Exact quoted evidence, source fingerprint, rule-level review queue and non-executable draft examples |
| Segment-length width | `contract/element-84-width-assertions.json` and `GenerateAtl105Element84WidthGate` | Element 84 exception lists and segment layout rows | Partial width-only claims; no calculated-length or execution certification |
| Address Line 2 positions | `contract/element-4-layout-assertions.json` and `GenerateAtl105Element4LayoutGate` | Bounded Chapter 13 Element 4 shape and five positional rows | Partial layout-only claim; external city/state/ZIP validity and DL1 parsing remain open |
| Fixed segment type | `GenerateAtl105FixedSegmentTypeGate` | Element 85 catalog titles and the exact Section 12 field-1 fixed value | Partial type-only claims; conflicting or absent values stay in review |
| Check Data Segment field rows | `contract/segment-110-field-row-assertions.json` and `GenerateAtl105Segment110FieldEvidence` | Unique §12.9 body rows, existing rule IDs and approval matrix | Six bounded, partial row claims; no draft cases or conditional-trigger inference |
| Totals Prompt Code rows | `contract/totals-prompt-code-assertions.json` and `GenerateAtl105TotalsPromptCodeEvidence` | Section 12.6 and 12.17 Field 5 rows and existing BRs | Two fixed-value-only claims; no Totals workflow approval |
| Source-evidence progress | `GenerateAtl105SourceEvidenceProgress` | Six current source gates, catalog, and BR approval matrix | Reconciled distinct-rule progress; no semantic certification |
| Authored evidence | `test-output/test-json/` and `GenerateAllTestSolutionBrTsTcTdPackage` | Independent Test Solution packages | Aggregate evidence; conflicting canonical IDs fail closed |
| Structural completion | `GenerateCompleteCanonicalTestSolutionPackage` | Aggregate evidence and rule catalogs | One BR per rule ID plus explicit missing-chain placeholders |
| Chain integrity | `CanonicalTraceabilityValidator` | Canonical artifacts and source anchors | Anchor continuity and contract errors |
| Aggregate review | `ValidateAllTestSolutionBrTsTcTdAggregate` | Aggregate evidence | Link gaps, duplicate IDs; never execution certification by itself |
| Pre-SME AI validation | `ValidateRun2TrainedSegments`, `Run2PayloadBatchValidator`, and `Run2SegmentValidationEvidenceWriter` | Immutable Run2 payloads, independent catalogs and crosswalks | Technical checks and bounded review evidence; never SME approval or execution certification |
| Element review | `GenerateAtl105ElementChainCoverage` | Complete package and element reference | Per-element structural and non-placeholder chain measures |
| Limitations | `GenerateAtl105RemainingLimitationsStatus` | Published review matrices | Summary of unresolved semantic and execution gates |

The aggregate, complete package, element reference, and matrices are different evidence stages, not interchangeable copies. Do not delete a generated stage while downstream consumers still require it. AI Solution artifacts are evidence under test, never the source of independent rules.

## Refresh Order

From the validator module, after changing source catalogs or canonical Test Solution inputs:

```powershell
mvn -q compile exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GenerateAtl105TrainingReport'
mvn -q compile exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GenerateKnowledgeBaseBrCoveragePackage'
mvn -q compile exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GenerateAtl105BrApprovalMatrix'
mvn -q compile exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GenerateAtl105SourceBackedRuleGate'
mvn -q compile exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GenerateAtl105Element84WidthGate'
mvn -q compile exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GenerateAtl105Element4LayoutGate'
mvn -q compile exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GenerateAtl105FixedSegmentTypeGate'
mvn -q compile exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GenerateAtl105Segment110FieldEvidence'
mvn -q compile exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GenerateAtl105TotalsPromptCodeEvidence'
mvn -q compile exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GenerateAtl105SourceEvidenceProgress'
mvn -q compile exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GenerateAllTestSolutionBrTsTcTdPackage'
mvn -q compile exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GenerateCompleteCanonicalTestSolutionPackage'
mvn -q compile exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GenerateAtl105ElementChainCoverage'
mvn -q compile exec:java '-Dexec.mainClass=com.coreauth.validator.coverage.GenerateAtl105RemainingLimitationsStatus'
mvn test
```

The last two reports also depend on the separately generated all-element reference, transaction, omission, BR-approval and dependency matrices. Refresh those producers before publishing a full limitations snapshot if their inputs change. Tests for chain and limitations reporting use temporary packs; publication belongs to explicit commands.

The source-backed gate starts with curated Element 83 assertions only. Each assertion must cite a literal Chapter 13 phrase and retain a source-version fingerprint. A matching quote establishes a checkable source claim, not SME semantic approval. Draft positive contextual codes and invalid-length cases are not inserted into the canonical TS/TC/TD package. Rules without curated assertions and mismatched source text remain review-required.

## Current Decision Boundary

The current catalog contains 601 rules across 49 segments. The canonical package has one rule-derived BR per catalog rule; 539 rule-element links have generated structural chains. Every one of those 539 links remains placeholder/review evidence, not an executable chain. No segment is certified for intake; SME semantic approvals, unresolved source questions, approved payloads, and independent AI-artifact intake remain separate gates. Segment 116 still needs the external TransArmor specification.

The source-backed gate currently resolves 600 rule citations. It locates every cited body token for 553 rules, some tokens for 14 rules and none for 34 rules; these are reference-only navigation results. It matches 21 curated assertions touching 8 Element 83 rules, including 2 partial cross-section claims, and creates 42 review-only draft examples. The other 593 rules are not silently promoted by citation resolution or body location. None of the cross-section cases proves the full Block Number or Host Discount workflow. SME-approved rule count remains 0.

The generated `test-output/test-solution-independent-review/atl105-source-backed-rule-review-queue.csv` lists all 601 rules with their citation token, body-location status, source lines, assertion state and review counts. Use it to prioritize the 34 fully unlocated and 14 partially located references before curating further rule claims. A located heading is not a verified rule statement; the remaining 593 rules require independently checked literal assertions and appropriate context before draft cases can be derived.

The Element 84 width report covers 13 existing rules (101, 104, 108, 109, 113, 114, 115, 118, 130, 131, 132, 135, 136) with 26 width-only draft examples. Segment 135's width evidence does not settle its open length-counting question. The fixed-type gate reviews 24 exact-title candidates: 22 have matching field-1 evidence and two (`SEG114-R-003`, `SEG151-R-004`) remain source mismatches. Six Segment 110 row/maximum assertions match §12.9; both existing Totals Prompt Code `990` rules match their own field rows. The Element 4 gate adds one layout-only rule with two structural examples; it does not establish external city, state, or ZIP validity. The reconciled ledger has 52 distinct rules with at least one partial source-backed claim and 549 without any such claim, plus 114 review-only examples. Its 49-segment backlog table reconciles to all 601 rules; the two fixed-type source mismatches remain review-required. None of those 52 is thereby a fully proven BR, SME-approved, or executable.

For pre-SME AI output, Run2 segment evidence now distinguishes technical success from approval: even if all six assessment strategies pass, its decision is `PRE_SME_TECHNICAL_CHECKS_PASSED_REVIEW_REQUIRED` and `executionCertified` stays false. Missing, unrelated, invalid or unaccounted physical payloads block payload compliance. These checks can reject defective AI output before SME review but cannot certify semantic equivalence or replace an approver's decision.

Further consolidation of report formats should follow consumer migration and provenance checks. In particular, the limitations summary reads on-disk matrices and does not currently prove that all of them were regenerated from one snapshot. Until that freshness gate exists, regenerate in dependency order and keep publication review-required.