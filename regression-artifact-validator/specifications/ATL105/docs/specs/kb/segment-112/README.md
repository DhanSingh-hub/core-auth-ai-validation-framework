# Segment 112 (Additional Information Data Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3 (August 7, 2026)
**Source Section:** 12.11 Additional Information Data Segment (pages 12-30 to 12-31), Elements 115-118 (chapter 13.2, pages 424-429), Appendix K Additional Information Data Layouts (Table IDs 001-047)
**Training Handbook:** [ATL105 Segment Training Handbook](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md) (8-Item Framework)
**Item Progress:** Combined Segment 111/112 envelope flow is implemented. Appendix K selector inventory and recognition chains cover 43 assigned IDs plus reserved/unlisted boundaries; per-table Element 118 layouts remain `REVIEW_REQUIRED` except bounded representation checks for Tables 001, 003, and 004 (see [Appendix K training note](../segment-100/appendix-k-additional-information-training.md)).

**Common strategy:** [Common LLM Segment Training Strategy](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md)

---

## Learning Module Index (mirrors [Segment 100 Learning Module](../segment-100/README.md) / [Segment 113 Learning Module](../segment-113/README.md) topic-note pattern)

- [SME and Technical Business Analysis Note](segment-112-sme-tba-learning-note.md)
- [Segment 112 End-to-End Flow](segment-112-flow.md)
- [Additional Information Indicator Note](additional-information-indicator-sme-tba-note.md)
- [Additional Information Indicator Flow](additional-information-indicator-flow.md)
- [Additional Information Value Catalog (Business Requirements)](additional-information-value-catalog-business-requirements.md)
- [Companion-Segment Compatibility Note (Segment 111 vs. Segment 112)](companion-compatibility/segment-111-112-companion-compatibility-sme-tba-note.md)
- [Companion-Segment Compatibility Flow](companion-compatibility/segment-111-112-companion-compatibility-flow.md)
- [Serialization and Wire-Format Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Serialization and Wire-Format Flow](serialization-wire-format/serialization-wire-format-flow.md)
- [Combined Segment 111/112 Supplemental Information Flow](../supplemental-information-111-112-flow.md)
- [Coverage Closure](coverage/README.md)
- [Segment 112 Rule Catalog (authoritative, draft)](coverage/segment-112-rule-catalog.json)
- [AI-to-Test Requirement Crosswalk](coverage/segment-112-ai-to-test-requirement-crosswalk.md)
- [SME/TBA Input Register](segment-112-sme-tba-input-register.md)

### Segment 100 Topics Not Mirrored (and Why)

- **Account Number**, **Sequence/Lifecycle**, **Partial Approval**, and **Prompt Code** (Segment 100's `account-number-*`, `sequence-lifecycle-*`, `partial-approval-*`, `prompt-code-*` topics): Segment 112's 5-field layout (Section 12.11) contains none of these elements. Segment 112 carries no PAN/token/account reference, no sequence number, no partial-approval indicator, and no prompt code — it is a host-originated, response-only, repeating information carrier. Do not fabricate Segment 112 rules for these concepts; if a future specification revision adds them, extend this catalog then.
- **Final Closure** (Segment 100's `final-closure-*` topic): that topic concerns request-side transaction lifecycle closure (reversal/void/completion correlation). Segment 112 is response-only and carries no lifecycle-correlation fields of its own; any lifecycle behavior is governed by Segment 100/Financial Transaction Response fields, not Segment 112.

---

## 1. Segment Definition

| Attribute | Value | Source |
|---|---|---|
| Segment number | 112 | Section 12.11 |
| Segment name | Additional Information Data Segment | Section 12.11 heading |
| Purpose | Carries host-originated supplemental data (balances, AVS/CVV results, loyalty, tokens, fraud scores, and 40+ other information types) in a Financial Transaction Response | Section 12.11 opening |
| Placement | Response-only; Section 11.1.2 places it in Data Section 2 Field 16/17/18. Relative order against optional Segment 115 remains `REVIEW_REQUIRED` (`SEG112-SME-009`, `SEG115-SME-004`). | Sections 11.1.2, 12.11 |
| Origin | BUYPASS (Host) originates transmission of the segment's content | Section 12.11 |
| Segment length range | 001–999 alphanumeric characters | Section 12.11 |
| Included when | Element 115 (Additional Information Data Segment Flag) equals `1` in the Financial Transaction Response | Section 13.2, Element 115 |
| Message family | Financial Transaction Response only — not a request-side companion segment | Section 12.11; Section 11.1.1 lists Segment 112 only as response-side |
| Companion segments | None required; structurally parallel to (but independent of) Segment 111 Variable Information Data Segment — see the [companion-compatibility note](companion-compatibility/segment-111-112-companion-compatibility-sme-tba-note.md) | Section 12.10 vs 12.11 comparison |
| Future use | "This data segment may be used in the future for additional information" — the Element 116 value list is not closed | Section 12.11 note |

---

## 2. Field Layout

| # | Element | Name | Type / Len | R/O/C | Source | Rule Anchor | JSON field |
|---|---|---|---|---|---|---|---|
| 1 | 85 | Segment Type | N, 3 | R | Host *(SEG112-SME-001 resolved 2026-09-26: field table's "Device" is a transcription error)* | `segment-type` (fixed value 112) | `SegmentType` |
| 2 | 84 | Segment Length | N, 3 | R | Host | `segment-length` | `SegmentLength` |
| 3 | 116 | Additional Information Indicator | N, 3 | R | Host | `additional-information-indicator` | `AdditionalInformationIndicator` |
| 4 | 117 | Additional Information Length | N, 3 | R | Host | `additional-information-length` | `AdditionalInformationLength` |
| 5 | 118 | Additional Information | AN, Var. (max 984) | R | Host | `additional-information-value` | `AdditionalInformation` |

Fields 3–5 form the **Additional Information Section**, a repeating triad (Indicator/Length/Value) repeated once per information type present, up to a combined maximum of **990 bytes**. Maximum Segment 112 length is **999 alphanumeric characters** overall (Section 12.11).

---

## 3. Rule Set — Draft (Directly Derived from Specification)

Rule ID prefix: `SEG112-R-###`. Every rule carries a canonical source anchor: `spec | version | section | segment | element | rule`. See [the authoritative catalog](coverage/segment-112-rule-catalog.json) for the 10 core envelope/applicability rules. Appendix K's 44 selectors are independently inventoried and recognition-tested; per-table Element 118 rules remain partial, as described in the [Appendix K training note](../segment-100/appendix-k-additional-information-training.md).

| Rule ID | Title | Class |
|---|---|---|
| SEG112-R-001 | Segment Type is 112 | field |
| SEG112-R-002 | Segment Length identifies total encoded length | field |
| SEG112-R-003 | Maximum length 999 alphanumeric characters | serialization |
| SEG112-R-004 | Appears only at the end of a Financial Transaction response | structure |
| SEG112-R-005 | Host-originated (not device) | metadata |
| SEG112-R-006 | Additional Information Section repeats by Indicator, max 990 bytes total | serialization |
| SEG112-R-007 | Additional Information Indicator identifies the information type | field |
| SEG112-R-008 | Additional Information Length identifies the length of Element 118 | field |
| SEG112-R-009 | Additional Information carries the value for the paired Indicator/Length | field |
| SEG112-R-010 | Required only when Element 115 (Financial Transaction Response) equals 1 | compatibility |

---

## 4. SME/TBA Resolutions (2026-09-26 Intake)

See the [SME/TBA Input Register](segment-112-sme-tba-input-register.md) for the full table. Summary:

- **SEG112-SME-001** (Segment Type source) → **RESOLVED**: `Source: Device` in the Section 12.11 field table is a transcription error; Host is authoritative for all 5 Segment 112 fields.
- **SEG112-SME-002** (undocumented Element 116 codes `014`/`015`/`033`) → **RESOLVED**: treat as reserved/invalid, same disposition as `002`.
- **SEG112-SME-003** (Element 115 cross-catalog placement) → still OPEN; preliminary finding stands (no current duplication in `segment-100-rule-catalog.json`).
- **SEG112-SME-004** (Appendix K scope) → **RESOLVED**: keep deferred to a separate workstream.
- **SEG112-SME-005** (AI Solution input source) → **RESOLVED**: use the existing cross-segment `atomic-br-ai-crosswalk.csv` extraction (309 rows, 59 matched) as documented in the [AI-to-Test Requirement Crosswalk](coverage/segment-112-ai-to-test-requirement-crosswalk.md).
- **SEG112-SME-006** (likely mis-tagged EMV rows) → still OPEN, pending confirmation.
- **Java validator/mutation-test framework** → **deferred by decision**: this pass is documentation-only; Item 2-8 code (mirroring `Segment101*`...`Segment120*`) is not part of this delivery.

---

## 5. Cross-Reference to Segment 100 / Segment 108 / Segment 113 Frameworks

| Segment 100/108/113 file | Segment 112 counterpart | Status |
|---|---|---|
| `docs/specs/kb/segment-100/README.md`, `segment-113/README.md` | `docs/specs/kb/segment-112/README.md` | ✅ this file |
| `docs/specs/kb/segment-108/coverage/segment-108-rule-catalog.json` | `docs/specs/kb/segment-112/coverage/segment-112-rule-catalog.json` | ✅ expanded (draft) |
| `docs/specs/kb/segment-100/segment-100-flow.md` | `docs/specs/kb/segment-112/segment-112-flow.md` | ✅ produced |
| `docs/specs/kb/segment-108/segment-108-sme-tba-input-register.md` | `docs/specs/kb/segment-112/segment-112-sme-tba-input-register.md` | ✅ produced |
| `src/main/java/…/Segment111PayloadValidator.java` and siblings | `Segment112PayloadValidator.java` and 6 sibling classes | ⏭ next (not part of this documentation pass) |

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-112-rule-catalog.json) (10 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment 112 |
|---|---|
| SME/TBA learning note | [Learning note](segment-112-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-112-flow.md) |
| Topic deep-dives | [additional-information-indicator](additional-information-indicator-sme-tba-note.md) · [applicability-decision](applicability-decision-sme-tba-note.md) · [conditional-dependency-rules](conditional-dependency-rules-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) |
| Topic flows | [additional-information-indicator](additional-information-indicator-flow.md) · [applicability-decision](applicability-decision-flow.md) · [conditional-dependency-rules](conditional-dependency-rules-flow.md) · [field-definitions](field-definitions-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [additional-information-value-catalog-business-requirements.md](additional-information-value-catalog-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) · [segment-111-112-companion-compatibility-sme-tba-note.md](companion-compatibility/segment-111-112-companion-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-112-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-112-sme-tba-input-register.md) |
