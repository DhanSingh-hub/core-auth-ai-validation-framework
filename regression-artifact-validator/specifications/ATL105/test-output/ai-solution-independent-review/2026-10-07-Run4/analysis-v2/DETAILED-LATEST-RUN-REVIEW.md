# October 7 Run4 | Independent Extensive Review

Disposition: REVIEW_REQUIRED. This is graph, physical-file, metadata and producer-claim reconciliation; not semantic coverage or host certification.

## Verified Intake

129,764 files / 3,158,902,761 bytes; all source/archive relative-path SHA-256 hashes agree. Original Downloads source and prior runs remain unchanged.

## Inventory and graph

| Measure | Latest verified count |
|---|---:|
| AI BRs | 5,153 |
| AI TSs | 14,297 |
| AI TC candidates | 21,531 |
| AI BRs directly linked to candidate TCs (internal graph) | 3,719 |
| TSs with TC | 12,860 |
| TSs without TC | 1,437 |
| Unique physical request filenames | 21,621 |
| Physical request copies across candidate/reporting locations | 23,271 |
| Logical TCs with verified physical metadata | 21,507 |
| Missing declared TD files | 24 |
| Empty request bodies (physical copies) | 764 |

## Independent Test Solution BR coverage

Formula: confirmed common Test Solution BRs / independent Test Solution BRs. Run4 has 0/601 confirmed common matches recorded (0.00% confirmed evidence); status: NOT_ASSESSED. A Run4-specific source-anchor candidate crosswalk now covers all 5,153 AI BRs; its candidates are not semantic confirmations. All 601 Test Solution BRs remain NOT_ASSESSED pending review. This is not evidence that no equivalent AI BRs exist.

The 6,473-entry decision register is tied to September Run2 and contains 6,473 pending decisions; it is not applied to Run4. The old crosswalk cannot transfer by reused BR ID.

Separate AI-internal diagnostic: 3,719/5,153 AI BRs directly linked to candidate TCs (72.17%). The producer matrix shows 3,720/5,153 (72.19%) with 62 proposed, unconfirmed relink edges. These are not Test Solution BR coverage.

[Independent Test Solution BR inventory](../../../test-solution-independent-review/source-derived-requirement-inventory.json) | [Run4 coverage evidence](test-solution-br-coverage.json)

## AI-internal BR-to-TC linkage (not Test Solution coverage)

Direct structural linkage: 3,719/5,153 AI BRs (72.17%). The matrix claims 3,720/5,153 (72.19%), including 62 proposed source-rule relink edges that are not confirmed direct TC links. The matrix has one additional unique BR ID (REQ-SRC-ATL105-PDF-001:748); validate before accepting it. These are structural rates only: semantic coverage remains NOT_CALCULABLE.

| Measure | AI BRs | Rate | Status |
|---|---:|---:|---|
| Direct TC-linked BRs | 3,719/5,153 | 72.17% | Recounted internal links |
| Matrix-linked BRs | 3,720/5,153 | 72.19% | Includes proposed links; review required |

## Multi-leg package audit

ZIP: 406 members; manifest: 89 logical flow cases / 406 files. CRC failure: None; unsafe paths: 0; missing candidate members: 0; content differences: 0. This verifies package/file integrity, not lifecycle behavior or host outcomes.

## October 5 comparison

| Metric | October 5 | October 7 Run4 | Delta |
|---|---:|---:|---:|
| BR inventory | 6,887 | 5,153 | -1,734 |
| TS inventory | 12,679 | 14,297 | +1,618 |
| TC candidates | 21,123 | 21,531 | +408 |
| Scenarios without TC | 3,732 | 1,437 | -2,295 |
| Cases with empty expected response | 21,123 | 0 | -21,123 |
| Logical cases with physical outputs | 21,096 | 21,507 | +411 |

## Expected-response evidence

Counts overlap across tiers within a TC; presence of a tier is not a qualified outcome oracle.

| Tier | Distinct cases | Nodes |
|---|---:|---:|
| INFER | 21,531 | 195,182 |
| GROUNDED_SECONDARY | 4,683 | 4,683 |
| ASSERT | 8,637 | 37,497 |
| FLAG | 18,277 | 28,451 |

## Payload/metadata agreement scope

Direct scalar correspondence was audited separately from the existing Java adapter. Matching metadata values to actual JSON values does not validate source business rules, field presence, wire bytes or expected host outcomes. Composite values and fields without direct physical paths remain unassessed.

```json
{
  "DIRECT_VALUE_AGREEMENT": 537403,
  "COMPOSITE_VALUE_UNASSESSED": 18739,
  "NO_DIRECT_PHYSICAL_PATH_UNASSESSED": 35868
}
```

Disagreements: 0; replica content conflicts: 0; audited JSON errors: 0.

## Full matrix audit

The complete JSON contains 5,153 requirement records and 65,989 flat leaves. Producer test_cases_with_data_file=64,528 is a leaf-occurrence count, not a unique TC count. The direct and proposed attribution graphs remain distinct.

```json
{
  "rowsWithTestCase": 64552,
  "rowsDeclaringTd": 64528,
  "fullyTracedRowsWithoutTdDeclaration": 28,
  "matrixBrEdgeNotDirectInCase": 62,
  "brsLinkedByMatrixRows": 3720,
  "uniqueCasesDeclaringDataPath": 21376,
  "matrixOnlyLinkedBrIds": [
    "REQ-SRC-ATL105-PDF-001:748"
  ],
  "proposedRelinkUniqueCases": 62,
  "matrixMissingDataUniqueCases": 0
}
```

Changes in BR count are not missing-rule conclusions: derive by source/business identity, not local ID. Reused IDs changed extensively.

## Detailed Findings and SME Questions

### SME-RUN4-TD_EMPTY | 764 empty request bodies counted across physical copies

This is a candidate/reporting copy count, not 764 distinct TCs. A present file is not a populated executable request; TC-000001 is an Auth Completion (0220) empty object.

Required action: Resolve message-family/template grounding or classify blocked; do not call empty bodies real executable data.

Status: OPEN_REVIEW_REQUIRED; approval not granted.

### SME-RUN4-TD_MISSING | 24 declared request-data files are missing

Catalog declares physical files not found; complete affected-case list is exported.

Required action: Producer supplies corrected payload/metadata pairs with declared case links.

Status: OPEN_REVIEW_REQUIRED; approval not granted.

### SME-RUN4-ADAPTER_SCOPE | Existing independent Java adapter cannot map current payload roots

21,621 metadata records were read; element observations and field mappings are empty. All records remain REVIEW_REQUIRED.

Required action: Review a run-versioned root-family crosswalk and exact field identities before rerunning value rules; no implicit alias promotion.

Status: OPEN_REVIEW_REQUIRED; approval not granted.

### SME-RUN4-APPROVAL_PROVENANCE | 1,650 producer approvals are automated-batch-nonSME

Approval audit count/reviewer identity verified; source report also cautions that approval is demo/sample rather than real SME approval.

Required action: Do not claim SME-approved or execution-ready suite; retain reviewer provenance and review required status.

Status: OPEN_REVIEW_REQUIRED; approval not granted.

### SME-RUN4-ID_DRIFT | Reused IDs contain materially changed content

5,141 of 5,153 reused BR statements changed; all 21,123 reused TC fingerprints changed compared with October 5.

Required action: Do not migrate older matches by ID; remap by version/source/context and business meaning.

Status: OPEN_REVIEW_REQUIRED; approval not granted.

### SME-RUN4-AUDIT_ZERO | TC audit reports zero cases

The actual catalog contains 21,531 TCs; the TC audit summary is not a reliable coverage gate.

Required action: Regenerate the audit from the final case catalog and pin hashes/timestamps.

Status: OPEN_REVIEW_REQUIRED; approval not granted.

### SME-RUN4-ASSERT_COUNTS | ASSERT figures use conflicting or unexplained counting units

Run report says 516; TD audit says 3,476; 8,637 cases contain ASSERT-tier nodes, with 37,497 ASSERT nodes and 6,710 cases carrying explicit assertion/value evidence under the analyzer's disclosed criterion.

Required action: Separate node counts, case counts and grounded/qualified assertions; do not sum overlapping tiers or treat ASSERT labels as certified oracles.

Status: OPEN_REVIEW_REQUIRED; approval not granted.

### SME-RUN4-MATRIX_RELINK | Matrix proposes 62 BR edges absent from direct TC links

Full JSON has 65,989 leaves; 3,720 matrix-linked BRs versus 3,719 direct-linked BRs. Matrix-only BR is :748; relinks remain producer candidates.

Required action: Review each source-rule relink against underlying BR/scenario/context; preserve direct graph and proposed graph separately.

Status: OPEN_REVIEW_REQUIRED; approval not granted.

### SME-RUN4-MATRIX_LABEL | 28 FULLY_TRACED leaves have no TD declaration

Status may be propagated from a BR with another complete chain; individual leaves are not proven complete.

Required action: Emit leaf-level and BR-level trace states separately; no automatic semantic or readiness pass.

Status: OPEN_REVIEW_REQUIRED; approval not granted.

### SME-RUN4-SCENARIO_BACKLOG | 1,437 scenarios have no TC

Generation backlog improves from 3,732 but remains in the denominator; no approved exclusion is established.

Required action: Use exported backlog for grounded regeneration or explicit reviewed scope decisions.

Status: OPEN_REVIEW_REQUIRED; approval not granted.

### SME-RUN4-UNMAPPED_BR | 193 cases have no direct BR attribution

Matrix reports 131 orphan scenarios and documents 62 source-rule relinks; this is a proposed attribution distinction, not confirmed semantic matching.

Required action: Validate source/context justification for proposed edges and disclose residual unattributed artifacts.

Status: OPEN_REVIEW_REQUIRED; approval not granted.

### SME-RUN4-SEMANTIC_HOST | Business equivalence and host readiness remain unqualified

Expected-response structures now exist but contain INFER/GROUNDED_SECONDARY/FLAG claims; Judge bridge caveat, partial templates and root-mapping limitations remain.

Required action: Independently qualify rule interpretations, assertions, applicable data/wire and authorized host/state/response evidence; record SME questions without granting approval.

Status: OPEN_REVIEW_REQUIRED; approval not granted.

## Assurance boundaries

- Structural linkage percentages do not measure independent semantic coverage.
- Physical copy counts include reporting duplicates; unique request filenames include flow legs, not one-to-one TC artifacts.
- Expected response tiers overlap within a case. ASSERT presence is not proof of a source-backed outcome oracle.
- A negative invalid value is not automatically a defective test; valid controls and isolated intended-rule detection are required.
- Raw R/O/C template labels are not adjudicated mandatory/conditional/optional business logic.
- The existing Java report records unsupported input mappings, not successful field checks.
- Confirmed semantic coverage remains NOT_CALCULABLE; execution certification false.

[Filterable HTML](LATEST-RUN-INDEPENDENT-REVIEW.html) | [Case CSV](case-register.csv) | [Physical CSV](physical-file-register.csv) | [SME queries](latest-run-findings-and-sme-queries.csv)

## Complete Run4 AI BR Chain and Test Solution Candidate Crosswalk

All 5,153 AI BRs were included without an approval filter. The full matrix contains 65,989 flat leaves: 64,552 with TC, 64,528 with a TD declaration, and 28 producer-labeled FULLY_TRACED without a TD declaration. Matrix-to-catalog validation found 62 BR-to-TS rows not present as direct scenario requirement links and 0 TS-to-TC scenario disagreements.
Composed from direct AI catalogs, 5,153 BRs link to TS; 3,719 reach a TC through those TSs; 3,719 reach a physically present candidate TD. TD metadata joins are reported separately per BR in the complete export.

| Test Solution source-anchor candidate disposition | AI BR count |
|---|---:|
| NOT_MATCHABLE_NO_NUMERIC_ELEMENT_ANCHOR | 2,883 |
| NOT_MATCHABLE_SEGMENT_UNRESOLVED | 1,500 |
| NO_EXACT_SOURCE_ANCHOR_CANDIDATE_REVIEW_REQUIRED | 208 |
| REVIEW_REQUIRED_AMBIGUOUS_OR_COMPOSITE_CANDIDATE | 277 |
| REVIEW_REQUIRED_EXACT_SOURCE_ANCHOR_CANDIDATE | 285 |

562 AI BRs have source-anchor candidates covering 270 distinct Test Solution BRs. All candidates remain REVIEW_REQUIRED; no title or text-similarity matching is used. `NO_EXACT_SOURCE_ANCHOR_CANDIDATE_REVIEW_REQUIRED`, unresolved segment, and unsupported element statuses are not `AI_ONLY` findings.

- [Complete one-row-per-AI-BR CSV](run4-independent-br-crosswalk-v2/run4-ai-br-chain-crosswalk.csv)
- [Structured one-row-per-AI-BR JSONL](run4-independent-br-crosswalk-v2/run4-ai-br-chain-crosswalk.jsonl)
- [Crosswalk and chain summary JSON](run4-independent-br-crosswalk-v2/run4-ai-br-chain-crosswalk-summary.json)

## Semantic confirmation workflow

The source-backed review packet contains 1,950 AI/Test BR candidate pairs across 270 independent Test BRs; 331 Test BRs require manual search across all 5,153 AI BRs. The separate pair decision register contains 1,950 PENDING events and no reviewer decisions. No coverage credit is issued.

Example review evidence (not a decision): AI `REQ-SRC-ATL105-PDF-001:004` states Address Line 2 is alphanumeric and at most 21 bytes. Candidate Test BR `SEGDL1-R-010` and the cited Section 13.2 source quote specify the 21-position city/space/state/space/ZIP layout. The reviewer must decide whether the AI statement is equivalent, partial, or otherwise; the shared element anchor is insufficient by itself.

- [Reviewer instructions and Java recorder commands](run4-semantic-review-v2/README.md)
- [One row per independent Test BR](run4-semantic-review-v2/run4-test-br-semantic-review.csv)
- [Source-quoted AI/Test BR candidate pairs](run4-semantic-review-v2/run4-ai-test-br-semantic-review-pairs.jsonl)
- [Run4-only pending decision register](run4-semantic-review-v2/run4-semantic-decision-register.json)
- [Review packet summary](run4-semantic-review-v2/run4-semantic-review-summary.json)

## Test Team Interpretation - Element 4

Both BRs are correct and semantically aligned. The AI BR states the alphanumeric character type and 21-byte maximum; the Test BR adds the city/state/ZIP positional layout, which is compatible specificity, not a contradiction.

Element 4 is Address Line 2, character type AN, fixed width 21 bytes, carrying city/state/ZIP content. The ATL105 Section 13.2 positional detail is compatible specificity, not a contradiction. Formal pair decision remains PENDING until an authorized reviewer records it; this interpretation alone issues no coverage credit.

[Dated interpretation JSON](test-team-interpretation-2026-10-08.json)

## Section 10.1 Independent Test Solution Draft Chains

The independent Test Solution source catalog has one BR with a primary source anchor inside Section 10.1; its existing complete-chain package also has 10 unique direct Section 10.1 rule anchors plus 3 related partial-approval chains. The additive review supplement reuses those references and adds 46 source-quoted draft BRs, each linked to one TS, TC, and TD design file.
New draft chains: 46 BR / 46 TS / 46 TC / 46 TD design records. All remain REVIEW_REQUIRED; TDs are non-converter-ready placeholders; official 601-rule baseline unchanged; coverage credit 0; execution certification false.

- [Section 10.1 draft chain package](../../../test-solution-independent-review/section-10-1-credit-card-processing-review-v3/section-10-1-br-ts-tc-td-draft-package.json)
- [Topic-level coverage assessment and reused rules](../../../test-solution-independent-review/section-10-1-credit-card-processing-review-v3/section-10-1-coverage-assessment.json)
- [Review instructions](../../../test-solution-independent-review/section-10-1-credit-card-processing-review-v3/README.md)