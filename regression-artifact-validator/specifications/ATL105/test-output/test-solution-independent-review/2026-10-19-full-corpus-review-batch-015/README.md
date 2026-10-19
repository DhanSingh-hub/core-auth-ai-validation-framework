# Full-Corpus Review Batch 015: End-of-Load Indicator

**Status:** Preliminary source review; authorized decision pending. No semantic match or coverage credit is granted.

The roster links BR :042 to Element 35 with one scenario, no cases, and no payload paths. The AI claim describes an alphabetic, one-byte field. ATL105 defines Character Type A, fixed length of one alpha character, and gives `*` as the valid value. Since `*` is punctuation, the generic alphabetic description conflicts with the explicit valid value; preserve the conflict for authorized interpretation.

Element 35 is used in the Table Load Response. The response table assigns the host-sourced `*` marker to End-of-Load Indicator. DL6 follows conditionally when DL1 carries Card Type 173 and is Data Block No. 4. This is distinct from Element 34 End-of-Data Indicator `~`.

There is no exact-anchor Test candidate. The closest ranked leads are `SEGDL6-R-006` and `SEGDL1-R-012`, both draft/review-required Table Load Response sequencing rules. Their BR/TS/TC/TD chains and expected validation remain review-required; they are candidate leads, not approved Element 35 counterparts. No payload or execution evidence exists for this BR.

Follow-up: resolve the source’s alpha-versus-`*` wording and author source-bound cases for Table Load Response marker/block sequencing before seeking semantic or execution credit. The official Test baseline is unchanged.
