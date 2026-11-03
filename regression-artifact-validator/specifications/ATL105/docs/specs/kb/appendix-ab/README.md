# Appendix AB - Purchase Repayment

## Source and implementation

ATL105 2026-3 Appendix AB-1 defines Purchase Repayment. It is only one page;
the implementation also follows the explicitly linked Appendix I and K fields.

- [Independent logical oracle](../../../../../../src/main/java/com/coreauth/validator/canonical/AppendixABPurchaseRepaymentOracle.java)
- [Tests](../../../../../../src/test/java/com/coreauth/validator/AppendixABPurchaseRepaymentOracleTest.java)
- [Source-anchored coverage and synthetic fixtures](../../../../test-output/test-json/appendices/appendix-ab-segment-100-coverage.json)

The package contains 7 requirements, 4 scenarios, 4 test-case records and
25 persisted executable fixtures. Status is **PARTIALLY_COVERED**: synthetic
logical fields do not certify enrollment, real messages or authorizer behavior.

## Trained rules

Purchase repayments are installment payments submitted by third-party installment
providers or payment facilitators. A third-party provider is different from the
retailer selling goods/services. Examples include post-purchase payments and
third-party Buy Now Pay Later. Missing identity/role evidence is reviewed; an
explicit non-installment or same-retailer third-party claim fails.

| Location | Source rule |
|---|---|
| Appendix I-41/I-42, Segment 111 Table 049 sub-table 04 | ID 04, length 2; CB = Consumer Bill Payment, Visa-only; IR = Purchase Repayment, MasterCard-only |
| Appendix I-9/I-10/I-42, Segment 111 Table 013 | MasterCard repayment requires I (Installment payment), one data character |
| Appendix I-39/I-42, Segment 111 Table 047 | Provider name followed by `*` followed by retailer name, e.g. `XYZ*A SMALL CO`; maximum 38 bytes |
| Appendix I-44, Segment 111 Table 049 sub-table 07 | ID 07, length 1; Y opts into receiving Merchant Advice Code |
| Appendix K-24/K-25, Segment 112 Table 030 | ID 030, length 002; two alphanumeric advice-code bytes, authorizer supplied, currently MasterCard-only |
| Appendix K-25 | An unregistered Purchase Repayment merchant's decline returns 22 when advice acceptance Y was sent |
| Appendix K-24/K-25 | Merchant must not retry after advice 03, 21 or 22; future codes can be introduced |

The prior placeholder incorrectly treated CB as Visa Purchase Repayment.
CB is Consumer Bill Payment, not a documented Visa repayment mapping. Appendix AB
is not a comprehensive network allowlist: other brands' repayment applicability
is reviewed, while CB/IR brand restrictions are enforced. Discover third-party
installment rules remain in Appendix Z/I; they must not inherit MasterCard IR.
No MCC, amount, installment-count or timing rule is invented.

The oracle consumes parsed logical fields, not wire tables. It checks the 38-byte
descriptor boundary for ASCII, required separator/nonblank components and supplied
provider/retailer names in the correct order. Non-ASCII encoding needs a separate
wire contract. A well-shaped descriptor without identity evidence is reviewed.

Advice receipt is conditional on request opt-in and observed response. Request-only
fixtures cannot prove a response. Missing enrollment or decline evidence is
reviewed, not silently defaulted to registered/approved. Code 22 alone is not
used to infer enrollment. Structurally valid new advice codes remain reviewed,
not rejected by a frozen list or assumed retryable. The broader timed-retry
policies for other Appendix K codes are outside this repayment profile.

## Evidence boundaries

Remaining work:

- Independent provider/retailer identity, payment-facilitator role and registration evidence.
- Actual authorizer declines and advice, and terminal retry-behavior evidence.
- Full Segment 111/112 table framing, ordering and request/response lifecycle parsing.
- Non-ASCII descriptor encoding and unspecified brand/program eligibility.
- AI-produced artifacts for independent comparison and scoring.

Every scenario emits an `EXTERNAL-EVIDENCE` review assessment. Non-applicable
PASS results explicitly state their boundary and are not approval/certification.
Persisted fixtures shallow-merge payloads over `fixtureDefaults`; executable
REVIEW_REQUIRED outcomes test guardrails, not resolution of external questions.

## Validation

Run from the Maven module:

```powershell
mvn "-Dtest=AppendixABPurchaseRepaymentOracleTest,AppendixITableCatalogTest,ProducerNeutralContractTest,RepositoryStructureTest" test
```
