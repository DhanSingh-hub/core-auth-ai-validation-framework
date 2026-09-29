# Segment 114 (SKU Data Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3 (August 7, 2026)
**Source Section:** 12.13 SKU Data Segment (pages 12-34/248), 11.2 Loyalty Card Transactions (pages 11-11 to 11-13/171-172), 11.1.1 / 11.3.1 / 11.9.1 Data Section 3 companion lists, Element 149 (chapter 13.2)
**Training Handbook:** [ATL105 Segment Training Handbook](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md) (8-Item Framework)
**Item Progress:** Item 1 — Coverage Closure COMPLETE; SME intake held 2026-09-26 (6 of 6 open items resolved, see [SME/TBA Input Register](segment-114-sme-tba-input-register.md))

**Common strategy:** [Common LLM Segment Training Strategy](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md)

---

## Learning Module Index (mirrors [Segment 100 Learning Module](../segment-100/README.md) and [Segment 108 Learning Module](../segment-108/README.md) topic-note pattern)

- [SME and Technical Business Analysis Note](segment-114-sme-tba-learning-note.md)
- [Segment 114 End-to-End Flow](segment-114-flow.md)
- [AI-Generated vs Test-Generated Requirement Comparison](segment-114-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md)
- [Companion-Segment Compatibility Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
- [Companion-Segment Compatibility Flow](companion-compatibility/companion-segment-compatibility-flow.md)
- [Serialization and Wire-Format Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Serialization and Wire-Format Flow](serialization-wire-format/serialization-wire-format-flow.md)
- [Segment 114 Rule Catalog (authoritative)](coverage/segment-114-rule-catalog.json)
- [SME/TBA Input Register](segment-114-sme-tba-input-register.md)

### Segment 100 / Segment 108 Topics Not Mirrored (and Why)

Segment 114 has only 3 fields (Segment Type, Segment Length, SKU Data) and no field-level decision logic, so the following topic notes used for larger segments are **not** mirrored here — do not fabricate them:

- **Account Number / Card-Not-Present** (Segment 108's `account-number-*` topic): no account, card, or cardholder data element exists in Segment 114.
- **Prompt Code / Card Type Routing** (Segment 108's `prompt-code-*` topic): Segment 114 carries no prompt-code or card-type field.
- **Lifecycle and Correlation** (Segment 108's `lifecycle-*` topic): Segment 114 has no unit-of-work or correlation field; its only lifecycle fact (request-only) is captured in the rule catalog (`SEG114-R-013`) and the SME/TBA learning note.
- **Partial Approval** (Segment 100's `partial-approval-*` topic): no specification text applies partial-approval capability to the SKU Data Segment.

---

## 1. Segment Definition

| Attribute | Value | Source |
|---|---|---|
| Segment number | 114 | Section 12.13 |
| Segment name | SKU Data Segment | Section 12.13 heading |
| Purpose | Carries bar code SKU data | Section 12.13 opening |
| Placement | Always Field No. 5 in Data Section No. 3 | Section 12.13 opening ("It always appears in Field No. 5 in Data Section No. 3.") |
| Origin | Device | Section 12.13 opening |
| Segment length range | 001–1010 alphanumeric characters (`a-z`/`A-Z`) | Section 12.13 opening; see `SEG114-R-005` for a conflicting "1009" figure in the Loyalty Card Transaction Request layout table |
| Included when | Loyalty Card Transaction Request, optional | Section 11.2.1 Data Section 3 table (Field No. 5, Entry `O`) |
| Message family | Loyalty Card Transaction Request only — **not** documented as a Financial Transaction Request, ECA/TeleCheck Service Transaction Request, or CA Public Key File Load Request companion | Sections 11.1.1, 11.3.1, 11.9.1 Data Section 3 companion lists (none list Segment 114) |
| Required co-segment | Segment 108 (Loyalty Card Data Segment) — always present whenever Segment 114 is present | Section 11.2.1 Data Section 3 table (Field No. 4 = 108, Required; Field No. 5 = 114, Optional) |

---

## 2. Field Layout

| # | Element | Name | Type / Len | R/O/C | Rule Anchor | JSON field |
|---|---|---|---|---|---|---|
| 1 | 85 | Segment Type | N, 3 | R | `segment-type` (114, context-inferred — see `SEG114-R-003`) | `SegmentType` |
| 2 | 84 | Segment Length | N, 4 | R | `segment-length-4-digit` | `SegmentLength` |
| 3 | 149 | SKU Data | AN, 1000 | R | `sku-data` | `SkuData` |

Maximum Segment 114 length is **1010 alphanumeric characters** per Section 12.13 (`SEG114-R-005`, provisional — see the conflicting "1009" figure in the Loyalty Card Transaction Request layout table). This is the smallest field layout of any segment trained so far (3 fields, versus Segment 108's 15).

### 2.1 Why Segment Length Is 4 Digits

Segment 114 is one of exactly seven segments across the entire specification that use a 4-digit Segment Length: EBT Data Segment (103), **SKU Data Segment (114)**, Print Data Segment (115), Proprietary Data Load Segment (118), Print Data 2 Segment (120), EMV Request Data Segment (130), and EMV Response Data Segment (131). Every other segment (including Segment 108) uses a 3-digit Segment Length. This cross-segment fact is independently confirmed in [Segment 120's rule catalog](../segment-120/coverage/segment-120-rule-catalog.json) and [segment-length-encoding-sme-tba-note.md](../segment-120/segment-length-encoding-sme-tba-note.md).

---

## 3. Rule Set — Approved (Directly Derived from Specification)

Rule ID prefix: `SEG114-R-###`. Every rule carries a canonical source anchor: `spec | version | section | segment | element | rule`. See [the authoritative catalog](coverage/segment-114-rule-catalog.json) for the full machine-readable list (13 rules).

| Rule ID | Title | Class |
|---|---|---|
| SEG114-R-001 | Exclusive to Loyalty Card Transaction Request Data Section 3 | structure |
| SEG114-R-002 | Optional, Field No. 5, alongside required Segment 108 | applicability |
| SEG114-R-003 | Segment Type identifies 114 (context-inferred, not a printed "Fixed value") | field |
| SEG114-R-004 | Segment Length is 4 digits, valid values 0001-1010 | field |
| SEG114-R-005 | Maximum length 1010 characters (provisional vs conflicting "1009") | serialization |
| SEG114-R-006 | Field order: Segment Type, Segment Length, SKU Data | serialization |
| SEG114-R-007 | Field Separators required, including trailing | serialization |
| SEG114-R-008 | SKU Data alphanumeric max 1000, required | field |
| SEG114-R-009 | Originates at the device | metadata |
| SEG114-R-010 | May repeat, once per scanned SKU (resolved 2026-09-26) | structure |
| SEG114-R-011 | Never appears without Segment 108 | compatibility |
| SEG114-R-012 | AI-asserted Financial Transaction Request companion REJECTED (resolved 2026-09-26) | compatibility |
| SEG114-R-013 | Not present in the Loyalty Card Transaction Response (resolved 2026-09-26) | lifecycle |

---

## 4. `[PROVISIONAL]` Items — All Resolved 2026-09-26

See the catalog's `provisionalItems` array and the [SME/TBA Input Register](segment-114-sme-tba-input-register.md) for the authoritative, trackable list. All 6 items were resolved during the 2026-09-26 SME/TBA intake:

- **P-01 / SEG114-SME-001**: RESOLVED — 1010 is authoritative; the layout table's 1009 is a spec typo.
- **P-02 / SEG114-SME-002**: RESOLVED — the AI-asserted Financial Transaction Request relationship is rejected as an AI defect.
- **P-03 / SEG114-SME-003**: RESOLVED — confirmed request-only, never in the response.
- **P-04 / SEG114-SME-004**: RESOLVED — SegmentType == 114 is enforced as a hard rule.
- **P-05 / SEG114-SME-005**: RESOLVED — proceed with synthesized `.synthetic.json` fixtures pending real data.
- **P-06 / SEG114-SME-006**: RESOLVED — Segment 114 can repeat, once per scanned SKU; not limited to zero-or-one.

---

## 5. AI-Generated vs Test-Generated Requirement Matching

A full extraction and side-by-side comparison of every AI Solution Team requirement statement that references Segment 114 (`BR-248-1` through `BR-248-4`, the field-level requirements, and the two `REL-ENT-SEG-114-*` relationship statements) against the Test Team's independently-derived `SEG114-R-###` rule catalog is in [segment-114-ai-vs-test-requirement-comparison.md](segment-114-ai-vs-test-requirement-comparison.md). Headline finding: the AI Solution Team's automated segment-matcher paired all four `BR-248-*` statements against the **wrong segment's** business requirements (Segment 103, 104, 111, and 101 respectively) with `POTENTIAL_MATCH_REVIEW_REQUIRED` / `MATCHED_SEMANTICS_ONLY` status — none reached a `CONFIRMED` match against a Segment 114-specific Test rule, because no Segment 114-specific Test rule existed before this training pass.

---

## 6. Cross-Reference to Segment 108 Framework

| Segment 108 file | Segment 114 counterpart | Status |
|---|---|---|
| `docs/specs/kb/segment-108/README.md` | `docs/specs/kb/segment-114/README.md` | ✅ this file |
| `docs/specs/kb/segment-108/coverage/segment-108-rule-catalog.json` | `docs/specs/kb/segment-114/coverage/segment-114-rule-catalog.json` | ✅ produced |
| `docs/specs/kb/segment-108/segment-108-flow.md` | `docs/specs/kb/segment-114/segment-114-flow.md` | ✅ produced |
| `docs/specs/kb/segment-108/segment-108-sme-tba-input-register.md` | `docs/specs/kb/segment-114/segment-114-sme-tba-input-register.md` | ✅ produced |
| — (no equivalent for Segment 108) | `docs/specs/kb/segment-114/segment-114-ai-vs-test-requirement-comparison.md` | ✅ produced (new artifact for this training pass) |
| `src/main/java/…/Segment108PayloadValidator.java` | `Segment114PayloadValidator.java` | ⏭ next (blocked by open SME items) |
| `src/main/java/…/Segment108ArtifactComparison.java` … `ConsolidatedReport.java` | `Segment114*` (7 more classes) | ⏭ next |

---

## 7. Do-Not-Assume Rules

1. Do not certify a Financial Transaction Request + Segment 114 combination as supported — no specification citation in Sections 11.1.1, 11.3.1, or 11.9.1 lists Segment 114 among their Data Section 3 companions; the AI Solution Team's `REL-ENT-SEG-114-FINANCIAL_TRANSACTION_REQUEST` statement is **REJECTED** (`SEG114-SME-002`, resolved 2026-09-26) and must be reported back to the AI Solution Team as a defect.
2. Do not certify Segment 114 appearing without Segment 108 — its only documented context (Section 11.2.1) always pairs it with the required Segment 108.
3. Max length is **confirmed 1010** (`SEG114-SME-001`, resolved 2026-09-26); the 1009 figure in the Loyalty Card Transaction Request layout table is a spec typo.
4. Segment Type fixed value 114 is **confirmed enforceable** (`SEG114-SME-004`, resolved 2026-09-26) even though Section 12.13 does not print an explicit "Fixed value: 114" phrase.
5. Do not treat an AI Solution Team `POTENTIAL_MATCH_REVIEW_REQUIRED` or `MATCHED_SEMANTICS_ONLY` crosswalk result as a confirmed equivalence — every existing AI-to-Test crosswalk entry for Segment 114's `BR-248-*` statements matched against a **different segment's** rule (103, 104, 111, 101); see the [requirement comparison](segment-114-ai-vs-test-requirement-comparison.md).
6. Segment 114 **can repeat** within a message, once per scanned SKU (`SEG114-SME-006`, resolved 2026-09-26) — do not enforce a zero-or-one occurrence limit.

<!-- segment-100-parity-index -->
## Segment 100 Parity Index

Structure mirrors the [Segment 100 Learning Module](../segment-100/README.md). Files added on 2026-09-28 are derived from the [rule catalog](coverage/segment-114-rule-catalog.json) (13 rules) and ATL105 Chapter 13 element definitions; existing files were not modified.

| Segment 100 component | Segment 114 |
|---|---|
| SME/TBA learning note | [Learning note](segment-114-sme-tba-learning-note.md) |
| End-to-end flow | [Flow](segment-114-flow.md) |
| Topic deep-dives | [applicability-decision](applicability-decision-sme-tba-note.md) · [conditional-dependency-rules](conditional-dependency-rules-sme-tba-note.md) · [field-definitions](field-definitions-sme-tba-note.md) · [lifecycle-response-correlation](lifecycle-response-correlation-sme-tba-note.md) |
| Topic flows | [applicability-decision](applicability-decision-flow.md) · [conditional-dependency-rules](conditional-dependency-rules-flow.md) · [field-definitions](field-definitions-flow.md) · [lifecycle-response-correlation](lifecycle-response-correlation-flow.md) |
| Final closure | [Note](final-closure-sme-tba-note.md) · [Flow](final-closure-flow.md) |
| Business requirements | [segment-114-business-requirements.md](segment-114-business-requirements.md) |
| Companion compatibility | [companion-segment-compatibility-sme-tba-note.md](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) |
| Serialization / wire format | [Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md) · [Flow](serialization-wire-format/serialization-wire-format-flow.md) |
| Coverage | [Coverage closure](coverage/README.md) · [Rule catalog](coverage/segment-114-rule-catalog.json) |
| SME/TBA input register | [Input register](segment-114-sme-tba-input-register.md) |
| AI vs Test comparison | [Comparison](segment-114-ai-vs-test-requirement-comparison.md) |
