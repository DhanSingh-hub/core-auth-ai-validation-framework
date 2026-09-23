# Prompt Code: SME/TBA Learning Note

## Source-Confirmed Mapping

Unlike Segment 100, Segment 109 Prompt Code is an Electronic Mail operation selector, not a financial card/transaction composite.

| Operation | Prompt Code | Source |
|---|---|---|
| Retrieve electronic mail | `981` | Section 10.11.1 |
| Retrieve proprietary-card data | `996` | Section 10.11.1 |
| Submit electronic mail | `995` | Section 10.11.2 |

## TBA Decomposition

```text
BR: A submission Electronic Mail Request shall use Prompt Code 995.
TS: Device sends one submission block.
TC: Declare submission while sending Prompt Code 981.
TD: Prompt Code 981 with operation SUBMISSION; expected FAIL.
```

## Review Boundary

The source does not establish whether these are the complete locally permitted codes, which client/device populations may use them, or Information Byte/Block Number combinations. Those decisions remain `SEG109-SME-002` through `SEG109-SME-004`.