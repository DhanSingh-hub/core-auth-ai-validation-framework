# ATL105 Chapter Training: Execution Report and Remaining Gaps

## Scope and working policy

This report supersedes the earlier duplicate snapshots and zero-code assertions.
It records the autonomous chapter continuations on `Appendix-N`. Earlier batches
were committed at the user's request: `2848fe10` and `1a76dc09`; both were later
pushed only to `Appendix-N`. The subsequent Chapter 13 processing-history,
contextual/composite and intake continuation was committed locally at the user's
request as `f18caea7`. That commit was not pushed. The new Chapter 12 continuation
is uncommitted; no other branch was changed. Existing changes and independent
Test Solution artifacts are retained for the user's final review.

The appendix A-AE review pass is not complete-spec executable coverage. Likewise,
a catalog, a Java class, a candidate match or a generated placeholder chain is not
proof that every rule in its chapter is implemented.

SME decisions and approvals remain deferred. Implement source-derivable checks
without requesting sign-off; retain source conflicts, missing context and external
dependencies as explicit `REVIEW_REQUIRED` evidence. They must not hide other
implementable work.

Primary source: [ATL105 2026-3 extracted text](../extracted_text.txt).
Current catalog inventory: **49 segments, 604 rules, zero segments trained for
intake**, as recorded in [training status](../../../training-status.json) and the
regenerated [segment execution report](SEGMENT-TRAINING-EXECUTION-REPORT.md).
The 604 rules are a catalog denominator, not a denominator for the whole spec.

## Completed work in this continuation and why it was required

### 1. Corrected Segment 102 and shared product validation with Segment 157

- Replaced incorrect fixed-width quantity/price assumptions with the source's
  variable decimal-prefix representation, including malformed-value rejection.
- Product codes are exactly three ASCII digits; Appendix F classification is
  reused rather than trusting a producer's `isFuel` flag.
- Enforced source-supported fuel ordering/uniqueness, Comdata applicability,
  102/157 exclusion and the 157 exception for product 955 above code 899.
- Added Segment 157 reconciliation against supplied Segment 100 amounts.
- Preserved the Segment 102 aggregate reconciliation conflict as review-required.
  Appendix B's 2290 and Section 12.3's 2387, including 97 separate tax, must not
  produce an invented unconditional rejection.

**Reason:** the earlier validator could reject valid variable quantities, accept
misclassified products and apply incorrect authorizer/amount assumptions.

Implementation:
[shared product validator](../../../../../src/main/java/com/coreauth/validator/canonical/ProductSegmentPayloadValidator.java),
[Segment 102 adapter](../../../../../src/main/java/com/coreauth/validator/canonical/Segment102PayloadValidator.java).
Independent tests:
[Segment 102](../../../../../src/test/java/com/coreauth/validator/Segment102PayloadValidatorTest.java),
[Segment 157/product checks](../../../../../src/test/java/com/coreauth/validator/ProductSegmentPayloadValidatorTest.java).
These tests do not close physical encoding, primary-fuel or EV correlation scope.

### 2. Repaired Segment 114, Segment 100 and Segment 105 checks

| Surface | Change | Why required |
|---|---|---|
| Segment 114 | Recursively inspect nested/repeated named SKU containers; validate required SKU, N4 length, SKU length + 10 and optional supplied wire; reject loyalty response usage | The prior implementation could skip nested observations and did not prove encoded length |
| Segment 100 | Separate the N3 Segment Length rule from the TCP header length rule; check literal 001-218; label calculation sentinels review-required | A sentinel is not measured wire evidence; the two length fields have different formats |
| Segment 105 | Calendar validation plus six source-listed special dates; sequence/count bounds; version widths; source card-label order; encoded length and optional supplied-wire agreement | Regex-only dates, zero counters and declared lengths could appear valid without satisfying the source |
| Segment 105 context | Keep merchant-history/activity/reset semantics explicit as review-required | Date codes and totals structure do not prove reset, settlement or lifecycle behavior |

Sources include Sections 12.1, 12.5, 12.13 and the relevant Chapter 13 elements.
Tests:
[Segment 100](../../../../../src/test/java/com/coreauth/validator/Segment100SerializationValidatorTest.java),
[Segment 114](../../../../../src/test/java/com/coreauth/validator/Segment114PayloadValidatorTest.java),
[Totals Request](../../../../../src/test/java/com/coreauth/validator/TotalsRequestPayloadValidatorTest.java).

### 3. Added bounded Chapter 12 numbered-element execution

[Chapter12SegmentPayloadValidator](../../../../../src/main/java/com/coreauth/validator/canonical/Chapter12SegmentPayloadValidator.java)
now dispatches observations for:

`123, 130, 131, 132, 134, 135, 136, 139, 140, 141, 142, 143, 145, 146, 148,
149, 150, 151, 152, 153, 155, 156`.

This is **22 numbered adapters**, plus the separately tested shared Segment 157
validator: bounded implementation across the previously listed 23-segment backlog,
not complete certification of those segments.

| Group | Implemented bounded checks | Still not claimed |
|---|---|---|
| 123 | Tokenization indicator, widths and supported request/response restrictions | Full authentication/correlation and eligibility |
| 130-132 | Existing Appendix R chip oracle, decoded byte limits, supported checksum/additional-data framing | Cryptographic validity, all additional information and every source conflict |
| 134-136 | CAPK/table framing and supported attributes | Actual key trust, complete load/session semantics |
| 139-142 | Moneris/SPDH widths, batch fields, numeric signed totals, MAC width | Historical echoes, close frequency, MAC authenticity |
| 143 | Product/tax count, omission and framing checks | Every tax eligibility/reconciliation dependency |
| 145-146 | TLV caps, supported authorizer restrictions and companion exclusions | All prompt masks/categories and external fleet processing |
| 148-150 | Supported indicator/field/length observations | Complete WEX restrictions, source-conflicted maxima and operational decisions |
| 151-152 | Raw section caps and declared dataset-count constraints | External InComm dataset syntax or proof that parsed declarations match raw content |
| 153 | Source-supported token table IDs and widths | Invented token alphabets, expiry format or credential validity |
| 155 | Reused Appendix AD response-layout predicates | Full opt-in, brand, optimizer and retry gates |
| 156 | Mandatory EV table, N2/N3 framing, 200-byte cap, source-listed times/reasons/connectors and context checks | Complete fleet/EV transaction lifecycle or production eligibility |

**Reason:** catalog existence and direct class-name searches had been incorrectly
equated with executable coverage. Existing generic element profiles and appendix
oracles already supplied some prior art. Those helpers were reused; remaining
checks are bounded explicitly rather than silently defaulting to PASS.

Three source-derived catalog additions changed the total from 601 to 604:
one Segment 123 width rule and two Segment 156 header/TLV rules. Their backlog
catalog denominator is now 156, previously 153.

Independent [22 fixture observations](../../../test-output/test-json/chapter-12-segment-observations.json)
and [validator tests](../../../../../src/test/java/com/coreauth/validator/Chapter12SegmentPayloadValidatorTest.java)
exercise positive structure and negative semantics. Successful observations may
remain `REVIEW_REQUIRED`; `CHECKS_PASSED` means only the stated check scope passed.

### 4. Persisted executed Chapter 12 canonical evidence

[GenerateChapter12TrainingEvidence](../../../../../src/main/java/com/coreauth/validator/coverage/GenerateChapter12TrainingEvidence.java)
produced:

- [Canonical package](../../../test-output/test-json/chapter-12-training-package.json):
  **22 BRs, 22 TSs, 44 TCs, 44 TD records**.
- [Execution evidence](../../../test-output/test-json/chapter-12-training-evidence.json):
  **44 actual validator executions**, expected/actual outcomes, findings, catalog
  gaps, canonical anchors, source/fixture SHA-256 and existing Test BR candidates.

The generator validates canonical integrity before saving and fails on an
unexpected fixture outcome. Its [persistence test](../../../../../src/test/java/com/coreauth/validator/coverage/GenerateChapter12TrainingEvidenceTest.java)
checks counts, readback and rejection of execution-ready certification.

**Boundary:** these chains cover bounded header observations, not every semantic
predicate in the validator. Existing-candidate reconciliation scans root-level
segment JSON BR packages; it is not a complete recursive/narrative artifact audit.
Candidates are not automatically confirmed matches or approvals.
`unassessedCatalogRules` lists rules without a finding; a rule with a finding can
still be partly checked or review-only.

### 5. Added Chapter 11 envelope dispatch

[Chapter11MessageLayoutValidator](../../../../../src/main/java/com/coreauth/validator/canonical/Chapter11MessageLayoutValidator.java)
loads the existing 31-family template catalog and:

- Resolves response aliases with cycle detection.
- Checks supplied top-level required/fixed/max-length fields and segment placement.
- Checks Element 63 against actual occurrences, not the number of alternatives in
  a template; applies existing standard/EMV slot limits.
- Detects supported 102/157 and 101/145 exclusions.
- Delegates supplied numbered child observations to the Chapter 12 validator.
- Keeps external TransArmor layouts and unsupported content review-required.

**Reason:** template extraction alone did not execute message-shape checks.

[Envelope evidence](../../../test-output/test-json/chapter-11-envelope-evidence.json)
records dispatch observations across all 31 families.
[Tests](../../../../../src/test/java/com/coreauth/validator/Chapter11MessageLayoutValidatorTest.java)
exercise count, placement, aliases, exclusions and partial/external boundaries.

**Boundary:** the 31 observations are partial dispatch evidence, not 31 valid
complete messages. This is not a positional/wire parser or a full conditional
trigger validator. Successful structural observations remain review-required.
The new numbered surfaces are not fully integrated into actual producer aliases
and the real AI batch intake.

### 6. Continued Chapter 10 using the existing electronic-mail validator

Added `validateProcessingObservation` to
[Segment109PayloadValidator](../../../../../src/main/java/com/coreauth/validator/canonical/Segment109PayloadValidator.java),
cross-checking existing `SEG109-R-019` and `SEG109-R-020` against Sections 10.11.1
and 10.11.2 rather than introducing duplicate rules:

- Retrieval requires Prompt Code 981; proprietary-card retrieval requires 996.
- Submission requires 995.
- Exact ASCII data limits: retrieval print data 750 bytes, submission data 217.
- Non-text prompt/data are rejected. Missing data and unknown non-ASCII transport
  encoding remain review-required, not invented byte-count results.
- Chapter 11 delegates supplied Segment 109 processing observations and rejects
  a Prompt Code contradictory to supplied Element 78.

**Reason:** the existing validator accepted the union of three prompt codes but
did not verify which operation each code belonged to. Its Segment 109 Text Data
cap of 150 is distinct from Chapter 10's aggregate submission cap of 217; those
must not be substituted for one another.

[Processing tests](../../../../../src/test/java/com/coreauth/validator/Chapter10ElectronicMailProcessingTest.java)
exercise 750/751, 217/218, operation/code mismatches, numeric coercion, unknown
encoding and parent dispatch.
[Canonical evidence generator](../../../../../src/main/java/com/coreauth/validator/coverage/GenerateChapter10ElectronicMailEvidence.java)
creates [2 BR/2 TS/8 TC/8 TD chains](../../../test-output/test-json/chapter-10-electronic-mail-package.json)
and [8 executed observations](../../../test-output/test-json/chapter-10-electronic-mail-evidence.json).
It requires exact-anchor candidates in the existing independent canonical BR
package; line-location metadata is not part of canonical identity.
Its [test](../../../../../src/test/java/com/coreauth/validator/coverage/GenerateChapter10ElectronicMailEvidenceTest.java)
checks actual outcomes, explicit error shape, persistence and traceability.

This does not implement the whole electronic-mail lifecycle, response confirmation,
host storage or the other Chapter 10 processing families.

### 7. Repaired coupled report/contract dependencies

- Segment DL7 BR003 now references open provisional item P-02, not resolved P-01.
  The existing strict open-provisional check was preserved. Both DL7 tests pass.
- Regenerated Element 84/85 profiles after the Segment 156 catalog addition.
- Regenerated the complete canonical rule-denominator package from 604 rules;
  its placeholder chains remain non-execution-ready.
- Updated the knowledge-base consistency test's expected source count to 604 and
  regenerated the segment training report.
- Extracted the existing Appendix AD response-layout checker for reuse; its
  existing 13 tests pass.
- Corrected Chapter 13 Account Number maximum/context wording and N4 length notes.

**Reason:** updating catalogs without their dependent profiles and reports created
real consistency regressions. These were fixed rather than disabling assertions.
The DL7 failure was a wrong provisional dependency, not a missing catalog schema.

## Validation record

All Maven commands ran from the module directory with installed JDK 27 targeting
Java 26. No dependency changes or new lint tooling were required.

| Validation gate | Outcome |
|---|---|
| Combined chapter/product/totals/canonical/AD/DL7 batch | 110 tests, zero failures/errors |
| Chapter 10/11 plus profile, denominator, source and status integration batch | 37 tests, zero failures/errors |
| Chapter 10 canonical evidence and dispatch batch | 11 tests, zero failures/errors |
| First full suite in this continuation | 1,699 tests; five failures, zero errors |
| Coupled regressions from that full run | Stale generated profile, canonical denominator package and 601 assertion repaired; targeted consistency tests pass |
| Final full suite | 1,705 tests: 1,703 passed, two pre-existing failures, zero errors/skips; no new failures |

The two previously failing unrelated tests are
`AppendixCoverageConsistencyTest.allAppendixRecordsMatchTheCanonicalInventory`
and `AppendixJPosEntryModeOracleTest.rejectsMalformedAndUnlistedComponents`.
They were not weakened or altered merely to obtain a green suite.
The earlier DL7 error has been fixed.

## Chapter 13 expanded domain and processing continuation

This is additional uncommitted work after the first Chapter 13 profile package.
The request is full non-SME Chapter 13 coverage. **That completion criterion is
not yet met:** passing the additional predicates below is not evidence that every
processing sentence, applicability condition and lifecycle requirement is closed.

### Completed tasks and reasons

| Task | Executed change | Why required |
|---|---|---|
| Source-local valid values | Added 55 explicit code/range/pattern domains; constructor verifies local evidence and numeric endpoints | Baseline type/maximum checks accepted zero counters and unlisted codes; a representation match is not a valid-value match |
| Dates and clocks | Added full-year, MMDD, MMYY, timestamp and Totals response checks; preserved source 01-24/01-60 clock ranges separately from 0000-2359 start/end time | Conventional clock assumptions and a global six-digit date regex can contradict this source |
| Encoding and wire distinction | Explicit WIRE fixed-width checks, ASCII byte-count limits, source-defined password padding and Store Number masking | Logical observations do not prove serialized widths; padded/masked numeric exceptions must not be falsely rejected |
| Amount and range correlation | Executed net = grand total - fee and beginning/ending BIN ordering; reused the product decimal-prefix helper | Individually numeric values can still violate cross-field formulas |
| Mail and companions | Transport-specific 750/150 limits and measured declared/data lengths; partial missing companions review, complete missing companions fail | Declared lengths and protocol-independent maxima are insufficient |
| Tax | True omission for N, mandatory companions for I/E and source-listed QST; corrected the Chapter 12 Canadian tax predicate too | The previous Canadian list omitted an actual valid source code; blank fields are not omission |
| Repeated records | Independently counted print lines, receipt lines, card types, products and Moneris key iterations | Counts for different source record families must not be substituted; each Y requires one 16-character key iteration |
| Lifecycle observations | Original sequence and authorization-reversal prompt reuse; received checksum reuse; MICR first-50 and long ECA/TeleCheck extension | Shape alone cannot detect changed follow-on identities or lost check data |
| Existing oracles | Composed the existing element profiles, D/L catalogs and I/K/R oracles; extracted the unchanged product decimal-prefix predicate | Avoided duplicated or contradictory independent implementations |
| Envelope/reference integration | Explicit `chapter13Semantics: true` mode, inherited source context and contradictory-child rejection | New checks must be callable through existing observation surfaces without silently changing their legacy default behavior |
| Evidence persistence | 149 BRs, 149 TSs, 437 TCs/TDs with actual outcomes, input hashes, source lines, existing-candidate cross-check and canonical integrity validation | Generated chains alone are not executed evidence; candidate identity is not a semantic match or approval |

Implementation:
[Chapter13DataElementValidator](../../../../../src/main/java/com/coreauth/validator/validation/Chapter13DataElementValidator.java),
[source-local domains](elements/value-domains.json),
[shared decimal prefix](../../../../../src/main/java/com/coreauth/validator/validation/Atl105DecimalPrefix.java),
[evidence generator](../../../../../src/main/java/com/coreauth/validator/coverage/GenerateChapter13DomainEvidence.java).
Evidence:
[semantic probes](../../../test-output/test-json/chapter-13-semantic-probes.json),
[canonical package](../../../test-output/test-json/chapter-13-domain-package.json),
[execution and all-element reconciliation](../../../test-output/test-json/chapter-13-domain-evidence.json).
Tests:
[domain and dependency tests](../../../../../src/test/java/com/coreauth/validator/Chapter13DataElementValidatorTest.java),
[persisted-evidence tests](../../../../../src/test/java/com/coreauth/validator/coverage/GenerateChapter13DomainEvidenceTest.java).

The persisted additional package contains **368 scalar/domain/type executions
and 69 semantic/dependency executions**: 254 target checks passed, 174 invalid
targets detected and 9 explicit reviews. It executes additional predicates for
**82 element identities**, but records **zero fully reconciled element closures**.
The earlier 110 contextual and 440 baseline probes remain a separate package;
these counts are not interchangeable or a percentage of complete Chapter 13.

Every generated chain preserves its Chapter 13 identity; no Segment 100 identity
is invented merely because a scalar test uses a synthetic observation. Existing
BR references are element-identity candidates, not automatic confirmations.
The existing canonical package has no numeric-element candidate for exercised
elements 6/56/61/100/120/193/205/212/243. All nine gaps are explicit in the report.
Other Test artifacts may contain their evidence; these rows need further
cross-package reconciliation, not fabricated matches.

### Validation and remaining work

The expanded targeted gate passed **433 tests, zero failures/errors**.
Editor diagnostics are clear and `git diff --check` passes.
The first expanded full run executed **2,098 tests**, with the two known
baseline failures plus three coupled stale-snapshot failures. Reconciliation
against local commit `2848fe10` found 15 additional existing catalog-to-element
references (Elements 24/34/84/85/196/204/238/242), not 15 invented rules. Refreshed
the inventory, reference, transaction applicability, omission, chain, approval,
dependency and limitation reports in dependency order. Updated exact snapshot
assertions to **554 catalog element references** and **13,358 omission rows**:
285 active template rows, 358 listed-but-unestablished rows and 12,715 rows with
no established applicability. This remains review evidence, not prohibition.

The repaired consistency gate passed **387 tests, zero failures/errors**.
The final full run executed **2,098 tests: 2,096 passed, two pre-existing
failures, zero errors/skips**. The remaining failures are Appendix AE's
`TD-SEG100-APPAE-29` readiness `UNSPECIFIED` and the existing Appendix J terminal
capability expectation. Neither unrelated test was weakened or changed.
No dependencies were installed, no commits were made and nothing was pushed.

**Non-SME implementation still required:** exhaustive source sentence/facet
mapping for all 231 identities; remaining conditional presence and network/card
gates; request/response field mapping in real AI intake; remaining table/composite
layouts; actual wire delimiter/byte codecs; complete settlement/active-day/reset
history, retry/confirmation behavior, CAPK assembly/replacement and receipt/site
retention/action evidence. Existing validators must be reused and independently
exercised rather than counting their filenames as closure.

**Separately deferred source/SME questions:** unresolved source conflicts
(including 169's 40-character receipt length versus 170's 20-character data cap,
99's Amex eight-byte note versus its seven-digit range, PIN/layout conflicts and
other already recorded discrepancies), source-unspecified century, reserved or
private operational assignments and approval decisions. External crypto/key
trust/merchant profiles and unavailable external layouts are separate dependency
boundaries; they are not excuses to leave source-local implementation undone.

No remaining implementation work is relabeled SME-only. Chapter 13 is still
`IN_PROGRESS`, not complete except SME.

## Chapter 13 continuation after local commit 2848fe10

This earlier continuation was included in commit `1a76dc09`. It extends the existing generic element
validator rather than creating a competing Chapter 13 oracle. Source definitions,
matching existing rule IDs and Test BR canonical anchors were cross-checked before
executed evidence was persisted.

### Changes and reasons

1. **Added 11 contextual profiles; extended one existing Sequence Number profile.**
   - Element 86's six-digit shape formerly allowed `000000`; the source requires
     `000001-999999`. Applied to Financial, Totals and Electronic Mail requests.
   - Elements 39/43/96 now execute Totals firmware/hardware/software widths.
     Hardware's 4/8 source form cannot be inferred from a single scalar maximum.
   - Totals 44/78 now enforce 0/1 and 990 within this request context.
   - Populated Employee Number 32 is N4, including source default 1111; empty
     omission is distinguished from malformed non-text data.
   - Dates 105/36/45 now receive calendar validation, not just six-digit regex
     checks. Totals requests allow only the six source-listed special codes.
     Response formatting and dates outside these contexts are not guessed.
2. **Reused a shared calendar representation helper.** The legacy Totals helper was
   extracted without changing its prior acceptance. Generic profiles explicitly
   return review-required for year-00 leap day until century evidence exists.
   Profile initialization rejects unsupported calendar formats and ungrounded
   special codes instead of silently ignoring bad configuration.
3. **Wired the profiles into Chapter 11's explicit observations.** Supplied numbered
   child observations for 100/105/109 and Electronic Mail response top-level
   elements now receive profile checks. Parent version/family/segment are
   authoritative; conflicting child declarations are errors. This does not add
   complete AI producer field mapping or serialized-message certification.
4. **Persisted independently executed canonical evidence.**
   [Probes](../../../test-output/test-json/chapter-13-element-probes.json),
   [package](../../../test-output/test-json/chapter-13-training-package.json),
   [execution report](../../../test-output/test-json/chapter-13-training-evidence.json):
   **12 BRs, 12 TSs, 110 TCs and 110 TD records**, with 110 actual contextual
   executions. Target outcomes: 32 checks passed, 63 invalid, 15 review-required.
   Whole-observation status is separate; partial observations never become
   certified messages. Exact existing Test BR anchor candidates are mandatory,
   but no match or approval is automatically confirmed.
5. **Reconciled all source identities.** There are **231 distinct elements and 232
   definitions**, including the repeated 118. Ran **440 baseline probes** across
   220 extractable single definitions; successful maximum-length/numeric-shape
   baselines remain review-required. Eleven baseline ambiguity/unextracted rows
   are explicitly retained: 2/33/43/51/94/118/183/184/202/203/243. A baseline gap is
   not proof that an existing segment/appendix oracle has no implementation.
6. **Refreshed the existing inventory and related reports.** Its command-line
   entry point now normalizes the pack to an absolute path before finding the
   module/legacy resources; the normal module-relative invocation previously
   failed on a null parent path. Shared canonical identity projection preserves
   the six contract identity fields while excluding line-location metadata.

### Validation and limits

- Source/profile/evidence/parent-dispatch/Totals/Chapter 10 integration batch:
  **47 tests, zero failures/errors**.
- Final targeted batch, including the added default inventory CLI regression:
  **48 tests, zero failures/errors**.
- Chapter 13 generator checks exact 231/232 source counts, 79 current profiles,
  110 contextual probes, 440 baseline probes, persisted JSON readback and
  canonical traceability. Execution-ready certification is explicitly rejected.
- Full integration suite: **1,714 tests; 1,712 passed, the same two pre-existing
  failures, zero errors/skips**. No new integration failures.

The catalog denominator remains 604; no catalog rules or approvals were added.
There are now 79 contextual profiles, not 79 fully trained elements. The 12
executed profile chains span ten element identities and are bounded representation
evidence. Full valid-value, conditional applicability, encoding and lifecycle
closure still require additional source-backed work.

## Honest chapter status

### Chapter 13 processing-history and intake work (local commit f18caea7)

This batch closes specific execution gaps, not the whole Chapter 13 gate.

| Work completed | Why the change was required |
|---|---|
| Source-local history/action predicates for 98/105/170/175/180/193/194 | Valid values alone did not prove masking actions, active-date eligibility, settlement ending/roll, reset boundaries, receipt retention/output, table replacement, final daily configuration or CAPK assembly/replacement |
| Authoritative parent history on both envelope and element-reference paths | A contradictory child could otherwise bypass the observed parent context; tests verify inheritance, rejection and input immutability |
| Opt-in actual-payload AI semantic intake | Legacy metadata values could differ from actual payload values and were not sufficient execution evidence |
| Exact source-family roots and scoped source-name mappings | Real artifacts use more than the reviewed shorthand `Financial Request`; unknown aliases must still remain reviews |
| Iteration-aware actual segment evaluation | Collapsing repeated segments would lose evidence; unsupported mapping is not itself a source prohibition |
| Per-case input hashes and real-batch execution | A synthetic adapter test is not evidence that real AI payloads have been evaluated |

Implementation:
[history validator](../../../../../src/main/java/com/coreauth/validator/validation/Chapter13ProcessingHistoryValidator.java),
[AI intake](../../../../../src/main/java/com/coreauth/validator/validation/Atl105AiElementIntake.java).
The [Chapter 13 guide](13-data-elements.md) documents accepted history fields,
CLI opt-in and retained limits. The original constructor/default intake remains
unchanged. Successful partial observations are not certified messages.

The refreshed [canonical package](../../../test-output/test-json/chapter-13-domain-package.json)
contains **251 BR / 251 TS / 711 TC / 711 TD chains**; the
[execution report](../../../test-output/test-json/chapter-13-domain-evidence.json)
contains **466 scalar + 245 semantic/context/composite/history executions**:
372 target checks passed, 317 invalid, 22 review-required. This adds 233 probes
and 85 predicate chains to the previous 478-execution package.
Canonical traceability validates; execution-ready
approval remains explicitly false.

Additional predicates now exercise 129 of the 231 identities; none is newly
declared a fully reconciled semantic closure. All 231 identities/232 definitions
are retained in reconciliation. Existing complete Test BR artifacts are still
cross-checked, together with 38 supplemental Test packages. The supplemental
scan excludes all Chapter 13 outputs to prevent circular self-validation; hashes,
skip reasons and unconfirmed candidate status are retained. Twelve exercised
identities lack numeric-element candidates after both cross-checks:
6/38/56/61/100/101/120/193/194/205/212/243. This is a candidate-reconciliation
gap, not proof of missing source implementation or an SME-only blocker.

[Actual AI intake report](../../../test-output/test-solution-independent-review/chapter-13-ai-semantic-intake-2026-09-29-phase1.json):
37 cases assessed from the 2026-09-29 phase-1 batch; 32 have mapped observations,
5 retain unsupported root-label reviews. Their actual labels are Auth Completion
(0220), Financial Transaction (three cases) and EMV Request; direction/family is
not guessed. The report has 37 per-case input-hash rows, 29 invalid and 8
review-required case verdicts, and 253 observations
(64 invalid, 189 review-required). All 74 producer files were hash-verified
unchanged. These cases are labeled `field_constraint`; the results are not a
measured positive/negative detection rate. Examples of defect candidates include
non-text Prompt Code objects, metadata/payload contradictions, missing EMV
envelope counts and malformed chip-data hex/TLV. No values were copied into the
report and no matches were auto-confirmed.
Report paths inside the producer input folder are explicitly rejected, including
attempts to overwrite metadata. A regression test verifies that protection.

Previous history-only focused validation: **429 tests, zero failures/errors/skips**.
Previous history-only full regression:
**2,126 tests; 2,124 passed, two pre-existing failures, zero errors/skips**.
The unchanged failures are AppendixCoverageConsistencyTest (Appendix AE
test-data readiness `UNSPECIFIED`) and
AppendixJPosEntryModeOracleTest.rejectsMalformedAndUnlistedComponents (terminal
capability expected invalid, received pass). Neither was weakened or relabeled
as passing. The element inventory is regenerated against the expanded package;
these remain structural artifact references, not full semantic closures.

### Latest contextual, operational and composite implementation

| Work completed | Why it was required |
|---|---|
| 14 additional explicit scalar domains, including zero-inclusive amounts and nonzero count limits | Character type/maximum length did not enforce the source's numeric endpoints |
| Segment/family-scoped required versions, passwords, pump, manual-check, loyalty and EMV fields | Conditional requiredness could otherwise be omitted or incorrectly applied to unrelated siblings |
| Account/track layouts, exact echoes, retry/approval, partial approval, fee and remaining-balance correlations | Valid individual values do not prove correct combined limits or transaction use |
| Card/response families, tokenized network gates, SafeKey and last observed interface TLVs | Overloaded codes and producer qualifiers cannot independently establish applicability; parent response family is authoritative |
| Observed key reuse, merchant currency, print output, dial ordering, connection and cut scheduling | Declaration of an action is not evidence that its ordering/output matches supplied observations |
| Composite decoding for 33/153/154/164, using existing Segment 103 checks | Metadata length and named fields did not prove actual nested TAG/LEN/body contents |
| Safe root aliases and actual unique DL indicators | Genuine DL identity should not be guessed from conflicting metadata, nor discarded when independently identifiable |
| Supplemental Test-package candidate cross-check and 245 executable semantic fixtures | New Test BR chains must be checked against existing artifacts without circular reconciliation or approval by alias |

See [context implementation](../../../../../src/main/java/com/coreauth/validator/validation/Chapter13ContextValidator.java),
[composite implementation](../../../../../src/main/java/com/coreauth/validator/validation/Chapter13CompositeValidator.java),
[independent dynamic fixture tests](../../../../../src/test/java/com/coreauth/validator/Chapter13SemanticProbeTest.java)
and [boundary/control tests](../../../../../src/test/java/com/coreauth/validator/Chapter13ContextAndCompositeTest.java).
The expanded focused integration gate passed **819 tests with no
failures/errors/skips** before the final family/clock/EF boundary hardening.
The subsequent full regression ran **2,483 tests: 2,481 passed, two failures,
zero errors/skips**. Both failing tests are the known appendix baseline:
AppendixCoverageConsistencyTest (artifact readiness `UNSPECIFIED`, including AE)
and AppendixJPosEntryModeOracleTest.rejectsMalformedAndUnlistedComponents
(terminal-capability expectation). After the final EF-framing and missing-SafeKey
guards, the smallest covering **273-test suite passed, with zero
failures/errors/skips**. Persistent package/evidence and actual AI intake were
regenerated after that gate; all 74 producer files remained unchanged. The full
suite was not rerun after these last two guards.

The WIC purchase worked example's declared `PS034` differs from its measured
37-character body; tests use the positional tables rather than weakening the
oracle. EF's N3 framing is not established by its eight-byte date descriptor.
Unselected EF framing is explicitly review-required. A selected `TAG_N3` profile
enables measured structure/calendar checks but does not certify that profile.
Source hour 24/minute 60 similarly remains unnormalized when correlating ISO cut
timestamps. Transformed EDATA is never subject to the plaintext stored-value PAN
width. These are explicit boundaries, not silent success-shaped fallbacks.
SafeKey Secure ID 25/26 also rejects omission of mandatory Element 203 from a
complete Segment 123 observation, reports partial omission as review-required,
and leaves unrelated siblings unaffected.

### Remaining Chapter 13 exit work

| Remaining work | Classification and required evidence |
|---|---|
| Reconcile every representation, valid-value, applicability and processing clause for all 231 identities/232 definitions | Implementation/evidence work; 129 exercised identities and 711 probes do not establish clause completeness |
| Complete WIC response-required subelements, earliest-benefit selection, APL adjustments and settlement correlation | Implementable context/history rules; selected bitmap structure is insufficient |
| Complete remaining conditional/card/network gates and wire/table layouts using existing appendix/segment oracles | Implementation/integration work; reuse existing rules before adding duplicates |
| Resolve supported structured actual producer fields and unambiguous family/occurrence mapping | Intake work; retain explicit reviews for unresolved labels instead of guessed matches |
| Reconcile the twelve existing-BR candidate gaps | Cross-artifact evidence work; do not silently approve new chains or relabel gaps SME-only |
| Cryptographic/key authenticity, device/merchant assignment, genuine source contradictions and unsupported framing/clock conventions | External/source-boundary evidence; approvals remain deferred |

No full-semantic element closure is asserted by this package. In particular,
**Chapter 13 is not complete even with SME approvals excluded**.

**Chapter 13 remains IN_PROGRESS.** Remaining source-facet reconciliation, conditional/network/card gates,
composite/table layouts and physical encoding are implementation work. Genuine
source conflicts and external trust evidence remain separate deferred boundaries.
The user explicitly authorized moving to Chapter 12 with this backlog carried
forward; Chapter 13 was not marked complete.

## Chapter 12 next-phase start after local Chapter 13 commit

The user requested committing the verified Chapter 13 batch on `Appendix-N`
and starting Chapter 12. Local commit `f18caea7` contains that Chapter 13 batch;
the following Chapter 12 changes are separate, uncommitted work.

### Segment 115 bounded field, wire and contextual execution

[Chapter12SegmentPayloadValidator](../../../../../src/main/java/com/coreauth/validator/canonical/Chapter12SegmentPayloadValidator.java)
now includes Segment 115, bringing its supported numbered-observation adapters
from 22 to 23. New checks cover fixed type 115, N4 declared length, required
textual Print Data, actual ASCII segment length, prescribed three-field order
and two field separators. The selected Section 12.14 layout does not append a
separator to Print Data. Request usage, nonfinancial response families, false
following-data flags, original request loyalty version other than textual `2`,
missing/empty/nontext print data and malformed context controls are rejected.
Absent operational/request evidence remains explicitly review-required.

[Chapter11MessageLayoutValidator](../../../../../src/main/java/com/coreauth/validator/canonical/Chapter11MessageLayoutValidator.java)
dispatches Segment 115 through that adapter. Parent message family, derived
request/response direction and supplied Element 115 flag are authoritative;
contradictory child values are rejected. Input immutability is tested.

**Why required:** the earlier envelope only assessed Segment 115's presence.
It did not execute print content or wire serialization, and the Chapter 12
adapter did not support this segment.

### Retained source boundaries

- Section 12.14 states a 1,009-byte segment cap and Print Data length 999.
  Chapter 13 Element 152 states 900; Section 11 Financial/EMV envelope tables
  state 910/999. The 901-999 print-data interval is an explicit source-conflict
  review, not an unconditional pass or rejection. Over 999 print characters
  is invalid under every stated print-field limit.
- The Section 12.14 no-other-segments statement conflicts with Section 11's
  listed companions. No blanket companion exclusion is inferred.
- Header/field observations do not establish host origination, complete
  receipt content, all response inclusion conditions or operational printing.
- Segment 119 source reconnaissance found inconsistent bucket numbering
  (17-19 versus 18-20) and delimiter prose. The subsequent continuation below
  adds a selected-profile validator while preserving that conflict.

These boundaries do not make all remaining Chapter 12 work SME-only.

### Cross-artifact evidence and verification

The [training generator](../../../../../src/main/java/com/coreauth/validator/coverage/GenerateChapter12TrainingEvidence.java)
now cross-checks the existing complete independent Test package as well as
segment packages, records their SHA-256 hashes, and separates source line
metadata from the six canonical identity fields. Empty exact-anchor results
are explicitly labeled `NO_EXACT_ANCHOR_CANDIDATE`; no approximate match or
approval is manufactured. Segment 115's header matches the existing
`BR-RULE-SEG115-R-003` anchor as an unapproved candidate.

The regenerated [canonical package](../../../test-output/test-json/chapter-12-training-package.json)
contains **23 BR / 23 TS / 46 TC / 46 TD records**. The
[execution evidence](../../../test-output/test-json/chapter-12-training-evidence.json)
contains **46 actual observations**, including Segment 115's independent
positive structure (whole status `REVIEW_REQUIRED`) and wrong-type negative
(`INVALID`). Traceability validates; approved requirements remain zero.
This header package is not a complete per-rule Chapter 12 coverage claim.

The focused gate passed **33 tests, zero failures/errors/skips**, covering
the Chapter 12 adapter, Chapter 11 parent integration, evidence generation,
producer-neutral contract and repository layout. Boundary tests cover print
lengths 900/901/999/1000, trailing delimiters, missing/nontext fields, wrong
directions/families, context controls, authoritative flags and input immutability.
No full-suite run or real-AI Chapter 12 execution is claimed for this initial batch.

### Segment 119 field, card-bucket and selected-wire continuation

This uncommitted continuation adds
[Segment119ObservationValidator](../../../../../src/main/java/com/coreauth/validator/canonical/Segment119ObservationValidator.java)
to the shared Chapter 12 adapter (now **24 supported numbered segments**).
Source Sections 11.4.1.2/12.17 and Chapter 13's referenced definitions were
cross-checked with the existing Segment 119 catalog and Test BRs before execution.

| Implemented | Why required |
|---|---|
| Required 119/N3 header, 0/1 Information Byte, terminal layout/state, 990 prompt, conditional employee/password and request family/direction | The earlier Segment 119 path assessed its envelope, not its individual fields |
| Request Totals Date/calendar/special codes; N4 hardware, N8 software/firmware, nonzero N6 sequence, N35 table version, real full-year timestamp, optional source-listed currency and N8 total | Text length alone can accept impossible dates, zero sequence, missing fields or malformed values |
| Explicit ordered `cardBuckets`, padded N4 labels, N5 nonzero request counts, N8 amounts, no duplicate/reversed source labels | Repeated values must be assessed individually; response-only inclusion prose must not be imposed on requests |
| Complete supplied bucket-scope total reconciliation excluding AO/SV1/SV3/SV4/HD | Including nonfinancial amounts can silently overstate Grand Total; incomplete scope cannot establish equality |
| Measured ASCII total length and explicitly selected `TABLE_17_19` serialization | Declared lengths do not prove encoding; ambiguous source prose cannot establish one universal wire profile |
| Authoritative Chapter 11 parent family/direction/placement with child contradiction rejection | A child could otherwise report a false family or data section independently of its actual envelope |
| Optional per-predicate evidence probes in the existing generator | Header chains alone do not show executed date, timestamp, sequence, bucket, total or wire predicates |

The [independent observations](../../../test-output/test-json/chapter-12-segment-observations.json)
contain **18 Segment 119 predicate probes**, with **7 passed targets, 9 invalid
targets and 2 explicit review targets**, plus positive-header and wrong-type
observations. Whole observations remain distinct from target results.
The refreshed [canonical package](../../../test-output/test-json/chapter-12-training-package.json)
contains **42 BR / 42 TS / 66 TC / 66 TD records**; the
[execution report](../../../test-output/test-json/chapter-12-training-evidence.json)
contains **66 actual executions**, including **20 Segment 119 executions**.
All 20 retain exact existing Test-anchor candidates, with no approval or
automatic AI semantic confirmation. Canonical traceability validates and all
expected/actual whole and target results match.

The focused **51-test gate passed with zero failures/errors/skips**, covering
Segment 119, the shared Chapter 12 adapter, Chapter 11 parent dispatch, canonical
evidence, legacy Totals Request behavior, producer contract and repository layout.
Tests exercise missing/nontext required fields, conditional controls, calendar
and leap-day boundaries, sequence/count zero, duplicate/reversed buckets,
amount shape, complete/incomplete totals, retained empty-field separators,
wrong measured length and parent-context immutability. All 19 source-listed
labels form an independently measured **453-byte** selected-profile segment;
declared **389/390/493/494** boundaries are explicitly tested.

The independent base fixture initially declared 157 but measured 147 bytes.
Validation caught this; the fixture declaration was corrected to its actual
byte count. No validator was weakened to accommodate the incorrect fixture.

#### Still not closed

- Section 11 cap 389 versus Section 12 cap 493, and separator-note 18-20
  versus table 17-19, remain genuine source conflicts. Selected-profile checks
  do not certify the profile; unselected wire remains review-required.
- The prose allows up to 20 buckets but names 19 labels. No missing label is
  fabricated. Approved-response bucket conditions are not unconditional
  request requirements.
- Totals activity eligibility, sequence lifecycle, request/response history,
  duplicate/retry, settlement cutoff, authentic table versions/timestamp
  provenance and complete selection policy remain unclosed.
- The legacy named Totals Request validator and actual AI adapter are not newly
  wired to these field predicates in the original batch. The subsequent
  actual-payload integration below adds opt-in execution; complete producer
  mapping remains unclosed.
- No full-suite run is claimed for this batch. Chapter 12 and Chapter 13 both
  remain IN_PROGRESS. No commit/push was made for this continuation.

### Segment 119 actual-payload and totals-history integration

The previous bounded batch was committed/pushed as `85897874`. This new batch
is uncommitted on `Appendix-N`.

- Added the [actual-payload adapter](../../../../../src/main/java/com/coreauth/validator/canonical/Segment119ActualPayloadAdapter.java):
  named fields and repeated `CategoryTotals` are mapped from the actual payload,
  not metadata. A single flat bucket triple is also supported; simultaneous
  flat and array forms are explicitly invalid. Missing/nontext fields are not
  coerced. Unknown aliases remain review-required. Inputs remain unchanged.
- Added the explicit adapter/evidence overload to the
  [legacy Totals Request validator](../../../../../src/main/java/com/coreauth/validator/canonical/TotalsRequestPayloadValidator.java).
  Its original default behavior is preserved, rather than breaking earlier
  envelope-only fixture acceptance or silently imposing strict field validation.
- Wired every actual Segment 119 occurrence through this adapter in
  [semantic AI intake](../../../../../src/main/java/com/coreauth/validator/validation/Atl105AiElementIntake.java).
  Existing semantic opt-in enables the new predicates; default intake remains
  unchanged. Parent payload family is authoritative. Predicate outcomes,
  including successful checks, are retained in intake findings.
- Reused Chapter 13's processing-history validator for observed totals activity,
  date correlation, ending/roll, reset and accumulation behavior. Explicit
  request direction is authoritative; contradictory/malformed evidence is
  invalid. Context/history is supplied evidence, not authenticated host proof.
- Expanded the canonical generator with six actual-adapter probes, including
  positive and negative timestamp, zero sequence, nontext bucket count and
  history-selected response date checks. The target rule is recorded separately
  from the catalog anchor and whole observation. Source/catalog chains remain
  unapproved.

**Why required:** numbered fixtures alone did not demonstrate that actual named
payloads reached the Segment 119 rules. Flat/repeated producer shapes and
history dates could otherwise be skipped or falsely supplied by metadata.

The regenerated [canonical package](../../../test-output/test-json/chapter-12-training-package.json)
contains **48 BR / 48 TS / 72 TC / 72 TD records**. The
[execution evidence](../../../test-output/test-json/chapter-12-training-evidence.json)
contains **72 executions**, including **26 Segment 119 executions** and six
actual-adapter observations. All Segment 119 chains retain exact existing Test
anchor candidates. Expected/actual whole and target results match; traceability
validates. These are bounded predicate executions, not full semantic closure.

The [real AI intake report](../../../test-output/test-solution-independent-review/chapter-12-segment119-ai-intake-2026-09-29-phase1.json)
assesses the unchanged 2026-09-29 phase-1 batch: **37 cases**, **29 invalid /
8 review-required**, with **253 generic semantic observations**. New Segment 119
checks execute on actual case `TC-000037` in addition to those generic observations.
Do not interpret those 253 observations as Segment 119 execution counts.
All **74 producer input files** were hash-verified unchanged. Findings retain
rule IDs and sampled case IDs without copying AI values. This batch's
`field_constraint` labels do not establish a positive/negative detection ratio.

Initial focused validation passed **86 tests with zero failures/errors/skips**.
The final flat-bucket mapping guard is tested separately after inspecting the
actual producer shape; final validation results are recorded below.

Final covering validation passed **87 tests with zero failures/errors/skips**.
The full suite during this batch ran **2,501 tests: 2,499 passed, two known
appendix failures, zero errors/skips**. The failures are
AppendixCoverageConsistencyTest.allAppendixRecordsMatchTheCanonicalInventory
(readiness `UNSPECIFIED`) and
AppendixJPosEntryModeOracleTest.rejectsMalformedAndUnlistedComponents
(terminal-capability expectation). No new failure was found or assertion
weakened. This full run preceded the final flat-bucket guard; its final behavior
was validated by the 87-test gate and both reports regenerated afterward.

The final actual case `TC-000037` produces **16 passed Segment 119 findings,
7 invalid findings and 9 review findings**. Invalid candidates include actual
use under a Financial Transaction Request rather than the dedicated totals
family, absent N3 length, malformed terminal/prompt, eight-byte hardware instead
of four and short Device Card Table Version. Its flat bucket is now mapped and
assessed, not incorrectly reported missing. Overall batch case verdicts remain
29 invalid / 8 review-required; finding counts are not whole-message pass rates.

Still remaining: complete producer alias reconciliation, parent history on every
numbered/envelope surface, independently executed Segment 119 reset/roll facets,
sequence/retry/cutoff and table/timestamp provenance correlations. Genuine
389/493 and delimiter numbering conflicts remain separate source boundaries.
Neither Chapter 12 nor Chapter 13 is newly declared complete.

Source line ranges refer to [extracted text](../extracted_text.txt), not Java code.
No substantive chapter is newly declared fully covered by this continuation.

| Chapter | Source lines | Current evidence and remaining scope |
|---|---|---|
| 1 Introduction | 1688-1818 | Reference narrative; not a substitute for protocol coverage |
| 2 Message Flow Overview | 1819-2572 | Existing multistep timeout/reversal checks; late approvals, unrecognized responses and complete TOR correlation remain |
| 3 Specifications Requirements | 2573-2646 | Not wholly N/A: Table 010 TPP/VAR, Table 018 unique terminal identity and telecom thresholds are testable; independent chapter closure remains |
| 4 Supported Industries | 2647-2694 | Reference inventory; context must remain available to processing rules |
| 5 Supported Payment Network Types | 2695-2728 | Existing payment-network oracle; complete applicability reconciliation remains |
| 6 PIN Encryption | 2729-3040 | Full bounded PIN/KSN source reconciliation remains; HSM/key authenticity is external |
| 7 Network Management | 3041-3071 | Existing communications validator implements related Section 11.6 formats; direct Chapter 7 anchors/chains and full chapter reconciliation remain |
| 8 Store-and-Forward | 3072-3286 | AFP/restaurant/lodging behavior remains; external references do not make source-local retry/approval rules untestable |
| 9 End-of-Day | 3287-3780 | Improved totals field checks; activity dates, local totals, reset and settlement lifecycle remain |
| 10 Processing Requirements | 3781-7444 | New 10.11 bounded evidence plus existing partial approval, eWIC, incremental/stored-credential/EMV slices; comprehensive family processing remains |
| 11 Message Formats | 7445-10844 | New 31-family partial dispatch; complete wire/conditional/positional and canonical-message closure remain |
| 12 Data Segment Formats | 10845-17699 | Bounded additions above and existing validators; full rule, lifecycle, serialization and intake closure remain |
| 13 Data Element Descriptions | 17700-25439 | 231 identities/232 definitions reconciled, 79 contextual profiles, 110 contextual/440 baseline and 711 domain/context/composite/history executions over 129 identities; 32/37 actual AI cases mapped; complete source-facet reconciliation remains |
| 14 Support/Testing/Certification | 25440-25504 | Process/certification evidence; no claim of host/network certification |

## Remaining execution plan, SME approvals deferred

1. Continue Chapter 12 as explicitly requested, while retaining Chapter 13's
   source-facet backlog as incomplete rather than treating a documentation review
   as an executable gate.
2. Close Chapter 12's remaining nonblocked semantics and wire surfaces, including
   115/119, product physical encoding, fleet prompts, WEX and EMV correlation.
   Convert each checked predicate to independently executed canonical evidence,
   not just one header chain per segment.
3. Complete Chapter 11 conditional requirements and serialized envelopes; wire
   actual producer fields through the intake adapter, retaining independent
   source-based verdicts and explicit alias-only candidate status.
4. Continue Chapter 10 families from existing oracles: credit, debit, PINless,
   signature debit, EBT, stored value, check, loyalty, software updates,
   tokenization and EMV. Section 10.6 fleet and 10.12 TransArmor external source
   boundaries remain explicit; no source-owner decision is fabricated.
5. Extend Chapters 2/8 lifecycle: timeout/late/unrecognized responses, TOR
   correlation, local AFP `LA0001` sequence, retry/reformat limits, exception logs,
   and queue/download ordering where the source is explicit.
6. Implement bounded Chapter 6 structures without processing real clear PINs or
   keys. Record the Chapter 6.7 fixed-20 vs Element 33 16-20 KSN conflict; actual
   cryptographic/HSM certification stays outside this oracle.
7. Extend Chapters 7/9 with direct anchors, request/response and history-driven
   totals activity/reset assertions.
8. Reconcile reference/process chapters. In particular, Chapter 3 requires
   boundary tests: **more than 1,500** monthly transactions requires dial backup;
   **more than 500,000** requires at least two diverse connections, automatic
   rerouting, distinct carriers and distinct data centers. Do not change strict
   `>` to `>=` or report the whole chapter N/A.
9. Re-run full integration and reconcile the final source-to-rule-to-BR-to-TS-to-
   TC-to-TD-to-execution matrix before review/commit.

## Source conflicts and external boundaries retained

- Segment 102 amount reconciliation: Appendix B vs Section 12.3.
- Segment 119 maximum: Chapter 11 389 vs Chapters 12/13 493.
- Segment 130 maximum: 9999 vs 3043; checksum optional/required conflict.
- Segment 131 maximum: 3850 vs 3834; Data Section 2/3 placement conflict.
- Segments 134/151/152 N4 tables vs the global seven-segment list.
- Segment 148 maximum 23 vs the stated 001-999 range.
- Segment 151 maximum 2309 vs 3334 and source-direction conflict.
- Segment 155 status table 005 length 3 vs maximum 4.
- Segment 115 print/total length, inclusion and companion-segment conflicts.
- Chapter 6.7 vs Element 33 KSN length.
- External TransArmor, InComm/fleet formats, cryptographic authenticity and
  production eligibility/history require their own evidence.

These are recorded review items, not blanket reasons to stop other work.
No approvals, confirmed producer matches, full-spec coverage or production
certification were created by this pass.
