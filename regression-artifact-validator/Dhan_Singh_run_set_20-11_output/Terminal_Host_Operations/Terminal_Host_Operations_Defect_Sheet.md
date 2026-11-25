# Run set 20-11, terminal & host operations part: defect sheet for the BA review

## Purpose and scope

Terminal & host operations part of the run set `Dhan_Singh_run_set_20-11`: totals, downloads, key loads, electronic mail, communications test and device behaviour. The sheet shows every finding of this part, with all affected test cases, and the test data of 3 representative test cases.

| Item | Value |
|---|---|
| Run | [terminal_and_host_operations](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/) - MasterCard Credit run of 19 Nov 2026, build `buypass-atl105-mastercard-credit-generated-6181e6cf0e` (only this run was used; its files were checked against the run's own MANIFEST.json - see the appendix) |
| Specification | BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026) - the attached `BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4 (1).pdf` |
| Test cases | [TC-078259](#tc-078259), [TC-078299](#tc-078299), [TC-078269](#tc-078269) |
| AI result | BLOCKED 2 · REVIEW 1 |
| Readiness | 2 `needs_client_input`, 1 `ready` |

- **TC** = test case: what the test says it will exercise (`test_cases.jsonl`, one test case per line).
- **TD** = test data: the request prepared for transmission to the host (`test_data/TD-TC-....json`).
- **DS** = data segment (Segment 100, 111, ...); **on the wire** = actually included in the transmitted request.
- **Element** = ATL105 data element (Chapter 13); **Table** = Variable Information table in Segment 111 (Appendix I).
- **FAIL** = must be fixed; **BLOCKED** = cannot run until a client or run-time value is supplied; **REVIEW** = to correct or to confirm with BUYPASS.

These are composition defects in the delivered test cases and test data, found by reading them against ATL105 - not observed production failures. No host transactions were executed for this review.

## Not checked field by field

32 of the 47 test cases of this part send request types the validator has no trained ATL105 rules for: Proprietary Data Load Request (10), Electronic Mail Request (8), Table Load Request (3), Totals with Proprietary Data Load Request (2), TransArmor Key Load Request (2), CA Public Key File Load Request (2), Communications Test Request (1), Phone Load Request (1), Date and Time Load Request (1), Software Load Request (1), Moneris Key Load Request (1). For these only the test case level was checked (status, expected response, citations, identical requests); their test data must be reviewed against their Chapter 11 / 12 layouts by hand. The defects below come from the Financial / EMV Financial Transaction Requests and Totals Requests of this part, which were checked field by field. Back to the main sheet: [MasterCard_Credit_Defect_Demo_Sheet.md](../MasterCard_Credit_Defect_Demo_Sheet.md).

## Defects in this sheet

**In the run** = test cases of the whole run (47) with the defect; **shown** = test cases of this sheet. Each defect section lists every affected test case of the run.

| Defect | In the run | Shown | Kind | Defect id | Priority |
|---|---:|---:|---|---|---|
| [1. The Terminal Identifier is a generated placeholder, yet the test cases are marked ready](#d1) | 10 | 2 | Awaiting client input / run-time value, Test case issue to correct | I-01, C-03 | High |
| [2. EMV chip data is generated, not captured from a card or simulator](#d2) | 1 | 1 | Awaiting client input / run-time value | I-04 | Medium |
| [3. Totals Request device versions are generated placeholders](#d3) | 9 | 1 | Awaiting client input / run-time value | I-05 | Medium |
| [4. Expected Response Code not stated for a step, or several codes accepted](#d4) | 21 | 1 | Test case issue to correct | C-04 | Medium |
| [5. Same request as another test case (difference only on the device / response side)](#d5) | 13 | 1 | To confirm with BUYPASS / SME | C-08 | Medium |
| [6. ATL105 citations in the test cases are on the wrong page, not ATL105 text, or about another context](#d6) | 47 | 3 | Citation issue | Q-02 | Low |

<a id="d1"></a>

## Defect 1: The Terminal Identifier is a generated placeholder, yet the test cases are marked ready

**Priority:** High · **Kind:** Awaiting client input / run-time value, Test case issue to correct · **Test cases:** 2 shown (10 in the run) · **Defect id:** C-03, I-01

<details><summary>All 10 test cases of the run with this defect (click to expand)</summary>

[TC-078259](#tc-078259), [TC-078260](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L8), [TC-078261](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L9), [TC-078262](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L10), [TC-078263](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L11), [TC-078264](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L12), [TC-078265](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L13), [TC-078266](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L14), [TC-078267](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L15), [TC-078299](#tc-078299)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-078259](#tc-078259) ([definition](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L7)) | `ready` | Totals Request: Totals for the current settlement date and end of day | Totals for the current settlement date and end of day (222222) | [TD-TC-078259.json](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078259.json) |
| [TC-078299](#tc-078299) ([definition](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L47)) | `needs_client_input` | Downloads (Downline Load Request): Transaction response requests a download → device sends a Partial Table Load | Transaction response requests a download -> device sends a Partial Table Load | [TD-TC-078299.step1.purchase.json](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078299.step1.purchase.json), [TD-TC-078299.step2.table-load.json](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078299.step2.table-load.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-078259 | - |  → `Standard Message Data Segment → TerminalIdentifier` | `JX41894821001` | The Terminal Identifier is a placeholder |
| TC-078259 | - | test case record [test_cases.jsonl line 7](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L7) → `status` | `status` = `ready` | Marked 'ready' although the terminal is a placeholder |
| TC-078299 | - | [TD-TC-078299.step1.purchase.json line 9](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078299.step1.purchase.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `JX41894821001` | The Terminal Identifier is a placeholder |

### Expected behavior

- Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 13 (this placeholder) plus the length of the real Terminal Identifier.
- `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied.

### Why it matters

- The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX41894821001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '41' = Oregon (OR), United States.
- The test case status is 'ready', but its Terminal Identifier is still a placeholder.

**BA explanation:** "Every request uses a made-up terminal. BUYPASS only accepts terminals it has set up, so these tests cannot run until the real Terminal ID is supplied - and they should not be 'ready' until then."

### Acceptance criteria

- Client / BUYPASS supply the real Terminal Identifier of the merchant's terminal; every step uses it and the Segment 100 length is recalculated.
- A test case with a placeholder terminal has a status other than 'ready'.
- A regression check flags 'ready' test cases whose merchant is a placeholder.

**Specification reference (ATL105, word for word):**

- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS." ([ATL105 text line 23795](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L23795))
- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code)." ([ATL105 text line 23816](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L23816))
- Appendix D. Valid State Codes, PDF p.515 (printed Appendix D-2): "Oregon OR 41" ([ATL105 text line 28244](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L28244))

**Test data excerpt - [TC-078299](#tc-078299), step 1, `TD-TC-078299.step1.purchase.json` (selected fields, actual then expected):**

```jsonc
// actual - line 7
"SegmentLength": "083",
// expected
"SegmentLength": "083",  // I-01: recalculate: 070 + length of the real Terminal Identifier
// actual - line 9
"TerminalIdentifier": "JX41894821001",
// expected
"TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
```

<a id="d2"></a>

## Defect 2: EMV chip data is generated, not captured from a card or simulator

**Priority:** Medium · **Kind:** Awaiting client input / run-time value · **Test cases:** 1 shown (1 in the run) · **Defect id:** I-04

<details><summary>All 1 test cases of the run with this defect (click to expand)</summary>

[TC-078299](#tc-078299)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-078299](#tc-078299) ([definition](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L47)) | `needs_client_input` | Downloads (Downline Load Request): Transaction response requests a download → device sends a Partial Table Load | Transaction response requests a download -> device sends a Partial Table Load | [TD-TC-078299.step1.purchase.json](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078299.step1.purchase.json), [TD-TC-078299.step2.table-load.json](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078299.step2.table-load.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-078299 | - | [TD-TC-078299.step1.purchase.json line 51](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078299.step1.purchase.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | `2012091700000100000102171` | The CA Public Key File Checksum is a sample value |
| TC-078299 | - | [TD-TC-078299.step1.purchase.json line 54](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078299.step1.purchase.json#L54) → `EMV Request Data Segment → EMVChipData` | `9F26=22F91638A4535D60, 9F36=8609, 9F37=C507316F, 95=8000008000` | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders |

### Expected behavior

- Confirm the checksum of the host's current CA Public Key file before execution.
- Produce the chip data with a card simulator / HSM before execution.

### Why it matters

- The format is right (25 digits), but the value must be the checksum received in the device's last CA Public Key File Load; the test case marks it as a configured test-data value.
- ATL105 requires the exact values from the card-to-terminal interface; generated values cannot be authenticated by the issuer.

**BA explanation:** "Cryptogram, counters and terminal results in the chip data are generated values. The issuer cannot validate them, so the test needs chip data from the client's test card or simulator."

### Acceptance criteria

- Element 190 comes from the client's test card or simulator; the CA Public Key checksum comes from the current key load.

**Specification reference (ATL105, word for word):**

- Element 187 CA Public Key File Checksum (Chapter 13), PDF p.477 (printed 13-137): "Once received, the checksum must be used for all subsequent EMV transaction requests from this device." ([ATL105 text line 26381](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L26381))
- Element 190 EMV Chip Data (Chapter 13), PDF p.478 (printed 13-138): "All data elements must contain the exact values that were present on the card to terminal interface." ([ATL105 text line 26438](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L26438))

<a id="d3"></a>

## Defect 3: Totals Request device versions are generated placeholders

**Priority:** Medium · **Kind:** Awaiting client input / run-time value · **Test cases:** 1 shown (9 in the run) · **Defect id:** I-05

<details><summary>All 9 test cases of the run with this defect (click to expand)</summary>

[TC-078259](#tc-078259), [TC-078260](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L8), [TC-078261](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L9), [TC-078262](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L10), [TC-078263](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L11), [TC-078264](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L12), [TC-078265](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L13), [TC-078266](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L14), [TC-078267](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L15)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-078259](#tc-078259) ([definition](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L7)) | `ready` | Totals Request: Totals for the current settlement date and end of day | Totals for the current settlement date and end of day (222222) | [TD-TC-078259.json](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078259.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-078259 | 1 | [TD-TC-078259.json line 5](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078259.json#L5) → `Totals Data Segment` | `FQ4R0WSY, A1R7QXCZ, 6KZUA8XA` | The device version fields of the Totals Request are generated values |

### Expected behavior

- Use the versions of the certified device that runs the test.

### Why it matters

- ATL105 requires the device's hardware, software and firmware versions on a Totals Request. The test case generated random values, so they do not identify a certified device.

### Acceptance criteria

- Client to supply the versions of the certified device.

**Specification reference (ATL105, word for word):**

- Element 43 Hardware Version (Chapter 13), PDF p.386 (printed 13-46): "Fixed length of four or eight alphanumeric characters" ([ATL105 text line 22384](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L22384))
- Element 96 Software Version (Chapter 13), PDF p.414 (printed 13-74): "Fixed length of eight alphanumeric characters" ([ATL105 text line 23657](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L23657))
- Element 39 Firmware Version (Chapter 13), PDF p.384 (printed 13-44): "Fixed length of eight alphanumeric characters" ([ATL105 text line 22307](../../specifications/ATL105/docs/specs/extracted_text_new.txt#L22307))

**Test data excerpt - [TC-078259](#tc-078259), step 1, `TD-TC-078259.json` (selected fields, actual then expected):**

```jsonc
// actual - line 5
"Totals Data Segment": {
// expected
"Totals Data Segment": {  // I-05: Use the versions of the certified device that runs the test.
```

<a id="d4"></a>

## Defect 4: Expected Response Code not stated for a step, or several codes accepted

**Priority:** Medium · **Kind:** Test case issue to correct · **Test cases:** 1 shown (21 in the run) · **Defect id:** C-04

<details><summary>All 21 test cases of the run with this defect (click to expand)</summary>

[TC-078265](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L13), [TC-078266](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L14), [TC-078268](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L16), [TC-078270](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L18), [TC-078271](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L19), [TC-078272](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L20), [TC-078273](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L21), [TC-078278](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L26), [TC-078279](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L27), [TC-078280](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L28), [TC-078281](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L29), [TC-078282](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L30), [TC-078288](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L36), [TC-078290](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L38), [TC-078293](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L41), [TC-078294](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L42), [TC-078295](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L43), [TC-078296](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L44), [TC-078297](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L45), [TC-078298](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L46), [TC-078299](#tc-078299)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-078299](#tc-078299) ([definition](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L47)) | `needs_client_input` | Downloads (Downline Load Request): Transaction response requests a download → device sends a Partial Table Load | Transaction response requests a download -> device sends a Partial Table Load | [TD-TC-078299.step1.purchase.json](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078299.step1.purchase.json), [TD-TC-078299.step2.table-load.json](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078299.step2.table-load.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-078299 | - | test case record [test_cases.jsonl line 47](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L47) → `expected_response.response_code (step 2)` | `expected_response.response_code (step 2)` = `null` | The test case accepts several Response Codes instead of one |

### Expected behavior

- State the one expected Response Code for this transaction type (from BUYPASS or a reference run).

### Why it matters

- The test case accepts any of K = see Element 83; L = see Element 83; M = see Element 83; O = see Element 83; T = see Element 83; Y = see Element 83 for the table load step. Element 83 has a separate approval code for each kind of transaction, so the test should state the one code BUYPASS returns.

### Acceptance criteria

- State the expected Response Code (from BUYPASS or a reference run).

<a id="d5"></a>

## Defect 5: Same request as another test case (difference only on the device / response side)

**Priority:** Medium · **Kind:** To confirm with BUYPASS / SME · **Test cases:** 1 shown (13 in the run) · **Defect id:** C-08

<details><summary>All 13 test cases of the run with this defect (click to expand)</summary>

[TC-078269](#tc-078269), [TC-078274](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L22), [TC-078275](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L23), [TC-078276](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L24), [TC-078277](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L25), [TC-078283](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L31), [TC-078284](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L32), [TC-078285](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L33), [TC-078286](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L34), [TC-078287](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L35), [TC-078289](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L37), [TC-078291](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L39), [TC-078298](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L46)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-078269](#tc-078269) ([definition](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L17)) | `needs_client_input` | Totals with Proprietary Data Load Request: Totals with the device's card table version and host discount data declined | Totals with the device's card table version and host discount data (333333) declined | [TD-TC-078269.json](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078269.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-078269 | 1 | TD-TC-078269.json vs TD-TC-078268.json | `expected 1 vs 5/6/B/D/N/W` | Same request as TC-078268 (BROP_TS3_TC1) |

### Expected behavior

- Check the difference on the device / in the response; optionally use another card.

### Why it matters

- Apart from the Sequence Number the message is identical to TC-078268 (all segments compared). TC-078268 expects 5/6/B/D/N/W; this test expects 1. What makes the two tests different is not in the message sent to BUYPASS.

### Acceptance criteria

- Check the difference on the device or in the response; optionally use another card.

<a id="d6"></a>

## Defect 6: ATL105 citations in the test cases are on the wrong page, not ATL105 text, or about another context

**Priority:** Low · **Kind:** Citation issue · **Test cases:** 3 shown (47 in the run) · **Defect id:** Q-02

<details><summary>All 47 test cases of the run with this defect (click to expand)</summary>

[TC-078253](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L1), [TC-078254](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L2), [TC-078255](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L3), [TC-078256](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L4), [TC-078257](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L5), [TC-078258](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L6), [TC-078259](#tc-078259), [TC-078260](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L8), [TC-078261](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L9), [TC-078262](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L10), [TC-078263](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L11), [TC-078264](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L12), [TC-078265](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L13), [TC-078266](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L14), [TC-078267](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L15), [TC-078268](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L16), [TC-078269](#tc-078269), [TC-078270](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L18), [TC-078271](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L19), [TC-078272](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L20), [TC-078273](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L21), [TC-078274](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L22), [TC-078275](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L23), [TC-078276](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L24), [TC-078277](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L25), [TC-078278](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L26), [TC-078279](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L27), [TC-078280](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L28), [TC-078281](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L29), [TC-078282](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L30), [TC-078283](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L31), [TC-078284](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L32), [TC-078285](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L33), [TC-078286](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L34), [TC-078287](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L35), [TC-078288](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L36), [TC-078289](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L37), [TC-078290](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L38), [TC-078291](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L39), [TC-078292](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L40), [TC-078293](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L41), [TC-078294](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L42), [TC-078295](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L43), [TC-078296](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L44), [TC-078297](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L45), [TC-078298](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L46), [TC-078299](#tc-078299)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-078259](#tc-078259) ([definition](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L7)) | `ready` | Totals Request: Totals for the current settlement date and end of day | Totals for the current settlement date and end of day (222222) | [TD-TC-078259.json](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078259.json) |
| [TC-078299](#tc-078299) ([definition](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L47)) | `needs_client_input` | Downloads (Downline Load Request): Transaction response requests a download → device sends a Partial Table Load | Transaction response requests a download -> device sends a Partial Table Load | [TD-TC-078299.step1.purchase.json](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078299.step1.purchase.json), [TD-TC-078299.step2.table-load.json](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078299.step2.table-load.json) |
| [TC-078269](#tc-078269) ([definition](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L17)) | `needs_client_input` | Totals with Proprietary Data Load Request: Totals with the device's card table version and host discount data declined | Totals with the device's card table version and host discount data (333333) declined | [TD-TC-078269.json](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078269.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-078259 | - | test case record [test_cases.jsonl line 7](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L7) → `spec_basis[5]` | `spec_basis[5]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | The quote cited for p.161 is on the neighbouring page |
| TC-078299 | - | test case record [test_cases.jsonl line 47](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L47) → `spec_basis[4]` | `spec_basis[4]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | The quote cited for p.161 is on the neighbouring page |
| TC-078269 | - | test case record [test_cases.jsonl line 17](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L17) → `spec_basis[5]` | `spec_basis[5]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | The quote cited for p.161 is on the neighbouring page |

### Expected behavior

- Cite p.160 instead of p.161.

### Why it matters

- The test case quotes "The following credit cards are supported for EMV processing: ... MasterCard" for p.161. Found on a neighbouring page, not the cited one (p.160).

**BA explanation:** "Some quotes the generator wrote into the test cases cite the wrong page, give no page, or are not ATL105 text. They do not change the request, but a reviewer following the citation will not find the rule."

### Acceptance criteria

- Every spec_basis quote is ATL105 text, word for word, on the cited page and about this transaction.

<a id="test-data"></a>

## Test data - actual and expected, per test case

For each test case: its findings (actual value, where it is, what it should be), the test data as delivered (click to expand) and the expected test data - the same file with every change; each changed line ends with a `//` comment naming the defect id. Values in `<...>` must come from the client, BUYPASS or the previous response.

<a id="tc-078259"></a>

### TC-078259 (BROP_TS2_TC1) - Totals for the current settlement date and end of day (222222)

**Test name:** Totals Request: Totals for the current settlement date and end of day. **What the test does:** step 1 sends `` (?), expected Response Code 5/6/B/D/N/W.

**Status:** `ready` · **AI result:** BLOCKED - Only after the client / run-time values are filled in · **Test case record:** [test_cases.jsonl line 7](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L7) · **Every field against ATL105:** [Result_MD_Folder/TC-078259.md](Result_MD_Folder/TC-078259.md) · **Step-by-step guide:** [TC-078259-Manual-Validation.md](Manual%20Validation/TC-078259-Manual-Validation.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 7](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L7) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[5]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 7](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L7) → `spec_basis[5]` | Cite p.160 instead of p.161. |
| I-05 | The device version fields of the Totals Request are generated values | Awaiting client input / run-time value | `FQ4R0WSY, A1R7QXCZ, 6KZUA8XA` | [TD-TC-078259.json line 5](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078259.json#L5) → `Totals Data Segment` | Use the versions of the certified device that runs the test. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX41894821001` |  → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 13 (this placeholder) plus the length of the real Terminal Identifier. |

<details><summary>Test data - actual, step 1: TD-TC-078259.json (click to expand)</summary>

```json
{
  "Totals Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "01",
    "Totals Data Segment": {
      "SegmentType": "105",
      "SegmentLength": "084",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": "990",
      "EmployeeNumber": "",
      "Password": "",
      "TotalsDate": "222222",
      "HardwareVersion": "FQ4R0WSY",
      "SoftwareVersion": "A1R7QXCZ",
      "FirmwareVersion": "6KZUA8XA",
      "SequenceNumber": "782591",
      "CurrencyCode": "840",
      "GrandTotal": "00000000"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-078259.json (changed lines end with //)</summary>

```jsonc
{
  "Totals Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "01",
    "Totals Data Segment": {  // I-05: Use the versions of the certified device that runs the test.
      "SegmentType": "105",
      "SegmentLength": "084",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": "990",
      "EmployeeNumber": "",
      "Password": "",
      "TotalsDate": "222222",
      "HardwareVersion": "FQ4R0WSY",
      "SoftwareVersion": "A1R7QXCZ",
      "FirmwareVersion": "6KZUA8XA",
      "SequenceNumber": "782591",
      "CurrencyCode": "840",
      "GrandTotal": "00000000"
    }
  }
}
```

</details>

<a id="tc-078299"></a>

### TC-078299 (BROP_TS9_TC7) - Transaction response requests a download -> device sends a Partial Table Load

**Test name:** Downloads (Downline Load Request): Transaction response requests a download → device sends a Partial Table Load. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`; step 2 sends `` (?), expected Response Code K/L/M/O/T/Y.

**Status:** `needs_client_input` · **AI result:** BLOCKED - Only after the client / run-time values are filled in · **Test case record:** [test_cases.jsonl line 47](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L47) · **Every field against ATL105:** [Result_MD_Folder/TC-078299.md](Result_MD_Folder/TC-078299.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-04 | The test case accepts several Response Codes instead of one | Test case issue to correct | `expected_response.response_code (step 2)` = `null` | test case record [test_cases.jsonl line 47](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L47) → `expected_response.response_code (step 2)` | State the one expected Response Code for this transaction type (from BUYPASS or a reference run). |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[4]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 47](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L47) → `spec_basis[4]` | Cite p.160 instead of p.161. |
| I-04 | The CA Public Key File Checksum is a sample value | Awaiting client input / run-time value | `2012091700000100000102171` | [TD-TC-078299.step1.purchase.json line 51](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078299.step1.purchase.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | Confirm the checksum of the host's current CA Public Key file before execution. |
| I-04 | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders | Awaiting client input / run-time value | `9F26=22F91638A4535D60, 9F36=8609, 9F37=C507316F, 95=8000008000` | [TD-TC-078299.step1.purchase.json line 54](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078299.step1.purchase.json#L54) → `EMV Request Data Segment → EMVChipData` | Produce the chip data with a card simulator / HSM before execution. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX41894821001` | [TD-TC-078299.step1.purchase.json line 9](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078299.step1.purchase.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 13 (this placeholder) plus the length of the real Terminal Identifier. |
| SCOPE | Table Load Request: not checked field by field | Not checked - request type outside the validator's trained ATL105 rules | `Table Load Request` | TD-TC-078299.step2.table-load.json | Check the request against its Chapter 11 layout by hand. |

<details><summary>Test data - actual, step 1: TD-TC-078299.step1.purchase.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5142153881702612",
      "CardDiscretionaryBlockData": "270920182778036",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "782991",
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
          "VariableInformation": "051"
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
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601149C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F260822F91638A4535D609F2701809F3303E0F0C89F34035E03029F3501229F360286099F3704C507316F9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-078299.step1.purchase.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",  // I-01: recalculate: 070 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5142153881702612",
      "CardDiscretionaryBlockData": "270920182778036",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "782991",
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
          "VariableInformation": "051"
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
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601149C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F260822F91638A4535D609F2701809F3303E0F0C89F34035E03029F3501229F360286099F3704C507316F9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details><summary>Test data - actual, step 2: TD-TC-078299.step2.table-load.json (click to expand)</summary>

```json
{
  "Table Load Request": {
    "Table Load Request fields": {
      "InformationByte": "?",
      "TerminalIdentifier": "JX41894821001",
      "LoadType": "P",
      "HardwareVersion": "E6GA18UM",
      "SoftwareVersion": "M0151IE4",
      "FirmwareVersion": "IRM9U0XS"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 2: TD-TC-078299.step2.table-load.json (changed lines end with //)</summary>

```jsonc
{
  "Table Load Request": {
    "Table Load Request fields": {
      "InformationByte": "?",
      "TerminalIdentifier": "JX41894821001",
      "LoadType": "P",
      "HardwareVersion": "E6GA18UM",
      "SoftwareVersion": "M0151IE4",
      "FirmwareVersion": "IRM9U0XS"
    }
  }
}
```

</details>

<a id="tc-078269"></a>

### TC-078269 (BROP_TS3_TC2) - Totals with the device's card table version and host discount data (333333) declined

**Test name:** Totals with Proprietary Data Load Request: Totals with the device's card table version and host discount data declined. **What the test does:** step 1 sends `` (?), expected Response Code `1`.

**Status:** `needs_client_input` · **AI result:** REVIEW - Yes, after the points to confirm are answered · **Test case record:** [test_cases.jsonl line 17](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L17) · **Every field against ATL105:** [Result_MD_Folder/TC-078269.md](Result_MD_Folder/TC-078269.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[5]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 17](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L17) → `spec_basis[5]` | Cite p.160 instead of p.161. |
| C-08 | Same request as TC-078268 (BROP_TS3_TC1) | To confirm with BUYPASS / SME | `expected 1 vs 5/6/B/D/N/W` | TD-TC-078269.json vs TD-TC-078268.json | Check the difference on the device / in the response; optionally use another card. |
| SCOPE | Totals with Proprietary Data Load Request: not checked field by field | Not checked - request type outside the validator's trained ATL105 rules | `Totals with Proprietary Data Load Request` | TD-TC-078269.json | Check the request against its Chapter 11 layout by hand. |

<details><summary>Test data - actual, step 1: TD-TC-078269.json (click to expand)</summary>

```json
{
  "Totals with Proprietary Data Load Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "01",
    "Totals with Proprietary Data Load Data Segment": {
      "SegmentType": "119",
      "SegmentLength": "153",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": "990",
      "EmployeeNumber": "",
      "Password": "",
      "TotalsDate": "333333",
      "HardwareVersion": "7016L2XX",
      "SoftwareVersion": "4GUHK3VA",
      "FirmwareVersion": "6Y77VE22",
      "SequenceNumber": "000001",
      "DeviceCardTableVersion": "58147230599259049266834321020259701",
      "HostDiscountTimestamp": "202506151430",
      "CurrencyCode": "840",
      "GrandTotal": "00000000",
      "CardLabel": "CC  ",
      "CardTypeTotalCount": "00001",
      "CardTypeTotalAmount": "00000000"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-078269.json (changed lines end with //)</summary>

```jsonc
{
  "Totals with Proprietary Data Load Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "01",
    "Totals with Proprietary Data Load Data Segment": {
      "SegmentType": "119",
      "SegmentLength": "153",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": "990",
      "EmployeeNumber": "",
      "Password": "",
      "TotalsDate": "333333",
      "HardwareVersion": "7016L2XX",
      "SoftwareVersion": "4GUHK3VA",
      "FirmwareVersion": "6Y77VE22",
      "SequenceNumber": "000001",
      "DeviceCardTableVersion": "58147230599259049266834321020259701",
      "HostDiscountTimestamp": "202506151430",
      "CurrencyCode": "840",
      "GrandTotal": "00000000",
      "CardLabel": "CC  ",
      "CardTypeTotalCount": "00001",
      "CardTypeTotalAmount": "00000000"
    }
  }
}
```

</details>

## How to demonstrate the defects during the BA discussion

1. **The Terminal Identifier is a generated placeholder, yet the test cases are marked ready** - open [TC-078259](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L7) and read the test name and objective, then open  → `Standard Message Data Segment → TerminalIdentifier`: actual `JX41894821001`; expected: Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 13 (this placeholder) plus the length of the real Terminal Identifier.
2. **EMV chip data is generated, not captured from a card or simulator** - open [TC-078299](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L47) and read the test name and objective, then open [TD-TC-078299.step1.purchase.json line 51](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078299.step1.purchase.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum`: actual `2012091700000100000102171`; expected: Confirm the checksum of the host's current CA Public Key file before execution.
3. **Totals Request device versions are generated placeholders** - open [TC-078259](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L7) and read the test name and objective, then open [TD-TC-078259.json line 5](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078259.json#L5) → `Totals Data Segment`: actual `FQ4R0WSY, A1R7QXCZ, 6KZUA8XA`; expected: Use the versions of the certified device that runs the test.
4. **Expected Response Code not stated for a step, or several codes accepted** - open [TC-078299](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L47) and read the test name and objective, then open test case record [test_cases.jsonl line 47](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L47) → `expected_response.response_code (step 2)`: actual `expected_response.response_code (step 2)` = `null`; expected: State the one expected Response Code for this transaction type (from BUYPASS or a reference run).
5. **Same request as another test case (difference only on the device / response side)** - open [TC-078269](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L17) and read the test name and objective, then open TD-TC-078269.json vs TD-TC-078268.json: actual `expected 1 vs 5/6/B/D/N/W`; expected: Check the difference on the device / in the response; optionally use another card.
6. **ATL105 citations in the test cases are on the wrong page, not ATL105 text, or about another context** - open [TC-078259](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L7) and read the test name and objective, then open test case record [test_cases.jsonl line 7](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl#L7) → `spec_basis[5]`: actual `spec_basis[5]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard"; expected: Cite p.160 instead of p.161.

## Summary for BA

**6 defects in 3 of the 3 test cases; 1 of them are high priority.**

High priority:

- The Terminal Identifier is a generated placeholder, yet the test cases are marked ready - 2 test case(s).

Medium and low priority: EMV chip data is generated, not captured from a card or simulator (1); Totals Request device versions are generated placeholders (1); Expected Response Code not stated for a step, or several codes accepted (1); Same request as another test case (difference only on the device / response side) (1); ATL105 citations in the test cases are on the wrong page, not ATL105 text, or about another context (3).

The risk is misleading test results and coverage, not a demonstrated host defect: the requests must match the scenario each test claims, and nothing can run before the real client values are supplied.

<a id="appendix"></a>

## Appendix - run files checked, documents and how this sheet was produced

The run's `MANIFEST.json` lists 68 files with their SHA-256; 68 of them are in the folder. Files these test cases use:

| File | SHA-256 | Same as MANIFEST.json |
|---|---|---|
| [test_cases/test_cases.jsonl](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_cases/test_cases.jsonl) | `cd1b28c879d43fba…` | yes |
| [test_data/TD-TC-078259.json](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078259.json) | `c300793ea488e2e5…` | yes |
| [test_data/TD-TC-078299.step1.purchase.json](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078299.step1.purchase.json) | `fd5cc061e5f3f4ed…` | yes |
| [test_data/TD-TC-078299.step2.table-load.json](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078299.step2.table-load.json) | `382f572172d12856…` | yes |
| [test_data/TD-TC-078269.json](../../Dhan_Singh_run_set_20-11/terminal_and_host_operations/test_data/TD-TC-078269.json) | `097f566f5ea1a656…` | yes |

The other 38 test data files of the run were read as well, for the checks across test cases (Sequence Number per terminal, identical requests).

| Document | What it is |
|---|---|
| [Result_MD_Folder/ATL105-TD-Validation-Summary.md](Result_MD_Folder/ATL105-TD-Validation-Summary.md) | Summary: every finding with the ATL105 section, PDF page, printed page and exact text, how to check it by hand and the fix |
| [Result_MD_Folder/TC-078259.md](Result_MD_Folder/TC-078259.md) | BROP_TS2_TC1: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-078299.md](Result_MD_Folder/TC-078299.md) | BROP_TS9_TC7: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-078269.md](Result_MD_Folder/TC-078269.md) | BROP_TS3_TC2: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Manual Validation/TC-078259-Manual-Validation.md](Manual%20Validation/TC-078259-Manual-Validation.md) | BROP_TS2_TC1: step-by-step manual validation guide with the ATL105 extracts |

Produced with (from `core-auth-ai-validation-framework/regression-artifact-validator`): `python scripts/validate-run-and-build-manual-validation.py --run "C:\Users\F7OKAGW\OneDrive - Fiserv Corp\Documents\Core_Auth_Updated_BRs\core-auth-ai-validation-framework\regression-artifact-validator\Dhan_Singh_run_set_20-11\terminal_and_host_operations" --package "C:\Users\F7OKAGW\OneDrive - Fiserv Corp\Documents\Core_Auth_Updated_BRs\core-auth-ai-validation-framework\regression-artifact-validator\Dhan_Singh_run_set_20-11_output\Terminal_Host_Operations" --only TC-078259,TC-078299,TC-078269 --guides TC-078259 --sheet Terminal_Host_Operations_Defect_Sheet.md --title "Run set 20-11, terminal & host operations part: defect sheet for the BA review" --scope "Terminal & host operations part of the run set `Dhan_Singh_run_set_20-11`: totals, downloads, key loads, electronic mail, communications test and device behaviour. The sheet shows every finding of this part, with all affected test cases, and the test data of 3 representative test cases." --groups 20-11-parts --intro C:\Users\F7OKAGW\AppData\Local\Temp\atl105_16nov\intro_th.md`. The validator ran on all 47 test cases of the run; this sheet covers the test cases above.

