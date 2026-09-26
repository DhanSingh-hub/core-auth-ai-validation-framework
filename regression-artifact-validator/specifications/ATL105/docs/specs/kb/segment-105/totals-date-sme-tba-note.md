# Segment 105 Totals Date: SME and TBA Learning Note

Element 105, Totals Date, is a six-byte numeric request selector. The source supports an explicit `MMDDYY` date plus operation codes `111111`, `222222`, `333333`, `444444`, `555555`, and `999999`.

| Request | Meaning |
| --- | --- |
| `MMDDYY` | Request totals for the stated settlement date. |
| `111111` | Request totals since the last `999999`. |
| `222222` | Request current-date totals, end the date, and roll to the next settlement date. |
| `333333` to `555555` | Request the first through third most recent active dates. |
| `999999` | Clear totals since the last `999999`; used with `111111`. |

The source prohibits requests earlier than the third most recent active date. It does not define merchant cutoff, timezone, enabled operations, or duplicate/end-of-day behavior. Those decisions remain `REVIEW_REQUIRED` until supplied by the SME.

Required tests: one positive and one invalid-code test per enabled operation, an activity-window boundary test, and a response-date assertion where source evidence exists.