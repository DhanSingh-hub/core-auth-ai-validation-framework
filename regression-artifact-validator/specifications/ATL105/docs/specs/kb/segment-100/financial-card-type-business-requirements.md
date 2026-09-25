# ATL105 Segment 100 Financial Card-Type Business Requirements

## Scope and provenance

This catalog defines the card-type requirements for Financial Transaction Request Prompt Codes in ATL105 Segment 100. The 36 card codes and labels are anchored to the independent `Segment100CardTypeOracle` and the Appendix E card-type catalog.

These are requirements for the card-type dimension of the request. They do not replace transaction-flow, account-entry, EMV, fleet, EBT, loyalty, check, receipt, or message-format requirements. A request is business-valid only when the selected card type, transaction type, data elements, and conditional segments agree.

The supplied JSON files under `src/test/resources` are validation fixtures and are not the source of these requirements.

## Common requirements

| ID | Requirement | Acceptance criteria |
|---|---|---|
| CARD-100-001 | A Financial Transaction Request shall contain a Standard Segment (100) when a financial Prompt Code is sent. | `Financial Request.Standard Segment.SegmentType` is `100`. |
| CARD-100-002 | The Prompt Code shall combine one valid transaction-type code with one valid three-digit card-type code. | `PromptCode.TransactionType` is one of `0, 3, 4, 5, 6, 7, 8, A, B, C, S, U, Z`; `PromptCode.CardType` is one of the 36 codes in this catalog. |
| CARD-100-003 | The card-type code shall be preserved as a three-character value, including leading zeroes. | `020` is valid; `20` is invalid. |
| CARD-100-004 | The remaining request elements shall be populated according to the selected transaction flow and card category. | Amounts, account data, PIN data, variable information, EMV data, fleet data, EBT data, and response-dependent fields are validated by their applicable ATL105 rules. |
| CARD-100-005 | A card-type code shall not be treated as evidence that a conditional segment is required unless the applicable ATL105 rule establishes that condition. | The card code alone does not promote an EMV, fleet, EBT, loyalty, check, or purchase-card rule. |
| CARD-100-006 | A card-type/transaction-type combination shall be considered syntactically accepted only after the card code and transaction code pass the allowlist. | Unknown card codes and unknown transaction codes are rejected; business applicability is evaluated separately. |

## Card-type requirements

Each row is an independently testable allowlist requirement. The category is descriptive and is not, by itself, a claim that every transaction type is supported for that card.

| ID | Card Type | Category | Requirement |
|---|---:|---|---|
| CARD-100-001-001 | `001` | BUYPASS Fleet | A financial Prompt Code may identify BUYPASS Fleet with card type `001` when the applicable fleet and transaction-flow rules are satisfied. |
| CARD-100-001-011 | `011` | Debit Checking | A financial Prompt Code may identify Debit Checking with card type `011` when the applicable debit and transaction-flow rules are satisfied. |
| CARD-100-001-012 | `012` | Debit Saving | A financial Prompt Code may identify Debit Saving with card type `012` when the applicable debit and transaction-flow rules are satisfied. |
| CARD-100-001-013 | `013` | Debit ACH | A financial Prompt Code may identify Debit ACH with card type `013` when the applicable debit and transaction-flow rules are satisfied. |
| CARD-100-001-020 | `020` | Credit | A financial Prompt Code may identify Credit with card type `020` when the applicable credit, account-entry, and transaction-flow rules are satisfied. |
| CARD-100-001-040 | `040` | Loyalty | A financial Prompt Code may identify Loyalty with card type `040` when the applicable loyalty transaction rules are satisfied. |
| CARD-100-001-041 | `041` | Certegy / Telecredit | A financial Prompt Code may identify Certegy / Telecredit with card type `041` when the applicable check-processing rules are satisfied. |
| CARD-100-001-045 | `045` | Generic Check | A financial Prompt Code may identify Generic Check with card type `045` when the applicable check-processing rules are satisfied. |
| CARD-100-001-046 | `046` | ECA / TeleCheck | A financial Prompt Code may identify ECA / TeleCheck with card type `046` when the applicable check-processing rules are satisfied. |
| CARD-100-001-050 | `050` | Valero Energy | A financial Prompt Code may identify Valero Energy with card type `050` when the applicable proprietary-card rules are satisfied. |
| CARD-100-001-051 | `051` | Shell | A financial Prompt Code may identify Shell with card type `051` when the applicable proprietary-card rules are satisfied. |
| CARD-100-001-052 | `052` | ExxonMobil | A financial Prompt Code may identify ExxonMobil with card type `052` when the applicable proprietary-card rules are satisfied. |
| CARD-100-001-053 | `053` | ExxonMobil Fleet | A financial Prompt Code may identify ExxonMobil Fleet with card type `053` when the applicable fleet rules are satisfied. |
| CARD-100-001-055 | `055` | Valero UCC | A financial Prompt Code may identify Valero UCC with card type `055` when the applicable proprietary-card rules are satisfied. |
| CARD-100-001-056 | `056` | Generic Proprietary | A financial Prompt Code may identify Generic Proprietary with card type `056` when the applicable proprietary-card rules are satisfied. |
| CARD-100-001-057 | `057` | Sohio / Gulf | A financial Prompt Code may identify Sohio / Gulf with card type `057` when the applicable proprietary-card rules are satisfied. |
| CARD-100-001-058 | `058` | Cenex | A financial Prompt Code may identify Cenex with card type `058` when the applicable proprietary-card rules are satisfied. |
| CARD-100-001-059 | `059` | Wright Express | A financial Prompt Code may identify Wright Express with card type `059` when the applicable fleet rules are satisfied. |
| CARD-100-001-060 | `060` | Voyager | A financial Prompt Code may identify Voyager with card type `060` when the applicable fleet rules are satisfied. |
| CARD-100-001-061 | `061` | Car Care One | A financial Prompt Code may identify Car Care One with card type `061` when the applicable proprietary-card rules are satisfied. |
| CARD-100-001-064 | `064` | Tesoro Petroleum | A financial Prompt Code may identify Tesoro Petroleum with card type `064` when the applicable proprietary-card rules are satisfied. |
| CARD-100-001-070 | `070` | Valero Fleet | A financial Prompt Code may identify Valero Fleet with card type `070` when the applicable fleet rules are satisfied. |
| CARD-100-001-071 | `071` | Fleet One | A financial Prompt Code may identify Fleet One with card type `071` when the applicable fleet rules are satisfied. |
| CARD-100-001-072 | `072` | Tesoro UCC | A financial Prompt Code may identify Tesoro UCC with card type `072` when the applicable proprietary-card rules are satisfied. |
| CARD-100-001-073 | `073` | EBT Food Stamps | A financial Prompt Code may identify EBT Food Stamps with card type `073` when the applicable EBT rules are satisfied. |
| CARD-100-001-074 | `074` | EBT Cash Benefits | A financial Prompt Code may identify EBT Cash Benefits with card type `074` when the applicable EBT rules are satisfied. |
| CARD-100-001-075 | `075` | Citgo Fleet | A financial Prompt Code may identify Citgo Fleet with card type `075` when the applicable fleet rules are satisfied. |
| CARD-100-001-077 | `077` | Unocal | A financial Prompt Code may identify Unocal with card type `077` when the applicable proprietary-card rules are satisfied. |
| CARD-100-001-078 | `078` | Phone | A financial Prompt Code may identify Phone with card type `078` when the applicable stored-value and transaction-flow rules are satisfied. |
| CARD-100-001-079 | `079` | Stored Value | A financial Prompt Code may identify Stored Value with card type `079` when the applicable stored-value and transaction-flow rules are satisfied. |
| CARD-100-001-080 | `080` | MasterCard Fleet | A financial Prompt Code may identify MasterCard Fleet with card type `080` when the applicable fleet and card-brand rules are satisfied. |
| CARD-100-001-081 | `081` | Fuelman / Gascard | A financial Prompt Code may identify Fuelman / Gascard with card type `081` when the applicable fleet rules are satisfied. |
| CARD-100-001-083 | `083` | Cardlock Fuel System | A financial Prompt Code may identify Cardlock Fuel System with card type `083` when the applicable fleet and fuel rules are satisfied. |
| CARD-100-001-084 | `084` | Sinclair | A financial Prompt Code may identify Sinclair with card type `084` when the applicable proprietary-card rules are satisfied. |
| CARD-100-001-085 | `085` | Sunoco | A financial Prompt Code may identify Sunoco with card type `085` when the applicable proprietary-card rules are satisfied. |
| CARD-100-001-090 | `090` | Conoco | A financial Prompt Code may identify Conoco with card type `090` when the applicable proprietary-card rules are satisfied. |

## Required negative coverage

| ID | Requirement | Expected result |
|---|---|---|
| CARD-100-NEG-001 | Reject a card type that is not in the 36-code allowlist. | Validation error: unsupported financial card type. |
| CARD-100-NEG-002 | Reject a card type that is not exactly three numeric characters. | Validation error: card type must be exactly three digits. |
| CARD-100-NEG-003 | Reject a financial Prompt Code with an unsupported transaction-type code. | Validation error: unsupported financial transaction type. |
| CARD-100-NEG-004 | Do not infer EMV, fleet, EBT, loyalty, check, or stored-value behavior from a generic card-type code alone. | Requirement remains conditional until the applicable ATL105 rule and request evidence are present. |

## Implementation traceability

- Card-code allowlist: `Segment100CardTypeOracle.EXPECTED_CARD_TYPES`
- Transaction-code allowlist: `Segment100CardTypeOracle.VALID_TRANSACTION_TYPES`
- Positive and negative coverage: `Segment100CardTypeOracleTest`
- Source card-type catalog: `docs/specs/kb/appendix-code-tables.md`, Appendix E
- This catalog does not use the user-supplied JSON fixtures as business-requirement evidence.
