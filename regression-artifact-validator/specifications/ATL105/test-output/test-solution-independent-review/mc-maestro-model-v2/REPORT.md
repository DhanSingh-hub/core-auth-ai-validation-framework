# Mastercard / Maestro model v2 - pending-work implementation

## Implemented repository-side work
- **38** page/hash-pinned draft BRs, **66** applicability rows,
  **66 TS / 132 TC / 132 TD** logical control/mutant chains.
- Every **1343** legacy BR retained with per-anchor old/new block identity,
  changed/missing/ambiguous locator findings, original status and three context decisions.
- **41** legacy/overlay overlap proposals retained; no automatic merges.
- All **730** source pages (including front matter) scanned; **2936** source units
  retained, including nonnormative/table-like text. Modal language is only a triage label.
- **27** context/category review rows retain entry-method and product scope boundaries.
- All **1851** package-qualified AI supporting rules retained; crosswalk
  has **66** rows, **19** with review candidates and exact TS/TC/TD identities.
- **15** synthetic ASCII Segment 100 controls/mutants executed against a bounded
  framing/length/sequence/card-code checker. These are not complete ATL105 messages.
- **1944** delivered TD JSON files independently observed for bounded field
  representations; no payload account/PIN data is copied into the ledgers.
- Physical observation dispositions: `{'FAIL': 294, 'PASS_BOUNDED_FIELDS': 1443, 'NOT_APPLICABLE_NO_SEGMENT100': 207}`.
- Existing native-gate status: `{'mastercard_credit': 'FAIL', 'mastercard_fleet': 'FAIL', 'maestro_direct_debit': 'FAIL'}`; readiness remains BLOCKED.

## What remains external or review-bound
An exact normalized source block match proves only block-text identity, not legacy BR
equivalence, atomicity or applicability. Changed blocks and overlap proposals need
independent semantic adjudication; lexical candidates are never confirmed.
The complete source-unit population is now mechanically accounted for, but is NOT a
complete classified Mastercard/Maestro BR denominator. Source units can span multiple
obligations or contain information only. No hidden threshold makes them atomic rules.
Fleet inheritance, Maestro savings/ACH eligibility, Signature Debit/Common AID, MOTO
completion, credit/FSA TOR conflicts and issuer product subtypes remain explicit decisions.
Client credentials, cryptograms, actual device procedures, response captures and host
execution were not supplied. No synthetic data substitutes for that evidence.
Approved denominator and confirmed coverage remain **null / NOT_ASSESSED**.

## Fixture boundaries
The ASCII fixture is Segment 100 only, with deliberately synthetic non-card/terminal
values and fields through Sequence Number. It checks just declared field separation,
length, Segment Type, six-digit sequence and contextual card-code mapping. It is not
a positive full-message fixture or proof of all required/conditional fields.
Logical companion and lifecycle checks require explicitly supplied preconditions.
JSON object presence/count is never treated as serialized-wire evidence.

## Persistent evidence
- [Summary and input identities](summary.json)
- [Expanded independent draft BR/TS/TC/TD](model-package.json)
- [Applicability/denominator register](denominator-register.csv)
- [Legacy release reconciliation](legacy-release-register.csv)
- [Overlap reconciliation proposals](overlap-register.csv)
- [All retained source units](source-unit-register.csv)
- [Page scan completeness](source-page-register.csv)
- [Context/category scope review](scope-register.csv)
- [Delivery-bound candidate crosswalk](delivery-crosswalk.csv)
- [Every AI rule, including unmatched](ai-rule-review-register.csv)
- [Executed bounded physical fixture suite](wire-fixtures.json)
- [Delivered physical TD observations](physical-observations.csv)
- [BA/SME decisions and dependencies](decision-register.csv)
- [Existing native gates](native-gates.json)
- [Input manifest](input-manifest.json)
