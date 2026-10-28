# Full-Corpus Review Batch 008: DL2 Dial String Fields

**Preliminary disposition:** Both AI format claims align with ATL105 but omit fixed-value/positional rules; neither has linked cases.

ATL105 defines Element 27 as fixed one-character AN: `A` terminates the first dial string and `F` the second. Element 28 is fixed one-digit numeric and fixed value `1` for transaction dial strings. The current Test model has two DL2 rules covering these additional constraints, but both chains remain draft placeholders.

The two AI BRs have one scenario each and zero cases. Full DL2 field order, complete phone-number block behavior, terminator placement, and independent expected outcomes are therefore untested in these AI chains.

See the [detailed review record](dl2-dial-string-format-review.json) and [batch manifest](manifest.json). The Test baseline and package are unchanged; no match, execution, or coverage credit is approved.
