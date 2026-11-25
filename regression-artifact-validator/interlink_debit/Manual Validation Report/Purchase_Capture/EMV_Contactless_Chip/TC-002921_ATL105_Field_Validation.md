# TC-002921 (BR1_TS2_TC13) - ATL105 Test Data Validation

**BUYPASS ATL105 Interlink Debit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Purchase timed out → TOR sent ahead of next Purchase**

| Item | Value |
|---|---|
| Result | **FAIL** |
| Can it run as-is? | No - fix the FAIL items first |
| Test data | `interlink_debit/test_data/TD-TC-002921.step1.purchase.json`, `interlink_debit/test_data/TD-TC-002921.step2.timeout-reversal.json`, `interlink_debit/test_data/TD-TC-002921.step3.next-transaction.json` |
| Template / status | `T6-PUR-TOR-NEXT` / `needs_client_input` |
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
- **Where:** TD-TC-002921.step1.purchase.json -> Standard Message Data Segment -> AccountNumber, CardDiscretionaryBlockData
- **Value:** `(empty)`
- **What ATL105 says:**
  - Element 2 Account Number (Chapter 13), PDF p.364 (printed 13-24): "a variable length (up to 24 alphanumeric bytes) Account Number"
  - Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "If the Account Number is obtained from a Track 2 card reader, instead of being manually keyed, the Account Number and Card Discretionary Block Data should consist of all data read from the card between, but not including, the Start Sentinel and End Sentinel."
  - Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number is considered the data before the = character, and Card Discretionary Block Data is considered the data after the = character."
- **Why:** This purchase must carry the data read from the card (entry mode 07 = contactless chip read), but Account Number and Card Discretionary Block Data are empty, so BUYPASS cannot identify the card. The test case marks the Account Number AWAITING_CLIENT_VALUE: the Interlink Debit card profile was never supplied, and the generator wrote an empty field instead of a value or an explicit placeholder. The Card Discretionary Block Data is marked as an omitted conditional field, although it is needed here.
- **What it should be / action:** Fill in the data read from the card (entry mode 07 = contactless chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field.

### 2. The debit PIN block (Element 33) is empty - FAIL

- **Step:** 1
- **Where:** TD-TC-002921.step1.purchase.json -> Standard Message Data Segment -> EncryptedPINBlockData
- **Value:** `(empty)`
- **What ATL105 says:**
  - Section 10.2.1.3 PIN Entry, PDF p.96 (printed 10-17): "All transactions—except the Purchase Reversal transaction—require PIN entry."
  - Section 10.2.1.3 PIN Entry, PDF p.96 (printed 10-17): "When processing a debit card transaction, the entry of the customer PIN is required on a DES-compliant entry device, and it must be encrypted using the DUKPT PIN encryption method."
  - Element 33 Encrypted PIN Block Data (Chapter 13), PDF p.382 (printed 13-42): "Required for debit and EBT."
  - Element 33 Encrypted PIN Block Data (Chapter 13), PDF p.382 (printed 13-42): "For DUKPT PIN encryption, this is a 16- to 20-byte Key Serial Number (KSN) plus a 16-byte PIN block."
- **Why:** ATL105 requires PIN entry, DUKPT-encrypted, on every debit transaction except the Purchase Reversal, and Element 33 is required for debit. This purchase (Transaction Type 0) sends no PIN block, although its chip data reports an online PIN (Tag 9F34 = 420300).
- **What it should be / action:** Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input).

### 3. Table 030 (POS Condition Code) is missing - FAIL

- **Step:** 1
- **Where:** TD-TC-002921.step1.purchase.json -> Variable Information Data Segment
- **Value:** `tables sent: 005, 031`
- **What ATL105 says:**
  - Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030), PDF p.568 (printed Appendix I-19): "Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions."
- **Why:** This step is an EMV transaction (Segment 130 is sent). Table 030 identifies the device class, presentation data, security condition and terminal type, and ATL105 requires it for all EMV and all Credential on File transactions.
- **What it should be / action:** Add Table 030 (10 digits, values from Appendix Q) for the certified terminal.

### 4. EMV Card Sequence Number (Element 188) is 2 bytes instead of 3 - FAIL

- **Step:** 1
- **Where:** TD-TC-002921.step1.purchase.json -> EMV Request Data Segment -> EMVCardSequenceNumber
- **Value:** `01`
- **What ATL105 says:**
  - Element 188 EMV Card Sequence Number (Chapter 13), PDF p.477 (printed 13-137): "Fixed length of three bytes, right-aligned and padded with zeroes"
  - Element 188 EMV Card Sequence Number (Chapter 13), PDF p.477 (printed 13-137): "Valid Codes/Values: 000—099 EMV Card Sequence Number is supplied by the EMV chip card"
- **Why:** Element 188 is fixed at 3 bytes, right-aligned and zero-filled. The chip data carries Tag 5F34 = '01', so the card sequence number itself is right, but '01' is 2 bytes; it must be '001'. The declared Segment Length (1181) was calculated with the 2-byte value and changes by +1 when corrected.
- **What it should be / action:** '001' (then recalculate the Segment 130 length)

### 5. EMV Additional Information (Element 118) does not contain EMV Table Data - FAIL

- **Step:** 1
- **Where:** TD-TC-002921.step1.purchase.json -> EMV Request Data Segment -> EMVAdditionalInformation
- **Value:** `ZXD68Z9J5SX8KVZKD3WX...AC69VSDI (984 characters)`
- **What ATL105 says:**
  - Element 118 EMV Additional Information (Chapter 13), PDF p.439 (printed 13-99): "When EMV Additional Information Indicator has a value of 001, EMV table data is included here."
  - Appendix T. EMV Additional Information Data Layouts - EMV Table Data, PDF p.701 (printed Appendix T-1): "EMVYES This code indicates that the device is able to receive EMV Table Data."
  - Appendix T. EMV Additional Information Data Layouts - EMV Table Data, PDF p.701 (printed Appendix T-1): "EMVNOT This code indicates that the device is unable to receive EMV Table Data."
  - Appendix T. EMV Additional Information Data Layouts - EMV Table Data, PDF p.701 (printed Appendix T-1): "Maximum Length n6 Len.: 6"
- **Why:** Indicator 001 means EMV Table Data, and Appendix T allows only EMVYES or EMVNOT (6 characters). The field holds 984 generated characters. Element 192 = '984' matches that length, so it is consistent with the content, but the content itself is not allowed for indicator 001.
- **What it should be / action:** EMVYES (device can receive EMV Table Data) or EMVNOT, with Element 192 = 006 (see the open question on framing); the Segment 130 length then becomes 0204 together with the Element 188 fix.

### 6. The card data is empty (Account Number and Card Discretionary Block Data) - FAIL

- **Step:** 2
- **Where:** TD-TC-002921.step2.timeout-reversal.json -> Standard Message Data Segment -> AccountNumber, CardDiscretionaryBlockData
- **Value:** `(empty)`
- **What ATL105 says:**
  - Element 2 Account Number (Chapter 13), PDF p.364 (printed 13-24): "a variable length (up to 24 alphanumeric bytes) Account Number"
  - Section 10.2.5.2 Track Support Requirements, PDF p.105 (printed 10-26): "The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM)."
- **Why:** This timeout reversal must carry the Account Number and the expiry as YYMM (follow-on transaction), but Account Number and Card Discretionary Block Data are empty, so BUYPASS cannot identify the card. The test case marks the Account Number AWAITING_CLIENT_VALUE: the Interlink Debit card profile was never supplied, and the generator wrote an empty field instead of a value or an explicit placeholder. The Card Discretionary Block Data is marked as an omitted conditional field, although it is needed here.
- **What it should be / action:** Fill in the Account Number and the expiry as YYMM (follow-on transaction) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field.

### 7. The card data is empty (Account Number and Card Discretionary Block Data) - FAIL

- **Step:** 3
- **Where:** TD-TC-002921.step3.next-transaction.json -> Standard Message Data Segment -> AccountNumber, CardDiscretionaryBlockData
- **Value:** `(empty)`
- **What ATL105 says:**
  - Element 2 Account Number (Chapter 13), PDF p.364 (printed 13-24): "a variable length (up to 24 alphanumeric bytes) Account Number"
  - Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "If the Account Number is obtained from a Track 2 card reader, instead of being manually keyed, the Account Number and Card Discretionary Block Data should consist of all data read from the card between, but not including, the Start Sentinel and End Sentinel."
  - Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number is considered the data before the = character, and Card Discretionary Block Data is considered the data after the = character."
- **Why:** This next transaction must carry the data read from the card (entry mode 07 = contactless chip read), but Account Number and Card Discretionary Block Data are empty, so BUYPASS cannot identify the card. The test case marks the Account Number AWAITING_CLIENT_VALUE: the Interlink Debit card profile was never supplied, and the generator wrote an empty field instead of a value or an explicit placeholder. The Card Discretionary Block Data is marked as an omitted conditional field, although it is needed here.
- **What it should be / action:** Fill in the data read from the card (entry mode 07 = contactless chip read) from the client's Interlink Debit test card. Until the card profile is supplied, keep the test case blocked and write an explicit placeholder instead of an empty field.

### 8. The debit PIN block (Element 33) is empty - FAIL

- **Step:** 3
- **Where:** TD-TC-002921.step3.next-transaction.json -> Standard Message Data Segment -> EncryptedPINBlockData
- **Value:** `(empty)`
- **What ATL105 says:**
  - Section 10.2.1.3 PIN Entry, PDF p.96 (printed 10-17): "All transactions—except the Purchase Reversal transaction—require PIN entry."
  - Section 10.2.1.3 PIN Entry, PDF p.96 (printed 10-17): "When processing a debit card transaction, the entry of the customer PIN is required on a DES-compliant entry device, and it must be encrypted using the DUKPT PIN encryption method."
  - Element 33 Encrypted PIN Block Data (Chapter 13), PDF p.382 (printed 13-42): "Required for debit and EBT."
  - Element 33 Encrypted PIN Block Data (Chapter 13), PDF p.382 (printed 13-42): "For DUKPT PIN encryption, this is a 16- to 20-byte Key Serial Number (KSN) plus a 16-byte PIN block."
- **Why:** ATL105 requires PIN entry, DUKPT-encrypted, on every debit transaction except the Purchase Reversal, and Element 33 is required for debit. This next transaction (Transaction Type 0) sends no PIN block, although its chip data reports an online PIN (Tag 9F34 = 420300).
- **What it should be / action:** Element 33 = DUKPT Key Serial Number + PIN block from the client's test PIN and DUKPT key; until they are supplied, mark the test case blocked (needs client input).

### 9. Table 030 (POS Condition Code) is missing - FAIL

- **Step:** 3
- **Where:** TD-TC-002921.step3.next-transaction.json -> Variable Information Data Segment
- **Value:** `tables sent: 005, 031`
- **What ATL105 says:**
  - Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030), PDF p.568 (printed Appendix I-19): "Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions."
- **Why:** This step is an EMV transaction (Segment 130 is sent). Table 030 identifies the device class, presentation data, security condition and terminal type, and ATL105 requires it for all EMV and all Credential on File transactions.
- **What it should be / action:** Add Table 030 (10 digits, values from Appendix Q) for the certified terminal.

### 10. EMV Card Sequence Number (Element 188) is 2 bytes instead of 3 - FAIL

- **Step:** 3
- **Where:** TD-TC-002921.step3.next-transaction.json -> EMV Request Data Segment -> EMVCardSequenceNumber
- **Value:** `01`
- **What ATL105 says:**
  - Element 188 EMV Card Sequence Number (Chapter 13), PDF p.477 (printed 13-137): "Fixed length of three bytes, right-aligned and padded with zeroes"
  - Element 188 EMV Card Sequence Number (Chapter 13), PDF p.477 (printed 13-137): "Valid Codes/Values: 000—099 EMV Card Sequence Number is supplied by the EMV chip card"
- **Why:** Element 188 is fixed at 3 bytes, right-aligned and zero-filled. The chip data carries Tag 5F34 = '01', so the card sequence number itself is right, but '01' is 2 bytes; it must be '001'. The declared Segment Length (1181) was calculated with the 2-byte value and changes by +1 when corrected.
- **What it should be / action:** '001' (then recalculate the Segment 130 length)

### 11. EMV Additional Information (Element 118) does not contain EMV Table Data - FAIL

- **Step:** 3
- **Where:** TD-TC-002921.step3.next-transaction.json -> EMV Request Data Segment -> EMVAdditionalInformation
- **Value:** `AX5K3PHZE31OPT7H75RT...Q8QU1GQO (984 characters)`
- **What ATL105 says:**
  - Element 118 EMV Additional Information (Chapter 13), PDF p.439 (printed 13-99): "When EMV Additional Information Indicator has a value of 001, EMV table data is included here."
  - Appendix T. EMV Additional Information Data Layouts - EMV Table Data, PDF p.701 (printed Appendix T-1): "EMVYES This code indicates that the device is able to receive EMV Table Data."
  - Appendix T. EMV Additional Information Data Layouts - EMV Table Data, PDF p.701 (printed Appendix T-1): "EMVNOT This code indicates that the device is unable to receive EMV Table Data."
  - Appendix T. EMV Additional Information Data Layouts - EMV Table Data, PDF p.701 (printed Appendix T-1): "Maximum Length n6 Len.: 6"
- **Why:** Indicator 001 means EMV Table Data, and Appendix T allows only EMVYES or EMVNOT (6 characters). The field holds 984 generated characters. Element 192 = '984' matches that length, so it is consistent with the content, but the content itself is not allowed for indicator 001.
- **What it should be / action:** EMVYES (device can receive EMV Table Data) or EMVNOT, with Element 192 = 006 (see the open question on framing); the Segment 130 length then becomes 0204 together with the Element 188 fix.

## Items to confirm

### 12. The CA Public Key File Checksum is a sample value - REVIEW

- **Where:** EMV Request Data Segment -> CAPublicKeyFileChecksum (every EMV request)
- **Value:** `2012091700000100000102171`
- **What ATL105 says:**
  - Element 187 CA Public Key File Checksum (Chapter 13), PDF p.477 (printed 13-137): "Once received, the checksum must be used for all subsequent EMV transaction requests from this device."
- **Why:** The format is right (25 digits), but the value must be the checksum received in the device's last CA Public Key File Load; the test case marks it as a configured test-data value.
- **What it should be / action:** Confirm the checksum of the host's current CA Public Key file before execution.

### 13. Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders - REVIEW

- **Where:** EMV Request Data Segment -> EMVChipData (every EMV request)
- **Value:** `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=9879990F, 95=8000048000`
- **What ATL105 says:**
  - Element 190 EMV Chip Data (Chapter 13), PDF p.478 (printed 13-138): "All data elements must contain the exact values that were present on the card to terminal interface."
- **Why:** ATL105 requires the exact values from the card-to-terminal interface; generated values cannot be authenticated by the issuer.
- **What it should be / action:** Produce the chip data with a card simulator / HSM before execution.

### 14. Contactless chip data reuses the contact tag set - REVIEW

- **Where:** EMV Request Data Segment -> EMVChipData (every EMV request)
- **Value:** `22 tags, CVM 9F34 = 420300`
- **What ATL105 says:**
  - Appendix R. EMV Chip Data Example, PDF p.697 (printed Appendix R-1): "The below table does not contain complete list of tags."
- **Why:** The contactless request carries the same tags as the contact requests and no contactless-specific tag (9F6E, 9F6D, 9F7C, 9F66). ATL105 Appendix R lists an example only, so this cannot be decided from the spec.
- **What it should be / action:** Confirm the contactless chip data with a MasterCard contactless (M/Chip) kernel trace.

### 15. Outdated page cited for the POS Entry Mode table (Table 005) - REVIEW

- **Where:** test case request -> Variable Information Data Segment -> Variable Information (source_page), every step
- **Value:** `p.548`
- **What ATL105 says:**
  - Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005), PDF p.555 (printed Appendix I-6): "Point-of-Service Entry Mode Information"
- **Why:** The test case cites p.548 for the POS Entry Mode table (Table 005). In ATL105 2026-4, PDF p.548 (printed Appendix H-2) does not contain it; it is on PDF p.555 (printed Appendix I-6). p.548 is where it was in the older 2026-3 edition.
- **What it should be / action:** Change the cited page from 548 to 555. The data values are not affected.

### 16. The quote cited for p.512 is not word-for-word in ATL105 - REVIEW

- **Where:** test case spec_basis
- **Value:** `48 Interlink - Authorizer Type: Debit`
- **What ATL105 says:**
  - (no specific ATL105 page)
- **Why:** The test case quotes "48 Interlink - Authorizer Type: Debit" for p.512. Not word-for-word: all its words are on the cited page, but not in this form.
- **What it should be / action:** Quote the text exactly as it appears on the page.

### 17. The Terminal Identifier is a placeholder - REVIEW

- **Where:** Standard Message Data Segment -> TerminalIdentifier (every step)
- **Value:** `JX72948216050257001`
- **What ATL105 says:**
  - Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."
  - Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code)."
  - Appendix D. Valid State Codes, PDF p.517 (printed Appendix D-4): "Yukon YT 72"
- **Why:** The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada.
- **What it should be / action:** Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location.

## Field-by-field check - step 1, purchase (`TD-TC-002921.step1.purchase.json`)

| Segment | Field No. | Element | Field | Test data value | Test case value | ATL105 rule | Where in ATL105 | Result |
|---|---|---|---|---|---|---|---|---|
| Message | - | - | Message type | `EMV Financial Transaction Request` | `-` | Segment 130 is sent only in an EMV Financial Transaction Request | Section 12.20, PDF p.270 (printed 12-53); Section 11.8.1, PDF p.205 (printed 11-42) | PASS |
| Data Section 1 | 1 | 55 | Message Format Version Identifier | `ATL105` | `ATL105` | Fixed 6 characters: ATL105 | Section 11.1, PDF p.167 (printed 11-4); Element 55, PDF p.391 (printed 13-51) | PASS |
| Data Section 1 | 2 | 63 | Number of Segments | `03` | `-` | Up to 2 digits; equals the number of segments sent (3) | Section 11.1, PDF p.167 (printed 11-4); Element 63, PDF p.395 (printed 13-55) | PASS |
| 100 | 1 | 85 | Segment Type | `100` | `-` | Fixed value 100 | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 2 | 84 | Segment Length | `058` | `-` | Length of all 17 fields + 17 Field Separators = 058 | Section 12.1, PDF p.223 (printed 12-6); Element 84, PDF p.406 (printed 13-66) | PASS |
| 100 | 3 | 44 | Information Byte | `0` | `0` | 1 character: '0' = single-message transmission | Element 44, PDF p.386 (printed 13-46) | PASS |
| 100 | 4 | 102 | Terminal Identifier | `JX72948216050257001` | `JX72948216050257001` | Device Type 'JX' + State Code '72' = Yukon (YT), Canada + Merchant Number and Device Number '948216050257001' | Element 102, PDF p.417 (printed 13-77); Appendix D, PDF p.517 (printed D-4) | PASS |
| 100 | 5 | 78 | Prompt Code | `0011` | `0011` | Transaction Type '0' = POS Purchase/Capture; Card Type '011' = Debit checking | Element 78, PDF p.400 (printed 13-60); Appendix G, PDF p.545 (printed G-1); Appendix E, PDF p.521 (printed E-4) | PASS |
| 100 | 6 | 2 | Account Number | `(empty)` | `(not sent)` | Required: the data read from the card (entry mode 07 = contactless chip read) | Element 2, PDF p.364 (printed 13-24); Element 12, PDF p.371 (printed 13-31) | FAIL |
| 100 | 7 | 12 | Card Discretionary Block Data | `(empty)` | `(not sent)` | Required with the Account Number (see the card data finding) | Element 12, PDF p.371 (printed 13-31) | FAIL |
| 100 | 8 | 33 | Encrypted PIN Block Data | `(empty)` | `(not sent)` | Required for debit: DUKPT Key Serial Number + PIN block | Section 10.2.1.3, PDF p.96 (printed 10-17); Element 33, PDF p.382 (printed 13-42) | FAIL |
| 100 | 9 | 79 | Pump/Lane Number | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 10 | 41 | Fuel Purchase Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 11 | 58 | Nonfuel Amount | `1500` | `1500` | Digits only, at most 7 for Interlink (8 only for Amex): '1500' = $15.00 | Element 58, PDF p.393 (printed 13-53) | PASS |
| 100 | 12 | 99 | Tax Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 13 | 17 | Cash Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 14 | 86 | Sequence Number | `029211` | `029211` | 6 digits, 000001-999999 | Element 86, PDF p.409 (printed 13-69) | PASS |
| 100 | 15 | 5 | Approval Number | `(empty)` | `(not sent)` | Not sent on a purchase (it would make the purchase preauthorized) | Element 5, PDF p.368 (printed 13-28) | PASS |
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
| 130 | 6 / 9F03 | 190 | Tag 9F03 Amount, Other | `000000000000` | `-` | No cash back, so zero (EMV Book 3) | Element 17, PDF p.375 (printed 13-35); Element 190, PDF p.478 (printed 13-138) | PASS |
| 130 | 6 / 9C | 190 | Tag 9C Transaction Type | `00` | `-` | 00 = purchase of goods/services (Transaction Type 0; EMV Book 3) | Appendix R, PDF p.697 (printed R-1); Element 190, PDF p.478 (printed 13-138) | PASS |
| 130 | 6 / 5F2A | 190 | Tag 5F2A Transaction Currency Code | `0840` | `-` | 0840 = United States (Table 006 default 840) | Appendix I, PDF p.555 (printed I-6) | PASS |
| 130 | 6 / 9F1A | 190 | Tag 9F1A Terminal Country Code | `0840` | `-` | 0840 = United States (Table 006 default 840) | Appendix I, PDF p.555 (printed I-6) | PASS |
| 130 | 6 / 9F27 | 190 | Tag 9F27 Cryptogram Information Data | `80` | `-` | ARQC (go online) (EMV Book 3) | Element 190, PDF p.478 (printed 13-138) | PASS |
| 130 | 6 / 9F34 | 190 | Tag 9F34 CVM Results | `420300` | `-` | CVM code 02 = enciphered PIN verified online (EMV Book 3) | Element 190, PDF p.478 (printed 13-138) | PASS |
| 130 | 7 | 191 | EMV Additional Information Indicator | `001` | `001` | 001 = EMV table data | Element 191, PDF p.479 (printed 13-139) | PASS |
| 130 | 8 | 192 | EMV Additional Information Length | `984` | `984` | 3 digits, 001-985, = length of Element 118 (984) | Element 192, PDF p.480 (printed 13-140) | PASS |
| 130 | 9 | 118 | EMV Additional Information | `ZXD68Z9J5SX8KVZKD3WX...AC69VSDI (984 characters)` | `ZXD68Z9J5SX8KVZK... (984)` | Indicator 001 = EMV Table Data: EMVYES or EMVNOT (6 characters) | Element 118, PDF p.439 (printed 13-99); Appendix T, PDF p.701 (printed T-1) | FAIL |

### EMV Chip Data (Element 190) decoded - step 1

Decoded from the hexadecimal test data (2-byte BCD length first, Appendix R). Tag names follow EMV Book 3; Appendix R shows an example tag set only.

| Tag | Name | Length (bytes) | Value | In the Appendix R example |
|---|---|---|---|---|
| 5F2A | Transaction Currency Code | 2 | `0840` | yes |
| 5F34 | PAN Sequence Number | 1 | `01` | no |
| 82 | Application Interchange Profile | 2 | `5800` | yes |
| 84 | Dedicated File (DF) Name | 7 | `A0000000033010` | yes |
| 95 | Terminal Verification Results | 5 | `8000048000` | yes |
| 9A | Transaction Date | 3 | `260101` | yes |
| 9C | Transaction Type | 1 | `00` | yes |
| 9F02 | Amount, Authorized | 6 | `000000001500` | yes |
| 9F03 | Amount, Other | 6 | `000000000000` | no |
| 9F06 | Application Identifier (AID) | 7 | `A0000000033010` | yes |
| 9F10 | Issuer Application Data | 8 | `010103A40000DAC0` | yes |
| 9F1A | Terminal Country Code | 2 | `0840` | yes |
| 9F1E | Interface Device Serial Number | 8 | `3132333435363738` | no |
| 9F26 | Application Cryptogram | 8 | `B4302C8AE8B7E91C` | yes |
| 9F27 | Cryptogram Information Data | 1 | `80` | yes |
| 9F33 | Terminal Capabilities | 3 | `E0F0C8` | yes |
| 9F34 | CVM Results | 3 | `420300` | yes |
| 9F35 | Terminal Type | 1 | `22` | no |
| 9F36 | Application Transaction Counter | 2 | `01D0` | yes |
| 9F37 | Unpredictable Number | 4 | `9879990F` | yes |
| 9F41 | Transaction Sequence Counter | 3 | `012850` | no |
| 9F53 | Transaction Category Code | 1 | `52` | no |

## Field-by-field check - step 2, timeout reversal (`TD-TC-002921.step2.timeout-reversal.json`)

| Segment | Field No. | Element | Field | Test data value | Test case value | ATL105 rule | Where in ATL105 | Result |
|---|---|---|---|---|---|---|---|---|
| Message | - | - | Message type | `Financial Transaction Request` | `-` | Time-out Reversal of an EMV transaction: no Segment 130 needed | Section 10.14.2.4, PDF p.161 (printed 10-82) | PASS |
| Data Section 1 | 1 | 55 | Message Format Version Identifier | `ATL105` | `ATL105` | Fixed 6 characters: ATL105 | Section 11.1, PDF p.167 (printed 11-4); Element 55, PDF p.391 (printed 13-51) | PASS |
| Data Section 1 | 2 | 63 | Number of Segments | `02` | `-` | Up to 2 digits; equals the number of segments sent (2) | Section 11.1, PDF p.167 (printed 11-4); Element 63, PDF p.395 (printed 13-55) | PASS |
| 100 | 1 | 85 | Segment Type | `100` | `-` | Fixed value 100 | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 2 | 84 | Segment Length | `058` | `-` | Length of all 17 fields + 17 Field Separators = 058 | Section 12.1, PDF p.223 (printed 12-6); Element 84, PDF p.406 (printed 13-66) | PASS |
| 100 | 3 | 44 | Information Byte | `0` | `0` | 1 character: '0' = single-message transmission | Element 44, PDF p.386 (printed 13-46) | PASS |
| 100 | 4 | 102 | Terminal Identifier | `JX72948216050257001` | `JX72948216050257001` | Device Type 'JX' + State Code '72' = Yukon (YT), Canada + Merchant Number and Device Number '948216050257001' | Element 102, PDF p.417 (printed 13-77); Appendix D, PDF p.517 (printed D-4) | PASS |
| 100 | 5 | 78 | Prompt Code | `Z011` | `Z011` | Transaction Type 'Z' = Time-out Reversal; Card Type '011' = Debit checking | Element 78, PDF p.400 (printed 13-60); Appendix G, PDF p.546 (printed G-2); Appendix E, PDF p.521 (printed E-4) | PASS |
| 100 | 6 | 2 | Account Number | `(empty)` | `(not sent)` | Required: the Account Number and the expiry as YYMM (follow-on transaction) | Element 2, PDF p.364 (printed 13-24); Section 10.2.5.2, PDF p.105 (printed 10-26) | FAIL |
| 100 | 7 | 12 | Card Discretionary Block Data | `(empty)` | `(not sent)` | Required with the Account Number (see the card data finding) | Element 12, PDF p.371 (printed 13-31) | FAIL |
| 100 | 8 | 33 | Encrypted PIN Block Data | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 9 | 79 | Pump/Lane Number | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 10 | 41 | Fuel Purchase Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 11 | 58 | Nonfuel Amount | `1500` | `1500` | Digits only, at most 7 for Interlink (8 only for Amex): '1500' = $15.00 | Element 58, PDF p.393 (printed 13-53) | PASS |
| 100 | 12 | 99 | Tax Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 13 | 17 | Cash Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 14 | 86 | Sequence Number | `029211` | `029211` | 6 digits, 000001-999999 | Element 86, PDF p.409 (printed 13-69) | PASS |
| 100 | 15 | 5 | Approval Number | `(empty)` | `(not sent)` | Not sent: a TOR follows a transaction whose response never arrived; it is matched by Sequence Number, Account Number and amount | Section 2.3, PDF p.39 (printed 2-4); Section 2.3.1, PDF p.40 (printed 2-5) | PASS |
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

## Field-by-field check - step 3, next transaction (`TD-TC-002921.step3.next-transaction.json`)

| Segment | Field No. | Element | Field | Test data value | Test case value | ATL105 rule | Where in ATL105 | Result |
|---|---|---|---|---|---|---|---|---|
| Message | - | - | Message type | `EMV Financial Transaction Request` | `-` | Segment 130 is sent only in an EMV Financial Transaction Request | Section 12.20, PDF p.270 (printed 12-53); Section 11.8.1, PDF p.205 (printed 11-42) | PASS |
| Data Section 1 | 1 | 55 | Message Format Version Identifier | `ATL105` | `ATL105` | Fixed 6 characters: ATL105 | Section 11.1, PDF p.167 (printed 11-4); Element 55, PDF p.391 (printed 13-51) | PASS |
| Data Section 1 | 2 | 63 | Number of Segments | `03` | `-` | Up to 2 digits; equals the number of segments sent (3) | Section 11.1, PDF p.167 (printed 11-4); Element 63, PDF p.395 (printed 13-55) | PASS |
| 100 | 1 | 85 | Segment Type | `100` | `-` | Fixed value 100 | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 2 | 84 | Segment Length | `058` | `-` | Length of all 17 fields + 17 Field Separators = 058 | Section 12.1, PDF p.223 (printed 12-6); Element 84, PDF p.406 (printed 13-66) | PASS |
| 100 | 3 | 44 | Information Byte | `0` | `0` | 1 character: '0' = single-message transmission | Element 44, PDF p.386 (printed 13-46) | PASS |
| 100 | 4 | 102 | Terminal Identifier | `JX72948216050257001` | `JX72948216050257001` | Device Type 'JX' + State Code '72' = Yukon (YT), Canada + Merchant Number and Device Number '948216050257001' | Element 102, PDF p.417 (printed 13-77); Appendix D, PDF p.517 (printed D-4) | PASS |
| 100 | 5 | 78 | Prompt Code | `0011` | `0011` | Transaction Type '0' = POS Purchase/Capture; Card Type '011' = Debit checking | Element 78, PDF p.400 (printed 13-60); Appendix G, PDF p.545 (printed G-1); Appendix E, PDF p.521 (printed E-4) | PASS |
| 100 | 6 | 2 | Account Number | `(empty)` | `(not sent)` | Required: the data read from the card (entry mode 07 = contactless chip read) | Element 2, PDF p.364 (printed 13-24); Element 12, PDF p.371 (printed 13-31) | FAIL |
| 100 | 7 | 12 | Card Discretionary Block Data | `(empty)` | `(not sent)` | Required with the Account Number (see the card data finding) | Element 12, PDF p.371 (printed 13-31) | FAIL |
| 100 | 8 | 33 | Encrypted PIN Block Data | `(empty)` | `(not sent)` | Required for debit: DUKPT Key Serial Number + PIN block | Section 10.2.1.3, PDF p.96 (printed 10-17); Element 33, PDF p.382 (printed 13-42) | FAIL |
| 100 | 9 | 79 | Pump/Lane Number | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 10 | 41 | Fuel Purchase Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 11 | 58 | Nonfuel Amount | `1500` | `1500` | Digits only, at most 7 for Interlink (8 only for Amex): '1500' = $15.00 | Element 58, PDF p.393 (printed 13-53) | PASS |
| 100 | 12 | 99 | Tax Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 13 | 17 | Cash Amount | `(empty)` | `(not sent)` | Optional - not sent (the Field Separator is still sent) | Section 12.1, PDF p.223 (printed 12-6) | PASS |
| 100 | 14 | 86 | Sequence Number | `029212` | `029212` | 6 digits, 000001-999999 | Element 86, PDF p.409 (printed 13-69) | PASS |
| 100 | 15 | 5 | Approval Number | `(empty)` | `(not sent)` | Not sent on a purchase (it would make the purchase preauthorized) | Element 5, PDF p.368 (printed 13-28) | PASS |
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
| 130 | 6 / 9F03 | 190 | Tag 9F03 Amount, Other | `000000000000` | `-` | No cash back, so zero (EMV Book 3) | Element 17, PDF p.375 (printed 13-35); Element 190, PDF p.478 (printed 13-138) | PASS |
| 130 | 6 / 9C | 190 | Tag 9C Transaction Type | `00` | `-` | 00 = purchase of goods/services (Transaction Type 0; EMV Book 3) | Appendix R, PDF p.697 (printed R-1); Element 190, PDF p.478 (printed 13-138) | PASS |
| 130 | 6 / 5F2A | 190 | Tag 5F2A Transaction Currency Code | `0840` | `-` | 0840 = United States (Table 006 default 840) | Appendix I, PDF p.555 (printed I-6) | PASS |
| 130 | 6 / 9F1A | 190 | Tag 9F1A Terminal Country Code | `0840` | `-` | 0840 = United States (Table 006 default 840) | Appendix I, PDF p.555 (printed I-6) | PASS |
| 130 | 6 / 9F27 | 190 | Tag 9F27 Cryptogram Information Data | `80` | `-` | ARQC (go online) (EMV Book 3) | Element 190, PDF p.478 (printed 13-138) | PASS |
| 130 | 6 / 9F34 | 190 | Tag 9F34 CVM Results | `420300` | `-` | CVM code 02 = enciphered PIN verified online (EMV Book 3) | Element 190, PDF p.478 (printed 13-138) | PASS |
| 130 | 7 | 191 | EMV Additional Information Indicator | `001` | `001` | 001 = EMV table data | Element 191, PDF p.479 (printed 13-139) | PASS |
| 130 | 8 | 192 | EMV Additional Information Length | `984` | `984` | 3 digits, 001-985, = length of Element 118 (984) | Element 192, PDF p.480 (printed 13-140) | PASS |
| 130 | 9 | 118 | EMV Additional Information | `AX5K3PHZE31OPT7H75RT...Q8QU1GQO (984 characters)` | `AX5K3PHZE31OPT7H... (984)` | Indicator 001 = EMV Table Data: EMVYES or EMVNOT (6 characters) | Element 118, PDF p.439 (printed 13-99); Appendix T, PDF p.701 (printed T-1) | FAIL |

### EMV Chip Data (Element 190) decoded - step 3

Decoded from the hexadecimal test data (2-byte BCD length first, Appendix R). Tag names follow EMV Book 3; Appendix R shows an example tag set only.

| Tag | Name | Length (bytes) | Value | In the Appendix R example |
|---|---|---|---|---|
| 5F2A | Transaction Currency Code | 2 | `0840` | yes |
| 5F34 | PAN Sequence Number | 1 | `01` | no |
| 82 | Application Interchange Profile | 2 | `5800` | yes |
| 84 | Dedicated File (DF) Name | 7 | `A0000000033010` | yes |
| 95 | Terminal Verification Results | 5 | `8000048000` | yes |
| 9A | Transaction Date | 3 | `260101` | yes |
| 9C | Transaction Type | 1 | `00` | yes |
| 9F02 | Amount, Authorized | 6 | `000000001500` | yes |
| 9F03 | Amount, Other | 6 | `000000000000` | no |
| 9F06 | Application Identifier (AID) | 7 | `A0000000033010` | yes |
| 9F10 | Issuer Application Data | 8 | `010103A40000DAC0` | yes |
| 9F1A | Terminal Country Code | 2 | `0840` | yes |
| 9F1E | Interface Device Serial Number | 8 | `3132333435363738` | no |
| 9F26 | Application Cryptogram | 8 | `B4302C8AE8B7E91C` | yes |
| 9F27 | Cryptogram Information Data | 1 | `80` | yes |
| 9F33 | Terminal Capabilities | 3 | `E0F0C8` | yes |
| 9F34 | CVM Results | 3 | `420300` | yes |
| 9F35 | Terminal Type | 1 | `22` | no |
| 9F36 | Application Transaction Counter | 2 | `01D0` | yes |
| 9F37 | Unpredictable Number | 4 | `DB124943` | yes |
| 9F41 | Transaction Sequence Counter | 3 | `012850` | no |
| 9F53 | Transaction Category Code | 1 | `52` | no |

## Step-to-step checks

| Step | Check | This step | Original step | Where in ATL105 | Result |
|---|---|---|---|---|---|
| 2 | Same Sequence Number as the original | `029211` | `029211` | Section 2.3.1, PDF p.40 (printed 2-5); Element 86, PDF p.409 (printed 13-69) | PASS |
| 2 | Same Account Number as the original | `` | `` | Section 2.3.1, PDF p.40 (printed 2-5) | PASS |
| 2 | Same terminal as the original | `JX72948216050257001` | `JX72948216050257001` | Section 2.3.1, PDF p.40 (printed 2-5) | PASS |
| 2 | Same POS entry mode as the original | `07` | `07` | Appendix J, PDF p.638 (printed J-1) | PASS |
| 3 | New Sequence Number | `029212` | `029211` | Section 2.3.1, PDF p.40 (printed 2-5) | PASS |

## Expected response codes

| Step | Expected Response Code | Meaning | Where in ATL105 | Result |
|---|---|---|---|---|
| 2 | `0` | Approved - Purchase/Capture | Element 83, PDF p.404 (printed 13-64) | PASS |
| 3 | `0` | Approved - Purchase/Capture | Element 83, PDF p.404 (printed 13-64) | PASS |

## Spec text quoted by the test case

| Cited page | Quote in the test case | Check | Result |
|---|---|---|---|
| PDF p.39 (printed 2-4) | A TOR is generated when any of the following transactions is unsuccessful: POS Purchase/Capture transaction, CAT Purchase/Capture transaction, Mail/Phone Purchase transaction | Found word-for-word on the cited page | PASS |
| PDF p.40 (printed 2-5) | The Sequence Number of a TOR must be the same as the Sequence Number of the financial transaction being reversed. | Found word-for-word on the cited page | PASS |
| PDF p.40 (printed 2-5) | When the next financial transaction is initiated from that device, the queued TOR is transmitted to BUYPASS ahead of the new financial transaction request. | Found word-for-word on the cited page | PASS |
| PDF p.512 (printed Appendix C-2) | 48 Interlink - Authorizer Type: Debit | Not word-for-word: all its words are on the cited page, but not in this form | REVIEW |
| PDF p.97 (printed 10-18) | The following point-of-sale transactions are supported for debit card processing: ... Purchase/Capture | Found word-for-word on the cited page | PASS |
| PDF p.161 (printed 10-82) | The following debit cards are supported for EMV processing: ... Interlink | Found word-for-word on the cited page | PASS |

