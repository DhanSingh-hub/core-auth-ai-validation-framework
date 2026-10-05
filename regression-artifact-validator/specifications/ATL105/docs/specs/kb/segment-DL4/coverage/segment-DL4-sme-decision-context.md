# Segment DL4 SME Decision Context

This note supplements the generated [DL4 SME/TBA input register](../segment-DL4-sme-tba-input-register.md). Record answers in the communication register and regenerate its views; do not edit the generated register directly.

Use synthetic or masked values only. Do not include production software phone numbers, merchant identifiers, terminal identifiers, or proprietary application data. Each answer should include the source reference or configuration owner, effective environment, approval date, and any exception.

## P-01 — AI/Test fixture approval

- **Register item:** `SEGDL4-SME-001`
- **Evidence:** The phase-one delivery has a representative DL4 AI chain and one structured Software Load Response sample; it is not a dedicated DL4 AI/Test package. The independent package is synthetic and unapproved.
- **Decision needed:** Provide a dedicated AI/Test package or approve the synthetic candidates and state the isolated environment in which they may be used.
- **Affected work:** Fixture approval, AI/Test package comparison, and execution readiness.
- **Can proceed:** Maintain the independent ATL105 catalog, traceability candidates, and comparison crosswalks. Do not count candidate fixtures as approved or executed coverage.

## P-02 — DL4/DL5 exchange and lifecycle

- **Register item:** `SEGDL4-SME-002`
- **ATL105 evidence:** Section 10.10 describes DL4 and DL5 being returned after a Table Load request following Download Indicator 1 and a profile DLL bit. Sections 11.7.4/11.7.4.2 and the Chapter 12 matrix place them in the Software Load Response under merchant flag SOFT, with both required.
- **Decision needed:** Confirm the exchange/message family carrying DL4/DL5, whether DLL and SOFT are equivalent or distinct gates, and the request/response sequence for devices that may use dial and IP download data.
- **Affected work:** `SEGDL4-R-006` placement and co-presence assertions; `SEGDL4-R-007` lifecycle scenarios and expected response chain.
- **Can proceed:** Retain the Section 11.7.4.2 candidate pair and the Section 10.10 event sequence as separately labeled evidence. Do not certify their reconciliation or generate executable cross-message fixtures.

## P-03 — Variable phone field boundary

- **Register item:** `SEGDL4-SME-003`
- **ATL105 evidence:** Section 12.45 defines no Field Separators; Element 91 is variable length up to 18 characters and is immediately followed by the six-character request date (Element 92).
- **Decision needed:** Confirm whether Element 91 is padded to 18 characters, the padding value if so, or the parsing rule that identifies the end of the phone field before the date.
- **Affected work:** `SEGDL4-R-004` serialized adjacency and `SEGDL4-R-005` phone length/format boundaries, including the 52-character maximum and parser mutations.
- **Can proceed:** Validate structured field presence, known fixed widths, and the F/P enumeration. Keep byte-level phone parsing, serialized maximum-length tests, and ambiguous phone mutations review-required.

## Current gates

| Decision | Status | Tests/data held |
|---|---|---|
| P-01 | OPEN | Candidate data unapproved; zero approved pairs. |
| P-02 | OPEN | Placement, SOFT/DLL semantics, and lifecycle execution. |
| P-03 | OPEN | Phone field serialization/boundary, parser, and length mutations. |
