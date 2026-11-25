# TC-002112 (BR1_TS17_TC3) - ATL105 Test Data Validation

**BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Credential on File Pre-authorization → Completion for a different final amount**

| Item | Value |
|---|---|
| Result | **FAIL** |
| Can it run as-is? | No - fix the FAIL items first |
| Test data | `MasterCard Credit/mastercard_credit/test_data/TD-TC-002112.step1.auth-only.json`, `MasterCard Credit/mastercard_credit/test_data/TD-TC-002112.step2.completion.json` |
| Template / status | `T5-AUT-COMPLETION-DIFF` / `requires_review` |
| Specification | BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026) |

All page references are to the BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026). "PDF p." is the page number shown in the PDF viewer; "printed" is the page number in the document footer.

- **FAIL** - a value in the test data breaks an ATL105 rule. It must be fixed.
- **BLOCKED** - the field holds instruction text instead of a value. It must be filled in from the step 1 response before the message can be sent.
- **REVIEW** - the data values are acceptable, but something in the test case must be confirmed or corrected.
- **INFO** - a note for the team that builds the wire message.
- **PASS** - matches ATL105.

## What must be fixed

### 1. Table 030 (POS Condition Code) is missing - FAIL

- **Step:** 1
- **Where:** TD-TC-002112.step1.auth-only.json -> Variable Information Data Segment
- **Value:** `tables sent: 005, 031`
- **What ATL105 says:**
  - Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030), PDF p.568 (printed Appendix I-19): "Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions."
- **Why:** This step is a Credential on File transaction. Table 030 identifies the device class, presentation data, security condition and terminal type, and ATL105 requires it for all EMV and all Credential on File transactions.
- **What it should be / action:** Add Table 030 (10 digits, values from Appendix Q) for the certified terminal.

### 2. The Credential on File indicators are missing - FAIL

- **Step:** 1
- **Where:** TD-TC-002112.step1.auth-only.json -> Variable Information Data Segment
- **Value:** `tables sent: 005, 031`
- **What ATL105 says:**
  - Appendix I. Variable Information Data Layouts - POS Additional Information (Table 032), Sub Table 08, PDF p.575 (printed Appendix I-26): "It is merchant’s responsibility to send the indicator as Initial (“I”) or Subsequent Credential on File (“C”) from the terminal for all COF transactions (Authorization, Completion, Cancellation, Reversal etc.)."
  - Appendix I. Variable Information Data Layouts - Merchant Supplementary Data (Table 049), Sub Table 02, PDF p.589 (printed Appendix I-40): "This field is required for all subsequent Credential on File transactions."
  - Section 10.13.2.4.2 Stored Credential Use Cases, PDF p.144 (printed 10-65): "Table ID 56 (Stored Credentials Transaction Additional Data), Sub Table ID 09 (MIT/CIT category and subcategory data) used to indicate category and subcategory data for Cardholder initiated transaction (CIT) and Merchant initiated transactions (MIT) for MasterCard card type."
  - Appendix J. Valid Point-of-Service Entry Mode Codes, PDF p.638 (printed Appendix J-1): "10 Credential on file"
- **Why:** Table 005 entry mode 10 marks a transaction with stored credentials. ATL105 requires the merchant to send the Initial/Credential on File indicator for all COF transactions, the Transaction Initiation Indicator for all subsequent COF transactions and, for MasterCard, the MIT/CIT category. Missing: Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'); Table 056 sub-table 09 (MasterCard MIT/CIT category).
- **What it should be / action:** Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'); Table 056 sub-table 09 (MasterCard MIT/CIT category).

### 3. Table 030 (POS Condition Code) is missing - FAIL

- **Step:** 2
- **Where:** TD-TC-002112.step2.completion.json -> Variable Information Data Segment
- **Value:** `tables sent: 005, 031`
- **What ATL105 says:**
  - Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030), PDF p.568 (printed Appendix I-19): "Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions."
- **Why:** This step is a Credential on File transaction. Table 030 identifies the device class, presentation data, security condition and terminal type, and ATL105 requires it for all EMV and all Credential on File transactions.
- **What it should be / action:** Add Table 030 (10 digits, values from Appendix Q) for the certified terminal.

### 4. The Credential on File indicators are missing - FAIL

- **Step:** 2
- **Where:** TD-TC-002112.step2.completion.json -> Variable Information Data Segment
- **Value:** `tables sent: 005, 031`
- **What ATL105 says:**
  - Appendix I. Variable Information Data Layouts - POS Additional Information (Table 032), Sub Table 08, PDF p.575 (printed Appendix I-26): "It is merchant’s responsibility to send the indicator as Initial (“I”) or Subsequent Credential on File (“C”) from the terminal for all COF transactions (Authorization, Completion, Cancellation, Reversal etc.)."
  - Appendix I. Variable Information Data Layouts - Merchant Supplementary Data (Table 049), Sub Table 02, PDF p.589 (printed Appendix I-40): "This field is required for all subsequent Credential on File transactions."
  - Section 10.13.2.4.2 Stored Credential Use Cases, PDF p.144 (printed 10-65): "Table ID 56 (Stored Credentials Transaction Additional Data), Sub Table ID 09 (MIT/CIT category and subcategory data) used to indicate category and subcategory data for Cardholder initiated transaction (CIT) and Merchant initiated transactions (MIT) for MasterCard card type."
  - Appendix J. Valid Point-of-Service Entry Mode Codes, PDF p.638 (printed Appendix J-1): "10 Credential on file"
- **Why:** Table 005 entry mode 10 marks a transaction with stored credentials. ATL105 requires the merchant to send the Initial/Credential on File indicator for all COF transactions, the Transaction Initiation Indicator for all subsequent COF transactions and, for MasterCard, the MIT/CIT category. Missing: Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'); Table 056 sub-table 09 (MasterCard MIT/CIT category).
- **What it should be / action:** Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'); Table 056 sub-table 09 (MasterCard MIT/CIT category).

## Values to fill in at run time

### 5. Approval Number holds instruction text, not a value - BLOCKED

- **Step:** 2
- **Where:** TD-TC-002112.step2.completion.json -> Standard Message Data Segment -> ApprovalNumber
- **Value:** `<ApprovalNumber from step 1 response>`
- **What ATL105 says:**
  - Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization."
  - Appendix G. Valid Transaction Type Codes, PDF p.545 (printed Appendix G-1): "0 POS Purchase/Capture Point-of-sale purchase/preauthorized completion"
- **Why:** The field holds the instruction text '<ApprovalNumber from step 1 response>' instead of a real Approval Number. BUYPASS cannot process this text, so the message cannot be sent until the real value from the step 1 response is filled in.
- **What it should be / action:** Fill in at run time: Element No. 5 (Approval Number)-present in the original transaction-must be present and identical (runtime link to the original's response, never invented)..

## Items to confirm

### 6. Expiry format for stored credentials is not stated - REVIEW

- **Where:** Card Discretionary Block Data (entry mode 10 steps)
- **Value:** `0630`
- **What ATL105 says:**
  - Element 12 Card Discretionary Block Data (Chapter 13), PDF p.372 (printed 13-32): "For a card whose number has been manually entered, the expiration date is in MMYY format"
  - Section 10.1.8.2 Track Support Requirements, PDF p.94 (printed 10-15): "The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM)."
- **Why:** The stored-credential steps send the expiry as MMYY, the format for manually entered cards. ATL105 also says that card data the merchant stored is sent as Account Number = YYMM. Entry mode 10 uses stored credentials, and the spec does not say which of the two applies.
- **What it should be / action:** Confirm the expiry format for entry mode 10 with BUYPASS.

### 7. Outdated page cited for the POS Entry Mode table (Table 005) - REVIEW

- **Where:** test case request -> Variable Information Data Segment -> Variable Information (source_page), every step
- **Value:** `p.548`
- **What ATL105 says:**
  - Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005), PDF p.555 (printed Appendix I-6): "Point-of-Service Entry Mode Information"
- **Why:** The test case cites p.548 for the POS Entry Mode table (Table 005). In ATL105 2026-4, PDF p.548 (printed Appendix H-2) does not contain it; it is on PDF p.555 (printed Appendix I-6). p.548 is where it was in the older 2026-3 edition.
- **What it should be / action:** Change the cited page from 548 to 555. The data values are not affected.

### 8. The Approval Number rule cites the wrong section - REVIEW

- **Step:** 2
- **Where:** test case step 2 -> link rule for Approval Number
- **Value:** `p.85`
- **What ATL105 says:**
  - Section 10.1.2.2 Authorization Only Reversal Transaction, PDF p.85 (printed 10-6): "Element No. 5 (Approval Number)—present in the original transaction—must be present and identical in the Authorization Only Reversal transaction."
  - Appendix G. Valid Transaction Type Codes, PDF p.545 (printed Appendix G-1): "0 POS Purchase/Capture Point-of-sale purchase/preauthorized completion"
  - Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization."
- **Why:** p.85 is Section 10.1.2.2 Authorization Only Reversal Transaction: it covers the Authorization Only Reversal (Transaction Type S), while this step is a preauthorized completion (Prompt Code 0020, Transaction Type 0).
- **What it should be / action:** Cite Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28) instead. The data value itself is correct.

### 9. The quote cited for p.511 is not word-for-word in ATL105 - REVIEW

- **Where:** test case spec_basis
- **Value:** `03 MasterCard - Authorizer Type: Credit`
- **What ATL105 says:**
  - Appendix C. Valid Authorizer Codes, PDF p.511 (printed Appendix C-1): "03 MasterCard® Credit"
- **Why:** The test case quotes "03 MasterCard - Authorizer Type: Credit" for p.511. Not word-for-word: all its words are on the cited page, but not in this form.
- **What it should be / action:** Quote the text exactly as it appears: "03 MasterCard® Credit".

### 10. The Terminal Identifier is a placeholder - REVIEW

- **Where:** Standard Message Data Segment -> TerminalIdentifier (every step)
- **Value:** `JX72948216050257001`
- **What ATL105 says:**
  - Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."
  - Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code)."
  - Appendix D. Valid State Codes, PDF p.517 (printed Appendix D-4): "Yukon YT 72"
- **Why:** The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada.
- **What it should be / action:** Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location.

## Notes for the message builder

### 11. Join Account Number and expiry with '=' in the wire message - INFO

- **Step:** 2
- **Where:** TD-TC-002112.step2.completion.json -> AccountNumber, CardDiscretionaryBlockData
- **Value:** `5169477457161640 / 3006`
- **What ATL105 says:**
  - Section 10.1.8.2 Track Support Requirements, PDF p.94 (printed 10-15): "The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM)."
- **Why:** The JSON keeps Account Number and the expiry as separate fields. In the real message for this follow-on transaction they are sent as Account Number, '=', YYMM - not separated by a Field Separator. The Segment Length is the same either way.
- **What it should be / action:** The message builder sends 5169477457161640=3006.

## Field-by-field check - step 1, auth only (`TD-TC-002112.step1.auth-only.json`)

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
| 100 | 6 | 2 | Account Number | `5169477457161640` | `5169477457161640` | Up to 24 characters; valid check digit and MasterCard range 51-55 / 2221-2720 (card-industry rule) | Element 2, PDF p.364 (printed 13-24) | PASS |
| 100 | 7 | 12 | Card Discretionary Block Data | `0630` | `0630` | Stored credential: expiry MMYY = June 2030 | Element 12, PDF p.372 (printed 13-32); Section 10.1.1.3, PDF p.81 (printed 10-2); Appendix J, PDF p.638 (printed J-1) | PASS |
| 100 | 8 | 33 | Encrypted PIN Block Data | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 9 | 79 | Pump/Lane Number | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 10 | 41 | Fuel Purchase Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 11 | 58 | Nonfuel Amount | `1500` | `1500` | Digits only, at most 7 for MasterCard (8 only for Amex): '1500' = $15.00 | Element 58, PDF p.393 (printed 13-53) | PASS |
| 100 | 12 | 99 | Tax Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 13 | 17 | Cash Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 14 | 86 | Sequence Number | `021121` | `021121` | 6 digits, 000001-999999 | Element 86, PDF p.409 (printed 13-69) | PASS |
| 100 | 15 | 5 | Approval Number | `(empty)` | `(not sent)` | Not sent on a new transaction | Element 5, PDF p.368 (printed 13-28) | PASS |
| 100 | 16 | 49 | Local Date and Local Time | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 17 | 121 | Partial Approval Indicator | `1` | `1` | '1' = partial approval supported | Element 121, PDF p.440 (printed 13-100) | PASS |
| 111 | 1 | 85 | Segment Type | `111` | `111` | Fixed value 111 | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 2 | 84 | Segment Length | `026` | `-` | 3 (type) + 3 (length) + 17 (tables) + 3 Field Separators = 026 | Section 12.10, PDF p.246 (printed 12-29); Element 84, PDF p.406 (printed 13-66) | PASS |
| 111 | 3 [1] | 111 | Variable Information Indicator | `005` | `-` | 3-character table ID (Table 005) | Element 111, PDF p.422 (printed 13-82) | PASS |
| 111 | 4 [1] | 112 | Variable Information Length | `003` | `-` | 3 digits = length of the table data (003) | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 5 [1] | 113 | Variable Information (Table 005) | `101` | `101` | '10' = credential on file; terminal capability '1' = PIN entry capability | Appendix I, PDF p.555 (printed I-6); Appendix J, PDF p.638 (printed J-1); Appendix J, PDF p.639 (printed J-2) | PASS |
| 111 | 3 [2] | 111 | Variable Information Indicator | `031` | `-` | 3-character table ID (Table 031) | Element 111, PDF p.422 (printed 13-82) | PASS |
| 111 | 4 [2] | 112 | Variable Information Length | `002` | `-` | 3 digits = length of the table data (002) | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 5 [2] | 113 | Variable Information (Table 031) | `12` | `12` | '12' = hybrid reader: contact chip and contactless chip; required on every transaction | Appendix I, PDF p.569 (printed I-20) | PASS |

## Field-by-field check - step 2, completion (`TD-TC-002112.step2.completion.json`)

| Segment | Field No. | Element | Field | Test data value | Test case value | ATL105 rule | Where in ATL105 | Result |
|---|---|---|---|---|---|---|---|---|
| Message | - | - | Message type | `Financial Transaction Request` | `-` | Financial Transaction Request (no chip data) | Section 11.1, PDF p.167 (printed 11-4) | PASS |
| Data Section 1 | 1 | 55 | Message Format Version Identifier | `ATL105` | `ATL105` | Fixed 6 characters: ATL105 | Section 11.1, PDF p.167 (printed 11-4); Element 55, PDF p.391 (printed 13-51) | PASS |
| Data Section 1 | 2 | 63 | Number of Segments | `02` | `-` | Up to 2 digits; equals the number of segments sent (2) | Section 11.1, PDF p.167 (printed 11-4); Element 63, PDF p.395 (printed 13-55) | PASS |
| 100 | 1 | 85 | Segment Type | `100` | `-` | Fixed value 100 | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 2 | 84 | Segment Length | `084` | `-` | Length of all 17 fields + 17 Field Separators = 084 (using the test case value for Approval Number); with the real run-time value(s) it is 084 - the declared value fits | Section 12.1, PDF p.223 (printed 12-6); Element 84, PDF p.406 (printed 13-66) | PASS |
| 100 | 3 | 44 | Information Byte | `0` | `0` | 1 character: '0' = single-message transmission | Element 44, PDF p.386 (printed 13-46) | PASS |
| 100 | 4 | 102 | Terminal Identifier | `JX72948216050257001` | `JX72948216050257001` | Device Type 'JX' + State Code '72' = Yukon (YT), Canada + Merchant Number and Device Number '948216050257001' | Element 102, PDF p.417 (printed 13-77); Appendix D, PDF p.517 (printed D-4) | PASS |
| 100 | 5 | 78 | Prompt Code | `0020` | `0020` | Transaction Type '0' = POS Purchase/Capture; Card Type '020' = Credit | Element 78, PDF p.400 (printed 13-60); Appendix G, PDF p.545 (printed G-1); Appendix E, PDF p.521 (printed E-4) | PASS |
| 100 | 6 | 2 | Account Number | `5169477457161640` | `5169477457161640` | Up to 24 characters; valid check digit and MasterCard range 51-55 / 2221-2720 (card-industry rule) | Element 2, PDF p.364 (printed 13-24) | PASS |
| 100 | 7 | 12 | Card Discretionary Block Data | `3006` | `3006` | Follow-on transaction: expiry as YYMM only (original MMYY 0630 -> 3006) | Section 10.1.8.2, PDF p.94 (printed 10-15); Section 10.1.1.2, PDF p.81 (printed 10-2) | PASS |
| 100 | 8 | 33 | Encrypted PIN Block Data | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 9 | 79 | Pump/Lane Number | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 10 | 41 | Fuel Purchase Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 11 | 58 | Nonfuel Amount | `0750` | `0750` | Digits only, at most 7 for MasterCard (8 only for Amex): '0750' = $7.50 | Element 58, PDF p.393 (printed 13-53) | PASS |
| 100 | 12 | 99 | Tax Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 13 | 17 | Cash Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 14 | 86 | Sequence Number | `021121` | `021121` | 6 digits, 000001-999999 | Element 86, PDF p.409 (printed 13-69) | PASS |
| 100 | 15 | 5 | Approval Number | `<ApprovalNumber from step 1 response>` | `YCKVE7` | Filled in at run time: Element No. 5 (Approval Number)-present in the original transaction-must be present and identical (runtime link to the original's response, never invented). | Element 5, PDF p.368 (printed 13-28); Appendix G, PDF p.545 (printed G-1) | BLOCKED |
| 100 | 16 | 49 | Local Date and Local Time | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 17 | 121 | Partial Approval Indicator | `1` | `1` | '1' = partial approval supported | Element 121, PDF p.440 (printed 13-100) | PASS |
| 111 | 1 | 85 | Segment Type | `111` | `111` | Fixed value 111 | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 2 | 84 | Segment Length | `026` | `-` | 3 (type) + 3 (length) + 17 (tables) + 3 Field Separators = 026 | Section 12.10, PDF p.246 (printed 12-29); Element 84, PDF p.406 (printed 13-66) | PASS |
| 111 | 3 [1] | 111 | Variable Information Indicator | `005` | `-` | 3-character table ID (Table 005) | Element 111, PDF p.422 (printed 13-82) | PASS |
| 111 | 4 [1] | 112 | Variable Information Length | `003` | `-` | 3 digits = length of the table data (003) | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 5 [1] | 113 | Variable Information (Table 005) | `101` | `101` | '10' = credential on file; terminal capability '1' = PIN entry capability | Appendix I, PDF p.555 (printed I-6); Appendix J, PDF p.638 (printed J-1); Appendix J, PDF p.639 (printed J-2) | PASS |
| 111 | 3 [2] | 111 | Variable Information Indicator | `031` | `-` | 3-character table ID (Table 031) | Element 111, PDF p.422 (printed 13-82) | PASS |
| 111 | 4 [2] | 112 | Variable Information Length | `002` | `-` | 3 digits = length of the table data (002) | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 5 [2] | 113 | Variable Information (Table 031) | `12` | `12` | '12' = hybrid reader: contact chip and contactless chip; required on every transaction | Appendix I, PDF p.569 (printed I-20) | PASS |

## Step-to-step checks

| Step | Check | This step | Original step | Where in ATL105 | Result |
|---|---|---|---|---|---|
| 2 | Same Sequence Number as the original | `021121` | `021121` | Element 86, PDF p.409 (printed 13-69) | PASS |
| 2 | Same Account Number as the original | `5169477457161640` | `5169477457161640` | Section 2.3.1, PDF p.40 (printed 2-5) | PASS |
| 2 | Same terminal as the original | `JX72948216050257001` | `JX72948216050257001` | Section 2.3.1, PDF p.40 (printed 2-5) | PASS |
| 2 | Same POS entry mode as the original | `10` | `10` | Appendix J, PDF p.638 (printed J-1) | PASS |

## Expected response codes

| Step | Expected Response Code | Meaning | Where in ATL105 | Result |
|---|---|---|---|---|
| 1 | `2` | Approved - Authorization only | Element 83, PDF p.404 (printed 13-64) | PASS |
| 2 | `0` | Approved - Purchase/Capture | Element 83, PDF p.404 (printed 13-64) | PASS |

## Spec text quoted by the test case

| Cited page | Quote in the test case | Check | Result |
|---|---|---|---|
| PDF p.545 (printed Appendix G-1) | Preauthorization is a request for an estimated amount, the amount of the original authorization may be adjusted if the final transaction amount is different than the original authorization amount requested | Found word-for-word on the cited page | PASS |
| PDF p.511 (printed Appendix C-1) | 03 MasterCard - Authorizer Type: Credit | Not word-for-word: all its words are on the cited page, but not in this form | REVIEW |
| PDF p.83 (printed 10-4) | The following point-of-sale transactions are supported for credit card processing: ... Authorization Only | Found word-for-word on the cited page | PASS |
| PDF p.638 (printed Appendix J-1) | Entry Mode 10 is applicable to Visa Credit, MasterCard Credit, Discover Credit, and Amex transactions | Found word-for-word on the cited page | PASS |

