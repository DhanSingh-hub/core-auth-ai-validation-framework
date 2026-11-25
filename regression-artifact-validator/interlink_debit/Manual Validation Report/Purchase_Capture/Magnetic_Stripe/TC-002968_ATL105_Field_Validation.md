# TC-002968 (BR1_TS3_TC28) - ATL105 Test Data Validation

**BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Store and Forward blocking window (card type 173) → no off-line approval inside the window**

| Item | Value |
|---|---|
| Result | **REVIEW** |
| Can it run as-is? | Yes, once the real Terminal Identifier is supplied |
| Test data | none – device-only test case: no message is sent to BUYPASS |
| Template / status | `T11-SAF-BLOCKED` / `pending_sme` |
| Specification | BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026) |

All page references are to the BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026). "PDF p." is the page number shown in the PDF viewer; "printed" is the page number in the document footer.

- **FAIL** - a value in the test data breaks an ATL105 rule. It must be fixed.
- **BLOCKED** - the field holds instruction text instead of a value. It must be filled in from the step 1 response before the message can be sent.
- **REVIEW** - the data values are acceptable, but something in the test case must be confirmed or corrected.
- **INFO** - a note for the team that builds the wire message.
- **PASS** - matches ATL105.

## Device-only test case

No message is sent to BUYPASS, so there is no test data to check against the ATL105 message layouts. The check covers the ATL105 text the test case quotes (end of this report).

- **Objective:** Store and Forward blocking window (card type 173) -> no off-line approval inside the window
- **Procedure (on the device):** Table load with card type 173 and a Store and Forward Data Segment (DL6) Start Time / End Time window; Take the line to BUYPASS down inside the window and start a Purchase; Repeat outside the window
- **Expected behaviour:** Inside the window: store and forward (off-line approval) is blocked; Outside the window: off-line processing follows the device's off-line mode (p.40)

## What must be fixed

Nothing - no value in the test data breaks an ATL105 rule.

## Items to confirm

### 1. The quote cited for p.512 is not word-for-word in ATL105 - REVIEW

- **Where:** test case spec_basis
- **Value:** `48 Interlink - Authorizer Type: Debit`
- **What ATL105 says:**
  - (no specific ATL105 page)
- **Why:** The test case quotes "48 Interlink - Authorizer Type: Debit" for p.512. Not word-for-word: all its words are on the cited page, but not in this form.
- **What it should be / action:** Quote the text exactly as it appears on the page.

## Spec text quoted by the test case

| Cited page | Quote in the test case | Check | Result |
|---|---|---|---|
| PDF p.520 (printed Appendix E-3) | 173 Store and Forward Enables Store and Forward blocking for a designated range of time each day. | Found word-for-word on the cited page | PASS |
| PDF p.335 (printed 12-118) | Indicates the time to begin blocking store and forward processing. | Found word-for-word on the cited page | PASS |
| PDF p.335 (printed 12-118) | Indicates the time to stop blocking store and forward processing. | Found word-for-word on the cited page | PASS |
| PDF p.512 (printed Appendix C-2) | 48 Interlink - Authorizer Type: Debit | Not word-for-word: all its words are on the cited page, but not in this form | REVIEW |
| PDF p.97 (printed 10-18) | The following point-of-sale transactions are supported for debit card processing: ... Purchase/Capture | Found word-for-word on the cited page | PASS |
| PDF p.81 (printed 10-2) | If a Pre-Auth transaction is approved offline, the full track data must be sent. Also if a Sale transaction is approved offline, the full track data must be sent | Found word-for-word on the cited page | PASS |

