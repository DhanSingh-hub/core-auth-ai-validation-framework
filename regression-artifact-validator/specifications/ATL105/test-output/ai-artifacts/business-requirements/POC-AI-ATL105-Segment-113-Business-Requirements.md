# POC AI Segment 113 Business Requirements

Source: `step5_requirements/approved/requirement_catalog.json`

Scope: rules whose source rule is explicitly related to ENT-SEG-113 or a direct field of that segment in the approved POC extraction.

Count: 60

| ID | Scope | Source rule | Page | Confidence | Requirement |
|---|---|---|---:|---:|---|
| REQ-SRC-ATL105-PDF-001:785 | DIRECT_SEGMENT_113 | BR-131-1 | 131 | 85% | A merchant authorization and check writer copy must be printed for each approved ECA/TeleCheck transaction. |
| REQ-SRC-ATL105-PDF-001:786 | DIRECT_SEGMENT_113 | BR-131-2 | 131 | 79% | Check writer must sign merchant's copy before transaction is considered complete. |
| REQ-SRC-ATL105-PDF-001:788 | SEGMENT_113_FIELD_RULE | BR-131-4 | 131 | 71% | Merchant Name, City/State/ZIP, Merchant Phone, ECA/TeleCheck Service Phone must appear on each receipt copy. |
| REQ-SRC-ATL105-PDF-001:789 | SEGMENT_113_FIELD_RULE | BR-131-5 | 131 | 74% | Terminal ID, ECA/TeleCheck Merchant ID, Date/Time, Transaction Number, Trace ID, Approval Number required on receipt. |
| REQ-SRC-ATL105-PDF-001:793 | SEGMENT_113_FIELD_RULE | BR-131-9 | 131 | 48% | Additional recommended fields include Customer Telephone Number if prompted, Custom Field prompt/data, Merchant Trace ID. |
| REQ-SRC-ATL105-PDF-001:796 | SEGMENT_113_FIELD_RULE | BR-132-2 | 132 | 51% | Denial Record Receipt prints details and Denial Record Number for consumer on transaction decline. |
| REQ-SRC-ATL105-PDF-001:797 | SEGMENT_113_FIELD_RULE | BR-132-3 | 132 | 61% | Denial Record number is used with all TeleCheck products. |
| REQ-SRC-ATL105-PDF-001:798 | SEGMENT_113_FIELD_RULE | BR-132-4 | 132 | 58% | Version 2 Denial Receipt must print Denial Record Number, Check Amount, NACHA Language, TeleCheck Phone Number if cashiers not using courtesy cards. |
| REQ-SRC-ATL105-PDF-001:979 | DIRECT_SEGMENT_113 | BR-173-3 | 173 | 85% | Data Section No. 3 contains none, one, or more of Check, Variable Information, or ECA/TeleCheck Data Segments. |
| REQ-SRC-ATL105-PDF-001:983 | DIRECT_SEGMENT_113 | BR-174-4 | 174 | 85% | ECA/TeleCheck Service Transaction Request contains segments in Field Nos. 4-6: Segments 110, 111, 113. |
| REQ-SRC-ATL105-PDF-001:1134 | SEGMENT_113_FIELD_RULE | BR-220-5 | 220 | 90% | Segment Type field has fixed value 100 for Standard Message Data Segment. |
| REQ-SRC-ATL105-PDF-001:1135 | SEGMENT_113_FIELD_RULE | BR-220-6 | 220 | 85% | Segment Length includes Segment Type's length and Field Separators. |
| REQ-SRC-ATL105-PDF-001:1146 | SEGMENT_113_FIELD_RULE | BR-223-4 | 223 | 90% | Segment Type field has fixed value 101 identifying the Fleet Data Segment. |
| REQ-SRC-ATL105-PDF-001:1165 | SEGMENT_113_FIELD_RULE | BR-226-12 | 226 | 80% | A Field Separator always follows Segment Type and Segment Length; Field Separator follows Product Amount when it's the last element. |
| REQ-SRC-ATL105-PDF-001:1178 | SEGMENT_113_FIELD_RULE | BR-229-3 | 229 | 90% | Segment Length should be 4 for EBT with eWIC data transactions. |
| REQ-SRC-ATL105-PDF-001:1187 | SEGMENT_113_FIELD_RULE | BR-232-6 | 232 | 90% | Segment Type field has a fixed value of 105 in Totals Data Segment. |
| REQ-SRC-ATL105-PDF-001:1208 | SEGMENT_113_FIELD_RULE | BR-240-3 | 240 | 21% | Element number 239 is used for Alternate MICR Indicator since element 131 is currently in use. |
| REQ-SRC-ATL105-PDF-001:1215 | SEGMENT_113_FIELD_RULE | BR-243-3 | 243 | 85% | A Field Separator exists between Field Nos. 1 and 2 and between Field No. 2 and Variable Information Section. |
| REQ-SRC-ATL105-PDF-001:1218 | SEGMENT_113_FIELD_RULE | BR-243-6 | 243 | 90% | Segment Type (Field No. 1) has fixed value 111 identifying the Variable Information Data Segment. |
| REQ-SRC-ATL105-PDF-001:1224 | SEGMENT_113_FIELD_RULE | BR-245-2 | 245 | 90% | Segment Type field has fixed value 112 for Additional Information Data Segment. |
| REQ-SRC-ATL105-PDF-001:1225 | DIRECT_SEGMENT_113 | BR-246-1 | 246 | 81% | ECA/TeleCheck Trace ID must be present in all Void transaction requests. |
| REQ-SRC-ATL105-PDF-001:1226 | DIRECT_SEGMENT_113 | BR-246-2 | 246 | 90% | ECA/TeleCheck Data Segment has a maximum length of 156 alphanumeric characters. |
| REQ-SRC-ATL105-PDF-001:1227 | DIRECT_SEGMENT_113 | BR-246-3 | 246 | 79% | All fields in the segment are separated by Field Separators; unpopulated fields still send the Field Separator. |
| REQ-SRC-ATL105-PDF-001:1228 | SEGMENT_113_FIELD_RULE | BR-247-1 | 247 | 58% | Extended MICR Data is conditional, used when MICR length is greater than 50-bytes. |
| REQ-SRC-ATL105-PDF-001:1238 | SEGMENT_113_FIELD_RULE | BR-249-5 | 249 | 85% | There is a Field Separator between Field Nos. 1 and 2 and between Field Nos. 2 and 3. |
| REQ-SRC-ATL105-PDF-001:1246 | SEGMENT_113_FIELD_RULE | BR-251-7 | 251 | 90% | Segment Type fixed value is 118 for the Proprietary Data Load Segment. |
| REQ-SRC-ATL105-PDF-001:1282 | SEGMENT_113_FIELD_RULE | BR-259-2 | 259 | 70% | Fuel Volume Data field length is determined by segment length in field 2, up to max 3,600. |
| REQ-SRC-ATL105-PDF-001:1291 | SEGMENT_113_FIELD_RULE | BR-260-7 | 260 | 90% | Segment Type fixed value is 119. |
| REQ-SRC-ATL105-PDF-001:1301 | SEGMENT_113_FIELD_RULE | BR-263-4 | 263 | 90% | A Field Separator exists between Field Nos. 1 and 2 and between Field Nos. 2 and 3. |
| REQ-SRC-ATL105-PDF-001:1323 | SEGMENT_113_FIELD_RULE | BR-269-5 | 269 | 90% | Segment Type field has fixed value 131 for the EMV Response Data Segment. |
| REQ-SRC-ATL105-PDF-001:1330 | SEGMENT_113_FIELD_RULE | BR-273-3 | 273 | 90% | Segment Type field fixed value is 134. |
| REQ-SRC-ATL105-PDF-001:1335 | SEGMENT_113_FIELD_RULE | BR-274-1 | 274 | 90% | Field Nos 1&2 and 2&3 are separated by Field Separators; segment should end with a field separator. |
| REQ-SRC-ATL105-PDF-001:1337 | SEGMENT_113_FIELD_RULE | BR-274-3 | 274 | 90% | Segment Type field (Element 85) has fixed value 135 for Moneris Data Segment (Request). |
| REQ-SRC-ATL105-PDF-001:1340 | SEGMENT_113_FIELD_RULE | BR-276-1 | 276 | 90% | Segment Type field must have fixed value 139 for Moneris Day End Batch Balance Request Segment. |
| REQ-SRC-ATL105-PDF-001:1343 | SEGMENT_113_FIELD_RULE | BR-277-2 | 277 | 90% | Segment Type field 1 must have fixed value 140. |
| REQ-SRC-ATL105-PDF-001:1346 | SEGMENT_113_FIELD_RULE | BR-279-3 | 279 | 90% | Segment Type fixed value is 141 for Moneris Day End Batch Close Request segment. |
| REQ-SRC-ATL105-PDF-001:1356 | SEGMENT_113_FIELD_RULE | BR-282-8 | 282 | 74% | A Field Separator always follows Segment Type and Segment Length, and the last Tax Amount per product. |
| REQ-SRC-ATL105-PDF-001:1409 | SEGMENT_113_FIELD_RULE | BR-295-4 | 295 | 90% | Segment Type field has fixed value 146 for Enhanced Fleet Response Segment. |
| REQ-SRC-ATL105-PDF-001:1433 | SEGMENT_113_FIELD_RULE | BR-303-1 | 303 | 80% | Fields 1-2, 2-3, and 3-4 are separated by Field Separators; segment ends with a field separator. |
| REQ-SRC-ATL105-PDF-001:1435 | SEGMENT_113_FIELD_RULE | BR-303-3 | 303 | 90% | Segment Type field has fixed value 149 for Fuel Price Update Request Segment. |
| REQ-SRC-ATL105-PDF-001:1436 | SEGMENT_113_FIELD_RULE | BR-304-1 | 304 | 90% | Segment Type field 1 has fixed value 150 for Fuel Price Update Response Segment. |
| REQ-SRC-ATL105-PDF-001:1437 | SEGMENT_113_FIELD_RULE | BR-304-2 | 304 | 84% | Segment Length includes Segment Type's length and Field Separators. |
| REQ-SRC-ATL105-PDF-001:1521 | SEGMENT_113_FIELD_RULE | BR-320-12 | 320 | 70% | A Field Separator always follows Segment Type and Segment Length; sent even when field not populated. |
| REQ-SRC-ATL105-PDF-001:1564 | SEGMENT_113_FIELD_RULE | BR-332-2 | 332 | 70% | Segment Length Indicator excludes the Data Type Indicator from its length calculation. |
| REQ-SRC-ATL105-PDF-001:1669 | SEGMENT_113_FIELD_RULE | BR-368-5 | 368 | 68% | For ECA/TeleCheck processing, refer to Data Element No. 131, ECA/TeleCheck Clerk ID, instead of Clerk ID. |
| REQ-SRC-ATL105-PDF-001:1821 | SEGMENT_113_FIELD_RULE | BR-399-1 | 399 | 90% | Segment Length has four digits only for segments 103, 114, 115, 118, 120, 130, and 131. |
| REQ-SRC-ATL105-PDF-001:1822 | SEGMENT_113_FIELD_RULE | BR-399-2 | 399 | 90% | Segment Length cannot have a length of 4 digits when sending any segment other than the listed seven. |
| REQ-SRC-ATL105-PDF-001:1823 | SEGMENT_113_FIELD_RULE | BR-399-3 | 399 | 80% | Segment Length is calculated using lengths of all data elements plus Field Separators/Product Data Field Delimiters in the segment. |
| REQ-SRC-ATL105-PDF-001:1835 | DIRECT_SEGMENT_113 | BR-400-12 | 400 | 57% | ECA/TeleCheck Data Segment length is 001-156. |
| REQ-SRC-ATL105-PDF-001:1848 | SEGMENT_113_FIELD_RULE | BR-401-1 | 401 | 90% | Segment Type is required for the TransArmor PKI Encryption and Tokenization Load Request. |
| REQ-SRC-ATL105-PDF-001:1849 | SEGMENT_113_FIELD_RULE | BR-401-2 | 401 | 90% | Segment Type value 106 and 107 are reserved for proprietary use. |
| REQ-SRC-ATL105-PDF-001:1989 | SEGMENT_113_FIELD_RULE | BR-429-5 | 429 | 78% | When Additional Information Indicator = 005, ECA/TeleCheck Trace ID information is included. |
| REQ-SRC-ATL105-PDF-001:1990 | SEGMENT_113_FIELD_RULE | BR-429-6 | 429 | 78% | When Additional Information Indicator = 006, ECA/TeleCheck Denial Record Number information is included. |
| REQ-SRC-ATL105-PDF-001:2043 | DIRECT_SEGMENT_113 | BR-434-4 | 434 | 79% | If MICR data > 50 bytes and prompt code is ECA/TeleCheck check service, refer to ECA/TeleCheck Data Segment (Segment 113). |
| REQ-SRC-ATL105-PDF-001:2049 | SEGMENT_113_FIELD_RULE | BR-437-1 | 437 | 58% | ECA/ TeleCheck Clerk ID valid values range from 1 to 999999. |
| REQ-SRC-ATL105-PDF-001:2050 | SEGMENT_113_FIELD_RULE | BR-439-1 | 439 | 58% | Extended MICR Data must be populated in addition to MICR Data when raw MICR data exceeds the length. |
| REQ-SRC-ATL105-PDF-001:2251 | SEGMENT_113_FIELD_RULE | BR-499-1 | 499 | 70% | Segment Length value (078) identifies the length in bytes of the Standard Message Data Segment, including field separators. |
| REQ-SRC-ATL105-PDF-001:2263 | SEGMENT_113_FIELD_RULE | BR-503-3 | 503 | 70% | Segment Length field identifies the length (017 bytes) of the Variable Information Data Segment, including Field Separators. |
| REQ-SRC-ATL105-PDF-001:2716 | SEGMENT_113_FIELD_RULE | BR-643-1 | 643 | 51% | ECA/TeleCheck Trace ID Table ID is fixed value 005, with fixed table length and maximum length 22. |
| REQ-SRC-ATL105-PDF-001:2717 | SEGMENT_113_FIELD_RULE | BR-643-2 | 643 | 51% | ECA/TeleCheck Denial Record Number Table ID is fixed value 006, with fixed table length and maximum length 7. |
