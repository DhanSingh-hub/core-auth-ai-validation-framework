# Security AI-to-Test BR Crossmatch and Chain Review

This review covers the full 387 AI Security BR and 23 Test Security BR populations. Existing source-anchor candidates, ranked cross-category leads, physical AI chains, Test Solution primary and alternative chains, and offline Test-BR predicate results are kept distinct. All relation classifications are preliminary; no SME decision is recorded.

| Measure | Count |
|---|---:|
| aiSecurityBrs | 387 |
| testSecurityBrs | 23 |
| completeChainAiSecurityBrsWithoutV3Candidate | 247 |
| testSecurityBrsWithoutV3AiCandidate | 8 |
| existingCandidatePairsTouchingSecurity | 73 |
| sameCategorySecurityPairs | 10 |
| rankedAiSecurityLeads | 3935 |
| rankedAiToTestSecurityLeads | 1093 |
| aiSecurityWithCaseChains | 256 |
| aiSecurityWithPhysicalCompleteCases | 387 |
| testSecurityRulesInRunnerBaseline | 22 |
| testSecurityRulesWithDecisivePredicateResults | 0 |
| semanticFinalDecisions | 0 |
| testFixtureExecutions | 0 |
| hostExecutions | 0 |

## Direct Security-to-Security Candidates

| AI BR | Test BR | Preliminary relation |
|---|---|---|
| REQ-SRC-ATL105-PDF-001:389 | SEG100-R-043 | PARTIAL_FIELD_AND_CONTEXT_OVERLAP |
| REQ-SRC-ATL105-PDF-001:1701 | SEG100-R-035 | RELATED_DIFFERENT_OBLIGATION |
| REQ-SRC-ATL105-PDF-001:1935 | SEG116-R-003 | PARTIAL_SEGMENT_IDENTITY_OVERLAP |
| REQ-SRC-ATL105-PDF-001:2343 | SEG123-R-010 | PARTIAL_FIELD_PRESENCE_AND_FORMAT_OVERLAP |
| REQ-SRC-ATL105-PDF-001:2343 | SEG123-R-011 | COMPOSITE_CONTEXT_OVERLAP |
| REQ-SRC-ATL105-PDF-001:2346 | SEG123-R-012 | PARTIAL_COMPOSITE_WIDTH_OVERLAP |
| REQ-SRC-ATL105-PDF-001:2592 | SEG123-R-010 | PARTIAL_FIELD_AND_FORMAT_OVERLAP |
| REQ-SRC-ATL105-PDF-001:2592 | SEG123-R-011 | COMPOSITE_CONTEXT_OVERLAP |
| REQ-SRC-ATL105-PDF-001:3131 | SEG123-R-010 | PARTIAL_FIELD_AND_FORMAT_OVERLAP |
| REQ-SRC-ATL105-PDF-001:3131 | SEG123-R-011 | COMPOSITE_CONTEXT_OVERLAP |

## Validation and Limits

The complete-chain/no-candidate list contains 247 AI Security BRs; the v3 crosswalk has 8 Test Security BRs with no AI candidate. These are candidate gaps, not authorized NOT_MATCH decisions. Source-anchor candidates are not equivalence. Ranked leads use retrieval prioritization only.

The offline field/structure runner has rows for 22 of 23 Security Test BRs, with 0 having bounded decisive results on AI payloads. These results validate only the bound predicate and are not Test fixture executions or whole-case outcomes.

Test chains and exact-anchor alternatives retain their review statuses. Draft expected outcomes and fixture payloads are not run as an approved Test harness. Semantic approval, wire outcomes and host execution remain NOT_ASSESSED/NOT_EXECUTED.
