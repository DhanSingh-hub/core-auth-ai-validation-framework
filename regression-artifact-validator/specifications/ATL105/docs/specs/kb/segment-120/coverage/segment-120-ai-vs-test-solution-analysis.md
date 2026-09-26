# Segment 120 — Test Solution Coverage of AI Solution BRs

**Generated:** 2026-09-23T19:00:00+05:30

## Question

> The AI Solution produced **13** Segment 120-relevant requirements (12 bucketed `SEG-120` + 1 mis-bucketed `UNASSIGNED`); our Test Solution has **8** Segment 120 rules (`SEG120-R-001`..`SEG120-R-008`). Does the Test Solution semantically cover all 13 AI requirements?

This is the inverse direction of the [AI Solution coverage report](segment-120-ai-coverage-report.md): that report asks "does each Test rule have adequate AI-generated *test evidence*?"; this analysis asks "does each AI-generated *requirement statement* map to a Test rule at all?"

## Bottom Line

- **13 of 13** AI requirements are semantically covered by at least one Test Solution rule (**100%**).
- **0 of 13** AI requirements are NOT covered by any Test Solution rule.
- **0 of 8** Test Solution rules have no AI requirement support — every rule in the catalog was derived to explain at least one AI-generated statement.
- Segment 120's small requirement count (13, versus 68 for Segment 101) made a clean 1:1-or-more mapping achievable without any orphaned AI requirement — a smaller surface area is the main reason this coverage percentage is higher than Segment 101's 97.1%, not necessarily a higher-quality AI extraction.

## Coverage by AI Derivation Method

| AI derivation method | AI requirement count | Covered | Not covered | Coverage % |
|---|---:|---:|---:|---:|
| `business_rule_derived` | 7 | 7 | 0 | 100.0% |
| `field_constraint` | 3 | 3 | 0 | 100.0% |
| `field_fixed_value` | 1 | 1 | 0 | 100.0% |
| `relationship_derived` | 2 | 2 | 0 | 100.0% |
| **Total** | **13** | **13** | **0** | **100.0%** |

## Test Solution Rule → AI Requirement Coverage

How many AI requirements each Test Solution rule enforces.

| Rule | Title | Class | AI requirements enforced |
|---|---|---|---:|
| `SEG120-R-001` | Segment Type is 120 | field | 3 |
| `SEG120-R-002` | Segment Length present/numeric/range 001-1009 | field | 2 |
| `SEG120-R-003` | Print Data is required | field | 1 |
| `SEG120-R-004` | Total Segment 120 length <= 1009 characters | serialization | 1 |
| `SEG120-R-005` | Field separators between 1/2 and 2/3 only, no trailing separator | serialization | 1 |
| `SEG120-R-006` | Segment 120 only present in Financial Transaction Response / EMV Financial Transaction Response | applicability | 3 |
| `SEG120-R-007` | Segment 120 is the final segment of Data Section 3 | structure | 1 |
| `SEG120-R-008` | Print Data may contain `\` Blackhawk line delimiter | content | 1 |

### Test Solution rules with NO AI requirement support

None. Every Segment 120 rule in the catalog traces back to at least one AI-generated requirement statement.

## AI Requirement → Test Solution Rule(s) — Full Coverage Table

| # | AI Requirement ID | Source | Method | Statement (first 120 chars) | Covered by rule(s) | Flag |
|---:|---|---|---|---|---|---|
| 1 | `REQ-SRC-ATL105-PDF-001:1257` | `BR-263-1` | business_rule_derived | The Print Data 2 Segment is only sent on Financial Transaction Response messages requiring large amo | `SEG120-R-006` | - |
| 2 | `REQ-SRC-ATL105-PDF-001:1258` | `BR-263-2` | business_rule_derived | The Print Data 2 Segment always appears at the end of a Financial Transaction response. | `SEG120-R-007` | ✅ Resolved 2026-09-23 — authoritative over `atl105_complete_templates.json` |
| 3 | `REQ-SRC-ATL105-PDF-001:1259` | `BR-263-3` | business_rule_derived | The Print Data 2 Segment has a maximum length of 1,009 alphanumeric characters (01–1,009/a-z/A–Z). | `SEG120-R-004` | - |
| 4 | `REQ-SRC-ATL105-PDF-001:1260` | `BR-263-4` | business_rule_derived | There is a Field Separator between Field Nos. 1 and 2 and between Field Nos. 2 and 3 in the Print Da | `SEG120-R-005` | - |
| 5 | `REQ-SRC-ATL105-PDF-001:1261` | `BR-263-5` | business_rule_derived | For Blackhawk phone activation and recharge, the Print Data field contains receipt text with a field | `SEG120-R-008` | ⚠️ Mis-bucketed `UNASSIGNED` by the AI pipeline |
| 6 | `REQ-SRC-ATL105-PDF-001:1262` | `BR-263-6` | business_rule_derived | Segment Type field contains fixed value '120' for the Print Data 2 Segment. | `SEG120-R-001` | - |
| 7 | `REQ-SRC-ATL105-PDF-001:1778` | `BR-400-18` | business_rule_derived | Print Data 2 Segment (No. 120) Segment Length valid values are 001-1009. | `SEG120-R-002` | - |
| 8 | `REQ-SRC-ATL105-PDF-001:3087` | `ENT-FIELD-120-1` | field_constraint | In Print Data 2 Segment (Data Segment No. 120), field 1 (Segment Type) is required and must be prese | `SEG120-R-001` | - |
| 9 | `REQ-SRC-ATL105-PDF-001:3088` | `ENT-FIELD-120-1` | field_fixed_value | In Print Data 2 Segment (Data Segment No. 120), field 1 (Segment Type) must equal the fixed value 12 | `SEG120-R-001` | ⚠️ 0 scenarios / 0 test cases generated for this specific requirement |
| 10 | `REQ-SRC-ATL105-PDF-001:3089` | `ENT-FIELD-120-2` | field_constraint | In Print Data 2 Segment (Data Segment No. 120), field 2 (Segment Length) is required and must be pre | `SEG120-R-002` | - |
| 11 | `REQ-SRC-ATL105-PDF-001:3090` | `ENT-FIELD-120-3` | field_constraint | In Print Data 2 Segment (Data Segment No. 120), field 3 (Print Data) is required and must be present | `SEG120-R-003` | - |
| 12 | `REQ-SRC-ATL105-PDF-001:3711` | `REL-ENT-SEG-120-FINANCIAL_TRANSACTION_RESPONSE` | relationship_derived | The Financial Transaction Response transaction includes Print Data 2 Segment (Data Segment No. 120). | `SEG120-R-006` | - |
| 13 | `REQ-SRC-ATL105-PDF-001:4068` | `ENT-SEG-120` | relationship_derived | The EMV Financial Transaction Response transaction includes Print Data 2 Segment (Data Segment No. 1 | `SEG120-R-006` | - |

## Reading This Alongside the AI Coverage Report

A rule can appear "fully covered" in this table (every AI requirement mapped to a rule) while still being `REVIEW_REQUIRED` or `PARTIALLY_COVERED` in the [AI coverage report](segment-120-ai-coverage-report.md). Semantic mapping and test-evidence adequacy are different questions:

- `SEG120-R-007` maps cleanly to `BR-263-2` here, and (as of 2026-09-23) the ordering question is resolved: the AI Solution's own template data is assessed as a numeric-sort extraction artifact, not a genuine conflict, so `BR-263-2` stands as authoritative and the rule is hard-enforced. It is still `PARTIALLY_COVERED` in the report, not `COVERED`, because both its test cases are positive.
- `SEG120-R-001` maps to three AI requirements here, but the report marks it `PARTIALLY_COVERED` because none of its 46 test cases are negative.

Both files must be read together to reach a certification decision; neither one alone is sufficient.
