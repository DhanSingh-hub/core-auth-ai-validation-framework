# ATL105 Section 1 And Section 2 Test Scenarios

**Status:** Working baseline - Test Team review required before Client submission.

## Scope

TCP/IP Message Header, ATL105 Data Section No. 1, and Segment 100 only; no Data Section No. 3 segments.

| ID | Linked Test-BR | Scenario | Expected Outcome |
| --- | --- | --- | --- |
| TEST-SCN-ATL105-MIN-001 | ELIG-001 | Valid minimal financial request | The request is eligible when no Section 3 trigger applies. |
| TEST-SCN-ATL105-MIN-002 | ELIG-001 | POS purchase/capture | A basic non-EMV POS purchase can use the minimal structure. |
| TEST-SCN-ATL105-MIN-003 | S100-007 | Preauthorized completion | Completion is correlated to a prior authorization without Section 3 when eligible. |
| TEST-SCN-ATL105-MIN-004 | ELIG-001 | POS authorization only | A basic non-EMV authorization-only request can use the minimal structure. |
| TEST-SCN-ATL105-MIN-005 | S100-007 | Merchandise return/refund | A refund is represented with the minimal structure when eligible. |
| TEST-SCN-ATL105-MIN-006 | S100-007 | Purchase reversal/void | A void references the intended original transaction. |
| TEST-SCN-ATL105-MIN-007 | S100-007 | Void of merchandise return | A void-of-return references the intended original refund. |
| TEST-SCN-ATL105-MIN-008 | S100-007 | Time-out reversal | A reversal is correlated to a transaction with unknown completion status. |
| TEST-SCN-ATL105-MIN-009 | ELIG-001 | Account verification | Account verification is represented without a financial purchase amount. |
| TEST-SCN-ATL105-MIN-010 | ELIG-001 | Mail/phone purchase | An eligible card-not-present purchase uses the minimal structure. |
| TEST-SCN-ATL105-MIN-011 | ELIG-001 | Mail/phone authorization and reversal | Eligible card-not-present authorization and reversal use the minimal structure. |
| TEST-SCN-ATL105-MIN-012 | HDR-001, HDR-002 | Valid header framing | TCP/IP header length matches the complete following ATL105 payload. |
| TEST-SCN-ATL105-MIN-013 | HDR-003, HDR-004 | Valid TPDU controls | Protocol ID and reserved TPDU bytes have their required values. |
| TEST-SCN-ATL105-MIN-014 | DS1-001, DS1-002, DS1-003 | Valid Data Section No. 1 | `ATL105`, `01`, and required separators are correctly serialized. |
| TEST-SCN-ATL105-MIN-015 | S100-001, S100-002, S100-003 | Valid Segment 100 structure | Segment 100 occurs once, identifies itself as `100`, and has valid length/order. |
| TEST-SCN-ATL105-MIN-016 | S100-004, S100-005, S100-006 | Segment 100 field handling | Required and conditional fields are serialized according to the selected flow. |
| TEST-SCN-ATL105-MIN-017 | ELIG-002 | EMV exclusion | EMV transaction is ineligible because Segment 130 is required. |
| TEST-SCN-ATL105-MIN-018 | ELIG-002 | Additional-data exclusion | Token, fleet, product/fuel, EBT, purchase-card, variable, and Moneris triggers make the minimal structure ineligible. |