# Segment 130 SME/TBA Input Register

These questions are required to turn the specification-backed starter catalog into approved Segment 130 business requirements and test data. Each response must cite a source, configuration, or named business owner. Unanswered items remain `REVIEW_REQUIRED` and must not be auto-approved.

| ID | Manual input required | Why it is needed | Proposed test impact | Status |
| --- | --- | --- | --- | --- |
| SEG130-SME-001 | Confirm the exact printed maximum-length value in the Financial Transaction Request layout table's Segment 130 row (page ~201) against the source PDF — the extracted text is OCR-ambiguous ("999" followed by a line-wrapped "9"). | Needed to confirm 3,043 (Section 12.20) is not contradicted by a genuine second figure. | Boundary/oversized-segment mutation. | REVIEW_REQUIRED |
| SEG130-SME-002 | Provide a real production CA Public Key File (CA_KEYS), or confirm structural-only (non-cryptographic) validation is acceptable for Segment 130/131's CA Public Key File Checksum and Appendix S's AID-to-key resolution. | Already flagged `EXTERNAL_FIXTURE_REQUIRED` by the pre-existing Test Team appendix (`appendix-s-segment-100-coverage.json`); genuine checksum/AID verification cannot be synthesized. | CA key authenticity test, if a real file is provided; otherwise remains structural-only. | REVIEW_REQUIRED (pre-existing) |
| SEG130-SME-003 | Confirm the complete EMV chip-value cross-field consistency rules (authorized amount, transaction type, currency, terminal country) that must agree between Segment 100 and Segment 130. | Already flagged `REVIEW_REQUIRED` by the pre-existing Test Team appendix (`BR-SEG100-APPR-CROSS-FIELD`). | Cross-field consistency test scenarios. | REVIEW_REQUIRED (pre-existing) |
| SEG130-SME-004 | Confirm Application Cryptogram (Tag 9F26) authenticity verification remains permanently out of scope (requires certified EMV kernel/HSM), or provide a certified test fixture if one is now available. | Needed to close `SEG130-R-010` as either "permanently out of scope" or "now testable." | Cryptogram authenticity test, if a certified fixture becomes available. | REVIEW_REQUIRED (pre-existing) |
| SEG130-SME-005 | Confirm whether the existing AI Solution Team BR package (`POC-AI-ATL105-Segment-130-Business-Requirements.json`) should be treated as the canonical AI artifact for Item 2 (Artifact Comparison), given the pre-existing Test Team packages already partially cross-reference AI statements. | Needed to avoid duplicating or conflicting with existing cross-references. | AI-to-Test crosswalk / coverage-ratio reporting. | REVIEW_REQUIRED |
| SEG130-SME-006 | Confirm whether Appendix T (EMV Additional Information Table IDs and response echo behavior, referenced elsewhere for Segment 100/130) is in scope for this Segment 130 training pass, or a separate workstream. | Appendix T is referenced but not transcribed into this KB pass. | EMV Additional Information Table ID scenarios (e.g., Table 001 EMVYES/EMVNOT). | REVIEW_REQUIRED |

## Response Format

For each answer provide: `ID`, answer, source reference or configuration owner, effective environment, approved date, and any exception. Do not include real production CA keys, cardholder PANs, or cryptogram material; use synthetic or masked values.

## Pre-Existing Test Team Context

Unlike Segments 108/114/115, Segment 130 already has a partial Test Team baseline (`test-json/segment-130-core-structure-package.json`, `test-json/appendices/appendix-r-segment-100-coverage.json`, `test-json/appendices/appendix-s-segment-100-coverage.json`) predating this KB training pass. Several open items above (`SEG130-SME-002`, `SEG130-SME-003`, `SEG130-SME-004`) were already flagged `REVIEW_REQUIRED` or `EXTERNAL_FIXTURE_REQUIRED` by that prior work — this register consolidates them into the standard SME/TBA intake format rather than re-deriving them.
