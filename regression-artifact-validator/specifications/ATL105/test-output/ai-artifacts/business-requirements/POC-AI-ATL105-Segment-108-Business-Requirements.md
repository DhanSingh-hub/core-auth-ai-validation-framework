# POC AI Segment 108 Business Requirements

Source: `step5_requirements/approved/requirement_catalog.json`

Scope: rules whose source rule is explicitly related to ENT-SEG-108 or a direct field of that segment in the approved POC extraction.

Count: 133

| ID | Scope | Source rule | Page | Confidence | Requirement |
|---|---|---|---:|---:|---|
| REQ-SRC-ATL105-PDF-001:460 | SEGMENT_108_FIELD_RULE | BR-79-10 | 79 | 76% | If card cannot be read magnetically after three swipe attempts, device should prompt for manual entry of Account Number and Expiration Date (Format: MMYY). |
| REQ-SRC-ATL105-PDF-001:521 | SEGMENT_108_FIELD_RULE | BR-89-13 | 89 | 51% | Receipts must include account number, expiration date, transaction date/time, sequence number, product, balance, approved/declined message, signature line. |
| REQ-SRC-ATL105-PDF-001:522 | SEGMENT_108_FIELD_RULE | BR-90-1 | 90 | 28% | The Expiration Date must be suppressed on all transaction receipts. |
| REQ-SRC-ATL105-PDF-001:523 | SEGMENT_108_FIELD_RULE | BR-90-1 | 90 | 84% | Account Number must be truncated to last four digits on all transaction receipts. |
| REQ-SRC-ATL105-PDF-001:526 | SEGMENT_108_FIELD_RULE | BR-90-4 | 90 | 84% | Expiration Date must be suppressed on all transaction receipts. |
| REQ-SRC-ATL105-PDF-001:536 | SEGMENT_108_FIELD_RULE | BR-92-1 | 92 | 28% | These two items must be separated by an "=" sign, rather than a Field Separator. |
| REQ-SRC-ATL105-PDF-001:537 | SEGMENT_108_FIELD_RULE | BR-92-1 | 92 | 76% | Device at point of interaction must not display/store card-read data except account number, expiration date, cardholder name. |
| REQ-SRC-ATL105-PDF-001:544 | SEGMENT_108_FIELD_RULE | BR-92-8 | 92 | 70% | Host message should only contain Account Number and Expiration Date (YYMM) in Card Discretionary Block Data fields. |
| REQ-SRC-ATL105-PDF-001:545 | SEGMENT_108_FIELD_RULE | BR-92-9 | 92 | 76% | Account Number and Expiration Date must be separated by an '=' sign rather than a Field Separator. |
| REQ-SRC-ATL105-PDF-001:546 | SEGMENT_108_FIELD_RULE | BR-92-10 | 92 | 71% | Host message should contain Account Number, followed by '=' delimiter, followed by 4-digit Expiration Date (YYMM). |
| REQ-SRC-ATL105-PDF-001:547 | SEGMENT_108_FIELD_RULE | BR-93-1 | 93 | 64% | Card Discretionary Data field follows the 4-digit Expiration Date only for specific credit transactions. |
| REQ-SRC-ATL105-PDF-001:550 | SEGMENT_108_FIELD_RULE | BR-94-1 | 94 | 84% | Device does not perform expiration date validation on debit card; online authorization always required. |
| REQ-SRC-ATL105-PDF-001:600 | SEGMENT_108_FIELD_RULE | BR-103-1 | 103 | 25% | These two items must be separated by an "=" sign, rather than a Field Separator. |
| REQ-SRC-ATL105-PDF-001:601 | SEGMENT_108_FIELD_RULE | BR-103-1 | 103 | 71% | Device must not display/store card-read data except account number, expiration date, and cardholder name. |
| REQ-SRC-ATL105-PDF-001:608 | SEGMENT_108_FIELD_RULE | BR-103-8 | 103 | 70% | Host message should only contain Account Number and Expiration Date (YYMM) in Card Discretionary Block Data field. |
| REQ-SRC-ATL105-PDF-001:609 | SEGMENT_108_FIELD_RULE | BR-103-9 | 103 | 76% | Account Number and Expiration Date must be separated by '=' sign rather than a Field Separator. |
| REQ-SRC-ATL105-PDF-001:610 | SEGMENT_108_FIELD_RULE | BR-103-10 | 103 | 79% | No other Card Discretionary Data field follows the 4-digit Expiration Date for Debit Completions, Reversals, and Cancellations. |
| REQ-SRC-ATL105-PDF-001:651 | SEGMENT_108_FIELD_RULE | BR-110-1 | 110 | 84% | The device does not perform expiration date validation in an EBT card transaction. |
| REQ-SRC-ATL105-PDF-001:654 | SEGMENT_108_FIELD_RULE | BR-110-4 | 110 | 81% | If no expiration date is provided for manually entered EBT account number, use 1249. |
| REQ-SRC-ATL105-PDF-001:659 | SEGMENT_108_FIELD_RULE | BR-110-9 | 110 | 90% | EBT processing supports tender types: Food stamps and Cash benefits. |
| REQ-SRC-ATL105-PDF-001:661 | SEGMENT_108_FIELD_RULE | BR-112-2 | 112 | 71% | Stand-alone equipment electronic voucher transaction must allow manual entry of Account Number and Expiration Date. |
| REQ-SRC-ATL105-PDF-001:709 | SEGMENT_108_FIELD_RULE | BR-119-4 | 119 | 60% | eWIC card receipts must include Payment Tender Type, Transaction Type, Clerk ID, Voucher ID, amounts, balances, approved/declined messages, benefit expiration date. |
| REQ-SRC-ATL105-PDF-001:719 | SEGMENT_108_FIELD_RULE | BR-123-1 | 123 | 28% | an activation (Please see section 10.7.3.6 â€” "Activation Transaction. |
| REQ-SRC-ATL105-PDF-001:720 | SEGMENT_108_FIELD_RULE | BR-123-1 | 123 | 84% | Device does not perform expiration date validation in a stored value card transaction. |
| REQ-SRC-ATL105-PDF-001:722 | SEGMENT_108_FIELD_RULE | BR-123-3 | 123 | 76% | If manual entry allowed and stripe unreadable after 3 tries, prompt manual entry of Account Number and Expiration Date (MMYY). |
| REQ-SRC-ATL105-PDF-001:723 | SEGMENT_108_FIELD_RULE | BR-123-4 | 123 | 79% | First Data Premium Gift Card has no embossed expiration date; default value 1249 (MMYY) sent by POS device. |
| REQ-SRC-ATL105-PDF-001:727 | SEGMENT_108_FIELD_RULE | BR-123-8 | 123 | 76% | Bar Code Scanned Gift card treated as manual entry transaction, sent with Account Number and Expiration Date, entry mode '01'. |
| REQ-SRC-ATL105-PDF-001:779 | SEGMENT_108_FIELD_RULE | BR-129-7 | 129 | 84% | Partial Track 2 Data consists of PAN, '=' sign, then 4-digit expiration date (YYMM), no other data follows. |
| REQ-SRC-ATL105-PDF-001:794 | SEGMENT_108_FIELD_RULE | BR-132-1 | 132 | 28% | The following information must be printed on each copy of Version 2 Denial Receipt. |
| REQ-SRC-ATL105-PDF-001:795 | SEGMENT_108_FIELD_RULE | BR-132-1 | 132 | 54% | Merchant copy of receipt must print customer name, street address, and state/ZIP lines when ECA/TeleCheck used. |
| REQ-SRC-ATL105-PDF-001:799 | SEGMENT_108_FIELD_RULE | BR-133-1 | 133 | 84% | Device does not perform expiration date validation on loyalty card transactions. |
| REQ-SRC-ATL105-PDF-001:800 | SEGMENT_108_FIELD_RULE | BR-133-2 | 133 | 81% | If card cannot be read after three swipe attempts, device prompts for manual entry of Account Number and Expiration Date. |
| REQ-SRC-ATL105-PDF-001:801 | SEGMENT_108_FIELD_RULE | BR-133-3 | 133 | 84% | User may skip Expiration Date prompt when manually keyed and no expiration date present on card. |
| REQ-SRC-ATL105-PDF-001:802 | SEGMENT_108_FIELD_RULE | BR-133-4 | 133 | 84% | When no expiration date is present, device sends default value of 1249 (MMYY). |
| REQ-SRC-ATL105-PDF-001:803 | SEGMENT_108_FIELD_RULE | BR-133-5 | 133 | 72% | When loyalty card is not present, Street Address and Loyalty Phone Number are keyed in lieu of Loyalty Account Number. |
| REQ-SRC-ATL105-PDF-001:804 | DIRECT_SEGMENT_108 | BR-133-6 | 133 | 79% | Loyalty card information can be included in Purchase/Capture, Purchase Reversal, and Time-out Reversal transactions. |
| REQ-SRC-ATL105-PDF-001:805 | SEGMENT_108_FIELD_RULE | BR-134-1 | 134 | 58% | If loyalty card not available for Coupon redemption, clerk enters street number and telephone number instead of Loyalty Account Number. |
| REQ-SRC-ATL105-PDF-001:806 | SEGMENT_108_FIELD_RULE | BR-134-2 | 134 | 55% | If loyalty card not available for Expiration date update, clerk enters street number and telephone number instead of account number. |
| REQ-SRC-ATL105-PDF-001:807 | SEGMENT_108_FIELD_RULE | BR-134-3 | 134 | 58% | If loyalty card not available for Account inquiry, clerk enters street number and telephone number instead of account number. |
| REQ-SRC-ATL105-PDF-001:808 | SEGMENT_108_FIELD_RULE | BR-134-4 | 134 | 58% | If loyalty card not available for Points redemption, clerk enters street number and telephone number instead of Loyalty Account Number. |
| REQ-SRC-ATL105-PDF-001:809 | SEGMENT_108_FIELD_RULE | BR-134-5 | 134 | 58% | Reversal of coupon redeem requires Coupon ID, Coupon Amount, and approval number of previous transaction receipt. |
| REQ-SRC-ATL105-PDF-001:810 | SEGMENT_108_FIELD_RULE | BR-134-6 | 134 | 58% | If loyalty card not available for Reversal of coupon redeem, clerk enters street number and telephone number instead of account number. |
| REQ-SRC-ATL105-PDF-001:811 | SEGMENT_108_FIELD_RULE | BR-134-7 | 134 | 64% | Reversal of points redeemed requires point amount and approval number of previous transaction receipt. |
| REQ-SRC-ATL105-PDF-001:812 | SEGMENT_108_FIELD_RULE | BR-134-8 | 134 | 58% | If loyalty card not available for Reversal of points redeemed, clerk enters street number and telephone number instead of account number. |
| REQ-SRC-ATL105-PDF-001:813 | SEGMENT_108_FIELD_RULE | BR-134-9 | 134 | 54% | Add account transaction requires clerk to swipe new loyalty card and enter customer's street number and telephone number. |
| REQ-SRC-ATL105-PDF-001:815 | SEGMENT_108_FIELD_RULE | BR-135-1 | 135 | 58% | If loyalty card is not available, clerk enters street number and telephone number instead of Loyalty Account Number. |
| REQ-SRC-ATL105-PDF-001:816 | SEGMENT_108_FIELD_RULE | BR-135-2 | 135 | 70% | Account update transaction requires clerk to enter both new street number and new telephone number. |
| REQ-SRC-ATL105-PDF-001:819 | SEGMENT_108_FIELD_RULE | BR-135-5 | 135 | 54% | Any loyalty information received by the device from Table 008 or Table 010 layouts is printed. |
| REQ-SRC-ATL105-PDF-001:920 | SEGMENT_108_FIELD_RULE | BR-157-7 | 157 | 79% | The device does not perform expiration date validation in an EMV card transaction. |
| REQ-SRC-ATL105-PDF-001:921 | SEGMENT_108_FIELD_RULE | BR-157-8 | 157 | 71% | If the account number is keyed, device prompts for Expiration Date (MMYY) and other optional/conditional data. |
| REQ-SRC-ATL105-PDF-001:971 | DIRECT_SEGMENT_108 | BR-171-3 | 171 | 64% | Loyalty Card Transaction Request Data Section 3 may contain none, one, or more of Loyalty Card Data Segment or SKU Data Segment. |
| REQ-SRC-ATL105-PDF-001:973 | DIRECT_SEGMENT_108 | BR-172-2 | 172 | 90% | Loyalty Card Data Segment is required for all loyalty card transactions. |
| REQ-SRC-ATL105-PDF-001:1134 | SEGMENT_108_FIELD_RULE | BR-220-5 | 220 | 90% | Segment Type field has fixed value 100 for Standard Message Data Segment. |
| REQ-SRC-ATL105-PDF-001:1135 | SEGMENT_108_FIELD_RULE | BR-220-6 | 220 | 85% | Segment Length includes Segment Type's length and Field Separators. |
| REQ-SRC-ATL105-PDF-001:1146 | SEGMENT_108_FIELD_RULE | BR-223-4 | 223 | 90% | Segment Type field has fixed value 101 identifying the Fleet Data Segment. |
| REQ-SRC-ATL105-PDF-001:1165 | SEGMENT_108_FIELD_RULE | BR-226-12 | 226 | 80% | A Field Separator always follows Segment Type and Segment Length; Field Separator follows Product Amount when it's the last element. |
| REQ-SRC-ATL105-PDF-001:1178 | SEGMENT_108_FIELD_RULE | BR-229-3 | 229 | 90% | Segment Length should be 4 for EBT with eWIC data transactions. |
| REQ-SRC-ATL105-PDF-001:1187 | SEGMENT_108_FIELD_RULE | BR-232-6 | 232 | 90% | Segment Type field has a fixed value of 105 in Totals Data Segment. |
| REQ-SRC-ATL105-PDF-001:1190 | DIRECT_SEGMENT_108 | BR-235-1 | 235 | 25% | a field is not populated â€” still send the Field Separator. |
| REQ-SRC-ATL105-PDF-001:1191 | DIRECT_SEGMENT_108 | BR-235-1 | 235 | 80% | All fields separated by Field Separators; Field Separator follows Field No. 13 even if unpopulated. |
| REQ-SRC-ATL105-PDF-001:1192 | DIRECT_SEGMENT_108 | BR-235-2 | 235 | 90% | Loyalty Card Data Segment has maximum length of 142 alphanumeric characters. |
| REQ-SRC-ATL105-PDF-001:1193 | SEGMENT_108_FIELD_RULE | BR-235-3 | 235 | 48% | Loyalty Account Number is conditional (C) rather than always required. |
| REQ-SRC-ATL105-PDF-001:1194 | SEGMENT_108_FIELD_RULE | BR-235-4 | 235 | 41% | Update Code field presence is Conditional depending on loyalty function performed. |
| REQ-SRC-ATL105-PDF-001:1195 | SEGMENT_108_FIELD_RULE | BR-235-5 | 235 | 41% | Street Address field presence is Conditional. |
| REQ-SRC-ATL105-PDF-001:1196 | SEGMENT_108_FIELD_RULE | BR-236-1 | 236 | 64% | Expiration Date is reserved for future use. |
| REQ-SRC-ATL105-PDF-001:1197 | SEGMENT_108_FIELD_RULE | BR-236-2 | 236 | 64% | Payment Tender Type used to determine whether transaction is a multiple-card transaction involving a loyalty card. |
| REQ-SRC-ATL105-PDF-001:1215 | SEGMENT_108_FIELD_RULE | BR-243-3 | 243 | 85% | A Field Separator exists between Field Nos. 1 and 2 and between Field No. 2 and Variable Information Section. |
| REQ-SRC-ATL105-PDF-001:1218 | SEGMENT_108_FIELD_RULE | BR-243-6 | 243 | 90% | Segment Type (Field No. 1) has fixed value 111 identifying the Variable Information Data Segment. |
| REQ-SRC-ATL105-PDF-001:1224 | SEGMENT_108_FIELD_RULE | BR-245-2 | 245 | 90% | Segment Type field has fixed value 112 for Additional Information Data Segment. |
| REQ-SRC-ATL105-PDF-001:1238 | SEGMENT_108_FIELD_RULE | BR-249-5 | 249 | 85% | There is a Field Separator between Field Nos. 1 and 2 and between Field Nos. 2 and 3. |
| REQ-SRC-ATL105-PDF-001:1246 | SEGMENT_108_FIELD_RULE | BR-251-7 | 251 | 90% | Segment Type fixed value is 118 for the Proprietary Data Load Segment. |
| REQ-SRC-ATL105-PDF-001:1282 | SEGMENT_108_FIELD_RULE | BR-259-2 | 259 | 70% | Fuel Volume Data field length is determined by segment length in field 2, up to max 3,600. |
| REQ-SRC-ATL105-PDF-001:1291 | SEGMENT_108_FIELD_RULE | BR-260-7 | 260 | 90% | Segment Type fixed value is 119. |
| REQ-SRC-ATL105-PDF-001:1301 | SEGMENT_108_FIELD_RULE | BR-263-4 | 263 | 90% | A Field Separator exists between Field Nos. 1 and 2 and between Field Nos. 2 and 3. |
| REQ-SRC-ATL105-PDF-001:1323 | SEGMENT_108_FIELD_RULE | BR-269-5 | 269 | 90% | Segment Type field has fixed value 131 for the EMV Response Data Segment. |
| REQ-SRC-ATL105-PDF-001:1330 | SEGMENT_108_FIELD_RULE | BR-273-3 | 273 | 90% | Segment Type field fixed value is 134. |
| REQ-SRC-ATL105-PDF-001:1335 | SEGMENT_108_FIELD_RULE | BR-274-1 | 274 | 90% | Field Nos 1&2 and 2&3 are separated by Field Separators; segment should end with a field separator. |
| REQ-SRC-ATL105-PDF-001:1337 | SEGMENT_108_FIELD_RULE | BR-274-3 | 274 | 90% | Segment Type field (Element 85) has fixed value 135 for Moneris Data Segment (Request). |
| REQ-SRC-ATL105-PDF-001:1340 | SEGMENT_108_FIELD_RULE | BR-276-1 | 276 | 90% | Segment Type field must have fixed value 139 for Moneris Day End Batch Balance Request Segment. |
| REQ-SRC-ATL105-PDF-001:1343 | SEGMENT_108_FIELD_RULE | BR-277-2 | 277 | 90% | Segment Type field 1 must have fixed value 140. |
| REQ-SRC-ATL105-PDF-001:1346 | SEGMENT_108_FIELD_RULE | BR-279-3 | 279 | 90% | Segment Type fixed value is 141 for Moneris Day End Batch Close Request segment. |
| REQ-SRC-ATL105-PDF-001:1356 | SEGMENT_108_FIELD_RULE | BR-282-8 | 282 | 74% | A Field Separator always follows Segment Type and Segment Length, and the last Tax Amount per product. |
| REQ-SRC-ATL105-PDF-001:1409 | SEGMENT_108_FIELD_RULE | BR-295-4 | 295 | 90% | Segment Type field has fixed value 146 for Enhanced Fleet Response Segment. |
| REQ-SRC-ATL105-PDF-001:1433 | SEGMENT_108_FIELD_RULE | BR-303-1 | 303 | 80% | Fields 1-2, 2-3, and 3-4 are separated by Field Separators; segment ends with a field separator. |
| REQ-SRC-ATL105-PDF-001:1435 | SEGMENT_108_FIELD_RULE | BR-303-3 | 303 | 90% | Segment Type field has fixed value 149 for Fuel Price Update Request Segment. |
| REQ-SRC-ATL105-PDF-001:1436 | SEGMENT_108_FIELD_RULE | BR-304-1 | 304 | 90% | Segment Type field 1 has fixed value 150 for Fuel Price Update Response Segment. |
| REQ-SRC-ATL105-PDF-001:1437 | SEGMENT_108_FIELD_RULE | BR-304-2 | 304 | 84% | Segment Length includes Segment Type's length and Field Separators. |
| REQ-SRC-ATL105-PDF-001:1448 | SEGMENT_108_FIELD_RULE | BR-308-1 | 308 | 18% | Table ID for Network Token Expiration Date layout is fixed value 002 with Table Length fixed at 004. |
| REQ-SRC-ATL105-PDF-001:1459 | SEGMENT_108_FIELD_RULE | BR-311-4 | 311 | 74% | Account Updater Expiration Date only included when merchant sent Account Updater Request Indicator = Y. |
| REQ-SRC-ATL105-PDF-001:1460 | SEGMENT_108_FIELD_RULE | BR-311-5 | 311 | 74% | Account Updater Expiration Date is applicable only to Visa and MasterCard. |
| REQ-SRC-ATL105-PDF-001:1521 | SEGMENT_108_FIELD_RULE | BR-320-12 | 320 | 70% | A Field Separator always follows Segment Type and Segment Length; sent even when field not populated. |
| REQ-SRC-ATL105-PDF-001:1564 | SEGMENT_108_FIELD_RULE | BR-332-2 | 332 | 70% | Segment Length Indicator excludes the Data Type Indicator from its length calculation. |
| REQ-SRC-ATL105-PDF-001:1613 | SEGMENT_108_FIELD_RULE | BR-359-2 | 359 | 76% | For Loyalty Totals Request with Update Code = 'T', Account Number field must contain 'TOTALS REQUEST'. |
| REQ-SRC-ATL105-PDF-001:1614 | SEGMENT_108_FIELD_RULE | BR-359-3 | 359 | 63% | For other loyalty advice transactions, Account Number must contain the actual Loyalty Account Number. |
| REQ-SRC-ATL105-PDF-001:1646 | SEGMENT_108_FIELD_RULE | BR-364-5 | 364 | 73% | For either TransArmor processing method, the expiration date must be included in Card Discretionary Block Data. |
| REQ-SRC-ATL105-PDF-001:1647 | SEGMENT_108_FIELD_RULE | BR-364-6 | 364 | 73% | When merchant receives Account Updater Expiration Date in Segment 155, follow-on transactions may send new or original expiration date in this field. |
| REQ-SRC-ATL105-PDF-001:1648 | SEGMENT_108_FIELD_RULE | BR-364-7 | 364 | 76% | Merchant should send Expiration Date corresponding to the PAN or TransArmor Token sent in Account Number (Data Element 2, Segment 100). |
| REQ-SRC-ATL105-PDF-001:1650 | SEGMENT_108_FIELD_RULE | BR-365-2 | 365 | 74% | For manually entered card number, expiration date must be in MMYY format. |
| REQ-SRC-ATL105-PDF-001:1651 | SEGMENT_108_FIELD_RULE | BR-365-3 | 365 | 79% | Merchants must send an expiration date value even for expired cards, for all authorizations. |
| REQ-SRC-ATL105-PDF-001:1654 | SEGMENT_108_FIELD_RULE | BR-365-6 | 365 | 74% | For either TransArmor processing method, expiration date must be included and stored for follow-on transactions with a Token. |
| REQ-SRC-ATL105-PDF-001:1655 | SEGMENT_108_FIELD_RULE | BR-365-7 | 365 | 79% | For Comdata Express Code or Check request (card type 095/096), expiration date field should be skipped but separators included. |
| REQ-SRC-ATL105-PDF-001:1656 | SEGMENT_108_FIELD_RULE | BR-365-8 | 365 | 79% | For Money Code/Check request (card type 093), expiration date field should be skipped but separators included. |
| REQ-SRC-ATL105-PDF-001:1657 | SEGMENT_108_FIELD_RULE | BR-365-9 | 365 | 79% | Expiration date is mandatory for Accel/STAR e-commerce and one-time bill payment transactions. |
| REQ-SRC-ATL105-PDF-001:1658 | SEGMENT_108_FIELD_RULE | BR-365-10 | 365 | 79% | Effective May 1, 2025, expiration date is optional for Accel/STAR COF transactions (bill payment, recurring, installment). |
| REQ-SRC-ATL105-PDF-001:1707 | SEGMENT_108_FIELD_RULE | BR-378-4 | 378 | 76% | For fuel-only transactions with discounts/coupons, Fuel Purchase Amount equals fuel product sum minus discount/coupon amount. |
| REQ-SRC-ATL105-PDF-001:1735 | DIRECT_SEGMENT_108 | BR-384-6 | 384 | 85% | For Loyalty Card Request messages, Element 63 (Number of Segments) and Segment 108 (Loyalty Card Data Segment) always follow Element 55. |
| REQ-SRC-ATL105-PDF-001:1755 | DIRECT_SEGMENT_108 | BR-388-2 | 388 | 90% | Loyalty Card Transaction Requests: Number of Segments followed by Segment 100, Segment 108, and Segment 114. |
| REQ-SRC-ATL105-PDF-001:1821 | SEGMENT_108_FIELD_RULE | BR-399-1 | 399 | 90% | Segment Length has four digits only for segments 103, 114, 115, 118, 120, 130, and 131. |
| REQ-SRC-ATL105-PDF-001:1822 | SEGMENT_108_FIELD_RULE | BR-399-2 | 399 | 90% | Segment Length cannot have a length of 4 digits when sending any segment other than the listed seven. |
| REQ-SRC-ATL105-PDF-001:1823 | SEGMENT_108_FIELD_RULE | BR-399-3 | 399 | 80% | Segment Length is calculated using lengths of all data elements plus Field Separators/Product Data Field Delimiters in the segment. |
| REQ-SRC-ATL105-PDF-001:1830 | DIRECT_SEGMENT_108 | BR-400-7 | 400 | 57% | Loyalty Card Data Segment length is 001-142. |
| REQ-SRC-ATL105-PDF-001:1848 | SEGMENT_108_FIELD_RULE | BR-401-1 | 401 | 90% | Segment Type is required for the TransArmor PKI Encryption and Tokenization Load Request. |
| REQ-SRC-ATL105-PDF-001:1849 | SEGMENT_108_FIELD_RULE | BR-401-2 | 401 | 90% | Segment Type value 106 and 107 are reserved for proprietary use. |
| REQ-SRC-ATL105-PDF-001:1997 | SEGMENT_108_FIELD_RULE | BR-430-5 | 430 | 81% | When Additional Information Indicator = 013, Expiration Date (MMYY) is included, fixed length 4 bytes. |
| REQ-SRC-ATL105-PDF-001:2051 | DIRECT_SEGMENT_108 | BR-439-2 | 439 | 61% | Loyalty Program ID is required on all loyalty card transactions. |
| REQ-SRC-ATL105-PDF-001:2052 | DIRECT_SEGMENT_108 | BR-440-1 | 440 | 78% | Loyalty Account Number is required on all loyalty card transactions. |
| REQ-SRC-ATL105-PDF-001:2053 | SEGMENT_108_FIELD_RULE | BR-443-1 | 443 | 84% | If Payment Tender Type is not 'CS', two cards must be swiped: a loyalty card and a payment card. |
| REQ-SRC-ATL105-PDF-001:2054 | SEGMENT_108_FIELD_RULE | BR-444-1 | 444 | 84% | If Loyalty Information Version not sent in a transaction request, value defaults to 1. |
| REQ-SRC-ATL105-PDF-001:2055 | SEGMENT_108_FIELD_RULE | BR-444-2 | 444 | 84% | Unit of Work is required on loyalty reversals to match original purchase information. |
| REQ-SRC-ATL105-PDF-001:2061 | SEGMENT_108_FIELD_RULE | BR-446-2 | 446 | 74% | Earliest WIC Benefit Expiration Date is included in eWIC Balance Inquiry, Authorization, and Purchase Completion Responses. |
| REQ-SRC-ATL105-PDF-001:2068 | SEGMENT_108_FIELD_RULE | BR-447-2 | 447 | 18% | Earliest WIC Benefit Expiration Date format is CCYYMMDD, 8 bytes. |
| REQ-SRC-ATL105-PDF-001:2251 | SEGMENT_108_FIELD_RULE | BR-499-1 | 499 | 70% | Segment Length value (078) identifies the length in bytes of the Standard Message Data Segment, including field separators. |
| REQ-SRC-ATL105-PDF-001:2256 | SEGMENT_108_FIELD_RULE | BR-499-6 | 499 | 48% | Card Discretionary Block Data identifies card expiration date used in manual entry, max length 51. |
| REQ-SRC-ATL105-PDF-001:2263 | SEGMENT_108_FIELD_RULE | BR-503-3 | 503 | 70% | Segment Length field identifies the length (017 bytes) of the Variable Information Data Segment, including Field Separators. |
| REQ-SRC-ATL105-PDF-001:2319 | SEGMENT_108_FIELD_RULE | BR-547-1 | 547 | 18% | AVS Information Table Data has max length 29, first 9 characters are ZIP or ZIP+4. |
| REQ-SRC-ATL105-PDF-001:2731 | SEGMENT_108_FIELD_RULE | BR-647-4 | 647 | 41% | Loyalty Information Unit of Work maximum length is 19 numeric. |
| REQ-SRC-ATL105-PDF-001:2732 | SEGMENT_108_FIELD_RULE | BR-648-1 | 648 | 18% | If no value has been stored at the device, default value of 1249 is sent for Card Expiration Date. |
| REQ-SRC-ATL105-PDF-001:2733 | SEGMENT_108_FIELD_RULE | BR-648-2 | 648 | 18% | Depending on transaction context, Card Expiration Date value can be either MMYY or YYMM format. |
| REQ-SRC-ATL105-PDF-001:2838 | SEGMENT_108_FIELD_RULE | BR-679-7 | 679 | 54% | Shipping address DETAIL consists of 28-character street address followed by 9-digit zip code. |
| REQ-SRC-ATL105-PDF-001:2846 | SEGMENT_108_FIELD_RULE | BR-681-7 | 681 | 54% | Payment Token Expiration Date is populated in the PAN Expiration Date field during transaction processing. |
| REQ-SRC-ATL105-PDF-001:2992 | SEGMENT_108_FIELD_RULE | BR-710-4 | 710 | 73% | On all subsequent transactions, unencrypted expiration date should be sent in Data Element 12, Card Discretionary Block Data. |
| REQ-SRC-ATL105-PDF-001:2997 | SEGMENT_108_FIELD_RULE | BR-711-3 | 711 | 74% | Encrypted data for a manually entered PAN should be followed by <fs> and unencrypted MMYY expiration date. |
| REQ-SRC-ATL105-PDF-001:3003 | SEGMENT_108_FIELD_RULE | BR-712-1 | 712 | 74% | For TransArmor processing, expiration date must be stored for subsequent transactions with a token. |
