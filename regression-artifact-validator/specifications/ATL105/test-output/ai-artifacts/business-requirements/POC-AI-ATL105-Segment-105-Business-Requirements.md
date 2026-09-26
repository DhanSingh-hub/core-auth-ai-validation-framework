# POC AI Segment 105 Business Requirements

Source: `step5_requirements/approved/requirement_catalog.json`

Scope: rules whose source rule is directly related to ENT-SEG-105. Shared-element and cross-segment candidates are excluded from this approved-scope package.

Count: 13
Excluded related/cross-segment candidates: 260
Review required: 3
Below confidence gate: 3
Confidence bands: HIGH=10, MEDIUM=2, LOW=1
Duplicate source-rule groups preserved: 1
Exact duplicate rows removed: 0
Baseline status: AVAILABLE (17 requirements; 4 review-required)
Validation status: VALID

| ID | Scope | Source rule | Page | Confidence | Requirement |
|---|---|---|---:|---:|---|
| REQ-SRC-ATL105-PDF-001:407 | DIRECT_SEGMENT_105 | BR-62-3 | 62 | 80% | A Totals Request (999999 - Shift Read and Reset) follows successful completion of a batch upload. |
| REQ-SRC-ATL105-PDF-001:413 | DIRECT_SEGMENT_105 | BR-62-9 | 62 | 80% | At completion of business day, terminal sends Totals Request (222222 - Read and Cut Settlement) after last batch. |
| REQ-SRC-ATL105-PDF-001:705 | DIRECT_SEGMENT_105 | BR-118-4 | 118 | 57% | eWIC Dollar Amount and eWIC Count fields are added to Totals Response for eWIC processing. |
| REQ-SRC-ATL105-PDF-001:987 | DIRECT_SEGMENT_105 | BR-175-3 | 175 | 85% | Totals Request Data Section No. 3 contains the Totals Data Segment (Data Segment No. 105). |
| REQ-SRC-ATL105-PDF-001:990 | DIRECT_SEGMENT_105 | BR-176-3 | 176 | 85% | Totals Data Segment sent only on transactions requiring totals data. |
| REQ-SRC-ATL105-PDF-001:1181 | DIRECT_SEGMENT_105 | BR-232-1 | 232 | 25% | a field is not populated â€” still send the Field Separator. |
| REQ-SRC-ATL105-PDF-001:1182 | DIRECT_SEGMENT_105 | BR-232-1 | 232 | 90% | Totals Data Segment has a maximum length of 409 alphanumeric characters. |
| REQ-SRC-ATL105-PDF-001:1183 | DIRECT_SEGMENT_105 | BR-232-2 | 232 | 90% | Field Nos. 1-14 are separated by Field Separators, including one after Field No. 14. |
| REQ-SRC-ATL105-PDF-001:1184 | DIRECT_SEGMENT_105 | BR-232-3 | 232 | 90% | When a field is not populated, still send the Field Separator. |
| REQ-SRC-ATL105-PDF-001:1185 | DIRECT_SEGMENT_105 | BR-232-4 | 232 | 90% | Field Nos. 15-17 are not separated by Field Separators. |
| REQ-SRC-ATL105-PDF-001:1734 | DIRECT_SEGMENT_105 | BR-384-5 | 384 | 85% | For Totals Request messages, Element 63 (Number of Segments) and Segment 105 (Totals Data Segment) always follow Element 55. |
| REQ-SRC-ATL105-PDF-001:1757 | DIRECT_SEGMENT_105 | BR-388-4 | 388 | 90% | Totals Requests: Number of Segments always followed by Segment 105, Totals Data Segment. |
| REQ-SRC-ATL105-PDF-001:1829 | DIRECT_SEGMENT_105 | BR-400-6 | 400 | 57% | Totals Data Segment length is 001-409. |
