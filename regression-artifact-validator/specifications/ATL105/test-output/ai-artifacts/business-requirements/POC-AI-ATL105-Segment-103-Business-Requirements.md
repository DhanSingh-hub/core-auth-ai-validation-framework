# POC AI Segment 103 Business Requirements

Source: `step5_requirements/approved/requirement_catalog.json`

Scope: rules whose source rule is explicitly related to ENT-SEG-103 or a direct field of that segment in the approved POC extraction.

Count: 131

| ID | Scope | Source rule | Page | Confidence | Requirement |
|---|---|---|---:|---:|---|
| REQ-SRC-ATL105-PDF-001:268 | SEGMENT_103_FIELD_RULE | BR-7-4 | 7 | % | Adjusted Product Code Data Segment (No. 157) added; data elements 76, 77, 81, 84, 85, 87, 106, and 107 updated with references to data segment 157. |
| REQ-SRC-ATL105-PDF-001:654 | DIRECT_SEGMENT_103 | BR-110-1 | 110 | % | The device does not perform expiration date validation in an EBT card transaction. |
| REQ-SRC-ATL105-PDF-001:655 | DIRECT_SEGMENT_103 | BR-110-2 | 110 | % | All EBT transactions require PIN entry except Food Stamp Electronic Voucher, Food Stamp Reversal (Void), and Cash Benefit Reversal (Void) transactions. |
| REQ-SRC-ATL105-PDF-001:658 | DIRECT_SEGMENT_103 | BR-110-5 | 110 | % | There is no stand-in mode for EBT transactions; USDA guidelines do not allow off-line processing, and no defined SAF EBT transaction format exists. If the retailer supports SAF EBT, retailer assumes all risk. |
| REQ-SRC-ATL105-PDF-001:660 | DIRECT_SEGMENT_103 | BR-110-7 | 110 | % | The balance field must not print on the receipt (do not print zeros). |
| REQ-SRC-ATL105-PDF-001:661 | DIRECT_SEGMENT_103 | BR-110-8 | 110 | % | EBT processing supports Food stamps and Cash benefits tender types. |
| REQ-SRC-ATL105-PDF-001:662 | SEGMENT_103_FIELD_RULE | BR-112-1 | 112 | % | Electronic vouchers must prompt for Account Number, Approval Number, and Voucher Number. |
| REQ-SRC-ATL105-PDF-001:666 | SEGMENT_103_FIELD_RULE | BR-112-5 | 112 | % | The device must allow for entry of the Voucher Number from the manual voucher ticket. |
| REQ-SRC-ATL105-PDF-001:667 | DIRECT_SEGMENT_103 | BR-112-6 | 112 | % | Supported EBT transaction types: Food Stamp Purchase, Food Stamp Reversal (Void), Food Stamp Return, Cash Benefit Purchase, Cash Benefit Reversal (Void), Food Stamp Balance Inquiry, Food Stamp Electronic Voucher, Cash Benefit Purchase with Cash Back, Cash Benefit Balance Inquiry, Time-out Reversal, Food Stamp Void of a Merchandise Return. |
| REQ-SRC-ATL105-PDF-001:669 | SEGMENT_103_FIELD_RULE | BR-113-2 | 113 | % | The food stamp dollar amount must be printed on a Food Stamp transaction receipt. |
| REQ-SRC-ATL105-PDF-001:671 | SEGMENT_103_FIELD_RULE | BR-113-2 | 113 | % | EBT card receipts must include Tender Type and Transaction Type, Clerk ID, Voucher Number, Transaction amounts, Balances, Approved messages, Declined messages, and Approved HIP transaction data. |
| REQ-SRC-ATL105-PDF-001:674 | SEGMENT_103_FIELD_RULE | BR-113-5 | 113 | % | Clerk ID is an optional EBT receipt requirement; may be retrieved via prompting or log on/log off and printed on receipt. |
| REQ-SRC-ATL105-PDF-001:675 | SEGMENT_103_FIELD_RULE | BR-113-6 | 113 | % | For Food Stamp Electronic Voucher transactions only, the manually entered Voucher Number must appear on the receipt. |
| REQ-SRC-ATL105-PDF-001:708 | SEGMENT_103_FIELD_RULE | BR-119-3 | 119 | % | eWIC card receipts must include Payment Tender Type, Transaction Type, Clerk ID, Voucher ID, Transaction amounts, Balances, Approved messages, Declined messages, and Benefit expiration date (Earliest WIC Benefit Expiration Date subelement of Element 154 WIC Product Data). |
| REQ-SRC-ATL105-PDF-001:711 | SEGMENT_103_FIELD_RULE | BR-120-2 | 120 | % | Additional balances are returned providing the incentive earned for the transaction and month-to-date incentive earned; balances are reset at the beginning of the fiscal month. |
| REQ-SRC-ATL105-PDF-001:712 | DIRECT_SEGMENT_103 | BR-120-3 | 120 | % | Data element No. 164 (EBT Program Data) is used in the EBT Data Segment (Segment No. 103) to support the HIP feature. |
| REQ-SRC-ATL105-PDF-001:937 | DIRECT_SEGMENT_103 | BR-163-3 | 163 | % | Data Section No. 3 of a Financial Transaction Request contains none, one, or more of the following data segments: Fleet (101), Product Code (102), EBT (103), Purchase Card (104), Variable Information (111), per the Data Section No. 3 table. |
| REQ-SRC-ATL105-PDF-001:943 | DIRECT_SEGMENT_103 | BR-165-3 | 165 | % | EBT Data Segment (103) is sent only on transactions requiring EBT data. |
| REQ-SRC-ATL105-PDF-001:950 | DIRECT_SEGMENT_103 | BR-165-10 | 165 | % | Segments 101â€“145 in Financial Transaction Request occupy Field Nos. 4â€“9 with specified maximum lengths (101:308, 102:381, 103:3334, 104:86, 111:999, 123:186, 135:110, 143:399, 145:999). |
| REQ-SRC-ATL105-PDF-001:1067 | DIRECT_SEGMENT_103 | BR-203-7 | 203 | % | Segment 103 (EBT Data Segment) is sent only on transactions requiring EBT data. |
| REQ-SRC-ATL105-PDF-001:1086 | SEGMENT_103_FIELD_RULE | BR-211-5 | 211 | % | Segment Type field 3 of Data Segment 132 has fixed value 132. |
| REQ-SRC-ATL105-PDF-001:1103 | SEGMENT_103_FIELD_RULE | BR-220-4 | 220 | % | Field 1 Segment Type in Segment 100 has fixed value 100. |
| REQ-SRC-ATL105-PDF-001:1104 | SEGMENT_103_FIELD_RULE | BR-220-5 | 220 | % | Segment Length includes the Segment Type's length and Field Separators. |
| REQ-SRC-ATL105-PDF-001:1109 | SEGMENT_103_FIELD_RULE | BR-223-5 | 223 | % | Segment Type field in the Fleet Data Segment has fixed value 101. |
| REQ-SRC-ATL105-PDF-001:1110 | SEGMENT_103_FIELD_RULE | BR-223-6 | 223 | % | Segment Length field identifies the data segment's length, including the Segment Type field's length and Field Separators. |
| REQ-SRC-ATL105-PDF-001:1129 | SEGMENT_103_FIELD_RULE | BR-226-12 | 226 | % | A Field Separator always follows Segment Type and Segment Length. A Field Separator follows Product Amount when Product Amount is the very last element in the segment. When a field is not populated, still send the Field Separator. |
| REQ-SRC-ATL105-PDF-001:1140 | DIRECT_SEGMENT_103 | BR-229-1 | 229 | % | When a Financial Transaction request includes the EBT Data Segment, all fields are separated by Field Separators; when a field is not populated, still send the Field Separator. |
| REQ-SRC-ATL105-PDF-001:1141 | DIRECT_SEGMENT_103 | BR-229-2 | 229 | % | When a Financial Transaction response includes the EBT Data Segment, there is no Field Separator between fields in this message. |
| REQ-SRC-ATL105-PDF-001:1142 | DIRECT_SEGMENT_103 | BR-229-3 | 229 | % | Segment Length should be '4' for EBT with eWIC data transactions. |
| REQ-SRC-ATL105-PDF-001:1143 | DIRECT_SEGMENT_103 | BR-229-4 | 229 | % | EBT Data Segment has a maximum length of 3,334 alphanumeric characters (01â€“3,334/aâ€“z/Aâ€“Z). |
| REQ-SRC-ATL105-PDF-001:1144 | DIRECT_SEGMENT_103 | BR-229-5 | 229 | % | EBT Data Segment originates at the device and can appear in any of the fields in Data Section No. 3. |
| REQ-SRC-ATL105-PDF-001:1145 | DIRECT_SEGMENT_103 | BR-229-6 | 229 | % | Segment Type is a fixed value of 103 for the EBT Data Segment. |
| REQ-SRC-ATL105-PDF-001:1149 | SEGMENT_103_FIELD_RULE | BR-230-4 | 230 | % | Segment Type field is fixed value 104 for the Purchase Card Data Segment. |
| REQ-SRC-ATL105-PDF-001:1155 | SEGMENT_103_FIELD_RULE | BR-232-5 | 232 | % | For Totals Data Segment, Segment Type (Field 1) has fixed value 105. |
| REQ-SRC-ATL105-PDF-001:1162 | SEGMENT_103_FIELD_RULE | BR-235-4 | 235 | % | Segment Type in Loyalty Card Data Segment is fixed value 108. |
| REQ-SRC-ATL105-PDF-001:1167 | SEGMENT_103_FIELD_RULE | BR-237-5 | 237 | % | Segment Type (field 1) has fixed value 109 for the Electronic Mail Data Segment. |
| REQ-SRC-ATL105-PDF-001:1181 | SEGMENT_103_FIELD_RULE | BR-243-2 | 243 | % | Segment Type for Data Segment No. 111 has fixed value 111. |
| REQ-SRC-ATL105-PDF-001:1188 | SEGMENT_103_FIELD_RULE | BR-245-2 | 245 | % | Segment Type in segment 112 must be the fixed value 112. |
| REQ-SRC-ATL105-PDF-001:1194 | SEGMENT_103_FIELD_RULE | BR-246-5 | 246 | % | Segment Type (field 1) is fixed value 113 for the ECA/TeleCheck Data Segment. |
| REQ-SRC-ATL105-PDF-001:1204 | SEGMENT_103_FIELD_RULE | BR-249-5 | 249 | % | Segment Type field is a fixed value of 115 for the Print Data Segment. |
| REQ-SRC-ATL105-PDF-001:1210 | SEGMENT_103_FIELD_RULE | BR-251-5 | 251 | % | Segment Type field for Proprietary Data Load Segment has a fixed value of 118. |
| REQ-SRC-ATL105-PDF-001:1226 | SEGMENT_103_FIELD_RULE | BR-255-1 | 255 | % | When Prompt Code = 902 (Dynamic Card Table Data), Field 14 contains Card Table Data (max 3,600 bytes, Required); its length is determined by the segment length in field 2. |
| REQ-SRC-ATL105-PDF-001:1249 | SEGMENT_103_FIELD_RULE | BR-260-4 | 260 | % | In segment 119, Segment Type has fixed value 119. |
| REQ-SRC-ATL105-PDF-001:1262 | SEGMENT_103_FIELD_RULE | BR-263-6 | 263 | % | Segment Type field contains fixed value '120' for the Print Data 2 Segment. |
| REQ-SRC-ATL105-PDF-001:1267 | SEGMENT_103_FIELD_RULE | BR-264-5 | 264 | % | Segment Type (Field 1) must be the fixed value 123. |
| REQ-SRC-ATL105-PDF-001:1291 | SEGMENT_103_FIELD_RULE | BR-271-4 | 271 | % | Segment Type field for CA Public Key File Data Segment has fixed value 132. |
| REQ-SRC-ATL105-PDF-001:1296 | SEGMENT_103_FIELD_RULE | BR-274-1 | 274 | % | Field Nos 1 and 2, and 2 and 3, are separated by Field Separators. The segment should end with a field separator. Data fields contained within field 3 are not separated by Field Separators. |
| REQ-SRC-ATL105-PDF-001:1297 | SEGMENT_103_FIELD_RULE | BR-274-2 | 274 | % | Segment Type for Data Segment 135 has a fixed value of 135. |
| REQ-SRC-ATL105-PDF-001:1299 | SEGMENT_103_FIELD_RULE | BR-275-1 | 275 | % | Segment Type for Moneris Data (Response) Segment must have the fixed value 136. |
| REQ-SRC-ATL105-PDF-001:1304 | SEGMENT_103_FIELD_RULE | BR-276-2 | 276 | % | Segment Type must be the fixed value 139 for the Moneris Day End Batch Balance Request Segment. |
| REQ-SRC-ATL105-PDF-001:1305 | SEGMENT_103_FIELD_RULE | BR-276-3 | 276 | % | Segment Length includes Segment Type's length and Field Separators. |
| REQ-SRC-ATL105-PDF-001:1307 | SEGMENT_103_FIELD_RULE | BR-279-2 | 279 | % | Segment Type field 1 has fixed value 141. |
| REQ-SRC-ATL105-PDF-001:1312 | SEGMENT_103_FIELD_RULE | BR-281-2 | 281 | % | Segment Type field in Segment 142 has a fixed value of 142. |
| REQ-SRC-ATL105-PDF-001:1370 | SEGMENT_103_FIELD_RULE | BR-295-4 | 295 | % | Segment Type field of Data Segment 146 must be fixed value '146'. |
| REQ-SRC-ATL105-PDF-001:1405 | SEGMENT_103_FIELD_RULE | BR-301-3 | 301 | % | Within Segment 148, Segment Type (field 1) has fixed value 148. |
| REQ-SRC-ATL105-PDF-001:1413 | SEGMENT_103_FIELD_RULE | BR-305-3 | 305 | % | Segment Type field must be fixed value 151 for the Request InComm OTC Market Basket Data Segment. |
| REQ-SRC-ATL105-PDF-001:1416 | SEGMENT_103_FIELD_RULE | BR-306-1 | 306 | % | Segment Type for Data Segment 152 has a fixed value of 152. |
| REQ-SRC-ATL105-PDF-001:1421 | SEGMENT_103_FIELD_RULE | BR-307-2 | 307 | % | Segment Type for Network Token Data Request Segment is fixed at value 153. |
| REQ-SRC-ATL105-PDF-001:1433 | SEGMENT_103_FIELD_RULE | BR-310-5 | 310 | % | Segment Type in segment 155 is a fixed value of 155, length 3, required. |
| REQ-SRC-ATL105-PDF-001:1474 | SEGMENT_103_FIELD_RULE | BR-320-12 | 320 | % | In Segment 157, a Field Separator always follows Segment Type and Segment Length; a Field Separator follows Adjusted Product Amount only when it is the very last element; when a field is not populated, still send the Field Separator. |
| REQ-SRC-ATL105-PDF-001:1481 | SEGMENT_103_FIELD_RULE | BR-321-5 | 321 | % | Segment Type for the Adjusted Product Code Data Segment has fixed value 157. |
| REQ-SRC-ATL105-PDF-001:1521 | SEGMENT_103_FIELD_RULE | BR-333-5 | 333 | % | Segment Length Indicator indicates the data segment's length exclusive of the Data Type Indicator. |
| REQ-SRC-ATL105-PDF-001:1613 | SEGMENT_103_FIELD_RULE | BR-368-4 | 368 | % | When identifying a Clerk ID for ECA/TeleCheck processing, refer to Data Element No. 131, ECA/TeleCheck Clerk ID, instead of Data Element 18. |
| REQ-SRC-ATL105-PDF-001:1614 | SEGMENT_103_FIELD_RULE | BR-368-5 | 368 | % | Clerk ID is variable length up to 10 digits; valid values 1â€“9999999999. |
| REQ-SRC-ATL105-PDF-001:1672 | DIRECT_SEGMENT_103 | BR-384-3 | 384 | % | For Financial Transaction Requests, zero, one, or more of Data Segments 101 (Fleet), 102 (Product Code), 103 (EBT), 104 (Purchase Card), and 111 (Variable Information) may also follow. |
| REQ-SRC-ATL105-PDF-001:1759 | DIRECT_SEGMENT_103 | BR-399-1 | 399 | % | Segment Length has a length of four digits only when transmitting EBT Data Segment (103), SKU Data Segment (114), Print Data Segment (115), Proprietary Data Load Segment (118), Print Data 2 Segment (120), EMV Request Data Segment (130), or EMV Response Data Segment (131). It cannot be 4 digits for any other segment. |
| REQ-SRC-ATL105-PDF-001:1760 | SEGMENT_103_FIELD_RULE | BR-399-2 | 399 | % | Segment Length is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements. |
| REQ-SRC-ATL105-PDF-001:1761 | SEGMENT_103_FIELD_RULE | BR-400-1 | 400 | % | Standard Message Data Segment (No. 100) Segment Length valid values are 001â€“218. |
| REQ-SRC-ATL105-PDF-001:1762 | SEGMENT_103_FIELD_RULE | BR-400-2 | 400 | % | Fleet Data Segment (No. 101) Segment Length valid values are 001â€“61. |
| REQ-SRC-ATL105-PDF-001:1763 | SEGMENT_103_FIELD_RULE | BR-400-3 | 400 | % | Product Code Data Segment (No. 102) Segment Length valid values are 001â€“381. |
| REQ-SRC-ATL105-PDF-001:1764 | DIRECT_SEGMENT_103 | BR-400-4 | 400 | % | EBT Data Segment (No. 103) Segment Length valid values are 001â€“3334. |
| REQ-SRC-ATL105-PDF-001:1765 | SEGMENT_103_FIELD_RULE | BR-400-5 | 400 | % | Purchase Card Data Segment (No. 104) Segment Length valid values are 001â€“86. |
| REQ-SRC-ATL105-PDF-001:1766 | SEGMENT_103_FIELD_RULE | BR-400-6 | 400 | % | Totals Data Segment (No. 105) Segment Length valid values are 001â€“409. |
| REQ-SRC-ATL105-PDF-001:1767 | SEGMENT_103_FIELD_RULE | BR-400-7 | 400 | % | Loyalty Card Data Segment (No. 108) Segment Length valid values are 001â€“142. |
| REQ-SRC-ATL105-PDF-001:1768 | SEGMENT_103_FIELD_RULE | BR-400-8 | 400 | % | Electronic Mail Data Segment (No. 109) Segment Length valid values are 001â€“232. |
| REQ-SRC-ATL105-PDF-001:1769 | SEGMENT_103_FIELD_RULE | BR-400-9 | 400 | % | Check Data Segment (No. 110) Segment Length valid values are 001â€“168. |
| REQ-SRC-ATL105-PDF-001:1770 | SEGMENT_103_FIELD_RULE | BR-400-10 | 400 | % | Variable Information Data Segment (No. 111) Segment Length valid values are 001â€“999. |
| REQ-SRC-ATL105-PDF-001:1771 | SEGMENT_103_FIELD_RULE | BR-400-11 | 400 | % | Additional Information Data Segment (No. 112) Segment Length valid values are 001â€“999. |
| REQ-SRC-ATL105-PDF-001:1772 | SEGMENT_103_FIELD_RULE | BR-400-12 | 400 | % | ECA/TeleCheck Data Segment (No. 113) Segment Length valid values are 001â€“156. |
| REQ-SRC-ATL105-PDF-001:1773 | SEGMENT_103_FIELD_RULE | BR-400-13 | 400 | % | SKU Data Segment (No. 114) Segment Length valid values are 0001â€“1010. |
| REQ-SRC-ATL105-PDF-001:1774 | SEGMENT_103_FIELD_RULE | BR-400-14 | 400 | % | Print Data Segment (No. 115) Segment Length valid values are 001-1009. |
| REQ-SRC-ATL105-PDF-001:1775 | SEGMENT_103_FIELD_RULE | BR-400-15 | 400 | % | TransArmor Load Data Segment (No. 116) Segment Length valid values are 01â€“50. |
| REQ-SRC-ATL105-PDF-001:1776 | SEGMENT_103_FIELD_RULE | BR-400-16 | 400 | % | Proprietary Data Load Segment (No. 118) Segment Length valid values are 0001-3800. |
| REQ-SRC-ATL105-PDF-001:1777 | SEGMENT_103_FIELD_RULE | BR-400-17 | 400 | % | Totals with Proprietary Data Load Data Segment (No. 119) Segment Length valid values are 001-493. |
| REQ-SRC-ATL105-PDF-001:1778 | SEGMENT_103_FIELD_RULE | BR-400-18 | 400 | % | Print Data 2 Segment (No. 120) Segment Length valid values are 001-1009. |
| REQ-SRC-ATL105-PDF-001:1779 | SEGMENT_103_FIELD_RULE | BR-400-19 | 400 | % | NFC Payment Tokenization Data Segment (No. 123) Segment Length valid values are 001-186. |
| REQ-SRC-ATL105-PDF-001:1780 | SEGMENT_103_FIELD_RULE | BR-400-20 | 400 | % | EMV Request Data Segment (No.130) Segment Length valid values are 001-3043. |
| REQ-SRC-ATL105-PDF-001:1781 | SEGMENT_103_FIELD_RULE | BR-400-21 | 400 | % | EMV Response Data Segment (No. 131) Segment Length valid values are 001-3834. |
| REQ-SRC-ATL105-PDF-001:1782 | SEGMENT_103_FIELD_RULE | BR-400-22 | 400 | % | CA Public Key File Data Segment (No. 132) Segment Length valid values are 01-77. |
| REQ-SRC-ATL105-PDF-001:1783 | SEGMENT_103_FIELD_RULE | BR-400-23 | 400 | % | Transaction Attributes Data Segment (No. 134) Segment Length valid values are 01-19. |
| REQ-SRC-ATL105-PDF-001:1784 | SEGMENT_103_FIELD_RULE | BR-400-24 | 400 | % | Adjusted Product Code Data Segment (No. 157) Segment Length valid values are 001â€“381. |
| REQ-SRC-ATL105-PDF-001:1785 | SEGMENT_103_FIELD_RULE | BR-401-1 | 401 | % | Segment Type is required for the TransArmor PKI Encryption and Tokenization Load Request. |
| REQ-SRC-ATL105-PDF-001:1786 | SEGMENT_103_FIELD_RULE | BR-401-2 | 401 | % | Segment Type is a fixed length of three digits (N, max 3 bytes). |
| REQ-SRC-ATL105-PDF-001:1837 | DIRECT_SEGMENT_103 | BR-415-1 | 415 | % | Voucher ID is required by some states when an EBT transaction is approved locally. |
| REQ-SRC-ATL105-PDF-001:2003 | DIRECT_SEGMENT_103 | BR-445-1 | 445 | % | WIC Discount Amount appears in the EBT Data Segment (Segment No. 103). |
| REQ-SRC-ATL105-PDF-001:2004 | SEGMENT_103_FIELD_RULE | BR-445-2 | 445 | % | WIC Discount Amount subfield structure: positions 1-2 Account Type (value 97), 3-4 Amount Type (value 52), 5-7 Currency Code (per Appendix L), 8-20 Amount formatted as x+n12 where x=0 or C (credit amount) or D (debit amount). |
| REQ-SRC-ATL105-PDF-001:2005 | SEGMENT_103_FIELD_RULE | BR-446-1 | 446 | % | WIC Product Data is composed of a Total Length subelement plus one or more of: Earliest WIC Benefit Expiration Date, WIC Prescription Balance Information, WIC UPC Exception/Denial Information, WIC UPC Purchase Information. |
| REQ-SRC-ATL105-PDF-001:2006 | SEGMENT_103_FIELD_RULE | BR-446-2 | 446 | % | Earliest WIC Benefit Expiration Date is included in the eWIC Balance Inquiry Response, eWIC Authorization Response, and eWIC Purchase Completion Response. |
| REQ-SRC-ATL105-PDF-001:2007 | SEGMENT_103_FIELD_RULE | BR-446-3 | 446 | % | WIC Prescription Balance is included in the eWIC Balance Inquiry Response, eWIC Authorization Response, and eWIC Purchase Completion Response. |
| REQ-SRC-ATL105-PDF-001:2008 | SEGMENT_103_FIELD_RULE | BR-446-4 | 446 | % | WIC UPC Exception/Denial Information is included in the eWIC Purchase Completion Response and eWIC Voucher Clear Response. |
| REQ-SRC-ATL105-PDF-001:2009 | SEGMENT_103_FIELD_RULE | BR-446-5 | 446 | % | For WIC purchases involving fruits and vegetables via the Cash Value Benefit (CVB), set the Quantity equal to the price in the WIC Purchase Information. |
| REQ-SRC-ATL105-PDF-001:2010 | SEGMENT_103_FIELD_RULE | BR-446-6 | 446 | % | When WIC Purchase price exceeds the state-defined APL value, an exception record is returned in WIC Product Data reflecting both approved and original prices; the Settlement Amount is adjusted and returned in the Approved Amount field. |
| REQ-SRC-ATL105-PDF-001:2012 | SEGMENT_103_FIELD_RULE | BR-447-2 | 447 | % | Earliest WIC Benefit Expiration Date must be formatted as CCYYMMDD (8 numeric bytes) and identified with Data Set Identifier 'EF'. |
| REQ-SRC-ATL105-PDF-001:2019 | SEGMENT_103_FIELD_RULE | BR-451-1 | 451 | % | If Item Action Code (Bit No. 8) is '00', the product was approved but the maximum price was exceeded; the Item price field (Bit No. 6) contains the price. |
| REQ-SRC-ATL105-PDF-001:2020 | SEGMENT_103_FIELD_RULE | BR-451-2 | 451 | % | The exact length of the UPC/PLU data (Bit No. 2), measured from the right-most digit, is specified in the UPC/PLU data length field (Bit No. 11). |
| REQ-SRC-ATL105-PDF-001:2021 | SEGMENT_103_FIELD_RULE | BR-451-3 | 451 | % | The first position of the UPC/PLU data (Bit No. 2) indicates data type: 0 = UPC, 1 = PLU. |
| REQ-SRC-ATL105-PDF-001:2037 | SEGMENT_103_FIELD_RULE | BR-456-1 | 456 | % | EBT Program Data element has a variable maximum length of up to 267 alphanumeric bytes composed of Total Length (fixed 3 digits, appears once) and Program Data (max 264 alphanumeric bytes, 1-6 subelements each max 44 bytes). |
| REQ-SRC-ATL105-PDF-001:2038 | DIRECT_SEGMENT_103 | BR-456-2 | 456 | % | EBT Program Data element is transmitted in Data Segment No. 103 (EBT Data Segment). |
| REQ-SRC-ATL105-PDF-001:2039 | SEGMENT_103_FIELD_RULE | BR-456-3 | 456 | % | The total length of the EBT Program Data element is based on the number of Program Data subelements sent/received. |
| REQ-SRC-ATL105-PDF-001:2042 | SEGMENT_103_FIELD_RULE | BR-458-1 | 458 | % | ACCOUNT TYPE subfield (positions 5-6, fixed value 98) is not required when TAG = IT. |
| REQ-SRC-ATL105-PDF-001:2043 | SEGMENT_103_FIELD_RULE | BR-458-2 | 458 | % | EBT Program Data subelement has a maximum length of 264 bytes, allowing up to 6 occurrences of maximum variable length 44 bytes each. |
| REQ-SRC-ATL105-PDF-001:2044 | SEGMENT_103_FIELD_RULE | BR-458-3 | 458 | % | Valid TAG values for requests are 50 (HIP purchase/return amount) and IT (HIP Internet purchase shipping address/zip code). |
| REQ-SRC-ATL105-PDF-001:2045 | SEGMENT_103_FIELD_RULE | BR-458-4 | 458 | % | Valid TAG values for responses are 51 (HIP incentive earned/returned) and 52 (HIP month-to-date incentive earned). |
| REQ-SRC-ATL105-PDF-001:2046 | SEGMENT_103_FIELD_RULE | BR-459-1 | 459 | % | AMOUNT TYPE (positions 7-8) is not required when TAG = IT. |
| REQ-SRC-ATL105-PDF-001:2047 | SEGMENT_103_FIELD_RULE | BR-459-2 | 459 | % | CURRENCY CODE (positions 9-11) has fixed value 840 (US Dollars) and is not required when TAG = IT. |
| REQ-SRC-ATL105-PDF-001:2048 | SEGMENT_103_FIELD_RULE | BR-459-3 | 459 | % | AMOUNT DESCRIPTOR (position 12) has fixed value 0/C/D and is not required when TAG = IT. |
| REQ-SRC-ATL105-PDF-001:2049 | SEGMENT_103_FIELD_RULE | BR-459-4 | 459 | % | When TAG = 50, 51, or 52, DETAIL (positions 13-24) is a HIP dollar amount with fixed length 12 digits, right-aligned, zero-padded, 2 assumed decimal places. |
| REQ-SRC-ATL105-PDF-001:2050 | SEGMENT_103_FIELD_RULE | BR-459-5 | 459 | % | When TAG = IT, DETAIL is a shipping address of fixed length 37 alphanumeric characters: Address portion positions 5-32 (28 chars, left-aligned, space-filled) and Zip Code portion positions 33-41 (max 9 digits, left-aligned, space-filled). |
| REQ-SRC-ATL105-PDF-001:2051 | SEGMENT_103_FIELD_RULE | BR-459-6 | 459 | % | EBT Program Data AMOUNT TYPE valid values for requests: 50 (HIP purchase/return amount). |
| REQ-SRC-ATL105-PDF-001:2052 | SEGMENT_103_FIELD_RULE | BR-459-7 | 459 | % | EBT Program Data AMOUNT TYPE valid values for responses: 51 (HIP incentive earned/returned), 52 (HIP month-to-date incentive earned). |
| REQ-SRC-ATL105-PDF-001:2197 | SEGMENT_103_FIELD_RULE | BR-499-3 | 499 | % | Standard Message Data Segment (100) length is 78 bytes including field separators in this example. |
| REQ-SRC-ATL105-PDF-001:2199 | SEGMENT_103_FIELD_RULE | BR-501-2 | 501 | % | The Product Code Data Segment (102) length in this example is 49 bytes including Field Separators and Product Data Field Delimiters. |
| REQ-SRC-ATL105-PDF-001:2688 | SEGMENT_103_FIELD_RULE | BR-676-1 | 676 | % | When EBT Program Data (Element 164) is used in a request containing a HIP purchase/return amount, Subelement 1 (Total Length) is formatted n3, right-aligned, zero-filled, and specifies the total length of all data in the entire message (e.g. '024' means 24 characters follow). |
| REQ-SRC-ATL105-PDF-001:2689 | SEGMENT_103_FIELD_RULE | BR-676-2 | 676 | % | Within EBT Program Data (Element 164), the TAG subelement value '50' indicates a HIP purchase/return amount follows; TAG is format n2. |
| REQ-SRC-ATL105-PDF-001:2690 | SEGMENT_103_FIELD_RULE | BR-676-3 | 676 | % | Within EBT Program Data (Element 164), the LEN subelement is format n2 and indicates the length of the Detail data that follows (e.g. '20' indicates 20 characters follow). |
| REQ-SRC-ATL105-PDF-001:2691 | SEGMENT_103_FIELD_RULE | BR-676-4 | 676 | % | Example EBT Program Data value '02450209850840C000000001234' demonstrates Element 164 format for a request containing one set of subelements (a HIP purchase/return amount). |
| REQ-SRC-ATL105-PDF-001:2692 | SEGMENT_103_FIELD_RULE | BR-677-1 | 677 | % | When the EBT Program Data element is used in a request containing a HIP Purchase/Return Amount, Subelement 2 ACCOUNT TYPE must be '98' (HIP Account Type). |
| REQ-SRC-ATL105-PDF-001:2693 | SEGMENT_103_FIELD_RULE | BR-677-2 | 677 | % | When the EBT Program Data element is used in a request containing a HIP Purchase/Return Amount, AMOUNT TYPE must be '40' (HIP Amount Type). Note: the raw value shown is '50' but the valid value is stated as 40. |
| REQ-SRC-ATL105-PDF-001:2694 | SEGMENT_103_FIELD_RULE | BR-677-3 | 677 | % | CURRENCY CODE subfield must be '840' (US Dollars) when used in a HIP Purchase/Return Amount request. |
| REQ-SRC-ATL105-PDF-001:2695 | SEGMENT_103_FIELD_RULE | BR-677-4 | 677 | % | AMOUNT DESCRIPTOR must be one of '0', 'C', or 'D', where 0 or C indicate a credit amount and D indicates a debit amount. |
| REQ-SRC-ATL105-PDF-001:2696 | SEGMENT_103_FIELD_RULE | BR-677-5 | 677 | % | DETAIL (HIP purchase/return amount) is a 12-digit numeric with two assumed decimal places, right-aligned and zero-padded (e.g., '000000001234' = $12.34). |
