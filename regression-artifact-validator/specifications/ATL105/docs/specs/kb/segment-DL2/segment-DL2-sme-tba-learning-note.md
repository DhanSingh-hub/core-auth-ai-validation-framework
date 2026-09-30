# Segment DL2 Dial String Data Segment: SME and TBA Learning Note

Verified against Sections 12.43, 11.7.1.2, 11.7.2, 13.2 (Elements 1, 24, 27, 28, 34, 66, 75, 82, 97).

## What Segment DL2 Means

DL2 tells a dial-up device which numbers to call for transactions: a primary and a secondary dial string, each with a redial count, an optional access code (PBX prefix or pause sequence) and a phone number. It is host-originated and arrives in either a Phone Load Response (DL2 only) or a Table Load Response (as Data Block 2).

```text
Merchant profile (PHON or TABL)
  -> Phone Load / Table Load Request
  -> Response carrying DL2
  -> device dials primary (Redial Count times) then secondary
```

Compare with Segment 100:

| Question | Segment 100 | Segment DL2 |
|---|---|---|
| Who sends it? | Device | BUYPASS host |
| Field delimiting | Field Separators | None; `B`, `A`, `F` act as in-band markers |
| Conditional fields | Many, governed by Prompt Code | Access Code + Pause Indicator, together or not at all |
| Lifecycle | Original → follow-up transaction | Load request → response → dialing behaviour |

## Dial String Anatomy

```text
'!' '1' | Redial(1-3) [AccessCode ... 'B'] PhoneNumber 'A' | Redial(1-3) [AccessCode ... 'B'] PhoneNumber 'F' | '~'
          \______________ primary (fields 3-7) ____________/   \_____________ secondary (fields 8-12) ____________/
```

- Each `B` inside the Access Code is a one-second pause (for slow dial tone).
- The Pause Indicator `B` after the Access Code is required whenever an Access Code is present.
- A Hayes-command modem replaces `B` with a comma when dialing — that is device behaviour, not wire content.
- A tertiary number is "assigned but not used" (Element 75) and never appears in DL2.

## SME Reasoning

1. Does this merchant dial through a PBX or need a long pause? If yes, the fixture needs an Access Code and a Pause Indicator.
2. What redial count is configured (1-3)?
3. Which load is being tested (Phone Load vs Table Load) and which load flag is set?
4. Are phone numbers synthetic (`555` range)?
5. How should the device parse Access Code vs Phone Number (`SEGDL2-SME-004`)?

## TBA Decomposition Example

```text
BR:  If an Access Code is present, the Pause Indicator 'B' shall immediately follow it (SEGDL2-R-005).
TS:  Phone Load Response for a PBX merchant (access code 9).
TC+: '!1' '3' '9' 'B' '5555550100' 'A' '2' '5555550199' 'F' '~'. Expected PASS.
TC-: Access Code '9' without Pause Indicator: '!1' '3' '9' '5555550100' 'A' ... Expected FAIL citing SEGDL2-R-005
     (the parser cannot tell the '9' from the phone number).
TD:  Structured DL2 JSON: primary {redialCount 3, accessCode "9", pauseIndicator "B", phoneNumber "5555550100"}.
```

## Source References

Section 12.43: lines 17207-17300 · Section 11.7.2: lines 9240-9345 · [Rule Catalog](coverage/segment-DL2-rule-catalog.json).
