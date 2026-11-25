# Defect fix verification – previous AI run (31-10-out) vs new AI run (5-11-out)

**Start here:** [Consolidated_Report_All_Programs.md](./Consolidated_Report_All_Programs.md) – one report for all five programs: what is fixed, what is still open, what waits for client input, the Maestro Direct Debit defects that the previous logs missed, and the full register.

## Result by program

| Program | Defects re-checked | FIXED | FIXED – awaiting client input | NOT FIXED | CLOSED – test cases removed | Report |
|---|---|---|---|---|---|---|
| American Express Credit | 17 | 15 | 2 | 0 | 0 | [AMEX_Credit_Fix_Verification.md](./AMEX_Credit_Fix_Verification.md) |
| MasterCard Credit | 20 | 16 | 4 | 0 | 0 | [MasterCard_Credit_Fix_Verification.md](./MasterCard_Credit_Fix_Verification.md) |
| MasterCard Fleet | 11 | 9 | 2 | 0 | 0 | [MasterCard_Fleet_Fix_Verification.md](./MasterCard_Fleet_Fix_Verification.md) |
| Interlink Debit | 14 | 9 | 3 | **1** | 1 | [Interlink_Debit_Fix_Verification.md](./Interlink_Debit_Fix_Verification.md) |
| Maestro Direct Debit (missed in the previous logs) | 14 | 9 | 3 | **1** | 1 | [Maestro_Direct_Debit_Fix_Verification.md](./Maestro_Direct_Debit_Fix_Verification.md) |

## Files

- [Consolidated_Report_All_Programs.md](./Consolidated_Report_All_Programs.md) – consolidated report, all programs.
- `<Program>_Fix_Verification.md` – per program: every defect with its previous and new counts, what the new files contain, a worked example (previous file and line → new file and line) and every previously failing file.
- `Defect_Fix_Verification.xlsx` – *Register*, *Rows* (every previously failing file → new file and line), *Still failing (new run)*, *Package*, *TC mapping* (previous TC ID → new TC ID).
- [Maestro Direct Debit validation of the previous run](../maestro_direct_debit/Manual%20Validation%20Report/README.md) – the full ATL105 report (per scenario and per test case) that found the Maestro defects; its defect list: [Consolidated_Defect_Report.md](../maestro_direct_debit/Manual%20Validation%20Report/Consolidated_Defect_Report.md).

Re-run (from `regression-artifact-validator`):

```powershell
$env:PYTHONUTF8 = '1'
python scripts/verify-defect-fixes.py
```

