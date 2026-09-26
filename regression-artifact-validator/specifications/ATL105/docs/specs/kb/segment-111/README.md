# Segment 111 Validation Rules

Envelope-only ATL105 2026-3 baseline for the Variable Information Data Segment (Section 12.10).
Table-ID-specific rules remain in Appendix I and are intentionally not duplicated.

| Rule | Requirement |
|---|---|
| SEG111-R-001 | Segment Type is exactly `111`. |
| SEG111-R-002 | Segment Length is three digits and includes Segment Type, separators, and section content. |
| SEG111-R-003 | Variable Information Indicator is three digits per repetition. |
| SEG111-R-004 | Variable Information Length is three digits and equals the value length. |
| SEG111-R-005 | Repeated Variable Information Section is at most 991 characters including its final separator. |
| SEG111-R-006 | Total Segment 111 length is at most 999 characters. |
| SEG111-R-007 | No separators occur inside or between repetitions; exactly one follows the final repetition. |

Sources: ATL105 2026-3, Section 12.10, elements 85, 84, 111, 112, 113.

## Contents

- [SME and Technical Business Analysis Note](segment-111-sme-tba-learning-note.md)
- [Segment 111 End-to-End Flow](segment-111-flow.md)
- [Coverage Closure](coverage/README.md)
- [Variable Information Indicator (Table ID) SME/TBA Note](variable-information-indicator-sme-tba-note.md)
- [Variable Information Indicator Flow](variable-information-indicator-flow.md)
- [Variable Information Length SME/TBA Note](variable-information-length-sme-tba-note.md)
- [Variable Information Length Flow](variable-information-length-flow.md)
- [Repeated-Section Boundary SME/TBA Note](repeated-section-boundary-sme-tba-note.md)
- [Repeated-Section Boundary Flow](repeated-section-boundary-flow.md)
- [Companion-Segment Compatibility Note](companion-compatibility/companion-segment-compatibility-sme-tba-note.md)
- [Companion-Segment Compatibility Flow](companion-compatibility/companion-segment-compatibility-flow.md)
- [Serialization and Wire-Format Note](serialization-wire-format/serialization-wire-format-sme-tba-note.md)
- [Serialization and Wire-Format Flow](serialization-wire-format/serialization-wire-format-flow.md)

## Scope

This module covers:

- The Segment 111 envelope: Segment Type, Segment Length, and repeating
  Variable Information Indicator/Length/Value structure
- The 991-character repeated-section cap and 999-character total cap
- Field-separator placement in the serialized wire format
- Segment 111's companion-compatibility relationship to Segment 100
- Business-requirement, scenario, test-case, and test-data analysis for the
  envelope rules

It does not claim that the envelope validates Appendix I Table-ID-specific
content. Approximately 400 Table-ID business rules for elements 111 and 113
remain out of scope and are tracked in the
[coverage closure](coverage/README.md) as `REVIEW_REQUIRED`, not silently
passed. Two provisional items remain open pending SME resolution: `P-01`
(Ship-to/Ship-from Postal Code format) and `P-02` (Segment 111 cardinality).

## Validation resources

- [Training methodology](../../../SEGMENT-100-TRAINING-METHODOLOGY.md)
- [Training questionnaire](../../../test-validation-strategy/SEGMENT-TRAINING-QUESTIONNAIRE.md)
- [Segment 111 validation rules (JSON)](segment-111-validation-rules.json)
- [Segment 111 rule catalog (JSON)](coverage/segment-111-rule-catalog.json)
- [SME TBA learning note](segment-111-sme-tba-learning-note.md)
- Generated consolidated report target: `test-output/consolidated-reports/SEGMENT-111-CONSOLIDATED-REPORT.txt`

## Authoritative Neighboring Knowledge

- [Segment 100 canonical anchors](../segment-100-canonical-anchors.md)
- [Segment compatibility matrix](../segment-compatibility-matrix.md)
- [Financial transaction request sections](../11-financial-transaction-request-sections.md)
- [Segment 100 knowledge base](../segment-100/README.md)
- [Segment 101 knowledge base](../segment-101/README.md)
- [Canonical artifact contract](../../../canonical-artifact-contract.md)
