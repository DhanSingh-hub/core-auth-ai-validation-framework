# Segment DL3 — Manual Validation Test Cases

Format per QE feedback Def_SepRun06: functional coverage only (happy path, BDD scenario, contract, negative, boundary); no NFR rows. Test Case IDs are sequential per feature; the AI test-case ID is kept in TC Name. Priority maps producer P1→P0, P2→P1, P3/P4→P2.

## Electronic Mail

| Test Case ID | TC Name | Description | Test Steps | Expected Result | Actual Result | Priority | Feature Name (Endpoint) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC_01 | TC-013209 — Field_Constraint — Electronic Mail — In Date and Time Data Segment (Data Segment No. DL3), field 6 (Passwor | In Date and Time Data Segment (Data Segment No. DL3), field 6 (Password) is required and must be present. Scenario SC-8994: Field Constraint — ENT-FIELD-DL3-6: In Date and Time Data Segment (Data Segment No. DL3), field 6 (Password) is required and must be present. | 1. Load test data `TC-013209.json` (Electronic Mail)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail | Not Executed | P2 | Segment DL3 — Electronic Mail (ATL105 request) |

## Electronic Mail Request

| Test Case ID | TC Name | Description | Test Steps | Expected Result | Actual Result | Priority | Feature Name (Endpoint) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC_01 | TC-013210 — Field_Constraint — Electronic Mail Request — In Date and Time Data Segment (Data Segment No. DL3), field 6 (Passwor | In Date and Time Data Segment (Data Segment No. DL3), field 6 (Password) is required and must be present. Scenario SC-8994: Field Constraint — ENT-FIELD-DL3-6: In Date and Time Data Segment (Data Segment No. DL3), field 6 (Password) is required and must be present. | 1. Load test data `TC-013210.json` (Electronic Mail Request)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail | Not Executed | P2 | Segment DL3 — Electronic Mail Request (ATL105 request) |

## Financial Transaction

| Test Case ID | TC Name | Description | Test Steps | Expected Result | Actual Result | Priority | Feature Name (Endpoint) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC_01 | TC-020916 — Business_Rule — Financial Transaction — Transaction date prints on all credit card receipts. | Transaction date prints on all credit card receipts. Scenario SC-5933: Business Rule — BR-90-5: Transaction date prints on all credit card receipts. | 1. Load test data `TC-020916.json` (Financial Transaction)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | NOT DEFINED — producer expected response code is null (oracle tier FLAG); QEF-009 blocks pass/fail | Not Executed | P2 | Segment DL3 — Financial Transaction (ATL105 request) |
| TC_02 | TC-020917 — Business_Rule — Financial Transaction — Transaction time prints on all credit card receipts. | Transaction time prints on all credit card receipts. Scenario SC-5934: Business Rule — BR-90-6: Transaction time prints on all credit card receipts. | 1. Load test data `TC-020917.json` (Financial Transaction)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | NOT DEFINED — producer expected response code is null (oracle tier FLAG); QEF-009 blocks pass/fail | Not Executed | P2 | Segment DL3 — Financial Transaction (ATL105 request) |

## Financial Transaction Request

| Test Case ID | TC Name | Description | Test Steps | Expected Result | Actual Result | Priority | Feature Name (Endpoint) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC_01 | TC-013211 — Field_Constraint — Financial Transaction Request — In Date and Time Data Segment (Data Segment No. DL3), field 6 (Passwor | In Date and Time Data Segment (Data Segment No. DL3), field 6 (Password) is required and must be present. Scenario SC-8994: Field Constraint — ENT-FIELD-DL3-6: In Date and Time Data Segment (Data Segment No. DL3), field 6 (Password) is required and must be present. | 1. Load test data `TC-013211.json` (Financial Transaction Request)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | Response code 0 (oracle tier ASSERT) | Not Executed | P2 | Segment DL3 — Financial Transaction Request (ATL105 request) |

## Financial Transactions

| Test Case ID | TC Name | Description | Test Steps | Expected Result | Actual Result | Priority | Feature Name (Endpoint) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC_01 | TC-013212 — Field_Constraint — Financial Transactions — In Date and Time Data Segment (Data Segment No. DL3), field 6 (Passwor | In Date and Time Data Segment (Data Segment No. DL3), field 6 (Password) is required and must be present. Scenario SC-8994: Field Constraint — ENT-FIELD-DL3-6: In Date and Time Data Segment (Data Segment No. DL3), field 6 (Password) is required and must be present. | 1. Load test data `TC-013212.json` (Financial Transactions)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | NOT DEFINED — producer expected response code is null (oracle tier FLAG); QEF-009 blocks pass/fail | Not Executed | P2 | Segment DL3 — Financial Transactions (ATL105 request) |

## Mail Request

| Test Case ID | TC Name | Description | Test Steps | Expected Result | Actual Result | Priority | Feature Name (Endpoint) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC_01 | TC-013213 — Field_Constraint — Mail Request — In Date and Time Data Segment (Data Segment No. DL3), field 6 (Passwor | In Date and Time Data Segment (Data Segment No. DL3), field 6 (Password) is required and must be present. Scenario SC-8994: Field Constraint — ENT-FIELD-DL3-6: In Date and Time Data Segment (Data Segment No. DL3), field 6 (Password) is required and must be present. | 1. Load test data `TC-013213.json` (Mail Request)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail | Not Executed | P2 | Segment DL3 — Mail Request (ATL105 request) |

## Totals Request

| Test Case ID | TC Name | Description | Test Steps | Expected Result | Actual Result | Priority | Feature Name (Endpoint) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC_01 | TC-005440 — Business_Rule — Totals Request — If merchant does not request read and cut totals, BUYPASS automaticall | If merchant does not request read and cut totals, BUYPASS automatically closes business day at designated cut time. Scenario SC-5842: Business Rule — BR-77-1: If merchant does not request read and cut totals, BUYPASS automatically closes business day at designated cut time. | 1. Load test data `TC-005440.json` (Totals Request)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail | Not Executed | P2 | Segment DL3 — Totals Request (ATL105 request) |
