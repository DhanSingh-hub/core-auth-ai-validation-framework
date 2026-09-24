# Segment 120 — AI Solution Coverage Report

**Specification:** ATL105 · **Version:** 2026-3 · **Segment:** 120 (Print Data 2 Segment)
**Generated:** 2026-09-23T19:00:00+05:30

This report classifies every Segment 120 rule from the authoritative [rule catalog](segment-120-rule-catalog.json) against evidence produced by the AI Solution pipeline under `src_Harit_Latest_AI_Sol/src/pipeline/`.

## AI Catalog Totals for SEG-120

| Metric | Count |
|---|---:|
| Business Requirements bucketed to SEG-120 | 12 |
| Business Requirements related but mis-bucketed UNASSIGNED (BR-263-5) | 1 |
| Scenarios linked to Segment 120 requirements | 12 |
| Test Cases linked to those scenarios | 106 |
| Test Cases — positive | 106 |
| Test Cases — negative (`violated_element_name` populated) | 0 |
| Test Cases — priority P3 | 106 |

## Coverage Summary

| Status | Count | % |
|---|---:|---:|
| ✅ COVERED | 0 | 0.0% |
| 🟡 PARTIALLY_COVERED | 6 | 75.0% |
| ⚠️ REVIEW_REQUIRED | 2 | 25.0% |
| ❌ MISSING | 0 | 0.0% |
| **Total Rules** | **8** | **100%** |

**Headline coverage: 0.0%** of Segment 120 rules are fully certifiable (`COVERED`) by the AI Solution's current output, because zero negative test cases exist for this segment. Two rules (`SEG120-R-001..007`'s prior blockers, `P-01` and the width part of `P-02`) were **resolved on 2026-09-23** by deeper spec reading — see [Cross-Cutting Findings](#cross-cutting-findings). Only `SEG120-R-004` (a narrower residual arithmetic gap, `P-02-RESIDUAL`) and `SEG120-R-008` (`P-03`) remain `REVIEW_REQUIRED`.

## Rule-Level Coverage

| # | Rule ID | Title | Class | Severity | Status | REQs | Scenarios | Test Cases | PROVISIONAL |
|---:|---|---|---|---|---|---:|---:|---:|---|
| 1 | `SEG120-R-001` | Segment Type is 120 | field | error | 🟡 PARTIALLY_COVERED | 3 | 2 | 46 | - |
| 2 | `SEG120-R-002` | Segment Length present, exactly 4 digits, computed-length equality | field | error | 🟡 PARTIALLY_COVERED | 2 | 2 | 46 | RESOLVED (P-02-WIDTH) |
| 3 | `SEG120-R-003` | Print Data is required | field | error | 🟡 PARTIALLY_COVERED | 1 | 1 | 2 | - |
| 4 | `SEG120-R-004` | Print Data <= 999 chars AND total <= 1009 chars (independent caps) | serialization | error | ⚠️ REVIEW_REQUIRED | 1 | 1 | 2 | P-02-RESIDUAL |
| 5 | `SEG120-R-005` | Field separators between 1/2 and 2/3 only, no trailing separator | serialization | error | 🟡 PARTIALLY_COVERED | 1 | 1 | 2 | - |
| 6 | `SEG120-R-006` | Segment 120 only present in Financial Transaction Response / EMV Financial Transaction Response | applicability | error | 🟡 PARTIALLY_COVERED | 3 | 3 | 4 | - |
| 7 | `SEG120-R-007` | Segment 120 is the final segment of Data Section 3 | structure | error | 🟡 PARTIALLY_COVERED | 1 | 1 | 2 | RESOLVED (P-01) |
| 8 | `SEG120-R-008` | Print Data may contain `\` Blackhawk line delimiter | content | info | ⚠️ REVIEW_REQUIRED | 1 | 1 | 2 | P-03 |

## Cross-Cutting Findings

1. **No negative test cases exist for Segment 120 anywhere in the approved catalog.** All 106 test cases tied to the 12 Segment 120 scenarios are positive (`violated_element_name` is `null` for every one), all priority P3. This alone prevents any error-severity rule from reaching `COVERED` under the same standard applied to Segment 101 — a complete chain without a negative case is `PARTIALLY_COVERED`, not `COVERED`.
2. **A requirement is mis-bucketed.** `REQ-SRC-ATL105-PDF-001:1261` (BR-263-5, the Blackhawk `\` delimiter rule) is tagged `UNASSIGNED` by the pipeline's own segment bucketer, even though its statement, scenario (`SC-1842`), and test cases (`TC-4311`, `TC-4312`) are exclusively about Segment 120 Print Data. It has been included in this report as `SEG120-R-008`.
3. **RESOLVED — the apparent AI Solution self-contradiction on segment ordering (`P-01`).** `BR-263-2` ("Segment 120 always appears at the end") was turned into `SC-1839` / `TC-4305`, `TC-4306` by the same pipeline that also produced `docs/atl105_complete_templates.json`, which places Segment 120 as the 3rd of 9 segments in the Financial Transaction Response template (3rd of 10 in the EMV variant). Re-examination shows the template orders segments in **strict ascending segment-number order** in both cases (112, 115, 120, 134, 136, 146, 148, 152, 155 / EMV: … 120, 131, 134 …) — matching the pattern of the spec's own numeric cross-reference appendices rather than any documented wire-order table. This is assessed as a numeric-sort extraction artifact, not genuine evidence of wire order. **BR-263-2 is treated as authoritative**; `SEG120-R-007` is no longer blocked and the Item 1 validator now hard-enforces "last segment" when an explicit segment order is present in test data.
4. **RESOLVED — Segment Length field width (`P-02-WIDTH`).** `regression-artifact-validator/docs/specs/extracted_text.txt` lines 21313-21340 (Element 84 definition, page 13-66) explicitly lists Segment 120 as one of only seven segments (103, 114, 115, 118, **120**, 130, 131) whose Segment Length is always exactly 4 digits, and states it "cannot have a length of 4 digits when sending any other segment." Segment Length is therefore always zero-padded to 4 digits for Segment 120 — not variable-width as originally suspected.
5. **OPEN — a narrower residual arithmetic gap (`P-02-RESIDUAL`).** `1,009 (total) - 3 (Segment Type) - 4 (Segment Length) - 2 (separators) = 1,000`, not `999` as the field table states for Print Data alone. This 1-character gap between the per-field cap and the total cap remains unresolved and both caps are enforced independently as a safety measure.

## Detailed Findings by Status

### 🟡 PARTIALLY_COVERED — chain incomplete or negative case absent (6)

#### `SEG120-R-001` — Segment Type is 120

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.18 | segment 120 | element 85 | rule `segment-type`
- **AI evidence:** 3 REQ / 2 scenarios / 46 test cases (positive 46, negative 0)
- **AI REQ IDs:** `REQ-SRC-ATL105-PDF-001:1262` (BR-263-6, fixed value), `REQ-SRC-ATL105-PDF-001:3087` (required present), `REQ-SRC-ATL105-PDF-001:3088` (fixed value, field_fixed_value derivation — **0 SC / 0 TC**, its own dedicated evidence is empty)
- **AI Scenario IDs:** `SC-1843`, `SC-3639`
- **AI Test Case sample:** TC-4313, TC-4314, TC-4315 … (23 under SC-1843), TC-12290, TC-12291 … (23 under SC-3639)

#### `SEG120-R-002` — Segment Length present, exactly 4 digits, computed-length equality — `P-02-WIDTH` RESOLVED

- **Class / Severity:** field / error
- **AI evidence:** 2 REQ / 2 scenarios / 46 test cases (positive 46, negative 0)
- **AI REQ IDs:** `REQ-SRC-ATL105-PDF-001:1778` (BR-400-18), `REQ-SRC-ATL105-PDF-001:3089`
- **Resolution:** the field-width question is now settled — Segment Length is always exactly 4 digits (Element 84 spec text, `extracted_text.txt` lines 21313-21340). No longer blocked.
- **Remaining gap:** all 46 test cases use the same width and are positive; no negative case (wrong width, non-numeric, out-of-range value) exists.

#### `SEG120-R-003` — Print Data is required

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.18 | segment 120 | element 152 | rule `print-data-required`
- **AI evidence:** 1 REQ / 1 scenario / 2 test cases (positive 2, negative 0)
- **AI REQ IDs:** `REQ-SRC-ATL105-PDF-001:3090`
- **AI Scenario IDs:** `SC-3641`
- **AI Test Case sample:** TC-12336, TC-12337

#### `SEG120-R-005` — Field separators between 1/2 and 2/3 only, no trailing separator

- **Class / Severity:** serialization / error
- **Source anchor:** ATL105 | 2026-3 | 12.18 | segment 120 | element - | rule `field-separator-placement`
- **AI evidence:** 1 REQ / 1 scenario / 2 test cases (positive 2, negative 0)
- **AI REQ IDs:** `REQ-SRC-ATL105-PDF-001:1260` (BR-263-4)
- **AI Scenario IDs:** `SC-1841`
- **AI Test Case sample:** TC-4309, TC-4310

#### `SEG120-R-006` — Segment 120 only present in Financial Transaction Response / EMV Financial Transaction Response

- **Class / Severity:** applicability / error
- **Source anchor:** ATL105 | 2026-3 | 12.18 | segment 120 | element - | rule `response-only-applicability`
- **AI evidence:** 3 REQ / 3 scenarios / 4 test cases (positive 4, negative 0)
- **AI REQ IDs:** `REQ-SRC-ATL105-PDF-001:1257` (BR-263-1), `REQ-SRC-ATL105-PDF-001:3711` (Financial Transaction Response relationship), `REQ-SRC-ATL105-PDF-001:4068` (EMV Financial Transaction Response relationship)
- **AI Scenario IDs:** `SC-1838`, `SC-4235`, `SC-4493`
- **AI Test Case sample:** TC-4303, TC-4304, TC-15548, TC-16652
- **Gap:** no negative case proving Segment 120 is rejected when it appears on a *request* message.

#### `SEG120-R-007` — Segment 120 is the final segment of Data Section 3 — `P-01` RESOLVED

- **Class / Severity:** structure / error
- **AI evidence:** 1 REQ / 1 scenario / 2 test cases (positive 2, negative 0)
- **AI REQ IDs:** `REQ-SRC-ATL105-PDF-001:1258` (BR-263-2)
- **Resolution:** the ordering conflict with `docs/atl105_complete_templates.json` is assessed as a numeric-sort extraction artifact (see Cross-Cutting Findings #3); BR-263-2 is authoritative and this rule is now hard-enforced. No longer blocked.
- **Remaining gap:** both test cases are positive; no negative case (Segment 120 not last) exists.

### ⚠️ REVIEW_REQUIRED — blocked by an open PROVISIONAL question (2)

#### `SEG120-R-004` — Print Data <= 999 chars AND total <= 1009 characters (independent caps) — blocked by `P-02-RESIDUAL`

- **AI evidence:** 1 REQ / 1 scenario / 2 test cases (positive 2, negative 0)
- **AI REQ IDs:** `REQ-SRC-ATL105-PDF-001:1259` (BR-263-3)
- **Why blocked:** it is unconfirmed whether the 999-character Print Data cap and the 1,009-character total cap are two independently enforced limits (1009-3-4-2=1000, a 1-character gap from 999) or the field table has a typo.

#### `SEG120-R-008` — Print Data may contain `\` Blackhawk line delimiter — blocked by `P-03`

- **AI evidence:** 1 REQ / 1 scenario / 2 test cases (positive 2, negative 0)
- **AI REQ IDs:** `REQ-SRC-ATL105-PDF-001:1261` (BR-263-5, mis-bucketed `UNASSIGNED`)
- **Why blocked:** whether delimiter well-formedness belongs to the envelope validator or a separate Blackhawk/loyalty content module is unresolved.

## Sources

- AI requirement catalog: `src_Harit_Latest_AI_Sol/src/pipeline/step5_requirements/approved/requirement_catalog.json`, readable bucket `.../readable_by_segment/markdown/SEG-120.md`
- AI scenarios: `src_Harit_Latest_AI_Sol/src/pipeline/scenarios/approved/approved_scenarios.json`
- AI test cases: `src_Harit_Latest_AI_Sol/src/pipeline/test_generation/approved/test_case_catalog.json`
- AI traceability: `src_Harit_Latest_AI_Sol/src/pipeline/traceability/markdown/BY_SEGMENT.md`, `REQ_TO_SC_TC.md`
- AI message templates: `regression-artifact-validator/docs/atl105_complete_templates.json`
- Specification: `regression-artifact-validator/docs/specs/extracted_text.txt` lines 13753-13792 (Section 12.18)
