# Segment DL7 Final Closure: Serialization and Lifecycle Learning Note

**Segment:** DL7 — Supplemental Terminal Data Segment · **Sources:** 12.48, 13.2, Appendix W · **Oracle:** [rule catalog](coverage/segment-DL7-rule-catalog.json) · **Benchmark:** Segment 100 [final-closure note](../segment-100/final-closure-sme-tba-note.md)

## Corrections to the Previous Closure Note

| Earlier statement | Correct DL7 behaviour | Evidence |
|---|---|---|
| "An empty middle field keeps its separator" | No separators; TLV entries | 12.48, Appendix W |
| Segment Length "includes the Segment Type and separators" (review question) | DL7 Segment Length **excludes** `^`; own-digit counting open | 12.48 field 2; `SEGDL7-SME-005` |
| Appendix W "not transcribed" | Transcribed as `SEGDL7-R-004` | Appendix W |

## Two Candidate Encodings (until `SEGDL7-SME-005`)

Download Data `001003eng002013A1B 2C3      ` is 28 bytes.

| Reading | Segment Length | Serialized |
|---|---|---|
| Length counts Download Data only | `028` | `^028` + data (32 characters) |
| Length counts its own 3 digits + data | `031` | `^031` + data (32 characters) |

A validator must accept only one of these once the SME answers; until then both are `REVIEW_REQUIRED`.

## Closure Gate

DL7 is not closeable while `SEGDL7-SME-001` to `SEGDL7-SME-005` are open.
