# Segment 101 — Test Solution Coverage of AI Solution BRs

**Generated:** 2026-09-17T19:48:42+05:30

## Question

> The AI Solution produced **68** Segment 101 BRs; our Test Solution has **26** Segment 101 rules (`SEG101-R-001`..`SEG101-R-026`). Does the Test Solution semantically cover all 68 AI BRs?

This analysis answers that question using **many-to-many semantic coverage predicates** — one AI BR can be covered by multiple Test Solution rules, and one Test Solution rule can enforce multiple AI BRs.

## Bottom Line

- **66 of 68** AI BRs are semantically covered by at least one Test Solution rule (97.1%).
- **2 of 68** AI BRs are NOT covered by any Test Solution rule.
- **19 of 68** AI BRs are covered by 2 or more Test Solution rules (these are compound BRs that legitimately merit multiple rules).
- **1 of 26** Test Solution rules have no AI BR support (the AI Solution did not derive that specific rule).

## Coverage by AI Derivation Method

| AI derivation method | AI BR count | Covered | Not covered | Coverage % |
|---|---:|---:|---:|---:|
| `business_rule_derived` | 31 | 29 | 2 | 93.5% |
| `field_constraint` | 18 | 18 | 0 | 100.0% |
| `field_fixed_value` | 1 | 1 | 0 | 100.0% |
| `relationship_derived` | 12 | 12 | 0 | 100.0% |
| `supplemental_entity_derived` | 6 | 6 | 0 | 100.0% |
| **Total** | **68** | **66** | **2** | **97.1%** |

## Test Solution Rule → AI BR Coverage

How many AI BRs each Test Solution rule enforces.

| Rule | Title | Class | AI BRs enforced |
|---|---|---|---:|
| `SEG101-R-001` | Segment 101 is a Data Section 3 companion of Segment 100 | structure | 7 |
| `SEG101-R-002` | Segment 101 is required in every fleet-card financial transaction request | applicability | 12 |
| `SEG101-R-003` | Segment 101 must not coexist with Segment 145 (Enhanced Fleet) in the same message | compatibility | 2 |
| `SEG101-R-004` | Segment Type is 101 | field | 4 |
| `SEG101-R-005` | Segment Length is 3 digits representing the segment content length | field | 3 |
| `SEG101-R-006` | Base Segment 101 maximum length is 61 alphanumeric characters | serialization | 3 |
| `SEG101-R-007` | Field order matches Section 12.2 (base fields 1-13, Auth Completion tag fields 14-18) | serialization | 2 |
| `SEG101-R-008` | Empty non-trailing fields retain their Field Separators | serialization | 1 |
| `SEG101-R-009` | Odometer, when populated, is numeric with maximum length 8 | field | 2 |
| `SEG101-R-010` | Vehicle Number, when populated, is alphanumeric with maximum length 10 | field | 3 |
| `SEG101-R-011` | Job Number, when populated, is alphanumeric with maximum length 10 | field | 3 |
| `SEG101-R-012` | Driver/Identification Number, when populated, is alphanumeric with maximum length 10 | field | 4 |
| `SEG101-R-013` | Fleet Employee Number, when populated, is alphanumeric with maximum length 10 | field | 4 |
| `SEG101-R-014` | License #, when populated, is alphanumeric with maximum length 10 | field | 3 |
| `SEG101-R-015` | Job ID, when populated, is alphanumeric with maximum length 12 | field | 3 |
| `SEG101-R-016` | Department #, when populated, is alphanumeric with maximum length 12 | field | 3 |
| `SEG101-R-017` | Customer Data, when populated, is alphanumeric with maximum length 12 | field | 3 |
| `SEG101-R-018` | User ID, when populated, is alphanumeric with maximum length 12 and non-zero | field | 3 |
| `SEG101-R-019` | Vehicle ID#, when populated, is alphanumeric with maximum length 8 | field | 3 |
| `SEG101-R-020` | Fleet Tag fields 14-18 are only present in Auth Completion (0220) messages | lifecycle | 13 |
| `SEG101-R-021` | Fleet Tag fields 14-18 are only sent when Host Prompts are supported | applicability | 1 |
| `SEG101-R-022` | Fleet Tag format is 3-byte code plus up to 31-byte data payload | field | 8 |
| `SEG101-R-023` | Fleet Tag 3-byte code must be one of the 17 codes in the fleet-tag code table | field | 1 |
| `SEG101-R-024` | Fleet Tag data payload conforms to the per-code format | field | 1 |
| `SEG101-R-025` | Segment 101 originates at the device | metadata | 1 |
| `SEG101-R-026` | Segment 101 appears exactly once per message | structure | 0 |

### Test Solution rules with NO AI BR support

These rules are in the Test Solution catalog but the AI Solution did NOT emit a BR for them.

- **`SEG101-R-026` — Segment 101 appears exactly once per message**

## AI BR → Test Solution Rule(s) — Full Coverage Table

| # | AI BR ID | Source | Method | Statement (first 120 chars) | Covered by rules |
|---:|---|---|---|---|---|
| 1 | `REQ-SRC-ATL105-PDF-001:1058` | `BR-201-4` | business_rule_derived | EMV Financial Transaction Request Data Section No. 3 contains one or more of: Fleet (101), Product Code (102), Purchase  | `SEG101-R-001`, `SEG101-R-002` |
| 2 | `REQ-SRC-ATL105-PDF-001:1065` | `BR-203-5` | business_rule_derived | Segment 101 (Fleet Data Segment) is sent only on transactions requiring fleet data. | `SEG101-R-002` |
| 3 | `REQ-SRC-ATL105-PDF-001:1105` | `BR-223-1` | business_rule_derived | The Fleet Data Segment (Data Segment No. 101) is included in all fleet card transaction requests. | `SEG101-R-002` |
| 4 | `REQ-SRC-ATL105-PDF-001:1106` | `BR-223-2` | business_rule_derived | Merchants should not send the Fleet Data Segment (101) and the Enhanced Fleet Data Segment (145) together in the same me | `SEG101-R-003` |
| 5 | `REQ-SRC-ATL105-PDF-001:1107` | `BR-223-3` | business_rule_derived | The Fleet Data Segment has a maximum length of 61 characters, alphanumeric (0-9, a-z, A-Z). | `SEG101-R-006` |
| 6 | `REQ-SRC-ATL105-PDF-001:1108` | `BR-223-4` | business_rule_derived | All fields in the Fleet Data Segment are separated by Field Separators; when a field is not populated, the Field Separat | `SEG101-R-008` |
| 7 | `REQ-SRC-ATL105-PDF-001:1109` | `BR-223-5` | business_rule_derived | Segment Type field in the Fleet Data Segment has fixed value 101. | `SEG101-R-004` |
| 8 | `REQ-SRC-ATL105-PDF-001:1110` | `BR-223-6` | business_rule_derived | Segment Length field identifies the data segment's length, including the Segment Type field's length and Field Separator | `SEG101-R-004`, `SEG101-R-005` |
| 9 | `REQ-SRC-ATL105-PDF-001:1111` | `BR-223-7` | business_rule_derived | The Fleet Data Segment originates at the device and can appear in any of the fields in Data Section No. 3. | `SEG101-R-001`, `SEG101-R-002`, `SEG101-R-007`, `SEG101-R-025` |
| 10 | `REQ-SRC-ATL105-PDF-001:1112` | `BR-224-1` | business_rule_derived | Fleet Tag 1–5 fields are sent only in the Auth Completion (0220) message, and only when Host Prompts are supported. | `SEG101-R-020`, `SEG101-R-021`, `SEG101-R-022` |
| 11 | `REQ-SRC-ATL105-PDF-001:1113` | `BR-224-2` | business_rule_derived | Each Fleet Tag consists of a 3-byte fixed-length Tag code followed by up to 31 bytes of Data. | `SEG101-R-020`, `SEG101-R-022` |
| 12 | `REQ-SRC-ATL105-PDF-001:1114` | `BR-224-3` | business_rule_derived | The 3-byte Fleet Tag code must be one of: DLS (Driver License State/Province Abbrev, an3), DLN (Driver License name, an2 | `SEG101-R-020`, `SEG101-R-022`, `SEG101-R-023`, `SEG101-R-024`, `SEG101-R-012` |
| 13 | `REQ-SRC-ATL105-PDF-001:1342` | `BR-286-6` | business_rule_derived | Merchants must not send Fleet segment (Data Segment No. 101) and Enhanced Fleet segment (Data Segment No. 145) together. | `SEG101-R-003` |
| 14 | `REQ-SRC-ATL105-PDF-001:1632` | `BR-374-1` | business_rule_derived | If required by the issuer, the Driver/Identification Number must be an unencrypted value and is found in the Fleet Data  | `SEG101-R-012` |
| 15 | `REQ-SRC-ATL105-PDF-001:1647` | `BR-378-1` | business_rule_derived | If required by the issuer, Fleet Employee Number is found in the Fleet Data Segment (Segment No. 101). | `SEG101-R-013` |
| 16 | `REQ-SRC-ATL105-PDF-001:1659` | `BR-380-1` | business_rule_derived | If required by the issuer, the Job Number element is found in the Fleet Data Segment (Segment No. 101). | `SEG101-R-011` |
| 17 | `REQ-SRC-ATL105-PDF-001:1672` | `BR-384-3` | business_rule_derived | For Financial Transaction Requests, zero, one, or more of Data Segments 101 (Fleet), 102 (Product Code), 103 (EBT), 104  | `SEG101-R-001`, `SEG101-R-002` |
| 18 | `REQ-SRC-ATL105-PDF-001:1703` | `BR-389-1` | business_rule_derived | If required by the issuer, the Odometer element is found in the Fleet Data Segment (Segment No. 101). | `SEG101-R-009` |
| 19 | `REQ-SRC-ATL105-PDF-001:1762` | `BR-400-2` | business_rule_derived | Fleet Data Segment (No. 101) Segment Length valid values are 001–61. | `SEG101-R-005`, `SEG101-R-006` |
| 20 | `REQ-SRC-ATL105-PDF-001:1836` | `BR-414-3` | business_rule_derived | Vehicle Number is found in the Fleet Data Segment (101) if required by the issuer. | `SEG101-R-010` |
| 21 | `REQ-SRC-ATL105-PDF-001:2030` | `BR-453-4` | business_rule_derived | If required by the issuer, License # (Element 158) is found in the Fleet Data Segment (Segment 101) and identifies the L | `SEG101-R-002`, `SEG101-R-014` |
| 22 | `REQ-SRC-ATL105-PDF-001:2031` | `BR-454-1` | business_rule_derived | If required by issuer, Job ID is found in the Fleet Data Segment (Segment No. 101). | `SEG101-R-015` |
| 23 | `REQ-SRC-ATL105-PDF-001:2032` | `BR-454-2` | business_rule_derived | If required by issuer, Department # is found in the Fleet Data Segment (Segment No. 101). | `SEG101-R-016` |
| 24 | `REQ-SRC-ATL105-PDF-001:2033` | `BR-454-3` | business_rule_derived | If required by issuer, Customer Data is found in the Fleet Data Segment (Segment No. 101). | `SEG101-R-017` |
| 25 | `REQ-SRC-ATL105-PDF-001:2034` | `BR-455-1` | business_rule_derived | If required by issuer, the User ID element is found in the Fleet Data Segment (Segment No. 101). | `SEG101-R-018` |
| 26 | `REQ-SRC-ATL105-PDF-001:2036` | `BR-455-3` | business_rule_derived | If required by issuer, the Vehicle ID# element is found in the Fleet Data Segment (Segment No. 101). | `SEG101-R-019` |
| 27 | `REQ-SRC-ATL105-PDF-001:2721` | `BR-687-1` | business_rule_derived | Value 28 indicates 'Unattended off premise customer-operated internet (nonsecure)' - a non-secure transaction in which t | **NONE** |
| 28 | `REQ-SRC-ATL105-PDF-001:2722` | `BR-687-2` | business_rule_derived | A prior value (context continuation) indicates a transaction protected with a form of internet security such as SSL, but | **NONE** |
| 29 | `REQ-SRC-ATL105-PDF-001:2912` | `ENT-FIELD-101-1` | field_constraint | In Fleet Data Segment (Data Segment No. 101), field 1 (Segment Type) is required and must be present. | `SEG101-R-004` |
| 30 | `REQ-SRC-ATL105-PDF-001:2913` | `ENT-FIELD-101-1` | field_fixed_value | In Fleet Data Segment (Data Segment No. 101), field 1 (Segment Type) must equal the fixed value 101. | `SEG101-R-004` |
| 31 | `REQ-SRC-ATL105-PDF-001:2914` | `ENT-FIELD-101-2` | field_constraint | In Fleet Data Segment (Data Segment No. 101), field 2 (Segment Length) is required and must be present. | `SEG101-R-005` |
| 32 | `REQ-SRC-ATL105-PDF-001:2915` | `ENT-FIELD-101-3` | field_constraint | In Fleet Data Segment (Data Segment No. 101), field 3 (Odometer) is conditional and must be present only when its docume | `SEG101-R-009` |
| 33 | `REQ-SRC-ATL105-PDF-001:2916` | `ENT-FIELD-101-4` | field_constraint | In Fleet Data Segment (Data Segment No. 101), field 4 (Vehicle Number) is conditional and must be present only when its  | `SEG101-R-010` |
| 34 | `REQ-SRC-ATL105-PDF-001:2917` | `ENT-FIELD-101-5` | field_constraint | In Fleet Data Segment (Data Segment No. 101), field 5 (Job Number) is conditional and must be present only when its docu | `SEG101-R-011` |
| 35 | `REQ-SRC-ATL105-PDF-001:2918` | `ENT-FIELD-101-6` | field_constraint | In Fleet Data Segment (Data Segment No. 101), field 6 (Driver /Identification Number) is conditional and must be present | `SEG101-R-012` |
| 36 | `REQ-SRC-ATL105-PDF-001:2919` | `ENT-FIELD-101-7` | field_constraint | In Fleet Data Segment (Data Segment No. 101), field 7 (Fleet Employee Number) is conditional and must be present only wh | `SEG101-R-013` |
| 37 | `REQ-SRC-ATL105-PDF-001:2920` | `ENT-FIELD-101-8` | field_constraint | In Fleet Data Segment (Data Segment No. 101), field 8 (License #) is conditional and must be present only when its docum | `SEG101-R-014` |
| 38 | `REQ-SRC-ATL105-PDF-001:2921` | `ENT-FIELD-101-9` | field_constraint | In Fleet Data Segment (Data Segment No. 101), field 9 (Job ID) is conditional and must be present only when its document | `SEG101-R-015` |
| 39 | `REQ-SRC-ATL105-PDF-001:2922` | `ENT-FIELD-101-10` | field_constraint | In Fleet Data Segment (Data Segment No. 101), field 10 (Department #) is conditional and must be present only when its d | `SEG101-R-016` |
| 40 | `REQ-SRC-ATL105-PDF-001:2923` | `ENT-FIELD-101-11` | field_constraint | In Fleet Data Segment (Data Segment No. 101), field 11 (Customer Data) is conditional and must be present only when its  | `SEG101-R-017` |
| 41 | `REQ-SRC-ATL105-PDF-001:2924` | `ENT-FIELD-101-12` | field_constraint | In Fleet Data Segment (Data Segment No. 101), field 12 (User ID) is conditional and must be present only when its docume | `SEG101-R-018` |
| 42 | `REQ-SRC-ATL105-PDF-001:2925` | `ENT-FIELD-101-13` | field_constraint | In Fleet Data Segment (Data Segment No. 101), field 13 (Vehicle ID#) is conditional and must be present only when its do | `SEG101-R-019` |
| 43 | `REQ-SRC-ATL105-PDF-001:2926` | `ENT-FIELD-101-14` | field_constraint | In Fleet Data Segment (Data Segment No. 101), field 14 (Fleet Tag 1) is conditional and must be present only when its do | `SEG101-R-020` |
| 44 | `REQ-SRC-ATL105-PDF-001:2927` | `ENT-FIELD-101-15` | field_constraint | In Fleet Data Segment (Data Segment No. 101), field 15 (Fleet Tag 2) is conditional and must be present only when its do | `SEG101-R-020` |
| 45 | `REQ-SRC-ATL105-PDF-001:2928` | `ENT-FIELD-101-16` | field_constraint | In Fleet Data Segment (Data Segment No. 101), field 16 (Fleet Tag 3) is conditional and must be present only when its do | `SEG101-R-020` |
| 46 | `REQ-SRC-ATL105-PDF-001:2929` | `ENT-FIELD-101-17` | field_constraint | In Fleet Data Segment (Data Segment No. 101), field 17 (Fleet Tag 4) is conditional and must be present only when its do | `SEG101-R-020` |
| 47 | `REQ-SRC-ATL105-PDF-001:2930` | `ENT-FIELD-101-18` | field_constraint | In Fleet Data Segment (Data Segment No. 101), field 18 (Fleet Tag 5) is conditional and must be present only when its do | `SEG101-R-020` |
| 48 | `REQ-SRC-ATL105-PDF-001:3698` | `REL-ENT-SEG-101-FINANCIAL_TRANSACTION_REQUEST` | relationship_derived | The Financial Transaction Request transaction includes Fleet Data Segment (Data Segment No. 101). | `SEG101-R-001`, `SEG101-R-002` |
| 49 | `REQ-SRC-ATL105-PDF-001:3733` | `REL-ENT-ELEM-31-ENT-SEG-101` | relationship_derived | ENT-ELEM-31 depends on ENT-SEG-101; the documented dependency must hold. | `SEG101-R-012` |
| 50 | `REQ-SRC-ATL105-PDF-001:3734` | `REL-ENT-ELEM-40-ENT-SEG-101` | relationship_derived | ENT-ELEM-40 depends on ENT-SEG-101; the documented dependency must hold. | `SEG101-R-013` |
| 51 | `REQ-SRC-ATL105-PDF-001:3736` | `REL-ENT-ELEM-47-ENT-SEG-101` | relationship_derived | ENT-ELEM-47 depends on ENT-SEG-101; the documented dependency must hold. | `SEG101-R-011` |
| 52 | `REQ-SRC-ATL105-PDF-001:3791` | `REL-ENT-ELEM-108-ENT-SEG-101` | relationship_derived | ENT-ELEM-108 depends on ENT-SEG-101; the documented dependency must hold. | `SEG101-R-010` |
| 53 | `REQ-SRC-ATL105-PDF-001:3817` | `REL-ENT-ELEM-158-ENT-SEG-101` | relationship_derived | ENT-ELEM-158 depends on ENT-SEG-101; the documented dependency must hold. | `SEG101-R-014` |
| 54 | `REQ-SRC-ATL105-PDF-001:3818` | `REL-ENT-ELEM-159-ENT-SEG-101` | relationship_derived | ENT-ELEM-159 depends on ENT-SEG-101; the documented dependency must hold. | `SEG101-R-015` |
| 55 | `REQ-SRC-ATL105-PDF-001:3819` | `REL-ENT-ELEM-160-ENT-SEG-101` | relationship_derived | ENT-ELEM-160 depends on ENT-SEG-101; the documented dependency must hold. | `SEG101-R-016` |
| 56 | `REQ-SRC-ATL105-PDF-001:3820` | `REL-ENT-ELEM-161-ENT-SEG-101` | relationship_derived | ENT-ELEM-161 depends on ENT-SEG-101; the documented dependency must hold. | `SEG101-R-017` |
| 57 | `REQ-SRC-ATL105-PDF-001:3821` | `REL-ENT-ELEM-162-ENT-SEG-101` | relationship_derived | ENT-ELEM-162 depends on ENT-SEG-101; the documented dependency must hold. | `SEG101-R-018` |
| 58 | `REQ-SRC-ATL105-PDF-001:3822` | `REL-ENT-ELEM-163-ENT-SEG-101` | relationship_derived | ENT-ELEM-163 depends on ENT-SEG-101; the documented dependency must hold. | `SEG101-R-019` |
| 59 | `REQ-SRC-ATL105-PDF-001:4006` | `ENT-SEG-101` | relationship_derived | The Financial Transaction Request transaction includes Fleet Data Segment (Data Segment No. 101). | `SEG101-R-001`, `SEG101-R-002` |
| 60 | `REQ-SRC-ATL105-PDF-001:5714` | `ENT-ELEM-FLEET-TAG-1` | supplemental_entity_derived | Fleet Tag 1: Fleet tag sent only in Auth Completion message; Tag 3 bytes fixed + Data up to 31 bytes. | `SEG101-R-020`, `SEG101-R-022` |
| 61 | `REQ-SRC-ATL105-PDF-001:5715` | `ENT-ELEM-FLEET-TAG-2` | supplemental_entity_derived | Fleet Tag 2: Fleet tag sent only in Auth Completion message; Tag 3 bytes fixed + Data up to 31 bytes. | `SEG101-R-020`, `SEG101-R-022` |
| 62 | `REQ-SRC-ATL105-PDF-001:5716` | `ENT-ELEM-FLEET-TAG-3` | supplemental_entity_derived | Fleet Tag 3: Fleet tag sent only in Auth Completion message; Tag 3 bytes fixed + Data up to 31 bytes. | `SEG101-R-020`, `SEG101-R-022` |
| 63 | `REQ-SRC-ATL105-PDF-001:5717` | `ENT-ELEM-FLEET-TAG-4` | supplemental_entity_derived | Fleet Tag 4: Fleet tag sent only in Auth Completion message; Tag 3 bytes fixed + Data up to 31 bytes. | `SEG101-R-020`, `SEG101-R-022` |
| 64 | `REQ-SRC-ATL105-PDF-001:5718` | `ENT-ELEM-FLEET-TAG-5` | supplemental_entity_derived | Fleet Tag 5: Fleet tag sent only in Auth Completion message; Tag 3 bytes fixed + Data up to 31 bytes. | `SEG101-R-020`, `SEG101-R-022` |
| 65 | `REQ-SRC-ATL105-PDF-001:5778` | `ENT-ELEM-40` | supplemental_entity_derived | Fleet Employee Number: Used for fleet cards that require a Fleet Employee Number; found in Fleet Data Segment when requi | `SEG101-R-002`, `SEG101-R-013` |
| 66 | `REQ-SRC-ATL105-PDF-001:937` | `BR-163-3` | business_rule_derived | Data Section No. 3 of a Financial Transaction Request contains none, one, or more of the following data segments: Fleet  | `SEG101-R-001`, `SEG101-R-002` |
| 67 | `REQ-SRC-ATL105-PDF-001:941` | `BR-165-1` | business_rule_derived | Fleet Data Segment (101) is sent only on transactions requiring fleet data. | `SEG101-R-002` |
| 68 | `REQ-SRC-ATL105-PDF-001:950` | `BR-165-10` | business_rule_derived | Segments 101–145 in Financial Transaction Request occupy Field Nos. 4–9 with specified maximum lengths (101:308, 102:381 | `SEG101-R-001`, `SEG101-R-002`, `SEG101-R-006`, `SEG101-R-007` |

## AI BRs NOT Covered by Any Test Solution Rule

These **2** AI BRs are the true coverage gap. For each, we note whether it is a genuine spec rule the Test Solution should adopt, or noise/duplicate.

### `REQ-SRC-ATL105-PDF-001:2721` — BR-687-1 (page 687)
- **Derivation method:** business_rule_derived
- **Confidence:** 0.5 · **Requires review:** True
- **Statement:** Value 28 indicates 'Unattended off premise customer-operated internet (nonsecure)' - a non-secure transaction in which the cardholder's payment card data was transmitted with no security method.
- **Constraint:** Denotes unattended off-premise customer-operated nonsecure internet transaction
- **Related entities:** ENT-SEG-101

### `REQ-SRC-ATL105-PDF-001:2722` — BR-687-2 (page 687)
- **Derivation method:** business_rule_derived
- **Confidence:** 0.4 · **Requires review:** True
- **Statement:** A prior value (context continuation) indicates a transaction protected with a form of internet security such as SSL, but where authentication was not performed.
- **Constraint:** Indicates SSL-protected but non-authenticated internet transaction
- **Related entities:** ENT-SEG-101

## AI BRs Covered by Multiple Test Solution Rules

These **19** AI BRs are compound statements — each legitimately corresponds to more than one rule in the Test Solution catalog.

| AI BR | Statement (120 chars) | Rules |
|---|---|---|
| `REQ-SRC-ATL105-PDF-001:937` | Data Section No. 3 of a Financial Transaction Request contains none, one, or more of the following data segments: Fleet  | `SEG101-R-001`, `SEG101-R-002` |
| `REQ-SRC-ATL105-PDF-001:950` | Segments 101–145 in Financial Transaction Request occupy Field Nos. 4–9 with specified maximum lengths (101:308, 102:381 | `SEG101-R-001`, `SEG101-R-002`, `SEG101-R-006`, `SEG101-R-007` |
| `REQ-SRC-ATL105-PDF-001:1058` | EMV Financial Transaction Request Data Section No. 3 contains one or more of: Fleet (101), Product Code (102), Purchase  | `SEG101-R-001`, `SEG101-R-002` |
| `REQ-SRC-ATL105-PDF-001:1110` | Segment Length field identifies the data segment's length, including the Segment Type field's length and Field Separator | `SEG101-R-004`, `SEG101-R-005` |
| `REQ-SRC-ATL105-PDF-001:1111` | The Fleet Data Segment originates at the device and can appear in any of the fields in Data Section No. 3. | `SEG101-R-001`, `SEG101-R-002`, `SEG101-R-007`, `SEG101-R-025` |
| `REQ-SRC-ATL105-PDF-001:1112` | Fleet Tag 1–5 fields are sent only in the Auth Completion (0220) message, and only when Host Prompts are supported. | `SEG101-R-020`, `SEG101-R-021`, `SEG101-R-022` |
| `REQ-SRC-ATL105-PDF-001:1113` | Each Fleet Tag consists of a 3-byte fixed-length Tag code followed by up to 31 bytes of Data. | `SEG101-R-020`, `SEG101-R-022` |
| `REQ-SRC-ATL105-PDF-001:1114` | The 3-byte Fleet Tag code must be one of: DLS (Driver License State/Province Abbrev, an3), DLN (Driver License name, an2 | `SEG101-R-020`, `SEG101-R-022`, `SEG101-R-023`, `SEG101-R-024`, `SEG101-R-012` |
| `REQ-SRC-ATL105-PDF-001:1672` | For Financial Transaction Requests, zero, one, or more of Data Segments 101 (Fleet), 102 (Product Code), 103 (EBT), 104  | `SEG101-R-001`, `SEG101-R-002` |
| `REQ-SRC-ATL105-PDF-001:1762` | Fleet Data Segment (No. 101) Segment Length valid values are 001–61. | `SEG101-R-005`, `SEG101-R-006` |
| `REQ-SRC-ATL105-PDF-001:2030` | If required by the issuer, License # (Element 158) is found in the Fleet Data Segment (Segment 101) and identifies the L | `SEG101-R-002`, `SEG101-R-014` |
| `REQ-SRC-ATL105-PDF-001:3698` | The Financial Transaction Request transaction includes Fleet Data Segment (Data Segment No. 101). | `SEG101-R-001`, `SEG101-R-002` |
| `REQ-SRC-ATL105-PDF-001:4006` | The Financial Transaction Request transaction includes Fleet Data Segment (Data Segment No. 101). | `SEG101-R-001`, `SEG101-R-002` |
| `REQ-SRC-ATL105-PDF-001:5714` | Fleet Tag 1: Fleet tag sent only in Auth Completion message; Tag 3 bytes fixed + Data up to 31 bytes. | `SEG101-R-020`, `SEG101-R-022` |
| `REQ-SRC-ATL105-PDF-001:5715` | Fleet Tag 2: Fleet tag sent only in Auth Completion message; Tag 3 bytes fixed + Data up to 31 bytes. | `SEG101-R-020`, `SEG101-R-022` |
| `REQ-SRC-ATL105-PDF-001:5716` | Fleet Tag 3: Fleet tag sent only in Auth Completion message; Tag 3 bytes fixed + Data up to 31 bytes. | `SEG101-R-020`, `SEG101-R-022` |
| `REQ-SRC-ATL105-PDF-001:5717` | Fleet Tag 4: Fleet tag sent only in Auth Completion message; Tag 3 bytes fixed + Data up to 31 bytes. | `SEG101-R-020`, `SEG101-R-022` |
| `REQ-SRC-ATL105-PDF-001:5718` | Fleet Tag 5: Fleet tag sent only in Auth Completion message; Tag 3 bytes fixed + Data up to 31 bytes. | `SEG101-R-020`, `SEG101-R-022` |
| `REQ-SRC-ATL105-PDF-001:5778` | Fleet Employee Number: Used for fleet cards that require a Fleet Employee Number; found in Fleet Data Segment when requi | `SEG101-R-002`, `SEG101-R-013` |

## Verdict

⚠️ **The Test Solution covers 66 of 68 AI BRs (97.1%).** 2 AI BR(s) lack a rule mapping — see the section above for details.

The AI Solution generated **2.6× more BRs** than the Test Solution because it emits multiple derivations of the same spec rule (business_rule, field_constraint, field_fixed_value, relationship, supplemental_entity). The Test Solution rules are the **de-duplicated, canonical form** — one rule per unique spec proposition.

⚠️ **1 Test Solution rules have no AI BR support.** These are gaps in the AI Solution's derivation — the Test Solution catalog encodes spec knowledge that the AI pipeline did not extract.
