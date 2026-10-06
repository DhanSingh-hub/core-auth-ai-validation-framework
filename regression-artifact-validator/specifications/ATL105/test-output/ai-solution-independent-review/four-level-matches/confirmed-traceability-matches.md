# Confirmed BR to TS/TC/TD Side-by-Side Comparison

Only CONFIRMED business-requirement matches are included: evidence-backed Test Solution decisions, not candidate or review-required matches.

- Confirmed BR mappings: **30**

## SEG100-R-015 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |25|1|
| TS |24|1|
| TC |58|2|
| TD |58|2|

### BR side-by-side (25)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:1072|Key Data section repeats up to 3 times (max 48 bytes total); presence based on Key Indicators field.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1089|EMV Request Data Segment is required for all EMV card transactions and is the only segment required for all EMV financial transactions.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1095|Incomm Market Basket Data (Response) Segment returned when Request segment was included in the request.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1131|Standard Message Data Segment is the only required data segment on all Financial Transaction Request messages.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1141|For DUKPT PIN encryption, Encrypted PIN Block Data is a 16- to 20-byte KSN plus a 16-byte PIN block.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1148|Each Fleet Tag has a 3-byte fixed-length tag portion and up to 31 bytes of data.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1153|a field is not populated — still send the Product Data Field Delimiter.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1167|When a field is not populated, still send the Field Separator or Field Delimiter.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1174|A Field Separator follows Product Amount if it is the last element in the segment.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1180|All fields are separated by Field Separators; unpopulated fields still send Field Separator.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1190|a field is not populated — still send the Field Separator.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1196|Expiration Date is reserved for future use.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1235|Print Data Segment always appears at the end of a Financial Transaction response.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1253|Device Card Table Version consists of seven version values, each 5 characters, strung together.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1277|Product Code 991 indicates an administrative amount, used in a pre-pay environment when price is adjusted to include discount amount.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1282|Fuel Volume Data field length is determined by segment length in field 2, up to max 3,600.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1283|Fields are not separated by Field Separators; unpopulated fields are immediately followed by the next field.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1285|Segment 119 always appears in Field No. 3 in Data Section No. 3.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1293|Field Nos. 17-19 are sent up to a maximum of 20 times, once for each card type, in order shown.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1298|Print Data 2 Segment is only sent on Financial Transaction Response messages requiring large amounts of print data.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1356|A Field Separator always follows Segment Type and Segment Length, and the last Tax Amount per product.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1391|Prompt Tokens differ by authorizer; different authorizers support different prompts.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1402|Table Length for Payee Name has a maximum variable value of 017.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1407|Flags in Table Data must be separated by a comma; if a flag is absent, it can be skipped using just the separator.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1467|Field is included in response only when merchant sent Account Updater Request Indicator with value 'Y' or 'I' in request.|CONFIRMED|BR-SEG100-ID-001|Segment Type must be 100 for the Standard Message Data Segment.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|

### TS side-by-side (24)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7229|REQ-SRC-ATL105-PDF-001:1072|MATCHED|SCN-SEG100-ID-001|BR-SEG100-ID-001|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|85\|segment-type`|
|SC-7246|REQ-SRC-ATL105-PDF-001:1089|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7252|REQ-SRC-ATL105-PDF-001:1095|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7288|REQ-SRC-ATL105-PDF-001:1131|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7298|REQ-SRC-ATL105-PDF-001:1141|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7305|REQ-SRC-ATL105-PDF-001:1148|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7310|REQ-SRC-ATL105-PDF-001:1153|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7321|REQ-SRC-ATL105-PDF-001:1167|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7327|REQ-SRC-ATL105-PDF-001:1174|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7333|REQ-SRC-ATL105-PDF-001:1180|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7342|REQ-SRC-ATL105-PDF-001:1190|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7347|REQ-SRC-ATL105-PDF-001:1196|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7385|REQ-SRC-ATL105-PDF-001:1235|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7403|REQ-SRC-ATL105-PDF-001:1253|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7427|REQ-SRC-ATL105-PDF-001:1277|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7433|REQ-SRC-ATL105-PDF-001:1283|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7442|REQ-SRC-ATL105-PDF-001:1293|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7447|REQ-SRC-ATL105-PDF-001:1298|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7505|REQ-SRC-ATL105-PDF-001:1356|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7540|REQ-SRC-ATL105-PDF-001:1391|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7551|REQ-SRC-ATL105-PDF-001:1402|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7556|REQ-SRC-ATL105-PDF-001:1407|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7616|REQ-SRC-ATL105-PDF-001:1467|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7432|REQ-SRC-ATL105-PDF-001:1282|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (58)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-031462|verification_status=PASS|MATCHED|TC-SEG100-ID-001|PASS|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|85\|segment-type`|
|TC-031476|verification_status=PASS|MATCHED|TC-SEG100-ID-002|FAIL|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|85\|segment-type`|
|TC-031477|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031578|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031579|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031580|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031581|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031582|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031583|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031699|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031700|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031701|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031702|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031703|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031704|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031851|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031852|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031854|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031855|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031897|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031898|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031899|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031900|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031985|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031986|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031987|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032188|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032289|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032415|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032416|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032418|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032419|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032513|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032530|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032877|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032878|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032879|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032880|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032886|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032888|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032891|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032893|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032894|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033040|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033271|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033272|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033273|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033274|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033275|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032432|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032433|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032434|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032435|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032441|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032443|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032446|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032448|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032449|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (58)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-031462|33 keys|MATCHED_ANCHOR_ONLY|TD-SEG100-ID-001|7 keys|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|85\|segment-type`; alias crosswalk exists for this segment but no AI element name resolves to Test element 85 (AI observed: AccountNumber, ApprovalNumber, CardDiscretionaryBlockData, CashAmount, FuelPurchaseAmount). JSON key-name overlap 0% (0/7 Test keys found by name).|
|TD-TC-031476|33 keys|MATCHED_ANCHOR_ONLY|TD-SEG100-ID-002|7 keys|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|85\|segment-type`; alias crosswalk exists for this segment but no AI element name resolves to Test element 85 (AI observed: AccountNumber, ApprovalNumber, CardDiscretionaryBlockData, CashAmount, FuelPurchaseAmount). JSON key-name overlap 0% (0/7 Test keys found by name).|
|TD-TC-031477|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031578|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031579|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031580|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031581|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031582|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031583|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031699|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031700|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031701|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031702|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031703|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031704|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031851|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031852|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031854|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031855|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031897|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031898|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031899|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031900|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031985|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031986|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031987|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032188|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032289|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032415|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032416|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032418|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032419|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032513|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032530|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032877|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032878|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032879|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032880|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032886|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032888|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032891|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032893|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032894|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033040|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033271|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033272|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033273|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033274|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033275|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032432|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032433|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032434|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032435|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032441|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032443|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032446|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032448|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032449|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

## SEG100-R-039 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |18|1|
| TS |18|1|
| TC |58|2|
| TD |15|2|

### BR side-by-side (18)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:484|Bill payment/recurring and installment payment transactions must be identified by Variable Information Indicator in Segment 111.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:485|Point-of-Sale Entry Mode in Element 113 must be populated correctly on all transactions when RFID receiver is connected.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:489|For Split Tender, the cardholder must use a different payment form for nonqualified items.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:492|Market-Specific Data Indicator required in FSA/HRA transaction requests.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:519|Card Type ID for Visa is 'VISA'.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:554|Any response to Debit Card Completion transaction ends retry attempts, whether approved or declined.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:565|Merchants supporting cashback must also support Partial Approval transactions (Interlink mandate, April 2013).|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:566|If cashback is partially approved, partially approved amount applies to purchase only, not cashback.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:571|Acquirers must ensure merchants process authorization reversals for card-present errors/cancellations within 24 hours.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:575|Approval Number present in original transaction must be present and identical in the Authorization Only Reversal transaction.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:580|Total QHP Amount (Code 894) is required and includes Clinical, Dental, Prescription/Rx, and Vision/Optical amounts.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:637|Transactions cannot be keyed.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:719|an activation (Please see section 10.7.3.6 — "Activation Transaction.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1566|Inclusion of the EMV Terminal Floor Limits segment in a table load depends on a Special set at the terminal level.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1963|When Variable Information Indicator = 066, Digital Commerce Data is included.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1964|When Variable Information Indicator = 067, Anticipated Amount data is included.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1965|When Variable Information Indicator = 068, Additional Transaction Fee 1 Transaction Fee data is included.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2257|Encrypted PIN Block Data field has maximum length 36 bytes with no example data.|CONFIRMED|BR-SEG100-E121-002|Partial Approval Indicator value 5 requires Amex prepaid balance-receipt context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|

### TS side-by-side (18)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-6887|REQ-SRC-ATL105-PDF-001:719|MATCHED|SCN-SEG100-E121-002|BR-SEG100-E121-002|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|121\|partial-approval-context`|
|SC-7712|REQ-SRC-ATL105-PDF-001:1566|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8109|REQ-SRC-ATL105-PDF-001:1963|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8110|REQ-SRC-ATL105-PDF-001:1964|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8111|REQ-SRC-ATL105-PDF-001:1965|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8402|REQ-SRC-ATL105-PDF-001:2257|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6660|REQ-SRC-ATL105-PDF-001:484|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6661|REQ-SRC-ATL105-PDF-001:485|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6665|REQ-SRC-ATL105-PDF-001:489|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6668|REQ-SRC-ATL105-PDF-001:492|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6695|REQ-SRC-ATL105-PDF-001:519|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6728|REQ-SRC-ATL105-PDF-001:554|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6739|REQ-SRC-ATL105-PDF-001:565|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6740|REQ-SRC-ATL105-PDF-001:566|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6745|REQ-SRC-ATL105-PDF-001:571|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6749|REQ-SRC-ATL105-PDF-001:575|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6754|REQ-SRC-ATL105-PDF-001:580|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6808|REQ-SRC-ATL105-PDF-001:637|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (58)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-035653|verification_status=PASS|MATCHED|TC-SEG100-E121-003|PASS|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|121\|partial-approval-context`|
|TC-035654|verification_status=PASS|MATCHED|TC-SEG100-E121-004|FAIL|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|121\|partial-approval-context`|
|TC-035655|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035656|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035657|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035658|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035659|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035660|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035661|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035662|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035663|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035664|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035665|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035666|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035667|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036563|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036564|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036565|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036566|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036567|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036568|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029698|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029699|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029700|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029701|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029702|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029703|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029704|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029705|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029706|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029707|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029730|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029731|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029732|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029733|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029734|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029980|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029981|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029982|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029983|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029984|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029985|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029986|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029987|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029988|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029989|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029990|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029991|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030017|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030018|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030019|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030020|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030021|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030022|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030060|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030061|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030063|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030064|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (15)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-035653|11 keys|MATCHED_ANCHOR_ONLY|TD-SEG100-E121-003|7 keys|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|121\|partial-approval-context`; alias crosswalk exists for this segment but no AI element name resolves to Test element 121 (AI observed: SegmentType, SegmentLength, VariableInformation). JSON key-name overlap 0% (0/7 Test keys found by name).|
|TD-TC-035654|27 keys|MATCHED_ANCHOR_ONLY|TD-SEG100-E121-004|7 keys|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|121\|partial-approval-context`; alias crosswalk exists for this segment but no AI element name resolves to Test element 121 (AI observed: AccountNumber, ApprovalNumber, CardDiscretionaryBlockData, CashAmount, FuelPurchaseAmount). JSON key-name overlap 0% (0/7 Test keys found by name).|
|TD-TC-035655|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035656|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035657|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035658|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035659|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035660|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035661|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035662|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035663|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035664|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035665|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035666|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035667|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

## SEG100-R-041 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |13|1|
| TS |13|1|
| TC |13|2|
| TD |13|2|

### BR side-by-side (13)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:511|Card Type ID must print on all credit card receipts.|CONFIRMED|BR-SEG100-GAP-ACCOUNT|Segment 100 shall contain the account number or identification value required by the transaction context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:512|Card Type ID for Amex is 'AX'.|CONFIRMED|BR-SEG100-GAP-ACCOUNT|Segment 100 shall contain the account number or identification value required by the transaction context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:649|Split Tender Processing requirements for credit cards also apply to Signature Debit card.|CONFIRMED|BR-SEG100-GAP-ACCOUNT|Segment 100 shall contain the account number or identification value required by the transaction context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:650|Qualified Healthcare Products requirements for credit cards also apply to Signature Debit card.|CONFIRMED|BR-SEG100-GAP-ACCOUNT|Segment 100 shall contain the account number or identification value required by the transaction context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1526|Product Data section repeats per product for a maximum of 10 products, total variable length up to 370 bytes.|CONFIRMED|BR-SEG100-GAP-ACCOUNT|Segment 100 shall contain the account number or identification value required by the transaction context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1532|If unit price omits assumed decimal place digits (e.g. '359' instead of '3059'), it is invalid.|CONFIRMED|BR-SEG100-GAP-ACCOUNT|Segment 100 shall contain the account number or identification value required by the transaction context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1546|Pause Indicator is conditional; fixed value B indicates device should pause about one second before proceeding.|CONFIRMED|BR-SEG100-GAP-ACCOUNT|Segment 100 shall contain the account number or identification value required by the transaction context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1548|Dial String Terminator has fixed value F, sourced from Host.|CONFIRMED|BR-SEG100-GAP-ACCOUNT|Segment 100 shall contain the account number or identification value required by the transaction context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1550|Date and Time Data Segment has a maximum length of 23 alphanumeric characters.|CONFIRMED|BR-SEG100-GAP-ACCOUNT|Segment 100 shall contain the account number or identification value required by the transaction context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1551|Fields in the segment are not separated by Field Separators; unpopulated fields are followed immediately by the next field.|CONFIRMED|BR-SEG100-GAP-ACCOUNT|Segment 100 shall contain the account number or identification value required by the transaction context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1552|Data Type Indicator field has fixed value colon (:) indicating day of week, date, and time data follows.|CONFIRMED|BR-SEG100-GAP-ACCOUNT|Segment 100 shall contain the account number or identification value required by the transaction context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1968|When Variable Information Indicator = 071, Enabler Verification Value data is included.|CONFIRMED|BR-SEG100-GAP-ACCOUNT|Segment 100 shall contain the account number or identification value required by the transaction context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2860|Only Hash Algorithm Indicator value 01 (SHA-1) is currently supported.|CONFIRMED|BR-SEG100-GAP-ACCOUNT|Segment 100 shall contain the account number or identification value required by the transaction context.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|

### TS side-by-side (13)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7672|REQ-SRC-ATL105-PDF-001:1526|MATCHED|TS-SEG100-GAP-ACCOUNT|BR-SEG100-GAP-ACCOUNT|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|2\|account-number`|
|SC-7678|REQ-SRC-ATL105-PDF-001:1532|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7692|REQ-SRC-ATL105-PDF-001:1546|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7694|REQ-SRC-ATL105-PDF-001:1548|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7696|REQ-SRC-ATL105-PDF-001:1550|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7697|REQ-SRC-ATL105-PDF-001:1551|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7698|REQ-SRC-ATL105-PDF-001:1552|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8114|REQ-SRC-ATL105-PDF-001:1968|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-9000|REQ-SRC-ATL105-PDF-001:2860|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6687|REQ-SRC-ATL105-PDF-001:511|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6688|REQ-SRC-ATL105-PDF-001:512|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6820|REQ-SRC-ATL105-PDF-001:649|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6821|REQ-SRC-ATL105-PDF-001:650|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (13)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-033352|verification_status=PASS|MATCHED|TC-SEG100-GAP-ACCOUNT-PASS|PASS|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|2\|account-number`|
|TC-033353|verification_status=PASS|MATCHED|TC-SEG100-GAP-ACCOUNT-FAIL|FAIL|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|2\|account-number`|
|TC-033355|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033356|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033382|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033383|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033385|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033386|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035678|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035679|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035680|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035681|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035682|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (13)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-033352|11 keys|MATCHED_ANCHOR_ONLY|TD-SEG100-GAP-ACCOUNT-PASS|4 keys|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|2\|account-number`; alias crosswalk exists for this segment but no AI element name resolves to Test element 2 (AI observed: SegmentType, SegmentLength, VariableInformation). JSON key-name overlap 0% (0/4 Test keys found by name).|
|TD-TC-033353|33 keys|MATCHED_ELEMENT_CONFIRMED|TD-SEG100-GAP-ACCOUNT-FAIL|4 keys|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|2\|account-number`; CONFIRMED via field-alias crosswalk - AI element name `AccountNumber` maps to Test element 2. JSON key-name overlap (fallback signal) 0%.|
|TD-TC-033355|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033356|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033382|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033383|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033385|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033386|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035678|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035679|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035680|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035681|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035682|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

## SEG100-R-043 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |16|1|
| TS |15|1|
| TC |26|1|
| TD |20|1|

### BR side-by-side (16)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:352|The encrypted PIN block and the KSN are sent to the customer's host system for forwarding to BUYPASS.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:354|A seed key and corresponding serial number can generate approximately one million unique PIN encryption keys.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:359|Pathway terminal configuration for the PIN pad value must be defined for 3DES DUKPT.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:370|Pad digits F consist of (14-L) digits of 1111 (F16) after the entered PIN.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:371|For PAN field, the check digit before the field separator is dropped and the remaining right-most 12 digits of Track 2 data are used.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:375|Device may receive 4R or 4< Decline Code if encrypted PIN data is missing or not sent.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:377|Decline may occur if Terminal Profile PIN Encryption type is not set to UKP.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:390|BUYPASS assigns one merchant number and one device number to the overall AFP system.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:541|For American Express, default track should be Track 1; if unreadable, send unaltered Track 2 on initial transaction before manual entry.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:645|Bill Payment/Recurring Payment Transaction rules for credit card also apply to Signature Debit card.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:669|EBT receipts must show Tender Type, Transaction Type, Clerk ID, Voucher Number, amounts, balances, approved/declined messages, HIP data.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:910|CAVV Revised Format applies to Visa only, identifying Verified By Visa Format with ATN replacing XID.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1623|Address Line 2 positions 13 and 16 must be a space character.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1624|Approval Number is required on reversals for matching information to the original purchase.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1625|If Approval Number is included in a Purchase Request, it is considered preauthorized and BUYPASS does not seek authorization.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1626|In an Authorization Only Reversal, Approval Number present in original transaction must be present and identical in the reversal.|CONFIRMED|BR-SEG100-GAP-PIN|Encrypted PIN Block Data shall use the source-defined DUKPT KSN and PIN-block representation when PIN processing applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|

### TS side-by-side (15)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7769|REQ-SRC-ATL105-PDF-001:1623|MATCHED|TS-SEG100-GAP-PIN|BR-SEG100-GAP-PIN|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|33\|encrypted-pin-block-data`|
|SC-7770|REQ-SRC-ATL105-PDF-001:1624|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7771|REQ-SRC-ATL105-PDF-001:1625|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7772|REQ-SRC-ATL105-PDF-001:1626|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6533|REQ-SRC-ATL105-PDF-001:352|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6535|REQ-SRC-ATL105-PDF-001:354|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6539|REQ-SRC-ATL105-PDF-001:359|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6548|REQ-SRC-ATL105-PDF-001:370|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6549|REQ-SRC-ATL105-PDF-001:371|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6553|REQ-SRC-ATL105-PDF-001:375|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6555|REQ-SRC-ATL105-PDF-001:377|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6568|REQ-SRC-ATL105-PDF-001:390|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6715|REQ-SRC-ATL105-PDF-001:541|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6816|REQ-SRC-ATL105-PDF-001:645|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7067|REQ-SRC-ATL105-PDF-001:910|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (26)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-033927|verification_status=PASS|MATCHED|TC-SEG100-GAP-FIELD-POSITIVE-NEGATIVE|PASS_AND_FAIL|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|33\|encrypted-pin-block-data`|
|TC-033928|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033929|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033930|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033931|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033932|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033933|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033934|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033935|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033936|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033937|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033938|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033939|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033940|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033941|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033942|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033943|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033944|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029465|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029466|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029467|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029468|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029469|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029470|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031030|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031031|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (20)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-033927|27 keys|MATCHED_ELEMENT_CONFIRMED|TD-SEG100-GAP-CONDITIONAL-FIELDS|3 keys|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|33\|encrypted-pin-block-data`; CONFIRMED via field-alias crosswalk - AI element name `CardDiscretionaryBlockData` maps to Test element 12. JSON key-name overlap (fallback signal) 0%.|
|TD-TC-033928|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033929|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033930|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033931|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033932|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033933|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033934|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033935|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033936|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033937|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033938|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033939|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033940|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033941|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033942|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033943|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033944|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031030|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031031|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

## SEG100-R-047 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |5|1|
| TS |5|1|
| TC |20|1|
| TD |20|1|

### BR side-by-side (5)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:1110|A Field Separator follows Field No. 12 in the CA Public Key File Load Request.|CONFIRMED|BR-SEG100-GAP-TAX-AMOUNT|Tax Amount shall represent the total tax amount when tax data applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1697|For EMV offline PIN validated debit, PIN block must contain a PIN or all F's, else transaction declines as invalid transaction.|CONFIRMED|BR-SEG100-GAP-TAX-AMOUNT|Tax Amount shall represent the total tax amount when tax data applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1795|Pump/Lane Number valid codes/values are 1-99.|CONFIRMED|BR-SEG100-GAP-TAX-AMOUNT|Tax Amount shall represent the total tax amount when tax data applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1798|Quantity, Product Code, Unit of Measure, Unit Price, Product Amount repeat for up to 10 products in Segment 102 or 157.|CONFIRMED|BR-SEG100-GAP-TAX-AMOUNT|Tax Amount shall represent the total tax amount when tax data applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1799|Quantity valid codes/values are 00000000, 01-399999999.|CONFIRMED|BR-SEG100-GAP-TAX-AMOUNT|Tax Amount shall represent the total tax amount when tax data applies.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|

### TS side-by-side (5)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7843|REQ-SRC-ATL105-PDF-001:1697|MATCHED|TS-SEG100-GAP-TAX-AMOUNT|BR-SEG100-GAP-TAX-AMOUNT|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|99\|tax-amount`|
|SC-7945|REQ-SRC-ATL105-PDF-001:1799|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7267|REQ-SRC-ATL105-PDF-001:1110|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7941|REQ-SRC-ATL105-PDF-001:1795|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7944|REQ-SRC-ATL105-PDF-001:1798|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (20)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-034229|verification_status=PASS|MATCHED|TC-SEG100-GAP-FIELD-POSITIVE-NEGATIVE|PASS_AND_FAIL|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|99\|tax-amount`|
|TC-034230|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034231|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034232|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034233|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034234|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034774|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034775|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034777|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034778|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034753|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034754|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034755|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034756|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034757|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034758|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034769|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034770|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034772|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034773|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (20)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-034229|27 keys|MATCHED_ELEMENT_CONFIRMED|TD-SEG100-GAP-CONDITIONAL-FIELDS|3 keys|Shares canonical anchor `ATL105\|2026-3\|12.1\|100\|99\|tax-amount`; CONFIRMED via field-alias crosswalk - AI element name `CardDiscretionaryBlockData` maps to Test element 12. JSON key-name overlap (fallback signal) 0%.|
|TD-TC-034230|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034231|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034232|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034233|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034234|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034774|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034775|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034777|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034778|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034753|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034754|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034755|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034756|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034757|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034758|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034769|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034770|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034772|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034773|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

## SEG100-R-060 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |2|1|
| TS |2|1|
| TC |5|1|
| TD |5|1|

### BR side-by-side (2)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:1717|Initiation Time is calculated by BUYPASS using system clock, merchant profile time zone, and daylight saving flag.|CONFIRMED|BR-SEG100-EWIC-VOID|eWIC Purchase Reversal/Void uses Prompt Code 8086 and completes before another eWIC transaction.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1718|If required by the issuer, Job Number is found in the Fleet Data Segment (Segment No. 101).|CONFIRMED|BR-SEG100-EWIC-VOID|eWIC Purchase Reversal/Void uses Prompt Code 8086 and completes before another eWIC transaction.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|

### TS side-by-side (2)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7863|REQ-SRC-ATL105-PDF-001:1717|MATCHED|TS-SEG100-EWIC-VOID|BR-SEG100-EWIC-VOID|Shares canonical anchor `ATL105\|2026-3\|10.5.5\|100\|78\|ewic-purchase-reversal`|
|SC-7864|REQ-SRC-ATL105-PDF-001:1718|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (5)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-034345|verification_status=PASS|MATCHED|TC-SEG100-EWIC-PROMPTS|PASS_AND_FAIL|Shares canonical anchor `ATL105\|2026-3\|10.5.5\|100\|78\|ewic-purchase-reversal`|
|TC-034346|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034347|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034348|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034349|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (5)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-034345|6 keys|MATCHED_ANCHOR_ONLY|TD-SEG100-EWIC-PROMPTS|6 keys|Shares canonical anchor `ATL105\|2026-3\|10.5.5\|100\|78\|ewic-purchase-reversal`; alias crosswalk exists for this segment but no AI element name resolves to Test element 78 (AI observed: SegmentType, SegmentLength). JSON key-name overlap 0% (0/6 Test keys found by name).|
|TD-TC-034346|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034347|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034348|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034349|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

## SEG103-R-003 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |7|1|
| TS |7|1|
| TC |19|2|
| TD |19|1|

### BR side-by-side (7)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:1104|Number of Print Lines indicates how many times Terminal Display/Printer Message occurs in the response.|CONFIRMED|BR-SEG103-CORE-003|Segment Type is 103.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1188|Fields 15-17 (Category Total Data Section) are sent up to 18 times, once per card type, only when data occurs for that type.|CONFIRMED|BR-SEG103-CORE-003|Segment Type is 103.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1204|All fields in Check Data Segment are separated by Field Separators; unpopulated fields still send the separator.|CONFIRMED|BR-SEG103-CORE-003|Segment Type is 103.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1305|Token PAN Suffix is returned in the response only if supplied by the authorizer.|CONFIRMED|BR-SEG103-CORE-003|Segment Type is 103.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1370|The ETX (▲) after last product's tax data signifies end of the segment.|CONFIRMED|BR-SEG103-CORE-003|Segment Type is 103.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1785|Financial transaction device transmits a 4-character Prompt Code at initiation.|CONFIRMED|BR-SEG103-CORE-003|Segment Type is 103.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1786|Special transaction device transmits a 3-character Prompt Code (Card Type) at initiation.|CONFIRMED|BR-SEG103-CORE-003|Segment Type is 103.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|

### TS side-by-side (7)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7261|REQ-SRC-ATL105-PDF-001:1104|MATCHED|SCN-SEG103-CORE-001|BR-SEG103-CORE-003, BR-SEG103-CORE-004|Shares canonical anchor `ATL105\|2026-3\|12.4\|103\|85\|segment-type`|
|SC-7340|REQ-SRC-ATL105-PDF-001:1188|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7355|REQ-SRC-ATL105-PDF-001:1204|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7454|REQ-SRC-ATL105-PDF-001:1305|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7519|REQ-SRC-ATL105-PDF-001:1370|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7931|REQ-SRC-ATL105-PDF-001:1785|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7932|REQ-SRC-ATL105-PDF-001:1786|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (19)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-031946|verification_status=PASS|MATCHED|TC-SEG103-CORE-001|PASS|Shares canonical anchor `ATL105\|2026-3\|12.4\|103\|85\|segment-type`|
|TC-032018|verification_status=PASS|MATCHED|TC-SEG103-CORE-002|FAIL|Shares canonical anchor `ATL105\|2026-3\|12.4\|103\|85\|segment-type`|
|TC-032019|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032572|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032573|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032963|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032964|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034651|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034652|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034654|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034656|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034657|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034658|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034663|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034664|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034666|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034668|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034669|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034670|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (19)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-031946|39 keys|MATCHED_ANCHOR_ONLY|TD-SEG103-CORE-001|3 keys|Shares canonical anchor `ATL105\|2026-3\|12.4\|103\|85\|segment-type`; no field-alias crosswalk available for this segment/element yet, falling back to JSON key-name overlap 0% (0/3 expected Test keys found by name; 3 Test keys not found, 39 extra AI keys). Values are not compared.|
|TD-TC-032018|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032019|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032572|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032573|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032963|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032964|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034651|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034652|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034654|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034656|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034657|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034658|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034663|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034664|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034666|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034668|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034669|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034670|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

## SEG103-R-004 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |8|1|
| TS |8|1|
| TC |23|2|
| TD |23|1|

### BR side-by-side (8)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:943|Standard Message Data Segment is the only segment required for all financial transactions.|CONFIRMED|BR-SEG103-CORE-004|Segment Length is 3 or 4 digits; 4 digits required for EBT-with-eWIC transactions.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1067|No need to set any load flag on the terminal record; Moneris Key Load can be performed at any time.|CONFIRMED|BR-SEG103-CORE-004|Segment Length is 3 or 4 digits; 4 digits required for EBT-with-eWIC transactions.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1129|Error codes BLOCK NBR NOT NUMERIC or BLOCK NBR NOT 000-xxx indicate key load transaction failure.|CONFIRMED|BR-SEG103-CORE-004|Segment Length is 3 or 4 digits; 4 digits required for EBT-with-eWIC transactions.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1142|Local Date and Local Time is conditional, identifying date/time of preauthorized transaction.|CONFIRMED|BR-SEG103-CORE-004|Segment Length is 3 or 4 digits; 4 digits required for EBT-with-eWIC transactions.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1296|Use Card Labels (in parentheses) instead of field numbers to identify total buckets.|CONFIRMED|BR-SEG103-CORE-004|Segment Length is 3 or 4 digits; 4 digits required for EBT-with-eWIC transactions.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1433|Fields 1-2, 2-3, and 3-4 are separated by Field Separators; segment ends with a field separator.|CONFIRMED|BR-SEG103-CORE-004|Segment Length is 3 or 4 digits; 4 digits required for EBT-with-eWIC transactions.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1474|VAU006 indicates transaction is not a qualifying transaction type.|CONFIRMED|BR-SEG103-CORE-004|Segment Length is 3 or 4 digits; 4 digits required for EBT-with-eWIC transactions.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1759|Communications Test Requests: Number of Segments always followed by Element 120, Network Management Message.|CONFIRMED|BR-SEG103-CORE-004|Segment Length is 3 or 4 digits; 4 digits required for EBT-with-eWIC transactions.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|

### TS side-by-side (8)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7224|REQ-SRC-ATL105-PDF-001:1067|MATCHED|SCN-SEG103-CORE-001|BR-SEG103-CORE-003, BR-SEG103-CORE-004|Shares canonical anchor `ATL105\|2026-3\|12.4\|103\|84\|segment-length`|
|SC-7286|REQ-SRC-ATL105-PDF-001:1129|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7299|REQ-SRC-ATL105-PDF-001:1142|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7445|REQ-SRC-ATL105-PDF-001:1296|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7582|REQ-SRC-ATL105-PDF-001:1433|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7623|REQ-SRC-ATL105-PDF-001:1474|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7905|REQ-SRC-ATL105-PDF-001:1759|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7100|REQ-SRC-ATL105-PDF-001:943|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (25)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-031388|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031705|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031706|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031708|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031709|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031710|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031711|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032523|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033084|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033085|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033086|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033087|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033093|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033095|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033098|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033100|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033101|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031085|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031086|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031087|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031088|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031089|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031090|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|||TEST_ONLY|TC-SEG103-CORE-001|PASS|No AI TC artifact shares a canonical anchor with this Test TC item|
|||TEST_ONLY|TC-SEG103-CORE-002|FAIL|No AI TC artifact shares a canonical anchor with this Test TC item|

### TD side-by-side (24)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-031388|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031705|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031706|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031708|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031709|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031710|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031711|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032523|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033084|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033085|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033086|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033087|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033093|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033095|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033098|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033100|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033101|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031085|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031086|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031087|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031088|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031089|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031090|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|||TEST_ONLY|TD-SEG103-CORE-001|3 keys|No AI TD artifact shares a canonical anchor with this Test TD item|

## SEG103-R-009 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |5|1|
| TS |4|1|
| TC |9|1|
| TD |9|0|

### BR side-by-side (5)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:669|EBT receipts must show Tender Type, Transaction Type, Clerk ID, Voucher Number, amounts, balances, approved/declined messages, HIP data.|CONFIRMED|BR-SEG103-FIELD-009|Clerk ID is numeric max 10 when populated.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:671|Receipt must indicate whether transaction is Food Stamp or Cash Benefit and the transaction type attempted.|CONFIRMED|BR-SEG103-FIELD-009|Clerk ID is numeric max 10 when populated.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:674|Food stamp dollar amount must be printed on Food Stamp transaction receipt.|CONFIRMED|BR-SEG103-FIELD-009|Clerk ID is numeric max 10 when populated.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1613|For Loyalty Totals Request with Update Code = 'T', Account Number field must contain 'TOTALS REQUEST'.|CONFIRMED|BR-SEG103-FIELD-009|Clerk ID is numeric max 10 when populated.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1614|For other loyalty advice transactions, Account Number must contain the actual Loyalty Account Number.|CONFIRMED|BR-SEG103-FIELD-009|Clerk ID is numeric max 10 when populated.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|

### TS side-by-side (4)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7759|REQ-SRC-ATL105-PDF-001:1613|MATCHED|SCN-SEG103-FIELD-001|BR-SEG103-FIELD-009|Shares canonical anchor `ATL105\|2026-3\|12.4\|103\|18\|clerk-id`|
|SC-7760|REQ-SRC-ATL105-PDF-001:1614|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6840|REQ-SRC-ATL105-PDF-001:671|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6843|REQ-SRC-ATL105-PDF-001:674|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (9)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-033862|verification_status=PASS|MATCHED|TC-SEG103-FIELD-001|FAIL|Shares canonical anchor `ATL105\|2026-3\|12.4\|103\|18\|clerk-id`|
|TC-033863|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033864|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033868|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033869|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033870|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033872|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033873|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033874|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (9)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-033862|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033863|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033864|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033868|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033869|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033870|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033872|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033873|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033874|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

## SEG103-R-010 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |4|1|
| TS |4|1|
| TC |7|1|
| TD |1|0|

### BR side-by-side (4)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:662|PAN printed on receipts must be truncated to last four digits, preceded by X's equal to truncated digit count.|CONFIRMED|BR-SEG103-FIELD-010|Voucher ID is numeric max 10 when populated.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:666|No signature line — printing an EBT receipt.|CONFIRMED|BR-SEG103-FIELD-010|Voucher ID is numeric max 10 when populated.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:675|Cash benefit dollar amount(s) must be printed on Cash Benefit transaction receipt, broken down by purchase vs cash back with totals.|CONFIRMED|BR-SEG103-FIELD-010|Voucher ID is numeric max 10 when populated.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1837|Print Data Segment length is 001-1009.|CONFIRMED|BR-SEG103-FIELD-010|Voucher ID is numeric max 10 when populated.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|

### TS side-by-side (4)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7983|REQ-SRC-ATL105-PDF-001:1837|MATCHED|SCN-SEG103-FIELD-002|BR-SEG103-FIELD-010|Shares canonical anchor `ATL105\|2026-3\|12.4\|103\|109\|voucher-id`|
|SC-6833|REQ-SRC-ATL105-PDF-001:662|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6837|REQ-SRC-ATL105-PDF-001:666|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6844|REQ-SRC-ATL105-PDF-001:675|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (7)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-034936|verification_status=PASS|MATCHED|TC-SEG103-FIELD-002|FAIL|Shares canonical anchor `ATL105\|2026-3\|12.4\|103\|109\|voucher-id`|
|TC-030252|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030253|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030254|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030255|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030256|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030257|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (1)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-034936|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

## SEG103-R-011 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |3|1|
| TS |3|1|
| TC |0|1|
| TD |0|1|

### BR side-by-side (3)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:2003|When Additional Information Indicator = 021, Re-Price Data Response Information is included.|CONFIRMED|BR-SEG103-WIC-011|WIC Discount Amount uses the positional Account Type/Amount Type/Currency Code/signed Amount format.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2004|When Additional Information Indicator = 022, CAVV Result Information is included.|CONFIRMED|BR-SEG103-WIC-011|WIC Discount Amount uses the positional Account Type/Amount Type/Currency Code/signed Amount format.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2694|mPOS devices (Phone/Tablet + Dongle) without PIN Pad should use PIN Entry Capability Mode code 3.|CONFIRMED|BR-SEG103-WIC-011|WIC Discount Amount uses the positional Account Type/Amount Type/Currency Code/signed Amount format.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|

### TS side-by-side (3)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-8149|REQ-SRC-ATL105-PDF-001:2003|MATCHED|SCN-SEG103-WIC-001|BR-SEG103-WIC-011|Shares canonical anchor `ATL105\|2026-3\|13.2\|103\|153\|wic-discount-amount-format`|
|SC-8150|REQ-SRC-ATL105-PDF-001:2004|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8834|REQ-SRC-ATL105-PDF-001:2694|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (1)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|||TEST_ONLY|TC-SEG103-WIC-001|PASS|No AI TC artifact shares a canonical anchor with this Test TC item|

### TD side-by-side (1)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|||TEST_ONLY|TD-SEG103-WIC-001|2 keys|No AI TD artifact shares a canonical anchor with this Test TD item|

## SEG103-R-014 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |7|1|
| TS |7|1|
| TC |12|1|
| TD |12|1|

### BR side-by-side (7)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:2020|When Additional Information Indicator = 039, Transaction Link Action Indicator is included.|CONFIRMED|BR-SEG103-EBT-014|EBT Program Data begins with a 3-digit Total Length subelement bounded to 264.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2037|Value 5 indicates merchant only supports card balance receipt, not Partial Approval processing.|CONFIRMED|BR-SEG103-EBT-014|EBT Program Data begins with a 3-digit Total Length subelement bounded to 264.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2043|If MICR data > 50 bytes and prompt code is ECA/TeleCheck check service, refer to ECA/TeleCheck Data Segment (Segment 113).|CONFIRMED|BR-SEG103-EBT-014|EBT Program Data begins with a 3-digit Total Length subelement bounded to 264.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2197|SPDH Header appears in both Moneris Key Load request and response.|CONFIRMED|BR-SEG103-EBT-014|EBT Program Data begins with a 3-digit Total Length subelement bounded to 264.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2688|If Contactless MSR is attached with no chip capability, all transactions use PIN Entry Capability Mode code 8.|CONFIRMED|BR-SEG103-EBT-014|EBT Program Data begins with a 3-digit Total Length subelement bounded to 264.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2690|If no Contactless MSR attached and PIN debit feature enabled, all transactions use PIN Entry Capability Mode code 1.|CONFIRMED|BR-SEG103-EBT-014|EBT Program Data begins with a 3-digit Total Length subelement bounded to 264.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2691|If no Contactless MSR attached and PIN debit feature disabled, all transactions use PIN Entry Capability Mode code 2.|CONFIRMED|BR-SEG103-EBT-014|EBT Program Data begins with a 3-digit Total Length subelement bounded to 264.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|

### TS side-by-side (7)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-8189|REQ-SRC-ATL105-PDF-001:2043|MATCHED|SCN-SEG103-EBT-001|BR-SEG103-EBT-014, BR-SEG103-EBT-016, BR-SEG103-EBT-017|Shares canonical anchor `ATL105\|2026-3\|13.2\|103\|164\|ebt-program-data-total-length`|
|SC-8166|REQ-SRC-ATL105-PDF-001:2020|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8183|REQ-SRC-ATL105-PDF-001:2037|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8343|REQ-SRC-ATL105-PDF-001:2197|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8828|REQ-SRC-ATL105-PDF-001:2688|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8830|REQ-SRC-ATL105-PDF-001:2690|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8831|REQ-SRC-ATL105-PDF-001:2691|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (13)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-035896|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035897|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035899|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035901|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035903|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035904|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035865|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035866|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035867|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035868|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035869|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035870|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|||TEST_ONLY|TC-SEG103-EBT-001|PASS|No AI TC artifact shares a canonical anchor with this Test TC item|

### TD side-by-side (13)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-035896|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035897|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035899|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035901|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035903|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035904|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035865|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035866|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035867|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035868|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035869|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035870|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|||TEST_ONLY|TD-SEG103-EBT-001|2 keys|No AI TD artifact shares a canonical anchor with this Test TD item|

## SEG103-R-015 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |2|0|
| TS |2|0|
| TC |12|0|
| TD |12|0|

### BR side-by-side (2)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:2038|Support for Partial Authorization is mandatory for all card brands; must be populated correctly for card present transactions.|UNMATCHED|SEG103-R-015||Test Solution rule catalog has no business-requirement artifact for SEG103-R-015|
|REQ-SRC-ATL105-PDF-001:2039|For Amex transactions with zero amount and values 1 or 5, merchant will not receive card balance in response.|UNMATCHED|SEG103-R-015||Test Solution rule catalog has no business-requirement artifact for SEG103-R-015|

### TS side-by-side (2)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-8184|REQ-SRC-ATL105-PDF-001:2038|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8185|REQ-SRC-ATL105-PDF-001:2039|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (12)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-035871|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035872|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035873|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035874|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035875|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035876|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035877|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035878|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035879|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035880|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035881|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035882|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (12)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-035871|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035872|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035873|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035874|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035875|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035876|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035877|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035878|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035879|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035880|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035881|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035882|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

## SEG103-R-016 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |10|1|
| TS |10|1|
| TC |36|1|
| TD |36|1|

### BR side-by-side (10)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:712|HIP receipts must follow receipt requirements listed in section 10.1.7.|CONFIRMED|BR-SEG103-EBT-016|EBT Program Data subelement TAG must be one of the documented values.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1764|Password required for totals request or electronic mail request.|CONFIRMED|BR-SEG103-EBT-016|EBT Program Data subelement TAG must be one of the documented values.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1773|Phone Number data consists of all data up to the following C (Log-on Indicator) or A (Delimiter) character.|CONFIRMED|BR-SEG103-EBT-016|EBT Program Data subelement TAG must be one of the documented values.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1775|Product Amount and related elements repeat for up to a maximum of 10 products in Segment 102 or 157.|CONFIRMED|BR-SEG103-EBT-016|EBT Program Data subelement TAG must be one of the documented values.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1776|Valid Product Amount values range from 1 to 999999999999.|CONFIRMED|BR-SEG103-EBT-016|EBT Program Data subelement TAG must be one of the documented values.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1782|Prompt Code (4 chars) identifies Transaction Type (1 char) and Card Type (3 chars) for financial transactions.|CONFIRMED|BR-SEG103-EBT-016|EBT Program Data subelement TAG must be one of the documented values.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1783|Special transaction Prompt Code is 3-character Card Type identifying requested special data.|CONFIRMED|BR-SEG103-EBT-016|EBT Program Data subelement TAG must be one of the documented values.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2045|State Code is required when Driver's License (Element 123) is included in a check transaction.|CONFIRMED|BR-SEG103-EBT-016|EBT Program Data subelement TAG must be one of the documented values.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2051|Loyalty Program ID is required on all loyalty card transactions.|CONFIRMED|BR-SEG103-EBT-016|EBT Program Data subelement TAG must be one of the documented values.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2052|Loyalty Account Number is required on all loyalty card transactions.|CONFIRMED|BR-SEG103-EBT-016|EBT Program Data subelement TAG must be one of the documented values.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|

### TS side-by-side (10)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7919|REQ-SRC-ATL105-PDF-001:1773|MATCHED|SCN-SEG103-EBT-001|BR-SEG103-EBT-014, BR-SEG103-EBT-016, BR-SEG103-EBT-017|Shares canonical anchor `ATL105\|2026-3\|13.2\|103\|164\|ebt-program-data-tag-enumeration`|
|SC-7921|REQ-SRC-ATL105-PDF-001:1775|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7922|REQ-SRC-ATL105-PDF-001:1776|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7910|REQ-SRC-ATL105-PDF-001:1764|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8191|REQ-SRC-ATL105-PDF-001:2045|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8198|REQ-SRC-ATL105-PDF-001:2052|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7928|REQ-SRC-ATL105-PDF-001:1782|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7929|REQ-SRC-ATL105-PDF-001:1783|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8197|REQ-SRC-ATL105-PDF-001:2051|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6880|REQ-SRC-ATL105-PDF-001:712|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (36)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-034569|verification_status=PASS|MATCHED|TC-SEG103-EBT-001|PASS|Shares canonical anchor `ATL105\|2026-3\|13.2\|103\|164\|ebt-program-data-tag-enumeration`|
|TC-034570|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034572|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034573|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034574|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034575|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034577|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034578|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034510|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035916|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035917|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035918|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035919|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035920|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035921|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035947|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035948|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035949|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035951|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035952|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035953|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034615|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034616|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034618|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034620|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034621|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034622|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034627|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034628|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034630|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034632|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034633|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034634|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035941|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035942|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035943|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (36)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-034569|11 keys|MATCHED_ANCHOR_ONLY|TD-SEG103-EBT-001|2 keys|Shares canonical anchor `ATL105\|2026-3\|13.2\|103\|164\|ebt-program-data-tag-enumeration`; no field-alias crosswalk available for this segment/element yet, falling back to JSON key-name overlap 0% (0/2 expected Test keys found by name; 2 Test keys not found, 11 extra AI keys). Values are not compared.|
|TD-TC-034570|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034572|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034573|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034574|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034575|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034577|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034578|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034510|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035916|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035917|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035918|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035919|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035920|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035921|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035947|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035948|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035949|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035951|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035952|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035953|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034615|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034616|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034618|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034620|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034621|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034622|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034627|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034628|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034630|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034632|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034633|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034634|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035941|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035942|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035943|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

## SEG103-R-017 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |34|1|
| TS |33|1|
| TC |55|1|
| TD |55|1|

### BR side-by-side (34)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:1086|Purchase Card Data Segment sent only on transactions requiring purchase card data.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1103|Terminal Display/Printer Message data element can be repeated up to a maximum of three times.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1109|Field Separators are present between Field Nos. 1-2, 2-3, 3-4, 4-5, 5-6, but not between 6-11.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1145|All fields in Fleet Data Segment are separated by Field Separators; unpopulated fields still send the separator.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1149|Valid 3-byte Fleet Tag codes include DLS (an3), DLN (an22), PON (an31), INV (an31), TRP (an15), UNT (an31).|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1155|Segment 102 and segment 157 are mutually exclusive; sending both causes decline.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1162|A unique Product Code must be sent for each type of fuel purchase.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1167|When a field is not populated, still send the Field Separator or Field Delimiter.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1181|a field is not populated — still send the Field Separator.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1194|Update Code field presence is Conditional depending on loyalty function performed.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1210|Check Number is required for manually keyed check data.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1249|Card Table Load Version comes from Host and is echoed back in each subsequent block request.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1262|Prompt Code 903 is used for Site Configuration Data loads sent in a Proprietary Data Load request.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1267|Response Code T means Approved-Proprietary load data, no more data pending.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1291|Segment Type fixed value is 119.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1297|Card categories marked with * are non-financial; their dollar amounts are not included in the Grand Total.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1299|Print Data 2 Segment always appears at the end of a Financial Transaction response.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1304|Segment 123 required for transactions including MasterCard Token, DSRP, or Visa TAVV data.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1307|When first 28 bytes of SafeKey Data value is not applicable, it should be space filled.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1312|No Field Separators present between elements within EMV Additional Information Section.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1405|Cash Advance Limit amount must include a decimal point.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1413|Sum of all products in a product category should not exceed the stated Amount.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1416|Product Data is repeated for as many products as needed, separated by '\|' and ending with '\|'.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1421|Edit mask '?' indicates re-prompt using previously received formatting.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1481|VAU013 indicates expiry date in authorization request is later than VAU data.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2021|When Additional Information Indicator = 040, Fraud Score is included.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2042|MICR Data is also included in Account Number (Element 2) of the Standard Message Data Segment (Segment 100).|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2046|Check Type is required on all manually entered check transactions.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2047|Check Type valid codes are P (Personal) and C (Company).|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2048|Check Number is required on all manually entered check transactions.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2049|ECA/ TeleCheck Clerk ID valid values range from 1 to 999999.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2689|For EMV Contact and Contactless, valid PIN Entry Capability Mode code is 1, not 8; code 8 is reserved for Contactless MSR only.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2692|PIN entry capability mode code 3 applies only to MasterCard Mobile POS transactions.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2693|mPOS devices (Phone/Tablet + Dongle) with PIN Pad should use PIN Entry Capability Mode code 1.|CONFIRMED|BR-SEG103-EBT-017|EBT Program Data subelement with TAG 50 requires ACCOUNT TYPE 98.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|

### TS side-by-side (33)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7243|REQ-SRC-ATL105-PDF-001:1086|MATCHED|SCN-SEG103-EBT-001|BR-SEG103-EBT-014, BR-SEG103-EBT-016, BR-SEG103-EBT-017|Shares canonical anchor `ATL105\|2026-3\|13.2\|103\|164\|ebt-program-data-account-type-98`|
|SC-7321|REQ-SRC-ATL105-PDF-001:1167|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7260|REQ-SRC-ATL105-PDF-001:1103|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7266|REQ-SRC-ATL105-PDF-001:1109|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7302|REQ-SRC-ATL105-PDF-001:1145|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7306|REQ-SRC-ATL105-PDF-001:1149|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7316|REQ-SRC-ATL105-PDF-001:1162|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7334|REQ-SRC-ATL105-PDF-001:1181|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7345|REQ-SRC-ATL105-PDF-001:1194|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7361|REQ-SRC-ATL105-PDF-001:1210|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7399|REQ-SRC-ATL105-PDF-001:1249|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7412|REQ-SRC-ATL105-PDF-001:1262|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7417|REQ-SRC-ATL105-PDF-001:1267|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7440|REQ-SRC-ATL105-PDF-001:1291|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7446|REQ-SRC-ATL105-PDF-001:1297|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7448|REQ-SRC-ATL105-PDF-001:1299|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7453|REQ-SRC-ATL105-PDF-001:1304|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7456|REQ-SRC-ATL105-PDF-001:1307|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7461|REQ-SRC-ATL105-PDF-001:1312|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7554|REQ-SRC-ATL105-PDF-001:1405|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7562|REQ-SRC-ATL105-PDF-001:1413|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7565|REQ-SRC-ATL105-PDF-001:1416|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7570|REQ-SRC-ATL105-PDF-001:1421|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7630|REQ-SRC-ATL105-PDF-001:1481|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8167|REQ-SRC-ATL105-PDF-001:2021|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8188|REQ-SRC-ATL105-PDF-001:2042|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8192|REQ-SRC-ATL105-PDF-001:2046|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8193|REQ-SRC-ATL105-PDF-001:2047|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8194|REQ-SRC-ATL105-PDF-001:2048|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8195|REQ-SRC-ATL105-PDF-001:2049|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8829|REQ-SRC-ATL105-PDF-001:2689|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8832|REQ-SRC-ATL105-PDF-001:2692|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8833|REQ-SRC-ATL105-PDF-001:2693|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (56)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-031451|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031452|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031453|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031454|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031722|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031723|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031724|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031725|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031726|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031785|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031786|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031788|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031789|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031971|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031972|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031973|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032037|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032038|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032355|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032356|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032358|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032360|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032361|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032362|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032482|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032483|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032484|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032485|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032491|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032493|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032496|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032498|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032499|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032527|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032533|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032570|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032571|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032576|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032577|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032585|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035889|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035890|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035891|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035892|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035894|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035895|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035923|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035924|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035926|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035927|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035929|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035930|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035932|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035933|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035935|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|||TEST_ONLY|TC-SEG103-EBT-001|PASS|No AI TC artifact shares a canonical anchor with this Test TC item|

### TD side-by-side (56)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-031451|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031452|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031453|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031454|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031722|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031723|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031724|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031725|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031726|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031785|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031786|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031788|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031789|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031971|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031972|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031973|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032037|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032038|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032355|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032356|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032358|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032360|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032361|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032362|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032482|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032483|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032484|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032485|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032491|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032493|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032496|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032498|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032499|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032527|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032533|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032570|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032571|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032576|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032577|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032585|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035889|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035890|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035891|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035892|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035894|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035895|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035923|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035924|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035926|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035927|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035929|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035930|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035932|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035933|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035935|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|||TEST_ONLY|TD-SEG103-EBT-001|2 keys|No AI TD artifact shares a canonical anchor with this Test TD item|

## SEG103-R-022 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |4|0|
| TS |4|0|
| TC |10|0|
| TD |8|0|

### BR side-by-side (4)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:711|Incentive balances are reset at the beginning of the fiscal month.|UNMATCHED|SEG103-R-022||Test Solution rule catalog has no business-requirement artifact for SEG103-R-022|
|REQ-SRC-ATL105-PDF-001:2044|Driver's License is required on all manually entered check transactions.|UNMATCHED|SEG103-R-022||Test Solution rule catalog has no business-requirement artifact for SEG103-R-022|
|REQ-SRC-ATL105-PDF-001:2050|Extended MICR Data must be populated in addition to MICR Data when raw MICR data exceeds the length.|UNMATCHED|SEG103-R-022||Test Solution rule catalog has no business-requirement artifact for SEG103-R-022|
|REQ-SRC-ATL105-PDF-001:2696|Balance Information Table ID must have fixed value 001.|UNMATCHED|SEG103-R-022||Test Solution rule catalog has no business-requirement artifact for SEG103-R-022|

### TS side-by-side (4)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-8196|REQ-SRC-ATL105-PDF-001:2050|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8190|REQ-SRC-ATL105-PDF-001:2044|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8836|REQ-SRC-ATL105-PDF-001:2696|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6879|REQ-SRC-ATL105-PDF-001:711|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (10)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-035937|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035938|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035909|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035910|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035911|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035912|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035913|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035914|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030325|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030327|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (8)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-035937|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035938|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035909|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035910|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035911|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035912|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035913|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035914|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

## SEG103-R-023 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |9|0|
| TS |9|0|
| TC |0|0|
| TD |0|0|

### BR side-by-side (9)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:708|Credit card receipt requirements apply to eWIC receipts except for Approved/Declined message.|UNMATCHED|SEG103-R-023||Test Solution rule catalog has no business-requirement artifact for SEG103-R-023|
|REQ-SRC-ATL105-PDF-001:1672|Current Date month value must be between 01 and 12.|UNMATCHED|SEG103-R-023||Test Solution rule catalog has no business-requirement artifact for SEG103-R-023|
|REQ-SRC-ATL105-PDF-001:2005|When Additional Information Indicator = 023, MCX Reference Number Information is included.|UNMATCHED|SEG103-R-023||Test Solution rule catalog has no business-requirement artifact for SEG103-R-023|
|REQ-SRC-ATL105-PDF-001:2006|When Additional Information Indicator = 024, Carwash Indicator Information is included.|UNMATCHED|SEG103-R-023||Test Solution rule catalog has no business-requirement artifact for SEG103-R-023|
|REQ-SRC-ATL105-PDF-001:2007|When Additional Information Indicator = 025, Language Indicator Information is included.|UNMATCHED|SEG103-R-023||Test Solution rule catalog has no business-requirement artifact for SEG103-R-023|
|REQ-SRC-ATL105-PDF-001:2008|When Additional Information Indicator = 026, DST Response Information is included.|UNMATCHED|SEG103-R-023||Test Solution rule catalog has no business-requirement artifact for SEG103-R-023|
|REQ-SRC-ATL105-PDF-001:2009|When Additional Information Indicator = 027, Universal Unique Identifier is included.|UNMATCHED|SEG103-R-023||Test Solution rule catalog has no business-requirement artifact for SEG103-R-023|
|REQ-SRC-ATL105-PDF-001:2010|When Additional Information Indicator = 028, Transaction Identifier Information is included.|UNMATCHED|SEG103-R-023||Test Solution rule catalog has no business-requirement artifact for SEG103-R-023|
|REQ-SRC-ATL105-PDF-001:2012|When Additional Information Indicator = 030, Merchant Advice code is included.|UNMATCHED|SEG103-R-023||Test Solution rule catalog has no business-requirement artifact for SEG103-R-023|

### TS side-by-side (9)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7818|REQ-SRC-ATL105-PDF-001:1672|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8151|REQ-SRC-ATL105-PDF-001:2005|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8152|REQ-SRC-ATL105-PDF-001:2006|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8153|REQ-SRC-ATL105-PDF-001:2007|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8154|REQ-SRC-ATL105-PDF-001:2008|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8155|REQ-SRC-ATL105-PDF-001:2009|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8156|REQ-SRC-ATL105-PDF-001:2010|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8158|REQ-SRC-ATL105-PDF-001:2012|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6876|REQ-SRC-ATL105-PDF-001:708|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (0)

_No AI or Test Solution artifacts at this level._

### TD side-by-side (0)

_No AI or Test Solution artifacts at this level._

## SEG104-R-004 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |43|0|
| TS |61|0|
| TC |121|0|
| TD |109|0|

### BR side-by-side (43)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:254|Enhanced Fleet Data must be alphanumeric and must not exceed 999 bytes.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:923|EMV data is not required on Reversal transactions.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:930|If card is not chip or device not chip capable, and card is swiped, send MSR Entry Mode of '90' or spec equivalent.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:936|R indicates required, O indicates optional, C indicates conditional entry for a data element.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1044|Information Byte fixed value is '?' identifying a Download Request.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1054|Software Dial Load Data Segment is sent only on a Software Load Response.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1072|Key Data section repeats up to 3 times (max 48 bytes total); presence based on Key Indicators field.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1089|EMV Request Data Segment is required for all EMV card transactions and is the only segment required for all EMV financial transactions.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1095|Incomm Market Basket Data (Response) Segment returned when Request segment was included in the request.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1131|Standard Message Data Segment is the only required data segment on all Financial Transaction Request messages.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1133|All fields separated by Field Separator; when a field is not populated, still send the Field Separator.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1135|Segment Length includes Segment Type's length and Field Separators.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1141|For DUKPT PIN encryption, Encrypted PIN Block Data is a 16- to 20-byte KSN plus a 16-byte PIN block.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1148|Each Fleet Tag has a 3-byte fixed-length tag portion and up to 31 bytes of data.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1153|a field is not populated — still send the Product Data Field Delimiter.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1167|When a field is not populated, still send the Field Separator or Field Delimiter.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1174|A Field Separator follows Product Amount if it is the last element in the segment.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1180|All fields are separated by Field Separators; unpopulated fields still send Field Separator.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1190|a field is not populated — still send the Field Separator.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1196|Expiration Date is reserved for future use.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1235|Print Data Segment always appears at the end of a Financial Transaction response.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1248|Prompt Code, Pending valid values are 0901, 0902, 0904, 0981.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1253|Device Card Table Version consists of seven version values, each 5 characters, strung together.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1277|Product Code 991 indicates an administrative amount, used in a pre-pay environment when price is adjusted to include discount amount.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1283|Fields are not separated by Field Separators; unpopulated fields are immediately followed by the next field.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1285|Segment 119 always appears in Field No. 3 in Data Section No. 3.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1290|Prompt Code fixed value is 990 identifying format type for requested totals.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1293|Field Nos. 17-19 are sent up to a maximum of 20 times, once for each card type, in order shown.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1298|Print Data 2 Segment is only sent on Financial Transaction Response messages requiring large amounts of print data.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1356|A Field Separator always follows Segment Type and Segment Length, and the last Tax Amount per product.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1391|Prompt Tokens differ by authorizer; different authorizers support different prompts.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1399|Prompt Code 999 indicates no prompts issued.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1402|Table Length for Payee Name has a maximum variable value of 017.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1407|Flags in Table Data must be separated by a comma; if a flag is absent, it can be skipped using just the separator.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1419|Product Data repeated for as many products as needed, separated by '\|' and ending with '\|'.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1467|Field is included in response only when merchant sent Account Updater Request Indicator with value 'Y' or 'I' in request.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1658|Effective May 1, 2025, expiration date is optional for Accel/STAR COF transactions (bill payment, recurring, installment).|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1696|PIN data must be customer-keyed and encrypted using DUKPT; if absent from request, insert a Field Separator.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1697|For EMV offline PIN validated debit, PIN block must contain a PIN or all F's, else transaction declines as invalid transaction.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1771|Secondary Phone Number is used once attempts using the primary Phone Number are exhausted.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1772|A tertiary Phone Number is assigned to but not used by the device.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1781|In Proprietary Load Response (Prompt Code 904), first Product Code instance identifies discount-eligible product, second identifies discount product code for Segment 102.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|
|REQ-SRC-ATL105-PDF-001:1799|Quantity valid codes/values are 00000000, 01-399999999.|UNMATCHED|SEG104-R-004||Test Solution rule catalog has no business-requirement artifact for SEG104-R-004|

### TS side-by-side (61)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7201|REQ-SRC-ATL105-PDF-001:1044|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7211|REQ-SRC-ATL105-PDF-001:1054|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7229|REQ-SRC-ATL105-PDF-001:1072|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7246|REQ-SRC-ATL105-PDF-001:1089|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7252|REQ-SRC-ATL105-PDF-001:1095|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7288|REQ-SRC-ATL105-PDF-001:1131|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7290|REQ-SRC-ATL105-PDF-001:1133|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7292|REQ-SRC-ATL105-PDF-001:1135|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7298|REQ-SRC-ATL105-PDF-001:1141|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7305|REQ-SRC-ATL105-PDF-001:1148|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7310|REQ-SRC-ATL105-PDF-001:1153|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7321|REQ-SRC-ATL105-PDF-001:1167|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7327|REQ-SRC-ATL105-PDF-001:1174|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7333|REQ-SRC-ATL105-PDF-001:1180|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7342|REQ-SRC-ATL105-PDF-001:1190|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7347|REQ-SRC-ATL105-PDF-001:1196|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7385|REQ-SRC-ATL105-PDF-001:1235|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7398|REQ-SRC-ATL105-PDF-001:1248|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7403|REQ-SRC-ATL105-PDF-001:1253|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7427|REQ-SRC-ATL105-PDF-001:1277|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7433|REQ-SRC-ATL105-PDF-001:1283|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7439|REQ-SRC-ATL105-PDF-001:1290|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7442|REQ-SRC-ATL105-PDF-001:1293|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7447|REQ-SRC-ATL105-PDF-001:1298|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7505|REQ-SRC-ATL105-PDF-001:1356|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7540|REQ-SRC-ATL105-PDF-001:1391|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7548|REQ-SRC-ATL105-PDF-001:1399|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7551|REQ-SRC-ATL105-PDF-001:1402|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7556|REQ-SRC-ATL105-PDF-001:1407|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7568|REQ-SRC-ATL105-PDF-001:1419|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7616|REQ-SRC-ATL105-PDF-001:1467|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7804|REQ-SRC-ATL105-PDF-001:1658|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7842|REQ-SRC-ATL105-PDF-001:1696|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7843|REQ-SRC-ATL105-PDF-001:1697|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7917|REQ-SRC-ATL105-PDF-001:1771|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7918|REQ-SRC-ATL105-PDF-001:1772|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7927|REQ-SRC-ATL105-PDF-001:1781|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7945|REQ-SRC-ATL105-PDF-001:1799|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-3041|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-3090|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-3091|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-3242|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-3339|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-3340|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-4225|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-4382|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-4383|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-4477|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-4490|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-4491|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-4722|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-4749|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-4750|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6039|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6088|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6254|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6261|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6295|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7080|REQ-SRC-ATL105-PDF-001:923|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7087|REQ-SRC-ATL105-PDF-001:930|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7093|REQ-SRC-ATL105-PDF-001:936|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (121)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-031303|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031304|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031306|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031308|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031309|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031310|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031462|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031476|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031477|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031578|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031579|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031580|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031581|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031582|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031583|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031590|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031591|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031592|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031593|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031594|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031595|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031622|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031623|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031624|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031625|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031631|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031633|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031636|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031638|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031639|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031699|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031700|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031701|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031702|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031703|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031704|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031851|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031852|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031854|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031855|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031897|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031898|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031899|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031900|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031985|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031986|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031987|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032188|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032289|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032415|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032416|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032418|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032419|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032470|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032471|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032473|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032475|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032476|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032477|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032513|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032530|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032877|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032878|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032879|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032880|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032886|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032888|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032891|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032893|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032894|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033020|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033021|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033023|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033025|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033026|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033027|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033040|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033075|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033076|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033078|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033079|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033271|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033272|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033273|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033274|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033275|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034097|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034098|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034099|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034223|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034224|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034225|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034226|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034227|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034228|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034229|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034230|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034231|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034232|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034233|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034234|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034610|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034611|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034613|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034614|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034774|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034775|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034777|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034778|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-025819|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-025868|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-025869|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-026020|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-026117|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-026118|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-027003|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-027160|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-027161|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-028817|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-028866|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029032|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (109)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-031303|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031304|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031306|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031308|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031309|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031310|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031462|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031476|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031477|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031578|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031579|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031580|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031581|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031582|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031583|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031590|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031591|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031592|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031593|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031594|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031595|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031622|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031623|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031624|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031625|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031631|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031633|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031636|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031638|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031639|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031699|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031700|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031701|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031702|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031703|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031704|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031851|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031852|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031854|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031855|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031897|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031898|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031899|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031900|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031985|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031986|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031987|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032188|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032289|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032415|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032416|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032418|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032419|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032470|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032471|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032473|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032475|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032476|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032477|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032513|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032530|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032877|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032878|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032879|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032880|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032886|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032888|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032891|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032893|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032894|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033020|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033021|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033023|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033025|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033026|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033027|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033040|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033075|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033076|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033078|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033079|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033271|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033272|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033273|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033274|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033275|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034097|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034098|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034099|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034223|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034224|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034225|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034226|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034227|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034228|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034229|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034230|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034231|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034232|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034233|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034234|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034610|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034611|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034613|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034614|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034774|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034775|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034777|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034778|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

## SEG104-R-005 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |38|0|
| TS |38|0|
| TC |121|0|
| TD |117|0|

### BR side-by-side (38)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:1090|Moneris Data (Request) Segment required only for transactions destined for the Moneris authorizer.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1096|All EMV Financial Transaction Requests contain one or more of the listed data segments in Field Nos. 4-8.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1115|For EMV, the first two characters (Device Type) of Terminal Identifier must be '+*' regardless of actual device type.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1128|RQST/CURRENT HASH MATCH code indicates key update is not required.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1134|Segment Type field has fixed value 100 for Standard Message Data Segment.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1212|Value 'Y' in Alternate MICR IND indicates Alternate MICR format is being sent from the terminal.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1282|Fuel Volume Data field length is determined by segment length in field 2, up to max 3,600.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1291|Segment Type fixed value is 119.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1460|Account Updater Expiration Date is applicable only to Visa and MasterCard.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1507|Connector type field has maximum length of 3 characters.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1745|For fuel and nonfuel amounts together with discounts/coupons, discounts/coupons are first applied to the nonfuel amount.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1746|Nonfuel Amount valid values are 1-99999999.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1747|Number of Card Types valid values are 01-99.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1748|Number of Print Lines does not include Data Element 168, Count of Receipt Text Line.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1749|Data Element 168 count does not reflect total from Number of Print Lines.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1750|Number of Print Lines information comes from BUYPASS.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1751|Number of Print Lines valid values are 1, 2, or 3.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1752|Number of Products must precede single digits (1-9) with a zero (01-09).|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1753|Number of Products valid values range from 01 to 10.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1754|Financial Transaction Requests: Number of Segments followed by Data Segment 100, plus optional input segments from Chapter 12.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1755|Loyalty Card Transaction Requests: Number of Segments followed by Segment 100, Segment 108, and Segment 114.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1756|ECA/TeleCheck Service Transaction Requests: Number of Segments followed by Segment 100, Segment 110, and Segment 111.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1757|Totals Requests: Number of Segments always followed by Segment 105, Totals Data Segment.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1758|Electronic Mail Requests: Number of Segments always followed by Segment 109, Electronic Mail Data Segment.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1759|Communications Test Requests: Number of Segments always followed by Element 120, Network Management Message.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1760|TransArmor PKI Encryption and Tokenization: Used for Key and Key ID Load, followed by Segment 116, TransArmor Load Data Segment.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1761|Number of Segments value N indicates transaction has Segment 100 plus N-1 input segments listed in Chapter 12.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1762|Odometer element found in Fleet Data Segment if required by issuer.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1763|Odometer valid values range from 1 to 99999999.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1764|Password required for totals request or electronic mail request.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1765|If password entry is less than six characters, right-align entry using spaces.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1766|Default password is 123456.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1767|PC Duty Amount is used for Purchase Card Transaction Request.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1768|PC Freight Amount is used in Direct Marketing/Address Verification System Request.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1769|PC Tax Amount differs from Element No. 99, Tax Amount.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:1770|Transaction dialing always begins with the primary Phone Number.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:2183|Safekey Data positions 1-2 must be fixed value 'SK' indicating SafeKey cryptogram transaction.|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|
|REQ-SRC-ATL105-PDF-001:2185|Safekey Data appears in Data Segment No. 123 (NFC Payment Tokenization Data Segment).|UNMATCHED|SEG104-R-005||Test Solution rule catalog has no business-requirement artifact for SEG104-R-005|

### TS side-by-side (38)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7905|REQ-SRC-ATL105-PDF-001:1759|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7432|REQ-SRC-ATL105-PDF-001:1282|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7440|REQ-SRC-ATL105-PDF-001:1291|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7247|REQ-SRC-ATL105-PDF-001:1090|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7253|REQ-SRC-ATL105-PDF-001:1096|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7272|REQ-SRC-ATL105-PDF-001:1115|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7285|REQ-SRC-ATL105-PDF-001:1128|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7291|REQ-SRC-ATL105-PDF-001:1134|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7363|REQ-SRC-ATL105-PDF-001:1212|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7609|REQ-SRC-ATL105-PDF-001:1460|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7656|REQ-SRC-ATL105-PDF-001:1507|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7891|REQ-SRC-ATL105-PDF-001:1745|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7892|REQ-SRC-ATL105-PDF-001:1746|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7893|REQ-SRC-ATL105-PDF-001:1747|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7894|REQ-SRC-ATL105-PDF-001:1748|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7895|REQ-SRC-ATL105-PDF-001:1749|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7896|REQ-SRC-ATL105-PDF-001:1750|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7897|REQ-SRC-ATL105-PDF-001:1751|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7898|REQ-SRC-ATL105-PDF-001:1752|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7899|REQ-SRC-ATL105-PDF-001:1753|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7900|REQ-SRC-ATL105-PDF-001:1754|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7901|REQ-SRC-ATL105-PDF-001:1755|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7902|REQ-SRC-ATL105-PDF-001:1756|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7903|REQ-SRC-ATL105-PDF-001:1757|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7904|REQ-SRC-ATL105-PDF-001:1758|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7906|REQ-SRC-ATL105-PDF-001:1760|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7907|REQ-SRC-ATL105-PDF-001:1761|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7908|REQ-SRC-ATL105-PDF-001:1762|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7909|REQ-SRC-ATL105-PDF-001:1763|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7910|REQ-SRC-ATL105-PDF-001:1764|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7911|REQ-SRC-ATL105-PDF-001:1765|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7912|REQ-SRC-ATL105-PDF-001:1766|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7913|REQ-SRC-ATL105-PDF-001:1767|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7914|REQ-SRC-ATL105-PDF-001:1768|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7915|REQ-SRC-ATL105-PDF-001:1769|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7916|REQ-SRC-ATL105-PDF-001:1770|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8329|REQ-SRC-ATL105-PDF-001:2183|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8331|REQ-SRC-ATL105-PDF-001:2185|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (121)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-032482|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032483|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032484|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032485|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032491|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032493|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032496|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032498|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032499|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032432|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032433|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032434|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032435|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032441|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032443|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032446|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032448|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032449|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031463|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031464|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031479|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031480|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031530|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031531|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031532|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031534|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031536|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031537|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031538|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031596|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031597|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031598|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031599|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031605|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031607|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031610|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031612|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031613|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032045|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032046|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032047|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033240|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033241|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033242|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033243|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033245|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033246|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033247|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033292|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034444|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034445|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034446|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034447|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034448|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034449|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034450|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034451|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034452|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034453|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034454|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034455|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034458|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034459|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034461|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034462|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034463|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034464|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034466|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034467|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034468|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034469|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034470|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034471|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034472|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034473|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034474|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034475|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034476|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034478|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034479|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034480|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034484|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034485|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034486|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034487|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034488|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034490|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034491|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034498|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034499|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034500|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034501|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034502|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034503|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034504|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034505|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034506|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034507|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034510|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034517|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034524|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034529|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034530|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034531|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034532|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034533|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034534|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034535|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034536|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034537|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034538|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034539|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034540|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034541|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034542|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034543|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034544|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036361|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036362|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036365|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036366|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (117)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-032482|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032483|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032484|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032485|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032491|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032493|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032496|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032498|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032499|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032432|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032433|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032434|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032435|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032441|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032443|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032446|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032448|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032449|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031463|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031464|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031479|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031480|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031530|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031531|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031532|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031534|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031536|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031537|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031538|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031596|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031597|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031598|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031599|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031605|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031607|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031610|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031612|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031613|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032045|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032046|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032047|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033240|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033241|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033242|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033243|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033245|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033246|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033247|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033292|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034444|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034445|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034446|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034447|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034448|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034449|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034450|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034451|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034452|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034453|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034454|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034455|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034458|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034459|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034461|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034462|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034463|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034464|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034466|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034467|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034468|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034469|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034470|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034471|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034472|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034473|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034474|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034475|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034476|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034478|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034479|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034480|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034484|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034485|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034486|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034487|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034488|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034490|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034491|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034498|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034499|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034500|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034501|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034502|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034503|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034504|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034505|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034506|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034507|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034510|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034517|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034524|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034529|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034530|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034531|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034532|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034533|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034534|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034535|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034536|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034537|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034538|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034539|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034540|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034541|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034542|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034543|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034544|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

## SEG105-R-001 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |1|0|
| TS |1|0|
| TC |0|0|
| TD |0|0|

### BR side-by-side (1)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:987|Totals Request Data Section No. 3 contains the Totals Data Segment (Data Segment No. 105).|UNMATCHED|SEG105-R-001||Test Solution rule catalog has no business-requirement artifact for SEG105-R-001|

### TS side-by-side (1)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7144|REQ-SRC-ATL105-PDF-001:987|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (0)

_No AI or Test Solution artifacts at this level._

### TD side-by-side (0)

_No AI or Test Solution artifacts at this level._

## SEG105-R-002 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |4|0|
| TS |4|0|
| TC |0|0|
| TD |0|0|

### BR side-by-side (4)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:1181|a field is not populated — still send the Field Separator.|UNMATCHED|SEG105-R-002||Test Solution rule catalog has no business-requirement artifact for SEG105-R-002|
|REQ-SRC-ATL105-PDF-001:1183|Field Nos. 1-14 are separated by Field Separators, including one after Field No. 14.|UNMATCHED|SEG105-R-002||Test Solution rule catalog has no business-requirement artifact for SEG105-R-002|
|REQ-SRC-ATL105-PDF-001:1184|When a field is not populated, still send the Field Separator.|UNMATCHED|SEG105-R-002||Test Solution rule catalog has no business-requirement artifact for SEG105-R-002|
|REQ-SRC-ATL105-PDF-001:1185|Field Nos. 15-17 are not separated by Field Separators.|UNMATCHED|SEG105-R-002||Test Solution rule catalog has no business-requirement artifact for SEG105-R-002|

### TS side-by-side (4)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7334|REQ-SRC-ATL105-PDF-001:1181|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7335|REQ-SRC-ATL105-PDF-001:1183|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7336|REQ-SRC-ATL105-PDF-001:1184|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7337|REQ-SRC-ATL105-PDF-001:1185|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (0)

_No AI or Test Solution artifacts at this level._

### TD side-by-side (0)

_No AI or Test Solution artifacts at this level._

## SEG105-R-008 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |1|0|
| TS |1|0|
| TC |0|0|
| TD |0|0|

### BR side-by-side (1)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:413|At completion of business day, terminal sends Totals Request (222222 - Read and Cut Settlement) after last batch.|UNMATCHED|SEG105-R-008||Test Solution rule catalog has no business-requirement artifact for SEG105-R-008|

### TS side-by-side (1)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-6591|REQ-SRC-ATL105-PDF-001:413|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (0)

_No AI or Test Solution artifacts at this level._

### TD side-by-side (0)

_No AI or Test Solution artifacts at this level._

## SEG108-R-001 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |6|1|
| TS |6|0|
| TC |24|0|
| TD |15|0|

### BR side-by-side (6)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:804|Loyalty card information can be included in Purchase/Capture, Purchase Reversal, and Time-out Reversal transactions.|CONFIRMED|BR-SEG108-CORE-001|Segment 108 is exclusive to the Loyalty Card Transaction Request.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:812|If loyalty card not available for Reversal of points redeemed, clerk enters street number and telephone number instead of account number.|CONFIRMED|BR-SEG108-CORE-001|Segment 108 is exclusive to the Loyalty Card Transaction Request.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:971|Loyalty Card Transaction Request Data Section 3 may contain none, one, or more of Loyalty Card Data Segment or SKU Data Segment.|CONFIRMED|BR-SEG108-CORE-001|Segment 108 is exclusive to the Loyalty Card Transaction Request.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1197|Payment Tender Type used to determine whether transaction is a multiple-card transaction involving a loyalty card.|CONFIRMED|BR-SEG108-CORE-001|Segment 108 is exclusive to the Loyalty Card Transaction Request.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1735|For Loyalty Card Request messages, Element 63 (Number of Segments) and Segment 108 (Loyalty Card Data Segment) always follow Element 55.|CONFIRMED|BR-SEG108-CORE-001|Segment 108 is exclusive to the Loyalty Card Transaction Request.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1755|Loyalty Card Transaction Requests: Number of Segments followed by Segment 100, Segment 108, and Segment 114.|CONFIRMED|BR-SEG108-CORE-001|Segment 108 is exclusive to the Loyalty Card Transaction Request.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|

### TS side-by-side (6)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7901|REQ-SRC-ATL105-PDF-001:1755|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7348|REQ-SRC-ATL105-PDF-001:1197|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7881|REQ-SRC-ATL105-PDF-001:1735|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6966|REQ-SRC-ATL105-PDF-001:804|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6974|REQ-SRC-ATL105-PDF-001:812|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7128|REQ-SRC-ATL105-PDF-001:971|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (24)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-034474|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034475|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034476|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034478|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034479|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034480|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031992|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031993|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031994|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034400|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034401|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034402|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030557|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030558|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030559|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030633|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030634|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030635|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030637|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030638|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030639|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031156|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031157|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031158|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (15)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-034474|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034475|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034476|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034478|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034479|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034480|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031992|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031993|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031994|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034400|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034401|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034402|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031156|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031157|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031158|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

## SEG108-R-005 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |4|1|
| TS |4|1|
| TC |24|1|
| TD |24|0|

### BR side-by-side (4)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:1165|A Field Separator always follows Segment Type and Segment Length; Field Separator follows Product Amount when it's the last element.|CONFIRMED|BR-SEG108-CORE-005|Segment 108 maximum length is 142 alphanumeric characters.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1335|Field Nos 1&2 and 2&3 are separated by Field Separators; segment should end with a field separator.|CONFIRMED|BR-SEG108-CORE-005|Segment 108 maximum length is 142 alphanumeric characters.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1830|Loyalty Card Data Segment length is 001-142.|CONFIRMED|BR-SEG108-CORE-005|Segment 108 maximum length is 142 alphanumeric characters.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2319|AVS Information Table Data has max length 29, first 9 characters are ZIP or ZIP+4.|CONFIRMED|BR-SEG108-CORE-005|Segment 108 maximum length is 142 alphanumeric characters.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|

### TS side-by-side (4)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7319|REQ-SRC-ATL105-PDF-001:1165|MATCHED|SCN-SEG108-CORE-002|BR-SEG108-CORE-005|Shares canonical anchor `ATL105\|2026-3\|12.7\|108\|84\|segment-length-max-142`|
|SC-7484|REQ-SRC-ATL105-PDF-001:1335|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7976|REQ-SRC-ATL105-PDF-001:1830|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8461|REQ-SRC-ATL105-PDF-001:2319|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (24)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-031800|verification_status=PASS|MATCHED|TC-SEG108-CORE-003|FAIL|Shares canonical anchor `ATL105\|2026-3\|12.7\|108\|84\|segment-length-max-142`|
|TC-031801|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031802|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031803|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031809|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031811|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031814|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031816|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031817|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032718|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032719|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032720|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032721|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032727|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032729|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032732|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032734|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032735|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034912|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034913|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034914|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036911|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036912|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036913|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (24)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-031800|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031801|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031802|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031803|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031809|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031811|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031814|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031816|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031817|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032718|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032719|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032720|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032721|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032727|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032729|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032732|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032734|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032735|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034912|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034913|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034914|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-036911|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-036912|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-036913|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

## SEG108-R-008 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |2|1|
| TS |2|1|
| TC |6|1|
| TD |6|0|

### BR side-by-side (2)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:2051|Loyalty Program ID is required on all loyalty card transactions.|CONFIRMED|BR-SEG108-FIELD-008|Loyalty Program ID is required, numeric max 6.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2731|Loyalty Information Unit of Work maximum length is 19 numeric.|CONFIRMED|BR-SEG108-FIELD-008|Loyalty Program ID is required, numeric max 6.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|

### TS side-by-side (2)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-8197|REQ-SRC-ATL105-PDF-001:2051|MATCHED|SCN-SEG108-FIELD-001|BR-SEG108-FIELD-008|Shares canonical anchor `ATL105\|2026-3\|12.7\|108\|138\|loyalty-program-id`|
|SC-8871|REQ-SRC-ATL105-PDF-001:2731|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (6)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-035941|verification_status=PASS|MATCHED|TC-SEG108-FIELD-001|FAIL|Shares canonical anchor `ATL105\|2026-3\|12.7\|108\|138\|loyalty-program-id`|
|TC-035942|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035943|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037212|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037213|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037214|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (6)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-035941|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035942|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035943|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037212|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037213|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037214|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

## SEG108-R-009 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |12|1|
| TS |12|0|
| TC |64|0|
| TD |36|0|

### BR side-by-side (12)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:521|Receipts must include account number, expiration date, transaction date/time, sequence number, product, balance, approved/declined message, signature line.|CONFIRMED|BR-SEG108-FIELD-009|Loyalty Account Number is numeric max 24 when populated.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:546|Host message should contain Account Number, followed by '=' delimiter, followed by 4-digit Expiration Date (YYMM).|CONFIRMED|BR-SEG108-FIELD-009|Loyalty Account Number is numeric max 24 when populated.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:802|When no expiration date is present, device sends default value of 1249 (MMYY).|CONFIRMED|BR-SEG108-FIELD-009|Loyalty Account Number is numeric max 24 when populated.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:803|When loyalty card is not present, Street Address and Loyalty Phone Number are keyed in lieu of Loyalty Account Number.|CONFIRMED|BR-SEG108-FIELD-009|Loyalty Account Number is numeric max 24 when populated.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:805|If loyalty card not available for Coupon redemption, clerk enters street number and telephone number instead of Loyalty Account Number.|CONFIRMED|BR-SEG108-FIELD-009|Loyalty Account Number is numeric max 24 when populated.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:815|If loyalty card is not available, clerk enters street number and telephone number instead of Loyalty Account Number.|CONFIRMED|BR-SEG108-FIELD-009|Loyalty Account Number is numeric max 24 when populated.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:816|Account update transaction requires clerk to enter both new street number and new telephone number.|CONFIRMED|BR-SEG108-FIELD-009|Loyalty Account Number is numeric max 24 when populated.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:921|If the account number is keyed, device prompts for Expiration Date (MMYY) and other optional/conditional data.|CONFIRMED|BR-SEG108-FIELD-009|Loyalty Account Number is numeric max 24 when populated.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1193|Loyalty Account Number is conditional (C) rather than always required.|CONFIRMED|BR-SEG108-FIELD-009|Loyalty Account Number is numeric max 24 when populated.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1613|For Loyalty Totals Request with Update Code = 'T', Account Number field must contain 'TOTALS REQUEST'.|CONFIRMED|BR-SEG108-FIELD-009|Loyalty Account Number is numeric max 24 when populated.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1648|Merchant should send Expiration Date corresponding to the PAN or TransArmor Token sent in Account Number (Data Element 2, Segment 100).|CONFIRMED|BR-SEG108-FIELD-009|Loyalty Account Number is numeric max 24 when populated.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:2052|Loyalty Account Number is required on all loyalty card transactions.|CONFIRMED|BR-SEG108-FIELD-009|Loyalty Account Number is numeric max 24 when populated.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|

### TS side-by-side (12)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7344|REQ-SRC-ATL105-PDF-001:1193|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7759|REQ-SRC-ATL105-PDF-001:1613|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7794|REQ-SRC-ATL105-PDF-001:1648|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8198|REQ-SRC-ATL105-PDF-001:2052|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6697|REQ-SRC-ATL105-PDF-001:521|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6720|REQ-SRC-ATL105-PDF-001:546|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6964|REQ-SRC-ATL105-PDF-001:802|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6965|REQ-SRC-ATL105-PDF-001:803|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6967|REQ-SRC-ATL105-PDF-001:805|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6977|REQ-SRC-ATL105-PDF-001:815|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6978|REQ-SRC-ATL105-PDF-001:816|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7078|REQ-SRC-ATL105-PDF-001:921|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (64)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-031960|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031961|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031962|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031964|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031965|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031966|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033862|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033863|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033864|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034034|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034035|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034036|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034038|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034039|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034040|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035947|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035948|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035949|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035951|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035952|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035953|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029822|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029823|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029824|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029826|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029828|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029829|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029830|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029927|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029928|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029929|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029931|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029932|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029933|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030536|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030537|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030538|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030543|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030544|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030545|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030547|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030548|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030549|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030563|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030564|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030565|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030567|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030568|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030569|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030653|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030654|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030655|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030657|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030658|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030659|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030664|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030665|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030666|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031048|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031049|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031050|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031052|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031053|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031054|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (36)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-031960|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031961|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031962|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031964|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031965|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031966|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033862|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033863|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033864|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034034|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034035|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034036|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034038|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034039|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034040|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035947|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035948|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035949|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035951|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035952|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035953|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-030653|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-030654|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-030655|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-030657|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-030658|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-030659|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-030664|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-030665|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-030666|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031048|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031049|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031050|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031052|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031053|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031054|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

## SEG108-R-016 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |46|0|
| TS |43|0|
| TC |168|0|
| TD |87|0|

### BR side-by-side (46)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:460|If card cannot be read magnetically after three swipe attempts, device should prompt for manual entry of Account Number and Expiration Date (Format: MMYY).|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:522|The Expiration Date must be suppressed on all transaction receipts.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:526|Expiration Date must be suppressed on all transaction receipts.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:536|These two items must be separated by an "=" sign, rather than a Field Separator.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:537|Device at point of interaction must not display/store card-read data except account number, expiration date, cardholder name.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:544|Host message should only contain Account Number and Expiration Date (YYMM) in Card Discretionary Block Data fields.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:547|Card Discretionary Data field follows the 4-digit Expiration Date only for specific credit transactions.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:550|Device does not perform expiration date validation on debit card; online authorization always required.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:600|These two items must be separated by an "=" sign, rather than a Field Separator.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:601|Device must not display/store card-read data except account number, expiration date, and cardholder name.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:608|Host message should only contain Account Number and Expiration Date (YYMM) in Card Discretionary Block Data field.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:610|No other Card Discretionary Data field follows the 4-digit Expiration Date for Debit Completions, Reversals, and Cancellations.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:651|The device does not perform expiration date validation in an EBT card transaction.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:654|If no expiration date is provided for manually entered EBT account number, use 1249.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:661|Stand-alone equipment electronic voucher transaction must allow manual entry of Account Number and Expiration Date.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:719|an activation (Please see section 10.7.3.6 — "Activation Transaction.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:720|Device does not perform expiration date validation in a stored value card transaction.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:722|If manual entry allowed and stripe unreadable after 3 tries, prompt manual entry of Account Number and Expiration Date (MMYY).|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:723|First Data Premium Gift Card has no embossed expiration date; default value 1249 (MMYY) sent by POS device.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:727|Bar Code Scanned Gift card treated as manual entry transaction, sent with Account Number and Expiration Date, entry mode '01'.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:799|Device does not perform expiration date validation on loyalty card transactions.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:800|If card cannot be read after three swipe attempts, device prompts for manual entry of Account Number and Expiration Date.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:801|User may skip Expiration Date prompt when manually keyed and no expiration date present on card.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:1190|a field is not populated — still send the Field Separator.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:1196|Expiration Date is reserved for future use.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:1448|Table ID for Network Token Expiration Date layout is fixed value 002 with Table Length fixed at 004.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:1459|Account Updater Expiration Date only included when merchant sent Account Updater Request Indicator = Y.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:1460|Account Updater Expiration Date is applicable only to Visa and MasterCard.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:1646|For either TransArmor processing method, the expiration date must be included in Card Discretionary Block Data.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:1647|When merchant receives Account Updater Expiration Date in Segment 155, follow-on transactions may send new or original expiration date in this field.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:1650|For manually entered card number, expiration date must be in MMYY format.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:1651|Merchants must send an expiration date value even for expired cards, for all authorizations.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:1654|For either TransArmor processing method, expiration date must be included and stored for follow-on transactions with a Token.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:1655|For Comdata Express Code or Check request (card type 095/096), expiration date field should be skipped but separators included.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:1656|For Money Code/Check request (card type 093), expiration date field should be skipped but separators included.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:1657|Expiration date is mandatory for Accel/STAR e-commerce and one-time bill payment transactions.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:1658|Effective May 1, 2025, expiration date is optional for Accel/STAR COF transactions (bill payment, recurring, installment).|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:1997|When Additional Information Indicator = 013, Expiration Date (MMYY) is included, fixed length 4 bytes.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:2061|Earliest WIC Benefit Expiration Date is included in eWIC Balance Inquiry, Authorization, and Purchase Completion Responses.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:2068|Earliest WIC Benefit Expiration Date format is CCYYMMDD, 8 bytes.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:2732|If no value has been stored at the device, default value of 1249 is sent for Card Expiration Date.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:2733|Depending on transaction context, Card Expiration Date value can be either MMYY or YYMM format.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:2846|Payment Token Expiration Date is populated in the PAN Expiration Date field during transaction processing.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:2992|On all subsequent transactions, unencrypted expiration date should be sent in Data Element 12, Card Discretionary Block Data.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:2997|Encrypted data for a manually entered PAN should be followed by <fs> and unencrypted MMYY expiration date.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|
|REQ-SRC-ATL105-PDF-001:3003|For TransArmor processing, expiration date must be stored for subsequent transactions with a token.|UNMATCHED|SEG108-R-016||Test Solution rule catalog has no business-requirement artifact for SEG108-R-016|

### TS side-by-side (43)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7342|REQ-SRC-ATL105-PDF-001:1190|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7347|REQ-SRC-ATL105-PDF-001:1196|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7804|REQ-SRC-ATL105-PDF-001:1658|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7609|REQ-SRC-ATL105-PDF-001:1460|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8214|REQ-SRC-ATL105-PDF-001:2068|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7597|REQ-SRC-ATL105-PDF-001:1448|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7608|REQ-SRC-ATL105-PDF-001:1459|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7792|REQ-SRC-ATL105-PDF-001:1646|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7793|REQ-SRC-ATL105-PDF-001:1647|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7796|REQ-SRC-ATL105-PDF-001:1650|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7797|REQ-SRC-ATL105-PDF-001:1651|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7800|REQ-SRC-ATL105-PDF-001:1654|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7801|REQ-SRC-ATL105-PDF-001:1655|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7802|REQ-SRC-ATL105-PDF-001:1656|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7803|REQ-SRC-ATL105-PDF-001:1657|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8143|REQ-SRC-ATL105-PDF-001:1997|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8207|REQ-SRC-ATL105-PDF-001:2061|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8872|REQ-SRC-ATL105-PDF-001:2732|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8873|REQ-SRC-ATL105-PDF-001:2733|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8986|REQ-SRC-ATL105-PDF-001:2846|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-9130|REQ-SRC-ATL105-PDF-001:2992|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-9135|REQ-SRC-ATL105-PDF-001:2997|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-9141|REQ-SRC-ATL105-PDF-001:3003|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6636|REQ-SRC-ATL105-PDF-001:460|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6698|REQ-SRC-ATL105-PDF-001:522|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6701|REQ-SRC-ATL105-PDF-001:526|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6711|REQ-SRC-ATL105-PDF-001:536|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6718|REQ-SRC-ATL105-PDF-001:544|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6721|REQ-SRC-ATL105-PDF-001:547|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6724|REQ-SRC-ATL105-PDF-001:550|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6773|REQ-SRC-ATL105-PDF-001:600|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6780|REQ-SRC-ATL105-PDF-001:608|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6782|REQ-SRC-ATL105-PDF-001:610|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6822|REQ-SRC-ATL105-PDF-001:651|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6825|REQ-SRC-ATL105-PDF-001:654|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6832|REQ-SRC-ATL105-PDF-001:661|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6887|REQ-SRC-ATL105-PDF-001:719|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6889|REQ-SRC-ATL105-PDF-001:722|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6890|REQ-SRC-ATL105-PDF-001:723|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6894|REQ-SRC-ATL105-PDF-001:727|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6961|REQ-SRC-ATL105-PDF-001:799|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6962|REQ-SRC-ATL105-PDF-001:800|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6963|REQ-SRC-ATL105-PDF-001:801|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (168)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-031985|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031986|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031987|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034097|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034098|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034099|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033240|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033241|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033242|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033243|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033245|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033246|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033247|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036020|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036021|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036022|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033202|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033203|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033204|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033229|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033230|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033231|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033232|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033234|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033235|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033236|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034013|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034014|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034015|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034017|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034018|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034019|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034023|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034024|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034025|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034027|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034029|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034030|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034045|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034046|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034047|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034052|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034053|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034054|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034069|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034070|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034071|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034076|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034077|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034078|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034083|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034084|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034085|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034090|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034091|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034092|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035772|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035774|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035775|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035993|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035995|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035996|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-035997|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037219|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037220|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037221|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037226|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037227|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037228|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037327|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037328|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037329|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037641|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037642|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037643|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037645|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037646|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037647|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037664|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037665|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037666|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037690|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037691|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037692|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037694|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037695|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037696|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029595|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029596|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029597|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029599|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029600|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029601|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029839|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029840|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029841|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029858|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029859|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029860|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029907|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029908|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029909|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029911|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029912|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029913|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029937|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029938|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029939|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029941|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029942|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029943|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029948|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029949|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029950|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030142|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030143|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030144|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030146|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030147|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030148|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030162|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030163|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030164|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030166|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030167|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030168|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030206|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030207|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030208|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030218|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030219|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030220|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030222|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030223|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030224|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030242|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030243|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030244|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030246|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030247|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030248|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030328|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030329|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030330|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030332|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030333|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030334|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030339|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030340|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030341|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030351|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030352|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030353|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030355|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030356|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030357|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030512|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030513|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030514|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030518|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030519|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030520|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030522|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030523|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030524|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030529|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030530|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-030531|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (87)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-031985|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031986|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031987|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034097|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034098|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034099|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033240|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033241|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033242|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033243|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033245|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033246|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033247|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-036020|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-036021|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-036022|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033202|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033203|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033204|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033229|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033230|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033231|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033232|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033234|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033235|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033236|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034013|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034014|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034015|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034017|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034018|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034019|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034023|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034024|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034025|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034027|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034029|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034030|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034045|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034046|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034047|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034052|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034053|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034054|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034069|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034070|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034071|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034076|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034077|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034078|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034083|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034084|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034085|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034090|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034091|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034092|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035772|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035774|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035775|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035993|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035995|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035996|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-035997|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037219|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037220|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037221|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037226|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037227|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037228|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037327|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037328|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037329|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037641|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037642|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037643|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037645|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037646|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037647|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037664|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037665|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037666|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037690|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037691|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037692|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037694|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037695|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037696|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

## SEG111-R-001 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |49|0|
| TS |48|0|
| TC |91|0|
| TD |88|0|

### BR side-by-side (49)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:631|STAR Signature Debit transactions must occur within US and US territories.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:931|POS Devices processing chip-initiated credit/signature debit transactions must support submitting a PIN in authorization request.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1072|Key Data section repeats up to 3 times (max 48 bytes total); presence based on Key Indicators field.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1089|EMV Request Data Segment is required for all EMV card transactions and is the only segment required for all EMV financial transactions.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1095|Incomm Market Basket Data (Response) Segment returned when Request segment was included in the request.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1131|Standard Message Data Segment is the only required data segment on all Financial Transaction Request messages.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1135|Segment Length includes Segment Type's length and Field Separators.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1141|For DUKPT PIN encryption, Encrypted PIN Block Data is a 16- to 20-byte KSN plus a 16-byte PIN block.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1148|Each Fleet Tag has a 3-byte fixed-length tag portion and up to 31 bytes of data.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1153|a field is not populated — still send the Product Data Field Delimiter.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1167|When a field is not populated, still send the Field Separator or Field Delimiter.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1174|A Field Separator follows Product Amount if it is the last element in the segment.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1180|All fields are separated by Field Separators; unpopulated fields still send Field Separator.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1190|a field is not populated — still send the Field Separator.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1196|Expiration Date is reserved for future use.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1235|Print Data Segment always appears at the end of a Financial Transaction response.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1248|Prompt Code, Pending valid values are 0901, 0902, 0904, 0981.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1253|Device Card Table Version consists of seven version values, each 5 characters, strung together.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1277|Product Code 991 indicates an administrative amount, used in a pre-pay environment when price is adjusted to include discount amount.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1283|Fields are not separated by Field Separators; unpopulated fields are immediately followed by the next field.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1285|Segment 119 always appears in Field No. 3 in Data Section No. 3.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1290|Prompt Code fixed value is 990 identifying format type for requested totals.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1293|Field Nos. 17-19 are sent up to a maximum of 20 times, once for each card type, in order shown.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1298|Print Data 2 Segment is only sent on Financial Transaction Response messages requiring large amounts of print data.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1356|A Field Separator always follows Segment Type and Segment Length, and the last Tax Amount per product.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1391|Prompt Tokens differ by authorizer; different authorizers support different prompts.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1399|Prompt Code 999 indicates no prompts issued.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1402|Table Length for Payee Name has a maximum variable value of 017.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1407|Flags in Table Data must be separated by a comma; if a flag is absent, it can be skipped using just the separator.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1419|Product Data repeated for as many products as needed, separated by '\|' and ending with '\|'.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1420|Prompt Data repeated for as many prompts as needed, separated by '\|' and ending with '\|'.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1467|Field is included in response only when merchant sent Account Updater Request Indicator with value 'Y' or 'I' in request.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1771|Secondary Phone Number is used once attempts using the primary Phone Number are exhausted.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:1772|A tertiary Phone Number is assigned to but not used by the device.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:2275|Card Type codes 127-168 turn on specific device features and are not used in any Prompt Codes.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:2308|Decline codes prompt clerk/customer to reenter only the specific data indicated in the prompt.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:2331|Table Data for Partial Approval Indicator valid values: 0=Not supported, 1=Supported, 5=Supports balance receipt but not partial approval.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:2366|Table ID for Originating Device Type Indicator is fixed value 022.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:2367|Table Data default value is 00 (Card) if not specified.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:2430|When Variable Information Indicator = 035 (Verified by Visa Data), merchant must send XID and CAVV in binary data format.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:2436|Merchant must send UCAF data with subfield ID value 43 and subfield length 28 within the 32 bytes of UCAF data.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:2442|Fraud Enhanced Data Table ID fixed value 039, length fixed at 8 bytes.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:2443|Final Amount Indicator allowed only on EMV initial transactions where final Settlement Amount may differ from Authorization Amount.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:2444|Final Amount Indicator should NOT be sent when final Settlement Amount equals original Authorization Amount.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:2453|Third Party Installment Payment Providers must include their name and underlying retailer name in format 'Third party name*underlying merchant name'.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:2454|Table length should be '1' when sending only Program Protocol, or '37' when sending both Program Protocol and Directory Server Transaction ID.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:2464|Sub-Table ID fixed value is 02 for entity initiating transaction sub-table.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:2606|This field must be spaces if not used by merchant.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|
|REQ-SRC-ATL105-PDF-001:2858|Security Condition values 8-9 are reserved for private use.|UNMATCHED|SEG111-R-001||Test Solution rule catalog has no business-requirement artifact for SEG111-R-001|

### TS side-by-side (48)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7229|REQ-SRC-ATL105-PDF-001:1072|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7246|REQ-SRC-ATL105-PDF-001:1089|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7252|REQ-SRC-ATL105-PDF-001:1095|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7288|REQ-SRC-ATL105-PDF-001:1131|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7292|REQ-SRC-ATL105-PDF-001:1135|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7298|REQ-SRC-ATL105-PDF-001:1141|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7305|REQ-SRC-ATL105-PDF-001:1148|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7310|REQ-SRC-ATL105-PDF-001:1153|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7321|REQ-SRC-ATL105-PDF-001:1167|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7327|REQ-SRC-ATL105-PDF-001:1174|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7333|REQ-SRC-ATL105-PDF-001:1180|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7342|REQ-SRC-ATL105-PDF-001:1190|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7347|REQ-SRC-ATL105-PDF-001:1196|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7385|REQ-SRC-ATL105-PDF-001:1235|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7398|REQ-SRC-ATL105-PDF-001:1248|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7403|REQ-SRC-ATL105-PDF-001:1253|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7427|REQ-SRC-ATL105-PDF-001:1277|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7433|REQ-SRC-ATL105-PDF-001:1283|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7439|REQ-SRC-ATL105-PDF-001:1290|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7442|REQ-SRC-ATL105-PDF-001:1293|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7447|REQ-SRC-ATL105-PDF-001:1298|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7505|REQ-SRC-ATL105-PDF-001:1356|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7540|REQ-SRC-ATL105-PDF-001:1391|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7548|REQ-SRC-ATL105-PDF-001:1399|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7551|REQ-SRC-ATL105-PDF-001:1402|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7556|REQ-SRC-ATL105-PDF-001:1407|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7568|REQ-SRC-ATL105-PDF-001:1419|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7616|REQ-SRC-ATL105-PDF-001:1467|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7917|REQ-SRC-ATL105-PDF-001:1771|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7918|REQ-SRC-ATL105-PDF-001:1772|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7569|REQ-SRC-ATL105-PDF-001:1420|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8420|REQ-SRC-ATL105-PDF-001:2275|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8450|REQ-SRC-ATL105-PDF-001:2308|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8473|REQ-SRC-ATL105-PDF-001:2331|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8508|REQ-SRC-ATL105-PDF-001:2366|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8509|REQ-SRC-ATL105-PDF-001:2367|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8572|REQ-SRC-ATL105-PDF-001:2430|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8578|REQ-SRC-ATL105-PDF-001:2436|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8584|REQ-SRC-ATL105-PDF-001:2442|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8585|REQ-SRC-ATL105-PDF-001:2443|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8586|REQ-SRC-ATL105-PDF-001:2444|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8595|REQ-SRC-ATL105-PDF-001:2453|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8596|REQ-SRC-ATL105-PDF-001:2454|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8606|REQ-SRC-ATL105-PDF-001:2464|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8748|REQ-SRC-ATL105-PDF-001:2606|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8998|REQ-SRC-ATL105-PDF-001:2858|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6802|REQ-SRC-ATL105-PDF-001:631|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7088|REQ-SRC-ATL105-PDF-001:931|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (91)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-031462|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031476|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031477|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031578|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031579|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031580|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031581|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031582|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031583|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031622|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031623|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031624|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031625|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031631|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031633|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031636|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031638|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031639|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031699|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031700|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031701|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031702|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031703|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031704|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031851|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031852|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031854|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031855|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031897|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031898|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031899|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031900|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031985|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031986|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031987|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032188|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032289|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032415|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032416|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032418|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032419|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032470|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032471|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032473|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032475|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032476|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032477|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032513|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032530|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032877|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032878|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032879|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032880|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032886|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032888|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032891|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032893|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032894|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033020|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033021|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033023|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033025|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033026|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033027|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033040|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033075|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033076|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033078|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033079|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033271|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033272|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033273|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033274|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033275|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036706|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036707|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036709|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036711|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036712|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036713|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036921|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036922|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036923|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036924|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036925|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036926|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037026|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037027|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037028|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037029|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-037030|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (88)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-031462|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031476|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031477|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031578|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031579|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031580|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031581|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031582|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031583|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031622|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031623|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031624|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031625|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031631|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031633|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031636|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031638|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031639|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031699|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031700|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031701|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031702|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031703|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031704|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031851|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031852|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031854|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031855|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031897|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031898|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031899|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031900|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031985|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031986|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031987|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032188|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032289|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032415|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032416|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032418|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032419|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032470|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032471|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032473|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032475|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032476|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032477|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032513|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032530|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032877|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032878|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032879|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032880|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032886|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032888|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032891|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032893|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032894|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033020|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033021|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033023|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033025|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033026|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033027|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033040|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033075|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033076|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033078|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033079|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033271|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033272|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033273|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033274|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033275|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-036711|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-036712|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-036713|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-036921|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-036922|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-036923|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-036924|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-036925|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-036926|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037026|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037027|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037028|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037029|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-037030|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

## SEG111-R-002 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |42|0|
| TS |61|0|
| TC |121|0|
| TD |105|0|

### BR side-by-side (42)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:254|Enhanced Fleet Data must be alphanumeric and must not exceed 999 bytes.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1090|Moneris Data (Request) Segment required only for transactions destined for the Moneris authorizer.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1096|All EMV Financial Transaction Requests contain one or more of the listed data segments in Field Nos. 4-8.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1115|For EMV, the first two characters (Device Type) of Terminal Identifier must be '+*' regardless of actual device type.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1128|RQST/CURRENT HASH MATCH code indicates key update is not required.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1282|Fuel Volume Data field length is determined by segment length in field 2, up to max 3,600.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1291|Segment Type fixed value is 119.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1460|Account Updater Expiration Date is applicable only to Visa and MasterCard.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1507|Connector type field has maximum length of 3 characters.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1745|For fuel and nonfuel amounts together with discounts/coupons, discounts/coupons are first applied to the nonfuel amount.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1746|Nonfuel Amount valid values are 1-99999999.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1747|Number of Card Types valid values are 01-99.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1748|Number of Print Lines does not include Data Element 168, Count of Receipt Text Line.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1749|Data Element 168 count does not reflect total from Number of Print Lines.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1750|Number of Print Lines information comes from BUYPASS.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1751|Number of Print Lines valid values are 1, 2, or 3.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1752|Number of Products must precede single digits (1-9) with a zero (01-09).|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1753|Number of Products valid values range from 01 to 10.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1754|Financial Transaction Requests: Number of Segments followed by Data Segment 100, plus optional input segments from Chapter 12.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1755|Loyalty Card Transaction Requests: Number of Segments followed by Segment 100, Segment 108, and Segment 114.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1756|ECA/TeleCheck Service Transaction Requests: Number of Segments followed by Segment 100, Segment 110, and Segment 111.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1757|Totals Requests: Number of Segments always followed by Segment 105, Totals Data Segment.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1758|Electronic Mail Requests: Number of Segments always followed by Segment 109, Electronic Mail Data Segment.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1759|Communications Test Requests: Number of Segments always followed by Element 120, Network Management Message.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1760|TransArmor PKI Encryption and Tokenization: Used for Key and Key ID Load, followed by Segment 116, TransArmor Load Data Segment.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1761|Number of Segments value N indicates transaction has Segment 100 plus N-1 input segments listed in Chapter 12.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1762|Odometer element found in Fleet Data Segment if required by issuer.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1763|Odometer valid values range from 1 to 99999999.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1764|Password required for totals request or electronic mail request.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1765|If password entry is less than six characters, right-align entry using spaces.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1766|Default password is 123456.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1767|PC Duty Amount is used for Purchase Card Transaction Request.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1768|PC Freight Amount is used in Direct Marketing/Address Verification System Request.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1769|PC Tax Amount differs from Element No. 99, Tax Amount.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:1770|Transaction dialing always begins with the primary Phone Number.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:2183|Safekey Data positions 1-2 must be fixed value 'SK' indicating SafeKey cryptogram transaction.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:2185|Safekey Data appears in Data Segment No. 123 (NFC Payment Tokenization Data Segment).|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:2427|For Visa Card Type, cardholder name data is variable length up to 105 digits including Middle Name after First and Last Name.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:2428|NVS Table ID is fixed value 034 with length 3 bytes.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:2435|Value 4 (Identity Check Insights) means no fraud liability shift since issuer does not receive authentication request.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:2440|MCX Checkout Token Table ID fixed value 037, length fixed value 040, data an40.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|
|REQ-SRC-ATL105-PDF-001:2441|Paydiant Tender ID Table ID fixed value 038, length fixed value 012, data an12.|UNMATCHED|SEG111-R-002||Test Solution rule catalog has no business-requirement artifact for SEG111-R-002|

### TS side-by-side (61)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-3041|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-3090|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-3091|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-3242|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-3339|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-3340|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-4225|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-4382|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-4383|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-4477|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-4490|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-4491|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-4722|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-4749|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-4750|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6039|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6088|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6254|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6261|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-6295|REQ-SRC-ATL105-PDF-001:254|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7905|REQ-SRC-ATL105-PDF-001:1759|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7432|REQ-SRC-ATL105-PDF-001:1282|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7440|REQ-SRC-ATL105-PDF-001:1291|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7247|REQ-SRC-ATL105-PDF-001:1090|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7253|REQ-SRC-ATL105-PDF-001:1096|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7272|REQ-SRC-ATL105-PDF-001:1115|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7285|REQ-SRC-ATL105-PDF-001:1128|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7609|REQ-SRC-ATL105-PDF-001:1460|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7656|REQ-SRC-ATL105-PDF-001:1507|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7891|REQ-SRC-ATL105-PDF-001:1745|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7892|REQ-SRC-ATL105-PDF-001:1746|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7893|REQ-SRC-ATL105-PDF-001:1747|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7894|REQ-SRC-ATL105-PDF-001:1748|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7895|REQ-SRC-ATL105-PDF-001:1749|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7896|REQ-SRC-ATL105-PDF-001:1750|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7897|REQ-SRC-ATL105-PDF-001:1751|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7898|REQ-SRC-ATL105-PDF-001:1752|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7899|REQ-SRC-ATL105-PDF-001:1753|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7900|REQ-SRC-ATL105-PDF-001:1754|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7901|REQ-SRC-ATL105-PDF-001:1755|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7902|REQ-SRC-ATL105-PDF-001:1756|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7903|REQ-SRC-ATL105-PDF-001:1757|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7904|REQ-SRC-ATL105-PDF-001:1758|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7906|REQ-SRC-ATL105-PDF-001:1760|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7907|REQ-SRC-ATL105-PDF-001:1761|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7908|REQ-SRC-ATL105-PDF-001:1762|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7909|REQ-SRC-ATL105-PDF-001:1763|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7910|REQ-SRC-ATL105-PDF-001:1764|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7911|REQ-SRC-ATL105-PDF-001:1765|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7912|REQ-SRC-ATL105-PDF-001:1766|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7913|REQ-SRC-ATL105-PDF-001:1767|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7914|REQ-SRC-ATL105-PDF-001:1768|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7915|REQ-SRC-ATL105-PDF-001:1769|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7916|REQ-SRC-ATL105-PDF-001:1770|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8329|REQ-SRC-ATL105-PDF-001:2183|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8331|REQ-SRC-ATL105-PDF-001:2185|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8569|REQ-SRC-ATL105-PDF-001:2427|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8570|REQ-SRC-ATL105-PDF-001:2428|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8577|REQ-SRC-ATL105-PDF-001:2435|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8582|REQ-SRC-ATL105-PDF-001:2440|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-8583|REQ-SRC-ATL105-PDF-001:2441|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (121)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-025819|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-025868|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-025869|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-026020|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-026117|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-026118|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-027003|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-027160|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-027161|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-028817|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-028866|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-029032|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032482|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032483|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032484|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032485|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032491|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032493|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032496|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032498|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032499|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032432|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032433|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032434|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032435|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032441|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032443|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032446|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032448|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032449|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031463|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031464|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031479|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031480|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031530|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031531|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031532|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031534|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031536|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031537|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031538|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033240|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033241|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033242|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033243|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033245|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033246|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033247|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-033292|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034444|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034445|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034446|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034447|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034448|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034449|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034450|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034451|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034452|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034453|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034454|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034455|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034458|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034459|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034461|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034462|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034463|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034464|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034466|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034467|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034468|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034469|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034470|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034471|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034472|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034473|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034474|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034475|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034476|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034478|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034479|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034480|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034484|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034485|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034486|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034487|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034488|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034490|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034491|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034498|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034499|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034500|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034501|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034502|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034503|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034504|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034505|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034506|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034507|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034510|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034517|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034524|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034529|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034530|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034531|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034532|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034533|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034534|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034535|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034536|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034537|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034538|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034539|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034540|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034541|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034542|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034543|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034544|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036361|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036362|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036365|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-036366|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (105)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-032482|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032483|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032484|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032485|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032491|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032493|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032496|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032498|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032499|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032432|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032433|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032434|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032435|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032441|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032443|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032446|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032448|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032449|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031463|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031464|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031479|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031480|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031530|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031531|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031532|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031534|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031536|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031537|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031538|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033240|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033241|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033242|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033243|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033245|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033246|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033247|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-033292|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034444|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034445|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034446|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034447|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034448|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034449|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034450|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034451|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034452|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034453|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034454|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034455|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034458|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034459|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034461|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034462|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034463|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034464|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034466|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034467|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034468|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034469|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034470|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034471|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034472|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034473|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034474|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034475|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034476|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034478|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034479|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034480|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034484|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034485|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034486|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034487|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034488|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034490|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034491|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034498|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034499|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034500|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034501|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034502|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034503|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034504|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034505|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034506|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034507|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034510|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034517|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034524|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034529|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034530|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034531|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034532|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034533|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034534|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034535|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034536|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034537|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034538|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034539|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034540|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034541|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034542|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034543|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034544|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

## SEG113-R-005 - CONFIRMED

**Reason:** Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.

| Level | AI count | Test count |
|---|---:|---:|
| BR |5|1|
| TS |5|1|
| TC |31|0|
| TD |31|0|

### BR side-by-side (5)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|REQ-SRC-ATL105-PDF-001:1134|Segment Type field has fixed value 100 for Standard Message Data Segment.|CONFIRMED|BR-SEG113-CORE-005|Segment 113 maximum length is 156 alphanumeric characters.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1165|A Field Separator always follows Segment Type and Segment Length; Field Separator follows Product Amount when it's the last element.|CONFIRMED|BR-SEG113-CORE-005|Segment 113 maximum length is 156 alphanumeric characters.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1226|ECA/TeleCheck Data Segment has a maximum length of 156 alphanumeric characters.|CONFIRMED|BR-SEG113-CORE-005|Segment 113 maximum length is 156 alphanumeric characters.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1335|Field Nos 1&2 and 2&3 are separated by Field Separators; segment should end with a field separator.|CONFIRMED|BR-SEG113-CORE-005|Segment 113 maximum length is 156 alphanumeric characters.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|
|REQ-SRC-ATL105-PDF-001:1835|ECA/TeleCheck Data Segment length is 001-156.|CONFIRMED|BR-SEG113-CORE-005|Segment 113 maximum length is 156 alphanumeric characters.|Migrated from prior Test Solution-reviewed decision using an exact canonical-anchor or unambiguous canonical rule-id mapping.|

### TS side-by-side (5)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|SC-7291|REQ-SRC-ATL105-PDF-001:1134|MATCHED|SCN-SEG113-CORE-002|BR-SEG113-CORE-005|Shares canonical anchor `ATL105\|2026-3\|12.12\|113\|84\|segment-length-max-156`|
|SC-7319|REQ-SRC-ATL105-PDF-001:1165|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7377|REQ-SRC-ATL105-PDF-001:1226|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7484|REQ-SRC-ATL105-PDF-001:1335|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|
|SC-7981|REQ-SRC-ATL105-PDF-001:1835|AI_ONLY|||No Test TS artifact shares a canonical anchor with this AI TS item|

### TC side-by-side (31)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TC-031596|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031597|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031598|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031599|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031605|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031607|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031610|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031612|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031613|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031800|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031801|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031802|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031803|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031809|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031811|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031814|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031816|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-031817|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032164|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032165|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032718|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032719|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032720|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032721|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032727|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032729|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032732|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032734|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-032735|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034929|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|
|TC-034930|verification_status=PASS|AI_ONLY|||No Test TC artifact shares a canonical anchor with this AI TC item|

### TD side-by-side (31)

| AI ID | AI Detail | Status | Test ID | Test Detail | Reason |
|---|---|---|---|---|---|
|TD-TC-031596|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031597|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031598|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031599|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031605|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031607|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031610|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031612|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031613|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031800|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031801|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031802|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031803|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031809|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031811|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031814|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031816|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-031817|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032164|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032165|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032718|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032719|11 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032720|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032721|33 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032727|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032729|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032732|6 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032734|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-032735|22 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034929|27 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|
|TD-TC-034930|39 keys|AI_ONLY|||No Test TD artifact shares a canonical anchor with this AI TD item|

