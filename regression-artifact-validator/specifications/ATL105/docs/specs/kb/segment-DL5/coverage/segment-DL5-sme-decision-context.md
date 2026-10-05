# Segment DL5 SME Decision Context

This note supplements the generated [DL5 SME/TBA input register](../segment-DL5-sme-tba-input-register.md). Record answers in the communication register and regenerate its views; do not edit the generated register directly.

Use synthetic or masked values only. Do not include production IP/URL addresses, phone numbers, passwords, merchant identifiers, terminal identifiers, or proprietary application data. Each answer should include the source reference or configuration owner, effective environment, approval date, and any exception.

## P-01 — AI/Test fixture approval

- **Register item:** `SEGDL5-SME-001`
- **Evidence:** The phase-one delivery has one representative DL5 AI chain and structured Software Load Response sample; it is not a dedicated DL5 AI/Test package. The independent package is synthetic and unapproved.
- **Decision needed:** Provide a dedicated AI/Test package or approve the synthetic candidates and state the isolated environment in which they may be used.
- **Affected work:** Fixture approval, AI/Test package comparison, independence evidence, and execution readiness.
- **Can proceed:** Maintain the independent ATL105 catalog, traceability candidates, and comparison crosswalks. Do not count candidate fixtures as approved or executed coverage.

## P-02 — DL5 maximum length

- **Register item:** `SEGDL5-SME-002`
- **ATL105 evidence:** Section 12.46 sums to 64 characters for `$` + fields + `~`; Section 11.7.4.2 lists Software Load Response field 3 max length 66.
- **Decision needed:** Confirm the authoritative DL5 maximum length and whether any response-wrapper bytes explain the 66 value.
- **Affected work:** `SEGDL5-R-002` marker/length boundary candidates and mutation expectations.
- **Can proceed:** Keep logical marker presence and source-width calculations as candidate evidence. Do not certify the length boundary until resolved.

## P-03 — Element 114 IP/URL content and padding

- **Register item:** `SEGDL5-SME-003`
- **ATL105 evidence:** Section 12.46 states Element 114 is Software Load IP/URL Address, fixed length 30; Section 13.2 describes terminal-number purpose and valid values that do not include URL punctuation.
- **Decision needed:** Confirm valid content, character set, whether dotted IP/URL punctuation is allowed, and how shorter values are padded to 30.
- **Affected work:** `SEGDL5-R-005` field validator, positive/negative address values, padding mutations, and serializer-aware validation.
- **Can proceed:** Validate structured field presence and shared DL4-style fields. Keep Element 114 content and padding review-required.

## Current gates

| Decision | Status | Tests/data held |
|---|---|---|
| P-01 | OPEN | Candidate data unapproved; zero approved pairs. |
| P-02 | OPEN | DL5 max-length boundary and length mutations. |
| P-03 | OPEN | Element 114 content, characters, padding, and serializer-aware validator. |
