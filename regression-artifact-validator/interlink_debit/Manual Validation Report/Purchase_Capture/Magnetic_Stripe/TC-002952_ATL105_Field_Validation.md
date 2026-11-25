# TC-002952 (BR1_TS3_TC17) - ATL105 Test Data Validation

**BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Magnetic Stripe Purchase → Settlement (read and cut 222222)**

| Item | Value |
|---|---|
| Result | **FAIL** |
| Can it run as-is? | No - fix the FAIL items first |
| Test data | `interlink_debit/test_data/TD-TC-002952.step1.purchase.json`, `interlink_debit/test_data/TD-TC-002952.step2.totals.json` |
| Template / status | `T8-PUR-SETTLE` / `pending_sme` |
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
- **Where:** TD-TC-002952.step1.purchase.json -> Standard Message Data Segment -> AccountNumber, CardDiscretionaryBlockData
- **Value:** `(empty)`
- **What ATL105 says:**
  - Element 2 Account Number (Chapter 13), PDF p.364 (printed 13-24): "a variable length (up to 24 alphanumeric bytes) Account Number"
  - Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "If the Account Number is obtained from a Track 2 card reader, instead of being manually keyed, the Account Number and Card Discretionary Block Data should consist of all data read from the card between, but not including, the Start Sentinel and End Sentinel."
  - Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number is considered the data before the = character, and Card Discretionary Block Data is considered the data after the = character."
- **Why:** This purchase must carry the data read from the card (entry mode 90 = magnetic stripe, full track data), but Account Number and Card Discretionary Block Data are empty, so BUYPASS cannot identify the card. The test case marks the Account Number AWAITING_CLIENT_VALUE: the Interlink Debit card profile was never supplied, and the generator wrote an empty field instead of a value or an explicit placeholder. The Card Discretionary Block Data is marked as an omitted conditional field, although it is needed here.
- **What it should be / action:** Fill in the data read from the card (entry mode 90 = magnetic stripe, full track data) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field.

### 2. The debit PIN block (Element 33) is empty - FAIL

- **Step:** 1
- **Where:** TD-TC-002952.step1.purchase.json -> Standard Message Data Segment -> EncryptedPINBlockData
- **Value:** `(empty)`
- **What ATL105 says:**
  - Section 10.2.1.3 PIN Entry, PDF p.96 (printed 10-17): "All transactions—except the Purchase Reversal transaction—require PIN entry."
  - Section 10.2.1.3 PIN Entry, PDF p.96 (printed 10-17): "When processing a debit card transaction, the entry of the customer PIN is required on a DES-compliant entry device, and it must be encrypted using the DUKPT PIN encryption method."
  - Element 33 Encrypted PIN Block Data (Chapter 13), PDF p.382 (printed 13-42): "Required for debit and EBT."
  - Element 33 Encrypted PIN Block Data (Chapter 13), PDF p.382 (printed 13-42): "For DUKPT PIN encryption, this is a 16- to 20-byte Key Serial Number (KSN) plus a 16-byte PIN block."
- **Why:** ATL105 requires PIN entry, DUKPT-encrypted, on every debit transaction except the Purchase Reversal, and Element 33 is required for debit. This purchase (Transaction Type 0) sends no PIN block.
- **What it should be / action:** Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input).

### 3. Segment 105 length is counted with the wrong separators and label width - FAIL

- **Step:** 2
- **Where:** TD-TC-002952.step2.totals.json -> Totals Data Segment -> SegmentLength
- **Value:** `108`
- **What ATL105 says:**
  - Section 12.6 Totals Data Segment, PDF p.235 (printed 12-18): "Field Nos. 1–14 are separated by Field Separators, with a Field Separator following Field No. 14."
  - Section 12.6 Totals Data Segment, PDF p.235 (printed 12-18): "Field Nos. 15–17 are not separated by Field Separators."
  - Element 13 Card Label (Chapter 13), PDF p.372 (printed 13-32): "Fixed length of four alphanumeric characters; left-aligned and space-filled"
  - Element 84 Segment Length (Chapter 13), PDF p.406 (printed 13-66): "This figure is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements."
- **Why:** Fields 1-14 add up to 76 characters, plus 14 Field Separators = 90. Fields 15-17 (the Category Total Data Section) are not separated: Card Label 4 (fixed width) + Count 5 + Amount 8 = 17. The Segment Length is therefore 107, not '108'. '108' is what you get with a Field Separator after every one of the 17 fields and the 2-character Card Label, as in Segment 100.
- **What it should be / action:** 107

### 4. Card Label (Element 13) is not 4 characters - FAIL

- **Step:** 2
- **Where:** TD-TC-002952.step2.totals.json -> Totals Data Segment -> CardLabel
- **Value:** `'DB'`
- **What ATL105 says:**
  - Element 13 Card Label (Chapter 13), PDF p.372 (printed 13-32): "Fixed length of four alphanumeric characters; left-aligned and space-filled"
  - Element 13 Card Label (Chapter 13), PDF p.373 (printed 13-33): "Debit DB"
- **Why:** Element 13 is a fixed field of four characters, left-aligned and space-filled. 'DB' has 2, so the fields after it shift by 2 on the wire.
- **What it should be / action:** 'DB  ' (DB followed by 2 spaces)

## Items to confirm

### 5. The device version fields of the Totals Request are generated values - REVIEW

- **Step:** 2
- **Where:** TD-TC-002952.step2.totals.json -> Totals Data Segment
- **Value:** `WQJI6DEX, PHULB0ED, 2XGO93RJ`
- **What ATL105 says:**
  - Element 43 Hardware Version (Chapter 13), PDF p.386 (printed 13-46): "Fixed length of four or eight alphanumeric characters"
  - Element 96 Software Version (Chapter 13), PDF p.414 (printed 13-74): "Fixed length of eight alphanumeric characters"
  - Element 39 Firmware Version (Chapter 13), PDF p.384 (printed 13-44): "Fixed length of eight alphanumeric characters"
- **Why:** ATL105 requires the device's hardware, software and firmware versions on a Totals Request. The test case generated random values, so they do not identify a certified device.
- **What it should be / action:** Use the versions of the certified device that runs the test.

### 6. The test case does not state the expected Response Code - REVIEW

- **Where:** test case step 2 expected_response
- **Value:** `? (FLAG)`
- **What ATL105 says:**
  - Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "0 Approved—Purchase/Capture"
  - Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "2 Approved—Authorization only"
- **Why:** The expected response is left open ('?'), so a run cannot pass or fail on the Response Code. Element 83 has a separate approval code for each kind of transaction.
- **What it should be / action:** State the expected Response Code for this transaction type (from BUYPASS or a reference run).

### 7. Outdated page cited for the POS Entry Mode table (Table 005) - REVIEW

- **Where:** test case request -> Variable Information Data Segment -> Variable Information (source_page), every step
- **Value:** `p.548`
- **What ATL105 says:**
  - Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005), PDF p.555 (printed Appendix I-6): "Point-of-Service Entry Mode Information"
- **Why:** The test case cites p.548 for the POS Entry Mode table (Table 005). In ATL105 2026-4, PDF p.548 (printed Appendix H-2) does not contain it; it is on PDF p.555 (printed Appendix I-6). p.548 is where it was in the older 2026-3 edition.
- **What it should be / action:** Change the cited page from 548 to 555. The data values are not affected.

### 8. The quote cited for p.512 is not word-for-word in ATL105 - REVIEW

- **Where:** test case spec_basis
- **Value:** `48 Interlink - Authorizer Type: Debit`
- **What ATL105 says:**
  - (no specific ATL105 page)
- **Why:** The test case quotes "48 Interlink - Authorizer Type: Debit" for p.512. Not word-for-word: all its words are on the cited page, but not in this form.
- **What it should be / action:** Quote the text exactly as it appears on the page.

### 9. The Terminal Identifier is a placeholder - REVIEW

- **Where:** Standard Message Data Segment -> TerminalIdentifier (every step)
- **Value:** `JX72948216050257001`
- **What ATL105 says:**
  - Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."
  - Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code)."
  - Appendix D. Valid State Codes, PDF p.517 (printed Appendix D-4): "Yukon YT 72"
- **Why:** The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada.
- **What it should be / action:** Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location.

## Field-by-field check - step 1, purchase (`TD-TC-002952.step1.purchase.json`)

| Segment | Field No. | Element | Field | Test data value | Test case value | ATL105 rule | Where in ATL105 | Result |
|---|---|---|---|---|---|---|---|---|
| Message | - | - | Message type | `Financial Transaction Request` | `-` | Financial Transaction Request (no chip data) | Section 11.1, PDF p.167 (printed 11-4) | PASS |
| Data Section 1 | 1 | 55 | Message Format Version Identifier | `ATL105` | `ATL105` | Fixed 6 characters: ATL105 | Section 11.1, PDF p.167 (printed 11-4); Element 55, PDF p.391 (printed 13-51) | PASS |
| Data Section 1 | 2 | 63 | Number of Segments | `02` | `-` | Up to 2 digits; equals the number of segments sent (2) | Section 11.1, PDF p.167 (printed 11-4); Element 63, PDF p.395 (printed 13-55) | PASS |
| 100 | 1 | 85 | Segment Type | `100` | `-` | Fixed value 100 | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 2 | 84 | Segment Length | `058` | `-` | Length of all 17 fields + 17 Field Separators = 058 | Section 12.1, PDF p.223 (printed 12-6); Element 84, PDF p.406 (printed 13-66) | PASS |
| 100 | 3 | 44 | Information Byte | `0` | `0` | 1 character: '0' = single-message transmission | Element 44, PDF p.386 (printed 13-46) | PASS |
| 100 | 4 | 102 | Terminal Identifier | `JX72948216050257001` | `JX72948216050257001` | Device Type 'JX' + State Code '72' = Yukon (YT), Canada + Merchant Number and Device Number '948216050257001' | Element 102, PDF p.417 (printed 13-77); Appendix D, PDF p.517 (printed D-4) | PASS |
| 100 | 5 | 78 | Prompt Code | `0011` | `0011` | Transaction Type '0' = POS Purchase/Capture; Card Type '011' = Debit checking | Element 78, PDF p.400 (printed 13-60); Appendix G, PDF p.545 (printed G-1); Appendix E, PDF p.521 (printed E-4) | PASS |
| 100 | 6 | 2 | Account Number | `(empty)` | `(not sent)` | Required: the data read from the card (entry mode 90 = magnetic stripe, full track data) | Element 2, PDF p.364 (printed 13-24); Element 12, PDF p.371 (printed 13-31) | FAIL |
| 100 | 7 | 12 | Card Discretionary Block Data | `(empty)` | `(not sent)` | Required with the Account Number (see the card data finding) | Element 12, PDF p.371 (printed 13-31) | FAIL |
| 100 | 8 | 33 | Encrypted PIN Block Data | `(empty)` | `(not sent)` | Required for debit: DUKPT Key Serial Number + PIN block | Section 10.2.1.3, PDF p.96 (printed 10-17); Element 33, PDF p.382 (printed 13-42) | FAIL |
| 100 | 9 | 79 | Pump/Lane Number | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 10 | 41 | Fuel Purchase Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 11 | 58 | Nonfuel Amount | `1500` | `1500` | Digits only, at most 7 for Interlink (8 only for Amex): '1500' = $15.00 | Element 58, PDF p.393 (printed 13-53) | PASS |
| 100 | 12 | 99 | Tax Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 13 | 17 | Cash Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 14 | 86 | Sequence Number | `029521` | `029521` | 6 digits, 000001-999999 | Element 86, PDF p.409 (printed 13-69) | PASS |
| 100 | 15 | 5 | Approval Number | `(empty)` | `(not sent)` | Not sent on a purchase (it would make the purchase preauthorized) | Element 5, PDF p.368 (printed 13-28) | PASS |
| 100 | 16 | 49 | Local Date and Local Time | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 17 | 121 | Partial Approval Indicator | `1` | `1` | '1' = partial approval supported | Element 121, PDF p.440 (printed 13-100) | PASS |
| 111 | 1 | 85 | Segment Type | `111` | `111` | Fixed value 111 | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 2 | 84 | Segment Length | `026` | `-` | 3 (type) + 3 (length) + 17 (tables) + 3 Field Separators = 026 | Section 12.10, PDF p.246 (printed 12-29); Element 84, PDF p.406 (printed 13-66) | PASS |
| 111 | 3 [1] | 111 | Variable Information Indicator | `005` | `-` | 3-character table ID (Table 005) | Element 111, PDF p.422 (printed 13-82) | PASS |
| 111 | 4 [1] | 112 | Variable Information Length | `003` | `-` | 3 digits = length of the table data (003) | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 5 [1] | 113 | Variable Information (Table 005) | `901` | `901` | '90' = magnetic stripe, full track data; terminal capability '1' = PIN entry capability | Appendix I, PDF p.555 (printed I-6); Appendix J, PDF p.638 (printed J-1); Appendix J, PDF p.639 (printed J-2) | PASS |
| 111 | 3 [2] | 111 | Variable Information Indicator | `031` | `-` | 3-character table ID (Table 031) | Element 111, PDF p.422 (printed 13-82) | PASS |
| 111 | 4 [2] | 112 | Variable Information Length | `002` | `-` | 3 digits = length of the table data (002) | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 5 [2] | 113 | Variable Information (Table 031) | `12` | `12` | '12' = hybrid reader: contact chip and contactless chip; required on every transaction | Appendix I, PDF p.569 (printed I-20) | PASS |

## Field-by-field check - step 2, totals (`TD-TC-002952.step2.totals.json`)

| Segment | Field No. | Element | Field | Test data value | Test case value | ATL105 rule | Where in ATL105 | Result |
|---|---|---|---|---|---|---|---|---|
| Message | - | - | Message type | `Totals Request` | `-` | Totals Request: Data Section 1 + Totals Data Segment (105); no Segment 100 | Section 11.4.1.1, PDF p.178 (printed 11-15) | PASS |
| Data Section 1 | 1 | 55 | Message Format Version Identifier | `ATL105` | `ATL105` | Fixed 6 characters: ATL105 | Section 11.1, PDF p.167 (printed 11-4); Element 55, PDF p.391 (printed 13-51) | PASS |
| Data Section 1 | 2 | 63 | Number of Segments | `01` | `-` | Equals the number of segments sent (1) | Element 63, PDF p.395 (printed 13-55) | PASS |
| 105 | 1 | 85 | Segment Type | `105` | `105` | Fixed value 105 | Section 12.6, PDF p.235 (printed 12-18) | PASS |
| 105 | 2 | 84 | Segment Length | `108` | `-` | Fields 1-14 (76) + 14 Field Separators + Category Total Data Section without separators (Card Label 4 + Count 5 + Amount 8) = 107 | Section 12.6, PDF p.235 (printed 12-18); Element 84, PDF p.406 (printed 13-66) | FAIL |
| 105 | 3 | 44 | Information Byte | `0` | `0` | '0' = single-message transmission | Element 44, PDF p.386 (printed 13-46) | PASS |
| 105 | 4 | 102 | Terminal Identifier | `JX72948216050257001` | `JX72948216050257001` | Same device as the financial steps; State Code '72' = Yukon (YT) | Element 102, PDF p.417 (printed 13-77) | PASS |
| 105 | 5 | 78 | Prompt Code | `990` | `990` | Fixed value 990 (Totals) | Section 12.6, PDF p.235 (printed 12-18) | PASS |
| 105 | 6 | 32 | Employee Number | `(empty)` | `-` | Conditional - not sent (the Field Separator is still sent) | - | PASS |
| 105 | 7 | 65 | Password | `(empty)` | `-` | Conditional - not sent (the Field Separator is still sent) | - | PASS |
| 105 | 8 | 105 | Totals Date | `222222` | `222222` | '222222' = current settlement date, read and cut | Element 105, PDF p.419 (printed 13-79) | PASS |
| 105 | 9 | 43 | Hardware Version | `WQJI6DEX` | `WQJI6DEX` | Device hardware version, 4 or 8 alphanumeric characters | Element 43, PDF p.386 (printed 13-46) | PASS |
| 105 | 10 | 96 | Software Version | `PHULB0ED` | `PHULB0ED` | Device software version, 8 alphanumeric characters | Element 96, PDF p.414 (printed 13-74) | PASS |
| 105 | 11 | 39 | Firmware Version | `2XGO93RJ` | `2XGO93RJ` | Device firmware version, 8 alphanumeric characters | Element 39, PDF p.384 (printed 13-44) | PASS |
| 105 | 12 | 86 | Sequence Number | `029522` | `029522` | 6 digits, not 000000; a new Sequence Number for the totals request | Element 86, PDF p.409 (printed 13-69) | PASS |
| 105 | 13 | 20 | Currency Code | `840` | `840` | 3 digits; '840' = US dollar (Appendix L, default 840) | Element 20, PDF p.376 (printed 13-36) | PASS |
| 105 | 14 | 42 | Grand Total | `00001500` | `00001500` | 8 digits with two assumed decimals: $15.00 | Element 42, PDF p.386 (printed 13-46); Section 9.2, PDF p.72 (printed 9-8) | PASS |
| 105 | 15 | 13 | Card Label | `DB` | `DB` | Fixed 4 characters, left-aligned and space-filled; DB = Debit bucket | Element 13, PDF p.372 (printed 13-32); Section 12.6, PDF p.237 (printed 12-20) | FAIL |
| 105 | 16 | 16 | Card Type Total Count | `00001` | `00001` | 5 digits, 00001-99999 | Element 16, PDF p.375 (printed 13-35) | PASS |
| 105 | 17 | 15 | Card Type Total Amount | `00001500` | `00001500` | 8 digits with two assumed decimals: $15.00 | Element 15, PDF p.374 (printed 13-34) | PASS |

## Step-to-step checks

| Step | Check | This step | Original step | Where in ATL105 | Result |
|---|---|---|---|---|---|
| 2 | Totals match the transactions of the flow (Section 9.2) | `count 00001, amount 00001500, grand total 00001500` | `count 1, amount 1500` | Section 9.2, PDF p.72 (printed 9-8) | PASS |

## Expected response codes

| Step | Expected Response Code | Meaning | Where in ATL105 | Result |
|---|---|---|---|---|
| 1 | `0` | Approved - Purchase/Capture | Element 83, PDF p.404 (printed 13-64) | PASS |

## Spec text quoted by the test case

| Cited page | Quote in the test case | Check | Result |
|---|---|---|---|
| PDF p.78 (printed 9-14) | Settlement totals can be requested as read only or read and cut | Found word-for-word on the cited page | PASS |
| PDF p.79 (printed 9-15) | the controller completes a read and cut total request (Totals Date code: 222222) | Found word-for-word on the cited page | PASS |
| PDF p.89 (printed 10-10) | All approved Purchase/Capture transactions are added to the count and amount of the card type totals bucket. | Found word-for-word on the cited page | PASS |
| PDF p.512 (printed Appendix C-2) | 48 Interlink - Authorizer Type: Debit | Not word-for-word: all its words are on the cited page, but not in this form | REVIEW |
| PDF p.97 (printed 10-18) | The following point-of-sale transactions are supported for debit card processing: ... Purchase/Capture | Found word-for-word on the cited page | PASS |
| PDF p.81 (printed 10-2) | If a Pre-Auth transaction is approved offline, the full track data must be sent. Also if a Sale transaction is approved offline, the full track data must be sent | Found word-for-word on the cited page | PASS |

