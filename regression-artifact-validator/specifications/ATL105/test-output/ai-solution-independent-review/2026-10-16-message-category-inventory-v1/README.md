# ATL105 AI and Test Message-Category Inventory

This inventory adds message family/direction and POS/card-code fields while keeping them separate from requirement type and segment scope.
Missing Test-side transaction-family metadata is explicitly `NOT_DECLARED`; it is not inferred from a BR title or segment alone.

Rows: **91,213**.

## Rows by artifact type

| Artifact set and kind | Rows |
|---|---:|
| AI:AI_BR | 6,880 |
| AI:AI_TC | 21,254 |
| AI:AI_TD_METADATA | 21,711 |
| AI:AI_TD_PAYLOAD | 21,711 |
| AI:AI_TS | 12,254 |
| TEST_SOLUTION:TEST_BR | 1,330 |
| TEST_SOLUTION:TEST_TC | 2,047 |
| TEST_SOLUTION:TEST_TD | 2,061 |
| TEST_SOLUTION:TEST_TS | 1,965 |

## Category status

| Status | Rows |
|---|---:|
| EXPLICIT_OR_CHAIN_DERIVED | 78,467 |
| MESSAGE_CATEGORY_NOT_DECLARED | 434 |
| SCOPE_OR_DOMAIN_ONLY_NO_MESSAGE_FAMILY | 5,343 |
| SEGMENT_OR_RULE_SCOPE_ONLY_MESSAGE_FAMILY_NOT_DECLARED | 6,969 |

## Classification rules

- `messageFamily` retains the exact source transaction-family string; linked family values are identified by `categoryBasis`.
- `messageDirection` is parsed from explicit request/response wording only.
- `posTransactionCategory` maps only transaction code `3` to `POS_AUTHORIZATION_ONLY`; other codes remain unmapped raw codes.
- `cardCategory` maps only card type `020` to `CREDIT_CARD`; other codes remain unmapped.
- `segmentScope` records explicit anchors, metadata/payload observations, or test-package scopes. `segmentEvidence` distinguishes them.
- `businessCategory`, `requirementType`, and `scenarioType` are not substituted for message family.
- This is categorization metadata, not semantic adjudication or approval.

See `artifact-message-category-register.csv` for the complete AI BR/TS/TC/TD and Test BR/TS/TC/TD inventory.
