# Code 0: Approved Purchase/Capture

**Element:** 83, Response Code  
**Source:** ATL105 2026-3, §13.2 Element 83, Appendix G; response layouts §§11.1.2, 11.2.2, 11.3.2, and 11.8.2  
**Status:** Source-derived training; transaction-type cross-product is partially inferred and remains reviewable.

## Meaning and Context

Element 83 value `0` means **Approved—Purchase/Capture**. Appendix G transaction type `0` is POS Purchase/Capture or preauthorized completion; type `4` is Customer-activated Purchase/Capture; type `6` is Mail/Phone Purchase. The current Test Solution response validator treats `{0,4,6}` as purchase contexts compatible with response code `0`.

ATL105 does not provide a complete response-code-by-transaction-type-by-card-type table. Therefore:

- The message-family semantics and transaction-type descriptions above are source-backed.
- The pairing of response code `0` with each purchase transaction type is the Test Solution’s source-aligned compatibility rule, not an explicit PDF cross-tab.
- Card/network/product-specific approval behavior must be evaluated under its own source and lifecycle rules; do not infer a universal card-type matrix from Element 83 alone.

## Response Families and Segment Context

### Financial Transaction Response (§11.1.2)

Element 83 is Data Section 1, Field 1. The response has no Field Separators. Related request context is a Financial Transaction Request:

- Segment 100 is required in Data Section 2.
- The literal §11.1.1 Data Section 3 table permits conditional segments `101`, `102`, `103`, `104`, `111`, `123`, `135`, `143`, `145`, `146`, `151`, `152`, and `153`.
- Segment 110 is a separate SME-approved extension based on §12.9 / `SEG110-R-001`, not a row in the literal §11.1.1 table.

The Financial Transaction Response may contain conditional response companions Segment 112 and Segment 115. Segment 112 depends on Element 115; Segment 115 depends on the print-data condition and request Loyalty Version Number 2. These companions do not carry Response Code 0; they accompany the response containing Element 83.

### Loyalty Card Transaction Response (§11.2.2)

The section explicitly says the response has the same information and layout as the Financial Transaction Response. Code-0 interpretation and response companion rules therefore follow §11.1.2. The Loyalty request contains Segment 100 and required Segment 108, plus optional Segment 114; those are request segments, not Element 83 fields.

### ECA/TeleCheck Service Transaction Response (§11.3.2)

The section explicitly says the response reuses the Financial Transaction Response layout. Code-0 interpretation follows that response layout. The ECA/TeleCheck request contains Segment 100 and Segments 110, 111, and 113 per §11.3.1; these are request segments, not Element 83 fields.

### EMV Financial Transaction Response (§11.8.2)

Element 83 is Data Section 1, Field 1. The response is fixed-length with no Field Separators. The source identifies Element 83 as carrying approved/rejected EMV outcomes; code `0` is the generic approved Purchase/Capture response value. The message may carry conditional response companions `112`, `115`, `120`, `131`, and `134` according to the §11.8.2 response table and triggers. Those segments do not carry Element 83.

The EMV request carries Segment 100 and required Segment 130, plus conditional Section 3 companions from the §11.8.1 table. The request-side segments establish context but are not themselves response-code values.

## Valid and Invalid Checks

A code-0 response is valid for this focused rule when:

1. The response family is Financial, Loyalty (Financial response alias), ECA/TeleCheck (Financial response alias), or EMV Financial.
2. If transaction-type metadata is supplied to the Financial response validator, the type is a purchase context accepted by the current oracle: `0`, `4`, or `6`.
3. Required response envelope fields and formats are valid under the corresponding family layout.
4. Approval Number is present only when the applicable lifecycle context requires an approval reference. It is not a universal requirement for every code-0 response.

It is invalid under the focused compatibility rule when the supplied transaction type is authorization-only (`3`, `5`), return/reversal (`7`, `8`), or another non-purchase type. If transaction-type context is absent, family membership alone cannot prove the code-to-transaction pairing; classify that pairing as unverified rather than silently passing it as a confirmed combination.

## Test Evidence

The response-code tests accept code `0` with transaction types `0`, `4`, and `6`; reject incompatible authorization/return contexts; allow an absent Approval Number when not required by lifecycle metadata; and reject its absence when metadata declares it required. These are Test Solution regression tests, not AI-generated test-chain evidence or business approval.
