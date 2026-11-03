# ATL105 MasterCard Credit – Additional Defect D-11

**Date:** 2026-11-03
**Specification:** BUYPASS ATL105 Message Format Specifications, Release 2026-4 (PDF page numbers)
**Found during:** preparation of the EMV defect log (`EMV_Defect_Log.md`). It was not included in `ATL105_MC_Credit_Consolidated_Defects.md`.
**Independent check:** a scan of every multi-leg EMV test case for a Sequence Number shared between a Totals Request and a financial request on the same terminal found exactly these 4 test cases.

## Register

| ID | Defect | Element / field | Test cases affected | Severity | AI team: Agree / Disagree | AI team: Fix plan / comment |
|---|---|---|---|---|---|---|
| D-11 | Return after settlement reuses the Totals Request's Sequence Number (the TD also differs from its own TC) | E86, Seg 100 | 4 | Medium | | |

## D-11 – Return after settlement reuses the Totals Request's Sequence Number

| | |
|---|---|
| Flow | Purchase → Settlement (Totals Request, Totals Date `222222`) → Merchandise Return |
| Test cases | TC-001754, TC-001755 (EMV contact), TC-001791, TC-001792 (EMV contactless) |
| Generated | The step 2 Totals Request and the step 3 Return carry the **same** Sequence Number on the same terminal `JX72948216050257001` |
| Expected | The Return is a new transaction and needs its own Sequence Number. The TC itself specifies the next number (method `flow_new_value_required`), so the TD also differs from its own TC. |

### Evidence

| TC | Entry mode | Step 2 Totals (file : line) | Step 3 Return (file : line) | TD value (both) | TC value for the Return |
|---|---|---|---|---|---|
| TC-001754 | Contact | `TD-TC-001754.step2.totals.json` : 17 | `TD-TC-001754.step3.return.json` : 22 | `017542` | `017543` |
| TC-001755 | Contact | `TD-TC-001755.step2.totals.json` : 17 | `TD-TC-001755.step3.return.json` : 22 | `017552` | `017553` |
| TC-001791 | Contactless | `TD-TC-001791.step2.totals.json` : 17 | `TD-TC-001791.step3.return.json` : 22 | `017912` | `017913` |
| TC-001792 | Contactless | `TD-TC-001792.step2.totals.json` : 17 | `TD-TC-001792.step3.return.json` : 22 | `017922` | `017923` |

### ATL105 reference

| Section / element | PDF page (2026-4) | Text |
|---|---|---|
| Element 86 Sequence Number | p.409 | *"Processing Rules: Each transaction has a unique Sequence Number. It is included on all transaction types, and originates at the device."* |
| Element 86 Sequence Number | p.409 | *"In the case of a purchase reversal/void or time-out reversal transaction, the Sequence Number must be the same as in the original transaction request."* The Return is not one of these follow-on types. |
| 2.3.1 Sequence Number | p.40 | *"Each time any financial transaction request is forwarded to BUYPASS, the financial transaction should be assigned a new Sequence Number"* |

### How to verify manually

1. Open `TD-TC-001754.step2.totals.json` (line 17) and `TD-TC-001754.step3.return.json` (line 22). Both have `"SequenceNumber": "017542"` and the same `TerminalIdentifier`.
2. Open `test_cases.jsonl`, find TC-001754, and go to `steps[2].request` → `Standard Message Data Segment` → `Sequence Number`. The value is `017543`, with method `flow_new_value_required` and a flag stating that each new financial transaction gets a new Sequence Number.
3. Read E86 on p.409: the only cases that reuse a Sequence Number are reversals/voids, time-out reversals, auth-only reversals and preauthorized completions. A Merchandise Return after settlement is none of these.

### Fix

- Emit the TC's Sequence Number in the TD (`017543`, `017553`, `017913`, `017923`).
- Allocate a new Sequence Number for every new request on a terminal (Totals Requests included), and reuse a number only for the follow-on types listed in E86.

### Reviewer verdict

| Log ID | TC | Verdict | Notes |
|---|---|---|---|
| D-11-001 | TC-001754 | ☐ Correct ☐ Incorrect | |
| D-11-002 | TC-001755 | ☐ Correct ☐ Incorrect | |
| D-11-003 | TC-001791 | ☐ Correct ☐ Incorrect | |
| D-11-004 | TC-001792 | ☐ Correct ☐ Incorrect | |
