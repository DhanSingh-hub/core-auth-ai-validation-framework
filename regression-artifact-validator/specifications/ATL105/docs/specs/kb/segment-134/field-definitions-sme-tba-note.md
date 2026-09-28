# Segment 134 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 134 — Transaction Attributes Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.23  
**Oracle:** [segment-134-rule-catalog.json](coverage/segment-134-rule-catalog.json) (7 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 134 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (4)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG134-R-004` | Segment Type is fixed value 134, Segment Length is 4 digits | 12.23 | 85,84 | REVIEW_REQUIRED |
| `SEG134-R-005` | Settlement Type (Element 198) is required, 1 character, valid values D (Dual message), S (Single message), X (Non-traditional Signature Debit — availability must be confirmed with First Data Project/Relationship Manager) | 12.23 | 198 | REVIEW_REQUIRED |
| `SEG134-R-006` | Signature Required (Element 199) is required, 1 character, valid values T (required), F (not required), Space (device software logic determines) | 12.23 | 199 | SPEC_DERIVED |
| `SEG134-R-007` | Receipt Card Description (Element 200) is required, 10 characters, left-justified and space-filled (e.g., 'STAR ') | 12.23 | 200 | SPEC_DERIVED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | Code Description 100 Data Segment No. 100, Standard Message Data Segment 101 Data Segment No. 101, Fleet Data Segment 102 Data Segment No. 102, Produ… |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits Note: Segment Length has a length of four digits in the following seven instances only: • EBT Data Segment (N… | Data Length Data Segment 001–218 Standard Message Data Segment (No. 100) 001–61 Fleet Data Segment (No. 101) 001–381 Product Code Data Segment (No. 1… |
| 199 | Signature Required | AN | 1 byte | Fixed length 1 alphanumeric character. | Following are the values that are returned in the terminal: Value Description T Signature is required F Signature is not required Space Device softwa… |
| 200 | Receipt Card Description | AN | 10 bytes | Fixed length 10 alphanumeric characters. | A left-justified, space filled, fixed length text value that indicates the card type of the transaction. For example, a Star Northeast authorized tra… |

## Catalog Notes

- `SEG134-R-004` — PROVISIONAL: Segment 134 is not among the seven segments independently confirmed (via Segment 120's cross-reference) to require a 4-digit Segment Length, yet its own field table lists Segment Length as 4 characters. Flag this as an apparent addition to that family pending SME confirmation (SEG134-SME-001).
- `SEG134-R-005` — PROVISIONAL: value X's availability is explicitly conditional on a business relationship configuration, not a pure technical rule. Pending SME confirmation (SEG134-SME-002) on which merchants/configurations have Signature Debit enabled.

## SME Reasoning

Ask:

1. Which element number does each field carry, and does the payload preserve it?
2. Is the value fixed-length or variable-length, and is the length measured in bytes or characters?
3. Is the field Required, Optional, or Conditional, and what triggers the condition?
4. Does the valid-value set come from Chapter 13 or from an appendix table?
5. Is any value cardholder- or key-sensitive and therefore synthetic-only in test data?

## TBA Dependency Chain

```text
element definition (Chapter 13)
  -> field position in Segment 134
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type is fixed value 134, Segment Length is 4 digits
  -> source: ATL105 2026-3 §12.23 (SEG134-R-004)
  -> a violating payload shall fail validation citing SEG134-R-004
```

## Open Provisional Items

- **P-01** (SEG134-R-004): Is Segment 134 genuinely an eighth 4-digit-Segment-Length segment (in addition to the previously-confirmed seven: 103,114,115,118,120,130,131), or is this a table transcription inconsistency?
- **P-02** (SEG134-R-005): Which merchant configurations have Non-traditional Signature Debit (Settlement Type X) enabled? Confirm with First Data Project/Relationship Manager per the specification's own note.

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment134PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG134-R-004`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
