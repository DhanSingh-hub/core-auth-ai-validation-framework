# TC-003253 (BR1_TS3_TC30) - ATL105 Test Data Validation

**BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Debit card, 3 failed swipes → transaction ended (no manual entry)**

| Item | Value |
|---|---|
| Result | **REVIEW** |
| Can it run as-is? | Yes, once the real Terminal Identifier is supplied |
| Test data | none – device-only test case: no message is sent to BUYPASS |
| Template / status | `T11-DEBIT-NO-MANUAL` / `pending_sme` |
| Specification | BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026) |

All page references are to the BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026). "PDF p." is the page number shown in the PDF viewer; "printed" is the page number in the document footer.

- **FAIL** - a value in the test data breaks an ATL105 rule. It must be fixed.
- **BLOCKED** - the field holds instruction text instead of a value. It must be filled in from the step 1 response before the message can be sent.
- **REVIEW** - the data values are acceptable, but something in the test case must be confirmed or corrected.
- **INFO** - a note for the team that builds the wire message.
- **PASS** - matches ATL105.

## Device-only test case

No message is sent to BUYPASS, so there is no test data to check against the ATL105 message layouts. The check covers the ATL105 text the test case quotes (end of this report).

- **Objective:** Debit card, 3 failed swipes -> transaction ended (no manual entry)
- **Procedure (on the device):** Swipe a debit card with an unreadable stripe three times
- **Expected behaviour:** The device prompts to swipe again up to three times, then displays a message to the clerk and ends the transaction; manual entry is not offered and no request is sent to BUYPASS

## What must be fixed

Nothing - no value in the test data breaks an ATL105 rule.

## Items to confirm

### 1. The quote cited for p.512 is not word-for-word in ATL105 - REVIEW

- **Where:** test case spec_basis
- **Value:** `40 Maestro Direct - Authorizer Type: Debit`
- **What ATL105 says:**
  - (no specific ATL105 page)
- **Why:** The test case quotes "40 Maestro Direct - Authorizer Type: Debit" for p.512. Not word-for-word: all its words are on the cited page, but not in this form.
- **What it should be / action:** Quote the text exactly as it appears on the page.

## Spec text quoted by the test case

| Cited page | Quote in the test case | Check | Result |
|---|---|---|---|
| PDF p.96 (printed 10-17) | Manual entry is not allowed on a debit card transaction. | Found word-for-word on the cited page | PASS |
| PDF p.96 (printed 10-17) | If unable to read the magnetic strip after three attempts, a message should be displayed to the clerk and the transaction ended. | Found word-for-word on the cited page | PASS |
| PDF p.512 (printed Appendix C-2) | 40 Maestro Direct - Authorizer Type: Debit | Not word-for-word: all its words are on the cited page, but not in this form | REVIEW |
| PDF p.97 (printed 10-18) | The following point-of-sale transactions are supported for debit card processing: ... Purchase/Capture | Found word-for-word on the cited page | PASS |
| PDF p.81 (printed 10-2) | If a Pre-Auth transaction is approved offline, the full track data must be sent. Also if a Sale transaction is approved offline, the full track data must be sent | Found word-for-word on the cited page | PASS |

