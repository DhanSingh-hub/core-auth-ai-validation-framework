# ATL105 Field-Level Test Data Validation - TC-001794

| Item | Value |
|---|---|
| Test case | TC-001794 (BR1_TS2_TC36) |
| Name | BUYPASS ATL105 MasterCard Credit MERCHANT 01 (POS PLACEHOLDER) Purchase/Capture – EMV Contactless Chip Store and Forward blocking window (card type 173) → no off-line approval inside the window |
| Scenario | TS-ATL105-CREDIT-MASTERCARD-PURCHASE-EMV_CONTACTLESS |
| Template / type | T11-SAF-BLOCKED / Device-only |
| Objective | Store and Forward blocking window (card type 173) -> no off-line approval inside the window |
| Transaction type |  |
| TC status | requires_review (catalog: sme_required) |
| Open items | ["Terminal table load with card type 173 and a DL6 window (client).","What the device does with a blocked transaction is not stated (SME_QUESTIONS_FLOWS FQ-03)."] |
| Test data file(s) |  |
| Specification | BUYPASS ATL105 Message Format Specifications 2026-3 (Ch. 11.8.1, 12.1, 12.10, 12.20, Ch. 13, App. D/E/G/I/J/R/T) |
| Validator | regression-artifact-validator TestDataSpecFieldValidationRunner + AppendixR/T/I/J oracles |
| Run | 2026-11-02T17:54:50.1008283 |
| Overall verdict | **NOT_APPLICABLE** |
| Counts | PASS 0 / FAIL 0 / REVIEW 0 / N/A 1 |

## Defects (FAIL)

None.

## Review items

None.

## Test objective and flow checks

| Area | Field | Element | Value | Rule | Verdict | Detail | Spec source |
|---|---|---|---|---|---|---|---|
| Traceability | Test data file | - | `none` | TD-PRESENT | **NA** | Device-configuration scenario with no host message (tx_legs empty); no ATL105 message to validate. Objective: Store and Forward blocking window (card type 173) -> no off-line approval inside the window; device checkpoints: [] | TC tx_legs |

---
Verdicts: PASS = conforms to ATL105; FAIL = violates ATL105 or the TC's own objective/contract; REVIEW = not assertable from the spec/snapshot alone (placeholder, BUYPASS-assigned or runtime value); N/A = conditional field correctly not populated / informational.
