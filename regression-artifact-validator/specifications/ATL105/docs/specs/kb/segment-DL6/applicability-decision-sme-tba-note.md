# Segment DL6 Applicability and Message-Family Decision: SME/TBA Learning Note

**Segment:** DL6 — Store and Forward Data Segment · **Sources:** 12.47, 11.7.1.2, Appendix E · **Oracle:** [rule catalog](coverage/segment-DL6-rule-catalog.json) · **Benchmark:** Segment 100 [prompt-code note](../segment-100/prompt-code-sme-tba-note.md)

## Core Idea

DL6 is the clearest example of a Conditional segment in the DL family. The layout marks it `C`, and 12.47 states the condition exactly: "only sent in a Table Load Response when Segment No. DL1 … includes a Card Type value of 173". That is a two-way rule:

| DL1 has `173` | DL6 present | Result |
|---|---|---|
| Yes | Yes | Pass |
| Yes | No | Fail (`SEGDL6-R-001`, `SEGDL1-R-005`) |
| No | Yes | Fail (`SEGDL6-R-001`) |
| No | No | Pass |

## SME Questions

1. If DL1 contains `173` more than once, is DL6 still sent once?
2. Can DL6 appear in a partial (dial-string-only) table load?

## TBA Decomposition

```text
BR:  DL6 shall be present if and only if DL1 has Card Type 173 (SEGDL6-R-001).
TC:  Four cases from the truth table above, each in one Table Load Response record.
```
