# Mastercard — BR → Test Scenario → Test Case → Test Data (JSON)

Source: AI Solution run of **18-Oct-2026** (`181026 Run`), ATL105 specification **2026-3**.
Scope: **Mastercard only**.

## What is in this pack

| | Count |
|---|---:|
| Business Requirements (BR) | **110** — 95 Mastercard-specific BRs + 15 lifecycle BRs exercised with a Mastercard card |
| Test Scenarios (TS) | **231** |
| Test Cases (TC) | **367** — 136 multi-leg (transaction flows) + 231 single-leg |
| Single-leg JSONs by transaction type | **540** |

## Folder map

```
MASTERCARD_INDEX.xlsx              <- start here (filterable index of everything below)
1_BR_to_TS_to_TC/
   REQ-<n>/BR.json                 <- Business Requirement
      SC-<n>/scenario.json         <- Test Scenario for that BR
         TC-<n>/testcase.json      <- Test Case for that scenario
         TC-<n>/TD-TC-<n>*.json    <- Test data: one JSON per transaction (leg)
2_By_Transaction_Type/
   single-leg/01_Sale, 02_Authorization, 03_Incremental_Authorization, 04_Completion,
              05_Void_Purchase_Reversal, 06_Cancellation, 07_Return_Refund,
              08_Void_of_Return, 09_Timeout_Reversal, 10_Other_*
   multi-leg/FLOW-<name>/TC-<n>/   <- complete flow: testcase.json + step1, step2, (step3) JSONs
```

`REQ-<n>` is the short form of the BR ID `REQ-SRC-ATL105-PDF-001:<n>`.
A test case linked to more than one BR appears under each of those BRs.

## Single-leg vs multi-leg

- **Multi-leg** test cases are complete flows such as Sale → Void, Authorization → Cancellation, Authorization → Completion, Return → Void of Return, and Transaction → Timeout Reversal (standard and EMV).
- **Single-leg** JSONs under `2_By_Transaction_Type/single-leg` are either:
  - individual steps cut from a multi-leg flow (file name contains `.stepN.<role>`), or
  - native single-leg test cases generated for a Mastercard-specific BR (file name `TD-TC-<n>.json`).
  The `By_Transaction_Type` sheet in the index shows the origin of each file.

## How Mastercard cases were identified

- **Flows:** every leg uses a Mastercard card number (BIN 51–55 / 2221–2720).
- **Single-leg:** the test case is linked to a BR whose text is specific to Mastercard (for example MIT/CIT, UCAF, Mastercard Authentication Data, Preauthorization/Final Authorization presentment, Purchase Repayment).

## Notes

- Field values (card number, approval number, amounts, terminal ID, dates) are generated sample values and are to be configured per test environment. Test cases whose card number is not yet a Mastercard number are marked in the index (`Card number is Mastercard = No`).
- Only test cases that passed the AI run's own verification are included. The `Excluded` sheet lists the rest with reasons. This includes Authorization → Void (code `8`) flows, which are invalid per ATL105 §10.1.2.2/10.2.2.2 because an authorization-only reversal uses Cancellation (`S`).
- The `BR_Register` sheet lists all 158 BRs that mention Mastercard and whether each has test cases in this pack.
