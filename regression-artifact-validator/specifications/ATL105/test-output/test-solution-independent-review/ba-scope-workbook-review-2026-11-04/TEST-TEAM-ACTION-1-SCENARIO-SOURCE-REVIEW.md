# Test Team Action 1: Scenario Review Against Existing BRs and Sources

**Status:** Initial source-grounded review; broader adjudication blocked on BA crosswalk and release confirmation  
**Input:** BA workbook `BR & TS Scope updates.xlsx` (SHA-256 recorded in the accompanying [workbook review](./REPORT.md))  
**Boundary:** No existing BRs or workbook content were changed. This review does not approve the workbook as a denominator or establish coverage.

## 1. Review outcome

The workbook contains useful candidate scenarios, but this pass cannot confirm
that each scenario covers, extends, or conflicts with a particular existing
canonical BR. The workbook does not identify a source release, source anchors,
or a crosswalk from its BR1–BR13 labels to canonical ATL105 BR IDs. Its detailed
case tabs also do not consistently use the BR/TS mapping in its own scope tab.

The review therefore separates:

1. **Source-backed observations** that can be used to refine questions and
   expected assertions.
2. **Candidate gaps** suggested by the scenarios but requiring canonical BR,
   product, and release mapping before they can be called ATL105 test gaps.
3. **Blocked comparisons** where the workbook does not provide enough
   authority or traceability to adjudicate.

## 2. Traceability prerequisite

The workbook’s scope tab defines 86 TS IDs across BR1–BR13. The case tabs for
BR1–BR5 contain 105 distinct TS IDs, but only 7 are paired with the same BR in
the scope tab, and only 6 rows match on BR, TS, and scenario. For example:

- The BR2 case tab uses TS25 for Authorization Decline; the scope tab defines
  BR4 TS25 as Card Verification (CVV/AVS).
- The BR3 case tab uses TS46 for Full Merchandise Return; the scope tab defines
  BR7 TS46 as Duplicate Settlement Exception.
- The BR4 case tab uses TS76 for Balance Inquiry decline; the scope tab defines
  BR12 TS76 as Driver ID Prompt Validation.
- The BR5 case tab uses TS91–TS116, outside the scope tab’s TS1–TS86 range.

These are confirmed workbook-internal inconsistencies, not proof that the
scenario ideas themselves are invalid. Until the BA confirms the intended
crosswalk, do not use these IDs as canonical trace links or use the mismatch to
declare an ATL105 BR uncovered.

## 3. Source-backed observations and candidate test refinements

The ATL105 knowledge documents inspected for this pass explicitly cite release
2026-3. The workbook does not identify its governing release. Confirm that the
same source baseline applies before converting these observations into accepted
test oracles.

### Purchase, authorization, return, and follow-up transactions (workbook BR1–BR3)

The source-backed [financial transaction request overview](../../../docs/specs/kb/11-financial-transaction-request-sections.md)
lists transaction types for purchase/capture, authorization, refund, reversal,
cancellation, void of return, and timeout reversal. It also distinguishes a
standard request from cases that require additional Section 3 segments, such
as EMV, NFC tokenization, fleet, product, EBT, purchase-card, and variable
information data.

The [sequence/lifecycle flow](../../../docs/specs/kb/segment-100/sequence-lifecycle-flow.md)
and [lifecycle learning note](../../../docs/specs/kb/segment-100/sequence-lifecycle-sme-tba-note.md)
support these assertions:

- An authorization-only timeout is recovered with cancellation (Prompt Code
  `S`), not purchase TOR (Prompt Code `Z`). TOR is for an eligible timed-out
  financial purchase.
- A purchase reversal/void, an authorization cancellation, and a void of
  merchandise return are distinct lifecycle flows; their original transactions
  and follow-up identities differ.
- Follow-ups must preserve the original Sequence Number. Some lifecycle fields,
  including Approval Number and amount, have flow-specific rules and should not
  be asserted as universally unchanged.

The case title **“Partial Authorization Plus Timeout Reversal”** in the BR2
authorization cases is therefore a source-review flag. If the underlying
transaction is authorization-only, the proposed TOR outcome conflicts with the
inspected source flow and should be replaced or clarified with BA/SME input.
Because the workbook supplies no governing release or source anchor, treat this
as a candidate conflict to adjudicate—not a final defect finding.

The [partial-approval flow](../../../docs/specs/kb/segment-100/partial-approval-flow.md)
also distinguishes the capability indicator from the host’s approval result.
The cases should identify the applicable capability/context and assert the
approved amount and remaining-balance behavior; a generic “partial approval”
or “transaction completed” expected result is not enough to validate those
separate outcomes. The source notes an Amex prepaid-specific indicator context;
do not generalize it to other products.

For return/refund and reversal cases, the workbook names several useful
positive, decline, duplicate, timeout, and void scenarios. To make them
source-testable, each needs the correct original transaction type, follow-up
type, original Sequence Number, applicable approval/reference data, and
settlement state. The current BR3 case-sheet layout does not provide explicit
expected-result or precondition columns.

### Credential-on-file (workbook BR5)

The source-backed [Appendix J entry-mode reference](../../../docs/specs/kb/segment-100/appendix-j-pos-entry-mode-training.md)
states that PAN mode `10` applies only to specified stored-credential
transactions and contexts; it does **not** apply to the first
credential-verification/storage transaction. It also records network-specific
exceptions.

Accordingly, separate initial storage/verification from subsequent CIT/MIT or
other stored-credential use. A test should assert entry mode and its context
only where the applicable source rule supports it. The workbook’s broad
“Credential Storage”, recurring, installment, bill-payment, and MIT scenarios
do not by themselves establish those network-specific conditions or their
expected processor responses.

### Declines and response assertions (workbook BR1–BR5 and BR10)

The [Element 83 response-code training](../../../docs/specs/kb/element-83-response-code/README.md)
states that response-code validity depends on the response family and
transaction/lifecycle context; a global response-code list is not a universal
allowed set. Scenario names such as “Do Not Honor,” “Insufficient Funds,” or
“Lost Card” are not sufficient evidence for a specific raw response, normalized
result, or decline reason.

Before approving decline tests, obtain the authoritative response/reason mapping
for the relevant network, processor, response family, and transaction context.
Keep malformed request validation, unsupported transaction/context, and a
well-formed issuer-declined transaction as distinct test outcomes unless the
applicable source explicitly equates them.

### EMV and entry-mode scenarios (workbook BR6)

The financial-request source overview says Segment 130 is required for every
EMV financial transaction; [Appendix J](../../../docs/specs/kb/segment-100/appendix-j-pos-entry-mode-training.md)
documents source-defined entry-mode values and context/lifecycle restrictions.
The workbook’s EMV contact/contactless, PIN, magstripe, fallback, and mobile
scenarios are plausible test dimensions, but they need case-level terminal,
card, transaction, and required-data conditions. An entry-mode value’s
syntactic validity alone does not establish that it is appropriate for a
particular device/card/network combination.

### Remaining workbook groups (BR4, BR7–BR13)

This pass does not make a semantic pass/fail decision for balance inquiry,
settlement, store-and-forward/device controls, healthcare, fleet, or industry
processing. Their candidate scenarios must be mapped to canonical BRs and
checked against the applicable source and product/program scope. Several groups
are specialized or processor/network dependent; titles and product labels alone
do not establish ATL105 requirements or expected results.

Detailed case tabs were supplied only for BR1–BR5. For BR6–BR13, the scope
sheet’s scenario names can be reviewed as suggestions, but there is no detailed
case data to evaluate for setup, execution steps, expected outcomes, or TDs.

## 4. Candidate coverage dimensions to carry forward

These are review prompts, not approved additions to the BR set:

| Area | Candidate dimension to verify against canonical BR/source |
|---|---|
| Purchase/authorization outcomes | Approved, partial, declined, and invalid/unsupported request outcomes; keep network response mapping explicit |
| Partial approval | Capability indicator, applicable card/product context, approved amount, remaining balance, cancellation and follow-up lifecycle |
| Lifecycle | Authorization completion/cancellation; purchase reversal/TOR; return void; late response; preserve original identity and apply flow-specific values |
| Returns | Full/partial/multiple returns, amount limits, duplicate attempts, return void, settlement state, and original transaction linkage |
| Stored credentials | Initial storage versus later CIT/MIT/recurring/installment use; network and entry-mode constraints; consent/setup as required by authoritative rules |
| EMV/entry mode | Required companion data and context-appropriate entry mode; malformed data versus valid-but-declined transaction |
| Exceptions | Timeout, communication failure, retry, duplicate retry, late response, recovery, and sequence/reference mismatch |
| Specialized products/programs | Healthcare, fleet/petroleum, EBT/eWIC, and industry-specific applicability only where a source and approved product scope support it |

This is not an exhaustive coverage statement: the canonical BR crosswalk and
applicable release are not established.

## 5. Decisions needed before closing action 1

Ask the BA to confirm:

1. The canonical ATL105 BR IDs represented by BR1–BR13 and the intended
   BR-to-TS-to-case associations.
2. The source specification release and authoritative source anchors for each
   mapping.
3. Whether the BR2 partial-authorization timeout case is truly authorization
   only; if so, how it should represent cancellation rather than purchase TOR.
4. The response-code/reason authority and exact expected result fields for each
   network/processor context.
5. In-scope products/programs and which specialized contexts require separate
   source documents.
6. Detailed cases and test-data definitions for BR6–BR13.

Once these decisions are available, the Test team can complete a true
scenario-to-canonical-BR/source adjudication. Until then, preserve the current
BRs and record the uncertain mappings as open rather than inventing traceability
or expected behavior.
