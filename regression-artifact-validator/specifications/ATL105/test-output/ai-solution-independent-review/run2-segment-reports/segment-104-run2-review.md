# Segment 104 Run2 AI vs Test Solution Review

- Composite delivery: `2026-09-23/Run1+Run2`
- Decision: **REVIEW_REQUIRED**
- Version resolution: `ATL105-RUN2-SPEC-VERSION-RESOLUTION-001`
- Confirmed BR coverage: **14.3%**
- Full-chain coverage: **14.3%**

## Coverage

| Denominator | Confirmed | Review required | Missing | Unmatched AI | Full chain |
|---:|---:|---:|---:|---:|---:|
|14|2|1|11|36|2|

## Artifact chain

| AI BRs | Scenarios | Test cases | Test data |
|---:|---:|---:|---:|
|123|268|486|345|

## Payload validation

| Validator | Linked | Applicable | Valid | Invalid | Unreadable |
|---|---:|---:|---:|---:|---:|
|implemented|345|0|0|0|0|

## AI to Test Solution crosswalk

| Test rule | Status | AI requirement IDs | Canonical anchor | Reason |
|---|---|---|---|---|
|SEG104-R-001|MISSING||ATL105\|2026-3\|11.1.1\|104\|null\|section-3-companion-of-segment-100|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG104-R-002|MISSING||ATL105\|2026-3\|12.5\|104\|null\|purchase-card-segment-object-required|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG104-R-004|CONFIRMED|REQ-SRC-ATL105-PDF-001:254, REQ-SRC-ATL105-PDF-001:923, REQ-SRC-ATL105-PDF-001:930, REQ-SRC-ATL105-PDF-001:936, REQ-SRC-ATL105-PDF-001:1044, REQ-SRC-ATL105-PDF-001:1054, REQ-SRC-ATL105-PDF-001:1072, REQ-SRC-ATL105-PDF-001:1089, REQ-SRC-ATL105-PDF-001:1095, REQ-SRC-ATL105-PDF-001:1131, REQ-SRC-ATL105-PDF-001:1133, REQ-SRC-ATL105-PDF-001:1135, REQ-SRC-ATL105-PDF-001:1141, REQ-SRC-ATL105-PDF-001:1148, REQ-SRC-ATL105-PDF-001:1153, REQ-SRC-ATL105-PDF-001:1167, REQ-SRC-ATL105-PDF-001:1174, REQ-SRC-ATL105-PDF-001:1180, REQ-SRC-ATL105-PDF-001:1190, REQ-SRC-ATL105-PDF-001:1196, REQ-SRC-ATL105-PDF-001:1235, REQ-SRC-ATL105-PDF-001:1248, REQ-SRC-ATL105-PDF-001:1253, REQ-SRC-ATL105-PDF-001:1277, REQ-SRC-ATL105-PDF-001:1283, REQ-SRC-ATL105-PDF-001:1285, REQ-SRC-ATL105-PDF-001:1290, REQ-SRC-ATL105-PDF-001:1293, REQ-SRC-ATL105-PDF-001:1298, REQ-SRC-ATL105-PDF-001:1356, REQ-SRC-ATL105-PDF-001:1391, REQ-SRC-ATL105-PDF-001:1399, REQ-SRC-ATL105-PDF-001:1402, REQ-SRC-ATL105-PDF-001:1407, REQ-SRC-ATL105-PDF-001:1419, REQ-SRC-ATL105-PDF-001:1467, REQ-SRC-ATL105-PDF-001:1658, REQ-SRC-ATL105-PDF-001:1696, REQ-SRC-ATL105-PDF-001:1697, REQ-SRC-ATL105-PDF-001:1771, REQ-SRC-ATL105-PDF-001:1772, REQ-SRC-ATL105-PDF-001:1781, REQ-SRC-ATL105-PDF-001:1799|ATL105\|2026-3\|12.5\|104\|85\|segment-104-type-fixed-value|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG104-R-005|CONFIRMED|REQ-SRC-ATL105-PDF-001:1090, REQ-SRC-ATL105-PDF-001:1096, REQ-SRC-ATL105-PDF-001:1115, REQ-SRC-ATL105-PDF-001:1128, REQ-SRC-ATL105-PDF-001:1134, REQ-SRC-ATL105-PDF-001:1212, REQ-SRC-ATL105-PDF-001:1282, REQ-SRC-ATL105-PDF-001:1291, REQ-SRC-ATL105-PDF-001:1460, REQ-SRC-ATL105-PDF-001:1507, REQ-SRC-ATL105-PDF-001:1745, REQ-SRC-ATL105-PDF-001:1746, REQ-SRC-ATL105-PDF-001:1747, REQ-SRC-ATL105-PDF-001:1748, REQ-SRC-ATL105-PDF-001:1749, REQ-SRC-ATL105-PDF-001:1750, REQ-SRC-ATL105-PDF-001:1751, REQ-SRC-ATL105-PDF-001:1752, REQ-SRC-ATL105-PDF-001:1753, REQ-SRC-ATL105-PDF-001:1754, REQ-SRC-ATL105-PDF-001:1755, REQ-SRC-ATL105-PDF-001:1756, REQ-SRC-ATL105-PDF-001:1757, REQ-SRC-ATL105-PDF-001:1758, REQ-SRC-ATL105-PDF-001:1759, REQ-SRC-ATL105-PDF-001:1760, REQ-SRC-ATL105-PDF-001:1761, REQ-SRC-ATL105-PDF-001:1762, REQ-SRC-ATL105-PDF-001:1763, REQ-SRC-ATL105-PDF-001:1764, REQ-SRC-ATL105-PDF-001:1765, REQ-SRC-ATL105-PDF-001:1766, REQ-SRC-ATL105-PDF-001:1767, REQ-SRC-ATL105-PDF-001:1768, REQ-SRC-ATL105-PDF-001:1769, REQ-SRC-ATL105-PDF-001:1770, REQ-SRC-ATL105-PDF-001:2183, REQ-SRC-ATL105-PDF-001:2185|ATL105\|2026-3\|12.5\|104\|84\|segment-104-length-format|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG104-R-006|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1132, REQ-SRC-ATL105-PDF-001:1695, REQ-SRC-ATL105-PDF-001:1698, REQ-SRC-ATL105-PDF-001:1699, REQ-SRC-ATL105-PDF-001:1700, REQ-SRC-ATL105-PDF-001:1784|ATL105\|2026-3\|12.5\|104\|null\|segment-104-max-length|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG104-R-007|MISSING||ATL105\|2026-3\|12.5\|104\|80\|purchase-code-format|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG104-R-008|MISSING||ATL105\|2026-3\|12.5\|104\|74\|pc-tax-amount-format|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG104-R-009|MISSING||ATL105\|2026-3\|12.5\|104\|73\|pc-freight-amount-format|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG104-R-010|MISSING||ATL105\|2026-3\|12.5\|104\|72\|pc-duty-amount-format|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG104-R-011|MISSING||ATL105\|2026-3\|12.5\|104\|89\|ship-to-country-code-format|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG104-R-012|MISSING||ATL105\|2026-3\|12.5\|104\|90\|ship-to-postal-code-format|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG104-R-013|MISSING||ATL105\|2026-3\|12.5\|104\|88\|ship-from-postal-code-format|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG104-R-014|MISSING||ATL105\|2026-3\|12.5\|104\|29\|direct-marketing-invoice-number-format|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG104-R-020|MISSING||ATL105\|2026-3\|12\|104\|null\|segment-occurrence|No prior AI requirement was mapped to this independent Test Solution rule.|
