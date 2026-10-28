# Appendix K Additional Information Training

**Specification:** BUYPASS ATL105 2026-3, Appendix K-1 through K-34<br>
**Data segment:** Segment 112, Additional Information Data Segment (§12.11)<br>
**Fields:** Element 116 selector, Element 117 length, Element 118 value<br>
**Status:** `PARTIALLY_COVERED`; selector recognition is implemented, per-table layouts remain incomplete.

## Scope and Field Boundaries

Appendix K defines layouts selected by Element 116 and carried in Element 118 of the response-only **Segment 112**. Segment 112 appears at the end of a Financial Transaction Response and is gated by Element 115 (`0` means absent, `1` means present). Element 115 is the segment-presence flag; it is not an Appendix K Table ID.

Do not conflate Element 118 here with the separately defined EMV Additional Information field that also uses Element 118 in Segments 130 and 131. Those are distinct source anchors and message structures.

## Selector Inventory

The Appendix K table bodies contain 44 selector rows. `002` is explicitly reserved, leaving 43 assigned selectors. The source omits `014`, `015`, and `033`; the catalog classifies those as unlisted, not as independently proven reserved codes. The Appendix K introductory bullets omit `044`-`046`, but their table definitions are present on Appendix K-33.

The 43 assigned IDs are:

`001`, `003`-`013`, `016`-`032`, and `034`-`047`.

`016` is BUYPASS Card Type Code; `017` is PIN-on-Receipt Information. `018` is presented with the label “Additional Information Indicator” rather than “Table ID,” and `019` uses a two-digit numeric type (`n2`) in the source row. Preserve both as three-character selector values; the table identity is not determined by its displayed numeric type token alone.

## Test Solution Work Completed

`AppendixKTableCatalog` recognizes the exact source-defined selectors and returns distinct dispositions for `ASSIGNED`, `RESERVED`, `UNLISTED`, and `INVALID_FORMAT`. Its source regression scans Appendix K's extracted table-body range and asserts the complete set, including the wrapped `018`, `n2` `019`, and body-only `044`-`046` definitions.

The Appendix K package now includes one source-anchored BR → TS → TC → TD chain for each of the 43 assigned selector IDs. These harness fixtures prove only that the selector maps to the documented table name. Separate bounded representation predicates cover Table `001` (numeric value up to six characters), Table `003` (signed AVS/NVS framing up to ten characters), Table `004` (one-character CVV result, with network-specific code validation only when network context is supplied), Tables `024` and `025` (fixed one-character indicators, without value-meaning assertions), Table `026` (fixed 16-character alphanumeric framing), Tables `028` and `029` (fixed 15- and 29-character alphanumeric framing), Tables `030` and `031` (fixed two-character alphanumeric framing without value-set assertions), Table `032` (fixed four-character framing), Table `035` (maximum 50 characters), Table `036` (two-character Account Type from the six source-listed values), Table `037` (one-character Account Funding Source from `C`, `D`, or `P`), Table `038` (maximum 22 characters), Table `039` (one-character value from `1`, `2`, or `3`), Tables `040` and `041` (numeric values up to three and two characters), Table `042` (one-character value `1` or `2`), Table `043` (one-character value `Y`), Table `044` (maximum 95 characters), Tables `045` and `046` (one-digit numeric shape only), and Table `047` (one-character value from `1`, `2`, or `3`). Tables 035/038/044 check character-count caps only; encoding width and field meaning are not evaluated. Tables 036/037 are representation/value-set checks only; they do not establish Visa applicability or merchant eligibility. Table 026 framing does not establish DST challenge semantics; Table 039 does not establish lifecycle behavior; Tables 042/043/047 do not establish card/network applicability or transaction eligibility. These predicates do **not** establish balance eligibility, issuer response-code meaning, or a complete response envelope. The other 19 assigned table layouts remain `NOT_ASSERTABLE`; boundary fixtures for reserved `002` and unlisted `014`, `015`, and `033` remain `REVIEW_REQUIRED`.

Package totals: **49 BR / 46 TS / 46 TC / 47 TD**. The additional five existing requirements cover the Segment 112 flag, general layout boundary, balance, AVS/NVS, and card verification; their table-specific behavior remains partial or review-required.

## Deferred Layout and Context Work

1. Derive independent BRs and focused layout tests for the remaining 19 assigned Element 118 formats, preserving fixed versus variable lengths and source-defined subfields.
2. Build response-envelope fixtures for Segment Type 112, Element 115 gating, Element 116/117/118 values, repeated triads, and the 990-byte Additional Information Section cap.
3. Resolve issuer/network-specific representations (including AVS/NVS and card verification) and external definitions or unspecified formats before asserting their semantics.
4. Keep unsupported, undocumented, or externally owned behavior `REVIEW_REQUIRED`; do not infer a layout from table names or Element 118's shared number in another segment.

## Source References

- ATL105 2026-3 §12.11, Segment 112 layout and response placement: [extracted specification](../../extracted_text.txt), lines 12635-12745.
- ATL105 2026-3 Appendix K selector list and table layouts: [extracted specification](../../extracted_text.txt), lines 32577-34456.
- Segment 112 disposition for reserved Element 116 values `002`, `014`, `015`, `033`: [SME/TBA input register](../segment-112/segment-112-sme-tba-input-register.md), `SEG112-SME-002`.