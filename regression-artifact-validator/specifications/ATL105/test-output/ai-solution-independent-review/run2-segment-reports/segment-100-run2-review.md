# Segment 100 Run2 AI vs Test Solution Review

- Composite delivery: `2026-09-23/Run1+Run2`
- Decision: **REVIEW_REQUIRED**
- Version resolution: `ATL105-RUN2-SPEC-VERSION-RESOLUTION-001`
- Confirmed BR coverage: **10.3%**
- Full-chain coverage: **10.3%**

## Coverage

| Denominator | Confirmed | Review required | Missing | Unmatched AI | Full chain |
|---:|---:|---:|---:|---:|---:|
|58|6|24|28|503|6|

## Artifact chain

| AI BRs | Scenarios | Test cases | Test data |
|---:|---:|---:|---:|
|732|2323|3621|2139|

## Payload validation

| Validator | Linked | Applicable | Valid | Invalid | Unreadable |
|---|---:|---:|---:|---:|---:|
|implemented|2139|1934|1934|0|0|

## AI to Test Solution crosswalk

| Test rule | Status | AI requirement IDs | Canonical anchor | Reason |
|---|---|---|---|---|
|SEG100-R-001|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:925, REQ-SRC-ATL105-PDF-001:949, REQ-SRC-ATL105-PDF-001:955, REQ-SRC-ATL105-PDF-001:960, REQ-SRC-ATL105-PDF-001:1027, REQ-SRC-ATL105-PDF-001:1030, REQ-SRC-ATL105-PDF-001:1045, REQ-SRC-ATL105-PDF-001:1068, REQ-SRC-ATL105-PDF-001:1073, REQ-SRC-ATL105-PDF-001:1086|ATL105\|2026-3\|11.1.1\|100\|null\|segment-100-required-once|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG100-R-002|MISSING||ATL105\|2026-3\|11.8.1\|130\|null\|section-3-required-for-emv|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-003|MISSING||ATL105\|2026-3\|11.1.1\|101\|null\|fleet-data-required|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-004|MISSING||ATL105\|2026-3\|11.1.1\|102\|null\|product-or-fuel-data-required|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-005|MISSING||ATL105\|2026-3\|11.1.1\|101,102\|null\|multiple-section-3-segments-required|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-006|MISSING||ATL105\|2026-3\|12\|UNKNOWN\|null\|unknown-combination-requires-review|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-007|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1688|ATL105\|2026-3\|11.1.1\|DATA-SECTION-1\|63\|number-of-segments|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG100-R-008|MISSING||ATL105\|2026-3\|12\|101\|null\|companion-segment-non-duplication|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-009|MISSING||ATL105\|2026-3\|12\|100\|null\|segment-100-field-order|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-010|MISSING||ATL105\|2026-3\|Appendix A\|TCP-IP-HEADER\|message-length\|message-length|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-011|MISSING||ATL105\|2026-3\|Appendix A\|TCP-IP-HEADER\|message-length\|network-byte-order|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-012|MISSING||ATL105\|2026-3\|Appendix A\|TCP-IP-HEADER\|protocol-id\|tpdu-protocol-id|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-024|MISSING||ATL105\|2026-3\|Appendix A\|TCP-IP-HEADER\|destination-source-address\|reserved-tpdu-addresses|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-013|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:956, REQ-SRC-ATL105-PDF-001:1657|ATL105\|2026-3\|11.1.1\|DATA-SECTION-1\|55\|message-format-version-identifier|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG100-R-014|MISSING||ATL105\|2026-3\|11.1.1\|DATA-SECTION-1\|55-63\|section-1-field-separators|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-015|CONFIRMED|REQ-SRC-ATL105-PDF-001:1072, REQ-SRC-ATL105-PDF-001:1089, REQ-SRC-ATL105-PDF-001:1095, REQ-SRC-ATL105-PDF-001:1131, REQ-SRC-ATL105-PDF-001:1141, REQ-SRC-ATL105-PDF-001:1148, REQ-SRC-ATL105-PDF-001:1153, REQ-SRC-ATL105-PDF-001:1167, REQ-SRC-ATL105-PDF-001:1174, REQ-SRC-ATL105-PDF-001:1180, REQ-SRC-ATL105-PDF-001:1190, REQ-SRC-ATL105-PDF-001:1196, REQ-SRC-ATL105-PDF-001:1235, REQ-SRC-ATL105-PDF-001:1253, REQ-SRC-ATL105-PDF-001:1277, REQ-SRC-ATL105-PDF-001:1282, REQ-SRC-ATL105-PDF-001:1283, REQ-SRC-ATL105-PDF-001:1285, REQ-SRC-ATL105-PDF-001:1293, REQ-SRC-ATL105-PDF-001:1298, REQ-SRC-ATL105-PDF-001:1356, REQ-SRC-ATL105-PDF-001:1391, REQ-SRC-ATL105-PDF-001:1402, REQ-SRC-ATL105-PDF-001:1407, REQ-SRC-ATL105-PDF-001:1467|ATL105\|2026-3\|12.1\|100\|85\|segment-type|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG100-R-016|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1128, REQ-SRC-ATL105-PDF-001:1748, REQ-SRC-ATL105-PDF-001:1754, REQ-SRC-ATL105-PDF-001:1755, REQ-SRC-ATL105-PDF-001:1756, REQ-SRC-ATL105-PDF-001:1757, REQ-SRC-ATL105-PDF-001:1758, REQ-SRC-ATL105-PDF-001:1760, REQ-SRC-ATL105-PDF-001:1761, REQ-SRC-ATL105-PDF-001:1764, REQ-SRC-ATL105-PDF-001:1769|ATL105\|2026-3\|12.1\|100\|84\|segment-length|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG100-R-017|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1071, REQ-SRC-ATL105-PDF-001:1275, REQ-SRC-ATL105-PDF-001:1805|ATL105\|2026-3\|12.1\|100\|102\|terminal-identifier|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG100-R-018|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1708|ATL105\|2026-3\|12.1\|100\|78\|prompt-code|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG100-R-019|MISSING||ATL105\|2026-3\|12.1\|100\|86\|sequence-number|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-020|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:460, REQ-SRC-ATL105-PDF-001:464, REQ-SRC-ATL105-PDF-001:494, REQ-SRC-ATL105-PDF-001:499, REQ-SRC-ATL105-PDF-001:546, REQ-SRC-ATL105-PDF-001:550, REQ-SRC-ATL105-PDF-001:553, REQ-SRC-ATL105-PDF-001:573, REQ-SRC-ATL105-PDF-001:578, REQ-SRC-ATL105-PDF-001:1961, REQ-SRC-ATL105-PDF-001:1962|ATL105\|2026-3\|12.1\|100\|121\|partial-approval-indicator|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG100-R-021|MISSING||ATL105\|2026-3\|12.1\|100\|null\|non-trailing-empty-field-separator|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-022|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1087|ATL105\|2026-3\|12.1\|100\|null\|trailing-optional-fields-omitted|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG100-R-023|MISSING||ATL105\|2026-3\|10\|100\|null\|lifecycle-correlation|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-030|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1806, REQ-SRC-ATL105-PDF-001:1807, REQ-SRC-ATL105-PDF-001:1808, REQ-SRC-ATL105-PDF-001:2857|ATL105\|2026-3\|12.1\|100\|102\|terminal-identifier-load-length|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG100-R-031|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:774, REQ-SRC-ATL105-PDF-001:1006, REQ-SRC-ATL105-PDF-001:1809|ATL105\|2026-3\|12.1\|100\|102\|terminal-identifier-components|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG100-R-033|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1520|ATL105\|2026-3\|12.1\|100\|78\|prompt-code-card-type|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG100-R-034|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:457, REQ-SRC-ATL105-PDF-001:513, REQ-SRC-ATL105-PDF-001:642, REQ-SRC-ATL105-PDF-001:709, REQ-SRC-ATL105-PDF-001:755, REQ-SRC-ATL105-PDF-001:2844|ATL105\|2026-3\|10.1.1\|100\|2\|account-number-entry-method|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG100-R-035|MISSING||ATL105\|2026-3\|10.13\|100\|2\|account-number-token-representation|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-037|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:291, REQ-SRC-ATL105-PDF-001:292, REQ-SRC-ATL105-PDF-001:299, REQ-SRC-ATL105-PDF-001:316, REQ-SRC-ATL105-PDF-001:317, REQ-SRC-ATL105-PDF-001:323, REQ-SRC-ATL105-PDF-001:467, REQ-SRC-ATL105-PDF-001:475, REQ-SRC-ATL105-PDF-001:497, REQ-SRC-ATL105-PDF-001:502, REQ-SRC-ATL105-PDF-001:517, REQ-SRC-ATL105-PDF-001:555, REQ-SRC-ATL105-PDF-001:562, REQ-SRC-ATL105-PDF-001:576, REQ-SRC-ATL105-PDF-001:581, REQ-SRC-ATL105-PDF-001:970, REQ-SRC-ATL105-PDF-001:1077, REQ-SRC-ATL105-PDF-001:1773, REQ-SRC-ATL105-PDF-001:1775, REQ-SRC-ATL105-PDF-001:1776, REQ-SRC-ATL105-PDF-001:1777, REQ-SRC-ATL105-PDF-001:1778, REQ-SRC-ATL105-PDF-001:2872|ATL105\|2026-3\|10\|100\|86\|lifecycle-correlation|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG100-R-039|CONFIRMED|REQ-SRC-ATL105-PDF-001:484, REQ-SRC-ATL105-PDF-001:485, REQ-SRC-ATL105-PDF-001:489, REQ-SRC-ATL105-PDF-001:492, REQ-SRC-ATL105-PDF-001:519, REQ-SRC-ATL105-PDF-001:554, REQ-SRC-ATL105-PDF-001:565, REQ-SRC-ATL105-PDF-001:566, REQ-SRC-ATL105-PDF-001:571, REQ-SRC-ATL105-PDF-001:575, REQ-SRC-ATL105-PDF-001:580, REQ-SRC-ATL105-PDF-001:637, REQ-SRC-ATL105-PDF-001:719, REQ-SRC-ATL105-PDF-001:1566, REQ-SRC-ATL105-PDF-001:1963, REQ-SRC-ATL105-PDF-001:1964, REQ-SRC-ATL105-PDF-001:1965, REQ-SRC-ATL105-PDF-001:2257|ATL105\|2026-3\|12.1\|100\|121\|partial-approval-context|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG100-R-040|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:991, REQ-SRC-ATL105-PDF-001:1004, REQ-SRC-ATL105-PDF-001:1012, REQ-SRC-ATL105-PDF-001:1019, REQ-SRC-ATL105-PDF-001:1036, REQ-SRC-ATL105-PDF-001:1642, REQ-SRC-ATL105-PDF-001:1643, REQ-SRC-ATL105-PDF-001:1644, REQ-SRC-ATL105-PDF-001:2181|ATL105\|2026-3\|12.1\|100\|44\|information-byte|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG100-R-041|CONFIRMED|REQ-SRC-ATL105-PDF-001:511, REQ-SRC-ATL105-PDF-001:512, REQ-SRC-ATL105-PDF-001:649, REQ-SRC-ATL105-PDF-001:650, REQ-SRC-ATL105-PDF-001:1526, REQ-SRC-ATL105-PDF-001:1532, REQ-SRC-ATL105-PDF-001:1546, REQ-SRC-ATL105-PDF-001:1548, REQ-SRC-ATL105-PDF-001:1550, REQ-SRC-ATL105-PDF-001:1551, REQ-SRC-ATL105-PDF-001:1552, REQ-SRC-ATL105-PDF-001:1968, REQ-SRC-ATL105-PDF-001:2860|ATL105\|2026-3\|12.1\|100\|2\|account-number|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG100-R-042|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:253|ATL105\|2026-3\|12.1\|100\|12\|card-discretionary-block-data|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG100-R-043|CONFIRMED|REQ-SRC-ATL105-PDF-001:352, REQ-SRC-ATL105-PDF-001:354, REQ-SRC-ATL105-PDF-001:359, REQ-SRC-ATL105-PDF-001:370, REQ-SRC-ATL105-PDF-001:371, REQ-SRC-ATL105-PDF-001:375, REQ-SRC-ATL105-PDF-001:377, REQ-SRC-ATL105-PDF-001:390, REQ-SRC-ATL105-PDF-001:541, REQ-SRC-ATL105-PDF-001:645, REQ-SRC-ATL105-PDF-001:669, REQ-SRC-ATL105-PDF-001:910, REQ-SRC-ATL105-PDF-001:1623, REQ-SRC-ATL105-PDF-001:1624, REQ-SRC-ATL105-PDF-001:1625, REQ-SRC-ATL105-PDF-001:1626|ATL105\|2026-3\|12.1\|100\|33\|encrypted-pin-block-data|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG100-R-044|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1721, REQ-SRC-ATL105-PDF-001:1722, REQ-SRC-ATL105-PDF-001:1724, REQ-SRC-ATL105-PDF-001:2849|ATL105\|2026-3\|12.1\|100\|79\|pump-lane-number|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG100-R-045|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:392, REQ-SRC-ATL105-PDF-001:1634, REQ-SRC-ATL105-PDF-001:1635, REQ-SRC-ATL105-PDF-001:1636, REQ-SRC-ATL105-PDF-001:1638|ATL105\|2026-3\|12.1\|100\|41\|fuel-purchase-amount|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG100-R-046|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1667, REQ-SRC-ATL105-PDF-001:1668, REQ-SRC-ATL105-PDF-001:1669, REQ-SRC-ATL105-PDF-001:1670, REQ-SRC-ATL105-PDF-001:1673|ATL105\|2026-3\|12.1\|100\|58\|nonfuel-amount|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG100-R-047|CONFIRMED|REQ-SRC-ATL105-PDF-001:1110, REQ-SRC-ATL105-PDF-001:1697, REQ-SRC-ATL105-PDF-001:1795, REQ-SRC-ATL105-PDF-001:1798, REQ-SRC-ATL105-PDF-001:1799|ATL105\|2026-3\|12.1\|100\|99\|tax-amount|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG100-R-048|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:552, REQ-SRC-ATL105-PDF-001:621, REQ-SRC-ATL105-PDF-001:1597, REQ-SRC-ATL105-PDF-001:1598|ATL105\|2026-3\|12.1\|100\|17\|cash-amount|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG100-R-049|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:382, REQ-SRC-ATL105-PDF-001:403, REQ-SRC-ATL105-PDF-001:474, REQ-SRC-ATL105-PDF-001:520, REQ-SRC-ATL105-PDF-001:561, REQ-SRC-ATL105-PDF-001:648, REQ-SRC-ATL105-PDF-001:651, REQ-SRC-ATL105-PDF-001:668, REQ-SRC-ATL105-PDF-001:720, REQ-SRC-ATL105-PDF-001:752, REQ-SRC-ATL105-PDF-001:790, REQ-SRC-ATL105-PDF-001:791, REQ-SRC-ATL105-PDF-001:1558, REQ-SRC-ATL105-PDF-001:1559, REQ-SRC-ATL105-PDF-001:1560, REQ-SRC-ATL105-PDF-001:1561, REQ-SRC-ATL105-PDF-001:1562, REQ-SRC-ATL105-PDF-001:2871|ATL105\|2026-3\|12.1\|100\|5\|approval-number|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG100-R-050|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1651, REQ-SRC-ATL105-PDF-001:1652|ATL105\|2026-3\|12.1\|100\|49\|local-date-time|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG100-R-051|MISSING||ATL105\|2026-3\|11.1.1\|103\|null\|ebt-data-required|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-052|MISSING||ATL105\|2026-3\|11.1.1\|104\|null\|purchase-card-data-required|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-053|MISSING||ATL105\|2026-3\|null\|111\|null\|variable-information-required|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-054|MISSING||ATL105\|2026-3\|null\|123\|null\|nfc-tokenization-required|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-055|MISSING||ATL105\|2026-3\|null\|135\|null\|moneris-authorizer-required|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-056|MISSING||ATL105\|2026-3\|10.5.5\|100\|78\|ewic-authorization|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-057|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1236|ATL105\|2026-3\|10.5.5\|100\|78\|ewic-authorization-cancellation|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG100-R-058|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1349|ATL105\|2026-3\|10.5.5\|100\|78\|ewic-balance-inquiry|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG100-R-059|MISSING||ATL105\|2026-3\|10.5.5\|100\|78\|ewic-purchase-completion|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-060|CONFIRMED|REQ-SRC-ATL105-PDF-001:1717, REQ-SRC-ATL105-PDF-001:1718|ATL105\|2026-3\|10.5.5\|100\|78\|ewic-purchase-reversal|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG100-R-061|MISSING||ATL105\|2026-3\|12.4\|103\|null\|ewic-segment-103-data|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-062|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:691, REQ-SRC-ATL105-PDF-001:806, REQ-SRC-ATL105-PDF-001:807, REQ-SRC-ATL105-PDF-001:809, REQ-SRC-ATL105-PDF-001:1140, REQ-SRC-ATL105-PDF-001:1198, REQ-SRC-ATL105-PDF-001:1229, REQ-SRC-ATL105-PDF-001:1514, REQ-SRC-ATL105-PDF-001:1515, REQ-SRC-ATL105-PDF-001:1517, REQ-SRC-ATL105-PDF-001:1518, REQ-SRC-ATL105-PDF-001:1519, REQ-SRC-ATL105-PDF-001:1521, REQ-SRC-ATL105-PDF-001:1522, REQ-SRC-ATL105-PDF-001:1744, REQ-SRC-ATL105-PDF-001:1969, REQ-SRC-ATL105-PDF-001:2043, REQ-SRC-ATL105-PDF-001:2045, REQ-SRC-ATL105-PDF-001:2048, REQ-SRC-ATL105-PDF-001:2049, REQ-SRC-ATL105-PDF-001:2068, REQ-SRC-ATL105-PDF-001:2069, REQ-SRC-ATL105-PDF-001:2107|ATL105\|2026-3\|10.5.5\|100\|78\|ewic-voucher-clear|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG100-R-063|MISSING||ATL105\|2026-3\|13.2\|FINANCIAL-RESPONSE\|83\|response-code-0-4|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-064|MISSING||ATL105\|2026-3\|13.2\|FINANCIAL-RESPONSE\|83\|response-code-2-3|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-065|MISSING||ATL105\|2026-3\|13.2\|FINANCIAL-RESPONSE\|83\|response-code-F|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG100-R-066|MISSING||ATL105\|2026-3\|13.2\|FINANCIAL-RESPONSE\|83\|response-code-1-S|No prior AI requirement was mapped to this independent Test Solution rule.|
