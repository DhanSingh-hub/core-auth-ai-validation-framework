# Segment DL1 SME Decision Context

This note supplements the generated [DL1 SME/TBA input register](../segment-DL1-sme-tba-input-register.md). Record answers in the communication register and regenerate its views; do not edit the generated register directly.

Use synthetic or masked values only. Do not include production merchant identifiers, phone numbers, IP addresses, passwords, keys, or proprietary terminal data. Each answer should include the source reference or configuration owner, effective environment, approval date, and any exception.

## P-01 — AI/Test fixture approval

- **Register item:** `SEGDL1-SME-001`
- **Evidence:** The phase-one delivery has representative DL1 AI chains and structured samples, but no dedicated approved DL1 AI/Test package. The independent package is synthetic and unexecuted.
- **Decision needed:** Provide a dedicated Segment DL1 AI/Test package or approve the synthetic candidates and state the isolated environment in which they may be used.
- **Affected work:** Fixture approval, AI/Test package comparison, and execution readiness.
- **Can proceed:** Maintain the independent ATL105 rule catalog, traceability candidates, comparison crosswalks, and logical sample validation. Do not count candidate fixtures as approved or executed coverage.

## P-02 — Field padding and parse boundaries

- **Register item:** `SEGDL1-SME-002`
- **ATL105 evidence:** Segment DL1 has no Field Separators, while Merchant Name, Address Line 1, and Store Number are fixed length "up to" their maxima.
- **Decision needed:** Confirm whether these fields are always padded to full width, the padding characters, and whether shorter values are permitted in production payloads.
- **Affected work:** `SEGDL1-R-003` merchant identity fixtures, `SEGDL1-R-006` no-field-separator parsing, 399-character maximum serialization, and short-value mutations.
- **Can proceed:** Keep logical field-presence candidates and crosswalk evidence. Keep byte-level parser and boundary fixtures review-required.

## P-03 — End-of-Load and DL6 framing

- **Register item:** `SEGDL1-SME-003`
- **ATL105 evidence:** Section 11.7.1.2 shows End-of-Load placement around DL3 and DL6; Section 12.47 links Card Type `173` to DL6.
- **Decision needed:** Confirm whether End-of-Load is sent after DL3 and DL6, only once at the end, and how many blocks appear when DL2/DL3 are omitted.
- **Affected work:** `SEGDL1-R-005` DL6 co-presence and `SEGDL1-R-012` response block-order/end-marker fixtures.
- **Can proceed:** Preserve candidate lifecycle scenarios and logical DL6 presence checks. Do not certify serialized response framing or execute lifecycle mutations.

## P-04 — Appendix E Card Type valid set

- **Register item:** `SEGDL1-SME-004`
- **ATL105 evidence:** Appendix E prose lists ranges, while its table also includes additional card/feature codes.
- **Decision needed:** Confirm the complete valid set for DL1 field 8 Card Type values and any environment-specific exclusions.
- **Affected work:** `SEGDL1-R-009` valid/invalid Card Type fixtures and Card Type count/repetition mutations that depend on known-valid values.
- **Can proceed:** Use explicitly documented sample codes as candidates and keep complete valid-set assertions review-required.

## Current gates

| Decision | Status | Tests/data held |
|---|---|---|
| P-01 | OPEN | Candidate data unapproved; zero approved pairs. |
| P-02 | OPEN | Wire padding, parse boundaries, and maximum-length serialization. |
| P-03 | OPEN | DL6 lifecycle framing and End-of-Load count. |
| P-04 | OPEN | Complete Card Type valid/invalid mutation set. |
