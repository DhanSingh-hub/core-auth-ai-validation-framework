# Segment 113 Run2 AI vs Test Solution Review

- Composite delivery: `2026-09-23/Run1+Run2`
- Decision: **REVIEW_REQUIRED**
- Version resolution: `ATL105-RUN2-SPEC-VERSION-RESOLUTION-001`
- Confirmed BR coverage: **5.6%**
- Full-chain coverage: **5.6%**

## Coverage

| Denominator | Confirmed | Review required | Missing | Unmatched AI | Full chain |
|---:|---:|---:|---:|---:|---:|
|18|1|12|5|31|1|

## Artifact chain

| AI BRs | Scenarios | Test cases | Test data |
|---:|---:|---:|---:|
|91|158|483|400|

## Payload validation

| Validator | Linked | Applicable | Valid | Invalid | Unreadable |
|---|---:|---:|---:|---:|---:|
|implemented|400|0|0|0|0|

## AI to Test Solution crosswalk

| Test rule | Status | AI requirement IDs | Canonical anchor | Reason |
|---|---|---|---|---|
|SEG113-R-001|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:983|ATL105\|2026-3\|11.1.1,11.3.1\|113\|63\|ecatelecheck-transaction-exclusive|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG113-R-002|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:979, REQ-SRC-ATL105-PDF-001:1218, REQ-SRC-ATL105-PDF-001:1337, REQ-SRC-ATL105-PDF-001:1346, REQ-SRC-ATL105-PDF-001:2043|ATL105\|2026-3\|11.3.1\|113\|null\|ecatelecheck-request-conditional|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG113-R-003|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1224, REQ-SRC-ATL105-PDF-001:1238, REQ-SRC-ATL105-PDF-001:1246, REQ-SRC-ATL105-PDF-001:1291, REQ-SRC-ATL105-PDF-001:1330, REQ-SRC-ATL105-PDF-001:1343, REQ-SRC-ATL105-PDF-001:1409, REQ-SRC-ATL105-PDF-001:1564, REQ-SRC-ATL105-PDF-001:1848, REQ-SRC-ATL105-PDF-001:1849|ATL105\|2026-3\|12.12\|113\|85\|segment-type|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG113-R-004|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1178, REQ-SRC-ATL105-PDF-001:1521, REQ-SRC-ATL105-PDF-001:1821, REQ-SRC-ATL105-PDF-001:1822|ATL105\|2026-3\|12.12\|113\|84\|segment-length|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG113-R-005|CONFIRMED|REQ-SRC-ATL105-PDF-001:1134, REQ-SRC-ATL105-PDF-001:1165, REQ-SRC-ATL105-PDF-001:1226, REQ-SRC-ATL105-PDF-001:1335, REQ-SRC-ATL105-PDF-001:1835|ATL105\|2026-3\|12.12\|113\|84\|segment-length-max-156|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG113-R-006|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1146, REQ-SRC-ATL105-PDF-001:1187, REQ-SRC-ATL105-PDF-001:1215, REQ-SRC-ATL105-PDF-001:1323, REQ-SRC-ATL105-PDF-001:1435|ATL105\|2026-3\|12.12\|113\|null\|segment-113-field-order|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG113-R-007|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:793, REQ-SRC-ATL105-PDF-001:1135, REQ-SRC-ATL105-PDF-001:1227, REQ-SRC-ATL105-PDF-001:1301, REQ-SRC-ATL105-PDF-001:1340, REQ-SRC-ATL105-PDF-001:1356, REQ-SRC-ATL105-PDF-001:1433, REQ-SRC-ATL105-PDF-001:1436, REQ-SRC-ATL105-PDF-001:1437, REQ-SRC-ATL105-PDF-001:1823, REQ-SRC-ATL105-PDF-001:2251, REQ-SRC-ATL105-PDF-001:2263|ATL105\|2026-3\|12.12\|113\|null\|field-separator-required|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG113-R-008|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1208, REQ-SRC-ATL105-PDF-001:1669, REQ-SRC-ATL105-PDF-001:2049, REQ-SRC-ATL105-PDF-001:2716, REQ-SRC-ATL105-PDF-001:2717|ATL105\|2026-3\|12.12,13.2\|113\|131\|eca-clerk-id|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG113-R-009|MISSING||ATL105\|2026-3\|12.12,13.2\|113\|132\|eca-product-code|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG113-R-010|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:788, REQ-SRC-ATL105-PDF-001:798, REQ-SRC-ATL105-PDF-001:1990|ATL105\|2026-3\|12.12,13.2\|113\|133\|eca-phone-number|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG113-R-011|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:789, REQ-SRC-ATL105-PDF-001:1225, REQ-SRC-ATL105-PDF-001:1989|ATL105\|2026-3\|12.12,13.2\|113\|134\|eca-trace-id|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG113-R-012|MISSING||ATL105\|2026-3\|12.12,13.2\|113\|135\|merchant-trace-id|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG113-R-013|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:796, REQ-SRC-ATL105-PDF-001:797|ATL105\|2026-3\|10.8.5,12.12,13.2\|113\|136\|denial-record-number|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG113-R-014|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1228, REQ-SRC-ATL105-PDF-001:1282, REQ-SRC-ATL105-PDF-001:2050|ATL105\|2026-3\|12.12,13.2\|113,110\|137\|extended-micr-data|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG113-R-015|MISSING||ATL105\|2026-3\|12\|113\|null\|segment-occurrence|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG113-R-016|MISSING||ATL105\|2026-3\|12.12\|113\|null\|origin-device|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG113-R-017|MISSING||ATL105\|2026-3\|11.3.1\|113,110,111\|null\|ecatelecheck-companion-segments|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG113-R-018|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:785, REQ-SRC-ATL105-PDF-001:786|ATL105\|2026-3\|11.3.2\|113\|null\|ecatelecheck-segment-request-only|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
