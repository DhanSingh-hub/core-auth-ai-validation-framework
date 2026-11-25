# TC-002038 (BR1_TS12_TC4) - ATL105 Test Data Validation

**BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Authorization Only – EMV Contactless Chip Partial authorization → Completion for approved amount**

| Item | Value |
|---|---|
| Result | **FAIL** |
| Can it run as-is? | No - fix the FAIL items first |
| Test data | `MasterCard Credit/mastercard_credit/test_data/TD-TC-002038.step1.auth-only.json`, `MasterCard Credit/mastercard_credit/test_data/TD-TC-002038.step2.completion.json` |
| Template / status | `T4-AUT-COMPLETION` / `needs_client_input` |
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
- **Where:** TD-TC-002038.step1.auth-only.json -> Variable Information Data Segment
- **Value:** `tables sent: 005, 031`
- **What ATL105 says:**
  - Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030), PDF p.568 (printed Appendix I-19): "Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions."
- **Why:** This step is an EMV transaction (Segment 130 is sent). Table 030 identifies the device class, presentation data, security condition and terminal type, and ATL105 requires it for all EMV and all Credential on File transactions.
- **What it should be / action:** Add Table 030 (10 digits, values from Appendix Q) for the certified terminal.

### 2. EMV Card Sequence Number (Element 188) is 2 bytes instead of 3 - FAIL

- **Step:** 1
- **Where:** TD-TC-002038.step1.auth-only.json -> EMV Request Data Segment -> EMVCardSequenceNumber
- **Value:** `01`
- **What ATL105 says:**
  - Element 188 EMV Card Sequence Number (Chapter 13), PDF p.477 (printed 13-137): "Fixed length of three bytes, right-aligned and padded with zeroes"
  - Element 188 EMV Card Sequence Number (Chapter 13), PDF p.477 (printed 13-137): "Valid Codes/Values: 000—099 EMV Card Sequence Number is supplied by the EMV chip card"
- **Why:** Element 188 is fixed at 3 bytes, right-aligned and zero-filled. The chip data carries Tag 5F34 = '01', so the card sequence number itself is right, but '01' is 2 bytes; it must be '001'. The declared Segment Length (1181) was calculated with the 2-byte value and changes by +1 when corrected.
- **What it should be / action:** '001' (then recalculate the Segment 130 length)

### 3. EMV Additional Information (Element 118) does not contain EMV Table Data - FAIL

- **Step:** 1
- **Where:** TD-TC-002038.step1.auth-only.json -> EMV Request Data Segment -> EMVAdditionalInformation
- **Value:** `7LB8CEDF37JK57D9CATB...B5SWLWMF (984 characters)`
- **What ATL105 says:**
  - Element 118 EMV Additional Information (Chapter 13), PDF p.439 (printed 13-99): "When EMV Additional Information Indicator has a value of 001, EMV table data is included here."
  - Appendix T. EMV Additional Information Data Layouts - EMV Table Data, PDF p.701 (printed Appendix T-1): "EMVYES This code indicates that the device is able to receive EMV Table Data."
  - Appendix T. EMV Additional Information Data Layouts - EMV Table Data, PDF p.701 (printed Appendix T-1): "EMVNOT This code indicates that the device is unable to receive EMV Table Data."
  - Appendix T. EMV Additional Information Data Layouts - EMV Table Data, PDF p.701 (printed Appendix T-1): "Maximum Length n6 Len.: 6"
- **Why:** Indicator 001 means EMV Table Data, and Appendix T allows only EMVYES or EMVNOT (6 characters). The field holds 984 generated characters. Element 192 = '984' matches that length, so it is consistent with the content, but the content itself is not allowed for indicator 001.
- **What it should be / action:** EMVYES (device can receive EMV Table Data) or EMVNOT, with Element 192 = 006 (see the open question on framing); the Segment 130 length then becomes 0204 together with the Element 188 fix.

### 4. The test case value for Nonfuel Amount is too long - FAIL

- **Step:** 2
- **Where:** test case step 2 request -> Nonfuel Amount (used to build TD-TC-002038.step2.completion.json)
- **Value:** `90964076`
- **What ATL105 says:**
  - Element 58 Nonfuel Amount (Chapter 13), PDF p.393 (printed 13-53): "A maximum length of 7 bytes is allowed for all other card types."
- **Why:** '90964076' ($909,640.76) has 8 digits. MasterCard amounts allow at most 7 digits; 8 digits are allowed for American Express cards only.
- **What it should be / action:** Completion amount = the step 1 response Approved Amount without the leading zeros (for example 000001000 -> 1000).

### 5. The completion amount cannot be larger than the original authorization - FAIL

- **Step:** 2
- **Where:** test case step 2 request -> Nonfuel Amount
- **Value:** `90964076`
- **What ATL105 says:**
  - Section 10.1.2.1 Partial Approval Transaction, PDF p.84 (printed 10-5): "Approved Amount (Element No. 6) contains the approved partial amount, and the POS device must prompt for payment of the remaining balance due on the sale."
  - Element 121 Partial Approval Indicator (Chapter 13), PDF p.440 (printed 13-100): "the network can approve an amount that is less than the amount in the Authorization request"
  - Element 6 Approved Amount (Chapter 13), PDF p.368 (printed 13-28): "Fixed length of nine digits with two assumed decimal places Zero-filled and right aligned"
- **Why:** The completion must carry exactly the amount approved in step 1. A partial approval is always less than the $15.00 requested, so '90964076' ($909,640.76) is impossible.
- **What it should be / action:** Completion amount = the step 1 response Approved Amount without the leading zeros (for example 000001000 -> 1000).

### 6. Segment Length was calculated from the invalid test case value - FAIL

- **Step:** 2
- **Where:** TD-TC-002038.step2.completion.json -> Standard Message Data Segment -> SegmentLength
- **Value:** `088`
- **What ATL105 says:**
  - Section 12.1 Standard Message Data Segment, PDF p.223 (printed 12-6): "Identifies the data segment's length, including Segment Type's length and Field Separators."
  - Element 84 Segment Length (Chapter 13), PDF p.406 (printed 13-66): "This figure is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements."
- **Why:** '088' is the length you get with the test case value(s) Nonfuel Amount '90964076', Approval Number 'XQIKBJ'. With real values (amount 1 to 4 digits; Approval Number always 6 characters) the Segment Length can only be 081 to 084, so '088' can never be correct.
- **What it should be / action:** Recalculate the Segment Length after filling in the real values (for example Approved Amount 000001000 -> amount 1000 plus a 6-character Approval Number -> Segment Length 084).

## Values to fill in at run time

### 7. Nonfuel Amount holds instruction text, not a value - BLOCKED

- **Step:** 2
- **Where:** TD-TC-002038.step2.completion.json -> Standard Message Data Segment -> NonfuelAmount
- **Value:** `<NonfuelAmount from step 1 response>`
- **What ATL105 says:**
  - Section 10.1.2.1 Partial Approval Transaction, PDF p.84 (printed 10-5): "Approved Amount (Element No. 6) contains the approved partial amount, and the POS device must prompt for payment of the remaining balance due on the sale."
  - Element 6 Approved Amount (Chapter 13), PDF p.368 (printed 13-28): "Fixed length of nine digits with two assumed decimal places Zero-filled and right aligned"
- **Why:** The field holds the instruction text '<NonfuelAmount from step 1 response>' instead of a real Nonfuel Amount. BUYPASS cannot process this text, so the message cannot be sent until the real value from the step 1 response is filled in. Note: the step 1 response has no Nonfuel Amount field; the value comes from response Element 6 Approved Amount (9 digits, zero-filled), with the leading zeros removed.
- **What it should be / action:** Completion amount = the step 1 response Approved Amount without the leading zeros (for example 000001000 -> 1000).

### 8. Approval Number holds instruction text, not a value - BLOCKED

- **Step:** 2
- **Where:** TD-TC-002038.step2.completion.json -> Standard Message Data Segment -> ApprovalNumber
- **Value:** `<ApprovalNumber from step 1 response>`
- **What ATL105 says:**
  - Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization."
  - Appendix G. Valid Transaction Type Codes, PDF p.545 (printed Appendix G-1): "0 POS Purchase/Capture Point-of-sale purchase/preauthorized completion"
- **Why:** The field holds the instruction text '<ApprovalNumber from step 1 response>' instead of a real Approval Number. BUYPASS cannot process this text, so the message cannot be sent until the real value from the step 1 response is filled in.
- **What it should be / action:** Fill in at run time: Element No. 5 (Approval Number)-present in the original transaction-must be present and identical (runtime link to the original's response, never invented)..

## Items to confirm

### 9. The CA Public Key File Checksum is a sample value - REVIEW

- **Where:** EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request)
- **Value:** `2012091700000100000102171`
- **What ATL105 says:**
  - Element 187 CA Public Key File Checksum (Chapter 13), PDF p.477 (printed 13-137): "Once received, the checksum must be used for all subsequent EMV transaction requests from this device."
- **Why:** The format is right (25 digits), but the value must be the checksum received in the device's last CA Public Key File Load; the test case marks it as a configured test-data value.
- **What it should be / action:** Confirm the checksum of the host's current CA Public Key file before execution.

### 10. TVR says 'online PIN entered' on a transaction without online PIN - REVIEW

- **Where:** EMV Request Data Segment -> EMVChipData -> Tag 95 (every EMV request)
- **Value:** `95=8000048000, 9F34=5E0302`
- **What ATL105 says:**
  - Element 190 EMV Chip Data (Chapter 13), PDF p.478 (printed 13-138): "All data elements must contain the exact values that were present on the card to terminal interface."
- **Why:** TVR byte 3 (`04`) sets 'Online PIN entered' (EMV Book 3), but the CVM Results (Tag 9F34 = 5E0302) say signature and Encrypted PIN Block Data is empty. The TVR is a generated placeholder; it must match the CVM once the chip data comes from a card simulator.
- **What it should be / action:** Produce the TVR and the CVM Results together with a card simulator.

### 11. Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders - REVIEW

- **Where:** EMV Request Data Segment -> EMVChipData (every EMV request)
- **Value:** `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=E7C54C89, 95=8000048000`
- **What ATL105 says:**
  - Element 190 EMV Chip Data (Chapter 13), PDF p.478 (printed 13-138): "All data elements must contain the exact values that were present on the card to terminal interface."
- **Why:** ATL105 requires the exact values from the card-to-terminal interface; generated values cannot be authenticated by the issuer.
- **What it should be / action:** Produce the chip data with a card simulator / HSM before execution.

### 12. Contactless chip data reuses the contact tag set - REVIEW

- **Where:** EMV Request Data Segment -> EMVChipData (every EMV request)
- **Value:** `22 tags, CVM 9F34 = 5E0302`
- **What ATL105 says:**
  - Appendix R. EMV Chip Data Example, PDF p.697 (printed Appendix R-1): "The below table does not contain complete list of tags."
- **Why:** The contactless request carries the same tags as the contact requests and no contactless-specific tag (9F6E, 9F6D, 9F7C, 9F66). ATL105 Appendix R lists an example only, so this cannot be decided from the spec.
- **What it should be / action:** Confirm the contactless chip data with a MasterCard contactless (M/Chip) kernel trace.

### 13. The completion of an EMV authorization carries no chip data - REVIEW

- **Step:** 2
- **Where:** TD-TC-002038.step2.completion.json
- **Value:** `(absent)`
- **What ATL105 says:**
  - Section 10.14.2.4 Supported Transaction Types (EMV), PDF p.161 (printed 10-82): "EMV data is not required on Reversal transactions."
  - Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization."
- **Why:** ATL105 says EMV data is not required on reversal transactions, but it says nothing about completions. The completion is a preauthorized Purchase/Capture (it carries the authorization's Approval Number).
- **What it should be / action:** Confirm with BUYPASS whether a completion of an EMV authorization is sent without Segment 130.

### 14. Outdated page cited for the POS Entry Mode table (Table 005) - REVIEW

- **Where:** test case request -> Variable Information Data Segment -> Variable Information (source_page), every step
- **Value:** `p.548`
- **What ATL105 says:**
  - Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005), PDF p.555 (printed Appendix I-6): "Point-of-Service Entry Mode Information"
- **Why:** The test case cites p.548 for the POS Entry Mode table (Table 005). In ATL105 2026-4, PDF p.548 (printed Appendix H-2) does not contain it; it is on PDF p.555 (printed Appendix I-6). p.548 is where it was in the older 2026-3 edition.
- **What it should be / action:** Change the cited page from 548 to 555. The data values are not affected.

### 15. The Approval Number rule cites the wrong section - REVIEW

- **Step:** 2
- **Where:** test case step 2 -> link rule for Approval Number
- **Value:** `p.85`
- **What ATL105 says:**
  - Section 10.1.2.2 Authorization Only Reversal Transaction, PDF p.85 (printed 10-6): "Element No. 5 (Approval Number)—present in the original transaction—must be present and identical in the Authorization Only Reversal transaction."
  - Appendix G. Valid Transaction Type Codes, PDF p.545 (printed Appendix G-1): "0 POS Purchase/Capture Point-of-sale purchase/preauthorized completion"
  - Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization."
- **Why:** p.85 is Section 10.1.2.2 Authorization Only Reversal Transaction: it covers the Authorization Only Reversal (Transaction Type S), while this step is a preauthorized completion (Prompt Code 0020, Transaction Type 0).
- **What it should be / action:** Cite Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28) instead. The data value itself is correct.

### 16. The quote cited for p.511 is not word-for-word in ATL105 - REVIEW

- **Where:** test case spec_basis
- **Value:** `03 MasterCard - Authorizer Type: Credit`
- **What ATL105 says:**
  - Appendix C. Valid Authorizer Codes, PDF p.511 (printed Appendix C-1): "03 MasterCard® Credit"
- **Why:** The test case quotes "03 MasterCard - Authorizer Type: Credit" for p.511. Not word-for-word: all its words are on the cited page, but not in this form.
- **What it should be / action:** Quote the text exactly as it appears: "03 MasterCard® Credit".

### 17. The quote cited for p.161 is on the neighbouring page - REVIEW

- **Where:** test case spec_basis
- **Value:** `The following credit cards are supported for EMV processing: ... MasterCard`
- **What ATL105 says:**
  - (no specific ATL105 page)
- **Why:** The test case quotes "The following credit cards are supported for EMV processing: ... MasterCard" for p.161. Found on a neighbouring page, not the cited one (p.160).
- **What it should be / action:** Cite p.160 instead of p.161.

### 18. The Terminal Identifier is a placeholder - REVIEW

- **Where:** Standard Message Data Segment -> TerminalIdentifier (every step)
- **Value:** `JX72948216050257001`
- **What ATL105 says:**
  - Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."
  - Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code)."
  - Appendix D. Valid State Codes, PDF p.517 (printed Appendix D-4): "Yukon YT 72"
- **Why:** The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada.
- **What it should be / action:** Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location.

### 19. Step 1 needs a partial-approval test card - REVIEW

- **Step:** 1
- **Where:** test case step 1 expected Response Code
- **Value:** `F`
- **What ATL105 says:**
  - Section 10.1.2.1 Partial Approval Transaction, PDF p.84 (printed 10-5): "This information is returned when a Partial Approval Indicator value of 1 is sent in Partial Approval Indicator (Element No. 121)."
  - Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "F Approved—Partial approval"
  - Element 121 Partial Approval Indicator (Chapter 13), PDF p.440 (printed 13-100): "the network can approve an amount that is less than the amount in the Authorization request"
- **Why:** Step 1 expects Response Code F (partial approval). BUYPASS returns F only when Partial Approval Indicator 1 is sent and the network approves less than the $15.00 requested, so the test needs a card whose available balance is below $15.00.
- **What it should be / action:** Client to supply a partial-approval test card or simulator trigger (the test case is already 'needs_client_input').

## Notes for the message builder

### 20. Join Account Number and expiry with '=' in the wire message - INFO

- **Step:** 2
- **Where:** TD-TC-002038.step2.completion.json -> AccountNumber, CardDiscretionaryBlockData
- **Value:** `5161138166849671 / 3109`
- **What ATL105 says:**
  - Section 10.1.8.2 Track Support Requirements, PDF p.94 (printed 10-15): "The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM)."
- **Why:** The JSON keeps Account Number and the expiry as separate fields. In the real message for this follow-on transaction they are sent as Account Number, '=', YYMM - not separated by a Field Separator. The Segment Length is the same either way.
- **What it should be / action:** The message builder sends 5161138166849671=3109.

## Field-by-field check - step 1, auth only (`TD-TC-002038.step1.auth-only.json`)

| Segment | Field No. | Element | Field | Test data value | Test case value | ATL105 rule | Where in ATL105 | Result |
|---|---|---|---|---|---|---|---|---|
| Message | - | - | Message type | `EMV Financial Transaction Request` | `-` | Segment 130 is sent only in an EMV Financial Transaction Request | Section 12.20, PDF p.270 (printed 12-53); Section 11.8.1, PDF p.205 (printed 11-42) | PASS |
| Data Section 1 | 1 | 55 | Message Format Version Identifier | `ATL105` | `ATL105` | Fixed 6 characters: ATL105 | Section 11.1, PDF p.167 (printed 11-4); Element 55, PDF p.391 (printed 13-51) | PASS |
| Data Section 1 | 2 | 63 | Number of Segments | `03` | `-` | Up to 2 digits; equals the number of segments sent (3) | Section 11.1, PDF p.167 (printed 11-4); Element 63, PDF p.395 (printed 13-55) | PASS |
| 100 | 1 | 85 | Segment Type | `100` | `-` | Fixed value 100 | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 2 | 84 | Segment Length | `089` | `-` | Length of all 17 fields + 17 Field Separators = 089 | Section 12.1, PDF p.223 (printed 12-6); Element 84, PDF p.406 (printed 13-66) | PASS |
| 100 | 3 | 44 | Information Byte | `0` | `0` | 1 character: '0' = single-message transmission | Element 44, PDF p.386 (printed 13-46) | PASS |
| 100 | 4 | 102 | Terminal Identifier | `JX72948216050257001` | `JX72948216050257001` | Device Type 'JX' + State Code '72' = Yukon (YT), Canada + Merchant Number and Device Number '948216050257001' | Element 102, PDF p.417 (printed 13-77); Appendix D, PDF p.517 (printed D-4) | PASS |
| 100 | 5 | 78 | Prompt Code | `3020` | `3020` | Transaction Type '3' = POS Authorization Only; Card Type '020' = Credit | Element 78, PDF p.400 (printed 13-60); Appendix G, PDF p.545 (printed G-1); Appendix E, PDF p.521 (printed E-4) | PASS |
| 100 | 6 | 2 | Account Number | `5161138166849671` | `5161138166849671` | Up to 24 characters; valid check digit and MasterCard range 51-55 / 2221-2720 (card-industry rule) | Element 2, PDF p.364 (printed 13-24) | PASS |
| 100 | 7 | 12 | Card Discretionary Block Data | `310920162109438` | `310920162109438` | Track 2 data: expiry 3109 = September 2031; service code 201 (chip card, ISO/IEC 7813); Account Number + '=' + this field = 32 characters (at most 37) | Element 12, PDF p.371 (printed 13-31) | PASS |
| 100 | 8 | 33 | Encrypted PIN Block Data | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 9 | 79 | Pump/Lane Number | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 10 | 41 | Fuel Purchase Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 11 | 58 | Nonfuel Amount | `1500` | `1500` | Digits only, at most 7 for MasterCard (8 only for Amex): '1500' = $15.00 | Element 58, PDF p.393 (printed 13-53) | PASS |
| 100 | 12 | 99 | Tax Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 13 | 17 | Cash Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 14 | 86 | Sequence Number | `020381` | `020381` | 6 digits, 000001-999999 | Element 86, PDF p.409 (printed 13-69) | PASS |
| 100 | 15 | 5 | Approval Number | `(empty)` | `(not sent)` | Not sent on a new transaction | Element 5, PDF p.368 (printed 13-28) | PASS |
| 100 | 16 | 49 | Local Date and Local Time | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 17 | 121 | Partial Approval Indicator | `1` | `1` | '1' = partial approval supported | Element 121, PDF p.440 (printed 13-100) | PASS |
| 111 | 1 | 85 | Segment Type | `111` | `111` | Fixed value 111 | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 2 | 84 | Segment Length | `026` | `-` | 3 (type) + 3 (length) + 17 (tables) + 3 Field Separators = 026 | Section 12.10, PDF p.246 (printed 12-29); Element 84, PDF p.406 (printed 13-66) | PASS |
| 111 | 3 [1] | 111 | Variable Information Indicator | `005` | `-` | 3-character table ID (Table 005) | Element 111, PDF p.422 (printed 13-82) | PASS |
| 111 | 4 [1] | 112 | Variable Information Length | `003` | `-` | 3 digits = length of the table data (003) | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 5 [1] | 113 | Variable Information (Table 005) | `071` | `071` | '07' = contactless chip read; terminal capability '1' = PIN entry capability | Appendix I, PDF p.555 (printed I-6); Appendix J, PDF p.638 (printed J-1); Appendix J, PDF p.639 (printed J-2) | PASS |
| 111 | 3 [2] | 111 | Variable Information Indicator | `031` | `-` | 3-character table ID (Table 031) | Element 111, PDF p.422 (printed 13-82) | PASS |
| 111 | 4 [2] | 112 | Variable Information Length | `002` | `-` | 3 digits = length of the table data (002) | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 5 [2] | 113 | Variable Information (Table 031) | `12` | `12` | '12' = hybrid reader: contact chip and contactless chip; required on every transaction | Appendix I, PDF p.569 (printed I-20) | PASS |
| 130 | 1 | 85 | Segment Type | `130` | `130` | Fixed value 130 | Section 12.20, PDF p.270 (printed 12-53) | PASS |
| 130 | 2 | 84 | Segment Length | `1181` | `-` | 4 digits: Fields 1-6 + 6 Field Separators + EMV Additional Information Section + 1 Field Separator = 1181 (EMV Chip Data counted as 147 binary bytes) | Section 12.20, PDF p.270 (printed 12-53); Element 190, PDF p.478 (printed 13-138) | PASS |
| 130 | 3 | 187 | CA Public Key File Checksum | `2012091700000100000102171` | `2012091700000100000102171` | Numeric, fixed length 25; the checksum from the last CA Public Key File Load | Element 187, PDF p.476 (printed 13-136); Element 187, PDF p.477 (printed 13-137) | PASS |
| 130 | 4 | 188 | EMV Card Sequence Number | `01` | `01` | Fixed 3 bytes, right-aligned, zero-filled; Tag 5F34 = 01 -> '001' | Element 188, PDF p.477 (printed 13-137) | FAIL |
| 130 | 5 | 189 | EMV Chip Data Length | `147` | `147` | 3 digits = bytes of EMV Chip Data: 147 (2-byte BCD length + 145 bytes of TLV data) | Element 189, PDF p.477 (printed 13-137); Appendix R, PDF p.697 (printed R-1) | PASS |
| 130 | 6 | 190 | EMV Chip Data | `294 hex characters = 147 bytes, 22 tags` | `01455F2A0208405F... (294)` | 2-byte BCD length + TLV data; Tag 9F06 or 84 present; no Tag 5A or 57 | Element 190, PDF p.478 (printed 13-138); Appendix R, PDF p.697 (printed R-1) | PASS |
| 130 | 6 / 9F02 | 190 | Tag 9F02 Amount, Authorized | `000000001500` | `-` | = Nonfuel Amount 1500 as 12 digits | Appendix R, PDF p.697 (printed R-1); Element 190, PDF p.478 (printed 13-138) | PASS |
| 130 | 6 / 9C | 190 | Tag 9C Transaction Type | `00` | `-` | 00 = purchase of goods/services (Transaction Type 3; EMV Book 3) | Appendix R, PDF p.697 (printed R-1); Element 190, PDF p.478 (printed 13-138) | PASS |
| 130 | 6 / 5F2A | 190 | Tag 5F2A Transaction Currency Code | `0840` | `-` | 0840 = United States (Table 006 default 840) | Appendix I, PDF p.555 (printed I-6) | PASS |
| 130 | 6 / 9F1A | 190 | Tag 9F1A Terminal Country Code | `0840` | `-` | 0840 = United States (Table 006 default 840) | Appendix I, PDF p.555 (printed I-6) | PASS |
| 130 | 6 / 9F27 | 190 | Tag 9F27 Cryptogram Information Data | `80` | `-` | ARQC (go online) (EMV Book 3) | Element 190, PDF p.478 (printed 13-138) | PASS |
| 130 | 6 / 9F34 | 190 | Tag 9F34 CVM Results | `5E0302` | `-` | CVM code 1E = signature (EMV Book 3) | Element 190, PDF p.478 (printed 13-138) | PASS |
| 130 | 7 | 191 | EMV Additional Information Indicator | `001` | `001` | 001 = EMV table data | Element 191, PDF p.479 (printed 13-139) | PASS |
| 130 | 8 | 192 | EMV Additional Information Length | `984` | `984` | 3 digits, 001-985, = length of Element 118 (984) | Element 192, PDF p.480 (printed 13-140) | PASS |
| 130 | 9 | 118 | EMV Additional Information | `7LB8CEDF37JK57D9CATB...B5SWLWMF (984 characters)` | `7LB8CEDF37JK57D9... (984)` | Indicator 001 = EMV Table Data: EMVYES or EMVNOT (6 characters) | Element 118, PDF p.439 (printed 13-99); Appendix T, PDF p.701 (printed T-1) | FAIL |

### EMV Chip Data (Element 190) decoded - step 1

Decoded from the hexadecimal test data (2-byte BCD length first, Appendix R). Tag names follow EMV Book 3; Appendix R shows an example tag set only.

| Tag | Name | Length (bytes) | Value | In the Appendix R example |
|---|---|---|---|---|
| 5F2A | Transaction Currency Code | 2 | `0840` | yes |
| 5F34 | PAN Sequence Number | 1 | `01` | no |
| 82 | Application Interchange Profile | 2 | `5800` | yes |
| 84 | Dedicated File (DF) Name | 7 | `A0000000041010` | yes |
| 95 | Terminal Verification Results | 5 | `8000048000` | yes |
| 9A | Transaction Date | 3 | `260101` | yes |
| 9C | Transaction Type | 1 | `00` | yes |
| 9F02 | Amount, Authorized | 6 | `000000001500` | yes |
| 9F03 | Amount, Other | 6 | `000000000000` | no |
| 9F06 | Application Identifier (AID) | 7 | `A0000000041010` | yes |
| 9F10 | Issuer Application Data | 8 | `010103A40000DAC0` | yes |
| 9F1A | Terminal Country Code | 2 | `0840` | yes |
| 9F1E | Interface Device Serial Number | 8 | `3132333435363738` | no |
| 9F26 | Application Cryptogram | 8 | `B4302C8AE8B7E91C` | yes |
| 9F27 | Cryptogram Information Data | 1 | `80` | yes |
| 9F33 | Terminal Capabilities | 3 | `E0F0C8` | yes |
| 9F34 | CVM Results | 3 | `5E0302` | yes |
| 9F35 | Terminal Type | 1 | `22` | no |
| 9F36 | Application Transaction Counter | 2 | `01D0` | yes |
| 9F37 | Unpredictable Number | 4 | `E7C54C89` | yes |
| 9F41 | Transaction Sequence Counter | 3 | `012850` | no |
| 9F53 | Transaction Category Code | 1 | `52` | no |

## Field-by-field check - step 2, completion (`TD-TC-002038.step2.completion.json`)

| Segment | Field No. | Element | Field | Test data value | Test case value | ATL105 rule | Where in ATL105 | Result |
|---|---|---|---|---|---|---|---|---|
| Message | - | - | Message type | `Financial Transaction Request` | `-` | Completion of an EMV authorization, sent without Segment 130 | Section 10.14.2.4, PDF p.161 (printed 10-82); Element 5, PDF p.368 (printed 13-28) | REVIEW |
| Data Section 1 | 1 | 55 | Message Format Version Identifier | `ATL105` | `ATL105` | Fixed 6 characters: ATL105 | Section 11.1, PDF p.167 (printed 11-4); Element 55, PDF p.391 (printed 13-51) | PASS |
| Data Section 1 | 2 | 63 | Number of Segments | `02` | `-` | Up to 2 digits; equals the number of segments sent (2) | Section 11.1, PDF p.167 (printed 11-4); Element 63, PDF p.395 (printed 13-55) | PASS |
| 100 | 1 | 85 | Segment Type | `100` | `-` | Fixed value 100 | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 2 | 84 | Segment Length | `088` | `-` | Length of all 17 fields + 17 Field Separators = 088 (using the test case value for Nonfuel Amount, Approval Number) | Section 12.1, PDF p.223 (printed 12-6); Element 84, PDF p.406 (printed 13-66) | FAIL |
| 100 | 3 | 44 | Information Byte | `0` | `0` | 1 character: '0' = single-message transmission | Element 44, PDF p.386 (printed 13-46) | PASS |
| 100 | 4 | 102 | Terminal Identifier | `JX72948216050257001` | `JX72948216050257001` | Device Type 'JX' + State Code '72' = Yukon (YT), Canada + Merchant Number and Device Number '948216050257001' | Element 102, PDF p.417 (printed 13-77); Appendix D, PDF p.517 (printed D-4) | PASS |
| 100 | 5 | 78 | Prompt Code | `0020` | `0020` | Transaction Type '0' = POS Purchase/Capture; Card Type '020' = Credit | Element 78, PDF p.400 (printed 13-60); Appendix G, PDF p.545 (printed G-1); Appendix E, PDF p.521 (printed E-4) | PASS |
| 100 | 6 | 2 | Account Number | `5161138166849671` | `5161138166849671` | Up to 24 characters; valid check digit and MasterCard range 51-55 / 2221-2720 (card-industry rule) | Element 2, PDF p.364 (printed 13-24) | PASS |
| 100 | 7 | 12 | Card Discretionary Block Data | `3109` | `3109` | Follow-on transaction: expiry as YYMM only (original track data 310920162109438 -> 3109) | Section 10.1.8.2, PDF p.94 (printed 10-15); Section 10.1.1.2, PDF p.81 (printed 10-2) | PASS |
| 100 | 8 | 33 | Encrypted PIN Block Data | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 9 | 79 | Pump/Lane Number | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 10 | 41 | Fuel Purchase Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 11 | 58 | Nonfuel Amount | `<NonfuelAmount from step 1 response>` | `90964076` | Filled in at run time: Amount = the Approved Amount (Element 6) returned in the original's partial approval response (p.84); runtime link. | Section 10.1.2.1, PDF p.84 (printed 10-5); Element 6, PDF p.368 (printed 13-28) | BLOCKED |
| 100 | 12 | 99 | Tax Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 13 | 17 | Cash Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 14 | 86 | Sequence Number | `020381` | `020381` | 6 digits, 000001-999999 | Element 86, PDF p.409 (printed 13-69) | PASS |
| 100 | 15 | 5 | Approval Number | `<ApprovalNumber from step 1 response>` | `XQIKBJ` | Filled in at run time: Element No. 5 (Approval Number)-present in the original transaction-must be present and identical (runtime link to the original's response, never invented). | Element 5, PDF p.368 (printed 13-28); Appendix G, PDF p.545 (printed G-1) | BLOCKED |
| 100 | 16 | 49 | Local Date and Local Time | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 17 | 121 | Partial Approval Indicator | `1` | `1` | '1' = partial approval supported | Element 121, PDF p.440 (printed 13-100) | PASS |
| 111 | 1 | 85 | Segment Type | `111` | `111` | Fixed value 111 | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 2 | 84 | Segment Length | `026` | `-` | 3 (type) + 3 (length) + 17 (tables) + 3 Field Separators = 026 | Section 12.10, PDF p.246 (printed 12-29); Element 84, PDF p.406 (printed 13-66) | PASS |
| 111 | 3 [1] | 111 | Variable Information Indicator | `005` | `-` | 3-character table ID (Table 005) | Element 111, PDF p.422 (printed 13-82) | PASS |
| 111 | 4 [1] | 112 | Variable Information Length | `003` | `-` | 3 digits = length of the table data (003) | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 5 [1] | 113 | Variable Information (Table 005) | `071` | `071` | '07' = contactless chip read; terminal capability '1' = PIN entry capability | Appendix I, PDF p.555 (printed I-6); Appendix J, PDF p.638 (printed J-1); Appendix J, PDF p.639 (printed J-2) | PASS |
| 111 | 3 [2] | 111 | Variable Information Indicator | `031` | `-` | 3-character table ID (Table 031) | Element 111, PDF p.422 (printed 13-82) | PASS |
| 111 | 4 [2] | 112 | Variable Information Length | `002` | `-` | 3 digits = length of the table data (002) | Section 12.10, PDF p.246 (printed 12-29) | PASS |
| 111 | 5 [2] | 113 | Variable Information (Table 031) | `12` | `12` | '12' = hybrid reader: contact chip and contactless chip; required on every transaction | Appendix I, PDF p.569 (printed I-20) | PASS |

## Step-to-step checks

| Step | Check | This step | Original step | Where in ATL105 | Result |
|---|---|---|---|---|---|
| 2 | Same Sequence Number as the original | `020381` | `020381` | Element 86, PDF p.409 (printed 13-69) | PASS |
| 2 | Same Account Number as the original | `5161138166849671` | `5161138166849671` | Section 2.3.1, PDF p.40 (printed 2-5) | PASS |
| 2 | Same terminal as the original | `JX72948216050257001` | `JX72948216050257001` | Section 2.3.1, PDF p.40 (printed 2-5) | PASS |
| 2 | Same POS entry mode as the original | `07` | `07` | Appendix J, PDF p.638 (printed J-1) | PASS |

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
| PDF p.85 (printed 10-6) | All approved and partially approved authorization transactions must be either cleared or reversed. | Found word-for-word on the cited page | PASS |
| PDF p.511 (printed Appendix C-1) | 03 MasterCard - Authorizer Type: Credit | Not word-for-word: all its words are on the cited page, but not in this form | REVIEW |
| PDF p.83 (printed 10-4) | The following point-of-sale transactions are supported for credit card processing: ... Authorization Only | Found word-for-word on the cited page | PASS |
| PDF p.161 (printed 10-82) | The following credit cards are supported for EMV processing: ... MasterCard | Found on a neighbouring page, not the cited one (p.160) | REVIEW |

