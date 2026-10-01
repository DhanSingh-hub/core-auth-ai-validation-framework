# Segment 101 (Fleet Data Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3 (August 7, 2026)
**Source Section:** 12.2 Fleet Data Segment (pages 12-9 to 12-11) and 11.1.1 Request companion-segment placement
**External Program Reference (deferred by ATL105):** BUYPASS® Platform Petroleum Industry Processing Specifications (see Section 10.6)
**Training Handbook:** [ATL105 Segment Training Handbook](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md) (8-Item Framework)
**Item Progress:** Item 1 — Coverage Closure (rule catalog derived from specification; provisional items flagged for SME review)

**Common strategy:** [Common LLM Segment Training Strategy](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md)

---

## Learning Module Index (mirrors [Segment 100 Learning Module](../segment-100/README.md))

- [SME and Technical Business Analysis Note](segment-101-sme-tba-learning-note.md)
- [Segment 101 End-to-End Flow](segment-101-flow.md)
- [Coverage Closure](coverage/README.md)
- [Odometer SME/TBA Note](odometer-sme-tba-note.md)
- [Odometer Dependency Flow](odometer-flow.md)
- [Driver / Identification Number SME/TBA Note](driver-identification-number-sme-tba-note.md)
- [Driver / Identification Number Dependency Flow](driver-identification-number-flow.md)
- [Fleet Tag (Auth Completion) SME/TBA Note](fleet-tag-sme-tba-note.md)
- [Fleet Tag Decision Flow](fleet-tag-flow.md)
- [Companion-Segment Compatibility Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
- [Companion-Segment Compatibility Flow](companion-compatibility/companion-segment-compatibility-flow.md)
- [Serialization and Wire-Format Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Serialization and Wire-Format Flow](serialization-wire-format/serialization-wire-format-flow.md)
- [Segment 101 Rule Catalog (authoritative)](coverage/segment-101-rule-catalog.json)
- [Segment 101 Payload Validation Rules](segment-101-validation-rules.json)
- [Segment 101 Canonical Source Anchors](../segment-101-canonical-anchors.md)
- **AI Solution Coverage Report**
  - [JSON](coverage/segment-101-ai-coverage-report.json)
  - [Markdown](coverage/segment-101-ai-coverage-report.md)

---

## 1. Segment Definition

| Attribute | Value | Source |
|---|---|---|
| Segment number | 101 | Section 12.2 |
| Segment name | Fleet Data Segment | Section 12.2 heading |
| Purpose | Fleet-card companion data carried alongside Segment 100 in fleet-eligible financial transaction requests and Auth Completion messages | Section 12.2 opening + Section 11.1.1 Data Section 3 |
| Placement | Data Section No. 3 (companion to Segment 100) | Section 11.1.1 |
| Origin | Device | Section 12.2 opening |
| Segment length range | 001–061 alphanumeric characters (base) | Element 84 valid-values table |
| Included when | All fleet-card transaction requests | Section 12.2 ("The Fleet Data Segment is included in all fleet card transaction requests.") |
| Mutually exclusive with | Segment 145 (Enhanced Fleet Data Segment) | Section 12.2 note ("Merchants should not send the Fleet segment (Data Segment No. 101) and the Enhanced fleet segment together (Data Segment No. 145).") |
| Sub-programs deferred | BUYPASS® Petroleum Industry Processing Specifications | Section 10.6 |

---

## 2. Field Layout

Base fields 1–13 apply to every fleet request; Fleet Tag fields 14–18 apply only to Auth Completion (0220) messages when Host Prompts are supported.

| # | Element | Name | Type / Len | R/O/C | Rule Anchor | JSON field |
|---|---|---|---|---|---|---|
| 1 | 85 | Segment Type | N, 3 | R | `segment-type` (fixed value 101) | `segmentType` |
| 2 | 84 | Segment Length | N, 3 | R | `segment-length` | `segmentLength` |
| 3 | 64 | Odometer | N, 8 | C | `odometer` | `odometer` |
| 4 | 108 | Vehicle Number | AN, 10 | C | `vehicle-number` | `vehicleNumber` |
| 5 | 47 | Job Number | AN, 10 | C | `job-number` | `jobNumber` |
| 6 | 31 | Driver/Identification Number | AN, 10 | C | `driver-identification-number` | `driverIdentificationNumber` |
| 7 | 40 | Fleet Employee Number | AN, 10 | C | `fleet-employee-number` | `fleetEmployeeNumber` |
| 8 | 158 | License # | AN, 10 | C | `license-number` | `licenseNumber` |
| 9 | 159 | Job ID | AN, 12 | C | `job-id` | `jobId` |
| 10 | 160 | Department # | AN, 12 | C | `department-number` | `departmentNumber` |
| 11 | 161 | Customer Data | AN, 12 | C | `customer-data` | `customerData` |
| 12 | 162 | User ID | AN, 12 | C | `user-id` | `userId` |
| 13 | 163 | Vehicle ID# | AN, 8 | C | `vehicle-id-number` | `vehicleIdNumber` |
| 14 | — | Fleet Tag 1 | AN, 34 | C (Auth Completion + Host Prompts only) | `fleet-tag` | `fleetTag1` |
| 15 | — | Fleet Tag 2 | AN, 34 | C (Auth Completion + Host Prompts only) | `fleet-tag` | `fleetTag2` |
| 16 | — | Fleet Tag 3 | AN, 34 | C (Auth Completion + Host Prompts only) | `fleet-tag` | `fleetTag3` |
| 17 | — | Fleet Tag 4 | AN, 34 | C (Auth Completion + Host Prompts only) | `fleet-tag` | `fleetTag4` |
| 18 | — | Fleet Tag 5 | AN, 34 | C (Auth Completion + Host Prompts only) | `fleet-tag` | `fleetTag5` |

### 2.1 Valid Fleet Tag Codes (Auth Completion only)

| Code | Format | Description |
|---|---|---|
| DLS | an3 | Driver License State/Province Abbreviation |
| DLN | an22 | Driver License name  *(spec text "nameation" is a suspected typo — see PROVISIONAL P-02)* |
| PON | an31 | Work Order / P.O. Number |
| INV | an31 | Invoice Number |
| TRP | an15 | Trip Number |
| UNT | an31 | Unit Number |
| TLH | n6 | Trailer Hours / Refer Hours |
| DOB | n8 | Date of Birth |
| ZIP | an9 | ZIP / Postal Code |
| END | an31 | Entered Data (Alphanumeric) |
| MID | an31 | Maintenance ID |
| VIN | an17 | VIN |
| TRA | an15 | Tractor Number |
| HUB | n9 | Hubometer |
| TLR | an15 | Trailer Number |
| CBA | n6 | Cashback Amount |
| VHT | an10 | Vehicle Tag |

### 2.2 Field Separator Rule

All fields are separated by Field Separators. When a field is not populated, the Field Separator is still sent (empty non-trailing fields retain their separators). Unneeded trailing optional fields may be omitted only where the spec permits it.

---

## 3. Rule Set — Approved (Directly Derived from Specification)

Rule ID prefix: `SEG101-R-###`. Every rule carries a **canonical source anchor** matching the anchor format used elsewhere in the framework: `spec | version | section | segment | element | rule`.

| Rule ID | Title | Class | Severity | Source |
|---|---|---|---|---|
| SEG101-R-001 | Segment 101 is a Data Section 3 companion of Segment 100 | structure | error | 11.1.1 |
| SEG101-R-002 | Segment 101 is required in every fleet-card financial transaction request | applicability | error | 12.2 opening |
| SEG101-R-003 | Segment 101 must not coexist with Segment 145 (Enhanced Fleet) in the same message | compatibility | error | 12.2 note |
| SEG101-R-004 | Segment Type is 101 | field | error | 12.2 field 1 (Element 85) |
| SEG101-R-005 | Segment Length is 3 digits representing the segment content length (including Segment Type and Field Separators) | field | error | 12.2 field 2 (Element 84) |
| SEG101-R-006 | Base Segment 101 maximum length is 61 alphanumeric characters | serialization | error | Element 84 valid-values 001–061 |
| SEG101-R-007 | Field order matches Section 12.2 (fields 1–13, then 14–18 for Auth Completion) | serialization | error | 12.2 field-order table |
| SEG101-R-008 | Empty non-trailing fields retain their Field Separators | serialization | error | 12.2 note |
| SEG101-R-009 | Odometer, when populated, is numeric with maximum length 8 | field | error | 12.2 field 3 (Element 64) |
| SEG101-R-010 | Vehicle Number, when populated, is alphanumeric with maximum length 10 | field | error | 12.2 field 4 (Element 108) |
| SEG101-R-011 | Job Number, when populated, is alphanumeric with maximum length 10 | field | error | 12.2 field 5 (Element 47) |
| SEG101-R-012 | Driver/Identification Number, when populated, is alphanumeric with maximum length 10 | field | error | 12.2 field 6 (Element 31) |
| SEG101-R-013 | Fleet Employee Number, when populated, is alphanumeric with maximum length 10 | field | error | 12.2 field 7 (Element 40) |
| SEG101-R-014 | License #, when populated, is alphanumeric with maximum length 10 | field | error | 12.2 field 8 (Element 158) |
| SEG101-R-015 | Job ID, when populated, is alphanumeric with maximum length 12 | field | error | 12.2 field 9 (Element 159) |
| SEG101-R-016 | Department #, when populated, is alphanumeric with maximum length 12 | field | error | 12.2 field 10 (Element 160) |
| SEG101-R-017 | Customer Data, when populated, is alphanumeric with maximum length 12 | field | error | 12.2 field 11 (Element 161) |
| SEG101-R-018 | User ID, when populated, is alphanumeric with maximum length 12; docs/specs/kb/13-data-elements.md adds "any except zero" | field | error | 12.2 field 12 (Element 162) + 13-data-elements.md |
| SEG101-R-019 | Vehicle ID#, when populated, is alphanumeric with maximum length 8 | field | error | 12.2 field 13 (Element 163) |
| SEG101-R-020 | Fleet Tag fields 14–18 are only present in Auth Completion (0220) messages | field | error | 12.2 field 14–18 description |
| SEG101-R-021 | Fleet Tag fields 14–18 are only sent when Host Prompts are supported | field | error | 12.2 opening of tag-code table |
| SEG101-R-022 | Fleet Tag format is 3-byte code + up to 31-byte data payload | field | error | 12.2 tag-code layout |
| SEG101-R-023 | Fleet Tag 3-byte code must be one of the 17 codes in the fleet-tag code table | field | error | 12.2 fleet-tag code table |
| SEG101-R-024 | Fleet Tag data payload conforms to the per-code format (an3, an22, an31, an15, n6, n8, an9, an17, n9, an10) | field | error | 12.2 fleet-tag code table |
| SEG101-R-025 | Segment 101 originates at the device | metadata | info | 12.2 opening |
| SEG101-R-026 | The number of Segment 101 occurrences in one message is one | structure | error | Implied by 12.2 companion-segment role and Element 63 cardinality |

---

## 4. `[PROVISIONAL]` Items Requiring SME / TBA Input

Per **Lesson 5** of the Segment 100 Training Methodology, ambiguous items are flagged at Item 1 and block final Item-8 sign-off until resolved. All P-items below apply to Segment 101.

| ID | Item | ATL105 Evidence | Question for SME / TBA | Blocks |
|---|---|---|---|---|
| P-01 | **Segment length vs. Fleet Tag total** | Element 84 says base length 001–061; Fleet Tags 5 × 34 = 170 in Auth Completion. | Is the 61-byte cap enforced in Auth Completion, or does a different rule extend the max length when Fleet Tags 1–5 are present? What is the *actual* max when tags are present? | Item 5 mutation MUT-006 (length violation) and Item 7 |
| P-02 | **DLN description "Driver License nameation"** | Spec text (page 12-10). | Confirm whether "DLN" carries the **Driver License Name** (an22) or a different attribute (e.g., Driver License Number). "nameation" appears to be an OCR/spec typo. | Item 1 rule catalog, Item 5 MUT-004 for tag values |
| P-03 | **Fleet-eligible card types (Appendix E)** | ATL105 does not enumerate a "fleet subset" of the 36 Appendix E card types. | Which Appendix E card codes are fleet-eligible? (This subset gates when Segment 101 is expected and defines Item 3 independence tests.) | Item 3 independence, Item 6 batch runner |
| P-04 | **Fleet-applicable transaction types (Appendix G)** | Working assumption: `0`, `5`, `6`, `7`, `S`, `C`, `U`. | Confirm the exact subset of Appendix G transaction types that trigger Segment 101, and which of those may carry Fleet Tags in the completion. | Item 4 traceability coverage |
| P-05 | **Response-side presence** | Spec index shows Segment 101 only under Request; user has indicated we should cover both request and response. | Is Segment 101 (or any variant) actually returned in Financial Response messages? If yes, provide the response-side field layout / source anchor. | Item 3 independence, Item 4 traceability |
| P-06 | **Per-field mandatory triggers** | Fields 3–13 are Conditional; ATL105 does not enumerate the conditions. | For each Conditional field, which fleet-program rule (Petroleum Industry Processing Specifications or client contract) mandates its presence? | Item 3 independence semantics |
| P-07 | **Petroleum Industry Processing Specifications scope** | Section 10.6 defers processing rules to that external document. | Is Petroleum Industry Processing Specifications in scope for this Segment 101 training, or REVIEW_REQUIRED / out-of-scope? Version to use if in scope? | Item 2 artifact comparison, Item 4 traceability |
| P-08 | **Segment 145 scope** | 12.2 mutual-exclusion note is the only ATL105 mention here. | Is Segment 145 (Enhanced Fleet) a separate training track, or is it in-scope only through the SEG101-R-003 mutual-exclusion rule? | Item 5 mutation MUT-009 (interdependency) |
| P-09 | **AI-generated Segment 101 artifacts** | Segment 100 has AI-produced BR/TS/TC/TD packages under `test-input/ai-solution/…` and `test-output/…`. Segment 101 has none yet. | Where do the AI-generated Segment 101 BR/TS/TC/TD packages come from (path or upload)? Item 2 compares against them; without them, Item 2 cannot certify. | Item 2 artifact comparison |
| P-10 | **Test-data JSON samples** | Segment 100 has 33 sample JSONs (`lifecycle/`, `transaction-types/`). | Do you have Segment 101 sample JSONs (fleet-card auth, completion with Fleet Tags, cancellation)? If not, may I synthesize `.synthetic.json` fixtures like the Segment 100 lifecycle files? | Item 1 baseline tests, Item 3 independence |

---

## 5. Item 1 Deliverables (This Pass)

Following the methodology's Item 1 checklist for **Coverage Closure**:

- ✅ Read segment definition in ATL105 spec (Section 12.2 extracted)
- ✅ Extracted all field constraints (18 fields, 17 tag codes, 26 rules)
- ✅ Documented source anchors (every rule and every field row references a section/element)
- ✅ Marked ambiguities (10 `[PROVISIONAL]` items flagged in Section 4)
- ⏭ Create `Segment101PayloadValidator.java` (next, once P-02 and P-05 are settled — otherwise its checks would be provisional)
- ⏭ 3+ baseline tests with fleet-card happy-path data (blocked on P-10 test-data availability)

---

## 6. Cross-Reference to Segment 100 Framework

| Segment 100 file | Segment 101 counterpart | Status |
|---|---|---|
| `docs/specs/kb/segment-100/README.md` | `docs/specs/kb/segment-101/README.md` | ✅ this file |
| `docs/specs/kb/segment-100/coverage/segment-100-rule-catalog.json` | `docs/specs/kb/segment-101/coverage/segment-101-rule-catalog.json` | ✅ produced |
| `docs/specs/kb/segment-100/segment-100-flow.md` | `docs/specs/kb/segment-101/segment-101-flow.md` | ✅ produced |
| `docs/specs/kb/segment-100-canonical-anchors.md` | `docs/specs/kb/segment-101-canonical-anchors.md` | ✅ produced |
| `test-output/test-json/knowledge/segment-100-field-knowledge-inventory.json` | `test-output/test-json/knowledge/segment-101-field-knowledge-inventory.json` | ✅ produced |
| `test-output/test-json/knowledge/segment-100-context-model.json` | `test-output/test-json/knowledge/segment-101-context-model.json` | ✅ produced |
| `test-output/test-json/knowledge/segment-100-request-response-lifecycle-knowledge.json` | `test-output/test-json/knowledge/segment-101-request-response-lifecycle-knowledge.json` | ✅ produced |
| `src/main/java/…/Segment100PayloadValidator.java` | `Segment101PayloadValidator.java` | ⏭ Item 1 code (pending P-02, P-05) |
| `src/test/java/…/Segment100PayloadValidatorTest.java` | `Segment101PayloadValidatorTest.java` | ⏭ Item 1 tests (pending P-10) |
| … (Items 2–8) | Segment 101 counterparts | ⏭ Wave 2 after SME input |

---

## 7. Do-Not-Assume Rules (from methodology)

The following are explicit **do-not-infer** boundaries per the Segment 100 training rules and the questionnaire:

1. Do not certify Segment 101 for non-fleet card types.
2. Do not certify Fleet Tags 1–5 in messages other than Auth Completion (0220) with Host Prompts supported.
3. Do not certify a Segment 101 + Segment 145 combination — this is a rejection scenario, not a coverage gap.
4. Do not treat generic Segment 100 fixtures as Segment 101 coverage evidence.
5. Do not certify Petroleum Industry Processing Specifications behavior from ATL105 fixtures alone; it is a separate-domain reference.
6. Do not infer companion Segment 102 (Product Code Data) presence from Segment 101 alone — check Segment 100 fuel/product amounts.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-101-rule-catalog.json) (26 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment 101 |
|---|---|
| SME/TBA learning note | [Learning note](segment-101-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-101-flow.md) |
| Topic deep-dives | [driver-identification-number](driver-identification-number-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [fleet-tag](fleet-tag-sme-tba-note.md) · [odometer](odometer-sme-tba-note.md) |
| Topic flows | [driver-identification-number](driver-identification-number-flow.md) · [field-definitions](field-definitions-flow.md) · [fleet-tag](fleet-tag-flow.md) · [odometer](odometer-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-101-business-requirements.md](segment-101-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-101-rule-catalog.json) |
