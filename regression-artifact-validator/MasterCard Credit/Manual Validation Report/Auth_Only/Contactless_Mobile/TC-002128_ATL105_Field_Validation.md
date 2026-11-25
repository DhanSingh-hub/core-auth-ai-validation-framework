# TC-002128 (BR1_TS18_TC8) - ATL105 Test Data Validation

**BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – Contactless Mobile Commerce Device Completion timed out → Time-out Reversal → Completion resubmitted**

| Item | Value |
|---|---|
| Result | **BLOCKED** |
| Can it run as-is? | Only after the run-time values are filled in from the step 1 response |
| Test data | `MasterCard Credit/mastercard_credit/test_data/TD-TC-002128.step1.auth-only.json`, `MasterCard Credit/mastercard_credit/test_data/TD-TC-002128.step2.completion.json`, `MasterCard Credit/mastercard_credit/test_data/TD-TC-002128.step3.timeout-reversal.json`, `MasterCard Credit/mastercard_credit/test_data/TD-TC-002128.step4.completion-resubmitted.json` |
| Template / status | `T6-AUT-COMPLETION-TOR` / `pending_sme` |
| Specification | BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026) |

All page references are to the BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026). "PDF p." is the page number shown in the PDF viewer; "printed" is the page number in the document footer.

- **FAIL** - a value in the test data breaks an ATL105 rule. It must be fixed.
- **BLOCKED** - the field holds instruction text instead of a value. It must be filled in from the step 1 response before the message can be sent.
- **REVIEW** - the data values are acceptable, but something in the test case must be confirmed or corrected.
- **INFO** - a note for the team that builds the wire message.
- **PASS** - matches ATL105.

## What must be fixed

Nothing - no value in the test data breaks an ATL105 rule.

## Values to fill in at run time

### 1. Approval Number holds instruction text, not a value - BLOCKED

- **Step:** 2
- **Where:** TD-TC-002128.step2.completion.json -> Standard Message Data Segment -> ApprovalNumber
- **Value:** `<ApprovalNumber from step 1 response>`
- **What ATL105 says:**
  - Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization."
  - Appendix G. Valid Transaction Type Codes, PDF p.545 (printed Appendix G-1): "0 POS Purchase/Capture Point-of-sale purchase/preauthorized completion"
- **Why:** The field holds the instruction text '<ApprovalNumber from step 1 response>' instead of a real Approval Number. BUYPASS cannot process this text, so the message cannot be sent until the real value from the step 1 response is filled in.
- **What it should be / action:** Fill in at run time: Element No. 5 (Approval Number)-present in the original transaction-must be present and identical (runtime link to the original's response, never invented)..

### 2. Approval Number holds instruction text, not a value - BLOCKED

- **Step:** 4
- **Where:** TD-TC-002128.step4.completion-resubmitted.json -> Standard Message Data Segment -> ApprovalNumber
- **Value:** `<ApprovalNumber from step 1 response>`
- **What ATL105 says:**
  - (no specific ATL105 page)
- **Why:** The field holds the instruction text '<ApprovalNumber from step 1 response>' instead of a real Approval Number. BUYPASS cannot process this text, so the message cannot be sent until the real value from the step 1 response is filled in.
- **What it should be / action:** Fill in at run time: Element No. 5 (Approval Number)-present in the original transaction-must be present and identical (runtime link to the original's response, never invented)..

## Items to confirm

### 3. Sequence Number of the resubmitted completion: ATL105 rules conflict - REVIEW

- **Step:** 4
- **Where:** TD-TC-002128.step4.completion-resubmitted.json -> Standard Message Data Segment -> SequenceNumber
- **Value:** `021282`
- **What ATL105 says:**
  - Element 86 Sequence Number (Chapter 13), PDF p.409 (printed 13-69): "In the case of a preauthorized transaction, the Sequence Number in the Purchase/Capture is the same Sequence Number as in the transaction’s Authorization Only request."
  - Section 2.3.4 Summary of TOR Processing Rules, PDF p.47 (printed 2-12): "When attempting to submit a TOR for a completion transaction, the device must finalize the TOR processing prior to resubmitting the completion. If the completion is resent before the TOR processing has been completed, the device risks voiding the resubmitted completion."
  - Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "The Sequence Number, Account Number, and dollar amount—assigned by the customer's host/controller/POS system—are the elements used to match a request with its respective response."
  - Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "Each time any financial transaction request is forwarded to BUYPASS, the financial transaction should be assigned a new Sequence Number"
- **Why:** The test case gives the resubmitted completion a new Sequence Number ('021282'), citing the general rule that every forwarded financial request gets a new Sequence Number. Element 86 says the Purchase/Capture of a preauthorized transaction carries the Authorization Only's Sequence Number ('021281'), and Section 2.3.4 warns that a completion resent before its TOR is finished risks being voided by that TOR - which only happens if both carry the same matching data (Sequence Number, Account Number, amount). ATL105 does not say which rule wins for a resubmitted completion.
- **What it should be / action:** Confirm with BUYPASS. If Element 86 applies, use '021281' (the Authorization Only's Sequence Number).

### 4. Outdated page cited for the POS Entry Mode table (Table 005) - REVIEW

- **Where:** test case request -> Variable Information Data Segment -> Variable Information (source_page), every step
- **Value:** `p.548`
- **What ATL105 says:**
  - Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005), PDF p.555 (printed Appendix I-6): "Point-of-Service Entry Mode Information"
- **Why:** The test case cites p.548 for the POS Entry Mode table (Table 005). In ATL105 2026-4, PDF p.548 (printed Appendix H-2) does not contain it; it is on PDF p.555 (printed Appendix I-6). p.548 is where it was in the older 2026-3 edition.
- **What it should be / action:** Change the cited page from 548 to 555. The data values are not affected.

### 5. The Approval Number rule cites the wrong section - REVIEW

- **Step:** 2
- **Where:** test case step 2 -> link rule for Approval Number
- **Value:** `p.85`
- **What ATL105 says:**
  - Section 10.1.2.2 Authorization Only Reversal Transaction, PDF p.85 (printed 10-6): "Element No. 5 (Approval Number)—present in the original transaction—must be present and identical in the Authorization Only Reversal transaction."
  - Appendix G. Valid Transaction Type Codes, PDF p.545 (printed Appendix G-1): "0 POS Purchase/Capture Point-of-sale purchase/preauthorized completion"
  - Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization."
- **Why:** p.85 is Section 10.1.2.2 Authorization Only Reversal Transaction: it covers the Authorization Only Reversal (Transaction Type S), while this step is a preauthorized completion (Prompt Code 0020, Transaction Type 0).
- **What it should be / action:** Cite Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28) instead. The data value itself is correct.

### 6. The Approval Number rule cites the wrong section - REVIEW

- **Step:** 4
- **Where:** test case step 4 -> link rule for Approval Number
- **Value:** `p.85`
- **What ATL105 says:**
  - Section 10.1.2.2 Authorization Only Reversal Transaction, PDF p.85 (printed 10-6): "Element No. 5 (Approval Number)—present in the original transaction—must be present and identical in the Authorization Only Reversal transaction."
  - Appendix G. Valid Transaction Type Codes, PDF p.545 (printed Appendix G-1): "0 POS Purchase/Capture Point-of-sale purchase/preauthorized completion"
  - Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization."
- **Why:** p.85 is Section 10.1.2.2 Authorization Only Reversal Transaction: it covers the Authorization Only Reversal (Transaction Type S), while this step is a preauthorized completion (Prompt Code 0020, Transaction Type 0).
- **What it should be / action:** Cite Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28) instead. The data value itself is correct.

### 7. The quote cited for p.511 is not word-for-word in ATL105 - REVIEW

- **Where:** test case spec_basis
- **Value:** `03 MasterCard - Authorizer Type: Credit`
- **What ATL105 says:**
  - Appendix C. Valid Authorizer Codes, PDF p.511 (printed Appendix C-1): "03 MasterCard® Credit"
- **Why:** The test case quotes "03 MasterCard - Authorizer Type: Credit" for p.511. Not word-for-word: all its words are on the cited page, but not in this form.
- **What it should be / action:** Quote the text exactly as it appears: "03 MasterCard® Credit".

### 8. The Terminal Identifier is a placeholder - REVIEW

- **Where:** Standard Message Data Segment -> TerminalIdentifier (every step)
- **Value:** `JX72948216050257001`
- **What ATL105 says:**
  - Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."
  - Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code)."
  - Appendix D. Valid State Codes, PDF p.517 (printed Appendix D-4): "Yukon YT 72"
- **Why:** The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada.
- **What it should be / action:** Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location.

## Notes for the message builder

### 9. Join Account Number and expiry with '=' in the wire message - INFO

- **Step:** 2
- **Where:** TD-TC-002128.step2.completion.json -> AccountNumber, CardDiscretionaryBlockData
- **Value:** `5137775372098485 / 2908`
- **What ATL105 says:**
  - Section 10.1.8.2 Track Support Requirements, PDF p.94 (printed 10-15): "The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM)."
- **Why:** The JSON keeps Account Number and the expiry as separate fields. In the real message for this follow-on transaction they are sent as Account Number, '=', YYMM - not separated by a Field Separator. The Segment Length is the same either way.
- **What it should be / action:** The message builder sends 5137775372098485=2908.

### 10. Join Account Number and expiry with '=' in the wire message - INFO

- **Step:** 3
- **Where:** TD-TC-002128.step3.timeout-reversal.json -> AccountNumber, CardDiscretionaryBlockData
- **Value:** `5137775372098485 / 2908`
- **What ATL105 says:**
  - Section 10.1.8.2 Track Support Requirements, PDF p.94 (printed 10-15): "The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM)."
- **Why:** The JSON keeps Account Number and the expiry as separate fields. In the real message for this follow-on transaction they are sent as Account Number, '=', YYMM - not separated by a Field Separator. The Segment Length is the same either way.
- **What it should be / action:** The message builder sends 5137775372098485=2908.

### 11. Join Account Number and expiry with '=' in the wire message - INFO

- **Step:** 4
- **Where:** TD-TC-002128.step4.completion-resubmitted.json -> AccountNumber, CardDiscretionaryBlockData
- **Value:** `5137775372098485 / 2908`
- **What ATL105 says:**
  - Section 10.1.8.2 Track Support Requirements, PDF p.94 (printed 10-15): "The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM)."
- **Why:** The JSON keeps Account Number and the expiry as separate fields. In the real message for this follow-on transaction they are sent as Account Number, '=', YYMM - not separated by a Field Separator. The Segment Length is the same either way.
- **What it should be / action:** The message builder sends 5137775372098485=2908.

## Field-by-field check - step 1, auth only (`TD-TC-002128.step1.auth-only.json`)

| Segment | Field No. | Element | Field | Test data value | Test case value | ATL105 rule | Where in ATL105 | Result |
|---|---|---|---|---|---|---|---|---|
| Message | - | - | Message type | `Financial Transaction Request` | `-` | Financial Transaction Request (no chip data) | Section 11.1, PDF p.167 (printed 11-4) | PASS |
| Data Section 1 | 1 | 55 | Message Format Version Identifier | `ATL105` | `ATL105` | Fixed 6 characters: ATL105 | Section 11.1, PDF p.167 (printed 11-4); Element 55, PDF p.391 (printed 13-51) | PASS |
| Data Section 1 | 2 | 63 | Number of Segments | `02` | `-` | Up to 2 digits; equals the number of segments sent (2) | Section 11.1, PDF p.167 (printed 11-4); Element 63, PDF p.395 (printed 13-55) | PASS |
| 100 | 1 | 85 | Segment Type | `100` | `-` | Fixed value 100 | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 2 | 84 | Segment Length | `089` | `-` | Length of all 17 fields + 17 Field Separators = 089 | Section 12.1, PDF p.223 (printed 12-6); Element 84, PDF p.406 (printed 13-66) | PASS |
| 100 | 3 | 44 | Information Byte | `0` | `0` | 1 character: '0' = single-message transmission | Element 44, PDF p.386 (printed 13-46) | PASS |
| 100 | 4 | 102 | Terminal Identifier | `JX72948216050257001` | `JX72948216050257001` | Device Type 'JX' + State Code '72' = Yukon (YT), Canada + Merchant Number and Device Number '948216050257001' | Element 102, PDF p.417 (printed 13-77); Appendix D, PDF p.517 (printed D-4) | PASS |
| 100 | 5 | 78 | Prompt Code | `3020` | `3020` | Transaction Type '3' = POS Authorization Only; Card Type '020' = Credit | Element 78, PDF p.400 (printed 13-60); Appendix G, PDF p.545 (printed G-1); Appendix E, PDF p.521 (printed E-4) | PASS |
| 100 | 6 | 2 | Account Number | `5137775372098485` | `5137775372098485` | Up to 24 characters; valid check digit and MasterCard range 51-55 / 2221-2720 (card-industry rule) | Element 2, PDF p.364 (printed 13-24) | PASS |
| 100 | 7 | 12 | Card Discretionary Block Data | `290820111451702` | `290820111451702` | Track 2 data: expiry 2908 = August 2029; service code 201 (chip card, ISO/IEC 7813); Account Number + '=' + this field = 32 characters (at most 37) | Element 12, PDF p.371 (printed 13-31) | PASS |
| 100 | 8 | 33 | Encrypted PIN Block Data | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 9 | 79 | Pump/Lane Number | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 10 | 41 | Fuel Purchase Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 11 | 58 | Nonfuel Amount | `1500` | `1500` | Digits only, at most 7 for MasterCard (8 only for Amex): '1500' = $15.00 | Element 58, PDF p.393 (printed 13-53) | PASS |
| 100 | 12 | 99 | Tax Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 13 | 17 | Cash Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 14 | 86 | Sequence Number | `021281` | `021281` | 6 digits, 000001-999999 | Element 86, PDF p.409 (printed 13-69) | PASS |
| 100 | 15 | 5 | Approval Number | `(empty)` | `(not sent)` | Not sent on a new transaction | Element 5, PDF p.368 (printed 13-28) | PASS |
| 100 | 16 | 49 | Local Date and Local Time | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 17 | 121 | Partial Approval Indicator | `1` | `1` | '1' = partial approval supported | Element 121, PDF p.440 (printed 13-100) | PASS |
| 111 | 1 | 85 | Segment Type | `111` | `111` | Fixed value 111 | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 2 | 84 | Segment Length | `026` | `-` | 3 (type) + 3 (length) + 17 (tables) + 3 Field Separators = 026 | Section 12.10, PDF p.246 (printed 12-29); Element 84, PDF p.406 (printed 13-66) | PASS |
| 111 | 3 [1] | 111 | Variable Information Indicator | `005` | `-` | 3-character table ID (Table 005) | Element 111, PDF p.422 (printed 13-82) | PASS |
| 111 | 4 [1] | 112 | Variable Information Length | `003` | `-` | 3 digits = length of the table data (003) | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 5 [1] | 113 | Variable Information (Table 005) | `821` | `821` | '82' = contactless mobile commerce device; terminal capability '1' = PIN entry capability | Appendix I, PDF p.555 (printed I-6); Appendix J, PDF p.638 (printed J-1); Appendix J, PDF p.639 (printed J-2) | PASS |
| 111 | 3 [2] | 111 | Variable Information Indicator | `031` | `-` | 3-character table ID (Table 031) | Element 111, PDF p.422 (printed 13-82) | PASS |
| 111 | 4 [2] | 112 | Variable Information Length | `002` | `-` | 3 digits = length of the table data (002) | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 5 [2] | 113 | Variable Information (Table 031) | `12` | `12` | '12' = hybrid reader: contact chip and contactless chip; required on every transaction | Appendix I, PDF p.569 (printed I-20) | PASS |

## Field-by-field check - step 2, completion (`TD-TC-002128.step2.completion.json`)

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
| 100 | 6 | 2 | Account Number | `5137775372098485` | `5137775372098485` | Up to 24 characters; valid check digit and MasterCard range 51-55 / 2221-2720 (card-industry rule) | Element 2, PDF p.364 (printed 13-24) | PASS |
| 100 | 7 | 12 | Card Discretionary Block Data | `2908` | `2908` | Follow-on transaction: expiry as YYMM only (original track data 290820111451702 -> 2908) | Section 10.1.8.2, PDF p.94 (printed 10-15); Section 10.1.1.2, PDF p.81 (printed 10-2) | PASS |
| 100 | 8 | 33 | Encrypted PIN Block Data | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 9 | 79 | Pump/Lane Number | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 10 | 41 | Fuel Purchase Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 11 | 58 | Nonfuel Amount | `1500` | `1500` | Digits only, at most 7 for MasterCard (8 only for Amex): '1500' = $15.00 | Element 58, PDF p.393 (printed 13-53) | PASS |
| 100 | 12 | 99 | Tax Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 13 | 17 | Cash Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 14 | 86 | Sequence Number | `021281` | `021281` | 6 digits, 000001-999999 | Element 86, PDF p.409 (printed 13-69) | PASS |
| 100 | 15 | 5 | Approval Number | `<ApprovalNumber from step 1 response>` | `R2VEQ5` | Filled in at run time: Element No. 5 (Approval Number)-present in the original transaction-must be present and identical (runtime link to the original's response, never invented). | Element 5, PDF p.368 (printed 13-28); Appendix G, PDF p.545 (printed G-1) | BLOCKED |
| 100 | 16 | 49 | Local Date and Local Time | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 17 | 121 | Partial Approval Indicator | `1` | `1` | '1' = partial approval supported | Element 121, PDF p.440 (printed 13-100) | PASS |
| 111 | 1 | 85 | Segment Type | `111` | `111` | Fixed value 111 | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 2 | 84 | Segment Length | `026` | `-` | 3 (type) + 3 (length) + 17 (tables) + 3 Field Separators = 026 | Section 12.10, PDF p.246 (printed 12-29); Element 84, PDF p.406 (printed 13-66) | PASS |
| 111 | 3 [1] | 111 | Variable Information Indicator | `005` | `-` | 3-character table ID (Table 005) | Element 111, PDF p.422 (printed 13-82) | PASS |
| 111 | 4 [1] | 112 | Variable Information Length | `003` | `-` | 3 digits = length of the table data (003) | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 5 [1] | 113 | Variable Information (Table 005) | `821` | `821` | '82' = contactless mobile commerce device; terminal capability '1' = PIN entry capability | Appendix I, PDF p.555 (printed I-6); Appendix J, PDF p.638 (printed J-1); Appendix J, PDF p.639 (printed J-2) | PASS |
| 111 | 3 [2] | 111 | Variable Information Indicator | `031` | `-` | 3-character table ID (Table 031) | Element 111, PDF p.422 (printed 13-82) | PASS |
| 111 | 4 [2] | 112 | Variable Information Length | `002` | `-` | 3 digits = length of the table data (002) | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 5 [2] | 113 | Variable Information (Table 031) | `12` | `12` | '12' = hybrid reader: contact chip and contactless chip; required on every transaction | Appendix I, PDF p.569 (printed I-20) | PASS |

## Field-by-field check - step 3, timeout reversal (`TD-TC-002128.step3.timeout-reversal.json`)

| Segment | Field No. | Element | Field | Test data value | Test case value | ATL105 rule | Where in ATL105 | Result |
|---|---|---|---|---|---|---|---|---|
| Message | - | - | Message type | `Financial Transaction Request` | `-` | Financial Transaction Request (no chip data) | Section 11.1, PDF p.167 (printed 11-4) | PASS |
| Data Section 1 | 1 | 55 | Message Format Version Identifier | `ATL105` | `ATL105` | Fixed 6 characters: ATL105 | Section 11.1, PDF p.167 (printed 11-4); Element 55, PDF p.391 (printed 13-51) | PASS |
| Data Section 1 | 2 | 63 | Number of Segments | `02` | `-` | Up to 2 digits; equals the number of segments sent (2) | Section 11.1, PDF p.167 (printed 11-4); Element 63, PDF p.395 (printed 13-55) | PASS |
| 100 | 1 | 85 | Segment Type | `100` | `-` | Fixed value 100 | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 2 | 84 | Segment Length | `078` | `-` | Length of all 17 fields + 17 Field Separators = 078 | Section 12.1, PDF p.223 (printed 12-6); Element 84, PDF p.406 (printed 13-66) | PASS |
| 100 | 3 | 44 | Information Byte | `0` | `0` | 1 character: '0' = single-message transmission | Element 44, PDF p.386 (printed 13-46) | PASS |
| 100 | 4 | 102 | Terminal Identifier | `JX72948216050257001` | `JX72948216050257001` | Device Type 'JX' + State Code '72' = Yukon (YT), Canada + Merchant Number and Device Number '948216050257001' | Element 102, PDF p.417 (printed 13-77); Appendix D, PDF p.517 (printed D-4) | PASS |
| 100 | 5 | 78 | Prompt Code | `Z020` | `Z020` | Transaction Type 'Z' = Time-out Reversal; Card Type '020' = Credit | Element 78, PDF p.400 (printed 13-60); Appendix G, PDF p.546 (printed G-2); Appendix E, PDF p.521 (printed E-4) | PASS |
| 100 | 6 | 2 | Account Number | `5137775372098485` | `5137775372098485` | Up to 24 characters; valid check digit and MasterCard range 51-55 / 2221-2720 (card-industry rule) | Element 2, PDF p.364 (printed 13-24) | PASS |
| 100 | 7 | 12 | Card Discretionary Block Data | `2908` | `2908` | Follow-on transaction: expiry as YYMM only (original YYMM 2908 -> 2908) | Section 10.1.8.2, PDF p.94 (printed 10-15); Section 10.1.1.2, PDF p.81 (printed 10-2) | PASS |
| 100 | 8 | 33 | Encrypted PIN Block Data | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 9 | 79 | Pump/Lane Number | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 10 | 41 | Fuel Purchase Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 11 | 58 | Nonfuel Amount | `1500` | `1500` | Digits only, at most 7 for MasterCard (8 only for Amex): '1500' = $15.00 | Element 58, PDF p.393 (printed 13-53) | PASS |
| 100 | 12 | 99 | Tax Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 13 | 17 | Cash Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 14 | 86 | Sequence Number | `021281` | `021281` | 6 digits, 000001-999999 | Element 86, PDF p.409 (printed 13-69) | PASS |
| 100 | 15 | 5 | Approval Number | `(empty)` | `(not sent)` | Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | Section 2.3, PDF p.39 (printed 2-4); Section 2.3.1, PDF p.40 (printed 2-5) | PASS |
| 100 | 16 | 49 | Local Date and Local Time | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 17 | 121 | Partial Approval Indicator | `1` | `1` | '1' = partial approval supported | Element 121, PDF p.440 (printed 13-100) | PASS |
| 111 | 1 | 85 | Segment Type | `111` | `111` | Fixed value 111 | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 2 | 84 | Segment Length | `026` | `-` | 3 (type) + 3 (length) + 17 (tables) + 3 Field Separators = 026 | Section 12.10, PDF p.246 (printed 12-29); Element 84, PDF p.406 (printed 13-66) | PASS |
| 111 | 3 [1] | 111 | Variable Information Indicator | `005` | `-` | 3-character table ID (Table 005) | Element 111, PDF p.422 (printed 13-82) | PASS |
| 111 | 4 [1] | 112 | Variable Information Length | `003` | `-` | 3 digits = length of the table data (003) | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 5 [1] | 113 | Variable Information (Table 005) | `821` | `821` | '82' = contactless mobile commerce device; terminal capability '1' = PIN entry capability | Appendix I, PDF p.555 (printed I-6); Appendix J, PDF p.638 (printed J-1); Appendix J, PDF p.639 (printed J-2) | PASS |
| 111 | 3 [2] | 111 | Variable Information Indicator | `031` | `-` | 3-character table ID (Table 031) | Element 111, PDF p.422 (printed 13-82) | PASS |
| 111 | 4 [2] | 112 | Variable Information Length | `002` | `-` | 3 digits = length of the table data (002) | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 5 [2] | 113 | Variable Information (Table 031) | `12` | `12` | '12' = hybrid reader: contact chip and contactless chip; required on every transaction | Appendix I, PDF p.569 (printed I-20) | PASS |

## Field-by-field check - step 4, completion resubmitted (`TD-TC-002128.step4.completion-resubmitted.json`)

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
| 100 | 6 | 2 | Account Number | `5137775372098485` | `5137775372098485` | Up to 24 characters; valid check digit and MasterCard range 51-55 / 2221-2720 (card-industry rule) | Element 2, PDF p.364 (printed 13-24) | PASS |
| 100 | 7 | 12 | Card Discretionary Block Data | `2908` | `2908` | Follow-on transaction: expiry as YYMM only (original track data 290820111451702 -> 2908) | Section 10.1.8.2, PDF p.94 (printed 10-15); Section 10.1.1.2, PDF p.81 (printed 10-2) | PASS |
| 100 | 8 | 33 | Encrypted PIN Block Data | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 9 | 79 | Pump/Lane Number | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 10 | 41 | Fuel Purchase Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 11 | 58 | Nonfuel Amount | `1500` | `1500` | Digits only, at most 7 for MasterCard (8 only for Amex): '1500' = $15.00 | Element 58, PDF p.393 (printed 13-53) | PASS |
| 100 | 12 | 99 | Tax Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 13 | 17 | Cash Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 14 | 86 | Sequence Number | `021282` | `021282` | 6 digits, 000001-999999 | Element 86, PDF p.409 (printed 13-69) | PASS |
| 100 | 15 | 5 | Approval Number | `<ApprovalNumber from step 1 response>` | `BRUB16` | Filled in at run time: Element No. 5 (Approval Number)-present in the original transaction-must be present and identical (runtime link to the original's response, never invented). | - | BLOCKED |
| 100 | 16 | 49 | Local Date and Local Time | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 17 | 121 | Partial Approval Indicator | `1` | `1` | '1' = partial approval supported | Element 121, PDF p.440 (printed 13-100) | PASS |
| 111 | 1 | 85 | Segment Type | `111` | `111` | Fixed value 111 | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 2 | 84 | Segment Length | `026` | `-` | 3 (type) + 3 (length) + 17 (tables) + 3 Field Separators = 026 | Section 12.10, PDF p.246 (printed 12-29); Element 84, PDF p.406 (printed 13-66) | PASS |
| 111 | 3 [1] | 111 | Variable Information Indicator | `005` | `-` | 3-character table ID (Table 005) | Element 111, PDF p.422 (printed 13-82) | PASS |
| 111 | 4 [1] | 112 | Variable Information Length | `003` | `-` | 3 digits = length of the table data (003) | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 5 [1] | 113 | Variable Information (Table 005) | `821` | `821` | '82' = contactless mobile commerce device; terminal capability '1' = PIN entry capability | Appendix I, PDF p.555 (printed I-6); Appendix J, PDF p.638 (printed J-1); Appendix J, PDF p.639 (printed J-2) | PASS |
| 111 | 3 [2] | 111 | Variable Information Indicator | `031` | `-` | 3-character table ID (Table 031) | Element 111, PDF p.422 (printed 13-82) | PASS |
| 111 | 4 [2] | 112 | Variable Information Length | `002` | `-` | 3 digits = length of the table data (002) | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 5 [2] | 113 | Variable Information (Table 031) | `12` | `12` | '12' = hybrid reader: contact chip and contactless chip; required on every transaction | Appendix I, PDF p.569 (printed I-20) | PASS |

## Step-to-step checks

| Step | Check | This step | Original step | Where in ATL105 | Result |
|---|---|---|---|---|---|
| 2 | Same Sequence Number as the original | `021281` | `021281` | Element 86, PDF p.409 (printed 13-69) | PASS |
| 2 | Same Account Number as the original | `5137775372098485` | `5137775372098485` | Section 2.3.1, PDF p.40 (printed 2-5) | PASS |
| 2 | Same terminal as the original | `JX72948216050257001` | `JX72948216050257001` | Section 2.3.1, PDF p.40 (printed 2-5) | PASS |
| 2 | Same POS entry mode as the original | `82` | `82` | Appendix J, PDF p.638 (printed J-1) | PASS |
| 3 | Same Sequence Number as the original | `021281` | `021281` | Section 2.3.1, PDF p.40 (printed 2-5); Element 86, PDF p.409 (printed 13-69) | PASS |
| 3 | Same Account Number as the original | `5137775372098485` | `5137775372098485` | Section 2.3.1, PDF p.40 (printed 2-5) | PASS |
| 3 | Same terminal as the original | `JX72948216050257001` | `JX72948216050257001` | Section 2.3.1, PDF p.40 (printed 2-5) | PASS |
| 3 | Same POS entry mode as the original | `82` | `82` | Appendix J, PDF p.638 (printed J-1) | PASS |
| 4 | Same Sequence Number as the original | `021282` | `021281` | Element 86, PDF p.409 (printed 13-69); Section 2.3.4, PDF p.47 (printed 2-12); Section 2.3.1, PDF p.40 (printed 2-5) | REVIEW |
| 4 | Same Account Number as the original | `5137775372098485` | `5137775372098485` | Section 2.3.1, PDF p.40 (printed 2-5) | PASS |
| 4 | Same terminal as the original | `JX72948216050257001` | `JX72948216050257001` | Section 2.3.1, PDF p.40 (printed 2-5) | PASS |
| 4 | Same POS entry mode as the original | `82` | `82` | Appendix J, PDF p.638 (printed J-1) | PASS |

## Expected response codes

| Step | Expected Response Code | Meaning | Where in ATL105 | Result |
|---|---|---|---|---|
| 1 | `2` | Approved - Authorization only | Element 83, PDF p.404 (printed 13-64) | PASS |
| 3 | `0` | Approved - Purchase/Capture | Element 83, PDF p.404 (printed 13-64) | PASS |
| 4 | `0` | Approved - Purchase/Capture | Element 83, PDF p.404 (printed 13-64) | PASS |

## Spec text quoted by the test case

| Cited page | Quote in the test case | Check | Result |
|---|---|---|---|
| PDF p.47 (printed 2-12) | When attempting to submit a TOR for a completion transaction, the device must finalize the TOR processing prior to resubmitting | Found word-for-word on the cited page | PASS |
| PDF p.47 (printed 2-12) | TOR processing has been completed, the device risks voiding the resubmitted completion. | Found word-for-word on the cited page | PASS |
| PDF p.511 (printed Appendix C-1) | 03 MasterCard - Authorizer Type: Credit | Not word-for-word: all its words are on the cited page, but not in this form | REVIEW |
| PDF p.83 (printed 10-4) | The following point-of-sale transactions are supported for credit card processing: ... Authorization Only | Found word-for-word on the cited page | PASS |

