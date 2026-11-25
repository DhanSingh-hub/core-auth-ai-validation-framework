# Payment completeness

Standard card-authorization risk areas (config/payment_obligations.json) matched to this BR's own KB rules. Nothing is generated from the checklist: a matched rule is tested through the normal techniques; an area with no KB rule is an SME question (domain_gap_candidates.csv).

| Group | Area | Status | Network rules | Tested | Duties met | Shared (DE / OP) rules |
|---|---|---|---|---|---|---|
| Card data | pan_validity | partly | 36 | 27 | 35 / 48 | 91 |
| Card data | expiry | partly | 23 | 15 | 17 / 36 | 53 |
| Card data | chip_swipe_fallback | partly | 41 | 32 | 38 / 65 | 3 |
| Card data | track_data | partly | 28 | 14 | 10 / 42 | 28 |
| Card data | commercial_card | covered | 2 | 2 | 2 / 2 | 6 |
| EMV / CVM | emv_data | partly | 22 | 13 | 11 / 30 | 24 |
| EMV / CVM | cvm | partly | 60 | 32 | 32 / 92 | 13 |
| Network rules | transaction_link | partly | 5 | 3 | 1 / 2 | 7 |
| Network rules | stored_credential | partly | 20 | 6 | 5 / 33 | 30 |
| Network rules | auth_hold | partly | 1 | 1 | 0 / 2 | 0 |
| Network rules | incremental | partly | 16 | 3 | 3 / 25 | 4 |
| Network rules | three_ds | partly | 10 | 5 | 3 / 10 | 6 |
| Network rules | cashback | partly | 2 | 1 | 1 / 6 | 6 |
| Network rules | account_verification | no_rule | 0 | 0 | 0 / 0 | 0 |
| Lifecycle | reversal_void | partly | 60 | 28 | 28 / 88 | 16 |
| Lifecycle | duplicate_resubmission | partly | 8 | 6 | 11 / 14 | 3 |
| Lifecycle | sequence_uniqueness | partly | 14 | 12 | 11 / 20 | 44 |
| Lifecycle | partial_approval | partly | 13 | 9 | 9 / 18 | 20 |
| Lifecycle | split_tender | gap | 3 | 0 | 0 / 3 | 0 |
| Lifecycle | completion_amount | partly | 14 | 11 | 13 / 23 | 17 |
| Ordering | ordering | partly | 12 | 9 | 6 / 16 | 5 |
| Time / offline | store_forward | partly | 125 | 48 | 19 / 167 | 4 |
| Time / offline | timeout | partly | 17 | 8 | 4 / 23 | 1 |
| Time / offline | cut_time | partly | 13 | 9 | 7 / 17 | 7 |
| Security | pan_masking | partly | 25 | 24 | 33 / 35 | 3 |
| Security | data_retention | partly | 2 | 1 | 1 / 1 | 3 |
| Security | key_management | partly | 6 | 1 | 1 / 11 | 38 |
| Security | tokenization | partly | 16 | 7 | 6 / 20 | 46 |
| Hospitality | tip_gratuity | covered | 1 | 1 | 1 / 1 | 0 |
| Hospitality | batch_capture | partly | 5 | 3 | 4 / 5 | 2 |
