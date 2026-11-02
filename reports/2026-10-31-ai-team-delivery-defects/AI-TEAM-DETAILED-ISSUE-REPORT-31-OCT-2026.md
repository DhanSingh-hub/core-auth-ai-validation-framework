# Detailed issue report - AI artifact run of 31 October 2026

**Prepared:** 02 November 2026  
**Audience:** AI generation team, Test Model team, BA/SME and QA execution team  
**Delivery:** 31-10-out  
**Baseline:** ATL105 2026-4  
**Original-delivery disposition:** Changes required  
**Suggested ownership:** Proposed below; assignment to be confirmed by the teams.

## 1. Executive summary

The 31 October delivery is a substantive correction of the 29 October artifacts,
but it is not ready for unrestricted execution or complete semantic acceptance.

Eight issues are recorded: three High and five Medium priority. Four describe
confirmed artifact/conformance defects; the remaining four describe execution,
consistency and coverage questions requiring evidence rather than assumed fixes.
Priority reflects impact on delivery acceptance, not security severity.

The main confirmed issues are stale traceability references, incorrect rendered
EMV Card Sequence Number width, obsolete embedded delivery content and reassigned
historical TC IDs.

| Package | Scenarios | Scenarios with TCs | TCs | Physical TD files |
|---|---:|---:|---:|---:|
| Amex Credit | 36 | 36 | 416 | 790 |
| Interlink Debit | 28 | 21 | 278 | 536 |
| Maestro Direct Debit | 28 | 21 | 278 | 536 |
| Mastercard Credit | 36 | 36 | 450 | 839 |
| Mastercard Fleet | 24 | 24 | 299 | 569 |
| **Total** | **152** | **138** | **1,721** | **3,270** |

### Important status distinction

This report describes findings in the **original AI delivery**. A later,
separate corrected copy was produced by the Test Model team:

- 500 Element 188 values and associated segment lengths were corrected.
- Trace references were rebuilt from final TD paths.
- Stale embedded archives were preserved outside deliverable packages.
- Build-qualified identities were added.
- Independent deterministic gates pass for the five corrected packages.

Those repairs do **not** close the upstream generator defects. The producer
source was not available in the Test Model repository. The corrected copy remains
pending review, execution readiness remains BLOCKED, and full semantic validity
remains NOT_CERTIFIED. Original input files were not overwritten.

## 2. Issue summary and ownership

| ID | Priority | Classification | Proposed owner | Upstream disposition |
|---|---|---|---|---|
| O31-01 | High | Confirmed traceability defect | Traceability/export pipeline | Open; copy repaired |
| O31-02 | High | Confirmed rendered-data defect | Value generation and serializer | Open; copy repaired |
| O31-03 | High | Confirmed packaging/documentation defect | Delivery packaging pipeline | Open; copy corrected |
| O31-04 | Medium | Confirmed historical identity defect | TC identity and review ledger | Open; qualified IDs added to copy |
| O31-05 | Medium | Runtime dependency blocker | Harness and EMV integration | Open |
| O31-06 | Medium | Execution/readiness evidence gap | Harness, client-input and QA teams | Open |
| O31-07 | Medium | Inline/rendered consistency review | Composer and renderer | Open; copy reconciled for observed return sequence |
| O31-08 | Medium | Coverage/source applicability review | BR mapping team and BA/SME | Open |

Counts overlap. Do not sum issue counts into distinct defective TCs. In
particular, most EMV field failures also have broken trace references.

## 3. O31-01 - Matrix references do not resolve to the delivered TD files

**Priority:** High  
**Confirmed scope:** 1,529 physical-TC primary trace rows, repeated in nested
and flat matrix representations.

| Package | Affected TC references |
|---|---:|
| Amex Credit | 374 |
| Interlink Debit | 249 |
| Maestro Direct Debit | 249 |
| Mastercard Credit | 408 |
| Fleet | 249 |
| **Total** | **1,529** |

### Reproduction

1. Locate Mastercard TC-001722 in the delivered TC JSONL export.
2. Resolve its primary TD reference relative to the package root. The current
   test_data file exists.
3. Locate the same TC in the full traceability matrix.
4. Resolve the matrix's qe_shaped_test_data reference relative to the same root.
   The referenced legacy location is absent.
5. Inspect nested file_exists and FULLY_TRACED/summary claims.

**Actual:** The matrix describes a different file location and can assert
file_exists=true for a path unavailable in the delivered package.

**Expected:** Every trace reference resolves to final delivered bytes and agrees
with primary/all-leg TC and run-set references.

**Business impact:** A reviewer or automated consumer following the matrix cannot
reconstruct the claimed BR-to-data chain. The 100% metric can be mistaken for
complete coverage even though it uses the one-scope-BR denominator.

**Root-cause hypothesis:** Trace construction retained intermediate producer
paths rather than final export references. This is an evidence-supported
hypothesis, not a verified producer-code diagnosis.

**Required correction and acceptance**

- Build nested, flat, Markdown and any CSV trace surfaces from final file paths.
- Validate all primary and leg references inside the actual export.
- Assert file_exists from exported bytes, not a producer working directory.
- Publish denominator-specific coverage; do not call scope linkage semantic
  specification coverage.
- Mutation test: replace one path with a nonexistent file; the gate must fail.

**Later copy repair:** Completed for final paths and all-leg mapping. Producer
regeneration and independent acceptance remain required.

### Developer investigation and implementation sequence

1. Reproduce against the original export, not the later corrected copy. Use the
   package root as the reference-resolution base.
2. Compare the TC export, run set and matrix entry for TC-001722. Record the
   actual reference strings and existence results without including card data.
3. Identify the producer stage that writes legacy references into the matrix.
   The producer code was unavailable during this review; the precise function
   and root cause still need developer confirmation.
4. Generate trace entries after final TD placement, using the same final
   references as the TC/run-set export rather than reconstructing legacy paths.
5. Apply the correction to nested, flat, Markdown and any CSV representations.
   Include every additional request leg, not only the primary file.
6. Validate the packaged output independently and regenerate coverage summaries
   from the validated population.

### Concrete acceptance checks

| Test | Required result |
|---|---|
| Resolve every matrix TD reference from its package root | No missing or out-of-package target |
| Compare TC/run-set and matrix references for the same TC and leg | Same authoritative delivered target |
| Validate every additional request leg | No omitted leg or substituted primary-only mapping |
| Remove a referenced TD file from a test fixture | Validation fails; existence is not reported as true |
| Replace a matrix path with an absent legacy path | Validation fails even if the correct TD exists elsewhere |
| Review published coverage | Denominator and metric meaning are explicit; linkage is not semantic certification |

**Evidence to return:** Fresh producer export, per-reference validation results,
all-leg reconciliation, updated coverage definitions and regression-test results.

## 4. O31-02 - Element 188 is rendered with two characters instead of three

**Priority:** High  
**Confirmed scope:** 500 EMV request files/legs affecting 402 distinct TCs.

| Package | Files/legs | Distinct TCs |
|---|---:|---:|
| Amex Credit | 116 | 94 |
| Interlink Debit | 90 | 72 |
| Maestro Direct Debit | 90 | 72 |
| Mastercard Credit | 126 | 104 |
| Fleet | 78 | 60 |
| **Total** | **500** | **402** |

### Reproduction

1. Open the emitted request for Mastercard TC-001722.
2. Inspect EMVCardSequenceNumber in Segment 130.
3. Compare it with tag 5F34 and ATL105 Element 188.

**Actual:** A value such as 01 is rendered as two characters.

**Expected:** ATL105 Element 188, PDF page 477, requires three bytes:
right-aligned, zero-padded numeric 000-099, or three spaces when 5F34 is absent.
For tag value 01, the corresponding representation is 001.

**Impact:** The provided request JSON does not implement the stated field
representation. No particular host decline code has been inferred.

**Qualification:** EMV TLV structure, chip lengths and packed-BCD table prefixes
passed the checks. The two-byte packed-BCD prefix is not this defect.

**Required correction and acceptance**

- Normalize Element 188 using the actual card sequence, not a generic default.
- Validate tag 5F34 consistency; preserve three spaces when absent.
- Recalculate Segment 130 length for changed field width.
- If an external serializer pads the value, provide that implementation and
  observed bytes; current delivery language calls the JSON executable wire format.
- Mutation tests must reject two-character values, tag disagreement and stale
  segment lengths. Include a multi-digit BCD value such as 10 -> 010.

**Later copy repair:** All 500 values corrected, dependent lengths recalculated,
and independent gates passed. Upstream renderer correction remains open.

### Developer investigation and implementation sequence

1. Reproduce the two-character value in the final request for TC-001722.
2. Trace how the producer obtains the sequence from tag 5F34 and where it
   formats Element 188. Do not assign a generic sequence to all cards.
3. Implement the three-byte representation at the authoritative formatting
   stage, preserving three spaces when 5F34 is absent.
4. Recalculate Segment 130 length using the actual encoded field widths and
   the applicable ATL105 separator/length rules.
5. Synchronize inline data, final requests and any derived validation evidence.
   Do not preserve old hashes or PASS claims as though the bytes were unchanged.
6. Regenerate all affected packages and independently validate the final output.

The likely investigation area is field formatting/serialization. This is not a
confirmed diagnosis of a particular producer function.

### Concrete acceptance checks

| Input or mutation | Required result |
|---|---|
| Tag 5F34 contains BCD 01 | Element 188 is exactly `"001"` |
| Tag 5F34 contains BCD 10 | Element 188 is exactly `"010"`, not `"016"` |
| Tag 5F34 is absent | Element 188 contains exactly three spaces |
| Element 188 contains `"01"` | Validation fails |
| Element 188 disagrees with the decoded card sequence | Validation fails |
| Width is corrected but Segment 130 length is left stale | Validation fails |
| Final request differs from inline representation | Reconciled or explicitly validated transformation |

**Evidence to return:** Sanitized field-level before/after examples, final
Segment 130 length calculations, final serialized-byte evidence if an external
serializer is involved, and positive/negative regression-test results.

**Closure boundary:** Correct field width does not certify cryptograms, card
eligibility or host execution. No host decline code is asserted by this issue.

## 5. O31-03 - Obsolete archive and README are mixed with current artifacts

**Priority:** High  
**Scope:** Mastercard Credit.

### Reproduction and evidence

1. Compare the outer current export with the embedded BR_SCE_Test_cases archive.
2. The outer export has 450 TCs and 839 request TD files.
3. The archive has 241 TCs from build
   buypass-atl105-mastercard-credit-65482e4498 and no test_data folder.
4. The top-level README describes the old 241-TC build and 446 request files.

**Expected:** One authoritative current build, or explicitly labelled historical
evidence outside the executable delivery.

**Impact:** Consumers can select the obsolete archive, misunderstand readiness
counts, or believe they received the current standalone data package.

**Required correction and acceptance**

- Remove or quarantine obsolete delivery material without destroying evidence.
- Generate documentation counts/build IDs from current authoritative artifacts.
- Validate archive contents after packaging, not only the surrounding directory.
- Publish a manifest binding build identity, paths and digests.
- Mutation test: embed a prior-build archive; packaging gate must fail.

**Later copy repair:** Archive preserved outside deliverable packages; top-level
README and contract replaced with accurate copy-repair status and counts.

### Developer investigation and implementation sequence

1. Inventory both the current outer export and the embedded archive before
   changing packaging.
2. Confirm which build is authoritative. Record the old archive identity
   separately so historical evidence is not destroyed.
3. Investigate how the previous archive and old documentation entered the
   delivery. Reused packaging inputs are a possibility, not a proven code cause.
4. Package only the selected authoritative executable artifacts. Place any
   retained historical evidence outside that executable package and label it.
5. Generate documentation from the final inventory, rather than copying counts
   or build details from an earlier delivery.
6. Validate the final package, including archive contents, against its manifest.

### Concrete acceptance checks

| Test | Required result |
|---|---|
| Reconcile current original-build inventory | 450 Mastercard Credit TCs and 839 request TD files |
| Compare documentation with final inventory | Counts, build identity and advertised contents agree |
| Inspect every embedded delivery archive | No unlabelled obsolete executable build |
| Inject the prior 241-TC archive into a current test package | Packaging gate fails |
| Validate manifest paths and digests | Every listed current artifact resolves and matches its digest |
| Preserve historical evidence | Clearly separated from the authoritative executable delivery |

The counts above describe the audited original build. A regenerated build may
legitimately change counts, but must explain the changes and publish matching
inventory and documentation.

**Evidence to return:** Final package inventory, build manifest, archive-content
validation, regenerated documentation and stale-archive regression-test results.

## 6. O31-04 - Historical TC IDs identify different tests

**Priority:** Medium  
**Scope:** Fleet TC-002461, TC-002462, TC-002468 and TC-002469.

### Reproduction

Compare these IDs between the 29 October and 31 October Fleet exports. Each
overlapping ID now refers to a different semantic case.

**Expected:** Stable identity across deliveries, or explicit build-qualified
identity and migration.

**Impact:** Using bare IDs can associate a previous approval, defect or execution
result with the wrong current test.

All 1,721 current cases have unique matches to previous objectives using name,
template ID, TC type and merchant ID. This reconstructs a migration mapping; it
does not prove their payloads or outcomes are unchanged.

**Required correction and acceptance**

- Use stable IDs or a build-plus-TC identity everywhere history is persisted.
- Supply old-to-new mappings and preserve prior review evidence.
- Do not transfer semantic approval solely because an ID string matches.
- Mutation test: reuse an ID for another objective; unqualified historical lookup
  must fail validation.

**Later copy repair:** Build-qualified identities added. The original migration
map is available separately. Producer identity policy remains to be confirmed.

## 7. O31-05 - Runtime remaining amount has no demonstrated dependent EMV update

**Priority:** Medium  
**Classification:** Execution blocker; not an observed host rejection.

Affected leg-2 cases:

- Amex: TC-002474, TC-002491, TC-002508, TC-002525.
- Mastercard: TC-001726, TC-001743, TC-001763, TC-001780.

### Reproduction

Inspect the second-card TD and its runtime substitutions. The outer Nonfuel
Amount is derived from the first card's actual approved amount, but tag 9F02
inside EMV chip data remains literal.

**Expected:** The dependent amount and cryptographic data reflect the actual
second authorization/card-terminal interaction. ATL105 Element 190 requires
exact values from that interaction.

**Impact:** Substituting only the outer amount can leave inconsistent chip
amount/cryptographic context when the approved amount differs from a fixed sample.

**Required correction and acceptance**

- Define runtime dependency ordering: first response -> remaining amount ->
  second-card/simulator EMV data -> final encoding.
- Generate cryptographic inputs through authorized infrastructure.
- Test more than one first-card approved amount and verify final outer/tag values.
- Do not replace runtime values with fixed literals merely to pass validation.

**Later copy repair:** Deliberately not synthesized. Independent gates retain an
explicit review item; authorized harness evidence is required.

## 8. O31-06 - Execution prerequisites and readiness remain unresolved

**Priority:** Medium  
**Classification:** Execution evidence/readiness gap.

Evidence populations, which overlap:

- 402 TCs explicitly contain EMV cryptogram/IAD/TVR placeholder evidence.
- 90 ready TCs retain open items, including device prerequisites.
- Four Mastercard online-PIN cases remain input-blocked:
  TC-001724, TC-001762, TC-002021 and TC-002037.
- Merchant/card/BIN/route/key/service eligibility is not independently established.

The four Credit PIN cases are not the earlier debit readiness defect. The 72
previous debit cases were correctly moved from ready to needs_client_input.

**Expected:** Separate design-ready, data-ready and execution-ready properties,
with prerequisites resolved before calling a case executable.

**Required correction and acceptance**

- Supply secure PIN/card/simulator/device contracts and test evidence.
- Expose required prerequisites in status/open items.
- Distinguish optional template placeholders from required execution inputs.
- Do not interpret producer PASS as cryptographic validity.
- Preserve blocks until evidence exists; do not invent PINs, keys or host results.

**Later copy status:** Execution readiness remains BLOCKED. Independent gates
can pass deterministic artifact checks while these review items remain open.

## 9. O31-07 - Inline and final TD Sequence Numbers diverge

**Priority:** Medium  
**Classification:** Consistency review, not a proven transaction defect.

**Scope:** 76 return steps across the five packages. Example:
Mastercard TC-001754, step 3.

### Reproduction

Compare the return step's inline Sequence Number with its emitted TD value.
The values differ. The contract says final TD wins.

**Qualification:** A new return can legitimately have a new transaction identity.
The review does not require copying the purchase sequence into every return.

**Impact:** Consumers validating inline data can assess a different transaction
identity from the one actually emitted.

**Required correction and acceptance**

- Synchronize inline state after final rendering, or explicitly document and
  validate the transformation.
- Run correlation checks against final TD bytes/values.
- Demonstrate correct identities for return and follow-on legs.

**Later copy repair:** Observed return sequence values reconciled to authoritative
rendered values. This does not replace source-backed lifecycle validation.

## 10. O31-08 - Coverage and source applicability remain unapproved

**Priority:** Medium  
**Classification:** Coverage/source review gap.

### Uncovered scenarios

Both debit packages have seven Balance Inquiry scenarios without TCs:
BR1_TS22 through BR1_TS28. In each package, two are labelled valid and five
unconfirmed.

The aggregate scenario-link coverage is 138/152, or 90.79%. Each debit package
has 21/28, or 75%. These are delivered-record metrics, not whole-spec coverage.

### Rule and provenance limits

- 1,026 distinct linked rule IDs were inventoried across packages.
- The producer reports zero dedicated own-rule TCs; this is not zero exercised
  predicates, but rule linking alone does not demonstrate boundary coverage.
- Candidate mappings, applicability, missing explicit citation context and
  source revision/hash recipes need review.
- Some exclusion decisions use absence from a supported list; confirm that the
  list is exhaustive for the relevant context.
- The SME questionnaire still describes the EMV guide as missing in places,
  while current supporting rules reference that guide.

**Required correction and acceptance**

- Generate applicable Balance Inquiry cases or document approved exclusions.
- Establish rule/predicate coverage with executable checks and reviewed scope.
- Reconcile companion-source revisions and remaining SME questions.
- Label scope-BR, scenario, predicate, data and execution coverage separately.
- Do not use the second BA workbook sheet as a required denominator in this review.

**Later copy repair:** Coverage gaps and semantic questions remain open.

## 11. Corrections already observed in the original 31 October delivery

The following are improvements made by the AI team, not new defects:

- All 20 split-tender requests use different second-card account values and no
  inherited approval.
- The 72 debit PIN cases previously marked ready are now input-blocked.
- All 3,270 primary/run-set TD references resolve.
- Twelve healthcare cases now materialize selected QHP product 894 data.
- Current client-flow reports accurately limit sample approval to format/hierarchy.
- The referenced SME questionnaire is now delivered.

The later trace-matrix issue does not mean these TD files are absent; their
primary/run-set references are valid while the matrix references are stale.

## 12. Validation evidence and limits

- Original delivery: 3,393 files hash-verified unchanged.
- Original supplied structured assertions: 19,138 PASS on replay.
- Original EMV packet TLV/length-prefix checks pass; Element 188 still fails.
- Later independent gates: original packages FAIL; corrected copies PASS the
  implemented deterministic checks.
- Regression verification: 17 new tests and 33 existing release-validator tests
  passed. The 17 tests were also rerun successfully after the repository merge.
- Maven structure validation could not complete because plugin resolution
  returned HTTP 403. It is not recorded as a test pass.

No live host execution, EMV cryptographic verification, complete transport-byte
serialization or exhaustive rule-by-rule semantic certification was performed.
Passing bounded checks is not complete semantic approval.

## 13. Requested AI-team response

For each issue ID, return:

1. Accepted/disputed disposition with source-backed rationale.
2. Assigned owner and planned producer/root-cause correction.
3. Regenerated artifacts and build/source manifest.
4. Regression evidence, including negative/mutation checks.
5. Remaining client/BA/SME prerequisites and execution impact.

Do not close upstream issues solely because a repaired downstream copy exists,
or because the same producer-authored assertions pass again.

## 14. Evidence attachments

- [Issue register](ISSUE-REGISTER.csv)

The following supporting evidence is retained separately and is not included in
this repository copy. Request it through the approved internal sharing channel:

- Detailed technical validation: `DETAILED-VALIDATION-REPORT.md`
- Affected-record findings: `findings.csv`
- Per-TC and per-TD assessments: `tc-assessments.csv`, `td-assessments.csv`
- BR assessments and citation checks: `br-assessments.csv`, `br-citation-checks.csv`
- Scenario and rule inventories: `scenario-coverage.csv`, `rule-coverage.csv`
- Historical identity mapping: `old-to-new-tc-map.csv`
- Input hashes: `input-manifest.csv`
- Corrected-copy gate results: `oct31-final-regression-gates/validation.json`
- Copy-repair provenance: `oct31-corrected-export/repair-manifest.json`

This report contains no card values, credentials or internal absolute filesystem
paths. Source references are limited to issue-specific requirements and page
anchors. Original evidence and corrected-copy evidence are kept separate.
