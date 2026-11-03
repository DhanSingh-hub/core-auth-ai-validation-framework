# ATL105 Field-Level Test Data Validation - TC-001795

| Item | Value |
|---|---|
| Test case | TC-001795 (BR1_TS2_TC37) |
| Name | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Void/Reversal disabled (card type 167) → not offered at the device |
| Scenario | TS-ATL105-CREDIT-MASTERCARD-PURCHASE-EMV_CONTACTLESS |
| Template / type | T11-DISABLED-VOID / Device-only |
| Objective | Void/Reversal disabled (card type 167) -> not offered at the device |
| Transaction type |  |
| TC status | ready (catalog: needs_client_input) |
| Open items | ["Terminal table load with card type 167 (client)."] |
| Test data file(s) |  |
| Specification | BUYPASS ATL105 Message Format Specifications 2026-3 (Ch. 11.8.1, 12.1, 12.10, 12.20, Ch. 13, App. D/E/G/I/J/R/T) |
| Validator | regression-artifact-validator TestDataSpecFieldValidationRunner + AppendixR/T/I/J oracles |
| Run | 2026-11-02T17:54:50.1088278 |
| Overall verdict | **NOT_APPLICABLE** |
| Counts | PASS 0 / FAIL 0 / REVIEW 0 / N/A 1 |

## Defects (FAIL)

None.

## Review items

None.

## Test objective and flow checks

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| Traceability | Test data file | - | `none` | TD-PRESENT | **NA** | Device-configuration scenario with no host message (tx_legs empty); no ATL105 message to validate. Objective: Void/Reversal disabled (card type 167) -> not offered at the device; device checkpoints: [] | TC tx_legs |

---
Verdicts: PASS = conforms to ATL105; FAIL = violates ATL105 or the TC's own objective/contract; REVIEW = not assertable from the spec/snapshot alone (placeholder, BUYPASS-assigned or runtime value); N/A = conditional field correctly not populated / informational.
