# ATL105 Section 10.1 Credit Card Processing - Independent Draft BR/TS/TC/TD Supplement

Status: DRAFT_REVIEW_REQUIRED. This is an independent Test Solution draft derived from ATL105 Section 10.1, not from AI BR wording. No coverage credit or execution certification is issued.

New draft chains: 46 BR / 46 TS / 46 TC / 46 TD design records. Existing independent chains reused by reference: 14.

TD artifacts are non-executable fixture-design placeholders. Each requires valid request payload, applicable fixture/context, expected response oracle, and SME/Test Solution review before execution.

## Draft Rules

| BR ID | Section | Topic | Requirement |
|---|---|---|---|
| BR-DRAFT-10-1-001 | 10.1.1.1 | Credit card expiration date is validated | For a credit-card transaction, the device validates the expiration date of the presented card. |
| BR-DRAFT-10-1-002 | 10.1.1.2 | Track selection uses the supported default and fallback | When Track 2 is supported, the device sends Track 2 by default; if it cannot read Track 2, it sends unaltered Track 1 before manual entry, subject to the Amex-specific rule. |
| BR-DRAFT-10-1-003 | 10.1.1.2 | American Express track selection uses Track 1 first | For American Express, the device sends Track 1 by default and sends unaltered Track 2 before manual entry when Track 1 cannot be read. |
| BR-DRAFT-10-1-004 | 10.1.1.2 | Encrypted AFD Track 1 data is carried in its encryption block | For an AFD transaction with encrypted TransArmor data, Track 1 is present in the Encryption Block and the Encryption Target identifies Track 1. |
| BR-DRAFT-10-1-005 | 10.1.1.2 | Offline pre-authorization and sale preserve full track data | An offline-approved Pre-Auth or Sale sends full track data; follow-up transactions use truncated Track 2 or truncated Track 2 facsimile. |
| BR-DRAFT-10-1-006 | 10.1.1.3 | Manual entry follows three unsuccessful magnetic-stripe attempts | After three unsuccessful magnetic-stripe reads, the device prompts for manual Account Number and Expiration Date in MMYY format. |
| BR-DRAFT-10-1-007 | 10.1.1.3 | Manual entry is identified on receipt and journal | When manual entry is used, an asterisk precedes the Account Number on the receipt and journal. |
| BR-DRAFT-10-1-008 | 10.1.2 | POS credit-card processing supports the documented transaction set | Point-of-sale credit processing supports the transaction types enumerated in Section 10.1.2, including purchase/capture, reversal, authorization-only, return, timeout reversal, mail/phone, void, balance inquiry, partial approval, and authorization-only reversal. |
| BR-DRAFT-10-1-009 | 10.1.2 | Customer-activated credit processing supports its documented transaction set | Customer-activated credit processing supports Purchase/Capture, Authorization Only, Time-out Reversal, Partial Approval, and Authorization Only Reversal. |
| BR-DRAFT-10-1-010 | 10.1.2.1 | Partial approval response identifies the approved amount and remaining balance | With Partial Approval Indicator 1, the network response uses Response Code F, provides the approved amount, and prompts payment of the remaining balance; issuer-returned balance is carried in Segment 112. |
| BR-DRAFT-10-1-011 | 10.1.2.1 | Partial approval applies only to the documented POS and CAT transaction contexts | Partial Approval is supported for POS Purchase, POS Authorization Only, and CAT Authorization Only. |
| BR-DRAFT-10-1-012 | 10.1.2.1 | Partial-approval reversal uses the approved amount and is not a partial reversal | A reversal of a partially approved authorization uses the amount approved in the original response; BUYPASS supports reversal of the partially approved authorization, not a partial reversal of an original transaction. |
| BR-DRAFT-10-1-013 | 10.1.2.1 | Partial-approval timeout reversal preserves original requested amount and sequence | For a partially approved transaction, the TOR uses the amount requested in the original request and the original request's Sequence Number. |
| BR-DRAFT-10-1-014 | 10.1.2.2 | Approved authorizations are cleared or reversed within applicable timeframes | Approved and partially approved authorizations are cleared or reversed; the source gives separate 24-hour card-present and 72-hour card-absent merchant windows. |
| BR-DRAFT-10-1-015 | 10.1.2.2 | Authorization-only reversal supports POS and CAT authorization-only types | The supported reversal contexts are POS Authorization Only type 3 and CAT Authorization Only type 5. |
| BR-DRAFT-10-1-016 | 10.1.2.2 | Authorization-only reversal matches original approval and sequence | Successful matching requires the original Approval Number and Sequence Number to be present and identical, and Prompt Code S to identify cancellation. |
| BR-DRAFT-10-1-017 | 10.1.3 | AVS request and response data use their specified data segments | AVS information is sent in the Variable Information Data Segment and returned in the Additional Information Data Segment alongside the authorization response code. |
| BR-DRAFT-10-1-018 | 10.1.3 | AVS result does not override approval; merchant rejection uses purchase reversal | BUYPASS processes an approved Purchase/Capture regardless of AVS result; if the merchant rejects due to AVS, the merchant performs a Purchase Reversal. |
| BR-DRAFT-10-1-019 | 10.1.4 | Bill-payment, recurring, and installment transactions carry the Variable Information Indicator | The optional bill-payment/recurring/installment service is identified by a Variable Information Indicator in Segment 111. |
| BR-DRAFT-10-1-020 | 10.1.5 | RFID credit-card capture transmits unaltered Track 2 without card contact | RFID capture obtains and transmits full, unaltered Track 2 data wirelessly when the card is near the receiver. |
| BR-DRAFT-10-1-021 | 10.1.5 | RFID receiver presence determines Point-of-Sale Entry Mode on every transaction | When an RFID receiver is connected, Point-of-Sale Entry Mode is populated correctly for all transactions, including swiped, manually keyed, and non-RFID-applicable cards. |
| BR-DRAFT-10-1-022 | 10.1.6 | Healthcare auto-substantiation supports the documented credit and PIN-debit issuers | The service identifies and substantiates medical and OTC purchases for FSA and HRA accounts using the supported issuer/card contexts. |
| BR-DRAFT-10-1-023 | 10.1.6.1.1 | Eligible healthcare benefit cards are identified by stored nine-digit BINs | The POS stores eligible benefit-card BIN information for use when the card is swiped. |
| BR-DRAFT-10-1-024 | 10.1.6.1.2,10.1.6.1.3 | Healthcare partial approval and split tender handle qualified and nonqualified amounts | When partial approval is unsupported, the request is fully approved or declined; when partial approval returns, the POS prompts for remaining balance using another payment method for nonqualified items. |
| BR-DRAFT-10-1-025 | 10.1.6.1.4 | Qualified healthcare product totals follow the documented product-code table | FSA/HRA qualified medical categories and associated Product Codes follow the Section 10.1.6.1.4 table, including required Total QHP Amount code 894 and optional category codes. |
| BR-DRAFT-10-1-026 | 10.1.6.2 | Healthcare requests include MSDI, Partial Approval Indicator, and Product Code data | FSA/HRA transaction requests include Market-Specific Data Indicator in Segment 111, Partial Approval Indicator in Segment 100, and Product Code Segment 102. |
| BR-DRAFT-10-1-027 | 10.1.6.3 | Healthcare responses use standard response data without FSA/HRA-specific elements | The response contains standard response data; this service defines no additional FSA/HRA-specific response elements. |
| BR-DRAFT-10-1-028 | 10.1.6.4 | Healthcare card processing supports the documented purchase, reversal, return, and timeout types | FSA/HRA supports Purchase/Capture, Purchase Reversal, Merchandise Return, and Time-out Reversal. |
| BR-DRAFT-10-1-029 | 10.1.6.4.1 | Approved healthcare purchases update the card-type totals bucket | An approved FSA/HRA Purchase/Capture requires Partial Approval Indicator and Product Code Segment and adds to the card-type totals count and amount. |
| BR-DRAFT-10-1-030 | 10.1.6.4.2 | Healthcare purchase reversal requires the indicator and reverses totals | FSA/HRA Purchase Reversal requires Partial Approval Indicator and subtracts approved reversal count and amount from the card-type totals bucket. |
| BR-DRAFT-10-1-031 | 10.1.6.4.3 | Approved healthcare merchandise returns update totals with the source-defined signs | An approved Merchandise Return adds to the card-type totals count and subtracts from the amount. |
| BR-DRAFT-10-1-032 | 10.1.6.4.4 | Healthcare timeout reversal requires the indicator and reverses totals | FSA/HRA TOR requires Partial Approval Indicator and subtracts approved TOR count and amount from the card-type totals bucket. |
| BR-DRAFT-10-1-033 | 10.1.6.5 | Approved healthcare receipt shows the QHP indicator and subtotal | Approved FSA/HRA receipts print the QHP item indicator and subtotal including taxes and discounts, in addition to general credit-card receipt requirements. |
| BR-DRAFT-10-1-034 | 10.1.7 | Credit-card receipts display the source-required merchant and transaction information | The receipt contains merchant information, transaction type, card type ID, account number, expiration date handling, transaction date/time, sequence number, product and balance information, approval/decline message, and signature-line handling. |
| BR-DRAFT-10-1-035 | 10.1.7.4,10.1.7.5 | Credit-card receipt masks the account number and suppresses expiration date | The receipt masks the PAN to its last four digits with Xs for suppressed digits and suppresses the expiration date; manual entry is marked separately by an asterisk. |
| BR-DRAFT-10-1-036 | 10.1.7.6,10.1.7.7,10.1.7.8 | Credit-card receipt prints transaction date, time, and sequence number | The transaction date, time, and Sequence Number appear on credit-card receipts. |
| BR-DRAFT-10-1-037 | 10.1.7.9 | Approved credit receipt shows fuel or nonfuel product details | Approved transaction receipts show fuel type, quantity, unit price, total fuel amount or nonfuel quantity, unit price, and total product amount. |
| BR-DRAFT-10-1-038 | 10.1.7.10 | Receipt prints returned balance for partial approval or balance-return purchase | When the issuer returns available balance, it prints for Partial Approval and Balance Return with Purchase on approved or declined receipts. |
| BR-DRAFT-10-1-039 | 10.1.7.11 | Credit-card receipt shows approval code or decline message according to device context | Approved POS receipts include the approval phrase and code; declined POS receipts include a decline message when preprint is used; customer-activated devices display the decline message on screen. |
| BR-DRAFT-10-1-040 | 10.1.7.12 | Credit receipt signature line appears only on merchant copy | A signature line appears only on the merchant copy of a credit-card transaction receipt. |
| BR-DRAFT-10-1-041 | 10.1.8.1 | Credit processing restricts card-read data displayed or stored at point of interaction | A device must not display/store card-read data other than account number, expiration date, and cardholder name if present; merchants must not store full magnetic-stripe data. |
| BR-DRAFT-10-1-042 | 10.1.8.1 | Permitted research records are limited and securely maintained | For research, only account number, expiration date, and cardholder name may be recorded in the specified files and must be kept securely; full card-read/discretionary data is prohibited. |
| BR-DRAFT-10-1-043 | 10.1.8.1,10.1.8.2 | Credit clearing data is not reconstructed from previously captured full track data | Full card-read data is not stored or reused to produce later authorization/clearing data, except for the explicitly stated TransArmor-Verifone exception. |
| BR-DRAFT-10-1-044 | 10.1.8.2 | Regulatory track selection uses the card-specific default and unaltered fallback | Track 2 is default when supported, otherwise unaltered Track 1 before manual entry; Amex uses Track 1 by default and unaltered Track 2 fallback. |
| BR-DRAFT-10-1-045 | 10.1.8.2 | Host card-data format contains only account number and expiration after an equals delimiter | For the enumerated credit transaction types, the host message uses account number, '=' delimiter, and four-digit YYMM expiration date with no further Card Discretionary Data after the date. |
| BR-DRAFT-10-1-046 | 10.1.8.2 | In-flight track retention is temporary and offline transactions send full unaltered tracks | Track 2 may be retained in the In Flight Table only while a transaction is in process and is removed when complete; offline transactions send full unaltered Track 1 and Track 2. |

## Existing Rules Reused by Reference

| Existing Test BR | Disposition |
|---|---|
| BR-RULE-SEG100-R-034 | EXISTING_DRAFT_CHAIN_REUSE_NOT_PROMOTED |
| BR-SEG100-RECEIPT-CARDTYPE-AMEX | EXISTING_DRAFT_CHAIN_REUSE_NOT_PROMOTED |
| BR-SEG100-RECEIPT-CARDTYPE-DEBIT | EXISTING_DRAFT_CHAIN_REUSE_NOT_PROMOTED |
| BR-SEG100-RECEIPT-CARDTYPE-DINERS-CLUB | EXISTING_DRAFT_CHAIN_REUSE_NOT_PROMOTED |
| BR-SEG100-RECEIPT-CARDTYPE-DISCOVER | EXISTING_DRAFT_CHAIN_REUSE_NOT_PROMOTED |
| BR-SEG100-RECEIPT-CARDTYPE-EBT | EXISTING_DRAFT_CHAIN_REUSE_NOT_PROMOTED |
| BR-SEG100-RECEIPT-CARDTYPE-JCB | EXISTING_DRAFT_CHAIN_REUSE_NOT_PROMOTED |
| BR-SEG100-RECEIPT-CARDTYPE-MASTERCARD | EXISTING_DRAFT_CHAIN_REUSE_NOT_PROMOTED |
| BR-SEG100-RECEIPT-CARDTYPE-VISA | EXISTING_DRAFT_CHAIN_REUSE_NOT_PROMOTED |
| BR-SEG100-RECEIPT-CARDTYPE-STAR-SIGNATURE-DEBIT | EXISTING_DRAFT_CHAIN_REUSE_NOT_PROMOTED |
| BR-SEG100-E2-001 | EXISTING_DRAFT_CHAIN_REUSE_NOT_PROMOTED |
| BR-RULE-SEG100-R-020 | RELATED_PARTIAL_APPROVAL_DRAFT_CHAIN_REUSE_NOT_PROMOTED |
| BR-RULE-SEG100-R-039 | RELATED_PARTIAL_APPROVAL_DRAFT_CHAIN_REUSE_NOT_PROMOTED |
| BR-RULE-SEG100-R-065 | RELATED_PARTIAL_APPROVAL_DRAFT_CHAIN_REUSE_NOT_PROMOTED |

## Promotion Gates

1. Review each source quote, BR atomicity, transaction/card/lifecycle applicability, and interactions with related rules.
2. Resolve open source conflicts and card-network context with the authorized SME.
3. Complete converter-ready positive/negative request fixtures and authoritative expected response/receipt/storage oracles.
4. Execute independently, validate mutation isolation and full BR -> TS -> TC -> TD trace, then approve into the independent catalog.
5. Recompute coverage only after promoted Test Solution rules and confirmed Run4 crosswalk decisions are recorded.

No `EXECUTION_READY`, `EXECUTABLE`, `CONFIRMED`, or coverage-credit claim is present in this supplement.
