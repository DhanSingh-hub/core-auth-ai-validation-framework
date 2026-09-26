# Confirmed Matches: Composite Run1 + Run2 Delivery

- Composite delivery: `2026-09-23/Run1+Run2`
- Confirmed Test Solution rules: **30/235 (12.8%)**
- Distinct linked Run1 AI BRs: **312**

## Segment 100 - `SEG100-R-015`

**Segment Type is 100**

| AI requirement ID | Statement | Source rule | Page | Confidence |
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

## Segment 100 - `SEG100-R-039`

**Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context**

| AI requirement ID | Statement | Source rule | Page | Confidence |
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

## Segment 100 - `SEG100-R-041`

**Account Number or identification value is present for the transaction context**

| AI requirement ID | Statement | Source rule | Page | Confidence |
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

## Segment 100 - `SEG100-R-043`

**Encrypted PIN Block Data follows DUKPT representation when PIN applies**

| AI requirement ID | Statement | Source rule | Page | Confidence |
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

## Segment 100 - `SEG100-R-047`

**Tax Amount follows the total tax amount rule**

| AI requirement ID | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1110|A Field Separator follows Field No. 12 in the CA Public Key File Load Request.|BR-209-2|209|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1697|For EMV offline PIN validated debit, PIN block must contain a PIN or all F's, else transaction declines as invalid transaction.|BR-375-3|375|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1795|Pump/Lane Number valid codes/values are 1-99.|BR-395-3|395|81% HIGH|
|REQ-SRC-ATL105-PDF-001:1798|Quantity, Product Code, Unit of Measure, Unit Price, Product Amount repeat for up to 10 products in Segment 102 or 157.|BR-395-6|395|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1799|Quantity valid codes/values are 00000000, 01-399999999.|BR-395-7|395|85% HIGH|

## Segment 100 - `SEG100-R-060`

**eWIC Purchase Reversal/Void uses Prompt Code 8086**

| AI requirement ID | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1717|Initiation Time is calculated by BUYPASS using system clock, merchant profile time zone, and daylight saving flag.|BR-380-2|380|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1718|If required by the issuer, Job Number is found in the Fleet Data Segment (Segment No. 101).|BR-380-3|380|79% MEDIUM|

## Segment 103 - `SEG103-R-003`

**Segment Type is 103**

| AI requirement ID | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1104|Number of Print Lines indicates how many times Terminal Display/Printer Message occurs in the response.|BR-207-4|207|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1188|Fields 15-17 (Category Total Data Section) are sent up to 18 times, once per card type, only when data occurs for that type.|BR-234-1|234|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1204|All fields in Check Data Segment are separated by Field Separators; unpopulated fields still send the separator.|BR-239-3|239|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1305|Token PAN Suffix is returned in the response only if supplied by the authorizer.|BR-264-3|264|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1370|The ETX (▲) after last product's tax data signifies end of the segment.|BR-284-7|284|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1785|Financial transaction device transmits a 4-character Prompt Code at initiation.|BR-394-3|394|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1786|Special transaction device transmits a 3-character Prompt Code (Card Type) at initiation.|BR-394-4|394|80% HIGH|

## Segment 103 - `SEG103-R-004`

**Segment Length is 3 or 4 digits; 4 digits is required for EBT-with-eWIC transactions**

| AI requirement ID | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:943|Standard Message Data Segment is the only segment required for all financial transactions.|BR-164-4|164|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1067|No need to set any load flag on the terminal record; Moneris Key Load can be performed at any time.|BR-199-1|199|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1129|Error codes BLOCK NBR NOT NUMERIC or BLOCK NBR NOT 000-xxx indicate key load transaction failure.|BR-214-5|214|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1142|Local Date and Local Time is conditional, identifying date/time of preauthorized transaction.|BR-222-1|222|38% LOW|
|REQ-SRC-ATL105-PDF-001:1296|Use Card Labels (in parentheses) instead of field numbers to identify total buckets.|BR-261-4|261|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1433|Fields 1-2, 2-3, and 3-4 are separated by Field Separators; segment ends with a field separator.|BR-303-1|303|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1474|VAU006 indicates transaction is not a qualifying transaction type.|BR-313-8|313|57% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1759|Communications Test Requests: Number of Segments always followed by Element 120, Network Management Message.|BR-388-6|388|90% HIGH|

## Segment 103 - `SEG103-R-009`

**Clerk ID, when populated, is numeric with maximum length 10**

| AI requirement ID | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:669|EBT receipts must show Tender Type, Transaction Type, Clerk ID, Voucher Number, amounts, balances, approved/declined messages, HIP data.|BR-113-2|113|74% MEDIUM|
|REQ-SRC-ATL105-PDF-001:671|Receipt must indicate whether transaction is Food Stamp or Cash Benefit and the transaction type attempted.|BR-113-4|113|80% HIGH|
|REQ-SRC-ATL105-PDF-001:674|Food stamp dollar amount must be printed on Food Stamp transaction receipt.|BR-113-7|113|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1613|For Loyalty Totals Request with Update Code = 'T', Account Number field must contain 'TOTALS REQUEST'.|BR-359-2|359|76% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1614|For other loyalty advice transactions, Account Number must contain the actual Loyalty Account Number.|BR-359-3|359|63% MEDIUM|

## Segment 103 - `SEG103-R-010`

**Voucher ID, when populated, is numeric with maximum length 10**

| AI requirement ID | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:662|PAN printed on receipts must be truncated to last four digits, preceded by X's equal to truncated digit count.|BR-112-3|112|90% HIGH|
|REQ-SRC-ATL105-PDF-001:666|No signature line — printing an EBT receipt.|BR-113-1|113|28% LOW|
|REQ-SRC-ATL105-PDF-001:675|Cash benefit dollar amount(s) must be printed on Cash Benefit transaction receipt, broken down by purchase vs cash back with totals.|BR-113-8|113|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1837|Print Data Segment length is 001-1009.|BR-400-14|400|57% MEDIUM|

## Segment 103 - `SEG103-R-011`

**WIC Discount Amount uses the positional format Account Type(97) + Amount Type(52) + Currency Code + signed Amount**

| AI requirement ID | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:2003|When Additional Information Indicator = 021, Re-Price Data Response Information is included.|BR-430-11|430|81% HIGH|
|REQ-SRC-ATL105-PDF-001:2004|When Additional Information Indicator = 022, CAVV Result Information is included.|BR-430-12|430|84% HIGH|
|REQ-SRC-ATL105-PDF-001:2694|mPOS devices (Phone/Tablet + Dongle) without PIN Pad should use PIN Entry Capability Mode code 3.|BR-632-7|632|21% LOW|

## Segment 103 - `SEG103-R-014`

**EBT Program Data is bounded to 267 bytes and begins with a 3-digit Total Length subelement with maximum value 264**

| AI requirement ID | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:2020|When Additional Information Indicator = 039, Transaction Link Action Indicator is included.|BR-431-10|431|84% HIGH|
|REQ-SRC-ATL105-PDF-001:2037|Value 5 indicates merchant only supports card balance receipt, not Partial Approval processing.|BR-433-4|433|81% HIGH|
|REQ-SRC-ATL105-PDF-001:2043|If MICR data > 50 bytes and prompt code is ECA/TeleCheck check service, refer to ECA/TeleCheck Data Segment (Segment 113).|BR-434-4|434|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2197|SPDH Header appears in both Moneris Key Load request and response.|BR-480-2|480|70% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2688|If Contactless MSR is attached with no chip capability, all transactions use PIN Entry Capability Mode code 8.|BR-632-1|632|21% LOW|
|REQ-SRC-ATL105-PDF-001:2690|If no Contactless MSR attached and PIN debit feature enabled, all transactions use PIN Entry Capability Mode code 1.|BR-632-3|632|21% LOW|
|REQ-SRC-ATL105-PDF-001:2691|If no Contactless MSR attached and PIN debit feature disabled, all transactions use PIN Entry Capability Mode code 2.|BR-632-4|632|21% LOW|

## Segment 103 - `SEG103-R-015`

**EBT Program Data contains 1 to 6 Program Data subelements, each bounded to 44 bytes**

| AI requirement ID | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:2038|Support for Partial Authorization is mandatory for all card brands; must be populated correctly for card present transactions.|BR-433-5|433|90% HIGH|
|REQ-SRC-ATL105-PDF-001:2039|For Amex transactions with zero amount and values 1 or 5, merchant will not receive card balance in response.|BR-433-6|433|76% MEDIUM|

## Segment 103 - `SEG103-R-016`

**EBT Program Data subelement TAG must be one of the documented values (50, IT for requests; 51, 52 for responses)**

| AI requirement ID | Statement | Source rule | Page | Confidence |
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

## Segment 103 - `SEG103-R-017`

**EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE fixed value 98 (not required when TAG is IT)**

| AI requirement ID | Statement | Source rule | Page | Confidence |
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

## Segment 103 - `SEG103-R-022`

**EBT Program Data subelement full positional layout: TAG+LEN+ACCOUNT TYPE(98)+AMOUNT TYPE(=TAG)+CURRENCY CODE(840)+AMOUNT DESCRIPTOR(0/C/D)+DETAIL(12-digit amount) for TAG 50/51/52; TAG+LEN+ADDRESS(28)+ZIP(9) for TAG IT**

| AI requirement ID | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:711|Incentive balances are reset at the beginning of the fiscal month.|BR-120-2|120|57% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2044|Driver's License is required on all manually entered check transactions.|BR-434-5|434|81% HIGH|
|REQ-SRC-ATL105-PDF-001:2050|Extended MICR Data must be populated in addition to MICR Data when raw MICR data exceeds the length.|BR-439-1|439|58% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2696|Balance Information Table ID must have fixed value 001.|BR-635-1|635|21% LOW|

## Segment 103 - `SEG103-R-023`

**WIC Product Data subelement catalog: EF (Earliest WIC Benefit Expiration Date, 8 bytes), EA (WIC Prescription Balance Information, up to 14 bytes), PS (WIC UPC Exception/Denial or Purchase Information, up to 47 bytes)**

| AI requirement ID | Statement | Source rule | Page | Confidence |
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

## Segment 104 - `SEG104-R-004`

**Segment Type is 104.**

| AI requirement ID | Statement | Source rule | Page | Confidence |
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

## Segment 104 - `SEG104-R-005`

**Segment Length is 3 digits representing the content length.**

| AI requirement ID | Statement | Source rule | Page | Confidence |
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

## Segment 105 - `SEG105-R-001`

**Segment Type is 105**

| AI requirement ID | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:987|Totals Request Data Section No. 3 contains the Totals Data Segment (Data Segment No. 105).|BR-175-3|175|85% HIGH|

## Segment 105 - `SEG105-R-002`

**Segment Length includes Segment Type and field separators**

| AI requirement ID | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1181|a field is not populated — still send the Field Separator.|BR-232-1|232|25% LOW|
|REQ-SRC-ATL105-PDF-001:1183|Field Nos. 1-14 are separated by Field Separators, including one after Field No. 14.|BR-232-2|232|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1184|When a field is not populated, still send the Field Separator.|BR-232-3|232|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1185|Field Nos. 15-17 are not separated by Field Separators.|BR-232-4|232|90% HIGH|

## Segment 105 - `SEG105-R-008`

**Totals Date uses a permitted source-defined request code**

| AI requirement ID | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:413|At completion of business day, terminal sends Totals Request (222222 - Read and Cut Settlement) after last batch.|BR-62-9|62|80% HIGH|

## Segment 108 - `SEG108-R-001`

**Segment 108 belongs exclusively to the Loyalty Card Transaction Request; it is not a Financial Transaction Request companion**

| AI requirement ID | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:804|Loyalty card information can be included in Purchase/Capture, Purchase Reversal, and Time-out Reversal transactions.|BR-133-6|133|79% MEDIUM|
|REQ-SRC-ATL105-PDF-001:812|If loyalty card not available for Reversal of points redeemed, clerk enters street number and telephone number instead of account number.|BR-134-8|134|58% MEDIUM|
|REQ-SRC-ATL105-PDF-001:971|Loyalty Card Transaction Request Data Section 3 may contain none, one, or more of Loyalty Card Data Segment or SKU Data Segment.|BR-171-3|171|64% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1197|Payment Tender Type used to determine whether transaction is a multiple-card transaction involving a loyalty card.|BR-236-2|236|64% MEDIUM|
|REQ-SRC-ATL105-PDF-001:1735|For Loyalty Card Request messages, Element 63 (Number of Segments) and Segment 108 (Loyalty Card Data Segment) always follow Element 55.|BR-384-6|384|85% HIGH|
|REQ-SRC-ATL105-PDF-001:1755|Loyalty Card Transaction Requests: Number of Segments followed by Segment 100, Segment 108, and Segment 114.|BR-388-2|388|90% HIGH|

## Segment 108 - `SEG108-R-005`

**Segment 108 maximum length is 142 alphanumeric characters**

| AI requirement ID | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1165|A Field Separator always follows Segment Type and Segment Length; Field Separator follows Product Amount when it's the last element.|BR-226-12|226|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1335|Field Nos 1&2 and 2&3 are separated by Field Separators; segment should end with a field separator.|BR-274-1|274|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1830|Loyalty Card Data Segment length is 001-142.|BR-400-7|400|57% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2319|AVS Information Table Data has max length 29, first 9 characters are ZIP or ZIP+4.|BR-547-1|547|18% LOW|

## Segment 108 - `SEG108-R-008`

**Loyalty Program ID, required, is numeric with maximum length 6**

| AI requirement ID | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:2051|Loyalty Program ID is required on all loyalty card transactions.|BR-439-2|439|61% MEDIUM|
|REQ-SRC-ATL105-PDF-001:2731|Loyalty Information Unit of Work maximum length is 19 numeric.|BR-647-4|647|41% LOW|

## Segment 108 - `SEG108-R-009`

**Loyalty Account Number, when populated, is numeric with maximum length 24**

| AI requirement ID | Statement | Source rule | Page | Confidence |
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

## Segment 108 - `SEG108-R-016`

**Expiration Date, when populated, is numeric length 4 in MMYY format; device sends default 1249 when no expiration date is present on the card**

| AI requirement ID | Statement | Source rule | Page | Confidence |
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

## Segment 111 - `SEG111-R-001`

**Segment Type fixed 111**

| AI requirement ID | Statement | Source rule | Page | Confidence |
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

## Segment 111 - `SEG111-R-002`

**Segment Length format and computation**

| AI requirement ID | Statement | Source rule | Page | Confidence |
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

## Segment 113 - `SEG113-R-005`

**Segment 113 maximum length is 156 alphanumeric characters**

| AI requirement ID | Statement | Source rule | Page | Confidence |
|---|---|---|---:|---|
|REQ-SRC-ATL105-PDF-001:1134|Segment Type field has fixed value 100 for Standard Message Data Segment.|BR-220-5|220|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1165|A Field Separator always follows Segment Type and Segment Length; Field Separator follows Product Amount when it's the last element.|BR-226-12|226|80% HIGH|
|REQ-SRC-ATL105-PDF-001:1226|ECA/TeleCheck Data Segment has a maximum length of 156 alphanumeric characters.|BR-246-2|246|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1335|Field Nos 1&2 and 2&3 are separated by Field Separators; segment should end with a field separator.|BR-274-1|274|90% HIGH|
|REQ-SRC-ATL105-PDF-001:1835|ECA/TeleCheck Data Segment length is 001-156.|BR-400-12|400|57% MEDIUM|

