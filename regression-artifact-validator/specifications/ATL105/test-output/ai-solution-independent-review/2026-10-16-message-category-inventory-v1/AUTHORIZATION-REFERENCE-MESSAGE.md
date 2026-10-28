# Reference Message Category: Authorization.json

This reference transaction is categorized separately from the AI/Test catalog rows in `artifact-message-category-register.csv`.

| Dimension | Category | Evidence / basis |
|---|---|---|
| Message family | ATL105 Financial Request | Request envelope `MessageType=ATL105` |
| Direction | Request | Request envelope and Segment 100 request fields |
| Transaction intent | POS Authorization Only | Prompt Code transaction type `3`; mapping confirmed by the user |
| Payment card category | Credit card | Prompt Code card type `020`; mapping confirmed by the user |
| Entry/capability | ICC / EMV capable | Table 005 value `051` indicates ICC entry mode `05` and PIN capability `1`; Table 031 value `05` indicates ICC read capability; Segment 130 is present |
| POS context | Attended, customer-operated, on-premise POS; customer/card present; original presentment; no security concern | Table 030 value `0000000001`, interpreted by Appendix Q positions |
| Segment scope | 100 Standard, 111 Variable Information, 130 EMV | Three segment objects and `NumSegments=3` |
| Partial approval | Merchant capability indicated | Element 121 value `1`; does not predict whether the host grants a partial approval |

**Validation note:** Terminal ID `HC375003` is only 8 characters. ATL105 requires a provisioned composite ID: 2-character device type, 2-character ANSI state code, 6-15-character BUYPASS merchant number, and 3-character device number. The supplied value fails that component layout and should be replaced by the assigned complete identifier. Do not pad or synthesize one.

**Privacy:** Raw track data and EMV chip data are intentionally excluded from this category note and from the corpus-wide category register.