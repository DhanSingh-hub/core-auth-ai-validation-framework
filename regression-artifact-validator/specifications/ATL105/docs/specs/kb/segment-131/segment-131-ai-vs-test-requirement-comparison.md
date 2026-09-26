# Segment 131: AI-Generated vs Test-Generated Requirement Comparison

## AI-Generated Requirement Statements Extracted (Source Pages 268-270)

| Statement | Source Rule | Page |
|---|---|---|
| "The EMV Additional Information Section is repeated per EMV Additional Information Indicator for a maximum total length of 2,000 bytes; the Field Separator following Field No. 9 is included with the last repetition." (Segment 130's section, cross-referenced) | BR-268-1 | 268 |
| "Fields within the EMV Response Data Segment are not separated by Field Separators; when a field is not populated, the next field immediately follows." | BR-269-1 | 269 |
| "EMV Response Data Segment has a maximum length of 3834 alphanumeric characters." | BR-269-2 | 269 |
| "Segment Type (Element 85) must be fixed value 131." | BR-269-3 | 269 |
| "CA Public Key File Checksum is echoed from the Request." | BR-269-4 | 269 |
| "EMV Chip Data Length (Element 189) identifies the length of the following EMV Chip Data field (Element 190)." | BR-269-5 | 269 |
| "The EMV Additional Information Section is repeated by EMV Additional Information Indicator for a maximum length of 2,800 bytes." | BR-270-1 | 270 |
| "The EMV Response Data Segment (Segment 131) follows in Field No. 17/18/19/20 when EMV data is required." | (unattributed, cross-referenced from Segment 100's package) | — |

## Match Against the Test Team's Rule Catalog

| AI Statement | AI Solution Team's Own Crosswalk Result | Correct Test Rule | Match Verdict |
|---|---|---|---|
| BR-269-1: no Field Separators | `POTENTIAL_MATCH_REVIEW_REQUIRED` against `BR-SEG103-SEPARATOR-BEHAVIOR` — wrong segment, score 0.357 | `SEG131-R-006` | **CONFIRMED equivalent** once correctly attributed. |
| BR-269-2: max length 3,834 | `MATCHED_SEMANTICS_ONLY` against `BR-SEG101-MAX-LENGTH` — wrong segment, score 0.667 | `SEG131-R-005` | **CONFIRMED**. |
| BR-269-3: Segment Type 131 | `POTENTIAL_MATCH_REVIEW_REQUIRED` against `BR-SEG101-TYPE` — wrong segment, score 0.375 | `SEG131-R-003` | **CONFIRMED**. |
| BR-269-4: checksum echoed from Request | `POTENTIAL_MATCH_REVIEW_REQUIRED` against `BR-SEG130-CA-KEY-CHECKSUM` — related but not identical, score 0.308 | `SEG131-R-007` | **CONFIRMED** — this is the single most valuable AI-extracted fact for this segment, directly confirming the Segment 130 echo relationship. |
| BR-269-5: chip data length relationship | `MATCHED_SEMANTICS_ONLY` against `BR-SEG130-CHIP-DATA-LENGTH-FIELD` — related, score 0.692 | `SEG131-R-008` | **CONFIRMED**. |
| BR-270-1: EMV Additional Information cap 2,800 bytes | `POTENTIAL_MATCH_REVIEW_REQUIRED` against an unrelated Appendix K rule, score 0.4 | `SEG131-R-009` | **CONFIRMED** — and critically, the AI correctly extracted 2,800 (not 2,000), distinguishing it from Segment 130's cap. |
| "follows in Field No. 17/18/19/20" | Not present in a Segment 131-specific crosswalk entry; found only via cross-reference from Segment 100's package | `SEG131-R-001` | **DISPUTED** — this AI statement agrees with the layout table, but directly contradicts Section 12.21's own "Field No. 4 in Data Section No. 3" statement. A genuine specification ambiguity, not an AI error. |

## Headline Findings

1. Unlike Segments 108/114/115, several `BR-269-*` statements reached reasonably high semantic-similarity scores (0.667, 0.692) against the *related* Segment 130 rules — because Segment 131 shares terminology and structure with its request-side counterpart, even though no confirmed match existed before this training pass.
2. **The AI correctly distinguished the two different EMV Additional Information Section byte caps** (2,000 for Segment 130, 2,800 for Segment 131) — a detail that would be easy to conflate.
3. **The placement contradiction (`SEG131-R-001`) is corroborated, not caused, by the AI** — the AI's statement matches the layout table, which conflicts with Section 12.21's own opening sentence. This must be routed to the specification owner.

## Next Steps

- Route `SEG131-SME-001` through `SEG131-SME-004` (see [SME/TBA Input Register](segment-131-sme-tba-input-register.md)) for resolution.
- Report the Section 12.21 vs layout-table placement contradiction to the specification owner.
