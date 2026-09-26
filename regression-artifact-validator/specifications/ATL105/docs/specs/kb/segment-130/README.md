# Segment 130 (EMV Request Data Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3 (August 7, 2026)
**Source Section:** 12.20 EMV Request Data Segment (pages 12-53/267 to 268), 11.8.1 EMV Financial Transaction Request, Appendix R (EMV Chip Data Example), Appendix S (CA Public Key File), Elements 84, 85, 118, 187-192
**Training Methodology:** [SEGMENT-100-TRAINING-METHODOLOGY.md](../../../test-validation-strategy/SEGMENT-100-TRAINING-METHODOLOGY.md) (8-Item Framework)
**Item Progress:** Item 1 — Coverage Closure in progress, building on a **pre-existing partial Test Team baseline** (see Section 5). SME intake requested (0 of 6 open items resolved, see [SME/TBA Input Register](segment-130-sme-tba-input-register.md))

**Common strategy:** [Common LLM Segment Training Strategy](../COMMON-LLM-SEGMENT-TRAINING-STRATEGY.md)

---

## Learning Module Index (mirrors [Segment 100 Learning Module](../segment-100/README.md) topic-note pattern)

- [SME and Technical Business Analysis Note](segment-130-sme-tba-learning-note.md)
- [Segment 130 End-to-End Flow](segment-130-flow.md)
- [AI-Generated vs Test-Generated Requirement Comparison](segment-130-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md)
- [Companion-Segment Compatibility Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
- [Companion-Segment Compatibility Flow](companion-compatibility/companion-segment-compatibility-flow.md)
- [Serialization and Wire-Format Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Serialization and Wire-Format Flow](serialization-wire-format/serialization-wire-format-flow.md)
- [Segment 130 Rule Catalog (authoritative)](coverage/segment-130-rule-catalog.json)
- [SME/TBA Input Register](segment-130-sme-tba-input-register.md)

---

## 1. Segment Definition

| Attribute | Value | Source |
|---|---|---|
| Segment number | 130 | Section 12.20 |
| Segment name | EMV Request Data Segment | Section 12.20 heading |
| Purpose | Carries EMV chip-card transaction data (TLV-encoded) | Section 12.20 opening |
| Placement | Data Section 3 of the EMV Financial Transaction Request | Section 11.8.1 |
| Origin | Device | Section 12.20 |
| Segment length range | 001–3,043 alphanumeric characters | Section 12.20 opening (`SEG130-R-004`, provisional on a possible second OCR-ambiguous figure) |
| Included when | Required for every EMV Financial Transaction Request — "the only segment required for all EMV financial transactions" | Section 11.8.1 / generic Financial Transaction Request Data Section 3 table |
| Message family | EMV Financial Transaction Request only; companions are 101, 102, 104, 111 (Segment 103/EBT is excluded, unlike the generic Financial Transaction Request) | Section 11.8.1 |

---

## 2. Field Layout

| # | Element | Name | Type / Len | R/O/C | Rule Anchor | JSON field |
|---|---|---|---|---|---|---|
| 1 | 85 | Segment Type | N, 3 | R | `segment-130-type-fixed-value` (fixed value 130) | `SegmentType` |
| 2 | 84 | Segment Length | N, 4 | R | `segment-130-length-format` | `SegmentLength` |
| 3 | 187 | CA Public Key File Checksum | AN, 25 | O | `segment-130-ca-key-checksum` | `CAPublicKeyFileChecksum` |
| 4 | 188 | EMV Card Sequence Number | N, 3 | C | `emv-card-sequence-number` | `EMVCardSequenceNumber` |
| 5 | 189 | EMV Chip Data Length | N, 3 | R | `segment-130-chip-data-length-range` (000-999) | `EMVChipDataLength` |
| 6 | 190 | EMV Chip Data | AN, 999 | R | `emv-chip-data-tlv-format` | `EMVChipData` |
| 7* | 191 | EMV Additional Information Indicator | N, 3 | R (when section present) | `emv-additional-info-repetition` | `EMVAdditionalInformationIndicator` |
| 8* | 192 | EMV Additional Information Length | N, 3 | R (when section present) | `emv-additional-info-repetition` | `EMVAdditionalInformationLength` |
| 9* | 118 | EMV Additional Information | AN, Var. | R (when section present) | `emv-additional-info-repetition` | `EMVAdditionalInformation` |

`*` Fields 7-9 form a repeating group (the EMV Additional Information Section), capped at 2,000 bytes total, with no internal separators — structurally identical to Segment 111's Variable Information Section repetition rule.

Maximum Segment 130 length is **3,043 alphanumeric characters** (`SEG130-R-004`). Note Element 118's reuse across Segment 112 ("Additional Information") and Segment 130 ("EMV Additional Information") — same number, different fields (`SEG130-R-012`).

---

## 3. Rule Set — Approved (Directly Derived from Specification + Pre-Existing Test Baseline)

Rule ID prefix: `SEG130-R-###`. See [the authoritative catalog](coverage/segment-130-rule-catalog.json) for the full machine-readable list (16 rules).

| Rule ID | Title | Class |
|---|---|---|
| SEG130-R-001 | Required for every EMV Financial Transaction Request | applicability |
| SEG130-R-002 | Segment Type fixed value 130 | field |
| SEG130-R-003 | Segment Length 4 digits | field |
| SEG130-R-004 | Maximum length 3,043 (provisional on OCR-ambiguous second figure) | serialization |
| SEG130-R-005 | CA Public Key File Checksum (external-fixture-required for authenticity) | field |
| SEG130-R-006 | EMV Card Sequence Number | field |
| SEG130-R-007 | EMV Chip Data Length, valid 000-999 | field |
| SEG130-R-008 | EMV Chip Data TLV structure | field |
| SEG130-R-009 | Cross-field consistency with Segment 100 (review required) | field |
| SEG130-R-010 | Cryptogram (9F26) authenticity — out of scope | field |
| SEG130-R-011 | EMV Additional Information Section repetition, max 2,000 bytes | structure |
| SEG130-R-012 | Element 118 cross-segment reuse (112 vs 130) | metadata |
| SEG130-R-013 | Separator rules (fixed fields + repeating section) | serialization |
| SEG130-R-014 | Originates at the device | metadata |
| SEG130-R-015 | CA Public Key File Checksum echoed by Segment 131 | lifecycle |
| SEG130-R-016 | CA Public Key File (CA_KEYS) header/record layout | field |

---

## 4. `[PROVISIONAL]` Items Requiring SME / TBA Input

See the [SME/TBA Input Register](segment-130-sme-tba-input-register.md). All 6 items are open:

- **P-01 / SEG130-SME-001**: Possible second max-length figure in the generic layout table (OCR-ambiguous) needing visual PDF confirmation.
- **P-02 / SEG130-SME-002**: Real CA Public Key File needed for genuine checksum/AID-to-key authenticity (already `EXTERNAL_FIXTURE_REQUIRED` in the pre-existing Appendix S package).
- **P-03 / SEG130-SME-003**: Complete EMV chip-value cross-field consistency rules vs Segment 100 (already `REVIEW_REQUIRED` in the pre-existing Appendix R package).
- **P-04 / SEG130-SME-004**: Confirm cryptogram (9F26) authenticity remains permanently out of scope.
- **P-05 / SEG130-SME-005**: Whether the existing dedicated AI Solution Team BR package should be treated as canonical for Item 2.
- **P-06 / SEG130-SME-006**: Whether Appendix T (EMV Additional Information Table IDs) is in scope for this training pass.

---

## 5. Pre-Existing Test Team Baseline (Important Context)

Unlike Segments 108/114/115 (which had no dedicated Test Team artifacts before this training pass), Segment 130 already had:

- `test-json/segment-130-core-structure-package.json` — Segment Type/Length and EMV Chip Data Length baseline (4 BRs).
- `test-json/appendices/appendix-r-segment-100-coverage.json` — EMV Chip Data TLV format and cross-field consistency (4 BRs, 1 explicitly `EXTERNAL_FIXTURE_REQUIRED`).
- `test-json/appendices/appendix-s-segment-100-coverage.json` — CA Public Key File layout and AID-to-key link (3 BRs, 1 `EXTERNAL_FIXTURE_REQUIRED`).

This training pass **consolidates** these pre-existing BRs into `segment-130-rule-catalog.json` (see Section 3) rather than duplicating or contradicting them, and folds their known gaps into the standard SME/TBA Input Register format used across all segments in this KB.

---

## 6. AI-Generated vs Test-Generated Requirement Matching

See [segment-130-ai-vs-test-requirement-comparison.md](segment-130-ai-vs-test-requirement-comparison.md). Headline finding: Segment 130 is the **first segment in this training pass where the AI Solution Team's statements largely align** with the Test Team's rules, because a dedicated AI BR package and dedicated coverage-report files already existed for Segment 130 (unlike Segments 108/114/115, where every AI statement was auto-matched against the wrong segment).

---

## 7. Do-Not-Assume Rules

1. Do not assume Segment 103 (EBT) can accompany Segment 130 — it is excluded from the EMV Financial Transaction Request's companion list.
2. Do not attempt to synthesize CA Public Key File authenticity or cryptogram (9F26) verification — both are `EXTERNAL_FIXTURE_REQUIRED` per the pre-existing Appendix S/R packages.
3. Do not emit a Field Separator between EMV Additional Information Section repetitions, or omit the single trailing separator after the last repetition.
4. Do not conflate Element 118 in Segment 112 with Element 118 in Segment 130 — same number, independent fields.
5. Do not certify EMV chip cross-field consistency (amount/currency/transaction-type vs Segment 100) as `COVERED` — it remains `REVIEW_REQUIRED` pending `SEG130-SME-003`.
6. Do not pick a maximum length without flagging the possible OCR-ambiguous second figure (`SEG130-SME-001`, open).
