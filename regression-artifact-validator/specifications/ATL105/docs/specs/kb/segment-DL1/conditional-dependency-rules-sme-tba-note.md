# Segment DL1 Conditional Fields and Cross-Field Dependencies: SME/TBA Learning Note

**Segment:** DL1 — Merchant Data Segment · **Sources:** 12.42, 12.47, 11.7.1.2 · **Oracle:** [rule catalog](coverage/segment-DL1-rule-catalog.json) · **Benchmark:** Segment 100 [partial-approval note](../segment-100/partial-approval-sme-tba-note.md) (a conditional feature with cross-field consequences)

## Core Idea

DL1 has no Conditional (`C`) fields — all nine are `R`. Its dependencies are between fields and across segments:

| Dependency | Trigger | Consequence | Rule |
|---|---|---|---|
| Count ↔ occurrences | Number of Card Types = N | Exactly N Card Type values, then `~` | `SEGDL1-R-004` |
| Card Type → segment | Any Card Type = `173` | DL6 must follow in the same Table Load Response | `SEGDL1-R-005` |
| Segment → Card Type (reverse) | DL6 present | At least one DL1 Card Type = `173` | `SEGDL1-R-005`, `SEGDL6-R-001` |
| Load flag → segment | Merchant load flag `TABL` | DL1 returned; otherwise no DL1 | `SEGDL1-R-008` |

The earlier version of this note recorded "no conditional rules" — that was a gap: the count/occurrence and `173` → DL6 dependencies are cross-field and cross-segment rules and are now catalogued explicitly (Lesson L3).

## Why the Count Matters More Here Than in Segment 100

With no separators and no Segment Length, Number of Card Types is the only way a parser knows where the Card Type list ends. A wrong count does not produce a "bad value" — it moves the `~` and corrupts the next data block.

## SME Questions

1. Can the same Card Type appear twice in the list? The source does not prohibit it; treat duplicates as `REVIEW_REQUIRED`.
2. Is the order of Card Types meaningful to the device?
3. If `173` is present but the merchant has no blocking window configured, does the host omit `173` or send DL6 with a default window?

## TBA Decomposition

```text
BR:  If DL1 contains Card Type 173, the same Table Load Response shall contain DL6 (SEGDL1-R-005).
TS:  Merchant with Store and Forward blocking enabled.
TC+: DL1 Card Types 020,173; DL6 '\' 2300 0500 '~' present. Expected PASS.
TC-: DL1 Card Types 020,173; DL6 absent. Expected FAIL citing SEGDL1-R-005.
TC-: DL1 Card Types 020 only; DL6 present. Expected FAIL citing SEGDL1-R-005 / SEGDL6-R-001.
TD:  Table Load Response JSON holding both DL1 and (optionally) DL6 blocks.
```

## Review Checklist

- Does every `173` fixture contain both segments in one response object, not two separate files?
- Is the reverse case (DL6 without `173`) tested?
- Is the count mutation tested both ways (count too high and too low)?
