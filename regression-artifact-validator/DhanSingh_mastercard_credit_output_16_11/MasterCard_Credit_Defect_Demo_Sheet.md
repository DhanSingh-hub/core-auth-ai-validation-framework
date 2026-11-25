# MasterCard Credit (plan v12) - defect demo sheet: 5 manual-validation test cases and the BA defect log (up to 19-11-2026)

## Purpose and scope

One sheet for the demo. It combines (1) the manual validation of TC-039912 to TC-039916 (scenario BR1_TS5, manual/keyed purchase) and (2) every test case of the BA defect log: Table 048 MasterCard Authentication Data claimed but not sent (11 test cases) and Tax Type 1 = GST claimed but not sent, with Segments 102 and 143 missing (8) - AI-team register N17-01; original Authorization Only requests that carry an Approval Number (8) - N17-03; and, raised on 19-11-2026, AVS information expected in the response while Table 003 is not sent (10) and TC-038607, a negative test that leaves out Segment 108 although nothing makes it mandatory. All 43 come from the run mastercard_credit_new_16_11; every field of their test data was checked against ATL105 by the validator, which now detects each of these defect kinds. Each defect is explained the way the BA note does, and the actual and expected test data of every test case follow.

| Item | Value |
|---|---|
| Run | [mastercard_credit_new_16_11](mastercard_credit_new_16_11/) - MasterCard Credit run of 16 Nov 2026 (plan v12), build `buypass-atl105-mastercard-credit-generated-923e8c2eac` (only this run was used; its files were checked against the run's own MANIFEST.json - see the appendix) |
| Specification | BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026) - the attached `BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4 (1).pdf` |
| Test cases | [TC-039912](#tc-039912), [TC-039913](#tc-039913), [TC-039914](#tc-039914), [TC-039915](#tc-039915), [TC-039916](#tc-039916), [TC-040080](#tc-040080), [TC-040194](#tc-040194), [TC-040308](#tc-040308), [TC-040512](#tc-040512), [TC-040623](#tc-040623), [TC-040737](#tc-040737), [TC-040856](#tc-040856), [TC-040974](#tc-040974), [TC-041219](#tc-041219), [TC-041315](#tc-041315), [TC-041606](#tc-041606), [TC-040116](#tc-040116), [TC-040230](#tc-040230), [TC-040346](#tc-040346), [TC-040547](#tc-040547), [TC-040658](#tc-040658), [TC-040776](#tc-040776), [TC-040893](#tc-040893), [TC-041009](#tc-041009), [TC-040126](#tc-040126), [TC-040240](#tc-040240), [TC-040356](#tc-040356), [TC-040557](#tc-040557), [TC-040668](#tc-040668), [TC-040786](#tc-040786), [TC-040903](#tc-040903), [TC-041019](#tc-041019), [TC-040140](#tc-040140), [TC-040254](#tc-040254), [TC-040396](#tc-040396), [TC-040571](#tc-040571), [TC-040683](#tc-040683), [TC-040800](#tc-040800), [TC-040919](#tc-040919), [TC-041032](#tc-041032), [TC-041083](#tc-041083), [TC-041101](#tc-041101), [TC-038607](#tc-038607) |
| AI result | FAIL 38 · BLOCKED 5 |
| Readiness | 35 `ready`, 5 `pending_sme`, 3 `needs_client_input` |

- **TC** = test case: what the test says it will exercise (`test_cases.jsonl`, one test case per line).
- **TD** = test data: the request prepared for transmission to the host (`test_data/TD-TC-....json`).
- **DS** = data segment (Segment 100, 111, ...); **on the wire** = actually included in the transmitted request.
- **Element** = ATL105 data element (Chapter 13); **Table** = Variable Information table in Segment 111 (Appendix I).
- **FAIL** = must be fixed; **BLOCKED** = cannot run until a client or run-time value is supplied; **REVIEW** = to correct or to confirm with BUYPASS.

These are composition defects in the delivered test cases and test data, found by reading them against ATL105 - not observed production failures. No host transactions were executed for this review.

| Defect | Affected test cases | Kind | Defect id | Priority |
|---|---:|---|---|---|
| [1. Table 048 MasterCard Authentication Data is claimed by the test but not sent in the request](#d1) | 11 | Test case defect | C-10 (AI-team register N17-01) | High |
| [2. Tax Type 1 = GST is claimed by the test but not sent in the request](#d2) | 8 | Test case defect | C-10 (AI-team register N17-01) | High |
| [3. Original Authorization Only requests carry an Approval Number and other fields that do not belong to them](#d3) | 8 | Test data defect | D-02 (AI-team register N17-03) | High |
| [4. AVS information expected in the response, but the request sends no AVS data (Table 003)](#d4) | 10 | Test case defect | C-11 (BA review 19-11-2026) | High |
| [5. Negative test leaves out something ATL105 does not require here - the expected decline is not justified](#d5) | 1 | Test case defect | C-02 (BA review 19-11-2026) | High |
| [6. The Terminal Identifier is a generated placeholder, yet the test cases are marked ready](#d6) | 43 | Awaiting client input / run-time value, Test case issue to correct | I-01, C-03 | High |
| [7. Time-out Reversal sent although the original request is expected to be answered](#d7) | 1 | To confirm with BUYPASS / SME | C-06 | High |
| [8. 'Different terminal' negative tests use a terminal BUYPASS does not know](#d8) | 2 | To confirm with BUYPASS / SME | C-07 | Medium |
| [9. The test objective names a different transaction than the one sent](#d9) | 1 | Test case issue to correct | C-05 | Medium |
| [10. Network token data (Segment 153) is synthesized, and its indicator and length need confirmation](#d10) | 1 | To confirm with BUYPASS / SME, Awaiting client input / run-time value | S-03, S-04, I-06 | Medium |
| [11. Follow-on values are instruction text to be filled in from the previous response](#d11) | 2 | Awaiting client input / run-time value | I-02 | Medium |
| [12. EMV chip data is generated, not captured from a card or simulator](#d12) | 11 | Awaiting client input / run-time value | I-04 | Medium |
| [13. Same request as another test case (difference only on the device / response side)](#d13) | 11 | To confirm with BUYPASS / SME | C-08 | Medium |
| [14. Expiry format for stored credentials (entry mode 10) not stated](#d14) | 5 | To confirm with BUYPASS / SME | S-01 | Medium |
| [15. ATL105 citations in the test cases are on the wrong page, not ATL105 text, or about another context](#d15) | 22 | Citation issue | Q-04, Q-02, Q-01, Q-03 | Low |

<a id="d1"></a>

## Defect 1: Table 048 MasterCard Authentication Data is claimed by the test but not sent in the request

**Priority:** High · **Kind:** Test case defect · **Test cases:** 11 · **Defect id:** C-10 (AI-team register N17-01)

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-040080](#tc-040080) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L315)) | `ready` | EMV Contact Chip Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040080.json](mastercard_credit_new_16_11/test_data/TD-TC-040080.json) |
| [TC-040194](#tc-040194) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L429)) | `ready` | EMV Contactless Chip Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040194.json](mastercard_credit_new_16_11/test_data/TD-TC-040194.json) |
| [TC-040308](#tc-040308) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L543)) | `ready` | Magnetic Stripe Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040308.json](mastercard_credit_new_16_11/test_data/TD-TC-040308.json) |
| [TC-040512](#tc-040512) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L747)) | `ready` | Contactless Magnetic Stripe Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040512.json](mastercard_credit_new_16_11/test_data/TD-TC-040512.json) |
| [TC-040623](#tc-040623) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L858)) | `ready` | Manual / Keyed Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040623.json](mastercard_credit_new_16_11/test_data/TD-TC-040623.json) |
| [TC-040737](#tc-040737) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L972)) | `ready` | Chip Fallback Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040737.json](mastercard_credit_new_16_11/test_data/TD-TC-040737.json) |
| [TC-040856](#tc-040856) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1091)) | `ready` | Credential on File Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040856.json](mastercard_credit_new_16_11/test_data/TD-TC-040856.json) |
| [TC-040974](#tc-040974) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1209)) | `pending_sme` | Contactless Mobile Commerce Device Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040974.json](mastercard_credit_new_16_11/test_data/TD-TC-040974.json) |
| [TC-041219](#tc-041219) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1454)) | `needs_client_input` | SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-041219.json](mastercard_credit_new_16_11/test_data/TD-TC-041219.json) |
| [TC-041315](#tc-041315) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1550)) | `needs_client_input` | SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-041315.json](mastercard_credit_new_16_11/test_data/TD-TC-041315.json) |
| [TC-041606](#tc-041606) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1841)) | `needs_client_input` | SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-041606.json](mastercard_credit_new_16_11/test_data/TD-TC-041606.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-040080 | 1 | [TD-TC-040080.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040080.json#L27) → `Variable Information Data Segment` | `Segment 111 tables sent: 005, 030, 031` | The test claims Table 048 MasterCard Authentication Data but the request does not send it |
| TC-040194 | 1 | [TD-TC-040194.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040194.json#L27) → `Variable Information Data Segment` | `Segment 111 tables sent: 005, 030, 031` | The test claims Table 048 MasterCard Authentication Data but the request does not send it |
| TC-040308 | 1 | [TD-TC-040308.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040308.json#L27) → `Variable Information Data Segment` | `Segment 111 tables sent: 005, 030, 031` | The test claims Table 048 MasterCard Authentication Data but the request does not send it |
| TC-040512 | 1 | [TD-TC-040512.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040512.json#L27) → `Variable Information Data Segment` | `Segment 111 tables sent: 005, 030, 031` | The test claims Table 048 MasterCard Authentication Data but the request does not send it |
| TC-040623 | 1 | [TD-TC-040623.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040623.json#L27) → `Variable Information Data Segment` | `Segment 111 tables sent: 005, 030, 031` | The test claims Table 048 MasterCard Authentication Data but the request does not send it |
| TC-040737 | 1 | [TD-TC-040737.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040737.json#L27) → `Variable Information Data Segment` | `Segment 111 tables sent: 005, 030, 031` | The test claims Table 048 MasterCard Authentication Data but the request does not send it |
| TC-040856 | 1 | [TD-TC-040856.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040856.json#L27) → `Variable Information Data Segment` | `Segment 111 tables sent: 005, 030, 031, 032, 049, 056` | The test claims Table 048 MasterCard Authentication Data but the request does not send it |
| TC-040974 | 1 | [TD-TC-040974.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040974.json#L27) → `Variable Information Data Segment` | `Segment 111 tables sent: 005, 030, 031` | The test claims Table 048 MasterCard Authentication Data but the request does not send it |
| TC-041219 | 1 | [TD-TC-041219.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-041219.json#L27) → `Variable Information Data Segment` | `Segment 111 tables sent: 005, 030, 031` | The test claims Table 048 MasterCard Authentication Data but the request does not send it |
| TC-041315 | 1 | [TD-TC-041315.json line 38](mastercard_credit_new_16_11/test_data/TD-TC-041315.json#L38) → `Variable Information Data Segment` | `Segment 111 tables sent: 005, 030, 031` | The test claims Table 048 MasterCard Authentication Data but the request does not send it |
| TC-041606 | 1 | [TD-TC-041606.json line 38](mastercard_credit_new_16_11/test_data/TD-TC-041606.json#L38) → `Variable Information Data Segment` | `Segment 111 tables sent: 005, 030, 031` | The test claims Table 048 MasterCard Authentication Data but the request does not send it |

### Expected behavior

- Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 048 applies to e-commerce transactions authenticated with (EMV) 3-D Secure (Appendix I), so a card-present test must be re-scoped to such a transaction or withdrawn.
- Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 048 applies to e-commerce transactions authenticated with (EMV) 3-D Secure (Appendix I), so confirm this card-not-present test is such a transaction, or re-scope / withdraw it.

### Why this is a defect

- The objective "Happy path: Authorization Only sending Table 048 MasterCard Authentication Data" says the request carries Table 048 MasterCard Authentication Data, but the test data does not contain it and the test case names no missing input (status `ready`). An approval would only prove that the request without Table 048 MasterCard Authentication Data is approved - not that Table 048 MasterCard Authentication Data is handled. The test case even plans the table: tx_legs[0].generated_tables lists Table 048 with value '1', but it was not written into the test data.

**BA explanation:** "The test says it sends Table 048 MasterCard Authentication Data, but the request does not contain it. A pass cannot be counted as coverage of Table 048 MasterCard Authentication Data."

### Acceptance criteria

- The request carries the claimed data with the segments it depends on, and the test asserts it - or the test is re-scoped or withdrawn with the reason written down.
- Missing client data is named as an open item and the test is not marked 'ready'.
- A regression check fails a positive test whose claimed table, segment or value is not in its request.

**Specification reference (ATL105, word for word):**

- Element 111 Variable Information Indicator (Chapter 13), PDF p.422 (printed 13-82): "Fixed length of 3 alphanumeric characters" ([ATL105 text line 24031](../specifications/ATL105/docs/specs/extracted_text_new.txt#L24031))
- Element 111 Variable Information Indicator (Chapter 13), PDF p.424 (printed 13-84): "048 MasterCard Authentication Data" ([ATL105 text line 24111](../specifications/ATL105/docs/specs/extracted_text_new.txt#L24111))
- Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048), PDF p.586 (printed Appendix I-37): "This field is applicable to merchants sending e-Commerce transactions with 3D Secure version or EMV 3D Secure for authentication." ([ATL105 text line 31314](../specifications/ATL105/docs/specs/extracted_text_new.txt#L31314))
- Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048), PDF p.586 (printed Appendix I-37): "Merchant should send the table length as ‘1’ when sending only Program Protocol, and table length should be ‘37’ when they support both Program Protocol and Directory Server Transaction ID." ([ATL105 text line 31308](../specifications/ATL105/docs/specs/extracted_text_new.txt#L31308))
- Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048), PDF p.587 (printed Appendix I-38): "1 = EMV 3-D Secure Version 2.1" ([ATL105 text line 31328](../specifications/ATL105/docs/specs/extracted_text_new.txt#L31328))

**Test data excerpt - [TC-040080](#tc-040080), step 1, `TD-TC-040080.json` (selected fields, actual then expected):**

```jsonc
// actual - line 29
"SegmentLength": "042",
// expected
"SegmentLength": "049",  // C-10: 042 + 7 for the Table 048 entry (indicator 3 + length 3 + 1 character)
// actual - not in the request
// expected
{  // C-10: Table 048 MasterCard Authentication Data, as the test claims; Table 048 is for e-commerce with (EMV) 3-D Secure - re-scope this card-present test or withdraw it
  "VariableInformationIndicator": "048",
  "VariableInformationLength": "001",  // Program Protocol only (037 with the Directory Server Transaction ID)
  "VariableInformation": "1"  // the value the test case itself planned (tx_legs[0].generated_tables): 1 = EMV 3-D Secure Version 2.1
}
```

<a id="d2"></a>

## Defect 2: Tax Type 1 = GST is claimed by the test but not sent in the request

**Priority:** High · **Kind:** Test case defect · **Test cases:** 8 · **Defect id:** C-10 (AI-team register N17-01)

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-040116](#tc-040116) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L351)) | `ready` | EMV Contact Chip Authorization Only with Tax Type 1 = GST → approved | Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds) | [TD-TC-040116.json](mastercard_credit_new_16_11/test_data/TD-TC-040116.json) |
| [TC-040230](#tc-040230) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L465)) | `ready` | EMV Contactless Chip Authorization Only with Tax Type 1 = GST → approved | Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds) | [TD-TC-040230.json](mastercard_credit_new_16_11/test_data/TD-TC-040230.json) |
| [TC-040346](#tc-040346) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L581)) | `ready` | Magnetic Stripe Authorization Only with Tax Type 1 = GST → approved | Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds) | [TD-TC-040346.json](mastercard_credit_new_16_11/test_data/TD-TC-040346.json) |
| [TC-040547](#tc-040547) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L782)) | `ready` | Contactless Magnetic Stripe Authorization Only with Tax Type 1 = GST → approved | Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds) | [TD-TC-040547.json](mastercard_credit_new_16_11/test_data/TD-TC-040547.json) |
| [TC-040658](#tc-040658) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L893)) | `ready` | Manual / Keyed Authorization Only with Tax Type 1 = GST → approved | Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds) | [TD-TC-040658.json](mastercard_credit_new_16_11/test_data/TD-TC-040658.json) |
| [TC-040776](#tc-040776) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1011)) | `ready` | Chip Fallback Authorization Only with Tax Type 1 = GST → approved | Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds) | [TD-TC-040776.json](mastercard_credit_new_16_11/test_data/TD-TC-040776.json) |
| [TC-040893](#tc-040893) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1128)) | `ready` | Credential on File Authorization Only with Tax Type 1 = GST → approved | Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds) | [TD-TC-040893.json](mastercard_credit_new_16_11/test_data/TD-TC-040893.json) |
| [TC-041009](#tc-041009) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1244)) | `pending_sme` | Contactless Mobile Commerce Device Authorization Only with Tax Type 1 = GST → approved | Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds) | [TD-TC-041009.json](mastercard_credit_new_16_11/test_data/TD-TC-041009.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-040116 | 1 | [TD-TC-040116.json line 2](mastercard_credit_new_16_11/test_data/TD-TC-040116.json#L2) → `EMV Financial Transaction Request` | `Tax Type 1: not in the test data; segments sent: 100, 111, 130` | The test claims Tax Type 1 = GST but the request does not send it |
| TC-040230 | 1 | [TD-TC-040230.json line 2](mastercard_credit_new_16_11/test_data/TD-TC-040230.json#L2) → `EMV Financial Transaction Request` | `Tax Type 1: not in the test data; segments sent: 100, 111, 130` | The test claims Tax Type 1 = GST but the request does not send it |
| TC-040346 | 1 | [TD-TC-040346.json line 2](mastercard_credit_new_16_11/test_data/TD-TC-040346.json#L2) → `Financial Transaction Request` | `Tax Type 1: not in the test data; segments sent: 100, 111` | The test claims Tax Type 1 = GST but the request does not send it |
| TC-040547 | 1 | [TD-TC-040547.json line 2](mastercard_credit_new_16_11/test_data/TD-TC-040547.json#L2) → `Financial Transaction Request` | `Tax Type 1: not in the test data; segments sent: 100, 111` | The test claims Tax Type 1 = GST but the request does not send it |
| TC-040658 | 1 | [TD-TC-040658.json line 2](mastercard_credit_new_16_11/test_data/TD-TC-040658.json#L2) → `Financial Transaction Request` | `Tax Type 1: not in the test data; segments sent: 100, 111` | The test claims Tax Type 1 = GST but the request does not send it |
| TC-040776 | 1 | [TD-TC-040776.json line 2](mastercard_credit_new_16_11/test_data/TD-TC-040776.json#L2) → `Financial Transaction Request` | `Tax Type 1: not in the test data; segments sent: 100, 111` | The test claims Tax Type 1 = GST but the request does not send it |
| TC-040893 | 1 | [TD-TC-040893.json line 2](mastercard_credit_new_16_11/test_data/TD-TC-040893.json#L2) → `Financial Transaction Request` | `Tax Type 1: not in the test data; segments sent: 100, 111` | The test claims Tax Type 1 = GST but the request does not send it |
| TC-041009 | 1 | [TD-TC-041009.json line 2](mastercard_credit_new_16_11/test_data/TD-TC-041009.json#L2) → `Financial Transaction Request` | `Tax Type 1: not in the test data; segments sent: 100, 111` | The test claims Tax Type 1 = GST but the request does not send it |

### Expected behavior

- Send Segment 102 (Product Code) and Segment 143 (Tax by Product) with the same product(s) in the same order, the inclusive/exclusive flag (I or E), Tax Type 1 = GST and the tax amount; recalculate the segment count and lengths. GST is a Canadian tax type and Segment 143 is sent only if the account manager instructs it (Section 11.1), so confirm the merchant context - or re-scope / withdraw the test.

### Why this is a defect

- The objective "Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds)" says the request carries Tax Type 1 = GST, but the test data does not contain it and the test case names no missing input (status `ready`). An approval would only prove that the request without Tax Type 1 = GST is approved - not that Tax Type 1 = GST is handled.

**BA explanation:** "The test says it sends Tax Type 1 = GST, but the request does not contain it. A pass cannot be counted as coverage of Tax Type 1 = GST."

### Acceptance criteria

- The request carries the claimed data with the segments it depends on, and the test asserts it - or the test is re-scoped or withdrawn with the reason written down.
- Missing client data is named as an open item and the test is not marked 'ready'.
- A regression check fails a positive test whose claimed table, segment or value is not in its request.

**Specification reference (ATL105, word for word):**

- Section 12.30 Tax by Product Data Segment, PDF p.285 (printed 12-68): "A Product Code Data Segment (Data Segment No. 102) must be present in the transaction." ([ATL105 text line 16612](../specifications/ATL105/docs/specs/extracted_text_new.txt#L16612))
- Section 12.30 Tax by Product Data Segment, PDF p.285 (printed 12-68): "Each entry in the Product Code Data Segment (Data Segment No. 102) should have an entry in this segment too and the entries should be in the same order in both the segments." ([ATL105 text line 16613](../specifications/ATL105/docs/specs/extracted_text_new.txt#L16613))
- Section 12.30 Tax by Product Data Segment, PDF p.285 (printed 12-68): "For Canadian transactions, the valid values for Tax Type are GST (Goods and Services Tax), HST (Harmonized Sales Tax), and PST (Provincial State Tax)." ([ATL105 text line 16623](../specifications/ATL105/docs/specs/extracted_text_new.txt#L16623))
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "143 Tax by Product Data Segment 399 C Contains tax data for each product included in segment 102. This segment has limited applicability and should only be included if instructed by your account manager." ([ATL105 text line 8001](../specifications/ATL105/docs/specs/extracted_text_new.txt#L8001))

**Test data excerpt - [TC-040116](#tc-040116), step 1, `TD-TC-040116.json` (selected fields, actual then expected):**

```jsonc
// actual - line 4
"NumberofSegments": "03",
// expected
"NumberofSegments": "05",  // C-10: + Segments 102 and 143
// actual - not in the request
// expected
"Product Code Data Segment": {  // C-10: Segment 143 needs Segment 102
  "SegmentType": "102",
  "SegmentLength": "029",  // whole segment: type, length, 3 Field Separators and the data with its 2 '\' separators
  "ServiceLevel": "O",  // O = Other/Fuel not present (Element 87)
  "NumberofProducts": "01",
  "ProductCode": "400",  // 400 = General merchandise (Appendix F) - client to confirm
  "UnitofMeasure": "U",  // U = Unit (Element 106)
  "Quantity": "01",  // 1 (leading digit = decimal places)
  "UnitPrice": "21500",  // leading 2 = two decimal places
  "ProductAmount": "1500"  // = Nonfuel Amount
},
"Tax by Product Data Segment": {  // C-10: Tax Type 1 = GST, as the test claims
  "SegmentType": "143",
  "SegmentLength": "020",  // counted like Segment 102 - the one-product Segment 143 examples show 023 for 21 characters; confirm with BUYPASS
  "NumberofProducts": "01",  // same products, same order as Segment 102
  "ProductCode": "400",
  "InclusiveExclusiveforTax1": "I",  // I = tax included in the amount
  "TaxType1": "GST",  // the claimed value
  "TaxAmount1": "71"  // example: 5% GST included in the amount - client to supply
}
```

<a id="d3"></a>

## Defect 3: Original Authorization Only requests carry an Approval Number and other fields that do not belong to them

**Priority:** High · **Kind:** Test data defect · **Test cases:** 8 · **Defect id:** D-02 (AI-team register N17-03)

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-040126](#tc-040126) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L361)) | `ready` | EMV Contact Chip Authorization Only with optional Standard Message Data Segment → approved | Decision table: Authorization Only with optional segment 100 present | [TD-TC-040126.json](mastercard_credit_new_16_11/test_data/TD-TC-040126.json) |
| [TC-040240](#tc-040240) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L475)) | `ready` | EMV Contactless Chip Authorization Only with optional Standard Message Data Segment → approved | Decision table: Authorization Only with optional segment 100 present | [TD-TC-040240.json](mastercard_credit_new_16_11/test_data/TD-TC-040240.json) |
| [TC-040356](#tc-040356) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L591)) | `ready` | Magnetic Stripe Authorization Only with optional Standard Message Data Segment → approved | Decision table: Authorization Only with optional segment 100 present | [TD-TC-040356.json](mastercard_credit_new_16_11/test_data/TD-TC-040356.json) |
| [TC-040557](#tc-040557) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L792)) | `ready` | Contactless Magnetic Stripe Authorization Only with optional Standard Message Data Segment → approved | Decision table: Authorization Only with optional segment 100 present | [TD-TC-040557.json](mastercard_credit_new_16_11/test_data/TD-TC-040557.json) |
| [TC-040668](#tc-040668) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L903)) | `ready` | Manual / Keyed Authorization Only with optional Standard Message Data Segment → approved | Decision table: Authorization Only with optional segment 100 present | [TD-TC-040668.json](mastercard_credit_new_16_11/test_data/TD-TC-040668.json) |
| [TC-040786](#tc-040786) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1021)) | `ready` | Chip Fallback Authorization Only with optional Standard Message Data Segment → approved | Decision table: Authorization Only with optional segment 100 present | [TD-TC-040786.json](mastercard_credit_new_16_11/test_data/TD-TC-040786.json) |
| [TC-040903](#tc-040903) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1138)) | `ready` | Credential on File Authorization Only with optional Standard Message Data Segment → approved | Decision table: Authorization Only with optional segment 100 present | [TD-TC-040903.json](mastercard_credit_new_16_11/test_data/TD-TC-040903.json) |
| [TC-041019](#tc-041019) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1254)) | `pending_sme` | Contactless Mobile Commerce Device Authorization Only with optional Standard Message Data Segment → approved | Decision table: Authorization Only with optional segment 100 present | [TD-TC-041019.json](mastercard_credit_new_16_11/test_data/TD-TC-041019.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-040126 | 1 | [TD-TC-040126.json line 23](mastercard_credit_new_16_11/test_data/TD-TC-040126.json#L23) → `Standard Message Data Segment → ApprovalNumber` | `J38FQA` | Approval Number sent on an original Authorization Only |
| TC-040126 | 1 | [TD-TC-040126.json line 16](mastercard_credit_new_16_11/test_data/TD-TC-040126.json#L16) → `Standard Message Data Segment → EncryptedPINBlockData` | `16` | Encrypted PIN Block Data is not a DUKPT KSN + PIN block |
| TC-040126 | 1 | [TD-TC-040126.json line 24](mastercard_credit_new_16_11/test_data/TD-TC-040126.json#L24), [TD-TC-040126.json line 17](mastercard_credit_new_16_11/test_data/TD-TC-040126.json#L17), [TD-TC-040126.json line 18](mastercard_credit_new_16_11/test_data/TD-TC-040126.json#L18) → `Standard Message Data Segment → LocalDateandLocalTime, PumpLaneNumber, FuelPurchaseAmount` | Local Date and Local Time '0615251430' on a new Authorization Only; Pump/Lane Number '1' at a GENERAL (non-petroleum) merchant; Fuel Purchase Amount '1' at a GENERAL (non-petroleum) merchant | Conditional fields sent although their condition does not apply |
| TC-040240 | 1 | [TD-TC-040240.json line 23](mastercard_credit_new_16_11/test_data/TD-TC-040240.json#L23) → `Standard Message Data Segment → ApprovalNumber` | `6FH3NZ` | Approval Number sent on an original Authorization Only |
| TC-040240 | 1 | [TD-TC-040240.json line 16](mastercard_credit_new_16_11/test_data/TD-TC-040240.json#L16) → `Standard Message Data Segment → EncryptedPINBlockData` | `16` | Encrypted PIN Block Data is not a DUKPT KSN + PIN block |
| TC-040240 | 1 | [TD-TC-040240.json line 24](mastercard_credit_new_16_11/test_data/TD-TC-040240.json#L24), [TD-TC-040240.json line 17](mastercard_credit_new_16_11/test_data/TD-TC-040240.json#L17), [TD-TC-040240.json line 18](mastercard_credit_new_16_11/test_data/TD-TC-040240.json#L18) → `Standard Message Data Segment → LocalDateandLocalTime, PumpLaneNumber, FuelPurchaseAmount` | Local Date and Local Time '0615251430' on a new Authorization Only; Pump/Lane Number '1' at a GENERAL (non-petroleum) merchant; Fuel Purchase Amount '1' at a GENERAL (non-petroleum) merchant | Conditional fields sent although their condition does not apply |
| TC-040356 | 1 | [TD-TC-040356.json line 23](mastercard_credit_new_16_11/test_data/TD-TC-040356.json#L23) → `Standard Message Data Segment → ApprovalNumber` | `C9HTU3` | Approval Number sent on an original Authorization Only |
| TC-040356 | 1 | [TD-TC-040356.json line 16](mastercard_credit_new_16_11/test_data/TD-TC-040356.json#L16) → `Standard Message Data Segment → EncryptedPINBlockData` | `16` | Encrypted PIN Block Data is not a DUKPT KSN + PIN block |
| TC-040356 | 1 | [TD-TC-040356.json line 24](mastercard_credit_new_16_11/test_data/TD-TC-040356.json#L24), [TD-TC-040356.json line 17](mastercard_credit_new_16_11/test_data/TD-TC-040356.json#L17), [TD-TC-040356.json line 18](mastercard_credit_new_16_11/test_data/TD-TC-040356.json#L18) → `Standard Message Data Segment → LocalDateandLocalTime, PumpLaneNumber, FuelPurchaseAmount` | Local Date and Local Time '0615251430' on a new Authorization Only; Pump/Lane Number '1' at a GENERAL (non-petroleum) merchant; Fuel Purchase Amount '1' at a GENERAL (non-petroleum) merchant | Conditional fields sent although their condition does not apply |
| TC-040557 | 1 | [TD-TC-040557.json line 23](mastercard_credit_new_16_11/test_data/TD-TC-040557.json#L23) → `Standard Message Data Segment → ApprovalNumber` | `EMUBI3` | Approval Number sent on an original Authorization Only |
| TC-040557 | 1 | [TD-TC-040557.json line 16](mastercard_credit_new_16_11/test_data/TD-TC-040557.json#L16) → `Standard Message Data Segment → EncryptedPINBlockData` | `16` | Encrypted PIN Block Data is not a DUKPT KSN + PIN block |
| TC-040557 | 1 | [TD-TC-040557.json line 24](mastercard_credit_new_16_11/test_data/TD-TC-040557.json#L24), [TD-TC-040557.json line 17](mastercard_credit_new_16_11/test_data/TD-TC-040557.json#L17), [TD-TC-040557.json line 18](mastercard_credit_new_16_11/test_data/TD-TC-040557.json#L18) → `Standard Message Data Segment → LocalDateandLocalTime, PumpLaneNumber, FuelPurchaseAmount` | Local Date and Local Time '0615251430' on a new Authorization Only; Pump/Lane Number '1' at a GENERAL (non-petroleum) merchant; Fuel Purchase Amount '1' at a GENERAL (non-petroleum) merchant | Conditional fields sent although their condition does not apply |
| TC-040668 | 1 | [TD-TC-040668.json line 23](mastercard_credit_new_16_11/test_data/TD-TC-040668.json#L23) → `Standard Message Data Segment → ApprovalNumber` | `DKJ066` | Approval Number sent on an original Authorization Only |
| TC-040668 | 1 | [TD-TC-040668.json line 16](mastercard_credit_new_16_11/test_data/TD-TC-040668.json#L16) → `Standard Message Data Segment → EncryptedPINBlockData` | `16` | Encrypted PIN Block Data is not a DUKPT KSN + PIN block |
| TC-040668 | 1 | [TD-TC-040668.json line 24](mastercard_credit_new_16_11/test_data/TD-TC-040668.json#L24), [TD-TC-040668.json line 17](mastercard_credit_new_16_11/test_data/TD-TC-040668.json#L17), [TD-TC-040668.json line 18](mastercard_credit_new_16_11/test_data/TD-TC-040668.json#L18) → `Standard Message Data Segment → LocalDateandLocalTime, PumpLaneNumber, FuelPurchaseAmount` | Local Date and Local Time '0615251430' on a new Authorization Only; Pump/Lane Number '1' at a GENERAL (non-petroleum) merchant; Fuel Purchase Amount '1' at a GENERAL (non-petroleum) merchant | Conditional fields sent although their condition does not apply |
| TC-040786 | 1 | [TD-TC-040786.json line 23](mastercard_credit_new_16_11/test_data/TD-TC-040786.json#L23) → `Standard Message Data Segment → ApprovalNumber` | `LR2COZ` | Approval Number sent on an original Authorization Only |
| TC-040786 | 1 | [TD-TC-040786.json line 16](mastercard_credit_new_16_11/test_data/TD-TC-040786.json#L16) → `Standard Message Data Segment → EncryptedPINBlockData` | `16` | Encrypted PIN Block Data is not a DUKPT KSN + PIN block |
| TC-040786 | 1 | [TD-TC-040786.json line 24](mastercard_credit_new_16_11/test_data/TD-TC-040786.json#L24), [TD-TC-040786.json line 17](mastercard_credit_new_16_11/test_data/TD-TC-040786.json#L17), [TD-TC-040786.json line 18](mastercard_credit_new_16_11/test_data/TD-TC-040786.json#L18) → `Standard Message Data Segment → LocalDateandLocalTime, PumpLaneNumber, FuelPurchaseAmount` | Local Date and Local Time '0615251430' on a new Authorization Only; Pump/Lane Number '1' at a GENERAL (non-petroleum) merchant; Fuel Purchase Amount '1' at a GENERAL (non-petroleum) merchant | Conditional fields sent although their condition does not apply |
| TC-040903 | 1 | [TD-TC-040903.json line 23](mastercard_credit_new_16_11/test_data/TD-TC-040903.json#L23) → `Standard Message Data Segment → ApprovalNumber` | `6ANJP4` | Approval Number sent on an original Authorization Only |
| TC-040903 | 1 | [TD-TC-040903.json line 16](mastercard_credit_new_16_11/test_data/TD-TC-040903.json#L16) → `Standard Message Data Segment → EncryptedPINBlockData` | `16` | Encrypted PIN Block Data is not a DUKPT KSN + PIN block |
| TC-040903 | 1 | [TD-TC-040903.json line 24](mastercard_credit_new_16_11/test_data/TD-TC-040903.json#L24), [TD-TC-040903.json line 17](mastercard_credit_new_16_11/test_data/TD-TC-040903.json#L17), [TD-TC-040903.json line 18](mastercard_credit_new_16_11/test_data/TD-TC-040903.json#L18) → `Standard Message Data Segment → LocalDateandLocalTime, PumpLaneNumber, FuelPurchaseAmount` | Local Date and Local Time '0615251430' on a new Authorization Only; Pump/Lane Number '1' at a CARD_NOT_PRESENT-0100 (non-petroleum) merchant; Fuel Purchase Amount '1' at a CARD_NOT_PRESENT-0100 (non-petroleum) merchant | Conditional fields sent although their condition does not apply |
| TC-041019 | 1 | [TD-TC-041019.json line 23](mastercard_credit_new_16_11/test_data/TD-TC-041019.json#L23) → `Standard Message Data Segment → ApprovalNumber` | `MDCNDN` | Approval Number sent on an original Authorization Only |
| TC-041019 | 1 | [TD-TC-041019.json line 16](mastercard_credit_new_16_11/test_data/TD-TC-041019.json#L16) → `Standard Message Data Segment → EncryptedPINBlockData` | `16` | Encrypted PIN Block Data is not a DUKPT KSN + PIN block |
| TC-041019 | 1 | [TD-TC-041019.json line 24](mastercard_credit_new_16_11/test_data/TD-TC-041019.json#L24), [TD-TC-041019.json line 17](mastercard_credit_new_16_11/test_data/TD-TC-041019.json#L17), [TD-TC-041019.json line 18](mastercard_credit_new_16_11/test_data/TD-TC-041019.json#L18) → `Standard Message Data Segment → LocalDateandLocalTime, PumpLaneNumber, FuelPurchaseAmount` | Local Date and Local Time '0615251430' on a new Authorization Only; Pump/Lane Number '1' at a GENERAL (non-petroleum) merchant; Fuel Purchase Amount '1' at a GENERAL (non-petroleum) merchant | Conditional fields sent although their condition does not apply |

### Expected behavior

- (empty) - an original Authorization Only carries no Approval Number
- A real DUKPT KSN + PIN block, or leave it empty for credit.
- Send conditional fields only when their condition holds.

### Why this is a defect

- 'J38FQA' is an Approval Number on an original Authorization Only (Transaction Type 3), which asks for a new approval. ATL105 uses Element 5 to match a follow-on to its original (reversals) and says that in a Purchase Request it makes the purchase preauthorized; it does not say how BUYPASS treats an Authorization Only that carries one, so the request contradicts the scenario it is meant to test.
- Element 33 carries a 16- to 20-byte Key Serial Number plus a 16-byte PIN block (32-36 characters); '16' has 2 characters.
- Local Date and Local Time '0615251430' on a new Authorization Only; Pump/Lane Number '1' at a GENERAL (non-petroleum) merchant; Fuel Purchase Amount '1' at a GENERAL (non-petroleum) merchant.
- Local Date and Local Time '0615251430' on a new Authorization Only; Pump/Lane Number '1' at a CARD_NOT_PRESENT-0100 (non-petroleum) merchant; Fuel Purchase Amount '1' at a CARD_NOT_PRESENT-0100 (non-petroleum) merchant.

**BA explanation:** "We are asking for a new approval while already sending an approval number - plus PIN, fuel, pump and date/time values that do not belong to this transaction or merchant. The test must show a clean new authorization."

### Acceptance criteria

- No Approval Number on an original Authorization Only: the field stays empty (its Field Separator is still sent).
- Reversals and completions keep the Approval Number of their original - do not remove those.
- Local Date and Local Time, Pump/Lane Number, Fuel Purchase Amount and Encrypted PIN Block Data are sent only when their condition holds, and the Segment 100 length is recalculated.
- A regression check rejects a populated Approval Number on an original Authorization Only.

**Specification reference (ATL105, word for word):**

- Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization." ([ATL105 text line 21570](../specifications/ATL105/docs/specs/extracted_text_new.txt#L21570))
- Element 33 Encrypted PIN Block Data (Chapter 13), PDF p.382 (printed 13-42): "For DUKPT PIN encryption, this is a 16- to 20-byte Key Serial Number (KSN) plus a 16-byte PIN block." ([ATL105 text line 22212](../specifications/ATL105/docs/specs/extracted_text_new.txt#L22212))
- Section 12.1 Standard Message Data Segment, PDF p.225 (printed 12-8): "Identifies the local date and local time of the preauthorized transaction." ([ATL105 text line 12991](../specifications/ATL105/docs/specs/extracted_text_new.txt#L12991))

**Test data excerpt - [TC-040126](#tc-040126), step 1, `TD-TC-040126.json` (selected fields, actual then expected):**

```jsonc
// actual - line 7
"SegmentLength": "111",
// expected
"SegmentLength": "111",  // D-02/I-01: recalculate: 072 + length of the real Terminal Identifier
// actual - line 16
"EncryptedPINBlockData": "16",
// expected
"EncryptedPINBlockData": "",  // D-02: empty - '16' is not a DUKPT KSN + PIN block; no online PIN on this credit request
// actual - line 17
"PumpLaneNumber": "1",
// expected
"PumpLaneNumber": "",  // D-02: empty - fuel field at a non-petroleum merchant
// actual - line 18
"FuelPurchaseAmount": "1",
// expected
"FuelPurchaseAmount": "",  // D-02: empty - fuel field at a non-petroleum merchant
// actual - line 23
"ApprovalNumber": "J38FQA",
// expected
"ApprovalNumber": "",  // D-02: empty - an original Authorization Only carries no Approval Number
// actual - line 24
"LocalDateandLocalTime": "0615251430",
// expected
"LocalDateandLocalTime": "",  // D-02: empty - Element 49 is for a preauthorized transaction; this request is not one
```

<a id="d4"></a>

## Defect 4: AVS information expected in the response, but the request sends no AVS data (Table 003)

**Priority:** High · **Kind:** Test case defect · **Test cases:** 10 · **Defect id:** C-11 (BA review 19-11-2026)

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-040140](#tc-040140) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L375)) | `ready` | EMV Contact Chip Authorization Only (AVS or NVS or Both Information returned) → approved | Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned | [TD-TC-040140.json](mastercard_credit_new_16_11/test_data/TD-TC-040140.json) |
| [TC-040254](#tc-040254) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L489)) | `ready` | EMV Contactless Chip Authorization Only (AVS or NVS or Both Information returned) → approved | Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned | [TD-TC-040254.json](mastercard_credit_new_16_11/test_data/TD-TC-040254.json) |
| [TC-040396](#tc-040396) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L631)) | `ready` | Magnetic Stripe Authorization Only (AVS or NVS or Both Information returned) → approved | Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned | [TD-TC-040396.json](mastercard_credit_new_16_11/test_data/TD-TC-040396.json) |
| [TC-040571](#tc-040571) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L806)) | `ready` | Contactless Magnetic Stripe Authorization Only (AVS or NVS or Both Information returned) → approved | Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned | [TD-TC-040571.json](mastercard_credit_new_16_11/test_data/TD-TC-040571.json) |
| [TC-040683](#tc-040683) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L918)) | `ready` | Manual / Keyed Authorization Only (AVS or NVS or Both Information returned) → approved | Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned | [TD-TC-040683.json](mastercard_credit_new_16_11/test_data/TD-TC-040683.json) |
| [TC-040800](#tc-040800) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1035)) | `ready` | Chip Fallback Authorization Only (AVS or NVS or Both Information returned) → approved | Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned | [TD-TC-040800.json](mastercard_credit_new_16_11/test_data/TD-TC-040800.json) |
| [TC-040919](#tc-040919) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1154)) | `ready` | Credential on File Authorization Only (AVS or NVS or Both Information returned) → approved | Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned | [TD-TC-040919.json](mastercard_credit_new_16_11/test_data/TD-TC-040919.json) |
| [TC-041032](#tc-041032) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1267)) | `pending_sme` | Contactless Mobile Commerce Device Authorization Only (AVS or NVS or Both Information returned) → approved | Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned | [TD-TC-041032.json](mastercard_credit_new_16_11/test_data/TD-TC-041032.json) |
| [TC-041083](#tc-041083) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1318)) | `ready` | Manual / Keyed Mail/Phone Authorization Only (AVS or NVS or Both Information returned) → approved | Response / oracle check: Mail/Phone Authorization Only -> AVS or NVS or Both Information returned | [TD-TC-041083.json](mastercard_credit_new_16_11/test_data/TD-TC-041083.json) |
| [TC-041101](#tc-041101) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1336)) | `pending_sme` | Credential on File Mail/Phone Authorization Only (AVS or NVS or Both Information returned) → approved | Response / oracle check: Mail/Phone Authorization Only -> AVS or NVS or Both Information returned | [TD-TC-041101.json](mastercard_credit_new_16_11/test_data/TD-TC-041101.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-040140 | 1 | [TD-TC-040140.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040140.json#L27) → `Variable Information Data Segment` | `Segment 111 tables sent: 005, 030, 031; expected back: Segment 112 Table 003 AVS or NVS or Both Information` | The test expects AVS or NVS or Both Information back, but the request does not send AVS data (Table 003) |
| TC-040254 | 1 | [TD-TC-040254.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040254.json#L27) → `Variable Information Data Segment` | `Segment 111 tables sent: 005, 030, 031; expected back: Segment 112 Table 003 AVS or NVS or Both Information` | The test expects AVS or NVS or Both Information back, but the request does not send AVS data (Table 003) |
| TC-040396 | 1 | [TD-TC-040396.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040396.json#L27) → `Variable Information Data Segment` | `Segment 111 tables sent: 005, 030, 031; expected back: Segment 112 Table 003 AVS or NVS or Both Information` | The test expects AVS or NVS or Both Information back, but the request does not send AVS data (Table 003) |
| TC-040571 | 1 | [TD-TC-040571.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040571.json#L27) → `Variable Information Data Segment` | `Segment 111 tables sent: 005, 030, 031; expected back: Segment 112 Table 003 AVS or NVS or Both Information` | The test expects AVS or NVS or Both Information back, but the request does not send AVS data (Table 003) |
| TC-040683 | 1 | [TD-TC-040683.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040683.json#L27) → `Variable Information Data Segment` | `Segment 111 tables sent: 005, 030, 031; expected back: Segment 112 Table 003 AVS or NVS or Both Information` | The test expects AVS or NVS or Both Information back, but the request does not send AVS data (Table 003) |
| TC-040800 | 1 | [TD-TC-040800.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040800.json#L27) → `Variable Information Data Segment` | `Segment 111 tables sent: 005, 030, 031; expected back: Segment 112 Table 003 AVS or NVS or Both Information` | The test expects AVS or NVS or Both Information back, but the request does not send AVS data (Table 003) |
| TC-040919 | 1 | [TD-TC-040919.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040919.json#L27) → `Variable Information Data Segment` | `Segment 111 tables sent: 005, 030, 031, 032, 049, 056; expected back: Segment 112 Table 003 AVS or NVS or Both Information` | The test expects AVS or NVS or Both Information back, but the request does not send AVS data (Table 003) |
| TC-041032 | 1 | [TD-TC-041032.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-041032.json#L27) → `Variable Information Data Segment` | `Segment 111 tables sent: 005, 030, 031; expected back: Segment 112 Table 003 AVS or NVS or Both Information` | The test expects AVS or NVS or Both Information back, but the request does not send AVS data (Table 003) |
| TC-041083 | 1 | [TD-TC-041083.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-041083.json#L27) → `Variable Information Data Segment` | `Segment 111 tables sent: 005, 030, 031; expected back: Segment 112 Table 003 AVS or NVS or Both Information` | The test expects AVS or NVS or Both Information back, but the request does not send AVS data (Table 003) |
| TC-041101 | 1 | [TD-TC-041101.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-041101.json#L27) → `Variable Information Data Segment` | `Segment 111 tables sent: 005, 030, 031, 032, 049, 056; expected back: Segment 112 Table 003 AVS or NVS or Both Information` | The test expects AVS or NVS or Both Information back, but the request does not send AVS data (Table 003) |

### Expected behavior

- Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111 length and assert the AVS result in Segment 112 Table 003 - or drop the AVS expectation. AVS is used primarily for non-face-to-face transactions (mail/phone order, CAT), so confirm the card-present variants with BUYPASS.

### Why this is a defect

- tx_legs[0].response_tables expects Segment 112 Table 003 AVS or NVS or Both Information in the response (status `ready`). ATL105 says the request initiates this information (Element 116), but the request sends only Segment 111 tables 005, 030, 031 - no Table 003. The host has nothing to verify and nothing to return, so the expected result cannot occur and an approval proves nothing about it.

**BA explanation:** "The test expects the host to send back the AVS (address verification) result, but the request carries no address data. ATL105 says the request initiates the AVS information in the response, so there is nothing to verify and nothing to return - an approval alone proves nothing about AVS."

### Acceptance criteria

- The request sends Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and up to 20 characters of street address, from the client's test card data; the Segment 111 length is recalculated.
- The test asserts the AVS result in Segment 112 Table 003 (AVS+ or AVS- with the issuer's code) - not only Response Code 2.
- AVS is used primarily for mail/phone order and CAT transactions: confirm the card-present variants with BUYPASS, or re-scope them.
- A regression check fails a positive test whose expected response table is initiated by a request table it does not send.

**Specification reference (ATL105, word for word):**

- Element 116 Additional Information Indicator (Chapter 13), PDF p.433 (printed 13-93): "A Financial Transaction Request initiates transmittal of AVS information (003) and CVV information (004) in a Financial Transaction Response." ([ATL105 text line 24543](../specifications/ATL105/docs/specs/extracted_text_new.txt#L24543))
- Section 10.1.3 Address Verification Service, PDF p.86 (printed 10-7): "The device sends a transaction request to BUYPASS containing the AVS information in the Variable Information Data Segment" ([ATL105 text line 4205](../specifications/ATL105/docs/specs/extracted_text_new.txt#L4205))
- Section 10.1.3 Address Verification Service, PDF p.86 (printed 10-7): "The authorizer returns a response containing both the transaction authorization Response Code and the AVS information in the Additional Information Data Segment" ([ATL105 text line 4208](../specifications/ATL105/docs/specs/extracted_text_new.txt#L4208))
- Section 10.1.3 Address Verification Service, PDF p.86 (printed 10-7): "Address Verification Service (AVS) is a credit card authorization tool used for transactions performed primarily in a non-face-to-face environment, such as mail order, phone order, or customer-activated terminal (CAT)." ([ATL105 text line 4201](../specifications/ATL105/docs/specs/extracted_text_new.txt#L4201))
- Element 113 Variable Information (Chapter 13), PDF p.426 (printed 13-86): "When Element No. 111 (Variable Information Indicator) has a value of 003 (AVS indicator), address information (street name, ZIP Code™/ZIP+4®) is included here." ([ATL105 text line 24187](../specifications/ATL105/docs/specs/extracted_text_new.txt#L24187))
- Appendix I. Variable Information Data Layouts - AVS Information (Table 003), PDF p.554 (printed Appendix I-5): "ZIP Code™ (5 characters), followed by 4 spaces, for a total of 9 characters; or" ([ATL105 text line 29761](../specifications/ATL105/docs/specs/extracted_text_new.txt#L29761))
- Appendix I. Variable Information Data Layouts - AVS Information (Table 003), PDF p.554 (printed Appendix I-5): "The remaining 00–20 characters contain street address information." ([ATL105 text line 29764](../specifications/ATL105/docs/specs/extracted_text_new.txt#L29764))
- Appendix K. Additional Information Data Layouts - AVS or NVS or Both Information (Table 003), PDF p.643 (printed Appendix K-4): "Identifies the AVS or NVS or both response from the authorizer." ([ATL105 text line 34943](../specifications/ATL105/docs/specs/extracted_text_new.txt#L34943))
- Element 111 Variable Information Indicator (Chapter 13), PDF p.423 (printed 13-83): "003 Address verification service (AVS) indicator" ([ATL105 text line 24054](../specifications/ATL105/docs/specs/extracted_text_new.txt#L24054))

**Test data excerpt - [TC-040140](#tc-040140), step 1, `TD-TC-040140.json` (selected fields, actual then expected):**

```jsonc
// actual - line 29
"SegmentLength": "042",
// expected
"SegmentLength": "042",  // C-11: recalculate: 042 + 6 + length of the Table 003 data
// actual - not in the request
// expected
{  // C-11: Table 003 AVS Information - the request initiates the AVS result the test expects back
  "VariableInformationIndicator": "003",
  "VariableInformationLength": "<length of the AVS data, up to 029>",
  "VariableInformation": "<ZIP Code + 4 spaces, or ZIP+4><street address, up to 20 characters>"  // the cardholder's billing address - client test card data (Appendix I)
}
```

<a id="d5"></a>

## Defect 5: Negative test leaves out something ATL105 does not require here - the expected decline is not justified

**Priority:** High · **Kind:** Test case defect · **Test cases:** 1 · **Defect id:** C-02 (BA review 19-11-2026)

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-038607](#tc-038607) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L16)) | `ready` | Manual / Keyed Authorization Only Loyalty Card Data Segment omitted where requires it → declined | Loyalty Card Data Segment (segment 108) omitted where p.15 requires it | [TD-TC-038607.json](mastercard_credit_new_16_11/test_data/TD-TC-038607.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-038607 | - | test case record [test_cases.jsonl line 16](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L16) → `test_objective` | `test_objective` = `Loyalty Card Data Segment (segment 108) omitted where p.15 requires it`; expected Response Code `1`; `spec_basis[0]` = p.15: "Expiration Date must be included in Card Discretionary Block Data (Element No." | The rule this negative test claims to break is not in ATL105 |

### Expected behavior

- Remove the test or rewrite it around a rule ATL105 states.

### Why this is a defect

- PDF p.15 (front matter, no printed page number) does not mention Loyalty Card Data Segment at all. The test case's own citation for p.15 (spec_basis, from the ATL105 TransArmor specification, not the ATL105 Message Format Specifications) reads "Expiration Date must be included in Card Discretionary Block Data (Element No." - it says nothing about Loyalty Card Data Segment. ATL105 makes Segment 108 required only in the Loyalty Card Transaction Request (Section 11.2.1: it 'Contains the loyalty card data required for all loyalty card transactions'); the Financial Transaction Request layout in Section 11.1 does not list it. This MasterCard Credit transaction carries no loyalty card, so leaving Segment 108 out is correct and BUYPASS has no reason to decline.

**BA explanation:** "Omitting a segment is a negative test only when the scenario makes that segment mandatory. If it is optional or not applicable, its absence alone does not justify expecting a decline - and the page the test cites does not say the segment is required."

### Acceptance criteria

- Cite the ATL105 rule that makes the segment mandatory for this exact scenario, word for word on the cited page - or remove the test.
- To test a missing mandatory segment, use a scenario where ATL105 requires it (for Segment 108: a Loyalty Card Transaction Request) and leave it out there.
- A regression check fails 'segment omitted' negative tests whose cited page does not make the segment required.

**Specification reference (ATL105, word for word):**

- Section 11.2.1 Loyalty Card Transactions - Request, PDF p.175 (printed 11-12): "The Loyalty Card Transaction Request contains Segment Nos. 108 and 114." ([ATL105 text line 8360](../specifications/ATL105/docs/specs/extracted_text_new.txt#L8360))
- Section 11.2.1 Loyalty Card Transactions - Request, PDF p.175 (printed 11-12): "Contains the loyalty card data required for all loyalty card transactions." ([ATL105 text line 8422](../specifications/ATL105/docs/specs/extracted_text_new.txt#L8422))
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "All Financial Transaction Requests contain none, one, or more of the following data segments in Field Nos. 4–9." ([ATL105 text line 7950](../specifications/ATL105/docs/specs/extracted_text_new.txt#L7950))

**Test data excerpt - [TC-038607](#tc-038607), step 1, `TD-TC-038607.json` (selected fields, actual then expected):**

```jsonc
// actual - line 2
"Financial Transaction Request": {
// expected
"Financial Transaction Request": {  // C-02: no change to the request - what must change is the test (its premise and the expected decline); see the finding
```

<a id="d6"></a>

## Defect 6: The Terminal Identifier is a generated placeholder, yet the test cases are marked ready

**Priority:** High · **Kind:** Awaiting client input / run-time value, Test case issue to correct · **Test cases:** 43 · **Defect id:** C-03, I-01

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-039912](#tc-039912) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L147)) | `ready` | Manual / Keyed Purchase/Capture → approved | Happy path: Purchase/Capture approved | [TD-TC-039912.json](mastercard_credit_new_16_11/test_data/TD-TC-039912.json) |
| [TC-039913](#tc-039913) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L148)) | `ready` | Manual / Keyed Purchase/Capture, then Purchase Reversal with Sequence Number differe… → declined (to confirm) | Single rule broken: Purchase/Capture -> Purchase Reversal with Sequence Number not matching the original -> declined | [TD-TC-039913.step1.purchase.json](mastercard_credit_new_16_11/test_data/TD-TC-039913.step1.purchase.json), [TD-TC-039913.step2.reversal.json](mastercard_credit_new_16_11/test_data/TD-TC-039913.step2.reversal.json) |
| [TC-039914](#tc-039914) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L149)) | `ready` | Manual / Keyed Purchase/Capture, then Purchase Reversal with Terminal Identifier dif… → declined (to confirm) | Single rule broken: Purchase/Capture -> Purchase Reversal with Terminal Identifier not matching the original -> declined | [TD-TC-039914.step1.purchase.json](mastercard_credit_new_16_11/test_data/TD-TC-039914.step1.purchase.json), [TD-TC-039914.step2.reversal.json](mastercard_credit_new_16_11/test_data/TD-TC-039914.step2.reversal.json) |
| [TC-039915](#tc-039915) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L150)) | `ready` | Manual / Keyed Purchase/Capture, then Time-out Reversal with Terminal Identifier dif… → declined (to confirm) | Single rule broken: Purchase/Capture -> Time-out Reversal with Terminal Identifier not matching the original -> declined | [TD-TC-039915.step1.purchase.json](mastercard_credit_new_16_11/test_data/TD-TC-039915.step1.purchase.json), [TD-TC-039915.step2.timeout-reversal.json](mastercard_credit_new_16_11/test_data/TD-TC-039915.step2.timeout-reversal.json) |
| [TC-039916](#tc-039916) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151)) | `ready` | Manual / Keyed Purchase/Capture with Network Token Data Request sent → approved | Decision table: Authorization Only with segment 153 sent (the rule's condition holds) | [TD-TC-039916.json](mastercard_credit_new_16_11/test_data/TD-TC-039916.json) |
| [TC-040080](#tc-040080) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L315)) | `ready` | EMV Contact Chip Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040080.json](mastercard_credit_new_16_11/test_data/TD-TC-040080.json) |
| [TC-040194](#tc-040194) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L429)) | `ready` | EMV Contactless Chip Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040194.json](mastercard_credit_new_16_11/test_data/TD-TC-040194.json) |
| [TC-040308](#tc-040308) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L543)) | `ready` | Magnetic Stripe Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040308.json](mastercard_credit_new_16_11/test_data/TD-TC-040308.json) |
| [TC-040512](#tc-040512) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L747)) | `ready` | Contactless Magnetic Stripe Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040512.json](mastercard_credit_new_16_11/test_data/TD-TC-040512.json) |
| [TC-040623](#tc-040623) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L858)) | `ready` | Manual / Keyed Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040623.json](mastercard_credit_new_16_11/test_data/TD-TC-040623.json) |
| [TC-040737](#tc-040737) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L972)) | `ready` | Chip Fallback Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040737.json](mastercard_credit_new_16_11/test_data/TD-TC-040737.json) |
| [TC-040856](#tc-040856) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1091)) | `ready` | Credential on File Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040856.json](mastercard_credit_new_16_11/test_data/TD-TC-040856.json) |
| + 31 more: [TC-040974](#tc-040974), [TC-041219](#tc-041219), [TC-041315](#tc-041315), [TC-041606](#tc-041606), [TC-040116](#tc-040116), [TC-040230](#tc-040230), [TC-040346](#tc-040346), [TC-040547](#tc-040547), [TC-040658](#tc-040658), [TC-040776](#tc-040776), [TC-040893](#tc-040893), [TC-041009](#tc-041009), [TC-040126](#tc-040126), [TC-040240](#tc-040240), [TC-040356](#tc-040356), [TC-040557](#tc-040557), [TC-040668](#tc-040668), [TC-040786](#tc-040786), [TC-040903](#tc-040903), [TC-041019](#tc-041019), [TC-040140](#tc-040140), [TC-040254](#tc-040254), [TC-040396](#tc-040396), [TC-040571](#tc-040571), [TC-040683](#tc-040683), [TC-040800](#tc-040800), [TC-040919](#tc-040919), [TC-041032](#tc-041032), [TC-041083](#tc-041083), [TC-041101](#tc-041101), [TC-038607](#tc-038607) | | | | |

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
| TC-040080 | - | [TD-TC-040080.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040080.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `JX72948216050257001` | The Terminal Identifier is a placeholder |
| TC-040080 | - | test case record [test_cases.jsonl line 315](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L315) → `status` | `status` = `ready` | Marked 'ready' although the terminal is a placeholder |
| TC-040194 | - | [TD-TC-040194.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040194.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `JX72948216050257001` | The Terminal Identifier is a placeholder |
| TC-040194 | - | test case record [test_cases.jsonl line 429](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L429) → `status` | `status` = `ready` | Marked 'ready' although the terminal is a placeholder |
| TC-040308 | - | [TD-TC-040308.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040308.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `JX72948216050257001` | The Terminal Identifier is a placeholder |
| TC-040308 | - | test case record [test_cases.jsonl line 543](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L543) → `status` | `status` = `ready` | Marked 'ready' although the terminal is a placeholder |
| TC-040512 | - | [TD-TC-040512.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040512.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `JX72948216050257001` | The Terminal Identifier is a placeholder |
| TC-040512 | - | test case record [test_cases.jsonl line 747](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L747) → `status` | `status` = `ready` | Marked 'ready' although the terminal is a placeholder |
| TC-040623 | - | [TD-TC-040623.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040623.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `JX72948216050257001` | The Terminal Identifier is a placeholder |
| TC-040623 | - | test case record [test_cases.jsonl line 858](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L858) → `status` | `status` = `ready` | Marked 'ready' although the terminal is a placeholder |
| TC-040737 | - | [TD-TC-040737.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040737.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `JX72948216050257001` | The Terminal Identifier is a placeholder |
| TC-040737 | - | test case record [test_cases.jsonl line 972](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L972) → `status` | `status` = `ready` | Marked 'ready' although the terminal is a placeholder |
| TC-040856 | - | [TD-TC-040856.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040856.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | `JX72948216050257001` | The Terminal Identifier is a placeholder |
| TC-040856 | - | test case record [test_cases.jsonl line 1091](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1091) → `status` | `status` = `ready` | Marked 'ready' although the terminal is a placeholder |
| + 31 more with the same kind of finding: [TC-040974](#tc-040974), [TC-041219](#tc-041219), [TC-041315](#tc-041315), [TC-041606](#tc-041606), [TC-040116](#tc-040116), [TC-040230](#tc-040230), [TC-040346](#tc-040346), [TC-040547](#tc-040547), [TC-040658](#tc-040658), [TC-040776](#tc-040776), [TC-040893](#tc-040893), [TC-041009](#tc-041009), [TC-040126](#tc-040126), [TC-040240](#tc-040240), [TC-040356](#tc-040356), [TC-040557](#tc-040557), [TC-040668](#tc-040668), [TC-040786](#tc-040786), [TC-040903](#tc-040903), [TC-041019](#tc-041019), [TC-040140](#tc-040140), [TC-040254](#tc-040254), [TC-040396](#tc-040396), [TC-040571](#tc-040571), [TC-040683](#tc-040683), [TC-040800](#tc-040800), [TC-040919](#tc-040919), [TC-041032](#tc-041032), [TC-041083](#tc-041083), [TC-041101](#tc-041101), [TC-038607](#tc-038607) | | | | |

### Expected behavior

- Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier.
- `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied.

### Why it matters

- The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'JX72948216050257001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '72' = Yukon (YT), Canada.
- The test case status is 'ready', but its Terminal Identifier is still a placeholder.
- The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'QU67070249149717001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '67' = Northwest Territories (NT), Canada.
- The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'KC57457075622174001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '57' = Armed Forces Americas (AA), US military.
- The test case flags the merchant as a placeholder (MERCHANT_PLACEHOLDER), so 'VZ26598162666071001' was generated, not assigned by BUYPASS. Its State Code (positions 3-4) '26' = Michigan (MI), United States.

**BA explanation:** "Every request uses a made-up terminal. BUYPASS only accepts terminals it has set up, so these tests cannot run until the real Terminal ID is supplied - and they should not be 'ready' until then."

### Acceptance criteria

- Client / BUYPASS supply the real Terminal Identifier of the merchant's terminal; every step uses it and the Segment 100 length is recalculated.
- A test case with a placeholder terminal has a status other than 'ready' (for example requires_review).
- A regression check flags 'ready' test cases whose merchant is a placeholder.

**Specification reference (ATL105, word for word):**

- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "The Terminal Identifier must be assigned by and set up at BUYPASS." ([ATL105 text line 23795](../specifications/ATL105/docs/specs/extracted_text_new.txt#L23795))
- Element 102 Terminal Identifier (Chapter 13), PDF p.417 (printed 13-77): "State Code (Pos. Nos. 3–4) Any valid 2-character State Code (ANSI® Code)." ([ATL105 text line 23816](../specifications/ATL105/docs/specs/extracted_text_new.txt#L23816))
- Appendix D. Valid State Codes, PDF p.517 (printed Appendix D-4): "Yukon YT 72" ([ATL105 text line 28319](../specifications/ATL105/docs/specs/extracted_text_new.txt#L28319))
- Appendix D. Valid State Codes, PDF p.517 (printed Appendix D-4): "Northwest Territories NT 67" ([ATL105 text line 28313](../specifications/ATL105/docs/specs/extracted_text_new.txt#L28313))
- Appendix D. Valid State Codes, PDF p.516 (printed Appendix D-3): "Armed Forces Americas AA 57" ([ATL105 text line 28287](../specifications/ATL105/docs/specs/extracted_text_new.txt#L28287))
- Appendix D. Valid State Codes, PDF p.515 (printed Appendix D-2): "Michigan MI 26" ([ATL105 text line 28229](../specifications/ATL105/docs/specs/extracted_text_new.txt#L28229))

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

<a id="d7"></a>

## Defect 7: Time-out Reversal sent although the original request is expected to be answered

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

<a id="d8"></a>

## Defect 8: 'Different terminal' negative tests use a terminal BUYPASS does not know

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

<a id="d9"></a>

## Defect 9: The test objective names a different transaction than the one sent

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

<a id="d10"></a>

## Defect 10: Network token data (Segment 153) is synthesized, and its indicator and length need confirmation

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

<a id="d11"></a>

## Defect 11: Follow-on values are instruction text to be filled in from the previous response

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

<a id="d12"></a>

## Defect 12: EMV chip data is generated, not captured from a card or simulator

**Priority:** Medium · **Kind:** Awaiting client input / run-time value · **Test cases:** 11 · **Defect id:** I-04

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-040080](#tc-040080) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L315)) | `ready` | EMV Contact Chip Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040080.json](mastercard_credit_new_16_11/test_data/TD-TC-040080.json) |
| [TC-040194](#tc-040194) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L429)) | `ready` | EMV Contactless Chip Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040194.json](mastercard_credit_new_16_11/test_data/TD-TC-040194.json) |
| [TC-041219](#tc-041219) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1454)) | `needs_client_input` | SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-041219.json](mastercard_credit_new_16_11/test_data/TD-TC-041219.json) |
| [TC-041315](#tc-041315) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1550)) | `needs_client_input` | SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-041315.json](mastercard_credit_new_16_11/test_data/TD-TC-041315.json) |
| [TC-041606](#tc-041606) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1841)) | `needs_client_input` | SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-041606.json](mastercard_credit_new_16_11/test_data/TD-TC-041606.json) |
| [TC-040116](#tc-040116) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L351)) | `ready` | EMV Contact Chip Authorization Only with Tax Type 1 = GST → approved | Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds) | [TD-TC-040116.json](mastercard_credit_new_16_11/test_data/TD-TC-040116.json) |
| [TC-040230](#tc-040230) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L465)) | `ready` | EMV Contactless Chip Authorization Only with Tax Type 1 = GST → approved | Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds) | [TD-TC-040230.json](mastercard_credit_new_16_11/test_data/TD-TC-040230.json) |
| [TC-040126](#tc-040126) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L361)) | `ready` | EMV Contact Chip Authorization Only with optional Standard Message Data Segment → approved | Decision table: Authorization Only with optional segment 100 present | [TD-TC-040126.json](mastercard_credit_new_16_11/test_data/TD-TC-040126.json) |
| [TC-040240](#tc-040240) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L475)) | `ready` | EMV Contactless Chip Authorization Only with optional Standard Message Data Segment → approved | Decision table: Authorization Only with optional segment 100 present | [TD-TC-040240.json](mastercard_credit_new_16_11/test_data/TD-TC-040240.json) |
| [TC-040140](#tc-040140) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L375)) | `ready` | EMV Contact Chip Authorization Only (AVS or NVS or Both Information returned) → approved | Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned | [TD-TC-040140.json](mastercard_credit_new_16_11/test_data/TD-TC-040140.json) |
| [TC-040254](#tc-040254) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L489)) | `ready` | EMV Contactless Chip Authorization Only (AVS or NVS or Both Information returned) → approved | Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned | [TD-TC-040254.json](mastercard_credit_new_16_11/test_data/TD-TC-040254.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-040080 | - | [TD-TC-040080.json line 51](mastercard_credit_new_16_11/test_data/TD-TC-040080.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | `2012091700000100000102171` | The CA Public Key File Checksum is a sample value |
| TC-040080 | - | [TD-TC-040080.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040080.json#L54) → `EMV Request Data Segment → EMVChipData → Tag 95` | `95=8000048000, 9F34=5E0302` | TVR says 'online PIN entered' on a transaction without online PIN |
| TC-040080 | - | [TD-TC-040080.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040080.json#L54) → `EMV Request Data Segment → EMVChipData` | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=D9509DAD, 95=8000048000` | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders |
| TC-040194 | - | [TD-TC-040194.json line 51](mastercard_credit_new_16_11/test_data/TD-TC-040194.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | `2012091700000100000102171` | The CA Public Key File Checksum is a sample value |
| TC-040194 | - | [TD-TC-040194.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040194.json#L54) → `EMV Request Data Segment → EMVChipData → Tag 95` | `95=8000048000, 9F34=5E0302` | TVR says 'online PIN entered' on a transaction without online PIN |
| TC-040194 | - | [TD-TC-040194.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040194.json#L54) → `EMV Request Data Segment → EMVChipData` | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=2FB75EB4, 95=8000048000` | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders |
| TC-040194 | - | [TD-TC-040194.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040194.json#L54) → `EMV Request Data Segment → EMVChipData` | `22 tags, CVM 9F34 = 5E0302` | Contactless chip data reuses the contact tag set |
| TC-041219 | - | [TD-TC-041219.json line 51](mastercard_credit_new_16_11/test_data/TD-TC-041219.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | `2012091700000100000102171` | The CA Public Key File Checksum is a sample value |
| TC-041219 | - | [TD-TC-041219.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-041219.json#L54) → `EMV Request Data Segment → EMVChipData → Tag 95` | `95=8000048000, 9F34=5E0302` | TVR says 'online PIN entered' on a transaction without online PIN |
| TC-041219 | - | [TD-TC-041219.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-041219.json#L54) → `EMV Request Data Segment → EMVChipData` | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=3EAF5E40, 95=8000048000` | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders |
| TC-041315 | - | [TD-TC-041315.json line 62](mastercard_credit_new_16_11/test_data/TD-TC-041315.json#L62) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | `2012091700000100000102171` | The CA Public Key File Checksum is a sample value |
| TC-041315 | - | [TD-TC-041315.json line 65](mastercard_credit_new_16_11/test_data/TD-TC-041315.json#L65) → `EMV Request Data Segment → EMVChipData → Tag 95` | `95=8000048000, 9F34=5E0302` | TVR says 'online PIN entered' on a transaction without online PIN |
| TC-041315 | - | [TD-TC-041315.json line 65](mastercard_credit_new_16_11/test_data/TD-TC-041315.json#L65) → `EMV Request Data Segment → EMVChipData` | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=255978D3, 95=8000048000` | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders |
| TC-041606 | - | [TD-TC-041606.json line 62](mastercard_credit_new_16_11/test_data/TD-TC-041606.json#L62) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | `2012091700000100000102171` | The CA Public Key File Checksum is a sample value |
| TC-041606 | - | [TD-TC-041606.json line 65](mastercard_credit_new_16_11/test_data/TD-TC-041606.json#L65) → `EMV Request Data Segment → EMVChipData → Tag 95` | `95=8000048000, 9F34=5E0302` | TVR says 'online PIN entered' on a transaction without online PIN |
| TC-041606 | - | [TD-TC-041606.json line 65](mastercard_credit_new_16_11/test_data/TD-TC-041606.json#L65) → `EMV Request Data Segment → EMVChipData` | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=F6A5A51F, 95=8000048000` | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders |
| TC-040116 | - | [TD-TC-040116.json line 51](mastercard_credit_new_16_11/test_data/TD-TC-040116.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | `2012091700000100000102171` | The CA Public Key File Checksum is a sample value |
| TC-040116 | - | [TD-TC-040116.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040116.json#L54) → `EMV Request Data Segment → EMVChipData → Tag 95` | `95=8000048000, 9F34=5E0302` | TVR says 'online PIN entered' on a transaction without online PIN |
| TC-040116 | - | [TD-TC-040116.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040116.json#L54) → `EMV Request Data Segment → EMVChipData` | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=733C27AF, 95=8000048000` | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders |
| TC-040230 | - | [TD-TC-040230.json line 51](mastercard_credit_new_16_11/test_data/TD-TC-040230.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | `2012091700000100000102171` | The CA Public Key File Checksum is a sample value |
| TC-040230 | - | [TD-TC-040230.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040230.json#L54) → `EMV Request Data Segment → EMVChipData → Tag 95` | `95=8000048000, 9F34=5E0302` | TVR says 'online PIN entered' on a transaction without online PIN |
| TC-040230 | - | [TD-TC-040230.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040230.json#L54) → `EMV Request Data Segment → EMVChipData` | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=91FCEF97, 95=8000048000` | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders |
| TC-040230 | - | [TD-TC-040230.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040230.json#L54) → `EMV Request Data Segment → EMVChipData` | `22 tags, CVM 9F34 = 5E0302` | Contactless chip data reuses the contact tag set |
| TC-040126 | - | [TD-TC-040126.json line 51](mastercard_credit_new_16_11/test_data/TD-TC-040126.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | `2012091700000100000102171` | The CA Public Key File Checksum is a sample value |
| + 4 more with the same kind of finding: [TC-040126](#tc-040126), [TC-040240](#tc-040240), [TC-040140](#tc-040140), [TC-040254](#tc-040254) | | | | |

### Expected behavior

- Confirm the checksum of the host's current CA Public Key file before execution.
- Produce the TVR and the CVM Results together with a card simulator.
- Produce the chip data with a card simulator / HSM before execution.
- Confirm the contactless chip data with a MasterCard contactless (M/Chip) kernel trace.

### Why it matters

- The format is right (25 digits), but the value must be the checksum received in the device's last CA Public Key File Load; the test case marks it as a configured test-data value.
- TVR byte 3 (`04`) sets 'Online PIN entered' (EMV Book 3), but the CVM Results (Tag 9F34 = 5E0302) say signature and Encrypted PIN Block Data is empty. The TVR is a generated placeholder; it must match the CVM once the chip data comes from a card simulator.
- ATL105 requires the exact values from the card-to-terminal interface; generated values cannot be authenticated by the issuer.
- The contactless request carries the same tags as the contact requests and no contactless-specific tag (9F6E, 9F6D, 9F7C, 9F66). ATL105 Appendix R lists an example only, so this cannot be decided from the spec.

**BA explanation:** "Cryptogram, counters and terminal results in the chip data are generated values. The issuer cannot validate them, so the test needs chip data from the client's test card or simulator."

### Acceptance criteria

- Element 190 comes from the client's test card or simulator; TVR and CVM results agree; the CA Public Key checksum comes from the current key load.

**Specification reference (ATL105, word for word):**

- Element 187 CA Public Key File Checksum (Chapter 13), PDF p.477 (printed 13-137): "Once received, the checksum must be used for all subsequent EMV transaction requests from this device." ([ATL105 text line 26381](../specifications/ATL105/docs/specs/extracted_text_new.txt#L26381))
- Element 190 EMV Chip Data (Chapter 13), PDF p.478 (printed 13-138): "All data elements must contain the exact values that were present on the card to terminal interface." ([ATL105 text line 26438](../specifications/ATL105/docs/specs/extracted_text_new.txt#L26438))
- Appendix R. EMV Chip Data Example, PDF p.697 (printed Appendix R-1): "The below table does not contain complete list of tags." ([ATL105 text line 37728](../specifications/ATL105/docs/specs/extracted_text_new.txt#L37728))

<a id="d13"></a>

## Defect 13: Same request as another test case (difference only on the device / response side)

**Priority:** Medium · **Kind:** To confirm with BUYPASS / SME · **Test cases:** 11 · **Defect id:** C-08

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-040080](#tc-040080) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L315)) | `ready` | EMV Contact Chip Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040080.json](mastercard_credit_new_16_11/test_data/TD-TC-040080.json) |
| [TC-040194](#tc-040194) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L429)) | `ready` | EMV Contactless Chip Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040194.json](mastercard_credit_new_16_11/test_data/TD-TC-040194.json) |
| [TC-040308](#tc-040308) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L543)) | `ready` | Magnetic Stripe Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040308.json](mastercard_credit_new_16_11/test_data/TD-TC-040308.json) |
| [TC-040512](#tc-040512) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L747)) | `ready` | Contactless Magnetic Stripe Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040512.json](mastercard_credit_new_16_11/test_data/TD-TC-040512.json) |
| [TC-040623](#tc-040623) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L858)) | `ready` | Manual / Keyed Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040623.json](mastercard_credit_new_16_11/test_data/TD-TC-040623.json) |
| [TC-040737](#tc-040737) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L972)) | `ready` | Chip Fallback Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040737.json](mastercard_credit_new_16_11/test_data/TD-TC-040737.json) |
| [TC-040856](#tc-040856) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1091)) | `ready` | Credential on File Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040856.json](mastercard_credit_new_16_11/test_data/TD-TC-040856.json) |
| [TC-040974](#tc-040974) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1209)) | `pending_sme` | Contactless Mobile Commerce Device Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040974.json](mastercard_credit_new_16_11/test_data/TD-TC-040974.json) |
| [TC-041219](#tc-041219) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1454)) | `needs_client_input` | SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-041219.json](mastercard_credit_new_16_11/test_data/TD-TC-041219.json) |
| [TC-041315](#tc-041315) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1550)) | `needs_client_input` | SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-041315.json](mastercard_credit_new_16_11/test_data/TD-TC-041315.json) |
| [TC-041606](#tc-041606) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1841)) | `needs_client_input` | SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-041606.json](mastercard_credit_new_16_11/test_data/TD-TC-041606.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-040080 | 1 | TD-TC-040080.json vs TD-TC-040065.json | `expected 2 vs 2` | Same request as TC-040065 (BR1_TS11_TC6) |
| TC-040194 | 1 | TD-TC-040194.json vs TD-TC-040179.json | `expected 2 vs 2` | Same request as TC-040179 (BR1_TS12_TC2) |
| TC-040308 | 1 | TD-TC-040308.json vs TD-TC-040293.json | `expected 2 vs 2` | Same request as TC-040293 (BR1_TS13_TC2) |
| TC-040512 | 1 | TD-TC-040512.json vs TD-TC-040497.json | `expected 2 vs 2` | Same request as TC-040497 (BR1_TS14_TC1) |
| TC-040623 | 1 | TD-TC-040623.json vs TD-TC-040608.json | `expected 2 vs 2` | Same request as TC-040608 (BR1_TS15_TC2) |
| TC-040737 | 1 | TD-TC-040737.json vs TD-TC-040722.json | `expected 2 vs 2` | Same request as TC-040722 (BR1_TS16_TC1) |
| TC-040856 | 1 | TD-TC-040856.json vs TD-TC-040837.json | `expected 2 vs 2` | Same request as TC-040837 (BR1_TS17_TC4) |
| TC-040974 | 1 | TD-TC-040974.json vs TD-TC-040959.json | `expected 2 vs 2` | Same request as TC-040959 (BR1_TS18_TC1) |
| TC-041219 | 1 | TD-TC-041219.json vs TD-TC-041206.json | `expected 2 vs 2` | Same request as TC-041206 (BR1_TS54_TC1) |
| TC-041315 | 1 | TD-TC-041315.json vs TD-TC-041301.json | `expected 2 vs 2` | Same request as TC-041301 (BR1_TS154_TC5) |
| TC-041606 | 1 | TD-TC-041606.json vs TD-TC-041592.json | `expected 2 vs 2` | Same request as TC-041592 (BR1_TS173_TC1) |

### Expected behavior

- Check the difference on the device / in the response; optionally use another card.

### Why it matters

- Apart from the Sequence Number the message is identical to TC-040065 (all segments compared). TC-040065 expects 2; this test expects 2. What makes the two tests different is not in the message sent to BUYPASS.

### Acceptance criteria

- Check the difference on the device or in the response; optionally use another card.

<a id="d14"></a>

## Defect 14: Expiry format for stored credentials (entry mode 10) not stated

**Priority:** Medium · **Kind:** To confirm with BUYPASS / SME · **Test cases:** 5 · **Defect id:** S-01

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-040856](#tc-040856) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1091)) | `ready` | Credential on File Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040856.json](mastercard_credit_new_16_11/test_data/TD-TC-040856.json) |
| [TC-040893](#tc-040893) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1128)) | `ready` | Credential on File Authorization Only with Tax Type 1 = GST → approved | Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds) | [TD-TC-040893.json](mastercard_credit_new_16_11/test_data/TD-TC-040893.json) |
| [TC-040903](#tc-040903) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1138)) | `ready` | Credential on File Authorization Only with optional Standard Message Data Segment → approved | Decision table: Authorization Only with optional segment 100 present | [TD-TC-040903.json](mastercard_credit_new_16_11/test_data/TD-TC-040903.json) |
| [TC-040919](#tc-040919) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1154)) | `ready` | Credential on File Authorization Only (AVS or NVS or Both Information returned) → approved | Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned | [TD-TC-040919.json](mastercard_credit_new_16_11/test_data/TD-TC-040919.json) |
| [TC-041101](#tc-041101) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1336)) | `pending_sme` | Credential on File Mail/Phone Authorization Only (AVS or NVS or Both Information returned) → approved | Response / oracle check: Mail/Phone Authorization Only -> AVS or NVS or Both Information returned | [TD-TC-041101.json](mastercard_credit_new_16_11/test_data/TD-TC-041101.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-040856 | - | Card Discretionary Block Data (entry mode 10 steps) | `1127` | Expiry format for stored credentials is not stated |
| TC-040893 | - | Card Discretionary Block Data (entry mode 10 steps) | `0328` | Expiry format for stored credentials is not stated |
| TC-040903 | - | Card Discretionary Block Data (entry mode 10 steps) | `1230` | Expiry format for stored credentials is not stated |
| TC-040919 | - | Card Discretionary Block Data (entry mode 10 steps) | `1127` | Expiry format for stored credentials is not stated |
| TC-041101 | - | Card Discretionary Block Data (entry mode 10 steps) | `0428` | Expiry format for stored credentials is not stated |

### Expected behavior

- Confirm the expiry format for entry mode 10 with BUYPASS.

### Why it matters

- The stored-credential steps send the expiry as MMYY, the format for manually entered cards. ATL105 also says that card data the merchant stored is sent as Account Number = YYMM. Entry mode 10 uses stored credentials, and the spec does not say which of the two applies.

### Acceptance criteria

- Confirm with BUYPASS.

**Specification reference (ATL105, word for word):**

- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.372 (printed 13-32): "For a card whose number has been manually entered, the expiration date is in MMYY format" ([ATL105 text line 21761](../specifications/ATL105/docs/specs/extracted_text_new.txt#L21761))
- Section 10.1.8.2 Track Support Requirements, PDF p.94 (printed 10-15): "The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM)." ([ATL105 text line 4564](../specifications/ATL105/docs/specs/extracted_text_new.txt#L4564))

<a id="d15"></a>

## Defect 15: ATL105 citations in the test cases are on the wrong page, not ATL105 text, or about another context

**Priority:** Low · **Kind:** Citation issue · **Test cases:** 22 · **Defect id:** Q-01, Q-02, Q-03, Q-04

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-039912](#tc-039912) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L147)) | `ready` | Manual / Keyed Purchase/Capture → approved | Happy path: Purchase/Capture approved | [TD-TC-039912.json](mastercard_credit_new_16_11/test_data/TD-TC-039912.json) |
| [TC-039913](#tc-039913) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L148)) | `ready` | Manual / Keyed Purchase/Capture, then Purchase Reversal with Sequence Number differe… → declined (to confirm) | Single rule broken: Purchase/Capture -> Purchase Reversal with Sequence Number not matching the original -> declined | [TD-TC-039913.step1.purchase.json](mastercard_credit_new_16_11/test_data/TD-TC-039913.step1.purchase.json), [TD-TC-039913.step2.reversal.json](mastercard_credit_new_16_11/test_data/TD-TC-039913.step2.reversal.json) |
| [TC-039914](#tc-039914) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L149)) | `ready` | Manual / Keyed Purchase/Capture, then Purchase Reversal with Terminal Identifier dif… → declined (to confirm) | Single rule broken: Purchase/Capture -> Purchase Reversal with Terminal Identifier not matching the original -> declined | [TD-TC-039914.step1.purchase.json](mastercard_credit_new_16_11/test_data/TD-TC-039914.step1.purchase.json), [TD-TC-039914.step2.reversal.json](mastercard_credit_new_16_11/test_data/TD-TC-039914.step2.reversal.json) |
| [TC-039915](#tc-039915) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L150)) | `ready` | Manual / Keyed Purchase/Capture, then Time-out Reversal with Terminal Identifier dif… → declined (to confirm) | Single rule broken: Purchase/Capture -> Time-out Reversal with Terminal Identifier not matching the original -> declined | [TD-TC-039915.step1.purchase.json](mastercard_credit_new_16_11/test_data/TD-TC-039915.step1.purchase.json), [TD-TC-039915.step2.timeout-reversal.json](mastercard_credit_new_16_11/test_data/TD-TC-039915.step2.timeout-reversal.json) |
| [TC-039916](#tc-039916) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151)) | `ready` | Manual / Keyed Purchase/Capture with Network Token Data Request sent → approved | Decision table: Authorization Only with segment 153 sent (the rule's condition holds) | [TD-TC-039916.json](mastercard_credit_new_16_11/test_data/TD-TC-039916.json) |
| [TC-040080](#tc-040080) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L315)) | `ready` | EMV Contact Chip Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040080.json](mastercard_credit_new_16_11/test_data/TD-TC-040080.json) |
| [TC-040194](#tc-040194) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L429)) | `ready` | EMV Contactless Chip Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040194.json](mastercard_credit_new_16_11/test_data/TD-TC-040194.json) |
| [TC-040308](#tc-040308) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L543)) | `ready` | Magnetic Stripe Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040308.json](mastercard_credit_new_16_11/test_data/TD-TC-040308.json) |
| [TC-040512](#tc-040512) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L747)) | `ready` | Contactless Magnetic Stripe Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040512.json](mastercard_credit_new_16_11/test_data/TD-TC-040512.json) |
| [TC-040623](#tc-040623) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L858)) | `ready` | Manual / Keyed Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040623.json](mastercard_credit_new_16_11/test_data/TD-TC-040623.json) |
| [TC-040737](#tc-040737) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L972)) | `ready` | Chip Fallback Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040737.json](mastercard_credit_new_16_11/test_data/TD-TC-040737.json) |
| [TC-040856](#tc-040856) ([definition](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1091)) | `ready` | Credential on File Authorization Only with MasterCard Authentication Data → approved | Happy path: Authorization Only sending Table 048 MasterCard Authentication Data | [TD-TC-040856.json](mastercard_credit_new_16_11/test_data/TD-TC-040856.json) |
| + 10 more: [TC-040974](#tc-040974), [TC-041219](#tc-041219), [TC-041315](#tc-041315), [TC-041606](#tc-041606), [TC-040116](#tc-040116), [TC-040230](#tc-040230), [TC-040126](#tc-040126), [TC-040240](#tc-040240), [TC-040140](#tc-040140), [TC-040254](#tc-040254) | | | | |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-039912 | - | test case record [test_cases.jsonl line 147](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L147) → `device_checkpoints[3]`, `device_checkpoints[4]` | `device_checkpoints[3]` = MUST: When pumping is complete, the device must provide an Invoice number, as well as all prompts for which the device has already captured responses, with the Capture message request. (p.127); `device_checkpoints[4]` = MUST: In either case, when pumping is complete, the values entered for all prompts must be sent with the capture message. (p.138) | Device checkpoints about fuel pumping in a non-petroleum test |
| TC-039913 | - | test case record [test_cases.jsonl line 148](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L148) → `spec_basis[2]` | `spec_basis[2]` = p.402: "The Sequence Number must be the Sequence Number contained in the original transaction." | The quote cited for p.402 is on a different page |
| TC-039914 | - | test case record [test_cases.jsonl line 149](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L149) → `spec_basis[2]` | `spec_basis[2]` = p.40: "Plan v7 W2: a follow-on of the same transaction is sent by the same device as its original (stated for TOR p.40 'A TOR must be sent from the same device as the original financial transaction request'; applied to every follow-on for matching)." | The quote cited for p.40 is not in ATL105 |
| TC-039915 | - | test case record [test_cases.jsonl line 150](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L150) → `spec_basis[2]` | `spec_basis[2]` = p.38: "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR." | The quote cited for p.38 is on a different page |
| TC-039916 | - | test case record [test_cases.jsonl line 151](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151) → `spec_basis[0]` | `spec_basis[0]` = p.312: "This data is applicable only to subsequent transactions (Example: Reversal, Completion, Cancellation)." | Cited Segment 153 rule is for Table 006 on subsequent transactions |
| TC-040080 | - | test case record [test_cases.jsonl line 315](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L315) → `spec_basis[0]` | `spec_basis[0]` = `p.586: "Table 048 MasterCard Authentication Data"` | The quote cited for p.586 is not word-for-word in ATL105 |
| TC-040080 | - | test case record [test_cases.jsonl line 315](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L315) → `spec_basis[3]` | `spec_basis[3]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | The quote cited for p.161 is on the neighbouring page |
| TC-040194 | - | test case record [test_cases.jsonl line 429](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L429) → `spec_basis[0]` | `spec_basis[0]` = `p.586: "Table 048 MasterCard Authentication Data"` | The quote cited for p.586 is not word-for-word in ATL105 |
| TC-040194 | - | test case record [test_cases.jsonl line 429](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L429) → `spec_basis[3]` | `spec_basis[3]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | The quote cited for p.161 is on the neighbouring page |
| TC-040308 | - | test case record [test_cases.jsonl line 543](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L543) → `spec_basis[0]` | `spec_basis[0]` = `p.586: "Table 048 MasterCard Authentication Data"` | The quote cited for p.586 is not word-for-word in ATL105 |
| TC-040512 | - | test case record [test_cases.jsonl line 747](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L747) → `spec_basis[0]` | `spec_basis[0]` = `p.586: "Table 048 MasterCard Authentication Data"` | The quote cited for p.586 is not word-for-word in ATL105 |
| TC-040623 | - | test case record [test_cases.jsonl line 858](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L858) → `spec_basis[0]` | `spec_basis[0]` = `p.586: "Table 048 MasterCard Authentication Data"` | The quote cited for p.586 is not word-for-word in ATL105 |
| TC-040737 | - | test case record [test_cases.jsonl line 972](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L972) → `spec_basis[0]` | `spec_basis[0]` = `p.586: "Table 048 MasterCard Authentication Data"` | The quote cited for p.586 is not word-for-word in ATL105 |
| TC-040856 | - | test case record [test_cases.jsonl line 1091](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1091) → `spec_basis[0]` | `spec_basis[0]` = `p.586: "Table 048 MasterCard Authentication Data"` | The quote cited for p.586 is not word-for-word in ATL105 |
| TC-040974 | - | test case record [test_cases.jsonl line 1209](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1209) → `spec_basis[0]` | `spec_basis[0]` = `p.586: "Table 048 MasterCard Authentication Data"` | The quote cited for p.586 is not word-for-word in ATL105 |
| TC-041219 | - | test case record [test_cases.jsonl line 1454](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1454) → `spec_basis[0]` | `spec_basis[0]` = `p.586: "Table 048 MasterCard Authentication Data"` | The quote cited for p.586 is not word-for-word in ATL105 |
| TC-041219 | - | test case record [test_cases.jsonl line 1454](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1454) → `spec_basis[3]` | `spec_basis[3]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | The quote cited for p.161 is on the neighbouring page |
| TC-041315 | - | test case record [test_cases.jsonl line 1550](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1550) → `spec_basis[0]` | `spec_basis[0]` = `p.586: "Table 048 MasterCard Authentication Data"` | The quote cited for p.586 is not word-for-word in ATL105 |
| TC-041315 | - | test case record [test_cases.jsonl line 1550](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1550) → `spec_basis[3]` | `spec_basis[3]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | The quote cited for p.161 is on the neighbouring page |
| TC-041606 | - | test case record [test_cases.jsonl line 1841](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1841) → `spec_basis[0]` | `spec_basis[0]` = `p.586: "Table 048 MasterCard Authentication Data"` | The quote cited for p.586 is not word-for-word in ATL105 |
| TC-041606 | - | test case record [test_cases.jsonl line 1841](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1841) → `spec_basis[3]` | `spec_basis[3]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | The quote cited for p.161 is on the neighbouring page |
| TC-040116 | - | test case record [test_cases.jsonl line 351](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L351) → `spec_basis[3]` | `spec_basis[3]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | The quote cited for p.161 is on the neighbouring page |
| TC-040230 | - | test case record [test_cases.jsonl line 465](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L465) → `spec_basis[3]` | `spec_basis[3]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | The quote cited for p.161 is on the neighbouring page |
| TC-040126 | - | test case record [test_cases.jsonl line 361](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L361) → `spec_basis[4]` | `spec_basis[4]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | The quote cited for p.161 is on the neighbouring page |
| + 3 more with the same kind of finding: [TC-040240](#tc-040240), [TC-040140](#tc-040140), [TC-040254](#tc-040254) | | | | |

### Expected behavior

- Drop checkpoints that do not apply to this merchant.
- `spec_basis[2].page` = `84` instead of `402` - the quoted text itself is right.
- Quote the text exactly as it appears: "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR.".
- Cite the Segment 153 layout (p.310) as the basis.
- Quote the text exactly as it appears on the page.
- Cite p.160 instead of p.161.

### Why it matters

- 2 device checkpoint(s) come from the Petroleum Industry Processing Specification (pumping, capture after pumping), but this test runs at MERCHANT 01 (POS PLACEHOLDER) (GENERAL).
- The test case quotes "The Sequence Number must be the Sequence Number contained in the original transaction." for p.402. Found on a different page, not the cited one (p.84, p.89, p.98).
- The test case quotes "Plan v7 W2: a follow-on of the same transaction is sent by the same device as its original (stated for TOR p.40 'A TOR must be sent from the same device as the original financial transaction request'; applied to every follow-on for matching)." for p.40. Not found in ATL105.
- The test case quotes "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR." for p.38. Found on a different page, not the cited one (p.40, p.45).
- The quote is the description of Network Token PAN Indicator (Table 006), which applies only to subsequent transactions. This test sends an original transaction and does not send Table 006.
- The test case quotes "Table 048 MasterCard Authentication Data" for p.586. Not word-for-word: all its words are on the cited page, but not in this form.
- The test case quotes "The following credit cards are supported for EMV processing: ... MasterCard" for p.161. Found on a neighbouring page, not the cited one (p.160).

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

One row per kind of finding with its first example; each test case section below lists its own.

| Kind | Finding (first example) | Test cases | Actual | Where | Why it is correct |
|---|---|---|---|---|---|
| Information | Join Account Number and expiry with '=' in the wire message | [TC-039913](#tc-039913), [TC-039914](#tc-039914), [TC-039915](#tc-039915) | `5187270512804249 / 2809` | [TD-TC-039913.step2.reversal.json line 14](mastercard_credit_new_16_11/test_data/TD-TC-039913.step2.reversal.json#L14), [TD-TC-039913.step2.reversal.json line 15](mastercard_credit_new_16_11/test_data/TD-TC-039913.step2.reversal.json#L15) → `AccountNumber, CardDiscretionaryBlockData` | The message builder sends 5187270512804249=2809. No change in the test data. |
| Intended violation of a negative test (correct) | Step 2: same sequence number as the original - check failed | [TC-039913](#tc-039913) | `000002` | [TD-TC-039913.step2.reversal.json line 22](mastercard_credit_new_16_11/test_data/TD-TC-039913.step2.reversal.json#L22) → `Standard Message Data Segment → SequenceNumber` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `399131`). |
| Intended violation of a negative test (correct) | Step 2: same terminal as the original - check failed | [TC-039914](#tc-039914), [TC-039915](#tc-039915) | `VS24671841985066391107` | [TD-TC-039914.step2.reversal.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-039914.step2.reversal.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `JX72948216050257001`). |
| AI validator rule not applicable - value checked and correct | The chip data does not include the cash back | [TC-040126](#tc-040126), [TC-040240](#tc-040240) | `9F02=000000001503, 9F03=000000000001, 9C=09` | [TD-TC-040126.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040126.json#L54) → `EMV Request Data Segment → EMVChipData → Tags 9F02, 9F03, 9C` | The chip data (9F02 = total including the cash back, 9F03 = the cash back, 9C = 09) describes the cash-back amounts consistently; the AI rule expected 9C = 00 because the step is an Authorization Only. The real problem of this test - optional fields filled without their condition - is D-02. |
| Information | Same request as TC-040065 (BR1_TS11_TC6) | [TC-040140](#tc-040140), [TC-040254](#tc-040254), [TC-040396](#tc-040396), [TC-040571](#tc-040571), [TC-040683](#tc-040683), [TC-040800](#tc-040800), [TC-040919](#tc-040919), [TC-041032](#tc-041032), [TC-041083](#tc-041083), [TC-041101](#tc-041101) | `expected 2 vs 2` | TD-TC-040140.json vs TD-TC-040065.json | Check the difference on the device / in the response; optionally use another card. No change in the test data. |

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

<a id="tc-040080"></a>

### TC-040080 (BR1_TS11_TC21) - Happy path: Authorization Only sending Table 048 MasterCard Authentication Data

**Test name:** EMV Contact Chip Authorization Only with MasterCard Authentication Data → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 315](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L315) · **Every field against ATL105:** [Result_MD_Folder/TC-040080.md](Result_MD_Folder/TC-040080.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-10 | The test claims Table 048 MasterCard Authentication Data but the request does not send it | Test case defect | `Segment 111 tables sent: 005, 030, 031` | [TD-TC-040080.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040080.json#L27) → `Variable Information Data Segment` | Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 048 applies to e-commerce transactions authenticated with (EMV) 3-D Secure (Appendix I), so a card-present test must be re-scoped to such a transaction or withdrawn. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 315](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L315) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-03 | The quote cited for p.586 is not word-for-word in ATL105 | Citation issue | `spec_basis[0]` = `p.586: "Table 048 MasterCard Authentication Data"` | test case record [test_cases.jsonl line 315](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L315) → `spec_basis[0]` | Quote the text exactly as it appears on the page. |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[3]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 315](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L315) → `spec_basis[3]` | Cite p.160 instead of p.161. |
| I-04 | The CA Public Key File Checksum is a sample value | Awaiting client input / run-time value | `2012091700000100000102171` | [TD-TC-040080.json line 51](mastercard_credit_new_16_11/test_data/TD-TC-040080.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | Confirm the checksum of the host's current CA Public Key file before execution. |
| I-04 | TVR says 'online PIN entered' on a transaction without online PIN | Awaiting client input / run-time value | `95=8000048000, 9F34=5E0302` | [TD-TC-040080.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040080.json#L54) → `EMV Request Data Segment → EMVChipData → Tag 95` | Produce the TVR and the CVM Results together with a card simulator. |
| I-04 | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders | Awaiting client input / run-time value | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=D9509DAD, 95=8000048000` | [TD-TC-040080.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040080.json#L54) → `EMV Request Data Segment → EMVChipData` | Produce the chip data with a card simulator / HSM before execution. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040080.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040080.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| C-08 | Same request as TC-040065 (BR1_TS11_TC6) | To confirm with BUYPASS / SME | `expected 2 vs 2` | TD-TC-040080.json vs TD-TC-040065.json | Check the difference on the device / in the response; optionally use another card. |

<details><summary>Test data - actual, step 1: TD-TC-040080.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5177951757699269",
      "CardDiscretionaryBlockData": "290520101440570",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "400801",
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000480009A032601019C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608B4302C8AE8B7E91C9F2701809F3303E0F0C89F34035E03029F3501229F360201D09F3704D9509DAD9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-040080.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",  // I-01: recalculate: 070 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5177951757699269",
      "CardDiscretionaryBlockData": "290520101440570",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "400801",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "049",  // C-10: 042 + 7 for the Table 048 entry (indicator 3 + length 3 + 1 character)
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
        },
        {  // C-10: Table 048 MasterCard Authentication Data, as the test claims; Table 048 is for e-commerce with (EMV) 3-D Secure - re-scope this card-present test or withdraw it
          "VariableInformationIndicator": "048",
          "VariableInformationLength": "001",  // Program Protocol only (037 with the Directory Server Transaction ID)
          "VariableInformation": "1"  // the value the test case itself planned (tx_legs[0].generated_tables): 1 = EMV 3-D Secure Version 2.1
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000480009A032601019C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608B4302C8AE8B7E91C9F2701809F3303E0F0C89F34035E03029F3501229F360201D09F3704D9509DAD9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-040194"></a>

### TC-040194 (BR1_TS12_TC17) - Happy path: Authorization Only sending Table 048 MasterCard Authentication Data

**Test name:** EMV Contactless Chip Authorization Only with MasterCard Authentication Data → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 429](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L429) · **Every field against ATL105:** [Result_MD_Folder/TC-040194.md](Result_MD_Folder/TC-040194.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-10 | The test claims Table 048 MasterCard Authentication Data but the request does not send it | Test case defect | `Segment 111 tables sent: 005, 030, 031` | [TD-TC-040194.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040194.json#L27) → `Variable Information Data Segment` | Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 048 applies to e-commerce transactions authenticated with (EMV) 3-D Secure (Appendix I), so a card-present test must be re-scoped to such a transaction or withdrawn. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 429](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L429) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-03 | The quote cited for p.586 is not word-for-word in ATL105 | Citation issue | `spec_basis[0]` = `p.586: "Table 048 MasterCard Authentication Data"` | test case record [test_cases.jsonl line 429](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L429) → `spec_basis[0]` | Quote the text exactly as it appears on the page. |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[3]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 429](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L429) → `spec_basis[3]` | Cite p.160 instead of p.161. |
| I-04 | The CA Public Key File Checksum is a sample value | Awaiting client input / run-time value | `2012091700000100000102171` | [TD-TC-040194.json line 51](mastercard_credit_new_16_11/test_data/TD-TC-040194.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | Confirm the checksum of the host's current CA Public Key file before execution. |
| I-04 | TVR says 'online PIN entered' on a transaction without online PIN | Awaiting client input / run-time value | `95=8000048000, 9F34=5E0302` | [TD-TC-040194.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040194.json#L54) → `EMV Request Data Segment → EMVChipData → Tag 95` | Produce the TVR and the CVM Results together with a card simulator. |
| I-04 | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders | Awaiting client input / run-time value | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=2FB75EB4, 95=8000048000` | [TD-TC-040194.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040194.json#L54) → `EMV Request Data Segment → EMVChipData` | Produce the chip data with a card simulator / HSM before execution. |
| I-04 | Contactless chip data reuses the contact tag set | Awaiting client input / run-time value | `22 tags, CVM 9F34 = 5E0302` | [TD-TC-040194.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040194.json#L54) → `EMV Request Data Segment → EMVChipData` | Confirm the contactless chip data with a MasterCard contactless (M/Chip) kernel trace. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040194.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040194.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| C-08 | Same request as TC-040179 (BR1_TS12_TC2) | To confirm with BUYPASS / SME | `expected 2 vs 2` | TD-TC-040194.json vs TD-TC-040179.json | Check the difference on the device / in the response; optionally use another card. |

<details><summary>Test data - actual, step 1: TD-TC-040194.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5123037123672506",
      "CardDiscretionaryBlockData": "300520105478208",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "401941",
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
          "VariableInformation": "071"
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000480009A032601019C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608B4302C8AE8B7E91C9F2701809F3303E0F0C89F34035E03029F3501229F360201D09F37042FB75EB49F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-040194.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",  // I-01: recalculate: 070 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5123037123672506",
      "CardDiscretionaryBlockData": "300520105478208",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "401941",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "049",  // C-10: 042 + 7 for the Table 048 entry (indicator 3 + length 3 + 1 character)
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "071"
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
        },
        {  // C-10: Table 048 MasterCard Authentication Data, as the test claims; Table 048 is for e-commerce with (EMV) 3-D Secure - re-scope this card-present test or withdraw it
          "VariableInformationIndicator": "048",
          "VariableInformationLength": "001",  // Program Protocol only (037 with the Directory Server Transaction ID)
          "VariableInformation": "1"  // the value the test case itself planned (tx_legs[0].generated_tables): 1 = EMV 3-D Secure Version 2.1
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000480009A032601019C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608B4302C8AE8B7E91C9F2701809F3303E0F0C89F34035E03029F3501229F360201D09F37042FB75EB49F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-040308"></a>

### TC-040308 (BR1_TS13_TC17) - Happy path: Authorization Only sending Table 048 MasterCard Authentication Data

**Test name:** Magnetic Stripe Authorization Only with MasterCard Authentication Data → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 543](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L543) · **Every field against ATL105:** [Result_MD_Folder/TC-040308.md](Result_MD_Folder/TC-040308.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-10 | The test claims Table 048 MasterCard Authentication Data but the request does not send it | Test case defect | `Segment 111 tables sent: 005, 030, 031` | [TD-TC-040308.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040308.json#L27) → `Variable Information Data Segment` | Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 048 applies to e-commerce transactions authenticated with (EMV) 3-D Secure (Appendix I), so a card-present test must be re-scoped to such a transaction or withdrawn. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 543](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L543) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-03 | The quote cited for p.586 is not word-for-word in ATL105 | Citation issue | `spec_basis[0]` = `p.586: "Table 048 MasterCard Authentication Data"` | test case record [test_cases.jsonl line 543](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L543) → `spec_basis[0]` | Quote the text exactly as it appears on the page. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040308.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040308.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| C-08 | Same request as TC-040293 (BR1_TS13_TC2) | To confirm with BUYPASS / SME | `expected 2 vs 2` | TD-TC-040308.json vs TD-TC-040293.json | Check the difference on the device / in the response; optionally use another card. |

<details><summary>Test data - actual, step 1: TD-TC-040308.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5113711878437156",
      "CardDiscretionaryBlockData": "270410167649565",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "403081",
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
          "VariableInformation": "901"
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

<details open><summary>Test data - expected, step 1: TD-TC-040308.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",  // I-01: recalculate: 070 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5113711878437156",
      "CardDiscretionaryBlockData": "270410167649565",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "403081",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "049",  // C-10: 042 + 7 for the Table 048 entry (indicator 3 + length 3 + 1 character)
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "901"
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
        },
        {  // C-10: Table 048 MasterCard Authentication Data, as the test claims; Table 048 is for e-commerce with (EMV) 3-D Secure - re-scope this card-present test or withdraw it
          "VariableInformationIndicator": "048",
          "VariableInformationLength": "001",  // Program Protocol only (037 with the Directory Server Transaction ID)
          "VariableInformation": "1"  // the value the test case itself planned (tx_legs[0].generated_tables): 1 = EMV 3-D Secure Version 2.1
        }
      ]
    }
  }
}
```

</details>

<a id="tc-040512"></a>

### TC-040512 (BR1_TS14_TC16) - Happy path: Authorization Only sending Table 048 MasterCard Authentication Data

**Test name:** Contactless Magnetic Stripe Authorization Only with MasterCard Authentication Data → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 747](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L747) · **Every field against ATL105:** [Result_MD_Folder/TC-040512.md](Result_MD_Folder/TC-040512.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-10 | The test claims Table 048 MasterCard Authentication Data but the request does not send it | Test case defect | `Segment 111 tables sent: 005, 030, 031` | [TD-TC-040512.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040512.json#L27) → `Variable Information Data Segment` | Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 048 applies to e-commerce transactions authenticated with (EMV) 3-D Secure (Appendix I), so a card-present test must be re-scoped to such a transaction or withdrawn. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 747](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L747) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-03 | The quote cited for p.586 is not word-for-word in ATL105 | Citation issue | `spec_basis[0]` = `p.586: "Table 048 MasterCard Authentication Data"` | test case record [test_cases.jsonl line 747](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L747) → `spec_basis[0]` | Quote the text exactly as it appears on the page. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040512.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040512.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| C-08 | Same request as TC-040497 (BR1_TS14_TC1) | To confirm with BUYPASS / SME | `expected 2 vs 2` | TD-TC-040512.json vs TD-TC-040497.json | Check the difference on the device / in the response; optionally use another card. |

<details><summary>Test data - actual, step 1: TD-TC-040512.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5165598658574242",
      "CardDiscretionaryBlockData": "310720142895840",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "405121",
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
          "VariableInformation": "911"
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

<details open><summary>Test data - expected, step 1: TD-TC-040512.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",  // I-01: recalculate: 070 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5165598658574242",
      "CardDiscretionaryBlockData": "310720142895840",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "405121",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "049",  // C-10: 042 + 7 for the Table 048 entry (indicator 3 + length 3 + 1 character)
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "911"
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
        },
        {  // C-10: Table 048 MasterCard Authentication Data, as the test claims; Table 048 is for e-commerce with (EMV) 3-D Secure - re-scope this card-present test or withdraw it
          "VariableInformationIndicator": "048",
          "VariableInformationLength": "001",  // Program Protocol only (037 with the Directory Server Transaction ID)
          "VariableInformation": "1"  // the value the test case itself planned (tx_legs[0].generated_tables): 1 = EMV 3-D Secure Version 2.1
        }
      ]
    }
  }
}
```

</details>

<a id="tc-040623"></a>

### TC-040623 (BR1_TS15_TC17) - Happy path: Authorization Only sending Table 048 MasterCard Authentication Data

**Test name:** Manual / Keyed Authorization Only with MasterCard Authentication Data → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 858](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L858) · **Every field against ATL105:** [Result_MD_Folder/TC-040623.md](Result_MD_Folder/TC-040623.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-10 | The test claims Table 048 MasterCard Authentication Data but the request does not send it | Test case defect | `Segment 111 tables sent: 005, 030, 031` | [TD-TC-040623.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040623.json#L27) → `Variable Information Data Segment` | Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 048 applies to e-commerce transactions authenticated with (EMV) 3-D Secure (Appendix I), so a card-present test must be re-scoped to such a transaction or withdrawn. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 858](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L858) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-03 | The quote cited for p.586 is not word-for-word in ATL105 | Citation issue | `spec_basis[0]` = `p.586: "Table 048 MasterCard Authentication Data"` | test case record [test_cases.jsonl line 858](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L858) → `spec_basis[0]` | Quote the text exactly as it appears on the page. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040623.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040623.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| C-08 | Same request as TC-040608 (BR1_TS15_TC2) | To confirm with BUYPASS / SME | `expected 2 vs 2` | TD-TC-040623.json vs TD-TC-040608.json | Check the difference on the device / in the response; optionally use another card. |

<details><summary>Test data - actual, step 1: TD-TC-040623.json (click to expand)</summary>

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
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5140312601563116",
      "CardDiscretionaryBlockData": "0932",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "406231",
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

<details open><summary>Test data - expected, step 1: TD-TC-040623.json (changed lines end with //)</summary>

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
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5140312601563116",
      "CardDiscretionaryBlockData": "0932",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "406231",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "049",  // C-10: 042 + 7 for the Table 048 entry (indicator 3 + length 3 + 1 character)
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
        },
        {  // C-10: Table 048 MasterCard Authentication Data, as the test claims; Table 048 is for e-commerce with (EMV) 3-D Secure - re-scope this card-present test or withdraw it
          "VariableInformationIndicator": "048",
          "VariableInformationLength": "001",  // Program Protocol only (037 with the Directory Server Transaction ID)
          "VariableInformation": "1"  // the value the test case itself planned (tx_legs[0].generated_tables): 1 = EMV 3-D Secure Version 2.1
        }
      ]
    }
  }
}
```

</details>

<a id="tc-040737"></a>

### TC-040737 (BR1_TS16_TC16) - Happy path: Authorization Only sending Table 048 MasterCard Authentication Data

**Test name:** Chip Fallback Authorization Only with MasterCard Authentication Data → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 972](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L972) · **Every field against ATL105:** [Result_MD_Folder/TC-040737.md](Result_MD_Folder/TC-040737.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-10 | The test claims Table 048 MasterCard Authentication Data but the request does not send it | Test case defect | `Segment 111 tables sent: 005, 030, 031` | [TD-TC-040737.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040737.json#L27) → `Variable Information Data Segment` | Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 048 applies to e-commerce transactions authenticated with (EMV) 3-D Secure (Appendix I), so a card-present test must be re-scoped to such a transaction or withdrawn. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 972](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L972) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-03 | The quote cited for p.586 is not word-for-word in ATL105 | Citation issue | `spec_basis[0]` = `p.586: "Table 048 MasterCard Authentication Data"` | test case record [test_cases.jsonl line 972](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L972) → `spec_basis[0]` | Quote the text exactly as it appears on the page. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040737.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040737.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| C-08 | Same request as TC-040722 (BR1_TS16_TC1) | To confirm with BUYPASS / SME | `expected 2 vs 2` | TD-TC-040737.json vs TD-TC-040722.json | Check the difference on the device / in the response; optionally use another card. |

<details><summary>Test data - actual, step 1: TD-TC-040737.json (click to expand)</summary>

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
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5176689985215877",
      "CardDiscretionaryBlockData": "0729",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "407371",
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
          "VariableInformation": "791"
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

<details open><summary>Test data - expected, step 1: TD-TC-040737.json (changed lines end with //)</summary>

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
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5176689985215877",
      "CardDiscretionaryBlockData": "0729",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "407371",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "049",  // C-10: 042 + 7 for the Table 048 entry (indicator 3 + length 3 + 1 character)
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "791"
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
        },
        {  // C-10: Table 048 MasterCard Authentication Data, as the test claims; Table 048 is for e-commerce with (EMV) 3-D Secure - re-scope this card-present test or withdraw it
          "VariableInformationIndicator": "048",
          "VariableInformationLength": "001",  // Program Protocol only (037 with the Directory Server Transaction ID)
          "VariableInformation": "1"  // the value the test case itself planned (tx_legs[0].generated_tables): 1 = EMV 3-D Secure Version 2.1
        }
      ]
    }
  }
}
```

</details>

<a id="tc-040856"></a>

### TC-040856 (BR1_TS17_TC23) - Happy path: Authorization Only sending Table 048 MasterCard Authentication Data

**Test name:** Credential on File Authorization Only with MasterCard Authentication Data → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 1091](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1091) · **Every field against ATL105:** [Result_MD_Folder/TC-040856.md](Result_MD_Folder/TC-040856.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-10 | The test claims Table 048 MasterCard Authentication Data but the request does not send it | Test case defect | `Segment 111 tables sent: 005, 030, 031, 032, 049, 056` | [TD-TC-040856.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040856.json#L27) → `Variable Information Data Segment` | Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 048 applies to e-commerce transactions authenticated with (EMV) 3-D Secure (Appendix I), so confirm this card-not-present test is such a transaction, or re-scope / withdraw it. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 1091](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1091) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-03 | The quote cited for p.586 is not word-for-word in ATL105 | Citation issue | `spec_basis[0]` = `p.586: "Table 048 MasterCard Authentication Data"` | test case record [test_cases.jsonl line 1091](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1091) → `spec_basis[0]` | Quote the text exactly as it appears on the page. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040856.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040856.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| S-01 | Expiry format for stored credentials is not stated | To confirm with BUYPASS / SME | `1127` | Card Discretionary Block Data (entry mode 10 steps) | Confirm the expiry format for entry mode 10 with BUYPASS. |
| C-08 | Same request as TC-040837 (BR1_TS17_TC4) | To confirm with BUYPASS / SME | `expected 2 vs 2` | TD-TC-040856.json vs TD-TC-040837.json | Check the difference on the device / in the response; optionally use another card. |

<details><summary>Test data - actual, step 1: TD-TC-040856.json (click to expand)</summary>

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
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5186650025425825",
      "CardDiscretionaryBlockData": "1127",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "408561",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "078",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "101"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100100004"
        },
        {
          "VariableInformationIndicator": "032",
          "VariableInformationLength": "006",
          "VariableInformation": "08001C"
        },
        {
          "VariableInformationIndicator": "049",
          "VariableInformationLength": "004",
          "VariableInformation": "021C"
        },
        {
          "VariableInformationIndicator": "056",
          "VariableInformationLength": "008",
          "VariableInformation": "0904C101"
        }
      ]
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-040856.json (changed lines end with //)</summary>

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
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5186650025425825",
      "CardDiscretionaryBlockData": "1127",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "408561",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "085",  // C-10: 078 + 7 for the Table 048 entry (indicator 3 + length 3 + 1 character)
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "101"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100100004"
        },
        {
          "VariableInformationIndicator": "032",
          "VariableInformationLength": "006",
          "VariableInformation": "08001C"
        },
        {
          "VariableInformationIndicator": "049",
          "VariableInformationLength": "004",
          "VariableInformation": "021C"
        },
        {
          "VariableInformationIndicator": "056",
          "VariableInformationLength": "008",
          "VariableInformation": "0904C101"
        },
        {  // C-10: Table 048 MasterCard Authentication Data, as the test claims; Table 048 is for e-commerce with (EMV) 3-D Secure - confirm this card-not-present transaction is one, or re-scope / withdraw it
          "VariableInformationIndicator": "048",
          "VariableInformationLength": "001",  // Program Protocol only (037 with the Directory Server Transaction ID)
          "VariableInformation": "1"  // the value the test case itself planned (tx_legs[0].generated_tables): 1 = EMV 3-D Secure Version 2.1
        }
      ]
    }
  }
}
```

</details>

<a id="tc-040974"></a>

### TC-040974 (BR1_TS18_TC16) - Happy path: Authorization Only sending Table 048 MasterCard Authentication Data

**Test name:** Contactless Mobile Commerce Device Authorization Only with MasterCard Authentication Data → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `pending_sme` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 1209](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1209) · **Every field against ATL105:** [Result_MD_Folder/TC-040974.md](Result_MD_Folder/TC-040974.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-10 | The test claims Table 048 MasterCard Authentication Data but the request does not send it | Test case defect | `Segment 111 tables sent: 005, 030, 031` | [TD-TC-040974.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040974.json#L27) → `Variable Information Data Segment` | Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 048 applies to e-commerce transactions authenticated with (EMV) 3-D Secure (Appendix I), so a card-present test must be re-scoped to such a transaction or withdrawn. |
| Q-03 | The quote cited for p.586 is not word-for-word in ATL105 | Citation issue | `spec_basis[0]` = `p.586: "Table 048 MasterCard Authentication Data"` | test case record [test_cases.jsonl line 1209](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1209) → `spec_basis[0]` | Quote the text exactly as it appears on the page. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040974.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040974.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| C-08 | Same request as TC-040959 (BR1_TS18_TC1) | To confirm with BUYPASS / SME | `expected 2 vs 2` | TD-TC-040974.json vs TD-TC-040959.json | Check the difference on the device / in the response; optionally use another card. |

<details><summary>Test data - actual, step 1: TD-TC-040974.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5191864178826274",
      "CardDiscretionaryBlockData": "311220125996579",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "409741",
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
          "VariableInformation": "821"
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

<details open><summary>Test data - expected, step 1: TD-TC-040974.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",  // I-01: recalculate: 070 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5191864178826274",
      "CardDiscretionaryBlockData": "311220125996579",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "409741",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "049",  // C-10: 042 + 7 for the Table 048 entry (indicator 3 + length 3 + 1 character)
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "821"
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
        },
        {  // C-10: Table 048 MasterCard Authentication Data, as the test claims; Table 048 is for e-commerce with (EMV) 3-D Secure - re-scope this card-present test or withdraw it
          "VariableInformationIndicator": "048",
          "VariableInformationLength": "001",  // Program Protocol only (037 with the Directory Server Transaction ID)
          "VariableInformation": "1"  // the value the test case itself planned (tx_legs[0].generated_tables): 1 = EMV 3-D Secure Version 2.1
        }
      ]
    }
  }
}
```

</details>

<a id="tc-041219"></a>

### TC-041219 (BR1_TS54_TC14) - Happy path: Authorization Only sending Table 048 MasterCard Authentication Data

**Test name:** SAMPLE Hospitality · attended POS EMV Contact Chip Authorization Only with MasterCard Authentication Data → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `needs_client_input` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 1454](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1454) · **Every field against ATL105:** [Result_MD_Folder/TC-041219.md](Result_MD_Folder/TC-041219.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-10 | The test claims Table 048 MasterCard Authentication Data but the request does not send it | Test case defect | `Segment 111 tables sent: 005, 030, 031` | [TD-TC-041219.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-041219.json#L27) → `Variable Information Data Segment` | Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 048 applies to e-commerce transactions authenticated with (EMV) 3-D Secure (Appendix I), so a card-present test must be re-scoped to such a transaction or withdrawn. |
| Q-03 | The quote cited for p.586 is not word-for-word in ATL105 | Citation issue | `spec_basis[0]` = `p.586: "Table 048 MasterCard Authentication Data"` | test case record [test_cases.jsonl line 1454](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1454) → `spec_basis[0]` | Quote the text exactly as it appears on the page. |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[3]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 1454](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1454) → `spec_basis[3]` | Cite p.160 instead of p.161. |
| I-04 | The CA Public Key File Checksum is a sample value | Awaiting client input / run-time value | `2012091700000100000102171` | [TD-TC-041219.json line 51](mastercard_credit_new_16_11/test_data/TD-TC-041219.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | Confirm the checksum of the host's current CA Public Key file before execution. |
| I-04 | TVR says 'online PIN entered' on a transaction without online PIN | Awaiting client input / run-time value | `95=8000048000, 9F34=5E0302` | [TD-TC-041219.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-041219.json#L54) → `EMV Request Data Segment → EMVChipData → Tag 95` | Produce the TVR and the CVM Results together with a card simulator. |
| I-04 | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders | Awaiting client input / run-time value | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=3EAF5E40, 95=8000048000` | [TD-TC-041219.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-041219.json#L54) → `EMV Request Data Segment → EMVChipData` | Produce the chip data with a card simulator / HSM before execution. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `QU67070249149717001` | [TD-TC-041219.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-041219.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| C-08 | Same request as TC-041206 (BR1_TS54_TC1) | To confirm with BUYPASS / SME | `expected 2 vs 2` | TD-TC-041219.json vs TD-TC-041206.json | Check the difference on the device / in the response; optionally use another card. |

<details><summary>Test data - actual, step 1: TD-TC-041219.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",
      "InformationByte": "0",
      "TerminalIdentifier": "QU67070249149717001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5107253659844050",
      "CardDiscretionaryBlockData": "290820137544256",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "412191",
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000480009A032601019C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608B4302C8AE8B7E91C9F2701809F3303E0F0C89F34035E03029F3501229F360201D09F37043EAF5E409F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-041219.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",  // I-01: recalculate: 070 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5107253659844050",
      "CardDiscretionaryBlockData": "290820137544256",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "412191",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "049",  // C-10: 042 + 7 for the Table 048 entry (indicator 3 + length 3 + 1 character)
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
        },
        {  // C-10: Table 048 MasterCard Authentication Data, as the test claims; Table 048 is for e-commerce with (EMV) 3-D Secure - re-scope this card-present test or withdraw it
          "VariableInformationIndicator": "048",
          "VariableInformationLength": "001",  // Program Protocol only (037 with the Directory Server Transaction ID)
          "VariableInformation": "1"  // the value the test case itself planned (tx_legs[0].generated_tables): 1 = EMV 3-D Secure Version 2.1
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000480009A032601019C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608B4302C8AE8B7E91C9F2701809F3303E0F0C89F34035E03029F3501229F360201D09F37043EAF5E409F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-041315"></a>

### TC-041315 (BR1_TS154_TC19) - Happy path: Authorization Only sending Table 048 MasterCard Authentication Data

**Test name:** SAMPLE Petroleum · attended POS EMV Contact Chip Authorization Only with MasterCard Authentication Data → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `needs_client_input` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 1550](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1550) · **Every field against ATL105:** [Result_MD_Folder/TC-041315.md](Result_MD_Folder/TC-041315.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-10 | The test claims Table 048 MasterCard Authentication Data but the request does not send it | Test case defect | `Segment 111 tables sent: 005, 030, 031` | [TD-TC-041315.json line 38](mastercard_credit_new_16_11/test_data/TD-TC-041315.json#L38) → `Variable Information Data Segment` | Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 048 applies to e-commerce transactions authenticated with (EMV) 3-D Secure (Appendix I), so a card-present test must be re-scoped to such a transaction or withdrawn. |
| Q-03 | The quote cited for p.586 is not word-for-word in ATL105 | Citation issue | `spec_basis[0]` = `p.586: "Table 048 MasterCard Authentication Data"` | test case record [test_cases.jsonl line 1550](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1550) → `spec_basis[0]` | Quote the text exactly as it appears on the page. |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[3]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 1550](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1550) → `spec_basis[3]` | Cite p.160 instead of p.161. |
| I-04 | The CA Public Key File Checksum is a sample value | Awaiting client input / run-time value | `2012091700000100000102171` | [TD-TC-041315.json line 62](mastercard_credit_new_16_11/test_data/TD-TC-041315.json#L62) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | Confirm the checksum of the host's current CA Public Key file before execution. |
| I-04 | TVR says 'online PIN entered' on a transaction without online PIN | Awaiting client input / run-time value | `95=8000048000, 9F34=5E0302` | [TD-TC-041315.json line 65](mastercard_credit_new_16_11/test_data/TD-TC-041315.json#L65) → `EMV Request Data Segment → EMVChipData → Tag 95` | Produce the TVR and the CVM Results together with a card simulator. |
| I-04 | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders | Awaiting client input / run-time value | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=255978D3, 95=8000048000` | [TD-TC-041315.json line 65](mastercard_credit_new_16_11/test_data/TD-TC-041315.json#L65) → `EMV Request Data Segment → EMVChipData` | Produce the chip data with a card simulator / HSM before execution. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `KC57457075622174001` | [TD-TC-041315.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-041315.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| C-08 | Same request as TC-041301 (BR1_TS154_TC5) | To confirm with BUYPASS / SME | `expected 2 vs 2` | TD-TC-041315.json vs TD-TC-041301.json | Check the difference on the device / in the response; optionally use another card. |

<details><summary>Test data - actual, step 1: TD-TC-041315.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "04",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",
      "InformationByte": "0",
      "TerminalIdentifier": "KC57457075622174001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5169671040305215",
      "CardDiscretionaryBlockData": "290320147161544",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "413151",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Product Code Data Segment": {
      "SegmentType": "102",
      "SegmentLength": "029",
      "ServiceLevel": "F",
      "NumberofProducts": "01",
      "ProductCode": "400",
      "UnitofMeasure": "U",
      "Quantity": "01",
      "UnitPrice": "21500",
      "ProductAmount": "1500"
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000480009A032601019C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608B4302C8AE8B7E91C9F2701809F3303E0F0C89F34035E03029F3501229F360201D09F3704255978D39F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-041315.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "04",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",  // I-01: recalculate: 070 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5169671040305215",
      "CardDiscretionaryBlockData": "290320147161544",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "413151",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Product Code Data Segment": {
      "SegmentType": "102",
      "SegmentLength": "029",
      "ServiceLevel": "F",
      "NumberofProducts": "01",
      "ProductCode": "400",
      "UnitofMeasure": "U",
      "Quantity": "01",
      "UnitPrice": "21500",
      "ProductAmount": "1500"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "049",  // C-10: 042 + 7 for the Table 048 entry (indicator 3 + length 3 + 1 character)
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
        },
        {  // C-10: Table 048 MasterCard Authentication Data, as the test claims; Table 048 is for e-commerce with (EMV) 3-D Secure - re-scope this card-present test or withdraw it
          "VariableInformationIndicator": "048",
          "VariableInformationLength": "001",  // Program Protocol only (037 with the Directory Server Transaction ID)
          "VariableInformation": "1"  // the value the test case itself planned (tx_legs[0].generated_tables): 1 = EMV 3-D Secure Version 2.1
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000480009A032601019C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608B4302C8AE8B7E91C9F2701809F3303E0F0C89F34035E03029F3501229F360201D09F3704255978D39F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-041606"></a>

### TC-041606 (BR1_TS173_TC15) - Happy path: Authorization Only sending Table 048 MasterCard Authentication Data

**Test name:** SAMPLE Petroleum · unattended CAT (fuel machine) EMV Contact Chip Authorization Only with MasterCard Authentication Data → approved. **What the test does:** step 1 sends `5020` (Customer-activated Authorization Only), expected Response Code `2`.

**Status:** `needs_client_input` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 1841](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1841) · **Every field against ATL105:** [Result_MD_Folder/TC-041606.md](Result_MD_Folder/TC-041606.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-10 | The test claims Table 048 MasterCard Authentication Data but the request does not send it | Test case defect | `Segment 111 tables sent: 005, 030, 031` | [TD-TC-041606.json line 38](mastercard_credit_new_16_11/test_data/TD-TC-041606.json#L38) → `Variable Information Data Segment` | Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 048 applies to e-commerce transactions authenticated with (EMV) 3-D Secure (Appendix I), so a card-present test must be re-scoped to such a transaction or withdrawn. |
| Q-03 | The quote cited for p.586 is not word-for-word in ATL105 | Citation issue | `spec_basis[0]` = `p.586: "Table 048 MasterCard Authentication Data"` | test case record [test_cases.jsonl line 1841](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1841) → `spec_basis[0]` | Quote the text exactly as it appears on the page. |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[3]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 1841](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1841) → `spec_basis[3]` | Cite p.160 instead of p.161. |
| I-04 | The CA Public Key File Checksum is a sample value | Awaiting client input / run-time value | `2012091700000100000102171` | [TD-TC-041606.json line 62](mastercard_credit_new_16_11/test_data/TD-TC-041606.json#L62) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | Confirm the checksum of the host's current CA Public Key file before execution. |
| I-04 | TVR says 'online PIN entered' on a transaction without online PIN | Awaiting client input / run-time value | `95=8000048000, 9F34=5E0302` | [TD-TC-041606.json line 65](mastercard_credit_new_16_11/test_data/TD-TC-041606.json#L65) → `EMV Request Data Segment → EMVChipData → Tag 95` | Produce the TVR and the CVM Results together with a card simulator. |
| I-04 | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders | Awaiting client input / run-time value | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=F6A5A51F, 95=8000048000` | [TD-TC-041606.json line 65](mastercard_credit_new_16_11/test_data/TD-TC-041606.json#L65) → `EMV Request Data Segment → EMVChipData` | Produce the chip data with a card simulator / HSM before execution. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `VZ26598162666071001` | [TD-TC-041606.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-041606.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| C-08 | Same request as TC-041592 (BR1_TS173_TC1) | To confirm with BUYPASS / SME | `expected 2 vs 2` | TD-TC-041606.json vs TD-TC-041592.json | Check the difference on the device / in the response; optionally use another card. |

<details><summary>Test data - actual, step 1: TD-TC-041606.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "04",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "092",
      "InformationByte": "0",
      "TerminalIdentifier": "VZ26598162666071001",
      "PromptCode": {
        "TransactionType": "5",
        "CardType": "020"
      },
      "AccountNumber": "5152520381517764",
      "CardDiscretionaryBlockData": "280520109263771",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "01",
      "FuelPurchaseAmount": "1500",
      "NonfuelAmount": "0",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "416061",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Product Code Data Segment": {
      "SegmentType": "102",
      "SegmentLength": "029",
      "ServiceLevel": "F",
      "NumberofProducts": "01",
      "ProductCode": "001",
      "UnitofMeasure": "G",
      "Quantity": "01",
      "UnitPrice": "21500",
      "ProductAmount": "1500"
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
          "VariableInformation": "00"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "1000000007"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000480009A032601019C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608B4302C8AE8B7E91C9F2701809F3303E0F0C89F34035E03029F3501229F360201D09F3704F6A5A51F9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-041606.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "04",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "092",  // I-01: recalculate: 073 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "5",
        "CardType": "020"
      },
      "AccountNumber": "5152520381517764",
      "CardDiscretionaryBlockData": "280520109263771",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "01",
      "FuelPurchaseAmount": "1500",
      "NonfuelAmount": "0",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "416061",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Product Code Data Segment": {
      "SegmentType": "102",
      "SegmentLength": "029",
      "ServiceLevel": "F",
      "NumberofProducts": "01",
      "ProductCode": "001",
      "UnitofMeasure": "G",
      "Quantity": "01",
      "UnitPrice": "21500",
      "ProductAmount": "1500"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "049",  // C-10: 042 + 7 for the Table 048 entry (indicator 3 + length 3 + 1 character)
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "00"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "1000000007"
        },
        {  // C-10: Table 048 MasterCard Authentication Data, as the test claims; Table 048 is for e-commerce with (EMV) 3-D Secure - re-scope this card-present test or withdraw it
          "VariableInformationIndicator": "048",
          "VariableInformationLength": "001",  // Program Protocol only (037 with the Directory Server Transaction ID)
          "VariableInformation": "1"  // the value the test case itself planned (tx_legs[0].generated_tables): 1 = EMV 3-D Secure Version 2.1
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000480009A032601019C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608B4302C8AE8B7E91C9F2701809F3303E0F0C89F34035E03029F3501229F360201D09F3704F6A5A51F9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-040116"></a>

### TC-040116 (BR1_TS11_TC57) - Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds)

**Test name:** EMV Contact Chip Authorization Only with Tax Type 1 = GST → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 351](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L351) · **Every field against ATL105:** [Result_MD_Folder/TC-040116.md](Result_MD_Folder/TC-040116.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-10 | The test claims Tax Type 1 = GST but the request does not send it | Test case defect | `Tax Type 1: not in the test data; segments sent: 100, 111, 130` | [TD-TC-040116.json line 2](mastercard_credit_new_16_11/test_data/TD-TC-040116.json#L2) → `EMV Financial Transaction Request` | Send Segment 102 (Product Code) and Segment 143 (Tax by Product) with the same product(s) in the same order, the inclusive/exclusive flag (I or E), Tax Type 1 = GST and the tax amount; recalculate the segment count and lengths. GST is a Canadian tax type and Segment 143 is sent only if the account manager instructs it (Section 11.1), so confirm the merchant context - or re-scope / withdraw the test. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 351](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L351) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[3]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 351](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L351) → `spec_basis[3]` | Cite p.160 instead of p.161. |
| I-04 | The CA Public Key File Checksum is a sample value | Awaiting client input / run-time value | `2012091700000100000102171` | [TD-TC-040116.json line 51](mastercard_credit_new_16_11/test_data/TD-TC-040116.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | Confirm the checksum of the host's current CA Public Key file before execution. |
| I-04 | TVR says 'online PIN entered' on a transaction without online PIN | Awaiting client input / run-time value | `95=8000048000, 9F34=5E0302` | [TD-TC-040116.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040116.json#L54) → `EMV Request Data Segment → EMVChipData → Tag 95` | Produce the TVR and the CVM Results together with a card simulator. |
| I-04 | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders | Awaiting client input / run-time value | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=733C27AF, 95=8000048000` | [TD-TC-040116.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040116.json#L54) → `EMV Request Data Segment → EMVChipData` | Produce the chip data with a card simulator / HSM before execution. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040116.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040116.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |

<details><summary>Test data - actual, step 1: TD-TC-040116.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5178588476140867",
      "CardDiscretionaryBlockData": "320120108983647",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "401161",
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000480009A032601019C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608B4302C8AE8B7E91C9F2701809F3303E0F0C89F34035E03029F3501229F360201D09F3704733C27AF9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-040116.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "05",  // C-10: + Segments 102 and 143
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",  // I-01: recalculate: 070 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5178588476140867",
      "CardDiscretionaryBlockData": "320120108983647",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "401161",
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000480009A032601019C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608B4302C8AE8B7E91C9F2701809F3303E0F0C89F34035E03029F3501229F360201D09F3704733C27AF9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    },
    "Product Code Data Segment": {  // C-10: Segment 143 needs Segment 102
      "SegmentType": "102",
      "SegmentLength": "029",  // whole segment: type, length, 3 Field Separators and the data with its 2 '\' separators
      "ServiceLevel": "O",  // O = Other/Fuel not present (Element 87)
      "NumberofProducts": "01",
      "ProductCode": "400",  // 400 = General merchandise (Appendix F) - client to confirm
      "UnitofMeasure": "U",  // U = Unit (Element 106)
      "Quantity": "01",  // 1 (leading digit = decimal places)
      "UnitPrice": "21500",  // leading 2 = two decimal places
      "ProductAmount": "1500"  // = Nonfuel Amount
    },
    "Tax by Product Data Segment": {  // C-10: Tax Type 1 = GST, as the test claims
      "SegmentType": "143",
      "SegmentLength": "020",  // counted like Segment 102 - the one-product Segment 143 examples show 023 for 21 characters; confirm with BUYPASS
      "NumberofProducts": "01",  // same products, same order as Segment 102
      "ProductCode": "400",
      "InclusiveExclusiveforTax1": "I",  // I = tax included in the amount
      "TaxType1": "GST",  // the claimed value
      "TaxAmount1": "71"  // example: 5% GST included in the amount - client to supply
    }
  }
}
```

</details>

<a id="tc-040230"></a>

### TC-040230 (BR1_TS12_TC53) - Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds)

**Test name:** EMV Contactless Chip Authorization Only with Tax Type 1 = GST → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 465](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L465) · **Every field against ATL105:** [Result_MD_Folder/TC-040230.md](Result_MD_Folder/TC-040230.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-10 | The test claims Tax Type 1 = GST but the request does not send it | Test case defect | `Tax Type 1: not in the test data; segments sent: 100, 111, 130` | [TD-TC-040230.json line 2](mastercard_credit_new_16_11/test_data/TD-TC-040230.json#L2) → `EMV Financial Transaction Request` | Send Segment 102 (Product Code) and Segment 143 (Tax by Product) with the same product(s) in the same order, the inclusive/exclusive flag (I or E), Tax Type 1 = GST and the tax amount; recalculate the segment count and lengths. GST is a Canadian tax type and Segment 143 is sent only if the account manager instructs it (Section 11.1), so confirm the merchant context - or re-scope / withdraw the test. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 465](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L465) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[3]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 465](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L465) → `spec_basis[3]` | Cite p.160 instead of p.161. |
| I-04 | The CA Public Key File Checksum is a sample value | Awaiting client input / run-time value | `2012091700000100000102171` | [TD-TC-040230.json line 51](mastercard_credit_new_16_11/test_data/TD-TC-040230.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | Confirm the checksum of the host's current CA Public Key file before execution. |
| I-04 | TVR says 'online PIN entered' on a transaction without online PIN | Awaiting client input / run-time value | `95=8000048000, 9F34=5E0302` | [TD-TC-040230.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040230.json#L54) → `EMV Request Data Segment → EMVChipData → Tag 95` | Produce the TVR and the CVM Results together with a card simulator. |
| I-04 | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders | Awaiting client input / run-time value | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=91FCEF97, 95=8000048000` | [TD-TC-040230.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040230.json#L54) → `EMV Request Data Segment → EMVChipData` | Produce the chip data with a card simulator / HSM before execution. |
| I-04 | Contactless chip data reuses the contact tag set | Awaiting client input / run-time value | `22 tags, CVM 9F34 = 5E0302` | [TD-TC-040230.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040230.json#L54) → `EMV Request Data Segment → EMVChipData` | Confirm the contactless chip data with a MasterCard contactless (M/Chip) kernel trace. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040230.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040230.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |

<details><summary>Test data - actual, step 1: TD-TC-040230.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5173975756699818",
      "CardDiscretionaryBlockData": "270520144809900",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "402301",
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
          "VariableInformation": "071"
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000480009A032601019C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608B4302C8AE8B7E91C9F2701809F3303E0F0C89F34035E03029F3501229F360201D09F370491FCEF979F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-040230.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "05",  // C-10: + Segments 102 and 143
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",  // I-01: recalculate: 070 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5173975756699818",
      "CardDiscretionaryBlockData": "270520144809900",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "402301",
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
          "VariableInformation": "071"
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000480009A032601019C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608B4302C8AE8B7E91C9F2701809F3303E0F0C89F34035E03029F3501229F360201D09F370491FCEF979F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    },
    "Product Code Data Segment": {  // C-10: Segment 143 needs Segment 102
      "SegmentType": "102",
      "SegmentLength": "029",  // whole segment: type, length, 3 Field Separators and the data with its 2 '\' separators
      "ServiceLevel": "O",  // O = Other/Fuel not present (Element 87)
      "NumberofProducts": "01",
      "ProductCode": "400",  // 400 = General merchandise (Appendix F) - client to confirm
      "UnitofMeasure": "U",  // U = Unit (Element 106)
      "Quantity": "01",  // 1 (leading digit = decimal places)
      "UnitPrice": "21500",  // leading 2 = two decimal places
      "ProductAmount": "1500"  // = Nonfuel Amount
    },
    "Tax by Product Data Segment": {  // C-10: Tax Type 1 = GST, as the test claims
      "SegmentType": "143",
      "SegmentLength": "020",  // counted like Segment 102 - the one-product Segment 143 examples show 023 for 21 characters; confirm with BUYPASS
      "NumberofProducts": "01",  // same products, same order as Segment 102
      "ProductCode": "400",
      "InclusiveExclusiveforTax1": "I",  // I = tax included in the amount
      "TaxType1": "GST",  // the claimed value
      "TaxAmount1": "71"  // example: 5% GST included in the amount - client to supply
    }
  }
}
```

</details>

<a id="tc-040346"></a>

### TC-040346 (BR1_TS13_TC55) - Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds)

**Test name:** Magnetic Stripe Authorization Only with Tax Type 1 = GST → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 581](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L581) · **Every field against ATL105:** [Result_MD_Folder/TC-040346.md](Result_MD_Folder/TC-040346.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-10 | The test claims Tax Type 1 = GST but the request does not send it | Test case defect | `Tax Type 1: not in the test data; segments sent: 100, 111` | [TD-TC-040346.json line 2](mastercard_credit_new_16_11/test_data/TD-TC-040346.json#L2) → `Financial Transaction Request` | Send Segment 102 (Product Code) and Segment 143 (Tax by Product) with the same product(s) in the same order, the inclusive/exclusive flag (I or E), Tax Type 1 = GST and the tax amount; recalculate the segment count and lengths. GST is a Canadian tax type and Segment 143 is sent only if the account manager instructs it (Section 11.1), so confirm the merchant context - or re-scope / withdraw the test. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 581](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L581) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040346.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040346.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |

<details><summary>Test data - actual, step 1: TD-TC-040346.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5104116011325360",
      "CardDiscretionaryBlockData": "300210158191524",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "403461",
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
          "VariableInformation": "901"
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

<details open><summary>Test data - expected, step 1: TD-TC-040346.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "04",  // C-10: + Segments 102 and 143
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",  // I-01: recalculate: 070 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5104116011325360",
      "CardDiscretionaryBlockData": "300210158191524",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "403461",
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
          "VariableInformation": "901"
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
    "Product Code Data Segment": {  // C-10: Segment 143 needs Segment 102
      "SegmentType": "102",
      "SegmentLength": "029",  // whole segment: type, length, 3 Field Separators and the data with its 2 '\' separators
      "ServiceLevel": "O",  // O = Other/Fuel not present (Element 87)
      "NumberofProducts": "01",
      "ProductCode": "400",  // 400 = General merchandise (Appendix F) - client to confirm
      "UnitofMeasure": "U",  // U = Unit (Element 106)
      "Quantity": "01",  // 1 (leading digit = decimal places)
      "UnitPrice": "21500",  // leading 2 = two decimal places
      "ProductAmount": "1500"  // = Nonfuel Amount
    },
    "Tax by Product Data Segment": {  // C-10: Tax Type 1 = GST, as the test claims
      "SegmentType": "143",
      "SegmentLength": "020",  // counted like Segment 102 - the one-product Segment 143 examples show 023 for 21 characters; confirm with BUYPASS
      "NumberofProducts": "01",  // same products, same order as Segment 102
      "ProductCode": "400",
      "InclusiveExclusiveforTax1": "I",  // I = tax included in the amount
      "TaxType1": "GST",  // the claimed value
      "TaxAmount1": "71"  // example: 5% GST included in the amount - client to supply
    }
  }
}
```

</details>

<a id="tc-040547"></a>

### TC-040547 (BR1_TS14_TC51) - Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds)

**Test name:** Contactless Magnetic Stripe Authorization Only with Tax Type 1 = GST → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 782](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L782) · **Every field against ATL105:** [Result_MD_Folder/TC-040547.md](Result_MD_Folder/TC-040547.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-10 | The test claims Tax Type 1 = GST but the request does not send it | Test case defect | `Tax Type 1: not in the test data; segments sent: 100, 111` | [TD-TC-040547.json line 2](mastercard_credit_new_16_11/test_data/TD-TC-040547.json#L2) → `Financial Transaction Request` | Send Segment 102 (Product Code) and Segment 143 (Tax by Product) with the same product(s) in the same order, the inclusive/exclusive flag (I or E), Tax Type 1 = GST and the tax amount; recalculate the segment count and lengths. GST is a Canadian tax type and Segment 143 is sent only if the account manager instructs it (Section 11.1), so confirm the merchant context - or re-scope / withdraw the test. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 782](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L782) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040547.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040547.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |

<details><summary>Test data - actual, step 1: TD-TC-040547.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5132869732106784",
      "CardDiscretionaryBlockData": "310120138804472",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "405471",
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
          "VariableInformation": "911"
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

<details open><summary>Test data - expected, step 1: TD-TC-040547.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "04",  // C-10: + Segments 102 and 143
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",  // I-01: recalculate: 070 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5132869732106784",
      "CardDiscretionaryBlockData": "310120138804472",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "405471",
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
          "VariableInformation": "911"
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
    "Product Code Data Segment": {  // C-10: Segment 143 needs Segment 102
      "SegmentType": "102",
      "SegmentLength": "029",  // whole segment: type, length, 3 Field Separators and the data with its 2 '\' separators
      "ServiceLevel": "O",  // O = Other/Fuel not present (Element 87)
      "NumberofProducts": "01",
      "ProductCode": "400",  // 400 = General merchandise (Appendix F) - client to confirm
      "UnitofMeasure": "U",  // U = Unit (Element 106)
      "Quantity": "01",  // 1 (leading digit = decimal places)
      "UnitPrice": "21500",  // leading 2 = two decimal places
      "ProductAmount": "1500"  // = Nonfuel Amount
    },
    "Tax by Product Data Segment": {  // C-10: Tax Type 1 = GST, as the test claims
      "SegmentType": "143",
      "SegmentLength": "020",  // counted like Segment 102 - the one-product Segment 143 examples show 023 for 21 characters; confirm with BUYPASS
      "NumberofProducts": "01",  // same products, same order as Segment 102
      "ProductCode": "400",
      "InclusiveExclusiveforTax1": "I",  // I = tax included in the amount
      "TaxType1": "GST",  // the claimed value
      "TaxAmount1": "71"  // example: 5% GST included in the amount - client to supply
    }
  }
}
```

</details>

<a id="tc-040658"></a>

### TC-040658 (BR1_TS15_TC52) - Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds)

**Test name:** Manual / Keyed Authorization Only with Tax Type 1 = GST → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 893](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L893) · **Every field against ATL105:** [Result_MD_Folder/TC-040658.md](Result_MD_Folder/TC-040658.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-10 | The test claims Tax Type 1 = GST but the request does not send it | Test case defect | `Tax Type 1: not in the test data; segments sent: 100, 111` | [TD-TC-040658.json line 2](mastercard_credit_new_16_11/test_data/TD-TC-040658.json#L2) → `Financial Transaction Request` | Send Segment 102 (Product Code) and Segment 143 (Tax by Product) with the same product(s) in the same order, the inclusive/exclusive flag (I or E), Tax Type 1 = GST and the tax amount; recalculate the segment count and lengths. GST is a Canadian tax type and Segment 143 is sent only if the account manager instructs it (Section 11.1), so confirm the merchant context - or re-scope / withdraw the test. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 893](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L893) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040658.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040658.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |

<details><summary>Test data - actual, step 1: TD-TC-040658.json (click to expand)</summary>

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
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5118957673039326",
      "CardDiscretionaryBlockData": "0130",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "406581",
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

<details open><summary>Test data - expected, step 1: TD-TC-040658.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "04",  // C-10: + Segments 102 and 143
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "078",  // I-01: recalculate: 059 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5118957673039326",
      "CardDiscretionaryBlockData": "0130",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "406581",
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
    "Product Code Data Segment": {  // C-10: Segment 143 needs Segment 102
      "SegmentType": "102",
      "SegmentLength": "029",  // whole segment: type, length, 3 Field Separators and the data with its 2 '\' separators
      "ServiceLevel": "O",  // O = Other/Fuel not present (Element 87)
      "NumberofProducts": "01",
      "ProductCode": "400",  // 400 = General merchandise (Appendix F) - client to confirm
      "UnitofMeasure": "U",  // U = Unit (Element 106)
      "Quantity": "01",  // 1 (leading digit = decimal places)
      "UnitPrice": "21500",  // leading 2 = two decimal places
      "ProductAmount": "1500"  // = Nonfuel Amount
    },
    "Tax by Product Data Segment": {  // C-10: Tax Type 1 = GST, as the test claims
      "SegmentType": "143",
      "SegmentLength": "020",  // counted like Segment 102 - the one-product Segment 143 examples show 023 for 21 characters; confirm with BUYPASS
      "NumberofProducts": "01",  // same products, same order as Segment 102
      "ProductCode": "400",
      "InclusiveExclusiveforTax1": "I",  // I = tax included in the amount
      "TaxType1": "GST",  // the claimed value
      "TaxAmount1": "71"  // example: 5% GST included in the amount - client to supply
    }
  }
}
```

</details>

<a id="tc-040776"></a>

### TC-040776 (BR1_TS16_TC55) - Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds)

**Test name:** Chip Fallback Authorization Only with Tax Type 1 = GST → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 1011](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1011) · **Every field against ATL105:** [Result_MD_Folder/TC-040776.md](Result_MD_Folder/TC-040776.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-10 | The test claims Tax Type 1 = GST but the request does not send it | Test case defect | `Tax Type 1: not in the test data; segments sent: 100, 111` | [TD-TC-040776.json line 2](mastercard_credit_new_16_11/test_data/TD-TC-040776.json#L2) → `Financial Transaction Request` | Send Segment 102 (Product Code) and Segment 143 (Tax by Product) with the same product(s) in the same order, the inclusive/exclusive flag (I or E), Tax Type 1 = GST and the tax amount; recalculate the segment count and lengths. GST is a Canadian tax type and Segment 143 is sent only if the account manager instructs it (Section 11.1), so confirm the merchant context - or re-scope / withdraw the test. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 1011](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1011) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040776.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040776.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |

<details><summary>Test data - actual, step 1: TD-TC-040776.json (click to expand)</summary>

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
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5152464452376240",
      "CardDiscretionaryBlockData": "0931",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "407761",
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
          "VariableInformation": "791"
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

<details open><summary>Test data - expected, step 1: TD-TC-040776.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "04",  // C-10: + Segments 102 and 143
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "078",  // I-01: recalculate: 059 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5152464452376240",
      "CardDiscretionaryBlockData": "0931",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "407761",
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
          "VariableInformation": "791"
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
    "Product Code Data Segment": {  // C-10: Segment 143 needs Segment 102
      "SegmentType": "102",
      "SegmentLength": "029",  // whole segment: type, length, 3 Field Separators and the data with its 2 '\' separators
      "ServiceLevel": "O",  // O = Other/Fuel not present (Element 87)
      "NumberofProducts": "01",
      "ProductCode": "400",  // 400 = General merchandise (Appendix F) - client to confirm
      "UnitofMeasure": "U",  // U = Unit (Element 106)
      "Quantity": "01",  // 1 (leading digit = decimal places)
      "UnitPrice": "21500",  // leading 2 = two decimal places
      "ProductAmount": "1500"  // = Nonfuel Amount
    },
    "Tax by Product Data Segment": {  // C-10: Tax Type 1 = GST, as the test claims
      "SegmentType": "143",
      "SegmentLength": "020",  // counted like Segment 102 - the one-product Segment 143 examples show 023 for 21 characters; confirm with BUYPASS
      "NumberofProducts": "01",  // same products, same order as Segment 102
      "ProductCode": "400",
      "InclusiveExclusiveforTax1": "I",  // I = tax included in the amount
      "TaxType1": "GST",  // the claimed value
      "TaxAmount1": "71"  // example: 5% GST included in the amount - client to supply
    }
  }
}
```

</details>

<a id="tc-040893"></a>

### TC-040893 (BR1_TS17_TC60) - Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds)

**Test name:** Credential on File Authorization Only with Tax Type 1 = GST → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 1128](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1128) · **Every field against ATL105:** [Result_MD_Folder/TC-040893.md](Result_MD_Folder/TC-040893.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-10 | The test claims Tax Type 1 = GST but the request does not send it | Test case defect | `Tax Type 1: not in the test data; segments sent: 100, 111` | [TD-TC-040893.json line 2](mastercard_credit_new_16_11/test_data/TD-TC-040893.json#L2) → `Financial Transaction Request` | Send Segment 102 (Product Code) and Segment 143 (Tax by Product) with the same product(s) in the same order, the inclusive/exclusive flag (I or E), Tax Type 1 = GST and the tax amount; recalculate the segment count and lengths. GST is a Canadian tax type and Segment 143 is sent only if the account manager instructs it (Section 11.1), so confirm the merchant context - or re-scope / withdraw the test. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 1128](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1128) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040893.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040893.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| S-01 | Expiry format for stored credentials is not stated | To confirm with BUYPASS / SME | `0328` | Card Discretionary Block Data (entry mode 10 steps) | Confirm the expiry format for entry mode 10 with BUYPASS. |

<details><summary>Test data - actual, step 1: TD-TC-040893.json (click to expand)</summary>

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
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5127166681060345",
      "CardDiscretionaryBlockData": "0328",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "408931",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "078",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "101"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100100004"
        },
        {
          "VariableInformationIndicator": "032",
          "VariableInformationLength": "006",
          "VariableInformation": "08001C"
        },
        {
          "VariableInformationIndicator": "049",
          "VariableInformationLength": "004",
          "VariableInformation": "021C"
        },
        {
          "VariableInformationIndicator": "056",
          "VariableInformationLength": "008",
          "VariableInformation": "0904C101"
        }
      ]
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-040893.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "04",  // C-10: + Segments 102 and 143
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "078",  // I-01: recalculate: 059 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5127166681060345",
      "CardDiscretionaryBlockData": "0328",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "408931",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "078",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "101"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100100004"
        },
        {
          "VariableInformationIndicator": "032",
          "VariableInformationLength": "006",
          "VariableInformation": "08001C"
        },
        {
          "VariableInformationIndicator": "049",
          "VariableInformationLength": "004",
          "VariableInformation": "021C"
        },
        {
          "VariableInformationIndicator": "056",
          "VariableInformationLength": "008",
          "VariableInformation": "0904C101"
        }
      ]
    },
    "Product Code Data Segment": {  // C-10: Segment 143 needs Segment 102
      "SegmentType": "102",
      "SegmentLength": "029",  // whole segment: type, length, 3 Field Separators and the data with its 2 '\' separators
      "ServiceLevel": "O",  // O = Other/Fuel not present (Element 87)
      "NumberofProducts": "01",
      "ProductCode": "400",  // 400 = General merchandise (Appendix F) - client to confirm
      "UnitofMeasure": "U",  // U = Unit (Element 106)
      "Quantity": "01",  // 1 (leading digit = decimal places)
      "UnitPrice": "21500",  // leading 2 = two decimal places
      "ProductAmount": "1500"  // = Nonfuel Amount
    },
    "Tax by Product Data Segment": {  // C-10: Tax Type 1 = GST, as the test claims
      "SegmentType": "143",
      "SegmentLength": "020",  // counted like Segment 102 - the one-product Segment 143 examples show 023 for 21 characters; confirm with BUYPASS
      "NumberofProducts": "01",  // same products, same order as Segment 102
      "ProductCode": "400",
      "InclusiveExclusiveforTax1": "I",  // I = tax included in the amount
      "TaxType1": "GST",  // the claimed value
      "TaxAmount1": "71"  // example: 5% GST included in the amount - client to supply
    }
  }
}
```

</details>

<a id="tc-041009"></a>

### TC-041009 (BR1_TS18_TC51) - Decision table: Authorization Only with Tax Type 1 = GST (the rule's condition holds)

**Test name:** Contactless Mobile Commerce Device Authorization Only with Tax Type 1 = GST → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `pending_sme` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 1244](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1244) · **Every field against ATL105:** [Result_MD_Folder/TC-041009.md](Result_MD_Folder/TC-041009.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-10 | The test claims Tax Type 1 = GST but the request does not send it | Test case defect | `Tax Type 1: not in the test data; segments sent: 100, 111` | [TD-TC-041009.json line 2](mastercard_credit_new_16_11/test_data/TD-TC-041009.json#L2) → `Financial Transaction Request` | Send Segment 102 (Product Code) and Segment 143 (Tax by Product) with the same product(s) in the same order, the inclusive/exclusive flag (I or E), Tax Type 1 = GST and the tax amount; recalculate the segment count and lengths. GST is a Canadian tax type and Segment 143 is sent only if the account manager instructs it (Section 11.1), so confirm the merchant context - or re-scope / withdraw the test. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-041009.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-041009.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |

<details><summary>Test data - actual, step 1: TD-TC-041009.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5105438133807318",
      "CardDiscretionaryBlockData": "300620121327418",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "410091",
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
          "VariableInformation": "821"
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

<details open><summary>Test data - expected, step 1: TD-TC-041009.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "04",  // C-10: + Segments 102 and 143
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",  // I-01: recalculate: 070 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5105438133807318",
      "CardDiscretionaryBlockData": "300620121327418",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "410091",
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
          "VariableInformation": "821"
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
    "Product Code Data Segment": {  // C-10: Segment 143 needs Segment 102
      "SegmentType": "102",
      "SegmentLength": "029",  // whole segment: type, length, 3 Field Separators and the data with its 2 '\' separators
      "ServiceLevel": "O",  // O = Other/Fuel not present (Element 87)
      "NumberofProducts": "01",
      "ProductCode": "400",  // 400 = General merchandise (Appendix F) - client to confirm
      "UnitofMeasure": "U",  // U = Unit (Element 106)
      "Quantity": "01",  // 1 (leading digit = decimal places)
      "UnitPrice": "21500",  // leading 2 = two decimal places
      "ProductAmount": "1500"  // = Nonfuel Amount
    },
    "Tax by Product Data Segment": {  // C-10: Tax Type 1 = GST, as the test claims
      "SegmentType": "143",
      "SegmentLength": "020",  // counted like Segment 102 - the one-product Segment 143 examples show 023 for 21 characters; confirm with BUYPASS
      "NumberofProducts": "01",  // same products, same order as Segment 102
      "ProductCode": "400",
      "InclusiveExclusiveforTax1": "I",  // I = tax included in the amount
      "TaxType1": "GST",  // the claimed value
      "TaxAmount1": "71"  // example: 5% GST included in the amount - client to supply
    }
  }
}
```

</details>

<a id="tc-040126"></a>

### TC-040126 (BR1_TS11_TC67) - Decision table: Authorization Only with optional segment 100 present

**Test name:** EMV Contact Chip Authorization Only with optional Standard Message Data Segment → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 361](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L361) · **Every field against ATL105:** [Result_MD_Folder/TC-040126.md](Result_MD_Folder/TC-040126.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-02 | Approval Number sent on an original Authorization Only | Test data defect | `J38FQA` | [TD-TC-040126.json line 23](mastercard_credit_new_16_11/test_data/TD-TC-040126.json#L23) → `Standard Message Data Segment → ApprovalNumber` | (empty) - an original Authorization Only carries no Approval Number |
| D-02 | Encrypted PIN Block Data is not a DUKPT KSN + PIN block | Test data defect | `16` | [TD-TC-040126.json line 16](mastercard_credit_new_16_11/test_data/TD-TC-040126.json#L16) → `Standard Message Data Segment → EncryptedPINBlockData` | A real DUKPT KSN + PIN block, or leave it empty for credit. |
| D-02 | Conditional fields sent although their condition does not apply | Test data defect | Local Date and Local Time '0615251430' on a new Authorization Only; Pump/Lane Number '1' at a GENERAL (non-petroleum) merchant; Fuel Purchase Amount '1' at a GENERAL (non-petroleum) merchant | [TD-TC-040126.json line 24](mastercard_credit_new_16_11/test_data/TD-TC-040126.json#L24), [TD-TC-040126.json line 17](mastercard_credit_new_16_11/test_data/TD-TC-040126.json#L17), [TD-TC-040126.json line 18](mastercard_credit_new_16_11/test_data/TD-TC-040126.json#L18) → `Standard Message Data Segment → LocalDateandLocalTime, PumpLaneNumber, FuelPurchaseAmount` | Send conditional fields only when their condition holds. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 361](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L361) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[4]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 361](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L361) → `spec_basis[4]` | Cite p.160 instead of p.161. |
| I-04 | The CA Public Key File Checksum is a sample value | Awaiting client input / run-time value | `2012091700000100000102171` | [TD-TC-040126.json line 51](mastercard_credit_new_16_11/test_data/TD-TC-040126.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | Confirm the checksum of the host's current CA Public Key file before execution. |
| I-04 | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders | Awaiting client input / run-time value | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=A02F1566, 95=8000048000` | [TD-TC-040126.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040126.json#L54) → `EMV Request Data Segment → EMVChipData` | Produce the chip data with a card simulator / HSM before execution. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040126.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040126.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| V-01 | The chip data does not include the cash back | AI validator rule not applicable - value checked and correct | `9F02=000000001503, 9F03=000000000001, 9C=09` | [TD-TC-040126.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040126.json#L54) → `EMV Request Data Segment → EMVChipData → Tags 9F02, 9F03, 9C` | The chip data (9F02 = total including the cash back, 9F03 = the cash back, 9C = 09) describes the cash-back amounts consistently; the AI rule expected 9C = 00 because the step is an Authorization Only. The real problem of this test - optional fields filled without their condition - is D-02. |

<details><summary>Test data - actual, step 1: TD-TC-040126.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "111",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5172713356049162",
      "CardDiscretionaryBlockData": "290520100887266",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1500",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "401261",
      "ApprovalNumber": "J38FQA",
      "LocalDateandLocalTime": "0615251430",
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000480009A032601019C01099F02060000000015039F03060000000000019F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608B4302C8AE8B7E91C9F2701809F3303E0F0C89F34035E03029F3501229F360201D09F3704A02F15669F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-040126.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "111",  // D-02/I-01: recalculate: 072 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5172713356049162",
      "CardDiscretionaryBlockData": "290520100887266",
      "EncryptedPINBlockData": "",  // D-02: empty - '16' is not a DUKPT KSN + PIN block; no online PIN on this credit request
      "PumpLaneNumber": "",  // D-02: empty - fuel field at a non-petroleum merchant
      "FuelPurchaseAmount": "",  // D-02: empty - fuel field at a non-petroleum merchant
      "NonfuelAmount": "1500",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "401261",
      "ApprovalNumber": "",  // D-02: empty - an original Authorization Only carries no Approval Number
      "LocalDateandLocalTime": "",  // D-02: empty - Element 49 is for a preauthorized transaction; this request is not one
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000480009A032601019C01099F02060000000015039F03060000000000019F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608B4302C8AE8B7E91C9F2701809F3303E0F0C89F34035E03029F3501229F360201D09F3704A02F15669F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-040240"></a>

### TC-040240 (BR1_TS12_TC63) - Decision table: Authorization Only with optional segment 100 present

**Test name:** EMV Contactless Chip Authorization Only with optional Standard Message Data Segment → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 475](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L475) · **Every field against ATL105:** [Result_MD_Folder/TC-040240.md](Result_MD_Folder/TC-040240.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-02 | Approval Number sent on an original Authorization Only | Test data defect | `6FH3NZ` | [TD-TC-040240.json line 23](mastercard_credit_new_16_11/test_data/TD-TC-040240.json#L23) → `Standard Message Data Segment → ApprovalNumber` | (empty) - an original Authorization Only carries no Approval Number |
| D-02 | Encrypted PIN Block Data is not a DUKPT KSN + PIN block | Test data defect | `16` | [TD-TC-040240.json line 16](mastercard_credit_new_16_11/test_data/TD-TC-040240.json#L16) → `Standard Message Data Segment → EncryptedPINBlockData` | A real DUKPT KSN + PIN block, or leave it empty for credit. |
| D-02 | Conditional fields sent although their condition does not apply | Test data defect | Local Date and Local Time '0615251430' on a new Authorization Only; Pump/Lane Number '1' at a GENERAL (non-petroleum) merchant; Fuel Purchase Amount '1' at a GENERAL (non-petroleum) merchant | [TD-TC-040240.json line 24](mastercard_credit_new_16_11/test_data/TD-TC-040240.json#L24), [TD-TC-040240.json line 17](mastercard_credit_new_16_11/test_data/TD-TC-040240.json#L17), [TD-TC-040240.json line 18](mastercard_credit_new_16_11/test_data/TD-TC-040240.json#L18) → `Standard Message Data Segment → LocalDateandLocalTime, PumpLaneNumber, FuelPurchaseAmount` | Send conditional fields only when their condition holds. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 475](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L475) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[4]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 475](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L475) → `spec_basis[4]` | Cite p.160 instead of p.161. |
| I-04 | The CA Public Key File Checksum is a sample value | Awaiting client input / run-time value | `2012091700000100000102171` | [TD-TC-040240.json line 51](mastercard_credit_new_16_11/test_data/TD-TC-040240.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | Confirm the checksum of the host's current CA Public Key file before execution. |
| I-04 | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders | Awaiting client input / run-time value | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=48200C74, 95=8000048000` | [TD-TC-040240.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040240.json#L54) → `EMV Request Data Segment → EMVChipData` | Produce the chip data with a card simulator / HSM before execution. |
| I-04 | Contactless chip data reuses the contact tag set | Awaiting client input / run-time value | `22 tags, CVM 9F34 = 5E0302` | [TD-TC-040240.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040240.json#L54) → `EMV Request Data Segment → EMVChipData` | Confirm the contactless chip data with a MasterCard contactless (M/Chip) kernel trace. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040240.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040240.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| V-01 | The chip data does not include the cash back | AI validator rule not applicable - value checked and correct | `9F02=000000001503, 9F03=000000000001, 9C=09` | [TD-TC-040240.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040240.json#L54) → `EMV Request Data Segment → EMVChipData → Tags 9F02, 9F03, 9C` | The chip data (9F02 = total including the cash back, 9F03 = the cash back, 9C = 09) describes the cash-back amounts consistently; the AI rule expected 9C = 00 because the step is an Authorization Only. The real problem of this test - optional fields filled without their condition - is D-02. |

<details><summary>Test data - actual, step 1: TD-TC-040240.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "111",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5195236981090503",
      "CardDiscretionaryBlockData": "310420103579019",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1500",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "402401",
      "ApprovalNumber": "6FH3NZ",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "071"
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000480009A032601019C01099F02060000000015039F03060000000000019F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608B4302C8AE8B7E91C9F2701809F3303E0F0C89F34035E03029F3501229F360201D09F370448200C749F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-040240.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "111",  // D-02/I-01: recalculate: 072 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5195236981090503",
      "CardDiscretionaryBlockData": "310420103579019",
      "EncryptedPINBlockData": "",  // D-02: empty - '16' is not a DUKPT KSN + PIN block; no online PIN on this credit request
      "PumpLaneNumber": "",  // D-02: empty - fuel field at a non-petroleum merchant
      "FuelPurchaseAmount": "",  // D-02: empty - fuel field at a non-petroleum merchant
      "NonfuelAmount": "1500",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "402401",
      "ApprovalNumber": "",  // D-02: empty - an original Authorization Only carries no Approval Number
      "LocalDateandLocalTime": "",  // D-02: empty - Element 49 is for a preauthorized transaction; this request is not one
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "071"
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000480009A032601019C01099F02060000000015039F03060000000000019F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608B4302C8AE8B7E91C9F2701809F3303E0F0C89F34035E03029F3501229F360201D09F370448200C749F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-040356"></a>

### TC-040356 (BR1_TS13_TC65) - Decision table: Authorization Only with optional segment 100 present

**Test name:** Magnetic Stripe Authorization Only with optional Standard Message Data Segment → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 591](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L591) · **Every field against ATL105:** [Result_MD_Folder/TC-040356.md](Result_MD_Folder/TC-040356.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-02 | Approval Number sent on an original Authorization Only | Test data defect | `C9HTU3` | [TD-TC-040356.json line 23](mastercard_credit_new_16_11/test_data/TD-TC-040356.json#L23) → `Standard Message Data Segment → ApprovalNumber` | (empty) - an original Authorization Only carries no Approval Number |
| D-02 | Encrypted PIN Block Data is not a DUKPT KSN + PIN block | Test data defect | `16` | [TD-TC-040356.json line 16](mastercard_credit_new_16_11/test_data/TD-TC-040356.json#L16) → `Standard Message Data Segment → EncryptedPINBlockData` | A real DUKPT KSN + PIN block, or leave it empty for credit. |
| D-02 | Conditional fields sent although their condition does not apply | Test data defect | Local Date and Local Time '0615251430' on a new Authorization Only; Pump/Lane Number '1' at a GENERAL (non-petroleum) merchant; Fuel Purchase Amount '1' at a GENERAL (non-petroleum) merchant | [TD-TC-040356.json line 24](mastercard_credit_new_16_11/test_data/TD-TC-040356.json#L24), [TD-TC-040356.json line 17](mastercard_credit_new_16_11/test_data/TD-TC-040356.json#L17), [TD-TC-040356.json line 18](mastercard_credit_new_16_11/test_data/TD-TC-040356.json#L18) → `Standard Message Data Segment → LocalDateandLocalTime, PumpLaneNumber, FuelPurchaseAmount` | Send conditional fields only when their condition holds. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 591](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L591) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040356.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040356.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |

<details><summary>Test data - actual, step 1: TD-TC-040356.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "111",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5116719703251996",
      "CardDiscretionaryBlockData": "301010123398180",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1500",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "403561",
      "ApprovalNumber": "C9HTU3",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "901"
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

<details open><summary>Test data - expected, step 1: TD-TC-040356.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "111",  // D-02/I-01: recalculate: 072 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5116719703251996",
      "CardDiscretionaryBlockData": "301010123398180",
      "EncryptedPINBlockData": "",  // D-02: empty - '16' is not a DUKPT KSN + PIN block; no online PIN on this credit request
      "PumpLaneNumber": "",  // D-02: empty - fuel field at a non-petroleum merchant
      "FuelPurchaseAmount": "",  // D-02: empty - fuel field at a non-petroleum merchant
      "NonfuelAmount": "1500",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "403561",
      "ApprovalNumber": "",  // D-02: empty - an original Authorization Only carries no Approval Number
      "LocalDateandLocalTime": "",  // D-02: empty - Element 49 is for a preauthorized transaction; this request is not one
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "901"
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

<a id="tc-040557"></a>

### TC-040557 (BR1_TS14_TC61) - Decision table: Authorization Only with optional segment 100 present

**Test name:** Contactless Magnetic Stripe Authorization Only with optional Standard Message Data Segment → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 792](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L792) · **Every field against ATL105:** [Result_MD_Folder/TC-040557.md](Result_MD_Folder/TC-040557.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-02 | Approval Number sent on an original Authorization Only | Test data defect | `EMUBI3` | [TD-TC-040557.json line 23](mastercard_credit_new_16_11/test_data/TD-TC-040557.json#L23) → `Standard Message Data Segment → ApprovalNumber` | (empty) - an original Authorization Only carries no Approval Number |
| D-02 | Encrypted PIN Block Data is not a DUKPT KSN + PIN block | Test data defect | `16` | [TD-TC-040557.json line 16](mastercard_credit_new_16_11/test_data/TD-TC-040557.json#L16) → `Standard Message Data Segment → EncryptedPINBlockData` | A real DUKPT KSN + PIN block, or leave it empty for credit. |
| D-02 | Conditional fields sent although their condition does not apply | Test data defect | Local Date and Local Time '0615251430' on a new Authorization Only; Pump/Lane Number '1' at a GENERAL (non-petroleum) merchant; Fuel Purchase Amount '1' at a GENERAL (non-petroleum) merchant | [TD-TC-040557.json line 24](mastercard_credit_new_16_11/test_data/TD-TC-040557.json#L24), [TD-TC-040557.json line 17](mastercard_credit_new_16_11/test_data/TD-TC-040557.json#L17), [TD-TC-040557.json line 18](mastercard_credit_new_16_11/test_data/TD-TC-040557.json#L18) → `Standard Message Data Segment → LocalDateandLocalTime, PumpLaneNumber, FuelPurchaseAmount` | Send conditional fields only when their condition holds. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 792](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L792) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040557.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040557.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |

<details><summary>Test data - actual, step 1: TD-TC-040557.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "111",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5151935028658964",
      "CardDiscretionaryBlockData": "311120106551213",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1500",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "405571",
      "ApprovalNumber": "EMUBI3",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "911"
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

<details open><summary>Test data - expected, step 1: TD-TC-040557.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "111",  // D-02/I-01: recalculate: 072 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5151935028658964",
      "CardDiscretionaryBlockData": "311120106551213",
      "EncryptedPINBlockData": "",  // D-02: empty - '16' is not a DUKPT KSN + PIN block; no online PIN on this credit request
      "PumpLaneNumber": "",  // D-02: empty - fuel field at a non-petroleum merchant
      "FuelPurchaseAmount": "",  // D-02: empty - fuel field at a non-petroleum merchant
      "NonfuelAmount": "1500",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "405571",
      "ApprovalNumber": "",  // D-02: empty - an original Authorization Only carries no Approval Number
      "LocalDateandLocalTime": "",  // D-02: empty - Element 49 is for a preauthorized transaction; this request is not one
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "911"
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

<a id="tc-040668"></a>

### TC-040668 (BR1_TS15_TC62) - Decision table: Authorization Only with optional segment 100 present

**Test name:** Manual / Keyed Authorization Only with optional Standard Message Data Segment → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 903](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L903) · **Every field against ATL105:** [Result_MD_Folder/TC-040668.md](Result_MD_Folder/TC-040668.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-02 | Approval Number sent on an original Authorization Only | Test data defect | `DKJ066` | [TD-TC-040668.json line 23](mastercard_credit_new_16_11/test_data/TD-TC-040668.json#L23) → `Standard Message Data Segment → ApprovalNumber` | (empty) - an original Authorization Only carries no Approval Number |
| D-02 | Encrypted PIN Block Data is not a DUKPT KSN + PIN block | Test data defect | `16` | [TD-TC-040668.json line 16](mastercard_credit_new_16_11/test_data/TD-TC-040668.json#L16) → `Standard Message Data Segment → EncryptedPINBlockData` | A real DUKPT KSN + PIN block, or leave it empty for credit. |
| D-02 | Conditional fields sent although their condition does not apply | Test data defect | Local Date and Local Time '0615251430' on a new Authorization Only; Pump/Lane Number '1' at a GENERAL (non-petroleum) merchant; Fuel Purchase Amount '1' at a GENERAL (non-petroleum) merchant | [TD-TC-040668.json line 24](mastercard_credit_new_16_11/test_data/TD-TC-040668.json#L24), [TD-TC-040668.json line 17](mastercard_credit_new_16_11/test_data/TD-TC-040668.json#L17), [TD-TC-040668.json line 18](mastercard_credit_new_16_11/test_data/TD-TC-040668.json#L18) → `Standard Message Data Segment → LocalDateandLocalTime, PumpLaneNumber, FuelPurchaseAmount` | Send conditional fields only when their condition holds. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 903](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L903) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040668.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040668.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |

<details><summary>Test data - actual, step 1: TD-TC-040668.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "100",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5120548867262653",
      "CardDiscretionaryBlockData": "1127",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1500",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "406681",
      "ApprovalNumber": "DKJ066",
      "LocalDateandLocalTime": "0615251430",
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

<details open><summary>Test data - expected, step 1: TD-TC-040668.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "100",  // D-02/I-01: recalculate: 061 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5120548867262653",
      "CardDiscretionaryBlockData": "1127",
      "EncryptedPINBlockData": "",  // D-02: empty - '16' is not a DUKPT KSN + PIN block; no online PIN on this credit request
      "PumpLaneNumber": "",  // D-02: empty - fuel field at a non-petroleum merchant
      "FuelPurchaseAmount": "",  // D-02: empty - fuel field at a non-petroleum merchant
      "NonfuelAmount": "1500",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "406681",
      "ApprovalNumber": "",  // D-02: empty - an original Authorization Only carries no Approval Number
      "LocalDateandLocalTime": "",  // D-02: empty - Element 49 is for a preauthorized transaction; this request is not one
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

<a id="tc-040786"></a>

### TC-040786 (BR1_TS16_TC65) - Decision table: Authorization Only with optional segment 100 present

**Test name:** Chip Fallback Authorization Only with optional Standard Message Data Segment → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 1021](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1021) · **Every field against ATL105:** [Result_MD_Folder/TC-040786.md](Result_MD_Folder/TC-040786.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-02 | Approval Number sent on an original Authorization Only | Test data defect | `LR2COZ` | [TD-TC-040786.json line 23](mastercard_credit_new_16_11/test_data/TD-TC-040786.json#L23) → `Standard Message Data Segment → ApprovalNumber` | (empty) - an original Authorization Only carries no Approval Number |
| D-02 | Encrypted PIN Block Data is not a DUKPT KSN + PIN block | Test data defect | `16` | [TD-TC-040786.json line 16](mastercard_credit_new_16_11/test_data/TD-TC-040786.json#L16) → `Standard Message Data Segment → EncryptedPINBlockData` | A real DUKPT KSN + PIN block, or leave it empty for credit. |
| D-02 | Conditional fields sent although their condition does not apply | Test data defect | Local Date and Local Time '0615251430' on a new Authorization Only; Pump/Lane Number '1' at a GENERAL (non-petroleum) merchant; Fuel Purchase Amount '1' at a GENERAL (non-petroleum) merchant | [TD-TC-040786.json line 24](mastercard_credit_new_16_11/test_data/TD-TC-040786.json#L24), [TD-TC-040786.json line 17](mastercard_credit_new_16_11/test_data/TD-TC-040786.json#L17), [TD-TC-040786.json line 18](mastercard_credit_new_16_11/test_data/TD-TC-040786.json#L18) → `Standard Message Data Segment → LocalDateandLocalTime, PumpLaneNumber, FuelPurchaseAmount` | Send conditional fields only when their condition holds. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 1021](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1021) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040786.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040786.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |

<details><summary>Test data - actual, step 1: TD-TC-040786.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "100",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5147657420439697",
      "CardDiscretionaryBlockData": "1132",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1500",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "407861",
      "ApprovalNumber": "LR2COZ",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "791"
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

<details open><summary>Test data - expected, step 1: TD-TC-040786.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "100",  // D-02/I-01: recalculate: 061 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5147657420439697",
      "CardDiscretionaryBlockData": "1132",
      "EncryptedPINBlockData": "",  // D-02: empty - '16' is not a DUKPT KSN + PIN block; no online PIN on this credit request
      "PumpLaneNumber": "",  // D-02: empty - fuel field at a non-petroleum merchant
      "FuelPurchaseAmount": "",  // D-02: empty - fuel field at a non-petroleum merchant
      "NonfuelAmount": "1500",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "407861",
      "ApprovalNumber": "",  // D-02: empty - an original Authorization Only carries no Approval Number
      "LocalDateandLocalTime": "",  // D-02: empty - Element 49 is for a preauthorized transaction; this request is not one
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "791"
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

<a id="tc-040903"></a>

### TC-040903 (BR1_TS17_TC70) - Decision table: Authorization Only with optional segment 100 present

**Test name:** Credential on File Authorization Only with optional Standard Message Data Segment → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 1138](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1138) · **Every field against ATL105:** [Result_MD_Folder/TC-040903.md](Result_MD_Folder/TC-040903.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-02 | Approval Number sent on an original Authorization Only | Test data defect | `6ANJP4` | [TD-TC-040903.json line 23](mastercard_credit_new_16_11/test_data/TD-TC-040903.json#L23) → `Standard Message Data Segment → ApprovalNumber` | (empty) - an original Authorization Only carries no Approval Number |
| D-02 | Encrypted PIN Block Data is not a DUKPT KSN + PIN block | Test data defect | `16` | [TD-TC-040903.json line 16](mastercard_credit_new_16_11/test_data/TD-TC-040903.json#L16) → `Standard Message Data Segment → EncryptedPINBlockData` | A real DUKPT KSN + PIN block, or leave it empty for credit. |
| D-02 | Conditional fields sent although their condition does not apply | Test data defect | Local Date and Local Time '0615251430' on a new Authorization Only; Pump/Lane Number '1' at a CARD_NOT_PRESENT-0100 (non-petroleum) merchant; Fuel Purchase Amount '1' at a CARD_NOT_PRESENT-0100 (non-petroleum) merchant | [TD-TC-040903.json line 24](mastercard_credit_new_16_11/test_data/TD-TC-040903.json#L24), [TD-TC-040903.json line 17](mastercard_credit_new_16_11/test_data/TD-TC-040903.json#L17), [TD-TC-040903.json line 18](mastercard_credit_new_16_11/test_data/TD-TC-040903.json#L18) → `Standard Message Data Segment → LocalDateandLocalTime, PumpLaneNumber, FuelPurchaseAmount` | Send conditional fields only when their condition holds. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 1138](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1138) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040903.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040903.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| S-01 | Expiry format for stored credentials is not stated | To confirm with BUYPASS / SME | `1230` | Card Discretionary Block Data (entry mode 10 steps) | Confirm the expiry format for entry mode 10 with BUYPASS. |

<details><summary>Test data - actual, step 1: TD-TC-040903.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "100",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5101362022561121",
      "CardDiscretionaryBlockData": "1230",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1500",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "409031",
      "ApprovalNumber": "6ANJP4",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "078",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "101"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100100004"
        },
        {
          "VariableInformationIndicator": "032",
          "VariableInformationLength": "006",
          "VariableInformation": "08001C"
        },
        {
          "VariableInformationIndicator": "049",
          "VariableInformationLength": "004",
          "VariableInformation": "021C"
        },
        {
          "VariableInformationIndicator": "056",
          "VariableInformationLength": "008",
          "VariableInformation": "0904C101"
        }
      ]
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-040903.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "100",  // D-02/I-01: recalculate: 061 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5101362022561121",
      "CardDiscretionaryBlockData": "1230",
      "EncryptedPINBlockData": "",  // D-02: empty - '16' is not a DUKPT KSN + PIN block; no online PIN on this credit request
      "PumpLaneNumber": "",  // D-02: empty - fuel field at a non-petroleum merchant
      "FuelPurchaseAmount": "",  // D-02: empty - fuel field at a non-petroleum merchant
      "NonfuelAmount": "1500",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "409031",
      "ApprovalNumber": "",  // D-02: empty - an original Authorization Only carries no Approval Number
      "LocalDateandLocalTime": "",  // D-02: empty - Element 49 is for a preauthorized transaction; this request is not one
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "078",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "101"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100100004"
        },
        {
          "VariableInformationIndicator": "032",
          "VariableInformationLength": "006",
          "VariableInformation": "08001C"
        },
        {
          "VariableInformationIndicator": "049",
          "VariableInformationLength": "004",
          "VariableInformation": "021C"
        },
        {
          "VariableInformationIndicator": "056",
          "VariableInformationLength": "008",
          "VariableInformation": "0904C101"
        }
      ]
    }
  }
}
```

</details>

<a id="tc-041019"></a>

### TC-041019 (BR1_TS18_TC61) - Decision table: Authorization Only with optional segment 100 present

**Test name:** Contactless Mobile Commerce Device Authorization Only with optional Standard Message Data Segment → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `pending_sme` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 1254](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1254) · **Every field against ATL105:** [Result_MD_Folder/TC-041019.md](Result_MD_Folder/TC-041019.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-02 | Approval Number sent on an original Authorization Only | Test data defect | `MDCNDN` | [TD-TC-041019.json line 23](mastercard_credit_new_16_11/test_data/TD-TC-041019.json#L23) → `Standard Message Data Segment → ApprovalNumber` | (empty) - an original Authorization Only carries no Approval Number |
| D-02 | Encrypted PIN Block Data is not a DUKPT KSN + PIN block | Test data defect | `16` | [TD-TC-041019.json line 16](mastercard_credit_new_16_11/test_data/TD-TC-041019.json#L16) → `Standard Message Data Segment → EncryptedPINBlockData` | A real DUKPT KSN + PIN block, or leave it empty for credit. |
| D-02 | Conditional fields sent although their condition does not apply | Test data defect | Local Date and Local Time '0615251430' on a new Authorization Only; Pump/Lane Number '1' at a GENERAL (non-petroleum) merchant; Fuel Purchase Amount '1' at a GENERAL (non-petroleum) merchant | [TD-TC-041019.json line 24](mastercard_credit_new_16_11/test_data/TD-TC-041019.json#L24), [TD-TC-041019.json line 17](mastercard_credit_new_16_11/test_data/TD-TC-041019.json#L17), [TD-TC-041019.json line 18](mastercard_credit_new_16_11/test_data/TD-TC-041019.json#L18) → `Standard Message Data Segment → LocalDateandLocalTime, PumpLaneNumber, FuelPurchaseAmount` | Send conditional fields only when their condition holds. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-041019.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-041019.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |

<details><summary>Test data - actual, step 1: TD-TC-041019.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "111",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5131130817423649",
      "CardDiscretionaryBlockData": "280320159659401",
      "EncryptedPINBlockData": "16",
      "PumpLaneNumber": "1",
      "FuelPurchaseAmount": "1",
      "NonfuelAmount": "1500",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "410191",
      "ApprovalNumber": "MDCNDN",
      "LocalDateandLocalTime": "0615251430",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "821"
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

<details open><summary>Test data - expected, step 1: TD-TC-041019.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "111",  // D-02/I-01: recalculate: 072 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5131130817423649",
      "CardDiscretionaryBlockData": "280320159659401",
      "EncryptedPINBlockData": "",  // D-02: empty - '16' is not a DUKPT KSN + PIN block; no online PIN on this credit request
      "PumpLaneNumber": "",  // D-02: empty - fuel field at a non-petroleum merchant
      "FuelPurchaseAmount": "",  // D-02: empty - fuel field at a non-petroleum merchant
      "NonfuelAmount": "1500",
      "TaxAmount": "1",
      "CashAmount": "1",
      "SequenceNumber": "410191",
      "ApprovalNumber": "",  // D-02: empty - an original Authorization Only carries no Approval Number
      "LocalDateandLocalTime": "",  // D-02: empty - Element 49 is for a preauthorized transaction; this request is not one
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "821"
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

<a id="tc-040140"></a>

### TC-040140 (BR1_TS11_TC81) - Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned

**Test name:** EMV Contact Chip Authorization Only (AVS or NVS or Both Information returned) → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 375](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L375) · **Every field against ATL105:** [Result_MD_Folder/TC-040140.md](Result_MD_Folder/TC-040140.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-11 | The test expects AVS or NVS or Both Information back, but the request does not send AVS data (Table 003) | Test case defect | `Segment 111 tables sent: 005, 030, 031; expected back: Segment 112 Table 003 AVS or NVS or Both Information` | [TD-TC-040140.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040140.json#L27) → `Variable Information Data Segment` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111 length and assert the AVS result in Segment 112 Table 003 - or drop the AVS expectation. AVS is used primarily for non-face-to-face transactions (mail/phone order, CAT), so confirm the card-present variants with BUYPASS. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 375](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L375) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[6]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 375](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L375) → `spec_basis[6]` | Cite p.160 instead of p.161. |
| I-04 | The CA Public Key File Checksum is a sample value | Awaiting client input / run-time value | `2012091700000100000102171` | [TD-TC-040140.json line 51](mastercard_credit_new_16_11/test_data/TD-TC-040140.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | Confirm the checksum of the host's current CA Public Key file before execution. |
| I-04 | TVR says 'online PIN entered' on a transaction without online PIN | Awaiting client input / run-time value | `95=8000048000, 9F34=5E0302` | [TD-TC-040140.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040140.json#L54) → `EMV Request Data Segment → EMVChipData → Tag 95` | Produce the TVR and the CVM Results together with a card simulator. |
| I-04 | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders | Awaiting client input / run-time value | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=D9509DAD, 95=8000048000` | [TD-TC-040140.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040140.json#L54) → `EMV Request Data Segment → EMVChipData` | Produce the chip data with a card simulator / HSM before execution. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040140.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040140.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| INFO | Same request as TC-040065 (BR1_TS11_TC6) | Information | `expected 2 vs 2` | TD-TC-040140.json vs TD-TC-040065.json | Check the difference on the device / in the response; optionally use another card. No change in the test data. |

<details><summary>Test data - actual, step 1: TD-TC-040140.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5177951757699269",
      "CardDiscretionaryBlockData": "290520101440570",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "401401",
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000480009A032601019C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608B4302C8AE8B7E91C9F2701809F3303E0F0C89F34035E03029F3501229F360201D09F3704D9509DAD9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-040140.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",  // I-01: recalculate: 070 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5177951757699269",
      "CardDiscretionaryBlockData": "290520101440570",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "401401",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",  // C-11: recalculate: 042 + 6 + length of the Table 003 data
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
        },
        {  // C-11: Table 003 AVS Information - the request initiates the AVS result the test expects back
          "VariableInformationIndicator": "003",
          "VariableInformationLength": "<length of the AVS data, up to 029>",
          "VariableInformation": "<ZIP Code + 4 spaces, or ZIP+4><street address, up to 20 characters>"  // the cardholder's billing address - client test card data (Appendix I)
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000480009A032601019C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608B4302C8AE8B7E91C9F2701809F3303E0F0C89F34035E03029F3501229F360201D09F3704D9509DAD9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-040254"></a>

### TC-040254 (BR1_TS12_TC77) - Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned

**Test name:** EMV Contactless Chip Authorization Only (AVS or NVS or Both Information returned) → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 489](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L489) · **Every field against ATL105:** [Result_MD_Folder/TC-040254.md](Result_MD_Folder/TC-040254.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-11 | The test expects AVS or NVS or Both Information back, but the request does not send AVS data (Table 003) | Test case defect | `Segment 111 tables sent: 005, 030, 031; expected back: Segment 112 Table 003 AVS or NVS or Both Information` | [TD-TC-040254.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040254.json#L27) → `Variable Information Data Segment` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111 length and assert the AVS result in Segment 112 Table 003 - or drop the AVS expectation. AVS is used primarily for non-face-to-face transactions (mail/phone order, CAT), so confirm the card-present variants with BUYPASS. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 489](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L489) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[6]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 489](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L489) → `spec_basis[6]` | Cite p.160 instead of p.161. |
| I-04 | The CA Public Key File Checksum is a sample value | Awaiting client input / run-time value | `2012091700000100000102171` | [TD-TC-040254.json line 51](mastercard_credit_new_16_11/test_data/TD-TC-040254.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum` | Confirm the checksum of the host's current CA Public Key file before execution. |
| I-04 | TVR says 'online PIN entered' on a transaction without online PIN | Awaiting client input / run-time value | `95=8000048000, 9F34=5E0302` | [TD-TC-040254.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040254.json#L54) → `EMV Request Data Segment → EMVChipData → Tag 95` | Produce the TVR and the CVM Results together with a card simulator. |
| I-04 | Cryptogram, ATC, unpredictable number, TVR and IAD are generated placeholders | Awaiting client input / run-time value | `9F26=B4302C8AE8B7E91C, 9F36=01D0, 9F37=2FB75EB4, 95=8000048000` | [TD-TC-040254.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040254.json#L54) → `EMV Request Data Segment → EMVChipData` | Produce the chip data with a card simulator / HSM before execution. |
| I-04 | Contactless chip data reuses the contact tag set | Awaiting client input / run-time value | `22 tags, CVM 9F34 = 5E0302` | [TD-TC-040254.json line 54](mastercard_credit_new_16_11/test_data/TD-TC-040254.json#L54) → `EMV Request Data Segment → EMVChipData` | Confirm the contactless chip data with a MasterCard contactless (M/Chip) kernel trace. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040254.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040254.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| INFO | Same request as TC-040179 (BR1_TS12_TC2) | Information | `expected 2 vs 2` | TD-TC-040254.json vs TD-TC-040179.json | Check the difference on the device / in the response; optionally use another card. No change in the test data. |

<details><summary>Test data - actual, step 1: TD-TC-040254.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5123037123672506",
      "CardDiscretionaryBlockData": "300520105478208",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "402541",
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
          "VariableInformation": "071"
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
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000480009A032601019C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608B4302C8AE8B7E91C9F2701809F3303E0F0C89F34035E03029F3501229F360201D09F37042FB75EB49F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-040254.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",  // I-01: recalculate: 070 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5123037123672506",
      "CardDiscretionaryBlockData": "300520105478208",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "402541",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",  // C-11: recalculate: 042 + 6 + length of the Table 003 data
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "071"
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
        },
        {  // C-11: Table 003 AVS Information - the request initiates the AVS result the test expects back
          "VariableInformationIndicator": "003",
          "VariableInformationLength": "<length of the AVS data, up to 029>",
          "VariableInformation": "<ZIP Code + 4 spaces, or ZIP+4><street address, up to 20 characters>"  // the cardholder's billing address - client test card data (Appendix I)
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000480009A032601019C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608B4302C8AE8B7E91C9F2701809F3303E0F0C89F34035E03029F3501229F360201D09F37042FB75EB49F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-040396"></a>

### TC-040396 (BR1_TS13_TC105) - Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned

**Test name:** Magnetic Stripe Authorization Only (AVS or NVS or Both Information returned) → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 631](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L631) · **Every field against ATL105:** [Result_MD_Folder/TC-040396.md](Result_MD_Folder/TC-040396.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-11 | The test expects AVS or NVS or Both Information back, but the request does not send AVS data (Table 003) | Test case defect | `Segment 111 tables sent: 005, 030, 031; expected back: Segment 112 Table 003 AVS or NVS or Both Information` | [TD-TC-040396.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040396.json#L27) → `Variable Information Data Segment` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111 length and assert the AVS result in Segment 112 Table 003 - or drop the AVS expectation. AVS is used primarily for non-face-to-face transactions (mail/phone order, CAT), so confirm the card-present variants with BUYPASS. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 631](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L631) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040396.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040396.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| INFO | Same request as TC-040293 (BR1_TS13_TC2) | Information | `expected 2 vs 2` | TD-TC-040396.json vs TD-TC-040293.json | Check the difference on the device / in the response; optionally use another card. No change in the test data. |

<details><summary>Test data - actual, step 1: TD-TC-040396.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5113711878437156",
      "CardDiscretionaryBlockData": "270410167649565",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "403961",
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
          "VariableInformation": "901"
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

<details open><summary>Test data - expected, step 1: TD-TC-040396.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",  // I-01: recalculate: 070 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5113711878437156",
      "CardDiscretionaryBlockData": "270410167649565",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "403961",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",  // C-11: recalculate: 042 + 6 + length of the Table 003 data
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "901"
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
        },
        {  // C-11: Table 003 AVS Information - the request initiates the AVS result the test expects back
          "VariableInformationIndicator": "003",
          "VariableInformationLength": "<length of the AVS data, up to 029>",
          "VariableInformation": "<ZIP Code + 4 spaces, or ZIP+4><street address, up to 20 characters>"  // the cardholder's billing address - client test card data (Appendix I)
        }
      ]
    }
  }
}
```

</details>

<a id="tc-040571"></a>

### TC-040571 (BR1_TS14_TC75) - Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned

**Test name:** Contactless Magnetic Stripe Authorization Only (AVS or NVS or Both Information returned) → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 806](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L806) · **Every field against ATL105:** [Result_MD_Folder/TC-040571.md](Result_MD_Folder/TC-040571.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-11 | The test expects AVS or NVS or Both Information back, but the request does not send AVS data (Table 003) | Test case defect | `Segment 111 tables sent: 005, 030, 031; expected back: Segment 112 Table 003 AVS or NVS or Both Information` | [TD-TC-040571.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040571.json#L27) → `Variable Information Data Segment` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111 length and assert the AVS result in Segment 112 Table 003 - or drop the AVS expectation. AVS is used primarily for non-face-to-face transactions (mail/phone order, CAT), so confirm the card-present variants with BUYPASS. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 806](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L806) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040571.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040571.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| INFO | Same request as TC-040497 (BR1_TS14_TC1) | Information | `expected 2 vs 2` | TD-TC-040571.json vs TD-TC-040497.json | Check the difference on the device / in the response; optionally use another card. No change in the test data. |

<details><summary>Test data - actual, step 1: TD-TC-040571.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5165598658574242",
      "CardDiscretionaryBlockData": "310720142895840",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "405711",
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
          "VariableInformation": "911"
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

<details open><summary>Test data - expected, step 1: TD-TC-040571.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",  // I-01: recalculate: 070 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5165598658574242",
      "CardDiscretionaryBlockData": "310720142895840",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "405711",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",  // C-11: recalculate: 042 + 6 + length of the Table 003 data
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "911"
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
        },
        {  // C-11: Table 003 AVS Information - the request initiates the AVS result the test expects back
          "VariableInformationIndicator": "003",
          "VariableInformationLength": "<length of the AVS data, up to 029>",
          "VariableInformation": "<ZIP Code + 4 spaces, or ZIP+4><street address, up to 20 characters>"  // the cardholder's billing address - client test card data (Appendix I)
        }
      ]
    }
  }
}
```

</details>

<a id="tc-040683"></a>

### TC-040683 (BR1_TS15_TC77) - Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned

**Test name:** Manual / Keyed Authorization Only (AVS or NVS or Both Information returned) → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 918](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L918) · **Every field against ATL105:** [Result_MD_Folder/TC-040683.md](Result_MD_Folder/TC-040683.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-11 | The test expects AVS or NVS or Both Information back, but the request does not send AVS data (Table 003) | Test case defect | `Segment 111 tables sent: 005, 030, 031; expected back: Segment 112 Table 003 AVS or NVS or Both Information` | [TD-TC-040683.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040683.json#L27) → `Variable Information Data Segment` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111 length and assert the AVS result in Segment 112 Table 003 - or drop the AVS expectation. AVS is used primarily for non-face-to-face transactions (mail/phone order, CAT), so confirm the card-present variants with BUYPASS. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 918](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L918) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040683.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040683.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| INFO | Same request as TC-040608 (BR1_TS15_TC2) | Information | `expected 2 vs 2` | TD-TC-040683.json vs TD-TC-040608.json | Check the difference on the device / in the response; optionally use another card. No change in the test data. |

<details><summary>Test data - actual, step 1: TD-TC-040683.json (click to expand)</summary>

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
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5140312601563116",
      "CardDiscretionaryBlockData": "0932",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "406831",
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

<details open><summary>Test data - expected, step 1: TD-TC-040683.json (changed lines end with //)</summary>

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
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5140312601563116",
      "CardDiscretionaryBlockData": "0932",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "406831",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",  // C-11: recalculate: 042 + 6 + length of the Table 003 data
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
        },
        {  // C-11: Table 003 AVS Information - the request initiates the AVS result the test expects back
          "VariableInformationIndicator": "003",
          "VariableInformationLength": "<length of the AVS data, up to 029>",
          "VariableInformation": "<ZIP Code + 4 spaces, or ZIP+4><street address, up to 20 characters>"  // the cardholder's billing address - client test card data (Appendix I)
        }
      ]
    }
  }
}
```

</details>

<a id="tc-040800"></a>

### TC-040800 (BR1_TS16_TC79) - Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned

**Test name:** Chip Fallback Authorization Only (AVS or NVS or Both Information returned) → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 1035](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1035) · **Every field against ATL105:** [Result_MD_Folder/TC-040800.md](Result_MD_Folder/TC-040800.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-11 | The test expects AVS or NVS or Both Information back, but the request does not send AVS data (Table 003) | Test case defect | `Segment 111 tables sent: 005, 030, 031; expected back: Segment 112 Table 003 AVS or NVS or Both Information` | [TD-TC-040800.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040800.json#L27) → `Variable Information Data Segment` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111 length and assert the AVS result in Segment 112 Table 003 - or drop the AVS expectation. AVS is used primarily for non-face-to-face transactions (mail/phone order, CAT), so confirm the card-present variants with BUYPASS. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 1035](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1035) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040800.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040800.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| INFO | Same request as TC-040722 (BR1_TS16_TC1) | Information | `expected 2 vs 2` | TD-TC-040800.json vs TD-TC-040722.json | Check the difference on the device / in the response; optionally use another card. No change in the test data. |

<details><summary>Test data - actual, step 1: TD-TC-040800.json (click to expand)</summary>

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
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5176689985215877",
      "CardDiscretionaryBlockData": "0729",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "408001",
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
          "VariableInformation": "791"
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

<details open><summary>Test data - expected, step 1: TD-TC-040800.json (changed lines end with //)</summary>

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
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5176689985215877",
      "CardDiscretionaryBlockData": "0729",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "408001",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",  // C-11: recalculate: 042 + 6 + length of the Table 003 data
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "791"
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
        },
        {  // C-11: Table 003 AVS Information - the request initiates the AVS result the test expects back
          "VariableInformationIndicator": "003",
          "VariableInformationLength": "<length of the AVS data, up to 029>",
          "VariableInformation": "<ZIP Code + 4 spaces, or ZIP+4><street address, up to 20 characters>"  // the cardholder's billing address - client test card data (Appendix I)
        }
      ]
    }
  }
}
```

</details>

<a id="tc-040919"></a>

### TC-040919 (BR1_TS17_TC86) - Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned

**Test name:** Credential on File Authorization Only (AVS or NVS or Both Information returned) → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 1154](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1154) · **Every field against ATL105:** [Result_MD_Folder/TC-040919.md](Result_MD_Folder/TC-040919.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-11 | The test expects AVS or NVS or Both Information back, but the request does not send AVS data (Table 003) | Test case defect | `Segment 111 tables sent: 005, 030, 031, 032, 049, 056; expected back: Segment 112 Table 003 AVS or NVS or Both Information` | [TD-TC-040919.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040919.json#L27) → `Variable Information Data Segment` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111 length and assert the AVS result in Segment 112 Table 003 - or drop the AVS expectation. AVS is used primarily for non-face-to-face transactions (mail/phone order, CAT), so confirm the card-present variants with BUYPASS. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 1154](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1154) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-040919.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-040919.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| S-01 | Expiry format for stored credentials is not stated | To confirm with BUYPASS / SME | `1127` | Card Discretionary Block Data (entry mode 10 steps) | Confirm the expiry format for entry mode 10 with BUYPASS. |
| INFO | Same request as TC-040837 (BR1_TS17_TC4) | Information | `expected 2 vs 2` | TD-TC-040919.json vs TD-TC-040837.json | Check the difference on the device / in the response; optionally use another card. No change in the test data. |

<details><summary>Test data - actual, step 1: TD-TC-040919.json (click to expand)</summary>

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
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5186650025425825",
      "CardDiscretionaryBlockData": "1127",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "409191",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "078",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "101"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100100004"
        },
        {
          "VariableInformationIndicator": "032",
          "VariableInformationLength": "006",
          "VariableInformation": "08001C"
        },
        {
          "VariableInformationIndicator": "049",
          "VariableInformationLength": "004",
          "VariableInformation": "021C"
        },
        {
          "VariableInformationIndicator": "056",
          "VariableInformationLength": "008",
          "VariableInformation": "0904C101"
        }
      ]
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-040919.json (changed lines end with //)</summary>

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
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5186650025425825",
      "CardDiscretionaryBlockData": "1127",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "409191",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "078",  // C-11: recalculate: 078 + 6 + length of the Table 003 data
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "101"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100100004"
        },
        {
          "VariableInformationIndicator": "032",
          "VariableInformationLength": "006",
          "VariableInformation": "08001C"
        },
        {
          "VariableInformationIndicator": "049",
          "VariableInformationLength": "004",
          "VariableInformation": "021C"
        },
        {
          "VariableInformationIndicator": "056",
          "VariableInformationLength": "008",
          "VariableInformation": "0904C101"
        },
        {  // C-11: Table 003 AVS Information - the request initiates the AVS result the test expects back
          "VariableInformationIndicator": "003",
          "VariableInformationLength": "<length of the AVS data, up to 029>",
          "VariableInformation": "<ZIP Code + 4 spaces, or ZIP+4><street address, up to 20 characters>"  // the cardholder's billing address - client test card data (Appendix I)
        }
      ]
    }
  }
}
```

</details>

<a id="tc-041032"></a>

### TC-041032 (BR1_TS18_TC74) - Response / oracle check: Authorization Only -> AVS or NVS or Both Information returned

**Test name:** Contactless Mobile Commerce Device Authorization Only (AVS or NVS or Both Information returned) → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `pending_sme` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 1267](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1267) · **Every field against ATL105:** [Result_MD_Folder/TC-041032.md](Result_MD_Folder/TC-041032.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-11 | The test expects AVS or NVS or Both Information back, but the request does not send AVS data (Table 003) | Test case defect | `Segment 111 tables sent: 005, 030, 031; expected back: Segment 112 Table 003 AVS or NVS or Both Information` | [TD-TC-041032.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-041032.json#L27) → `Variable Information Data Segment` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111 length and assert the AVS result in Segment 112 Table 003 - or drop the AVS expectation. AVS is used primarily for non-face-to-face transactions (mail/phone order, CAT), so confirm the card-present variants with BUYPASS. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-041032.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-041032.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| INFO | Same request as TC-040959 (BR1_TS18_TC1) | Information | `expected 2 vs 2` | TD-TC-041032.json vs TD-TC-040959.json | Check the difference on the device / in the response; optionally use another card. No change in the test data. |

<details><summary>Test data - actual, step 1: TD-TC-041032.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",
      "InformationByte": "0",
      "TerminalIdentifier": "JX72948216050257001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5191864178826274",
      "CardDiscretionaryBlockData": "311220125996579",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "410321",
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
          "VariableInformation": "821"
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

<details open><summary>Test data - expected, step 1: TD-TC-041032.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "089",  // I-01: recalculate: 070 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5191864178826274",
      "CardDiscretionaryBlockData": "311220125996579",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "410321",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",  // C-11: recalculate: 042 + 6 + length of the Table 003 data
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "821"
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
        },
        {  // C-11: Table 003 AVS Information - the request initiates the AVS result the test expects back
          "VariableInformationIndicator": "003",
          "VariableInformationLength": "<length of the AVS data, up to 029>",
          "VariableInformation": "<ZIP Code + 4 spaces, or ZIP+4><street address, up to 20 characters>"  // the cardholder's billing address - client test card data (Appendix I)
        }
      ]
    }
  }
}
```

</details>

<a id="tc-041083"></a>

### TC-041083 (BR1_TS19_TC15) - Response / oracle check: Mail/Phone Authorization Only -> AVS or NVS or Both Information returned

**Test name:** Manual / Keyed Mail/Phone Authorization Only (AVS or NVS or Both Information returned) → approved. **What the test does:** step 1 sends `B020` (Mail/Phone Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 1318](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1318) · **Every field against ATL105:** [Result_MD_Folder/TC-041083.md](Result_MD_Folder/TC-041083.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-11 | The test expects AVS or NVS or Both Information back, but the request does not send AVS data (Table 003) | Test case defect | `Segment 111 tables sent: 005, 030, 031; expected back: Segment 112 Table 003 AVS or NVS or Both Information` | [TD-TC-041083.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-041083.json#L27) → `Variable Information Data Segment` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111 length and assert the AVS result in Segment 112 Table 003 - or drop the AVS expectation. AVS is used primarily for non-face-to-face transactions (mail/phone order, CAT), so confirm the card-present variants with BUYPASS. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 1318](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1318) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-041083.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-041083.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| INFO | Same request as TC-041069 (BR1_TS19_TC1) | Information | `expected 2 vs 2` | TD-TC-041083.json vs TD-TC-041069.json | Check the difference on the device / in the response; optionally use another card. No change in the test data. |

<details><summary>Test data - actual, step 1: TD-TC-041083.json (click to expand)</summary>

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
        "TransactionType": "B",
        "CardType": "020"
      },
      "AccountNumber": "5167557820382510",
      "CardDiscretionaryBlockData": "1131",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "410831",
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
          "VariableInformation": "0102100004"
        }
      ]
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-041083.json (changed lines end with //)</summary>

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
        "TransactionType": "B",
        "CardType": "020"
      },
      "AccountNumber": "5167557820382510",
      "CardDiscretionaryBlockData": "1131",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "410831",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",  // C-11: recalculate: 042 + 6 + length of the Table 003 data
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
          "VariableInformation": "0102100004"
        },
        {  // C-11: Table 003 AVS Information - the request initiates the AVS result the test expects back
          "VariableInformationIndicator": "003",
          "VariableInformationLength": "<length of the AVS data, up to 029>",
          "VariableInformation": "<ZIP Code + 4 spaces, or ZIP+4><street address, up to 20 characters>"  // the cardholder's billing address - client test card data (Appendix I)
        }
      ]
    }
  }
}
```

</details>

<a id="tc-041101"></a>

### TC-041101 (BR1_TS20_TC9) - Response / oracle check: Mail/Phone Authorization Only -> AVS or NVS or Both Information returned

**Test name:** Credential on File Mail/Phone Authorization Only (AVS or NVS or Both Information returned) → approved. **What the test does:** step 1 sends `B020` (Mail/Phone Authorization Only), expected Response Code `2`.

**Status:** `pending_sme` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 1336](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1336) · **Every field against ATL105:** [Result_MD_Folder/TC-041101.md](Result_MD_Folder/TC-041101.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-11 | The test expects AVS or NVS or Both Information back, but the request does not send AVS data (Table 003) | Test case defect | `Segment 111 tables sent: 005, 030, 031, 032, 049, 056; expected back: Segment 112 Table 003 AVS or NVS or Both Information` | [TD-TC-041101.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-041101.json#L27) → `Variable Information Data Segment` | Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111 length and assert the AVS result in Segment 112 Table 003 - or drop the AVS expectation. AVS is used primarily for non-face-to-face transactions (mail/phone order, CAT), so confirm the card-present variants with BUYPASS. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-041101.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-041101.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |
| S-01 | Expiry format for stored credentials is not stated | To confirm with BUYPASS / SME | `0428` | Card Discretionary Block Data (entry mode 10 steps) | Confirm the expiry format for entry mode 10 with BUYPASS. |
| INFO | Same request as TC-041093 (BR1_TS20_TC1) | Information | `expected 2 vs 2` | TD-TC-041101.json vs TD-TC-041093.json | Check the difference on the device / in the response; optionally use another card. No change in the test data. |

<details><summary>Test data - actual, step 1: TD-TC-041101.json (click to expand)</summary>

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
        "TransactionType": "B",
        "CardType": "020"
      },
      "AccountNumber": "5197451601367458",
      "CardDiscretionaryBlockData": "0428",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "411011",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "078",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "101"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0102100004"
        },
        {
          "VariableInformationIndicator": "032",
          "VariableInformationLength": "006",
          "VariableInformation": "08001C"
        },
        {
          "VariableInformationIndicator": "049",
          "VariableInformationLength": "004",
          "VariableInformation": "021C"
        },
        {
          "VariableInformationIndicator": "056",
          "VariableInformationLength": "008",
          "VariableInformation": "0904C101"
        }
      ]
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-041101.json (changed lines end with //)</summary>

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
        "TransactionType": "B",
        "CardType": "020"
      },
      "AccountNumber": "5197451601367458",
      "CardDiscretionaryBlockData": "0428",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "411011",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "078",  // C-11: recalculate: 078 + 6 + length of the Table 003 data
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "101"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0102100004"
        },
        {
          "VariableInformationIndicator": "032",
          "VariableInformationLength": "006",
          "VariableInformation": "08001C"
        },
        {
          "VariableInformationIndicator": "049",
          "VariableInformationLength": "004",
          "VariableInformation": "021C"
        },
        {
          "VariableInformationIndicator": "056",
          "VariableInformationLength": "008",
          "VariableInformation": "0904C101"
        },
        {  // C-11: Table 003 AVS Information - the request initiates the AVS result the test expects back
          "VariableInformationIndicator": "003",
          "VariableInformationLength": "<length of the AVS data, up to 029>",
          "VariableInformation": "<ZIP Code + 4 spaces, or ZIP+4><street address, up to 20 characters>"  // the cardholder's billing address - client test card data (Appendix I)
        }
      ]
    }
  }
}
```

</details>

<a id="tc-038607"></a>

### TC-038607 (BR1_TS15_TC1) - Loyalty Card Data Segment (segment 108) omitted where p.15 requires it

**Test name:** Manual / Keyed Authorization Only Loyalty Card Data Segment omitted where requires it → declined. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `1`.

**Status:** `ready` · **AI result:** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 16](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L16) · **Every field against ATL105:** [Result_MD_Folder/TC-038607.md](Result_MD_Folder/TC-038607.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-02 | The rule this negative test claims to break is not in ATL105 | Test case defect | `test_objective` = `Loyalty Card Data Segment (segment 108) omitted where p.15 requires it`; expected Response Code `1`; `spec_basis[0]` = p.15: "Expiration Date must be included in Card Discretionary Block Data (Element No." | test case record [test_cases.jsonl line 16](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L16) → `test_objective` | Remove the test or rewrite it around a rule ATL105 states. |
| C-03 | Marked 'ready' although the terminal is a placeholder | Test case issue to correct | `status` = `ready` | test case record [test_cases.jsonl line 16](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L16) → `status` | `status` = `requires_review` (not `ready`) until the real Terminal Identifier is supplied. |
| I-01 | The Terminal Identifier is a placeholder | Awaiting client input / run-time value | `JX72948216050257001` | [TD-TC-038607.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-038607.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier. |

<details><summary>Test data - actual, step 1: TD-TC-038607.json (click to expand)</summary>

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
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5122228349075928",
      "CardDiscretionaryBlockData": "1232",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "386071",
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

<details open><summary>Test data - expected, step 1: TD-TC-038607.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {  // C-02: no change to the request - what must change is the test (its premise and the expected decline); see the finding
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "078",  // I-01: recalculate: 059 + length of the real Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "<Terminal Identifier assigned by BUYPASS>",  // I-01: placeholder - use the Terminal Identifier BUYPASS assigned to this terminal
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5122228349075928",
      "CardDiscretionaryBlockData": "1232",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "386071",
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

## How to demonstrate the defects during the BA discussion

1. **Table 048 MasterCard Authentication Data is claimed by the test but not sent in the request** - open [TC-040080](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L315) and read the test name and objective, then open [TD-TC-040080.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040080.json#L27) → `Variable Information Data Segment`: actual `Segment 111 tables sent: 005, 030, 031`; expected: Add Table 048 to Segment 111 with the data its Appendix I layout requires and recalculate the Segment 111 length - or keep the test out of 'ready' with the missing data named as an open item. Table 048 applies to e-commerce transactions authenticated with (EMV) 3-D Secure (Appendix I), so a card-present test must be re-scoped to such a transaction or withdrawn.
2. **Tax Type 1 = GST is claimed by the test but not sent in the request** - open [TC-040116](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L351) and read the test name and objective, then open [TD-TC-040116.json line 2](mastercard_credit_new_16_11/test_data/TD-TC-040116.json#L2) → `EMV Financial Transaction Request`: actual `Tax Type 1: not in the test data; segments sent: 100, 111, 130`; expected: Send Segment 102 (Product Code) and Segment 143 (Tax by Product) with the same product(s) in the same order, the inclusive/exclusive flag (I or E), Tax Type 1 = GST and the tax amount; recalculate the segment count and lengths. GST is a Canadian tax type and Segment 143 is sent only if the account manager instructs it (Section 11.1), so confirm the merchant context - or re-scope / withdraw the test.
3. **Original Authorization Only requests carry an Approval Number and other fields that do not belong to them** - open [TC-040126](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L361) and read the test name and objective, then open [TD-TC-040126.json line 23](mastercard_credit_new_16_11/test_data/TD-TC-040126.json#L23) → `Standard Message Data Segment → ApprovalNumber`: actual `J38FQA`; expected: (empty) - an original Authorization Only carries no Approval Number
4. **AVS information expected in the response, but the request sends no AVS data (Table 003)** - open [TC-040140](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L375) and read the test name and objective, then open [TD-TC-040140.json line 27](mastercard_credit_new_16_11/test_data/TD-TC-040140.json#L27) → `Variable Information Data Segment`: actual `Segment 111 tables sent: 005, 030, 031; expected back: Segment 112 Table 003 AVS or NVS or Both Information`; expected: Send Table 003 in Segment 111 with the cardholder's ZIP Code (5 characters + 4 spaces, or ZIP+4) and street address (up to 20 characters) from the client's test card data, recalculate the Segment 111 length and assert the AVS result in Segment 112 Table 003 - or drop the AVS expectation. AVS is used primarily for non-face-to-face transactions (mail/phone order, CAT), so confirm the card-present variants with BUYPASS.
5. **Negative test leaves out something ATL105 does not require here - the expected decline is not justified** - open [TC-038607](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L16) and read the test name and objective, then open test case record [test_cases.jsonl line 16](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L16) → `test_objective`: actual `test_objective` = `Loyalty Card Data Segment (segment 108) omitted where p.15 requires it`; expected Response Code `1`; `spec_basis[0]` = p.15: "Expiration Date must be included in Card Discretionary Block Data (Element No."; expected: Remove the test or rewrite it around a rule ATL105 states.
6. **The Terminal Identifier is a generated placeholder, yet the test cases are marked ready** - open [TC-039912](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L147) and read the test name and objective, then open [TD-TC-039912.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-039912.json#L9) → `Standard Message Data Segment → TerminalIdentifier`: actual `JX72948216050257001`; expected: Client/BUYPASS to supply the real Terminal Identifier for this merchant and confirm its location. Then recalculate the Segment 100 Segment Length of each step: the length minus 19 (this placeholder) plus the length of the real Terminal Identifier.
7. **Time-out Reversal sent although the original request is expected to be answered** - open [TC-039915](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L150) and read the test name and objective, then open test case record [test_cases.jsonl line 150](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L150) → `tx_legs[0].expect`: actual `tx_legs[0].expect` = {"tier": "ASSERT", "page": 403, "response_code": "0", "note": "Approved-Purchase/Capture"} and `harness` = `null` (no fault injection); expected: Make step 1 a fault-injected 'no response' (or a late response the harness ignores). Step 1 then expects no response (instead of Response Code 0), and the device sends the Time-out Reversal after the 30-second response interval.
8. **'Different terminal' negative tests use a terminal BUYPASS does not know** - open [TC-039914](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L149) and read the test name and objective, then open [TD-TC-039914.step2.reversal.json line 9](mastercard_credit_new_16_11/test_data/TD-TC-039914.step2.reversal.json#L9) → `Standard Message Data Segment → TerminalIdentifier`: actual `VS24671841985066391107`; expected: Use a second real terminal of the same merchant.
9. **The test objective names a different transaction than the one sent** - open [TC-039916](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151) and read the test name and objective, then open test case record [test_cases.jsonl line 151](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151) → `test_objective`: actual `test_objective` = Decision table: Authorization Only with segment 153 sent (the rule's condition holds); expected: `test_objective` = `Decision table: Purchase/Capture with segment 153 sent (the rule's condition holds)` - Correct the objective to POS Purchase/Capture.
10. **Network token data (Segment 153) is synthesized, and its indicator and length need confirmation** - open [TC-039916](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L151) and read the test name and objective, then open [TD-TC-039916.json line 50](mastercard_credit_new_16_11/test_data/TD-TC-039916.json#L50) → `Network Token Data Request → SegmentLengthIndicator`: actual `051`; expected: Confirm the Field Separator rule of this segment with BUYPASS.
11. **Follow-on values are instruction text to be filled in from the previous response** - open [TC-039913](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L148) and read the test name and objective, then open [TD-TC-039913.step2.reversal.json line 23](mastercard_credit_new_16_11/test_data/TD-TC-039913.step2.reversal.json#L23) → `Standard Message Data Segment → ApprovalNumber`: actual `<ApprovalNumber from step 1 response>`; expected: At run time the Approval Number returned in the step 1 response replaces this text (the test case's own example value is `7IYI8R`).
12. **EMV chip data is generated, not captured from a card or simulator** - open [TC-040080](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L315) and read the test name and objective, then open [TD-TC-040080.json line 51](mastercard_credit_new_16_11/test_data/TD-TC-040080.json#L51) → `EMV Request Data Segment → CAPublicKeyFileChecksum`: actual `2012091700000100000102171`; expected: Confirm the checksum of the host's current CA Public Key file before execution.
13. **Same request as another test case (difference only on the device / response side)** - open [TC-040080](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L315) and read the test name and objective, then open TD-TC-040080.json vs TD-TC-040065.json: actual `expected 2 vs 2`; expected: Check the difference on the device / in the response; optionally use another card.
14. **Expiry format for stored credentials (entry mode 10) not stated** - open [TC-040856](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L1091) and read the test name and objective, then open Card Discretionary Block Data (entry mode 10 steps): actual `1127`; expected: Confirm the expiry format for entry mode 10 with BUYPASS.
15. **ATL105 citations in the test cases are on the wrong page, not ATL105 text, or about another context** - open [TC-039912](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L147) and read the test name and objective, then open test case record [test_cases.jsonl line 147](mastercard_credit_new_16_11/test_cases/test_cases.jsonl#L147) → `device_checkpoints[3]`, `device_checkpoints[4]`: actual `device_checkpoints[3]` = MUST: When pumping is complete, the device must provide an Invoice number, as well as all prompts for which the device has already captured responses, with the Capture message request. (p.127); `device_checkpoints[4]` = MUST: In either case, when pumping is complete, the values entered for all prompts must be sent with the capture message. (p.138); expected: Drop checkpoints that do not apply to this merchant.

## Summary for BA

**15 defects in 43 of the 43 test cases; 7 of them are high priority.**

Raised in the BA defect log:

- Table 048 MasterCard Authentication Data is claimed by the test but not sent in the request - 11 test case(s) (AI-team register N17-01).
- Tax Type 1 = GST is claimed by the test but not sent in the request - 8 test case(s) (AI-team register N17-01).
- Original Authorization Only requests carry an Approval Number and other fields that do not belong to them - 8 test case(s) (AI-team register N17-03).
- AVS information expected in the response, but the request sends no AVS data (Table 003) - 10 test case(s) (BA review 19-11-2026).
- Negative test leaves out something ATL105 does not require here - the expected decline is not justified - 1 test case(s) (BA review 19-11-2026).

Also found by the validator (high priority):

- The Terminal Identifier is a generated placeholder, yet the test cases are marked ready - 43 test case(s).
- Time-out Reversal sent although the original request is expected to be answered - 1 test case(s).

Medium and low priority: 'Different terminal' negative tests use a terminal BUYPASS does not know (2); The test objective names a different transaction than the one sent (1); Network token data (Segment 153) is synthesized, and its indicator and length need confirmation (1); Follow-on values are instruction text to be filled in from the previous response (2); EMV chip data is generated, not captured from a card or simulator (11); Same request as another test case (difference only on the device / response side) (11); Expiry format for stored credentials (entry mode 10) not stated (5); ATL105 citations in the test cases are on the wrong page, not ATL105 text, or about another context (22).

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
| [test_data/TD-TC-040080.json](mastercard_credit_new_16_11/test_data/TD-TC-040080.json) | `ae1cad077ddc07bd…` | yes |
| [test_data/TD-TC-040194.json](mastercard_credit_new_16_11/test_data/TD-TC-040194.json) | `2599d7cd8f7bbaec…` | yes |
| [test_data/TD-TC-040308.json](mastercard_credit_new_16_11/test_data/TD-TC-040308.json) | `9fb87dfb4f52f9e6…` | yes |
| [test_data/TD-TC-040512.json](mastercard_credit_new_16_11/test_data/TD-TC-040512.json) | `e23d21e70b2de057…` | yes |
| [test_data/TD-TC-040623.json](mastercard_credit_new_16_11/test_data/TD-TC-040623.json) | `00f1817a375ca8d8…` | yes |
| [test_data/TD-TC-040737.json](mastercard_credit_new_16_11/test_data/TD-TC-040737.json) | `8abf00ee29cfb9d3…` | yes |
| [test_data/TD-TC-040856.json](mastercard_credit_new_16_11/test_data/TD-TC-040856.json) | `f84e6fb64a8acb13…` | yes |
| [test_data/TD-TC-040974.json](mastercard_credit_new_16_11/test_data/TD-TC-040974.json) | `351382458f6fd75a…` | yes |
| [test_data/TD-TC-041219.json](mastercard_credit_new_16_11/test_data/TD-TC-041219.json) | `a856ed2cc6ec61dd…` | yes |
| [test_data/TD-TC-041315.json](mastercard_credit_new_16_11/test_data/TD-TC-041315.json) | `e35b74776580bc58…` | yes |
| [test_data/TD-TC-041606.json](mastercard_credit_new_16_11/test_data/TD-TC-041606.json) | `427169d3ce606bb1…` | yes |
| [test_data/TD-TC-040116.json](mastercard_credit_new_16_11/test_data/TD-TC-040116.json) | `998b1c53c54eeec2…` | yes |
| [test_data/TD-TC-040230.json](mastercard_credit_new_16_11/test_data/TD-TC-040230.json) | `6aa8ae208ebe27dd…` | yes |
| [test_data/TD-TC-040346.json](mastercard_credit_new_16_11/test_data/TD-TC-040346.json) | `a22e798e8f9a3882…` | yes |
| [test_data/TD-TC-040547.json](mastercard_credit_new_16_11/test_data/TD-TC-040547.json) | `39ca6ed4346c01a7…` | yes |
| [test_data/TD-TC-040658.json](mastercard_credit_new_16_11/test_data/TD-TC-040658.json) | `c2e0a22bd60d386b…` | yes |
| [test_data/TD-TC-040776.json](mastercard_credit_new_16_11/test_data/TD-TC-040776.json) | `08831e2c4979a176…` | yes |
| [test_data/TD-TC-040893.json](mastercard_credit_new_16_11/test_data/TD-TC-040893.json) | `69278010cfc3c69b…` | yes |
| [test_data/TD-TC-041009.json](mastercard_credit_new_16_11/test_data/TD-TC-041009.json) | `9286b6b3187e02dd…` | yes |
| [test_data/TD-TC-040126.json](mastercard_credit_new_16_11/test_data/TD-TC-040126.json) | `f7a0b79469469ddf…` | yes |
| [test_data/TD-TC-040240.json](mastercard_credit_new_16_11/test_data/TD-TC-040240.json) | `bb6c30991bad509e…` | yes |
| [test_data/TD-TC-040356.json](mastercard_credit_new_16_11/test_data/TD-TC-040356.json) | `cc5645d34e1c9267…` | yes |
| [test_data/TD-TC-040557.json](mastercard_credit_new_16_11/test_data/TD-TC-040557.json) | `45897f878199503f…` | yes |
| [test_data/TD-TC-040668.json](mastercard_credit_new_16_11/test_data/TD-TC-040668.json) | `96a134a101e50eea…` | yes |
| [test_data/TD-TC-040786.json](mastercard_credit_new_16_11/test_data/TD-TC-040786.json) | `8ce06b757ee83203…` | yes |
| [test_data/TD-TC-040903.json](mastercard_credit_new_16_11/test_data/TD-TC-040903.json) | `0736783fbe290f7e…` | yes |
| [test_data/TD-TC-041019.json](mastercard_credit_new_16_11/test_data/TD-TC-041019.json) | `db7bb52e4769b8d7…` | yes |
| [test_data/TD-TC-040140.json](mastercard_credit_new_16_11/test_data/TD-TC-040140.json) | `8ddaebf46984a39b…` | yes |
| [test_data/TD-TC-040254.json](mastercard_credit_new_16_11/test_data/TD-TC-040254.json) | `0a52871d4f3b0121…` | yes |
| [test_data/TD-TC-040396.json](mastercard_credit_new_16_11/test_data/TD-TC-040396.json) | `25dc6bafc2b46b80…` | yes |
| [test_data/TD-TC-040571.json](mastercard_credit_new_16_11/test_data/TD-TC-040571.json) | `85fbcfd649b5eeef…` | yes |
| [test_data/TD-TC-040683.json](mastercard_credit_new_16_11/test_data/TD-TC-040683.json) | `fbb996833d45c1f7…` | yes |
| [test_data/TD-TC-040800.json](mastercard_credit_new_16_11/test_data/TD-TC-040800.json) | `d713670d0f5be2ba…` | yes |
| [test_data/TD-TC-040919.json](mastercard_credit_new_16_11/test_data/TD-TC-040919.json) | `dbd8891fd47eeabf…` | yes |
| [test_data/TD-TC-041032.json](mastercard_credit_new_16_11/test_data/TD-TC-041032.json) | `ddfbe64b4ce3b509…` | yes |
| [test_data/TD-TC-041083.json](mastercard_credit_new_16_11/test_data/TD-TC-041083.json) | `8cfd15bdb9a72a23…` | yes |
| [test_data/TD-TC-041101.json](mastercard_credit_new_16_11/test_data/TD-TC-041101.json) | `3c65ba6383034df6…` | yes |
| [test_data/TD-TC-038607.json](mastercard_credit_new_16_11/test_data/TD-TC-038607.json) | `f4959b7e0533967f…` | yes |

The other 2,376 test data files of the run were read as well, for the checks across test cases (Sequence Number per terminal, identical requests).

| Document | What it is |
|---|---|
| [Result_MD_Folder/ATL105-TD-Validation-Summary.md](Result_MD_Folder/ATL105-TD-Validation-Summary.md) | Summary: every finding with the ATL105 section, PDF page, printed page and exact text, how to check it by hand and the fix |
| [Result_MD_Folder/TC-039912.md](Result_MD_Folder/TC-039912.md) | BR1_TS5_TC1: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-039913.md](Result_MD_Folder/TC-039913.md) | BR1_TS5_TC2: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-039914.md](Result_MD_Folder/TC-039914.md) | BR1_TS5_TC3: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-039915.md](Result_MD_Folder/TC-039915.md) | BR1_TS5_TC4: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-039916.md](Result_MD_Folder/TC-039916.md) | BR1_TS5_TC5: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040080.md](Result_MD_Folder/TC-040080.md) | BR1_TS11_TC21: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040194.md](Result_MD_Folder/TC-040194.md) | BR1_TS12_TC17: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040308.md](Result_MD_Folder/TC-040308.md) | BR1_TS13_TC17: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040512.md](Result_MD_Folder/TC-040512.md) | BR1_TS14_TC16: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040623.md](Result_MD_Folder/TC-040623.md) | BR1_TS15_TC17: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040737.md](Result_MD_Folder/TC-040737.md) | BR1_TS16_TC16: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040856.md](Result_MD_Folder/TC-040856.md) | BR1_TS17_TC23: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040974.md](Result_MD_Folder/TC-040974.md) | BR1_TS18_TC16: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-041219.md](Result_MD_Folder/TC-041219.md) | BR1_TS54_TC14: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-041315.md](Result_MD_Folder/TC-041315.md) | BR1_TS154_TC19: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-041606.md](Result_MD_Folder/TC-041606.md) | BR1_TS173_TC15: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040116.md](Result_MD_Folder/TC-040116.md) | BR1_TS11_TC57: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040230.md](Result_MD_Folder/TC-040230.md) | BR1_TS12_TC53: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040346.md](Result_MD_Folder/TC-040346.md) | BR1_TS13_TC55: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040547.md](Result_MD_Folder/TC-040547.md) | BR1_TS14_TC51: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040658.md](Result_MD_Folder/TC-040658.md) | BR1_TS15_TC52: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040776.md](Result_MD_Folder/TC-040776.md) | BR1_TS16_TC55: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040893.md](Result_MD_Folder/TC-040893.md) | BR1_TS17_TC60: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-041009.md](Result_MD_Folder/TC-041009.md) | BR1_TS18_TC51: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040126.md](Result_MD_Folder/TC-040126.md) | BR1_TS11_TC67: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040240.md](Result_MD_Folder/TC-040240.md) | BR1_TS12_TC63: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040356.md](Result_MD_Folder/TC-040356.md) | BR1_TS13_TC65: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040557.md](Result_MD_Folder/TC-040557.md) | BR1_TS14_TC61: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040668.md](Result_MD_Folder/TC-040668.md) | BR1_TS15_TC62: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040786.md](Result_MD_Folder/TC-040786.md) | BR1_TS16_TC65: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040903.md](Result_MD_Folder/TC-040903.md) | BR1_TS17_TC70: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-041019.md](Result_MD_Folder/TC-041019.md) | BR1_TS18_TC61: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040140.md](Result_MD_Folder/TC-040140.md) | BR1_TS11_TC81: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040254.md](Result_MD_Folder/TC-040254.md) | BR1_TS12_TC77: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040396.md](Result_MD_Folder/TC-040396.md) | BR1_TS13_TC105: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040571.md](Result_MD_Folder/TC-040571.md) | BR1_TS14_TC75: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040683.md](Result_MD_Folder/TC-040683.md) | BR1_TS15_TC77: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040800.md](Result_MD_Folder/TC-040800.md) | BR1_TS16_TC79: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-040919.md](Result_MD_Folder/TC-040919.md) | BR1_TS17_TC86: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-041032.md](Result_MD_Folder/TC-041032.md) | BR1_TS18_TC74: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-041083.md](Result_MD_Folder/TC-041083.md) | BR1_TS19_TC15: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-041101.md](Result_MD_Folder/TC-041101.md) | BR1_TS20_TC9: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-038607.md](Result_MD_Folder/TC-038607.md) | BR1_TS15_TC1: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Manual Validation/TC-039912-Manual-Validation.md](Manual%20Validation/TC-039912-Manual-Validation.md) | BR1_TS5_TC1: step-by-step manual validation guide with the ATL105 extracts |
| [Manual Validation/TC-039913-Manual-Validation.md](Manual%20Validation/TC-039913-Manual-Validation.md) | BR1_TS5_TC2: step-by-step manual validation guide with the ATL105 extracts |
| [Manual Validation/TC-039914-Manual-Validation.md](Manual%20Validation/TC-039914-Manual-Validation.md) | BR1_TS5_TC3: step-by-step manual validation guide with the ATL105 extracts |
| [Manual Validation/TC-039915-Manual-Validation.md](Manual%20Validation/TC-039915-Manual-Validation.md) | BR1_TS5_TC4: step-by-step manual validation guide with the ATL105 extracts |
| [Manual Validation/TC-039916-Manual-Validation.md](Manual%20Validation/TC-039916-Manual-Validation.md) | BR1_TS5_TC5: step-by-step manual validation guide with the ATL105 extracts |

Produced with (from `core-auth-ai-validation-framework/regression-artifact-validator`): `python scripts/validate-run-and-build-manual-validation.py --run DhanSingh_mastercard_credit_output_16_11\mastercard_credit_new_16_11 --only TC-039912,TC-039913,TC-039914,TC-039915,TC-039916,TC-040080,TC-040194,TC-040308,TC-040512,TC-040623,TC-040737,TC-040856,TC-040974,TC-041219,TC-041315,TC-041606,TC-040116,TC-040230,TC-040346,TC-040547,TC-040658,TC-040776,TC-040893,TC-041009,TC-040126,TC-040240,TC-040356,TC-040557,TC-040668,TC-040786,TC-040903,TC-041019,TC-040140,TC-040254,TC-040396,TC-040571,TC-040683,TC-040800,TC-040919,TC-041032,TC-041083,TC-041101,TC-038607 --guides TC-039912,TC-039913,TC-039914,TC-039915,TC-039916 --package DhanSingh_mastercard_credit_output_16_11 --sheet MasterCard_Credit_Defect_Demo_Sheet.md --title "MasterCard Credit (plan v12) - defect demo sheet: 5 manual-validation test cases and the BA defect log (up to 19-11-2026)" --scope "One sheet for the demo. It combines (1) the manual validation of TC-039912 to TC-039916 (scenario BR1_TS5, manual/keyed purchase) and (2) every test case of the BA defect log: Table 048 MasterCard Authentication Data claimed but not sent (11 test cases) and Tax Type 1 = GST claimed but not sent, with Segments 102 and 143 missing (8) - AI-team register N17-01; original Authorization Only requests that carry an Approval Number (8) - N17-03; and, raised on 19-11-2026, AVS information expected in the response while Table 003 is not sent (10) and TC-038607, a negative test that leaves out Segment 108 although nothing makes it mandatory. All 43 come from the run mastercard_credit_new_16_11; every field of their test data was checked against ATL105 by the validator, which now detects each of these defect kinds. Each defect is explained the way the BA note does, and the actual and expected test data of every test case follow."`. The validator ran on all 2,129 test cases of the run; this sheet covers the test cases above.

