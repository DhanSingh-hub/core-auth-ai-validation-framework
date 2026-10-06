# Segment DL3 Conditional Fields and Cross-Field Dependencies: SME/TBA Learning Note

**Segment:** DL3 — Date and Time Data Segment · **Sources:** 12.44, 13.2 (Elements 21, 23, 25), Appendix E (Card Type 164) · **Oracle:** [rule catalog](coverage/segment-DL3-rule-catalog.json) · **Benchmark:** Segment 100 [partial-approval note](../segment-100/partial-approval-sme-tba-note.md)

## Core Idea

DL3 has no Conditional fields; its dependencies are between values and with device behaviour.

| Dependency | Trigger | Consequence | Rule / status |
|---|---|---|---|
| Day ↔ Date | Current Date | Day of the Week should be that date's weekday | Derived, `REVIEW_REQUIRED` |
| Cut Time → settlement | Automatic cut time, not yet settled | Device starts settlement 30 minutes before Cut Time | `SEGDL3-R-009` |
| DL1 `164` ↔ Cut Time | Auto Close enabled in DL1 | Batch closes 30 minutes before the default cut time | Appendix E; cross-segment, `REVIEW_REQUIRED` |
| Password → Totals | Password value | Must match the merchant profile when the device requests totals | Element 65; `SEGDL3-SME-002` |
| Time zone → Current Time | Device location | Current Time adjusted for time zone and DST | `SEGDL3-R-005`, `R-008` |

## SME Questions

1. Are "automatic cut time" (Element 23) and Card Type `164` Auto Close the same feature?
2. Is "default cut time" (Appendix E 164) the DL3 Cut Time?
3. Should a Day/Date mismatch be rejected?

## TBA Decomposition

```text
BR:  With automatic cut time, settlement shall start 30 minutes before the DL3 Cut Time (SEGDL3-R-009).
TS:  Merchant Cut Time 2300, automatic cut time, clerk has not settled.
TC:  Observe the Totals Request time. Expected: 2230 (review-severity rule).
TD:  DL3 with cutTime "2300" plus lifecycle context {autoCutTime: true, settledByClerk: false}.
```
