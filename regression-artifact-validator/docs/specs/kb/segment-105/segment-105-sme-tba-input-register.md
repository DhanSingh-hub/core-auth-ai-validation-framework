# Segment 105 SME/TBA Input Register

These questions are required to turn the specification-backed starter catalog into approved Segment 105 business requirements and test data. Each response must cite a source, configuration, or named business owner. Unanswered items remain `REVIEW_REQUIRED` and must not be auto-approved.

| ID | Manual input required | Why it is needed | Proposed test impact | Status |
| --- | --- | --- | --- | --- |
| SEG105-SME-001 | Confirm which totals-date operations this merchant/device population is permitted to issue: explicit date, `111111`, `222222`, `333333`, `444444`, `555555`, `999999`. | The specification describes codes but not enabled merchant policy. | Positive and negative totals-date scenarios. | REVIEW_REQUIRED |
| SEG105-SME-002 | Define the settlement cutoff, timezone, business-day rollover, and expected behavior for a `222222` end-of-day request. | Required to test date selection and end-of-day safely. | Boundary tests before/after cutoff; expected response date. | REVIEW_REQUIRED |
| SEG105-SME-003 | Confirm the required sequence-number format and whether a Totals Response must echo the request sequence. | The layout identifies the field, but this extracted source does not define correlation behavior. | Request/response correlation and mutation tests. | REVIEW_REQUIRED |
| SEG105-SME-004 | State when Employee Number and Password are required, allowed, masked, or prohibited, including end-of-day authorization. | Both fields are conditional in the layout; authorization policy is not present in the template. | Conditional-field presence/omission and secure-fixture tests. | REVIEW_REQUIRED |
| SEG105-SME-005 | Confirm the source and accepted formats/values for terminal, hardware, software, firmware, and currency fields. | The fields are required by the layout but their local valid-value catalog is not included here. | Format, length, and invalid-value mutations. | REVIEW_REQUIRED |
| SEG105-SME-006 | Define Grand Total, Card Label, Card Type Total Count, and Card Type Total Amount semantics for a Totals Request versus a Totals Response. | The layout marks these fields required; aggregation ownership and response behavior are not established. | Request/response data assertions and reconciliation tests. | REVIEW_REQUIRED |
| SEG105-SME-007 | Confirm whether one Segment 105 is always the sole data segment for Totals Request, or when Segment 119 is used instead. | The template includes a Segment 119 totals alternative with similar leading fields. | Missing/duplicate/wrong-segment and compatibility tests. | REVIEW_REQUIRED |
| SEG105-SME-008 | Define duplicate request, timeout, retry, and partial-response behavior for totals operations. | No lifecycle/retry behavior may be inferred from financial-transaction rules. | Ordered lifecycle and idempotency tests. | REVIEW_REQUIRED |
| SEG105-SME-009 | Provide one sanitized, converter-ready Totals Request and matching Totals Response for every enabled Totals Date operation. | Needed to prove JSON mapping and serialization against a real approved message shape. | Baseline fixtures, artifact comparison, and external converter validation. | REVIEW_REQUIRED |

## Response Format

For each answer provide: `ID`, answer, source reference or configuration owner, effective environment, approved date, and any exception. Do not include real passwords, PANs, tokens, or production terminal credentials; use synthetic or masked values.