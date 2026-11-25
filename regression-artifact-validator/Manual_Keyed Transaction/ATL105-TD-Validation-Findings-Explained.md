# ATL105 Test Data Review - TC-001867 to TC-001871 (Explained)

## 1. What was reviewed

| Item | Value |
|---|---|
| Specification | BUYPASS Platform ATL105 Message Format Specifications, Software Version ATL105, **Specification Release Version 2026-4 (October 09, 2026)** |
| Test data reviewed | `regression-artifact-validator/test_data/TD-TC-001867.json` ... `TD-TC-001871.step2.reversal.json` (7 files, one request message each) |
| What "test data" means | Each JSON file is the request message the test sends to BUYPASS: Segment 100 (Standard Message Data) + Segment 111 (Variable Information) |
| What "wrong" means | A value in the JSON breaks a rule written in ATL105, or contradicts the test case's own purpose, so the host may reject it or the test would not prove what it claims |

**How to find a page:** "PDF p.639" is the page number shown in the PDF viewer. "Printed J-2" is the number in the page footer of the document. Both point to the same page.

**Result meanings:**

- **FAIL** - a value breaks an ATL105 rule. Must be fixed.
- **BLOCKED** - a field contains instruction text (for example `<ApprovalNumber from step 1 response>`) instead of a real value. The file cannot be sent until that value is filled in from the step-1 response.
- **REVIEW** - the data itself is acceptable, but something must be confirmed (placeholder merchant, wrong page cited, and so on).

## 2. Results

| Test case | Test data file(s) | Result | Can it run as-is? | Issues |
|---|---|---|---|---|
| TC-001867 | TD-TC-001867.json | REVIEW | Yes, once the real terminal ID is supplied | 5, 6, 8 |
| TC-001868 | TD-TC-001868.json | REVIEW | Yes, once the real terminal ID is supplied | 5, 6, 7, 8 |
| TC-001869 | TD-TC-001869.json | **FAIL** | No - fix Issue 1 | **1**, 5, 6, 8 |
| TC-001870 | TD-TC-001870.step1.purchase.json, TD-TC-001870.step2.split-tender.json | **FAIL** | No - fix Issue 2 | **2**, 5, 6, 8, 9, 10 |
| TC-001871 | TD-TC-001871.step1.purchase.json, TD-TC-001871.step2.reversal.json | **FAIL** | No - fix Issue 3 | **3**, 4, 5, 6, 8, 10 |

## 3. Issues that must be fixed (FAIL)

### Issue 1 - TC-001869: the terminal is described two different ways in the same message

**File:** `TD-TC-001869.json`, Segment 111 ("Variable Information Data Segment"):

```json
{ "VariableInformationIndicator": "005", "VariableInformationLength": "003", "VariableInformation": "018" },
{ "VariableInformationIndicator": "031", "VariableInformationLength": "002", "VariableInformation": "12" }
```

**What the test case says it tests:** "Terminal has a contactless (RFID) receiver attached and **no chip reader**; the card is swiped or keyed."

**What ATL105 says:**

| Reference | Section / location | PDF page | Printed page | Exact text |
|---|---|---|---|---|
| Table 005, position 3 | Appendix J "Valid Point-of-Service Entry Mode Codes" | 639 | J-2 | "8 Contactless Magnetic Stripe Read Capability" |
| Table 005, position 3 (note 1) | Appendix J | 639 | J-2 | "If a Contactless MSR is attached to the POS device and it has no chip capability, then all transactions will use Terminal PIN Entry Capability Mode code 8; but For EMV Contact and Contactless the valid PIN Entry Capability Mode code is 1 and not 8." |
| Table 031 codes | Appendix I "Point-of-Service Terminal Capability Codes" (Table ID 031) | 569 | I-20 | "07 = Contactless magnetic stripe read capability (no contact or contactless chip capability)" / "12 = Hybrid Integrated Circuit Card Reader (Contact Chip & Contactless Chip) Capabilities" |
| Table 031 rule | Appendix I | 569 | I-20 | "Once certified, the respective terminal capability values do not change regardless of the POS Entry Mode." |

**Why it is wrong:**

- `018` (Table 005) tells BUYPASS: keyed entry (`01`) on a terminal with a contactless magstripe reader and **no chip** (`8`).
- `12` (Table 031) tells BUYPASS: the terminal **has** contact and contactless chip readers.
- Both cannot be true for the same terminal. Per p.639, a chip-capable terminal must send `1`, not `8`. Per p.569, a no-chip contactless-magstripe terminal is `07`, not `12`.
- The other four test cases send the same Terminal Identifier `JX72948216050257001` with `011` and `12`. The test generator reused that hybrid chip terminal for an RFID-without-chip scenario.

**Correct value:** keep Table 005 = `018` and change Table 031 to `07`, using a separate RFID terminal (not `JX72948216050257001`).

---

### Issue 2 - TC-001870 step 2 (split tender): the amount is not a value, and the segment length is impossible

**File:** `TD-TC-001870.step2.split-tender.json`, Segment 100:

```json
"SegmentLength": "082",
"NonfuelAmount": "<RemainingBalance = step 1 Nonfuel Amount - step 1 response Approved Amount>"
```

The test case JSON fills this amount with the provisional value `08073891` ($80,738.91). The `082` was calculated from that value.

**What ATL105 says:**

| Reference | Section / location | PDF page | Printed page | Exact text |
|---|---|---|---|---|
| Element 58 Nonfuel Amount | Chapter 13, Data Element Descriptions | 393 | 13-53 | "A maximum length of 8 bytes is allowed for American Express cards only. A maximum length of 7 bytes is allowed for all other card types." |
| Partial approval | 10.1.2.1 Partial Approval Transaction | 84 | 10-5 | "Approved Amount (Element No. 6) contains the approved partial amount, and the POS device must prompt for payment of the remaining balance due on the sale." |
| Segment Length | 12.1 Standard Message Data Segment | 223 | 12-6 | "Identifies the data segment's length, including Segment Type's length and Field Separators." / "When a field is not populated, still send the Field Separator." |
| Element 84 Segment Length | Chapter 13 | 406 | 13-66 | "This figure is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements." |

**Why it is wrong:**

1. **BLOCKED:** the Nonfuel Amount field holds a sentence, not an amount, so the message cannot be sent as-is.
2. **FAIL:** the provisional amount `08073891` has 8 digits. MasterCard allows at most 7 (p.393).
3. **FAIL:** the original sale is `1500` ($15.00). The remainder after a partial approval must be less than $15.00, so `08073891` can never be a remainder.
4. **FAIL:** `082` was calculated with the 8-digit value.
   - Step 1 (amount `1500`, 4 digits) gives `078`.
   - A real remainder is 1 to 1499 (1 to 4 digits), so the step-2 length must be between `075` and `078`.
   - `082` can never be correct for this test.

**Correct value:**

- Nonfuel Amount = 1500 minus the step-1 response Approved Amount. Element 6 is "Fixed length of nine digits ... Zero-filled and right aligned" (PDF p.368, printed 13-28), so drop the leading zeros.
- Recalculate the Segment Length after substitution.
- Example: Approved Amount `000001000` → remainder `500` → Segment Length `077`.

---

### Issue 3 - TC-001871 step 2 (reversal): reversal amount and segment length are wrong

**File:** `TD-TC-001871.step2.reversal.json`, Segment 100:

```json
"SegmentLength": "088",
"NonfuelAmount": "<NonfuelAmount from step 1 response>",
"ApprovalNumber": "<ApprovalNumber from step 1 response>"
```

The test case JSON fills these with provisional values `73622739` ($736,227.39) and `2J0775`. The `088` was calculated from them.

**What ATL105 says:**

| Reference | Section / location | PDF page | Printed page | Exact text |
|---|---|---|---|---|
| Reversal amount | 10.1.2.1 Partial Approval Transaction | 84 | 10-5 | "The amount in a Reversal (Void) request must be the amount approved in the original transaction response when a transaction has been partially approved. BUYPASS supports reversal of a partially approved original authorization and NOT a partial reversal of an original transaction." |
| Element 58 Nonfuel Amount | Chapter 13 | 393 | 13-53 | "A maximum length of 7 bytes is allowed for all other card types." |
| Element 5 Approval Number | Chapter 13 | 368 | 13-28 | "Fixed length of six alphanumeric characters" / "Required on reversals for matching information to the original purchase." |
| Segment Length | 12.1 and Element 84 | 223 and 406 | 12-6 and 13-66 | (as in Issue 2) |

**Why it is wrong:**

1. **BLOCKED:** Nonfuel Amount and Approval Number hold sentences, not values.
2. **FAIL:** the provisional reversal amount `73622739` has 8 digits (MasterCard maximum is 7) and is far larger than the $15.00 sale. Per p.84 the reversal must carry exactly the partially approved amount, which is always less than `1500`.
3. **FAIL:** `088` was calculated with the 8-digit amount. With real values (amount 1 to 4 digits, Approval Number always 6), the length must be between `081` and `084`.
4. **Wording of the placeholder:** the step-1 response has no "Nonfuel Amount". The value must come from response Element 6 "Approved Amount" (9 digits, zero-filled, p.368), with the leading zeros removed.

**Correct value:**

- Nonfuel Amount = step-1 Approved Amount without leading zeros (for example `000001000` → `1000`).
- Approval Number = the exact 6-character value from the step-1 response.
- Recalculate the Segment Length (example above → `084`).

## 4. Items to review (data acceptable, wording or setup must be corrected)

| # | Test case(s) | What was found | ATL105 reference (Section, PDF page, printed page) | What to do |
|---|---|---|---|---|
| 4 | TC-001871 | The Approval Number link cites **p.85**. That page is **10.1.2.2 Authorization Only Reversal Transaction** (Transaction Type `S`, reversing Auth Only codes `3`/`5`). Step 2 is a **Purchase Reversal**: Prompt Code `8020`, where `8` = "Purchase reversal/void" (Appendix G, PDF p.546, printed G-2). | Correct basis: Element 5 Approval Number, Chapter 13, PDF p.368, printed 13-28: "Required on reversals for matching information to the original purchase." | Change the cited page from 85 to 368. The data is correct. |
| 5 | All 5 | Table 005 (POS Entry Mode) is cited as **p.548**. In 2026-4, p.548 is Appendix H-2 (Decline Codes). p.548 was Table 005's page in the older **2026-3** edition. | Table 005 layout: Appendix I "Point-of-Service Entry Mode Information", PDF p.555, printed I-6. Codes: Appendix J, PDF p.638-639, printed J-1/J-2. | Update citations to p.555. The values `011`/`018` are valid codes. |
| 6 | All 5 | Terminal Identifier `JX72948216050257001` is a placeholder (test-case flag `MERCHANT_PLACEHOLDER`). Positions 3-4 = State Code `72` = **Yukon, Canada**. TC-001867, TC-001868 and TC-001869 are marked `ready` anyway. | Element 102, PDF p.417, printed 13-77: "The Terminal Identifier must be assigned by and set up at BUYPASS." State codes: Appendix D, PDF p.517, printed D-4: "Yukon YT 72". | Client/BUYPASS to supply the real terminal ID. Confirm the location is intended. Do not mark the cases `ready` until then. |
| 7 | TC-001868 | The request is identical to TC-001867 (same card `5100586947757353`, expiry `0730`, amount, entry mode); only the Sequence Number differs. The "3 failed swipes" behaviour happens on the device and is not visible in the host message. | 10.1.1.3 Manual Entry, PDF p.81, printed 10-2: "...prompt the user to swipe the card again—up to three times. If unable to read the magnetic strip after three attempts, the device should prompt for manual entry..." and "If manual entry is used, this fact is indicated on the receipt and journal by an asterisk (*) before the Account Number." | Verify on the device (3 re-swipe prompts, manual-entry prompt, `*` on receipt). Optionally use a different card so the logs can tell the two cases apart. |
| 8 | All 5 | The quote "03 MasterCard - Authorizer Type: Credit" is not word-for-word; it is rebuilt from a table row. The meaning is correct. | Appendix C "Valid Authorizer Codes", PDF p.511, printed C-1: table row `03 / MasterCard® / Credit`. | Quote the table row as it appears. |
| 9 | TC-001870 | The split-tender quote is verbatim but comes from the **healthcare (FSA/HRA)** section. | Cited: 10.1.6.1.3 Split Tender Processing (part of 10.1.6 Healthcare Auto-Substantiation Service), PDF p.87, printed 10-8. General credit basis: 10.1.2.1 Partial Approval Transaction, PDF p.84, printed 10-5. | Cite p.84 as the primary basis. |
| 10 | TC-001870, TC-001871 | Step 1 expects Response Code `F` (partial approval). This only happens with a card whose available balance is below $15.00. | 10.1.2.1, PDF p.84, printed 10-5: "Note: This information is returned when a Partial Approval Indicator value of 1 is sent..." / Element 121, PDF p.440, printed 13-100: "1 Supported". | Client to supply a partial-approval test card or simulator trigger (already listed as `needs_client_input`). |

**Note for the team that builds the wire message (not a JSON error):** In the TC-001871 reversal, Account Number and expiry `3107` must be joined with `=` instead of a Field Separator. Source: 10.1.8.2 Track Support Requirements, PDF p.94, printed 10-15: "The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM)." The segment length is not affected.

## 5. What is correct in all 7 files (PASS)

| Field (JSON name) | Values in test data | ATL105 rule | Section / location | PDF page | Printed page |
|---|---|---|---|---|---|
| MessageFormatVersionIdentifier | `ATL105` | Fixed 6 characters, "ATL105" | Element 55; 11.1 Financial Transactions | 391; 167 | 13-51; 11-4 |
| NumberofSegments | `02` | Count of segments sent (100 + 111) | Element 63 | 395 | 13-55 |
| SegmentType (100) | `100` | Fixed value 100 | 12.1 Standard Message Data Segment | 223 | 12-6 |
| SegmentLength (100), all purchase steps | `078` | Data + Field Separators | 12.1; Element 84 | 223; 406 | 12-6; 13-66 |
| InformationByte | `0` | 0 = Single-message transmission | Element 44 | 386 | 13-46 |
| TerminalIdentifier | `JX72948216050257001` | Device Type + State Code + Merchant + Device, max 22 | Element 102; Appendix D | 417; 514-517 | 13-77; D-1 to D-4 |
| PromptCode | `0020`, `8020` | `0` = POS Purchase/Capture, `8` = Purchase reversal; `020` = Credit | Element 78; Appendix G; Appendix E | 400; 545-546; 521 | 13-60; G-1/G-2; E-4 |
| AccountNumber | 5 MasterCard numbers | Up to 24 characters; check digit valid; MasterCard range 51-55 | Element 2 | 363 | 13-23 |
| CardDiscretionaryBlockData (keyed) | `0730`, `0928`, `0129`, `0627`, `0731` | Keyed card: expiry MMYY | Element 12; 10.1.1.3 Manual Entry | 371-372; 81 | 13-31/13-32; 10-2 |
| CardDiscretionaryBlockData (reversal) | `3107` | Follow-on: expiry YYMM (matches `0731`) | 10.1.8.2 Track Support Requirements | 94-95 | 10-15/10-16 |
| Empty optional fields (PIN block, pump, fuel, tax, cash, local date/time) | empty | Not populated, Field Separator still sent | 12.1 | 223-225 | 12-6 to 12-8 |
| NonfuelAmount (step 1 / single step) | `1500` | Max 7 digits for non-Amex | Element 58 | 393 | 13-53 |
| SequenceNumber | `018671`, `018681`, `018691`, `018701`/`018702`, `018711`/`018711` | 6 digits; new number for split tender; same number for reversal | Element 86; 2.3.1; 10.1.2.1 | 409; 40; 84 | 13-69; 2-5; 10-5 |
| ApprovalNumber on purchase steps | empty | Must not be sent on a purchase (would make it preauthorized) | Element 5 | 368 | 13-28 |
| PartialApprovalIndicator | `1` | 1 = Supported | Element 121 | 440 | 13-100 |
| SegmentType / SegmentLength (111) | `111` / `026` | Fixed 111; length includes Field Separators | 12.10 Variable Information Data Segment | 246 | 12-29 |
| Table 005 (POS Entry Mode) | `011` (and `018` in TC-001869) | `01` = Manual/keyed; `1` = PIN entry capability | Appendix I; Appendix J | 555; 638-639 | I-6; J-1/J-2 |
| Table 031 (Terminal Capability) | `12` | Required on all transactions; valid code (except TC-001869, see Issue 1) | Appendix I | 569 | I-20 |
| Split tender (TC-001870) | card B differs, new Sequence Number | Different payment for remaining balance; new Sequence Number | 10.1.2.1; 2.3.1 | 84; 40 | 10-5; 2-5 |
| Reversal (TC-001871) | same Sequence Number, card, terminal, entry mode `01` | Must match the original transaction | Element 86; 10.1.2.1; Appendix J note 1 | 409; 84; 638 | 13-69; 10-5; J-1 |

Field-by-field tables for every file: [TC-001867.md](TC-001867.md), [TC-001868.md](TC-001868.md), [TC-001869.md](TC-001869.md), [TC-001870.md](TC-001870.md), [TC-001871.md](TC-001871.md). Summary: [ATL105-TD-Validation-Summary.md](ATL105-TD-Validation-Summary.md).
