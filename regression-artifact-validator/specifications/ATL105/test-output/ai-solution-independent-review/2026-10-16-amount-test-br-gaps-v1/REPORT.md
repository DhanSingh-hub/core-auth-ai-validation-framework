# Amount Category: 32 Test BR Gap Review

All 6,880 AI BRs and 1,330 independent Test BRs were available to the retrieval queue. The 32 Test BRs below lack a v3 exact-anchor candidate; that is not proof of semantic non-match.

## Dispositions

| Disposition | Test BRs |
|---|---:|
| PARTIAL_SCOPE_OVERLAP | 5 |
| CLAUSE_MATCH_PROPOSED | 22 |
| POLICY_COUNTERPART_NOT_ESTABLISHED | 2 |
| UNSUPPORTED_TEST_HYPOTHESIS_REVIEW_REQUIRED | 1 |
| SOURCE_CONFLICT_RESOLVED_BY_AUTHORITATIVE_LAYOUT | 1 |
| PARTIAL_SCOPE_OVERLAP_SOURCE_LABEL_CONFLICT | 1 |

Direct proposals are clause-scoped, not full-rule certification. Partial and conflict rows retain their unsupported claims explicitly. All Test chains remain draft.

## SEG100-R-065
Partial approval responses carry an approved amount

Disposition: **PARTIAL_SCOPE_OVERLAP**

AI Response Code F clauses identify a partial approval, but do not by themselves prove that the response carries the approved amount field.

- AI :4726: Data element 83 accepts documented value 'F' — Response Code F indicates approval, partial amount approved. (rank 2; AI cases 0; physical-complete 0; flags ["NO_LINKED_CASE", "PRODUCER_SEGMENT_UNRESOLVED"])
- AI :502: Response Code value F indicates approval, partial amount approved. (rank 6; AI cases 5; physical-complete 5; flags [])
- AI :791: Partially approved transaction responses contain a Response Code (Element No. 83) value of F, indicating partial amount approved. (rank 5; AI cases 5; physical-complete 5; flags [])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG102-R-020
Product Data Field Delimiter follows Quantity and Unit Price

Disposition: **CLAUSE_MATCH_PROPOSED**

AI clauses state that a Product Data Field Delimiter follows Quantity and Unit Price; this is the same serialization obligation.

- AI :1200: A Product Data Field Delimiter always follows Quantity and Unit Price. (rank 2; AI cases 4; physical-complete 4; flags [])
- AI :1571: A Product Data Field Delimiter always follows Quantity and Unit Price. (rank 2; AI cases 4; physical-complete 4; flags [])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG102-R-023
Segment 102 total length does not exceed 381 characters

Disposition: **CLAUSE_MATCH_PROPOSED**

AI :1912 states Segment 102 data length 001-381, matching the Test segment maximum. It does not establish full wire serialization.

- AI :1912: Product Code Data Segment (No. 102) data length ranges 001-381. (rank 4; AI cases 4; physical-complete 4; flags ["ELEMENT_SCOPE_UNRESOLVED"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG102-R-025
Segment 143 product order and count match Segment 102 when present

Disposition: **PARTIAL_SCOPE_OVERLAP**

AI leads establish Segment 143 fields/count in isolation, not that its order and count mirror Segment 102 when both are present.

- AI :3535: In Tax by Product Data Segment (Data Segment No. 143), field 2 (Segment Length) is required and must be present. (rank 2; AI cases 7; physical-complete 6; flags [])
- AI :3536: In Tax by Product Data Segment (Data Segment No. 143), field 3 (Number of Products) is required and must be present. (rank 2; AI cases 6; physical-complete 6; flags [])
- AI :3537: In Tax by Product Data Segment (Data Segment No. 143), field 4 (Product Code) is required and must be present. (rank 2; AI cases 6; physical-complete 6; flags [])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG103-R-006
Field order matches Section 12.4 (Segment Type, Segment Length, Clerk ID, Voucher ID, WIC Discount Amount, WIC Product Data, EBT Program Data)

Disposition: **PARTIAL_SCOPE_OVERLAP**

AI covers WIC Discount Amount presence/length only; it does not establish the seven-field Segment 103 order.

- AI :2160: WIC Discount Amount appears in EBT Data Segment (Segment No. 103). (rank 4; AI cases 3; physical-complete 3; flags [])
- AI :2161: WIC Discount Amount has variable length up to 40 digits. (rank 4; AI cases 3; physical-complete 3; flags [])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG105-R-017
Segment 119 use requires an approved proprietary-load selection rule

Disposition: **POLICY_COUNTERPART_NOT_ESTABLISHED**

This is a certification policy gate for selecting Segment 119, not an ATL105 runtime rule. Preserve it as a policy blocker, not an AI semantic NOT_MATCH.

No defensible AI counterpart selected; retained leads remain retrieval-only.

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG105-R-018
A Totals Request contains Data Sections 1 and 3 only; it has no Data Section 2 and no Segment 100

Disposition: **PARTIAL_SCOPE_OVERLAP**

AI :1034 identifies a Totals Data Segment in Data Section 3; :1040 repeats the erroneous 116 label. The source layout says Data Sections 1 and 3, no Section 2, and identifies Segment 119; no full AI match.

- AI :1034: Totals Request message contains Data Section No. 3 with Totals Data Segment. (rank 1; AI cases 1; physical-complete 1; flags ["ELEMENT_SCOPE_UNRESOLVED"])
- AI :1040: Totals with Proprietary Data Load Request messages contain Element Nos. 55 and 63 plus Data Segment No. 116, but not Data Segment No. 100. (rank NOT_RETAINED_IN_TOP_K; AI cases 5; physical-complete 4; flags ["CLAIMED_ELEMENT_NOT_IN_CASE_REQUEST_METADATA"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG105-R-019
In a Totals Request, Segment 105 is the only data segment and occupies Data Section 3 Field No. 3

Disposition: **CLAUSE_MATCH_PROPOSED**

AI :5880 states Totals Data Segment appears in Data Section 3, Field 3. Confirm the Test rule's sole-segment clause separately from placement.

- AI :5880: Totals Data Segment: Appears in Field No. 3, Data Section No. 3; max 409 alphanumeric characters; originates at device. (rank NOT_RETAINED_IN_TOP_K; AI cases 1; physical-complete 1; flags ["ELEMENT_SCOPE_UNRESOLVED"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG105-R-022
Segment 105 is present in every Totals Request

Disposition: **CLAUSE_MATCH_PROPOSED**

AI :4795 states the Totals Request includes Segment 105. The source defines that segment as required; AI :3698's conditional wording needs separate correction review.

- AI :4795: The Totals Request transaction includes Totals Data Segment (Data Segment No. 105). (rank 1; AI cases 1; physical-complete 1; flags ["ELEMENT_SCOPE_UNRESOLVED"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG111-R-006
Total maximum

Disposition: **CLAUSE_MATCH_PROPOSED**

AI :1247/:1919 state the Variable Information Segment maximum is 999 alphanumeric characters, matching the Test maximum.

- AI :1247: Variable Information Data Segment has a maximum length of 999 alphanumeric characters. (rank NOT_RETAINED_IN_TOP_K; AI cases 6; physical-complete 6; flags ["ELEMENT_SCOPE_UNRESOLVED"])
- AI :1919: Variable Information Data Segment (No. 111) data length ranges 001-999. (rank NOT_RETAINED_IN_TOP_K; AI cases 6; physical-complete 6; flags ["ELEMENT_SCOPE_UNRESOLVED"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG112-R-006
The Additional Information section (elements 116/117/118) repeats once per Additional Information Indicator, up to a maximum of 990 bytes total

Disposition: **CLAUSE_MATCH_PROPOSED**

AI :1254 states the Additional Information section repeats by indicator with a 990-byte maximum, matching Segment 112. Do not transfer Segment 130/131 caps.

- AI :1254: Additional Information section is repeated by Additional Information Indicator for a maximum length of 990 bytes. (rank 6; AI cases 0; physical-complete 0; flags ["NO_LINKED_CASE"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG115-R-012
Segment 115 is plausibly the wire-format vehicle for 'Loyalty Print Data' returned during a Segment 108 Account Inquiry (Update Code I) or Totals Report (Update Code T) flow, inferred from the shared Loyalty Information Version = 2 trigger condition

Disposition: **UNSUPPORTED_TEST_HYPOTHESIS_REVIEW_REQUIRED**

The Test title itself says 'plausibly' and infers Segment 115 from a shared version trigger. AI chain leads do not establish source authority for this wire-format vehicle; hold until the source/layout and SME interpretation settle it.

- AI :1149: If Element 115 indicates Print Data Segment and Print Data 2 Segment follow, and loyalty Version Number in request is '2', segments follow in Field 17/18/19. (rank 2; AI cases 0; physical-complete 0; flags ["NO_LINKED_CASE"])
- AI :1019: If Element 115 indicates Print Data Segment (115) follows and loyalty Version Number in request is '2', it appears in Field No. 17/18/19. (rank 2; AI cases 0; physical-complete 0; flags ["NO_LINKED_CASE"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG116-R-009
Section 11.4.1.2 labels 'Totals with Proprietary Data Load Request' as '(Data Segment No. 116)'; this is a source inconsistency. The authoritative Element 85 Segment Type valid-codes table and Section 12.17 both confirm that segment is actually Data Segment No. 119 ('Totals with Proprietary Data Load Data Segment'). Segment 116 is reserved exclusively for the TransArmor Load Data Segment; do not use the Section 11.4.1.2 label as evidence of Segment 116 content

Disposition: **SOURCE_CONFLICT_RESOLVED_BY_AUTHORITATIVE_LAYOUT**

Section 11.4.1.2 labels the request as Segment 116, but its layout/table and Section 12.17 identify Segment 119; Element 85 identifies 116 as TransArmor. Under source precedence, correct the misleading label and retain the conflict note; AI :1040's 116 claim is not coverage.

- AI :1040: Totals with Proprietary Data Load Request messages contain Element Nos. 55 and 63 plus Data Segment No. 116, but not Data Segment No. 100. (rank NOT_RETAINED_IN_TOP_K; AI cases 5; physical-complete 4; flags ["CLAIMED_ELEMENT_NOT_IN_CASE_REQUEST_METADATA"])
- AI :1924: TransArmor Load Data Segment (No. 116) data length ranges 01-50. (rank 8; AI cases 1; physical-complete 1; flags ["ELEMENT_SCOPE_UNRESOLVED"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG118-R-001
Proprietary Data Load Segment requests or carries Customer Specific Proprietary data for loading (host-to-device push: Dynamic Card Table, Custom Receipt Text, Host Discounts) or capturing (device-to-host push: Site Configuration, Fuel Volume) at the BUYPASS FEP; functionally similar to Electronic Mail

Disposition: **CLAUSE_MATCH_PROPOSED**

AI clauses describe proprietary data load as request/capture and split host-pushed vs device-pushed data categories, matching the Test purpose at clause level.

- AI :5895: Proprietary Data Load Segment: Requests or captures customer specific proprietary data for loading at BUYPASS FEP. (rank 1; AI cases 1; physical-complete 1; flags ["ELEMENT_SCOPE_UNRESOLVED"])
- AI :1272: Dynamic Card Table, Custom Receipt Text, and Host Discounts are pushed by host to POS. (rank 4; AI cases 1; physical-complete 1; flags ["ELEMENT_SCOPE_UNRESOLVED"])
- AI :1273: Site Configuration data and Fuel Volume data are driven by the POS in a data push to the host. (rank 1; AI cases 1; physical-complete 1; flags ["ELEMENT_SCOPE_UNRESOLVED"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG118-R-030
Response Code (Element 83) values V (Declined, Totals with Proprietary Custom Receipt Text data pending) and W (Approved, Totals with Proprietary Custom Receipt Text pending) indicate that a Prompt Code 901 (Custom Receipt Text) load is pending, analogous to the Host Discount D/E/M/N pattern

Disposition: **CLAUSE_MATCH_PROPOSED**

AI clauses state Response Code V/W meaning for pending Custom Receipt Text; this directly overlaps the Test trigger, while the full response family remains separate.

- AI :1904: Response Code 'W' means Approved-Totals with Proprietary Custom Receipt Text pending. (rank 1; AI cases 0; physical-complete 0; flags ["NO_LINKED_CASE", "PRODUCER_SEGMENT_UNRESOLVED"])
- AI :5033: Data element ENT-ELEM-83 accepts documented value 'V' — Response Code V indicates Declined-Totals with Proprietary Custom Receipt Text data pending. (rank 1; AI cases 0; physical-complete 0; flags ["NO_LINKED_CASE", "PRODUCER_SEGMENT_UNRESOLVED"])
- AI :5034: Data element ENT-ELEM-83 accepts documented value 'W' — Response Code W indicates Approved-Totals with Proprietary Custom Receipt Text pending. (rank 1; AI cases 0; physical-complete 0; flags ["NO_LINKED_CASE", "PRODUCER_SEGMENT_UNRESOLVED"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG119-R-001
Totals-with-proprietary-load request contains Data Sections 1 and 3 but not Data Section 2

Disposition: **PARTIAL_SCOPE_OVERLAP_SOURCE_LABEL_CONFLICT**

AI :1043 covers Segment 119 placement in Data Section 3 Field 3; AI :1040 repeats the conflicting 116 label. The source supports Sections 1 and 3/no Section 2 but not the erroneous 116 identity.

- AI :1040: Totals with Proprietary Data Load Request messages contain Element Nos. 55 and 63 plus Data Segment No. 116, but not Data Segment No. 100. (rank 2; AI cases 5; physical-complete 4; flags ["CLAIMED_ELEMENT_NOT_IN_CASE_REQUEST_METADATA"])
- AI :1043: All Totals Request messages contain the Totals with Proprietary Data Load Data Segment in Field No. 3. (rank 1; AI cases 2; physical-complete 2; flags ["ELEMENT_SCOPE_UNRESOLVED"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG119-R-002
Segment 119 is sent only when the transaction requires totals data

Disposition: **CLAUSE_MATCH_PROPOSED**

AI :1044 states Segment 119 is sent only when totals data is required, matching the Test condition.

- AI :1044: Totals with Proprietary Data Load Data Segment is sent only on transactions requiring totals data. (rank 1; AI cases 2; physical-complete 2; flags ["ELEMENT_SCOPE_UNRESOLVED"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG119-R-030
Non-financial card categories marked with * are excluded from Grand Total dollar aggregation

Disposition: **CLAUSE_MATCH_PROPOSED**

AI :1328 states that asterisk-marked non-financial card categories are excluded from Grand Total; :440 summarizes non-financial exclusions.

- AI :1328: Card categories marked with * are considered non-financial; their dollar amounts are not included in the Grand Total. (rank 1; AI cases 3; physical-complete 3; flags [])
- AI :440: Grand total excludes non-financial transactions: AO, SV1, SV3, SV4, HD categories. (rank 2; AI cases 3; physical-complete 3; flags [])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG119-R-034
Retry and duplicate Totals Request behavior is approved before certification

Disposition: **POLICY_COUNTERPART_NOT_ESTABLISHED**

Retry/duplicate behavior is an open Segment 119 policy/SME gate; generic transaction retry rules are not an approved totals policy.

No defensible AI counterpart selected; retained leads remain retrieval-only.

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG120-R-004
Print Data must not exceed 999 characters AND total serialized Segment 120 length (Segment Type + Segment Length + Print Data + separators) must not exceed 1,009 alphanumeric characters -- both caps enforced independently

Disposition: **PARTIAL_SCOPE_OVERLAP**

AI :1927 states Segment 120 total length 001-1009, covering the segment cap. The separate 999-character Print Data cap is not established by that clause; :178 has a different unresolved field scope.

- AI :1927: Print Data 2 Segment (No. 120) data length ranges 001-1009. (rank 4; AI cases 0; physical-complete 0; flags ["NO_LINKED_CASE", "ELEMENT_SCOPE_UNRESOLVED"])
- AI :178: Print Data must be alphanumeric with special characters and must not exceed 900 bytes. (rank 4; AI cases 0; physical-complete 0; flags ["NO_LINKED_CASE"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG130-R-011
The EMV Additional Information Section (fields 7-9: Indicator, Length, Information) repeats per EMV Additional Information Indicator for a maximum total length of 2,000 bytes

Disposition: **CLAUSE_MATCH_PROPOSED**

AI :1349 states Segment 130 EMV Additional Information repeats by indicator with a 2,000-byte cap; keep this distinct from Segment 112/131.

- AI :1349: EMV Additional Information Section repeats by EMV Additional Information Indicator, max 2,000 bytes. (rank 2; AI cases 0; physical-complete 0; flags ["NO_LINKED_CASE"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG131-R-009
The EMV Additional Information Section (fields 6-8: Indicator, Length, Information) repeats per EMV Additional Information Indicator for a maximum total length of 2,800 bytes — a DIFFERENT cap than Segment 130's 2,000-byte limit for the structurally identical section

Disposition: **CLAUSE_MATCH_PROPOSED**

AI :1354 states Segment 131 EMV Additional Information repeats by indicator with a 2,800-byte cap, distinct from Segment 130.

- AI :1354: EMV Additional Information Section is repeated by EMV Additional Information Indicator for a maximum length of 2,800 bytes. (rank 3; AI cases 0; physical-complete 0; flags ["NO_LINKED_CASE"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG139-R-001
Segment 139 is used to retrieve debit totals for the previous period, to be used in the day end batch close transaction (Segment 141)

Disposition: **CLAUSE_MATCH_PROPOSED**

AI :1371 states Segment 139 retrieves previous-period debit totals for the Segment 141 day-end batch close, matching the purpose clause.

- AI :1371: Purpose of segment 139 is to retrieve debit totals for previous period used in day end batch close transaction. (rank 1; AI cases 1; physical-complete 1; flags ["ELEMENT_SCOPE_UNRESOLVED"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG140-R-001
Segment 140 consists of a reiteration of the first 6 fields in the Segment 139 request, followed by a text response from Moneris, followed by the total of all debit transactions for this batch

Disposition: **CLAUSE_MATCH_PROPOSED**

AI clauses state that Segment 140 repeats the first six request fields, then Moneris text and debit total; same response composition.

- AI :1373: Segment consists of first 6 fields from request, then text response from Moneris, then total debit transactions. (rank 1; AI cases 0; physical-complete 0; flags ["NO_LINKED_CASE", "ELEMENT_SCOPE_UNRESOLVED"])
- AI :1377: Response to Moneris Day End Batch Close Request reiterates first 6 fields of request followed by text response from Moneris (rank 2; AI cases 0; physical-complete 0; flags ["NO_LINKED_CASE", "ELEMENT_SCOPE_UNRESOLVED"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG143-R-008
A dual-delimiter scheme is used: a Field Separator ('▲') follows the LAST tax amount for each product and signals moving to the next product (or end of segment for the last product); a Tax by Product Field Delimiter ('\') follows a tax amount when there is ANOTHER tax amount for the SAME product

Disposition: **CLAUSE_MATCH_PROPOSED**

AI clauses distinguish the tax delimiter within one product from the Field Separator between products/end of segment.

- AI :1399: Use '\' delimiter if there is another tax amount for this product, otherwise '▲' and continue to next product. (rank 2; AI cases 6; physical-complete 5; flags [])
- AI :1400: '▲' continues to next product; if last product, '▲' signifies end of segment. (rank 2; AI cases 2; physical-complete 2; flags [])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG143-R-009
A merchant may end a product's tax entry early (before 3 taxes) by using a Field Separator after the last reported tax entry instead of the '\' delimiter

Disposition: **CLAUSE_MATCH_PROPOSED**

AI :1381 states fewer than three taxes may terminate early with a Field Separator, matching the Test clause.

- AI :1381: If fewer than three taxes used, entry can end early using field separator instead of tax delimiter. (rank 1; AI cases 2; physical-complete 2; flags ["ELEMENT_SCOPE_UNRESOLVED"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG149-R-001
Segment 149 contains data required by the Comdata authorizer for fuel price updates

Disposition: **CLAUSE_MATCH_PROPOSED**

AI clauses identify Segment 149 Price Data as required and contain Comdata fuel-price values, matching the authorizer-specific data purpose.

- AI :3563: In Fuel Price Update Request Segment (Data Segment No. 149), field 4 (Price Data) is required and must be present. (rank 2; AI cases 0; physical-complete 0; flags ["NO_LINKED_CASE"])
- AI :2351: Price Data element is unique to the Fuel Price Update Request Segment (No 149) and contains Cash and Credit unit prices for Diesel #2 fuel. (rank 2; AI cases 0; physical-complete 0; flags ["NO_LINKED_CASE"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG149-R-002
Field Separators appear between fields 1-2, 2-3, and 3-4; the segment should end with a trailing Field Separator; data fields within field 4 (Price Data) are NOT separated by Field Separators

Disposition: **CLAUSE_MATCH_PROPOSED**

AI clauses state the three separators between fields 1-4 and that field 4 internal values are not Field-Separator-delimited; same serialization rule.

- AI :1475: Segment 149 fields 1-2, 2-3, 3-4 are separated by Field Separators; segment ends with a field separator. (rank 4; AI cases 0; physical-complete 0; flags ["NO_LINKED_CASE", "ELEMENT_SCOPE_UNRESOLVED"])
- AI :1476: Data fields contained within field 4 (Price Data) are not separated by Field Separators. (rank 2; AI cases 0; physical-complete 0; flags ["NO_LINKED_CASE"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG150-R-001
Segment 150 contains an indication of success or failure of the Segment 149 Fuel Price Update Request

Disposition: **CLAUSE_MATCH_PROPOSED**

AI :5968 states the Fuel Price Update Response indicates success or failure of the request, matching the Test outcome clause.

- AI :5968: Fuel Price Update Response Segment: Indicates success or failure of the Fuel Price Update Request. (rank 1; AI cases 0; physical-complete 0; flags ["NO_LINKED_CASE", "ELEMENT_SCOPE_UNRESOLVED"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG156-R-004
Time-based sub-tables (02 Total Time Plugged In, 03 Total Charging Time, 04 Start Time of Charge, 05 Finish Time of Charge) all use fixed 6-character hhmmss format, hh 00-99, mm/ss 00-59

Disposition: **CLAUSE_MATCH_PROPOSED**

AI clauses state the hhmmss format and bounds for the named EV time tables; test each table independently.

- AI :1535: Total Time Plugged In Table Data format hhmmss: hh 00-99, mm 00-59, ss 00-59. (rank 1; AI cases 3; physical-complete 3; flags ["ELEMENT_SCOPE_UNRESOLVED"])
- AI :1541: Finish Time of Charge format is hhmmss with hh 00-99, mm 00-59, ss 00-59. (rank 1; AI cases 3; physical-complete 3; flags ["ELEMENT_SCOPE_UNRESOLVED"])
- AI :5993: Total Time Plugged In: EV charging sub-element, Table ID 02, holds total time vehicle plugged in as hhmmss. (rank 1; AI cases 3; physical-complete 3; flags ["ELEMENT_SCOPE_UNRESOLVED"])
- AI :5996: Finish Time of Charge: Table ID 05, fixed length 006, finish time of charge in hhmmss local time format. (rank 1; AI cases 3; physical-complete 3; flags ["ELEMENT_SCOPE_UNRESOLVED"])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG157-R-006
Tax, discount, and coupon amounts are already accounted for in the individual Adjusted Product Amounts; separate tax/discount/coupon product codes must NOT be included in the segment

Disposition: **CLAUSE_MATCH_PROPOSED**

AI clauses state tax/discount/coupon values are included in adjusted product amounts and excluded as separate products.

- AI :1564: Discount, Tax, and Coupon amounts are accounted for in product amounts; no separate discount/tax/coupon codes included. (rank 1; AI cases 0; physical-complete 0; flags ["NO_LINKED_CASE", "ELEMENT_SCOPE_UNRESOLVED"])
- AI :1586: When adjusted product amounts are used, there is no separate Tax product included in the segment. (rank 5; AI cases 4; physical-complete 4; flags [])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## SEG157-R-008
A dual-delimiter scheme is used: Field Separator ('▲') follows Segment Type/Length and follows Adjusted Product Amount only when it is the LAST element in the segment; a Product Data Field Delimiter ('\') always follows Quantity and Unit Price, and follows Adjusted Product Amount when it is NOT the last element

Disposition: **CLAUSE_MATCH_PROPOSED**

AI clauses identify the Field Separator vs Product Data Field Delimiter by product position and field, matching the dual-delimiter scheme.

- AI :1569: A Field Separator follows Adjusted Product Amount when it is the very last element in the segment. (rank 1; AI cases 0; physical-complete 0; flags ["NO_LINKED_CASE", "ELEMENT_SCOPE_UNRESOLVED"])
- AI :1572: A Product Data Field Delimiter follows Adjusted Product Amount when it is not the last product. (rank 1; AI cases 0; physical-complete 0; flags ["NO_LINKED_CASE", "ELEMENT_SCOPE_UNRESOLVED"])
- AI :1200: A Product Data Field Delimiter always follows Quantity and Unit Price. (rank 4; AI cases 4; physical-complete 4; flags [])
- AI :1571: A Product Data Field Delimiter always follows Quantity and Unit Price. (rank 4; AI cases 4; physical-complete 4; flags [])

Test chain: DRAFT_REVIEW_REQUIRED; TS/TC/TD 1/1/1; execution NOT_EXECUTED.

## Key Source Resolution

Section 11.4.1.2 contains a `Data Segment No. 116` label, but Section 12.17 describes Segment 119 and Element 85 identifies 116 as TransArmor. Per the source-precedence rule, the authoritative layout/table resolves the request segment as 119; AI :1040's 116 use is not counted as a match.

The attached Authorization request is not evidence for Totals, PDL, Moneris, EMV response, Tax-by-Product, Fuel Price Update or Adjusted Product Code flows. No card/track values were ingested.

All proposed relations remain `NOT_ASSESSED`; the Test solution is independent and all 32 Test BRs remain `DRAFT_REVIEW_REQUIRED`. No SME approval or execution is claimed.
