# Segment 146 Field Definitions and Element Semantics: SME/TBA Learning Note

**Segment:** 146 — Enhanced Fleet Response Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.32, AppendixF  
**Oracle:** [segment-146-rule-catalog.json](coverage/segment-146-rule-catalog.json) (5 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `account-number-*` (the core data carrier).

## Core Idea

Each Segment 146 field is a specific ATL105 data element with its own type, length and valid-value rules. A field is only testable when its element definition is known; a field name in a JSON payload is not evidence of conformance.

## Specification-Derived Rules (4)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG146-R-001` | Segment Type fixed 146, Segment Length includes Segment Type's length; both Device-sourced (despite this being a response segment, mirroring the sourcing pattern already flagged for Segments 131/136) | 12.32 | 85,84 | REVIEW_REQUIRED |
| `SEG146-R-002` | Enhanced Fleet Data (Element 239) is required, max 999 characters, cataloged as: Table 001 (Response Flags, incl. Settlement Indicator: CP/DB/DF/RC/RF/FN), Table 002 (Non-Fuel Product Limits, NOT present in Comdata responses), Table 003 (Fuel Product Limits, using Appendix F product codes), Table 004 (Prompt Formats, using documented edit masks), Table 005 (Customer Information: name/city/state/account code), Table 010 (Additional Response Data: FNAM/LNAM/ATHN/ACCT/DMSG) | 12.32 | 239 | SPEC_DERIVED |
| `SEG146-R-004` | Table 004 (Prompt Formats) uses a documented edit-mask grammar: '?' (re-prompt), 'A'/'B' (alphanumeric, treated identically since 2015-01-02), 'N' (numeric only), 'I' (free format), 'O' (optional response), 'Z' (capture but don't resend), 'T' (data type N=Number/S=String), 'Mn'/'Xn' (min/max), 'Vn' (exact match), 'Pn' (pattern match using @ /# /* /literal) | 12.32 | 239 | SPEC_DERIVED |
| `SEG146-R-005` | Table 003 (Fuel Product Limits) uses standard product codes from ATL105 Appendix F (Valid Payment Systems Product Codes) — unlike Table 002's non-fuel category codes | 12.32,AppendixF | 239 | REVIEW_REQUIRED |

## Element Definitions (ATL105 Chapter 13)

| Element | Name | Type | Max length | Representation | Valid values |
|---|---|---|---|---|---|
| 85 | Segment Type | N | 3 bytes | Fixed length of three digits | Code Description 100 Data Segment No. 100, Standard Message Data Segment 101 Data Segment No. 101, Fleet Data Segment 102 Data Segment No. 102, Produ… |
| 84 | Segment Length | N | 4 bytes | Variable length of three or four digits Note: Segment Length has a length of four digits in the following seven instances only: • EBT Data Segment (N… | Data Length Data Segment 001–218 Standard Message Data Segment (No. 100) 001–61 Fleet Data Segment (No. 101) 001–381 Product Code Data Segment (No. 1… |
| 239 | Enhanced Fleet Data | AN | 999 bytes | Variable length of up to 999 alphanumeric characters | Refer to Data Segment 145— Enhanced Fleet Request Format Note: Visa Fleet 2.0 is a new Fleet EMV Standard that harnesses the additional capabilities… |

## Catalog Notes

- `SEG146-R-001` — PROVISIONAL: same sourcing-inconsistency question already open for Segments 131/136. Pending SME confirmation (SEG146-SME-001).
- `SEG146-R-002` — Table IDs 006-009 are absent from the response-side catalog (present only in the request-side Segment 145) — this is the mirror-image asymmetry of SEG145-R-006/P-03.
- `SEG146-R-004` — A genuinely rich validation grammar — full parser-level validation of arbitrary edit-mask combinations is out of scope for a basic rule catalog; flagged as SEG146-SME-002 for scope confirmation.
- `SEG146-R-005` — PROVISIONAL: Appendix F has not been transcribed into this KB pass. Pending SME confirmation (SEG146-SME-003) on scope.

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
  -> field position in Segment 146
  -> type / length / valid values
  -> R / O / C entry rule
  -> serialization and separator handling
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment Type fixed 146, Segment Length includes Segment Type's length both Device-sourced despite this being a response segment, mirroring the sourcing pattern already flagged for Segments 131/136
  -> source: ATL105 2026-3 §12.32 (SEG146-R-001)
  -> a violating payload shall fail validation citing SEG146-R-001
```

## Open Provisional Items

- **P-01** (SEG146-R-001): Confirm whether Segment Type/Length 'Source: Device' is intentional for this response segment or a transcription error (mirrors Segments 131/136).
- **P-02** (SEG146-R-004): Is full edit-mask grammar parsing (M/X/V/P patterns) required for Item 1 sign-off, or is format-only validation sufficient?
- **P-03** (SEG146-R-005): Is Appendix F (Valid Payment Systems Product Codes) in scope for this training pass?

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment146PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG146-R-001`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
