# Segment DL4 — Manual Validation Test Cases

Format per QE feedback Def_SepRun06: functional coverage only (happy path, BDD scenario, contract, negative, boundary); no NFR rows. Test Case IDs are sequential per feature; the AI test-case ID is kept in TC Name. Priority maps producer P1→P0, P2→P1, P3/P4→P2.

## Financial Transaction Request

| Test Case ID | TC Name | Description | Test Steps | Expected Result | Actual Result | Priority | Feature Name (Endpoint) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC_01 | TC-009256 — Business_Rule — Financial Transaction Request — Software Load Type is one alphanumeric character: F (Full) or P (Parti | Software Load Type is one alphanumeric character: F (Full) or P (Partial). Scenario SC-7323: Business Rule — BR-406-2: Software Load Type is one alphanumeric character: F (Full) or P (Partial). | 1. Load test data `TC-009256.json` (Financial Transaction Request)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | Response code 0 (oracle tier ASSERT) | Not Executed | P2 | Segment DL4 — Financial Transaction Request (ATL105 request) |

## Proprietary Data Load Request

| Test Case ID | TC Name | Description | Test Steps | Expected Result | Actual Result | Priority | Feature Name (Endpoint) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC_01 | TC-020974 — Business_Rule — Proprietary Data Load Request — Downloads must be requestable via manual user intervention and automat | Downloads must be requestable via manual user intervention and automatically by the device based on host response. Scenario SC-6439: Business Rule — BR-186-3: Downloads must be requestable via manual user intervention and automatically by the device based on host response. | 1. Load test data `TC-020974.json` (Proprietary Data Load Request)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail | Not Executed | P2 | Segment DL4 — Proprietary Data Load Request (ATL105 request) |
