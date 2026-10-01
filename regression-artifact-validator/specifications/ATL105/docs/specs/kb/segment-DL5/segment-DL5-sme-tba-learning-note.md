# Segment DL5 Software IP Load Data Segment: SME and TBA Learning Note

Verified against Sections 12.46, 12.45, 11.7.4, 10.10, 13.2 (Elements 24, 34, 57, 92, 93, 94, 95, 114).

## What Segment DL5 Means

DL5 is the IP/URL twin of DL4: the same software-load schedule, but with the address of the device management system instead of a phone number. BUYPASS sends DL4 and DL5 together; the device keeps "the Software Load Phone Number **or** the Software Load IP/URL Address" (10.10 step 5c) depending on how it connects.

## DL5 versus DL4

| Field | DL4 | DL5 |
|---|---|---|
| 1 Data Type Indicator | `@` | `$` |
| 4 Address | Element 91 Software Load Phone Number, AN **variable** ≤ 18 | Element 114 Software Load IP/URL Address, AN **fixed** 30 |
| Max length | 52 | 64 (66 in 11.7.4.2) |
| Position in Software Load Response | Field 2 | Field 3 |
| All other fields | Same elements and rules | Same |

Because Element 114 is fixed length, DL5 parses by position, unlike DL4 (whose phone number is variable).

## Findings

- 64 vs 66 maximum length → `SEGDL5-SME-002`.
- Element 114 purpose ("terminal number") and valid values (`01-999`, `a-z`, `A-Z`) do not fit an IP address or URL → `SEGDL5-SME-003`.
- 12.46 calls field 2 "the new software application name"; Element 57 calls it the version number. Test it as AN 8 either way.

## SME Reasoning

1. Does the test device connect by IP? If so, it uses the DL5 address.
2. What exactly goes into the 30 characters: IPv4 with port, hostname, or URL?
3. How is a short address padded?

## TBA Decomposition Example

```text
BR:  DL5 shall carry a 30-character Software Load IP/URL Address (SEGDL5-R-005).
TS:  Scheduled application load for an IP-connected BUYPASS-managed device.
TC+: Address field exactly 30 characters. Expected PASS (content REVIEW_REQUIRED per SEGDL5-SME-003).
TC-: Address field 29 characters, shifting Date. Expected FAIL citing SEGDL5-R-005.
```

## Source References

Section 12.46: lines 17447-17530 · Section 11.7.4: lines 9455-9580 · [Rule Catalog](coverage/segment-DL5-rule-catalog.json).
