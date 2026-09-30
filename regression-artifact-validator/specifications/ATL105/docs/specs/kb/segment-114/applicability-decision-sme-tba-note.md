# Segment 114 Applicability and Message-Family Decision: SME/TBA Learning Note

**Segment:** 114 — SKU Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 11.1.1, 11.2.1, 11.2.2, 11.3.1, 11.9.1, 12.13, 13.2  
**Oracle:** [segment-114-rule-catalog.json](coverage/segment-114-rule-catalog.json) (13 rules)  
**Benchmark:** [Segment 100 Learning Module](../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

Segment 100 analogue: `prompt-code-*` (the inclusion decision).

## Core Idea

Segment 114 is valid only inside the message families and Data Sections the specification assigns to it. A structurally perfect segment placed in the wrong message is an invalid message, not a weak test.

## Specification-Derived Rules (5)

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG114-R-001` | Segment 114 is exclusive to the Loyalty Card Transaction Request's Data Section 3; it is not documented as a companion in the Financial Transaction Request (11.1.1), ECA/TeleCheck Service Transaction Request (11.3.1), o… | 11.1.1,11.2.1,11.3.1,11.9.1 | — | SPEC_DERIVED |
| `SEG114-R-002` | Segment 114 is optional in every Loyalty Card Transaction Request, always in Field No. 5 of Data Section No. 3, alongside the required Segment 108 in Field No. 4 | 11.2.1 | — | SPEC_DERIVED |
| `SEG114-R-010` | Segment 114 may repeat within a single message, once per scanned bar code SKU; it is not limited to zero-or-one occurrence | 11.2.1 | — | SPEC_DERIVED |
| `SEG114-R-011` | Segment 114 never appears without Segment 108: its only documented context is Data Section 3 of the Loyalty Card Transaction Request, where Segment 108 is required | 11.2.1 | — | SPEC_DERIVED |
| `SEG114-R-012` | The AI-generated relationship 'The Financial Transaction Request transaction includes SKU Data Segment (Data Segment No. 114)' (source_rule_id REL-ENT-SEG-114-FINANCIAL_TRANSACTION_REQUEST) is REJECTED as unsupported by… | 11.1.1 | — | SPEC_DERIVED |

## Catalog Notes

- `SEG114-R-010` — RESOLVED 2026-09-26 (SEG114-SME-006): user confirmed Segment 114 can repeat for multiple SKUs. This supersedes the initially-proposed 'at most once' inference from the single Field No. 5 table row; the table row represents one occurrence slot, not a hard cardinality limit.
- `SEG114-R-012` — RESOLVED 2026-09-26 (SEG114-SME-002): user confirmed this AI Solution Team statement is an error and must be rejected; Segment 114 is Loyalty Card Transaction Request-exclusive. Report REL-ENT-SEG-114-FINANCIAL_TRANSACTION_REQUEST back to the AI Solution Team as a confirmed defect.

## SME Reasoning

Ask:

1. Which message families may carry Segment 114, and in which Data Section?
2. Is Segment 114 Required, Conditional, or Optional in each of those families?
3. What business condition causes Segment 114 to be included?
4. Which companion segments may, must, or must not accompany it?
5. Is absence of Segment 114 ever legitimate, and how should that be diagnosed?

## TBA Dependency Chain

```text
transaction / message family
  -> Data Section placement
  -> inclusion condition
  -> companion-segment set
  -> Element 63 (Number of Segments) count where applicable
```

A requirement such as "the field is valid" is untestable. A useful requirement names the element, the condition, and the observable outcome, and cites the rule ID it is derived from.

Example derived from the catalog:

```text
Segment 114 is exclusive to the Loyalty Card Transaction Request's Data Section 3 it is not documented as a companion in the Financial Transaction Request 11.1.1 , ECA/TeleCheck Service Transaction Request 11.3.1 , or CA Public Key File Load Request 11.9.1 Data Section 3 lists
  -> source: ATL105 2026-3 §11.1.1,11.2.1,11.3.1,11.9.1 (SEG114-R-001)
  -> a violating payload shall fail validation citing SEG114-R-001
```

## Open Provisional Items

_No open provisional items are linked to these rules._

## Security and Test-Data Guidance

- Use synthetic values only; never copy production PANs, PINs, keys, tokens, or cryptographic material into artifacts.
- Do not treat a JSON field name as proof that the underlying element rule is satisfied.
- Keep `REVIEW_REQUIRED` rules out of `COVERED` status until the SME resolves the linked provisional item.

## Current Validator Boundary

No `Segment114PayloadValidator` exists yet. These rules are documented but not yet enforced in code.

## Review Checklist

- Is every rule traced to its source anchor (`SEG114-R-001`…)?
- Does each requirement name an element, a condition, and an observable outcome?
- Are provisional rules kept at `REVIEW_REQUIRED`?
- Is the test data synthetic and complete enough to exercise the rule?
- Is the negative case tested, not just the happy path?
