# POC AI Segment 130 Business Requirements

Source: `step5_requirements/approved/requirement_catalog.json`

Scope: rules whose source rule is explicitly related to ENT-SEG-130 or a direct field of that segment in the approved POC extraction.

Count: 107

| ID | Scope | Source rule | Page | Confidence | Requirement |
|---|---|---|---:|---:|---|
| REQ-SRC-ATL105-PDF-001:254 | SEGMENT_130_FIELD_RULE | BR-7-4 | 7 | 74% | Adjusted Product Code Data Segment (No. 157) added; data elements 76, 77, 81, 84, 85, 87, 106, and 107 updated with references to data segment 157. |
| REQ-SRC-ATL105-PDF-001:624 | SEGMENT_130_FIELD_RULE | BR-106-10 | 106 | 95% | STAR Single message transactions must be card present with full mag-stripe data or full EMV chip data. |
| REQ-SRC-ATL105-PDF-001:907 | DIRECT_SEGMENT_130 | BR-158-1 | 158 | 90% | EMV data is not required on Reversal transactions. |
| REQ-SRC-ATL105-PDF-001:1044 | DIRECT_SEGMENT_130 | BR-201-4 | 201 | 90% | EMV Financial Transaction Request Data Section No. 3 contains one or more of: Fleet (101), Product Code (102), Purchase Card (104), Variable Information (111), EMV Request (130). |
| REQ-SRC-ATL105-PDF-001:1047 | DIRECT_SEGMENT_130 | BR-203-1 | 203 | 90% | Segment 130 (EMV Request Data Segment) is required and is the only segment required for all EMV financial transactions. |
| REQ-SRC-ATL105-PDF-001:1072 | SEGMENT_130_FIELD_RULE | BR-211-5 | 211 | 95% | Segment Type field 3 of Data Segment 132 has fixed value 132. |
| REQ-SRC-ATL105-PDF-001:1079 | SEGMENT_130_FIELD_RULE | BR-213-4 | 213 | 95% | CA Public Key File Checksum must be used with all subsequent EMV transactions from this device. |
| REQ-SRC-ATL105-PDF-001:1089 | SEGMENT_130_FIELD_RULE | BR-220-4 | 220 | 97% | Field 1 Segment Type in Segment 100 has fixed value 100. |
| REQ-SRC-ATL105-PDF-001:1090 | SEGMENT_130_FIELD_RULE | BR-220-5 | 220 | 90% | Segment Length includes the Segment Type's length and Field Separators. |
| REQ-SRC-ATL105-PDF-001:1095 | SEGMENT_130_FIELD_RULE | BR-223-5 | 223 | 95% | Segment Type field in the Fleet Data Segment has fixed value 101. |
| REQ-SRC-ATL105-PDF-001:1096 | SEGMENT_130_FIELD_RULE | BR-223-6 | 223 | 90% | Segment Length field identifies the data segment's length, including the Segment Type field's length and Field Separators. |
| REQ-SRC-ATL105-PDF-001:1115 | SEGMENT_130_FIELD_RULE | BR-226-12 | 226 | 90% | A Field Separator always follows Segment Type and Segment Length. A Field Separator follows Product Amount when Product Amount is the very last element in the segment. When a field is not populated, still send the Field Separator. |
| REQ-SRC-ATL105-PDF-001:1128 | SEGMENT_130_FIELD_RULE | BR-229-3 | 229 | 95% | Segment Length should be '4' for EBT with eWIC data transactions. |
| REQ-SRC-ATL105-PDF-001:1131 | SEGMENT_130_FIELD_RULE | BR-229-6 | 229 | 98% | Segment Type is a fixed value of 103 for the EBT Data Segment. |
| REQ-SRC-ATL105-PDF-001:1135 | SEGMENT_130_FIELD_RULE | BR-230-4 | 230 | 95% | Segment Type field is fixed value 104 for the Purchase Card Data Segment. |
| REQ-SRC-ATL105-PDF-001:1141 | SEGMENT_130_FIELD_RULE | BR-232-5 | 232 | 95% | For Totals Data Segment, Segment Type (Field 1) has fixed value 105. |
| REQ-SRC-ATL105-PDF-001:1148 | SEGMENT_130_FIELD_RULE | BR-235-4 | 235 | 95% | Segment Type in Loyalty Card Data Segment is fixed value 108. |
| REQ-SRC-ATL105-PDF-001:1153 | SEGMENT_130_FIELD_RULE | BR-237-5 | 237 | 95% | Segment Type (field 1) has fixed value 109 for the Electronic Mail Data Segment. |
| REQ-SRC-ATL105-PDF-001:1167 | SEGMENT_130_FIELD_RULE | BR-243-2 | 243 | 95% | Segment Type for Data Segment No. 111 has fixed value 111. |
| REQ-SRC-ATL105-PDF-001:1174 | SEGMENT_130_FIELD_RULE | BR-245-2 | 245 | 95% | Segment Type in segment 112 must be the fixed value 112. |
| REQ-SRC-ATL105-PDF-001:1180 | SEGMENT_130_FIELD_RULE | BR-246-5 | 246 | 95% | Segment Type (field 1) is fixed value 113 for the ECA/TeleCheck Data Segment. |
| REQ-SRC-ATL105-PDF-001:1190 | SEGMENT_130_FIELD_RULE | BR-249-5 | 249 | 95% | Segment Type field is a fixed value of 115 for the Print Data Segment. |
| REQ-SRC-ATL105-PDF-001:1196 | SEGMENT_130_FIELD_RULE | BR-251-5 | 251 | 98% | Segment Type field for Proprietary Data Load Segment has a fixed value of 118. |
| REQ-SRC-ATL105-PDF-001:1212 | SEGMENT_130_FIELD_RULE | BR-255-1 | 255 | 95% | When Prompt Code = 902 (Dynamic Card Table Data), Field 14 contains Card Table Data (max 3,600 bytes, Required); its length is determined by the segment length in field 2. |
| REQ-SRC-ATL105-PDF-001:1235 | SEGMENT_130_FIELD_RULE | BR-260-4 | 260 | 95% | In segment 119, Segment Type has fixed value 119. |
| REQ-SRC-ATL105-PDF-001:1248 | SEGMENT_130_FIELD_RULE | BR-263-6 | 263 | 95% | Segment Type field contains fixed value '120' for the Print Data 2 Segment. |
| REQ-SRC-ATL105-PDF-001:1253 | SEGMENT_130_FIELD_RULE | BR-264-5 | 264 | 95% | Segment Type (Field 1) must be the fixed value 123. |
| REQ-SRC-ATL105-PDF-001:1260 | DIRECT_SEGMENT_130 | BR-267-1 | 267 | 90% | Segment 130 (EMV Request Data Segment) always appears in Data Section No. 3 of an EMV Financial Transaction Request. |
| REQ-SRC-ATL105-PDF-001:1261 | DIRECT_SEGMENT_130 | BR-267-2 | 267 | 95% | The EMV Request Data Segment has a maximum length of 3043 alphanumeric characters (001â€“3043/a-z/Aâ€“Z). |
| REQ-SRC-ATL105-PDF-001:1262 | DIRECT_SEGMENT_130 | BR-267-3 | 267 | 95% | The EMV Request Data Segment originates at the device. |
| REQ-SRC-ATL105-PDF-001:1263 | DIRECT_SEGMENT_130 | BR-267-4 | 267 | 95% | Field Separators exist between Field Nos. 1-2, 2-3, 3-4, 4-5, 5-6, and between Field No. 6 and the EMV Additional Information Section. Even when a field is not populated, the Field Separator must still be sent. |
| REQ-SRC-ATL105-PDF-001:1264 | DIRECT_SEGMENT_130 | BR-267-5 | 267 | 90% | Field Separators are not present between elements within the EMV Additional Information Section, nor between repetitions of the EMV Additional Information Section. A Field Separator follows the final EMV Additional Information Section. |
| REQ-SRC-ATL105-PDF-001:1265 | SEGMENT_130_FIELD_RULE | BR-267-6 | 267 | 95% | EMV Chip Data Length (Field 5, Element 189) valid values are 000â€“999. |
| REQ-SRC-ATL105-PDF-001:1266 | DIRECT_SEGMENT_130 | BR-267-7 | 267 | 70% | EMV Card Sequence Number (Field 4, Element 188) is conditional in the EMV Request Data Segment. |
| REQ-SRC-ATL105-PDF-001:1267 | DIRECT_SEGMENT_130 | BR-268-1 | 268 | 90% | The EMV Additional Information Section is repeated - by EMV Additional Information Indicator - for a maximum length of 2,000 bytes. |
| REQ-SRC-ATL105-PDF-001:1268 | DIRECT_SEGMENT_130 | BR-268-2 | 268 | 90% | The Field Separator that follows Field No. 9 (EMV Additional Information) is included with the last repetition of the EMV Additional Information Section. |
| REQ-SRC-ATL105-PDF-001:1271 | SEGMENT_130_FIELD_RULE | BR-269-3 | 269 | 90% | CA Public Key File Checksum in the EMV Response is echoed from the Request. |
| REQ-SRC-ATL105-PDF-001:1277 | SEGMENT_130_FIELD_RULE | BR-271-4 | 271 | 95% | Segment Type field for CA Public Key File Data Segment has fixed value 132. |
| REQ-SRC-ATL105-PDF-001:1282 | SEGMENT_130_FIELD_RULE | BR-274-1 | 274 | 90% | Field Nos 1 and 2, and 2 and 3, are separated by Field Separators. The segment should end with a field separator. Data fields contained within field 3 are not separated by Field Separators. |
| REQ-SRC-ATL105-PDF-001:1283 | SEGMENT_130_FIELD_RULE | BR-274-2 | 274 | 95% | Segment Type for Data Segment 135 has a fixed value of 135. |
| REQ-SRC-ATL105-PDF-001:1285 | SEGMENT_130_FIELD_RULE | BR-275-1 | 275 | 95% | Segment Type for Moneris Data (Response) Segment must have the fixed value 136. |
| REQ-SRC-ATL105-PDF-001:1290 | SEGMENT_130_FIELD_RULE | BR-276-2 | 276 | 95% | Segment Type must be the fixed value 139 for the Moneris Day End Batch Balance Request Segment. |
| REQ-SRC-ATL105-PDF-001:1291 | SEGMENT_130_FIELD_RULE | BR-276-3 | 276 | 90% | Segment Length includes Segment Type's length and Field Separators. |
| REQ-SRC-ATL105-PDF-001:1293 | SEGMENT_130_FIELD_RULE | BR-279-2 | 279 | 95% | Segment Type field 1 has fixed value 141. |
| REQ-SRC-ATL105-PDF-001:1298 | SEGMENT_130_FIELD_RULE | BR-281-2 | 281 | 95% | Segment Type field in Segment 142 has a fixed value of 142. |
| REQ-SRC-ATL105-PDF-001:1356 | SEGMENT_130_FIELD_RULE | BR-295-4 | 295 | 95% | Segment Type field of Data Segment 146 must be fixed value '146'. |
| REQ-SRC-ATL105-PDF-001:1391 | SEGMENT_130_FIELD_RULE | BR-301-3 | 301 | 95% | Within Segment 148, Segment Type (field 1) has fixed value 148. |
| REQ-SRC-ATL105-PDF-001:1399 | SEGMENT_130_FIELD_RULE | BR-305-3 | 305 | 95% | Segment Type field must be fixed value 151 for the Request InComm OTC Market Basket Data Segment. |
| REQ-SRC-ATL105-PDF-001:1402 | SEGMENT_130_FIELD_RULE | BR-306-1 | 306 | 95% | Segment Type for Data Segment 152 has a fixed value of 152. |
| REQ-SRC-ATL105-PDF-001:1407 | SEGMENT_130_FIELD_RULE | BR-307-2 | 307 | 95% | Segment Type for Network Token Data Request Segment is fixed at value 153. |
| REQ-SRC-ATL105-PDF-001:1419 | SEGMENT_130_FIELD_RULE | BR-310-5 | 310 | 95% | Segment Type in segment 155 is a fixed value of 155, length 3, required. |
| REQ-SRC-ATL105-PDF-001:1460 | SEGMENT_130_FIELD_RULE | BR-320-12 | 320 | 85% | In Segment 157, a Field Separator always follows Segment Type and Segment Length; a Field Separator follows Adjusted Product Amount only when it is the very last element; when a field is not populated, still send the Field Separator. |
| REQ-SRC-ATL105-PDF-001:1467 | SEGMENT_130_FIELD_RULE | BR-321-5 | 321 | 98% | Segment Type for the Adjusted Product Code Data Segment has fixed value 157. |
| REQ-SRC-ATL105-PDF-001:1507 | SEGMENT_130_FIELD_RULE | BR-333-5 | 333 | 90% | Segment Length Indicator indicates the data segment's length exclusive of the Data Type Indicator. |
| REQ-SRC-ATL105-PDF-001:1616 | SEGMENT_130_FIELD_RULE | BR-373-3 | 373 | 85% | Download Indicator is required for EMV Public Key loads; the value indicates the device must update its CA Public Key File because the checksum in the EMV Financial Transaction Request does not match the checksum value at the host. |
| REQ-SRC-ATL105-PDF-001:1717 | DIRECT_SEGMENT_130 | BR-394-6 | 394 | 95% | In EMV transactions, if 'D' is returned in response, then subsequent completion/void/reversal transactions must be sent as credit, reflected in the Prompt Code (Segment 100, Element 78). |
| REQ-SRC-ATL105-PDF-001:1718 | DIRECT_SEGMENT_130 | BR-394-7 | 394 | 95% | In EMV transactions, if 'S' is returned in response, then subsequent completion/void/reversal transactions must be sent as debit, reflected in the Prompt Code (Segment 100, Element 78). |
| REQ-SRC-ATL105-PDF-001:1743 | SEGMENT_130_FIELD_RULE | BR-398-9 | 398 | 79% | Response Code 'X' indicates Declinedâ€”Proprietary data load, proceed to next pending Prompt Code; OR, for EMV Key Load, Not Required/Rejected (the checksum in the request matches the host). |
| REQ-SRC-ATL105-PDF-001:1745 | DIRECT_SEGMENT_130 | BR-399-1 | 399 | 95% | Segment Length has a length of four digits only when transmitting EBT Data Segment (103), SKU Data Segment (114), Print Data Segment (115), Proprietary Data Load Segment (118), Print Data 2 Segment (120), EMV Request Data Segment (130), or EMV Response Data Segment (131). It cannot be 4 digits for any other segment. |
| REQ-SRC-ATL105-PDF-001:1746 | SEGMENT_130_FIELD_RULE | BR-399-2 | 399 | 95% | Segment Length is calculated using the lengths of all data elements in the segment plus all Field Separators/Product Data Field Delimiters related to those elements. |
| REQ-SRC-ATL105-PDF-001:1747 | SEGMENT_130_FIELD_RULE | BR-400-1 | 400 | 90% | Standard Message Data Segment (No. 100) Segment Length valid values are 001â€“218. |
| REQ-SRC-ATL105-PDF-001:1748 | SEGMENT_130_FIELD_RULE | BR-400-2 | 400 | 90% | Fleet Data Segment (No. 101) Segment Length valid values are 001â€“61. |
| REQ-SRC-ATL105-PDF-001:1749 | SEGMENT_130_FIELD_RULE | BR-400-3 | 400 | 90% | Product Code Data Segment (No. 102) Segment Length valid values are 001â€“381. |
| REQ-SRC-ATL105-PDF-001:1750 | SEGMENT_130_FIELD_RULE | BR-400-4 | 400 | 90% | EBT Data Segment (No. 103) Segment Length valid values are 001â€“3334. |
| REQ-SRC-ATL105-PDF-001:1751 | SEGMENT_130_FIELD_RULE | BR-400-5 | 400 | 90% | Purchase Card Data Segment (No. 104) Segment Length valid values are 001â€“86. |
| REQ-SRC-ATL105-PDF-001:1752 | SEGMENT_130_FIELD_RULE | BR-400-6 | 400 | 90% | Totals Data Segment (No. 105) Segment Length valid values are 001â€“409. |
| REQ-SRC-ATL105-PDF-001:1753 | SEGMENT_130_FIELD_RULE | BR-400-7 | 400 | 90% | Loyalty Card Data Segment (No. 108) Segment Length valid values are 001â€“142. |
| REQ-SRC-ATL105-PDF-001:1754 | SEGMENT_130_FIELD_RULE | BR-400-8 | 400 | 90% | Electronic Mail Data Segment (No. 109) Segment Length valid values are 001â€“232. |
| REQ-SRC-ATL105-PDF-001:1755 | SEGMENT_130_FIELD_RULE | BR-400-9 | 400 | 90% | Check Data Segment (No. 110) Segment Length valid values are 001â€“168. |
| REQ-SRC-ATL105-PDF-001:1756 | SEGMENT_130_FIELD_RULE | BR-400-10 | 400 | 90% | Variable Information Data Segment (No. 111) Segment Length valid values are 001â€“999. |
| REQ-SRC-ATL105-PDF-001:1757 | SEGMENT_130_FIELD_RULE | BR-400-11 | 400 | 90% | Additional Information Data Segment (No. 112) Segment Length valid values are 001â€“999. |
| REQ-SRC-ATL105-PDF-001:1758 | SEGMENT_130_FIELD_RULE | BR-400-12 | 400 | 90% | ECA/TeleCheck Data Segment (No. 113) Segment Length valid values are 001â€“156. |
| REQ-SRC-ATL105-PDF-001:1759 | SEGMENT_130_FIELD_RULE | BR-400-13 | 400 | 90% | SKU Data Segment (No. 114) Segment Length valid values are 0001â€“1010. |
| REQ-SRC-ATL105-PDF-001:1760 | SEGMENT_130_FIELD_RULE | BR-400-14 | 400 | 90% | Print Data Segment (No. 115) Segment Length valid values are 001-1009. |
| REQ-SRC-ATL105-PDF-001:1761 | SEGMENT_130_FIELD_RULE | BR-400-15 | 400 | 90% | TransArmor Load Data Segment (No. 116) Segment Length valid values are 01â€“50. |
| REQ-SRC-ATL105-PDF-001:1762 | SEGMENT_130_FIELD_RULE | BR-400-16 | 400 | 90% | Proprietary Data Load Segment (No. 118) Segment Length valid values are 0001-3800. |
| REQ-SRC-ATL105-PDF-001:1763 | SEGMENT_130_FIELD_RULE | BR-400-17 | 400 | 90% | Totals with Proprietary Data Load Data Segment (No. 119) Segment Length valid values are 001-493. |
| REQ-SRC-ATL105-PDF-001:1764 | SEGMENT_130_FIELD_RULE | BR-400-18 | 400 | 90% | Print Data 2 Segment (No. 120) Segment Length valid values are 001-1009. |
| REQ-SRC-ATL105-PDF-001:1765 | SEGMENT_130_FIELD_RULE | BR-400-19 | 400 | 90% | NFC Payment Tokenization Data Segment (No. 123) Segment Length valid values are 001-186. |
| REQ-SRC-ATL105-PDF-001:1766 | DIRECT_SEGMENT_130 | BR-400-20 | 400 | 90% | EMV Request Data Segment (No.130) Segment Length valid values are 001-3043. |
| REQ-SRC-ATL105-PDF-001:1767 | SEGMENT_130_FIELD_RULE | BR-400-21 | 400 | 90% | EMV Response Data Segment (No. 131) Segment Length valid values are 001-3834. |
| REQ-SRC-ATL105-PDF-001:1768 | SEGMENT_130_FIELD_RULE | BR-400-22 | 400 | 90% | CA Public Key File Data Segment (No. 132) Segment Length valid values are 01-77. |
| REQ-SRC-ATL105-PDF-001:1769 | SEGMENT_130_FIELD_RULE | BR-400-23 | 400 | 90% | Transaction Attributes Data Segment (No. 134) Segment Length valid values are 01-19. |
| REQ-SRC-ATL105-PDF-001:1770 | SEGMENT_130_FIELD_RULE | BR-400-24 | 400 | 90% | Adjusted Product Code Data Segment (No. 157) Segment Length valid values are 001â€“381. |
| REQ-SRC-ATL105-PDF-001:1771 | SEGMENT_130_FIELD_RULE | BR-401-1 | 401 | 90% | Segment Type is required for the TransArmor PKI Encryption and Tokenization Load Request. |
| REQ-SRC-ATL105-PDF-001:1772 | SEGMENT_130_FIELD_RULE | BR-401-2 | 401 | 95% | Segment Type is a fixed length of three digits (N, max 3 bytes). |
| REQ-SRC-ATL105-PDF-001:2070 | SEGMENT_130_FIELD_RULE | BR-469-4 | 469 | 95% | CA Public Key File Checksum is required in the EMV Financial Transaction Request. |
| REQ-SRC-ATL105-PDF-001:2071 | SEGMENT_130_FIELD_RULE | BR-470-1 | 470 | 90% | For transactions containing ICC Data, the EMV Card Sequence Number is required when the card product includes the Card Sequence Number in the EMV data. |
| REQ-SRC-ATL105-PDF-001:2072 | SEGMENT_130_FIELD_RULE | BR-470-2 | 470 | 95% | If EMV Tag 5F34 is not provided by the EMV chip card, send three spaces for the EMV Card Sequence Number. |
| REQ-SRC-ATL105-PDF-001:2073 | SEGMENT_130_FIELD_RULE | BR-470-3 | 470 | 95% | EMV Chip Data Length is required in all EMV transactions. |
| REQ-SRC-ATL105-PDF-001:2074 | SEGMENT_130_FIELD_RULE | BR-470-4 | 470 | 95% | EMV Card Sequence Number valid values are 000â€“099 or three spaces. |
| REQ-SRC-ATL105-PDF-001:2075 | SEGMENT_130_FIELD_RULE | BR-470-5 | 470 | 95% | EMV Chip Data Length valid values are 000â€“999. |
| REQ-SRC-ATL105-PDF-001:2076 | SEGMENT_130_FIELD_RULE | BR-470-6 | 470 | 85% | The CA Public Key File Checksum is required in the CA Public Key File Load Response and must be used for all subsequent EMV transaction requests from the device. |
| REQ-SRC-ATL105-PDF-001:2077 | SEGMENT_130_FIELD_RULE | BR-471-1 | 471 | 95% | Data Element 190 (EMV Chip Data) is required in all EMV transactions. |
| REQ-SRC-ATL105-PDF-001:2078 | SEGMENT_130_FIELD_RULE | BR-471-2 | 471 | 90% | EMV data elements must contain the exact values that were present on the card-to-terminal interface. |
| REQ-SRC-ATL105-PDF-001:2079 | SEGMENT_130_FIELD_RULE | BR-471-3 | 471 | 90% | If the same data element appears several times on the card-to-terminal interface, the field must contain the last value that was present during the transaction. |
| REQ-SRC-ATL105-PDF-001:2080 | SEGMENT_130_FIELD_RULE | BR-471-4 | 471 | 95% | For the EMV transaction to be processed properly, the Application ID (AID) TLV (tag 9F06) or Dedicated File Name (tag 84) must be included in Data Element No. 190 (EMV Chip Data). |
| REQ-SRC-ATL105-PDF-001:2081 | SEGMENT_130_FIELD_RULE | BR-471-5 | 471 | 95% | Tag '5A' (PAN) and Tag '57' (Track 2 equivalent data) must not be included in Data Element 190 â€” EMV Chip Data. |
| REQ-SRC-ATL105-PDF-001:2082 | SEGMENT_130_FIELD_RULE | BR-471-6 | 471 | 90% | Tag length determination: check the last five bits of the first byte; if all set to 1, the tag is two bytes long (including the current byte); otherwise it is one byte. |
| REQ-SRC-ATL105-PDF-001:2083 | SEGMENT_130_FIELD_RULE | BR-471-7 | 471 | 90% | Length field encoding: if the first bit of the first length byte is 1, the remaining seven bits indicate the number of following length bytes; if the first bit is 0, the remaining bits give the actual length of the data that follows. |
| REQ-SRC-ATL105-PDF-001:2084 | SEGMENT_130_FIELD_RULE | BR-471-8 | 471 | 90% | EMV Chip Data has a maximum length of 999 bytes, preceded by a three-digit length indicator, and all fields after the table length are in binary format. |
| REQ-SRC-ATL105-PDF-001:2085 | SEGMENT_130_FIELD_RULE | BR-472-1 | 472 | 85% | The data of each Tag/Length/Value combination is up to 255 bytes long, depending on the length specified by the preceding length portion of the sub element. |
| REQ-SRC-ATL105-PDF-001:2183 | SEGMENT_130_FIELD_RULE | BR-499-3 | 499 | 80% | Standard Message Data Segment (100) length is 78 bytes including field separators in this example. |
| REQ-SRC-ATL105-PDF-001:2185 | SEGMENT_130_FIELD_RULE | BR-501-2 | 501 | 85% | The Product Code Data Segment (102) length in this example is 49 bytes including Field Separators and Product Data Field Delimiters. |
| REQ-SRC-ATL105-PDF-001:2711 | SEGMENT_130_FIELD_RULE | BR-690-1 | 690 | 70% | EMV chip data is BCD Packed/Hex Nibbles in format 'nn\'. |
| REQ-SRC-ATL105-PDF-001:2712 | SEGMENT_130_FIELD_RULE | BR-690-2 | 690 | 70% | The EMV tag list shown is not complete; for the complete list of EMV tags, contact your BUYPASS representative. |
| REQ-SRC-ATL105-PDF-001:2722 | SEGMENT_130_FIELD_RULE | BR-692-4 | 692 | 95% | The Certification Authority Public Key Check Sum is a SHA-1 check value calculated on the concatenation of RID, CA Public Key Index, CA Public Key Modulus, and CA Public Key Exponent. |
