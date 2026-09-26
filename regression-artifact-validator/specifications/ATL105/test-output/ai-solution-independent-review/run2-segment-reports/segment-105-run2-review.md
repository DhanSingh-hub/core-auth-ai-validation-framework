# Segment 105 Run2 AI vs Test Solution Review

- Composite delivery: `2026-09-23/Run1+Run2`
- Decision: **REVIEW_REQUIRED**
- Version resolution: `ATL105-RUN2-SPEC-VERSION-RESOLUTION-001`
- Confirmed BR coverage: **17.6%**
- Full-chain coverage: **0.0%**

## Coverage

| Denominator | Confirmed | Review required | Missing | Unmatched AI | Full chain |
|---:|---:|---:|---:|---:|---:|
|17|3|6|8|238|0|

## Artifact chain

| AI BRs | Scenarios | Test cases | Test data |
|---:|---:|---:|---:|
|251|1001|409|183|

## Payload validation

| Validator | Linked | Applicable | Valid | Invalid | Unreadable |
|---|---:|---:|---:|---:|---:|
|not implemented|183|0|0|0|0|

## AI to Test Solution crosswalk

| Test rule | Status | AI requirement IDs | Canonical anchor | Reason |
|---|---|---|---|---|
|SEG105-R-001|CONFIRMED|REQ-SRC-ATL105-PDF-001:987|ATL105\|2026-3\|Totals Request\|105\|85\|segment-type|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG105-R-002|CONFIRMED|REQ-SRC-ATL105-PDF-001:1181, REQ-SRC-ATL105-PDF-001:1183, REQ-SRC-ATL105-PDF-001:1184, REQ-SRC-ATL105-PDF-001:1185|ATL105\|2026-3\|Totals Request\|105\|84\|segment-length|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG105-R-003|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1829|ATL105\|2026-3\|Totals Request\|105\|44\|information-byte|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG105-R-004|MISSING||ATL105\|2026-3\|Totals Request\|105\|102\|terminal-identifier|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG105-R-005|MISSING||ATL105\|2026-3\|Totals Request\|105\|78\|prompt-code|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG105-R-006|MISSING||ATL105\|2026-3\|Totals Request\|105\|32\|employee-number|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG105-R-007|MISSING||ATL105\|2026-3\|Totals Request\|105\|65\|password|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG105-R-008|CONFIRMED|REQ-SRC-ATL105-PDF-001:413|ATL105\|2026-3\|Totals Request\|105\|105\|totals-date|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|SEG105-R-009|MISSING||ATL105\|2026-3\|Totals Request\|105\|105\|totals-date-activity-window|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG105-R-010|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1182|ATL105\|2026-3\|Totals Request\|105\|43\|hardware-version|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG105-R-011|MISSING||ATL105\|2026-3\|Totals Request\|105\|96\|software-version|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG105-R-012|MISSING||ATL105\|2026-3\|Totals Request\|105\|39\|firmware-version|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG105-R-013|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:1757|ATL105\|2026-3\|Totals Request\|105\|86\|sequence-number|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG105-R-014|MISSING||ATL105\|2026-3\|Totals Request\|105\|20\|currency-code|No prior AI requirement was mapped to this independent Test Solution rule.|
|SEG105-R-015|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:705|ATL105\|2026-3\|Totals Request\|105\|null\|totals-aggregate-fields|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG105-R-016|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:407, REQ-SRC-ATL105-PDF-001:1734|ATL105\|2026-3\|Totals Request\|105\|86\|request-response-correlation|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
|SEG105-R-017|REVIEW_REQUIRED|REQ-SRC-ATL105-PDF-001:990|ATL105\|2026-3\|Totals Request\|119\|null\|totals-load-alternative|Prior comparison produced candidates but no evidence-supported confirmation for this rule.|
