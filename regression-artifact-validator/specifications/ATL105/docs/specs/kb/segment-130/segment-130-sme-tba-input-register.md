# Segment 130 SME/TBA Input Register

These questions are required to turn the specification-backed starter catalog into approved Segment 130 business requirements and test data. Each response must cite a source, configuration, or named business owner. Unanswered items remain `REVIEW_REQUIRED` and must not be auto-approved.

**Status summary (2026-09-28):** 2 resolved from source, 7 open, 1 administrative.

## Resolved From Source — No SME Input Needed

| ID | Question | Resolution |
| --- | --- | --- |
| SEG130-SME-001 | Exact printed maximum length for Segment 130. | **RESOLVED (administrative follow-up only).** Genuine specification conflict, not OCR noise. Section 12.20 = 3,043; Element 84 valid-codes = 3,043; Section 11.8.1 table = **9,999**. The layout column wraps after three characters (sibling rows: 103 → `3,33`+`4` = 3,334; 151 → `230`+`9` = 2,309), so 9,999 is genuinely printed. **Adopted: 3,043** — two sources against one, and exact field arithmetic (`3+4+25+3+3+999` + 2,000 + 6 separators) reproduces it. Remaining action: log a specification defect against the 11.8.1 table. |
| SEG130-SME-006 | Is Appendix T in scope, or a separate workstream? | **RESOLVED.** Appendix T **is present** in the extracted specification text and has been transcribed into this KB pass. It defines exactly two indicators — Table `001` (EMV Table Data, `EMVYES`/`EMVNOT`, length 6, device echo obligation on subsequent advice/batch upload) and Table `002` (CARC, length 1, returned by Visa in Bit 44.8). See `SEG130-R-020`, `SEG130-R-021`, and the [EMV Additional Information note](emv-additional-information-sme-tba-note.md). |

## Open — Manual Input Required

| ID | Manual input required | Why it is needed | Proposed test impact | Status |
| --- | --- | --- | --- | --- |
| SEG130-SME-002 | Provide a real production CA Public Key File (CA_KEYS), or confirm structural-only (non-cryptographic) validation is acceptable for Segment 130/131's CA Public Key File Checksum and Appendix S's AID-to-key resolution. | Already flagged `EXTERNAL_FIXTURE_REQUIRED` by the pre-existing Test Team appendix (`appendix-s-segment-100-coverage.json`); genuine checksum/AID verification cannot be synthesized. | CA key authenticity test, if a real file is provided; otherwise remains structural-only. | REVIEW_REQUIRED (pre-existing) |
| SEG130-SME-003 | Confirm the complete EMV chip-value cross-field consistency rules that must agree between Segment 100 and Segment 130. Candidate tag mapping derived in this pass: `9F02` ↔ authorized amount, `9C` ↔ transaction type, `5F2A` ↔ currency, `9F1A` ↔ terminal country. Confirm the authoritative list **and** comparison semantics (e.g. is amount compared pre- or post-partial-approval?). | Already flagged `REVIEW_REQUIRED` by the pre-existing Test Team appendix (`BR-SEG100-APPR-CROSS-FIELD`). | Cross-field consistency test scenarios. | REVIEW_REQUIRED (pre-existing) |
| SEG130-SME-004 | Confirm Application Cryptogram (Tag 9F26) authenticity verification remains permanently out of scope (requires certified EMV kernel/HSM), or provide a certified test fixture if one is now available. | Needed to close `SEG130-R-010` as either "permanently out of scope" or "now testable." | Cryptogram authenticity test, if a certified fixture becomes available. | REVIEW_REQUIRED (pre-existing) |
| SEG130-SME-005 | Confirm whether the existing AI Solution Team BR package (`POC-AI-ATL105-Segment-130-Business-Requirements.json`) should be treated as the canonical AI artifact for Item 2 (Artifact Comparison). | Needed to avoid duplicating or conflicting with existing cross-references. | AI-to-Test crosswalk / coverage-ratio reporting. | REVIEW_REQUIRED |
| **SEG130-SME-007** | **NEW.** Appendix T Table `002` (CARC) is defined as a value *returned by Visa in the response*. Confirm whether Table `002` may ever legitimately appear in a Segment 130 **request**, or whether request-side indicators are restricted to Table `001` only. | Determines whether the request-side indicator allow-list is `{001}` or `{001, 002}`. Directly affects the Item 5 mutation set for Element 191. | Indicator allow-list validation and negative mutation. | REVIEW_REQUIRED |
| **SEG130-SME-008** | **NEW.** Section 10.14.2.3 lists the 17 EMV-supported card products by brand/network name only (American Express, Discover, MasterCard, Visa, STAR Signature Debit, Interlink, Maestro, STAR West/Southeast/Northeast, NYCE, PULSE, Accel, Shazam, Generic Proprietary, Voyager, Wright Express). Provide the authoritative mapping to Appendix E three-digit ATL105 card-type codes. | Without the mapping, EMV card eligibility cannot be machine-checked from the Segment 100 Prompt Code. Blocks Item 4 traceability for all `EMV-130-CR/DB/PF-*` requirements. | EMV card-eligibility validation from Prompt Code. | REVIEW_REQUIRED |
| **SEG130-SME-009** | **NEW.** Section 10.14.2.3 qualifies EMV support for "Generic Proprietary" as *"Some issuers participating."* Confirm which issuers participate, per deployment environment. | `EMV-130-PF-001` cannot be asserted unconditionally. | Conditional card-eligibility scenario. | REVIEW_REQUIRED |
| **SEG130-SME-010** | **NEW.** Element 187 (CA Public Key File Checksum) is **Optional** in Segment 130 but **Required** in Segment 131. Sections 12.20 and 12.21 do not state what the host must echo when the request omits Element 187. Confirm the expected response behaviour for that branch. | The echo rule (`SEG130-R-015`) cannot be fully specified without it. | CA key checksum echo lifecycle test. | REVIEW_REQUIRED |

## Response Format

For each answer provide: `ID`, answer, source reference or configuration owner, effective environment, approved date, and any exception. Do not include real production CA keys, cardholder PANs, or cryptogram material; use synthetic or masked values.

## Pre-Existing Test Team Context

Unlike Segments 108/114/115, Segment 130 already has a partial Test Team baseline (`test-json/segment-130-core-structure-package.json`, `test-json/appendices/appendix-r-segment-100-coverage.json`, `test-json/appendices/appendix-s-segment-100-coverage.json`) predating this KB training pass. Several open items above (`SEG130-SME-002`, `SEG130-SME-003`, `SEG130-SME-004`) were already flagged `REVIEW_REQUIRED` or `EXTERNAL_FIXTURE_REQUIRED` by that prior work — this register consolidates them into the standard SME/TBA intake format rather than re-deriving them.
