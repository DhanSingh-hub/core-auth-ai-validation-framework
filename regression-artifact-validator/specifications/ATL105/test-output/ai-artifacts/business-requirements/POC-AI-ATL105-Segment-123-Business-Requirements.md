# POC AI Segment 123 Business Requirements

Source: `step5_requirements/approved/requirement_catalog.json`

Scope: rules whose source rule is explicitly related to ENT-SEG-123 or a direct field of that segment in the approved POC extraction.

Count: 129

| ID | Scope | Source rule | Page | Confidence | Requirement |
|---|---|---|---:|---:|---|
| REQ-SRC-ATL105-PDF-001:254 | SEGMENT_123_FIELD_RULE | BR-7-4 | 7 | 74% | Adjusted Product Code Data Segment (No. 157) added; data elements 76, 77, 81, 84, 85, 87, 106, and 107 updated with references to data segment 157. |
| REQ-SRC-ATL105-PDF-001:623 | SEGMENT_123_FIELD_RULE | BR-106-9 | 106 | 95% | eCommerce transactions, including 3-D Secure Protocol (Verified by Visa, MC Secure code, Amex SafeKey, Discover ProtectBuy), are not supported for STAR Single/Dual message processing. |
| REQ-SRC-ATL105-PDF-001:814 | SEGMENT_123_FIELD_RULE | BR-141-2 | 141 | 54% | In a Card On File (COF) scenario, the payment token provided by the card brand replaces the PAN stored at the merchant. |
| REQ-SRC-ATL105-PDF-001:889 | DIRECT_SEGMENT_123 | BR-154-5 | 154 | 85% | Supported Payment Token usage: Visa supports In-App, NFC, and COF; MasterCard supports In-App, NFC, and COF; AMEX supports In-App, NFC, and COF; Discover supports In-App, NFC, and COF; Interlink supports NFC; Maestro supports NFC. |
| REQ-SRC-ATL105-PDF-001:890 | DIRECT_SEGMENT_123 | BR-155-1 | 155 | 90% | Data Segment 123 (NFC Payment Tokenization Data Segment) is required on all initial and recurring transactions involving tokenized data. |
| REQ-SRC-ATL105-PDF-001:891 | SEGMENT_123_FIELD_RULE | BR-155-2 | 155 | 90% | Data Element 195 (CAVV Revised Format) applies to Visa only and identifies Verified By Visa Format; Authentication Tracking Number (ATN) replaces XID to support payment tokenization. |
| REQ-SRC-ATL105-PDF-001:892 | SEGMENT_123_FIELD_RULE | BR-155-3 | 155 | 90% | Data Element 197 (Token PAN Suffix) contains the 4 digits of the cardholder PAN to be printed on the transaction receipt. |
| REQ-SRC-ATL105-PDF-001:893 | SEGMENT_123_FIELD_RULE | BR-155-4 | 155 | 90% | Data Element 202 (Cryptogram Token Data) is specific to In-App transactions and represents merchant's cryptogram token data block A (or block A and B). |
| REQ-SRC-ATL105-PDF-001:895 | SEGMENT_123_FIELD_RULE | BR-155-6 | 155 | 85% | Data Element 196 (Token Requestor ID) identifies the merchant's Token Requestor ID. |
| REQ-SRC-ATL105-PDF-001:932 | DIRECT_SEGMENT_123 | BR-165-6 | 165 | 95% | NFC Payment Tokenization Data Segment (123) is required on all initial and recurring transactions involving tokenized data. |
| REQ-SRC-ATL105-PDF-001:936 | DIRECT_SEGMENT_123 | BR-165-10 | 165 | 85% | Segments 101â€“145 in Financial Transaction Request occupy Field Nos. 4â€“9 with specified maximum lengths (101:308, 102:381, 103:3334, 104:86, 111:999, 123:186, 135:110, 143:399, 145:999). |
| REQ-SRC-ATL105-PDF-001:1048 | DIRECT_SEGMENT_123 | BR-203-2 | 203 | 90% | Segment 123 (NFC Payment Tokenization Data Segment) is required on all initial and recurring transactions involving tokenized data. |
| REQ-SRC-ATL105-PDF-001:1072 | SEGMENT_123_FIELD_RULE | BR-211-5 | 211 | 95% | Segment Type field 3 of Data Segment 132 has fixed value 132. |
| REQ-SRC-ATL105-PDF-001:1089 | SEGMENT_123_FIELD_RULE | BR-220-4 | 220 | 97% | Field 1 Segment Type in Segment 100 has fixed value 100. |
| REQ-SRC-ATL105-PDF-001:1090 | SEGMENT_123_FIELD_RULE | BR-220-5 | 220 | 90% | Segment Length includes the Segment Type's length and Field Separators. |
| REQ-SRC-ATL105-PDF-001:1095 | SEGMENT_123_FIELD_RULE | BR-223-5 | 223 | 95% | Segment Type field in the Fleet Data Segment has fixed value 101. |
| REQ-SRC-ATL105-PDF-001:1096 | SEGMENT_123_FIELD_RULE | BR-223-6 | 223 | 90% | Segment Length field identifies the data segment's length, including the Segment Type field's length and Field Separators. |
| REQ-SRC-ATL105-PDF-001:1115 | SEGMENT_123_FIELD_RULE | BR-226-12 | 226 | 90% | A Field Separator always follows Segment Type and Segment Length. A Field Separator follows Product Amount when Product Amount is the very last element in the segment. When a field is not populated, still send the Field Separator. |
| REQ-SRC-ATL105-PDF-001:1128 | SEGMENT_123_FIELD_RULE | BR-229-3 | 229 | 95% | Segment Length should be '4' for EBT with eWIC data transactions. |
| REQ-SRC-ATL105-PDF-001:1131 | SEGMENT_123_FIELD_RULE | BR-229-6 | 229 | 98% | Segment Type is a fixed value of 103 for the EBT Data Segment. |
| REQ-SRC-ATL105-PDF-001:1135 | SEGMENT_123_FIELD_RULE | BR-230-4 | 230 | 95% | Segment Type field is fixed value 104 for the Purchase Card Data Segment. |
| REQ-SRC-ATL105-PDF-001:1141 | SEGMENT_123_FIELD_RULE | BR-232-5 | 232 | 95% | For Totals Data Segment, Segment Type (Field 1) has fixed value 105. |
| REQ-SRC-ATL105-PDF-001:1148 | SEGMENT_123_FIELD_RULE | BR-235-4 | 235 | 95% | Segment Type in Loyalty Card Data Segment is fixed value 108. |
| REQ-SRC-ATL105-PDF-001:1153 | SEGMENT_123_FIELD_RULE | BR-237-5 | 237 | 95% | Segment Type (field 1) has fixed value 109 for the Electronic Mail Data Segment. |
| REQ-SRC-ATL105-PDF-001:1167 | SEGMENT_123_FIELD_RULE | BR-243-2 | 243 | 95% | Segment Type for Data Segment No. 111 has fixed value 111. |
| REQ-SRC-ATL105-PDF-001:1174 | SEGMENT_123_FIELD_RULE | BR-245-2 | 245 | 95% | Segment Type in segment 112 must be the fixed value 112. |
| REQ-SRC-ATL105-PDF-001:1180 | SEGMENT_123_FIELD_RULE | BR-246-5 | 246 | 95% | Segment Type (field 1) is fixed value 113 for the ECA/TeleCheck Data Segment. |
| REQ-SRC-ATL105-PDF-001:1190 | SEGMENT_123_FIELD_RULE | BR-249-5 | 249 | 95% | Segment Type field is a fixed value of 115 for the Print Data Segment. |
| REQ-SRC-ATL105-PDF-001:1196 | SEGMENT_123_FIELD_RULE | BR-251-5 | 251 | 98% | Segment Type field for Proprietary Data Load Segment has a fixed value of 118. |
| REQ-SRC-ATL105-PDF-001:1212 | SEGMENT_123_FIELD_RULE | BR-255-1 | 255 | 95% | When Prompt Code = 902 (Dynamic Card Table Data), Field 14 contains Card Table Data (max 3,600 bytes, Required); its length is determined by the segment length in field 2. |
| REQ-SRC-ATL105-PDF-001:1235 | SEGMENT_123_FIELD_RULE | BR-260-4 | 260 | 95% | In segment 119, Segment Type has fixed value 119. |
| REQ-SRC-ATL105-PDF-001:1248 | SEGMENT_123_FIELD_RULE | BR-263-6 | 263 | 95% | Segment Type field contains fixed value '120' for the Print Data 2 Segment. |
| REQ-SRC-ATL105-PDF-001:1249 | DIRECT_SEGMENT_123 | BR-264-1 | 264 | 90% | The NFC Payment Tokenization Data Segment is required on all initial and recurring transactions involving tokenized data and for transactions that include MasterCard Token or Digital Secure Remote Payment (DSRP) or Visa TAVV data. |
| REQ-SRC-ATL105-PDF-001:1250 | DIRECT_SEGMENT_123 | BR-264-2 | 264 | 90% | The NFC Payment Tokenization Data Segment has a maximum length of 186 alphanumeric characters (a-z, A-Z, 0-9). |
| REQ-SRC-ATL105-PDF-001:1251 | SEGMENT_123_FIELD_RULE | BR-264-3 | 264 | 90% | The Token PAN Suffix is returned in the response only if supplied by the authorizer. |
| REQ-SRC-ATL105-PDF-001:1252 | DIRECT_SEGMENT_123 | BR-264-4 | 264 | 90% | All fields in the NFC Payment Tokenization Data Segment are separated by a Field Separator; when a field is not populated, the Field Separator must still be sent. A Field Separator follows Field No. 4. |
| REQ-SRC-ATL105-PDF-001:1253 | DIRECT_SEGMENT_123 | BR-264-5 | 264 | 95% | Segment Type (Field 1) must be the fixed value 123. |
| REQ-SRC-ATL105-PDF-001:1254 | SEGMENT_123_FIELD_RULE | BR-265-1 | 265 | 90% | When the first 28 bytes of SafeKey Data (AEVV) is not applicable, it should be space filled. |
| REQ-SRC-ATL105-PDF-001:1255 | SEGMENT_123_FIELD_RULE | BR-265-2 | 265 | 90% | If AESK is optional for merchants, then the last 28 bytes of SafeKey Data should be space filled. |
| REQ-SRC-ATL105-PDF-001:1256 | SEGMENT_123_FIELD_RULE | BR-265-3 | 265 | 90% | Cryptogram Token Data length is 28 or 56 bytes (Block A alone, or Blocks A and B). |
| REQ-SRC-ATL105-PDF-001:1257 | DIRECT_SEGMENT_123 | BR-266-1 | 266 | 80% | Merchant can only populate the TAVV Cryptogram (28 bytes) in the NFC Payment Tokenization Data Segment during a transaction request. |
| REQ-SRC-ATL105-PDF-001:1258 | SEGMENT_123_FIELD_RULE | BR-266-2 | 266 | 75% | Refer to Appendix-Y for using the TAVV Cryptogram field in a MasterCard DSRP transaction or for Visa 3D Secure. |
| REQ-SRC-ATL105-PDF-001:1259 | SEGMENT_123_FIELD_RULE | BR-266-3 | 266 | 80% | TAVV Result Code identifies whether the TAVV Cryptogram result received from Visa matches the response value sent back to the merchant's terminal, and is populated as appropriate based on the TAVV cryptogram. |
| REQ-SRC-ATL105-PDF-001:1277 | SEGMENT_123_FIELD_RULE | BR-271-4 | 271 | 95% | Segment Type field for CA Public Key File Data Segment has fixed value 132. |
| REQ-SRC-ATL105-PDF-001:1282 | SEGMENT_123_FIELD_RULE | BR-274-1 | 274 | 90% | Field Nos 1 and 2, and 2 and 3, are separated by Field Separators. The segment should end with a field separator. Data fields contained within field 3 are not separated by Field Separators. |
| REQ-SRC-ATL105-PDF-001:1283 | SEGMENT_123_FIELD_RULE | BR-274-2 | 274 | 95% | Segment Type for Data Segment 135 has a fixed value of 135. |
| REQ-SRC-ATL105-PDF-001:1285 | SEGMENT_123_FIELD_RULE | BR-275-1 | 275 | 95% | Segment Type for Moneris Data (Response) Segment must have the fixed value 136. |
| REQ-SRC-ATL105-PDF-001:1290 | SEGMENT_123_FIELD_RULE | BR-276-2 | 276 | 95% | Segment Type must be the fixed value 139 for the Moneris Day End Batch Balance Request Segment. |
| REQ-SRC-ATL105-PDF-001:1291 | SEGMENT_123_FIELD_RULE | BR-276-3 | 276 | 90% | Segment Length includes Segment Type's length and Field Separators. |
| REQ-SRC-ATL105-PDF-001:1293 | SEGMENT_123_FIELD_RULE | BR-279-2 | 279 | 95% | Segment Type field 1 has fixed value 141. |
| REQ-SRC-ATL105-PDF-001:1298 | SEGMENT_123_FIELD_RULE | BR-281-2 | 281 | 95% | Segment Type field in Segment 142 has a fixed value of 142. |
| REQ-SRC-ATL105-PDF-001:1356 | SEGMENT_123_FIELD_RULE | BR-295-4 | 295 | 95% | Segment Type field of Data Segment 146 must be fixed value '146'. |
| REQ-SRC-ATL105-PDF-001:1391 | SEGMENT_123_FIELD_RULE | BR-301-3 | 301 | 95% | Within Segment 148, Segment Type (field 1) has fixed value 148. |
| REQ-SRC-ATL105-PDF-001:1399 | SEGMENT_123_FIELD_RULE | BR-305-3 | 305 | 95% | Segment Type field must be fixed value 151 for the Request InComm OTC Market Basket Data Segment. |
| REQ-SRC-ATL105-PDF-001:1402 | SEGMENT_123_FIELD_RULE | BR-306-1 | 306 | 95% | Segment Type for Data Segment 152 has a fixed value of 152. |
| REQ-SRC-ATL105-PDF-001:1407 | SEGMENT_123_FIELD_RULE | BR-307-2 | 307 | 95% | Segment Type for Network Token Data Request Segment is fixed at value 153. |
| REQ-SRC-ATL105-PDF-001:1419 | SEGMENT_123_FIELD_RULE | BR-310-5 | 310 | 95% | Segment Type in segment 155 is a fixed value of 155, length 3, required. |
| REQ-SRC-ATL105-PDF-001:1460 | SEGMENT_123_FIELD_RULE | BR-320-12 | 320 | 85% | In Segment 157, a Field Separator always follows Segment Type and Segment Length; a Field Separator follows Adjusted Product Amount only when it is the very last element; when a field is not populated, still send the Field Separator. |
| REQ-SRC-ATL105-PDF-001:1467 | SEGMENT_123_FIELD_RULE | BR-321-5 | 321 | 98% | Segment Type for the Adjusted Product Code Data Segment has fixed value 157. |
| REQ-SRC-ATL105-PDF-001:1507 | SEGMENT_123_FIELD_RULE | BR-333-5 | 333 | 90% | Segment Length Indicator indicates the data segment's length exclusive of the Data Type Indicator. |
| REQ-SRC-ATL105-PDF-001:1745 | SEGMENT_123_FIELD_RULE | BR-399-1 | 399 | 95% | Segment Length has a length of four digits only when transmitting EBT Data Segment (103), SKU Data Segment (114), Print Data Segment (115), Proprietary Data Load Segment (118), Print Data 2 Segment (120), EMV Request Data Segment (130), or EMV Response Data Segment (131). It cannot be 4 digits for any other segment. |
| REQ-SRC-ATL105-PDF-001:1746 | SEGMENT_123_FIELD_RULE | BR-399-2 | 399 | 95% | Segment Length is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements. |
| REQ-SRC-ATL105-PDF-001:1747 | SEGMENT_123_FIELD_RULE | BR-400-1 | 400 | 90% | Standard Message Data Segment (No. 100) Segment Length valid values are 001â€“218. |
| REQ-SRC-ATL105-PDF-001:1748 | SEGMENT_123_FIELD_RULE | BR-400-2 | 400 | 90% | Fleet Data Segment (No. 101) Segment Length valid values are 001â€“61. |
| REQ-SRC-ATL105-PDF-001:1749 | SEGMENT_123_FIELD_RULE | BR-400-3 | 400 | 90% | Product Code Data Segment (No. 102) Segment Length valid values are 001â€“381. |
| REQ-SRC-ATL105-PDF-001:1750 | SEGMENT_123_FIELD_RULE | BR-400-4 | 400 | 90% | EBT Data Segment (No. 103) Segment Length valid values are 001â€“3334. |
| REQ-SRC-ATL105-PDF-001:1751 | SEGMENT_123_FIELD_RULE | BR-400-5 | 400 | 90% | Purchase Card Data Segment (No. 104) Segment Length valid values are 001â€“86. |
| REQ-SRC-ATL105-PDF-001:1752 | SEGMENT_123_FIELD_RULE | BR-400-6 | 400 | 90% | Totals Data Segment (No. 105) Segment Length valid values are 001â€“409. |
| REQ-SRC-ATL105-PDF-001:1753 | SEGMENT_123_FIELD_RULE | BR-400-7 | 400 | 90% | Loyalty Card Data Segment (No. 108) Segment Length valid values are 001â€“142. |
| REQ-SRC-ATL105-PDF-001:1754 | SEGMENT_123_FIELD_RULE | BR-400-8 | 400 | 90% | Electronic Mail Data Segment (No. 109) Segment Length valid values are 001â€“232. |
| REQ-SRC-ATL105-PDF-001:1755 | SEGMENT_123_FIELD_RULE | BR-400-9 | 400 | 90% | Check Data Segment (No. 110) Segment Length valid values are 001â€“168. |
| REQ-SRC-ATL105-PDF-001:1756 | SEGMENT_123_FIELD_RULE | BR-400-10 | 400 | 90% | Variable Information Data Segment (No. 111) Segment Length valid values are 001â€“999. |
| REQ-SRC-ATL105-PDF-001:1757 | SEGMENT_123_FIELD_RULE | BR-400-11 | 400 | 90% | Additional Information Data Segment (No. 112) Segment Length valid values are 001â€“999. |
| REQ-SRC-ATL105-PDF-001:1758 | SEGMENT_123_FIELD_RULE | BR-400-12 | 400 | 90% | ECA/TeleCheck Data Segment (No. 113) Segment Length valid values are 001â€“156. |
| REQ-SRC-ATL105-PDF-001:1759 | SEGMENT_123_FIELD_RULE | BR-400-13 | 400 | 90% | SKU Data Segment (No. 114) Segment Length valid values are 0001â€“1010. |
| REQ-SRC-ATL105-PDF-001:1760 | SEGMENT_123_FIELD_RULE | BR-400-14 | 400 | 90% | Print Data Segment (No. 115) Segment Length valid values are 001-1009. |
| REQ-SRC-ATL105-PDF-001:1761 | SEGMENT_123_FIELD_RULE | BR-400-15 | 400 | 90% | TransArmor Load Data Segment (No. 116) Segment Length valid values are 01â€“50. |
| REQ-SRC-ATL105-PDF-001:1762 | SEGMENT_123_FIELD_RULE | BR-400-16 | 400 | 90% | Proprietary Data Load Segment (No. 118) Segment Length valid values are 0001-3800. |
| REQ-SRC-ATL105-PDF-001:1763 | SEGMENT_123_FIELD_RULE | BR-400-17 | 400 | 90% | Totals with Proprietary Data Load Data Segment (No. 119) Segment Length valid values are 001-493. |
| REQ-SRC-ATL105-PDF-001:1764 | SEGMENT_123_FIELD_RULE | BR-400-18 | 400 | 90% | Print Data 2 Segment (No. 120) Segment Length valid values are 001-1009. |
| REQ-SRC-ATL105-PDF-001:1765 | DIRECT_SEGMENT_123 | BR-400-19 | 400 | 90% | NFC Payment Tokenization Data Segment (No. 123) Segment Length valid values are 001-186. |
| REQ-SRC-ATL105-PDF-001:1766 | SEGMENT_123_FIELD_RULE | BR-400-20 | 400 | 90% | EMV Request Data Segment (No.130) Segment Length valid values are 001-3043. |
| REQ-SRC-ATL105-PDF-001:1767 | SEGMENT_123_FIELD_RULE | BR-400-21 | 400 | 90% | EMV Response Data Segment (No. 131) Segment Length valid values are 001-3834. |
| REQ-SRC-ATL105-PDF-001:1768 | SEGMENT_123_FIELD_RULE | BR-400-22 | 400 | 90% | CA Public Key File Data Segment (No. 132) Segment Length valid values are 01-77. |
| REQ-SRC-ATL105-PDF-001:1769 | SEGMENT_123_FIELD_RULE | BR-400-23 | 400 | 90% | Transaction Attributes Data Segment (No. 134) Segment Length valid values are 01-19. |
| REQ-SRC-ATL105-PDF-001:1770 | SEGMENT_123_FIELD_RULE | BR-400-24 | 400 | 90% | Adjusted Product Code Data Segment (No. 157) Segment Length valid values are 001â€“381. |
| REQ-SRC-ATL105-PDF-001:1771 | SEGMENT_123_FIELD_RULE | BR-401-1 | 401 | 90% | Segment Type is required for the TransArmor PKI Encryption and Tokenization Load Request. |
| REQ-SRC-ATL105-PDF-001:1772 | SEGMENT_123_FIELD_RULE | BR-401-2 | 401 | 95% | Segment Type is a fixed length of three digits (N, max 3 bytes). |
| REQ-SRC-ATL105-PDF-001:2094 | SEGMENT_123_FIELD_RULE | BR-474-6 | 474 | 95% | CAVV Revised Format is used only for Visa and Interlink payment tokenized transactions. |
| REQ-SRC-ATL105-PDF-001:2095 | SEGMENT_123_FIELD_RULE | BR-475-1 | 475 | 90% | Token Requestor ID (Data Element 196) is used only for Visa/MasterCard payment tokenized transactions. |
| REQ-SRC-ATL105-PDF-001:2096 | SEGMENT_123_FIELD_RULE | BR-475-2 | 475 | 90% | Merchants are advised not to send the Token Requestor ID when submitting a Card on File tokenized transaction / Static Token type. |
| REQ-SRC-ATL105-PDF-001:2097 | SEGMENT_123_FIELD_RULE | BR-475-3 | 475 | 90% | If Token Requestor ID is submitted for a MasterCard Static Token transaction, MasterCard will validate it and will decline if invalid. |
| REQ-SRC-ATL105-PDF-001:2098 | SEGMENT_123_FIELD_RULE | BR-475-4 | 475 | 90% | Cryptographic Data is not required for Static Tokens. |
| REQ-SRC-ATL105-PDF-001:2099 | SEGMENT_123_FIELD_RULE | BR-475-5 | 475 | 90% | Merchants that submit Secure Element and Cloud-based token types are required to submit the Token Requestor ID along with Cryptographic data, if applicable. |
| REQ-SRC-ATL105-PDF-001:2100 | SEGMENT_123_FIELD_RULE | BR-475-6 | 475 | 95% | Token Requestor ID is fixed length 11 bytes, alphanumeric, left justified and space-filled. |
| REQ-SRC-ATL105-PDF-001:2101 | SEGMENT_123_FIELD_RULE | BR-476-1 | 476 | 90% | Token PAN Suffix is returned only for Visa/MasterCard and Interlink/Maestro tokenized transactions. |
| REQ-SRC-ATL105-PDF-001:2109 | SEGMENT_123_FIELD_RULE | BR-478-1 | 478 | 90% | Merchant can send only token data block A (28 bytes) or both Token data block A and B (56 bytes) in the request for Cryptogram Token Data. |
| REQ-SRC-ATL105-PDF-001:2110 | SEGMENT_123_FIELD_RULE | BR-478-2 | 478 | 90% | Cryptogram Token Data is used for In App payment tokenization transactions. |
| REQ-SRC-ATL105-PDF-001:2111 | DIRECT_SEGMENT_123 | BR-478-3 | 478 | 95% | Safekey Data is used in SafeKey payment tokenization transactions and appears in Data Segment No. 123 (NFC Payment Tokenization Data Segment). |
| REQ-SRC-ATL105-PDF-001:2112 | SEGMENT_123_FIELD_RULE | BR-478-4 | 478 | 95% | For Safekey Data, positions 1-2 must be fixed value 'SK' to indicate a SafeKey cryptogram transaction. |
| REQ-SRC-ATL105-PDF-001:2113 | SEGMENT_123_FIELD_RULE | BR-478-5 | 478 | 90% | For Safekey Data, positions 3-30 contain SafeKey AEVV in Base64 format and must be space-filled when not applicable. |
| REQ-SRC-ATL105-PDF-001:2114 | SEGMENT_123_FIELD_RULE | BR-478-6 | 478 | 90% | For Safekey Data, positions 31-58 contain SafeKey AESK in Base64 format and must be space-filled when not applicable. |
| REQ-SRC-ATL105-PDF-001:2115 | SEGMENT_123_FIELD_RULE | BR-479-1 | 479 | 90% | When eCommerce Secure ID POS Condition Code is 25 or 26, indicator SK is used with mandatory 28 bytes of AEVV followed by optional 28 bytes of AESK. |
| REQ-SRC-ATL105-PDF-001:2116 | SEGMENT_123_FIELD_RULE | BR-479-2 | 479 | 90% | When eCommerce Secure ID POS Condition Code is 27, indicator SK is used with optional AEVV and optional AESK (each 28 bytes). |
| REQ-SRC-ATL105-PDF-001:2117 | SEGMENT_123_FIELD_RULE | BR-479-3 | 479 | 85% | If Optional AEVV/AESK data is received as spaces from merchants, AEVV and AESK values will be in Base 64 format. |
| REQ-SRC-ATL105-PDF-001:2118 | DIRECT_SEGMENT_123 | BR-479-4 | 479 | 95% | SafeKey Response (Element 204) indicates the AEVV validation result and appears in Data Segment No. 123 (NFC Payment Tokenization Data Segment). |
| REQ-SRC-ATL105-PDF-001:2119 | SEGMENT_123_FIELD_RULE | BR-479-5 | 479 | 95% | SafeKey Response is a fixed-length alphanumeric field of 1 byte with valid values 0-8 (0, 5, 6 reserved for future use). |
| REQ-SRC-ATL105-PDF-001:2157 | DIRECT_SEGMENT_123 | BR-489-3 | 489 | 95% | TAVV Cryptogram appears in Data Segment No. 123 (NFC Payment Tokenization Data Segment) and is used for payment token-based card on file, Digital Secure Payment Cryptogram, e-commerce and application-based e-commerce transactions. |
| REQ-SRC-ATL105-PDF-001:2158 | SEGMENT_123_FIELD_RULE | BR-489-4 | 489 | 90% | For TAVV cryptogram usage in a Visa transaction, refer to the Verified by Visa section in Appendix Y (3-D Secure). For MasterCard, refer to the MasterCard Digital Secure Remote Payment section in Appendix Y. |
| REQ-SRC-ATL105-PDF-001:2160 | DIRECT_SEGMENT_123 | BR-490-2 | 490 | 95% | TAVV Result Code appears in Data Segment No. 123, NFC Payment Tokenization Data Segment. |
| REQ-SRC-ATL105-PDF-001:2183 | SEGMENT_123_FIELD_RULE | BR-499-3 | 499 | 80% | Standard Message Data Segment (100) length is 78 bytes including field separators in this example. |
| REQ-SRC-ATL105-PDF-001:2185 | SEGMENT_123_FIELD_RULE | BR-501-2 | 501 | 85% | The Product Code Data Segment (102) length in this example is 49 bytes including Field Separators and Product Data Field Delimiters. |
| REQ-SRC-ATL105-PDF-001:2244 | SEGMENT_123_FIELD_RULE | BR-548-1 | 548 | 90% | When a DTVV (three-digit token cryptogram) is present in the Visa authorization request, Visa validates it and declines the transaction if invalid. |
| REQ-SRC-ATL105-PDF-001:2245 | SEGMENT_123_FIELD_RULE | BR-548-2 | 548 | 90% | When a DTVV is not present, Visa forwards the transaction to the issuer to make the authorization decision without a cryptogram validation result. |
| REQ-SRC-ATL105-PDF-001:2246 | SEGMENT_123_FIELD_RULE | BR-548-3 | 548 | 80% | Browsers use a three-digit DTVV cryptogram collected by merchants that support CVV2 and sent in the same manner as a CVV2; transactions without a token cryptogram are also supported where the merchant does not collect/submit CVV2. |
| REQ-SRC-ATL105-PDF-001:2340 | DIRECT_SEGMENT_123 | BR-576-4 | 576 | 21% | For MasterCard Token and Digital Secure Remote Payment (DSRP) cryptogram, use the TAVV cryptogram field in segment 123 instead of UCAF Data. |
| REQ-SRC-ATL105-PDF-001:2417 | DIRECT_SEGMENT_123 | BR-596-1 | 596 | 21% | The Remote Commerce Acceptor Identifier is applicable only to MasterCard transactions where the merchant is also sending the DSRP or Token Authentication Verification Value (TAVV) data field in segment 123. |
| REQ-SRC-ATL105-PDF-001:2703 | SEGMENT_123_FIELD_RULE | BR-681-4 | 681 | 84% | Cryptogram Token Data may be sent as 28 bytes (treated as token data block A) or 56 bytes (treated as token data block A and token data block B) in base64 format. |
| REQ-SRC-ATL105-PDF-001:2760 | SEGMENT_123_FIELD_RULE | BR-703-2 | 703 | 74% | Cryptographic value returned depends on card brand: Visa returns CAVV, MasterCard returns AAV, Amex returns AEVV. |
| REQ-SRC-ATL105-PDF-001:2761 | SEGMENT_123_FIELD_RULE | BR-704-1 | 704 | 90% | For Verified-by-Visa only transactions, use CAVV as defined in Table ID: 035 (Verified By Visa Data) of Appendix I; TAVV is not applicable. |
| REQ-SRC-ATL105-PDF-001:2762 | SEGMENT_123_FIELD_RULE | BR-704-2 | 704 | 90% | For Digital Wallet (Payment Token) only transactions, use CAVV â€“ Revised Format (Data Element 195); TAVV is not applicable. |
| REQ-SRC-ATL105-PDF-001:2763 | SEGMENT_123_FIELD_RULE | BR-704-3 | 704 | 90% | For combined Verified-by-Visa and Digital Wallet transactions, populate both CAVV â€“ Revised Format (Data Element 195) and Digital Wallet Payment Token Cryptogram TAVV (Data Element 237). |
| REQ-SRC-ATL105-PDF-001:2764 | SEGMENT_123_FIELD_RULE | BR-704-4 | 704 | 85% | Effective October 2018, Visa supports two separate cryptograms in the authorization message: the TAVV Cryptogram for payment token validation and the 3DS CAVV cryptogram for cardholder authentication. |
| REQ-SRC-ATL105-PDF-001:2765 | SEGMENT_123_FIELD_RULE | BR-704-5 | 704 | 90% | Amex SafeKey transactions from merchants are sent with the indicator 'SK' followed by 56 bytes of SafeKey data in base64 format; when the first 28 bytes are not applicable, they must be space filled. |
| REQ-SRC-ATL105-PDF-001:2766 | SEGMENT_123_FIELD_RULE | BR-704-6 | 704 | 85% | Merchants participating in Visa Token Service may optionally support the TAVV in combination with 3-D Secure (3DS) CAVV for payment token-based transactions with 3-D Secure. |
| REQ-SRC-ATL105-PDF-001:2768 | DIRECT_SEGMENT_123 | BR-705-2 | 705 | 92% | When both DSRP cryptogram and SecureCode/Identity Check AAV are present in the same request message, the UCAF Security level code (position 1-2) must be '21' (Channel Encrypted). |
| REQ-SRC-ATL105-PDF-001:2769 | DIRECT_SEGMENT_123 | BR-705-3 | 705 | 92% | Merchants must use the UCAF data field in Data Segment 111 (table ID 36) for the MasterCard AAV cryptogram, and must use the TAVV field in Segment 123 (Element 237) for all Token and DSRP cryptograms. |
| REQ-SRC-ATL105-PDF-001:2770 | SEGMENT_123_FIELD_RULE | BR-705-4 | 705 | 90% | American Express SafeKey is not available for OptBlue merchants. |
| REQ-SRC-ATL105-PDF-001:2772 | DIRECT_SEGMENT_123 | BR-705-6 | 705 | 88% | When only a Token or DSRP cryptogram is present, populate the Digital Wallet Payment Token Cryptogram (TAVV) in Data Segment 123 Element 237. |
