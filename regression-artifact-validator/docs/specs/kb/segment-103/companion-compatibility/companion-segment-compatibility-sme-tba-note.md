# Segment 103 Companion-Segment Compatibility: SME and TBA Note

## Purpose

Segment 101 has a documented mutual-exclusion rule against Segment 145 (Enhanced Fleet). Segment 103 has **no equivalent documented conflict** in Section 12.4 or elsewhere in the extracted specification text reviewed for this training pass.

## What Is Known

- Segment 103 "can appear in any of the fields in Data Section No. 3" (Section 12.4 opening). This wording is permissive, not exclusive — it does not imply Segment 103 excludes any other Data Section 3 segment.
- No appendix or section cross-reference in this KB currently states that Segment 103 cannot coexist with another companion segment (e.g., Segment 101 Fleet Data, Segment 102 Product Code Data, Segment 145 Enhanced Fleet).
- A transaction is very unlikely to combine EBT/WIC with Fleet in practice (different card programs), but "unlikely in practice" is a business observation, not a specification rule, and must not be encoded as a validator rejection.

## What Not To Assume

- Do not add a `SEG103-R-0xx` mutual-exclusion rule modeled on `SEG101-R-003` without a specification citation.
- Do not reject a payload solely because it contains both an EBT Data Segment and a Fleet Data Segment; that is `REVIEW_REQUIRED`, not `FAIL`, until an SME confirms otherwise.
- Do not assume Segment 103 shares the same "at most one companion of this type" and "no known conflict" conclusions with every other segment — each companion pairing needs its own SME confirmation as new segments are trained.

## Open Question

Is there an undocumented business rule (outside the ATL105 specification, e.g., a client contract or a Petroleum Industry Processing Specifications-style external document) that constrains which programs can combine with EBT/WIC in the same message? Track this alongside PROVISIONAL P-01 in the [rule catalog](../coverage/segment-103-rule-catalog.json).
