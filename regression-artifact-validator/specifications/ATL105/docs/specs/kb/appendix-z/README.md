# Appendix Z - Stored Credential

## Sources and scope

ATL105 2026-3 Appendix Z-1 through Z-4 describes Visa, MasterCard, Discover and Amex
stored-credential profiles. Its referenced Sections 10.13.2.4.1 and 10.13.2.4.2 define
stored credentials and non-exhaustive use cases. Appendix I supplies the related
Table 005, 013, 015, 016, 017, 032, 044, 045, 047, 049 and 056-059 layouts.
Appendix K supplies response references in Tables 012 and 028.

- [Independent logical oracle](../../../../../../src/main/java/com/coreauth/validator/canonical/AppendixZStoredCredentialOracle.java)
- [Executable tests](../../../../../../src/test/java/com/coreauth/validator/AppendixZStoredCredentialOracleTest.java)
- [Source-anchored coverage and fixtures](../../../../test-output/test-json/appendices/appendix-z-segment-100-coverage.json)

The package contains 17 requirements, 8 scenarios, 8 test-case records and 20
executable synthetic fixtures. It is **PARTIALLY_COVERED**, not a network-certified
implementation or a complete wire-message validator.

## Definitions and profiles

A stored credential is an account number or payment token retained by a merchant,
agent, payment facilitator or staged digital wallet for future transactions.
Temporary retention to finish a single transaction or its related incremental
authorizations is excluded. CIT means active cardholder participation; MIT means
previously stored credentials, prior consent and no active cardholder engagement.
The reference use cases are not exhaustive.

Logical request keys use three-digit Table IDs and optional sub-table IDs, for
example `032/08`. Table 005 is the full three-character PAN-mode/PIN-capability
value. The Appendix Z PAN mode `10` must not be treated as the entire Table 005.

| Profile | COF `032/08` | Initiator `049/02` | Payment `013` | PAN mode |
|---|---|---|---|---|
| Initial storage | I | Initial CIT context from referenced use cases | R/I if an amount is due for recurring/installment | Original |
| MIT recurring | C | M | R | 10 |
| MIT installment | C | M | I | 10 |
| MIT unscheduled | C | M | Neither R nor I | 10 |
| CIT recurring initial | I | C | R | Original |
| CIT installment initial | I | C | I | Original |
| Subsequent CIT adhoc | C | C | No recurring/installment requirement inferred | 10 |

If no amount is due on initial storage, a zero-dollar Account Verification with
`I` can be sent; this is not a mandate to send a transaction in every such case.
Original entry mode requires supplied evidence and uses the existing Appendix I
logical validator/Appendix J value sets.

CVV2 (Table 004) must not be sent on MIT recurring/installment or the listed
initial CIT recurring/installment profiles. Subsequent CIT permits optional CVV2.
Appendix Z does not establish a blanket prohibition for every other profile.

## Network-specific training

- Visa/Amex response Table 028 maps to subsequent MIT request Table 044;
  Discover response Table 012 maps to request Table 015. Required references have
  15-character alphanumeric representation. Actual storage and association with
  the correct credential require external evidence.
- Discover MIT Table 045 retains the first approved amount, with up to 12 digits
  and two assumed decimal places. Numeric comparisons ignore leading zeros.
- Discover India-issued MIT recurring/unscheduled above USD 52 references Table
  056 tags 01-08. The threshold is strictly greater than 52, not greater than or
  equal. The oracle requires supplied USD-equivalent evidence, not guessed FX.
- Discover MIT installment uses tags 056/01 and 056/08, plus installment type
  049/13 (`S` merchant, `T` third party, `V` issuer). Third-party descriptors use
  `provider*retailer`, underlying retailer city/state/country and retailer SIC.
  Supplied installment sequence values must ascend relative to a supplied earlier
  sequence; actual retailer identity is not established by a matching string.
- Discover unscheduled Delayed Card Sale uses Table 016 `D`; No Show uses 017 `N`.
- MasterCard 056/09 has all eight source mappings: `C101`/`M101` adhoc/unscheduled,
  `C102`/`M102` standing order (variable amount/fixed frequency), `C103`/`M103`
  subscription (fixed amount/fixed frequency), and `C104`/`M104` installment.
- Visa India recurring uses tags 01/02/03/05/06/12/13. Do not require every tag
  between 01 and 13: 04/07/08 are Discover-only and 09 is MasterCard-only.
  Tag 12 is 1 registration, 2 subsequent, 3 modification or 4 cancellation.
  Subsequent amounts <=15,000 INR use non-ecommerce without CAVV; above that
  source-version threshold, ecommerce, Terminal Type 25 and CAVV are required.
  Modification and cancellation require authentication, and cancellation requires
  zero-dollar Account Verification.
- Visa registration reference 056/13 remains fixed. Recurring parameters
  01/02/03/05/06 use registration or latest modification values. Subsequent
  recurring requires successful original registration/authentication/authorization.
  Country, INR-equivalent amount, success, ecommerce and CAVV presence are supplied
  evidence, not facts derivable from a synthetic fixture. CAVV presence does not
  prove cryptographic authenticity. The source encourages subscription to the
  Global BIN File to identify India-issued accounts accurately.

## Source conflicts and remaining boundaries

1. Appendix Z says Discover subsequent requests include tags that Appendix I
   describes as first-payment-only (01/02/05). Section 10.13.2.4.2 also mentions
   tags 01-08 for subsequent installments, while Appendix Z specifically names
   01 and 08. Requiredness beyond the explicit shared predicates remains
   review-gated; shape and missing explicit installment fields are still checked.
2. Appendix Z says reuse original Visa/Amex references, while Appendix I Table 044
   says original/previous and illustrates rolling references. A changed reference
   is REVIEW_REQUIRED pending authoritative lifecycle selection, not silently
   accepted as a match.
3. Complete Table 056 sub-table framing, outer ATL105 message validation and all
   completion/reversal/cancellation COF message lifecycles are separate surfaces.
   The oracle checks logical maps; it must not claim wire-format certification.
4. Prior consent, merchant storage, issuer country/BIN provenance, FX rates,
   provider/retailer identity, authorization evidence and cryptogram validity need
   trusted external inputs. RBI threshold revisions are not inferred.

Non-applicable assessments have PASS with an explicit non-applicability reason;
they are not counted as transaction approvals. Fixtures target an individual
requirement, not an aggregate approval verdict. Each fixture shallow-merges its
payload over `fixtureDefaults`; supplied maps replace defaults. The test actually
executes every fixture and verifies its expected assessment.

## Validation

Run from the Maven module:

```powershell
mvn "-Dtest=AppendixZStoredCredentialOracleTest,AppendixILogicalDataValidatorTest,AppendixISegmentWireValidatorTest,AppendixITableCatalogTest" test
```
