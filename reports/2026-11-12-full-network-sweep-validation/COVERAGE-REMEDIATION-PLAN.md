# Coverage remediation plan - PROPRIETARY / FLEET / STORED_VALUE / DEBIT / Signature Debit gaps (N12-03)

**Prepared:** 12 November 2026 (supplement to [FULL-NETWORK-SWEEP-VALIDATION-REPORT-12-NOV-2026.md](./FULL-NETWORK-SWEEP-VALIDATION-REPORT-12-NOV-2026.md), issue N12-03)
**Audience:** BA/SME team (decisions), AI generation team (execution once decided), Test Model/QE team (catalog updates)
**Scope:** all 1,895 structural coverage gaps across the 50 affected packages.

## 1. Reframing: this is not 1,895 separate problems, and not primarily a generation defect

Every coverage gap in this delivery was traced to its source scenario or follow-on leg in `pack/business_scenarios.json` / `pack/follow_on_leg_combinations.csv`. In every case checked - across every one of the 11 affected transaction categories, in both "derived" packages (e.g. Texaco Proprietary) and in an **SME-reviewed priority package** (Interlink Debit) - the record itself already carries:

```
"status": "unconfirmed",
"requires_sme": true,
"open_pairs": ["program-txn_category", "program-entry_mode", "network-txn_category", "network-entry_mode", "txn_category-entry_mode"]
```

and the matching question already exists in `pack/open_pair_questions.json` (e.g. `"a": "PROPRIETARY", "b": "BALANCE_INQUIRY"` and `"a": "TEXACO@PROPRIETARY", "b": "BALANCE_INQUIRY"`). The test-case composer is **correctly** declining to fabricate an assertion for a combination nobody has confirmed is real - this is the responsible behaviour, not the N12-01-style defect where it silently produced a wrong value. The 1,895 gaps are the visible, downstream symptom of a much smaller number of unanswered business questions, and they overlap substantially with the open-pair-question backlog already counted under **N12-02**.

This means the fix is not "generate 1,673 missing test cases" - it is "get a small number of yes/no answers from BA/SME," after which each answer cascades to every network in that program automatically.

## 2. The entire 1,895-gap backlog reduces to 6 decision clusters

Every one of the 11 affected transaction/leg categories was grouped by its underlying business question (a stored-value lifecycle question covers all 5 `SV_*` categories at once; a loyalty question covers both `LOYALTY_*` categories at once). Counts below are fully reconciled: they sum to exactly 1,895.

| # | Decision cluster | Transaction/leg categories | Gap kind | Programs in question | (program,network) pairs affected | Gap count | % of total |
|---|---|---|---|---|---:|---:|---:|
| 1 | **Stored-value lifecycle** | SV_ACTIVATION, SV_DEACTIVATION, SV_BALANCE_MERGE, SV_REPLACE_CARD, SV_RECHARGE | scenario has 0 test cases | FLEET, PROPRIETARY, STORED_VALUE | 31 | **930** | 49.1% |
| 2 | **Cancellation (follow-on leg)** | CANCELLATION | follow-on leg never exercised | FLEET, PROPRIETARY, SIGNATURE_DEBIT, STORED_VALUE | 33 | **222** | 11.7% |
| 3 | **Balance Inquiry** | BALANCE_INQUIRY | scenario has 0 test cases | DEBIT, FLEET, PROPRIETARY | 38 | **227** | 12.0% |
| 4 | **Loyalty program** | LOYALTY_LOOKUP, LOYALTY_ADVICE | scenario has 0 test cases | FLEET, PROPRIETARY | 21 | **252** | 13.3% |
| 5 | **Account Verification** | ACCOUNT_VERIFICATION | scenario has 0 test cases | FLEET, PROPRIETARY, STORED_VALUE | 23 | **138** | 7.3% |
| 6 | **Token Registration** | TOKEN_REGISTRATION | scenario has 0 test cases | FLEET, PROPRIETARY | 21 | **126** | 6.6% |
| | | | | **Total** | | **1,895** | **100%** |

At most **17 (cluster x program) questions** - not 1,895, not even 50 - need an SME/BA answer. Several programs need only one: **DEBIT's entire 99-gap footprint across all 17 of its networks is 100% the Balance Inquiry cluster** - a single "does BUYPASS Debit support Balance Inquiry, and on which entry modes" decision closes every DEBIT gap in this delivery.

Full scenario/leg-level backlog (program, network, scenario ID, kind, reason) backing this table: [coverage_gaps_full.csv](./coverage_gaps_full.csv) (1,895 rows).

## 3. Per-program impact if each program's open clusters are resolved

| Program | Clusters in question | Networks | Gaps closed if fully resolved |
|---|---|---:|---:|
| PROPRIETARY | All 6 | 13 (all) | 858 (100% of its gaps) |
| FLEET | All 6 | 9 of 14 | 544 (100% of its gaps) |
| STORED_VALUE | Stored-value lifecycle, Cancellation, Account Verification | 10 (all) | 386 (100% of its gaps) |
| DEBIT | Balance Inquiry only | 17 (all) | 99 (100% of its gaps) |
| SIGNATURE_DEBIT | Cancellation only | 1 (all) | 8 (100% of its gaps) |

Resolving all 17 cluster-program questions closes **all 1,895 gaps in all 50 packages** - there is no separate long tail once these decisions are made.

## 4. Remediation phases

### Phase 1 - SME/BA decision (one pass per cluster-program pair, not per network)

For each of the up to 17 cluster-program cells in Section 2/3, BA/SME answers, against the pinned ATL105 2026-4 source:
- **Does this program support this transaction/leg category at all**, and if so **on which entry modes**?
- Record the citation (page/quote) the same way `excluded_combinations.csv` already does for confirmed-unsupported pairs, so the answer is auditable.

Recommended sequencing by impact: (1) Stored-value lifecycle (930 gaps, 3 programs) first, (2) Loyalty program (252) and Balance Inquiry (227) next, (3) Cancellation (222), (4) Account Verification (138), (5) Token Registration (126). Note PROPRIETARY and FLEET need a decision on all 6 clusters each; clearing those two programs first closes 1,402 of 1,895 gaps (74%).

### Phase 2 - cascade the answer to every network in that program

- **If confirmed supported:** hand the (program, category, confirmed entry modes) to the AI generation team to compose the missing test case(s) - for the `CANCELLATION` cluster specifically, this means composing a test case that actually chains into and exercises the leg, not just defining it. Apply identically to every network already listed against that program in Section 2 (no per-network re-analysis needed beyond the usual network-specific data substitution).
- **If confirmed not supported:** Test Model/QE team moves every matching scenario/leg ID from "0 test cases" into `pack/excluded_combinations.csv` (and `pack/follow_on_leg_combinations.csv`'s `blocked_flows`) with the SME citation, for every network in that program. This removes it from `coverage_gaps` entirely (excluded combinations are not gaps) and from the scenario-count denominator, so the package's reported coverage becomes accurate instead of just numerically smaller.
- Either path also resolves the matching entries already sitting in `pack/open_pair_questions.json` (N12-02) for that program/category - no separate exercise needed.

### Phase 3 - verification

- Re-run this analysis (or equivalent) against the regenerated packages: `manifest.json summary.coverage_gaps` should be `0` for all 50 currently-affected packages, and any remaining "0 test case" scenario must have a matching, cited `excluded_combinations` row - none may remain silently unresolved.
- Spot-check one network per program (e.g. Texaco for PROPRIETARY, Wright Express for FLEET, NetSpend for STORED_VALUE, Interlink for DEBIT) end-to-end: scenario status no longer `unconfirmed`, open_pair_questions entry removed or answered, and either a composed test case or an excluded_combinations citation exists.

## 5. What this plan does not do

- It does not decide any of the 17 cluster-program questions itself - these are genuine BUYPASS/network business-rule determinations (e.g. "does a Texaco fuel card actually support Loyalty Lookup") that require BA/SME judgment against the source spec and/or the network operator, not something derivable from the delivered artefacts alone.
- It is scoped to the 1,895 **structural** coverage gaps (N12-03). It does not re-open N12-01 (generation defects), N12-02 (the broader 63%-unreviewed supporting-rules backlog, of which these open pair-questions are a subset), N12-04/05/06, or the 5 priority networks' previously-reported 31-Oct issues.
