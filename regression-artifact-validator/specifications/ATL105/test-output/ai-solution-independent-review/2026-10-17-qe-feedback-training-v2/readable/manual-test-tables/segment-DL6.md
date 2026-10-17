# Segment DL6 — Manual Validation Test Cases

Format per QE feedback Def_SepRun06: functional coverage only (happy path, BDD scenario, contract, negative, boundary); no NFR rows. Test Case IDs are sequential per feature; the AI test-case ID is kept in TC Name. Priority maps producer P1→P0, P2→P1, P3/P4→P2.

## EMV Financial Transaction Request

| Test Case ID | TC Name | Description | Test Steps | Expected Result | Actual Result | Priority | Feature Name (Endpoint) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC_01 | TC-008026 — Business_Rule — EMV Financial Transaction Request — Table ID fixed value 04 for Start Time of Charge sub-element. | Table ID fixed value 04 for Start Time of Charge sub-element. Scenario SC-6914: Business Rule — BR-315-6: Table ID fixed value 04 for Start Time of Charge sub-element. | 1. Load test data `TC-008026.json` (EMV Financial Transaction Request)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | Response code 0 (oracle tier ASSERT) | Not Executed | P2 | Segment DL6 — EMV Financial Transaction Request (ATL105 request) |

## EMV Request Transaction

| Test Case ID | TC Name | Description | Test Steps | Expected Result | Actual Result | Priority | Feature Name (Endpoint) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC_01 | TC-008027 — Business_Rule — EMV Request Transaction — Table ID fixed value 04 for Start Time of Charge sub-element. | Table ID fixed value 04 for Start Time of Charge sub-element. Scenario SC-6914: Business Rule — BR-315-6: Table ID fixed value 04 for Start Time of Charge sub-element. | 1. Load test data `TC-008027.json` (EMV Request Transaction)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | Response code 0 (oracle tier ASSERT) | Not Executed | P2 | Segment DL6 — EMV Request Transaction (ATL105 request) |

## Financial Transaction Request

| Test Case ID | TC Name | Description | Test Steps | Expected Result | Actual Result | Priority | Feature Name (Endpoint) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC_01 | TC-008028 — Business_Rule — Financial Transaction Request — Table ID fixed value 04 for Start Time of Charge sub-element. | Table ID fixed value 04 for Start Time of Charge sub-element. Scenario SC-6914: Business Rule — BR-315-6: Table ID fixed value 04 for Start Time of Charge sub-element. | 1. Load test data `TC-008028.json` (Financial Transaction Request)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | Response code 0 (oracle tier ASSERT) | Not Executed | P2 | Segment DL6 — Financial Transaction Request (ATL105 request) |

## Proprietary Data Load Request

| Test Case ID | TC Name | Description | Test Steps | Expected Result | Actual Result | Priority | Feature Name (Endpoint) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC_01 | TC-010125 — Business_Rule — Proprietary Data Load Request — Element 165 (Start/End Date) is used with Element 166 (Start/End Time) | Element 165 (Start/End Date) is used with Element 166 (Start/End Time). Scenario SC-7601: Business Rule — BR-460-4: Element 165 (Start/End Date) is used with Element 166 (Start/End Time). | 1. Load test data `TC-010125.json` (Proprietary Data Load Request)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail | Not Executed | P2 | Segment DL6 — Proprietary Data Load Request (ATL105 request) |
