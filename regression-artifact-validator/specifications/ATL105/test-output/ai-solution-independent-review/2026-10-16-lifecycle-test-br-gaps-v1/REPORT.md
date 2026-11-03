# Lifecycle Category: 10 Test BR Gap Review

All 6,880 AI BRs and 1,330 independent Test BRs were searched. These ten Test BRs lack a v3 exact-anchor candidate; absence is not semantic non-match.

## Dispositions

| Disposition | Test BRs |
|---|---:|
| PARTIAL_SCOPE_OVERLAP | 6 |
| CLAUSE_MATCH_PROPOSED | 4 |

## Rule Review

### SEG100-R-023
Lifecycle messages preserve required original references

Disposition: **PARTIAL_SCOPE_OVERLAP**

AI clauses cover same-value persistence for Approval Number, Sequence Number, Prompt Code, and specific reversal/completion contexts. Test R-023 is a broader lifecycle rule; exact applicability and the complete required-reference set are not established by one atomic AI rule.

- AI :517: Approval Number must be present and identical to the original transaction for reversal matching. (rank NOT_RETAINED_IN_TOP_K; cases 6; physical complete 5; flags [])
- AI :518: Sequence Number must be present and identical to the original transaction for reversal matching. (rank NOT_RETAINED_IN_TOP_K; cases 6; physical complete 4; flags [])
- AI :607: Element No. 5 (Approval Number) present in original transaction must be present and identical in the Authorization Only Reversal transaction. (rank NOT_RETAINED_IN_TOP_K; cases 6; physical complete 5; flags [])
- AI :608: Element No. 86 (Sequence Number) present in original transaction must be present and identical in the Authorization Only Reversal transaction. (rank NOT_RETAINED_IN_TOP_K; cases 6; physical complete 4; flags [])
- AI :1679: For an Authorization Only Reversal, Approval Number present in the original transaction must be present and identical in the reversal. (rank NOT_RETAINED_IN_TOP_K; cases 6; physical complete 5; flags [])
- AI :1939: In purchase reversal/void or time-out reversal, Sequence Number must match original transaction request. (rank NOT_RETAINED_IN_TOP_K; cases 6; physical complete 4; flags [])
- AI :1940: For Authorization Only Reversal, Sequence Number present in original transaction must be present and identical in the reversal. (rank NOT_RETAINED_IN_TOP_K; cases 6; physical complete 4; flags [])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

### SEG100-R-063
Financial responses support approved purchase/capture response codes

Disposition: **CLAUSE_MATCH_PROPOSED**

AI :1873 and :1877 state approved Purchase/Capture response codes 0 and 4. These are the two source-defined approved Purchase/Capture values; Response Code family context remains explicit.

- AI :1873: Transaction Code 0 means Approved-Purchase/Capture. (rank 1; cases 0; physical complete 0; flags ["NO_LINKED_CASE", "PRODUCER_SEGMENT_UNRESOLVED"])
- AI :1877: Transaction Code 4 means Approved-Purchase/Capture with AVS. (rank 1; cases 0; physical complete 0; flags ["NO_LINKED_CASE", "PRODUCER_SEGMENT_UNRESOLVED"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

### SEG100-R-064
Financial responses support approved authorization response codes

Disposition: **CLAUSE_MATCH_PROPOSED**

AI :1875 and :1876 state approved Authorization Only values 2 and 3 (with AVS). This is clause-level enumeration overlap, not response execution.

- AI :1875: Transaction Code 2 means Approved-Authorization only. (rank 1; cases 0; physical complete 0; flags ["NO_LINKED_CASE", "PRODUCER_SEGMENT_UNRESOLVED"])
- AI :1876: Transaction Code 3 means Approved-Authorization only with AVS. (rank 1; cases 0; physical complete 0; flags ["NO_LINKED_CASE", "PRODUCER_SEGMENT_UNRESOLVED"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

### SEG101-R-007
Field order matches Section 12.2 (base fields 1-13, Auth Completion tag fields 14-18)

Disposition: **PARTIAL_SCOPE_OVERLAP**

AI states Fleet Tag fields 14-18 are Auth Completion-only and gives the tag/data shape, but does not establish the complete Section 12.2 order of fields 1-13 followed by fields 14-18.

- AI :1182: Fleet Tag 1-5 are sent only in Auth Completion message, formatted as 3-byte tag plus up to 31 bytes data. (rank 6; cases 3; physical complete 3; flags ["ELEMENT_SCOPE_UNRESOLVED"])
- AI :1183: 3-byte Fleet Tag values are only valid on Auth Completion (0220) messages when Host Prompts are supported. (rank 6; cases 3; physical complete 3; flags ["ELEMENT_SCOPE_UNRESOLVED"])
- AI :5877: Fleet Tag 1: Conditional field sent only in Auth Completion message with 3-byte tag and up to 31 bytes data. (rank 5; cases 3; physical complete 3; flags ["ELEMENT_SCOPE_UNRESOLVED"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

### SEG101-R-020
Fleet Tag fields 14-18 are only present in Auth Completion (0220) messages

Disposition: **CLAUSE_MATCH_PROPOSED**

AI :1182 and :1183 state Fleet Tags are sent only in Auth Completion (0220); :1183 adds the source condition that Host Prompts are supported.

- AI :1182: Fleet Tag 1-5 are sent only in Auth Completion message, formatted as 3-byte tag plus up to 31 bytes data. (rank 1; cases 3; physical complete 3; flags ["ELEMENT_SCOPE_UNRESOLVED"])
- AI :1183: 3-byte Fleet Tag values are only valid on Auth Completion (0220) messages when Host Prompts are supported. (rank 1; cases 3; physical complete 3; flags ["ELEMENT_SCOPE_UNRESOLVED"])
- AI :5877: Fleet Tag 1: Conditional field sent only in Auth Completion message with 3-byte tag and up to 31 bytes data. (rank 2; cases 3; physical complete 3; flags ["ELEMENT_SCOPE_UNRESOLVED"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

### SEG103-R-024
Segment 103 applicability matrix: REQUIRED for Food Stamp Electronic Voucher (Voucher ID) and eWIC Purchase Completion/Voucher Clear (WIC data); OPTIONAL for all other EBT/eWIC transaction types listed in Section 10.5.3; PROHIBITED for eWIC Return

Disposition: **PARTIAL_SCOPE_OVERLAP**

AI :741 supports the eWIC Return prohibition, and :726 concerns Food Stamp Electronic Voucher. These do not establish the full Required/Optional/Prohibited Segment 103 applicability matrix across all listed EBT/eWIC flows.

- AI :741: eWIC specifications do not support Return transactions. (rank 4; cases 0; physical complete 0; flags ["NO_LINKED_CASE", "ELEMENT_SCOPE_UNRESOLVED"])
- AI :726: Manually entered Voucher Number must appear on receipt for Food Stamp Electronic Voucher transactions only. (rank 1; cases 1; physical complete 1; flags ["ELEMENT_SCOPE_UNRESOLVED"])
- AI :752: eWIC Voucher Clear is used to settle a previously authorized eWIC Voucher issued during an outage. (rank 4; cases 0; physical complete 0; flags ["NO_LINKED_CASE", "ELEMENT_SCOPE_UNRESOLVED"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

### SEG130-R-001
Segment 130 is Required in every EMV authorization-class Financial Transaction Request; the Section 11.8.1 Data Section 3 table marks it R with the note 'This segment is the only segment required for all EMV financial transactions'

Disposition: **PARTIAL_SCOPE_OVERLAP**

AI :1133 states EMV request Segment 130 requirement and :979 states EMV data is not required on reversals. Together they cover the major obligation/exception, but Test's authorization-class applicability and exact Section 11.8.1 context still need clause-level review.

- AI :1133: EMV Request Data Segment is required for all EMV card transactions and is the only segment required for all EMV financial transactions. (rank 1; cases 1; physical complete 1; flags ["ELEMENT_SCOPE_UNRESOLVED"])
- AI :1348: EMV Request Data Segment always appears in Data Section No. 3 of an EMV Financial Transaction Request. (rank 3; cases 1; physical complete 1; flags ["ELEMENT_SCOPE_UNRESOLVED"])
- AI :979: EMV data is not required on Reversal transactions. (rank 5; cases 0; physical complete 0; flags ["NO_LINKED_CASE", "ELEMENT_SCOPE_UNRESOLVED"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

### SEG130-R-017
EMV data is not required on Reversal transactions; Segment 130 may legitimately be absent from an EMV Purchase Reversal or Time-out Reversal

Disposition: **CLAUSE_MATCH_PROPOSED**

AI :979 states EMV data is not required on Reversal transactions, matching the source-defined reversal exception.

- AI :979: EMV data is not required on Reversal transactions. (rank 1; cases 0; physical complete 0; flags ["NO_LINKED_CASE", "ELEMENT_SCOPE_UNRESOLVED"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

### SEG130-R-020
Appendix T Table ID 001 (EMV Table Data) carries exactly EMVYES or EMVNOT, length 6, sent only in an EMV Transaction request; if returned in an authorization response the device echoes it on any subsequent advice or batch upload request

Disposition: **PARTIAL_SCOPE_OVERLAP**

AI clauses cover EMVYES/EMVNOT request use and response echo. The reviewed AI clauses do not establish the complete Table ID 001 / fixed six-character length assertion, so keep the composite rule partial.

- AI :3086: EMVYES indicates device is able to receive EMV Table Data; sent only in an EMV Transaction request. (rank 1; cases 1; physical complete 1; flags ["ELEMENT_SCOPE_UNRESOLVED"])
- AI :3087: EMVNOT indicates device is unable to receive EMV Table Data; sent only in an EMV Transaction request. (rank 1; cases 1; physical complete 1; flags ["ELEMENT_SCOPE_UNRESOLVED"])
- AI :3088: If this field is included in an authorization response, the device echoes it back on any subsequent advice or batch upload request. (rank 2; cases 0; physical complete 0; flags ["NO_LINKED_CASE", "ELEMENT_SCOPE_UNRESOLVED"])
- AI :5836: Data element Maximum Length accepts documented value 'EMVYES' — Indicates device can receive EMV Table Data in EMV Transaction request. (rank 1; cases 0; physical complete 0; flags ["NO_LINKED_CASE", "ELEMENT_SCOPE_UNRESOLVED"])
- AI :5837: Data element Maximum Length accepts documented value 'EMVNOT' — Indicates device cannot receive EMV Table Data in EMV Transaction request. (rank 1; cases 0; physical complete 0; flags ["NO_LINKED_CASE", "ELEMENT_SCOPE_UNRESOLVED"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

### SEG130-R-023
Segment 131 (EMV Response Data Segment) uses inverted serialization rules and must not reuse the Segment 130 parser: no Field Separators at all, maximum length 3,834, EMV Additional Information Section cap 2,800 bytes, CA Public Key File Checksum Required rather than Optional, and no EMV Card Sequence Number field

Disposition: **PARTIAL_SCOPE_OVERLAP**

AI covers Segment 131 max length 3834, no separators, 2800-byte additional-information cap, and required CA checksum. The Test rule's explicit no-EMV-Card-Sequence-Number assertion and whole-parser non-reuse conclusion are not fully matched.

- AI :1351: EMV Response Data Segment has a maximum length of 3834 alphanumeric characters. (rank NOT_RETAINED_IN_TOP_K; cases 0; physical complete 0; flags ["NO_LINKED_CASE", "ELEMENT_SCOPE_UNRESOLVED"])
- AI :1352: Fields in the EMV Response Data Segment are not separated by Field Separators; unpopulated fields are skipped. (rank NOT_RETAINED_IN_TOP_K; cases 0; physical complete 0; flags ["NO_LINKED_CASE", "ELEMENT_SCOPE_UNRESOLVED"])
- AI :1354: EMV Additional Information Section is repeated by EMV Additional Information Indicator for a maximum length of 2,800 bytes. (rank NOT_RETAINED_IN_TOP_K; cases 0; physical complete 0; flags ["NO_LINKED_CASE"])
- AI :3455: In EMV Response Data Segment (Data Segment No. 131), field 3 (CA Public Key File Checksum) is required and must be present. (rank 5; cases 2; physical complete 2; flags [])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## Source Notes

Section 11.4.1.2 narrative says Segment 116, but its layout table says 119; Section 12.17 and Element 85 identify 119 as Totals-with-Proprietary-Data and 116 as TransArmor. Source precedence resolves the runtime segment as 119 while retaining the editorial conflict.

The EMV Table Data case is partial because the AI clauses cover values/echo but not the full Table ID/length claim. The Segment 131 response rule is partial because no reviewed AI statement covers the absence of an EMV Card Sequence Number field.

All relations remain NOT_ASSESSED; Test BRs and their placeholder chains remain DRAFT_REVIEW_REQUIRED. No SME decisions, fixtures, wire checks or host execution are claimed.
