# Segment 105 Canonical Source Anchors

Verified against the ATL105 2026-3 Totals Request template extracted from specification pages 227-229.

## Anchor Format

```text
ATL105 | version | section | segment | element | rule
```

Use these values consistently in business requirements, scenarios, test cases, and test data.

| Canonical rule | Section | Segment | Element | JSON field | Status |
| --- | --- | --- | --- | --- | --- |
| `segment-type` | `Totals Request` | `105` | `85` | `segmentType` | CONFIRMED |
| `segment-length` | `Totals Request` | `105` | `84` | `segmentLength` | CONFIRMED |
| `information-byte` | `Totals Request` | `105` | `44` | `informationByte` | CONFIRMED |
| `terminal-identifier` | `Totals Request` | `105` | `102` | `terminalIdentifier` | CONFIRMED |
| `prompt-code` | `Totals Request` | `105` | `78` | `promptCode` | CONFIRMED |
| `employee-number` | `Totals Request` | `105` | `32` | `employeeNumber` | REVIEW_REQUIRED |
| `password` | `Totals Request` | `105` | `65` | `password` | REVIEW_REQUIRED |
| `totals-date` | `Totals Request` | `105` | `105` | `totalsDate` | CONFIRMED |
| `hardware-version` | `Totals Request` | `105` | `43` | `hardwareVersion` | CONFIRMED |
| `software-version` | `Totals Request` | `105` | `96` | `softwareVersion` | CONFIRMED |
| `firmware-version` | `Totals Request` | `105` | `39` | `firmwareVersion` | CONFIRMED |
| `sequence-number` | `Totals Request` | `105` | `86` | `sequenceNumber` | CONFIRMED |
| `currency-code` | `Totals Request` | `105` | `20` | `currencyCode` | CONFIRMED |
| `grand-total` | `Totals Request` | `105` | `42` | `grandTotal` | CONFIRMED |
| `card-label` | `Totals Request` | `105` | `13` | `cardLabel` | CONFIRMED |
| `card-type-total-count` | `Totals Request` | `105` | `16` | `cardTypeTotalCount` | CONFIRMED |
| `card-type-total-amount` | `Totals Request` | `105` | `15` | `cardTypeTotalAmount` | CONFIRMED |
| `request-response-correlation` | `Totals Request` | `105` | `86` | `sequenceNumber` | REVIEW_REQUIRED |
| `totals-load-alternative` | `Totals Request` | `119` | `` | `segments` | REVIEW_REQUIRED |

## Fixed Values

- Segment Type, Element 85: `105`.
- Prompt Code, Element 78: `990`.

## Totals Date, Element 105

| Request value | Response value | Source-defined meaning |
| --- | --- | --- |
| `MMDDYY` | `YYMMDD` | Totals for the specified settlement date. |
| `111111` | `111111` | Totals since the last `999999` request. |
| `222222` | `YYMMDD` | Current settlement-date totals; ends and rolls the settlement date. |
| `333333` | `YYMMDD` | Most recent date with activity. |
| `444444` | `YYMMDD` | Second most recent date with activity. |
| `555555` | `YYMMDD` | Third most recent date with activity. |
| `999999` | `999999` | Clear totals since the last `999999`; used with `111111`. |

The source states that requests cannot target dates earlier than the third most recent date with activity. The actual dates with activity and settlement calendar remain an external-system dependency.