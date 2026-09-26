# Block Lifecycle and Multi-Message Loads: SME/TBA Learning Note

## Core Idea

Segment 118 supports both single-message and multi-message ("multi-block") loads via Block Number (Element 11, conditional field 13), Prompt Code Pending (Element 182, field 7), and a dedicated set of Response Code (Element 83) values. This cross-cutting lifecycle applies across all five Prompt Codes, but its exact rules are only fully documented for Site Configuration Data (903).

```text
Block-based load
  -> Block Number starts at 1 (confirmed for Site Configuration Data, 903)
  -> Host/device echo Card Table Load Version, Card Table Type, Load Control Key each round
  -> Response Code signals continuation:
       H, O  -> more pending, continue
       T, Y  -> approved (Y: proceed to next Prompt Code)
       U, X  -> declined (X: proceed to next Prompt Code)
  -> Block Number = 0 -> final message, no payload (confirmed for 903 only)
```

## Source-Confirmed Facts

| Concept | Confirmed for | Source |
|---|---|---|
| Block Number starts at 1, increments per block | Site Configuration Data (903) | Section 12.16.3 |
| Block Number = 0 is a final, data-less message | Site Configuration Data (903) | Section 12.16.3 |
| Host uses Block Number to determine next block | General (field 13 description) | Section 12.16 |
| Response Code H/O = more pending | General Proprietary Data Load | Chapter 13, Element 83 |
| Response Code T/U = approved/declined, no more pending | General Proprietary Data Load | Chapter 13, Element 83 |
| Response Code X/Y = declined/approved, proceed to next pending Prompt Code | General Proprietary Data Load | Chapter 13, Element 83 |
| Prompt Code Pending (182) values 0901/0902/0904/0981 | General | Chapter 13, Element 182 |

## SME Questions

1. Does the Block Number = 0 "final message" convention documented for Site Configuration Data (903) also apply to Dynamic Card Table (902) and Host Discount (904) multi-block loads, or is it specific to 903?
2. What is the retry policy if a block response is lost (timeout, duplicate block, out-of-order block)?
3. When Response Code is X or Y ("proceed to next pending Prompt Code"), does the device read the next code from Prompt Code Pending (182), or is it separately signaled?

## TBA Rule Pattern

```text
BR: A multi-block Proprietary Data Load shall continue while Response Code is H or O, and shall terminate when Response Code is T or U.
TS: Host sends 2 blocks with Response Code O, then a final block with Response Code T.
TC: Validate Block Number increments and Response Code transitions across the block sequence.
TD: Sanitized converter-ready multi-block request/response sequence.
```

## Current Boundary

The validator checks Block Number's format (3 digits) when present. It does not enforce cross-block sequencing, retry, or the Prompt-Code-Pending handoff, since those require session-level state beyond a single-payload Item 1 validator, and are only partially confirmed by source (`SEG118-SME-003`).
