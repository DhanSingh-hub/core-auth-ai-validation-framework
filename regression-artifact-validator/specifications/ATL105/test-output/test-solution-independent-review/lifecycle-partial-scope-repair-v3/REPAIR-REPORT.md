# Lifecycle: Six Partial-Scope Repair Proposal

This supplement expands six existing Lifecycle Test BR scopes into 23 source-backed review cases. It does not modify the canonical package or authorize matches.

## Disposition

| Test BR | Draft cases | Remaining scope / gate |
|---|---:|---|
| SEG100-R-023 | 5 | The broad Test title must be split/scoped by transaction class; do not apply Authorization Only cancellation rules to every lifecycle message. |
| SEG101-R-007 | 3 | Source-derived cases drafted; reviewer sign-off required |
| SEG103-R-024 | 5 | Do not promote the proposed Optional classification for all other EBT/eWIC types without a message-layout source or SME decision. |
| SEG130-R-001 | 3 | The authorization-class qualifier and product/network exceptions require context binding; do not generalize this rule to every EMV-related message. |
| SEG130-R-020 | 4 | Link response/follow-up cases to an authorized response oracle; this proposal does not execute a host flow. |
| SEG130-R-023 | 5 | Length-boundary vectors are logical validator tests only; complete EMV response bytes and authentic cryptograms require separately approved fixtures/oracles. |

## Proposed Cases

### BR-RULE-SEG100-R-023

Current: Lifecycle messages preserve required original references

- `LIFE-023-AUTH-REV-MATCH`: Authorization Only Reversal matches original references — expected assertion: reversal Prompt Code transaction type is S; Approval Number matches original when present; Sequence Number matches original when present. Evidence: AUTH_ONLY_REVERSAL_REFERENCES.
- `LIFE-023-AUTH-REV-SEQ-MISMATCH`: Reject Authorization Only Reversal with mismatched Sequence Number — expected assertion: reject or source-validator failure cites Sequence Number mismatch. Evidence: AUTH_ONLY_REVERSAL_REFERENCES.
- `LIFE-023-AUTH-REV-APPROVAL-MISSING`: Require original Approval Number on matched Authorization Only Reversal — expected assertion: reject or source-validator failure cites required identical Approval Number. Evidence: AUTH_ONLY_REVERSAL_REFERENCES.
- `LIFE-023-PURCHASE-TOR-SEQ`: Preserve original Sequence Number on Purchase Reversal and TOR — expected assertion: each applicable reversal Sequence Number equals the original request. Evidence: PURCHASE_AND_TIMEOUT_REVERSAL_REFERENCES.
- `LIFE-023-PARTIAL-AMOUNT`: Use source-defined original amount on partial-approval reversal paths — expected assertion: reversal amount matches the source-specified approved/requested amount for the selected reversal class. Evidence: PURCHASE_AND_TIMEOUT_REVERSAL_REFERENCES.

Open scope: The broad Test title must be split/scoped by transaction class; do not apply Authorization Only cancellation rules to every lifecycle message.

### BR-RULE-SEG101-R-007

Current: Field order matches Section 12.2 (base fields 1-13, Auth Completion tag fields 14-18)

- `LIFE-101-ORDER-POS`: Accept Section 12.2 base fields followed by Fleet Tag fields — expected assertion: base fields occupy positions 1-13 in source order; Fleet Tag fields occupy positions 14-18. Evidence: FLEET_SEGMENT_FIELD_ORDER_AND_COMPLETION_TAGS.
- `LIFE-101-ORDER-NEG`: Reject reordered or interleaved Fleet Tag fields — expected assertion: field order violation is detected against the Section 12.2 order. Evidence: FLEET_SEGMENT_FIELD_ORDER_AND_COMPLETION_TAGS.
- `LIFE-101-TAGS-CONTEXT`: Scope fields 14-18 to Auth Completion/Host Prompts — expected assertion: context-gate is enforced; expected rejection requires Test Team confirmation of validator behavior. Evidence: FLEET_SEGMENT_FIELD_ORDER_AND_COMPLETION_TAGS.

### BR-RULE-SEG103-R-024

Current: Segment 103 applicability matrix: REQUIRED for Food Stamp Electronic Voucher (Voucher ID) and eWIC Purchase Completion/Voucher Clear (WIC data); OPTIONAL for all other EBT/eWIC transaction types listed in Section 10.5.3; PROHIBITED for eWIC Return

- `LIFE-103-VOUCHER-ID`: Require Voucher ID for Food Stamp Electronic Voucher case — expected assertion: Voucher ID field is represented in the EBT Data Segment for the voucher flow. Evidence: EBT_FOOD_VOUCHERS_AND_SUPPORTED_TYPES, EBT_SEGMENT_103_FIELDS.
- `LIFE-103-EWIC-COMPLETION`: Exercise Segment 103 WIC fields on eWIC Purchase Completion — expected assertion: WIC-specific data remains scoped to eWIC Purchase Completion. Evidence: EWIC_COMPLETION_AND_VOUCHER_CLEAR, EBT_SEGMENT_103_FIELDS.
- `LIFE-103-EWIC-VOUCHER-CLEAR`: Exercise Segment 103 WIC fields on eWIC Voucher Clear — expected assertion: voucher-clear WIC data is scoped to the source-defined operation. Evidence: EWIC_COMPLETION_AND_VOUCHER_CLEAR, EBT_SEGMENT_103_FIELDS.
- `LIFE-103-EWIC-RETURN`: Block unsupported eWIC Return — expected assertion: reject/block unsupported eWIC Return before host request. Evidence: EWIC_RETURN_AND_INITIAL_FLOWS.
- `LIFE-103-OTHER-TYPES`: Keep remaining EBT/eWIC Segment 103 presence matrix open — expected assertion: derive required/optional/prohibited independently per message type; no inferred blanket optionality. Evidence: EBT_FOOD_VOUCHERS_AND_SUPPORTED_TYPES, EWIC_RETURN_AND_INITIAL_FLOWS.

Open scope: Do not promote the proposed Optional classification for all other EBT/eWIC types without a message-layout source or SME decision.

### BR-RULE-SEG130-R-001

Current: Segment 130 is Required in every EMV authorization-class Financial Transaction Request; the Section 11.8.1 Data Section 3 table marks it R with the note 'This segment is the only segment required for all EMV financial transactions'

- `LIFE-130-REQ-POS`: Accept EMV authorization-class request containing Segment 130 — expected assertion: Segment 130 present in the EMV Financial Transaction Request. Evidence: EMV_FINANCIAL_REQUEST_LAYOUT, EMV_SEGMENT_130_LAYOUT.
- `LIFE-130-REQ-NEG`: Reject EMV authorization-class request missing Segment 130 — expected assertion: required Segment 130 absence is detected. Evidence: EMV_FINANCIAL_REQUEST_LAYOUT, EMV_SEGMENT_130_LAYOUT.
- `LIFE-130-REVERSAL-EXCEPTION`: Allow source-defined EMV reversal without Segment 130 — expected assertion: do not flag missing Segment 130 as invalid for source-defined reversal context. Evidence: EMV_REVERSAL_EXCEPTION.

Open scope: The authorization-class qualifier and product/network exceptions require context binding; do not generalize this rule to every EMV-related message.

### BR-RULE-SEG130-R-020

Current: Appendix T Table ID 001 (EMV Table Data) carries exactly EMVYES or EMVNOT, length 6, sent only in an EMV Transaction request; if returned in an authorization response the device echoes it on any subsequent advice or batch upload request

- `LIFE-130-T001-YES`: Accept EMVYES request value at six characters — expected assertion: fixed Table ID 001; six-character value EMVYES; EMV request context. Evidence: APPENDIX_T_EMV_TABLE_001.
- `LIFE-130-T001-NOT`: Accept EMVNOT request value at six characters — expected assertion: fixed Table ID 001; six-character value EMVNOT; EMV request context. Evidence: APPENDIX_T_EMV_TABLE_001.
- `LIFE-130-T001-NEG`: Reject wrong Table ID, length, value or non-EMV request context — expected assertion: each source-defined constraint is checked independently. Evidence: APPENDIX_T_EMV_TABLE_001.
- `LIFE-130-T001-ECHO`: Echo response Table 001 data on subsequent advice/batch upload — expected assertion: follow-up value equals value included in authorization response. Evidence: APPENDIX_T_EMV_TABLE_001.

Open scope: Link response/follow-up cases to an authorized response oracle; this proposal does not execute a host flow.

### BR-RULE-SEG130-R-023

Current: Segment 131 (EMV Response Data Segment) uses inverted serialization rules and must not reuse the Segment 130 parser: no Field Separators at all, maximum length 3,834, EMV Additional Information Section cap 2,800 bytes, CA Public Key File Checksum Required rather than Optional, and no EMV Card Sequence Number field

- `LIFE-131-NO-SEPARATORS`: Parse Segment 131 without Field Separators — expected assertion: all Segment 131 fields are positional; no Field Separators. Evidence: EMV_SEGMENT_131_LAYOUT.
- `LIFE-131-SEGMENT-LENGTH`: Enforce Segment 131 maximum length 3834 — expected assertion: 3834 accepted at length gate; 3835 rejected at length gate. Evidence: EMV_SEGMENT_131_LAYOUT.
- `LIFE-131-ADDITIONAL-INFO`: Enforce Segment 131 Additional Information cap 2800 bytes — expected assertion: 2800 accepted at cap gate; 2801 rejected at cap gate. Evidence: EMV_SEGMENT_131_LAYOUT.
- `LIFE-131-CHECKSUM-REQUIRED`: Require CA Public Key File Checksum and omit card-sequence field — expected assertion: checksum required; do not parse EMV Card Sequence Number as a Segment 131 field. Evidence: EMV_SEGMENT_131_LAYOUT.
- `LIFE-131-NOT-SEG130-PARSER`: Use Segment 131 response layout, not Segment 130 request parser — expected assertion: response parser accepts the source-defined positional response layout independently of the request parser. Evidence: EMV_SEGMENT_131_LAYOUT, EMV_SEGMENT_130_LAYOUT.

Open scope: Length-boundary vectors are logical validator tests only; complete EMV response bytes and authentic cryptograms require separately approved fixtures/oracles.

## Governance

All cases have `expectedOutcome=REVIEW_REQUIRED` and `executionStatus=NOT_EXECUTED`. They are logical source-derived designs, not executable fixture payloads or authorized Test results. Approve the BR interpretation and independent oracle before promoting any to the canonical package.

The two source windows for EMV differ by request/response: Segment 130 uses separators and 2,000-byte EMV Additional Information; Segment 131 has no separators and a 2,800-byte cap. Keep their parsers independent.

The source-backed EBT/eWIC operation matrix does not explicitly decide presence for every other EBT operation; those branches remain unresolved rather than being inferred optional.
