# Segment DL2 SME Decision Context

This note prepares the open DL2 decisions for SME review. It supplements the generated [SME/TBA input register](../segment-DL2-sme-tba-input-register.md); update the register through its source communication register and regenerate it after decisions are approved.

Use synthetic or masked examples only. Do not include production dial strings, phone numbers, access codes, or merchant data. For every answer, provide the decision ID, answer, source reference or configuration owner, effective environment, approval date, and exceptions.

## P-01 — Secondary-number fallback detail

- **Register item:** `SEGDL2-SME-001`
- **ATL105 evidence:** Section 13.2 Element 75 says dialing starts with the primary number and the secondary is used after primary attempts are exhausted. Element 82 bounds Redial Count to 1–3.
- **Unresolved decision:** Is the separate *Asynchronous Communications Protocol Specifications* behavior in scope? If yes, provide/approve the exact retry and fallback sequence, including any failure conditions or timing that the test must assert. If no, confirm the ATL105 exhaustion statement and redial bounds are the full scope for this pass.
- **Affected coverage:** `SEGDL2-R-003`, `BR-SEGDL2-003`, `TS-SEGDL2-003`; fallback-order candidate `TC-SEGDL2-003-N-ORDER`.
- **Can proceed without the answer:** Validate the secondary block's structured fields, redial count, and `F` terminator. Do not assert timing or detailed recovery transitions.

## P-02 — Approval of AI/test fixtures

- **Register item:** `SEGDL2-SME-002`
- **Evidence:** A dedicated Segment DL2 AI/Test package was not located during this validation. The current package uses synthetic logical request/response fields and is explicitly unapproved.
- **Unresolved decision:** Provide the approved DL2 AI/Test artifacts, or approve the current synthetic candidate package as a test fixture. State its permitted environment and any constraints.
- **Affected coverage:** All candidate test data and the fixture-approval gate; this decision is not a change to the ATL105 rule text.
- **Can proceed without the answer:** Maintain catalog/source crosswalks and run structural checks on candidate definitions. Do not treat the fixtures as approved evidence, execute certification runs, or count a rule as covered.

## P-03 — Phone Load Response field-1 framing

- **Register item:** `SEGDL2-SME-003`
- **ATL105 evidence:** Section 11.7.2.2 presents Phone Load Response field 1 as Element 97 with fixed value `!`. The global Element 97 definition in Section 13.2 specifies `)`, while DL2's own Element 24 Data Type Indicator is `!`.
- **Unresolved decision:** Confirm the Phone Load Response wire sequence: does Element 97 `)` precede the DL2 segment's `!`, or does the response begin directly with DL2 `!`? Confirm whether the answer differs by response or transport.
- **Affected coverage:** `SEGDL2-R-008`, `BR-SEGDL2-008`, `TS-SEGDL2-008`, including response-placement and framing mutations.
- **Can proceed without the answer:** Validate response-family metadata and logical DL2 fields. Do not assert serialized Phone Load Response prefix bytes.

## P-04 — Separator-free field parsing

- **Register item:** `SEGDL2-SME-004`
- **ATL105 evidence:** Section 12.43 says DL2 has no Field Separators and that the next field immediately follows an unpopulated field. Section 13.2 describes Access Code as variable length and allows `B` pause characters within it, defines Pause Indicator as `B`, and describes Phone Number as variable length with terminator behavior that differs for primary and secondary blocks.
- **Unresolved decision:** Provide/approve the parsing grammar for separator-free DL2, including how to distinguish `B` characters in Access Code from the Pause Indicator, how Phone Number boundaries are determined, whether `C` occurs in DL2, and how primary `A` versus secondary `F` termination is handled.
- **Affected coverage:** `SEGDL2-R-004`, `-005`, and `-007`, their BR/TS/TC mappings, serialized test-data generation, byte-level mutations, and any production parser.
- **Can proceed without the answer:** Validate structured fields and explicit fixed values (including that a populated Pause Indicator is exactly `B`). Do not implement or claim a byte parser, field-boundary assertions, or serialized mutation execution.

## Decision tracking

| ID | Status | Evidence or test impact |
|---|---|---|
| P-01 | OPEN | Detailed fallback timing/order assertions remain review-required. |
| P-02 | OPEN | Synthetic fixtures remain unapproved; approved test-data pairs remain zero. |
| P-03 | OPEN | Phone Load Response wire framing remains unresolved. |
| P-04 | OPEN | Separator-free parsing and wire-level mutations remain blocked. |

Until these decisions are recorded in the generated register and reflected in the rule catalog, keep all linked requirements and tests in their current review-required state.
