# ATL105 AI Test Data – Consolidated Defect Report, all programs (previous run 31-10-out → new run 5-11-out)

| Item | Value |
|---|---|
| Programs | American Express Credit, MasterCard Credit, MasterCard Fleet, Interlink Debit, Maestro Direct Debit |
| Previous run | `C:/Users/F7OKAGW/Downloads/31-10-out` |
| New run | `5-11-out` |
| Previous defect logs | `Defect MD file/` – Consolidated_Defect_Log_AMEX-Credit_MC-Credit_MC-Fleet.md (CD-xx) with AMEX_Credit_Defect_Log.md (A-xx), MasterCard_Credit_Consolidated_Defect_Report.md (D-xx), MasterCard_Fleet_Consolidated_Defects.md (FL-xx); Interlink_Debit_Consolidated_Defect_Report.md (D-xx). **Maestro Direct Debit had no log** – its defects were found now (§4). |
| Specification | BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026) |
| Method | Every defect rule was run on every test data file of both runs; the previous-run counts reproduce the logs. Test case IDs are renumbered in the new run, so every previously failing file was traced to its new file by transaction category + entry mode + template and step. |
| Date | 2026-11-05 |
| Overall result | 76 defect entries re-checked: **58 fixed**, **14 fixed – value awaiting client input**, **2 not fixed**, 2 closed (test cases removed). One generator rule is still wrong in every program: the settlement totals (72 test cases, §2). |

Status: **FIXED** – every previously failing file now passes · **FIXED – value awaiting client input** – the generator builds the field correctly, but the value can only come from the client (listed in the test case's `awaiting_inputs`, status `needs_client_input`) · **NOT FIXED** · **CLOSED – test cases removed** – the affected test cases are no longer in the catalog.

---

## 1. Status by program

| Program | Defects re-checked | FIXED | FIXED – awaiting client input | NOT FIXED | CLOSED – test cases removed | Report |
|---|---|---|---|---|---|---|
| American Express Credit | 17 | 15 | 2 | 0 | 0 | [AMEX_Credit_Fix_Verification.md](./AMEX_Credit_Fix_Verification.md) |
| MasterCard Credit | 20 | 16 | 4 | 0 | 0 | [MasterCard_Credit_Fix_Verification.md](./MasterCard_Credit_Fix_Verification.md) |
| MasterCard Fleet | 11 | 9 | 2 | 0 | 0 | [MasterCard_Fleet_Fix_Verification.md](./MasterCard_Fleet_Fix_Verification.md) |
| Interlink Debit | 14 | 9 | 3 | **1** | 1 | [Interlink_Debit_Fix_Verification.md](./Interlink_Debit_Fix_Verification.md) |
| Maestro Direct Debit (missed in the previous logs) | 14 | 9 | 3 | **1** | 1 | [Maestro_Direct_Debit_Fix_Verification.md](./Maestro_Direct_Debit_Fix_Verification.md) |

---

## 2. Still open – the generator must change

One rule fails in all five programs: the **Totals Request does not match the transactions of its flow** (ATL105 Section 9.2, p.72: a Purchase/Capture increases the count and amount, a Reversal decreases both, an Authorization Only goes to the AO bucket, which the grand total leaves out). Two settlement flows are affected in every program:

- *Purchase timed out → Time-out Reversal → Settlement*: the purchase never got a response, so nothing is in the totals, but the file sends the card bucket with count `00001` and amount `00000000`. Expected: no card-type category, Grand Total `00000000`.
- *Authorization Only → Completion → Settlement*: the completion captured $15.00, but the file sends amount and grand total `00000000`. Expected: count `00001`, amount `00001500`, grand total `00001500`.

**Root cause:** how the test cases produce Grand Total, Card Type Total Count and Card Type Total Amount (`test_cases.jsonl` → the totals step → `"Totals Data Segment"` → `method`), over all Totals Requests of the new run: failing `qe_example_value` 72; passing `spec_rule_value` 252. For these two templates the generator copies a fixed example instead of computing the totals from the steps of the flow, as it already does for every other settlement template. The test cases even describe the right result in their own notes (*"the timed-out purchase is not in the totals"*; *"the completion is in the totals; the authorization only is not"*). Worked examples with every step, file and line: D-21 in the Interlink and Maestro reports, §4 in the other program reports.

| Program | Logged as | Previous run TCs | New run TCs | Check it yourself (new run) |
|---|---|---|---|---|
| Interlink Debit | [D-21](./Interlink_Debit_Fix_Verification.md#d-21) NOT FIXED | 14 | 12 | Previous: [TD-TC-002896.step3.totals.json](../interlink_debit/test_data/TD-TC-002896.step3.totals.json#L21) line 21 → `count 00001, amount 00000000, grand total 00000000`. New (TC-000010): [TD-TC-000010.step3.totals.json](../5-11-out/interlink_debit/test_data/TD-TC-000010.step3.totals.json#L21) line 21 → `count 00001, amount 00000000, grand total 00000000` |
| Maestro Direct Debit | [D-21](./Maestro_Direct_Debit_Fix_Verification.md#d-21) NOT FIXED (missed in the previous logs, found now) | 14 | 12 | Previous: [TD-TC-003174.step3.totals.json](../maestro_direct_debit/test_data/TD-TC-003174.step3.totals.json#L21) line 21 → `count 00001, amount 00000000, grand total 00000000`. New (TC-001516): [TD-TC-001516.step3.totals.json](../5-11-out/maestro_direct_debit/test_data/TD-TC-001516.step3.totals.json#L21) line 21 → `count 00001, amount 00000000, grand total 00000000` |
| American Express Credit | not in the previous log (found now) | 16 | 16 | TC-001069 T8-PUR-TOR-SETTLE: [TD-TC-001069.step3.totals.json](../5-11-out/american_express_credit/test_data/TD-TC-001069.step3.totals.json#L21) line 21 → `count 00001, amount 00000000, grand total 00000000`; expected: Leave out the TE bucket and send Grand Total 00000000 |
| MasterCard Credit | not in the previous log (found now) | 16 | 16 | TC-000275 T8-PUR-TOR-SETTLE: [TD-TC-000275.step3.totals.json](../5-11-out/mastercard_credit/test_data/TD-TC-000275.step3.totals.json#L21) line 21 → `count 00001, amount 00000000, grand total 00000000`; expected: Leave out the CC bucket and send Grand Total 00000000 |
| MasterCard Fleet | not in the previous log (found now) | 16 | 16 | TC-000756 T8-PUR-TOR-SETTLE: [TD-TC-000756.step3.totals.json](../5-11-out/mastercard_fleet/test_data/TD-TC-000756.step3.totals.json#L21) line 21 → `count 00001, amount 00000000, grand total 00000000`; expected: Leave out the FL bucket and send Grand Total 00000000 |

Every affected file: sheet *Still failing (new run)* of `Defect_Fix_Verification.xlsx` and §4 of each program report.

---

## 3. Waiting for client input – generator fixed, value still to be supplied

The new run builds these fields and lists them in each test case's `awaiting_inputs` (test case status `needs_client_input`). The files cannot be sent until the client supplies the value; this is the fix the previous logs asked for. Note: Table 030 is sent with length `010` and an empty value – the length is for the value to come.

| Value awaited from the client | Program (defect): new-run files waiting | Example (new run) |
|---|---|---|
| Table 030 POS Condition Code (10 digits, Appendix Q) of the certified terminal | American Express Credit (CD-03): 239 files<br>MasterCard Credit (CD-03): 258 files<br>MasterCard Fleet (CD-03): 157 files<br>Interlink Debit (D-12): 104 files<br>Maestro Direct Debit (D-12): 104 files | American Express Credit TC-001053: [TD-TC-001053.json](../5-11-out/american_express_credit/test_data/TD-TC-001053.json#L44) line 44 → `030 sent, length 010, value empty` |
| Table 003 AVS address / ZIP Code of the AVS test card | American Express Credit (CD-18): 8 files<br>MasterCard Credit (CD-18): 8 files | American Express Credit TC-001203: [TD-TC-001203.json](../5-11-out/american_express_credit/test_data/TD-TC-001203.json#L44) line 44 → `003 sent, value empty` |
| Table 056 sub-table 09 MIT/CIT category of the Credential-on-File use case (MasterCard) | MasterCard Credit (CD-04): 116 files<br>MasterCard Fleet (CD-04): 69 files | MasterCard Credit TC-000471: [TD-TC-000471.json](../5-11-out/mastercard_credit/test_data/TD-TC-000471.json#L49) line 49 → `032-08 C/I 08001C; 049-02 021C; 056-…ng client) (62 chars)` |
| Element 33 PIN block of the online-PIN test card (DUKPT key) | MasterCard Credit (CD-13): 4 files | MasterCard Credit TC-000260: [TD-TC-000260.json](../5-11-out/mastercard_credit/test_data/TD-TC-000260.json#L16) line 16 → `E33 '' / 9F34 420300` |
| Debit card data: Account Number and Element 12 of the client's debit test card | Interlink Debit (D-15): 478 files<br>Maestro Direct Debit (D-15): 478 files | Interlink Debit TC-000001: [TD-TC-000001.json](../5-11-out/interlink_debit/test_data/TD-TC-000001.json#L14) line 14 → `<AWAITING:AccountNumber> / <AWAITING…BlockData> (64 chars)` |
| Element 33 debit PIN block (client's test PIN and DUKPT key) | Interlink Debit (D-16): 310 files<br>Maestro Direct Debit (D-16): 310 files | Interlink Debit TC-000001: [TD-TC-000001.json](../5-11-out/interlink_debit/test_data/TD-TC-000001.json#L16) line 16 → `<AWAITING:EncryptedPINBlockData>` |

---

## 4. Maestro Direct Debit – the defects missed in the previous logs

Maestro Direct Debit was part of the previous delivery (`31-10-out/maestro_direct_debit`, 278 test cases, 536 test data files), but no defect log covered it – it appears only in the counts of CD-01 and CD-19 of the consolidated log. It has now been validated field by field with the same ATL105 rules as Interlink Debit (both are PIN debit programs with the same templates), in the previous run and in the new run.

- Full validation of the previous run (per scenario, per test case, every finding with its ATL105 text): [Maestro Manual Validation Report](../maestro_direct_debit/Manual%20Validation%20Report/README.md); defect list: [Consolidated_Defect_Report.md](../maestro_direct_debit/Manual%20Validation%20Report/Consolidated_Defect_Report.md) (same D-IDs as Interlink).
- Previous run vs new run: [Maestro_Direct_Debit_Fix_Verification.md](./Maestro_Direct_Debit_Fix_Verification.md).

| Defect | What was wrong (previous run) | Previous run TCs / files | New run TCs / files failing | Status in the new run |
|---|---|---|---|---|
| [D-01](../maestro_direct_debit/Manual%20Validation%20Report/Consolidated_Defect_Report.md#d-01) | EMV Card Sequence Number (Element 188) has the wrong width | 72 / 90 | 0 / 0 | [FIXED](./Maestro_Direct_Debit_Fix_Verification.md#d-01) |
| [D-02](../maestro_direct_debit/Manual%20Validation%20Report/Consolidated_Defect_Report.md#d-02) | EMV Additional Information (Element 118) does not contain EMV Table Data | 72 / 90 | 0 / 0 | [FIXED](./Maestro_Direct_Debit_Fix_Verification.md#d-02) |
| [D-09](../maestro_direct_debit/Manual%20Validation%20Report/Consolidated_Defect_Report.md#d-09) | Credential on File indicators (Tables 032, 049, 056) are missing | 35 / 67 | 0 / 0 | [CLOSED – test cases removed](./Maestro_Direct_Debit_Fix_Verification.md#d-09) |
| [D-12](../maestro_direct_debit/Manual%20Validation%20Report/Consolidated_Defect_Report.md#d-12) | Table 030 (POS Condition Code) is missing on EMV and Credential on File transactions | 107 / 157 | 0 / 0 | [FIXED – value awaiting client input](./Maestro_Direct_Debit_Fix_Verification.md#d-12) |
| [D-14](../maestro_direct_debit/Manual%20Validation%20Report/Consolidated_Defect_Report.md#d-14) | Follow-on step (completion, cancellation, reversal): amount data built from an impossible value | 7 / 7 | 0 / 0 | [FIXED](./Maestro_Direct_Debit_Fix_Verification.md#d-14) |
| [D-15](../maestro_direct_debit/Manual%20Validation%20Report/Consolidated_Defect_Report.md#d-15) | The card data (Account Number, Card Discretionary Block Data) is empty | 249 / 473 | 0 / 0 | [FIXED – value awaiting client input](./Maestro_Direct_Debit_Fix_Verification.md#d-15) |
| [D-16](../maestro_direct_debit/Manual%20Validation%20Report/Consolidated_Defect_Report.md#d-16) | The debit PIN block (Element 33) is empty | 242 / 305 | 0 / 0 | [FIXED – value awaiting client input](./Maestro_Direct_Debit_Fix_Verification.md#d-16) |
| [D-17](../maestro_direct_debit/Manual%20Validation%20Report/Consolidated_Defect_Report.md#d-17) | A keyed (manual) entry mode is used for a debit card | 34 / 43 | 0 / 0 | [FIXED](./Maestro_Direct_Debit_Fix_Verification.md#d-17) |
| [D-18](../maestro_direct_debit/Manual%20Validation%20Report/Consolidated_Defect_Report.md#d-18) | Cash back: the chip amounts (Tags 9F02, 9F03, 9C) leave out the cash back | 4 / 4 | 0 / 0 | [FIXED](./Maestro_Direct_Debit_Fix_Verification.md#d-18) |
| [D-19](../maestro_direct_debit/Manual%20Validation%20Report/Consolidated_Defect_Report.md#d-19) | Totals Request: Card Label (Element 13) is not 4 characters | 63 / 63 | 0 / 0 | [FIXED](./Maestro_Direct_Debit_Fix_Verification.md#d-19) |
| [D-20](../maestro_direct_debit/Manual%20Validation%20Report/Consolidated_Defect_Report.md#d-20) | Totals Request: Segment 105 length is counted with the wrong layout | 63 / 63 | 0 / 0 | [FIXED](./Maestro_Direct_Debit_Fix_Verification.md#d-20) |
| [D-21](../maestro_direct_debit/Manual%20Validation%20Report/Consolidated_Defect_Report.md#d-21) | Totals Request: the totals do not match the transactions of the flow | 14 / 14 | 12 / 12 | [NOT FIXED](./Maestro_Direct_Debit_Fix_Verification.md#d-21) |
| [D-24](../maestro_direct_debit/Manual%20Validation%20Report/Consolidated_Defect_Report.md#d-24) | A test data value differs from the test case it was generated from | 14 / 14 | 0 / 0 | [FIXED](./Maestro_Direct_Debit_Fix_Verification.md#d-24) |
| CD-19 | Traceability matrix points to a folder that is not delivered; follow-on legs never traced | 249 trace rows with a missing path; 287 files untraced | 0 trace rows with a missing path; 0 files untraced | FIXED |

Maestro-specific checks that pass in both runs: card type in the Prompt Code `011` (478 files) = Appendix E `011` Debit checking; chip application identifier (Tags 84 / 9F06) `A0000000043060` (104 files) = the Maestro application (previous run: `A0000000043060` (90 files)); totals card bucket `DB  ` (48 files) = Element 13 `DB` Debit; ATL105 lists Maestro as a supported EMV debit card (Section 10.14.2.3, p.161) and as authorizer 40 *Maestro® Direct Debit* (Appendix C, p.512).

---

## 5. Full defect register (all programs)

| Program | Defect | Source ID | What was wrong | Previous run TCs / files (log) | New run TCs / files failing | Status |
|---|---|---|---|---|---|---|
| American Express Credit | [CD-01](./AMEX_Credit_Fix_Verification.md#cd-01) | A-01 | EMV Card Sequence Number (Element 188) is 2 bytes instead of 3 | 94 / 116 (94 / 116) | 0 / 0 | **FIXED** |
| American Express Credit | [CD-02](./AMEX_Credit_Fix_Verification.md#cd-02) | A-02 | EMV Additional Information (Element 118) is not EMVYES / EMVNOT | 94 / 116 (94 / 116) | 0 / 0 | **FIXED** |
| American Express Credit | [CD-03](./AMEX_Credit_Fix_Verification.md#cd-03) | – | Table 030 (POS Condition Code) missing on EMV and Credential-on-File requests | 140 / 204 (140 / 204) | 0 / 0 | **FIXED – value awaiting client input** |
| American Express Credit | [CD-04](./AMEX_Credit_Fix_Verification.md#cd-04) | – | Credential-on-File indicators (Tables 032, 049, 056) missing | 46 / 88 (46 / 88) | 0 / 0 | **FIXED** |
| American Express Credit | [CD-05](./AMEX_Credit_Fix_Verification.md#cd-05) | A-03, A-03b | Chip fallback entry mode 79 (fallback to manual) sent with swiped Track 2 data | 44 / 54 (44 / 54) | 0 / 0 | **FIXED** |
| American Express Credit | [CD-06](./AMEX_Credit_Fix_Verification.md#cd-06) | – | Unmatched time-out reversal carries full track data | 5 / 5 (5 / 5) | 0 / 0 | **FIXED** |
| American Express Credit | [CD-07](./AMEX_Credit_Fix_Verification.md#cd-07) | A-06 | Runtime amount after a partial approval: impossible provisional value, Segment Length calculated from it | 31 / 31 (31 / 31) | 0 / 0 | **FIXED** |
| American Express Credit | [CD-08](./AMEX_Credit_Fix_Verification.md#cd-08) | A-07 | Split-tender card B chip amount (Tag 9F02) is the full original amount | 4 / 4 (4 / 4) | 0 / 0 | **FIXED** |
| American Express Credit | [CD-09](./AMEX_Credit_Fix_Verification.md#cd-09) | – | Product Code Data Segment (Segment 102) length 033, spec arithmetic gives 029 | 6 / 6 (6 / 6) | 0 / 0 | **FIXED** |
| American Express Credit | [CD-10](./AMEX_Credit_Fix_Verification.md#cd-10) | A-05 | Totals Card Label (Element 13) is 2 bytes instead of 4 | 72 / 72 (72 / 72) | 0 / 0 | **FIXED** |
| American Express Credit | [CD-11](./AMEX_Credit_Fix_Verification.md#cd-11) | – | Totals card-type category sent with count 00000 | 8 / 8 (8 / 8) | 0 / 0 | **FIXED** |
| American Express Credit | [CD-12](./AMEX_Credit_Fix_Verification.md#cd-12) | A-08 | Expired-card test uses a card that is not expired | 2 / 2 (2 / 2) | 0 / 0 | **FIXED** |
| American Express Credit | [CD-14](./AMEX_Credit_Fix_Verification.md#cd-14) | A-09 | Offline-PIN test has a signature CVM | 2 / 2 (2 / 2) | 0 / 0 | **FIXED** |
| American Express Credit | [CD-16](./AMEX_Credit_Fix_Verification.md#cd-16) | – | 'Track 1' test carries Track 2 data | 2 / 2 | 0 / 0 | **FIXED** |
| American Express Credit | [CD-17](./AMEX_Credit_Fix_Verification.md#cd-17) | A-10 | Terminal described two ways: Table 005 capability 8 with Table 031 12 | 2 / 2 (2 / 2) | 0 / 0 | **FIXED** |
| American Express Credit | [CD-18](./AMEX_Credit_Fix_Verification.md#cd-18) | – | AVS test sends no address data (Table 003) | 8 / 8 (8 / 8) | 0 / 0 | **FIXED – value awaiting client input** |
| American Express Credit | [CD-19](./AMEX_Credit_Fix_Verification.md#cd-19) | – | Traceability matrix points to a folder that is not delivered; follow-on legs never traced | 374 trace rows with a missing path; 416 files untraced | 0 trace rows with a missing path; 0 files untraced | **FIXED** |
| MasterCard Credit | [CD-01](./MasterCard_Credit_Fix_Verification.md#cd-01) | D-01 | EMV Card Sequence Number (Element 188) is 2 bytes instead of 3 | 104 / 126 (104 / 126) | 0 / 0 | **FIXED** |
| MasterCard Credit | [CD-02](./MasterCard_Credit_Fix_Verification.md#cd-02) | D-02 | EMV Additional Information (Element 118) is not EMVYES / EMVNOT | 104 / 126 (104 / 126) | 0 / 0 | **FIXED** |
| MasterCard Credit | [CD-03](./MasterCard_Credit_Fix_Verification.md#cd-03) | D-12 | Table 030 (POS Condition Code) missing on EMV and Credential-on-File requests | 158 / 223 (158 / 223) | 0 / 0 | **FIXED – value awaiting client input** |
| MasterCard Credit | [CD-04](./MasterCard_Credit_Fix_Verification.md#cd-04) | D-09 | Credential-on-File indicators (Tables 032, 049, 056) missing | 54 / 97 (54 / 97) | 0 / 0 | **FIXED – value awaiting client input** |
| MasterCard Credit | [CD-05](./MasterCard_Credit_Fix_Verification.md#cd-05) | D-08 | Chip fallback entry mode 79 (fallback to manual) sent with swiped Track 2 data | 47 / 57 (47 / 57) | 0 / 0 | **FIXED** |
| MasterCard Credit | [CD-06](./MasterCard_Credit_Fix_Verification.md#cd-06) | D-13 | Unmatched time-out reversal carries full track data | 5 / 5 (5 / 5) | 0 / 0 | **FIXED** |
| MasterCard Credit | [CD-07](./MasterCard_Credit_Fix_Verification.md#cd-07) | D-05, D-14 | Runtime amount after a partial approval: impossible provisional value, Segment Length calculated from it | 31 / 31 (31 / 31) | 0 / 0 | **FIXED** |
| MasterCard Credit | [CD-08](./MasterCard_Credit_Fix_Verification.md#cd-08) | D-05 | Split-tender card B chip amount (Tag 9F02) is the full original amount | 4 / 4 (4 / 4) | 0 / 0 | **FIXED** |
| MasterCard Credit | [CD-09](./MasterCard_Credit_Fix_Verification.md#cd-09) | S2 D-08 | Product Code Data Segment (Segment 102) length 033, spec arithmetic gives 029 | 6 / 6 (6 / 6) | 0 / 0 | **FIXED** |
| MasterCard Credit | [CD-10](./MasterCard_Credit_Fix_Verification.md#cd-10) | S2 D-06 | Totals Card Label (Element 13) is 2 bytes instead of 4 | 72 / 72 (72 / 72) | 0 / 0 | **FIXED** |
| MasterCard Credit | [CD-11](./MasterCard_Credit_Fix_Verification.md#cd-11) | S2 D-10 | Totals card-type category sent with count 00000 | 8 / 8 (8 / 8) | 0 / 0 | **FIXED** |
| MasterCard Credit | [CD-12](./MasterCard_Credit_Fix_Verification.md#cd-12) | D-03 | Expired-card test uses a card that is not expired | 2 / 2 (2 / 2) | 0 / 0 | **FIXED** |
| MasterCard Credit | [CD-13](./MasterCard_Credit_Fix_Verification.md#cd-13) | D-04 | Online-PIN test has no PIN block and a signature CVM | 4 / 4 (4 / 4) | 0 / 0 | **FIXED – value awaiting client input** |
| MasterCard Credit | [CD-14](./MasterCard_Credit_Fix_Verification.md#cd-14) | D-11 | Offline-PIN test has a signature CVM | 2 / 2 (2 / 2) | 0 / 0 | **FIXED** |
| MasterCard Credit | [CD-15](./MasterCard_Credit_Fix_Verification.md#cd-15) | S2 D-09 | Cashback test: chip data ignores the cash amount | 2 / 2 (2 / 2) | 0 / 0 | **FIXED** |
| MasterCard Credit | [CD-16](./MasterCard_Credit_Fix_Verification.md#cd-16) | D-07 | 'Track 1' test carries Track 2 data | 4 / 4 (4 / 4) | 0 / 0 | **FIXED** |
| MasterCard Credit | [CD-17](./MasterCard_Credit_Fix_Verification.md#cd-17) | D-06 | Terminal described two ways: Table 005 capability 8 with Table 031 12 | 2 / 2 (2 / 2) | 0 / 0 | **FIXED** |
| MasterCard Credit | [CD-18](./MasterCard_Credit_Fix_Verification.md#cd-18) | D-10 | AVS test sends no address data (Table 003) | 8 / 8 (8 / 8) | 0 / 0 | **FIXED – value awaiting client input** |
| MasterCard Credit | [CD-19](./MasterCard_Credit_Fix_Verification.md#cd-19) | S2 O31-01 | Traceability matrix points to a folder that is not delivered; follow-on legs never traced | 408 trace rows with a missing path; 431 files untraced | 0 trace rows with a missing path; 0 files untraced | **FIXED** |
| MasterCard Credit | [CD-20](./MasterCard_Credit_Fix_Verification.md#cd-20) | S2 O31-03 | MC Credit package mixes two builds | 450 TCs + archive with 241 TCs | 488 TCs, one build, MANIFEST | **FIXED** |
| MasterCard Fleet | [CD-01](./MasterCard_Fleet_Fix_Verification.md#cd-01) | FL-03 | EMV Card Sequence Number (Element 188) is 2 bytes instead of 3 | 60 / 78 (60 / 78) | 0 / 0 | **FIXED** |
| MasterCard Fleet | [CD-02](./MasterCard_Fleet_Fix_Verification.md#cd-02) | FL-04 | EMV Additional Information (Element 118) is not EMVYES / EMVNOT | 60 / 78 (60 / 78) | 0 / 0 | **FIXED** |
| MasterCard Fleet | [CD-03](./MasterCard_Fleet_Fix_Verification.md#cd-03) | – | Table 030 (POS Condition Code) missing on EMV and Credential-on-File requests | 90 / 139 (90 / 139) | 0 / 0 | **FIXED – value awaiting client input** |
| MasterCard Fleet | [CD-04](./MasterCard_Fleet_Fix_Verification.md#cd-04) | – | Credential-on-File indicators (Tables 032, 049, 056) missing | 30 / 61 (30 / 61) | 0 / 0 | **FIXED – value awaiting client input** |
| MasterCard Fleet | [CD-05](./MasterCard_Fleet_Fix_Verification.md#cd-05) | – | Chip fallback entry mode 79 (fallback to manual) sent with swiped Track 2 data | 30 / 39 (30 / 39) | 0 / 0 | **FIXED** |
| MasterCard Fleet | [CD-06](./MasterCard_Fleet_Fix_Verification.md#cd-06) | – | Unmatched time-out reversal carries full track data | 5 / 5 (5 / 5) | 0 / 0 | **FIXED** |
| MasterCard Fleet | [CD-09](./MasterCard_Fleet_Fix_Verification.md#cd-09) | FL-02 | Product Code Data Segment (Segment 102) length 033, spec arithmetic gives 029 | 249 / 497 (249 / 497) | 0 / 0 | **FIXED** |
| MasterCard Fleet | [CD-10](./MasterCard_Fleet_Fix_Verification.md#cd-10) | – | Totals Card Label (Element 13) is 2 bytes instead of 4 | 72 / 72 (72 / 72) | 0 / 0 | **FIXED** |
| MasterCard Fleet | [CD-11](./MasterCard_Fleet_Fix_Verification.md#cd-11) | – | Totals card-type category sent with count 00000 | 8 / 8 (8 / 8) | 0 / 0 | **FIXED** |
| MasterCard Fleet | [CD-16](./MasterCard_Fleet_Fix_Verification.md#cd-16) | – | 'Track 1' test carries Track 2 data | 2 / 2 (2 / 2) | 0 / 0 | **FIXED** |
| MasterCard Fleet | [CD-19](./MasterCard_Fleet_Fix_Verification.md#cd-19) | O31-01 | Traceability matrix points to a folder that is not delivered; follow-on legs never traced | 249 trace rows with a missing path; 320 files untraced | 0 trace rows with a missing path; 0 files untraced | **FIXED** |
| Interlink Debit | [D-01](./Interlink_Debit_Fix_Verification.md#d-01) | = CD-01 | EMV Card Sequence Number (Element 188) has the wrong width | 72 / 90 (72 / 90) | 0 / 0 | **FIXED** |
| Interlink Debit | [D-02](./Interlink_Debit_Fix_Verification.md#d-02) | = CD-02 | EMV Additional Information (Element 118) does not contain EMV Table Data | 72 / 90 (72 / 90) | 0 / 0 | **FIXED** |
| Interlink Debit | [D-09](./Interlink_Debit_Fix_Verification.md#d-09) | = CD-04 | Credential on File indicators (Tables 032, 049, 056) are missing | 35 / 67 (35 / 67) | 0 / 0 | **CLOSED – test cases removed** |
| Interlink Debit | [D-12](./Interlink_Debit_Fix_Verification.md#d-12) | = CD-03 | Table 030 (POS Condition Code) is missing on EMV and Credential on File transactions | 107 / 157 (107 / 157) | 0 / 0 | **FIXED – value awaiting client input** |
| Interlink Debit | [D-14](./Interlink_Debit_Fix_Verification.md#d-14) | = CD-07 | Follow-on step (completion, cancellation, reversal): amount data built from an impossible value | 7 / 7 (7 / 7) | 0 / 0 | **FIXED** |
| Interlink Debit | [D-15](./Interlink_Debit_Fix_Verification.md#d-15) | – | The card data (Account Number, Card Discretionary Block Data) is empty | 249 / 473 (249 / 473) | 0 / 0 | **FIXED – value awaiting client input** |
| Interlink Debit | [D-16](./Interlink_Debit_Fix_Verification.md#d-16) | – | The debit PIN block (Element 33) is empty | 242 / 305 (242 / 305) | 0 / 0 | **FIXED – value awaiting client input** |
| Interlink Debit | [D-17](./Interlink_Debit_Fix_Verification.md#d-17) | – | A keyed (manual) entry mode is used for a debit card | 34 / 43 (34 / 43) | 0 / 0 | **FIXED** |
| Interlink Debit | [D-18](./Interlink_Debit_Fix_Verification.md#d-18) | = CD-15 | Cash back: the chip amounts (Tags 9F02, 9F03, 9C) leave out the cash back | 4 / 4 (4 / 4) | 0 / 0 | **FIXED** |
| Interlink Debit | [D-19](./Interlink_Debit_Fix_Verification.md#d-19) | = CD-10 | Totals Request: Card Label (Element 13) is not 4 characters | 63 / 63 (63 / 63) | 0 / 0 | **FIXED** |
| Interlink Debit | [D-20](./Interlink_Debit_Fix_Verification.md#d-20) | – | Totals Request: Segment 105 length is counted with the wrong layout | 63 / 63 (63 / 63) | 0 / 0 | **FIXED** |
| Interlink Debit | [D-21](./Interlink_Debit_Fix_Verification.md#d-21) | – | Totals Request: the totals do not match the transactions of the flow | 14 / 14 (14 / 14) | 12 / 12 | **NOT FIXED** |
| Interlink Debit | [D-24](./Interlink_Debit_Fix_Verification.md#d-24) | – | A test data value differs from the test case it was generated from | 14 / 14 (14 / 14) | 0 / 0 | **FIXED** |
| Interlink Debit | [CD-19](./Interlink_Debit_Fix_Verification.md#cd-19) | – | Traceability matrix points to a folder that is not delivered; follow-on legs never traced | 249 trace rows with a missing path; 287 files untraced | 0 trace rows with a missing path; 0 files untraced | **FIXED** |
| Maestro Direct Debit | [D-01](./Maestro_Direct_Debit_Fix_Verification.md#d-01) | = CD-01 | EMV Card Sequence Number (Element 188) has the wrong width | 72 / 90 (missed) | 0 / 0 | **FIXED** |
| Maestro Direct Debit | [D-02](./Maestro_Direct_Debit_Fix_Verification.md#d-02) | = CD-02 | EMV Additional Information (Element 118) does not contain EMV Table Data | 72 / 90 (missed) | 0 / 0 | **FIXED** |
| Maestro Direct Debit | [D-09](./Maestro_Direct_Debit_Fix_Verification.md#d-09) | = CD-04 | Credential on File indicators (Tables 032, 049, 056) are missing | 35 / 67 (missed) | 0 / 0 | **CLOSED – test cases removed** |
| Maestro Direct Debit | [D-12](./Maestro_Direct_Debit_Fix_Verification.md#d-12) | = CD-03 | Table 030 (POS Condition Code) is missing on EMV and Credential on File transactions | 107 / 157 (missed) | 0 / 0 | **FIXED – value awaiting client input** |
| Maestro Direct Debit | [D-14](./Maestro_Direct_Debit_Fix_Verification.md#d-14) | = CD-07 | Follow-on step (completion, cancellation, reversal): amount data built from an impossible value | 7 / 7 (missed) | 0 / 0 | **FIXED** |
| Maestro Direct Debit | [D-15](./Maestro_Direct_Debit_Fix_Verification.md#d-15) | – | The card data (Account Number, Card Discretionary Block Data) is empty | 249 / 473 (missed) | 0 / 0 | **FIXED – value awaiting client input** |
| Maestro Direct Debit | [D-16](./Maestro_Direct_Debit_Fix_Verification.md#d-16) | – | The debit PIN block (Element 33) is empty | 242 / 305 (missed) | 0 / 0 | **FIXED – value awaiting client input** |
| Maestro Direct Debit | [D-17](./Maestro_Direct_Debit_Fix_Verification.md#d-17) | – | A keyed (manual) entry mode is used for a debit card | 34 / 43 (missed) | 0 / 0 | **FIXED** |
| Maestro Direct Debit | [D-18](./Maestro_Direct_Debit_Fix_Verification.md#d-18) | = CD-15 | Cash back: the chip amounts (Tags 9F02, 9F03, 9C) leave out the cash back | 4 / 4 (missed) | 0 / 0 | **FIXED** |
| Maestro Direct Debit | [D-19](./Maestro_Direct_Debit_Fix_Verification.md#d-19) | = CD-10 | Totals Request: Card Label (Element 13) is not 4 characters | 63 / 63 (missed) | 0 / 0 | **FIXED** |
| Maestro Direct Debit | [D-20](./Maestro_Direct_Debit_Fix_Verification.md#d-20) | – | Totals Request: Segment 105 length is counted with the wrong layout | 63 / 63 (missed) | 0 / 0 | **FIXED** |
| Maestro Direct Debit | [D-21](./Maestro_Direct_Debit_Fix_Verification.md#d-21) | – | Totals Request: the totals do not match the transactions of the flow | 14 / 14 (missed) | 12 / 12 | **NOT FIXED** |
| Maestro Direct Debit | [D-24](./Maestro_Direct_Debit_Fix_Verification.md#d-24) | – | A test data value differs from the test case it was generated from | 14 / 14 (missed) | 0 / 0 | **FIXED** |
| Maestro Direct Debit | [CD-19](./Maestro_Direct_Debit_Fix_Verification.md#cd-19) | – | Traceability matrix points to a folder that is not delivered; follow-on legs never traced | 249 trace rows with a missing path; 287 files untraced | 0 trace rows with a missing path; 0 files untraced | **FIXED** |

---

## 6. Review items of the previous logs (not defects)

| Program | ID | Item | Previous run | New run | Result |
|---|---|---|---|---|---|
| American Express Credit | RV-01 | Keyed / Credential-on-File follow-on expiry sent as YYMM while the original is MMYY | YYMM: 70 | YYMM: 84 | Unchanged – follow-ons still send YYMM (still a review item for BUYPASS / SME) |
| MasterCard Credit | RV-01 | Keyed / Credential-on-File follow-on expiry sent as YYMM while the original is MMYY | YYMM: 72 | YYMM: 86 | Unchanged – follow-ons still send YYMM (still a review item for BUYPASS / SME) |
| MasterCard Fleet | RV-01 | Keyed / Credential-on-File follow-on expiry sent as YYMM while the original is MMYY | YYMM: 44 | YYMM: 48 | Unchanged – follow-ons still send YYMM (still a review item for BUYPASS / SME) |
| MasterCard Fleet | RV-02 | Fleet Data Segment (Segment 101) length 035 (021 / 030 / 035 depend on the spec reading) | 035: 492, 031: 5 | 035: 556, 031: 5 | Unchanged – still a review item for SME / BUYPASS |

The other review items of the consolidated log (RV-03 – RV-22: framing of Element 118, placeholder terminal, sample CA checksum and cryptograms, citations, contactless chip data, …) are questions for BUYPASS / SME or the client and were not re-checked.

---

## 7. How to check any result by hand

1. Open the program report linked in §1 and go to the defect (the links in §5 jump there).
2. Its *Check it yourself* row gives the previous file and line and the new file and line with the value found; the table below it lists every previously failing file with its new file, line, value and status.
3. New-run files link directly into `5-11-out/<program>/test_data/`. Previous-run files of MasterCard Credit, Interlink Debit and Maestro Direct Debit link to identical workspace copies (`MasterCard Credit/mastercard_credit/`, `interlink_debit/`, `maestro_direct_debit/`); AMEX Credit and MasterCard Fleet previous files are in `C:/Users/F7OKAGW/Downloads/31-10-out`.
4. Test case IDs changed between the runs: the *TC mapping* sheet of `Defect_Fix_Verification.xlsx` gives the new ID of every previous test case.

Re-run (from `regression-artifact-validator`):

```powershell
$env:PYTHONUTF8 = '1'
python scripts/verify-defect-fixes.py
python scripts/build-entry-mode-validation-report.py --data maestro_direct_debit --all   # Maestro previous-run report
```

