# Segment 131 SME/TBA Input Register

| ID | Manual input required | Why it is needed | Proposed test impact | Status |
| --- | --- | --- | --- | --- |
| SEG131-SME-001 | Resolve the placement contradiction: Section 12.21 says Segment 131 appears in "Field No. 4 in Data Section No. 3," but the EMV Financial Transaction Response layout table (and the AI Solution Team's own extraction) places it at Field No. 17/18/19/20 of Data Section No. 2. | Blocks a confident structural/placement rule (`SEG131-R-001`) and Item 4 traceability. | Response-structure placement test. | REVIEW_REQUIRED |
| SEG131-SME-002 | Confirm 3,834 is the sole authoritative maximum length for Segment 131. | An earlier OCR-uncertain reading suggested a possible shorter figure; needs confirmation before `SEG131-R-005` is finalized. | Boundary/oversized-segment mutation. | REVIEW_REQUIRED |
| SEG131-SME-003 | Confirm whether EMV Chip Data Length/EMV Chip Data's "Source: Device" notation (Segment 131, fields 4-5) is a transcription artifact or intentional, given the segment as a whole originates at BUYPASS. | Needed to decide whether `SEG131-R-008`'s sourcing note is enforced or purely informational. | None (metadata only). | REVIEW_REQUIRED |
| SEG131-SME-004 | Provide the location of a dedicated Segment 131 AI Solution Team BR/TS/TC/TD package and Test Team core-structure package (Segment 130 has one; Segment 131 does not), or approve continued use of synthesized `.synthetic.json` fixtures. | Item 2/3 need real or approved-synthetic data to certify beyond a placeholder. | AI-to-Test crosswalk; baseline and independence tests. | REVIEW_REQUIRED |

## Response Format

For each answer provide: `ID`, answer, source reference or configuration owner, effective environment, approved date, and any exception.

## Why All Four Items Are Open

No SME/TBA intake has yet occurred for Segment 131. Item P-01 (placement contradiction) is the highest-priority item, since it affects whether Segment 131 is modeled as a Data Section 2 or Data Section 3 construct in any downstream validator.
