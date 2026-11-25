**To:** &lt;AI developer – test case / test data generator&gt;  
**Cc:** &lt;QA lead&gt;; &lt;BUYPASS SME&gt;  
**Subject:** ATL105 MasterCard Credit run 16-Nov-2026 – generator issues in test cases and test data (154 of 1,861 test cases fail)  
**Attachments:** ATL105_Run_Validation_Summary.md; ATL105_Run_Validation_All_Test_Cases.xlsx

---

Hi &lt;name&gt;,

We checked every test case and every test data file in the MasterCard Credit run delivered on 16 Nov 2026 against the *BUYPASS Platform ATL105 Message Format Specifications*, release 2026-4 (October 09, 2026). This is the PDF delivered with the run.

This mail lists the issues that come from the generator. For each issue it gives:

- the evidence: test case, file, field and value
- the ATL105 rule, with the PDF page, the printed page and the exact text
- what we think causes it in the generator, based on the `method` and `flags` the generator writes into `test_cases.jsonl`
- the fix we need
- how we will check the fix

| Run | Value |
|---|---|
| Run folder | `mastercard_credit_new - Copy (2)` |
| Build | `buypass-atl105-mastercard-credit-generated-cdf683cfd7` |
| Test cases | 1,861 (`test_cases/test_cases.jsonl`) |
| Test data files | 2,147 (`test_data/`) |
| Specification | ATL105 release 2026-4, October 09, 2026 |
| Not part of this check | `data_element_validation` (1,029 test cases), `terminal_and_host_operations` (47 test cases) |

*PDF p.* is the page number in the PDF viewer. *Printed* is the page number in the ATL105 page footer.

## 1. Summary

| Result | Test cases | Meaning |
|---|---:|---|
| **FAIL** | 154 | At least one defect in the test data or in the test case |
| **BLOCKED** | 1,570 | No defect found, but client values or run-time values are still missing |
| **DEVICE** | 137 | Device-only test with no message to BUYPASS; only its ATL105 quotes could be checked |
| **PASS** | 0 | – |

No test case can run as delivered. All 1,861 use a generated placeholder merchant and Terminal Identifier, which waits for client input. The defects below come from the generator, and they will still be there after the client values are filled in.

| # | Id | Issue | Test cases | Priority |
|---|---|---|---:|---|
| 1 | D-13 | Card read from the chip or stripe, but Card Discretionary Block Data is only an MMYY expiry | 37 | High |
| 2 | D-01 | Totals Request values are QE example values, not the totals of the flow | 28 | High |
| 3 | D-02 | Optional Segment 100 fields filled although their condition does not hold; the literal text "PIN" sent as the PIN block | 18 | High |
| 4 | D-03 | Segment 145 is over 999 characters, has a 4-digit length indicator and its data is not in tag/length/data format | 9 | High |
| 5 | D-12 | Segment 108 (Loyalty) is 156 characters; the maximum is 142 | 1 | High |
| 6 | D-09 | Negative tests that break a second rule | 10 | High |
| 7 | D-15 | Positive boundary tests whose value is not at the limit or is not a valid value | 3 | High |
| 8 | C-02 | Negative tests built on a rule that ATL105 does not contain | 43 | High |
| 9 | C-01 | Same request as another test case, but the opposite expected result | 28 | High |
| 10 | C-03 | Status `ready` although the Terminal Identifier is a placeholder | 606 | Medium |
| 11 | C-04 | Expected Response Code left open (`?`) | 276 | Medium |
| 12 | C-05 | Objective or name does not match the message sent or the expected result | 15 | Medium |
| 13 | C-06 | Time-out Reversal sent after a step that expects a response | 64 | Medium |
| 14 | C-07 | "Different terminal" tests use a random Terminal Identifier | 40 | Medium |
| 15 | Q-01 to Q-04 | ATL105 quotes not in ATL105 (79 test cases), on another page (554), not word for word (474), or from the wrong context (82) | 976 | Medium |
| 16 | P-1 to P-7 | Delivery structure: missing `tx_legs`, cut names, file paths, filler values, missing test data | – | Medium / Low |

- **High:** the test cannot give a valid result even after the client values are supplied.
- **Medium:** must be corrected before sign-off.
- **Low:** clarity only.

The ids are the same as in the attached summary (sections 3 to 7) and in the attached Excel. To see every finding of an id, open sheet **Findings** and filter on column **Defect id**.

## 2. Test data defects (High)

### 2.1 D-13 – Card read from the chip or stripe, but Card Discretionary Block Data is only an MMYY expiry (37 test cases)

**What is wrong.** These are the single-rule and boundary tests that have no `tx_legs` (see P-1). Table 005 says the card was read: entry mode 05 (contact chip), 07 (contactless chip) or 90 (magnetic stripe). But Card Discretionary Block Data (Element 12) holds only a 4-digit expiry in MMYY format, which is the format for a keyed card. For a card that was read, this field must be the Track 2 data after the "=".

- In the negative tests, this is a second broken rule, so a decline cannot be traced to the rule under test.
- In the boundary tests, it prevents the expected approval.

**Evidence.** TC-024314 (BR1_TS1_TC4, line 1798 of `test_cases.jsonl`), file `TD-TC-024314.json`:

| Field | Value |
|---|---|
| Table 005 | `051`: entry mode 05, chip read |
| Account Number | `5107085774521837` |
| Card Discretionary Block Data | `0930`: MMYY only |
| Generator | `method: realistic_expiry_date`, flag `CARD_DATA_KEYED: manually entered card - expiration date MMYY (element 12, p.372; p.81)` |

**ATL105**

- Element 12 Card Discretionary Block Data, PDF p.371 (printed 13-31): "Account Number is considered the data before the = character, and Card Discretionary Block Data is considered the data after the = character."
- Element 12, PDF p.371 (printed 13-31): "Account Number and Card Discretionary Block Data combined do not exceed 76 characters for Track 1 or 37 characters for Track 2."
- Element 12, PDF p.372 (printed 13-32): "For a card whose number has been manually entered, the expiration date is in MMYY format". This rule is for keyed cards only.

**Likely cause.** In this template, the card-data step applies the keyed-card rule (`CARD_DATA_KEYED`) without checking the Table 005 entry mode. The business-flow tests that have `tx_legs` send correct Track 2 data, so only this template is affected.

**Fix.** Build Element 12 from the entry mode:

- 05, 07, 80, 90 or 91: Track 2 (equivalent) data after the "=". That is YYMM, a 3-digit service code (2xx for a chip card) and the discretionary data. Account Number + "=" + Element 12 must be at most 37 characters.
- 01 (keyed): MMYY.
- Follow-on transactions: YYMM, as in Section 10.1.8.2.

**Test cases:** TC-024312, TC-024314, TC-024315, TC-024316, TC-024317, TC-024320, TC-024321, TC-024322, TC-024323, TC-024324, TC-024325, TC-024329, TC-024330, TC-024331, TC-024332, TC-024333, TC-024334, TC-024336, TC-024337, TC-024339, TC-024340, TC-024341, TC-024342, TC-024343, TC-024344, TC-024345, TC-024346, TC-024347, TC-024348, TC-024349, TC-024351, TC-024352, TC-024353, TC-024354, TC-024358, TC-024359, TC-024360. Three more test cases have the same problem: TC-024355, TC-024356 and TC-024357. Their Table 005 is broken on purpose, so they are counted under D-09.

**We will check:** no `FIELD_12` finding for entry modes 05, 07, 80, 90 and 91.

### 2.2 D-01 – Totals Request values are QE example values, not the totals of the flow (28 test cases)

**What is wrong.** 37 of the 67 Totals Requests carry the same values whatever flow comes before them:

- Card Label `CC  `
- Card Type Total Count `00001`
- Card Type Total Amount `00000000`
- Grand Total `00000000`

Count, Amount and Grand Total have `method: qe_example_value`. The 30 Totals Requests that follow purchase flows are calculated correctly (`method: spec_rule_value`). So the calculation exists, but it is not used for Authorization Only, Mail/Phone Authorization Only, Merchandise Return and Balance Inquiry flows.

Of the 37:

- 28 have values that are wrong for their flow. These are D-01.
- 8 are return-only flows whose net amount is negative. They are listed under S-06 in section 6.
- 1 is a Balance Inquiry flow (TC-024042). ATL105 Section 9.2 does not say how a balance inquiry is totalled.

**Evidence.** TC-023513 (BR1_TS11_TC77). The flow is Authorization Only $15.00 approved, then Cancellation, then Totals Request. File `TD-TC-023513.step3.totals.json`:

| Field | In the file | Expected (Section 9.2) |
|---|---|---|
| CC bucket (Card Label / Count / Amount) | `CC  ` / `00001` / `00000000` | Not sent. The Authorization Only is in the AO bucket, and the cancellation subtracts it again. |
| Grand Total | `00000000` | `00000000` |

Other examples of the same pattern:

- TC-023988: Merchandise Return, then Void of the Return, then Totals. The CC bucket nets to count 0, but the file sends count 00001.
- TC-023961: Mail/Phone Authorization Only, then Cancellation, then Totals.

**ATL105**

- Section 9.2 Local Totals, PDF p.72 (printed 9-8): "The grand total includes all transactions except the non-financial transactions grouped in the following categories: • AO—Authorization only"
- Section 9.2, PDF p.72 (printed 9-8): "Note: Cancellations of non-gift cards must subtract from the AO bucket."
- Section 9.2, PDF p.72 (printed 9-8): "A Merchandise Return increases the count and decreases the dollar amount for the corresponding category as well as the grand total."
- Section 12.6 Totals Data Segment, PDF p.237 (printed 12-20): "Field Nos. 15–17 are sent up to a maximum of 18 times—once for each of the following card types—in the order shown and only when data occurs for a card type."

**Fix.** Use the purchase-flow calculation for every flow:

- Authorization Only (including Mail/Phone): goes to the AO bucket and is not in the grand total.
- Cancellation: subtracts from the AO bucket.
- Merchandise Return: count +1, amount −.
- Void of a Return: the opposite of the return.
- A bucket whose net count is 0 is not sent.

**Test cases:** TC-023513, TC-023514, TC-023559, TC-023560, TC-023609, TC-023610, TC-023739, TC-023740, TC-023783, TC-023784, TC-023829, TC-023830, TC-023873, TC-023874, TC-023920, TC-023921, TC-023961, TC-023962, TC-023981, TC-023982, TC-023988, TC-023994, TC-024000, TC-024006, TC-024012, TC-024018, TC-024025, TC-024031.

**We will check:** no `TOTALS_FLOW_MISMATCH` finding, and no Totals field with `method: qe_example_value`.

### 2.3 D-02 – Optional Segment 100 fields with invalid values, or filled although their condition does not hold (18 test cases)

**What is wrong.** There are two patterns, both in Authorization Only decision-table tests:

- **(a) "Authorization Only with optional segment 100 present"** (8 test cases, one per scenario BR1_TS11 to BR1_TS18). Every optional Segment 100 field is filled, including:
  - an Approval Number on a new Authorization Only, which makes the request preauthorized
  - PIN block `16`
  - Local Date and Local Time
  - Pump/Lane Number, Fuel Purchase Amount, Tax Amount and Cash Amount at merchants that are not petroleum
- **(b) "Authorization Only with Encrypted PIN Block Data = PIN"** (10 test cases). Element 33 contains the literal text `PIN` (`method: scenario_parameter_literal`). This is the decision-table parameter label, not a PIN block.

**Evidence**

| Test case | File | Field | Value | Generator `method` |
|---|---|---|---|---|
| TC-020989 (BR1_TS11_TC66) | TD-TC-020989.json | ApprovalNumber | `J38FQA` | format_synthesis |
| | | EncryptedPINBlockData | `16` | qe_example_value |
| | | LocalDateandLocalTime | `0615251430` | qe_example_value |
| | | PumpLaneNumber / FuelPurchaseAmount / CashAmount | `1` / `1` / `1`, merchant GENERAL (not petroleum) | qe_example_value |
| TC-020974 (BR1_TS11_TC51) | TD-TC-020974.json | EncryptedPINBlockData | `PIN` | scenario_parameter_literal |

**ATL105**

- Element 5 Approval Number, PDF p.368 (printed 13-28): "If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization."
- Element 33 Encrypted PIN Block Data, PDF p.382 (printed 13-42): "For DUKPT PIN encryption, this is a 16- to 20-byte Key Serial Number (KSN) plus a 16-byte PIN block."
- Section 12.1 Standard Message Data Segment, Local Date and Local Time, PDF p.225 (printed 12-8): "Identifies the local date and local time of the preauthorized transaction."

**Fix**

- **(a)** Fill only the optional fields whose condition holds for the transaction:
  - no Approval Number and no Local Date and Local Time on a new authorization
  - no pump or fuel data outside petroleum
  - either a real PIN block or no PIN block
- **(b)** Turn a decision-table parameter into a valid value, never the label itself. For "PIN", that is a DUKPT KSN + PIN block (32 to 36 hex characters) from the client, or an `<AWAITING:...>` value.

**Test cases**

- (a) TC-020989, TC-021075, TC-021163, TC-021336, TC-021419, TC-021509, TC-021592, TC-021680
- (b) TC-020974, TC-021060, TC-021148, TC-021321, TC-021404, TC-021494, TC-021577, TC-021665, TC-021890, TC-022051

**We will check:** no `FIELD_5`, `X_PIN_BLOCK_FORMAT` or `X_OPTIONAL_FIELD_CONDITION` finding.

### 2.4 D-03 – Segment 145 (Enhanced Fleet Request) is not valid (9 test cases)

**What is wrong.** These are the "Decision table: … with segment 145 sent" tests. Enhanced Fleet Data is 999 random characters. As a result:

- the segment is 1,009 characters, but its maximum is 999
- the Segment Length Indicator has 4 digits instead of 3
- the data is not in tag/length/data format

**Evidence.** TC-023424 (BR1_TS7_TC9), file `TD-TC-023424.json`:

| Field | Value |
|---|---|
| SegmentLengthIndicator | `1008`: 4 characters, but the field is 3 characters long |
| EnhancedFleetData | 999 characters, `SE1Q7F4VS4SQT9AELSGV…` (`method: format_synthesis`). Position 1 is not a 3-digit tag followed by a 3-digit length. |
| Whole segment | 1,009 characters |

**ATL105**

- Section 12.31 Enhanced Fleet Request Segment, PDF p.289 (printed 12-72): "2 84 Segment Length Indicator 3 R Indicates the data segment length including the Segment Type length"
- Section 12.31, PDF p.289 (printed 12-72): "All or some of the Enhanced Fleet sub-segment data is specified below. Data is in <tag><len><data> format."
- Section 12.31, PDF p.289 (printed 12-72): "Only the Prompt Table sub-segment is valid for MasterCard Enhanced Fleet EMV transactions."
- Section 11.1 Financial Transactions, PDF p.168 (printed 11-5): "145 Enhanced Fleet Request Segment 999 C Contains data that applies to enhanced fleet offerings."

**Likely cause.** `format_synthesis` fills the field up to its maximum with random characters instead of building sub-segments. The length indicator is then calculated from the oversized content.

**Fix.** Either:

- build Segment 145 from the sub-segment layouts, as `<3-digit tag><3-digit length><data>` (for MasterCard, only the Prompt Table sub-segment), and keep the segment within 999 characters; or
- do not send Segment 145 for MasterCard Credit at all (see C-09).

**Test cases:** TC-020992, TC-021078, TC-021166, TC-021339, TC-021422, TC-021512, TC-021595, TC-021682, TC-023424.

### 2.5 D-12 – Segment 108 (Loyalty Card Data) is 156 characters; the maximum is 142 (1 test case)

**Evidence.** TC-021424 (BR1_TS15_TC68, "Decision table: Authorization Only with segment 108 sent"), file `TD-TC-021424.json`. SegmentLength is `156`. All 13 data fields are filled, including LoyaltyTrack2Data (38 characters), CouponID (19) and UnitofWork (19).

**ATL105.** Section 12.7 Loyalty Card Data Segment, PDF p.238 (printed 12-21): "It has a maximum length of 142 alphanumeric characters"

**Fix.** Fill only the loyalty fields the scenario needs, and check the segment against its maximum length once it is built.

### 2.6 D-09 – Negative tests that break a second rule (10 test cases)

**What is wrong.** A single-rule negative test must be valid apart from the rule under test. Otherwise BUYPASS can decline for the other reason, and the test proves nothing.

| Test case | Rule under test (objective) | Second rule broken in the test data |
|---|---|---|
| TC-022198, TC-024319, TC-024326 | Segment 145 / 123 / 104 omitted | Credential on File (Table 005 entry mode 10) sent without Table 030, and without the COF indicators (Table 032 sub-table 08, Table 049 sub-table 02, Table 056 sub-table 09) |
| TC-024323, TC-024324, TC-024349 | EMV Chip Data Length empty / one byte too long / wrong character type | Segment 130 Segment Length is calculated from the broken length field, as if the chip data were 0 bytes. TC-024323 declares `0054`, but with its 147-byte chip data the segment is `0201`. |
| TC-024350 | Card Discretionary Block Data one byte too long | Account Number is `TOTALS REQUEST` (`method: qe_example_value`), which is not a card number |
| TC-024355 | Variable Information one byte too long | Variable Information Length left empty for the 983-character table; Card Discretionary Block Data is MMYY (see D-13) |
| TC-024356, TC-024357 | Variable Information Indicator empty / not a valid table | Card Discretionary Block Data is MMYY on a contactless chip test (see D-13) |

**ATL105**

- Appendix I, Table 030 POS Condition Code, PDF p.568 (printed Appendix I-19): "Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions."
- Appendix I, Table 032 Sub Table 08, PDF p.575 (printed Appendix I-26): "It is merchant’s responsibility to send the indicator as Initial (“I”) or Subsequent Credential on File (“C”) from the terminal for all COF transactions (Authorization, Completion, Cancellation, Reversal etc.)."
- Section 12.20 EMV Request Data Segment, PDF p.270 (printed 12-53): "Identifies the data segment's length, including Segment Type's length and Field Separators."
- Element 2 Account Number, PDF p.364 (printed 13-24): "a variable length (up to 24 alphanumeric bytes) Account Number"

**Fix**

1. Start each negative test from the valid message of the matching positive test.
2. Change only the field under test.
3. Recalculate the lengths that depend on it (Segment Length, Variable Information Length) from the actual content.

Do not take Account Number values from Totals Request samples. In TC-024334 and TC-024343 the generator already replaced the same value with `<AWAITING:AccountNumber>`; please handle TC-024350 the same way.

### 2.7 D-15 – Positive boundary tests: the value is not at the limit or is not a valid value (3 test cases)

| Test case | Objective | Value in the test data | Problem |
|---|---|---|---|
| TC-024336 (BR1_TS73_TC1) | Service Level exactly at its documented maximum length (1 bytes) is accepted | `A` | Not a valid Service Level |
| TC-024341 (BR1_TS58_TC1) | Account Number exactly at its documented maximum length (24 bytes) is accepted | `5191561344107494` (16 digits) | Not at the 24-character limit, so the boundary is not tested |
| TC-024353 (BR1_TS12_TC1) | Information Byte exactly at its documented maximum length (1 bytes) is accepted | `A` | Not a valid Information Byte |

In TC-024336 and TC-024353 the value was written with `method: boundary_at_limit_value` and flag `BOUNDARY_TEST`, which fills in `A` without checking the valid values. In TC-024341 the Account Number is an ordinary card number (`method: realistic_pan`), so the boundary value was never applied. The names of all three end with "→ declined", but they expect an approval (see C-05).

**ATL105**

- Element 87 Service Level, PDF p.410 (printed 13-70): "F Full serve S Self-serve N Mini-serve X Maxi-serve H High-Speed Dispense O Other/Fuel not present."
- Element 44 Information Byte, PDF p.386 (printed 13-46). The valid codes are `?` (Download Request), `0` (Single-message transmission) and `1` (Multimessage transmission).
- Element 2 Account Number, PDF p.364 (printed 13-24): "a variable length (up to 24 alphanumeric bytes) Account Number"

**Fix.** A positive boundary value must be a valid value of exactly the maximum length. A MasterCard card number has 16 digits, so a 24-character Account Number cannot be approved. Drop that positive test, or turn it into a negative test.

## 3. Test case design defects (High)

### 3.1 C-02 – Negative tests built on a rule that ATL105 does not contain (43 test cases)

All of these tests expect a decline (Response Code 1). ATL105 gives BUYPASS no reason to decline these requests, so the expected result is wrong.

| Objective pattern | Test cases | Why the premise is wrong | ATL105 |
|---|---:|---|---|
| "Authorization Only with Encrypted PIN Block Data sent where the spec forbids it" | 10 | A PIN is optional for credit, and online PIN applies to credit | Section 10.14.3, PDF p.162 (printed 10-83): "Online PIN: This is applicable for credit or debit transactions, in which the PIN is electronically sent to and validated by the card issuer." |
| "… Information Byte sent where the spec forbids it" | 8 | Information Byte is a Required field of Segment 100 | Section 12.1, PDF p.223 (printed 12-6): "3 44 Information Byte 1 R" |
| "… Payment Tender Type sent where the spec forbids it" | 8 | Required field of Segment 108 | Section 12.7, PDF p.239 (printed 12-22): "12 148 Payment Tender Type 2 R Used to determine whether the transaction is a multiple-card transaction involving a loyalty card." |
| "… Service Level sent where the spec forbids it" | 8 | Required field of Segment 102 | Section 12.3, PDF p.230 (printed 12-13): "3 87 Service Level 1 R Identifies the type of sale." |
| "Mail/Phone Reversal sent on a … MasterCard Credit card (transaction not supported)" | 1 | Mail/Phone Reversal is listed as supported for credit | Section 10.1.2, PDF p.83 (printed 10-4): "The following point-of-sale transactions are supported for credit card processing: Purchase/Capture Purchase Reversal Authorization Only Merchandise Return Time-out Reversal Mail/Phone Purchase Mail/Phone Authorization Only Mail/Phone Reversal" |
| "<segment> (segment NNN) omitted where p.N requires it" | 8 | The cited page does not say the segment is required (see the table below). Section 11.1 lists Segments 101, 104, 123 and 145 as conditional (C), and nothing in these MasterCard Credit tests needs them. Segment 130 is required only for EMV transactions, and TC-024360 is a magnetic-stripe transaction. | Section 11.1, PDF p.168 (printed 11-5): "101 Fleet Data Segment 308 C Contains fleet-specific data. Sent only on transactions requiring fleet data." Section 11.8, PDF p.206 (printed 11-43), on Segment 130: "This segment is the only segment required for all EMV financial transactions." |

The cited pages for the "segment omitted" tests:

| Test case | Objective | What the cited page contains |
|---|---|---|
| TC-022198 | Enhanced Fleet Request (segment 145) omitted where p.561 requires it | p.561 is Appendix I-12, the Visa payWave table |
| TC-024325 | NFC Payment Tokenization Data Segment (segment 123) omitted where p.688 requires it | p.688 is Appendix O-1 |
| TC-024326 | Purchase Card Data Segment (segment 104) omitted where p.728 requires it | p.728 is Appendix AD-1 |
| TC-024328 / TC-024329 / TC-024330 / TC-024331 | Segment 108 / 104 / 101 / 104 omitted where p.15 / p.28 / p.23 / p.24 requires it | Front matter, with no printed page number |
| TC-024360 | EMV Request Data Segment (segment 130) omitted where p.142 requires it | p.142 is Section 10.13 (tokenization). The test is a magnetic-stripe Authorization Only, which does not need Segment 130. |

**Fix.** Generate negative tests only from rules that ATL105 states:

- a Required field that is omitted or empty
- a value outside the documented list
- a length or character-type violation
- a transaction type that is not in Section 10.1.2

Cite the page that states the rule. Remove the "sent where the spec forbids it" pattern for Required fields.

**Test cases:**

- Encrypted PIN Block Data: TC-020970, TC-021056, TC-021144, TC-021317, TC-021400, TC-021490, TC-021573, TC-021661, TC-021888, TC-022049
- Information Byte: TC-020971, TC-021057, TC-021145, TC-021318, TC-021401, TC-021491, TC-021574, TC-021662
- Payment Tender Type: TC-020972, TC-021058, TC-021146, TC-021319, TC-021402, TC-021492, TC-021575, TC-021663
- Service Level: TC-020973, TC-021059, TC-021147, TC-021320, TC-021403, TC-021493, TC-021576, TC-021664
- Mail/Phone Reversal: TC-023733
- Segment omitted: TC-022198, TC-024325, TC-024326, TC-024328, TC-024329, TC-024330, TC-024331, TC-024360

### 3.2 C-01 – Same request as another test case, but the opposite expected result (28 test cases)

Each test case below sends the same message as another test case: all segments are identical except the Sequence Number. Yet the two tests expect opposite results, so at most one of them can pass.

| Test case (expects) | Same request as (expects) | Pairs |
|---|---|---:|
| Decision table "Authorization Only with Encrypted PIN Block Data = PIN" (2, approved), e.g. TC-020974 | Negative "… Encrypted PIN Block Data sent where the spec forbids it" (1, declined), e.g. TC-020970 | 10 |
| Decision table "… with Payment Tender Type = AX" (2), e.g. TC-020977 | Negative "… Payment Tender Type sent where the spec forbids it" (1), e.g. TC-020972 | 8 |
| Decision table "… with Service Level = F" (2), e.g. TC-020978 | Negative "… Service Level sent where the spec forbids it" (1), e.g. TC-020973 | 8 |
| "Mail/Phone Purchase -> declined (issuer decline)" (1): TC-023483, TC-023508 | Happy path "Mail/Phone Purchase approved" (0/2): TC-022443, TC-022476 | 2 |

**Fix**

- The first three rows go away when the C-02 negative tests are removed.
- For the issuer-decline tests, give the decline test its own trigger: a decline test card, amount or simulator rule. Request it in `open_items`, as the 41 tests in I-07 already do.

**Test cases:** TC-020974, TC-020977, TC-020978, TC-021060, TC-021063, TC-021064, TC-021148, TC-021151, TC-021152, TC-021321, TC-021324, TC-021325, TC-021404, TC-021407, TC-021408, TC-021494, TC-021497, TC-021498, TC-021577, TC-021580, TC-021581, TC-021665, TC-021668, TC-021669, TC-021890, TC-022051, TC-023483, TC-023508.

## 4. Test case status, expected results and naming (Medium)

### 4.1 C-03 – Status `ready` although the Terminal Identifier is a placeholder (606 test cases)

**Evidence.** TC-020705 (BR1_TS1_TC7):

- status `ready`
- merchant "MERCHANT 01 (POS PLACEHOLDER)", `merchant_provenance: placeholder`
- Terminal Identifier `JX72948216050257001`, whose State Code `72` is Yukon, Canada

**ATL105.** Element 102 Terminal Identifier, PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."

**Fix.** Set `ready` only when no placeholder or awaited value is left. Otherwise use `needs_client_input`.

### 4.2 C-04 – Expected Response Code left open (276 test cases)

**Evidence.** TC-023308 (BR1_TS1_TC14, "Valid flow: Purchase/Capture -> Purchase Reversal"): step 2 has `expected_response` `?` (tier FLAG). Most of these are Totals, valid-flow, invalid-transition and fault-recovery steps.

**ATL105.** Element 83 Response Code, PDF p.404 (printed 13-64) has a separate code for each kind of transaction, for example "0 Approved—Purchase/Capture" and "2 Approved—Authorization only".

**Fix.** State the expected code for every step. If it is not known yet, keep the test at `pending_sme`.

### 4.3 C-05 – Objective or name does not match the message or the expected result (15 test cases)

- **12 test cases** have a decision-table objective that names the wrong transaction:
  - TC-022280 (BR1_TS1_TC11): the objective says "Authorization Only with segment 153 sent", but Prompt Code Transaction Type `0` (Purchase/Capture) is sent.
  - TC-023461 (BR1_TS9_TC5): the objective says "Purchase/Capture", but Transaction Type `6` (Mail/Phone Purchase) is sent.
  - The others: TC-022300, TC-022320, TC-022342, TC-022362, TC-022382, TC-022402, TC-022423, TC-022966, TC-022987, TC-023491.
- **3 test cases** are the boundary tests TC-024336, TC-024341 and TC-024353. Their name ends with "→ declined", but they expect an approval (0 or 2).

**Fix.** Build the objective and the name from the transaction actually sent and from the expected result.

### 4.4 C-06 – Time-out Reversal sent after a step that expects a response (64 test cases)

**Evidence.** TC-020708 (BR1_TS1_TC10):

- `tx_legs[0]` is a purchase that expects Response Code `0` (ASSERT) and has no harness directive.
- `tx_legs[1]` is a Time-out Reversal.

A device that receives the approval has no reason to send a Time-out Reversal.

**ATL105**

- Section 2.3 Unsuccessful Financial Transactions, PDF p.39 (printed 2-4): "A time-out reversal (TOR) is created for an unsuccessful financial transaction."
- Section 2.3.1, PDF p.40 (printed 2-5): "The customer’s 30-second response interval expires without a response from BUYPASS."

**Fix.** Mark the original step as a fault-injected "no response", as the `fault_recovery` tests do. If your harness drops the response in another way, please tell us how; then this item can be closed.

### 4.5 C-07 – "Different terminal" tests use a random Terminal Identifier (40 test cases)

**Evidence.** TC-020707 (BR1_TS1_TC9), step 2: Terminal Identifier `XT47320042968001199580` (`method: deliberate_violation_missing_carry`); step 1 used `JX72948216050257001`. BUYPASS would decline because the terminal is unknown, not because of the same-device rule.

**ATL105**

- Element 102, PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS."
- Section 2.3.1, PDF p.40 (printed 2-5): "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR."

**Fix.** Use a second real terminal of the same merchant from client input, for example `<AWAITING:SecondTerminalIdentifier>`.

## 5. ATL105 quotes in the test cases (Medium)

Every `spec_basis` quote was searched on its cited page in the 2026-4 text.

| Id | Problem | Test cases (findings) | Example |
|---|---|---:|---|
| Q-01 | Quote not in ATL105 | 79 (79) | TC-020707 quotes an internal note as ATL105 text for p.40: "Plan v7 W2: a follow-on of the same transaction is sent by the same device as its original (…)". TC-020968 quotes "§10.1.2.2/§10.2.2.2 (p.83/97): '…' — verified verbatim against raw/pdf_pages/page_0083.txt and page_0097.txt, 2026-10-01." TC-018709 quotes "Table 042 Cardholder Language" (p.583). |
| Q-02 | Quote on another page than cited | 554 (560) | TC-020706: "The Sequence Number must be the Sequence Number contained in the original transaction." is cited as p.402, but it is on p.84. TC-020708: the TOR quote is cited as p.38, but it is on p.40. 533 quotes are on a neighbouring page. 1 test case cites a 2026-3 page: p.246 for Table 005, which is now p.555. |
| Q-03 | Quote not word for word | 474 (601) | TC-018696: "Table 006 Currency Code Information" (p.555) |
| Q-04 | Quote or checkpoint from the wrong context | 82 (82) | 39 tests at non-petroleum merchants carry petroleum device checkpoints ("When pumping is complete, …"), e.g. TC-020705. 30 cite the Segment 153 Table 006 text, which applies to subsequent transactions only, for an original transaction. 11 quote the healthcare (FSA/HRA) split-tender section for normal credit cards. 2 negative tests on Segment 130 cite a page that does not state the rule; the rule is in Section 11.8, PDF p.206 (printed 11-43). |

**Fix**

- Copy quotes word for word from the 2026-4 text, with 2026-4 page numbers.
- Never put internal notes or verification remarks into `spec_basis`.
- Filter device checkpoints and basis quotes by acceptance context and transaction type.

## 6. Delivery structure (P-1 to P-7)

These observations are not rows in the Excel.

| Id | Observation | Example | Request |
|---|---|---|---|
| P-1 | 50 host tests have no `tx_legs`: 48 with test data, plus TC-024318 and TC-024338 without. Every other host test has them. Without `tx_legs`, the harness cannot know the step role or the expected code per step. To validate them, we took the role from the Prompt Code. | TC-022198 and TC-024312 to TC-024360 (the data-element single-rule and boundary tests) | Generate `tx_legs` for every host test |
| P-2 | PDF description text in the objective and name instead of the element name | TC-024348, TC-024354: "EMV Chip Data Component Name: Value (ANSB ...255) The data of each Tag/Length/Value combination is up to 255 byteslong, …" | Use the element name (Element 190 EMV Chip Data) |
| P-3 | Test case names are cut in the middle with "…", so the name does not show what is tested | TC-024336: "… Service Level exactly at its documented maximum length (1 b… → declined" | Keep the full objective in the name, or drop the fixed prefix |
| P-4 | `test_data_file` and `test_data_leg_files` point to `qe_shaped_test_data/…`, but the files are delivered in `test_data/` | TC-020705: `qe_shaped_test_data/TD-TC-020705.json` | Use the delivered folder name |
| P-5 | QE example values are used where a calculated value or a client value is needed | Totals (D-01), Account Number `TOTALS REQUEST` (D-09), PIN block `16` (D-02) | Use QE examples only where the example fits the transaction |
| P-6 | Deliberate violations use an "A…" filler whatever the element type | TC-024348: EMV Chip Data (hex) filled with `AAAA…`. This also breaks the BCD length and the TLV structure, not only the maximum length. | Keep the character type when only the length is broken |
| P-7 | 14 host tests have no test data (`blocked_no_test_data`). TC-024318 lists no awaited input. | TC-021894, TC-021919, TC-021934, TC-021964, TC-021980, TC-021995, TC-022010, TC-022055, TC-022081, TC-022096, TC-022127, TC-022142, TC-024318, TC-024338 | Generate the test data, or list the missing input in `awaiting_inputs` |

## 7. For information: not generator defects

These items wait for client values or for a BUYPASS answer. No generator change is needed unless BUYPASS answers otherwise.

### Waiting for client values or run-time values

- **I-01** (1,709 test cases): placeholder Terminal Identifier.
- **I-02** (230): values taken from the previous response at run time. This is expected.
- **I-03** (438): Variable Information tables awaiting client values.
- **I-04** (541): EMV chip data is generated, not captured from a card or simulator.
- **I-05** (67): Totals device versions are random values.
- **I-06** (65): content of optional segments is synthesized.
- **I-07** (41): decline triggers needed.
- **I-08** (14): no test data.
- **I-09** (2): Account Number awaited.

### Being confirmed with BUYPASS

- **S-01** (196): expiry format for stored credentials.
- **S-02** (10): AO bucket after an authorization.
- **S-03** (52): Field Separator rule for the lengths of Segments 153 and 108.
- **S-04** (26): Network Token Input Indicator `Y` with a clear card number.
- **S-05** (15): premises for Account Verification, Cancellation and Segments 123/153.
- **S-06** (8): negative net total after a Merchandise Return. ATL105 defines no sign for Card Type Total Amount.
- **C-08** (226): same request as another test, where the difference is only on the device side.
- **C-09** (10): Segments 145 and 101 on MasterCard Credit.

## 8. What is correct (no change needed)

- **Test data matches the test cases.** We compared all segments and found no differences.
- **The test cases' own rule checks hold.** Of the 15,630 `rule_checks` written in the test cases, 15,619 hold on the test data, 11 could not be evaluated automatically, and none fails.
- **Sequence Numbers are unique** per terminal for all 1,665 new transactions.
- **Message layout is correct.** Segment 100, 111 and 130 structure, the Variable Information table IDs, and the Chapter 13 format of the fields in the other segments have no findings, apart from the cases listed above.
- **Follow-on links are correct.** Sequence Number, Approval Number taken from the response, and card data as YYMM for follow-ons.
- **150 negative tests break the rule they target, as designed** (157 findings marked as intended). Some of them also break a second rule; those are listed under D-09.
- **Totals are correct for the 30 purchase-based flows.**

## 9. What we need from you

1. Fix the High items in the generator (D-13, D-01, D-02, D-03, D-12, D-09, D-15, C-02, C-01) and send a new build.
2. Fix the Medium items (C-03 to C-07, Q-01 to Q-04, P-1 to P-7) in the same build if possible.
3. Reply per item if you read the cause differently from us.
4. When the new build is available, we will run the same validation (`scripts/validate-run-and-build-manual-validation.py`) on it and send you the comparison.

The attached summary has the full list of findings for every id, with the ATL105 text, how to check it by hand and the fix. The Excel has one row per finding.

Thanks and regards,  
&lt;Your name&gt;  
QA – Core Auth
