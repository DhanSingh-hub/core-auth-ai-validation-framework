# ATL105 Business-Requirement Coverage

## 181026 Run | Presentation Brief

**Assessment date:** 2026-10-19  
**Delivery reviewed:** Immutable Run3 intake from `181026 Run`  
**Assessment class:** Corrected v4 structural inventory and traceability review; semantic approval and execution are not assessed.

---

## Executive Message

The AI delivery reports **6,070 of 8,470 candidate BRs fully traced (71.66%) within its own extracted catalog**. That is a producer-generated traceability measure, not independently validated BR coverage.

Against the independent Test Solution baseline, **353 of 601 source-derived Test BRs (58.74%) have at least one structural source-anchor candidate**. None is confirmed as semantically equivalent. After an explicit, recorded nested-layout prefix is applied, **327 of 601 (54.41%) have a complete physical-chain candidate**. This is structural candidate reach, not semantic confirmation.

The corrected v4 audit resolves **17,127 of 17,193** declared payload/sidecar references with matching Test Case and scenario identities using the explicit `qe_shaped_test_data/` prefix. Thirty payload references remain missing and 36 declarations have missing or invalid sidecars. The current tree has 33 payload files without valid adjacent sidecars, zero catalog-unreferenced payloads/metadata, and 15,562 of 15,625 Test Case occurrences with complete physical routes.

The current Run3 tree is **93 files smaller than the prior v3 inventory**: 30 payloads and 63 metadata sidecars are no longer present, no files were added, and common-file hashes are unchanged. V4 reflects the current tree; do not restore or substitute files without checking the source intake.

Across the 8,470 AI BR candidates, **4,191 distinct AI BRs have at least one structural candidate link to a Test BR**. The remaining **4,279 (50.52%) have no structural candidate link** in this crosswalk. These are candidate-link counts, not semantic-match counts.

**Semantic coverage and executed coverage remain NOT ASSESSED.** Do not present either as 0% or as a pass rate.

## Scorecard

| Measure | Result | What it means |
|---|---:|---|
| AI requirement candidates | 8,470 | Producer candidate BR records; no approved BR catalog was supplied |
| Producer-reported fully traced | 6,070 / 8,470 (71.66%) | AI matrix says the BR reaches scenario, test-case, and test-data records within its extracted universe |
| AI-matrix scenario-only BRs | 2,400 / 8,470 (28.34%) | Matrix trace status is `SCENARIO_ONLY`; no test case or test data credited by that status |
| Independent Test BR baseline | 601 | Source-derived comparison denominator |
| Structural Test-BR candidate reach | 353 / 601 (58.74%) | At least one source-anchor candidate exists; review triage only |
| Physical-chain candidate reach | 327 / 601 (54.41%) | Test BRs with a candidate and at least one complete physical chain; structural only |
| AI BRs with a structural Test-BR candidate | 4,191 / 8,470 (49.48%) | Distinct AI BR IDs appearing in at least one candidate link |
| AI BRs without a structural Test-BR candidate | 4,279 / 8,470 (50.52%) | No candidate link to any baseline Test BR was found |
| Complete physical Test Case routes | 15,562 / 15,625 | 63 Test Case occurrences remain physically incomplete |
| Confirmed semantic coverage | NOT ASSESSED | No authorized semantic decisions are included |
| Executed validation coverage | NOT ASSESSED | No execution evidence is certified by this review |

**Important denominator rule:** 71.66% and 58.74% answer different questions and must not be compared as competing estimates of the same coverage.

## What the AI Traceability Matrix Shows

The supplied matrix contains all **8,470** BR candidate IDs from the requirement catalog. It reports:

- **8,470 / 8,470** requirements with scenarios.
- **6,070 / 8,470** requirements with test cases and test data, reported as fully traced.
- **16,715** BR-to-scenario edges in both the catalog and matrix; the edge sets reconcile exactly.
- **20,812** BR-to-test-case edges in both the catalog and matrix; the edge sets reconcile exactly.
- **15,625** scenario-to-test-case edges in the catalog versus **15,481** in requirement-rooted matrix rows, a difference of **144**.
- **144 scenarios and 144 associated test cases** appear outside the matrix's requirement-rooted branches. They are not thereby proven orphaned from each other; they lack a BR-rooted matrix path.

These checks establish internal ID and edge consistency for the supplied catalogs. They do **not** establish that the extracted BR universe is complete against ATL105 source, that the BR meaning is correct, or that a test passes.

## Independent Test-BR Comparison

The independent baseline contains **601 source-derived Test BRs**. Structural anchor screening found at least one candidate for **353 rules (58.74%)**. The audit's disposition counts across the **8,470 AI BR candidates** were:

The crosswalk includes **4,191 distinct AI BR IDs** linked to one or more of those reached Test BRs, leaving **4,279 AI BR IDs (50.52%) without a structural candidate link**. The count means “no candidate link was generated by this anchor screen,” not “proven semantically unrelated.”

| Anchor disposition | AI BR candidates |
|---|---:|
| Ambiguous or composite candidate | 3,166 |
| Unique exact source-anchor candidate | 1,025 |
| Segment unresolved | 637 |
| No exact source-anchor candidate | 201 |
| No numeric element anchor | 3,441 |

These are structural screening dispositions, not semantic adjudications. In particular, a unique exact anchor means a candidate was found; it does not mean the candidate BRs are equivalent, compatible, or approved. A missing candidate is also not proof that the AI BR is absent from the Test Solution.

## Physical-Link Blocker

The current v4 Run3 inventory contains **16,466 payloads and 16,433 metadata sidecars**. Catalog references resolve through one explicit nested-layout prefix; the remaining pair failures are:

| TD path check | Result |
|---|---:|
| Declared payload/metadata path occurrences | 17,193 |
| Resolved with explicit layout prefix; sidecar identity matches | 17,127 |
| Declared payload references still missing | 30 |
| Sidecar missing/invalid references | 36 |
| Physical payloads without valid adjacent sidecars | 33 |
| Test cases with a complete physical route | 15,562 / 15,625 |

The catalog declares paths like `qe_shaped_test_data/TD-TC-….json`; the physical files are nested under `qe_shaped_test_data/qe_shaped_test_data/TD-TC-….json`. V4 resolves this known layout through the explicit `--catalog-data-prefix qe_shaped_test_data` option and records each resolution method. It does not search by basename. The remaining 30 missing payload references and 36 sidecar missing/invalid references remain open; producer presence claims are not accepted automatically.

**Recommended remediation:** reconcile missing files/sidecars against the source intake; update the catalog or delivery layout in a new immutable intake; then rerun the audit. Preserve the current Run3 and its v3/v4 snapshots.

## Delivery and Review Status

| Area | Observed status | Presentation interpretation |
|---|---|---|
| Requirement catalog | 8,470 candidates; 4,886 flagged for review | Candidate population, not approved BR baseline |
| Scenario catalog | 11,356 records marked `DEV_APPROVED`; 10,003 flagged | Producer-scripted status; approval note explicitly says no SME review |
| Candidate scenario catalog | 0 records; 11,367 skipped duplicates | No separate candidate-scenario population was delivered |
| Test-case catalog | 15,625 candidate records; 13,478 marked `COMPLETE` by producer | Producer completeness labels are not independent acceptance |
| Approved BR / approved TC catalogs | Not supplied | Cannot calculate approved-tier parity |
| Payload field values | Not exported in this structural review | No payload value correctness claim |
| SME, source interpretation, host, wire, and response-oracle evidence | Not certified | Remain open / not assessed |

The producer reports **100 newly composed** test cases, **15,355 skipped duplicates**, **1,694 skipped for no consistent transaction**, **4,752 deferred response-side**, and **0 composition failures**. These describe pipeline activity, not BR coverage or test execution.

## Recommended Presentation Talk Track

> “Run3 reports 71.66% traceability inside the AI-produced catalog. Independent screening finds anchors for 58.74% of the Test baseline and complete physical-chain candidates for 54.41%. Of 17,193 references, 17,127 pair to matching payload/sidecar identities after explicit nested-layout resolution; 30 payload references and 36 sidecar references remain unresolved. The current snapshot is 93 files smaller than v3. Semantic and execution coverage remain not assessed.”

## Decisions and Next Steps

1. **Reconcile missing physical artifacts:** resolve 30 missing payload references, 36 sidecar failures, and the 93-file v3-v4 inventory delta against the source intake; do not infer or restore files by guesswork.
2. **Resolve the 144 non-BR-rooted branches:** inspect the scenario/test-case relationships and source attribution; do not infer missing BR edges.
3. **Provide approved catalogs:** obtain approved BR and TC tiers before reporting approved-versus-candidate parity.
4. **Adjudicate semantics:** review candidate anchors against authoritative source and record authorized decisions. Keep `REVIEW_REQUIRED` candidates unconfirmed.
5. **Establish execution evidence:** define approved test data, response oracles, host environment, and run-bound results before calculating executed coverage.

## Metric Definitions

- **Producer-reported full traceability:** matrix count of BRs marked fully traced divided by the producer's 8,470 extracted BR records.
- **Structural candidate reach:** independent Test BR rules with one or more structural source-anchor candidates divided by the 601-rule Test baseline.
- **Physical-chain candidate reach:** a Test BR with an anchor candidate that reaches at least one TC whose payload and sidecar resolve and whose sidecar identity matches. Current value is 327/601 (54.41%); this is structural only.
- **Complete physical Test Case route:** a TC occurrence with an object payload, object sidecar and matching TC/scenario IDs. Current value is 15,562/15,625; 63 occurrences remain incomplete.
- **Semantic coverage:** requires authorized evidence that a BR and its linked tests express the same applicable source rule. Not assessed here.
- **Executed coverage:** requires run-bound execution evidence against an approved test chain. Not assessed here.

## Evidence and Reproducibility

Primary analysis: [Corrected v4 delivery-wide report](../2026-10-19-181026-run-delivery-wide-assessment-v4/REPORT.md)  
Structured source: [v4 summary and hashes](../2026-10-19-181026-run-delivery-wide-assessment-v4/summary.json)  
Paired-field results: [v4 paired observations](../2026-10-19-181026-run-delivery-wide-assessment-v4/paired-field-observations.jsonl.gz)  
Recursive field scan: [v4 field-data scan](../2026-10-19-181026-run-delivery-wide-assessment-v4/field-data-scan.jsonl.gz)

Key input hashes (SHA-256):

- V4 summary: `75f8631ef48b56e11a40b8dab66c4ca12c6951db1f7507ebd0ee888f2f7922d5`
- V4 report: `cd29da9c915c8583c1824b84b6680618e59a307ab4fcf4d68716174e7a2b8728`
- V4 file inventory: `5053bb1c3b047df22416ec9aca9961536b239f3c11858c3a825afe421c4d7a6c`
- V4 paired-field observations: `ab6f102865970804275026eae787b97179c9f1777006cb4f975b00a68a9e247c`
- Independent 601-rule Test BR baseline: `371499ffb82c545724d28e32f4b688f6124ccb9c746b99d0ef47e06d56dd1d16`
- Source text: `6d03bf09f25b9126975b584aef83c21d1b7b081cabccfb9af3b89d46a26d06aa`

V3-to-v4 snapshot comparison: 93 paths removed (30 payload, 63 metadata), zero added, and zero changed hashes among common paths. V4 validates only files present in the current Run3 tree.

**Use limitation:** This brief is a structural coverage presentation, not an SME sign-off, semantic coverage certificate, test-execution report, or host certification.
