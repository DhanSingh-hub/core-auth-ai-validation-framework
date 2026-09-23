# Segment 109 Canonical Source Anchors

The training and validation artifacts for Segment 109 use these canonical anchors. They are source references, not inferred operational policy.

| Anchor | Source evidence | Scope |
|---|---|---|
| `ATL105|2026-3|10.11.1|Segment109|PromptCode|retrieval` | Pages 10-60 to 10-61 | Retrieval is `981`; proprietary-card retrieval is `996`; up to 750 bytes of print data. |
| `ATL105|2026-3|10.11.2|Segment109|PromptCode|submission` | Pages 10-60 to 10-61 | Submission is `995`; up to 217 bytes; host confirms receipt. |
| `ATL105|2026-3|11.5.1|ElectronicMailRequest|DataSection1|envelope` | Pages 11-21 to 11-22 | Required Elements 55 and 63, separated and terminated by Field Separators. |
| `ATL105|2026-3|11.5.1|Segment109|placement` | Page 11-22 | Segment 109 is Data Section 2, field 3. |
| `ATL105|2026-3|11.5.2|ElectronicMailResponse|layout` | Page 11-23 | Positional variable-length response with no Field Separators. |
| `ATL105|2026-3|12.8|Segment109|segment-type` | Page 12-23 | Element 85 is fixed `109`. |
| `ATL105|2026-3|12.8|Segment109|segment-length` | Page 12-23 | Element 84 is three numeric characters and includes segment type and separators. |
| `ATL105|2026-3|12.8|Segment109|field-order` | Pages 12-23 to 12-24 | Fourteen ordered fields. |
| `ATL105|2026-3|12.8|Segment109|empty-separators` | Page 12-23 | Empty fields retain Field Separators. |
| `ATL105|2026-3|12.8|Segment109|max-length` | Page 12-23 | Segment length is 001-232 alphanumeric characters. |
| `ATL105|2026-3|12.8|Segment109|text-data` | Page 12-24 | Text Data Length and Text Data are conditional, lengths 3 and 150. |

## Boundary

An anchor confirms only the behavior stated by its source. Information Byte values, Block Number allocation, local terminal format, credential policy, response-code semantics, and payload fragmentation remain `REVIEW_REQUIRED` until the SME/TBA input register records an approved decision.