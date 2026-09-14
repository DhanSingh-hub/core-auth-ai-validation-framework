# Segment 100 Coverage Report

- Package: `ATL105-SEG100-COMPATIBILITY-001`
- Status: **APPROVED_WITH_REVIEW_ITEMS**
- Covered: 29
- Partially covered: 0
- Review required: 2
- Missing: 0

| Rule | Title | Canonical anchor | Status | BR | Scenario | Test Case | Test Data |
| --- | --- | --- | --- | --- | --- | --- | --- |
| `SEG100-R-001` | Segment 100 is required exactly once | `atl105|2026-3|11.1.1|100||segment-100-required-once` | REVIEW_REQUIRED | yes | yes | yes | yes |
| `SEG100-R-002` | EMV requires Segment 130 | `atl105|2026-3|11.8.1|130||section-3-required-for-emv` | COVERED | yes | yes | yes | yes |
| `SEG100-R-003` | Fleet data requires Segment 101 | `atl105|2026-3|11.1.1|101||fleet-data-required` | COVERED | yes | yes | yes | yes |
| `SEG100-R-004` | Product or fuel data requires Segment 102 | `atl105|2026-3|11.1.1|102||product-or-fuel-data-required` | COVERED | yes | yes | yes | yes |
| `SEG100-R-005` | Multiple applicable categories require every companion segment | `atl105|2026-3|11.1.1|101,102||multiple-section-3-segments-required` | COVERED | yes | yes | yes | yes |
| `SEG100-R-006` | Unknown segment combinations require review | `atl105|2026-3|12|unknown||unknown-combination-requires-review` | REVIEW_REQUIRED | yes | yes | yes | yes |
| `SEG100-R-007` | Element 63 equals serialized segment count | `atl105|2026-3|11.1.1|data-section-1|63|number-of-segments` | COVERED | yes | yes | yes | yes |
| `SEG100-R-008` | Companion segments are not duplicated | `atl105|2026-3|12|101||segment-occurrence` | COVERED | yes | yes | yes | yes |
| `SEG100-R-009` | Segments follow the applicable order | `atl105|2026-3|12|100||segment-100-field-order` | COVERED | yes | yes | yes | yes |
| `SEG100-R-010` | TCP/IP message length uses following payload length | `atl105|2026-3|appendix a|tcp-ip-header|message-length|message-length` | COVERED | yes | yes | yes | yes |
| `SEG100-R-011` | TCP/IP message length uses network byte order | `atl105|2026-3|appendix a|tcp-ip-header|message-length|network-byte-order` | COVERED | yes | yes | yes | yes |
| `SEG100-R-012` | TPDU Protocol ID is 0x60 | `atl105|2026-3|appendix a|tcp-ip-header|protocol-id|tpdu-protocol-id` | COVERED | yes | yes | yes | yes |
| `SEG100-R-024` | Reserved TPDU destination and source addresses are zero | `atl105|2026-3|appendix a|tcp-ip-header|destination-source-address|reserved-tpdu-addresses` | COVERED | yes | yes | yes | yes |
| `SEG100-R-013` | Message format identifier is ATL105 | `atl105|2026-3|11.1.1|data-section-1|55|message-format-version-identifier` | COVERED | yes | yes | yes | yes |
| `SEG100-R-014` | Data Section 1 separators are present | `atl105|2026-3|11.1.1|data-section-1|55-63|section-1-field-separators` | COVERED | yes | yes | yes | yes |
| `SEG100-R-015` | Segment Type is 100 | `atl105|2026-3|12.1|100|85|segment-type` | COVERED | yes | yes | yes | yes |
| `SEG100-R-016` | Segment Length represents encoded Segment 100 content | `atl105|2026-3|12.1|100|84|segment-length` | COVERED | yes | yes | yes | yes |
| `SEG100-R-017` | Terminal Identifier has valid format | `atl105|2026-3|12.1|100|102|terminal-identifier` | COVERED | yes | yes | yes | yes |
| `SEG100-R-018` | Prompt Code represents transaction and card context | `atl105|2026-3|12.1|100|78|prompt-code` | COVERED | yes | yes | yes | yes |
| `SEG100-R-019` | Sequence Number is six digits and persists through lifecycle | `atl105|2026-3|12.1|100|86|sequence-number` | COVERED | yes | yes | yes | yes |
| `SEG100-R-020` | Partial Approval Indicator uses an allowed value | `atl105|2026-3|12.1|100|121|partial-approval-indicator` | COVERED | yes | yes | yes | yes |
| `SEG100-R-021` | Empty non-trailing fields retain separators | `atl105|2026-3|12.1|100||non-trailing-empty-field-separator` | COVERED | yes | yes | yes | yes |
| `SEG100-R-022` | Unneeded trailing optional fields are omitted | `atl105|2026-3|12.1|100||trailing-optional-fields-omitted` | COVERED | yes | yes | yes | yes |
| `SEG100-R-023` | Lifecycle messages preserve required original references | `atl105|2026-3|10|100||lifecycle-correlation` | COVERED | yes | yes | yes | yes |
| `SEG100-R-030` | Terminal Identifier respects load-flow length dependency | `atl105|2026-3|12.1|100|102|terminal-identifier-load-length` | COVERED | yes | yes | yes | yes |
| `SEG100-R-031` | Terminal Identifier matches declared components | `atl105|2026-3|12.1|100|102|terminal-identifier-components` | COVERED | yes | yes | yes | yes |
| `SEG100-R-033` | Prompt Code card type or flow code matches expected context | `atl105|2026-3|12.1|100|78|prompt-code-card-type` | COVERED | yes | yes | yes | yes |
| `SEG100-R-034` | Account Number representation agrees with POS entry method | `atl105|2026-3|10.1.1|100|2|account-number-entry-method` | COVERED | yes | yes | yes | yes |
| `SEG100-R-035` | Tokenized or TransArmor Account Number representation is explicit | `atl105|2026-3|10.13|100|2|account-number-token-representation` | COVERED | yes | yes | yes | yes |
| `SEG100-R-037` | Lifecycle follow-ups reuse the original Sequence Number and required approval context | `atl105|2026-3|10|100|86|lifecycle-correlation` | COVERED | yes | yes | yes | yes |
| `SEG100-R-039` | Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context | `atl105|2026-3|12.1|100|121|partial-approval-context` | COVERED | yes | yes | yes | yes |
