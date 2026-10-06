# Detailed Assessment: 3,732 Scenarios Without a Test Case

**Assessment date:** 2026-10-06  
**Delivery:** October 5 Run1, complete handoff supplement  
**Population:** All 12,679 supplied approved-catalog scenarios checked against all 21,123 TC candidates  
**Scope:** Recounted generation omissions and producer evidence, not independent ATL105 semantic approval  
**Disposition:** `REVIEW_REQUIRED`; each scenario's independent semantic disposition remains `NOT_ASSESSED`.

## Executive Result

Every scenario without a TC has a recorded generation disposition. The 3,732 are not unexplained losses, but their recorded causes have not been independently approved as acceptable exclusions.

| Mutually exclusive category | Scenarios | Share of missing population |
|---|---:|---:|
| No consistent transaction could be resolved | 2,026 | 54.29% |
| Response-side composition deferred | 1,706 | 45.71% |
| In both categories | 0 | 0% |
| No recorded skip/defer explanation | 0 | 0% |
| Total without a TC | 3,732 | 100% |

This is 29.43% of the delivered 12,679-scenario inventory. The other 8,947 have at least one candidate TC. Neither percentage is certified ATL105 coverage.

All 3,732 missing-TC scenarios have BR links and every linked BR ID resolves. The absence of TCs is not caused by orphan scenarios or invalid BR IDs in this population. The previously reported 155 cases without BR links belong to a different population: cases that already exist.

The 27 payload-write failures also belong to a different population. All 27 TC IDs exist in the candidate catalog, and none of their scenario IDs is among these 3,732. Do not count failed TD writes as missing TC generation.

## Group A: No Consistent Transaction (2,026)

The skip file lists 2,026 scenario IDs, the unresolved registry contains the same 2,026 IDs, and none has a TC candidate. The unresolved registry explicitly records `llm_transaction_resolver_bypassed=true`; the log records the transaction resolver bypass setting.

Every skip diagnostic has an empty `discriminating_parameters` list. Every diagnostic also has an empty `unresolved_parameters` list. This means the supplied diagnostics do not identify a concrete parameter name to repair for these cases; it does not prove that the source supplies no valid context. In the original scenario catalog, 2,006 have no `target_transaction`; the remaining 20 have a target claim but still did not resolve to a composable transaction.

### Scenario types

| Type | Count |
|---|---:|
| Business rule | 1,118 |
| Supplemental entity | 497 |
| Relationship | 296 |
| Field constraint | 78 |
| Message field constraint | 17 |
| Narrative rule | 17 |
| Negative | 3 |
| Total | 2,026 |

### Source and quality signals

| Signal | Scenarios |
|---|---:|
| Missing source rule ID | 271 |
| Missing source page | 3 |
| `LLM_PROPOSED_SEGMENT_ATTRIBUTION` flag | 751 |
| `UNRESOLVED_ENTITY_REF` flag | 439 |
| `UNRESOLVED_FIELD_REF` flag | 272 |
| `UNRESOLVED_KB_GAP` flag | 271 |
| `BELOW_CONFIDENCE_GATE` flag | 646 |
| Producer judge not applied / no declared verdict | 2,023 |
| Producer verdict `FAIL` | 3 |
| Low / medium / high confidence | 824 / 935 / 267 |
| No expected response code | 2,026 |

Flag populations overlap and cannot be added. Producer flags/verdicts are review signals, not independent Test Solution invalidity decisions.

### Concrete examples

- `SC-10328`: relationship between Elements 52 and 51; page 382 and producer rule `REL-ENT-ELEM-52-ENT-ELEM-51`. The dependency statement needs message/transaction context before a request can be composed.
- `SC-10362`: dependency of Element 97 on Table Load Response; page 407. Although the wording mentions a response, the composer recorded this as unresolved, not in the explicit response-deferred list. Preserve that recorded classification and verify actual applicability before reassignment.
- `SC-5340`: orphan completion without prior authorization.
- `SC-5341`: orphan void without prior authorization/completion.
- `SC-5342`: orphan timeout reversal without the original financial transaction.

The last three are producer `FAIL` cases with `NEEDS_AUTHORIZER_DOC` and traceback mismatch flags. Their own oracle text says processor outcomes are not asserted by ATL105. Do not invent rejection codes or promote them into source-confirmed negative tests merely to reduce the skip count.

### Recommended action

1. AI-DEV should trace scenario -> BR -> source proposition -> message family and applicability; retain the supporting source quote and version.
2. Resolve entity/field/segment attribution and discriminate message/transaction context where source evidence supports it. An LLM resolver can propose candidates, but enabling it is not itself proof of correct applicability.
3. Decide whether each source proposition needs a transaction payload, a static catalog/relationship check, an external behavioral oracle, or continued review. Do not force every rule into an arbitrary financial transaction.
4. Rerun the composer for resolved cases, preserving IDs and negative intent. Compare the new skip register and candidate hash to this snapshot.
5. Keep the three explicit authorizer-dependent cases gated until authoritative external behavior evidence is supplied.

## Group B: Response-Side Deferred (1,706)

These scenarios occur in the explicit `deferredResponseSide` records and have no candidate TC. They are not in the no-consistent-transaction list. The missing population spans 3,458 scenario/response-family pairs; pairs must not be treated as additional distinct scenarios or as a mandatory one-TC-per-pair rule without a defined scope.

| Response family | Deferred pairs for scenarios with no TC |
|---|---:|
| Financial Transaction Response | 883 |
| EMV Financial Transaction Response | 828 |
| Totals Response | 502 |
| Table Load Response | 381 |
| Date & Time Load Response | 354 |
| Proprietary Data Load Response | 208 |
| Phone Load Response | 151 |
| Software Load Response | 151 |
| Total pairs | 3,458 |

The producer records direction using `data_section_1_host_sourced` for 1,919 pairs and `every_segment_one_side` for 1,539 pairs. These are producer classifier bases; this assessment did not independently verify every direction decision against ATL105.

### Scenario types

| Type | Count |
|---|---:|
| Business rule | 403 |
| Relationship | 345 |
| Negative | 263 |
| Positive | 218 |
| Table field constraint | 136 |
| Field constraint | 105 |
| Supplemental entity | 103 |
| Message field constraint | 74 |
| Narrative rule | 31 |
| Boundary | 24 |
| Field fixed value | 4 |
| Total | 1,706 |

### Source and quality signals

| Signal | Scenarios |
|---|---:|
| Missing source page | 505 |
| Missing source rule ID | 218 |
| No `target_transaction` in original catalog | 1,100 |
| `TRACEBACK_MISMATCH` flag | 249 |
| Producer verdict `FAIL` | 246 |
| Producer verdict `PASS` | 233 |
| Producer verdict `NEEDS_REVIEW` | 2 |
| No declared producer verdict | 1,225 |
| Low / medium / high confidence | 396 / 542 / 768 |
| Missing expected response code | 1,443 |

Response deferral is a declared composition limitation, not a semantic acceptance decision. High confidence or a producer `PASS` does not establish response validity. Likewise, absent target metadata in the original catalog is not proof that the later response-direction classification is correct or incorrect.

`SC-0611` is a representative positive response scenario claiming Segment Length `(No. 123)`, Segment Type `100`, and Signature Required `F`. Its source page/rule are missing, and its producer verdict is `FAIL`. Do not generate a passing response fixture for that wording before verifying and correcting the source interpretation. Similar named variants `SC-0612` through `SC-0615` have the same review concern.

### Recommended action

1. AI-DEV must declare whether the deliverable is request-only or request-plus-response. An explicit request-only contract explains response deferral but does not confer full-scenario coverage or silently shrink the original baseline.
2. If response validation is in scope, add source-grounded response templates, field/layout checks, response TC objectives, and explicit expected outcomes. Separate message-format assertions from host/business outcomes requiring external rules.
3. Triage the 246 producer `FAIL` records and the 505 missing-page / 218 missing-rule records before bulk response generation. Populations overlap; use the row-level register for work assignments.
4. Retain an explicit deferred disposition for unresolved cases. Do not fabricate server responses, derive expected outcomes from generated input alone, or label deferrals as completed tests.

## Partial Response Gaps Outside the 3,732

The overall skip file contains 4,120 deferred response pairs across 2,286 distinct scenarios, independently recounted here. Only 1,706 of those scenarios have no TC; **580 already have at least one TC but still have deferred response variants**. Those 580 account for the remaining 662 pairs. They belong in a separate partial-composition backlog, not the no-TC register. Having one request TC must not hide an ungenerated in-scope response variant.

## BR Impact and Acceptance Criteria

The missing scenarios reference 3,298 distinct BRs. Of these, 3,228 have no direct reference from any TC candidate; 70 have direct TC references elsewhere. Those 70 BRs cannot be labeled completely untested solely from this scenario list, but their existing cases do not prove that the missing scenarios are redundant or their required behavior is covered.

This is producer-internal traceability, not an independently confirmed AI/Test crosswalk. Absence of TC generation does not establish that the underlying BR is invalid, AI-only, or absent from the Test Solution baseline.

Closure requires every currently missing scenario to receive a hash-bound disposition supported by source/context evidence: an appropriate TC with linked TD and expected behavior, an explicit externally blocked state, or an accountable scope/rejection/duplicate decision. New cases must validate their intended rule; blank placeholders or arbitrary default transactions do not close the gap. Preserve the original 12,679-scenario snapshot and show any denominator changes explicitly. No reviewed decision or executable coverage is created by this assessment.

## Row-Level Evidence and Reproduction

- [Interactive HTML assessment](AI-ARTIFACT-FILTER-VIEW.html): searchable/paginated no-TC register, category/verdict/source filters, selected CSV export, detailed evidence, failed writes and aggregate quality signals. Filtering never changes acceptance status or the original denominator.
- [PDF snapshot](AI-ARTIFACT-FILTER-VIEW.pdf): summary and full 3,732-scenario / 27-failed-write appendices. It is an exhaustive static snapshot, not the current HTML filter selection.
- [Exhaustive CSV register](scenario-no-tc-register.csv): all 3,732 IDs, names/types, category, BR links, source fields, producer verdict/flags, skip diagnostics, response pairs and recommended action.
- [Exhaustive JSON register](scenario-no-tc-register.json): the same fields with arrays preserved.
- [Assessment summary](scenario-no-tc-assessment.json): input SHA-256 hashes, group breakdowns and reconciliation results.
- [Overall handoff review](DETAILED-REVIEW.md): physical-file and producer-audit assessment of the complete delivery.

From the workspace root, run the existing streaming analysis script with the archived root, original source root, and review output directory, followed by `--missing-scenarios-only`. The focused mode reads the archived scenario/BR catalogs, streams all TC records, hashes its six evidence inputs, and checks the 2,026/1,706 partition. It does not alter the source artifacts, generate TCs, or modify a semantic approval register.

The HTML/PDF are generated by `scripts/generate-ai-artifact-filter-view.py <review-directory>` using the verified JSON/CSV outputs and ReportLab. The PDF uses local Windows Segoe UI fonts. `scripts/validate-ai-artifact-filter-view.cjs <review-directory> <jsdom-module-path>` checks filter counts, pagination, detail states, CSV export and exhaustive print registers in an offline DOM. Local Edge headless/debugging is blocked by corporate policy; PDF export does not depend on it. Selection views with only aggregate evidence are explicitly labeled and must not be represented as full-delivery row-level acceptance filters.