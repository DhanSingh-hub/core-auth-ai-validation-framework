# Full-Corpus Review Batch 014: End-of-Data Indicator

**Status:** Preliminary source review; authorized decision pending. No semantic match or coverage credit is granted.

The roster links BR :041 to Element 34 in Segments DL3, DL4, DL5 and DL6, with one scenario and no cases, payload paths or physical evidence. No case-level or execution conclusion can be drawn.

ATL105 Section 12.6 defines Element 34 as Character Type A, fixed one alpha character, and gives `~` as its valid code. `~` is ASCII punctuation, not alphabetic, so the generic type/representation and valid-code statements conflict under an alphabetic-only interpretation. Do not replace `~` with the distinct `*` End-of-Load Indicator (Element 35).

There is a second source-scope inconsistency. Section 12.6’s processing list names DL1–DL5 but omits DL6. Section 12.47’s DL6 layout explicitly includes Element 34 at field 4, fixed to `~`. Thus the AI’s DL3–DL6 scope has support in the individual DL6 table, while the generic processing list remains incomplete or inconsistent; authorized interpretation is still required.

The four exact Test leads (`SEGDL3-R-001`, `SEGDL4-R-002`, `SEGDL5-R-002`, `SEGDL6-R-002`) mention `~` in segment rules, but each BR/TS/TC/TD chain remains draft/review-required with expected validation unresolved. These combined segment-length/marker leads are not approved Element 34-only counterparts. Ranked DL1, DL6 and unrelated leads remain candidates only.

Follow-up: adjudicate the source’s alpha-versus-`~` wording, reconcile the DL1–DL5 list with the explicit DL6 layout, and author source-bound cases for the marker and each applicable segment before seeking semantic or execution credit. The official Test baseline is unchanged.
