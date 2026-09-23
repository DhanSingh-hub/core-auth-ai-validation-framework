# Fleet Tag (Auth Completion only): SME/TBA Learning Note

## What Fleet Tags Do

Fleet Tag fields 14..18 in Segment 101 carry Host-Prompt data from the device to the host on **Auth Completion (0220) messages**. Each tag is a compact 3-byte code plus a per-code payload — a lightweight TLV encoded inline in Segment 101.

```text
Fleet Tag n
  -> first 3 bytes: fixed tag code (DLS, DLN, PON, INV, TRP, UNT, TLH, DOB, ZIP, END, MID, VIN, TRA, HUB, TLR, CBA, VHT)
  -> remaining bytes: per-code payload (an3..an31 or n6..n9 depending on code)
```

Fleet Tags are **not** free text. Each 3-byte code determines the format and maximum length of its payload.

## SME/TBA Learning Objective

The SME identifies what the fleet program prompted for at the pump and how many prompts were captured. The TBA converts that understanding into explicit, testable rules. Neither role should begin by copying a Fleet Tag from an AI artifact.

```text
POS Auth Completion event
  -> Host Prompts capability confirmed
  -> Host Prompt responses captured
  -> Fleet Tag positions populated (1..5)
  -> per-code format enforced
  -> Segment 101 field-order and Segment Length adjusted
  -> host reconciles fleet-program data
```

Key questions:

1. **Is the message type Auth Completion (0220)?** Fleet Tags outside 0220 are invalid.
2. **Are Host Prompts supported?** Without Host Prompts, no Fleet Tag may be present.
3. **Which tag codes were captured?** ATL105 enumerates 17 codes; anything else is invalid.
4. **Does each payload match the per-code format?** DLS = an3, DLN = an22, PON = an31, INV = an31, TRP = an15, UNT = an31, TLH = n6, DOB = n8, ZIP = an9, END = an31, MID = an31, VIN = an17, TRA = an15, HUB = n9, TLR = an15, CBA = n6, VHT = an10.

## Example Fleet Tag Combinations

| Host Prompt captured | Tag code | Payload example |
| --- | --- | --- |
| Driver license state | DLS | `TX` |
| Driver license name  | DLN | `SMITHJOHN` (subject to PROVISIONAL P-02 typo review) |
| Work order / P.O. Number | PON | `PO-4471928` |
| Invoice Number | INV | `INV-2026-000789` |
| Trip Number | TRP | `T-99123` |
| Unit Number | UNT | `UNIT-2201` |
| Trailer/reefer hours | TLH | `002143` |
| Driver Date of Birth | DOB | `19811024` (YYYYMMDD) |
| ZIP / postal code | ZIP | `750014` |
| Entered data (freeform) | END | `MAINT-CYCLE-Q4` |
| Maintenance ID | MID | `MAINT-2211-A` |
| VIN | VIN | `1FTFW1EF3EFA12345` |
| Tractor Number | TRA | `TRAC-77812` |
| Hubometer | HUB | `123456789` |
| Trailer Number | TLR | `TRLR-45510` |
| Cashback Amount | CBA | `002000` |
| Vehicle Tag | VHT | `TX9AB1234` |

## What Not To Assume

- Do not populate any Fleet Tag on an initial Financial Transaction Request.
- Do not populate any Fleet Tag when Host Prompts are not supported.
- Do not treat the tag code as free text — only the 17 codes above are valid.
- Do not exceed the per-code payload length caps.
- Do not treat Fleet Tag positions 1..5 as ordered by tag code — the fleet program decides ordering.

## Related Rules

- `SEG101-R-020` — Fleet Tag fields 14..18 are only present in Auth Completion (0220).
- `SEG101-R-021` — Fleet Tag fields 14..18 are only sent when Host Prompts are supported.
- `SEG101-R-022` — Fleet Tag format is 3-byte code + up to 31-byte payload.
- `SEG101-R-023` — Fleet Tag 3-byte code must be one of the 17 codes.
- `SEG101-R-024` — Fleet Tag payload conforms to the per-code format.

## Boundary: PROVISIONAL Items Impacting Fleet Tag Coverage

- `P-01` — Segment Length envelope with tags present (up to 5×34 bytes of tag data plus base fields exceeds the 001..061 base cap). Confirm the actual max length rule.
- `P-02` — DLN description "nameation" reads as a typo of "name"; SME must confirm.
- `P-06` — Which Host Prompts must fire in which fleet program.
