# Complete Matched Business Requirements: AI vs Test Solution

- Delivery: `2026-09-23/Run1+Run2`
- Scope: every crosswalk mapping with one or more AI requirement IDs
- `CONFIRMED` mappings count toward independent coverage; `REVIEW_REQUIRED` mappings do not.

## Segment 100 - SEG100-R-001 - REVIEW_REQUIRED

**Test Solution rule:** Segment 100 is required exactly once  
**Class/severity:** structure / error  
**Canonical anchor:** `ATL105|2026-3|11.1.1|100|null|segment-100-required-once`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-100

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:925|Credit card receipt requirements in section 10.1.7 also apply to EMV cards.|BR-158-3|158|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:949|NFC Payment Tokenization Data Segment is required on all initial and recurring transactions involving tokenized data.|BR-165-6|165|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:955|Incomm Market Basket Data (Request) Segment applicability determined by section 12.35.|BR-166-2|166|57% MEDIUM|
|REQ-SRC-ATL105-PDF-001:960|All Financial Transaction Responses contain Data Section No. 1.|BR-168-3|168|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1027|Load Type field fixed value is 'P' meaning Partial Load.|BR-187-5|187|84% HIGH|
|REQ-SRC-ATL105-PDF-001:1030|Dial String Data Segment is sent only on a Table Load Response and a Phone Load Response.|BR-188-3|188|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1045|Load Type fixed value is 'D' identifying Date and Time Load.|BR-192-4|192|84% HIGH|
|REQ-SRC-ATL105-PDF-001:1068|There is no Field Separator between fields in the Moneris Key Load Request message.|BR-199-2|199|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1073|No Field Separator exists between fields in the Moneris Key Load Response message.|BR-200-2|200|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1086|Purchase Card Data Segment sent only on transactions requiring purchase card data.|BR-203-5|203|79% MEDIUM|

## Segment 100 - SEG100-R-007 - REVIEW_REQUIRED

**Test Solution rule:** Element 63 equals serialized segment count  
**Class/severity:** serialization / error  
**Canonical anchor:** `ATL105|2026-3|11.1.1|DATA-SECTION-1|63|number-of-segments`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-100

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1688|Download Indicator required for EMV Public Key loads when checksum mismatch occurs between request and host.|BR-373-3|373|70% MEDIUM|

## Segment 100 - SEG100-R-013 - REVIEW_REQUIRED

**Test Solution rule:** Message format identifier is ATL105  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|11.1.1|DATA-SECTION-1|55|message-format-version-identifier`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-100

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:956|Incomm Market Basket Data (Response) Segment included only when Request segment is in the request.|BR-166-3|166|57% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1657|Expiration date is mandatory for Accel/STAR e-commerce and one-time bill payment transactions.|BR-365-9|365|79% MEDIUM|

## Segment 100 - SEG100-R-015 - CONFIRMED

**Test Solution rule:** Segment Type is 100  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.1|100|85|segment-type`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1072|Key Data section repeats up to 3 times (max 48 bytes total); presence based on Key Indicators field.|BR-200-1|200|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1089|EMV Request Data Segment is required for all EMV card transactions and is the only segment required for all EMV financial transactions.|BR-203-8|203|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1095|Incomm Market Basket Data (Response) Segment returned when Request segment was included in the request.|BR-204-4|204|57% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1131|Standard Message Data Segment is the only required data segment on all Financial Transaction Request messages.|BR-220-2|220|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1141|For DUKPT PIN encryption, Encrypted PIN Block Data is a 16- to 20-byte KSN plus a 16-byte PIN block.|BR-221-3|221|61% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1148|Each Fleet Tag has a 3-byte fixed-length tag portion and up to 31 bytes of data.|BR-224-2|224|21% LOW|
|REQ-SRC-ATL105-PDF-001:1153|a field is not populated — still send the Product Data Field Delimiter.|BR-226-3|226|28% LOW|
|REQ-SRC-ATL105-PDF-001:1167|When a field is not populated, still send the Field Separator or Field Delimiter.|BR-226-14|226|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1174|A Field Separator follows Product Amount if it is the last element in the segment.|BR-228-2|228|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1180|All fields are separated by Field Separators; unpopulated fields still send Field Separator.|BR-230-2|230|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1190|a field is not populated — still send the Field Separator.|BR-235-1|235|25% LOW|
|REQ-SRC-ATL105-PDF-001:1196|Expiration Date is reserved for future use.|BR-236-1|236|64% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1235|Print Data Segment always appears at the end of a Financial Transaction response.|BR-249-2|249|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1253|Device Card Table Version consists of seven version values, each 5 characters, strung together.|BR-252-7|252|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1277|Product Code 991 indicates an administrative amount, used in a pre-pay environment when price is adjusted to include discount amount.|BR-257-5|257|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1282|Fuel Volume Data field length is determined by segment length in field 2, up to max 3,600.|BR-259-2|259|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1283|Fields are not separated by Field Separators; unpopulated fields are immediately followed by the next field.|BR-259-3|259|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1285|Segment 119 always appears in Field No. 3 in Data Section No. 3.|BR-260-1|260|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1293|Field Nos. 17-19 are sent up to a maximum of 20 times, once for each card type, in order shown.|BR-261-1|261|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1298|Print Data 2 Segment is only sent on Financial Transaction Response messages requiring large amounts of print data.|BR-263-1|263|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1356|A Field Separator always follows Segment Type and Segment Length, and the last Tax Amount per product.|BR-282-8|282|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1391|Prompt Tokens differ by authorizer; different authorizers support different prompts.|BR-289-3|289|21% LOW|
|REQ-SRC-ATL105-PDF-001:1402|Table Length for Payee Name has a maximum variable value of 017.|BR-294-3|294|21% LOW|
|REQ-SRC-ATL105-PDF-001:1407|Flags in Table Data must be separated by a comma; if a flag is absent, it can be skipped using just the separator.|BR-295-2|295|57% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1467|Field is included in response only when merchant sent Account Updater Request Indicator with value 'Y' or 'I' in request.|BR-313-1|313|85% HIGH|

## Segment 100 - SEG100-R-016 - REVIEW_REQUIRED

**Test Solution rule:** Segment Length represents encoded Segment 100 content  
**Class/severity:** serialization / error  
**Canonical anchor:** `ATL105|2026-3|12.1|100|84|segment-length`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-100

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1128|RQST/CURRENT HASH MATCH code indicates key update is not required.|BR-214-4|214|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1748|Number of Print Lines does not include Data Element 168, Count of Receipt Text Line.|BR-387-1|387|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1754|Financial Transaction Requests: Number of Segments followed by Data Segment 100, plus optional input segments from Chapter 12.|BR-388-1|388|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1755|Loyalty Card Transaction Requests: Number of Segments followed by Segment 100, Segment 108, and Segment 114.|BR-388-2|388|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1756|ECA/TeleCheck Service Transaction Requests: Number of Segments followed by Segment 100, Segment 110, and Segment 111.|BR-388-3|388|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1757|Totals Requests: Number of Segments always followed by Segment 105, Totals Data Segment.|BR-388-4|388|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1758|Electronic Mail Requests: Number of Segments always followed by Segment 109, Electronic Mail Data Segment.|BR-388-5|388|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1760|TransArmor PKI Encryption and Tokenization: Used for Key and Key ID Load, followed by Segment 116, TransArmor Load Data Segment.|BR-388-7|388|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1761|Number of Segments value N indicates transaction has Segment 100 plus N-1 input segments listed in Chapter 12.|BR-388-8|388|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1764|Password required for totals request or electronic mail request.|BR-389-3|389|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1769|PC Tax Amount differs from Element No. 99, Tax Amount.|BR-391-3|391|71% MEDIUM|

## Segment 100 - SEG100-R-017 - REVIEW_REQUIRED

**Test Solution rule:** Terminal Identifier has valid format  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.1|100|102|terminal-identifier`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-100

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1071|Load Subtype must be fixed value M (Moneris Key Load) identifying subtype as Moneris.|BR-199-5|199|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1275|Discount amount in Financial Transaction Request is included in Product Code Data Segment using Product Code 941 or 991.|BR-257-3|257|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1805|For TransArmor PKI Encryption and Tokenization transaction, Response Code identifies approved, rejected, or merchant not TransArmor.|BR-396-6|396|80% HIGH|

## Segment 100 - SEG100-R-018 - REVIEW_REQUIRED

**Test Solution rule:** Prompt Code represents transaction and card context  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.1|100|78|prompt-code`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-100

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1708|For transactions with fuel and nonfuel amounts, discounts/coupons are first applied to the nonfuel amount.|BR-378-5|378|79% MEDIUM|

## Segment 100 - SEG100-R-020 - REVIEW_REQUIRED

**Test Solution rule:** Partial Approval Indicator uses an allowed value  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.1|100|121|partial-approval-indicator`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-100

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:460|If card cannot be read magnetically after three swipe attempts, device should prompt for manual entry of Account Number and Expiration Date (Format: MMYY).|BR-79-10|79|76% MEDIUM|
|REQ-SRC-ATL105-PDF-001:464|Partial approval info returned when Partial Approval Indicator value is 1.|BR-82-1|82|81% HIGH|
|REQ-SRC-ATL105-PDF-001:494|Product Code required in FSA/HRA transaction requests.|BR-87-3|87|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:499|Approved Purchase Reversal transactions are subtracted from count and amount of card type totals bucket.|BR-87-8|87|80% HIGH|
|REQ-SRC-ATL105-PDF-001:546|Host message should contain Account Number, followed by '=' delimiter, followed by 4-digit Expiration Date (YYMM).|BR-92-10|92|71% MEDIUM|
|REQ-SRC-ATL105-PDF-001:550|Device does not perform expiration date validation on debit card; online authorization always required.|BR-94-1|94|84% HIGH|
|REQ-SRC-ATL105-PDF-001:553|If Response Code = 1 (decline) and Decline Code = 24, treat as already captured and update totals as approved.|BR-94-4|94|84% HIGH|
|REQ-SRC-ATL105-PDF-001:573|Issuers must immediately process matched reversal transactions and release holds on cardholder available funds.|BR-97-4|97|85% HIGH|
|REQ-SRC-ATL105-PDF-001:578|If Partial Approvals are not supported, transaction is either approved for full amount or declined.|BR-98-1|98|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1961|When Variable Information Indicator = 064, Service Location Information data is included.|BR-423-5|423|84% HIGH|
|REQ-SRC-ATL105-PDF-001:1962|When Variable Information Indicator = 065, Merchant Payment Gateway ID Data is included.|BR-423-6|423|84% HIGH|

## Segment 100 - SEG100-R-022 - REVIEW_REQUIRED

**Test Solution rule:** Unneeded trailing optional fields are omitted  
**Class/severity:** serialization / error  
**Canonical anchor:** `ATL105|2026-3|12.1|100|null|trailing-optional-fields-omitted`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-100

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1087|Variable Information Data Segment contains data elements unique to transactions requiring variable information.|BR-203-6|203|79% MEDIUM|

## Segment 100 - SEG100-R-030 - REVIEW_REQUIRED

**Test Solution rule:** Terminal Identifier respects load-flow length dependency  
**Class/severity:** dependency / error  
**Canonical anchor:** `ATL105|2026-3|12.1|100|102|terminal-identifier-load-length`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-100

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1806|For an EMV transaction, Response Code identifies an approved EMV transaction or a rejected EMV transaction.|BR-396-7|396|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1807|Transaction Code 'L' meaning depends on context: rejected TransArmor error or approved EMV Key Load, last block.|BR-397-1|397|31% LOW|
|REQ-SRC-ATL105-PDF-001:1808|Transaction Code 'M' meaning depends on context: approved EMV Key Load more pending or declined totals with proprietary host discount data pending.|BR-397-2|397|31% LOW|
|REQ-SRC-ATL105-PDF-001:2857|Security Condition values 4-7 are reserved for national use.|BR-689-1|689|21% LOW|

## Segment 100 - SEG100-R-031 - REVIEW_REQUIRED

**Test Solution rule:** Terminal Identifier matches declared components  
**Class/severity:** dependency / error  
**Canonical anchor:** `ATL105|2026-3|12.1|100|102|terminal-identifier-components`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-100

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:774|ZIP Code value is not validated by the issuer and does not affect approval or decline.|BR-129-2|129|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1006|All Electronic Mail Request messages contain Element Nos. 55 and 63.|BR-182-1|182|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1809|If a TransArmor Key Load request is declined, the last element in the message will be Response Code.|BR-398-1|398|85% HIGH|

## Segment 100 - SEG100-R-033 - REVIEW_REQUIRED

**Test Solution rule:** Prompt Code card type or flow code matches expected context  
**Class/severity:** dependency / error  
**Canonical anchor:** `ATL105|2026-3|12.1|100|78|prompt-code-card-type`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-100

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1520|For multi-fuel transactions, primary fuel product must be sent as the very first product code in the segment.|BR-320-11|320|85% HIGH|

## Segment 100 - SEG100-R-034 - REVIEW_REQUIRED

**Test Solution rule:** Account Number representation agrees with POS entry method  
**Class/severity:** dependency / error  
**Canonical anchor:** `ATL105|2026-3|10.1.1|100|2|account-number-entry-method`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-100

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:457|If AFD transaction has encrypted data in TransArmor Encryption Block, Track 1 data must be present inside block with Encryption Target as Track 1.|BR-79-7|79|90% HIGH|
|REQ-SRC-ATL105-PDF-001:513|Card Type ID for Debit is 'DB'.|BR-89-5|89|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:642|If original response has Segment 134 with Settlement Type = 'X' (Non-traditional Signature Debit), follow-up transactions must include Settlement Data Acceptance Flag, Table ID 21.|BR-107-6|107|76% MEDIUM|
|REQ-SRC-ATL105-PDF-001:709|eWIC card receipts must include Payment Tender Type, Transaction Type, Clerk ID, Voucher ID, amounts, balances, approved/declined messages, benefit expiration date.|BR-119-4|119|60% MEDIUM|
|REQ-SRC-ATL105-PDF-001:755|After Balance Merge, remaining card's account balance is reduced to $0.00.|BR-127-9|127|85% HIGH|
|REQ-SRC-ATL105-PDF-001:2844|Payment tokens must not have the same value as a cardholder PAN.|BR-681-5|681|70% MEDIUM|

## Segment 100 - SEG100-R-037 - REVIEW_REQUIRED

**Test Solution rule:** Lifecycle follow-ups reuse the original Sequence Number and required approval context  
**Class/severity:** lifecycle / error  
**Canonical anchor:** `ATL105|2026-3|10|100|86|lifecycle-correlation`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-100

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:291|A TOR is not supported for Debit POS Capture or Debit CAT Capture transactions.|BR-37-4|37|85% HIGH|
|REQ-SRC-ATL105-PDF-001:292|A time-out occurs if the 30-second response interval expires without a response, or response arrives late.|BR-38-1|38|85% HIGH|
|REQ-SRC-ATL105-PDF-001:299|Off-line transactions are forwarded to BUYPASS only after a valid response is received for all queued TORs.|BR-38-8|38|85% HIGH|
|REQ-SRC-ATL105-PDF-001:316|Sequence Number of a TOR must be same as Sequence Number of the financial transaction being reversed.|BR-43-5|43|90% HIGH|
|REQ-SRC-ATL105-PDF-001:317|Each financial transaction request forwarded to BUYPASS should be assigned a new Sequence Number.|BR-43-6|43|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:323|A TOR can only be sent for financial transactions.|BR-45-3|45|90% HIGH|
|REQ-SRC-ATL105-PDF-001:467|Card balance, if returned by issuer, is placed in Additional Information Data Segment.|BR-82-4|82|85% HIGH|
|REQ-SRC-ATL105-PDF-001:475|Card-absent merchants are allowed up to 72 hours to process required partial or full authorization reversals.|BR-83-3|83|90% HIGH|
|REQ-SRC-ATL105-PDF-001:497|Approved Purchase/Capture transactions are added to count and amount of card type totals bucket.|BR-87-6|87|80% HIGH|
|REQ-SRC-ATL105-PDF-001:502|Approved Merchandise Return transactions are added to count and subtracted from amount of card type totals bucket.|BR-87-11|87|80% HIGH|
|REQ-SRC-ATL105-PDF-001:517|Card Type ID for JCB is 'JCB-DISC'.|BR-89-9|89|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:555|Debit card transaction requires PIN entry on DES-compliant device, encrypted using DUKPT PIN encryption method.|BR-94-6|94|90% HIGH|
|REQ-SRC-ATL105-PDF-001:562|Approved Amount contains partial amount; POS must prompt for remaining balance due.|BR-95-4|95|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:576|Sequence Number present in original transaction must be present and identical in the Authorization Only Reversal transaction.|BR-97-7|97|90% HIGH|
|REQ-SRC-ATL105-PDF-001:581|FSA/HRA Purchase/Capture requires Partial Approval Indicator and Product Code Data Segment.|BR-100-1|100|81% HIGH|
|REQ-SRC-ATL105-PDF-001:970|Loyalty Card Transaction Request Data Section 2 contains the Standard Message Data Segment.|BR-171-2|171|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1077|Data Section No. 3 may contain one or more of Fleet, Product Code, Purchase Card, Variable Information, or EMV Request segments.|BR-201-4|201|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1773|Phone Number data consists of all data up to the following C (Log-on Indicator) or A (Delimiter) character.|BR-392-4|392|54% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1775|Product Amount and related elements repeat for up to a maximum of 10 products in Segment 102 or 157.|BR-392-6|392|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1776|Valid Product Amount values range from 1 to 999999999999.|BR-392-7|392|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1777|Product Code and related fields repeat up to 10 products in Data Segment 102 or 157.|BR-393-1|393|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1778|A unique Product Code must be sent for each type of fuel purchase.|BR-393-2|393|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2872|Pass-through DWO must ensure cardholder consents in writing to account use in wallet, and must not perform Visa payment services for another DWO.|BR-696-1|696|70% MEDIUM|

## Segment 100 - SEG100-R-039 - CONFIRMED

**Test Solution rule:** Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context  
**Class/severity:** dependency / error  
**Canonical anchor:** `ATL105|2026-3|12.1|100|121|partial-approval-context`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:484|Bill payment/recurring and installment payment transactions must be identified by Variable Information Indicator in Segment 111.|BR-84-3|84|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:485|Point-of-Sale Entry Mode in Element 113 must be populated correctly on all transactions when RFID receiver is connected.|BR-84-4|84|85% HIGH|
|REQ-SRC-ATL105-PDF-001:489|For Split Tender, the cardholder must use a different payment form for nonqualified items.|BR-85-4|85|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:492|Market-Specific Data Indicator required in FSA/HRA transaction requests.|BR-87-1|87|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:519|Card Type ID for Visa is 'VISA'.|BR-89-11|89|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:554|Any response to Debit Card Completion transaction ends retry attempts, whether approved or declined.|BR-94-5|94|90% HIGH|
|REQ-SRC-ATL105-PDF-001:565|Merchants supporting cashback must also support Partial Approval transactions (Interlink mandate, April 2013).|BR-95-7|95|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:566|If cashback is partially approved, partially approved amount applies to purchase only, not cashback.|BR-96-1|96|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:571|Acquirers must ensure merchants process authorization reversals for card-present errors/cancellations within 24 hours.|BR-97-2|97|90% HIGH|
|REQ-SRC-ATL105-PDF-001:575|Approval Number present in original transaction must be present and identical in the Authorization Only Reversal transaction.|BR-97-6|97|84% HIGH|
|REQ-SRC-ATL105-PDF-001:580|Total QHP Amount (Code 894) is required and includes Clinical, Dental, Prescription/Rx, and Vision/Optical amounts.|BR-99-1|99|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:637|Transactions cannot be keyed.|BR-107-1|107|90% HIGH|
|REQ-SRC-ATL105-PDF-001:719|an activation (Please see section 10.7.3.6 — "Activation Transaction.|BR-123-1|123|28% LOW|
|REQ-SRC-ATL105-PDF-001:1566|Inclusion of the EMV Terminal Floor Limits segment in a table load depends on a Special set at the terminal level.|BR-333-1|333|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1963|When Variable Information Indicator = 066, Digital Commerce Data is included.|BR-423-7|423|84% HIGH|
|REQ-SRC-ATL105-PDF-001:1964|When Variable Information Indicator = 067, Anticipated Amount data is included.|BR-423-8|423|84% HIGH|
|REQ-SRC-ATL105-PDF-001:1965|When Variable Information Indicator = 068, Additional Transaction Fee 1 Transaction Fee data is included.|BR-423-9|423|84% HIGH|
|REQ-SRC-ATL105-PDF-001:2257|Encrypted PIN Block Data field has maximum length 36 bytes with no example data.|BR-499-7|499|35% LOW|

## Segment 100 - SEG100-R-040 - REVIEW_REQUIRED

**Test Solution rule:** Information Byte uses a source-defined value  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.1|100|44|information-byte`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-100

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:991|A Field Separator separates Field Nos. 1 and 2; a Field Separator follows Field No. 2.|BR-176-4|176|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1004|Card Labels in parentheses are used instead of field numbers to identify total buckets.|BR-179-5|179|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1012|Network Management Message must be 'COMMTEST' for Communications Test request from device.|BR-184-1|184|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1019|In dial communications, when host indicates a download is requested, device receives ENQ instead of EOT and sends up a Partial Load Request.|BR-186-3|186|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1036|Load flag in merchant profile on BUYPASS must be set to PHON for phone load to take place.|BR-190-1|190|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1642|If Account Number obtained via Track 2 reader, Account Number is data before '=', Discretionary Block Data is data after.|BR-364-1|364|73% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1643|If Account Number obtained via Track 1 reader (credit only), split fields at first '^' character.|BR-364-2|364|73% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1644|Account Number and Card Discretionary Block Data combined do not exceed 76 characters for Track 1.|BR-364-3|364|78% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2181|Merchant can send only Token data block A, or both Token data blocks A and B, in the request.|BR-478-1|478|80% HIGH|

## Segment 100 - SEG100-R-041 - CONFIRMED

**Test Solution rule:** Account Number or identification value is present for the transaction context  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.1|100|2|account-number`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:511|Card Type ID must print on all credit card receipts.|BR-89-3|89|80% HIGH|
|REQ-SRC-ATL105-PDF-001:512|Card Type ID for Amex is 'AX'.|BR-89-4|89|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:649|Split Tender Processing requirements for credit cards also apply to Signature Debit card.|BR-108-7|108|80% HIGH|
|REQ-SRC-ATL105-PDF-001:650|Qualified Healthcare Products requirements for credit cards also apply to Signature Debit card.|BR-108-8|108|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1526|Product Data section repeats per product for a maximum of 10 products, total variable length up to 370 bytes.|BR-321-2|321|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1532|If unit price omits assumed decimal place digits (e.g. '359' instead of '3059'), it is invalid.|BR-322-4|322|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1546|Pause Indicator is conditional; fixed value B indicates device should pause about one second before proceeding.|BR-326-4|326|51% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1548|Dial String Terminator has fixed value F, sourced from Host.|BR-327-2|327|64% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1550|Date and Time Data Segment has a maximum length of 23 alphanumeric characters.|BR-328-1|328|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1551|Fields in the segment are not separated by Field Separators; unpopulated fields are followed immediately by the next field.|BR-328-2|328|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1552|Data Type Indicator field has fixed value colon (:) indicating day of week, date, and time data follows.|BR-328-3|328|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1968|When Variable Information Indicator = 071, Enabler Verification Value data is included.|BR-423-12|423|84% HIGH|
|REQ-SRC-ATL105-PDF-001:2860|Only Hash Algorithm Indicator value 01 (SHA-1) is currently supported.|BR-691-2|691|21% LOW|

## Segment 100 - SEG100-R-042 - REVIEW_REQUIRED

**Test Solution rule:** Card Discretionary Block Data follows conditional source rules  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.1|100|12|card-discretionary-block-data`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-100

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:253|TAVV Result Code must be one of: TAVV response values:, 1 TAVV Cryptogram failed validation, 2 TAVV Cryptogram passed validation, validation, validation.|ENT-ELEM-238|490|50% MEDIUM|

## Segment 100 - SEG100-R-043 - CONFIRMED

**Test Solution rule:** Encrypted PIN Block Data follows DUKPT representation when PIN applies  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.1|100|33|encrypted-pin-block-data`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:352|The encrypted PIN block and the KSN are sent to the customer's host system for forwarding to BUYPASS.|BR-52-5|52|69% MEDIUM|
|REQ-SRC-ATL105-PDF-001:354|A seed key and corresponding serial number can generate approximately one million unique PIN encryption keys.|BR-52-7|52|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:359|Pathway terminal configuration for the PIN pad value must be defined for 3DES DUKPT.|BR-53-4|53|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:370|Pad digits F consist of (14-L) digits of 1111 (F16) after the entered PIN.|BR-55-4|55|21% LOW|
|REQ-SRC-ATL105-PDF-001:371|For PAN field, the check digit before the field separator is dropped and the remaining right-most 12 digits of Track 2 data are used.|BR-55-5|55|21% LOW|
|REQ-SRC-ATL105-PDF-001:375|Device may receive 4R or 4< Decline Code if encrypted PIN data is missing or not sent.|BR-56-2|56|64% MEDIUM|
|REQ-SRC-ATL105-PDF-001:377|Decline may occur if Terminal Profile PIN Encryption type is not set to UKP.|BR-56-4|56|64% MEDIUM|
|REQ-SRC-ATL105-PDF-001:390|BUYPASS assigns one merchant number and one device number to the overall AFP system.|BR-59-7|59|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:541|For American Express, default track should be Track 1; if unreadable, send unaltered Track 2 on initial transaction before manual entry.|BR-92-5|92|85% HIGH|
|REQ-SRC-ATL105-PDF-001:645|Bill Payment/Recurring Payment Transaction rules for credit card also apply to Signature Debit card.|BR-108-3|108|80% HIGH|
|REQ-SRC-ATL105-PDF-001:669|EBT receipts must show Tender Type, Transaction Type, Clerk ID, Voucher Number, amounts, balances, approved/declined messages, HIP data.|BR-113-2|113|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:910|CAVV Revised Format applies to Visa only, identifying Verified By Visa Format with ATN replacing XID.|BR-155-2|155|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1623|Address Line 2 positions 13 and 16 must be a space character.|BR-360-4|360|64% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1624|Approval Number is required on reversals for matching information to the original purchase.|BR-361-1|361|84% HIGH|
|REQ-SRC-ATL105-PDF-001:1625|If Approval Number is included in a Purchase Request, it is considered preauthorized and BUYPASS does not seek authorization.|BR-361-2|361|84% HIGH|
|REQ-SRC-ATL105-PDF-001:1626|In an Authorization Only Reversal, Approval Number present in original transaction must be present and identical in the reversal.|BR-361-3|361|84% HIGH|

## Segment 100 - SEG100-R-044 - REVIEW_REQUIRED

**Test Solution rule:** Pump/Lane Number is valid when fuel or lane context applies  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.1|100|79|pump-lane-number`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-100

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1721|BUYPASS Device Type must be '+*' for the CA Public Key File Load Request.|BR-381-3|381|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1722|Local Date and Local Time is only sent in a POS and CAT Capture transaction.|BR-381-4|381|76% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1724|Unless Merchant sends local date and time, BUYPASS cannot forward it to associations for Debit Cancellations handled as TORs.|BR-381-6|381|80% HIGH|
|REQ-SRC-ATL105-PDF-001:2849|Terminal Class Position No. 3: 0=On premise, 1=Off premise, 2-7 reserved national, 8-9 reserved private.|BR-684-3|684|21% LOW|

## Segment 100 - SEG100-R-045 - REVIEW_REQUIRED

**Test Solution rule:** Fuel Purchase Amount follows the net fuel amount rule  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.1|100|41|fuel-purchase-amount`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-100

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:392|If DLL flag set in host response, controller connects to BUYPASS and requests PIN encryption load once queue is empty.|BR-60-2|60|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1634|Host tracks Block Number of last complete data block sent in Electronic Mail response and CA Public Key File Load response.|BR-363-1|363|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1635|Device may reinstate interrupted Electronic Mail response transmission using Block Number in next request.|BR-363-2|363|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1636|Device always uses Block Number to track data block sent in a Proprietary Data Load request.|BR-363-3|363|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1638|Block Number value 000 means host sends first data block for CA Public Key File Load.|BR-363-5|363|80% HIGH|

## Segment 100 - SEG100-R-046 - REVIEW_REQUIRED

**Test Solution rule:** Nonfuel Amount follows the net nonfuel amount rule  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.1|100|58|nonfuel-amount`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-100

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1667|If merchant supports Interlink's Optional Cash Back Fee, Cash Amount includes cash back amount plus fee.|BR-368-3|368|84% HIGH|
|REQ-SRC-ATL105-PDF-001:1668|Clerk ID valid values range from 1 to 9999999999.|BR-368-4|368|84% HIGH|
|REQ-SRC-ATL105-PDF-001:1669|For ECA/TeleCheck processing, refer to Data Element No. 131, ECA/TeleCheck Clerk ID, instead of Clerk ID.|BR-368-5|368|68% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1670|If Currency Code not sent in Transaction Request, value defaults to 840 (United States currency).|BR-369-1|369|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1673|Current Date day value must be between 01 and 31.|BR-369-4|369|64% MEDIUM|

## Segment 100 - SEG100-R-047 - CONFIRMED

**Test Solution rule:** Tax Amount follows the total tax amount rule  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.1|100|99|tax-amount`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1110|A Field Separator follows Field No. 12 in the CA Public Key File Load Request.|BR-209-2|209|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1697|For EMV offline PIN validated debit, PIN block must contain a PIN or all F's, else transaction declines as invalid transaction.|BR-375-3|375|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1795|Pump/Lane Number valid codes/values are 1-99.|BR-395-3|395|81% HIGH|
|REQ-SRC-ATL105-PDF-001:1798|Quantity, Product Code, Unit of Measure, Unit Price, Product Amount repeat for up to 10 products in Segment 102 or 157.|BR-395-6|395|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1799|Quantity valid codes/values are 00000000, 01-399999999.|BR-395-7|395|85% HIGH|

## Segment 100 - SEG100-R-048 - REVIEW_REQUIRED

**Test Solution rule:** Cash Amount follows the total cash amount rule  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.1|100|17|cash-amount`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-100

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:552|If no response received, device should resend Debit Card Completion request on next dial attempt or 30 minutes later, whichever first.|BR-94-3|94|85% HIGH|
|REQ-SRC-ATL105-PDF-001:621|Transactions with cashback are excluded from PINless POS Debit processing.|BR-104-10|104|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1597|TransArmor VeriFone Edition Encryption/Tokenization all responses Account Number is 25 bytes maximum.|BR-356-12|356|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1598|Account Number in non-TransArmor initial/subsequent transaction requests is variable length up to 24 alphanumeric bytes.|BR-357-1|357|74% MEDIUM|

## Segment 100 - SEG100-R-049 - REVIEW_REQUIRED

**Test Solution rule:** Approval Number is present when the lifecycle requires approval reference  
**Class/severity:** dependency / error  
**Canonical anchor:** `ATL105|2026-3|12.1|100|5|approval-number`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-100

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:382|Communications Test Request format includes Message Format Version Identifier, Number of Segments, and Network Management Message COMMTEST.|BR-57-1|57|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:403|All queued transactions, including Daily Exception Log, should be sent to BUYPASS prior to requesting end-of-day totals.|BR-61-6|61|80% HIGH|
|REQ-SRC-ATL105-PDF-001:474|Acquirers must ensure merchants process authorization reversals within 24 hours for card-present transactions.|BR-83-2|83|90% HIGH|
|REQ-SRC-ATL105-PDF-001:520|Card Type ID for STAR Signature Debit is 'STAR'.|BR-89-12|89|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:561|Response Code value F indicates approval, partial amount approved.|BR-95-3|95|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:648|Partial Approval requirements for credit cards also apply to Signature Debit card.|BR-108-6|108|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:651|The device does not perform expiration date validation in an EBT card transaction.|BR-110-1|110|84% HIGH|
|REQ-SRC-ATL105-PDF-001:668|EBT card receipts must include credit card receipt requirements except Approved/Declined Message.|BR-113-1|113|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:720|Device does not perform expiration date validation in a stored value card transaction.|BR-123-1|123|84% HIGH|
|REQ-SRC-ATL105-PDF-001:752|POS device must subtract this amount from the SV1 amount total.|BR-127-6|127|57% MEDIUM|
|REQ-SRC-ATL105-PDF-001:790|Total Check Amount, Response Check Number, Return Fee Amount must appear on receipt.|BR-131-6|131|69% MEDIUM|
|REQ-SRC-ATL105-PDF-001:791|ECA/TeleCheck Acceptance Statement authorizing electronic deposit and return fee must appear on receipt.|BR-131-7|131|75% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1558|Fields in the Software IP Load Data Segment are not separated by Field Separators; unpopulated fields are followed immediately by the next field.|BR-330-2|330|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1559|Data Type Indicator field has fixed value '$' indicating software IP load data follows.|BR-330-3|330|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1560|End-of-Data Indicator field has fixed value '~' indicating end of data segment.|BR-330-4|330|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1561|Store and Forward Data Segment is only sent in a Table Load Response when Merchant Data Segment includes Card Type value 173.|BR-331-1|331|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1562|Fields in the Store and Forward Data Segment are not separated by Field Separators; unpopulated fields are followed immediately by the next field.|BR-331-2|331|80% HIGH|
|REQ-SRC-ATL105-PDF-001:2871|CARC value identifies the code returned by Visa in Bit No. 44.8 of the response.|BR-695-2|695|21% LOW|

## Segment 100 - SEG100-R-050 - REVIEW_REQUIRED

**Test Solution rule:** Local Date and Local Time use the source-defined representation when required  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.1|100|49|local-date-time`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-100

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1651|Merchants must send an expiration date value even for expired cards, for all authorizations.|BR-365-3|365|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1652|For Certegy transaction, information consists of 2-char State Code and 6-char birth date (MMDDYY).|BR-365-4|365|74% MEDIUM|

## Segment 100 - SEG100-R-057 - REVIEW_REQUIRED

**Test Solution rule:** eWIC Authorization Cancellation uses Prompt Code S086 and Segment 103  
**Class/severity:** compatibility / error  
**Canonical anchor:** `ATL105|2026-3|10.5.5|100|78|ewic-authorization-cancellation`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-100

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1236|Print Data Segment has a maximum length of 1,009 alphanumeric characters.|BR-249-3|249|85% HIGH|

## Segment 100 - SEG100-R-058 - REVIEW_REQUIRED

**Test Solution rule:** eWIC Balance Inquiry uses Prompt Code E086 and Segment 103  
**Class/severity:** compatibility / error  
**Canonical anchor:** `ATL105|2026-3|10.5.5|100|78|ewic-balance-inquiry`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-100

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1349|If Tax by Product Segment is used, Product Code Data Segment must also be present.|BR-282-1|282|90% HIGH|

## Segment 100 - SEG100-R-060 - CONFIRMED

**Test Solution rule:** eWIC Purchase Reversal/Void uses Prompt Code 8086  
**Class/severity:** lifecycle / error  
**Canonical anchor:** `ATL105|2026-3|10.5.5|100|78|ewic-purchase-reversal`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1717|Initiation Time is calculated by BUYPASS using system clock, merchant profile time zone, and daylight saving flag.|BR-380-2|380|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1718|If required by the issuer, Job Number is found in the Fleet Data Segment (Segment No. 101).|BR-380-3|380|79% MEDIUM|

## Segment 100 - SEG100-R-062 - REVIEW_REQUIRED

**Test Solution rule:** eWIC Voucher Clear uses Prompt Code 0086 with applicable voucher data  
**Class/severity:** lifecycle / error  
**Canonical anchor:** `ATL105|2026-3|10.5.5|100|78|ewic-voucher-clear`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-100

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:691|eWIC Authorization transaction is initiated by swiping card and entering PIN, then device sends request to BUYPASS.|BR-115-3|115|57% MEDIUM|
|REQ-SRC-ATL105-PDF-001:806|If loyalty card not available for Expiration date update, clerk enters street number and telephone number instead of account number.|BR-134-2|134|55% MEDIUM|
|REQ-SRC-ATL105-PDF-001:807|If loyalty card not available for Account inquiry, clerk enters street number and telephone number instead of account number.|BR-134-3|134|58% MEDIUM|
|REQ-SRC-ATL105-PDF-001:809|Reversal of coupon redeem requires Coupon ID, Coupon Amount, and approval number of previous transaction receipt.|BR-134-5|134|58% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1140|Merchants using Real Time Account Updater should refer to Card Discretionary Block Data Data Element 12 for additional info.|BR-221-2|221|51% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1198|Electronic Mail Data Segment has a maximum length of 232 alphanumeric characters.|BR-237-1|237|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1229|a field is not populated — still send the Field Separator.|BR-248-1|248|25% LOW|
|REQ-SRC-ATL105-PDF-001:1514|A maximum of ten products is allowed in the Adjusted Product Code segment.|BR-320-5|320|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1515|Total adjusted product amounts must equal sum of Fuel Purchase Amount, Nonfuel Amount, Tax Amount, and Cash Amount in Standard Message Data Segment.|BR-320-6|320|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1517|Discount, Tax, and Coupon amounts should not be included as separate product codes since accounted for in product amounts.|BR-320-8|320|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1518|All fuel merchants must send fuel and nonfuel product data if applicable.|BR-320-9|320|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1519|A unique Product Code must be sent for each type of fuel purchased.|BR-320-10|320|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1521|A Field Separator always follows Segment Type and Segment Length; sent even when field not populated.|BR-320-12|320|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1522|A Field Separator follows Adjusted Product Amount when it is the last element in the segment.|BR-320-13|320|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1744|For nonfuel purchase with discounts/coupons, Nonfuel Amount is net of discount/coupon subtracted from sum of Product Amount.|BR-386-5|386|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1969|When Variable Information Indicator = 072, Transaction Link Identifier data is included.|BR-423-13|423|84% HIGH|
|REQ-SRC-ATL105-PDF-001:2043|If MICR data > 50 bytes and prompt code is ECA/TeleCheck check service, refer to ECA/TeleCheck Data Segment (Segment 113).|BR-434-4|434|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2045|State Code is required when Driver's License (Element 123) is included in a check transaction.|BR-435-1|435|78% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2048|Check Number is required on all manually entered check transactions.|BR-436-1|436|84% HIGH|
|REQ-SRC-ATL105-PDF-001:2049|ECA/ TeleCheck Clerk ID valid values range from 1 to 999999.|BR-437-1|437|58% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2068|Earliest WIC Benefit Expiration Date format is CCYYMMDD, 8 bytes.|BR-447-2|447|18% LOW|
|REQ-SRC-ATL105-PDF-001:2069|If Item Action Code (Bit No. 8) is '00', product was approved, but maximum price was exceeded; Item Price contains that price.|BR-449-1|449|21% LOW|
|REQ-SRC-ATL105-PDF-001:2107|AMOUNT TYPE value 50 indicates HIP purchase/return amount (request).|BR-459-8|459|57% MEDIUM|

## Segment 103 - SEG103-R-001 - REVIEW_REQUIRED

**Test Solution rule:** Segment 103 is a Data Section 3 companion segment usable in any Data Section 3 field slot  
**Class/severity:** structure / error  
**Canonical anchor:** `ATL105|2026-3|11.1,12.4|103|null|section-3-companion-any-slot`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-103

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:660|Electronic vouchers must prompt for Account Number, Approval Number, and Voucher Number.|BR-112-1|112|76% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1144|Merchants should not send the Fleet segment (No. 101) and the Enhanced Fleet segment (No. 145) together.|BR-223-2|223|90% HIGH|

## Segment 103 - SEG103-R-003 - CONFIRMED

**Test Solution rule:** Segment Type is 103  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.4|103|85|segment-type`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1104|Number of Print Lines indicates how many times Terminal Display/Printer Message occurs in the response.|BR-207-4|207|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1188|Fields 15-17 (Category Total Data Section) are sent up to 18 times, once per card type, only when data occurs for that type.|BR-234-1|234|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1204|All fields in Check Data Segment are separated by Field Separators; unpopulated fields still send the separator.|BR-239-3|239|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1305|Token PAN Suffix is returned in the response only if supplied by the authorizer.|BR-264-3|264|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1370|The ETX (▲) after last product's tax data signifies end of the segment.|BR-284-7|284|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1785|Financial transaction device transmits a 4-character Prompt Code at initiation.|BR-394-3|394|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1786|Special transaction device transmits a 3-character Prompt Code (Card Type) at initiation.|BR-394-4|394|80% HIGH|

## Segment 103 - SEG103-R-004 - CONFIRMED

**Test Solution rule:** Segment Length is 3 or 4 digits; 4 digits is required for EBT-with-eWIC transactions  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.4|103|84|segment-length`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:943|Standard Message Data Segment is the only segment required for all financial transactions.|BR-164-4|164|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1067|No need to set any load flag on the terminal record; Moneris Key Load can be performed at any time.|BR-199-1|199|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1129|Error codes BLOCK NBR NOT NUMERIC or BLOCK NBR NOT 000-xxx indicate key load transaction failure.|BR-214-5|214|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1142|Local Date and Local Time is conditional, identifying date/time of preauthorized transaction.|BR-222-1|222|38% LOW|
|REQ-SRC-ATL105-PDF-001:1296|Use Card Labels (in parentheses) instead of field numbers to identify total buckets.|BR-261-4|261|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1433|Fields 1-2, 2-3, and 3-4 are separated by Field Separators; segment ends with a field separator.|BR-303-1|303|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1474|VAU006 indicates transaction is not a qualifying transaction type.|BR-313-8|313|57% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1759|Communications Test Requests: Number of Segments always followed by Element 120, Network Management Message.|BR-388-6|388|90% HIGH|

## Segment 103 - SEG103-R-005 - REVIEW_REQUIRED

**Test Solution rule:** Segment 103 maximum length is 3,334 alphanumeric characters  
**Class/severity:** serialization / error  
**Canonical anchor:** `ATL105|2026-3|12.4|103|84|segment-length-max-3334`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-103

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1143|The Fleet Data Segment is included in all fleet card transaction requests.|BR-223-1|223|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1760|TransArmor PKI Encryption and Tokenization: Used for Key and Key ID Load, followed by Segment 116, TransArmor Load Data Segment.|BR-388-7|388|90% HIGH|

## Segment 103 - SEG103-R-006 - REVIEW_REQUIRED

**Test Solution rule:** Field order matches Section 12.4 (Segment Type, Segment Length, Clerk ID, Voucher ID, WIC Discount Amount, WIC Product Data, EBT Program Data)  
**Class/severity:** serialization / error  
**Canonical anchor:** `ATL105|2026-3|12.4|103|null|segment-103-field-order`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-103

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:937|Data Section No. 1 always contains Message Format Version Identifier and Number of Segments elements.|BR-163-1|163|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1110|A Field Separator follows Field No. 12 in the CA Public Key File Load Request.|BR-209-2|209|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1521|A Field Separator always follows Segment Type and Segment Length; sent even when field not populated.|BR-320-12|320|70% MEDIUM|

## Segment 103 - SEG103-R-007 - REVIEW_REQUIRED

**Test Solution rule:** Request serialization: every Segment 103 field is separated by a Field Separator, including empty fields  
**Class/severity:** serialization / error  
**Canonical anchor:** `ATL105|2026-3|12.4|103|null|request-field-separator-required`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-103

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:950|Moneris Data (Request) Segment is required only for transactions destined for the Moneris authorizer.|BR-165-7|165|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1140|Merchants using Real Time Account Updater should refer to Card Discretionary Block Data Data Element 12 for additional info.|BR-221-2|221|51% MEDIUM|

## Segment 103 - SEG103-R-008 - REVIEW_REQUIRED

**Test Solution rule:** Response serialization: there is no Field Separator between Segment 103 fields  
**Class/severity:** serialization / error  
**Canonical anchor:** `ATL105|2026-3|12.4|103|null|response-no-field-separator`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-103

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1141|For DUKPT PIN encryption, Encrypted PIN Block Data is a 16- to 20-byte KSN plus a 16-byte PIN block.|BR-221-3|221|61% MEDIUM|

## Segment 103 - SEG103-R-009 - CONFIRMED

**Test Solution rule:** Clerk ID, when populated, is numeric with maximum length 10  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.4|103|18|clerk-id`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:669|EBT receipts must show Tender Type, Transaction Type, Clerk ID, Voucher Number, amounts, balances, approved/declined messages, HIP data.|BR-113-2|113|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:671|Receipt must indicate whether transaction is Food Stamp or Cash Benefit and the transaction type attempted.|BR-113-4|113|80% HIGH|
|REQ-SRC-ATL105-PDF-001:674|Food stamp dollar amount must be printed on Food Stamp transaction receipt.|BR-113-7|113|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1613|For Loyalty Totals Request with Update Code = 'T', Account Number field must contain 'TOTALS REQUEST'.|BR-359-2|359|76% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1614|For other loyalty advice transactions, Account Number must contain the actual Loyalty Account Number.|BR-359-3|359|63% MEDIUM|

## Segment 103 - SEG103-R-010 - CONFIRMED

**Test Solution rule:** Voucher ID, when populated, is numeric with maximum length 10  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.4|103|109|voucher-id`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:662|PAN printed on receipts must be truncated to last four digits, preceded by X's equal to truncated digit count.|BR-112-3|112|90% HIGH|
|REQ-SRC-ATL105-PDF-001:666|No signature line — printing an EBT receipt.|BR-113-1|113|28% LOW|
|REQ-SRC-ATL105-PDF-001:675|Cash benefit dollar amount(s) must be printed on Cash Benefit transaction receipt, broken down by purchase vs cash back with totals.|BR-113-8|113|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1837|Print Data Segment length is 001-1009.|BR-400-14|400|57% MEDIUM|

## Segment 103 - SEG103-R-011 - CONFIRMED

**Test Solution rule:** WIC Discount Amount uses the positional format Account Type(97) + Amount Type(52) + Currency Code + signed Amount  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|13.2|103|153|wic-discount-amount-format`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:2003|When Additional Information Indicator = 021, Re-Price Data Response Information is included.|BR-430-11|430|81% HIGH|
|REQ-SRC-ATL105-PDF-001:2004|When Additional Information Indicator = 022, CAVV Result Information is included.|BR-430-12|430|84% HIGH|
|REQ-SRC-ATL105-PDF-001:2694|mPOS devices (Phone/Tablet + Dongle) without PIN Pad should use PIN Entry Capability Mode code 3.|BR-632-7|632|21% LOW|

## Segment 103 - SEG103-R-012 - REVIEW_REQUIRED

**Test Solution rule:** WIC Discount Amount maximum length is 40 bytes, composed of one or more 20-byte positional blocks  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|13.2|103|153|wic-discount-amount-max-040`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-103

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:2695|Additional Information codes (001-038) are used in conjunction with Element 116 (Additional Information Indicator) and Element 118 (Additional Information).|BR-633-1|633|64% MEDIUM|

## Segment 103 - SEG103-R-013 - REVIEW_REQUIRED

**Test Solution rule:** WIC Product Data is bounded to 3,001 bytes and begins with a 4-digit Total Length subelement with maximum value 2997  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|13.2|103|154|wic-product-data-total-length`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-103

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:268|Data element 111 value '066' (Digital Commerce Data) triggers a processing rule for data element 113.|BR-12-2|12|54% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1226|ECA/TeleCheck Data Segment has a maximum length of 156 alphanumeric characters.|BR-246-2|246|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1761|Number of Segments value N indicates transaction has Segment 100 plus N-1 input segments listed in Chapter 12.|BR-388-8|388|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1762|Odometer element found in Fleet Data Segment if required by issuer.|BR-389-1|389|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1763|Odometer valid values range from 1 to 99999999.|BR-389-2|389|84% HIGH|
|REQ-SRC-ATL105-PDF-001:1765|If password entry is less than six characters, right-align entry using spaces.|BR-389-4|389|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1766|Default password is 123456.|BR-389-5|389|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1767|PC Duty Amount is used for Purchase Card Transaction Request.|BR-391-1|391|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1768|PC Freight Amount is used in Direct Marketing/Address Verification System Request.|BR-391-2|391|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1769|PC Tax Amount differs from Element No. 99, Tax Amount.|BR-391-3|391|71% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1770|Transaction dialing always begins with the primary Phone Number.|BR-392-1|392|64% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1771|Secondary Phone Number is used once attempts using the primary Phone Number are exhausted.|BR-392-2|392|64% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1772|A tertiary Phone Number is assigned to but not used by the device.|BR-392-3|392|64% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1774|Product Amount decimal point implied by optional Currency Code; default has two assumed decimal places.|BR-392-5|392|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1777|Product Code and related fields repeat up to 10 products in Data Segment 102 or 157.|BR-393-1|393|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1778|A unique Product Code must be sent for each type of fuel purchase.|BR-393-2|393|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1779|When device uses Dynamic Card Table, only product codes defined in the table are valid.|BR-393-3|393|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1780|In Proprietary Load Response with Prompt Code 904, Product Code positions 2/3 may contain wildcard '*' allowing any digit.|BR-393-4|393|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1781|In Proprietary Load Response (Prompt Code 904), first Product Code instance identifies discount-eligible product, second identifies discount product code for Segment 102.|BR-393-5|393|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1784|Prompt Code must be a valid Transaction Type code and/or Card Type code.|BR-394-2|394|85% HIGH|
|REQ-SRC-ATL105-PDF-001:2019|When Additional Information Indicator = 038, Transaction Link Identifier is included.|BR-431-9|431|84% HIGH|
|REQ-SRC-ATL105-PDF-001:2199|AEVV cardholder verification value has codes 9, A, B, C, D, U for various AEVV outcomes.|BR-480-4|480|41% LOW|

## Segment 103 - SEG103-R-014 - CONFIRMED

**Test Solution rule:** EBT Program Data is bounded to 267 bytes and begins with a 3-digit Total Length subelement with maximum value 264  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|13.2|103|164|ebt-program-data-total-length`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:2020|When Additional Information Indicator = 039, Transaction Link Action Indicator is included.|BR-431-10|431|84% HIGH|
|REQ-SRC-ATL105-PDF-001:2037|Value 5 indicates merchant only supports card balance receipt, not Partial Approval processing.|BR-433-4|433|81% HIGH|
|REQ-SRC-ATL105-PDF-001:2043|If MICR data > 50 bytes and prompt code is ECA/TeleCheck check service, refer to ECA/TeleCheck Data Segment (Segment 113).|BR-434-4|434|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2197|SPDH Header appears in both Moneris Key Load request and response.|BR-480-2|480|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2688|If Contactless MSR is attached with no chip capability, all transactions use PIN Entry Capability Mode code 8.|BR-632-1|632|21% LOW|
|REQ-SRC-ATL105-PDF-001:2690|If no Contactless MSR attached and PIN debit feature enabled, all transactions use PIN Entry Capability Mode code 1.|BR-632-3|632|21% LOW|
|REQ-SRC-ATL105-PDF-001:2691|If no Contactless MSR attached and PIN debit feature disabled, all transactions use PIN Entry Capability Mode code 2.|BR-632-4|632|21% LOW|

## Segment 103 - SEG103-R-015 - CONFIRMED

**Test Solution rule:** EBT Program Data contains 1 to 6 Program Data subelements, each bounded to 44 bytes  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|13.2|103|164|ebt-program-data-subelement-count`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:2038|Support for Partial Authorization is mandatory for all card brands; must be populated correctly for card present transactions.|BR-433-5|433|90% HIGH|
|REQ-SRC-ATL105-PDF-001:2039|For Amex transactions with zero amount and values 1 or 5, merchant will not receive card balance in response.|BR-433-6|433|76% MEDIUM|

## Segment 103 - SEG103-R-016 - CONFIRMED

**Test Solution rule:** EBT Program Data subelement TAG must be one of the documented values (50, IT for requests; 51, 52 for responses)  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|13.2|103|164|ebt-program-data-tag-enumeration`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:712|HIP receipts must follow receipt requirements listed in section 10.1.7.|BR-120-3|120|57% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1764|Password required for totals request or electronic mail request.|BR-389-3|389|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1773|Phone Number data consists of all data up to the following C (Log-on Indicator) or A (Delimiter) character.|BR-392-4|392|54% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1775|Product Amount and related elements repeat for up to a maximum of 10 products in Segment 102 or 157.|BR-392-6|392|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1776|Valid Product Amount values range from 1 to 999999999999.|BR-392-7|392|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1782|Prompt Code (4 chars) identifies Transaction Type (1 char) and Card Type (3 chars) for financial transactions.|BR-393-6|393|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1783|Special transaction Prompt Code is 3-character Card Type identifying requested special data.|BR-394-1|394|80% HIGH|
|REQ-SRC-ATL105-PDF-001:2045|State Code is required when Driver's License (Element 123) is included in a check transaction.|BR-435-1|435|78% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2051|Loyalty Program ID is required on all loyalty card transactions.|BR-439-2|439|61% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2052|Loyalty Account Number is required on all loyalty card transactions.|BR-440-1|440|78% MEDIUM|

## Segment 103 - SEG103-R-017 - CONFIRMED

**Test Solution rule:** EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE fixed value 98 (not required when TAG is IT)  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|13.2|103|164|ebt-program-data-account-type-98`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1086|Purchase Card Data Segment sent only on transactions requiring purchase card data.|BR-203-5|203|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1103|Terminal Display/Printer Message data element can be repeated up to a maximum of three times.|BR-207-3|207|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1109|Field Separators are present between Field Nos. 1-2, 2-3, 3-4, 4-5, 5-6, but not between 6-11.|BR-209-1|209|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1145|All fields in Fleet Data Segment are separated by Field Separators; unpopulated fields still send the separator.|BR-223-3|223|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1149|Valid 3-byte Fleet Tag codes include DLS (an3), DLN (an22), PON (an31), INV (an31), TRP (an15), UNT (an31).|BR-224-3|224|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1155|Segment 102 and segment 157 are mutually exclusive; sending both causes decline.|BR-226-2|226|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1162|A unique Product Code must be sent for each type of fuel purchase.|BR-226-9|226|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1167|When a field is not populated, still send the Field Separator or Field Delimiter.|BR-226-14|226|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1181|a field is not populated — still send the Field Separator.|BR-232-1|232|25% LOW|
|REQ-SRC-ATL105-PDF-001:1194|Update Code field presence is Conditional depending on loyalty function performed.|BR-235-4|235|41% LOW|
|REQ-SRC-ATL105-PDF-001:1210|Check Number is required for manually keyed check data.|BR-241-1|241|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1249|Card Table Load Version comes from Host and is echoed back in each subsequent block request.|BR-252-3|252|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1262|Prompt Code 903 is used for Site Configuration Data loads sent in a Proprietary Data Load request.|BR-255-3|255|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1267|Response Code T means Approved-Proprietary load data, no more data pending.|BR-255-8|255|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1291|Segment Type fixed value is 119.|BR-260-7|260|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1297|Card categories marked with * are non-financial; their dollar amounts are not included in the Grand Total.|BR-262-1|262|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1299|Print Data 2 Segment always appears at the end of a Financial Transaction response.|BR-263-2|263|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1304|Segment 123 required for transactions including MasterCard Token, DSRP, or Visa TAVV data.|BR-264-2|264|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1307|When first 28 bytes of SafeKey Data value is not applicable, it should be space filled.|BR-265-1|265|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1312|No Field Separators present between elements within EMV Additional Information Section.|BR-267-2|267|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1405|Cash Advance Limit amount must include a decimal point.|BR-294-6|294|21% LOW|
|REQ-SRC-ATL105-PDF-001:1413|Sum of all products in a product category should not exceed the stated Amount.|BR-296-2|296|15% LOW|
|REQ-SRC-ATL105-PDF-001:1416|Product Data is repeated for as many products as needed, separated by '\|' and ending with '\|'.|BR-296-5|296|21% LOW|
|REQ-SRC-ATL105-PDF-001:1421|Edit mask '?' indicates re-prompt using previously received formatting.|BR-297-5|297|15% LOW|
|REQ-SRC-ATL105-PDF-001:1481|VAU013 indicates expiry date in authorization request is later than VAU data.|BR-313-15|313|57% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2021|When Additional Information Indicator = 040, Fraud Score is included.|BR-431-11|431|84% HIGH|
|REQ-SRC-ATL105-PDF-001:2042|MICR Data is also included in Account Number (Element 2) of the Standard Message Data Segment (Segment 100).|BR-434-3|434|76% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2046|Check Type is required on all manually entered check transactions.|BR-435-2|435|84% HIGH|
|REQ-SRC-ATL105-PDF-001:2047|Check Type valid codes are P (Personal) and C (Company).|BR-435-3|435|84% HIGH|
|REQ-SRC-ATL105-PDF-001:2048|Check Number is required on all manually entered check transactions.|BR-436-1|436|84% HIGH|
|REQ-SRC-ATL105-PDF-001:2049|ECA/ TeleCheck Clerk ID valid values range from 1 to 999999.|BR-437-1|437|58% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2689|For EMV Contact and Contactless, valid PIN Entry Capability Mode code is 1, not 8; code 8 is reserved for Contactless MSR only.|BR-632-2|632|21% LOW|
|REQ-SRC-ATL105-PDF-001:2692|PIN entry capability mode code 3 applies only to MasterCard Mobile POS transactions.|BR-632-5|632|21% LOW|
|REQ-SRC-ATL105-PDF-001:2693|mPOS devices (Phone/Tablet + Dongle) with PIN Pad should use PIN Entry Capability Mode code 1.|BR-632-6|632|21% LOW|

## Segment 103 - SEG103-R-020 - REVIEW_REQUIRED

**Test Solution rule:** eWIC does not support Return transactions (absolute prohibition, not a code lookup)  
**Class/severity:** lifecycle / error  
**Canonical anchor:** `ATL105|2026-3|10.5.5.1|103|null|ewic-no-return`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-103

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:654|If no expiration date is provided for manually entered EBT account number, use 1249.|BR-110-4|110|81% HIGH|

## Segment 103 - SEG103-R-022 - CONFIRMED

**Test Solution rule:** EBT Program Data subelement full positional layout: TAG+LEN+ACCOUNT TYPE(98)+AMOUNT TYPE(=TAG)+CURRENCY CODE(840)+AMOUNT DESCRIPTOR(0/C/D)+DETAIL(12-digit amount) for TAG 50/51/52; TAG+LEN+ADDRESS(28)+ZIP(9) for TAG IT  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|13.2,Appendix M|103|164|ebt-program-data-full-layout`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:711|Incentive balances are reset at the beginning of the fiscal month.|BR-120-2|120|57% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2044|Driver's License is required on all manually entered check transactions.|BR-434-5|434|81% HIGH|
|REQ-SRC-ATL105-PDF-001:2050|Extended MICR Data must be populated in addition to MICR Data when raw MICR data exceeds the length.|BR-439-1|439|58% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2696|Balance Information Table ID must have fixed value 001.|BR-635-1|635|21% LOW|

## Segment 103 - SEG103-R-023 - CONFIRMED

**Test Solution rule:** WIC Product Data subelement catalog: EF (Earliest WIC Benefit Expiration Date, 8 bytes), EA (WIC Prescription Balance Information, up to 14 bytes), PS (WIC UPC Exception/Denial or Purchase Information, up to 47 bytes)  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|13.2|103|154|wic-product-data-subelement-catalog`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:708|Credit card receipt requirements apply to eWIC receipts except for Approved/Declined message.|BR-119-3|119|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1672|Current Date month value must be between 01 and 12.|BR-369-3|369|64% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2005|When Additional Information Indicator = 023, MCX Reference Number Information is included.|BR-430-13|430|84% HIGH|
|REQ-SRC-ATL105-PDF-001:2006|When Additional Information Indicator = 024, Carwash Indicator Information is included.|BR-430-14|430|84% HIGH|
|REQ-SRC-ATL105-PDF-001:2007|When Additional Information Indicator = 025, Language Indicator Information is included.|BR-430-15|430|84% HIGH|
|REQ-SRC-ATL105-PDF-001:2008|When Additional Information Indicator = 026, DST Response Information is included.|BR-430-16|430|84% HIGH|
|REQ-SRC-ATL105-PDF-001:2009|When Additional Information Indicator = 027, Universal Unique Identifier is included.|BR-430-17|430|84% HIGH|
|REQ-SRC-ATL105-PDF-001:2010|When Additional Information Indicator = 028, Transaction Identifier Information is included.|BR-430-18|430|84% HIGH|
|REQ-SRC-ATL105-PDF-001:2012|When Additional Information Indicator = 030, Merchant Advice code is included.|BR-431-2|431|84% HIGH|

## Segment 103 - SEG103-R-024 - REVIEW_REQUIRED

**Test Solution rule:** Segment 103 applicability matrix: REQUIRED for Food Stamp Electronic Voucher (Voucher ID) and eWIC Purchase Completion/Voucher Clear (WIC data); OPTIONAL for all other EBT/eWIC transaction types listed in Section 10.5.3; PROHIBITED for eWIC Return  
**Class/severity:** applicability / error  
**Canonical anchor:** `ATL105|2026-3|10.5.2,10.5.3,10.5.4.8,10.5.5,10.5.6|103|null|segment-103-applicability-matrix`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-103

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:655|There is no stand-in mode for EBT transactions currently; USDA guidelines do not allow off-line processing of EBT transactions.|BR-110-5|110|84% HIGH|
|REQ-SRC-ATL105-PDF-001:658|The balance field must not print on the receipt for stored-and-forward EBT transactions; do not print zeros.|BR-110-8|110|85% HIGH|
|REQ-SRC-ATL105-PDF-001:661|Stand-alone equipment electronic voucher transaction must allow manual entry of Account Number and Expiration Date.|BR-112-2|112|71% MEDIUM|
|REQ-SRC-ATL105-PDF-001:667|The food stamp dollar amount must be printed on a Food Stamp transaction receipt.|BR-113-2|113|28% LOW|

## Segment 104 - SEG104-R-004 - CONFIRMED

**Test Solution rule:** Segment Type is 104.  
**Class/severity:**  /   
**Canonical anchor:** `ATL105|2026-3|12.5|104|85|segment-104-type-fixed-value`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:254|Enhanced Fleet Data must be alphanumeric and must not exceed 999 bytes.|ENT-ELEM-239|490|50% MEDIUM|
|REQ-SRC-ATL105-PDF-001:923|EMV data is not required on Reversal transactions.|BR-158-1|158|85% HIGH|
|REQ-SRC-ATL105-PDF-001:930|If card is not chip or device not chip capable, and card is swiped, send MSR Entry Mode of '90' or spec equivalent.|BR-159-4|159|90% HIGH|
|REQ-SRC-ATL105-PDF-001:936|R indicates required, O indicates optional, C indicates conditional entry for a data element.|BR-162-4|162|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1044|Information Byte fixed value is '?' identifying a Download Request.|BR-192-3|192|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1054|Software Dial Load Data Segment is sent only on a Software Load Response.|BR-195-2|195|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1072|Key Data section repeats up to 3 times (max 48 bytes total); presence based on Key Indicators field.|BR-200-1|200|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1089|EMV Request Data Segment is required for all EMV card transactions and is the only segment required for all EMV financial transactions.|BR-203-8|203|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1095|Incomm Market Basket Data (Response) Segment returned when Request segment was included in the request.|BR-204-4|204|57% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1131|Standard Message Data Segment is the only required data segment on all Financial Transaction Request messages.|BR-220-2|220|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1133|All fields separated by Field Separator; when a field is not populated, still send the Field Separator.|BR-220-4|220|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1135|Segment Length includes Segment Type's length and Field Separators.|BR-220-6|220|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1141|For DUKPT PIN encryption, Encrypted PIN Block Data is a 16- to 20-byte KSN plus a 16-byte PIN block.|BR-221-3|221|61% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1148|Each Fleet Tag has a 3-byte fixed-length tag portion and up to 31 bytes of data.|BR-224-2|224|21% LOW|
|REQ-SRC-ATL105-PDF-001:1153|a field is not populated — still send the Product Data Field Delimiter.|BR-226-3|226|28% LOW|
|REQ-SRC-ATL105-PDF-001:1167|When a field is not populated, still send the Field Separator or Field Delimiter.|BR-226-14|226|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1174|A Field Separator follows Product Amount if it is the last element in the segment.|BR-228-2|228|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1180|All fields are separated by Field Separators; unpopulated fields still send Field Separator.|BR-230-2|230|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1190|a field is not populated — still send the Field Separator.|BR-235-1|235|25% LOW|
|REQ-SRC-ATL105-PDF-001:1196|Expiration Date is reserved for future use.|BR-236-1|236|64% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1235|Print Data Segment always appears at the end of a Financial Transaction response.|BR-249-2|249|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1248|Prompt Code, Pending valid values are 0901, 0902, 0904, 0981.|BR-252-2|252|84% HIGH|
|REQ-SRC-ATL105-PDF-001:1253|Device Card Table Version consists of seven version values, each 5 characters, strung together.|BR-252-7|252|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1277|Product Code 991 indicates an administrative amount, used in a pre-pay environment when price is adjusted to include discount amount.|BR-257-5|257|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1283|Fields are not separated by Field Separators; unpopulated fields are immediately followed by the next field.|BR-259-3|259|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1285|Segment 119 always appears in Field No. 3 in Data Section No. 3.|BR-260-1|260|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1290|Prompt Code fixed value is 990 identifying format type for requested totals.|BR-260-6|260|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1293|Field Nos. 17-19 are sent up to a maximum of 20 times, once for each card type, in order shown.|BR-261-1|261|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1298|Print Data 2 Segment is only sent on Financial Transaction Response messages requiring large amounts of print data.|BR-263-1|263|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1356|A Field Separator always follows Segment Type and Segment Length, and the last Tax Amount per product.|BR-282-8|282|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1391|Prompt Tokens differ by authorizer; different authorizers support different prompts.|BR-289-3|289|21% LOW|
|REQ-SRC-ATL105-PDF-001:1399|Prompt Code 999 indicates no prompts issued.|BR-293-2|293|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1402|Table Length for Payee Name has a maximum variable value of 017.|BR-294-3|294|21% LOW|
|REQ-SRC-ATL105-PDF-001:1407|Flags in Table Data must be separated by a comma; if a flag is absent, it can be skipped using just the separator.|BR-295-2|295|57% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1419|Product Data repeated for as many products as needed, separated by '\|' and ending with '\|'.|BR-297-3|297|57% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1467|Field is included in response only when merchant sent Account Updater Request Indicator with value 'Y' or 'I' in request.|BR-313-1|313|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1658|Effective May 1, 2025, expiration date is optional for Accel/STAR COF transactions (bill payment, recurring, installment).|BR-365-10|365|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1696|PIN data must be customer-keyed and encrypted using DUKPT; if absent from request, insert a Field Separator.|BR-375-2|375|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1697|For EMV offline PIN validated debit, PIN block must contain a PIN or all F's, else transaction declines as invalid transaction.|BR-375-3|375|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1771|Secondary Phone Number is used once attempts using the primary Phone Number are exhausted.|BR-392-2|392|64% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1772|A tertiary Phone Number is assigned to but not used by the device.|BR-392-3|392|64% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1781|In Proprietary Load Response (Prompt Code 904), first Product Code instance identifies discount-eligible product, second identifies discount product code for Segment 102.|BR-393-5|393|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1799|Quantity valid codes/values are 00000000, 01-399999999.|BR-395-7|395|85% HIGH|

## Segment 104 - SEG104-R-005 - CONFIRMED

**Test Solution rule:** Segment Length is 3 digits representing the content length.  
**Class/severity:**  /   
**Canonical anchor:** `ATL105|2026-3|12.5|104|84|segment-104-length-format`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1090|Moneris Data (Request) Segment required only for transactions destined for the Moneris authorizer.|BR-203-9|203|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1096|All EMV Financial Transaction Requests contain one or more of the listed data segments in Field Nos. 4-8.|BR-204-5|204|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1115|For EMV, the first two characters (Device Type) of Terminal Identifier must be '+*' regardless of actual device type.|BR-211-3|211|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1128|RQST/CURRENT HASH MATCH code indicates key update is not required.|BR-214-4|214|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1134|Segment Type field has fixed value 100 for Standard Message Data Segment.|BR-220-5|220|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1212|Value 'Y' in Alternate MICR IND indicates Alternate MICR format is being sent from the terminal.|BR-242-2|242|18% LOW|
|REQ-SRC-ATL105-PDF-001:1282|Fuel Volume Data field length is determined by segment length in field 2, up to max 3,600.|BR-259-2|259|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1291|Segment Type fixed value is 119.|BR-260-7|260|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1460|Account Updater Expiration Date is applicable only to Visa and MasterCard.|BR-311-5|311|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1507|Connector type field has maximum length of 3 characters.|BR-319-1|319|51% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1745|For fuel and nonfuel amounts together with discounts/coupons, discounts/coupons are first applied to the nonfuel amount.|BR-386-6|386|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1746|Nonfuel Amount valid values are 1-99999999.|BR-386-7|386|84% HIGH|
|REQ-SRC-ATL105-PDF-001:1747|Number of Card Types valid values are 01-99.|BR-386-8|386|84% HIGH|
|REQ-SRC-ATL105-PDF-001:1748|Number of Print Lines does not include Data Element 168, Count of Receipt Text Line.|BR-387-1|387|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1749|Data Element 168 count does not reflect total from Number of Print Lines.|BR-387-2|387|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1750|Number of Print Lines information comes from BUYPASS.|BR-387-3|387|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1751|Number of Print Lines valid values are 1, 2, or 3.|BR-387-4|387|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1752|Number of Products must precede single digits (1-9) with a zero (01-09).|BR-387-5|387|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1753|Number of Products valid values range from 01 to 10.|BR-387-6|387|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1754|Financial Transaction Requests: Number of Segments followed by Data Segment 100, plus optional input segments from Chapter 12.|BR-388-1|388|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1755|Loyalty Card Transaction Requests: Number of Segments followed by Segment 100, Segment 108, and Segment 114.|BR-388-2|388|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1756|ECA/TeleCheck Service Transaction Requests: Number of Segments followed by Segment 100, Segment 110, and Segment 111.|BR-388-3|388|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1757|Totals Requests: Number of Segments always followed by Segment 105, Totals Data Segment.|BR-388-4|388|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1758|Electronic Mail Requests: Number of Segments always followed by Segment 109, Electronic Mail Data Segment.|BR-388-5|388|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1759|Communications Test Requests: Number of Segments always followed by Element 120, Network Management Message.|BR-388-6|388|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1760|TransArmor PKI Encryption and Tokenization: Used for Key and Key ID Load, followed by Segment 116, TransArmor Load Data Segment.|BR-388-7|388|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1761|Number of Segments value N indicates transaction has Segment 100 plus N-1 input segments listed in Chapter 12.|BR-388-8|388|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1762|Odometer element found in Fleet Data Segment if required by issuer.|BR-389-1|389|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1763|Odometer valid values range from 1 to 99999999.|BR-389-2|389|84% HIGH|
|REQ-SRC-ATL105-PDF-001:1764|Password required for totals request or electronic mail request.|BR-389-3|389|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1765|If password entry is less than six characters, right-align entry using spaces.|BR-389-4|389|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1766|Default password is 123456.|BR-389-5|389|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1767|PC Duty Amount is used for Purchase Card Transaction Request.|BR-391-1|391|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1768|PC Freight Amount is used in Direct Marketing/Address Verification System Request.|BR-391-2|391|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1769|PC Tax Amount differs from Element No. 99, Tax Amount.|BR-391-3|391|71% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1770|Transaction dialing always begins with the primary Phone Number.|BR-392-1|392|64% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2183|Safekey Data positions 1-2 must be fixed value 'SK' indicating SafeKey cryptogram transaction.|BR-478-3|478|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2185|Safekey Data appears in Data Segment No. 123 (NFC Payment Tokenization Data Segment).|BR-478-5|478|74% MEDIUM|

## Segment 104 - SEG104-R-006 - REVIEW_REQUIRED

**Test Solution rule:** Purchase Card Data Segment max length is 86 alphanumeric characters.  
**Class/severity:**  /   
**Canonical anchor:** `ATL105|2026-3|12.5|104|null|segment-104-max-length`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-104

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1132|Trailing fields not needed in a data segment should not be transmitted; only populated leading fields sent.|BR-220-3|220|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1695|Encrypted PIN Block Data is required for debit and EBT, but not required for a completion.|BR-375-1|375|76% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1698|If PIN block composition is invalid or device is out-of-sync with BUYPASS, the transaction is declined.|BR-375-4|375|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1699|End-of-Data Indicator is used in Merchant, Dial String, Date and Time, Software Dial Load, and Software IP Load data segments.|BR-375-5|375|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1700|End-of-Load Indicator is used in the Table Load Response.|BR-376-1|376|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1784|Prompt Code must be a valid Transaction Type code and/or Card Type code.|BR-394-2|394|85% HIGH|

## Segment 105 - SEG105-R-001 - CONFIRMED

**Test Solution rule:** Segment Type is 105  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|Totals Request|105|85|segment-type`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:987|Totals Request Data Section No. 3 contains the Totals Data Segment (Data Segment No. 105).|BR-175-3|175|85% HIGH|

## Segment 105 - SEG105-R-002 - CONFIRMED

**Test Solution rule:** Segment Length includes Segment Type and field separators  
**Class/severity:** serialization / error  
**Canonical anchor:** `ATL105|2026-3|Totals Request|105|84|segment-length`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1181|a field is not populated — still send the Field Separator.|BR-232-1|232|25% LOW|
|REQ-SRC-ATL105-PDF-001:1183|Field Nos. 1-14 are separated by Field Separators, including one after Field No. 14.|BR-232-2|232|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1184|When a field is not populated, still send the Field Separator.|BR-232-3|232|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1185|Field Nos. 15-17 are not separated by Field Separators.|BR-232-4|232|90% HIGH|

## Segment 105 - SEG105-R-003 - REVIEW_REQUIRED

**Test Solution rule:** Information Byte is required  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|Totals Request|105|44|information-byte`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-105

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1829|Totals Data Segment length is 001-409.|BR-400-6|400|57% MEDIUM|

## Segment 105 - SEG105-R-008 - CONFIRMED

**Test Solution rule:** Totals Date uses a permitted source-defined request code  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|Totals Request|105|105|totals-date`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:413|At completion of business day, terminal sends Totals Request (222222 - Read and Cut Settlement) after last batch.|BR-62-9|62|80% HIGH|

## Segment 105 - SEG105-R-010 - REVIEW_REQUIRED

**Test Solution rule:** Hardware Version is required  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|Totals Request|105|43|hardware-version`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-105

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1182|Totals Data Segment has a maximum length of 409 alphanumeric characters.|BR-232-1|232|90% HIGH|

## Segment 105 - SEG105-R-013 - REVIEW_REQUIRED

**Test Solution rule:** Sequence Number is required  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|Totals Request|105|86|sequence-number`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-105

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1757|Totals Requests: Number of Segments always followed by Segment 105, Totals Data Segment.|BR-388-4|388|90% HIGH|

## Segment 105 - SEG105-R-015 - REVIEW_REQUIRED

**Test Solution rule:** Grand Total, Card Label, Card Type Total Count, and Card Type Total Amount are required  
**Class/severity:** structure / error  
**Canonical anchor:** `ATL105|2026-3|Totals Request|105|null|totals-aggregate-fields`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-105

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:705|eWIC Dollar Amount and eWIC Count fields are added to Totals Response for eWIC processing.|BR-118-4|118|57% MEDIUM|

## Segment 105 - SEG105-R-016 - REVIEW_REQUIRED

**Test Solution rule:** Request and response correlation follows approved totals lifecycle policy  
**Class/severity:** lifecycle / review  
**Canonical anchor:** `ATL105|2026-3|Totals Request|105|86|request-response-correlation`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-105

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:407|A Totals Request (999999 - Shift Read and Reset) follows successful completion of a batch upload.|BR-62-3|62|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1734|For Totals Request messages, Element 63 (Number of Segments) and Segment 105 (Totals Data Segment) always follow Element 55.|BR-384-5|384|85% HIGH|

## Segment 105 - SEG105-R-017 - REVIEW_REQUIRED

**Test Solution rule:** Segment 119 use requires an approved proprietary-load selection rule  
**Class/severity:** compatibility / review  
**Canonical anchor:** `ATL105|2026-3|Totals Request|119|null|totals-load-alternative`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-105

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:990|Totals Data Segment sent only on transactions requiring totals data.|BR-176-3|176|85% HIGH|

## Segment 108 - SEG108-R-001 - CONFIRMED

**Test Solution rule:** Segment 108 belongs exclusively to the Loyalty Card Transaction Request; it is not a Financial Transaction Request companion  
**Class/severity:** structure / error  
**Canonical anchor:** `ATL105|2026-3|11.2.1,13.2|108|63|loyalty-transaction-exclusive`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:804|Loyalty card information can be included in Purchase/Capture, Purchase Reversal, and Time-out Reversal transactions.|BR-133-6|133|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:812|If loyalty card not available for Reversal of points redeemed, clerk enters street number and telephone number instead of account number.|BR-134-8|134|58% MEDIUM|
|REQ-SRC-ATL105-PDF-001:971|Loyalty Card Transaction Request Data Section 3 may contain none, one, or more of Loyalty Card Data Segment or SKU Data Segment.|BR-171-3|171|64% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1197|Payment Tender Type used to determine whether transaction is a multiple-card transaction involving a loyalty card.|BR-236-2|236|64% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1735|For Loyalty Card Request messages, Element 63 (Number of Segments) and Segment 108 (Loyalty Card Data Segment) always follow Element 55.|BR-384-6|384|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1755|Loyalty Card Transaction Requests: Number of Segments followed by Segment 100, Segment 108, and Segment 114.|BR-388-2|388|90% HIGH|

## Segment 108 - SEG108-R-002 - REVIEW_REQUIRED

**Test Solution rule:** Segment 108 is required in every Loyalty Card Transaction Request  
**Class/severity:** applicability / error  
**Canonical anchor:** `ATL105|2026-3|11.2.1|108|null|loyalty-request-required`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-108

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:811|Reversal of points redeemed requires point amount and approval number of previous transaction receipt.|BR-134-7|134|64% MEDIUM|
|REQ-SRC-ATL105-PDF-001:813|Add account transaction requires clerk to swipe new loyalty card and enter customer's street number and telephone number.|BR-134-9|134|54% MEDIUM|
|REQ-SRC-ATL105-PDF-001:973|Loyalty Card Data Segment is required for all loyalty card transactions.|BR-172-2|172|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1340|Segment Type field must have fixed value 139 for Moneris Day End Batch Balance Request Segment.|BR-276-1|276|90% HIGH|

## Segment 108 - SEG108-R-003 - REVIEW_REQUIRED

**Test Solution rule:** Segment Type is 108  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.7|108|85|segment-type`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-108

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1194|Update Code field presence is Conditional depending on loyalty function performed.|BR-235-4|235|41% LOW|
|REQ-SRC-ATL105-PDF-001:1224|Segment Type field has fixed value 112 for Additional Information Data Segment.|BR-245-2|245|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1238|There is a Field Separator between Field Nos. 1 and 2 and between Field Nos. 2 and 3.|BR-249-5|249|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1246|Segment Type fixed value is 118 for the Proprietary Data Load Segment.|BR-251-7|251|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1291|Segment Type fixed value is 119.|BR-260-7|260|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1330|Segment Type field fixed value is 134.|BR-273-3|273|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1343|Segment Type field 1 must have fixed value 140.|BR-277-2|277|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1409|Segment Type field has fixed value 146 for Enhanced Fleet Response Segment.|BR-295-4|295|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1848|Segment Type is required for the TransArmor PKI Encryption and Tokenization Load Request.|BR-401-1|401|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1849|Segment Type value 106 and 107 are reserved for proprietary use.|BR-401-2|401|90% HIGH|

## Segment 108 - SEG108-R-004 - REVIEW_REQUIRED

**Test Solution rule:** Segment Length is 3 digits representing the segment content length  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.7|108|84|segment-length`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-108

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1134|Segment Type field has fixed value 100 for Standard Message Data Segment.|BR-220-5|220|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1178|Segment Length should be 4 for EBT with eWIC data transactions.|BR-229-3|229|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1521|A Field Separator always follows Segment Type and Segment Length; sent even when field not populated.|BR-320-12|320|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1821|Segment Length has four digits only for segments 103, 114, 115, 118, 120, 130, and 131.|BR-399-1|399|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1822|Segment Length cannot have a length of 4 digits when sending any segment other than the listed seven.|BR-399-2|399|90% HIGH|

## Segment 108 - SEG108-R-005 - CONFIRMED

**Test Solution rule:** Segment 108 maximum length is 142 alphanumeric characters  
**Class/severity:** serialization / error  
**Canonical anchor:** `ATL105|2026-3|12.7|108|84|segment-length-max-142`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1165|A Field Separator always follows Segment Type and Segment Length; Field Separator follows Product Amount when it's the last element.|BR-226-12|226|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1335|Field Nos 1&2 and 2&3 are separated by Field Separators; segment should end with a field separator.|BR-274-1|274|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1830|Loyalty Card Data Segment length is 001-142.|BR-400-7|400|57% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2319|AVS Information Table Data has max length 29, first 9 characters are ZIP or ZIP+4.|BR-547-1|547|18% LOW|

## Segment 108 - SEG108-R-006 - REVIEW_REQUIRED

**Test Solution rule:** Field order matches Section 12.7 (Segment Type, Segment Length, Loyalty Program ID, Loyalty Account Number, Points to Redeem, Coupon ID, Coupon Amount, Update Code, Street Address, Phone Number Loyalty, Expiration Date, Payment Tender Type, Loyalty Track 2 Data, Loyalty Information Version, Unit of Work)  
**Class/severity:** serialization / error  
**Canonical anchor:** `ATL105|2026-3|12.7|108|null|segment-108-field-order`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-108

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1337|Segment Type field (Element 85) has fixed value 135 for Moneris Data Segment (Request).|BR-274-3|274|90% HIGH|

## Segment 108 - SEG108-R-007 - REVIEW_REQUIRED

**Test Solution rule:** All fields are separated by Field Separators, including a separator following the last field; empty fields still send the separator  
**Class/severity:** serialization / error  
**Canonical anchor:** `ATL105|2026-3|12.7|108|null|field-separator-required`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-108

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:523|Account Number must be truncated to last four digits on all transaction receipts.|BR-90-1|90|84% HIGH|
|REQ-SRC-ATL105-PDF-001:545|Account Number and Expiration Date must be separated by an '=' sign rather than a Field Separator.|BR-92-9|92|76% MEDIUM|
|REQ-SRC-ATL105-PDF-001:609|Account Number and Expiration Date must be separated by '=' sign rather than a Field Separator.|BR-103-9|103|76% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1135|Segment Length includes Segment Type's length and Field Separators.|BR-220-6|220|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1191|All fields separated by Field Separators; Field Separator follows Field No. 13 even if unpopulated.|BR-235-1|235|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1215|A Field Separator exists between Field Nos. 1 and 2 and between Field No. 2 and Variable Information Section.|BR-243-3|243|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1301|A Field Separator exists between Field Nos. 1 and 2 and between Field Nos. 2 and 3.|BR-263-4|263|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1356|A Field Separator always follows Segment Type and Segment Length, and the last Tax Amount per product.|BR-282-8|282|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1433|Fields 1-2, 2-3, and 3-4 are separated by Field Separators; segment ends with a field separator.|BR-303-1|303|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1435|Segment Type field has fixed value 149 for Fuel Price Update Request Segment.|BR-303-3|303|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1437|Segment Length includes Segment Type's length and Field Separators.|BR-304-2|304|84% HIGH|
|REQ-SRC-ATL105-PDF-001:1823|Segment Length is calculated using lengths of all data elements plus Field Separators/Product Data Field Delimiters in the segment.|BR-399-3|399|80% HIGH|
|REQ-SRC-ATL105-PDF-001:2251|Segment Length value (078) identifies the length in bytes of the Standard Message Data Segment, including field separators.|BR-499-1|499|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2263|Segment Length field identifies the length (017 bytes) of the Variable Information Data Segment, including Field Separators.|BR-503-3|503|70% MEDIUM|

## Segment 108 - SEG108-R-008 - CONFIRMED

**Test Solution rule:** Loyalty Program ID, required, is numeric with maximum length 6  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.7|108|138|loyalty-program-id`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:2051|Loyalty Program ID is required on all loyalty card transactions.|BR-439-2|439|61% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2731|Loyalty Information Unit of Work maximum length is 19 numeric.|BR-647-4|647|41% LOW|

## Segment 108 - SEG108-R-009 - CONFIRMED

**Test Solution rule:** Loyalty Account Number, when populated, is numeric with maximum length 24  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.7|108|139|loyalty-account-number`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:521|Receipts must include account number, expiration date, transaction date/time, sequence number, product, balance, approved/declined message, signature line.|BR-89-13|89|51% MEDIUM|
|REQ-SRC-ATL105-PDF-001:546|Host message should contain Account Number, followed by '=' delimiter, followed by 4-digit Expiration Date (YYMM).|BR-92-10|92|71% MEDIUM|
|REQ-SRC-ATL105-PDF-001:802|When no expiration date is present, device sends default value of 1249 (MMYY).|BR-133-4|133|84% HIGH|
|REQ-SRC-ATL105-PDF-001:803|When loyalty card is not present, Street Address and Loyalty Phone Number are keyed in lieu of Loyalty Account Number.|BR-133-5|133|72% MEDIUM|
|REQ-SRC-ATL105-PDF-001:805|If loyalty card not available for Coupon redemption, clerk enters street number and telephone number instead of Loyalty Account Number.|BR-134-1|134|58% MEDIUM|
|REQ-SRC-ATL105-PDF-001:815|If loyalty card is not available, clerk enters street number and telephone number instead of Loyalty Account Number.|BR-135-1|135|58% MEDIUM|
|REQ-SRC-ATL105-PDF-001:816|Account update transaction requires clerk to enter both new street number and new telephone number.|BR-135-2|135|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:921|If the account number is keyed, device prompts for Expiration Date (MMYY) and other optional/conditional data.|BR-157-8|157|71% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1193|Loyalty Account Number is conditional (C) rather than always required.|BR-235-3|235|48% LOW|
|REQ-SRC-ATL105-PDF-001:1613|For Loyalty Totals Request with Update Code = 'T', Account Number field must contain 'TOTALS REQUEST'.|BR-359-2|359|76% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1648|Merchant should send Expiration Date corresponding to the PAN or TransArmor Token sent in Account Number (Data Element 2, Segment 100).|BR-364-7|364|76% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2052|Loyalty Account Number is required on all loyalty card transactions.|BR-440-1|440|78% MEDIUM|

## Segment 108 - SEG108-R-010 - REVIEW_REQUIRED

**Test Solution rule:** Points to Redeem, when populated, is numeric with maximum length 6  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.7|108|140|points-to-redeem`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-108

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:809|Reversal of coupon redeem requires Coupon ID, Coupon Amount, and approval number of previous transaction receipt.|BR-134-5|134|58% MEDIUM|
|REQ-SRC-ATL105-PDF-001:810|If loyalty card not available for Reversal of coupon redeem, clerk enters street number and telephone number instead of account number.|BR-134-6|134|58% MEDIUM|

## Segment 108 - SEG108-R-012 - REVIEW_REQUIRED

**Test Solution rule:** Coupon Amount, when populated, is numeric with maximum length 8  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.7|108|142|coupon-amount`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-108

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:807|If loyalty card not available for Account inquiry, clerk enters street number and telephone number instead of account number.|BR-134-3|134|58% MEDIUM|
|REQ-SRC-ATL105-PDF-001:808|If loyalty card not available for Points redemption, clerk enters street number and telephone number instead of Loyalty Account Number.|BR-134-4|134|58% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1707|For fuel-only transactions with discounts/coupons, Fuel Purchase Amount equals fuel product sum minus discount/coupon amount.|BR-378-4|378|76% MEDIUM|

## Segment 108 - SEG108-R-013 - REVIEW_REQUIRED

**Test Solution rule:** Update Code, when populated, is exactly one alphanumeric character from the documented set A, C, E, I, P, S, T, U  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|13.2|108|143|update-code-enumeration`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-108

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1436|Segment Type field 1 has fixed value 150 for Fuel Price Update Response Segment.|BR-304-1|304|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1614|For other loyalty advice transactions, Account Number must contain the actual Loyalty Account Number.|BR-359-3|359|63% MEDIUM|

## Segment 108 - SEG108-R-014 - REVIEW_REQUIRED

**Test Solution rule:** Street Address, when populated, is numeric with maximum length 5  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.7|108|144|street-address`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-108

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:795|Merchant copy of receipt must print customer name, street address, and state/ZIP lines when ECA/TeleCheck used.|BR-132-1|132|54% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1195|Street Address field presence is Conditional.|BR-235-5|235|41% LOW|
|REQ-SRC-ATL105-PDF-001:2838|Shipping address DETAIL consists of 28-character street address followed by 9-digit zip code.|BR-679-7|679|54% MEDIUM|

## Segment 108 - SEG108-R-015 - REVIEW_REQUIRED

**Test Solution rule:** Phone Number, Loyalty, when populated, is numeric with maximum length 10  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.7|108|145|phone-number-loyalty`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-108

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:806|If loyalty card not available for Expiration date update, clerk enters street number and telephone number instead of account number.|BR-134-2|134|55% MEDIUM|

## Segment 108 - SEG108-R-016 - CONFIRMED

**Test Solution rule:** Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|10.9.1.2,13.2|108|146|expiration-date-mmyy`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:460|If card cannot be read magnetically after three swipe attempts, device should prompt for manual entry of Account Number and Expiration Date (Format: MMYY).|BR-79-10|79|76% MEDIUM|
|REQ-SRC-ATL105-PDF-001:522|The Expiration Date must be suppressed on all transaction receipts.|BR-90-1|90|28% LOW|
|REQ-SRC-ATL105-PDF-001:526|Expiration Date must be suppressed on all transaction receipts.|BR-90-4|90|84% HIGH|
|REQ-SRC-ATL105-PDF-001:536|These two items must be separated by an "=" sign, rather than a Field Separator.|BR-92-1|92|28% LOW|
|REQ-SRC-ATL105-PDF-001:537|Device at point of interaction must not display/store card-read data except account number, expiration date, cardholder name.|BR-92-1|92|76% MEDIUM|
|REQ-SRC-ATL105-PDF-001:544|Host message should only contain Account Number and Expiration Date (YYMM) in Card Discretionary Block Data fields.|BR-92-8|92|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:547|Card Discretionary Data field follows the 4-digit Expiration Date only for specific credit transactions.|BR-93-1|93|64% MEDIUM|
|REQ-SRC-ATL105-PDF-001:550|Device does not perform expiration date validation on debit card; online authorization always required.|BR-94-1|94|84% HIGH|
|REQ-SRC-ATL105-PDF-001:600|These two items must be separated by an "=" sign, rather than a Field Separator.|BR-103-1|103|25% LOW|
|REQ-SRC-ATL105-PDF-001:601|Device must not display/store card-read data except account number, expiration date, and cardholder name.|BR-103-1|103|71% MEDIUM|
|REQ-SRC-ATL105-PDF-001:608|Host message should only contain Account Number and Expiration Date (YYMM) in Card Discretionary Block Data field.|BR-103-8|103|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:610|No other Card Discretionary Data field follows the 4-digit Expiration Date for Debit Completions, Reversals, and Cancellations.|BR-103-10|103|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:651|The device does not perform expiration date validation in an EBT card transaction.|BR-110-1|110|84% HIGH|
|REQ-SRC-ATL105-PDF-001:654|If no expiration date is provided for manually entered EBT account number, use 1249.|BR-110-4|110|81% HIGH|
|REQ-SRC-ATL105-PDF-001:661|Stand-alone equipment electronic voucher transaction must allow manual entry of Account Number and Expiration Date.|BR-112-2|112|71% MEDIUM|
|REQ-SRC-ATL105-PDF-001:719|an activation (Please see section 10.7.3.6 — "Activation Transaction.|BR-123-1|123|28% LOW|
|REQ-SRC-ATL105-PDF-001:720|Device does not perform expiration date validation in a stored value card transaction.|BR-123-1|123|84% HIGH|
|REQ-SRC-ATL105-PDF-001:722|If manual entry allowed and stripe unreadable after 3 tries, prompt manual entry of Account Number and Expiration Date (MMYY).|BR-123-3|123|76% MEDIUM|
|REQ-SRC-ATL105-PDF-001:723|First Data Premium Gift Card has no embossed expiration date; default value 1249 (MMYY) sent by POS device.|BR-123-4|123|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:727|Bar Code Scanned Gift card treated as manual entry transaction, sent with Account Number and Expiration Date, entry mode '01'.|BR-123-8|123|76% MEDIUM|
|REQ-SRC-ATL105-PDF-001:799|Device does not perform expiration date validation on loyalty card transactions.|BR-133-1|133|84% HIGH|
|REQ-SRC-ATL105-PDF-001:800|If card cannot be read after three swipe attempts, device prompts for manual entry of Account Number and Expiration Date.|BR-133-2|133|81% HIGH|
|REQ-SRC-ATL105-PDF-001:801|User may skip Expiration Date prompt when manually keyed and no expiration date present on card.|BR-133-3|133|84% HIGH|
|REQ-SRC-ATL105-PDF-001:1190|a field is not populated — still send the Field Separator.|BR-235-1|235|25% LOW|
|REQ-SRC-ATL105-PDF-001:1196|Expiration Date is reserved for future use.|BR-236-1|236|64% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1448|Table ID for Network Token Expiration Date layout is fixed value 002 with Table Length fixed at 004.|BR-308-1|308|18% LOW|
|REQ-SRC-ATL105-PDF-001:1459|Account Updater Expiration Date only included when merchant sent Account Updater Request Indicator = Y.|BR-311-4|311|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1460|Account Updater Expiration Date is applicable only to Visa and MasterCard.|BR-311-5|311|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1646|For either TransArmor processing method, the expiration date must be included in Card Discretionary Block Data.|BR-364-5|364|73% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1647|When merchant receives Account Updater Expiration Date in Segment 155, follow-on transactions may send new or original expiration date in this field.|BR-364-6|364|73% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1650|For manually entered card number, expiration date must be in MMYY format.|BR-365-2|365|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1651|Merchants must send an expiration date value even for expired cards, for all authorizations.|BR-365-3|365|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1654|For either TransArmor processing method, expiration date must be included and stored for follow-on transactions with a Token.|BR-365-6|365|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1655|For Comdata Express Code or Check request (card type 095/096), expiration date field should be skipped but separators included.|BR-365-7|365|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1656|For Money Code/Check request (card type 093), expiration date field should be skipped but separators included.|BR-365-8|365|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1657|Expiration date is mandatory for Accel/STAR e-commerce and one-time bill payment transactions.|BR-365-9|365|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1658|Effective May 1, 2025, expiration date is optional for Accel/STAR COF transactions (bill payment, recurring, installment).|BR-365-10|365|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1997|When Additional Information Indicator = 013, Expiration Date (MMYY) is included, fixed length 4 bytes.|BR-430-5|430|81% HIGH|
|REQ-SRC-ATL105-PDF-001:2061|Earliest WIC Benefit Expiration Date is included in eWIC Balance Inquiry, Authorization, and Purchase Completion Responses.|BR-446-2|446|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2068|Earliest WIC Benefit Expiration Date format is CCYYMMDD, 8 bytes.|BR-447-2|447|18% LOW|
|REQ-SRC-ATL105-PDF-001:2732|If no value has been stored at the device, default value of 1249 is sent for Card Expiration Date.|BR-648-1|648|18% LOW|
|REQ-SRC-ATL105-PDF-001:2733|Depending on transaction context, Card Expiration Date value can be either MMYY or YYMM format.|BR-648-2|648|18% LOW|
|REQ-SRC-ATL105-PDF-001:2846|Payment Token Expiration Date is populated in the PAN Expiration Date field during transaction processing.|BR-681-7|681|54% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2992|On all subsequent transactions, unencrypted expiration date should be sent in Data Element 12, Card Discretionary Block Data.|BR-710-4|710|73% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2997|Encrypted data for a manually entered PAN should be followed by <fs> and unencrypted MMYY expiration date.|BR-711-3|711|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:3003|For TransArmor processing, expiration date must be stored for subsequent transactions with a token.|BR-712-1|712|74% MEDIUM|

## Segment 108 - SEG108-R-017 - REVIEW_REQUIRED

**Test Solution rule:** Payment Tender Type is required, exactly 2 alphanumeric characters from the documented set AX, CK, CS, DB, DN, DS, EB, EC, FL, GC, JC, MC, PC, PR, VS  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|13.2|108|148|payment-tender-type-enumeration`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-108

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:659|EBT processing supports tender types: Food stamps and Cash benefits.|BR-110-9|110|90% HIGH|
|REQ-SRC-ATL105-PDF-001:709|eWIC card receipts must include Payment Tender Type, Transaction Type, Clerk ID, Voucher ID, amounts, balances, approved/declined messages, benefit expiration date.|BR-119-4|119|60% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1346|Segment Type fixed value is 141 for Moneris Day End Batch Close Request segment.|BR-279-3|279|90% HIGH|
|REQ-SRC-ATL105-PDF-001:2053|If Payment Tender Type is not 'CS', two cards must be swiped: a loyalty card and a payment card.|BR-443-1|443|84% HIGH|

## Segment 108 - SEG108-R-018 - REVIEW_REQUIRED

**Test Solution rule:** Loyalty Track 2 Data, when populated, is alphanumeric with maximum length 38  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.7|108|147|loyalty-track2-data`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-108

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:779|Partial Track 2 Data consists of PAN, '=' sign, then 4-digit expiration date (YYMM), no other data follows.|BR-129-7|129|84% HIGH|
|REQ-SRC-ATL105-PDF-001:1187|Segment Type field has a fixed value of 105 in Totals Data Segment.|BR-232-6|232|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1192|Loyalty Card Data Segment has maximum length of 142 alphanumeric characters.|BR-235-2|235|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1282|Fuel Volume Data field length is determined by segment length in field 2, up to max 3,600.|BR-259-2|259|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1564|Segment Length Indicator excludes the Data Type Indicator from its length calculation.|BR-332-2|332|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2256|Card Discretionary Block Data identifies card expiration date used in manual entry, max length 51.|BR-499-6|499|48% LOW|

## Segment 108 - SEG108-R-019 - REVIEW_REQUIRED

**Test Solution rule:** Loyalty Information Version, when populated, is numeric length 1 with valid values 1 or 2; defaults to 1 when not sent  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|13.2|108|150|loyalty-information-version`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-108

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:794|The following information must be printed on each copy of Version 2 Denial Receipt.|BR-132-1|132|28% LOW|
|REQ-SRC-ATL105-PDF-001:819|Any loyalty information received by the device from Table 008 or Table 010 layouts is printed.|BR-135-5|135|54% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2054|If Loyalty Information Version not sent in a transaction request, value defaults to 1.|BR-444-1|444|84% HIGH|

## Segment 108 - SEG108-R-020 - REVIEW_REQUIRED

**Test Solution rule:** Unit of Work, when populated, is numeric with fixed length 19; required on loyalty reversals to match the original purchase  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|13.2|108|151|unit-of-work`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-108

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:2055|Unit of Work is required on loyalty reversals to match original purchase information.|BR-444-2|444|84% HIGH|

## Segment 108 - SEG108-R-023 - REVIEW_REQUIRED

**Test Solution rule:** Segment 108's sole optional Data Section 3 companion is Segment 114 (SKU Data Segment)  
**Class/severity:** compatibility / error  
**Canonical anchor:** `ATL105|2026-3|11.2.1|108,114|null|loyalty-sku-sole-companion`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-108

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1146|Segment Type field has fixed value 101 identifying the Fleet Data Segment.|BR-223-4|223|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1218|Segment Type (Field No. 1) has fixed value 111 identifying the Variable Information Data Segment.|BR-243-6|243|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1323|Segment Type field has fixed value 131 for the EMV Response Data Segment.|BR-269-5|269|90% HIGH|

## Segment 108 - SEG108-R-024 - REVIEW_REQUIRED

**Test Solution rule:** Segment 108 does not appear in the Loyalty Card Transaction Response, which mirrors the generic Financial Transaction Response layout  
**Class/severity:** lifecycle / error  
**Canonical anchor:** `ATL105|2026-3|11.2.2|108|null|loyalty-segment-request-only`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-108

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:920|The device does not perform expiration date validation in an EMV card transaction.|BR-157-7|157|79% MEDIUM|

## Segment 111 - SEG111-R-001 - CONFIRMED

**Test Solution rule:** Segment Type fixed 111  
**Class/severity:**  /   
**Canonical anchor:** `ATL105|2026-3|12.10|111|85|segment-111-type-fixed-value`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:631|STAR Signature Debit transactions must occur within US and US territories.|BR-106-5|106|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:931|POS Devices processing chip-initiated credit/signature debit transactions must support submitting a PIN in authorization request.|BR-159-5|159|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1072|Key Data section repeats up to 3 times (max 48 bytes total); presence based on Key Indicators field.|BR-200-1|200|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1089|EMV Request Data Segment is required for all EMV card transactions and is the only segment required for all EMV financial transactions.|BR-203-8|203|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1095|Incomm Market Basket Data (Response) Segment returned when Request segment was included in the request.|BR-204-4|204|57% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1131|Standard Message Data Segment is the only required data segment on all Financial Transaction Request messages.|BR-220-2|220|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1135|Segment Length includes Segment Type's length and Field Separators.|BR-220-6|220|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1141|For DUKPT PIN encryption, Encrypted PIN Block Data is a 16- to 20-byte KSN plus a 16-byte PIN block.|BR-221-3|221|61% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1148|Each Fleet Tag has a 3-byte fixed-length tag portion and up to 31 bytes of data.|BR-224-2|224|21% LOW|
|REQ-SRC-ATL105-PDF-001:1153|a field is not populated — still send the Product Data Field Delimiter.|BR-226-3|226|28% LOW|
|REQ-SRC-ATL105-PDF-001:1167|When a field is not populated, still send the Field Separator or Field Delimiter.|BR-226-14|226|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1174|A Field Separator follows Product Amount if it is the last element in the segment.|BR-228-2|228|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1180|All fields are separated by Field Separators; unpopulated fields still send Field Separator.|BR-230-2|230|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1190|a field is not populated — still send the Field Separator.|BR-235-1|235|25% LOW|
|REQ-SRC-ATL105-PDF-001:1196|Expiration Date is reserved for future use.|BR-236-1|236|64% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1235|Print Data Segment always appears at the end of a Financial Transaction response.|BR-249-2|249|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1248|Prompt Code, Pending valid values are 0901, 0902, 0904, 0981.|BR-252-2|252|84% HIGH|
|REQ-SRC-ATL105-PDF-001:1253|Device Card Table Version consists of seven version values, each 5 characters, strung together.|BR-252-7|252|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1277|Product Code 991 indicates an administrative amount, used in a pre-pay environment when price is adjusted to include discount amount.|BR-257-5|257|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1283|Fields are not separated by Field Separators; unpopulated fields are immediately followed by the next field.|BR-259-3|259|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1285|Segment 119 always appears in Field No. 3 in Data Section No. 3.|BR-260-1|260|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1290|Prompt Code fixed value is 990 identifying format type for requested totals.|BR-260-6|260|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1293|Field Nos. 17-19 are sent up to a maximum of 20 times, once for each card type, in order shown.|BR-261-1|261|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1298|Print Data 2 Segment is only sent on Financial Transaction Response messages requiring large amounts of print data.|BR-263-1|263|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1356|A Field Separator always follows Segment Type and Segment Length, and the last Tax Amount per product.|BR-282-8|282|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1391|Prompt Tokens differ by authorizer; different authorizers support different prompts.|BR-289-3|289|21% LOW|
|REQ-SRC-ATL105-PDF-001:1399|Prompt Code 999 indicates no prompts issued.|BR-293-2|293|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1402|Table Length for Payee Name has a maximum variable value of 017.|BR-294-3|294|21% LOW|
|REQ-SRC-ATL105-PDF-001:1407|Flags in Table Data must be separated by a comma; if a flag is absent, it can be skipped using just the separator.|BR-295-2|295|57% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1419|Product Data repeated for as many products as needed, separated by '\|' and ending with '\|'.|BR-297-3|297|57% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1420|Prompt Data repeated for as many prompts as needed, separated by '\|' and ending with '\|'.|BR-297-4|297|21% LOW|
|REQ-SRC-ATL105-PDF-001:1467|Field is included in response only when merchant sent Account Updater Request Indicator with value 'Y' or 'I' in request.|BR-313-1|313|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1771|Secondary Phone Number is used once attempts using the primary Phone Number are exhausted.|BR-392-2|392|64% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1772|A tertiary Phone Number is assigned to but not used by the device.|BR-392-3|392|64% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2275|Card Type codes 127-168 turn on specific device features and are not used in any Prompt Codes.|BR-511-5|511|80% HIGH|
|REQ-SRC-ATL105-PDF-001:2308|Decline codes prompt clerk/customer to reenter only the specific data indicated in the prompt.|BR-541-1|541|54% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2331|Table Data for Partial Approval Indicator valid values: 0=Not supported, 1=Supported, 5=Supports balance receipt but not partial approval.|BR-550-1|550|15% LOW|
|REQ-SRC-ATL105-PDF-001:2366|Table ID for Originating Device Type Indicator is fixed value 022.|BR-557-1|557|21% LOW|
|REQ-SRC-ATL105-PDF-001:2367|Table Data default value is 00 (Card) if not specified.|BR-557-2|557|21% LOW|
|REQ-SRC-ATL105-PDF-001:2430|When Variable Information Indicator = 035 (Verified by Visa Data), merchant must send XID and CAVV in binary data format.|BR-574-1|574|84% HIGH|
|REQ-SRC-ATL105-PDF-001:2436|Merchant must send UCAF data with subfield ID value 43 and subfield length 28 within the 32 bytes of UCAF data.|BR-576-1|576|90% HIGH|
|REQ-SRC-ATL105-PDF-001:2442|Fraud Enhanced Data Table ID fixed value 039, length fixed at 8 bytes.|BR-576-7|576|12% LOW|
|REQ-SRC-ATL105-PDF-001:2443|Final Amount Indicator allowed only on EMV initial transactions where final Settlement Amount may differ from Authorization Amount.|BR-577-1|577|21% LOW|
|REQ-SRC-ATL105-PDF-001:2444|Final Amount Indicator should NOT be sent when final Settlement Amount equals original Authorization Amount.|BR-577-2|577|21% LOW|
|REQ-SRC-ATL105-PDF-001:2453|Third Party Installment Payment Providers must include their name and underlying retailer name in format 'Third party name*underlying merchant name'.|BR-580-2|580|18% LOW|
|REQ-SRC-ATL105-PDF-001:2454|Table length should be '1' when sending only Program Protocol, or '37' when sending both Program Protocol and Directory Server Transaction ID.|BR-580-3|580|15% LOW|
|REQ-SRC-ATL105-PDF-001:2464|Sub-Table ID fixed value is 02 for entity initiating transaction sub-table.|BR-582-4|582|21% LOW|
|REQ-SRC-ATL105-PDF-001:2606|This field must be spaces if not used by merchant.|BR-609-1|609|28% LOW|
|REQ-SRC-ATL105-PDF-001:2858|Security Condition values 8-9 are reserved for private use.|BR-689-2|689|21% LOW|

## Segment 111 - SEG111-R-002 - CONFIRMED

**Test Solution rule:** Segment Length format and computation  
**Class/severity:**  /   
**Canonical anchor:** `ATL105|2026-3|12.10|111|84|segment-111-length-format`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:254|Enhanced Fleet Data must be alphanumeric and must not exceed 999 bytes.|ENT-ELEM-239|490|50% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1090|Moneris Data (Request) Segment required only for transactions destined for the Moneris authorizer.|BR-203-9|203|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1096|All EMV Financial Transaction Requests contain one or more of the listed data segments in Field Nos. 4-8.|BR-204-5|204|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1115|For EMV, the first two characters (Device Type) of Terminal Identifier must be '+*' regardless of actual device type.|BR-211-3|211|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1128|RQST/CURRENT HASH MATCH code indicates key update is not required.|BR-214-4|214|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1282|Fuel Volume Data field length is determined by segment length in field 2, up to max 3,600.|BR-259-2|259|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1291|Segment Type fixed value is 119.|BR-260-7|260|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1460|Account Updater Expiration Date is applicable only to Visa and MasterCard.|BR-311-5|311|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1507|Connector type field has maximum length of 3 characters.|BR-319-1|319|51% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1745|For fuel and nonfuel amounts together with discounts/coupons, discounts/coupons are first applied to the nonfuel amount.|BR-386-6|386|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1746|Nonfuel Amount valid values are 1-99999999.|BR-386-7|386|84% HIGH|
|REQ-SRC-ATL105-PDF-001:1747|Number of Card Types valid values are 01-99.|BR-386-8|386|84% HIGH|
|REQ-SRC-ATL105-PDF-001:1748|Number of Print Lines does not include Data Element 168, Count of Receipt Text Line.|BR-387-1|387|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1749|Data Element 168 count does not reflect total from Number of Print Lines.|BR-387-2|387|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1750|Number of Print Lines information comes from BUYPASS.|BR-387-3|387|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1751|Number of Print Lines valid values are 1, 2, or 3.|BR-387-4|387|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1752|Number of Products must precede single digits (1-9) with a zero (01-09).|BR-387-5|387|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1753|Number of Products valid values range from 01 to 10.|BR-387-6|387|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1754|Financial Transaction Requests: Number of Segments followed by Data Segment 100, plus optional input segments from Chapter 12.|BR-388-1|388|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1755|Loyalty Card Transaction Requests: Number of Segments followed by Segment 100, Segment 108, and Segment 114.|BR-388-2|388|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1756|ECA/TeleCheck Service Transaction Requests: Number of Segments followed by Segment 100, Segment 110, and Segment 111.|BR-388-3|388|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1757|Totals Requests: Number of Segments always followed by Segment 105, Totals Data Segment.|BR-388-4|388|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1758|Electronic Mail Requests: Number of Segments always followed by Segment 109, Electronic Mail Data Segment.|BR-388-5|388|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1759|Communications Test Requests: Number of Segments always followed by Element 120, Network Management Message.|BR-388-6|388|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1760|TransArmor PKI Encryption and Tokenization: Used for Key and Key ID Load, followed by Segment 116, TransArmor Load Data Segment.|BR-388-7|388|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1761|Number of Segments value N indicates transaction has Segment 100 plus N-1 input segments listed in Chapter 12.|BR-388-8|388|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1762|Odometer element found in Fleet Data Segment if required by issuer.|BR-389-1|389|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1763|Odometer valid values range from 1 to 99999999.|BR-389-2|389|84% HIGH|
|REQ-SRC-ATL105-PDF-001:1764|Password required for totals request or electronic mail request.|BR-389-3|389|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1765|If password entry is less than six characters, right-align entry using spaces.|BR-389-4|389|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1766|Default password is 123456.|BR-389-5|389|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1767|PC Duty Amount is used for Purchase Card Transaction Request.|BR-391-1|391|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1768|PC Freight Amount is used in Direct Marketing/Address Verification System Request.|BR-391-2|391|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1769|PC Tax Amount differs from Element No. 99, Tax Amount.|BR-391-3|391|71% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1770|Transaction dialing always begins with the primary Phone Number.|BR-392-1|392|64% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2183|Safekey Data positions 1-2 must be fixed value 'SK' indicating SafeKey cryptogram transaction.|BR-478-3|478|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2185|Safekey Data appears in Data Segment No. 123 (NFC Payment Tokenization Data Segment).|BR-478-5|478|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2427|For Visa Card Type, cardholder name data is variable length up to 105 digits including Middle Name after First and Last Name.|BR-573-2|573|21% LOW|
|REQ-SRC-ATL105-PDF-001:2428|NVS Table ID is fixed value 034 with length 3 bytes.|BR-573-3|573|21% LOW|
|REQ-SRC-ATL105-PDF-001:2435|Value 4 (Identity Check Insights) means no fraud liability shift since issuer does not receive authentication request.|BR-575-5|575|21% LOW|
|REQ-SRC-ATL105-PDF-001:2440|MCX Checkout Token Table ID fixed value 037, length fixed value 040, data an40.|BR-576-5|576|9% LOW|
|REQ-SRC-ATL105-PDF-001:2441|Paydiant Tender ID Table ID fixed value 038, length fixed value 012, data an12.|BR-576-6|576|9% LOW|

## Segment 111 - SEG111-R-006 - REVIEW_REQUIRED

**Test Solution rule:** Total maximum  
**Class/severity:**  /   
**Canonical anchor:** `ATL105|2026-3|12.10|111||segment-111-max-length`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-111

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:936|R indicates required, O indicates optional, C indicates conditional entry for a data element.|BR-162-4|162|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1166|A Product Data Field Delimiter always follows Quantity and Unit Price, and follows Product Amount unless it is last element.|BR-226-13|226|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1825|Fleet Data Segment length is 001-61.|BR-400-2|400|57% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2399|Merchant must send COF indicator as 'I' (initial) or 'C' (subsequent) for all COF transactions.|BR-568-1|568|80% HIGH|

## Segment 111 - SEG111-R-007 - REVIEW_REQUIRED

**Test Solution rule:** Separator serialization  
**Class/severity:**  /   
**Canonical anchor:** `ATL105|2026-3|12.10|111||segment-111-repetition-separator`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-111

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1169|Product Data section repeats per product for a maximum of 10 products, total variable length up to 370 bytes.|BR-227-1|227|90% HIGH|

## Segment 113 - SEG113-R-001 - REVIEW_REQUIRED

**Test Solution rule:** Segment 113 belongs exclusively to the ECA/TeleCheck® Service Transaction Request; it is not a Financial Transaction Request companion  
**Class/severity:** structure / error  
**Canonical anchor:** `ATL105|2026-3|11.1.1,11.3.1|113|63|ecatelecheck-transaction-exclusive`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-113

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:983|ECA/TeleCheck Service Transaction Request contains segments in Field Nos. 4-6: Segments 110, 111, 113.|BR-174-4|174|85% HIGH|

## Segment 113 - SEG113-R-002 - REVIEW_REQUIRED

**Test Solution rule:** Segment 113 is a conditional member of the ECA/TeleCheck® Service Transaction Request's Data Section 3 (present when check-service risk-control data is being sent)  
**Class/severity:** applicability / error  
**Canonical anchor:** `ATL105|2026-3|11.3.1|113|null|ecatelecheck-request-conditional`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-113

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:979|Data Section No. 3 contains none, one, or more of Check, Variable Information, or ECA/TeleCheck Data Segments.|BR-173-3|173|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1218|Segment Type (Field No. 1) has fixed value 111 identifying the Variable Information Data Segment.|BR-243-6|243|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1337|Segment Type field (Element 85) has fixed value 135 for Moneris Data Segment (Request).|BR-274-3|274|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1346|Segment Type fixed value is 141 for Moneris Day End Batch Close Request segment.|BR-279-3|279|90% HIGH|
|REQ-SRC-ATL105-PDF-001:2043|If MICR data > 50 bytes and prompt code is ECA/TeleCheck check service, refer to ECA/TeleCheck Data Segment (Segment 113).|BR-434-4|434|79% MEDIUM|

## Segment 113 - SEG113-R-003 - REVIEW_REQUIRED

**Test Solution rule:** Segment Type is 113  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.12|113|85|segment-type`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-113

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1224|Segment Type field has fixed value 112 for Additional Information Data Segment.|BR-245-2|245|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1238|There is a Field Separator between Field Nos. 1 and 2 and between Field Nos. 2 and 3.|BR-249-5|249|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1246|Segment Type fixed value is 118 for the Proprietary Data Load Segment.|BR-251-7|251|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1291|Segment Type fixed value is 119.|BR-260-7|260|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1330|Segment Type field fixed value is 134.|BR-273-3|273|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1343|Segment Type field 1 must have fixed value 140.|BR-277-2|277|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1409|Segment Type field has fixed value 146 for Enhanced Fleet Response Segment.|BR-295-4|295|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1564|Segment Length Indicator excludes the Data Type Indicator from its length calculation.|BR-332-2|332|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1848|Segment Type is required for the TransArmor PKI Encryption and Tokenization Load Request.|BR-401-1|401|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1849|Segment Type value 106 and 107 are reserved for proprietary use.|BR-401-2|401|90% HIGH|

## Segment 113 - SEG113-R-004 - REVIEW_REQUIRED

**Test Solution rule:** Segment Length is 3 digits representing the segment content length  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.12|113|84|segment-length`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-113

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1178|Segment Length should be 4 for EBT with eWIC data transactions.|BR-229-3|229|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1521|A Field Separator always follows Segment Type and Segment Length; sent even when field not populated.|BR-320-12|320|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1821|Segment Length has four digits only for segments 103, 114, 115, 118, 120, 130, and 131.|BR-399-1|399|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1822|Segment Length cannot have a length of 4 digits when sending any segment other than the listed seven.|BR-399-2|399|90% HIGH|

## Segment 113 - SEG113-R-005 - CONFIRMED

**Test Solution rule:** Segment 113 maximum length is 156 alphanumeric characters  
**Class/severity:** serialization / error  
**Canonical anchor:** `ATL105|2026-3|12.12|113|84|segment-length-max-156`  
**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.  
**Review owner:** 

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1134|Segment Type field has fixed value 100 for Standard Message Data Segment.|BR-220-5|220|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1165|A Field Separator always follows Segment Type and Segment Length; Field Separator follows Product Amount when it's the last element.|BR-226-12|226|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1226|ECA/TeleCheck Data Segment has a maximum length of 156 alphanumeric characters.|BR-246-2|246|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1335|Field Nos 1&2 and 2&3 are separated by Field Separators; segment should end with a field separator.|BR-274-1|274|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1835|ECA/TeleCheck Data Segment length is 001-156.|BR-400-12|400|57% MEDIUM|

## Segment 113 - SEG113-R-006 - REVIEW_REQUIRED

**Test Solution rule:** Field order matches Section 12.12 ascending element order (85, 84, 131, 132, 133, 134, 135, 136, 137)  
**Class/severity:** serialization / error  
**Canonical anchor:** `ATL105|2026-3|12.12|113|null|segment-113-field-order`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-113

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1146|Segment Type field has fixed value 101 identifying the Fleet Data Segment.|BR-223-4|223|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1187|Segment Type field has a fixed value of 105 in Totals Data Segment.|BR-232-6|232|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1215|A Field Separator exists between Field Nos. 1 and 2 and between Field No. 2 and Variable Information Section.|BR-243-3|243|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1323|Segment Type field has fixed value 131 for the EMV Response Data Segment.|BR-269-5|269|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1435|Segment Type field has fixed value 149 for Fuel Price Update Request Segment.|BR-303-3|303|90% HIGH|

## Segment 113 - SEG113-R-007 - REVIEW_REQUIRED

**Test Solution rule:** All fields are separated by Field Separators; empty fields still send the separator  
**Class/severity:** serialization / error  
**Canonical anchor:** `ATL105|2026-3|12.12|113|null|field-separator-required`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-113

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:793|Additional recommended fields include Customer Telephone Number if prompted, Custom Field prompt/data, Merchant Trace ID.|BR-131-9|131|48% LOW|
|REQ-SRC-ATL105-PDF-001:1135|Segment Length includes Segment Type's length and Field Separators.|BR-220-6|220|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1227|All fields in the segment are separated by Field Separators; unpopulated fields still send the Field Separator.|BR-246-3|246|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1301|A Field Separator exists between Field Nos. 1 and 2 and between Field Nos. 2 and 3.|BR-263-4|263|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1340|Segment Type field must have fixed value 139 for Moneris Day End Batch Balance Request Segment.|BR-276-1|276|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1356|A Field Separator always follows Segment Type and Segment Length, and the last Tax Amount per product.|BR-282-8|282|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1433|Fields 1-2, 2-3, and 3-4 are separated by Field Separators; segment ends with a field separator.|BR-303-1|303|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1436|Segment Type field 1 has fixed value 150 for Fuel Price Update Response Segment.|BR-304-1|304|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1437|Segment Length includes Segment Type's length and Field Separators.|BR-304-2|304|84% HIGH|
|REQ-SRC-ATL105-PDF-001:1823|Segment Length is calculated using lengths of all data elements plus Field Separators/Product Data Field Delimiters in the segment.|BR-399-3|399|80% HIGH|
|REQ-SRC-ATL105-PDF-001:2251|Segment Length value (078) identifies the length in bytes of the Standard Message Data Segment, including field separators.|BR-499-1|499|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2263|Segment Length field identifies the length (017 bytes) of the Variable Information Data Segment, including Field Separators.|BR-503-3|503|70% MEDIUM|

## Segment 113 - SEG113-R-008 - REVIEW_REQUIRED

**Test Solution rule:** ECA/TeleCheck® Clerk ID, required, is alphanumeric with maximum length 6  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.12,13.2|113|131|eca-clerk-id`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-113

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1208|Element number 239 is used for Alternate MICR Indicator since element 131 is currently in use.|BR-240-3|240|21% LOW|
|REQ-SRC-ATL105-PDF-001:1669|For ECA/TeleCheck processing, refer to Data Element No. 131, ECA/TeleCheck Clerk ID, instead of Clerk ID.|BR-368-5|368|68% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2049|ECA/ TeleCheck Clerk ID valid values range from 1 to 999999.|BR-437-1|437|58% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2716|ECA/TeleCheck Trace ID Table ID is fixed value 005, with fixed table length and maximum length 22.|BR-643-1|643|51% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2717|ECA/TeleCheck Denial Record Number Table ID is fixed value 006, with fixed table length and maximum length 7.|BR-643-2|643|51% MEDIUM|

## Segment 113 - SEG113-R-010 - REVIEW_REQUIRED

**Test Solution rule:** ECA/TeleCheck® Phone Number, when populated, is numeric with maximum length 10  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.12,13.2|113|133|eca-phone-number`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-113

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:788|Merchant Name, City/State/ZIP, Merchant Phone, ECA/TeleCheck Service Phone must appear on each receipt copy.|BR-131-4|131|71% MEDIUM|
|REQ-SRC-ATL105-PDF-001:798|Version 2 Denial Receipt must print Denial Record Number, Check Amount, NACHA Language, TeleCheck Phone Number if cashiers not using courtesy cards.|BR-132-4|132|58% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1990|When Additional Information Indicator = 006, ECA/TeleCheck Denial Record Number information is included.|BR-429-6|429|78% MEDIUM|

## Segment 113 - SEG113-R-011 - REVIEW_REQUIRED

**Test Solution rule:** ECA/TeleCheck® Trace ID, when populated, is alphanumeric with maximum length 22; required on Void transaction requests  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.12,13.2|113|134|eca-trace-id`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-113

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:789|Terminal ID, ECA/TeleCheck Merchant ID, Date/Time, Transaction Number, Trace ID, Approval Number required on receipt.|BR-131-5|131|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1225|ECA/TeleCheck Trace ID must be present in all Void transaction requests.|BR-246-1|246|81% HIGH|
|REQ-SRC-ATL105-PDF-001:1989|When Additional Information Indicator = 005, ECA/TeleCheck Trace ID information is included.|BR-429-5|429|78% MEDIUM|

## Segment 113 - SEG113-R-013 - REVIEW_REQUIRED

**Test Solution rule:** Denial Record Number, when populated, is alphanumeric with maximum length 7; used to reference a declined ECA/TeleCheck® transaction on Denial Record receipts  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|10.8.5,12.12,13.2|113|136|denial-record-number`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-113

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:796|Denial Record Receipt prints details and Denial Record Number for consumer on transaction decline.|BR-132-2|132|51% MEDIUM|
|REQ-SRC-ATL105-PDF-001:797|Denial Record number is used with all TeleCheck products.|BR-132-3|132|61% MEDIUM|

## Segment 113 - SEG113-R-014 - REVIEW_REQUIRED

**Test Solution rule:** Extended MICR Data, when populated, is alphanumeric with maximum length 65; must supplement MICR Data (Element 122) in Segment 110 when raw MICR data exceeds 50 bytes  
**Class/severity:** field / error  
**Canonical anchor:** `ATL105|2026-3|12.12,13.2|113,110|137|extended-micr-data`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-113

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1228|Extended MICR Data is conditional, used when MICR length is greater than 50-bytes.|BR-247-1|247|58% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1282|Fuel Volume Data field length is determined by segment length in field 2, up to max 3,600.|BR-259-2|259|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2050|Extended MICR Data must be populated in addition to MICR Data when raw MICR data exceeds the length.|BR-439-1|439|58% MEDIUM|

## Segment 113 - SEG113-R-018 - REVIEW_REQUIRED

**Test Solution rule:** Segment 113 does not appear in the ECA/TeleCheck® Service Transaction Response, which mirrors the generic Financial Transaction Response layout  
**Class/severity:** lifecycle / error  
**Canonical anchor:** `ATL105|2026-3|11.3.2|113|null|ecatelecheck-segment-request-only`  
**Reason:** Prior comparison produced candidates but no evidence-supported confirmation for this rule.  
**Review owner:** ATL105-SME-113

| AI BR | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:785|A merchant authorization and check writer copy must be printed for each approved ECA/TeleCheck transaction.|BR-131-1|131|85% HIGH|
|REQ-SRC-ATL105-PDF-001:786|Check writer must sign merchant's copy before transaction is considered complete.|BR-131-2|131|79% MEDIUM|

