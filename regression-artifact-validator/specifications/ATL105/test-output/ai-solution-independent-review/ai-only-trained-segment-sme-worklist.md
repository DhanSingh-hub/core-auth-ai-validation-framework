# AI-Only Trained-Segment SME Triage Worklist

Ranked by HIGH_SME_REVIEW volume (`ELEMENT_NOT_IN_CATALOG_NEW_RULE_CANDIDATE`). Start with segment 111 — it has the largest gap between AI output volume and the Test Solution's own rule catalog.

## Segment priority ranking

| Segment | HIGH_SME_REVIEW (new-rule candidates) | LOW_REMATCH (matching gap) | Total AI-only |
|---|---:|---:|---:|
|111|634|114|748|
|100|237|266|503|
|105|205|33|238|
|102|113|79|192|
|103|54|23|77|
|109|49|6|55|
|101|48|37|85|
|108|30|64|94|
|104|19|17|36|
|113|15|16|31|

## Segment 111 — first worklist (634 total new-rule candidates; showing first 40, sorted by source page)

Each row needs an SME decision: **NEW_RULE** (independently derive a rule from the spec at this page, then build its own TS/TC/TD chain), **DUPLICATE** (matches an existing rule after closer reading; record which one), or **REJECT** (not a real, independently verifiable requirement).

| Requirement ID | Source page | Statement | SME decision |
|---|---:|---|---|
|REQ-SRC-ATL105-PDF-001:258|9|Data element 111 value '073' triggers processing rule defined in data element 113.| |
|REQ-SRC-ATL105-PDF-001:261|11|Values '68' (Additional Transaction Fee 1) and '69' (Additional Transaction Fee 2) added to data element '111'.| |
|REQ-SRC-ATL105-PDF-001:262|11|Processing rules added for data element '111' values '68' and '69' within data element '113'.| |
|REQ-SRC-ATL105-PDF-001:264|11|Anticipated update: value '70' (Anticipated Completion Amount) to be added in data element '111' (Variable Information Indicator).| |
|REQ-SRC-ATL105-PDF-001:267|12|Data element 111 value '70' triggers a processing rule for data element 113.| |
|REQ-SRC-ATL105-PDF-001:269|12|Value '066' added as valid value for data element 111 (Variable Information Indicator).| |
|REQ-SRC-ATL105-PDF-001:279|16|Value '067' added for data element 111; processing rule added for data element 113 accordingly.| |
|REQ-SRC-ATL105-PDF-001:334|46|Vendor assigned TPP/VAR ID must send it to BUYPASS in Variable Information (Element 113) of Authorization Request.| |
|REQ-SRC-ATL105-PDF-001:335|46|A unique terminal ID must be assigned per POS entry point and sent in Variable Information (Element 113) of Authorization Request.| |
|REQ-SRC-ATL105-PDF-001:477|83|Recommended SIC Information Code (Segment 111, Table ID 007) is '5542' for CAT outside transactions and '5541' for inside transactions.| |
|REQ-SRC-ATL105-PDF-001:643|108|PINless Debit Acceptance field value must be 'X' in Variable Information Data Segment for this transaction.| |
|REQ-SRC-ATL105-PDF-001:773|129|ZIP Code value is sent in Table ID 003 of Element 111 (Variable Information Indicator).| |
|REQ-SRC-ATL105-PDF-001:837|142|If Soft Descriptor Merchant Name is sent from a Third Party Payment Provider, underlying merchant name field must also be present.| |
|REQ-SRC-ATL105-PDF-001:839|142|SIC Information with MCC/SIC of underlying retailer for third-party payment provider Installments must be sent for Discover.| |
|REQ-SRC-ATL105-PDF-001:840|142|Sub Table ID 09 of Table ID 56 indicates MIT/CIT category and subcategory data for MasterCard card type.| |
|REQ-SRC-ATL105-PDF-001:841|143|For storing credentials first time, Data Element 111 Table ID 32 Sub table ID 08 must have value 'I' (Initial).| |
|REQ-SRC-ATL105-PDF-001:842|143|For MasterCard, merchant must send MIT/CIT category and subcategory data valid for CIT transactions.| |
|REQ-SRC-ATL105-PDF-001:843|143|For subsequent cardholder-initiated transaction using stored credentials, Data Element 111 Table ID 05 POS Entry Mode value must be '10'.| |
|REQ-SRC-ATL105-PDF-001:844|143|Subsequent transaction also requires Data Element 111 Table ID 32 POS Additional Data Sub table ID 08 value, continued on next page.| |
|REQ-SRC-ATL105-PDF-001:845|144|For MasterCard card type, merchant must send MIT/CIT category and subcategory data for CIT ad hoc/unscheduled transactions.| |
|REQ-SRC-ATL105-PDF-001:846|144|Credential on File transactions require Data Element 111, Table ID 49, Sub Table ID 02 value 'C' (Cardholder Initiated).| |
|REQ-SRC-ATL105-PDF-001:847|144|Recurring standing order (variable amount, fixed frequency) CIT requires cardholder to store credential-on-file and initiate first transaction.| |
|REQ-SRC-ATL105-PDF-001:848|145|For MasterCard, merchant must send MIT/CIT category and subcategory data valid for CIT transactions.| |
|REQ-SRC-ATL105-PDF-001:849|145|Recurring Subscription (fixed amount/frequency) CIT sets Data Element 111 Table ID 13 Special Payments Indicator to 'R' (Recurring).| |
|REQ-SRC-ATL105-PDF-001:850|145|Recurring Subscription CIT sets Data Element 111 Table ID 49 Sub Table ID 02 to 'C' (Customer Initiated).| |
|REQ-SRC-ATL105-PDF-001:851|145|Recurring Subscription CIT sets Data Element 111 Table ID 32 Sub Table ID 08 to 'I' (Initial) with original POS Entry Mode.| |
|REQ-SRC-ATL105-PDF-001:853|146|For MasterCard, Merchant must send MIT/CIT category and subcategory data valid for CIT transactions.| |
|REQ-SRC-ATL105-PDF-001:854|146|Cardholder-initiated installment transaction stores credential-on-file and initiates first transaction in a series with set frequency/duration.| |
|REQ-SRC-ATL105-PDF-001:855|146|Installment transaction requires Data Element 111 Table ID 32 Sub Table ID 08 value 'I' (Initial).| |
|REQ-SRC-ATL105-PDF-001:856|146|Installment transaction requires Data Element 111 Table ID 13 value 'I' (Installment).| |
|REQ-SRC-ATL105-PDF-001:857|146|Installment transaction requires Data Element 111 Table ID 49 Sub Table ID 02 value 'C' (Customer Initiated).| |
|REQ-SRC-ATL105-PDF-001:858|147|For MasterCard card type, Merchant must send MIT/CIT category and subcategory data for CIT transactions.| |
|REQ-SRC-ATL105-PDF-001:859|148|First recurring transaction with no supplies is a cardholder-initiated $0.00 transaction with Data Element 111 Table ID 13 = 'R'.| |
|REQ-SRC-ATL105-PDF-001:862|148|First recurring transaction with supplies is cardholder-initiated with actual authorization amount and DE 111 Table ID 13 = 'R'.| |
|REQ-SRC-ATL105-PDF-001:864|149|Merchant receives Discover Network Reference ID in Segment 112, Table ID 012 for use in subsequent recurring transactions.| |
|REQ-SRC-ATL105-PDF-001:865|149|Subsequent recurring transactions on 30th of month must be MIT with POS Entry Mode value '10' in Data Element 111 Table ID 05.| |
|REQ-SRC-ATL105-PDF-001:866|149|Data Element 111, Table ID 13 Special Payments Indicator must be 'R' (Recurring) for subsequent recurring MIT transactions.| |
|REQ-SRC-ATL105-PDF-001:867|149|Data Element 111, Table ID 32, Sub Table ID 08 POS Additional Data must be 'C' (Credential on File) for recurring MIT.| |
|REQ-SRC-ATL105-PDF-001:868|149|Data Element 111, Table ID 49, Sub Table ID 02 Merchant Supplementary Data must be 'M' (Merchant Initiated) for recurring MIT.| |
|REQ-SRC-ATL105-PDF-001:869|149|For Visa, Discover and Amex recurring transactions, merchants should include original Tran ID and Discover Network Reference ID in request.| |

Full list (all 634 segment 111 candidates, plus every other segment): `ai-only-trained-segment-triage.csv`.
