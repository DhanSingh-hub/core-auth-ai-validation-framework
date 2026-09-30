# Segment DL3 Date and Time Data Segment: SME and TBA Learning Note

Verified against Sections 12.44, 11.7.1.2, 11.7.3, 13.2 (Elements 21, 22, 23, 24, 25, 34, 65) and Appendix E (Card Type 164).

## What Segment DL3 Means

DL3 synchronizes the device with BUYPASS: the current day, date and time (adjusted for the device's time zone and daylight saving time), the merchant's end-of-day Cut Time, and the password used for end-of-day / host-totals functions.

```text
Device (Load Type D, no flag)  or  Table Load (flag TABL)
  -> DL3 ':' Day Date Time CutTime Password '~'
  -> device clock + settlement schedule + totals password
  -> automatic settlement 30 minutes before Cut Time (if configured)
```

Compare with Segment 100:

| Question | Segment 100 | Segment DL3 |
|---|---|---|
| Who sends it? | Device | BUYPASS host |
| Field delimiting | Field Separators | None; all fields fixed width |
| Time fields | Local Transaction Date/Time from the device | Host clock, adjusted for the device's time zone |
| Sensitive data | PAN, PIN data | End-of-day password |

## The Password Conflict

| Source | Says |
|---|---|
| 12.44 field 6 | "Identifies the end-of-day function's password. Source: Device" |
| 11.7.3.2 field 6 | "Identifies the device password used for requesting host totals, etc. Source: Host" |
| Element 65 | Numeric, up to 6 digits, right-aligned with spaces; "matched against the password defined in the merchant's profile at BUYPASS"; required for totals and electronic mail requests |

A host-originated segment carrying a Device-sourced value is unusual. The earlier note treated "Device-sourced" as a settled "notable finding"; it is now a conflict (`SEGDL3-SME-002`) and `SEGDL3-R-003` stays `REVIEW_REQUIRED`.

## SME Reasoning

1. Which time zone is the test device in, and is DST in effect on the test date? The expected Current Time depends on both.
2. What Cut Time is configured for the merchant, and does the device use automatic cut time?
3. Is DL1 Card Type `164` (Auto Close) enabled for the same merchant?
4. Is the password in the fixture synthetic and different from the Element 65 default?

## TBA Decomposition Example

```text
BR:  Day of the Week shall be 0-6 with 0 = Sunday (SEGDL3-R-005).
TS:  Date and Time Load on Wednesday 2026-09-30 at 14:05 device local time.
TC+: ':' '3' '093026' '1405' '2300' '  4821' '~'. Expected PASS.
TC-: Day of the Week '7'. Expected FAIL citing SEGDL3-R-005.
TD:  Structured DL3 JSON {dayOfWeek "3", currentDate "093026", currentTime "1405", cutTime "2300", password "  4821"}.
```

## Source References

Section 12.44: lines 17302-17361 · Section 11.7.3: lines 9347-9453 · [Rule Catalog](coverage/segment-DL3-rule-catalog.json).
