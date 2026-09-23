# POC AI Segment 102 Business Requirements

Source: `step5_requirements/approved/requirement_catalog.json`

Scope: rules whose source rule is explicitly related to ENT-SEG-102 or a direct field of that segment in the approved POC extraction.

Count: 192

| ID | Scope | Source rule | Page | Confidence | Requirement |
|---|---|---|---:|---:|---|
| REQ-SRC-ATL105-PDF-001:247 | SEGMENT_102_FIELD_RULE | BR-4-1 | 4 | 64% | Value 'H' was added to data element 87 (Service Level) under Data Elements in Data Element Number Order. |
| REQ-SRC-ATL105-PDF-001:254 | SEGMENT_102_FIELD_RULE | BR-7-4 | 7 | 74% | Adjusted Product Code Data Segment (No. 157) added; data elements 76, 77, 81, 84, 85, 87, 106, and 107 updated with references to data segment 157. |
| REQ-SRC-ATL105-PDF-001:260 | DIRECT_SEGMENT_102 | BR-10-4 | 10 | 64% | EV charging transactions information added in Product Code Data Segment; data segment '156' (EV Charging Data Segment) added under Data Segment Formats. |
| REQ-SRC-ATL105-PDF-001:265 | SEGMENT_102_FIELD_RULE | BR-11-4 | 11 | 79% | Data element 106 (Unit of Measure) supports values 'M' and 'W'. |
| REQ-SRC-ATL105-PDF-001:392 | SEGMENT_102_FIELD_RULE | BR-60-5 | 60 | 88% | The controller must supply the Preauthorization amount, anticipated fuel type, and price per gallon; these are required for fuel limits and fuel-only card validation, and are overridden when actual fuel is dispensed. |
| REQ-SRC-ATL105-PDF-001:486 | SEGMENT_102_FIELD_RULE | BR-86-1 | 86 | 64% | Total QHP Amount (Product Code 894) is required and must include all Clinical Services, Dental Services, Prescription/Rx, and Vision/Optical amounts, including tax and shipping less discounts. |
| REQ-SRC-ATL105-PDF-001:487 | SEGMENT_102_FIELD_RULE | BR-86-2 | 86 | 74% | Product Codes 890 (Clinical), 891 (Dental), 892 (Prescription/Rx), 893 (Vision/Optical), 895 (Co-Payment), and 896 (Transit) are optional in QHP messages. |
| REQ-SRC-ATL105-PDF-001:490 | DIRECT_SEGMENT_102 | BR-87-3 | 87 | 95% | FSA/HRA transaction requests must include Product Code in the Product Code Data Segment (No. 102). |
| REQ-SRC-ATL105-PDF-001:492 | DIRECT_SEGMENT_102 | BR-87-5 | 87 | 95% | The Partial Approval Indicator and Product Code Data Segment fields are required in a FSA/HRA Purchase/Capture transaction. |
| REQ-SRC-ATL105-PDF-001:518 | SEGMENT_102_FIELD_RULE | BR-90-8 | 90 | 90% | For an approved transaction receipt, fuel product information (fuel type, quantity in gallons, price per gallon, total fuel purchase amount) and nonfuel product information (number of units, unit price, total product purchase amount) must be printed. |
| REQ-SRC-ATL105-PDF-001:567 | SEGMENT_102_FIELD_RULE | BR-99-1 | 99 | 69% | Total QHP Amount (Product Code 894) is required and must equal the sum of Clinical Services (890), Dental Services (891), Prescription/Rx (892), and Vision/Optical (893) amounts, including tax and shipping less discounts. |
| REQ-SRC-ATL105-PDF-001:568 | SEGMENT_102_FIELD_RULE | BR-99-2 | 99 | 54% | Qualified medical expenses are generally items that qualify for IRS medical and dental expense deductions; eligible items are categorized by OTC grouping and brand name. |
| REQ-SRC-ATL105-PDF-001:569 | DIRECT_SEGMENT_102 | BR-100-1 | 100 | 90% | FSA/HRA transaction requests must include Market-Specific Data Indicator (MSDI) in Variable Information Data Segment 111, Partial Approval Indicator in Standard Message Data Segment 100, and Product Code in Product Code Data Segment 102. |
| REQ-SRC-ATL105-PDF-001:571 | DIRECT_SEGMENT_102 | BR-100-3 | 100 | 95% | Partial Approval Indicator and Product Code Data Segment are required in an FSA/HRA Purchase/Capture transaction. |
| REQ-SRC-ATL105-PDF-001:923 | DIRECT_SEGMENT_102 | BR-163-3 | 163 | 90% | Data Section No. 3 of a Financial Transaction Request contains none, one, or more of the following data segments: Fleet (101), Product Code (102), EBT (103), Purchase Card (104), Variable Information (111), per the Data Section No. 3 table. |
| REQ-SRC-ATL105-PDF-001:928 | DIRECT_SEGMENT_102 | BR-165-2 | 165 | 90% | Product Code Data Segment (102) is sent only on transactions requiring product data. |
| REQ-SRC-ATL105-PDF-001:934 | DIRECT_SEGMENT_102 | BR-165-8 | 165 | 90% | Tax by Product Data Segment (143) contains tax data for each product included in segment 102 and should only be included if instructed by your account manager. |
| REQ-SRC-ATL105-PDF-001:936 | DIRECT_SEGMENT_102 | BR-165-10 | 165 | 85% | Segments 101â€“145 in Financial Transaction Request occupy Field Nos. 4â€“9 with specified maximum lengths (101:308, 102:381, 103:3334, 104:86, 111:999, 123:186, 135:110, 143:399, 145:999). |
| REQ-SRC-ATL105-PDF-001:1044 | DIRECT_SEGMENT_102 | BR-201-4 | 201 | 90% | EMV Financial Transaction Request Data Section No. 3 contains one or more of: Fleet (101), Product Code (102), Purchase Card (104), Variable Information (111), EMV Request (130). |
| REQ-SRC-ATL105-PDF-001:1050 | DIRECT_SEGMENT_102 | BR-203-4 | 203 | 85% | Segment 143 (Tax by Product Data Segment) has limited applicability and should only be included if instructed by the account manager; it contains tax data for each product included in segment 102. |
| REQ-SRC-ATL105-PDF-001:1052 | DIRECT_SEGMENT_102 | BR-203-6 | 203 | 90% | Segment 102 (Product Code Data Segment) is sent only on transactions requiring product data. |
| REQ-SRC-ATL105-PDF-001:1072 | SEGMENT_102_FIELD_RULE | BR-211-5 | 211 | 95% | Segment Type field 3 of Data Segment 132 has fixed value 132. |
| REQ-SRC-ATL105-PDF-001:1089 | SEGMENT_102_FIELD_RULE | BR-220-4 | 220 | 97% | Field 1 Segment Type in Segment 100 has fixed value 100. |
| REQ-SRC-ATL105-PDF-001:1090 | SEGMENT_102_FIELD_RULE | BR-220-5 | 220 | 90% | Segment Length includes the Segment Type's length and Field Separators. |
| REQ-SRC-ATL105-PDF-001:1095 | SEGMENT_102_FIELD_RULE | BR-223-5 | 223 | 95% | Segment Type field in the Fleet Data Segment has fixed value 101. |
| REQ-SRC-ATL105-PDF-001:1096 | SEGMENT_102_FIELD_RULE | BR-223-6 | 223 | 90% | Segment Length field identifies the data segment's length, including the Segment Type field's length and Field Separators. |
| REQ-SRC-ATL105-PDF-001:1101 | DIRECT_SEGMENT_102 | BR-226-1 | 226 | 28% | A unique Product Code must be sent for each type of fuel purchase. |
| REQ-SRC-ATL105-PDF-001:1102 | DIRECT_SEGMENT_102 | BR-226-2 | 226 | 28% | a field is not populated â€” still send the Field Separator. |
| REQ-SRC-ATL105-PDF-001:1103 | DIRECT_SEGMENT_102 | BR-226-3 | 226 | 28% | a field is not populated â€” still send the Product Data Field Delimiter. |
| REQ-SRC-ATL105-PDF-001:1104 | DIRECT_SEGMENT_102 | BR-226-1 | 226 | 90% | Segment 102 (Product Code Data Segment) can be used for all card types except Comdata cards. |
| REQ-SRC-ATL105-PDF-001:1105 | DIRECT_SEGMENT_102 | BR-226-2 | 226 | 95% | Segment 102 (Product Code Data Segment) is mutually exclusive with Segment 157 (Adjusted Product Code Data Segment). If both are sent, the transaction will be declined. |
| REQ-SRC-ATL105-PDF-001:1106 | DIRECT_SEGMENT_102 | BR-226-3 | 226 | 95% | When a Financial Transaction request includes the Product Code Data Segment, fuel products must always be the first products in the segment. |
| REQ-SRC-ATL105-PDF-001:1107 | DIRECT_SEGMENT_102 | BR-226-4 | 226 | 95% | For EV charging transactions, the EV fuel product code should be the first product code in the Product Code Data Segment. |
| REQ-SRC-ATL105-PDF-001:1108 | DIRECT_SEGMENT_102 | BR-226-5 | 226 | 98% | A maximum of ten products is allowed in the Product Code Data Segment. |
| REQ-SRC-ATL105-PDF-001:1109 | DIRECT_SEGMENT_102 | BR-226-6 | 226 | 97% | The total of product amounts in Segment 102 must equal the sum of Element 41 (Fuel Purchase Amount) + Element 58 (Nonfuel Amount) + Element 99 (Tax Amount) + Element 17 (Cash Amount) in Segment 100. |
| REQ-SRC-ATL105-PDF-001:1110 | DIRECT_SEGMENT_102 | BR-226-7 | 226 | 95% | When tax product codes are included in Segment 102, the total dollar amount of tax products must also be reported in Element 99 (Tax Amount) in Segment 100. |
| REQ-SRC-ATL105-PDF-001:1111 | DIRECT_SEGMENT_102 | BR-226-8 | 226 | 95% | All fuel merchants must send fuel and nonfuel product data. |
| REQ-SRC-ATL105-PDF-001:1112 | SEGMENT_102_FIELD_RULE | BR-226-9 | 226 | 89% | A unique Product Code must be sent for each type of fuel purchase. |
| REQ-SRC-ATL105-PDF-001:1113 | DIRECT_SEGMENT_102 | BR-226-10 | 226 | 90% | For multi-fuel transactions (OTR), the merchant must send the primary fuel (used for the primary fuel need of the truck/vehicle) as the very first product code within the Product segment, and include all fuel codes and any non-fuel codes with quantity, unit of measure, unit price, and product amount. |
| REQ-SRC-ATL105-PDF-001:1114 | DIRECT_SEGMENT_102 | BR-226-11 | 226 | 98% | The Product Code Data Segment has a maximum length of 381 alphanumeric characters (001-381/a-z/A-Z). |
| REQ-SRC-ATL105-PDF-001:1115 | DIRECT_SEGMENT_102 | BR-226-12 | 226 | 90% | A Field Separator always follows Segment Type and Segment Length. A Field Separator follows Product Amount when Product Amount is the very last element in the segment. When a field is not populated, still send the Field Separator. |
| REQ-SRC-ATL105-PDF-001:1116 | SEGMENT_102_FIELD_RULE | BR-226-13 | 226 | 90% | A Product Data Field Delimiter always follows Quantity and Unit Price. A Product Data Field Delimiter follows Product Amount when Product Amount is not the last element in the segment. When a field is not populated, still send the Product Data Field Delimiter. |
| REQ-SRC-ATL105-PDF-001:1117 | DIRECT_SEGMENT_102 | BR-226-14 | 226 | 90% | Segment 102 originates at the device and can appear in any of the fields in Data Section No. 3. |
| REQ-SRC-ATL105-PDF-001:1118 | DIRECT_SEGMENT_102 | BR-227-1 | 227 | 25% | A unique Product Code must be sent for each type of fuel purchase. |
| REQ-SRC-ATL105-PDF-001:1119 | DIRECT_SEGMENT_102 | BR-227-1 | 227 | 95% | The Product Data section is repeated by product for a maximum of 10 products, for a total variable length up to 370 bytes. |
| REQ-SRC-ATL105-PDF-001:1120 | DIRECT_SEGMENT_102 | BR-227-2 | 227 | 95% | Fuel products must always be the first products in the Product Code Data Segment. |
| REQ-SRC-ATL105-PDF-001:1121 | SEGMENT_102_FIELD_RULE | BR-227-3 | 227 | 89% | A unique Product Code must be sent for each type of fuel purchase. |
| REQ-SRC-ATL105-PDF-001:1122 | SEGMENT_102_FIELD_RULE | BR-227-4 | 227 | 95% | Quantity value must contain a digit for every position of assumed decimal places (e.g., quantity 0.05 with 2 assumed decimals must be '205'; '25' is invalid). |
| REQ-SRC-ATL105-PDF-001:1123 | SEGMENT_102_FIELD_RULE | BR-227-5 | 227 | 90% | Negative Product Codes identify applicable discounts and/or coupons rather than products sold. |
| REQ-SRC-ATL105-PDF-001:1124 | SEGMENT_102_FIELD_RULE | BR-228-1 | 228 | 95% | Unit Price value must contain a digit for every position of assumed decimal places; e.g., $0.059 with 3 assumed decimal places must be represented as '3059', and '359' is invalid. |
| REQ-SRC-ATL105-PDF-001:1125 | SEGMENT_102_FIELD_RULE | BR-228-2 | 228 | 95% | A Field Separator follows Product Amount if Product Amount is the last element in the segment; otherwise a Product Data Field Delimiter follows Product Amount. |
| REQ-SRC-ATL105-PDF-001:1128 | SEGMENT_102_FIELD_RULE | BR-229-3 | 229 | 95% | Segment Length should be '4' for EBT with eWIC data transactions. |
| REQ-SRC-ATL105-PDF-001:1131 | SEGMENT_102_FIELD_RULE | BR-229-6 | 229 | 98% | Segment Type is a fixed value of 103 for the EBT Data Segment. |
| REQ-SRC-ATL105-PDF-001:1135 | SEGMENT_102_FIELD_RULE | BR-230-4 | 230 | 95% | Segment Type field is fixed value 104 for the Purchase Card Data Segment. |
| REQ-SRC-ATL105-PDF-001:1141 | SEGMENT_102_FIELD_RULE | BR-232-5 | 232 | 95% | For Totals Data Segment, Segment Type (Field 1) has fixed value 105. |
| REQ-SRC-ATL105-PDF-001:1148 | SEGMENT_102_FIELD_RULE | BR-235-4 | 235 | 95% | Segment Type in Loyalty Card Data Segment is fixed value 108. |
| REQ-SRC-ATL105-PDF-001:1153 | SEGMENT_102_FIELD_RULE | BR-237-5 | 237 | 95% | Segment Type (field 1) has fixed value 109 for the Electronic Mail Data Segment. |
| REQ-SRC-ATL105-PDF-001:1167 | SEGMENT_102_FIELD_RULE | BR-243-2 | 243 | 95% | Segment Type for Data Segment No. 111 has fixed value 111. |
| REQ-SRC-ATL105-PDF-001:1174 | SEGMENT_102_FIELD_RULE | BR-245-2 | 245 | 95% | Segment Type in segment 112 must be the fixed value 112. |
| REQ-SRC-ATL105-PDF-001:1180 | SEGMENT_102_FIELD_RULE | BR-246-5 | 246 | 95% | Segment Type (field 1) is fixed value 113 for the ECA/TeleCheck Data Segment. |
| REQ-SRC-ATL105-PDF-001:1190 | SEGMENT_102_FIELD_RULE | BR-249-5 | 249 | 95% | Segment Type field is a fixed value of 115 for the Print Data Segment. |
| REQ-SRC-ATL105-PDF-001:1196 | SEGMENT_102_FIELD_RULE | BR-251-5 | 251 | 98% | Segment Type field for Proprietary Data Load Segment has a fixed value of 118. |
| REQ-SRC-ATL105-PDF-001:1212 | SEGMENT_102_FIELD_RULE | BR-255-1 | 255 | 95% | When Prompt Code = 902 (Dynamic Card Table Data), Field 14 contains Card Table Data (max 3,600 bytes, Required); its length is determined by the segment length in field 2. |
| REQ-SRC-ATL105-PDF-001:1222 | DIRECT_SEGMENT_102 | BR-257-2 | 257 | 95% | The amount of the discount in a Financial Transaction Request is included in the Product Code Data Segment (Segment No. 102) using Product Code 941 or 991. |
| REQ-SRC-ATL105-PDF-001:1223 | SEGMENT_102_FIELD_RULE | BR-257-3 | 257 | 95% | Product Code 941 indicates a negative amount and is used in a post-pay environment when the discount is applied to the transaction amount. |
| REQ-SRC-ATL105-PDF-001:1224 | SEGMENT_102_FIELD_RULE | BR-257-4 | 257 | 95% | Product Code 991 indicates an administrative amount and is used in a pre-pay environment when the price is adjusted to include the discount amount. |
| REQ-SRC-ATL105-PDF-001:1235 | SEGMENT_102_FIELD_RULE | BR-260-4 | 260 | 95% | In segment 119, Segment Type has fixed value 119. |
| REQ-SRC-ATL105-PDF-001:1248 | SEGMENT_102_FIELD_RULE | BR-263-6 | 263 | 95% | Segment Type field contains fixed value '120' for the Print Data 2 Segment. |
| REQ-SRC-ATL105-PDF-001:1253 | SEGMENT_102_FIELD_RULE | BR-264-5 | 264 | 95% | Segment Type (Field 1) must be the fixed value 123. |
| REQ-SRC-ATL105-PDF-001:1277 | SEGMENT_102_FIELD_RULE | BR-271-4 | 271 | 95% | Segment Type field for CA Public Key File Data Segment has fixed value 132. |
| REQ-SRC-ATL105-PDF-001:1282 | SEGMENT_102_FIELD_RULE | BR-274-1 | 274 | 90% | Field Nos 1 and 2, and 2 and 3, are separated by Field Separators. The segment should end with a field separator. Data fields contained within field 3 are not separated by Field Separators. |
| REQ-SRC-ATL105-PDF-001:1283 | SEGMENT_102_FIELD_RULE | BR-274-2 | 274 | 95% | Segment Type for Data Segment 135 has a fixed value of 135. |
| REQ-SRC-ATL105-PDF-001:1285 | SEGMENT_102_FIELD_RULE | BR-275-1 | 275 | 95% | Segment Type for Moneris Data (Response) Segment must have the fixed value 136. |
| REQ-SRC-ATL105-PDF-001:1290 | SEGMENT_102_FIELD_RULE | BR-276-2 | 276 | 95% | Segment Type must be the fixed value 139 for the Moneris Day End Batch Balance Request Segment. |
| REQ-SRC-ATL105-PDF-001:1291 | SEGMENT_102_FIELD_RULE | BR-276-3 | 276 | 90% | Segment Length includes Segment Type's length and Field Separators. |
| REQ-SRC-ATL105-PDF-001:1293 | SEGMENT_102_FIELD_RULE | BR-279-2 | 279 | 95% | Segment Type field 1 has fixed value 141. |
| REQ-SRC-ATL105-PDF-001:1298 | SEGMENT_102_FIELD_RULE | BR-281-2 | 281 | 95% | Segment Type field in Segment 142 has a fixed value of 142. |
| REQ-SRC-ATL105-PDF-001:1299 | DIRECT_SEGMENT_102 | BR-282-1 | 282 | 95% | When a Financial Transaction request includes the Tax by Product Segment, a Product Code Data Segment (Data Segment No. 102) must be present in the transaction. |
| REQ-SRC-ATL105-PDF-001:1300 | DIRECT_SEGMENT_102 | BR-282-2 | 282 | 95% | Each entry in the Product Code Data Segment (102) should have a corresponding entry in the Tax by Product Segment (143), and the entries must be in the same order in both segments. |
| REQ-SRC-ATL105-PDF-001:1307 | DIRECT_SEGMENT_102 | BR-283-1 | 283 | 90% | Number of Products should match the number of products from the Product Code Data Segment. |
| REQ-SRC-ATL105-PDF-001:1309 | DIRECT_SEGMENT_102 | BR-283-3 | 283 | 90% | Product Code in Tax by Product Data must match the same entry in the Product Code Data Segment. |
| REQ-SRC-ATL105-PDF-001:1320 | DIRECT_SEGMENT_102 | BR-285-1 | 285 | 64% | Product code '963' represents a tax product itself, so no tax amount is associated with it in the Tax by Product Data Segment. |
| REQ-SRC-ATL105-PDF-001:1321 | DIRECT_SEGMENT_102 | BR-285-2 | 285 | 64% | When exclusive taxes are used per product, the sum of the exclusive tax amounts across products in the Tax by Product Data Segment (143) equals the total amount of the tax product (e.g., product 963) reported in the Product Code Data Segment (102). |
| REQ-SRC-ATL105-PDF-001:1335 | SEGMENT_102_FIELD_RULE | BR-288-1 | 288 | 85% | Product Data is repeated for as many products as needed, separated by '\\|' and ending with '\\|'. |
| REQ-SRC-ATL105-PDF-001:1336 | SEGMENT_102_FIELD_RULE | BR-288-2 | 288 | 90% | For WEX OTR transactions, the listed product category codes (ADD, ANFR, BRAK, CADV, CLTH, DEF, DELI, ELEC, ETAX, EVC1, EVC2, EVC3, FAX, FLAT, GROC, HARD, IDLE, LMPR, LUBE, MERC, OIL, OILC, PADV, PART, PHON, PNT, RECP, REPR, REST, SCAN, SCLE, SHWR, TCHN, TIRE, TOLL, TRAL, TRPP, UREA, WASH, WWFL) are used to categorize products. |
| REQ-SRC-ATL105-PDF-001:1337 | SEGMENT_102_FIELD_RULE | BR-288-3 | 288 | 80% | Example format for non-fuel Scale product: '002014\\|SCLE:1,10.00\\|'. |
| REQ-SRC-ATL105-PDF-001:1356 | SEGMENT_102_FIELD_RULE | BR-295-4 | 295 | 95% | Segment Type field of Data Segment 146 must be fixed value '146'. |
| REQ-SRC-ATL105-PDF-001:1369 | SEGMENT_102_FIELD_RULE | BR-297-3 | 297 | 84% | Product Data is repeated for as many products as needed, separated by '\\|' and ending with '\\|'. |
| REQ-SRC-ATL105-PDF-001:1372 | SEGMENT_102_FIELD_RULE | BR-297-6 | 297 | 80% | Total Price for a product is variable length with a maximum of 8 and includes a decimal point. |
| REQ-SRC-ATL105-PDF-001:1373 | SEGMENT_102_FIELD_RULE | BR-297-7 | 297 | 75% | Fuel products use product codes from ATL105 Appendix F (unlike non-fuel products which use standard product codes). |
| REQ-SRC-ATL105-PDF-001:1391 | SEGMENT_102_FIELD_RULE | BR-301-3 | 301 | 95% | Within Segment 148, Segment Type (field 1) has fixed value 148. |
| REQ-SRC-ATL105-PDF-001:1399 | SEGMENT_102_FIELD_RULE | BR-305-3 | 305 | 95% | Segment Type field must be fixed value 151 for the Request InComm OTC Market Basket Data Segment. |
| REQ-SRC-ATL105-PDF-001:1402 | SEGMENT_102_FIELD_RULE | BR-306-1 | 306 | 95% | Segment Type for Data Segment 152 has a fixed value of 152. |
| REQ-SRC-ATL105-PDF-001:1407 | SEGMENT_102_FIELD_RULE | BR-307-2 | 307 | 95% | Segment Type for Network Token Data Request Segment is fixed at value 153. |
| REQ-SRC-ATL105-PDF-001:1419 | SEGMENT_102_FIELD_RULE | BR-310-5 | 310 | 95% | Segment Type in segment 155 is a fixed value of 155, length 3, required. |
| REQ-SRC-ATL105-PDF-001:1429 | DIRECT_SEGMENT_102 | BR-314-2 | 314 | 95% | The EV Charging Data Segment is sent only for an EV charging transaction (EV fuel code in Product Code Segment 102). |
| REQ-SRC-ATL105-PDF-001:1448 | DIRECT_SEGMENT_102 | BR-320-2 | 320 | 25% | a field is not populated â€” still send the Field Separator. |
| REQ-SRC-ATL105-PDF-001:1450 | DIRECT_SEGMENT_102 | BR-320-2 | 320 | 97% | Segment 157 is mutually exclusive with Segment 102 Product Code Data; if both segments are sent, the transaction will be declined. |
| REQ-SRC-ATL105-PDF-001:1451 | SEGMENT_102_FIELD_RULE | BR-320-3 | 320 | 95% | Segment 157 does not allow any product codes above '899' except for '955' Cash Back; presence of such product codes causes the transaction to be declined. |
| REQ-SRC-ATL105-PDF-001:1452 | SEGMENT_102_FIELD_RULE | BR-320-4 | 320 | 95% | In a Financial Transaction request with Segment 157, fuel products must always be the first products in the segment. |
| REQ-SRC-ATL105-PDF-001:1458 | SEGMENT_102_FIELD_RULE | BR-320-10 | 320 | 84% | A unique Product Code must be sent for each type of fuel purchased. |
| REQ-SRC-ATL105-PDF-001:1459 | SEGMENT_102_FIELD_RULE | BR-320-11 | 320 | 90% | For OTR multi-fuel transactions, the merchant must send the primary fuel as the very first product code within the Adjusted Product Code Segment, along with all fuel and non-fuel codes with quantity, unit of measure, unit price, and product amount. |
| REQ-SRC-ATL105-PDF-001:1460 | SEGMENT_102_FIELD_RULE | BR-320-12 | 320 | 85% | In Segment 157, a Field Separator always follows Segment Type and Segment Length; a Field Separator follows Adjusted Product Amount only when it is the very last element; when a field is not populated, still send the Field Separator. |
| REQ-SRC-ATL105-PDF-001:1461 | SEGMENT_102_FIELD_RULE | BR-320-13 | 320 | 85% | In Segment 157, a Product Data Field Delimiter always follows Quantity and Unit Price; a Product Data Field Delimiter follows Adjusted Product Amount when it is not the last element. |
| REQ-SRC-ATL105-PDF-001:1464 | SEGMENT_102_FIELD_RULE | BR-321-2 | 321 | 95% | The Product Data section in the Adjusted Product Code Data Segment repeats per product for a maximum of 10 products, with a total variable length of up to 370 bytes. |
| REQ-SRC-ATL105-PDF-001:1465 | SEGMENT_102_FIELD_RULE | BR-321-3 | 321 | 95% | Fuel products are always listed as the first products in the Adjusted Product Code Data Segment. |
| REQ-SRC-ATL105-PDF-001:1466 | SEGMENT_102_FIELD_RULE | BR-321-4 | 321 | 95% | A unique Product Code must be sent for each type of fuel purchase in the Adjusted Product Code Data Segment. |
| REQ-SRC-ATL105-PDF-001:1467 | SEGMENT_102_FIELD_RULE | BR-321-5 | 321 | 98% | Segment Type for the Adjusted Product Code Data Segment has fixed value 157. |
| REQ-SRC-ATL105-PDF-001:1468 | SEGMENT_102_FIELD_RULE | BR-322-1 | 322 | 95% | Quantity value must contain a digit for every position of assumed decimal places (e.g., 0.05 with 2 assumed decimals is '205'; '25' is invalid). |
| REQ-SRC-ATL105-PDF-001:1469 | SEGMENT_102_FIELD_RULE | BR-322-2 | 322 | 95% | Unit Price value must contain a digit for every position of assumed decimal places (e.g., $0.059 with 3 assumed decimals is '3059'; '359' is invalid). |
| REQ-SRC-ATL105-PDF-001:1470 | SEGMENT_102_FIELD_RULE | BR-322-3 | 322 | 95% | A Field Separator follows Product Amount if it is the very last element in the segment; otherwise a Product Data Field Delimiter follows Product Amount. |
| REQ-SRC-ATL105-PDF-001:1471 | SEGMENT_102_FIELD_RULE | BR-322-4 | 322 | 95% | Product Amount in the Adjusted Product Code Data Segment identifies the monetary value of the product sold AFTER tax and applicable discounts or coupons. |
| REQ-SRC-ATL105-PDF-001:1472 | SEGMENT_102_FIELD_RULE | BR-322-5 | 322 | 90% | Fuel product amounts are not adjusted because tax and discount amounts are already included in their unit prices; non-fuel product amounts are adjusted to allocate tax/discounts (example: $30 fuel A stays $30; $10 non-fuel B becomes $11; $20 non-fuel C becomes $22 when $3 tax is distributed). |
| REQ-SRC-ATL105-PDF-001:1507 | SEGMENT_102_FIELD_RULE | BR-333-5 | 333 | 90% | Segment Length Indicator indicates the data segment's length exclusive of the Data Type Indicator. |
| REQ-SRC-ATL105-PDF-001:1636 | SEGMENT_102_FIELD_RULE | BR-378-4 | 378 | 90% | For a transaction involving only a fuel purchase and discounts and/or coupons, Fuel Purchase Amount is the net amount derived by subtracting the discount and/or coupon amount from the sum of fuel products in Product Amount (No. 76). |
| REQ-SRC-ATL105-PDF-001:1658 | DIRECT_SEGMENT_102 | BR-384-3 | 384 | 95% | For Financial Transaction Requests, zero, one, or more of Data Segments 101 (Fleet), 102 (Product Code), 103 (EBT), 104 (Purchase Card), and 111 (Variable Information) may also follow. |
| REQ-SRC-ATL105-PDF-001:1671 | SEGMENT_102_FIELD_RULE | BR-386-5 | 386 | 95% | For a transaction involving only nonfuel purchase with discounts and/or coupons, Nonfuel Amount is the net amount derived by subtracting the discount and/or coupon amount from the sum of nonfuel products in Product Amount (No. 76). |
| REQ-SRC-ATL105-PDF-001:1678 | SEGMENT_102_FIELD_RULE | BR-387-3 | 387 | 95% | Number of Products must always precede single digits (1â€“9) with a zero (01â€“09). |
| REQ-SRC-ATL105-PDF-001:1679 | SEGMENT_102_FIELD_RULE | BR-387-4 | 387 | 95% | Number of Products valid range is 01â€“10. |
| REQ-SRC-ATL105-PDF-001:1703 | SEGMENT_102_FIELD_RULE | BR-392-3 | 392 | 95% | Product Amount valid values are 1â€“999999999999 with two assumed decimal places, implied by the optional Currency Code. |
| REQ-SRC-ATL105-PDF-001:1704 | DIRECT_SEGMENT_102 | BR-392-4 | 392 | 95% | Product Amount, together with Product Code (77), Unit of Measure (106), Quantity (81), and Unit Price (107), is repeated for up to a maximum of 10 products in Data Segment No. 102 or No. 157. |
| REQ-SRC-ATL105-PDF-001:1705 | DIRECT_SEGMENT_102 | BR-393-1 | 393 | 95% | Product Code and its related data elements (Unit of Measure, Quantity, Unit Price, Product Amount) are repeated for up to a maximum of 10 products in Data Segment No. 102 or Data Segment No. 157. |
| REQ-SRC-ATL105-PDF-001:1706 | SEGMENT_102_FIELD_RULE | BR-393-2 | 393 | 84% | A unique Product Code must be sent for each type of fuel purchase. |
| REQ-SRC-ATL105-PDF-001:1707 | SEGMENT_102_FIELD_RULE | BR-393-3 | 393 | 90% | When a device uses a Dynamic Card Table, only the product codes defined in the table are valid for transaction processing. |
| REQ-SRC-ATL105-PDF-001:1708 | DIRECT_SEGMENT_102 | BR-393-4 | 393 | 90% | In a Proprietary Load Response (Prompt Code 904), the first Product Code instance identifies the product eligible for discount, and the second instance identifies the corresponding discount product code to send in Data Segment No. 102. |
| REQ-SRC-ATL105-PDF-001:1709 | SEGMENT_102_FIELD_RULE | BR-393-5 | 393 | 90% | In a Proprietary Load Response (Host Discount Data, Prompt Code 904), the second or third position of the Product Code may contain a wild card character '*', in which case any digit is allowed in that position (e.g., '01*' matches product codes 010-019). |
| REQ-SRC-ATL105-PDF-001:1725 | SEGMENT_102_FIELD_RULE | BR-395-4 | 395 | 95% | Quantity leading digit indicates the number of assumed decimal places, with a maximum of three assumed decimal places. |
| REQ-SRC-ATL105-PDF-001:1726 | DIRECT_SEGMENT_102 | BR-395-5 | 395 | 95% | Quantity, along with Product Code (77), Unit of Measure (106), Unit Price (107), and Product Amount (76), is repeated for up to 10 products in Data Segment 102 (Product Code) or 157 (Adjusted Product Code). |
| REQ-SRC-ATL105-PDF-001:1727 | SEGMENT_102_FIELD_RULE | BR-395-6 | 395 | 90% | Valid codes/values for Quantity are 00000000.01â€“399999999. |
| REQ-SRC-ATL105-PDF-001:1745 | SEGMENT_102_FIELD_RULE | BR-399-1 | 399 | 95% | Segment Length has a length of four digits only when transmitting EBT Data Segment (103), SKU Data Segment (114), Print Data Segment (115), Proprietary Data Load Segment (118), Print Data 2 Segment (120), EMV Request Data Segment (130), or EMV Response Data Segment (131). It cannot be 4 digits for any other segment. |
| REQ-SRC-ATL105-PDF-001:1746 | SEGMENT_102_FIELD_RULE | BR-399-2 | 399 | 95% | Segment Length is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements. |
| REQ-SRC-ATL105-PDF-001:1747 | SEGMENT_102_FIELD_RULE | BR-400-1 | 400 | 90% | Standard Message Data Segment (No. 100) Segment Length valid values are 001â€“218. |
| REQ-SRC-ATL105-PDF-001:1748 | SEGMENT_102_FIELD_RULE | BR-400-2 | 400 | 90% | Fleet Data Segment (No. 101) Segment Length valid values are 001â€“61. |
| REQ-SRC-ATL105-PDF-001:1749 | DIRECT_SEGMENT_102 | BR-400-3 | 400 | 90% | Product Code Data Segment (No. 102) Segment Length valid values are 001â€“381. |
| REQ-SRC-ATL105-PDF-001:1750 | SEGMENT_102_FIELD_RULE | BR-400-4 | 400 | 90% | EBT Data Segment (No. 103) Segment Length valid values are 001â€“3334. |
| REQ-SRC-ATL105-PDF-001:1751 | SEGMENT_102_FIELD_RULE | BR-400-5 | 400 | 90% | Purchase Card Data Segment (No. 104) Segment Length valid values are 001â€“86. |
| REQ-SRC-ATL105-PDF-001:1752 | SEGMENT_102_FIELD_RULE | BR-400-6 | 400 | 90% | Totals Data Segment (No. 105) Segment Length valid values are 001â€“409. |
| REQ-SRC-ATL105-PDF-001:1753 | SEGMENT_102_FIELD_RULE | BR-400-7 | 400 | 90% | Loyalty Card Data Segment (No. 108) Segment Length valid values are 001â€“142. |
| REQ-SRC-ATL105-PDF-001:1754 | SEGMENT_102_FIELD_RULE | BR-400-8 | 400 | 90% | Electronic Mail Data Segment (No. 109) Segment Length valid values are 001â€“232. |
| REQ-SRC-ATL105-PDF-001:1755 | SEGMENT_102_FIELD_RULE | BR-400-9 | 400 | 90% | Check Data Segment (No. 110) Segment Length valid values are 001â€“168. |
| REQ-SRC-ATL105-PDF-001:1756 | SEGMENT_102_FIELD_RULE | BR-400-10 | 400 | 90% | Variable Information Data Segment (No. 111) Segment Length valid values are 001â€“999. |
| REQ-SRC-ATL105-PDF-001:1757 | SEGMENT_102_FIELD_RULE | BR-400-11 | 400 | 90% | Additional Information Data Segment (No. 112) Segment Length valid values are 001â€“999. |
| REQ-SRC-ATL105-PDF-001:1758 | SEGMENT_102_FIELD_RULE | BR-400-12 | 400 | 90% | ECA/TeleCheck Data Segment (No. 113) Segment Length valid values are 001â€“156. |
| REQ-SRC-ATL105-PDF-001:1759 | SEGMENT_102_FIELD_RULE | BR-400-13 | 400 | 90% | SKU Data Segment (No. 114) Segment Length valid values are 0001â€“1010. |
| REQ-SRC-ATL105-PDF-001:1760 | SEGMENT_102_FIELD_RULE | BR-400-14 | 400 | 90% | Print Data Segment (No. 115) Segment Length valid values are 001-1009. |
| REQ-SRC-ATL105-PDF-001:1761 | SEGMENT_102_FIELD_RULE | BR-400-15 | 400 | 90% | TransArmor Load Data Segment (No. 116) Segment Length valid values are 01â€“50. |
| REQ-SRC-ATL105-PDF-001:1762 | SEGMENT_102_FIELD_RULE | BR-400-16 | 400 | 90% | Proprietary Data Load Segment (No. 118) Segment Length valid values are 0001-3800. |
| REQ-SRC-ATL105-PDF-001:1763 | SEGMENT_102_FIELD_RULE | BR-400-17 | 400 | 90% | Totals with Proprietary Data Load Data Segment (No. 119) Segment Length valid values are 001-493. |
| REQ-SRC-ATL105-PDF-001:1764 | SEGMENT_102_FIELD_RULE | BR-400-18 | 400 | 90% | Print Data 2 Segment (No. 120) Segment Length valid values are 001-1009. |
| REQ-SRC-ATL105-PDF-001:1765 | SEGMENT_102_FIELD_RULE | BR-400-19 | 400 | 90% | NFC Payment Tokenization Data Segment (No. 123) Segment Length valid values are 001-186. |
| REQ-SRC-ATL105-PDF-001:1766 | SEGMENT_102_FIELD_RULE | BR-400-20 | 400 | 90% | EMV Request Data Segment (No.130) Segment Length valid values are 001-3043. |
| REQ-SRC-ATL105-PDF-001:1767 | SEGMENT_102_FIELD_RULE | BR-400-21 | 400 | 90% | EMV Response Data Segment (No. 131) Segment Length valid values are 001-3834. |
| REQ-SRC-ATL105-PDF-001:1768 | SEGMENT_102_FIELD_RULE | BR-400-22 | 400 | 90% | CA Public Key File Data Segment (No. 132) Segment Length valid values are 01-77. |
| REQ-SRC-ATL105-PDF-001:1769 | SEGMENT_102_FIELD_RULE | BR-400-23 | 400 | 90% | Transaction Attributes Data Segment (No. 134) Segment Length valid values are 01-19. |
| REQ-SRC-ATL105-PDF-001:1770 | SEGMENT_102_FIELD_RULE | BR-400-24 | 400 | 90% | Adjusted Product Code Data Segment (No. 157) Segment Length valid values are 001â€“381. |
| REQ-SRC-ATL105-PDF-001:1771 | SEGMENT_102_FIELD_RULE | BR-401-1 | 401 | 90% | Segment Type is required for the TransArmor PKI Encryption and Tokenization Load Request. |
| REQ-SRC-ATL105-PDF-001:1772 | SEGMENT_102_FIELD_RULE | BR-401-2 | 401 | 95% | Segment Type is a fixed length of three digits (N, max 3 bytes). |
| REQ-SRC-ATL105-PDF-001:1780 | DIRECT_SEGMENT_102 | BR-403-2 | 403 | 95% | Service Level identifies the sale type for a product in Data Segment No. 102 (Product Code Data Segment) or Data Segment No. 157 (Adjusted Product Code Data Segment). |
| REQ-SRC-ATL105-PDF-001:1818 | DIRECT_SEGMENT_102 | BR-413-1 | 413 | 95% | Unit of Measure, along with Product Code (77), Quantity (81), Unit Price (107), and Product Amount (76), is repeated for up to 10 products in Data Segment 102 (Product Code Data Segment) or Data Segment 157 (Adjusted Product Code Data Segment). |
| REQ-SRC-ATL105-PDF-001:1819 | SEGMENT_102_FIELD_RULE | BR-413-2 | 413 | 95% | Unit of Measure is a fixed length of one alpha character (max 1 byte). |
| REQ-SRC-ATL105-PDF-001:1820 | SEGMENT_102_FIELD_RULE | BR-414-1 | 414 | 90% | Unit Price has a maximum of three assumed decimal places, with the leading digit indicating the number of assumed decimal places. |
| REQ-SRC-ATL105-PDF-001:1821 | DIRECT_SEGMENT_102 | BR-414-2 | 414 | 90% | Unit Price, along with Product Code (77), Unit of Measure (106), Quantity (81), and Product Amount (76), is repeated for up to 10 products in Data Segment 102 or 157. |
| REQ-SRC-ATL105-PDF-001:1995 | SEGMENT_102_FIELD_RULE | BR-446-5 | 446 | 90% | For WIC purchases involving fruits and vegetables via the Cash Value Benefit (CVB), set the Quantity equal to the price in the WIC Purchase Information. |
| REQ-SRC-ATL105-PDF-001:2165 | DIRECT_SEGMENT_102 | BR-492-1 | 492 | 90% | The Extended Unit of Measure element, along with related data elements, is repeated for up to 10 Non-Fuel products in Data Segment No. 102 (Product Code Data Segment). |
| REQ-SRC-ATL105-PDF-001:2179 | SEGMENT_102_FIELD_RULE | BR-498-1 | 498 | 25% | A unique Product Code must be sent for each type of fuel purchase. |
| REQ-SRC-ATL105-PDF-001:2180 | SEGMENT_102_FIELD_RULE | BR-498-1 | 498 | 84% | A unique Product Code must be sent for each type of fuel purchase. |
| REQ-SRC-ATL105-PDF-001:2183 | SEGMENT_102_FIELD_RULE | BR-499-3 | 499 | 80% | Standard Message Data Segment (100) length is 78 bytes including field separators in this example. |
| REQ-SRC-ATL105-PDF-001:2184 | SEGMENT_102_FIELD_RULE | BR-501-1 | 501 | 90% | For Quantity and Unit Price fields, the leading digit indicates the number of assumed decimal places (e.g. '34170' = 4.170, '31199' = 1.199). |
| REQ-SRC-ATL105-PDF-001:2185 | DIRECT_SEGMENT_102 | BR-501-2 | 501 | 85% | The Product Code Data Segment (102) length in this example is 49 bytes including Field Separators and Product Data Field Delimiters. |
| REQ-SRC-ATL105-PDF-001:2203 | DIRECT_SEGMENT_102 | BR-516-1 | 516 | 90% | Product Code values must be valid entries for Element No. 77 (Product Code), which is part of the Product Data section in Data Segment No. 102 (Product Code Data Segment). |
| REQ-SRC-ATL105-PDF-001:2204 | SEGMENT_102_FIELD_RULE | BR-516-2 | 516 | 90% | Any product listed in the Appendix F tables must be represented in messages to BUYPASS by the Product Code assigned to it. |
| REQ-SRC-ATL105-PDF-001:2205 | SEGMENT_102_FIELD_RULE | BR-516-3 | 516 | 95% | For fuel purchases, a unique Product Code must be sent for each type of fuel purchase. |
| REQ-SRC-ATL105-PDF-001:2206 | SEGMENT_102_FIELD_RULE | BR-516-4 | 516 | 95% | Product Code 000 is designated as 'Not used'. |
| REQ-SRC-ATL105-PDF-001:2207 | SEGMENT_102_FIELD_RULE | BR-517-1 | 517 | 25% | A unique Product Code must be sent for each type of fuel purchase. |
| REQ-SRC-ATL105-PDF-001:2208 | SEGMENT_102_FIELD_RULE | BR-517-1 | 517 | 84% | A unique Product Code must be sent for each type of fuel purchase. |
| REQ-SRC-ATL105-PDF-001:2209 | SEGMENT_102_FIELD_RULE | BR-522-1 | 522 | 25% | A unique Product Code must be sent for each type of fuel purchase. |
| REQ-SRC-ATL105-PDF-001:2210 | SEGMENT_102_FIELD_RULE | BR-522-1 | 522 | 84% | A unique Product Code must be sent for each type of fuel purchase. |
| REQ-SRC-ATL105-PDF-001:2211 | SEGMENT_102_FIELD_RULE | BR-525-1 | 525 | 25% | A unique Product Code must be sent for each type of fuel purchase. |
| REQ-SRC-ATL105-PDF-001:2212 | SEGMENT_102_FIELD_RULE | BR-525-1 | 525 | 84% | A unique Product Code must be sent for each type of fuel purchase. |
| REQ-SRC-ATL105-PDF-001:2213 | SEGMENT_102_FIELD_RULE | BR-532-1 | 532 | 90% | BUYPASS Product Code 800 (Administrativeâ€”Debit surcharge) is applicable for both credit (Visa/MasterCard) and debit transactions. |
| REQ-SRC-ATL105-PDF-001:2214 | SEGMENT_102_FIELD_RULE | BR-533-1 | 533 | 90% | Product Code numbers 810â€“889 are reserved for proprietary use. |
| REQ-SRC-ATL105-PDF-001:2215 | SEGMENT_102_FIELD_RULE | BR-535-1 | 535 | 90% | Product Code numbers 897â€“899 are reserved for proprietary use. |
| REQ-SRC-ATL105-PDF-001:2216 | SEGMENT_102_FIELD_RULE | BR-536-1 | 536 | 90% | If the transaction involves a fuel purchase, a unique Product Code must be sent for each type of fuel involved in the transaction. |
| REQ-SRC-ATL105-PDF-001:2217 | SEGMENT_102_FIELD_RULE | BR-536-2 | 536 | 90% | Discounts and coupons are applied first to nonfuel purchase amounts. |
| REQ-SRC-ATL105-PDF-001:2218 | SEGMENT_102_FIELD_RULE | BR-536-3 | 536 | 80% | Product Codes 915 through 948 are Undefined negative codes reserved within the negative code range (900â€“949). |
| REQ-SRC-ATL105-PDF-001:2219 | SEGMENT_102_FIELD_RULE | BR-537-1 | 537 | 90% | If a transaction involves a fuel purchase, a unique Product Code must be sent for each type of fuel purchase. |
| REQ-SRC-ATL105-PDF-001:2370 | SEGMENT_102_FIELD_RULE | BR-584-5 | 584 | 90% | Sub-Table ID '05' Sub-Table Data has a maximum length of 6 digits and identifies the Product code of the highest priced item of a consumer's transaction at checkout at the POS. |
| REQ-SRC-ATL105-PDF-001:2540 | DIRECT_SEGMENT_102 | BR-624-1 | 624 | 90% | Any product codes sent in Table 078 must also be sent in Product Code Data Segment 102. |
| REQ-SRC-ATL105-PDF-001:2617 | SEGMENT_102_FIELD_RULE | BR-652-5 | 652 | 21% | Unit of Measure in Re-Price Table Data must match the unit of measure sent in the product data. |
| REQ-SRC-ATL105-PDF-001:2618 | SEGMENT_102_FIELD_RULE | BR-652-6 | 652 | 80% | Product Code is a NACS Product Code of 3 bytes. |
| REQ-SRC-ATL105-PDF-001:2620 | SEGMENT_102_FIELD_RULE | BR-652-8 | 652 | 80% | Unit Price is up to 8 bytes representing price per unit. |
| REQ-SRC-ATL105-PDF-001:2621 | SEGMENT_102_FIELD_RULE | BR-652-9 | 652 | 21% | Decimal Identifier Prc (1 byte) identifies the number of decimals in the Unit Price field. |
