# Appendix W Download Data Layout Training

**Specification:** BUYPASS ATL105 2026-3, Appendix W-1
**Data segment:** DL7 (Supplemental Terminal Data Segment)
**Status:** `PARTIALLY_COVERED`; all printed table fields and values are catalogued and checked where unambiguous. Table Data semantics remain `REVIEW_REQUIRED` because the source's `an1` attribute conflicts with the fixed lengths and prose descriptions.

## Appendix W layouts

Appendix W defines these Download Data records:

| Table | Field | Attribute | Published value / description |
| --- | --- | --- | --- |
| Site Language | Table ID | `n3` | Fixed value `001` |
| Site Language | Table Length | `n3` | Fixed value `003` |
| Site Language | Table Data | `an1` | ISO 639-2 language code associated with this terminal |
| Postal Code | Table ID | `n3` | Fixed value `002` |
| Postal Code | Table Length | `n3` | Fixed value `013` |
| Postal Code | Table Data | `an1` | Identifies the 13-character international postal code associated with the transmitting device |

The containing Segment DL7 identifies its data with `^`, a three-digit Segment Length, and required
Download Data in `<tag><len><data>` format (Section 12.48). Element 232 permits up to 100 bytes.
[`AppendixWDownloadDataOracle`](../../../../../../src/main/java/com/coreauth/validator/canonical/AppendixWDownloadDataOracle.java)
checks the Appendix W Download Data records independently of Segment DL7's outer framing.

The oracle checks the fixed Table IDs and Table Lengths, three-digit numeric headers, record
framing, and the 100-byte Element 232 ceiling for ASCII candidate values. It does not invent
character-set rules for either Table Data value. Table 001 is described as an ISO 639-2 language code;
Table 002 is described as a 13-character international postal code. However, both rows label Table
Data `an1`, which conflicts with the fixed lengths and prose descriptions, so both value assessments
remain `REVIEW_REQUIRED`. The described associations (terminal and transmitting device) cannot be
verified from the Download Data value alone.

Both tables can be assessed alone or together. Since Appendix W does not require both tables in each
Download Data occurrence or specify table order or uniqueness, the oracle does not impose those
constraints. Non-ASCII values remain review-gated because the relevant byte encoding is not
specified here.

## Explicit source and scope limits

- Appendix W prints `Table Data an1` for both tables, despite assigning fixed Table Lengths
  `003` and `013` and describing an ISO 639-2 code and a 13-character postal code. Reading `an1`
  literally as one alphanumeric character conflicts with those lengths/descriptions. The oracle
  records both readings but does not select one; source-owner confirmation is still needed.
- Table Data character rules, language-code membership, country-specific postal validity, and the
  external terminal/device associations are not established by this oracle.
- Appendix W concerns DL7 download data, not the standard Segment 100 authorization request/response.
- This training does not validate outer DL7 Segment Length counting, whether that field counts its
  own digits, or the inconsistent `Source: Device` label. Those are open DL7 questions, as is the
  message location and whether Download Data is split across segments.
- Synthetic records are source-shaped examples, not approved terminal configuration or AI artifacts.

## Test Solution artifacts

- Oracle tests: [`AppendixWDownloadDataOracleTest`](../../../../../../src/test/java/com/coreauth/validator/AppendixWDownloadDataOracleTest.java)
- Coverage package: [`appendix-w-segment-100-coverage.json`](../../../../test-output/test-json/appendices/appendix-w-segment-100-coverage.json)
- DL7 context and remaining questions: [Segment DL7 module](../segment-DL7/README.md)

## Source

ATL105 2026-3 Appendix W-1 (extracted text around lines 35814-35830), Section 12.48 (DL7
framing, around lines 17587-17634), and Element 232 (Download Data, around lines 25160-25166) in
[`extracted_text.txt`](../../extracted_text.txt).
