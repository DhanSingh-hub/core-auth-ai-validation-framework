# Segment DL4 Software Dial Load Data Segment: SME and TBA Learning Note

Verified against Sections 12.45, 11.7, 11.7.4, 10.10, 13.2 (Elements 24, 30, 34, 57, 91, 92, 93, 94, 95).

## What Segment DL4 Means

DL4 is a **schedule**, not the software itself. It tells a BUYPASS-managed device which new application version exists, which device-management record to use, which number to dial, and when (date/time) to request a full or partial application load.

```text
Profile bit (DLL) -> Download Indicator 1 -> load request
  -> DL4 (dial) + DL5 (IP) in the response
  -> device dials at Request Date/Time, up to 3 attempts
  -> Table Load after a successful load
```

Compare with Segment 100:

| Question | Segment 100 | Segment DL4 |
|---|---|---|
| Who sends it? | Device | BUYPASS host |
| Field delimiting | Field Separators | None |
| Applicability driver | Prompt Code | Device management model (BUYPASS vs vendor) |
| Lifecycle | Original → follow-up | Schedule → future load attempt → Table Load |

## Correction: DL4 and DL5 Are Not Mutually Exclusive

The previous note said DL4 and DL5 are "mutually exclusive delivery mechanisms". The source says the opposite:

- 11.7.4.2 lists DL4 (field 2) **and** DL5 (field 3), both `R`.
- 10.10 step 4: "BUYPASS responds … by sending the Software Dial Load Data Segment **and** Software IP Load Data Segment."
- 10.10 step 5c: the device stores "Software Load Phone Number **or** Software Load IP/URL Address" — the choice is made by the device, not by omitting a segment.

How the device chooses is open (`SEGDL4-SME-002`).

## SME Reasoning

1. Is the test device BUYPASS-managed? If vendor-managed, no DL4 is valid.
2. Which request triggers DL4 — Software Load (flag `SOFT`) or Table Load (10.10)?
3. Full (`F`) or partial (`P`) application load?
4. Is the scheduled date/time in the future relative to the device clock (DL3)?

## TBA Decomposition Example

```text
BR:  Software Load Type shall be 'F' or 'P' (SEGDL4-R-005).
TS:  Scheduled full application load for a BUYPASS-managed device.
TC+: '@' 'APP02.10' 'STR0000000123' <phone> '101526' '0200' 'F' '~'. Expected PASS.
TC-: Software Load Type 'X'. Expected FAIL citing SEGDL4-R-005.
TD:  Structured DL4 JSON {newSoftwareVersion "APP02.10", softwareTerminalRecordId "STR0000000123", ...}.
```

## Source References

Section 12.45: lines 17363-17445 · Section 11.7.4: lines 9455-9580 · Section 10.10: lines 6166-6200 · [Rule Catalog](coverage/segment-DL4-rule-catalog.json).
