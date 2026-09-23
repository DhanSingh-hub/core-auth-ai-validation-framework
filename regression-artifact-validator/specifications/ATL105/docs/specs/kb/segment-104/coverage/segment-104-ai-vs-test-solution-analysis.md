# Segment 104 — Test Solution Coverage of AI Solution BRs

**Generated:** 2026-09-22T18:43:51+05:30

## Question

> The AI Solution produced **25** Segment 104 BRs; our Test Solution has **14** Segment 104 rules (`SEG104-R-001`..`SEG104-R-020`). Does the Test Solution semantically cover all 25 AI BRs?

This analysis answers that question using **many-to-many semantic coverage predicates** — one AI BR can be covered by multiple Test Solution rules, and one Test Solution rule can enforce multiple AI BRs.

## Bottom Line

- **24 of 25** AI BRs are semantically covered by at least one Test Solution rule (96.0%).
- **1 of 25** AI BRs are NOT covered by any Test Solution rule.
- **5 of 25** AI BRs are covered by 2 or more Test Solution rules (these are compound BRs that legitimately merit multiple rules).
- **1 of 14** Test Solution rules have no AI BR support (the AI Solution did not derive that specific rule).

## Coverage by AI Derivation Method

| AI derivation method | AI BR count | Covered | Not covered | Coverage % |
|---|---:|---:|---:|---:|
| `business_rule_derived` | 12 | 11 | 1 | 91.7% |
| `field_constraint` | 10 | 10 | 0 | 100.0% |
| `field_fixed_value` | 1 | 1 | 0 | 100.0% |
| `relationship_derived` | 2 | 2 | 0 | 100.0% |
| **Total** | **25** | **24** | **1** | **96.0%** |

## Test Solution Rule → AI BR Coverage

How many AI BRs each Test Solution rule enforces.

| Rule | Title | Class | AI BRs enforced |
|---|---|---|---:|
| `SEG104-R-001` | Purchase Card Data Segment is a Data Section 3 companion of Segment 100. | structure | 7 |
| `SEG104-R-002` | Purchase Card Data Segment object is required in the document being validated. | applicability | 6 |
| `SEG104-R-004` | Segment Type is 104. | field | 3 |
| `SEG104-R-005` | Segment Length is 3 digits representing the content length. | field | 2 |
| `SEG104-R-006` | Purchase Card Data Segment max length is 86 alphanumeric characters. | serialization | 3 |
| `SEG104-R-007` | Purchase Code is alphanumeric, max 16 characters, when populated. | field | 1 |
| `SEG104-R-008` | PC Tax Amount is numeric, up to 7 digits with 2 assumed decimal places, when populated. | field | 1 |
| `SEG104-R-009` | PC Freight Amount is numeric, up to 7 digits with 2 assumed decimal places, when popula... | field | 1 |
| `SEG104-R-010` | PC Duty Amount is numeric, up to 7 digits with 2 assumed decimal places, when populated. | field | 2 |
| `SEG104-R-011` | Ship-to Country Code is a fixed length of 3 digits, when populated. | field | 1 |
| `SEG104-R-012` | Ship-to Postal Code format is PROVISIONAL (P-01): strict US ZIP+4 or general alphanumer... | field | 1 |
| `SEG104-R-013` | Ship-from Postal Code format is PROVISIONAL (P-01): strict US ZIP+4 or general alphanum... | field | 1 |
| `SEG104-R-014` | Direct Marketing Invoice Number is alphanumeric, max 10 characters, when populated. | field | 1 |
| `SEG104-R-020` | At most one Segment 104 per message. PROVISIONAL (P-02): not explicitly stated in the s... | structure | 0 |

### Test Solution rules with NO AI BR support

These rules are in the Test Solution catalog but the AI Solution did NOT emit a BR for them.

- **`SEG104-R-020` — At most one Segment 104 per message. PROVISIONAL (P-02): not explicitly stated in the spec text the way Segment 101's cardinality is; enforced by analogy to the Data Section 3 slot structure pending SME confirmation.**

## AI BR → Test Solution Rule(s) — Full Coverage Table

| # | AI BR ID | Source | Method | Statement (first 120 chars) | Covered by rules |
|---:|---|---|---|---|---|
| 1 | `REQ-SRC-ATL105-PDF-001:937` | `BR-163-3` | business_rule_derived | Data Section No. 3 of a Financial Transaction Request contains none, one, or more of the following data segments: Fleet  | `SEG104-R-001`, `SEG104-R-002` |
| 2 | `REQ-SRC-ATL105-PDF-001:944` | `BR-165-4` | business_rule_derived | Purchase Card Data Segment (104) is sent only on transactions requiring purchase card data. | `SEG104-R-002` |
| 3 | `REQ-SRC-ATL105-PDF-001:950` | `BR-165-10` | business_rule_derived | Segments 101–145 in Financial Transaction Request occupy Field Nos. 4–9 with specified maximum lengths (101:308, 102:381 | `SEG104-R-001`, `SEG104-R-002`, `SEG104-R-006` |
| 4 | `REQ-SRC-ATL105-PDF-001:1058` | `BR-201-4` | business_rule_derived | EMV Financial Transaction Request Data Section No. 3 contains one or more of: Fleet (101), Product Code (102), Purchase  | `SEG104-R-001`, `SEG104-R-002` |
| 5 | `REQ-SRC-ATL105-PDF-001:1068` | `BR-203-8` | business_rule_derived | Segment 104 (Purchase Card Data Segment) is sent only on transactions requiring purchase card data. | `SEG104-R-002` |
| 6 | `REQ-SRC-ATL105-PDF-001:1146` | `BR-230-1` | business_rule_derived | The Purchase Card Data Segment has a maximum length of 86 alphanumeric characters (01–86/a–z/A–Z). | `SEG104-R-006` |
| 7 | `REQ-SRC-ATL105-PDF-001:1147` | `BR-230-2` | business_rule_derived | The Purchase Card Data Segment originates at the device and can appear in any of the fields in Data Section No. 3. | `SEG104-R-001` |
| 8 | `REQ-SRC-ATL105-PDF-001:1148` | `BR-230-3` | business_rule_derived | All fields in the Purchase Card Data Segment are separated by Field Separators; when a field is not populated, still sen | _(none)_ |
| 9 | `REQ-SRC-ATL105-PDF-001:1149` | `BR-230-4` | business_rule_derived | Segment Type field is fixed value 104 for the Purchase Card Data Segment. | `SEG104-R-004` |
| 10 | `REQ-SRC-ATL105-PDF-001:1672` | `BR-384-3` | business_rule_derived | For Financial Transaction Requests, zero, one, or more of Data Segments 101 (Fleet), 102 (Product Code), 103 (EBT), 104  | `SEG104-R-001`, `SEG104-R-002` |
| 11 | `REQ-SRC-ATL105-PDF-001:1709` | `BR-391-1` | business_rule_derived | PC Duty Amount is used for Purchase Card Transaction Request. | `SEG104-R-010` |
| 12 | `REQ-SRC-ATL105-PDF-001:1765` | `BR-400-5` | business_rule_derived | Purchase Card Data Segment (No. 104) Segment Length valid values are 001–86. | `SEG104-R-005`, `SEG104-R-006` |
| 13 | `REQ-SRC-ATL105-PDF-001:2949` | `ENT-FIELD-104-1` | field_constraint | In Purchase Card Data Segment (Data Segment No. 104), field 1 (Segment Type) is required and must be present. | `SEG104-R-004` |
| 14 | `REQ-SRC-ATL105-PDF-001:2950` | `ENT-FIELD-104-1` | field_fixed_value | In Purchase Card Data Segment (Data Segment No. 104), field 1 (Segment Type) must equal the fixed value 104. | `SEG104-R-004` |
| 15 | `REQ-SRC-ATL105-PDF-001:2951` | `ENT-FIELD-104-2` | field_constraint | In Purchase Card Data Segment (Data Segment No. 104), field 2 (Segment Length) is required and must be present. | `SEG104-R-005` |
| 16 | `REQ-SRC-ATL105-PDF-001:2952` | `ENT-FIELD-104-3` | field_constraint | In Purchase Card Data Segment (Data Segment No. 104), field 3 (Purchase Code) is conditional and must be present only wh | `SEG104-R-007` |
| 17 | `REQ-SRC-ATL105-PDF-001:2953` | `ENT-FIELD-104-4` | field_constraint | In Purchase Card Data Segment (Data Segment No. 104), field 4 (PC Tax Amount) is conditional and must be present only wh | `SEG104-R-008` |
| 18 | `REQ-SRC-ATL105-PDF-001:2954` | `ENT-FIELD-104-5` | field_constraint | In Purchase Card Data Segment (Data Segment No. 104), field 5 (PC Freight Amount) is conditional and must be present onl | `SEG104-R-009` |
| 19 | `REQ-SRC-ATL105-PDF-001:2955` | `ENT-FIELD-104-6` | field_constraint | In Purchase Card Data Segment (Data Segment No. 104), field 6 (PC Duty Amount) is conditional and must be present only w | `SEG104-R-010` |
| 20 | `REQ-SRC-ATL105-PDF-001:2956` | `ENT-FIELD-104-7` | field_constraint | In Purchase Card Data Segment (Data Segment No. 104), field 7 (Ship-to Country Code) is conditional and must be present  | `SEG104-R-011` |
| 21 | `REQ-SRC-ATL105-PDF-001:2957` | `ENT-FIELD-104-8` | field_constraint | In Purchase Card Data Segment (Data Segment No. 104), field 8 (Ship-to Postal Code) is conditional and must be present o | `SEG104-R-012` |
| 22 | `REQ-SRC-ATL105-PDF-001:2958` | `ENT-FIELD-104-9` | field_constraint | In Purchase Card Data Segment (Data Segment No. 104), field 9 (Ship-from Postal Code) is conditional and must be present | `SEG104-R-013` |
| 23 | `REQ-SRC-ATL105-PDF-001:2959` | `ENT-FIELD-104-10` | field_constraint | In Purchase Card Data Segment (Data Segment No. 104), field 10 (Direct Marketing Invoice Number) is conditional and must | `SEG104-R-014` |
| 24 | `REQ-SRC-ATL105-PDF-001:3701` | `REL-ENT-SEG-104-FINANCIAL_TRANSACTION_REQUEST` | relationship_derived | The Financial Transaction Request transaction includes Purchase Card Data Segment (Data Segment No. 104). | `SEG104-R-001` |
| 25 | `REQ-SRC-ATL105-PDF-001:4008` | `ENT-SEG-104` | relationship_derived | The Financial Transaction Request transaction includes Purchase Card Data Segment (Data Segment No. 104). | `SEG104-R-001` |

## AI BRs NOT Covered by Any Test Solution Rule

These **1** AI BRs are the true coverage gap. For each, we note whether it is a genuine spec rule the Test Solution should adopt, or noise/duplicate.

### `REQ-SRC-ATL105-PDF-001:1148` — BR-230-3 (page 230)
- **Derivation method:** business_rule_derived
- **Confidence:** 0.95 · **Requires review:** False
- **Statement:** All fields in the Purchase Card Data Segment are separated by Field Separators; when a field is not populated, still send the Field Separator.
- **Constraint:** Field Separator must still be sent
- **Related entities:** ENT-SEG-104


## AI BRs Covered by Multiple Test Solution Rules

These **5** AI BRs are compound statements — each legitimately corresponds to more than one rule in the Test Solution catalog.

| AI BR | Statement (120 chars) | Rules |
|---|---|---|
| `REQ-SRC-ATL105-PDF-001:937` | Data Section No. 3 of a Financial Transaction Request contains none, one, or more of the following data segments: Fleet  | `SEG104-R-001`, `SEG104-R-002` |
| `REQ-SRC-ATL105-PDF-001:950` | Segments 101–145 in Financial Transaction Request occupy Field Nos. 4–9 with specified maximum lengths (101:308, 102:381 | `SEG104-R-001`, `SEG104-R-002`, `SEG104-R-006` |
| `REQ-SRC-ATL105-PDF-001:1058` | EMV Financial Transaction Request Data Section No. 3 contains one or more of: Fleet (101), Product Code (102), Purchase  | `SEG104-R-001`, `SEG104-R-002` |
| `REQ-SRC-ATL105-PDF-001:1672` | For Financial Transaction Requests, zero, one, or more of Data Segments 101 (Fleet), 102 (Product Code), 103 (EBT), 104  | `SEG104-R-001`, `SEG104-R-002` |
| `REQ-SRC-ATL105-PDF-001:1765` | Purchase Card Data Segment (No. 104) Segment Length valid values are 001–86. | `SEG104-R-005`, `SEG104-R-006` |

