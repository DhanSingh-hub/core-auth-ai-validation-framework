# MasterCard Credit run set 20-11: defects to discuss with the BA and the dev team

## Purpose and scope

This is the BA / dev discussion copy of the full [defect demo sheet](MasterCard_Credit_Defect_Demo_Sheet.md) of the run set `Dhan_Singh_run_set_20-11` (main MasterCard Credit run, built 19 Nov 2026). It keeps only the defects that need a decision or a fix from the BA and the dev team: data field validation (format, length, valid values, required and conditional data, consistency between the steps of a flow), test case design and the ATL105 citations. **22 of the 34 defects of the full sheet are kept; 12 are [filtered out](#filtered-out)**, together with the placeholder part of two others, because they are about values that are not defects of the test data:

- **Run-time values of multi-leg flows** - a follow-on step (reversal, time-out reversal, cancellation, completion) carries values of the earlier step, such as the Approval Number of the step 1 response or the card of the original; they are filled in at run time, and the Segment Length is recalculated after the substitution.
- **Client values** - `<AWAITING:...>` placeholders the client still supplies: test card, AVS data, Variable Information tables, PIN block, decline trigger.
- **Environment values, not validated as hard-coded test data** - the Terminal Identifier and its State Code (generated placeholder, made-up or 'different' terminal), the device hardware / software / firmware versions, the EMV cryptogram, counters and CA Public Key checksum, and synthesized network-token and loyalty content.

The kept defect sections, their evidence, the ATL105 quotes and the test data are those of the full sheet; the per-test-case findings and the expected test data show only the kept defects. Five evidence references that pointed to the wrong line of the test data were corrected ([how this sheet was produced](#how-filtered)). Every finding was produced by the ATL105 validator from the test case definitions and their test data files, and every ATL105 text quoted here was found word for word on its PDF page.

| Item | Value |
|---|---|
| Run | [Dhan_Singh_run_set_20-11](../Dhan_Singh_run_set_20-11/) - MasterCard Credit run of 19 Nov 2026, build `buypass-atl105-mastercard-credit-generated-42c2d5f6ba` (only this run was used; its files were checked against the run's own MANIFEST.json - see the appendix) |
| Specification | BUYPASS Platform ATL105 Message Format Specifications, release 2026-4 (October 09, 2026) - the attached `BUYPASS_Platform_ATL105_Message_Format_Specifications_2026-4 (1).pdf` |
| Test cases | [TC-076166](#tc-076166), [TC-076167](#tc-076167), [TC-076168](#tc-076168), [TC-076658](#tc-076658), [TC-075618](#tc-075618), [TC-075654](#tc-075654), [TC-075699](#tc-075699), [TC-075657](#tc-075657), [TC-076586](#tc-076586), [TC-075650](#tc-075650), [TC-075755](#tc-075755), [TC-075656](#tc-075656), [TC-075757](#tc-075757), [TC-075685](#tc-075685), [TC-075670](#tc-075670), [TC-077181](#tc-077181), [TC-077186](#tc-077186), [TC-077193](#tc-077193), [TC-077219](#tc-077219), [TC-076596](#tc-076596), [TC-076613](#tc-076613), [TC-075751](#tc-075751), [TC-075667](#tc-075667), [TC-076423](#tc-076423), [TC-077185](#tc-077185), [TC-077206](#tc-077206), [TC-077218](#tc-077218), [TC-076725](#tc-076725), [TC-076723](#tc-076723), [TC-075601](#tc-075601), [TC-075810](#tc-075810), [TC-075855](#tc-075855), [TC-076597](#tc-076597), [TC-076780](#tc-076780), [TC-077179](#tc-077179), [TC-077203](#tc-077203) |
| AI result (this sheet) | FAIL 21 · REVIEW 15 (full sheet: FAIL 23 · BLOCKED 14) |
| Readiness | 15 `ready`, 12 `requires_review`, 7 `needs_client_input`, 1 `blocked_verification`, 1 `blocked_no_test_data` |
| Findings in the run | 1,202 of the 1,489 test cases of the run with a finding have at least one finding to discuss (683 with a data field or test case defect, the others only citation issues); 287 have only filtered-out findings. Findings: 2,185 kept, 4,185 filtered out (`Defects_in_the_run.csv`). |

- **TC** = test case: what the test says it will exercise (`test_cases.jsonl`, one test case per line).
- **TD** = test data: the request prepared for transmission to the host (`test_data/TD-TC-....json`).
- **DS** = data segment (Segment 100, 111, ...); **on the wire** = actually included in the transmitted request.
- **Element** = ATL105 data element (Chapter 13); **Table** = Variable Information table in Segment 111 (Appendix I).
- **FAIL** = must be fixed; **REVIEW** = to correct or to confirm with BUYPASS. The full sheet's **BLOCKED** (waiting only for a client or run-time value) does not occur here - those findings are filtered out.

These are composition defects in the delivered test cases and test data, found by reading them against ATL105 - not observed production failures. No host transactions were executed for this review.

## What changed since the 16-11 run

The same validator (same ATL105 rules, same release 2026-4 text) was run on the 16-11 run
(`DhanSingh_mastercard_credit_output_16_11/mastercard_credit_new_16_11`, 2,129 test cases, 2,422 test data files:
FAIL 245 · BLOCKED 1,748 · DEVICE 136) and on the main MasterCard Credit run of the 20-11 run set (1,623 test cases, 2,010 test data files:
FAIL 296 · BLOCKED 1,184 · DEVICE 143), so the counts below can be compared. The 20-11 run is a regenerated set: test case ids, names and scenario numbers
changed, so the logged test cases were matched by their scenario and objective.

### Defects the BA logged for the 16-11 run

| Logged defect (16-11 run) | Test cases logged | What the 20-11 run does | Status | Evidence |
|---|---:|---|---|---|
| Table 048 MasterCard Authentication Data claimed but not sent (C-10, AI-team register N17-01) | 11 | The 8 tests "Purchase/Capture sending Table 048" send Table 048 = `1` (length 001, Program Protocol 1 = EMV 3-D Secure 2.1) - the documented format | **Fixed.** New point to confirm: ATL105 applies Table 048 to e-commerce 3-D Secure transactions and these are POS purchases (C-12 below) | [TC-075618](#tc-075618) |
| Tax Type 1 = GST claimed but not sent (C-10, BA review 19-11-2026) | 8 | 8 tests, now "Purchase/Capture with Tax Type 1 = GST" (7 of them `ready`), still send no Product Code (102) or Tax by Product (143) segment - only Segments 100 and 111 (+ 130 for chip) | **Still open** | [TC-075654](#tc-075654) |
| Approval Number sent on an original Authorization Only (D-02, AI-team register N17-03) | 8 | No original Authorization Only carries an Approval Number; the validator finds no D-02 in the whole run (16-11: 28 test cases) | **Fixed** | [TC-076586](#tc-076586) |
| AVS information expected, but the request sends no Table 003 (C-11, BA review 19-11-2026) | 10 | The AVS tests send Table 003 as `<AWAITING:...>` client data, list it in `awaiting_inputs`, are `needs_client_input` and expect Response Code 4 (Purchase/Capture with AVS) or 3 (Authorization only with AVS); no C-11 in the run (16-11: 42) | **Fixed** - waits for the client's AVS test card data | [TC-075699](#tc-075699), [TC-075657](#tc-075657) |
| Segment 108 left out although not required (C-02, TC-038607, BA review 19-11-2026) | 1 | That test is no longer generated. 21 other negative tests still claim a rule ATL105 does not state (16-11: 54) | **Fixed for Segment 108**; the defect type remains (C-02) | [TC-075650](#tc-075650) |

### Every defect kind - 16-11 run against 20-11 main run (test cases with the finding)

| Id | Defect | Kind | 16-11 (2,129 TCs) | 20-11 (1,623 TCs) | Change | In this sheet |
|---|---|---|---:|---:|---|---|
| D-01 | Totals Request: card bucket count/amount does not match the transactions of the flow | Test data defect | 28 | 38 | more | discussed - [Defect 6](#d6) |
| D-02 | Optional Segment 100 fields filled with invalid values or with values whose conditions do not apply | Test data defect | 28 | 0 | **fixed** | - (none in the 20-11 run) |
| D-03 | Segment 145 (Enhanced Fleet Request) is not valid: length indicator over 3 digits, segment over its maximum and data not in <tag><len><data> format | Test data defect | 9 | 0 | **fixed** | - (none in the 20-11 run) |
| D-09 | A negative test breaks more than the one rule it is meant to break | Test data defect | 4 | 87 | more | partly - [Defect 4](#d4) (a second rule broken, 8); filtered out: made-up Terminal Identifier (31), follow-on waiting for another card (48) |
| D-11 | Segment 100 / 111 / 130 / 105 value breaks an ATL105 rule (trained validator) | Test data defect | 1 | 3 | more | discussed - [Defect 9](#d9) |
| D-12 | Another data segment is longer than its ATL105 maximum or breaks its TLV layout | Test data defect | 1 | 0 | **fixed** | - (none in the 20-11 run) |
| D-13 | Card read from the chip / stripe, but Card Discretionary Block Data holds only an MMYY expiry instead of the Track 2 data after the '=' | Test data defect | 37 | 39 | more | discussed - [Defect 5](#d5) |
| D-15 | Positive boundary test: the value is not at the documented limit or is not a valid value | Test data defect | 1 | 2 | more | partly - [Defect 10](#d10) (invalid value, 1); filtered out: length measured on an `<AWAITING:...>` value (1) |
| D-16 | Follow-on step sends the keyed card's MMYY expiry instead of YYMM | Test data defect | 0 | 122 | **new** | discussed - [Defect 3](#d3) |
| C-01 | Same request as another test case but a different expected result | Test case defect | 38 | 0 | **fixed** | - (none in the 20-11 run) |
| C-02 | Negative test premise contradicts ATL105 (the rule it claims to break does not exist) | Test case defect | 54 | 21 | fewer | discussed - [Defect 7](#d7) |
| C-10 | Positive test does not send the condition it claims (table, segment or field value) | Test case defect | 33 | 16 | fewer | discussed - [Defect 1](#d1), [Defect 2](#d2) |
| C-11 | Positive test expects response information that its request does not ask for | Test case defect | 42 | 0 | **fixed** | - (none in the 20-11 run) |
| C-03 | Test case marked 'ready' although its Terminal Identifier is a placeholder | Test case issue to correct | 619 | 289 | fewer | filtered out - placeholder Terminal Identifier |
| C-04 | Expected Response Code not stated for a step, or several codes accepted | Test case issue to correct | 252 | 256 | more | discussed - [Defect 17](#d17) |
| C-05 | Test objective / name does not match the message sent or the expected result | Test case issue to correct | 15 | 2 | fewer | discussed - [Defect 13](#d13) |
| Q-01 | Quote not found in ATL105 or missing (generator note or other text cited as ATL105) | Citation issue | 114 | 136 | more | discussed - [Defect 22](#d22) |
| Q-02 | Quote is on a different page than cited | Citation issue | 622 | 387 | fewer | discussed - [Defect 22](#d22) |
| Q-03 | Quote is not word-for-word | Citation issue | 499 | 424 | fewer | discussed - [Defect 22](#d22) |
| Q-04 | Citation or device checkpoint from an unrelated section or document | Citation issue | 108 | 40 | fewer | discussed - [Defect 22](#d22) |
| I-01 | Terminal Identifier is a generated placeholder | Awaiting client input / run-time value | 1,990 | 1,478 | fewer | filtered out - Terminal Identifier / State Code (environment value) |
| I-02 | Value filled in at run time from the previous response (expected for follow-on steps) | Awaiting client input / run-time value | 226 | 401 | more | filtered out - run-time value from the previous response |
| I-03 | Variable Information table value awaited from the client (Segment 111 length to recalculate) | Awaiting client input / run-time value | 62 | 362 | more | filtered out - client value (`<AWAITING:...>`) |
| I-04 | EMV chip data is generated, not from a card / simulator | Awaiting client input / run-time value | 607 | 384 | fewer | filtered out - generated EMV data (card / simulator at execution) |
| I-05 | Totals Request device versions are generated placeholders | Awaiting client input / run-time value | 67 | 84 | more | filtered out - device versions (environment value) |
| I-06 | Optional segment content is synthesized - real client data needed before an approval can be expected | Awaiting client input / run-time value | 65 | 56 | fewer | filtered out - synthesized content (client data at execution) |
| I-07 | Decline trigger needed: the request is identical to the approval test | Awaiting client input / run-time value | 73 | 21 | fewer | filtered out - decline trigger from the client |
| I-08 | No test data generated for a host test case | Awaiting client input / run-time value | 3 | 2 | fewer | filtered out - waiting for the missing input |
| I-09 | Segment 100 value awaited from the client (<AWAITING:...>) | Awaiting client input / run-time value | 1 | 163 | more | filtered out - client value (`<AWAITING:...>`) |
| I-10 | Claimed table not in the test data yet - the test case lists it as client data to supply | Awaiting client input / run-time value | 398 | 0 | **fixed** | - (none in the 20-11 run) |
| C-06 | Time-out Reversal sent after a step that is expected to be answered | To confirm with BUYPASS / SME | 62 | 86 | more | discussed - [Defect 8](#d8) |
| C-07 | 'Different terminal' negative uses a generated terminal that is not set up at BUYPASS | To confirm with BUYPASS / SME | 39 | 49 | more | filtered out - generated Terminal Identifier |
| C-08 | Same request as another test case (difference only on the device / response side) | To confirm with BUYPASS / SME | 584 | 124 | fewer | discussed - [Defect 18](#d18) |
| C-09 | Segment sent although ATL105 scopes it to other card programs | To confirm with BUYPASS / SME | 23 | 2 | fewer | discussed - [Defect 21](#d21) |
| C-12 | Table 048 (MasterCard 3-D Secure authentication data) sent on a POS transaction | To confirm with BUYPASS / SME | 0 | 8 | **new** | discussed - [Defect 11](#d11) |
| S-01 | Expiry format for stored credentials (entry mode 10) not stated | To confirm with BUYPASS / SME | 196 | 172 | fewer | discussed - [Defect 15](#d15) |
| S-02 | Totals: Authorization Only (AO) bucket after the flow | To confirm with BUYPASS / SME | 10 | 20 | more | discussed - [Defect 19](#d19) |
| S-03 | Segment length: ATL105 Field Separator rule of the segment is unclear | To confirm with BUYPASS / SME | 53 | 51 | fewer | discussed - [Defect 14](#d14) |
| S-04 | Network Token Input Indicator 'Y' while Element 2 carries a clear card number | To confirm with BUYPASS / SME | 26 | 26 | same | discussed - [Defect 14](#d14) |
| S-05 | Other points to confirm | To confirm with BUYPASS / SME | 15 | 25 | more | discussed - [Defect 12](#d12), [Defect 16](#d16) |
| S-06 | Totals after a Merchandise Return: the net amount is negative, but ATL105 defines no sign for the totals amounts | To confirm with BUYPASS / SME | 8 | 8 | same | discussed - [Defect 20](#d20) |

New in the 20-11 run: **D-16** (follow-on steps copy the keyed MMYY expiry), the two new **D-09** patterns (issuer-decline
tests that wait for another card in the follow-on step; "no original" tests with a made-up terminal), **C-10** for
Variable Information Indicator = 001 and **C-12** (Table 048 on POS purchases). Gone: C-01 (same request, different
expected result), C-11 (AVS/CVV response not initiated), D-02 (fields whose condition does not apply), D-03 (Segment 145),
I-10 (claimed table awaited without placeholder).

In this sheet the two new D-09 patterns are filtered out: one rests on an awaited follow-on card (placeholder), the other on the Terminal Identifier - see [Filtered out](#filtered-out).

## Defects in this sheet

**In the run** = test cases of the whole run (1,623) with the defect; **shown** = test cases of this sheet; **full sheet #** = number of the defect in the full sheet. Each defect section lists every affected test case of the run.

| Defect | In the run | Shown | Kind | Defect id | Priority | Full sheet # |
|---|---:|---:|---|---|---|---:|
| [1. Tax Type 1 = GST is claimed by the test but not sent in the request](#d1) | 8 | 2 | Test case defect | C-10 (BA review 19-11-2026 (GST) - still open, now on Purchase/Capture) | High | 1 |
| [2. Variable Information Indicator = 001 is claimed by the test but not sent in the request](#d2) | 8 | 2 | Test case defect | C-10 (new in the 20-11 run) | High | 2 |
| [3. Follow-on steps send the keyed card's expiry as MMYY instead of YYMM](#d3) | 122 | 3 | Test data defect | D-16 (new in the 20-11 run (0 test cases in 16-11)) | High | 3 |
| [4. Other negative tests that break a second rule besides the one under test](#d4) | 8 | 4 | Test data defect | D-09 | High | 6 |
| [5. Card read from the chip or stripe, but the card data is only an MMYY expiry](#d5) | 39 | 7 | Test data defect | D-13 (also in 16-11 (37 test cases)) | High | 7 |
| [6. Totals Request does not match the transactions of the flow](#d6) | 38 | 3 | Test data defect | D-01 (also in 16-11 (28 test cases)) | High | 8 |
| [7. Negative tests whose premise is not in ATL105 - the expected decline is not justified](#d7) | 21 | 2 | Test case defect | C-02 (BA review 19-11-2026 (Segment 108) - premise defect still in other tests) | High | 9 |
| [8. Time-out Reversal sent although the original request is expected to be answered](#d8) | 86 | 2 | To confirm with BUYPASS / SME | C-06 (also in 16-11 (62 test cases)) | High | 11 |
| [9. Segment 100 / 111 / 130 / 105 value breaks an ATL105 rule (trained validator)](#d9) | 3 | 2 | Test data defect | D-11 | High | 12 |
| [10. Positive boundary test: the value is not at the documented limit or is not a valid value](#d10) | 1 | 1 | Test data defect | D-15 | High | 13 |
| [11. Table 048 (3-D Secure authentication data) is now sent, but on POS purchases](#d11) | 8 | 1 | To confirm with BUYPASS / SME | C-12 (BA review 16/19-11-2026 (Table 048 not sent) - fixed: now sent; scope to confirm) | Medium | 14 |
| [12. Mail/Phone Authorization Only completed with Transaction Type 6 - the code is to be confirmed](#d12) | 10 | 1 | To confirm with BUYPASS / SME | S-05 | Medium | 17 |
| [13. The test objective names a different transaction than the one sent](#d13) | 2 | 1 | Test case issue to correct | C-05 | Medium | 18 |
| [14. Network Token Input Indicator and the Segment 153 / 108 lengths need confirmation](#d14) | 51 | 5 | To confirm with BUYPASS / SME | S-03, S-04 | Medium | 19 |
| [15. Expiry format for stored credentials (entry mode 10) not stated](#d15) | 172 | 2 | To confirm with BUYPASS / SME | S-01 | Medium | 23 |
| [16. Other points to confirm](#d16) | 15 | 2 | To confirm with BUYPASS / SME | S-05 | Medium | 24 |
| [17. Expected Response Code not stated for a step, or several codes accepted](#d17) | 256 | 6 | Test case issue to correct | C-04 | Medium | 25 |
| [18. Same request as another test case (difference only on the device / response side)](#d18) | 124 | 1 | To confirm with BUYPASS / SME | C-08 | Medium | 28 |
| [19. Totals: Authorization Only (AO) bucket after the flow](#d19) | 20 | 1 | To confirm with BUYPASS / SME | S-02 | Medium | 30 |
| [20. Totals after a Merchandise Return: the net amount is negative, but ATL105 defines no sign for the totals amounts](#d20) | 8 | 1 | To confirm with BUYPASS / SME | S-06 | Medium | 31 |
| [21. Segment sent although ATL105 scopes it to other card programs](#d21) | 2 | 1 | To confirm with BUYPASS / SME | C-09 | Medium | 33 |
| [22. ATL105 citations in the test cases are on the wrong page, not ATL105 text, or about another context](#d22) | 831 | 33 | Citation issue | Q-02, Q-01, Q-04, Q-03 | Low | 34 |

<a id="filtered-out"></a>

## Filtered out - placeholders, run-time and environment values

These defects of the full sheet are not raised with the BA and the dev team. Each finding is about a value that is filled in at run time, supplied by the client or set by the test environment - not about a format or content rule of the test data. The values are still needed before the tests can run, so they remain execution inputs to collect from the client / BUYPASS, not defects. The full sheet keeps their details.

| Full sheet # | Defect | Defect id | Test cases in the run | Why it is filtered out |
|---|---|---|---:|---|
| [4](MasterCard_Credit_Defect_Demo_Sheet.md#d4) | Issuer-decline tests of a follow-on transaction wait for a different card in the follow-on step only | D-09 | 48 | The follow-on step holds `<AWAITING:AccountNumber>` / `<AWAITING:CardDiscretionaryBlockData>`. In a multi-leg flow the card of the original step is carried forward into the follow-on at run time; the finding only compares that placeholder with step 1. |
| [5](MasterCard_Credit_Defect_Demo_Sheet.md#d5) | 'No original' negative tests use a made-up, invalid Terminal Identifier | D-09 | 31 | The finding is the Terminal Identifier and its State Code - an environment value assigned and set up by BUYPASS, not validated as hard-coded test data. |
| [10](MasterCard_Credit_Defect_Demo_Sheet.md#d10) | The Terminal Identifier is a generated placeholder, yet the test cases are marked ready | I-01, C-03 | 1,478 | Generated placeholder Terminal Identifier (and its State Code), and the 'ready' status questioned only because of it - the real terminal is supplied for execution. |
| [13](MasterCard_Credit_Defect_Demo_Sheet.md#d13) (part) | Positive boundary test: Encrypted PIN Block Data not at its documented maximum length | D-15 | 1 | The value is the client placeholder `<AWAITING:EncryptedPINBlockData>`; the length measured is the placeholder's, not the test's. The Information Byte finding of this defect is kept. |
| [15](MasterCard_Credit_Defect_Demo_Sheet.md#d15) | AVS tests now send Table 003 and wait for the client's AVS card data | I-03 | 29 | The AVS data of Table 003 is a client value (`<AWAITING:...>`); the table and Segment 111 lengths are recalculated when it is filled in. |
| [16](MasterCard_Credit_Defect_Demo_Sheet.md#d16) | 'Different terminal' negative tests use a terminal BUYPASS does not know | C-07 | 49 | The finding is the generated Terminal Identifier of the 'different terminal' step - an environment value. |
| [19](MasterCard_Credit_Defect_Demo_Sheet.md#d19) (part) | Network token and loyalty segment content is synthesized | I-06 | 56 | Generated sample content; the real provisioned network token and loyalty card data are supplied for execution. The indicator and segment length findings of this defect are kept. |
| [20](MasterCard_Credit_Defect_Demo_Sheet.md#d20) | Follow-on values are instruction text to be filled in from the previous response | I-02 | 401 | Approval Number (and the Segment Length that depends on it) carried forward at run time from the previous step's response - expected in every multi-leg flow. |
| [21](MasterCard_Credit_Defect_Demo_Sheet.md#d21) | EMV chip data is generated, not captured from a card or simulator | I-04 | 384 | Cryptogram, ATC, unpredictable number, TVR / IAD and CA Public Key checksum come from the card, simulator or key load at execution - not validated as hard-coded values. |
| [22](MasterCard_Credit_Defect_Demo_Sheet.md#d22) | Segment 100 value awaited from the client (<AWAITING:...>) | I-09 | 163 | Segment 100 value the client still supplies (`<AWAITING:...>`). |
| [26](MasterCard_Credit_Defect_Demo_Sheet.md#d26) | Totals Request device versions are generated placeholders | I-05 | 84 | Hardware, software and firmware versions of the device that runs the test - environment values. |
| [27](MasterCard_Credit_Defect_Demo_Sheet.md#d27) | Variable Information table value awaited from the client (Segment 111 length to recalculate) | I-03 | 333 | Variable Information table value the client still supplies (`<AWAITING:...>`); the lengths are recalculated when it is filled in. |
| [29](MasterCard_Credit_Defect_Demo_Sheet.md#d29) | Decline trigger needed: the request is identical to the approval test | I-07 | 21 | The decline trigger (test card or amount) is a client value and is already listed in the test case's open items. |
| [32](MasterCard_Credit_Defect_Demo_Sheet.md#d32) | No test data generated for a host test case | I-08 | 2 | Waiting for the missing input - there is no test data to validate yet. |

Not a reason to filter: a value that is present but breaks a format or content rule stays in the sheet even when it sits in a follow-on step - for example the keyed expiry sent as MMYY instead of YYMM ([Defect 3](#d3)) or the totals that do not add up to the flow ([Defect 6](#d6)).

<a id="d1"></a>

## Defect 1: Tax Type 1 = GST is claimed by the test but not sent in the request

**Priority:** High · **Kind:** Test case defect · **Test cases:** 2 shown (8 in the run) · **Defect id:** C-10 (BA review 19-11-2026 (GST) - still open, now on Purchase/Capture)

<details><summary>All 8 test cases of the run with this defect (click to expand)</summary>

[TC-075654](#tc-075654), [TC-075755](#tc-075755), [TC-075857](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L258), [TC-076075](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L476), [TC-076173](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L574), [TC-076278](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L679), [TC-076383](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L784), [TC-076492](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L893)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-075654](#tc-075654) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L55)) | `ready` | EMV Contact Chip Purchase/Capture with Tax Type 1 = GST → approved | Decision table: Purchase/Capture with Tax Type 1 = GST (the rule's condition holds) | [TD-TC-075654.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075654.json) |
| [TC-075755](#tc-075755) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L156)) | `ready` | EMV Contactless Chip Purchase/Capture with Tax Type 1 = GST → approved | Decision table: Purchase/Capture with Tax Type 1 = GST (the rule's condition holds) | [TD-TC-075755.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075755.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-075654 | 1 | [TD-TC-075654.json line 2](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075654.json#L2) → `EMV Financial Transaction Request` | `Tax Type 1: not in the test data; segments sent: 100, 111, 130` | The test claims Tax Type 1 = GST but the request does not send it |
| TC-075755 | 1 | [TD-TC-075755.json line 2](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075755.json#L2) → `EMV Financial Transaction Request` | `Tax Type 1: not in the test data; segments sent: 100, 111, 130` | The test claims Tax Type 1 = GST but the request does not send it |

### Expected behavior

- Send Segment 102 (Product Code) and Segment 143 (Tax by Product) with the same product(s) in the same order, the inclusive/exclusive flag (I or E), Tax Type 1 = GST and the tax amount; recalculate the segment count and lengths. GST is a Canadian tax type and Segment 143 is sent only if the account manager instructs it (Section 11.1), so confirm the merchant context - or re-scope / withdraw the test.

### Why this is a defect

- The objective "Decision table: Purchase/Capture with Tax Type 1 = GST (the rule's condition holds)" says the request carries Tax Type 1 = GST, but the test data does not contain it and the test case names no missing input (status `ready`). An approval would only prove that the request without Tax Type 1 = GST is approved - not that Tax Type 1 = GST is handled.

**BA explanation:** "The test says it sends Tax Type 1 = GST, but the request does not contain it. A pass cannot be counted as coverage of Tax Type 1 = GST."

### Acceptance criteria

- The request carries the claimed data with the segments it depends on, and the test asserts it - or the test is re-scoped or withdrawn with the reason written down.
- Missing client data is named as an open item and the test is not marked 'ready'.
- A regression check fails a positive test whose claimed table, segment or value is not in its request.

**Specification reference (ATL105, word for word):**

- Section 12.30 Tax by Product Data Segment, PDF p.285 (printed 12-68): "A Product Code Data Segment (Data Segment No. 102) must be present in the transaction." ([ATL105 text line 16612](../specifications/ATL105/docs/specs/extracted_text_new.txt#L16612))
- Section 12.30 Tax by Product Data Segment, PDF p.285 (printed 12-68): "Each entry in the Product Code Data Segment (Data Segment No. 102) should have an entry in this segment too and the entries should be in the same order in both the segments." ([ATL105 text line 16613](../specifications/ATL105/docs/specs/extracted_text_new.txt#L16613))
- Section 12.30 Tax by Product Data Segment, PDF p.285 (printed 12-68): "For Canadian transactions, the valid values for Tax Type are GST (Goods and Services Tax), HST (Harmonized Sales Tax), and PST (Provincial State Tax)." ([ATL105 text line 16623](../specifications/ATL105/docs/specs/extracted_text_new.txt#L16623))
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "143 Tax by Product Data Segment 399 C Contains tax data for each product included in segment 102. This segment has limited applicability and should only be included if instructed by your account manager." ([ATL105 text line 8001](../specifications/ATL105/docs/specs/extracted_text_new.txt#L8001))

**Test data excerpt - [TC-075654](#tc-075654), step 1, `TD-TC-075654.json` (selected fields, actual then expected):**

```jsonc
// actual - line 4
"NumberofSegments": "03",
// expected
"NumberofSegments": "05",  // C-10: + Segments 102 and 143
// actual - not in the request
// expected
"Product Code Data Segment": {  // C-10: Segment 143 needs Segment 102
  "SegmentType": "102",
  "SegmentLength": "029",  // whole segment: type, length, 3 Field Separators and the data with its 2 '\' separators
  "ServiceLevel": "O",  // O = Other/Fuel not present (Element 87)
  "NumberofProducts": "01",
  "ProductCode": "400",  // 400 = General merchandise (Appendix F) - client to confirm
  "UnitofMeasure": "U",  // U = Unit (Element 106)
  "Quantity": "01",  // 1 (leading digit = decimal places)
  "UnitPrice": "21500",  // leading 2 = two decimal places
  "ProductAmount": "1500"  // = Nonfuel Amount
},
"Tax by Product Data Segment": {  // C-10: Tax Type 1 = GST, as the test claims
  "SegmentType": "143",
  "SegmentLength": "020",  // counted like Segment 102 - the one-product Segment 143 examples show 023 for 21 characters; confirm with BUYPASS
  "NumberofProducts": "01",  // same products, same order as Segment 102
  "ProductCode": "400",
  "InclusiveExclusiveforTax1": "I",  // I = tax included in the amount
  "TaxType1": "GST",  // the claimed value
  "TaxAmount1": "71"  // example: 5% GST included in the amount - client to supply
}
```

<a id="d2"></a>

## Defect 2: Variable Information Indicator = 001 is claimed by the test but not sent in the request

**Priority:** High · **Kind:** Test case defect · **Test cases:** 2 shown (8 in the run) · **Defect id:** C-10 (new in the 20-11 run)

<details><summary>All 8 test cases of the run with this defect (click to expand)</summary>

[TC-075656](#tc-075656), [TC-075757](#tc-075757), [TC-075859](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L260), [TC-076077](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L478), [TC-076175](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L576), [TC-076280](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L681), [TC-076385](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L786), [TC-076494](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L895)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-075656](#tc-075656) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L57)) | `ready` | EMV Contact Chip Purchase/Capture with Variable Information Indicator = 001 → approved | Decision table: Purchase/Capture with Variable Information Indicator = 001 (the rule's condition holds) | [TD-TC-075656.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075656.json) |
| [TC-075757](#tc-075757) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L158)) | `ready` | EMV Contactless Chip Purchase/Capture with Variable Information Indicator = 001 → approved | Decision table: Purchase/Capture with Variable Information Indicator = 001 (the rule's condition holds) | [TD-TC-075757.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075757.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-075656 | 1 | [TD-TC-075656.json line 2](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075656.json#L2) → `EMV Financial Transaction Request` | `Variable Information Indicator: not in the test data; segments sent: 100, 111, 130` | The test claims Variable Information Indicator = 001 but the request does not send it |
| TC-075757 | 1 | [TD-TC-075757.json line 2](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075757.json#L2) → `EMV Financial Transaction Request` | `Variable Information Indicator: not in the test data; segments sent: 100, 111, 130` | The test claims Variable Information Indicator = 001 but the request does not send it |

### Expected behavior

- Send Variable Information Indicator = 001 in Variable Information Data Segment, or re-scope / withdraw the test.

### Why this is a defect

- The objective "Decision table: Purchase/Capture with Variable Information Indicator = 001 (the rule's condition holds)" says the request carries Variable Information Indicator = 001, but the test data does not contain it and the test case names no missing input (status `ready`). An approval would only prove that the request without Variable Information Indicator = 001 is approved - not that Variable Information Indicator = 001 is handled.

**BA explanation:** "The test says it sends Variable Information Indicator = 001, but the request does not contain it. A pass cannot be counted as coverage of Variable Information Indicator = 001."

### Acceptance criteria

- The request carries the claimed data with the segments it depends on, and the test asserts it - or the test is re-scoped or withdrawn with the reason written down.
- Missing client data is named as an open item and the test is not marked 'ready'.
- A regression check fails a positive test whose claimed table, segment or value is not in its request.

**Test data excerpt - [TC-075656](#tc-075656), step 1, `TD-TC-075656.json` (selected fields, actual then expected):**

```jsonc
// actual - line 29
"SegmentLength": "042",
// expected
"SegmentLength": "042",  // C-10: recalculate: 042 + 6 + length of the Table 001 data
// actual - not in the request
// expected
{  // C-10: Table 001 - the table this test claims to send
  "VariableInformationIndicator": "001",
  "VariableInformationLength": "<length of the data>",
  "VariableInformation": "<Table 001 data - Appendix I layout>"
}
```

<a id="d3"></a>

## Defect 3: Follow-on steps send the keyed card's expiry as MMYY instead of YYMM

**Priority:** High · **Kind:** Test data defect · **Test cases:** 3 shown (122 in the run) · **Defect id:** D-16 (new in the 20-11 run (0 test cases in 16-11))

<details><summary>All 122 test cases of the run with this defect (click to expand)</summary>

[TC-076166](#tc-076166), [TC-076167](#tc-076167), [TC-076168](#tc-076168), [TC-076187](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L588), [TC-076188](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L589), [TC-076190](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L591), [TC-076191](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L592), [TC-076192](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L593), [TC-076193](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L594), [TC-076194](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L595), [TC-076195](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L596), [TC-076196](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L597), [TC-076197](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L598), [TC-076198](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L599), [TC-076199](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L600), [TC-076200](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L601), [TC-076271](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L672), [TC-076272](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L673), [TC-076273](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L674), [TC-076289](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L690), [TC-076290](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L691), [TC-076292](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L693), [TC-076293](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L694), [TC-076294](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L695), [TC-076295](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L696), [TC-076296](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L697), [TC-076297](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L698), [TC-076298](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L699), [TC-076299](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L700), [TC-076300](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L701), [TC-076301](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L702), [TC-076302](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L703), [TC-076376](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L777), [TC-076377](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L778), [TC-076378](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L779), [TC-076398](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L799), [TC-076399](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L800), [TC-076401](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L802), [TC-076402](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L803), [TC-076403](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L804), [TC-076404](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L805), [TC-076405](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L806), [TC-076406](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L807), [TC-076407](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L808), [TC-076408](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L809), [TC-076409](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L810), [TC-076410](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L811), [TC-076411](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L812), [TC-076538](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L939), [TC-076539](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L940), [TC-076540](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L941), [TC-076541](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L942), [TC-076542](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L943), [TC-076543](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L944), [TC-076544](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L945), [TC-076545](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L946), [TC-076546](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L947), [TC-076547](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L948), [TC-076548](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L949), [TC-076569](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L970), [TC-076570](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L971), [TC-076571](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L972), [TC-076572](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L973), [TC-076573](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L974), [TC-076574](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L975), [TC-076575](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L976), [TC-076576](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L977), [TC-076577](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L978), [TC-076578](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L979), [TC-076579](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L980), [TC-076580](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L981), [TC-076654](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1055), [TC-076655](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1056), [TC-076659](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1060), [TC-076661](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1062), [TC-076662](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1063), [TC-076663](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1064), [TC-076670](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1071), [TC-076671](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1072), [TC-076675](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1076), [TC-076677](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1078), [TC-076678](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1079), [TC-076679](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1080), [TC-076687](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1088), [TC-076688](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1089), [TC-076692](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1093), [TC-076694](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1095), [TC-076695](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1096), [TC-076696](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1097), [TC-076719](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1120), [TC-076720](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1121), [TC-076724](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1125), [TC-076726](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1127), [TC-076727](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1128), [TC-076758](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1159), [TC-076759](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1160), [TC-076763](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1164), [TC-076765](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1166), [TC-076766](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1167), [TC-076816](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1217), [TC-076817](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1218), [TC-076818](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1219), [TC-076819](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1220), [TC-076820](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1221), [TC-076821](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1222), [TC-076826](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1227), [TC-076827](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1228), [TC-076828](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1229), [TC-076829](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1230), [TC-076830](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1231), [TC-076831](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1232), [TC-076836](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1237), [TC-076837](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1238), [TC-076838](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1239), [TC-076839](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1240), [TC-076840](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1241), [TC-076841](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1242), [TC-076842](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1243), [TC-076972](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1373), [TC-076973](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1374), [TC-076983](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1384), [TC-076984](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1385)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-076166](#tc-076166) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L567)) | `requires_review` | Manual / Keyed Purchase/Capture, then Purchase Reversal with Sequence Number different from the original → declined (to confirm) | Single rule broken: Purchase/Capture -> Purchase Reversal with Sequence Number not matching the original -> declined | [TD-TC-076166.step1.purchase.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076166.step1.purchase.json), [TD-TC-076166.step2.reversal.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076166.step2.reversal.json) |
| [TC-076167](#tc-076167) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L568)) | `requires_review` | Manual / Keyed Purchase/Capture, then Purchase Reversal with Terminal Identifier different from the original → declined (to confirm) | Single rule broken: Purchase/Capture -> Purchase Reversal with Terminal Identifier not matching the original -> declined | [TD-TC-076167.step1.purchase.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076167.step1.purchase.json), [TD-TC-076167.step2.reversal.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076167.step2.reversal.json) |
| [TC-076168](#tc-076168) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L569)) | `requires_review` | Manual / Keyed Purchase/Capture, then Time-out Reversal with Terminal Identifier different from the original → declined (to confirm) | Single rule broken: Purchase/Capture -> Time-out Reversal with Terminal Identifier not matching the original -> declined | [TD-TC-076168.step1.purchase.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076168.step1.purchase.json), [TD-TC-076168.step2.timeout-reversal.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076168.step2.timeout-reversal.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-076166 | 2 | [TD-TC-076166.step2.reversal.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076166.step2.reversal.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `0928` | Card Discretionary Block Data is not valid |
| TC-076167 | 2 | [TD-TC-076167.step2.reversal.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076167.step2.reversal.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `0430` | Card Discretionary Block Data is not valid |
| TC-076168 | 2 | [TD-TC-076168.step2.timeout-reversal.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076168.step2.timeout-reversal.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `0929` | Card Discretionary Block Data is not valid |

### Expected behavior

- `CardDiscretionaryBlockData` = `2809` - the expiry as YYMM (the original's MMYY `0928` with month and year swapped), as every follow-on transaction sends it.

### Why this is a defect

- Step 2 is a reversal of step 1, a keyed card whose expiry is MMYY '0928' (September 2028). A follow-on transaction sends the stored card data as Account Number = YYMM, i.e. '2809'; the file repeats '0928', which read as YYMM is month '28' - not a valid month.
- Step 2 is a reversal of step 1, a keyed card whose expiry is MMYY '0430' (April 2030). A follow-on transaction sends the stored card data as Account Number = YYMM, i.e. '3004'; the file repeats '0430', which read as YYMM is month '30' - not a valid month.
- Step 2 is a timeout reversal of step 1, a keyed card whose expiry is MMYY '0929' (September 2029). A follow-on transaction sends the stored card data as Account Number = YYMM, i.e. '2909'; the file repeats '0929', which read as YYMM is month '29' - not a valid month.

**BA explanation:** "A reversal, time-out reversal, cancellation or void is built from the card data stored with the original transaction, and ATL105 sends that card data as Account Number = YYMM. These follow-on steps copy the keyed MMYY expiry of the original (for example 0131 = January 2031), which read as YYMM is month 31. The follow-on can be rejected or not matched for a reason the test does not intend."

### Acceptance criteria

- Every follow-on step of a keyed original (Table 005 entry mode 01) sends the expiry as YYMM: month and year of the original's MMYY swapped (0131 -> 3101).
- The Segment 100 length does not change (4 characters either way).
- A regression check compares the follow-on expiry with the original's MMYY.

**Specification reference (ATL105, word for word):**

- Section 10.1.8.2 Track Support Requirements, PDF p.94 (printed 10-15): "The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM)." ([ATL105 text line 4564](../specifications/ATL105/docs/specs/extracted_text_new.txt#L4564))
- Section 10.1.1.2 Track Support, PDF p.81 (printed 10-2): "All follow-up transactions should use truncated Track 2 or truncated Track 2 facsimile." ([ATL105 text line 4062](../specifications/ATL105/docs/specs/extracted_text_new.txt#L4062))

**Test data excerpt - [TC-076166](#tc-076166), step 2, `TD-TC-076166.step2.reversal.json` (selected fields, actual then expected):**

```jsonc
// actual - line 15
"CardDiscretionaryBlockData": "0928",
// expected
"CardDiscretionaryBlockData": "2809",  // INFO: the wire message sends AccountNumber '=' this value: 5187270512804249=0928; D-16: expiry as YYMM - the original's MMYY 0928 with month and year swapped (follow-on transaction, Section 10.1.8.2)
```

<a id="d4"></a>

## Defect 4: Other negative tests that break a second rule besides the one under test

**Priority:** High · **Kind:** Test data defect · **Test cases:** 4 shown (8 in the run) · **Defect id:** D-09

<details><summary>All 8 test cases of the run with this defect (click to expand)</summary>

[TC-077181](#tc-077181), [TC-077184](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1585), [TC-077186](#tc-077186), [TC-077188](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1589), [TC-077193](#tc-077193), [TC-077209](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1610), [TC-077210](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1611), [TC-077219](#tc-077219)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-077181](#tc-077181) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1582)) | `requires_review` | Credential on File Purchase/Capture NFC Payment Tokenization Data Segment omitted where requires it → declined | NFC Payment Tokenization Data Segment (segment 123) omitted where p.267 requires it | [TD-TC-077181.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077181.json) |
| [TC-077186](#tc-077186) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1587)) | `ready` | EMV Contact Chip Authorization Only Required field EMV Chip Data Length sent empty → declined | Required field EMV Chip Data Length sent empty | [TD-TC-077186.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077186.json) |
| [TC-077193](#tc-077193) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1594)) | `needs_client_input` | SAMPLE Petroleum CAT Magnetic Stripe Authorization Only Required field Prompt Code omitted → declined | Required field Prompt Code omitted | [TD-TC-077193.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077193.json) |
| [TC-077219](#tc-077219) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1620)) | `ready` | EMV Contact Chip Purchase/Capture Variable Information one byte over its documented maximum length → declined | Variable Information one byte over its documented maximum length (982) | [TD-TC-077219.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077219.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-077181 | 1 | [TD-TC-077181.json line 27](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077181.json#L27) → `Variable Information Data Segment` | `tables sent: 005, 031` | Table 030 (POS Condition Code) is missing |
| TC-077181 | 1 | [TD-TC-077181.json line 27](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077181.json#L27) → `Variable Information Data Segment` | `tables sent: 005, 031` | The Credential on File indicators are missing |
| TC-077186 | 1 | [TD-TC-077186.json line 50](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077186.json#L50) → `EMV Request Data Segment → SegmentLength` | `0054` | Segment 130 length is wrong |
| TC-077193 | 1 | [TD-TC-077193.json line 35](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077193.json#L35) → `Variable Information Data Segment → VariableInformationSection[0]` | `021` | Table 005 value is not valid |
| TC-077219 | 1 | [TD-TC-077219.json line 27](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077219.json#L27) → `Variable Information Data Segment → VariableInformationSection[0]` | `` | Table 005 length is wrong |

### Expected behavior

- Add Table 030 (10 digits, values from Appendix Q) for the certified terminal.
- Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'); Table 056 sub-table 09 (MasterCard MIT/CIT category).
- 0201

### Why this is a defect

- This step is a Credential on File transaction. Table 030 identifies the device class, presentation data, security condition and terminal type, and ATL105 requires it for all EMV and all Credential on File transactions.
- Table 005 entry mode 10 marks a transaction with stored credentials. ATL105 requires the merchant to send the Initial/Credential on File indicator for all COF transactions, the Transaction Initiation Indicator for all subsequent COF transactions and, for MasterCard, the MIT/CIT category. Missing: Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'); Table 056 sub-table 09 (MasterCard MIT/CIT category).
- '0054' is declared, but the segment adds up to 0201.
- '021' has entry mode '02', which does not fit the MAGSTRIPE scenario (expected 90).
- '' is declared, but the table data 'AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA' is 983 characters long.

**BA explanation:** "A negative test must break exactly one rule, so that the decline can only come from that rule. These requests also break another ATL105 rule (missing Table 030 / Credential on File indicators, wrong segment length, entry mode that does not fit the scenario), so the cause of a decline cannot be told."

### Acceptance criteria

- Every field except the one under test is valid for the scenario; the other rule named in each finding is fixed.
- A regression check validates the whole request of each negative test except its deliberate field.

**Specification reference (ATL105, word for word):**

- Appendix I. Variable Information Data Layouts - POS Condition Code (Table 030), PDF p.568 (printed Appendix I-19): "Required for all EMV transactions, all internet based transactions (Ex: Mobile Commerce, ecommerce and In App), and all Credential on File transactions." ([ATL105 text line 30379](../specifications/ATL105/docs/specs/extracted_text_new.txt#L30379))
- Appendix I. Variable Information Data Layouts - POS Additional Information (Table 032), Sub Table 08, PDF p.575 (printed Appendix I-26): "It is merchant’s responsibility to send the indicator as Initial (“I”) or Subsequent Credential on File (“C”) from the terminal for all COF transactions (Authorization, Completion, Cancellation, Reversal etc.)." ([ATL105 text line 30662](../specifications/ATL105/docs/specs/extracted_text_new.txt#L30662))
- Appendix I. Variable Information Data Layouts - Merchant Supplementary Data (Table 049), Sub Table 02, PDF p.589 (printed Appendix I-40): "This field is required for all subsequent Credential on File transactions." ([ATL105 text line 31460](../specifications/ATL105/docs/specs/extracted_text_new.txt#L31460))
- Section 10.13.2.4.2 Stored Credential Use Cases, PDF p.144 (printed 10-65): "Table ID 56 (Stored Credentials Transaction Additional Data), Sub Table ID 09 (MIT/CIT category and subcategory data) used to indicate category and subcategory data for Cardholder initiated transaction (CIT) and Merchant initiated transactions (MIT) for MasterCard card type." ([ATL105 text line 6708](../specifications/ATL105/docs/specs/extracted_text_new.txt#L6708))
- Appendix J. Valid Point-of-Service Entry Mode Codes, PDF p.638 (printed Appendix J-1): "10 Credential on file" ([ATL105 text line 34760](../specifications/ATL105/docs/specs/extracted_text_new.txt#L34760))
- Section 12.20 EMV Request Data Segment, PDF p.270 (printed 12-53): "Identifies the data segment's length, including Segment Type's length and Field Separators." ([ATL105 text line 15702](../specifications/ATL105/docs/specs/extracted_text_new.txt#L15702))
- Section 12.20 EMV Request Data Segment, PDF p.270 (printed 12-53): "A Field Separator follows the final EMV Additional Information Section." ([ATL105 text line 15681](../specifications/ATL105/docs/specs/extracted_text_new.txt#L15681))
- Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005), PDF p.555 (printed Appendix I-6): "Point-of-Service Entry Mode Information" ([ATL105 text line 29811](../specifications/ATL105/docs/specs/extracted_text_new.txt#L29811))
- Appendix J. Valid Point-of-Service Entry Mode Codes, PDF p.638 (printed Appendix J-1): "02 Magnetic stripe read with partial track data" ([ATL105 text line 34754](../specifications/ATL105/docs/specs/extracted_text_new.txt#L34754))
- Appendix J. Valid Point-of-Service Entry Mode Codes, PDF p.639 (printed Appendix J-2): "1 PIN entry capability (including both EMV Contact and Contactless)" ([ATL105 text line 34804](../specifications/ATL105/docs/specs/extracted_text_new.txt#L34804))
- Section 12.10 Variable Information Data Segment, PDF p.246 (printed 12-29): "Identifies the length of Variable Information (Element No. 113)." ([ATL105 text line 14251](../specifications/ATL105/docs/specs/extracted_text_new.txt#L14251))

**Test data excerpt - [TC-077181](#tc-077181), step 1, `TD-TC-077181.json` (selected fields, actual then expected):**

```jsonc
// actual - line 27
"Variable Information Data Segment": {
// expected
"Variable Information Data Segment": {  // D-09: Add Table 030 (10 digits, values from Appendix Q) for the certified terminal.; D-09: Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'); Table 056 sub-t
```

<a id="d5"></a>

## Defect 5: Card read from the chip or stripe, but the card data is only an MMYY expiry

**Priority:** High · **Kind:** Test data defect · **Test cases:** 7 shown (39 in the run) · **Defect id:** D-13 (also in 16-11 (37 test cases))

<details><summary>All 39 test cases of the run with this defect (click to expand)</summary>

[TC-077177](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1578), [TC-077178](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1579), [TC-077182](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1583), [TC-077183](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1584), [TC-077185](#tc-077185), [TC-077186](#tc-077186), [TC-077187](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1588), [TC-077190](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1591), [TC-077192](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1593), [TC-077193](#tc-077193), [TC-077194](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1595), [TC-077195](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1596), [TC-077196](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1597), [TC-077197](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1598), [TC-077198](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1599), [TC-077199](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1600), [TC-077200](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1601), [TC-077201](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1602), [TC-077202](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1603), [TC-077203](#tc-077203), [TC-077204](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1605), [TC-077205](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1606), [TC-077206](#tc-077206), [TC-077207](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1608), [TC-077208](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1609), [TC-077209](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1610), [TC-077210](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1611), [TC-077211](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1612), [TC-077212](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1613), [TC-077213](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1614), [TC-077214](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1615), [TC-077215](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1616), [TC-077216](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1617), [TC-077217](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1618), [TC-077218](#tc-077218), [TC-077219](#tc-077219), [TC-077220](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1621), [TC-077221](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1622), [TC-077222](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1623)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-077186](#tc-077186) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1587)) | `ready` | EMV Contact Chip Authorization Only Required field EMV Chip Data Length sent empty → declined | Required field EMV Chip Data Length sent empty | [TD-TC-077186.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077186.json) |
| [TC-077193](#tc-077193) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1594)) | `needs_client_input` | SAMPLE Petroleum CAT Magnetic Stripe Authorization Only Required field Prompt Code omitted → declined | Required field Prompt Code omitted | [TD-TC-077193.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077193.json) |
| [TC-077219](#tc-077219) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1620)) | `ready` | EMV Contact Chip Purchase/Capture Variable Information one byte over its documented maximum length → declined | Variable Information one byte over its documented maximum length (982) | [TD-TC-077219.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077219.json) |
| [TC-077185](#tc-077185) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1586)) | `requires_review` | Magnetic Stripe Balance Inquiry Merchant Trace ID exactly at its documented maximum length (25 bytes) is accepted → approved | Merchant Trace ID exactly at its documented maximum length (25 bytes) is accepted | [TD-TC-077185.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077185.json) |
| [TC-077206](#tc-077206) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1607)) | `needs_client_input` | EMV Contact Chip Purchase/Capture Encrypted PIN Block Data exactly at its documented maximum length (36 bytes) is accepted → approved | Encrypted PIN Block Data exactly at its documented maximum length (36 bytes) is accepted | [TD-TC-077206.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077206.json) |
| [TC-077218](#tc-077218) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1619)) | `requires_review` | EMV Contact Chip Balance Inquiry Information Byte exactly at its documented maximum length (1 bytes) is accepted → approved | Information Byte exactly at its documented maximum length (1 bytes) is accepted | [TD-TC-077218.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077218.json) |
| [TC-077203](#tc-077203) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1604)) | `needs_client_input` | SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture Customer Data contains a character outside its character type (AN) → declined | Customer Data contains a character outside its character type (AN) | [TD-TC-077203.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077203.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-077186 | 1 | [TD-TC-077186.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077186.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `0427` | Card Discretionary Block Data is not valid |
| TC-077193 | 1 | [TD-TC-077193.json line 12](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077193.json#L12) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `1030` | Card Discretionary Block Data is not valid |
| TC-077219 | 1 | [TD-TC-077219.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077219.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `0530` | Card Discretionary Block Data is not valid |
| TC-077185 | 1 | [TD-TC-077185.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077185.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `0232` | Card Discretionary Block Data is not valid |
| TC-077206 | 1 | [TD-TC-077206.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077206.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `0927` | Card Discretionary Block Data is not valid |
| TC-077218 | 1 | [TD-TC-077218.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077218.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `0228` | Card Discretionary Block Data is not valid |
| TC-077203 | 1 | [TD-TC-077203.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077203.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `0828` | Card Discretionary Block Data is not valid |

### Expected behavior

- `CardDiscretionaryBlockData` = the card's Track 2 data after the '=' (expiry YYMM + 3-digit service code + discretionary data) from the client's test card - or a keyed entry mode if only the expiry is known.

### Why this is a defect

- '0427' does not start with an expiry (YYMM) and a 3-digit service code, as Track 2 data does.

**BA explanation:** "Table 005 says the card was read (chip or magnetic stripe), so ATL105 expects the track data after the '=' (expiry YYMM, service code, discretionary data). The request sends only a 4-digit MMYY expiry, as for a keyed card - the message contradicts itself."

### Acceptance criteria

- A read card sends its Track 2 (equivalent) data after the '=' in Card Discretionary Block Data, or the test uses a keyed entry mode when only the expiry is known.

**Specification reference (ATL105, word for word):**

- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number is considered the data before the = character, and Card Discretionary Block Data is considered the data after the = character." ([ATL105 text line 21712](../specifications/ATL105/docs/specs/extracted_text_new.txt#L21712))
- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.371 (printed 13-31): "Account Number and Card Discretionary Block Data combined do not exceed 76 characters for Track 1 or 37 characters for Track 2." ([ATL105 text line 21710](../specifications/ATL105/docs/specs/extracted_text_new.txt#L21710))

**Test data excerpt - [TC-077186](#tc-077186), step 1, `TD-TC-077186.json` (selected fields, actual then expected):**

```jsonc
// actual - line 7
"SegmentLength": "072",
// expected
"SegmentLength": "072",  // D-13: recalculate: 055 + length of the track data + length of the Terminal Identifier
// actual - line 15
"CardDiscretionaryBlockData": "0427",
// expected
"CardDiscretionaryBlockData": "<Track 2 data after '=': 2704 + service code + discretionary data>",  // D-13: the card was read (Table 005) - send the track data after the '=': expiry 2704 (YYMM), service code, discretionary data - from the client's test card
```

<a id="d6"></a>

## Defect 6: Totals Request does not match the transactions of the flow

**Priority:** High · **Kind:** Test data defect · **Test cases:** 3 shown (38 in the run) · **Defect id:** D-01 (also in 16-11 (28 test cases))

<details><summary>All 38 test cases of the run with this defect (click to expand)</summary>

[TC-076596](#tc-076596), [TC-076597](#tc-076597), [TC-076598](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L999), [TC-076613](#tc-076613), [TC-076614](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1015), [TC-076615](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1016), [TC-076630](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1031), [TC-076631](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1032), [TC-076632](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1033), [TC-076647](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1048), [TC-076648](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1049), [TC-076649](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1050), [TC-076663](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1064), [TC-076664](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1065), [TC-076665](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1066), [TC-076679](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1080), [TC-076680](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1081), [TC-076681](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1082), [TC-076696](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1097), [TC-076697](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1098), [TC-076698](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1099), [TC-076712](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1113), [TC-076713](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1114), [TC-076714](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1115), [TC-076727](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1128), [TC-076728](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1129), [TC-076729](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1130), [TC-076766](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1167), [TC-076767](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1168), [TC-076768](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1169), [TC-076779](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1180), [TC-076790](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1191), [TC-076801](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1202), [TC-076811](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1212), [TC-076821](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1222), [TC-076831](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1232), [TC-076842](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1243), [TC-076852](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1253)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-076596](#tc-076596) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L997)) | `requires_review` | EMV Contact Chip Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | Running total / state: Authorization Only -> Cancellation -> Totals Request | [TD-TC-076596.step1.auth-only.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076596.step1.auth-only.json), [TD-TC-076596.step2.cancellation.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076596.step2.cancellation.json), [TD-TC-076596.step3.totals.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076596.step3.totals.json) |
| [TC-076613](#tc-076613) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1014)) | `requires_review` | EMV Contactless Chip Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | Running total / state: Authorization Only -> Cancellation -> Totals Request | [TD-TC-076613.step1.auth-only.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076613.step1.auth-only.json), [TD-TC-076613.step2.cancellation.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076613.step2.cancellation.json), [TD-TC-076613.step3.totals.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076613.step3.totals.json) |
| [TC-076597](#tc-076597) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L998)) | `requires_review` | EMV Contact Chip Authorization Only, then Purchase/Capture, then Totals Request (totals include it) → approved | Running total / state: Authorization Only -> Purchase/Capture -> Totals Request | [TD-TC-076597.step1.auth-only.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076597.step1.auth-only.json), [TD-TC-076597.step2.completion.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076597.step2.completion.json), [TD-TC-076597.step3.totals.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076597.step3.totals.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-076596 | 3 | [TD-TC-076596.step3.totals.json line 5](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076596.step3.totals.json#L5) → `Totals Data Segment` | `count 00001, amount 00000000, grand total 00000000` | The totals do not match the transactions of the flow |
| TC-076613 | 3 | [TD-TC-076613.step3.totals.json line 5](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076613.step3.totals.json#L5) → `Totals Data Segment` | `count 00001, amount 00000000, grand total 00000000` | The totals do not match the transactions of the flow |
| TC-076597 | 3 | [TD-TC-076597.step3.totals.json line 5](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076597.step3.totals.json#L5) → `Totals Data Segment` | `count 00001, amount 00000000, grand total 00000000` | The totals do not match the transactions of the flow |

### Expected behavior

- Leave out the CC bucket and send Grand Total 00000000
- Count 00001, amount 00001500, grand total 00001500

### Why this is a defect

- Section 9.2 counts each approved transaction into its card bucket: step 1 auth only $15.00: Authorization Only - AO bucket, not in the grand total; step 2 cancellation $15.00: cancellation - subtracts from the AO bucket. The CC bucket nets to count 0 and amount 0 (so it is not sent at all), but the file has count 00001 and amount 00000000.
- Section 9.2 counts each approved transaction into its card bucket: step 1 auth only $15.00: Authorization Only - AO bucket, not in the grand total; step 2 completion $15.00: count +1, amount +$15.00. Card Type Total Amount should be 00001500 ($15.00), the file has 00000000; Grand Total should be 00001500, the file has 00000000.

**BA explanation:** "The totals at the end of the flow must add up the approved transactions of the same test (Section 9.2). The files send counts and amounts that do not match the steps, so a totals test cannot pass for the right reason."

### Acceptance criteria

- Card bucket count/amount and Grand Total are computed from the approved steps of the flow; an Authorization Only goes to the AO bucket; a bucket with net count 0 is not sent.

**Specification reference (ATL105, word for word):**

- Section 9.2 Local Totals, PDF p.72 (printed 9-8): "A Purchase/Capture increases the count and dollar amount of the corresponding category as well as the grand total." ([ATL105 text line 3729](../specifications/ATL105/docs/specs/extracted_text_new.txt#L3729))
- Section 9.2 Local Totals, PDF p.72 (printed 9-8): "A Reversal decreases the count and dollar amount of the corresponding category as well as the grand total." ([ATL105 text line 3731](../specifications/ATL105/docs/specs/extracted_text_new.txt#L3731))
- Section 9.2 Local Totals, PDF p.72 (printed 9-8): "A Merchandise Return increases the count and decreases the dollar amount for the corresponding category as well as the grand total." ([ATL105 text line 3733](../specifications/ATL105/docs/specs/extracted_text_new.txt#L3733))
- Section 9.2 Local Totals, PDF p.72 (printed 9-8): "The grand total includes all transactions except the non-financial transactions grouped in the following categories: • AO—Authorization only" ([ATL105 text line 3719](../specifications/ATL105/docs/specs/extracted_text_new.txt#L3719))
- Section 12.6 Totals Data Segment, PDF p.237 (printed 12-20): "Field Nos. 15–17 are sent up to a maximum of 18 times—once for each of the following card types—in the order shown and only when data occurs for a card type." ([ATL105 text line 13692](../specifications/ATL105/docs/specs/extracted_text_new.txt#L13692))

**Test data excerpt - [TC-076596](#tc-076596), step 3, `TD-TC-076596.step3.totals.json` (selected fields, actual then expected):**

```jsonc
// actual - line 5
"Totals Data Segment": {
// expected
"Totals Data Segment": {  // D-01: Leave out the CC bucket and send Grand Total 00000000
```

<a id="d7"></a>

## Defect 7: Negative tests whose premise is not in ATL105 - the expected decline is not justified

**Priority:** High · **Kind:** Test case defect · **Test cases:** 2 shown (21 in the run) · **Defect id:** C-02 (BA review 19-11-2026 (Segment 108) - premise defect still in other tests)

<details><summary>All 21 test cases of the run with this defect (click to expand)</summary>

[TC-075650](#tc-075650), [TC-075651](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L52), [TC-075751](#tc-075751), [TC-075752](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L153), [TC-075853](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L254), [TC-075854](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L255), [TC-076020](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L421), [TC-076071](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L472), [TC-076072](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L473), [TC-076169](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L570), [TC-076170](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L571), [TC-076274](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L675), [TC-076275](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L676), [TC-076379](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L780), [TC-076380](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L781), [TC-076488](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L889), [TC-076489](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L890), [TC-077187](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1588), [TC-077188](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1589), [TC-077190](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1591), [TC-077222](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1623)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-075650](#tc-075650) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L51)) | `requires_review` | EMV Contact Chip Purchase/Capture with Payment Tender Type sent where it is not allowed → declined (to confirm) | Single rule broken: Purchase/Capture with Payment Tender Type sent where the spec forbids it -> declined | [TD-TC-075650.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075650.json) |
| [TC-075751](#tc-075751) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L152)) | `requires_review` | EMV Contactless Chip Purchase/Capture with Payment Tender Type sent where it is not allowed → declined (to confirm) | Single rule broken: Purchase/Capture with Payment Tender Type sent where the spec forbids it -> declined | [TD-TC-075751.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075751.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-075650 | - | test case record [test_cases.jsonl line 51](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L51) → `test_objective` | `test_objective` = `Single rule broken: Purchase/Capture with Payment Tender Type sent where the spec forbids it -> declined`; expected Response Code `1` | The rule this negative test claims to break is not in ATL105 |
| TC-075751 | - | test case record [test_cases.jsonl line 152](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L152) → `test_objective` | `test_objective` = `Single rule broken: Purchase/Capture with Payment Tender Type sent where the spec forbids it -> declined`; expected Response Code `1` | The rule this negative test claims to break is not in ATL105 |

### Expected behavior

- Remove or rewrite the test around a rule ATL105 states.

### Why this is a defect

- Payment Tender Type (Element 148) is a Required field (R) of Segment 108 whenever the segment is sent.

**BA explanation:** "A negative test must break a rule ATL105 states. These tests send a field ATL105 makes Required whenever its segment is sent (Payment Tender Type in Segment 108, Service Level in Segment 102), call a supported transaction 'not supported' (Mail/Phone Reversal), or leave out a conditional segment that the cited page does not require - and the pages they cite are about something else."

### Acceptance criteria

- Cite the ATL105 rule the test breaks, word for word on the cited page - or remove the test.
- A 'field sent where the spec forbids it' test names the rule that forbids the field; a 'segment omitted' test uses a scenario where ATL105 requires the segment.
- A regression check fails negative tests whose premise is contradicted by Section 11.1 / 12.x.

**Specification reference (ATL105, word for word):**

- Section 12.7 Loyalty Card Data Segment, PDF p.239 (printed 12-22): "12 148 Payment Tender Type 2 R Used to determine whether the transaction is a multiple-card transaction involving a loyalty card." ([ATL105 text line 13844](../specifications/ATL105/docs/specs/extracted_text_new.txt#L13844))

**Test data excerpt - [TC-075650](#tc-075650), step 1, `TD-TC-075650.json` (selected fields, actual then expected):**

```jsonc
// actual - line 2
"EMV Financial Transaction Request": {
// expected
"EMV Financial Transaction Request": {  // C-02: no change to the request - what must change is the test (its premise and the expected decline); see the finding
```

<a id="d8"></a>

## Defect 8: Time-out Reversal sent although the original request is expected to be answered

**Priority:** High · **Kind:** To confirm with BUYPASS / SME · **Test cases:** 2 shown (86 in the run) · **Defect id:** C-06 (also in 16-11 (62 test cases))

<details><summary>All 86 test cases of the run with this defect (click to expand)</summary>

[TC-075649](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L50), [TC-075667](#tc-075667), [TC-075669](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L70), [TC-075676](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L77), [TC-075677](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L78), [TC-075679](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L80), [TC-075687](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L88), [TC-075750](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L151), [TC-075767](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L168), [TC-075769](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L170), [TC-075776](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L177), [TC-075777](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L178), [TC-075779](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L180), [TC-075787](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L188), [TC-075852](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L253), [TC-075878](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L279), [TC-075880](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L281), [TC-075887](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L288), [TC-075888](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L289), [TC-075890](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L291), [TC-075918](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L319), [TC-076070](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L471), [TC-076087](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L488), [TC-076089](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L490), [TC-076096](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L497), [TC-076097](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L498), [TC-076099](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L500), [TC-076106](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L507), [TC-076168](#tc-076168), [TC-076188](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L589), [TC-076190](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L591), [TC-076197](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L598), [TC-076198](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L599), [TC-076200](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L601), [TC-076207](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L608), [TC-076273](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L674), [TC-076290](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L691), [TC-076292](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L693), [TC-076299](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L700), [TC-076300](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L701), [TC-076302](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L703), [TC-076309](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L710), [TC-076378](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L779), [TC-076399](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L800), [TC-076401](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L802), [TC-076408](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L809), [TC-076409](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L810), [TC-076411](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L812), [TC-076418](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L819), [TC-076487](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L888), [TC-076503](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L904), [TC-076505](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L906), [TC-076512](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L913), [TC-076513](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L914), [TC-076515](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L916), [TC-076523](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L924), [TC-076540](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L941), [TC-076542](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L943), [TC-076544](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L945), [TC-076548](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L949), [TC-076557](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L958), [TC-076571](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L972), [TC-076573](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L974), [TC-076575](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L976), [TC-076580](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L981), [TC-076583](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L984), [TC-077028](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1429), [TC-077030](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1431), [TC-077042](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1443), [TC-077044](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1445), [TC-077059](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1460), [TC-077061](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1462), [TC-077085](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1486), [TC-077087](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1488), [TC-077098](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1499), [TC-077100](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1501), [TC-077137](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1538), [TC-077139](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1540), [TC-077143](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1544), [TC-077145](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1546), [TC-077149](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1550), [TC-077151](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1552), [TC-077154](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1555), [TC-077156](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1557), [TC-077159](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1560), [TC-077161](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1562)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-076168](#tc-076168) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L569)) | `requires_review` | Manual / Keyed Purchase/Capture, then Time-out Reversal with Terminal Identifier different from the original → declined (to confirm) | Single rule broken: Purchase/Capture -> Time-out Reversal with Terminal Identifier not matching the original -> declined | [TD-TC-076168.step1.purchase.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076168.step1.purchase.json), [TD-TC-076168.step2.timeout-reversal.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076168.step2.timeout-reversal.json) |
| [TC-075667](#tc-075667) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L68)) | `ready` | EMV Contact Chip Purchase/Capture, then Time-out Reversal → approved | Valid flow: Purchase/Capture -> Time-out Reversal | [TD-TC-075667.step1.purchase.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075667.step1.purchase.json), [TD-TC-075667.step2.timeout-reversal.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075667.step2.timeout-reversal.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-076168 | 2 | test case record [test_cases.jsonl line 569](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L569) → `tx_legs[0].expect` | `tx_legs[0].expect` = {"tier": "ASSERT", "page": 403, "response_code": "0", "note": "Approved-Purchase/Capture"} and `harness` = `null` (no fault injection) | Step 2 sends a Time-out Reversal although step 1 is expected to be answered |
| TC-075667 | 2 | test case record [test_cases.jsonl line 68](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L68) → `tx_legs[0].expect` | `tx_legs[0].expect` = {"tier": "ASSERT", "page": 403, "response_code": "0", "note": "Approved-Purchase/Capture"} and `harness` = `null` (no fault injection) | Step 2 sends a Time-out Reversal although step 1 is expected to be answered |

### Expected behavior

- Make step 1 a fault-injected 'no response' (or a late response the harness ignores). Step 1 then expects no response (instead of Response Code 0), and the device sends the Time-out Reversal after the 30-second response interval.

### Why it matters

- ATL105 creates a TOR only when the original request times out (no response within 30 seconds, or a late response that is ignored). Step 1 has no 'no response' harness directive and expects Response Code 0, so the device would not send a TOR.

**BA explanation:** "A time-out reversal is only sent when the original gets no answer within 30 seconds. This test expects the original to be approved, so a real device would never send the reversal."

### Acceptance criteria

- The original step is a fault-injected 'no response' (or a late response the harness ignores) and expects no response.
- The Time-out Reversal then follows on the same device with the original's Sequence Number, card and amount.

**Specification reference (ATL105, word for word):**

- Section 2.3 Unsuccessful Financial Transactions, PDF p.39 (printed 2-4): "A time-out reversal (TOR) is created for an unsuccessful financial transaction." ([ATL105 text line 2144](../specifications/ATL105/docs/specs/extracted_text_new.txt#L2144))
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "The customer’s 30-second response interval expires without a response from BUYPASS." ([ATL105 text line 2171](../specifications/ATL105/docs/specs/extracted_text_new.txt#L2171))
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "If the customer’s host/controller/POS system receives a late response to the original transaction request from BUYPASS, the late response is ignored." ([ATL105 text line 2174](../specifications/ATL105/docs/specs/extracted_text_new.txt#L2174))
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "Once a time-out occurs for a device, the customer’s host/controller/POS system formats a TOR" ([ATL105 text line 2179](../specifications/ATL105/docs/specs/extracted_text_new.txt#L2179))

<a id="d9"></a>

## Defect 9: Segment 100 / 111 / 130 / 105 value breaks an ATL105 rule (trained validator)

**Priority:** High · **Kind:** Test data defect · **Test cases:** 2 shown (3 in the run) · **Defect id:** D-11

<details><summary>All 3 test cases of the run with this defect (click to expand)</summary>

[TC-076423](#tc-076423), [TC-076424](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L825), [TC-077185](#tc-077185)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-076423](#tc-076423) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L824)) | `ready` | Credential on File Purchase/Capture sending Merchant Supplementary Data (Merchant Advice Code returned) → approved | Response / oracle check: Purchase/Capture sending Merchant Supplementary Data -> Merchant Advice Code returned | [TD-TC-076423.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076423.json) |
| [TC-077185](#tc-077185) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1586)) | `requires_review` | Magnetic Stripe Balance Inquiry Merchant Trace ID exactly at its documented maximum length (25 bytes) is accepted → approved | Merchant Trace ID exactly at its documented maximum length (25 bytes) is accepted | [TD-TC-077185.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077185.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-076423 | 1 | [TD-TC-076423.json line 27](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076423.json#L27) → `Variable Information Data Segment` | `tables sent: 005, 031, 030, 032, 049, 056` | The Credential on File indicators are missing |
| TC-077185 | 1 | [TD-TC-077185.json line 27](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077185.json#L27) → `Variable Information Data Segment → VariableInformationSection[0]` | `021` | Table 005 value is not valid |

### Expected behavior

- Add Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M').
- 021

### Why this is a defect

- Table 005 entry mode 10 marks a transaction with stored credentials. ATL105 requires the merchant to send the Initial/Credential on File indicator for all COF transactions, the Transaction Initiation Indicator for all subsequent COF transactions and, for MasterCard, the MIT/CIT category. Missing: Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M').
- '021' has entry mode '02', which does not fit the MAGSTRIPE scenario (expected 90).

### Acceptance criteria

- Correct the value as stated in the finding.

**Specification reference (ATL105, word for word):**

- Appendix I. Variable Information Data Layouts - POS Additional Information (Table 032), Sub Table 08, PDF p.575 (printed Appendix I-26): "It is merchant’s responsibility to send the indicator as Initial (“I”) or Subsequent Credential on File (“C”) from the terminal for all COF transactions (Authorization, Completion, Cancellation, Reversal etc.)." ([ATL105 text line 30662](../specifications/ATL105/docs/specs/extracted_text_new.txt#L30662))
- Appendix I. Variable Information Data Layouts - Merchant Supplementary Data (Table 049), Sub Table 02, PDF p.589 (printed Appendix I-40): "This field is required for all subsequent Credential on File transactions." ([ATL105 text line 31460](../specifications/ATL105/docs/specs/extracted_text_new.txt#L31460))
- Section 10.13.2.4.2 Stored Credential Use Cases, PDF p.144 (printed 10-65): "Table ID 56 (Stored Credentials Transaction Additional Data), Sub Table ID 09 (MIT/CIT category and subcategory data) used to indicate category and subcategory data for Cardholder initiated transaction (CIT) and Merchant initiated transactions (MIT) for MasterCard card type." ([ATL105 text line 6708](../specifications/ATL105/docs/specs/extracted_text_new.txt#L6708))
- Appendix J. Valid Point-of-Service Entry Mode Codes, PDF p.638 (printed Appendix J-1): "10 Credential on file" ([ATL105 text line 34760](../specifications/ATL105/docs/specs/extracted_text_new.txt#L34760))
- Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005), PDF p.555 (printed Appendix I-6): "Point-of-Service Entry Mode Information" ([ATL105 text line 29811](../specifications/ATL105/docs/specs/extracted_text_new.txt#L29811))
- Appendix J. Valid Point-of-Service Entry Mode Codes, PDF p.638 (printed Appendix J-1): "02 Magnetic stripe read with partial track data" ([ATL105 text line 34754](../specifications/ATL105/docs/specs/extracted_text_new.txt#L34754))
- Appendix J. Valid Point-of-Service Entry Mode Codes, PDF p.639 (printed Appendix J-2): "1 PIN entry capability (including both EMV Contact and Contactless)" ([ATL105 text line 34804](../specifications/ATL105/docs/specs/extracted_text_new.txt#L34804))

**Test data excerpt - [TC-076423](#tc-076423), step 1, `TD-TC-076423.json` (selected fields, actual then expected):**

```jsonc
// actual - line 27
"Variable Information Data Segment": {
// expected
"Variable Information Data Segment": {  // D-11: Add Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M').
```

<a id="d10"></a>

## Defect 10: Positive boundary test: the value is not at the documented limit or is not a valid value

**Priority:** High · **Kind:** Test data defect · **Test cases:** 1 shown (1 in the run) · **Defect id:** D-15

<details><summary>The 1 test case of the run with this defect (click to expand)</summary>

[TC-077218](#tc-077218)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-077218](#tc-077218) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1619)) | `requires_review` | EMV Contact Chip Balance Inquiry Information Byte exactly at its documented maximum length (1 bytes) is accepted → approved | Information Byte exactly at its documented maximum length (1 bytes) is accepted | [TD-TC-077218.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077218.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-077218 | 1 | [TD-TC-077218.json line 8](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077218.json#L8) → `Standard Message Data Segment → InformationByte` | `A` | Information Byte is not valid |

### Expected behavior

- A value that meets: 1 character: 'A' = not a valid code.

### Why this is a defect

- 'A' is not a valid Information Byte (?, 0 or 1).

### Acceptance criteria

- Send a valid value of exactly the documented maximum length; if no such value can be approved (a MasterCard card number is 16 digits, not 24), drop the positive boundary test.

**Specification reference (ATL105, word for word):**

- Element 44 Information Byte (Chapter 13), PDF p.386 (printed 13-46): "0 Single-message transmission" ([ATL105 text line 22405](../specifications/ATL105/docs/specs/extracted_text_new.txt#L22405))

**Test data excerpt - [TC-077218](#tc-077218), step 1, `TD-TC-077218.json` (selected fields, actual then expected):**

```jsonc
// actual - line 8
"InformationByte": "A",
// expected
"InformationByte": "A",  // D-15: A value that meets: 1 character: 'A' = not a valid code.
```

<a id="d11"></a>

## Defect 11: Table 048 (3-D Secure authentication data) is now sent, but on POS purchases

**Priority:** Medium · **Kind:** To confirm with BUYPASS / SME · **Test cases:** 1 shown (8 in the run) · **Defect id:** C-12 (BA review 16/19-11-2026 (Table 048 not sent) - fixed: now sent; scope to confirm)

<details><summary>All 8 test cases of the run with this defect (click to expand)</summary>

[TC-075618](#tc-075618), [TC-075719](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L120), [TC-075819](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L220), [TC-076040](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L441), [TC-076138](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L539), [TC-076239](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L640), [TC-076346](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L747), [TC-076457](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L858)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-075618](#tc-075618) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L19)) | `ready` | EMV Contact Chip Purchase/Capture with MasterCard Authentication Data → approved | Happy path: Purchase/Capture sending Table 048 MasterCard Authentication Data | [TD-TC-075618.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075618.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-075618 | 1 | [TD-TC-075618.json line 27](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075618.json#L27) → `Variable Information Data Segment → Table 048` | `1` | Table 048 (3-D Secure authentication data) sent on a POS transaction |

### Expected behavior

- Confirm with BUYPASS; otherwise send Table 048 only in e-commerce (3-D Secure) tests, or re-scope the test.

### Why it matters

- Table 048 = '1' (Program Protocol 1 = EMV 3-D Secure Version 2.1). ATL105 applies this field to merchants sending e-commerce transactions with 3-D Secure / EMV 3-D Secure authentication. This step is a POS Purchase/Capture (Prompt Code 0020, Table 005 entry mode 05 = chip read) at a POS terminal, not an e-commerce transaction, so no 3-D Secure authentication took place and an approval does not show that BUYPASS processes MasterCard authentication data.

**BA explanation:** "The logged defect is fixed: the tests now send Table 048 (Program Protocol 1 = EMV 3-D Secure 2.1). But ATL105 applies Table 048 to e-commerce transactions authenticated with 3-D Secure, and these are POS chip, contactless, swiped and keyed purchases. BUYPASS may ignore or reject the table; either way the test does not show 3-D Secure handling."

### Acceptance criteria

- BUYPASS confirms whether Table 048 is accepted on POS transactions; if not, the tests are re-scoped to e-commerce (3-D Secure) or withdrawn.
- If kept, the test asserts how BUYPASS treats the table, not only the approval.

**Specification reference (ATL105, word for word):**

- Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048), PDF p.586 (printed Appendix I-37): "This field is applicable to merchants sending e-Commerce transactions with 3D Secure version or EMV 3D Secure for authentication." ([ATL105 text line 31314](../specifications/ATL105/docs/specs/extracted_text_new.txt#L31314))
- Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048), PDF p.586 (printed Appendix I-37): "Merchant should send the table length as ‘1’ when sending only Program Protocol, and table length should be ‘37’ when they support both Program Protocol and Directory Server Transaction ID." ([ATL105 text line 31308](../specifications/ATL105/docs/specs/extracted_text_new.txt#L31308))
- Appendix I. Variable Information Data Layouts - MasterCard Authentication Data (Table 048), PDF p.587 (printed Appendix I-38): "1 = EMV 3-D Secure Version 2.1" ([ATL105 text line 31328](../specifications/ATL105/docs/specs/extracted_text_new.txt#L31328))
- Element 111 Variable Information Indicator (Chapter 13), PDF p.424 (printed 13-84): "048 MasterCard Authentication Data" ([ATL105 text line 24111](../specifications/ATL105/docs/specs/extracted_text_new.txt#L24111))

**Test data excerpt - [TC-075618](#tc-075618), step 1, `TD-TC-075618.json` (selected fields, actual then expected):**

```jsonc
// actual - line 47
"VariableInformationIndicator": "048",
// expected
"VariableInformationIndicator": "048",  // C-12: Table 048 is for e-commerce 3-D Secure transactions - confirm with BUYPASS that it is accepted on this POS transaction, or re-scope / withdraw the test
```

<a id="d12"></a>

## Defect 12: Mail/Phone Authorization Only completed with Transaction Type 6 - the code is to be confirmed

**Priority:** Medium · **Kind:** To confirm with BUYPASS / SME · **Test cases:** 1 shown (10 in the run) · **Defect id:** S-05

<details><summary>All 10 test cases of the run with this defect (click to expand)</summary>

[TC-076721](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1122), [TC-076722](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1123), [TC-076725](#tc-076725), [TC-076728](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1129), [TC-076737](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1138), [TC-076760](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1161), [TC-076761](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1162), [TC-076764](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1165), [TC-076767](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1168), [TC-076770](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1171)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-076725](#tc-076725) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1126)) | `ready` | Manual / Keyed Mail/Phone Authorization Only, then Mail/Phone Purchase → approved | Valid flow: Mail/Phone Authorization Only -> Mail/Phone Purchase | [TD-TC-076725.step1.moto-auth-only.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076725.step1.moto-auth-only.json), [TD-TC-076725.step2.completion.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076725.step2.completion.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-076725 | 2 | [TD-TC-076725.step2.completion.json line 10](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076725.step2.completion.json#L10) → `Standard Message Data Segment → PromptCode` | `6020` | Completion of a Mail/Phone Authorization Only: Transaction Type to confirm |

### Expected behavior

- Confirm with BUYPASS whether a Mail/Phone Authorization Only is completed with Transaction Type 6 or 0.

### Why it matters

- Step 2 completes the Mail/Phone Authorization Only (Transaction Type B) of step 1 with Prompt Code '6020' (Transaction Type 6 = Mail/Phone Purchase) and the Approval Number. Appendix G names a completion code only for POS (0 = point-of-sale purchase/preauthorized completion) and customer-activated (4) purchases; Element 5 says a Purchase Request carrying an Approval Number is preauthorized. ATL105 does not say which Transaction Type completes a Mail/Phone Authorization Only.

**BA explanation:** "The completion of a Mail/Phone Authorization Only is sent as a Mail/Phone Purchase (6) with the Approval Number. ATL105 names completion codes only for POS (0) and customer-activated (4) purchases, so BUYPASS must confirm the code before these tests can be trusted."

### Acceptance criteria

- BUYPASS confirms the Transaction Type that completes a Mail/Phone Authorization Only, and the tests use it.

**Specification reference (ATL105, word for word):**

- Appendix G. Valid Transaction Type Codes, PDF p.545 (printed Appendix G-1): "0 POS Purchase/Capture Point-of-sale purchase/preauthorized completion" ([ATL105 text line 29405](../specifications/ATL105/docs/specs/extracted_text_new.txt#L29405))
- Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization." ([ATL105 text line 21570](../specifications/ATL105/docs/specs/extracted_text_new.txt#L21570))

**Test data excerpt - [TC-076725](#tc-076725), step 2, `TD-TC-076725.step2.completion.json` (selected fields, actual then expected):**

```jsonc
// actual - line 10
"PromptCode": {
// expected
"PromptCode": {  // S-05: Confirm with BUYPASS whether a Mail/Phone Authorization Only is completed with Transaction Type 6 or 0.
```

<a id="d13"></a>

## Defect 13: The test objective names a different transaction than the one sent

**Priority:** Medium · **Kind:** Test case issue to correct · **Test cases:** 1 shown (2 in the run) · **Defect id:** C-05

<details><summary>All 2 test cases of the run with this defect (click to expand)</summary>

[TC-076723](#tc-076723), [TC-076762](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1163)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-076723](#tc-076723) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1124)) | `ready` | Manual / Keyed Mail/Phone Authorization Only with Network Token Data Request sent → approved | Decision table: Authorization Only with segment 153 sent (the rule's condition holds) | [TD-TC-076723.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076723.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-076723 | - | test case record [test_cases.jsonl line 1124](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1124) → `test_objective` | `test_objective` = Decision table: Authorization Only with segment 153 sent (the rule's condition holds) | The test objective names a different transaction than the one sent |

### Expected behavior

- `test_objective` = `Decision table: Mail/Phone Authorization Only with segment 153 sent (the rule's condition holds)` - Correct the objective to Mail/Phone Authorization Only.

### Why it matters

- The objective says 'Authorization Only', but step 1 sends Prompt Code Transaction Type 'B' (Mail/Phone Authorization Only) and the name says Mail/Phone Authorization Only.

**BA explanation:** "The objective says one transaction, but the request (and the test name) is another. Coverage reports grouped by objective count this test in the wrong place."

### Acceptance criteria

- Objective, name and Prompt Code describe the same transaction.

**Specification reference (ATL105, word for word):**

- Element 78 Prompt Code (Chapter 13), PDF p.400 (printed 13-60): "this is a 4-character Prompt Code that identifies the transaction's Transaction Type (one character) and Card Type (three characters)." ([ATL105 text line 23036](../specifications/ATL105/docs/specs/extracted_text_new.txt#L23036))

<a id="d14"></a>

## Defect 14: Network Token Input Indicator and the Segment 153 / 108 lengths need confirmation

**Priority:** Medium · **Kind:** To confirm with BUYPASS / SME · **Test cases:** 5 shown (51 in the run) · **Defect id:** S-03, S-04

<details><summary>All 51 test cases of the run with this defect (click to expand)</summary>

[TC-075650](#tc-075650), [TC-075652](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L53), [TC-075655](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L56), [TC-075663](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L64), [TC-075684](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L85), [TC-075751](#tc-075751), [TC-075753](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L154), [TC-075756](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L157), [TC-075763](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L164), [TC-075784](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L185), [TC-075853](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L254), [TC-075855](#tc-075855), [TC-075858](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L259), [TC-075865](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L266), [TC-075915](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L316), [TC-076071](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L472), [TC-076073](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L474), [TC-076076](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L477), [TC-076083](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L484), [TC-076103](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L504), [TC-076169](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L570), [TC-076171](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L572), [TC-076174](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L575), [TC-076183](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L584), [TC-076204](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L605), [TC-076274](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L675), [TC-076276](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L677), [TC-076279](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L680), [TC-076286](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L687), [TC-076306](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L707), [TC-076379](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L780), [TC-076381](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L782), [TC-076384](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L785), [TC-076393](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L794), [TC-076415](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L816), [TC-076488](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L889), [TC-076490](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L891), [TC-076493](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L894), [TC-076499](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L900), [TC-076520](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L921), [TC-076591](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L992), [TC-076608](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1009), [TC-076625](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1026), [TC-076642](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1043), [TC-076658](#tc-076658), [TC-076674](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1075), [TC-076691](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1092), [TC-076707](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1108), [TC-076723](#tc-076723), [TC-076762](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1163), [TC-077205](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1606)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-076658](#tc-076658) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1059)) | `ready` | Manual / Keyed Authorization Only with Network Token Data Request sent → approved | Decision table: Authorization Only with segment 153 sent (the rule's condition holds) | [TD-TC-076658.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076658.json) |
| [TC-075650](#tc-075650) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L51)) | `requires_review` | EMV Contact Chip Purchase/Capture with Payment Tender Type sent where it is not allowed → declined (to confirm) | Single rule broken: Purchase/Capture with Payment Tender Type sent where the spec forbids it -> declined | [TD-TC-075650.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075650.json) |
| [TC-075751](#tc-075751) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L152)) | `requires_review` | EMV Contactless Chip Purchase/Capture with Payment Tender Type sent where it is not allowed → declined (to confirm) | Single rule broken: Purchase/Capture with Payment Tender Type sent where the spec forbids it -> declined | [TD-TC-075751.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075751.json) |
| [TC-076723](#tc-076723) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1124)) | `ready` | Manual / Keyed Mail/Phone Authorization Only with Network Token Data Request sent → approved | Decision table: Authorization Only with segment 153 sent (the rule's condition holds) | [TD-TC-076723.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076723.json) |
| [TC-075855](#tc-075855) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L256)) | `ready` | Magnetic Stripe Purchase/Capture with Payment Tender Type = AX → approved | Decision table: Purchase/Capture with Payment Tender Type = AX (the rule's condition holds) | [TD-TC-075855.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075855.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-076658 | 1 | [TD-TC-076658.json line 45](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076658.json#L45) → `Network Token Data Request → SegmentLengthIndicator` | `051` | Network Token Data Request: length depends on an unclear Field Separator rule |
| TC-076658 | 1 | [TD-TC-076658.json line 46](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076658.json#L46) → `Network Token Data Request → NetworkTokenData (table 004)` | `Y` | Network Token Input Indicator 'Y' while Element 2 is a card number |
| TC-075650 | 1 | [TD-TC-075650.json line 29](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075650.json#L29) → `Loyalty Card Data Segment → SegmentLength` | `120` | Loyalty Card Data Segment: length depends on an unclear Field Separator rule |
| TC-075751 | 1 | [TD-TC-075751.json line 29](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075751.json#L29) → `Loyalty Card Data Segment → SegmentLength` | `120` | Loyalty Card Data Segment: length depends on an unclear Field Separator rule |
| TC-076723 | 1 | [TD-TC-076723.json line 45](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076723.json#L45) → `Network Token Data Request → SegmentLengthIndicator` | `051` | Network Token Data Request: length depends on an unclear Field Separator rule |
| TC-076723 | 1 | [TD-TC-076723.json line 46](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076723.json#L46) → `Network Token Data Request → NetworkTokenData (table 004)` | `Y` | Network Token Input Indicator 'Y' while Element 2 is a card number |
| TC-075855 | 1 | [TD-TC-075855.json line 29](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075855.json#L29) → `Loyalty Card Data Segment → SegmentLength` | `120` | Loyalty Card Data Segment: length depends on an unclear Field Separator rule |

### Expected behavior

- Confirm the Field Separator rule of this segment with BUYPASS.
- Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number.

### Why it matters

- '051' assumes a Field Separator after every field (051). ATL105 reads differently for this segment - Field Separator only between Fields 1 and 2 (literal reading of p.310): 049; Field Separator after Fields 1 and 2, none after Field 3: 050.
- Table 004 'Y' says the merchant sent a network token, but Element 2 carries the card number 514031…3116 and the token is only in table 001. ATL105 does not say which field carries the token when the indicator is Y.
- '120' assumes a Field Separator after every field (120). ATL105 reads differently for this segment - Field Separators up to Field 13 only (literal reading of p.238): 118.

**BA explanation:** "The token flag says 'token' while the card number is sent in Element 2, and the segment length depends on a Field Separator rule ATL105 does not state clearly."

### Acceptance criteria

- BUYPASS confirms which field carries the token when table 004 = Y, and the Segment 153 and Segment 108 length rules.

**Specification reference (ATL105, word for word):**

- Section 12.38 Network Token Data Request Segment, PDF p.310 (printed 12-93): "There is a Field Separator between Field Nos. 1 and 2." ([ATL105 text line 18011](../specifications/ATL105/docs/specs/extracted_text_new.txt#L18011))
- Section 12.38 Network Token Data Request Segment, PDF p.310 (printed 12-93): "Field Separators are not there between the elements in Network Token Data Layouts." ([ATL105 text line 18012](../specifications/ATL105/docs/specs/extracted_text_new.txt#L18012))
- Section 12.38 Network Token Data Request Segment, PDF p.310 (printed 12-93): "2 84 Segment Length Indicator 3 R Indicates the data segment length including the Segment Type length"
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.169 (printed 11-6): "153 Network Token Data Request Segment 999 C Contains Network Token Data associated with this transaction." ([ATL105 text line 8054](../specifications/ATL105/docs/specs/extracted_text_new.txt#L8054))
- Section 12.38 Network Token Data Request Segment, PDF p.312 (printed 12-95): "Identifies that the Token or PAN is sent by the merchant Valid values are: Y – Network Token N – Clear PAN" ([ATL105 text line 18118](../specifications/ATL105/docs/specs/extracted_text_new.txt#L18118))
- Element 2 Account Number (Chapter 13), PDF p.364 (printed 13-24): "a variable length (up to 24 alphanumeric bytes) Account Number" ([ATL105 text line 21375](../specifications/ATL105/docs/specs/extracted_text_new.txt#L21375))
- Section 12.7 Loyalty Card Data Segment, PDF p.238 (printed 12-21): "All fields are separated by Field Separators, with a Field Separator following Field No. 13." ([ATL105 text line 13746](../specifications/ATL105/docs/specs/extracted_text_new.txt#L13746))
- Section 12.7 Loyalty Card Data Segment, PDF p.238 (printed 12-21): "It has a maximum length of 142 alphanumeric characters" ([ATL105 text line 13743](../specifications/ATL105/docs/specs/extracted_text_new.txt#L13743))

**Test data excerpt - [TC-076658](#tc-076658), step 1, `TD-TC-076658.json` (selected fields, actual then expected):**

```jsonc
// actual - line 45
"SegmentLengthIndicator": "051",
// expected
"SegmentLengthIndicator": "051",  // S-03: 051 = a Field Separator after every field; other readings of ATL105 give 049, 050 - confirm with BUYPASS
// actual - line 46
"NetworkTokenData": "00101967928410653764478340020042701004001Y"
// expected
"NetworkTokenData": "00101967928410653764478340020042701004001N"  // S-04: table 004 'N' (Clear PAN) if Element 2 keeps the card number - confirm with BUYPASS
```

<a id="d15"></a>

## Defect 15: Expiry format for stored credentials (entry mode 10) not stated

**Priority:** Medium · **Kind:** To confirm with BUYPASS / SME · **Test cases:** 2 shown (172 in the run) · **Defect id:** S-01

<details><summary>All 172 test cases of the run with this defect (click to expand)</summary>

[TC-076323](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L724), [TC-076324](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L725), [TC-076325](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L726), [TC-076326](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L727), [TC-076327](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L728), [TC-076328](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L729), [TC-076329](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L730), [TC-076330](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L731), [TC-076331](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L732), [TC-076332](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L733), [TC-076333](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L734), [TC-076334](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L735), [TC-076335](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L736), [TC-076336](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L737), [TC-076337](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L738), [TC-076338](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L739), [TC-076339](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L740), [TC-076340](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L741), [TC-076341](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L742), [TC-076342](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L743), [TC-076343](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L744), [TC-076344](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L745), [TC-076345](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L746), [TC-076346](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L747), [TC-076347](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L748), [TC-076348](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L749), [TC-076349](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L750), [TC-076350](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L751), [TC-076351](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L752), [TC-076352](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L753), [TC-076353](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L754), [TC-076354](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L755), [TC-076355](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L756), [TC-076356](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L757), [TC-076357](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L758), [TC-076358](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L759), [TC-076359](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L760), [TC-076360](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L761), [TC-076361](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L762), [TC-076362](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L763), [TC-076363](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L764), [TC-076364](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L765), [TC-076365](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L766), [TC-076366](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L767), [TC-076367](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L768), [TC-076368](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L769), [TC-076369](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L770), [TC-076370](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L771), [TC-076371](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L772), [TC-076372](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L773), [TC-076373](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L774), [TC-076374](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L775), [TC-076375](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L776), [TC-076376](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L777), [TC-076377](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L778), [TC-076378](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L779), [TC-076379](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L780), [TC-076380](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L781), [TC-076381](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L782), [TC-076382](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L783), [TC-076383](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L784), [TC-076384](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L785), [TC-076385](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L786), [TC-076386](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L787), [TC-076387](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L788), [TC-076388](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L789), [TC-076389](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L790), [TC-076390](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L791), [TC-076391](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L792), [TC-076392](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L793), [TC-076393](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L794), [TC-076394](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L795), [TC-076395](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L796), [TC-076396](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L797), [TC-076397](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L798), [TC-076398](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L799), [TC-076399](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L800), [TC-076400](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L801), [TC-076401](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L802), [TC-076403](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L804), [TC-076404](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L805), [TC-076405](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L806), [TC-076406](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L807), [TC-076407](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L808), [TC-076408](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L809), [TC-076409](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L810), [TC-076410](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L811), [TC-076411](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L812), [TC-076412](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L813), [TC-076413](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L814), [TC-076414](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L815), [TC-076415](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L816), [TC-076416](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L817), [TC-076417](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L818), [TC-076418](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L819), [TC-076419](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L820), [TC-076420](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L821), [TC-076421](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L822), [TC-076423](#tc-076423), [TC-076424](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L825), [TC-076425](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L826), [TC-076426](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L827), [TC-076427](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L828), [TC-076428](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L829), [TC-076429](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L830), [TC-076430](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L831), [TC-076431](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L832), [TC-076432](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L833), [TC-076433](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L834), [TC-076434](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L835), [TC-076567](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L968), [TC-076568](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L969), [TC-076569](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L970), [TC-076570](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L971), [TC-076571](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L972), [TC-076572](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L973), [TC-076573](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L974), [TC-076574](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L975), [TC-076575](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L976), [TC-076577](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L978), [TC-076578](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L979), [TC-076579](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L980), [TC-076580](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L981), [TC-076581](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L982), [TC-076582](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L983), [TC-076583](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L984), [TC-076585](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L986), [TC-076685](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1086), [TC-076686](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1087), [TC-076687](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1088), [TC-076688](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1089), [TC-076689](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1090), [TC-076690](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1091), [TC-076691](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1092), [TC-076692](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1093), [TC-076693](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1094), [TC-076694](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1095), [TC-076696](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1097), [TC-076697](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1098), [TC-076698](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1099), [TC-076699](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1100), [TC-076700](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1101), [TC-076756](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1157), [TC-076757](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1158), [TC-076758](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1159), [TC-076759](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1160), [TC-076760](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1161), [TC-076761](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1162), [TC-076762](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1163), [TC-076763](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1164), [TC-076764](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1165), [TC-076765](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1166), [TC-076766](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1167), [TC-076767](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1168), [TC-076768](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1169), [TC-076769](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1170), [TC-076770](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1171), [TC-076772](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1173), [TC-076835](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1236), [TC-076836](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1237), [TC-076837](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1238), [TC-076838](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1239), [TC-076839](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1240), [TC-076841](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1242), [TC-076842](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1243), [TC-076843](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1244), [TC-076844](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1245), [TC-076876](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1277), [TC-076877](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1278), [TC-077181](#tc-077181), [TC-077184](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1585), [TC-077188](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1589)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-077181](#tc-077181) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1582)) | `requires_review` | Credential on File Purchase/Capture NFC Payment Tokenization Data Segment omitted where requires it → declined | NFC Payment Tokenization Data Segment (segment 123) omitted where p.267 requires it | [TD-TC-077181.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077181.json) |
| [TC-076423](#tc-076423) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L824)) | `ready` | Credential on File Purchase/Capture sending Merchant Supplementary Data (Merchant Advice Code returned) → approved | Response / oracle check: Purchase/Capture sending Merchant Supplementary Data -> Merchant Advice Code returned | [TD-TC-076423.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076423.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-077181 | - | Card Discretionary Block Data (entry mode 10 steps) | `1131` | Expiry format for stored credentials is not stated |
| TC-076423 | - | Card Discretionary Block Data (entry mode 10 steps) | `0929` | Expiry format for stored credentials is not stated |

### Expected behavior

- Confirm the expiry format for entry mode 10 with BUYPASS.

### Why it matters

- The stored-credential steps send the expiry as MMYY, the format for manually entered cards. ATL105 also says that card data the merchant stored is sent as Account Number = YYMM. Entry mode 10 uses stored credentials, and the spec does not say which of the two applies.

### Acceptance criteria

- Confirm with BUYPASS.

**Specification reference (ATL105, word for word):**

- Element 12 Card Discretionary Block Data (Chapter 13), PDF p.372 (printed 13-32): "For a card whose number has been manually entered, the expiration date is in MMYY format" ([ATL105 text line 21761](../specifications/ATL105/docs/specs/extracted_text_new.txt#L21761))
- Section 10.1.8.2 Track Support Requirements, PDF p.94 (printed 10-15): "The host message should only contain the Account Number, followed by an account delimiter (=), followed by the 4 digit Expiration Date (Storage format – YYMM)." ([ATL105 text line 4564](../specifications/ATL105/docs/specs/extracted_text_new.txt#L4564))

<a id="d16"></a>

## Defect 16: Other points to confirm

**Priority:** Medium · **Kind:** To confirm with BUYPASS / SME · **Test cases:** 2 shown (15 in the run) · **Defect id:** S-05

<details><summary>All 15 test cases of the run with this defect (click to expand)</summary>

[TC-076021](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L422), [TC-076589](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L990), [TC-076590](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L991), [TC-076593](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L994), [TC-076597](#tc-076597), [TC-076601](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1002), [TC-076606](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1007), [TC-076607](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1008), [TC-076610](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1011), [TC-076614](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1015), [TC-076618](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1019), [TC-077112](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1513), [TC-077117](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1518), [TC-077181](#tc-077181), [TC-077182](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1583)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-077181](#tc-077181) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1582)) | `requires_review` | Credential on File Purchase/Capture NFC Payment Tokenization Data Segment omitted where requires it → declined | NFC Payment Tokenization Data Segment (segment 123) omitted where p.267 requires it | [TD-TC-077181.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077181.json) |
| [TC-076597](#tc-076597) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L998)) | `requires_review` | EMV Contact Chip Authorization Only, then Purchase/Capture, then Totals Request (totals include it) → approved | Running total / state: Authorization Only -> Purchase/Capture -> Totals Request | [TD-TC-076597.step1.auth-only.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076597.step1.auth-only.json), [TD-TC-076597.step2.completion.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076597.step2.completion.json), [TD-TC-076597.step3.totals.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076597.step3.totals.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-077181 | - | test case record [test_cases.jsonl line 1582](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1582) → `test_objective` | `test_objective` = NFC Payment Tokenization Data Segment (segment 123) omitted where p.267 requires it | Confirm the premise of this negative test |
| TC-076597 | 2 | TD-TC-076597.step2.completion.json | `(absent)` | The completion of an EMV authorization carries no chip data |

### Expected behavior

- Confirm with BUYPASS, or send a transaction for which the segment is required.
- Confirm with BUYPASS whether a completion of an EMV authorization is sent without Segment 130.

### Why it matters

- p.267 requires Segment 123 only on transactions involving tokenized data (MasterCard Token, DSRP or Visa TAVV data). This test sends a card number without any token data, so leaving the segment out may be correct and BUYPASS may approve.
- ATL105 says EMV data is not required on reversal transactions, but it says nothing about completions. The completion is a preauthorized Purchase/Capture (it carries the authorization's Approval Number).

### Acceptance criteria

- Confirm with BUYPASS / SME.

**Specification reference (ATL105, word for word):**

- Section 12.19 NFC Payment Tokenization Data Segment, PDF p.267 (printed 12-50): "It is required on all initial and recurring transactions involving tokenized data and for transactions that include MasterCard Token or Digital Secure Remote Payment (DSRP) or Visa TAVV data." ([ATL105 text line 15474](../specifications/ATL105/docs/specs/extracted_text_new.txt#L15474))
- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "123 NFC Payment Tokenization Data Segment 186 C Contains data needed to process NFC tokenized transactions." ([ATL105 text line 7987](../specifications/ATL105/docs/specs/extracted_text_new.txt#L7987))
- Section 10.14.2.4 Supported Transaction Types (EMV), PDF p.161 (printed 10-82): "EMV data is not required on Reversal transactions." ([ATL105 text line 7663](../specifications/ATL105/docs/specs/extracted_text_new.txt#L7663))
- Element 5 Approval Number (Chapter 13), PDF p.368 (printed 13-28): "If it is included in a Purchase Request, then the Purchase Request is considered preauthorized, and BUYPASS does not go out for authorization." ([ATL105 text line 21570](../specifications/ATL105/docs/specs/extracted_text_new.txt#L21570))

<a id="d17"></a>

## Defect 17: Expected Response Code not stated for a step, or several codes accepted

**Priority:** Medium · **Kind:** Test case issue to correct · **Test cases:** 6 shown (256 in the run) · **Defect id:** C-04

<details><summary>All 256 test cases of the run with this defect (click to expand)</summary>

[TC-075666](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L67), [TC-075667](#tc-075667), [TC-075669](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L70), [TC-075671](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L72), [TC-075672](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L73), [TC-075673](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L74), [TC-075674](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L75), [TC-075676](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L77), [TC-075678](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L79), [TC-075679](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L80), [TC-075766](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L167), [TC-075767](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L168), [TC-075769](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L170), [TC-075771](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L172), [TC-075772](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L173), [TC-075773](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L174), [TC-075774](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L175), [TC-075776](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L177), [TC-075778](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L179), [TC-075779](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L180), [TC-075877](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L278), [TC-075878](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L279), [TC-075880](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L281), [TC-075882](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L283), [TC-075883](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L284), [TC-075884](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L285), [TC-075885](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L286), [TC-075887](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L288), [TC-075889](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L290), [TC-075890](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L291), [TC-076086](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L487), [TC-076087](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L488), [TC-076089](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L490), [TC-076091](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L492), [TC-076092](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L493), [TC-076093](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L494), [TC-076094](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L495), [TC-076096](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L497), [TC-076098](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L499), [TC-076099](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L500), [TC-076187](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L588), [TC-076188](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L589), [TC-076190](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L591), [TC-076192](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L593), [TC-076193](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L594), [TC-076194](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L595), [TC-076195](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L596), [TC-076197](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L598), [TC-076199](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L600), [TC-076200](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L601), [TC-076289](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L690), [TC-076290](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L691), [TC-076292](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L693), [TC-076294](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L695), [TC-076295](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L696), [TC-076296](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L697), [TC-076297](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L698), [TC-076299](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L700), [TC-076301](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L702), [TC-076302](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L703), [TC-076398](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L799), [TC-076399](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L800), [TC-076401](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L802), [TC-076403](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L804), [TC-076404](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L805), [TC-076405](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L806), [TC-076406](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L807), [TC-076408](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L809), [TC-076410](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L811), [TC-076411](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L812), [TC-076502](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L903), [TC-076503](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L904), [TC-076505](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L906), [TC-076507](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L908), [TC-076508](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L909), [TC-076509](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L910), [TC-076510](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L911), [TC-076512](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L913), [TC-076514](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L915), [TC-076515](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L916), [TC-076541](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L942), [TC-076542](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L943), [TC-076543](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L944), [TC-076544](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L945), [TC-076546](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L947), [TC-076547](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L948), [TC-076548](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L949), [TC-076572](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L973), [TC-076573](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L974), [TC-076574](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L975), [TC-076575](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L976), [TC-076577](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L978), [TC-076578](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L979), [TC-076579](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L980), [TC-076580](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L981), [TC-076592](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L993), [TC-076594](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L995), [TC-076596](#tc-076596), [TC-076609](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1010), [TC-076611](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1012), [TC-076613](#tc-076613), [TC-076626](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1027), [TC-076628](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1029), [TC-076630](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1031), [TC-076643](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1044), [TC-076645](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1046), [TC-076647](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1048), [TC-076659](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1060), [TC-076661](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1062), [TC-076663](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1064), [TC-076675](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1076), [TC-076677](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1078), [TC-076679](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1080), [TC-076692](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1093), [TC-076694](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1095), [TC-076696](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1097), [TC-076708](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1109), [TC-076710](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1111), [TC-076712](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1113), [TC-076724](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1125), [TC-076726](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1127), [TC-076727](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1128), [TC-076763](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1164), [TC-076765](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1166), [TC-076766](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1167), [TC-076773](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1174), [TC-076774](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1175), [TC-076775](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1176), [TC-076776](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1177), [TC-076777](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1178), [TC-076779](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1180), [TC-076780](#tc-076780), [TC-076781](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1182), [TC-076782](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1183), [TC-076784](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1185), [TC-076785](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1186), [TC-076786](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1187), [TC-076787](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1188), [TC-076788](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1189), [TC-076790](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1191), [TC-076791](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1192), [TC-076792](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1193), [TC-076793](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1194), [TC-076795](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1196), [TC-076796](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1197), [TC-076797](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1198), [TC-076798](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1199), [TC-076799](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1200), [TC-076801](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1202), [TC-076802](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1203), [TC-076803](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1204), [TC-076805](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1206), [TC-076806](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1207), [TC-076807](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1208), [TC-076808](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1209), [TC-076809](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1210), [TC-076811](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1212), [TC-076812](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1213), [TC-076813](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1214), [TC-076815](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1216), [TC-076816](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1217), [TC-076817](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1218), [TC-076818](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1219), [TC-076819](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1220), [TC-076821](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1222), [TC-076822](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1223), [TC-076823](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1224), [TC-076825](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1226), [TC-076826](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1227), [TC-076827](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1228), [TC-076828](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1229), [TC-076829](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1230), [TC-076831](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1232), [TC-076832](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1233), [TC-076833](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1234), [TC-076835](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1236), [TC-076836](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1237), [TC-076837](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1238), [TC-076838](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1239), [TC-076839](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1240), [TC-076841](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1242), [TC-076842](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1243), [TC-076843](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1244), [TC-076844](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1245), [TC-076846](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1247), [TC-076847](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1248), [TC-076848](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1249), [TC-076849](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1250), [TC-076850](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1251), [TC-076852](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1253), [TC-076853](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1254), [TC-076854](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1255), [TC-076856](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1257), [TC-076857](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1258), [TC-076858](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1259), [TC-076860](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1261), [TC-076861](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1262), [TC-076862](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1263), [TC-076864](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1265), [TC-076865](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1266), [TC-076867](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1268), [TC-076868](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1269), [TC-076870](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1271), [TC-076871](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1272), [TC-076873](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1274), [TC-076874](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1275), [TC-076876](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1277), [TC-076877](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1278), [TC-076879](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1280), [TC-076880](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1281), [TC-076915](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1316), [TC-076927](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1328), [TC-076942](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1343), [TC-076962](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1363), [TC-076973](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1374), [TC-076984](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1385), [TC-076995](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1396), [TC-077018](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1419), [TC-077019](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1420), [TC-077020](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1421), [TC-077021](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1422), [TC-077022](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1423), [TC-077023](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1424), [TC-077024](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1425), [TC-077028](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1429), [TC-077030](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1431), [TC-077042](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1443), [TC-077044](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1445), [TC-077059](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1460), [TC-077061](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1462), [TC-077085](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1486), [TC-077087](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1488), [TC-077098](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1499), [TC-077100](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1501), [TC-077111](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1512), [TC-077113](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1514), [TC-077116](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1517), [TC-077118](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1519), [TC-077121](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1522), [TC-077123](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1524), [TC-077126](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1527), [TC-077128](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1529), [TC-077131](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1532), [TC-077133](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1534), [TC-077137](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1538), [TC-077139](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1540), [TC-077143](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1544), [TC-077145](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1546), [TC-077149](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1550), [TC-077151](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1552) and 6 more - every one is in [Defects_in_the_run.csv](Defects_in_the_run.csv)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-076596](#tc-076596) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L997)) | `requires_review` | EMV Contact Chip Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | Running total / state: Authorization Only -> Cancellation -> Totals Request | [TD-TC-076596.step1.auth-only.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076596.step1.auth-only.json), [TD-TC-076596.step2.cancellation.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076596.step2.cancellation.json), [TD-TC-076596.step3.totals.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076596.step3.totals.json) |
| [TC-076613](#tc-076613) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1014)) | `requires_review` | EMV Contactless Chip Authorization Only, then Cancellation, then Totals Request (totals include it) → approved | Running total / state: Authorization Only -> Cancellation -> Totals Request | [TD-TC-076613.step1.auth-only.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076613.step1.auth-only.json), [TD-TC-076613.step2.cancellation.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076613.step2.cancellation.json), [TD-TC-076613.step3.totals.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076613.step3.totals.json) |
| [TC-075667](#tc-075667) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L68)) | `ready` | EMV Contact Chip Purchase/Capture, then Time-out Reversal → approved | Valid flow: Purchase/Capture -> Time-out Reversal | [TD-TC-075667.step1.purchase.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075667.step1.purchase.json), [TD-TC-075667.step2.timeout-reversal.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075667.step2.timeout-reversal.json) |
| [TC-077185](#tc-077185) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1586)) | `requires_review` | Magnetic Stripe Balance Inquiry Merchant Trace ID exactly at its documented maximum length (25 bytes) is accepted → approved | Merchant Trace ID exactly at its documented maximum length (25 bytes) is accepted | [TD-TC-077185.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077185.json) |
| [TC-077218](#tc-077218) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1619)) | `requires_review` | EMV Contact Chip Balance Inquiry Information Byte exactly at its documented maximum length (1 bytes) is accepted → approved | Information Byte exactly at its documented maximum length (1 bytes) is accepted | [TD-TC-077218.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077218.json) |
| [TC-076780](#tc-076780) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1181)) | `requires_review` | EMV Contact Chip Merchandise Return, then Totals Request (totals include it) → approved | Running total / state: Merchandise Return approved -> Totals Request | [TD-TC-076780.step1.return.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076780.step1.return.json), [TD-TC-076780.step2.totals.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076780.step2.totals.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-076596 | - | test case record [test_cases.jsonl line 997](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L997) → `step 2 expected_response` | `step 2 expected_response` = `null` | The test case does not state the expected Response Code |
| TC-076613 | - | test case record [test_cases.jsonl line 1014](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1014) → `step 2 expected_response` | `step 2 expected_response` = `null` | The test case does not state the expected Response Code |
| TC-075667 | - | test case record [test_cases.jsonl line 68](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L68) → `expected_response.response_code (step 2)` | `expected_response.response_code (step 2)` = `null` | The test case accepts several Response Codes instead of one |
| TC-077185 | - | test case record [test_cases.jsonl line 1586](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1586) → `step 1 expected_response` | `step 1 expected_response` = `null` | The test case does not state the expected Response Code |
| TC-077218 | - | test case record [test_cases.jsonl line 1619](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1619) → `step 1 expected_response` | `step 1 expected_response` = `null` | The test case does not state the expected Response Code |
| TC-076780 | - | test case record [test_cases.jsonl line 1181](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1181) → `step 1 expected_response` | `step 1 expected_response` = `null` | The test case does not state the expected Response Code |

### Expected behavior

- State the expected Response Code for this transaction type (from BUYPASS or a reference run).
- State the one expected Response Code for this transaction type (from BUYPASS or a reference run).

### Why it matters

- The expected response is left open ('?'), so a run cannot pass or fail on the Response Code. Element 83 has a separate approval code for each kind of transaction.
- The test case accepts any of 0 = Approved - Purchase/Capture; 2 = Approved - Authorization only for the timeout reversal step. Element 83 has a separate approval code for each kind of transaction, so the test should state the one code BUYPASS returns.

### Acceptance criteria

- State the expected Response Code (from BUYPASS or a reference run).

**Specification reference (ATL105, word for word):**

- Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "0 Approved—Purchase/Capture" ([ATL105 text line 23207](../specifications/ATL105/docs/specs/extracted_text_new.txt#L23207))
- Element 83 Response Code (Chapter 13), PDF p.404 (printed 13-64): "2 Approved—Authorization only" ([ATL105 text line 23210](../specifications/ATL105/docs/specs/extracted_text_new.txt#L23210))

<a id="d18"></a>

## Defect 18: Same request as another test case (difference only on the device / response side)

**Priority:** Medium · **Kind:** To confirm with BUYPASS / SME · **Test cases:** 1 shown (124 in the run) · **Defect id:** C-08

<details><summary>All 124 test cases of the run with this defect (click to expand)</summary>

[TC-075810](#tc-075810), [TC-075837](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L238), [TC-075838](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L239), [TC-075839](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L240), [TC-075840](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L241), [TC-075841](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L242), [TC-075842](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L243), [TC-075843](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L244), [TC-075844](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L245), [TC-075845](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L246), [TC-075846](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L247), [TC-075847](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L248), [TC-075848](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L249), [TC-075849](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L250), [TC-075859](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L260), [TC-075867](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L268), [TC-075937](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L338), [TC-075938](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L339), [TC-075940](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L341), [TC-075941](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L342), [TC-075942](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L343), [TC-075943](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L344), [TC-075944](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L345), [TC-075945](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L346), [TC-075946](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L347), [TC-075947](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L348), [TC-075948](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L349), [TC-075949](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L350), [TC-076031](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L432), [TC-076058](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L459), [TC-076059](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L460), [TC-076060](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L461), [TC-076061](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L462), [TC-076062](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L463), [TC-076063](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L464), [TC-076064](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L465), [TC-076065](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L466), [TC-076066](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L467), [TC-076067](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L468), [TC-076077](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L478), [TC-076085](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L486), [TC-076129](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L530), [TC-076156](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L557), [TC-076157](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L558), [TC-076158](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L559), [TC-076159](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L560), [TC-076160](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L561), [TC-076161](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L562), [TC-076162](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L563), [TC-076163](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L564), [TC-076164](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L565), [TC-076165](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L566), [TC-076175](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L576), [TC-076180](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L581), [TC-076185](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L586), [TC-076186](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L587), [TC-076230](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L631), [TC-076257](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L658), [TC-076258](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L659), [TC-076259](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L660), [TC-076260](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L661), [TC-076261](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L662), [TC-076262](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L663), [TC-076263](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L664), [TC-076264](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L665), [TC-076265](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L666), [TC-076266](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L667), [TC-076267](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L668), [TC-076268](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L669), [TC-076269](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L670), [TC-076270](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L671), [TC-076280](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L681), [TC-076288](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L689), [TC-076324](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L725), [TC-076326](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L727), [TC-076337](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L738), [TC-076366](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L767), [TC-076367](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L768), [TC-076368](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L769), [TC-076369](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L770), [TC-076370](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L771), [TC-076371](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L772), [TC-076372](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L773), [TC-076373](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L774), [TC-076374](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L775), [TC-076375](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L776), [TC-076385](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L786), [TC-076388](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L789), [TC-076395](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L796), [TC-076436](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L837), [TC-076437](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L838), [TC-076438](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L839), [TC-076448](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L849), [TC-076475](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L876), [TC-076476](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L877), [TC-076477](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L878), [TC-076478](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L879), [TC-076479](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L880), [TC-076480](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L881), [TC-076481](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L882), [TC-076482](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L883), [TC-076483](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L884), [TC-076484](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L885), [TC-076494](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L895), [TC-076501](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L902), [TC-076568](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L969), [TC-076686](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1087), [TC-076741](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1142), [TC-076742](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1143), [TC-076743](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1144), [TC-076744](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1145), [TC-076745](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1146), [TC-076746](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1147), [TC-076747](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1148), [TC-076748](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1149), [TC-076757](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1158), [TC-076937](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1338), [TC-076960](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1361), [TC-076971](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1372), [TC-076982](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1383), [TC-076993](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1394), [TC-077055](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1456), [TC-077084](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1485), [TC-077097](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1498)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-075810](#tc-075810) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L211)) | `ready` | Magnetic Stripe Purchase/Capture with Point-of-Service Terminal Capability Codes → approved | Happy path: Purchase/Capture sending Table 031 Point-of-Service Terminal Capability Codes | [TD-TC-075810.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075810.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-075810 | 1 | TD-TC-075810.json vs TD-TC-075801.json | `expected 0 vs 0` | Same request as TC-075801 (BR1_TS3_TC3) |

### Expected behavior

- Check the difference on the device / in the response; optionally use another card.

### Why it matters

- Apart from the Sequence Number the message is identical to TC-075801 (all segments compared). TC-075801 expects 0; this test expects 0. What makes the two tests different is not in the message sent to BUYPASS.

### Acceptance criteria

- Check the difference on the device or in the response; optionally use another card.

<a id="d19"></a>

## Defect 19: Totals: Authorization Only (AO) bucket after the flow

**Priority:** Medium · **Kind:** To confirm with BUYPASS / SME · **Test cases:** 1 shown (20 in the run) · **Defect id:** S-02

<details><summary>All 20 test cases of the run with this defect (click to expand)</summary>

[TC-076597](#tc-076597), [TC-076598](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L999), [TC-076614](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1015), [TC-076615](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1016), [TC-076631](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1032), [TC-076632](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1033), [TC-076648](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1049), [TC-076649](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1050), [TC-076664](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1065), [TC-076665](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1066), [TC-076680](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1081), [TC-076681](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1082), [TC-076697](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1098), [TC-076698](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1099), [TC-076713](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1114), [TC-076714](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1115), [TC-076728](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1129), [TC-076729](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1130), [TC-076767](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1168), [TC-076768](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1169)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-076597](#tc-076597) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L998)) | `requires_review` | EMV Contact Chip Authorization Only, then Purchase/Capture, then Totals Request (totals include it) → approved | Running total / state: Authorization Only -> Purchase/Capture -> Totals Request | [TD-TC-076597.step1.auth-only.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076597.step1.auth-only.json), [TD-TC-076597.step2.completion.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076597.step2.completion.json), [TD-TC-076597.step3.totals.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076597.step3.totals.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-076597 | 3 | [TD-TC-076597.step3.totals.json line 5](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076597.step3.totals.json#L5) → `Totals Data Segment` | `CC  ` | The Authorization Only step has no AO bucket in the totals |

### Expected behavior

- Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed.

### Why it matters

- Section 12.6 lists Preauthorization (AO) as bucket 4, and the grand total leaves the AO bucket out. This flow has an approved Authorization Only, but only the CC bucket is sent. ATL105 says cancellations subtract from the AO bucket; it does not say whether a completed authorization stays in it.

### Acceptance criteria

- Confirm with BUYPASS.

**Specification reference (ATL105, word for word):**

- Section 12.6 Totals Data Segment, PDF p.237 (printed 12-20): "Field Nos. 15–17 are sent up to a maximum of 18 times—once for each of the following card types—in the order shown and only when data occurs for a card type." ([ATL105 text line 13692](../specifications/ATL105/docs/specs/extracted_text_new.txt#L13692))
- Section 9.2 Local Totals, PDF p.72 (printed 9-8): "The grand total includes all transactions except the non-financial transactions grouped in the following categories: • AO—Authorization only" ([ATL105 text line 3719](../specifications/ATL105/docs/specs/extracted_text_new.txt#L3719))

**Test data excerpt - [TC-076597](#tc-076597), step 3, `TD-TC-076597.step3.totals.json` (selected fields, actual then expected):**

```jsonc
// actual - line 5
"Totals Data Segment": {
// expected
"Totals Data Segment": {  // D-01: Count 00001, amount 00001500, grand total 00001500; S-02: Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed.
```

<a id="d20"></a>

## Defect 20: Totals after a Merchandise Return: the net amount is negative, but ATL105 defines no sign for the totals amounts

**Priority:** Medium · **Kind:** To confirm with BUYPASS / SME · **Test cases:** 1 shown (8 in the run) · **Defect id:** S-06

<details><summary>All 8 test cases of the run with this defect (click to expand)</summary>

[TC-076780](#tc-076780), [TC-076791](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1192), [TC-076802](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1203), [TC-076812](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1213), [TC-076822](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1223), [TC-076832](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1233), [TC-076843](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1244), [TC-076853](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1254)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-076780](#tc-076780) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1181)) | `requires_review` | EMV Contact Chip Merchandise Return, then Totals Request (totals include it) → approved | Running total / state: Merchandise Return approved -> Totals Request | [TD-TC-076780.step1.return.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076780.step1.return.json), [TD-TC-076780.step2.totals.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076780.step2.totals.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-076780 | 2 | [TD-TC-076780.step2.totals.json line 5](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076780.step2.totals.json#L5) → `Totals Data Segment` | `count 00001, amount 00000000, grand total 00000000` | The totals do not match the transactions of the flow |

### Expected behavior

- Confirm with BUYPASS how a negative net amount is reported in the Totals Request.

### Why it matters

- Section 9.2: a Merchandise Return increases the count and decreases the dollar amount, so the card bucket nets to count 00001 and amount -$15.00. Card Type Total Amount (Element 15) is a fixed-length unsigned 8-digit amount and ATL105 does not say how a negative net amount is sent; the file sends 00000000.

### Acceptance criteria

- Confirm with BUYPASS how a negative net amount is reported in the Totals Request.

**Specification reference (ATL105, word for word):**

- Section 9.2 Local Totals, PDF p.72 (printed 9-8): "A Merchandise Return increases the count and decreases the dollar amount for the corresponding category as well as the grand total." ([ATL105 text line 3733](../specifications/ATL105/docs/specs/extracted_text_new.txt#L3733))
- Element 15 Card Type Total Amount (Chapter 13), PDF p.374 (printed 13-34): "Fixed length of eight digits with two assumed decimal places" ([ATL105 text line 21868](../specifications/ATL105/docs/specs/extracted_text_new.txt#L21868))

**Test data excerpt - [TC-076780](#tc-076780), step 2, `TD-TC-076780.step2.totals.json` (selected fields, actual then expected):**

```jsonc
// actual - line 5
"Totals Data Segment": {
// expected
"Totals Data Segment": {  // S-06: Confirm with BUYPASS how a negative net amount is reported in the Totals Request.
```

<a id="d21"></a>

## Defect 21: Segment sent although ATL105 scopes it to other card programs

**Priority:** Medium · **Kind:** To confirm with BUYPASS / SME · **Test cases:** 1 shown (2 in the run) · **Defect id:** C-09

<details><summary>All 2 test cases of the run with this defect (click to expand)</summary>

[TC-077203](#tc-077203), [TC-077204](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1605)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-077203](#tc-077203) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1604)) | `needs_client_input` | SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture Customer Data contains a character outside its character type (AN) → declined | Customer Data contains a character outside its character type (AN) | [TD-TC-077203.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077203.json) |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-077203 | 1 | [TD-TC-077203.json line 27](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077203.json#L27) → `Fleet Data Segment` | `Segment 101` | Fleet Data Segment sent on a MasterCard Credit card |

### Expected behavior

- Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it.

### Why it matters

- ATL105 describes Segment 101 as fleet data, sent only on transactions requiring fleet data. This test uses a MasterCard Credit card (card type 020), which is not a fleet card.

### Acceptance criteria

- Confirm with BUYPASS or drop the segment from MasterCard Credit tests.

**Specification reference (ATL105, word for word):**

- Section 11.1 Financial Transactions (Data Section No. 3), PDF p.168 (printed 11-5): "101 Fleet Data Segment 308 C Contains fleet-specific data. Sent only on transactions requiring fleet data." ([ATL105 text line 7964](../specifications/ATL105/docs/specs/extracted_text_new.txt#L7964))

**Test data excerpt - [TC-077203](#tc-077203), step 1, `TD-TC-077203.json` (selected fields, actual then expected):**

```jsonc
// actual - line 27
"Fleet Data Segment": {
// expected
"Fleet Data Segment": {  // C-09: Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it.
```

<a id="d22"></a>

## Defect 22: ATL105 citations in the test cases are on the wrong page, not ATL105 text, or about another context

**Priority:** Low · **Kind:** Citation issue · **Test cases:** 33 shown (831 in the run) · **Defect id:** Q-01, Q-02, Q-03, Q-04

<details><summary>All 831 test cases of the run with this defect (click to expand)</summary>

[TC-075600](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1), [TC-075601](#tc-075601), [TC-075602](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L3), [TC-075603](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L4), [TC-075604](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L5), [TC-075605](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L6), [TC-075606](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L7), [TC-075607](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L8), [TC-075608](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L9), [TC-075609](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L10), [TC-075610](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L11), [TC-075611](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L12), [TC-075612](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L13), [TC-075613](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L14), [TC-075614](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L15), [TC-075615](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L16), [TC-075616](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L17), [TC-075617](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L18), [TC-075618](#tc-075618), [TC-075619](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L20), [TC-075620](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L21), [TC-075621](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L22), [TC-075622](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L23), [TC-075623](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L24), [TC-075624](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L25), [TC-075625](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L26), [TC-075626](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L27), [TC-075627](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L28), [TC-075628](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L29), [TC-075629](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L30), [TC-075630](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L31), [TC-075631](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L32), [TC-075632](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L33), [TC-075633](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L34), [TC-075634](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L35), [TC-075635](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L36), [TC-075636](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L37), [TC-075637](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L38), [TC-075638](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L39), [TC-075639](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L40), [TC-075640](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L41), [TC-075641](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L42), [TC-075642](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L43), [TC-075643](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L44), [TC-075644](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L45), [TC-075645](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L46), [TC-075646](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L47), [TC-075647](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L48), [TC-075648](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L49), [TC-075649](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L50), [TC-075650](#tc-075650), [TC-075651](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L52), [TC-075652](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L53), [TC-075653](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L54), [TC-075654](#tc-075654), [TC-075655](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L56), [TC-075656](#tc-075656), [TC-075657](#tc-075657), [TC-075658](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L59), [TC-075659](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L60), [TC-075660](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L61), [TC-075661](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L62), [TC-075662](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L63), [TC-075663](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L64), [TC-075664](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L65), [TC-075665](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L66), [TC-075666](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L67), [TC-075667](#tc-075667), [TC-075668](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L69), [TC-075669](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L70), [TC-075670](#tc-075670), [TC-075671](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L72), [TC-075672](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L73), [TC-075673](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L74), [TC-075674](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L75), [TC-075675](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L76), [TC-075676](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L77), [TC-075677](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L78), [TC-075678](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L79), [TC-075679](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L80), [TC-075680](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L81), [TC-075681](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L82), [TC-075682](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L83), [TC-075683](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L84), [TC-075684](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L85), [TC-075685](#tc-075685), [TC-075686](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L87), [TC-075687](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L88), [TC-075688](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L89), [TC-075689](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L90), [TC-075690](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L91), [TC-075691](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L92), [TC-075692](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L93), [TC-075693](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L94), [TC-075694](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L95), [TC-075695](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L96), [TC-075696](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L97), [TC-075697](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L98), [TC-075698](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L99), [TC-075699](#tc-075699), [TC-075700](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L101), [TC-075701](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L102), [TC-075702](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L103), [TC-075703](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L104), [TC-075704](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L105), [TC-075705](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L106), [TC-075706](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L107), [TC-075707](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L108), [TC-075708](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L109), [TC-075709](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L110), [TC-075710](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L111), [TC-075711](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L112), [TC-075712](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L113), [TC-075713](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L114), [TC-075714](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L115), [TC-075715](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L116), [TC-075716](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L117), [TC-075717](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L118), [TC-075718](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L119), [TC-075719](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L120), [TC-075720](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L121), [TC-075721](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L122), [TC-075722](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L123), [TC-075723](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L124), [TC-075724](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L125), [TC-075725](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L126), [TC-075726](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L127), [TC-075727](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L128), [TC-075728](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L129), [TC-075729](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L130), [TC-075730](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L131), [TC-075731](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L132), [TC-075732](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L133), [TC-075733](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L134), [TC-075734](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L135), [TC-075735](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L136), [TC-075736](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L137), [TC-075737](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L138), [TC-075738](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L139), [TC-075739](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L140), [TC-075740](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L141), [TC-075741](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L142), [TC-075742](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L143), [TC-075743](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L144), [TC-075744](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L145), [TC-075745](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L146), [TC-075746](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L147), [TC-075747](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L148), [TC-075748](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L149), [TC-075749](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L150), [TC-075750](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L151), [TC-075751](#tc-075751), [TC-075752](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L153), [TC-075753](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L154), [TC-075754](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L155), [TC-075755](#tc-075755), [TC-075756](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L157), [TC-075757](#tc-075757), [TC-075758](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L159), [TC-075759](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L160), [TC-075760](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L161), [TC-075761](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L162), [TC-075762](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L163), [TC-075763](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L164), [TC-075764](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L165), [TC-075765](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L166), [TC-075766](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L167), [TC-075767](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L168), [TC-075768](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L169), [TC-075769](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L170), [TC-075770](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L171), [TC-075771](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L172), [TC-075772](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L173), [TC-075773](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L174), [TC-075774](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L175), [TC-075775](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L176), [TC-075776](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L177), [TC-075777](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L178), [TC-075778](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L179), [TC-075779](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L180), [TC-075780](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L181), [TC-075781](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L182), [TC-075782](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L183), [TC-075783](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L184), [TC-075784](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L185), [TC-075785](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L186), [TC-075786](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L187), [TC-075787](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L188), [TC-075788](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L189), [TC-075789](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L190), [TC-075790](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L191), [TC-075791](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L192), [TC-075792](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L193), [TC-075793](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L194), [TC-075794](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L195), [TC-075795](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L196), [TC-075796](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L197), [TC-075797](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L198), [TC-075798](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L199), [TC-075799](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L200), [TC-075800](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L201), [TC-075802](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L203), [TC-075803](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L204), [TC-075804](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L205), [TC-075805](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L206), [TC-075806](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L207), [TC-075807](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L208), [TC-075808](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L209), [TC-075809](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L210), [TC-075810](#tc-075810), [TC-075811](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L212), [TC-075812](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L213), [TC-075813](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L214), [TC-075814](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L215), [TC-075815](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L216), [TC-075816](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L217), [TC-075817](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L218), [TC-075818](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L219), [TC-075819](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L220), [TC-075820](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L221), [TC-075821](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L222), [TC-075822](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L223), [TC-075823](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L224), [TC-075824](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L225), [TC-075825](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L226), [TC-075826](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L227), [TC-075827](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L228), [TC-075828](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L229), [TC-075829](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L230), [TC-075830](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L231), [TC-075831](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L232), [TC-075832](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L233), [TC-075833](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L234), [TC-075834](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L235), [TC-075835](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L236), [TC-075836](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L237), [TC-075847](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L248), [TC-075850](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L251), [TC-075851](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L252), [TC-075852](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L253), [TC-075859](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L260), [TC-075860](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L261), [TC-075865](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L266), [TC-075876](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L277), [TC-075879](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L280), [TC-075895](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L296), [TC-075898](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L299), [TC-075925](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L326), [TC-075926](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L327), [TC-075927](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L328) and 581 more - every one is in [Defects_in_the_run.csv](Defects_in_the_run.csv)

</details>

### TC and TD references

| Test case | Status | Test name (merchant prefix left out) | Recorded objective | Test data |
|---|---|---|---|---|
| [TC-076166](#tc-076166) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L567)) | `requires_review` | Manual / Keyed Purchase/Capture, then Purchase Reversal with Sequence Number different from the original → declined (to confirm) | Single rule broken: Purchase/Capture -> Purchase Reversal with Sequence Number not matching the original -> declined | [TD-TC-076166.step1.purchase.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076166.step1.purchase.json), [TD-TC-076166.step2.reversal.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076166.step2.reversal.json) |
| [TC-076167](#tc-076167) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L568)) | `requires_review` | Manual / Keyed Purchase/Capture, then Purchase Reversal with Terminal Identifier different from the original → declined (to confirm) | Single rule broken: Purchase/Capture -> Purchase Reversal with Terminal Identifier not matching the original -> declined | [TD-TC-076167.step1.purchase.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076167.step1.purchase.json), [TD-TC-076167.step2.reversal.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076167.step2.reversal.json) |
| [TC-076168](#tc-076168) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L569)) | `requires_review` | Manual / Keyed Purchase/Capture, then Time-out Reversal with Terminal Identifier different from the original → declined (to confirm) | Single rule broken: Purchase/Capture -> Time-out Reversal with Terminal Identifier not matching the original -> declined | [TD-TC-076168.step1.purchase.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076168.step1.purchase.json), [TD-TC-076168.step2.timeout-reversal.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076168.step2.timeout-reversal.json) |
| [TC-076658](#tc-076658) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1059)) | `ready` | Manual / Keyed Authorization Only with Network Token Data Request sent → approved | Decision table: Authorization Only with segment 153 sent (the rule's condition holds) | [TD-TC-076658.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076658.json) |
| [TC-075618](#tc-075618) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L19)) | `ready` | EMV Contact Chip Purchase/Capture with MasterCard Authentication Data → approved | Happy path: Purchase/Capture sending Table 048 MasterCard Authentication Data | [TD-TC-075618.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075618.json) |
| [TC-075654](#tc-075654) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L55)) | `ready` | EMV Contact Chip Purchase/Capture with Tax Type 1 = GST → approved | Decision table: Purchase/Capture with Tax Type 1 = GST (the rule's condition holds) | [TD-TC-075654.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075654.json) |
| [TC-075699](#tc-075699) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L100)) | `needs_client_input` | EMV Contact Chip Purchase/Capture sending avs = true (AVS or NVS or Both Information returned) → approved | Response / oracle check: Purchase/Capture sending avs = true -> AVS or NVS or Both Information returned | [TD-TC-075699.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075699.json) |
| [TC-075657](#tc-075657) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L58)) | `needs_client_input` | EMV Contact Chip Purchase/Capture with avs = true → approved | Decision table: Purchase/Capture with avs = true | [TD-TC-075657.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075657.json) |
| [TC-076586](#tc-076586) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L987)) | `ready` | EMV Contact Chip Authorization Only → approved | Happy path: Authorization Only approved | [TD-TC-076586.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076586.json) |
| [TC-075650](#tc-075650) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L51)) | `requires_review` | EMV Contact Chip Purchase/Capture with Payment Tender Type sent where it is not allowed → declined (to confirm) | Single rule broken: Purchase/Capture with Payment Tender Type sent where the spec forbids it -> declined | [TD-TC-075650.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075650.json) |
| [TC-075755](#tc-075755) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L156)) | `ready` | EMV Contactless Chip Purchase/Capture with Tax Type 1 = GST → approved | Decision table: Purchase/Capture with Tax Type 1 = GST (the rule's condition holds) | [TD-TC-075755.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075755.json) |
| [TC-075656](#tc-075656) ([definition](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L57)) | `ready` | EMV Contact Chip Purchase/Capture with Variable Information Indicator = 001 → approved | Decision table: Purchase/Capture with Variable Information Indicator = 001 (the rule's condition holds) | [TD-TC-075656.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075656.json) |
| + 21 more: [TC-075757](#tc-075757), [TC-075685](#tc-075685), [TC-075670](#tc-075670), [TC-077186](#tc-077186), [TC-077193](#tc-077193), [TC-077219](#tc-077219), [TC-076596](#tc-076596), [TC-076613](#tc-076613), [TC-075751](#tc-075751), [TC-075667](#tc-075667), [TC-077185](#tc-077185), [TC-077206](#tc-077206), [TC-077218](#tc-077218), [TC-076725](#tc-076725), [TC-076723](#tc-076723), [TC-075601](#tc-075601), [TC-075810](#tc-075810), [TC-076597](#tc-076597), [TC-076780](#tc-076780), [TC-077179](#tc-077179), [TC-077203](#tc-077203) | | | | |

### Actual evidence

| Test case | Step | Where | Actual | What it means |
|---|---|---|---|---|
| TC-076166 | - | test case record [test_cases.jsonl line 567](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L567) → `spec_basis[2]` | `spec_basis[2]` = p.402: "The Sequence Number must be the Sequence Number contained in the original transaction." | The quote cited for p.402 is on a different page |
| TC-076167 | - | test case record [test_cases.jsonl line 568](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L568) → `spec_basis[2]` | `spec_basis[2]` = p.40: "Plan v7 W2: a follow-on of the same transaction is sent by the same device as its original (stated for TOR p.40 'A TOR must be sent from the same device as the original financial transaction request'; applied to every follow-on for matching)." | The quote cited for p.40 is not in ATL105 |
| TC-076168 | - | test case record [test_cases.jsonl line 569](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L569) → `spec_basis[2]` | `spec_basis[2]` = p.38: "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR." | The quote cited for p.38 is on a different page |
| TC-076658 | - | test case record [test_cases.jsonl line 1059](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1059) → `spec_basis[0]` | `spec_basis[0]` = p.312: "This data is applicable only to subsequent transactions (Example: Reversal, Completion, Cancellation)." | Cited Segment 153 rule is for Table 006 on subsequent transactions |
| TC-075618 | - | test case record [test_cases.jsonl line 19](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L19) → `spec_basis[0]` | `spec_basis[0]` = `p.586: "Table 048 MasterCard Authentication Data"` | The quote cited for p.586 is not word-for-word in ATL105 |
| TC-075618 | - | test case record [test_cases.jsonl line 19](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L19) → `spec_basis[3]` | `spec_basis[3]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | The quote cited for p.161 is on the neighbouring page |
| TC-075654 | - | test case record [test_cases.jsonl line 55](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L55) → `spec_basis[3]` | `spec_basis[3]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | The quote cited for p.161 is on the neighbouring page |
| TC-075699 | - | test case record [test_cases.jsonl line 100](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L100) → `spec_basis[8]` | `spec_basis[8]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | The quote cited for p.161 is on the neighbouring page |
| TC-075657 | - | test case record [test_cases.jsonl line 58](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L58) → `spec_basis[2]` | `spec_basis[2]` = p.622: "Table Data The Service Location Country Code must be present for all transactions when different from the location identified in the Acceptor Country Code." | The quote cited for p.622 is not word-for-word in ATL105 |
| TC-075657 | - | test case record [test_cases.jsonl line 58](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L58) → `spec_basis[5]` | `spec_basis[5]` = p.632: "0 = False 1 = True Sub Table ID "04" Local Tax Included Indicator Field Attributes Description Sub-Table an2 Fixed Value:" | The quote cited for p.632 is not word-for-word in ATL105 |
| TC-075657 | - | test case record [test_cases.jsonl line 58](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L58) → `spec_basis[8]` | `spec_basis[8]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | The quote cited for p.161 is on the neighbouring page |
| TC-076586 | - | test case record [test_cases.jsonl line 987](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L987) → `spec_basis[7]` | `spec_basis[7]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | The quote cited for p.161 is on the neighbouring page |
| TC-075650 | - | test case record [test_cases.jsonl line 51](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L51) → `spec_basis[3]` | `spec_basis[3]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | The quote cited for p.161 is on the neighbouring page |
| TC-075755 | - | test case record [test_cases.jsonl line 156](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L156) → `spec_basis[3]` | `spec_basis[3]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | The quote cited for p.161 is on the neighbouring page |
| TC-075656 | - | test case record [test_cases.jsonl line 57](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L57) → `spec_basis[0]` | `spec_basis[0]` = p.149: "Transaction Transaction Attributes Sequence o For MasterCard card type, Merchant needs to send MIT/CIT category and subcategory data with valid value as applicable to CIT transactions." | The quote cited for p.149 is not word-for-word in ATL105 |
| TC-075656 | - | test case record [test_cases.jsonl line 57](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L57) → `spec_basis[1]` | `spec_basis[1]` = p.152: "Transaction Transaction Attributes Sequence o For MasterCard card type, Merchant needs to send MIT/CIT category and subcategory data with valid value as applicable to MIT transactions." | The quote cited for p.152 is not word-for-word in ATL105 |
| TC-075656 | - | test case record [test_cases.jsonl line 57](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L57) → `spec_basis[4]` | `spec_basis[4]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | The quote cited for p.161 is on the neighbouring page |
| TC-075757 | - | test case record [test_cases.jsonl line 158](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L158) → `spec_basis[0]` | `spec_basis[0]` = p.149: "Transaction Transaction Attributes Sequence o For MasterCard card type, Merchant needs to send MIT/CIT category and subcategory data with valid value as applicable to CIT transactions." | The quote cited for p.149 is not word-for-word in ATL105 |
| TC-075757 | - | test case record [test_cases.jsonl line 158](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L158) → `spec_basis[1]` | `spec_basis[1]` = p.152: "Transaction Transaction Attributes Sequence o For MasterCard card type, Merchant needs to send MIT/CIT category and subcategory data with valid value as applicable to MIT transactions." | The quote cited for p.152 is not word-for-word in ATL105 |
| TC-075757 | - | test case record [test_cases.jsonl line 158](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L158) → `spec_basis[4]` | `spec_basis[4]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | The quote cited for p.161 is on the neighbouring page |
| TC-075685 | - | test case record [test_cases.jsonl line 86](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L86) → `spec_basis[8]` | `spec_basis[8]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | The quote cited for p.161 is on the neighbouring page |
| TC-075670 | - | test case record [test_cases.jsonl line 71](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L71) → `spec_basis[4]` | `spec_basis[4]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | The quote cited for p.161 is on the neighbouring page |
| TC-077186 | - | test case record [test_cases.jsonl line 1587](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1587) → `spec_basis[2]` | `spec_basis[2]` = `p.None: "EMV Chip Data Length: N max 3"` | The citation of the rule gives no ATL105 page |
| TC-077193 | - | test case record [test_cases.jsonl line 1594](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1594) → `spec_basis[2]` | `spec_basis[2]` = `p.None: "Prompt Code: AN max 4"` | The citation of the rule gives no ATL105 page |
| + 16 more with the same kind of finding: [TC-077219](#tc-077219), [TC-076596](#tc-076596), [TC-076613](#tc-076613), [TC-075751](#tc-075751), [TC-075667](#tc-075667), [TC-077185](#tc-077185), [TC-077206](#tc-077206), [TC-077218](#tc-077218), [TC-076725](#tc-076725), [TC-076723](#tc-076723), [TC-075601](#tc-075601), [TC-075810](#tc-075810), [TC-076597](#tc-076597), [TC-076780](#tc-076780), [TC-077179](#tc-077179), [TC-077203](#tc-077203) | | | | |

### Expected behavior

- `spec_basis[2].page` = `84` instead of `402` - the quoted text itself is right.
- Quote the text exactly as it appears: "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR.".
- Cite the Segment 153 layout (p.310) as the basis.
- Quote the text exactly as it appears on the page.
- Cite p.160 instead of p.161.
- Cite the Chapter 13 page of the element and quote its ATL105 text word for word.
- Change the cited page from 246 to 555. The data values are not affected.
- Add the ATL105 sentence the rule is based on.

### Why it matters

- The test case quotes "The Sequence Number must be the Sequence Number contained in the original transaction." for p.402. Found on a different page, not the cited one (p.84, p.89, p.98).
- The test case quotes "Plan v7 W2: a follow-on of the same transaction is sent by the same device as its original (stated for TOR p.40 'A TOR must be sent from the same device as the original financial transaction request'; applied to every follow-on for matching)." for p.40. Not found in ATL105.
- The test case quotes "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR." for p.38. Found on a different page, not the cited one (p.40, p.45).
- The quote is the description of Network Token PAN Indicator (Table 006), which applies only to subsequent transactions. This test sends an original transaction and does not send Table 006.
- The test case quotes "Table 048 MasterCard Authentication Data" for p.586. Not word-for-word: all its words are on the cited page, but not in this form.
- The test case quotes "The following credit cards are supported for EMV processing: ... MasterCard" for p.161. Found on a neighbouring page, not the cited one (p.160).
- The test case quotes "0 = False 1 = True Sub Table ID "04" Local Tax Included Indicator Field Attributes Description Sub-Table an2 Fixed Value:" for p.632. Not word-for-word: all its words are on the cited page, but not in this form.
- The spec_basis entry quotes "EMV Chip Data Length: N max 3" with page = null, so it cannot be traced to ATL105. The text is a summary of the element's attributes, not an ATL105 sentence.
- The test case cites p.246 for the POS Entry Mode table (Table 005). In ATL105 2026-4, PDF p.246 (printed 12-29) does not contain it; it is on PDF p.555 (printed Appendix I-6).
- The spec_basis entry gives a rule id and a page but no ATL105 text, so the basis cannot be checked word for word.

**BA explanation:** "Some quotes the generator wrote into the test cases cite the wrong page, give no page, or are not ATL105 text. They do not change the request, but a reviewer following the citation will not find the rule."

### Acceptance criteria

- Every spec_basis quote is ATL105 text, word for word, on the cited page and about this transaction.

**Specification reference (ATL105, word for word):**

- "The Sequence Number must be the Sequence Number contained in the original transaction." is on PDF p.84 (printed 10-5), PDF p.89 (printed 10-10), PDF p.98 (printed 10-19)
- Section 2.3.1 Time-out by Customer's Host/Controller/POS System, PDF p.40 (printed 2-5): "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR." ([ATL105 text line 2181](../specifications/ATL105/docs/specs/extracted_text_new.txt#L2181))
- "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR." is on PDF p.40 (printed 2-5), PDF p.45 (printed 2-10)
- Section 12.38 Network Token Data Request Segment, PDF p.312 (printed 12-95): "This data is applicable only to subsequent transactions (Example: Reversal, Completion, Cancellation)." ([ATL105 text line 18141](../specifications/ATL105/docs/specs/extracted_text_new.txt#L18141))
- Section 12.38 Network Token Data Request Segment, PDF p.310 (printed 12-93): "All or some of the Network Token sub-segment data is specified below. Data is in <tag><len><data> format." ([ATL105 text line 18057](../specifications/ATL105/docs/specs/extracted_text_new.txt#L18057))
- Appendix I. Variable Information Data Layouts - Point-of-Service Entry Mode Information (Table 005), PDF p.555 (printed Appendix I-6): "Point-of-Service Entry Mode Information" ([ATL105 text line 29811](../specifications/ATL105/docs/specs/extracted_text_new.txt#L29811))
- Element 86 Sequence Number (Chapter 13), PDF p.409 (printed 13-69): "In the case of a preauthorized transaction, the Sequence Number in the Purchase/Capture is the same Sequence Number as in the transaction’s Authorization Only request." ([ATL105 text line 23453](../specifications/ATL105/docs/specs/extracted_text_new.txt#L23453))

<a id="not-defects"></a>

## Not defects (shown so they are not raised again)

One row per kind of finding with its first example; each test case section below lists its own. Placeholders, run-time and environment values are listed under [Filtered out](#filtered-out).

| Kind | Finding (first example) | Test cases | Actual | Where | Why it is correct |
|---|---|---|---|---|---|
| Information | Join Account Number and expiry with '=' in the wire message | [TC-076166](#tc-076166), [TC-076167](#tc-076167), [TC-076168](#tc-076168), [TC-075670](#tc-075670), [TC-076596](#tc-076596), [TC-076613](#tc-076613), [TC-075667](#tc-075667), [TC-076725](#tc-076725), [TC-076597](#tc-076597) | `5187270512804249 / 0928` | [TD-TC-076166.step2.reversal.json line 14](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076166.step2.reversal.json#L14), [TD-TC-076166.step2.reversal.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076166.step2.reversal.json#L15) → `AccountNumber, CardDiscretionaryBlockData` | The message builder sends 5187270512804249=0928. No change in the test data. |
| Intended violation of a negative test (correct) | Step 2: same sequence number as the original - check failed | [TC-076166](#tc-076166) | `000002` | [TD-TC-076166.step2.reversal.json line 22](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076166.step2.reversal.json#L22) → `Standard Message Data Segment → SequenceNumber` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `761661`). |
| Intended violation of a negative test (correct) | Step 2: same terminal as the original - check failed | [TC-076167](#tc-076167), [TC-076168](#tc-076168) | `VS24671841985066391107` | [TD-TC-076167.step2.reversal.json line 9](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076167.step2.reversal.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `JX41894821001`). |
| Intended violation of a negative test (correct) | EMV Chip Data Length (Element 189) does not match the chip data | [TC-077186](#tc-077186) | `` | [TD-TC-077186.json line 53](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077186.json#L53) → `EMV Request Data Segment → EMVChipDataLength` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `147`). |
| Intended violation of a negative test (correct) | Prompt Code is empty | [TC-077193](#tc-077193) | `(empty)` | [TD-TC-077193.json line 10](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077193.json#L10) → `Standard Message Data Segment → PromptCode` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `Populate Prompt Code.`). |
| Intended violation of a negative test (correct) | Segment 111 length is wrong | [TC-077219](#tc-077219) | `1019` | [TD-TC-077219.json line 29](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077219.json#L29) → `Variable Information Data Segment → SegmentLength` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `1019`). |
| Intended violation of a negative test (correct) | Table 005 value is not valid | [TC-077219](#tc-077219) | `AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA` | [TD-TC-077219.json line 27](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077219.json#L27) → `Variable Information Data Segment → VariableInformationSection[0]` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA`). |
| AI validator rule not applicable - value checked and correct | Card Discretionary Block Data is not valid | [TC-076725](#tc-076725) | `0427` | [TD-TC-076725.step2.completion.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076725.step2.completion.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | This step completes the Authorization Only of step 1 (preauthorized Purchase/Capture). Credit completions send Account Number = YYMM (Section 10.1.8.2), which is what the file has; the AI rule treated the step as a new purchase. |
| Intended violation of a negative test (correct) | Fleet Data Segment: Customer Data breaks its Chapter 13 format | [TC-077203](#tc-077203) | `AAAAAAAAAAA#` | [TD-TC-077203.json line 38](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077203.json#L38) → `Fleet Data Segment → CustomerData` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `A value of type AN with at most 12 characters.`). |

<a id="test-data"></a>

## Test data - actual and expected, per test case

For each test case: its findings for the defects of this sheet (actual value, where it is, what it should be), the test data as delivered (click to expand) and the expected test data - the same file with the changes for these defects; each changed line ends with a `//` comment naming the defect id. Placeholders, run-time and environment values (for example the Terminal Identifier, `<ApprovalNumber from step 1 response>`, `<AWAITING:...>` values and the generated EMV data) are left as delivered: they are filled in at execution. A step whose test data needs no change says so instead of repeating the file. Not shown, because all their findings are filtered out: [TC-076120](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L521).

<a id="tc-076166"></a>

### TC-076166 (BR1_TS5_TC47) - Single rule broken: Purchase/Capture -> Purchase Reversal with Sequence Number not matching the original -> declined

**Test name:** Manual / Keyed Purchase/Capture, then Purchase Reversal with Sequence Number different from the original → declined (to confirm). **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`; step 2 sends `8020` (Purchase reversal), expected Response Code `1`.

**Status:** `requires_review` · **AI result (this sheet):** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 567](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L567) · **Every field against ATL105:** [Result_MD_Folder/TC-076166.md](Result_MD_Folder/TC-076166.md) · **Step-by-step guide:** [TC-076166-Manual-Validation.md](Manual%20Validation/TC-076166-Manual-Validation.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-16 | Card Discretionary Block Data is not valid | Test data defect | `0928` | [TD-TC-076166.step2.reversal.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076166.step2.reversal.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `CardDiscretionaryBlockData` = `2809` - the expiry as YYMM (the original's MMYY `0928` with month and year swapped), as every follow-on transaction sends it. |
| Q-02 | The quote cited for p.402 is on a different page | Citation issue | `spec_basis[2]` = p.402: "The Sequence Number must be the Sequence Number contained in the original transaction." | test case record [test_cases.jsonl line 567](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L567) → `spec_basis[2]` | `spec_basis[2].page` = `84` instead of `402` - the quoted text itself is right. |
| N-01 | Step 2: same sequence number as the original - check failed | Intended violation of a negative test (correct) | `000002` | [TD-TC-076166.step2.reversal.json line 22](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076166.step2.reversal.json#L22) → `Standard Message Data Segment → SequenceNumber` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `761661`). |
| INFO | Join Account Number and expiry with '=' in the wire message | Information | `5187270512804249 / 0928` | [TD-TC-076166.step2.reversal.json line 14](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076166.step2.reversal.json#L14), [TD-TC-076166.step2.reversal.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076166.step2.reversal.json#L15) → `AccountNumber, CardDiscretionaryBlockData` | The message builder sends 5187270512804249=0928. No change in the test data. |

<details><summary>Test data - actual, step 1: TD-TC-076166.step1.purchase.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5187270512804249",
      "CardDiscretionaryBlockData": "0928",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "761661",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

Test data - expected, step 1: `TD-TC-076166.step1.purchase.json` - no change for the defects of this sheet (the same as the test data as delivered).

<details><summary>Test data - actual, step 2: TD-TC-076166.step2.reversal.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "078",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "8",
        "CardType": "020"
      },
      "AccountNumber": "5187270512804249",
      "CardDiscretionaryBlockData": "0928",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "000002",
      "ApprovalNumber": "<ApprovalNumber from step 1 response>",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 2: TD-TC-076166.step2.reversal.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "078",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "8",
        "CardType": "020"
      },
      "AccountNumber": "5187270512804249",
      "CardDiscretionaryBlockData": "2809",  // INFO: the wire message sends AccountNumber '=' this value: 5187270512804249=0928; D-16: expiry as YYMM - the original's MMYY 0928 with month and year swapped (follow-on transaction, Section 10.1.8.2)
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "000002",  // N-01: different from step 1 ('761661') on purpose - the rule this negative test breaks; keep
      "ApprovalNumber": "<ApprovalNumber from step 1 response>",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

<a id="tc-076167"></a>

### TC-076167 (BR1_TS5_TC48) - Single rule broken: Purchase/Capture -> Purchase Reversal with Terminal Identifier not matching the original -> declined

**Test name:** Manual / Keyed Purchase/Capture, then Purchase Reversal with Terminal Identifier different from the original → declined (to confirm). **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`; step 2 sends `8020` (Purchase reversal), expected Response Code `1`.

**Status:** `requires_review` · **AI result (this sheet):** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 568](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L568) · **Every field against ATL105:** [Result_MD_Folder/TC-076167.md](Result_MD_Folder/TC-076167.md) · **Step-by-step guide:** [TC-076167-Manual-Validation.md](Manual%20Validation/TC-076167-Manual-Validation.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-16 | Card Discretionary Block Data is not valid | Test data defect | `0430` | [TD-TC-076167.step2.reversal.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076167.step2.reversal.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `CardDiscretionaryBlockData` = `3004` - the expiry as YYMM (the original's MMYY `0430` with month and year swapped), as every follow-on transaction sends it. |
| Q-01 | The quote cited for p.40 is not in ATL105 | Citation issue | `spec_basis[2]` = p.40: "Plan v7 W2: a follow-on of the same transaction is sent by the same device as its original (stated for TOR p.40 'A TOR must be sent from the same device as the original financial transaction request'; applied to every follow-on for matching)." | test case record [test_cases.jsonl line 568](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L568) → `spec_basis[2]` | Quote the text exactly as it appears: "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR.". |
| N-01 | Step 2: same terminal as the original - check failed | Intended violation of a negative test (correct) | `VS24671841985066391107` | [TD-TC-076167.step2.reversal.json line 9](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076167.step2.reversal.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `JX41894821001`). |
| INFO | Join Account Number and expiry with '=' in the wire message | Information | `5189638922468029 / 0430` | [TD-TC-076167.step2.reversal.json line 14](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076167.step2.reversal.json#L14), [TD-TC-076167.step2.reversal.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076167.step2.reversal.json#L15) → `AccountNumber, CardDiscretionaryBlockData` | The message builder sends 5189638922468029=0430. No change in the test data. |

<details><summary>Test data - actual, step 1: TD-TC-076167.step1.purchase.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5189638922468029",
      "CardDiscretionaryBlockData": "0430",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "761671",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

Test data - expected, step 1: `TD-TC-076167.step1.purchase.json` - no change for the defects of this sheet (the same as the test data as delivered).

<details><summary>Test data - actual, step 2: TD-TC-076167.step2.reversal.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "087",
      "InformationByte": "0",
      "TerminalIdentifier": "VS24671841985066391107",
      "PromptCode": {
        "TransactionType": "8",
        "CardType": "020"
      },
      "AccountNumber": "5189638922468029",
      "CardDiscretionaryBlockData": "0430",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "761671",
      "ApprovalNumber": "<ApprovalNumber from step 1 response>",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 2: TD-TC-076167.step2.reversal.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "087",
      "InformationByte": "0",
      "TerminalIdentifier": "VS24671841985066391107",  // N-01: different from step 1 ('JX41894821001') on purpose - the rule this negative test breaks; keep
      "PromptCode": {
        "TransactionType": "8",
        "CardType": "020"
      },
      "AccountNumber": "5189638922468029",
      "CardDiscretionaryBlockData": "3004",  // INFO: the wire message sends AccountNumber '=' this value: 5189638922468029=0430; D-16: expiry as YYMM - the original's MMYY 0430 with month and year swapped (follow-on transaction, Section 10.1.8.2)
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "761671",
      "ApprovalNumber": "<ApprovalNumber from step 1 response>",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

<a id="tc-076168"></a>

### TC-076168 (BR1_TS5_TC49) - Single rule broken: Purchase/Capture -> Time-out Reversal with Terminal Identifier not matching the original -> declined

**Test name:** Manual / Keyed Purchase/Capture, then Time-out Reversal with Terminal Identifier different from the original → declined (to confirm). **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`; step 2 sends `Z020` (Time-out Reversal), expected Response Code `1`.

**Status:** `requires_review` · **AI result (this sheet):** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 569](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L569) · **Every field against ATL105:** [Result_MD_Folder/TC-076168.md](Result_MD_Folder/TC-076168.md) · **Step-by-step guide:** [TC-076168-Manual-Validation.md](Manual%20Validation/TC-076168-Manual-Validation.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-16 | Card Discretionary Block Data is not valid | Test data defect | `0929` | [TD-TC-076168.step2.timeout-reversal.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076168.step2.timeout-reversal.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `CardDiscretionaryBlockData` = `2909` - the expiry as YYMM (the original's MMYY `0929` with month and year swapped), as every follow-on transaction sends it. |
| Q-02 | The quote cited for p.38 is on a different page | Citation issue | `spec_basis[2]` = p.38: "A TOR must be sent from the same device as the original financial transaction request that resulted in the TOR." | test case record [test_cases.jsonl line 569](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L569) → `spec_basis[2]` | `spec_basis[2].page` = `40` instead of `38` - the quoted text itself is right. |
| C-06 | Step 2 sends a Time-out Reversal although step 1 is expected to be answered | To confirm with BUYPASS / SME | `tx_legs[0].expect` = {"tier": "ASSERT", "page": 403, "response_code": "0", "note": "Approved-Purchase/Capture"} and `harness` = `null` (no fault injection) | test case record [test_cases.jsonl line 569](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L569) → `tx_legs[0].expect` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). Step 1 then expects no response (instead of Response Code 0), and the device sends the Time-out Reversal after the 30-second response interval. |
| N-01 | Step 2: same terminal as the original - check failed | Intended violation of a negative test (correct) | `QL66203054504417704598` | [TD-TC-076168.step2.timeout-reversal.json line 9](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076168.step2.timeout-reversal.json#L9) → `Standard Message Data Segment → TerminalIdentifier` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `JX41894821001`). |
| INFO | Join Account Number and expiry with '=' in the wire message | Information | `5144277772726279 / 0929` | [TD-TC-076168.step2.timeout-reversal.json line 14](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076168.step2.timeout-reversal.json#L14), [TD-TC-076168.step2.timeout-reversal.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076168.step2.timeout-reversal.json#L15) → `AccountNumber, CardDiscretionaryBlockData` | The message builder sends 5144277772726279=0929. No change in the test data. |

<details><summary>Test data - actual, step 1: TD-TC-076168.step1.purchase.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5144277772726279",
      "CardDiscretionaryBlockData": "0929",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "761681",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

Test data - expected, step 1: `TD-TC-076168.step1.purchase.json` - no change for the defects of this sheet (the same as the test data as delivered).

<details><summary>Test data - actual, step 2: TD-TC-076168.step2.timeout-reversal.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "081",
      "InformationByte": "0",
      "TerminalIdentifier": "QL66203054504417704598",
      "PromptCode": {
        "TransactionType": "Z",
        "CardType": "020"
      },
      "AccountNumber": "5144277772726279",
      "CardDiscretionaryBlockData": "0929",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "761681",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 2: TD-TC-076168.step2.timeout-reversal.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "081",
      "InformationByte": "0",
      "TerminalIdentifier": "QL66203054504417704598",  // N-01: different from step 1 ('JX41894821001') on purpose - the rule this negative test breaks; keep
      "PromptCode": {
        "TransactionType": "Z",
        "CardType": "020"
      },
      "AccountNumber": "5144277772726279",
      "CardDiscretionaryBlockData": "2909",  // INFO: the wire message sends AccountNumber '=' this value: 5144277772726279=0929; D-16: expiry as YYMM - the original's MMYY 0929 with month and year swapped (follow-on transaction, Section 10.1.8.2)
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "761681",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

<a id="tc-076658"></a>

### TC-076658 (BR1_TS15_TC7) - Decision table: Authorization Only with segment 153 sent (the rule's condition holds)

**Test name:** Manual / Keyed Authorization Only with Network Token Data Request sent → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result (this sheet):** REVIEW - correct the test case or confirm with BUYPASS · **Test case record:** [test_cases.jsonl line 1059](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1059) · **Every field against ATL105:** [Result_MD_Folder/TC-076658.md](Result_MD_Folder/TC-076658.md) · **Step-by-step guide:** [TC-076658-Manual-Validation.md](Manual%20Validation/TC-076658-Manual-Validation.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| Q-04 | Cited Segment 153 rule is for Table 006 on subsequent transactions | Citation issue | `spec_basis[0]` = p.312: "This data is applicable only to subsequent transactions (Example: Reversal, Completion, Cancellation)." | test case record [test_cases.jsonl line 1059](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1059) → `spec_basis[0]` | Cite the Segment 153 layout (p.310) as the basis. |
| S-03 | Network Token Data Request: length depends on an unclear Field Separator rule | To confirm with BUYPASS / SME | `051` | [TD-TC-076658.json line 45](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076658.json#L45) → `Network Token Data Request → SegmentLengthIndicator` | Confirm the Field Separator rule of this segment with BUYPASS. |
| S-04 | Network Token Input Indicator 'Y' while Element 2 is a card number | To confirm with BUYPASS / SME | `Y` | [TD-TC-076658.json line 46](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076658.json#L46) → `Network Token Data Request → NetworkTokenData (table 004)` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |

<details><summary>Test data - actual, step 1: TD-TC-076658.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5140312601563116",
      "CardDiscretionaryBlockData": "0932",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "766581",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    },
    "Network Token Data Request": {
      "SegmentType": "153",
      "SegmentLengthIndicator": "051",
      "NetworkTokenData": "00101967928410653764478340020042701004001Y"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-076658.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5140312601563116",
      "CardDiscretionaryBlockData": "0932",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "766581",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    },
    "Network Token Data Request": {
      "SegmentType": "153",
      "SegmentLengthIndicator": "051",  // S-03: 051 = a Field Separator after every field; other readings of ATL105 give 049, 050 - confirm with BUYPASS
      "NetworkTokenData": "00101967928410653764478340020042701004001N"  // S-04: table 004 'N' (Clear PAN) if Element 2 keeps the card number - confirm with BUYPASS
    }
  }
}
```

</details>

<a id="tc-075618"></a>

### TC-075618 (BR1_TS1_TC28) - Happy path: Purchase/Capture sending Table 048 MasterCard Authentication Data

**Test name:** EMV Contact Chip Purchase/Capture with MasterCard Authentication Data → approved. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`.

**Status:** `ready` · **AI result (this sheet):** REVIEW - correct the test case or confirm with BUYPASS · **Test case record:** [test_cases.jsonl line 19](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L19) · **Every field against ATL105:** [Result_MD_Folder/TC-075618.md](Result_MD_Folder/TC-075618.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| Q-03 | The quote cited for p.586 is not word-for-word in ATL105 | Citation issue | `spec_basis[0]` = `p.586: "Table 048 MasterCard Authentication Data"` | test case record [test_cases.jsonl line 19](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L19) → `spec_basis[0]` | Quote the text exactly as it appears on the page. |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[3]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 19](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L19) → `spec_basis[3]` | Cite p.160 instead of p.161. |
| C-12 | Table 048 (3-D Secure authentication data) sent on a POS transaction | To confirm with BUYPASS / SME | `1` | [TD-TC-075618.json line 27](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075618.json#L27) → `Variable Information Data Segment → Table 048` | Confirm with BUYPASS; otherwise send Table 048 only in e-commerce (3-D Secure) tests, or re-scope the test. |

<details><summary>Test data - actual, step 1: TD-TC-075618.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5158644613167836",
      "CardDiscretionaryBlockData": "311020127370826",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "756181",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "049",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        },
        {
          "VariableInformationIndicator": "048",
          "VariableInformationLength": "001",
          "VariableInformation": "1"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601189C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F260850652082A41781339F2701809F3303E0F0C89F34035E03029F3501229F3602187B9F370432556ADB9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-075618.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5158644613167836",
      "CardDiscretionaryBlockData": "311020127370826",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "756181",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "049",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        },
        {
          "VariableInformationIndicator": "048",  // C-12: Table 048 is for e-commerce 3-D Secure transactions - confirm with BUYPASS that it is accepted on this POS transaction, or re-scope / withdraw the test
          "VariableInformationLength": "001",
          "VariableInformation": "1"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601189C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F260850652082A41781339F2701809F3303E0F0C89F34035E03029F3501229F3602187B9F370432556ADB9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-075654"></a>

### TC-075654 (BR1_TS1_TC64) - Decision table: Purchase/Capture with Tax Type 1 = GST (the rule's condition holds)

**Test name:** EMV Contact Chip Purchase/Capture with Tax Type 1 = GST → approved. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`.

**Status:** `ready` · **AI result (this sheet):** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 55](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L55) · **Every field against ATL105:** [Result_MD_Folder/TC-075654.md](Result_MD_Folder/TC-075654.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-10 | The test claims Tax Type 1 = GST but the request does not send it | Test case defect | `Tax Type 1: not in the test data; segments sent: 100, 111, 130` | [TD-TC-075654.json line 2](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075654.json#L2) → `EMV Financial Transaction Request` | Send Segment 102 (Product Code) and Segment 143 (Tax by Product) with the same product(s) in the same order, the inclusive/exclusive flag (I or E), Tax Type 1 = GST and the tax amount; recalculate the segment count and lengths. GST is a Canadian tax type and Segment 143 is sent only if the account manager instructs it (Section 11.1), so confirm the merchant context - or re-scope / withdraw the test. |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[3]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 55](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L55) → `spec_basis[3]` | Cite p.160 instead of p.161. |

<details><summary>Test data - actual, step 1: TD-TC-075654.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5117809477999349",
      "CardDiscretionaryBlockData": "300620158401613",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "756541",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601289C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F260846D9B2E7B76B4AA69F2701809F3303E0F0C89F34035E03029F3501229F3602923E9F3704017A6C139F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-075654.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "05",  // C-10: + Segments 102 and 143
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5117809477999349",
      "CardDiscretionaryBlockData": "300620158401613",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "756541",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601289C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F260846D9B2E7B76B4AA69F2701809F3303E0F0C89F34035E03029F3501229F3602923E9F3704017A6C139F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    },
    "Product Code Data Segment": {  // C-10: Segment 143 needs Segment 102
      "SegmentType": "102",
      "SegmentLength": "029",  // whole segment: type, length, 3 Field Separators and the data with its 2 '\' separators
      "ServiceLevel": "O",  // O = Other/Fuel not present (Element 87)
      "NumberofProducts": "01",
      "ProductCode": "400",  // 400 = General merchandise (Appendix F) - client to confirm
      "UnitofMeasure": "U",  // U = Unit (Element 106)
      "Quantity": "01",  // 1 (leading digit = decimal places)
      "UnitPrice": "21500",  // leading 2 = two decimal places
      "ProductAmount": "1500"  // = Nonfuel Amount
    },
    "Tax by Product Data Segment": {  // C-10: Tax Type 1 = GST, as the test claims
      "SegmentType": "143",
      "SegmentLength": "020",  // counted like Segment 102 - the one-product Segment 143 examples show 023 for 21 characters; confirm with BUYPASS
      "NumberofProducts": "01",  // same products, same order as Segment 102
      "ProductCode": "400",
      "InclusiveExclusiveforTax1": "I",  // I = tax included in the amount
      "TaxType1": "GST",  // the claimed value
      "TaxAmount1": "71"  // example: 5% GST included in the amount - client to supply
    }
  }
}
```

</details>

<a id="tc-075699"></a>

### TC-075699 (BR1_TS1_TC109) - Response / oracle check: Purchase/Capture sending avs = true -> AVS or NVS or Both Information returned

**Test name:** EMV Contact Chip Purchase/Capture sending avs = true (AVS or NVS or Both Information returned) → approved. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `4`.

**Status:** `needs_client_input` · **AI result (this sheet):** REVIEW - correct the test case or confirm with BUYPASS · **Test case record:** [test_cases.jsonl line 100](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L100) · **Every field against ATL105:** [Result_MD_Folder/TC-075699.md](Result_MD_Folder/TC-075699.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[8]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 100](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L100) → `spec_basis[8]` | Cite p.160 instead of p.161. |

<details><summary>Test data - actual, step 1: TD-TC-075699.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5158644613167836",
      "CardDiscretionaryBlockData": "311020127370826",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "756991",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "045",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        },
        {
          "VariableInformationIndicator": "003",
          "VariableInformationLength": "<AWAITING:VariableInformationLength[4]>",
          "VariableInformation": "<AWAITING:VariableInformation[4]>"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601059C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F260806A2AE10B04417ED9F2701809F3303E0F0C89F34035E03029F3501229F36021F7D9F3704372BF34F9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

Test data - expected, step 1: `TD-TC-075699.json` - no change for the defects of this sheet (the same as the test data as delivered).

<a id="tc-075657"></a>

### TC-075657 (BR1_TS1_TC67) - Decision table: Purchase/Capture with avs = true

**Test name:** EMV Contact Chip Purchase/Capture with avs = true → approved. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `4`.

**Status:** `needs_client_input` · **AI result (this sheet):** REVIEW - correct the test case or confirm with BUYPASS · **Test case record:** [test_cases.jsonl line 58](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L58) · **Every field against ATL105:** [Result_MD_Folder/TC-075657.md](Result_MD_Folder/TC-075657.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| Q-03 | The quote cited for p.622 is not word-for-word in ATL105 | Citation issue | `spec_basis[2]` = p.622: "Table Data The Service Location Country Code must be present for all transactions when different from the location identified in the Acceptor Country Code." | test case record [test_cases.jsonl line 58](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L58) → `spec_basis[2]` | Quote the text exactly as it appears on the page. |
| Q-03 | The quote cited for p.632 is not word-for-word in ATL105 | Citation issue | `spec_basis[5]` = p.632: "0 = False 1 = True Sub Table ID "04" Local Tax Included Indicator Field Attributes Description Sub-Table an2 Fixed Value:" | test case record [test_cases.jsonl line 58](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L58) → `spec_basis[5]` | Quote the text exactly as it appears on the page. |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[8]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 58](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L58) → `spec_basis[8]` | Cite p.160 instead of p.161. |

<details><summary>Test data - actual, step 1: TD-TC-075657.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5158644613167836",
      "CardDiscretionaryBlockData": "311020127370826",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "756571",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "045",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        },
        {
          "VariableInformationIndicator": "003",
          "VariableInformationLength": "<AWAITING:VariableInformationLength[4]>",
          "VariableInformation": "<AWAITING:VariableInformation[4]>"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601149C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F26082A2AE6FF4C1DBDD79F2701809F3303E0F0C89F34035E03029F3501229F360292C59F3704F2A7A7209F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

Test data - expected, step 1: `TD-TC-075657.json` - no change for the defects of this sheet (the same as the test data as delivered).

<a id="tc-076586"></a>

### TC-076586 (BR1_TS11_TC5) - Happy path: Authorization Only approved

**Test name:** EMV Contact Chip Authorization Only → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result (this sheet):** REVIEW - correct the test case or confirm with BUYPASS · **Test case record:** [test_cases.jsonl line 987](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L987) · **Every field against ATL105:** [Result_MD_Folder/TC-076586.md](Result_MD_Folder/TC-076586.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[7]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 987](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L987) → `spec_basis[7]` | Cite p.160 instead of p.161. |

<details><summary>Test data - actual, step 1: TD-TC-076586.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5177951757699269",
      "CardDiscretionaryBlockData": "290520101440570",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "765861",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601199C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F26086065F4A5762643F99F2701809F3303E0F0C89F34035E03029F3501229F3602F2BB9F370479B09E009F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

Test data - expected, step 1: `TD-TC-076586.json` - no change for the defects of this sheet (the same as the test data as delivered).

<a id="tc-075650"></a>

### TC-075650 (BR1_TS1_TC60) - Single rule broken: Purchase/Capture with Payment Tender Type sent where the spec forbids it -> declined

**Test name:** EMV Contact Chip Purchase/Capture with Payment Tender Type sent where it is not allowed → declined (to confirm). **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `1`.

**Status:** `requires_review` · **AI result (this sheet):** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 51](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L51) · **Every field against ATL105:** [Result_MD_Folder/TC-075650.md](Result_MD_Folder/TC-075650.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-02 | The rule this negative test claims to break is not in ATL105 | Test case defect | `test_objective` = `Single rule broken: Purchase/Capture with Payment Tender Type sent where the spec forbids it -> declined`; expected Response Code `1` | test case record [test_cases.jsonl line 51](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L51) → `test_objective` | Remove or rewrite the test around a rule ATL105 states. |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[3]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 51](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L51) → `spec_basis[3]` | Cite p.160 instead of p.161. |
| S-03 | Loyalty Card Data Segment: length depends on an unclear Field Separator rule | To confirm with BUYPASS / SME | `120` | [TD-TC-075650.json line 29](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075650.json#L29) → `Loyalty Card Data Segment → SegmentLength` | Confirm the Field Separator rule of this segment with BUYPASS. |

<details><summary>Test data - actual, step 1: TD-TC-075650.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "04",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5158764571026343",
      "CardDiscretionaryBlockData": "310120104706946",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "756501",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Loyalty Card Data Segment": {
      "SegmentType": "108",
      "SegmentLength": "120",
      "LoyaltyProgramID": "664170",
      "LoyaltyAccountNumber": "",
      "PointstoRedeem": "575656",
      "CouponID": "7646861318558938691",
      "CouponAmount": "53195931",
      "UpdateCode": "",
      "StreetAddress": "",
      "PhoneNumberLoyalty": "",
      "ExpirationDate": "",
      "PaymentTenderType": "AX",
      "LoyaltyTrack2Data": "51185417662739901029632254332875496578",
      "LoyaltyInformationVersion": "1",
      "UnitofWork": "1338080973545022018"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601099C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608CAA3F3A29FF407469F2701809F3303E0F0C89F34035E03029F3501229F3602E78F9F3704FA07264C9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-075650.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {  // C-02: no change to the request - what must change is the test (its premise and the expected decline); see the finding
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "04",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5158764571026343",
      "CardDiscretionaryBlockData": "310120104706946",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "756501",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Loyalty Card Data Segment": {
      "SegmentType": "108",
      "SegmentLength": "120",  // S-03: 120 = a Field Separator after every field; other readings of ATL105 give 118 - confirm with BUYPASS
      "LoyaltyProgramID": "664170",
      "LoyaltyAccountNumber": "",
      "PointstoRedeem": "575656",
      "CouponID": "7646861318558938691",
      "CouponAmount": "53195931",
      "UpdateCode": "",
      "StreetAddress": "",
      "PhoneNumberLoyalty": "",
      "ExpirationDate": "",
      "PaymentTenderType": "AX",
      "LoyaltyTrack2Data": "51185417662739901029632254332875496578",
      "LoyaltyInformationVersion": "1",
      "UnitofWork": "1338080973545022018"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601099C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608CAA3F3A29FF407469F2701809F3303E0F0C89F34035E03029F3501229F3602E78F9F3704FA07264C9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-075755"></a>

### TC-075755 (BR1_TS2_TC57) - Decision table: Purchase/Capture with Tax Type 1 = GST (the rule's condition holds)

**Test name:** EMV Contactless Chip Purchase/Capture with Tax Type 1 = GST → approved. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`.

**Status:** `ready` · **AI result (this sheet):** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 156](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L156) · **Every field against ATL105:** [Result_MD_Folder/TC-075755.md](Result_MD_Folder/TC-075755.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-10 | The test claims Tax Type 1 = GST but the request does not send it | Test case defect | `Tax Type 1: not in the test data; segments sent: 100, 111, 130` | [TD-TC-075755.json line 2](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075755.json#L2) → `EMV Financial Transaction Request` | Send Segment 102 (Product Code) and Segment 143 (Tax by Product) with the same product(s) in the same order, the inclusive/exclusive flag (I or E), Tax Type 1 = GST and the tax amount; recalculate the segment count and lengths. GST is a Canadian tax type and Segment 143 is sent only if the account manager instructs it (Section 11.1), so confirm the merchant context - or re-scope / withdraw the test. |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[3]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 156](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L156) → `spec_basis[3]` | Cite p.160 instead of p.161. |

<details><summary>Test data - actual, step 1: TD-TC-075755.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5158078586863529",
      "CardDiscretionaryBlockData": "300920163399183",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "757551",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "071"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601149C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F260888FBA888C431C4AA9F2701809F3303E0F0C89F34035E03029F3501229F36022D5A9F3704F1FD4F299F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-075755.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "05",  // C-10: + Segments 102 and 143
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5158078586863529",
      "CardDiscretionaryBlockData": "300920163399183",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "757551",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "071"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601149C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F260888FBA888C431C4AA9F2701809F3303E0F0C89F34035E03029F3501229F36022D5A9F3704F1FD4F299F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    },
    "Product Code Data Segment": {  // C-10: Segment 143 needs Segment 102
      "SegmentType": "102",
      "SegmentLength": "029",  // whole segment: type, length, 3 Field Separators and the data with its 2 '\' separators
      "ServiceLevel": "O",  // O = Other/Fuel not present (Element 87)
      "NumberofProducts": "01",
      "ProductCode": "400",  // 400 = General merchandise (Appendix F) - client to confirm
      "UnitofMeasure": "U",  // U = Unit (Element 106)
      "Quantity": "01",  // 1 (leading digit = decimal places)
      "UnitPrice": "21500",  // leading 2 = two decimal places
      "ProductAmount": "1500"  // = Nonfuel Amount
    },
    "Tax by Product Data Segment": {  // C-10: Tax Type 1 = GST, as the test claims
      "SegmentType": "143",
      "SegmentLength": "020",  // counted like Segment 102 - the one-product Segment 143 examples show 023 for 21 characters; confirm with BUYPASS
      "NumberofProducts": "01",  // same products, same order as Segment 102
      "ProductCode": "400",
      "InclusiveExclusiveforTax1": "I",  // I = tax included in the amount
      "TaxType1": "GST",  // the claimed value
      "TaxAmount1": "71"  // example: 5% GST included in the amount - client to supply
    }
  }
}
```

</details>

<a id="tc-075656"></a>

### TC-075656 (BR1_TS1_TC66) - Decision table: Purchase/Capture with Variable Information Indicator = 001 (the rule's condition holds)

**Test name:** EMV Contact Chip Purchase/Capture with Variable Information Indicator = 001 → approved. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`.

**Status:** `ready` · **AI result (this sheet):** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 57](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L57) · **Every field against ATL105:** [Result_MD_Folder/TC-075656.md](Result_MD_Folder/TC-075656.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-10 | The test claims Variable Information Indicator = 001 but the request does not send it | Test case defect | `Variable Information Indicator: not in the test data; segments sent: 100, 111, 130` | [TD-TC-075656.json line 2](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075656.json#L2) → `EMV Financial Transaction Request` | Send Variable Information Indicator = 001 in Variable Information Data Segment, or re-scope / withdraw the test. |
| Q-03 | The quote cited for p.149 is not word-for-word in ATL105 | Citation issue | `spec_basis[0]` = p.149: "Transaction Transaction Attributes Sequence o For MasterCard card type, Merchant needs to send MIT/CIT category and subcategory data with valid value as applicable to CIT transactions." | test case record [test_cases.jsonl line 57](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L57) → `spec_basis[0]` | Quote the text exactly as it appears on the page. |
| Q-03 | The quote cited for p.152 is not word-for-word in ATL105 | Citation issue | `spec_basis[1]` = p.152: "Transaction Transaction Attributes Sequence o For MasterCard card type, Merchant needs to send MIT/CIT category and subcategory data with valid value as applicable to MIT transactions." | test case record [test_cases.jsonl line 57](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L57) → `spec_basis[1]` | Quote the text exactly as it appears on the page. |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[4]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 57](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L57) → `spec_basis[4]` | Cite p.160 instead of p.161. |

<details><summary>Test data - actual, step 1: TD-TC-075656.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5158644613167836",
      "CardDiscretionaryBlockData": "311020127370826",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "756561",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601189C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608598DB044D898CA359F2701809F3303E0F0C89F34035E03029F3501229F360270999F3704B325DC109F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-075656.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5158644613167836",
      "CardDiscretionaryBlockData": "311020127370826",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "756561",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",  // C-10: recalculate: 042 + 6 + length of the Table 001 data
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        },
        {  // C-10: Table 001 - the table this test claims to send
          "VariableInformationIndicator": "001",
          "VariableInformationLength": "<length of the data>",
          "VariableInformation": "<Table 001 data - Appendix I layout>"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601189C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608598DB044D898CA359F2701809F3303E0F0C89F34035E03029F3501229F360270999F3704B325DC109F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-075757"></a>

### TC-075757 (BR1_TS2_TC59) - Decision table: Purchase/Capture with Variable Information Indicator = 001 (the rule's condition holds)

**Test name:** EMV Contactless Chip Purchase/Capture with Variable Information Indicator = 001 → approved. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`.

**Status:** `ready` · **AI result (this sheet):** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 158](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L158) · **Every field against ATL105:** [Result_MD_Folder/TC-075757.md](Result_MD_Folder/TC-075757.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-10 | The test claims Variable Information Indicator = 001 but the request does not send it | Test case defect | `Variable Information Indicator: not in the test data; segments sent: 100, 111, 130` | [TD-TC-075757.json line 2](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075757.json#L2) → `EMV Financial Transaction Request` | Send Variable Information Indicator = 001 in Variable Information Data Segment, or re-scope / withdraw the test. |
| Q-03 | The quote cited for p.149 is not word-for-word in ATL105 | Citation issue | `spec_basis[0]` = p.149: "Transaction Transaction Attributes Sequence o For MasterCard card type, Merchant needs to send MIT/CIT category and subcategory data with valid value as applicable to CIT transactions." | test case record [test_cases.jsonl line 158](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L158) → `spec_basis[0]` | Quote the text exactly as it appears on the page. |
| Q-03 | The quote cited for p.152 is not word-for-word in ATL105 | Citation issue | `spec_basis[1]` = p.152: "Transaction Transaction Attributes Sequence o For MasterCard card type, Merchant needs to send MIT/CIT category and subcategory data with valid value as applicable to MIT transactions." | test case record [test_cases.jsonl line 158](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L158) → `spec_basis[1]` | Quote the text exactly as it appears on the page. |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[4]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 158](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L158) → `spec_basis[4]` | Cite p.160 instead of p.161. |

<details><summary>Test data - actual, step 1: TD-TC-075757.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5139485532100688",
      "CardDiscretionaryBlockData": "290720142194190",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "757571",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "071"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601279C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F260853BE7828BC90BAB39F2701809F3303E0F0C89F34035E03029F3501229F3602C3C69F3704F97982D69F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-075757.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5139485532100688",
      "CardDiscretionaryBlockData": "290720142194190",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "757571",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",  // C-10: recalculate: 042 + 6 + length of the Table 001 data
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "071"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        },
        {  // C-10: Table 001 - the table this test claims to send
          "VariableInformationIndicator": "001",
          "VariableInformationLength": "<length of the data>",
          "VariableInformation": "<Table 001 data - Appendix I layout>"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601279C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F260853BE7828BC90BAB39F2701809F3303E0F0C89F34035E03029F3501229F3602C3C69F3704F97982D69F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-075685"></a>

### TC-075685 (BR1_TS1_TC95) - Response / oracle check: Purchase/Capture -> Purchase Reversal -> declined (issuer decline)

**Test name:** EMV Contact Chip Purchase/Capture, then Purchase Reversal (card issuer declines) → declined. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`; step 2 sends `8020` (Purchase reversal), expected Response Code `1`.

**Status:** `needs_client_input` · **AI result (this sheet):** REVIEW - correct the test case or confirm with BUYPASS · **Test case record:** [test_cases.jsonl line 86](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L86) · **Every field against ATL105:** [Result_MD_Folder/TC-075685.md](Result_MD_Folder/TC-075685.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[8]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 86](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L86) → `spec_basis[8]` | Cite p.160 instead of p.161. |

<details><summary>Test data - actual, step 1: TD-TC-075685.step1.purchase.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5197986646906760",
      "CardDiscretionaryBlockData": "320520186191354",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "756851",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601089C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F260898C2A00F4A21A3809F2701809F3303E0F0C89F34035E03029F3501229F360287DF9F37040EEF7A8B9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

Test data - expected, step 1: `TD-TC-075685.step1.purchase.json` - no change for the defects of this sheet (the same as the test data as delivered).

<details><summary>Test data - actual, step 2: TD-TC-075685.step2.reversal.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "058",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "8",
        "CardType": "020"
      },
      "AccountNumber": "<AWAITING:AccountNumber>",
      "CardDiscretionaryBlockData": "<AWAITING:CardDiscretionaryBlockData>",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "756851",
      "ApprovalNumber": "<ApprovalNumber from step 1 response>",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

Test data - expected, step 2: `TD-TC-075685.step2.reversal.json` - no change for the defects of this sheet (the same as the test data as delivered).

<a id="tc-075670"></a>

### TC-075670 (BR1_TS1_TC80) - Invalid state transition: Time-out Reversal with no original -> declined

**Test name:** EMV Contact Chip Time-out Reversal with no original transaction → declined (to confirm). **What the test does:** step 1 sends `Z020` (Time-out Reversal), expected Response Code `1`.

**Status:** `blocked_verification` · **AI result (this sheet):** REVIEW - correct the test case or confirm with BUYPASS · **Test case record:** [test_cases.jsonl line 71](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L71) · **Every field against ATL105:** [Result_MD_Folder/TC-075670.md](Result_MD_Folder/TC-075670.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[4]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 71](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L71) → `spec_basis[4]` | Cite p.160 instead of p.161. |
| INFO | Join Account Number and expiry with '=' in the wire message | Information | `5116941452533818 / 3010` | [TD-TC-075670.json line 14](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075670.json#L14), [TD-TC-075670.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075670.json#L15) → `AccountNumber, CardDiscretionaryBlockData` | The message builder sends 5116941452533818=3010. No change in the test data. |

<details><summary>Test data - actual, step 1: TD-TC-075670.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "081",
      "InformationByte": "0",
      "TerminalIdentifier": "XE6LR2ZPLXTZ9BOKDYPUAB",
      "PromptCode": {
        "TransactionType": "Z",
        "CardType": "020"
      },
      "AccountNumber": "5116941452533818",
      "CardDiscretionaryBlockData": "3010",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "756701",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

Test data - expected, step 1: `TD-TC-075670.json` - no change for the defects of this sheet (the same as the test data as delivered).

<a id="tc-077181"></a>

### TC-077181 (BR1_TS7_TC1) - NFC Payment Tokenization Data Segment (segment 123) omitted where p.267 requires it

**Test name:** Credential on File Purchase/Capture NFC Payment Tokenization Data Segment omitted where requires it → declined. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `1`.

**Status:** `requires_review` · **AI result (this sheet):** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 1582](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1582) · **Every field against ATL105:** [Result_MD_Folder/TC-077181.md](Result_MD_Folder/TC-077181.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-09 | Table 030 (POS Condition Code) is missing | Test data defect | `tables sent: 005, 031` | [TD-TC-077181.json line 27](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077181.json#L27) → `Variable Information Data Segment` | Add Table 030 (10 digits, values from Appendix Q) for the certified terminal. |
| D-09 | The Credential on File indicators are missing | Test data defect | `tables sent: 005, 031` | [TD-TC-077181.json line 27](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077181.json#L27) → `Variable Information Data Segment` | Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'); Table 056 sub-table 09 (MasterCard MIT/CIT category). |
| S-01 | Expiry format for stored credentials is not stated | To confirm with BUYPASS / SME | `1131` | Card Discretionary Block Data (entry mode 10 steps) | Confirm the expiry format for entry mode 10 with BUYPASS. |
| S-05 | Confirm the premise of this negative test | To confirm with BUYPASS / SME | `test_objective` = NFC Payment Tokenization Data Segment (segment 123) omitted where p.267 requires it | test case record [test_cases.jsonl line 1582](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1582) → `test_objective` | Confirm with BUYPASS, or send a transaction for which the segment is required. |

<details><summary>Test data - actual, step 1: TD-TC-077181.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5190149691807386",
      "CardDiscretionaryBlockData": "1131",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "771811",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "101"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-077181.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5190149691807386",
      "CardDiscretionaryBlockData": "1131",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "771811",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {  // D-09: Add Table 030 (10 digits, values from Appendix Q) for the certified terminal.; D-09: Add Table 032 sub-table 08 = 'C' (Credential on File); Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'); Table 056 sub-t
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "101"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

<a id="tc-077186"></a>

### TC-077186 (BR1_TS11_TC2) - Required field EMV Chip Data Length sent empty

**Test name:** EMV Contact Chip Authorization Only Required field EMV Chip Data Length sent empty → declined. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `1`.

**Status:** `ready` · **AI result (this sheet):** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 1587](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1587) · **Every field against ATL105:** [Result_MD_Folder/TC-077186.md](Result_MD_Folder/TC-077186.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-13 | Card Discretionary Block Data is not valid | Test data defect | `0427` | [TD-TC-077186.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077186.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `CardDiscretionaryBlockData` = the card's Track 2 data after the '=' (expiry YYMM + 3-digit service code + discretionary data) from the client's test card - or a keyed entry mode if only the expiry is known. |
| D-09 | Segment 130 length is wrong | Test data defect | `0054` | [TD-TC-077186.json line 50](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077186.json#L50) → `EMV Request Data Segment → SegmentLength` | 0201 |
| Q-01 | The citation of the rule gives no ATL105 page | Citation issue | `spec_basis[2]` = `p.None: "EMV Chip Data Length: N max 3"` | test case record [test_cases.jsonl line 1587](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1587) → `spec_basis[2]` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| N-01 | EMV Chip Data Length (Element 189) does not match the chip data | Intended violation of a negative test (correct) | `` | [TD-TC-077186.json line 53](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077186.json#L53) → `EMV Request Data Segment → EMVChipDataLength` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `147`). |

<details><summary>Test data - actual, step 1: TD-TC-077186.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5123862393198829",
      "CardDiscretionaryBlockData": "0427",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "771861",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0054",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601019C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F260850DC092FB9A071979F2701809F3303E0F0C89F34035E03029F3501229F360253529F370488E727C89F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-077186.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",  // D-13: recalculate: 055 + length of the track data + length of the Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5123862393198829",
      "CardDiscretionaryBlockData": "<Track 2 data after '=': 2704 + service code + discretionary data>",  // D-13: the card was read (Table 005) - send the track data after the '=': expiry 2704 (YYMM), service code, discretionary data - from the client's test card
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "771861",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0201",  // D-09: 0201
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "",  // N-01: different from step None ('147') on purpose - the rule this negative test breaks; keep
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601019C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F260850DC092FB9A071979F2701809F3303E0F0C89F34035E03029F3501229F360253529F370488E727C89F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-077193"></a>

### TC-077193 (BR1_TS175_TC1) - Required field Prompt Code omitted

**Test name:** SAMPLE Petroleum CAT Magnetic Stripe Authorization Only Required field Prompt Code omitted → declined. **What the test does:** step 1 sends `` (?), expected Response Code `1`.

**Status:** `needs_client_input` · **AI result (this sheet):** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 1594](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1594) · **Every field against ATL105:** [Result_MD_Folder/TC-077193.md](Result_MD_Folder/TC-077193.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-13 | Card Discretionary Block Data is not valid | Test data defect | `1030` | [TD-TC-077193.json line 12](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077193.json#L12) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `CardDiscretionaryBlockData` = the card's Track 2 data after the '=' (expiry YYMM + 3-digit service code + discretionary data) from the client's test card - or a keyed entry mode if only the expiry is known. |
| D-09 | Table 005 value is not valid | Test data defect | `021` | [TD-TC-077193.json line 35](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077193.json#L35) → `Variable Information Data Segment → VariableInformationSection[0]` | 021 |
| Q-01 | The citation of the rule gives no ATL105 page | Citation issue | `spec_basis[2]` = `p.None: "Prompt Code: AN max 4"` | test case record [test_cases.jsonl line 1594](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1594) → `spec_basis[2]` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| N-01 | Prompt Code is empty | Intended violation of a negative test (correct) | `(empty)` | [TD-TC-077193.json line 10](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077193.json#L10) → `Standard Message Data Segment → PromptCode` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `Populate Prompt Code.`). |

<details><summary>Test data - actual, step 1: TD-TC-077193.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "054",
      "InformationByte": "0",
      "TerminalIdentifier": "VZ15598162001",
      "PromptCode": "",
      "AccountNumber": "<AWAITING:AccountNumber>",
      "CardDiscretionaryBlockData": "1030",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "01",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "771931",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Product Code Data Segment": {
      "SegmentType": "102",
      "SegmentLength": "029",
      "ServiceLevel": "F",
      "NumberofProducts": "01",
      "ProductCode": "400",
      "UnitofMeasure": "U",
      "Quantity": "01",
      "UnitPrice": "21500",
      "ProductAmount": "1500"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "021"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "00"
        }
      ]
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-077193.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "054",  // D-13: recalculate: 061 + length of the track data + length of the Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "VZ15598162001",
      "PromptCode": "",  // N-01: different from step None ('Populate Prompt Code.') on purpose - the rule this negative test breaks; keep
      "AccountNumber": "<AWAITING:AccountNumber>",
      "CardDiscretionaryBlockData": "<Track 2 data after '=': 3010 + service code + discretionary data>",  // D-13: the card was read (Table 005) - send the track data after the '=': expiry 3010 (YYMM), service code, discretionary data - from the client's test card
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "01",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "771931",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Product Code Data Segment": {
      "SegmentType": "102",
      "SegmentLength": "029",
      "ServiceLevel": "F",
      "NumberofProducts": "01",
      "ProductCode": "400",
      "UnitofMeasure": "U",
      "Quantity": "01",
      "UnitPrice": "21500",
      "ProductAmount": "1500"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "021"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "00"
        }
      ]
    }
  }
}
```

</details>

<a id="tc-077219"></a>

### TC-077219 (BR1_TS1_TC4) - Variable Information one byte over its documented maximum length (982)

**Test name:** EMV Contact Chip Purchase/Capture Variable Information one byte over its documented maximum length → declined. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `1`.

**Status:** `ready` · **AI result (this sheet):** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 1620](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1620) · **Every field against ATL105:** [Result_MD_Folder/TC-077219.md](Result_MD_Folder/TC-077219.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-13 | Card Discretionary Block Data is not valid | Test data defect | `0530` | [TD-TC-077219.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077219.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `CardDiscretionaryBlockData` = the card's Track 2 data after the '=' (expiry YYMM + 3-digit service code + discretionary data) from the client's test card - or a keyed entry mode if only the expiry is known. |
| D-09 | Table 005 length is wrong | Test data defect | `` | [TD-TC-077219.json line 27](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077219.json#L27) → `Variable Information Data Segment → VariableInformationSection[0]` | 983 |
| Q-02 | Outdated page cited for the POS Entry Mode table (Table 005) | Citation issue | `request -> Variable Information Data Segment -> Variable Information (source_page), every step` = `null` | test case record [test_cases.jsonl line 1620](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1620) → `request -> Variable Information Data Segment -> Variable Information (source_page), every step` | Change the cited page from 246 to 555. The data values are not affected. |
| Q-03 | The quote cited for p.148 is not word-for-word in ATL105 | Citation issue | `spec_basis[1]` = p.148: "Transaction Transaction Attributes Sequence o For MasterCard card type, Merchant needs to send MIT/CIT category and subcategory data with valid value as applicable to CIT transactions." | test case record [test_cases.jsonl line 1620](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1620) → `spec_basis[1]` | Quote the text exactly as it appears on the page. |
| Q-03 | The quote cited for p.152 is not word-for-word in ATL105 | Citation issue | `spec_basis[2]` = p.152: "Transaction Transaction Attributes Sequence o For MasterCard card type, Merchant needs to send MIT/CIT category and subcategory data with valid value as applicable to MIT transactions." | test case record [test_cases.jsonl line 1620](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1620) → `spec_basis[2]` | Quote the text exactly as it appears on the page. |
| Q-01 | The citation of the rule gives no ATL105 page | Citation issue | `spec_basis[4]` = `p.None: "Variable Information: AN max 982"` | test case record [test_cases.jsonl line 1620](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1620) → `spec_basis[4]` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| N-01 | Segment 111 length is wrong | Intended violation of a negative test (correct) | `1019` | [TD-TC-077219.json line 29](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077219.json#L29) → `Variable Information Data Segment → SegmentLength` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `1019`). |
| N-01 | Table 005 value is not valid | Intended violation of a negative test (correct) | `AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA` | [TD-TC-077219.json line 27](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077219.json#L27) → `Variable Information Data Segment → VariableInformationSection[0]` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA`). |

<details><summary>Test data - actual, step 1: TD-TC-077219.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5194112665756134",
      "CardDiscretionaryBlockData": "0530",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "772191",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "1019",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "",
          "VariableInformation": "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601149C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F26086FBCE8D39791EB919F2701809F3303E0F0C89F34035E03029F3501229F3602D8769F3704DEF660FD9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-077219.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",  // D-13: recalculate: 055 + length of the track data + length of the Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5194112665756134",
      "CardDiscretionaryBlockData": "<Track 2 data after '=': 3005 + service code + discretionary data>",  // D-13: the card was read (Table 005) - send the track data after the '=': expiry 3005 (YYMM), service code, discretionary data - from the client's test card
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "772191",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "1019",  // N-01: different from step None ('1019') on purpose - the rule this negative test breaks; keep
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "",
          "VariableInformation": "AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601149C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F26086FBCE8D39791EB919F2701809F3303E0F0C89F34035E03029F3501229F3602D8769F3704DEF660FD9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-076596"></a>

### TC-076596 (BR1_TS11_TC15) - Running total / state: Authorization Only -> Cancellation -> Totals Request

**Test name:** EMV Contact Chip Authorization Only, then Cancellation, then Totals Request (totals include it) → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`; step 2 sends `S020` (Cancellation), expected Response Code 0/2; step 3 sends `` (?), expected Response Code 5/6/B/D/N/W.

**Status:** `requires_review` · **AI result (this sheet):** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 997](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L997) · **Every field against ATL105:** [Result_MD_Folder/TC-076596.md](Result_MD_Folder/TC-076596.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-01 | The totals do not match the transactions of the flow | Test data defect | `count 00001, amount 00000000, grand total 00000000` | [TD-TC-076596.step3.totals.json line 5](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076596.step3.totals.json#L5) → `Totals Data Segment` | Leave out the CC bucket and send Grand Total 00000000 |
| C-04 | The test case does not state the expected Response Code | Test case issue to correct | `step 2 expected_response` = `null` | test case record [test_cases.jsonl line 997](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L997) → `step 2 expected_response` | State the expected Response Code for this transaction type (from BUYPASS or a reference run). |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[10]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 997](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L997) → `spec_basis[10]` | Cite p.160 instead of p.161. |
| INFO | Join Account Number and expiry with '=' in the wire message | Information | `5186759436129544 / 3101` | [TD-TC-076596.step2.cancellation.json line 14](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076596.step2.cancellation.json#L14), [TD-TC-076596.step2.cancellation.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076596.step2.cancellation.json#L15) → `AccountNumber, CardDiscretionaryBlockData` | The message builder sends 5186759436129544=3101. No change in the test data. |

<details><summary>Test data - actual, step 1: TD-TC-076596.step1.auth-only.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5186759436129544",
      "CardDiscretionaryBlockData": "310120158760032",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "765961",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601059C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608141A54908577E6169F2701809F3303E0F0C89F34035E03029F3501229F36028A1B9F3704905867C89F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

Test data - expected, step 1: `TD-TC-076596.step1.auth-only.json` - no change for the defects of this sheet (the same as the test data as delivered).

<details><summary>Test data - actual, step 2: TD-TC-076596.step2.cancellation.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "078",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "S",
        "CardType": "020"
      },
      "AccountNumber": "5186759436129544",
      "CardDiscretionaryBlockData": "3101",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "765961",
      "ApprovalNumber": "<ApprovalNumber from step 1 response>",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

Test data - expected, step 2: `TD-TC-076596.step2.cancellation.json` - no change for the defects of this sheet (the same as the test data as delivered).

<details><summary>Test data - actual, step 3: TD-TC-076596.step3.totals.json (click to expand)</summary>

```json
{
  "Totals Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "01",
    "Totals Data Segment": {
      "SegmentType": "105",
      "SegmentLength": "101",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": "990",
      "EmployeeNumber": "",
      "Password": "",
      "TotalsDate": "111111",
      "HardwareVersion": "H86V8MTA",
      "SoftwareVersion": "C33JC0M4",
      "FirmwareVersion": "UHYXXEE0",
      "SequenceNumber": "765962",
      "CurrencyCode": "840",
      "GrandTotal": "00000000",
      "CardLabel": "CC  ",
      "CardTypeTotalCount": "00001",
      "CardTypeTotalAmount": "00000000"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 3: TD-TC-076596.step3.totals.json (changed lines end with //)</summary>

```jsonc
{
  "Totals Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "01",
    "Totals Data Segment": {  // D-01: Leave out the CC bucket and send Grand Total 00000000
      "SegmentType": "105",
      "SegmentLength": "101",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": "990",
      "EmployeeNumber": "",
      "Password": "",
      "TotalsDate": "111111",
      "HardwareVersion": "H86V8MTA",
      "SoftwareVersion": "C33JC0M4",
      "FirmwareVersion": "UHYXXEE0",
      "SequenceNumber": "765962",
      "CurrencyCode": "840",
      "GrandTotal": "00000000",
      "CardLabel": "CC  ",
      "CardTypeTotalCount": "00001",
      "CardTypeTotalAmount": "00000000"
    }
  }
}
```

</details>

<a id="tc-076613"></a>

### TC-076613 (BR1_TS12_TC13) - Running total / state: Authorization Only -> Cancellation -> Totals Request

**Test name:** EMV Contactless Chip Authorization Only, then Cancellation, then Totals Request (totals include it) → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`; step 2 sends `S020` (Cancellation), expected Response Code 0/2; step 3 sends `` (?), expected Response Code 5/6/B/D/N/W.

**Status:** `requires_review` · **AI result (this sheet):** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 1014](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1014) · **Every field against ATL105:** [Result_MD_Folder/TC-076613.md](Result_MD_Folder/TC-076613.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-01 | The totals do not match the transactions of the flow | Test data defect | `count 00001, amount 00000000, grand total 00000000` | [TD-TC-076613.step3.totals.json line 5](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076613.step3.totals.json#L5) → `Totals Data Segment` | Leave out the CC bucket and send Grand Total 00000000 |
| C-04 | The test case does not state the expected Response Code | Test case issue to correct | `step 2 expected_response` = `null` | test case record [test_cases.jsonl line 1014](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1014) → `step 2 expected_response` | State the expected Response Code for this transaction type (from BUYPASS or a reference run). |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[10]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 1014](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1014) → `spec_basis[10]` | Cite p.160 instead of p.161. |
| INFO | Join Account Number and expiry with '=' in the wire message | Information | `5106597975164408 / 2801` | [TD-TC-076613.step2.cancellation.json line 14](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076613.step2.cancellation.json#L14), [TD-TC-076613.step2.cancellation.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076613.step2.cancellation.json#L15) → `AccountNumber, CardDiscretionaryBlockData` | The message builder sends 5106597975164408=2801. No change in the test data. |

<details><summary>Test data - actual, step 1: TD-TC-076613.step1.auth-only.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5106597975164408",
      "CardDiscretionaryBlockData": "280120179821035",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "766131",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "071"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601249C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F260818D0406571B4AE169F2701809F3303E0F0C89F34035E03029F3501229F360223949F370412F3E0A29F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

Test data - expected, step 1: `TD-TC-076613.step1.auth-only.json` - no change for the defects of this sheet (the same as the test data as delivered).

<details><summary>Test data - actual, step 2: TD-TC-076613.step2.cancellation.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "078",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "S",
        "CardType": "020"
      },
      "AccountNumber": "5106597975164408",
      "CardDiscretionaryBlockData": "2801",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "766131",
      "ApprovalNumber": "<ApprovalNumber from step 1 response>",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "071"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

Test data - expected, step 2: `TD-TC-076613.step2.cancellation.json` - no change for the defects of this sheet (the same as the test data as delivered).

<details><summary>Test data - actual, step 3: TD-TC-076613.step3.totals.json (click to expand)</summary>

```json
{
  "Totals Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "01",
    "Totals Data Segment": {
      "SegmentType": "105",
      "SegmentLength": "101",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": "990",
      "EmployeeNumber": "",
      "Password": "",
      "TotalsDate": "111111",
      "HardwareVersion": "PCWCHE0E",
      "SoftwareVersion": "UTFOYCO7",
      "FirmwareVersion": "7WO2I7PD",
      "SequenceNumber": "766132",
      "CurrencyCode": "840",
      "GrandTotal": "00000000",
      "CardLabel": "CC  ",
      "CardTypeTotalCount": "00001",
      "CardTypeTotalAmount": "00000000"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 3: TD-TC-076613.step3.totals.json (changed lines end with //)</summary>

```jsonc
{
  "Totals Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "01",
    "Totals Data Segment": {  // D-01: Leave out the CC bucket and send Grand Total 00000000
      "SegmentType": "105",
      "SegmentLength": "101",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": "990",
      "EmployeeNumber": "",
      "Password": "",
      "TotalsDate": "111111",
      "HardwareVersion": "PCWCHE0E",
      "SoftwareVersion": "UTFOYCO7",
      "FirmwareVersion": "7WO2I7PD",
      "SequenceNumber": "766132",
      "CurrencyCode": "840",
      "GrandTotal": "00000000",
      "CardLabel": "CC  ",
      "CardTypeTotalCount": "00001",
      "CardTypeTotalAmount": "00000000"
    }
  }
}
```

</details>

<a id="tc-075751"></a>

### TC-075751 (BR1_TS2_TC53) - Single rule broken: Purchase/Capture with Payment Tender Type sent where the spec forbids it -> declined

**Test name:** EMV Contactless Chip Purchase/Capture with Payment Tender Type sent where it is not allowed → declined (to confirm). **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `1`.

**Status:** `requires_review` · **AI result (this sheet):** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 152](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L152) · **Every field against ATL105:** [Result_MD_Folder/TC-075751.md](Result_MD_Folder/TC-075751.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-02 | The rule this negative test claims to break is not in ATL105 | Test case defect | `test_objective` = `Single rule broken: Purchase/Capture with Payment Tender Type sent where the spec forbids it -> declined`; expected Response Code `1` | test case record [test_cases.jsonl line 152](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L152) → `test_objective` | Remove or rewrite the test around a rule ATL105 states. |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[3]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 152](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L152) → `spec_basis[3]` | Cite p.160 instead of p.161. |
| S-03 | Loyalty Card Data Segment: length depends on an unclear Field Separator rule | To confirm with BUYPASS / SME | `120` | [TD-TC-075751.json line 29](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075751.json#L29) → `Loyalty Card Data Segment → SegmentLength` | Confirm the Field Separator rule of this segment with BUYPASS. |

<details><summary>Test data - actual, step 1: TD-TC-075751.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "04",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5105262543550676",
      "CardDiscretionaryBlockData": "280320174344867",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "757511",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Loyalty Card Data Segment": {
      "SegmentType": "108",
      "SegmentLength": "120",
      "LoyaltyProgramID": "685399",
      "LoyaltyAccountNumber": "",
      "PointstoRedeem": "527426",
      "CouponID": "9362020444811442195",
      "CouponAmount": "81396510",
      "UpdateCode": "",
      "StreetAddress": "",
      "PhoneNumberLoyalty": "",
      "ExpirationDate": "",
      "PaymentTenderType": "AX",
      "LoyaltyTrack2Data": "51203710631613261127709396827146171221",
      "LoyaltyInformationVersion": "1",
      "UnitofWork": "7770287320331915518"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "071"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601209C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608A4BD02D51CB3AC7C9F2701809F3303E0F0C89F34035E03029F3501229F3602E1C39F3704D13842199F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-075751.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {  // C-02: no change to the request - what must change is the test (its premise and the expected decline); see the finding
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "04",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5105262543550676",
      "CardDiscretionaryBlockData": "280320174344867",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "757511",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Loyalty Card Data Segment": {
      "SegmentType": "108",
      "SegmentLength": "120",  // S-03: 120 = a Field Separator after every field; other readings of ATL105 give 118 - confirm with BUYPASS
      "LoyaltyProgramID": "685399",
      "LoyaltyAccountNumber": "",
      "PointstoRedeem": "527426",
      "CouponID": "9362020444811442195",
      "CouponAmount": "81396510",
      "UpdateCode": "",
      "StreetAddress": "",
      "PhoneNumberLoyalty": "",
      "ExpirationDate": "",
      "PaymentTenderType": "AX",
      "LoyaltyTrack2Data": "51203710631613261127709396827146171221",
      "LoyaltyInformationVersion": "1",
      "UnitofWork": "7770287320331915518"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "071"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601209C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608A4BD02D51CB3AC7C9F2701809F3303E0F0C89F34035E03029F3501229F3602E1C39F3704D13842199F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-075667"></a>

### TC-075667 (BR1_TS1_TC77) - Valid flow: Purchase/Capture -> Time-out Reversal

**Test name:** EMV Contact Chip Purchase/Capture, then Time-out Reversal → approved. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`; step 2 sends `Z020` (Time-out Reversal), expected Response Code 0/2.

**Status:** `ready` · **AI result (this sheet):** REVIEW - correct the test case or confirm with BUYPASS · **Test case record:** [test_cases.jsonl line 68](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L68) · **Every field against ATL105:** [Result_MD_Folder/TC-075667.md](Result_MD_Folder/TC-075667.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-04 | The test case accepts several Response Codes instead of one | Test case issue to correct | `expected_response.response_code (step 2)` = `null` | test case record [test_cases.jsonl line 68](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L68) → `expected_response.response_code (step 2)` | State the one expected Response Code for this transaction type (from BUYPASS or a reference run). |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[5]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 68](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L68) → `spec_basis[5]` | Cite p.160 instead of p.161. |
| C-06 | Step 2 sends a Time-out Reversal although step 1 is expected to be answered | To confirm with BUYPASS / SME | `tx_legs[0].expect` = {"tier": "ASSERT", "page": 403, "response_code": "0", "note": "Approved-Purchase/Capture"} and `harness` = `null` (no fault injection) | test case record [test_cases.jsonl line 68](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L68) → `tx_legs[0].expect` | Make step 1 a fault-injected 'no response' (or a late response the harness ignores). Step 1 then expects no response (instead of Response Code 0), and the device sends the Time-out Reversal after the 30-second response interval. |
| INFO | Join Account Number and expiry with '=' in the wire message | Information | `5120905983728753 / 2909` | [TD-TC-075667.step2.timeout-reversal.json line 14](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075667.step2.timeout-reversal.json#L14), [TD-TC-075667.step2.timeout-reversal.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075667.step2.timeout-reversal.json#L15) → `AccountNumber, CardDiscretionaryBlockData` | The message builder sends 5120905983728753=2909. No change in the test data. |

<details><summary>Test data - actual, step 1: TD-TC-075667.step1.purchase.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5120905983728753",
      "CardDiscretionaryBlockData": "290920186211065",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "756671",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601059C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608E516E19722E88A5D9F2701809F3303E0F0C89F34035E03029F3501229F3602CFAF9F3704E69192F79F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

Test data - expected, step 1: `TD-TC-075667.step1.purchase.json` - no change for the defects of this sheet (the same as the test data as delivered).

<details><summary>Test data - actual, step 2: TD-TC-075667.step2.timeout-reversal.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "Z",
        "CardType": "020"
      },
      "AccountNumber": "5120905983728753",
      "CardDiscretionaryBlockData": "2909",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "756671",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

Test data - expected, step 2: `TD-TC-075667.step2.timeout-reversal.json` - no change for the defects of this sheet (the same as the test data as delivered).

<a id="tc-076423"></a>

### TC-076423 (BR1_TS7_TC103) - Response / oracle check: Purchase/Capture sending Merchant Supplementary Data -> Merchant Advice Code returned

**Test name:** Credential on File Purchase/Capture sending Merchant Supplementary Data (Merchant Advice Code returned) → approved. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`.

**Status:** `ready` · **AI result (this sheet):** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 824](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L824) · **Every field against ATL105:** [Result_MD_Folder/TC-076423.md](Result_MD_Folder/TC-076423.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-11 | The Credential on File indicators are missing | Test data defect | `tables sent: 005, 031, 030, 032, 049, 056` | [TD-TC-076423.json line 27](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076423.json#L27) → `Variable Information Data Segment` | Add Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M'). |
| S-01 | Expiry format for stored credentials is not stated | To confirm with BUYPASS / SME | `0929` | Card Discretionary Block Data (entry mode 10 steps) | Confirm the expiry format for entry mode 10 with BUYPASS. |

<details><summary>Test data - actual, step 1: TD-TC-076423.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5161527891760486",
      "CardDiscretionaryBlockData": "0929",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "764231",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "078",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "101"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100100004"
        },
        {
          "VariableInformationIndicator": "032",
          "VariableInformationLength": "006",
          "VariableInformation": "08001C"
        },
        {
          "VariableInformationIndicator": "049",
          "VariableInformationLength": "004",
          "VariableInformation": "071Y"
        },
        {
          "VariableInformationIndicator": "056",
          "VariableInformationLength": "008",
          "VariableInformation": "0904C101"
        }
      ]
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-076423.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5161527891760486",
      "CardDiscretionaryBlockData": "0929",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "764231",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {  // D-11: Add Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M').
      "SegmentType": "111",
      "SegmentLength": "078",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "101"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100100004"
        },
        {
          "VariableInformationIndicator": "032",
          "VariableInformationLength": "006",
          "VariableInformation": "08001C"
        },
        {
          "VariableInformationIndicator": "049",
          "VariableInformationLength": "004",
          "VariableInformation": "071Y"
        },
        {
          "VariableInformationIndicator": "056",
          "VariableInformationLength": "008",
          "VariableInformation": "0904C101"
        }
      ]
    }
  }
}
```

</details>

<a id="tc-077185"></a>

### TC-077185 (BR1_TS31_TC1) - Merchant Trace ID exactly at its documented maximum length (25 bytes) is accepted

**Test name:** Magnetic Stripe Balance Inquiry Merchant Trace ID exactly at its documented maximum length (25 bytes) is accepted → approved. **What the test does:** step 1 sends `E020` (Balance Inquiry), expected Response Code not stated.

**Status:** `requires_review` · **AI result (this sheet):** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 1586](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1586) · **Every field against ATL105:** [Result_MD_Folder/TC-077185.md](Result_MD_Folder/TC-077185.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-13 | Card Discretionary Block Data is not valid | Test data defect | `0232` | [TD-TC-077185.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077185.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `CardDiscretionaryBlockData` = the card's Track 2 data after the '=' (expiry YYMM + 3-digit service code + discretionary data) from the client's test card - or a keyed entry mode if only the expiry is known. |
| D-11 | Table 005 value is not valid | Test data defect | `021` | [TD-TC-077185.json line 27](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077185.json#L27) → `Variable Information Data Segment → VariableInformationSection[0]` | 021 |
| C-04 | The test case does not state the expected Response Code | Test case issue to correct | `step 1 expected_response` = `null` | test case record [test_cases.jsonl line 1586](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1586) → `step 1 expected_response` | State the expected Response Code for this transaction type (from BUYPASS or a reference run). |
| Q-01 | The citation of the rule gives no ATL105 page | Citation issue | `spec_basis[1]` = `p.None: "Merchant Trace ID: AN max 25"` | test case record [test_cases.jsonl line 1586](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1586) → `spec_basis[1]` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |

<details><summary>Test data - actual, step 1: TD-TC-077185.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "068",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "E",
        "CardType": "020"
      },
      "AccountNumber": "5110138382451890",
      "CardDiscretionaryBlockData": "0232",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "771851",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "021"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    },
    "ECA/ TeleCheck Data Segment Format ®": {
      "SegmentType": "113",
      "SegmentLength": "051",
      "ECATeleCheckClerkID": "1",
      "ECATeleCheckProductCode": "",
      "ECATeleCheckPhoneNumber": "7346278919",
      "ECATeleCheckTraceID": "",
      "MerchantTraceID": "AAAAAAAAAAAAAAAAAAAAAAAAA",
      "DenialRecordNumber": "",
      "ExtendedMICRData": ""
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-077185.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "068",  // D-13: recalculate: 051 + length of the track data + length of the Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "E",
        "CardType": "020"
      },
      "AccountNumber": "5110138382451890",
      "CardDiscretionaryBlockData": "<Track 2 data after '=': 3202 + service code + discretionary data>",  // D-13: the card was read (Table 005) - send the track data after the '=': expiry 3202 (YYMM), service code, discretionary data - from the client's test card
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "771851",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "021"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    },
    "ECA/ TeleCheck Data Segment Format ®": {
      "SegmentType": "113",
      "SegmentLength": "051",
      "ECATeleCheckClerkID": "1",
      "ECATeleCheckProductCode": "",
      "ECATeleCheckPhoneNumber": "7346278919",
      "ECATeleCheckTraceID": "",
      "MerchantTraceID": "AAAAAAAAAAAAAAAAAAAAAAAAA",
      "DenialRecordNumber": "",
      "ExtendedMICRData": ""
    }
  }
}
```

</details>

<a id="tc-077206"></a>

### TC-077206 (BR1_TS1_TC1) - Encrypted PIN Block Data exactly at its documented maximum length (36 bytes) is accepted

**Test name:** EMV Contact Chip Purchase/Capture Encrypted PIN Block Data exactly at its documented maximum length (36 bytes) is accepted → approved. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`.

**Status:** `needs_client_input` · **AI result (this sheet):** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 1607](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1607) · **Every field against ATL105:** [Result_MD_Folder/TC-077206.md](Result_MD_Folder/TC-077206.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-13 | Card Discretionary Block Data is not valid | Test data defect | `0927` | [TD-TC-077206.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077206.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `CardDiscretionaryBlockData` = the card's Track 2 data after the '=' (expiry YYMM + 3-digit service code + discretionary data) from the client's test card - or a keyed entry mode if only the expiry is known. |
| Q-01 | The citation of the rule gives no ATL105 page | Citation issue | `spec_basis[2]` = `p.None: "Encrypted PIN Block Data: AN max 36"` | test case record [test_cases.jsonl line 1607](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1607) → `spec_basis[2]` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |

<details><summary>Test data - actual, step 1: TD-TC-077206.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5140924315113085",
      "CardDiscretionaryBlockData": "0927",
      "EncryptedPINBlockData": "<AWAITING:EncryptedPINBlockData>",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "772061",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601079C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608D614843B18A8C3189F2701809F3303E0F0C89F34035E03029F3501229F36029ACB9F37044B51B49E9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-077206.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",  // D-13: recalculate: 087 + length of the track data + length of the Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5140924315113085",
      "CardDiscretionaryBlockData": "<Track 2 data after '=': 2709 + service code + discretionary data>",  // D-13: the card was read (Table 005) - send the track data after the '=': expiry 2709 (YYMM), service code, discretionary data - from the client's test card
      "EncryptedPINBlockData": "<AWAITING:EncryptedPINBlockData>",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "772061",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601079C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608D614843B18A8C3189F2701809F3303E0F0C89F34035E03029F3501229F36029ACB9F37044B51B49E9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-077218"></a>

### TC-077218 (BR1_TS29_TC1) - Information Byte exactly at its documented maximum length (1 bytes) is accepted

**Test name:** EMV Contact Chip Balance Inquiry Information Byte exactly at its documented maximum length (1 bytes) is accepted → approved. **What the test does:** step 1 sends `E020` (Balance Inquiry), expected Response Code not stated.

**Status:** `requires_review` · **AI result (this sheet):** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 1619](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1619) · **Every field against ATL105:** [Result_MD_Folder/TC-077218.md](Result_MD_Folder/TC-077218.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-15 | Information Byte is not valid | Test data defect | `A` | [TD-TC-077218.json line 8](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077218.json#L8) → `Standard Message Data Segment → InformationByte` | A value that meets: 1 character: 'A' = not a valid code. |
| D-13 | Card Discretionary Block Data is not valid | Test data defect | `0228` | [TD-TC-077218.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077218.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `CardDiscretionaryBlockData` = the card's Track 2 data after the '=' (expiry YYMM + 3-digit service code + discretionary data) from the client's test card - or a keyed entry mode if only the expiry is known. |
| C-04 | The test case does not state the expected Response Code | Test case issue to correct | `step 1 expected_response` = `null` | test case record [test_cases.jsonl line 1619](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1619) → `step 1 expected_response` | State the expected Response Code for this transaction type (from BUYPASS or a reference run). |
| Q-01 | The citation of the rule gives no ATL105 page | Citation issue | `spec_basis[2]` = `p.None: "Information Byte: AN max 1"` | test case record [test_cases.jsonl line 1619](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1619) → `spec_basis[2]` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |

<details><summary>Test data - actual, step 1: TD-TC-077218.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "068",
      "InformationByte": "A",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "E",
        "CardType": "020"
      },
      "AccountNumber": "5144321361713734",
      "CardDiscretionaryBlockData": "0228",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "772181",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601099C01319F02060000000000009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F26081FF743C2CD1132FB9F2701809F3303E0F0C89F34035E03029F3501229F3602E33F9F370430BDE2969F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-077218.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "068",  // D-13: recalculate: 051 + length of the track data + length of the Terminal Identifier
      "InformationByte": "A",  // D-15: A value that meets: 1 character: 'A' = not a valid code.
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "E",
        "CardType": "020"
      },
      "AccountNumber": "5144321361713734",
      "CardDiscretionaryBlockData": "<Track 2 data after '=': 2802 + service code + discretionary data>",  // D-13: the card was read (Table 005) - send the track data after the '=': expiry 2802 (YYMM), service code, discretionary data - from the client's test card
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "772181",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601099C01319F02060000000000009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F26081FF743C2CD1132FB9F2701809F3303E0F0C89F34035E03029F3501229F3602E33F9F370430BDE2969F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<a id="tc-076725"></a>

### TC-076725 (BR1_TS19_TC8) - Valid flow: Mail/Phone Authorization Only -> Mail/Phone Purchase

**Test name:** Manual / Keyed Mail/Phone Authorization Only, then Mail/Phone Purchase → approved. **What the test does:** step 1 sends `B020` (Mail/Phone Authorization Only), expected Response Code `2`; step 2 sends `6020` (Mail/Phone Purchase), expected Response Code `0`.

**Status:** `ready` · **AI result (this sheet):** REVIEW - correct the test case or confirm with BUYPASS · **Test case record:** [test_cases.jsonl line 1126](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1126) · **Every field against ATL105:** [Result_MD_Folder/TC-076725.md](Result_MD_Folder/TC-076725.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| Q-03 | The quote cited for p.409 is not word-for-word in ATL105 | Citation issue | `spec_basis[1]` = p.409: "In Purchase/Capture, Sequence Number is same as in Authorization Only request." | test case record [test_cases.jsonl line 1126](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1126) → `spec_basis[1]` | Quote the text exactly as it appears: "In the case of a preauthorized transaction, the Sequence Number in the Purchase/Capture is the same Sequence Number as in the transaction’s Authorization Only request.". |
| S-05 | Completion of a Mail/Phone Authorization Only: Transaction Type to confirm | To confirm with BUYPASS / SME | `6020` | [TD-TC-076725.step2.completion.json line 10](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076725.step2.completion.json#L10) → `Standard Message Data Segment → PromptCode` | Confirm with BUYPASS whether a Mail/Phone Authorization Only is completed with Transaction Type 6 or 0. |
| V-01 | Card Discretionary Block Data is not valid | AI validator rule not applicable - value checked and correct | `0427` | [TD-TC-076725.step2.completion.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076725.step2.completion.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | This step completes the Authorization Only of step 1 (preauthorized Purchase/Capture). Credit completions send Account Number = YYMM (Section 10.1.8.2), which is what the file has; the AI rule treated the step as a new purchase. |
| INFO | Join Account Number and expiry with '=' in the wire message | Information | `5130868284295864 / 0427` | [TD-TC-076725.step2.completion.json line 14](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076725.step2.completion.json#L14), [TD-TC-076725.step2.completion.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076725.step2.completion.json#L15) → `AccountNumber, CardDiscretionaryBlockData` | The message builder sends 5130868284295864=0427. No change in the test data. |

<details><summary>Test data - actual, step 1: TD-TC-076725.step1.moto-auth-only.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "B",
        "CardType": "020"
      },
      "AccountNumber": "5130868284295864",
      "CardDiscretionaryBlockData": "0427",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "767251",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

Test data - expected, step 1: `TD-TC-076725.step1.moto-auth-only.json` - no change for the defects of this sheet (the same as the test data as delivered).

<details><summary>Test data - actual, step 2: TD-TC-076725.step2.completion.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "078",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "6",
        "CardType": "020"
      },
      "AccountNumber": "5130868284295864",
      "CardDiscretionaryBlockData": "0427",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "767251",
      "ApprovalNumber": "<ApprovalNumber from step 1 response>",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 2: TD-TC-076725.step2.completion.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "078",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {  // S-05: Confirm with BUYPASS whether a Mail/Phone Authorization Only is completed with Transaction Type 6 or 0.
        "TransactionType": "6",
        "CardType": "020"
      },
      "AccountNumber": "5130868284295864",
      "CardDiscretionaryBlockData": "0427",  // INFO: the wire message sends AccountNumber '=' this value: 5130868284295864=0427
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "767251",
      "ApprovalNumber": "<ApprovalNumber from step 1 response>",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

<a id="tc-076723"></a>

### TC-076723 (BR1_TS19_TC6) - Decision table: Authorization Only with segment 153 sent (the rule's condition holds)

**Test name:** Manual / Keyed Mail/Phone Authorization Only with Network Token Data Request sent → approved. **What the test does:** step 1 sends `B020` (Mail/Phone Authorization Only), expected Response Code `2`.

**Status:** `ready` · **AI result (this sheet):** REVIEW - correct the test case or confirm with BUYPASS · **Test case record:** [test_cases.jsonl line 1124](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1124) · **Every field against ATL105:** [Result_MD_Folder/TC-076723.md](Result_MD_Folder/TC-076723.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-05 | The test objective names a different transaction than the one sent | Test case issue to correct | `test_objective` = Decision table: Authorization Only with segment 153 sent (the rule's condition holds) | test case record [test_cases.jsonl line 1124](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1124) → `test_objective` | `test_objective` = `Decision table: Mail/Phone Authorization Only with segment 153 sent (the rule's condition holds)` - Correct the objective to Mail/Phone Authorization Only. |
| Q-04 | Cited Segment 153 rule is for Table 006 on subsequent transactions | Citation issue | `spec_basis[0]` = p.312: "This data is applicable only to subsequent transactions (Example: Reversal, Completion, Cancellation)." | test case record [test_cases.jsonl line 1124](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1124) → `spec_basis[0]` | Cite the Segment 153 layout (p.310) as the basis. |
| S-03 | Network Token Data Request: length depends on an unclear Field Separator rule | To confirm with BUYPASS / SME | `051` | [TD-TC-076723.json line 45](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076723.json#L45) → `Network Token Data Request → SegmentLengthIndicator` | Confirm the Field Separator rule of this segment with BUYPASS. |
| S-04 | Network Token Input Indicator 'Y' while Element 2 is a card number | To confirm with BUYPASS / SME | `Y` | [TD-TC-076723.json line 46](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076723.json#L46) → `Network Token Data Request → NetworkTokenData (table 004)` | Confirm with BUYPASS; use 'N' (clear PAN) if Element 2 holds the card number. |

<details><summary>Test data - actual, step 1: TD-TC-076723.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "B",
        "CardType": "020"
      },
      "AccountNumber": "5167557820382510",
      "CardDiscretionaryBlockData": "1131",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "767231",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    },
    "Network Token Data Request": {
      "SegmentType": "153",
      "SegmentLengthIndicator": "051",
      "NetworkTokenData": "00101916695895566197364200020042807004001Y"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-076723.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "B",
        "CardType": "020"
      },
      "AccountNumber": "5167557820382510",
      "CardDiscretionaryBlockData": "1131",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "767231",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "011"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    },
    "Network Token Data Request": {
      "SegmentType": "153",
      "SegmentLengthIndicator": "051",  // S-03: 051 = a Field Separator after every field; other readings of ATL105 give 049, 050 - confirm with BUYPASS
      "NetworkTokenData": "00101916695895566197364200020042807004001N"  // S-04: table 004 'N' (Clear PAN) if Element 2 keeps the card number - confirm with BUYPASS
    }
  }
}
```

</details>

<a id="tc-075601"></a>

### TC-075601 (BR1_TS1_TC11) - Happy path: Purchase/Capture sending Table 001 UPC Information

**Test name:** EMV Contact Chip Purchase/Capture with UPC Information → approved. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`.

**Status:** `needs_client_input` · **AI result (this sheet):** REVIEW - correct the test case or confirm with BUYPASS · **Test case record:** [test_cases.jsonl line 2](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L2) · **Every field against ATL105:** [Result_MD_Folder/TC-075601.md](Result_MD_Folder/TC-075601.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| Q-03 | The quote cited for p.552 is not word-for-word in ATL105 | Citation issue | `spec_basis[0]` = `p.552: "Table 001 UPC Information"` | test case record [test_cases.jsonl line 2](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L2) → `spec_basis[0]` | Quote the text exactly as it appears on the page. |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[3]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 2](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L2) → `spec_basis[3]` | Cite p.160 instead of p.161. |

<details><summary>Test data - actual, step 1: TD-TC-075601.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5158644613167836",
      "CardDiscretionaryBlockData": "311020127370826",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "756011",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "045",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        },
        {
          "VariableInformationIndicator": "001",
          "VariableInformationLength": "<AWAITING:VariableInformationLength[4]>",
          "VariableInformation": "<AWAITING:VariableInformation[4]>"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601189C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608710EA4CA4BB4CBE49F2701809F3303E0F0C89F34035E03029F3501229F3602A9B89F3704FCF6E8079F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

Test data - expected, step 1: `TD-TC-075601.json` - no change for the defects of this sheet (the same as the test data as delivered).

<a id="tc-075810"></a>

### TC-075810 (BR1_TS3_TC12) - Happy path: Purchase/Capture sending Table 031 Point-of-Service Terminal Capability Codes

**Test name:** Magnetic Stripe Purchase/Capture with Point-of-Service Terminal Capability Codes → approved. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`.

**Status:** `ready` · **AI result (this sheet):** REVIEW - correct the test case or confirm with BUYPASS · **Test case record:** [test_cases.jsonl line 211](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L211) · **Every field against ATL105:** [Result_MD_Folder/TC-075810.md](Result_MD_Folder/TC-075810.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| Q-03 | The quote cited for p.569 is not word-for-word in ATL105 | Citation issue | `spec_basis[0]` = `p.569: "Table 031 Point-of-Service Terminal Capability Codes"` | test case record [test_cases.jsonl line 211](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L211) → `spec_basis[0]` | Quote the text exactly as it appears on the page. |
| C-08 | Same request as TC-075801 (BR1_TS3_TC3) | To confirm with BUYPASS / SME | `expected 0 vs 0` | TD-TC-075810.json vs TD-TC-075801.json | Check the difference on the device / in the response; optionally use another card. |

<details><summary>Test data - actual, step 1: TD-TC-075810.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5159417392576181",
      "CardDiscretionaryBlockData": "300510138259706",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "758101",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "901"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

Test data - expected, step 1: `TD-TC-075810.json` - no change for the defects of this sheet (the same as the test data as delivered).

<a id="tc-075855"></a>

### TC-075855 (BR1_TS3_TC57) - Decision table: Purchase/Capture with Payment Tender Type = AX (the rule's condition holds)

**Test name:** Magnetic Stripe Purchase/Capture with Payment Tender Type = AX → approved. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `0`.

**Status:** `ready` · **AI result (this sheet):** REVIEW - correct the test case or confirm with BUYPASS · **Test case record:** [test_cases.jsonl line 256](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L256) · **Every field against ATL105:** [Result_MD_Folder/TC-075855.md](Result_MD_Folder/TC-075855.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| S-03 | Loyalty Card Data Segment: length depends on an unclear Field Separator rule | To confirm with BUYPASS / SME | `120` | [TD-TC-075855.json line 29](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075855.json#L29) → `Loyalty Card Data Segment → SegmentLength` | Confirm the Field Separator rule of this segment with BUYPASS. |

<details><summary>Test data - actual, step 1: TD-TC-075855.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5155309880938755",
      "CardDiscretionaryBlockData": "270710144763203",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "758551",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Loyalty Card Data Segment": {
      "SegmentType": "108",
      "SegmentLength": "120",
      "LoyaltyProgramID": "386100",
      "LoyaltyAccountNumber": "",
      "PointstoRedeem": "405547",
      "CouponID": "3789137446807968051",
      "CouponAmount": "42516884",
      "UpdateCode": "",
      "StreetAddress": "",
      "PhoneNumberLoyalty": "",
      "ExpirationDate": "",
      "PaymentTenderType": "AX",
      "LoyaltyTrack2Data": "51365391054292631132043485522841716766",
      "LoyaltyInformationVersion": "1",
      "UnitofWork": "3691975643415173078"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "901"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-075855.json (changed lines end with //)</summary>

```jsonc
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5155309880938755",
      "CardDiscretionaryBlockData": "270710144763203",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "758551",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Loyalty Card Data Segment": {
      "SegmentType": "108",
      "SegmentLength": "120",  // S-03: 120 = a Field Separator after every field; other readings of ATL105 give 118 - confirm with BUYPASS
      "LoyaltyProgramID": "386100",
      "LoyaltyAccountNumber": "",
      "PointstoRedeem": "405547",
      "CouponID": "3789137446807968051",
      "CouponAmount": "42516884",
      "UpdateCode": "",
      "StreetAddress": "",
      "PhoneNumberLoyalty": "",
      "ExpirationDate": "",
      "PaymentTenderType": "AX",
      "LoyaltyTrack2Data": "51365391054292631132043485522841716766",
      "LoyaltyInformationVersion": "1",
      "UnitofWork": "3691975643415173078"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "901"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

<a id="tc-076597"></a>

### TC-076597 (BR1_TS11_TC16) - Running total / state: Authorization Only -> Purchase/Capture -> Totals Request

**Test name:** EMV Contact Chip Authorization Only, then Purchase/Capture, then Totals Request (totals include it) → approved. **What the test does:** step 1 sends `3020` (POS Authorization Only), expected Response Code `2`; step 2 sends `0020` (POS Purchase/Capture), expected Response Code `0`; step 3 sends `` (?), expected Response Code 5/6/B/D/N/W.

**Status:** `requires_review` · **AI result (this sheet):** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 998](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L998) · **Every field against ATL105:** [Result_MD_Folder/TC-076597.md](Result_MD_Folder/TC-076597.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-01 | The totals do not match the transactions of the flow | Test data defect | `count 00001, amount 00000000, grand total 00000000` | [TD-TC-076597.step3.totals.json line 5](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076597.step3.totals.json#L5) → `Totals Data Segment` | Count 00001, amount 00001500, grand total 00001500 |
| Q-03 | The quote cited for p.409 is not word-for-word in ATL105 | Citation issue | `spec_basis[1]` = p.409: "In Purchase/Capture, Sequence Number is same as in Authorization Only request." | test case record [test_cases.jsonl line 998](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L998) → `spec_basis[1]` | Quote the text exactly as it appears: "In the case of a preauthorized transaction, the Sequence Number in the Purchase/Capture is the same Sequence Number as in the transaction’s Authorization Only request.". |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[10]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 998](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L998) → `spec_basis[10]` | Cite p.160 instead of p.161. |
| S-05 | The completion of an EMV authorization carries no chip data | To confirm with BUYPASS / SME | `(absent)` | TD-TC-076597.step2.completion.json | Confirm with BUYPASS whether a completion of an EMV authorization is sent without Segment 130. |
| S-02 | The Authorization Only step has no AO bucket in the totals | To confirm with BUYPASS / SME | `CC  ` | [TD-TC-076597.step3.totals.json line 5](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076597.step3.totals.json#L5) → `Totals Data Segment` | Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed. |
| INFO | Join Account Number and expiry with '=' in the wire message | Information | `5196663933849620 / 3202` | [TD-TC-076597.step2.completion.json line 14](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076597.step2.completion.json#L14), [TD-TC-076597.step2.completion.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076597.step2.completion.json#L15) → `AccountNumber, CardDiscretionaryBlockData` | The message builder sends 5196663933849620=3202. No change in the test data. |

<details><summary>Test data - actual, step 1: TD-TC-076597.step1.auth-only.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "3",
        "CardType": "020"
      },
      "AccountNumber": "5196663933849620",
      "CardDiscretionaryBlockData": "320220148087727",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "765971",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601169C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608FD5CD1B0D2B9C8519F2701809F3303E0F0C89F34035E03029F3501229F36021E979F37041DEE36139F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

Test data - expected, step 1: `TD-TC-076597.step1.auth-only.json` - no change for the defects of this sheet (the same as the test data as delivered).

<details><summary>Test data - actual, step 2: TD-TC-076597.step2.completion.json (click to expand)</summary>

```json
{
  "Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "02",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "078",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5196663933849620",
      "CardDiscretionaryBlockData": "3202",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "765971",
      "ApprovalNumber": "<ApprovalNumber from step 1 response>",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "026",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        }
      ]
    }
  }
}
```

</details>

Test data - expected, step 2: `TD-TC-076597.step2.completion.json` - no change for the defects of this sheet (the same as the test data as delivered).

<details><summary>Test data - actual, step 3: TD-TC-076597.step3.totals.json (click to expand)</summary>

```json
{
  "Totals Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "01",
    "Totals Data Segment": {
      "SegmentType": "105",
      "SegmentLength": "101",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": "990",
      "EmployeeNumber": "",
      "Password": "",
      "TotalsDate": "111111",
      "HardwareVersion": "MZ3AAYCX",
      "SoftwareVersion": "QQND0U7W",
      "FirmwareVersion": "TTHXJWLT",
      "SequenceNumber": "765972",
      "CurrencyCode": "840",
      "GrandTotal": "00000000",
      "CardLabel": "CC  ",
      "CardTypeTotalCount": "00001",
      "CardTypeTotalAmount": "00000000"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 3: TD-TC-076597.step3.totals.json (changed lines end with //)</summary>

```jsonc
{
  "Totals Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "01",
    "Totals Data Segment": {  // D-01: Count 00001, amount 00001500, grand total 00001500; S-02: Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed.
      "SegmentType": "105",
      "SegmentLength": "101",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": "990",
      "EmployeeNumber": "",
      "Password": "",
      "TotalsDate": "111111",
      "HardwareVersion": "MZ3AAYCX",
      "SoftwareVersion": "QQND0U7W",
      "FirmwareVersion": "TTHXJWLT",
      "SequenceNumber": "765972",
      "CurrencyCode": "840",
      "GrandTotal": "00000000",
      "CardLabel": "CC  ",
      "CardTypeTotalCount": "00001",
      "CardTypeTotalAmount": "00000000"
    }
  }
}
```

</details>

<a id="tc-076780"></a>

### TC-076780 (BR1_TS21_TC10) - Running total / state: Merchandise Return approved -> Totals Request

**Test name:** EMV Contact Chip Merchandise Return, then Totals Request (totals include it) → approved. **What the test does:** step 1 sends `7020` (Merchandise return), expected Response Code 0/2; step 2 sends `` (?), expected Response Code 5/6/B/D/N/W.

**Status:** `requires_review` · **AI result (this sheet):** REVIEW - correct the test case or confirm with BUYPASS · **Test case record:** [test_cases.jsonl line 1181](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1181) · **Every field against ATL105:** [Result_MD_Folder/TC-076780.md](Result_MD_Folder/TC-076780.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| C-04 | The test case does not state the expected Response Code | Test case issue to correct | `step 1 expected_response` = `null` | test case record [test_cases.jsonl line 1181](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1181) → `step 1 expected_response` | State the expected Response Code for this transaction type (from BUYPASS or a reference run). |
| Q-02 | The quote cited for p.161 is on the neighbouring page | Citation issue | `spec_basis[9]` = p.161: "The following credit cards are supported for EMV processing: ... MasterCard" | test case record [test_cases.jsonl line 1181](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1181) → `spec_basis[9]` | Cite p.160 instead of p.161. |
| S-06 | The totals do not match the transactions of the flow | To confirm with BUYPASS / SME | `count 00001, amount 00000000, grand total 00000000` | [TD-TC-076780.step2.totals.json line 5](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076780.step2.totals.json#L5) → `Totals Data Segment` | Confirm with BUYPASS how a negative net amount is reported in the Totals Request. |

<details><summary>Test data - actual, step 1: TD-TC-076780.step1.return.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "03",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "083",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": {
        "TransactionType": "7",
        "CardType": "020"
      },
      "AccountNumber": "5171099068413032",
      "CardDiscretionaryBlockData": "280820130561838",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "767801",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601079C01209F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F2608A1412E1A904CD03C9F2701809F3303E0F0C89F34035E03029F3501229F36024CA59F37049403166C9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

Test data - expected, step 1: `TD-TC-076780.step1.return.json` - no change for the defects of this sheet (the same as the test data as delivered).

<details><summary>Test data - actual, step 2: TD-TC-076780.step2.totals.json (click to expand)</summary>

```json
{
  "Totals Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "01",
    "Totals Data Segment": {
      "SegmentType": "105",
      "SegmentLength": "101",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": "990",
      "EmployeeNumber": "",
      "Password": "",
      "TotalsDate": "111111",
      "HardwareVersion": "EC1AYF6Q",
      "SoftwareVersion": "ICIUZVQS",
      "FirmwareVersion": "73FB5C66",
      "SequenceNumber": "767802",
      "CurrencyCode": "840",
      "GrandTotal": "00000000",
      "CardLabel": "CC  ",
      "CardTypeTotalCount": "00001",
      "CardTypeTotalAmount": "00000000"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 2: TD-TC-076780.step2.totals.json (changed lines end with //)</summary>

```jsonc
{
  "Totals Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "01",
    "Totals Data Segment": {  // S-06: Confirm with BUYPASS how a negative net amount is reported in the Totals Request.
      "SegmentType": "105",
      "SegmentLength": "101",
      "InformationByte": "0",
      "TerminalIdentifier": "JX41894821001",
      "PromptCode": "990",
      "EmployeeNumber": "",
      "Password": "",
      "TotalsDate": "111111",
      "HardwareVersion": "EC1AYF6Q",
      "SoftwareVersion": "ICIUZVQS",
      "FirmwareVersion": "73FB5C66",
      "SequenceNumber": "767802",
      "CurrencyCode": "840",
      "GrandTotal": "00000000",
      "CardLabel": "CC  ",
      "CardTypeTotalCount": "00001",
      "CardTypeTotalAmount": "00000000"
    }
  }
}
```

</details>

<a id="tc-077179"></a>

### TC-077179 (BR1_TS29_TC4) - required segment 100 Standard Message Data Segment omitted

**Test name:** EMV Contact Chip Balance Inquiry required Standard Message Data Segment omitted → declined. **What the test does:** .

**Status:** `blocked_no_test_data` · **AI result (this sheet):** REVIEW - correct the test case or confirm with BUYPASS · **Test case record:** [test_cases.jsonl line 1580](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1580) · **Every field against ATL105:** [Result_MD_Folder/TC-077179.md](Result_MD_Folder/TC-077179.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| Q-01 | The citation of p.62 has no quoted text | Citation issue | `spec_basis` = [{"page": 162, "quote": "In a credit/signature debit transaction, a PIN of all F's is NOT required since PINs are an optional field for a credit/signature debit transaction.", "rule_id": "BR-162-2", "source_id": "SRC-ATL105-PDF-001"}, {"page": 66, "quote": "Credit Message o CVM can be either ODCVM or No CVM with no PIN block. o If PIN block required, Message must have a valid and properly PIN bloc | test case record [test_cases.jsonl line 1580](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1580) → `spec_basis` | Add the ATL105 sentence the rule is based on. |
| Q-01 | The citation of p.112 has no quoted text | Citation issue | `spec_basis` = [{"page": 162, "quote": "In a credit/signature debit transaction, a PIN of all F's is NOT required since PINs are an optional field for a credit/signature debit transaction.", "rule_id": "BR-162-2", "source_id": "SRC-ATL105-PDF-001"}, {"page": 66, "quote": "Credit Message o CVM can be either ODCVM or No CVM with no PIN block. o If PIN block required, Message must have a valid and properly PIN bloc | test case record [test_cases.jsonl line 1580](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1580) → `spec_basis` | Add the ATL105 sentence the rule is based on. |
| Q-01 | The citation of p.545 has no quoted text | Citation issue | `spec_basis` = [{"page": 162, "quote": "In a credit/signature debit transaction, a PIN of all F's is NOT required since PINs are an optional field for a credit/signature debit transaction.", "rule_id": "BR-162-2", "source_id": "SRC-ATL105-PDF-001"}, {"page": 66, "quote": "Credit Message o CVM can be either ODCVM or No CVM with no PIN block. o If PIN block required, Message must have a valid and properly PIN bloc | test case record [test_cases.jsonl line 1580](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1580) → `spec_basis` | Add the ATL105 sentence the rule is based on. |

<a id="tc-077203"></a>

### TC-077203 (BR1_TS147_TC3) - Customer Data contains a character outside its character type (AN)

**Test name:** SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture Customer Data contains a character outside its character type (AN) → declined. **What the test does:** step 1 sends `0020` (POS Purchase/Capture), expected Response Code `1`.

**Status:** `needs_client_input` · **AI result (this sheet):** FAIL - No - fix the defects first · **Test case record:** [test_cases.jsonl line 1604](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1604) · **Every field against ATL105:** [Result_MD_Folder/TC-077203.md](Result_MD_Folder/TC-077203.md)

| Id | Finding | Kind | Actual | Where | Expected |
|---|---|---|---|---|---|
| D-13 | Card Discretionary Block Data is not valid | Test data defect | `0828` | [TD-TC-077203.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077203.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData` | `CardDiscretionaryBlockData` = the card's Track 2 data after the '=' (expiry YYMM + 3-digit service code + discretionary data) from the client's test card - or a keyed entry mode if only the expiry is known. |
| Q-01 | The citation of the rule gives no ATL105 page | Citation issue | `spec_basis[1]` = `p.None: "Customer Data: AN max 12"` | test case record [test_cases.jsonl line 1604](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1604) → `spec_basis[1]` | Cite the Chapter 13 page of the element and quote its ATL105 text word for word. |
| C-09 | Fleet Data Segment sent on a MasterCard Credit card | To confirm with BUYPASS / SME | `Segment 101` | [TD-TC-077203.json line 27](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077203.json#L27) → `Fleet Data Segment` | Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it. |
| N-01 | Fleet Data Segment: Customer Data breaks its Chapter 13 format | Intended violation of a negative test (correct) | `AAAAAAAAAAA#` | [TD-TC-077203.json line 38](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077203.json#L38) → `Fleet Data Segment → CustomerData` | Keep it - this is the one rule this negative test breaks on purpose (a valid message would have `A value of type AN with at most 12 characters.`). |

<details><summary>Test data - actual, step 1: TD-TC-077203.json (click to expand)</summary>

```json
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "04",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",
      "InformationByte": "0",
      "TerminalIdentifier": "KC47645707001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5157230215107288",
      "CardDiscretionaryBlockData": "0828",
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "772031",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Fleet Data Segment": {
      "SegmentType": "101",
      "SegmentLength": "036",
      "Odometer": "",
      "VehicleNumber": "",
      "JobNumber": "",
      "DriverIdentificationNumber": "",
      "FleetEmployeeNumber": "",
      "License": "",
      "JobID": "",
      "Department": "",
      "CustomerData": "AAAAAAAAAAA#",
      "UserID": "",
      "VehicleID": "",
      "FleetTag1": "",
      "FleetTag2": "",
      "FleetTag3": "",
      "FleetTag4": "",
      "FleetTag5": ""
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601039C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F260846FEA43053F3C85C9F2701809F3303E0F0C89F34035E03029F3501229F360254F09F37047C99628B9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

<details open><summary>Test data - expected, step 1: TD-TC-077203.json (changed lines end with //)</summary>

```jsonc
{
  "EMV Financial Transaction Request": {
    "MessageFormatVersionIdentifier": "ATL105",
    "NumberofSegments": "04",
    "Standard Message Data Segment": {
      "SegmentType": "100",
      "SegmentLength": "072",  // D-13: recalculate: 055 + length of the track data + length of the Terminal Identifier
      "InformationByte": "0",
      "TerminalIdentifier": "KC47645707001",
      "PromptCode": {
        "TransactionType": "0",
        "CardType": "020"
      },
      "AccountNumber": "5157230215107288",
      "CardDiscretionaryBlockData": "<Track 2 data after '=': 2808 + service code + discretionary data>",  // D-13: the card was read (Table 005) - send the track data after the '=': expiry 2808 (YYMM), service code, discretionary data - from the client's test card
      "EncryptedPINBlockData": "",
      "PumpLaneNumber": "",
      "FuelPurchaseAmount": "",
      "NonfuelAmount": "1500",
      "TaxAmount": "",
      "CashAmount": "",
      "SequenceNumber": "772031",
      "ApprovalNumber": "",
      "LocalDateandLocalTime": "",
      "PartialApprovalIndicator": "1"
    },
    "Fleet Data Segment": {  // C-09: Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it.
      "SegmentType": "101",
      "SegmentLength": "036",
      "Odometer": "",
      "VehicleNumber": "",
      "JobNumber": "",
      "DriverIdentificationNumber": "",
      "FleetEmployeeNumber": "",
      "License": "",
      "JobID": "",
      "Department": "",
      "CustomerData": "AAAAAAAAAAA#",  // N-01: different from step None ('A value of type AN with at most 12 characters.') on purpose - the rule this negative test breaks; keep
      "UserID": "",
      "VehicleID": "",
      "FleetTag1": "",
      "FleetTag2": "",
      "FleetTag3": "",
      "FleetTag4": "",
      "FleetTag5": ""
    },
    "Variable Information Data Segment": {
      "SegmentType": "111",
      "SegmentLength": "042",
      "VariableInformationSection": [
        {
          "VariableInformationIndicator": "005",
          "VariableInformationLength": "003",
          "VariableInformation": "051"
        },
        {
          "VariableInformationIndicator": "031",
          "VariableInformationLength": "002",
          "VariableInformation": "12"
        },
        {
          "VariableInformationIndicator": "030",
          "VariableInformationLength": "010",
          "VariableInformation": "0100000004"
        }
      ]
    },
    "EMV Request Data Segment": {
      "SegmentType": "130",
      "SegmentLength": "0204",
      "CAPublicKeyFileChecksum": "2012091700000100000102171",
      "EMVCardSequenceNumber": "001",
      "EMVChipDataLength": "147",
      "EMVChipData": "01455F2A0208405F340101820258008407A0000000041010950580000080009A032601039C01009F02060000000015009F03060000000000009F0607A00000000410109F1008010103A40000DAC09F1A0208409F1E0831323334353637389F260846FEA43053F3C85C9F2701809F3303E0F0C89F34035E03029F3501229F360254F09F37047C99628B9F41030128509F530152",
      "EMVAdditionalInformationIndicator": "001",
      "EMVAdditionalInformationLength": "006",
      "EMVAdditionalInformation": "EMVYES"
    }
  }
}
```

</details>

## How to demonstrate the defects during the BA discussion

1. **Tax Type 1 = GST is claimed by the test but not sent in the request** - open [TC-075654](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L55) and read the test name and objective, then open [TD-TC-075654.json line 2](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075654.json#L2) → `EMV Financial Transaction Request`: actual `Tax Type 1: not in the test data; segments sent: 100, 111, 130`; expected: Send Segment 102 (Product Code) and Segment 143 (Tax by Product) with the same product(s) in the same order, the inclusive/exclusive flag (I or E), Tax Type 1 = GST and the tax amount; recalculate the segment count and lengths. GST is a Canadian tax type and Segment 143 is sent only if the account manager instructs it (Section 11.1), so confirm the merchant context - or re-scope / withdraw the test.
2. **Variable Information Indicator = 001 is claimed by the test but not sent in the request** - open [TC-075656](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L57) and read the test name and objective, then open [TD-TC-075656.json line 2](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075656.json#L2) → `EMV Financial Transaction Request`: actual `Variable Information Indicator: not in the test data; segments sent: 100, 111, 130`; expected: Send Variable Information Indicator = 001 in Variable Information Data Segment, or re-scope / withdraw the test.
3. **Follow-on steps send the keyed card's expiry as MMYY instead of YYMM** - open [TC-076166](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L567) and read the test name and objective, then open [TD-TC-076166.step2.reversal.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076166.step2.reversal.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData`: actual `0928`; expected: `CardDiscretionaryBlockData` = `2809` - the expiry as YYMM (the original's MMYY `0928` with month and year swapped), as every follow-on transaction sends it.
4. **Other negative tests that break a second rule besides the one under test** - open [TC-077181](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1582) and read the test name and objective, then open [TD-TC-077181.json line 27](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077181.json#L27) → `Variable Information Data Segment`: actual `tables sent: 005, 031`; expected: Add Table 030 (10 digits, values from Appendix Q) for the certified terminal.
5. **Card read from the chip or stripe, but the card data is only an MMYY expiry** - open [TC-077186](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1587) and read the test name and objective, then open [TD-TC-077186.json line 15](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077186.json#L15) → `Standard Message Data Segment → CardDiscretionaryBlockData`: actual `0427`; expected: `CardDiscretionaryBlockData` = the card's Track 2 data after the '=' (expiry YYMM + 3-digit service code + discretionary data) from the client's test card - or a keyed entry mode if only the expiry is known.
6. **Totals Request does not match the transactions of the flow** - open [TC-076596](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L997) and read the test name and objective, then open [TD-TC-076596.step3.totals.json line 5](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076596.step3.totals.json#L5) → `Totals Data Segment`: actual `count 00001, amount 00000000, grand total 00000000`; expected: Leave out the CC bucket and send Grand Total 00000000
7. **Negative tests whose premise is not in ATL105 - the expected decline is not justified** - open [TC-075650](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L51) and read the test name and objective, then open test case record [test_cases.jsonl line 51](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L51) → `test_objective`: actual `test_objective` = `Single rule broken: Purchase/Capture with Payment Tender Type sent where the spec forbids it -> declined`; expected Response Code `1`; expected: Remove or rewrite the test around a rule ATL105 states.
8. **Time-out Reversal sent although the original request is expected to be answered** - open [TC-076168](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L569) and read the test name and objective, then open test case record [test_cases.jsonl line 569](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L569) → `tx_legs[0].expect`: actual `tx_legs[0].expect` = {"tier": "ASSERT", "page": 403, "response_code": "0", "note": "Approved-Purchase/Capture"} and `harness` = `null` (no fault injection); expected: Make step 1 a fault-injected 'no response' (or a late response the harness ignores). Step 1 then expects no response (instead of Response Code 0), and the device sends the Time-out Reversal after the 30-second response interval.
9. **Segment 100 / 111 / 130 / 105 value breaks an ATL105 rule (trained validator)** - open [TC-076423](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L824) and read the test name and objective, then open [TD-TC-076423.json line 27](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076423.json#L27) → `Variable Information Data Segment`: actual `tables sent: 005, 031, 030, 032, 049, 056`; expected: Add Table 049 sub-table 02 (Transaction Initiation Indicator 'C' or 'M').
10. **Positive boundary test: the value is not at the documented limit or is not a valid value** - open [TC-077218](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1619) and read the test name and objective, then open [TD-TC-077218.json line 8](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077218.json#L8) → `Standard Message Data Segment → InformationByte`: actual `A`; expected: A value that meets: 1 character: 'A' = not a valid code.
11. **Table 048 (3-D Secure authentication data) is now sent, but on POS purchases** - open [TC-075618](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L19) and read the test name and objective, then open [TD-TC-075618.json line 27](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075618.json#L27) → `Variable Information Data Segment → Table 048`: actual `1`; expected: Confirm with BUYPASS; otherwise send Table 048 only in e-commerce (3-D Secure) tests, or re-scope the test.
12. **Mail/Phone Authorization Only completed with Transaction Type 6 - the code is to be confirmed** - open [TC-076725](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1126) and read the test name and objective, then open [TD-TC-076725.step2.completion.json line 10](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076725.step2.completion.json#L10) → `Standard Message Data Segment → PromptCode`: actual `6020`; expected: Confirm with BUYPASS whether a Mail/Phone Authorization Only is completed with Transaction Type 6 or 0.
13. **The test objective names a different transaction than the one sent** - open [TC-076723](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1124) and read the test name and objective, then open test case record [test_cases.jsonl line 1124](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1124) → `test_objective`: actual `test_objective` = Decision table: Authorization Only with segment 153 sent (the rule's condition holds); expected: `test_objective` = `Decision table: Mail/Phone Authorization Only with segment 153 sent (the rule's condition holds)` - Correct the objective to Mail/Phone Authorization Only.
14. **Network Token Input Indicator and the Segment 153 / 108 lengths need confirmation** - open [TC-076658](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1059) and read the test name and objective, then open [TD-TC-076658.json line 45](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076658.json#L45) → `Network Token Data Request → SegmentLengthIndicator`: actual `051`; expected: Confirm the Field Separator rule of this segment with BUYPASS.
15. **Expiry format for stored credentials (entry mode 10) not stated** - open [TC-077181](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1582) and read the test name and objective, then open Card Discretionary Block Data (entry mode 10 steps): actual `1131`; expected: Confirm the expiry format for entry mode 10 with BUYPASS.
16. **Other points to confirm** - open [TC-077181](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1582) and read the test name and objective, then open test case record [test_cases.jsonl line 1582](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1582) → `test_objective`: actual `test_objective` = NFC Payment Tokenization Data Segment (segment 123) omitted where p.267 requires it; expected: Confirm with BUYPASS, or send a transaction for which the segment is required.
17. **Expected Response Code not stated for a step, or several codes accepted** - open [TC-076596](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L997) and read the test name and objective, then open test case record [test_cases.jsonl line 997](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L997) → `step 2 expected_response`: actual `step 2 expected_response` = `null`; expected: State the expected Response Code for this transaction type (from BUYPASS or a reference run).
18. **Same request as another test case (difference only on the device / response side)** - open [TC-075810](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L211) and read the test name and objective, then open TD-TC-075810.json vs TD-TC-075801.json: actual `expected 0 vs 0`; expected: Check the difference on the device / in the response; optionally use another card.
19. **Totals: Authorization Only (AO) bucket after the flow** - open [TC-076597](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L998) and read the test name and objective, then open [TD-TC-076597.step3.totals.json line 5](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076597.step3.totals.json#L5) → `Totals Data Segment`: actual `CC  `; expected: Confirm with BUYPASS whether the AO bucket is sent after the authorization is completed.
20. **Totals after a Merchandise Return: the net amount is negative, but ATL105 defines no sign for the totals amounts** - open [TC-076780](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1181) and read the test name and objective, then open [TD-TC-076780.step2.totals.json line 5](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076780.step2.totals.json#L5) → `Totals Data Segment`: actual `count 00001, amount 00000000, grand total 00000000`; expected: Confirm with BUYPASS how a negative net amount is reported in the Totals Request.
21. **Segment sent although ATL105 scopes it to other card programs** - open [TC-077203](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L1604) and read the test name and objective, then open [TD-TC-077203.json line 27](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077203.json#L27) → `Fleet Data Segment`: actual `Segment 101`; expected: Confirm with BUYPASS whether the segment applies to MasterCard Credit; otherwise drop it.
22. **ATL105 citations in the test cases are on the wrong page, not ATL105 text, or about another context** - open [TC-076166](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L567) and read the test name and objective, then open test case record [test_cases.jsonl line 567](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl#L567) → `spec_basis[2]`: actual `spec_basis[2]` = p.402: "The Sequence Number must be the Sequence Number contained in the original transaction."; expected: `spec_basis[2].page` = `84` instead of `402` - the quoted text itself is right.

## Summary for BA

**22 defects to discuss in 36 test cases; 10 of them are high priority.** 12 defects of the full sheet (and the placeholder part of two more) are filtered out as placeholders, run-time or environment values.

Raised in the BA defect log:

- Tax Type 1 = GST is claimed by the test but not sent in the request - 2 test case(s) (BA review 19-11-2026 (GST) - still open, now on Purchase/Capture).
- Negative tests whose premise is not in ATL105 - the expected decline is not justified - 2 test case(s) (BA review 19-11-2026 (Segment 108) - premise defect still in other tests).
- Table 048 (3-D Secure authentication data) is now sent, but on POS purchases - 1 test case(s) (BA review 16/19-11-2026 (Table 048 not sent) - fixed: now sent; scope to confirm).

Also found by the validator (high priority):

- Variable Information Indicator = 001 is claimed by the test but not sent in the request - 2 test case(s) (new in the 20-11 run).
- Follow-on steps send the keyed card's expiry as MMYY instead of YYMM - 3 test case(s) (new in the 20-11 run (0 test cases in 16-11)).
- Other negative tests that break a second rule besides the one under test - 4 test case(s).
- Card read from the chip or stripe, but the card data is only an MMYY expiry - 7 test case(s) (also in 16-11 (37 test cases)).
- Totals Request does not match the transactions of the flow - 3 test case(s) (also in 16-11 (28 test cases)).
- Time-out Reversal sent although the original request is expected to be answered - 2 test case(s) (also in 16-11 (62 test cases)).
- Segment 100 / 111 / 130 / 105 value breaks an ATL105 rule (trained validator) - 2 test case(s).
- Positive boundary test: the value is not at the documented limit or is not a valid value - 1 test case(s).

Medium and low priority: Table 048 (3-D Secure authentication data) is now sent, but on POS purchases (1); Mail/Phone Authorization Only completed with Transaction Type 6 - the code is to be confirmed (1); The test objective names a different transaction than the one sent (1); Network Token Input Indicator and the Segment 153 / 108 lengths need confirmation (5); Expiry format for stored credentials (entry mode 10) not stated (2); Other points to confirm (2); Expected Response Code not stated for a step, or several codes accepted (6); Same request as another test case (difference only on the device / response side) (1); Totals: Authorization Only (AO) bucket after the flow (1); Totals after a Merchandise Return: the net amount is negative, but ATL105 defines no sign for the totals amounts (1); Segment sent although ATL105 scopes it to other card programs (1); ATL105 citations in the test cases are on the wrong page, not ATL105 text, or about another context (33).

The risk is misleading test results and coverage, not a demonstrated host defect: the requests must match the scenario each test claims. Client, run-time and environment values (Terminal Identifier, Approval Number of the previous response, `<AWAITING:...>` values, EMV cryptograms, device versions) are filled in at execution and are not part of this discussion.

## The other two parts of the run set

The run set also contains two separately built parts. Each has its own defect sheet in the same layout (defects, every
affected test case, actual and expected test data of representative test cases):

| Part | Build | Test cases | Test data files | AI result | Largest defect groups | Defect sheet |
|---|---|---:|---:|---|---|---|
| Data element validation | `buypass-atl105-mastercard-credit-generated-35c195d1e7` | 1,030 | 1,014 | FAIL 489 · BLOCKED 98 · REVIEW 443 | D-13 457, C-03 318, C-04 128, C-08 94, D-09 40, D-15 33 | [Data_Element_Validation_Defect_Sheet.md](Data_Element_Validation/Data_Element_Validation_Defect_Sheet.md) |
| Terminal & host operations | `buypass-atl105-mastercard-credit-generated-6181e6cf0e` | 47 | 42 | BLOCKED 10 · REVIEW 31 · DEVICE 6 | C-04 21, C-08 13, C-03 6 | [Terminal_Host_Operations_Defect_Sheet.md](Terminal_Host_Operations/Terminal_Host_Operations_Defect_Sheet.md) |

Not checked field by field: 485 requests of request types the validator has no trained ATL105 rules
for - Totals with Proprietary Data Load Request (112), Proprietary Data Load Request (86), Electronic Mail Request (75), CA Public Key File Load Request (56), TransArmor Key Load Request (47), Moneris Key Load Request (29), Table Load Request (29), Software Load Request (27), Date and Time Load Request (10), Phone Load Request (10), Communications Test Request (4). For these the validator checked only the test case
level (status, expected response, citations, identical requests); their test data must be reviewed against their Chapter 11
/ 12 layouts by hand.

Those two sheets are not filtered here; the same filter can be applied to them.

<a id="appendix"></a>

## Appendix - run files checked, documents and how this sheet was produced

The run's `MANIFEST.json` lists 2,075 files with their SHA-256; 2,075 of them are in the folder. Files these test cases use:

| File | SHA-256 | Same as MANIFEST.json |
|---|---|---|
| [test_cases/test_cases.jsonl](../Dhan_Singh_run_set_20-11/test_cases/test_cases.jsonl) | `293dc20a335cf08b…` | yes |
| [test_data/TD-TC-076120.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076120.json) | `1bf765e461508fd5…` | yes |
| [test_data/TD-TC-076166.step1.purchase.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076166.step1.purchase.json) | `c72184c368c7b5dd…` | yes |
| [test_data/TD-TC-076166.step2.reversal.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076166.step2.reversal.json) | `8a4377a89a971706…` | yes |
| [test_data/TD-TC-076167.step1.purchase.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076167.step1.purchase.json) | `9ba745dd96024787…` | yes |
| [test_data/TD-TC-076167.step2.reversal.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076167.step2.reversal.json) | `52669f7001d91847…` | yes |
| [test_data/TD-TC-076168.step1.purchase.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076168.step1.purchase.json) | `03a4d789c5ca631d…` | yes |
| [test_data/TD-TC-076168.step2.timeout-reversal.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076168.step2.timeout-reversal.json) | `934dfa70f8b6ec76…` | yes |
| [test_data/TD-TC-076658.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076658.json) | `ce21dbb2ebfa040c…` | yes |
| [test_data/TD-TC-075618.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075618.json) | `66c6bf422339aba9…` | yes |
| [test_data/TD-TC-075654.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075654.json) | `bf3ed4def7229dce…` | yes |
| [test_data/TD-TC-075699.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075699.json) | `3f592006cb9d637c…` | yes |
| [test_data/TD-TC-075657.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075657.json) | `c20b126e91b88872…` | yes |
| [test_data/TD-TC-076586.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076586.json) | `8c012406c76852bf…` | yes |
| [test_data/TD-TC-075650.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075650.json) | `f8fff903b5809f38…` | yes |
| [test_data/TD-TC-075755.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075755.json) | `742ef13ab0b20a41…` | yes |
| [test_data/TD-TC-075656.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075656.json) | `6c756e18e861c515…` | yes |
| [test_data/TD-TC-075757.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075757.json) | `818327d6a4fd55be…` | yes |
| [test_data/TD-TC-075685.step1.purchase.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075685.step1.purchase.json) | `d50bf97036bef5a1…` | yes |
| [test_data/TD-TC-075685.step2.reversal.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075685.step2.reversal.json) | `5bfef8066f35f37a…` | yes |
| [test_data/TD-TC-075670.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075670.json) | `76c6d10b97eec694…` | yes |
| [test_data/TD-TC-077181.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077181.json) | `56a39b574037d510…` | yes |
| [test_data/TD-TC-077186.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077186.json) | `4413c718ae7c8866…` | yes |
| [test_data/TD-TC-077193.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077193.json) | `88d7fddc521e4a3f…` | yes |
| [test_data/TD-TC-077219.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077219.json) | `05be3f641c57d0ee…` | yes |
| [test_data/TD-TC-076596.step1.auth-only.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076596.step1.auth-only.json) | `a856ba1173b9af1d…` | yes |
| [test_data/TD-TC-076596.step2.cancellation.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076596.step2.cancellation.json) | `2fb8b7193051d82f…` | yes |
| [test_data/TD-TC-076596.step3.totals.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076596.step3.totals.json) | `2cf3625957012059…` | yes |
| [test_data/TD-TC-076613.step1.auth-only.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076613.step1.auth-only.json) | `3a423ac5db28fb68…` | yes |
| [test_data/TD-TC-076613.step2.cancellation.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076613.step2.cancellation.json) | `97384e3fb0dd08ae…` | yes |
| [test_data/TD-TC-076613.step3.totals.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076613.step3.totals.json) | `a58022816d849396…` | yes |
| [test_data/TD-TC-075751.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075751.json) | `1d8ee0bbee616735…` | yes |
| [test_data/TD-TC-075667.step1.purchase.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075667.step1.purchase.json) | `135e83830b47ae64…` | yes |
| [test_data/TD-TC-075667.step2.timeout-reversal.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075667.step2.timeout-reversal.json) | `a024082cf29667ed…` | yes |
| [test_data/TD-TC-076423.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076423.json) | `0f00623b8fbdffe3…` | yes |
| [test_data/TD-TC-077185.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077185.json) | `865f2e8bd89cfab0…` | yes |
| [test_data/TD-TC-077206.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077206.json) | `b3fc4d99fffc89d1…` | yes |
| [test_data/TD-TC-077218.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077218.json) | `b4739bb8ef3a8719…` | yes |
| [test_data/TD-TC-076725.step1.moto-auth-only.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076725.step1.moto-auth-only.json) | `42dd12ccc9edaef0…` | yes |
| [test_data/TD-TC-076725.step2.completion.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076725.step2.completion.json) | `902f3b5ea151f7b8…` | yes |
| [test_data/TD-TC-076723.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076723.json) | `d667470e749bf2d8…` | yes |
| [test_data/TD-TC-075601.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075601.json) | `19e04d96cfc4ddba…` | yes |
| [test_data/TD-TC-075810.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075810.json) | `ac62203dbb09d03e…` | yes |
| [test_data/TD-TC-075855.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-075855.json) | `ca84c2aa8707fcd3…` | yes |
| [test_data/TD-TC-076597.step1.auth-only.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076597.step1.auth-only.json) | `10a398150cfe20ad…` | yes |
| [test_data/TD-TC-076597.step2.completion.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076597.step2.completion.json) | `4f57d0094fb392fa…` | yes |
| [test_data/TD-TC-076597.step3.totals.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076597.step3.totals.json) | `c198847adba39e8f…` | yes |
| [test_data/TD-TC-076780.step1.return.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076780.step1.return.json) | `e767d70d0e3f385e…` | yes |
| [test_data/TD-TC-076780.step2.totals.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-076780.step2.totals.json) | `0ea41bd5e43220bd…` | yes |
| [test_data/TD-TC-077203.json](../Dhan_Singh_run_set_20-11/test_data/TD-TC-077203.json) | `d72dd8f4aeb6ae05…` | yes |

The other 1,961 test data files of the run were read as well, for the checks across test cases (Sequence Number per terminal, identical requests).

| Document | What it is |
|---|---|
| [Result_MD_Folder/ATL105-TD-Validation-Summary.md](Result_MD_Folder/ATL105-TD-Validation-Summary.md) | Summary: every finding with the ATL105 section, PDF page, printed page and exact text, how to check it by hand and the fix |
| [Result_MD_Folder/TC-076120.md](Result_MD_Folder/TC-076120.md) | BR1_TS5_TC1: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-076166.md](Result_MD_Folder/TC-076166.md) | BR1_TS5_TC47: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-076167.md](Result_MD_Folder/TC-076167.md) | BR1_TS5_TC48: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-076168.md](Result_MD_Folder/TC-076168.md) | BR1_TS5_TC49: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-076658.md](Result_MD_Folder/TC-076658.md) | BR1_TS15_TC7: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-075618.md](Result_MD_Folder/TC-075618.md) | BR1_TS1_TC28: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-075654.md](Result_MD_Folder/TC-075654.md) | BR1_TS1_TC64: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-075699.md](Result_MD_Folder/TC-075699.md) | BR1_TS1_TC109: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-075657.md](Result_MD_Folder/TC-075657.md) | BR1_TS1_TC67: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-076586.md](Result_MD_Folder/TC-076586.md) | BR1_TS11_TC5: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-075650.md](Result_MD_Folder/TC-075650.md) | BR1_TS1_TC60: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-075755.md](Result_MD_Folder/TC-075755.md) | BR1_TS2_TC57: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-075656.md](Result_MD_Folder/TC-075656.md) | BR1_TS1_TC66: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-075757.md](Result_MD_Folder/TC-075757.md) | BR1_TS2_TC59: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-075685.md](Result_MD_Folder/TC-075685.md) | BR1_TS1_TC95: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-075670.md](Result_MD_Folder/TC-075670.md) | BR1_TS1_TC80: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-077181.md](Result_MD_Folder/TC-077181.md) | BR1_TS7_TC1: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-077186.md](Result_MD_Folder/TC-077186.md) | BR1_TS11_TC2: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-077193.md](Result_MD_Folder/TC-077193.md) | BR1_TS175_TC1: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-077219.md](Result_MD_Folder/TC-077219.md) | BR1_TS1_TC4: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-076596.md](Result_MD_Folder/TC-076596.md) | BR1_TS11_TC15: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-076613.md](Result_MD_Folder/TC-076613.md) | BR1_TS12_TC13: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-075751.md](Result_MD_Folder/TC-075751.md) | BR1_TS2_TC53: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-075667.md](Result_MD_Folder/TC-075667.md) | BR1_TS1_TC77: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-076423.md](Result_MD_Folder/TC-076423.md) | BR1_TS7_TC103: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-077185.md](Result_MD_Folder/TC-077185.md) | BR1_TS31_TC1: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-077206.md](Result_MD_Folder/TC-077206.md) | BR1_TS1_TC1: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-077218.md](Result_MD_Folder/TC-077218.md) | BR1_TS29_TC1: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-076725.md](Result_MD_Folder/TC-076725.md) | BR1_TS19_TC8: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-076723.md](Result_MD_Folder/TC-076723.md) | BR1_TS19_TC6: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-075601.md](Result_MD_Folder/TC-075601.md) | BR1_TS1_TC11: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-075810.md](Result_MD_Folder/TC-075810.md) | BR1_TS3_TC12: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-075855.md](Result_MD_Folder/TC-075855.md) | BR1_TS3_TC57: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-076597.md](Result_MD_Folder/TC-076597.md) | BR1_TS11_TC16: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-076780.md](Result_MD_Folder/TC-076780.md) | BR1_TS21_TC10: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-077179.md](Result_MD_Folder/TC-077179.md) | BR1_TS29_TC4: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Result_MD_Folder/TC-077203.md](Result_MD_Folder/TC-077203.md) | BR1_TS147_TC3: every segment and field of the test data against ATL105, how the data was generated, what is missing, the ATL105 text |
| [Manual Validation/TC-076120-Manual-Validation.md](Manual%20Validation/TC-076120-Manual-Validation.md) | BR1_TS5_TC1: step-by-step manual validation guide with the ATL105 extracts |
| [Manual Validation/TC-076166-Manual-Validation.md](Manual%20Validation/TC-076166-Manual-Validation.md) | BR1_TS5_TC47: step-by-step manual validation guide with the ATL105 extracts |
| [Manual Validation/TC-076167-Manual-Validation.md](Manual%20Validation/TC-076167-Manual-Validation.md) | BR1_TS5_TC48: step-by-step manual validation guide with the ATL105 extracts |
| [Manual Validation/TC-076168-Manual-Validation.md](Manual%20Validation/TC-076168-Manual-Validation.md) | BR1_TS5_TC49: step-by-step manual validation guide with the ATL105 extracts |
| [Manual Validation/TC-076658-Manual-Validation.md](Manual%20Validation/TC-076658-Manual-Validation.md) | BR1_TS15_TC7: step-by-step manual validation guide with the ATL105 extracts |

The full sheet was produced with (from `core-auth-ai-validation-framework/regression-artifact-validator`): `python scripts/validate-run-and-build-manual-validation.py --run "C:\Users\F7OKAGW\OneDrive - Fiserv Corp\Documents\Core_Auth_Updated_BRs\core-auth-ai-validation-framework\regression-artifact-validator\Dhan_Singh_run_set_20-11" --package "C:\Users\F7OKAGW\OneDrive - Fiserv Corp\Documents\Core_Auth_Updated_BRs\core-auth-ai-validation-framework\regression-artifact-validator\Dhan_Singh_run_set_20-11_output" --only TC-076120,TC-076166,TC-076167,TC-076168,TC-076658,TC-075618,TC-075654,TC-075699,TC-075657,TC-076586,TC-075650,TC-075755,TC-075656,TC-075757,TC-075685,TC-075670,TC-077181,TC-077186,TC-077193,TC-077219,TC-076596,TC-076613,TC-075751,TC-075667,TC-076423,TC-077185,TC-077206,TC-077218,TC-076725,TC-076723,TC-075601,TC-075810,TC-075855,TC-076597,TC-076780,TC-077179,TC-077203 --guides TC-076120,TC-076166,TC-076167,TC-076168,TC-076658 --sheet MasterCard_Credit_Defect_Demo_Sheet.md --title "MasterCard Credit run set 20-11: defect demo sheet for the BA review" --scope "This sheet repeats the BA analysis of the 16-11 run on the new run set `Dhan_Singh_run_set_20-11` (main MasterCard Credit run, built 19 Nov 2026). It shows what happened to every defect the BA logged for the 16-11 run, every defect the validator finds in the new run with all affected test cases, and the actual and expected test data of 37 representative test cases. Every finding was produced by the ATL105 validator from the test case definitions and their test data files, and every ATL105 text quoted here was found word for word on its PDF page." --groups 20-11 --intro C:\Users\F7OKAGW\AppData\Local\Temp\atl105_16nov\intro_main.md --outro C:\Users\F7OKAGW\AppData\Local\Temp\atl105_16nov\outro_main.md`. The validator ran on all 1,623 test cases of the run; this sheet covers the test cases above.

<a id="how-filtered"></a>

### How this filtered sheet was produced

- Source: the full sheet [MasterCard_Credit_Defect_Demo_Sheet.md](MasterCard_Credit_Defect_Demo_Sheet.md) and [Defects_in_the_run.csv](Defects_in_the_run.csv) (6,370 findings of the run). The kept content is copied from the full sheet; the only changes are listed here.
- Filtered out (finding ids): I-01 to I-09 (placeholders, run-time and client values, generated or synthesized values), C-03 ('ready' although the Terminal Identifier is a placeholder), C-07 (generated 'different terminal'); D-09 findings about the Terminal Identifier (rule `FIELD_102`) or about a follow-on Account Number awaited from the client (`LINK_ACCOUNTNUMBER`); the D-15 finding measured on an `<AWAITING:...>` value (`X_BOUNDARY_NOT_AT_LIMIT`).
- Kept: every other finding - D-01, D-09 (a second rule broken), D-11, D-13, D-15 (invalid value), D-16, C-02, C-04, C-05, C-06, C-08, C-09, C-10, C-12, S-01 to S-06, Q-01 to Q-04 - and the notes that are not defects (N-01, V-01, INFO).
- Expected test data: a `//` comment keeps only the ids of kept defects; a line changed only for a filtered-out finding is shown as delivered (for example the placeholder Terminal Identifier and `<ApprovalNumber from step 1 response>`). Where a kept and a filtered-out finding share a line, the kept part stays: `D-13/I-01` reads `D-13` (the Terminal Identifier length is kept in the formula), and the Segment 153 token data keeps the delivered token with table 004 = `N`, as S-04 proposes.
- Corrected references: in the full sheet five evidence references and their test data comments pointed to line 7 (Segment 100 Segment Length) instead of the segment they describe - `TD-TC-075650.json` Loyalty Card Data Segment Segment Length: line 29 instead of line 7; `TD-TC-075751.json` Loyalty Card Data Segment Segment Length: line 29 instead of line 7; `TD-TC-075855.json` Loyalty Card Data Segment Segment Length: line 29 instead of line 7; `TD-TC-077186.json` EMV Request Data Segment Segment Length: line 50 instead of line 7; `TD-TC-077219.json` Variable Information Data Segment Segment Length: line 29 instead of line 7. In `TD-TC-077186.json` the expected Segment 130 length is the `0201` the D-09 finding states.
- Defects renumbered 1-22; the 'Full sheet #' column of [Defects in this sheet](#defects-in-this-sheet) gives the number in the full sheet.
