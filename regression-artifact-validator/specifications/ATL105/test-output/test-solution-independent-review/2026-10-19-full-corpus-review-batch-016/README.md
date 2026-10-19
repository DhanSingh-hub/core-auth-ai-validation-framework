# Full-Corpus Review Batch 016: Extract Date

**Status:** Preliminary analyst review; authorized decision pending. No semantic match or coverage credit is granted.

ATL105 Element 36 is numeric, fixed six digits in MMDDYY form, with valid values described as “any valid date.” The AI claim (numeric, maximum six bytes) is partly aligned but omits fixed N6/MMDDYY and date validity. The source gives no two-digit-year century pivot in this definition, so this audit does not certify calendar validity or infer a pivot. The element purpose ties the date to an Electronic Mail Data Segment; no presence rule is inferred here.

The roster links 16 Segment 109 cases, with 4 marked physical-complete. The 32 candidate payload/sidecar copies across two trees are byte-identical, and all target sidecar values match payloads. Across one copy per case, the redacted N6/ASCII shape audit found 4 over-width values, 4 nonnumeric/non-ASCII values, 4 empty values, and 4 six-digit numeric MMDDYY-shaped values. Empty values remain presence-unresolved. Producer-declared scenario classes align with these shape findings, but are not independent expected outcomes; no execution was performed.

Twenty-four registered approved-path references are unavailable in the frozen 10-Oct source and remain unresolved; they were not substituted from another intake. The audit uses the 32 available candidate copies only.

The exact Test lead, `SEG109-R-016`, is a compound conditional rule covering Local Time, Extract Date and Extract Time. Its BR/TS/TC/TD chain and expected validation remain review-required, so it is not an approved standalone Element 36 oracle. Follow-up requires approved-path provenance, independent date outcomes and authorized clarification of date validity/century interpretation. Raw date values are not included in the report, and the official Test baseline is unchanged.
