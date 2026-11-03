# Full-Corpus Review Batch 007: Decline Code

**Preliminary disposition:** The format claim is partly aligned; the 14-code claim is a valid contextual subset, not an established complete Element 26 enum.

ATL105 defines Element 26 as a fixed two-character AN code and says “any valid Decline Code.” The AI list combines codes from Appendix H re-entry, timeout, and TransArmor token-retrieval tables. It omits other listed values, including EBT re-entry `!3`/`!W`, gas-card `:H`, and recurring-payment `K=`/`K#`; its generic “must be one of” scope is unsupported without a narrower transaction context.

The current Test model has a Segment 150 two-character field rule and an Appendix H response-behavior rule, but neither is a complete Element 26 code-set oracle. Both AI BRs have one scenario and zero cases.

See the [detailed review record](decline-code-format-values-review.json) and [batch manifest](manifest.json). No intended enum is repaired by guesswork; the Test baseline is unchanged and no approval, execution, or coverage credit is claimed.
