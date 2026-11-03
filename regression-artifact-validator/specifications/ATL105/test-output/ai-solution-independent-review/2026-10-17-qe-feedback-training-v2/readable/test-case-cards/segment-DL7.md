# Segment DL7 — Test Case Cards

One card per test case (QE feedback Def_SepRun07/Def_SepRun10). Derived from the canonical JSON on every run; JSON stays authoritative. Fields flagged UNTRUSTED_VALID_VALUES, AMBIGUOUS_LENGTH or DELIBERATE_CONSTRAINT_VIOLATION are marked ⚠.

## TC-013214 — Field_Constraint — ECA/TeleCheck Service Transaction — In Supplemental Terminal Data Segment (Data Segment No. DL7), field 2 

**Purpose:** In Supplemental Terminal Data Segment (Data Segment No. DL7), field 2 (Segment Length Indicator) is required and must be present. (source [p.332](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=332))

**Derivation path:** Spec [p.332](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=332) (ATL105 2026-3) → ENT-FIELD-DL7-2 → REQ-SRC-ATL105-PDF-001:3644 → derivation_method = field_constraint → SC-9018 (field_constraint) → TC-013214 (transaction = ECA/TeleCheck Service Transaction)

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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3644](../traceability/segment-DL7.md) → SC-9018 → TC-013214 → `TC-013214.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-013215 — Field_Constraint — ECA/TeleCheck Service Transaction Request — In Supplemental Terminal Data Segment (Data Segment No. DL7), field 2 

**Purpose:** In Supplemental Terminal Data Segment (Data Segment No. DL7), field 2 (Segment Length Indicator) is required and must be present. (source [p.332](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=332))

**Derivation path:** Spec [p.332](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=332) (ATL105 2026-3) → ENT-FIELD-DL7-2 → REQ-SRC-ATL105-PDF-001:3644 → derivation_method = field_constraint → SC-9018 (field_constraint) → TC-013215 (transaction = ECA/TeleCheck Service Transaction Request)

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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3644](../traceability/segment-DL7.md) → SC-9018 → TC-013215 → `TC-013215.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-013216 — Field_Constraint — EMV Financial Transaction Request — In Supplemental Terminal Data Segment (Data Segment No. DL7), field 2 

**Purpose:** In Supplemental Terminal Data Segment (Data Segment No. DL7), field 2 (Segment Length Indicator) is required and must be present. (source [p.332](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=332))

**Derivation path:** Spec [p.332](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=332) (ATL105 2026-3) → ENT-FIELD-DL7-2 → REQ-SRC-ATL105-PDF-001:3644 → derivation_method = field_constraint → SC-9018 (field_constraint) → TC-013216 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 793572 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 317681 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 27854127 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3644](../traceability/segment-DL7.md) → SC-9018 → TC-013216 → `TC-013216.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-013217 — Field_Constraint — EMV Request Transaction — In Supplemental Terminal Data Segment (Data Segment No. DL7), field 2 

**Purpose:** In Supplemental Terminal Data Segment (Data Segment No. DL7), field 2 (Segment Length Indicator) is required and must be present. (source [p.332](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=332))

**Derivation path:** Spec [p.332](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=332) (ATL105 2026-3) → ENT-FIELD-DL7-2 → REQ-SRC-ATL105-PDF-001:3644 → derivation_method = field_constraint → SC-9018 (field_constraint) → TC-013217 (transaction = EMV Request Transaction)

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
| 108 | Loyalty Program ID | 356009 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 820555 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 91254070 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3644](../traceability/segment-DL7.md) → SC-9018 → TC-013217 → `TC-013217.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-013218 — Field_Constraint — Electronic Mail — In Supplemental Terminal Data Segment (Data Segment No. DL7), field 2 

**Purpose:** In Supplemental Terminal Data Segment (Data Segment No. DL7), field 2 (Segment Length Indicator) is required and must be present. (source [p.332](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=332))

**Derivation path:** Spec [p.332](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=332) (ATL105 2026-3) → ENT-FIELD-DL7-2 → REQ-SRC-ATL105-PDF-001:3644 → derivation_method = field_constraint → SC-9018 (field_constraint) → TC-013218 (transaction = Electronic Mail)

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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3644](../traceability/segment-DL7.md) → SC-9018 → TC-013218 → `TC-013218.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021181 — Supplemental_Entity — Proprietary Data Load Request — Supplemental Terminal Data Segment: Data Segment No. DL7, identified b

**Purpose:** Supplemental Terminal Data Segment: Data Segment No. DL7, identified by special character '^', holds download data. (source [p.332](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=332))

**Derivation path:** Spec [p.332](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=332) (ATL105 2026-3) → ENT-SEG-DL7 → REQ-SRC-ATL105-PDF-001:6015 → derivation_method = supplemental_entity_derived → SC-11389 (supplemental_entity) → TC-021181 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 171457 | format_synthesis |  |
| 118 | Card Table Load Version | 862235 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:6015](../traceability/segment-DL7.md) → SC-11389 → TC-021181 → `TC-021181.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021182 — Supplemental_Entity — Proprietary Data Load Request — Download Data: All or some of the Download Data in <tag><len><data> fo

**Purpose:** Download Data: All or some of the Download Data in <tag><len><data> format. (source [p.332](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=332))

**Derivation path:** Spec [p.332](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=332) (ATL105 2026-3) → ENT-ELEM-232 → REQ-SRC-ATL105-PDF-001:6016 → derivation_method = supplemental_entity_derived → SC-11390 (supplemental_entity) → TC-021182 (transaction = Proprietary Data Load Request)

**Preconditions:** transaction type Proprietary Data Load Request; message layout step 1 Proprietary Data Load Request segments 118

**Input:**

| Segment | Field | Value | Method | Flags |
| --- | --- | --- | --- | --- |
| 118 | Segment Type | 118 | fixed_value |  |
| 118 | Sequence Number | 000001 | qe_example_value |  |
| 118 | Information Byte | 0 | qe_example_value |  |
| 118 | Terminal Identifier | ‹masked, 22 chars› | realistic_terminal_id | ⚠ UNTRUSTED_VALID_VALUES |
| 118 | Prompt Code | 900 | composite_from_spec_parts | ⚠ AMBIGUOUS_LENGTH |
| 118 | Prompt Code, Pending | 0901 | qe_example_value |  |
| 118 | Device Card Table Version | 102386 | format_synthesis |  |
| 118 | Card Table Load Version | 239345 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:6016](../traceability/segment-DL7.md) → SC-11390 → TC-021182 → `TC-021182.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?
