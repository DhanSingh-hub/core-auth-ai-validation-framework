# Segment 118 Prompt Code: Preliminary Review

**Status:** Preliminary source-level review only. No semantic match is approved and no coverage is credited.

The independent Test package contains `BR-RULE-SEG118-R-011`, but its scenario, case, and data records are placeholders. The source confirms Segment 118 field 6 / Element 78 is required, length 3, and accepts Prompt Codes 901 through 905. See [the review record](segment-118-prompt-code-review-v1.json), [the draft chain package](segment-118-prompt-code-test-chain-package-v1.json), and [the provenance manifest](manifest-v1.json).

The AI BR `REQ-SRC-ATL105-PDF-001:3401` aligns with the required-presence component. Its seven linked cases do not yet establish full chain adequacy: `TC-004055` is the direct omission case but has no frozen payload route; the other six list Prompt Code among request fields without identifying it as the violated element. Their segment placement and values remain unverified.

The draft adds five catalog-value probes and three presence/format/value probes. They are field-level designs, not complete converter-ready requests. All TS/TC/TD records remain `REVIEW_REQUIRED`; the cited source does not define case-specific host responses for invalid requests. The official Test baseline is unchanged.
