# Segment 132 SME/TBA Input Register

| ID | Manual input required | Why it is needed | Status |
| --- | --- | --- | --- |
| SEG132-SME-001 | Confirm Segment 132's exact R/O/C designation within the CA Public Key File Load Request's Data Section 3 table. | Needed to certify `SEG132-R-001` as Required (strongly implied by segment name/role) vs Conditional. | REVIEW_REQUIRED |
| SEG132-SME-002 | Reconcile the 77-byte maximum length against the documented field lengths, which sum to 74. | Needed to finalize `SEG132-R-004`'s boundary check. | REVIEW_REQUIRED |
| SEG132-SME-003 | Confirm whether the multi-block CA key file transfer protocol (Block Number, Element 11) is in scope for this training pass. | Section 12.22 references block-by-block transfer without detailing the full protocol. | REVIEW_REQUIRED |
| SEG132-SME-004 | Confirm Field Separator behavior for fields 4 through 10 — Section 12.22 lacks the usual summary sentence found in other segments. | Needed before any serialization/mutation rule can be finalized. | REVIEW_REQUIRED |
| SEG132-SME-005 | Provide a dedicated Segment 132 AI Solution Team BR/TS/TC/TD package and real CA Public Key File Load sample data, or approve synthesized `.synthetic.json` fixtures. | No dedicated package was located. | REVIEW_REQUIRED |

## Response Format

For each answer provide: `ID`, answer, source reference or configuration owner, effective environment, approved date, and any exception. Do not include real production CA keys.
