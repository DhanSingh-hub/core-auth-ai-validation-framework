# Test cases - BROP BUYPASS ATL105 Terminal & Host Operations

Build `terminal-and-host-operations-45f78fecab`; mapping status **candidate**; merchants: **placeholder**; catalog `config/transaction_patterns.json` None.

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
| Response code tiers | N/A: 6, FLAG: 40, ASSERT: 1 |
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
| BROP_TS1_TC1 | TC-001764 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Device & terminal behaviour: Store and Forward blocking window (card type 173) → no off-line approval inside the window | T11 | - | n/a (no host message) | requires_review |
| BROP_TS1_TC2 | TC-001765 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Device & terminal behaviour: Return/Refund disabled (card type 166) → not offered at the device | T11 | - | n/a (no host message) | ready |
| BROP_TS1_TC3 | TC-001766 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Device & terminal behaviour: Void/Reversal disabled (card type 167) → not offered at the device | T11 | - | n/a (no host message) | ready |
| BROP_TS1_TC4 | TC-001767 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Device & terminal behaviour: Preauthorization disabled (card type 168) → not offered at the device | T11 | - | n/a (no host message) | ready |
| BROP_TS1_TC5 | TC-001768 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Device & terminal behaviour: Before the first successful Table Load every transaction except downline loads is refused at the device | T13 | - | n/a (no host message) | ready |
| BROP_TS1_TC6 | TC-001769 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Device & terminal behaviour: Local totals grouped by Card Label; Authorization only and stored value buckets kept out of the grand total | T13 | - | n/a (no host message) | ready |

### BROP_TS2 BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BROP_TS2_TC1 | TC-001770 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Totals Request (Prompt Code 990): Totals for the current settlement date and end of day (222222) | T13 | 990 | 1: ? (FLAG) | ready |
| BROP_TS2_TC2 | TC-001771 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Totals Request (Prompt Code 990): Totals for the most recent date with activity (333333) | T13 | 990 | 1: ? (FLAG) | ready |
| BROP_TS2_TC3 | TC-001772 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Totals Request (Prompt Code 990): Totals for the second most recent date with activity (444444) | T13 | 990 | 1: ? (FLAG) | ready |
| BROP_TS2_TC4 | TC-001773 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Totals Request (Prompt Code 990): Totals for the third most recent date with activity (555555) | T13 | 990 | 1: ? (FLAG) | ready |
| BROP_TS2_TC5 | TC-001774 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Totals Request (Prompt Code 990): Totals since the last clear (111111) | T13 | 990 | 1: ? (FLAG) | ready |
| BROP_TS2_TC6 | TC-001775 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Totals Request (Prompt Code 990): Clear all totals since the last clear (999999) | T13 | 990 | 1: ? (FLAG) | ready |
| BROP_TS2_TC7 | TC-001776 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Totals Request (Prompt Code 990): Totals for a date before the third most recent date with activity → declined | T13 | 990 | 1: ? (FLAG) | needs_client_input |
| BROP_TS2_TC8 | TC-001777 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Totals Request (Prompt Code 990): Totals with an end-of-day password that does not match the merchant profile → declined | T13 | 990 | 1: ? (FLAG) | needs_client_input |
| BROP_TS2_TC9 | TC-001778 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Totals Request (Prompt Code 990): Totals for the current settlement date and end of day (222222) declined | T13 | 990 | 1: 1 (FLAG) | needs_client_input |

### BROP_TS3 BUYPASS ATL105 Terminal & Host Operations Totals with Proprietary Data Load Request (Prompt Code 990) (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BROP_TS3_TC1 | TC-002812 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Totals with Proprietary Data Load Request (Prompt Code 990): Totals with the device's card table version and host discount data (333333) | T13 | 990 | 1: ? (FLAG) | needs_client_input |
| BROP_TS3_TC2 | TC-002813 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Totals with Proprietary Data Load Request (Prompt Code 990): Totals with the device's card table version and host discount data (333333) declined | T13 | 990 | 1: 1 (FLAG) | needs_client_input |

### BROP_TS4 BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BROP_TS4_TC1 | TC-002814 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Electronic Mail Request: Retrieve nonproprietary electronic mail (981) | T13 | 981 | 1: ? (FLAG) | ready |
| BROP_TS4_TC2 | TC-002815 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Electronic Mail Request: Retrieve proprietary electronic mail (996) | T13 | 996 | 1: ? (FLAG) | ready |
| BROP_TS4_TC3 | TC-002816 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Electronic Mail Request: Reset the electronic mail status flag (997) | T13 | 997 | 1: ? (FLAG) | ready |
| BROP_TS4_TC4 | TC-002817 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Electronic Mail Request: Submit electronic mail (995) | T13 | 995 | 1: ? (FLAG) | needs_client_input |
| BROP_TS4_TC5 | TC-002818 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Electronic Mail Request: Retrieve nonproprietary electronic mail (981) declined | T13 | 981 | 1: 1 (FLAG) | needs_client_input |
| BROP_TS4_TC6 | TC-002819 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Electronic Mail Request: Retrieve proprietary electronic mail (996) declined | T13 | 996 | 1: 1 (FLAG) | needs_client_input |
| BROP_TS4_TC7 | TC-002820 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Electronic Mail Request: Reset the electronic mail status flag (997) declined | T13 | 997 | 1: 1 (FLAG) | needs_client_input |
| BROP_TS4_TC8 | TC-002821 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Electronic Mail Request: Submit electronic mail (995) declined | T13 | 995 | 1: 1 (FLAG) | needs_client_input |

### BROP_TS5 BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BROP_TS5_TC1 | TC-002822 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Proprietary Data Load Request: Retrieve custom receipt text (901) | T13 | 901 | 1: ? (FLAG) | ready |
| BROP_TS5_TC2 | TC-002823 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Proprietary Data Load Request: Retrieve the dynamic card table (902) | T13 | 902 | 1: ? (FLAG) | ready |
| BROP_TS5_TC3 | TC-002824 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Proprietary Data Load Request: Submit site configuration data (903) | T13 | 903 | 1: ? (FLAG) | needs_client_input |
| BROP_TS5_TC4 | TC-002825 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Proprietary Data Load Request: Retrieve host discount data after a Totals Response with proprietary data pending (904) | T13 | 904 | 1: ? (FLAG) | needs_client_input |
| BROP_TS5_TC5 | TC-002826 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Proprietary Data Load Request: Submit fuel volume data (905) | T13 | 905 | 1: ? (FLAG) | needs_client_input |
| BROP_TS5_TC6 | TC-002827 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Proprietary Data Load Request: Retrieve custom receipt text (901) declined | T13 | 901 | 1: 1 (FLAG) | needs_client_input |
| BROP_TS5_TC7 | TC-002828 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Proprietary Data Load Request: Retrieve the dynamic card table (902) declined | T13 | 902 | 1: 1 (FLAG) | needs_client_input |
| BROP_TS5_TC8 | TC-002829 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Proprietary Data Load Request: Submit site configuration data (903) declined | T13 | 903 | 1: 1 (FLAG) | needs_client_input |
| BROP_TS5_TC9 | TC-002830 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Proprietary Data Load Request: Retrieve host discount data after a Totals Response with proprietary data pending (904) declined | T13 | 904 | 1: 1 (FLAG) | needs_client_input |
| BROP_TS5_TC10 | TC-002831 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Proprietary Data Load Request: Submit fuel volume data (905) declined | T13 | 905 | 1: 1 (FLAG) | needs_client_input |

### BROP_TS6 BUYPASS ATL105 Terminal & Host Operations TransArmor Key Load Request (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BROP_TS6_TC1 | TC-001799 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) TransArmor Key Load Request: TransArmor key load | T13 | - | 1: ? (FLAG) | needs_client_input |
| BROP_TS6_TC2 | TC-001800 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) TransArmor Key Load Request: TransArmor key load declined | T13 | - | 1: 1 (FLAG) | needs_client_input |

### BROP_TS7 BUYPASS ATL105 Terminal & Host Operations CA Public Key File Load Request (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BROP_TS7_TC1 | TC-001801 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) CA Public Key File Load Request: CA public key file load | T13 | - | 1: ? (FLAG) | needs_client_input |
| BROP_TS7_TC2 | TC-001802 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) CA Public Key File Load Request: CA public key file load declined | T13 | - | 1: 1 (FLAG) | needs_client_input |

### BROP_TS8 BUYPASS ATL105 Terminal & Host Operations Communications Test (Transaction Type 900) (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BROP_TS8_TC1 | TC-001803 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Communications Test (Transaction Type 900): Communications test (COMMTEST) | T13 | - | 1: 1 (ASSERT) | ready |

### BROP_TS9 BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) (valid)

| TC | ID | Test case | Type | Steps (Transaction Type) | Expected response | Status |
|---|---|---|---|---|---|---|
| BROP_TS9_TC1 | TC-001804 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Downloads (Downline Load Request): Partial Table Load | T13 | - | 1: ? (FLAG) | ready |
| BROP_TS9_TC2 | TC-001805 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Downloads (Downline Load Request): Phone Load | T13 | - | 1: ? (FLAG) | ready |
| BROP_TS9_TC3 | TC-001806 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Downloads (Downline Load Request): Date and Time Load | T13 | - | 1: ? (FLAG) | ready |
| BROP_TS9_TC4 | TC-001807 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Downloads (Downline Load Request): Software Load | T13 | - | 1: ? (FLAG) | needs_client_input |
| BROP_TS9_TC5 | TC-001808 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Downloads (Downline Load Request): Moneris Key Load | T13 | - | 1: ? (FLAG) | needs_client_input |
| BROP_TS9_TC6 | TC-001809 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Downloads (Downline Load Request): Table Load with the merchant profile load flag not set → error message block only | T13 | - | 1: ? (FLAG) | needs_client_input |
| BROP_TS9_TC7 | TC-001810 | BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 (POS PLACEHOLDER) Downloads (Downline Load Request): Transaction response requests a download → device sends a Partial Table Load | T13 | 0 -> - | 1: 0 (ASSERT) ; 2: ? (FLAG) | needs_client_input |

## Follow-on legs (reversal / void / cancellation ...)

0 of 0 follow-on leg combination(s) run inside a flow test case; 0 not exercised, 0 blocked.
