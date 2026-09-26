# ATL105 Section 1 And Section 2 Business Requirements

**Status:** Working baseline - Test Team review required before Client submission.

## Scope

TCP/IP Message Header, ATL105 Data Section No. 1, and Data Section No. 2 (Segment 100) only. Data Section No. 3 is excluded. This covers standard non-EMV financial transactions only where no additional data segment is required.

## Terminology

- **TCP/IP Message Header:** transport envelope; not ATL105 Data Section No. 1.
- **Data Section No. 1:** Element 55 (`ATL105`) and Element 63 (Number of Segments).
- **Data Section No. 2:** Segment 100, Standard Message Data Segment.

## Business Requirements

| ID | Category | Requirement Statement | Acceptance Criteria | Source Reference |
| --- | --- | --- | --- | --- |
| TEST-BR-ATL105-HDR-001 | Header framing | The message shall include a TCP/IP header before the ATL105 payload. | Header-only input is incomplete; declared payload follows the header. | Appendix A |
| TEST-BR-ATL105-HDR-002 | Header length | Message Length shall represent all following data and exclude its own two bytes. | Encoded header length equals actual following byte count. | Appendix A |
| TEST-BR-ATL105-HDR-003 | Header protocol | TPDU Protocol ID shall be `0x60`. | Any other protocol ID is invalid for this message format. | Appendix A |
| TEST-BR-ATL105-HDR-004 | Reserved TPDU fields | TPDU destination and source addresses shall be `0x00` when sending to BUYPASS. | All four reserved address bytes are zero. | Appendix A |
| TEST-BR-ATL105-DS1-001 | Message identity | Data Section No. 1 shall identify the message format as `ATL105`. | Element 55 equals `ATL105`. | 11.1.1; Element 55 |
| TEST-BR-ATL105-DS1-002 | Segment count | Data Section No. 1 shall state the actual number of BUYPASS-defined data segments. | Element 63 is `01` for this minimal-message scope. | 11.1.1; Element 63 |
| TEST-BR-ATL105-DS1-003 | Section separators | Data Section No. 1 fields shall use the specified field separators. | Separator exists between Elements 55 and 63 and after Element 63. | 11.1.1 |
| TEST-BR-ATL105-S100-001 | Segment presence | Segment 100 shall appear once in Data Section No. 2. | Exactly one Segment 100 is present. | 11.1.1; 12.1 |
| TEST-BR-ATL105-S100-002 | Segment identity | Segment 100 shall declare Segment Type `100`. | Element 85 equals `100`. | 12.1; Element 85 |
| TEST-BR-ATL105-S100-003 | Segment structure | Segment 100 shall retain the specified 17-field order and calculated segment length. | Field order and Element 84 match the encoded segment. | 12.1; Elements 84 and 85 |
| TEST-BR-ATL105-S100-004 | Core transaction identity | Segment 100 shall include required terminal, prompt, account/identifier, sequence, and partial-approval data. | Required fields are present and satisfy their applicable format/value rules. | 12.1; Elements 2, 78, 86, 102, 121 |
| TEST-BR-ATL105-S100-005 | Conditional data | Conditional Segment 100 fields shall be populated only when the selected transaction flow requires them. | PIN, amount, pump, approval, and date/time fields follow the applicable flow rule. | 12.1 |
| TEST-BR-ATL105-S100-006 | Field serialization | An unpopulated non-trailing Segment 100 field shall retain its field separator; unneeded trailing optional fields shall not be transmitted. | Serialized field-separator behavior follows section 12.1. | 12.1 |
| TEST-BR-ATL105-S100-007 | Lifecycle correlation | Completion, void, return-void, and time-out reversal shall correlate to the intended original transaction. | Applicable original transaction reference data is present and consistent. | Appendix G; 12.1 |
| TEST-BR-ATL105-ELIG-001 | Minimal-message eligibility | A transaction may omit Data Section No. 3 only when it does not require transaction-specific data. | Transaction is classified eligible only when no Section 3 trigger applies. | 11.1.1 |
| TEST-BR-ATL105-ELIG-002 | Excluded flows | EMV and other flows requiring additional data shall not use this minimal-message structure. | EMV requires Segment 130; other applicable triggers require their specified Segment 3 data. | 11.1.1; 11.8.1 |

## Approval

| Test Team Review | Client Review |
| --- | --- |
| Pending | Not submitted |
