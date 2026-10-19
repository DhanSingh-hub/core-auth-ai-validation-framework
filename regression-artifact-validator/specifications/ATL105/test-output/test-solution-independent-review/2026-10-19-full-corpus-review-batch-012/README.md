# Full-Corpus Review Batch 012: Employee Number

**Status:** Preliminary analyst review; authorized decision pending. No semantic match or coverage credit is granted.

ATL105 Element 32 is numeric, fixed length of four digits (N4). Its valid values are “valid employee numbers”; the source does not provide an exhaustive employee-number domain. It also says to use `1111` if no employee numbers are assigned. The AI claim (“numeric” and “must not exceed 4 bytes”) is partly aligned but omits the fixed N4 requirement and does not establish semantic validity.

The roster links 28 cases across Segments 105, 109 and 119; 16 are marked physical-complete in the frozen roster. All 28 have candidate payload/sidecar pairs in two source trees, and the duplicate copies are byte-identical. All target sidecar values match their payloads. Across one copy per case, the redacted format audit found 7 exact-width numeric observations, 7 five-character width violations, 7 four-character nonnumeric observations, and 7 empty values. These counts match the producer-declared boundary/negative classes, but producer labels are not independent oracles, and no execution was performed. Numeric N4 format alone does not verify an employee number against the source’s open value domain.

Twenty-four registered `approved`-path references are absent from the frozen 10-Oct source tree. They were not substituted from another intake; the report uses the 56 available candidate payloads and sidecars only. The folder label “approved” is a corpus status, not semantic approval.

Exact Test leads are all draft/review-required: `SEG109-R-013` is the closest numeric/max-four format rule but omits fixed width; `SEG105-R-006` addresses authorization policy; `SEG119-R-013` addresses conditionality. Their BR/TS/TC/TD chains and expected validation remain review-required. Follow-up requires the missing approved-path provenance, independent expected outcomes and authorized decisions on format, applicability and valid employee values. The official Test baseline is unchanged.
