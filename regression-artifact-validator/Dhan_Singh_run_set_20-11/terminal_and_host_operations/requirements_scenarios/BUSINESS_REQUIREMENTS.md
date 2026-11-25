# Business Requirements - buypass-atl105-mastercard-credit-generated

Selection: **platform=BUYPASS, spec=ATL105, network=MASTERCARD, program=CREDIT**. A Business Requirement (BR) is one Platform x Spec x Network x Card Program inside the selection; every spec-evaluated original transaction category x entry mode under it is a Scenario. Follow-on categories are flow legs.

- Dimension mapping status: **candidate** (not SME-approved; built with --allow-unapproved)
- Mapping built from the current knowledge base: **True**
- Validation: **passed**
- Built: 2026-11-19T16:33:43.847706+00:00

| BR | Name | Spec | Scenarios | valid | valid (conditional) | requires SME | multi-leg | with gaps | excluded | supporting rules |
|---|---|---|---|---|---|---|---|---|---|---|
| BROP | BUYPASS ATL105 Terminal & Host Operations | ATL105 2026-4 | 9 | 9 | 0 | 0 | 0 | 0 | 0 | 0 |

## BROP. BUYPASS ATL105 Terminal & Host Operations

`BR-A0098BFB` - status **built**

Programs: All 9. Transaction categories: 9. Entry modes: 1. Scenarios with a pre-authorized variant: 0.

### Scenarios

| ID | Scenario | Status | SME | Kind | Txn type | Entry mode | Card type | Message | Rules | Gaps |
|---|---|---|---|---|---|---|---|---|---|---|
| BROP_TS1 | BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BROP_TS2 | BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BROP_TS3 | BUYPASS ATL105 Terminal & Host Operations Totals with Proprietary Data Load Request (Prompt Code 990) | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BROP_TS4 | BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BROP_TS5 | BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BROP_TS6 | BUYPASS ATL105 Terminal & Host Operations TransArmor Key Load Request | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BROP_TS7 | BUYPASS ATL105 Terminal & Host Operations CA Public Key File Load Request | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BROP_TS8 | BUYPASS ATL105 Terminal & Host Operations Communications Test (Transaction Type 900) | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BROP_TS9 | BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |

### Spec citations behind these scenarios

- SRC-ATL105-PDF-001 p.77 (9.3 Host Totals): "The device must be able to support all requests for merchant shift totals and settlement totals."
- SRC-ATL105-PDF-001 p.522 (Appendix E): "The following table contains a list of valid Card Type codes used to create valid 3-character Prompt Codes for special transactions."

