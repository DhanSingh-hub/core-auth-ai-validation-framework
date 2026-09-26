# Segment 132 (CA Public Key File Segment) — Rule Catalog & Specification Anchors

**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3
**Source Section:** 12.22 CA Public Key File Segment (pages 12-57/271 to 272), 11.9.1 CA Public Key File Load Request
**Training Methodology:** [SEGMENT-100-TRAINING-METHODOLOGY.md](../../../test-validation-strategy/SEGMENT-100-TRAINING-METHODOLOGY.md)
**Item Progress:** Item 1 in progress; 0 of 5 SME items resolved — see [SME/TBA Input Register](segment-132-sme-tba-input-register.md)

## Learning Module Index

- [SME and Technical Business Analysis Note](segment-132-sme-tba-learning-note.md)
- [Segment 132 End-to-End Flow](segment-132-flow.md)
- [AI-Generated vs Test-Generated Requirement Comparison](segment-132-ai-vs-test-requirement-comparison.md)
- [Coverage Closure](coverage/README.md)
- [Companion-Segment Compatibility](companion-compatibility/companion-segment-compatibility-sme-tba-note.md) / [Flow](companion-compatibility/companion-segment-compatibility-flow.md)
- [Serialization and Wire-Format](serialization-wire-format/serialization-wire-format-sme-tba-note.md) / [Flow](serialization-wire-format/serialization-wire-format-flow.md)
- [Segment 132 Rule Catalog](coverage/segment-132-rule-catalog.json)
- [SME/TBA Input Register](segment-132-sme-tba-input-register.md)

## 1. Segment Definition

| Attribute | Value |
|---|---|
| Segment number | 132 |
| Purpose | Requests/carries CA Public Key File data for EMV certificate authority verification |
| Placement | Data Section 3 of the CA Public Key File Load Request |
| Origin | Device |
| Segment length range | 01–77 alphanumeric characters (field-sum reconciliation provisional) |
| Message family | CA Public Key File Load Request only; companions 101/102/104/111 |

## 2. Field Layout (10 fields — see [rule catalog](coverage/segment-132-rule-catalog.json) for all 12 rules)

Segment Type(85), Segment Length(84, 3-digit), Sequence Number(86), Terminal Identifier(102, EMV "+*" override), Load Type(48, fixed "K"), Hardware/Software/Firmware Version(43/96/39), CA Public Key File Checksum(187), Block Number(11).

## 3. `[PROVISIONAL]` Items — All 5 Open

See [SME/TBA Input Register](segment-132-sme-tba-input-register.md): applicability designation, 77-vs-74 byte reconciliation, multi-block transfer protocol scope, undocumented separator behavior for fields 4-10, missing AI/Test artifact package.

## 4. Do-Not-Assume Rules

1. Do not assume Segment 132 uses a 4-digit Segment Length — it uses 3.
2. Do not assume full delimiter coverage for fields 4-10 without SME confirmation.
3. Do not assume the multi-block CA key file transfer protocol is fully specified here.
4. Do not assume Segment 132's applicability (Required vs Conditional) without confirming against the CA Public Key File Load Request's own table.
