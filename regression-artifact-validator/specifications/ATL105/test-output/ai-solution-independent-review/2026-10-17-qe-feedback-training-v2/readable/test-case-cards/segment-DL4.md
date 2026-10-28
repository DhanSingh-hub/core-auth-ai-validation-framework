# Segment DL4 — Test Case Cards

One card per test case (QE feedback Def_SepRun07/Def_SepRun10). Derived from the canonical JSON on every run; JSON stays authoritative. Fields flagged UNTRUSTED_VALID_VALUES, AMBIGUOUS_LENGTH or DELIBERATE_CONSTRAINT_VIOLATION are marked ⚠.

## TC-009256 — Business_Rule — Financial Transaction Request — Software Load Type is one alphanumeric character: F (Full) or P (Parti

**Purpose:** Software Load Type is one alphanumeric character: F (Full) or P (Partial). (source [p.406](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=406))

**Derivation path:** Spec [p.406](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=406) (ATL105 2026-3) → BR-406-2 → REQ-SRC-ATL105-PDF-001:1949 → derivation_method = business_rule_derived → SC-7323 (business_rule) → TC-009256 (transaction = Financial Transaction Request)

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
| 108 | Loyalty Program ID | 206536 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 299654 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 15122958 | realistic_amount |  |
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
| 119 | Hardware Version | WGDETXTZ | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | LH8RNTIO | format_synthesis |  |
| 119 | Firmware Version | LC5FCKX3 | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 314449 | format_synthesis |  |
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
| 132 | Hardware Version | WGDETXTZ | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | LH8RNTIO | format_synthesis |  |
| 132 | Firmware Version | LC5FCKX3 | format_synthesis |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:1949](../traceability/segment-DL4.md) → SC-7323 → TC-009256 → `TC-009256.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-017×1, QEF-018×1, QEF-019×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-024×1, QEF-026×1, QEF-027×1, QEF-028×1, QEF-030×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020974 — Business_Rule — Proprietary Data Load Request — Downloads must be requestable via manual user intervention and automat

**Purpose:** Downloads must be requestable via manual user intervention and automatically by the device based on host response. (source [p.186](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=186))

**Derivation path:** Spec [p.186](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=186) (ATL105 2026-3) → BR-186-3 → REQ-SRC-ATL105-PDF-001:1065 → derivation_method = business_rule_derived → SC-6439 (business_rule) → TC-020974 (transaction = Proprietary Data Load Request)

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
| 118 | Device Card Table Version | 613849 | format_synthesis |  |
| 118 | Card Table Load Version | 634141 | format_synthesis |  |
| 118 | Card Table Type | 0001 | qe_example_value |  |
| 118 | Load Control Key | ‹masked, 6 chars› | format_synthesis |  |
| 118 | Host Discount Timestamp | 202506151430 | qe_example_value |  |

**Expected outcome:** NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail

**Traceability:** [REQ-SRC-ATL105-PDF-001:1065](../traceability/segment-DL4.md) → SC-6439 → TC-020974 → `TC-020974.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?
