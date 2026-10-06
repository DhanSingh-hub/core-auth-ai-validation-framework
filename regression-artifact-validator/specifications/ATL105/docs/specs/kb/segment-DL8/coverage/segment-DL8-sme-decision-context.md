# Segment DL8 SME Decision Context

This note provides candidate-only context for the open DL8 provisional items. It does not replace the generated SME/TBA input register.

| Provisional item | Register ID | Current candidate handling | Required SME decision |
|---|---|---|---|
| P-01 | SEGDL8-SME-001 | The coverage package uses synthetic request/response objects only; approved/executed/certified counts remain zero. | Provide a dedicated DL8 AI/Test package or approve synthetic DL8 fixtures. |
| P-02 | SEGDL8-SME-002 | Rules SEGDL8-R-001 and SEGDL8-R-002 remain REVIEW_REQUIRED. Candidate data uses `OMITTED_PENDING_P02` for Table Load placement and terminal Special name. | Confirm where DL8 appears in Table Load Response, relative to DL6 and End-of-Load, the actual Special name, and Element 84 source handling. |
| P-03 | SEGDL8-SME-003 | Rule SEGDL8-R-004 remains REVIEW_REQUIRED. Candidate data uses source-backed widths only and does not certify RID/card-type semantics. | Confirm RID representation, BUYPASS RID Card Type valid values, and whether Stand-in Indicator 1 permits a non-zero Floor Limit. |

Until these items close, DL8 artifacts are comparison-only candidate evidence and must not be counted as executed Test Solution coverage.
