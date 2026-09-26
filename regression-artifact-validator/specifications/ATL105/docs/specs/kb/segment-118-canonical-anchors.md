# Segment 118 Canonical Source Anchors

The training and validation artifacts for Segment 118 use these canonical anchors. They are source references, not inferred operational policy.

| Anchor | Source evidence | Scope |
|---|---|---|
| `ATL105|2026-3|11.7.6.1|Segment118|request-envelope-no-segment-100` | Pages 11-36 to 11-37 | Request contains Data Section 1 (55, 63) and Data Section 3 (Segment 118); explicitly excludes Segment 100. |
| `ATL105|2026-3|11.7.7|Segment118|response-envelope` | Pages 11-38 to 11-39 | Response contains Data Section 1 (Response Code, Download Indicator, Initiation Date/Time, Sequence Number) and Data Section 3 (Segment 118). |
| `ATL105|2026-3|12.16|Segment118|segment-type` | Page 12-37 | Element 85 fixed `118`. |
| `ATL105|2026-3|12.16|Segment118|segment-length-4-digits` | Page 12-37 | Element 84, 4 numeric digits (not the usual 3). |
| `ATL105|2026-3|12.16|Segment118|max-length` | Page 12-37 | 01-3,800 alphanumeric characters, request and response. |
| `ATL105|2026-3|12.16|Segment118|field-separators` | Page 12-37 | Request fields 1-13 separated; response fields positional (no separators). |
| `ATL105|2026-3|12.16,AppendixE|Segment118|element-78|prompt-code` | Page 12-38, Appendix E | Prompt Code values 901-905. |
| `ATL105|2026-3|Chapter13-Element182|Segment118|prompt-code-pending` | Chapter 13 | Values 0901, 0902, 0904, 0981. |
| `ATL105|2026-3|Chapter13-Element174|Segment118|card-table-type` | Chapter 13 | Values 0001-0006 (BIN, RULES, RESTRICTIONS, SAF, PROMPT, PRODUCT). |
| `ATL105|2026-3|12.16.1|Segment118|prompt-code-901` | Pages 12-40 | Custom Receipt Text Data, fields 14-20, response only. |
| `ATL105|2026-3|12.16.2|Segment118|prompt-code-902` | Page 12-41 | Dynamic Card Table Data, field 14, response only. |
| `ATL105|2026-3|12.16.3|Segment118|prompt-code-903` | Pages 12-41 to 12-42 | Site Configuration Data, field 14, request only; Block Number lifecycle. |
| `ATL105|2026-3|12.16.4|Segment118|prompt-code-904` | Pages 12-43 to 12-44 | Host Discount Data, fields 14-27, response only; Segment 102 cross-reference. |
| `ATL105|2026-3|12.16.5|Segment118|prompt-code-905` | Page 12-45 | Fuel Volume Data, field 14, request only. |
| `ATL105|2026-3|Chapter13-Element83|Segment118|response-code-catalog` | Chapter 13 | H, O, T, U, V, W, X, Y (proprietary load / receipt text pending); D, E, M, N (totals pending indicators). |

## Boundary

An anchor confirms only the behavior stated by its source. Information Byte values, the Receipt Text Data encoding nuance, and the full multi-block retry/resume lifecycle beyond Site Configuration Data's documented Block Number = 0 convention remain `REVIEW_REQUIRED` until the SME/TBA input register records an approved decision.
