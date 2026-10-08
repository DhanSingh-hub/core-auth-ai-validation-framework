# Appendix AE - Visa Estimated and Incremental Authorization

## Sources and implementation

Training covers all of AE-1, with directly related Appendix I-26 estimated
indicator, I-67 through I-72 Table 063 layouts/examples, and Section 10.4.5
signature-debit follow-up rules. Appendix I-36 Table 044 and K-23 Table 028
provide related 15-character identifier evidence, but do not resolve AE's
incomplete Transaction Identifier segment reference.

- [Independent logical oracle](../../../../../../src/main/java/com/coreauth/validator/canonical/AppendixAEIncrementalAuthorizationOracle.java)
- [Tests](../../../../../../src/test/java/com/coreauth/validator/AppendixAEIncrementalAuthorizationOracleTest.java)
- [Source-anchored coverage and fixtures](../../../../test-output/test-json/appendices/appendix-ae-segment-100-coverage.json)

There are 10 requirements, 4 scenarios, 4 test-case records and 29 executable
synthetic fixtures. Status remains **PARTIALLY_COVERED**.

## Permission and initial request

AE is a Visa profile. MCC 4121 and 5411 are CNP-only; MCC 5552 is for EV.
These are not an exhaustive MCC allowlist: the same appendix discusses hotels,
cruise lines and rental merchants. The former placeholder's three-MCC-only
interpretation has been removed. Actual permitted-merchant and MCC/category
mapping evidence is required; no blanket CNP requirement is invented for all
lodging/rental/EV flows.

When final amount is unknown, the initial estimated request uses indicator `1`
in Acquirer Trace Data sub-table 09 (length 001). The known-final-amount initial
variant is not fully specified by AE and remains reviewed.

## Incremental series and host behavior

Each incremental follows an estimated/initial authorization and retains:

- Sequence Number.
- Transaction Identifier.
- Table 063 Terminal Transaction Number.

It omits estimated indicator 09, rather than resending `1`. Merchant sends the
returned approval code in Authorization Identification Response. BUYPASS
identifies the incremental and sends the incremental indicator to Visa.
There is no invented merchant-side incremental tag.

The oracle compares supplied original/current identifiers and approval evidence.
It uses a six-digit logical sequence and a 15-alphanumeric logical identifier;
the latter is bounded related Table 044/028 evidence, not a claim that either
table supplies the missing AE wire location. Real series/approval provenance
and host-to-Visa behavior require external evidence.

## Table 063

Table ID 063 has Table Length 011 and logical data:

| Field | Width | Rule |
|---|---|---|
| Arrival | 4 | MMDD or four spaces when unused |
| Duration | 2 | Numeric days or two spaces when unused |
| Terminal Transaction Number | 4 | Numeric; mandatory for lodging, incremental and EV |
| Preferred Customer | 1 | P; examples use a space when unused |

Arrival/duration are independently optional. MMDD is checked against possible
month/day pairs, including February 29 because no year is supplied. The terminal
number is unique for a new series and stays the same until the series completes.
Uniqueness is supplied evidence, not inferred from one message.

Source lodging example `0323050058P` and non-T&E/EV example with six spaces,
`0058`, then one space are tested for initial/incremental/completion contexts.
Only terminal number is required to remain identical; changed arrival/duration
is not silently rejected as an entire-table mismatch.

## Completion, expiry and extended stays

Completion sends the final amount. Fixture amounts are logical nonnegative
minor-unit digit strings; numeric comparison does not define a new wire width,
currency or incremental-amount aggregation rule.

| Merchant category supplied as evidence | Window from initial estimated authorization |
|---|---|
| Cruise, lodging, car/vehicle rental | 14 days |
| Other rental, e.g. trailer parks/campgrounds | 7 days |
| Other merchants | No window invented; review |

Subsequent incrementals do not restart the clock. Tests cover just before, exactly
at and fractionally after days 7/14. The source's "on or before" language is used
for the logical boundary; timezone/calendar-day cutoff and actual approval expiry
need an authoritative implementation policy.

For a stay/rental exceeding that window, the merchant completes the first
transaction THEN initiates the second on/before day 14/7. Both events and their
order are checked. Recording only completion or only a new initial does not pass.
Late activity indicates source-described chargeback exposure; the oracle does
not predict actual chargebacks or impose a processor decline code.

Section 10.4.5 adds Table 021 PINless Debit Acceptance `X` to associated
incremental/completion requests when the initial Segment 134 Settlement Type
(Element 198) is `X`. The source also names reversals; this AE stage model does
not claim full reversal validation.

## Remaining boundaries

- Real permitted-merchant/MCC-category mapping, approval and same-series evidence.
- Exact wire mapping for AE's incomplete Transaction Identifier reference and
  Authorization Identification Response.
- Merchant-history uniqueness and actual host-to-Visa incremental behavior.
- Full envelopes, Acquirer Trace Data and Table 063 wire framing, currency/amount
  reconciliation, real completion/restart and calendar deadline policy.
- Chargeback/settlement evidence and AI-generated artifacts.

Each assessment set has an `EXTERNAL-EVIDENCE` review gate. Missing evidence
does not hide a definite mismatch. Non-applicable PASS is not approval or
certification. Fixtures shallow-merge over defaults; executable review
expectations test gates, not resolution of source gaps.

## Validation

Run from the Maven module:

```powershell
mvn "-Dtest=AppendixAEIncrementalAuthorizationOracleTest,AppendixZStoredCredentialOracleTest,AppendixITableCatalogTest,ProducerNeutralContractTest,RepositoryStructureTest" test
```
