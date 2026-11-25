# Test cases - BROP BUYPASS ATL105 Terminal & Host Operations

Build `buypass-atl105-mastercard-credit-generated-70fc786d3e`; mapping status **candidate**; merchants: **placeholder**; catalog `config/transaction_patterns.json` None.

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
| BROP_TS1_TC1 | TC-039767 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Device & terminal behaviour: Store and Forward blocking window → no off-line approval inside the window | T11 | - | n/a (no host message) | requires_review |
| BROP_TS1_TC2 | TC-039768 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Device & terminal behaviour: Return/Refund disabled → not offered at the device | T11 | - | n/a (no host message) | ready |
| BROP_TS1_TC3 | TC-039769 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Device & terminal behaviour: Void/Reversal disabled → not offered at the device | T11 | - | n/a (no host message) | ready |
| BROP_TS1_TC4 | TC-039770 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Device & terminal behaviour: Preauthorization disabled → not offered at the device | T11 | - | n/a (no host message) | ready |
| BROP_TS1_TC5 | TC-039771 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Device & terminal behaviour: Before the first successful Table Load every transaction except downline loads i… | T13 | - | n/a (no host message) | ready |
| BROP_TS1_TC6 | TC-039772 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Device & terminal behaviour: Local totals grouped by Card Label; Authorization only and stored value buckets… | T13 | - | n/a (no host message) | ready |

### BROP_TS2 BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BROP_TS2_TC1 | TC-039773 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Totals Request: Totals for the current settlement date and end of day | T13 | 990 | 1: no response expected | ready |
| BROP_TS2_TC2 | TC-039774 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Totals Request: Totals for the most recent date with activity | T13 | 990 | 1: no response expected | ready |
| BROP_TS2_TC3 | TC-039775 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Totals Request: Totals for the second most recent date with activity | T13 | 990 | 1: no response expected | ready |
| BROP_TS2_TC4 | TC-039776 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Totals Request: Totals for the third most recent date with activity | T13 | 990 | 1: no response expected | ready |
| BROP_TS2_TC5 | TC-039777 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Totals Request: Totals since the last clear | T13 | 990 | 1: no response expected | ready |
| BROP_TS2_TC6 | TC-039778 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Totals Request: Clear all totals since the last clear | T13 | 990 | 1: no response expected | ready |
| BROP_TS2_TC7 | TC-039779 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Totals Request: Totals for a date before the third most recent date with activity → declined | T13 | 990 | 1: no response expected | needs_client_input |
| BROP_TS2_TC8 | TC-039780 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Totals Request: Totals with an end-of-day password that does not match the merchant profile → declined | T13 | 990 | 1: no response expected | needs_client_input |
| BROP_TS2_TC9 | TC-039781 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Totals Request: Totals for the current settlement date and end of day declined | T13 | 990 | 1: 1 (FLAG) | needs_client_input |

### BROP_TS3 BUYPASS ATL105 Terminal & Host Operations Totals with Proprietary Data Load Request (Prompt Code 990) (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BROP_TS3_TC1 | TC-039782 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Totals with Proprietary Data Load Request: Totals with the device's card table version and host discount data | T13 | 990 | 1: no response expected | needs_client_input |
| BROP_TS3_TC2 | TC-039783 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Totals with Proprietary Data Load Request: Totals with the device's card table version and host discount data… | T13 | 990 | 1: 1 (FLAG) | needs_client_input |

### BROP_TS4 BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BROP_TS4_TC1 | TC-039784 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Electronic Mail Request: Retrieve nonproprietary electronic mail | T13 | 981 | 1: no response expected | ready |
| BROP_TS4_TC2 | TC-039785 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Electronic Mail Request: Retrieve proprietary electronic mail | T13 | 996 | 1: no response expected | ready |
| BROP_TS4_TC3 | TC-039786 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Electronic Mail Request: Reset the electronic mail status flag | T13 | 997 | 1: no response expected | ready |
| BROP_TS4_TC4 | TC-039787 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Electronic Mail Request: Submit electronic mail | T13 | 995 | 1: no response expected | needs_client_input |
| BROP_TS4_TC5 | TC-039788 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Electronic Mail Request: Retrieve nonproprietary electronic mail declined | T13 | 981 | 1: 1 (FLAG) | needs_client_input |
| BROP_TS4_TC6 | TC-039789 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Electronic Mail Request: Retrieve proprietary electronic mail declined | T13 | 996 | 1: 1 (FLAG) | needs_client_input |
| BROP_TS4_TC7 | TC-039790 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Electronic Mail Request: Reset the electronic mail status flag declined | T13 | 997 | 1: 1 (FLAG) | needs_client_input |
| BROP_TS4_TC8 | TC-039791 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Electronic Mail Request: Submit electronic mail declined | T13 | 995 | 1: 1 (FLAG) | needs_client_input |

### BROP_TS5 BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BROP_TS5_TC1 | TC-039792 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Proprietary Data Load Request: Retrieve custom receipt text | T13 | 901 | 1: no response expected | ready |
| BROP_TS5_TC2 | TC-039793 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Proprietary Data Load Request: Retrieve the dynamic card table | T13 | 902 | 1: no response expected | ready |
| BROP_TS5_TC3 | TC-039794 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Proprietary Data Load Request: Submit site configuration data | T13 | 903 | 1: no response expected | needs_client_input |
| BROP_TS5_TC4 | TC-039795 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Proprietary Data Load Request: Retrieve host discount data after a Totals Response with proprietary data pend… [Purchase/Capture - EMV Contact Chip @ General Retail · attended POS #1] | T13 | 904 | 1: no response expected | needs_client_input |
| BROP_TS5_TC5 | TC-039796 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Proprietary Data Load Request: Submit fuel volume data | T13 | 905 | 1: no response expected | needs_client_input |
| BROP_TS5_TC6 | TC-039797 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Proprietary Data Load Request: Retrieve custom receipt text declined | T13 | 901 | 1: 1 (FLAG) | needs_client_input |
| BROP_TS5_TC7 | TC-039798 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Proprietary Data Load Request: Retrieve the dynamic card table declined | T13 | 902 | 1: 1 (FLAG) | needs_client_input |
| BROP_TS5_TC8 | TC-039799 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Proprietary Data Load Request: Submit site configuration data declined | T13 | 903 | 1: 1 (FLAG) | needs_client_input |
| BROP_TS5_TC9 | TC-039800 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Proprietary Data Load Request: Retrieve host discount data after a Totals Response with proprietary data pend… [Purchase/Capture - EMV Contact Chip @ General Retail · attended POS #2] | T13 | 904 | 1: 1 (FLAG) | needs_client_input |
| BROP_TS5_TC10 | TC-039801 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Proprietary Data Load Request: Submit fuel volume data declined | T13 | 905 | 1: 1 (FLAG) | needs_client_input |

### BROP_TS6 BUYPASS ATL105 Terminal & Host Operations TransArmor Key Load Request (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BROP_TS6_TC1 | TC-039802 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) TransArmor Key Load Request: TransArmor key load | T13 | - | 1: no response expected | needs_client_input |
| BROP_TS6_TC2 | TC-039803 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) TransArmor Key Load Request: TransArmor key load declined | T13 | - | 1: 1 (FLAG) | needs_client_input |

### BROP_TS7 BUYPASS ATL105 Terminal & Host Operations CA Public Key File Load Request (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BROP_TS7_TC1 | TC-039804 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) CA Public Key File Load Request: CA public key file load | T13 | - | 1: no response expected | needs_client_input |
| BROP_TS7_TC2 | TC-039805 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) CA Public Key File Load Request: CA public key file load declined | T13 | - | 1: 1 (FLAG) | needs_client_input |

### BROP_TS8 BUYPASS ATL105 Terminal & Host Operations Communications Test (Transaction Type 900) (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BROP_TS8_TC1 | TC-039806 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Communications Test (Transaction Type 900): Communications test (COMMTEST) | T13 | - | 1: 1 (ASSERT) | ready |

### BROP_TS9 BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BROP_TS9_TC1 | TC-039807 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Downloads (Downline Load Request): Partial Table Load | T13 | - | 1: no response expected | ready |
| BROP_TS9_TC2 | TC-039808 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Downloads (Downline Load Request): Phone Load | T13 | - | 1: no response expected | ready |
| BROP_TS9_TC3 | TC-039809 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Downloads (Downline Load Request): Date and Time Load | T13 | - | 1: no response expected | ready |
| BROP_TS9_TC4 | TC-039810 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Downloads (Downline Load Request): Software Load | T13 | - | 1: no response expected | needs_client_input |
| BROP_TS9_TC5 | TC-039811 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Downloads (Downline Load Request): Moneris Key Load | T13 | - | 1: no response expected | needs_client_input |
| BROP_TS9_TC6 | TC-039812 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Downloads (Downline Load Request): Table Load with the merchant profile load flag… → error message block only | T13 | - | 1: no response expected | needs_client_input |
| BROP_TS9_TC7 | TC-039813 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Downloads (Downline Load Request): Transaction response requests a downl… → device sends a Partial Table Load | T13 | 0 -> - | 1: 0 (ASSERT) ; 2: no response expected | needs_client_input |

## Follow-on legs (reversal / void / cancellation ...)

0 of 0 follow-on leg combination(s) run inside a flow test case; 0 not exercised, 0 blocked.
