# Business Requirements - buypass-atl105-mastercard-credit-generated

Selection: **platform=BUYPASS, spec=ATL105, network=MASTERCARD, program=CREDIT**. A Business Requirement (BR) is one Platform x Spec x Network x Card Program inside the selection; every spec-evaluated original transaction category x entry mode under it is a Scenario. Follow-on categories are flow legs.

- Dimension mapping status: **candidate** (not SME-approved; built with --allow-unapproved)
- Mapping built from the current knowledge base: **True**
- Validation: **passed**
- Built: 2026-11-13T17:49:58.028944+00:00

| BR | Name | Spec | Scenarios | valid | valid (conditional) | requires SME | multi-leg | with gaps | excluded | supporting rules |
|---|---|---|---|---|---|---|---|---|---|---|
| BRDE | BUYPASS ATL105 Data Element Validation | ATL105 2026-4 | 35 | 35 | 0 | 0 | 0 | 0 | 0 | 0 |

## BRDE. BUYPASS ATL105 Data Element Validation

`BR-169EDE9D` - status **built**

Programs: All 35. Transaction categories: 35. Entry modes: 1. Scenarios with a pre-authorized variant: 0.

### Scenarios

| ID | Scenario | Status | SME | Kind | Txn type | Entry mode | Card type | Message | Rules | Gaps |
|---|---|---|---|---|---|---|---|---|---|---|
| BRDE_TS1 | BUYPASS ATL105 Data Element Validation Segment 100 – Standard Message Data Segment | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS2 | BUYPASS ATL105 Data Element Validation Segment 102 – Product Code Data Segment | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS3 | BUYPASS ATL105 Data Element Validation Segment 104 – Purchase Card Data Segment | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS4 | BUYPASS ATL105 Data Element Validation Segment 105 – Totals Data Segment | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS5 | BUYPASS ATL105 Data Element Validation Segment 108 – Loyalty Card Data Segment | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS6 | BUYPASS ATL105 Data Element Validation Segment 109 – Electronic Mail Data Segment | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS7 | BUYPASS ATL105 Data Element Validation Segment 111 – Variable Information Data Segment | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS8 | BUYPASS ATL105 Data Element Validation Segment 114 – SKU Data Segment | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS9 | BUYPASS ATL105 Data Element Validation Segment 116 – TransArmor Load Data Segment | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS10 | BUYPASS ATL105 Data Element Validation Segment 118 – Proprietary Data Load Segment | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS11 | BUYPASS ATL105 Data Element Validation Segment 119 – Totals with Proprietary Data Load Data Segment | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS12 | BUYPASS ATL105 Data Element Validation Segment 123 – NFC Payment Tokenization Data Segment | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS13 | BUYPASS ATL105 Data Element Validation Segment 130 – EMV Request Data Segment | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS14 | BUYPASS ATL105 Data Element Validation Segment 132 – CA Public Key File Data Segment | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS15 | BUYPASS ATL105 Data Element Validation Segment 153 – Network Token Data Request | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS16 | BUYPASS ATL105 Data Element Validation Communications Test Request – Message fields | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS17 | BUYPASS ATL105 Data Element Validation Date and Time Load Request – Message fields | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS18 | BUYPASS ATL105 Data Element Validation Moneris Key Load Request – Message fields | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS19 | BUYPASS ATL105 Data Element Validation Phone Load Request – Message fields | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS20 | BUYPASS ATL105 Data Element Validation Software Load Request – Message fields | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS21 | BUYPASS ATL105 Data Element Validation Table Load Request – Message fields | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS22 | BUYPASS ATL105 Data Element Validation Financial Transaction Request – Message structure | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS23 | BUYPASS ATL105 Data Element Validation EMV Financial Transaction Request – Message structure | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS24 | BUYPASS ATL105 Data Element Validation Totals Request – Message structure | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS25 | BUYPASS ATL105 Data Element Validation Electronic Mail Request – Message structure | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS26 | BUYPASS ATL105 Data Element Validation TransArmor Key Load Request – Message structure | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS27 | BUYPASS ATL105 Data Element Validation Proprietary Data Load Request – Message structure | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS28 | BUYPASS ATL105 Data Element Validation Totals with Proprietary Data Load Request – Message structure | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS29 | BUYPASS ATL105 Data Element Validation CA Public Key File Load Request – Message structure | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS30 | BUYPASS ATL105 Data Element Validation Communications Test Request – Message structure | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS31 | BUYPASS ATL105 Data Element Validation Table Load Request – Message structure | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS32 | BUYPASS ATL105 Data Element Validation Phone Load Request – Message structure | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS33 | BUYPASS ATL105 Data Element Validation Date and Time Load Request – Message structure | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS34 | BUYPASS ATL105 Data Element Validation Software Load Request – Message structure | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |
| BRDE_TS35 | BUYPASS ATL105 Data Element Validation Moneris Key Load Request – Message structure | valid |  | single-leg |  |  |  (gap) | per leg | 0 | 0 |

### Spec citations behind these scenarios

- SRC-ATL105-PDF-001 p.164 (11. Message Formats): "This chapter discusses the BUYPASS variable length message formats."
- SRC-ATL105-PDF-001 p.235 (12.6 Totals Data Segment): "Note: Field Nos. 1-14 are separated by Field Separators, with a Field Separator following Field No. 14."

