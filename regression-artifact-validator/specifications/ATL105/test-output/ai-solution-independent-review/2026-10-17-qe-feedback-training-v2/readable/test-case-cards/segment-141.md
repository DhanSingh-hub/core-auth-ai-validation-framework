# Segment 141 — Test Case Cards

One card per test case (QE feedback Def_SepRun07/Def_SepRun10). Derived from the canonical JSON on every run; JSON stays authoritative. Fields flagged UNTRUSTED_VALID_VALUES, AMBIGUOUS_LENGTH or DELIBERATE_CONSTRAINT_VIOLATION are marked ⚠.

## TC-012900 — Field_Constraint — ECA/TeleCheck Service Transaction — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 1 (Segment Type) is required and must be present. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-1 → REQ-SRC-ATL105-PDF-001:3508 → derivation_method = field_constraint → SC-8882 (field_constraint) → TC-012900 (transaction = ECA/TeleCheck Service Transaction)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction; message layout step 1 ECA/TeleCheck Service Transaction segments 100, 110, 111, 113

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

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3508](../traceability/segment-141.md) → SC-8882 → TC-012900 → `TC-012900.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012901 — Field_Constraint — ECA/TeleCheck Service Transaction Request — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 1 (Segment Type) is required and must be present. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-1 → REQ-SRC-ATL105-PDF-001:3508 → derivation_method = field_constraint → SC-8882 (field_constraint) → TC-012901 (transaction = ECA/TeleCheck Service Transaction Request)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction Request; message layout step 1 ECA/TeleCheck Service Transaction Request segments 100, 110, 111, 113

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

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3508](../traceability/segment-141.md) → SC-8882 → TC-012901 → `TC-012901.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012902 — Field_Constraint — EMV Financial Transaction Request — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 1 (Segment Type) is required and must be present. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-1 → REQ-SRC-ATL105-PDF-001:3508 → derivation_method = field_constraint → SC-8882 (field_constraint) → TC-012902 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 130, 135, 143, 145, 151, 153, 156

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
| 108 | Loyalty Program ID | 813119 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 203071 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 90649000 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 130 | Segment Type | 130 | fixed_value |  |
| 130 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 130 | EMV Chip Data Length | 000 | qe_example_value |  |
| 130 | EMV Chip Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3508](../traceability/segment-141.md) → SC-8882 → TC-012902 → `TC-012902.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012903 — Field_Constraint — EMV Request Transaction — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 1 (Segment Type) is required and must be present. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-1 → REQ-SRC-ATL105-PDF-001:3508 → derivation_method = field_constraint → SC-8882 (field_constraint) → TC-012903 (transaction = EMV Request Transaction)

**Preconditions:** transaction type EMV Request Transaction; message layout step 1 EMV Request Transaction segments 100, 108, 111, 114, 156

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
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 652405 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 378587 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 27215135 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3508](../traceability/segment-141.md) → SC-8882 → TC-012903 → `TC-012903.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012904 — Field_Constraint — Electronic Mail — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 1 (Segment Type) is required and must be present. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-1 → REQ-SRC-ATL105-PDF-001:3508 → derivation_method = field_constraint → SC-8882 (field_constraint) → TC-012904 (transaction = Electronic Mail)

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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3508](../traceability/segment-141.md) → SC-8882 → TC-012904 → `TC-012904.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012905 — Field_Fixed_Value — ECA/TeleCheck Service Transaction — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 1 (Segment Type) must equal the fixed value 141. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-1 → REQ-SRC-ATL105-PDF-001:3509 → derivation_method = field_fixed_value → SC-8883 (field_fixed_value) → TC-012905 (transaction = ECA/TeleCheck Service Transaction)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction; message layout step 1 ECA/TeleCheck Service Transaction segments 100, 110, 111, 113

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

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3509](../traceability/segment-141.md) → SC-8883 → TC-012905 → `TC-012905.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012906 — Field_Fixed_Value — ECA/TeleCheck Service Transaction Request — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 1 (Segment Type) must equal the fixed value 141. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-1 → REQ-SRC-ATL105-PDF-001:3509 → derivation_method = field_fixed_value → SC-8883 (field_fixed_value) → TC-012906 (transaction = ECA/TeleCheck Service Transaction Request)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction Request; message layout step 1 ECA/TeleCheck Service Transaction Request segments 100, 110, 111, 113

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

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3509](../traceability/segment-141.md) → SC-8883 → TC-012906 → `TC-012906.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012907 — Field_Fixed_Value — EMV Financial Transaction Request — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 1 (Segment Type) must equal the fixed value 141. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-1 → REQ-SRC-ATL105-PDF-001:3509 → derivation_method = field_fixed_value → SC-8883 (field_fixed_value) → TC-012907 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 130, 135, 143, 145, 151, 153, 156

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
| 108 | Loyalty Program ID | 811034 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 907276 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 46539544 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 130 | Segment Type | 130 | fixed_value |  |
| 130 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 130 | EMV Chip Data Length | 000 | qe_example_value |  |
| 130 | EMV Chip Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3509](../traceability/segment-141.md) → SC-8883 → TC-012907 → `TC-012907.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012908 — Field_Fixed_Value — EMV Request Transaction — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 1 (Segment Type) must equal the fixed value 141. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-1 → REQ-SRC-ATL105-PDF-001:3509 → derivation_method = field_fixed_value → SC-8883 (field_fixed_value) → TC-012908 (transaction = EMV Request Transaction)

**Preconditions:** transaction type EMV Request Transaction; message layout step 1 EMV Request Transaction segments 100, 108, 111, 114, 156

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
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 446750 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 116194 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 57241017 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3509](../traceability/segment-141.md) → SC-8883 → TC-012908 → `TC-012908.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012909 — Field_Fixed_Value — Electronic Mail — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 1 (Segment Type) must equal the fixed value 141. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-1 → REQ-SRC-ATL105-PDF-001:3509 → derivation_method = field_fixed_value → SC-8883 (field_fixed_value) → TC-012909 (transaction = Electronic Mail)

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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3509](../traceability/segment-141.md) → SC-8883 → TC-012909 → `TC-012909.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012910 — Field_Constraint — ECA/TeleCheck Service Transaction — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 2 (Segment Length) is required and must be present. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-2 → REQ-SRC-ATL105-PDF-001:3510 → derivation_method = field_constraint → SC-8884 (field_constraint) → TC-012910 (transaction = ECA/TeleCheck Service Transaction)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction; message layout step 1 ECA/TeleCheck Service Transaction segments 100, 110, 111, 113

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

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3510](../traceability/segment-141.md) → SC-8884 → TC-012910 → `TC-012910.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012911 — Field_Constraint — ECA/TeleCheck Service Transaction Request — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 2 (Segment Length) is required and must be present. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-2 → REQ-SRC-ATL105-PDF-001:3510 → derivation_method = field_constraint → SC-8884 (field_constraint) → TC-012911 (transaction = ECA/TeleCheck Service Transaction Request)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction Request; message layout step 1 ECA/TeleCheck Service Transaction Request segments 100, 110, 111, 113

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

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3510](../traceability/segment-141.md) → SC-8884 → TC-012911 → `TC-012911.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012912 — Field_Constraint — EMV Financial Transaction Request — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 2 (Segment Length) is required and must be present. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-2 → REQ-SRC-ATL105-PDF-001:3510 → derivation_method = field_constraint → SC-8884 (field_constraint) → TC-012912 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 130, 135, 143, 145, 151, 153, 156

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
| 108 | Loyalty Program ID | 648294 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 967147 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 36814814 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 130 | Segment Type | 130 | fixed_value |  |
| 130 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 130 | EMV Chip Data Length | 000 | qe_example_value |  |
| 130 | EMV Chip Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3510](../traceability/segment-141.md) → SC-8884 → TC-012912 → `TC-012912.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012913 — Field_Constraint — EMV Request Transaction — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 2 (Segment Length) is required and must be present. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-2 → REQ-SRC-ATL105-PDF-001:3510 → derivation_method = field_constraint → SC-8884 (field_constraint) → TC-012913 (transaction = EMV Request Transaction)

**Preconditions:** transaction type EMV Request Transaction; message layout step 1 EMV Request Transaction segments 100, 108, 111, 114, 156

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
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 946068 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 148471 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 78355733 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3510](../traceability/segment-141.md) → SC-8884 → TC-012913 → `TC-012913.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012914 — Field_Constraint — Electronic Mail — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 2 (Segment Length) is required and must be present. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-2 → REQ-SRC-ATL105-PDF-001:3510 → derivation_method = field_constraint → SC-8884 (field_constraint) → TC-012914 (transaction = Electronic Mail)

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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3510](../traceability/segment-141.md) → SC-8884 → TC-012914 → `TC-012914.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012915 — Field_Constraint — ECA/TeleCheck Service Transaction — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 3 (Terminal Identifier) is required and must be present. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-3 → REQ-SRC-ATL105-PDF-001:3511 → derivation_method = field_constraint → SC-8885 (field_constraint) → TC-012915 (transaction = ECA/TeleCheck Service Transaction)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction; message layout step 1 ECA/TeleCheck Service Transaction segments 100, 110, 111, 113

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

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3511](../traceability/segment-141.md) → SC-8885 → TC-012915 → `TC-012915.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012916 — Field_Constraint — ECA/TeleCheck Service Transaction Request — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 3 (Terminal Identifier) is required and must be present. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-3 → REQ-SRC-ATL105-PDF-001:3511 → derivation_method = field_constraint → SC-8885 (field_constraint) → TC-012916 (transaction = ECA/TeleCheck Service Transaction Request)

**Preconditions:** transaction type ECA/TeleCheck Service Transaction Request; message layout step 1 ECA/TeleCheck Service Transaction Request segments 100, 110, 111, 113

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

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3511](../traceability/segment-141.md) → SC-8885 → TC-012916 → `TC-012916.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012917 — Field_Constraint — EMV Financial Transaction Request — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 3 (Terminal Identifier) is required and must be present. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-3 → REQ-SRC-ATL105-PDF-001:3511 → derivation_method = field_constraint → SC-8885 (field_constraint) → TC-012917 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 130, 135, 143, 145, 151, 153, 156

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
| 108 | Loyalty Program ID | 116994 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 659889 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 89126052 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 123 | Segment Type | 123 | fixed_value |  |
| 123 | CAVV, Revised Format | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Token Requesto r ID | ‹masked, 6 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | Cryptogr am Token Data | ‹masked, 1 chars› | qe_example_value |  |
| 123 | SafeKey Data | ‹masked, 58 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 123 | SafeKey Respons e | ‹masked, 1 chars› | qe_example_value |  |
| 123 | TAVV Cryptogr am | ‹masked, 28 chars› | format_synthesis |  |
| 123 | TAVV Result Code | ‹masked, 1 chars› | qe_example_value |  |
| 130 | Segment Type | 130 | fixed_value |  |
| 130 | CA Public Key File Checksum | ‹masked, 25 chars› | format_synthesis |  |
| 130 | EMV Chip Data Length | 000 | qe_example_value |  |
| 130 | EMV Chip Data | ‹masked, 999 chars› | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3511](../traceability/segment-141.md) → SC-8885 → TC-012917 → `TC-012917.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012918 — Field_Constraint — EMV Request Transaction — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 3 (Terminal Identifier) is required and must be present. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-3 → REQ-SRC-ATL105-PDF-001:3511 → derivation_method = field_constraint → SC-8885 (field_constraint) → TC-012918 (transaction = EMV Request Transaction)

**Preconditions:** transaction type EMV Request Transaction; message layout step 1 EMV Request Transaction segments 100, 108, 111, 114, 156

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
| 108 | Segment Type | 108 | fixed_value |  |
| 108 | Loyalty Program ID | 584238 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 521485 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 43519269 | realistic_amount |  |
| 108 | Payment Tender Type | VS | qe_example_value |  |
| 108 | Loyalty Track 2 Data | ‹masked, 38 chars› | realistic_track2_data |  |
| 108 | Loyalty Information Version | 1 | qe_example_value |  |
| 108 | Unit of Work | ‹masked, 19 chars› | format_synthesis |  |
| 111 | Segment Type | 111 | fixed_value |  |
| 111 | Variable Information Indicator | 005 | fixed_value |  |
| 111 | Variable Information Length | 003 | derived_from_table_data |  |
| 111 | Variable Information | 000 | spec_unspecified_code |  |
| 111 | Variable Information Indicator [2] | 031 | fixed_value |  |
| 111 | Variable Information Length [2] | 002 | derived_from_table_data |  |
| 111 | Variable Information [2] | 00 | spec_unspecified_code |  |
| 114 | Segment Type | 100 | qe_example_value |  |
| 114 | SKU Data | ‹masked, 1000 chars› | format_synthesis |  |
| 156 | Segment Type | 156 | fixed_value |  |
| 156 | EV Charging Data | 01 | qe_example_value |  |

**Expected outcome:** Response code 0 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3511](../traceability/segment-141.md) → SC-8885 → TC-012918 → `TC-012918.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012919 — Field_Constraint — Electronic Mail — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 3 (Terminal Identifier) is required and must be present. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-3 → REQ-SRC-ATL105-PDF-001:3511 → derivation_method = field_constraint → SC-8885 (field_constraint) → TC-012919 (transaction = Electronic Mail)

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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3511](../traceability/segment-141.md) → SC-8885 → TC-012919 → `TC-012919.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012920 — Field_Constraint — Electronic Mail Request — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 3 (Terminal Identifier) is required and must be present. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-3 → REQ-SRC-ATL105-PDF-001:3511 → derivation_method = field_constraint → SC-8885 (field_constraint) → TC-012920 (transaction = Electronic Mail Request)

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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3511](../traceability/segment-141.md) → SC-8885 → TC-012920 → `TC-012920.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020976 — Business_Rule — Key Load — No field separator exists between fields in the Moneris Key Load messa

**Purpose:** No field separator exists between fields in the Moneris Key Load message. (source [p.199](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=199))

**Derivation path:** Spec [p.199](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=199) (ATL105 2026-3) → BR-199-1 → REQ-SRC-ATL105-PDF-001:1114 → derivation_method = business_rule_derived → SC-6488 (business_rule) → TC-020976 (transaction = Key Load)

**Preconditions:** transaction type Key Load; message layout step 1 Key Load segments 136

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 136 | Segment Type | 100 | qe_example_value |  |
| 136 | Moneris Data | 001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier FLAG); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1114](../traceability/segment-141.md) → SC-6488 → TC-020976 → `TC-020976.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020977 — Business_Rule — Key Load — No load flag needs to be set on the terminal record for a Moneris Key 

**Purpose:** No load flag needs to be set on the terminal record for a Moneris Key Load; it can be performed at any time. (source [p.199](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=199))

**Derivation path:** Spec [p.199](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=199) (ATL105 2026-3) → BR-199-2 → REQ-SRC-ATL105-PDF-001:1115 → derivation_method = business_rule_derived → SC-6489 (business_rule) → TC-020977 (transaction = Key Load)

**Preconditions:** transaction type Key Load; message layout step 1 Key Load segments 136

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 136 | Segment Type | 100 | qe_example_value |  |
| 136 | Moneris Data | 001 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier FLAG); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1115](../traceability/segment-141.md) → SC-6489 → TC-020977 → `TC-020977.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020983 — Business_Rule — Totals Request — Segment 141 should be sent once for each pay point at day end to close

**Purpose:** Segment 141 should be sent once for each pay point at day end to close out to Moneris. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → BR-279-2 → REQ-SRC-ATL105-PDF-001:1375 → derivation_method = business_rule_derived → SC-6749 (business_rule) → TC-020983 (transaction = Totals Request)

**Preconditions:** transaction type Totals Request; message layout step 1 Totals Request segments 105

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 105 | Segment Type | 105 | fixed_value |  |
| 105 | Information Byte | 0 | qe_example_value |  |
| 105 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 105 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 105 | Totals Date | 111111 | qe_example_value |  |
| 105 | Hardware Version | XJ0TKI3L | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 105 | Software Version | K6B15NA5 | format_synthesis |  |
| 105 | Firmware Version | JR360OPQ | format_synthesis |  |
| 105 | Sequence Number | 000001 | qe_example_value |  |
| 105 | Currency Code | 840 | qe_example_value |  |
| 105 | Grand Total | 00000000 | qe_example_value |  |
| 105 | Card Label | CC | qe_example_value |  |
| 105 | Card Type Total Count | 00001 | qe_example_value |  |
| 105 | Card Type Total Amount | 00000000 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1375](../traceability/segment-141.md) → SC-6749 → TC-020983 → `TC-020983.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021125 — Field_Constraint — Totals Request — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 4 (SPDH Header) is required and must be present. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-4 → REQ-SRC-ATL105-PDF-001:3512 → derivation_method = field_constraint → SC-8886 (field_constraint) → TC-021125 (transaction = Totals Request)

**Preconditions:** transaction type Totals Request; message layout step 1 Totals Request segments 105

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 105 | Segment Type | 105 | fixed_value |  |
| 105 | Information Byte | 0 | qe_example_value |  |
| 105 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 105 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 105 | Totals Date | 111111 | qe_example_value |  |
| 105 | Hardware Version | 01B27ASP | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 105 | Software Version | 1PN0VT61 | format_synthesis |  |
| 105 | Firmware Version | WVO4DL85 | format_synthesis |  |
| 105 | Sequence Number | 000001 | qe_example_value |  |
| 105 | Currency Code | 840 | qe_example_value |  |
| 105 | Grand Total | 00000000 | qe_example_value |  |
| 105 | Card Label | CC | qe_example_value |  |
| 105 | Card Type Total Count | 00001 | qe_example_value |  |
| 105 | Card Type Total Amount | 00000000 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3512](../traceability/segment-141.md) → SC-8886 → TC-021125 → `TC-021125.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021126 — Field_Constraint — Totals Request — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 5 (Moneris Terminal Identifier) is required and must be present. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-5 → REQ-SRC-ATL105-PDF-001:3513 → derivation_method = field_constraint → SC-8887 (field_constraint) → TC-021126 (transaction = Totals Request)

**Preconditions:** transaction type Totals Request; message layout step 1 Totals Request segments 105

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 105 | Segment Type | 105 | fixed_value |  |
| 105 | Information Byte | 0 | qe_example_value |  |
| 105 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 105 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 105 | Totals Date | 111111 | qe_example_value |  |
| 105 | Hardware Version | 8EXSBNBG | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 105 | Software Version | VHCK2XU0 | format_synthesis |  |
| 105 | Firmware Version | 081WM6V6 | format_synthesis |  |
| 105 | Sequence Number | 000001 | qe_example_value |  |
| 105 | Currency Code | 840 | qe_example_value |  |
| 105 | Grand Total | 00000000 | qe_example_value |  |
| 105 | Card Label | CC | qe_example_value |  |
| 105 | Card Type Total Count | 00001 | qe_example_value |  |
| 105 | Card Type Total Amount | 00000000 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3513](../traceability/segment-141.md) → SC-8887 → TC-021126 → `TC-021126.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021127 — Field_Constraint — Totals Request — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 6 (Moneris Merchant ID) is required and must be present. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-6 → REQ-SRC-ATL105-PDF-001:3514 → derivation_method = field_constraint → SC-8888 (field_constraint) → TC-021127 (transaction = Totals Request)

**Preconditions:** transaction type Totals Request; message layout step 1 Totals Request segments 105

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 105 | Segment Type | 105 | fixed_value |  |
| 105 | Information Byte | 0 | qe_example_value |  |
| 105 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 105 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 105 | Totals Date | 111111 | qe_example_value |  |
| 105 | Hardware Version | K1YD1BHQ | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 105 | Software Version | 6D333G87 | format_synthesis |  |
| 105 | Firmware Version | WLXLAO6S | format_synthesis |  |
| 105 | Sequence Number | 000001 | qe_example_value |  |
| 105 | Currency Code | 840 | qe_example_value |  |
| 105 | Grand Total | 00000000 | qe_example_value |  |
| 105 | Card Label | CC | qe_example_value |  |
| 105 | Card Type Total Count | 00001 | qe_example_value |  |
| 105 | Card Type Total Amount | 00000000 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3514](../traceability/segment-141.md) → SC-8888 → TC-021127 → `TC-021127.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021128 — Field_Constraint — Totals Request — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 7 (Batch Number) is required and must be present. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-7 → REQ-SRC-ATL105-PDF-001:3515 → derivation_method = field_constraint → SC-8889 (field_constraint) → TC-021128 (transaction = Totals Request)

**Preconditions:** transaction type Totals Request; message layout step 1 Totals Request segments 105

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 105 | Segment Type | 105 | fixed_value |  |
| 105 | Information Byte | 0 | qe_example_value |  |
| 105 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 105 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 105 | Totals Date | 111111 | qe_example_value |  |
| 105 | Hardware Version | NRBCTUAZ | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 105 | Software Version | EAMWY7D2 | format_synthesis |  |
| 105 | Firmware Version | CQESV8ZV | format_synthesis |  |
| 105 | Sequence Number | 000001 | qe_example_value |  |
| 105 | Currency Code | 840 | qe_example_value |  |
| 105 | Grand Total | 00000000 | qe_example_value |  |
| 105 | Card Label | CC | qe_example_value |  |
| 105 | Card Type Total Count | 00001 | qe_example_value |  |
| 105 | Card Type Total Amount | 00000000 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3515](../traceability/segment-141.md) → SC-8889 → TC-021128 → `TC-021128.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021129 — Field_Constraint — Totals Request — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 8 (Language Indicator) is required and must be present. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-8 → REQ-SRC-ATL105-PDF-001:3516 → derivation_method = field_constraint → SC-8890 (field_constraint) → TC-021129 (transaction = Totals Request)

**Preconditions:** transaction type Totals Request; message layout step 1 Totals Request segments 105

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 105 | Segment Type | 105 | fixed_value |  |
| 105 | Information Byte | 0 | qe_example_value |  |
| 105 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 105 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 105 | Totals Date | 111111 | qe_example_value |  |
| 105 | Hardware Version | PYQNOGD2 | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 105 | Software Version | WNHHC5BS | format_synthesis |  |
| 105 | Firmware Version | D4G2Z5S2 | format_synthesis |  |
| 105 | Sequence Number | 000001 | qe_example_value |  |
| 105 | Currency Code | 840 | qe_example_value |  |
| 105 | Grand Total | 00000000 | qe_example_value |  |
| 105 | Card Label | CC | qe_example_value |  |
| 105 | Card Type Total Count | 00001 | qe_example_value |  |
| 105 | Card Type Total Amount | 00000000 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3516](../traceability/segment-141.md) → SC-8890 → TC-021129 → `TC-021129.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021130 — Field_Constraint — Totals Request — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 9 (Number of Debits) is required and must be present. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-9 → REQ-SRC-ATL105-PDF-001:3517 → derivation_method = field_constraint → SC-8891 (field_constraint) → TC-021130 (transaction = Totals Request)

**Preconditions:** transaction type Totals Request; message layout step 1 Totals Request segments 105

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 105 | Segment Type | 105 | fixed_value |  |
| 105 | Information Byte | 0 | qe_example_value |  |
| 105 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 105 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 105 | Totals Date | 111111 | qe_example_value |  |
| 105 | Hardware Version | PRW9JLOC | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 105 | Software Version | ABF6XEVO | format_synthesis |  |
| 105 | Firmware Version | TK7PB0BC | format_synthesis |  |
| 105 | Sequence Number | 000001 | qe_example_value |  |
| 105 | Currency Code | 840 | qe_example_value |  |
| 105 | Grand Total | 00000000 | qe_example_value |  |
| 105 | Card Label | CC | qe_example_value |  |
| 105 | Card Type Total Count | 00001 | qe_example_value |  |
| 105 | Card Type Total Amount | 00000000 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3517](../traceability/segment-141.md) → SC-8891 → TC-021130 → `TC-021130.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021131 — Field_Constraint — Totals Request — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 10 (Debit Dollar Value) is required and must be present. (source [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279))

**Derivation path:** Spec [p.279](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=279) (ATL105 2026-3) → ENT-FIELD-141-10 → REQ-SRC-ATL105-PDF-001:3518 → derivation_method = field_constraint → SC-8892 (field_constraint) → TC-021131 (transaction = Totals Request)

**Preconditions:** transaction type Totals Request; message layout step 1 Totals Request segments 105

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 105 | Segment Type | 105 | fixed_value |  |
| 105 | Information Byte | 0 | qe_example_value |  |
| 105 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 105 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 105 | Totals Date | 111111 | qe_example_value |  |
| 105 | Hardware Version | 1QALYKJD | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 105 | Software Version | CE42XBVC | format_synthesis |  |
| 105 | Firmware Version | G1N7VLPD | format_synthesis |  |
| 105 | Sequence Number | 000001 | qe_example_value |  |
| 105 | Currency Code | 840 | qe_example_value |  |
| 105 | Grand Total | 00000000 | qe_example_value |  |
| 105 | Card Label | CC | qe_example_value |  |
| 105 | Card Type Total Count | 00001 | qe_example_value |  |
| 105 | Card Type Total Amount | 00000000 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3518](../traceability/segment-141.md) → SC-8892 → TC-021131 → `TC-021131.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021132 — Field_Constraint — Totals Request — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 11 (Number of Credits) is required and must be present. (source [p.280](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=280))

**Derivation path:** Spec [p.280](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=280) (ATL105 2026-3) → ENT-FIELD-141-11 → REQ-SRC-ATL105-PDF-001:3519 → derivation_method = field_constraint → SC-8893 (field_constraint) → TC-021132 (transaction = Totals Request)

**Preconditions:** transaction type Totals Request; message layout step 1 Totals Request segments 105

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 105 | Segment Type | 105 | fixed_value |  |
| 105 | Information Byte | 0 | qe_example_value |  |
| 105 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 105 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 105 | Totals Date | 111111 | qe_example_value |  |
| 105 | Hardware Version | CA3JR9L1 | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 105 | Software Version | IY67AJ1T | format_synthesis |  |
| 105 | Firmware Version | JDFODBJE | format_synthesis |  |
| 105 | Sequence Number | 000001 | qe_example_value |  |
| 105 | Currency Code | 840 | qe_example_value |  |
| 105 | Grand Total | 00000000 | qe_example_value |  |
| 105 | Card Label | CC | qe_example_value |  |
| 105 | Card Type Total Count | 00001 | qe_example_value |  |
| 105 | Card Type Total Amount | 00000000 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3519](../traceability/segment-141.md) → SC-8893 → TC-021132 → `TC-021132.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021133 — Field_Constraint — Totals Request — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 12 (Credit Dollar Value) is required and must be present. (source [p.280](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=280))

**Derivation path:** Spec [p.280](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=280) (ATL105 2026-3) → ENT-FIELD-141-12 → REQ-SRC-ATL105-PDF-001:3520 → derivation_method = field_constraint → SC-8894 (field_constraint) → TC-021133 (transaction = Totals Request)

**Preconditions:** transaction type Totals Request; message layout step 1 Totals Request segments 105

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 105 | Segment Type | 105 | fixed_value |  |
| 105 | Information Byte | 0 | qe_example_value |  |
| 105 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 105 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 105 | Totals Date | 111111 | qe_example_value |  |
| 105 | Hardware Version | SLJ6AR5H | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 105 | Software Version | 398S4O2K | format_synthesis |  |
| 105 | Firmware Version | 687CNAQT | format_synthesis |  |
| 105 | Sequence Number | 000001 | qe_example_value |  |
| 105 | Currency Code | 840 | qe_example_value |  |
| 105 | Grand Total | 00000000 | qe_example_value |  |
| 105 | Card Label | CC | qe_example_value |  |
| 105 | Card Type Total Count | 00001 | qe_example_value |  |
| 105 | Card Type Total Amount | 00000000 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3520](../traceability/segment-141.md) → SC-8894 → TC-021133 → `TC-021133.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021134 — Field_Constraint — Totals Request — In Moneris Day End Bath Close Segment Format (Request) (Data Segment N

**Purpose:** In Moneris Day End Bath Close Segment Format (Request) (Data Segment No. 141), field 13 (Number of Corrections) is required and must be present. (source [p.280](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=280))

**Derivation path:** Spec [p.280](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=280) (ATL105 2026-3) → ENT-FIELD-141-13 → REQ-SRC-ATL105-PDF-001:3521 → derivation_method = field_constraint → SC-8895 (field_constraint) → TC-021134 (transaction = Totals Request)

**Preconditions:** transaction type Totals Request; message layout step 1 Totals Request segments 105

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 105 | Segment Type | 105 | fixed_value |  |
| 105 | Information Byte | 0 | qe_example_value |  |
| 105 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 105 | Prompt Code | 990 | fixed_value | ⚠ AMBIGUOUS_LENGTH |
| 105 | Totals Date | 111111 | qe_example_value |  |
| 105 | Hardware Version | RBG4I49E | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 105 | Software Version | ZCCPO9AE | format_synthesis |  |
| 105 | Firmware Version | IUW601UK | format_synthesis |  |
| 105 | Sequence Number | 000001 | qe_example_value |  |
| 105 | Currency Code | 840 | qe_example_value |  |
| 105 | Grand Total | 00000000 | qe_example_value |  |
| 105 | Card Label | CC | qe_example_value |  |
| 105 | Card Type Total Count | 00001 | qe_example_value |  |
| 105 | Card Type Total Amount | 00000000 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:3521](../traceability/segment-141.md) → SC-8895 → TC-021134 → `TC-021134.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?
