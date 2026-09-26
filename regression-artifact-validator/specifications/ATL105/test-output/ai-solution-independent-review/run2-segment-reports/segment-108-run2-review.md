# Segment 108 Run2 AI vs Test Solution Review

- Composite delivery: `2026-09-23/Run1+Run2`
- Decision: **REVIEW_REQUIRED**
- Version resolution: `ATL105-RUN2-SPEC-VERSION-RESOLUTION-001`
- Confirmed BR coverage: **20.8%**
- Full-chain coverage: **20.8%**

## Coverage

| Denominator | Confirmed | Review required | Missing | Unmatched AI | Full chain |
|---:|---:|---:|---:|---:|---:|
|24|5|16|3|94|5|

## Artifact chain

| AI BRs | Scenarios | Test cases | Test data |
|---:|---:|---:|---:|
|227|956|1163|641|

## Payload validation

| Validator | Linked | Applicable | Valid | Invalid | Unreadable |
|---|---:|---:|---:|---:|---:|
|implemented|641|0|0|0|0|

## AI to Test Solution crosswalk

| Test rule | Status | AI requirement IDs | Canonical anchor | Reason |
|---|---|---|---|---|
|SEG108-R-001|CONFIRMED|REQ-SRC-ATL105-PDF-001:804, REQ-SRC-ATL105-PDF-001:812, REQ-SRC-ATL105-PDF-001:971, REQ-SRC-ATL105-PDF-001:1197, REQ-SRC-ATL105-PDF-001:1735, REQ-SRC-ATL105-PDF-001:1755|ATL105\|2026-3\|11.2.1,13.2\|108\|63\|loyalty-transaction-exclusive|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG108-R-002|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:811, REQ-SRC-ATL105-PDF-001:813, REQ-SRC-ATL105-PDF-001:973, REQ-SRC-ATL105-PDF-001:1340|ATL105\|2026-3\|11.2.1\|108\|null\|loyalty-request-required|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG108-R-003|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1194, REQ-SRC-ATL105-PDF-001:1224, REQ-SRC-ATL105-PDF-001:1238, REQ-SRC-ATL105-PDF-001:1246, REQ-SRC-ATL105-PDF-001:1291, REQ-SRC-ATL105-PDF-001:1330, REQ-SRC-ATL105-PDF-001:1343, REQ-SRC-ATL105-PDF-001:1409, REQ-SRC-ATL105-PDF-001:1848, REQ-SRC-ATL105-PDF-001:1849|ATL105\|2026-3\|12.7\|108\|85\|segment-type|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG108-R-004|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1134, REQ-SRC-ATL105-PDF-001:1178, REQ-SRC-ATL105-PDF-001:1521, REQ-SRC-ATL105-PDF-001:1821, REQ-SRC-ATL105-PDF-001:1822|ATL105\|2026-3\|12.7\|108\|84\|segment-length|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG108-R-005|CONFIRMED|REQ-SRC-ATL105-PDF-001:1165, REQ-SRC-ATL105-PDF-001:1335, REQ-SRC-ATL105-PDF-001:1830, REQ-SRC-ATL105-PDF-001:2319|ATL105\|2026-3\|12.7\|108\|84\|segment-length-max-142|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG108-R-006|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1337|ATL105\|2026-3\|12.7\|108\|null\|segment-108-field-order|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG108-R-007|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:523, REQ-SRC-ATL105-PDF-001:545, REQ-SRC-ATL105-PDF-001:609, REQ-SRC-ATL105-PDF-001:1135, REQ-SRC-ATL105-PDF-001:1191, REQ-SRC-ATL105-PDF-001:1215, REQ-SRC-ATL105-PDF-001:1301, REQ-SRC-ATL105-PDF-001:1356, REQ-SRC-ATL105-PDF-001:1433, REQ-SRC-ATL105-PDF-001:1435, REQ-SRC-ATL105-PDF-001:1437, REQ-SRC-ATL105-PDF-001:1823, REQ-SRC-ATL105-PDF-001:2251, REQ-SRC-ATL105-PDF-001:2263|ATL105\|2026-3\|12.7\|108\|null\|field-separator-required|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG108-R-008|CONFIRMED|REQ-SRC-ATL105-PDF-001:2051, REQ-SRC-ATL105-PDF-001:2731|ATL105\|2026-3\|12.7\|108\|138\|loyalty-program-id|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG108-R-009|CONFIRMED|REQ-SRC-ATL105-PDF-001:521, REQ-SRC-ATL105-PDF-001:546, REQ-SRC-ATL105-PDF-001:802, REQ-SRC-ATL105-PDF-001:803, REQ-SRC-ATL105-PDF-001:805, REQ-SRC-ATL105-PDF-001:815, REQ-SRC-ATL105-PDF-001:816, REQ-SRC-ATL105-PDF-001:921, REQ-SRC-ATL105-PDF-001:1193, REQ-SRC-ATL105-PDF-001:1613, REQ-SRC-ATL105-PDF-001:1648, REQ-SRC-ATL105-PDF-001:2052|ATL105\|2026-3\|12.7\|108\|139\|loyalty-account-number|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG108-R-010|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:809, REQ-SRC-ATL105-PDF-001:810|ATL105\|2026-3\|12.7\|108\|140\|points-to-redeem|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG108-R-011|MISSING||ATL105\|2026-3\|12.7\|108\|141\|coupon-id|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG108-R-012|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:807, REQ-SRC-ATL105-PDF-001:808, REQ-SRC-ATL105-PDF-001:1707|ATL105\|2026-3\|12.7\|108\|142\|coupon-amount|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG108-R-013|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1436, REQ-SRC-ATL105-PDF-001:1614|ATL105\|2026-3\|13.2\|108\|143\|update-code-enumeration|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG108-R-014|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:795, REQ-SRC-ATL105-PDF-001:1195, REQ-SRC-ATL105-PDF-001:2838|ATL105\|2026-3\|12.7\|108\|144\|street-address|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG108-R-015|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:806|ATL105\|2026-3\|12.7\|108\|145\|phone-number-loyalty|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG108-R-016|CONFIRMED|REQ-SRC-ATL105-PDF-001:460, REQ-SRC-ATL105-PDF-001:522, REQ-SRC-ATL105-PDF-001:526, REQ-SRC-ATL105-PDF-001:536, REQ-SRC-ATL105-PDF-001:537, REQ-SRC-ATL105-PDF-001:544, REQ-SRC-ATL105-PDF-001:547, REQ-SRC-ATL105-PDF-001:550, REQ-SRC-ATL105-PDF-001:600, REQ-SRC-ATL105-PDF-001:601, REQ-SRC-ATL105-PDF-001:608, REQ-SRC-ATL105-PDF-001:610, REQ-SRC-ATL105-PDF-001:651, REQ-SRC-ATL105-PDF-001:654, REQ-SRC-ATL105-PDF-001:661, REQ-SRC-ATL105-PDF-001:719, REQ-SRC-ATL105-PDF-001:720, REQ-SRC-ATL105-PDF-001:722, REQ-SRC-ATL105-PDF-001:723, REQ-SRC-ATL105-PDF-001:727, REQ-SRC-ATL105-PDF-001:799, REQ-SRC-ATL105-PDF-001:800, REQ-SRC-ATL105-PDF-001:801, REQ-SRC-ATL105-PDF-001:1190, REQ-SRC-ATL105-PDF-001:1196, REQ-SRC-ATL105-PDF-001:1448, REQ-SRC-ATL105-PDF-001:1459, REQ-SRC-ATL105-PDF-001:1460, REQ-SRC-ATL105-PDF-001:1646, REQ-SRC-ATL105-PDF-001:1647, REQ-SRC-ATL105-PDF-001:1650, REQ-SRC-ATL105-PDF-001:1651, REQ-SRC-ATL105-PDF-001:1654, REQ-SRC-ATL105-PDF-001:1655, REQ-SRC-ATL105-PDF-001:1656, REQ-SRC-ATL105-PDF-001:1657, REQ-SRC-ATL105-PDF-001:1658, REQ-SRC-ATL105-PDF-001:1997, REQ-SRC-ATL105-PDF-001:2061, REQ-SRC-ATL105-PDF-001:2068, REQ-SRC-ATL105-PDF-001:2732, REQ-SRC-ATL105-PDF-001:2733, REQ-SRC-ATL105-PDF-001:2846, REQ-SRC-ATL105-PDF-001:2992, REQ-SRC-ATL105-PDF-001:2997, REQ-SRC-ATL105-PDF-001:3003|ATL105\|2026-3\|10.9.1.2,13.2\|108\|146\|expiration-date-mmyy|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG108-R-017|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:659, REQ-SRC-ATL105-PDF-001:709, REQ-SRC-ATL105-PDF-001:1346, REQ-SRC-ATL105-PDF-001:2053|ATL105\|2026-3\|13.2\|108\|148\|payment-tender-type-enumeration|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG108-R-018|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:779, REQ-SRC-ATL105-PDF-001:1187, REQ-SRC-ATL105-PDF-001:1192, REQ-SRC-ATL105-PDF-001:1282, REQ-SRC-ATL105-PDF-001:1564, REQ-SRC-ATL105-PDF-001:2256|ATL105\|2026-3\|12.7\|108\|147\|loyalty-track2-data|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG108-R-019|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:794, REQ-SRC-ATL105-PDF-001:819, REQ-SRC-ATL105-PDF-001:2054|ATL105\|2026-3\|13.2\|108\|150\|loyalty-information-version|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG108-R-020|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:2055|ATL105\|2026-3\|13.2\|108\|151\|unit-of-work|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG108-R-021|MISSING||ATL105\|2026-3\|12\|108\|null\|segment-occurrence|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG108-R-022|MISSING||ATL105\|2026-3\|12.7\|108\|null\|origin-device|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG108-R-023|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1146, REQ-SRC-ATL105-PDF-001:1218, REQ-SRC-ATL105-PDF-001:1323|ATL105\|2026-3\|11.2.1\|108,114\|null\|loyalty-sku-sole-companion|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG108-R-024|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:920|ATL105\|2026-3\|11.2.2\|108\|null\|loyalty-segment-request-only|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
