# Segment DL6 Store and Forward Data Segment: SME and TBA Learning Note

Verified against Sections 12.47, 11.7.1.2, 13.2 (Elements 14, 24, 34, 35, 166) and Appendix E (Card Type 173).

## What Segment DL6 Means

Store and forward lets a device keep transactions and send them later when the host is unreachable. DL6 tells the device the daily time window during which that behaviour is **blocked**. It exists only because DL1 switched the feature on with Card Type `173` ("Enables Store and Forward blocking for a designated range of time each day").

```text
DL1 Card Type 173  ->  DL6 '\' Start End '~' in the same Table Load Response  ->  daily blocking window at the device
```

Compare with Segment 100:

| Question | Segment 100 | Segment DL6 |
|---|---|---|
| What triggers it? | Always present in a financial request | Only DL1 Card Type `173` |
| Size | Dozens of fields | Two 4-digit times |
| Risk | Field-level errors | Cross-segment presence errors and window semantics |

## Source Defects Found

| Defect | Where | Item |
|---|---|---|
| Max length 9, but fields sum to 10 | 12.47, 11.7.1.2 | `SEGDL6-SME-003` |
| Field 1 says "software IP load data follows" | 12.47 field 1 | `SEGDL6-SME-004` |
| Element 34 lists End-of-Data for DL1-DL5 only | 13.2 Element 34 | `SEGDL6-SME-004` |
| End Time has no Source | 12.47 field 3 | `SEGDL6-SME-004` |
| Element 166's purpose mentions only Segment 118 | 13.2 Element 166 | `SEGDL6-SME-001` |

## SME Reasoning

1. Is Store and Forward blocking enabled for the merchant (DL1 `173`)?
2. What window is configured? Does it cross midnight?
3. Are the times device-local?

## TBA Decomposition Example

```text
BR:  DL6 shall be present if and only if DL1 carries Card Type 173 (SEGDL6-R-001).
TS:  Merchant with blocking window 01:00-05:00.
TC+: DL1 Card Types 020,173; DL6 '\01000500~'. Expected PASS.
TC-: DL1 Card Types 020; DL6 '\01000500~'. Expected FAIL citing SEGDL6-R-001.
TD:  Table Load Response JSON with DL1 and DL6 blocks.
```

## Source References

Section 12.47: lines 17532-17585 · Section 11.7.1.2: lines 9180-9236 · [Rule Catalog](coverage/segment-DL6-rule-catalog.json).
