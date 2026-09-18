# Segment 101 — AI Solution Coverage Report

**Specification:** ATL105 · **Version:** 2026-3 · **Segment:** 101 (Fleet Data Segment)
**Generated:** 2026-09-17T13:37:19.512805+00:00

This report classifies every Segment 101 rule from the authoritative [rule catalog](segment-101-rule-catalog.json) against evidence produced by the AI Solution pipeline under `src_Harit_Latest_AI_Sol/src/pipeline/`.

## AI Catalog Totals for SEG-101

| Metric | Count |
|---|---:|
| Business Requirements (bucketed to SEG-101) | 68 |
| Scenarios linked to SEG-101 Requirements | 67 |
| Test Cases linked to SEG-101 Scenarios | 305 |
| Test Cases — positive | 305 |
| Test Cases — negative (violated_element_name populated) | 0 |
| Test Cases — priority P3 | 305 |

## Coverage Summary

| Status | Count | % |
|---|---:|---:|
| ✅ COVERED | 1 | 3.8% |
| 🟡 PARTIALLY_COVERED | 9 | 34.6% |
| ⚠️ REVIEW_REQUIRED | 15 | 57.7% |
| ❌ MISSING | 1 | 3.8% |
| **Total Rules** | **26** | **100%** |

**Headline coverage: 3.8%** of Segment 101 rules are fully certifiable (`COVERED`) by the AI Solution's current output. The remaining rules are either only partially exercised, blocked by unresolved SME questions, or unmapped.

## Rule-Level Coverage

| # | Rule ID | Title | Class | Severity | Status | REQs | Scenarios | Test Cases | PROVISIONAL |
|---:|---|---|---|---|---|---:|---:|---:|---|
| 1 | `SEG101-R-001` | Segment 101 is a Data Section 3 companion of Segment 100 | structure | error | 🟡 PARTIALLY_COVERED | 2 | 2 | 10 | - |
| 2 | `SEG101-R-002` | Segment 101 is required in every fleet-card financial transaction request | applicability | error | ⚠️ REVIEW_REQUIRED | 42 | 41 | 199 | P-04, P-03 |
| 3 | `SEG101-R-003` | Segment 101 must not coexist with Segment 145 (Enhanced Fleet) in the same messa | compatibility | error | ⚠️ REVIEW_REQUIRED | 3 | 3 | 11 | P-08 |
| 4 | `SEG101-R-004` | Segment Type is 101 | field | error | 🟡 PARTIALLY_COVERED | 4 | 3 | 69 | - |
| 5 | `SEG101-R-005` | Segment Length is 3 digits representing the segment content length | field | error | 🟡 PARTIALLY_COVERED | 3 | 3 | 69 | - |
| 6 | `SEG101-R-006` | Base Segment 101 maximum length is 61 alphanumeric characters | serialization | error | ⚠️ REVIEW_REQUIRED | 4 | 4 | 72 | P-01 |
| 7 | `SEG101-R-007` | Field order matches Section 12.2 (base fields 1-13, Auth Completion tag fields 1 | serialization | error | 🟡 PARTIALLY_COVERED | 1 | 1 | 3 | - |
| 8 | `SEG101-R-008` | Empty non-trailing fields retain their Field Separators | serialization | error | 🟡 PARTIALLY_COVERED | 2 | 2 | 26 | - |
| 9 | `SEG101-R-009` | Odometer, when populated, is numeric with maximum length 8 | field | error | ⚠️ REVIEW_REQUIRED | 2 | 2 | 6 | P-06 |
| 10 | `SEG101-R-010` | Vehicle Number, when populated, is alphanumeric with maximum length 10 | field | error | ⚠️ REVIEW_REQUIRED | 3 | 3 | 9 | P-06 |
| 11 | `SEG101-R-011` | Job Number, when populated, is alphanumeric with maximum length 10 | field | error | ⚠️ REVIEW_REQUIRED | 3 | 3 | 9 | P-06 |
| 12 | `SEG101-R-012` | Driver/Identification Number, when populated, is alphanumeric with maximum lengt | field | error | ⚠️ REVIEW_REQUIRED | 4 | 4 | 12 | P-06 |
| 13 | `SEG101-R-013` | Fleet Employee Number, when populated, is alphanumeric with maximum length 10 | field | error | ⚠️ REVIEW_REQUIRED | 4 | 4 | 12 | P-06 |
| 14 | `SEG101-R-014` | License #, when populated, is alphanumeric with maximum length 10 | field | error | ⚠️ REVIEW_REQUIRED | 4 | 4 | 12 | P-06 |
| 15 | `SEG101-R-015` | Job ID, when populated, is alphanumeric with maximum length 12 | field | error | ⚠️ REVIEW_REQUIRED | 3 | 3 | 9 | P-06 |
| 16 | `SEG101-R-016` | Department #, when populated, is alphanumeric with maximum length 12 | field | error | ⚠️ REVIEW_REQUIRED | 3 | 3 | 9 | P-06 |
| 17 | `SEG101-R-017` | Customer Data, when populated, is alphanumeric with maximum length 12 | field | error | ⚠️ REVIEW_REQUIRED | 3 | 3 | 9 | P-06 |
| 18 | `SEG101-R-018` | User ID, when populated, is alphanumeric with maximum length 12 and non-zero | field | error | ⚠️ REVIEW_REQUIRED | 3 | 3 | 9 | P-06 |
| 19 | `SEG101-R-019` | Vehicle ID#, when populated, is alphanumeric with maximum length 8 | field | error | ⚠️ REVIEW_REQUIRED | 3 | 3 | 9 | P-06 |
| 20 | `SEG101-R-020` | Fleet Tag fields 14-18 are only present in Auth Completion (0220) messages | lifecycle | error | 🟡 PARTIALLY_COVERED | 13 | 13 | 39 | - |
| 21 | `SEG101-R-021` | Fleet Tag fields 14-18 are only sent when Host Prompts are supported | applicability | error | 🟡 PARTIALLY_COVERED | 1 | 1 | 3 | - |
| 22 | `SEG101-R-022` | Fleet Tag format is 3-byte code plus up to 31-byte data payload | field | error | 🟡 PARTIALLY_COVERED | 8 | 8 | 24 | - |
| 23 | `SEG101-R-023` | Fleet Tag 3-byte code must be one of the 17 codes in the fleet-tag code table | field | error | 🟡 PARTIALLY_COVERED | 2 | 2 | 6 | - |
| 24 | `SEG101-R-024` | Fleet Tag data payload conforms to the per-code format | field | error | ⚠️ REVIEW_REQUIRED | 1 | 1 | 3 | P-02 |
| 25 | `SEG101-R-025` | Segment 101 originates at the device | metadata | info | ✅ COVERED | 1 | 1 | 3 | - |
| 26 | `SEG101-R-026` | Segment 101 appears exactly once per message | structure | error | ❌ MISSING | 0 | 0 | 0 | - |

## Detailed Findings by Status

### ❌ MISSING — no AI evidence found (1)

#### `SEG101-R-026` — Segment 101 appears exactly once per message

- **Class / Severity:** structure / error
- **Source anchor:** ATL105 | 2026-3 | 12 | segment 101 | element - | rule `segment-occurrence`
- **AI evidence:** 0 REQ / 0 scenarios / 0 test cases (positive 0, negative 0)

### 🟡 PARTIALLY_COVERED — chain incomplete or negative case absent (9)

#### `SEG101-R-001` — Segment 101 is a Data Section 3 companion of Segment 100

- **Class / Severity:** structure / error
- **Source anchor:** ATL105 | 2026-3 | 11.1.1 | segment 101 | element - | rule `section-3-companion-of-segment-100`
- **AI evidence:** 2 REQ / 2 scenarios / 10 test cases (positive 10, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:937`, `REQ-SRC-ATL105-PDF-001:1672`
- **AI Scenario IDs (up to 10):** `SC-1526`, `SC-2250`
- **AI Test Case sample:** TC-2760, TC-2761, TC-2762, TC-2763, TC-2764, TC-6417, TC-6418, TC-6419, TC-6420, TC-6421

#### `SEG101-R-004` — Segment Type is 101

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.2 | segment 101 | element 85 | rule `segment-type`
- **AI evidence:** 4 REQ / 3 scenarios / 69 test cases (positive 69, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:1109`, `REQ-SRC-ATL105-PDF-001:1110`, `REQ-SRC-ATL105-PDF-001:2912`, `REQ-SRC-ATL105-PDF-001:2913`
- **AI Scenario IDs (up to 10):** `SC-1698`, `SC-1699`, `SC-3480`
- **AI Test Case sample:** TC-10946, TC-10947, TC-10948, TC-10949, TC-10950, TC-10951, TC-10952, TC-10953, TC-10954, TC-10955, TC-10956, TC-10957, TC-10958, TC-10959, TC-10960

#### `SEG101-R-005` — Segment Length is 3 digits representing the segment content length

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.2 | segment 101 | element 84 | rule `segment-length`
- **AI evidence:** 3 REQ / 3 scenarios / 69 test cases (positive 69, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:1110`, `REQ-SRC-ATL105-PDF-001:1762`, `REQ-SRC-ATL105-PDF-001:2914`
- **AI Scenario IDs (up to 10):** `SC-1699`, `SC-2340`, `SC-3481`
- **AI Test Case sample:** TC-10969, TC-10970, TC-10971, TC-10972, TC-10973, TC-10974, TC-10975, TC-10976, TC-10977, TC-10978, TC-10979, TC-10980, TC-10981, TC-10982, TC-10983

#### `SEG101-R-007` — Field order matches Section 12.2 (base fields 1-13, Auth Completion tag fields 14-18)

- **Class / Severity:** serialization / error
- **Source anchor:** ATL105 | 2026-3 | 12.2 | segment 101 | element - | rule `segment-101-field-order`
- **AI evidence:** 1 REQ / 1 scenarios / 3 test cases (positive 3, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:1111`
- **AI Scenario IDs (up to 10):** `SC-1700`
- **AI Test Case sample:** TC-3417, TC-3418, TC-3419

#### `SEG101-R-008` — Empty non-trailing fields retain their Field Separators

- **Class / Severity:** serialization / error
- **Source anchor:** ATL105 | 2026-3 | 12.2 | segment 101 | element - | rule `non-trailing-empty-field-separator`
- **AI evidence:** 2 REQ / 2 scenarios / 26 test cases (positive 26, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:1108`, `REQ-SRC-ATL105-PDF-001:1110`
- **AI Scenario IDs (up to 10):** `SC-1697`, `SC-1699`
- **AI Test Case sample:** TC-3368, TC-3369, TC-3370, TC-3394, TC-3395, TC-3396, TC-3397, TC-3398, TC-3399, TC-3400, TC-3401, TC-3402, TC-3403, TC-3404, TC-3405

#### `SEG101-R-020` — Fleet Tag fields 14-18 are only present in Auth Completion (0220) messages

- **Class / Severity:** lifecycle / error
- **Source anchor:** ATL105 | 2026-3 | 12.2 | segment 101 | element - | rule `fleet-tags-auth-completion-only`
- **AI evidence:** 13 REQ / 13 scenarios / 39 test cases (positive 39, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:1112`, `REQ-SRC-ATL105-PDF-001:1113`, `REQ-SRC-ATL105-PDF-001:1114`, `REQ-SRC-ATL105-PDF-001:2926`, `REQ-SRC-ATL105-PDF-001:2927`, `REQ-SRC-ATL105-PDF-001:2928`, `REQ-SRC-ATL105-PDF-001:2929`, `REQ-SRC-ATL105-PDF-001:2930`, `REQ-SRC-ATL105-PDF-001:5714`, `REQ-SRC-ATL105-PDF-001:5715` (+3 more)
- **AI Scenario IDs (up to 10):** `SC-1701`, `SC-1702`, `SC-1703`, `SC-3493`, `SC-3494`, `SC-3495`, `SC-3496`, `SC-3497`, `SC-4714`, `SC-4715` (+3 more)
- **AI Test Case sample:** TC-11025, TC-11026, TC-11027, TC-11028, TC-11029, TC-11030, TC-11031, TC-11032, TC-11033, TC-11034, TC-11035, TC-11036, TC-11037, TC-11038, TC-11039

#### `SEG101-R-021` — Fleet Tag fields 14-18 are only sent when Host Prompts are supported

- **Class / Severity:** applicability / error
- **Source anchor:** ATL105 | 2026-3 | 12.2 | segment 101 | element - | rule `fleet-tags-host-prompts-required`
- **AI evidence:** 1 REQ / 1 scenarios / 3 test cases (positive 3, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:1112`
- **AI Scenario IDs (up to 10):** `SC-1701`
- **AI Test Case sample:** TC-3420, TC-3421, TC-3422

#### `SEG101-R-022` — Fleet Tag format is 3-byte code plus up to 31-byte data payload

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.2 | segment 101 | element - | rule `fleet-tag-format`
- **AI evidence:** 8 REQ / 8 scenarios / 24 test cases (positive 24, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:1112`, `REQ-SRC-ATL105-PDF-001:1113`, `REQ-SRC-ATL105-PDF-001:1114`, `REQ-SRC-ATL105-PDF-001:5714`, `REQ-SRC-ATL105-PDF-001:5715`, `REQ-SRC-ATL105-PDF-001:5716`, `REQ-SRC-ATL105-PDF-001:5717`, `REQ-SRC-ATL105-PDF-001:5718`
- **AI Scenario IDs (up to 10):** `SC-1701`, `SC-1702`, `SC-1703`, `SC-4714`, `SC-4715`, `SC-4716`, `SC-4717`, `SC-4718`
- **AI Test Case sample:** TC-16836, TC-16837, TC-16838, TC-16839, TC-16840, TC-16841, TC-16842, TC-16843, TC-16844, TC-16845, TC-16846, TC-16847, TC-16848, TC-16849, TC-16850

#### `SEG101-R-023` — Fleet Tag 3-byte code must be one of the 17 codes in the fleet-tag code table

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.2 | segment 101 | element - | rule `fleet-tag-code-enumeration`
- **AI evidence:** 2 REQ / 2 scenarios / 6 test cases (positive 6, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:1113`, `REQ-SRC-ATL105-PDF-001:1114`
- **AI Scenario IDs (up to 10):** `SC-1702`, `SC-1703`
- **AI Test Case sample:** TC-3423, TC-3424, TC-3425, TC-3426, TC-3427, TC-3428

### ⚠️ REVIEW_REQUIRED — SME resolution needed (PROVISIONAL blockers) (15)

#### `SEG101-R-002` — Segment 101 is required in every fleet-card financial transaction request

- **Class / Severity:** applicability / error
- **Source anchor:** ATL105 | 2026-3 | 12.2 | segment 101 | element - | rule `fleet-request-required`
- **PROVISIONAL P-04:** Confirm the Appendix G transaction types applicable to fleet (working: 0,5,6,7,S,C,U).
- **PROVISIONAL P-03:** Enumerate the fleet-eligible Appendix E card types.
- **AI evidence:** 42 REQ / 41 scenarios / 199 test cases (positive 199, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:941`, `REQ-SRC-ATL105-PDF-001:1065`, `REQ-SRC-ATL105-PDF-001:1105`, `REQ-SRC-ATL105-PDF-001:1106`, `REQ-SRC-ATL105-PDF-001:1107`, `REQ-SRC-ATL105-PDF-001:1108`, `REQ-SRC-ATL105-PDF-001:1109`, `REQ-SRC-ATL105-PDF-001:1111`, `REQ-SRC-ATL105-PDF-001:1632`, `REQ-SRC-ATL105-PDF-001:1647` (+32 more)
- **AI Scenario IDs (up to 10):** `SC-1530`, `SC-1654`, `SC-1694`, `SC-1695`, `SC-1696`, `SC-1697`, `SC-1698`, `SC-1700`, `SC-2210`, `SC-2225` (+31 more)
- **AI Test Case sample:** TC-10946, TC-10947, TC-10948, TC-10949, TC-10950, TC-10951, TC-10952, TC-10953, TC-10954, TC-10955, TC-10956, TC-10957, TC-10958, TC-10959, TC-10960

#### `SEG101-R-003` — Segment 101 must not coexist with Segment 145 (Enhanced Fleet) in the same message

- **Class / Severity:** compatibility / error
- **Source anchor:** ATL105 | 2026-3 | 12.2 | segment 101,145 | element - | rule `mutual-exclusion-enhanced-fleet`
- **PROVISIONAL P-08:** Segment 145 (Enhanced Fleet) scope for this training.
- **AI evidence:** 3 REQ / 3 scenarios / 11 test cases (positive 11, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:950`, `REQ-SRC-ATL105-PDF-001:1106`, `REQ-SRC-ATL105-PDF-001:1342`
- **AI Scenario IDs (up to 10):** `SC-1539`, `SC-1695`, `SC-1923`
- **AI Test Case sample:** TC-2800, TC-2801, TC-2802, TC-2803, TC-2804, TC-3362, TC-3363, TC-3364, TC-4725, TC-4726, TC-4727

#### `SEG101-R-006` — Base Segment 101 maximum length is 61 alphanumeric characters

- **Class / Severity:** serialization / error
- **Source anchor:** ATL105 | 2026-3 | 12.2 | segment 101 | element 84 | rule `segment-length-max-061-base`
- **PROVISIONAL P-01:** Reconcile base 001-061 max length with 5 x 34-byte Fleet Tags in Auth Completion messages.
- **AI evidence:** 4 REQ / 4 scenarios / 72 test cases (positive 72, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:1107`, `REQ-SRC-ATL105-PDF-001:1110`, `REQ-SRC-ATL105-PDF-001:1762`, `REQ-SRC-ATL105-PDF-001:2914`
- **AI Scenario IDs (up to 10):** `SC-1696`, `SC-1699`, `SC-2340`, `SC-3481`
- **AI Test Case sample:** TC-10969, TC-10970, TC-10971, TC-10972, TC-10973, TC-10974, TC-10975, TC-10976, TC-10977, TC-10978, TC-10979, TC-10980, TC-10981, TC-10982, TC-10983

#### `SEG101-R-009` — Odometer, when populated, is numeric with maximum length 8

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.2 | segment 101 | element 64 | rule `odometer`
- **PROVISIONAL P-06:** Per-field mandatory-presence triggers from fleet-program rules.
- **AI evidence:** 2 REQ / 2 scenarios / 6 test cases (positive 6, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:1703`, `REQ-SRC-ATL105-PDF-001:2915`
- **AI Scenario IDs (up to 10):** `SC-2281`, `SC-3482`
- **AI Test Case sample:** TC-10992, TC-10993, TC-10994, TC-6551, TC-6552, TC-6553

#### `SEG101-R-010` — Vehicle Number, when populated, is alphanumeric with maximum length 10

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.2 | segment 101 | element 108 | rule `vehicle-number`
- **PROVISIONAL P-06:** Per-field mandatory-presence triggers from fleet-program rules.
- **AI evidence:** 3 REQ / 3 scenarios / 9 test cases (positive 9, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:1836`, `REQ-SRC-ATL105-PDF-001:2916`, `REQ-SRC-ATL105-PDF-001:3791`
- **AI Scenario IDs (up to 10):** `SC-2414`, `SC-3483`, `SC-4315`
- **AI Test Case sample:** TC-10995, TC-10996, TC-10997, TC-16005, TC-16006, TC-16007, TC-7864, TC-7865, TC-7866

#### `SEG101-R-011` — Job Number, when populated, is alphanumeric with maximum length 10

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.2 | segment 101 | element 47 | rule `job-number`
- **PROVISIONAL P-06:** Per-field mandatory-presence triggers from fleet-program rules.
- **AI evidence:** 3 REQ / 3 scenarios / 9 test cases (positive 9, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:1659`, `REQ-SRC-ATL105-PDF-001:2917`, `REQ-SRC-ATL105-PDF-001:3736`
- **AI Scenario IDs (up to 10):** `SC-2237`, `SC-3484`, `SC-4260`
- **AI Test Case sample:** TC-10998, TC-10999, TC-11000, TC-15629, TC-15630, TC-15631, TC-6385, TC-6386, TC-6387

#### `SEG101-R-012` — Driver/Identification Number, when populated, is alphanumeric with maximum length 10

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.2 | segment 101 | element 31 | rule `driver-identification-number`
- **PROVISIONAL P-06:** Per-field mandatory-presence triggers from fleet-program rules.
- **AI evidence:** 4 REQ / 4 scenarios / 12 test cases (positive 12, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:1114`, `REQ-SRC-ATL105-PDF-001:1632`, `REQ-SRC-ATL105-PDF-001:2918`, `REQ-SRC-ATL105-PDF-001:3733`
- **AI Scenario IDs (up to 10):** `SC-1703`, `SC-2210`, `SC-3485`, `SC-4257`
- **AI Test Case sample:** TC-11001, TC-11002, TC-11003, TC-15615, TC-15616, TC-15617, TC-3426, TC-3427, TC-3428, TC-6233, TC-6234, TC-6235

#### `SEG101-R-013` — Fleet Employee Number, when populated, is alphanumeric with maximum length 10

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.2 | segment 101 | element 40 | rule `fleet-employee-number`
- **PROVISIONAL P-06:** Per-field mandatory-presence triggers from fleet-program rules.
- **AI evidence:** 4 REQ / 4 scenarios / 12 test cases (positive 12, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:1647`, `REQ-SRC-ATL105-PDF-001:2919`, `REQ-SRC-ATL105-PDF-001:3734`, `REQ-SRC-ATL105-PDF-001:5778`
- **AI Scenario IDs (up to 10):** `SC-2225`, `SC-3486`, `SC-4258`, `SC-4767`
- **AI Test Case sample:** TC-11004, TC-11005, TC-11006, TC-15618, TC-15619, TC-15620, TC-16971, TC-16972, TC-16973, TC-6286, TC-6287, TC-6288

#### `SEG101-R-014` — License #, when populated, is alphanumeric with maximum length 10

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.2 | segment 101 | element 158 | rule `license-number`
- **PROVISIONAL P-06:** Per-field mandatory-presence triggers from fleet-program rules.
- **AI evidence:** 4 REQ / 4 scenarios / 12 test cases (positive 12, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:1114`, `REQ-SRC-ATL105-PDF-001:2030`, `REQ-SRC-ATL105-PDF-001:2920`, `REQ-SRC-ATL105-PDF-001:3817`
- **AI Scenario IDs (up to 10):** `SC-1703`, `SC-2608`, `SC-3487`, `SC-4341`
- **AI Test Case sample:** TC-11007, TC-11008, TC-11009, TC-16127, TC-16128, TC-16129, TC-3426, TC-3427, TC-3428, TC-8682, TC-8683, TC-8684

#### `SEG101-R-015` — Job ID, when populated, is alphanumeric with maximum length 12

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.2 | segment 101 | element 159 | rule `job-id`
- **PROVISIONAL P-06:** Per-field mandatory-presence triggers from fleet-program rules.
- **AI evidence:** 3 REQ / 3 scenarios / 9 test cases (positive 9, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:2031`, `REQ-SRC-ATL105-PDF-001:2921`, `REQ-SRC-ATL105-PDF-001:3818`
- **AI Scenario IDs (up to 10):** `SC-2609`, `SC-3488`, `SC-4342`
- **AI Test Case sample:** TC-11010, TC-11011, TC-11012, TC-16130, TC-16131, TC-16132, TC-8685, TC-8686, TC-8687

#### `SEG101-R-016` — Department #, when populated, is alphanumeric with maximum length 12

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.2 | segment 101 | element 160 | rule `department-number`
- **PROVISIONAL P-06:** Per-field mandatory-presence triggers from fleet-program rules.
- **AI evidence:** 3 REQ / 3 scenarios / 9 test cases (positive 9, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:2032`, `REQ-SRC-ATL105-PDF-001:2922`, `REQ-SRC-ATL105-PDF-001:3819`
- **AI Scenario IDs (up to 10):** `SC-2610`, `SC-3489`, `SC-4343`
- **AI Test Case sample:** TC-11013, TC-11014, TC-11015, TC-16133, TC-16134, TC-16135, TC-8688, TC-8689, TC-8690

#### `SEG101-R-017` — Customer Data, when populated, is alphanumeric with maximum length 12

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.2 | segment 101 | element 161 | rule `customer-data`
- **PROVISIONAL P-06:** Per-field mandatory-presence triggers from fleet-program rules.
- **AI evidence:** 3 REQ / 3 scenarios / 9 test cases (positive 9, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:2033`, `REQ-SRC-ATL105-PDF-001:2923`, `REQ-SRC-ATL105-PDF-001:3820`
- **AI Scenario IDs (up to 10):** `SC-2611`, `SC-3490`, `SC-4344`
- **AI Test Case sample:** TC-11016, TC-11017, TC-11018, TC-16136, TC-16137, TC-16138, TC-8691, TC-8692, TC-8693

#### `SEG101-R-018` — User ID, when populated, is alphanumeric with maximum length 12 and non-zero

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.2 | segment 101 | element 162 | rule `user-id`
- **PROVISIONAL P-06:** Per-field mandatory-presence triggers from fleet-program rules.
- **AI evidence:** 3 REQ / 3 scenarios / 9 test cases (positive 9, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:2034`, `REQ-SRC-ATL105-PDF-001:2924`, `REQ-SRC-ATL105-PDF-001:3821`
- **AI Scenario IDs (up to 10):** `SC-2612`, `SC-3491`, `SC-4345`
- **AI Test Case sample:** TC-11019, TC-11020, TC-11021, TC-16139, TC-16140, TC-16141, TC-8694, TC-8695, TC-8696

#### `SEG101-R-019` — Vehicle ID#, when populated, is alphanumeric with maximum length 8

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.2 | segment 101 | element 163 | rule `vehicle-id-number`
- **PROVISIONAL P-06:** Per-field mandatory-presence triggers from fleet-program rules.
- **AI evidence:** 3 REQ / 3 scenarios / 9 test cases (positive 9, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:2036`, `REQ-SRC-ATL105-PDF-001:2925`, `REQ-SRC-ATL105-PDF-001:3822`
- **AI Scenario IDs (up to 10):** `SC-2614`, `SC-3492`, `SC-4346`
- **AI Test Case sample:** TC-11022, TC-11023, TC-11024, TC-16142, TC-16143, TC-16144, TC-8700, TC-8701, TC-8702

#### `SEG101-R-024` — Fleet Tag data payload conforms to the per-code format

- **Class / Severity:** field / error
- **Source anchor:** ATL105 | 2026-3 | 12.2 | segment 101 | element - | rule `fleet-tag-per-code-format`
- **PROVISIONAL P-02:** Confirm DLN carries 'Driver License name' (spec text 'nameation' appears to be a typo).
- **AI evidence:** 1 REQ / 1 scenarios / 3 test cases (positive 3, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:1114`
- **AI Scenario IDs (up to 10):** `SC-1703`
- **AI Test Case sample:** TC-3426, TC-3427, TC-3428

### ✅ COVERED — full artifact chain and positive/negative evidence present (1)

#### `SEG101-R-025` — Segment 101 originates at the device

- **Class / Severity:** metadata / info
- **Source anchor:** ATL105 | 2026-3 | 12.2 | segment 101 | element - | rule `origin-device`
- **AI evidence:** 1 REQ / 1 scenarios / 3 test cases (positive 3, negative 0)
- **AI REQ IDs (up to 10):** `REQ-SRC-ATL105-PDF-001:1111`
- **AI Scenario IDs (up to 10):** `SC-1700`
- **AI Test Case sample:** TC-3417, TC-3418, TC-3419

## Unmapped AI Requirements

The following **3** AI-derived business requirements are bucketed to SEG-101 but do not match any specific rule in the catalog. They may be duplicates, low-confidence noise, or new rules the catalog should incorporate.

| REQ ID | Source Rule | Confidence | Requires Review | Statement (first 150 chars) |
|---|---|---:|---|---|
| `REQ-SRC-ATL105-PDF-001:1058` | `BR-201-4` | 0.9 | False | EMV Financial Transaction Request Data Section No. 3 contains one or more of: Fleet (101), Product Code (102), Purchase Card (104), Variable Informati |
| `REQ-SRC-ATL105-PDF-001:2721` | `BR-687-1` | 0.5 | True | Value 28 indicates 'Unattended off premise customer-operated internet (nonsecure)' - a non-secure transaction in which the cardholder's payment card d |
| `REQ-SRC-ATL105-PDF-001:2722` | `BR-687-2` | 0.4 | True | A prior value (context continuation) indicates a transaction protected with a form of internet security such as SSL, but where authentication was not  |

## Notes and Caveats

- All AI test cases carry review_status=approved but reviewed_by=scripted-approval (no human SME review captured).
- All AI test cases are P3 priority; no negative (violated_element_name) test cases were emitted for SEG-101 in this build.
- Rules SEG101-R-020..R-024 (Fleet Tag rules) and SEG101-R-009..R-019 (per-field conditional rules) are blocked by PROVISIONAL P-01, P-02, or P-06 until SME resolves the underlying question.