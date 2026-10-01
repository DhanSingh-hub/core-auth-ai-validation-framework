# Segment DL1 Merchant Data Segment: SME and TBA Learning Note

Verified against Sections 12.42, 11.7, 11.7.1, 13.2 (Elements 3, 4, 14, 24, 34, 35, 53, 54, 59, 97, 98) and Appendix E.

## What Segment DL1 Means

DL1 is how BUYPASS tells a device *who it is* (merchant name, store, address, phone) and *what it may do* (accepted card types and enabled features). It is host-originated and appears only in the Table Load Response.

```text
Merchant profile (load flag TABL)
  -> Table Load Request from device
  -> Table Load Response, Data Block 1
  -> Segment DL1 identity + Card Type list
  -> device configuration for the very next transaction
```

Compare with Segment 100:

| Question | Segment 100 | Segment DL1 |
|---|---|---|
| Who sends it? | Device (request) | BUYPASS host (response) |
| How are fields delimited? | Field Separators, empty middle fields keep their separator | No separators; an unpopulated field is simply skipped |
| Segment identity | Segment Type `100` + Segment Length | Data Type Indicator `#` + End-of-Data `~` |
| What drives companions? | Prompt Code and POS context | Card Type `173` drives DL6 |
| Lifecycle | Original → follow-up transactions | Table Load Request → Table Load Response → next transaction |

## Field Layout

`#`(24) · Merchant Name(53, 24) · Store Number(98, 16) · Address Line 1(3, 24) · Address Line 2(4, 21) · Merchant Phone Number(54, 13) · Number of Card Types(59, 2) · Card Type(14, 3) × 01-99 · `~`(34).

## Card Type List Semantics

Appendix E separates two kinds of Table Load codes:

| Kind | Examples | Meaning for the device |
|---|---|---|
| Accepted card types | 001 fleet, 011 debit, 020 credit, 073/074 EBT, 086 eWIC | The device may accept this card |
| Feature codes | 127/162/163 AVS prompts, 148 PAN truncation, 150 debit convenience fee, 164 auto close, 165 keyed gift card, 166-168 disable functions, 171 BUYPASS TAP, 173 Store and Forward, 174 card verification data | The device switches a feature on/off; never used in Prompt Codes |

`173` is the only feature code that also changes the **message**: it makes DL6 part of the same Table Load Response.

## SME Reasoning

1. Is the merchant's load flag `TABL`? If not, no DL1 is expected at all.
2. Which card types and which features should this merchant have? The list is configuration, so the SME must confirm the expected set for each fixture.
3. Is Store and Forward blocking enabled (`173`)? If yes, what blocking window should DL6 carry?
4. Are the merchant identity values synthetic? Merchant name, address and phone must not be copied from production profiles.
5. How are short values padded (`SEGDL1-SME-002`)?

## TBA Decomposition Example

```text
BR:  Segment DL1 shall carry exactly Number-of-Card-Types Card Type values (SEGDL1-R-004).
TS:  Table Load Response for a merchant accepting credit and debit with Store and Forward enabled.
TC-Positive: Number of Card Types = 03; Card Types 020, 011, 173; DL6 present. Expected PASS.
TC-Negative: Number of Card Types = 03 but only two Card Types serialized. Expected FAIL citing SEGDL1-R-004.
TD:  Structured DL1 JSON with numberOfCardTypes "03" and cardTypes ["020","011"] (negative) or ["020","011","173"] (positive).
```

## Source References

Section 12.42: lines 17101-17206 · Section 11.7.1: lines 8968-9236 · Appendix E: lines 26245-26330 · [Rule Catalog](coverage/segment-DL1-rule-catalog.json).
