# Segment DL3 SME Decision Context

This note prepares open DL3 questions for review and supplements the generated [SME/TBA input register](../segment-DL3-sme-tba-input-register.md). Update the source communication register and regenerate its views after decisions; do not edit the generated register directly.

Use synthetic or masked values only. Never include a default or production Password. Record each answer with its source reference or configuration owner, effective environment, approval date, and any exception.

## P-01 — AI/Test fixtures

- **Register item:** `SEGDL3-SME-001`
- **Question:** Provide a dedicated AI/Test DL3 package, or approve the independent synthetic candidates.
- **Current evidence:** The phase-one AI delivery contains one representative DL3 chain, not a complete segment package. The Test Solution candidate package is synthetic and unapproved.
- **Decision needed:** Supply the dedicated package or approve the candidate fixtures and identify the permitted isolated environment.
- **Affected work:** Fixture approval, AI/Test comparison, and independent test-data execution.
- **Can proceed:** Maintain source-grounded rules, candidate mappings, and structural traceability. Do not count candidate pairs as approved or executed.

## P-02 — Password source and ownership

- **Register item:** `SEGDL3-SME-002`
- **ATL105 evidence:** Section 12.44 says Password is Device-sourced; Date and Time Load Response Section 11.7.3.2 says Host-sourced. Element 65 also describes a device password used when requesting host totals.
- **Decision needed:** Confirm which source is correct, whether the host sends this value in DL3, and which environment owns a safe synthetic test credential. Clarify which behavior, if any, is within this segment test scope.
- **Affected work:** `SEGDL3-R-003` source/role and `SEGDL3-R-006` fixture and format validation.
- **Can proceed:** Document the conflict and check no candidate package includes a default or production value. Keep Password assertions and executable fixtures blocked.

## P-03 — Date and Time Load Response terminator

- **Register item:** `SEGDL3-SME-003`
- **ATL105 evidence:** Section 12.44 defines `~` as the last DL3 field and a 23-character maximum. Section 11.7.3.2 lays out the Date and Time Load Response only through Password (positions 1-22). The Table Load Response data-block layout includes DL3 separately.
- **Decision needed:** Confirm whether the Date and Time Load Response includes `~`, and whether any message-family or transport condition changes framing.
- **Affected work:** `SEGDL3-R-001` length/marker assertions and `SEGDL3-R-007` response placement.
- **Can proceed:** Check Table Load Response block shape and disallowed response families. Keep Date and Time Load Response byte framing in review.

## P-04 — HHMM boundary values

- **Register item:** `SEGDL3-SME-004`
- **ATL105 evidence:** Elements 22 and 23 describe Current Time and Cut Time as HHMM but list hour 01-24 and minute 01-60; Element 166 uses clock bounds that differ.
- **Decision needed:** Confirm accepted boundary values, specifically `0000`, hour `24`, minute `60`, and whether values outside conventional clock ranges are valid.
- **Affected work:** `SEGDL3-R-005` positive and negative boundary tests.
- **Can proceed:** Validate four-character numeric shape and undisputed values. Do not classify disputed endpoints as valid or invalid.

## Current gates

| Decision | Status | Tests/data held |
|---|---|---|
| P-01 | OPEN | Synthetic fixtures unapproved; zero approved pairs. |
| P-02 | OPEN | Password source/ownership and executable Password examples. |
| P-03 | OPEN | Date and Time Load Response terminator and length. |
| P-04 | OPEN | HHMM range boundaries. |
