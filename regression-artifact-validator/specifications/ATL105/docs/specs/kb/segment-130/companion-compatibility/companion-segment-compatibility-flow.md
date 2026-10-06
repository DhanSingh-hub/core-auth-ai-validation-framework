# Segment 130 Companion-Segment Compatibility Flow

```text
Candidate message assembled with Segment 130 (EMV Request Data Segment)
  |
  v
Is the message an EMV Financial Transaction Request?
  |
  no --> FAIL: Segment 130 is exclusive to EMV Financial Transaction Requests (SEG130-R-001)
  |
  yes
  v
Is this an EMV Reversal / Time-out Reversal?
  |
  yes --> Segment 130 is NOT required (Section 10.14.2.4: "EMV data is not
  |        required on Reversal transactions"). Absence is legal. (SEG130-R-017)
  no
  v
Is Segment 130 present?
  |
  no --> FAIL: Segment 130 is the only segment required for all EMV financial
  |       transactions (SEG130-R-001)
  yes
  v
Are all Section 3 companions drawn from the EMV allow-list?
  {101, 102, 103, 104, 111, 123, 135, 143, 145, 146, 151, 152, 153}
  |
  no  --> FAIL: segment not permitted in the EMV Financial Transaction Request
  |
  yes
  v
Is the Section 3 segment count <= 5 (Field Nos. 4-8, inclusive of 130)?
  |
  no  --> FAIL: EMV Section 3 has only five field slots (SEG130-R-018)
  |
  yes
  v
proceed; matches the documented EMV Financial Transaction Request shape
  {100, 130, plus up to four of the allow-listed companions}
```

> **Corrected:** a prior revision of this flow rejected Segment 103 (EBT) as an
> illegal companion. Segment 103 **is** permitted — it appears in both the
> Section 11.8.1 Data Section 3 table and the Chapter 12 matrix for the EMV
> Financial Transaction Request. See the
> [compatibility note](companion-segment-compatibility-sme-tba-note.md) for the evidence.
