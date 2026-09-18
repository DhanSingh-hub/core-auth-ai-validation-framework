# POC AI Segment 103 Business Requirements

Source: `step5_requirements/approved/requirement_catalog.json`

Scope: rules whose source rule is explicitly related to ENT-SEG-103 or a direct field of that segment in the approved POC extraction.

Count: 131

| ID | Scope | Source rule | Page | Confidence | Requirement |
|---|---|---|---:|---:|---|
| REQ-SRC-ATL105-PDF-001:254 | SEGMENT_103_FIELD_RULE | BR-7-4 | 7 | 74% | Adjusted Product Code Data Segment (No. 157) added; data elements 76, 77, 81, 84, 85, 87, 106, and 107 updated with references to data segment 157. |
| REQ-SRC-ATL105-PDF-001:640 | DIRECT_SEGMENT_103 | BR-110-1 | 110 | 90% | The device does not perform expiration date validation in an EBT card transaction. |
| REQ-SRC-ATL105-PDF-001:641 | DIRECT_SEGMENT_103 | BR-110-2 | 110 | 95% | All EBT transactions require PIN entry except Food Stamp Electronic Voucher, Food Stamp Reversal (Void), and Cash Benefit Reversal (Void) transactions. |
| REQ-SRC-ATL105-PDF-001:644 | DIRECT_SEGMENT_103 | BR-110-5 | 110 | 90% | There is no stand-in mode for EBT transactions; USDA guidelines do not allow off-line processing, and no defined SAF EBT transaction format exists. If the retailer supports SAF EBT, retailer assumes all risk. |
| REQ-SRC-ATL105-PDF-001:646 | DIRECT_SEGMENT_103 | BR-110-7 | 110 | 90% | The balance field must not print on the receipt (do not print zeros). |
| REQ-SRC-ATL105-PDF-001:647 | DIRECT_SEGMENT_103 | BR-110-8 | 110 | 90% | EBT processing supports Food stamps and Cash benefits tender types. |
| REQ-SRC-ATL105-PDF-001:648 | SEGMENT_103_FIELD_RULE | BR-112-1 | 112 | 90% | Electronic vouchers must prompt for Account Number, Approval Number, and Voucher Number. |
| REQ-SRC-ATL105-PDF-001:652 | SEGMENT_103_FIELD_RULE | BR-112-5 | 112 | 90% | The device must allow for entry of the Voucher Number from the manual voucher ticket. |
| REQ-SRC-ATL105-PDF-001:653 | DIRECT_SEGMENT_103 | BR-112-6 | 112 | 95% | Supported EBT transaction types: Food Stamp Purchase, Food Stamp Reversal (Void), Food Stamp Return, Cash Benefit Purchase, Cash Benefit Reversal (Void), Food Stamp Balance Inquiry, Food Stamp Electronic Voucher, Cash Benefit Purchase with Cash Back, Cash Benefit Balance Inquiry, Time-out Reversal, Food Stamp Void of a Merchandise Return. |
| REQ-SRC-ATL105-PDF-001:655 | SEGMENT_103_FIELD_RULE | BR-113-2 | 113 | 28% | The food stamp dollar amount must be printed on a Food Stamp transaction receipt. |
| REQ-SRC-ATL105-PDF-001:657 | SEGMENT_103_FIELD_RULE | BR-113-2 | 113 | 90% | EBT card receipts must include Tender Type and Transaction Type, Clerk ID, Voucher Number, Transaction amounts, Balances, Approved messages, Declined messages, and Approved HIP transaction data. |
| REQ-SRC-ATL105-PDF-001:660 | SEGMENT_103_FIELD_RULE | BR-113-5 | 113 | 90% | Clerk ID is an optional EBT receipt requirement; may be retrieved via prompting or log on/log off and printed on receipt. |
| REQ-SRC-ATL105-PDF-001:661 | SEGMENT_103_FIELD_RULE | BR-113-6 | 113 | 95% | For Food Stamp Electronic Voucher transactions only, the manually entered Voucher Number must appear on the receipt. |
| REQ-SRC-ATL105-PDF-001:694 | SEGMENT_103_FIELD_RULE | BR-119-3 | 119 | 79% | eWIC card receipts must include Payment Tender Type, Transaction Type, Clerk ID, Voucher ID, Transaction amounts, Balances, Approved messages, Declined messages, and Benefit expiration date (Earliest WIC Benefit Expiration Date subelement of Element 154 WIC Product Data). |
| REQ-SRC-ATL105-PDF-001:697 | SEGMENT_103_FIELD_RULE | BR-120-2 | 120 | 85% | Additional balances are returned providing the incentive earned for the transaction and month-to-date incentive earned; balances are reset at the beginning of the fiscal month. |
| REQ-SRC-ATL105-PDF-001:698 | DIRECT_SEGMENT_103 | BR-120-3 | 120 | 90% | Data element No. 164 (EBT Program Data) is used in the EBT Data Segment (Segment No. 103) to support the HIP feature. |
| REQ-SRC-ATL105-PDF-001:923 | DIRECT_SEGMENT_103 | BR-163-3 | 163 | 90% | Data Section No. 3 of a Financial Transaction Request contains none, one, or more of the following data segments: Fleet (101), Product Code (102), EBT (103), Purchase Card (104), Variable Information (111), per the Data Section No. 3 table. |
| REQ-SRC-ATL105-PDF-001:929 | DIRECT_SEGMENT_103 | BR-165-3 | 165 | 90% | EBT Data Segment (103) is sent only on transactions requiring EBT data. |
| REQ-SRC-ATL105-PDF-001:936 | DIRECT_SEGMENT_103 | BR-165-10 | 165 | 85% | Segments 101â€“145 in Financial Transaction Request occupy Field Nos. 4â€“9 with specified maximum lengths (101:308, 102:381, 103:3334, 104:86, 111:999, 123:186, 135:110, 143:399, 145:999). |
| REQ-SRC-ATL105-PDF-001:1053 | DIRECT_SEGMENT_103 | BR-203-7 | 203 | 90% | Segment 103 (EBT Data Segment) is sent only on transactions requiring EBT data. |
| REQ-SRC-ATL105-PDF-001:1072 | SEGMENT_103_FIELD_RULE | BR-211-5 | 211 | 95% | Segment Type field 3 of Data Segment 132 has fixed value 132. |
| REQ-SRC-ATL105-PDF-001:1089 | SEGMENT_103_FIELD_RULE | BR-220-4 | 220 | 97% | Field 1 Segment Type in Segment 100 has fixed value 100. |
| REQ-SRC-ATL105-PDF-001:1090 | SEGMENT_103_FIELD_RULE | BR-220-5 | 220 | 90% | Segment Length includes the Segment Type's length and Field Separators. |
| REQ-SRC-ATL105-PDF-001:1095 | SEGMENT_103_FIELD_RULE | BR-223-5 | 223 | 95% | Segment Type field in the Fleet Data Segment has fixed value 101. |
| REQ-SRC-ATL105-PDF-001:1096 | SEGMENT_103_FIELD_RULE | BR-223-6 | 223 | 90% | Segment Length field identifies the data segment's length, including the Segment Type field's length and Field Separators. |
| REQ-SRC-ATL105-PDF-001:1115 | SEGMENT_103_FIELD_RULE | BR-226-12 | 226 | 90% | A Field Separator always follows Segment Type and Segment Length. A Field Separator follows Product Amount when Product Amount is the very last element in the segment. When a field is not populated, still send the Field Separator. |
| REQ-SRC-ATL105-PDF-001:1126 | DIRECT_SEGMENT_103 | BR-229-1 | 229 | 95% | When a Financial Transaction request includes the EBT Data Segment, all fields are separated by Field Separators; when a field is not populated, still send the Field Separator. |
| REQ-SRC-ATL105-PDF-001:1127 | DIRECT_SEGMENT_103 | BR-229-2 | 229 | 95% | When a Financial Transaction response includes the EBT Data Segment, there is no Field Separator between fields in this message. |
| REQ-SRC-ATL105-PDF-001:1128 | DIRECT_SEGMENT_103 | BR-229-3 | 229 | 95% | Segment Length should be '4' for EBT with eWIC data transactions. |
| REQ-SRC-ATL105-PDF-001:1129 | DIRECT_SEGMENT_103 | BR-229-4 | 229 | 95% | EBT Data Segment has a maximum length of 3,334 alphanumeric characters (01â€“3,334/aâ€“z/Aâ€“Z). |
| REQ-SRC-ATL105-PDF-001:1130 | DIRECT_SEGMENT_103 | BR-229-5 | 229 | 90% | EBT Data Segment originates at the device and can appear in any of the fields in Data Section No. 3. |
| REQ-SRC-ATL105-PDF-001:1131 | DIRECT_SEGMENT_103 | BR-229-6 | 229 | 98% | Segment Type is a fixed value of 103 for the EBT Data Segment. |
| REQ-SRC-ATL105-PDF-001:1135 | SEGMENT_103_FIELD_RULE | BR-230-4 | 230 | 95% | Segment Type field is fixed value 104 for the Purchase Card Data Segment. |
| REQ-SRC-ATL105-PDF-001:1141 | SEGMENT_103_FIELD_RULE | BR-232-5 | 232 | 95% | For Totals Data Segment, Segment Type (Field 1) has fixed value 105. |
| REQ-SRC-ATL105-PDF-001:1148 | SEGMENT_103_FIELD_RULE | BR-235-4 | 235 | 95% | Segment Type in Loyalty Card Data Segment is fixed value 108. |
| REQ-SRC-ATL105-PDF-001:1153 | SEGMENT_103_FIELD_RULE | BR-237-5 | 237 | 95% | Segment Type (field 1) has fixed value 109 for the Electronic Mail Data Segment. |
| REQ-SRC-ATL105-PDF-001:1167 | SEGMENT_103_FIELD_RULE | BR-243-2 | 243 | 95% | Segment Type for Data Segment No. 111 has fixed value 111. |
| REQ-SRC-ATL105-PDF-001:1174 | SEGMENT_103_FIELD_RULE | BR-245-2 | 245 | 95% | Segment Type in segment 112 must be the fixed value 112. |
| REQ-SRC-ATL105-PDF-001:1180 | SEGMENT_103_FIELD_RULE | BR-246-5 | 246 | 95% | Segment Type (field 1) is fixed value 113 for the ECA/TeleCheck Data Segment. |
| REQ-SRC-ATL105-PDF-001:1190 | SEGMENT_103_FIELD_RULE | BR-249-5 | 249 | 95% | Segment Type field is a fixed value of 115 for the Print Data Segment. |
| REQ-SRC-ATL105-PDF-001:1196 | SEGMENT_103_FIELD_RULE | BR-251-5 | 251 | 98% | Segment Type field for Proprietary Data Load Segment has a fixed value of 118. |
| REQ-SRC-ATL105-PDF-001:1212 | SEGMENT_103_FIELD_RULE | BR-255-1 | 255 | 95% | When Prompt Code = 902 (Dynamic Card Table Data), Field 14 contains Card Table Data (max 3,600 bytes, Required); its length is determined by the segment length in field 2. |
| REQ-SRC-ATL105-PDF-001:1235 | SEGMENT_103_FIELD_RULE | BR-260-4 | 260 | 95% | In segment 119, Segment Type has fixed value 119. |
| REQ-SRC-ATL105-PDF-001:1248 | SEGMENT_103_FIELD_RULE | BR-263-6 | 263 | 95% | Segment Type field contains fixed value '120' for the Print Data 2 Segment. |
| REQ-SRC-ATL105-PDF-001:1253 | SEGMENT_103_FIELD_RULE | BR-264-5 | 264 | 95% | Segment Type (Field 1) must be the fixed value 123. |
| REQ-SRC-ATL105-PDF-001:1277 | SEGMENT_103_FIELD_RULE | BR-271-4 | 271 | 95% | Segment Type field for CA Public Key File Data Segment has fixed value 132. |
| REQ-SRC-ATL105-PDF-001:1282 | SEGMENT_103_FIELD_RULE | BR-274-1 | 274 | 90% | Field Nos 1 and 2, and 2 and 3, are separated by Field Separators. The segment should end with a field separator. Data fields contained within field 3 are not separated by Field Separators. |
| REQ-SRC-ATL105-PDF-001:1283 | SEGMENT_103_FIELD_RULE | BR-274-2 | 274 | 95% | Segment Type for Data Segment 135 has a fixed value of 135. |
| REQ-SRC-ATL105-PDF-001:1285 | SEGMENT_103_FIELD_RULE | BR-275-1 | 275 | 95% | Segment Type for Moneris Data (Response) Segment must have the fixed value 136. |
| REQ-SRC-ATL105-PDF-001:1290 | SEGMENT_103_FIELD_RULE | BR-276-2 | 276 | 95% | Segment Type must be the fixed value 139 for the Moneris Day End Batch Balance Request Segment. |
| REQ-SRC-ATL105-PDF-001:1291 | SEGMENT_103_FIELD_RULE | BR-276-3 | 276 | 90% | Segment Length includes Segment Type's length and Field Separators. |
| REQ-SRC-ATL105-PDF-001:1293 | SEGMENT_103_FIELD_RULE | BR-279-2 | 279 | 95% | Segment Type field 1 has fixed value 141. |
| REQ-SRC-ATL105-PDF-001:1298 | SEGMENT_103_FIELD_RULE | BR-281-2 | 281 | 95% | Segment Type field in Segment 142 has a fixed value of 142. |
| REQ-SRC-ATL105-PDF-001:1356 | SEGMENT_103_FIELD_RULE | BR-295-4 | 295 | 95% | Segment Type field of Data Segment 146 must be fixed value '146'. |
| REQ-SRC-ATL105-PDF-001:1391 | SEGMENT_103_FIELD_RULE | BR-301-3 | 301 | 95% | Within Segment 148, Segment Type (field 1) has fixed value 148. |
| REQ-SRC-ATL105-PDF-001:1399 | SEGMENT_103_FIELD_RULE | BR-305-3 | 305 | 95% | Segment Type field must be fixed value 151 for the Request InComm OTC Market Basket Data Segment. |
| REQ-SRC-ATL105-PDF-001:1402 | SEGMENT_103_FIELD_RULE | BR-306-1 | 306 | 95% | Segment Type for Data Segment 152 has a fixed value of 152. |
| REQ-SRC-ATL105-PDF-001:1407 | SEGMENT_103_FIELD_RULE | BR-307-2 | 307 | 95% | Segment Type for Network Token Data Request Segment is fixed at value 153. |
| REQ-SRC-ATL105-PDF-001:1419 | SEGMENT_103_FIELD_RULE | BR-310-5 | 310 | 95% | Segment Type in segment 155 is a fixed value of 155, length 3, required. |
| REQ-SRC-ATL105-PDF-001:1460 | SEGMENT_103_FIELD_RULE | BR-320-12 | 320 | 85% | In Segment 157, a Field Separator always follows Segment Type and Segment Length; a Field Separator follows Adjusted Product Amount only when it is the very last element; when a field is not populated, still send the Field Separator. |
| REQ-SRC-ATL105-PDF-001:1467 | SEGMENT_103_FIELD_RULE | BR-321-5 | 321 | 98% | Segment Type for the Adjusted Product Code Data Segment has fixed value 157. |
| REQ-SRC-ATL105-PDF-001:1507 | SEGMENT_103_FIELD_RULE | BR-333-5 | 333 | 90% | Segment Length Indicator indicates the data segment's length exclusive of the Data Type Indicator. |
| REQ-SRC-ATL105-PDF-001:1599 | SEGMENT_103_FIELD_RULE | BR-368-4 | 368 | 90% | When identifying a Clerk ID for ECA/TeleCheck processing, refer to Data Element No. 131, ECA/TeleCheck Clerk ID, instead of Data Element 18. |
| REQ-SRC-ATL105-PDF-001:1600 | SEGMENT_103_FIELD_RULE | BR-368-5 | 368 | 90% | Clerk ID is variable length up to 10 digits; valid values 1â€“9999999999. |
| REQ-SRC-ATL105-PDF-001:1658 | DIRECT_SEGMENT_103 | BR-384-3 | 384 | 95% | For Financial Transaction Requests, zero, one, or more of Data Segments 101 (Fleet), 102 (Product Code), 103 (EBT), 104 (Purchase Card), and 111 (Variable Information) may also follow. |
| REQ-SRC-ATL105-PDF-001:1745 | DIRECT_SEGMENT_103 | BR-399-1 | 399 | 95% | Segment Length has a length of four digits only when transmitting EBT Data Segment (103), SKU Data Segment (114), Print Data Segment (115), Proprietary Data Load Segment (118), Print Data 2 Segment (120), EMV Request Data Segment (130), or EMV Response Data Segment (131). It cannot be 4 digits for any other segment. |
| REQ-SRC-ATL105-PDF-001:1746 | SEGMENT_103_FIELD_RULE | BR-399-2 | 399 | 95% | Segment Length is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements. |
| REQ-SRC-ATL105-PDF-001:1747 | SEGMENT_103_FIELD_RULE | BR-400-1 | 400 | 90% | Standard Message Data Segment (No. 100) Segment Length valid values are 001â€“218. |
| REQ-SRC-ATL105-PDF-001:1748 | SEGMENT_103_FIELD_RULE | BR-400-2 | 400 | 90% | Fleet Data Segment (No. 101) Segment Length valid values are 001â€“61. |
| REQ-SRC-ATL105-PDF-001:1749 | SEGMENT_103_FIELD_RULE | BR-400-3 | 400 | 90% | Product Code Data Segment (No. 102) Segment Length valid values are 001â€“381. |
| REQ-SRC-ATL105-PDF-001:1750 | DIRECT_SEGMENT_103 | BR-400-4 | 400 | 90% | EBT Data Segment (No. 103) Segment Length valid values are 001â€“3334. |
| REQ-SRC-ATL105-PDF-001:1751 | SEGMENT_103_FIELD_RULE | BR-400-5 | 400 | 90% | Purchase Card Data Segment (No. 104) Segment Length valid values are 001â€“86. |
| REQ-SRC-ATL105-PDF-001:1752 | SEGMENT_103_FIELD_RULE | BR-400-6 | 400 | 90% | Totals Data Segment (No. 105) Segment Length valid values are 001â€“409. |
| REQ-SRC-ATL105-PDF-001:1753 | SEGMENT_103_FIELD_RULE | BR-400-7 | 400 | 90% | Loyalty Card Data Segment (No. 108) Segment Length valid values are 001â€“142. |
| REQ-SRC-ATL105-PDF-001:1754 | SEGMENT_103_FIELD_RULE | BR-400-8 | 400 | 90% | Electronic Mail Data Segment (No. 109) Segment Length valid values are 001â€“232. |
| REQ-SRC-ATL105-PDF-001:1755 | SEGMENT_103_FIELD_RULE | BR-400-9 | 400 | 90% | Check Data Segment (No. 110) Segment Length valid values are 001â€“168. |
| REQ-SRC-ATL105-PDF-001:1756 | SEGMENT_103_FIELD_RULE | BR-400-10 | 400 | 90% | Variable Information Data Segment (No. 111) Segment Length valid values are 001â€“999. |
| REQ-SRC-ATL105-PDF-001:1757 | SEGMENT_103_FIELD_RULE | BR-400-11 | 400 | 90% | Additional Information Data Segment (No. 112) Segment Length valid values are 001â€“999. |
| REQ-SRC-ATL105-PDF-001:1758 | SEGMENT_103_FIELD_RULE | BR-400-12 | 400 | 90% | ECA/TeleCheck Data Segment (No. 113) Segment Length valid values are 001â€“156. |
| REQ-SRC-ATL105-PDF-001:1759 | SEGMENT_103_FIELD_RULE | BR-400-13 | 400 | 90% | SKU Data Segment (No. 114) Segment Length valid values are 0001â€“1010. |
| REQ-SRC-ATL105-PDF-001:1760 | SEGMENT_103_FIELD_RULE | BR-400-14 | 400 | 90% | Print Data Segment (No. 115) Segment Length valid values are 001-1009. |
| REQ-SRC-ATL105-PDF-001:1761 | SEGMENT_103_FIELD_RULE | BR-400-15 | 400 | 90% | TransArmor Load Data Segment (No. 116) Segment Length valid values are 01â€“50. |
| REQ-SRC-ATL105-PDF-001:1762 | SEGMENT_103_FIELD_RULE | BR-400-16 | 400 | 90% | Proprietary Data Load Segment (No. 118) Segment Length valid values are 0001-3800. |
| REQ-SRC-ATL105-PDF-001:1763 | SEGMENT_103_FIELD_RULE | BR-400-17 | 400 | 90% | Totals with Proprietary Data Load Data Segment (No. 119) Segment Length valid values are 001-493. |
| REQ-SRC-ATL105-PDF-001:1764 | SEGMENT_103_FIELD_RULE | BR-400-18 | 400 | 90% | Print Data 2 Segment (No. 120) Segment Length valid values are 001-1009. |
| REQ-SRC-ATL105-PDF-001:1765 | SEGMENT_103_FIELD_RULE | BR-400-19 | 400 | 90% | NFC Payment Tokenization Data Segment (No. 123) Segment Length valid values are 001-186. |
| REQ-SRC-ATL105-PDF-001:1766 | SEGMENT_103_FIELD_RULE | BR-400-20 | 400 | 90% | EMV Request Data Segment (No.130) Segment Length valid values are 001-3043. |
| REQ-SRC-ATL105-PDF-001:1767 | SEGMENT_103_FIELD_RULE | BR-400-21 | 400 | 90% | EMV Response Data Segment (No. 131) Segment Length valid values are 001-3834. |
| REQ-SRC-ATL105-PDF-001:1768 | SEGMENT_103_FIELD_RULE | BR-400-22 | 400 | 90% | CA Public Key File Data Segment (No. 132) Segment Length valid values are 01-77. |
| REQ-SRC-ATL105-PDF-001:1769 | SEGMENT_103_FIELD_RULE | BR-400-23 | 400 | 90% | Transaction Attributes Data Segment (No. 134) Segment Length valid values are 01-19. |
| REQ-SRC-ATL105-PDF-001:1770 | SEGMENT_103_FIELD_RULE | BR-400-24 | 400 | 90% | Adjusted Product Code Data Segment (No. 157) Segment Length valid values are 001â€“381. |
| REQ-SRC-ATL105-PDF-001:1771 | SEGMENT_103_FIELD_RULE | BR-401-1 | 401 | 90% | Segment Type is required for the TransArmor PKI Encryption and Tokenization Load Request. |
| REQ-SRC-ATL105-PDF-001:1772 | SEGMENT_103_FIELD_RULE | BR-401-2 | 401 | 95% | Segment Type is a fixed length of three digits (N, max 3 bytes). |
| REQ-SRC-ATL105-PDF-001:1823 | DIRECT_SEGMENT_103 | BR-415-1 | 415 | 85% | Voucher ID is required by some states when an EBT transaction is approved locally. |
| REQ-SRC-ATL105-PDF-001:1989 | DIRECT_SEGMENT_103 | BR-445-1 | 445 | 90% | WIC Discount Amount appears in the EBT Data Segment (Segment No. 103). |
| REQ-SRC-ATL105-PDF-001:1990 | SEGMENT_103_FIELD_RULE | BR-445-2 | 445 | 90% | WIC Discount Amount subfield structure: positions 1-2 Account Type (value 97), 3-4 Amount Type (value 52), 5-7 Currency Code (per Appendix L), 8-20 Amount formatted as x+n12 where x=0 or C (credit amount) or D (debit amount). |
| REQ-SRC-ATL105-PDF-001:1991 | SEGMENT_103_FIELD_RULE | BR-446-1 | 446 | 90% | WIC Product Data is composed of a Total Length subelement plus one or more of: Earliest WIC Benefit Expiration Date, WIC Prescription Balance Information, WIC UPC Exception/Denial Information, WIC UPC Purchase Information. |
| REQ-SRC-ATL105-PDF-001:1992 | SEGMENT_103_FIELD_RULE | BR-446-2 | 446 | 90% | Earliest WIC Benefit Expiration Date is included in the eWIC Balance Inquiry Response, eWIC Authorization Response, and eWIC Purchase Completion Response. |
| REQ-SRC-ATL105-PDF-001:1993 | SEGMENT_103_FIELD_RULE | BR-446-3 | 446 | 90% | WIC Prescription Balance is included in the eWIC Balance Inquiry Response, eWIC Authorization Response, and eWIC Purchase Completion Response. |
| REQ-SRC-ATL105-PDF-001:1994 | SEGMENT_103_FIELD_RULE | BR-446-4 | 446 | 90% | WIC UPC Exception/Denial Information is included in the eWIC Purchase Completion Response and eWIC Voucher Clear Response. |
| REQ-SRC-ATL105-PDF-001:1995 | SEGMENT_103_FIELD_RULE | BR-446-5 | 446 | 90% | For WIC purchases involving fruits and vegetables via the Cash Value Benefit (CVB), set the Quantity equal to the price in the WIC Purchase Information. |
| REQ-SRC-ATL105-PDF-001:1996 | SEGMENT_103_FIELD_RULE | BR-446-6 | 446 | 90% | When WIC Purchase price exceeds the state-defined APL value, an exception record is returned in WIC Product Data reflecting both approved and original prices; the Settlement Amount is adjusted and returned in the Approved Amount field. |
| REQ-SRC-ATL105-PDF-001:1998 | SEGMENT_103_FIELD_RULE | BR-447-2 | 447 | 21% | Earliest WIC Benefit Expiration Date must be formatted as CCYYMMDD (8 numeric bytes) and identified with Data Set Identifier 'EF'. |
| REQ-SRC-ATL105-PDF-001:2005 | SEGMENT_103_FIELD_RULE | BR-451-1 | 451 | 80% | If Item Action Code (Bit No. 8) is '00', the product was approved but the maximum price was exceeded; the Item price field (Bit No. 6) contains the price. |
| REQ-SRC-ATL105-PDF-001:2006 | SEGMENT_103_FIELD_RULE | BR-451-2 | 451 | 85% | The exact length of the UPC/PLU data (Bit No. 2), measured from the right-most digit, is specified in the UPC/PLU data length field (Bit No. 11). |
| REQ-SRC-ATL105-PDF-001:2007 | SEGMENT_103_FIELD_RULE | BR-451-3 | 451 | 90% | The first position of the UPC/PLU data (Bit No. 2) indicates data type: 0 = UPC, 1 = PLU. |
| REQ-SRC-ATL105-PDF-001:2023 | SEGMENT_103_FIELD_RULE | BR-456-1 | 456 | 90% | EBT Program Data element has a variable maximum length of up to 267 alphanumeric bytes composed of Total Length (fixed 3 digits, appears once) and Program Data (max 264 alphanumeric bytes, 1-6 subelements each max 44 bytes). |
| REQ-SRC-ATL105-PDF-001:2024 | DIRECT_SEGMENT_103 | BR-456-2 | 456 | 95% | EBT Program Data element is transmitted in Data Segment No. 103 (EBT Data Segment). |
| REQ-SRC-ATL105-PDF-001:2025 | SEGMENT_103_FIELD_RULE | BR-456-3 | 456 | 85% | The total length of the EBT Program Data element is based on the number of Program Data subelements sent/received. |
| REQ-SRC-ATL105-PDF-001:2028 | SEGMENT_103_FIELD_RULE | BR-458-1 | 458 | 90% | ACCOUNT TYPE subfield (positions 5-6, fixed value 98) is not required when TAG = IT. |
| REQ-SRC-ATL105-PDF-001:2029 | SEGMENT_103_FIELD_RULE | BR-458-2 | 458 | 90% | EBT Program Data subelement has a maximum length of 264 bytes, allowing up to 6 occurrences of maximum variable length 44 bytes each. |
| REQ-SRC-ATL105-PDF-001:2030 | SEGMENT_103_FIELD_RULE | BR-458-3 | 458 | 90% | Valid TAG values for requests are 50 (HIP purchase/return amount) and IT (HIP Internet purchase shipping address/zip code). |
| REQ-SRC-ATL105-PDF-001:2031 | SEGMENT_103_FIELD_RULE | BR-458-4 | 458 | 90% | Valid TAG values for responses are 51 (HIP incentive earned/returned) and 52 (HIP month-to-date incentive earned). |
| REQ-SRC-ATL105-PDF-001:2032 | SEGMENT_103_FIELD_RULE | BR-459-1 | 459 | 85% | AMOUNT TYPE (positions 7-8) is not required when TAG = IT. |
| REQ-SRC-ATL105-PDF-001:2033 | SEGMENT_103_FIELD_RULE | BR-459-2 | 459 | 85% | CURRENCY CODE (positions 9-11) has fixed value 840 (US Dollars) and is not required when TAG = IT. |
| REQ-SRC-ATL105-PDF-001:2034 | SEGMENT_103_FIELD_RULE | BR-459-3 | 459 | 85% | AMOUNT DESCRIPTOR (position 12) has fixed value 0/C/D and is not required when TAG = IT. |
| REQ-SRC-ATL105-PDF-001:2035 | SEGMENT_103_FIELD_RULE | BR-459-4 | 459 | 90% | When TAG = 50, 51, or 52, DETAIL (positions 13-24) is a HIP dollar amount with fixed length 12 digits, right-aligned, zero-padded, 2 assumed decimal places. |
| REQ-SRC-ATL105-PDF-001:2036 | SEGMENT_103_FIELD_RULE | BR-459-5 | 459 | 90% | When TAG = IT, DETAIL is a shipping address of fixed length 37 alphanumeric characters: Address portion positions 5-32 (28 chars, left-aligned, space-filled) and Zip Code portion positions 33-41 (max 9 digits, left-aligned, space-filled). |
| REQ-SRC-ATL105-PDF-001:2037 | SEGMENT_103_FIELD_RULE | BR-459-6 | 459 | 90% | EBT Program Data AMOUNT TYPE valid values for requests: 50 (HIP purchase/return amount). |
| REQ-SRC-ATL105-PDF-001:2038 | SEGMENT_103_FIELD_RULE | BR-459-7 | 459 | 90% | EBT Program Data AMOUNT TYPE valid values for responses: 51 (HIP incentive earned/returned), 52 (HIP month-to-date incentive earned). |
| REQ-SRC-ATL105-PDF-001:2183 | SEGMENT_103_FIELD_RULE | BR-499-3 | 499 | 80% | Standard Message Data Segment (100) length is 78 bytes including field separators in this example. |
| REQ-SRC-ATL105-PDF-001:2185 | SEGMENT_103_FIELD_RULE | BR-501-2 | 501 | 85% | The Product Code Data Segment (102) length in this example is 49 bytes including Field Separators and Product Data Field Delimiters. |
| REQ-SRC-ATL105-PDF-001:2674 | SEGMENT_103_FIELD_RULE | BR-676-1 | 676 | 90% | When EBT Program Data (Element 164) is used in a request containing a HIP purchase/return amount, Subelement 1 (Total Length) is formatted n3, right-aligned, zero-filled, and specifies the total length of all data in the entire message (e.g. '024' means 24 characters follow). |
| REQ-SRC-ATL105-PDF-001:2675 | SEGMENT_103_FIELD_RULE | BR-676-2 | 676 | 90% | Within EBT Program Data (Element 164), the TAG subelement value '50' indicates a HIP purchase/return amount follows; TAG is format n2. |
| REQ-SRC-ATL105-PDF-001:2676 | SEGMENT_103_FIELD_RULE | BR-676-3 | 676 | 90% | Within EBT Program Data (Element 164), the LEN subelement is format n2 and indicates the length of the Detail data that follows (e.g. '20' indicates 20 characters follow). |
| REQ-SRC-ATL105-PDF-001:2677 | SEGMENT_103_FIELD_RULE | BR-676-4 | 676 | 85% | Example EBT Program Data value '02450209850840C000000001234' demonstrates Element 164 format for a request containing one set of subelements (a HIP purchase/return amount). |
| REQ-SRC-ATL105-PDF-001:2678 | SEGMENT_103_FIELD_RULE | BR-677-1 | 677 | 80% | When the EBT Program Data element is used in a request containing a HIP Purchase/Return Amount, Subelement 2 ACCOUNT TYPE must be '98' (HIP Account Type). |
| REQ-SRC-ATL105-PDF-001:2679 | SEGMENT_103_FIELD_RULE | BR-677-2 | 677 | 54% | When the EBT Program Data element is used in a request containing a HIP Purchase/Return Amount, AMOUNT TYPE must be '40' (HIP Amount Type). Note: the raw value shown is '50' but the valid value is stated as 40. |
| REQ-SRC-ATL105-PDF-001:2680 | SEGMENT_103_FIELD_RULE | BR-677-3 | 677 | 85% | CURRENCY CODE subfield must be '840' (US Dollars) when used in a HIP Purchase/Return Amount request. |
| REQ-SRC-ATL105-PDF-001:2681 | SEGMENT_103_FIELD_RULE | BR-677-4 | 677 | 90% | AMOUNT DESCRIPTOR must be one of '0', 'C', or 'D', where 0 or C indicate a credit amount and D indicates a debit amount. |
| REQ-SRC-ATL105-PDF-001:2682 | SEGMENT_103_FIELD_RULE | BR-677-5 | 677 | 90% | DETAIL (HIP purchase/return amount) is a 12-digit numeric with two assumed decimal places, right-aligned and zero-padded (e.g., '000000001234' = $12.34). |
