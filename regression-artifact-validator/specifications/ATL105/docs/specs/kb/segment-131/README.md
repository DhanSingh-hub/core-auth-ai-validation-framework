# Segment 131 (EMV Response Data Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3 (August 7, 2026)
**Source Section:** 12.21 EMV Response Data Segment (pages 12-55/269 to 270), 11.1.2-EMV EMV Financial Transaction Response layout, Elements 84, 85, 118, 187, 189-192
**Training Methodology:** [SEGMENT-100-TRAINING-METHODOLOGY.md](../../../test-validation-strategy/SEGMENT-100-TRAINING-METHODOLOGY.md) (8-Item Framework)
**Item Progress:** Item 1 — Coverage Closure in progress; SME intake requested (0 of 4 open items resolved, see [SME/TBA Input Register](segment-131-sme-tba-input-register.md))

---

## Learning Module Index

- [SME and Technical Business Analysis Note](segment-131-sme-tba-learning-note.md)
- [Segment 131 End-to-End Flow](segment-131-flow.md)
- [AI-Generated vs Test-Generated Requirement Comparison](segment-131-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md)
- [Companion-Segment Compatibility Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
- [Companion-Segment Compatibility Flow](companion-compatibility/companion-segment-compatibility-flow.md)
- [Serialization and Wire-Format Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Serialization and Wire-Format Flow](serialization-wire-format/serialization-wire-format-flow.md)
- [Segment 131 Rule Catalog (authoritative)](coverage/segment-131-rule-catalog.json)
- [SME/TBA Input Register](segment-131-sme-tba-input-register.md)

---

## 1. Segment Definition

| Attribute | Value | Source |
|---|---|---|
| Segment number | 131 | Section 12.21 |
| Segment name | EMV Response Data Segment | Section 12.21 heading |
| Purpose | Echoes CA Public Key File Checksum and EMV chip data back to the device | Section 12.21 |
| Placement | **Disputed** — Section 12.21 says Field No. 4, Data Section 3; the EMV Financial Transaction Response layout table says Field No. 17/18/19/20, Data Section 2 | `SEG131-R-001`, provisional |
| Origin | BUYPASS (Host) | Section 12.21 opening (though fields 4-5 list "Device" — provisional, `SEG131-R-008`) |
| Segment length range | 001–3,834 alphanumeric characters | Section 12.21 opening, corroborated by AI Solution Team `BR-269-2` |
| Included when | Conditional — follows in the EMV Financial Transaction Response when EMV data is required | Section 11.1.2-EMV |
| Wire format | **No Field Separators at all** — the first fully non-delimited segment in this KB | Section 12.21 |

---

## 2. Field Layout

| # | Element | Name | Type / Len | R/O/C | Source |
|---|---|---|---|---|---|
| 1 | 85 | Segment Type | N, 3 | R | Host (fixed value 131) |
| 2 | 84 | Segment Length | N, 4 | R | Host |
| 3 | 187 | CA Public Key File Checksum | AN, 25 | R | Device/Host — **echoed from the Request** |
| 4 | 189 | EMV Chip Data Length | N, 3 | R | Device (sic — provisional) |
| 5 | 190 | EMV Chip Data | AN, 999 | R | Device (sic — provisional) |
| 6* | 191 | EMV Additional Information Indicator | N, 3 | R (section present) | Host |
| 7* | 192 | EMV Additional Information Length | N, 3 | R (section present) | Host |
| 8* | 118 | EMV Additional Information | AN, Var. | R (section present) | Host |

`*` Repeating group, capped at **2,800 bytes** total (different from Segment 130's 2,000-byte cap for the same structural pattern).

---

## 3. Rule Set (12 rules — see [the authoritative catalog](coverage/segment-131-rule-catalog.json))

| Rule ID | Title | Class |
|---|---|---|
| SEG131-R-001 | Placement contradiction (Section 12.21 vs layout table) | structure |
| SEG131-R-002 | Conditional inclusion in EMV Financial Transaction Response | applicability |
| SEG131-R-003 | Segment Type fixed value 131 | field |
| SEG131-R-004 | Segment Length 4 digits | field |
| SEG131-R-005 | Maximum length 3,834 (provisional) | serialization |
| SEG131-R-006 | No Field Separators at all | serialization |
| SEG131-R-007 | CA Public Key File Checksum echoed from Segment 130 (confirmed) | lifecycle |
| SEG131-R-008 | EMV Chip Data Length/Data sourcing inconsistency (provisional) | field |
| SEG131-R-009 | EMV Additional Information Section cap 2,800 bytes (distinct from Segment 130) | structure |
| SEG131-R-010 | Element 118 cross-segment reuse (3rd segment: 112, 130, 131) | metadata |
| SEG131-R-011 | Originates at BUYPASS (Host) | metadata |
| SEG131-R-012 | Sequence after Segments 112/115/120 in the EMV response | compatibility |

---

## 4. `[PROVISIONAL]` Items — All 4 Open

See the [SME/TBA Input Register](segment-131-sme-tba-input-register.md): `SEG131-SME-001` (placement contradiction — highest priority), `SEG131-SME-002` (max-length confirmation), `SEG131-SME-003` (Device/Host sourcing inconsistency), `SEG131-SME-004` (missing AI/Test artifact package).

---

## 5. AI-Generated vs Test-Generated Requirement Matching

See [segment-131-ai-vs-test-requirement-comparison.md](segment-131-ai-vs-test-requirement-comparison.md). Headline finding: the AI Solution Team correctly extracted the distinct 2,800-byte cap (vs Segment 130's 2,000) and the CA Public Key File Checksum echo relationship — but its "Field 17/18/19/20" placement statement, while accurate against the layout table, directly conflicts with Section 12.21's own text, which is a genuine specification issue rather than an AI defect.

---

## 6. Do-Not-Assume Rules

1. Do not insert Field Separators anywhere in Segment 131 — it is the first fully non-delimited segment in this KB.
2. Do not reuse Segment 130's 2,000-byte EMV Additional Information Section cap — Segment 131's is 2,800 bytes.
3. Do not assert a specific Data Section/field placement for Segment 131 without flagging the Section 12.21 vs layout-table contradiction (`SEG131-SME-001`, open).
4. Do not assume the CA Public Key File Checksum in Segment 131 is independent data — it must equal Segment 130's request value.
5. Do not silently "correct" the "Source: Device" notation on EMV Chip Data Length/Data — flag it (`SEG131-SME-003`, open) rather than assuming either interpretation.
