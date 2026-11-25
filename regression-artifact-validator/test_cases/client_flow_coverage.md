# Client flow coverage

Client input RI-004 vs the planned test cases (vocabulary `config/client_flow_vocabulary.json` 2026-10-29.1). Only spec-verified flows are test cases; the rest are questions in `mastercard_credit/SME_QUESTIONS_FLOWS.md`.

| Source | covered | partial | sme_question | not_generated | separate_program | unmapped | total |
|---|---|---|---|---|---|---|---|
| flow_doc | 24 | 17 | 20 | 0 | 21 | 0 | 82 |
| ideas | 42 | 29 | 46 | 0 | 30 | 0 | 147 |

Open SME questions by number of client items: FQ-01 (12), FQ-02 (6), FQ-03 (8), FQ-04 (10), FQ-05 (1), FQ-06 (9), FQ-07 (7), FQ-08 (4), FQ-09 (2), FQ-10 (1), FQ-11 (1), FQ-12 (4), FQ-13 (4), FQ-14 (8), FQ-15 (7), FQ-16 (2), FQ-17 (1), FQ-18 (1), FQ-19 (6), FQ-20 (16), FQ-21 (2)

Client sample template ('Approved Template' tab): 230 distinct test-case names listed across 241 rows; scenario status differences vs this build: 3.

The client's approval of this template covers **message format and hierarchy only, not sample-content validity**. It is not a content approval of any generated test case; content approval comes only from the review ledger.

Sample names present in the generated-name column but not in the template's name list (no traceable client decision is recorded for these - open questions for the client, not scope exclusions):

- BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase approved
- BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Partial approval → remainder on 2nd card (split tender)
- BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Partial approval → Purchase Reversal of approved amount
- BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Partial approval → Time-out Reversal
- BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Partial not supported (indicator 0) → full approval or decline
- BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase → Purchase Reversal
- BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, no response → Time-out Reversal
- BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, late approval → Time-out Reversal
- BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase, unreadable response → Time-out Reversal
- BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Purchase timed out → TOR sent ahead of next Purchase
- BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Contactless Mobile Commerce Device Time-out Reversal unanswered → retried (max 3)
