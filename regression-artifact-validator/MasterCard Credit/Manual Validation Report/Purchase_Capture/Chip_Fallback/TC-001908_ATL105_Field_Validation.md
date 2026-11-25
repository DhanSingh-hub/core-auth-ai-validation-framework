# TC-001908 (BR1_TS6_TC6) - ATL105 Test Data Validation

**BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – Chip Fallback Partial approval → remainder on 2nd card (split tender)**

| Item | Value |
|---|---|
| Result | **FAIL** |
| Can it run as-is? | No - fix the FAIL items first |
| Test data | `MasterCard Credit/mastercard_credit/test_data/TD-TC-001908.step1.purchase.json`, `MasterCard Credit/mastercard_credit/test_data/TD-TC-001908.step2.split-tender.json` |
| Template / status | `T4-PUR-SPLIT` / `needs_client_input` |
| Specification | BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026) |

All page references are to the BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026). "PDF p." is the page number shown in the PDF viewer; "printed" is the page number in the document footer.

- **FAIL** - a value in the test data breaks an ATL105 rule. It must be fixed.
- **BLOCKED** - the field holds instruction text instead of a value. It must be filled in from the step 1 response before the message can be sent.
- **REVIEW** - the data values are acceptable, but something in the test case must be confirmed or corrected.
- **INFO** - a note for the team that builds the wire message.
- **PASS** - matches ATL105.

## What must be fixed

### 1. Track data is sent with an entry mode that does not read the card - FAIL

- **Step:** 1
- **Where:** TD-TC-001908.step1.purchase.json -> Standard Message Data Segment -> CardDiscretionaryBlockData
- **Value:** `290720106815923`
- **What ATL105 says:**
  - Element 12 Card Discretionary Block Data (Chapter 13), PDF p.372 (printed 13-32): "For a card whose number has been manually entered, the expiration date is in MMYY format"
  - Section 10.1.1.3 Manual Entry, PDF p.81 (printed 10-2): "If unable to read the magnetic strip after three attempts, the device should prompt for manual entry of the Account Number and Expiration Date (Format: MMYY)."
  - Appendix J. Valid Point-of-Service Entry Mode Codes, PDF p.638 (printed Appendix J-1): "79 Contact and Contactless chip fallback to manual"
- **Why:** Table 005 entry mode '79' = chip fallback to manual: the card number is keyed in, so no magnetic stripe or chip data is read. '290720106815923' is Track 2 data (expiry 2907, service code 201, discretionary data). For a keyed card ATL105 sends only the expiration date, as MMYY.
- **What it should be / action:** '0729' (expiry MMYY), or a swipe entry mode if the card was really read

### 2. Track data is sent with an entry mode that does not read the card - FAIL

- **Step:** 2
- **Where:** TD-TC-001908.step2.split-tender.json -> Standard Message Data Segment -> CardDiscretionaryBlockData
- **Value:** `310620133641193`
- **What ATL105 says:**
  - Element 12 Card Discretionary Block Data (Chapter 13), PDF p.372 (printed 13-32): "For a card whose number has been manually entered, the expiration date is in MMYY format"
  - Section 10.1.1.3 Manual Entry, PDF p.81 (printed 10-2): "If unable to read the magnetic strip after three attempts, the device should prompt for manual entry of the Account Number and Expiration Date (Format: MMYY)."
  - Appendix J. Valid Point-of-Service Entry Mode Codes, PDF p.638 (printed Appendix J-1): "79 Contact and Contactless chip fallback to manual"
- **Why:** Table 005 entry mode '79' = chip fallback to manual: the card number is keyed in, so no magnetic stripe or chip data is read. '310620133641193' is Track 2 data (expiry 3106, service code 201, discretionary data). For a keyed card ATL105 sends only the expiration date, as MMYY.
- **What it should be / action:** '0631' (expiry MMYY), or a swipe entry mode if the card was really read

### 3. The test case value for Nonfuel Amount is too long - FAIL

- **Step:** 2
- **Where:** test case step 2 request -> Nonfuel Amount (used to build TD-TC-001908.step2.split-tender.json)
- **Value:** `18989100`
- **What ATL105 says:**
  - Element 58 Nonfuel Amount (Chapter 13), PDF p.393 (printed 13-53): "A maximum length of 7 bytes is allowed for all other card types."
- **Why:** '18989100' ($189,891.00) has 8 digits. MasterCard amounts allow at most 7 digits; 8 digits are allowed for American Express cards only.
- **What it should be / action:** Remaining balance = 1500 minus the step 1 response Approved Amount, leading zeros removed (for example Approved Amount 000001000 -> remainder 500).

### 4. The remaining balance cannot be larger than the original sale - FAIL

- **Step:** 2
- **Where:** test case step 2 request -> Nonfuel Amount
- **Value:** `18989100`
- **What ATL105 says:**
  - Section 10.1.2.1 Partial Approval Transaction, PDF p.84 (printed 10-5): "Approved Amount (Element No. 6) contains the approved partial amount, and the POS device must prompt for payment of the remaining balance due on the sale."
  - Element 121 Partial Approval Indicator (Chapter 13), PDF p.440 (printed 13-100): "the network can approve an amount that is less than the amount in the Authorization request"
  - Element 6 Approved Amount (Chapter 13), PDF p.368 (printed 13-28): "Fixed length of nine digits with two assumed decimal places Zero-filled and right aligned"
- **Why:** The original sale is $15.00 ('1500'). After a partial approval the remaining balance is the sale minus the approved amount, so it is always less than $15.00; '18989100' ($189,891.00) is impossible.
- **What it should be / action:** Remaining balance = 1500 minus the step 1 response Approved Amount, leading zeros removed (for example Approved Amount 000001000 -> remainder 500).

### 5. Segment Length was calculated from the invalid test case value - FAIL

- **Step:** 2
- **Where:** TD-TC-001908.step2.split-tender.json -> Standard Message Data Segment -> SegmentLength
- **Value:** `093`
- **What ATL105 says:**
  - Section 12.1 Standard Message Data Segment, PDF p.223 (printed 12-6): "Identifies the data segment's length, including Segment Type's length and Field Separators."
  - Element 84 Segment Length (Chapter 13), PDF p.406 (printed 13-66): "This figure is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements."
- **Why:** '093' is the length you get with the test case value(s) Nonfuel Amount '18989100'. With real values (amount 1 to 4 digits) the Segment Length can only be 086 to 089, so '093' can never be correct.
- **What it should be / action:** Recalculate the Segment Length after filling in the real values (for example Approved Amount 000001000 -> amount 500 -> Segment Length 088).

## Values to fill in at run time

### 6. Nonfuel Amount holds instruction text, not a value - BLOCKED

- **Step:** 2
- **Where:** TD-TC-001908.step2.split-tender.json -> Standard Message Data Segment -> NonfuelAmount
- **Value:** `<RemainingBalance = step 1 Nonfuel Amount - step 1 response Approved Amount>`
- **What ATL105 says:**
  - Section 10.1.2.1 Partial Approval Transaction, PDF p.84 (printed 10-5): "Approved Amount (Element No. 6) contains the approved partial amount, and the POS device must prompt for payment of the remaining balance due on the sale."
  - Element 6 Approved Amount (Chapter 13), PDF p.368 (printed 13-28): "Fixed length of nine digits with two assumed decimal places Zero-filled and right aligned"
- **Why:** The field holds the instruction text '<RemainingBalance = step 1 Nonfuel Amount - step 1 response Approved Amount>' instead of a real Nonfuel Amount. BUYPASS cannot process this text, so the message cannot be sent until the real value from the step 1 response is filled in.
- **What it should be / action:** Remaining balance = 1500 minus the step 1 response Approved Amount, leading zeros removed (for example Approved Amount 000001000 -> remainder 500).

## Items to confirm

### 7. Outdated page cited for the POS Entry Mode table (Table 005) - REVIEW

- **Where:** test case request -> Variable Information Data Segment -> Variable Information (source_page), every step
- **Value:** `p.548`
- **What ATL105 says:**
  - Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005), PDF p.555 (printed Appendix I-6): "Point-of-Service Entry Mode Information"
- **Why:** The test case cites p.548 for the POS Entry Mode table (Table 005). In ATL105 2026-4, PDF p.548 (printed Appendix H-2) does not contain it; it is on PDF p.555 (printed Appendix I-6). p.548 is where it was in the older 2026-3 edition.
- **What it should be / action:** Change the cited page from 548 to 555. The data values are not affected.

### 8. The quote cited for p.87 comes from an unrelated section - REVIEW

- **Where:** test case spec_basis
- **Value:** `A Split Tender transaction results when a Partial Approval is returned to the POS in the transaction response.`
- **What ATL105 says:**
  - Section 10.1.6.1.3 Split Tender Processing (part of 10.1.6 Healthcare Auto-Substantiation Service), PDF p.87 (printed 10-8): "A Split Tender transaction results when a Partial Approval is returned to the POS in the transaction response."
  - Section 10.1.2.1 Partial Approval Transaction, PDF p.84 (printed 10-5): "Approved Amount (Element No. 6) contains the approved partial amount, and the POS device must prompt for payment of the remaining balance due on the sale."
- **Why:** The quote is word-for-word, but it comes from Section 10.1.6.1.3 Split Tender Processing (part of 10.1.6 Healthcare Auto-Substantiation Service), PDF p.87 (printed 10-8); that section is about FSA/HRA healthcare benefit cards.
- **What it should be / action:** Cite Section 10.1.2.1 Partial Approval Transaction, PDF p.84 (printed 10-5) as the main basis for a normal MasterCard credit card.

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

### 11. Step 1 needs a partial-approval test card - REVIEW

- **Step:** 1
- **Where:** test case step 1 expected Response Code
- **Value:** `F`
- **What ATL105 says:**
  - Section 10.1.2.1 Partial Approval Transaction, PDF p.84 (printed 10-5): "This information is returned when a Partial Approval Indicator value of 1 is sent in Partial Approval Indicator (Element No. 121)."
  - Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "F Approved—Partial approval"
  - Element 121 Partial Approval Indicator (Chapter 13), PDF p.440 (printed 13-100): "the network can approve an amount that is less than the amount in the Authorization request"
- **Why:** Step 1 expects Response Code F (partial approval). BUYPASS returns F only when Partial Approval Indicator 1 is sent and the network approves less than the $15.00 requested, so the test needs a card whose available balance is below $15.00.
- **What it should be / action:** Client to supply a partial-approval test card or simulator trigger (the test case is already 'needs_client_input').

## Field-by-field check - step 1, purchase (`TD-TC-001908.step1.purchase.json`)

| Segment | Field No. | Element | Field | Test data value | Test case value | ATL105 rule | Where in ATL105 | Result |
|---|---|---|---|---|---|---|---|---|
| Message | - | - | Message type | `Financial Transaction Request` | `-` | Financial Transaction Request (no chip data) | Section 11.1, PDF p.167 (printed 11-4) | PASS |
| Data Section 1 | 1 | 55 | Message Format Version Identifier | `ATL105` | `ATL105` | Fixed 6 characters: ATL105 | Section 11.1, PDF p.167 (printed 11-4); Element 55, PDF p.391 (printed 13-51) | PASS |
| Data Section 1 | 2 | 63 | Number of Segments | `02` | `-` | Up to 2 digits; equals the number of segments sent (2) | Section 11.1, PDF p.167 (printed 11-4); Element 63, PDF p.395 (printed 13-55) | PASS |
| 100 | 1 | 85 | Segment Type | `100` | `-` | Fixed value 100 | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 2 | 84 | Segment Length | `089` | `-` | Length of all 17 fields + 17 Field Separators = 089 | Section 12.1, PDF p.223 (printed 12-6); Element 84, PDF p.406 (printed 13-66) | PASS |
| 100 | 3 | 44 | Information Byte | `0` | `0` | 1 character: '0' = single-message transmission | Element 44, PDF p.386 (printed 13-46) | PASS |
| 100 | 4 | 102 | Terminal Identifier | `JX72948216050257001` | `JX72948216050257001` | Device Type 'JX' + State Code '72' = Yukon (YT), Canada + Merchant Number and Device Number '948216050257001' | Element 102, PDF p.417 (printed 13-77); Appendix D, PDF p.517 (printed D-4) | PASS |
| 100 | 5 | 78 | Prompt Code | `0020` | `0020` | Transaction Type '0' = POS Purchase/Capture; Card Type '020' = Credit | Element 78, PDF p.400 (printed 13-60); Appendix G, PDF p.545 (printed G-1); Appendix E, PDF p.521 (printed E-4) | PASS |
| 100 | 6 | 2 | Account Number | `5102971996950398` | `5102971996950398` | Up to 24 characters; valid check digit and MasterCard range 51-55 / 2221-2720 (card-industry rule) | Element 2, PDF p.364 (printed 13-24) | PASS |
| 100 | 7 | 12 | Card Discretionary Block Data | `290720106815923` | `290720106815923` | Entry mode 79 (chip fallback to manual): no card read, so expiry MMYY only | Element 12, PDF p.372 (printed 13-32); Section 10.1.1.3, PDF p.81 (printed 10-2); Appendix J, PDF p.638 (printed J-1) | FAIL |
| 100 | 8 | 33 | Encrypted PIN Block Data | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 9 | 79 | Pump/Lane Number | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 10 | 41 | Fuel Purchase Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 11 | 58 | Nonfuel Amount | `1500` | `1500` | Digits only, at most 7 for MasterCard (8 only for Amex): '1500' = $15.00 | Element 58, PDF p.393 (printed 13-53) | PASS |
| 100 | 12 | 99 | Tax Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 13 | 17 | Cash Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 14 | 86 | Sequence Number | `019081` | `019081` | 6 digits, 000001-999999 | Element 86, PDF p.409 (printed 13-69) | PASS |
| 100 | 15 | 5 | Approval Number | `(empty)` | `(not sent)` | Not sent on a purchase (it would make the purchase preauthorized) | Element 5, PDF p.368 (printed 13-28) | PASS |
| 100 | 16 | 49 | Local Date and Local Time | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 17 | 121 | Partial Approval Indicator | `1` | `1` | '1' = partial approval supported | Element 121, PDF p.440 (printed 13-100) | PASS |
| 111 | 1 | 85 | Segment Type | `111` | `111` | Fixed value 111 | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 2 | 84 | Segment Length | `026` | `-` | 3 (type) + 3 (length) + 17 (tables) + 3 Field Separators = 026 | Section 12.10, PDF p.246 (printed 12-29); Element 84, PDF p.406 (printed 13-66) | PASS |
| 111 | 3 [1] | 111 | Variable Information Indicator | `005` | `-` | 3-character table ID (Table 005) | Element 111, PDF p.422 (printed 13-82) | PASS |
| 111 | 4 [1] | 112 | Variable Information Length | `003` | `-` | 3 digits = length of the table data (003) | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 5 [1] | 113 | Variable Information (Table 005) | `791` | `791` | '79' = chip fallback to manual; terminal capability '1' = PIN entry capability | Appendix I, PDF p.555 (printed I-6); Appendix J, PDF p.638 (printed J-1); Appendix J, PDF p.639 (printed J-2) | PASS |
| 111 | 3 [2] | 111 | Variable Information Indicator | `031` | `-` | 3-character table ID (Table 031) | Element 111, PDF p.422 (printed 13-82) | PASS |
| 111 | 4 [2] | 112 | Variable Information Length | `002` | `-` | 3 digits = length of the table data (002) | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 5 [2] | 113 | Variable Information (Table 031) | `12` | `12` | '12' = hybrid reader: contact chip and contactless chip; required on every transaction | Appendix I, PDF p.569 (printed I-20) | PASS |

## Field-by-field check - step 2, split tender (`TD-TC-001908.step2.split-tender.json`)

| Segment | Field No. | Element | Field | Test data value | Test case value | ATL105 rule | Where in ATL105 | Result |
|---|---|---|---|---|---|---|---|---|
| Message | - | - | Message type | `Financial Transaction Request` | `-` | Financial Transaction Request (no chip data) | Section 11.1, PDF p.167 (printed 11-4) | PASS |
| Data Section 1 | 1 | 55 | Message Format Version Identifier | `ATL105` | `ATL105` | Fixed 6 characters: ATL105 | Section 11.1, PDF p.167 (printed 11-4); Element 55, PDF p.391 (printed 13-51) | PASS |
| Data Section 1 | 2 | 63 | Number of Segments | `02` | `-` | Up to 2 digits; equals the number of segments sent (2) | Section 11.1, PDF p.167 (printed 11-4); Element 63, PDF p.395 (printed 13-55) | PASS |
| 100 | 1 | 85 | Segment Type | `100` | `-` | Fixed value 100 | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 2 | 84 | Segment Length | `093` | `-` | Length of all 17 fields + 17 Field Separators = 093 (using the test case value for Nonfuel Amount) | Section 12.1, PDF p.223 (printed 12-6); Element 84, PDF p.406 (printed 13-66) | FAIL |
| 100 | 3 | 44 | Information Byte | `0` | `0` | 1 character: '0' = single-message transmission | Element 44, PDF p.386 (printed 13-46) | PASS |
| 100 | 4 | 102 | Terminal Identifier | `JX72948216050257001` | `JX72948216050257001` | Device Type 'JX' + State Code '72' = Yukon (YT), Canada + Merchant Number and Device Number '948216050257001' | Element 102, PDF p.417 (printed 13-77); Appendix D, PDF p.517 (printed D-4) | PASS |
| 100 | 5 | 78 | Prompt Code | `0020` | `0020` | Transaction Type '0' = POS Purchase/Capture; Card Type '020' = Credit | Element 78, PDF p.400 (printed 13-60); Appendix G, PDF p.545 (printed G-1); Appendix E, PDF p.521 (printed E-4) | PASS |
| 100 | 6 | 2 | Account Number | `5186287391495312` | `5186287391495312` | Up to 24 characters; valid check digit and MasterCard range 51-55 / 2221-2720 (card-industry rule) | Element 2, PDF p.364 (printed 13-24) | PASS |
| 100 | 7 | 12 | Card Discretionary Block Data | `310620133641193` | `310620133641193` | Entry mode 79 (chip fallback to manual): no card read, so expiry MMYY only | Element 12, PDF p.372 (printed 13-32); Section 10.1.1.3, PDF p.81 (printed 10-2); Appendix J, PDF p.638 (printed J-1) | FAIL |
| 100 | 8 | 33 | Encrypted PIN Block Data | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 9 | 79 | Pump/Lane Number | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 10 | 41 | Fuel Purchase Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 11 | 58 | Nonfuel Amount | `<RemainingBalance = step 1 Nonfuel Amount - step 1 response Approved Amount>` | `18989100` | Filled in at run time: Amount = requested amount minus the Approved Amount (Element 6) of the partial approval response (p.84 'remaining balance due'); runtime link. | Section 10.1.2.1, PDF p.84 (printed 10-5); Element 6, PDF p.368 (printed 13-28) | BLOCKED |
| 100 | 12 | 99 | Tax Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 13 | 17 | Cash Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 14 | 86 | Sequence Number | `019082` | `019082` | 6 digits, 000001-999999 | Element 86, PDF p.409 (printed 13-69) | PASS |
| 100 | 15 | 5 | Approval Number | `(empty)` | `(not sent)` | Not sent on a purchase (it would make the purchase preauthorized) | Element 5, PDF p.368 (printed 13-28) | PASS |
| 100 | 16 | 49 | Local Date and Local Time | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 17 | 121 | Partial Approval Indicator | `1` | `1` | '1' = partial approval supported | Element 121, PDF p.440 (printed 13-100) | PASS |
| 111 | 1 | 85 | Segment Type | `111` | `111` | Fixed value 111 | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 2 | 84 | Segment Length | `026` | `-` | 3 (type) + 3 (length) + 17 (tables) + 3 Field Separators = 026 | Section 12.10, PDF p.246 (printed 12-29); Element 84, PDF p.406 (printed 13-66) | PASS |
| 111 | 3 [1] | 111 | Variable Information Indicator | `005` | `-` | 3-character table ID (Table 005) | Element 111, PDF p.422 (printed 13-82) | PASS |
| 111 | 4 [1] | 112 | Variable Information Length | `003` | `-` | 3 digits = length of the table data (003) | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 5 [1] | 113 | Variable Information (Table 005) | `791` | `791` | '79' = chip fallback to manual; terminal capability '1' = PIN entry capability | Appendix I, PDF p.555 (printed I-6); Appendix J, PDF p.638 (printed J-1); Appendix J, PDF p.639 (printed J-2) | PASS |
| 111 | 3 [2] | 111 | Variable Information Indicator | `031` | `-` | 3-character table ID (Table 031) | Element 111, PDF p.422 (printed 13-82) | PASS |
| 111 | 4 [2] | 112 | Variable Information Length | `002` | `-` | 3 digits = length of the table data (002) | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 5 [2] | 113 | Variable Information (Table 031) | `12` | `12` | '12' = hybrid reader: contact chip and contactless chip; required on every transaction | Appendix I, PDF p.569 (printed I-20) | PASS |

## Step-to-step checks

| Step | Check | This step | Original step | Where in ATL105 | Result |
|---|---|---|---|---|---|
| 2 | New Sequence Number | `019082` | `019081` | Section 2.3.1, PDF p.40 (printed 2-5) | PASS |
| 2 | A different card pays the remaining balance | `5186287391495312` | `5102971996950398` | Section 10.1.2.1, PDF p.84 (printed 10-5) | PASS |

## Expected response codes

| Step | Expected Response Code | Meaning | Where in ATL105 | Result |
|---|---|---|---|---|
| 1 | `F` | Approved - Partial approval | Element 83, PDF p.404 (printed 13-64) | PASS |
| 2 | `0` | Approved - Purchase/Capture | Element 83, PDF p.404 (printed 13-64) | PASS |

## Spec text quoted by the test case

| Cited page | Quote in the test case | Check | Result |
|---|---|---|---|
| PDF p.84 (printed 10-5) | A Response Code (Element No. 83) value of F (Indicates "approval, partial amount approved.") | Found word-for-word on the cited page | PASS |
| PDF p.84 (printed 10-5) | Partial Approval transactions are supported for: POS Purchase, POS Authorization Only, CAT Authorization Only | Found word-for-word on the cited page | PASS |
| PDF p.84 (printed 10-5) | Approved Amount (Element No. 6) contains the approved partial amount, and the POS device must prompt for payment of the remaining balance due on the sale. | Found word-for-word on the cited page | PASS |
| PDF p.87 (printed 10-8) | A Split Tender transaction results when a Partial Approval is returned to the POS in the transaction response. | Found word-for-word on the cited page | PASS |
| PDF p.511 (printed Appendix C-1) | 03 MasterCard - Authorizer Type: Credit | Not word-for-word: all its words are on the cited page, but not in this form | REVIEW |
| PDF p.83 (printed 10-4) | The following point-of-sale transactions are supported for credit card processing: ... Purchase/Capture | Found word-for-word on the cited page | PASS |
| PDF p.162 (printed 10-83) | If a chip card is presented at a chip capable terminal and a chip transaction cannot be performed, then the merchant may process the chip card as a magnetic stripe read | Found word-for-word on the cited page | PASS |

