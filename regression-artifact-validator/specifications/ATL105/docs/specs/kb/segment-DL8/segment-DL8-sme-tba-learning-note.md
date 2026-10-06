# Segment DL8 EMV Terminal Floor Limits Data Segment: SME and TBA Learning Note

Verified against Section 12.49 and Section 13.2 (Elements 24, 84, 233, 234, 235, 236).

## What Segment DL8 Means

For EMV cards the terminal needs to know, per payment application provider (RID), how much it may approve offline ("stand-in") and in which situations. DL8 delivers one 26-byte group per RID: the RID, the stand-in rule, the floor-limit amount and the BUYPASS card type for that RID.

```text
'%' + SegmentLength(3) + { RID(10) StandIn(1) FloorLimit(12) CardType(3) } x 1..24
```

Compare with Segment 100:

| Question | Segment 100 | DL8 |
|---|---|---|
| Who sends it? | Device | BUYPASS host |
| Repetition | None in the core segment | 1-24 groups |
| Gate | Transaction context | Terminal-level Special |
| EMV link | Segment 130 carries chip data per transaction | DL8 sets per-RID floor limits used by the terminal |

## Stand-in Indicator Semantics (Element 234)

| Value | Meaning | Floor Limit relevance |
|---|---|---|
| `1` | No stand-in | Floor limit should not allow offline approval (non-zero value → `SEGDL8-SME-003`) |
| `2` | Domestic only stand-in | Applies to domestic cards |
| `3` | Domestic & foreign stand-in | Applies to all cards |

## SME Reasoning

1. Does the test terminal have the EMV floor-limit Special?
2. Which RIDs and floor limits are configured? Use public RIDs and synthetic amounts.
3. Is the RID sent as 10 hexadecimal characters?
4. Which BUYPASS card type belongs to each RID?

## TBA Decomposition Example

```text
BR:  DL8 content after the Segment Length shall be 1-24 whole 26-byte groups (SEGDL8-R-003, R-005).
TS:  Terminal with two RIDs configured.
TC+: Two groups (52 bytes). Expected PASS.
TC-: 51 bytes (Card Type truncated in group 2). Expected FAIL citing SEGDL8-R-005.
TC-: 25 groups. Expected FAIL citing SEGDL8-R-003.
```

## Source References

Section 12.49: lines 17636-17690 · Section 13.2 Elements 233-236 · [Rule Catalog](coverage/segment-DL8-rule-catalog.json).
