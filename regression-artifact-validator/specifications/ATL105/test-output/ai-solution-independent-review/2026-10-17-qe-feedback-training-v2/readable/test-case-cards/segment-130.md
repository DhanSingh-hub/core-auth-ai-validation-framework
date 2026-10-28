# Segment 130 — Test Case Cards

One card per test case (QE feedback Def_SepRun07/Def_SepRun10). Derived from the canonical JSON on every run; JSON stays authoritative. Fields flagged UNTRUSTED_VALID_VALUES, AMBIGUOUS_LENGTH or DELIBERATE_CONSTRAINT_VIOLATION are marked ⚠.

## TC-002529 — Negative — EMV Financial Transaction Request — CA Public Key File Checksum

**Purpose:** CA Public Key File<br>Checksum must be numeric and must not exceed 25 bytes. (source [p.469](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=469))

**Derivation path:** Spec [p.469](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=469) (ATL105 2026-3) → ENT-ELEM-187 → REQ-SRC-ATL105-PDF-001:200 → derivation_method = python_template → SC-2633 (negative, violated = CA Public Key File Checksum) → TC-002529 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 970523 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 161459 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 30410979 | realistic_amount |  |
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

**Expected outcome:** Response code 1 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:200](../traceability/segment-130.md) → SC-2633 → TC-002529 → `TC-002529.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-002530 — Negative — EMV Financial Transaction Request — EMV Card Sequence Number

**Purpose:** EMV Card Sequence<br>Number must be alphanumeric and must not exceed 3 bytes. (source [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470))

**Derivation path:** Spec [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (ATL105 2026-3) → ENT-ELEM-188 → REQ-SRC-ATL105-PDF-001:201 → derivation_method = python_template → SC-2634 (negative, violated = EMV Card Sequence Number) → TC-002530 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 433775 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 806331 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 07885926 | realistic_amount |  |
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

**Expected outcome:** Response code 1, decline code 2H (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:201](../traceability/segment-130.md) → SC-2634 → TC-002530 → `TC-002530.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-002531 — Negative — EMV Financial Transaction Request — EMV Card Sequence Number

**Purpose:** EMV Card Sequence<br>Number must be one of: card, chip card. (source [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470))

**Derivation path:** Spec [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (ATL105 2026-3) → ENT-ELEM-188 → REQ-SRC-ATL105-PDF-001:202 → derivation_method = python_template → SC-2635 (negative, violated = EMV Card Sequence Number) → TC-002531 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 043321 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 644641 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 82372041 | realistic_amount |  |
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

**Expected outcome:** Response code 1, decline code 2H (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:202](../traceability/segment-130.md) → SC-2635 → TC-002531 → `TC-002531.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-002532 — Negative — EMV Financial Transaction Request — EMV Chip Data Length

**Purpose:** EMV Chip Data Length must be numeric and must not exceed 3 bytes. (source [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470))

**Derivation path:** Spec [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (ATL105 2026-3) → ENT-ELEM-189 → REQ-SRC-ATL105-PDF-001:203 → derivation_method = python_template → SC-2636 (negative, violated = EMV Chip Data Length) → TC-002532 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 816745 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 098090 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 97526395 | realistic_amount |  |
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
| 130 | EMV Chip Data Length | 0000 | deliberate_violation_length | ⚠ DELIBERATE_CONSTRAINT_VIOLATION |
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

**Expected outcome:** Response code 1 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:203](../traceability/segment-130.md) → SC-2636 → TC-002532 → `TC-002532.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-002533 — Negative — EMV Financial Transaction Request — EMV Chip Data Component Name: Value (ANSB ...255) The data of each Tag/Length/Value combination is up to 255 bytes long, depending on the length of the field specified by the preceding length portion of the sub element. For an example of this data element, please see Appendix R. EMV Chip Data.

**Purpose:** EMV Chip Data<br>Component Name: Value (ANSB ...255)<br>The data of each Tag/Length/Value combination is up to 255 bytes<br>long, depending on the length of the field specified by the preceding<br>length portion of the sub element.<br>For an example of this data element, please see Appendix R. EMV<br>Chip Data. must be alphanumeric, special, and binary and must not exceed 999 bytes. (source [p.471](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471))

**Derivation path:** Spec [p.471](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471) (ATL105 2026-3) → ENT-ELEM-190 → REQ-SRC-ATL105-PDF-001:204 → derivation_method = python_template → SC-2637 (negative, violated = EMV Chip Data Component Name: Value (ANSB ...255) The data of each Tag/Length/Value combination is up to 255 bytes long, depending on the length of the field specified by the preceding length portion of the sub element. For an example of this data element, please see Appendix R. EMV Chip Data.) → TC-002533 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 349376 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 616665 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 03174947 | realistic_amount |  |
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

**Expected outcome:** Response code 1, decline code 4M (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:204](../traceability/segment-130.md) → SC-2637 → TC-002533 → `TC-002533.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-002658 — Negative — EMV Financial Transaction Request — EMV Chip Data Length

**Purpose:** EMV Chip Data Length must be numeric and must not exceed 3 bytes. (source [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470))

**Derivation path:** Spec [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (ATL105 2026-3) → ENT-ELEM-189 → REQ-SRC-ATL105-PDF-001:203 → derivation_method = python_template → SC-2762 (negative, violated = EMV Chip Data Length) → TC-002658 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 947099 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 318230 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 78410257 | realistic_amount |  |
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
| 130 | EMV Chip Data Length | 00# | deliberate_violation_charset | ⚠ DELIBERATE_CONSTRAINT_VIOLATION |
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

**Expected outcome:** Response code 1 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:203](../traceability/segment-130.md) → SC-2762 → TC-002658 → `TC-002658.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-002659 — Negative — EMV Financial Transaction Request — EMV Chip Data Length

**Purpose:** EMV Chip Data Length must be numeric and must not exceed 3 bytes. (source [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470))

**Derivation path:** Spec [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (ATL105 2026-3) → ENT-ELEM-189 → REQ-SRC-ATL105-PDF-001:203 → derivation_method = python_template → SC-2763 (negative, violated = EMV Chip Data Length) → TC-002659 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 144756 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 725234 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 56167105 | realistic_amount |  |
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
| 130 | EMV Chip Data Length |  | deliberate_violation_empty | ⚠ DELIBERATE_CONSTRAINT_VIOLATION |
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

**Expected outcome:** Response code 1 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:203](../traceability/segment-130.md) → SC-2763 → TC-002659 → `TC-002659.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-002709 — Negative — EMV Financial Transaction Request — Segment Type

**Purpose:** In EMV Request Data Segment (Data Segment No. 130), field 1 (Segment Type) is required and must be present. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → ENT-FIELD-130-1 → REQ-SRC-ATL105-PDF-001:3445 → derivation_method = field_constraint → SC-2813 (negative, violated = Segment Type) → TC-002709 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 690191 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 205767 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 32466982 | realistic_amount |  |
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
| 130 | Segment Type |  | deliberate_violation_missing_mandatory | ⚠ DELIBERATE_CONSTRAINT_VIOLATION |
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

**Expected outcome:** Response code 1 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3445](../traceability/segment-130.md) → SC-2813 → TC-002709 → `TC-002709.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-002710 — Negative — EMV Financial Transaction Request — Segment Length

**Purpose:** In EMV Request Data Segment (Data Segment No. 130), field 2 (Segment Length) is required and must be present. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → ENT-FIELD-130-2 → REQ-SRC-ATL105-PDF-001:3447 → derivation_method = field_constraint → SC-2814 (negative, violated = Segment Length) → TC-002710 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 134347 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 427234 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 77812379 | realistic_amount |  |
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
| 130 | Segment Length |  | deliberate_violation_missing_mandatory | ⚠ DELIBERATE_CONSTRAINT_VIOLATION |
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

**Expected outcome:** Response code 1 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3447](../traceability/segment-130.md) → SC-2814 → TC-002710 → `TC-002710.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-002711 — Negative — EMV Financial Transaction Request — EMV Chip Data Length

**Purpose:** In EMV Request Data Segment (Data Segment No. 130), field 5 (EMV Chip Data Length) is required and must be present. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → ENT-FIELD-130-5 → REQ-SRC-ATL105-PDF-001:3450 → derivation_method = field_constraint → SC-2815 (negative, violated = EMV Chip Data Length) → TC-002711 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 824745 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 119406 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 76339490 | realistic_amount |  |
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
| 130 | EMV Chip Data Length |  | deliberate_violation_missing_mandatory | ⚠ DELIBERATE_CONSTRAINT_VIOLATION |
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

**Expected outcome:** Response code 1 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3450](../traceability/segment-130.md) → SC-2815 → TC-002711 → `TC-002711.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-002712 — Negative — EMV Financial Transaction Request — EMV Chip Data

**Purpose:** In EMV Request Data Segment (Data Segment No. 130), field 6 (EMV Chip Data) is required and must be present. (source [p.268](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=268))

**Derivation path:** Spec [p.268](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=268) (ATL105 2026-3) → ENT-FIELD-130-6 → REQ-SRC-ATL105-PDF-001:3451 → derivation_method = field_constraint → SC-2816 (negative, violated = EMV Chip Data) → TC-002712 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 781244 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 650462 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 64320383 | realistic_amount |  |
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
| 130 | EMV Chip Data |  | deliberate_violation_missing_mandatory | ⚠ DELIBERATE_CONSTRAINT_VIOLATION |
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

**Expected outcome:** Response code 1 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3451](../traceability/segment-130.md) → SC-2816 → TC-002712 → `TC-002712.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-002744 — Negative — EMV Financial Transaction Request — Segment Type

**Purpose:** In EMV Request Data Segment (Data Segment No. 130), field 1 (Segment Type) must equal the fixed value 130. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → ENT-FIELD-130-1 → REQ-SRC-ATL105-PDF-001:3446 → derivation_method = field_fixed_value → SC-2848 (negative, violated = Segment Type) → TC-002744 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 282749 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 967644 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 14451307 | realistic_amount |  |
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
| 130 | Segment Type | 131 | deliberate_violation_wrong_fixed_value | ⚠ DELIBERATE_CONSTRAINT_VIOLATION |
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

**Expected outcome:** Response code 1 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:3446](../traceability/segment-130.md) → SC-2848 → TC-002744 → `TC-002744.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-003237 — Negative — Financial Transaction Request — CA Public Key File Checksum

**Purpose:** CA Public Key File<br>Checksum must be numeric and must not exceed 25 bytes. (source [p.469](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=469))

**Derivation path:** Spec [p.469](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=469) (ATL105 2026-3) → ENT-ELEM-187 → REQ-SRC-ATL105-PDF-001:200 → derivation_method = python_template → SC-3395 (negative, violated = CA Public Key File Checksum) → TC-003237 (transaction = Financial Transaction Request)

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
| 108 | Loyalty Program ID | 813353 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 563840 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 41922093 | realistic_amount |  |
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
| 119 | Hardware Version | 8269KP46 | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | N2P216RC | format_synthesis |  |
| 119 | Firmware Version | OL9LEU4V | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 692261 | format_synthesis |  |
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
| 132 | Hardware Version | 8269KP46 | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | N2P216RC | format_synthesis |  |
| 132 | Firmware Version | OL9LEU4V | format_synthesis |  |
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

**Expected outcome:** Response code 1 (oracle tier ASSERT)

**Traceability:** [REQ-SRC-ATL105-PDF-001:200](../traceability/segment-130.md) → SC-3395 → TC-003237 → `TC-003237.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-017×1, QEF-018×1, QEF-019×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-024×1, QEF-026×1, QEF-027×1, QEF-028×1, QEF-030×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-004837 — Boundary — EMV Financial Transaction Request — EMV Chip Data Length must be numeric and must not exceed 3 bytes.

**Purpose:** EMV Chip Data Length must be numeric and must not exceed 3 bytes. (source [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470))

**Derivation path:** Spec [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (ATL105 2026-3) → ENT-ELEM-189 → REQ-SRC-ATL105-PDF-001:203 → derivation_method = python_template → SC-5183 (boundary) → TC-004837 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 745949 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 407471 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 91389810 | realistic_amount |  |
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
| 130 | EMV Chip Data Length | 000 | boundary_at_limit_value |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:203](../traceability/segment-130.md) → SC-5183 → TC-004837 → `TC-004837.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-005892 — Business_Rule — EMV Financial Transaction Request — STAR Single message transactions must be card present, full mag-stripe

**Purpose:** STAR Single message transactions must be card present, full mag-stripe data, or full EMV chip data. (source [p.106](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=106))

**Derivation path:** Spec [p.106](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=106) (ATL105 2026-3) → BR-106-11 → REQ-SRC-ATL105-PDF-001:676 → derivation_method = business_rule_derived → SC-6050 (business_rule) → TC-005892 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 807091 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 770866 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 60690304 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:676](../traceability/segment-130.md) → SC-6050 → TC-005892 → `TC-005892.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007073 — Business_Rule — EMV Financial Transaction Request — EMV Request Data Segment is required for all EMV card transactions and

**Purpose:** EMV Request Data Segment is required for all EMV card transactions and is the only segment required for all EMV financial transactions. (source [p.203](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=203))

**Derivation path:** Spec [p.203](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=203) (ATL105 2026-3) → BR-203-7 → REQ-SRC-ATL105-PDF-001:1133 → derivation_method = business_rule_derived → SC-6507 (business_rule) → TC-007073 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 093657 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 408739 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 73720116 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:1133](../traceability/segment-130.md) → SC-6507 → TC-007073 → `TC-007073.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007130 — Business_Rule — EMV Financial Transaction Request — CA Public Key File Checksum must be used with all subsequent EMV trans

**Purpose:** CA Public Key File Checksum must be used with all subsequent EMV transactions from this device. (source [p.213](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=213))

**Derivation path:** Spec [p.213](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=213) (ATL105 2026-3) → BR-213-3 → REQ-SRC-ATL105-PDF-001:1162 → derivation_method = business_rule_derived → SC-6536 (business_rule) → TC-007130 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 148302 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 724344 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 69922914 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:1162](../traceability/segment-130.md) → SC-6536 → TC-007130 → `TC-007130.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007131 — Business_Rule — Financial Transaction Request — CA Public Key File Checksum must be used with all subsequent EMV trans

**Purpose:** CA Public Key File Checksum must be used with all subsequent EMV transactions from this device. (source [p.213](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=213))

**Derivation path:** Spec [p.213](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=213) (ATL105 2026-3) → BR-213-3 → REQ-SRC-ATL105-PDF-001:1162 → derivation_method = business_rule_derived → SC-6536 (business_rule) → TC-007131 (transaction = Financial Transaction Request)

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
| 108 | Loyalty Program ID | 168552 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 643826 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 07542116 | realistic_amount |  |
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
| 119 | Hardware Version | 6VWCN5K1 | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | BAPRZWTE | format_synthesis |  |
| 119 | Firmware Version | XANE6IJ1 | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 552915 | format_synthesis |  |
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
| 132 | Hardware Version | 6VWCN5K1 | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | BAPRZWTE | format_synthesis |  |
| 132 | Firmware Version | XANE6IJ1 | format_synthesis |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:1162](../traceability/segment-130.md) → SC-6536 → TC-007131 → `TC-007131.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-017×1, QEF-018×1, QEF-019×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-024×1, QEF-026×1, QEF-027×1, QEF-028×1, QEF-030×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007685 — Business_Rule — EMV Financial Transaction Request — A Field Separator is required between Fields 1-6 even when a field is 

**Purpose:** A Field Separator is required between Fields 1-6 even when a field is not populated. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → BR-267-1 → REQ-SRC-ATL105-PDF-001:1344 → derivation_method = business_rule_derived → SC-6718 (business_rule) → TC-007685 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 180864 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 736960 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 75996508 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:1344](../traceability/segment-130.md) → SC-6718 → TC-007685 → `TC-007685.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007686 — Business_Rule — EMV Financial Transaction Request — No Field Separators appear between elements within EMV Additional Info

**Purpose:** No Field Separators appear between elements within EMV Additional Information Section or between its repetitions. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → BR-267-2 → REQ-SRC-ATL105-PDF-001:1345 → derivation_method = business_rule_derived → SC-6719 (business_rule) → TC-007686 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 400662 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 904206 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 24851779 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:1345](../traceability/segment-130.md) → SC-6719 → TC-007686 → `TC-007686.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007687 — Business_Rule — EMV Financial Transaction Request — A Field Separator follows the final EMV Additional Information Section

**Purpose:** A Field Separator follows the final EMV Additional Information Section. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → BR-267-3 → REQ-SRC-ATL105-PDF-001:1346 → derivation_method = business_rule_derived → SC-6720 (business_rule) → TC-007687 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 129974 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 317647 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 35522654 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:1346](../traceability/segment-130.md) → SC-6720 → TC-007687 → `TC-007687.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007688 — Business_Rule — EMV Financial Transaction Request — EMV Chip Data Length valid values are 000-999.

**Purpose:** EMV Chip Data Length valid values are 000-999. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → BR-267-4 → REQ-SRC-ATL105-PDF-001:1347 → derivation_method = business_rule_derived → SC-6721 (business_rule) → TC-007688 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 608964 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 377191 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 83970158 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:1347](../traceability/segment-130.md) → SC-6721 → TC-007688 → `TC-007688.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-007689 — Business_Rule — EMV Financial Transaction Request — EMV Request Data Segment always appears in Data Section No. 3 of an EM

**Purpose:** EMV Request Data Segment always appears in Data Section No. 3 of an EMV Financial Transaction Request. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → BR-267-5 → REQ-SRC-ATL105-PDF-001:1348 → derivation_method = business_rule_derived → SC-6722 (business_rule) → TC-007689 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 971874 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 256628 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 66159386 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:1348](../traceability/segment-130.md) → SC-6722 → TC-007689 → `TC-007689.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-009186 — Business_Rule — EMV Financial Transaction Request — EMV Request Data Segment (No. 130) data length ranges 001-3043.

**Purpose:** EMV Request Data Segment (No. 130) data length ranges 001-3043. (source [p.400](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=400))

**Derivation path:** Spec [p.400](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=400) (ATL105 2026-3) → BR-400-20 → REQ-SRC-ATL105-PDF-001:1929 → derivation_method = business_rule_derived → SC-7303 (business_rule) → TC-009186 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 603983 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 995956 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 21851767 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:1929](../traceability/segment-130.md) → SC-7303 → TC-009186 → `TC-009186.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010213 — Business_Rule — EMV Financial Transaction Request — CA Public Key File Checksum is required in the EMV Financial Transacti

**Purpose:** CA Public Key File Checksum is required in the EMV Financial Transaction Request. (source [p.469](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=469))

**Derivation path:** Spec [p.469](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=469) (ATL105 2026-3) → BR-469-4 → REQ-SRC-ATL105-PDF-001:2258 → derivation_method = business_rule_derived → SC-7632 (business_rule) → TC-010213 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 832165 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 969872 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 30703477 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2258](../traceability/segment-130.md) → SC-7632 → TC-010213 → `TC-010213.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010214 — Business_Rule — Financial Transaction Request — CA Public Key File Checksum is required in the EMV Financial Transacti

**Purpose:** CA Public Key File Checksum is required in the EMV Financial Transaction Request. (source [p.469](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=469))

**Derivation path:** Spec [p.469](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=469) (ATL105 2026-3) → BR-469-4 → REQ-SRC-ATL105-PDF-001:2258 → derivation_method = business_rule_derived → SC-7632 (business_rule) → TC-010214 (transaction = Financial Transaction Request)

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
| 108 | Loyalty Program ID | 575658 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 492538 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 46246022 | realistic_amount |  |
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
| 119 | Hardware Version | B5DM45MN | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | 7W1JLQZV | format_synthesis |  |
| 119 | Firmware Version | TWLPLCM5 | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 582812 | format_synthesis |  |
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
| 132 | Hardware Version | B5DM45MN | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | 7W1JLQZV | format_synthesis |  |
| 132 | Firmware Version | TWLPLCM5 | format_synthesis |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2258](../traceability/segment-130.md) → SC-7632 → TC-010214 → `TC-010214.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-017×1, QEF-018×1, QEF-019×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-024×1, QEF-026×1, QEF-027×1, QEF-028×1, QEF-030×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010215 — Business_Rule — EMV Financial Transaction Request — Once received, the checksum must be used for all subsequent EMV transa

**Purpose:** Once received, the checksum must be used for all subsequent EMV transaction requests from this device. (source [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470))

**Derivation path:** Spec [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (ATL105 2026-3) → BR-470-1 → REQ-SRC-ATL105-PDF-001:2259 → derivation_method = business_rule_derived → SC-7633 (business_rule) → TC-010215 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 890855 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 992791 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 00602591 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2259](../traceability/segment-130.md) → SC-7633 → TC-010215 → `TC-010215.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010216 — Business_Rule — Financial Transaction Request — Once received, the checksum must be used for all subsequent EMV transa

**Purpose:** Once received, the checksum must be used for all subsequent EMV transaction requests from this device. (source [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470))

**Derivation path:** Spec [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (ATL105 2026-3) → BR-470-1 → REQ-SRC-ATL105-PDF-001:2259 → derivation_method = business_rule_derived → SC-7633 (business_rule) → TC-010216 (transaction = Financial Transaction Request)

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
| 108 | Loyalty Program ID | 609889 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 246850 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 68681487 | realistic_amount |  |
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
| 119 | Hardware Version | F9XXQ1ZP | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | J6HL4834 | format_synthesis |  |
| 119 | Firmware Version | G31KVGL7 | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 699916 | format_synthesis |  |
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
| 132 | Hardware Version | F9XXQ1ZP | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | J6HL4834 | format_synthesis |  |
| 132 | Firmware Version | G31KVGL7 | format_synthesis |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2259](../traceability/segment-130.md) → SC-7633 → TC-010216 → `TC-010216.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-017×1, QEF-018×1, QEF-019×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-024×1, QEF-026×1, QEF-027×1, QEF-028×1, QEF-030×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010217 — Business_Rule — EMV Financial Transaction Request — CA Public Key File Checksum is required in the CA Public Key File Load

**Purpose:** CA Public Key File Checksum is required in the CA Public Key File Load Response. (source [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470))

**Derivation path:** Spec [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (ATL105 2026-3) → BR-470-2 → REQ-SRC-ATL105-PDF-001:2260 → derivation_method = business_rule_derived → SC-7634 (business_rule) → TC-010217 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 757160 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 231351 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 10506151 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2260](../traceability/segment-130.md) → SC-7634 → TC-010217 → `TC-010217.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010218 — Business_Rule — Financial Transaction Request — CA Public Key File Checksum is required in the CA Public Key File Load

**Purpose:** CA Public Key File Checksum is required in the CA Public Key File Load Response. (source [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470))

**Derivation path:** Spec [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (ATL105 2026-3) → BR-470-2 → REQ-SRC-ATL105-PDF-001:2260 → derivation_method = business_rule_derived → SC-7634 (business_rule) → TC-010218 (transaction = Financial Transaction Request)

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
| 108 | Loyalty Program ID | 645293 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 949586 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 28571961 | realistic_amount |  |
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
| 119 | Hardware Version | LHPXLLG9 | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | PWOKKOXM | format_synthesis |  |
| 119 | Firmware Version | O7P7W3ZL | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 861874 | format_synthesis |  |
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
| 132 | Hardware Version | LHPXLLG9 | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | PWOKKOXM | format_synthesis |  |
| 132 | Firmware Version | O7P7W3ZL | format_synthesis |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2260](../traceability/segment-130.md) → SC-7634 → TC-010218 → `TC-010218.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-017×1, QEF-018×1, QEF-019×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-024×1, QEF-026×1, QEF-027×1, QEF-028×1, QEF-030×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010219 — Business_Rule — EMV Financial Transaction Request — EMV Card Sequence Number is required when ICC Data is present and card

**Purpose:** EMV Card Sequence Number is required when ICC Data is present and card product includes it in EMV data. (source [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470))

**Derivation path:** Spec [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (ATL105 2026-3) → BR-470-3 → REQ-SRC-ATL105-PDF-001:2261 → derivation_method = business_rule_derived → SC-7635 (business_rule) → TC-010219 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 147557 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 232016 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 10916594 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2261](../traceability/segment-130.md) → SC-7635 → TC-010219 → `TC-010219.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010220 — Business_Rule — EMV Financial Transaction Request — If EMV Tag 5F34 is not provided by the card, send three spaces for EMV

**Purpose:** If EMV Tag 5F34 is not provided by the card, send three spaces for EMV Card Sequence Number. (source [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470))

**Derivation path:** Spec [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (ATL105 2026-3) → BR-470-4 → REQ-SRC-ATL105-PDF-001:2262 → derivation_method = business_rule_derived → SC-7636 (business_rule) → TC-010220 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 190400 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 320864 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 33740922 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2262](../traceability/segment-130.md) → SC-7636 → TC-010220 → `TC-010220.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010221 — Business_Rule — EMV Financial Transaction Request — EMV Card Sequence Number valid values are 000-099.

**Purpose:** EMV Card Sequence Number valid values are 000-099. (source [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470))

**Derivation path:** Spec [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (ATL105 2026-3) → BR-470-5 → REQ-SRC-ATL105-PDF-001:2263 → derivation_method = business_rule_derived → SC-7637 (business_rule) → TC-010221 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 788174 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 558333 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 26043501 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2263](../traceability/segment-130.md) → SC-7637 → TC-010221 → `TC-010221.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010222 — Business_Rule — EMV Financial Transaction Request — EMV Chip Data Length is required in all EMV transactions.

**Purpose:** EMV Chip Data Length is required in all EMV transactions. (source [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470))

**Derivation path:** Spec [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (ATL105 2026-3) → BR-470-6 → REQ-SRC-ATL105-PDF-001:2264 → derivation_method = business_rule_derived → SC-7638 (business_rule) → TC-010222 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 000104 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 811353 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 56356322 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2264](../traceability/segment-130.md) → SC-7638 → TC-010222 → `TC-010222.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010223 — Business_Rule — EMV Financial Transaction Request — EMV Chip Data Length valid values are 000-999.

**Purpose:** EMV Chip Data Length valid values are 000-999. (source [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470))

**Derivation path:** Spec [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (ATL105 2026-3) → BR-470-7 → REQ-SRC-ATL105-PDF-001:2265 → derivation_method = business_rule_derived → SC-7639 (business_rule) → TC-010223 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 878787 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 100097 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 26537888 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2265](../traceability/segment-130.md) → SC-7639 → TC-010223 → `TC-010223.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010224 — Business_Rule — EMV Financial Transaction Request — EMV Chip Data is required in all EMV transactions.

**Purpose:** EMV Chip Data is required in all EMV transactions. (source [p.471](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471))

**Derivation path:** Spec [p.471](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471) (ATL105 2026-3) → BR-471-1 → REQ-SRC-ATL105-PDF-001:2266 → derivation_method = business_rule_derived → SC-7640 (business_rule) → TC-010224 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 121966 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 305178 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 26465802 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2266](../traceability/segment-130.md) → SC-7640 → TC-010224 → `TC-010224.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010225 — Business_Rule — EMV Financial Transaction Request — All EMV data elements must contain exact values present on the card to

**Purpose:** All EMV data elements must contain exact values present on the card to terminal interface. (source [p.471](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471))

**Derivation path:** Spec [p.471](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471) (ATL105 2026-3) → BR-471-2 → REQ-SRC-ATL105-PDF-001:2267 → derivation_method = business_rule_derived → SC-7641 (business_rule) → TC-010225 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 162145 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 388470 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 23178854 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2267](../traceability/segment-130.md) → SC-7641 → TC-010225 → `TC-010225.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010226 — Business_Rule — EMV Financial Transaction Request — If a data element appears multiple times, field must contain the last 

**Purpose:** If a data element appears multiple times, field must contain the last value present during the transaction. (source [p.471](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471))

**Derivation path:** Spec [p.471](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471) (ATL105 2026-3) → BR-471-3 → REQ-SRC-ATL105-PDF-001:2268 → derivation_method = business_rule_derived → SC-7642 (business_rule) → TC-010226 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 546933 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 169051 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 80602884 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2268](../traceability/segment-130.md) → SC-7642 → TC-010226 → `TC-010226.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010227 — Business_Rule — EMV Financial Transaction Request — AID (tag 9F06) or Dedicated File Name (tag 84) must be included in EMV

**Purpose:** AID (tag 9F06) or Dedicated File Name (tag 84) must be included in EMV Chip Data for proper EMV processing. (source [p.471](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471))

**Derivation path:** Spec [p.471](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471) (ATL105 2026-3) → BR-471-4 → REQ-SRC-ATL105-PDF-001:2269 → derivation_method = business_rule_derived → SC-7643 (business_rule) → TC-010227 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 580397 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 554430 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 23950018 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2269](../traceability/segment-130.md) → SC-7643 → TC-010227 → `TC-010227.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010228 — Business_Rule — EMV Financial Transaction Request — Tag '5A' (PAN) and Tag '57' (Track 2 equivalent data) must not be incl

**Purpose:** Tag '5A' (PAN) and Tag '57' (Track 2 equivalent data) must not be included in EMV Chip Data. (source [p.471](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471))

**Derivation path:** Spec [p.471](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471) (ATL105 2026-3) → BR-471-5 → REQ-SRC-ATL105-PDF-001:2270 → derivation_method = business_rule_derived → SC-7644 (business_rule) → TC-010228 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 003879 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 347020 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 38191642 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2270](../traceability/segment-130.md) → SC-7644 → TC-010228 → `TC-010228.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010229 — Business_Rule — EMV Financial Transaction Request — Tag is two bytes long if last five bits of first byte are all 1; other

**Purpose:** Tag is two bytes long if last five bits of first byte are all 1; otherwise one byte. (source [p.471](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471))

**Derivation path:** Spec [p.471](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471) (ATL105 2026-3) → BR-471-6 → REQ-SRC-ATL105-PDF-001:2271 → derivation_method = business_rule_derived → SC-7645 (business_rule) → TC-010229 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 310782 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 705646 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 93256420 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2271](../traceability/segment-130.md) → SC-7645 → TC-010229 → `TC-010229.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010230 — Business_Rule — EMV Financial Transaction Request — If first bit of length field's first byte is 1, remaining 7 bits indic

**Purpose:** If first bit of length field's first byte is 1, remaining 7 bits indicate number of following length bytes. (source [p.471](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471))

**Derivation path:** Spec [p.471](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471) (ATL105 2026-3) → BR-471-7 → REQ-SRC-ATL105-PDF-001:2272 → derivation_method = business_rule_derived → SC-7646 (business_rule) → TC-010230 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 693564 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 362482 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 00512058 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2272](../traceability/segment-130.md) → SC-7646 → TC-010230 → `TC-010230.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-010231 — Business_Rule — EMV Financial Transaction Request — If first bit of length field's first byte is 0, remaining bits indicat

**Purpose:** If first bit of length field's first byte is 0, remaining bits indicate actual data length. (source [p.471](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471))

**Derivation path:** Spec [p.471](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471) (ATL105 2026-3) → BR-471-8 → REQ-SRC-ATL105-PDF-001:2273 → derivation_method = business_rule_derived → SC-7647 (business_rule) → TC-010231 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 940523 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 269313 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 87281212 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2273](../traceability/segment-130.md) → SC-7647 → TC-010231 → `TC-010231.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012720 — Field_Constraint — ECA/TeleCheck Service Transaction — In EMV Request Data Segment (Data Segment No. 130), field 1 (Segment T

**Purpose:** In EMV Request Data Segment (Data Segment No. 130), field 1 (Segment Type) is required and must be present. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → ENT-FIELD-130-1 → REQ-SRC-ATL105-PDF-001:3445 → derivation_method = field_constraint → SC-8819 (field_constraint) → TC-012720 (transaction = ECA/TeleCheck Service Transaction)

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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3445](../traceability/segment-130.md) → SC-8819 → TC-012720 → `TC-012720.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012721 — Field_Constraint — ECA/TeleCheck Service Transaction Request — In EMV Request Data Segment (Data Segment No. 130), field 1 (Segment T

**Purpose:** In EMV Request Data Segment (Data Segment No. 130), field 1 (Segment Type) is required and must be present. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → ENT-FIELD-130-1 → REQ-SRC-ATL105-PDF-001:3445 → derivation_method = field_constraint → SC-8819 (field_constraint) → TC-012721 (transaction = ECA/TeleCheck Service Transaction Request)

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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3445](../traceability/segment-130.md) → SC-8819 → TC-012721 → `TC-012721.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012722 — Field_Constraint — EMV Financial Transaction Request — In EMV Request Data Segment (Data Segment No. 130), field 1 (Segment T

**Purpose:** In EMV Request Data Segment (Data Segment No. 130), field 1 (Segment Type) is required and must be present. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → ENT-FIELD-130-1 → REQ-SRC-ATL105-PDF-001:3445 → derivation_method = field_constraint → SC-8819 (field_constraint) → TC-012722 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 613209 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 917823 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 47736746 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3445](../traceability/segment-130.md) → SC-8819 → TC-012722 → `TC-012722.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012723 — Field_Constraint — EMV Request Transaction — In EMV Request Data Segment (Data Segment No. 130), field 1 (Segment T

**Purpose:** In EMV Request Data Segment (Data Segment No. 130), field 1 (Segment Type) is required and must be present. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → ENT-FIELD-130-1 → REQ-SRC-ATL105-PDF-001:3445 → derivation_method = field_constraint → SC-8819 (field_constraint) → TC-012723 (transaction = EMV Request Transaction)

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
| 108 | Loyalty Program ID | 800038 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 039157 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 86993349 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3445](../traceability/segment-130.md) → SC-8819 → TC-012723 → `TC-012723.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012724 — Field_Constraint — Electronic Mail — In EMV Request Data Segment (Data Segment No. 130), field 1 (Segment T

**Purpose:** In EMV Request Data Segment (Data Segment No. 130), field 1 (Segment Type) is required and must be present. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → ENT-FIELD-130-1 → REQ-SRC-ATL105-PDF-001:3445 → derivation_method = field_constraint → SC-8819 (field_constraint) → TC-012724 (transaction = Electronic Mail)

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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3445](../traceability/segment-130.md) → SC-8819 → TC-012724 → `TC-012724.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012725 — Field_Fixed_Value — ECA/TeleCheck Service Transaction — In EMV Request Data Segment (Data Segment No. 130), field 1 (Segment T

**Purpose:** In EMV Request Data Segment (Data Segment No. 130), field 1 (Segment Type) must equal the fixed value 130. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → ENT-FIELD-130-1 → REQ-SRC-ATL105-PDF-001:3446 → derivation_method = field_fixed_value → SC-8820 (field_fixed_value) → TC-012725 (transaction = ECA/TeleCheck Service Transaction)

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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3446](../traceability/segment-130.md) → SC-8820 → TC-012725 → `TC-012725.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012726 — Field_Fixed_Value — ECA/TeleCheck Service Transaction Request — In EMV Request Data Segment (Data Segment No. 130), field 1 (Segment T

**Purpose:** In EMV Request Data Segment (Data Segment No. 130), field 1 (Segment Type) must equal the fixed value 130. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → ENT-FIELD-130-1 → REQ-SRC-ATL105-PDF-001:3446 → derivation_method = field_fixed_value → SC-8820 (field_fixed_value) → TC-012726 (transaction = ECA/TeleCheck Service Transaction Request)

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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3446](../traceability/segment-130.md) → SC-8820 → TC-012726 → `TC-012726.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012727 — Field_Fixed_Value — EMV Financial Transaction Request — In EMV Request Data Segment (Data Segment No. 130), field 1 (Segment T

**Purpose:** In EMV Request Data Segment (Data Segment No. 130), field 1 (Segment Type) must equal the fixed value 130. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → ENT-FIELD-130-1 → REQ-SRC-ATL105-PDF-001:3446 → derivation_method = field_fixed_value → SC-8820 (field_fixed_value) → TC-012727 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 608421 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 587746 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 83463307 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3446](../traceability/segment-130.md) → SC-8820 → TC-012727 → `TC-012727.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012728 — Field_Fixed_Value — EMV Request Transaction — In EMV Request Data Segment (Data Segment No. 130), field 1 (Segment T

**Purpose:** In EMV Request Data Segment (Data Segment No. 130), field 1 (Segment Type) must equal the fixed value 130. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → ENT-FIELD-130-1 → REQ-SRC-ATL105-PDF-001:3446 → derivation_method = field_fixed_value → SC-8820 (field_fixed_value) → TC-012728 (transaction = EMV Request Transaction)

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
| 108 | Loyalty Program ID | 996055 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 931588 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 12090451 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3446](../traceability/segment-130.md) → SC-8820 → TC-012728 → `TC-012728.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012729 — Field_Fixed_Value — Electronic Mail — In EMV Request Data Segment (Data Segment No. 130), field 1 (Segment T

**Purpose:** In EMV Request Data Segment (Data Segment No. 130), field 1 (Segment Type) must equal the fixed value 130. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → ENT-FIELD-130-1 → REQ-SRC-ATL105-PDF-001:3446 → derivation_method = field_fixed_value → SC-8820 (field_fixed_value) → TC-012729 (transaction = Electronic Mail)

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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3446](../traceability/segment-130.md) → SC-8820 → TC-012729 → `TC-012729.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012730 — Field_Constraint — ECA/TeleCheck Service Transaction — In EMV Request Data Segment (Data Segment No. 130), field 2 (Segment L

**Purpose:** In EMV Request Data Segment (Data Segment No. 130), field 2 (Segment Length) is required and must be present. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → ENT-FIELD-130-2 → REQ-SRC-ATL105-PDF-001:3447 → derivation_method = field_constraint → SC-8821 (field_constraint) → TC-012730 (transaction = ECA/TeleCheck Service Transaction)

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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3447](../traceability/segment-130.md) → SC-8821 → TC-012730 → `TC-012730.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012731 — Field_Constraint — ECA/TeleCheck Service Transaction Request — In EMV Request Data Segment (Data Segment No. 130), field 2 (Segment L

**Purpose:** In EMV Request Data Segment (Data Segment No. 130), field 2 (Segment Length) is required and must be present. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → ENT-FIELD-130-2 → REQ-SRC-ATL105-PDF-001:3447 → derivation_method = field_constraint → SC-8821 (field_constraint) → TC-012731 (transaction = ECA/TeleCheck Service Transaction Request)

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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3447](../traceability/segment-130.md) → SC-8821 → TC-012731 → `TC-012731.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012732 — Field_Constraint — EMV Financial Transaction Request — In EMV Request Data Segment (Data Segment No. 130), field 2 (Segment L

**Purpose:** In EMV Request Data Segment (Data Segment No. 130), field 2 (Segment Length) is required and must be present. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → ENT-FIELD-130-2 → REQ-SRC-ATL105-PDF-001:3447 → derivation_method = field_constraint → SC-8821 (field_constraint) → TC-012732 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 561353 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 331908 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 95025218 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3447](../traceability/segment-130.md) → SC-8821 → TC-012732 → `TC-012732.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012733 — Field_Constraint — EMV Request Transaction — In EMV Request Data Segment (Data Segment No. 130), field 2 (Segment L

**Purpose:** In EMV Request Data Segment (Data Segment No. 130), field 2 (Segment Length) is required and must be present. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → ENT-FIELD-130-2 → REQ-SRC-ATL105-PDF-001:3447 → derivation_method = field_constraint → SC-8821 (field_constraint) → TC-012733 (transaction = EMV Request Transaction)

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
| 108 | Loyalty Program ID | 305912 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 927866 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 11480176 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3447](../traceability/segment-130.md) → SC-8821 → TC-012733 → `TC-012733.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012734 — Field_Constraint — Electronic Mail — In EMV Request Data Segment (Data Segment No. 130), field 2 (Segment L

**Purpose:** In EMV Request Data Segment (Data Segment No. 130), field 2 (Segment Length) is required and must be present. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → ENT-FIELD-130-2 → REQ-SRC-ATL105-PDF-001:3447 → derivation_method = field_constraint → SC-8821 (field_constraint) → TC-012734 (transaction = Electronic Mail)

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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3447](../traceability/segment-130.md) → SC-8821 → TC-012734 → `TC-012734.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-009×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-022×1, QEF-023×1, QEF-031×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012735 — Field_Constraint — EMV Financial Transaction Request — In EMV Request Data Segment (Data Segment No. 130), field 3 (CA Public

**Purpose:** In EMV Request Data Segment (Data Segment No. 130), field 3 (CA Public Key File Checksum) is optional and may be omitted. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → ENT-FIELD-130-3 → REQ-SRC-ATL105-PDF-001:3448 → derivation_method = field_constraint → SC-8822 (field_constraint) → TC-012735 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 490431 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 015182 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 26064000 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3448](../traceability/segment-130.md) → SC-8822 → TC-012735 → `TC-012735.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012736 — Field_Constraint — Financial Transaction Request — In EMV Request Data Segment (Data Segment No. 130), field 3 (CA Public

**Purpose:** In EMV Request Data Segment (Data Segment No. 130), field 3 (CA Public Key File Checksum) is optional and may be omitted. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → ENT-FIELD-130-3 → REQ-SRC-ATL105-PDF-001:3448 → derivation_method = field_constraint → SC-8822 (field_constraint) → TC-012736 (transaction = Financial Transaction Request)

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
| 108 | Loyalty Program ID | 816117 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 577974 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 15640382 | realistic_amount |  |
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
| 119 | Hardware Version | 7Z6RFTT7 | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | Q6ALOBYS | format_synthesis |  |
| 119 | Firmware Version | 781WKKI0 | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 258094 | format_synthesis |  |
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
| 132 | Hardware Version | 7Z6RFTT7 | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | Q6ALOBYS | format_synthesis |  |
| 132 | Firmware Version | 781WKKI0 | format_synthesis |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3448](../traceability/segment-130.md) → SC-8822 → TC-012736 → `TC-012736.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-017×1, QEF-018×1, QEF-019×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-024×1, QEF-026×1, QEF-027×1, QEF-028×1, QEF-030×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012737 — Field_Constraint — EMV Financial Transaction Request — In EMV Request Data Segment (Data Segment No. 130), field 4 (EMV Card 

**Purpose:** In EMV Request Data Segment (Data Segment No. 130), field 4 (EMV Card Sequence Number) is conditional and must be present only when its documented condition holds. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → ENT-FIELD-130-4 → REQ-SRC-ATL105-PDF-001:3449 → derivation_method = field_constraint → SC-8823 (field_constraint) → TC-012737 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 089215 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 305490 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 50828330 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3449](../traceability/segment-130.md) → SC-8823 → TC-012737 → `TC-012737.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012738 — Field_Constraint — EMV Financial Transaction Request — In EMV Request Data Segment (Data Segment No. 130), field 5 (EMV Chip 

**Purpose:** In EMV Request Data Segment (Data Segment No. 130), field 5 (EMV Chip Data Length) is required and must be present. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → ENT-FIELD-130-5 → REQ-SRC-ATL105-PDF-001:3450 → derivation_method = field_constraint → SC-8824 (field_constraint) → TC-012738 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 923243 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 946059 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 76175426 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3450](../traceability/segment-130.md) → SC-8824 → TC-012738 → `TC-012738.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-012739 — Field_Constraint — EMV Financial Transaction Request — In EMV Request Data Segment (Data Segment No. 130), field 6 (EMV Chip 

**Purpose:** In EMV Request Data Segment (Data Segment No. 130), field 6 (EMV Chip Data) is required and must be present. (source [p.268](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=268))

**Derivation path:** Spec [p.268](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=268) (ATL105 2026-3) → ENT-FIELD-130-6 → REQ-SRC-ATL105-PDF-001:3451 → derivation_method = field_constraint → SC-8825 (field_constraint) → TC-012739 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 713650 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 960531 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 94320443 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3451](../traceability/segment-130.md) → SC-8825 → TC-012739 → `TC-012739.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-013407 — Message_Field_Constraint — EMV Financial Transaction Request — In the EMV Financial Transaction Request message, Data Segment No. 130

**Purpose:** In the EMV Financial Transaction Request message, Data Segment No. 130 (EMV Request Data Segment) is required and must be present. (source [p.203](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=203))

**Derivation path:** Spec [p.203](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=203) (ATL105 2026-3) → ENT-MSG-EMV_FINANCIAL_TRANSACTION_REQUEST → REQ-SRC-ATL105-PDF-001:3802 → derivation_method = message_field_constraint → SC-9176 (message_field_constraint) → TC-013407 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 765431 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 284923 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 14760916 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3802](../traceability/segment-130.md) → SC-9176 → TC-013407 → `TC-013407.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-013457 — Message_Field_Constraint — EMV Financial Transaction Request — In the CA Public Key File Load Request message, field 11 is required a

**Purpose:** In the CA Public Key File Load Request message, field 11 is required and must be present. (source [p.212](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=212))

**Derivation path:** Spec [p.212](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=212) (ATL105 2026-3) → ENT-FIELD-CA_PUBLIC_KEY_FILE_LOAD_REQUEST-11 → REQ-SRC-ATL105-PDF-001:3840 → derivation_method = message_field_constraint → SC-9214 (message_field_constraint) → TC-013457 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 696409 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 326397 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 32595322 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3840](../traceability/segment-130.md) → SC-9214 → TC-013457 → `TC-013457.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-013458 — Message_Field_Constraint — Financial Transaction Request — In the CA Public Key File Load Request message, field 11 is required a

**Purpose:** In the CA Public Key File Load Request message, field 11 is required and must be present. (source [p.212](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=212))

**Derivation path:** Spec [p.212](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=212) (ATL105 2026-3) → ENT-FIELD-CA_PUBLIC_KEY_FILE_LOAD_REQUEST-11 → REQ-SRC-ATL105-PDF-001:3840 → derivation_method = message_field_constraint → SC-9214 (message_field_constraint) → TC-013458 (transaction = Financial Transaction Request)

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
| 108 | Loyalty Program ID | 139233 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 262328 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 20834214 | realistic_amount |  |
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
| 119 | Hardware Version | P7KJZLHF | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | CLI280I5 | format_synthesis |  |
| 119 | Firmware Version | WCXNHZUL | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 730452 | format_synthesis |  |
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
| 132 | Hardware Version | P7KJZLHF | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | CLI280I5 | format_synthesis |  |
| 132 | Firmware Version | WCXNHZUL | format_synthesis |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3840](../traceability/segment-130.md) → SC-9214 → TC-013458 → `TC-013458.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-017×1, QEF-018×1, QEF-019×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-024×1, QEF-026×1, QEF-027×1, QEF-028×1, QEF-030×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-013471 — Message_Field_Constraint — EMV Financial Transaction Request — In the CA Public Key File Load Response message, field 4 is required a

**Purpose:** In the CA Public Key File Load Response message, field 4 is required and must be present. (source [p.213](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=213))

**Derivation path:** Spec [p.213](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=213) (ATL105 2026-3) → ENT-FIELD-CA_PUBLIC_KEY_FILE_LOAD_RESPONSE-4 → REQ-SRC-ATL105-PDF-001:3845 → derivation_method = message_field_constraint → SC-9219 (message_field_constraint) → TC-013471 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 920881 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 791340 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 62927671 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3845](../traceability/segment-130.md) → SC-9219 → TC-013471 → `TC-013471.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-013472 — Message_Field_Constraint — Financial Transaction Request — In the CA Public Key File Load Response message, field 4 is required a

**Purpose:** In the CA Public Key File Load Response message, field 4 is required and must be present. (source [p.213](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=213))

**Derivation path:** Spec [p.213](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=213) (ATL105 2026-3) → ENT-FIELD-CA_PUBLIC_KEY_FILE_LOAD_RESPONSE-4 → REQ-SRC-ATL105-PDF-001:3845 → derivation_method = message_field_constraint → SC-9219 (message_field_constraint) → TC-013472 (transaction = Financial Transaction Request)

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
| 108 | Loyalty Program ID | 399226 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 886756 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 77438081 | realistic_amount |  |
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
| 119 | Hardware Version | SOSBADRZ | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | D9UHK1YM | format_synthesis |  |
| 119 | Firmware Version | 43O70VDN | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 307920 | format_synthesis |  |
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
| 132 | Hardware Version | SOSBADRZ | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | D9UHK1YM | format_synthesis |  |
| 132 | Firmware Version | 43O70VDN | format_synthesis |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3845](../traceability/segment-130.md) → SC-9219 → TC-013472 → `TC-013472.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-017×1, QEF-018×1, QEF-019×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-024×1, QEF-026×1, QEF-027×1, QEF-028×1, QEF-030×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-016365 — Relationship — EMV Financial Transaction Request — The Emv Financial Transaction Request transaction includes EMV Request

**Purpose:** The Emv Financial Transaction Request transaction includes EMV Request Data Segment (Data Segment No. 130). (source [p.217](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=217))

**Derivation path:** Spec [p.217](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=217) (ATL105 2026-3) → REL-ENT-SEG-130-EMV_FINANCIAL_TRANSACTION_REQUEST → REQ-SRC-ATL105-PDF-001:4493 → derivation_method = relationship_derived → SC-9867 (relationship) → TC-016365 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 035419 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 433001 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 53987039 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:4493](../traceability/segment-130.md) → SC-9867 → TC-016365 → `TC-016365.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-016735 — Relationship — EMV Financial Transaction Request — ENT-ELEM-187 depends on EMV Financial Transaction Request; the documen

**Purpose:** ENT-ELEM-187 depends on EMV Financial Transaction Request; the documented dependency must hold. (source [p.469](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=469))

**Derivation path:** Spec [p.469](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=469) (ATL105 2026-3) → REL-ENT-ELEM-187-EMV_Financial_Transaction_Request → REQ-SRC-ATL105-PDF-001:4630 → derivation_method = relationship_derived → SC-10004 (relationship) → TC-016735 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 458029 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 686738 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 49882506 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:4630](../traceability/segment-130.md) → SC-10004 → TC-016735 → `TC-016735.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-016736 — Relationship — Financial Transaction Request — ENT-ELEM-187 depends on EMV Financial Transaction Request; the documen

**Purpose:** ENT-ELEM-187 depends on EMV Financial Transaction Request; the documented dependency must hold. (source [p.469](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=469))

**Derivation path:** Spec [p.469](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=469) (ATL105 2026-3) → REL-ENT-ELEM-187-EMV_Financial_Transaction_Request → REQ-SRC-ATL105-PDF-001:4630 → derivation_method = relationship_derived → SC-10004 (relationship) → TC-016736 (transaction = Financial Transaction Request)

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
| 108 | Loyalty Program ID | 438971 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 207390 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 47534631 | realistic_amount |  |
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
| 119 | Hardware Version | ZZ1HNCFG | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | NUX5Z8GC | format_synthesis |  |
| 119 | Firmware Version | 1NUBBK72 | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 037282 | format_synthesis |  |
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
| 132 | Hardware Version | ZZ1HNCFG | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | NUX5Z8GC | format_synthesis |  |
| 132 | Firmware Version | 1NUBBK72 | format_synthesis |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:4630](../traceability/segment-130.md) → SC-10004 → TC-016736 → `TC-016736.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-017×1, QEF-018×1, QEF-019×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-024×1, QEF-026×1, QEF-027×1, QEF-028×1, QEF-030×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-016737 — Relationship — EMV Financial Transaction Request — ENT-ELEM-187 depends on CA Public Key File Load Response; the document

**Purpose:** ENT-ELEM-187 depends on CA Public Key File Load Response; the documented dependency must hold. (source [p.469](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=469))

**Derivation path:** Spec [p.469](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=469) (ATL105 2026-3) → REL-ENT-ELEM-187-CA_Public_Key_File_Load_Response → REQ-SRC-ATL105-PDF-001:4631 → derivation_method = relationship_derived → SC-10005 (relationship) → TC-016737 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 714859 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 898883 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 02095204 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:4631](../traceability/segment-130.md) → SC-10005 → TC-016737 → `TC-016737.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-016738 — Relationship — Financial Transaction Request — ENT-ELEM-187 depends on CA Public Key File Load Response; the document

**Purpose:** ENT-ELEM-187 depends on CA Public Key File Load Response; the documented dependency must hold. (source [p.469](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=469))

**Derivation path:** Spec [p.469](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=469) (ATL105 2026-3) → REL-ENT-ELEM-187-CA_Public_Key_File_Load_Response → REQ-SRC-ATL105-PDF-001:4631 → derivation_method = relationship_derived → SC-10005 (relationship) → TC-016738 (transaction = Financial Transaction Request)

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
| 108 | Loyalty Program ID | 410611 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 020679 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 26379098 | realistic_amount |  |
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
| 119 | Hardware Version | 47F5DBZ8 | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | E9JXPY9S | format_synthesis |  |
| 119 | Firmware Version | 3XH6BHA8 | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 506909 | format_synthesis |  |
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
| 132 | Hardware Version | 47F5DBZ8 | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | E9JXPY9S | format_synthesis |  |
| 132 | Firmware Version | 3XH6BHA8 | format_synthesis |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:4631](../traceability/segment-130.md) → SC-10005 → TC-016738 → `TC-016738.json`

**Test Solution findings:** QEF-005×1, QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-017×1, QEF-018×1, QEF-019×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-024×1, QEF-026×1, QEF-027×1, QEF-028×1, QEF-030×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-016739 — Relationship — EMV Financial Transaction Request — ENT-ELEM-188 depends on EMV Tag 5F34; the documented dependency must h

**Purpose:** ENT-ELEM-188 depends on EMV Tag 5F34; the documented dependency must hold. (source [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470))

**Derivation path:** Spec [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (ATL105 2026-3) → REL-ENT-ELEM-188-EMV_Tag_5F34 → REQ-SRC-ATL105-PDF-001:4632 → derivation_method = relationship_derived → SC-10006 (relationship) → TC-016739 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 591076 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 723571 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 57183012 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:4632](../traceability/segment-130.md) → SC-10006 → TC-016739 → `TC-016739.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-016740 — Relationship — EMV Financial Transaction Request — ENT-ELEM-190 depends on Data Element No. 190 (EMV Chip Data); the docu

**Purpose:** ENT-ELEM-190 depends on Data Element No. 190 (EMV Chip Data); the documented dependency must hold. (source [p.471](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471))

**Derivation path:** Spec [p.471](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471) (ATL105 2026-3) → REL-ENT-ELEM-190-Data_Element_No._190_EMV_Chip_Data_ → REQ-SRC-ATL105-PDF-001:4633 → derivation_method = relationship_derived → SC-10007 (relationship) → TC-016740 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 166021 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 988406 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 41981745 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:4633](../traceability/segment-130.md) → SC-10007 → TC-016740 → `TC-016740.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-016874 — Relationship — EMV Financial Transaction Request — ENT-FIELD-130-5 depends on EMV Chip Data; the documented dependency mu

**Purpose:** ENT-FIELD-130-5 depends on EMV Chip Data; the documented dependency must hold. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → REL-ENT-FIELD-130-5-EMV_Chip_Data → REQ-SRC-ATL105-PDF-001:4673 → derivation_method = relationship_derived → SC-10047 (relationship) → TC-016874 (transaction = EMV Financial Transaction Request)

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
| 108 | Loyalty Program ID | 934732 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 258177 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 19077215 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:4673](../traceability/segment-130.md) → SC-10047 → TC-016874 → `TC-016874.json`

**Test Solution findings:** QEF-007×1, QEF-012×1, QEF-014×1, QEF-016×1, QEF-018×1, QEF-021×1, QEF-022×1, QEF-023×1, QEF-027×1, QEF-028×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-017357 — Relationship — EMV Financial Transaction Request — The EMV Financial Transaction Request transaction includes EMV Request

**Purpose:** The EMV Financial Transaction Request transaction includes EMV Request Data Segment (Data Segment No. 130). (source [p.201](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=201))

**Derivation path:** Spec [p.201](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=201) (ATL105 2026-3) → ENT-SEG-130 → REQ-SRC-ATL105-PDF-001:4828 → derivation_method = relationship_derived → SC-10202 (relationship) → TC-017357 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 501726 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 129263 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 55255528 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:4828](../traceability/segment-130.md) → SC-10202 → TC-017357 → `TC-017357.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-018607 — Relationship — EMV Financial Transaction Request — Data element 188 accepts documented value '000-099' — Valid range for 

**Purpose:** Data element 188 accepts documented value '000-099' — Valid range for EMV Card Sequence Number supplied by chip card. (source [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470))

**Derivation path:** Spec [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (ATL105 2026-3) → ENT-ELEM-188 → REQ-SRC-ATL105-PDF-001:5290 → derivation_method = relationship_derived → SC-10664 (relationship) → TC-018607 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 568980 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 858107 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 67545664 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:5290](../traceability/segment-130.md) → SC-10664 → TC-018607 → `TC-018607.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-018608 — Relationship — EMV Financial Transaction Request — Data element 188 accepts documented value 'Spaces' — Three spaces if E

**Purpose:** Data element 188 accepts documented value 'Spaces' — Three spaces if EMV Tag 5F34 not provided by chip card. (source [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470))

**Derivation path:** Spec [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (ATL105 2026-3) → ENT-ELEM-188 → REQ-SRC-ATL105-PDF-001:5291 → derivation_method = relationship_derived → SC-10665 (relationship) → TC-018608 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 581626 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 962580 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 79317746 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:5291](../traceability/segment-130.md) → SC-10665 → TC-018608 → `TC-018608.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-018609 — Relationship — EMV Financial Transaction Request — Data element 189 accepts documented value '000-999' — Valid numeric ra

**Purpose:** Data element 189 accepts documented value '000-999' — Valid numeric range for EMV Chip Data Length. (source [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470))

**Derivation path:** Spec [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (ATL105 2026-3) → ENT-ELEM-189 → REQ-SRC-ATL105-PDF-001:5292 → derivation_method = relationship_derived → SC-10666 (relationship) → TC-018609 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 203812 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 301704 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 95596609 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:5292](../traceability/segment-130.md) → SC-10666 → TC-018609 → `TC-018609.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-019591 — Supplemental_Entity — EMV Financial Transaction Request — EMV Request Data Segment: Data Segment No. 130, appears in Data Sectio

**Purpose:** EMV Request Data Segment: Data Segment No. 130, appears in Data Section 3 of EMV Financial Transaction Request, originates at device. (source [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267))

**Derivation path:** Spec [p.267](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=267) (ATL105 2026-3) → ENT-SEG-130 → REQ-SRC-ATL105-PDF-001:5910 → derivation_method = supplemental_entity_derived → SC-11284 (supplemental_entity) → TC-019591 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 502533 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 948465 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 15646472 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:5910](../traceability/segment-130.md) → SC-11284 → TC-019591 → `TC-019591.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020115 — Supplemental_Entity — EMV Financial Transaction Request — CA Public Key File Checksum: Identifies the checksum of the CA Public 

**Purpose:** CA Public Key File Checksum: Identifies the checksum of the CA Public Key File Block. (source [p.469](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=469))

**Derivation path:** Spec [p.469](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=469) (ATL105 2026-3) → ENT-ELEM-187 → REQ-SRC-ATL105-PDF-001:6257 → derivation_method = supplemental_entity_derived → SC-11631 (supplemental_entity) → TC-020115 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 535146 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 273546 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 26330850 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:6257](../traceability/segment-130.md) → SC-11631 → TC-020115 → `TC-020115.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020116 — Supplemental_Entity — Financial Transaction Request — CA Public Key File Checksum: Identifies the checksum of the CA Public 

**Purpose:** CA Public Key File Checksum: Identifies the checksum of the CA Public Key File Block. (source [p.469](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=469))

**Derivation path:** Spec [p.469](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=469) (ATL105 2026-3) → ENT-ELEM-187 → REQ-SRC-ATL105-PDF-001:6257 → derivation_method = supplemental_entity_derived → SC-11631 (supplemental_entity) → TC-020116 (transaction = Financial Transaction Request)

**Preconditions:** transaction type Financial Transaction Request; message layout step 1 Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 110, 111, 113, 114, 119, 123, 132, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 602549 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 491538 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 01965220 | realistic_amount |  |
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
| 119 | Hardware Version | TYWX2PJM | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | B954XDGD | format_synthesis |  |
| 119 | Firmware Version | RHNT9QWN | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 928231 | format_synthesis |  |
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
| 132 | Hardware Version | TYWX2PJM | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | B954XDGD | format_synthesis |  |
| 132 | Firmware Version | RHNT9QWN | format_synthesis |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:6257](../traceability/segment-130.md) → SC-11631 → TC-020116 → `TC-020116.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020117 — Supplemental_Entity — EMV Financial Transaction Request — CA Public Key File Checksum: Checksum provided in CA Public Key File L

**Purpose:** CA Public Key File Checksum: Checksum provided in CA Public Key File Load Response, used for subsequent EMV transactions. (source [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470))

**Derivation path:** Spec [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (ATL105 2026-3) → ENT-ELEM-187 → REQ-SRC-ATL105-PDF-001:6258 → derivation_method = supplemental_entity_derived → SC-11632 (supplemental_entity) → TC-020117 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 248866 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 245900 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 15166794 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:6258](../traceability/segment-130.md) → SC-11632 → TC-020117 → `TC-020117.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020118 — Supplemental_Entity — Financial Transaction Request — CA Public Key File Checksum: Checksum provided in CA Public Key File L

**Purpose:** CA Public Key File Checksum: Checksum provided in CA Public Key File Load Response, used for subsequent EMV transactions. (source [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470))

**Derivation path:** Spec [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (ATL105 2026-3) → ENT-ELEM-187 → REQ-SRC-ATL105-PDF-001:6258 → derivation_method = supplemental_entity_derived → SC-11632 (supplemental_entity) → TC-020118 (transaction = Financial Transaction Request)

**Preconditions:** transaction type Financial Transaction Request; message layout step 1 Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 110, 111, 113, 114, 119, 123, 132, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 751459 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 994755 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 99953488 | realistic_amount |  |
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
| 119 | Hardware Version | 4DP49DH1 | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | 15SPY1DG | format_synthesis |  |
| 119 | Firmware Version | 8NAG70XW | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 561182 | format_synthesis |  |
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
| 132 | Hardware Version | 4DP49DH1 | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | 15SPY1DG | format_synthesis |  |
| 132 | Firmware Version | 8NAG70XW | format_synthesis |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:6258](../traceability/segment-130.md) → SC-11632 → TC-020118 → `TC-020118.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020119 — Supplemental_Entity — EMV Financial Transaction Request — EMV Card Sequence Number: Identifies card sequence number from EMV chi

**Purpose:** EMV Card Sequence Number: Identifies card sequence number from EMV chip to differentiate cards with same PAN. (source [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470))

**Derivation path:** Spec [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (ATL105 2026-3) → ENT-ELEM-188 → REQ-SRC-ATL105-PDF-001:6259 → derivation_method = supplemental_entity_derived → SC-11633 (supplemental_entity) → TC-020119 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 268390 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 511540 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 54749398 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:6259](../traceability/segment-130.md) → SC-11633 → TC-020119 → `TC-020119.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020120 — Supplemental_Entity — EMV Financial Transaction Request — EMV Chip Data Length: Identifies the length of the following EMV Chip 

**Purpose:** EMV Chip Data Length: Identifies the length of the following EMV Chip Data field. (source [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470))

**Derivation path:** Spec [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (ATL105 2026-3) → ENT-ELEM-189 → REQ-SRC-ATL105-PDF-001:6260 → derivation_method = supplemental_entity_derived → SC-11634 (supplemental_entity) → TC-020120 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 742957 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 166008 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 80036846 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:6260](../traceability/segment-130.md) → SC-11634 → TC-020120 → `TC-020120.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020121 — Supplemental_Entity — EMV Financial Transaction Request — EMV Chip Data: Identifies the data sent in the EMV card to terminal in

**Purpose:** EMV Chip Data: Identifies the data sent in the EMV card to terminal interface via TLV combinations. (source [p.471](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471))

**Derivation path:** Spec [p.471](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471) (ATL105 2026-3) → ENT-ELEM-190 → REQ-SRC-ATL105-PDF-001:6261 → derivation_method = supplemental_entity_derived → SC-11635 (supplemental_entity) → TC-020121 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 858860 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 744102 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 98599752 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:6261](../traceability/segment-130.md) → SC-11635 → TC-020121 → `TC-020121.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020299 — Supplemental_Entity — EMV Financial Transaction Request — Card Sequence Number: Card sequence number field in Visa payWave data 

**Purpose:** Card Sequence Number: Card sequence number field in Visa payWave data layout. (source [p.553](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=553))

**Derivation path:** Spec [p.553](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=553) (ATL105 2026-3) → ENT-ELEM-188 → REQ-SRC-ATL105-PDF-001:6400 → derivation_method = supplemental_entity_derived → SC-11774 (supplemental_entity) → TC-020299 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 208679 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 295749 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 14022242 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:6400](../traceability/segment-130.md) → SC-11774 → TC-020299 → `TC-020299.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020788 — Narrative_Rule — EMV Financial Transaction Request — CA Public Key File Checksum must be required in the EMV Financial Tran

**Purpose:** CA Public Key File Checksum must be required in the EMV Financial Transaction Request. (source [p.469](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=469))

**Derivation path:** Spec [p.469](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=469) (ATL105 2026-3) → ENT-ELEM-187 → REQ-SRC-ATL105-PDF-001:6845 → derivation_method = llm_phrased → SC-12219 (narrative_rule) → TC-020788 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 943801 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 525810 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 02512093 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:6845](../traceability/segment-130.md) → SC-12219 → TC-020788 → `TC-020788.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020789 — Narrative_Rule — Financial Transaction Request — CA Public Key File Checksum must be required in the EMV Financial Tran

**Purpose:** CA Public Key File Checksum must be required in the EMV Financial Transaction Request. (source [p.469](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=469))

**Derivation path:** Spec [p.469](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=469) (ATL105 2026-3) → ENT-ELEM-187 → REQ-SRC-ATL105-PDF-001:6845 → derivation_method = llm_phrased → SC-12219 (narrative_rule) → TC-020789 (transaction = Financial Transaction Request)

**Preconditions:** transaction type Financial Transaction Request; message layout step 1 Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 110, 111, 113, 114, 119, 123, 132, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 354925 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 370069 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 39733045 | realistic_amount |  |
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
| 119 | Hardware Version | N460PD6O | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | NR2IKF41 | format_synthesis |  |
| 119 | Firmware Version | BYCLH12Y | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 535982 | format_synthesis |  |
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
| 132 | Hardware Version | N460PD6O | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | NR2IKF41 | format_synthesis |  |
| 132 | Firmware Version | BYCLH12Y | format_synthesis |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:6845](../traceability/segment-130.md) → SC-12219 → TC-020789 → `TC-020789.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020790 — Narrative_Rule — EMV Financial Transaction Request — Card Sequence Number must be required when card product includes Card 

**Purpose:** Card Sequence Number must be required when card product includes Card Sequence Number in EMV data (source [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470))

**Derivation path:** Spec [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (ATL105 2026-3) → ENT-ELEM-188 → REQ-SRC-ATL105-PDF-001:6846 → derivation_method = llm_phrased → SC-12220 (narrative_rule) → TC-020790 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 910892 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 354750 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 10748396 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:6846](../traceability/segment-130.md) → SC-12220 → TC-020790 → `TC-020790.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020791 — Narrative_Rule — EMV Financial Transaction Request — EMV Chip Data Length must be present in all EMV transactions

**Purpose:** EMV Chip Data Length must be present in all EMV transactions (source [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470))

**Derivation path:** Spec [p.470](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=470) (ATL105 2026-3) → ENT-ELEM-189 → REQ-SRC-ATL105-PDF-001:6847 → derivation_method = llm_phrased → SC-12221 (narrative_rule) → TC-020791 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 722895 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 485876 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 58112689 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:6847](../traceability/segment-130.md) → SC-12221 → TC-020791 → `TC-020791.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020792 — Narrative_Rule — EMV Financial Transaction Request — EMV Chip Data must be present in all EMV transactions

**Purpose:** EMV Chip Data must be present in all EMV transactions (source [p.471](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471))

**Derivation path:** Spec [p.471](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=471) (ATL105 2026-3) → ENT-ELEM-190 → REQ-SRC-ATL105-PDF-001:6848 → derivation_method = llm_phrased → SC-12222 (narrative_rule) → TC-020792 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 682546 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 503918 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 12365977 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:6848](../traceability/segment-130.md) → SC-12222 → TC-020792 → `TC-020792.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020969 — Business_Rule — EMV Financial Transaction Request — If device is EMV enabled, card is chip, and card is swiped, always sen

**Purpose:** If device is EMV enabled, card is chip, and card is swiped, always send Fallback Entry Mode of '80' or spec equivalent. (source [p.159](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=159))

**Derivation path:** Spec [p.159](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=159) (ATL105 2026-3) → BR-159-3 → REQ-SRC-ATL105-PDF-001:982 → derivation_method = business_rule_derived → SC-6356 (business_rule) → TC-020969 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 084482 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 301148 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 55091297 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:982](../traceability/segment-130.md) → SC-6356 → TC-020969 → `TC-020969.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-020970 — Business_Rule — Financial Transaction Request — If card is NOT chip or device is not chip capable, and card is swiped,

**Purpose:** If card is NOT chip or device is not chip capable, and card is swiped, send as MSR Entry Mode of '90' or spec equivalent. (source [p.159](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=159))

**Derivation path:** Spec [p.159](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=159) (ATL105 2026-3) → BR-159-4 → REQ-SRC-ATL105-PDF-001:983 → derivation_method = business_rule_derived → SC-6357 (business_rule) → TC-020970 (transaction = Financial Transaction Request)

**Preconditions:** transaction type Financial Transaction Request; message layout step 1 Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 110, 111, 113, 114, 119, 123, 132, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 740122 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 551566 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 99100454 | realistic_amount |  |
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
| 119 | Hardware Version | X69SH0CN | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 119 | Software Version | YFS6HSAU | format_synthesis |  |
| 119 | Firmware Version | DZLTIPGX | format_synthesis |  |
| 119 | Sequence Number | 000001 | qe_example_value |  |
| 119 | Device Card Table Version | 368450 | format_synthesis |  |
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
| 132 | Hardware Version | X69SH0CN | format_synthesis | ⚠ AMBIGUOUS_LENGTH, UNTRUSTED_VALID_VALUES |
| 132 | Software Version | YFS6HSAU | format_synthesis |  |
| 132 | Firmware Version | DZLTIPGX | format_synthesis |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:983](../traceability/segment-130.md) → SC-6357 → TC-020970 → `TC-020970.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021015 — Business_Rule — EMV Financial Transaction Request — Amount Authorized Tag fixed value is 9F02.

**Purpose:** Amount Authorized Tag fixed value is 9F02. (source [p.553](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=553))

**Derivation path:** Spec [p.553](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=553) (ATL105 2026-3) → BR-553-5 → REQ-SRC-ATL105-PDF-001:2498 → derivation_method = business_rule_derived → SC-7872 (business_rule) → TC-021015 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 310358 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 203470 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 30922670 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2498](../traceability/segment-130.md) → SC-7872 → TC-021015 → `TC-021015.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021016 — Business_Rule — EMV Financial Transaction Request — Amount Authorized Len fixed length is 12.

**Purpose:** Amount Authorized Len fixed length is 12. (source [p.553](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=553))

**Derivation path:** Spec [p.553](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=553) (ATL105 2026-3) → BR-553-6 → REQ-SRC-ATL105-PDF-001:2499 → derivation_method = business_rule_derived → SC-7873 (business_rule) → TC-021016 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 146824 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 239740 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 85608959 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2499](../traceability/segment-130.md) → SC-7873 → TC-021016 → `TC-021016.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021017 — Business_Rule — EMV Financial Transaction Request — Application Cryptogram Tag fixed value is 9F26.

**Purpose:** Application Cryptogram Tag fixed value is 9F26. (source [p.553](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=553))

**Derivation path:** Spec [p.553](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=553) (ATL105 2026-3) → BR-553-7 → REQ-SRC-ATL105-PDF-001:2500 → derivation_method = business_rule_derived → SC-7874 (business_rule) → TC-021017 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 418410 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 869660 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 72320863 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2500](../traceability/segment-130.md) → SC-7874 → TC-021017 → `TC-021017.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021018 — Business_Rule — EMV Financial Transaction Request — Application Cryptogram Len fixed length is 16.

**Purpose:** Application Cryptogram Len fixed length is 16. (source [p.553](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=553))

**Derivation path:** Spec [p.553](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=553) (ATL105 2026-3) → BR-553-8 → REQ-SRC-ATL105-PDF-001:2501 → derivation_method = business_rule_derived → SC-7875 (business_rule) → TC-021018 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 026831 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 467673 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 91515456 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2501](../traceability/segment-130.md) → SC-7875 → TC-021018 → `TC-021018.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021019 — Business_Rule — EMV Financial Transaction Request — Application Transaction Counter Tag fixed value is 9F36.

**Purpose:** Application Transaction Counter Tag fixed value is 9F36. (source [p.553](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=553))

**Derivation path:** Spec [p.553](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=553) (ATL105 2026-3) → BR-553-9 → REQ-SRC-ATL105-PDF-001:2502 → derivation_method = business_rule_derived → SC-7876 (business_rule) → TC-021019 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 146050 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 153532 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 11591691 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2502](../traceability/segment-130.md) → SC-7876 → TC-021019 → `TC-021019.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021020 — Business_Rule — EMV Financial Transaction Request — Application Transaction Counter Len fixed length is 04.

**Purpose:** Application Transaction Counter Len fixed length is 04. (source [p.553](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=553))

**Derivation path:** Spec [p.553](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=553) (ATL105 2026-3) → BR-553-10 → REQ-SRC-ATL105-PDF-001:2503 → derivation_method = business_rule_derived → SC-7877 (business_rule) → TC-021020 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 522911 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 014138 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 02407806 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2503](../traceability/segment-130.md) → SC-7877 → TC-021020 → `TC-021020.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021036 — Business_Rule — EMV Financial Transaction Request — Final Amount Indicator value 1 allowed only on EMV initial transaction

**Purpose:** Final Amount Indicator value 1 allowed only on EMV initial transactions where final Settlement Amount may differ. (source [p.577](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=577))

**Derivation path:** Spec [p.577](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=577) (ATL105 2026-3) → BR-577-1 → REQ-SRC-ATL105-PDF-001:2596 → derivation_method = business_rule_derived → SC-7970 (business_rule) → TC-021036 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 643167 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 487959 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 38067042 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2596](../traceability/segment-130.md) → SC-7970 → TC-021036 → `TC-021036.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021037 — Business_Rule — EMV Financial Transaction Request — Final Amount Indicator should NOT be sent when final Settlement Amount

**Purpose:** Final Amount Indicator should NOT be sent when final Settlement Amount will be same as original Authorization Amount. (source [p.577](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=577))

**Derivation path:** Spec [p.577](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=577) (ATL105 2026-3) → BR-577-2 → REQ-SRC-ATL105-PDF-001:2597 → derivation_method = business_rule_derived → SC-7971 (business_rule) → TC-021037 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 578416 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 185715 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 81012342 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:2597](../traceability/segment-130.md) → SC-7971 → TC-021037 → `TC-021037.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021084 — Business_Rule — EMV Financial Transaction Request — Security Condition value 3 indicates electronic commerce transaction w

**Purpose:** Security Condition value 3 indicates electronic commerce transaction with digital signature. (source [p.689](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=689))

**Derivation path:** Spec [p.689](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=689) (ATL105 2026-3) → BR-689-2 → REQ-SRC-ATL105-PDF-001:3073 → derivation_method = business_rule_derived → SC-8447 (business_rule) → TC-021084 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 298972 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 865574 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 83144336 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3073](../traceability/segment-130.md) → SC-8447 → TC-021084 → `TC-021084.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021095 — Business_Rule — EMV Request Transaction — EMVYES indicates device is able to receive EMV Table Data; sent only i

**Purpose:** EMVYES indicates device is able to receive EMV Table Data; sent only in an EMV Transaction request. (source [p.694](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=694))

**Derivation path:** Spec [p.694](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=694) (ATL105 2026-3) → BR-694-1 → REQ-SRC-ATL105-PDF-001:3086 → derivation_method = business_rule_derived → SC-8460 (business_rule) → TC-021095 (transaction = EMV Request Transaction)

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
| 108 | Loyalty Program ID | 533632 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 213246 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 56228398 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3086](../traceability/segment-130.md) → SC-8460 → TC-021095 → `TC-021095.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021096 — Business_Rule — EMV Request Transaction — EMVNOT indicates device is unable to receive EMV Table Data; sent only

**Purpose:** EMVNOT indicates device is unable to receive EMV Table Data; sent only in an EMV Transaction request. (source [p.694](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=694))

**Derivation path:** Spec [p.694](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=694) (ATL105 2026-3) → BR-694-2 → REQ-SRC-ATL105-PDF-001:3087 → derivation_method = business_rule_derived → SC-8461 (business_rule) → TC-021096 (transaction = EMV Request Transaction)

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
| 108 | Loyalty Program ID | 204752 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 300160 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 04762459 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:3087](../traceability/segment-130.md) → SC-8461 → TC-021096 → `TC-021096.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021159 — Relationship — EMV Financial Transaction Request — Data element Prompt Token accepts documented value 'DF10 (AUID)' — Voy

**Purpose:** Data element Prompt Token accepts documented value 'DF10 (AUID)' — Voyager EMV prompt token DF10 identifies Voyager ID field, max length 6. (source [p.289](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=289))

**Derivation path:** Spec [p.289](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=289) (ATL105 2026-3) → no source rule → REQ-SRC-ATL105-PDF-001:4920 → derivation_method = relationship_derived → SC-10294 (relationship) → TC-021159 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 294938 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 794546 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 51050832 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:4920](../traceability/segment-130.md) → SC-10294 → TC-021159 → `TC-021159.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021160 — Relationship — EMV Financial Transaction Request — Data element Prompt Token accepts documented value 'DF11 (VHID)' — Voy

**Purpose:** Data element Prompt Token accepts documented value 'DF11 (VHID)' — Voyager EMV prompt token DF11 identifies Vehicle ID field, max length 6. (source [p.289](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=289))

**Derivation path:** Spec [p.289](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=289) (ATL105 2026-3) → no source rule → REQ-SRC-ATL105-PDF-001:4921 → derivation_method = relationship_derived → SC-10295 (relationship) → TC-021160 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 211543 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 882535 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 81993587 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:4921](../traceability/segment-130.md) → SC-10295 → TC-021160 → `TC-021160.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021161 — Relationship — EMV Financial Transaction Request — Data element Prompt Token accepts documented value 'DF12 (DRID)' — Voy

**Purpose:** Data element Prompt Token accepts documented value 'DF12 (DRID)' — Voyager EMV prompt token DF12 identifies Driver ID field, max length 6. (source [p.289](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=289))

**Derivation path:** Spec [p.289](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=289) (ATL105 2026-3) → no source rule → REQ-SRC-ATL105-PDF-001:4922 → derivation_method = relationship_derived → SC-10296 (relationship) → TC-021161 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 218549 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 580346 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 11767356 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:4922](../traceability/segment-130.md) → SC-10296 → TC-021161 → `TC-021161.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021162 — Relationship — EMV Financial Transaction Request — Data element Prompt Token accepts documented value 'DF13 (ODRD)' — Voy

**Purpose:** Data element Prompt Token accepts documented value 'DF13 (ODRD)' — Voyager EMV prompt token DF13 identifies Odometer field, max length 6. (source [p.289](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=289))

**Derivation path:** Spec [p.289](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=289) (ATL105 2026-3) → no source rule → REQ-SRC-ATL105-PDF-001:4923 → derivation_method = relationship_derived → SC-10297 (relationship) → TC-021162 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 915445 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 807087 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 29770713 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:4923](../traceability/segment-130.md) → SC-10297 → TC-021162 → `TC-021162.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021163 — Relationship — EMV Financial Transaction Request — Data element Prompt Token accepts documented value 'DF20 (TRLR)' — Voy

**Purpose:** Data element Prompt Token accepts documented value 'DF20 (TRLR)' — Voyager EMV prompt token DF20 identifies Trailer ID field, max length 10. (source [p.289](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=289))

**Derivation path:** Spec [p.289](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=289) (ATL105 2026-3) → no source rule → REQ-SRC-ATL105-PDF-001:4924 → derivation_method = relationship_derived → SC-10298 (relationship) → TC-021163 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 762629 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 429613 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 13380146 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:4924](../traceability/segment-130.md) → SC-10298 → TC-021163 → `TC-021163.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021164 — Relationship — EMV Financial Transaction Request — Data element Prompt Token accepts documented value 'DF21 (HRRD)' — Voy

**Purpose:** Data element Prompt Token accepts documented value 'DF21 (HRRD)' — Voyager EMV prompt token DF21 identifies Reefer Hours field, max length 7. (source [p.289](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=289))

**Derivation path:** Spec [p.289](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=289) (ATL105 2026-3) → no source rule → REQ-SRC-ATL105-PDF-001:4925 → derivation_method = relationship_derived → SC-10299 (relationship) → TC-021164 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 083188 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 888162 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 81560251 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:4925](../traceability/segment-130.md) → SC-10299 → TC-021164 → `TC-021164.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021165 — Relationship — EMV Financial Transaction Request — Data element Prompt Token accepts documented value 'DF22 (TRIP)' — Voy

**Purpose:** Data element Prompt Token accepts documented value 'DF22 (TRIP)' — Voyager EMV prompt token DF22 identifies Trip Number field, max length 10. (source [p.289](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=289))

**Derivation path:** Spec [p.289](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=289) (ATL105 2026-3) → no source rule → REQ-SRC-ATL105-PDF-001:4926 → derivation_method = relationship_derived → SC-10300 (relationship) → TC-021165 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 726440 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 031867 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 56338800 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:4926](../traceability/segment-130.md) → SC-10300 → TC-021165 → `TC-021165.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021166 — Relationship — EMV Financial Transaction Request — Data element Prompt Token accepts documented value 'DF23 (EXID)' — Voy

**Purpose:** Data element Prompt Token accepts documented value 'DF23 (EXID)' — Voyager EMV prompt token DF23 identifies Extended Driver ID field, max length 20. (source [p.289](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=289))

**Derivation path:** Spec [p.289](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=289) (ATL105 2026-3) → no source rule → REQ-SRC-ATL105-PDF-001:4927 → derivation_method = relationship_derived → SC-10301 (relationship) → TC-021166 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 418877 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 532508 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 49145515 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:4927](../traceability/segment-130.md) → SC-10301 → TC-021166 → `TC-021166.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021167 — Relationship — EMV Financial Transaction Request — Data element Prompt Token accepts documented value 'DF24 (UNIT)' — Voy

**Purpose:** Data element Prompt Token accepts documented value 'DF24 (UNIT)' — Voyager EMV prompt token DF24 identifies Unit ID field, max length 10. (source [p.289](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=289))

**Derivation path:** Spec [p.289](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=289) (ATL105 2026-3) → no source rule → REQ-SRC-ATL105-PDF-001:4928 → derivation_method = relationship_derived → SC-10302 (relationship) → TC-021167 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 872894 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 604411 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 28921758 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:4928](../traceability/segment-130.md) → SC-10302 → TC-021167 → `TC-021167.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021168 — Relationship — EMV Financial Transaction Request — Data element Prompt Token accepts documented value 'DF25 (PONB)' — Voy

**Purpose:** Data element Prompt Token accepts documented value 'DF25 (PONB)' — Voyager EMV prompt token DF25 identifies Contract/PO field, max length 10. (source [p.289](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=289))

**Derivation path:** Spec [p.289](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=289) (ATL105 2026-3) → no source rule → REQ-SRC-ATL105-PDF-001:4929 → derivation_method = relationship_derived → SC-10303 (relationship) → TC-021168 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 322732 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 381804 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 21612065 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:4929](../traceability/segment-130.md) → SC-10303 → TC-021168 → `TC-021168.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021169 — Relationship — EMV Financial Transaction Request — Data element Prompt Token accepts documented value 'DF26 (DLIC)' — Voy

**Purpose:** Data element Prompt Token accepts documented value 'DF26 (DLIC)' — Voyager EMV prompt token DF26 identifies Driver License field, max length 20. (source [p.289](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=289))

**Derivation path:** Spec [p.289](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=289) (ATL105 2026-3) → no source rule → REQ-SRC-ATL105-PDF-001:4930 → derivation_method = relationship_derived → SC-10304 (relationship) → TC-021169 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 830501 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 492401 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 15035596 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:4930](../traceability/segment-130.md) → SC-10304 → TC-021169 → `TC-021169.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021207 — Supplemental_Entity — EMV Financial Transaction Request — Amount Authorized Tag: Fixed value: 9F02.

**Purpose:** Amount Authorized Tag: Fixed value: 9F02. (source [p.553](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=553))

**Derivation path:** Spec [p.553](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=553) (ATL105 2026-3) → ENT-CONCEPT-553-AMOUNT-AUTHORIZED-TAG → REQ-SRC-ATL105-PDF-001:6404 → derivation_method = supplemental_entity_derived → SC-11778 (supplemental_entity) → TC-021207 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 061772 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 398622 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 59490089 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:6404](../traceability/segment-130.md) → SC-11778 → TC-021207 → `TC-021207.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021208 — Supplemental_Entity — EMV Financial Transaction Request — Amount Authorized Len: Fixed length: 12.

**Purpose:** Amount Authorized Len: Fixed length: 12. (source [p.553](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=553))

**Derivation path:** Spec [p.553](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=553) (ATL105 2026-3) → ENT-CONCEPT-553-AMOUNT-AUTHORIZED-LEN → REQ-SRC-ATL105-PDF-001:6405 → derivation_method = supplemental_entity_derived → SC-11779 (supplemental_entity) → TC-021208 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 447866 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 102256 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 48265054 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:6405](../traceability/segment-130.md) → SC-11779 → TC-021208 → `TC-021208.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?

## TC-021209 — Supplemental_Entity — EMV Financial Transaction Request — Amount Authorized: Amount used to generate cryptogram.

**Purpose:** Amount Authorized: Amount used to generate cryptogram. (source [p.553](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=553))

**Derivation path:** Spec [p.553](../../../../../docs/specs/BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-3.pdf#page=553) (ATL105 2026-3) → ENT-CONCEPT-553-AMOUNT-AUTHORIZED → REQ-SRC-ATL105-PDF-001:6406 → derivation_method = supplemental_entity_derived → SC-11780 (supplemental_entity) → TC-021209 (transaction = EMV Financial Transaction Request)

**Preconditions:** transaction type EMV Financial Transaction Request; message layout step 1 EMV Financial Transaction Request segments 100, 101, 102, 103, 104, 108, 111, 114, 123, 130, 135, 143, 145, 146, 151, 153, 156

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
| 108 | Loyalty Program ID | 808194 | format_synthesis | ⚠ UNTRUSTED_VALID_VALUES |
| 108 | Points to Redeem | 131483 | format_synthesis |  |
| 108 | Coupon ID | ‹masked, 19 chars› | format_synthesis |  |
| 108 | Coupon Amount | 17725521 | realistic_amount |  |
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

**Traceability:** [REQ-SRC-ATL105-PDF-001:6406](../traceability/segment-130.md) → SC-11780 → TC-021209 → `TC-021209.json`

**Test Solution findings:** QEF-004×1, QEF-007×1, QEF-012×1, QEF-014×1

**Reviewer checklist (SME):**

- [ ] Does the BR statement match the cited ATL105 page and segment?
- [ ] Is the scenario/test design (positive, negative, boundary or flow) appropriate for this BR?
- [ ] Are all segments and fields required for this transaction present with valid values?
- [ ] Is the expected response/decline code correct for this transaction context?
- [ ] Can this test case be executed as-is without further data?
