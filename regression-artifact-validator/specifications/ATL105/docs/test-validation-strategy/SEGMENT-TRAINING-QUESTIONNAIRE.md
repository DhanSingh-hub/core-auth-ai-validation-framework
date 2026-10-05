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

## Trainer/Tester Interview for LLM Lifecycle Training

Complete this section with a Test Team trainer before teaching a lifecycle flow to the Test LLM. Ask for source evidence and reasoning; do not treat agreement with a proposed answer or current validator behavior as approval.

1. Which ATL105 release and exact sections/elements establish each flow? What source text supports the rule, and what is interpretation?
2. For each flow, what is the exact original -> follow-up sequence? Is it a two-message pair, a three-leg chain, or a queued recovery flow?
3. Which transaction codes are valid in each role, and which similar-looking combinations are invalid? Distinguish code `0` Purchase/Capture from preauthorized completion.
4. Which identity fields must match for this flow (Sequence Number, Approval Number, account, amount, device/terminal), and which are conditional rather than universal?
5. How do card type, transaction channel, product, partial approval, and response outcome change applicability or required amounts?
6. For TOR, what starts the response timeout, when may the TOR be forwarded, what is the queue ordering, and what must happen before the next financial request?
7. What is the retry limit per connection route? What response or exhaustion condition clears the queued TOR?
8. Which approved, declined, partial-approved, debit, and completion variants are explicitly supported, explicitly prohibited, or not established?
9. Is Authorization -> Completion -> Void a confirmed flow for this context? Identify the void target and any debit/EMV restrictions; otherwise record the SME/TBA item and keep it `REVIEW_REQUIRED`.
10. Provide one valid example and at least one near-miss invalid example per flow. For each, explain the expected result and cite the rule that decides it.
11. Which existing BR, scenario, test case, fixture, validator, matrix, or catalog conflicts with the source? What correction or blocker should be recorded?
12. What question remains unanswered, who owns the answer, and what evidence will close it?

Record each answer with: trainer/tester, date, specification release, flow/context, source anchor, evidence or source quote location, decision (`SOURCE_SUPPORTED`, `INVALID`, or `REVIEW_REQUIRED`), rationale, affected BR/TS/TC/TD IDs, reviewer, and next action. An SME answer without an applicable source citation remains `REVIEW_REQUIRED`.

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
