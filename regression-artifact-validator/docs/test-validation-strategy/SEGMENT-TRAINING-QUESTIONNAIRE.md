# Segment Training Questionnaire
## Use Before Training Any New Segment

**Boundary:** AI Solution generates BR, TS, TC, request/response JSON, and traceability. Test Solution independently validates them against source rules.

## Source and Scope

1. What specification and release are authoritative?
2. What is the segment name and number?
3. Which sections, pages, and appendices apply?
4. Is the segment used in requests, responses, or both?
5. Is it required, optional, conditional, repeatable, or prohibited?
6. What is confirmed source evidence versus interpretation?
7. Which items require `REVIEW_REQUIRED`?

## Segment Model

1. What are the fields and exact order?
2. Which fields are required, optional, or conditional?
3. What are their types, lengths, allowed values, and empty-field rules?
4. Are separators, binary values, encryption, tokenization, or encoding involved?
5. Are request and response rules different?

## Business Context

1. Which transaction types and card types use the segment?
2. Which Prompt Code combinations are valid?
3. Which POS entry modes, card programs, and channels apply?
4. Which companion segments are required for EMV, NFC, fleet, fuel, EBT/eWIC, purchase-card, variable information, or authorizer-specific flows?
5. What happens for missing, duplicate, misordered, or unknown companions?

## BR Questions

For each source rule:

1. What business behavior is required?
2. Under what condition?
3. What field or segment proves it?
4. What is valid, invalid, boundary, and review behavior?
5. What is the canonical source anchor?
6. Is it core, conditional, lifecycle, response, or separate-domain?

## TS/TC Questions

For each BR:

1. What is the positive scenario?
2. What is the negative scenario?
3. What boundary or mutation should fail?
4. Does the case require one message, a request/response pair, or an ordered lifecycle?
5. What exact assertions and expected outcome apply?
6. Does the test case have a shared source anchor with its scenario and BR?

## Test Data Questions

1. Is the AI JSON converter-ready and complete?
2. Does it preserve the real message envelope?
3. Are all required fields and companion segments present?
4. Is Element/segment count correct?
5. Are lengths, separators, order, and encoding representable?
6. Are sensitive values synthetic or masked?
7. Are original and follow-up messages linked by the correct identity?
8. Are response code, approval, decline, and authorizer fields present when applicable?

## Lifecycle Questions

1. What is the original message and what is the follow-up?
2. Which sequence, terminal, account, amount, approval, and Prompt Code values must persist?
3. When is a new sequence required?
4. Are completion, cancellation, reversal, void, timeout, TOR, retry, or staged-wallet flows possible?
5. What is the retry limit and terminal behavior?
6. Are duplicate follow-ups invalid?

## Response Questions

1. Which response codes apply to this message family?
2. Which codes mean approval, decline, partial approval, retry, or pending work?
3. Is an approval number, approved amount, decline code, authorizer code, or additional information required?
4. Does the response correlate to the request sequence and transaction context?
5. Are response-only appendices applicable?

## Specialized Context Questions

Before certifying specialized behavior, declare the applicable context:

1. Is this `MASTERCARD_AUTHORIZATION` behavior?
2. Is this `POS_CONDITION_ENTRY_MODE` behavior from Tables 005/030?
3. Is this `TOKENIZATION` behavior?
4. Is this `APPENDIX_RESPONSE_LAYOUT` behavior?
5. Is this `PRODUCT_CARD_LIFECYCLE` behavior?
6. What specialized specification or configuration proves the behavior?
7. Does the artifact include Segment 100 plus the required specialized data?
8. Is the outcome `COVERED` or `REVIEW_REQUIRED`?

Never certify these contexts from generic Segment 100 fixtures alone.

## Appendix Questions

For every applicable appendix:

1. Does it add a direct field rule?
2. Does it add a conditional companion rule?
3. Does it add a lifecycle or response rule?
4. Is it a separate domain requiring its own package?
5. Which BRs, TSs, TCs, and TDs prove applicability?
6. Is the appendix fully transcribed, partial, external, or review-only?

## Mutation and Coverage Gate

Create mutations for wrong values, missing fields, length/type errors, invalid combinations, missing/duplicate companions, count/order errors, sequence mismatches, response mismatches, and unsupported contexts.

Report:

- Source rules
- BRs, TSs, TCs, TDs
- Source anchors
- Transaction/card coverage
- Lifecycle coverage
- Response coverage
- Companion coverage
- Mutation detection
- Missing/duplicate/invalid artifacts
- `REVIEW_REQUIRED` items
- AI intake status
- External converter status

## Required Artifact Chain

```text
BR -> TS -> TC -> converter-ready request/response JSON
```

Every level must retain a canonical source anchor. Preserve AI artifacts unchanged; never use Test Solution fixtures as proof of AI coverage.
