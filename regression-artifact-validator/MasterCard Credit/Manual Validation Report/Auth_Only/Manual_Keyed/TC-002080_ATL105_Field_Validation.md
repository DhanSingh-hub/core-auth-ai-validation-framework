# TC-002080 (BR1_TS15_TC2) - ATL105 Test Data Validation

**BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Manual / Keyed 3 failed swipes → keyed entry → approved**

| Item | Value |
|---|---|
| Result | **REVIEW** |
| Can it run as-is? | Yes, once the real Terminal Identifier is supplied |
| Test data | `MasterCard Credit/mastercard_credit/test_data/TD-TC-002080.json` |
| Template / status | `T2-3SWIPES` / `ready` |
| Specification | BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026) |

All page references are to the BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026). "PDF p." is the page number shown in the PDF viewer; "printed" is the page number in the document footer.

- **FAIL** - a value in the test data breaks an ATL105 rule. It must be fixed.
- **BLOCKED** - the field holds instruction text instead of a value. It must be filled in from the step 1 response before the message can be sent.
- **REVIEW** - the data values are acceptable, but something in the test case must be confirmed or corrected.
- **INFO** - a note for the team that builds the wire message.
- **PASS** - matches ATL105.

## What must be fixed

Nothing - no value in the test data breaks an ATL105 rule.

## Items to confirm

### 1. Outdated page cited for the POS Entry Mode table (Table 005) - REVIEW

- **Where:** test case request -> Variable Information Data Segment -> Variable Information (source_page), every step
- **Value:** `p.548`
- **What ATL105 says:**
  - Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005), PDF p.555 (printed Appendix I-6): "Point-of-Service Entry Mode Information"
- **Why:** The test case cites p.548 for the POS Entry Mode table (Table 005). In ATL105 2026-4, PDF p.548 (printed Appendix H-2) does not contain it; it is on PDF p.555 (printed Appendix I-6). p.548 is where it was in the older 2026-3 edition.
- **What it should be / action:** Change the cited page from 548 to 555. The data values are not affected.

### 2. The quote cited for p.511 is not word-for-word in ATL105 - REVIEW

- **Where:** test case spec_basis
- **Value:** `03 MasterCard - Authorizer Type: Credit`
- **What ATL105 says:**
  - Appendix C. Valid Authorizer Codes, PDF p.511 (printed Appendix C-1): "03 MasterCard® Credit"
- **Why:** The test case quotes "03 MasterCard - Authorizer Type: Credit" for p.511. Not word-for-word: all its words are on the cited page, but not in this form.
- **What it should be / action:** Quote the text exactly as it appears: "03 MasterCard® Credit".

### 3. The Terminal Identifier is a placeholder - REVIEW

- **Where:** Standard Message Data Segment -> TerminalIdentifier (every step)
- **Value:** `JX72948216050257001`
- **What ATL105 says:**
  - Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."
  - Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code)."
  - Appendix D. Valid State Codes, PDF p.517 (printed Appendix D-4): "Yukon YT 72"
- **Why:** The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada.
- **What it should be / action:** Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location.

### 4. Marked 'ready' although the terminal is a placeholder - REVIEW

- **Where:** test case status
- **Value:** `ready`
- **What ATL105 says:**
  - Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."
- **Why:** The test case status is 'ready', but its Terminal Identifier is still a placeholder.
- **What it should be / action:** Keep the test case out of 'ready' until the real Terminal Identifier is supplied.

### 5. Same request as TC-002079 - REVIEW

- **Where:** test_data/TD-TC-002080.json
- **Value:** `identical to TD-TC-002079.json except SequenceNumber`
- **What ATL105 says:**
  - Section 10.1.1.3 Manual Entry, PDF p.81 (printed 10-2): "If unable to read the magnetic strip after three attempts, the device should prompt for manual entry of the Account Number and Expiration Date (Format: MMYY)."
  - Section 10.1.1.3 Manual Entry, PDF p.81 (printed 10-2): "If manual entry is used, this fact is indicated on the receipt and journal by an asterisk (*) before the Account Number."
- **Why:** The request is identical to TC-002079 (T1-AUT) except the Sequence Number: same card 514031...3116, expiry 0932, amount $15.00 and entry mode 011. What makes this test different (Device prompts to swipe again after each of the first three failed reads; no host message meanwhile (p.81); Manual-entry prompt asks for Account Number and Expiration Date (MMYY) (p.81); Receipt and journal mark manual entry with '*' before the Account Number (p.81)) happens on the device and is not visible in the message sent to BUYPASS.
- **What it should be / action:** Verify on the device: Device prompts to swipe again after each of the first three failed reads; no host message meanwhile (p.81); Manual-entry prompt asks for Account Number and Expiration Date (MMYY) (p.81); Receipt and journal mark manual entry with '*' before the Account Number (p.81). Optionally use a different card so the logs can tell the two tests apart.

## Field-by-field check - step 1, auth only (`TD-TC-002080.json`)

| Segment | Field No. | Element | Field | Test data value | Test case value | ATL105 rule | Where in ATL105 | Result |
|---|---|---|---|---|---|---|---|---|
| Message | - | - | Message type | `Financial Transaction Request` | `-` | Financial Transaction Request (no chip data) | Section 11.1, PDF p.167 (printed 11-4) | PASS |
| Data Section 1 | 1 | 55 | Message Format Version Identifier | `ATL105` | `ATL105` | Fixed 6 characters: ATL105 | Section 11.1, PDF p.167 (printed 11-4); Element 55, PDF p.391 (printed 13-51) | PASS |
| Data Section 1 | 2 | 63 | Number of Segments | `02` | `-` | Up to 2 digits; equals the number of segments sent (2) | Section 11.1, PDF p.167 (printed 11-4); Element 63, PDF p.395 (printed 13-55) | PASS |
| 100 | 1 | 85 | Segment Type | `100` | `-` | Fixed value 100 | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 2 | 84 | Segment Length | `078` | `-` | Length of all 17 fields + 17 Field Separators = 078 | Section 12.1, PDF p.223 (printed 12-6); Element 84, PDF p.406 (printed 13-66) | PASS |
| 100 | 3 | 44 | Information Byte | `0` | `0` | 1 character: '0' = single-message transmission | Element 44, PDF p.386 (printed 13-46) | PASS |
| 100 | 4 | 102 | Terminal Identifier | `JX72948216050257001` | `JX72948216050257001` | Device Type 'JX' + State Code '72' = Yukon (YT), Canada + Merchant Number and Device Number '948216050257001' | Element 102, PDF p.417 (printed 13-77); Appendix D, PDF p.517 (printed D-4) | PASS |
| 100 | 5 | 78 | Prompt Code | `3020` | `3020` | Transaction Type '3' = POS Authorization Only; Card Type '020' = Credit | Element 78, PDF p.400 (printed 13-60); Appendix G, PDF p.545 (printed G-1); Appendix E, PDF p.521 (printed E-4) | PASS |
| 100 | 6 | 2 | Account Number | `5140312601563116` | `5140312601563116` | Up to 24 characters; valid check digit and MasterCard range 51-55 / 2221-2720 (card-industry rule) | Element 2, PDF p.364 (printed 13-24) | PASS |
| 100 | 7 | 12 | Card Discretionary Block Data | `0932` | `0932` | Keyed card: expiry MMYY = September 2032 | Element 12, PDF p.372 (printed 13-32); Section 10.1.1.3, PDF p.81 (printed 10-2) | PASS |
| 100 | 8 | 33 | Encrypted PIN Block Data | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 9 | 79 | Pump/Lane Number | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 10 | 41 | Fuel Purchase Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 11 | 58 | Nonfuel Amount | `1500` | `1500` | Digits only, at most 7 for MasterCard (8 only for Amex): '1500' = $15.00 | Element 58, PDF p.393 (printed 13-53) | PASS |
| 100 | 12 | 99 | Tax Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 13 | 17 | Cash Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 14 | 86 | Sequence Number | `020801` | `020801` | 6 digits, 000001-999999 | Element 86, PDF p.409 (printed 13-69) | PASS |
| 100 | 15 | 5 | Approval Number | `(empty)` | `(not sent)` | Not sent on a new transaction | Element 5, PDF p.368 (printed 13-28) | PASS |
| 100 | 16 | 49 | Local Date and Local Time | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 17 | 121 | Partial Approval Indicator | `1` | `1` | '1' = partial approval supported | Element 121, PDF p.440 (printed 13-100) | PASS |
| 111 | 1 | 85 | Segment Type | `111` | `111` | Fixed value 111 | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 2 | 84 | Segment Length | `026` | `-` | 3 (type) + 3 (length) + 17 (tables) + 3 Field Separators = 026 | Section 12.10, PDF p.246 (printed 12-29); Element 84, PDF p.406 (printed 13-66) | PASS |
| 111 | 3 [1] | 111 | Variable Information Indicator | `005` | `-` | 3-character table ID (Table 005) | Element 111, PDF p.422 (printed 13-82) | PASS |
| 111 | 4 [1] | 112 | Variable Information Length | `003` | `-` | 3 digits = length of the table data (003) | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 5 [1] | 113 | Variable Information (Table 005) | `011` | `011` | '01' = manual/keyed; terminal capability '1' = PIN entry capability | Appendix I, PDF p.555 (printed I-6); Appendix J, PDF p.638 (printed J-1); Appendix J, PDF p.639 (printed J-2) | PASS |
| 111 | 3 [2] | 111 | Variable Information Indicator | `031` | `-` | 3-character table ID (Table 031) | Element 111, PDF p.422 (printed 13-82) | PASS |
| 111 | 4 [2] | 112 | Variable Information Length | `002` | `-` | 3 digits = length of the table data (002) | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 5 [2] | 113 | Variable Information (Table 031) | `12` | `12` | '12' = hybrid reader: contact chip and contactless chip; required on every transaction | Appendix I, PDF p.569 (printed I-20) | PASS |

## Expected response codes

| Step | Expected Response Code | Meaning | Where in ATL105 | Result |
|---|---|---|---|---|
| 1 | `2` | Approved - Authorization only | Element 83, PDF p.404 (printed 13-64) | PASS |

## Spec text quoted by the test case

| Cited page | Quote in the test case | Check | Result |
|---|---|---|---|
| PDF p.81 (printed 10-2) | If the device is unable to read the card's magnetic strip, it should prompt the user to swipe the card again-up to three times. | Found word-for-word on the cited page | PASS |
| PDF p.81 (printed 10-2) | If unable to read the magnetic strip after three attempts, the device should prompt for manual entry of the Account Number and Expiration Date (Format: MMYY). | Found word-for-word on the cited page | PASS |
| PDF p.511 (printed Appendix C-1) | 03 MasterCard - Authorizer Type: Credit | Not word-for-word: all its words are on the cited page, but not in this form | REVIEW |
| PDF p.83 (printed 10-4) | The following point-of-sale transactions are supported for credit card processing: ... Authorization Only | Found word-for-word on the cited page | PASS |
| PDF p.81 (printed 10-2) | Manual entry is allowed on a credit card transaction | Found word-for-word on the cited page | PASS |

