# Segment 130 EMV Chip Data Length: Preliminary Review

**Status:** Source-level review open. No AI-to-Test match is approved and no coverage is credited.

Both AI BRs state the source-supported `000-999` value domain. Their linked candidate payload observations set Element 189 to `000` while Element 190 is non-empty, so those cases do not demonstrate a valid length/data relationship. See [the review record](segment-130-emv-chip-length-review-v1.json) and [the provenance manifest](manifest-v1.json).

The official `SEG130-R-007` chain remains a placeholder. An existing authored Test chain has a reviewed-only positive vector (`010` with a 10-byte Element 190 value), but its case has no concrete negative Test Data vector. The [supplemental draft](segment-130-emv-chip-length-test-chain-package-v1.json) references that positive and adds two mismatch probes; all new records remain `REVIEW_REQUIRED`.

The full payloads and sidecars have not been independently re-opened here, the byte/count interpretation needs Test-owner confirmation, and no host response is inferred. The official baseline is unchanged.
