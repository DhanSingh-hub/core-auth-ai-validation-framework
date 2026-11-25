**To:** &lt;AI developer&gt;  
**Cc:** &lt;QA lead&gt;  
**Subject:** ATL105 MasterCard Credit run 16-Nov-2026 – generator issues (154 of 1,861 test cases fail)  
**Attachments:** Email_Issue_Report_to_AI_Developer.md (full details), ATL105_Run_Validation_All_Test_Cases.xlsx

Hi &lt;name&gt;,

We checked the 16-Nov MasterCard Credit run against the BUYPASS ATL105 Message Format Specifications, release 2026-4. The run has 1,861 test cases and 2,147 test data files.

| Result | Test cases |
|---|---:|
| Fail | 154 |
| Blocked (client values missing) | 1,570 |
| Device-only | 137 |
| Pass | 0 |

**Please fix in the generator (High priority):**

1. **D-13 (37 test cases):** for a card read from the chip or stripe, Card Discretionary Block Data has only an MMYY expiry. It must be the Track 2 data after the "=" (Element 12, p.371).
2. **D-01 (28):** Totals Requests after Authorization Only and Return flows use QE example values (count 00001, amount 0) instead of the calculated totals (Section 9.2, p.72).
3. **D-02 (18):** optional Segment 100 fields are filled although their condition does not hold. An Approval Number on a new Authorization Only makes it preauthorized (Element 5, p.368). The literal text "PIN" is sent as the PIN block (Element 33, p.382).
4. **D-03 / D-12 (10):** two segments are too long.
   - Segment 145 is over 999 characters, has a 4-digit length and is not in tag/length/data format (p.289).
   - Segment 108 is 156 characters; the maximum is 142 (p.238).
5. **D-09 (10):** negative tests break a second rule. Examples: Credential on File tables missing, Segment 130 length not recalculated, Account Number "TOTALS REQUEST".
6. **D-15 (3):** positive boundary tests are not at the limit, or use an invalid value (`A`).
7. **C-02 (43):** negative tests are built on rules ATL105 does not have. Example: "Information Byte sent where the spec forbids it". It is a Required field (p.223).
8. **C-01 (28):** a test sends the same request as another test but expects the opposite result.

**Also to correct (Medium priority):**

- 606 test cases are marked `ready` although the terminal is a placeholder.
- 276 test cases have the expected Response Code left open (`?`).
- 64 test cases send a Time-out Reversal after a step that expects a response.
- 40 "different terminal" tests use a random Terminal Identifier.
- 976 test cases have ATL105 quotes that are not in the spec, on another page, or not word for word.
- 50 host test cases have no `tx_legs`.

The attached report has the evidence, the exact ATL105 text and the test case IDs for each issue. In the Excel, open sheet Findings and filter on "Defect id".

Please let us know when a new build is ready. We will re-run the validation and send you the differences.

Thanks,  
&lt;Your name&gt;
