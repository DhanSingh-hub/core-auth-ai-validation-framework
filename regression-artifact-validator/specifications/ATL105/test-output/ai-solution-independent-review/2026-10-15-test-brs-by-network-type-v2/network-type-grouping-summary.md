# Independent Test BRs by Network Type

Groups use only explicit `networkType` fields in the independent Test Solution package. Unassigned BRs are not inferred to be network-specific.

- Test BRs in package: 1,331
- BRs with explicit Section 5 leaf type: 16

| Network group | Test BRs |
|---|---:|
| ACH_PREAUTHORIZED_DEBIT | 1 |
| ATM_PINLESS_DEBIT | 1 |
| ATM_PIN_DEBIT | 1 |
| CHECK_GUARANTEE | 1 |
| CHECK_VERIFICATION_BUYPASS | 1 |
| CHECK_VERIFICATION_EXTERNAL | 1 |
| CREDIT_NONPROPRIETARY | 1 |
| CREDIT_PROPRIETARY | 1 |
| EBT_CASH | 1 |
| EBT_EWIC | 1 |
| EBT_FOOD | 1 |
| FLEET | 1 |
| LOYALTY | 1 |
| NETWORK_CROSS_CONTEXT_REVIEW_REQUIRED | 1 |
| NETWORK_TAXONOMY_GENERAL | 1 |
| NO_NETWORK_TYPE_DECLARED | 1,313 |
| PETROLEUM_PROPRIETARY | 1 |
| PROPRIETARY_CARD | 1 |
| STORED_VALUE | 1 |

An explicit Section 5 `networkType` is a training classification, not a wire field or a mapping to Visa/Mastercard/card-type codes. The cross-context rule remains review-required.
