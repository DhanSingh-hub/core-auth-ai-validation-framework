Hi Team,

We reviewed the test cases and test data in the 17-11 MasterCard Credit delivery (3 packages, 240 scenarios, 3,205 test cases) against ATL105 2026-4.

Several issues must be fixed before the delivery can be treated as validated and ready for execution. 1,667 test cases (52%) have at least one defect. The attached issue register lists 14 issues: 10 High and 4 Medium.

**Key findings**

**1. Test data does not match the test case (High)**
447 positive test cases claim a table or condition that the test data never sends. For example, TC-040080 claims Table 048 but does not send it. 159 negative test cases contain nothing that would cause the decline. 102 of them send the same request as an approved test case. For example, TC-039837 expects a decline and TC-039814 expects an approval for the same request, and both are marked ready.

**2. Wrong expected results (High)**
Pre-authorized completions are expected to be declined, although ATL105 defines them as valid. No test case covers a successful completion. AVS approvals expect 2 or 0 instead of 3 or 4, and partial approvals expect 2 instead of F. 78 test cases expect download (DL) segments in a purchase or authorization response, but these only appear in Table Load responses.

**3. Invalid values in the test data (High)**
- Nonfuel Amount "0" on 208 fuel-machine test cases (it must be empty or 1–99999999).
- PIN block sent as the text "PIN" or "16" (48 test cases).
- Only an expiry date where the card's track data is required (37 test cases, plus 435 in the data element package).
- Fleet, loyalty or check segments on MasterCard credit requests (51 test cases).
- Offline PIN on non-chip transactions (39 test cases).
- New authorizations that already carry an Approval Number (8 test cases).

The data element and host operations packages were not regenerated, so they still have the Round 4 defects.

**4. "Ready" status is not reliable (High)**
664 of the 1,429 ready test cases (46%) have defects, and 115 ready network test cases have no confirmed expected result. The README still reports "validation findings 0".

**5. Other issues (Medium)**
Six features that ATL105 supports have no test case. Examples are Authorization Only Reversal, chip fallback to swipe (entry mode 80) and an expired card. Split-tender amounts, client-input placeholders, spec references and the ID migration map also need correction.

Details, ATL105 references and the full list of affected test cases are in the attachments.

Thanks
