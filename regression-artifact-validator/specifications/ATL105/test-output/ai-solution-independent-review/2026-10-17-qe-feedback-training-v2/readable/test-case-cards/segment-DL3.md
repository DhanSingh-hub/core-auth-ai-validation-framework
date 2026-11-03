# Segment DL3 — Test Case Cards

One card per test case (QE feedback Def_SepRun07/Def_SepRun10). Derived from the canonical JSON on every run; JSON stays authoritative. Fields flagged UNTRUSTED_VALID_VALUES, AMBIGUOUS_LENGTH or DELIBERATE_CONSTRAINT_VIOLATION are marked ⚠.

## TC-005440 — Business_Rule — Totals Request — If merchant does not request read and cut totals, BUYPASS automaticall

**Purpose:** If merchant does not request read and cut totals, BUYPASS automatically closes business day at designated cut time. (source [p.77](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=77))

**Derivation path:** Spec [p.77](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=77) (ATL105 2026-3) → BR-77-1 → REQ-SRC-ATL105-PDF-001:468 → derivation_method = business_rule_derived → SC-5842 (business_rule) → TC-005440 (transaction = Totals Request)

**Preconditions:** transaction type Totals Request; message layout step 1 Totals Request segments 105

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 105 | Segment Type | 105 | fixed_value |  |
| 105 | Information Byte | 0 | qe_example_value |  |
| 105 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 105 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 105 | Totals Date | 111111 | qe_example_value |  |
| 105 | Hardware Version | GMC21VFP | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 105 | Software Version | 7N6BG7HW | format_synthesis |  |
| 105 | Firmware Version | O474B2I1 | format_synthesis |  |
| 105 | Sequence Number | 000001 | qe_example_value |  |
| 105 | Currency Code | 840 | qe_example_value |  |
| 105 | Grand Total | 00000000 | qe_example_value |  |
| 105 | Card Label | CC | qe_example_value |  |
| 105 | Card Type Total Count | 00001 | qe_example_value |  |
| 105 | Card Type Total Amount | 00000000 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:468](../traceability/segment-DL3.md) → SC-5842 → TC-005440 → `TC-005440.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-013209 — Field_Constraint — Electronic Mail — In Date and Time Data Segment (Data Segment No. DL3), field 6 (Passwor

**Purpose:** In Date and Time Data Segment (Data Segment No. DL3), field 6 (Password) is required and must be present. (source [p.328](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=328))

**Derivation path:** Spec [p.328](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=328) (ATL105 2026-3) → ENT-FIELD-DL3-6 → REQ-SRC-ATL105-PDF-001:3620 → derivation_method = field_constraint → SC-8994 (field_constraint) → TC-013209 (transaction = Electronic Mail)

**Preconditions:** transaction type Electronic Mail; message layout step 1 Electronic Mail segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3620](../traceability/segment-DL3.md) → SC-8994 → TC-013209 → `TC-013209.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-013210 — Field_Constraint — Electronic Mail Request — In Date and Time Data Segment (Data Segment No. DL3), field 6 (Passwor

**Purpose:** In Date and Time Data Segment (Data Segment No. DL3), field 6 (Password) is required and must be present. (source [p.328](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=328))

**Derivation path:** Spec [p.328](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=328) (ATL105 2026-3) → ENT-FIELD-DL3-6 → REQ-SRC-ATL105-PDF-001:3620 → derivation_method = field_constraint → SC-8994 (field_constraint) → TC-013210 (transaction = Electronic Mail Request)

**Preconditions:** transaction type Electronic Mail Request; message layout step 1 Electronic Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3620](../traceability/segment-DL3.md) → SC-8994 → TC-013210 → `TC-013210.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-013211 — Field_Constraint — Financial Transaction Request — In Date and Time Data Segment (Data Segment No. DL3), field 6 (Passwor

**Purpose:** In Date and Time Data Segment (Data Segment No. DL3), field 6 (Password) is required and must be present. (source [p.328](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=328))

**Derivation path:** Spec [p.328](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=328) (ATL105 2026-3) → ENT-FIELD-DL3-6 → REQ-SRC-ATL105-PDF-001:3620 → derivation_method = field_constraint → SC-8994 (field_constraint) → TC-013211 (transaction = Financial Transaction Request)

**Preconditions:** transaction type Financial Transaction Request; message layout step 1 Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 110, 111, 113, 114, 119, 123, 132, 135, 143, 145, 151, 153, 156

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 100 | Segment Type | 100 | fixed_value |  |
| 100 | Information Byte | 0 | qe_example_value |  |
| 100 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 100 | Prompt Code | 0001 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 100 | Account Number | ‹masked, 24 chars› | realistic_pan | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 100 | Sequence Number | 000001 | qe_example_value |  |
| 100 | Partial Approval Indicator | 0 | qe_example_value |  |
| 101 | Segment Type | 101 | fixed_value |  |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 221990 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 830264 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 46802701 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 113 | Segment Type | 113 | fixed_value |  |
| 113 | ECA/ TeleCheck ® Clerk ID | 1 | qe_example_value |  |
| 113 | ECA/ TeleCheck ® Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 113 | Merchant Trace ID | ‹masked, 25 chars› | format_synthesis |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 119 | Segment Type | 119 | fixed_value |  |
| 119 | Information Byte | 0 | qe_example_value |  |
| 119 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 119 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 119 | Totals Date | 111111 | qe_example_value |  |
| 119 | Hardware Version | 34TMGG0Y | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | D2QQ8E4W | format_synthesis |  |
| 119 | Firmware Version | 0AUS4ADZ | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 227317 | format_synthesis |  |
| 119 | Host Discount Timestamp | 202506151430 | qe_example_value |  |
| 119 | Currency Code | 840 | qe_example_value |  |
| 119 | Grand Total | 00000000 | qe_example_value |  |
| 119 | Card Label | CC | qe_example_value |  |
| 119 | Card Type Total Count | 00001 | qe_example_value |  |
| 119 | Card Type Total Amount | 00000000 | qe_example_value |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 132 | Segment Type | 132 | fixed_value |  |
| 132 | Sequence Number | 000001 | qe_example_value |  |
| 132 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 132 | Load Type | K | fixed_value |  |
| 132 | Hardware Version | 34TMGG0Y | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | D2QQ8E4W | format_synthesis |  |
| 132 | Firmware Version | 0AUS4ADZ | format_synthesis |  |
| 132 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 132 | Block Number | 000 | qe_example_value |  |
| 135 | Segment Type | 100 | qe_example_value |  |
| 135 | Moneris Data | 001 | qe_example_value |  |
| 143 | Segment Type | 143 | fixed_value |  |
| 143 | Number of Products | 01 | qe_example_value |  |
| 143 | Product Code | 001 | qe_example_value |  |
| 143 | Inclusive/ Exclusive for Tax 1 | I | qe_example_value |  |
| 145 | Segment Type | 100 | qe_example_value |  |
| 145 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 146 | Segment Type | 100 | qe_example_value |  |
| 146 | Enhanced Fleet Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 151 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Segme nt Type | 100 | qe_example_value |  |
| 153 | Networ k Token Data | ‹masked, 7 chars› | placeholder |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3620](../traceability/segment-DL3.md) → SC-8994 → TC-013211 → `TC-013211.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-017×1, QEF-018×1, QEF-019×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-024×1, QEF-026×1, QEF-027×1, QEF-028×1, QEF-030×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-013212 — Field_Constraint — Financial Transactions — In Date and Time Data Segment (Data Segment No. DL3), field 6 (Passwor

**Purpose:** In Date and Time Data Segment (Data Segment No. DL3), field 6 (Password) is required and must be present. (source [p.328](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=328))

**Derivation path:** Spec [p.328](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=328) (ATL105 2026-3) → ENT-FIELD-DL3-6 → REQ-SRC-ATL105-PDF-001:3620 → derivation_method = field_constraint → SC-8994 (field_constraint) → TC-013212 (transaction = Financial Transactions)

**Preconditions:** transaction type Financial Transactions; message layout step 1 Financial Transactions segments 101, 104, 108, 109, 110

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 101 | Segment Type | 101 | fixed_value |  |
| 104 | Segment Type | 104 | fixed_value |  |
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 864944 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 779883 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 74381652 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |
| 110 | Segment Type | 110 | fixed_value |  |
| 110 | MICR Data | ‹masked, 50 chars› | format_synthesis |  |
| 110 | Check Type | P | qe_example_value |  |
| 110 | Customer Phone Number | ‹masked, 10 chars› | format_synthesis |  |
| 110 | Customer Last Name | ‹masked, 24 chars› | format_synthesis |  |
| 110 | Check Issue Date | 06152025 | qe_example_value |  |
| 110 | Alternate MICR IND | ‹masked, 7 chars› | placeholder |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier FLAG); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3620](../traceability/segment-DL3.md) → SC-8994 → TC-013212 → `TC-013212.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-013213 — Field_Constraint — Mail Request — In Date and Time Data Segment (Data Segment No. DL3), field 6 (Passwor

**Purpose:** In Date and Time Data Segment (Data Segment No. DL3), field 6 (Password) is required and must be present. (source [p.328](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=328))

**Derivation path:** Spec [p.328](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=328) (ATL105 2026-3) → ENT-FIELD-DL3-6 → REQ-SRC-ATL105-PDF-001:3620 → derivation_method = field_constraint → SC-8994 (field_constraint) → TC-013213 (transaction = Mail Request)

**Preconditions:** transaction type Mail Request; message layout step 1 Mail Request segments 109

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 109 | Segment Type | 109 | fixed_value |  |
| 109 | Information Byte | 0 | qe_example_value |  |
| 109 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 109 | Prompt Code | 0 | qe_example_value | ⚠ AMBIGUOUS_LENGTH |
| 109 | Block Number | 000 | qe_example_value |  |
| 109 | Sequence Number | 000001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3620](../traceability/segment-DL3.md) → SC-8994 → TC-013213 → `TC-013213.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020916 — Business_Rule — Financial Transaction — Transaction date prints on all credit card receipts.

**Purpose:** Transaction date prints on all credit card receipts. (source [p.90](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=90))

**Derivation path:** Spec [p.90](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=90) (ATL105 2026-3) → BR-90-5 → REQ-SRC-ATL105-PDF-001:559 → derivation_method = business_rule_derived → SC-5933 (business_rule) → TC-020916 (transaction = Financial Transaction)

**Preconditions:** transaction type Financial Transaction; message layout step 1 Financial Transaction segments 102, 103, 136

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 136 | Segment Type | 100 | qe_example_value |  |
| 136 | Moneris Data | 001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier FLAG); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:559](../traceability/segment-DL3.md) → SC-5933 → TC-020916 → `TC-020916.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020917 — Business_Rule — Financial Transaction — Transaction time prints on all credit card receipts.

**Purpose:** Transaction time prints on all credit card receipts. (source [p.90](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=90))

**Derivation path:** Spec [p.90](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=90) (ATL105 2026-3) → BR-90-6 → REQ-SRC-ATL105-PDF-001:560 → derivation_method = business_rule_derived → SC-5934 (business_rule) → TC-020917 (transaction = Financial Transaction)

**Preconditions:** transaction type Financial Transaction; message layout step 1 Financial Transaction segments 102, 103, 136

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 102 | Segment Type | 102 | fixed_value |  |
| 102 | Service Level | F | qe_example_value |  |
| 102 | Number of Products | 01 | qe_example_value |  |
| 102 | Product Code | 001 | qe_example_value |  |
| 102 | Unit of Measure | C | qe_example_value |  |
| 102 | Quantity | 01 | qe_example_value |  |
| 102 | Unit Price | 000 | qe_example_value |  |
| 102 | Product Amount | 1 | qe_example_value |  |
| 103 | Segment Type | 103 | fixed_value |  |
| 136 | Segment Type | 100 | qe_example_value |  |
| 136 | Moneris Data | 001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier FLAG); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:560](../traceability/segment-DL3.md) → SC-5934 → TC-020917 → `TC-020917.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?
