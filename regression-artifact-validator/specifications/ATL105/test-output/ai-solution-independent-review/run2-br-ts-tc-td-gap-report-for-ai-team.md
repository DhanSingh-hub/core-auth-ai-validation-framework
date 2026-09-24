# Run2 BR -> TS -> TC -> TD Link Gap Report

**Audience:** AI Solution team
**Source:** `traceability_matrix_full.json` (Run2, 2026-09-23), using the file's own `trace_status` field
**Purpose:** Identify every business requirement where the AI-generated chain does not reach test data, so scenario/test-case/test-data generation can be re-run for the affected items.

## 1. Overall link-gap summary

Total business requirements: **6473**

| trace_status | Count | % of total | Meaning |
|---|---:|---:|---|
|FULLY_TRACED|1964|30.3%|Concrete test data resolved on disk; BR->TS->TC->TD fully linked.|
|REQUIREMENT_ONLY|1586|24.5%|No scenario generated from this requirement (BR -> TS gap).|
|SCENARIO_ONLY|2462|38.0%|Scenario(s) exist but no test case generated (TS -> TC gap).|
|TEST_CASE_NO_DATA|461|7.1%|Test case(s) exist but no test data file resolves on disk (TC -> TD gap).|

## 2. Gap breakdown by segment

Segments ranked by total non-fully-traced requirements. Only segments with at least one gap are shown.

| Segment | REQUIREMENT_ONLY (no TS) | SCENARIO_ONLY (no TC) | TEST_CASE_NO_DATA (no TD) | Other gaps | Total gaps |
|---|---:|---:|---:|---:|---:|
|UNMAPPED|627|1663|0|0|2290|
|ENT-SEG-112|87|213|1|0|301|
|ENT-SEG-100|126|15|134|0|275|
|ENT-SEG-111|137|45|23|0|205|
|ENT-SEG-105|109|19|46|0|174|
|ENT-SEG-102|95|9|22|0|126|
|ENT-SEG-DL1|62|41|3|0|106|
|ENT-SEG-108|36|2|37|0|75|
|ENT-SEG-118|28|39|7|0|74|
|ENT-SEG-123|34|4|29|0|67|
|ENT-SEG-DL2|21|37|0|0|58|
|ENT-SEG-143|29|4|23|0|56|
|ENT-SEG-DL4|14|33|0|0|47|
|ENT-SEG-DL3|13|33|0|0|46|
|ENT-SEG-139|8|31|1|0|40|
|ENT-SEG-150|12|27|1|0|40|
|ENT-SEG-109|5|29|2|0|36|
|ENT-SEG-110|18|1|17|0|36|
|ENT-SEG-140|10|25|1|0|36|
|ENT-SEG-DL8|13|22|0|0|35|
|ENT-SEG-101|14|6|12|0|32|
|ENT-SEG-103|13|2|15|0|30|
|ENT-SEG-134|14|4|12|0|30|
|ENT-SEG-130|4|4|21|0|29|
|ENT-SEG-DL5|3|20|0|0|23|
|ENT-SEG-142|2|16|1|0|19|
|ENT-SEG-141|1|16|1|0|18|
|ENT-SEG-113|2|2|13|0|17|
|ENT-SEG-148|2|13|1|0|16|
|ENT-SEG-104|6|0|9|0|15|
|ENT-SEG-DL6|2|13|0|0|15|
|ENT-SEG-132|5|6|3|0|14|
|ENT-SEG-116|0|12|0|0|12|
|ENT-SEG-DL7|3|9|0|0|12|
|ENT-SEG-136|6|3|1|0|10|
|ENT-SEG-145|1|2|7|0|10|
|ENT-SEG-156|3|2|4|0|9|
|ENT-SEG-119|4|3|1|0|8|
|ENT-SEG-135|3|2|3|0|8|
|ENT-SEG-149|2|5|1|0|8|
|ENT-SEG-RPD|0|7|0|0|7|
|ENT-SEG-115|3|2|1|0|6|
|ENT-SEG-131|1|4|1|0|6|
|ENT-SEG-146|0|4|1|0|5|
|ENT-SEG-157|4|0|1|0|5|
|ENT-SEG-152|0|3|1|0|4|
|ENT-SEG-155|1|3|0|0|4|
|ENT-SEG-114|1|1|1|0|3|
|ENT-SEG-120|0|2|1|0|3|
|ENT-SEG-151|0|2|1|0|3|
|ENT-SEG-153|1|1|1|0|3|
|ENT-SEG-106|0|1|0|0|1|
|ENT-SEG-107|1|0|0|0|1|

## 3. Sample requirements per gap type

Up to 10 examples per status. Full list (all 4509 gap rows) is in the companion CSV: `run2-br-ts-tc-td-gap-details.csv`.

### REQUIREMENT_ONLY (1586 total)

| Requirement ID | Segment | Page | Scenarios | Test Cases | Test Data | Gap reason |
|---|---|---:|---:|---:|---:|---|
|REQ-SRC-ATL105-PDF-001:001|ENT-SEG-DL2|355|0|0|0|No scenario has been generated from this requirement. Run/expand Scenario Generation for this requirement.|
|REQ-SRC-ATL105-PDF-001:003|ENT-SEG-DL1|360|0|0|0|No scenario has been generated from this requirement. Run/expand Scenario Generation for this requirement.|
|REQ-SRC-ATL105-PDF-001:004|ENT-SEG-DL1|360|0|0|0|No scenario has been generated from this requirement. Run/expand Scenario Generation for this requirement.|
|REQ-SRC-ATL105-PDF-001:006|UNMAPPED|361|0|0|0|No scenario has been generated from this requirement. Run/expand Scenario Generation for this requirement.|
|REQ-SRC-ATL105-PDF-001:007|UNMAPPED|362|0|0|0|No scenario has been generated from this requirement. Run/expand Scenario Generation for this requirement.|
|REQ-SRC-ATL105-PDF-001:008|UNMAPPED|362|0|0|0|No scenario has been generated from this requirement. Run/expand Scenario Generation for this requirement.|
|REQ-SRC-ATL105-PDF-001:009|UNMAPPED|362|0|0|0|No scenario has been generated from this requirement. Run/expand Scenario Generation for this requirement.|
|REQ-SRC-ATL105-PDF-001:015|UNMAPPED|367|0|0|0|No scenario has been generated from this requirement. Run/expand Scenario Generation for this requirement.|
|REQ-SRC-ATL105-PDF-001:016|UNMAPPED|367|0|0|0|No scenario has been generated from this requirement. Run/expand Scenario Generation for this requirement.|
|REQ-SRC-ATL105-PDF-001:023|ENT-SEG-DL3|369|0|0|0|No scenario has been generated from this requirement. Run/expand Scenario Generation for this requirement.|

### SCENARIO_ONLY (2462 total)

| Requirement ID | Segment | Page | Scenarios | Test Cases | Test Data | Gap reason |
|---|---|---:|---:|---:|---:|---|
|REQ-SRC-ATL105-PDF-001:040|ENT-SEG-109|376|4|0|0|Scenario(s) exist but no test case has been generated yet. Run Test Case Generation.|
|REQ-SRC-ATL105-PDF-001:041|ENT-SEG-109|377|4|0|0|Scenario(s) exist but no test case has been generated yet. Run Test Case Generation.|
|REQ-SRC-ATL105-PDF-001:056|ENT-SEG-109|382|4|0|0|Scenario(s) exist but no test case has been generated yet. Run Test Case Generation.|
|REQ-SRC-ATL105-PDF-001:1005|UNMAPPED|180|1|0|0|Scenario(s) exist but no test case has been generated yet. Run Test Case Generation.|
|REQ-SRC-ATL105-PDF-001:1006|UNMAPPED|182|1|0|0|Scenario(s) exist but no test case has been generated yet. Run Test Case Generation.|
|REQ-SRC-ATL105-PDF-001:1007|ENT-SEG-109|182|1|0|0|Scenario(s) exist but no test case has been generated yet. Run Test Case Generation.|
|REQ-SRC-ATL105-PDF-001:1008|UNMAPPED|182|1|0|0|Scenario(s) exist but no test case has been generated yet. Run Test Case Generation.|
|REQ-SRC-ATL105-PDF-001:1009|ENT-SEG-109|183|1|0|0|Scenario(s) exist but no test case has been generated yet. Run Test Case Generation.|
|REQ-SRC-ATL105-PDF-001:1010|ENT-SEG-109|183|1|0|0|Scenario(s) exist but no test case has been generated yet. Run Test Case Generation.|
|REQ-SRC-ATL105-PDF-001:1011|ENT-SEG-109|183|1|0|0|Scenario(s) exist but no test case has been generated yet. Run Test Case Generation.|

### TEST_CASE_NO_DATA (461 total)

| Requirement ID | Segment | Page | Scenarios | Test Cases | Test Data | Gap reason |
|---|---|---:|---:|---:|---:|---|
|REQ-SRC-ATL105-PDF-001:002|ENT-SEG-100|356|6|6|0|Test cases exist but no test data file resolves on disk. Re-run Test Case Generation to regenerate test data files.|
|REQ-SRC-ATL105-PDF-001:005|ENT-SEG-100|361|24|24|0|Test cases exist but no test data file resolves on disk. Re-run Test Case Generation to regenerate test data files.|
|REQ-SRC-ATL105-PDF-001:010|ENT-SEG-109|363|16|4|0|Test cases exist but no test data file resolves on disk. Re-run Test Case Generation to regenerate test data files.|
|REQ-SRC-ATL105-PDF-001:012|ENT-SEG-100|364|6|6|0|Test cases exist but no test data file resolves on disk. Re-run Test Case Generation to regenerate test data files.|
|REQ-SRC-ATL105-PDF-001:013|ENT-SEG-105|365|16|4|0|Test cases exist but no test data file resolves on disk. Re-run Test Case Generation to regenerate test data files.|
|REQ-SRC-ATL105-PDF-001:017|ENT-SEG-105|367|4|1|0|Test cases exist but no test data file resolves on disk. Re-run Test Case Generation to regenerate test data files.|
|REQ-SRC-ATL105-PDF-001:018|ENT-SEG-105|368|16|4|0|Test cases exist but no test data file resolves on disk. Re-run Test Case Generation to regenerate test data files.|
|REQ-SRC-ATL105-PDF-001:019|ENT-SEG-100|368|24|24|0|Test cases exist but no test data file resolves on disk. Re-run Test Case Generation to regenerate test data files.|
|REQ-SRC-ATL105-PDF-001:020|ENT-SEG-103|368|12|8|0|Test cases exist but no test data file resolves on disk. Re-run Test Case Generation to regenerate test data files.|
|REQ-SRC-ATL105-PDF-001:021|ENT-SEG-105|369|16|4|0|Test cases exist but no test data file resolves on disk. Re-run Test Case Generation to regenerate test data files.|

## 4. Orphan scenarios (no requirement link at all)

Total orphan scenarios: **102**. These scenarios exist with no traceable parent requirement. Full list: `run2-orphan-scenarios.csv`.

| Scenario ID | Reason |
|---|---|
|SC-11156|UNRESOLVED_REQUIREMENT|
|SC-11157|UNRESOLVED_REQUIREMENT|
|SC-11158|UNRESOLVED_REQUIREMENT|
|SC-11159|UNRESOLVED_REQUIREMENT|
|SC-11160|UNRESOLVED_REQUIREMENT|
|SC-11161|UNRESOLVED_REQUIREMENT|
|SC-11167|UNRESOLVED_REQUIREMENT|
|SC-11168|UNRESOLVED_REQUIREMENT|
|SC-11169|UNRESOLVED_REQUIREMENT|
|SC-11170|UNRESOLVED_REQUIREMENT|

## 5. Requested actions for the AI team

1. **REQUIREMENT_ONLY** requirements: run/expand Scenario Generation so every requirement has at least one scenario.
2. **SCENARIO_ONLY** requirements: run Test Case Generation for the listed scenarios.
3. **TEST_CASE_NO_DATA** requirements: re-run Test Case Generation so `qe_shaped_test_data/*.json` files are produced and resolve on disk for every listed test case.
4. **Orphan scenarios**: attach a `requirement_id` to each orphan scenario, or confirm and document why no requirement applies.
5. Re-publish an updated `traceability_matrix_full.json` referencing the same `deliveryId` so the Test Solution can re-run this gap analysis and confirm closure.

## 6. Companion files

- `run2-br-ts-tc-td-gap-details.csv` — one row per non-fully-traced requirement
- `run2-orphan-scenarios.csv` — one row per orphan scenario
- `run2-traceability-gap-analysis.json` — independently recomputed link-gap counts (cross-check)
