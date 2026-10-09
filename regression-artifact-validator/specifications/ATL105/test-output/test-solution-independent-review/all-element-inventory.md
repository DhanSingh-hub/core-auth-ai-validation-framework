# ATL105 Element Inventory

Source/BR reconciliation only. NOT full semantic validation or model training. Approved = 0 until human review.

| Element | Source name(s) | Constraint | Catalog refs | BR refs | Template refs | Code refs | Test refs | Profiles |
|---|---|---|---:|---:|---:|---:|---:|---:|
|1|Access Code; |AN VARIABLE max 12|2|7|0|2|2|0|
|2|Account Number; |REVIEW|5|66|4|0|0|0|
|3|Address Line 1; |AN FIXED max 24|1|3|0|1|0|0|
|4|Address Line 2; |AN FIXED max 21|2|7|0|2|1|0|
|5|Approval Number; |AN FIXED max 6|1|7|4|0|0|0|
|6|Approved Amount; |N FIXED max 9|0|4|2|0|0|0|
|7|Authorizer Code; |N FIXED max 2|0|4|2|0|0|0|
|8|Authorizer Response Code; |AN FIXED max 2|0|0|2|0|0|0|
|11|Block Number; |N FIXED max 3|5|19|6|4|2|0|
|12|Card Discretionary Block Data; |AN VARIABLE max 51|1|24|2|0|0|0|
|13|Card Label; |AN FIXED max 4|1|3|3|0|0|0|
|14|Card Type; |AN FIXED max 3|5|25|0|5|2|0|
|15|Card Type Total Amount; |N FIXED max 8|1|5|3|0|0|0|
|16|Card Type Total Count; |N FIXED max 5|1|5|3|0|0|0|
|17|Cash Amount; |N VARIABLE max 8|3|24|2|0|1|0|
|18|Clerk ID; |N VARIABLE max 10|1|9|0|1|1|0|
|20|Currency Code; |N FIXED max 3|2|169|2|1|0|0|
|21|Current Date; |N FIXED max 6|2|14|1|2|2|0|
|22|Current Time; |N FIXED max 4|2|13|1|2|2|0|
|23|Cut Time; |N FIXED max 4|3|19|1|3|3|0|
|24|Data Type Indicator; |AN FIXED max 1|8|41|3|8|3|0|
|25|Day of the Week; |N FIXED max 1|2|14|1|2|2|0|
|26|Decline Code; |AN FIXED max 2|1|12|2|1|0|0|
|27|Dial String Terminator; |AN FIXED max 1|2|8|0|2|2|0|
|28|Dial String Type; |N FIXED max 1|2|8|0|2|2|0|
|29|Direct Marketing Invoice Number; |AN VARIABLE max 10|1|3|0|1|1|0|
|30|Download Indicator; |N FIXED max 1|2|11|4|1|2|2|
|31|Driver/Identification Number; |N VARIABLE max 10|1|3|0|1|0|0|
|32|Employee Number; |N FIXED max 4|3|12|3|2|0|1|
|33|Encrypted PIN Block Data; |REVIEW|1|18|2|0|0|0|
|34|End-of-Data Indicator; |A FIXED max 1|5|29|0|5|2|0|
|35|End-of-Load Indicator; |A FIXED max 1|2|11|0|2|1|0|
|36|Extract Date; |N FIXED max 6|1|5|1|1|0|1|
|37|Extract Time; |N FIXED max 4|1|4|1|1|0|0|
|38|Fee Amount; |N FIXED max 8|0|2|1|0|0|0|
|39|Firmware Version; |AN FIXED max 8|3|12|5|2|0|1|
|40|Fleet Employee Number; |N VARIABLE max 10|1|3|0|1|0|0|
|41|Fuel Purchase Amount; |N VARIABLE max 8|3|21|2|0|1|0|
|42|Grand Total; |N FIXED max 8|2|8|3|0|0|0|
|43|Hardware Version; |REVIEW|3|13|5|2|1|1|
|44|Information Byte; |AN FIXED max 1|5|24|12|3|2|1|
|45|Initiation Date; |N FIXED max 6|2|8|3|1|2|1|
|46|Initiation Time; |N FIXED max 4|2|7|3|1|2|0|
|47|Job Number; |N VARIABLE max 10|1|3|0|1|0|0|
|48|Load Type; |A FIXED max 1|1|6|6|1|0|0|
|49|Local Date and Local Time; |N FIXED max 10|1|6|2|0|0|0|
|50|Local Time; |N FIXED max 4|1|4|1|1|0|0|
|51|Mail Text Data; |REVIEW|1|5|1|0|1|0|
|52|Mail Text Data Length; |N FIXED max 3|1|5|1|0|1|0|
|53|Merchant Name; |AN FIXED max 24|1|3|0|1|0|0|
|54|Merchant Phone Number; |AN FIXED max 13|2|6|0|2|1|0|
|55|Message Format Version Identifier; |AN FIXED max 6|2|11|9|2|0|1|
|56|Net Amount; |N FIXED max 8|0|3|1|0|0|0|
|57|New Software Version; |AN FIXED max 8|3|18|0|3|0|0|
|58|Nonfuel Amount; |N VARIABLE max 8|3|21|2|0|1|0|
|59|Number of Card Types; |N FIXED max 2|1|5|0|1|1|0|
|61|Number of Print Lines; |N FIXED max 1|0|3|2|0|0|0|
|62|Number of Products; |N FIXED max 2|4|14|0|1|1|0|
|63|Number of Segments; |N VARIABLE max 2|8|38|9|8|4|5|
|64|Odometer; |N VARIABLE max 8|1|8|0|1|1|0|
|65|Password; |N VARIABLE max 6|5|24|4|4|2|0|
|66|Pause Indicator; |AN FIXED max 1|2|8|0|2|2|0|
|72|PC Duty Amount; |N VARIABLE max 7|1|3|0|1|1|0|
|73|PC Freight Amount; |N VARIABLE max 7|1|3|0|1|1|0|
|74|PC Tax Amount; |N VARIABLE max 7|1|3|0|1|1|0|
|75|Phone Number; |N VARIABLE max 18|2|7|0|2|2|0|
|76|Product Amount; |N VARIABLE max 12|4|16|0|1|1|0|
|77|Product Code; |N FIXED max 3|10|1182|0|2|4|0|
|78|Prompt Code; |AN VARIABLE max 4|14|324|7|5|4|1|
|79|Pump/Lane Number; |N VARIABLE max 2|1|10|2|0|0|0|
|80|Purchase Code; |AN VARIABLE max 16|1|3|0|1|1|0|
|81|Quantity; |N VARIABLE max 9|2|7|0|0|1|0|
|82|Redial Count; |N FIXED max 1|2|8|0|2|2|0|
|83|Response Code; |AN FIXED max 1|10|45|7|4|6|0|
|84|Segment Length; |N VARIABLE max 4|56|242|13|34|19|19|
|85|Segment Type; |N FIXED max 3|40|171|13|19|14|39|
|86|Sequence Number; |N FIXED max 6|11|71|14|7|5|4|
|87|Service Level; |A FIXED max 1|2|7|0|0|0|0|
|88|Ship-from Postal Code; |AN FIXED max 10|1|3|0|1|0|0|
|89|Ship-to Country Code; |N FIXED max 3|1|3|0|1|1|0|
|90|Ship-to Postal Code; |AN FIXED max 10|1|3|0|1|1|0|
|91|Software Load Phone Number; |AN VARIABLE max 18|3|18|0|3|0|0|
|92|Software Load Request Date; |N FIXED max 6|3|18|0|3|0|0|
|93|Software Load Request Time; |N FIXED max 4|3|18|0|3|0|0|
|94|Software Load Type; |REVIEW|3|20|0|3|0|0|
|95|Software Terminal Record ID; |AN FIXED max 13|3|18|0|3|0|0|
|96|Software Version; |AN FIXED max 8|3|12|5|2|0|1|
|97|Start-of-Data Block Indicator; |A FIXED max 1|2|8|2|2|1|0|
|98|Store Number; |N FIXED max 16|2|8|0|2|1|0|
|99|Tax Amount; |N VARIABLE max 8|4|25|2|0|1|0|
|100|Terminal Display Communications Message; |A FIXED max 16|0|2|1|0|0|0|
|101|Terminal Display /Printer Message; |AN FIXED max 31|0|2|5|0|0|0|
|102|Terminal Identifier; |AN VARIABLE max 22|14|69|13|7|1|0|
|103|Text Data; |AN VARIABLE max 150|1|3|1|1|1|0|
|104|Text Data Length; |N FIXED max 3|2|8|1|2|1|0|
|105|Totals Date; |N FIXED max 6|3|24|4|2|1|1|
|106|Unit of Measure; |A FIXED max 1|2|7|0|0|0|0|
|107|Unit Price; |N VARIABLE max 9|2|7|0|0|1|0|
|108|Vehicle Number; |N VARIABLE max 10|1|3|0|1|0|0|
|109|Voucher ID; |N VARIABLE max 10|1|7|0|1|1|0|
|111|Variable Information Indicator; |AN FIXED max 3|3|13|1|1|0|0|
|112|Variable Information Length; |N FIXED max 3|3|11|1|1|1|0|
|113|Variable Information; |AN VARIABLE max 982|2|70|1|0|0|0|
|114|Software Load IP/URL Address; |AN FIXED max 30|2|12|0|2|0|0|
|115|Additional Information Data Segment Flag; |N FIXED max 1|2|10|2|1|0|0|
|116|Additional Information Indicator; |N FIXED max 3|1|67|0|1|0|0|
|117|Additional Information Length; |N FIXED max 3|1|5|0|1|0|0|
|118|Additional Information; EMV Additional Information; |REVIEW|7|50|0|7|0|0|
|119|Settlement Date; |N FIXED max 4|0|0|2|0|0|0|
|120|Network Management Message; |A FIXED max 8|0|2|1|0|0|0|
|121|Partial Approval Indicator; |N FIXED max 1|2|15|2|1|0|0|
|122|MICR Data; |AN VARIABLE max 50|3|10|1|1|1|0|
|123|Driver’s License; |AN VARIABLE max 40|1|4|1|1|0|0|
|124|State Code; |AN FIXED max 2|1|8|1|1|1|0|
|125|Date of Birth; |N FIXED max 8|1|4|1|1|0|0|
|126|Check Type; |AN FIXED max 1|1|5|1|1|1|0|
|127|Check Number; |AN VARIABLE max 8|1|3|1|1|0|0|
|128|Customer Phone Number; |N VARIABLE max 10|1|3|1|1|0|0|
|129|Customer Last Name; |AN VARIABLE max 24|1|3|1|1|0|0|
|130|Check Issue Date; |N FIXED max 8|1|3|1|1|0|0|
|131|ECA/ TeleCheck® Clerk ID; |AN VARIABLE max 6|1|9|1|1|1|0|
|132|ECA/ TeleCheck® Product Code; |AN VARIABLE max 6|1|4|1|1|1|0|
|133|ECA/ TeleCheck® Phone Number; |N VARIABLE max 10|1|4|1|1|1|0|
|134|ECA/ TeleCheck® Trace ID; |AN VARIABLE max 22|1|7|1|1|1|0|
|135|Merchant Trace ID; |AN VARIABLE max 25|1|4|1|1|1|0|
|136|Denial Record Number; |AN VARIABLE max 7|1|4|1|1|1|0|
|137|Extended MICR Data; |AN VARIABLE max 65|1|4|1|1|1|0|
|138|Loyalty Program ID; |N VARIABLE max 6|1|7|1|1|1|0|
|139|Loyalty Account Number; |N VARIABLE max 24|1|8|1|1|1|0|
|140|Points to Redeem; |N VARIABLE max 6|1|4|1|1|0|0|
|141|Coupon ID; |N VARIABLE max 19|1|4|1|1|0|0|
|142|Coupon Amount; |N VARIABLE max 8|1|4|1|1|0|0|
|143|Update Code; |AN FIXED max 1|1|9|1|1|1|0|
|144|Street Address; |N VARIABLE max 5|1|4|1|1|0|0|
|145|Phone Number, Loyalty; |N VARIABLE max 10|1|4|1|1|0|0|
|146|Expiration Date; |N VARIABLE max 4|1|4|1|1|1|0|
|147|Loyalty Track 2 Data; |AN VARIABLE max 38|1|4|1|1|1|0|
|148|Payment Tender Type; |AN FIXED max 2|1|9|1|1|1|0|
|149|SKU Data; |AN VARIABLE max 1000|1|3|1|1|1|0|
|150|Loyalty Information Version; |N FIXED max 1|2|9|1|1|1|0|
|151|Unit of Work; |N FIXED max 19|1|5|1|1|0|0|
|152|Print Data; |ANS VARIABLE max 900|3|9|0|2|2|0|
|153|WIC Discount Amount; |N VARIABLE max 40|2|16|0|2|1|0|
|154|WIC Product Data; |AN VARIABLE max 3001|2|12|0|2|2|0|
|155|Key ID; |AN FIXED max 11|1|4|0|1|1|0|
|156|Key Data Length; |N VARIABLE max 3|1|5|0|1|1|0|
|157|Key Data; |AN VARIABLE max 999|1|4|0|1|1|0|
|158|License #; |AN VARIABLE max 10|1|3|0|1|0|0|
|159|Job ID; |AN VARIABLE max 12|1|3|0|1|0|0|
|160|Department #; |AN VARIABLE max 12|1|3|0|1|0|0|
|161|Customer Data; |AN VARIABLE max 12|1|3|0|1|0|0|
|162|User ID; |AN VARIABLE max 12|1|6|0|1|1|0|
|163|Vehicle ID#; |AN VARIABLE max 8|1|3|0|1|0|0|
|164|EBT Program Data; |AN VARIABLE max 267|5|38|0|5|4|0|
|165|Start Date or End Date; |N FIXED max 8|2|6|0|2|2|0|
|166|Start Time or End Time; |N FIXED max 4|5|25|0|5|2|0|
|168|Number of Receipt Text Lines; |N FIXED max 2|1|6|0|1|1|0|
|169|Receipt Text Data Length; |N FIXED max 2|1|3|0|1|1|0|
|170|Receipt Text Data; |AN VARIABLE max 20|1|5|0|1|1|0|
|171|Number of Discounts; |N FIXED max 2|1|3|0|1|1|0|
|172|Product Discount Amount; |N FIXED max 5|1|3|0|1|1|0|
|173|BUYPASS Card Type; |N FIXED max 4|1|3|0|1|1|0|
|174|Card Table Type; |N FIXED max 4|1|5|2|1|1|0|
|175|Card Table Data; |AN VARIABLE max 3600|1|5|0|1|1|0|
|176|Device Card Table Version; |N FIXED max 35|2|7|3|1|1|0|
|177|Card Table Load Version; |N FIXED max 35|1|3|2|1|1|0|
|178|Load Control Key; |AN FIXED max 60|1|3|2|1|1|0|
|179|Host Discount Timestamp; |N FIXED max 12|3|10|3|2|2|0|
|180|Site Configuration Data; |AN VARIABLE max 3600|1|4|0|1|1|0|
|182|Prompt Code, Pending; |AN FIXED max 4|1|5|2|1|1|0|
|183|Card BIN Range, Beginning; |REVIEW|1|3|0|1|1|0|
|184|Card BIN Range, Ending; |REVIEW|1|4|0|1|1|0|
|185|Discount Quantity Limit; |N FIXED max 3|1|3|0|1|1|0|
|186|Discount Program Description; |AN FIXED max 15|1|3|0|1|1|0|
|187|CA Public Key File Checksum; |N FIXED max 25|4|14|2|3|0|0|
|188|EMV Card Sequence Number; |AN FIXED max 3|1|6|0|1|0|0|
|189|EMV Chip Data Length; |N FIXED max 3|2|11|0|0|0|0|
|190|EMV Chip Data; |ANSB VARIABLE max 999|3|15|0|0|0|0|
|191|EMV Additional Information Indicator; |N FIXED max 3|4|16|0|4|0|0|
|192|EMV Additional Information Length; |N FIXED max 3|4|15|0|4|0|0|
|193|CA Public Key File Block Length; |N FIXED max 3|0|5|1|0|0|0|
|194|CA Public Key File Block; |AN VARIABLE max 9999|0|3|1|0|0|0|
|195|CAVV Revised Format; |AN FIXED max 20|1|9|0|1|0|0|
|196|Token Requestor ID; |AN FIXED max 11|1|2|0|1|0|0|
|197|Token PAN Suffix; |AN FIXED max 4|1|5|0|1|0|0|
|198|Settlement Type; |AN FIXED max 1|1|7|0|1|0|0|
|199|Signature Required; |AN FIXED max 1|1|5|0|1|0|0|
|200|Receipt Card Description; |AN FIXED max 10|1|3|0|1|0|0|
|201|Fuel Volume Data; |AN VARIABLE max 3600|1|3|0|1|1|0|
|202|Cryptogram Token Data; |REVIEW|1|3|0|1|0|0|
|203|Safekey Data; |REVIEW|1|6|0|1|0|0|
|204|SafeKey Response; |AN FIXED max 1|1|3|0|1|0|0|
|205|Load Subtype; |AN FIXED max 1|0|2|1|0|0|0|
|206|SPDH Header; |AN FIXED max 48|4|15|2|0|0|0|
|207|Moneris Terminal Identifier; |AN FIXED max 8|4|14|2|0|0|0|
|208|Moneris Merchant ID; |AN FIXED max 13|4|14|2|0|0|0|
|209|Moneris Response Code; |AN FIXED max 2|0|0|1|0|0|0|
|210|MAC; |AN FIXED max 16|1|3|1|0|0|0|
|211|Moneris Key Indicators; |AN FIXED max 3|0|0|1|0|0|0|
|212|Moneris Key Data; |AN FIXED max 16|0|1|2|0|0|0|
|213|Moneris Data; |AN VARIABLE max 100|2|6|0|0|0|0|
|214|Batch Number; |AN FIXED max 3|4|16|0|0|0|0|
|215|Language Indicator; |AN FIXED max 1|2|8|0|0|0|0|
|216|Response Display; |AN FIXED max 16|2|7|0|0|0|0|
|217|Number of Debits; |N FIXED max 4|2|10|0|0|0|0|
|218|Debit Dollar Value; |AN FIXED max 19|3|13|0|1|0|0|
|219|Number of Credits; |N FIXED max 4|2|10|0|0|0|0|
|220|Credit Dollar Value; |AN FIXED max 19|3|13|0|1|0|0|
|221|Number of Corrections; |N FIXED max 4|2|10|0|0|0|0|
|222|Corrections Dollar Value; |AN FIXED max 19|3|13|0|1|0|0|
|223|Inclusive/Exclusive for Tax 1; |AN FIXED max 1|2|9|0|2|0|0|
|224|Tax Type 1; |AN FIXED max 3|2|7|0|2|0|0|
|225|Tax Amount 1; |N VARIABLE max 9|1|5|0|1|0|0|
|226|Inclusive/Exclusive for Tax 2; |AN FIXED max 3|2|8|0|2|0|0|
|227|Tax Type 2; |AN FIXED max 3|2|6|0|2|0|0|
|228|Tax Amount 2; |N VARIABLE max 9|1|5|0|1|0|0|
|229|Inclusive/Exclusive for Tax 3; |AN FIXED max 1|2|8|0|2|0|0|
|230|Tax Type 3; |AN FIXED max 3|2|6|0|2|0|0|
|231|Tax Amount 3; |N VARIABLE max 9|1|5|0|1|0|0|
|232|Download Data; |AN VARIABLE max 100|3|19|0|3|0|0|
|233|RID; |AN FIXED max 10|2|12|0|2|0|0|
|234|Stand-in Indicator; |N FIXED max 1|2|14|0|2|0|0|
|235|Floor Limit; |N FIXED max 12|2|14|0|2|0|0|
|236|BUYPASS RID Card Type; |AN FIXED max 3|2|12|0|2|0|0|
|237|TAVV Cryptogram; |AN FIXED max 28|2|16|0|2|0|0|
|238|TAVV Result Code; |AN FIXED max 1|1|3|0|1|0|0|
|239|Enhanced Fleet Data; |AN VARIABLE max 999|9|27|1|2|1|0|
|240|Available Product Information; |AN VARIABLE max 17|1|3|0|1|0|0|
|241|Price Data; |AN VARIABLE max 999|1|4|0|1|0|0|
|242|EV Charging Data; |AN VARIABLE max 200|1|1|0|1|0|0|
|243|Extended Unit of Measure; |REVIEW|0|2|0|0|0|0|
