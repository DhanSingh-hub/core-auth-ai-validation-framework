# Response Code (Element 83) Training

**Specification:** BUYPASS Platform ATL105, Release 2026-3  
**Primary source:** Chapter 13, Element 83, pages 13-63 to 13-65  
**Machine-readable source-derived roster:** [Response-code family matrix](../../../../test-output/test-json/atl105-response-code-family-matrix.json)  
**Complete code/family BR validity matrix:** [Element 83 BR matrix](../element-83-response-code/coverage/element-83-response-code-br-validation-matrix.json)  
**Executable family oracle:** `Atl105ResponseCodeFamilyOracle`  
**Standard Financial validator:** `Segment100ResponseCodeValidator`

## Training Boundary

Element 83 is a one-character code whose meaning depends on the message family and, for some codes, the more specific transaction/load context. Never validate an Element 83 value using a global allowed-code set alone. Resolve the message family first, then validate the code within that family. If the family is missing or unknown, do not infer meaning from the code.

The source-derived matrix contains **31 unique code characters** across seven families:

All 217 code/family pairs are exercised by the response-code oracle test: 35 source-defined valid pairs and 182 invalid-for-selected-family pairs. These are message-family membership decisions, not a complete card-type or transaction-type cross-product.

| Message family | Codes |
|---|---|
| Financial transaction | `0`, `1`, `2`, `3`, `4`, `F`, `S` |
| Totals | `5`, `6`, `7`, `B`, `C`, `D`, `E`, `M`, `N`, `V`, `W` |
| Electronic Mail | `8`, `9`, `G`, `J` |
| Communications Test | `1` |
| Proprietary Data Load response | `H`, `O`, `T`, `U`, `X`, `Y` |
| TransArmor load | `K`, `L`, `P` |
| EMV CA key load | `L`, `M`, `X` |

This is a membership index, not a claim that every family has an implemented validator or an approved executable BR/TS/TC/TD chain.

## Context-Sensitive Codes

| Code | Source contexts | Training rule |
|---|---|---|
| `1` | Financial: decline for other transactions; Communications Test: approved | Require message family before interpreting it. |
| `L` | TransArmor: rejected load; EMV CA key load: approved final block | Do not infer outcome without load family. TransArmor details remain source-limited. |
| `M` | Totals: declined with proprietary Host Discount pending; EMV CA key load: approved more blocks pending | Distinguish a Totals response from an EMV key-load response. |
| `X` | Proprietary Data Load: decline and proceed to next pending Prompt Code; EMV CA key load: not required/rejected | Require family context; EMV error detail is in Element 194. |

A second, easy-to-miss distinction is between a **Totals response that triggers a load** and the subsequent **load response**:

- Totals codes `D`/`E` and `M`/`N` indicate proprietary or Host Discount data pending; they are not Proprietary Data Load response codes.
- Totals codes `V`/`W` indicate Custom Receipt Text (Prompt Code 901) pending; they are not Proprietary Data Load response codes.
- Proprietary Data Load response codes are `H`, `O`, `T`, `U`, `X`, and `Y`.

The Segment 118 validator therefore rejects `V` and `W` in a Proprietary Data Load Response while the family oracle classifies them under Totals.

## Code 0: Approved Purchase/Capture

The dedicated [Element 83 code-0 training module](../element-83-response-code/response-code-0-approved-purchase-capture.md) records the message-family and Segment 100/request-context crosswalk in structured form.

**Meaning:** Approved Purchase/Capture (Element 83). Appendix G identifies transaction type `0` as POS Purchase/Capture or preauthorized completion; transaction type `4` as Customer-activated Purchase/Capture; and transaction type `6` as Mail/Phone Purchase. Code `0` is accepted with those purchase contexts by the Segment 100 response validator. It is rejected with authorization-only (`3`, `5`) and return/reversal (`7`, `8`) contexts.

Element 5 Approval Number is conditional in the Section 11.1.2 response layout and is not universally required for every approved code-0 response. Segment 100 rule `SEG100-R-049` and the lifecycle note make it required only when the applicable lifecycle declares an approval reference necessary (for example, a reversal or a flow using a prior-authorization reference). A response test must not fail a plain code-0 purchase solely because Approval Number is absent.

Focused tests cover code `0` with transaction types `0`, `4`, and `6`; incompatible authorization/return types; and Approval Number conditionality. This lesson does not infer requirements for codes `4` or `F` beyond their own response-code training step.

## Executable Coverage

- `Atl105ResponseCodeFamilyOracle` checks family/code membership against the source-derived family map. Unknown or missing family context fails validation; overlapping meanings produce a warning scoped to the selected family.
- `Segment100ResponseCodeValidator` intentionally accepts only the seven standard Financial codes. It also checks transaction-type compatibility, Approval Number, Approved Amount, Decline Code, Sequence Number, and Authorizer Response Code.
- `CommunicationsTestPayloadValidator` checks the Communications Test fixed Response Code `1`.
- `Segment118PayloadValidator` checks the PDL response code subset `H/O/T/U/X/Y`.
- The Segment 100 response-code BR/TS/TC/TD package currently covers four financial outcome groups and marks its test data `REVIEW`; it is not evidence that the other response families are trained or approved.

## Approval and Gaps

The matrix is source-derived classification evidence. It does not resolve all family-specific lifecycle policy or replace message validators. Electronic Mail response semantics remain open under `SEG109-SME-007`; TransArmor layouts/processing require the external specification; Totals, EMV key-load, and Moneris response assertions need family-specific executable evidence before those families can be called trained. Do not promote the response-code package or any code-family result to `APPROVED` without reviewed BR decisions and validated TS/TC/TD evidence.
