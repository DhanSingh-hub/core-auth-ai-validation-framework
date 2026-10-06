# Segment 105 Companion-Segment Compatibility: SME and TBA Note

**Segment:** 105 — Segment 105  
**Specification:** BUYPASS® Platform ATL105 Message Format Specifications, Release 2026-3  
**Source sections:** Totals Request  
**Oracle:** [segment-105-rule-catalog.json](../coverage/segment-105-rule-catalog.json) (17 rules)  
**Benchmark:** [Segment 100 Learning Module](../../segment-100/README.md)  
**Generated:** 2026-09-28 from the rule catalog and ATL105 Chapter 13 element definitions

## Purpose

Defines which message families and companion segments Segment 105 may appear with, derived from the rule catalog.

## Specification-Derived Rules

| Rule | Title | Section | Element | Status |
|---|---|---|---|---|
| `SEG105-R-009` | Totals cannot be requested before the third most recent active date | Totals Request | 105 | SPEC_DERIVED |
| `SEG105-R-015` | Grand Total, Card Label, Card Type Total Count, and Card Type Total Amount are required | Totals Request | — | SPEC_DERIVED |
| `SEG105-R-017` | Segment 119 use requires an approved proprietary-load selection rule | Totals Request | — | REVIEW_REQUIRED |

## Catalog Notes

_No catalog notes are recorded against these rules._

## What Not To Assume

- Do not infer companion legality from an abbreviated prose list when a normative Data Section table exists.
- Do not assume Segment 105 is required merely because a companion segment is present.
- Do not assume companion rules are symmetric between request and response messages.

## Open Items

_No open provisional items are linked to these rules._
