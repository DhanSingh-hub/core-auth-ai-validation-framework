# Segment DL7 — Manual Validation Test Cases

Format per QE feedback Def_SepRun06: functional coverage only (happy path, BDD scenario, contract, negative, boundary); no NFR rows. Test Case IDs are sequential per feature; the AI test-case ID is kept in TC Name. Priority maps producer P1→P0, P2→P1, P3/P4→P2.

## ECA/TeleCheck Service Transaction

| Test Case ID | TC Name | Description | Test Steps | Expected Result | Actual Result | Priority | Feature Name (Endpoint) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC_01 | TC-013214 — Field_Constraint — ECA/TeleCheck Service Transaction — In Supplemental Terminal Data Segment (Data Segment No. DL7), field 2  | In Supplemental Terminal Data Segment (Data Segment No. DL7), field 2 (Segment Length Indicator) is required and must be present. Scenario SC-9018: Field Constraint — ENT-FIELD-DL7-2: In Supplemental Terminal Data Segment (Data Segment No. DL7), field 2 (Segment Length Indicator) is required and must be | 1. Load test data `TC-013214.json` (ECA/TeleCheck Service Transaction)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | Response code 0 (oracle tier ASSERT) | Not Executed | P2 | Segment DL7 — ECA/TeleCheck Service Transaction (ATL105 request) |

## ECA/TeleCheck Service Transaction Request

| Test Case ID | TC Name | Description | Test Steps | Expected Result | Actual Result | Priority | Feature Name (Endpoint) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC_01 | TC-013215 — Field_Constraint — ECA/TeleCheck Service Transaction Request — In Supplemental Terminal Data Segment (Data Segment No. DL7), field 2  | In Supplemental Terminal Data Segment (Data Segment No. DL7), field 2 (Segment Length Indicator) is required and must be present. Scenario SC-9018: Field Constraint — ENT-FIELD-DL7-2: In Supplemental Terminal Data Segment (Data Segment No. DL7), field 2 (Segment Length Indicator) is required and must be | 1. Load test data `TC-013215.json` (ECA/TeleCheck Service Transaction Request)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | Response code 0 (oracle tier ASSERT) | Not Executed | P2 | Segment DL7 — ECA/TeleCheck Service Transaction Request (ATL105 request) |

## EMV Financial Transaction Request

| Test Case ID | TC Name | Description | Test Steps | Expected Result | Actual Result | Priority | Feature Name (Endpoint) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC_01 | TC-013216 — Field_Constraint — EMV Financial Transaction Request — In Supplemental Terminal Data Segment (Data Segment No. DL7), field 2  | In Supplemental Terminal Data Segment (Data Segment No. DL7), field 2 (Segment Length Indicator) is required and must be present. Scenario SC-9018: Field Constraint — ENT-FIELD-DL7-2: In Supplemental Terminal Data Segment (Data Segment No. DL7), field 2 (Segment Length Indicator) is required and must be | 1. Load test data `TC-013216.json` (EMV Financial Transaction Request)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | Response code 0 (oracle tier ASSERT) | Not Executed | P2 | Segment DL7 — EMV Financial Transaction Request (ATL105 request) |

## EMV Request Transaction

| Test Case ID | TC Name | Description | Test Steps | Expected Result | Actual Result | Priority | Feature Name (Endpoint) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC_01 | TC-013217 — Field_Constraint — EMV Request Transaction — In Supplemental Terminal Data Segment (Data Segment No. DL7), field 2  | In Supplemental Terminal Data Segment (Data Segment No. DL7), field 2 (Segment Length Indicator) is required and must be present. Scenario SC-9018: Field Constraint — ENT-FIELD-DL7-2: In Supplemental Terminal Data Segment (Data Segment No. DL7), field 2 (Segment Length Indicator) is required and must be | 1. Load test data `TC-013217.json` (EMV Request Transaction)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | Response code 0 (oracle tier ASSERT) | Not Executed | P2 | Segment DL7 — EMV Request Transaction (ATL105 request) |

## Electronic Mail

| Test Case ID | TC Name | Description | Test Steps | Expected Result | Actual Result | Priority | Feature Name (Endpoint) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC_01 | TC-013218 — Field_Constraint — Electronic Mail — In Supplemental Terminal Data Segment (Data Segment No. DL7), field 2  | In Supplemental Terminal Data Segment (Data Segment No. DL7), field 2 (Segment Length Indicator) is required and must be present. Scenario SC-9018: Field Constraint — ENT-FIELD-DL7-2: In Supplemental Terminal Data Segment (Data Segment No. DL7), field 2 (Segment Length Indicator) is required and must be | 1. Load test data `TC-013218.json` (Electronic Mail)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail | Not Executed | P2 | Segment DL7 — Electronic Mail (ATL105 request) |

## Proprietary Data Load Request

| Test Case ID | TC Name | Description | Test Steps | Expected Result | Actual Result | Priority | Feature Name (Endpoint) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC_01 | TC-021181 — Supplemental_Entity — Proprietary Data Load Request — Supplemental Terminal Data Segment: Data Segment No. DL7, identified b | Supplemental Terminal Data Segment: Data Segment No. DL7, identified by special character '^', holds download data. Scenario SC-11389: Supplemental Entity — ENT-SEG-DL7: Supplemental Terminal Data Segment: Data Segment No. DL7, identified by special character '^', holds download data. | 1. Load test data `TC-021181.json` (Proprietary Data Load Request)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail | Not Executed | P2 | Segment DL7 — Proprietary Data Load Request (ATL105 request) |
| TC_02 | TC-021182 — Supplemental_Entity — Proprietary Data Load Request — Download Data: All or some of the Download Data in <tag><len><data> fo | Download Data: All or some of the Download Data in <tag><len><data> format. Scenario SC-11390: Supplemental Entity — ENT-ELEM-232: Download Data: All or some of the Download Data in <tag><len><data> format. | 1. Load test data `TC-021182.json` (Proprietary Data Load Request)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail | Not Executed | P2 | Segment DL7 — Proprietary Data Load Request (ATL105 request) |
