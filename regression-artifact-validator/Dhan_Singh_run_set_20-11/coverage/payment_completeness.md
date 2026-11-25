# Payment completeness

Standard card-authorization risk areas (config/payment_obligations.json) matched to this BR's own KB rules. Nothing is generated from the checklist: a matched rule is tested through the normal techniques; an area with no KB rule is an SME question (domain_gap_candidates.csv).

| Group | Area | Status | Network rules | Tested | Duties met | Shared (DE / OP) rules |
|---|---|---|---|---|---|---|
| Card data | pan_validity | partly | 25 | 16 | 17 / 28 | 84 |
| Card data | expiry | partly | 20 | 11 | 7 / 28 | 36 |
| Card data | chip_swipe_fallback | partly | 35 | 27 | 28 / 48 | 3 |
| Card data | track_data | partly | 17 | 5 | 3 / 14 | 20 |
| Card data | commercial_card | covered | 2 | 2 | 2 / 2 | 6 |
| EMV / CVM | emv_data | partly | 20 | 10 | 8 / 24 | 23 |
| EMV / CVM | cvm | partly | 55 | 26 | 21 / 75 | 12 |
| Network rules | transaction_link | partly | 4 | 2 | 1 / 2 | 7 |
| Network rules | stored_credential | partly | 20 | 2 | 1 / 35 | 30 |
| Network rules | auth_hold | partly | 1 | 1 | 0 / 2 | 0 |
| Network rules | incremental | partly | 8 | 1 | 1 / 13 | 1 |
| Network rules | three_ds | partly | 10 | 4 | 2 / 10 | 6 |
| Network rules | cashback | partly | 2 | 1 | 1 / 6 | 6 |
| Network rules | account_verification | no_rule | 0 | 0 | 0 / 0 | 0 |
| Lifecycle | reversal_void | partly | 60 | 23 | 22 / 86 | 16 |
| Lifecycle | duplicate_resubmission | partly | 7 | 3 | 7 / 12 | 3 |
| Lifecycle | sequence_uniqueness | partly | 14 | 5 | 5 / 20 | 40 |
| Lifecycle | partial_approval | partly | 13 | 9 | 7 / 18 | 14 |
| Lifecycle | split_tender | gap | 3 | 0 | 0 / 3 | 0 |
| Lifecycle | completion_amount | partly | 12 | 9 | 9 / 17 | 16 |
| Ordering | ordering | partly | 10 | 7 | 4 / 12 | 5 |
| Time / offline | store_forward | partly | 98 | 28 | 18 / 110 | 4 |
| Time / offline | timeout | partly | 17 | 8 | 4 / 22 | 1 |
| Time / offline | cut_time | partly | 11 | 7 | 5 / 13 | 7 |
| Security | pan_masking | partly | 18 | 17 | 19 / 21 | 2 |
| Security | data_retention | partly | 2 | 1 | 1 / 1 | 3 |
| Security | key_management | partly | 6 | 1 | 1 / 11 | 38 |
| Security | tokenization | partly | 16 | 11 | 10 / 20 | 46 |
| Hospitality | tip_gratuity | covered | 1 | 1 | 1 / 1 | 0 |
| Hospitality | batch_capture | partly | 5 | 3 | 4 / 5 | 2 |
