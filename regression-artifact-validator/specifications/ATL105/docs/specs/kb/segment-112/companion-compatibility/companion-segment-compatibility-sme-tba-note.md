# Segment 112 Companion-Segment Compatibility: SME and TBA Note

**Segment:** 112 — Additional Information Data Segment  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** 12.11, 13  
**Oracle:** [segment-112-rule-catalog.json](../coverage/segment-112-rule-catalog.json) (10 rules)  
**Benchmark:** [Segment 100 Learning Module](../../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Purpose

Defines which message families and companion segments Segment 112 may appear with, derived from the rule catalog.

## Specification-Derived Rules

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG112-R-004` | Segment 112 appears only at the end of a Financial Transaction response | 12.11 | — | SPEC_DERIVED |
| `SEG112-R-010` | Segment 112 is required in a Financial Transaction Response only when Element 115 (Additional Information Data Segment Flag) equals 1 | 13 | 115 | SPEC_DERIVED |

## Catalog Notes

_No catalog notes are recorded against these rules._

## What Not To Assume

- Do not infer companion legality from an abbreviated prose list when a normative Data Section table exists.
- Do not assume Segment 112 is required merely because a companion segment is present.
- Do not assume companion rules are symmetric between request and response messages.

## Open Items

_No open provisional items are linked to these rules._
