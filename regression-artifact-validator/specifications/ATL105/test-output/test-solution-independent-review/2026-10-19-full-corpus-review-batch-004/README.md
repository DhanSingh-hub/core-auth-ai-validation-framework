# Full-Corpus Review Batch 004: Currency Code

**Preliminary disposition:** The AI’s 153-code allow-list exactly matches Appendix L. Its format claim is aligned but omits the source’s fixed-length rule; Test applicability remains context-sensitive.

The current Test model has optionality rules for Currency Code in Segments 105 and 119, plus an authored Appendix L rule scoped to Segments 100/111. No direct numeric/fixed-length rule was found for the AI’s Segment 105/119 scope. ATL105 says Element 20 is fixed three digits, defaults to `840` when omitted from a Transaction Request, and is ignored during transaction processing; merchant totals use the merchant-associated currency.

The 632 linked cases exercise six allowed codes (`008`, `012`, `270`, `524`, `840`, `996`) and invalid code `000`; they do not cover the complete Appendix L list. Producer response outcomes include inferred cases and are not independent oracles. Empty-string observations do not establish field omission.

See the [detailed review record](currency-code-format-values-review.json) and [batch manifest](manifest.json). The Test baseline and package are unchanged; no semantic approval, execution, or coverage credit is claimed.
