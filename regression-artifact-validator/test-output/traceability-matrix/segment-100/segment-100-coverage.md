# Segment 100 Coverage Report

- Package: `ATL105-SEG100-COMPATIBILITY-001`
- Status: **APPROVED_WITH_REVIEW_ITEMS**
- Covered: 31
- Partially covered: 0
- Review required: 27
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
| `SEG100-R-040` | Information Byte uses a source-defined value | `atl105|2026-3|12.1|100|44|information-byte` | COVERED | yes | yes | yes | yes |
| `SEG100-R-041` | Account Number or identification value is present for the transaction context | `atl105|2026-3|12.1|100|2|account-number` | COVERED | yes | yes | yes | yes |
| `SEG100-R-042` | Card Discretionary Block Data follows conditional source rules | `atl105|2026-3|12.1|100|12|card-discretionary-block-data` | REVIEW_REQUIRED | yes | yes | yes | yes |
| `SEG100-R-043` | Encrypted PIN Block Data follows DUKPT representation when PIN applies | `atl105|2026-3|12.1|100|33|encrypted-pin-block-data` | REVIEW_REQUIRED | yes | yes | yes | yes |
| `SEG100-R-044` | Pump/Lane Number is valid when fuel or lane context applies | `atl105|2026-3|12.1|100|79|pump-lane-number` | REVIEW_REQUIRED | yes | yes | yes | yes |
| `SEG100-R-045` | Fuel Purchase Amount follows the net fuel amount rule | `atl105|2026-3|12.1|100|41|fuel-purchase-amount` | REVIEW_REQUIRED | yes | yes | yes | yes |
| `SEG100-R-046` | Nonfuel Amount follows the net nonfuel amount rule | `atl105|2026-3|12.1|100|58|nonfuel-amount` | REVIEW_REQUIRED | yes | yes | yes | yes |
| `SEG100-R-047` | Tax Amount follows the total tax amount rule | `atl105|2026-3|12.1|100|99|tax-amount` | REVIEW_REQUIRED | yes | yes | yes | yes |
| `SEG100-R-048` | Cash Amount follows the total cash amount rule | `atl105|2026-3|12.1|100|17|cash-amount` | REVIEW_REQUIRED | yes | yes | yes | yes |
| `SEG100-R-049` | Approval Number is present when the lifecycle requires approval reference | `atl105|2026-3|12.1|100|5|approval-number` | REVIEW_REQUIRED | yes | yes | yes | yes |
| `SEG100-R-050` | Local Date and Local Time use the source-defined representation when required | `atl105|2026-3|12.1|100|49|local-date-time` | REVIEW_REQUIRED | yes | yes | yes | yes |
| `SEG100-R-051` | EBT flows requiring EBT data include Segment 103 | `atl105|2026-3|11.1.1|103||ebt-data-required` | REVIEW_REQUIRED | yes | yes | yes | yes |
| `SEG100-R-052` | Purchase-card flows requiring purchase-card data include Segment 104 | `atl105|2026-3|11.1.1|104||purchase-card-data-required` | REVIEW_REQUIRED | yes | yes | yes | yes |
| `SEG100-R-053` | Variable-information flows include Segment 111 | `atl105|2026-3||111||variable-information-required` | REVIEW_REQUIRED | yes | yes | yes | yes |
| `SEG100-R-054` | NFC tokenized flows include Segment 123 | `atl105|2026-3||123||nfc-tokenization-required` | REVIEW_REQUIRED | yes | yes | yes | yes |
| `SEG100-R-055` | Moneris authorizer flows include Segment 135 | `atl105|2026-3||135||moneris-authorizer-required` | REVIEW_REQUIRED | yes | yes | yes | yes |
| `SEG100-R-056` | eWIC Authorization uses Prompt Code 3086 and Segment 103 | `atl105|2026-3|10.5.5|100|78|ewic-authorization` | REVIEW_REQUIRED | yes | yes | yes | yes |
| `SEG100-R-057` | eWIC Authorization Cancellation uses Prompt Code S086 and Segment 103 | `atl105|2026-3|10.5.5|100|78|ewic-authorization-cancellation` | REVIEW_REQUIRED | yes | yes | yes | yes |
| `SEG100-R-058` | eWIC Balance Inquiry uses Prompt Code E086 and Segment 103 | `atl105|2026-3|10.5.5|100|78|ewic-balance-inquiry` | REVIEW_REQUIRED | yes | yes | yes | yes |
| `SEG100-R-059` | eWIC Purchase Completion uses Prompt Code 0086 and original lifecycle identity | `atl105|2026-3|10.5.5|100|78|ewic-purchase-completion` | REVIEW_REQUIRED | yes | yes | yes | yes |
| `SEG100-R-060` | eWIC Purchase Reversal/Void uses Prompt Code 8086 | `atl105|2026-3|10.5.5|100|78|ewic-purchase-reversal` | REVIEW_REQUIRED | yes | yes | yes | yes |
| `SEG100-R-061` | eWIC Segment 103 carries bounded WIC and EBT data | `atl105|2026-3|12.4|103||ewic-segment-103-data` | REVIEW_REQUIRED | yes | yes | yes | yes |
| `SEG100-R-062` | eWIC Voucher Clear uses Prompt Code 0086 with applicable voucher data | `atl105|2026-3|10.5.5|100|78|ewic-voucher-clear` | REVIEW_REQUIRED | yes | yes | yes | yes |
| `SEG100-R-063` | Financial responses support approved purchase/capture response codes | `atl105|2026-3|13.2|financial-response|83|response-code-0-4` | REVIEW_REQUIRED | yes | yes | yes | yes |
| `SEG100-R-064` | Financial responses support approved authorization response codes | `atl105|2026-3|13.2|financial-response|83|response-code-2-3` | REVIEW_REQUIRED | yes | yes | yes | yes |
| `SEG100-R-065` | Partial approval responses carry an approved amount | `atl105|2026-3|13.2|financial-response|83|response-code-f` | REVIEW_REQUIRED | yes | yes | yes | yes |
| `SEG100-R-066` | Declined financial responses carry a decline code | `atl105|2026-3|13.2|financial-response|83|response-code-1-s` | REVIEW_REQUIRED | yes | yes | yes | yes |
