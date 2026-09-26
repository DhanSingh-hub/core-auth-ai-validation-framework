# Segment 116 vs. Segment 119 Disambiguation: SME/TBA Learning Note

## Core Conflict

Section 11.4.1.2 ("Totals with Proprietary Data Load Request") contains this sentence:

> "Data Section No. 3 contains the following data segment: Totals with Proprietary Data Load Request (Data Segment No. 116)"

Taken in isolation, this would suggest Segment 116 is the Totals-with-load segment, contradicting everything else in this KB module. It is not. Two independent, authoritative sources settle the question in favor of Segment 116 = TransArmor Load Data Segment:

1. **Element 85 (Segment Type) valid-codes table** — the single master list of every segment number in the specification — states plainly:
   - `116  Data Segment No. 116, TransArmor Load Data Segment`
   - `119  Data Segment No. 119, Totals with Proprietary Data Load Load Data Segment`
2. **Section 12.17 heading** states: "This section describes Data Segment No. 119, Totals with Proprietary Data Load Data Segment."

Section 11.4.1.2's "(Data Segment No. 116)" is therefore a **typo**: it should read "(Data Segment No. 119)". This has been independently corroborated by the supplied AI requirement catalog, whose own entry linking "Totals with Proprietary Data Load Request" to Segment 116 carries only **34% confidence** — the lowest of any Segment 116-related AI requirement, suggesting the AI pipeline also detected the inconsistency without being able to resolve it.

## Why This Matters for Training

If this conflict were resolved the wrong way — by trusting Section 11.4.1.2's literal text over the master valid-codes table — a validator could end up applying Totals-with-load business rules (409-byte Totals Data, proprietary load fields) to Segment 116 fixtures, or vice versa. Both segments would then be mis-trained.

## Resolution Rule

**When a segment number appears in two places with conflicting names, the Element 85 Segment Type valid-codes table is the tie-breaker**, because it is the single specification-wide index of every segment number and is cross-referenced by the segment's own dedicated Chapter 12 section heading (12.15 for 116, 12.17 for 119).

## SME Questions

1. Should a correction request be filed against the ATL105 specification for the Section 11.4.1.2 typo?
2. Are there any other segment-number references elsewhere in the document that should be independently re-verified against the Element 85 table, given that this one turned out to be wrong?

## Current Boundary

`SEG116-R-009` in the rule catalog records this resolution. Do not re-open it by citing Section 11.4.1.2 alone; any future evidence must also address why the Element 85 table and Section 12.17 heading would both be wrong before the resolution changes.
