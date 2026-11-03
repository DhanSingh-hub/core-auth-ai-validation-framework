# Account/Card 283-BR Cohort Review

All 283 frozen AI gap BRs are retained. The retrieval search considered all 1330 independent Test package records, across all categories, not just the seven Account/Card Test gaps. Candidate IDs resolve to BR-RULE catalog aliases or authored IDs.

## Results

| Preliminary disposition | AI BRs |
|---|---:|
| PARTIAL_SCOPE_OVERLAP | 147 |
| CONFLICT_REVIEW_REQUIRED | 2 |
| CLAUSE_MATCH_PROPOSED | 49 |
| COUNTERPART_NOT_ESTABLISHED | 85 |

These are clause-level proposals, not authorized semantic decisions. A narrow AI clause may overlap part of a broader Test BR; no full Test BR or chain coverage credit is granted. Partial relations explicitly identify unsupported clauses.

All cohort statements were read; 198 receive explicit curated relations. Counterpart-not-established rows remain unresolved, not NOT_MATCH. Top-K retrieval and this title/clause review are not exhaustive semantic adjudication of every AI/Test combination.

## Artifact Evidence

1234 unique linked AI cases; 1234 case screens; 2804 unique current payloads checked against frozen observation hashes.

Current payload hash states: {"CURRENT_HASH_MATCH": 2398, "MISSING_OR_HASH_DISAGREEMENT_REVIEW_REQUIRED": 406}

Cases with metadata/payload field disagreements: 0. Negative/boundary intent must be reviewed before calling a disagreement a defect. Multi-leg step bindings, response-only claims and aliases remain unresolved where flagged.

The frozen 283 membership is unchanged. Earlier v9 chain counts and the newer queue's case associations may differ; both are retained. Physical presence does not prove BR intent, an independent expected result, wire validity, or host behavior.

Sources and hashes are in summary.json; full AI/Test chains, value-free payload observations, field flags, candidate pairs and curated proposed relations are included. Transaction card/track values from the attached authorization were not ingested or published.

## Authority and Open Gates

Use authoritative ATL105 source when it resolves a conflict; otherwise Test BRs control conflicting AI interpretations. Existing draft/Test labels do not authorize mappings. Source quotes/complete claim interpretation, full scenario/case intent, independent outcomes, executable Test fixtures and wire/host evidence remain open. No Test or host execution occurred.

## Per-AI Review

## REQ-SRC-ATL105-PDF-001:1021
Loyalty Card Transaction Request Data Section No. 2 must contain the Standard Message Data Segment.

Disposition: PARTIAL_SCOPE_OVERLAP

Test supports Segment 100 in loyalty requests; exact Data Section 2 placement is not stated in these Test titles.

Selected Test BRs: BR-RULE-SEG100-R-001;BR-RULE-SEG108-R-025

Ranked retrieval only:
- BR-TXV-ACCOUNT-UPDATE: Account update
- BR-TXV-ACCOUNT-INQUIRY: Account inquiry
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1022
Loyalty Card Transaction Request Data Section No. 1 contains Message Format Version Identifier and Number of Segments.

Disposition: PARTIAL_SCOPE_OVERLAP

Test covers format identity and segment count, not the entire named message-family/field-position assertion.

Selected Test BRs: BR-RULE-SEG100-R-013;BR-RULE-SEG100-R-007

Ranked retrieval only:
- BR-RULE-SEG109-R-002: Data Section 1 contains required Message Format Version Identifier (Element 55) and Number of Segments (Element 63), separated and followed by a Field Separator
- BR-SEG100-E55-001: Element 55 Message Format Version Identifier must be ATL105.
- BR-RULE-SEG100-R-013: Message format identifier is ATL105

Artifact review flags: ["CLAIMED_ELEMENT_NOT_IN_CASE_REQUEST_METADATA", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1024
Standard Message Data Segment is the only segment required for all loyalty card transactions.

Disposition: CONFLICT_REVIEW_REQUIRED

AI says Segment 100 is the only required loyalty segment, while Test requires Segment 108 as well. Do not treat only-required wording as equivalent; verify source context.

Selected Test BRs: BR-RULE-SEG108-R-002;BR-RULE-SEG108-R-025;BR-RULE-SEG100-R-001

Ranked retrieval only:
- BR-TXV-ACCOUNT-UPDATE: Account update
- BR-TXV-ACCOUNT-INQUIRY: Account inquiry
- BR-TXV-ADD-ACCOUNT: Add account

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1047
Fields 7, 8, and 9 are sent 18 times, once per card type, in the order shown.

Disposition: PARTIAL_SCOPE_OVERLAP

Aggregate totals fields are related, but the 18-card repetition, order and data-occurs condition are not specified by this Test BR.

Selected Test BRs: BR-RULE-SEG105-R-015

Ranked retrieval only:
- BR-RULE-SEG119-R-026: Card Type Total Amount is required for each card bucket
- BR-RULE-SEG119-R-025: Card Type Total Count is required for each card bucket
- BR-RULE-SEG112-R-006: The Additional Information section (elements 116/117/118) repeats once per Additional Information Indicator, up to a maximum of 990 bytes total

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1050
Card Labels (in parentheses) should be used instead of field numbers to identify total buckets.

Disposition: PARTIAL_SCOPE_OVERLAP

Card Label fields are covered, but label-instead-of-field-number display behavior or journal-derived local totals is not established.

Selected Test BRs: BR-RULE-SEG105-R-015;BR-RULE-SEG119-R-024

Ranked retrieval only:
- BR-RULE-SEG119-R-024: Card Label is required for each card bucket
- BR-RULE-SEG105-R-015: Grand Total, Card Label, Card Type Total Count, and Card Type Total Amount are required
- BR-RULE-SEG119-R-026: Card Type Total Amount is required for each card bucket

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1124
Data Section No. 3 may include one or more of: Fleet, Product Code, Purchase Card, Variable Information, EMV Request Data Segments.

Disposition: PARTIAL_SCOPE_OVERLAP

An allowed-segment list is not equivalent to requiring every applicable companion; permission and obligation differ.

Selected Test BRs: BR-RULE-SEG100-R-005

Ranked retrieval only:
- BR-SEG100-EMV-001: An EMV purchase must include Segment 130 with Segment 100.
- BR-SEG100-APPR-EMV-COMPANION: An EMV Segment 100 transaction shall include Segment 130 EMV Request Data in Data Section 3.
- BR-RULE-SEG100-R-053: Variable-information flows include Segment 111

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1126
This segment is required for all EMV card transactions.

Disposition: PARTIAL_SCOPE_OVERLAP

Test requires Segment 130 for authorization-class EMV requests and waives reversal data; AI all-EMV wording needs scope review.

Selected Test BRs: BR-RULE-SEG130-R-001;BR-RULE-SEG130-R-017

Ranked retrieval only:
- BR-SEG100-ID-001: Segment Type must be 100 for the Standard Message Data Segment.
- BR-APPG-CODE-0: Appendix G transaction type 0 is recognized as STANDARD_FINANCIAL.
- BR-APPG-CODE-3: Appendix G transaction type 3 is recognized as STANDARD_FINANCIAL.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1130
Purchase Card Data Segment is sent only on transactions requiring purchase card data.

Disposition: PARTIAL_SCOPE_OVERLAP

Same purchase-card companion topic, but AI only-when and Test requiring-when-needed implications are not logically identical.

Selected Test BRs: BR-RULE-SEG100-R-052

Ranked retrieval only:
- BR-RULE-SEG100-R-052: Purchase-card flows requiring purchase-card data include Segment 104
- BR-SEG100-GAP-PURCHASE-CARD: A purchase-card flow requiring purchase-card data shall include Segment 104 in addition to Segment 100.
- BR-SEG100-FLEET-001: A fleet transaction requiring fleet data must include Segment 101 with Segment 100.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1133
EMV Request Data Segment is required for all EMV card transactions and is the only segment required for all EMV financial transactions.

Disposition: PARTIAL_SCOPE_OVERLAP

Test requires Segment 130 for authorization-class EMV requests and waives reversal data; AI all-EMV wording needs scope review.

Selected Test BRs: BR-RULE-SEG130-R-001;BR-RULE-SEG130-R-017

Ranked retrieval only:
- BR-RULE-SEG130-R-001: Segment 130 is Required in every EMV authorization-class Financial Transaction Request; the Section 11.8.1 Data Section 3 table marks it R with the note 'This segment is the only segment required for all EMV financial transactions'
- BR-SEG100-APPR-EMV-COMPANION: An EMV Segment 100 transaction shall include Segment 130 EMV Request Data in Data Section 3.
- BR-RULE-SEG130-R-012: Element 118 is reused across segments: it is 'Additional Information' in Segment 112 (max 984, single occurrence) and 'EMV Additional Information' in Segment 130 (variable length, repeating). Do not assume element numbers are globally unique or that the two fields share business meaning

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1136
All EMV Financial Transaction Requests contain one or more of these data segments in Field Nos. 4-8.

Disposition: PARTIAL_SCOPE_OVERLAP

Same five-slot EMV Data Section 3 context; AI one-or-more wording does not establish the Test companion maximum.

Selected Test BRs: BR-RULE-SEG130-R-018

Ranked retrieval only:
- BR-RULE-SEG145-R-006: Enhanced Fleet Data (Element 239) is required, max 999 characters, containing one or more sub-segments in <tag><len><data> format, cataloged as: Table 001 (Request Flags, incl. Commercial/Retail Flag), Table 002 (Non-Fuel Product Data, product-category-coded, '|'-delimited repeating), Table 004 (Prompt Data, authorizer-specific prompt tokens, '|'-delimited repeating), Table 006 (Money Code Payee Name), Table 007 (Money Code Check Number, required for all Money Code transactions), Table 008 (Cash Advance Limit)
- BR-SEG100-APPE-FINANCIAL-CARD-TYPES: Financial Segment 100 Prompt Codes shall use one of the 36 Appendix E card types valid for financial transaction Prompt Codes.
- BR-RULE-SEG146-R-002: Enhanced Fleet Data (Element 239) is required, max 999 characters, cataloged as: Table 001 (Response Flags, incl. Settlement Indicator: CP/DB/DF/RC/RF/FN), Table 002 (Non-Fuel Product Limits, NOT present in Comdata responses), Table 003 (Fuel Product Limits, using Appendix F product codes), Table 004 (Prompt Formats, using documented edit masks), Table 005 (Customer Information: name/city/state/account code), Table 010 (Additional Response Data: FNAM/LNAM/ATHN/ACCT/DMSG)

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1156
For EMV, the first two characters of Terminal Identifier must be '+*' regardless of actual device type.

Disposition: PARTIAL_SCOPE_OVERLAP

Test explicitly states +* in CAPK-load Terminal Identifier context, not necessarily every EMV request context asserted by AI.

Selected Test BRs: BR-RULE-SEG132-R-006;BR-RULE-SEG100-R-017

Ranked retrieval only:
- BR-SEG100-E102-001: Terminal Identifier is 1-22 alphanumeric characters for a financial flow.
- BR-RULE-SEG132-R-006: Terminal Identifier (Element 102) is required, 13 characters; for EMV, the first two characters (Device Type) must be '+*' regardless of the actual device type
- BR-SEG100-E102-002: Terminal Identifier is at most 13 characters for load flows.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1173
Merchants using Real Time Account Updater should refer to Account Number - Data Element 2 for additional information.

Disposition: PARTIAL_SCOPE_OVERLAP

Reference-only Account Updater guidance is related, not an atomic rule equivalent to a specific field constraint.

Selected Test BRs: BR-RULE-SEG155-R-003;BR-RULE-SEG100-R-041;BR-RULE-SEG100-R-042

Ranked retrieval only:
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.
- BR-SEG100-E2-002: Tokenized or TransArmor account representation must be explicit.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1174
Merchants using Real Time Account Updater should refer to Card Discretionary Block Data - Data Element 12 for additional information.

Disposition: PARTIAL_SCOPE_OVERLAP

Reference-only Account Updater guidance is related, not an atomic rule equivalent to a specific field constraint.

Selected Test BRs: BR-RULE-SEG155-R-003;BR-RULE-SEG100-R-041;BR-RULE-SEG100-R-042

Ranked retrieval only:
- BR-RULE-SEG100-R-042: Card Discretionary Block Data follows conditional source rules
- BR-SEG100-GAP-DISCRETIONARY: Card Discretionary Block Data shall be validated to its source-defined conditional length and usage.
- BR-SEG100-APPD-ALPHA-STATE: Alphabetical state codes used in Card Discretionary Block Data or state context shall use an Appendix D alphabetical code.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1179
The Fleet Data Segment is included in all fleet card transaction requests.

Disposition: CLAUSE_MATCH_PROPOSED

Both require Segment 101 for fleet-card financial requests; enhanced-fleet exceptions still require source/context review.

Selected Test BRs: BR-RULE-SEG101-R-002

Ranked retrieval only:
- BR-SEG101-CORE-002: Fleet Data Segment is required in every fleet-card financial transaction request.
- BR-RULE-SEG101-R-002: Segment 101 is required in every fleet-card financial transaction request
- BR-SEG100-FLEET-001: A fleet transaction requiring fleet data must include Segment 101 with Segment 100.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1217
Purchase Card Data Segment has a maximum length of 86 alphanumeric characters.

Disposition: CLAUSE_MATCH_PROPOSED

Same Segment 104 maximum of 86; the Test BR establishes the maximum, not every lower-bound value or wire encoding.

Selected Test BRs: BR-RULE-SEG104-R-006

Ranked retrieval only:
- BR-SEG104-MAX-LENGTH: The Purchase Card Data Segment has a maximum length of 86 alphanumeric characters.
- BR-RULE-SEG104-R-006: Purchase Card Data Segment max length is 86 alphanumeric characters.
- BR-RULE-SEG104-R-007: Purchase Code is alphanumeric, max 16 characters, when populated.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1225
Field Nos. 15-17 are sent up to a maximum of 18 times, once for each listed card type.

Disposition: PARTIAL_SCOPE_OVERLAP

Aggregate totals fields are related, but the 18-card repetition, order and data-occurs condition are not specified by this Test BR.

Selected Test BRs: BR-RULE-SEG105-R-015

Ranked retrieval only:
- BR-RULE-SEG119-R-025: Card Type Total Count is required for each card bucket
- BR-RULE-SEG119-R-026: Card Type Total Amount is required for each card bucket
- BR-RULE-SEG105-R-021: Segment 105 maximum length is 409 characters

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1226
Category Total Data Section fields are sent only when data occurs for a card type.

Disposition: PARTIAL_SCOPE_OVERLAP

Aggregate totals fields are related, but the 18-card repetition, order and data-occurs condition are not specified by this Test BR.

Selected Test BRs: BR-RULE-SEG105-R-015

Ranked retrieval only:
- BR-RULE-SEG119-R-026: Card Type Total Amount is required for each card bucket
- BR-RULE-SEG119-R-025: Card Type Total Count is required for each card bucket
- BR-RULE-SEG105-R-018: A Totals Request contains Data Sections 1 and 3 only; it has no Data Section 2 and no Segment 100

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1227
Card Labels in parentheses should be used instead of field numbers to identify total buckets.

Disposition: PARTIAL_SCOPE_OVERLAP

Card Label fields are covered, but label-instead-of-field-number display behavior or journal-derived local totals is not established.

Selected Test BRs: BR-RULE-SEG105-R-015;BR-RULE-SEG119-R-024

Ranked retrieval only:
- BR-RULE-SEG119-R-024: Card Label is required for each card bucket
- BR-RULE-SEG105-R-015: Grand Total, Card Label, Card Type Total Count, and Card Type Total Amount are required
- BR-RULE-SEG119-R-026: Card Type Total Amount is required for each card bucket

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1231
Loyalty Card Data Segment has a maximum length of 142 alphanumeric characters.

Disposition: CLAUSE_MATCH_PROPOSED

Same Segment 108 maximum of 142; serialization and minimum-length claims remain separate.

Selected Test BRs: BR-RULE-SEG108-R-005

Ranked retrieval only:
- BR-SEG108-CORE-005: Segment 108 maximum length is 142 alphanumeric characters.
- BR-RULE-SEG108-R-005: Segment 108 maximum length is 142 alphanumeric characters
- BR-SEG104-MAX-LENGTH: The Purchase Card Data Segment has a maximum length of 86 alphanumeric characters.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1232
Expiration Date is reserved for future use.

Disposition: CONFLICT_REVIEW_REQUIRED

AI calls Expiration Date reserved for future use while Test gives MMYY/default-1249 behavior. Resolve source/version/scope before matching.

Selected Test BRs: BR-RULE-SEG108-R-016

Ranked retrieval only:
- BR-RULE-SEG108-R-016: Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)
- BR-TXV-EXPIRATION-UPDATE: Expiration date update

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1233
Payment Tender Type is used to determine whether the transaction is a multiple-card transaction involving a loyalty card.

Disposition: PARTIAL_SCOPE_OVERLAP

Tender field/allowed codes are related; two-card behavior when tender is not CS is not covered by this Test title.

Selected Test BRs: BR-RULE-SEG108-R-017

Ranked retrieval only:
- BR-SEG108-FIELD-017: Payment Tender Type is required and one of the documented values.
- BR-RULE-SEG108-R-017: Payment Tender Type is required, exactly 2 alphanumeric characters from the documented set AX, CK, CS, DB, DN, DS, EB, EC, FL, GC, JC, MC, PC, PR, VS
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1272
Dynamic Card Table, Custom Receipt Text, and Host Discounts are pushed by host to POS.

Disposition: CLAUSE_MATCH_PROPOSED

Same host-to-device Dynamic Card Table, Custom Receipt Text and Host Discount categories.

Selected Test BRs: BR-RULE-SEG118-R-001

Ranked retrieval only:
- BR-RULE-SEG118-R-030: Response Code (Element 83) values V (Declined, Totals with Proprietary Custom Receipt Text data pending) and W (Approved, Totals with Proprietary Custom Receipt Text pending) indicate that a Prompt Code 901 (Custom Receipt Text) load is pending, analogous to the Host Discount D/E/M/N pattern
- BR-RULE-SEG118-R-012: Prompt Code, Pending (Element 182) is Required, field 7, 4 digits, and must be one of the documented values: 0901 (Custom Receipt Text Data), 0902 (Proprietary Data Load), 0904 (Host Discount Data), 0981 (Electronic Mail)
- BR-RULE-SEG118-R-011: Prompt Code (Element 78) is Required, field 6, 3 digits, and must be one of the five documented values: 901 (Custom receipt text), 902 (Dynamic card table), 903 (Site configuration data), 904 (Host discount data), 905 (Fuel volume data), per Appendix E's Valid Card Type Codes Used in Special Transaction Prompt Codes

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1282
Card Table Load Version value comes from Host and is echoed back in each subsequent block request.

Disposition: CLAUSE_MATCH_PROPOSED

Same host-sourced Card Table Load Version echoed in subsequent block requests.

Selected Test BRs: BR-RULE-SEG118-R-014

Ranked retrieval only:
- BR-RULE-SEG118-R-014: Card Table Load Version (Element 177) is Required, field 9, 35 numeric digits, representing the versions of the Card Table components currently being loaded; sourced from the Host and echoed back in each subsequent block request
- BR-RULE-SEG118-R-016: Load Control Key (Element 178) is Required, field 11, 60 characters, sourced from the Host and echoed back in each subsequent block request
- BR-RULE-SEG118-R-018: Block Number (Element 11) is Conditional, field 13, 3 digits, identifying the specific block being sent by device or Host; the Host uses this value to determine the next block to send, and it is echoed back in the subsequent block request

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1283
Card Table Type value comes from host and is echoed back in each subsequent block request.

Disposition: CLAUSE_MATCH_PROPOSED

Same host-sourced Card Table Type echoed in subsequent block requests.

Selected Test BRs: BR-RULE-SEG118-R-015

Ranked retrieval only:
- BR-RULE-SEG118-R-015: Card Table Type (Element 174) is Required, field 10, 4 digits, identifying the specific table type this segment addresses, sourced from the Host and echoed back; valid values are 0001 (BIN Table), 0002 (RULES Table), 0003 (RESTRICTIONS Table), 0004 (SAF Table), 0005 (PROMPT Table), 0006 (PRODUCT Table)
- BR-RULE-SEG118-R-016: Load Control Key (Element 178) is Required, field 11, 60 characters, sourced from the Host and echoed back in each subsequent block request
- BR-RULE-SEG118-R-014: Card Table Load Version (Element 177) is Required, field 9, 35 numeric digits, representing the versions of the Card Table components currently being loaded; sourced from the Host and echoed back in each subsequent block request

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1285
Device Card Table Version fields are each 5 characters individually, strung together.

Disposition: CLAUSE_MATCH_PROPOSED

Test explicitly describes concatenated five-digit versions and the 99999 no-card-table sentinel; each AI BR covers one clause, not the full Test BR.

Selected Test BRs: BR-RULE-SEG118-R-013

Ranked retrieval only:
- BR-RULE-SEG118-R-013: Device Card Table Version (Element 176) is Required, field 8, 35 numeric digits: seven 5-digit version values strung together for the Card Table Master ID, BIN table, RULES table, RESTRICTIONS table, SAF table, PROMPT table, and PRODUCT table versions; a value beginning with 99999 indicates no card table is used at the location
- BR-RULE-SEG119-R-020: Device Card Table Version is required
- BR-RULE-SEG118-R-010: Terminal Identifier (Element 102) is Required, field 5, 13 characters, identifying the device by Device Type, State Code, BUYPASS Merchant Number, and Device Number

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1294
Prompt Code 902 indicates Dynamic Card Table Data load, used to return Card Table types in a Load Request.

Disposition: CLAUSE_MATCH_PROPOSED

Same Prompt Code 902 Dynamic Card Table meaning and response content; load lifecycle is not executed.

Selected Test BRs: BR-TX9-PROMPT-902;BR-RULE-SEG118-R-020

Ranked retrieval only:
- BR-TX9-PROMPT-902: Dynamic card table
- BR-TXV-ACCOUNT-UPDATE: Account update
- BR-TXV-ACCOUNT-INQUIRY: Account inquiry

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1312
BUYPASS Card Type value is matched to Card Type value of BIN table to determine if Host Discount applies

Disposition: PARTIAL_SCOPE_OVERLAP

Test defines Host Discount fields, not the full BIN matching or authorization/prepay discount algorithm.

Selected Test BRs: BR-RULE-SEG118-R-022

Ranked retrieval only:
- BR-RULE-SEG118-R-022: When Prompt Code is 904 (Host Discount Data, response only), fields 14-27 are: Host Discount Timestamp (179, 12), Number of Discounts (171, 2), then a repeated block of Start Date (165,8), Start Time (166,4), End Date (165,8), End Time (166,4), BUYPASS Card Type (173,4), Card BIN Range Beginning (183,12), Card BIN Range Ending (184,12), Product Code (77,3), Product Discount Amount (172,5), Product Code (77,3), Discount Quantity Limit (185,3), Discount Program Description (186,15), for a total variable length not to exceed 3,600 bytes
- BR-TX9-PROMPT-904: Host discount data
- BR-RULE-SEG119-R-021: Host Discount Timestamp is required and uses CCYYMMDDHHMM

Artifact review flags: ["CLAIMED_ELEMENT_NOT_IN_CASE_REQUEST_METADATA", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1324
Field Nos. 17-19 sent up to 20 times, once for each card type in order shown.

Disposition: CLAUSE_MATCH_PROPOSED

Same fields 17-19 repeated up to 20 card types in documented order; Test R-039 retains the competing 18-20 separator-note numbering.

Selected Test BRs: BR-RULE-SEG119-R-027

Ranked retrieval only:
- BR-RULE-SEG119-R-027: Card bucket fields 17-19 repeat up to 20 times in documented order
- BR-RULE-SEG112-R-006: The Additional Information section (elements 116/117/118) repeats once per Additional Information Indicator, up to a maximum of 990 bytes total
- BR-RULE-SEG119-R-002: Segment 119 is sent only when the transaction requires totals data

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1325
Card types 1 through 15 will be included in all approved responses.

Disposition: CLAUSE_MATCH_PROPOSED

Same approved-response inclusion of card types 1-15; actual complete responses still need independent validation.

Selected Test BRs: BR-RULE-SEG119-R-028

Ranked retrieval only:
- BR-RULE-SEG119-R-028: Approved responses include card types 1 through 15
- BR-RULE-SEG119-R-029: Card types 16 through 20 are included only when data occurs
- BR-RULE-SEG119-R-025: Card Type Total Count is required for each card bucket

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1326
Card types 16 through 20 included only when data occurs for that card type.

Disposition: CLAUSE_MATCH_PROPOSED

Same card types 16-20 conditional on data occurring for the card type.

Selected Test BRs: BR-RULE-SEG119-R-029

Ranked retrieval only:
- BR-RULE-SEG119-R-029: Card types 16 through 20 are included only when data occurs
- BR-RULE-SEG119-R-028: Approved responses include card types 1 through 15
- BR-RULE-SEG120-R-003: Print Data is required and must be present whenever Segment 120 is included

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1327
Card Labels (in parentheses) used instead of field numbers to identify total buckets.

Disposition: PARTIAL_SCOPE_OVERLAP

Card Label fields are covered, but label-instead-of-field-number display behavior or journal-derived local totals is not established.

Selected Test BRs: BR-RULE-SEG105-R-015;BR-RULE-SEG119-R-024

Ranked retrieval only:
- BR-RULE-SEG119-R-024: Card Label is required for each card bucket
- BR-RULE-SEG105-R-015: Grand Total, Card Label, Card Type Total Count, and Card Type Total Amount are required
- BR-RULE-SEG119-R-026: Card Type Total Amount is required for each card bucket

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1347
EMV Chip Data Length valid values are 000-999.

Disposition: CLAUSE_MATCH_PROPOSED

Shared explicit EMV Chip Data Length presence/range clause, 000-999. A range-only AI BR does not cover every Test obligation.

Selected Test BRs: BR-RULE-SEG130-R-007

Ranked retrieval only:
- BR-RULE-SEG130-R-007: EMV Chip Data Length (Element 189) is required and identifies the length of the following EMV Chip Data field; valid values 000-999
- BR-SEG100-APPR-CHIP-LENGTH: EMV Chip Data shall begin with a valid three-digit length consistent with the following encoded chip data.
- BR-SEG100-APPR-TLV-FORMAT: EMV Chip Data shall preserve valid BCD-packed/hex TLV tag, length, and value structure.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1348
EMV Request Data Segment always appears in Data Section No. 3 of an EMV Financial Transaction Request.

Disposition: PARTIAL_SCOPE_OVERLAP

Test requires Segment 130 for authorization-class EMV requests and waives reversal data; AI all-EMV wording needs scope review.

Selected Test BRs: BR-RULE-SEG130-R-001;BR-RULE-SEG130-R-017

Ranked retrieval only:
- BR-SEG100-APPR-EMV-COMPANION: An EMV Segment 100 transaction shall include Segment 130 EMV Request Data in Data Section 3.
- BR-SEG100-EMV-001: An EMV purchase must include Segment 130 with Segment 100.
- BR-RULE-SEG130-R-001: Segment 130 is Required in every EMV authorization-class Financial Transaction Request; the Section 11.8.1 Data Section 3 table marks it R with the note 'This segment is the only segment required for all EMV financial transactions'

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1353
Segment Type field in EMV Response Data Segment has fixed value 131.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG100-R-015: Segment Type is 100
- BR-SEG100-ID-001: Segment Type must be 100 for the Standard Message Data Segment.
- BR-SEG104-TYPE: Segment Type field in the Purchase Card Data Segment has fixed value 104.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1356
For EMV, the first two characters of Terminal Identifier must be '+*' regardless of actual device type.

Disposition: PARTIAL_SCOPE_OVERLAP

Test explicitly states +* in CAPK-load Terminal Identifier context, not necessarily every EMV request context asserted by AI.

Selected Test BRs: BR-RULE-SEG132-R-006;BR-RULE-SEG100-R-017

Ranked retrieval only:
- BR-SEG100-E102-001: Terminal Identifier is 1-22 alphanumeric characters for a financial flow.
- BR-RULE-SEG132-R-006: Terminal Identifier (Element 102) is required, 13 characters; for EMV, the first two characters (Device Type) must be '+*' regardless of the actual device type
- BR-SEG100-E102-002: Terminal Identifier is at most 13 characters for load flows.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1405
Only the Prompt Table sub-segment is valid for Voyager EMV transactions.

Disposition: CLAUSE_MATCH_PROPOSED

Test explicitly limits Voyager and MasterCard Enhanced Fleet EMV to the Prompt Table sub-segment; AI covers one named authorizer clause.

Selected Test BRs: BR-RULE-SEG145-R-003

Ranked retrieval only:
- BR-RULE-SEG145-R-003: Only the Prompt Table sub-segment (Table ID 004) is valid for Voyager EMV, Visa Fleet 2.0, Comdata, and MasterCard Enhanced Fleet EMV transactions — other sub-segment tables (001, 002, 006, 007, 008) are restricted to different authorizer contexts not enumerated by this restriction
- BR-RULE-SEG145-R-001: Segment 145 carries fleet data for enhanced fleet offerings (Wex OTR, Comdata, Voyager EMV, Visa Fleet 2.0, MasterCard Enhanced Fleet EMV); it may hold multiple TLV-encoded sub-segments (sub-segment type, sub-segment length, sub-segment value)
- BR-RULE-SEG145-R-008: Table 004 (Prompt Data) prompt tokens are authorizer-specific and independently cataloged for Voyager EMV (DF-tag-based), Visa Fleet 2.0, Comdata, WEX OTR, and Conexxus (numeric prompt codes, used by MasterCard Enhanced Fleet EMV); full enumeration of all prompt-token tables is out of scope for this rule catalog pass

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1408
Only the Prompt Table sub-segment is valid for MasterCard Enhanced Fleet EMV transactions.

Disposition: CLAUSE_MATCH_PROPOSED

Test explicitly limits Voyager and MasterCard Enhanced Fleet EMV to the Prompt Table sub-segment; AI covers one named authorizer clause.

Selected Test BRs: BR-RULE-SEG145-R-003

Ranked retrieval only:
- BR-RULE-SEG145-R-003: Only the Prompt Table sub-segment (Table ID 004) is valid for Voyager EMV, Visa Fleet 2.0, Comdata, and MasterCard Enhanced Fleet EMV transactions — other sub-segment tables (001, 002, 006, 007, 008) are restricted to different authorizer contexts not enumerated by this restriction
- BR-RULE-SEG145-R-001: Segment 145 carries fleet data for enhanced fleet offerings (Wex OTR, Comdata, Voyager EMV, Visa Fleet 2.0, MasterCard Enhanced Fleet EMV); it may hold multiple TLV-encoded sub-segments (sub-segment type, sub-segment length, sub-segment value)
- BR-RULE-SEG145-R-008: Table 004 (Prompt Data) prompt tokens are authorizer-specific and independently cataloged for Voyager EMV (DF-tag-based), Visa Fleet 2.0, Comdata, WEX OTR, and Conexxus (numeric prompt codes, used by MasterCard Enhanced Fleet EMV); full enumeration of all prompt-token tables is out of scope for this rule catalog pass

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1409
MasterCard Enhanced transaction functionality expected enabled in First Data production no earlier than June 2026; not for live production until then.

Disposition: CLAUSE_MATCH_PROPOSED

Same specification-era June 2026 production/development limitation; no current production activation is inferred.

Selected Test BRs: BR-RULE-SEG145-R-004

Ranked retrieval only:
- BR-RULE-SEG145-R-004: MasterCard Enhanced Fleet EMV functionality was, as of the specification's authoring, expected to be enabled in First Data production no earlier than June 2026; until then it is for development/certification preparation only and must not be used in live production transactions
- BR-RULE-SEG145-R-003: Only the Prompt Table sub-segment (Table ID 004) is valid for Voyager EMV, Visa Fleet 2.0, Comdata, and MasterCard Enhanced Fleet EMV transactions — other sub-segment tables (001, 002, 006, 007, 008) are restricted to different authorizer contexts not enumerated by this restriction
- BR-RULE-SEG145-R-008: Table 004 (Prompt Data) prompt tokens are authorizer-specific and independently cataloged for Voyager EMV (DF-tag-based), Visa Fleet 2.0, Comdata, WEX OTR, and Conexxus (numeric prompt codes, used by MasterCard Enhanced Fleet EMV); full enumeration of all prompt-token tables is out of scope for this rule catalog pass

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1495
Account Updater Card Number field is only included when merchant sent Account Updater Request Indicator = 'Y' in request.

Disposition: PARTIAL_SCOPE_OVERLAP

Test aggregates Y or Y/I gating and Visa/MasterCard applicability across sub-tables. Exact per-field gating needs source review; do not over-credit the aggregate.

Selected Test BRs: BR-RULE-SEG155-R-003

Ranked retrieval only:
- BR-RULE-SEG111-R-003: Indicator length
- BR-RULE-SEG116-R-008: A companion sub-table, 'Additional TransArmor Data' (Segment 111, Variable Information Indicator/Table ID 052), carries a longer Key ID and/or special device type via two documented sub-tables: Sub-Table ID 01 (KSN, up to 40 bytes, required for AES DUKPT, TDES, and Ingenico OnGuard encryption types) and Sub-Table ID 02 (Device Type, up to 8 bytes, required for AES DUKPT, optional for TDES and Ingenico OnGuard); this is a Segment 111 companion table, not a Segment 116 field
- BR-RULE-SEG110-R-020: The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format codes 'T$' (T<aba>A<acct>C<checknum>), '18' (<aba><acct> concatenated), '09' (raw MICR, not touched), and '19' (numerals from bottom of check); Segment 110's MICR Data (122) and this Segment 111 sub-table are companions, confirmed independently of the AI-generated requirement set, which had attributed these codes to Segment 110 with LOW-MEDIUM confidence (44-47%)

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1500
Account Updater Expiration Date field only included when Account Updater Request Indicator = 'Y'.

Disposition: PARTIAL_SCOPE_OVERLAP

Test aggregates Y or Y/I gating and Visa/MasterCard applicability across sub-tables. Exact per-field gating needs source review; do not over-credit the aggregate.

Selected Test BRs: BR-RULE-SEG155-R-003

Ranked retrieval only:
- BR-RULE-SEG111-R-003: Indicator length
- BR-RULE-SEG116-R-008: A companion sub-table, 'Additional TransArmor Data' (Segment 111, Variable Information Indicator/Table ID 052), carries a longer Key ID and/or special device type via two documented sub-tables: Sub-Table ID 01 (KSN, up to 40 bytes, required for AES DUKPT, TDES, and Ingenico OnGuard encryption types) and Sub-Table ID 02 (Device Type, up to 8 bytes, required for AES DUKPT, optional for TDES and Ingenico OnGuard); this is a Segment 111 companion table, not a Segment 116 field
- BR-RULE-SEG110-R-020: The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format codes 'T$' (T<aba>A<acct>C<checknum>), '18' (<aba><acct> concatenated), '09' (raw MICR, not touched), and '19' (numerals from bottom of check); Segment 110's MICR Data (122) and this Segment 111 sub-table are companions, confirmed independently of the AI-generated requirement set, which had attributed these codes to Segment 110 with LOW-MEDIUM confidence (44-47%)

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1501
Account Updater Expiration Date applicable only to Visa and MasterCard.

Disposition: PARTIAL_SCOPE_OVERLAP

Test aggregates Y or Y/I gating and Visa/MasterCard applicability across sub-tables. Exact per-field gating needs source review; do not over-credit the aggregate.

Selected Test BRs: BR-RULE-SEG155-R-003

Ranked retrieval only:
- BR-RULE-SEG108-R-016: Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card
- BR-RULE-SEG155-R-003: Most sub-tables (Card Number 001, TransArmor Token 002, Expiration Date 003, Card Status 004, Original Response Code 005) are included ONLY when the merchant sent the Account Updater Request Indicator (Segment 111 Table 060 Sub-table 01) with value 'Y' (or 'Y'/'I' for some fields) in the request, and are applicable ONLY to Visa and MasterCard
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)

Artifact review flags: ["CLAIMED_ELEMENT_NOT_IN_CASE_REQUEST_METADATA"]

## REQ-SRC-ATL105-PDF-001:1502
This field will not be sent in the response for Card number or PAN based transactions.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG155-R-003: Most sub-tables (Card Number 001, TransArmor Token 002, Expiration Date 003, Card Status 004, Original Response Code 005) are included ONLY when the merchant sent the Account Updater Request Indicator (Segment 111 Table 060 Sub-table 01) with value 'Y' (or 'Y'/'I' for some fields) in the request, and are applicable ONLY to Visa and MasterCard
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context
- BR-RULE-SEG155-R-001: Segment 155 can only be sent when the transaction qualifies for, and uses, the First Data Auth Optimizer service

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1503
Account Updater Card Status included in response only when Account Updater Request Indicator = 'Y' or 'I'.

Disposition: PARTIAL_SCOPE_OVERLAP

Test aggregates Y or Y/I gating and Visa/MasterCard applicability across sub-tables. Exact per-field gating needs source review; do not over-credit the aggregate.

Selected Test BRs: BR-RULE-SEG155-R-003

Ranked retrieval only:
- BR-RULE-SEG111-R-003: Indicator length
- BR-RULE-SEG116-R-008: A companion sub-table, 'Additional TransArmor Data' (Segment 111, Variable Information Indicator/Table ID 052), carries a longer Key ID and/or special device type via two documented sub-tables: Sub-Table ID 01 (KSN, up to 40 bytes, required for AES DUKPT, TDES, and Ingenico OnGuard encryption types) and Sub-Table ID 02 (Device Type, up to 8 bytes, required for AES DUKPT, optional for TDES and Ingenico OnGuard); this is a Segment 111 companion table, not a Segment 116 field
- BR-RULE-SEG110-R-020: The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format codes 'T$' (T<aba>A<acct>C<checknum>), '18' (<aba><acct> concatenated), '09' (raw MICR, not touched), and '19' (numerals from bottom of check); Segment 110's MICR Data (122) and this Segment 111 sub-table are companions, confirmed independently of the AI-generated requirement set, which had attributed these codes to Segment 110 with LOW-MEDIUM confidence (44-47%)

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1504
Account Updater Card Status applicable only to Visa and MasterCard.

Disposition: PARTIAL_SCOPE_OVERLAP

Test aggregates Y or Y/I gating and Visa/MasterCard applicability across sub-tables. Exact per-field gating needs source review; do not over-credit the aggregate.

Selected Test BRs: BR-RULE-SEG155-R-003

Ranked retrieval only:
- BR-RULE-SEG155-R-003: Most sub-tables (Card Number 001, TransArmor Token 002, Expiration Date 003, Card Status 004, Original Response Code 005) are included ONLY when the merchant sent the Account Updater Request Indicator (Segment 111 Table 060 Sub-table 01) with value 'Y' (or 'Y'/'I' for some fields) in the request, and are applicable ONLY to Visa and MasterCard
- BR-RULE-SEG155-R-005: Account Updater Result Code (Table 006) is a fixed 6-character code from a documented enumeration (VAU001 through VAU016, non-contiguous — VAU015 absent), applicable only to Visa
- BR-RULE-SEG156-R-001: Segment 156 is sent only for an EV charging transaction (EV fuel code in Segment 102 Product Code Segment), and is applicable to the Visa card type only

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1505
Account Updater Card Status valid values: A=New Acct#&Exp Date, E=New Exp Date, Q=Contact Cardholder, C=Closed, U=Unknown Card.

Disposition: CLAUSE_MATCH_PROPOSED

Same five Card Status codes and meanings A/E/Q/C/U.

Selected Test BRs: BR-RULE-SEG155-R-004

Ranked retrieval only:
- BR-RULE-SEG155-R-004: Card Status (Table 004) valid values: A (New Acct# & Exp Date), E (New Exp Date), Q (Contact Cardholder), C (Closed), U (Unknown Card)
- BR-RULE-SEG155-R-003: Most sub-tables (Card Number 001, TransArmor Token 002, Expiration Date 003, Card Status 004, Original Response Code 005) are included ONLY when the merchant sent the Account Updater Request Indicator (Segment 111 Table 060 Sub-table 01) with value 'Y' (or 'Y'/'I' for some fields) in the request, and are applicable ONLY to Visa and MasterCard
- BR-RULE-SEG155-R-005: Account Updater Result Code (Table 006) is a fixed 6-character code from a documented enumeration (VAU001 through VAU016, non-contiguous — VAU015 absent), applicable only to Visa

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1537
Start Time of Charge Table Data format hhmmss: hh 00-99, mm 00-59, ss 00-59, expressed in local time of card acceptor location.

Disposition: PARTIAL_SCOPE_OVERLAP

Same hhmmss bounds for charge times; local-time semantics and individual table applicability remain additional claims.

Selected Test BRs: BR-RULE-SEG156-R-004

Ranked retrieval only:
- BR-RULE-SEG109-R-016: Local Time, Extract Date, and Extract Time, when populated, are numeric with lengths four, six, and four respectively
- BR-RULE-SEG156-R-004: Time-based sub-tables (02 Total Time Plugged In, 03 Total Charging Time, 04 Start Time of Charge, 05 Finish Time of Charge) all use fixed 6-character hhmmss format, hh 00-99, mm/ss 00-59
- BR-RULE-SEG118-R-022: When Prompt Code is 904 (Host Discount Data, response only), fields 14-27 are: Host Discount Timestamp (179, 12), Number of Discounts (171, 2), then a repeated block of Start Date (165,8), Start Time (166,4), End Date (165,8), End Time (166,4), BUYPASS Card Type (173,4), Card BIN Range Beginning (183,12), Card BIN Range Ending (184,12), Product Code (77,3), Product Discount Amount (172,5), Product Code (77,3), Discount Quantity Limit (185,3), Discount Program Description (186,15), for a total variable length not to exceed 3,600 bytes

Artifact review flags: ["CLAIMED_ELEMENT_NOT_IN_CASE_REQUEST_METADATA"]

## REQ-SRC-ATL105-PDF-001:1620
BUYPASS Card Type (173) appears when Prompt Code 904 is returned.

Disposition: CLAUSE_MATCH_PROPOSED

Test explicitly places BUYPASS Card Type and BIN range endpoints in the Prompt 904 response; AI covers a named field clause only.

Selected Test BRs: BR-RULE-SEG118-R-022

Ranked retrieval only:
- BR-TXV-ACCOUNT-UPDATE: Account update
- BR-TXV-ACCOUNT-INQUIRY: Account inquiry
- BR-RULE-SEG100-R-018: Prompt Code represents transaction and card context

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1621
Card BIN Range, Beginning (183) appears when Prompt Code 904 is returned.

Disposition: CLAUSE_MATCH_PROPOSED

Test explicitly places BUYPASS Card Type and BIN range endpoints in the Prompt 904 response; AI covers a named field clause only.

Selected Test BRs: BR-RULE-SEG118-R-022

Ranked retrieval only:
- BR-TXV-ACCOUNT-UPDATE: Account update
- BR-RULE-SEG100-R-018: Prompt Code represents transaction and card context
- BR-TXV-ACCOUNT-INQUIRY: Account inquiry

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1622
Card BIN Range, Ending (184) appears when Prompt Code 904 is returned.

Disposition: CLAUSE_MATCH_PROPOSED

Test explicitly places BUYPASS Card Type and BIN range endpoints in the Prompt 904 response; AI covers a named field clause only.

Selected Test BRs: BR-RULE-SEG118-R-022

Ranked retrieval only:
- BR-TXV-ACCOUNT-UPDATE: Account update
- BR-RULE-SEG100-R-018: Prompt Code represents transaction and card context
- BR-TXV-ACCOUNT-INQUIRY: Account inquiry

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1623
Card Table Data appears when Prompt Code 902 is returned.

Disposition: CLAUSE_MATCH_PROPOSED

Same Card Table Data in Prompt 902 responses.

Selected Test BRs: BR-RULE-SEG118-R-020

Ranked retrieval only:
- BR-TXV-ACCOUNT-UPDATE: Account update
- BR-RULE-SEG100-R-018: Prompt Code represents transaction and card context
- BR-TX9-PROMPT-902: Dynamic card table

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1624
Card Table Type appears when Prompt Code 902 is returned.

Disposition: PARTIAL_SCOPE_OVERLAP

Table type and Prompt 902 data are supported separately, not the full update/create/replace behavior or conditional field rule.

Selected Test BRs: BR-RULE-SEG118-R-015;BR-RULE-SEG118-R-020

Ranked retrieval only:
- BR-RULE-SEG118-R-011: Prompt Code (Element 78) is Required, field 6, 3 digits, and must be one of the five documented values: 901 (Custom receipt text), 902 (Dynamic card table), 903 (Site configuration data), 904 (Host discount data), 905 (Fuel volume data), per Appendix E's Valid Card Type Codes Used in Special Transaction Prompt Codes
- BR-RULE-SEG118-R-015: Card Table Type (Element 174) is Required, field 10, 4 digits, identifying the specific table type this segment addresses, sourced from the Host and echoed back; valid values are 0001 (BIN Table), 0002 (RULES Table), 0003 (RESTRICTIONS Table), 0004 (SAF Table), 0005 (PROMPT Table), 0006 (PRODUCT Table)
- BR-RULE-SEG118-R-028: Host Discount Data (Prompt Code 904) is triggered when a Totals Response's Response Code (83) indicates proprietary/host-discount data pending (values D/E for general proprietary data pending, M/N for Host Discount data pending specifically); the device then sends a Proprietary Data Load Request with Prompt Code 904

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1625
Card Type is sometimes used to construct a Prompt Code.

Disposition: PARTIAL_SCOPE_OVERLAP

Card Type contributes to Prompt Code, but the AI general/eWIC statement and financial Test scope are not fully equivalent.

Selected Test BRs: BR-SEG100-E78-002;BR-SEG100-APPE-PROMPT-COMPOSITION

Ranked retrieval only:
- BR-TXV-ACCOUNT-UPDATE: Account update
- BR-TXV-ACCOUNT-INQUIRY: Account inquiry
- BR-RULE-SEG100-R-018: Prompt Code represents transaction and card context

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1632
Prompt Code is constructed using Card Type.

Disposition: PARTIAL_SCOPE_OVERLAP

Card Type contributes to Prompt Code, but the AI general/eWIC statement and financial Test scope are not fully equivalent.

Selected Test BRs: BR-SEG100-E78-002;BR-SEG100-APPE-PROMPT-COMPOSITION

Ranked retrieval only:
- BR-TXV-ACCOUNT-UPDATE: Account update
- BR-TXV-ACCOUNT-INQUIRY: Account inquiry
- BR-RULE-SEG100-R-018: Prompt Code represents transaction and card context

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1637
Account Number maximum length is 24 bytes for non-TransArmor requests.

Disposition: PARTIAL_SCOPE_OVERLAP

Test establishes account/identification presence in context, not the AI-specific lengths, response representation, code values or account semantics.

Selected Test BRs: BR-RULE-SEG100-R-041

Ranked retrieval only:
- BR-SEG100-E2-002: Tokenized or TransArmor account representation must be explicit.
- BR-RULE-SEG100-R-035: Tokenized or TransArmor Account Number representation is explicit
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1638
Account Number maximum length is 25 bytes for non-TransArmor responses.

Disposition: PARTIAL_SCOPE_OVERLAP

Test establishes account/identification presence in context, not the AI-specific lengths, response representation, code values or account semantics.

Selected Test BRs: BR-RULE-SEG100-R-041

Ranked retrieval only:
- BR-SEG100-E2-002: Tokenized or TransArmor account representation must be explicit.
- BR-RULE-SEG100-R-035: Tokenized or TransArmor Account Number representation is explicit
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1651
Non-TransArmor Initial/Subsequent Transaction Request Account Number is variable length up to 24 alphanumeric bytes.

Disposition: PARTIAL_SCOPE_OVERLAP

Test establishes account/identification presence in context, not the AI-specific lengths, response representation, code values or account semantics.

Selected Test BRs: BR-RULE-SEG100-R-041

Ranked retrieval only:
- BR-SEG100-E2-002: Tokenized or TransArmor account representation must be explicit.
- BR-RULE-SEG100-R-035: Tokenized or TransArmor Account Number representation is explicit
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1652
Non-TransArmor Initial/Subsequent Transaction Response Account Number is fixed length of 25 alphanumeric bytes.

Disposition: PARTIAL_SCOPE_OVERLAP

Test establishes account/identification presence in context, not the AI-specific lengths, response representation, code values or account semantics.

Selected Test BRs: BR-RULE-SEG100-R-041

Ranked retrieval only:
- BR-SEG100-E2-002: Tokenized or TransArmor account representation must be explicit.
- BR-RULE-SEG100-R-035: Tokenized or TransArmor Account Number representation is explicit
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1653
If Account Number obtained from Track 2 card reader, Account Number and Card Discretionary Block Data consist of data between Start/End Sentinel.

Disposition: PARTIAL_SCOPE_OVERLAP

Entry-method and discretionary-data topics overlap, but sentinel parsing, exact track grammar/limits or authorizer-specific full-track requirements are not enumerated.

Selected Test BRs: BR-RULE-SEG100-R-034;BR-RULE-SEG100-R-042

Ranked retrieval only:
- BR-RULE-SEG100-R-042: Card Discretionary Block Data follows conditional source rules
- BR-SEG100-GAP-DISCRETIONARY: Card Discretionary Block Data shall be validated to its source-defined conditional length and usage.
- BR-SEG100-APPD-ALPHA-STATE: Alphabetical state codes used in Card Discretionary Block Data or state context shall use an Appendix D alphabetical code.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1654
If Account Number obtained from Track 1 card reader (credit cards only), similar sentinel-based parsing applies.

Disposition: PARTIAL_SCOPE_OVERLAP

Entry-method and discretionary-data topics overlap, but sentinel parsing, exact track grammar/limits or authorizer-specific full-track requirements are not enumerated.

Selected Test BRs: BR-RULE-SEG100-R-034;BR-RULE-SEG100-R-042

Ranked retrieval only:
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.
- BR-SEG100-E2-002: Tokenized or TransArmor account representation must be explicit.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1656
Non-TransArmor: Account Number and Card Discretionary Block Data combined must not exceed 76 bytes for Track 1 or 37 bytes for Track 2.

Disposition: PARTIAL_SCOPE_OVERLAP

Entry-method and discretionary-data topics overlap, but sentinel parsing, exact track grammar/limits or authorizer-specific full-track requirements are not enumerated.

Selected Test BRs: BR-RULE-SEG100-R-034;BR-RULE-SEG100-R-042

Ranked retrieval only:
- BR-RULE-SEG100-R-042: Card Discretionary Block Data follows conditional source rules
- BR-SEG100-GAP-DISCRETIONARY: Card Discretionary Block Data shall be validated to its source-defined conditional length and usage.
- BR-SEG100-E2-002: Tokenized or TransArmor account representation must be explicit.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1658
Manually keyed data: Account Number and Card Discretionary Block Data consist of unaltered card data between Start and End Sentinels, excluding sentinels.

Disposition: PARTIAL_SCOPE_OVERLAP

Entry-method and discretionary-data topics overlap, but sentinel parsing, exact track grammar/limits or authorizer-specific full-track requirements are not enumerated.

Selected Test BRs: BR-RULE-SEG100-R-034;BR-RULE-SEG100-R-042

Ranked retrieval only:
- BR-RULE-SEG100-R-042: Card Discretionary Block Data follows conditional source rules
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.
- BR-SEG100-GAP-DISCRETIONARY: Card Discretionary Block Data shall be validated to its source-defined conditional length and usage.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1659
Account Number is data before the first ^ character; Card Discretionary Block Data is data after the first ^ character.

Disposition: PARTIAL_SCOPE_OVERLAP

Entry-method and discretionary-data topics overlap, but sentinel parsing, exact track grammar/limits or authorizer-specific full-track requirements are not enumerated.

Selected Test BRs: BR-RULE-SEG100-R-034;BR-RULE-SEG100-R-042

Ranked retrieval only:
- BR-RULE-SEG100-R-042: Card Discretionary Block Data follows conditional source rules
- BR-SEG100-GAP-DISCRETIONARY: Card Discretionary Block Data shall be validated to its source-defined conditional length and usage.
- BR-SEG100-APPD-ALPHA-STATE: Alphabetical state codes used in Card Discretionary Block Data or state context shall use an Appendix D alphabetical code.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1660
If merchant receives Account Updater Card Number (New PAN) in Segment 155 response, follow-on transactions may use new or original PAN in Account Number field.

Disposition: PARTIAL_SCOPE_OVERLAP

Updater response fields and account data are covered separately; permission to reuse new or original values on follow-ups is not established.

Selected Test BRs: BR-RULE-SEG155-R-003;BR-RULE-SEG100-R-041;BR-RULE-SEG100-R-042

Ranked retrieval only:
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.
- BR-RULE-SEG155-R-003: Most sub-tables (Card Number 001, TransArmor Token 002, Expiration Date 003, Card Status 004, Original Response Code 005) are included ONLY when the merchant sent the Account Updater Request Indicator (Segment 111 Table 060 Sub-table 01) with value 'Y' (or 'Y'/'I' for some fields) in the request, and are applicable ONLY to Visa and MasterCard
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1663
Stored Value cards: Account Number length should be 16 or 19 bytes.

Disposition: PARTIAL_SCOPE_OVERLAP

Test establishes account/identification presence in context, not the AI-specific lengths, response representation, code values or account semantics.

Selected Test BRs: BR-RULE-SEG100-R-041

Ranked retrieval only:
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.
- BR-SEG100-E2-002: Tokenized or TransArmor account representation must be explicit.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1664
For a First Data Premium Gift Card Transaction Response, the calculated card number is returned in the Account Number field.

Disposition: PARTIAL_SCOPE_OVERLAP

Test establishes account/identification presence in context, not the AI-specific lengths, response representation, code values or account semantics.

Selected Test BRs: BR-RULE-SEG100-R-041

Ranked retrieval only:
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context
- BR-SEG100-E2-002: Tokenized or TransArmor account representation must be explicit.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1666
For Loyalty Totals Request with Update Code value 'T', Account Number must contain 'TOTALS REQUEST'.

Disposition: PARTIAL_SCOPE_OVERLAP

Test establishes account/identification presence in context, not the AI-specific lengths, response representation, code values or account semantics.

Selected Test BRs: BR-RULE-SEG100-R-041

Ranked retrieval only:
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context
- BR-SEG100-E2-002: Tokenized or TransArmor account representation must be explicit.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1667
For other loyalty advice transactions, Account Number contains the actual Loyalty Account Number.

Disposition: PARTIAL_SCOPE_OVERLAP

Test establishes account/identification presence in context, not the AI-specific lengths, response representation, code values or account semantics.

Selected Test BRs: BR-RULE-SEG100-R-041

Ranked retrieval only:
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context
- BR-SEG100-APPO-TOKEN-NOT-PAN: A payment token value must not be the same value as the cardholder's actual PAN.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1668
For an Express Code request (card type code 095), the 25 digit Express Code Value appears in Account Number.

Disposition: PARTIAL_SCOPE_OVERLAP

Test establishes account/identification presence in context, not the AI-specific lengths, response representation, code values or account semantics.

Selected Test BRs: BR-RULE-SEG100-R-041

Ranked retrieval only:
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context
- BR-SEG100-APPO-TOKEN-ACCOUNT: A payment token used as Segment 100 Account Number shall be a valid 13-19 digit token value subject to account validation and designated token BIN rules.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1669
For a ComCheck request (card type code 096), the 10 digit Check Value appears in Account Number.

Disposition: PARTIAL_SCOPE_OVERLAP

Test establishes account/identification presence in context, not the AI-specific lengths, response representation, code values or account semantics.

Selected Test BRs: BR-RULE-SEG100-R-041

Ranked retrieval only:
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context
- BR-SEG100-APPO-TOKEN-ACCOUNT: A payment token used as Segment 100 Account Number shall be a valid 13-19 digit token value subject to account validation and designated token BIN rules.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1670
For a Money Code/Check request (card type code 093), the 10-21 digit Money Code Value appears in Account Number.

Disposition: PARTIAL_SCOPE_OVERLAP

Test establishes account/identification presence in context, not the AI-specific lengths, response representation, code values or account semantics.

Selected Test BRs: BR-RULE-SEG100-R-041

Ranked retrieval only:
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context
- BR-SEG100-APPO-TOKEN-ACCOUNT: A payment token used as Segment 100 Account Number shall be a valid 13-19 digit token value subject to account validation and designated token BIN rules.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1688
Device not required to track Block Number in Electronic Mail response unless needed to reinstate interrupted transmission.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG109-R-022: Electronic Mail Response contains required Response Code, Download Indicator, Initiation Date, Initiation Time, Sequence Number, and Block Number; optional Mail Text Data Length and Mail Text Data are correlated
- BR-RULE-SEG109-R-012: Block Number is required and three numeric characters
- BR-RULE-SEG109-R-011: Prompt Code is required and must be 981 for standard retrieval, 996 for proprietary-card-data retrieval, or 995 for submission

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1689
Device always uses Block Number to track data block sent in Proprietary Data Load request.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG109-R-012: Block Number is required and three numeric characters
- BR-RULE-SEG109-R-022: Electronic Mail Response contains required Response Code, Download Indicator, Initiation Date, Initiation Time, Sequence Number, and Block Number; optional Mail Text Data Length and Mail Text Data are correlated
- BR-RULE-SEG118-R-018: Block Number (Element 11) is Conditional, field 13, 3 digits, identifying the specific block being sent by device or Host; the Host uses this value to determine the next block to send, and it is echoed back in the subsequent block request

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1697
Account Number and Card Discretionary Block Data combined do not exceed 76 characters for Track 1.

Disposition: PARTIAL_SCOPE_OVERLAP

Entry-method and discretionary-data topics overlap, but sentinel parsing, exact track grammar/limits or authorizer-specific full-track requirements are not enumerated.

Selected Test BRs: BR-RULE-SEG100-R-034;BR-RULE-SEG100-R-042

Ranked retrieval only:
- BR-RULE-SEG100-R-042: Card Discretionary Block Data follows conditional source rules
- BR-SEG100-GAP-DISCRETIONARY: Card Discretionary Block Data shall be validated to its source-defined conditional length and usage.
- BR-SEG100-APPD-ALPHA-STATE: Alphabetical state codes used in Card Discretionary Block Data or state context shall use an Appendix D alphabetical code.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1698
Account Number and Card Discretionary Block Data combined do not exceed 37 characters for Track 2.

Disposition: PARTIAL_SCOPE_OVERLAP

Entry-method and discretionary-data topics overlap, but sentinel parsing, exact track grammar/limits or authorizer-specific full-track requirements are not enumerated.

Selected Test BRs: BR-RULE-SEG100-R-034;BR-RULE-SEG100-R-042

Ranked retrieval only:
- BR-RULE-SEG100-R-042: Card Discretionary Block Data follows conditional source rules
- BR-SEG100-GAP-DISCRETIONARY: Card Discretionary Block Data shall be validated to its source-defined conditional length and usage.
- BR-SEG100-APPD-ALPHA-STATE: Alphabetical state codes used in Card Discretionary Block Data or state context shall use an Appendix D alphabetical code.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1699
For either TransArmor processing method, the expiration date must be included in Card Discretionary Block Data.

Disposition: PARTIAL_SCOPE_OVERLAP

TransArmor account representation is related; exact TAP/EDATA layout and mandatory expiration behavior are not established by broad representation rules.

Selected Test BRs: BR-RULE-SEG100-R-035;BR-SEG100-ANN-O

Ranked retrieval only:
- BR-SEG100-APPO-TOKEN-EXPIRATION: Payment Token Expiration Date shall be populated in the PAN Expiration Date field when token processing requires it.
- BR-RULE-SEG100-R-042: Card Discretionary Block Data follows conditional source rules
- BR-SEG100-GAP-DISCRETIONARY: Card Discretionary Block Data shall be validated to its source-defined conditional length and usage.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1700
When Account Updater Expiration Date received in Segment 155 response, merchant can send new or original expiration date in follow-on transactions.

Disposition: PARTIAL_SCOPE_OVERLAP

Updater response fields and account data are covered separately; permission to reuse new or original values on follow-ups is not established.

Selected Test BRs: BR-RULE-SEG155-R-003;BR-RULE-SEG100-R-041;BR-RULE-SEG100-R-042

Ranked retrieval only:
- BR-RULE-SEG100-R-042: Card Discretionary Block Data follows conditional source rules
- BR-RULE-SEG155-R-003: Most sub-tables (Card Number 001, TransArmor Token 002, Expiration Date 003, Card Status 004, Original Response Code 005) are included ONLY when the merchant sent the Account Updater Request Indicator (Segment 111 Table 060 Sub-table 01) with value 'Y' (or 'Y'/'I' for some fields) in the request, and are applicable ONLY to Visa and MasterCard
- BR-RULE-SEG108-R-016: Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1703
For manually entered card number, expiration date is in MMYY format; other required data depends on application.

Disposition: PARTIAL_SCOPE_OVERLAP

Discretionary-data conditional usage is broad; exact MMYY/YYMM format, delimiter and field ordering remain unproven by this Test BR.

Selected Test BRs: BR-RULE-SEG100-R-042

Ranked retrieval only:
- BR-RULE-SEG108-R-016: Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)
- BR-SEG108-FIELD-009: Loyalty Account Number is numeric max 24 when populated.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1708
For a Comdata Express Code or Check request (card type code 095/096), expiration date field should be skipped but separators included.

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact card-family expiration validation/prompt/default behavior is not established by generic account or loyalty format rules. Resolve request context and independently expected behavior.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG108-R-016: Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)
- BR-TXV-EXPIRATION-UPDATE: Expiration date update

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1709
For a Money Code/Check request (card type code 093), expiration date field should be skipped but separators included.

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact card-family expiration validation/prompt/default behavior is not established by generic account or loyalty format rules. Resolve request context and independently expected behavior.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG108-R-016: Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)
- BR-TXV-EXPIRATION-UPDATE: Expiration date update

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1710
For Accel and STAR, expiration date is mandatory for e-commerce and one-time bill payment transactions.

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact card-family expiration validation/prompt/default behavior is not established by generic account or loyalty format rules. Resolve request context and independently expected behavior.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG108-R-016: Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)
- BR-SEG108-FIELD-017: Payment Tender Type is required and one of the documented values.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1711
Effective May 1, 2025, expiration date is optional for Accel/STAR COF transactions (bill payment, recurring, installment) using stored customer data.

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact card-family expiration validation/prompt/default behavior is not established by generic account or loyalty format rules. Resolve request context and independently expected behavior.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG108-R-016: Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)
- BR-RULE-SEG101-R-017: Customer Data, when populated, is alphanumeric with maximum length 12

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1712
Card Label code 'CC' represents MasterCard/Visa and 'TE' represents Amex in Totals Response.

Disposition: PARTIAL_SCOPE_OVERLAP

Totals labels/count fields overlap; exact label-to-network mapping or numeric count range is not stated.

Selected Test BRs: BR-RULE-SEG105-R-015;BR-RULE-SEG119-R-024;BR-RULE-SEG119-R-025

Ranked retrieval only:
- BR-RULE-SEG119-R-024: Card Label is required for each card bucket
- BR-RULE-SEG105-R-015: Grand Total, Card Label, Card Type Total Count, and Card Type Total Amount are required
- BR-SEG100-GAP-TAX-AMOUNT: Tax Amount shall represent the total tax amount when tax data applies.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1715
In a financial transaction, device sends Card Type as part of the 4-character Prompt Code.

Disposition: CLAUSE_MATCH_PROPOSED

Same financial Prompt Code construction from transaction type and card type; each allowlist and exact position must also be checked.

Selected Test BRs: BR-SEG100-APPE-PROMPT-COMPOSITION;BR-SEG100-E78-001;BR-SEG100-E78-002

Ranked retrieval only:
- BR-TX-9-SPECIAL-PROMPT-CODE-THREE-CHARACTER-FORMAT: Special transaction Prompt Codes shall use the three-character special-data code without appending a financial card type.
- BR-TXV-ACCOUNT-UPDATE: Account update
- BR-TXV-ACCOUNT-INQUIRY: Account inquiry

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1716
In a special transaction, device sends Card Type as a 3-character Prompt Code requesting specific data.

Disposition: CLAUSE_MATCH_PROPOSED

Same special three-character Prompt Code without a financial transaction-type prefix.

Selected Test BRs: BR-TX-9-SPECIAL-PROMPT-CODE-THREE-CHARACTER-FORMAT

Ranked retrieval only:
- BR-TX-9-SPECIAL-PROMPT-CODE-THREE-CHARACTER-FORMAT: Special transaction Prompt Codes shall use the three-character special-data code without appending a financial card type.
- BR-TX9-PROMPT-990: Totals
- BR-TX9-PROMPT-902: Dynamic card table

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1718
Card Type Total Count valid values are 00001-99999.

Disposition: PARTIAL_SCOPE_OVERLAP

Totals labels/count fields overlap; exact label-to-network mapping or numeric count range is not stated.

Selected Test BRs: BR-RULE-SEG105-R-015;BR-RULE-SEG119-R-024;BR-RULE-SEG119-R-025

Ranked retrieval only:
- BR-RULE-SEG119-R-025: Card Type Total Count is required for each card bucket
- BR-RULE-SEG105-R-020: In a Totals Request, Element 63 (Number of Segments) is 01
- BR-RULE-SEG105-R-015: Grand Total, Card Label, Card Type Total Count, and Card Type Total Amount are required

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1794
Financial Transaction Request, Totals Request, Loyalty Card Request, and Electronic Mail Request messages contain this element in Field No. 1.

Disposition: PARTIAL_SCOPE_OVERLAP

Test covers format identity and segment count, not the entire named message-family/field-position assertion.

Selected Test BRs: BR-RULE-SEG100-R-013;BR-RULE-SEG100-R-007

Ranked retrieval only:
- BR-SEG100-E55-001: Element 55 Message Format Version Identifier must be ATL105.
- BR-RULE-SEG109-R-002: Data Section 1 contains required Message Format Version Identifier (Element 55) and Number of Segments (Element 63), separated and followed by a Field Separator
- BR-RULE-SEG100-R-013: Message format identifier is ATL105

Artifact review flags: ["CLAIMED_ELEMENT_NOT_IN_CASE_REQUEST_METADATA", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1846
When device uses Dynamic Card Table, only product codes defined in the table are valid.

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact code/table/tag meaning and context have no defensible counterpart established in this review. Generic segment/field matches and shared numbers are insufficient.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG102-R-010: Product Code is a valid value per Appendix F
- BR-RULE-SEG102-R-008: For EV charging transactions the EV product code is first
- BR-RULE-SEG102-R-009: A unique Product Code is sent for each type of fuel purchase

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1847
For Proprietary Load Response (Prompt Code 904), Product Code second/third positions may contain wildcard '*' allowing any digit.

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact code/table/tag meaning and context have no defensible counterpart established in this review. Generic segment/field matches and shared numbers are insufficient.

Selected Test BRs: None established

Ranked retrieval only:
- BR-APPF-FORMAT: Transaction Product Code is a string of exactly three ASCII digits; Prompt 904 wildcard patterns are outside this scope.
- BR-RULE-SEG102-R-008: For EV charging transactions the EV product code is first
- BR-RULE-SEG102-R-009: A unique Product Code is sent for each type of fuel purchase

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1849
Prompt Code 4-character value identifies Transaction Type (1 char) and Card Type (3 chars) for financial transactions.

Disposition: CLAUSE_MATCH_PROPOSED

Same financial Prompt Code construction from transaction type and card type; each allowlist and exact position must also be checked.

Selected Test BRs: BR-SEG100-APPE-PROMPT-COMPOSITION;BR-SEG100-E78-001;BR-SEG100-E78-002

Ranked retrieval only:
- BR-TX-9-SPECIAL-PROMPT-CODE-THREE-CHARACTER-FORMAT: Special transaction Prompt Codes shall use the three-character special-data code without appending a financial card type.
- BR-SEG100-E78-001: Prompt Code first position identifies a supported transaction type.
- BR-SEG100-E78-002: Prompt Code remaining positions identify the expected card type or flow code.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1850
For a special transaction, Prompt Code is a 3-character code identifying requested special data (Card Type).

Disposition: CLAUSE_MATCH_PROPOSED

Same special three-character Prompt Code without a financial transaction-type prefix.

Selected Test BRs: BR-TX-9-SPECIAL-PROMPT-CODE-THREE-CHARACTER-FORMAT

Ranked retrieval only:
- BR-TX-9-SPECIAL-PROMPT-CODE-THREE-CHARACTER-FORMAT: Special transaction Prompt Codes shall use the three-character special-data code without appending a financial card type.
- BR-TX9-PROMPT-990: Totals
- BR-TX9-PROMPT-902: Dynamic card table

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1851
Prompt Code must be a valid Transaction Type code and/or Card Type code.

Disposition: PARTIAL_SCOPE_OVERLAP

Prompt context matches broadly; separate financial/special allowlists and exact permitted combinations still need review.

Selected Test BRs: BR-RULE-SEG100-R-018;BR-RULE-SEG100-R-033

Ranked retrieval only:
- BR-TXV-ACCOUNT-UPDATE: Account update
- BR-TXV-ACCOUNT-INQUIRY: Account inquiry
- BR-SEG100-ANN-E: Segment 100 financial Prompt Codes use valid Appendix E card types and separate special Prompt Code families.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1853
For a special transaction, device transmits a 3-character Prompt Code (Card Type) when transaction is initiated.

Disposition: CLAUSE_MATCH_PROPOSED

Same special three-character Prompt Code without a financial transaction-type prefix.

Selected Test BRs: BR-TX-9-SPECIAL-PROMPT-CODE-THREE-CHARACTER-FORMAT

Ranked retrieval only:
- BR-TX-9-SPECIAL-PROMPT-CODE-THREE-CHARACTER-FORMAT: Special transaction Prompt Codes shall use the three-character special-data code without appending a financial card type.
- BR-TX9-PROMPT-990: Totals
- BR-TX9-PROMPT-902: Dynamic card table

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1856
If 'D' is returned in response for EMV transaction, subsequent completion/void/reversal must be sent as credit via Prompt Code.

Disposition: PARTIAL_SCOPE_OVERLAP

Lifecycle/context rules are related, but returned D/S routing and Table 21 triggers are not stated by these Test BRs.

Selected Test BRs: BR-RULE-SEG100-R-037;BR-RULE-SEG100-R-033

Ranked retrieval only:
- BR-RULE-SEG100-R-060: eWIC Purchase Reversal/Void uses Prompt Code 8086
- BR-SEG100-EWIC-VOID: eWIC Purchase Reversal/Void uses Prompt Code 8086 and completes before another eWIC transaction.
- BR-TXV-COUPON-REVERSAL: Reversal of coupon redeem

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1857
If 'S' is returned in response for EMV transaction, subsequent completion/void/reversal must be sent as debit via Prompt Code.

Disposition: PARTIAL_SCOPE_OVERLAP

Lifecycle/context rules are related, but returned D/S routing and Table 21 triggers are not stated by these Test BRs.

Selected Test BRs: BR-RULE-SEG100-R-037;BR-RULE-SEG100-R-033

Ranked retrieval only:
- BR-RULE-SEG100-R-060: eWIC Purchase Reversal/Void uses Prompt Code 8086
- BR-SEG100-EWIC-VOID: eWIC Purchase Reversal/Void uses Prompt Code 8086 and completes before another eWIC transaction.
- BR-TXV-COUPON-REVERSAL: Reversal of coupon redeem

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1858
In financial transaction Prompt Code, position 1 is Transaction Type, positions 2-4 are Card Type.

Disposition: CLAUSE_MATCH_PROPOSED

Same financial Prompt Code construction from transaction type and card type; each allowlist and exact position must also be checked.

Selected Test BRs: BR-SEG100-APPE-PROMPT-COMPOSITION;BR-SEG100-E78-001;BR-SEG100-E78-002

Ranked retrieval only:
- BR-SEG100-E78-001: Prompt Code first position identifies a supported transaction type.
- BR-SEG100-E78-002: Prompt Code remaining positions identify the expected card type or flow code.
- BR-TXV-ACCOUNT-UPDATE: Account update

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1859
In special transaction Prompt Code, positions 1-3 are Card Type identifying requested special data.

Disposition: CLAUSE_MATCH_PROPOSED

Same special three-character Prompt Code without a financial transaction-type prefix.

Selected Test BRs: BR-TX-9-SPECIAL-PROMPT-CODE-THREE-CHARACTER-FORMAT

Ranked retrieval only:
- BR-SEG100-E78-001: Prompt Code first position identifies a supported transaction type.
- BR-TX-9-SPECIAL-PROMPT-CODE-THREE-CHARACTER-FORMAT: Special transaction Prompt Codes shall use the three-character special-data code without appending a financial card type.
- BR-TX9-PROMPT-990: Totals

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1861
If Account Number EDATA Identifier is value 7, this field is used as pump/lane number for decryption only.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG100-R-044: Pump/Lane Number is valid when fuel or lane context applies
- BR-SEG100-GAP-PUMP: Pump/Lane Number shall be present and correctly formatted when the transaction is a fuel or lane-specific flow.
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1914
Purchase Card Data Segment (No. 104) data length ranges 001-86.

Disposition: CLAUSE_MATCH_PROPOSED

Same Segment 104 maximum of 86; the Test BR establishes the maximum, not every lower-bound value or wire encoding.

Selected Test BRs: BR-RULE-SEG104-R-006

Ranked retrieval only:
- BR-RULE-SEG104-R-006: Purchase Card Data Segment max length is 86 alphanumeric characters.
- BR-RULE-SEG104-R-001: Purchase Card Data Segment is a Data Section 3 companion of Segment 100.
- BR-SEG104-MAX-LENGTH: The Purchase Card Data Segment has a maximum length of 86 alphanumeric characters.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1916
Loyalty Card Data Segment (No. 108) data length ranges 001-142.

Disposition: CLAUSE_MATCH_PROPOSED

Same Segment 108 maximum of 142; serialization and minimum-length claims remain separate.

Selected Test BRs: BR-RULE-SEG108-R-005

Ranked retrieval only:
- BR-RULE-SEG108-R-009: Loyalty Account Number, when populated, is numeric with maximum length 24
- BR-SEG108-FIELD-009: Loyalty Account Number is numeric max 24 when populated.
- BR-RULE-SEG108-R-002: Segment 108 is required in every Loyalty Card Transaction Request

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1929
EMV Request Data Segment (No. 130) data length ranges 001-3043.

Disposition: CLAUSE_MATCH_PROPOSED

Same Segment 130 maximum 3043; minimum length and encoded-byte accounting are not established by this match.

Selected Test BRs: BR-RULE-SEG130-R-004

Ranked retrieval only:
- BR-RULE-SEG130-R-007: EMV Chip Data Length (Element 189) is required and identifies the length of the following EMV Chip Data field; valid values 000-999
- BR-RULE-SEG130-R-012: Element 118 is reused across segments: it is 'Additional Information' in Segment 112 (max 984, single occurrence) and 'EMV Additional Information' in Segment 130 (variable length, repeating). Do not assume element numbers are globally unique or that the two fields share business meaning
- BR-RULE-SEG130-R-006: EMV Card Sequence Number (Element 188), when populated, identifies the sequence number of the EMV card; Conditional, sourced Device

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:1970
If using TransArmor, first 6 digits of Merchant Number relate to Account Number element.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-SEG100-E2-002: Tokenized or TransArmor account representation must be explicit.
- BR-RULE-SEG100-R-035: Tokenized or TransArmor Account Number representation is explicit
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:1989
The account number of the account being reduced to $0.00 is placed in Variable Information (Element No. 113).

Disposition: PARTIAL_SCOPE_OVERLAP

Balance merge zeroes one card; the account number placement in Variable Information is an additional AI claim.

Selected Test BRs: BR-TX-M-TRANSACTION-TYPE-M-BALANCE-MERGE-TWO-CARDS

Ranked retrieval only:
- BR-SEG100-ANN-I: Conditional Segment 111 Variable Information uses the applicable Appendix I Table ID and layout.
- BR-RULE-SEG116-R-008: A companion sub-table, 'Additional TransArmor Data' (Segment 111, Variable Information Indicator/Table ID 052), carries a longer Key ID and/or special device type via two documented sub-tables: Sub-Table ID 01 (KSN, up to 40 bytes, required for AES DUKPT, TDES, and Ingenico OnGuard encryption types) and Sub-Table ID 02 (Device Type, up to 8 bytes, required for AES DUKPT, optional for TDES and Ingenico OnGuard); this is a Segment 111 companion table, not a Segment 116 field
- BR-RULE-SEG110-R-020: The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format codes 'T$' (T<aba>A<acct>C<checknum>), '18' (<aba><acct> concatenated), '09' (raw MICR, not touched), and '19' (numerals from bottom of check); Segment 110's MICR Data (122) and this Segment 111 sub-table are companions, confirmed independently of the AI-generated requirement set, which had attributed these codes to Segment 110 with LOW-MEDIUM confidence (44-47%)

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2008
When Variable Information Indicator = 016, Visa payWave card information is included.

Disposition: PARTIAL_SCOPE_OVERLAP

Generic indicator/length rules do not establish individual Table ID, sub-table, value, network or CIT/MIT applicability. No table-specific match credit.

Selected Test BRs: BR-RULE-SEG111-R-003;BR-RULE-SEG111-R-004

Ranked retrieval only:
- BR-RULE-SEG111-R-003: Indicator length
- BR-RULE-SEG116-R-008: A companion sub-table, 'Additional TransArmor Data' (Segment 111, Variable Information Indicator/Table ID 052), carries a longer Key ID and/or special device type via two documented sub-tables: Sub-Table ID 01 (KSN, up to 40 bytes, required for AES DUKPT, TDES, and Ingenico OnGuard encryption types) and Sub-Table ID 02 (Device Type, up to 8 bytes, required for AES DUKPT, optional for TDES and Ingenico OnGuard); this is a Segment 111 companion table, not a Segment 116 field
- BR-RULE-SEG110-R-020: The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format codes 'T$' (T<aba>A<acct>C<checknum>), '18' (<aba><acct> concatenated), '09' (raw MICR, not touched), and '19' (numerals from bottom of check); Segment 110's MICR Data (122) and this Segment 111 sub-table are companions, confirmed independently of the AI-generated requirement set, which had attributed these codes to Segment 110 with LOW-MEDIUM confidence (44-47%)

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2010
When Variable Information Indicator = 018, card acceptor terminal identifier information is included.

Disposition: PARTIAL_SCOPE_OVERLAP

Generic indicator/length rules do not establish individual Table ID, sub-table, value, network or CIT/MIT applicability. No table-specific match credit.

Selected Test BRs: BR-RULE-SEG111-R-003;BR-RULE-SEG111-R-004

Ranked retrieval only:
- BR-RULE-SEG111-R-003: Indicator length
- BR-RULE-SEG116-R-008: A companion sub-table, 'Additional TransArmor Data' (Segment 111, Variable Information Indicator/Table ID 052), carries a longer Key ID and/or special device type via two documented sub-tables: Sub-Table ID 01 (KSN, up to 40 bytes, required for AES DUKPT, TDES, and Ingenico OnGuard encryption types) and Sub-Table ID 02 (Device Type, up to 8 bytes, required for AES DUKPT, optional for TDES and Ingenico OnGuard); this is a Segment 111 companion table, not a Segment 116 field
- BR-RULE-SEG110-R-020: The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format codes 'T$' (T<aba>A<acct>C<checknum>), '18' (<aba><acct> concatenated), '09' (raw MICR, not touched), and '19' (numerals from bottom of check); Segment 110's MICR Data (122) and this Segment 111 sub-table are companions, confirmed independently of the AI-generated requirement set, which had attributed these codes to Segment 110 with LOW-MEDIUM confidence (44-47%)

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2016
When Variable Information Indicator = 025, Gift Card Number information is included.

Disposition: PARTIAL_SCOPE_OVERLAP

Generic indicator/length rules do not establish individual Table ID, sub-table, value, network or CIT/MIT applicability. No table-specific match credit.

Selected Test BRs: BR-RULE-SEG111-R-003;BR-RULE-SEG111-R-004

Ranked retrieval only:
- BR-RULE-SEG111-R-003: Indicator length
- BR-RULE-SEG116-R-008: A companion sub-table, 'Additional TransArmor Data' (Segment 111, Variable Information Indicator/Table ID 052), carries a longer Key ID and/or special device type via two documented sub-tables: Sub-Table ID 01 (KSN, up to 40 bytes, required for AES DUKPT, TDES, and Ingenico OnGuard encryption types) and Sub-Table ID 02 (Device Type, up to 8 bytes, required for AES DUKPT, optional for TDES and Ingenico OnGuard); this is a Segment 111 companion table, not a Segment 116 field
- BR-RULE-SEG110-R-020: The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format codes 'T$' (T<aba>A<acct>C<checknum>), '18' (<aba><acct> concatenated), '09' (raw MICR, not touched), and '19' (numerals from bottom of check); Segment 110's MICR Data (122) and this Segment 111 sub-table are companions, confirmed independently of the AI-generated requirement set, which had attributed these codes to Segment 110 with LOW-MEDIUM confidence (44-47%)

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2025
When Variable Information Indicator = 036, MasterCard UCAF Data is included.

Disposition: PARTIAL_SCOPE_OVERLAP

Generic indicator/length rules do not establish individual Table ID, sub-table, value, network or CIT/MIT applicability. No table-specific match credit.

Selected Test BRs: BR-RULE-SEG111-R-003;BR-RULE-SEG111-R-004

Ranked retrieval only:
- BR-RULE-SEG111-R-003: Indicator length
- BR-RULE-SEG116-R-008: A companion sub-table, 'Additional TransArmor Data' (Segment 111, Variable Information Indicator/Table ID 052), carries a longer Key ID and/or special device type via two documented sub-tables: Sub-Table ID 01 (KSN, up to 40 bytes, required for AES DUKPT, TDES, and Ingenico OnGuard encryption types) and Sub-Table ID 02 (Device Type, up to 8 bytes, required for AES DUKPT, optional for TDES and Ingenico OnGuard); this is a Segment 111 companion table, not a Segment 116 field
- BR-RULE-SEG110-R-020: The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format codes 'T$' (T<aba>A<acct>C<checknum>), '18' (<aba><acct> concatenated), '09' (raw MICR, not touched), and '19' (numerals from bottom of check); Segment 110's MICR Data (122) and this Segment 111 sub-table are companions, confirmed independently of the AI-generated requirement set, which had attributed these codes to Segment 110 with LOW-MEDIUM confidence (44-47%)

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2037
When Variable Information Indicator = 048, MasterCard Authentication Data information is included.

Disposition: PARTIAL_SCOPE_OVERLAP

Generic indicator/length rules do not establish individual Table ID, sub-table, value, network or CIT/MIT applicability. No table-specific match credit.

Selected Test BRs: BR-RULE-SEG111-R-003;BR-RULE-SEG111-R-004

Ranked retrieval only:
- BR-RULE-SEG111-R-003: Indicator length
- BR-RULE-SEG116-R-008: A companion sub-table, 'Additional TransArmor Data' (Segment 111, Variable Information Indicator/Table ID 052), carries a longer Key ID and/or special device type via two documented sub-tables: Sub-Table ID 01 (KSN, up to 40 bytes, required for AES DUKPT, TDES, and Ingenico OnGuard encryption types) and Sub-Table ID 02 (Device Type, up to 8 bytes, required for AES DUKPT, optional for TDES and Ingenico OnGuard); this is a Segment 111 companion table, not a Segment 116 field
- BR-RULE-SEG110-R-020: The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format codes 'T$' (T<aba>A<acct>C<checknum>), '18' (<aba><acct> concatenated), '09' (raw MICR, not touched), and '19' (numerals from bottom of check); Segment 110's MICR Data (122) and this Segment 111 sub-table are companions, confirmed independently of the AI-generated requirement set, which had attributed these codes to Segment 110 with LOW-MEDIUM confidence (44-47%)

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2040
When Variable Information Indicator = 051, Additional Card ID information is included.

Disposition: PARTIAL_SCOPE_OVERLAP

Generic indicator/length rules do not establish individual Table ID, sub-table, value, network or CIT/MIT applicability. No table-specific match credit.

Selected Test BRs: BR-RULE-SEG111-R-003;BR-RULE-SEG111-R-004

Ranked retrieval only:
- BR-RULE-SEG111-R-003: Indicator length
- BR-RULE-SEG116-R-008: A companion sub-table, 'Additional TransArmor Data' (Segment 111, Variable Information Indicator/Table ID 052), carries a longer Key ID and/or special device type via two documented sub-tables: Sub-Table ID 01 (KSN, up to 40 bytes, required for AES DUKPT, TDES, and Ingenico OnGuard encryption types) and Sub-Table ID 02 (Device Type, up to 8 bytes, required for AES DUKPT, optional for TDES and Ingenico OnGuard); this is a Segment 111 companion table, not a Segment 116 field
- BR-RULE-SEG110-R-020: The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format codes 'T$' (T<aba>A<acct>C<checknum>), '18' (<aba><acct> concatenated), '09' (raw MICR, not touched), and '19' (numerals from bottom of check); Segment 110's MICR Data (122) and this Segment 111 sub-table are companions, confirmed independently of the AI-generated requirement set, which had attributed these codes to Segment 110 with LOW-MEDIUM confidence (44-47%)

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2066
When Element 111 value is 079 (Visa Commercial Card Data), Visa Commercial Card Data is included.

Disposition: PARTIAL_SCOPE_OVERLAP

Generic indicator/length rules do not establish individual Table ID, sub-table, value, network or CIT/MIT applicability. No table-specific match credit.

Selected Test BRs: BR-RULE-SEG111-R-003;BR-RULE-SEG111-R-004

Ranked retrieval only:
- BR-RULE-SEG110-R-020: The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format codes 'T$' (T<aba>A<acct>C<checknum>), '18' (<aba><acct> concatenated), '09' (raw MICR, not touched), and '19' (numerals from bottom of check); Segment 110's MICR Data (122) and this Segment 111 sub-table are companions, confirmed independently of the AI-generated requirement set, which had attributed these codes to Segment 110 with LOW-MEDIUM confidence (44-47%)
- BR-RULE-SEG111-R-003: Indicator length
- BR-RULE-SEG116-R-008: A companion sub-table, 'Additional TransArmor Data' (Segment 111, Variable Information Indicator/Table ID 052), carries a longer Key ID and/or special device type via two documented sub-tables: Sub-Table ID 01 (KSN, up to 40 bytes, required for AES DUKPT, TDES, and Ingenico OnGuard encryption types) and Sub-Table ID 02 (Device Type, up to 8 bytes, required for AES DUKPT, optional for TDES and Ingenico OnGuard); this is a Segment 111 companion table, not a Segment 116 field

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2084
Code 013 indicates Expiration Date (MMYY) for TransArmor - VeriFone edition processing.

Disposition: PARTIAL_SCOPE_OVERLAP

Response indicator/length/value structure overlaps; the named code meaning and exact fixed length require a table-specific Test BR.

Selected Test BRs: BR-RULE-SEG112-R-007;BR-RULE-SEG112-R-008;BR-RULE-SEG112-R-009

Ranked retrieval only:
- BR-RULE-SEG108-R-016: Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)
- BR-SEG100-APPO-TOKEN-EXPIRATION: Payment Token Expiration Date shall be populated in the PAN Expiration Date field when token processing requires it.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2085
Code 017 indicates BUYPASS Host Card Type.

Disposition: PARTIAL_SCOPE_OVERLAP

Response indicator/length/value structure overlaps; the named code meaning and exact fixed length require a table-specific Test BR.

Selected Test BRs: BR-RULE-SEG112-R-007;BR-RULE-SEG112-R-008;BR-RULE-SEG112-R-009

Ranked retrieval only:
- BR-RULE-SEG118-R-022: When Prompt Code is 904 (Host Discount Data, response only), fields 14-27 are: Host Discount Timestamp (179, 12), Number of Discounts (171, 2), then a repeated block of Start Date (165,8), Start Time (166,4), End Date (165,8), End Time (166,4), BUYPASS Card Type (173,4), Card BIN Range Beginning (183,12), Card BIN Range Ending (184,12), Product Code (77,3), Product Discount Amount (172,5), Product Code (77,3), Discount Quantity Limit (185,3), Discount Program Description (186,15), for a total variable length not to exceed 3,600 bytes
- BR-RULE-SEG131-R-011: Segment 131 originates at BUYPASS (the Host)
- BR-RULE-SEG134-R-001: Segment 134 originates at BUYPASS (the Host)

Artifact review flags: ["CLAIMED_ELEMENT_NOT_IN_CASE_REQUEST_METADATA", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:2102
Element 116 = 013 means Expiration Date (MMYY) included, fixed length 4 bytes.

Disposition: PARTIAL_SCOPE_OVERLAP

Response indicator/length/value structure overlaps; the named code meaning and exact fixed length require a table-specific Test BR.

Selected Test BRs: BR-RULE-SEG112-R-007;BR-RULE-SEG112-R-008;BR-RULE-SEG112-R-009

Ranked retrieval only:
- BR-RULE-SEG112-R-007: Additional Information Indicator (Element 116) identifies the type of additional information being transmitted
- BR-RULE-SEG108-R-016: Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card
- BR-RULE-SEG130-R-012: Element 118 is reused across segments: it is 'Additional Information' in Segment 112 (max 984, single occurrence) and 'EMV Additional Information' in Segment 130 (variable length, repeating). Do not assume element numbers are globally unique or that the two fields share business meaning

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2143
Support for Partial Authorization is mandatory for all card brands; field must be populated correctly for card present transactions.

Disposition: PARTIAL_SCOPE_OVERLAP

Allowed indicator and approved amount do not establish mandatory partial-authorization support across brands/card-present contexts.

Selected Test BRs: BR-RULE-SEG100-R-020;BR-RULE-SEG100-R-065

Ranked retrieval only:
- BR-SEG100-E121-001: Partial Approval Indicator must use an allowed value.
- BR-RULE-SEG100-R-020: Partial Approval Indicator uses an allowed value
- BR-SEG100-E121-002: Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2155
Loyalty Program ID is required on all loyalty card transactions.

Disposition: CLAUSE_MATCH_PROPOSED

Same required Loyalty Program ID clause; Test also constrains numeric maximum length 6.

Selected Test BRs: BR-RULE-SEG108-R-008

Ranked retrieval only:
- BR-SEG108-FIELD-008: Loyalty Program ID is required, numeric max 6.
- BR-RULE-SEG108-R-008: Loyalty Program ID, required, is numeric with maximum length 6
- BR-RULE-SEG108-R-002: Segment 108 is required in every Loyalty Card Transaction Request

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2156
Loyalty Account Number is required on all loyalty card transactions.

Disposition: PARTIAL_SCOPE_OVERLAP

AI requires Loyalty Account Number on all loyalty transactions; Test constrains it only when populated and permits alternate identity context.

Selected Test BRs: BR-RULE-SEG108-R-009

Ranked retrieval only:
- BR-SEG108-FIELD-009: Loyalty Account Number is numeric max 24 when populated.
- BR-RULE-SEG108-R-009: Loyalty Account Number, when populated, is numeric with maximum length 24
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2157
If Payment Tender Type is not 'CS', two cards must be swiped: a loyalty card and a payment card.

Disposition: PARTIAL_SCOPE_OVERLAP

Tender field/allowed codes are related; two-card behavior when tender is not CS is not covered by this Test title.

Selected Test BRs: BR-RULE-SEG108-R-017

Ranked retrieval only:
- BR-SEG108-FIELD-017: Payment Tender Type is required and one of the documented values.
- BR-RULE-SEG108-R-017: Payment Tender Type is required, exactly 2 alphanumeric characters from the documented set AX, CK, CS, DB, DN, DS, EB, EC, FL, GC, JC, MC, PC, PR, VS
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2240
BUYPASS Card Type is used by POS Device for Authorization/Pre-Pay transaction discount calculation.

Disposition: PARTIAL_SCOPE_OVERLAP

Test defines Host Discount fields, not the full BIN matching or authorization/prepay discount algorithm.

Selected Test BRs: BR-RULE-SEG118-R-022

Ranked retrieval only:
- BR-RULE-SEG118-R-022: When Prompt Code is 904 (Host Discount Data, response only), fields 14-27 are: Host Discount Timestamp (179, 12), Number of Discounts (171, 2), then a repeated block of Start Date (165,8), Start Time (166,4), End Date (165,8), End Time (166,4), BUYPASS Card Type (173,4), Card BIN Range Beginning (183,12), Card BIN Range Ending (184,12), Product Code (77,3), Product Discount Amount (172,5), Product Code (77,3), Discount Quantity Limit (185,3), Discount Program Description (186,15), for a total variable length not to exceed 3,600 bytes
- BR-RULE-SEG118-R-029: The discount amount from Host Discount Data is carried in a subsequent Financial Transaction Request's Product Code Data Segment (102) using Product Code 941 (negative amount, post-pay environment) or 991 (administrative amount, pre-pay environment)
- BR-RULE-SEG119-R-020: Device Card Table Version is required

Artifact review flags: ["CLAIMED_ELEMENT_NOT_IN_CASE_REQUEST_METADATA", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:2243
POS Device uses Card Table Data to create/replace its Card Table information for the table type in Field 174.

Disposition: PARTIAL_SCOPE_OVERLAP

Table type and Prompt 902 data are supported separately, not the full update/create/replace behavior or conditional field rule.

Selected Test BRs: BR-RULE-SEG118-R-015;BR-RULE-SEG118-R-020

Ranked retrieval only:
- BR-RULE-SEG118-R-020: When Prompt Code is 902 (Dynamic Card Table Data, response only), field 14 is Card Table Data (175, max 3,600 bytes, length determined by the Segment Length in field 2)
- BR-RULE-SEG118-R-015: Card Table Type (Element 174) is Required, field 10, 4 digits, identifying the specific table type this segment addresses, sourced from the Host and echoed back; valid values are 0001 (BIN Table), 0002 (RULES Table), 0003 (RESTRICTIONS Table), 0004 (SAF Table), 0005 (PROMPT Table), 0006 (PRODUCT Table)
- BR-RULE-SEG118-R-025: A Proprietary Data Load Response contains Data Section 1 (Response Code 83 pos.1 len.1, Download Indicator 30 pos.2 len.1, Initiation Date 45 pos.3 len.6, Initiation Time 46 pos.9 len.4, Sequence Number 86 pos.13 len.6, all Required) and Data Section 3 (field 6, Segment 118, Required)

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2244
If Device Card Table Version begins with 99999, there is no card table used by the location.

Disposition: CLAUSE_MATCH_PROPOSED

Test explicitly describes concatenated five-digit versions and the 99999 no-card-table sentinel; each AI BR covers one clause, not the full Test BR.

Selected Test BRs: BR-RULE-SEG118-R-013

Ranked retrieval only:
- BR-RULE-SEG118-R-013: Device Card Table Version (Element 176) is Required, field 8, 35 numeric digits: seven 5-digit version values strung together for the Card Table Master ID, BIN table, RULES table, RESTRICTIONS table, SAF table, PROMPT table, and PRODUCT table versions; a value beginning with 99999 indicates no card table is used at the location
- BR-RULE-SEG119-R-020: Device Card Table Version is required
- BR-RULE-SEG118-R-005: In a Proprietary Data Load Request, all of fields 1-13 are separated by Field Separators, including unpopulated fields, and a Field Separator follows field 13; in a Proprietary Data Load Response, there are no Field Separators between fields (positional)

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2252
Prompt Code, Pending must be a valid Transaction Type and/or Card Type Code.

Disposition: PARTIAL_SCOPE_OVERLAP

Test has a specific pending-code enumeration; generic valid-transaction/card-code wording does not prove that exact allowlist.

Selected Test BRs: BR-RULE-SEG118-R-012

Ranked retrieval only:
- BR-RULE-SEG118-R-012: Prompt Code, Pending (Element 182) is Required, field 7, 4 digits, and must be one of the documented values: 0901 (Custom Receipt Text Data), 0902 (Proprietary Data Load), 0904 (Host Discount Data), 0981 (Electronic Mail)
- BR-SEG100-APPE-FINANCIAL-CARD-TYPES: Financial Segment 100 Prompt Codes shall use one of the 36 Appendix E card types valid for financial transaction Prompt Codes.
- BR-RULE-SEG118-R-028: Host Discount Data (Prompt Code 904) is triggered when a Totals Response's Response Code (83) indicates proprietary/host-discount data pending (values D/E for general proprietary data pending, M/N for Host Discount data pending specifically); the device then sends a Proprietary Data Load Request with Prompt Code 904

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2254
Card BIN Range, Beginning appears in Proprietary Load Response Data, Prompt Code 904 (Host Discount Data).

Disposition: CLAUSE_MATCH_PROPOSED

Test explicitly places BUYPASS Card Type and BIN range endpoints in the Prompt 904 response; AI covers a named field clause only.

Selected Test BRs: BR-RULE-SEG118-R-022

Ranked retrieval only:
- BR-RULE-SEG118-R-022: When Prompt Code is 904 (Host Discount Data, response only), fields 14-27 are: Host Discount Timestamp (179, 12), Number of Discounts (171, 2), then a repeated block of Start Date (165,8), Start Time (166,4), End Date (165,8), End Time (166,4), BUYPASS Card Type (173,4), Card BIN Range Beginning (183,12), Card BIN Range Ending (184,12), Product Code (77,3), Product Discount Amount (172,5), Product Code (77,3), Discount Quantity Limit (185,3), Discount Program Description (186,15), for a total variable length not to exceed 3,600 bytes
- BR-RULE-SEG118-R-012: Prompt Code, Pending (Element 182) is Required, field 7, 4 digits, and must be one of the documented values: 0901 (Custom Receipt Text Data), 0902 (Proprietary Data Load), 0904 (Host Discount Data), 0981 (Electronic Mail)
- BR-RULE-SEG118-R-013: Device Card Table Version (Element 176) is Required, field 8, 35 numeric digits: seven 5-digit version values strung together for the Card Table Master ID, BIN table, RULES table, RESTRICTIONS table, SAF table, PROMPT table, and PRODUCT table versions; a value beginning with 99999 indicates no card table is used at the location

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2259
Once received, the checksum must be used for all subsequent EMV transaction requests from this device.

Disposition: PARTIAL_SCOPE_OVERLAP

Checksum identification/response echo overlap; persistence across every later request is an additional temporal requirement.

Selected Test BRs: BR-RULE-SEG130-R-005;BR-RULE-SEG130-R-015

Ranked retrieval only:
- BR-RULE-SEG130-R-005: CA Public Key File Checksum (Element 187), when populated, identifies the checksum received in the last CA Public Key download currently in use at host and device; Optional in the request, sourced Device/Host
- BR-RULE-SEG130-R-015: CA Public Key File Checksum (Element 187) is echoed by Segment 131 (EMV Response Data Segment, Field 3, Required, Host-sourced) — a request/response handshake pattern used to detect when the device needs a new CA Public Key File download
- BR-RULE-SEG131-R-007: CA Public Key File Checksum (Element 187) is required and is explicitly echoed from the Request (Segment 130's CA Public Key File Checksum)

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2261
EMV Card Sequence Number is required when ICC Data is present and card product includes it in EMV data.

Disposition: PARTIAL_SCOPE_OVERLAP

Card Sequence field is shared; product trigger, absent-5F34 three-space encoding and 000-099 range are not explicit in this Test BR.

Selected Test BRs: BR-RULE-SEG130-R-006

Ranked retrieval only:
- BR-RULE-SEG130-R-006: EMV Card Sequence Number (Element 188), when populated, identifies the sequence number of the EMV card; Conditional, sourced Device
- BR-SEG100-APPR-EMV-COMPANION: An EMV Segment 100 transaction shall include Segment 130 EMV Request Data in Data Section 3.
- BR-SEG100-EMV-001: An EMV purchase must include Segment 130 with Segment 100.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2262
If EMV Tag 5F34 is not provided by the card, send three spaces for EMV Card Sequence Number.

Disposition: PARTIAL_SCOPE_OVERLAP

Card Sequence field is shared; product trigger, absent-5F34 three-space encoding and 000-099 range are not explicit in this Test BR.

Selected Test BRs: BR-RULE-SEG130-R-006

Ranked retrieval only:
- BR-RULE-SEG130-R-006: EMV Card Sequence Number (Element 188), when populated, identifies the sequence number of the EMV card; Conditional, sourced Device
- BR-RULE-SEG130-R-012: Element 118 is reused across segments: it is 'Additional Information' in Segment 112 (max 984, single occurrence) and 'EMV Additional Information' in Segment 130 (variable length, repeating). Do not assume element numbers are globally unique or that the two fields share business meaning
- BR-RULE-SEG105-R-013: Sequence Number is required

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2263
EMV Card Sequence Number valid values are 000-099.

Disposition: PARTIAL_SCOPE_OVERLAP

Card Sequence field is shared; product trigger, absent-5F34 three-space encoding and 000-099 range are not explicit in this Test BR.

Selected Test BRs: BR-RULE-SEG130-R-006

Ranked retrieval only:
- BR-RULE-SEG130-R-006: EMV Card Sequence Number (Element 188), when populated, identifies the sequence number of the EMV card; Conditional, sourced Device
- BR-RULE-SEG105-R-013: Sequence Number is required
- BR-RULE-SEG119-R-019: Sequence Number is required

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2264
EMV Chip Data Length is required in all EMV transactions.

Disposition: CLAUSE_MATCH_PROPOSED

Shared explicit EMV Chip Data Length presence/range clause, 000-999. A range-only AI BR does not cover every Test obligation.

Selected Test BRs: BR-RULE-SEG130-R-007

Ranked retrieval only:
- BR-RULE-SEG130-R-007: EMV Chip Data Length (Element 189) is required and identifies the length of the following EMV Chip Data field; valid values 000-999
- BR-RULE-SEG131-R-008: EMV Chip Data Length (Element 189) and EMV Chip Data (Element 190) are required, mirroring Segment 130's fields, but the field table lists their Source as 'Device' even though Segment 131 as a whole originates at BUYPASS
- BR-SEG100-ANN-R: EMV Segment 100 transactions include Segment 130 and validate Appendix R chip-data framing and context consistency.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2265
EMV Chip Data Length valid values are 000-999.

Disposition: CLAUSE_MATCH_PROPOSED

Shared explicit EMV Chip Data Length presence/range clause, 000-999. A range-only AI BR does not cover every Test obligation.

Selected Test BRs: BR-RULE-SEG130-R-007

Ranked retrieval only:
- BR-RULE-SEG130-R-007: EMV Chip Data Length (Element 189) is required and identifies the length of the following EMV Chip Data field; valid values 000-999
- BR-SEG100-APPR-CHIP-LENGTH: EMV Chip Data shall begin with a valid three-digit length consistent with the following encoded chip data.
- BR-SEG100-APPR-TLV-FORMAT: EMV Chip Data shall preserve valid BCD-packed/hex TLV tag, length, and value structure.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2266
EMV Chip Data is required in all EMV transactions.

Disposition: CLAUSE_MATCH_PROPOSED

Shared required EMV Chip Data clause for the applicable EMV request context; Test adds TLV/framing constraints and reversal exceptions must remain visible.

Selected Test BRs: BR-RULE-SEG130-R-008

Ranked retrieval only:
- BR-RULE-SEG130-R-008: EMV Chip Data (Element 190) is required, contains TLV-encoded BCD-packed/hex chip data, and must begin with a valid three-digit length consistent with the following encoded chip data
- BR-RULE-SEG130-R-010: Tag 9F26 (Application Cryptogram) authenticity verification is explicitly out of scope for AI/synthetic test generation; it requires a certified EMV kernel or HSM
- BR-RULE-SEG130-R-007: EMV Chip Data Length (Element 189) is required and identifies the length of the following EMV Chip Data field; valid values 000-999

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2267
All EMV data elements must contain exact values present on the card to terminal interface.

Disposition: PARTIAL_SCOPE_OVERLAP

Transaction cross-field consistency does not prove equality to independently captured card-interface data.

Selected Test BRs: BR-RULE-SEG130-R-009;BR-SEG100-APPR-CROSS-FIELD

Ranked retrieval only:
- BR-RULE-SEG130-R-008: EMV Chip Data (Element 190) is required, contains TLV-encoded BCD-packed/hex chip data, and must begin with a valid three-digit length consistent with the following encoded chip data
- BR-RULE-SEG130-R-010: Tag 9F26 (Application Cryptogram) authenticity verification is explicitly out of scope for AI/synthetic test generation; it requires a certified EMV kernel or HSM
- BR-RULE-SEG130-R-006: EMV Card Sequence Number (Element 188), when populated, identifies the sequence number of the EMV card; Conditional, sourced Device

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2269
AID (tag 9F06) or Dedicated File Name (tag 84) must be included in EMV Chip Data for proper EMV processing.

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact code/table/tag meaning and context have no defensible counterpart established in this review. Generic segment/field matches and shared numbers are insufficient.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG130-R-008: EMV Chip Data (Element 190) is required, contains TLV-encoded BCD-packed/hex chip data, and must begin with a valid three-digit length consistent with the following encoded chip data
- BR-RULE-SEG130-R-010: Tag 9F26 (Application Cryptogram) authenticity verification is explicitly out of scope for AI/synthetic test generation; it requires a certified EMV kernel or HSM
- BR-SEG100-APPS-CA-EMV-LINK: An EMV Segment 100/130 transaction shall use CA key configuration consistent with the applicable EMV AID and public-key data.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2270
Tag '5A' (PAN) and Tag '57' (Track 2 equivalent data) must not be included in EMV Chip Data.

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact code/table/tag meaning and context have no defensible counterpart established in this review. Generic segment/field matches and shared numbers are insufficient.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG130-R-008: EMV Chip Data (Element 190) is required, contains TLV-encoded BCD-packed/hex chip data, and must begin with a valid three-digit length consistent with the following encoded chip data
- BR-RULE-SEG130-R-010: Tag 9F26 (Application Cryptogram) authenticity verification is explicitly out of scope for AI/synthetic test generation; it requires a certified EMV kernel or HSM
- BR-SEG100-APPR-TLV-FORMAT: EMV Chip Data shall preserve valid BCD-packed/hex TLV tag, length, and value structure.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2281
For a declined EMV load transaction, an error message is returned in this field and displays at the device.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEGDL4-R-006: Segment DL4 is Required in the Software Load Response (Data Block No. 1, after the Start-of-Data Block Indicator ')'), is sent together with Segment DL5 (also Required), and is sent only on a Software Load Response; the load requires merchant load flag SOFT, otherwise only an error message block and a terminating block are returned
- BR-TXV-ACCOUNT-UPDATE: Account update
- BR-RULE-SEG119-R-020: Device Card Table Version is required

Artifact review flags: ["CLAIMED_ELEMENT_NOT_IN_CASE_REQUEST_METADATA", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:2356
Extended Unit of Measure for MasterCard Enhanced transactions not expected enabled in FirstData production before June 2026; for dev/certification only until then.

Disposition: PARTIAL_SCOPE_OVERLAP

June 2026 rollout limitation overlaps, but the AI specifically scopes Extended Unit of Measure rather than the full enhanced-fleet feature.

Selected Test BRs: BR-RULE-SEG145-R-004

Ranked retrieval only:
- BR-RULE-SEG145-R-004: MasterCard Enhanced Fleet EMV functionality was, as of the specification's authoring, expected to be enabled in First Data production no earlier than June 2026; until then it is for development/certification preparation only and must not be used in live production transactions
- BR-RULE-SEG102-R-011: Unit of Measure uses an allowed value
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)

Artifact review flags: ["CLAIMED_ELEMENT_NOT_IN_CASE_REQUEST_METADATA", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:2366
Prompt Code value 020 identifies Transaction Type as point-of-sale purchase/capture (0) and Card Type as credit (20).

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact code/table/tag meaning and context have no defensible counterpart established in this review. Generic segment/field matches and shared numbers are insufficient.

Selected Test BRs: None established

Ranked retrieval only:
- BR-TXV-SALE-UPDATE: Sale update
- BR-TXV-POINTS-REDEEM: Points redemption
- BR-SEG100-E78-001: Prompt Code first position identifies a supported transaction type.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2387
Alphabetical State Codes are used in Address Line 2, Card Discretionary Block Data, and State Code elements.

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact code/table/tag meaning and context have no defensible counterpart established in this review. Generic segment/field matches and shared numbers are insufficient.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEGDL1-R-010: Address Line 2 (Element 4, 21 characters) is positional: positions 1-12 city, 13 space, 14-15 alphabetic State Code (Appendix D), 16 space, 17-21 ZIP Code
- BR-RULE-SEGDL1-R-003: Merchant Name(53,24), Store Number(98,16), Address Line 1(3,24), Address Line 2(4,21), Merchant Phone Number(54,13) are all required, Host-sourced fixed-length fields
- BR-SEG100-APPD-ALPHA-STATE: Alphabetical state codes used in Card Discretionary Block Data or state context shall use an Appendix D alphabetical code.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2395
Card Type codes 001-086 are used in 4-character Prompt Codes; codes 127-168 are not used in any Prompt Codes.

Disposition: PARTIAL_SCOPE_OVERLAP

Financial and special code families are separated; AI ranges/combinations need exact allowlist comparison, not a generic range assumption.

Selected Test BRs: BR-SEG100-APPE-FINANCIAL-CARD-TYPES;BR-SEG100-APPE-SPECIAL-SEPARATION

Ranked retrieval only:
- BR-SEG100-ANN-E: Segment 100 financial Prompt Codes use valid Appendix E card types and separate special Prompt Code families.
- BR-TX-9-SPECIAL-PROMPT-CODE-THREE-CHARACTER-FORMAT: Special transaction Prompt Codes shall use the three-character special-data code without appending a financial card type.
- BR-TXV-ACCOUNT-UPDATE: Account update

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2396
Card Type (Element 14) can be used alone or preceded by a Transaction Type code to form a valid Prompt Code (Element 78).

Disposition: PARTIAL_SCOPE_OVERLAP

Card Type contributes to Prompt Code, but the AI general/eWIC statement and financial Test scope are not fully equivalent.

Selected Test BRs: BR-SEG100-E78-002;BR-SEG100-APPE-PROMPT-COMPOSITION

Ranked retrieval only:
- BR-TXV-ACCOUNT-UPDATE: Account update
- BR-TXV-ACCOUNT-INQUIRY: Account inquiry
- BR-SEG100-ANN-E: Segment 100 financial Prompt Codes use valid Appendix E card types and separate special Prompt Code families.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2400
Code 162 configures device to prompt for cardholder's address for manually keyed credit card sales.

Disposition: PARTIAL_SCOPE_OVERLAP

Test recognizes Table Load feature codes, but the AI-specific 162/148/165 device behavior is not established by code recognition alone.

Selected Test BRs: BR-SEG100-APPE-TABLE-LOAD-FAMILY;BR-RULE-SEGDL1-R-009

Ranked retrieval only:
- BR-RULE-SEG118-R-015: Card Table Type (Element 174) is Required, field 10, 4 digits, identifying the specific table type this segment addresses, sourced from the Host and echoed back; valid values are 0001 (BIN Table), 0002 (RULES Table), 0003 (RESTRICTIONS Table), 0004 (SAF Table), 0005 (PROMPT Table), 0006 (PRODUCT Table)
- BR-RULE-SEG118-R-022: When Prompt Code is 904 (Host Discount Data, response only), fields 14-27 are: Host Discount Timestamp (179, 12), Number of Discounts (171, 2), then a repeated block of Start Date (165,8), Start Time (166,4), End Date (165,8), End Time (166,4), BUYPASS Card Type (173,4), Card BIN Range Beginning (183,12), Card BIN Range Ending (184,12), Product Code (77,3), Product Discount Amount (172,5), Product Code (77,3), Discount Quantity Limit (185,3), Discount Program Description (186,15), for a total variable length not to exceed 3,600 bytes
- BR-RULE-SEG118-R-028: Host Discount Data (Prompt Code 904) is triggered when a Totals Response's Response Code (83) indicates proprietary/host-discount data pending (values D/E for general proprietary data pending, M/N for Host Discount data pending specifically); the device then sends a Proprietary Data Load Request with Prompt Code 904

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2408
Code 148 truncates account number to four digits and omits expiration date on customer receipt copy.

Disposition: PARTIAL_SCOPE_OVERLAP

Test recognizes Table Load feature codes, but the AI-specific 162/148/165 device behavior is not established by code recognition alone.

Selected Test BRs: BR-SEG100-APPE-TABLE-LOAD-FAMILY;BR-RULE-SEGDL1-R-009

Ranked retrieval only:
- BR-RULE-SEG118-R-015: Card Table Type (Element 174) is Required, field 10, 4 digits, identifying the specific table type this segment addresses, sourced from the Host and echoed back; valid values are 0001 (BIN Table), 0002 (RULES Table), 0003 (RESTRICTIONS Table), 0004 (SAF Table), 0005 (PROMPT Table), 0006 (PRODUCT Table)
- BR-RULE-SEG108-R-016: Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2409
Code 165 allows manually keyed gift card transactions.

Disposition: PARTIAL_SCOPE_OVERLAP

Test recognizes Table Load feature codes, but the AI-specific 162/148/165 device behavior is not established by code recognition alone.

Selected Test BRs: BR-SEG100-APPE-TABLE-LOAD-FAMILY;BR-RULE-SEGDL1-R-009

Ranked retrieval only:
- BR-RULE-SEG118-R-015: Card Table Type (Element 174) is Required, field 10, 4 digits, identifying the specific table type this segment addresses, sourced from the Host and echoed back; valid values are 0001 (BIN Table), 0002 (RULES Table), 0003 (RESTRICTIONS Table), 0004 (SAF Table), 0005 (PROMPT Table), 0006 (PRODUCT Table)
- BR-RULE-SEG118-R-022: When Prompt Code is 904 (Host Discount Data, response only), fields 14-27 are: Host Discount Timestamp (179, 12), Number of Discounts (171, 2), then a repeated block of Start Date (165,8), Start Time (166,4), End Date (165,8), End Time (166,4), BUYPASS Card Type (173,4), Card BIN Range Beginning (183,12), Card BIN Range Ending (184,12), Product Code (77,3), Product Discount Amount (172,5), Product Code (77,3), Discount Quantity Limit (185,3), Discount Program Description (186,15), for a total variable length not to exceed 3,600 bytes
- BR-RULE-SEG118-R-016: Load Control Key (Element 178) is Required, field 11, 60 characters, sourced from the Host and echoed back in each subsequent block request

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2410
Card Type codes are preceded by a valid Transaction Type to create a valid Prompt Code.

Disposition: CLAUSE_MATCH_PROPOSED

Same financial Prompt Code construction from transaction type and card type; each allowlist and exact position must also be checked.

Selected Test BRs: BR-SEG100-APPE-PROMPT-COMPOSITION;BR-SEG100-E78-001;BR-SEG100-E78-002

Ranked retrieval only:
- BR-TXV-ACCOUNT-UPDATE: Account update
- BR-TXV-ACCOUNT-INQUIRY: Account inquiry
- BR-SEG100-ANN-E: Segment 100 financial Prompt Codes use valid Appendix E card types and separate special Prompt Code families.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2413
A valid Prompt Code for a special transaction is composed of the 3-character Card Type data element only.

Disposition: CLAUSE_MATCH_PROPOSED

Same special three-character Prompt Code without a financial transaction-type prefix.

Selected Test BRs: BR-TX-9-SPECIAL-PROMPT-CODE-THREE-CHARACTER-FORMAT

Ranked retrieval only:
- BR-TX-9-SPECIAL-PROMPT-CODE-THREE-CHARACTER-FORMAT: Special transaction Prompt Codes shall use the three-character special-data code without appending a financial card type.
- BR-SEG100-ANN-E: Segment 100 financial Prompt Codes use valid Appendix E card types and separate special Prompt Code families.
- BR-TX9-PROMPT-990: Totals

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2434
Prompt Code is composed of a Transaction Type code and a Card Type code.

Disposition: CLAUSE_MATCH_PROPOSED

Same financial Prompt Code construction from transaction type and card type; each allowlist and exact position must also be checked.

Selected Test BRs: BR-SEG100-APPE-PROMPT-COMPOSITION;BR-SEG100-E78-001;BR-SEG100-E78-002

Ranked retrieval only:
- BR-TXV-ACCOUNT-UPDATE: Account update
- BR-TXV-ACCOUNT-INQUIRY: Account inquiry
- BR-RULE-SEG100-R-018: Prompt Code represents transaction and card context

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2436
Transaction Type code 9 can only be combined with Card Type codes 00 and 81-97.

Disposition: PARTIAL_SCOPE_OVERLAP

Financial and special code families are separated; AI ranges/combinations need exact allowlist comparison, not a generic range assumption.

Selected Test BRs: BR-SEG100-APPE-FINANCIAL-CARD-TYPES;BR-SEG100-APPE-SPECIAL-SEPARATION

Ranked retrieval only:
- BR-TXV-ACCOUNT-UPDATE: Account update
- BR-TXV-ACCOUNT-INQUIRY: Account inquiry
- BR-TXV-ADD-ACCOUNT: Add account

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2509
Card Acceptor Terminal Identifier Information is required for multi-controller TA-VE transactions.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG105-R-004: Terminal Identifier is required
- BR-RULE-SEG119-R-011: Terminal Identifier is required
- BR-RULE-SEG109-R-010: Terminal Identifier is required and uses the approved device identifier format

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:2571
Visa and MasterCard mandate cryptocurrency purchase attribute in all related transactions.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-SEG100-ANN-L: Currency values in Segment 100-related transaction and variable data use valid Appendix L currency codes.
- BR-RULE-SEG156-R-003: EV Transaction Indicator (Table 01) is mandatory for an EV transaction and must be 'Y'; if the EV segment is sent without this indicator set to 'Y', BUYPASS will decline the transaction
- BR-RULE-SEG155-R-003: Most sub-tables (Card Number 001, TransArmor Token 002, Expiration Date 003, Card Status 004, Original Response Code 005) are included ONLY when the merchant sent the Account Updater Request Indicator (Segment 111 Table 060 Sub-table 01) with value 'Y' (or 'Y'/'I' for some fields) in the request, and are applicable ONLY to Visa and MasterCard

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:2572
MasterCard mandates high risk security attribute in all related transactions.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-SEG100-ANN-L: Currency values in Segment 100-related transaction and variable data use valid Appendix L currency codes.
- BR-RULE-SEG156-R-003: EV Transaction Indicator (Table 01) is mandatory for an EV transaction and must be 'Y'; if the EV segment is sent without this indicator set to 'Y', BUYPASS will decline the transaction
- BR-SEG100-RECEIPT-CARDTYPE-MASTERCARD: MasterCard maps to receipt Card Type ID MC.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:2610
Program Protocol field applies to merchants sending e-Commerce transactions with 3D Secure version or EMV 3D Secure authentication.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)
- BR-SEG103-EBT-017: EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.
- BR-RULE-SEG103-R-017: EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE fixed value 98 (not required when TAG is IT)

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:2630
Value 'IR' (Purchase Repayment) is applicable to Mastercard transactions only.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG155-R-003: Most sub-tables (Card Number 001, TransArmor Token 002, Expiration Date 003, Card Status 004, Original Response Code 005) are included ONLY when the merchant sent the Account Updater Request Indicator (Segment 111 Table 060 Sub-table 01) with value 'Y' (or 'Y'/'I' for some fields) in the request, and are applicable ONLY to Visa and MasterCard
- BR-SEG100-ANN-O: Tokenized Segment 100 transactions use Appendix O token account, expiration, cryptogram, and presentment concepts where applicable.
- BR-SEG100-ANN-I: Conditional Segment 111 Variable Information uses the applicable Appendix I Table ID and layout.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:2631
For Mastercard Purchase repayment, Installment payment indicator must be submitted in Data Segment 111, Table ID 013 with value 'I'.

Disposition: PARTIAL_SCOPE_OVERLAP

Generic indicator/length rules do not establish individual Table ID, sub-table, value, network or CIT/MIT applicability. No table-specific match credit.

Selected Test BRs: BR-RULE-SEG111-R-003;BR-RULE-SEG111-R-004

Ranked retrieval only:
- BR-RULE-SEG155-R-003: Most sub-tables (Card Number 001, TransArmor Token 002, Expiration Date 003, Card Status 004, Original Response Code 005) are included ONLY when the merchant sent the Account Updater Request Indicator (Segment 111 Table 060 Sub-table 01) with value 'Y' (or 'Y'/'I' for some fields) in the request, and are applicable ONLY to Visa and MasterCard
- BR-RULE-SEG111-R-003: Indicator length
- BR-SEG100-ANN-O: Tokenized Segment 100 transactions use Appendix O token account, expiration, cryptogram, and presentment concepts where applicable.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:2632
For Mastercard Purchase repayment, Merchant name must be submitted in Data Segment 111, Table ID 047 as 'Provider*Merchant Name'.

Disposition: PARTIAL_SCOPE_OVERLAP

Generic indicator/length rules do not establish individual Table ID, sub-table, value, network or CIT/MIT applicability. No table-specific match credit.

Selected Test BRs: BR-RULE-SEG111-R-003;BR-RULE-SEG111-R-004

Ranked retrieval only:
- BR-RULE-SEGDL1-R-003: Merchant Name(53,24), Store Number(98,16), Address Line 1(3,24), Address Line 2(4,21), Merchant Phone Number(54,13) are all required, Host-sourced fixed-length fields
- BR-RULE-SEG155-R-003: Most sub-tables (Card Number 001, TransArmor Token 002, Expiration Date 003, Card Status 004, Original Response Code 005) are included ONLY when the merchant sent the Account Updater Request Indicator (Segment 111 Table 060 Sub-table 01) with value 'Y' (or 'Y'/'I' for some fields) in the request, and are applicable ONLY to Visa and MasterCard
- BR-SEG100-APPY-MASTERCARD-UCAF-DSRP: MasterCard AAV cryptogram data shall use Segment 111 Table ID 36 (UCAF Data) while Token/DSRP cryptograms shall use Segment 123 Element 237 (TAVV); when both are present, the UCAF Security Level Code shall be '21' (Channel Encrypted).

Artifact review flags: ["CLAIMED_ELEMENT_NOT_IN_CASE_REQUEST_METADATA"]

## REQ-SRC-ATL105-PDF-001:2638
"AC","AS","CC","CS" are valid codes for MasterCard and Maestro.

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact code/table/tag meaning and context have no defensible counterpart established in this review. Generic segment/field matches and shared numbers are insufficient.

Selected Test BRs: None established

Ranked retrieval only:
- BR-SEG100-RECEIPT-CARDTYPE-MASTERCARD: MasterCard maps to receipt Card Type ID MC.
- BR-SEG100-APPE-FINANCIAL-CARD-TYPES: Financial Segment 100 Prompt Codes shall use one of the 36 Appendix E card types valid for financial transaction Prompt Codes.
- BR-RULE-SEG155-R-003: Most sub-tables (Card Number 001, TransArmor Token 002, Expiration Date 003, Card Status 004, Original Response Code 005) are included ONLY when the merchant sent the Account Updater Request Indicator (Segment 111 Table 060 Sub-table 01) with value 'Y' (or 'Y'/'I' for some fields) in the request, and are applicable ONLY to Visa and MasterCard

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:2693
Remote Commerce Acceptor Identifier applies only to MasterCard transactions where merchant also sends DSRP or TAVV data in segment 123.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG123-R-005: Segment 123 is required on all initial and recurring transactions involving tokenized data, and on transactions that include MasterCard Token/DSRP or Visa TAVV data
- BR-SEG100-APPY-MASTERCARD-UCAF-DSRP: MasterCard AAV cryptogram data shall use Segment 111 Table ID 36 (UCAF Data) while Token/DSRP cryptograms shall use Segment 123 Element 237 (TAVV); when both are present, the UCAF Security Level Code shall be '21' (Channel Encrypted).
- BR-RULE-SEG123-R-010: TAVV Cryptogram (Element 237, 28 bytes, base64 alphabet a-z/A-Z/0-9/+/=// ) may only be populated by the merchant during the transaction request, not the response

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:2723
Validation Flag field is applicable only to Discover and Visa card transactions.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG156-R-001: Segment 156 is sent only for an EV charging transaction (EV fuel code in Segment 102 Product Code Segment), and is applicable to the Visa card type only
- BR-SEG100-RECEIPT-CARDTYPE-DISCOVER: Discover Network maps to receipt Card Type ID DS.
- BR-RULE-SEG155-R-003: Most sub-tables (Card Number 001, TransArmor Token 002, Expiration Date 003, Card Status 004, Original Response Code 005) are included ONLY when the merchant sent the Account Updater Request Indicator (Segment 111 Table 060 Sub-table 01) with value 'Y' (or 'Y'/'I' for some fields) in the request, and are applicable ONLY to Visa and MasterCard

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:2740
Recurring Payment Type field is applicable only to MasterCard transactions, per note.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG156-R-001: Segment 156 is sent only for an EV charging transaction (EV fuel code in Segment 102 Product Code Segment), and is applicable to the Visa card type only
- BR-RULE-SEG155-R-003: Most sub-tables (Card Number 001, TransArmor Token 002, Expiration Date 003, Card Status 004, Original Response Code 005) are included ONLY when the merchant sent the Account Updater Request Indicator (Segment 111 Table 060 Sub-table 01) with value 'Y' (or 'Y'/'I' for some fields) in the request, and are applicable ONLY to Visa and MasterCard
- BR-SEG100-RECEIPT-CARDTYPE-MASTERCARD: MasterCard maps to receipt Card Type ID MC.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:2848
Extended Unit of Measure must be one of 36 abbreviations set by MasterCard Enhanced Fleet Transactions.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG102-R-011: Unit of Measure uses an allowed value
- BR-RULE-SEG145-R-003: Only the Prompt Table sub-segment (Table ID 004) is valid for Voyager EMV, Visa Fleet 2.0, Comdata, and MasterCard Enhanced Fleet EMV transactions — other sub-segment tables (001, 002, 006, 007, 008) are restricted to different authorizer contexts not enumerated by this restriction
- BR-SEG101-CORE-002: Fleet Data Segment is required in every fleet-card financial transaction request.

Artifact review flags: ["CLAIMED_ELEMENT_NOT_IN_CASE_REQUEST_METADATA", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:285
Card Type codes are also used to construct Prompt Code.

Disposition: PARTIAL_SCOPE_OVERLAP

Card Type contributes to Prompt Code, but the AI general/eWIC statement and financial Test scope are not fully equivalent.

Selected Test BRs: BR-SEG100-E78-002;BR-SEG100-APPE-PROMPT-COMPOSITION

Ranked retrieval only:
- BR-TXV-ACCOUNT-UPDATE: Account update
- BR-TXV-ACCOUNT-INQUIRY: Account inquiry
- BR-RULE-SEG100-R-018: Prompt Code represents transaction and card context

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2860
A valid 3-digit Point-of-Service Entry Mode code has a 2-digit PAN Entry Mode code in positions 1-2 and a 1-digit Terminal Capability Mode code in position 3.

Disposition: PARTIAL_SCOPE_OVERLAP

POS entry-mode appendix/lifecycle topic overlaps; exact three-digit positions, Discover prohibitions and 90-to-02 exception need explicit source/Test assertions.

Selected Test BRs: BR-SEG100-ANN-J

Ranked retrieval only:
- BR-SEG100-ANN-J: POS Entry Mode in Segment 111 uses Appendix J PAN and terminal capability values and persists through lifecycle exceptions.
- BR-SEG100-APPO-ENTRY-MODE: Tokenized Segment 100 transactions shall use a POS Entry Mode that identifies the token presentment mode.
- BR-SEG100-ANN-O: Tokenized Segment 100 transactions use Appendix O token account, expiration, cryptogram, and presentment concepts where applicable.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2862
Voids and Completions can only pass account number and expiration date due to credit/debit authorizer regulatory requirements on full track data storage.

Disposition: PARTIAL_SCOPE_OVERLAP

Account/track representation overlaps; void/completion-only track restrictions are not fully captured by generic field rules.

Selected Test BRs: BR-RULE-SEG100-R-034;BR-RULE-SEG100-R-042

Ranked retrieval only:
- BR-SEG100-ANN-O: Tokenized Segment 100 transactions use Appendix O token account, expiration, cryptogram, and presentment concepts where applicable.
- BR-RULE-SEG110-R-020: The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format codes 'T$' (T<aba>A<acct>C<checknum>), '18' (<aba><acct> concatenated), '09' (raw MICR, not touched), and '19' (numerals from bottom of check); Segment 110's MICR Data (122) and this Segment 111 sub-table are companions, confirmed independently of the AI-generated requirement set, which had attributed these codes to Segment 110 with LOW-MEDIUM confidence (44-47%)
- BR-RULE-SEG116-R-008: A companion sub-table, 'Additional TransArmor Data' (Segment 111, Variable Information Indicator/Table ID 052), carries a longer Key ID and/or special device type via two documented sub-tables: Sub-Table ID 01 (KSN, up to 40 bytes, required for AES DUKPT, TDES, and Ingenico OnGuard encryption types) and Sub-Table ID 02 (Device Type, up to 8 bytes, required for AES DUKPT, optional for TDES and Ingenico OnGuard); this is a Segment 111 companion table, not a Segment 116 field

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2863
Voids/Completions use same PAN Entry Mode code as original transaction, except code 02 is used when original used code 90.

Disposition: PARTIAL_SCOPE_OVERLAP

POS entry-mode appendix/lifecycle topic overlaps; exact three-digit positions, Discover prohibitions and 90-to-02 exception need explicit source/Test assertions.

Selected Test BRs: BR-SEG100-ANN-J

Ranked retrieval only:
- BR-SEG100-APPO-ENTRY-MODE: Tokenized Segment 100 transactions shall use a POS Entry Mode that identifies the token presentment mode.
- BR-SEG100-ANN-J: POS Entry Mode in Segment 111 uses Appendix J PAN and terminal capability values and persists through lifecycle exceptions.
- BR-SEG100-ANN-O: Tokenized Segment 100 transactions use Appendix O token account, expiration, cryptogram, and presentment concepts where applicable.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2867
For Discover, POS Entry Mode 10 must NOT be present for QR Code/Bar Code/Payment Code transactions or Card Sales with stored/provisioned card info.

Disposition: PARTIAL_SCOPE_OVERLAP

POS entry-mode appendix/lifecycle topic overlaps; exact three-digit positions, Discover prohibitions and 90-to-02 exception need explicit source/Test assertions.

Selected Test BRs: BR-SEG100-ANN-J

Ranked retrieval only:
- BR-SEG100-APPO-ENTRY-MODE: Tokenized Segment 100 transactions shall use a POS Entry Mode that identifies the token presentment mode.
- BR-SEG100-ANN-J: POS Entry Mode in Segment 111 uses Appendix J PAN and terminal capability values and persists through lifecycle exceptions.
- BR-SEG100-ANN-O: Tokenized Segment 100 transactions use Appendix O token account, expiration, cryptogram, and presentment concepts where applicable.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2868
For Discover, Entry Mode 82 must NOT be assigned when cardholder uses stored card account information; must use 10 instead.

Disposition: PARTIAL_SCOPE_OVERLAP

POS entry-mode appendix/lifecycle topic overlaps; exact three-digit positions, Discover prohibitions and 90-to-02 exception need explicit source/Test assertions.

Selected Test BRs: BR-SEG100-ANN-J

Ranked retrieval only:
- BR-SEG100-ANN-J: POS Entry Mode in Segment 111 uses Appendix J PAN and terminal capability values and persists through lifecycle exceptions.
- BR-SEG100-APPO-ENTRY-MODE: Tokenized Segment 100 transactions shall use a POS Entry Mode that identifies the token presentment mode.
- BR-SEG100-ANN-I: Conditional Segment 111 Variable Information uses the applicable Appendix I Table ID and layout.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:288
State Code entries construct Address Line 2, Card Discretionary Block Data, Terminal Identifier, and State Code elements.

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact code/table/tag meaning and context have no defensible counterpart established in this review. Generic segment/field matches and shared numbers are insufficient.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEGDL1-R-010: Address Line 2 (Element 4, 21 characters) is positional: positions 1-12 city, 13 space, 14-15 alphabetic State Code (Appendix D), 16 space, 17-21 ZIP Code
- BR-RULE-SEGDL1-R-003: Merchant Name(53,24), Store Number(98,16), Address Line 1(3,24), Address Line 2(4,21), Merchant Phone Number(54,13) are all required, Host-sourced fixed-length fields
- BR-SEG100-ANN-D: Segment 100 Terminal Identifier and conditional state context use valid Appendix D state-code families.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2885
Code 016 indicates BUYPASS Card Type Code.

Disposition: PARTIAL_SCOPE_OVERLAP

Response indicator/length/value structure overlaps; the named code meaning and exact fixed length require a table-specific Test BR.

Selected Test BRs: BR-RULE-SEG112-R-007;BR-RULE-SEG112-R-008;BR-RULE-SEG112-R-009

Ranked retrieval only:
- BR-RULE-SEG130-R-012: Element 118 is reused across segments: it is 'Additional Information' in Segment 112 (max 984, single occurrence) and 'EMV Additional Information' in Segment 130 (variable length, repeating). Do not assume element numbers are globally unique or that the two fields share business meaning
- BR-RULE-SEG112-R-009: Additional Information (Element 118) is variable length and carries the value identified by its paired Indicator/Length
- BR-RULE-SEG131-R-010: Element 118 is reused across three segments now documented in this KB: Segment 112 ('Additional Information', single occurrence), Segment 130 ('EMV Additional Information', repeating, request-side), and Segment 131 ('EMV Additional Information', repeating, response-side)

Artifact review flags: ["CLAIMED_ELEMENT_NOT_IN_CASE_REQUEST_METADATA"]

## REQ-SRC-ATL105-PDF-001:2887
Code 013 indicates Expiration Date for TransArmor - VeriFone edition processing.

Disposition: PARTIAL_SCOPE_OVERLAP

Response indicator/length/value structure overlaps; the named code meaning and exact fixed length require a table-specific Test BR.

Selected Test BRs: BR-RULE-SEG112-R-007;BR-RULE-SEG112-R-008;BR-RULE-SEG112-R-009

Ranked retrieval only:
- BR-RULE-SEG130-R-012: Element 118 is reused across segments: it is 'Additional Information' in Segment 112 (max 984, single occurrence) and 'EMV Additional Information' in Segment 130 (variable length, repeating). Do not assume element numbers are globally unique or that the two fields share business meaning
- BR-RULE-SEG112-R-009: Additional Information (Element 118) is variable length and carries the value identified by its paired Indicator/Length
- BR-RULE-SEG131-R-010: Element 118 is reused across three segments now documented in this KB: Segment 112 ('Additional Information', single occurrence), Segment 130 ('EMV Additional Information', repeating, request-side), and Segment 131 ('EMV Additional Information', repeating, response-side)

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2946
If no value has been stored at the device, the default value 1249 is sent for Card Expiration Date.

Disposition: PARTIAL_SCOPE_OVERLAP

MMYY/default 1249 is explicit for loyalty Segment 108, but gift/other response contexts and YYMM alternatives must not be conflated.

Selected Test BRs: BR-RULE-SEG108-R-016

Ranked retrieval only:
- BR-RULE-SEG108-R-016: Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card
- BR-RULE-SEG108-R-019: Loyalty Information Version, when populated, is numeric length 1 with valid values 1 or 2; defaults to 1 when not sent
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2947
Card Expiration Date value may be either MMYY or YYMM depending on transaction context.

Disposition: PARTIAL_SCOPE_OVERLAP

MMYY/default 1249 is explicit for loyalty Segment 108, but gift/other response contexts and YYMM alternatives must not be conflated.

Selected Test BRs: BR-RULE-SEG108-R-016

Ranked retrieval only:
- BR-RULE-SEG108-R-016: Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)
- BR-TXV-EXPIRATION-UPDATE: Expiration date update

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:2949
Card Accepted As field is mandatory for requests where POS device sent Segment 111, Table 21 (Settlement Data Acceptance Flag).

Disposition: PARTIAL_SCOPE_OVERLAP

Lifecycle/context rules are related, but returned D/S routing and Table 21 triggers are not stated by these Test BRs.

Selected Test BRs: BR-RULE-SEG100-R-037;BR-RULE-SEG100-R-033

Ranked retrieval only:
- BR-RULE-SEG156-R-003: EV Transaction Indicator (Table 01) is mandatory for an EV transaction and must be 'Y'; if the EV segment is sent without this indicator set to 'Y', BUYPASS will decline the transaction
- BR-RULE-SEG155-R-003: Most sub-tables (Card Number 001, TransArmor Token 002, Expiration Date 003, Card Status 004, Original Response Code 005) are included ONLY when the merchant sent the Account Updater Request Indicator (Segment 111 Table 060 Sub-table 01) with value 'Y' (or 'Y'/'I' for some fields) in the request, and are applicable ONLY to Visa and MasterCard
- BR-SEG100-APPO-ENTRY-MODE: Tokenized Segment 100 transactions shall use a POS Entry Mode that identifies the token presentment mode.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:3058
Card's serial number is created from first four bytes of Discretionary Data and nine bytes following the 6-byte BIN in Account Number.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-SEG100-APPO-TOKEN-ACCOUNT: A payment token used as Segment 100 Account Number shall be a valid 13-19 digit token value subject to account validation and designated token BIN rules.
- BR-SEG100-APPO-TOKEN-LUHN: A payment token shall pass basic account-number validation rules, including the LUHN check, and shall fall within a BIN range designated and flagged as a token BIN range.
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:3059
Account Number is required to be 16 digits using First Data Premium Gift Card ISO assigned BIN.

Disposition: PARTIAL_SCOPE_OVERLAP

Test establishes account/identification presence in context, not the AI-specific lengths, response representation, code values or account semantics.

Selected Test BRs: BR-RULE-SEG100-R-041

Ranked retrieval only:
- BR-SEG100-APPO-TOKEN-ACCOUNT: A payment token used as Segment 100 Account Number shall be a valid 13-19 digit token value subject to account validation and designated token BIN rules.
- BR-SEG100-APPO-TOKEN-LUHN: A payment token shall pass basic account-number validation rules, including the LUHN check, and shall fall within a BIN range designated and flagged as a token BIN range.
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:3060
PVKI is not currently used by the First Data Premium Gift Card system.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-TX-L-TRANSACTION-TYPE-L-FIRST-DATA-CASHOUT: For First Data Premium Gift Card cash-out, the amount is greater than zero and no more than the balance; the remaining balance becomes zero and the used amount is returned as Approved Amount.
- BR-TX-L-TRANSACTION-TYPE-L-STANDARD-GIFT-DEACTIVATION: For non-First-Data Standard Gift Card deactivation, the requested amount equals the account balance; after success the account may be closed according to client configuration.
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:3072
Position Nos. 4-7 value 0000 means Customer and card present.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context
- BR-RULE-SEG101-R-017: Customer Data, when populated, is alphanumeric with maximum length 12
- BR-RULE-SEG110-R-015: Customer Last Name (Element 129) is optional, alphanumeric, maximum 24 bytes

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:3086
EMVYES indicates device is able to receive EMV Table Data; sent only in an EMV Transaction request.

Disposition: CLAUSE_MATCH_PROPOSED

Same EMVYES/EMVNOT capability meaning in EMV-request Table 001; response echo is an additional Test obligation.

Selected Test BRs: BR-RULE-SEG130-R-020;BR-SEG100-APPT-EMV-TABLE-DATA

Ranked retrieval only:
- BR-RULE-SEG130-R-020: Appendix T Table ID 001 (EMV Table Data) carries exactly EMVYES or EMVNOT, length 6, sent only in an EMV Transaction request; if returned in an authorization response the device echoes it on any subsequent advice or batch upload request
- BR-RULE-SEG130-R-006: EMV Card Sequence Number (Element 188), when populated, identifies the sequence number of the EMV card; Conditional, sourced Device
- BR-RULE-SEG130-R-001: Segment 130 is Required in every EMV authorization-class Financial Transaction Request; the Section 11.8.1 Data Section 3 table marks it R with the note 'This segment is the only segment required for all EMV financial transactions'

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:3087
EMVNOT indicates device is unable to receive EMV Table Data; sent only in an EMV Transaction request.

Disposition: CLAUSE_MATCH_PROPOSED

Same EMVYES/EMVNOT capability meaning in EMV-request Table 001; response echo is an additional Test obligation.

Selected Test BRs: BR-RULE-SEG130-R-020;BR-SEG100-APPT-EMV-TABLE-DATA

Ranked retrieval only:
- BR-RULE-SEG130-R-020: Appendix T Table ID 001 (EMV Table Data) carries exactly EMVYES or EMVNOT, length 6, sent only in an EMV Transaction request; if returned in an authorization response the device echoes it on any subsequent advice or batch upload request
- BR-RULE-SEG130-R-006: EMV Card Sequence Number (Element 188), when populated, identifies the sequence number of the EMV card; Conditional, sourced Device
- BR-RULE-SEG130-R-001: Segment 130 is Required in every EMV authorization-class Financial Transaction Request; the Section 11.8.1 Data Section 3 table marks it R with the note 'This segment is the only segment required for all EMV financial transactions'

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:3143
For all card types, DE 111 Table ID 32 Sub table 08 should have value 'C' for Subsequent Credential on File.

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact code/table/tag meaning and context have no defensible counterpart established in this review. Generic segment/field matches and shared numbers are insufficient.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG116-R-008: A companion sub-table, 'Additional TransArmor Data' (Segment 111, Variable Information Indicator/Table ID 052), carries a longer Key ID and/or special device type via two documented sub-tables: Sub-Table ID 01 (KSN, up to 40 bytes, required for AES DUKPT, TDES, and Ingenico OnGuard encryption types) and Sub-Table ID 02 (Device Type, up to 8 bytes, required for AES DUKPT, optional for TDES and Ingenico OnGuard); this is a Segment 111 companion table, not a Segment 116 field
- BR-RULE-SEG110-R-020: The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format codes 'T$' (T<aba>A<acct>C<checknum>), '18' (<aba><acct> concatenated), '09' (raw MICR, not touched), and '19' (numerals from bottom of check); Segment 110's MICR Data (122) and this Segment 111 sub-table are companions, confirmed independently of the AI-generated requirement set, which had attributed these codes to Segment 110 with LOW-MEDIUM confidence (44-47%)
- BR-RULE-SEG111-R-003: Indicator length

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:3144
For all card types, POS Entry Mode value of '10' must be submitted in DE 111 Table ID 05.

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact code/table/tag meaning and context have no defensible counterpart established in this review. Generic segment/field matches and shared numbers are insufficient.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG110-R-020: The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format codes 'T$' (T<aba>A<acct>C<checknum>), '18' (<aba><acct> concatenated), '09' (raw MICR, not touched), and '19' (numerals from bottom of check); Segment 110's MICR Data (122) and this Segment 111 sub-table are companions, confirmed independently of the AI-generated requirement set, which had attributed these codes to Segment 110 with LOW-MEDIUM confidence (44-47%)
- BR-RULE-SEG116-R-008: A companion sub-table, 'Additional TransArmor Data' (Segment 111, Variable Information Indicator/Table ID 052), carries a longer Key ID and/or special device type via two documented sub-tables: Sub-Table ID 01 (KSN, up to 40 bytes, required for AES DUKPT, TDES, and Ingenico OnGuard encryption types) and Sub-Table ID 02 (Device Type, up to 8 bytes, required for AES DUKPT, optional for TDES and Ingenico OnGuard); this is a Segment 111 companion table, not a Segment 116 field
- BR-RULE-SEG111-R-003: Indicator length

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:3154
DE 111 Table ID 32 Sub Table 08 should have value 'C' for Subsequent Credential on File for all card types

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact code/table/tag meaning and context have no defensible counterpart established in this review. Generic segment/field matches and shared numbers are insufficient.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG116-R-008: A companion sub-table, 'Additional TransArmor Data' (Segment 111, Variable Information Indicator/Table ID 052), carries a longer Key ID and/or special device type via two documented sub-tables: Sub-Table ID 01 (KSN, up to 40 bytes, required for AES DUKPT, TDES, and Ingenico OnGuard encryption types) and Sub-Table ID 02 (Device Type, up to 8 bytes, required for AES DUKPT, optional for TDES and Ingenico OnGuard); this is a Segment 111 companion table, not a Segment 116 field
- BR-RULE-SEG110-R-020: The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format codes 'T$' (T<aba>A<acct>C<checknum>), '18' (<aba><acct> concatenated), '09' (raw MICR, not touched), and '19' (numerals from bottom of check); Segment 110's MICR Data (122) and this Segment 111 sub-table are companions, confirmed independently of the AI-generated requirement set, which had attributed these codes to Segment 110 with LOW-MEDIUM confidence (44-47%)
- BR-RULE-SEG111-R-003: Indicator length

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:3169
For all card types, DE111 Table ID 32 Sub Table ID 08 should have value 'C' for Subsequent Credential on File.

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact code/table/tag meaning and context have no defensible counterpart established in this review. Generic segment/field matches and shared numbers are insufficient.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG116-R-008: A companion sub-table, 'Additional TransArmor Data' (Segment 111, Variable Information Indicator/Table ID 052), carries a longer Key ID and/or special device type via two documented sub-tables: Sub-Table ID 01 (KSN, up to 40 bytes, required for AES DUKPT, TDES, and Ingenico OnGuard encryption types) and Sub-Table ID 02 (Device Type, up to 8 bytes, required for AES DUKPT, optional for TDES and Ingenico OnGuard); this is a Segment 111 companion table, not a Segment 116 field
- BR-RULE-SEG110-R-020: The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format codes 'T$' (T<aba>A<acct>C<checknum>), '18' (<aba><acct> concatenated), '09' (raw MICR, not touched), and '19' (numerals from bottom of check); Segment 110's MICR Data (122) and this Segment 111 sub-table are companions, confirmed independently of the AI-generated requirement set, which had attributed these codes to Segment 110 with LOW-MEDIUM confidence (44-47%)
- BR-RULE-SEG111-R-003: Indicator length

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:3170
For all card types, POS Entry Mode value of '10' must be submitted in DE111 Table ID 05.

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact code/table/tag meaning and context have no defensible counterpart established in this review. Generic segment/field matches and shared numbers are insufficient.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG110-R-020: The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format codes 'T$' (T<aba>A<acct>C<checknum>), '18' (<aba><acct> concatenated), '09' (raw MICR, not touched), and '19' (numerals from bottom of check); Segment 110's MICR Data (122) and this Segment 111 sub-table are companions, confirmed independently of the AI-generated requirement set, which had attributed these codes to Segment 110 with LOW-MEDIUM confidence (44-47%)
- BR-RULE-SEG116-R-008: A companion sub-table, 'Additional TransArmor Data' (Segment 111, Variable Information Indicator/Table ID 052), carries a longer Key ID and/or special device type via two documented sub-tables: Sub-Table ID 01 (KSN, up to 40 bytes, required for AES DUKPT, TDES, and Ingenico OnGuard encryption types) and Sub-Table ID 02 (Device Type, up to 8 bytes, required for AES DUKPT, optional for TDES and Ingenico OnGuard); this is a Segment 111 companion table, not a Segment 116 field
- BR-RULE-SEG111-R-003: Indicator length

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:3175
For Discover Delayed Card Sale transactions, DE111 Table ID 16 Delayed Card Sale Indicator must be 'D'.

Disposition: PARTIAL_SCOPE_OVERLAP

Generic indicator/length rules do not establish individual Table ID, sub-table, value, network or CIT/MIT applicability. No table-specific match credit.

Selected Test BRs: BR-RULE-SEG111-R-003;BR-RULE-SEG111-R-004

Ranked retrieval only:
- BR-RULE-SEG111-R-003: Indicator length
- BR-RULE-SEG116-R-008: A companion sub-table, 'Additional TransArmor Data' (Segment 111, Variable Information Indicator/Table ID 052), carries a longer Key ID and/or special device type via two documented sub-tables: Sub-Table ID 01 (KSN, up to 40 bytes, required for AES DUKPT, TDES, and Ingenico OnGuard encryption types) and Sub-Table ID 02 (Device Type, up to 8 bytes, required for AES DUKPT, optional for TDES and Ingenico OnGuard); this is a Segment 111 companion table, not a Segment 116 field
- BR-RULE-SEG110-R-020: The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format codes 'T$' (T<aba>A<acct>C<checknum>), '18' (<aba><acct> concatenated), '09' (raw MICR, not touched), and '19' (numerals from bottom of check); Segment 110's MICR Data (122) and this Segment 111 sub-table are companions, confirmed independently of the AI-generated requirement set, which had attributed these codes to Segment 110 with LOW-MEDIUM confidence (44-47%)

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:3191
For all card types, Data Element 111 Table ID 32 Sub Table ID 08 should have value 'C' for Subsequent Credential on File.

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact code/table/tag meaning and context have no defensible counterpart established in this review. Generic segment/field matches and shared numbers are insufficient.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG116-R-008: A companion sub-table, 'Additional TransArmor Data' (Segment 111, Variable Information Indicator/Table ID 052), carries a longer Key ID and/or special device type via two documented sub-tables: Sub-Table ID 01 (KSN, up to 40 bytes, required for AES DUKPT, TDES, and Ingenico OnGuard encryption types) and Sub-Table ID 02 (Device Type, up to 8 bytes, required for AES DUKPT, optional for TDES and Ingenico OnGuard); this is a Segment 111 companion table, not a Segment 116 field
- BR-RULE-SEG110-R-020: The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format codes 'T$' (T<aba>A<acct>C<checknum>), '18' (<aba><acct> concatenated), '09' (raw MICR, not touched), and '19' (numerals from bottom of check); Segment 110's MICR Data (122) and this Segment 111 sub-table are companions, confirmed independently of the AI-generated requirement set, which had attributed these codes to Segment 110 with LOW-MEDIUM confidence (44-47%)
- BR-RULE-SEG111-R-003: Indicator length

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:3195
For TransArmor processing, Account Number field is replaced by TAP header and EDATA.

Disposition: PARTIAL_SCOPE_OVERLAP

TransArmor account representation is related; exact TAP/EDATA layout and mandatory expiration behavior are not established by broad representation rules.

Selected Test BRs: BR-RULE-SEG100-R-035;BR-SEG100-ANN-O

Ranked retrieval only:
- BR-SEG100-E2-002: Tokenized or TransArmor account representation must be explicit.
- BR-RULE-SEG100-R-035: Tokenized or TransArmor Account Number representation is explicit
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:3223
Merchants using Real Time Account Updater should refer to Account Number and Card Discretionary Block Data for additional information.

Disposition: PARTIAL_SCOPE_OVERLAP

Reference-only Account Updater guidance is related, not an atomic rule equivalent to a specific field constraint.

Selected Test BRs: BR-RULE-SEG155-R-003;BR-RULE-SEG100-R-041;BR-RULE-SEG100-R-042

Ranked retrieval only:
- BR-RULE-SEG100-R-042: Card Discretionary Block Data follows conditional source rules
- BR-SEG100-GAP-DISCRETIONARY: Card Discretionary Block Data shall be validated to its source-defined conditional length and usage.
- BR-SEG100-APPD-ALPHA-STATE: Alphabetical state codes used in Card Discretionary Block Data or state context shall use an Appendix D alphabetical code.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:3224
Estimated/incremental authorizations allowed for Card Not Present transactions only for MCC 4121, 5411.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context
- BR-TXV-ACCOUNT-UPDATE: Account update
- BR-RULE-SEG105-R-006: Employee Number follows approved authorization policy when present

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:387
The last digit of the PAN is considered a check digit whether it is or not.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG123-R-006: Token PAN Suffix (Element 197, 4 chars, Conditional, Issuer/Authorizer-sourced) — the last 4 digits of the cardholder PAN — is returned in the response only if supplied by the authorizer
- BR-RULE-SEG110-R-006: The twelve Segment 110 fields are ordered as defined in Section 12.9: Segment Type, Segment Length, MICR Data, Driver's License, State Code, Date of Birth, Check Type, Check Number, Customer Phone Number, Customer Last Name, Check Issue Date, Alternate MICR IND
- BR-RULE-SEG110-R-016: Check Issue Date (Element 130) is optional, numeric, fixed 8 digits (MMDDYYYY)

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:388
PAN field control field C is always zero.

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact code/table/tag meaning and context have no defensible counterpart established in this review. Generic segment/field matches and shared numbers are insufficient.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG114-R-002: Segment 114 is optional in every Loyalty Card Transaction Request, always in Field No. 5 of Data Section No. 3, alongside the required Segment 108 in Field No. 4
- BR-RULE-SEG102-R-007: Fuel products are always the first products in the segment
- BR-RULE-SEG118-R-016: Load Control Key (Element 178) is Required, field 11, 60 characters, sourced from the Host and echoed back in each subsequent block request

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:422
Restaurant Credit card sales are terminal-capture, initiated as POS Authorization Only transactions.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-TXV-SALE-UPDATE: Sale update
- BR-SEG100-SEC5-LEAF-CREDIT-PROPRIETARY: Recognize proprietary credit cards as a Section 5 credit type.
- BR-SEG100-SEC5-LEAF-CREDIT-NONPROPRIETARY: Recognize nonproprietary credit cards as a Section 5 credit type.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:438
Local totals are calculated from electronic journal entries, grouped like host totals, shown with Card Label.

Disposition: PARTIAL_SCOPE_OVERLAP

Card Label fields are covered, but label-instead-of-field-number display behavior or journal-derived local totals is not established.

Selected Test BRs: BR-RULE-SEG105-R-015;BR-RULE-SEG119-R-024

Ranked retrieval only:
- BR-RULE-SEG105-R-015: Grand Total, Card Label, Card Type Total Count, and Card Type Total Amount are required
- BR-RULE-SEG119-R-024: Card Label is required for each card bucket
- BR-RULE-SEG105-R-020: In a Totals Request, Element 63 (Number of Segments) is 01

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:480
If a Pre-Auth transaction is approved offline, full track data must be sent.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG108-R-018: Loyalty Track 2 Data, when populated, is alphanumeric with maximum length 38
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)
- BR-TXV-ACCOUNT-UPDATE: Account update

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:481
If a Sale transaction is approved offline, full track data must be sent.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-TXV-SALE-UPDATE: Sale update
- BR-RULE-SEG108-R-018: Loyalty Track 2 Data, when populated, is alphanumeric with maximum length 38
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:486
If magnetic strip unreadable after three swipe attempts, device should prompt for manual entry of Account Number and Expiration Date (Format MMYY).

Disposition: PARTIAL_SCOPE_OVERLAP

Entry method agrees broadly; retry count, manual expiration prompt and card-family conditions need specific tests.

Selected Test BRs: BR-RULE-SEG100-R-034

Ranked retrieval only:
- BR-RULE-SEG100-R-034: Account Number representation agrees with POS entry method
- BR-SEG100-E2-001: Account Number representation must agree with the declared POS entry method.
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:487
If manual entry is used, this is indicated on receipt and journal by an asterisk (*) before the Account Number.

Disposition: COUNTERPART_NOT_ESTABLISHED

No equivalent receipt/storage obligation established. Account format, token validation and receipt Card Type labels are not substitutes. Receipt or storage evidence is required.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG100-R-034: Account Number representation agrees with POS entry method
- BR-SEG100-E2-001: Account Number representation must agree with the declared POS entry method.
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:488
Purchase/Capture is a supported point-of-sale transaction type for credit card processing.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-TXV-SALE-UPDATE: Sale update
- BR-SEG100-SEC5-LEAF-CREDIT-PROPRIETARY: Recognize proprietary credit cards as a Section 5 credit type.
- BR-RULE-SEG100-R-063: Financial responses support approved purchase/capture response codes

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:489
Purchase Reversal is a supported point-of-sale transaction type for credit card processing.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-TXV-POINTS-REVERSAL: Reversal of points redeemed
- BR-TXV-SALE-UPDATE: Sale update
- BR-SEG100-SEC5-LEAF-CREDIT-PROPRIETARY: Recognize proprietary credit cards as a Section 5 credit type.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:490
Authorization Only is a supported point-of-sale transaction type for credit card processing.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-TXV-SALE-UPDATE: Sale update
- BR-SEG100-SEC5-LEAF-CREDIT-PROPRIETARY: Recognize proprietary credit cards as a Section 5 credit type.
- BR-TXV-POINTS-REDEEM: Points redemption

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:491
Merchandise Return is a supported point-of-sale transaction type for credit card processing.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-TXV-SALE-UPDATE: Sale update
- BR-SEG100-SEC5-LEAF-CREDIT-PROPRIETARY: Recognize proprietary credit cards as a Section 5 credit type.
- BR-TXV-POINTS-REDEEM: Points redemption

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:492
Time-out Reversal is a supported point-of-sale transaction type for credit card processing.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-TXV-POINTS-REVERSAL: Reversal of points redeemed
- BR-TXV-SALE-UPDATE: Sale update
- BR-RULE-SEG130-R-017: EMV data is not required on Reversal transactions; Segment 130 may legitimately be absent from an EMV Purchase Reversal or Time-out Reversal

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:493
Mail/Phone Purchase is a supported point-of-sale transaction type for credit card processing.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-TXV-SALE-UPDATE: Sale update
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)
- BR-SEG100-SEC5-LEAF-CREDIT-PROPRIETARY: Recognize proprietary credit cards as a Section 5 credit type.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:494
Mail/Phone Authorization Only is a supported point-of-sale transaction type for credit card processing.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-TXV-SALE-UPDATE: Sale update
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)
- BR-SEG100-SEC5-LEAF-CREDIT-PROPRIETARY: Recognize proprietary credit cards as a Section 5 credit type.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:495
Mail/Phone Reversal is a supported point-of-sale transaction type for credit card processing.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-TXV-POINTS-REVERSAL: Reversal of points redeemed
- BR-TXV-SALE-UPDATE: Sale update
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:496
Void of Merchandise Return is a supported point-of-sale transaction type for credit card processing.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-TXV-SALE-UPDATE: Sale update
- BR-SEG100-LIFE-SALE-VOID: Sale void reuses the original Sequence Number.
- BR-SEG100-SEC5-LEAF-CREDIT-PROPRIETARY: Recognize proprietary credit cards as a Section 5 credit type.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:498
Partial Approval is a supported point-of-sale transaction type for credit card processing.

Disposition: PARTIAL_SCOPE_OVERLAP

Allowed indicator and approved amount do not establish mandatory partial-authorization support across brands/card-present contexts.

Selected Test BRs: BR-RULE-SEG100-R-020;BR-RULE-SEG100-R-065

Ranked retrieval only:
- BR-SEG100-E121-001: Partial Approval Indicator must use an allowed value.
- BR-RULE-SEG100-R-020: Partial Approval Indicator uses an allowed value
- BR-SEG100-E121-002: Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:499
Authorization Only Reversal is a supported point-of-sale transaction type for credit card processing.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-TXV-POINTS-REVERSAL: Reversal of points redeemed
- BR-TXV-SALE-UPDATE: Sale update
- BR-SEG100-SEC5-LEAF-CREDIT-PROPRIETARY: Recognize proprietary credit cards as a Section 5 credit type.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:524
Point-of-Sale Entry Mode must be populated correctly even if transaction is swiped, manually keyed, or card type not RFID-applicable, when RFID receiver present.

Disposition: PARTIAL_SCOPE_OVERLAP

POS entry-mode appendix/lifecycle topic overlaps; exact three-digit positions, Discover prohibitions and 90-to-02 exception need explicit source/Test assertions.

Selected Test BRs: BR-SEG100-ANN-J

Ranked retrieval only:
- BR-SEG100-APPO-ENTRY-MODE: Tokenized Segment 100 transactions shall use a POS Entry Mode that identifies the token presentment mode.
- BR-RULE-SEG110-R-020: The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format codes 'T$' (T<aba>A<acct>C<checknum>), '18' (<aba><acct> concatenated), '09' (raw MICR, not touched), and '19' (numerals from bottom of check); Segment 110's MICR Data (122) and this Segment 111 sub-table are companions, confirmed independently of the AI-generated requirement set, which had attributed these codes to Segment 110 with LOW-MEDIUM confidence (44-47%)
- BR-SEG100-ANN-J: POS Entry Mode in Segment 111 uses Appendix J PAN and terminal capability values and persists through lifecycle exceptions.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:550
FSA/HRA card transactions must follow credit card receipt requirements from section 10.1.7.

Disposition: COUNTERPART_NOT_ESTABLISHED

No equivalent receipt/storage obligation established. Account format, token validation and receipt Card Type labels are not substitutes. Receipt or storage evidence is required.

Selected Test BRs: None established

Ranked retrieval only:
- BR-SEG100-SEC5-LEAF-CREDIT-PROPRIETARY: Recognize proprietary credit cards as a Section 5 credit type.
- BR-SEG100-SEC5-LEAF-CREDIT-NONPROPRIETARY: Recognize nonproprietary credit cards as a Section 5 credit type.
- BR-SEG100-RECEIPT-CARDTYPE-VISA: Visa maps to receipt Card Type ID VISA.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:555
The Expiration Date must be suppressed on all transaction receipts.

Disposition: COUNTERPART_NOT_ESTABLISHED

No equivalent receipt/storage obligation established. Account format, token validation and receipt Card Type labels are not substitutes. Receipt or storage evidence is required.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG108-R-016: Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)
- BR-TXV-EXPIRATION-UPDATE: Expiration date update

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:556
Account Number must be truncated-suppressed to its last four digits on all transaction receipts.

Disposition: COUNTERPART_NOT_ESTABLISHED

No equivalent receipt/storage obligation established. Account format, token validation and receipt Card Type labels are not substitutes. Receipt or storage evidence is required.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.
- BR-SEG100-APPO-TOKEN-ACCOUNT: A payment token used as Segment 100 Account Number shall be a valid 13-19 digit token value subject to account validation and designated token BIN rules.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:557
An asterisk (*) precedes the Account Number on receipt/journal if manual entry was used.

Disposition: COUNTERPART_NOT_ESTABLISHED

No equivalent receipt/storage obligation established. Account format, token validation and receipt Card Type labels are not substitutes. Receipt or storage evidence is required.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG100-R-034: Account Number representation agrees with POS entry method
- BR-SEG100-E2-001: Account Number representation must agree with the declared POS entry method.
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:558
Expiration Date must be suppressed on all transaction receipts.

Disposition: COUNTERPART_NOT_ESTABLISHED

No equivalent receipt/storage obligation established. Account format, token validation and receipt Card Type labels are not substitutes. Receipt or storage evidence is required.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG108-R-016: Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)
- BR-TXV-EXPIRATION-UPDATE: Expiration date update

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:559
Transaction date prints on all credit card receipts.

Disposition: COUNTERPART_NOT_ESTABLISHED

No equivalent receipt/storage obligation established. Account format, token validation and receipt Card Type labels are not substitutes. Receipt or storage evidence is required.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEGDL3-R-009: For devices that use an automatic cut time, the device should start the settlement procedure 30 minutes before the Cut Time (Element 23) if the clerk has not completed settlement for that business day
- BR-SEG100-SEC5-LEAF-CREDIT-PROPRIETARY: Recognize proprietary credit cards as a Section 5 credit type.
- BR-SEG100-SEC5-LEAF-CREDIT-NONPROPRIETARY: Recognize nonproprietary credit cards as a Section 5 credit type.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:560
Transaction time prints on all credit card receipts.

Disposition: COUNTERPART_NOT_ESTABLISHED

No equivalent receipt/storage obligation established. Account format, token validation and receipt Card Type labels are not substitutes. Receipt or storage evidence is required.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEGDL3-R-009: For devices that use an automatic cut time, the device should start the settlement procedure 30 minutes before the Cut Time (Element 23) if the clerk has not completed settlement for that business day
- BR-SEG100-SEC5-LEAF-CREDIT-PROPRIETARY: Recognize proprietary credit cards as a Section 5 credit type.
- BR-SEG100-SEC5-LEAF-CREDIT-NONPROPRIETARY: Recognize nonproprietary credit cards as a Section 5 credit type.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:561
Sequence Number appears on all credit card receipts.

Disposition: COUNTERPART_NOT_ESTABLISHED

No equivalent receipt/storage obligation established. Account format, token validation and receipt Card Type labels are not substitutes. Receipt or storage evidence is required.

Selected Test BRs: None established

Ranked retrieval only:
- BR-SEG100-E86-001: Sequence Number must be six digits.
- BR-RULE-SEG100-R-019: Sequence Number is six digits and persists through lifecycle
- BR-SEG100-LIFE-AUTH-COMPLETE: Authorization completion reuses the original Sequence Number.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:567
Signature line prints only on the merchant's copy of the credit card transaction receipt.

Disposition: COUNTERPART_NOT_ESTABLISHED

No equivalent receipt/storage obligation established. Account format, token validation and receipt Card Type labels are not substitutes. Receipt or storage evidence is required.

Selected Test BRs: None established

Ranked retrieval only:
- BR-SEG100-RECEIPT-CARDTYPE-STAR-SIGNATURE-DEBIT: STAR Signature Debit maps to receipt Card Type ID STAR.
- BR-RULE-SEG120-R-008: Print Data may contain '\' as a line delimiter between lines of receipt text for Blackhawk phone activation/recharge
- BR-SEG100-SEC5-LEAF-CREDIT-PROPRIETARY: Recognize proprietary credit cards as a Section 5 credit type.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:569
Device must not display/store card-read data except account number, expiration date, cardholder name.

Disposition: COUNTERPART_NOT_ESTABLISHED

No equivalent receipt/storage obligation established. Account format, token validation and receipt Card Type labels are not substitutes. Receipt or storage evidence is required.

Selected Test BRs: None established

Ranked retrieval only:
- BR-SEG100-APPO-TOKEN-NOT-PAN: A payment token value must not be the same value as the cardholder's actual PAN.
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:572
Default track sent to host should be Track 2 if device supports it; else unaltered Track 1 before manual entry.

Disposition: PARTIAL_SCOPE_OVERLAP

Entry-method and discretionary-data topics overlap, but sentinel parsing, exact track grammar/limits or authorizer-specific full-track requirements are not enumerated.

Selected Test BRs: BR-RULE-SEG100-R-034;BR-RULE-SEG100-R-042

Ranked retrieval only:
- BR-RULE-SEG108-R-016: Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card
- BR-RULE-SEG108-R-019: Loyalty Information Version, when populated, is numeric length 1 with valid values 1 or 2; defaults to 1 when not sent
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:576
Host message should only contain Account Number and Expiration Date (YYMM) in Card Discretionary Block Data fields.

Disposition: PARTIAL_SCOPE_OVERLAP

Discretionary-data conditional usage is broad; exact MMYY/YYMM format, delimiter and field ordering remain unproven by this Test BR.

Selected Test BRs: BR-RULE-SEG100-R-042

Ranked retrieval only:
- BR-RULE-SEG100-R-042: Card Discretionary Block Data follows conditional source rules
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.
- BR-SEG100-GAP-DISCRETIONARY: Card Discretionary Block Data shall be validated to its source-defined conditional length and usage.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:578
Expiration Date field format is YYMM (4 digits) following account delimiter.

Disposition: PARTIAL_SCOPE_OVERLAP

Discretionary-data conditional usage is broad; exact MMYY/YYMM format, delimiter and field ordering remain unproven by this Test BR.

Selected Test BRs: BR-RULE-SEG100-R-042

Ranked retrieval only:
- BR-SEG100-APPO-TOKEN-EXPIRATION: Payment Token Expiration Date shall be populated in the PAN Expiration Date field when token processing requires it.
- BR-SEG100-ANN-O: Tokenized Segment 100 transactions use Appendix O token account, expiration, cryptogram, and presentment concepts where applicable.
- BR-RULE-SEG100-R-042: Card Discretionary Block Data follows conditional source rules

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:579
Card Discretionary Data field follows the 4-digit Expiration Date for specific credit transactions.

Disposition: PARTIAL_SCOPE_OVERLAP

Discretionary-data conditional usage is broad; exact MMYY/YYMM format, delimiter and field ordering remain unproven by this Test BR.

Selected Test BRs: BR-RULE-SEG100-R-042

Ranked retrieval only:
- BR-RULE-SEG100-R-042: Card Discretionary Block Data follows conditional source rules
- BR-SEG100-APPO-TOKEN-EXPIRATION: Payment Token Expiration Date shall be populated in the PAN Expiration Date field when token processing requires it.
- BR-SEG100-GAP-DISCRETIONARY: Card Discretionary Block Data shall be validated to its source-defined conditional length and usage.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:581
All offline transactions must send full unaltered track data, including both Track 1 and Track 2 formats.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG102-R-017: Fuel merchants send both fuel and nonfuel product data
- BR-RULE-SEG108-R-018: Loyalty Track 2 Data, when populated, is alphanumeric with maximum length 38
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:582
Device does not perform expiration date validation on a debit card processed as debit transaction.

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact card-family expiration validation/prompt/default behavior is not established by generic account or loyalty format rules. Resolve request context and independently expected behavior.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG108-R-016: Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)
- BR-RULE-SEG108-R-024: Segment 108 does not appear in the Loyalty Card Transaction Response, which mirrors the generic Financial Transaction Response layout

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:633
FSA/HRA card transaction receipts must follow debit card receipt requirements in section 10.2.4.

Disposition: COUNTERPART_NOT_ESTABLISHED

No equivalent receipt/storage obligation established. Account format, token validation and receipt Card Type labels are not substitutes. Receipt or storage evidence is required.

Selected Test BRs: None established

Ranked retrieval only:
- BR-SEG100-RECEIPT-CARDTYPE-DEBIT: Generic Debit maps to receipt Card Type ID DB.
- BR-SEG100-RECEIPT-CARDTYPE-STAR-SIGNATURE-DEBIT: STAR Signature Debit maps to receipt Card Type ID STAR.
- BR-RULE-SEG115-R-002: Segment 115 is conditionally included at Field No. 17/18/19 of the Financial Transaction Response's Data Section 2, only when Element 115 (Additional Information Data Segment Flag) indicates it follows AND the request's Loyalty Information Version (Element 150) equals 2

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:642
Device at point of interaction must not display/store card-read data except account number, expiration date, cardholder name.

Disposition: COUNTERPART_NOT_ESTABLISHED

No equivalent receipt/storage obligation established. Account format, token validation and receipt Card Type labels are not substitutes. Receipt or storage evidence is required.

Selected Test BRs: None established

Ranked retrieval only:
- BR-SEG100-APPO-TOKEN-NOT-PAN: A payment token value must not be the same value as the cardholder's actual PAN.
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:648
This track data storage restriction does not apply to TransArmor-Verifone Edition processing.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG116-R-001: Segment 116 (TransArmor Load Data Segment) is used for Key and Key ID Load in TransArmor PKI Encryption and Tokenization processing; confirmed via the Element 63 (Number of Segments) and Element 85 (Segment Type) processing-rule text and valid-codes tables
- BR-RULE-SEG116-R-005: The TransArmor PKI Encryption and Tokenization Load Request contains Data Section 1 (Elements 55, 63) directly followed by Segment 116 in Field No. 3; by structural analogy with the sibling message types listed in the same Element 63 processing-rule paragraph (Totals, Loyalty Card, Electronic Mail, ECA/TeleCheck, Communications Test), the request likely excludes the Standard Message Data Segment (100) and carries no other segment, but this is not shown in a dedicated Segment 116 request table
- BR-RULE-SEG108-R-018: Loyalty Track 2 Data, when populated, is alphanumeric with maximum length 38

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:649
Host message should only contain Account Number and Card Discretionary Block Data with Expiration Date (YYMM).

Disposition: PARTIAL_SCOPE_OVERLAP

Discretionary-data conditional usage is broad; exact MMYY/YYMM format, delimiter and field ordering remain unproven by this Test BR.

Selected Test BRs: BR-RULE-SEG100-R-042

Ranked retrieval only:
- BR-RULE-SEG100-R-042: Card Discretionary Block Data follows conditional source rules
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.
- BR-SEG100-GAP-DISCRETIONARY: Card Discretionary Block Data shall be validated to its source-defined conditional length and usage.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:651
Host message should only contain Account Number, '=' delimiter, 4-digit Expiration Date (YYMM) for specific debit transactions.

Disposition: PARTIAL_SCOPE_OVERLAP

Discretionary-data conditional usage is broad; exact MMYY/YYMM format, delimiter and field ordering remain unproven by this Test BR.

Selected Test BRs: BR-RULE-SEG100-R-042

Ranked retrieval only:
- BR-RULE-SEG100-R-042: Card Discretionary Block Data follows conditional source rules
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:6742
Host must track the Block Number for the last complete data block sent in an Electronic Mail response.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG109-R-022: Electronic Mail Response contains required Response Code, Download Indicator, Initiation Date, Initiation Time, Sequence Number, and Block Number; optional Mail Text Data Length and Mail Text Data are correlated
- BR-RULE-SEG132-R-010: Block Number (Element 11) is required, 3 digits, identifying the specific data block requested by a device or sent by the host — implying a multi-block CA key file transfer protocol not fully detailed in this section
- BR-RULE-SEG118-R-018: Block Number (Element 11) is Conditional, field 13, 3 digits, identifying the specific block being sent by device or Host; the Host uses this value to determine the next block to send, and it is echoed back in the subsequent block request

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:6743
Account Number and Card Discretionary Block Data combined must not exceed 76 characters for Track 1.

Disposition: PARTIAL_SCOPE_OVERLAP

Entry-method and discretionary-data topics overlap, but sentinel parsing, exact track grammar/limits or authorizer-specific full-track requirements are not enumerated.

Selected Test BRs: BR-RULE-SEG100-R-034;BR-RULE-SEG100-R-042

Ranked retrieval only:
- BR-RULE-SEG100-R-042: Card Discretionary Block Data follows conditional source rules
- BR-SEG100-GAP-DISCRETIONARY: Card Discretionary Block Data shall be validated to its source-defined conditional length and usage.
- BR-SEG100-APPD-ALPHA-STATE: Alphabetical state codes used in Card Discretionary Block Data or state context shall use an Appendix D alphabetical code.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:676
STAR Single message transactions must be card present, full mag-stripe data, or full EMV chip data.

Disposition: PARTIAL_SCOPE_OVERLAP

Entry-method and discretionary-data topics overlap, but sentinel parsing, exact track grammar/limits or authorizer-specific full-track requirements are not enumerated.

Selected Test BRs: BR-RULE-SEG100-R-034;BR-RULE-SEG100-R-042

Ranked retrieval only:
- BR-RULE-SEG130-R-012: Element 118 is reused across segments: it is 'Additional Information' in Segment 112 (max 984, single occurrence) and 'EMV Additional Information' in Segment 130 (variable length, repeating). Do not assume element numbers are globally unique or that the two fields share business meaning
- BR-RULE-SEG130-R-007: EMV Chip Data Length (Element 189) is required and identifies the length of the following EMV Chip Data field; valid values 000-999
- BR-SEG100-ANN-R: EMV Segment 100 transactions include Segment 130 and validate Appendix R chip-data framing and context consistency.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:6809
Loyalty Program ID must be present when transaction is a loyalty card transaction.

Disposition: CLAUSE_MATCH_PROPOSED

Same required Loyalty Program ID clause; Test also constrains numeric maximum length 6.

Selected Test BRs: BR-RULE-SEG108-R-008

Ranked retrieval only:
- BR-SEG108-FIELD-008: Loyalty Program ID is required, numeric max 6.
- BR-RULE-SEG108-R-008: Loyalty Program ID, required, is numeric with maximum length 6
- BR-RULE-SEG108-R-002: Segment 108 is required in every Loyalty Card Transaction Request

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:6810
Loyalty Account Number must be present when transaction is a loyalty card transaction.

Disposition: PARTIAL_SCOPE_OVERLAP

AI requires Loyalty Account Number on all loyalty transactions; Test constrains it only when populated and permits alternate identity context.

Selected Test BRs: BR-RULE-SEG108-R-009

Ranked retrieval only:
- BR-SEG108-FIELD-009: Loyalty Account Number is numeric max 24 when populated.
- BR-RULE-SEG108-R-009: Loyalty Account Number, when populated, is numeric with maximum length 24
- BR-RULE-SEG108-R-025: In a Loyalty Card Transaction Request, Element 63 (Number of Segments) counts Segment 100, Segment 108 and the optional Segment 114, so it is 2 or 3 and equals the number of segments present

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:6832
BUYPASS Card Type must appear in Segment No. 118 when Prompt Code 904 is returned.

Disposition: CLAUSE_MATCH_PROPOSED

Test explicitly places BUYPASS Card Type and BIN range endpoints in the Prompt 904 response; AI covers a named field clause only.

Selected Test BRs: BR-RULE-SEG118-R-022

Ranked retrieval only:
- BR-RULE-SEG118-R-022: When Prompt Code is 904 (Host Discount Data, response only), fields 14-27 are: Host Discount Timestamp (179, 12), Number of Discounts (171, 2), then a repeated block of Start Date (165,8), Start Time (166,4), End Date (165,8), End Time (166,4), BUYPASS Card Type (173,4), Card BIN Range Beginning (183,12), Card BIN Range Ending (184,12), Product Code (77,3), Product Discount Amount (172,5), Product Code (77,3), Discount Quantity Limit (185,3), Discount Program Description (186,15), for a total variable length not to exceed 3,600 bytes
- BR-RULE-SEG105-R-005: Prompt Code is 990
- BR-RULE-SEG118-R-010: Terminal Identifier (Element 102) is Required, field 5, 13 characters, identifying the device by Device Type, State Code, BUYPASS Merchant Number, and Device Number

Artifact review flags: ["CLAIMED_ELEMENT_NOT_IN_CASE_REQUEST_METADATA", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:6833
Card Table Type must appear in Segment No. 118 when Prompt Code 902 is returned.

Disposition: PARTIAL_SCOPE_OVERLAP

Table type and Prompt 902 data are supported separately, not the full update/create/replace behavior or conditional field rule.

Selected Test BRs: BR-RULE-SEG118-R-015;BR-RULE-SEG118-R-020

Ranked retrieval only:
- BR-RULE-SEG118-R-015: Card Table Type (Element 174) is Required, field 10, 4 digits, identifying the specific table type this segment addresses, sourced from the Host and echoed back; valid values are 0001 (BIN Table), 0002 (RULES Table), 0003 (RESTRICTIONS Table), 0004 (SAF Table), 0005 (PROMPT Table), 0006 (PRODUCT Table)
- BR-RULE-SEG118-R-020: When Prompt Code is 902 (Dynamic Card Table Data, response only), field 14 is Card Table Data (175, max 3,600 bytes, length determined by the Segment Length in field 2)
- BR-RULE-SEG118-R-022: When Prompt Code is 904 (Host Discount Data, response only), fields 14-27 are: Host Discount Timestamp (179, 12), Number of Discounts (171, 2), then a repeated block of Start Date (165,8), Start Time (166,4), End Date (165,8), End Time (166,4), BUYPASS Card Type (173,4), Card BIN Range Beginning (183,12), Card BIN Range Ending (184,12), Product Code (77,3), Product Discount Amount (172,5), Product Code (77,3), Discount Quantity Limit (185,3), Discount Program Description (186,15), for a total variable length not to exceed 3,600 bytes

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:6834
Card Table Data must appear in Segment 118 when Prompt Code 902 is returned.

Disposition: CLAUSE_MATCH_PROPOSED

Same Card Table Data in Prompt 902 responses.

Selected Test BRs: BR-RULE-SEG118-R-020

Ranked retrieval only:
- BR-RULE-SEG118-R-020: When Prompt Code is 902 (Dynamic Card Table Data, response only), field 14 is Card Table Data (175, max 3,600 bytes, length determined by the Segment Length in field 2)
- BR-RULE-SEG105-R-005: Prompt Code is 990
- BR-RULE-SEG119-R-012: Prompt Code is fixed value 990

Artifact review flags: ["CLAIMED_ELEMENT_NOT_IN_CASE_REQUEST_METADATA", "PRODUCER_SEGMENT_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:6835
Device Card Table Version value beginning with 99999 must indicate no card table used by the location.

Disposition: CLAUSE_MATCH_PROPOSED

Test explicitly describes concatenated five-digit versions and the 99999 no-card-table sentinel; each AI BR covers one clause, not the full Test BR.

Selected Test BRs: BR-RULE-SEG118-R-013

Ranked retrieval only:
- BR-RULE-SEG118-R-013: Device Card Table Version (Element 176) is Required, field 8, 35 numeric digits: seven 5-digit version values strung together for the Card Table Master ID, BIN table, RULES table, RESTRICTIONS table, SAF table, PROMPT table, and PRODUCT table versions; a value beginning with 99999 indicates no card table is used at the location
- BR-RULE-SEG119-R-020: Device Card Table Version is required
- BR-RULE-SEG119-R-017: Software Version is required

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:6836
Card Table Load Version must appear in Segment No. 118.

Disposition: CLAUSE_MATCH_PROPOSED

Shared required Card Table Load Version field; host echo and complete version-vector behavior are additional Test obligations.

Selected Test BRs: BR-RULE-SEG118-R-014

Ranked retrieval only:
- BR-RULE-SEG118-R-014: Card Table Load Version (Element 177) is Required, field 9, 35 numeric digits, representing the versions of the Card Table components currently being loaded; sourced from the Host and echoed back in each subsequent block request
- BR-RULE-SEG119-R-020: Device Card Table Version is required
- BR-RULE-SEG118-R-016: Load Control Key (Element 178) is Required, field 11, 60 characters, sourced from the Host and echoed back in each subsequent block request

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:6840
Prompt Code, Pending must be a valid Transaction Type and/or Card Type Code.

Disposition: PARTIAL_SCOPE_OVERLAP

Test has a specific pending-code enumeration; generic valid-transaction/card-code wording does not prove that exact allowlist.

Selected Test BRs: BR-RULE-SEG118-R-012

Ranked retrieval only:
- BR-RULE-SEG118-R-012: Prompt Code, Pending (Element 182) is Required, field 7, 4 digits, and must be one of the documented values: 0901 (Custom Receipt Text Data), 0902 (Proprietary Data Load), 0904 (Host Discount Data), 0981 (Electronic Mail)
- BR-SEG100-APPE-FINANCIAL-CARD-TYPES: Financial Segment 100 Prompt Codes shall use one of the 36 Appendix E card types valid for financial transaction Prompt Codes.
- BR-RULE-SEG118-R-028: Host Discount Data (Prompt Code 904) is triggered when a Totals Response's Response Code (83) indicates proprietary/host-discount data pending (values D/E for general proprietary data pending, M/N for Host Discount data pending specifically); the device then sends a Proprietary Data Load Request with Prompt Code 904

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:6846
Card Sequence Number must be required when card product includes Card Sequence Number in EMV data

Disposition: PARTIAL_SCOPE_OVERLAP

Card Sequence field is shared; product trigger, absent-5F34 three-space encoding and 000-099 range are not explicit in this Test BR.

Selected Test BRs: BR-RULE-SEG130-R-006

Ranked retrieval only:
- BR-RULE-SEG130-R-006: EMV Card Sequence Number (Element 188), when populated, identifies the sequence number of the EMV card; Conditional, sourced Device
- BR-SEG100-APPR-EMV-COMPANION: An EMV Segment 100 transaction shall include Segment 130 EMV Request Data in Data Section 3.
- BR-SEG100-EMV-001: An EMV purchase must include Segment 130 with Segment 100.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:6847
EMV Chip Data Length must be present in all EMV transactions

Disposition: CLAUSE_MATCH_PROPOSED

Shared explicit EMV Chip Data Length presence/range clause, 000-999. A range-only AI BR does not cover every Test obligation.

Selected Test BRs: BR-RULE-SEG130-R-007

Ranked retrieval only:
- BR-RULE-SEG130-R-007: EMV Chip Data Length (Element 189) is required and identifies the length of the following EMV Chip Data field; valid values 000-999
- BR-RULE-SEG131-R-008: EMV Chip Data Length (Element 189) and EMV Chip Data (Element 190) are required, mirroring Segment 130's fields, but the field table lists their Source as 'Device' even though Segment 131 as a whole originates at BUYPASS
- BR-SEG100-ANN-R: EMV Segment 100 transactions include Segment 130 and validate Appendix R chip-data framing and context consistency.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:6848
EMV Chip Data must be present in all EMV transactions

Disposition: CLAUSE_MATCH_PROPOSED

Shared required EMV Chip Data clause for the applicable EMV request context; Test adds TLV/framing constraints and reversal exceptions must remain visible.

Selected Test BRs: BR-RULE-SEG130-R-008

Ranked retrieval only:
- BR-RULE-SEG130-R-008: EMV Chip Data (Element 190) is required, contains TLV-encoded BCD-packed/hex chip data, and must begin with a valid three-digit length consistent with the following encoded chip data
- BR-RULE-SEG131-R-008: EMV Chip Data Length (Element 189) and EMV Chip Data (Element 190) are required, mirroring Segment 130's fields, but the field table lists their Source as 'Device' even though Segment 131 as a whole originates at BUYPASS
- BR-RULE-SEG130-R-010: Tag 9F26 (Application Cryptogram) authenticity verification is explicitly out of scope for AI/synthetic test generation; it requires a certified EMV kernel or HSM

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:693
Purchase/Capture requirements for credit card also apply to Signature Debit card.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-SEG100-RECEIPT-CARDTYPE-STAR-SIGNATURE-DEBIT: STAR Signature Debit maps to receipt Card Type ID STAR.
- BR-RULE-SEG104-R-006: Purchase Card Data Segment max length is 86 alphanumeric characters.
- BR-RULE-SEG104-R-001: Purchase Card Data Segment is a Data Section 3 companion of Segment 100.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:697
FSA/HRA card transaction receipt requirements for credit card also apply to Signature Debit card.

Disposition: COUNTERPART_NOT_ESTABLISHED

No equivalent receipt/storage obligation established. Account format, token validation and receipt Card Type labels are not substitutes. Receipt or storage evidence is required.

Selected Test BRs: None established

Ranked retrieval only:
- BR-SEG100-RECEIPT-CARDTYPE-STAR-SIGNATURE-DEBIT: STAR Signature Debit maps to receipt Card Type ID STAR.
- BR-SEG100-RECEIPT-CARDTYPE-DEBIT: Generic Debit maps to receipt Card Type ID DB.
- BR-SEG100-SEC5-LEAF-CREDIT-PROPRIETARY: Recognize proprietary credit cards as a Section 5 credit type.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:700
Track support requirements for electronic credit transactions also apply to Signature Debit cards.

Disposition: PARTIAL_SCOPE_OVERLAP

Entry-method and discretionary-data topics overlap, but sentinel parsing, exact track grammar/limits or authorizer-specific full-track requirements are not enumerated.

Selected Test BRs: BR-RULE-SEG100-R-034;BR-RULE-SEG100-R-042

Ranked retrieval only:
- BR-SEG100-RECEIPT-CARDTYPE-STAR-SIGNATURE-DEBIT: STAR Signature Debit maps to receipt Card Type ID STAR.
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)
- BR-RULE-SEG104-R-014: Direct Marketing Invoice Number is alphanumeric, max 10 characters, when populated.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:701
The device does not perform expiration date validation in an EBT card transaction.

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact card-family expiration validation/prompt/default behavior is not established by generic account or loyalty format rules. Resolve request context and independently expected behavior.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG108-R-016: Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)
- BR-RULE-SEG108-R-024: Segment 108 does not appear in the Loyalty Card Transaction Response, which mirrors the generic Financial Transaction Response layout

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:704
For manually entered Account Numbers, device must prompt for card's expiration date.

Disposition: PARTIAL_SCOPE_OVERLAP

Entry method agrees broadly; retry count, manual expiration prompt and card-family conditions need specific tests.

Selected Test BRs: BR-RULE-SEG100-R-034

Ranked retrieval only:
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.
- BR-SEG100-ANN-O: Tokenized Segment 100 transactions use Appendix O token account, expiration, cryptogram, and presentment concepts where applicable.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:705
If no expiration date is provided for manually entered EBT account, use 1249.

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact card-family expiration validation/prompt/default behavior is not established by generic account or loyalty format rules. Resolve request context and independently expected behavior.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG108-R-016: Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)
- BR-RULE-SEG108-R-019: Loyalty Information Version, when populated, is numeric length 1 with valid values 1 or 2; defaults to 1 when not sent

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:712
Electronic vouchers must prompt for Account Number, Approval Number, and Voucher Number.

Disposition: PARTIAL_SCOPE_OVERLAP

Approval/account fields overlap, but electronic voucher prompting and Voucher Number are additional obligations.

Selected Test BRs: BR-RULE-SEG100-R-049;BR-RULE-SEG100-R-041

Ranked retrieval only:
- BR-SEG100-GAP-APPROVAL: Approval Number shall be validated when the transaction flow requires an approval reference.
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:713
Stand-alone equipment must allow manual entry of Account Number and Expiration Date for electronic voucher.

Disposition: PARTIAL_SCOPE_OVERLAP

Entry method agrees broadly; retry count, manual expiration prompt and card-family conditions need specific tests.

Selected Test BRs: BR-RULE-SEG100-R-034

Ranked retrieval only:
- BR-RULE-SEG100-R-034: Account Number representation agrees with POS entry method
- BR-SEG100-E2-001: Account Number representation must agree with the declared POS entry method.
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:714
PAN printed on receipts must be truncated to last four digits, preceded by X's equal to truncated digit count.

Disposition: COUNTERPART_NOT_ESTABLISHED

No equivalent receipt/storage obligation established. Account format, token validation and receipt Card Type labels are not substitutes. Receipt or storage evidence is required.

Selected Test BRs: None established

Ranked retrieval only:
- BR-SEG100-APPO-TOKEN-NOT-PAN: A payment token value must not be the same value as the cardholder's actual PAN.
- BR-SEG100-APPO-TOKEN-ACCOUNT: A payment token used as Segment 100 Account Number shall be a valid 13-19 digit token value subject to account validation and designated token BIN rules.
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:762
Prompt Code is constructed from Transaction Type Code and Card Type for eWIC processing.

Disposition: PARTIAL_SCOPE_OVERLAP

Card Type contributes to Prompt Code, but the AI general/eWIC statement and financial Test scope are not fully equivalent.

Selected Test BRs: BR-SEG100-E78-002;BR-SEG100-APPE-PROMPT-COMPOSITION

Ranked retrieval only:
- BR-TXV-ACCOUNT-UPDATE: Account update
- BR-TXV-ACCOUNT-INQUIRY: Account inquiry
- BR-RULE-SEG100-R-056: eWIC Authorization uses Prompt Code 3086 and Segment 103

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:767
Benefit expiration date comes from subelement Earliest WIC Benefit Expiration Date within WIC Product Data element.

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact card-family expiration validation/prompt/default behavior is not established by generic account or loyalty format rules. Resolve request context and independently expected behavior.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG103-R-023: WIC Product Data subelement catalog: EF (Earliest WIC Benefit Expiration Date, 8 bytes), EA (WIC Prescription Balance Information, up to 14 bytes), PS (WIC UPC Exception/Denial or Purchase Information, up to 47 bytes)
- BR-SEG103-WIC-013: WIC Product Data begins with a 4-digit Total Length subelement bounded to 2997.
- BR-RULE-SEG103-R-013: WIC Product Data is bounded to 3,001 bytes and begins with a 4-digit Total Length subelement with maximum value 2997

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:780
Device does not perform expiration date validation on stored value card transactions.

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact card-family expiration validation/prompt/default behavior is not established by generic account or loyalty format rules. Resolve request context and independently expected behavior.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG108-R-016: Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)
- BR-RULE-SEG108-R-024: Segment 108 does not appear in the Loyalty Card Transaction Response, which mirrors the generic Financial Transaction Response layout

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:782
When manual entry allowed and stripe unreadable after 3 attempts, prompt for Account Number and Expiration Date (MMYY).

Disposition: PARTIAL_SCOPE_OVERLAP

Entry method agrees broadly; retry count, manual expiration prompt and card-family conditions need specific tests.

Selected Test BRs: BR-RULE-SEG100-R-034

Ranked retrieval only:
- BR-RULE-SEG100-R-034: Account Number representation agrees with POS entry method
- BR-SEG100-E2-001: Account Number representation must agree with the declared POS entry method.
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:783
First Data Premium Gift Card has no embossed expiration date; default value 1249 (MMYY) is sent.

Disposition: PARTIAL_SCOPE_OVERLAP

MMYY/default 1249 is explicit for loyalty Segment 108, but gift/other response contexts and YYMM alternatives must not be conflated.

Selected Test BRs: BR-RULE-SEG108-R-016

Ranked retrieval only:
- BR-RULE-SEG108-R-016: Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card
- BR-RULE-SEG108-R-019: Loyalty Information Version, when populated, is numeric length 1 with valid values 1 or 2; defaults to 1 when not sent
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:784
First Data Premium Gift Card has a 16-digit Account Number.

Disposition: PARTIAL_SCOPE_OVERLAP

Test establishes account/identification presence in context, not the AI-specific lengths, response representation, code values or account semantics.

Selected Test BRs: BR-RULE-SEG100-R-041

Ranked retrieval only:
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context
- BR-SEG100-APPO-TOKEN-ACCOUNT: A payment token used as Segment 100 Account Number shall be a valid 13-19 digit token value subject to account validation and designated token BIN rules.
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:787
Bar code scanned gift card should be treated as manually entered transaction with entry mode '01'.

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact code/table/tag meaning and context have no defensible counterpart established in this review. Generic segment/field matches and shared numbers are insufficient.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.
- BR-RULE-SEG100-R-034: Account Number representation agrees with POS entry method

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:808
First Data Premium Gift Card has a 16-digit Account Number.

Disposition: PARTIAL_SCOPE_OVERLAP

Test establishes account/identification presence in context, not the AI-specific lengths, response representation, code values or account semantics.

Selected Test BRs: BR-RULE-SEG100-R-041

Ranked retrieval only:
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context
- BR-SEG100-APPO-TOKEN-ACCOUNT: A payment token used as Segment 100 Account Number shall be a valid 13-19 digit token value subject to account validation and designated token BIN rules.
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:825
PAN printed on receipts must be truncated to last four digits, preceded by X's equal to original Account Number length.

Disposition: COUNTERPART_NOT_ESTABLISHED

No equivalent receipt/storage obligation established. Account format, token validation and receipt Card Type labels are not substitutes. Receipt or storage evidence is required.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context
- BR-SEG100-APPO-TOKEN-ACCOUNT: A payment token used as Segment 100 Account Number shall be a valid 13-19 digit token value subject to account validation and designated token BIN rules.
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:826
Truncation of Account Number is required on all customer-activated transactions.

Disposition: COUNTERPART_NOT_ESTABLISHED

No full same-obligation counterpart established in the reviewed Test titles/chains. Ranked leads remain open; this is not an adjudicated NOT_MATCH or proof that the full Test corpus lacks coverage.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.
- BR-SEG100-E2-002: Tokenized or TransArmor account representation must be explicit.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:827
First Data Premium Gift Card receipts truncate last four digits of 16-digit Account Number, replaced by four X's.

Disposition: COUNTERPART_NOT_ESTABLISHED

No equivalent receipt/storage obligation established. Account format, token validation and receipt Card Type labels are not substitutes. Receipt or storage evidence is required.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context
- BR-SEG100-APPO-TOKEN-ACCOUNT: A payment token used as Segment 100 Account Number shall be a valid 13-19 digit token value subject to account validation and designated token BIN rules.
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:837
Partial Track 2 Data consists of PAN, '=' sign, then 4-digit expiration date (YYMM); no further discretionary data.

Disposition: PARTIAL_SCOPE_OVERLAP

Entry-method and discretionary-data topics overlap, but sentinel parsing, exact track grammar/limits or authorizer-specific full-track requirements are not enumerated.

Selected Test BRs: BR-RULE-SEG100-R-034;BR-RULE-SEG100-R-042

Ranked retrieval only:
- BR-RULE-SEG108-R-016: Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)
- BR-RULE-SEG108-R-018: Loyalty Track 2 Data, when populated, is alphanumeric with maximum length 38

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:854
Device does not perform expiration date validation in a loyalty card transaction.

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact card-family expiration validation/prompt/default behavior is not established by generic account or loyalty format rules. Resolve request context and independently expected behavior.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG108-R-016: Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)
- BR-RULE-SEG108-R-002: Segment 108 is required in every Loyalty Card Transaction Request

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:856
After three failed swipe attempts, device should prompt for manual entry of Account Number and Expiration Date (MMYY).

Disposition: PARTIAL_SCOPE_OVERLAP

Entry method agrees broadly; retry count, manual expiration prompt and card-family conditions need specific tests.

Selected Test BRs: BR-RULE-SEG100-R-034

Ranked retrieval only:
- BR-RULE-SEG100-R-034: Account Number representation agrees with POS entry method
- BR-SEG100-E2-001: Account Number representation must agree with the declared POS entry method.
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:857
User may skip Expiration Date prompt when manually keyed and no expiration date is present on card.

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact card-family expiration validation/prompt/default behavior is not established by generic account or loyalty format rules. Resolve request context and independently expected behavior.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG108-R-016: Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)
- BR-RULE-SEG108-R-025: In a Loyalty Card Transaction Request, Element 63 (Number of Segments) counts Segment 100, Segment 108 and the optional Segment 114, so it is 2 or 3 and equals the number of segments present

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:858
When no expiration data is present, device sends a default value of 1249 (MMYY).

Disposition: PARTIAL_SCOPE_OVERLAP

MMYY/default 1249 is explicit for loyalty Segment 108, but gift/other response contexts and YYMM alternatives must not be conflated.

Selected Test BRs: BR-RULE-SEG108-R-016

Ranked retrieval only:
- BR-RULE-SEG108-R-016: Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)
- BR-RULE-SEG108-R-019: Loyalty Information Version, when populated, is numeric length 1 with valid values 1 or 2; defaults to 1 when not sent

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:859
When loyalty card not present, Street Address and Loyalty Phone Number are keyed in lieu of Loyalty Account Number.

Disposition: PARTIAL_SCOPE_OVERLAP

Street/phone/account fields exist, but card-unavailable substitution and transaction-specific behavior are not established by independent field-format rules.

Selected Test BRs: BR-RULE-SEG108-R-014;BR-RULE-SEG108-R-015;BR-RULE-SEG108-R-009

Ranked retrieval only:
- BR-RULE-SEG108-R-014: Street Address, when populated, is numeric with maximum length 5
- BR-RULE-SEG108-R-015: Phone Number, Loyalty, when populated, is numeric with maximum length 10
- BR-SEG108-FIELD-009: Loyalty Account Number is numeric max 24 when populated.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:861
If loyalty card not available for Add Account, clerk enters customer's street number and telephone number.

Disposition: PARTIAL_SCOPE_OVERLAP

Street/phone/account fields exist, but card-unavailable substitution and transaction-specific behavior are not established by independent field-format rules.

Selected Test BRs: BR-RULE-SEG108-R-014;BR-RULE-SEG108-R-015;BR-RULE-SEG108-R-009

Ranked retrieval only:
- BR-TXV-ADD-ACCOUNT: Add account
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)
- BR-SEG108-FIELD-009: Loyalty Account Number is numeric max 24 when populated.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:863
Expiration date update: if loyalty card unavailable, enter street number and telephone number instead of account number.

Disposition: PARTIAL_SCOPE_OVERLAP

Street/phone/account fields exist, but card-unavailable substitution and transaction-specific behavior are not established by independent field-format rules.

Selected Test BRs: BR-RULE-SEG108-R-014;BR-RULE-SEG108-R-015;BR-RULE-SEG108-R-009

Ranked retrieval only:
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.
- BR-SEG100-ANN-O: Tokenized Segment 100 transactions use Appendix O token account, expiration, cryptogram, and presentment concepts where applicable.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:864
Account inquiry: if loyalty card unavailable, enter street number and telephone number instead of account number.

Disposition: PARTIAL_SCOPE_OVERLAP

Street/phone/account fields exist, but card-unavailable substitution and transaction-specific behavior are not established by independent field-format rules.

Selected Test BRs: BR-RULE-SEG108-R-014;BR-RULE-SEG108-R-015;BR-RULE-SEG108-R-009

Ranked retrieval only:
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.
- BR-SEG100-E2-002: Tokenized or TransArmor account representation must be explicit.

Artifact review flags: ["CLAIMED_ELEMENT_NOT_IN_CASE_REQUEST_METADATA"]

## REQ-SRC-ATL105-PDF-001:865
Points redemption requires number of points to redeem; if no card, use street number and phone number instead of account number.

Disposition: PARTIAL_SCOPE_OVERLAP

Street/phone/account fields exist, but card-unavailable substitution and transaction-specific behavior are not established by independent field-format rules.

Selected Test BRs: BR-RULE-SEG108-R-014;BR-RULE-SEG108-R-015;BR-RULE-SEG108-R-009

Ranked retrieval only:
- BR-RULE-SEG108-R-010: Points to Redeem, when populated, is numeric with maximum length 6
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)
- BR-TXV-POINTS-REDEEM: Points redemption

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:868
If loyalty card unavailable, clerk enters both street number and telephone number instead of Loyalty Account Number.

Disposition: PARTIAL_SCOPE_OVERLAP

Street/phone/account fields exist, but card-unavailable substitution and transaction-specific behavior are not established by independent field-format rules.

Selected Test BRs: BR-RULE-SEG108-R-014;BR-RULE-SEG108-R-015;BR-RULE-SEG108-R-009

Ranked retrieval only:
- BR-SEG108-FIELD-009: Loyalty Account Number is numeric max 24 when populated.
- BR-RULE-SEG108-R-009: Loyalty Account Number, when populated, is numeric with maximum length 24
- BR-RULE-SEG108-R-014: Street Address, when populated, is numeric with maximum length 5

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:882
For retrieval of proprietary card data, the Electronic Mail Request uses a Prompt Code of 996.

Disposition: CLAUSE_MATCH_PROPOSED

Same special Prompt Code 996 proprietary electronic-mail retrieval family; exact request/response lifecycle remains unexecuted.

Selected Test BRs: BR-TX9-PROMPT-996

Ranked retrieval only:
- BR-TX9-PROMPT-996: Proprietary electronic mail
- BR-TX9-PROMPT-981: Nonproprietary electronic mail
- BR-TX9-PROMPT-995: Electronic mail submission

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:892
Table ID 56 Sub Table ID 09 is used to indicate MIT/CIT category and subcategory data for MasterCard card type.

Disposition: PARTIAL_SCOPE_OVERLAP

Generic indicator/length rules do not establish individual Table ID, sub-table, value, network or CIT/MIT applicability. No table-specific match credit.

Selected Test BRs: BR-RULE-SEG111-R-003;BR-RULE-SEG111-R-004

Ranked retrieval only:
- BR-RULE-SEG116-R-008: A companion sub-table, 'Additional TransArmor Data' (Segment 111, Variable Information Indicator/Table ID 052), carries a longer Key ID and/or special device type via two documented sub-tables: Sub-Table ID 01 (KSN, up to 40 bytes, required for AES DUKPT, TDES, and Ingenico OnGuard encryption types) and Sub-Table ID 02 (Device Type, up to 8 bytes, required for AES DUKPT, optional for TDES and Ingenico OnGuard); this is a Segment 111 companion table, not a Segment 116 field
- BR-RULE-SEG110-R-020: The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format codes 'T$' (T<aba>A<acct>C<checknum>), '18' (<aba><acct> concatenated), '09' (raw MICR, not touched), and '19' (numerals from bottom of check); Segment 110's MICR Data (122) and this Segment 111 sub-table are companions, confirmed independently of the AI-generated requirement set, which had attributed these codes to Segment 110 with LOW-MEDIUM confidence (44-47%)
- BR-RULE-SEG111-R-003: Indicator length

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:911
For MasterCard card type, Merchant must send MIT/CIT category and subcategory data valid for CIT transactions.

Disposition: PARTIAL_SCOPE_OVERLAP

Generic indicator/length rules do not establish individual Table ID, sub-table, value, network or CIT/MIT applicability. No table-specific match credit.

Selected Test BRs: BR-RULE-SEG111-R-003;BR-RULE-SEG111-R-004

Ranked retrieval only:
- BR-RULE-SEG111-R-003: Indicator length
- BR-RULE-SEG116-R-008: A companion sub-table, 'Additional TransArmor Data' (Segment 111, Variable Information Indicator/Table ID 052), carries a longer Key ID and/or special device type via two documented sub-tables: Sub-Table ID 01 (KSN, up to 40 bytes, required for AES DUKPT, TDES, and Ingenico OnGuard encryption types) and Sub-Table ID 02 (Device Type, up to 8 bytes, required for AES DUKPT, optional for TDES and Ingenico OnGuard); this is a Segment 111 companion table, not a Segment 116 field
- BR-RULE-SEG110-R-020: The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format codes 'T$' (T<aba>A<acct>C<checknum>), '18' (<aba><acct> concatenated), '09' (raw MICR, not touched), and '19' (numerals from bottom of check); Segment 110's MICR Data (122) and this Segment 111 sub-table are companions, confirmed independently of the AI-generated requirement set, which had attributed these codes to Segment 110 with LOW-MEDIUM confidence (44-47%)

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:927
For MasterCard card type, Merchant must send MIT/CIT category and subcategory data using Data Element 111, Table ID 56, Sub Table ID 09.

Disposition: PARTIAL_SCOPE_OVERLAP

Generic indicator/length rules do not establish individual Table ID, sub-table, value, network or CIT/MIT applicability. No table-specific match credit.

Selected Test BRs: BR-RULE-SEG111-R-003;BR-RULE-SEG111-R-004

Ranked retrieval only:
- BR-RULE-SEG116-R-008: A companion sub-table, 'Additional TransArmor Data' (Segment 111, Variable Information Indicator/Table ID 052), carries a longer Key ID and/or special device type via two documented sub-tables: Sub-Table ID 01 (KSN, up to 40 bytes, required for AES DUKPT, TDES, and Ingenico OnGuard encryption types) and Sub-Table ID 02 (Device Type, up to 8 bytes, required for AES DUKPT, optional for TDES and Ingenico OnGuard); this is a Segment 111 companion table, not a Segment 116 field
- BR-RULE-SEG110-R-020: The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format codes 'T$' (T<aba>A<acct>C<checknum>), '18' (<aba><acct> concatenated), '09' (raw MICR, not touched), and '19' (numerals from bottom of check); Segment 110's MICR Data (122) and this Segment 111 sub-table are companions, confirmed independently of the AI-generated requirement set, which had attributed these codes to Segment 110 with LOW-MEDIUM confidence (44-47%)
- BR-RULE-SEG111-R-003: Indicator length

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:942
For MasterCard card type, Merchant must send MIT/CIT category and subcategory data applicable to MIT transactions.

Disposition: PARTIAL_SCOPE_OVERLAP

Generic indicator/length rules do not establish individual Table ID, sub-table, value, network or CIT/MIT applicability. No table-specific match credit.

Selected Test BRs: BR-RULE-SEG111-R-003;BR-RULE-SEG111-R-004

Ranked retrieval only:
- BR-RULE-SEG116-R-008: A companion sub-table, 'Additional TransArmor Data' (Segment 111, Variable Information Indicator/Table ID 052), carries a longer Key ID and/or special device type via two documented sub-tables: Sub-Table ID 01 (KSN, up to 40 bytes, required for AES DUKPT, TDES, and Ingenico OnGuard encryption types) and Sub-Table ID 02 (Device Type, up to 8 bytes, required for AES DUKPT, optional for TDES and Ingenico OnGuard); this is a Segment 111 companion table, not a Segment 116 field
- BR-RULE-SEG110-R-020: The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format codes 'T$' (T<aba>A<acct>C<checknum>), '18' (<aba><acct> concatenated), '09' (raw MICR, not touched), and '19' (numerals from bottom of check); Segment 110's MICR Data (122) and this Segment 111 sub-table are companions, confirmed independently of the AI-generated requirement set, which had attributed these codes to Segment 110 with LOW-MEDIUM confidence (44-47%)
- BR-RULE-SEG111-R-003: Indicator length

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:950
For Discover Delayed card sale transactions, Data Element 111 Table ID 16 Delayed card sale Indicator must be 'D'.

Disposition: PARTIAL_SCOPE_OVERLAP

Generic indicator/length rules do not establish individual Table ID, sub-table, value, network or CIT/MIT applicability. No table-specific match credit.

Selected Test BRs: BR-RULE-SEG111-R-003;BR-RULE-SEG111-R-004

Ranked retrieval only:
- BR-RULE-SEG111-R-003: Indicator length
- BR-RULE-SEG116-R-008: A companion sub-table, 'Additional TransArmor Data' (Segment 111, Variable Information Indicator/Table ID 052), carries a longer Key ID and/or special device type via two documented sub-tables: Sub-Table ID 01 (KSN, up to 40 bytes, required for AES DUKPT, TDES, and Ingenico OnGuard encryption types) and Sub-Table ID 02 (Device Type, up to 8 bytes, required for AES DUKPT, optional for TDES and Ingenico OnGuard); this is a Segment 111 companion table, not a Segment 116 field
- BR-RULE-SEG110-R-020: The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format codes 'T$' (T<aba>A<acct>C<checknum>), '18' (<aba><acct> concatenated), '09' (raw MICR, not touched), and '19' (numerals from bottom of check); Segment 110's MICR Data (122) and this Segment 111 sub-table are companions, confirmed independently of the AI-generated requirement set, which had attributed these codes to Segment 110 with LOW-MEDIUM confidence (44-47%)

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:962
For Discover delayed card sale transactions, Table ID 16 Delayed card sale Indicator must be 'D'.

Disposition: PARTIAL_SCOPE_OVERLAP

Generic indicator/length rules do not establish individual Table ID, sub-table, value, network or CIT/MIT applicability. No table-specific match credit.

Selected Test BRs: BR-RULE-SEG111-R-003;BR-RULE-SEG111-R-004

Ranked retrieval only:
- BR-RULE-SEG111-R-003: Indicator length
- BR-RULE-SEG116-R-008: A companion sub-table, 'Additional TransArmor Data' (Segment 111, Variable Information Indicator/Table ID 052), carries a longer Key ID and/or special device type via two documented sub-tables: Sub-Table ID 01 (KSN, up to 40 bytes, required for AES DUKPT, TDES, and Ingenico OnGuard encryption types) and Sub-Table ID 02 (Device Type, up to 8 bytes, required for AES DUKPT, optional for TDES and Ingenico OnGuard); this is a Segment 111 companion table, not a Segment 116 field
- BR-RULE-SEG110-R-020: The MICR encoding convention for a manually keyed check is carried in Segment 111 (Variable Information Data Segment), not Segment 110: Variable Information Indicator = '024' (Manual Check MICR Type), Table Length fixed 2, with documented Table Data format codes 'T$' (T<aba>A<acct>C<checknum>), '18' (<aba><acct> concatenated), '09' (raw MICR, not touched), and '19' (numerals from bottom of check); Segment 110's MICR Data (122) and this Segment 111 sub-table are companions, confirmed independently of the AI-generated requirement set, which had attributed these codes to Segment 110 with LOW-MEDIUM confidence (44-47%)

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:976
Device does not perform expiration date validation in an EMV card transaction

Disposition: COUNTERPART_NOT_ESTABLISHED

Exact card-family expiration validation/prompt/default behavior is not established by generic account or loyalty format rules. Resolve request context and independently expected behavior.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG108-R-016: Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card
- BR-RULE-SEG108-R-006: Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)
- BR-RULE-SEG108-R-024: Segment 108 does not appear in the Loyalty Card Transaction Response, which mirrors the generic Financial Transaction Response layout

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:977
If account number is manually keyed, device prompts for Expiration Date (MMYY) and other optional/conditional data

Disposition: PARTIAL_SCOPE_OVERLAP

Entry method agrees broadly; retry count, manual expiration prompt and card-family conditions need specific tests.

Selected Test BRs: BR-RULE-SEG100-R-034

Ranked retrieval only:
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context
- BR-SEG100-ANN-O: Tokenized Segment 100 transactions use Appendix O token account, expiration, cryptogram, and presentment concepts where applicable.
- BR-SEG100-GAP-ACCOUNT: Segment 100 shall contain the account number or identification value required by the transaction context.

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:978
If manual entry is used, this is indicated on receipt and journal by an asterisk before the account number

Disposition: COUNTERPART_NOT_ESTABLISHED

No equivalent receipt/storage obligation established. Account format, token validation and receipt Card Type labels are not substitutes. Receipt or storage evidence is required.

Selected Test BRs: None established

Ranked retrieval only:
- BR-RULE-SEG100-R-034: Account Number representation agrees with POS entry method
- BR-SEG100-E2-001: Account Number representation must agree with the declared POS entry method.
- BR-RULE-SEG100-R-041: Account Number or identification value is present for the transaction context

Artifact review flags: []

## REQ-SRC-ATL105-PDF-001:982
If device is EMV enabled, card is chip, and card is swiped, always send Fallback Entry Mode of '80' or spec equivalent.

Disposition: PARTIAL_SCOPE_OVERLAP

Test excludes Segment 130 for fallback/MSR modes 80/90; exact device/chip trigger and transmitted mode selection remain additional AI clauses.

Selected Test BRs: BR-RULE-SEG130-R-019

Ranked retrieval only:
- BR-RULE-SEG130-R-019: Segment 130 is present only for genuine chip-read EMV transactions; a fallback swipe (Entry Mode 80) or plain MSR swipe (Entry Mode 90) carries no Segment 130
- BR-RULE-SEG130-R-007: EMV Chip Data Length (Element 189) is required and identifies the length of the following EMV Chip Data field; valid values 000-999
- BR-RULE-SEG130-R-006: EMV Card Sequence Number (Element 188), when populated, identifies the sequence number of the EMV card; Conditional, sourced Device

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:983
If card is NOT chip or device is not chip capable, and card is swiped, send as MSR Entry Mode of '90' or spec equivalent.

Disposition: PARTIAL_SCOPE_OVERLAP

Test excludes Segment 130 for fallback/MSR modes 80/90; exact device/chip trigger and transmitted mode selection remain additional AI clauses.

Selected Test BRs: BR-RULE-SEG130-R-019

Ranked retrieval only:
- BR-RULE-SEG130-R-019: Segment 130 is present only for genuine chip-read EMV transactions; a fallback swipe (Entry Mode 80) or plain MSR swipe (Entry Mode 90) carries no Segment 130
- BR-RULE-SEG130-R-007: EMV Chip Data Length (Element 189) is required and identifies the length of the following EMV Chip Data field; valid values 000-999
- BR-SEG100-APPR-CROSS-FIELD: EMV chip values such as authorized amount, transaction type, currency, and terminal country shall agree with the Segment 100 transaction context.

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]

## REQ-SRC-ATL105-PDF-001:997
Purchase Card Data Segment sent only on transactions requiring purchase card data.

Disposition: PARTIAL_SCOPE_OVERLAP

Same purchase-card companion topic, but AI only-when and Test requiring-when-needed implications are not logically identical.

Selected Test BRs: BR-RULE-SEG100-R-052

Ranked retrieval only:
- BR-RULE-SEG100-R-052: Purchase-card flows requiring purchase-card data include Segment 104
- BR-SEG100-GAP-PURCHASE-CARD: A purchase-card flow requiring purchase-card data shall include Segment 104 in addition to Segment 100.
- BR-RULE-SEG109-R-003: Segment 109 is in Field No. 3 and is sent only for transactions requiring electronic mail

Artifact review flags: ["ELEMENT_SCOPE_UNRESOLVED"]
