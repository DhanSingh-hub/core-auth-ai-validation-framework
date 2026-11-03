# AI artifact delivery: defect and issue report

**Delivery reviewed:** 2026-10-29  
**Report date:** 2026-10-30  
**Specification baseline:** ATL105 2026-4  
**Status:** Open; submitted for AI-team investigation and correction  
**Scope:** BR/scenario/TC linkage, supplied test data, composed transaction flows,
readiness metadata, approval provenance and delivery completeness.

## Summary for the AI team

The delivery expands network/program coverage but does not yet demonstrate
complete semantic correctness or execution readiness. Please investigate and
correct the nine issues below, preserving unresolved BA/SME decisions rather
than inferring approved behavior.

This report distinguishes confirmed composition/reporting defects from missing
execution inputs and validation-evidence gaps. It does not assert that all
1,721 TCs are invalid.

| Package | Declared scope | Scenarios | TCs | Producer ready | Bounded composition FAIL |
|---|---|---:|---:|---:|---:|
| amex_cred | American Express Credit | 36 | 416 | 176 | 10 |
| mastercard_debit | Maestro Direct Debit | 28 | 278 | 42 | 0 |
| ms_cred_fix | Mastercard Credit | 36 | 450 | 180 | 10 |
| ms_fleet | Mastercard Fleet | 24 | 299 | 76 | 0 |
| visa_debit | Interlink Debit | 28 | 278 | 42 | 0 |
| **Total** | Five configurations | **152** | **1,721** | **516** | **20** |

Maestro remains a separate debit configuration within Mastercard-family reporting.
Zero bounded FAIL does not certify the other packages.

### Priority convention

- **High:** incorrect transaction meaning, unsupported approval, or a readiness
  claim that can cause an invalid execution handoff.
- **Medium:** incomplete executable data, broken delivery references or missing
  evidence needed to approve the solution.
- These are proposed remediation priorities, not security severity ratings.

| ID | Priority | Category | Issue |
|---|---|---|---|
| AI-001 | High | Confirmed composition defect | Second-card purchase inherits first-card approval |
| AI-002 | High | Confirmed data-binding defect | Second-card purchase retains the first account |
| AI-003 | High | Readiness defect | 72 ready debit cases explicitly await required PIN input |
| AI-004 | Medium | Execution blocker | Four Credit online-PIN cases lack resolved PIN data |
| AI-005 | Medium | Execution/data blocker | Twelve healthcare cases lack materialized healthcare/QHP data |
| AI-006 | Medium | Delivery defect | 657 TC references point to undelivered standalone TD files |
| AI-007 | High | Confirmed reporting defect | Template matches are called client approval/removal |
| AI-008 | Medium | Validation-evidence gap | PASS and linked-rule evidence do not establish semantic validity |
| AI-009 | Medium | Review-delivery gap | Referenced SME questionnaire is absent |

All counts can overlap. In particular AI-001 and AI-002 affect the same 20 TCs;
they are not 40 distinct defective TCs. Healthcare cases also overlap split-tender
cases. Do not sum issue counts into an invalid-TC total.

## AI-001 - Independent second-card purchase inherits first-card approval

**Priority:** High  
**Classification:** Confirmed composition defect  
**Scope:** 10 Mastercard Credit and 10 Amex Credit TCs.

**Reproduction**

1. Open the delivered Mastercard Credit TC JSONL export.
2. Locate TC-002205, partial approval followed by remainder on a second card.
3. Inspect step 2's execution instruction: card B pays the remainder.
4. Inspect step 2's `runtime_substitutions`: `Approval Number` is sourced from
   step 1. Its source-rule reference is `ENT-ELEM-5`.

**Actual:** The independent card-B Purchase inherits card A's Approval Number.

**Expected:** Card B's payment obtains an independent authorization. It must not
be converted to a preauthorized purchase using card A's approval reference.

**Specification basis:** ATL105 2026-4, Section 13.2, Element 5, Approval Number:
including the element in a Purchase Request makes it preauthorized and BUYPASS
does not go out for authorization.

**Impact:** The composed request does not implement the stated second-payment
objective even though the copied field is supported by a real source rule.

**Requested correction:** Remove the inappropriate cross-card approval binding.
Retain the sale-level relationship without confusing it with authorization
identity. Calculate the remainder from the actual first response.

**Acceptance criteria**

- Assert that the second independent purchase does not inherit card A's approval.
- Demonstrate a new authorization for card B in the controlled execution harness.
- Verify second requested amount = original requested amount minus first approved
  amount, using runtime response data rather than only fixed examples.
- Add a regression that fails when the original approval binding is restored.

## AI-002 - The second-card payload retains the first card's account

**Priority:** High  
**Classification:** Confirmed data-binding defect  
**Scope:** Same 20 TCs as AI-001.

**Reproduction:** In TC-002205, compare the account data in purchase steps 1 and
2 and inspect the runtime substitutions. The inline Account Number is identical;
no account substitution implements the stated card-B instruction.

**Actual:** Narrative says second card; delivered payload retains the same account.

**Expected:** The executable binding identifies a distinct second payment
instrument, with entry-mode-dependent data consistent with that instrument.

**Impact:** Passing the supplied flow would not prove the two-card objective.
This defect remains even if AI-001 is corrected.

**Acceptance criteria**

- Bind card B explicitly through an approved fixture or runtime input.
- Validate the instrument distinction in the controlled test environment.
- Regenerate dependent track/EMV/card data as applicable; do not change only the
  account identifier while retaining incompatible instrument data.
- Add a negative composition check rejecting an unbound second-card instruction.
- Do not embed real card credentials in corrective reports or committed fixtures.

### Affected IDs for AI-001 and AI-002

| Package | TCs |
|---|---|
| Mastercard Credit | TC-002205, TC-002216, TC-002221, TC-002232, TC-002236, TC-002253, TC-002260, TC-002274, TC-002290, TC-002315 |
| Amex Credit | TC-003038, TC-003055, TC-003072, TC-003089, TC-003105, TC-003122, TC-003140, TC-003173, TC-003209, TC-003268 |

## AI-003 - Ready debit cases still declare required PIN inputs unresolved

**Priority:** High  
**Classification:** Readiness/metadata defect and execution blocker.

| Package | Explicit required-PIN-awaiting-client flags | Flagged cases marked ready |
|---|---:|---:|
| Maestro Direct Debit | 249 | 36 |
| Interlink Debit | 249 | 36 |
| **Total** | **498** | **72** |

**Reproduction**

1. Locate Maestro TC-003729 or Interlink TC-003451.
2. Observe `status = ready`.
3. Observe the flag beginning
   `AWAITING_CLIENT_VALUE: Encrypted PIN Block Data required on step 1`.
4. Observe the unresolved PIN field and lack of an executable resolution in the
   inspected case; the example's `open_items` is empty.

**Actual:** An explicitly unresolved required input does not block ready status.

**Expected:** Execution readiness requires the input or a validated secure runtime
binding. Otherwise expose the client-input blocker.

**Qualification:** This is not a claim that every EMV debit flow requires PIN.
It concerns cases whose own metadata declares a required PIN input unresolved.
The 498 population is a flag-based count; the 72 ready cases are its subset.

**Acceptance criteria**

- Reconcile all 72 ready cases against their required-input flags.
- Gate execution readiness on validated secure PIN-harness capability and inputs.
- Expose unresolved required inputs consistently in status and open items.
- If ready means design-complete, separate it from execution-ready and document
  both meanings.
- Do not resolve the issue by hardcoding PIN blocks.

## AI-004 - Credit online-PIN cases lack resolved PIN data

**Priority:** Medium  
**Classification:** Execution blocker, not an asserted host rejection.

**Affected Mastercard Credit TCs:** TC-002203, TC-002220, TC-002337, TC-002348.

**Reproduction:** Inspect the online-PIN objective and the corresponding omitted
inline PIN value. TC-002203 is already marked `needs_client_input`.

**Expected:** A secure, testable runtime source for the required PIN data.

**Acceptance criteria:** Deliver the harness/input contract and demonstrate the
online-PIN execution with authorized test infrastructure. Preserve blocked-input
status until resolved. This is not the same readiness misclassification as AI-003.

## AI-005 - Healthcare cases lack materialized healthcare/QHP data

**Priority:** Medium  
**Classification:** Execution/data-completeness blocker.

**Affected TCs**

- Mastercard Credit: TC-002215, TC-002216, TC-002231, TC-002232, TC-002252, TC-002253.
- Amex Credit: TC-003054, TC-003055, TC-003088, TC-003089, TC-003121, TC-003122.

**Reproduction:** Compare the healthcare objective with selected request segments
and healthcare product data. The bounded check does not find materialized selected
Segment 102/QHP product 894 data implementing the objective.

**Expected:** Applicable healthcare cases carry the required healthcare data.
ATL105's healthcare table identifies Total QHP Amount, product code 894, as
required in that context.

**Impact:** A healthcare title does not prove that the request exercises healthcare
processing; a generic optional product template is not sufficient.

**Acceptance criteria**

- Confirm source-backed program and healthcare applicability.
- Select and populate the required healthcare data and validate amount relations.
- Demonstrate that serialization transmits the selected segment and products.
- If inapplicable to a selected configuration, obtain a documented disposition
  rather than silently treating a generic request as healthcare coverage.

No host decline code has been inferred from this finding.

## AI-006 - Standalone TD references do not resolve

**Priority:** Medium  
**Classification:** Delivery/integration defect.

**Scope:** 408 Mastercard Credit TC references and 249 Fleet TC references.

**Reproduction:** Read `test_data_file` for Mastercard TC-002201 and resolve its
`qe_shaped_test_data/TD-TC-002201.json` reference within the delivery. It is absent.
The audit finds 657 such TC references; this is a reference count, not a claim of
657 unique missing files.

**Expected:** Every exported artifact reference resolves under the documented
delivery contract.

**Impact:** A consumer following the export cannot load the promised standalone
TD. Inline payloads exist, so the finding is not that all test data is absent.
Inline-only Amex/debit exports without these pointers are not included.

**Acceptance criteria:** Either deliver the referenced files or remove broken
pointers and explicitly make inline data authoritative. Validate reference
resolution and inline/standalone parity where both are supplied.

## AI-007 - Unsupported client approval and removal claims

**Priority:** High  
**Classification:** Confirmed approval-provenance reporting defect.

**Scope:** Mastercard Credit and Fleet client-flow reports.

**Reproduction:** Read the client-flow coverage report. It states:

> Approved template: 230 of 241 test cases approved

It also labels 11 mobile cases "Removed by the client."

**Actual:** Sample matching and sample omissions are represented as client
semantic approval and scope decisions.

**Expected:** The established approval is for hierarchy/format, not sample
validity. An omitted sample row does not establish client rejection.

**Impact:** Unsupported claims can prematurely close review and suppress coverage.

**Acceptance criteria**

- Correct both reports and the generator logic producing these statements.
- Use neutral sample-match terminology.
- Require traceable client/BA decisions for content approval or scope removal.
- Reassess the mobile coverage disposition without inferring rejection from
  absence in a sample.

## AI-008 - Validation evidence does not establish full semantic PASS

**Priority:** Medium  
**Classification:** Validation-evidence gap; not proof that every PASS TC is wrong.

**Evidence**

- All 10,031 supplied structured assertions pass when replayed independently.
  They nevertheless miss AI-001 and AI-002.
- 1,529 producer-PASS TCs carry constraint-verification advisories.
- Each coverage report records zero linked request rules tested by its own
  dedicated TC. Rule-link populations overlap across packages.

**Expected:** Clearly separated structural, value, conditional, cross-field,
lifecycle, readiness and execution verdicts. Rule links must not be presented as
proof that a rule's predicate and boundary behavior were tested.

**Acceptance criteria**

- Add independent composition checks detecting AI-001 and AI-002.
- Expose unresolved advisories in the final disposition.
- Define the meaning and limits of each PASS/ready/coverage metric.
- Trace claimed rule validation to an executable predicate or reviewed evidence.
- Do not publish a semantic coverage percentage until the denominator, scope,
  applicability and evidence are established.

## AI-009 - Referenced SME questionnaire is not delivered

**Priority:** Medium  
**Classification:** Review-delivery gap.

**Scope:** Credit and Fleet client-flow reports reference
`ms_credit/SME_QUESTIONS_FLOWS.md`, absent from the supplied five-package tree.

**Impact:** Reviewers cannot use the referenced FQ questions to adjudicate partial
or unresolved mappings. Other open-item files do not automatically replace this
specific referenced questionnaire.

**Acceptance criteria:** Deliver the referenced questionnaire, or correct the
reference to an equivalent delivered artifact. Include question text, affected
items, decision status and traceable BA/SME disposition. Validate report links.

## Regression context

The prior Mastercard Credit delivery had 241 TCs; the new package has 450:

- 239 shared IDs, unchanged in name, root request, composed steps, planned legs,
  structured checks, verification, status and TD reference.
- 211 new IDs.
- Two prior return-void IDs absent: TC-002419 and TC-002421. Reconcile replacements
  before calling these missing objectives.

All ten prior split-tender defects remain. The package name `ms_cred_fix` is not
evidence of correction.

## Review boundaries and acceptance handoff

This was an offline structural and bounded semantic audit. It did not perform
live host tests, complete wire serialization validation, cryptographic validation,
or exhaustive source-to-rule adjudication. All 94 input files were hash-verified
unchanged. No real card values are reproduced in this report.

Independent TC disposition: 20 composition FAIL; 1,701 REVIEW_REQUIRED.
Additional execution/reporting issues are recorded separately and do not imply
an additional disjoint invalid-TC population.

The second BA workbook sheet is not a mandated coverage denominator in this
review. Fleet has Petroleum source references, but publication revision and
configuration applicability still require reconciliation. Source references alone
are not certification.

For each issue, please return:

1. Accepted/disputed disposition with source-backed rationale.
2. Generator/root-cause correction and affected-artifact list.
3. Regenerated outputs plus reproducible regression evidence.
4. Remaining client/BA/SME inputs and their execution impact.

Do not close issues solely by editing a label, suppressing a flag, or making the
same producer-authored assertions pass again.

Companion tracker: [AI-TEAM-ISSUE-REGISTER.csv](AI-TEAM-ISSUE-REGISTER.csv).
