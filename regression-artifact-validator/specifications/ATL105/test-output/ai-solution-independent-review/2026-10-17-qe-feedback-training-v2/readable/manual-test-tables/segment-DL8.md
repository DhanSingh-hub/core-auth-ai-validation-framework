# Segment DL8 — Manual Validation Test Cases

Format per QE feedback Def_SepRun06: functional coverage only (happy path, BDD scenario, contract, negative, boundary); no NFR rows. Test Case IDs are sequential per feature; the AI test-case ID is kept in TC Name. Priority maps producer P1→P0, P2→P1, P3/P4→P2.

## ECA/TeleCheck Service Transaction

| Test Case ID | TC Name | Description | Test Steps | Expected Result | Actual Result | Priority | Feature Name (Endpoint) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC_01 | TC-013219 — Field_Constraint — ECA/TeleCheck Service Transaction — In EMV Terminal Floor Limits Data Segment (Data Segment No. DL8), fiel | In EMV Terminal Floor Limits Data Segment (Data Segment No. DL8), field 2 (Segment Length Indicator) is required and must be present. Scenario SC-9021: Field Constraint — ENT-FIELD-DL8-2: In EMV Terminal Floor Limits Data Segment (Data Segment No. DL8), field 2 (Segment Length Indicator) is required and mus | 1. Load test data `TC-013219.json` (ECA/TeleCheck Service Transaction)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | Response code 0 (oracle tier ASSERT) | Not Executed | P2 | Segment DL8 — ECA/TeleCheck Service Transaction (ATL105 request) |

## ECA/TeleCheck Service Transaction Request

| Test Case ID | TC Name | Description | Test Steps | Expected Result | Actual Result | Priority | Feature Name (Endpoint) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC_01 | TC-013220 — Field_Constraint — ECA/TeleCheck Service Transaction Request — In EMV Terminal Floor Limits Data Segment (Data Segment No. DL8), fiel | In EMV Terminal Floor Limits Data Segment (Data Segment No. DL8), field 2 (Segment Length Indicator) is required and must be present. Scenario SC-9021: Field Constraint — ENT-FIELD-DL8-2: In EMV Terminal Floor Limits Data Segment (Data Segment No. DL8), field 2 (Segment Length Indicator) is required and mus | 1. Load test data `TC-013220.json` (ECA/TeleCheck Service Transaction Request)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | Response code 0 (oracle tier ASSERT) | Not Executed | P2 | Segment DL8 — ECA/TeleCheck Service Transaction Request (ATL105 request) |

## EMV Financial Transaction Request

| Test Case ID | TC Name | Description | Test Steps | Expected Result | Actual Result | Priority | Feature Name (Endpoint) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC_01 | TC-013221 — Field_Constraint — EMV Financial Transaction Request — In EMV Terminal Floor Limits Data Segment (Data Segment No. DL8), fiel | In EMV Terminal Floor Limits Data Segment (Data Segment No. DL8), field 2 (Segment Length Indicator) is required and must be present. Scenario SC-9021: Field Constraint — ENT-FIELD-DL8-2: In EMV Terminal Floor Limits Data Segment (Data Segment No. DL8), field 2 (Segment Length Indicator) is required and mus | 1. Load test data `TC-013221.json` (EMV Financial Transaction Request)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | Response code 0 (oracle tier ASSERT) | Not Executed | P2 | Segment DL8 — EMV Financial Transaction Request (ATL105 request) |
| TC_02 | TC-021183 — Supplemental_Entity — EMV Financial Transaction Request — EMV Terminal Floor Limits Data Segment: Contains floor limits per card | EMV Terminal Floor Limits Data Segment: Contains floor limits per card type (RID) for a terminal; included based on terminal-level special. Scenario SC-11391: Supplemental Entity — ENT-SEG-DL8: EMV Terminal Floor Limits Data Segment: Contains floor limits per card type (RID) for a terminal; included based on term | 1. Load test data `TC-021183.json` (EMV Financial Transaction Request)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | Response code 0 (oracle tier ASSERT) | Not Executed | P2 | Segment DL8 — EMV Financial Transaction Request (ATL105 request) |
| TC_03 | TC-021205 — Supplemental_Entity — EMV Financial Transaction Request — RID: Identifies EMV application via EMV Registered Application Provide | RID: Identifies EMV application via EMV Registered Application Provider identifier. Scenario SC-11681: Supplemental Entity — ENT-ELEM-233: RID: Identifies EMV application via EMV Registered Application Provider identifier. | 1. Load test data `TC-021205.json` (EMV Financial Transaction Request)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | Response code 0 (oracle tier ASSERT) | Not Executed | P2 | Segment DL8 — EMV Financial Transaction Request (ATL105 request) |
| TC_04 | TC-021206 — Supplemental_Entity — EMV Financial Transaction Request — BUYPASS RID Card Type: Indicates BUYPASS card type associated with RID | BUYPASS RID Card Type: Indicates BUYPASS card type associated with RID, fixed length 3 bytes, alphanumeric. Scenario SC-11684: Supplemental Entity — ENT-ELEM-236: BUYPASS RID Card Type: Indicates BUYPASS card type associated with RID, fixed length 3 bytes, alphanumeric. | 1. Load test data `TC-021206.json` (EMV Financial Transaction Request)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | Response code 0 (oracle tier ASSERT) | Not Executed | P2 | Segment DL8 — EMV Financial Transaction Request (ATL105 request) |

## EMV Request Transaction

| Test Case ID | TC Name | Description | Test Steps | Expected Result | Actual Result | Priority | Feature Name (Endpoint) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC_01 | TC-013222 — Field_Constraint — EMV Request Transaction — In EMV Terminal Floor Limits Data Segment (Data Segment No. DL8), fiel | In EMV Terminal Floor Limits Data Segment (Data Segment No. DL8), field 2 (Segment Length Indicator) is required and must be present. Scenario SC-9021: Field Constraint — ENT-FIELD-DL8-2: In EMV Terminal Floor Limits Data Segment (Data Segment No. DL8), field 2 (Segment Length Indicator) is required and mus | 1. Load test data `TC-013222.json` (EMV Request Transaction)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | Response code 0 (oracle tier ASSERT) | Not Executed | P2 | Segment DL8 — EMV Request Transaction (ATL105 request) |

## Electronic Mail

| Test Case ID | TC Name | Description | Test Steps | Expected Result | Actual Result | Priority | Feature Name (Endpoint) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC_01 | TC-013223 — Field_Constraint — Electronic Mail — In EMV Terminal Floor Limits Data Segment (Data Segment No. DL8), fiel | In EMV Terminal Floor Limits Data Segment (Data Segment No. DL8), field 2 (Segment Length Indicator) is required and must be present. Scenario SC-9021: Field Constraint — ENT-FIELD-DL8-2: In EMV Terminal Floor Limits Data Segment (Data Segment No. DL8), field 2 (Segment Length Indicator) is required and mus | 1. Load test data `TC-013223.json` (Electronic Mail)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | NOT DEFINED — producer expected response code is null (oracle tier INFER); QEF-009 blocks pass/fail | Not Executed | P2 | Segment DL8 — Electronic Mail (ATL105 request) |

## Financial Transaction Request

| Test Case ID | TC Name | Description | Test Steps | Expected Result | Actual Result | Priority | Feature Name (Endpoint) |
| --- | --- | --- | --- | --- | --- | --- | --- |
| TC_01 | TC-021104 — Business_Rule — Financial Transaction Request — For stand-in based MIT transactions, merchants should store original t | For stand-in based MIT transactions, merchants should store original transaction identifier for subsequent transactions. Scenario SC-8519: Business Rule — BR-706-12: For stand-in based MIT transactions, merchants should store original transaction identifier for subsequent transactions. | 1. Load test data `TC-021104.json` (Financial Transaction Request)<br>2. Send the request to the authorizer<br>3. Compare the response with the expected result | Response code 0 (oracle tier ASSERT) | Not Executed | P2 | Segment DL8 — Financial Transaction Request (ATL105 request) |
