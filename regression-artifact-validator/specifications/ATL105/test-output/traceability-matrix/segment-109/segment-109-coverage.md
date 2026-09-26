# Segment 109 Traceability Matrix

- Package: `ATL105-SEG109-RULE-CATALOG-001`
- Status: **IN_PROGRESS_AI_EVIDENCE_UNDER_REVIEW**
- Covered: 5
- Partially covered: 8
- Review required: 7
- Missing: 2

Business Requirement (BR) evidence in this matrix means an AI-generated requirement/scenario statement exists for the rule (see the [Supplied AI Pipeline Coverage Report](../../../docs/specs/kb/segment-109/coverage/segment-109-supplied-pipeline-ai-coverage-report.md)). Scenario evidence reflects linked AI scenario records (derivation-labelled, not executed). Test Case and Test Data remain blocked pending the [Segment 109 SME/TBA Input Register](../../../docs/specs/kb/segment-109/segment-109-sme-tba-input-register.md).

| Rule | Title | Class | Status | BR (AI evidence) | Scenario | Test Case | Test Data |
| --- | --- | --- | --- | --- | --- | --- | --- |
| `SEG109-R-001` | Electronic Mail Request has Data Section 1 followed by Data Section 2 containing Segment 109 | structure | COVERED_FOR_BR_MAPPING | partial | partial | no | no |
| `SEG109-R-002` | Data Section 1 contains required Message Format Version Identifier (Element 55) and Number of Segments (Element 63), separated and followed by a Field Separator | structure | PARTIALLY_COVERED | partial | partial | no | no |
| `SEG109-R-003` | Segment 109 is field 3 in Data Section 2 and is sent only for transactions requiring electronic mail | applicability | REVIEW_REQUIRED | partial | partial | no | no |
| `SEG109-R-004` | Segment Type is fixed value 109 | field | COVERED_FOR_BR_MAPPING | partial | partial | no | no |
| `SEG109-R-005` | Segment Length is three numeric characters and includes Segment Type and Field Separators | field | PARTIALLY_COVERED | partial | partial | no | no |
| `SEG109-R-006` | Segment 109 maximum length is 232 alphanumeric characters | serialization | COVERED_FOR_BR_MAPPING | partial | partial | no | no |
| `SEG109-R-007` | Fields are ordered as defined in Section 12.8 | serialization | MISSING | no | no | no | no |
| `SEG109-R-008` | Every Segment 109 field is separated by a Field Separator, including empty fields | serialization | COVERED_FOR_BR_MAPPING | partial | partial | no | no |
| `SEG109-R-009` | Information Byte is required and one numeric character | field | PARTIALLY_COVERED | partial | partial | no | no |
| `SEG109-R-010` | Terminal Identifier is required and uses the approved device identifier format | field | PARTIALLY_COVERED | partial | partial | no | no |
| `SEG109-R-011` | Prompt Code is required and must be 981 for standard retrieval, 996 for proprietary-card-data retrieval, or 995 for submission | compatibility | REVIEW_REQUIRED | partial | partial | no | no |
| `SEG109-R-012` | Block Number is required and three numeric characters | field | PARTIALLY_COVERED | partial | partial | no | no |
| `SEG109-R-013` | Employee Number, when populated, is numeric with maximum length four | field | REVIEW_REQUIRED | partial | partial | no | no |
| `SEG109-R-014` | Password, when populated, is alphanumeric with maximum length six and must be masked in fixtures | field | REVIEW_REQUIRED | partial | partial | no | no |
| `SEG109-R-015` | Sequence Number is required and six numeric characters | field | PARTIALLY_COVERED | partial | partial | no | no |
| `SEG109-R-016` | Local Time, Extract Date, and Extract Time, when populated, are numeric with lengths four, six, and four respectively | field | REVIEW_REQUIRED | partial | partial | no | no |
| `SEG109-R-017` | Text Data Length, when populated, is three numeric characters and represents Text Data length | interdependency | PARTIALLY_COVERED | partial | partial | no | no |
| `SEG109-R-018` | Text Data, when populated, is alphanumeric with maximum length 150 and agrees with Text Data Length | interdependency | PARTIALLY_COVERED | partial | partial | no | no |
| `SEG109-R-019` | Retrieval requests use Prompt Code 981, or 996 when retrieving proprietary card data, and allow up to 750 bytes of print data per request | applicability | REVIEW_REQUIRED | partial | partial | no | no |
| `SEG109-R-020` | Submission requests use Prompt Code 995, contain up to 217 bytes of data, and receive a confirmation response | applicability | REVIEW_REQUIRED | partial | partial | no | no |
| `SEG109-R-021` | Electronic Mail Response is variable length, positional, and contains no Field Separators | response | COVERED_FOR_BR_MAPPING | partial | partial | no | no |
| `SEG109-R-022` | Electronic Mail Response contains required Response Code, Download Indicator, Initiation Date, Initiation Time, Sequence Number, and Block Number; optional Mail Text Data Length and Mail Text Data are correlated | response | MISSING | no | no | no | no |
