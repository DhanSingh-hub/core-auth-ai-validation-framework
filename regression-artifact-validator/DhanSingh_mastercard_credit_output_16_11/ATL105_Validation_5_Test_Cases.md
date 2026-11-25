# MasterCard Credit (plan v12) - defect explanation for BA review: the 5 manual-validation test cases

## Purpose and scope

Manual validation of the 5 test cases TC-039912 to TC-039916 (previous ids TC-020803 to TC-020806 and TC-022362; scenario BR1_TS5, manual/keyed purchase) of the run mastercard_credit_new_16_11. Every field of their test data was checked against ATL105 by the validator; this sheet explains each defect the way the BA note DEFECT_EXPLANATION.md does - references, actual evidence, expected behavior, why it is a defect, acceptance criteria and the ATL105 text - followed by the actual and expected test data of every test case.

| Item | Value |
|---|---|
| Run | [mastercard_credit_new_16_11](mastercard_credit_new_16_11/) - MasterCard Credit run of 16 Nov 2026 (plan v12), build `buypass-atl105-mastercard-credit-generated-923e8c2eac` (only this run was used; its files were checked against the run's own MANIFEST.json - see the appendix) |
| Specification | BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026) - the attached `BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4 (1).pdf` |
| Test cases | [TC-039912](#tc-039912), [TC-039913](#tc-039913), [TC-039914](#tc-039914), [TC-039915](#tc-039915), [TC-039916](#tc-039916) |
| AI result | BLOCKED 5 |
| Readiness | 5 `ready` |

- **TC** = test case: what the test says it will exercise (`test_cases.jsonl`, one test case per line).
- **TD** = test data: the request prepared for transmission to the host (`test_data/TD-TC-....json`).
- **DS** = data segment (Segment 100, 111, ...); **on the wire** = actually included in the transmitted request.
- **Element** = ATL105 data element (Chapter 13); **Table** = Variable Information table in Segment 111 (Appendix I).
- **FAIL** = must be fixed; **BLOCKED** = cannot run until a client or run-time value is supplied; **REVIEW** = to correct or to confirm with BUYPASS.

These are composition defects in the delivered test cases and test data, found by reading them against ATL105 - not observed production failures. No host transactions were executed for this review.

| Defect | Affected test cases | Kind | Defect id | Priority |
|---|---:|---|---|---|
| [1. The Terminal Identifier is a generated placeholder, yet the test cases are marked ready](#d1) | 5 | Awaiting client input / run-time value, Test case issue to correct | I-01, C-03 | High |
| [2. Time-out Reversal sent although the original request is expected to be answered](#d2) | 1 | To confirm with BUYPASS / SME | C-06 | High |
| [3. 'Different terminal' negative tests use a terminal BUYPASS does not know](#d3) | 2 | To confirm with BUYPASS / SME | C-07 | Medium |
| [4. The test objective names a different transaction than the one sent](#d4) | 1 | Test case issue to correct | C-05 | Medium |
| [5. Network token data (Segment 153) is synthesized, and its indicator and length need confirmation](#d5) | 1 | To confirm with BUYPASS / SME, Awaiting client input / run-time value | S-03, S-04, I-06 | Medium |
| [6. Follow-on values are instruction text to be filled in from the previous response](#d6) | 2 | Awaiting client input / run-time value | I-02 | Medium |
| [7. ATL105 citations in the test cases are on the wrong page, not ATL105 text, or about another context](#d7) | 5 | Citation issue | Q-04, Q-02, Q-01 | Low |

<a id="d1"></a>

## Defect 1: The Terminal Identifier is a generated placeholder, yet the test cases are marked ready

**Priority:** High · **Kind:** Awaiting client input / run-time value, Test case issue to correct · **Test cases:** 5 · **Defect id:** C-03, I-01

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-039912](#tc-039912) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L147)) | `ready` | Manual / Keyed Purchase/Capture → approved | Happy path: Purchase/Capture approved | [TD-TC-039912.json](mastercard_credit_new_16_11/test_data/TD-TC-039912.json) |
| [TC-039913](#tc-039913) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L148)) | `ready` | Manual / Keyed Purchase/Capture, then Purchase Reversal with Sequence Number differe… → declined (to confirm) | Single rule broken: Purchase/Capture -> Purchase Reversal with Sequence Number not matching the original -> declined | [TD-TC-039913.step1.purchase.json](mastercard_credit_new_16_11/test_data/TD-TC-039913.step1.purchase.json), [TD-TC-039913.step2.reversal.json](mastercard_credit_new_16_11/test_data/TD-TC-039913.step2.reversal.json) |
| [TC-039914](#tc-039914) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L149)) | `ready` | Manual / Keyed Purchase/Capture, then Purchase Reversal with Terminal Identifier dif… → declined (to confirm) | Single rule broken: Purchase/Capture -> Purchase Reversal with Terminal Identifier not matching the original -> declined | [TD-TC-039914.step1.purchase.json](mastercard_credit_new_16_11/test_data/TD-TC-039914.step1.purchase.json), [TD-TC-039914.step2.reversal.json](mastercard_credit_new_16_11/test_data/TD-TC-039914.step2.reversal.json) |
| [TC-039915](#tc-039915) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L150)) | `ready` | Manual / Keyed Purchase/Capture, then Time-out Reversal with Terminal Identifier dif… → declined (to confirm) | Single rule broken: Purchase/Capture -> Time-out Reversal with Terminal Identifier not matching the original -> declined | [TD-TC-039915.step1.purchase.json](mastercard_credit_new_16_11/test_data/TD-TC-039915.step1.purchase.json), [TD-TC-039915.step2.timeout-reversal.json](mastercard_credit_new_16_11/test_data/TD-TC-039915.step2.timeout-reversal.json) |
| [TC-039916](#tc-039916) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151)) | `ready` | Manual / Keyed Purchase/Capture with Network Token Data Request sent → approved | Decision table: Authorization Only with segment 153 sent (the rule's condition holds) | [TD-TC-039916.json](mastercard_credit_new_16_11/test_data/TD-TC-039916.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-039912 | - | [TD-TC-039912.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-039912.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `JX72948216050257001` | The Terminal Identifier is a placeholder |
| TC-039912 | - | test case record [test_cases.jsonl line 147](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L147) → `status` | `status` = `ready` | Marked 'ready' although the terminal is a placeholder |
| TC-039913 | - | [TD-TC-039913.step1.purchase.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-039913.step1.purchase.json#L9), [TD-TC-039913.step2.reversal.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-039913.step2.reversal.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `JX72948216050257001` | The Terminal Identifier is a placeholder |
| TC-039913 | - | test case record [test_cases.jsonl line 148](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L148) → `status` | `status` = `ready` | Marked 'ready' although the terminal is a placeholder |
| TC-039914 | - | [TD-TC-039914.step1.purchase.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-039914.step1.purchase.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `JX72948216050257001` | The Terminal Identifier is a placeholder |
| TC-039914 | - | test case record [test_cases.jsonl line 149](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L149) → `status` | `status` = `ready` | Marked 'ready' although the terminal is a placeholder |
| TC-039915 | - | [TD-TC-039915.step1.purchase.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-039915.step1.purchase.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `JX72948216050257001` | The Terminal Identifier is a placeholder |
| TC-039915 | - | test case record [test_cases.jsonl line 150](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L150) → `status` | `status` = `ready` | Marked 'ready' although the terminal is a placeholder |
| TC-039916 | - | [TD-TC-039916.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-039916.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `JX72948216050257001` | The Terminal Identifier is a placeholder |
| TC-039916 | - | test case record [test_cases.jsonl line 151](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151) → `status` | `status` = `ready` | Marked 'ready' although the terminal is a placeholder |

### Expected behavior

- Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier.
- `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied.

### Why it matters

- The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada.
- The test case status is 'ready', but its Terminal Identifier is still a placeholder.

**BA explanation:** "Every request uses a made-up terminal. BUYPASS only accepts terminals it has set up, so these tests cannot run until the real Terminal ID is supplied - and they should not be 'ready' until then."

### Acceptance criteria

- Client / BUYPASS supply the real Terminal Identifier of the merchant's terminal; every step uses it and the Segment 100 length is recalculated.
- A test case with a placeholder terminal has a status other than 'ready' (for example requires_review).
- A regression check flags 'ready' test cases whose merchant is a placeholder.

**Specification reference (ATL105, word for word):**

- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS." ([ATL105 text line 23795](../specifications/ATL105/docs/specs/extracted_text_new.txt#L23795))
- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code)." ([ATL105 text line 23816](../specifications/ATL105/docs/specs/extracted_text_new.txt#L23816))
- Appendix D. Valid State Codes, PDF p.517 (printed Appendix D-4): "Yukon YT 72" ([ATL105 text line 28319](../specifications/ATL105/docs/specs/extracted_text_new.txt#L28319))

**Test data excerpt - [TC-039912](#tc-039912), step 1, `TD-TC-039912.json` (selected fields, actual then expected):**

```jsonc
// actual - line 7
"SegmentLength": "078",
// expected
"SegmentLength": "078",  // I-01: recalculate: 059 + length of the real Terminal Identifier
// actual - line 9
"TerminalIdentifier": "JX72948216050257001",
// expected
"TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
```

<a id="d2"></a>

## Defect 2: Time-out Reversal sent although the original request is expected to be answered

**Priority:** High · **Kind:** To confirm with BUYPASS / SME · **Test cases:** 1 · **Defect id:** C-06

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-039915](#tc-039915) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L150)) | `ready` | Manual / Keyed Purchase/Capture, then Time-out Reversal with Terminal Identifier dif… → declined (to confirm) | Single rule broken: Purchase/Capture -> Time-out Reversal with Terminal Identifier not matching the original -> declined | [TD-TC-039915.step1.purchase.json](mastercard_credit_new_16_11/test_data/TD-TC-039915.step1.purchase.json), [TD-TC-039915.step2.timeout-reversal.json](mastercard_credit_new_16_11/test_data/TD-TC-039915.step2.timeout-reversal.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-039915 | 2 | test case record [test_cases.jsonl line 150](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L150) → `tx_legs[0].expect` | `tx_legs[0].expect` = {"tier": "ASSERT", "page": 403, "response_code": "0", "note": "Approved-Purchase/Capture"} and `harness` = `null` (no fault injection) | Step 2 sends a Time-out Reversal although step 1 is expected to be answered |

### Expected behavior

- Make step 1 a fault-injected 'no response' (or a late response the harness ignores). Step 1 then expects no response (instead of Response Code 0), and the device sends the Time-out Reversal after the 30-second response interval.

### Why it matters

- ATL105 creates a TOR only when the original request times out (no response within 30 seconds, or a late response that is ignored). Step 1 has no 'no response' harness directive and expects Response Code 0, so the device would not send a TOR.

**BA explanation:** "A time-out reversal is only sent when the original gets no answer within 30 seconds. This test expects the original to be approved, so a real device would never send the reversal."

### Acceptance criteria

- The original step is a fault-injected 'no response' (or a late response the harness ignores) and expects no response.
- The Time-out Reversal then follows on the same device with the original's Sequence Number, card and amount - only the rule under test differs.

**Specification reference (ATL105, word for word):**

- Section 2.3 Unsuccessful Financial Transactions, PDF p.39 (printed 2-4): "A time-out reversal (TOR) is created for an unsuccessful financial transaction." ([ATL105 text line 2144](../specifications/ATL105/docs/specs/extracted_text_new.txt#L2144))
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "The customer’s 30-second response interval expires without a response from BUYPASS." ([ATL105 text line 2171](../specifications/ATL105/docs/specs/extracted_text_new.txt#L2171))
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "If the customer’s host/controller/POS system receives a late response to the original transaction request from BUYPASS, the late response is ignored." ([ATL105 text line 2174](../specifications/ATL105/docs/specs/extracted_text_new.txt#L2174))
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "Once a time-out occurs for a device, the customer’s host/controller/POS system formats a TOR" ([ATL105 text line 2179](../specifications/ATL105/docs/specs/extracted_text_new.txt#L2179))

<a id="d3"></a>

## Defect 3: 'Different terminal' negative tests use a terminal BUYPASS does not know

**Priority:** Medium · **Kind:** To confirm with BUYPASS / SME · **Test cases:** 2 · **Defect id:** C-07

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-039914](#tc-039914) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L149)) | `ready` | Manual / Keyed Purchase/Capture, then Purchase Reversal with Terminal Identifier dif… → declined (to confirm) | Single rule broken: Purchase/Capture -> Purchase Reversal with Terminal Identifier not matching the original -> declined | [TD-TC-039914.step1.purchase.json](mastercard_credit_new_16_11/test_data/TD-TC-039914.step1.purchase.json), [TD-TC-039914.step2.reversal.json](mastercard_credit_new_16_11/test_data/TD-TC-039914.step2.reversal.json) |
| [TC-039915](#tc-039915) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L150)) | `ready` | Manual / Keyed Purchase/Capture, then Time-out Reversal with Terminal Identifier dif… → declined (to confirm) | Single rule broken: Purchase/Capture -> Time-out Reversal with Terminal Identifier not matching the original -> declined | [TD-TC-039915.step1.purchase.json](mastercard_credit_new_16_11/test_data/TD-TC-039915.step1.purchase.json), [TD-TC-039915.step2.timeout-reversal.json](mastercard_credit_new_16_11/test_data/TD-TC-039915.step2.timeout-reversal.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-039914 | 2 | [TD-TC-039914.step2.reversal.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-039914.step2.reversal.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `VS24671841985066391107` | The 'different terminal' is a generated value not set up at BUYPASS |
| TC-039915 | 2 | [TD-TC-039915.step2.timeout-reversal.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-039915.step2.timeout-reversal.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `QL66203054504417704598` | The 'different terminal' is a generated value not set up at BUYPASS |

### Expected behavior

- Use a second real terminal of the same merchant.

### Why it matters

- Step 2 uses the generated Terminal Identifier 'VS24671841985066391107' (method deliberate_violation_missing_carry). A Terminal Identifier must be assigned by and set up at BUYPASS, so a decline would only prove that an unknown terminal is rejected, not the same-device rule.

**BA explanation:** "To prove that a reversal from another device is declined, the other device must be a real terminal of the same merchant. With a made-up terminal the decline only proves that unknown terminals are rejected."

### Acceptance criteria

- The follow-on step uses a second real Terminal Identifier of the same merchant, assigned by BUYPASS.
- The expected decline for the same-device rule is confirmed with BUYPASS.

**Specification reference (ATL105, word for word):**

- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS." ([ATL105 text line 23795](../specifications/ATL105/docs/specs/extracted_text_new.txt#L23795))
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR." ([ATL105 text line 2181](../specifications/ATL105/docs/specs/extracted_text_new.txt#L2181))

**Test data excerpt - [TC-039914](#tc-039914), step 2, `TD-TC-039914.step2.reversal.json` (selected fields, actual then expected):**

```jsonc
// actual - line 7
"SegmentLength": "087",
// expected
"SegmentLength": "087",  // C-07: recalculate: 065 + length of the real Terminal Identifier
// actual - line 9
"TerminalIdentifier": "VS24671841985066391107",
// expected
"TerminalIdentifier": "<second Terminal Identifier of the same merchant, assigned by BUYPASS>",  // N-01: different from step 1 ('JX72948216050257001') on purpose - the rule this negative test breaks; keep; C-07: a second real terminal of the same merchant (still different from step 1)
```

<a id="d4"></a>

## Defect 4: The test objective names a different transaction than the one sent

**Priority:** Medium · **Kind:** Test case issue to correct · **Test cases:** 1 · **Defect id:** C-05

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-039916](#tc-039916) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151)) | `ready` | Manual / Keyed Purchase/Capture with Network Token Data Request sent → approved | Decision table: Authorization Only with segment 153 sent (the rule's condition holds) | [TD-TC-039916.json](mastercard_credit_new_16_11/test_data/TD-TC-039916.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-039916 | - | test case record [test_cases.jsonl line 151](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151) → `test_objective` | `test_objective` = Decision table: Authorization Only with segment 153 sent (the rule's condition holds) | The test objective names a different transaction than the one sent |

### Expected behavior

- `test_objective` = `Decision table: Purchase/Capture with segment 153 sent (the rule's condition holds)` - Correct the objective to POS Purchase/Capture.

### Why it matters

- The objective says 'Authorization Only', but step 1 sends Prompt Code Transaction Type '0' (POS Purchase/Capture) and the name says Purchase/Capture.

**BA explanation:** "The objective says one transaction, but the request (and the test name) is another. Coverage reports grouped by objective count this test in the wrong place."

### Acceptance criteria

- Objective, name and Prompt Code describe the same transaction.

**Specification reference (ATL105, word for word):**

- Element 78 Prompt Code (Chapter 13), PDF p.400 (printed 13-60): "this is a 4-character Prompt Code that identifies the transaction's Transaction Type (one character) and Card Type (three characters)." ([ATL105 text line 23036](../specifications/ATL105/docs/specs/extracted_text_new.txt#L23036))

<a id="d5"></a>

## Defect 5: Network token data (Segment 153) is synthesized, and its indicator and length need confirmation

**Priority:** Medium · **Kind:** To confirm with BUYPASS / SME, Awaiting client input / run-time value · **Test cases:** 1 · **Defect id:** I-06, S-03, S-04

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-039916](#tc-039916) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151)) | `ready` | Manual / Keyed Purchase/Capture with Network Token Data Request sent → approved | Decision table: Authorization Only with segment 153 sent (the rule's condition holds) | [TD-TC-039916.json](mastercard_credit_new_16_11/test_data/TD-TC-039916.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-039916 | 1 | [TD-TC-039916.json line 50](mastercard_credit_new_16_11/test_data/TD-TC-039916.json#L50) → `Network Token Data Request → SegmentLengthIndicator` | `051` | Network Token Data Request: length depends on an unclear Field Separator rule |
| TC-039916 | 1 | [TD-TC-039916.json line 51](mastercard_credit_new_16_11/test_data/TD-TC-039916.json#L51) → `Network Token Data Request → NetworkTokenData (table 004)` | `Y` | Network Token Input Indicator 'Y' while Element 2 is a card number |
| TC-039916 | 1 | [TD-TC-039916.json line 48](mastercard_credit_new_16_11/test_data/TD-TC-039916.json#L48) → `Network Token Data Request` | `NetworkTokenData` = `00101926686754225094144590020043004004001Y`: table 001 Network Token `2668675422509414459`; table 002 Network Token Expiration Date `3004`; table 004 Network Token Input Indicator `Y` - all generated by the test generator (method `spec_tlv_composite`) | Network Token Data Request: content is synthesized, not client data |

### Expected behavior

- Confirm the Field Separator rule of this segment with BUYPASS.
- Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number.
- Client to supply the merchant's network-token entitlement and a real provisioned network token; keep the test case out of 'ready' until then.

### Why it matters

- '051' assumes a Field Separator after every field (051). ATL105 reads differently for this segment - Field Separator only between Fields 1 and 2 (literal reading of p.310): 049; Field Separator after Fields 1 and 2, none after Field 3: 050.
- Table 004 'Y' says the merchant sent a network token, but Element 2 carries the card number 510058…7353 and the token is only in table 001. ATL105 does not say which field carries the token when the indicator is Y.
- The test case generated the Segment 153 values itself (spec_tlv_composite); they are placeholders, so BUYPASS cannot be expected to approve them. Needed: the merchant's network-token entitlement and a real provisioned network token.

**BA explanation:** "The network token was made up by the generator, the token flag says 'token' while the card number is sent in Element 2, and the segment length depends on a Field Separator rule ATL105 does not state clearly. An approval cannot be expected until real token data is supplied."

### Acceptance criteria

- Client supplies the merchant's network-token entitlement and a real provisioned token (table 001) with its expiry (table 002).
- BUYPASS confirms which field carries the token when table 004 = Y, and the Segment 153 length rule.
- The test stays out of 'ready' until then.

**Specification reference (ATL105, word for word):**

- Section 12.38 Network Token Data Request Segment, PDF p.310 (printed 12-93): "There is a Field Separator between Field Nos. 1 and 2." ([ATL105 text line 18011](../specifications/ATL105/docs/specs/extracted_text_new.txt#L18011))
- Section 12.38 Network Token Data Request Segment, PDF p.310 (printed 12-93): "Field Separators are not there between the elements in Network Token Data Layouts." ([ATL105 text line 18012](../specifications/ATL105/docs/specs/extracted_text_new.txt#L18012))
- Section 12.38 Network Token Data Request Segment, PDF p.310 (printed 12-93): "2 84 Segment Length Indicator 3 R Indicates the data segment length including the Segment Type length"
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.169 (printed 11-6): "153 Network Token Data Request Segment 999 C Contains Network Token Data associated with this transaction." ([ATL105 text line 8054](../specifications/ATL105/docs/specs/extracted_text_new.txt#L8054))
- Section 12.38 Network Token Data Request Segment, PDF p.312 (printed 12-95): "Identifies that the Token or PAN is sent by the merchant Valid values are: Y – Network Token N – Clear PAN" ([ATL105 text line 18118](../specifications/ATL105/docs/specs/extracted_text_new.txt#L18118))
- Element 2 Account Number (Chapter 13), PDF p.364 (printed 13-24): "a variable length (up to 24 alphanumeric bytes) Account Number" ([ATL105 text line 21375](../specifications/ATL105/docs/specs/extracted_text_new.txt#L21375))

**Test data excerpt - [TC-039916](#tc-039916), step 1, `TD-TC-039916.json` (selected fields, actual then expected):**

```jsonc
// actual - line 50
"SegmentLengthIndicator": "051",
// expected
"SegmentLengthIndicator": "051",  // S-03: 051 = a Field Separator after every field; other readings of ATL105 give 049, 050 - confirm with BUYPASS
// actual - line 51
"NetworkTokenData": "00101926686754225094144590020043004004001Y"
// expected
"NetworkTokenData": "001nnn<network token>002004<token expiry YYMM>004001<Y or N>"  // S-04: table 004 'N' (Clear PAN) if Element 2 keeps the card number - confirm with BUYPASS; I-06: real provisioned network token from the client (nnn = its length, up to 019)
```

<a id="d6"></a>

## Defect 6: Follow-on values are instruction text to be filled in from the previous response

**Priority:** Medium · **Kind:** Awaiting client input / run-time value · **Test cases:** 2 · **Defect id:** I-02

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-039913](#tc-039913) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L148)) | `ready` | Manual / Keyed Purchase/Capture, then Purchase Reversal with Sequence Number differe… → declined (to confirm) | Single rule broken: Purchase/Capture -> Purchase Reversal with Sequence Number not matching the original -> declined | [TD-TC-039913.step1.purchase.json](mastercard_credit_new_16_11/test_data/TD-TC-039913.step1.purchase.json), [TD-TC-039913.step2.reversal.json](mastercard_credit_new_16_11/test_data/TD-TC-039913.step2.reversal.json) |
| [TC-039914](#tc-039914) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L149)) | `ready` | Manual / Keyed Purchase/Capture, then Purchase Reversal with Terminal Identifier dif… → declined (to confirm) | Single rule broken: Purchase/Capture -> Purchase Reversal with Terminal Identifier not matching the original -> declined | [TD-TC-039914.step1.purchase.json](mastercard_credit_new_16_11/test_data/TD-TC-039914.step1.purchase.json), [TD-TC-039914.step2.reversal.json](mastercard_credit_new_16_11/test_data/TD-TC-039914.step2.reversal.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-039913 | 2 | [TD-TC-039913.step2.reversal.json line 23](mastercard_credit_new_16_11/test_data/TD-TC-039913.step2.reversal.json#L23) → `Standard Message Data Segment → ApprovalNumber` | `<ApprovalNumber from step 1 response>` | Approval Number holds instruction text, not a value |
| TC-039914 | 2 | [TD-TC-039914.step2.reversal.json line 23](mastercard_credit_new_16_11/test_data/TD-TC-039914.step2.reversal.json#L23) → `Standard Message Data Segment → ApprovalNumber` | `<ApprovalNumber from step 1 response>` | Approval Number holds instruction text, not a value |

### Expected behavior

- At run time the Approval Number returned in the step 1 response replaces this text (the test case's own example value is `7IYI8R`).

### Why it matters

- The field holds the instruction text '<ApprovalNumber from step 1 response>' instead of a real Approval Number. BUYPASS cannot process this text, so the message cannot be sent until the real value from the step 1 response is filled in.

**BA explanation:** "The reversal must carry the approval number the host returns for the purchase. The file holds an instruction text; the test harness must substitute the real value at run time."

### Acceptance criteria

- The harness replaces the instruction text with the value from the previous response before sending.
- The Segment 100 length is recalculated after the substitution.

**Specification reference (ATL105, word for word):**

- Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "Required on reversals for matching information to the original purchase." ([ATL105 text line 21569](../specifications/ATL105/docs/specs/extracted_text_new.txt#L21569))
- Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "Fixed length of six alphanumeric characters; space-filled" ([ATL105 text line 21567](../specifications/ATL105/docs/specs/extracted_text_new.txt#L21567))

**Test data excerpt - [TC-039913](#tc-039913), step 2, `TD-TC-039913.step2.reversal.json` (selected fields, actual then expected):**

```jsonc
// actual - line 23
"ApprovalNumber": "<ApprovalNumber from step 1 response>",
// expected
"ApprovalNumber": "7IYI8R",  // I-02: filled in at run time from the step 1 response - '7IYI8R' is the test case's example
```

<a id="d7"></a>

## Defect 7: ATL105 citations in the test cases are on the wrong page, not ATL105 text, or about another context

**Priority:** Low · **Kind:** Citation issue · **Test cases:** 5 · **Defect id:** Q-01, Q-02, Q-04

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-039912](#tc-039912) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L147)) | `ready` | Manual / Keyed Purchase/Capture → approved | Happy path: Purchase/Capture approved | [TD-TC-039912.json](mastercard_credit_new_16_11/test_data/TD-TC-039912.json) |
| [TC-039913](#tc-039913) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L148)) | `ready` | Manual / Keyed Purchase/Capture, then Purchase Reversal with Sequence Number differe… → declined (to confirm) | Single rule broken: Purchase/Capture -> Purchase Reversal with Sequence Number not matching the original -> declined | [TD-TC-039913.step1.purchase.json](mastercard_credit_new_16_11/test_data/TD-TC-039913.step1.purchase.json), [TD-TC-039913.step2.reversal.json](mastercard_credit_new_16_11/test_data/TD-TC-039913.step2.reversal.json) |
| [TC-039914](#tc-039914) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L149)) | `ready` | Manual / Keyed Purchase/Capture, then Purchase Reversal with Terminal Identifier dif… → declined (to confirm) | Single rule broken: Purchase/Capture -> Purchase Reversal with Terminal Identifier not matching the original -> declined | [TD-TC-039914.step1.purchase.json](mastercard_credit_new_16_11/test_data/TD-TC-039914.step1.purchase.json), [TD-TC-039914.step2.reversal.json](mastercard_credit_new_16_11/test_data/TD-TC-039914.step2.reversal.json) |
| [TC-039915](#tc-039915) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L150)) | `ready` | Manual / Keyed Purchase/Capture, then Time-out Reversal with Terminal Identifier dif… → declined (to confirm) | Single rule broken: Purchase/Capture -> Time-out Reversal with Terminal Identifier not matching the original -> declined | [TD-TC-039915.step1.purchase.json](mastercard_credit_new_16_11/test_data/TD-TC-039915.step1.purchase.json), [TD-TC-039915.step2.timeout-reversal.json](mastercard_credit_new_16_11/test_data/TD-TC-039915.step2.timeout-reversal.json) |
| [TC-039916](#tc-039916) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151)) | `ready` | Manual / Keyed Purchase/Capture with Network Token Data Request sent → approved | Decision table: Authorization Only with segment 153 sent (the rule's condition holds) | [TD-TC-039916.json](mastercard_credit_new_16_11/test_data/TD-TC-039916.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-039912 | - | test case record [test_cases.jsonl line 147](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L147) → `device_checkpoints[3]`, `device_checkpoints[4]` | `device_checkpoints[3]` = MUST: When pumping is complete, the device must provide an Invoice number, as well as all prompts for which the device has already captured responses, with the Capture message request. (p.127); `device_checkpoints[4]` = MUST: In either case, when pumping is complete, the values entered for all prompts must be sent with the capture message. (p.138) | Device checkpoints about fuel pumping in a non-petroleum test |
| TC-039913 | - | test case record [test_cases.jsonl line 148](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L148) → `spec_basis[2]` | `spec_basis[2]` = p.402: "The Sequence Number must be the Sequence Number contained in the original transaction." | The quote cited for p.402 is on a different page |
| TC-039914 | - | test case record [test_cases.jsonl line 149](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L149) → `spec_basis[2]` | `spec_basis[2]` = p.40: "Plan v7 W2: a follow-on of the same transaction is sent by the same device as its original (stated for TOR p.40 'A TOR must be sent from the same device as the original financial transaction request'; applied to every follow-on for matching)." | The quote cited for p.40 is not in ATL105 |
| TC-039915 | - | test case record [test_cases.jsonl line 150](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L150) → `spec_basis[2]` | `spec_basis[2]` = p.38: "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR." | The quote cited for p.38 is on a different page |
| TC-039916 | - | test case record [test_cases.jsonl line 151](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151) → `spec_basis[0]` | `spec_basis[0]` = p.312: "This data is applicable only to subsequent transactions (Example: Reversal, Completion, Cancellation)." | Cited Segment 153 rule is for Table 006 on subsequent transactions |

### Expected behavior

- Drop checkpoints that do not apply to this merchant.
- `spec_basis[2].page` = `84` instead of `402` - the quoted text itself is right.
- Quote the text exactly as it appears: "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR.".
- Cite the Segment 153 layout (p.310) as the basis.

### Why it matters

- 2 device checkpoint(s) come from the Petroleum Industry Processing Specification (pumping, capture after pumping), but this test runs at MERCHANT 01 (POS PLACEHOLDER) (GENERAL).
- The test case quotes "The Sequence Number must be the Sequence Number contained in the original transaction." for p.402. Found on a different page, not the cited one (p.84, p.89, p.98).
- The test case quotes "Plan v7 W2: a follow-on of the same transaction is sent by the same device as its original (stated for TOR p.40 'A TOR must be sent from the same device as the original financial transaction request'; applied to every follow-on for matching)." for p.40. Not found in ATL105.
- The test case quotes "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR." for p.38. Found on a different page, not the cited one (p.40, p.45).
- The quote is the description of Network Token PAN Indicator (Table 006), which applies only to subsequent transactions. This test sends an original transaction and does not send Table 006.

**BA explanation:** "Some quotes the generator wrote into the test cases cite the wrong page or are not ATL105 text. They do not change the request, but a reviewer following the citation will not find the rule."

### Acceptance criteria

- Every spec_basis quote is ATL105 text, word for word, on the cited page and about this transaction.

**Specification reference (ATL105, word for word):**

- Not ATL105 - these checkpoints quote the BUYPASS Petroleum Industry Processing Specification (PIPS).
- "The Sequence Number must be the Sequence Number contained in the original transaction." is on PDF p.84 (printed 10-5), PDF p.89 (printed 10-10), PDF p.98 (printed 10-19)
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR." ([ATL105 text line 2181](../specifications/ATL105/docs/specs/extracted_text_new.txt#L2181))
- "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR." is on PDF p.40 (printed 2-5), PDF p.45 (printed 2-10)
- Section 12.38 Network Token Data Request Segment, PDF p.312 (printed 12-95): "This data is applicable only to subsequent transactions (Example: Reversal, Completion, Cancellation)." ([ATL105 text line 18141](../specifications/ATL105/docs/specs/extracted_text_new.txt#L18141))
- Section 12.38 Network Token Data Request Segment, PDF p.310 (printed 12-93): "All or some of the Network Token sub-segment data is specified below. Data is in <tag><len><data> format." ([ATL105 text line 18057](../specifications/ATL105/docs/specs/extracted_text_new.txt#L18057))

<a id="not-defects"></a>

## Not defects (shown so they are not raised again)

| Test case | Kind | Finding | Actual | Where | Why it is correct |
|---|---|---|---|---|---|
| TC-039913 | Information | Join Account Number and expiry with '=' in the wire message | `5187270512804249 / 2809` | [TD-TC-039913.step2.reversal.json line 14](mastercard_credit_new_16_11/test_data/TD-TC-039913.step2.reversal.json#L14), [TD-TC-039913.step2.reversal.json line 15](mastercard_credit_new_16_11/test_data/TD-TC-039913.step2.reversal.json#L15) → `AccountNumber, CardDiscretionaryBlockData` | The message builder sends 5187270512804249=2809. No change in the test data. |
| TC-039913 | Intended violation of a negative test (correct) | Step 2: same sequence number as the original - check failed | `000002` | [TD-TC-039913.step2.reversal.json line 22](mastercard_credit_new_16_11/test_data/TD-TC-039913.step2.reversal.json#L22) → `Standard Message Data Segment → SequenceNumber` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `399131`). |
| TC-039914 | Information | Join Account Number and expiry with '=' in the wire message | `5189638922468029 / 3004` | [TD-TC-039914.step2.reversal.json line 14](mastercard_credit_new_16_11/test_data/TD-TC-039914.step2.reversal.json#L14), [TD-TC-039914.step2.reversal.json line 15](mastercard_credit_new_16_11/test_data/TD-TC-039914.step2.reversal.json#L15) → `AccountNumber, CardDiscretionaryBlockData` | The message builder sends 5189638922468029=3004. No change in the test data. |
| TC-039914 | Intended violation of a negative test (correct) | Step 2: same terminal as the original - check failed | `VS24671841985066391107` | [TD-TC-039914.step2.reversal.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-039914.step2.reversal.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `JX72948216050257001`). |
| TC-039915 | Information | Join Account Number and expiry with '=' in the wire message | `5144277772726279 / 2909` | [TD-TC-039915.step2.timeout-reversal.json line 14](mastercard_credit_new_16_11/test_data/TD-TC-039915.step2.timeout-reversal.json#L14), [TD-TC-039915.step2.timeout-reversal.json line 15](mastercard_credit_new_16_11/test_data/TD-TC-039915.step2.timeout-reversal.json#L15) → `AccountNumber, CardDiscretionaryBlockData` | The message builder sends 5144277772726279=2909. No change in the test data. |
| TC-039915 | Intended violation of a negative test (correct) | Step 2: same terminal as the original - check failed | `QL66203054504417704598` | [TD-TC-039915.step2.timeout-reversal.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-039915.step2.timeout-reversal.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `JX72948216050257001`). |

<a id="test-data"></a>

## Test data - actual and expected, per test case

For each test case: its findings (actual value, where it is, what it should be), the test data as delivered (click to expand) and the expected test data - the same file with every change; each changed line ends with a `//` comment naming the defect id. Values in `<...>` must come from the client, BUYPASS or the previous response.

<a id="tc-039912"></a>

### TC-039912 (BR1_TS5_TC1) - Happy path: Purchase/Capture approved

**Test name:** Manual / Keyed Purchase/Capture → approved. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`.

**Status:** `ready` · **AI result:** BLOCKED - Only after the client / run-time values are filled in · **Test case record:** [test_cases.jsonl line 147](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L147) · **Every field against ATL105:** [Result_MD_Folder/TC-039912.md](Result_MD_Folder/TC-039912.md) · **Step-by-step guide:** [TC-039912-Manual-Validation.md](Manual%20Validation/TC-039912-Manual-Validation.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 147](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L147) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-04 | Device checkpoints about fuel pumping in a non-petroleum test | Citation issue | `device_checkpoints[3]` = MUST: When pumping is complete, the device must provide an Invoice number, as well as all prompts for which the device has already captured responses, with the Capture message request. (p.127); `device_checkpoints[4]` = MUST: In either case, when pumping is complete, the values entered for all prompts must be sent with the capture message. (p.138) | test case record [test_cases.jsonl line 147](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L147) → `device_checkpoints[3]`, `device_checkpoints[4]` | Drop checkpoints that do not apply to this merchant. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-039912.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-039912.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |

<details><summary>Test data - actual, step 1: TD-TC-039912.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "078",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5100586947757353",
      "CardDiscretionaryBlockData": "0730",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "399121",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-039912.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "078",  // I-01: recalculate: 059 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5100586947757353",
      "CardDiscretionaryBlockData": "0730",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "399121",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    }
  }
}
```

</details>

<a id="tc-039913"></a>

### TC-039913 (BR1_TS5_TC2) - Single rule broken: Purchase/Capture -> Purchase Reversal with Sequence Number not matching the original -> declined

**Test name:** Manual / Keyed Purchase/Capture, then Purchase Reversal with Sequence Number differe… → declined (to confirm). **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`; step 2 sends `8020` (Purchase reversal), expected Response Code `1`.

**Status:** `ready` · **AI result:** BLOCKED - Only after the client / run-time values are filled in · **Test case record:** [test_cases.jsonl line 148](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L148) · **Every field against ATL105:** [Result_MD_Folder/TC-039913.md](Result_MD_Folder/TC-039913.md) · **Step-by-step guide:** [TC-039913-Manual-Validation.md](Manual%20Validation/TC-039913-Manual-Validation.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 148](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L148) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-02 | The quote cited for p.402 is on a different page | Citation issue | `spec_basis[2]` = p.402: "The Sequence Number must be the Sequence Number contained in the original transaction." | test case record [test_cases.jsonl line 148](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L148) → `spec_basis[2]` | `spec_basis[2].page` = `84` instead of `402` - the quoted text itself is right. |
| I-02 | Approval Number holds instruction text, not a value | Awaiting client input / run-time value | `<ApprovalNumber from step 1 response>` | [TD-TC-039913.step2.reversal.json line 23](mastercard_credit_new_16_11/test_data/TD-TC-039913.step2.reversal.json#L23) → `Standard Message Data Segment → ApprovalNumber` | At run time the Approval Number returned in the step 1 response replaces this text (the test case's own example value is `7IYI8R`). |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-039913.step1.purchase.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-039913.step1.purchase.json#L9), [TD-TC-039913.step2.reversal.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-039913.step2.reversal.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| N-01 | Step 2: same sequence number as the original - check failed | Intended violation of a negative test (correct) | `000002` | [TD-TC-039913.step2.reversal.json line 22](mastercard_credit_new_16_11/test_data/TD-TC-039913.step2.reversal.json#L22) → `Standard Message Data Segment → SequenceNumber` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `399131`). |
| INFO | Join Account Number and expiry with '=' in the wire message | Information | `5187270512804249 / 2809` | [TD-TC-039913.step2.reversal.json line 14](mastercard_credit_new_16_11/test_data/TD-TC-039913.step2.reversal.json#L14), [TD-TC-039913.step2.reversal.json line 15](mastercard_credit_new_16_11/test_data/TD-TC-039913.step2.reversal.json#L15) → `AccountNumber, CardDiscretionaryBlockData` | The message builder sends 5187270512804249=2809. No change in the test data. |

<details><summary>Test data - actual, step 1: TD-TC-039913.step1.purchase.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "078",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5187270512804249",
      "CardDiscretionaryBlockData": "0928",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "399131",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-039913.step1.purchase.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "078",  // I-01: recalculate: 059 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5187270512804249",
      "CardDiscretionaryBlockData": "0928",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "399131",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    }
  }
}
```

</details>

<details><summary>Test data - actual, step 2: TD-TC-039913.step2.reversal.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "084",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "8",
        "CardType": "020"
      },
      "AccountNumber": "5187270512804249",
      "CardDiscretionaryBlockData": "2809",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "000002",
      "ApprovalNumber": "<ApprovalNumber from step 1 response>",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 2: TD-TC-039913.step2.reversal.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "084",  // I-01: recalculate: 065 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "8",
        "CardType": "020"
      },
      "AccountNumber": "5187270512804249",
      "CardDiscretionaryBlockData": "2809",  // INFO: the wire message sends AccountNumber '=' this value: 5187270512804249=2809
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "000002",  // N-01: different from step 1 ('399131') on purpose - the rule this negative test breaks; keep
      "ApprovalNumber": "7IYI8R",  // I-02: filled in at run time from the step 1 response - '7IYI8R' is the test case's example
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    }
  }
}
```

</details>

<a id="tc-039914"></a>

### TC-039914 (BR1_TS5_TC3) - Single rule broken: Purchase/Capture -> Purchase Reversal with Terminal Identifier not matching the original -> declined

**Test name:** Manual / Keyed Purchase/Capture, then Purchase Reversal with Terminal Identifier dif… → declined (to confirm). **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`; step 2 sends `8020` (Purchase reversal), expected Response Code `1`.

**Status:** `ready` · **AI result:** BLOCKED - Only after the client / run-time values are filled in · **Test case record:** [test_cases.jsonl line 149](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L149) · **Every field against ATL105:** [Result_MD_Folder/TC-039914.md](Result_MD_Folder/TC-039914.md) · **Step-by-step guide:** [TC-039914-Manual-Validation.md](Manual%20Validation/TC-039914-Manual-Validation.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 149](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L149) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-01 | The quote cited for p.40 is not in ATL105 | Citation issue | `spec_basis[2]` = p.40: "Plan v7 W2: a follow-on of the same transaction is sent by the same device as its original (stated for TOR p.40 'A TOR must be sent from the same device as the original financial transaction request'; applied to every follow-on for matching)." | test case record [test_cases.jsonl line 149](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L149) → `spec_basis[2]` | Quote the text exactly as it appears: "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR.". |
| I-02 | Approval Number holds instruction text, not a value | Awaiting client input / run-time value | `<ApprovalNumber from step 1 response>` | [TD-TC-039914.step2.reversal.json line 23](mastercard_credit_new_16_11/test_data/TD-TC-039914.step2.reversal.json#L23) → `Standard Message Data Segment → ApprovalNumber` | At run time the Approval Number returned in the step 1 response replaces this text (the test case's own example value is `KJXSG5`). |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-039914.step1.purchase.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-039914.step1.purchase.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| C-07 | The 'different terminal' is a generated value not set up at BUYPASS | To confirm with BUYPASS / SME | `VS24671841985066391107` | [TD-TC-039914.step2.reversal.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-039914.step2.reversal.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Use a second real terminal of the same merchant. |
| N-01 | Step 2: same terminal as the original - check failed | Intended violation of a negative test (correct) | `VS24671841985066391107` | [TD-TC-039914.step2.reversal.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-039914.step2.reversal.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `JX72948216050257001`). |
| INFO | Join Account Number and expiry with '=' in the wire message | Information | `5189638922468029 / 3004` | [TD-TC-039914.step2.reversal.json line 14](mastercard_credit_new_16_11/test_data/TD-TC-039914.step2.reversal.json#L14), [TD-TC-039914.step2.reversal.json line 15](mastercard_credit_new_16_11/test_data/TD-TC-039914.step2.reversal.json#L15) → `AccountNumber, CardDiscretionaryBlockData` | The message builder sends 5189638922468029=3004. No change in the test data. |

<details><summary>Test data - actual, step 1: TD-TC-039914.step1.purchase.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "078",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5189638922468029",
      "CardDiscretionaryBlockData": "0430",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "399141",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-039914.step1.purchase.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "078",  // I-01: recalculate: 059 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5189638922468029",
      "CardDiscretionaryBlockData": "0430",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "399141",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    }
  }
}
```

</details>

<details><summary>Test data - actual, step 2: TD-TC-039914.step2.reversal.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "087",
      "InformationByte": "0",
      "TerminalIdentifier": "VS24671841985066391107",
      "PromptCode": {
        "TransactionType": "8",
        "CardType": "020"
      },
      "AccountNumber": "5189638922468029",
      "CardDiscretionaryBlockData": "3004",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "399141",
      "ApprovalNumber": "<ApprovalNumber from step 1 response>",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 2: TD-TC-039914.step2.reversal.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "087",  // C-07: recalculate: 065 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<second Terminal Identifier of the same merchant, assigned by BUYPASS>",  // N-01: different from step 1 ('JX72948216050257001') on purpose - the rule this negative test breaks; keep; C-07: a second real terminal of the same merchant (still different from step 1)
      "PromptCode": {
        "TransactionType": "8",
        "CardType": "020"
      },
      "AccountNumber": "5189638922468029",
      "CardDiscretionaryBlockData": "3004",  // INFO: the wire message sends AccountNumber '=' this value: 5189638922468029=3004
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "399141",
      "ApprovalNumber": "KJXSG5",  // I-02: filled in at run time from the step 1 response - 'KJXSG5' is the test case's example
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    }
  }
}
```

</details>

<a id="tc-039915"></a>

### TC-039915 (BR1_TS5_TC4) - Single rule broken: Purchase/Capture -> Time-out Reversal with Terminal Identifier not matching the original -> declined

**Test name:** Manual / Keyed Purchase/Capture, then Time-out Reversal with Terminal Identifier dif… → declined (to confirm). **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`; step 2 sends `Z020` (Time-out Reversal), expected Response Code `1`.

**Status:** `ready` · **AI result:** BLOCKED - Only after the client / run-time values are filled in · **Test case record:** [test_cases.jsonl line 150](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L150) · **Every field against ATL105:** [Result_MD_Folder/TC-039915.md](Result_MD_Folder/TC-039915.md) · **Step-by-step guide:** [TC-039915-Manual-Validation.md](Manual%20Validation/TC-039915-Manual-Validation.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 150](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L150) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-02 | The quote cited for p.38 is on a different page | Citation issue | `spec_basis[2]` = p.38: "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR." | test case record [test_cases.jsonl line 150](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L150) → `spec_basis[2]` | `spec_basis[2].page` = `40` instead of `38` - the quoted text itself is right. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-039915.step1.purchase.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-039915.step1.purchase.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| C-06 | Step 2 sends a Time-out Reversal although step 1 is expected to be answered | To confirm with BUYPASS / SME | `tx_legs[0].expect` = {"tier": "ASSERT", "page": 403, "response_code": "0", "note": "Approved-Purchase/Capture"} and `harness` = `null` (no fault injection) | test case record [test_cases.jsonl line 150](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L150) → `tx_legs[0].expect` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). Step 1 then expects no response (instead of Response Code 0), and the device sends the Time-out Reversal after the 30-second response interval. |
| C-07 | The 'different terminal' is a generated value not set up at BUYPASS | To confirm with BUYPASS / SME | `QL66203054504417704598` | [TD-TC-039915.step2.timeout-reversal.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-039915.step2.timeout-reversal.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Use a second real terminal of the same merchant. |
| N-01 | Step 2: same terminal as the original - check failed | Intended violation of a negative test (correct) | `QL66203054504417704598` | [TD-TC-039915.step2.timeout-reversal.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-039915.step2.timeout-reversal.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `JX72948216050257001`). |
| INFO | Join Account Number and expiry with '=' in the wire message | Information | `5144277772726279 / 2909` | [TD-TC-039915.step2.timeout-reversal.json line 14](mastercard_credit_new_16_11/test_data/TD-TC-039915.step2.timeout-reversal.json#L14), [TD-TC-039915.step2.timeout-reversal.json line 15](mastercard_credit_new_16_11/test_data/TD-TC-039915.step2.timeout-reversal.json#L15) → `AccountNumber, CardDiscretionaryBlockData` | The message builder sends 5144277772726279=2909. No change in the test data. |

<details><summary>Test data - actual, step 1: TD-TC-039915.step1.purchase.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "078",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5144277772726279",
      "CardDiscretionaryBlockData": "0929",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "399151",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-039915.step1.purchase.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "078",  // I-01: recalculate: 059 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5144277772726279",
      "CardDiscretionaryBlockData": "0929",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "399151",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    }
  }
}
```

</details>

<details><summary>Test data - actual, step 2: TD-TC-039915.step2.timeout-reversal.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "081",
      "InformationByte": "0",
      "TerminalIdentifier": "QL66203054504417704598",
      "PromptCode": {
        "TransactionType": "Z",
        "CardType": "020"
      },
      "AccountNumber": "5144277772726279",
      "CardDiscretionaryBlockData": "2909",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "399151",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 2: TD-TC-039915.step2.timeout-reversal.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "081",  // C-07: recalculate: 059 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<second Terminal Identifier of the same merchant, assigned by BUYPASS>",  // N-01: different from step 1 ('JX72948216050257001') on purpose - the rule this negative test breaks; keep; C-07: a second real terminal of the same merchant (still different from step 1)
      "PromptCode": {
        "TransactionType": "Z",
        "CardType": "020"
      },
      "AccountNumber": "5144277772726279",
      "CardDiscretionaryBlockData": "2909",  // INFO: the wire message sends AccountNumber '=' this value: 5144277772726279=2909
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "399151",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    }
  }
}
```

</details>

<a id="tc-039916"></a>

### TC-039916 (BR1_TS5_TC5) - Decision table: Authorization Only with segment 153 sent (the rule's condition holds)

**Test name:** Manual / Keyed Purchase/Capture with Network Token Data Request sent → approved. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`.

**Status:** `ready` · **AI result:** BLOCKED - Only after the client / run-time values are filled in · **Test case record:** [test_cases.jsonl line 151](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151) · **Every field against ATL105:** [Result_MD_Folder/TC-039916.md](Result_MD_Folder/TC-039916.md) · **Step-by-step guide:** [TC-039916-Manual-Validation.md](Manual%20Validation/TC-039916-Manual-Validation.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 151](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| C-05 | The test objective names a different transaction than the one sent | Test case issue to correct | `test_objective` = Decision table: Authorization Only with segment 153 sent (the rule's condition holds) | test case record [test_cases.jsonl line 151](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151) → `test_objective` | `test_objective` = `Decision table: Purchase/Capture with segment 153 sent (the rule's condition holds)` - Correct the objective to POS Purchase/Capture. |
| Q-04 | Cited Segment 153 rule is for Table 006 on subsequent transactions | Citation issue | `spec_basis[0]` = p.312: "This data is applicable only to subsequent transactions (Example: Reversal, Completion, Cancellation)." | test case record [test_cases.jsonl line 151](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151) → `spec_basis[0]` | Cite the Segment 153 layout (p.310) as the basis. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-039916.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-039916.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| I-06 | Network Token Data Request: content is synthesized, not client data | Awaiting client input / run-time value | `NetworkTokenData` = `00101926686754225094144590020043004004001Y`: table 001 Network Token `2668675422509414459`; table 002 Network Token Expiration Date `3004`; table 004 Network Token Input Indicator `Y` - all generated by the test generator (method `spec_tlv_composite`) | [TD-TC-039916.json line 48](mastercard_credit_new_16_11/test_data/TD-TC-039916.json#L48) → `Network Token Data Request` | Client to supply the merchant's network-token entitlement and a real provisioned network token; keep the test case out of 'ready' until then. |
| S-03 | Network Token Data Request: length depends on an unclear Field Separator rule | To confirm with BUYPASS / SME | `051` | [TD-TC-039916.json line 50](mastercard_credit_new_16_11/test_data/TD-TC-039916.json#L50) → `Network Token Data Request → SegmentLengthIndicator` | Confirm the Field Separator rule of this segment with BUYPASS. |
| S-04 | Network Token Input Indicator 'Y' while Element 2 is a card number | To confirm with BUYPASS / SME | `Y` | [TD-TC-039916.json line 51](mastercard_credit_new_16_11/test_data/TD-TC-039916.json#L51) → `Network Token Data Request → NetworkTokenData (table 004)` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |

<details><summary>Test data - actual, step 1: TD-TC-039916.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "078",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5100586947757353",
      "CardDiscretionaryBlockData": "0730",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "399161",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "Network Token Data Request": {
      "SegmentType": "153",
      "SegmentLengthIndicator": "051",
      "NetworkTokenData": "00101926686754225094144590020043004004001Y"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-039916.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "078",  // I-01: recalculate: 059 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5100586947757353",
      "CardDiscretionaryBlockData": "0730",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "399161",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "Network Token Data Request": {
      "SegmentType": "153",
      "SegmentLengthIndicator": "051",  // S-03: 051 = a Field Separator after every field; other readings of ATL105 give 049, 050 - confirm with BUYPASS
      "NetworkTokenData": "001nnn<network token>002004<token expiry YYMM>004001<Y or N>"  // S-04: table 004 'N' (Clear PAN) if Element 2 keeps the card number - confirm with BUYPASS; I-06: real provisioned network token from the client (nnn = its length, up to 019)
    }
  }
}
```

</details>

## How to demonstrate the defects during the BA discussion

1. **The Terminal Identifier is a generated placeholder, yet the test cases are marked ready** - open [TC-039912](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L147) and read the test name and objective, then open [TD-TC-039912.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-039912.json#L9) → `Standard Message Data Segment → TerminalIdentifier`: actual `JX72948216050257001`; expected: Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier.
2. **Time-out Reversal sent although the original request is expected to be answered** - open [TC-039915](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L150) and read the test name and objective, then open test case record [test_cases.jsonl line 150](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L150) → `tx_legs[0].expect`: actual `tx_legs[0].expect` = {"tier": "ASSERT", "page": 403, "response_code": "0", "note": "Approved-Purchase/Capture"} and `harness` = `null` (no fault injection); expected: Make step 1 a fault-injected 'no response' (or a late response the harness ignores). Step 1 then expects no response (instead of Response Code 0), and the device sends the Time-out Reversal after the 30-second response interval.
3. **'Different terminal' negative tests use a terminal BUYPASS does not know** - open [TC-039914](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L149) and read the test name and objective, then open [TD-TC-039914.step2.reversal.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-039914.step2.reversal.json#L9) → `Standard Message Data Segment → TerminalIdentifier`: actual `VS24671841985066391107`; expected: Use a second real terminal of the same merchant.
4. **The test objective names a different transaction than the one sent** - open [TC-039916](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151) and read the test name and objective, then open test case record [test_cases.jsonl line 151](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151) → `test_objective`: actual `test_objective` = Decision table: Authorization Only with segment 153 sent (the rule's condition holds); expected: `test_objective` = `Decision table: Purchase/Capture with segment 153 sent (the rule's condition holds)` - Correct the objective to POS Purchase/Capture.
5. **Network token data (Segment 153) is synthesized, and its indicator and length need confirmation** - open [TC-039916](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151) and read the test name and objective, then open [TD-TC-039916.json line 50](mastercard_credit_new_16_11/test_data/TD-TC-039916.json#L50) → `Network Token Data Request → SegmentLengthIndicator`: actual `051`; expected: Confirm the Field Separator rule of this segment with BUYPASS.
6. **Follow-on values are instruction text to be filled in from the previous response** - open [TC-039913](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L148) and read the test name and objective, then open [TD-TC-039913.step2.reversal.json line 23](mastercard_credit_new_16_11/test_data/TD-TC-039913.step2.reversal.json#L23) → `Standard Message Data Segment → ApprovalNumber`: actual `<ApprovalNumber from step 1 response>`; expected: At run time the Approval Number returned in the step 1 response replaces this text (the test case's own example value is `7IYI8R`).
7. **ATL105 citations in the test cases are on the wrong page, not ATL105 text, or about another context** - open [TC-039912](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L147) and read the test name and objective, then open test case record [test_cases.jsonl line 147](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L147) → `device_checkpoints[3]`, `device_checkpoints[4]`: actual `device_checkpoints[3]` = MUST: When pumping is complete, the device must provide an Invoice number, as well as all prompts for which the device has already captured responses, with the Capture message request. (p.127); `device_checkpoints[4]` = MUST: In either case, when pumping is complete, the values entered for all prompts must be sent with the capture message. (p.138); expected: Drop checkpoints that do not apply to this merchant.

## Summary for BA

**7 defects in 5 of the 5 test cases; 2 of them are high priority.**

- The Terminal Identifier is a generated placeholder, yet the test cases are marked ready - 5 test case(s).
- Time-out Reversal sent although the original request is expected to be answered - 1 test case(s).

Medium and low priority: 'Different terminal' negative tests use a terminal BUYPASS does not know (2); The test objective names a different transaction than the one sent (1); Network token data (Segment 153) is synthesized, and its indicator and length need confirmation (1); Follow-on values are instruction text to be filled in from the previous response (2); ATL105 citations in the test cases are on the wrong page, not ATL105 text, or about another context (5).

The risk is misleading test results and coverage, not a demonstrated host defect: the requests must match the scenario each test claims, and nothing can run before the real client values are supplied.

<a id="appendix"></a>

## Appendix - run files checked, documents and how this sheet was produced

The run's `MANIFEST.json` lists 2,485 files with their SHA-256; 2,426 of them are in the folder (not in the folder: coverage 36, requirements_scenarios 12, test_cases 9, reports 2 - not needed for this validation). Files these test cases use:

| File | SHA-256 | Same as MANIFEST.json |
|---|---|---|
| [test_cases/test_cases.jsonl](mastercard_credit_new_16_11/test_cases/test_cases.jsonl) | `d1a849ea5a922dda…` | yes |
| [test_data/TD-TC-039912.json](mastercard_credit_new_16_11/test_data/TD-TC-039912.json) | `cc517065330b7bd8…` | yes |
| [test_data/TD-TC-039913.step1.purchase.json](mastercard_credit_new_16_11/test_data/TD-TC-039913.step1.purchase.json) | `1407e4991b727739…` | yes |
| [test_data/TD-TC-039913.step2.reversal.json](mastercard_credit_new_16_11/test_data/TD-TC-039913.step2.reversal.json) | `e3a955f12e6138bd…` | yes |
| [test_data/TD-TC-039914.step1.purchase.json](mastercard_credit_new_16_11/test_data/TD-TC-039914.step1.purchase.json) | `52b73f03c63b9f2a…` | yes |
| [test_data/TD-TC-039914.step2.reversal.json](mastercard_credit_new_16_11/test_data/TD-TC-039914.step2.reversal.json) | `8bf205a97a948457…` | yes |
| [test_data/TD-TC-039915.step1.purchase.json](mastercard_credit_new_16_11/test_data/TD-TC-039915.step1.purchase.json) | `10701ff07687fe08…` | yes |
| [test_data/TD-TC-039915.step2.timeout-reversal.json](mastercard_credit_new_16_11/test_data/TD-TC-039915.step2.timeout-reversal.json) | `85a766677384e0f5…` | yes |
| [test_data/TD-TC-039916.json](mastercard_credit_new_16_11/test_data/TD-TC-039916.json) | `6ee15756006efcf9…` | yes |

The other 2,414 test data files of the run were read as well, for the checks across test cases (Sequence Number per terminal, identical requests).

| Document | What it is |
|---|---|
| [Result_MD_Folder/ATL105-TD-Validation-Summary.md](Result_MD_Folder/ATL105-TD-Validation-Summary.md) | Summary: every finding with the ATL105 section, PDF page, printed page and exact text, how to check it by hand and the fix |
| [Result_MD_Folder/TC-039912.md](Result_MD_Folder/TC-039912.md) | BR1_TS5_TC1: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-039913.md](Result_MD_Folder/TC-039913.md) | BR1_TS5_TC2: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-039914.md](Result_MD_Folder/TC-039914.md) | BR1_TS5_TC3: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-039915.md](Result_MD_Folder/TC-039915.md) | BR1_TS5_TC4: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-039916.md](Result_MD_Folder/TC-039916.md) | BR1_TS5_TC5: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Manual Validation/TC-039912-Manual-Validation.md](Manual%20Validation/TC-039912-Manual-Validation.md) | BR1_TS5_TC1: step-by-step manual validation guide with the ATL105 extracts |
| [Manual Validation/TC-039913-Manual-Validation.md](Manual%20Validation/TC-039913-Manual-Validation.md) | BR1_TS5_TC2: step-by-step manual validation guide with the ATL105 extracts |
| [Manual Validation/TC-039914-Manual-Validation.md](Manual%20Validation/TC-039914-Manual-Validation.md) | BR1_TS5_TC3: step-by-step manual validation guide with the ATL105 extracts |
| [Manual Validation/TC-039915-Manual-Validation.md](Manual%20Validation/TC-039915-Manual-Validation.md) | BR1_TS5_TC4: step-by-step manual validation guide with the ATL105 extracts |
| [Manual Validation/TC-039916-Manual-Validation.md](Manual%20Validation/TC-039916-Manual-Validation.md) | BR1_TS5_TC5: step-by-step manual validation guide with the ATL105 extracts |

Produced with (from `core-auth-ai-validation-framework/regression-artifact-validator`): `python scripts/validate-run-and-build-manual-validation.py --run DhanSingh_mastercard_credit_output_16_11\mastercard_credit_new_16_11 --only TC-039912,TC-039913,TC-039914,TC-039915,TC-039916 --package DhanSingh_mastercard_credit_output_16_11 --title "MasterCard Credit (plan v12) - defect explanation for BA review: the 5 manual-validation test cases" --scope "Manual validation of the 5 test cases TC-039912 to TC-039916 (previous ids TC-020803 to TC-020806 and TC-022362; scenario BR1_TS5, manual/keyed purchase) of the run mastercard_credit_new_16_11. Every field of their test data was checked against ATL105 by the validator; this sheet explains each defect the way the BA note DEFECT_EXPLANATION.md does - references, actual evidence, expected behavior, why it is a defect, acceptance criteria and the ATL105 text - followed by the actual and expected test data of every test case."`. The validator ran on all 2,129 test cases of the run; this sheet covers the test cases above.

