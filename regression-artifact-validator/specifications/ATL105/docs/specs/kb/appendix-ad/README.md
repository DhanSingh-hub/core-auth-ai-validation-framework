# Appendix AD - Real Time Account Updater

## Source scope

Appendix AD-1 is a short definition and participation/trigger profile. Training
follows its explicit references to Elements 2/12 and the linked updater response:

- Appendix AD-1: participating acquirer, card-not-present COF/installment/recurring
  context, merchant First Data registration/certification and secure account storage.
- Appendix I-65/I-66: Segment 111 Table 060 request fields.
- Section 12.39, pages 12-96 through 12-99: Segment 155 response tables.
- Elements 2/12, pages 13-25, 13-31 and 13-32: follow-on and retry credentials.

These supporting rules are not additional sentences extracted from AD alone.
Missing-evidence review gates are framework safeguards, not network response codes.

- [Independent parsed-field oracle](../../../../../../src/main/java/com/coreauth/validator/canonical/AppendixADAccountUpdaterOracle.java)
- [Tests](../../../../../../src/test/java/com/coreauth/validator/AppendixADAccountUpdaterOracleTest.java)
- [Source-anchored coverage and fixtures](../../../../test-output/test-json/appendices/appendix-ad-segment-100-coverage.json)

The package has 10 requirements, 4 scenarios, 4 test-case records and 44 persisted
executable synthetic fixtures. Status is **PARTIALLY_COVERED**.

## Request and participation

The updater definition covers secure account-information exchange between
participating issuers/acquirers for merchants storing credentials for future
transactions. The merchant registers and certifies with First Data. The profile
requires participating acquirer, CNP and at least one of COF, installment or recurring.
Missing evidence is reviewed; explicit contradictions fail.

Participating merchants should send Table 060 sub-table 01 for applicable requests.
The table allows up to 100 bytes; sub-table ID/length are two decimal digits with
specified ranges 01-25, and data is 1-25 alphanumeric bytes.

| Sub-table | Length | Value / meaning |
|---|---|---|
| 01 | 01 | Y: all available account updates; I: indicators only |
| 02 | 01 | O: override, do not perform Auth Optimization |

01 and 02 are mutually exclusive. Both are Visa/MasterCard, CNP,
Auth/Sale/Refund only. Unspecified sub-tables within the printed range are
reviewed, not assigned invented semantics. Tests enforce the reconstructed
100-byte bound including sub-table headers; actual encoded headers, repeated IDs
and ordering are outside this map-based oracle.

If 01 is omitted, no information is returned even if optimization occurs.
Triggering optimization does not guarantee qualification or an available update:
First Data makes the qualification decision. Explicit override is supported by
Appendix I; its use alongside AD's all-participating-request guidance is reviewed
instead of silently forcing Y. Supplied O behavior must not show optimization
or Segment 155 data.

## Segment 155 response

Segment 155 can only be sent when the transaction qualifies for and uses First
Data Auth Optimizer. Tables have three-digit IDs/lengths; fields are not separated
by field separators, and unpopulated fields are omitted.

| Table | Logical data | Request gate / scope |
|---|---|---|
| 001 | Updated PAN, up to 19 alphanumeric bytes | Y; Visa/MasterCard; not TransArmor |
| 002 | Updated TransArmor token, up to 19 alphanumeric bytes | Y; Visa/MasterCard; not PAN transactions |
| 003 | Updated expiry, four digits YYMM | Y; Visa/MasterCard |
| 004 | One-character status A/E/Q/C/U | Y/I; Visa/MasterCard |
| 005 | Original association decline code | Y/I; Visa/MasterCard; network decline followed by optimizer-directed network reattempt |
| 006 | Six-character VAU result code | Y/I; Visa-only |

Statuses mean A = new account and expiry, E = new expiry, Q = contact cardholder,
C = closed, U = unknown card. The source does not explicitly require every update
field to appear whenever status A/E is present; no such mandatory combination is
invented.

Table 005 says variable length 3 while Table Data has maximum 4. Nonempty ASCII
data up to 4 bytes is checked, but the exact valid width remains REVIEW_REQUIRED.
Unknown response-table semantics are also reviewed.

All 15 listed VAU results are represented with their meanings:
VAU001-VAU014 and VAU016. VAU015 is not listed in this release. Recognition does
not infer approval or implement every MCC/network/issuer eligibility decision.
The result-code list is source-release-specific, not a future-code registry.

## Follow-on and retry credentials

Elements 2/12 allow original or updated PAN/TransArmor token on follow-on
transactions, with corresponding original/new expiration. The oracle checks
supplied original credentials or a pair derived from supplied Segment 155 data.
Expiry-only updates can retain the original account with updated expiry.
An updated account without corresponding expiry evidence is reviewed, not
automatically paired with the original expiry.

Response Table 003 is YYMM. Parsed manually entered Element 12 expiry is MMYY;
tests verify the conversion and reject mixed credential/expiry pairs.
Credentials in this model are logical values: TransArmor follow-ons still need
the complete TAP request profile and application-specific Element 12 layout.
During follow-on/retry assessment, `requestData` and `responseData` describe the
original updater exchange; `updaterRequestOperation` identifies its operation.
The current completion/reversal operation must not be mistaken for the original
Auth/Sale/Refund that carried the updater opt-in.

Retries of follow-on reversals, completions, cancellations, voids of merchandise
return and timeout reversals retain the credentials from the initial attempt.
Updating credentials between that initial attempt and its retry fails.

## Remaining evidence and boundaries

- Acquirer/issuer participation, merchant enrollment/certification and secure storage.
- Real First Data qualification, optimizer-directed reattempt and issuer updates.
- Genuine account/token-to-expiration association and token cryptographic validity.
- Table 005 width conflict and override/all-request policy clarification.
- Full Table 060/Segment 155 envelopes, Segment Length counting, ordering/repeated
  tables, Element 12 layouts, TAP requests and end-to-end lifecycle behavior.
- AI-generated artifacts for independent validation/scoring.

Every scenario emits an external-evidence review assessment. Individual logical
PASS results, including explicitly non-applicable results, are not transaction
approval or aggregate certification. Fixtures shallow-merge over defaults;
request/response maps replace default maps. Executable review expectations test
guardrails, not resolved external behavior.

## Validation

Run from the Maven module:

```powershell
mvn "-Dtest=AppendixADAccountUpdaterOracleTest,AppendixAATransArmorOracleTest,AppendixZStoredCredentialOracleTest,AppendixITableCatalogTest,ProducerNeutralContractTest,RepositoryStructureTest" test
```
