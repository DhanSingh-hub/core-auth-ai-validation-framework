# Segment 104 — AI Solution Coverage Report

**Specification:** ATL105 · **Version:** 2026-3 · **Segment:** 104 (Purchase Card Data Segment)
**Generated:** 2026-09-22T13:13:51.099888+00:00

This report classifies every Segment 104 rule from the authoritative [rule catalog](segment-104-rule-catalog.json) against evidence produced by the AI Solution pipeline under `src_Harit_Latest_AI_Sol/src/pipeline/`.

## AI Catalog Totals for SEG-104

| Metric | Count |
|---|---:|
| Business Requirements (bucketed to SEG-104) | 25 |
| Scenarios linked to SEG-104 Requirements | 24 |
| Test Cases linked to SEG-104 Scenarios | 156 |
| Test Cases — positive | 156 |
| Test Cases — negative (violated_element_name populated) | 0 |
| Test Cases — priority P3 | 156 |

## Coverage Summary

| Status | Count | % |
|---|---:|---:|
| ✅ COVERED | 0 | 0.0% |
| 🟡 PARTIALLY_COVERED | 11 | 78.6% |
| ⚠️ REVIEW_REQUIRED | 2 | 14.3% |
| ❌ MISSING | 1 | 7.1% |
| **Total Rules** | **14** | **100%** |

**Headline coverage: 0.0%** of Segment 104 rules are fully certifiable (`COVERED`) by the AI Solution's current output. The remaining rules are either only partially exercised, blocked by unresolved SME questions, or unmapped.

## Rule-Level Coverage

| # | Rule ID | Title | Class | Severity | Status | REQs | Scenarios | Test Cases | PROVISIONAL |
|---:|---|---|---|---|---|---:|---:|---:|---|
| 1 | `SEG104-R-001` | Purchase Card Data Segment is a Data Section 3 companion of Segment 100. | structure | error | 🟡 PARTIALLY_COVERED | 7 | 7 | 25 | - |
| 2 | `SEG104-R-002` | Purchase Card Data Segment object is required in the document being validated. | applicability | error | 🟡 PARTIALLY_COVERED | 6 | 6 | 26 | - |
| 3 | `SEG104-R-004` | Segment Type is 104. | field | error | 🟡 PARTIALLY_COVERED | 3 | 2 | 46 | - |
| 4 | `SEG104-R-005` | Segment Length is 3 digits representing the content length. | field | error | 🟡 PARTIALLY_COVERED | 2 | 2 | 46 | - |
| 5 | `SEG104-R-006` | Purchase Card Data Segment max length is 86 alphanumeric characters. | serialization | error | 🟡 PARTIALLY_COVERED | 3 | 3 | 31 | - |
| 6 | `SEG104-R-007` | Purchase Code is alphanumeric, max 16 characters, when populated. | field | error | 🟡 PARTIALLY_COVERED | 1 | 1 | 3 | - |
| 7 | `SEG104-R-008` | PC Tax Amount is numeric, up to 7 digits with 2 assumed decimal places, when pop | field | error | 🟡 PARTIALLY_COVERED | 1 | 1 | 3 | - |
| 8 | `SEG104-R-009` | PC Freight Amount is numeric, up to 7 digits with 2 assumed decimal places, when | field | error | 🟡 PARTIALLY_COVERED | 1 | 1 | 3 | - |
| 9 | `SEG104-R-010` | PC Duty Amount is numeric, up to 7 digits with 2 assumed decimal places, when po | field | error | 🟡 PARTIALLY_COVERED | 2 | 2 | 6 | - |
| 10 | `SEG104-R-011` | Ship-to Country Code is a fixed length of 3 digits, when populated. | field | error | 🟡 PARTIALLY_COVERED | 1 | 1 | 3 | - |
| 11 | `SEG104-R-012` | Ship-to Postal Code format is PROVISIONAL (P-01): strict US ZIP+4 or general alp | field | error | ⚠️ REVIEW_REQUIRED | 1 | 1 | 3 | P-01 |
| 12 | `SEG104-R-013` | Ship-from Postal Code format is PROVISIONAL (P-01): strict US ZIP+4 or general a | field | error | ⚠️ REVIEW_REQUIRED | 1 | 1 | 3 | P-01 |
| 13 | `SEG104-R-014` | Direct Marketing Invoice Number is alphanumeric, max 10 characters, when populat | field | error | 🟡 PARTIALLY_COVERED | 1 | 1 | 3 | - |
| 14 | `SEG104-R-020` | At most one Segment 104 per message. PROVISIONAL (P-02): not explicitly stated i | structure | error | ❌ MISSING | 0 | 0 | 0 | P-02 |

## Detailed Findings by Status

### ❌ MISSING — no AI evidence found (1)

#### `SEG104-R-020` — At most one Segment 104 per message. PROVISIONAL (P-02): not explicitly stated in the spec text the way Segment 101's cardinality is; enforced by analogy to the Data Section 3 slot structure pending SME confirmation.

- **Class / Severity:** structure / error
- **Source anchor:** ATL105 | 2026-3 | 12 | segment 104 | element - | rule `segment-occurrence`
- **PROVISIONAL P-02:** Confirm that Segment 104 must appear at most once per message.  The ATL105 spec text does not restate this cardinality the way it does for Segment 101; the validator currently enforces it by analogy to the Data Section 3 slot structure.
- **AI evidence:** 0 REQ / 0 scenarios / 0 test cases (positive 0, negative 0)

### 🟡 PARTIALLY_COVERED — chain incomplete or negative case absent (11)

#### `SEG104-R-001` — Purchase Card Data Segment is a Data Section 3 companion of Segment 100.

- **Class / Severity:** structure / error
- **Source anchor:** ATL105 | 2026-3 | 11.1.1 | segment 104 | element - | rule `section-3-companion-of-segment-100`
- **AI evidence:** 7 REQ / 7 scenarios / 25 test cases (positive 25, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:937`, `REQ-SRC-ATL105-PDF-001:950`, `REQ-SRC-ATL105-PDF-001:1058`, `REQ-SRC-ATL105-PDF-001:1147`, `REQ-SRC-ATL105-PDF-001:1672`, `REQ-SRC-ATL105-PDF-001:3701`, `REQ-SRC-ATL105-PDF-001:4008`
- **AI Scenario IDs (up to 10):** `SC-1526`, `SC-1539`, `SC-1647`, `SC-1732`, `SC-2250`, `SC-4225`, `SC-4457`
- **AI Test Case sample:** TC-2760, TC-2761, TC-2762, TC-2763, TC-2764, TC-2800, TC-2801, TC-2802, TC-2803, TC-2804, TC-3121, TC-3122, TC-3123, TC-3124, TC-3125

#### `SEG104-R-002` — Purchase Card Data Segment object is required in the document being validated.

- **Class / Severity:** applicability / error
- **Source anchor:** ATL105 | 2026-3 | 12.5 | segment 104 | element - | rule `purchase-card-segment-object-required`
- **AI evidence:** 6 REQ / 6 scenarios / 26 test cases (positive 26, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:937`, `REQ-SRC-ATL105-PDF-001:944`, `REQ-SRC-ATL105-PDF-001:950`, `REQ-SRC-ATL105-PDF-001:1058`, `REQ-SRC-ATL105-PDF-001:1068`, `REQ-SRC-ATL105-PDF-001:1672`
- **AI Scenario IDs (up to 10):** `SC-1526`, `SC-1533`, `SC-1539`, `SC-1647`, `SC-1657`, `SC-2250`
- **AI Test Case sample:** TC-2760, TC-2761, TC-2762, TC-2763, TC-2764, TC-2782, TC-2783, TC-2784, TC-2800, TC-2801, TC-2802, TC-2803, TC-2804, TC-3121, TC-3122

#### `SEG104-R-004` — Segment Type is 104.

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.5 | segment 104 | element 85 | rule `segment-104-type-fixed-value`
- **AI evidence:** 3 REQ / 2 scenarios / 46 test cases (positive 46, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:1149`, `REQ-SRC-ATL105-PDF-001:2949`, `REQ-SRC-ATL105-PDF-001:2950`
- **AI Scenario IDs (up to 10):** `SC-1734`, `SC-3514`
- **AI Test Case sample:** TC-3587, TC-3588, TC-3589, TC-3590, TC-3591, TC-3592, TC-3593, TC-3594, TC-3595, TC-3596, TC-3597, TC-3598, TC-3599, TC-3600, TC-3601

#### `SEG104-R-005` — Segment Length is 3 digits representing the content length.

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.5 | segment 104 | element 84 | rule `segment-104-length-format`
- **AI evidence:** 2 REQ / 2 scenarios / 46 test cases (positive 46, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:1765`, `REQ-SRC-ATL105-PDF-001:2951`
- **AI Scenario IDs (up to 10):** `SC-2343`, `SC-3515`
- **AI Test Case sample:** TC-7049, TC-7050, TC-7051, TC-7052, TC-7053, TC-7054, TC-7055, TC-7056, TC-7057, TC-7058, TC-7059, TC-7060, TC-7061, TC-7062, TC-7063

#### `SEG104-R-006` — Purchase Card Data Segment max length is 86 alphanumeric characters.

- **Class / Severity:** serialization / error
- **Source anchor:** ATL105 | 2026-3 | 12.5 | segment 104 | element - | rule `segment-104-max-length`
- **AI evidence:** 3 REQ / 3 scenarios / 31 test cases (positive 31, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:950`, `REQ-SRC-ATL105-PDF-001:1146`, `REQ-SRC-ATL105-PDF-001:1765`
- **AI Scenario IDs (up to 10):** `SC-1539`, `SC-1731`, `SC-2343`
- **AI Test Case sample:** TC-2800, TC-2801, TC-2802, TC-2803, TC-2804, TC-3578, TC-3579, TC-3580, TC-7049, TC-7050, TC-7051, TC-7052, TC-7053, TC-7054, TC-7055

#### `SEG104-R-007` — Purchase Code is alphanumeric, max 16 characters, when populated.

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.5 | segment 104 | element 80 | rule `purchase-code-format`
- **AI evidence:** 1 REQ / 1 scenarios / 3 test cases (positive 3, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:2952`
- **AI Scenario IDs (up to 10):** `SC-3516`
- **AI Test Case sample:** TC-11216, TC-11217, TC-11218

#### `SEG104-R-008` — PC Tax Amount is numeric, up to 7 digits with 2 assumed decimal places, when populated.

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.5 | segment 104 | element 74 | rule `pc-tax-amount-format`
- **AI evidence:** 1 REQ / 1 scenarios / 3 test cases (positive 3, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:2953`
- **AI Scenario IDs (up to 10):** `SC-3517`
- **AI Test Case sample:** TC-11219, TC-11220, TC-11221

#### `SEG104-R-009` — PC Freight Amount is numeric, up to 7 digits with 2 assumed decimal places, when populated.

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.5 | segment 104 | element 73 | rule `pc-freight-amount-format`
- **AI evidence:** 1 REQ / 1 scenarios / 3 test cases (positive 3, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:2954`
- **AI Scenario IDs (up to 10):** `SC-3518`
- **AI Test Case sample:** TC-11222, TC-11223, TC-11224

#### `SEG104-R-010` — PC Duty Amount is numeric, up to 7 digits with 2 assumed decimal places, when populated.

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.5 | segment 104 | element 72 | rule `pc-duty-amount-format`
- **AI evidence:** 2 REQ / 2 scenarios / 6 test cases (positive 6, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:1709`, `REQ-SRC-ATL105-PDF-001:2955`
- **AI Scenario IDs (up to 10):** `SC-2287`, `SC-3519`
- **AI Test Case sample:** TC-6584, TC-6585, TC-6586, TC-11225, TC-11226, TC-11227

#### `SEG104-R-011` — Ship-to Country Code is a fixed length of 3 digits, when populated.

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.5 | segment 104 | element 89 | rule `ship-to-country-code-format`
- **AI evidence:** 1 REQ / 1 scenarios / 3 test cases (positive 3, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:2956`
- **AI Scenario IDs (up to 10):** `SC-3520`
- **AI Test Case sample:** TC-11228, TC-11229, TC-11230

#### `SEG104-R-014` — Direct Marketing Invoice Number is alphanumeric, max 10 characters, when populated.

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.5 | segment 104 | element 29 | rule `direct-marketing-invoice-number-format`
- **AI evidence:** 1 REQ / 1 scenarios / 3 test cases (positive 3, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:2959`
- **AI Scenario IDs (up to 10):** `SC-3523`
- **AI Test Case sample:** TC-11237, TC-11238, TC-11239

### ⚠️ REVIEW_REQUIRED — SME resolution needed (PROVISIONAL blockers) (2)

#### `SEG104-R-012` — Ship-to Postal Code format is PROVISIONAL (P-01): strict US ZIP+4 or general alphanumeric up to 10 characters; non-conforming values are flagged for SME review rather than hard-rejected.

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.5 | segment 104 | element 90 | rule `ship-to-postal-code-format`
- **PROVISIONAL P-01:** Confirm whether Ship-to / Ship-from Postal Code must be strict US ZIP+4 (nnnnn or nnnnn-nnnn) or a general alphanumeric up to 10 characters, and whether non-conforming values are hard-rejected or flagged for SME review.
- **AI evidence:** 1 REQ / 1 scenarios / 3 test cases (positive 3, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:2957`
- **AI Scenario IDs (up to 10):** `SC-3521`
- **AI Test Case sample:** TC-11231, TC-11232, TC-11233

#### `SEG104-R-013` — Ship-from Postal Code format is PROVISIONAL (P-01): strict US ZIP+4 or general alphanumeric up to 10 characters; non-conforming values are flagged for SME review rather than hard-rejected.

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.5 | segment 104 | element 88 | rule `ship-from-postal-code-format`
- **PROVISIONAL P-01:** Confirm whether Ship-to / Ship-from Postal Code must be strict US ZIP+4 (nnnnn or nnnnn-nnnn) or a general alphanumeric up to 10 characters, and whether non-conforming values are hard-rejected or flagged for SME review.
- **AI evidence:** 1 REQ / 1 scenarios / 3 test cases (positive 3, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:2958`
- **AI Scenario IDs (up to 10):** `SC-3522`
- **AI Test Case sample:** TC-11234, TC-11235, TC-11236

