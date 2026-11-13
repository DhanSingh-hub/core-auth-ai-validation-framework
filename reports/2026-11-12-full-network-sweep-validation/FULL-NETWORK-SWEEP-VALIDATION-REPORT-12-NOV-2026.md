# Full BUYPASS ATL105 network-sweep validation - 12 November 2026

**Prepared:** 12 November 2026
**Audience:** AI generation team, Test Model team, BA/SME and QA execution team
**Delivery under review:** `C:\Users\F5QGZOJ\Downloads\builds\builds` (104 build directories)
**Baseline:** ATL105 2026-4, transaction catalog `2026-10-30.1`
**Build timestamp:** 2026-10-31, 10:06-11:10 UTC (single ~64-minute batch)
**Delivery state:** All 104 directories report `state: CANDIDATE`, `mapping_status: candidate` - nothing in this delivery has been promoted/approved.
**Scope of this review:** independent structural, BR, coverage and end-to-end artefact validation of the delivered files as received. No change was made to any file under `Downloads\builds\builds`.

## 1. Executive summary

The delivery contains **104 build directories**, which resolve to **99 unique BUYPASS network x program packages** plus **5 duplicate "priority" extracts** (Amex Credit, Interlink Debit, Maestro Direct Debit, Mastercard Credit, Mastercard Fleet) that carry rendered test data on top of their identical full-sweep counterpart.

Cross-checking test-case IDs (e.g. `TC-001722`, `TC-001748`) confirms the **5 priority packages are the same build content already reviewed in the [31-Oct-2026 report](../2026-10-31-ai-team-delivery-defects/AI-TEAM-DETAILED-ISSUE-REPORT-31-OCT-2026.md)**; those 8 previously-recorded issues (stale traceability references, Element 188 rendering width, obsolete archive, reused TC identities, etc.) still apply and are **not re-verified here**. The net-new content in this delivery is the **other 94 packages** - the first full sweep of every BUYPASS network/program combination for ATL105 2026-4 - which had no prior BR catalog, coverage report, or defect review anywhere in this workspace.

Headline results across all **99 unique packages (22,153 test cases, 3,746 scenarios)**:

| Dimension | Result |
|---|---|
| Manifest-to-file count reconciliation | **0 mismatches** across all 99 packages (jsonl lines, CSV rows, scenario rows, and every `by_status`/`verification` bucket all agree with the manifest summary) |
| Knowledge-base / hash consistency | **100% consistent** - one `knowledge_base_hash`, one `inputs_combined` hash, one `dimension_mapping_hash` across all 104 directories |
| Per-test-case rejected outcome (`verification.status: REJECTED_FINAL`) | **309 of 22,153 test cases (1.4%)**, across **20 of 99 packages** - two distinct root causes (see N12-01, corrected from an initial 293/18 estimate - Section 5.2) |
| Package self-validation (`test_cases/test_case_validation.json`) | **81 of 99 `valid:true`**, **18 of 99 `valid:false`** - but this file itself misses 2 of the 20 truly-affected packages (see N12-06) |
| Package-level validation (`pack/validation_report.json`) | 99 of 99 `valid:true` |
| Test-case readiness (`status: ready`) | **4.4%** of all test cases (976 of 22,153) |
| Test-case draft state (`status: pending_sme`) | **87.3%** (19,345 of 22,153) |
| Structural coverage gaps | 1,895 (1,673 scenarios with zero test cases + 222 unexercised follow-on legs), across 50 of 99 packages - but traced to only **6 underlying business questions**, each needing one BA/SME answer per program (see N12-03, Section 4, [COVERAGE-REMEDIATION-PLAN.md](./COVERAGE-REMEDIATION-PLAN.md)) |
| Supporting business rules flagged `requiring_review` | 23,563 of 37,403 (**63.0%**) |
| Artefact completeness | Only **2 of 99** packages ship every documented file; 97 are missing `client_flow_coverage.csv/.md` |

Five issues were found through direct structural and self-validation checks, plus a sixth discovered only by independently re-deriving every `by_status`/`verification` bucket per package rather than trusting the narrower `test_cases/test_case_validation.json` file (N12-06). Recorded in [ISSUE-REGISTER.csv](./ISSUE-REGISTER.csv): one High (a confirmed, systematic test-case generation defect with two distinct root causes), three Medium (a self-validation reporting gap, readiness and coverage gaps) and two Low (artefact completeness and a tracked spec gap). Priority reflects impact on delivery acceptance, not security severity. Full per-network detail is in [BUILD-MATRIX.csv](./BUILD-MATRIX.csv) (99 rows, with every `by_status`/`verification` key reconciled to each package's test-case count). A dedicated hand-off for the AI generation team, with the complete 309-row affected test-case list and annotated sample JSON for both root causes, is in [N12-01-DEEP-DIVE-AI-TEAM-HANDOFF.md](./N12-01-DEEP-DIVE-AI-TEAM-HANDOFF.md).

## 2. Detailed analysis - what is actually in this delivery

### 2.1 Directory-to-package resolution

```
104 build directories
 = 99 unique (program, network) packages          <- the full ATL105 2026-4 BUYPASS network sweep
 +  5 duplicate "priority" extracts (same content, + rendered test data):
      buypass-atl105-amex-credit-f04f342cff         == buypass-atl105-full-set-f04f342cff        (CREDIT / AMERICAN_EXPRESS)
      buypass-atl105-interlink-debit-5d305cd6cb     == buypass-atl105-full-set-5d305cd6cb        (DEBIT / INTERLINK)
      buypass-atl105-maestro-direct-debit-c63544518a== buypass-atl105-full-set-c63544518a        (DEBIT / MAESTRO_DIRECT)
      buypass-atl105-mastercard-credit-bada93f456   == buypass-atl105-full-set-bada93f456         (CREDIT / MASTERCARD)
      buypass-atl105-mastercard-fleet-f5c774c0de    == buypass-atl105-full-set-f5c774c0de         (FLEET / MASTERCARD)
```

Each pair shares an identical scenario count, test-case count and hash lineage; the "priority" copy additionally has `pattern_scope: reviewed` and `test_data: rendered`, while its full-set twin has `test_data: not requested (test cases only)`. All other 94 packages are `pattern_scope: derived` / `test_data: not requested`.

### 2.2 Batch identity and consistency

- All 104 manifests share **one** `knowledge_base_hash`, **one** `inputs_combined` hash and **one** `dimension_mapping_hash` - the whole batch was built against a single, unchanging knowledge-base snapshot (`spec_version 2026-4`, `transaction_catalog_version 2026-10-30.1`). No drift was detected mid-batch.
- The 94-package sweep ran as a single orchestrated run (`run_id 20261031T103409468465Z`, 10:34-11:10 UTC); the 5 priority packages were built individually just before it (10:06-10:34 UTC, distinct run_ids each).
- `merchant_provenance` is `"placeholder"` on **all 104** directories, including the 5 "rendered" priority packages - none of this delivery uses production/final merchant data yet.
- `state: CANDIDATE` / `mapping_status: candidate` on all 104 - nothing has been promoted.

### 2.3 Network/program coverage matrix (99 unique packages)

| Program | Packages | Scenarios | Test cases | Coverage gaps | Ready % |
|---|---:|---:|---:|---:|---:|
| CHECK | 5 | 40 | 420 | 0 | 0.0% |
| CREDIT | 5 | 166 | 1,970 | 0 | 41.6% |
| DEBIT | 17 | 396 | 3,926 | 99 | 1.1% |
| EBT | 16 | 384 | 3,648 | 0 | 0.0% |
| FLEET | 14 | 772 | 3,109 | 544 | 3.7% |
| LOYALTY | 1 | 8 | 164 | 0 | 0.0% |
| PINLESS_DEBIT | 17 | 272 | 3,196 | 0 (but 100% self-validation failure - see N12-01) | 0.0% |
| PROPRIETARY | 13 | 1,066 | 2,782 | 858 | 0.0% |
| SIGNATURE_DEBIT | 1 | 16 | 218 | 8 (+ self-validation failure) | 0.0% |
| STORED_VALUE | 10 | 626 | 2,720 | 386 | 0.0% |
| **Total** | **99** | **3,746** | **22,153** | **1,895** | **4.4%** |

Only CREDIT, DEBIT and FLEET show any non-zero "ready" test cases, and in each case it is driven entirely by that program's one reviewed priority network (Mastercard/Amex Credit; Interlink/Maestro Debit; Mastercard Fleet). Every other program - CHECK, EBT, LOYALTY, PINLESS_DEBIT, PROPRIETARY, STORED_VALUE, SIGNATURE_DEBIT - is **0% ready**, i.e. entirely first-pass/draft. The full 99-row matrix (network, scenario/TC counts, status breakdown, coverage gaps, self-validation result, BR review burden) is in [BUILD-MATRIX.csv](./BUILD-MATRIX.csv).

## 3. BR validation

Each package carries exactly one umbrella business requirement (e.g. `BR-12B94100` / display id `BR1`, "BUYPASS ATL105 MasterCard Credit"), under which the AI solution decomposes into test scenarios (`BR1_TS1`...) and, beneath those, the fine-grained **supporting rules** that are the actual atomic, source-cited business rules (576 supporting rules under the one Mastercard Credit BR, for example). `pack/validation_report.json` - the package's own structural self-check (exactly one BR, `status: built`, consistent selection/hash) - reports **valid:true with zero findings on all 99 packages**; this check is a schema/identity smoke test, not a semantic/business-rule check.

The meaningful BR-readiness signal is the **supporting-rules review burden**:

| Program | Supporting rules | Requiring review | % |
|---|---:|---:|---:|
| FLEET | 7,048 | 4,903 | 70% |
| STORED_VALUE | 4,524 | 2,948 | 65% |
| LOYALTY | 388 | 247 | 64% |
| PINLESS_DEBIT | 6,239 | 3,910 | 63% |
| CHECK | 1,771 | 1,103 | 62% |
| CREDIT | 2,466 | 1,533 | 62% (Mastercard Credit alone: 354 of 576, 61%) |
| SIGNATURE_DEBIT | 364 | 220 | 60% |
| EBT | 7,024 | 4,288 | 61% |
| DEBIT | 5,226 | 3,059 | 59% |
| PROPRIETARY | 2,353 | 1,352 | 57% |
| **Total** | **37,403** | **23,563** | **63.0%** |

63% of every business rule citation in the delivery - **including the 5 SME-reviewed priority networks** - is still self-flagged `requiring_review`, i.e. not yet confirmed by a human. In addition, the delivery logs **4,407 open pair-questions** (unresolved dimension combinations, e.g. "does Credit support Contactless Mobile Commerce Device entry?") and **10,101 excluded combinations** (dimension pairs the generator determined are *not* supported, each with a page-cited spec quote, e.g. `p.161 "...the following point-of-sale transactions are supported for EMV card processing..."` as the basis for excluding Mail/Phone Purchase x EMV Contact Chip). Spot-checked excluded-combination citations carry plausible page/quote pairs, but citation-by-citation verification against the source PDF was out of scope for this pass (see Section 6).

**Conclusion:** structurally the BR packs are internally well-formed (one BR per package, consistent counts, no schema findings), but only the 5 priority networks have any SME-reviewed status at all, and even those are majority-unreviewed at the rule level. The other 94 networks are **100% first-pass/draft** business requirements.

## 4. Coverage validation

Two independent coverage signals were checked per package:

1. **Structural coverage** (`test_cases/coverage_report.json`): whether every scenario has at least one test case, and every follow-on leg (void/reversal/timeout-reversal/cancellation) is exercised by some test case.
2. **Self-validation** (`test_cases/test_case_validation.json`): whether the test cases that *do* exist are internally consistent (correct transaction-type/POS codes, etc.).

Structural coverage gaps are **zero in CREDIT, EBT, PINLESS_DEBIT, CHECK and LOYALTY**, but heavily concentrated elsewhere:

| Network (worst offenders) | Program | Scenarios | With test cases | Gaps | Gap kinds |
|---|---|---:|---:|---:|---|
| WRIGHT_EXPRESS | FLEET | 90 | 28 | 70 | 62 scenarios w/o TCs, 8 legs not exercised |
| TEXACO, CITI, HSBC_DIRECT, HSBC_PAYPOINT, COMDATA_CORPORATION, ALLIANCE_DATA_SYSTEMS, SINCLAIR, SUN_OIL, UNOCAL_CORPORATION, OPTUM, ECA_TELECHECK_DEBIT, CAR_CARE, VALERO | PROPRIETARY (all 13 of 13) | 82 | 22 | 66 | 60 scenarios w/o TCs, 6 legs not exercised |
| ACORMIDA_BP_PAYPOINT, BUYPASS, CARDLOCK_BP_PAYPOINT, FLEETCOR2, GASCARD, VALERO, WEX_ISO | FLEET (7 of 14) | 82 | 22 | 66 | 60 scenarios w/o TCs, 6 legs not exercised |
| COMDATA_FLEET | FLEET (1 of 14) | 18 | 10 | 12 | smaller-scale gap |
| STORED_VALUE (9 of 10 at 38; INCOMM at 44) | STORED_VALUE | 62-68 | ~24 | 38-44 | majority scenarios w/o TCs |

The remaining 5 FLEET networks (FLEETCOR, FLEET_ONE, VOYAGER, WEX_OTR_FLEET, and the reviewed MASTERCARD) show zero structural gaps. Totalled across the delivery: **1,673 scenarios with zero test cases** ("no check planned") and **222 follow-on legs never exercised** by any test case = **1,895 structural gaps**, across **50 of 99 packages** (PROPRIETARY 13 of 13, DEBIT 17 of 17, STORED_VALUE 10 of 10, FLEET 9 of 14, Signature Debit 1 of 1). For PROPRIETARY/TEXACO specifically, and identically for all 12 other PROPRIETARY networks, **60 of 82 scenarios (73%) have no test case at all** - the single largest proportional gap found, repeated across an entire program.

**Every one of these 1,895 gaps was traced to its source scenario/leg record, and in every case checked - including in the SME-reviewed Interlink Debit package - the record itself is already marked `"status": "unconfirmed", "requires_sme": true`**, with a matching entry already sitting in `pack/open_pair_questions.json` (N12-02). The composer is correctly declining to invent a test case for a combination nobody has confirmed is real; it is not a generation defect like N12-01. The 1,895 gaps reduce to just **6 underlying business questions** (does each program support Stored-Value lifecycle operations, Cancellation, Balance Inquiry, Loyalty, Account Verification, and Token Registration), each needing one BA/SME answer **per program, not per network** - see the full breakdown and cascade plan in [COVERAGE-REMEDIATION-PLAN.md](./COVERAGE-REMEDIATION-PLAN.md).

Separately, the self-validation layer failed outright (see Section 5.2 / N12-01) for all 17 PINLESS_DEBIT networks and the one SIGNATURE_DEBIT network: these show **zero structural gaps** (every scenario has test cases, every leg is exercised) but a meaningful slice of those test cases **assert the wrong transaction-type code**, which is arguably a more serious coverage problem than an absent test case, because it looks covered on every summary metric except the one place that actually re-checks the content.

`verification: FAIL` (a literal key of that name) was not found anywhere, but that phrasing understates the true picture: `manifest.json summary.verification` actually carries a third key, `REJECTED_FINAL` - **309 of 22,153 test cases (1.4%)**, across **20 of 99 packages** - which is the delivery's real rejected/failed-verification outcome. See Section 5.2 for the full, corrected account (the initial pass through this report under-counted this at 293/18 by relying only on `test_cases/test_case_validation.json`).


## 5. End-to-end artefact validation

### 5.1 File-count reconciliation (manifest vs. actual files)

For every one of the 99 packages, the following were independently recomputed from the files on disk and compared against the `manifest.json` summary: line count of `test_cases/test_cases.jsonl`, row count of `test_cases/test_cases.csv`, row count of `pack/business_scenarios.csv`, the build-root copy of `test_cases.jsonl` against the nested copy, and - after the correction below - every individual `by_status` and `verification` bucket reported in each manifest (not just the 5/2 keys assumed on a first pass).

**Result: 0 mismatches across all 99 packages, every check.** Every manifest-declared `scenarios` and `test_cases` count is exactly reproduced by the underlying files, the build-root/nested `test_cases.jsonl` copies are byte-for-byte line-count identical, and - once all six `by_status` keys (`ready`, `needs_client_input`, `requires_review`, `pending_sme`, `blocked_spec_gap`, **and `blocked_verification`**) and all three `verification` keys (`PASS`, `NOT_APPLICABLE`, **and `REJECTED_FINAL`**) are summed per package, every package's totals reconcile exactly to its `test_cases` count. This is a clean result - the delivery's own count bookkeeping is trustworthy, provided every key present is actually read (see the correction in 5.2).

### 5.2 Self-validation outcome (corrected - two distinct defects, one of them under-reported by the delivery's own tooling)

An initial pass through this delivery checked only `test_cases/test_case_validation.json`, which reports `"valid": false` for 18 of 99 packages. Re-deriving the same signal independently from `manifest.json summary.by_status.blocked_verification` / `summary.verification.REJECTED_FINAL` - and reconciling it against every individual test case's own embedded `verification.status` field - found **20 of 99 packages and 309 of 22,153 test cases**, not 18/293. The extra 2 packages (Discover Network Credit, Moneris Canada Credit) are **not flagged at all** by `test_cases/test_case_validation.json` (it reports `valid:true, findings:[]` for both), even though 10 and 6 of their own test cases respectively carry `verification.status: REJECTED_FINAL` - this gap is itself recorded as **N12-06**.

The 309 rejected test cases resolve to exactly **two** distinct rule-check IDs, confirmed by reading every affected test case's own `verification.reasons`:

- **`LEG-TXN-TYPE` (293 cases, 18 packages - all 17 PINLESS_DEBIT networks + Signature Debit):**
  ```
  TC-005007 step 1: Transaction Type '0' is not a POS code of TIMEOUT_REVERSAL
  TC-021670 step 1: Transaction Type '0' is not a POS code of AUTH_ONLY
  ```
  16 such findings recur in every PINLESS_DEBIT package (one set per network, otherwise identical) and 21 recur in the Signature Debit package. Each affected test case's own `completeness_findings` self-documents the cause: element 78 "Prompt Code" (Segment 100, position 1 = Transaction Type) is assembled from spec parts, but when the leg's context (`TIMEOUT_REVERSAL`/`RETURN`/`AUTH_ONLY`) does not match any Appendix table caption (`COMPOSITE_CONTEXT_UNSCOPED`), the compositor searches every table in the appendix, fails to find a fit, and silently keeps the default `'0'` (`COMPOSITE_FROM_SPEC_PARTS: '0' did not fit the financial transaction layout ... kept from composed value`) instead of failing loudly or resolving the correct leg-specific digit.
- **`SPLIT-TENDER-CARD-B` (16 cases, 2 packages - Discover Network Credit 10, Moneris Canada Credit 6):**
  ```
  TC-003871 step 2: SPLIT-TENDER-CARD-B expected hold but is violated (actual {'this': 'TOTALS REQUEST', 'other': 'TOTALS REQUEST'})
  ```
  The rule requires the second ("split tender") card leg's Segment 100 "Account Number" to differ from the first card leg's, per spec basis "a different form of payment pays the remaining balance" (p.84, p.87). In every affected test case, **both** legs' Account Number field is populated with the literal placeholder string `"TOTALS REQUEST"` (`method: qe_example_value`) instead of two distinct example card numbers - the example-value catalog lookup for Account Number on these two networks' split-tender legs is returning the wrong catalog entry.

Both patterns are 100% reproducible (identical finding count per affected package) and point to a single root cause each, not scattered per-network data issues. Neither is visible from `manifest.json`'s top-level `state`/`mapping_status`, from `pack/validation_report.json`, or from any coverage number - all of those report green for all 20 packages. The complete, authoritative list of all 309 affected test cases (not just the 293 that `test_case_validation.json` surfaces), together with annotated sample JSON for both root causes, is in [N12-01-DEEP-DIVE-AI-TEAM-HANDOFF.md](./N12-01-DEEP-DIVE-AI-TEAM-HANDOFF.md) and [n12_01_COMPLETE_affected_test_cases.csv](./n12_01_COMPLETE_affected_test_cases.csv). See **N12-01** (the defects) and **N12-06** (the reporting gap) in the issue register.

### 5.3 Artefact completeness

Every package's directory tree was checked against the 24-file template demonstrated by a complete package (`manifest.json`, `pack/*` x 10, `reports/*` x 2, `test_cases/*` x 8, plus the build-root `test_cases.jsonl`/`test_cases.index.json`):

- **2 of 99** packages (Mastercard Credit, Mastercard Fleet) contain every documented file.
- **92 of 99** are missing only `test_cases/client_flow_coverage.csv` and `test_cases/client_flow_coverage.md` - including 3 of the 5 SME-reviewed priority packages (Amex Credit, Interlink Debit, Maestro Direct Debit).
- **5 of 99** (all CHECK-program networks) are additionally missing `pack/follow_on_leg_combinations.csv` - this is explainable, not a defect: CHECK transactions have `follow_on_legs: 0` in every one of those manifests, so there is nothing to list.
- No zero-byte / truncated files were found anywhere in the 99 packages.


See **N12-04** in the issue register for the `client_flow_coverage` gap.

### 5.4 Spec-gap acknowledgement

51 test cases across 26 packages (including Mastercard Credit and Amex Credit) carry `by_status: blocked_spec_gap` - the generator's own flag that the pinned ATL105 2026-4 text does not resolve a specific combination. These are correctly excluded from "ready"/"pending_sme" counts but are not yet routed to BA/SME for resolution (**N12-05**).

## 6. Issue register

Full detail, acceptance criteria and affected scope for each item: [ISSUE-REGISTER.csv](./ISSUE-REGISTER.csv).

| ID | Priority | Classification | Packages affected |
|---|---|---|---|
| N12-01 | High | Confirmed test-case generation defect - two root causes (`LEG-TXN-TYPE`; `SPLIT-TENDER-CARD-B`) | 20 of 99 (17 PINLESS_DEBIT + Signature Debit + Discover Network Credit + Moneris Canada Credit) |
| N12-02 | Medium | Business-rule validation readiness gap (63% rules unreviewed) | 99 of 99 |
| N12-03 | Medium | Coverage gap concentration (scenarios with no test case) | 50 of 99 (PROPRIETARY 13 of 13, STORED_VALUE 10 of 10, DEBIT 17 of 17, FLEET 9 of 14, Signature Debit 1 of 1) - see [COVERAGE-REMEDIATION-PLAN.md](./COVERAGE-REMEDIATION-PLAN.md) |
| N12-04 | Low | Artefact completeness (`client_flow_coverage` missing) | 97 of 99 |
| N12-05 | Low | Spec-gap acknowledgement (tracked, not a defect) | 26 of 99 |
| N12-06 | Medium | Self-validation reporting gap (`test_case_validation.json` misses 2 of 20 truly-affected packages) | 2 of 99 (Discover Network Credit; Moneris Canada Credit) |

## 7. Scope boundaries and what was not done

- The 5 priority-network packages' previously-reported issues (31-Oct report: traceability references, EMV Element 188 rendering width, obsolete embedded archive, reused TC identities, split-tender EMV dependency) were **not re-verified** here; they remain open per that report.
- Excluded-combination and supporting-rule **page citations were spot-checked for plausibility only**, not verified line-by-line against the ATL105 2026-4 source PDF. A dedicated citation-verification pass (as already exists for Mastercard Credit via `scripts/validate-mastercard-fr-matrix.py`) would be needed to certify the 94 new networks' citations.
- `test_cases/test_cases.jsonl` contents were validated for well-formed JSON, for the packages' own internal `test_case_validation.json`/`coverage_report.json` checks, and - per the N12-01/N12-06 correction - for each test case's own embedded `verification.status`/`reasons`/`rule_checks` fields directly. Independent host/execution verification (e.g. running the 22,153 test cases against a BUYPASS simulator) was not performed and is unrelated to this file-level review.
- The existing `regression-artifact-validator/scripts/delivery_gates.py` ("native ATL105 business-model delivery exports" gate) targets a different, later-stage package layout (`requirements_scenarios/business_requirements.json` + root `run_set.json`) and does not apply as-is to this `pack/`+`test_cases/` layout; it was not run against this delivery.

## 8. Recommendations

1. Send **N12-01** to the AI generation team immediately via [N12-01-DEEP-DIVE-AI-TEAM-HANDOFF.md](./N12-01-DEEP-DIVE-AI-TEAM-HANDOFF.md) - both root causes are precise, reproducible and single-cause (element 78 "Prompt Code" Transaction Type defaults to `'0'` on follow-on legs for PINLESS_DEBIT/SIGNATURE_DEBIT; Account Number example-value lookup returns the placeholder `"TOTALS REQUEST"` for split-tender second-card legs on Discover Network/Moneris Canada), each with exact file/line evidence and a complete 309-row affected-case list.
2. Fix **N12-06** alongside N12-01: `test_case_validation.json` must be generated from every test case's own `verification.status`/`reasons`, not a narrower check set, so it cannot silently miss an entire rule-check category again.
3. Treat all 94 newly-swept networks as **draft/candidate only** - none has SME review, and programs outside CREDIT/DEBIT/FLEET have no reviewed network at all to anchor a readiness baseline.
4. Resolve **N12-03** via the 6 decision clusters, not network-by-network - route the up to 17 cluster-program questions in [COVERAGE-REMEDIATION-PLAN.md](./COVERAGE-REMEDIATION-PLAN.md) to BA/SME, starting with Stored-Value lifecycle (930 of 1,895 gaps, 3 programs); resolving PROPRIETARY and FLEET's 6 clusters each closes 74% of all gaps in the delivery. This is the same open-pair-question backlog already counted in N12-02, so answering it also pays down part of N12-02.
5. Decide and document whether `client_flow_coverage.csv/.md` (N12-04) is a required deliverable; if so, regenerate it for all 97 missing packages.
6. Route the 51 `blocked_spec_gap` test cases (N12-05) to BA/SME now, rather than leaving them static across future rebuilds.
