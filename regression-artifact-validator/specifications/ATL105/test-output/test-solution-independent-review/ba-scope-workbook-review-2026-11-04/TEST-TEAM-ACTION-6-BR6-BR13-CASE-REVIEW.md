# Test Team Action 6: BR6–BR13 Case Review

**Status:** Source-bounded case-planning review; no canonical crosswalk or test
baseline approved  
**Input:** BA workbook `BR & TS Scope updates.xlsx` (SHA-256 recorded in
[the workbook review](./REPORT.md))  
**Boundary:** Existing BRs and workbook content were not changed. BR6–BR13 and
their TS labels are treated as proposed groupings, not canonical requirements.

## 1. Outcome

The workbook defines 54 scope rows under BR6–BR13, but has no detailed case tabs
for these groups. The scope rows therefore identify candidate test topics, not
test cases, approved coverage, or a required case count.

The repository contains relevant source knowledge for several topics, but it
does not validate the workbook's BR labels or establish all expected outcomes.
Existing source-derived packages elsewhere in the repository are not counted as
coverage of these workbook rows unless a canonical BR/TS/TC/TD crosswalk is
approved. No executable TC or TD was created by this review.

## 2. Scope inventory

| Workbook BR | Proposed topic | Scope rows | Workbook TS labels |
|---|---|---:|---|
| BR6 | Entry Mode Processing | 9 | TS33–TS41 |
| BR7 | Settlement & Reconciliation | 5 | TS42–TS46 |
| BR8 | Exception Handling | 5 | TS47–TS51 |
| BR9 | Device & Terminal Validation | 6 | TS52–TS57 |
| BR10 | Data Element Validation | 7 | TS58–TS64 |
| BR11 | Healthcare / FSA / HRA | 5 | TS65–TS69 |
| BR12 | Fleet Processing | 9 | TS70–TS78 |
| BR13 | Industry-Specific Processing | 8 | TS79–TS86 |
| **Total** |  | **54** |  |

These are structural workbook counts only. They are not an approved denominator
and do not imply that one case per row, or any particular number of cases, is
required.

## 3. Group-by-group review and case-generation gates

### BR6 — Entry Mode Processing

**Workbook topics:** EMV contact, offline PIN, contactless with/without CVM,
magstripe, manual entry, chip fallback, mobile commerce, and contactless mobile
wallet.

The [Appendix J entry-mode material](../../../docs/specs/kb/segment-100/appendix-j-pos-entry-mode-training.md)
distinguishes recognizing an entry-mode value from validating its card,
network, device, and transaction context. The
[Segment 130 applicability requirements](../../../docs/specs/kb/segment-130/emv-card-type-business-requirements.md)
and [fallback flow](../../../docs/specs/kb/segment-130/emv-entry-mode-fallback-flow.md)
also distinguish true EMV from fallback and ordinary magstripe, and specify
transaction contexts where Segment 130 is required or waived.

**Candidate cases after applicability is approved:**

- Separate contact EMV, contactless EMV, ordinary magstripe, and fallback
  paths; do not model them as one generic entry-mode test.
- For each applicable EMV flow, pair the entry mode with the source-required
  Segment 130 presence/content and terminal/card capabilities.
- Keep PIN/CVM behavior conditional on the actual device and transaction
  profile. Do not assign a universal PIN outcome from a title such as
  “Offline PIN.”
- Test credential-on-file and mobile-commerce modes only in their documented
  network/use context; distinguish mobile-wallet token use from stored card
  account use.

**Disposition:** `REVIEW_REQUIRED` for the workbook applicability and expected
outcomes. The source supports concrete assertions once card product, chip-read
result, transaction type, terminal capability, and release are fixed.

### BR7 — Settlement & Reconciliation

**Workbook topics:** settlement processing, settlement adjustment, read totals,
batch close, and duplicate settlement.

The repository's [Segment 105 module](../../../docs/specs/kb/segment-105/README.md)
documents the Totals Request structure and source-defined Totals Date values.
The [Totals Date note](../../../docs/specs/kb/segment-105/totals-date-sme-tba-note.md)
covers the listed selectors and the source restriction against requesting
dates earlier than the third most recent active date. The same source
explicitly leaves merchant cutoff/time zone, enabled operations, duplicate or
end-of-day behavior, and totals aggregation policy unresolved.

**Candidate cases after the request/message family is clarified:**

- Separate a Segment 105 Totals Request from settlement submission, settlement
  adjustment, batch close, and reconciliation; do not assume they are the same
  operation.
- For a confirmed Totals Request, cover source-listed date selectors,
  source-defined invalid selectors, the activity-window boundary, and
  request/response correlation.
- Add duplicate, retry, adjustment, batch-close, and reconciliation assertions
  only after the owner supplies the host/merchant policy and expected state
  transitions.

**Disposition:** The Totals Request structure and enumerated date semantics are
source-backed; the broader settlement/reconciliation behavior is
`REVIEW_REQUIRED`. No generic duplicate-settlement outcome can be assigned.

### BR8 — Exception Handling

**Workbook topics:** host timeout, communication failure, late response,
recovery transaction, and Sequence Number validation.

The [Segment 100 lifecycle flow](../../../docs/specs/kb/segment-100/sequence-lifecycle-flow.md)
supports a key distinction: an eligible timed-out purchase uses a timeout
reversal, while an authorization-only timeout is cancelled. Follow-ups preserve
the original transaction identity/Sequence Number under the documented
lifecycle rules. This does not establish a universal transport retry policy,
late-response window, duplicate suppression rule, or recovery SLA.

**Candidate cases after transport and lifecycle policy is supplied:**

- Pair each timeout with the original transaction type and the appropriate
  follow-up type; include positive and mismatched sequence/reference cases.
- Distinguish communication failure before a request is accepted from a timeout
  after possible host processing.
- Exercise late approval/decline and retry only with defined timing, host
  state, idempotency, and recovery-window expectations.

**Disposition:** Lifecycle distinctions are source-backed; communication
failure, retry, late-response, and recovery outcomes remain
`REVIEW_REQUIRED` pending the applicable host/processor contract.

### BR9 — Device & Terminal Validation

**Workbook topics:** disable Return, Void, and Preauthorization for card-type
labels 166, 167, and 168; Store & Forward window for label 173; receipt
validation; and Store & Forward processing.

The inspected ATL105 knowledge does not map the workbook's `166`, `167`, `168`,
or `173` labels to a canonical ATL105 card code or an approved device
configuration schema. Do not interpret these numbers as Appendix E Element 14
card codes. The repository separately documents receipt Card Type IDs, but
those are receipt labels and are not an established mapping to device
capabilities or financial Prompt Code card types.

**Candidate cases after the configuration owner supplies the schema:**

- For each disable flag, verify enabled/disabled behavior against the
  explicitly configured operation and the expected device/host layer.
- For Store & Forward, define the window boundary, offline acceptance rules,
  persistence/replay behavior, duplicate handling, and response reconciliation.
- For receipt validation, specify the receipt type, required fields, applicable
  Card Type ID, and validation oracle separately from authorization behavior.

**Disposition:** `REVIEW_REQUIRED`. The labels, configuration semantics,
timing, and failure outcomes need an authoritative device/POS source and
configuration owner. Do not generate “disabled means decline” cases without
that contract.

### BR10 — Data Element Validation

**Workbook topics:** invalid PAN/Mod 10, invalid or expired card, invalid
CVV/CVV2, invalid track data, invalid EMV data, invalid amount, and invalid
service code.

These are different validation layers and are not universally applicable to
every product or transaction. For example, CVV/CVV2 depends on the applicable
entry and transaction context; EMV-data checks apply only to an EMV path; and a
well-formed issuer decline is not equivalent to malformed request data. The
workbook's `All` scope does not prove that every field is present or validated
for every product.

**Candidate cases after field rules and ownership are confirmed:**

- For each field, state whether the test is a representation/format failure,
  a source-defined business-rule failure, a processor validation, or an issuer
  response.
- Select positive and negative boundaries from the applicable source rule and
  identify which component is expected to reject or process the request.
- Define response and decline-reason assertions only from the authoritative
  network/host mapping; do not infer them from the scenario title.

**Disposition:** Candidate negative-test dimensions are useful, but exact
applicability and expected outcomes are `REVIEW_REQUIRED` per field and flow.

### BR11 — Healthcare / FSA / HRA

**Workbook topics:** FSA purchase, HRA purchase, auto-substantiation,
healthcare return, and healthcare balance inquiry.

Appendix F source training identifies an FSA/HRA product-code table, but it
also records a conflict between the overview and detailed table for the
890–899 ranges. The presence of an FSA/HRA code does not establish merchant
eligibility, eligible-item policy, substantiation triggers, return behavior,
or balance-inquiry support. “Healthcare Card” is not a network identity.

**Candidate cases after program rules are approved:**

- Separate FSA and HRA product/program configuration and eligible/ineligible
  purchase outcomes.
- Define the auto-substantiation data, decision authority, and allowed
  response/state changes.
- Specify whether return and balance inquiry are supported for the named
  program and how each links to the original transaction.

**Disposition:** `REVIEW_REQUIRED`. Obtain the governing healthcare program
rules, product/network pairs, and resolution of the Appendix F source conflict.
Do not invent an eligibility decline code.

### BR12 — Fleet Processing

**Workbook topics:** fuel, non-fuel, mixed basket, product restriction, fleet
product type, odometer, driver ID, vehicle ID, and EV charging.

The [Segment 101 flow](../../../docs/specs/kb/segment-101/segment-101-flow.md)
describes Segment 101 as conditional fleet companion data and identifies
mutual exclusion with Segment 145. Its
[open SME/TBA register](../../../docs/specs/kb/segment-101/segment-101-sme-tba-input-register.md)
leaves fleet-eligible card types, transaction types, per-field requiredness,
Petroleum specification scope, and Segment 145 scope unresolved. Appendix F
also makes product-code table membership distinct from merchant Dynamic Card
Table permission and Segment 102 amount/order rules.

**Candidate cases after fleet profile and program are fixed:**

- Separate fuel, non-fuel, mixed baskets, and EV charging; validate each against
  the applicable product-code and configuration rules.
- Exercise Segment 101 presence/absence, conditional field requirements,
  field boundaries, and Segment 101/145 mutual exclusion with synthetic data.
- For driver/odometer/vehicle prompts, define the fleet program's requiredness
  and expected response for missing or invalid values.
- Keep fuel product uniqueness, order, and amount reconciliation distinct from
  field-format tests.

**Disposition:** Source-backed rule families exist, but the workbook's
network/product applicability and several decisive rules are still
`REVIEW_REQUIRED`. Petroleum-derived packages are drafts pending SME review,
not an approved universal fleet oracle.

### BR13 — Industry-Specific Processing

**Workbook topics:** petroleum, hospitality, car rental, utilities, government,
transit, mail order/MOTO, and e-commerce/CNP.

These labels mix merchant industries and transaction channels. They should
not be treated as one shared product/network rule. The Petroleum 2025-1
Mastercard Fleet and Visa Fleet training reports are explicitly
`DRAFT_REVIEW_REQUIRED`; they contain version-reconciliation questions and do
not establish approved behavior for all Petroleum or fleet programs. The other
industry rows have no case-level source anchors or applicability details in
the workbook.

**Candidate cases after each industry/channel is independently scoped:**

- Petroleum: choose the specific Petroleum document/version and fleet product;
  reconcile its rules with the governing ATL105 release before creating
  assertions.
- Hospitality, car rental, utilities, government, and transit: identify the
  supported message/transaction flow, program-specific data, lifecycle, and
  authoritative outcomes for each context.
- MOTO and e-commerce: treat these as channel/entry dimensions, then combine
  them only with source-supported transaction and card contexts.

**Disposition:** `REVIEW_REQUIRED` per row. Do not transfer a petroleum/fleet
rule to another industry or treat channel labels as product permission.

## 4. Decisions required to create executable cases

| Decision | Needed from | Why it blocks test generation |
|---|---|---|
| Canonical BR and TS crosswalk for BR6–BR13 | BA / Test Solution owner | Workbook labels and TS IDs are not confirmed canonical links. |
| Governing ATL105 and supplemental-source release per context | BA / product or network SME | Rules may differ by release and external program document. |
| Product, network, program, transaction, channel, and terminal profile | BA / product owner | Broad `All` / `Supported Products` values do not define case applicability. |
| Expected validation layer and response/reason mapping | Processor / network SME | Scenario titles do not identify the expected raw or normalized result. |
| Settlement, recovery, device, and Store & Forward policies | Relevant operational/configuration owners | ATL105 evidence does not define the complete operational behavior. |
| Synthetic TD input policy and available simulator/host | Test lead / environment owner | No execution-ready test data or environment is established by this workbook. |

## 5. Action 6 disposition

Action 6 is **complete as a source-bounded review and case-generation plan**.
The next step is not to invent missing expected outcomes: obtain the decisions
above, approve the canonical crosswalk, then create unique TC IDs and synthetic
TDs against the approved rules. No workbook rows were altered, no existing BR
was rewritten, and no coverage denominator or percentage is claimed.
