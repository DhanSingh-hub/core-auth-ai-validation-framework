# POC AI Segment 135 Business Requirements

Source: `step5_requirements/approved/requirement_catalog.json`

Scope: rules whose source rule is explicitly related to ENT-SEG-135 or a direct field of that segment in the approved POC extraction.

Count: 75

| ID | Scope | Source rule | Page | Confidence | Requirement |
|---|---|---|---:|---:|---|
| REQ-SRC-ATL105-PDF-001:254 | SEGMENT_135_FIELD_RULE | BR-7-4 | 7 | 74% | Adjusted Product Code Data Segment (No. 157) added; data elements 76, 77, 81, 84, 85, 87, 106, and 107 updated with references to data segment 157. |
| REQ-SRC-ATL105-PDF-001:933 | DIRECT_SEGMENT_135 | BR-165-7 | 165 | 95% | Moneris Data (Request) Segment (135) is required only for transactions destined for the Moneris authorizer. |
| REQ-SRC-ATL105-PDF-001:936 | DIRECT_SEGMENT_135 | BR-165-10 | 165 | 85% | Segments 101â€“145 in Financial Transaction Request occupy Field Nos. 4â€“9 with specified maximum lengths (101:308, 102:381, 103:3334, 104:86, 111:999, 123:186, 135:110, 143:399, 145:999). |
| REQ-SRC-ATL105-PDF-001:1049 | DIRECT_SEGMENT_135 | BR-203-3 | 203 | 90% | Segment 135 (Moneris Data Request Segment) is required only for transactions destined for the Moneris authorizer. |
| REQ-SRC-ATL105-PDF-001:1072 | SEGMENT_135_FIELD_RULE | BR-211-5 | 211 | 95% | Segment Type field 3 of Data Segment 132 has fixed value 132. |
| REQ-SRC-ATL105-PDF-001:1089 | SEGMENT_135_FIELD_RULE | BR-220-4 | 220 | 97% | Field 1 Segment Type in Segment 100 has fixed value 100. |
| REQ-SRC-ATL105-PDF-001:1090 | SEGMENT_135_FIELD_RULE | BR-220-5 | 220 | 90% | Segment Length includes the Segment Type's length and Field Separators. |
| REQ-SRC-ATL105-PDF-001:1095 | SEGMENT_135_FIELD_RULE | BR-223-5 | 223 | 95% | Segment Type field in the Fleet Data Segment has fixed value 101. |
| REQ-SRC-ATL105-PDF-001:1096 | SEGMENT_135_FIELD_RULE | BR-223-6 | 223 | 90% | Segment Length field identifies the data segment's length, including the Segment Type field's length and Field Separators. |
| REQ-SRC-ATL105-PDF-001:1115 | SEGMENT_135_FIELD_RULE | BR-226-12 | 226 | 90% | A Field Separator always follows Segment Type and Segment Length. A Field Separator follows Product Amount when Product Amount is the very last element in the segment. When a field is not populated, still send the Field Separator. |
| REQ-SRC-ATL105-PDF-001:1128 | SEGMENT_135_FIELD_RULE | BR-229-3 | 229 | 95% | Segment Length should be '4' for EBT with eWIC data transactions. |
| REQ-SRC-ATL105-PDF-001:1131 | SEGMENT_135_FIELD_RULE | BR-229-6 | 229 | 98% | Segment Type is a fixed value of 103 for the EBT Data Segment. |
| REQ-SRC-ATL105-PDF-001:1135 | SEGMENT_135_FIELD_RULE | BR-230-4 | 230 | 95% | Segment Type field is fixed value 104 for the Purchase Card Data Segment. |
| REQ-SRC-ATL105-PDF-001:1141 | SEGMENT_135_FIELD_RULE | BR-232-5 | 232 | 95% | For Totals Data Segment, Segment Type (Field 1) has fixed value 105. |
| REQ-SRC-ATL105-PDF-001:1148 | SEGMENT_135_FIELD_RULE | BR-235-4 | 235 | 95% | Segment Type in Loyalty Card Data Segment is fixed value 108. |
| REQ-SRC-ATL105-PDF-001:1153 | SEGMENT_135_FIELD_RULE | BR-237-5 | 237 | 95% | Segment Type (field 1) has fixed value 109 for the Electronic Mail Data Segment. |
| REQ-SRC-ATL105-PDF-001:1167 | SEGMENT_135_FIELD_RULE | BR-243-2 | 243 | 95% | Segment Type for Data Segment No. 111 has fixed value 111. |
| REQ-SRC-ATL105-PDF-001:1174 | SEGMENT_135_FIELD_RULE | BR-245-2 | 245 | 95% | Segment Type in segment 112 must be the fixed value 112. |
| REQ-SRC-ATL105-PDF-001:1180 | SEGMENT_135_FIELD_RULE | BR-246-5 | 246 | 95% | Segment Type (field 1) is fixed value 113 for the ECA/TeleCheck Data Segment. |
| REQ-SRC-ATL105-PDF-001:1190 | SEGMENT_135_FIELD_RULE | BR-249-5 | 249 | 95% | Segment Type field is a fixed value of 115 for the Print Data Segment. |
| REQ-SRC-ATL105-PDF-001:1196 | SEGMENT_135_FIELD_RULE | BR-251-5 | 251 | 98% | Segment Type field for Proprietary Data Load Segment has a fixed value of 118. |
| REQ-SRC-ATL105-PDF-001:1212 | SEGMENT_135_FIELD_RULE | BR-255-1 | 255 | 95% | When Prompt Code = 902 (Dynamic Card Table Data), Field 14 contains Card Table Data (max 3,600 bytes, Required); its length is determined by the segment length in field 2. |
| REQ-SRC-ATL105-PDF-001:1235 | SEGMENT_135_FIELD_RULE | BR-260-4 | 260 | 95% | In segment 119, Segment Type has fixed value 119. |
| REQ-SRC-ATL105-PDF-001:1248 | SEGMENT_135_FIELD_RULE | BR-263-6 | 263 | 95% | Segment Type field contains fixed value '120' for the Print Data 2 Segment. |
| REQ-SRC-ATL105-PDF-001:1253 | SEGMENT_135_FIELD_RULE | BR-264-5 | 264 | 95% | Segment Type (Field 1) must be the fixed value 123. |
| REQ-SRC-ATL105-PDF-001:1277 | SEGMENT_135_FIELD_RULE | BR-271-4 | 271 | 95% | Segment Type field for CA Public Key File Data Segment has fixed value 132. |
| REQ-SRC-ATL105-PDF-001:1282 | DIRECT_SEGMENT_135 | BR-274-1 | 274 | 90% | Field Nos 1 and 2, and 2 and 3, are separated by Field Separators. The segment should end with a field separator. Data fields contained within field 3 are not separated by Field Separators. |
| REQ-SRC-ATL105-PDF-001:1283 | DIRECT_SEGMENT_135 | BR-274-2 | 274 | 95% | Segment Type for Data Segment 135 has a fixed value of 135. |
| REQ-SRC-ATL105-PDF-001:1284 | SEGMENT_135_FIELD_RULE | BR-274-3 | 274 | 95% | Moneris Data (field 3) is formatted as <tag><len><data>; refer to Appendix V for Moneris Data layouts. |
| REQ-SRC-ATL105-PDF-001:1285 | SEGMENT_135_FIELD_RULE | BR-275-1 | 275 | 95% | Segment Type for Moneris Data (Response) Segment must have the fixed value 136. |
| REQ-SRC-ATL105-PDF-001:1286 | SEGMENT_135_FIELD_RULE | BR-275-2 | 275 | 90% | Moneris Data field is formatted as <tag><len><data>; refer to Appendix V for details on Moneris Data layouts. |
| REQ-SRC-ATL105-PDF-001:1290 | SEGMENT_135_FIELD_RULE | BR-276-2 | 276 | 95% | Segment Type must be the fixed value 139 for the Moneris Day End Batch Balance Request Segment. |
| REQ-SRC-ATL105-PDF-001:1291 | SEGMENT_135_FIELD_RULE | BR-276-3 | 276 | 90% | Segment Length includes Segment Type's length and Field Separators. |
| REQ-SRC-ATL105-PDF-001:1293 | SEGMENT_135_FIELD_RULE | BR-279-2 | 279 | 95% | Segment Type field 1 has fixed value 141. |
| REQ-SRC-ATL105-PDF-001:1298 | SEGMENT_135_FIELD_RULE | BR-281-2 | 281 | 95% | Segment Type field in Segment 142 has a fixed value of 142. |
| REQ-SRC-ATL105-PDF-001:1356 | SEGMENT_135_FIELD_RULE | BR-295-4 | 295 | 95% | Segment Type field of Data Segment 146 must be fixed value '146'. |
| REQ-SRC-ATL105-PDF-001:1391 | SEGMENT_135_FIELD_RULE | BR-301-3 | 301 | 95% | Within Segment 148, Segment Type (field 1) has fixed value 148. |
| REQ-SRC-ATL105-PDF-001:1399 | SEGMENT_135_FIELD_RULE | BR-305-3 | 305 | 95% | Segment Type field must be fixed value 151 for the Request InComm OTC Market Basket Data Segment. |
| REQ-SRC-ATL105-PDF-001:1402 | SEGMENT_135_FIELD_RULE | BR-306-1 | 306 | 95% | Segment Type for Data Segment 152 has a fixed value of 152. |
| REQ-SRC-ATL105-PDF-001:1407 | SEGMENT_135_FIELD_RULE | BR-307-2 | 307 | 95% | Segment Type for Network Token Data Request Segment is fixed at value 153. |
| REQ-SRC-ATL105-PDF-001:1419 | SEGMENT_135_FIELD_RULE | BR-310-5 | 310 | 95% | Segment Type in segment 155 is a fixed value of 155, length 3, required. |
| REQ-SRC-ATL105-PDF-001:1460 | SEGMENT_135_FIELD_RULE | BR-320-12 | 320 | 85% | In Segment 157, a Field Separator always follows Segment Type and Segment Length; a Field Separator follows Adjusted Product Amount only when it is the very last element; when a field is not populated, still send the Field Separator. |
| REQ-SRC-ATL105-PDF-001:1467 | SEGMENT_135_FIELD_RULE | BR-321-5 | 321 | 98% | Segment Type for the Adjusted Product Code Data Segment has fixed value 157. |
| REQ-SRC-ATL105-PDF-001:1507 | SEGMENT_135_FIELD_RULE | BR-333-5 | 333 | 90% | Segment Length Indicator indicates the data segment's length exclusive of the Data Type Indicator. |
| REQ-SRC-ATL105-PDF-001:1745 | SEGMENT_135_FIELD_RULE | BR-399-1 | 399 | 95% | Segment Length has a length of four digits only when transmitting EBT Data Segment (103), SKU Data Segment (114), Print Data Segment (115), Proprietary Data Load Segment (118), Print Data 2 Segment (120), EMV Request Data Segment (130), or EMV Response Data Segment (131). It cannot be 4 digits for any other segment. |
| REQ-SRC-ATL105-PDF-001:1746 | SEGMENT_135_FIELD_RULE | BR-399-2 | 399 | 95% | Segment Length is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements. |
| REQ-SRC-ATL105-PDF-001:1747 | SEGMENT_135_FIELD_RULE | BR-400-1 | 400 | 90% | Standard Message Data Segment (No. 100) Segment Length valid values are 001â€“218. |
| REQ-SRC-ATL105-PDF-001:1748 | SEGMENT_135_FIELD_RULE | BR-400-2 | 400 | 90% | Fleet Data Segment (No. 101) Segment Length valid values are 001â€“61. |
| REQ-SRC-ATL105-PDF-001:1749 | SEGMENT_135_FIELD_RULE | BR-400-3 | 400 | 90% | Product Code Data Segment (No. 102) Segment Length valid values are 001â€“381. |
| REQ-SRC-ATL105-PDF-001:1750 | SEGMENT_135_FIELD_RULE | BR-400-4 | 400 | 90% | EBT Data Segment (No. 103) Segment Length valid values are 001â€“3334. |
| REQ-SRC-ATL105-PDF-001:1751 | SEGMENT_135_FIELD_RULE | BR-400-5 | 400 | 90% | Purchase Card Data Segment (No. 104) Segment Length valid values are 001â€“86. |
| REQ-SRC-ATL105-PDF-001:1752 | SEGMENT_135_FIELD_RULE | BR-400-6 | 400 | 90% | Totals Data Segment (No. 105) Segment Length valid values are 001â€“409. |
| REQ-SRC-ATL105-PDF-001:1753 | SEGMENT_135_FIELD_RULE | BR-400-7 | 400 | 90% | Loyalty Card Data Segment (No. 108) Segment Length valid values are 001â€“142. |
| REQ-SRC-ATL105-PDF-001:1754 | SEGMENT_135_FIELD_RULE | BR-400-8 | 400 | 90% | Electronic Mail Data Segment (No. 109) Segment Length valid values are 001â€“232. |
| REQ-SRC-ATL105-PDF-001:1755 | SEGMENT_135_FIELD_RULE | BR-400-9 | 400 | 90% | Check Data Segment (No. 110) Segment Length valid values are 001â€“168. |
| REQ-SRC-ATL105-PDF-001:1756 | SEGMENT_135_FIELD_RULE | BR-400-10 | 400 | 90% | Variable Information Data Segment (No. 111) Segment Length valid values are 001â€“999. |
| REQ-SRC-ATL105-PDF-001:1757 | SEGMENT_135_FIELD_RULE | BR-400-11 | 400 | 90% | Additional Information Data Segment (No. 112) Segment Length valid values are 001â€“999. |
| REQ-SRC-ATL105-PDF-001:1758 | SEGMENT_135_FIELD_RULE | BR-400-12 | 400 | 90% | ECA/TeleCheck Data Segment (No. 113) Segment Length valid values are 001â€“156. |
| REQ-SRC-ATL105-PDF-001:1759 | SEGMENT_135_FIELD_RULE | BR-400-13 | 400 | 90% | SKU Data Segment (No. 114) Segment Length valid values are 0001â€“1010. |
| REQ-SRC-ATL105-PDF-001:1760 | SEGMENT_135_FIELD_RULE | BR-400-14 | 400 | 90% | Print Data Segment (No. 115) Segment Length valid values are 001-1009. |
| REQ-SRC-ATL105-PDF-001:1761 | SEGMENT_135_FIELD_RULE | BR-400-15 | 400 | 90% | TransArmor Load Data Segment (No. 116) Segment Length valid values are 01â€“50. |
| REQ-SRC-ATL105-PDF-001:1762 | SEGMENT_135_FIELD_RULE | BR-400-16 | 400 | 90% | Proprietary Data Load Segment (No. 118) Segment Length valid values are 0001-3800. |
| REQ-SRC-ATL105-PDF-001:1763 | SEGMENT_135_FIELD_RULE | BR-400-17 | 400 | 90% | Totals with Proprietary Data Load Data Segment (No. 119) Segment Length valid values are 001-493. |
| REQ-SRC-ATL105-PDF-001:1764 | SEGMENT_135_FIELD_RULE | BR-400-18 | 400 | 90% | Print Data 2 Segment (No. 120) Segment Length valid values are 001-1009. |
| REQ-SRC-ATL105-PDF-001:1765 | SEGMENT_135_FIELD_RULE | BR-400-19 | 400 | 90% | NFC Payment Tokenization Data Segment (No. 123) Segment Length valid values are 001-186. |
| REQ-SRC-ATL105-PDF-001:1766 | SEGMENT_135_FIELD_RULE | BR-400-20 | 400 | 90% | EMV Request Data Segment (No.130) Segment Length valid values are 001-3043. |
| REQ-SRC-ATL105-PDF-001:1767 | SEGMENT_135_FIELD_RULE | BR-400-21 | 400 | 90% | EMV Response Data Segment (No. 131) Segment Length valid values are 001-3834. |
| REQ-SRC-ATL105-PDF-001:1768 | SEGMENT_135_FIELD_RULE | BR-400-22 | 400 | 90% | CA Public Key File Data Segment (No. 132) Segment Length valid values are 01-77. |
| REQ-SRC-ATL105-PDF-001:1769 | SEGMENT_135_FIELD_RULE | BR-400-23 | 400 | 90% | Transaction Attributes Data Segment (No. 134) Segment Length valid values are 01-19. |
| REQ-SRC-ATL105-PDF-001:1770 | SEGMENT_135_FIELD_RULE | BR-400-24 | 400 | 90% | Adjusted Product Code Data Segment (No. 157) Segment Length valid values are 001â€“381. |
| REQ-SRC-ATL105-PDF-001:1771 | SEGMENT_135_FIELD_RULE | BR-401-1 | 401 | 90% | Segment Type is required for the TransArmor PKI Encryption and Tokenization Load Request. |
| REQ-SRC-ATL105-PDF-001:1772 | SEGMENT_135_FIELD_RULE | BR-401-2 | 401 | 95% | Segment Type is a fixed length of three digits (N, max 3 bytes). |
| REQ-SRC-ATL105-PDF-001:2129 | SEGMENT_135_FIELD_RULE | BR-482-2 | 482 | 95% | Moneris Data (Element 213) should be in <tag><len><data> format. |
| REQ-SRC-ATL105-PDF-001:2183 | SEGMENT_135_FIELD_RULE | BR-499-3 | 499 | 80% | Standard Message Data Segment (100) length is 78 bytes including field separators in this example. |
| REQ-SRC-ATL105-PDF-001:2185 | SEGMENT_135_FIELD_RULE | BR-501-2 | 501 | 85% | The Product Code Data Segment (102) length in this example is 49 bytes including Field Separators and Product Data Field Delimiters. |
