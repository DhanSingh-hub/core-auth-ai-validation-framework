# Test cases - BROP BUYPASS ATL105 Terminal & Host Operations

Build `buypass-atl105-mastercard-credit-generated-6181e6cf0e`; mapping status **candidate**; merchants: **placeholder**; catalog `config/transaction_patterns.json` None.

Transaction-level test cases: each is a business transaction or flow run end to end. The spec's data-element rules are checked inside every step (rule checks); device rules are checkpoints. The test data is the request of each step; the expected response is on the test case, per step.

Naming: BR = `{Platform} {Spec} {Network} {Program}`; scenario = `{BR} {Transaction Category} – {Entry Mode}`; test case = `{BR} {Merchant} {Transaction Category} – {Entry Mode} {flow} → {outcome}`.

## Summary

| Measure | Value |
|---|---|
| Scenarios | 9 |
| Test cases | 47 |
| By status | requires_review: 1, ready: 20, needs_client_input: 26 |
| By polarity | Negative: 21, Positive: 26 |
| Verification | NOT_APPLICABLE: 6, PASS: 41 |
| Verification level | NOT_APPLICABLE: 6, PASS: 41 (PASS_WITH_ADVISORIES = no rule violated, but some constraints are not verifiable by the producer) |
| Response code tiers | N/A: 6, INFER: 27, FLAG: 13, ASSERT: 1 |
| Scenarios with test cases | 9 / 9 |
| Validation | passed |

## By type

| Type | Test cases |
|---|---|
| T11 Device-only | 4 |
| T13 Terminal & host operation | 43 |

## BROP BUYPASS ATL105 Terminal & Host Operations


### BROP_TS1 BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BROP_TS1_TC1 | TC-078253 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Store and Forward blocking window → no off-line approval inside the window | T11 | - | n/a (no host message) | requires_review |
| BROP_TS1_TC2 | TC-078254 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Return/Refund disabled → not offered at the device | T11 | - | n/a (no host message) | ready |
| BROP_TS1_TC3 | TC-078255 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Void/Reversal disabled → not offered at the device | T11 | - | n/a (no host message) | ready |
| BROP_TS1_TC4 | TC-078256 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Preauthorization disabled → not offered at the device | T11 | - | n/a (no host message) | ready |
| BROP_TS1_TC5 | TC-078257 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Before the first successful Table Load every transaction except downline loads is refused at the device | T13 | - | n/a (no host message) | ready |
| BROP_TS1_TC6 | TC-078258 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Local totals grouped by Card Label; Authorization only and stored value buckets kept out of the grand total | T13 | - | n/a (no host message) | ready |

### BROP_TS2 BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BROP_TS2_TC1 | TC-078259 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Totals for the current settlement date and end of day | T13 | 990 | 1: approved (any of 5, 6, B, D, N, W) (INFER) | ready |
| BROP_TS2_TC2 | TC-078260 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Totals for the most recent date with activity | T13 | 990 | 1: approved (any of 5, 6, B, D, N, W) (INFER) | ready |
| BROP_TS2_TC3 | TC-078261 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Totals for the second most recent date with activity | T13 | 990 | 1: approved (any of 5, 6, B, D, N, W) (INFER) | ready |
| BROP_TS2_TC4 | TC-078262 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Totals for the third most recent date with activity | T13 | 990 | 1: approved (any of 5, 6, B, D, N, W) (INFER) | ready |
| BROP_TS2_TC5 | TC-078263 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Totals since the last clear | T13 | 990 | 1: approved (any of 5, 6, B, D, N, W) (INFER) | ready |
| BROP_TS2_TC6 | TC-078264 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Clear all totals since the last clear | T13 | 990 | 1: approved (any of 5, 6, B, D, N, W) (INFER) | ready |
| BROP_TS2_TC7 | TC-078265 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Totals for a date before the third most recent date with activity → declined | T13 | 990 | 1: declined (any of 7, C, E, M, V) (INFER) | needs_client_input |
| BROP_TS2_TC8 | TC-078266 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Totals with an end-of-day password that does not match the merchant profile → declined | T13 | 990 | 1: declined (any of 7, C, E, M, V) (INFER) | needs_client_input |
| BROP_TS2_TC9 | TC-078267 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Totals for the current settlement date and end of day declined | T13 | 990 | 1: 1 (FLAG) | needs_client_input |

### BROP_TS3 BUYPASS ATL105 Terminal & Host Operations Totals with Proprietary Data Load Request (Prompt Code 990) (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BROP_TS3_TC1 | TC-078268 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals with Proprietary Data Load Request: Totals with the device's card table version and host discount data | T13 | 990 | 1: approved (any of 5, 6, B, D, N, W) (INFER) | needs_client_input |
| BROP_TS3_TC2 | TC-078269 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals with Proprietary Data Load Request: Totals with the device's card table version and host discount data declined | T13 | 990 | 1: 1 (FLAG) | needs_client_input |

### BROP_TS4 BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BROP_TS4_TC1 | TC-078270 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Electronic Mail Request: Retrieve nonproprietary electronic mail | T13 | 981 | 1: approved (any of 8, 9, G, J) (INFER) | ready |
| BROP_TS4_TC2 | TC-078271 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Electronic Mail Request: Retrieve proprietary electronic mail | T13 | 996 | 1: approved (any of 8, 9, G, J) (INFER) | ready |
| BROP_TS4_TC3 | TC-078272 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Electronic Mail Request: Reset the electronic mail status flag | T13 | 997 | 1: approved (any of 8, 9, G, J) (INFER) | ready |
| BROP_TS4_TC4 | TC-078273 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Electronic Mail Request: Submit electronic mail | T13 | 995 | 1: approved (any of 8, 9, G, J) (INFER) | needs_client_input |
| BROP_TS4_TC5 | TC-078274 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Electronic Mail Request: Retrieve nonproprietary electronic mail declined | T13 | 981 | 1: 1 (FLAG) | needs_client_input |
| BROP_TS4_TC6 | TC-078275 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Electronic Mail Request: Retrieve proprietary electronic mail declined | T13 | 996 | 1: 1 (FLAG) | needs_client_input |
| BROP_TS4_TC7 | TC-078276 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Electronic Mail Request: Reset the electronic mail status flag declined | T13 | 997 | 1: 1 (FLAG) | needs_client_input |
| BROP_TS4_TC8 | TC-078277 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Electronic Mail Request: Submit electronic mail declined | T13 | 995 | 1: 1 (FLAG) | needs_client_input |

### BROP_TS5 BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BROP_TS5_TC1 | TC-078278 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: Retrieve custom receipt text | T13 | 901 | 1: approved (any of O, T, Y) (INFER) | ready |
| BROP_TS5_TC2 | TC-078279 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: Retrieve the dynamic card table | T13 | 902 | 1: approved (any of O, T, Y) (INFER) | ready |
| BROP_TS5_TC3 | TC-078280 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: Submit site configuration data | T13 | 903 | 1: approved (any of O, T, Y) (INFER) | needs_client_input |
| BROP_TS5_TC4 | TC-078281 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: Retrieve host discount data after a Totals Response with proprietary data pending | T13 | 904 | 1: approved (any of O, T, Y) (INFER) | needs_client_input |
| BROP_TS5_TC5 | TC-078282 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: Submit fuel volume data | T13 | 905 | 1: approved (any of O, T, Y) (INFER) | needs_client_input |
| BROP_TS5_TC6 | TC-078283 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: Retrieve custom receipt text declined | T13 | 901 | 1: 1 (FLAG) | needs_client_input |
| BROP_TS5_TC7 | TC-078284 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: Retrieve the dynamic card table declined | T13 | 902 | 1: 1 (FLAG) | needs_client_input |
| BROP_TS5_TC8 | TC-078285 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: Submit site configuration data declined | T13 | 903 | 1: 1 (FLAG) | needs_client_input |
| BROP_TS5_TC9 | TC-078286 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: Retrieve host discount data after a Totals Response with proprietary data pending declined | T13 | 904 | 1: 1 (FLAG) | needs_client_input |
| BROP_TS5_TC10 | TC-078287 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: Submit fuel volume data declined | T13 | 905 | 1: 1 (FLAG) | needs_client_input |

### BROP_TS6 BUYPASS ATL105 Terminal & Host Operations TransArmor Key Load Request (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BROP_TS6_TC1 | TC-078288 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS TransArmor Key Load Request: TransArmor key load | T13 | - | 1: approved (any of K, L, M, O, T, Y) (INFER) | needs_client_input |
| BROP_TS6_TC2 | TC-078289 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS TransArmor Key Load Request: TransArmor key load declined | T13 | - | 1: 1 (FLAG) | needs_client_input |

### BROP_TS7 BUYPASS ATL105 Terminal & Host Operations CA Public Key File Load Request (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BROP_TS7_TC1 | TC-078290 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS CA Public Key File Load Request: CA public key file load | T13 | - | 1: approved (any of K, L, M, O, T, Y) (INFER) | needs_client_input |
| BROP_TS7_TC2 | TC-078291 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS CA Public Key File Load Request: CA public key file load declined | T13 | - | 1: 1 (FLAG) | needs_client_input |

### BROP_TS8 BUYPASS ATL105 Terminal & Host Operations Communications Test (Transaction Type 900) (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BROP_TS8_TC1 | TC-078292 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Communications Test (Transaction Type 900): Communications test (COMMTEST) | T13 | - | 1: 1 (ASSERT) | ready |

### BROP_TS9 BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BROP_TS9_TC1 | TC-078293 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Downloads (Downline Load Request): Partial Table Load | T13 | - | 1: approved (any of K, L, M, O, T, Y) (INFER) | ready |
| BROP_TS9_TC2 | TC-078294 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Downloads (Downline Load Request): Phone Load | T13 | - | 1: approved (any of K, L, M, O, T, Y) (INFER) | ready |
| BROP_TS9_TC3 | TC-078295 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Downloads (Downline Load Request): Date and Time Load | T13 | - | 1: approved (any of K, L, M, O, T, Y) (INFER) | ready |
| BROP_TS9_TC4 | TC-078296 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Downloads (Downline Load Request): Software Load | T13 | - | 1: approved (any of K, L, M, O, T, Y) (INFER) | needs_client_input |
| BROP_TS9_TC5 | TC-078297 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Downloads (Downline Load Request): Moneris Key Load | T13 | - | 1: approved (any of K, L, M, O, T, Y) (INFER) | needs_client_input |
| BROP_TS9_TC6 | TC-078298 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Downloads (Downline Load Request): Table Load with the merchant profile load flag not set → error message block only | T13 | - | 1: declined (any of U, X) (INFER) | needs_client_input |
| BROP_TS9_TC7 | TC-078299 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Downloads (Downline Load Request): Transaction response requests a download → device sends a Partial Table Load | T13 | 0 -> - | 1: 0 (ASSERT) ; 2: approved (any of K, L, M, O, T, Y) (INFER) | needs_client_input |

## Follow-on legs (reversal / void / cancellation ...)

0 of 0 follow-on leg combination(s) run inside a flow test case; 0 not exercised, 0 blocked.
