# TC-003412 (BR1_TS13_TC7) - ATL105 Test Data Validation

**BUYPASS ATL105 Maestro Direct Debit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Preauthorization disabled (card type 168) → not offered at the device**

| Item | Value |
|---|---|
| Result | **REVIEW** |
| Can it run as-is? | Yes, once the real Terminal Identifier is supplied |
| Test data | none – device-only test case: no message is sent to BUYPASS |
| Template / status | `T11-DISABLED-PREAUTH` / `pending_sme` |
| Specification | BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026) |

All page references are to the BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026). "PDF p." is the page number shown in the PDF viewer; "printed" is the page number in the document footer.

- **FAIL** - a value in the test data breaks an ATL105 rule. It must be fixed.
- **BLOCKED** - the field holds instruction text instead of a value. It must be filled in from the step 1 response before the message can be sent.
- **REVIEW** - the data values are acceptable, but something in the test case must be confirmed or corrected.
- **INFO** - a note for the team that builds the wire message.
- **PASS** - matches ATL105.

## Device-only test case

No message is sent to BUYPASS, so there is no test data to check against the ATL105 message layouts. The check covers the ATL105 text the test case quotes (end of this report).

- **Objective:** Preauthorization disabled (card type 168) -> not offered at the device
- **Procedure (on the device):** Table load with card type 168; Try to start an Authorization Only (preauthorization)
- **Expected behaviour:** The device does not offer the Preauthorization function for this card; no request is sent to BUYPASS

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
| PDF p.520 (printed Appendix E-3) | 168 Disable a function Disables the Preauthorization function except for proprietary cards. | Found word-for-word on the cited page | PASS |
| PDF p.512 (printed Appendix C-2) | 40 Maestro Direct - Authorizer Type: Debit | Not word-for-word: all its words are on the cited page, but not in this form | REVIEW |
| PDF p.97 (printed 10-18) | The following point-of-sale transactions are supported for debit card processing: ... Authorization Only | Found word-for-word on the cited page | PASS |
| PDF p.638 (printed Appendix J-1) | also applicable to the subsequent Debit Completions and Debit Reversals when the original transaction was initiated as a Credit COF transaction | Found word-for-word on the cited page | PASS |

