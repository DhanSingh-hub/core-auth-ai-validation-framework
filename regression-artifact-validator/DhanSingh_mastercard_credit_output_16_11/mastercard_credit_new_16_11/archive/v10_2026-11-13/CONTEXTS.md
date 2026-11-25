# Acceptance contexts - MasterCard Credit (plan v10)

From `coverage/context_matrix.csv`. Every candidate the spec gives (industry x attendance, card not present, merchant categories named by rules) with its status, and the spec's terminal-profile codes.

| Context | Label | Industry | Attendance | Channel | MCC | Status | Test cases | Reason |
|---|---|---|---|---|---|---|---:|---|
| GENERAL | General (default) · attended POS | General | attended | card_present |  | general | 1148 | the mapping's own card-present scenarios (the merchant policy's default merchant) |
| CARD_NOT_PRESENT | Card not present | Card not present | attended | card_not_present |  | active | 257 |  |
| GENERAL_RETAIL-ATTENDED | General Retail · attended POS | General Retail | attended | card_present |  | merged_into_general | 0 | the general context's own industry and attendance (its themes run on GENERAL) |
| GENERAL_RETAIL-UNATTENDED | General Retail · unattended CAT | General Retail | unattended | card_present |  | equivalent_to_general | 0 | no verified theme profile binds this industry and attendance |
| HOSPITALITY-ATTENDED | Hospitality · attended POS | Hospitality | attended | card_present |  | active | 48 |  |
| HOSPITALITY-UNATTENDED | Hospitality · unattended CAT | Hospitality | unattended | card_present |  | equivalent_to_general | 0 | no verified theme profile binds this industry and attendance |
| SUPERMARKET-ATTENDED | Supermarket · attended POS | Supermarket | attended | card_present |  | equivalent_to_general | 0 | no verified theme profile binds this industry and attendance |
| SUPERMARKET-UNATTENDED | Supermarket · unattended CAT | Supermarket | unattended | card_present |  | equivalent_to_general | 0 | no verified theme profile binds this industry and attendance |
| MAIL_ORDER-ATTENDED | Mail Order · attended POS | Mail Order | attended | card_present |  | equivalent_to_general | 0 | no verified theme profile binds this industry and attendance |
| MAIL_ORDER-UNATTENDED | Mail Order · unattended CAT | Mail Order | unattended | card_present |  | equivalent_to_general | 0 | no verified theme profile binds this industry and attendance |
| UTILITIES-ATTENDED | Utilities · attended POS | Utilities | attended | card_present |  | equivalent_to_general | 0 | no verified theme profile binds this industry and attendance |
| UTILITIES-UNATTENDED | Utilities · unattended CAT | Utilities | unattended | card_present |  | equivalent_to_general | 0 | no verified theme profile binds this industry and attendance |
| PETROLEUM-ATTENDED | Petroleum · attended POS | Petroleum | attended | card_present |  | active | 214 |  |
| PETROLEUM-UNATTENDED | Petroleum · unattended CAT | Petroleum | unattended | card_present |  | active | 154 |  |
| MCC_4121 | Merchant category 4121 · card not present | Merchant category 4121 | attended | card_not_present | 4121 | equivalent_to_general | 0 | its rules apply to card-not-present only: bound to CARD_NOT_PRESENT |
| MCC_5542 | Merchant category 5542 · attended POS | Merchant category 5542 | attended | card_present | 5542 | active | 10 |  |
| MCC_5552 | Merchant category 5552 · attended POS | Merchant category 5552 | attended | card_present | 5552 | active | 10 |  |
| MCC_6051 | Merchant category 6051 · attended POS | Merchant category 6051 | attended | card_present | 6051 | active | 10 |  |
| MCC_6211 | Merchant category 6211 · attended POS | Merchant category 6211 | attended | card_present | 6211 | active | 10 |  |

## Terminal-profile codes of the spec (42)

Listed, not built as contexts of their own in this build (the placeholder terminals' profiles carry the contexts); see README 'Not done'.

| Code | Meaning |
|---|---|
| Q:0 | Attended |
| Q:1 | Unattended |
| Q:2 | Administrative |
| Q:00 | Administrative terminal |
| Q:01 | Point-of-service terminal |
| Q:02 | ATM |
| Q:03 | Home terminal |
| Q:04 | ECR |
| Q:05 | Dial terminal |
| Q:06 | Traveler's check machine |
| Q:07 | Fuel machine |
| Q:08 | Scrip machine |
| Q:09 | Coupon machine |
| Q:10 | Ticket machine |
| Q:11 | Point-of-banking terminal |
| Q:19 | Call Center Operator |
| Q:20 | Voice response unit (VRU). |
| Q:21 | Channel encryption; cardholder certificate is not used |
| Q:24 | Digital secure remote payment with UCAF data transactions |
| Q:25 | Internet (nonrecurring) (secure using Verified by Visa® or 3DES) |
| Q:26 | Internet (secure; 3DES attempted) |
| Q:27 | Internet (secure using Secure Socket Layer [SSL]) |
| Q:28 | Internet (nonsecure) |
| Q:29 | Internet recurring payment transaction after initial transaction was processed with a mobile device-issued payment token (MasterCard Only) |
| Q:020 | Attended on premise administrative terminal |
| Q:021 | Attended off premise administrative terminal |
| Q:100 | Unattended on premise customer-operated point-of-service terminal |
| Q:001 | Attended off premise customer-operated point-of-service terminal |
| Q:101 | Unattended off premise customer-operated ATM |
| Q:000 | Attended on premise customer-operated home terminal |
| Q:010 | Attended on premise Card acceptor-operated ECR |
| Q:8 | Preauthorized purchase |
| Q:9 | Reserved for private use |
| Q:3 | Third re-presentment |
| Q:4 | Previously authorized request |
| Q:5 | Resubmit |
| Q:0000 | Customer and card present |
| Q:0100 | Customer present; card not present |
| Q:1000 | Customer not present; card present |
| Q:1100 | Customer not present; card not present |
| Q:2100 | Mail and/or telephone order |
| Q:8800 | Preauthorized purchase |
