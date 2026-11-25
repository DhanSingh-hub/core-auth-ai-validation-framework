# TC-003163 (BR1_TS21_TC2) - ATL105 Test Data Validation

**BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Merchandise Return – Contactless Mobile Commerce Device Merchandise Return → Void of a Merchandise Return**

| Item | Value |
|---|---|
| Result | **FAIL** |
| Can it run as-is? | No - fix the FAIL items first |
| Test data | `interlink_debit/test_data/TD-TC-003163.step1.return.json`, `interlink_debit/test_data/TD-TC-003163.step2.void-return.json` |
| Template / status | `T5-RET-VOID` / `pending_sme` |
| Specification | BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026) |

All page references are to the BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026). "PDF p." is the page number shown in the PDF viewer; "printed" is the page number in the document footer.

- **FAIL** - a value in the test data breaks an ATL105 rule. It must be fixed.
- **BLOCKED** - the field holds instruction text instead of a value. It must be filled in from the step 1 response before the message can be sent.
- **REVIEW** - the data values are acceptable, but something in the test case must be confirmed or corrected.
- **INFO** - a note for the team that builds the wire message.
- **PASS** - matches ATL105.

## What must be fixed

### 1. The card data is empty (Account Number and Card Discretionary Block Data) - FAIL

- **Step:** 1
- **Where:** TD-TC-003163.step1.return.json -> Standard Message Data Segment -> AccountNumber, CardDiscretionaryBlockData
- **Value:** `(empty)`
- **What ATL105 says:**
  - Element 2 Account Number (Chapter 13), PDF p.364 (printed 13-24): "a variable length (up to 24 alphanumeric bytes) Account Number"
  - Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "If the Account Number is obtained from a Track 2 card reader, instead of being manually keyed, the Account Number and Card Discretionary Block Data should consist of all data read from the card between, but not including, the Start Sentinel and End Sentinel."
  - Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number is considered the data before the = character, and Card Discretionary Block Data is considered the data after the = character."
- **Why:** This return must carry the data read from the card (entry mode 82 = contactless mobile commerce device), but Account Number and Card Discretionary Block Data are empty, so BUYPASS cannot identify the card. The test case marks the Account Number AWAITING_CLIENT_VALUE: the Interlink Debit card profile was never supplied, and the generator wrote an empty field instead of a value or an explicit placeholder. The Card Discretionary Block Data is marked as an omitted conditional field, although it is needed here.
- **What it should be / action:** Fill in the data read from the card (entry mode 82 = contactless mobile commerce device) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field.

### 2. The debit PIN block (Element 33) is empty - FAIL

- **Step:** 1
- **Where:** TD-TC-003163.step1.return.json -> Standard Message Data Segment -> EncryptedPINBlockData
- **Value:** `(empty)`
- **What ATL105 says:**
  - Section 10.2.1.3 PIN Entry, PDF p.96 (printed 10-17): "All transactions—except the Purchase Reversal transaction—require PIN entry."
  - Section 10.2.1.3 PIN Entry, PDF p.96 (printed 10-17): "When processing a debit card transaction, the entry of the customer PIN is required on a DES-compliant entry device, and it must be encrypted using the DUKPT PIN encryption method."
  - Element 33 Encrypted PIN Block Data (Chapter 13), PDF p.382 (printed 13-42): "Required for debit and EBT."
  - Element 33 Encrypted PIN Block Data (Chapter 13), PDF p.382 (printed 13-42): "For DUKPT PIN encryption, this is a 16- to 20-byte Key Serial Number (KSN) plus a 16-byte PIN block."
- **Why:** ATL105 requires PIN entry, DUKPT-encrypted, on every debit transaction except the Purchase Reversal, and Element 33 is required for debit. This return (Transaction Type 7) sends no PIN block.
- **What it should be / action:** Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input).

### 3. The card data is empty (Account Number and Card Discretionary Block Data) - FAIL

- **Step:** 2
- **Where:** TD-TC-003163.step2.void-return.json -> Standard Message Data Segment -> AccountNumber, CardDiscretionaryBlockData
- **Value:** `(empty)`
- **What ATL105 says:**
  - Element 2 Account Number (Chapter 13), PDF p.364 (printed 13-24): "a variable length (up to 24 alphanumeric bytes) Account Number"
  - Section 10.2.5.2 Track Support Requirements, PDF p.105 (printed 10-26): "The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM)."
- **Why:** This void return must carry the Account Number and the expiry as YYMM (follow-on transaction), but Account Number and Card Discretionary Block Data are empty, so BUYPASS cannot identify the card. The test case marks the Account Number AWAITING_CLIENT_VALUE: the Interlink Debit card profile was never supplied, and the generator wrote an empty field instead of a value or an explicit placeholder. The Card Discretionary Block Data is marked as an omitted conditional field, although it is needed here.
- **What it should be / action:** Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field.

## Values to fill in at run time

### 4. Approval Number holds instruction text, not a value - BLOCKED

- **Step:** 2
- **Where:** TD-TC-003163.step2.void-return.json -> Standard Message Data Segment -> ApprovalNumber
- **Value:** `<ApprovalNumber from step 1 response>`
- **What ATL105 says:**
  - Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "Required on reversals for matching information to the original purchase."
  - Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "Fixed length of six alphanumeric characters; space-filled"
- **Why:** The field holds the instruction text '<ApprovalNumber from step 1 response>' instead of a real Approval Number. BUYPASS cannot process this text, so the message cannot be sent until the real value from the step 1 response is filled in.
- **What it should be / action:** Fill in at run time: Required on reversals for matching information to the original
purchase. If it is included in a Purchase Request, then the
Purchase Request is considered preauthorized, and BUYPASS
does not go out for authorization. In the case of an Authorization Only Reversal, this element-
present in the original transaction-must be present and identical
in the Authorization Only Reversal transaction. (Note:.

## Items to confirm

### 5. The test case does not state the expected Response Code - REVIEW

- **Where:** test case step 1 expected_response
- **Value:** `? (FLAG)`
- **What ATL105 says:**
  - Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "0 Approved—Purchase/Capture"
  - Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "2 Approved—Authorization only"
- **Why:** The expected response is left open ('?'), so a run cannot pass or fail on the Response Code. Element 83 has a separate approval code for each kind of transaction.
- **What it should be / action:** State the expected Response Code for this transaction type (from BUYPASS or a reference run).

### 6. Outdated page cited for the POS Entry Mode table (Table 005) - REVIEW

- **Where:** test case request -> Variable Information Data Segment -> Variable Information (source_page), every step
- **Value:** `p.548`
- **What ATL105 says:**
  - Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005), PDF p.555 (printed Appendix I-6): "Point-of-Service Entry Mode Information"
- **Why:** The test case cites p.548 for the POS Entry Mode table (Table 005). In ATL105 2026-4, PDF p.548 (printed Appendix H-2) does not contain it; it is on PDF p.555 (printed Appendix I-6). p.548 is where it was in the older 2026-3 edition.
- **What it should be / action:** Change the cited page from 548 to 555. The data values are not affected.

### 7. The quote cited for p.512 is not word-for-word in ATL105 - REVIEW

- **Where:** test case spec_basis
- **Value:** `48 Interlink - Authorizer Type: Debit`
- **What ATL105 says:**
  - (no specific ATL105 page)
- **Why:** The test case quotes "48 Interlink - Authorizer Type: Debit" for p.512. Not word-for-word: all its words are on the cited page, but not in this form.
- **What it should be / action:** Quote the text exactly as it appears on the page.

### 8. The Terminal Identifier is a placeholder - REVIEW

- **Where:** Standard Message Data Segment -> TerminalIdentifier (every step)
- **Value:** `JX72948216050257001`
- **What ATL105 says:**
  - Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."
  - Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code)."
  - Appendix D. Valid State Codes, PDF p.517 (printed Appendix D-4): "Yukon YT 72"
- **Why:** The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada.
- **What it should be / action:** Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location.

## Field-by-field check - step 1, return (`TD-TC-003163.step1.return.json`)

| Segment | Field No. | Element | Field | Test data value | Test case value | ATL105 rule | Where in ATL105 | Result |
|---|---|---|---|---|---|---|---|---|
| Message | - | - | Message type | `Financial Transaction Request` | `-` | Financial Transaction Request (no chip data) | Section 11.1, PDF p.167 (printed 11-4) | PASS |
| Data Section 1 | 1 | 55 | Message Format Version Identifier | `ATL105` | `ATL105` | Fixed 6 characters: ATL105 | Section 11.1, PDF p.167 (printed 11-4); Element 55, PDF p.391 (printed 13-51) | PASS |
| Data Section 1 | 2 | 63 | Number of Segments | `02` | `-` | Up to 2 digits; equals the number of segments sent (2) | Section 11.1, PDF p.167 (printed 11-4); Element 63, PDF p.395 (printed 13-55) | PASS |
| 100 | 1 | 85 | Segment Type | `100` | `-` | Fixed value 100 | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 2 | 84 | Segment Length | `058` | `-` | Length of all 17 fields + 17 Field Separators = 058 | Section 12.1, PDF p.223 (printed 12-6); Element 84, PDF p.406 (printed 13-66) | PASS |
| 100 | 3 | 44 | Information Byte | `0` | `0` | 1 character: '0' = single-message transmission | Element 44, PDF p.386 (printed 13-46) | PASS |
| 100 | 4 | 102 | Terminal Identifier | `JX72948216050257001` | `JX72948216050257001` | Device Type 'JX' + State Code '72' = Yukon (YT), Canada + Merchant Number and Device Number '948216050257001' | Element 102, PDF p.417 (printed 13-77); Appendix D, PDF p.517 (printed D-4) | PASS |
| 100 | 5 | 78 | Prompt Code | `7011` | `7011` | Transaction Type '7' = Merchandise return; Card Type '011' = Debit checking | Element 78, PDF p.400 (printed 13-60); Appendix G, PDF p.546 (printed G-2); Appendix E, PDF p.521 (printed E-4) | PASS |
| 100 | 6 | 2 | Account Number | `(empty)` | `(not sent)` | Required: the data read from the card (entry mode 82 = contactless mobile commerce device) | Element 2, PDF p.364 (printed 13-24); Element 12, PDF p.371 (printed 13-31) | FAIL |
| 100 | 7 | 12 | Card Discretionary Block Data | `(empty)` | `(not sent)` | Required with the Account Number (see the card data finding) | Element 12, PDF p.371 (printed 13-31) | FAIL |
| 100 | 8 | 33 | Encrypted PIN Block Data | `(empty)` | `(not sent)` | Required for debit: DUKPT Key Serial Number + PIN block | Section 10.2.1.3, PDF p.96 (printed 10-17); Element 33, PDF p.382 (printed 13-42) | FAIL |
| 100 | 9 | 79 | Pump/Lane Number | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 10 | 41 | Fuel Purchase Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 11 | 58 | Nonfuel Amount | `1500` | `1500` | Digits only, at most 7 for Interlink (8 only for Amex): '1500' = $15.00 | Element 58, PDF p.393 (printed 13-53) | PASS |
| 100 | 12 | 99 | Tax Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 13 | 17 | Cash Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 14 | 86 | Sequence Number | `031631` | `031631` | 6 digits, 000001-999999 | Element 86, PDF p.409 (printed 13-69) | PASS |
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

## Field-by-field check - step 2, void return (`TD-TC-003163.step2.void-return.json`)

| Segment | Field No. | Element | Field | Test data value | Test case value | ATL105 rule | Where in ATL105 | Result |
|---|---|---|---|---|---|---|---|---|
| Message | - | - | Message type | `Financial Transaction Request` | `-` | Financial Transaction Request (no chip data) | Section 11.1, PDF p.167 (printed 11-4) | PASS |
| Data Section 1 | 1 | 55 | Message Format Version Identifier | `ATL105` | `ATL105` | Fixed 6 characters: ATL105 | Section 11.1, PDF p.167 (printed 11-4); Element 55, PDF p.391 (printed 13-51) | PASS |
| Data Section 1 | 2 | 63 | Number of Segments | `02` | `-` | Up to 2 digits; equals the number of segments sent (2) | Section 11.1, PDF p.167 (printed 11-4); Element 63, PDF p.395 (printed 13-55) | PASS |
| 100 | 1 | 85 | Segment Type | `100` | `-` | Fixed value 100 | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 2 | 84 | Segment Length | `064` | `-` | Length of all 17 fields + 17 Field Separators = 064 (using the test case value for Approval Number); with the real run-time value(s) it is 064 - the declared value fits | Section 12.1, PDF p.223 (printed 12-6); Element 84, PDF p.406 (printed 13-66) | PASS |
| 100 | 3 | 44 | Information Byte | `0` | `0` | 1 character: '0' = single-message transmission | Element 44, PDF p.386 (printed 13-46) | PASS |
| 100 | 4 | 102 | Terminal Identifier | `JX72948216050257001` | `JX72948216050257001` | Device Type 'JX' + State Code '72' = Yukon (YT), Canada + Merchant Number and Device Number '948216050257001' | Element 102, PDF p.417 (printed 13-77); Appendix D, PDF p.517 (printed D-4) | PASS |
| 100 | 5 | 78 | Prompt Code | `U011` | `U011` | Transaction Type 'U' = Void of a Merchandise Return; Card Type '011' = Debit checking | Element 78, PDF p.400 (printed 13-60); Appendix G, PDF p.546 (printed G-2); Appendix E, PDF p.521 (printed E-4) | PASS |
| 100 | 6 | 2 | Account Number | `(empty)` | `(not sent)` | Required: the Account Number and the expiry as YYMM (follow-on transaction) | Element 2, PDF p.364 (printed 13-24); Section 10.2.5.2, PDF p.105 (printed 10-26) | FAIL |
| 100 | 7 | 12 | Card Discretionary Block Data | `(empty)` | `(not sent)` | Required with the Account Number (see the card data finding) | Element 12, PDF p.371 (printed 13-31) | FAIL |
| 100 | 8 | 33 | Encrypted PIN Block Data | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 9 | 79 | Pump/Lane Number | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 10 | 41 | Fuel Purchase Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 11 | 58 | Nonfuel Amount | `1500` | `1500` | Digits only, at most 7 for Interlink (8 only for Amex): '1500' = $15.00 | Element 58, PDF p.393 (printed 13-53) | PASS |
| 100 | 12 | 99 | Tax Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 13 | 17 | Cash Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 14 | 86 | Sequence Number | `031631` | `031631` | 6 digits, 000001-999999 | Element 86, PDF p.409 (printed 13-69) | PASS |
| 100 | 15 | 5 | Approval Number | `<ApprovalNumber from step 1 response>` | `KRY4U4` | Filled in at run time: Required on reversals for matching information to the original purchase. If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization. In the case of an Authorization Only Reversal, this element- present in the original transaction-must be present and identical in the Authorization Only Reversal transaction. (Note: | Element 5, PDF p.368 (printed 13-28) | BLOCKED |
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
| 2 | Same Sequence Number as the original | `031631` | `031631` | Element 86, PDF p.409 (printed 13-69); Section 10.1.2.1, PDF p.84 (printed 10-5) | PASS |
| 2 | Same Account Number as the original | `` | `` | Section 2.3.1, PDF p.40 (printed 2-5) | PASS |
| 2 | Same terminal as the original | `JX72948216050257001` | `JX72948216050257001` | Section 2.3.1, PDF p.40 (printed 2-5) | PASS |
| 2 | Same POS entry mode as the original | `82` | `82` | Appendix J, PDF p.638 (printed J-1) | PASS |

## Expected response codes

| Step | Expected Response Code | Meaning | Where in ATL105 | Result |
|---|---|---|---|---|
| 2 | `0` | Approved - Purchase/Capture | Element 83, PDF p.404 (printed 13-64) | PASS |

## Spec text quoted by the test case

| Cited page | Quote in the test case | Check | Result |
|---|---|---|---|
| PDF p.546 (printed Appendix G-2) | U Void of a Merchandise Return Void of a merchandise return | Found word-for-word on the cited page | PASS |
| PDF p.512 (printed Appendix C-2) | 48 Interlink - Authorizer Type: Debit | Not word-for-word: all its words are on the cited page, but not in this form | REVIEW |
| PDF p.97 (printed 10-18) | The following point-of-sale transactions are supported for debit card processing: ... Merchandise Return | Found word-for-word on the cited page | PASS |

