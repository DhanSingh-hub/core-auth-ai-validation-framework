# Full-Corpus Review Batch 009: Download Indicator

**Status:** Preliminary analyst review; authorized decision pending. No semantic match or coverage credit is granted.

The register links two AI business requirements to Element 30. The format claim (numeric, maximum one byte) is aligned but omits the source's fixed one-digit representation. The value-list claim is truncated and incomplete: ATL105 lists 0 (no download request), 1 (partial load), 8 (update Key ID and Key), and 9 (update Public Key). The BR does not state a narrower operation context that would justify a subset.

Both AI BRs have one linked scenario but no linked cases. The related Test rules cover response layout/requiredness and a DL4 lifecycle, not a complete Element 30 enum. Their chains remain draft/review-required; they are candidates for review, not semantic counterparts or oracles.

The report retains 12 source excerpts with verified quote text and corrected start-line anchors, including the EMV Public Key paragraph that wraps across source lines. No execution was performed; the official Test baseline and AI source artifacts were not modified. See the hash-bound `manifest.json` for the report, source, corpus roster and chain/package inputs.
