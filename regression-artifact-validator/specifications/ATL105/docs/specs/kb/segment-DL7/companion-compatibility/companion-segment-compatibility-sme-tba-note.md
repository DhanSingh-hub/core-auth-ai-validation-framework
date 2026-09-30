# Segment DL7 Companion-Segment Compatibility: SME and TBA Learning Note

**Sources:** 12.48, 12.49, 11.7.1.2 · **Benchmark:** Segment 100 [companion compatibility note](../../segment-100/companion-compatibility/companion-segment-compatibility-sme-tba-note.md)

## Compatibility Baseline

| Companion | Relationship | Disposition |
|---|---|---|
| DL1-DL6 | No stated relationship; DL7 not in their message layouts | `REVIEW_REQUIRED` |
| DL8 | Same hybrid framing (`Data Type Indicator` + Segment Length); both "table load" related | `REVIEW_REQUIRED` |
| Another DL7 | "All or some of the Download Data" | Multi-segment rule open (`SEGDL7-SME-004`) |

No compatibility rule can be `COVERED` until DL7 has a message. Fixtures must say "segment-only" in their context.
