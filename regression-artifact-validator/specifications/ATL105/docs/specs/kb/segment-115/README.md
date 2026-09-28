# Segment 115 (Print Data Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3 (August 7, 2026)
**Source Section:** 12.14 Print Data Segment (pages 12-35/249), 11.1.2 Financial Transaction Response and its EMV variant, Elements 115, 150, 152 (chapter 13.2)
**Training Methodology:** [SEGMENT-100-TRAINING-METHODOLOGY.md](../../../test-validation-strategy/SEGMENT-100-TRAINING-METHODOLOGY.md) (8-Item Framework)
**Item Progress:** Item 1 — Coverage Closure in progress; SME intake requested (0 of 6 open items resolved, see [SME/TBA Input Register](segment-115-sme-tba-input-register.md))

**Common strategy:** [Common LLM Segment Training Strategy](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md)

---

## Learning Module Index (mirrors [Segment 100 Learning Module](../segment-100/README.md) and [Segment 114 Learning Module](../segment-114/README.md) topic-note pattern)

- [SME and Technical Business Analysis Note](segment-115-sme-tba-learning-note.md)
- [Segment 115 End-to-End Flow](segment-115-flow.md)
- [AI-Generated vs Test-Generated Requirement Comparison](segment-115-ai-vs-test-requirement-comparison.md)
- [Response-Inclusion Condition Note](response-inclusion-condition-sme-tba-note.md)
- [Response-Inclusion Condition Flow](response-inclusion-condition-flow.md)
- [Coverage Closure](coverage/README.md)
- [Companion-Segment Compatibility Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
- [Companion-Segment Compatibility Flow](companion-compatibility/companion-segment-compatibility-flow.md)
- [Serialization and Wire-Format Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Serialization and Wire-Format Flow](serialization-wire-format/serialization-wire-format-flow.md)
- [Segment 115 Rule Catalog (authoritative)](coverage/segment-115-rule-catalog.json)
- [SME/TBA Input Register](segment-115-sme-tba-input-register.md)

### Segment 100 / Segment 114 Topics Not Mirrored (and Why)

Segment 115 has only 3 fields and is response-only, so the request-side topics used for other segments are **not** mirrored here:

- **Account Number / Card-Not-Present, Prompt Code, Partial Approval** (Segment 100/108 topics): Segment 115 carries no card, account, prompt-code, or partial-approval field — it is a pure host-originated print payload.
- **Lifecycle / Final Closure**: Segment 115 has no correlation or unit-of-work field of its own; its lifecycle question (request-only vs response-only, and whether it ever appears in the Loyalty response) is captured directly in the rule catalog (`SEG115-R-001`, `SEG115-R-013`) and learning note rather than a separate topic pair.

A **new** topic pair not present in Segment 100/108/114 is included here: **Response-Inclusion Condition** — because Segment 115 is the first *conditionally-included, response-side* segment trained in this KB, and its inclusion logic (Element 115 flag + Loyalty Information Version = 2) is genuinely novel and only partially documented.

---

## 1. Segment Definition

| Attribute | Value | Source |
|---|---|---|
| Segment number | 115 | Section 12.14 |
| Segment name | Print Data Segment | Section 12.14 heading |
| Purpose | Carries a large block of host-originated print data (documented use: Blackhawk phone activation/recharge receipt terms & conditions; inferred use: loyalty receipt data) | Section 12.14 opening + field note |
| Placement | Field No. 17/18/19 in Data Section No. 2 of the Financial Transaction Response (and its EMV variant) | Section 11.1.2 |
| Origin | Host | Section 12.14 (all fields "Source: Host") |
| Segment length range | 01–1,009 alphanumeric characters (conflicting figures exist — see `SEG115-R-005`) | Section 12.14 opening |
| Included when | Element 115 (Additional Information Data Segment Flag) indicates a segment follows AND the request's Loyalty Information Version (Element 150) equals 2 | Section 11.1.2 |
| Message family | (EMV) Financial Transaction Response only — **response-side exclusive**; never appears in any Request message | Section 11.1.2; no Request Data Section 3 list includes Segment 115 |
| Companion segments | Segment 112 (disputed — see `SEG115-R-010`), Segment 120 + Segment 131 (EMV variant only, `SEG115-R-011`) | Section 11.1.2 and its EMV variant |

---

## 2. Field Layout

| # | Element | Name | Type / Len | R/O/C | Rule Anchor | JSON field |
|---|---|---|---|---|---|---|
| 1 | 85 | Segment Type | N, 3 | R | `segment-type` (fixed value 115, explicitly cited) | `SegmentType` |
| 2 | 84 | Segment Length | N, 4 | R | `segment-length-4-digit` | `SegmentLength` |
| 3 | 152 | Print Data | AN, 999 | R | `print-data` | `PrintData` |

Maximum Segment 115 length is **1,009 alphanumeric characters** per Section 12.14 (`SEG115-R-005`, provisional — see the conflicting 910 and 999 figures in other layout tables). This is the same 3-field shape as Segment 114, but response-side and Host-originated instead of request-side and Device-originated.

### 2.1 The First No-Trailing-Separator Segment

Segments 108 and 114 both explicitly document a trailing Field Separator after their last field. Segment 115 is the first documented exception: Section 12.14 states only two separators exist (between fields 1-2 and 2-3), with **no** separator after Print Data (`SEG115-R-007`, high confidence — independently corroborated by the AI Solution Team's own `BR-249-5` statement).

---

## 3. Rule Set — Approved (Directly Derived from Specification)

Rule ID prefix: `SEG115-R-###`. Every rule carries a canonical source anchor: `spec | version | section | segment | element | rule`. See [the authoritative catalog](coverage/segment-115-rule-catalog.json) for the full machine-readable list (13 rules).

| Rule ID | Title | Class |
|---|---|---|
| SEG115-R-001 | Response-only; never in a Request message | structure |
| SEG115-R-002 | Conditional inclusion (Element 115 flag + Loyalty Version = 2), provisional mechanism | applicability |
| SEG115-R-003 | Segment Type fixed value 115 (explicit citation, no ambiguity) | field |
| SEG115-R-004 | Segment Length is 4 digits | field |
| SEG115-R-005 | Maximum length 1,009 characters (provisional vs conflicting 910/999) | serialization |
| SEG115-R-006 | Field order: Segment Type, Segment Length, Print Data | serialization |
| SEG115-R-007 | Only 2 Field Separators; no trailing separator | serialization |
| SEG115-R-008 | Print Data required, Blackhawk T&C content documented (provisional length 999 vs 900) | field |
| SEG115-R-009 | Originates at the Host | metadata |
| SEG115-R-010 | Apparent contradiction: "no other data segments" vs layout table showing Segment 112 co-occurrence (provisional) | compatibility |
| SEG115-R-011 | EMV variant: co-occurs with Segment 120, precedes Segment 131 | compatibility |
| SEG115-R-012 | Plausible Loyalty Print Data vehicle (inferred, provisional) | lifecycle |
| SEG115-R-013 | Possible presence in Loyalty Card Transaction Response (inferred, provisional) | lifecycle |

---

## 4. `[PROVISIONAL]` Items Requiring SME / TBA Input

See the catalog's `provisionalItems` array and the [SME/TBA Input Register](segment-115-sme-tba-input-register.md) for the authoritative, trackable list. All 6 items are **open** — no SME/TBA intake has occurred yet for Segment 115:

- **P-01 / SEG115-SME-001**: Maximum length 1,009 vs 910 vs 999 (three-way table discrepancy — worse than Segment 108's two-way conflict).
- **P-02 / SEG115-SME-003**: Print Data field length 999 vs 900.
- **P-03 / SEG115-SME-004**: Whether "no other data segments" forbids Segment 112 + Segment 115 co-occurrence — a genuine contradiction also surfaced by the AI Solution Team's own statement.
- **P-04 / SEG115-SME-005**: Whether Segment 115 is the Loyalty Print Data vehicle, and whether it can appear in the Loyalty Card Transaction Response.
- **P-05 / SEG115-SME-002**: Exact decision logic distinguishing Segment 112 vs Segment 115 inclusion from Element 115's single documented flag.
- **P-06 / SEG115-SME-006**: Location of a dedicated Segment 115 AI artifact package and real response sample data.

---

## 5. AI-Generated vs Test-Generated Requirement Matching

A full extraction and side-by-side comparison of every AI Solution Team requirement statement referencing Segment 115 (`BR-249-1` through `BR-249-7`, all from source page 249) against the Test Team's independently-derived `SEG115-R-###` rule catalog is in [segment-115-ai-vs-test-requirement-comparison.md](segment-115-ai-vs-test-requirement-comparison.md). Headline finding: every `BR-249-*` statement was auto-matched by the AI Solution Team's tooling against the **wrong segment** (101, 103, 104, 108, 111, 113, 123, 130, 135), and one AI-generated requirement ID (`BR-249-5`) is a confirmed collision reused for two unrelated statements across different per-segment BR files.

---

## 6. Cross-Reference to Segment 114 Framework

| Segment 114 file | Segment 115 counterpart | Status |
|---|---|---|
| `docs/specs/kb/segment-114/README.md` | `docs/specs/kb/segment-115/README.md` | ✅ this file |
| `docs/specs/kb/segment-114/coverage/segment-114-rule-catalog.json` | `docs/specs/kb/segment-115/coverage/segment-115-rule-catalog.json` | ✅ produced |
| `docs/specs/kb/segment-114/segment-114-flow.md` | `docs/specs/kb/segment-115/segment-115-flow.md` | ✅ produced |
| `docs/specs/kb/segment-114/segment-114-sme-tba-input-register.md` | `docs/specs/kb/segment-115/segment-115-sme-tba-input-register.md` | ✅ produced |
| `docs/specs/kb/segment-114/segment-114-ai-vs-test-requirement-comparison.md` | `docs/specs/kb/segment-115/segment-115-ai-vs-test-requirement-comparison.md` | ✅ produced |
| — (no equivalent for Segment 114) | `docs/specs/kb/segment-115/response-inclusion-condition-*.md` | ✅ produced (new topic for this training pass) |
| `src/main/java/…/Segment114PayloadValidator.java` | `Segment115PayloadValidator.java` | ⏭ next (blocked by open SME items) |

---

## 7. Do-Not-Assume Rules

1. Do not certify Segment 115 in any Request message — it is exclusively response-side, unlike every other segment trained so far.
2. Do not serialize a trailing Field Separator after Print Data — Segment 115 is the first documented exception to that pattern.
3. Do not pick a maximum length without flagging the 1,009 vs 910 vs 999 three-way conflict (`SEG115-SME-001`, open).
4. Do not certify or reject a Segment 112 + Segment 115 co-occurrence scenario — this is a genuine, unresolved specification contradiction (`SEG115-SME-004`, open), independently surfaced by the AI Solution Team's own extraction.
5. Do not assert Segment 115 carries "Loyalty Print Data" as settled fact — this is an inference pending SME confirmation (`SEG115-SME-005`, open), and is directly linked to Segment 108's still-open `SEG108-SME-003`.
6. Do not trust `source_rule_id` alone when cross-referencing older per-segment AI BR files — `BR-249-5` is a confirmed ID collision reused for two different statements (see `AI-DEFECT-115-001` in the rule catalog).

