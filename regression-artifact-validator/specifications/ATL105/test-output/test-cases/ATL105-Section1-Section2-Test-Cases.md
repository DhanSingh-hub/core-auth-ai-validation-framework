# ATL105 Section 1 And Section 2 Test Cases

**Status:** Working baseline - Test Team review required before Client submission.

## Scope

TCP/IP Message Header, ATL105 Data Section No. 1, and Segment 100 only; no Data Section No. 3 segments. Each test case has one primary verifiable objective. Expected platform responses remain to be confirmed with the Client and BUYPASS integration contract.

| ID | Scenario | Test Objective | Expected Result |
| --- | --- | --- | --- |
| TEST-CASE-ATL105-MIN-001 | MIN-001 | Submit an eligible minimal request. | Structure is classified as eligible. |
| TEST-CASE-ATL105-MIN-002 | MIN-001 | Include a Section 3 trigger in a minimal request. | Structure is classified as ineligible. |
| TEST-CASE-ATL105-MIN-003 | MIN-002 | Create eligible POS purchase/capture. | Transaction Type `0` is accepted by the minimal-scope validator. |
| TEST-CASE-ATL105-MIN-004 | MIN-003 | Create correlated preauthorized completion. | Completion correlation data is present. |
| TEST-CASE-ATL105-MIN-005 | MIN-004 | Create eligible POS authorization-only request. | Transaction Type `3` is accepted by the minimal-scope validator. |
| TEST-CASE-ATL105-MIN-006 | MIN-005 | Create eligible merchandise return. | Transaction Type `7` is accepted by the minimal-scope validator. |
| TEST-CASE-ATL105-MIN-007 | MIN-006 | Create eligible purchase reversal. | Transaction Type `8` and original reference are consistent. |
| TEST-CASE-ATL105-MIN-008 | MIN-007 | Create void of merchandise return. | Transaction Type `U` and original refund reference are consistent. |
| TEST-CASE-ATL105-MIN-009 | MIN-008 | Create time-out reversal. | Transaction Type `Z` and original reference are consistent. |
| TEST-CASE-ATL105-MIN-010 | MIN-009 | Create account verification without purchase amount. | Transaction Type `A` follows its minimal-scope rules. |
| TEST-CASE-ATL105-MIN-011 | MIN-010 | Create eligible mail/phone purchase. | Transaction Type `6` follows minimal-scope rules. |
| TEST-CASE-ATL105-MIN-012 | MIN-011 | Create mail/phone authorization-only request. | Transaction Type `B` follows minimal-scope rules. |
| TEST-CASE-ATL105-MIN-013 | MIN-011 | Create mail/phone reversal. | Transaction Type `C` follows minimal-scope rules. |
| TEST-CASE-ATL105-MIN-014 | MIN-012 | Encode valid Message Length. | Length equals the following payload byte count. |
| TEST-CASE-ATL105-MIN-015 | MIN-012 | Encode incorrect Message Length. | Header integrity validation fails. |
| TEST-CASE-ATL105-MIN-016 | MIN-012 | Reverse Message Length byte order. | Header integrity validation fails. |
| TEST-CASE-ATL105-MIN-017 | MIN-012 | Supply only one Message Length byte. | Input is identified as incomplete. |
| TEST-CASE-ATL105-MIN-018 | MIN-012 | Send header without declared payload. | Input is identified as incomplete. |
| TEST-CASE-ATL105-MIN-019 | MIN-013 | Set TPDU Protocol ID to `0x60`. | Protocol ID validation passes. |
| TEST-CASE-ATL105-MIN-020 | MIN-013 | Set invalid TPDU Protocol ID. | Protocol ID validation fails. |
| TEST-CASE-ATL105-MIN-021 | MIN-013 | Set all TPDU address bytes to `0x00`. | Reserved-field validation passes. |
| TEST-CASE-ATL105-MIN-022 | MIN-013 | Set a non-zero reserved TPDU address byte. | Reserved-field validation fails. |
| TEST-CASE-ATL105-MIN-023 | MIN-014 | Set Element 55 to `ATL105`. | Message identity validation passes. |
| TEST-CASE-ATL105-MIN-024 | MIN-014 | Set Element 55 to another value. | Message identity validation fails. |
| TEST-CASE-ATL105-MIN-025 | MIN-014 | Set Element 63 to `01` for one Segment 100. | Segment-count validation passes. |
| TEST-CASE-ATL105-MIN-026 | MIN-014 | Set Element 63 to a value other than `01`. | Segment-count validation fails. |
| TEST-CASE-ATL105-MIN-027 | MIN-014 | Omit required Data Section No. 1 separator. | Section serialization validation fails. |
| TEST-CASE-ATL105-MIN-028 | MIN-015 | Include one Segment 100. | Segment-presence validation passes. |
| TEST-CASE-ATL105-MIN-029 | MIN-015 | Omit Segment 100. | Segment-presence validation fails. |
| TEST-CASE-ATL105-MIN-030 | MIN-015 | Include duplicate Segment 100. | Single-occurrence validation fails. |
| TEST-CASE-ATL105-MIN-031 | MIN-015 | Set Segment Type to `100`. | Segment identity validation passes. |
| TEST-CASE-ATL105-MIN-032 | MIN-015 | Set Segment Type to another value. | Segment identity validation fails. |
| TEST-CASE-ATL105-MIN-033 | MIN-015 | Match Segment Length to encoded content. | Segment-length validation passes. |
| TEST-CASE-ATL105-MIN-034 | MIN-015 | Mismatch Segment Length and encoded content. | Segment-length validation fails. |
| TEST-CASE-ATL105-MIN-035 | MIN-016 | Populate required core Segment 100 fields. | Required-field validation passes. |
| TEST-CASE-ATL105-MIN-036 | MIN-016 | Omit a required core Segment 100 field. | Required-field validation fails. |
| TEST-CASE-ATL105-MIN-037 | MIN-016 | Leave a non-trailing conditional field unpopulated. | Its field separator remains present. |
| TEST-CASE-ATL105-MIN-038 | MIN-016 | Omit unneeded trailing optional fields. | Trailing-field serialization validation passes. |
| TEST-CASE-ATL105-MIN-039 | MIN-017 | Classify an EMV request without Segment 130. | Minimal-scope eligibility validation fails. |
| TEST-CASE-ATL105-MIN-040 | MIN-018 | Classify each additional-data trigger without its required segment. | Minimal-scope eligibility validation fails. |