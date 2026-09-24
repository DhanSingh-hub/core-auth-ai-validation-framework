# Field-Alias Crosswalk Coverage Report

Evidence source: `run2-ai-element-name-catalog.json` (test_data.key_values, or resolvable AI payload leaf keys as fallback). A crosswalk is only built when unambiguous AI field-name evidence exists; it is never fabricated.

- Trained segments: **10**
- Segments with a field-alias crosswalk: **3**
- Segments with any AI field-name evidence: **3**
- Segments with no AI field-name evidence in this delivery: **7**

| Segment | Crosswalk built | Confirmed aliases | AI evidence entries | Status |
|---|---|---:|---:|---|
|100|yes|5|5|CROSSWALK_BUILT|
|101|yes|2|2|CROSSWALK_BUILT|
|102|no|0|0|NO_AI_EVIDENCE_YET|
|103|no|0|0|NO_AI_EVIDENCE_YET|
|104|no|0|0|NO_AI_EVIDENCE_YET|
|105|no|0|0|NO_AI_EVIDENCE_YET|
|108|no|0|0|NO_AI_EVIDENCE_YET|
|109|no|0|0|NO_AI_EVIDENCE_YET|
|111|yes|4|4|CROSSWALK_BUILT|
|113|no|0|0|NO_AI_EVIDENCE_YET|

Segments with `NO_AI_EVIDENCE_YET` cannot get an evidence-based crosswalk from this delivery: every AI test case that resolves to a data file already carries `key_values`, and none of those key_values are labeled with these segments' numbers, meaning the AI Solution is not yet producing segment-specific field-level test data for them. This should be raised with the AI team separately from Test Solution training.
