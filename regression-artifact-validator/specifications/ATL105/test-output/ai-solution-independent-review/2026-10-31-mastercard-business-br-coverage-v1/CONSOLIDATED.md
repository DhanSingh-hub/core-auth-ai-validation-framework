# Consolidated Mastercard / Maestro Network Coverage Report

**Assessment date:** 3 November 2026  
**AI delivery:** `31-10-out`, builds dated 31 October 2026  
**Declared specification:** ATL105 2026-4  
**Disposition:** Structural inventory assessed; independent semantic BR coverage NOT CALCULABLE; execution BLOCKED.

## 1. Executive conclusion

The supplied network-family scope contains three packages: Mastercard Credit,
Mastercard Fleet and Maestro Direct Debit. Together they contain **3 umbrella BRs,
88 scenarios, 1,027 TCs and 1,944 physical TD files**.

**81/88 scenarios (92.05%) have linked TCs and physical TD.** All declared TD paths
resolve. This is structural scenario reach, not proof of correct scenarios, complete
business-rule coverage or executable tests.

The supporting-rule inventory has **998 distinct producer rule IDs** across the
three packages. Of these, **976/998 (97.80%)** have scenario-to-TC-to-physical-TD
lineage in at least one package. This union is producer-ID inventory reach only:
it does not establish semantic deduplication, independent applicability or coverage.
A rule reached in one package is not thereby tested for every applicable card type.

**All associated card types cannot be declared covered.** Physical financial TD
contains codes `020`, `080` and `011`. Maestro scenarios declare `012` and `013`
as alternatives, but neither code occurs in physical financial TD. Mastercard
Signature Debit/Common-AID behavior is source-recognized but not separately
established as covered. Credit/debit/prepaid consumer product classifications
cannot be inferred from a generic card code or package label.

## 2. Scope and card-type interpretation

Input root:

```text
C:\Users\F9HBPGC\OneDrive - Fiserv Corp\Documents\Ai Output\31-10-out
```

Included: `mastercard_credit`, `mastercard_fleet`, `maestro_direct_debit`.
Excluded as separate networks: `american_express_credit`, `interlink_debit`.
The consolidation groups Mastercard and Maestro for reporting, but preserves
their distinct network/program labels, builds and package-qualified identities.

### Source distinctions

The independently pinned ATL105 2026-4 text has SHA-256:

```text
93f11336ae817266df786c8a83541f96ae680ea08629117a775d362c8b45a497
```

Source: [ATL105 2026-4 extracted text](../../../docs/specs/extracted_text_new.txt).

- Appendix C, PDF pages 511-512: authorizer `03` identifies Mastercard Credit;
  authorizer `40` identifies Maestro Direct Debit. These are **authorizer codes**,
  not financial Prompt Code card types.
- Appendix E, PDF page 521: financial card types include `011` Debit checking,
  `012` Debit saving, `013` Debit ACH and `020` Credit.
- Appendix E, PDF page 522: `080` is Mastercard Fleet.
- Appendix E, PDF page 518: table-load descriptions distinguish original Signature
  Debit and follow-up processing using `020`, `011` and Element 198 settlement context.
  Do not flatten that contextual description into a universal brand/code mapping.
- Section 10.14.1, PDF page 160 (printed 10-81), discusses US Maestro Common AID
  and Mastercard credit/Signature Debit routing. An AID/routing context is not a
  separately proven card-product fixture.

Generic debit codes are not exclusively Maestro. Their appearance in this report
reflects the supplied Maestro package's declarations, not a universal mapping.

## 3. Card-type and product coverage matrix

| Network / context | Financial card code | Supplied evidence | Observed financial TD | TCs with that code in declared TD | Coverage disposition |
|---|---|---|---:|---:|---|
| Mastercard Credit | `020` | Credit package; 36 scenarios declare `020` | 767 | 408 | Physical presence and structural lineage established; semantic coverage unassessed |
| Mastercard Fleet | `080` | Fleet package; 24 scenarios declare `080` | 497 | 249 | Physical presence and structural lineage established; semantic coverage unassessed |
| Maestro Direct Debit - checking | `011` | Maestro package; 28 scenarios include this alternative | 473 | 249 | Physical presence established; only 21/28 scenarios have TCs |
| Maestro package - savings alternative | `012` | All 28 scenarios list this generic alternative | 0 | 0 | Declared but not represented in physical TD; applicability and required variants need review |
| Maestro package - ACH alternative | `013` | All 28 scenarios list this generic alternative | 0 | 0 | Declared but not represented in physical TD; do not assume Maestro ACH support |
| Mastercard Signature Debit / US Maestro Common-AID context | Context-dependent `020` / `011` | Source-recognized; no separately identified package in this delivery | Not separately classified | Not separately classified | NOT ASSESSED; generic `020` or `011` presence does not establish this context |
| Mastercard/Maestro prepaid, commercial or other issuer-product subtypes | Not established by this assessment | No independently classified subtype population established | Not separately classified | Not separately classified | NOT ASSESSED; do not invent subtype coverage or classify as unsupported |

Financial TD counts above are actual Segment 100 `PromptCode.CardType` values,
not the union of allowed scenario codes. Each TC is counted once per observed
code across its declared TD legs; code-specific TC counts can overlap in a
multi-card flow. In this delivery, the three observed codes belong to different
packages and their TC populations do not overlap.

There are also **207 Totals Request TD files** (Credit 72, Fleet 72, Maestro 63).
They contain no Segment 100 and are deliberately excluded from financial
card-code counts. Thus 767 + 497 + 473 + 207 = **1,944 physical TD files**.
Totals requests are not missing card-type tests.

## 4. Per-package BR / scenario / TC / TD coverage

| Metric | Mastercard Credit | Mastercard Fleet | Maestro Direct Debit | Combined |
|---|---:|---:|---:|---:|
| Umbrella BRs | 1 | 1 | 1 | 3 |
| Scenarios | 36 | 24 | 28 | 88 |
| Scenarios with TCs and physical TD | 36 | 24 | 21 | 81 |
| Scenario structural reach | 100% | 100% | 75% | 92.05% |
| TCs | 450 | 299 | 278 | 1,027 |
| TCs with all declared TD JSON files | 408 | 249 | 249 | 906 |
| TCs without TD declarations | 42 | 50 | 29 | 121 |
| Physical TD files | 839 | 569 | 536 | 1,944 |
| Missing declared TD references | 0 | 0 | 0 | 0 |
| Unreferenced physical TD files | 0 | 0 | 0 | 0 |
| Bounded native validation | FAIL | FAIL | FAIL | FAIL |
| Execution readiness | BLOCKED | BLOCKED | BLOCKED | BLOCKED |
| Confirmed independent BR coverage | Not calculable | Not calculable | Not calculable | Not calculable |

Combined TC-to-all-declared-TD presence is **906/1,027 (88.22%)**.
The other 121 TCs comprise **119 T11 device-only cases** and **2 Credit T5
blocked-spec-gap cases**. Absence of TD is not automatically a defect for these
classes. Conversely, physical TD presence does not establish runtime binding,
valid cryptograms, response oracles or execution readiness.

The three umbrella BRs each have a physical chain. Reporting that as "100% BR
coverage" would conceal the atomic-rule and scenario gaps and is not acceptable.

## 5. Supporting-rule reach, not semantic BR coverage

| Metric | Credit | Fleet | Maestro |
|---|---:|---:|---:|
| Supporting-rule inventory | 590 | 814 | 447 |
| Rules with scenario-to-TC-to-physical-TD lineage | 576 | 793 | 431 |
| Structural rule lineage | 97.63% | 97.42% | 96.42% |
| Rules with explicit `kb_rule_ids` predicate + physical TD lineage | 3 | 5 | 1 |
| Rules with response-check declarations | 14 | 21 | 14 |
| Producer marks rule `requires_review` | 354 | 537 | 261 |

### Deduplication

- Package-qualified rule inventory: **1,851 rows**; physically reached: **1,800 rows**.
- Distinct producer rule IDs: **998**; physically reached in at least one package: **976**.
- Distinct IDs with explicit predicate + TD lineage: **7**.
- Distinct IDs with response-check declarations: **22**.
- Shared IDs have identical full rule-record content across these three supplied
  supporting-rule catalogs. This was checked before computing the union.
- Identical IDs/content are not proof that differently identified rules are
  semantically distinct, or that a rule applies to all three programs.

Scenario-wide supporting-rule lists broadcast lineage to TCs. They do not prove
that a TC tests each inherited rule. The explicit-predicate metric includes only
checks carrying `kb_rule_ids` that resolve through the matching scenario and
physical TD. Generic named predicates without those IDs are excluded.
**The low explicit-ID count is not evidence that all other rules have no tests.**

No predicate execution or semantic confirmation is claimed by this consolidation.
Existing native gates execute only their separate named bounded predicates.
Response `FLAG`/`ASSERT` declarations are not observed issuer outcomes.

## 6. Transaction categories and uncovered scenarios

| Declared base scenario category | Credit | Fleet | Maestro |
|---|---:|---:|---:|
| Purchase/Capture | 8 | 8 | 7 |
| Authorization Only | 8 | 8 | 7 |
| Merchandise Return | 8 | 8 | 7 |
| Balance Inquiry | 8 | 0 | 7 |
| Mail/Phone Purchase | 2 | 0 | 0 |
| Mail/Phone Authorization Only | 2 | 0 | 0 |
| Total | 36 | 24 | 28 |

These are producer-declared base categories, not confirmed network permissions.
A zero means no base scenario supplied in that package, not a source-backed
prohibition or a proven missing requirement. Reversals, completions, TORs and
other follow-on legs can exist inside TCs rather than as base scenarios.

All **7 Maestro Balance Inquiry scenarios** have no TCs:

- Contactless Magnetic Stripe
- Contactless Mobile
- Credential on File
- EMV Contact
- EMV Contactless
- EMV Fallback
- Magnetic Stripe

Determine source applicability and resolve SME questions before generating or
promoting these cases. Their existence as proposed scenarios is not proof that
the corresponding combinations are valid.

## 7. Producer-declared TC readiness

| Status | Credit | Fleet | Maestro | Total |
|---|---:|---:|---:|---:|
| `ready` | 180 | 76 | 6 | 262 |
| `needs_client_input` | 61 | 13 | 48 | 122 |
| `pending_sme` | 61 | 144 | 198 | 403 |
| `requires_review` | 146 | 66 | 26 | 238 |
| `blocked_spec_gap` | 2 | 0 | 0 | 2 |
| Total | 450 | 299 | 278 | 1,027 |

These are producer declarations, not independent readiness decisions. In
particular, **51 `ready` TCs** still have open prerequisites according to the
bounded native gate: Credit 33, Fleet 12, Maestro 6.

## 8. Bounded validation findings

| Finding | Credit | Fleet | Maestro | Total occurrences |
|---|---:|---:|---:|---:|
| Element 188 representation/width | 126 | 78 | 90 | 294 |
| Element 188 disagreement with EMV tag 5F34 | 126 | 78 | 90 | 294 |
| EMV placeholder/simulator evidence required | 126 | 78 | 90 | 294 |
| Runtime amount requires dependent EMV regeneration | 4 | 0 | 0 | 4 |
| `ready` with open prerequisites | 33 | 12 | 6 | 51 |
| Unresolved trace-report paths | 816 | 498 | 498 | 1,812 |
| Scenarios with no TC | 0 | 0 | 7 | 7 |
| Missing declared coverage basis | 1 | 1 | 1 | 3 |
| Stale embedded archive | 1 | 0 | 0 | 1 |
| Stale README | 1 | 0 | 0 | 1 |

Counts are emitted finding occurrences, not distinct defective TCs. A single TC
can have several findings and appear on multiple trace surfaces. Trace-report
path failures do not mean the authoritative TC-declared TD files are missing:
all those references resolve.

JSON segment-object count checks found no disagreements or duplicate SegmentType
values. That does not validate serialized Element 63, field ordering, general
segment lengths, cryptography, host responses or whole-message validity.
For companion-segment interpretation, the
[SME/TBA compatibility note](../../../docs/specs/kb/segment-100/companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
is learning material, not a business approval record.

## 9. Required next actions

1. Establish an independent, source-pinned 2026-4 rule denominator for Credit,
   Fleet and Maestro, with explicit card-type and transaction applicability.
2. Resolve whether Maestro `012` and `013` alternatives are actually applicable.
   If required, add distinct source-backed scenarios, TCs and physical TD; do not
   treat an allowed-code list as generated variation coverage.
3. Inventory Mastercard Signature Debit, US Maestro Common AID and any requested
   prepaid/commercial product subtypes separately. Bind them to independent
   program/card/AID evidence before counting them as tested.
4. Resolve Maestro Balance Inquiry scenarios and all unresolved SME decisions.
5. Correct the bounded EMV and export defects; bind client inputs, dynamic amounts,
   cryptograms and host-response oracles.
6. Map atomic objectives and source-backed assertions to independent BRs. Only
   evidence-qualified confirmed matches may enter semantic coverage.

The historical 2026-3 Mastercard Purchase **168/189** training result is not the
denominator or numerator for this 2026-4 delivery. Confirmed independent BR
coverage remains **null / NOT ASSESSED**, not zero coverage and not 97.80%.

## 10. Evidence and verification

This report consolidates existing assessed evidence without changing the source
delivery, original package report or validator semantics. The full **2,021-file
input SHA-256 inventory was reverified on 3 November 2026**. Card-code counts were
read from actual physical Segment 100 objects, including nested message envelopes;
nonfinancial Totals Request files were counted separately.

- [Original package-level report](REPORT.md)
- [Summary and release/build identity](summary.json)
- [Input inventory and hashes](input-manifest.csv)
- [Supporting-rule ledger](supporting-rule-ledger.csv)
- [Scenario ledger](scenario-ledger.csv)
- [TC/TD linkage ledger](case-ledger.csv)
- [Physical TD ledger](physical-td-ledger.csv)
- [Bounded native validation findings](native-gates.json)
- [Consolidated card-type matrix](consolidated-card-type-coverage.csv)

Evidence hashes used for this consolidation:

| Evidence | SHA-256 |
|---|---|
| `summary.json` | `d37c45c49d0b955a0829f44133d5f9b703bc3f4688210a51513b07671f723c73` |
| `supporting-rule-ledger.csv` | `e8a94911e9c76fac258f32c284361f85f1eac5fca975d3faf8b8fe5cac192b4f` |
| `case-ledger.csv` | `c67c5d07fca0643f28b865c7f1923981c787df04476bf70ee852b6c33228f016` |
| `scenario-ledger.csv` | `fe1e60768ed18b7abebab587df51701bf5951e63c9c3c4922c245f0589ec0fc7` |
| `native-gates.json` | `bf20aca098fa8cbea702e5f8151cf01612a6a2222259afcbf1a93396ad176e25` |
| `input-manifest.csv` | `ab973a7bd50c526d3b4453e71ffb5dc9415a27c3c1b6ba9ce5c4edc3bda9fb00` |
