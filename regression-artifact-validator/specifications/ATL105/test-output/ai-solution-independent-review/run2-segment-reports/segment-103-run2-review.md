# Segment 103 Run2 AI vs Test Solution Review

- Composite delivery: `2026-09-23/Run1+Run2`
- Decision: **REVIEW_REQUIRED**
- Version resolution: `ATL105-RUN2-SPEC-VERSION-RESOLUTION-001`
- Confirmed BR coverage: **45.8%**
- Full-chain coverage: **37.5%**

## Coverage

| Denominator | Confirmed | Review required | Missing | Unmatched AI | Full chain |
|---:|---:|---:|---:|---:|---:|
|24|11|9|4|77|9|

## Artifact chain

| AI BRs | Scenarios | Test cases | Test data |
|---:|---:|---:|---:|
|208|269|477|387|

## Payload validation

| Validator | Linked | Applicable | Valid | Invalid | Unreadable |
|---|---:|---:|---:|---:|---:|
|implemented|387|0|0|0|0|

## AI to Test Solution crosswalk

| Test rule | Status | AI requirement IDs | Canonical anchor | Reason |
|---|---|---|---|---|
|SEG103-R-001|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:660, REQ-SRC-ATL105-PDF-001:1144|ATL105\|2026-3\|11.1,12.4\|103\|null\|section-3-companion-any-slot|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG103-R-002|MISSING||ATL105\|2026-3\|12.4,10.5.3,10.5.4.8,10.5.5,10.5.6\|103\|null\|ebt-request-conditional|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG103-R-003|CONFIRMED|REQ-SRC-ATL105-PDF-001:1104, REQ-SRC-ATL105-PDF-001:1188, REQ-SRC-ATL105-PDF-001:1204, REQ-SRC-ATL105-PDF-001:1305, REQ-SRC-ATL105-PDF-001:1370, REQ-SRC-ATL105-PDF-001:1785, REQ-SRC-ATL105-PDF-001:1786|ATL105\|2026-3\|12.4\|103\|85\|segment-type|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG103-R-004|CONFIRMED|REQ-SRC-ATL105-PDF-001:943, REQ-SRC-ATL105-PDF-001:1067, REQ-SRC-ATL105-PDF-001:1129, REQ-SRC-ATL105-PDF-001:1142, REQ-SRC-ATL105-PDF-001:1296, REQ-SRC-ATL105-PDF-001:1433, REQ-SRC-ATL105-PDF-001:1474, REQ-SRC-ATL105-PDF-001:1759|ATL105\|2026-3\|12.4\|103\|84\|segment-length|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG103-R-005|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1143, REQ-SRC-ATL105-PDF-001:1760|ATL105\|2026-3\|12.4\|103\|84\|segment-length-max-3334|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG103-R-006|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:937, REQ-SRC-ATL105-PDF-001:1110, REQ-SRC-ATL105-PDF-001:1521|ATL105\|2026-3\|12.4\|103\|null\|segment-103-field-order|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG103-R-007|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:950, REQ-SRC-ATL105-PDF-001:1140|ATL105\|2026-3\|12.4\|103\|null\|request-field-separator-required|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG103-R-008|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1141|ATL105\|2026-3\|12.4\|103\|null\|response-no-field-separator|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG103-R-009|CONFIRMED|REQ-SRC-ATL105-PDF-001:669, REQ-SRC-ATL105-PDF-001:671, REQ-SRC-ATL105-PDF-001:674, REQ-SRC-ATL105-PDF-001:1613, REQ-SRC-ATL105-PDF-001:1614|ATL105\|2026-3\|12.4\|103\|18\|clerk-id|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG103-R-010|CONFIRMED|REQ-SRC-ATL105-PDF-001:662, REQ-SRC-ATL105-PDF-001:666, REQ-SRC-ATL105-PDF-001:675, REQ-SRC-ATL105-PDF-001:1837|ATL105\|2026-3\|12.4\|103\|109\|voucher-id|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG103-R-011|CONFIRMED|REQ-SRC-ATL105-PDF-001:2003, REQ-SRC-ATL105-PDF-001:2004, REQ-SRC-ATL105-PDF-001:2694|ATL105\|2026-3\|13.2\|103\|153\|wic-discount-amount-format|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG103-R-012|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:2695|ATL105\|2026-3\|13.2\|103\|153\|wic-discount-amount-max-040|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG103-R-013|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:268, REQ-SRC-ATL105-PDF-001:1226, REQ-SRC-ATL105-PDF-001:1761, REQ-SRC-ATL105-PDF-001:1762, REQ-SRC-ATL105-PDF-001:1763, REQ-SRC-ATL105-PDF-001:1765, REQ-SRC-ATL105-PDF-001:1766, REQ-SRC-ATL105-PDF-001:1767, REQ-SRC-ATL105-PDF-001:1768, REQ-SRC-ATL105-PDF-001:1769, REQ-SRC-ATL105-PDF-001:1770, REQ-SRC-ATL105-PDF-001:1771, REQ-SRC-ATL105-PDF-001:1772, REQ-SRC-ATL105-PDF-001:1774, REQ-SRC-ATL105-PDF-001:1777, REQ-SRC-ATL105-PDF-001:1778, REQ-SRC-ATL105-PDF-001:1779, REQ-SRC-ATL105-PDF-001:1780, REQ-SRC-ATL105-PDF-001:1781, REQ-SRC-ATL105-PDF-001:1784, REQ-SRC-ATL105-PDF-001:2019, REQ-SRC-ATL105-PDF-001:2199|ATL105\|2026-3\|13.2\|103\|154\|wic-product-data-total-length|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG103-R-014|CONFIRMED|REQ-SRC-ATL105-PDF-001:2020, REQ-SRC-ATL105-PDF-001:2037, REQ-SRC-ATL105-PDF-001:2043, REQ-SRC-ATL105-PDF-001:2197, REQ-SRC-ATL105-PDF-001:2688, REQ-SRC-ATL105-PDF-001:2690, REQ-SRC-ATL105-PDF-001:2691|ATL105\|2026-3\|13.2\|103\|164\|ebt-program-data-total-length|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG103-R-015|CONFIRMED|REQ-SRC-ATL105-PDF-001:2038, REQ-SRC-ATL105-PDF-001:2039|ATL105\|2026-3\|13.2\|103\|164\|ebt-program-data-subelement-count|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG103-R-016|CONFIRMED|REQ-SRC-ATL105-PDF-001:712, REQ-SRC-ATL105-PDF-001:1764, REQ-SRC-ATL105-PDF-001:1773, REQ-SRC-ATL105-PDF-001:1775, REQ-SRC-ATL105-PDF-001:1776, REQ-SRC-ATL105-PDF-001:1782, REQ-SRC-ATL105-PDF-001:1783, REQ-SRC-ATL105-PDF-001:2045, REQ-SRC-ATL105-PDF-001:2051, REQ-SRC-ATL105-PDF-001:2052|ATL105\|2026-3\|13.2\|103\|164\|ebt-program-data-tag-enumeration|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG103-R-017|CONFIRMED|REQ-SRC-ATL105-PDF-001:1086, REQ-SRC-ATL105-PDF-001:1103, REQ-SRC-ATL105-PDF-001:1109, REQ-SRC-ATL105-PDF-001:1145, REQ-SRC-ATL105-PDF-001:1149, REQ-SRC-ATL105-PDF-001:1155, REQ-SRC-ATL105-PDF-001:1162, REQ-SRC-ATL105-PDF-001:1167, REQ-SRC-ATL105-PDF-001:1181, REQ-SRC-ATL105-PDF-001:1194, REQ-SRC-ATL105-PDF-001:1210, REQ-SRC-ATL105-PDF-001:1249, REQ-SRC-ATL105-PDF-001:1262, REQ-SRC-ATL105-PDF-001:1267, REQ-SRC-ATL105-PDF-001:1291, REQ-SRC-ATL105-PDF-001:1297, REQ-SRC-ATL105-PDF-001:1299, REQ-SRC-ATL105-PDF-001:1304, REQ-SRC-ATL105-PDF-001:1307, REQ-SRC-ATL105-PDF-001:1312, REQ-SRC-ATL105-PDF-001:1405, REQ-SRC-ATL105-PDF-001:1413, REQ-SRC-ATL105-PDF-001:1416, REQ-SRC-ATL105-PDF-001:1421, REQ-SRC-ATL105-PDF-001:1481, REQ-SRC-ATL105-PDF-001:2021, REQ-SRC-ATL105-PDF-001:2042, REQ-SRC-ATL105-PDF-001:2046, REQ-SRC-ATL105-PDF-001:2047, REQ-SRC-ATL105-PDF-001:2048, REQ-SRC-ATL105-PDF-001:2049, REQ-SRC-ATL105-PDF-001:2689, REQ-SRC-ATL105-PDF-001:2692, REQ-SRC-ATL105-PDF-001:2693|ATL105\|2026-3\|13.2\|103\|164\|ebt-program-data-account-type-98|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG103-R-018|MISSING||ATL105\|2026-3\|12\|103\|null\|segment-occurrence|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG103-R-019|MISSING||ATL105\|2026-3\|12.4\|103\|null\|origin-device|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG103-R-020|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:654|ATL105\|2026-3\|10.5.5.1\|103\|null\|ewic-no-return|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG103-R-021|MISSING||ATL105\|2026-3\|10.5.5.2\|100,103\|null\|ewic-prompt-code-enumeration|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG103-R-022|CONFIRMED|REQ-SRC-ATL105-PDF-001:711, REQ-SRC-ATL105-PDF-001:2044, REQ-SRC-ATL105-PDF-001:2050, REQ-SRC-ATL105-PDF-001:2696|ATL105\|2026-3\|13.2,Appendix M\|103\|164\|ebt-program-data-full-layout|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG103-R-023|CONFIRMED|REQ-SRC-ATL105-PDF-001:708, REQ-SRC-ATL105-PDF-001:1672, REQ-SRC-ATL105-PDF-001:2005, REQ-SRC-ATL105-PDF-001:2006, REQ-SRC-ATL105-PDF-001:2007, REQ-SRC-ATL105-PDF-001:2008, REQ-SRC-ATL105-PDF-001:2009, REQ-SRC-ATL105-PDF-001:2010, REQ-SRC-ATL105-PDF-001:2012|ATL105\|2026-3\|13.2\|103\|154\|wic-product-data-subelement-catalog|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG103-R-024|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:655, REQ-SRC-ATL105-PDF-001:658, REQ-SRC-ATL105-PDF-001:661, REQ-SRC-ATL105-PDF-001:667|ATL105\|2026-3\|10.5.2,10.5.3,10.5.4.8,10.5.5,10.5.6\|103\|null\|segment-103-applicability-matrix|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
