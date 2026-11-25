# Traceability - requirement -> scenario -> test case -> test data

Each business requirement (BR) lists its scenarios; each scenario its test cases; each test case one row per leg with that leg's own test data file. Scenarios of contexts awaiting SME are listed with no test cases. Reverse indexes at the end.

## Summary

| Measure | Count |
|---|---:|
| business requirements | 3 |
| scenarios | 240 |
| test cases | 2700 |
| blocked without test data | 18 |
| legs | 2007 |
| leg files | 2007 |
| missing files | 0 |
| awaiting sme | 96 |
| no request by design | 167 |
| brs without scenarios | 0 |

Gate: every BR has >= 1 scenario; every scenario has test cases or is awaiting SME; every leg has a file that exists. Result: **PASS**

## BUYPASS ATL105 MasterCard Credit (`BR-12B94100`)

Supporting citations: p.511; p.81; p.81; p.81; p.83; p.83; p.83; p.83; p.83; p.83; p.86; p.86; p.86; p.161; p.161; p.161; p.161; p.161; p.162; p.162

### SC-ATL105-6ADB5621 - BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076669 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only → approv | Happy path | Positive | ready | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-076669.json](test_data/TD-TC-076669.json) |
| TC-076670 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Ca | Single rule broken | Negative | requires_review | p.546; p.368; p.83 | 1 | Authorization Only | [TD-TC-076670.step1.auth-only.json](test_data/TD-TC-076670.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076670.step2.cancellation.json](test_data/TD-TC-076670.step2.cancellation.json) |
| TC-076671 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Ca | Single rule broken | Negative | requires_review | p.546; p.368; p.40 | 1 | Authorization Only | [TD-TC-076671.step1.auth-only.json](test_data/TD-TC-076671.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076671.step2.cancellation.json](test_data/TD-TC-076671.step2.cancellation.json) |
| TC-076672 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Pu | Single rule broken | Negative | requires_review | p.545; p.409; p.402 | 1 | Authorization Only | [TD-TC-076672.step1.auth-only.json](test_data/TD-TC-076672.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076672.step2.completion.json](test_data/TD-TC-076672.step2.completion.json) |
| TC-076673 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Pu | Single rule broken | Negative | requires_review | p.545; p.409; p.40 | 1 | Authorization Only | [TD-TC-076673.step1.auth-only.json](test_data/TD-TC-076673.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076673.step2.completion.json](test_data/TD-TC-076673.step2.completion.json) |
| TC-076674 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only with Net | Decision table | Positive | ready | BR-312-2 p.312; p.511; p.83 | 1 | Authorization Only | [TD-TC-076674.json](test_data/TD-TC-076674.json) |
| TC-076675 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Ca | Valid flow | Positive | ready | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-076675.step1.auth-only.json](test_data/TD-TC-076675.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076675.step2.cancellation.json](test_data/TD-TC-076675.step2.cancellation.json) |
| TC-076676 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Pu | Valid flow | Positive | ready | p.545; p.409; BR-402-1 p.402 | 1 | Authorization Only | [TD-TC-076676.step1.auth-only.json](test_data/TD-TC-076676.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076676.step2.completion.json](test_data/TD-TC-076676.step2.completion.json) |
| TC-076677 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Ca | Invalid state transition | Negative | requires_review | p.546; p.511; p.83 | 1 | Authorization Only | [TD-TC-076677.step1.auth-only.json](test_data/TD-TC-076677.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076677.step2.cancellation.json](test_data/TD-TC-076677.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-076677.step3.cancellation.json](test_data/TD-TC-076677.step3.cancellation.json) |
| TC-076678 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Cancellation with no origin | Invalid state transition | Negative | blocked_verification | p.546; p.511; p.83 | 1 | Cancellation | [TD-TC-076678.json](test_data/TD-TC-076678.json) |
| TC-076679 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Ca | Running total / state | Positive | requires_review | p.546; p.368; BR-181-1 p.181 | 1 | Authorization Only | [TD-TC-076679.step1.auth-only.json](test_data/TD-TC-076679.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076679.step2.cancellation.json](test_data/TD-TC-076679.step2.cancellation.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076679.step3.totals.json](test_data/TD-TC-076679.step3.totals.json) |
| TC-076680 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Pu | Running total / state | Positive | requires_review | p.545; p.409; BR-181-1 p.181 | 1 | Authorization Only | [TD-TC-076680.step1.auth-only.json](test_data/TD-TC-076680.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076680.step2.completion.json](test_data/TD-TC-076680.step2.completion.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076680.step3.totals.json](test_data/TD-TC-076680.step3.totals.json) |
| TC-076681 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then To | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Authorization Only | [TD-TC-076681.step1.auth-only.json](test_data/TD-TC-076681.step1.auth-only.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076681.step2.totals.json](test_data/TD-TC-076681.step2.totals.json) |
| TC-076682 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Ca | Response / oracle check | Negative | needs_client_input | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-076682.step1.auth-only.json](test_data/TD-TC-076682.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076682.step2.cancellation.json](test_data/TD-TC-076682.step2.cancellation.json) |
| TC-076683 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only, then Pu | Response / oracle check | Negative | needs_client_input | p.545; p.409; BR-402-1 p.402 | 1 | Authorization Only | [TD-TC-076683.step1.auth-only.json](test_data/TD-TC-076683.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076683.step2.completion.json](test_data/TD-TC-076683.step2.completion.json) |
| TC-076684 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Authorization Only (card is | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-076684.json](test_data/TD-TC-076684.json) |

### SC-ATL105-D5FF2646 - BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback @ Hospitality · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076908 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Chip Fallback Authorization Only → | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-076908.json](test_data/TD-TC-076908.json) |
| TC-076909 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Chip Fallback Authorization Only ( | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-076909.json](test_data/TD-TC-076909.json) |

### SC-ATL105-FDF8FDF0 - BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077014 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Authorization Only → a | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-077014.json](test_data/TD-TC-077014.json) |
| TC-077015 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Authorization Only (ca | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-077015.json](test_data/TD-TC-077015.json) |

### SC-ATL105-16B42CC8 - BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback @ Supermarket · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-A91378A8 - BUYPASS ATL105 MasterCard Credit Authorization Only – Chip Fallback @ Utilities · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-CE3AE8B8 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076637 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Happy path | Positive | ready | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-076637.json](test_data/TD-TC-076637.json) |
| TC-076638 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Single rule broken | Negative | requires_review | p.546; p.368; p.83 | 1 | Authorization Only | [TD-TC-076638.step1.auth-only.json](test_data/TD-TC-076638.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076638.step2.cancellation.json](test_data/TD-TC-076638.step2.cancellation.json) |
| TC-076639 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Single rule broken | Negative | requires_review | p.546; p.368; p.40 | 1 | Authorization Only | [TD-TC-076639.step1.auth-only.json](test_data/TD-TC-076639.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076639.step2.cancellation.json](test_data/TD-TC-076639.step2.cancellation.json) |
| TC-076640 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Single rule broken | Negative | requires_review | p.545; p.409; p.402 | 1 | Authorization Only | [TD-TC-076640.step1.auth-only.json](test_data/TD-TC-076640.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076640.step2.completion.json](test_data/TD-TC-076640.step2.completion.json) |
| TC-076641 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Single rule broken | Negative | requires_review | p.545; p.409; p.40 | 1 | Authorization Only | [TD-TC-076641.step1.auth-only.json](test_data/TD-TC-076641.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076641.step2.completion.json](test_data/TD-TC-076641.step2.completion.json) |
| TC-076642 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Decision table | Positive | ready | BR-312-2 p.312; p.511; p.83 | 1 | Authorization Only | [TD-TC-076642.json](test_data/TD-TC-076642.json) |
| TC-076643 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Valid flow | Positive | ready | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-076643.step1.auth-only.json](test_data/TD-TC-076643.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076643.step2.cancellation.json](test_data/TD-TC-076643.step2.cancellation.json) |
| TC-076644 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Valid flow | Positive | ready | p.545; p.409; BR-402-1 p.402 | 1 | Authorization Only | [TD-TC-076644.step1.auth-only.json](test_data/TD-TC-076644.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076644.step2.completion.json](test_data/TD-TC-076644.step2.completion.json) |
| TC-076645 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Invalid state transition | Negative | requires_review | p.546; p.511; p.83 | 1 | Authorization Only | [TD-TC-076645.step1.auth-only.json](test_data/TD-TC-076645.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076645.step2.cancellation.json](test_data/TD-TC-076645.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-076645.step3.cancellation.json](test_data/TD-TC-076645.step3.cancellation.json) |
| TC-076646 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Cancellation  | Invalid state transition | Negative | blocked_verification | p.546; p.511; p.83 | 1 | Cancellation | [TD-TC-076646.json](test_data/TD-TC-076646.json) |
| TC-076647 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Running total / state | Positive | requires_review | p.546; p.368; BR-181-1 p.181 | 1 | Authorization Only | [TD-TC-076647.step1.auth-only.json](test_data/TD-TC-076647.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076647.step2.cancellation.json](test_data/TD-TC-076647.step2.cancellation.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076647.step3.totals.json](test_data/TD-TC-076647.step3.totals.json) |
| TC-076648 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Running total / state | Positive | requires_review | p.545; p.409; BR-181-1 p.181 | 1 | Authorization Only | [TD-TC-076648.step1.auth-only.json](test_data/TD-TC-076648.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076648.step2.completion.json](test_data/TD-TC-076648.step2.completion.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076648.step3.totals.json](test_data/TD-TC-076648.step3.totals.json) |
| TC-076649 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Authorization Only | [TD-TC-076649.step1.auth-only.json](test_data/TD-TC-076649.step1.auth-only.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076649.step2.totals.json](test_data/TD-TC-076649.step2.totals.json) |
| TC-076650 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Response / oracle check | Negative | needs_client_input | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-076650.step1.auth-only.json](test_data/TD-TC-076650.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076650.step2.cancellation.json](test_data/TD-TC-076650.step2.cancellation.json) |
| TC-076651 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Response / oracle check | Negative | needs_client_input | p.545; p.409; BR-402-1 p.402 | 1 | Authorization Only | [TD-TC-076651.step1.auth-only.json](test_data/TD-TC-076651.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076651.step2.completion.json](test_data/TD-TC-076651.step2.completion.json) |
| TC-076652 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Authorization | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-076652.json](test_data/TD-TC-076652.json) |

### SC-ATL105-FCBF0C27 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ General Retail · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-A4123EFA - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Hospitality · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076904 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Magnetic Stripe Author | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-076904.json](test_data/TD-TC-076904.json) |
| TC-076905 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Magnetic Stripe Author | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-076905.json](test_data/TD-TC-076905.json) |

### SC-ATL105-DB112FC7 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Hospitality · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-95140904 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077010 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Authoriz | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-077010.json](test_data/TD-TC-077010.json) |
| TC-077011 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Authoriz | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-077011.json](test_data/TD-TC-077011.json) |
| TC-077199 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Authoriz | Single rule broken | Negative | needs_client_input | BR-BUYPASS-PIPS-181-2 p.181; p. | 1 |  | [TD-TC-077199.json](test_data/TD-TC-077199.json) |

### SC-ATL105-AB7323EA - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Petroleum · unattended CAT (fuel machine)
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077125 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authoriz | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-077125.json](test_data/TD-TC-077125.json) |
| TC-077126 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authoriz | Valid flow | Positive | needs_client_input | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-077126.step1.auth-only.json](test_data/TD-TC-077126.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-077126.step2.cancellation.json](test_data/TD-TC-077126.step2.cancellation.json) |
| TC-077127 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authoriz | Valid flow | Positive | needs_client_input | p.545; p.409; BR-402-1 p.402 | 1 | Authorization Only | [TD-TC-077127.step1.auth-only.json](test_data/TD-TC-077127.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-077127.step2.completion.json](test_data/TD-TC-077127.step2.completion.json) |
| TC-077128 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authoriz | Invalid state transition | Negative | needs_client_input | p.546; p.511; p.83 | 1 | Authorization Only | [TD-TC-077128.step1.auth-only.json](test_data/TD-TC-077128.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-077128.step2.cancellation.json](test_data/TD-TC-077128.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-077128.step3.cancellation.json](test_data/TD-TC-077128.step3.cancellation.json) |
| TC-077129 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Authoriz | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-077129.json](test_data/TD-TC-077129.json) |

### SC-ATL105-4A9C161D - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Supermarket · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-50E5DBD6 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Supermarket · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-52D5AFE1 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Utilities · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-D52C068B - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Magnetic Stripe @ Utilities · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-ED496ED8 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ General Retail · attended POS
- Status: unconfirmed; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076702 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Happy path | Positive | pending_sme | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-076702.json](test_data/TD-TC-076702.json) |
| TC-076703 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Single rule broken | Negative | pending_sme | p.546; p.368; p.83 | 1 | Authorization Only | [TD-TC-076703.step1.auth-only.json](test_data/TD-TC-076703.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076703.step2.cancellation.json](test_data/TD-TC-076703.step2.cancellation.json) |
| TC-076704 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Single rule broken | Negative | pending_sme | p.546; p.368; p.40 | 1 | Authorization Only | [TD-TC-076704.step1.auth-only.json](test_data/TD-TC-076704.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076704.step2.cancellation.json](test_data/TD-TC-076704.step2.cancellation.json) |
| TC-076705 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Single rule broken | Negative | pending_sme | p.545; p.409; p.402 | 1 | Authorization Only | [TD-TC-076705.step1.auth-only.json](test_data/TD-TC-076705.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076705.step2.completion.json](test_data/TD-TC-076705.step2.completion.json) |
| TC-076706 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Single rule broken | Negative | pending_sme | p.545; p.409; p.40 | 1 | Authorization Only | [TD-TC-076706.step1.auth-only.json](test_data/TD-TC-076706.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076706.step2.completion.json](test_data/TD-TC-076706.step2.completion.json) |
| TC-076707 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Decision table | Positive | pending_sme | BR-312-2 p.312; p.511; p.83 | 1 | Authorization Only | [TD-TC-076707.json](test_data/TD-TC-076707.json) |
| TC-076708 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Valid flow | Positive | pending_sme | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-076708.step1.auth-only.json](test_data/TD-TC-076708.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076708.step2.cancellation.json](test_data/TD-TC-076708.step2.cancellation.json) |
| TC-076709 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Valid flow | Positive | pending_sme | p.545; p.409; BR-402-1 p.402 | 1 | Authorization Only | [TD-TC-076709.step1.auth-only.json](test_data/TD-TC-076709.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076709.step2.completion.json](test_data/TD-TC-076709.step2.completion.json) |
| TC-076710 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Invalid state transition | Negative | pending_sme | p.546; p.511; p.83 | 1 | Authorization Only | [TD-TC-076710.step1.auth-only.json](test_data/TD-TC-076710.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076710.step2.cancellation.json](test_data/TD-TC-076710.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-076710.step3.cancellation.json](test_data/TD-TC-076710.step3.cancellation.json) |
| TC-076711 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Cancel | Invalid state transition | Negative | blocked_verification | p.546; p.511; p.83 | 1 | Cancellation | [TD-TC-076711.json](test_data/TD-TC-076711.json) |
| TC-076712 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Running total / state | Positive | pending_sme | p.546; p.368; BR-181-1 p.181 | 1 | Authorization Only | [TD-TC-076712.step1.auth-only.json](test_data/TD-TC-076712.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076712.step2.cancellation.json](test_data/TD-TC-076712.step2.cancellation.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076712.step3.totals.json](test_data/TD-TC-076712.step3.totals.json) |
| TC-076713 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Running total / state | Positive | pending_sme | p.545; p.409; BR-181-1 p.181 | 1 | Authorization Only | [TD-TC-076713.step1.auth-only.json](test_data/TD-TC-076713.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076713.step2.completion.json](test_data/TD-TC-076713.step2.completion.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076713.step3.totals.json](test_data/TD-TC-076713.step3.totals.json) |
| TC-076714 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Running total / state | Positive | pending_sme | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Authorization Only | [TD-TC-076714.step1.auth-only.json](test_data/TD-TC-076714.step1.auth-only.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076714.step2.totals.json](test_data/TD-TC-076714.step2.totals.json) |
| TC-076715 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Response / oracle check | Negative | pending_sme | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-076715.step1.auth-only.json](test_data/TD-TC-076715.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076715.step2.cancellation.json](test_data/TD-TC-076715.step2.cancellation.json) |
| TC-076716 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Response / oracle check | Negative | pending_sme | p.545; p.409; BR-402-1 p.402 | 1 | Authorization Only | [TD-TC-076716.step1.auth-only.json](test_data/TD-TC-076716.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076716.step2.completion.json](test_data/TD-TC-076716.step2.completion.json) |
| TC-076717 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Author | Response / oracle check | Negative | pending_sme | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-076717.json](test_data/TD-TC-076717.json) |

### SC-ATL105-C18E6DCC - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ General Retail · unattended CAT
- Status: unconfirmed; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-DF6AFFE2 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Hospitality · attended POS
- Status: unconfirmed; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076910 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Mobile Commerce Device | Happy path | Positive | pending_sme | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-076910.json](test_data/TD-TC-076910.json) |
| TC-076911 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Mobile Commerce Device | Response / oracle check | Negative | pending_sme | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-076911.json](test_data/TD-TC-076911.json) |

### SC-ATL105-EDC8B5AD - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Hospitality · unattended CAT
- Status: unconfirmed; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-CAEE4401 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Petroleum · attended POS
- Status: unconfirmed; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077016 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device A | Happy path | Positive | pending_sme | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-077016.json](test_data/TD-TC-077016.json) |
| TC-077017 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device A | Response / oracle check | Negative | pending_sme | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-077017.json](test_data/TD-TC-077017.json) |

### SC-ATL105-21D68F83 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Petroleum · unattended CAT (fuel machine)
- Status: unconfirmed; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077130 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device A | Happy path | Positive | pending_sme | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-077130.json](test_data/TD-TC-077130.json) |
| TC-077131 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device A | Valid flow | Positive | pending_sme | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-077131.step1.auth-only.json](test_data/TD-TC-077131.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-077131.step2.cancellation.json](test_data/TD-TC-077131.step2.cancellation.json) |
| TC-077132 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device A | Valid flow | Positive | pending_sme | p.545; p.409; BR-402-1 p.402 | 1 | Authorization Only | [TD-TC-077132.step1.auth-only.json](test_data/TD-TC-077132.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-077132.step2.completion.json](test_data/TD-TC-077132.step2.completion.json) |
| TC-077133 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device A | Invalid state transition | Negative | pending_sme | p.546; p.511; p.83 | 1 | Authorization Only | [TD-TC-077133.step1.auth-only.json](test_data/TD-TC-077133.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-077133.step2.cancellation.json](test_data/TD-TC-077133.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-077133.step3.cancellation.json](test_data/TD-TC-077133.step3.cancellation.json) |
| TC-077134 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device A | Response / oracle check | Negative | pending_sme | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-077134.json](test_data/TD-TC-077134.json) |

### SC-ATL105-F8DAF225 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Supermarket · attended POS
- Status: unconfirmed; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-9B6B9609 - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Supermarket · unattended CAT
- Status: unconfirmed; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-5E1067FA - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Utilities · attended POS
- Status: unconfirmed; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-A5669F4B - BUYPASS ATL105 MasterCard Credit Authorization Only – Contactless Mobile Commerce Device @ Utilities · unattended CAT
- Status: unconfirmed; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-0DFDDDDF - BUYPASS ATL105 MasterCard Credit Authorization Only – Credential on File @ Credential on File · customer present; card not present
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076685 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only → a | Happy path | Positive | ready | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-076685.json](test_data/TD-TC-076685.json) |
| TC-076686 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only → a | Happy path | Positive | ready | BR-83-1 p.83; BR-ATL105-TRANSARMOR-76-5 p.76; p.511 | 1 | Authorization Only | [TD-TC-076686.json](test_data/TD-TC-076686.json) |
| TC-076687 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Single rule broken | Negative | requires_review | p.546; p.368; p.83 | 1 | Authorization Only | [TD-TC-076687.step1.auth-only.json](test_data/TD-TC-076687.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076687.step2.cancellation.json](test_data/TD-TC-076687.step2.cancellation.json) |
| TC-076688 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Single rule broken | Negative | requires_review | p.546; p.368; p.40 | 1 | Authorization Only | [TD-TC-076688.step1.auth-only.json](test_data/TD-TC-076688.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076688.step2.cancellation.json](test_data/TD-TC-076688.step2.cancellation.json) |
| TC-076689 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Single rule broken | Negative | requires_review | p.545; p.409; p.402 | 1 | Authorization Only | [TD-TC-076689.step1.auth-only.json](test_data/TD-TC-076689.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076689.step2.completion.json](test_data/TD-TC-076689.step2.completion.json) |
| TC-076690 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Single rule broken | Negative | requires_review | p.545; p.409; p.40 | 1 | Authorization Only | [TD-TC-076690.step1.auth-only.json](test_data/TD-TC-076690.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076690.step2.completion.json](test_data/TD-TC-076690.step2.completion.json) |
| TC-076691 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only wit | Decision table | Positive | ready | BR-312-2 p.312; p.511; p.83 | 1 | Authorization Only | [TD-TC-076691.json](test_data/TD-TC-076691.json) |
| TC-076692 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Valid flow | Positive | ready | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-076692.step1.auth-only.json](test_data/TD-TC-076692.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076692.step2.cancellation.json](test_data/TD-TC-076692.step2.cancellation.json) |
| TC-076693 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Valid flow | Positive | ready | p.545; p.409; BR-402-1 p.402 | 1 | Authorization Only | [TD-TC-076693.step1.auth-only.json](test_data/TD-TC-076693.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076693.step2.completion.json](test_data/TD-TC-076693.step2.completion.json) |
| TC-076694 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Invalid state transition | Negative | requires_review | p.546; p.511; p.83 | 1 | Authorization Only | [TD-TC-076694.step1.auth-only.json](test_data/TD-TC-076694.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076694.step2.cancellation.json](test_data/TD-TC-076694.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-076694.step3.cancellation.json](test_data/TD-TC-076694.step3.cancellation.json) |
| TC-076695 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Cancellation with no o | Invalid state transition | Negative | blocked_verification | p.546; p.511; p.83 | 1 | Cancellation | [TD-TC-076695.json](test_data/TD-TC-076695.json) |
| TC-076696 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Running total / state | Positive | requires_review | p.546; p.368; BR-181-1 p.181 | 1 | Authorization Only | [TD-TC-076696.step1.auth-only.json](test_data/TD-TC-076696.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076696.step2.cancellation.json](test_data/TD-TC-076696.step2.cancellation.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076696.step3.totals.json](test_data/TD-TC-076696.step3.totals.json) |
| TC-076697 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Running total / state | Positive | requires_review | p.545; p.409; BR-181-1 p.181 | 1 | Authorization Only | [TD-TC-076697.step1.auth-only.json](test_data/TD-TC-076697.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076697.step2.completion.json](test_data/TD-TC-076697.step2.completion.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076697.step3.totals.json](test_data/TD-TC-076697.step3.totals.json) |
| TC-076698 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Authorization Only | [TD-TC-076698.step1.auth-only.json](test_data/TD-TC-076698.step1.auth-only.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076698.step2.totals.json](test_data/TD-TC-076698.step2.totals.json) |
| TC-076699 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Response / oracle check | Negative | needs_client_input | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-076699.step1.auth-only.json](test_data/TD-TC-076699.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076699.step2.cancellation.json](test_data/TD-TC-076699.step2.cancellation.json) |
| TC-076700 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only, th | Response / oracle check | Negative | needs_client_input | p.545; p.409; BR-402-1 p.402 | 1 | Authorization Only | [TD-TC-076700.step1.auth-only.json](test_data/TD-TC-076700.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076700.step2.completion.json](test_data/TD-TC-076700.step2.completion.json) |
| TC-076701 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only (ca | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-076701.json](test_data/TD-TC-076701.json) |
| TC-077184 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Authorization Only CAV | Single rule broken | Negative | ready | BR-486-4 p.486; p. | 1 |  | [TD-TC-077184.json](test_data/TD-TC-077184.json) |

### SC-ATL105-48F4A035 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076586 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only → app | Happy path | Positive | ready | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-076586.json](test_data/TD-TC-076586.json) |
| TC-076587 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Single rule broken | Negative | requires_review | p.546; p.368; p.83 | 1 | Authorization Only | [TD-TC-076587.step1.auth-only.json](test_data/TD-TC-076587.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076587.step2.cancellation.json](test_data/TD-TC-076587.step2.cancellation.json) |
| TC-076588 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Single rule broken | Negative | requires_review | p.546; p.368; p.40 | 1 | Authorization Only | [TD-TC-076588.step1.auth-only.json](test_data/TD-TC-076588.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076588.step2.cancellation.json](test_data/TD-TC-076588.step2.cancellation.json) |
| TC-076589 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Single rule broken | Negative | requires_review | p.545; p.409; p.402 | 1 | Authorization Only | [TD-TC-076589.step1.auth-only.json](test_data/TD-TC-076589.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076589.step2.completion.json](test_data/TD-TC-076589.step2.completion.json) |
| TC-076590 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Single rule broken | Negative | requires_review | p.545; p.409; p.40 | 1 | Authorization Only | [TD-TC-076590.step1.auth-only.json](test_data/TD-TC-076590.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076590.step2.completion.json](test_data/TD-TC-076590.step2.completion.json) |
| TC-076591 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only with  | Decision table | Positive | ready | BR-312-2 p.312; p.511; p.83 | 1 | Authorization Only | [TD-TC-076591.json](test_data/TD-TC-076591.json) |
| TC-076592 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Valid flow | Positive | ready | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-076592.step1.auth-only.json](test_data/TD-TC-076592.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076592.step2.cancellation.json](test_data/TD-TC-076592.step2.cancellation.json) |
| TC-076593 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Valid flow | Positive | ready | p.545; p.409; BR-402-1 p.402 | 1 | Authorization Only | [TD-TC-076593.step1.auth-only.json](test_data/TD-TC-076593.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076593.step2.completion.json](test_data/TD-TC-076593.step2.completion.json) |
| TC-076594 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Invalid state transition | Negative | requires_review | p.546; p.511; p.83 | 1 | Authorization Only | [TD-TC-076594.step1.auth-only.json](test_data/TD-TC-076594.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076594.step2.cancellation.json](test_data/TD-TC-076594.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-076594.step3.cancellation.json](test_data/TD-TC-076594.step3.cancellation.json) |
| TC-076595 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Cancellation with no ori | Invalid state transition | Negative | requires_review | p.546; p.511; p.83 | 1 | Cancellation | [TD-TC-076595.json](test_data/TD-TC-076595.json) |
| TC-076596 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Running total / state | Positive | requires_review | p.546; p.368; BR-181-1 p.181 | 1 | Authorization Only | [TD-TC-076596.step1.auth-only.json](test_data/TD-TC-076596.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076596.step2.cancellation.json](test_data/TD-TC-076596.step2.cancellation.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076596.step3.totals.json](test_data/TD-TC-076596.step3.totals.json) |
| TC-076597 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Running total / state | Positive | requires_review | p.545; p.409; BR-181-1 p.181 | 1 | Authorization Only | [TD-TC-076597.step1.auth-only.json](test_data/TD-TC-076597.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076597.step2.completion.json](test_data/TD-TC-076597.step2.completion.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076597.step3.totals.json](test_data/TD-TC-076597.step3.totals.json) |
| TC-076598 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Authorization Only | [TD-TC-076598.step1.auth-only.json](test_data/TD-TC-076598.step1.auth-only.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076598.step2.totals.json](test_data/TD-TC-076598.step2.totals.json) |
| TC-076599 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only on a  | Interaction (pairwise) | Positive | ready | p.545; BR-83-2 p.83; BR-FISERV-EMV-GUIDE-49-2 p.49 | 1 | Authorization Only | [TD-TC-076599.json](test_data/TD-TC-076599.json) |
| TC-076600 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Response / oracle check | Negative | needs_client_input | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-076600.step1.auth-only.json](test_data/TD-TC-076600.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076600.step2.cancellation.json](test_data/TD-TC-076600.step2.cancellation.json) |
| TC-076601 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only, then | Response / oracle check | Negative | needs_client_input | p.545; p.409; BR-402-1 p.402 | 1 | Authorization Only | [TD-TC-076601.step1.auth-only.json](test_data/TD-TC-076601.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076601.step2.completion.json](test_data/TD-TC-076601.step2.completion.json) |
| TC-076602 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only (card | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-076602.json](test_data/TD-TC-076602.json) |
| TC-077177 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only Parti | Single rule broken | Negative | ready | BR-89-3 p.89; BR-89-5 p.89; BR-89-10 p.89 | 1 |  | [TD-TC-077177.json](test_data/TD-TC-077177.json) |
| TC-077186 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only Requi | Single rule broken | Negative | ready | BR-639-2 p.639; BR-FISERV-EMV-GUIDE-42-1 p.42; p. | 1 |  | [TD-TC-077186.json](test_data/TD-TC-077186.json) |
| TC-077216 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only Ship- | Single rule broken | Negative | ready | BR-FISERV-EMV-GUIDE-186-8 p.186; p. | 1 |  | [TD-TC-077216.json](test_data/TD-TC-077216.json) |
| TC-077220 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Authorization Only Varia | Single rule broken | Negative | ready | BR-149-1 p.149; BR-146-2 p.146; BR-147-1 p.147 | 1 |  | [TD-TC-077220.json](test_data/TD-TC-077220.json) |

### SC-ATL105-5ED2613B - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ General Retail · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-BC90B09B - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Hospitality · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076898 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contact Chip Authorization Onl | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-076898.json](test_data/TD-TC-076898.json) |
| TC-076899 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contact Chip Authorization Onl | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-076899.json](test_data/TD-TC-076899.json) |

### SC-ATL105-99B31463 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Hospitality · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-677D33AA - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077004 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Authorization Only  | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-077004.json](test_data/TD-TC-077004.json) |
| TC-077005 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Authorization Only  | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-077005.json](test_data/TD-TC-077005.json) |
| TC-077197 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Authorization Only  | Single rule broken | Boundary | needs_client_input | BR-BUYPASS-PIPS-179-1 p.179; p. | 1 |  | [TD-TC-077197.json](test_data/TD-TC-077197.json) |
| TC-077204 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Authorization Only  | Single rule broken | Negative | needs_client_input | BR-BUYPASS-PIPS-16-3 p.16; p. | 1 |  | [TD-TC-077204.json](test_data/TD-TC-077204.json) |

### SC-ATL105-A210C900 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Petroleum · unattended CAT (fuel machine)
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077110 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only  | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-077110.json](test_data/TD-TC-077110.json) |
| TC-077111 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only, | Valid flow | Positive | needs_client_input | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-077111.step1.auth-only.json](test_data/TD-TC-077111.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-077111.step2.cancellation.json](test_data/TD-TC-077111.step2.cancellation.json) |
| TC-077112 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only, | Valid flow | Positive | needs_client_input | p.545; p.409; BR-402-1 p.402 | 1 | Authorization Only | [TD-TC-077112.step1.auth-only.json](test_data/TD-TC-077112.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-077112.step2.completion.json](test_data/TD-TC-077112.step2.completion.json) |
| TC-077113 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only, | Invalid state transition | Negative | needs_client_input | p.546; p.511; p.83 | 1 | Authorization Only | [TD-TC-077113.step1.auth-only.json](test_data/TD-TC-077113.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-077113.step2.cancellation.json](test_data/TD-TC-077113.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-077113.step3.cancellation.json](test_data/TD-TC-077113.step3.cancellation.json) |
| TC-077114 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only  | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-077114.json](test_data/TD-TC-077114.json) |
| TC-077201 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Authorization Only  | Single rule broken | Negative | needs_client_input | BR-BUYPASS-PIPS-185-2 p.185; BR-FISERV-EMV-GUIDE-178-4 p.178; p. | 1 |  | [TD-TC-077201.json](test_data/TD-TC-077201.json) |

### SC-ATL105-9D599CDE - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Supermarket · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-EC6265FA - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Supermarket · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-79111A1A - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Utilities · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-A86DCAB0 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contact Chip @ Utilities · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-6FE1F1D1 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076603 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only → | Happy path | Positive | ready | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-076603.json](test_data/TD-TC-076603.json) |
| TC-076604 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Single rule broken | Negative | requires_review | p.546; p.368; p.83 | 1 | Authorization Only | [TD-TC-076604.step1.auth-only.json](test_data/TD-TC-076604.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076604.step2.cancellation.json](test_data/TD-TC-076604.step2.cancellation.json) |
| TC-076605 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Single rule broken | Negative | requires_review | p.546; p.368; p.40 | 1 | Authorization Only | [TD-TC-076605.step1.auth-only.json](test_data/TD-TC-076605.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076605.step2.cancellation.json](test_data/TD-TC-076605.step2.cancellation.json) |
| TC-076606 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Single rule broken | Negative | requires_review | p.545; p.409; p.402 | 1 | Authorization Only | [TD-TC-076606.step1.auth-only.json](test_data/TD-TC-076606.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076606.step2.completion.json](test_data/TD-TC-076606.step2.completion.json) |
| TC-076607 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Single rule broken | Negative | requires_review | p.545; p.409; p.40 | 1 | Authorization Only | [TD-TC-076607.step1.auth-only.json](test_data/TD-TC-076607.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076607.step2.completion.json](test_data/TD-TC-076607.step2.completion.json) |
| TC-076608 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only w | Decision table | Positive | ready | BR-312-2 p.312; p.511; p.83 | 1 | Authorization Only | [TD-TC-076608.json](test_data/TD-TC-076608.json) |
| TC-076609 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Valid flow | Positive | ready | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-076609.step1.auth-only.json](test_data/TD-TC-076609.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076609.step2.cancellation.json](test_data/TD-TC-076609.step2.cancellation.json) |
| TC-076610 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Valid flow | Positive | ready | p.545; p.409; BR-402-1 p.402 | 1 | Authorization Only | [TD-TC-076610.step1.auth-only.json](test_data/TD-TC-076610.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076610.step2.completion.json](test_data/TD-TC-076610.step2.completion.json) |
| TC-076611 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Invalid state transition | Negative | requires_review | p.546; p.511; p.83 | 1 | Authorization Only | [TD-TC-076611.step1.auth-only.json](test_data/TD-TC-076611.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076611.step2.cancellation.json](test_data/TD-TC-076611.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-076611.step3.cancellation.json](test_data/TD-TC-076611.step3.cancellation.json) |
| TC-076612 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Cancellation with no | Invalid state transition | Negative | blocked_verification | p.546; p.511; p.83 | 1 | Cancellation | [TD-TC-076612.json](test_data/TD-TC-076612.json) |
| TC-076613 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Running total / state | Positive | requires_review | p.546; p.368; BR-181-1 p.181 | 1 | Authorization Only | [TD-TC-076613.step1.auth-only.json](test_data/TD-TC-076613.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076613.step2.cancellation.json](test_data/TD-TC-076613.step2.cancellation.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076613.step3.totals.json](test_data/TD-TC-076613.step3.totals.json) |
| TC-076614 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Running total / state | Positive | requires_review | p.545; p.409; BR-181-1 p.181 | 1 | Authorization Only | [TD-TC-076614.step1.auth-only.json](test_data/TD-TC-076614.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076614.step2.completion.json](test_data/TD-TC-076614.step2.completion.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076614.step3.totals.json](test_data/TD-TC-076614.step3.totals.json) |
| TC-076615 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Authorization Only | [TD-TC-076615.step1.auth-only.json](test_data/TD-TC-076615.step1.auth-only.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076615.step2.totals.json](test_data/TD-TC-076615.step2.totals.json) |
| TC-076616 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only o | Interaction (pairwise) | Positive | ready | p.545; BR-83-2 p.83; BR-FISERV-EMV-GUIDE-49-2 p.49 | 1 | Authorization Only | [TD-TC-076616.json](test_data/TD-TC-076616.json) |
| TC-076617 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Response / oracle check | Negative | needs_client_input | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-076617.step1.auth-only.json](test_data/TD-TC-076617.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076617.step2.cancellation.json](test_data/TD-TC-076617.step2.cancellation.json) |
| TC-076618 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only,  | Response / oracle check | Negative | needs_client_input | p.545; p.409; BR-402-1 p.402 | 1 | Authorization Only | [TD-TC-076618.step1.auth-only.json](test_data/TD-TC-076618.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076618.step2.completion.json](test_data/TD-TC-076618.step2.completion.json) |
| TC-076619 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only ( | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-076619.json](test_data/TD-TC-076619.json) |
| TC-077178 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only P | Single rule broken | Negative | ready | BR-89-3 p.89; p. | 1 |  | [TD-TC-077178.json](test_data/TD-TC-077178.json) |
| TC-077215 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Authorization Only S | Single rule broken | Negative | ready | BR-FISERV-EMV-GUIDE-186-8 p.186; p. | 1 |  | [TD-TC-077215.json](test_data/TD-TC-077215.json) |

### SC-ATL105-70B0E17B - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ General Retail · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-4E91C0CE - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Hospitality · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076900 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contactless Chip Authorization | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-076900.json](test_data/TD-TC-076900.json) |
| TC-076901 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contactless Chip Authorization | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-076901.json](test_data/TD-TC-076901.json) |

### SC-ATL105-86BADD94 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Hospitality · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-7BE85284 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077006 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Authorization O | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-077006.json](test_data/TD-TC-077006.json) |
| TC-077007 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Authorization O | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-077007.json](test_data/TD-TC-077007.json) |

### SC-ATL105-C1D2F2DB - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Petroleum · unattended CAT (fuel machine)
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077115 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization O | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-077115.json](test_data/TD-TC-077115.json) |
| TC-077116 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization O | Valid flow | Positive | needs_client_input | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-077116.step1.auth-only.json](test_data/TD-TC-077116.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-077116.step2.cancellation.json](test_data/TD-TC-077116.step2.cancellation.json) |
| TC-077117 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization O | Valid flow | Positive | needs_client_input | p.545; p.409; BR-402-1 p.402 | 1 | Authorization Only | [TD-TC-077117.step1.auth-only.json](test_data/TD-TC-077117.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-077117.step2.completion.json](test_data/TD-TC-077117.step2.completion.json) |
| TC-077118 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization O | Invalid state transition | Negative | needs_client_input | p.546; p.511; p.83 | 1 | Authorization Only | [TD-TC-077118.step1.auth-only.json](test_data/TD-TC-077118.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-077118.step2.cancellation.json](test_data/TD-TC-077118.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-077118.step3.cancellation.json](test_data/TD-TC-077118.step3.cancellation.json) |
| TC-077119 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization O | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-077119.json](test_data/TD-TC-077119.json) |
| TC-077194 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Authorization O | Single rule broken | Negative | needs_client_input | BR-BUYPASS-PIPS-130-3 p.130; BR-BUYPASS-PIPS-140-1 p.140; p. | 1 |  | [TD-TC-077194.json](test_data/TD-TC-077194.json) |

### SC-ATL105-9CF93F41 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Supermarket · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-2263784C - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Supermarket · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-BF0048E4 - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Utilities · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-616A6FCD - BUYPASS ATL105 MasterCard Credit Authorization Only – EMV Contactless Chip @ Utilities · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-76F82FFD - BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076620 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only → appr | Happy path | Positive | ready | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-076620.json](test_data/TD-TC-076620.json) |
| TC-076621 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Single rule broken | Negative | requires_review | p.546; p.368; p.83 | 1 | Authorization Only | [TD-TC-076621.step1.auth-only.json](test_data/TD-TC-076621.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076621.step2.cancellation.json](test_data/TD-TC-076621.step2.cancellation.json) |
| TC-076622 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Single rule broken | Negative | requires_review | p.546; p.368; p.40 | 1 | Authorization Only | [TD-TC-076622.step1.auth-only.json](test_data/TD-TC-076622.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076622.step2.cancellation.json](test_data/TD-TC-076622.step2.cancellation.json) |
| TC-076623 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Single rule broken | Negative | requires_review | p.545; p.409; p.402 | 1 | Authorization Only | [TD-TC-076623.step1.auth-only.json](test_data/TD-TC-076623.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076623.step2.completion.json](test_data/TD-TC-076623.step2.completion.json) |
| TC-076624 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Single rule broken | Negative | requires_review | p.545; p.409; p.40 | 1 | Authorization Only | [TD-TC-076624.step1.auth-only.json](test_data/TD-TC-076624.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076624.step2.completion.json](test_data/TD-TC-076624.step2.completion.json) |
| TC-076625 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only with N | Decision table | Positive | ready | BR-312-2 p.312; p.511; p.83 | 1 | Authorization Only | [TD-TC-076625.json](test_data/TD-TC-076625.json) |
| TC-076626 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Valid flow | Positive | ready | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-076626.step1.auth-only.json](test_data/TD-TC-076626.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076626.step2.cancellation.json](test_data/TD-TC-076626.step2.cancellation.json) |
| TC-076627 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Valid flow | Positive | ready | p.545; p.409; BR-402-1 p.402 | 1 | Authorization Only | [TD-TC-076627.step1.auth-only.json](test_data/TD-TC-076627.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076627.step2.completion.json](test_data/TD-TC-076627.step2.completion.json) |
| TC-076628 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Invalid state transition | Negative | requires_review | p.546; p.511; p.83 | 1 | Authorization Only | [TD-TC-076628.step1.auth-only.json](test_data/TD-TC-076628.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076628.step2.cancellation.json](test_data/TD-TC-076628.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-076628.step3.cancellation.json](test_data/TD-TC-076628.step3.cancellation.json) |
| TC-076629 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Cancellation with no orig | Invalid state transition | Negative | blocked_verification | p.546; p.511; p.83 | 1 | Cancellation | [TD-TC-076629.json](test_data/TD-TC-076629.json) |
| TC-076630 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Running total / state | Positive | requires_review | p.546; p.368; BR-181-1 p.181 | 1 | Authorization Only | [TD-TC-076630.step1.auth-only.json](test_data/TD-TC-076630.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076630.step2.cancellation.json](test_data/TD-TC-076630.step2.cancellation.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076630.step3.totals.json](test_data/TD-TC-076630.step3.totals.json) |
| TC-076631 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Running total / state | Positive | requires_review | p.545; p.409; BR-181-1 p.181 | 1 | Authorization Only | [TD-TC-076631.step1.auth-only.json](test_data/TD-TC-076631.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076631.step2.completion.json](test_data/TD-TC-076631.step2.completion.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076631.step3.totals.json](test_data/TD-TC-076631.step3.totals.json) |
| TC-076632 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Authorization Only | [TD-TC-076632.step1.auth-only.json](test_data/TD-TC-076632.step1.auth-only.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076632.step2.totals.json](test_data/TD-TC-076632.step2.totals.json) |
| TC-076633 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for ' | Running total / state | Positive | requires_review | BR-72-2 p.72; p.511; p.83 | 1 |  | - |
| TC-076634 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Response / oracle check | Negative | needs_client_input | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-076634.step1.auth-only.json](test_data/TD-TC-076634.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076634.step2.cancellation.json](test_data/TD-TC-076634.step2.cancellation.json) |
| TC-076635 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only, then  | Response / oracle check | Negative | needs_client_input | p.545; p.409; BR-402-1 p.402 | 1 | Authorization Only | [TD-TC-076635.step1.auth-only.json](test_data/TD-TC-076635.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076635.step2.completion.json](test_data/TD-TC-076635.step2.completion.json) |
| TC-076636 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Authorization Only (card  | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-076636.json](test_data/TD-TC-076636.json) |

### SC-ATL105-F651389F - BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ General Retail · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-DE8E9D65 - BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Hospitality · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076902 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Magnetic Stripe Authorization Only | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-076902.json](test_data/TD-TC-076902.json) |
| TC-076903 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Magnetic Stripe Authorization Only | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-076903.json](test_data/TD-TC-076903.json) |

### SC-ATL105-F6F1E237 - BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Hospitality · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-97DE7FC1 - BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077008 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Authorization Only → | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-077008.json](test_data/TD-TC-077008.json) |
| TC-077009 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Authorization Only ( | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-077009.json](test_data/TD-TC-077009.json) |

### SC-ATL105-B261473D - BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Petroleum · unattended CAT (fuel machine)
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077120 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only → | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-077120.json](test_data/TD-TC-077120.json) |
| TC-077121 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only,  | Valid flow | Positive | needs_client_input | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-077121.step1.auth-only.json](test_data/TD-TC-077121.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-077121.step2.cancellation.json](test_data/TD-TC-077121.step2.cancellation.json) |
| TC-077122 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only,  | Valid flow | Positive | needs_client_input | p.545; p.409; BR-402-1 p.402 | 1 | Authorization Only | [TD-TC-077122.step1.auth-only.json](test_data/TD-TC-077122.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-077122.step2.completion.json](test_data/TD-TC-077122.step2.completion.json) |
| TC-077123 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only,  | Invalid state transition | Negative | needs_client_input | p.546; p.511; p.83 | 1 | Authorization Only | [TD-TC-077123.step1.auth-only.json](test_data/TD-TC-077123.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-077123.step2.cancellation.json](test_data/TD-TC-077123.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-077123.step3.cancellation.json](test_data/TD-TC-077123.step3.cancellation.json) |
| TC-077124 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only ( | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-077124.json](test_data/TD-TC-077124.json) |
| TC-077193 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Authorization Only R | Single rule broken | Negative | needs_client_input | BR-BUYPASS-PIPS-130-3 p.130; BR-BUYPASS-PIPS-140-1 p.140; p. | 1 |  | [TD-TC-077193.json](test_data/TD-TC-077193.json) |

### SC-ATL105-557CF714 - BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Supermarket · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-A905C80E - BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Supermarket · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-580E8706 - BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Utilities · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-113E712E - BUYPASS ATL105 MasterCard Credit Authorization Only – Magnetic Stripe @ Utilities · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-6120DA43 - BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076653 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only → appro | Happy path | Positive | ready | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-076653.json](test_data/TD-TC-076653.json) |
| TC-076654 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then C | Single rule broken | Negative | requires_review | p.546; p.368; p.83 | 1 | Authorization Only | [TD-TC-076654.step1.auth-only.json](test_data/TD-TC-076654.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076654.step2.cancellation.json](test_data/TD-TC-076654.step2.cancellation.json) |
| TC-076655 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then C | Single rule broken | Negative | requires_review | p.546; p.368; p.40 | 1 | Authorization Only | [TD-TC-076655.step1.auth-only.json](test_data/TD-TC-076655.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076655.step2.cancellation.json](test_data/TD-TC-076655.step2.cancellation.json) |
| TC-076656 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then P | Single rule broken | Negative | requires_review | p.545; p.409; p.402 | 1 | Authorization Only | [TD-TC-076656.step1.auth-only.json](test_data/TD-TC-076656.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076656.step2.completion.json](test_data/TD-TC-076656.step2.completion.json) |
| TC-076657 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then P | Single rule broken | Negative | requires_review | p.545; p.409; p.40 | 1 | Authorization Only | [TD-TC-076657.step1.auth-only.json](test_data/TD-TC-076657.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076657.step2.completion.json](test_data/TD-TC-076657.step2.completion.json) |
| TC-076658 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only with Ne | Decision table | Positive | ready | BR-312-2 p.312; p.511; p.83 | 1 | Authorization Only | [TD-TC-076658.json](test_data/TD-TC-076658.json) |
| TC-076659 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then C | Valid flow | Positive | ready | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-076659.step1.auth-only.json](test_data/TD-TC-076659.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076659.step2.cancellation.json](test_data/TD-TC-076659.step2.cancellation.json) |
| TC-076660 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then P | Valid flow | Positive | ready | p.545; p.409; BR-402-1 p.402 | 1 | Authorization Only | [TD-TC-076660.step1.auth-only.json](test_data/TD-TC-076660.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076660.step2.completion.json](test_data/TD-TC-076660.step2.completion.json) |
| TC-076661 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then C | Invalid state transition | Negative | requires_review | p.546; p.511; p.83 | 1 | Authorization Only | [TD-TC-076661.step1.auth-only.json](test_data/TD-TC-076661.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076661.step2.cancellation.json](test_data/TD-TC-076661.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-076661.step3.cancellation.json](test_data/TD-TC-076661.step3.cancellation.json) |
| TC-076662 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Cancellation with no origi | Invalid state transition | Negative | blocked_verification | p.546; p.511; p.83 | 1 | Cancellation | [TD-TC-076662.json](test_data/TD-TC-076662.json) |
| TC-076663 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then C | Running total / state | Positive | requires_review | p.546; p.368; BR-181-1 p.181 | 1 | Authorization Only | [TD-TC-076663.step1.auth-only.json](test_data/TD-TC-076663.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076663.step2.cancellation.json](test_data/TD-TC-076663.step2.cancellation.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076663.step3.totals.json](test_data/TD-TC-076663.step3.totals.json) |
| TC-076664 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then P | Running total / state | Positive | requires_review | p.545; p.409; BR-181-1 p.181 | 1 | Authorization Only | [TD-TC-076664.step1.auth-only.json](test_data/TD-TC-076664.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076664.step2.completion.json](test_data/TD-TC-076664.step2.completion.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076664.step3.totals.json](test_data/TD-TC-076664.step3.totals.json) |
| TC-076665 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then T | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Authorization Only | [TD-TC-076665.step1.auth-only.json](test_data/TD-TC-076665.step1.auth-only.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076665.step2.totals.json](test_data/TD-TC-076665.step2.totals.json) |
| TC-076666 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then C | Response / oracle check | Negative | needs_client_input | p.546; p.368; BR-85-2 p.85 | 1 | Authorization Only | [TD-TC-076666.step1.auth-only.json](test_data/TD-TC-076666.step1.auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076666.step2.cancellation.json](test_data/TD-TC-076666.step2.cancellation.json) |
| TC-076667 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only, then P | Response / oracle check | Negative | needs_client_input | p.545; p.409; BR-402-1 p.402 | 1 | Authorization Only | [TD-TC-076667.step1.auth-only.json](test_data/TD-TC-076667.step1.auth-only.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076667.step2.completion.json](test_data/TD-TC-076667.step2.completion.json) |
| TC-076668 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only (card i | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-076668.json](test_data/TD-TC-076668.json) |
| TC-077191 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Authorization Only Token R | Single rule broken | Boundary | ready | BR-ATL105-TRANSARMOR-46-5 p.46; BR-ATL105-TRANSARMOR-48-5 p.48; BR-ATL105-TRANSARMOR-49-5 p.49 | 1 |  | [TD-TC-077191.json](test_data/TD-TC-077191.json) |

### SC-ATL105-D552FDE1 - BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed @ Hospitality · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076906 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Manual / Keyed Authorization Only  | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-076906.json](test_data/TD-TC-076906.json) |
| TC-076907 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Manual / Keyed Authorization Only  | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-076907.json](test_data/TD-TC-076907.json) |

### SC-ATL105-37350FC3 - BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077012 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Authorization Only →  | Happy path | Positive | needs_client_input | p.545; BR-66-2 p.66; BR-66-3 p.66 | 1 | Authorization Only | [TD-TC-077012.json](test_data/TD-TC-077012.json) |
| TC-077013 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Authorization Only (c | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Authorization Only | [TD-TC-077013.json](test_data/TD-TC-077013.json) |

### SC-ATL105-41CAD4E4 - BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed @ Supermarket · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-D3D1E256 - BUYPASS ATL105 MasterCard Credit Authorization Only – Manual / Keyed @ Utilities · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-2F941EC7 - BUYPASS ATL105 MasterCard Credit Balance Inquiry – Chip Fallback @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076873 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Balance Inquiry → approved | Happy path | Positive | ready | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-076873.json](test_data/TD-TC-076873.json) |
| TC-076874 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Balance Inquiry, then Total | Running total / state | Positive | requires_review | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Balance Inquiry | [TD-TC-076874.step1.balance-inquiry.json](test_data/TD-TC-076874.step1.balance-inquiry.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076874.step2.totals.json](test_data/TD-TC-076874.step2.totals.json) |
| TC-076875 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Balance Inquiry (card issue | Response / oracle check | Negative | needs_client_input | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-076875.json](test_data/TD-TC-076875.json) |

### SC-ATL105-49C2231B - BUYPASS ATL105 MasterCard Credit Balance Inquiry – Chip Fallback @ Supermarket · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-A6400475 - BUYPASS ATL105 MasterCard Credit Balance Inquiry – Chip Fallback @ Utilities · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-7645B626 - BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Magnetic Stripe @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076867 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Balance Inqui | Happy path | Positive | ready | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-076867.json](test_data/TD-TC-076867.json) |
| TC-076868 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Balance Inqui | Running total / state | Positive | requires_review | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Balance Inquiry | [TD-TC-076868.step1.balance-inquiry.json](test_data/TD-TC-076868.step1.balance-inquiry.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076868.step2.totals.json](test_data/TD-TC-076868.step2.totals.json) |
| TC-076869 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Balance Inqui | Response / oracle check | Negative | needs_client_input | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-076869.json](test_data/TD-TC-076869.json) |

### SC-ATL105-0E19698C - BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Magnetic Stripe @ Supermarket · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-FE53AF0A - BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Magnetic Stripe @ Utilities · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-09045E7A - BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Mobile Commerce Device @ General Retail · attended POS
- Status: unconfirmed; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076879 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Balanc | Happy path | Positive | pending_sme | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-076879.json](test_data/TD-TC-076879.json) |
| TC-076880 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Balanc | Running total / state | Positive | pending_sme | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Balance Inquiry | [TD-TC-076880.step1.balance-inquiry.json](test_data/TD-TC-076880.step1.balance-inquiry.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076880.step2.totals.json](test_data/TD-TC-076880.step2.totals.json) |
| TC-076881 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Balanc | Response / oracle check | Negative | pending_sme | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-076881.json](test_data/TD-TC-076881.json) |

### SC-ATL105-55D92531 - BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Mobile Commerce Device @ Supermarket · attended POS
- Status: unconfirmed; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-3DA9EEA4 - BUYPASS ATL105 MasterCard Credit Balance Inquiry – Contactless Mobile Commerce Device @ Utilities · attended POS
- Status: unconfirmed; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-A52A7906 - BUYPASS ATL105 MasterCard Credit Balance Inquiry – Credential on File @ Credential on File · customer present; card not present
- Status: unconfirmed; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076876 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Balance Inquiry → appr | Happy path | Positive | pending_sme | BR-83-1 p.83; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-076876.json](test_data/TD-TC-076876.json) |
| TC-076877 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Balance Inquiry, then  | Running total / state | Positive | pending_sme | BR-181-1 p.181; BR-183-1 p.183; BR-235-7 p.235 | 1 | Balance Inquiry | [TD-TC-076877.step1.balance-inquiry.json](test_data/TD-TC-076877.step1.balance-inquiry.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076877.step2.totals.json](test_data/TD-TC-076877.step2.totals.json) |
| TC-076878 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Balance Inquiry (card  | Response / oracle check | Negative | pending_sme | BR-83-1 p.83; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-076878.json](test_data/TD-TC-076878.json) |

### SC-ATL105-491A7A04 - BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contact Chip @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076856 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry → approv | Happy path | Positive | ready | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-076856.json](test_data/TD-TC-076856.json) |
| TC-076857 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry, then To | Running total / state | Positive | requires_review | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Balance Inquiry | [TD-TC-076857.step1.balance-inquiry.json](test_data/TD-TC-076857.step1.balance-inquiry.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076857.step2.totals.json](test_data/TD-TC-076857.step2.totals.json) |
| TC-076858 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry on a POS | Interaction (pairwise) | Positive | ready | p.546; BR-FISERV-EMV-GUIDE-49-2 p.49; BR-FISERV-EMV-GUIDE-50-2 p.50 | 1 | Balance Inquiry | [TD-TC-076858.json](test_data/TD-TC-076858.json) |
| TC-076859 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry (card is | Response / oracle check | Negative | needs_client_input | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-076859.json](test_data/TD-TC-076859.json) |
| TC-077179 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry required | Single rule broken | Negative | blocked_no_test_data | BR-162-2 p.162; BR-FISERV-EMV-GUIDE-66-4 p.66; BR-FISERV-EMV-GUIDE-74-7 p.74 | 1 |  | - |
| TC-077210 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry EMV Chip | Single rule broken | Negative | ready | BR-FISERV-EMV-GUIDE-70-6 p.70; p. | 1 |  | [TD-TC-077210.json](test_data/TD-TC-077210.json) |
| TC-077217 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry Tax Type | Single rule broken | Negative | ready | BR-FISERV-EMV-GUIDE-156-8 p.156; p. | 1 |  | [TD-TC-077217.json](test_data/TD-TC-077217.json) |
| TC-077218 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Balance Inquiry Informat | Single rule broken | Boundary | requires_review | BR-FISERV-EMV-GUIDE-157-10 p.157; BR-FISERV-EMV-GUIDE-157-11 p.157; p. | 1 |  | [TD-TC-077218.json](test_data/TD-TC-077218.json) |

### SC-ATL105-6E6D011E - BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contact Chip @ Supermarket · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-D8237178 - BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contact Chip @ Utilities · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-4030B66D - BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contactless Chip @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076860 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Balance Inquiry → ap | Happy path | Positive | ready | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-076860.json](test_data/TD-TC-076860.json) |
| TC-076861 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Balance Inquiry, the | Running total / state | Positive | requires_review | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Balance Inquiry | [TD-TC-076861.step1.balance-inquiry.json](test_data/TD-TC-076861.step1.balance-inquiry.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076861.step2.totals.json](test_data/TD-TC-076861.step2.totals.json) |
| TC-076862 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Balance Inquiry on a | Interaction (pairwise) | Positive | ready | p.546; BR-FISERV-EMV-GUIDE-49-2 p.49; BR-FISERV-EMV-GUIDE-50-2 p.50 | 1 | Balance Inquiry | [TD-TC-076862.json](test_data/TD-TC-076862.json) |
| TC-076863 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Balance Inquiry (car | Response / oracle check | Negative | needs_client_input | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-076863.json](test_data/TD-TC-076863.json) |
| TC-077205 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Balance Inquiry Upda | Single rule broken | Negative | ready | BR-FISERV-EMV-GUIDE-38-2 p.38; p. | 1 |  | [TD-TC-077205.json](test_data/TD-TC-077205.json) |

### SC-ATL105-D266ACC4 - BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contactless Chip @ Supermarket · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-C0BB3568 - BUYPASS ATL105 MasterCard Credit Balance Inquiry – EMV Contactless Chip @ Utilities · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-CF668DB7 - BUYPASS ATL105 MasterCard Credit Balance Inquiry – Magnetic Stripe @ General Retail · attended POS
- Status: spec_implied; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076864 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Balance Inquiry → approve | Happy path | Positive | ready | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-076864.json](test_data/TD-TC-076864.json) |
| TC-076865 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Balance Inquiry, then Tot | Running total / state | Positive | requires_review | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Balance Inquiry | [TD-TC-076865.step1.balance-inquiry.json](test_data/TD-TC-076865.step1.balance-inquiry.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076865.step2.totals.json](test_data/TD-TC-076865.step2.totals.json) |
| TC-076866 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Balance Inquiry (card iss | Response / oracle check | Negative | needs_client_input | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-076866.json](test_data/TD-TC-076866.json) |
| TC-077185 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Balance Inquiry Merchant  | Single rule broken | Boundary | requires_review | BR-503-1 p.503; p. | 1 |  | [TD-TC-077185.json](test_data/TD-TC-077185.json) |

### SC-ATL105-9F0150D4 - BUYPASS ATL105 MasterCard Credit Balance Inquiry – Magnetic Stripe @ Supermarket · attended POS
- Status: spec_implied; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-D2243B65 - BUYPASS ATL105 MasterCard Credit Balance Inquiry – Magnetic Stripe @ Utilities · attended POS
- Status: spec_implied; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-AA20AEAE - BUYPASS ATL105 MasterCard Credit Balance Inquiry – Manual / Keyed @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076870 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Balance Inquiry → approved | Happy path | Positive | ready | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-076870.json](test_data/TD-TC-076870.json) |
| TC-076871 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Balance Inquiry, then Tota | Running total / state | Positive | requires_review | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Balance Inquiry | [TD-TC-076871.step1.balance-inquiry.json](test_data/TD-TC-076871.step1.balance-inquiry.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076871.step2.totals.json](test_data/TD-TC-076871.step2.totals.json) |
| TC-076872 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Balance Inquiry (card issu | Response / oracle check | Negative | needs_client_input | p.546; p.511; p.83 | 1 | Balance Inquiry | [TD-TC-076872.json](test_data/TD-TC-076872.json) |

### SC-ATL105-AC0A2F97 - BUYPASS ATL105 MasterCard Credit Balance Inquiry – Manual / Keyed @ Supermarket · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-E696B3D1 - BUYPASS ATL105 MasterCard Credit Balance Inquiry – Manual / Keyed @ Utilities · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-3DF727E4 - BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Credential on File @ Mail Order · mail and/or telephone order
- Status: unconfirmed; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076756 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Happy path | Positive | pending_sme | p.546; BR-139-3 p.139; BR-139-6 p.139 | 1 | Mail/Phone Authorization Only | [TD-TC-076756.json](test_data/TD-TC-076756.json) |
| TC-076757 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Happy path | Positive | pending_sme | BR-83-1 p.83; BR-ATL105-TRANSARMOR-76-5 p.76; p.511 | 1 | Mail/Phone Authorization Only | [TD-TC-076757.json](test_data/TD-TC-076757.json) |
| TC-076758 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Single rule broken | Negative | pending_sme | p.546; p.368; p.83 | 1 | Mail/Phone Authorization Only | [TD-TC-076758.step1.moto-auth-only.json](test_data/TD-TC-076758.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076758.step2.cancellation.json](test_data/TD-TC-076758.step2.cancellation.json) |
| TC-076759 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Single rule broken | Negative | pending_sme | p.546; p.368; p.40 | 1 | Mail/Phone Authorization Only | [TD-TC-076759.step1.moto-auth-only.json](test_data/TD-TC-076759.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076759.step2.cancellation.json](test_data/TD-TC-076759.step2.cancellation.json) |
| TC-076760 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Single rule broken | Negative | pending_sme | p.545; p.409; p.402 | 1 | Mail/Phone Authorization Only | [TD-TC-076760.step1.moto-auth-only.json](test_data/TD-TC-076760.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Mail/Phone Purchase | [TD-TC-076760.step2.completion.json](test_data/TD-TC-076760.step2.completion.json) |
| TC-076761 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Single rule broken | Negative | pending_sme | p.545; p.409; p.40 | 1 | Mail/Phone Authorization Only | [TD-TC-076761.step1.moto-auth-only.json](test_data/TD-TC-076761.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Mail/Phone Purchase | [TD-TC-076761.step2.completion.json](test_data/TD-TC-076761.step2.completion.json) |
| TC-076762 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Decision table | Positive | pending_sme | BR-312-2 p.312; p.511; p.83 | 1 | Mail/Phone Authorization Only | [TD-TC-076762.json](test_data/TD-TC-076762.json) |
| TC-076763 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Valid flow | Positive | pending_sme | p.546; p.368; BR-85-2 p.85 | 1 | Mail/Phone Authorization Only | [TD-TC-076763.step1.moto-auth-only.json](test_data/TD-TC-076763.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076763.step2.cancellation.json](test_data/TD-TC-076763.step2.cancellation.json) |
| TC-076764 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Valid flow | Positive | pending_sme | p.545; p.409; BR-402-1 p.402 | 1 | Mail/Phone Authorization Only | [TD-TC-076764.step1.moto-auth-only.json](test_data/TD-TC-076764.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Mail/Phone Purchase | [TD-TC-076764.step2.completion.json](test_data/TD-TC-076764.step2.completion.json) |
| TC-076765 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Invalid state transition | Negative | pending_sme | p.546; p.511; p.83 | 1 | Mail/Phone Authorization Only | [TD-TC-076765.step1.moto-auth-only.json](test_data/TD-TC-076765.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076765.step2.cancellation.json](test_data/TD-TC-076765.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-076765.step3.cancellation.json](test_data/TD-TC-076765.step3.cancellation.json) |
| TC-076766 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Running total / state | Positive | pending_sme | p.546; p.368; BR-181-1 p.181 | 1 | Mail/Phone Authorization Only | [TD-TC-076766.step1.moto-auth-only.json](test_data/TD-TC-076766.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076766.step2.cancellation.json](test_data/TD-TC-076766.step2.cancellation.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076766.step3.totals.json](test_data/TD-TC-076766.step3.totals.json) |
| TC-076767 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Running total / state | Positive | pending_sme | p.545; p.409; BR-181-1 p.181 | 1 | Mail/Phone Authorization Only | [TD-TC-076767.step1.moto-auth-only.json](test_data/TD-TC-076767.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Mail/Phone Purchase | [TD-TC-076767.step2.completion.json](test_data/TD-TC-076767.step2.completion.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076767.step3.totals.json](test_data/TD-TC-076767.step3.totals.json) |
| TC-076768 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Running total / state | Positive | pending_sme | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Mail/Phone Authorization Only | [TD-TC-076768.step1.moto-auth-only.json](test_data/TD-TC-076768.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076768.step2.totals.json](test_data/TD-TC-076768.step2.totals.json) |
| TC-076769 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Response / oracle check | Negative | pending_sme | p.546; p.368; BR-85-2 p.85 | 1 | Mail/Phone Authorization Only | [TD-TC-076769.step1.moto-auth-only.json](test_data/TD-TC-076769.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076769.step2.cancellation.json](test_data/TD-TC-076769.step2.cancellation.json) |
| TC-076770 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Response / oracle check | Negative | pending_sme | p.545; p.409; BR-402-1 p.402 | 1 | Mail/Phone Authorization Only | [TD-TC-076770.step1.moto-auth-only.json](test_data/TD-TC-076770.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Mail/Phone Purchase | [TD-TC-076770.step2.completion.json](test_data/TD-TC-076770.step2.completion.json) |
| TC-076771 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Response / oracle check | Negative | pending_sme | BR-83-1 p.83; BR-ATL105-TRANSARMOR-76-5 p.76; p.511 | 1 | Mail/Phone Authorization Only | [TD-TC-076771.json](test_data/TD-TC-076771.json) |
| TC-076772 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Authorizati | Response / oracle check | Positive | pending_sme | BR-240-1 p.240; BR-252-3 p.252; BR-266-3 p.266 | 1 | Mail/Phone Authorization Only | [TD-TC-076772.json](test_data/TD-TC-076772.json) |

### SC-ATL105-12B03219 - BUYPASS ATL105 MasterCard Credit Mail/Phone Authorization Only – Manual / Keyed @ Mail Order · mail and/or telephone order
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076718 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Happy path | Positive | ready | p.546; BR-139-3 p.139; BR-139-6 p.139 | 1 | Mail/Phone Authorization Only | [TD-TC-076718.json](test_data/TD-TC-076718.json) |
| TC-076719 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Single rule broken | Negative | requires_review | p.546; p.368; p.83 | 1 | Mail/Phone Authorization Only | [TD-TC-076719.step1.moto-auth-only.json](test_data/TD-TC-076719.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076719.step2.cancellation.json](test_data/TD-TC-076719.step2.cancellation.json) |
| TC-076720 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Single rule broken | Negative | requires_review | p.546; p.368; p.40 | 1 | Mail/Phone Authorization Only | [TD-TC-076720.step1.moto-auth-only.json](test_data/TD-TC-076720.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076720.step2.cancellation.json](test_data/TD-TC-076720.step2.cancellation.json) |
| TC-076721 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Single rule broken | Negative | requires_review | p.545; p.409; p.402 | 1 | Mail/Phone Authorization Only | [TD-TC-076721.step1.moto-auth-only.json](test_data/TD-TC-076721.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Mail/Phone Purchase | [TD-TC-076721.step2.completion.json](test_data/TD-TC-076721.step2.completion.json) |
| TC-076722 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Single rule broken | Negative | requires_review | p.545; p.409; p.40 | 1 | Mail/Phone Authorization Only | [TD-TC-076722.step1.moto-auth-only.json](test_data/TD-TC-076722.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Mail/Phone Purchase | [TD-TC-076722.step2.completion.json](test_data/TD-TC-076722.step2.completion.json) |
| TC-076723 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Decision table | Positive | ready | BR-312-2 p.312; p.511; p.83 | 1 | Mail/Phone Authorization Only | [TD-TC-076723.json](test_data/TD-TC-076723.json) |
| TC-076724 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Valid flow | Positive | ready | p.546; p.368; BR-85-2 p.85 | 1 | Mail/Phone Authorization Only | [TD-TC-076724.step1.moto-auth-only.json](test_data/TD-TC-076724.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076724.step2.cancellation.json](test_data/TD-TC-076724.step2.cancellation.json) |
| TC-076725 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Valid flow | Positive | ready | p.545; p.409; BR-402-1 p.402 | 1 | Mail/Phone Authorization Only | [TD-TC-076725.step1.moto-auth-only.json](test_data/TD-TC-076725.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Mail/Phone Purchase | [TD-TC-076725.step2.completion.json](test_data/TD-TC-076725.step2.completion.json) |
| TC-076726 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Invalid state transition | Negative | requires_review | p.546; p.511; p.83 | 1 | Mail/Phone Authorization Only | [TD-TC-076726.step1.moto-auth-only.json](test_data/TD-TC-076726.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076726.step2.cancellation.json](test_data/TD-TC-076726.step2.cancellation.json) |
|  |  |  |  |  | 3 | Cancellation | [TD-TC-076726.step3.cancellation.json](test_data/TD-TC-076726.step3.cancellation.json) |
| TC-076727 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Running total / state | Positive | requires_review | p.546; p.368; BR-181-1 p.181 | 1 | Mail/Phone Authorization Only | [TD-TC-076727.step1.moto-auth-only.json](test_data/TD-TC-076727.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076727.step2.cancellation.json](test_data/TD-TC-076727.step2.cancellation.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076727.step3.totals.json](test_data/TD-TC-076727.step3.totals.json) |
| TC-076728 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Running total / state | Positive | requires_review | p.545; p.409; BR-181-1 p.181 | 1 | Mail/Phone Authorization Only | [TD-TC-076728.step1.moto-auth-only.json](test_data/TD-TC-076728.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Mail/Phone Purchase | [TD-TC-076728.step2.completion.json](test_data/TD-TC-076728.step2.completion.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076728.step3.totals.json](test_data/TD-TC-076728.step3.totals.json) |
| TC-076729 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Running total / state | Positive | requires_review | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Mail/Phone Authorization Only | [TD-TC-076729.step1.moto-auth-only.json](test_data/TD-TC-076729.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076729.step2.totals.json](test_data/TD-TC-076729.step2.totals.json) |
| TC-076730 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for R | Running total / state | Positive | requires_review | BR-403-3 p.403; p.511; p.83 | 1 |  | - |
| TC-076731 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for ' | Running total / state | Positive | requires_review | BR-404-11 p.404; p.511; p.83 | 1 |  | - |
| TC-076732 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for ' | Running total / state | Positive | requires_review | BR-404-12 p.404; p.511; p.83 | 1 |  | - |
| TC-076733 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for ' | Running total / state | Positive | requires_review | BR-404-7 p.404; p.511; p.83 | 1 |  | - |
| TC-076734 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for ' | Running total / state | Positive | requires_review | BR-404-8 p.404; p.511; p.83 | 1 |  | - |
| TC-076735 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: ordering for Electronic Mai | Ordering | Positive | requires_review | BR-ATL105-TRANSARMOR-67-1 p.67; p.511; p.83 | 1 |  | - |
| TC-076736 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Response / oracle check | Negative | needs_client_input | p.546; p.368; BR-85-2 p.85 | 1 | Mail/Phone Authorization Only | [TD-TC-076736.step1.moto-auth-only.json](test_data/TD-TC-076736.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Cancellation | [TD-TC-076736.step2.cancellation.json](test_data/TD-TC-076736.step2.cancellation.json) |
| TC-076737 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Response / oracle check | Negative | needs_client_input | p.545; p.409; BR-402-1 p.402 | 1 | Mail/Phone Authorization Only | [TD-TC-076737.step1.moto-auth-only.json](test_data/TD-TC-076737.step1.moto-auth-only.json) |
|  |  |  |  |  | 2 | Mail/Phone Purchase | [TD-TC-076737.step2.completion.json](test_data/TD-TC-076737.step2.completion.json) |
| TC-076738 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Response / oracle check | Negative | needs_client_input | p.546; BR-139-1 p.139; BR-139-4 p.139 | 1 | Mail/Phone Authorization Only | [TD-TC-076738.json](test_data/TD-TC-076738.json) |
| TC-076739 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Response / oracle check | Positive | needs_client_input | BR-240-1 p.240; BR-252-3 p.252; BR-266-3 p.266 | 1 | Mail/Phone Authorization Only | [TD-TC-076739.json](test_data/TD-TC-076739.json) |
| TC-076740 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Issuer outcome | Positive | needs_client_input | p.644; BR-240-1 p.240; BR-252-3 p.252 | 1 | Mail/Phone Authorization Only | [TD-TC-076740.json](test_data/TD-TC-076740.json) |
| TC-076741 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Issuer outcome | Positive | needs_client_input | p.644; BR-240-1 p.240; BR-252-3 p.252 | 1 | Mail/Phone Authorization Only | [TD-TC-076741.json](test_data/TD-TC-076741.json) |
| TC-076742 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Issuer outcome | Positive | needs_client_input | p.644; BR-240-1 p.240; BR-252-3 p.252 | 1 | Mail/Phone Authorization Only | [TD-TC-076742.json](test_data/TD-TC-076742.json) |
| TC-076743 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Issuer outcome | Positive | needs_client_input | p.644; BR-240-1 p.240; BR-252-3 p.252 | 1 | Mail/Phone Authorization Only | [TD-TC-076743.json](test_data/TD-TC-076743.json) |
| TC-076744 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Issuer outcome | Positive | needs_client_input | p.644; BR-240-1 p.240; BR-252-3 p.252 | 1 | Mail/Phone Authorization Only | [TD-TC-076744.json](test_data/TD-TC-076744.json) |
| TC-076745 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Issuer outcome | Positive | needs_client_input | p.644; BR-240-1 p.240; BR-252-3 p.252 | 1 | Mail/Phone Authorization Only | [TD-TC-076745.json](test_data/TD-TC-076745.json) |
| TC-076746 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Issuer outcome | Positive | needs_client_input | p.644; BR-240-1 p.240; BR-252-3 p.252 | 1 | Mail/Phone Authorization Only | [TD-TC-076746.json](test_data/TD-TC-076746.json) |
| TC-076747 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Issuer outcome | Positive | needs_client_input | p.644; BR-240-1 p.240; BR-252-3 p.252 | 1 | Mail/Phone Authorization Only | [TD-TC-076747.json](test_data/TD-TC-076747.json) |
| TC-076748 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Issuer outcome | Positive | needs_client_input | p.644; BR-240-1 p.240; BR-252-3 p.252 | 1 | Mail/Phone Authorization Only | [TD-TC-076748.json](test_data/TD-TC-076748.json) |
| TC-076749 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: Message Format Version Iden | Device checkpoint | Positive | requires_review | BR-184-1 p.184; p.511; p.83 | 1 |  | - |
| TC-076750 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Single rule broken | Negative | requires_review | p.161; p.511; p.83 | 1 | Mail/Phone Authorization Only | [TD-TC-076750.json](test_data/TD-TC-076750.json) |
| TC-076751 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Single rule broken | Negative | requires_review | p.161; p.511; p.83 | 1 | Mail/Phone Authorization Only | [TD-TC-076751.json](test_data/TD-TC-076751.json) |
| TC-076752 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Single rule broken | Negative | requires_review | p.695; p.511; p.83 | 1 | Mail/Phone Authorization Only | [TD-TC-076752.json](test_data/TD-TC-076752.json) |
| TC-076753 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Single rule broken | Negative | requires_review | p.695; p.511; p.83 | 1 | Mail/Phone Authorization Only | [TD-TC-076753.json](test_data/TD-TC-076753.json) |
| TC-076754 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Single rule broken | Negative | requires_review | p.695; p.511; p.83 | 1 | Mail/Phone Authorization Only | [TD-TC-076754.json](test_data/TD-TC-076754.json) |
| TC-076755 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Authorization O | Single rule broken | Negative | requires_review | p.695; p.511; p.83 | 1 | Mail/Phone Authorization Only | [TD-TC-076755.json](test_data/TD-TC-076755.json) |

### SC-ATL105-D2FEACFC - BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Credential on File @ Mail Order · mail and/or telephone order
- Status: unconfirmed; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076567 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase →  | Happy path | Positive | pending_sme | BR-83-1 p.83; p.511; p.83 | 1 | Mail/Phone Purchase | [TD-TC-076567.json](test_data/TD-TC-076567.json) |
| TC-076568 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase →  | Happy path | Positive | pending_sme | p.545; BR-139-3 p.139; BR-139-6 p.139 | 1 | Mail/Phone Purchase | [TD-TC-076568.json](test_data/TD-TC-076568.json) |
| TC-076569 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, t | Single rule broken | Negative | pending_sme | p.546; p.546; p.402 | 1 | Mail/Phone Purchase | [TD-TC-076569.step1.moto-purchase.json](test_data/TD-TC-076569.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Mail/Phone Reversal | [TD-TC-076569.step2.moto-reversal.json](test_data/TD-TC-076569.step2.moto-reversal.json) |
| TC-076570 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, t | Single rule broken | Negative | pending_sme | p.546; p.546; p.40 | 1 | Mail/Phone Purchase | [TD-TC-076570.step1.moto-purchase.json](test_data/TD-TC-076570.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Mail/Phone Reversal | [TD-TC-076570.step2.moto-reversal.json](test_data/TD-TC-076570.step2.moto-reversal.json) |
| TC-076571 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, t | Single rule broken | Negative | pending_sme | p.546; p.39; p.38 | 1 | Mail/Phone Purchase | [TD-TC-076571.step1.moto-purchase.json](test_data/TD-TC-076571.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076571.step2.timeout-reversal.json](test_data/TD-TC-076571.step2.timeout-reversal.json) |
| TC-076572 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, t | Valid flow | Positive | pending_sme | p.546; p.546; BR-402-3 p.402 | 1 | Mail/Phone Purchase | [TD-TC-076572.step1.moto-purchase.json](test_data/TD-TC-076572.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Mail/Phone Reversal | [TD-TC-076572.step2.moto-reversal.json](test_data/TD-TC-076572.step2.moto-reversal.json) |
| TC-076573 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, t | Valid flow | Positive | pending_sme | p.546; p.39; BR-84-4 p.84 | 1 | Mail/Phone Purchase | [TD-TC-076573.step1.moto-purchase.json](test_data/TD-TC-076573.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076573.step2.timeout-reversal.json](test_data/TD-TC-076573.step2.timeout-reversal.json) |
| TC-076574 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, t | Invalid state transition | Negative | pending_sme | p.546; BR-403-3 p.403; p.511 | 1 | Mail/Phone Purchase | [TD-TC-076574.step1.moto-purchase.json](test_data/TD-TC-076574.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Mail/Phone Reversal | [TD-TC-076574.step2.moto-reversal.json](test_data/TD-TC-076574.step2.moto-reversal.json) |
|  |  |  |  |  | 3 | Mail/Phone Reversal | [TD-TC-076574.step3.moto-reversal.json](test_data/TD-TC-076574.step3.moto-reversal.json) |
| TC-076575 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, t | Invalid state transition | Negative | pending_sme | p.546; BR-39-1 p.39; p.511 | 1 | Mail/Phone Purchase | [TD-TC-076575.step1.moto-purchase.json](test_data/TD-TC-076575.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076575.step2.timeout-reversal.json](test_data/TD-TC-076575.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-076575.step3.timeout-reversal.json](test_data/TD-TC-076575.step3.timeout-reversal.json) |
| TC-076576 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Reversal wi | Invalid state transition | Negative | blocked_verification | p.546; BR-403-3 p.403; p.511 | 1 | Mail/Phone Reversal | [TD-TC-076576.json](test_data/TD-TC-076576.json) |
| TC-076577 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase (n | Fault / recovery | Positive | pending_sme | BR-83-1 p.83; p.511; p.83 | 1 | Mail/Phone Purchase | [TD-TC-076577.step1.moto-purchase.json](test_data/TD-TC-076577.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Mail/Phone Reversal | [TD-TC-076577.step2.moto-reversal.json](test_data/TD-TC-076577.step2.moto-reversal.json) |
| TC-076578 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase (n | Fault / recovery | Positive | pending_sme | BR-39-1 p.39; BR-44-1 p.44; BR-43-2 p.43 | 1 | Mail/Phone Purchase | [TD-TC-076578.step1.moto-purchase.json](test_data/TD-TC-076578.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076578.step2.timeout-reversal.json](test_data/TD-TC-076578.step2.timeout-reversal.json) |
| TC-076579 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, t | Running total / state | Positive | pending_sme | p.546; p.546; BR-181-1 p.181 | 1 | Mail/Phone Purchase | [TD-TC-076579.step1.moto-purchase.json](test_data/TD-TC-076579.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Mail/Phone Reversal | [TD-TC-076579.step2.moto-reversal.json](test_data/TD-TC-076579.step2.moto-reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076579.step3.totals.json](test_data/TD-TC-076579.step3.totals.json) |
| TC-076580 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, t | Running total / state | Positive | pending_sme | p.546; p.39; BR-181-1 p.181 | 1 | Mail/Phone Purchase | [TD-TC-076580.step1.moto-purchase.json](test_data/TD-TC-076580.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076580.step2.timeout-reversal.json](test_data/TD-TC-076580.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076580.step3.totals.json](test_data/TD-TC-076580.step3.totals.json) |
| TC-076581 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, t | Running total / state | Positive | pending_sme | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Mail/Phone Purchase | [TD-TC-076581.step1.moto-purchase.json](test_data/TD-TC-076581.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076581.step2.totals.json](test_data/TD-TC-076581.step2.totals.json) |
| TC-076582 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, t | Response / oracle check | Negative | pending_sme | p.546; p.546; BR-402-3 p.402 | 1 | Mail/Phone Purchase | [TD-TC-076582.step1.moto-purchase.json](test_data/TD-TC-076582.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Mail/Phone Reversal | [TD-TC-076582.step2.moto-reversal.json](test_data/TD-TC-076582.step2.moto-reversal.json) |
| TC-076583 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase, t | Response / oracle check | Negative | pending_sme | p.546; p.39; BR-84-4 p.84 | 1 | Mail/Phone Purchase | [TD-TC-076583.step1.moto-purchase.json](test_data/TD-TC-076583.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076583.step2.timeout-reversal.json](test_data/TD-TC-076583.step2.timeout-reversal.json) |
| TC-076584 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase (c | Response / oracle check | Negative | pending_sme | p.545; BR-72-4 p.72; BR-139-1 p.139 | 1 | Mail/Phone Purchase | [TD-TC-076584.json](test_data/TD-TC-076584.json) |
| TC-076585 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Mail/Phone Purchase se | Response / oracle check | Positive | pending_sme | BR-240-1 p.240; BR-252-3 p.252; BR-266-3 p.266 | 1 | Mail/Phone Purchase | [TD-TC-076585.json](test_data/TD-TC-076585.json) |

### SC-ATL105-6BE2183C - BUYPASS ATL105 MasterCard Credit Mail/Phone Purchase – Manual / Keyed @ Mail Order · mail and/or telephone order
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076537 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase → appr | Happy path | Positive | ready | p.545; BR-139-3 p.139; BR-139-6 p.139 | 1 | Mail/Phone Purchase | [TD-TC-076537.json](test_data/TD-TC-076537.json) |
| TC-076538 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then  | Single rule broken | Negative | requires_review | p.546; p.546; p.402 | 1 | Mail/Phone Purchase | [TD-TC-076538.step1.moto-purchase.json](test_data/TD-TC-076538.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Mail/Phone Reversal | [TD-TC-076538.step2.moto-reversal.json](test_data/TD-TC-076538.step2.moto-reversal.json) |
| TC-076539 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then  | Single rule broken | Negative | requires_review | p.546; p.546; p.40 | 1 | Mail/Phone Purchase | [TD-TC-076539.step1.moto-purchase.json](test_data/TD-TC-076539.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Mail/Phone Reversal | [TD-TC-076539.step2.moto-reversal.json](test_data/TD-TC-076539.step2.moto-reversal.json) |
| TC-076540 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then  | Single rule broken | Negative | requires_review | p.546; p.39; p.38 | 1 | Mail/Phone Purchase | [TD-TC-076540.step1.moto-purchase.json](test_data/TD-TC-076540.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076540.step2.timeout-reversal.json](test_data/TD-TC-076540.step2.timeout-reversal.json) |
| TC-076541 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then  | Valid flow | Positive | ready | p.546; p.546; BR-402-3 p.402 | 1 | Mail/Phone Purchase | [TD-TC-076541.step1.moto-purchase.json](test_data/TD-TC-076541.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Mail/Phone Reversal | [TD-TC-076541.step2.moto-reversal.json](test_data/TD-TC-076541.step2.moto-reversal.json) |
| TC-076542 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then  | Valid flow | Positive | ready | p.546; p.39; BR-84-4 p.84 | 1 | Mail/Phone Purchase | [TD-TC-076542.step1.moto-purchase.json](test_data/TD-TC-076542.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076542.step2.timeout-reversal.json](test_data/TD-TC-076542.step2.timeout-reversal.json) |
| TC-076543 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then  | Invalid state transition | Negative | ready | p.546; BR-403-3 p.403; p.511 | 1 | Mail/Phone Purchase | [TD-TC-076543.step1.moto-purchase.json](test_data/TD-TC-076543.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Mail/Phone Reversal | [TD-TC-076543.step2.moto-reversal.json](test_data/TD-TC-076543.step2.moto-reversal.json) |
|  |  |  |  |  | 3 | Mail/Phone Reversal | [TD-TC-076543.step3.moto-reversal.json](test_data/TD-TC-076543.step3.moto-reversal.json) |
| TC-076544 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then  | Invalid state transition | Negative | requires_review | p.546; BR-39-1 p.39; p.511 | 1 | Mail/Phone Purchase | [TD-TC-076544.step1.moto-purchase.json](test_data/TD-TC-076544.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076544.step2.timeout-reversal.json](test_data/TD-TC-076544.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-076544.step3.timeout-reversal.json](test_data/TD-TC-076544.step3.timeout-reversal.json) |
| TC-076545 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Reversal with n | Invalid state transition | Negative | blocked_verification | p.546; BR-403-3 p.403; p.511 | 1 | Mail/Phone Reversal | [TD-TC-076545.json](test_data/TD-TC-076545.json) |
| TC-076546 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase (no re | Fault / recovery | Positive | ready | BR-39-1 p.39; BR-44-1 p.44; BR-43-2 p.43 | 1 | Mail/Phone Purchase | [TD-TC-076546.step1.moto-purchase.json](test_data/TD-TC-076546.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076546.step2.timeout-reversal.json](test_data/TD-TC-076546.step2.timeout-reversal.json) |
| TC-076547 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then  | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Mail/Phone Purchase | [TD-TC-076547.step1.moto-purchase.json](test_data/TD-TC-076547.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Mail/Phone Reversal | [TD-TC-076547.step2.moto-reversal.json](test_data/TD-TC-076547.step2.moto-reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076547.step3.totals.json](test_data/TD-TC-076547.step3.totals.json) |
| TC-076548 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then  | Running total / state | Positive | requires_review | p.546; p.39; BR-181-1 p.181 | 1 | Mail/Phone Purchase | [TD-TC-076548.step1.moto-purchase.json](test_data/TD-TC-076548.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076548.step2.timeout-reversal.json](test_data/TD-TC-076548.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076548.step3.totals.json](test_data/TD-TC-076548.step3.totals.json) |
| TC-076549 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then  | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Mail/Phone Purchase | [TD-TC-076549.step1.moto-purchase.json](test_data/TD-TC-076549.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076549.step2.totals.json](test_data/TD-TC-076549.step2.totals.json) |
| TC-076550 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for R | Running total / state | Positive | requires_review | BR-403-3 p.403; p.511; p.83 | 1 |  | - |
| TC-076551 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for ' | Running total / state | Positive | requires_review | BR-404-11 p.404; p.511; p.83 | 1 |  | - |
| TC-076552 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for ' | Running total / state | Positive | requires_review | BR-404-12 p.404; p.511; p.83 | 1 |  | - |
| TC-076553 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for ' | Running total / state | Positive | requires_review | BR-404-7 p.404; p.511; p.83 | 1 |  | - |
| TC-076554 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for ' | Running total / state | Positive | requires_review | BR-404-8 p.404; p.511; p.83 | 1 |  | - |
| TC-076555 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: ordering for Electronic Mai | Ordering | Positive | requires_review | BR-ATL105-TRANSARMOR-67-1 p.67; p.511; p.83 | 1 |  | - |
| TC-076556 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then  | Response / oracle check | Negative | needs_client_input | p.546; p.546; BR-402-3 p.402 | 1 | Mail/Phone Purchase | [TD-TC-076556.step1.moto-purchase.json](test_data/TD-TC-076556.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Mail/Phone Reversal | [TD-TC-076556.step2.moto-reversal.json](test_data/TD-TC-076556.step2.moto-reversal.json) |
| TC-076557 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase, then  | Response / oracle check | Negative | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Mail/Phone Purchase | [TD-TC-076557.step1.moto-purchase.json](test_data/TD-TC-076557.step1.moto-purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076557.step2.timeout-reversal.json](test_data/TD-TC-076557.step2.timeout-reversal.json) |
| TC-076558 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase (card  | Response / oracle check | Negative | needs_client_input | p.545; BR-72-4 p.72; BR-139-1 p.139 | 1 | Mail/Phone Purchase | [TD-TC-076558.json](test_data/TD-TC-076558.json) |
| TC-076559 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase sendin | Response / oracle check | Positive | needs_client_input | BR-240-1 p.240; BR-252-3 p.252; BR-266-3 p.266 | 1 | Mail/Phone Purchase | [TD-TC-076559.json](test_data/TD-TC-076559.json) |
| TC-076560 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: Message Format Version Iden | Device checkpoint | Positive | requires_review | BR-184-1 p.184; p.511; p.83 | 1 |  | - |
| TC-076561 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase sent w | Single rule broken | Negative | requires_review | p.161; p.511; p.83 | 1 | Mail/Phone Purchase | [TD-TC-076561.json](test_data/TD-TC-076561.json) |
| TC-076562 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase sent w | Single rule broken | Negative | requires_review | p.161; p.511; p.83 | 1 | Mail/Phone Purchase | [TD-TC-076562.json](test_data/TD-TC-076562.json) |
| TC-076563 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase sent w | Single rule broken | Negative | requires_review | p.695; p.511; p.83 | 1 | Mail/Phone Purchase | [TD-TC-076563.json](test_data/TD-TC-076563.json) |
| TC-076564 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase sent w | Single rule broken | Negative | requires_review | p.695; p.511; p.83 | 1 | Mail/Phone Purchase | [TD-TC-076564.json](test_data/TD-TC-076564.json) |
| TC-076565 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase sent w | Single rule broken | Negative | requires_review | p.695; p.511; p.83 | 1 | Mail/Phone Purchase | [TD-TC-076565.json](test_data/TD-TC-076565.json) |
| TC-076566 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase sent w | Single rule broken | Negative | requires_review | p.695; p.511; p.83 | 1 | Mail/Phone Purchase | [TD-TC-076566.json](test_data/TD-TC-076566.json) |
| TC-077176 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Mail/Phone Purchase Requir | Single rule broken | Negative | ready | BR-89-3 p.89; BR-89-5 p.89; BR-89-10 p.89 | 1 |  | [TD-TC-077176.json](test_data/TD-TC-077176.json) |

### SC-ATL105-873CDFFE - BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076825 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return → approv | Happy path | Positive | ready | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-076825.json](test_data/TD-TC-076825.json) |
| TC-076826 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return, then Vo | Single rule broken | Negative | requires_review | p.546; p.546; p.402 | 1 | Merchandise Return | [TD-TC-076826.step1.return.json](test_data/TD-TC-076826.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076826.step2.void-return.json](test_data/TD-TC-076826.step2.void-return.json) |
| TC-076827 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return, then Vo | Single rule broken | Negative | requires_review | p.546; p.546; p.40 | 1 | Merchandise Return | [TD-TC-076827.step1.return.json](test_data/TD-TC-076827.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076827.step2.void-return.json](test_data/TD-TC-076827.step2.void-return.json) |
| TC-076828 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return, then Vo | Valid flow | Positive | ready | p.546; p.546; BR-402-3 p.402 | 1 | Merchandise Return | [TD-TC-076828.step1.return.json](test_data/TD-TC-076828.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076828.step2.void-return.json](test_data/TD-TC-076828.step2.void-return.json) |
| TC-076829 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return, then Vo | Invalid state transition | Negative | requires_review | p.546; p.511; p.83 | 1 | Merchandise Return | [TD-TC-076829.step1.return.json](test_data/TD-TC-076829.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076829.step2.void-return.json](test_data/TD-TC-076829.step2.void-return.json) |
|  |  |  |  |  | 3 | Void of a Merchandise Return | [TD-TC-076829.step3.void-return.json](test_data/TD-TC-076829.step3.void-return.json) |
| TC-076830 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Void of a Merchandise Retur | Invalid state transition | Negative | blocked_verification | p.546; p.511; p.83 | 1 | Void of a Merchandise Return | [TD-TC-076830.json](test_data/TD-TC-076830.json) |
| TC-076831 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return, then Vo | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Merchandise Return | [TD-TC-076831.step1.return.json](test_data/TD-TC-076831.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076831.step2.void-return.json](test_data/TD-TC-076831.step2.void-return.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076831.step3.totals.json](test_data/TD-TC-076831.step3.totals.json) |
| TC-076832 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return, then To | Running total / state | Positive | requires_review | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Merchandise Return | [TD-TC-076832.step1.return.json](test_data/TD-TC-076832.step1.return.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076832.step2.totals.json](test_data/TD-TC-076832.step2.totals.json) |
| TC-076833 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return, then Vo | Response / oracle check | Negative | needs_client_input | p.546; p.546; BR-402-3 p.402 | 1 | Merchandise Return | [TD-TC-076833.step1.return.json](test_data/TD-TC-076833.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076833.step2.void-return.json](test_data/TD-TC-076833.step2.void-return.json) |
| TC-076834 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return (card is | Response / oracle check | Negative | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-076834.json](test_data/TD-TC-076834.json) |
| TC-077189 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Merchandise Return Token PA | Single rule broken | Negative | ready | BR-ATL105-TRANSARMOR-12-3 p.12; BR-ATL105-TRANSARMOR-17-7 p.17; BR-FISERV-EMV-GUIDE-43-5 p.43 | 1 |  | [TD-TC-077189.json](test_data/TD-TC-077189.json) |

### SC-ATL105-4B839359 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077023 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Merchandise Return → a | Happy path | Positive | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-077023.json](test_data/TD-TC-077023.json) |

### SC-ATL105-C4EB18DF - BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback @ Supermarket · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-801B9EC9 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Chip Fallback @ Utilities · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-FE1C30BA - BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076805 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise R | Happy path | Positive | ready | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-076805.json](test_data/TD-TC-076805.json) |
| TC-076806 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise R | Single rule broken | Negative | requires_review | p.546; p.546; p.402 | 1 | Merchandise Return | [TD-TC-076806.step1.return.json](test_data/TD-TC-076806.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076806.step2.void-return.json](test_data/TD-TC-076806.step2.void-return.json) |
| TC-076807 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise R | Single rule broken | Negative | requires_review | p.546; p.546; p.40 | 1 | Merchandise Return | [TD-TC-076807.step1.return.json](test_data/TD-TC-076807.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076807.step2.void-return.json](test_data/TD-TC-076807.step2.void-return.json) |
| TC-076808 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise R | Valid flow | Positive | ready | p.546; p.546; BR-402-3 p.402 | 1 | Merchandise Return | [TD-TC-076808.step1.return.json](test_data/TD-TC-076808.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076808.step2.void-return.json](test_data/TD-TC-076808.step2.void-return.json) |
| TC-076809 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise R | Invalid state transition | Negative | requires_review | p.546; p.511; p.83 | 1 | Merchandise Return | [TD-TC-076809.step1.return.json](test_data/TD-TC-076809.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076809.step2.void-return.json](test_data/TD-TC-076809.step2.void-return.json) |
|  |  |  |  |  | 3 | Void of a Merchandise Return | [TD-TC-076809.step3.void-return.json](test_data/TD-TC-076809.step3.void-return.json) |
| TC-076810 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Void of a Mer | Invalid state transition | Negative | blocked_verification | p.546; p.511; p.83 | 1 | Void of a Merchandise Return | [TD-TC-076810.json](test_data/TD-TC-076810.json) |
| TC-076811 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise R | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Merchandise Return | [TD-TC-076811.step1.return.json](test_data/TD-TC-076811.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076811.step2.void-return.json](test_data/TD-TC-076811.step2.void-return.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076811.step3.totals.json](test_data/TD-TC-076811.step3.totals.json) |
| TC-076812 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise R | Running total / state | Positive | requires_review | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Merchandise Return | [TD-TC-076812.step1.return.json](test_data/TD-TC-076812.step1.return.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076812.step2.totals.json](test_data/TD-TC-076812.step2.totals.json) |
| TC-076813 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise R | Response / oracle check | Negative | needs_client_input | p.546; p.546; BR-402-3 p.402 | 1 | Merchandise Return | [TD-TC-076813.step1.return.json](test_data/TD-TC-076813.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076813.step2.void-return.json](test_data/TD-TC-076813.step2.void-return.json) |
| TC-076814 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Merchandise R | Response / oracle check | Negative | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-076814.json](test_data/TD-TC-076814.json) |

### SC-ATL105-D2A34953 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077021 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Merchand | Happy path | Positive | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-077021.json](test_data/TD-TC-077021.json) |

### SC-ATL105-AC2FB54A - BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe @ Supermarket · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-9D86DA57 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Magnetic Stripe @ Utilities · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-DA3E80BE - BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device @ General Retail · attended POS
- Status: unconfirmed; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076846 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Mercha | Happy path | Positive | pending_sme | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-076846.json](test_data/TD-TC-076846.json) |
| TC-076847 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Mercha | Single rule broken | Negative | pending_sme | p.546; p.546; p.402 | 1 | Merchandise Return | [TD-TC-076847.step1.return.json](test_data/TD-TC-076847.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076847.step2.void-return.json](test_data/TD-TC-076847.step2.void-return.json) |
| TC-076848 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Mercha | Single rule broken | Negative | pending_sme | p.546; p.546; p.40 | 1 | Merchandise Return | [TD-TC-076848.step1.return.json](test_data/TD-TC-076848.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076848.step2.void-return.json](test_data/TD-TC-076848.step2.void-return.json) |
| TC-076849 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Mercha | Valid flow | Positive | pending_sme | p.546; p.546; BR-402-3 p.402 | 1 | Merchandise Return | [TD-TC-076849.step1.return.json](test_data/TD-TC-076849.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076849.step2.void-return.json](test_data/TD-TC-076849.step2.void-return.json) |
| TC-076850 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Mercha | Invalid state transition | Negative | pending_sme | p.546; p.511; p.83 | 1 | Merchandise Return | [TD-TC-076850.step1.return.json](test_data/TD-TC-076850.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076850.step2.void-return.json](test_data/TD-TC-076850.step2.void-return.json) |
|  |  |  |  |  | 3 | Void of a Merchandise Return | [TD-TC-076850.step3.void-return.json](test_data/TD-TC-076850.step3.void-return.json) |
| TC-076851 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Void o | Invalid state transition | Negative | blocked_verification | p.546; p.511; p.83 | 1 | Void of a Merchandise Return | [TD-TC-076851.json](test_data/TD-TC-076851.json) |
| TC-076852 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Mercha | Running total / state | Positive | pending_sme | p.546; p.546; BR-181-1 p.181 | 1 | Merchandise Return | [TD-TC-076852.step1.return.json](test_data/TD-TC-076852.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076852.step2.void-return.json](test_data/TD-TC-076852.step2.void-return.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076852.step3.totals.json](test_data/TD-TC-076852.step3.totals.json) |
| TC-076853 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Mercha | Running total / state | Positive | pending_sme | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Merchandise Return | [TD-TC-076853.step1.return.json](test_data/TD-TC-076853.step1.return.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076853.step2.totals.json](test_data/TD-TC-076853.step2.totals.json) |
| TC-076854 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Mercha | Response / oracle check | Negative | pending_sme | p.546; p.546; BR-402-3 p.402 | 1 | Merchandise Return | [TD-TC-076854.step1.return.json](test_data/TD-TC-076854.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076854.step2.void-return.json](test_data/TD-TC-076854.step2.void-return.json) |
| TC-076855 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Mercha | Response / oracle check | Negative | pending_sme | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-076855.json](test_data/TD-TC-076855.json) |

### SC-ATL105-7E0F33A6 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device @ Petroleum · attended POS
- Status: unconfirmed; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077024 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device M | Happy path | Positive | pending_sme | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-077024.json](test_data/TD-TC-077024.json) |

### SC-ATL105-D18425E7 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device @ Supermarket · attended POS
- Status: unconfirmed; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-30E7E142 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Contactless Mobile Commerce Device @ Utilities · attended POS
- Status: unconfirmed; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-9E4464F0 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Credential on File @ Credential on File · customer present; card not present
- Status: unconfirmed; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076835 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return → a | Happy path | Positive | pending_sme | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-076835.json](test_data/TD-TC-076835.json) |
| TC-076836 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return, th | Single rule broken | Negative | pending_sme | p.546; p.546; p.402 | 1 | Merchandise Return | [TD-TC-076836.step1.return.json](test_data/TD-TC-076836.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076836.step2.void-return.json](test_data/TD-TC-076836.step2.void-return.json) |
| TC-076837 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return, th | Single rule broken | Negative | pending_sme | p.546; p.546; p.40 | 1 | Merchandise Return | [TD-TC-076837.step1.return.json](test_data/TD-TC-076837.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076837.step2.void-return.json](test_data/TD-TC-076837.step2.void-return.json) |
| TC-076838 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return, th | Valid flow | Positive | pending_sme | p.546; p.546; BR-402-3 p.402 | 1 | Merchandise Return | [TD-TC-076838.step1.return.json](test_data/TD-TC-076838.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076838.step2.void-return.json](test_data/TD-TC-076838.step2.void-return.json) |
| TC-076839 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return, th | Invalid state transition | Negative | pending_sme | p.546; p.511; p.83 | 1 | Merchandise Return | [TD-TC-076839.step1.return.json](test_data/TD-TC-076839.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076839.step2.void-return.json](test_data/TD-TC-076839.step2.void-return.json) |
|  |  |  |  |  | 3 | Void of a Merchandise Return | [TD-TC-076839.step3.void-return.json](test_data/TD-TC-076839.step3.void-return.json) |
| TC-076840 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Void of a Merchandise  | Invalid state transition | Negative | pending_sme | p.546; p.511; p.83 | 1 | Void of a Merchandise Return | [TD-TC-076840.json](test_data/TD-TC-076840.json) |
| TC-076841 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return (no | Fault / recovery | Positive | pending_sme | BR-83-1 p.83; p.511; p.83 | 1 | Merchandise Return | [TD-TC-076841.step1.return.json](test_data/TD-TC-076841.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076841.step2.void-return.json](test_data/TD-TC-076841.step2.void-return.json) |
| TC-076842 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return, th | Running total / state | Positive | pending_sme | p.546; p.546; BR-181-1 p.181 | 1 | Merchandise Return | [TD-TC-076842.step1.return.json](test_data/TD-TC-076842.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076842.step2.void-return.json](test_data/TD-TC-076842.step2.void-return.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076842.step3.totals.json](test_data/TD-TC-076842.step3.totals.json) |
| TC-076843 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return, th | Running total / state | Positive | pending_sme | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Merchandise Return | [TD-TC-076843.step1.return.json](test_data/TD-TC-076843.step1.return.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076843.step2.totals.json](test_data/TD-TC-076843.step2.totals.json) |
| TC-076844 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return, th | Response / oracle check | Negative | pending_sme | p.546; p.546; BR-402-3 p.402 | 1 | Merchandise Return | [TD-TC-076844.step1.return.json](test_data/TD-TC-076844.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076844.step2.void-return.json](test_data/TD-TC-076844.step2.void-return.json) |
| TC-076845 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Merchandise Return (ca | Response / oracle check | Negative | pending_sme | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-076845.json](test_data/TD-TC-076845.json) |

### SC-ATL105-09CE3848 - BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076773 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return → app | Happy path | Positive | ready | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-076773.json](test_data/TD-TC-076773.json) |
| TC-076774 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return, then | Single rule broken | Negative | requires_review | p.546; p.546; p.402 | 1 | Merchandise Return | [TD-TC-076774.step1.return.json](test_data/TD-TC-076774.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076774.step2.void-return.json](test_data/TD-TC-076774.step2.void-return.json) |
| TC-076775 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return, then | Single rule broken | Negative | requires_review | p.546; p.546; p.40 | 1 | Merchandise Return | [TD-TC-076775.step1.return.json](test_data/TD-TC-076775.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076775.step2.void-return.json](test_data/TD-TC-076775.step2.void-return.json) |
| TC-076776 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return, then | Valid flow | Positive | requires_review | p.546; p.546; BR-402-3 p.402 | 1 | Merchandise Return | [TD-TC-076776.step1.return.json](test_data/TD-TC-076776.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076776.step2.void-return.json](test_data/TD-TC-076776.step2.void-return.json) |
| TC-076777 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return, then | Invalid state transition | Negative | requires_review | p.546; p.511; p.83 | 1 | Merchandise Return | [TD-TC-076777.step1.return.json](test_data/TD-TC-076777.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076777.step2.void-return.json](test_data/TD-TC-076777.step2.void-return.json) |
|  |  |  |  |  | 3 | Void of a Merchandise Return | [TD-TC-076777.step3.void-return.json](test_data/TD-TC-076777.step3.void-return.json) |
| TC-076778 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Void of a Merchandise Re | Invalid state transition | Negative | blocked_verification | p.546; p.511; p.83 | 1 | Void of a Merchandise Return | [TD-TC-076778.json](test_data/TD-TC-076778.json) |
| TC-076779 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return, then | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Merchandise Return | [TD-TC-076779.step1.return.json](test_data/TD-TC-076779.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076779.step2.void-return.json](test_data/TD-TC-076779.step2.void-return.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076779.step3.totals.json](test_data/TD-TC-076779.step3.totals.json) |
| TC-076780 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return, then | Running total / state | Positive | requires_review | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Merchandise Return | [TD-TC-076780.step1.return.json](test_data/TD-TC-076780.step1.return.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076780.step2.totals.json](test_data/TD-TC-076780.step2.totals.json) |
| TC-076781 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return on a  | Interaction (pairwise) | Positive | ready | p.546; BR-72-6 p.72; BR-FISERV-EMV-GUIDE-49-2 p.49 | 1 | Merchandise Return | [TD-TC-076781.json](test_data/TD-TC-076781.json) |
| TC-076782 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return, then | Response / oracle check | Negative | requires_review | p.546; p.546; BR-402-3 p.402 | 1 | Merchandise Return | [TD-TC-076782.step1.return.json](test_data/TD-TC-076782.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076782.step2.void-return.json](test_data/TD-TC-076782.step2.void-return.json) |
| TC-076783 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return (card | Response / oracle check | Negative | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-076783.json](test_data/TD-TC-076783.json) |
| TC-077183 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return Requi | Single rule broken | Negative | ready | BR-466-3 p.466; p. | 1 |  | [TD-TC-077183.json](test_data/TD-TC-077183.json) |
| TC-077221 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Merchandise Return Varia | Single rule broken | Negative | ready | BR-149-1 p.149; BR-146-2 p.146; BR-147-1 p.147 | 1 |  | [TD-TC-077221.json](test_data/TD-TC-077221.json) |

### SC-ATL105-D1464CF1 - BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077018 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Merchandise Return  | Happy path | Positive | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-077018.json](test_data/TD-TC-077018.json) |
| TC-077196 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Merchandise Return  | Single rule broken | Negative | needs_client_input | BR-BUYPASS-PIPS-179-1 p.179; p. | 1 |  | [TD-TC-077196.json](test_data/TD-TC-077196.json) |
| TC-077200 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Merchandise Return  | Single rule broken | Negative | needs_client_input | BR-BUYPASS-PIPS-181-2 p.181; p. | 1 |  | [TD-TC-077200.json](test_data/TD-TC-077200.json) |

### SC-ATL105-3104BAEA - BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip @ Supermarket · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-A9025C95 - BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contact Chip @ Utilities · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-7E915769 - BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076784 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return → | Happy path | Positive | ready | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-076784.json](test_data/TD-TC-076784.json) |
| TC-076785 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return,  | Single rule broken | Negative | requires_review | p.546; p.546; p.402 | 1 | Merchandise Return | [TD-TC-076785.step1.return.json](test_data/TD-TC-076785.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076785.step2.void-return.json](test_data/TD-TC-076785.step2.void-return.json) |
| TC-076786 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return,  | Single rule broken | Negative | requires_review | p.546; p.546; p.40 | 1 | Merchandise Return | [TD-TC-076786.step1.return.json](test_data/TD-TC-076786.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076786.step2.void-return.json](test_data/TD-TC-076786.step2.void-return.json) |
| TC-076787 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return,  | Valid flow | Positive | requires_review | p.546; p.546; BR-402-3 p.402 | 1 | Merchandise Return | [TD-TC-076787.step1.return.json](test_data/TD-TC-076787.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076787.step2.void-return.json](test_data/TD-TC-076787.step2.void-return.json) |
| TC-076788 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return,  | Invalid state transition | Negative | requires_review | p.546; p.511; p.83 | 1 | Merchandise Return | [TD-TC-076788.step1.return.json](test_data/TD-TC-076788.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076788.step2.void-return.json](test_data/TD-TC-076788.step2.void-return.json) |
|  |  |  |  |  | 3 | Void of a Merchandise Return | [TD-TC-076788.step3.void-return.json](test_data/TD-TC-076788.step3.void-return.json) |
| TC-076789 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Void of a Merchandis | Invalid state transition | Negative | blocked_verification | p.546; p.511; p.83 | 1 | Void of a Merchandise Return | [TD-TC-076789.json](test_data/TD-TC-076789.json) |
| TC-076790 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return,  | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Merchandise Return | [TD-TC-076790.step1.return.json](test_data/TD-TC-076790.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076790.step2.void-return.json](test_data/TD-TC-076790.step2.void-return.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076790.step3.totals.json](test_data/TD-TC-076790.step3.totals.json) |
| TC-076791 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return,  | Running total / state | Positive | requires_review | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Merchandise Return | [TD-TC-076791.step1.return.json](test_data/TD-TC-076791.step1.return.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076791.step2.totals.json](test_data/TD-TC-076791.step2.totals.json) |
| TC-076792 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return o | Interaction (pairwise) | Positive | ready | p.546; BR-72-6 p.72; BR-FISERV-EMV-GUIDE-49-2 p.49 | 1 | Merchandise Return | [TD-TC-076792.json](test_data/TD-TC-076792.json) |
| TC-076793 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return,  | Response / oracle check | Negative | requires_review | p.546; p.546; BR-402-3 p.402 | 1 | Merchandise Return | [TD-TC-076793.step1.return.json](test_data/TD-TC-076793.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076793.step2.void-return.json](test_data/TD-TC-076793.step2.void-return.json) |
| TC-076794 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Merchandise Return ( | Response / oracle check | Negative | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-076794.json](test_data/TD-TC-076794.json) |

### SC-ATL105-DC794243 - BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077019 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Merchandise Ret | Happy path | Positive | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-077019.json](test_data/TD-TC-077019.json) |

### SC-ATL105-7FF90FB7 - BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip @ Supermarket · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-88B8EA02 - BUYPASS ATL105 MasterCard Credit Merchandise Return – EMV Contactless Chip @ Utilities · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-975B8254 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe @ General Retail · attended POS
- Status: spec_implied; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076795 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return → appr | Happy path | Positive | ready | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-076795.json](test_data/TD-TC-076795.json) |
| TC-076796 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return, then  | Single rule broken | Negative | requires_review | p.546; p.546; p.402 | 1 | Merchandise Return | [TD-TC-076796.step1.return.json](test_data/TD-TC-076796.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076796.step2.void-return.json](test_data/TD-TC-076796.step2.void-return.json) |
| TC-076797 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return, then  | Single rule broken | Negative | requires_review | p.546; p.546; p.40 | 1 | Merchandise Return | [TD-TC-076797.step1.return.json](test_data/TD-TC-076797.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076797.step2.void-return.json](test_data/TD-TC-076797.step2.void-return.json) |
| TC-076798 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return, then  | Valid flow | Positive | ready | p.546; p.546; BR-402-3 p.402 | 1 | Merchandise Return | [TD-TC-076798.step1.return.json](test_data/TD-TC-076798.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076798.step2.void-return.json](test_data/TD-TC-076798.step2.void-return.json) |
| TC-076799 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return, then  | Invalid state transition | Negative | requires_review | p.546; p.511; p.83 | 1 | Merchandise Return | [TD-TC-076799.step1.return.json](test_data/TD-TC-076799.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076799.step2.void-return.json](test_data/TD-TC-076799.step2.void-return.json) |
|  |  |  |  |  | 3 | Void of a Merchandise Return | [TD-TC-076799.step3.void-return.json](test_data/TD-TC-076799.step3.void-return.json) |
| TC-076800 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Void of a Merchandise Ret | Invalid state transition | Negative | blocked_verification | p.546; p.511; p.83 | 1 | Void of a Merchandise Return | [TD-TC-076800.json](test_data/TD-TC-076800.json) |
| TC-076801 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return, then  | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Merchandise Return | [TD-TC-076801.step1.return.json](test_data/TD-TC-076801.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076801.step2.void-return.json](test_data/TD-TC-076801.step2.void-return.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076801.step3.totals.json](test_data/TD-TC-076801.step3.totals.json) |
| TC-076802 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return, then  | Running total / state | Positive | requires_review | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Merchandise Return | [TD-TC-076802.step1.return.json](test_data/TD-TC-076802.step1.return.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076802.step2.totals.json](test_data/TD-TC-076802.step2.totals.json) |
| TC-076803 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return, then  | Response / oracle check | Negative | needs_client_input | p.546; p.546; BR-402-3 p.402 | 1 | Merchandise Return | [TD-TC-076803.step1.return.json](test_data/TD-TC-076803.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076803.step2.void-return.json](test_data/TD-TC-076803.step2.void-return.json) |
| TC-076804 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Merchandise Return (card  | Response / oracle check | Negative | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-076804.json](test_data/TD-TC-076804.json) |

### SC-ATL105-B3DE8567 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe @ Petroleum · attended POS
- Status: spec_implied; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077020 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Merchandise Return → | Happy path | Positive | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-077020.json](test_data/TD-TC-077020.json) |

### SC-ATL105-5446114B - BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe @ Supermarket · attended POS
- Status: spec_implied; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-D9BBE155 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Magnetic Stripe @ Utilities · attended POS
- Status: spec_implied; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-60C55097 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076815 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return → appro | Happy path | Positive | ready | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-076815.json](test_data/TD-TC-076815.json) |
| TC-076816 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return, then V | Single rule broken | Negative | requires_review | p.546; p.546; p.402 | 1 | Merchandise Return | [TD-TC-076816.step1.return.json](test_data/TD-TC-076816.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076816.step2.void-return.json](test_data/TD-TC-076816.step2.void-return.json) |
| TC-076817 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return, then V | Single rule broken | Negative | requires_review | p.546; p.546; p.40 | 1 | Merchandise Return | [TD-TC-076817.step1.return.json](test_data/TD-TC-076817.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076817.step2.void-return.json](test_data/TD-TC-076817.step2.void-return.json) |
| TC-076818 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return, then V | Valid flow | Positive | ready | p.546; p.546; BR-402-3 p.402 | 1 | Merchandise Return | [TD-TC-076818.step1.return.json](test_data/TD-TC-076818.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076818.step2.void-return.json](test_data/TD-TC-076818.step2.void-return.json) |
| TC-076819 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return, then V | Invalid state transition | Negative | requires_review | p.546; p.511; p.83 | 1 | Merchandise Return | [TD-TC-076819.step1.return.json](test_data/TD-TC-076819.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076819.step2.void-return.json](test_data/TD-TC-076819.step2.void-return.json) |
|  |  |  |  |  | 3 | Void of a Merchandise Return | [TD-TC-076819.step3.void-return.json](test_data/TD-TC-076819.step3.void-return.json) |
| TC-076820 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Void of a Merchandise Retu | Invalid state transition | Negative | blocked_verification | p.546; p.511; p.83 | 1 | Void of a Merchandise Return | [TD-TC-076820.json](test_data/TD-TC-076820.json) |
| TC-076821 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return, then V | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Merchandise Return | [TD-TC-076821.step1.return.json](test_data/TD-TC-076821.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076821.step2.void-return.json](test_data/TD-TC-076821.step2.void-return.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076821.step3.totals.json](test_data/TD-TC-076821.step3.totals.json) |
| TC-076822 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return, then T | Running total / state | Positive | requires_review | p.546; BR-181-1 p.181; BR-183-1 p.183 | 1 | Merchandise Return | [TD-TC-076822.step1.return.json](test_data/TD-TC-076822.step1.return.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076822.step2.totals.json](test_data/TD-TC-076822.step2.totals.json) |
| TC-076823 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return, then V | Response / oracle check | Negative | needs_client_input | p.546; p.546; BR-402-3 p.402 | 1 | Merchandise Return | [TD-TC-076823.step1.return.json](test_data/TD-TC-076823.step1.return.json) |
|  |  |  |  |  | 2 | Void of a Merchandise Return | [TD-TC-076823.step2.void-return.json](test_data/TD-TC-076823.step2.void-return.json) |
| TC-076824 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Merchandise Return (card i | Response / oracle check | Negative | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-076824.json](test_data/TD-TC-076824.json) |

### SC-ATL105-DDF070B6 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077022 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Merchandise Return →  | Happy path | Positive | needs_client_input | p.546; BR-72-6 p.72; BR-ATL105-TRANSARMOR-21-1 p.21 | 1 | Merchandise Return | [TD-TC-077022.json](test_data/TD-TC-077022.json) |

### SC-ATL105-9B63DA61 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed @ Supermarket · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-ABF3F8D8 - BUYPASS ATL105 MasterCard Credit Merchandise Return – Manual / Keyed @ Utilities · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-A0EF7F0D - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076221 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture → approved | Happy path | Positive | ready | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-076221.json](test_data/TD-TC-076221.json) |
| TC-076222 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with UPC I | Happy path | Positive | needs_client_input | p.552; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076222.json](test_data/TD-TC-076222.json) |
| TC-076223 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Curre | Happy path | Positive | needs_client_input | p.555; BR-572-3 p.572; p.511 | 1 | Purchase/Capture | [TD-TC-076223.json](test_data/TD-TC-076223.json) |
| TC-076224 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Balan | Happy path | Positive | needs_client_input | p.556; BR-92-11 p.92; p.511 | 1 | Purchase/Capture | [TD-TC-076224.json](test_data/TD-TC-076224.json) |
| TC-076225 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Suspe | Happy path | Positive | needs_client_input | p.561; BR-85-4 p.85; BR-561-2 p.561 | 1 | Purchase/Capture | [TD-TC-076225.json](test_data/TD-TC-076225.json) |
| TC-076226 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Card  | Happy path | Positive | needs_client_input | p.562; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076226.json](test_data/TD-TC-076226.json) |
| TC-076227 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Recha | Happy path | Positive | needs_client_input | p.562; BR-562-2 p.562; p.511 | 1 | Purchase/Capture | [TD-TC-076227.json](test_data/TD-TC-076227.json) |
| TC-076228 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Origi | Happy path | Positive | ready | p.564; BR-564-1 p.564; BR-FISERV-EMV-GUIDE-64-2 p.64 | 1 | Purchase/Capture | [TD-TC-076228.json](test_data/TD-TC-076228.json) |
| TC-076229 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Custo | Happy path | Positive | needs_client_input | p.566; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076229.json](test_data/TD-TC-076229.json) |
| TC-076230 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Point | Happy path | Positive | ready | p.569; BR-569-1 p.569; p.511 | 1 | Purchase/Capture | [TD-TC-076230.json](test_data/TD-TC-076230.json) |
| TC-076231 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Maste | Happy path | Positive | needs_client_input | p.581; BR-428-8 p.428; BR-581-1 p.581 | 1 | Purchase/Capture | [TD-TC-076231.json](test_data/TD-TC-076231.json) |
| TC-076232 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Paydi | Happy path | Positive | needs_client_input | p.582; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076232.json](test_data/TD-TC-076232.json) |
| TC-076233 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Fraud | Happy path | Positive | needs_client_input | p.582; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076233.json](test_data/TD-TC-076233.json) |
| TC-076234 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Final | Happy path | Positive | needs_client_input | p.583; BR-583-1 p.583; BR-583-2 p.583 | 1 | Purchase/Capture | [TD-TC-076234.json](test_data/TD-TC-076234.json) |
| TC-076235 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Count | Happy path | Positive | needs_client_input | p.583; BR-583-3 p.583; p.511 | 1 | Purchase/Capture | [TD-TC-076235.json](test_data/TD-TC-076235.json) |
| TC-076236 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Cardh | Happy path | Positive | needs_client_input | p.583; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076236.json](test_data/TD-TC-076236.json) |
| TC-076237 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Previ | Happy path | Positive | needs_client_input | p.585; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076237.json](test_data/TD-TC-076237.json) |
| TC-076238 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Soft  | Happy path | Positive | needs_client_input | p.586; BR-590-4 p.590; p.511 | 1 | Purchase/Capture | [TD-TC-076238.json](test_data/TD-TC-076238.json) |
| TC-076239 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Maste | Happy path | Positive | ready | p.586; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076239.json](test_data/TD-TC-076239.json) |
| TC-076240 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Addit | Happy path | Positive | needs_client_input | p.600; BR-600-2 p.600; p.511 | 1 | Purchase/Capture | [TD-TC-076240.json](test_data/TD-TC-076240.json) |
| TC-076241 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Addit | Happy path | Positive | needs_client_input | p.600; BR-600-2 p.600; BR-600-3 p.600 | 1 | Purchase/Capture | [TD-TC-076241.json](test_data/TD-TC-076241.json) |
| TC-076242 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Addit | Happy path | Positive | needs_client_input | p.601; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076242.json](test_data/TD-TC-076242.json) |
| TC-076243 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Remot | Happy path | Positive | needs_client_input | p.602; BR-602-1 p.602; p.511 | 1 | Purchase/Capture | [TD-TC-076243.json](test_data/TD-TC-076243.json) |
| TC-076244 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Exper | Happy path | Positive | needs_client_input | p.602; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076244.json](test_data/TD-TC-076244.json) |
| TC-076245 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Soft  | Happy path | Positive | needs_client_input | p.613; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076245.json](test_data/TD-TC-076245.json) |
| TC-076246 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Real  | Happy path | Positive | needs_client_input | p.613; BR-614-4 p.614; BR-614-8 p.614 | 1 | Purchase/Capture | [TD-TC-076246.json](test_data/TD-TC-076246.json) |
| TC-076247 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Visa® | Happy path | Positive | needs_client_input | p.615; BR-637-2 p.637; BR-FISERV-EMV-GUIDE-38-6 p.38 | 1 | Purchase/Capture | [TD-TC-076247.json](test_data/TD-TC-076247.json) |
| TC-076248 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Servi | Happy path | Positive | needs_client_input | p.620; BR-620-2 p.620; p.511 | 1 | Purchase/Capture | [TD-TC-076248.json](test_data/TD-TC-076248.json) |
| TC-076249 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Merch | Happy path | Positive | needs_client_input | p.622; BR-622-5 p.622; BR-623-1 p.623 | 1 | Purchase/Capture | [TD-TC-076249.json](test_data/TD-TC-076249.json) |
| TC-076250 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Digit | Happy path | Positive | needs_client_input | p.623; BR-623-5 p.623; p.511 | 1 | Purchase/Capture | [TD-TC-076250.json](test_data/TD-TC-076250.json) |
| TC-076251 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Antic | Happy path | Positive | needs_client_input | p.625; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076251.json](test_data/TD-TC-076251.json) |
| TC-076252 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Antic | Happy path | Positive | needs_client_input | p.626; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076252.json](test_data/TD-TC-076252.json) |
| TC-076253 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Futur | Happy path | Positive | needs_client_input | p.628; BR-628-2 p.628; p.511 | 1 | Purchase/Capture | [TD-TC-076253.json](test_data/TD-TC-076253.json) |
| TC-076254 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Disco | Happy path | Positive | needs_client_input | p.628; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076254.json](test_data/TD-TC-076254.json) |
| TC-076255 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Disco | Happy path | Positive | needs_client_input | p.629; BR-629-1 p.629; p.511 | 1 | Purchase/Capture | [TD-TC-076255.json](test_data/TD-TC-076255.json) |
| TC-076256 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Stage | Happy path | Positive | needs_client_input | p.636; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076256.json](test_data/TD-TC-076256.json) |
| TC-076257 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-72-10 p.72; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076257.json](test_data/TD-TC-076257.json) |
| TC-076258 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-71-5 p.71; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076258.json](test_data/TD-TC-076258.json) |
| TC-076259 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-189-3 p.189; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076259.json](test_data/TD-TC-076259.json) |
| TC-076260 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-71-6 p.71; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076260.json](test_data/TD-TC-076260.json) |
| TC-076261 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-71-4 p.71; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076261.json](test_data/TD-TC-076261.json) |
| TC-076262 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-115-20 p.115; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076262.json](test_data/TD-TC-076262.json) |
| TC-076263 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-42-2 p.42; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076263.json](test_data/TD-TC-076263.json) |
| TC-076264 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-142-3 p.142; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076264.json](test_data/TD-TC-076264.json) |
| TC-076265 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-577-1 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076265.json](test_data/TD-TC-076265.json) |
| TC-076266 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-577-2 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076266.json](test_data/TD-TC-076266.json) |
| TC-076267 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-597-1 p.597; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076267.json](test_data/TD-TC-076267.json) |
| TC-076268 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-70-4 p.70; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076268.json](test_data/TD-TC-076268.json) |
| TC-076269 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-69-3 p.69; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076269.json](test_data/TD-TC-076269.json) |
| TC-076270 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture via the al | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-37-10 p.37; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076270.json](test_data/TD-TC-076270.json) |
| TC-076271 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purc | Single rule broken | Negative | requires_review | p.546; p.546; p.402 | 1 | Purchase/Capture | [TD-TC-076271.step1.purchase.json](test_data/TD-TC-076271.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076271.step2.reversal.json](test_data/TD-TC-076271.step2.reversal.json) |
| TC-076272 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purc | Single rule broken | Negative | requires_review | p.546; p.546; p.40 | 1 | Purchase/Capture | [TD-TC-076272.step1.purchase.json](test_data/TD-TC-076272.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076272.step2.reversal.json](test_data/TD-TC-076272.step2.reversal.json) |
| TC-076273 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Time | Single rule broken | Negative | requires_review | p.546; p.39; p.38 | 1 | Purchase/Capture | [TD-TC-076273.step1.purchase.json](test_data/TD-TC-076273.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076273.step2.timeout-reversal.json](test_data/TD-TC-076273.step2.timeout-reversal.json) |
| TC-076274 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Payme | Single rule broken | Negative | requires_review | BR-FISERV-EMV-GUIDE-70-10 p.70; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076274.json](test_data/TD-TC-076274.json) |
| TC-076275 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Servi | Single rule broken | Negative | requires_review | BR-ATL105-TRANSARMOR-13-7 p.13; BR-FISERV-EMV-GUIDE-75-3 p.75; p.511 | 1 | Purchase/Capture | [TD-TC-076275.json](test_data/TD-TC-076275.json) |
| TC-076276 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Payme | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-70-10 p.70; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076276.json](test_data/TD-TC-076276.json) |
| TC-076277 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Servi | Decision table | Positive | ready | BR-ATL105-TRANSARMOR-13-7 p.13; BR-FISERV-EMV-GUIDE-75-3 p.75; p.511 | 1 | Purchase/Capture | [TD-TC-076277.json](test_data/TD-TC-076277.json) |
| TC-076278 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Tax T | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-156-8 p.156; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076278.json](test_data/TD-TC-076278.json) |
| TC-076279 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Updat | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-38-2 p.38; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076279.json](test_data/TD-TC-076279.json) |
| TC-076280 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Varia | Decision table | Positive | ready | BR-149-1 p.149; BR-152-1 p.152; p.511 | 1 | Purchase/Capture | [TD-TC-076280.json](test_data/TD-TC-076280.json) |
| TC-076281 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with avs = | Decision table | Positive | needs_client_input | BR-315-4 p.315; BR-554-4 p.554; BR-622-1 p.622 | 1 | Purchase/Capture | [TD-TC-076281.json](test_data/TD-TC-076281.json) |
| TC-076282 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with cof = | Decision table | Positive | ready | BR-53-2 p.53; BR-56-8 p.56; BR-364-3 p.364 | 1 | Purchase/Capture | [TD-TC-076282.json](test_data/TD-TC-076282.json) |
| TC-076283 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with cvm = | Decision table | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-50-5 p.50; BR-FISERV-EMV-GUIDE-87-2 p.87; BR-FISERV-EMV-GUIDE-152-5 p.152 | 1 | Purchase/Capture | [TD-TC-076283.json](test_data/TD-TC-076283.json) |
| TC-076284 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with optio | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-53-3 p.53; BR-FISERV-EMV-GUIDE-53-4 p.53; BR-FISERV-EMV-GUIDE-43-4 p.43 | 1 | Purchase/Capture | [TD-TC-076284.json](test_data/TD-TC-076284.json) |
| TC-076285 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with optio | Decision table | Positive | ready | BR-156-7 p.156; BR-688-2 p.688; p.511 | 1 | Purchase/Capture | [TD-TC-076285.json](test_data/TD-TC-076285.json) |
| TC-076286 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with optio | Decision table | Positive | ready | BR-313-3 p.313; BR-ATL105-TRANSARMOR-47-5 p.47; BR-312-1 p.312 | 1 | Purchase/Capture | [TD-TC-076286.json](test_data/TD-TC-076286.json) |
| TC-076287 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Produ | Decision table | Positive | ready | BR-89-3 p.89; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076287.json](test_data/TD-TC-076287.json) |
| TC-076288 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Varia | Decision table | Positive | ready | BR-ATL105-TRANSARMOR-28-4 p.28; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076288.json](test_data/TD-TC-076288.json) |
| TC-076289 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purc | Valid flow | Positive | ready | p.546; p.546; BR-402-3 p.402 | 1 | Purchase/Capture | [TD-TC-076289.step1.purchase.json](test_data/TD-TC-076289.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076289.step2.reversal.json](test_data/TD-TC-076289.step2.reversal.json) |
| TC-076290 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Time | Valid flow | Positive | ready | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-076290.step1.purchase.json](test_data/TD-TC-076290.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076290.step2.timeout-reversal.json](test_data/TD-TC-076290.step2.timeout-reversal.json) |
| TC-076291 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purc | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-076291.step1.purchase.json](test_data/TD-TC-076291.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076291.step2.split-tender.json](test_data/TD-TC-076291.step2.split-tender.json) |
| TC-076292 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Time | Invalid state transition | Negative | requires_review | p.546; BR-39-1 p.39; p.511 | 1 | Purchase/Capture | [TD-TC-076292.step1.purchase.json](test_data/TD-TC-076292.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076292.step2.timeout-reversal.json](test_data/TD-TC-076292.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-076292.step3.timeout-reversal.json](test_data/TD-TC-076292.step3.timeout-reversal.json) |
| TC-076293 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Time-out Reversal with no o | Invalid state transition | Negative | blocked_verification | p.546; BR-39-1 p.39; p.511 | 1 | Time-out Reversal | [TD-TC-076293.json](test_data/TD-TC-076293.json) |
| TC-076294 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (no respon | Fault / recovery | Positive | ready | BR-409-4 p.409; BR-ATL105-TRANSARMOR-76-3 p.76; p.511 | 1 | Purchase/Capture | [TD-TC-076294.step1.purchase.json](test_data/TD-TC-076294.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076294.step2.reversal.json](test_data/TD-TC-076294.step2.reversal.json) |
| TC-076295 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (no respon | Fault / recovery | Positive | ready | BR-39-1 p.39; BR-44-1 p.44; BR-43-2 p.43 | 1 | Purchase/Capture | [TD-TC-076295.step1.purchase.json](test_data/TD-TC-076295.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076295.step2.timeout-reversal.json](test_data/TD-TC-076295.step2.timeout-reversal.json) |
| TC-076296 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (no respon | Idempotency / duplicate / retry | Positive | ready | BR-47-1 p.47; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076296.step1.purchase.json](test_data/TD-TC-076296.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076296.step2.timeout-reversal.json](test_data/TD-TC-076296.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-076296.step3.timeout-reversal.json](test_data/TD-TC-076296.step3.timeout-reversal.json) |
|  |  |  |  |  | 4 | Time-out Reversal | [TD-TC-076296.step4.timeout-reversal.json](test_data/TD-TC-076296.step4.timeout-reversal.json) |
| TC-076297 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purc | Time-based | Positive | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076297.step1.purchase.json](test_data/TD-TC-076297.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076297.step2.reversal.json](test_data/TD-TC-076297.step2.reversal.json) |
| TC-076298 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purc | Time-based | Negative | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076298.step1.purchase.json](test_data/TD-TC-076298.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076298.step2.reversal.json](test_data/TD-TC-076298.step2.reversal.json) |
| TC-076299 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Time | Time-based | Positive | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076299.step1.purchase.json](test_data/TD-TC-076299.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076299.step2.timeout-reversal.json](test_data/TD-TC-076299.step2.timeout-reversal.json) |
| TC-076300 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Time | Time-based | Negative | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076300.step1.purchase.json](test_data/TD-TC-076300.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076300.step2.timeout-reversal.json](test_data/TD-TC-076300.step2.timeout-reversal.json) |
| TC-076301 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purc | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Purchase/Capture | [TD-TC-076301.step1.purchase.json](test_data/TD-TC-076301.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076301.step2.reversal.json](test_data/TD-TC-076301.step2.reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076301.step3.totals.json](test_data/TD-TC-076301.step3.totals.json) |
| TC-076302 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Time | Running total / state | Positive | requires_review | p.546; p.39; BR-181-1 p.181 | 1 | Purchase/Capture | [TD-TC-076302.step1.purchase.json](test_data/TD-TC-076302.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076302.step2.timeout-reversal.json](test_data/TD-TC-076302.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076302.step3.totals.json](test_data/TD-TC-076302.step3.totals.json) |
| TC-076303 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Tota | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Purchase/Capture | [TD-TC-076303.step1.purchase.json](test_data/TD-TC-076303.step1.purchase.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076303.step2.totals.json](test_data/TD-TC-076303.step2.totals.json) |
| TC-076304 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (Additiona | Response / oracle check | Positive | ready | BR-650-4 p.650; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076304.json](test_data/TD-TC-076304.json) |
| TC-076305 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (Cardholde | Response / oracle check | Positive | ready | BR-FISERV-EMV-GUIDE-146-3 p.146; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076305.json](test_data/TD-TC-076305.json) |
| TC-076306 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (Network T | Response / oracle check | Positive | ready | BR-339-4 p.339; BR-340-2 p.340; BR-340-3 p.340 | 1 | Purchase/Capture | [TD-TC-076306.json](test_data/TD-TC-076306.json) |
| TC-076307 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Purc | Response / oracle check | Negative | needs_client_input | p.546; p.546; BR-402-3 p.402 | 1 | Purchase/Capture | [TD-TC-076307.step1.purchase.json](test_data/TD-TC-076307.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076307.step2.reversal.json](test_data/TD-TC-076307.step2.reversal.json) |
| TC-076308 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (Real Time | Response / oracle check | Positive | ready | BR-317-5 p.317; BR-317-9 p.317; p.511 | 1 | Purchase/Capture | [TD-TC-076308.json](test_data/TD-TC-076308.json) |
| TC-076309 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture, then Time | Response / oracle check | Negative | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-076309.step1.purchase.json](test_data/TD-TC-076309.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076309.step2.timeout-reversal.json](test_data/TD-TC-076309.step2.timeout-reversal.json) |
| TC-076310 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (Transacti | Response / oracle check | Positive | ready | BR-FISERV-EMV-GUIDE-138-2 p.138; BR-FISERV-EMV-GUIDE-138-4 p.138; BR-FISERV-EMV-GUIDE-141-4 p.141 | 1 | Purchase/Capture | [TD-TC-076310.json](test_data/TD-TC-076310.json) |
| TC-076311 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (Transacti | Response / oracle check | Positive | ready | BR-62-6 p.62; BR-94-5 p.94; BR-607-3 p.607 | 1 | Purchase/Capture | [TD-TC-076311.json](test_data/TD-TC-076311.json) |
| TC-076312 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture (card issu | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-076312.json](test_data/TD-TC-076312.json) |
| TC-076313 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture sending Me | Response / oracle check | Positive | ready | BR-545-5 p.545; BR-664-4 p.664; BR-663-1 p.663 | 1 | Purchase/Capture | [TD-TC-076313.json](test_data/TD-TC-076313.json) |
| TC-076314 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture sending Me | Response / oracle check | Positive | ready | BR-671-6 p.671; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076314.json](test_data/TD-TC-076314.json) |
| TC-076315 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture sending Pr | Response / oracle check | Positive | needs_client_input | BR-656-3 p.656; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076315.json](test_data/TD-TC-076315.json) |
| TC-076316 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Progr | Response / oracle check | Positive | needs_client_input | p.552; BR-552-2 p.552; BR-552-6 p.552 | 1 | Purchase/Capture | [TD-TC-076316.json](test_data/TD-TC-076316.json) |
| TC-076317 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Re-Pr | Response / oracle check | Positive | needs_client_input | p.578; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076317.json](test_data/TD-TC-076317.json) |
| TC-076318 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with MCX C | Response / oracle check | Positive | needs_client_input | p.582; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076318.json](test_data/TD-TC-076318.json) |
| TC-076319 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Merch | Response / oracle check | Positive | needs_client_input | p.602; BR-602-1 p.602; p.511 | 1 | Purchase/Capture | [TD-TC-076319.json](test_data/TD-TC-076319.json) |
| TC-076320 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture with Custo | Response / oracle check | Positive | needs_client_input | p.614; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076320.json](test_data/TD-TC-076320.json) |
| TC-076321 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture sending av | Response / oracle check | Positive | needs_client_input | BR-252-3 p.252; BR-240-1 p.240; BR-266-3 p.266 | 1 | Purchase/Capture | [TD-TC-076321.json](test_data/TD-TC-076321.json) |
| TC-076322 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Chip Fallback Purchase/Capture sending in | Response / oracle check | Positive | needs_client_input | BR-669-3 p.669; BR-669-4 p.669; p.511 | 1 | Purchase/Capture | [TD-TC-076322.json](test_data/TD-TC-076322.json) |

### SC-ATL105-0B4F31B7 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Hospitality · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076894 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Chip Fallback Purchase/Capture → a | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-076894.json](test_data/TD-TC-076894.json) |
| TC-076895 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Chip Fallback Purchase/Capture (ca | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-076895.json](test_data/TD-TC-076895.json) |

### SC-ATL105-2FB57EC0 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Merchant category 6051 (cryptocurrency purchase) · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077167 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS Chip Fallback Purchase/Capture → appr | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-077167.json](test_data/TD-TC-077167.json) |

### SC-ATL105-1CC160A6 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Merchant category 6211 (high risk security) · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077174 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS Chip Fallback Purchase/Capture → appr | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-077174.json](test_data/TD-TC-077174.json) |

### SC-ATL105-00955B6A - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076981 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture → app | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-BUYPASS-PIPS-24-7 p.24 | 1 | Purchase/Capture | [TD-TC-076981.json](test_data/TD-TC-076981.json) |
| TC-076982 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture with  | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; BR-BUYPASS-PIPS-185-5 p.185 | 1 | Purchase/Capture | [TD-TC-076982.json](test_data/TD-TC-076982.json) |
| TC-076983 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase Reversal with | Invalid state transition | Negative | blocked_verification | p.546; BR-BUYPASS-PIPS-30-3 p.30; p.511 | 1 | Purchase Reversal | [TD-TC-076983.json](test_data/TD-TC-076983.json) |
| TC-076984 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture, then | Invalid state transition | Negative | needs_client_input | p.546; BR-BUYPASS-PIPS-30-3 p.30; p.511 | 1 | Purchase/Capture | [TD-TC-076984.step1.purchase.json](test_data/TD-TC-076984.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076984.step2.reversal.json](test_data/TD-TC-076984.step2.reversal.json) |
|  |  |  |  |  | 3 | Purchase Reversal | [TD-TC-076984.step3.reversal.json](test_data/TD-TC-076984.step3.reversal.json) |
| TC-076985 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture resen | Idempotency / duplicate / retry | Positive | requires_review | BR-BUYPASS-PIPS-17-2 p.17; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076985.json](test_data/TD-TC-076985.json) |
| TC-076986 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture resen | Idempotency / duplicate / retry | Negative | requires_review | BR-BUYPASS-PIPS-17-2 p.17; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076986.json](test_data/TD-TC-076986.json) |
| TC-076987 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture held  | Time-based | Positive | requires_review | BR-BUYPASS-PIPS-21-2 p.21; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076987.json](test_data/TD-TC-076987.json) |
| TC-076988 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture held  | Time-based | Negative | requires_review | BR-BUYPASS-PIPS-21-2 p.21; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076988.json](test_data/TD-TC-076988.json) |
| TC-076989 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture (Addi | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-177-2 p.177; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076989.json](test_data/TD-TC-076989.json) |
| TC-076990 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture (Tran | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-138-2 p.138; BR-FISERV-EMV-GUIDE-138-4 p.138; BR-FISERV-EMV-GUIDE-141-4 p.141 | 1 | Purchase/Capture | [TD-TC-076990.json](test_data/TD-TC-076990.json) |
| TC-076991 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Chip Fallback Purchase/Capture (card | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-076991.json](test_data/TD-TC-076991.json) |

### SC-ATL105-8A82A179 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Supermarket · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-6964A6BF - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Chip Fallback @ Utilities · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-EBD811C6 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076022 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | ready | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-076022.json](test_data/TD-TC-076022.json) |
| TC-076023 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.552; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076023.json](test_data/TD-TC-076023.json) |
| TC-076024 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.555; BR-572-3 p.572; p.511 | 1 | Purchase/Capture | [TD-TC-076024.json](test_data/TD-TC-076024.json) |
| TC-076025 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.556; BR-92-11 p.92; p.511 | 1 | Purchase/Capture | [TD-TC-076025.json](test_data/TD-TC-076025.json) |
| TC-076026 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.561; BR-85-4 p.85; BR-561-2 p.561 | 1 | Purchase/Capture | [TD-TC-076026.json](test_data/TD-TC-076026.json) |
| TC-076027 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.562; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076027.json](test_data/TD-TC-076027.json) |
| TC-076028 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.562; BR-562-2 p.562; p.511 | 1 | Purchase/Capture | [TD-TC-076028.json](test_data/TD-TC-076028.json) |
| TC-076029 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | ready | p.564; BR-564-1 p.564; BR-FISERV-EMV-GUIDE-64-2 p.64 | 1 | Purchase/Capture | [TD-TC-076029.json](test_data/TD-TC-076029.json) |
| TC-076030 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.566; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076030.json](test_data/TD-TC-076030.json) |
| TC-076031 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | ready | p.569; BR-569-1 p.569; p.511 | 1 | Purchase/Capture | [TD-TC-076031.json](test_data/TD-TC-076031.json) |
| TC-076032 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.581; BR-428-8 p.428; BR-581-1 p.581 | 1 | Purchase/Capture | [TD-TC-076032.json](test_data/TD-TC-076032.json) |
| TC-076033 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.582; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076033.json](test_data/TD-TC-076033.json) |
| TC-076034 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.582; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076034.json](test_data/TD-TC-076034.json) |
| TC-076035 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.583; BR-583-1 p.583; BR-583-2 p.583 | 1 | Purchase/Capture | [TD-TC-076035.json](test_data/TD-TC-076035.json) |
| TC-076036 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.583; BR-583-3 p.583; p.511 | 1 | Purchase/Capture | [TD-TC-076036.json](test_data/TD-TC-076036.json) |
| TC-076037 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.583; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076037.json](test_data/TD-TC-076037.json) |
| TC-076038 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.585; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076038.json](test_data/TD-TC-076038.json) |
| TC-076039 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.586; BR-590-4 p.590; p.511 | 1 | Purchase/Capture | [TD-TC-076039.json](test_data/TD-TC-076039.json) |
| TC-076040 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | ready | p.586; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076040.json](test_data/TD-TC-076040.json) |
| TC-076041 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.600; BR-600-2 p.600; p.511 | 1 | Purchase/Capture | [TD-TC-076041.json](test_data/TD-TC-076041.json) |
| TC-076042 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.600; BR-600-2 p.600; BR-600-3 p.600 | 1 | Purchase/Capture | [TD-TC-076042.json](test_data/TD-TC-076042.json) |
| TC-076043 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.601; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076043.json](test_data/TD-TC-076043.json) |
| TC-076044 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.602; BR-602-1 p.602; p.511 | 1 | Purchase/Capture | [TD-TC-076044.json](test_data/TD-TC-076044.json) |
| TC-076045 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.602; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076045.json](test_data/TD-TC-076045.json) |
| TC-076046 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.613; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076046.json](test_data/TD-TC-076046.json) |
| TC-076047 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.613; BR-614-4 p.614; BR-614-8 p.614 | 1 | Purchase/Capture | [TD-TC-076047.json](test_data/TD-TC-076047.json) |
| TC-076048 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.615; BR-637-2 p.637; BR-FISERV-EMV-GUIDE-38-6 p.38 | 1 | Purchase/Capture | [TD-TC-076048.json](test_data/TD-TC-076048.json) |
| TC-076049 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.620; BR-620-2 p.620; p.511 | 1 | Purchase/Capture | [TD-TC-076049.json](test_data/TD-TC-076049.json) |
| TC-076050 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.622; BR-622-5 p.622; BR-623-1 p.623 | 1 | Purchase/Capture | [TD-TC-076050.json](test_data/TD-TC-076050.json) |
| TC-076051 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.623; BR-623-5 p.623; p.511 | 1 | Purchase/Capture | [TD-TC-076051.json](test_data/TD-TC-076051.json) |
| TC-076052 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.625; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076052.json](test_data/TD-TC-076052.json) |
| TC-076053 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.626; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076053.json](test_data/TD-TC-076053.json) |
| TC-076054 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.628; BR-628-2 p.628; p.511 | 1 | Purchase/Capture | [TD-TC-076054.json](test_data/TD-TC-076054.json) |
| TC-076055 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.628; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076055.json](test_data/TD-TC-076055.json) |
| TC-076056 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.629; BR-629-1 p.629; p.511 | 1 | Purchase/Capture | [TD-TC-076056.json](test_data/TD-TC-076056.json) |
| TC-076057 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Happy path | Positive | needs_client_input | p.636; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076057.json](test_data/TD-TC-076057.json) |
| TC-076058 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-72-10 p.72; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076058.json](test_data/TD-TC-076058.json) |
| TC-076059 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-71-5 p.71; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076059.json](test_data/TD-TC-076059.json) |
| TC-076060 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Alternate path | Positive | requires_review | BR-189-3 p.189; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076060.json](test_data/TD-TC-076060.json) |
| TC-076061 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-71-4 p.71; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076061.json](test_data/TD-TC-076061.json) |
| TC-076062 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-115-20 p.115; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076062.json](test_data/TD-TC-076062.json) |
| TC-076063 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Alternate path | Positive | requires_review | BR-577-1 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076063.json](test_data/TD-TC-076063.json) |
| TC-076064 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Alternate path | Positive | requires_review | BR-577-2 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076064.json](test_data/TD-TC-076064.json) |
| TC-076065 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Alternate path | Positive | requires_review | BR-597-1 p.597; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076065.json](test_data/TD-TC-076065.json) |
| TC-076066 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-69-3 p.69; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076066.json](test_data/TD-TC-076066.json) |
| TC-076067 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-37-10 p.37; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076067.json](test_data/TD-TC-076067.json) |
| TC-076068 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Single rule broken | Negative | requires_review | p.546; p.546; p.402 | 1 | Purchase/Capture | [TD-TC-076068.step1.purchase.json](test_data/TD-TC-076068.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076068.step2.reversal.json](test_data/TD-TC-076068.step2.reversal.json) |
| TC-076069 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Single rule broken | Negative | requires_review | p.546; p.546; p.40 | 1 | Purchase/Capture | [TD-TC-076069.step1.purchase.json](test_data/TD-TC-076069.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076069.step2.reversal.json](test_data/TD-TC-076069.step2.reversal.json) |
| TC-076070 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Single rule broken | Negative | requires_review | p.546; p.39; p.38 | 1 | Purchase/Capture | [TD-TC-076070.step1.purchase.json](test_data/TD-TC-076070.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076070.step2.timeout-reversal.json](test_data/TD-TC-076070.step2.timeout-reversal.json) |
| TC-076071 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Single rule broken | Negative | requires_review | BR-FISERV-EMV-GUIDE-70-10 p.70; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076071.json](test_data/TD-TC-076071.json) |
| TC-076072 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Single rule broken | Negative | requires_review | BR-ATL105-TRANSARMOR-13-7 p.13; BR-FISERV-EMV-GUIDE-75-3 p.75; p.511 | 1 | Purchase/Capture | [TD-TC-076072.json](test_data/TD-TC-076072.json) |
| TC-076073 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-70-10 p.70; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076073.json](test_data/TD-TC-076073.json) |
| TC-076074 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Decision table | Positive | ready | BR-ATL105-TRANSARMOR-13-7 p.13; BR-FISERV-EMV-GUIDE-75-3 p.75; p.511 | 1 | Purchase/Capture | [TD-TC-076074.json](test_data/TD-TC-076074.json) |
| TC-076075 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-156-8 p.156; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076075.json](test_data/TD-TC-076075.json) |
| TC-076076 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-38-2 p.38; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076076.json](test_data/TD-TC-076076.json) |
| TC-076077 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Decision table | Positive | ready | BR-149-1 p.149; BR-152-1 p.152; p.511 | 1 | Purchase/Capture | [TD-TC-076077.json](test_data/TD-TC-076077.json) |
| TC-076078 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Decision table | Positive | needs_client_input | BR-315-4 p.315; BR-554-4 p.554; BR-622-1 p.622 | 1 | Purchase/Capture | [TD-TC-076078.json](test_data/TD-TC-076078.json) |
| TC-076079 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Decision table | Positive | ready | BR-53-2 p.53; BR-56-8 p.56; BR-364-3 p.364 | 1 | Purchase/Capture | [TD-TC-076079.json](test_data/TD-TC-076079.json) |
| TC-076080 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Decision table | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-50-5 p.50; BR-FISERV-EMV-GUIDE-87-2 p.87; BR-FISERV-EMV-GUIDE-152-5 p.152 | 1 | Purchase/Capture | [TD-TC-076080.json](test_data/TD-TC-076080.json) |
| TC-076081 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-53-3 p.53; BR-FISERV-EMV-GUIDE-53-4 p.53; BR-FISERV-EMV-GUIDE-43-4 p.43 | 1 | Purchase/Capture | [TD-TC-076081.json](test_data/TD-TC-076081.json) |
| TC-076082 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Decision table | Positive | ready | BR-156-7 p.156; BR-688-2 p.688; p.511 | 1 | Purchase/Capture | [TD-TC-076082.json](test_data/TD-TC-076082.json) |
| TC-076083 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Decision table | Positive | ready | BR-313-3 p.313; BR-ATL105-TRANSARMOR-47-5 p.47; BR-312-1 p.312 | 1 | Purchase/Capture | [TD-TC-076083.json](test_data/TD-TC-076083.json) |
| TC-076084 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Decision table | Positive | ready | BR-89-3 p.89; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076084.json](test_data/TD-TC-076084.json) |
| TC-076085 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Decision table | Positive | ready | BR-ATL105-TRANSARMOR-28-4 p.28; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076085.json](test_data/TD-TC-076085.json) |
| TC-076086 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Valid flow | Positive | ready | p.546; p.546; BR-402-3 p.402 | 1 | Purchase/Capture | [TD-TC-076086.step1.purchase.json](test_data/TD-TC-076086.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076086.step2.reversal.json](test_data/TD-TC-076086.step2.reversal.json) |
| TC-076087 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Valid flow | Positive | ready | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-076087.step1.purchase.json](test_data/TD-TC-076087.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076087.step2.timeout-reversal.json](test_data/TD-TC-076087.step2.timeout-reversal.json) |
| TC-076088 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-076088.step1.purchase.json](test_data/TD-TC-076088.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076088.step2.split-tender.json](test_data/TD-TC-076088.step2.split-tender.json) |
| TC-076089 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Invalid state transition | Negative | requires_review | p.546; BR-39-1 p.39; p.511 | 1 | Purchase/Capture | [TD-TC-076089.step1.purchase.json](test_data/TD-TC-076089.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076089.step2.timeout-reversal.json](test_data/TD-TC-076089.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-076089.step3.timeout-reversal.json](test_data/TD-TC-076089.step3.timeout-reversal.json) |
| TC-076090 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Time-out Reve | Invalid state transition | Negative | blocked_verification | p.546; BR-39-1 p.39; p.511 | 1 | Time-out Reversal | [TD-TC-076090.json](test_data/TD-TC-076090.json) |
| TC-076091 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Fault / recovery | Positive | ready | BR-409-4 p.409; BR-ATL105-TRANSARMOR-76-3 p.76; p.511 | 1 | Purchase/Capture | [TD-TC-076091.step1.purchase.json](test_data/TD-TC-076091.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076091.step2.reversal.json](test_data/TD-TC-076091.step2.reversal.json) |
| TC-076092 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Fault / recovery | Positive | ready | BR-39-1 p.39; BR-44-1 p.44; BR-43-2 p.43 | 1 | Purchase/Capture | [TD-TC-076092.step1.purchase.json](test_data/TD-TC-076092.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076092.step2.timeout-reversal.json](test_data/TD-TC-076092.step2.timeout-reversal.json) |
| TC-076093 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Idempotency / duplicate / retry | Positive | ready | BR-47-1 p.47; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076093.step1.purchase.json](test_data/TD-TC-076093.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076093.step2.timeout-reversal.json](test_data/TD-TC-076093.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-076093.step3.timeout-reversal.json](test_data/TD-TC-076093.step3.timeout-reversal.json) |
|  |  |  |  |  | 4 | Time-out Reversal | [TD-TC-076093.step4.timeout-reversal.json](test_data/TD-TC-076093.step4.timeout-reversal.json) |
| TC-076094 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Time-based | Positive | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076094.step1.purchase.json](test_data/TD-TC-076094.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076094.step2.reversal.json](test_data/TD-TC-076094.step2.reversal.json) |
| TC-076095 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Time-based | Negative | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076095.step1.purchase.json](test_data/TD-TC-076095.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076095.step2.reversal.json](test_data/TD-TC-076095.step2.reversal.json) |
| TC-076096 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Time-based | Positive | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076096.step1.purchase.json](test_data/TD-TC-076096.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076096.step2.timeout-reversal.json](test_data/TD-TC-076096.step2.timeout-reversal.json) |
| TC-076097 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Time-based | Negative | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076097.step1.purchase.json](test_data/TD-TC-076097.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076097.step2.timeout-reversal.json](test_data/TD-TC-076097.step2.timeout-reversal.json) |
| TC-076098 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Purchase/Capture | [TD-TC-076098.step1.purchase.json](test_data/TD-TC-076098.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076098.step2.reversal.json](test_data/TD-TC-076098.step2.reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076098.step3.totals.json](test_data/TD-TC-076098.step3.totals.json) |
| TC-076099 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Running total / state | Positive | requires_review | p.546; p.39; BR-181-1 p.181 | 1 | Purchase/Capture | [TD-TC-076099.step1.purchase.json](test_data/TD-TC-076099.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076099.step2.timeout-reversal.json](test_data/TD-TC-076099.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076099.step3.totals.json](test_data/TD-TC-076099.step3.totals.json) |
| TC-076100 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Purchase/Capture | [TD-TC-076100.step1.purchase.json](test_data/TD-TC-076100.step1.purchase.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076100.step2.totals.json](test_data/TD-TC-076100.step2.totals.json) |
| TC-076101 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Response / oracle check | Positive | ready | BR-650-4 p.650; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076101.json](test_data/TD-TC-076101.json) |
| TC-076102 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Response / oracle check | Positive | ready | BR-FISERV-EMV-GUIDE-146-3 p.146; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076102.json](test_data/TD-TC-076102.json) |
| TC-076103 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Response / oracle check | Positive | ready | BR-339-4 p.339; BR-340-2 p.340; BR-340-3 p.340 | 1 | Purchase/Capture | [TD-TC-076103.json](test_data/TD-TC-076103.json) |
| TC-076104 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Response / oracle check | Negative | needs_client_input | p.546; p.546; BR-402-3 p.402 | 1 | Purchase/Capture | [TD-TC-076104.step1.purchase.json](test_data/TD-TC-076104.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076104.step2.reversal.json](test_data/TD-TC-076104.step2.reversal.json) |
| TC-076105 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Response / oracle check | Positive | ready | BR-317-5 p.317; BR-317-9 p.317; p.511 | 1 | Purchase/Capture | [TD-TC-076105.json](test_data/TD-TC-076105.json) |
| TC-076106 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Response / oracle check | Negative | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-076106.step1.purchase.json](test_data/TD-TC-076106.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076106.step2.timeout-reversal.json](test_data/TD-TC-076106.step2.timeout-reversal.json) |
| TC-076107 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Response / oracle check | Positive | ready | BR-FISERV-EMV-GUIDE-138-2 p.138; BR-FISERV-EMV-GUIDE-138-4 p.138; BR-FISERV-EMV-GUIDE-141-4 p.141 | 1 | Purchase/Capture | [TD-TC-076107.json](test_data/TD-TC-076107.json) |
| TC-076108 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Response / oracle check | Positive | ready | BR-62-6 p.62; BR-94-5 p.94; BR-607-3 p.607 | 1 | Purchase/Capture | [TD-TC-076108.json](test_data/TD-TC-076108.json) |
| TC-076109 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-076109.json](test_data/TD-TC-076109.json) |
| TC-076110 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Response / oracle check | Positive | ready | BR-545-5 p.545; BR-664-4 p.664; BR-663-1 p.663 | 1 | Purchase/Capture | [TD-TC-076110.json](test_data/TD-TC-076110.json) |
| TC-076111 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Response / oracle check | Positive | ready | BR-671-6 p.671; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076111.json](test_data/TD-TC-076111.json) |
| TC-076112 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Response / oracle check | Positive | needs_client_input | BR-656-3 p.656; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076112.json](test_data/TD-TC-076112.json) |
| TC-076113 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Response / oracle check | Positive | needs_client_input | p.552; BR-552-2 p.552; BR-552-6 p.552 | 1 | Purchase/Capture | [TD-TC-076113.json](test_data/TD-TC-076113.json) |
| TC-076114 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Response / oracle check | Positive | needs_client_input | p.578; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076114.json](test_data/TD-TC-076114.json) |
| TC-076115 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Response / oracle check | Positive | needs_client_input | p.582; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076115.json](test_data/TD-TC-076115.json) |
| TC-076116 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Response / oracle check | Positive | needs_client_input | p.602; BR-602-1 p.602; p.511 | 1 | Purchase/Capture | [TD-TC-076116.json](test_data/TD-TC-076116.json) |
| TC-076117 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Response / oracle check | Positive | needs_client_input | p.614; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076117.json](test_data/TD-TC-076117.json) |
| TC-076118 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Response / oracle check | Positive | needs_client_input | BR-252-3 p.252; BR-240-1 p.240; BR-266-3 p.266 | 1 | Purchase/Capture | [TD-TC-076118.json](test_data/TD-TC-076118.json) |
| TC-076119 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Magnetic Stripe Purchase/Capt | Response / oracle check | Positive | needs_client_input | BR-669-3 p.669; BR-669-4 p.669; p.511 | 1 | Purchase/Capture | [TD-TC-076119.json](test_data/TD-TC-076119.json) |

### SC-ATL105-DDB01831 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ General Retail · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-354E7BE3 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Hospitality · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076889 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Magnetic Stripe Purcha | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-076889.json](test_data/TD-TC-076889.json) |
| TC-076890 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Magnetic Stripe Purcha | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-076890.json](test_data/TD-TC-076890.json) |

### SC-ATL105-C679544C - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Hospitality · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-41B94458 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077152 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Magnetic Stripe Purchase/ | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-077152.json](test_data/TD-TC-077152.json) |
| TC-077153 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Magnetic Stripe Purchase/ | Happy path | Positive | needs_client_input | p.583; BR-583-1 p.583; BR-583-2 p.583 | 1 | Purchase/Capture | [TD-TC-077153.json](test_data/TD-TC-077153.json) |
| TC-077154 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Magnetic Stripe Purchase/ | Valid flow | Positive | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-077154.step1.purchase.json](test_data/TD-TC-077154.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-077154.step2.timeout-reversal.json](test_data/TD-TC-077154.step2.timeout-reversal.json) |
| TC-077155 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Magnetic Stripe Purchase/ | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-077155.step1.purchase.json](test_data/TD-TC-077155.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-077155.step2.split-tender.json](test_data/TD-TC-077155.step2.split-tender.json) |
| TC-077156 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Magnetic Stripe Purchase/ | Invalid state transition | Negative | needs_client_input | p.546; BR-39-1 p.39; p.511 | 1 | Purchase/Capture | [TD-TC-077156.step1.purchase.json](test_data/TD-TC-077156.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-077156.step2.timeout-reversal.json](test_data/TD-TC-077156.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-077156.step3.timeout-reversal.json](test_data/TD-TC-077156.step3.timeout-reversal.json) |

### SC-ATL105-71D768CE - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Merchant category 6051 (cryptocurrency purchase) · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077165 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS Contactless Magnetic Stripe Purchase/ | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-077165.json](test_data/TD-TC-077165.json) |

### SC-ATL105-7B5C5BF1 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Merchant category 6211 (high risk security) · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077172 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS Contactless Magnetic Stripe Purchase/ | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-077172.json](test_data/TD-TC-077172.json) |

### SC-ATL105-000F41EE - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076959 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-BUYPASS-PIPS-24-7 p.24 | 1 | Purchase/Capture | [TD-TC-076959.json](test_data/TD-TC-076959.json) |
| TC-076960 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; BR-BUYPASS-PIPS-185-5 p.185 | 1 | Purchase/Capture | [TD-TC-076960.json](test_data/TD-TC-076960.json) |
| TC-076961 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase | Invalid state transition | Negative | blocked_verification | p.546; BR-BUYPASS-PIPS-30-3 p.30; p.511 | 1 | Purchase Reversal | [TD-TC-076961.json](test_data/TD-TC-076961.json) |
| TC-076962 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase | Invalid state transition | Negative | needs_client_input | p.546; BR-BUYPASS-PIPS-30-3 p.30; p.511 | 1 | Purchase/Capture | [TD-TC-076962.step1.purchase.json](test_data/TD-TC-076962.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076962.step2.reversal.json](test_data/TD-TC-076962.step2.reversal.json) |
|  |  |  |  |  | 3 | Purchase Reversal | [TD-TC-076962.step3.reversal.json](test_data/TD-TC-076962.step3.reversal.json) |
| TC-076963 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase | Idempotency / duplicate / retry | Positive | requires_review | BR-BUYPASS-PIPS-17-2 p.17; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076963.json](test_data/TD-TC-076963.json) |
| TC-076964 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase | Idempotency / duplicate / retry | Negative | requires_review | BR-BUYPASS-PIPS-17-2 p.17; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076964.json](test_data/TD-TC-076964.json) |
| TC-076965 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase | Time-based | Positive | requires_review | BR-BUYPASS-PIPS-21-2 p.21; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076965.json](test_data/TD-TC-076965.json) |
| TC-076966 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase | Time-based | Negative | requires_review | BR-BUYPASS-PIPS-21-2 p.21; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076966.json](test_data/TD-TC-076966.json) |
| TC-076967 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-177-2 p.177; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076967.json](test_data/TD-TC-076967.json) |
| TC-076968 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-138-2 p.138; BR-FISERV-EMV-GUIDE-138-4 p.138; BR-FISERV-EMV-GUIDE-141-4 p.141 | 1 | Purchase/Capture | [TD-TC-076968.json](test_data/TD-TC-076968.json) |
| TC-076969 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Magnetic Stripe Purchase | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-076969.json](test_data/TD-TC-076969.json) |

### SC-ATL105-120C1A07 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Petroleum · unattended CAT (fuel machine)
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077083 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-BUYPASS-PIPS-24-7 p.24 | 1 | Purchase/Capture | [TD-TC-077083.json](test_data/TD-TC-077083.json) |
| TC-077084 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; BR-BUYPASS-PIPS-185-5 p.185 | 1 | Purchase/Capture | [TD-TC-077084.json](test_data/TD-TC-077084.json) |
| TC-077085 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase | Valid flow | Positive | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-077085.step1.purchase.json](test_data/TD-TC-077085.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-077085.step2.timeout-reversal.json](test_data/TD-TC-077085.step2.timeout-reversal.json) |
| TC-077086 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-077086.step1.purchase.json](test_data/TD-TC-077086.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-077086.step2.split-tender.json](test_data/TD-TC-077086.step2.split-tender.json) |
| TC-077087 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase | Invalid state transition | Negative | needs_client_input | p.546; BR-39-1 p.39; p.511 | 1 | Purchase/Capture | [TD-TC-077087.step1.purchase.json](test_data/TD-TC-077087.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-077087.step2.timeout-reversal.json](test_data/TD-TC-077087.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-077087.step3.timeout-reversal.json](test_data/TD-TC-077087.step3.timeout-reversal.json) |
| TC-077088 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase | Idempotency / duplicate / retry | Positive | requires_review | BR-BUYPASS-PIPS-17-2 p.17; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-077088.json](test_data/TD-TC-077088.json) |
| TC-077089 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase | Idempotency / duplicate / retry | Negative | requires_review | BR-BUYPASS-PIPS-17-2 p.17; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-077089.json](test_data/TD-TC-077089.json) |
| TC-077090 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase | Time-based | Positive | requires_review | BR-63-2 p.63; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-077090.json](test_data/TD-TC-077090.json) |
| TC-077091 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase | Time-based | Negative | requires_review | BR-63-2 p.63; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-077091.json](test_data/TD-TC-077091.json) |
| TC-077092 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-177-2 p.177; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-077092.json](test_data/TD-TC-077092.json) |
| TC-077093 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-138-2 p.138; BR-FISERV-EMV-GUIDE-138-4 p.138; BR-FISERV-EMV-GUIDE-141-4 p.141 | 1 | Purchase/Capture | [TD-TC-077093.json](test_data/TD-TC-077093.json) |
| TC-077094 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase | Response / oracle check | Positive | needs_client_input | BR-62-6 p.62; BR-94-5 p.94; BR-607-3 p.607 | 1 | Purchase/Capture | [TD-TC-077094.json](test_data/TD-TC-077094.json) |
| TC-077095 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Magnetic Stripe Purchase | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-077095.json](test_data/TD-TC-077095.json) |

### SC-ATL105-8B9E2091 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Supermarket · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-3EE3082B - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Supermarket · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-7FBABC84 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Utilities · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-053D91ED - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Magnetic Stripe @ Utilities · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-67649BC0 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ General Retail · attended POS
- Status: unconfirmed; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076439 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-076439.json](test_data/TD-TC-076439.json) |
| TC-076440 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.552; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076440.json](test_data/TD-TC-076440.json) |
| TC-076441 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.555; BR-572-3 p.572; p.511 | 1 | Purchase/Capture | [TD-TC-076441.json](test_data/TD-TC-076441.json) |
| TC-076442 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.556; BR-92-11 p.92; p.511 | 1 | Purchase/Capture | [TD-TC-076442.json](test_data/TD-TC-076442.json) |
| TC-076443 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.561; BR-85-4 p.85; BR-561-2 p.561 | 1 | Purchase/Capture | [TD-TC-076443.json](test_data/TD-TC-076443.json) |
| TC-076444 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.562; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076444.json](test_data/TD-TC-076444.json) |
| TC-076445 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.562; BR-562-2 p.562; p.511 | 1 | Purchase/Capture | [TD-TC-076445.json](test_data/TD-TC-076445.json) |
| TC-076446 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.564; BR-564-1 p.564; BR-FISERV-EMV-GUIDE-64-2 p.64 | 1 | Purchase/Capture | [TD-TC-076446.json](test_data/TD-TC-076446.json) |
| TC-076447 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.566; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076447.json](test_data/TD-TC-076447.json) |
| TC-076448 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.569; BR-569-1 p.569; p.511 | 1 | Purchase/Capture | [TD-TC-076448.json](test_data/TD-TC-076448.json) |
| TC-076449 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.581; BR-428-8 p.428; BR-581-1 p.581 | 1 | Purchase/Capture | [TD-TC-076449.json](test_data/TD-TC-076449.json) |
| TC-076450 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.582; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076450.json](test_data/TD-TC-076450.json) |
| TC-076451 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.582; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076451.json](test_data/TD-TC-076451.json) |
| TC-076452 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.583; BR-583-1 p.583; BR-583-2 p.583 | 1 | Purchase/Capture | [TD-TC-076452.json](test_data/TD-TC-076452.json) |
| TC-076453 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.583; BR-583-3 p.583; p.511 | 1 | Purchase/Capture | [TD-TC-076453.json](test_data/TD-TC-076453.json) |
| TC-076454 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.583; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076454.json](test_data/TD-TC-076454.json) |
| TC-076455 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.585; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076455.json](test_data/TD-TC-076455.json) |
| TC-076456 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.586; BR-590-4 p.590; p.511 | 1 | Purchase/Capture | [TD-TC-076456.json](test_data/TD-TC-076456.json) |
| TC-076457 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.586; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076457.json](test_data/TD-TC-076457.json) |
| TC-076458 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.600; BR-600-2 p.600; p.511 | 1 | Purchase/Capture | [TD-TC-076458.json](test_data/TD-TC-076458.json) |
| TC-076459 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.600; BR-600-2 p.600; BR-600-3 p.600 | 1 | Purchase/Capture | [TD-TC-076459.json](test_data/TD-TC-076459.json) |
| TC-076460 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.601; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076460.json](test_data/TD-TC-076460.json) |
| TC-076461 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.602; BR-602-1 p.602; p.511 | 1 | Purchase/Capture | [TD-TC-076461.json](test_data/TD-TC-076461.json) |
| TC-076462 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.602; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076462.json](test_data/TD-TC-076462.json) |
| TC-076463 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.613; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076463.json](test_data/TD-TC-076463.json) |
| TC-076464 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.613; BR-614-4 p.614; BR-614-8 p.614 | 1 | Purchase/Capture | [TD-TC-076464.json](test_data/TD-TC-076464.json) |
| TC-076465 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.615; BR-637-2 p.637; BR-FISERV-EMV-GUIDE-38-6 p.38 | 1 | Purchase/Capture | [TD-TC-076465.json](test_data/TD-TC-076465.json) |
| TC-076466 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.620; BR-620-2 p.620; p.511 | 1 | Purchase/Capture | [TD-TC-076466.json](test_data/TD-TC-076466.json) |
| TC-076467 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.622; BR-622-5 p.622; BR-623-1 p.623 | 1 | Purchase/Capture | [TD-TC-076467.json](test_data/TD-TC-076467.json) |
| TC-076468 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.623; BR-623-5 p.623; p.511 | 1 | Purchase/Capture | [TD-TC-076468.json](test_data/TD-TC-076468.json) |
| TC-076469 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.625; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076469.json](test_data/TD-TC-076469.json) |
| TC-076470 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.626; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076470.json](test_data/TD-TC-076470.json) |
| TC-076471 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.628; BR-628-2 p.628; p.511 | 1 | Purchase/Capture | [TD-TC-076471.json](test_data/TD-TC-076471.json) |
| TC-076472 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.628; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076472.json](test_data/TD-TC-076472.json) |
| TC-076473 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.629; BR-629-1 p.629; p.511 | 1 | Purchase/Capture | [TD-TC-076473.json](test_data/TD-TC-076473.json) |
| TC-076474 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Happy path | Positive | pending_sme | p.636; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076474.json](test_data/TD-TC-076474.json) |
| TC-076475 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Alternate path | Positive | pending_sme | BR-FISERV-EMV-GUIDE-72-10 p.72; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076475.json](test_data/TD-TC-076475.json) |
| TC-076476 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Alternate path | Positive | pending_sme | BR-FISERV-EMV-GUIDE-71-5 p.71; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076476.json](test_data/TD-TC-076476.json) |
| TC-076477 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Alternate path | Positive | pending_sme | BR-189-3 p.189; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076477.json](test_data/TD-TC-076477.json) |
| TC-076478 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Alternate path | Positive | pending_sme | BR-FISERV-EMV-GUIDE-71-4 p.71; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076478.json](test_data/TD-TC-076478.json) |
| TC-076479 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Alternate path | Positive | pending_sme | BR-FISERV-EMV-GUIDE-115-20 p.115; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076479.json](test_data/TD-TC-076479.json) |
| TC-076480 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Alternate path | Positive | pending_sme | BR-577-1 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076480.json](test_data/TD-TC-076480.json) |
| TC-076481 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Alternate path | Positive | pending_sme | BR-577-2 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076481.json](test_data/TD-TC-076481.json) |
| TC-076482 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Alternate path | Positive | pending_sme | BR-597-1 p.597; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076482.json](test_data/TD-TC-076482.json) |
| TC-076483 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Alternate path | Positive | pending_sme | BR-FISERV-EMV-GUIDE-69-3 p.69; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076483.json](test_data/TD-TC-076483.json) |
| TC-076484 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Alternate path | Positive | pending_sme | BR-FISERV-EMV-GUIDE-37-10 p.37; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076484.json](test_data/TD-TC-076484.json) |
| TC-076485 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Single rule broken | Negative | pending_sme | p.546; p.546; p.402 | 1 | Purchase/Capture | [TD-TC-076485.step1.purchase.json](test_data/TD-TC-076485.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076485.step2.reversal.json](test_data/TD-TC-076485.step2.reversal.json) |
| TC-076486 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Single rule broken | Negative | pending_sme | p.546; p.546; p.40 | 1 | Purchase/Capture | [TD-TC-076486.step1.purchase.json](test_data/TD-TC-076486.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076486.step2.reversal.json](test_data/TD-TC-076486.step2.reversal.json) |
| TC-076487 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Single rule broken | Negative | pending_sme | p.546; p.39; p.38 | 1 | Purchase/Capture | [TD-TC-076487.step1.purchase.json](test_data/TD-TC-076487.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076487.step2.timeout-reversal.json](test_data/TD-TC-076487.step2.timeout-reversal.json) |
| TC-076488 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Single rule broken | Negative | pending_sme | BR-FISERV-EMV-GUIDE-70-10 p.70; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076488.json](test_data/TD-TC-076488.json) |
| TC-076489 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Single rule broken | Negative | pending_sme | BR-ATL105-TRANSARMOR-13-7 p.13; BR-FISERV-EMV-GUIDE-75-3 p.75; p.511 | 1 | Purchase/Capture | [TD-TC-076489.json](test_data/TD-TC-076489.json) |
| TC-076490 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Decision table | Positive | pending_sme | BR-FISERV-EMV-GUIDE-70-10 p.70; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076490.json](test_data/TD-TC-076490.json) |
| TC-076491 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Decision table | Positive | pending_sme | BR-ATL105-TRANSARMOR-13-7 p.13; BR-FISERV-EMV-GUIDE-75-3 p.75; p.511 | 1 | Purchase/Capture | [TD-TC-076491.json](test_data/TD-TC-076491.json) |
| TC-076492 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Decision table | Positive | pending_sme | BR-FISERV-EMV-GUIDE-156-8 p.156; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076492.json](test_data/TD-TC-076492.json) |
| TC-076493 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Decision table | Positive | pending_sme | BR-FISERV-EMV-GUIDE-38-2 p.38; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076493.json](test_data/TD-TC-076493.json) |
| TC-076494 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Decision table | Positive | pending_sme | BR-149-1 p.149; BR-152-1 p.152; p.511 | 1 | Purchase/Capture | [TD-TC-076494.json](test_data/TD-TC-076494.json) |
| TC-076495 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Decision table | Positive | pending_sme | BR-315-4 p.315; BR-554-4 p.554; BR-622-1 p.622 | 1 | Purchase/Capture | [TD-TC-076495.json](test_data/TD-TC-076495.json) |
| TC-076496 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Decision table | Positive | pending_sme | BR-53-2 p.53; BR-56-8 p.56; BR-364-3 p.364 | 1 | Purchase/Capture | [TD-TC-076496.json](test_data/TD-TC-076496.json) |
| TC-076497 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Decision table | Positive | pending_sme | BR-FISERV-EMV-GUIDE-50-5 p.50; BR-FISERV-EMV-GUIDE-87-2 p.87; BR-FISERV-EMV-GUIDE-152-5 p.152 | 1 | Purchase/Capture | [TD-TC-076497.json](test_data/TD-TC-076497.json) |
| TC-076498 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Decision table | Positive | pending_sme | BR-FISERV-EMV-GUIDE-53-3 p.53; BR-FISERV-EMV-GUIDE-53-4 p.53; BR-FISERV-EMV-GUIDE-43-4 p.43 | 1 | Purchase/Capture | [TD-TC-076498.json](test_data/TD-TC-076498.json) |
| TC-076499 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Decision table | Positive | pending_sme | BR-313-3 p.313; BR-ATL105-TRANSARMOR-47-5 p.47; BR-312-1 p.312 | 1 | Purchase/Capture | [TD-TC-076499.json](test_data/TD-TC-076499.json) |
| TC-076500 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Decision table | Positive | pending_sme | BR-89-3 p.89; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076500.json](test_data/TD-TC-076500.json) |
| TC-076501 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Decision table | Positive | pending_sme | BR-ATL105-TRANSARMOR-28-4 p.28; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076501.json](test_data/TD-TC-076501.json) |
| TC-076502 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Valid flow | Positive | pending_sme | p.546; p.546; BR-402-3 p.402 | 1 | Purchase/Capture | [TD-TC-076502.step1.purchase.json](test_data/TD-TC-076502.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076502.step2.reversal.json](test_data/TD-TC-076502.step2.reversal.json) |
| TC-076503 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Valid flow | Positive | pending_sme | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-076503.step1.purchase.json](test_data/TD-TC-076503.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076503.step2.timeout-reversal.json](test_data/TD-TC-076503.step2.timeout-reversal.json) |
| TC-076504 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Valid flow | Positive | pending_sme | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-076504.step1.purchase.json](test_data/TD-TC-076504.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076504.step2.split-tender.json](test_data/TD-TC-076504.step2.split-tender.json) |
| TC-076505 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Invalid state transition | Negative | pending_sme | p.546; BR-39-1 p.39; p.511 | 1 | Purchase/Capture | [TD-TC-076505.step1.purchase.json](test_data/TD-TC-076505.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076505.step2.timeout-reversal.json](test_data/TD-TC-076505.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-076505.step3.timeout-reversal.json](test_data/TD-TC-076505.step3.timeout-reversal.json) |
| TC-076506 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Time-o | Invalid state transition | Negative | blocked_verification | p.546; BR-39-1 p.39; p.511 | 1 | Time-out Reversal | [TD-TC-076506.json](test_data/TD-TC-076506.json) |
| TC-076507 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Fault / recovery | Positive | pending_sme | BR-409-4 p.409; BR-ATL105-TRANSARMOR-76-3 p.76; p.511 | 1 | Purchase/Capture | [TD-TC-076507.step1.purchase.json](test_data/TD-TC-076507.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076507.step2.reversal.json](test_data/TD-TC-076507.step2.reversal.json) |
| TC-076508 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Fault / recovery | Positive | pending_sme | BR-39-1 p.39; BR-44-1 p.44; BR-43-2 p.43 | 1 | Purchase/Capture | [TD-TC-076508.step1.purchase.json](test_data/TD-TC-076508.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076508.step2.timeout-reversal.json](test_data/TD-TC-076508.step2.timeout-reversal.json) |
| TC-076509 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Idempotency / duplicate / retry | Positive | pending_sme | BR-47-1 p.47; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076509.step1.purchase.json](test_data/TD-TC-076509.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076509.step2.timeout-reversal.json](test_data/TD-TC-076509.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-076509.step3.timeout-reversal.json](test_data/TD-TC-076509.step3.timeout-reversal.json) |
|  |  |  |  |  | 4 | Time-out Reversal | [TD-TC-076509.step4.timeout-reversal.json](test_data/TD-TC-076509.step4.timeout-reversal.json) |
| TC-076510 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Time-based | Positive | pending_sme | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076510.step1.purchase.json](test_data/TD-TC-076510.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076510.step2.reversal.json](test_data/TD-TC-076510.step2.reversal.json) |
| TC-076511 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Time-based | Negative | pending_sme | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076511.step1.purchase.json](test_data/TD-TC-076511.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076511.step2.reversal.json](test_data/TD-TC-076511.step2.reversal.json) |
| TC-076512 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Time-based | Positive | pending_sme | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076512.step1.purchase.json](test_data/TD-TC-076512.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076512.step2.timeout-reversal.json](test_data/TD-TC-076512.step2.timeout-reversal.json) |
| TC-076513 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Time-based | Negative | pending_sme | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076513.step1.purchase.json](test_data/TD-TC-076513.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076513.step2.timeout-reversal.json](test_data/TD-TC-076513.step2.timeout-reversal.json) |
| TC-076514 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Running total / state | Positive | pending_sme | p.546; p.546; BR-181-1 p.181 | 1 | Purchase/Capture | [TD-TC-076514.step1.purchase.json](test_data/TD-TC-076514.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076514.step2.reversal.json](test_data/TD-TC-076514.step2.reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076514.step3.totals.json](test_data/TD-TC-076514.step3.totals.json) |
| TC-076515 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Running total / state | Positive | pending_sme | p.546; p.39; BR-181-1 p.181 | 1 | Purchase/Capture | [TD-TC-076515.step1.purchase.json](test_data/TD-TC-076515.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076515.step2.timeout-reversal.json](test_data/TD-TC-076515.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076515.step3.totals.json](test_data/TD-TC-076515.step3.totals.json) |
| TC-076516 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Running total / state | Positive | pending_sme | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Purchase/Capture | [TD-TC-076516.step1.purchase.json](test_data/TD-TC-076516.step1.purchase.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076516.step2.totals.json](test_data/TD-TC-076516.step2.totals.json) |
| TC-076517 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Response / oracle check | Positive | pending_sme | BR-650-4 p.650; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076517.json](test_data/TD-TC-076517.json) |
| TC-076518 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Response / oracle check | Positive | pending_sme | BR-FISERV-EMV-GUIDE-146-3 p.146; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076518.json](test_data/TD-TC-076518.json) |
| TC-076519 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Response / oracle check | Positive | pending_sme | BR-657-2 p.657; BR-657-5 p.657; p.511 | 1 | Purchase/Capture | [TD-TC-076519.json](test_data/TD-TC-076519.json) |
| TC-076520 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Response / oracle check | Positive | pending_sme | BR-339-4 p.339; BR-340-2 p.340; BR-340-3 p.340 | 1 | Purchase/Capture | [TD-TC-076520.json](test_data/TD-TC-076520.json) |
| TC-076521 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Response / oracle check | Negative | pending_sme | p.546; p.546; BR-402-3 p.402 | 1 | Purchase/Capture | [TD-TC-076521.step1.purchase.json](test_data/TD-TC-076521.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076521.step2.reversal.json](test_data/TD-TC-076521.step2.reversal.json) |
| TC-076522 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Response / oracle check | Positive | pending_sme | BR-317-5 p.317; BR-317-9 p.317; p.511 | 1 | Purchase/Capture | [TD-TC-076522.json](test_data/TD-TC-076522.json) |
| TC-076523 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Response / oracle check | Negative | pending_sme | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-076523.step1.purchase.json](test_data/TD-TC-076523.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076523.step2.timeout-reversal.json](test_data/TD-TC-076523.step2.timeout-reversal.json) |
| TC-076524 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Response / oracle check | Positive | pending_sme | BR-FISERV-EMV-GUIDE-138-2 p.138; BR-FISERV-EMV-GUIDE-138-4 p.138; BR-FISERV-EMV-GUIDE-141-4 p.141 | 1 | Purchase/Capture | [TD-TC-076524.json](test_data/TD-TC-076524.json) |
| TC-076525 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Response / oracle check | Positive | pending_sme | BR-62-6 p.62; BR-94-5 p.94; BR-607-3 p.607 | 1 | Purchase/Capture | [TD-TC-076525.json](test_data/TD-TC-076525.json) |
| TC-076526 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Response / oracle check | Negative | pending_sme | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-076526.json](test_data/TD-TC-076526.json) |
| TC-076527 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Response / oracle check | Positive | pending_sme | BR-545-5 p.545; BR-664-4 p.664; BR-663-1 p.663 | 1 | Purchase/Capture | [TD-TC-076527.json](test_data/TD-TC-076527.json) |
| TC-076528 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Response / oracle check | Positive | pending_sme | BR-671-6 p.671; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076528.json](test_data/TD-TC-076528.json) |
| TC-076529 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Response / oracle check | Positive | pending_sme | BR-656-3 p.656; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076529.json](test_data/TD-TC-076529.json) |
| TC-076530 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Response / oracle check | Positive | pending_sme | p.552; BR-552-2 p.552; BR-552-6 p.552 | 1 | Purchase/Capture | [TD-TC-076530.json](test_data/TD-TC-076530.json) |
| TC-076531 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Response / oracle check | Positive | pending_sme | p.578; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076531.json](test_data/TD-TC-076531.json) |
| TC-076532 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Response / oracle check | Positive | pending_sme | p.582; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076532.json](test_data/TD-TC-076532.json) |
| TC-076533 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Response / oracle check | Positive | pending_sme | p.602; BR-602-1 p.602; p.511 | 1 | Purchase/Capture | [TD-TC-076533.json](test_data/TD-TC-076533.json) |
| TC-076534 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Response / oracle check | Positive | pending_sme | p.614; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076534.json](test_data/TD-TC-076534.json) |
| TC-076535 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Response / oracle check | Positive | pending_sme | BR-252-3 p.252; BR-240-1 p.240; BR-266-3 p.266 | 1 | Purchase/Capture | [TD-TC-076535.json](test_data/TD-TC-076535.json) |
| TC-076536 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Contactless Mobile Commerce Device Purcha | Response / oracle check | Positive | pending_sme | BR-669-3 p.669; BR-669-4 p.669; p.511 | 1 | Purchase/Capture | [TD-TC-076536.json](test_data/TD-TC-076536.json) |

### SC-ATL105-ACA8835B - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ General Retail · unattended CAT
- Status: unconfirmed; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-1DAA6F93 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Hospitality · attended POS
- Status: unconfirmed; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076896 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Mobile Commerce Device | Happy path | Positive | pending_sme | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-076896.json](test_data/TD-TC-076896.json) |
| TC-076897 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Contactless Mobile Commerce Device | Response / oracle check | Negative | pending_sme | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-076897.json](test_data/TD-TC-076897.json) |

### SC-ATL105-67352694 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Hospitality · unattended CAT
- Status: unconfirmed; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-7C4EE598 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT
- Status: unconfirmed; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077157 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Mobile Commerce Device Pu | Happy path | Positive | pending_sme | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-077157.json](test_data/TD-TC-077157.json) |
| TC-077158 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Mobile Commerce Device Pu | Happy path | Positive | pending_sme | p.583; BR-583-1 p.583; BR-583-2 p.583 | 1 | Purchase/Capture | [TD-TC-077158.json](test_data/TD-TC-077158.json) |
| TC-077159 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Mobile Commerce Device Pu | Valid flow | Positive | pending_sme | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-077159.step1.purchase.json](test_data/TD-TC-077159.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-077159.step2.timeout-reversal.json](test_data/TD-TC-077159.step2.timeout-reversal.json) |
| TC-077160 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Mobile Commerce Device Pu | Valid flow | Positive | pending_sme | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-077160.step1.purchase.json](test_data/TD-TC-077160.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-077160.step2.split-tender.json](test_data/TD-TC-077160.step2.split-tender.json) |
| TC-077161 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Contactless Mobile Commerce Device Pu | Invalid state transition | Negative | pending_sme | p.546; BR-39-1 p.39; p.511 | 1 | Purchase/Capture | [TD-TC-077161.step1.purchase.json](test_data/TD-TC-077161.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-077161.step2.timeout-reversal.json](test_data/TD-TC-077161.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-077161.step3.timeout-reversal.json](test_data/TD-TC-077161.step3.timeout-reversal.json) |

### SC-ATL105-C1EFED34 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Merchant category 6051 (cryptocurrency purchase) · attended POS
- Status: unconfirmed; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077168 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS Contactless Mobile Commerce Device Pu | Happy path | Positive | pending_sme | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-077168.json](test_data/TD-TC-077168.json) |

### SC-ATL105-EB7AFA8C - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Merchant category 6211 (high risk security) · attended POS
- Status: unconfirmed; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077175 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS Contactless Mobile Commerce Device Pu | Happy path | Positive | pending_sme | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-077175.json](test_data/TD-TC-077175.json) |

### SC-ATL105-6070CD4F - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Petroleum · attended POS
- Status: unconfirmed; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076992 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device P | Happy path | Positive | pending_sme | p.545; BR-506-4 p.506; BR-BUYPASS-PIPS-24-7 p.24 | 1 | Purchase/Capture | [TD-TC-076992.json](test_data/TD-TC-076992.json) |
| TC-076993 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device P | Happy path | Positive | pending_sme | p.569; BR-569-1 p.569; BR-BUYPASS-PIPS-185-5 p.185 | 1 | Purchase/Capture | [TD-TC-076993.json](test_data/TD-TC-076993.json) |
| TC-076994 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device P | Invalid state transition | Negative | blocked_verification | p.546; BR-BUYPASS-PIPS-30-3 p.30; p.511 | 1 | Purchase Reversal | [TD-TC-076994.json](test_data/TD-TC-076994.json) |
| TC-076995 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device P | Invalid state transition | Negative | pending_sme | p.546; BR-BUYPASS-PIPS-30-3 p.30; p.511 | 1 | Purchase/Capture | [TD-TC-076995.step1.purchase.json](test_data/TD-TC-076995.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076995.step2.reversal.json](test_data/TD-TC-076995.step2.reversal.json) |
|  |  |  |  |  | 3 | Purchase Reversal | [TD-TC-076995.step3.reversal.json](test_data/TD-TC-076995.step3.reversal.json) |
| TC-076996 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device P | Idempotency / duplicate / retry | Positive | pending_sme | BR-BUYPASS-PIPS-17-2 p.17; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076996.json](test_data/TD-TC-076996.json) |
| TC-076997 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device P | Idempotency / duplicate / retry | Negative | pending_sme | BR-BUYPASS-PIPS-17-2 p.17; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076997.json](test_data/TD-TC-076997.json) |
| TC-076998 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device P | Time-based | Positive | pending_sme | BR-BUYPASS-PIPS-21-2 p.21; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076998.json](test_data/TD-TC-076998.json) |
| TC-076999 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device P | Time-based | Negative | pending_sme | BR-BUYPASS-PIPS-21-2 p.21; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076999.json](test_data/TD-TC-076999.json) |
| TC-077000 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device P | Response / oracle check | Positive | pending_sme | BR-FISERV-EMV-GUIDE-177-2 p.177; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-077000.json](test_data/TD-TC-077000.json) |
| TC-077001 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device P | Response / oracle check | Positive | pending_sme | BR-657-2 p.657; BR-657-5 p.657; BR-BUYPASS-PIPS-131-2 p.131 | 1 | Purchase/Capture | [TD-TC-077001.json](test_data/TD-TC-077001.json) |
| TC-077002 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device P | Response / oracle check | Positive | pending_sme | BR-FISERV-EMV-GUIDE-138-2 p.138; BR-FISERV-EMV-GUIDE-138-4 p.138; BR-FISERV-EMV-GUIDE-141-4 p.141 | 1 | Purchase/Capture | [TD-TC-077002.json](test_data/TD-TC-077002.json) |
| TC-077003 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Contactless Mobile Commerce Device P | Response / oracle check | Negative | pending_sme | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-077003.json](test_data/TD-TC-077003.json) |

### SC-ATL105-74D4DC2F - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Petroleum · unattended CAT (fuel machine)
- Status: unconfirmed; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077096 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device P | Happy path | Positive | pending_sme | p.545; BR-506-4 p.506; BR-BUYPASS-PIPS-24-7 p.24 | 1 | Purchase/Capture | [TD-TC-077096.json](test_data/TD-TC-077096.json) |
| TC-077097 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device P | Happy path | Positive | pending_sme | p.569; BR-569-1 p.569; BR-BUYPASS-PIPS-185-5 p.185 | 1 | Purchase/Capture | [TD-TC-077097.json](test_data/TD-TC-077097.json) |
| TC-077098 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device P | Valid flow | Positive | pending_sme | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-077098.step1.purchase.json](test_data/TD-TC-077098.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-077098.step2.timeout-reversal.json](test_data/TD-TC-077098.step2.timeout-reversal.json) |
| TC-077099 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device P | Valid flow | Positive | pending_sme | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-077099.step1.purchase.json](test_data/TD-TC-077099.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-077099.step2.split-tender.json](test_data/TD-TC-077099.step2.split-tender.json) |
| TC-077100 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device P | Invalid state transition | Negative | pending_sme | p.546; BR-39-1 p.39; p.511 | 1 | Purchase/Capture | [TD-TC-077100.step1.purchase.json](test_data/TD-TC-077100.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-077100.step2.timeout-reversal.json](test_data/TD-TC-077100.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-077100.step3.timeout-reversal.json](test_data/TD-TC-077100.step3.timeout-reversal.json) |
| TC-077101 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device P | Idempotency / duplicate / retry | Positive | pending_sme | BR-BUYPASS-PIPS-17-2 p.17; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-077101.json](test_data/TD-TC-077101.json) |
| TC-077102 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device P | Idempotency / duplicate / retry | Negative | pending_sme | BR-BUYPASS-PIPS-17-2 p.17; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-077102.json](test_data/TD-TC-077102.json) |
| TC-077103 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device P | Time-based | Positive | pending_sme | BR-63-2 p.63; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-077103.json](test_data/TD-TC-077103.json) |
| TC-077104 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device P | Time-based | Negative | pending_sme | BR-63-2 p.63; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-077104.json](test_data/TD-TC-077104.json) |
| TC-077105 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device P | Response / oracle check | Positive | pending_sme | BR-FISERV-EMV-GUIDE-177-2 p.177; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-077105.json](test_data/TD-TC-077105.json) |
| TC-077106 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device P | Response / oracle check | Positive | pending_sme | BR-657-2 p.657; BR-657-5 p.657; BR-BUYPASS-PIPS-131-2 p.131 | 1 | Purchase/Capture | [TD-TC-077106.json](test_data/TD-TC-077106.json) |
| TC-077107 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device P | Response / oracle check | Positive | pending_sme | BR-FISERV-EMV-GUIDE-138-2 p.138; BR-FISERV-EMV-GUIDE-138-4 p.138; BR-FISERV-EMV-GUIDE-141-4 p.141 | 1 | Purchase/Capture | [TD-TC-077107.json](test_data/TD-TC-077107.json) |
| TC-077108 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device P | Response / oracle check | Positive | pending_sme | BR-62-6 p.62; BR-94-5 p.94; BR-607-3 p.607 | 1 | Purchase/Capture | [TD-TC-077108.json](test_data/TD-TC-077108.json) |
| TC-077109 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Contactless Mobile Commerce Device P | Response / oracle check | Negative | pending_sme | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-077109.json](test_data/TD-TC-077109.json) |

### SC-ATL105-57B55A9C - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Supermarket · attended POS
- Status: unconfirmed; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-B4D86B80 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Supermarket · unattended CAT
- Status: unconfirmed; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-AE1C3A5C - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Utilities · attended POS
- Status: unconfirmed; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-2469B825 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Contactless Mobile Commerce Device @ Utilities · unattended CAT
- Status: unconfirmed; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-DA88DB74 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Credential on File @ Credential on File · customer present; card not present
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076323 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture → app | Happy path | Positive | ready | BR-83-1 p.83; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076323.json](test_data/TD-TC-076323.json) |
| TC-076324 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture → app | Happy path | Positive | ready | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-076324.json](test_data/TD-TC-076324.json) |
| TC-076325 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.552; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076325.json](test_data/TD-TC-076325.json) |
| TC-076326 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.555; BR-153-1 p.153; BR-555-3 p.555 | 1 | Purchase/Capture | [TD-TC-076326.json](test_data/TD-TC-076326.json) |
| TC-076327 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.555; BR-572-3 p.572; p.511 | 1 | Purchase/Capture | [TD-TC-076327.json](test_data/TD-TC-076327.json) |
| TC-076328 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.556; BR-92-11 p.92; p.511 | 1 | Purchase/Capture | [TD-TC-076328.json](test_data/TD-TC-076328.json) |
| TC-076329 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | ready | p.557; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076329.json](test_data/TD-TC-076329.json) |
| TC-076330 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | ready | p.558; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076330.json](test_data/TD-TC-076330.json) |
| TC-076331 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.559; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076331.json](test_data/TD-TC-076331.json) |
| TC-076332 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.561; BR-85-4 p.85; BR-561-2 p.561 | 1 | Purchase/Capture | [TD-TC-076332.json](test_data/TD-TC-076332.json) |
| TC-076333 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.562; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076333.json](test_data/TD-TC-076333.json) |
| TC-076334 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.562; BR-562-2 p.562; p.511 | 1 | Purchase/Capture | [TD-TC-076334.json](test_data/TD-TC-076334.json) |
| TC-076335 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | ready | p.564; BR-564-1 p.564; BR-FISERV-EMV-GUIDE-64-2 p.64 | 1 | Purchase/Capture | [TD-TC-076335.json](test_data/TD-TC-076335.json) |
| TC-076336 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.566; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076336.json](test_data/TD-TC-076336.json) |
| TC-076337 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | ready | p.569; BR-569-1 p.569; p.511 | 1 | Purchase/Capture | [TD-TC-076337.json](test_data/TD-TC-076337.json) |
| TC-076338 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.581; BR-428-8 p.428; BR-581-1 p.581 | 1 | Purchase/Capture | [TD-TC-076338.json](test_data/TD-TC-076338.json) |
| TC-076339 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.582; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076339.json](test_data/TD-TC-076339.json) |
| TC-076340 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.582; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076340.json](test_data/TD-TC-076340.json) |
| TC-076341 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.583; BR-583-1 p.583; BR-583-2 p.583 | 1 | Purchase/Capture | [TD-TC-076341.json](test_data/TD-TC-076341.json) |
| TC-076342 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.583; BR-583-3 p.583; p.511 | 1 | Purchase/Capture | [TD-TC-076342.json](test_data/TD-TC-076342.json) |
| TC-076343 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.583; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076343.json](test_data/TD-TC-076343.json) |
| TC-076344 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.585; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076344.json](test_data/TD-TC-076344.json) |
| TC-076345 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.586; BR-590-4 p.590; p.511 | 1 | Purchase/Capture | [TD-TC-076345.json](test_data/TD-TC-076345.json) |
| TC-076346 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | ready | p.586; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076346.json](test_data/TD-TC-076346.json) |
| TC-076347 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.600; BR-600-2 p.600; p.511 | 1 | Purchase/Capture | [TD-TC-076347.json](test_data/TD-TC-076347.json) |
| TC-076348 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.600; BR-600-2 p.600; BR-600-3 p.600 | 1 | Purchase/Capture | [TD-TC-076348.json](test_data/TD-TC-076348.json) |
| TC-076349 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.601; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076349.json](test_data/TD-TC-076349.json) |
| TC-076350 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.602; BR-602-1 p.602; p.511 | 1 | Purchase/Capture | [TD-TC-076350.json](test_data/TD-TC-076350.json) |
| TC-076351 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.602; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076351.json](test_data/TD-TC-076351.json) |
| TC-076352 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.612; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076352.json](test_data/TD-TC-076352.json) |
| TC-076353 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.612; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076353.json](test_data/TD-TC-076353.json) |
| TC-076354 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.613; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076354.json](test_data/TD-TC-076354.json) |
| TC-076355 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.613; BR-614-4 p.614; BR-614-8 p.614 | 1 | Purchase/Capture | [TD-TC-076355.json](test_data/TD-TC-076355.json) |
| TC-076356 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.615; BR-637-2 p.637; BR-FISERV-EMV-GUIDE-38-6 p.38 | 1 | Purchase/Capture | [TD-TC-076356.json](test_data/TD-TC-076356.json) |
| TC-076357 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.620; BR-620-2 p.620; p.511 | 1 | Purchase/Capture | [TD-TC-076357.json](test_data/TD-TC-076357.json) |
| TC-076358 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.622; BR-622-5 p.622; BR-623-1 p.623 | 1 | Purchase/Capture | [TD-TC-076358.json](test_data/TD-TC-076358.json) |
| TC-076359 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.623; BR-623-5 p.623; p.511 | 1 | Purchase/Capture | [TD-TC-076359.json](test_data/TD-TC-076359.json) |
| TC-076360 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.625; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076360.json](test_data/TD-TC-076360.json) |
| TC-076361 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.626; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076361.json](test_data/TD-TC-076361.json) |
| TC-076362 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.628; BR-628-2 p.628; p.511 | 1 | Purchase/Capture | [TD-TC-076362.json](test_data/TD-TC-076362.json) |
| TC-076363 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.628; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076363.json](test_data/TD-TC-076363.json) |
| TC-076364 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.629; BR-629-1 p.629; p.511 | 1 | Purchase/Capture | [TD-TC-076364.json](test_data/TD-TC-076364.json) |
| TC-076365 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Happy path | Positive | needs_client_input | p.636; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076365.json](test_data/TD-TC-076365.json) |
| TC-076366 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via t | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-72-10 p.72; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076366.json](test_data/TD-TC-076366.json) |
| TC-076367 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via t | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-71-5 p.71; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076367.json](test_data/TD-TC-076367.json) |
| TC-076368 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via t | Alternate path | Positive | requires_review | BR-189-3 p.189; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076368.json](test_data/TD-TC-076368.json) |
| TC-076369 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via t | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-71-4 p.71; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076369.json](test_data/TD-TC-076369.json) |
| TC-076370 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via t | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-115-20 p.115; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076370.json](test_data/TD-TC-076370.json) |
| TC-076371 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via t | Alternate path | Positive | requires_review | BR-577-1 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076371.json](test_data/TD-TC-076371.json) |
| TC-076372 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via t | Alternate path | Positive | requires_review | BR-577-2 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076372.json](test_data/TD-TC-076372.json) |
| TC-076373 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via t | Alternate path | Positive | requires_review | BR-597-1 p.597; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076373.json](test_data/TD-TC-076373.json) |
| TC-076374 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via t | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-69-3 p.69; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076374.json](test_data/TD-TC-076374.json) |
| TC-076375 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture via t | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-37-10 p.37; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076375.json](test_data/TD-TC-076375.json) |
| TC-076376 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then | Single rule broken | Negative | requires_review | p.546; p.546; p.402 | 1 | Purchase/Capture | [TD-TC-076376.step1.purchase.json](test_data/TD-TC-076376.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076376.step2.reversal.json](test_data/TD-TC-076376.step2.reversal.json) |
| TC-076377 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then | Single rule broken | Negative | requires_review | p.546; p.546; p.40 | 1 | Purchase/Capture | [TD-TC-076377.step1.purchase.json](test_data/TD-TC-076377.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076377.step2.reversal.json](test_data/TD-TC-076377.step2.reversal.json) |
| TC-076378 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then | Single rule broken | Negative | requires_review | p.546; p.39; p.38 | 1 | Purchase/Capture | [TD-TC-076378.step1.purchase.json](test_data/TD-TC-076378.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076378.step2.timeout-reversal.json](test_data/TD-TC-076378.step2.timeout-reversal.json) |
| TC-076379 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Single rule broken | Negative | requires_review | BR-FISERV-EMV-GUIDE-70-10 p.70; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076379.json](test_data/TD-TC-076379.json) |
| TC-076380 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Single rule broken | Negative | requires_review | BR-ATL105-TRANSARMOR-13-7 p.13; BR-FISERV-EMV-GUIDE-75-3 p.75; p.511 | 1 | Purchase/Capture | [TD-TC-076380.json](test_data/TD-TC-076380.json) |
| TC-076381 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-70-10 p.70; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076381.json](test_data/TD-TC-076381.json) |
| TC-076382 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Decision table | Positive | ready | BR-ATL105-TRANSARMOR-13-7 p.13; BR-FISERV-EMV-GUIDE-75-3 p.75; p.511 | 1 | Purchase/Capture | [TD-TC-076382.json](test_data/TD-TC-076382.json) |
| TC-076383 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-156-8 p.156; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076383.json](test_data/TD-TC-076383.json) |
| TC-076384 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-38-2 p.38; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076384.json](test_data/TD-TC-076384.json) |
| TC-076385 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Decision table | Positive | ready | BR-149-1 p.149; BR-152-1 p.152; p.511 | 1 | Purchase/Capture | [TD-TC-076385.json](test_data/TD-TC-076385.json) |
| TC-076386 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Decision table | Positive | needs_client_input | BR-315-4 p.315; BR-554-4 p.554; BR-622-1 p.622 | 1 | Purchase/Capture | [TD-TC-076386.json](test_data/TD-TC-076386.json) |
| TC-076387 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Decision table | Positive | needs_client_input | BR-315-5 p.315; BR-315-6 p.315; BR-554-4 p.554 | 1 | Purchase/Capture | [TD-TC-076387.json](test_data/TD-TC-076387.json) |
| TC-076388 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Decision table | Positive | needs_client_input | BR-315-5 p.315; BR-315-6 p.315; BR-554-4 p.554 | 1 | Purchase/Capture | [TD-TC-076388.json](test_data/TD-TC-076388.json) |
| TC-076389 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Decision table | Positive | needs_client_input | BR-40-4 p.40; BR-40-13 p.40; BR-44-1 p.44 | 1 | Purchase/Capture | [TD-TC-076389.json](test_data/TD-TC-076389.json) |
| TC-076390 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Decision table | Positive | ready | BR-61-2 p.61; BR-559-1 p.559; BR-713-12 p.713 | 1 | Purchase/Capture | [TD-TC-076390.json](test_data/TD-TC-076390.json) |
| TC-076391 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-53-3 p.53; BR-FISERV-EMV-GUIDE-53-4 p.53; BR-FISERV-EMV-GUIDE-43-4 p.43 | 1 | Purchase/Capture | [TD-TC-076391.json](test_data/TD-TC-076391.json) |
| TC-076392 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Decision table | Positive | ready | BR-156-7 p.156; BR-688-2 p.688; p.511 | 1 | Purchase/Capture | [TD-TC-076392.json](test_data/TD-TC-076392.json) |
| TC-076393 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Decision table | Positive | ready | BR-313-3 p.313; BR-ATL105-TRANSARMOR-47-5 p.47; BR-312-1 p.312 | 1 | Purchase/Capture | [TD-TC-076393.json](test_data/TD-TC-076393.json) |
| TC-076394 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Decision table | Positive | ready | BR-89-3 p.89; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076394.json](test_data/TD-TC-076394.json) |
| TC-076395 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Decision table | Positive | ready | BR-ATL105-TRANSARMOR-28-4 p.28; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076395.json](test_data/TD-TC-076395.json) |
| TC-076396 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Decision table | Positive | ready | BR-590-3 p.590; BR-594-4 p.594; p.511 | 1 | Purchase/Capture | [TD-TC-076396.json](test_data/TD-TC-076396.json) |
| TC-076397 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Decision table | Positive | ready | BR-590-3 p.590; BR-594-4 p.594; p.511 | 1 | Purchase/Capture | [TD-TC-076397.json](test_data/TD-TC-076397.json) |
| TC-076398 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then | Valid flow | Positive | ready | p.546; p.546; BR-402-3 p.402 | 1 | Purchase/Capture | [TD-TC-076398.step1.purchase.json](test_data/TD-TC-076398.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076398.step2.reversal.json](test_data/TD-TC-076398.step2.reversal.json) |
| TC-076399 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then | Valid flow | Positive | ready | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-076399.step1.purchase.json](test_data/TD-TC-076399.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076399.step2.timeout-reversal.json](test_data/TD-TC-076399.step2.timeout-reversal.json) |
| TC-076400 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-076400.step1.purchase.json](test_data/TD-TC-076400.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076400.step2.split-tender.json](test_data/TD-TC-076400.step2.split-tender.json) |
| TC-076401 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then | Invalid state transition | Negative | requires_review | p.546; BR-39-1 p.39; p.511 | 1 | Purchase/Capture | [TD-TC-076401.step1.purchase.json](test_data/TD-TC-076401.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076401.step2.timeout-reversal.json](test_data/TD-TC-076401.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-076401.step3.timeout-reversal.json](test_data/TD-TC-076401.step3.timeout-reversal.json) |
| TC-076402 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Time-out Reversal with | Invalid state transition | Negative | blocked_verification | p.546; BR-39-1 p.39; p.511 | 1 | Time-out Reversal | [TD-TC-076402.json](test_data/TD-TC-076402.json) |
| TC-076403 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (no r | Fault / recovery | Positive | ready | BR-409-4 p.409; BR-ATL105-TRANSARMOR-76-3 p.76; p.511 | 1 | Purchase/Capture | [TD-TC-076403.step1.purchase.json](test_data/TD-TC-076403.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076403.step2.reversal.json](test_data/TD-TC-076403.step2.reversal.json) |
| TC-076404 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (no r | Fault / recovery | Positive | ready | BR-39-1 p.39; BR-44-1 p.44; BR-43-2 p.43 | 1 | Purchase/Capture | [TD-TC-076404.step1.purchase.json](test_data/TD-TC-076404.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076404.step2.timeout-reversal.json](test_data/TD-TC-076404.step2.timeout-reversal.json) |
| TC-076405 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (no r | Idempotency / duplicate / retry | Positive | ready | BR-47-1 p.47; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076405.step1.purchase.json](test_data/TD-TC-076405.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076405.step2.timeout-reversal.json](test_data/TD-TC-076405.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-076405.step3.timeout-reversal.json](test_data/TD-TC-076405.step3.timeout-reversal.json) |
|  |  |  |  |  | 4 | Time-out Reversal | [TD-TC-076405.step4.timeout-reversal.json](test_data/TD-TC-076405.step4.timeout-reversal.json) |
| TC-076406 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then | Time-based | Positive | requires_review | BR-85-3 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076406.step1.purchase.json](test_data/TD-TC-076406.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076406.step2.reversal.json](test_data/TD-TC-076406.step2.reversal.json) |
| TC-076407 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then | Time-based | Negative | requires_review | BR-85-3 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076407.step1.purchase.json](test_data/TD-TC-076407.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076407.step2.reversal.json](test_data/TD-TC-076407.step2.reversal.json) |
| TC-076408 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then | Time-based | Positive | requires_review | BR-85-3 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076408.step1.purchase.json](test_data/TD-TC-076408.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076408.step2.timeout-reversal.json](test_data/TD-TC-076408.step2.timeout-reversal.json) |
| TC-076409 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then | Time-based | Negative | requires_review | BR-85-3 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076409.step1.purchase.json](test_data/TD-TC-076409.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076409.step2.timeout-reversal.json](test_data/TD-TC-076409.step2.timeout-reversal.json) |
| TC-076410 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Purchase/Capture | [TD-TC-076410.step1.purchase.json](test_data/TD-TC-076410.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076410.step2.reversal.json](test_data/TD-TC-076410.step2.reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076410.step3.totals.json](test_data/TD-TC-076410.step3.totals.json) |
| TC-076411 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then | Running total / state | Positive | requires_review | p.546; p.39; BR-181-1 p.181 | 1 | Purchase/Capture | [TD-TC-076411.step1.purchase.json](test_data/TD-TC-076411.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076411.step2.timeout-reversal.json](test_data/TD-TC-076411.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076411.step3.totals.json](test_data/TD-TC-076411.step3.totals.json) |
| TC-076412 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Purchase/Capture | [TD-TC-076412.step1.purchase.json](test_data/TD-TC-076412.step1.purchase.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076412.step2.totals.json](test_data/TD-TC-076412.step2.totals.json) |
| TC-076413 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (Addi | Response / oracle check | Positive | ready | BR-650-4 p.650; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076413.json](test_data/TD-TC-076413.json) |
| TC-076414 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (Card | Response / oracle check | Positive | ready | BR-FISERV-EMV-GUIDE-146-3 p.146; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076414.json](test_data/TD-TC-076414.json) |
| TC-076415 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (Netw | Response / oracle check | Positive | ready | BR-339-4 p.339; BR-340-2 p.340; BR-340-3 p.340 | 1 | Purchase/Capture | [TD-TC-076415.json](test_data/TD-TC-076415.json) |
| TC-076416 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then | Response / oracle check | Negative | needs_client_input | p.546; p.546; BR-402-3 p.402 | 1 | Purchase/Capture | [TD-TC-076416.step1.purchase.json](test_data/TD-TC-076416.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076416.step2.reversal.json](test_data/TD-TC-076416.step2.reversal.json) |
| TC-076417 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (Real | Response / oracle check | Positive | ready | BR-317-5 p.317; BR-317-9 p.317; p.511 | 1 | Purchase/Capture | [TD-TC-076417.json](test_data/TD-TC-076417.json) |
| TC-076418 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture, then | Response / oracle check | Negative | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-076418.step1.purchase.json](test_data/TD-TC-076418.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076418.step2.timeout-reversal.json](test_data/TD-TC-076418.step2.timeout-reversal.json) |
| TC-076419 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (Tran | Response / oracle check | Positive | ready | BR-FISERV-EMV-GUIDE-138-2 p.138; BR-FISERV-EMV-GUIDE-138-4 p.138; BR-FISERV-EMV-GUIDE-141-4 p.141 | 1 | Purchase/Capture | [TD-TC-076419.json](test_data/TD-TC-076419.json) |
| TC-076420 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (Tran | Response / oracle check | Positive | ready | BR-62-6 p.62; BR-94-5 p.94; BR-607-3 p.607 | 1 | Purchase/Capture | [TD-TC-076420.json](test_data/TD-TC-076420.json) |
| TC-076421 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (Tran | Response / oracle check | Positive | ready | BR-713-9 p.713; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076421.json](test_data/TD-TC-076421.json) |
| TC-076422 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture (card | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-076422.json](test_data/TD-TC-076422.json) |
| TC-076423 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sendi | Response / oracle check | Positive | ready | BR-545-5 p.545; BR-664-4 p.664; BR-663-1 p.663 | 1 | Purchase/Capture | [TD-TC-076423.json](test_data/TD-TC-076423.json) |
| TC-076424 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sendi | Response / oracle check | Positive | ready | BR-671-6 p.671; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076424.json](test_data/TD-TC-076424.json) |
| TC-076425 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sendi | Response / oracle check | Positive | needs_client_input | BR-656-3 p.656; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076425.json](test_data/TD-TC-076425.json) |
| TC-076426 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sendi | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-51-1 p.51; BR-656-3 p.656; BR-FISERV-EMV-GUIDE-100-2 p.100 | 1 | Purchase/Capture | [TD-TC-076426.json](test_data/TD-TC-076426.json) |
| TC-076427 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Response / oracle check | Positive | needs_client_input | p.578; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076427.json](test_data/TD-TC-076427.json) |
| TC-076428 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Response / oracle check | Positive | needs_client_input | p.582; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076428.json](test_data/TD-TC-076428.json) |
| TC-076429 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Response / oracle check | Positive | needs_client_input | p.602; BR-602-1 p.602; p.511 | 1 | Purchase/Capture | [TD-TC-076429.json](test_data/TD-TC-076429.json) |
| TC-076430 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture with  | Response / oracle check | Positive | needs_client_input | p.614; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076430.json](test_data/TD-TC-076430.json) |
| TC-076431 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sendi | Response / oracle check | Positive | needs_client_input | BR-252-3 p.252; BR-240-1 p.240; BR-266-3 p.266 | 1 | Purchase/Capture | [TD-TC-076431.json](test_data/TD-TC-076431.json) |
| TC-076432 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sendi | Response / oracle check | Positive | needs_client_input | BR-648-11 p.648; BR-648-12 p.648; BR-648-14 p.648 | 1 | Purchase/Capture | [TD-TC-076432.json](test_data/TD-TC-076432.json) |
| TC-076433 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sendi | Response / oracle check | Positive | ready | BR-56-5 p.56; BR-91-2 p.91; BR-FISERV-EMV-GUIDE-71-3 p.71 | 1 | Purchase/Capture | [TD-TC-076433.json](test_data/TD-TC-076433.json) |
| TC-076434 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sendi | Response / oracle check | Positive | needs_client_input | BR-669-3 p.669; BR-669-4 p.669; p.511 | 1 | Purchase/Capture | [TD-TC-076434.json](test_data/TD-TC-076434.json) |
| TC-076435 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sendi | Issuer outcome | Positive | needs_client_input | p.648; BR-315-5 p.315; BR-315-6 p.315 | 1 | Purchase/Capture | [TD-TC-076435.json](test_data/TD-TC-076435.json) |
| TC-076436 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sendi | Issuer outcome | Positive | needs_client_input | p.648; BR-315-5 p.315; BR-315-6 p.315 | 1 | Purchase/Capture | [TD-TC-076436.json](test_data/TD-TC-076436.json) |
| TC-076437 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sendi | Issuer outcome | Positive | needs_client_input | p.648; BR-315-5 p.315; BR-315-6 p.315 | 1 | Purchase/Capture | [TD-TC-076437.json](test_data/TD-TC-076437.json) |
| TC-076438 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture sendi | Issuer outcome | Positive | needs_client_input | p.648; BR-315-5 p.315; BR-315-6 p.315 | 1 | Purchase/Capture | [TD-TC-076438.json](test_data/TD-TC-076438.json) |
| TC-077181 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture NFC P | Single rule broken | Negative | requires_review | BR-267-1 p.267; BR-156-7 p.156; BR-688-2 p.688 | 1 |  | [TD-TC-077181.json](test_data/TD-TC-077181.json) |
| TC-077188 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Credential on File Purchase/Capture Purch | Single rule broken | Negative | requires_review | BR-728-1 p.728; BR-FISERV-EMV-GUIDE-53-3 p.53; BR-FISERV-EMV-GUIDE-53-4 p.53 | 1 |  | [TD-TC-077188.json](test_data/TD-TC-077188.json) |

### SC-ATL105-93D7F6A8 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-075600 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture → appro | Happy path | Positive | ready | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-075600.json](test_data/TD-TC-075600.json) |
| TC-075601 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with UP | Happy path | Positive | needs_client_input | p.552; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075601.json](test_data/TD-TC-075601.json) |
| TC-075602 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Cu | Happy path | Positive | needs_client_input | p.555; BR-572-3 p.572; p.511 | 1 | Purchase/Capture | [TD-TC-075602.json](test_data/TD-TC-075602.json) |
| TC-075603 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Ba | Happy path | Positive | needs_client_input | p.556; BR-92-11 p.92; p.511 | 1 | Purchase/Capture | [TD-TC-075603.json](test_data/TD-TC-075603.json) |
| TC-075604 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Su | Happy path | Positive | needs_client_input | p.561; BR-85-4 p.85; BR-561-2 p.561 | 1 | Purchase/Capture | [TD-TC-075604.json](test_data/TD-TC-075604.json) |
| TC-075605 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Ca | Happy path | Positive | needs_client_input | p.562; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075605.json](test_data/TD-TC-075605.json) |
| TC-075606 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Re | Happy path | Positive | needs_client_input | p.562; BR-562-2 p.562; p.511 | 1 | Purchase/Capture | [TD-TC-075606.json](test_data/TD-TC-075606.json) |
| TC-075607 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Or | Happy path | Positive | ready | p.564; BR-564-1 p.564; BR-FISERV-EMV-GUIDE-64-2 p.64 | 1 | Purchase/Capture | [TD-TC-075607.json](test_data/TD-TC-075607.json) |
| TC-075608 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Cu | Happy path | Positive | needs_client_input | p.566; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075608.json](test_data/TD-TC-075608.json) |
| TC-075609 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Po | Happy path | Positive | ready | p.569; BR-569-1 p.569; p.511 | 1 | Purchase/Capture | [TD-TC-075609.json](test_data/TD-TC-075609.json) |
| TC-075610 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Ma | Happy path | Positive | needs_client_input | p.581; BR-428-8 p.428; BR-581-1 p.581 | 1 | Purchase/Capture | [TD-TC-075610.json](test_data/TD-TC-075610.json) |
| TC-075611 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Pa | Happy path | Positive | needs_client_input | p.582; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075611.json](test_data/TD-TC-075611.json) |
| TC-075612 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Fr | Happy path | Positive | needs_client_input | p.582; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075612.json](test_data/TD-TC-075612.json) |
| TC-075613 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Fi | Happy path | Positive | needs_client_input | p.583; BR-583-1 p.583; BR-583-2 p.583 | 1 | Purchase/Capture | [TD-TC-075613.json](test_data/TD-TC-075613.json) |
| TC-075614 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Co | Happy path | Positive | needs_client_input | p.583; BR-583-3 p.583; p.511 | 1 | Purchase/Capture | [TD-TC-075614.json](test_data/TD-TC-075614.json) |
| TC-075615 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Ca | Happy path | Positive | needs_client_input | p.583; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075615.json](test_data/TD-TC-075615.json) |
| TC-075616 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Pr | Happy path | Positive | needs_client_input | p.585; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075616.json](test_data/TD-TC-075616.json) |
| TC-075617 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with So | Happy path | Positive | needs_client_input | p.586; BR-590-4 p.590; p.511 | 1 | Purchase/Capture | [TD-TC-075617.json](test_data/TD-TC-075617.json) |
| TC-075618 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Ma | Happy path | Positive | ready | p.586; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075618.json](test_data/TD-TC-075618.json) |
| TC-075619 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Ad | Happy path | Positive | needs_client_input | p.600; BR-600-2 p.600; p.511 | 1 | Purchase/Capture | [TD-TC-075619.json](test_data/TD-TC-075619.json) |
| TC-075620 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Ad | Happy path | Positive | needs_client_input | p.600; BR-600-2 p.600; BR-600-3 p.600 | 1 | Purchase/Capture | [TD-TC-075620.json](test_data/TD-TC-075620.json) |
| TC-075621 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Ad | Happy path | Positive | needs_client_input | p.601; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075621.json](test_data/TD-TC-075621.json) |
| TC-075622 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Re | Happy path | Positive | needs_client_input | p.602; BR-602-1 p.602; p.511 | 1 | Purchase/Capture | [TD-TC-075622.json](test_data/TD-TC-075622.json) |
| TC-075623 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Ex | Happy path | Positive | needs_client_input | p.602; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075623.json](test_data/TD-TC-075623.json) |
| TC-075624 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with So | Happy path | Positive | needs_client_input | p.613; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075624.json](test_data/TD-TC-075624.json) |
| TC-075625 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Re | Happy path | Positive | needs_client_input | p.613; BR-614-4 p.614; BR-614-8 p.614 | 1 | Purchase/Capture | [TD-TC-075625.json](test_data/TD-TC-075625.json) |
| TC-075626 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Vi | Happy path | Positive | needs_client_input | p.615; BR-637-2 p.637; BR-FISERV-EMV-GUIDE-38-6 p.38 | 1 | Purchase/Capture | [TD-TC-075626.json](test_data/TD-TC-075626.json) |
| TC-075627 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Se | Happy path | Positive | needs_client_input | p.620; BR-620-2 p.620; p.511 | 1 | Purchase/Capture | [TD-TC-075627.json](test_data/TD-TC-075627.json) |
| TC-075628 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Me | Happy path | Positive | needs_client_input | p.622; BR-622-5 p.622; BR-623-1 p.623 | 1 | Purchase/Capture | [TD-TC-075628.json](test_data/TD-TC-075628.json) |
| TC-075629 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Di | Happy path | Positive | needs_client_input | p.623; BR-623-5 p.623; p.511 | 1 | Purchase/Capture | [TD-TC-075629.json](test_data/TD-TC-075629.json) |
| TC-075630 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with An | Happy path | Positive | needs_client_input | p.625; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075630.json](test_data/TD-TC-075630.json) |
| TC-075631 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with An | Happy path | Positive | needs_client_input | p.626; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075631.json](test_data/TD-TC-075631.json) |
| TC-075632 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Fu | Happy path | Positive | needs_client_input | p.628; BR-628-2 p.628; p.511 | 1 | Purchase/Capture | [TD-TC-075632.json](test_data/TD-TC-075632.json) |
| TC-075633 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Di | Happy path | Positive | needs_client_input | p.628; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075633.json](test_data/TD-TC-075633.json) |
| TC-075634 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Di | Happy path | Positive | needs_client_input | p.629; BR-629-1 p.629; p.511 | 1 | Purchase/Capture | [TD-TC-075634.json](test_data/TD-TC-075634.json) |
| TC-075635 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with St | Happy path | Positive | needs_client_input | p.636; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075635.json](test_data/TD-TC-075635.json) |
| TC-075636 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-72-10 p.72; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075636.json](test_data/TD-TC-075636.json) |
| TC-075637 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-71-5 p.71; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075637.json](test_data/TD-TC-075637.json) |
| TC-075638 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the | Alternate path | Positive | requires_review | BR-189-3 p.189; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075638.json](test_data/TD-TC-075638.json) |
| TC-075639 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-71-6 p.71; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075639.json](test_data/TD-TC-075639.json) |
| TC-075640 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-71-4 p.71; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075640.json](test_data/TD-TC-075640.json) |
| TC-075641 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-115-20 p.115; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075641.json](test_data/TD-TC-075641.json) |
| TC-075642 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the | Alternate path | Positive | requires_review | BR-577-1 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075642.json](test_data/TD-TC-075642.json) |
| TC-075643 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the | Alternate path | Positive | requires_review | BR-577-2 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075643.json](test_data/TD-TC-075643.json) |
| TC-075644 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the | Alternate path | Positive | requires_review | BR-597-1 p.597; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075644.json](test_data/TD-TC-075644.json) |
| TC-075645 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-69-3 p.69; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075645.json](test_data/TD-TC-075645.json) |
| TC-075646 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture via the | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-37-10 p.37; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075646.json](test_data/TD-TC-075646.json) |
| TC-075647 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then P | Single rule broken | Negative | requires_review | p.546; p.546; p.402 | 1 | Purchase/Capture | [TD-TC-075647.step1.purchase.json](test_data/TD-TC-075647.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-075647.step2.reversal.json](test_data/TD-TC-075647.step2.reversal.json) |
| TC-075648 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then P | Single rule broken | Negative | requires_review | p.546; p.546; p.40 | 1 | Purchase/Capture | [TD-TC-075648.step1.purchase.json](test_data/TD-TC-075648.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-075648.step2.reversal.json](test_data/TD-TC-075648.step2.reversal.json) |
| TC-075649 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then T | Single rule broken | Negative | requires_review | p.546; p.39; p.38 | 1 | Purchase/Capture | [TD-TC-075649.step1.purchase.json](test_data/TD-TC-075649.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075649.step2.timeout-reversal.json](test_data/TD-TC-075649.step2.timeout-reversal.json) |
| TC-075650 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Pa | Single rule broken | Negative | requires_review | BR-FISERV-EMV-GUIDE-70-10 p.70; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075650.json](test_data/TD-TC-075650.json) |
| TC-075651 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Se | Single rule broken | Negative | requires_review | BR-ATL105-TRANSARMOR-13-7 p.13; BR-FISERV-EMV-GUIDE-75-3 p.75; p.511 | 1 | Purchase/Capture | [TD-TC-075651.json](test_data/TD-TC-075651.json) |
| TC-075652 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Pa | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-70-10 p.70; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075652.json](test_data/TD-TC-075652.json) |
| TC-075653 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Se | Decision table | Positive | ready | BR-ATL105-TRANSARMOR-13-7 p.13; BR-FISERV-EMV-GUIDE-75-3 p.75; p.511 | 1 | Purchase/Capture | [TD-TC-075653.json](test_data/TD-TC-075653.json) |
| TC-075654 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Ta | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-156-8 p.156; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075654.json](test_data/TD-TC-075654.json) |
| TC-075655 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Up | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-38-2 p.38; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075655.json](test_data/TD-TC-075655.json) |
| TC-075656 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Va | Decision table | Positive | ready | BR-149-1 p.149; BR-152-1 p.152; p.511 | 1 | Purchase/Capture | [TD-TC-075656.json](test_data/TD-TC-075656.json) |
| TC-075657 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with av | Decision table | Positive | needs_client_input | BR-315-4 p.315; BR-554-4 p.554; BR-622-1 p.622 | 1 | Purchase/Capture | [TD-TC-075657.json](test_data/TD-TC-075657.json) |
| TC-075658 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with co | Decision table | Positive | ready | BR-53-2 p.53; BR-56-8 p.56; BR-364-3 p.364 | 1 | Purchase/Capture | [TD-TC-075658.json](test_data/TD-TC-075658.json) |
| TC-075659 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with cv | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-49-2 p.49; BR-FISERV-EMV-GUIDE-50-2 p.50; BR-FISERV-EMV-GUIDE-50-3 p.50 | 1 | Purchase/Capture | [TD-TC-075659.json](test_data/TD-TC-075659.json) |
| TC-075660 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with cv | Decision table | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-50-5 p.50; BR-FISERV-EMV-GUIDE-87-2 p.87; BR-FISERV-EMV-GUIDE-152-5 p.152 | 1 | Purchase/Capture | [TD-TC-075660.json](test_data/TD-TC-075660.json) |
| TC-075661 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with in | Decision table | Positive | needs_client_input | BR-47-6 p.47; BR-63-4 p.63; BR-312-2 p.312 | 1 | Purchase/Capture | [TD-TC-075661.json](test_data/TD-TC-075661.json) |
| TC-075662 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with op | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-53-3 p.53; BR-FISERV-EMV-GUIDE-53-4 p.53; BR-FISERV-EMV-GUIDE-43-4 p.43 | 1 | Purchase/Capture | [TD-TC-075662.json](test_data/TD-TC-075662.json) |
| TC-075663 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with op | Decision table | Positive | ready | BR-313-3 p.313; BR-ATL105-TRANSARMOR-47-5 p.47; BR-312-1 p.312 | 1 | Purchase/Capture | [TD-TC-075663.json](test_data/TD-TC-075663.json) |
| TC-075664 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Pr | Decision table | Positive | ready | BR-89-3 p.89; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075664.json](test_data/TD-TC-075664.json) |
| TC-075665 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Va | Decision table | Positive | ready | BR-ATL105-TRANSARMOR-28-4 p.28; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075665.json](test_data/TD-TC-075665.json) |
| TC-075666 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then P | Valid flow | Positive | ready | p.546; p.546; BR-402-3 p.402 | 1 | Purchase/Capture | [TD-TC-075666.step1.purchase.json](test_data/TD-TC-075666.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-075666.step2.reversal.json](test_data/TD-TC-075666.step2.reversal.json) |
| TC-075667 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then T | Valid flow | Positive | ready | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-075667.step1.purchase.json](test_data/TD-TC-075667.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075667.step2.timeout-reversal.json](test_data/TD-TC-075667.step2.timeout-reversal.json) |
| TC-075668 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then P | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-075668.step1.purchase.json](test_data/TD-TC-075668.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-075668.step2.split-tender.json](test_data/TD-TC-075668.step2.split-tender.json) |
| TC-075669 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then T | Invalid state transition | Negative | requires_review | p.546; BR-39-1 p.39; p.511 | 1 | Purchase/Capture | [TD-TC-075669.step1.purchase.json](test_data/TD-TC-075669.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075669.step2.timeout-reversal.json](test_data/TD-TC-075669.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-075669.step3.timeout-reversal.json](test_data/TD-TC-075669.step3.timeout-reversal.json) |
| TC-075670 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Time-out Reversal with n | Invalid state transition | Negative | blocked_verification | p.546; BR-39-1 p.39; p.511 | 1 | Time-out Reversal | [TD-TC-075670.json](test_data/TD-TC-075670.json) |
| TC-075671 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (no res | Fault / recovery | Positive | ready | BR-409-4 p.409; BR-ATL105-TRANSARMOR-76-3 p.76; p.511 | 1 | Purchase/Capture | [TD-TC-075671.step1.purchase.json](test_data/TD-TC-075671.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-075671.step2.reversal.json](test_data/TD-TC-075671.step2.reversal.json) |
| TC-075672 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (no res | Fault / recovery | Positive | ready | BR-39-1 p.39; BR-44-1 p.44; BR-43-2 p.43 | 1 | Purchase/Capture | [TD-TC-075672.step1.purchase.json](test_data/TD-TC-075672.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075672.step2.timeout-reversal.json](test_data/TD-TC-075672.step2.timeout-reversal.json) |
| TC-075673 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (no res | Idempotency / duplicate / retry | Positive | ready | BR-47-1 p.47; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075673.step1.purchase.json](test_data/TD-TC-075673.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075673.step2.timeout-reversal.json](test_data/TD-TC-075673.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-075673.step3.timeout-reversal.json](test_data/TD-TC-075673.step3.timeout-reversal.json) |
|  |  |  |  |  | 4 | Time-out Reversal | [TD-TC-075673.step4.timeout-reversal.json](test_data/TD-TC-075673.step4.timeout-reversal.json) |
| TC-075674 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then P | Time-based | Positive | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075674.step1.purchase.json](test_data/TD-TC-075674.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-075674.step2.reversal.json](test_data/TD-TC-075674.step2.reversal.json) |
| TC-075675 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then P | Time-based | Negative | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075675.step1.purchase.json](test_data/TD-TC-075675.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-075675.step2.reversal.json](test_data/TD-TC-075675.step2.reversal.json) |
| TC-075676 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then T | Time-based | Positive | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075676.step1.purchase.json](test_data/TD-TC-075676.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075676.step2.timeout-reversal.json](test_data/TD-TC-075676.step2.timeout-reversal.json) |
| TC-075677 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then T | Time-based | Negative | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075677.step1.purchase.json](test_data/TD-TC-075677.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075677.step2.timeout-reversal.json](test_data/TD-TC-075677.step2.timeout-reversal.json) |
| TC-075678 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then P | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Purchase/Capture | [TD-TC-075678.step1.purchase.json](test_data/TD-TC-075678.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-075678.step2.reversal.json](test_data/TD-TC-075678.step2.reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-075678.step3.totals.json](test_data/TD-TC-075678.step3.totals.json) |
| TC-075679 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then T | Running total / state | Positive | requires_review | p.546; p.39; BR-181-1 p.181 | 1 | Purchase/Capture | [TD-TC-075679.step1.purchase.json](test_data/TD-TC-075679.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075679.step2.timeout-reversal.json](test_data/TD-TC-075679.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-075679.step3.totals.json](test_data/TD-TC-075679.step3.totals.json) |
| TC-075680 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then T | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Purchase/Capture | [TD-TC-075680.step1.purchase.json](test_data/TD-TC-075680.step1.purchase.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-075680.step2.totals.json](test_data/TD-TC-075680.step2.totals.json) |
| TC-075681 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (Additi | Response / oracle check | Positive | ready | BR-650-4 p.650; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075681.json](test_data/TD-TC-075681.json) |
| TC-075682 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (Cardho | Response / oracle check | Positive | ready | BR-FISERV-EMV-GUIDE-146-3 p.146; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075682.json](test_data/TD-TC-075682.json) |
| TC-075683 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (EMV Re | Response / oracle check | Positive | ready | BR-209-3 p.209; BR-FISERV-EMV-GUIDE-55-1 p.55; BR-FISERV-EMV-GUIDE-56-10 p.56 | 1 | Purchase/Capture | [TD-TC-075683.json](test_data/TD-TC-075683.json) |
| TC-075684 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (Networ | Response / oracle check | Positive | ready | BR-339-4 p.339; BR-340-2 p.340; BR-340-3 p.340 | 1 | Purchase/Capture | [TD-TC-075684.json](test_data/TD-TC-075684.json) |
| TC-075685 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then P | Response / oracle check | Negative | needs_client_input | p.546; p.546; BR-402-3 p.402 | 1 | Purchase/Capture | [TD-TC-075685.step1.purchase.json](test_data/TD-TC-075685.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-075685.step2.reversal.json](test_data/TD-TC-075685.step2.reversal.json) |
| TC-075686 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (Real T | Response / oracle check | Positive | ready | BR-317-5 p.317; BR-317-9 p.317; p.511 | 1 | Purchase/Capture | [TD-TC-075686.json](test_data/TD-TC-075686.json) |
| TC-075687 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture, then T | Response / oracle check | Negative | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-075687.step1.purchase.json](test_data/TD-TC-075687.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075687.step2.timeout-reversal.json](test_data/TD-TC-075687.step2.timeout-reversal.json) |
| TC-075688 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (Transa | Response / oracle check | Positive | ready | BR-FISERV-EMV-GUIDE-138-2 p.138; BR-FISERV-EMV-GUIDE-138-4 p.138; BR-FISERV-EMV-GUIDE-141-4 p.141 | 1 | Purchase/Capture | [TD-TC-075688.json](test_data/TD-TC-075688.json) |
| TC-075689 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (Transa | Response / oracle check | Positive | ready | BR-62-6 p.62; BR-94-5 p.94; BR-607-3 p.607 | 1 | Purchase/Capture | [TD-TC-075689.json](test_data/TD-TC-075689.json) |
| TC-075690 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture (card i | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-075690.json](test_data/TD-TC-075690.json) |
| TC-075691 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture sending | Response / oracle check | Positive | ready | BR-545-5 p.545; BR-664-4 p.664; BR-663-1 p.663 | 1 | Purchase/Capture | [TD-TC-075691.json](test_data/TD-TC-075691.json) |
| TC-075692 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture sending | Response / oracle check | Positive | ready | BR-671-6 p.671; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075692.json](test_data/TD-TC-075692.json) |
| TC-075693 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture sending | Response / oracle check | Positive | needs_client_input | BR-656-3 p.656; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075693.json](test_data/TD-TC-075693.json) |
| TC-075694 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Pr | Response / oracle check | Positive | needs_client_input | p.552; BR-552-2 p.552; BR-552-6 p.552 | 1 | Purchase/Capture | [TD-TC-075694.json](test_data/TD-TC-075694.json) |
| TC-075695 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Re | Response / oracle check | Positive | needs_client_input | p.578; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075695.json](test_data/TD-TC-075695.json) |
| TC-075696 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with MC | Response / oracle check | Positive | needs_client_input | p.582; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075696.json](test_data/TD-TC-075696.json) |
| TC-075697 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Me | Response / oracle check | Positive | needs_client_input | p.602; BR-602-1 p.602; p.511 | 1 | Purchase/Capture | [TD-TC-075697.json](test_data/TD-TC-075697.json) |
| TC-075698 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture with Cu | Response / oracle check | Positive | needs_client_input | p.614; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075698.json](test_data/TD-TC-075698.json) |
| TC-075699 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture sending | Response / oracle check | Positive | needs_client_input | BR-252-3 p.252; BR-240-1 p.240; BR-266-3 p.266 | 1 | Purchase/Capture | [TD-TC-075699.json](test_data/TD-TC-075699.json) |
| TC-075700 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture sending | Response / oracle check | Positive | needs_client_input | BR-669-3 p.669; BR-669-4 p.669; p.511 | 1 | Purchase/Capture | [TD-TC-075700.json](test_data/TD-TC-075700.json) |
| TC-077180 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture EMV Req | Single rule broken | Negative | blocked_no_test_data | BR-162-5 p.162; BR-ATL105-TRANSARMOR-40-7 p.40; BR-FISERV-EMV-GUIDE-45-1 p.45 | 1 |  | - |
| TC-077182 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture Network | Single rule broken | Negative | requires_review | BR-310-3 p.310; BR-FISERV-EMV-GUIDE-165-1 p.165; BR-312-1 p.312 | 1 |  | [TD-TC-077182.json](test_data/TD-TC-077182.json) |
| TC-077187 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture NFC Pay | Single rule broken | Negative | requires_review | BR-688-2 p.688; BR-156-7 p.156; BR-688-2 p.688 | 1 |  | [TD-TC-077187.json](test_data/TD-TC-077187.json) |
| TC-077190 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture Purchas | Boundary | Negative | requires_review | BR-ATL105-TRANSARMOR-28-3 p.28; BR-FISERV-EMV-GUIDE-43-4 p.43; BR-FISERV-EMV-GUIDE-53-2 p.53 | 1 |  | [TD-TC-077190.json](test_data/TD-TC-077190.json) |
| TC-077206 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture Encrypt | Single rule broken | Boundary | needs_client_input | BR-FISERV-EMV-GUIDE-49-5 p.49; BR-FISERV-EMV-GUIDE-49-6 p.49; p. | 1 |  | [TD-TC-077206.json](test_data/TD-TC-077206.json) |
| TC-077207 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture EMV Chi | Single rule broken | Negative | ready | BR-FISERV-EMV-GUIDE-50-1 p.50; BR-FISERV-EMV-GUIDE-55-12 p.55; BR-FISERV-EMV-GUIDE-55-13 p.55 | 1 |  | [TD-TC-077207.json](test_data/TD-TC-077207.json) |
| TC-077208 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture Require | Single rule broken | Negative | ready | BR-FISERV-EMV-GUIDE-55-12 p.55; BR-FISERV-EMV-GUIDE-55-13 p.55; BR-FISERV-EMV-GUIDE-55-14 p.55 | 1 |  | [TD-TC-077208.json](test_data/TD-TC-077208.json) |
| TC-077211 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture EMV Car | Single rule broken | Negative | ready | BR-FISERV-EMV-GUIDE-72-4 p.72; BR-FISERV-EMV-GUIDE-72-6 p.72; BR-FISERV-EMV-GUIDE-72-9 p.72 | 1 |  | [TD-TC-077211.json](test_data/TD-TC-077211.json) |
| TC-077219 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contact Chip Purchase/Capture Variabl | Single rule broken | Negative | ready | BR-145-2 p.145; BR-148-1 p.148; BR-152-1 p.152 | 1 |  | [TD-TC-077219.json](test_data/TD-TC-077219.json) |

### SC-ATL105-7C23049D - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ General Retail · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-EA8371EB - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Hospitality · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076882 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contact Chip Purchase/Capture  | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-076882.json](test_data/TD-TC-076882.json) |
| TC-076883 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contact Chip Purchase/Capture  | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-076883.json](test_data/TD-TC-076883.json) |

### SC-ATL105-78392C52 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Hospitality · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-B063BCE3 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077135 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contact Chip Purchase/Capture → a | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-077135.json](test_data/TD-TC-077135.json) |
| TC-077136 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contact Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.583; BR-583-1 p.583; BR-583-2 p.583 | 1 | Purchase/Capture | [TD-TC-077136.json](test_data/TD-TC-077136.json) |
| TC-077137 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contact Chip Purchase/Capture, th | Valid flow | Positive | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-077137.step1.purchase.json](test_data/TD-TC-077137.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-077137.step2.timeout-reversal.json](test_data/TD-TC-077137.step2.timeout-reversal.json) |
| TC-077138 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contact Chip Purchase/Capture, th | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-077138.step1.purchase.json](test_data/TD-TC-077138.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-077138.step2.split-tender.json](test_data/TD-TC-077138.step2.split-tender.json) |
| TC-077139 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contact Chip Purchase/Capture, th | Invalid state transition | Negative | needs_client_input | p.546; BR-39-1 p.39; p.511 | 1 | Purchase/Capture | [TD-TC-077139.step1.purchase.json](test_data/TD-TC-077139.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-077139.step2.timeout-reversal.json](test_data/TD-TC-077139.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-077139.step3.timeout-reversal.json](test_data/TD-TC-077139.step3.timeout-reversal.json) |
| TC-077140 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contact Chip Purchase/Capture on  | Interaction (pairwise) | Positive | needs_client_input | p.545; BR-83-2 p.83; BR-FISERV-EMV-GUIDE-49-2 p.49 | 1 | Purchase/Capture | [TD-TC-077140.json](test_data/TD-TC-077140.json) |

### SC-ATL105-5BF8E494 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Merchant category 6051 (cryptocurrency purchase) · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077162 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS EMV Contact Chip Purchase/Capture → a | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-077162.json](test_data/TD-TC-077162.json) |

### SC-ATL105-1768C7CF - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Merchant category 6211 (high risk security) · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077169 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS EMV Contact Chip Purchase/Capture → a | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-077169.json](test_data/TD-TC-077169.json) |

### SC-ATL105-B5496FB5 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076912 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture →  | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-BUYPASS-PIPS-24-7 p.24 | 1 | Purchase/Capture | [TD-TC-076912.json](test_data/TD-TC-076912.json) |
| TC-076913 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture wi | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; BR-BUYPASS-PIPS-185-5 p.185 | 1 | Purchase/Capture | [TD-TC-076913.json](test_data/TD-TC-076913.json) |
| TC-076914 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase Reversal w | Invalid state transition | Negative | blocked_verification | p.546; BR-BUYPASS-PIPS-30-3 p.30; p.511 | 1 | Purchase Reversal | [TD-TC-076914.json](test_data/TD-TC-076914.json) |
| TC-076915 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture, t | Invalid state transition | Negative | needs_client_input | p.546; BR-BUYPASS-PIPS-30-3 p.30; p.511 | 1 | Purchase/Capture | [TD-TC-076915.step1.purchase.json](test_data/TD-TC-076915.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076915.step2.reversal.json](test_data/TD-TC-076915.step2.reversal.json) |
|  |  |  |  |  | 3 | Purchase Reversal | [TD-TC-076915.step3.reversal.json](test_data/TD-TC-076915.step3.reversal.json) |
| TC-076916 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture re | Idempotency / duplicate / retry | Positive | requires_review | BR-BUYPASS-PIPS-17-2 p.17; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076916.json](test_data/TD-TC-076916.json) |
| TC-076917 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture re | Idempotency / duplicate / retry | Negative | requires_review | BR-BUYPASS-PIPS-17-2 p.17; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076917.json](test_data/TD-TC-076917.json) |
| TC-076918 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture he | Time-based | Positive | requires_review | BR-BUYPASS-PIPS-21-2 p.21; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076918.json](test_data/TD-TC-076918.json) |
| TC-076919 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture he | Time-based | Negative | requires_review | BR-BUYPASS-PIPS-21-2 p.21; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076919.json](test_data/TD-TC-076919.json) |
| TC-076920 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture (A | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-177-2 p.177; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076920.json](test_data/TD-TC-076920.json) |
| TC-076921 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture (E | Response / oracle check | Positive | needs_client_input | BR-209-3 p.209; BR-FISERV-EMV-GUIDE-55-1 p.55; BR-FISERV-EMV-GUIDE-56-10 p.56 | 1 | Purchase/Capture | [TD-TC-076921.json](test_data/TD-TC-076921.json) |
| TC-076922 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture (T | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-138-2 p.138; BR-FISERV-EMV-GUIDE-138-4 p.138; BR-FISERV-EMV-GUIDE-141-4 p.141 | 1 | Purchase/Capture | [TD-TC-076922.json](test_data/TD-TC-076922.json) |
| TC-076923 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture (c | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-076923.json](test_data/TD-TC-076923.json) |
| TC-077195 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture Pr | Single rule broken | Negative | needs_client_input | BR-BUYPASS-PIPS-131-4 p.131; BR-BUYPASS-PIPS-135-1 p.135; p. | 1 |  | [TD-TC-077195.json](test_data/TD-TC-077195.json) |
| TC-077202 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture Ad | Single rule broken | Boundary | needs_client_input | BR-BUYPASS-PIPS-186-1 p.186; p. | 1 |  | [TD-TC-077202.json](test_data/TD-TC-077202.json) |
| TC-077203 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contact Chip Purchase/Capture Cu | Single rule broken | Negative | needs_client_input | BR-BUYPASS-PIPS-16-3 p.16; p. | 1 |  | [TD-TC-077203.json](test_data/TD-TC-077203.json) |

### SC-ATL105-BF3D1777 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Petroleum · unattended CAT (fuel machine)
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077025 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture →  | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-BUYPASS-PIPS-24-7 p.24 | 1 | Purchase/Capture | [TD-TC-077025.json](test_data/TD-TC-077025.json) |
| TC-077026 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture wi | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; BR-BUYPASS-PIPS-185-5 p.185 | 1 | Purchase/Capture | [TD-TC-077026.json](test_data/TD-TC-077026.json) |
| TC-077027 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture wi | Decision table | Positive | needs_client_input | BR-47-6 p.47; BR-63-4 p.63; BR-312-2 p.312 | 1 | Purchase/Capture | [TD-TC-077027.json](test_data/TD-TC-077027.json) |
| TC-077028 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture, t | Valid flow | Positive | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-077028.step1.purchase.json](test_data/TD-TC-077028.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-077028.step2.timeout-reversal.json](test_data/TD-TC-077028.step2.timeout-reversal.json) |
| TC-077029 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture, t | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-077029.step1.purchase.json](test_data/TD-TC-077029.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-077029.step2.split-tender.json](test_data/TD-TC-077029.step2.split-tender.json) |
| TC-077030 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture, t | Invalid state transition | Negative | needs_client_input | p.546; BR-39-1 p.39; p.511 | 1 | Purchase/Capture | [TD-TC-077030.step1.purchase.json](test_data/TD-TC-077030.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-077030.step2.timeout-reversal.json](test_data/TD-TC-077030.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-077030.step3.timeout-reversal.json](test_data/TD-TC-077030.step3.timeout-reversal.json) |
| TC-077031 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture re | Idempotency / duplicate / retry | Positive | requires_review | BR-BUYPASS-PIPS-17-2 p.17; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-077031.json](test_data/TD-TC-077031.json) |
| TC-077032 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture re | Idempotency / duplicate / retry | Negative | requires_review | BR-BUYPASS-PIPS-17-2 p.17; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-077032.json](test_data/TD-TC-077032.json) |
| TC-077033 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture he | Time-based | Positive | requires_review | BR-63-2 p.63; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-077033.json](test_data/TD-TC-077033.json) |
| TC-077034 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture he | Time-based | Negative | requires_review | BR-63-2 p.63; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-077034.json](test_data/TD-TC-077034.json) |
| TC-077035 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture (A | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-177-2 p.177; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-077035.json](test_data/TD-TC-077035.json) |
| TC-077036 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture (E | Response / oracle check | Positive | needs_client_input | BR-209-3 p.209; BR-FISERV-EMV-GUIDE-55-1 p.55; BR-FISERV-EMV-GUIDE-56-10 p.56 | 1 | Purchase/Capture | [TD-TC-077036.json](test_data/TD-TC-077036.json) |
| TC-077037 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture (T | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-138-2 p.138; BR-FISERV-EMV-GUIDE-138-4 p.138; BR-FISERV-EMV-GUIDE-141-4 p.141 | 1 | Purchase/Capture | [TD-TC-077037.json](test_data/TD-TC-077037.json) |
| TC-077038 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture (T | Response / oracle check | Positive | needs_client_input | BR-62-6 p.62; BR-94-5 p.94; BR-607-3 p.607 | 1 | Purchase/Capture | [TD-TC-077038.json](test_data/TD-TC-077038.json) |
| TC-077039 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture (c | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-077039.json](test_data/TD-TC-077039.json) |
| TC-077192 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contact Chip Purchase/Capture Pr | Single rule broken | Negative | needs_client_input | BR-BUYPASS-PIPS-25-1 p.25; p. | 1 |  | [TD-TC-077192.json](test_data/TD-TC-077192.json) |

### SC-ATL105-05C50700 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Supermarket · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-FCAA3088 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Supermarket · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-126ED70B - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Utilities · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-940C9AA2 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contact Chip @ Utilities · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-A21F371F - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-075701 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture → a | Happy path | Positive | ready | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-075701.json](test_data/TD-TC-075701.json) |
| TC-075702 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.552; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075702.json](test_data/TD-TC-075702.json) |
| TC-075703 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.555; BR-572-3 p.572; p.511 | 1 | Purchase/Capture | [TD-TC-075703.json](test_data/TD-TC-075703.json) |
| TC-075704 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.556; BR-92-11 p.92; p.511 | 1 | Purchase/Capture | [TD-TC-075704.json](test_data/TD-TC-075704.json) |
| TC-075705 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.561; BR-85-4 p.85; BR-561-2 p.561 | 1 | Purchase/Capture | [TD-TC-075705.json](test_data/TD-TC-075705.json) |
| TC-075706 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.562; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075706.json](test_data/TD-TC-075706.json) |
| TC-075707 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.562; BR-562-2 p.562; p.511 | 1 | Purchase/Capture | [TD-TC-075707.json](test_data/TD-TC-075707.json) |
| TC-075708 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | ready | p.564; BR-564-1 p.564; BR-FISERV-EMV-GUIDE-64-2 p.64 | 1 | Purchase/Capture | [TD-TC-075708.json](test_data/TD-TC-075708.json) |
| TC-075709 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.566; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075709.json](test_data/TD-TC-075709.json) |
| TC-075710 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | ready | p.569; BR-569-1 p.569; p.511 | 1 | Purchase/Capture | [TD-TC-075710.json](test_data/TD-TC-075710.json) |
| TC-075711 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.581; BR-428-8 p.428; BR-581-1 p.581 | 1 | Purchase/Capture | [TD-TC-075711.json](test_data/TD-TC-075711.json) |
| TC-075712 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.582; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075712.json](test_data/TD-TC-075712.json) |
| TC-075713 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.582; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075713.json](test_data/TD-TC-075713.json) |
| TC-075714 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.583; BR-583-1 p.583; BR-583-2 p.583 | 1 | Purchase/Capture | [TD-TC-075714.json](test_data/TD-TC-075714.json) |
| TC-075715 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.583; BR-583-3 p.583; p.511 | 1 | Purchase/Capture | [TD-TC-075715.json](test_data/TD-TC-075715.json) |
| TC-075716 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.583; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075716.json](test_data/TD-TC-075716.json) |
| TC-075717 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.585; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075717.json](test_data/TD-TC-075717.json) |
| TC-075718 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.586; BR-590-4 p.590; p.511 | 1 | Purchase/Capture | [TD-TC-075718.json](test_data/TD-TC-075718.json) |
| TC-075719 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | ready | p.586; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075719.json](test_data/TD-TC-075719.json) |
| TC-075720 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.600; BR-600-2 p.600; p.511 | 1 | Purchase/Capture | [TD-TC-075720.json](test_data/TD-TC-075720.json) |
| TC-075721 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.600; BR-600-2 p.600; BR-600-3 p.600 | 1 | Purchase/Capture | [TD-TC-075721.json](test_data/TD-TC-075721.json) |
| TC-075722 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.601; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075722.json](test_data/TD-TC-075722.json) |
| TC-075723 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.602; BR-602-1 p.602; p.511 | 1 | Purchase/Capture | [TD-TC-075723.json](test_data/TD-TC-075723.json) |
| TC-075724 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.602; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075724.json](test_data/TD-TC-075724.json) |
| TC-075725 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.613; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075725.json](test_data/TD-TC-075725.json) |
| TC-075726 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.613; BR-614-4 p.614; BR-614-8 p.614 | 1 | Purchase/Capture | [TD-TC-075726.json](test_data/TD-TC-075726.json) |
| TC-075727 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.615; BR-637-2 p.637; BR-FISERV-EMV-GUIDE-38-6 p.38 | 1 | Purchase/Capture | [TD-TC-075727.json](test_data/TD-TC-075727.json) |
| TC-075728 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.620; BR-620-2 p.620; p.511 | 1 | Purchase/Capture | [TD-TC-075728.json](test_data/TD-TC-075728.json) |
| TC-075729 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.622; BR-622-5 p.622; BR-623-1 p.623 | 1 | Purchase/Capture | [TD-TC-075729.json](test_data/TD-TC-075729.json) |
| TC-075730 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.623; BR-623-5 p.623; p.511 | 1 | Purchase/Capture | [TD-TC-075730.json](test_data/TD-TC-075730.json) |
| TC-075731 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.625; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075731.json](test_data/TD-TC-075731.json) |
| TC-075732 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.626; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075732.json](test_data/TD-TC-075732.json) |
| TC-075733 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.628; BR-628-2 p.628; p.511 | 1 | Purchase/Capture | [TD-TC-075733.json](test_data/TD-TC-075733.json) |
| TC-075734 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.628; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075734.json](test_data/TD-TC-075734.json) |
| TC-075735 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.629; BR-629-1 p.629; p.511 | 1 | Purchase/Capture | [TD-TC-075735.json](test_data/TD-TC-075735.json) |
| TC-075736 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Happy path | Positive | needs_client_input | p.636; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075736.json](test_data/TD-TC-075736.json) |
| TC-075737 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-72-10 p.72; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075737.json](test_data/TD-TC-075737.json) |
| TC-075738 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-71-5 p.71; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075738.json](test_data/TD-TC-075738.json) |
| TC-075739 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via | Alternate path | Positive | requires_review | BR-189-3 p.189; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075739.json](test_data/TD-TC-075739.json) |
| TC-075740 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-71-6 p.71; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075740.json](test_data/TD-TC-075740.json) |
| TC-075741 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-71-4 p.71; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075741.json](test_data/TD-TC-075741.json) |
| TC-075742 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-115-20 p.115; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075742.json](test_data/TD-TC-075742.json) |
| TC-075743 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via | Alternate path | Positive | requires_review | BR-577-1 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075743.json](test_data/TD-TC-075743.json) |
| TC-075744 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via | Alternate path | Positive | requires_review | BR-577-2 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075744.json](test_data/TD-TC-075744.json) |
| TC-075745 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via | Alternate path | Positive | requires_review | BR-597-1 p.597; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075745.json](test_data/TD-TC-075745.json) |
| TC-075746 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-69-3 p.69; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075746.json](test_data/TD-TC-075746.json) |
| TC-075747 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture via | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-37-10 p.37; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075747.json](test_data/TD-TC-075747.json) |
| TC-075748 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, th | Single rule broken | Negative | requires_review | p.546; p.546; p.402 | 1 | Purchase/Capture | [TD-TC-075748.step1.purchase.json](test_data/TD-TC-075748.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-075748.step2.reversal.json](test_data/TD-TC-075748.step2.reversal.json) |
| TC-075749 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, th | Single rule broken | Negative | requires_review | p.546; p.546; p.40 | 1 | Purchase/Capture | [TD-TC-075749.step1.purchase.json](test_data/TD-TC-075749.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-075749.step2.reversal.json](test_data/TD-TC-075749.step2.reversal.json) |
| TC-075750 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, th | Single rule broken | Negative | requires_review | p.546; p.39; p.38 | 1 | Purchase/Capture | [TD-TC-075750.step1.purchase.json](test_data/TD-TC-075750.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075750.step2.timeout-reversal.json](test_data/TD-TC-075750.step2.timeout-reversal.json) |
| TC-075751 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Single rule broken | Negative | requires_review | BR-FISERV-EMV-GUIDE-70-10 p.70; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075751.json](test_data/TD-TC-075751.json) |
| TC-075752 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Single rule broken | Negative | requires_review | BR-ATL105-TRANSARMOR-13-7 p.13; BR-FISERV-EMV-GUIDE-75-3 p.75; p.511 | 1 | Purchase/Capture | [TD-TC-075752.json](test_data/TD-TC-075752.json) |
| TC-075753 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-70-10 p.70; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075753.json](test_data/TD-TC-075753.json) |
| TC-075754 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Decision table | Positive | ready | BR-ATL105-TRANSARMOR-13-7 p.13; BR-FISERV-EMV-GUIDE-75-3 p.75; p.511 | 1 | Purchase/Capture | [TD-TC-075754.json](test_data/TD-TC-075754.json) |
| TC-075755 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-156-8 p.156; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075755.json](test_data/TD-TC-075755.json) |
| TC-075756 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-38-2 p.38; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075756.json](test_data/TD-TC-075756.json) |
| TC-075757 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Decision table | Positive | ready | BR-149-1 p.149; BR-152-1 p.152; p.511 | 1 | Purchase/Capture | [TD-TC-075757.json](test_data/TD-TC-075757.json) |
| TC-075758 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Decision table | Positive | needs_client_input | BR-315-4 p.315; BR-554-4 p.554; BR-622-1 p.622 | 1 | Purchase/Capture | [TD-TC-075758.json](test_data/TD-TC-075758.json) |
| TC-075759 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Decision table | Positive | ready | BR-53-2 p.53; BR-56-8 p.56; BR-364-3 p.364 | 1 | Purchase/Capture | [TD-TC-075759.json](test_data/TD-TC-075759.json) |
| TC-075760 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-49-2 p.49; BR-FISERV-EMV-GUIDE-50-2 p.50; BR-FISERV-EMV-GUIDE-50-3 p.50 | 1 | Purchase/Capture | [TD-TC-075760.json](test_data/TD-TC-075760.json) |
| TC-075761 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Decision table | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-50-5 p.50; BR-FISERV-EMV-GUIDE-87-2 p.87; BR-FISERV-EMV-GUIDE-152-5 p.152 | 1 | Purchase/Capture | [TD-TC-075761.json](test_data/TD-TC-075761.json) |
| TC-075762 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-53-3 p.53; BR-FISERV-EMV-GUIDE-53-4 p.53; BR-FISERV-EMV-GUIDE-43-4 p.43 | 1 | Purchase/Capture | [TD-TC-075762.json](test_data/TD-TC-075762.json) |
| TC-075763 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Decision table | Positive | ready | BR-313-3 p.313; BR-ATL105-TRANSARMOR-47-5 p.47; BR-312-1 p.312 | 1 | Purchase/Capture | [TD-TC-075763.json](test_data/TD-TC-075763.json) |
| TC-075764 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Decision table | Positive | ready | BR-89-3 p.89; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075764.json](test_data/TD-TC-075764.json) |
| TC-075765 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Decision table | Positive | ready | BR-ATL105-TRANSARMOR-28-4 p.28; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075765.json](test_data/TD-TC-075765.json) |
| TC-075766 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, th | Valid flow | Positive | ready | p.546; p.546; BR-402-3 p.402 | 1 | Purchase/Capture | [TD-TC-075766.step1.purchase.json](test_data/TD-TC-075766.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-075766.step2.reversal.json](test_data/TD-TC-075766.step2.reversal.json) |
| TC-075767 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, th | Valid flow | Positive | ready | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-075767.step1.purchase.json](test_data/TD-TC-075767.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075767.step2.timeout-reversal.json](test_data/TD-TC-075767.step2.timeout-reversal.json) |
| TC-075768 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, th | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-075768.step1.purchase.json](test_data/TD-TC-075768.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-075768.step2.split-tender.json](test_data/TD-TC-075768.step2.split-tender.json) |
| TC-075769 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, th | Invalid state transition | Negative | requires_review | p.546; BR-39-1 p.39; p.511 | 1 | Purchase/Capture | [TD-TC-075769.step1.purchase.json](test_data/TD-TC-075769.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075769.step2.timeout-reversal.json](test_data/TD-TC-075769.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-075769.step3.timeout-reversal.json](test_data/TD-TC-075769.step3.timeout-reversal.json) |
| TC-075770 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Time-out Reversal wi | Invalid state transition | Negative | blocked_verification | p.546; BR-39-1 p.39; p.511 | 1 | Time-out Reversal | [TD-TC-075770.json](test_data/TD-TC-075770.json) |
| TC-075771 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (no | Fault / recovery | Positive | ready | BR-409-4 p.409; BR-ATL105-TRANSARMOR-76-3 p.76; p.511 | 1 | Purchase/Capture | [TD-TC-075771.step1.purchase.json](test_data/TD-TC-075771.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-075771.step2.reversal.json](test_data/TD-TC-075771.step2.reversal.json) |
| TC-075772 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (no | Fault / recovery | Positive | ready | BR-39-1 p.39; BR-44-1 p.44; BR-43-2 p.43 | 1 | Purchase/Capture | [TD-TC-075772.step1.purchase.json](test_data/TD-TC-075772.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075772.step2.timeout-reversal.json](test_data/TD-TC-075772.step2.timeout-reversal.json) |
| TC-075773 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (no | Idempotency / duplicate / retry | Positive | ready | BR-47-1 p.47; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075773.step1.purchase.json](test_data/TD-TC-075773.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075773.step2.timeout-reversal.json](test_data/TD-TC-075773.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-075773.step3.timeout-reversal.json](test_data/TD-TC-075773.step3.timeout-reversal.json) |
|  |  |  |  |  | 4 | Time-out Reversal | [TD-TC-075773.step4.timeout-reversal.json](test_data/TD-TC-075773.step4.timeout-reversal.json) |
| TC-075774 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, th | Time-based | Positive | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075774.step1.purchase.json](test_data/TD-TC-075774.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-075774.step2.reversal.json](test_data/TD-TC-075774.step2.reversal.json) |
| TC-075775 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, th | Time-based | Negative | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075775.step1.purchase.json](test_data/TD-TC-075775.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-075775.step2.reversal.json](test_data/TD-TC-075775.step2.reversal.json) |
| TC-075776 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, th | Time-based | Positive | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075776.step1.purchase.json](test_data/TD-TC-075776.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075776.step2.timeout-reversal.json](test_data/TD-TC-075776.step2.timeout-reversal.json) |
| TC-075777 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, th | Time-based | Negative | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075777.step1.purchase.json](test_data/TD-TC-075777.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075777.step2.timeout-reversal.json](test_data/TD-TC-075777.step2.timeout-reversal.json) |
| TC-075778 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, th | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Purchase/Capture | [TD-TC-075778.step1.purchase.json](test_data/TD-TC-075778.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-075778.step2.reversal.json](test_data/TD-TC-075778.step2.reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-075778.step3.totals.json](test_data/TD-TC-075778.step3.totals.json) |
| TC-075779 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, th | Running total / state | Positive | requires_review | p.546; p.39; BR-181-1 p.181 | 1 | Purchase/Capture | [TD-TC-075779.step1.purchase.json](test_data/TD-TC-075779.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075779.step2.timeout-reversal.json](test_data/TD-TC-075779.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-075779.step3.totals.json](test_data/TD-TC-075779.step3.totals.json) |
| TC-075780 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, th | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Purchase/Capture | [TD-TC-075780.step1.purchase.json](test_data/TD-TC-075780.step1.purchase.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-075780.step2.totals.json](test_data/TD-TC-075780.step2.totals.json) |
| TC-075781 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (Ad | Response / oracle check | Positive | ready | BR-650-4 p.650; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075781.json](test_data/TD-TC-075781.json) |
| TC-075782 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (Ca | Response / oracle check | Positive | ready | BR-FISERV-EMV-GUIDE-146-3 p.146; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075782.json](test_data/TD-TC-075782.json) |
| TC-075783 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (EM | Response / oracle check | Positive | ready | BR-209-3 p.209; BR-FISERV-EMV-GUIDE-55-1 p.55; BR-FISERV-EMV-GUIDE-56-10 p.56 | 1 | Purchase/Capture | [TD-TC-075783.json](test_data/TD-TC-075783.json) |
| TC-075784 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (Ne | Response / oracle check | Positive | ready | BR-339-4 p.339; BR-340-2 p.340; BR-340-3 p.340 | 1 | Purchase/Capture | [TD-TC-075784.json](test_data/TD-TC-075784.json) |
| TC-075785 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, th | Response / oracle check | Negative | needs_client_input | p.546; p.546; BR-402-3 p.402 | 1 | Purchase/Capture | [TD-TC-075785.step1.purchase.json](test_data/TD-TC-075785.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-075785.step2.reversal.json](test_data/TD-TC-075785.step2.reversal.json) |
| TC-075786 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (Re | Response / oracle check | Positive | ready | BR-317-5 p.317; BR-317-9 p.317; p.511 | 1 | Purchase/Capture | [TD-TC-075786.json](test_data/TD-TC-075786.json) |
| TC-075787 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture, th | Response / oracle check | Negative | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-075787.step1.purchase.json](test_data/TD-TC-075787.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075787.step2.timeout-reversal.json](test_data/TD-TC-075787.step2.timeout-reversal.json) |
| TC-075788 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (Tr | Response / oracle check | Positive | ready | BR-FISERV-EMV-GUIDE-138-2 p.138; BR-FISERV-EMV-GUIDE-138-4 p.138; BR-FISERV-EMV-GUIDE-141-4 p.141 | 1 | Purchase/Capture | [TD-TC-075788.json](test_data/TD-TC-075788.json) |
| TC-075789 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (Tr | Response / oracle check | Positive | ready | BR-62-6 p.62; BR-94-5 p.94; BR-607-3 p.607 | 1 | Purchase/Capture | [TD-TC-075789.json](test_data/TD-TC-075789.json) |
| TC-075790 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture (ca | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-075790.json](test_data/TD-TC-075790.json) |
| TC-075791 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture sen | Response / oracle check | Positive | ready | BR-545-5 p.545; BR-664-4 p.664; BR-663-1 p.663 | 1 | Purchase/Capture | [TD-TC-075791.json](test_data/TD-TC-075791.json) |
| TC-075792 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture sen | Response / oracle check | Positive | ready | BR-671-6 p.671; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075792.json](test_data/TD-TC-075792.json) |
| TC-075793 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture sen | Response / oracle check | Positive | needs_client_input | BR-656-3 p.656; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075793.json](test_data/TD-TC-075793.json) |
| TC-075794 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Response / oracle check | Positive | needs_client_input | p.552; BR-552-2 p.552; BR-552-6 p.552 | 1 | Purchase/Capture | [TD-TC-075794.json](test_data/TD-TC-075794.json) |
| TC-075795 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Response / oracle check | Positive | needs_client_input | p.578; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075795.json](test_data/TD-TC-075795.json) |
| TC-075796 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Response / oracle check | Positive | needs_client_input | p.582; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075796.json](test_data/TD-TC-075796.json) |
| TC-075797 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Response / oracle check | Positive | needs_client_input | p.602; BR-602-1 p.602; p.511 | 1 | Purchase/Capture | [TD-TC-075797.json](test_data/TD-TC-075797.json) |
| TC-075798 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture wit | Response / oracle check | Positive | needs_client_input | p.614; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075798.json](test_data/TD-TC-075798.json) |
| TC-075799 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture sen | Response / oracle check | Positive | needs_client_input | BR-252-3 p.252; BR-240-1 p.240; BR-266-3 p.266 | 1 | Purchase/Capture | [TD-TC-075799.json](test_data/TD-TC-075799.json) |
| TC-075800 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture sen | Response / oracle check | Positive | needs_client_input | BR-669-3 p.669; BR-669-4 p.669; p.511 | 1 | Purchase/Capture | [TD-TC-075800.json](test_data/TD-TC-075800.json) |
| TC-077212 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture Req | Single rule broken | Negative | needs_client_input | BR-FISERV-EMV-GUIDE-74-9 p.74; p. | 1 |  | [TD-TC-077212.json](test_data/TD-TC-077212.json) |
| TC-077213 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS EMV Contactless Chip Purchase/Capture CA  | Single rule broken | Boundary | ready | BR-FISERV-EMV-GUIDE-94-4 p.94; p. | 1 |  | [TD-TC-077213.json](test_data/TD-TC-077213.json) |

### SC-ATL105-D75CF311 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ General Retail · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-2B9A3CD9 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Hospitality · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076884 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contactless Chip Purchase/Capt | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-076884.json](test_data/TD-TC-076884.json) |
| TC-076885 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS EMV Contactless Chip Purchase/Capt | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-076885.json](test_data/TD-TC-076885.json) |

### SC-ATL105-948B2767 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Hospitality · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-87D1178C - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077141 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contactless Chip Purchase/Capture | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-077141.json](test_data/TD-TC-077141.json) |
| TC-077142 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contactless Chip Purchase/Capture | Happy path | Positive | needs_client_input | p.583; BR-583-1 p.583; BR-583-2 p.583 | 1 | Purchase/Capture | [TD-TC-077142.json](test_data/TD-TC-077142.json) |
| TC-077143 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contactless Chip Purchase/Capture | Valid flow | Positive | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-077143.step1.purchase.json](test_data/TD-TC-077143.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-077143.step2.timeout-reversal.json](test_data/TD-TC-077143.step2.timeout-reversal.json) |
| TC-077144 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contactless Chip Purchase/Capture | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-077144.step1.purchase.json](test_data/TD-TC-077144.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-077144.step2.split-tender.json](test_data/TD-TC-077144.step2.split-tender.json) |
| TC-077145 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contactless Chip Purchase/Capture | Invalid state transition | Negative | needs_client_input | p.546; BR-39-1 p.39; p.511 | 1 | Purchase/Capture | [TD-TC-077145.step1.purchase.json](test_data/TD-TC-077145.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-077145.step2.timeout-reversal.json](test_data/TD-TC-077145.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-077145.step3.timeout-reversal.json](test_data/TD-TC-077145.step3.timeout-reversal.json) |
| TC-077146 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT EMV Contactless Chip Purchase/Capture | Interaction (pairwise) | Positive | needs_client_input | p.545; BR-83-2 p.83; BR-FISERV-EMV-GUIDE-49-2 p.49 | 1 | Purchase/Capture | [TD-TC-077146.json](test_data/TD-TC-077146.json) |

### SC-ATL105-A94BEA8D - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Merchant category 6051 (cryptocurrency purchase) · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077163 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS EMV Contactless Chip Purchase/Capture | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-077163.json](test_data/TD-TC-077163.json) |

### SC-ATL105-881273CB - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Merchant category 6211 (high risk security) · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077170 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS EMV Contactless Chip Purchase/Capture | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-077170.json](test_data/TD-TC-077170.json) |

### SC-ATL105-B4E6A1C1 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076924 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Captur | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-BUYPASS-PIPS-24-7 p.24 | 1 | Purchase/Capture | [TD-TC-076924.json](test_data/TD-TC-076924.json) |
| TC-076925 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Captur | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; BR-BUYPASS-PIPS-185-5 p.185 | 1 | Purchase/Capture | [TD-TC-076925.json](test_data/TD-TC-076925.json) |
| TC-076926 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase Revers | Invalid state transition | Negative | blocked_verification | p.546; BR-BUYPASS-PIPS-30-3 p.30; p.511 | 1 | Purchase Reversal | [TD-TC-076926.json](test_data/TD-TC-076926.json) |
| TC-076927 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Captur | Invalid state transition | Negative | needs_client_input | p.546; BR-BUYPASS-PIPS-30-3 p.30; p.511 | 1 | Purchase/Capture | [TD-TC-076927.step1.purchase.json](test_data/TD-TC-076927.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076927.step2.reversal.json](test_data/TD-TC-076927.step2.reversal.json) |
|  |  |  |  |  | 3 | Purchase Reversal | [TD-TC-076927.step3.reversal.json](test_data/TD-TC-076927.step3.reversal.json) |
| TC-076928 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Captur | Idempotency / duplicate / retry | Positive | requires_review | BR-BUYPASS-PIPS-17-2 p.17; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076928.json](test_data/TD-TC-076928.json) |
| TC-076929 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Captur | Idempotency / duplicate / retry | Negative | requires_review | BR-BUYPASS-PIPS-17-2 p.17; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076929.json](test_data/TD-TC-076929.json) |
| TC-076930 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Captur | Time-based | Positive | requires_review | BR-BUYPASS-PIPS-21-2 p.21; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076930.json](test_data/TD-TC-076930.json) |
| TC-076931 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Captur | Time-based | Negative | requires_review | BR-BUYPASS-PIPS-21-2 p.21; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076931.json](test_data/TD-TC-076931.json) |
| TC-076932 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Captur | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-177-2 p.177; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076932.json](test_data/TD-TC-076932.json) |
| TC-076933 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Captur | Response / oracle check | Positive | needs_client_input | BR-209-3 p.209; BR-FISERV-EMV-GUIDE-55-1 p.55; BR-FISERV-EMV-GUIDE-56-10 p.56 | 1 | Purchase/Capture | [TD-TC-076933.json](test_data/TD-TC-076933.json) |
| TC-076934 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Captur | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-138-2 p.138; BR-FISERV-EMV-GUIDE-138-4 p.138; BR-FISERV-EMV-GUIDE-141-4 p.141 | 1 | Purchase/Capture | [TD-TC-076934.json](test_data/TD-TC-076934.json) |
| TC-076935 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Captur | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-076935.json](test_data/TD-TC-076935.json) |
| TC-077198 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Captur | Single rule broken | Negative | needs_client_input | BR-BUYPASS-PIPS-181-2 p.181; p. | 1 |  | [TD-TC-077198.json](test_data/TD-TC-077198.json) |
| TC-077214 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS EMV Contactless Chip Purchase/Captur | Single rule broken | Negative | needs_client_input | BR-FISERV-EMV-GUIDE-173-2 p.173; p. | 1 |  | [TD-TC-077214.json](test_data/TD-TC-077214.json) |

### SC-ATL105-D4408D33 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Petroleum · unattended CAT (fuel machine)
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077040 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Captur | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-BUYPASS-PIPS-24-7 p.24 | 1 | Purchase/Capture | [TD-TC-077040.json](test_data/TD-TC-077040.json) |
| TC-077041 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Captur | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; BR-BUYPASS-PIPS-185-5 p.185 | 1 | Purchase/Capture | [TD-TC-077041.json](test_data/TD-TC-077041.json) |
| TC-077042 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Captur | Valid flow | Positive | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-077042.step1.purchase.json](test_data/TD-TC-077042.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-077042.step2.timeout-reversal.json](test_data/TD-TC-077042.step2.timeout-reversal.json) |
| TC-077043 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Captur | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-077043.step1.purchase.json](test_data/TD-TC-077043.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-077043.step2.split-tender.json](test_data/TD-TC-077043.step2.split-tender.json) |
| TC-077044 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Captur | Invalid state transition | Negative | needs_client_input | p.546; BR-39-1 p.39; p.511 | 1 | Purchase/Capture | [TD-TC-077044.step1.purchase.json](test_data/TD-TC-077044.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-077044.step2.timeout-reversal.json](test_data/TD-TC-077044.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-077044.step3.timeout-reversal.json](test_data/TD-TC-077044.step3.timeout-reversal.json) |
| TC-077045 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Captur | Idempotency / duplicate / retry | Positive | requires_review | BR-BUYPASS-PIPS-17-2 p.17; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-077045.json](test_data/TD-TC-077045.json) |
| TC-077046 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Captur | Idempotency / duplicate / retry | Negative | requires_review | BR-BUYPASS-PIPS-17-2 p.17; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-077046.json](test_data/TD-TC-077046.json) |
| TC-077047 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Captur | Time-based | Positive | requires_review | BR-63-2 p.63; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-077047.json](test_data/TD-TC-077047.json) |
| TC-077048 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Captur | Time-based | Negative | requires_review | BR-63-2 p.63; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-077048.json](test_data/TD-TC-077048.json) |
| TC-077049 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Captur | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-177-2 p.177; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-077049.json](test_data/TD-TC-077049.json) |
| TC-077050 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Captur | Response / oracle check | Positive | needs_client_input | BR-209-3 p.209; BR-FISERV-EMV-GUIDE-55-1 p.55; BR-FISERV-EMV-GUIDE-56-10 p.56 | 1 | Purchase/Capture | [TD-TC-077050.json](test_data/TD-TC-077050.json) |
| TC-077051 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Captur | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-138-2 p.138; BR-FISERV-EMV-GUIDE-138-4 p.138; BR-FISERV-EMV-GUIDE-141-4 p.141 | 1 | Purchase/Capture | [TD-TC-077051.json](test_data/TD-TC-077051.json) |
| TC-077052 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Captur | Response / oracle check | Positive | needs_client_input | BR-62-6 p.62; BR-94-5 p.94; BR-607-3 p.607 | 1 | Purchase/Capture | [TD-TC-077052.json](test_data/TD-TC-077052.json) |
| TC-077053 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT EMV Contactless Chip Purchase/Captur | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-077053.json](test_data/TD-TC-077053.json) |

### SC-ATL105-7A4D2913 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Supermarket · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-AB57A844 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Supermarket · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-E6FF365F - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Utilities · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-3F1DD348 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – EMV Contactless Chip @ Utilities · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-12BB9B38 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-075801 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture → approv | Happy path | Positive | ready | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-075801.json](test_data/TD-TC-075801.json) |
| TC-075802 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with UPC | Happy path | Positive | needs_client_input | p.552; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075802.json](test_data/TD-TC-075802.json) |
| TC-075803 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Cur | Happy path | Positive | needs_client_input | p.555; BR-572-3 p.572; p.511 | 1 | Purchase/Capture | [TD-TC-075803.json](test_data/TD-TC-075803.json) |
| TC-075804 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Bal | Happy path | Positive | needs_client_input | p.556; BR-92-11 p.92; p.511 | 1 | Purchase/Capture | [TD-TC-075804.json](test_data/TD-TC-075804.json) |
| TC-075805 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Sus | Happy path | Positive | needs_client_input | p.561; BR-85-4 p.85; BR-561-2 p.561 | 1 | Purchase/Capture | [TD-TC-075805.json](test_data/TD-TC-075805.json) |
| TC-075806 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Car | Happy path | Positive | needs_client_input | p.562; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075806.json](test_data/TD-TC-075806.json) |
| TC-075807 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Rec | Happy path | Positive | needs_client_input | p.562; BR-562-2 p.562; p.511 | 1 | Purchase/Capture | [TD-TC-075807.json](test_data/TD-TC-075807.json) |
| TC-075808 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Ori | Happy path | Positive | ready | p.564; BR-564-1 p.564; BR-FISERV-EMV-GUIDE-64-2 p.64 | 1 | Purchase/Capture | [TD-TC-075808.json](test_data/TD-TC-075808.json) |
| TC-075809 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Cus | Happy path | Positive | needs_client_input | p.566; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075809.json](test_data/TD-TC-075809.json) |
| TC-075810 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Poi | Happy path | Positive | ready | p.569; BR-569-1 p.569; p.511 | 1 | Purchase/Capture | [TD-TC-075810.json](test_data/TD-TC-075810.json) |
| TC-075811 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Mas | Happy path | Positive | needs_client_input | p.581; BR-428-8 p.428; BR-581-1 p.581 | 1 | Purchase/Capture | [TD-TC-075811.json](test_data/TD-TC-075811.json) |
| TC-075812 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Pay | Happy path | Positive | needs_client_input | p.582; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075812.json](test_data/TD-TC-075812.json) |
| TC-075813 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Fra | Happy path | Positive | needs_client_input | p.582; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075813.json](test_data/TD-TC-075813.json) |
| TC-075814 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Fin | Happy path | Positive | needs_client_input | p.583; BR-583-1 p.583; BR-583-2 p.583 | 1 | Purchase/Capture | [TD-TC-075814.json](test_data/TD-TC-075814.json) |
| TC-075815 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Cou | Happy path | Positive | needs_client_input | p.583; BR-583-3 p.583; p.511 | 1 | Purchase/Capture | [TD-TC-075815.json](test_data/TD-TC-075815.json) |
| TC-075816 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Car | Happy path | Positive | needs_client_input | p.583; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075816.json](test_data/TD-TC-075816.json) |
| TC-075817 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Pre | Happy path | Positive | needs_client_input | p.585; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075817.json](test_data/TD-TC-075817.json) |
| TC-075818 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Sof | Happy path | Positive | needs_client_input | p.586; BR-590-4 p.590; p.511 | 1 | Purchase/Capture | [TD-TC-075818.json](test_data/TD-TC-075818.json) |
| TC-075819 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Mas | Happy path | Positive | ready | p.586; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075819.json](test_data/TD-TC-075819.json) |
| TC-075820 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Add | Happy path | Positive | needs_client_input | p.600; BR-600-2 p.600; p.511 | 1 | Purchase/Capture | [TD-TC-075820.json](test_data/TD-TC-075820.json) |
| TC-075821 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Add | Happy path | Positive | needs_client_input | p.600; BR-600-2 p.600; BR-600-3 p.600 | 1 | Purchase/Capture | [TD-TC-075821.json](test_data/TD-TC-075821.json) |
| TC-075822 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Add | Happy path | Positive | needs_client_input | p.601; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075822.json](test_data/TD-TC-075822.json) |
| TC-075823 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Rem | Happy path | Positive | needs_client_input | p.602; BR-602-1 p.602; p.511 | 1 | Purchase/Capture | [TD-TC-075823.json](test_data/TD-TC-075823.json) |
| TC-075824 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Exp | Happy path | Positive | needs_client_input | p.602; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075824.json](test_data/TD-TC-075824.json) |
| TC-075825 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Sof | Happy path | Positive | needs_client_input | p.613; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075825.json](test_data/TD-TC-075825.json) |
| TC-075826 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Rea | Happy path | Positive | needs_client_input | p.613; BR-614-4 p.614; BR-614-8 p.614 | 1 | Purchase/Capture | [TD-TC-075826.json](test_data/TD-TC-075826.json) |
| TC-075827 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Vis | Happy path | Positive | needs_client_input | p.615; BR-637-2 p.637; BR-FISERV-EMV-GUIDE-38-6 p.38 | 1 | Purchase/Capture | [TD-TC-075827.json](test_data/TD-TC-075827.json) |
| TC-075828 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Ser | Happy path | Positive | needs_client_input | p.620; BR-620-2 p.620; p.511 | 1 | Purchase/Capture | [TD-TC-075828.json](test_data/TD-TC-075828.json) |
| TC-075829 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Mer | Happy path | Positive | needs_client_input | p.622; BR-622-5 p.622; BR-623-1 p.623 | 1 | Purchase/Capture | [TD-TC-075829.json](test_data/TD-TC-075829.json) |
| TC-075830 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Dig | Happy path | Positive | needs_client_input | p.623; BR-623-5 p.623; p.511 | 1 | Purchase/Capture | [TD-TC-075830.json](test_data/TD-TC-075830.json) |
| TC-075831 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Ant | Happy path | Positive | needs_client_input | p.625; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075831.json](test_data/TD-TC-075831.json) |
| TC-075832 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Ant | Happy path | Positive | needs_client_input | p.626; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075832.json](test_data/TD-TC-075832.json) |
| TC-075833 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Fut | Happy path | Positive | needs_client_input | p.628; BR-628-2 p.628; p.511 | 1 | Purchase/Capture | [TD-TC-075833.json](test_data/TD-TC-075833.json) |
| TC-075834 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Dis | Happy path | Positive | needs_client_input | p.628; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075834.json](test_data/TD-TC-075834.json) |
| TC-075835 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Dis | Happy path | Positive | needs_client_input | p.629; BR-629-1 p.629; p.511 | 1 | Purchase/Capture | [TD-TC-075835.json](test_data/TD-TC-075835.json) |
| TC-075836 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Sta | Happy path | Positive | needs_client_input | p.636; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075836.json](test_data/TD-TC-075836.json) |
| TC-075837 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the  | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-71-7 p.71; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075837.json](test_data/TD-TC-075837.json) |
| TC-075838 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the  | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-72-10 p.72; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075838.json](test_data/TD-TC-075838.json) |
| TC-075839 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the  | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-71-5 p.71; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075839.json](test_data/TD-TC-075839.json) |
| TC-075840 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the  | Alternate path | Positive | requires_review | BR-189-3 p.189; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075840.json](test_data/TD-TC-075840.json) |
| TC-075841 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the  | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-71-4 p.71; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075841.json](test_data/TD-TC-075841.json) |
| TC-075842 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the  | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-115-20 p.115; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075842.json](test_data/TD-TC-075842.json) |
| TC-075843 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the  | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-42-2 p.42; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075843.json](test_data/TD-TC-075843.json) |
| TC-075844 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the  | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-142-3 p.142; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075844.json](test_data/TD-TC-075844.json) |
| TC-075845 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the  | Alternate path | Positive | requires_review | BR-577-1 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075845.json](test_data/TD-TC-075845.json) |
| TC-075846 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the  | Alternate path | Positive | requires_review | BR-577-2 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075846.json](test_data/TD-TC-075846.json) |
| TC-075847 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the  | Alternate path | Positive | requires_review | BR-597-1 p.597; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075847.json](test_data/TD-TC-075847.json) |
| TC-075848 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the  | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-69-3 p.69; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075848.json](test_data/TD-TC-075848.json) |
| TC-075849 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture via the  | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-37-10 p.37; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075849.json](test_data/TD-TC-075849.json) |
| TC-075850 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Pu | Single rule broken | Negative | requires_review | p.546; p.546; p.402 | 1 | Purchase/Capture | [TD-TC-075850.step1.purchase.json](test_data/TD-TC-075850.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-075850.step2.reversal.json](test_data/TD-TC-075850.step2.reversal.json) |
| TC-075851 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Pu | Single rule broken | Negative | requires_review | p.546; p.546; p.40 | 1 | Purchase/Capture | [TD-TC-075851.step1.purchase.json](test_data/TD-TC-075851.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-075851.step2.reversal.json](test_data/TD-TC-075851.step2.reversal.json) |
| TC-075852 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Ti | Single rule broken | Negative | requires_review | p.546; p.39; p.38 | 1 | Purchase/Capture | [TD-TC-075852.step1.purchase.json](test_data/TD-TC-075852.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075852.step2.timeout-reversal.json](test_data/TD-TC-075852.step2.timeout-reversal.json) |
| TC-075853 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Pay | Single rule broken | Negative | requires_review | BR-FISERV-EMV-GUIDE-70-10 p.70; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075853.json](test_data/TD-TC-075853.json) |
| TC-075854 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Ser | Single rule broken | Negative | requires_review | BR-ATL105-TRANSARMOR-13-7 p.13; BR-FISERV-EMV-GUIDE-75-3 p.75; p.511 | 1 | Purchase/Capture | [TD-TC-075854.json](test_data/TD-TC-075854.json) |
| TC-075855 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Pay | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-70-10 p.70; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075855.json](test_data/TD-TC-075855.json) |
| TC-075856 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Ser | Decision table | Positive | ready | BR-ATL105-TRANSARMOR-13-7 p.13; BR-FISERV-EMV-GUIDE-75-3 p.75; p.511 | 1 | Purchase/Capture | [TD-TC-075856.json](test_data/TD-TC-075856.json) |
| TC-075857 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Tax | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-156-8 p.156; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075857.json](test_data/TD-TC-075857.json) |
| TC-075858 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Upd | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-38-2 p.38; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075858.json](test_data/TD-TC-075858.json) |
| TC-075859 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Var | Decision table | Positive | ready | BR-149-1 p.149; BR-152-1 p.152; p.511 | 1 | Purchase/Capture | [TD-TC-075859.json](test_data/TD-TC-075859.json) |
| TC-075860 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with avs | Decision table | Positive | needs_client_input | BR-315-4 p.315; BR-554-4 p.554; BR-622-1 p.622 | 1 | Purchase/Capture | [TD-TC-075860.json](test_data/TD-TC-075860.json) |
| TC-075861 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with cof | Decision table | Positive | ready | BR-53-2 p.53; BR-56-8 p.56; BR-364-3 p.364 | 1 | Purchase/Capture | [TD-TC-075861.json](test_data/TD-TC-075861.json) |
| TC-075862 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with cvm | Decision table | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-50-5 p.50; BR-FISERV-EMV-GUIDE-87-2 p.87; BR-FISERV-EMV-GUIDE-152-5 p.152 | 1 | Purchase/Capture | [TD-TC-075862.json](test_data/TD-TC-075862.json) |
| TC-075863 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with opt | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-53-3 p.53; BR-FISERV-EMV-GUIDE-53-4 p.53; BR-FISERV-EMV-GUIDE-43-4 p.43 | 1 | Purchase/Capture | [TD-TC-075863.json](test_data/TD-TC-075863.json) |
| TC-075864 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with opt | Decision table | Positive | ready | BR-156-7 p.156; BR-688-2 p.688; p.511 | 1 | Purchase/Capture | [TD-TC-075864.json](test_data/TD-TC-075864.json) |
| TC-075865 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with opt | Decision table | Positive | ready | BR-313-3 p.313; BR-ATL105-TRANSARMOR-47-5 p.47; BR-312-1 p.312 | 1 | Purchase/Capture | [TD-TC-075865.json](test_data/TD-TC-075865.json) |
| TC-075866 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Pro | Decision table | Positive | ready | BR-89-3 p.89; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075866.json](test_data/TD-TC-075866.json) |
| TC-075867 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Var | Decision table | Positive | ready | BR-ATL105-TRANSARMOR-28-4 p.28; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075867.json](test_data/TD-TC-075867.json) |
| TC-075868 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour check | Soft constraint | Positive | requires_review | BR-FISERV-EMV-GUIDE-110-1 p.110; BR-FISERV-EMV-GUIDE-111-1 p.111; BR-FISERV-EMV-GUIDE-111-2 p.111 | 1 |  | - |
| TC-075869 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 2 terminal behaviour checks | Soft constraint | Positive | requires_review | BR-FISERV-EMV-GUIDE-115-27 p.115; BR-FISERV-EMV-GUIDE-115-6 p.115; p.511 | 1 |  | - |
| TC-075870 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 2 terminal behaviour checks | Soft constraint | Positive | requires_review | BR-313-1 p.313; BR-313-2 p.313; p.511 | 1 |  | - |
| TC-075871 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 2 terminal behaviour checks | Soft constraint | Positive | requires_review | BR-FISERV-EMV-GUIDE-59-3 p.59; BR-FISERV-EMV-GUIDE-59-4 p.59; p.511 | 1 |  | - |
| TC-075872 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 2 terminal behaviour checks | Soft constraint | Positive | requires_review | BR-639-6 p.639; BR-639-7 p.639; p.511 | 1 |  | - |
| TC-075873 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: EMV Chip  | Soft constraint | Positive | requires_review | BR-FISERV-EMV-GUIDE-120-11 p.120; p.511; p.83 | 1 |  | - |
| TC-075874 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: EMV Reque | Soft constraint | Positive | requires_review | BR-FISERV-EMV-GUIDE-195-2 p.195; p.511; p.83 | 1 |  | - |
| TC-075875 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Load Type | Soft constraint | Positive | requires_review | BR-49-4 p.49; p.511; p.83 | 1 |  | - |
| TC-075876 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Pause Ind | Soft constraint | Positive | requires_review | BR-330-4 p.330; p.511; p.83 | 1 |  | - |
| TC-075877 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Pu | Valid flow | Positive | ready | p.546; p.546; BR-402-3 p.402 | 1 | Purchase/Capture | [TD-TC-075877.step1.purchase.json](test_data/TD-TC-075877.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-075877.step2.reversal.json](test_data/TD-TC-075877.step2.reversal.json) |
| TC-075878 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Ti | Valid flow | Positive | ready | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-075878.step1.purchase.json](test_data/TD-TC-075878.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075878.step2.timeout-reversal.json](test_data/TD-TC-075878.step2.timeout-reversal.json) |
| TC-075879 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Pu | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-075879.step1.purchase.json](test_data/TD-TC-075879.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-075879.step2.split-tender.json](test_data/TD-TC-075879.step2.split-tender.json) |
| TC-075880 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Ti | Invalid state transition | Negative | requires_review | p.546; BR-39-1 p.39; p.511 | 1 | Purchase/Capture | [TD-TC-075880.step1.purchase.json](test_data/TD-TC-075880.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075880.step2.timeout-reversal.json](test_data/TD-TC-075880.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-075880.step3.timeout-reversal.json](test_data/TD-TC-075880.step3.timeout-reversal.json) |
| TC-075881 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Time-out Reversal with no | Invalid state transition | Negative | blocked_verification | p.546; BR-39-1 p.39; p.511 | 1 | Time-out Reversal | [TD-TC-075881.json](test_data/TD-TC-075881.json) |
| TC-075882 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (no resp | Fault / recovery | Positive | ready | BR-409-4 p.409; BR-ATL105-TRANSARMOR-76-3 p.76; p.511 | 1 | Purchase/Capture | [TD-TC-075882.step1.purchase.json](test_data/TD-TC-075882.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-075882.step2.reversal.json](test_data/TD-TC-075882.step2.reversal.json) |
| TC-075883 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (no resp | Fault / recovery | Positive | ready | BR-39-1 p.39; BR-44-1 p.44; BR-43-2 p.43 | 1 | Purchase/Capture | [TD-TC-075883.step1.purchase.json](test_data/TD-TC-075883.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075883.step2.timeout-reversal.json](test_data/TD-TC-075883.step2.timeout-reversal.json) |
| TC-075884 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (no resp | Idempotency / duplicate / retry | Positive | ready | BR-47-1 p.47; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075884.step1.purchase.json](test_data/TD-TC-075884.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075884.step2.timeout-reversal.json](test_data/TD-TC-075884.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-075884.step3.timeout-reversal.json](test_data/TD-TC-075884.step3.timeout-reversal.json) |
|  |  |  |  |  | 4 | Time-out Reversal | [TD-TC-075884.step4.timeout-reversal.json](test_data/TD-TC-075884.step4.timeout-reversal.json) |
| TC-075885 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Pu | Time-based | Positive | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075885.step1.purchase.json](test_data/TD-TC-075885.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-075885.step2.reversal.json](test_data/TD-TC-075885.step2.reversal.json) |
| TC-075886 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Pu | Time-based | Negative | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075886.step1.purchase.json](test_data/TD-TC-075886.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-075886.step2.reversal.json](test_data/TD-TC-075886.step2.reversal.json) |
| TC-075887 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Ti | Time-based | Positive | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075887.step1.purchase.json](test_data/TD-TC-075887.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075887.step2.timeout-reversal.json](test_data/TD-TC-075887.step2.timeout-reversal.json) |
| TC-075888 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Ti | Time-based | Negative | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075888.step1.purchase.json](test_data/TD-TC-075888.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075888.step2.timeout-reversal.json](test_data/TD-TC-075888.step2.timeout-reversal.json) |
| TC-075889 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Pu | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Purchase/Capture | [TD-TC-075889.step1.purchase.json](test_data/TD-TC-075889.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-075889.step2.reversal.json](test_data/TD-TC-075889.step2.reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-075889.step3.totals.json](test_data/TD-TC-075889.step3.totals.json) |
| TC-075890 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Ti | Running total / state | Positive | requires_review | p.546; p.39; BR-181-1 p.181 | 1 | Purchase/Capture | [TD-TC-075890.step1.purchase.json](test_data/TD-TC-075890.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075890.step2.timeout-reversal.json](test_data/TD-TC-075890.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-075890.step3.totals.json](test_data/TD-TC-075890.step3.totals.json) |
| TC-075891 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then To | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Purchase/Capture | [TD-TC-075891.step1.purchase.json](test_data/TD-TC-075891.step1.purchase.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-075891.step2.totals.json](test_data/TD-TC-075891.step2.totals.json) |
| TC-075892 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for P | Running total / state | Positive | requires_review | BR-199-2 p.199; p.511; p.83 | 1 |  | - |
| TC-075893 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for ' | Running total / state | Positive | requires_review | BR-FISERV-EMV-GUIDE-130-1 p.130; p.511; p.83 | 1 |  | - |
| TC-075894 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: running total / state for ' | Running total / state | Positive | requires_review | BR-FISERV-EMV-GUIDE-135-1 p.135; p.511; p.83 | 1 |  | - |
| TC-075895 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: ordering for Expiration Dat | Ordering | Positive | requires_review | BR-718-3 p.718; p.511; p.83 | 1 |  | - |
| TC-075896 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: ordering for Number of Segm | Ordering | Positive | requires_review | BR-ATL105-TRANSARMOR-67-4 p.67; p.511; p.83 | 1 |  | - |
| TC-075897 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: ordering for Summary of TOR | Ordering | Positive | requires_review | BR-47-7 p.47; p.511; p.83 | 1 |  | - |
| TC-075898 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: ordering for 'For TA-VE, ED | Ordering | Positive | requires_review | BR-718-6 p.718; p.511; p.83 | 1 |  | - |
| TC-075899 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: co-requirement / mutual exc | Co-requirement / mutual exclusion | Negative | requires_review | BR-588-1 p.588; p.511; p.83 | 1 |  | - |
| TC-075900 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: co-requirement / mutual exc | Co-requirement / mutual exclusion | Negative | requires_review | BR-FISERV-EMV-GUIDE-79-4 p.79; p.511; p.83 | 1 |  | - |
| TC-075901 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: routing for Card Label | Routing | Negative | requires_review | BR-FISERV-EMV-GUIDE-46-3 p.46; p.511; p.83 | 1 |  | - |
| TC-075902 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: routing for Dial Backup and | Routing | Negative | requires_review | BR-49-5 p.49; p.511; p.83 | 1 |  | - |
| TC-075903 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: routing for Dual Credit and | Routing | Negative | requires_review | BR-FISERV-EMV-GUIDE-87-8 p.87; p.511; p.83 | 1 |  | - |
| TC-075904 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: routing for Sub Table ID "1 | Routing | Negative | requires_review | BR-577-3 p.577; p.511; p.83 | 1 |  | - |
| TC-075905 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: routing for PINless Debit I | Routing | Negative | requires_review | BR-FISERV-EMV-GUIDE-54-6 p.54; p.511; p.83 | 1 |  | - |
| TC-075906 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: routing for Encrypted PIN B | Routing | Negative | requires_review | BR-FISERV-EMV-GUIDE-74-4 p.74; p.511; p.83 | 1 |  | - |
| TC-075907 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: routing for Network Managem | Routing | Negative | requires_review | BR-FISERV-EMV-GUIDE-43-7 p.43; p.511; p.83 | 1 |  | - |
| TC-075908 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: routing for Response Code | Routing | Negative | requires_review | BR-FISERV-EMV-GUIDE-88-14 p.88; p.511; p.83 | 1 |  | - |
| TC-075909 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: routing for Standard Messag | Routing | Negative | requires_review | BR-FISERV-EMV-GUIDE-74-6 p.74; p.511; p.83 | 1 |  | - |
| TC-075910 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: routing for BUYPASS Card Ty | Routing | Negative | requires_review | BR-656-3 p.656; p.511; p.83 | 1 |  | - |
| TC-075911 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: routing for 'TransArmor Ver | Routing | Negative | requires_review | BR-ATL105-TRANSARMOR-14-2 p.14; p.511; p.83 | 1 |  | - |
| TC-075912 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: routing for 'For routing pu | Routing | Negative | requires_review | BR-FISERV-EMV-GUIDE-125-1 p.125; p.511; p.83 | 1 |  | - |
| TC-075913 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (Additio | Response / oracle check | Positive | ready | BR-650-4 p.650; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075913.json](test_data/TD-TC-075913.json) |
| TC-075914 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (Cardhol | Response / oracle check | Positive | ready | BR-FISERV-EMV-GUIDE-146-3 p.146; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075914.json](test_data/TD-TC-075914.json) |
| TC-075915 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (Network | Response / oracle check | Positive | ready | BR-339-4 p.339; BR-340-2 p.340; BR-340-3 p.340 | 1 | Purchase/Capture | [TD-TC-075915.json](test_data/TD-TC-075915.json) |
| TC-075916 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Pu | Response / oracle check | Negative | needs_client_input | p.546; p.546; BR-402-3 p.402 | 1 | Purchase/Capture | [TD-TC-075916.step1.purchase.json](test_data/TD-TC-075916.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-075916.step2.reversal.json](test_data/TD-TC-075916.step2.reversal.json) |
| TC-075917 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (Real Ti | Response / oracle check | Positive | ready | BR-317-5 p.317; BR-317-9 p.317; p.511 | 1 | Purchase/Capture | [TD-TC-075917.json](test_data/TD-TC-075917.json) |
| TC-075918 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture, then Ti | Response / oracle check | Negative | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-075918.step1.purchase.json](test_data/TD-TC-075918.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-075918.step2.timeout-reversal.json](test_data/TD-TC-075918.step2.timeout-reversal.json) |
| TC-075919 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (Transac | Response / oracle check | Positive | ready | BR-FISERV-EMV-GUIDE-138-2 p.138; BR-FISERV-EMV-GUIDE-138-4 p.138; BR-FISERV-EMV-GUIDE-141-4 p.141 | 1 | Purchase/Capture | [TD-TC-075919.json](test_data/TD-TC-075919.json) |
| TC-075920 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (Transac | Response / oracle check | Positive | ready | BR-62-6 p.62; BR-94-5 p.94; BR-607-3 p.607 | 1 | Purchase/Capture | [TD-TC-075920.json](test_data/TD-TC-075920.json) |
| TC-075921 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture (card is | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-075921.json](test_data/TD-TC-075921.json) |
| TC-075922 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending  | Response / oracle check | Positive | ready | BR-545-5 p.545; BR-664-4 p.664; BR-663-1 p.663 | 1 | Purchase/Capture | [TD-TC-075922.json](test_data/TD-TC-075922.json) |
| TC-075923 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending  | Response / oracle check | Positive | ready | BR-671-6 p.671; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075923.json](test_data/TD-TC-075923.json) |
| TC-075924 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending  | Response / oracle check | Positive | needs_client_input | BR-656-3 p.656; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075924.json](test_data/TD-TC-075924.json) |
| TC-075925 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Pro | Response / oracle check | Positive | needs_client_input | p.552; BR-552-2 p.552; BR-552-6 p.552 | 1 | Purchase/Capture | [TD-TC-075925.json](test_data/TD-TC-075925.json) |
| TC-075926 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Re- | Response / oracle check | Positive | needs_client_input | p.578; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075926.json](test_data/TD-TC-075926.json) |
| TC-075927 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with MCX | Response / oracle check | Positive | needs_client_input | p.582; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075927.json](test_data/TD-TC-075927.json) |
| TC-075928 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Mer | Response / oracle check | Positive | needs_client_input | p.602; BR-602-1 p.602; p.511 | 1 | Purchase/Capture | [TD-TC-075928.json](test_data/TD-TC-075928.json) |
| TC-075929 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture with Cus | Response / oracle check | Positive | needs_client_input | p.614; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-075929.json](test_data/TD-TC-075929.json) |
| TC-075930 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending  | Response / oracle check | Positive | needs_client_input | BR-252-3 p.252; BR-240-1 p.240; BR-266-3 p.266 | 1 | Purchase/Capture | [TD-TC-075930.json](test_data/TD-TC-075930.json) |
| TC-075931 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending  | Response / oracle check | Positive | needs_client_input | BR-669-3 p.669; BR-669-4 p.669; p.511 | 1 | Purchase/Capture | [TD-TC-075931.json](test_data/TD-TC-075931.json) |
| TC-075932 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture without  | Response / oracle check | Negative | ready | p.663; BR-545-5 p.545; BR-592-2 p.592 | 1 | Purchase/Capture | [TD-TC-075932.json](test_data/TD-TC-075932.json) |
| TC-075933 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture without  | Response / oracle check | Negative | ready | p.671; BR-671-6 p.671; p.511 | 1 | Purchase/Capture | [TD-TC-075933.json](test_data/TD-TC-075933.json) |
| TC-075934 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending  | Issuer outcome | Positive | needs_client_input | p.663; BR-545-5 p.545; BR-592-2 p.592 | 1 | Purchase/Capture | [TD-TC-075934.json](test_data/TD-TC-075934.json) |
| TC-075935 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending  | Issuer outcome | Positive | needs_client_input | p.663; BR-545-5 p.545; BR-592-2 p.592 | 1 | Purchase/Capture | [TD-TC-075935.json](test_data/TD-TC-075935.json) |
| TC-075936 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending  | Issuer outcome | Positive | needs_client_input | p.663; BR-545-5 p.545; BR-592-2 p.592 | 1 | Purchase/Capture | [TD-TC-075936.json](test_data/TD-TC-075936.json) |
| TC-075937 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending  | Issuer outcome | Positive | needs_client_input | p.663; BR-545-5 p.545; BR-592-2 p.592 | 1 | Purchase/Capture | [TD-TC-075937.json](test_data/TD-TC-075937.json) |
| TC-075938 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending  | Issuer outcome | Positive | needs_client_input | p.663; BR-545-5 p.545; BR-592-2 p.592 | 1 | Purchase/Capture | [TD-TC-075938.json](test_data/TD-TC-075938.json) |
| TC-075939 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending  | Issuer outcome | Positive | needs_client_input | p.663; BR-545-5 p.545; BR-592-2 p.592 | 1 | Purchase/Capture | [TD-TC-075939.json](test_data/TD-TC-075939.json) |
| TC-075940 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending  | Issuer outcome | Positive | needs_client_input | p.663; BR-545-5 p.545; BR-592-2 p.592 | 1 | Purchase/Capture | [TD-TC-075940.json](test_data/TD-TC-075940.json) |
| TC-075941 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending  | Issuer outcome | Positive | needs_client_input | p.663; BR-545-5 p.545; BR-592-2 p.592 | 1 | Purchase/Capture | [TD-TC-075941.json](test_data/TD-TC-075941.json) |
| TC-075942 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending  | Issuer outcome | Positive | needs_client_input | p.663; BR-545-5 p.545; BR-592-2 p.592 | 1 | Purchase/Capture | [TD-TC-075942.json](test_data/TD-TC-075942.json) |
| TC-075943 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending  | Issuer outcome | Positive | needs_client_input | p.663; BR-545-5 p.545; BR-592-2 p.592 | 1 | Purchase/Capture | [TD-TC-075943.json](test_data/TD-TC-075943.json) |
| TC-075944 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending  | Issuer outcome | Positive | needs_client_input | p.663; BR-545-5 p.545; BR-592-2 p.592 | 1 | Purchase/Capture | [TD-TC-075944.json](test_data/TD-TC-075944.json) |
| TC-075945 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending  | Issuer outcome | Positive | needs_client_input | p.663; BR-545-5 p.545; BR-592-2 p.592 | 1 | Purchase/Capture | [TD-TC-075945.json](test_data/TD-TC-075945.json) |
| TC-075946 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending  | Issuer outcome | Positive | needs_client_input | p.663; BR-545-5 p.545; BR-592-2 p.592 | 1 | Purchase/Capture | [TD-TC-075946.json](test_data/TD-TC-075946.json) |
| TC-075947 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending  | Issuer outcome | Positive | needs_client_input | p.663; BR-545-5 p.545; BR-592-2 p.592 | 1 | Purchase/Capture | [TD-TC-075947.json](test_data/TD-TC-075947.json) |
| TC-075948 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending  | Issuer outcome | Positive | needs_client_input | p.663; BR-545-5 p.545; BR-592-2 p.592 | 1 | Purchase/Capture | [TD-TC-075948.json](test_data/TD-TC-075948.json) |
| TC-075949 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending  | Issuer outcome | Positive | needs_client_input | p.663; BR-545-5 p.545; BR-592-2 p.592 | 1 | Purchase/Capture | [TD-TC-075949.json](test_data/TD-TC-075949.json) |
| TC-075950 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture sending  | Issuer outcome | Positive | needs_client_input | p.671; BR-671-6 p.671; p.511 | 1 | Purchase/Capture | [TD-TC-075950.json](test_data/TD-TC-075950.json) |
| TC-075951 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: CAPK lifecycle within 6 mon | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-96-1 p.96; p.511; p.83 | 1 |  | - |
| TC-075952 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: CAPK lifecycle within 6 mon | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-95-2 p.95; p.511; p.83 | 1 |  | - |
| TC-075953 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour check | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-100-6 p.100; BR-FISERV-EMV-GUIDE-100-7 p.100; BR-FISERV-EMV-GUIDE-100-8 p.100 | 1 |  | - |
| TC-075954 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour check | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-102-1 p.102; BR-FISERV-EMV-GUIDE-102-2 p.102; BR-FISERV-EMV-GUIDE-102-4 p.102 | 1 |  | - |
| TC-075955 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour check | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-161-10 p.161; BR-FISERV-EMV-GUIDE-161-11 p.161; BR-FISERV-EMV-GUIDE-161-12 p.161 | 1 |  | - |
| TC-075956 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour check | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-161-9 p.161; BR-FISERV-EMV-GUIDE-162-2 p.162; BR-FISERV-EMV-GUIDE-163-11 p.163 | 1 |  | - |
| TC-075957 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour check | Device checkpoint | Positive | requires_review | BR-BUYPASS-DCT-18-2 p.18; BR-BUYPASS-DCT-18-4 p.18; BR-BUYPASS-DCT-21-2 p.21 | 1 |  | - |
| TC-075958 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour check | Device checkpoint | Positive | requires_review | BR-343-1 p.343; BR-343-2 p.343; BR-343-3 p.343 | 1 |  | - |
| TC-075959 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour check | Device checkpoint | Positive | requires_review | BR-364-4 p.364; BR-370-6 p.370; BR-374-3 p.374 | 1 |  | - |
| TC-075960 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour check | Device checkpoint | Positive | requires_review | BR-400-4 p.400; BR-400-5 p.400; BR-401-1 p.401 | 1 |  | - |
| TC-075961 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour check | Device checkpoint | Positive | requires_review | BR-416-2 p.416; BR-416-3 p.416; BR-416-4 p.416 | 1 |  | - |
| TC-075962 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour check | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-42-3 p.42; BR-FISERV-EMV-GUIDE-42-4 p.42; BR-FISERV-EMV-GUIDE-42-6 p.42 | 1 |  | - |
| TC-075963 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour check | Device checkpoint | Positive | requires_review | BR-470-1 p.470; BR-470-4 p.470; BR-471-1 p.471 | 1 |  | - |
| TC-075964 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour check | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-63-6 p.63; BR-FISERV-EMV-GUIDE-67-1 p.67; BR-FISERV-EMV-GUIDE-67-5 p.67 | 1 |  | - |
| TC-075965 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour check | Device checkpoint | Positive | requires_review | BR-81-10 p.81; BR-81-11 p.81; BR-81-12 p.81 | 1 |  | - |
| TC-075966 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour check | Device checkpoint | Positive | requires_review | BR-91-5 p.91; BR-91-6 p.91; BR-91-9 p.91 | 1 |  | - |
| TC-075967 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 12 terminal behaviour check | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-98-1 p.98; BR-FISERV-EMV-GUIDE-98-2 p.98; BR-FISERV-EMV-GUIDE-98-3 p.98 | 1 |  | - |
| TC-075968 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 2 terminal behaviour checks | Device checkpoint | Positive | requires_review | BR-138-1 p.138; BR-138-10 p.138; p.511 | 1 |  | - |
| TC-075969 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 2 terminal behaviour checks | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-141-3 p.141; BR-FISERV-EMV-GUIDE-142-2 p.142; p.511 | 1 |  | - |
| TC-075970 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 2 terminal behaviour checks | Device checkpoint | Positive | requires_review | BR-160-10 p.160; BR-160-9 p.160; p.511 | 1 |  | - |
| TC-075971 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 2 terminal behaviour checks | Device checkpoint | Positive | requires_review | BR-171-1 p.171; BR-172-2 p.172; p.511 | 1 |  | - |
| TC-075972 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 2 terminal behaviour checks | Device checkpoint | Positive | requires_review | BR-189-2 p.189; BR-200-1 p.200; p.511 | 1 |  | - |
| TC-075973 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 2 terminal behaviour checks | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-27-1 p.27; BR-ATL105-TRANSARMOR-27-2 p.27; p.511 | 1 |  | - |
| TC-075974 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 2 terminal behaviour checks | Device checkpoint | Positive | requires_review | BR-547-1 p.547; BR-548-1 p.548; p.511 | 1 |  | - |
| TC-075975 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 2 terminal behaviour checks | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-81-2 p.81; BR-FISERV-EMV-GUIDE-84-6 p.84; p.511 | 1 |  | - |
| TC-075976 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 3 terminal behaviour checks | Device checkpoint | Positive | requires_review | BR-34-1 p.34; BR-34-2 p.34; BR-34-4 p.34 | 1 |  | - |
| TC-075977 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 3 terminal behaviour checks | Device checkpoint | Positive | requires_review | BR-44-2 p.44; BR-45-7 p.45; BR-47-8 p.47 | 1 |  | - |
| TC-075978 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 3 terminal behaviour checks | Device checkpoint | Positive | requires_review | BR-66-1 p.66; BR-66-4 p.66; BR-66-5 p.66 | 1 |  | - |
| TC-075979 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 3 terminal behaviour checks | Device checkpoint | Positive | requires_review | BR-BUYPASS-DCT-8-3 p.8; BR-BUYPASS-DCT-11-1 p.11; BR-BUYPASS-DCT-11-2 p.11 | 1 |  | - |
| TC-075980 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 4 terminal behaviour checks | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-107-5 p.107; BR-FISERV-EMV-GUIDE-107-7 p.107; BR-FISERV-EMV-GUIDE-108-1 p.108 | 1 |  | - |
| TC-075981 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 4 terminal behaviour checks | Device checkpoint | Positive | requires_review | BR-266-1 p.266; BR-266-2 p.266; BR-266-3 p.266 | 1 |  | - |
| TC-075982 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 4 terminal behaviour checks | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-37-2 p.37; BR-FISERV-EMV-GUIDE-37-8 p.37; BR-FISERV-EMV-GUIDE-37-9 p.37 | 1 |  | - |
| TC-075983 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 4 terminal behaviour checks | Device checkpoint | Positive | requires_review | BR-476-2 p.476; BR-476-3 p.476; BR-481-3 p.481 | 1 |  | - |
| TC-075984 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 5 terminal behaviour checks | Device checkpoint | Positive | requires_review | BR-252-1 p.252; BR-252-2 p.252; BR-252-3 p.252 | 1 |  | - |
| TC-075985 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 5 terminal behaviour checks | Device checkpoint | Positive | requires_review | BR-71-1 p.71; BR-72-3 p.72; BR-76-1 p.76 | 1 |  | - |
| TC-075986 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 5 terminal behaviour checks | Device checkpoint | Positive | requires_review | BR-93-2 p.93; BR-93-3 p.93; BR-93-4 p.93 | 1 |  | - |
| TC-075987 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 6 terminal behaviour checks | Device checkpoint | Positive | requires_review | BR-518-5 p.518; BR-518-6 p.518; BR-519-1 p.519 | 1 |  | - |
| TC-075988 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 7 terminal behaviour checks | Device checkpoint | Positive | requires_review | BR-254-6 p.254; BR-257-3 p.257; BR-258-1 p.258 | 1 |  | - |
| TC-075989 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 7 terminal behaviour checks | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-57-4 p.57; BR-ATL105-TRANSARMOR-62-1 p.62; BR-ATL105-TRANSARMOR-62-2 p.62 | 1 |  | - |
| TC-075990 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: 9 terminal behaviour checks | Device checkpoint | Positive | requires_review | BR-BUYPASS-DCT-39-3 p.39; BR-BUYPASS-DCT-39-4 p.39; BR-BUYPASS-DCT-39-5 p.39 | 1 |  | - |
| TC-075991 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: CA Public | Device checkpoint | Positive | requires_review | BR-217-3 p.217; p.511; p.83 | 1 |  | - |
| TC-075992 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Card Tabl | Device checkpoint | Positive | requires_review | BR-BUYPASS-DCT-44-8 p.44; p.511; p.83 | 1 |  | - |
| TC-075993 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Card Type | Device checkpoint | Positive | requires_review | BR-545-6 p.545; p.511; p.83 | 1 |  | - |
| TC-075994 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Host Prom | Device checkpoint | Positive | requires_review | BR-658-1 p.658; p.511; p.83 | 1 |  | - |
| TC-075995 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Encrypted | Device checkpoint | Positive | requires_review | BR-48-3 p.48; p.511; p.83 | 1 |  | - |
| TC-075996 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Enhanced  | Device checkpoint | Positive | requires_review | BR-701-3 p.701; p.511; p.83 | 1 |  | - |
| TC-075997 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Key Data | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-32-2 p.32; p.511; p.83 | 1 |  | - |
| TC-075998 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Key ID | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-20-2 p.20; p.511; p.83 | 1 |  | - |
| TC-075999 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Receipt T | Device checkpoint | Positive | requires_review | p.511; p.83; p.81 | 1 |  | - |
| TC-076000 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Terminal  | Device checkpoint | Positive | requires_review | BR-210-3 p.210; p.511; p.83 | 1 |  | - |
| TC-076001 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Terminal  | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-23-3 p.23; p.511; p.83 | 1 |  | - |
| TC-076002 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Terminal  | Device checkpoint | Positive | requires_review | BR-59-2 p.59; p.511; p.83 | 1 |  | - |
| TC-076003 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Terminal  | Device checkpoint | Positive | requires_review | BR-188-3 p.188; p.511; p.83 | 1 |  | - |
| TC-076004 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Token PAN | Device checkpoint | Positive | requires_review | BR-157-5 p.157; p.511; p.83 | 1 |  | - |
| TC-076005 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: Token PAN | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-12-2 p.12; p.511; p.83 | 1 |  | - |
| TC-076006 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device behaviour: 'Device m | Device checkpoint | Positive | requires_review | BR-BUYPASS-MTAP-44-2 p.44; p.511; p.83 | 1 |  | - |
| TC-076007 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device checkpoint for Sub T | Device checkpoint | Positive | requires_review | BR-577-5 p.577; p.511; p.83 | 1 |  | - |
| TC-076008 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: device checkpoint for Token | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-46-6 p.46; p.511; p.83 | 1 |  | - |
| TC-076009 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: terminal / device certifica | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-24-2 p.24; BR-FISERV-EMV-GUIDE-26-1 p.26; BR-FISERV-EMV-GUIDE-26-2 p.26 | 1 |  | - |
| TC-076010 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: terminal / device certifica | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-33-5 p.33; BR-FISERV-EMV-GUIDE-33-7 p.33; BR-FISERV-EMV-GUIDE-34-1 p.34 | 1 |  | - |
| TC-076011 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: terminal / device certifica | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-36-7 p.36; p.511; p.83 | 1 |  | - |
| TC-076012 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: CA Public Key File Data Seg | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-33-2 p.33; p.511; p.83 | 1 |  | - |
| TC-076013 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: Card Discretionary Block Da | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-19-1 p.19; p.511; p.83 | 1 |  | - |
| TC-076014 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: Card Discretionary Block Da | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-26-1 p.26; p.511; p.83 | 1 |  | - |
| TC-076015 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: Card Type | Device checkpoint | Positive | requires_review | BR-BUYPASS-IIN-4-3 p.4; p.511; p.83 | 1 |  | - |
| TC-076016 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: Key ID | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-40-5 p.40; p.511; p.83 | 1 |  | - |
| TC-076017 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: Merchant Trace ID | Device checkpoint | Positive | requires_review | BR-503-1 p.503; p.511; p.83 | 1 |  | - |
| TC-076018 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: Real Time Account Updater R | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-35-9 p.35; p.511; p.83 | 1 |  | - |
| TC-076019 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Device check: Token Requestor ID | Device checkpoint | Positive | requires_review | BR-ATL105-TRANSARMOR-16-6 p.16; p.511; p.83 | 1 |  | - |
| TC-076020 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Mail/Phone Reversal (not  | Single rule broken | Negative | requires_review | p.161; p.511; p.83 | 1 | Mail/Phone Reversal | [TD-TC-076020.json](test_data/TD-TC-076020.json) |
| TC-076021 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Account Verification (not | Single rule broken | Negative | requires_review | p.83; p.511; p.83 | 1 | Account Verification | [TD-TC-076021.json](test_data/TD-TC-076021.json) |
| TC-077209 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture Cryptogr | Single rule broken | Negative | ready | BR-FISERV-EMV-GUIDE-59-1 p.59; BR-FISERV-EMV-GUIDE-81-4 p.81; p. | 1 |  | [TD-TC-077209.json](test_data/TD-TC-077209.json) |
| TC-077222 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Magnetic Stripe Purchase/Capture EMV Requ | Single rule broken | Negative | requires_review | BR-FISERV-EMV-GUIDE-142-3 p.142; BR-209-3 p.209; BR-FISERV-EMV-GUIDE-55-1 p.55 | 1 |  | [TD-TC-077222.json](test_data/TD-TC-077222.json) |

### SC-ATL105-D3C1F6EE - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ General Retail · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-1B7DB9D5 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Hospitality · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076886 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Magnetic Stripe Purchase/Capture → | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-076886.json](test_data/TD-TC-076886.json) |
| TC-076887 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Magnetic Stripe Purchase/Capture ( | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-076887.json](test_data/TD-TC-076887.json) |
| TC-076888 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Device check: device behaviour: 'A | Device checkpoint | Positive | requires_review | BR-64-6 p.64; p.511; p.83 | 1 |  | - |

### SC-ATL105-B45D131F - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Hospitality · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-E3520A16 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077147 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Magnetic Stripe Purchase/Capture → ap | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-077147.json](test_data/TD-TC-077147.json) |
| TC-077148 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Magnetic Stripe Purchase/Capture with | Happy path | Positive | needs_client_input | p.583; BR-583-1 p.583; BR-583-2 p.583 | 1 | Purchase/Capture | [TD-TC-077148.json](test_data/TD-TC-077148.json) |
| TC-077149 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Magnetic Stripe Purchase/Capture, the | Valid flow | Positive | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-077149.step1.purchase.json](test_data/TD-TC-077149.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-077149.step2.timeout-reversal.json](test_data/TD-TC-077149.step2.timeout-reversal.json) |
| TC-077150 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Magnetic Stripe Purchase/Capture, the | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-077150.step1.purchase.json](test_data/TD-TC-077150.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-077150.step2.split-tender.json](test_data/TD-TC-077150.step2.split-tender.json) |
| TC-077151 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 5542 CAT Magnetic Stripe Purchase/Capture, the | Invalid state transition | Negative | needs_client_input | p.546; BR-39-1 p.39; p.511 | 1 | Purchase/Capture | [TD-TC-077151.step1.purchase.json](test_data/TD-TC-077151.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-077151.step2.timeout-reversal.json](test_data/TD-TC-077151.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-077151.step3.timeout-reversal.json](test_data/TD-TC-077151.step3.timeout-reversal.json) |

### SC-ATL105-55A48834 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Merchant category 6051 (cryptocurrency purchase) · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077164 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS Magnetic Stripe Purchase/Capture → ap | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-077164.json](test_data/TD-TC-077164.json) |

### SC-ATL105-4ADF7122 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Merchant category 6211 (high risk security) · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077171 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS Magnetic Stripe Purchase/Capture → ap | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-077171.json](test_data/TD-TC-077171.json) |

### SC-ATL105-A28F1156 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076936 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture → a | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-BUYPASS-PIPS-24-7 p.24 | 1 | Purchase/Capture | [TD-TC-076936.json](test_data/TD-TC-076936.json) |
| TC-076937 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture wit | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; BR-BUYPASS-PIPS-185-5 p.185 | 1 | Purchase/Capture | [TD-TC-076937.json](test_data/TD-TC-076937.json) |
| TC-076938 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Device check: device behaviour: Cred | Soft constraint | Positive | requires_review | BR-BUYPASS-PIPS-136-1 p.136; p.511; p.83 | 1 |  | - |
| TC-076939 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Device check: device behaviour: Resp | Soft constraint | Positive | requires_review | BR-BUYPASS-PIPS-18-4 p.18; p.511; p.83 | 1 |  | - |
| TC-076940 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Device check: device behaviour: Tran | Soft constraint | Positive | requires_review | BR-FISERV-EMV-GUIDE-175-3 p.175; p.511; p.83 | 1 |  | - |
| TC-076941 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase Reversal wi | Invalid state transition | Negative | blocked_verification | p.546; BR-BUYPASS-PIPS-30-3 p.30; p.511 | 1 | Purchase Reversal | [TD-TC-076941.json](test_data/TD-TC-076941.json) |
| TC-076942 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture, th | Invalid state transition | Negative | needs_client_input | p.546; BR-BUYPASS-PIPS-30-3 p.30; p.511 | 1 | Purchase/Capture | [TD-TC-076942.step1.purchase.json](test_data/TD-TC-076942.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076942.step2.reversal.json](test_data/TD-TC-076942.step2.reversal.json) |
|  |  |  |  |  | 3 | Purchase Reversal | [TD-TC-076942.step3.reversal.json](test_data/TD-TC-076942.step3.reversal.json) |
| TC-076943 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture res | Idempotency / duplicate / retry | Positive | requires_review | BR-BUYPASS-PIPS-17-2 p.17; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076943.json](test_data/TD-TC-076943.json) |
| TC-076944 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture res | Idempotency / duplicate / retry | Negative | requires_review | BR-BUYPASS-PIPS-17-2 p.17; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076944.json](test_data/TD-TC-076944.json) |
| TC-076945 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture hel | Time-based | Positive | requires_review | BR-BUYPASS-PIPS-21-2 p.21; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076945.json](test_data/TD-TC-076945.json) |
| TC-076946 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture hel | Time-based | Negative | requires_review | BR-BUYPASS-PIPS-21-2 p.21; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076946.json](test_data/TD-TC-076946.json) |
| TC-076947 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture (Ad | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-177-2 p.177; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076947.json](test_data/TD-TC-076947.json) |
| TC-076948 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture (Tr | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-138-2 p.138; BR-FISERV-EMV-GUIDE-138-4 p.138; BR-FISERV-EMV-GUIDE-141-4 p.141 | 1 | Purchase/Capture | [TD-TC-076948.json](test_data/TD-TC-076948.json) |
| TC-076949 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Magnetic Stripe Purchase/Capture (ca | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-076949.json](test_data/TD-TC-076949.json) |
| TC-076950 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Device check: 2 terminal behaviour c | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-33-1 p.33; BR-BUYPASS-PIPS-33-4 p.33; p.511 | 1 |  | - |
| TC-076951 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Device check: 2 terminal behaviour c | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-34-2 p.34; BR-BUYPASS-PIPS-34-6 p.34; p.511 | 1 |  | - |
| TC-076952 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Device check: 4 terminal behaviour c | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-131-10 p.131; BR-BUYPASS-PIPS-131-11 p.131; BR-BUYPASS-PIPS-131-6 p.131 | 1 |  | - |
| TC-076953 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Device check: 4 terminal behaviour c | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-35-2 p.35; BR-BUYPASS-PIPS-35-3 p.35; BR-BUYPASS-PIPS-35-4 p.35 | 1 |  | - |
| TC-076954 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Device check: 6 terminal behaviour c | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-16-10 p.16; BR-BUYPASS-PIPS-16-11 p.16; BR-BUYPASS-PIPS-16-5 p.16 | 1 |  | - |
| TC-076955 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Device check: 8 terminal behaviour c | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-173-1 p.173; BR-BUYPASS-PIPS-174-1 p.174; BR-BUYPASS-PIPS-174-2 p.174 | 1 |  | - |
| TC-076956 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Device check: device behaviour: Prom | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-140-6 p.140; p.511; p.83 | 1 |  | - |
| TC-076957 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Device check: device behaviour: Rece | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-17-1 p.17; p.511; p.83 | 1 |  | - |
| TC-076958 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Device check: RID | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-178-3 p.178; p.511; p.83 | 1 |  | - |

### SC-ATL105-8E9C2873 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Petroleum · unattended CAT (fuel machine)
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077054 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture → a | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-BUYPASS-PIPS-24-7 p.24 | 1 | Purchase/Capture | [TD-TC-077054.json](test_data/TD-TC-077054.json) |
| TC-077055 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture wit | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; BR-BUYPASS-PIPS-185-5 p.185 | 1 | Purchase/Capture | [TD-TC-077055.json](test_data/TD-TC-077055.json) |
| TC-077056 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: device behaviour: Cred | Soft constraint | Positive | requires_review | BR-BUYPASS-PIPS-136-1 p.136; p.511; p.83 | 1 |  | - |
| TC-077057 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: device behaviour: Resp | Soft constraint | Positive | requires_review | BR-BUYPASS-PIPS-18-4 p.18; p.511; p.83 | 1 |  | - |
| TC-077058 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: device behaviour: Tran | Soft constraint | Positive | requires_review | BR-FISERV-EMV-GUIDE-175-3 p.175; p.511; p.83 | 1 |  | - |
| TC-077059 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture, th | Valid flow | Positive | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-077059.step1.purchase.json](test_data/TD-TC-077059.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-077059.step2.timeout-reversal.json](test_data/TD-TC-077059.step2.timeout-reversal.json) |
| TC-077060 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture, th | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-077060.step1.purchase.json](test_data/TD-TC-077060.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-077060.step2.split-tender.json](test_data/TD-TC-077060.step2.split-tender.json) |
| TC-077061 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture, th | Invalid state transition | Negative | needs_client_input | p.546; BR-39-1 p.39; p.511 | 1 | Purchase/Capture | [TD-TC-077061.step1.purchase.json](test_data/TD-TC-077061.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-077061.step2.timeout-reversal.json](test_data/TD-TC-077061.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-077061.step3.timeout-reversal.json](test_data/TD-TC-077061.step3.timeout-reversal.json) |
| TC-077062 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture res | Idempotency / duplicate / retry | Positive | requires_review | BR-BUYPASS-PIPS-17-2 p.17; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-077062.json](test_data/TD-TC-077062.json) |
| TC-077063 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture res | Idempotency / duplicate / retry | Negative | requires_review | BR-BUYPASS-PIPS-17-2 p.17; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-077063.json](test_data/TD-TC-077063.json) |
| TC-077064 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture hel | Time-based | Positive | requires_review | BR-63-2 p.63; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-077064.json](test_data/TD-TC-077064.json) |
| TC-077065 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture hel | Time-based | Negative | requires_review | BR-63-2 p.63; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-077065.json](test_data/TD-TC-077065.json) |
| TC-077066 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: running total / state  | Running total / state | Positive | requires_review | BR-63-6 p.63; p.511; p.83 | 1 |  | - |
| TC-077067 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture (Ad | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-177-2 p.177; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-077067.json](test_data/TD-TC-077067.json) |
| TC-077068 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture (Tr | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-138-2 p.138; BR-FISERV-EMV-GUIDE-138-4 p.138; BR-FISERV-EMV-GUIDE-141-4 p.141 | 1 | Purchase/Capture | [TD-TC-077068.json](test_data/TD-TC-077068.json) |
| TC-077069 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture (Tr | Response / oracle check | Positive | needs_client_input | BR-62-6 p.62; BR-94-5 p.94; BR-607-3 p.607 | 1 | Purchase/Capture | [TD-TC-077069.json](test_data/TD-TC-077069.json) |
| TC-077070 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Magnetic Stripe Purchase/Capture (ca | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-077070.json](test_data/TD-TC-077070.json) |
| TC-077071 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: 2 terminal behaviour c | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-27-11 p.27; BR-BUYPASS-PIPS-27-4 p.27; p.511 | 1 |  | - |
| TC-077072 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: 2 terminal behaviour c | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-33-1 p.33; BR-BUYPASS-PIPS-33-4 p.33; p.511 | 1 |  | - |
| TC-077073 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: 2 terminal behaviour c | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-34-2 p.34; BR-BUYPASS-PIPS-34-6 p.34; p.511 | 1 |  | - |
| TC-077074 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: 4 terminal behaviour c | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-131-10 p.131; BR-BUYPASS-PIPS-131-11 p.131; BR-BUYPASS-PIPS-131-6 p.131 | 1 |  | - |
| TC-077075 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: 4 terminal behaviour c | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-35-2 p.35; BR-BUYPASS-PIPS-35-3 p.35; BR-BUYPASS-PIPS-35-4 p.35 | 1 |  | - |
| TC-077076 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: 4 terminal behaviour c | Device checkpoint | Positive | requires_review | BR-61-5 p.61; BR-62-1 p.62; BR-62-10 p.62 | 1 |  | - |
| TC-077077 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: 6 terminal behaviour c | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-16-10 p.16; BR-BUYPASS-PIPS-16-11 p.16; BR-BUYPASS-PIPS-16-5 p.16 | 1 |  | - |
| TC-077078 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: 8 terminal behaviour c | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-173-1 p.173; BR-BUYPASS-PIPS-174-1 p.174; BR-BUYPASS-PIPS-174-2 p.174 | 1 |  | - |
| TC-077079 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: device behaviour: Prom | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-140-6 p.140; p.511; p.83 | 1 |  | - |
| TC-077080 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: device behaviour: Rece | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-17-1 p.17; p.511; p.83 | 1 |  | - |
| TC-077081 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: device behaviour: Rece | Device checkpoint | Positive | requires_review | BR-BUYPASS-PIPS-28-6 p.28; p.511; p.83 | 1 |  | - |
| TC-077082 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum CAT Device check: RID | Device checkpoint | Positive | requires_review | BR-FISERV-EMV-GUIDE-178-3 p.178; p.511; p.83 | 1 |  | - |

### SC-ATL105-29733E1E - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Supermarket · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-1927FCFC - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Supermarket · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-CEFB6DB4 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Utilities · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-F64C26C1 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Magnetic Stripe @ Utilities · unattended CAT
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-651AE253 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ General Retail · attended POS
- Status: valid; context status: general; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076120 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture → approve | Happy path | Positive | ready | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-076120.json](test_data/TD-TC-076120.json) |
| TC-076121 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with UPC  | Happy path | Positive | needs_client_input | p.552; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076121.json](test_data/TD-TC-076121.json) |
| TC-076122 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Curr | Happy path | Positive | needs_client_input | p.555; BR-572-3 p.572; p.511 | 1 | Purchase/Capture | [TD-TC-076122.json](test_data/TD-TC-076122.json) |
| TC-076123 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Bala | Happy path | Positive | needs_client_input | p.556; BR-92-11 p.92; p.511 | 1 | Purchase/Capture | [TD-TC-076123.json](test_data/TD-TC-076123.json) |
| TC-076124 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Susp | Happy path | Positive | needs_client_input | p.561; BR-85-4 p.85; BR-561-2 p.561 | 1 | Purchase/Capture | [TD-TC-076124.json](test_data/TD-TC-076124.json) |
| TC-076125 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Card | Happy path | Positive | needs_client_input | p.562; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076125.json](test_data/TD-TC-076125.json) |
| TC-076126 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Rech | Happy path | Positive | needs_client_input | p.562; BR-562-2 p.562; p.511 | 1 | Purchase/Capture | [TD-TC-076126.json](test_data/TD-TC-076126.json) |
| TC-076127 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Orig | Happy path | Positive | ready | p.564; BR-564-1 p.564; BR-FISERV-EMV-GUIDE-64-2 p.64 | 1 | Purchase/Capture | [TD-TC-076127.json](test_data/TD-TC-076127.json) |
| TC-076128 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Cust | Happy path | Positive | needs_client_input | p.566; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076128.json](test_data/TD-TC-076128.json) |
| TC-076129 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Poin | Happy path | Positive | ready | p.569; BR-569-1 p.569; p.511 | 1 | Purchase/Capture | [TD-TC-076129.json](test_data/TD-TC-076129.json) |
| TC-076130 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Mast | Happy path | Positive | needs_client_input | p.581; BR-428-8 p.428; BR-581-1 p.581 | 1 | Purchase/Capture | [TD-TC-076130.json](test_data/TD-TC-076130.json) |
| TC-076131 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Payd | Happy path | Positive | needs_client_input | p.582; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076131.json](test_data/TD-TC-076131.json) |
| TC-076132 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Frau | Happy path | Positive | needs_client_input | p.582; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076132.json](test_data/TD-TC-076132.json) |
| TC-076133 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Fina | Happy path | Positive | needs_client_input | p.583; BR-583-1 p.583; BR-583-2 p.583 | 1 | Purchase/Capture | [TD-TC-076133.json](test_data/TD-TC-076133.json) |
| TC-076134 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Coun | Happy path | Positive | needs_client_input | p.583; BR-583-3 p.583; p.511 | 1 | Purchase/Capture | [TD-TC-076134.json](test_data/TD-TC-076134.json) |
| TC-076135 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Card | Happy path | Positive | needs_client_input | p.583; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076135.json](test_data/TD-TC-076135.json) |
| TC-076136 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Prev | Happy path | Positive | needs_client_input | p.585; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076136.json](test_data/TD-TC-076136.json) |
| TC-076137 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Soft | Happy path | Positive | needs_client_input | p.586; BR-590-4 p.590; p.511 | 1 | Purchase/Capture | [TD-TC-076137.json](test_data/TD-TC-076137.json) |
| TC-076138 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Mast | Happy path | Positive | ready | p.586; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076138.json](test_data/TD-TC-076138.json) |
| TC-076139 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Addi | Happy path | Positive | needs_client_input | p.600; BR-600-2 p.600; p.511 | 1 | Purchase/Capture | [TD-TC-076139.json](test_data/TD-TC-076139.json) |
| TC-076140 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Addi | Happy path | Positive | needs_client_input | p.600; BR-600-2 p.600; BR-600-3 p.600 | 1 | Purchase/Capture | [TD-TC-076140.json](test_data/TD-TC-076140.json) |
| TC-076141 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Addi | Happy path | Positive | needs_client_input | p.601; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076141.json](test_data/TD-TC-076141.json) |
| TC-076142 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Remo | Happy path | Positive | needs_client_input | p.602; BR-602-1 p.602; p.511 | 1 | Purchase/Capture | [TD-TC-076142.json](test_data/TD-TC-076142.json) |
| TC-076143 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Expe | Happy path | Positive | needs_client_input | p.602; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076143.json](test_data/TD-TC-076143.json) |
| TC-076144 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Soft | Happy path | Positive | needs_client_input | p.613; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076144.json](test_data/TD-TC-076144.json) |
| TC-076145 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Real | Happy path | Positive | needs_client_input | p.613; BR-614-4 p.614; BR-614-8 p.614 | 1 | Purchase/Capture | [TD-TC-076145.json](test_data/TD-TC-076145.json) |
| TC-076146 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Visa | Happy path | Positive | needs_client_input | p.615; BR-637-2 p.637; BR-FISERV-EMV-GUIDE-38-6 p.38 | 1 | Purchase/Capture | [TD-TC-076146.json](test_data/TD-TC-076146.json) |
| TC-076147 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Serv | Happy path | Positive | needs_client_input | p.620; BR-620-2 p.620; p.511 | 1 | Purchase/Capture | [TD-TC-076147.json](test_data/TD-TC-076147.json) |
| TC-076148 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Merc | Happy path | Positive | needs_client_input | p.622; BR-622-5 p.622; BR-623-1 p.623 | 1 | Purchase/Capture | [TD-TC-076148.json](test_data/TD-TC-076148.json) |
| TC-076149 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Digi | Happy path | Positive | needs_client_input | p.623; BR-623-5 p.623; p.511 | 1 | Purchase/Capture | [TD-TC-076149.json](test_data/TD-TC-076149.json) |
| TC-076150 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Anti | Happy path | Positive | needs_client_input | p.625; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076150.json](test_data/TD-TC-076150.json) |
| TC-076151 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Anti | Happy path | Positive | needs_client_input | p.626; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076151.json](test_data/TD-TC-076151.json) |
| TC-076152 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Futu | Happy path | Positive | needs_client_input | p.628; BR-628-2 p.628; p.511 | 1 | Purchase/Capture | [TD-TC-076152.json](test_data/TD-TC-076152.json) |
| TC-076153 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Disc | Happy path | Positive | needs_client_input | p.628; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076153.json](test_data/TD-TC-076153.json) |
| TC-076154 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Disc | Happy path | Positive | needs_client_input | p.629; BR-629-1 p.629; p.511 | 1 | Purchase/Capture | [TD-TC-076154.json](test_data/TD-TC-076154.json) |
| TC-076155 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Stag | Happy path | Positive | needs_client_input | p.636; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076155.json](test_data/TD-TC-076155.json) |
| TC-076156 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the a | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-72-10 p.72; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076156.json](test_data/TD-TC-076156.json) |
| TC-076157 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the a | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-71-5 p.71; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076157.json](test_data/TD-TC-076157.json) |
| TC-076158 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the a | Alternate path | Positive | requires_review | BR-189-3 p.189; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076158.json](test_data/TD-TC-076158.json) |
| TC-076159 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the a | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-71-4 p.71; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076159.json](test_data/TD-TC-076159.json) |
| TC-076160 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the a | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-115-20 p.115; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076160.json](test_data/TD-TC-076160.json) |
| TC-076161 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the a | Alternate path | Positive | requires_review | BR-577-1 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076161.json](test_data/TD-TC-076161.json) |
| TC-076162 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the a | Alternate path | Positive | requires_review | BR-577-2 p.577; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076162.json](test_data/TD-TC-076162.json) |
| TC-076163 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the a | Alternate path | Positive | requires_review | BR-597-1 p.597; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076163.json](test_data/TD-TC-076163.json) |
| TC-076164 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the a | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-69-3 p.69; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076164.json](test_data/TD-TC-076164.json) |
| TC-076165 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture via the a | Alternate path | Positive | requires_review | BR-FISERV-EMV-GUIDE-37-10 p.37; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076165.json](test_data/TD-TC-076165.json) |
| TC-076166 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Pur | Single rule broken | Negative | requires_review | p.546; p.546; p.402 | 1 | Purchase/Capture | [TD-TC-076166.step1.purchase.json](test_data/TD-TC-076166.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076166.step2.reversal.json](test_data/TD-TC-076166.step2.reversal.json) |
| TC-076167 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Pur | Single rule broken | Negative | requires_review | p.546; p.546; p.40 | 1 | Purchase/Capture | [TD-TC-076167.step1.purchase.json](test_data/TD-TC-076167.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076167.step2.reversal.json](test_data/TD-TC-076167.step2.reversal.json) |
| TC-076168 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Tim | Single rule broken | Negative | requires_review | p.546; p.39; p.38 | 1 | Purchase/Capture | [TD-TC-076168.step1.purchase.json](test_data/TD-TC-076168.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076168.step2.timeout-reversal.json](test_data/TD-TC-076168.step2.timeout-reversal.json) |
| TC-076169 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Paym | Single rule broken | Negative | requires_review | BR-FISERV-EMV-GUIDE-70-10 p.70; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076169.json](test_data/TD-TC-076169.json) |
| TC-076170 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Serv | Single rule broken | Negative | requires_review | BR-ATL105-TRANSARMOR-13-7 p.13; BR-FISERV-EMV-GUIDE-75-3 p.75; p.511 | 1 | Purchase/Capture | [TD-TC-076170.json](test_data/TD-TC-076170.json) |
| TC-076171 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Paym | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-70-10 p.70; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076171.json](test_data/TD-TC-076171.json) |
| TC-076172 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Serv | Decision table | Positive | ready | BR-ATL105-TRANSARMOR-13-7 p.13; BR-FISERV-EMV-GUIDE-75-3 p.75; p.511 | 1 | Purchase/Capture | [TD-TC-076172.json](test_data/TD-TC-076172.json) |
| TC-076173 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Tax  | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-156-8 p.156; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076173.json](test_data/TD-TC-076173.json) |
| TC-076174 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Upda | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-38-2 p.38; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076174.json](test_data/TD-TC-076174.json) |
| TC-076175 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Vari | Decision table | Positive | ready | BR-149-1 p.149; BR-152-1 p.152; p.511 | 1 | Purchase/Capture | [TD-TC-076175.json](test_data/TD-TC-076175.json) |
| TC-076176 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with avs  | Decision table | Positive | needs_client_input | BR-315-4 p.315; BR-554-4 p.554; BR-622-1 p.622 | 1 | Purchase/Capture | [TD-TC-076176.json](test_data/TD-TC-076176.json) |
| TC-076177 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with cof  | Decision table | Positive | ready | BR-53-2 p.53; BR-56-8 p.56; BR-364-3 p.364 | 1 | Purchase/Capture | [TD-TC-076177.json](test_data/TD-TC-076177.json) |
| TC-076178 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with cvm  | Decision table | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-50-5 p.50; BR-FISERV-EMV-GUIDE-87-2 p.87; BR-FISERV-EMV-GUIDE-152-5 p.152 | 1 | Purchase/Capture | [TD-TC-076178.json](test_data/TD-TC-076178.json) |
| TC-076179 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with incr | Decision table | Positive | needs_client_input | BR-616-4 p.616; BR-616-6 p.616; BR-637-2 p.637 | 1 | Purchase/Capture | [TD-TC-076179.json](test_data/TD-TC-076179.json) |
| TC-076180 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with incr | Decision table | Positive | needs_client_input | BR-40-4 p.40; BR-40-13 p.40; BR-44-1 p.44 | 1 | Purchase/Capture | [TD-TC-076180.json](test_data/TD-TC-076180.json) |
| TC-076181 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with opti | Decision table | Positive | ready | BR-FISERV-EMV-GUIDE-53-3 p.53; BR-FISERV-EMV-GUIDE-53-4 p.53; BR-FISERV-EMV-GUIDE-43-4 p.43 | 1 | Purchase/Capture | [TD-TC-076181.json](test_data/TD-TC-076181.json) |
| TC-076182 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with opti | Decision table | Positive | ready | BR-156-7 p.156; BR-688-2 p.688; p.511 | 1 | Purchase/Capture | [TD-TC-076182.json](test_data/TD-TC-076182.json) |
| TC-076183 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with opti | Decision table | Positive | ready | BR-313-3 p.313; BR-ATL105-TRANSARMOR-47-5 p.47; BR-312-1 p.312 | 1 | Purchase/Capture | [TD-TC-076183.json](test_data/TD-TC-076183.json) |
| TC-076184 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Prod | Decision table | Positive | ready | BR-89-3 p.89; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076184.json](test_data/TD-TC-076184.json) |
| TC-076185 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Vari | Decision table | Positive | ready | BR-ATL105-TRANSARMOR-28-4 p.28; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076185.json](test_data/TD-TC-076185.json) |
| TC-076186 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with trac | Decision table | Positive | ready | BR-81-3 p.81; BR-94-6 p.94; BR-95-3 p.95 | 1 | Purchase/Capture | [TD-TC-076186.json](test_data/TD-TC-076186.json) |
| TC-076187 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Pur | Valid flow | Positive | ready | p.546; p.546; BR-402-3 p.402 | 1 | Purchase/Capture | [TD-TC-076187.step1.purchase.json](test_data/TD-TC-076187.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076187.step2.reversal.json](test_data/TD-TC-076187.step2.reversal.json) |
| TC-076188 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Tim | Valid flow | Positive | ready | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-076188.step1.purchase.json](test_data/TD-TC-076188.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076188.step2.timeout-reversal.json](test_data/TD-TC-076188.step2.timeout-reversal.json) |
| TC-076189 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Pur | Valid flow | Positive | needs_client_input | BR-84-3 p.84; BR-87-3 p.87; BR-368-7 p.368 | 1 | Purchase/Capture | [TD-TC-076189.step1.purchase.json](test_data/TD-TC-076189.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase/Capture | [TD-TC-076189.step2.split-tender.json](test_data/TD-TC-076189.step2.split-tender.json) |
| TC-076190 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Tim | Invalid state transition | Negative | requires_review | p.546; BR-39-1 p.39; p.511 | 1 | Purchase/Capture | [TD-TC-076190.step1.purchase.json](test_data/TD-TC-076190.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076190.step2.timeout-reversal.json](test_data/TD-TC-076190.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-076190.step3.timeout-reversal.json](test_data/TD-TC-076190.step3.timeout-reversal.json) |
| TC-076191 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Time-out Reversal with no  | Invalid state transition | Negative | blocked_verification | p.546; BR-39-1 p.39; p.511 | 1 | Time-out Reversal | [TD-TC-076191.json](test_data/TD-TC-076191.json) |
| TC-076192 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (no respo | Fault / recovery | Positive | ready | BR-409-4 p.409; BR-ATL105-TRANSARMOR-76-3 p.76; p.511 | 1 | Purchase/Capture | [TD-TC-076192.step1.purchase.json](test_data/TD-TC-076192.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076192.step2.reversal.json](test_data/TD-TC-076192.step2.reversal.json) |
| TC-076193 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (no respo | Fault / recovery | Positive | ready | BR-39-1 p.39; BR-44-1 p.44; BR-43-2 p.43 | 1 | Purchase/Capture | [TD-TC-076193.step1.purchase.json](test_data/TD-TC-076193.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076193.step2.timeout-reversal.json](test_data/TD-TC-076193.step2.timeout-reversal.json) |
| TC-076194 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (no respo | Idempotency / duplicate / retry | Positive | ready | BR-47-1 p.47; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076194.step1.purchase.json](test_data/TD-TC-076194.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076194.step2.timeout-reversal.json](test_data/TD-TC-076194.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | Time-out Reversal | [TD-TC-076194.step3.timeout-reversal.json](test_data/TD-TC-076194.step3.timeout-reversal.json) |
|  |  |  |  |  | 4 | Time-out Reversal | [TD-TC-076194.step4.timeout-reversal.json](test_data/TD-TC-076194.step4.timeout-reversal.json) |
| TC-076195 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Pur | Time-based | Positive | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076195.step1.purchase.json](test_data/TD-TC-076195.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076195.step2.reversal.json](test_data/TD-TC-076195.step2.reversal.json) |
| TC-076196 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Pur | Time-based | Negative | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076196.step1.purchase.json](test_data/TD-TC-076196.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076196.step2.reversal.json](test_data/TD-TC-076196.step2.reversal.json) |
| TC-076197 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Tim | Time-based | Positive | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076197.step1.purchase.json](test_data/TD-TC-076197.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076197.step2.timeout-reversal.json](test_data/TD-TC-076197.step2.timeout-reversal.json) |
| TC-076198 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Tim | Time-based | Negative | requires_review | BR-85-2 p.85; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076198.step1.purchase.json](test_data/TD-TC-076198.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076198.step2.timeout-reversal.json](test_data/TD-TC-076198.step2.timeout-reversal.json) |
| TC-076199 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Pur | Running total / state | Positive | requires_review | p.546; p.546; BR-181-1 p.181 | 1 | Purchase/Capture | [TD-TC-076199.step1.purchase.json](test_data/TD-TC-076199.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076199.step2.reversal.json](test_data/TD-TC-076199.step2.reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076199.step3.totals.json](test_data/TD-TC-076199.step3.totals.json) |
| TC-076200 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Tim | Running total / state | Positive | requires_review | p.546; p.39; BR-181-1 p.181 | 1 | Purchase/Capture | [TD-TC-076200.step1.purchase.json](test_data/TD-TC-076200.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076200.step2.timeout-reversal.json](test_data/TD-TC-076200.step2.timeout-reversal.json) |
|  |  |  |  |  | 3 | TOTALS | [TD-TC-076200.step3.totals.json](test_data/TD-TC-076200.step3.totals.json) |
| TC-076201 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Tot | Running total / state | Positive | requires_review | p.545; BR-181-1 p.181; BR-183-1 p.183 | 1 | Purchase/Capture | [TD-TC-076201.step1.purchase.json](test_data/TD-TC-076201.step1.purchase.json) |
|  |  |  |  |  | 2 | TOTALS | [TD-TC-076201.step2.totals.json](test_data/TD-TC-076201.step2.totals.json) |
| TC-076202 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (Addition | Response / oracle check | Positive | ready | BR-650-4 p.650; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076202.json](test_data/TD-TC-076202.json) |
| TC-076203 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (Cardhold | Response / oracle check | Positive | ready | BR-FISERV-EMV-GUIDE-146-3 p.146; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076203.json](test_data/TD-TC-076203.json) |
| TC-076204 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (Network  | Response / oracle check | Positive | ready | BR-339-4 p.339; BR-340-2 p.340; BR-340-3 p.340 | 1 | Purchase/Capture | [TD-TC-076204.json](test_data/TD-TC-076204.json) |
| TC-076205 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Pur | Response / oracle check | Negative | needs_client_input | p.546; p.546; BR-402-3 p.402 | 1 | Purchase/Capture | [TD-TC-076205.step1.purchase.json](test_data/TD-TC-076205.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076205.step2.reversal.json](test_data/TD-TC-076205.step2.reversal.json) |
| TC-076206 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (Real Tim | Response / oracle check | Positive | ready | BR-317-5 p.317; BR-317-9 p.317; p.511 | 1 | Purchase/Capture | [TD-TC-076206.json](test_data/TD-TC-076206.json) |
| TC-076207 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture, then Tim | Response / oracle check | Negative | needs_client_input | p.546; p.39; BR-84-4 p.84 | 1 | Purchase/Capture | [TD-TC-076207.step1.purchase.json](test_data/TD-TC-076207.step1.purchase.json) |
|  |  |  |  |  | 2 | Time-out Reversal | [TD-TC-076207.step2.timeout-reversal.json](test_data/TD-TC-076207.step2.timeout-reversal.json) |
| TC-076208 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (Transact | Response / oracle check | Positive | ready | BR-FISERV-EMV-GUIDE-138-2 p.138; BR-FISERV-EMV-GUIDE-138-4 p.138; BR-FISERV-EMV-GUIDE-141-4 p.141 | 1 | Purchase/Capture | [TD-TC-076208.json](test_data/TD-TC-076208.json) |
| TC-076209 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (Transact | Response / oracle check | Positive | ready | BR-62-6 p.62; BR-94-5 p.94; BR-607-3 p.607 | 1 | Purchase/Capture | [TD-TC-076209.json](test_data/TD-TC-076209.json) |
| TC-076210 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture (card iss | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-076210.json](test_data/TD-TC-076210.json) |
| TC-076211 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture sending M | Response / oracle check | Positive | ready | BR-545-5 p.545; BR-664-4 p.664; BR-663-1 p.663 | 1 | Purchase/Capture | [TD-TC-076211.json](test_data/TD-TC-076211.json) |
| TC-076212 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture sending M | Response / oracle check | Positive | ready | BR-671-6 p.671; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076212.json](test_data/TD-TC-076212.json) |
| TC-076213 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture sending P | Response / oracle check | Positive | needs_client_input | BR-656-3 p.656; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076213.json](test_data/TD-TC-076213.json) |
| TC-076214 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Prog | Response / oracle check | Positive | needs_client_input | p.552; BR-552-2 p.552; BR-552-6 p.552 | 1 | Purchase/Capture | [TD-TC-076214.json](test_data/TD-TC-076214.json) |
| TC-076215 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Re-P | Response / oracle check | Positive | needs_client_input | p.578; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076215.json](test_data/TD-TC-076215.json) |
| TC-076216 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with MCX  | Response / oracle check | Positive | needs_client_input | p.582; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076216.json](test_data/TD-TC-076216.json) |
| TC-076217 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Merc | Response / oracle check | Positive | needs_client_input | p.602; BR-602-1 p.602; p.511 | 1 | Purchase/Capture | [TD-TC-076217.json](test_data/TD-TC-076217.json) |
| TC-076218 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture with Cust | Response / oracle check | Positive | needs_client_input | p.614; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076218.json](test_data/TD-TC-076218.json) |
| TC-076219 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture sending a | Response / oracle check | Positive | needs_client_input | BR-252-3 p.252; BR-240-1 p.240; BR-266-3 p.266 | 1 | Purchase/Capture | [TD-TC-076219.json](test_data/TD-TC-076219.json) |
| TC-076220 BUYPASS ATL105 MasterCard Credit MERCHANT 01 POS Manual / Keyed Purchase/Capture sending i | Response / oracle check | Positive | needs_client_input | BR-669-3 p.669; BR-669-4 p.669; p.511 | 1 | Purchase/Capture | [TD-TC-076220.json](test_data/TD-TC-076220.json) |

### SC-ATL105-7B7648CE - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Hospitality · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076891 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Manual / Keyed Purchase/Capture →  | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-076891.json](test_data/TD-TC-076891.json) |
| TC-076892 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Manual / Keyed Purchase/Capture wi | Decision table | Positive | needs_client_input | BR-616-4 p.616; BR-616-6 p.616; BR-637-2 p.637 | 1 | Purchase/Capture | [TD-TC-076892.json](test_data/TD-TC-076892.json) |
| TC-076893 BUYPASS ATL105 MasterCard Credit SAMPLE Hospitality POS Manual / Keyed Purchase/Capture (c | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-076893.json](test_data/TD-TC-076893.json) |

### SC-ATL105-7CC97653 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Merchant category 6051 (cryptocurrency purchase) · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077166 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6051 POS Manual / Keyed Purchase/Capture → app | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-077166.json](test_data/TD-TC-077166.json) |

### SC-ATL105-6A7283D0 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Merchant category 6211 (high risk security) · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077173 BUYPASS ATL105 MasterCard Credit SAMPLE MCC 6211 POS Manual / Keyed Purchase/Capture → app | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-FISERV-EMV-GUIDE-120-3 p.120 | 1 | Purchase/Capture | [TD-TC-077173.json](test_data/TD-TC-077173.json) |

### SC-ATL105-A5A02764 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Petroleum · attended POS
- Status: valid; context status: active; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-076970 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture → ap | Happy path | Positive | needs_client_input | p.545; BR-506-4 p.506; BR-BUYPASS-PIPS-24-7 p.24 | 1 | Purchase/Capture | [TD-TC-076970.json](test_data/TD-TC-076970.json) |
| TC-076971 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture with | Happy path | Positive | needs_client_input | p.569; BR-569-1 p.569; BR-BUYPASS-PIPS-185-5 p.185 | 1 | Purchase/Capture | [TD-TC-076971.json](test_data/TD-TC-076971.json) |
| TC-076972 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase Reversal wit | Invalid state transition | Negative | blocked_verification | p.546; BR-BUYPASS-PIPS-30-3 p.30; p.511 | 1 | Purchase Reversal | [TD-TC-076972.json](test_data/TD-TC-076972.json) |
| TC-076973 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture, the | Invalid state transition | Negative | needs_client_input | p.546; BR-BUYPASS-PIPS-30-3 p.30; p.511 | 1 | Purchase/Capture | [TD-TC-076973.step1.purchase.json](test_data/TD-TC-076973.step1.purchase.json) |
|  |  |  |  |  | 2 | Purchase Reversal | [TD-TC-076973.step2.reversal.json](test_data/TD-TC-076973.step2.reversal.json) |
|  |  |  |  |  | 3 | Purchase Reversal | [TD-TC-076973.step3.reversal.json](test_data/TD-TC-076973.step3.reversal.json) |
| TC-076974 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture rese | Idempotency / duplicate / retry | Positive | requires_review | BR-BUYPASS-PIPS-17-2 p.17; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076974.json](test_data/TD-TC-076974.json) |
| TC-076975 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture rese | Idempotency / duplicate / retry | Negative | requires_review | BR-BUYPASS-PIPS-17-2 p.17; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076975.json](test_data/TD-TC-076975.json) |
| TC-076976 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture held | Time-based | Positive | requires_review | BR-BUYPASS-PIPS-21-2 p.21; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076976.json](test_data/TD-TC-076976.json) |
| TC-076977 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture held | Time-based | Negative | requires_review | BR-BUYPASS-PIPS-21-2 p.21; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076977.json](test_data/TD-TC-076977.json) |
| TC-076978 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture (Add | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-177-2 p.177; p.511; p.83 | 1 | Purchase/Capture | [TD-TC-076978.json](test_data/TD-TC-076978.json) |
| TC-076979 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture (Tra | Response / oracle check | Positive | needs_client_input | BR-FISERV-EMV-GUIDE-138-2 p.138; BR-FISERV-EMV-GUIDE-138-4 p.138; BR-FISERV-EMV-GUIDE-141-4 p.141 | 1 | Purchase/Capture | [TD-TC-076979.json](test_data/TD-TC-076979.json) |
| TC-076980 BUYPASS ATL105 MasterCard Credit SAMPLE Petroleum POS Manual / Keyed Purchase/Capture (car | Response / oracle check | Negative | needs_client_input | p.545; BR-83-2 p.83; BR-84-5 p.84 | 1 | Purchase/Capture | [TD-TC-076980.json](test_data/TD-TC-076980.json) |

### SC-ATL105-1D49A967 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Supermarket · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

### SC-ATL105-D10A1552 - BUYPASS ATL105 MasterCard Credit Purchase/Capture – Manual / Keyed @ Utilities · attended POS
- Status: valid; context status: candidate; supporting rules: BR-104-2, BR-110-2, BR-110-3, BR-110-5, BR-110-6, BR-110-7, BR-110-8, BR-115-3
- **No test cases - awaiting SME** (context pending confirmation)

## BUYPASS ATL105 Data Element Validation (`BR-169EDE9D`)

Supporting citations: p.164; p.235

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation CA Public Key File Load Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-078214 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS CA Public Key File Load Request – M | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078214.json](data_element_validation/test_data/TD-TC-078214.json) |
| TC-078215 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS CA Public Key File Load Request – M | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078215.json](data_element_validation/test_data/TD-TC-078215.json) |
| TC-078216 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS CA Public Key File Load Request – M | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078216.json](data_element_validation/test_data/TD-TC-078216.json) |
| TC-078217 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS CA Public Key File Load Request – M | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078217.json](data_element_validation/test_data/TD-TC-078217.json) |
| TC-078218 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS CA Public Key File Load Request – M | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078218.json](data_element_validation/test_data/TD-TC-078218.json) |
| TC-078219 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS CA Public Key File Load Request – M | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078219.json](data_element_validation/test_data/TD-TC-078219.json) |
| TC-078220 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS CA Public Key File Load Request – M | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078220.json](data_element_validation/test_data/TD-TC-078220.json) |
| TC-078221 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS CA Public Key File Load Request – M | Data element & segment validation | Positive | requires_review | p. | 1 |  | [TD-TC-078221.json](data_element_validation/test_data/TD-TC-078221.json) |
| TC-078222 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS CA Public Key File Load Request – M | Data element & segment validation | Positive | requires_review | p. | 1 |  | [TD-TC-078222.json](data_element_validation/test_data/TD-TC-078222.json) |
| TC-078223 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS CA Public Key File Load Request – M | Data element & segment validation | Negative | ready | BR-190-4 p.190; BR-193-1 p.193; BR-193-4 p.193 | 1 |  | [TD-TC-078223.json](data_element_validation/test_data/TD-TC-078223.json) |
| TC-078224 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS CA Public Key File Load Request – M | Data element & segment validation | Negative | ready | BR-190-4 p.190; BR-193-1 p.193 | 1 |  | [TD-TC-078224.json](data_element_validation/test_data/TD-TC-078224.json) |
| TC-078225 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS CA Public Key File Load Request – M | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078225.json](data_element_validation/test_data/TD-TC-078225.json) |
| TC-078226 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS CA Public Key File Load Request – M | Data element & segment validation | Negative | ready | BR-190-4 p.190; BR-193-1 p.193 | 1 |  | [TD-TC-078226.json](data_element_validation/test_data/TD-TC-078226.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Communications Test Request – Message fields @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077974 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Communications Test Request – Messa | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077974.json](data_element_validation/test_data/TD-TC-077974.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Communications Test Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-078227 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Communications Test Request – Messa | Data element & segment validation | Positive | requires_review | p. | 1 |  | [TD-TC-078227.json](data_element_validation/test_data/TD-TC-078227.json) |
| TC-078228 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Communications Test Request – Messa | Data element & segment validation | Positive | requires_review | p. | 1 |  | [TD-TC-078228.json](data_element_validation/test_data/TD-TC-078228.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Date and Time Load Request – Message fields @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077975 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Date and Time Load Request – Messag | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077975.json](data_element_validation/test_data/TD-TC-077975.json) |
| TC-077976 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Date and Time Load Request – Messag | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077976.json](data_element_validation/test_data/TD-TC-077976.json) |
| TC-077977 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Date and Time Load Request – Messag | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077977.json](data_element_validation/test_data/TD-TC-077977.json) |
| TC-077978 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Date and Time Load Request – Messag | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077978.json](data_element_validation/test_data/TD-TC-077978.json) |
| TC-077979 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Date and Time Load Request – Messag | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077979.json](data_element_validation/test_data/TD-TC-077979.json) |
| TC-077980 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Date and Time Load Request – Messag | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077980.json](data_element_validation/test_data/TD-TC-077980.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Date and Time Load Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-078238 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Date and Time Load Request – Messag | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078238.json](data_element_validation/test_data/TD-TC-078238.json) |
| TC-078239 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Date and Time Load Request – Messag | Data element & segment validation | Positive | requires_review | p. | 1 |  | [TD-TC-078239.json](data_element_validation/test_data/TD-TC-078239.json) |
| TC-078240 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Date and Time Load Request – Messag | Data element & segment validation | Positive | requires_review | p. | 1 |  | [TD-TC-078240.json](data_element_validation/test_data/TD-TC-078240.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation EMV Financial Transaction Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-078100 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078100.json](data_element_validation/test_data/TD-TC-078100.json) |
| TC-078101 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078101.json](data_element_validation/test_data/TD-TC-078101.json) |
| TC-078102 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-078102.json](data_element_validation/test_data/TD-TC-078102.json) |
| TC-078103 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078103.json](data_element_validation/test_data/TD-TC-078103.json) |
| TC-078104 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078104.json](data_element_validation/test_data/TD-TC-078104.json) |
| TC-078105 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078105.json](data_element_validation/test_data/TD-TC-078105.json) |
| TC-078106 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078106.json](data_element_validation/test_data/TD-TC-078106.json) |
| TC-078107 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078107.json](data_element_validation/test_data/TD-TC-078107.json) |
| TC-078108 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078108.json](data_element_validation/test_data/TD-TC-078108.json) |
| TC-078109 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078109.json](data_element_validation/test_data/TD-TC-078109.json) |
| TC-078110 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078110.json](data_element_validation/test_data/TD-TC-078110.json) |
| TC-078111 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Positive | ready | p. | 1 |  | [TD-TC-078111.json](data_element_validation/test_data/TD-TC-078111.json) |
| TC-078112 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-078112.json](data_element_validation/test_data/TD-TC-078112.json) |
| TC-078113 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Positive | ready | BR-89-3 p.89; BR-102-5 p.102 | 1 |  | [TD-TC-078113.json](data_element_validation/test_data/TD-TC-078113.json) |
| TC-078114 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Positive | ready | BR-168-4 p.168; BR-166-3 p.166; BR-204-4 p.204 | 1 |  | [TD-TC-078114.json](data_element_validation/test_data/TD-TC-078114.json) |
| TC-078115 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Positive | ready | BR-575-1 p.575; BR-575-4 p.575; BR-575-5 p.575 | 1 |  | [TD-TC-078115.json](data_element_validation/test_data/TD-TC-078115.json) |
| TC-078116 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Positive | ready | BR-251-2 p.251; BR-174-1 p.174; BR-251-3 p.251 | 1 |  | [TD-TC-078116.json](data_element_validation/test_data/TD-TC-078116.json) |
| TC-078117 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Positive | ready | BR-310-3 p.310; BR-310-4 p.310; BR-207-1 p.207 | 1 |  | [TD-TC-078117.json](data_element_validation/test_data/TD-TC-078117.json) |
| TC-078118 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | blocked_no_test_data | BR-62-7 p.62; BR-112-2 p.112; BR-545-1 p.545 | 1 |  | - |
| TC-078119 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | BR-62-7 p.62; BR-112-2 p.112 | 1 |  | [TD-TC-078119.json](data_element_validation/test_data/TD-TC-078119.json) |
| TC-078120 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p.[205, 219] | 1 |  | [TD-TC-078120.json](data_element_validation/test_data/TD-TC-078120.json) |
| TC-078121 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | BR-62-7 p.62; BR-112-2 p.112 | 1 |  | [TD-TC-078121.json](data_element_validation/test_data/TD-TC-078121.json) |
| TC-078122 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | BR-251-2 p.251 | 1 |  | [TD-TC-078122.json](data_element_validation/test_data/TD-TC-078122.json) |
| TC-078123 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078123.json](data_element_validation/test_data/TD-TC-078123.json) |
| TC-078124 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078124.json](data_element_validation/test_data/TD-TC-078124.json) |
| TC-078125 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078125.json](data_element_validation/test_data/TD-TC-078125.json) |
| TC-078126 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078126.json](data_element_validation/test_data/TD-TC-078126.json) |
| TC-078127 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078127.json](data_element_validation/test_data/TD-TC-078127.json) |
| TC-078128 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078128.json](data_element_validation/test_data/TD-TC-078128.json) |
| TC-078129 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-078129.json](data_element_validation/test_data/TD-TC-078129.json) |
| TC-078130 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-078130.json](data_element_validation/test_data/TD-TC-078130.json) |
| TC-078131 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-078131.json](data_element_validation/test_data/TD-TC-078131.json) |
| TC-078132 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078132.json](data_element_validation/test_data/TD-TC-078132.json) |
| TC-078133 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078133.json](data_element_validation/test_data/TD-TC-078133.json) |
| TC-078134 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078134.json](data_element_validation/test_data/TD-TC-078134.json) |
| TC-078135 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078135.json](data_element_validation/test_data/TD-TC-078135.json) |
| TC-078136 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078136.json](data_element_validation/test_data/TD-TC-078136.json) |
| TC-078137 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078137.json](data_element_validation/test_data/TD-TC-078137.json) |
| TC-078138 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078138.json](data_element_validation/test_data/TD-TC-078138.json) |
| TC-078139 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078139.json](data_element_validation/test_data/TD-TC-078139.json) |
| TC-078140 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078140.json](data_element_validation/test_data/TD-TC-078140.json) |
| TC-078141 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078141.json](data_element_validation/test_data/TD-TC-078141.json) |
| TC-078142 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078142.json](data_element_validation/test_data/TD-TC-078142.json) |
| TC-078143 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078143.json](data_element_validation/test_data/TD-TC-078143.json) |
| TC-078144 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078144.json](data_element_validation/test_data/TD-TC-078144.json) |
| TC-078145 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078145.json](data_element_validation/test_data/TD-TC-078145.json) |
| TC-078146 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078146.json](data_element_validation/test_data/TD-TC-078146.json) |
| TC-078147 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078147.json](data_element_validation/test_data/TD-TC-078147.json) |
| TC-078148 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078148.json](data_element_validation/test_data/TD-TC-078148.json) |
| TC-078149 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078149.json](data_element_validation/test_data/TD-TC-078149.json) |
| TC-078150 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078150.json](data_element_validation/test_data/TD-TC-078150.json) |
| TC-078151 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078151.json](data_element_validation/test_data/TD-TC-078151.json) |
| TC-078152 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078152.json](data_element_validation/test_data/TD-TC-078152.json) |
| TC-078153 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078153.json](data_element_validation/test_data/TD-TC-078153.json) |
| TC-078154 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078154.json](data_element_validation/test_data/TD-TC-078154.json) |
| TC-078155 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS EMV Financial Transaction Request – | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078155.json](data_element_validation/test_data/TD-TC-078155.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Electronic Mail Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-078162 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Electronic Mail Request – Message s | Data element & segment validation | Positive | ready | p. | 1 |  | [TD-TC-078162.json](data_element_validation/test_data/TD-TC-078162.json) |
| TC-078163 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Electronic Mail Request – Message s | Data element & segment validation | Positive | ready | p. | 1 |  | [TD-TC-078163.json](data_element_validation/test_data/TD-TC-078163.json) |
| TC-078164 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Electronic Mail Request – Message s | Data element & segment validation | Negative | ready | BR-185-2 p.185; BR-240-3 p.240; BR-395-5 p.395 | 1 |  | [TD-TC-078164.json](data_element_validation/test_data/TD-TC-078164.json) |
| TC-078165 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Electronic Mail Request – Message s | Data element & segment validation | Negative | ready | BR-185-2 p.185; BR-240-3 p.240 | 1 |  | [TD-TC-078165.json](data_element_validation/test_data/TD-TC-078165.json) |
| TC-078166 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Electronic Mail Request – Message s | Data element & segment validation | Negative | ready | p.[185] | 1 |  | [TD-TC-078166.json](data_element_validation/test_data/TD-TC-078166.json) |
| TC-078167 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Electronic Mail Request – Message s | Data element & segment validation | Negative | ready | BR-185-2 p.185; BR-240-3 p.240 | 1 |  | [TD-TC-078167.json](data_element_validation/test_data/TD-TC-078167.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Financial Transaction Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-078049 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078049.json](data_element_validation/test_data/TD-TC-078049.json) |
| TC-078050 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078050.json](data_element_validation/test_data/TD-TC-078050.json) |
| TC-078051 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-078051.json](data_element_validation/test_data/TD-TC-078051.json) |
| TC-078052 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078052.json](data_element_validation/test_data/TD-TC-078052.json) |
| TC-078053 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078053.json](data_element_validation/test_data/TD-TC-078053.json) |
| TC-078054 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078054.json](data_element_validation/test_data/TD-TC-078054.json) |
| TC-078055 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Positive | ready | p. | 1 |  | [TD-TC-078055.json](data_element_validation/test_data/TD-TC-078055.json) |
| TC-078056 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Positive | needs_client_input | p. | 1 |  | [TD-TC-078056.json](data_element_validation/test_data/TD-TC-078056.json) |
| TC-078057 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Positive | ready | BR-89-3 p.89; BR-102-5 p.102 | 1 |  | [TD-TC-078057.json](data_element_validation/test_data/TD-TC-078057.json) |
| TC-078058 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Positive | ready | BR-168-4 p.168; BR-166-3 p.166; BR-204-4 p.204 | 1 |  | [TD-TC-078058.json](data_element_validation/test_data/TD-TC-078058.json) |
| TC-078059 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Positive | ready | BR-575-1 p.575; BR-575-4 p.575; BR-575-5 p.575 | 1 |  | [TD-TC-078059.json](data_element_validation/test_data/TD-TC-078059.json) |
| TC-078060 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Positive | ready | BR-251-2 p.251; BR-174-1 p.174; BR-251-3 p.251 | 1 |  | [TD-TC-078060.json](data_element_validation/test_data/TD-TC-078060.json) |
| TC-078061 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Positive | pending_sme | BR-157-1 p.157; BR-168-6 p.168; BR-206-6 p.206 | 1 |  | [TD-TC-078061.json](data_element_validation/test_data/TD-TC-078061.json) |
| TC-078062 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Positive | ready | BR-310-3 p.310; BR-310-4 p.310; BR-207-1 p.207 | 1 |  | [TD-TC-078062.json](data_element_validation/test_data/TD-TC-078062.json) |
| TC-078063 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | blocked_no_test_data | BR-62-7 p.62; BR-112-2 p.112; BR-545-1 p.545 | 1 |  | - |
| TC-078064 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | pending_sme | BR-157-1 p.157; BR-168-6 p.168; BR-206-6 p.206 | 1 |  | [TD-TC-078064.json](data_element_validation/test_data/TD-TC-078064.json) |
| TC-078065 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | BR-206-7 p.206 | 1 |  | [TD-TC-078065.json](data_element_validation/test_data/TD-TC-078065.json) |
| TC-078066 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | BR-62-7 p.62; BR-112-2 p.112 | 1 |  | [TD-TC-078066.json](data_element_validation/test_data/TD-TC-078066.json) |
| TC-078067 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p.[167, 219] | 1 |  | [TD-TC-078067.json](data_element_validation/test_data/TD-TC-078067.json) |
| TC-078068 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | BR-62-7 p.62; BR-112-2 p.112 | 1 |  | [TD-TC-078068.json](data_element_validation/test_data/TD-TC-078068.json) |
| TC-078069 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | BR-251-2 p.251 | 1 |  | [TD-TC-078069.json](data_element_validation/test_data/TD-TC-078069.json) |
| TC-078070 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078070.json](data_element_validation/test_data/TD-TC-078070.json) |
| TC-078071 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078071.json](data_element_validation/test_data/TD-TC-078071.json) |
| TC-078072 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078072.json](data_element_validation/test_data/TD-TC-078072.json) |
| TC-078073 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078073.json](data_element_validation/test_data/TD-TC-078073.json) |
| TC-078074 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078074.json](data_element_validation/test_data/TD-TC-078074.json) |
| TC-078075 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078075.json](data_element_validation/test_data/TD-TC-078075.json) |
| TC-078076 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078076.json](data_element_validation/test_data/TD-TC-078076.json) |
| TC-078077 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078077.json](data_element_validation/test_data/TD-TC-078077.json) |
| TC-078078 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078078.json](data_element_validation/test_data/TD-TC-078078.json) |
| TC-078079 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078079.json](data_element_validation/test_data/TD-TC-078079.json) |
| TC-078080 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-078080.json](data_element_validation/test_data/TD-TC-078080.json) |
| TC-078081 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-078081.json](data_element_validation/test_data/TD-TC-078081.json) |
| TC-078082 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-078082.json](data_element_validation/test_data/TD-TC-078082.json) |
| TC-078083 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-078083.json](data_element_validation/test_data/TD-TC-078083.json) |
| TC-078084 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-078084.json](data_element_validation/test_data/TD-TC-078084.json) |
| TC-078085 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078085.json](data_element_validation/test_data/TD-TC-078085.json) |
| TC-078086 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078086.json](data_element_validation/test_data/TD-TC-078086.json) |
| TC-078087 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078087.json](data_element_validation/test_data/TD-TC-078087.json) |
| TC-078088 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078088.json](data_element_validation/test_data/TD-TC-078088.json) |
| TC-078089 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078089.json](data_element_validation/test_data/TD-TC-078089.json) |
| TC-078090 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078090.json](data_element_validation/test_data/TD-TC-078090.json) |
| TC-078091 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078091.json](data_element_validation/test_data/TD-TC-078091.json) |
| TC-078092 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078092.json](data_element_validation/test_data/TD-TC-078092.json) |
| TC-078093 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078093.json](data_element_validation/test_data/TD-TC-078093.json) |
| TC-078094 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078094.json](data_element_validation/test_data/TD-TC-078094.json) |
| TC-078095 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078095.json](data_element_validation/test_data/TD-TC-078095.json) |
| TC-078096 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078096.json](data_element_validation/test_data/TD-TC-078096.json) |
| TC-078097 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078097.json](data_element_validation/test_data/TD-TC-078097.json) |
| TC-078098 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078098.json](data_element_validation/test_data/TD-TC-078098.json) |
| TC-078099 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Financial Transaction Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078099.json](data_element_validation/test_data/TD-TC-078099.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Moneris Key Load Request – Message fields @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077981 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077981.json](data_element_validation/test_data/TD-TC-077981.json) |
| TC-077982 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077982.json](data_element_validation/test_data/TD-TC-077982.json) |
| TC-077983 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077983.json](data_element_validation/test_data/TD-TC-077983.json) |
| TC-077984 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077984.json](data_element_validation/test_data/TD-TC-077984.json) |
| TC-077985 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077985.json](data_element_validation/test_data/TD-TC-077985.json) |
| TC-077986 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077986.json](data_element_validation/test_data/TD-TC-077986.json) |
| TC-077987 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077987.json](data_element_validation/test_data/TD-TC-077987.json) |
| TC-077988 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077988.json](data_element_validation/test_data/TD-TC-077988.json) |
| TC-077989 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077989.json](data_element_validation/test_data/TD-TC-077989.json) |
| TC-077990 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077990.json](data_element_validation/test_data/TD-TC-077990.json) |
| TC-077991 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077991.json](data_element_validation/test_data/TD-TC-077991.json) |
| TC-077992 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077992.json](data_element_validation/test_data/TD-TC-077992.json) |
| TC-077993 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077993.json](data_element_validation/test_data/TD-TC-077993.json) |
| TC-077994 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077994.json](data_element_validation/test_data/TD-TC-077994.json) |
| TC-077995 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077995.json](data_element_validation/test_data/TD-TC-077995.json) |
| TC-077996 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077996.json](data_element_validation/test_data/TD-TC-077996.json) |
| TC-077997 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077997.json](data_element_validation/test_data/TD-TC-077997.json) |
| TC-077998 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077998.json](data_element_validation/test_data/TD-TC-077998.json) |
| TC-077999 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077999.json](data_element_validation/test_data/TD-TC-077999.json) |
| TC-078000 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078000.json](data_element_validation/test_data/TD-TC-078000.json) |
| TC-078001 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078001.json](data_element_validation/test_data/TD-TC-078001.json) |
| TC-078002 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078002.json](data_element_validation/test_data/TD-TC-078002.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Moneris Key Load Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-078247 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078247.json](data_element_validation/test_data/TD-TC-078247.json) |
| TC-078248 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078248.json](data_element_validation/test_data/TD-TC-078248.json) |
| TC-078249 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078249.json](data_element_validation/test_data/TD-TC-078249.json) |
| TC-078250 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078250.json](data_element_validation/test_data/TD-TC-078250.json) |
| TC-078251 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Positive | requires_review | p. | 1 |  | [TD-TC-078251.json](data_element_validation/test_data/TD-TC-078251.json) |
| TC-078252 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Moneris Key Load Request – Message  | Data element & segment validation | Positive | requires_review | p. | 1 |  | [TD-TC-078252.json](data_element_validation/test_data/TD-TC-078252.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Phone Load Request – Message fields @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-078003 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Phone Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078003.json](data_element_validation/test_data/TD-TC-078003.json) |
| TC-078004 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Phone Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078004.json](data_element_validation/test_data/TD-TC-078004.json) |
| TC-078005 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Phone Load Request – Message fields | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-078005.json](data_element_validation/test_data/TD-TC-078005.json) |
| TC-078006 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Phone Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078006.json](data_element_validation/test_data/TD-TC-078006.json) |
| TC-078007 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Phone Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078007.json](data_element_validation/test_data/TD-TC-078007.json) |
| TC-078008 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Phone Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078008.json](data_element_validation/test_data/TD-TC-078008.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Phone Load Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-078235 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Phone Load Request – Message struct | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078235.json](data_element_validation/test_data/TD-TC-078235.json) |
| TC-078236 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Phone Load Request – Message struct | Data element & segment validation | Positive | requires_review | p. | 1 |  | [TD-TC-078236.json](data_element_validation/test_data/TD-TC-078236.json) |
| TC-078237 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Phone Load Request – Message struct | Data element & segment validation | Positive | requires_review | p. | 1 |  | [TD-TC-078237.json](data_element_validation/test_data/TD-TC-078237.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Proprietary Data Load Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-078179 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078179.json](data_element_validation/test_data/TD-TC-078179.json) |
| TC-078180 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078180.json](data_element_validation/test_data/TD-TC-078180.json) |
| TC-078181 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078181.json](data_element_validation/test_data/TD-TC-078181.json) |
| TC-078182 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078182.json](data_element_validation/test_data/TD-TC-078182.json) |
| TC-078183 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078183.json](data_element_validation/test_data/TD-TC-078183.json) |
| TC-078184 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078184.json](data_element_validation/test_data/TD-TC-078184.json) |
| TC-078185 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078185.json](data_element_validation/test_data/TD-TC-078185.json) |
| TC-078186 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078186.json](data_element_validation/test_data/TD-TC-078186.json) |
| TC-078187 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078187.json](data_element_validation/test_data/TD-TC-078187.json) |
| TC-078188 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078188.json](data_element_validation/test_data/TD-TC-078188.json) |
| TC-078189 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Positive | ready | p. | 1 |  | [TD-TC-078189.json](data_element_validation/test_data/TD-TC-078189.json) |
| TC-078190 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Positive | ready | p. | 1 |  | [TD-TC-078190.json](data_element_validation/test_data/TD-TC-078190.json) |
| TC-078191 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | ready | BR-469-3 p.469; BR-180-3 p.180; BR-179-5 p.179 | 1 |  | [TD-TC-078191.json](data_element_validation/test_data/TD-TC-078191.json) |
| TC-078192 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | ready | BR-469-3 p.469; BR-180-3 p.180 | 1 |  | [TD-TC-078192.json](data_element_validation/test_data/TD-TC-078192.json) |
| TC-078193 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | ready | p.[219] | 1 |  | [TD-TC-078193.json](data_element_validation/test_data/TD-TC-078193.json) |
| TC-078194 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Proprietary Data Load Request – Mes | Data element & segment validation | Negative | ready | BR-469-3 p.469; BR-180-3 p.180 | 1 |  | [TD-TC-078194.json](data_element_validation/test_data/TD-TC-078194.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 100 – Standard Message Data Segment @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077223 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Se | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077223.json](data_element_validation/test_data/TD-TC-077223.json) |
| TC-077224 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: In | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077224.json](data_element_validation/test_data/TD-TC-077224.json) |
| TC-077225 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: In | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077225.json](data_element_validation/test_data/TD-TC-077225.json) |
| TC-077226 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: In | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077226.json](data_element_validation/test_data/TD-TC-077226.json) |
| TC-077227 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077227.json](data_element_validation/test_data/TD-TC-077227.json) |
| TC-077228 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: In | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077228.json](data_element_validation/test_data/TD-TC-077228.json) |
| TC-077229 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Te | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077229.json](data_element_validation/test_data/TD-TC-077229.json) |
| TC-077230 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Te | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077230.json](data_element_validation/test_data/TD-TC-077230.json) |
| TC-077231 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Te | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077231.json](data_element_validation/test_data/TD-TC-077231.json) |
| TC-077232 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077232.json](data_element_validation/test_data/TD-TC-077232.json) |
| TC-077233 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Pr | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-077233.json](data_element_validation/test_data/TD-TC-077233.json) |
| TC-077234 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Pr | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-077234.json](data_element_validation/test_data/TD-TC-077234.json) |
| TC-077235 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-077235.json](data_element_validation/test_data/TD-TC-077235.json) |
| TC-077236 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ac | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077236.json](data_element_validation/test_data/TD-TC-077236.json) |
| TC-077237 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ac | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077237.json](data_element_validation/test_data/TD-TC-077237.json) |
| TC-077238 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077238.json](data_element_validation/test_data/TD-TC-077238.json) |
| TC-077239 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ca | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077239.json](data_element_validation/test_data/TD-TC-077239.json) |
| TC-077240 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ca | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077240.json](data_element_validation/test_data/TD-TC-077240.json) |
| TC-077241 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ca | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077241.json](data_element_validation/test_data/TD-TC-077241.json) |
| TC-077242 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: En | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077242.json](data_element_validation/test_data/TD-TC-077242.json) |
| TC-077243 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: En | Data element & segment validation | Boundary | needs_client_input | p. | 1 |  | [TD-TC-077243.json](data_element_validation/test_data/TD-TC-077243.json) |
| TC-077244 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: En | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077244.json](data_element_validation/test_data/TD-TC-077244.json) |
| TC-077245 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Pu | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077245.json](data_element_validation/test_data/TD-TC-077245.json) |
| TC-077246 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Pu | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077246.json](data_element_validation/test_data/TD-TC-077246.json) |
| TC-077247 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Pu | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077247.json](data_element_validation/test_data/TD-TC-077247.json) |
| TC-077248 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Pu | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077248.json](data_element_validation/test_data/TD-TC-077248.json) |
| TC-077249 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Pu | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077249.json](data_element_validation/test_data/TD-TC-077249.json) |
| TC-077250 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Pu | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077250.json](data_element_validation/test_data/TD-TC-077250.json) |
| TC-077251 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Fu | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077251.json](data_element_validation/test_data/TD-TC-077251.json) |
| TC-077252 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Fu | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077252.json](data_element_validation/test_data/TD-TC-077252.json) |
| TC-077253 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Fu | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077253.json](data_element_validation/test_data/TD-TC-077253.json) |
| TC-077254 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Fu | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077254.json](data_element_validation/test_data/TD-TC-077254.json) |
| TC-077255 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Fu | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077255.json](data_element_validation/test_data/TD-TC-077255.json) |
| TC-077256 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Fu | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077256.json](data_element_validation/test_data/TD-TC-077256.json) |
| TC-077257 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: No | Data element & segment validation | Negative | ready | p.; p.393 | 1 |  | [TD-TC-077257.json](data_element_validation/test_data/TD-TC-077257.json) |
| TC-077258 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: No | Data element & segment validation | Boundary | ready | p.; p.393 | 1 |  | [TD-TC-077258.json](data_element_validation/test_data/TD-TC-077258.json) |
| TC-077259 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: No | Data element & segment validation | Negative | ready | p.; p.393 | 1 |  | [TD-TC-077259.json](data_element_validation/test_data/TD-TC-077259.json) |
| TC-077260 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: No | Data element & segment validation | Negative | ready | p.; p.393 | 1 |  | [TD-TC-077260.json](data_element_validation/test_data/TD-TC-077260.json) |
| TC-077261 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: No | Data element & segment validation | Boundary | blocked_verification | p.; p.393 | 1 |  | [TD-TC-077261.json](data_element_validation/test_data/TD-TC-077261.json) |
| TC-077262 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ta | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077262.json](data_element_validation/test_data/TD-TC-077262.json) |
| TC-077263 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ta | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077263.json](data_element_validation/test_data/TD-TC-077263.json) |
| TC-077264 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ta | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077264.json](data_element_validation/test_data/TD-TC-077264.json) |
| TC-077265 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ta | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077265.json](data_element_validation/test_data/TD-TC-077265.json) |
| TC-077266 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ta | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077266.json](data_element_validation/test_data/TD-TC-077266.json) |
| TC-077267 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ta | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077267.json](data_element_validation/test_data/TD-TC-077267.json) |
| TC-077268 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ta | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077268.json](data_element_validation/test_data/TD-TC-077268.json) |
| TC-077269 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ca | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077269.json](data_element_validation/test_data/TD-TC-077269.json) |
| TC-077270 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ca | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077270.json](data_element_validation/test_data/TD-TC-077270.json) |
| TC-077271 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ca | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077271.json](data_element_validation/test_data/TD-TC-077271.json) |
| TC-077272 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ca | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077272.json](data_element_validation/test_data/TD-TC-077272.json) |
| TC-077273 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ca | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077273.json](data_element_validation/test_data/TD-TC-077273.json) |
| TC-077274 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ca | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077274.json](data_element_validation/test_data/TD-TC-077274.json) |
| TC-077275 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Se | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077275.json](data_element_validation/test_data/TD-TC-077275.json) |
| TC-077276 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Se | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077276.json](data_element_validation/test_data/TD-TC-077276.json) |
| TC-077277 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Se | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077277.json](data_element_validation/test_data/TD-TC-077277.json) |
| TC-077278 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077278.json](data_element_validation/test_data/TD-TC-077278.json) |
| TC-077279 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Se | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077279.json](data_element_validation/test_data/TD-TC-077279.json) |
| TC-077280 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Se | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077280.json](data_element_validation/test_data/TD-TC-077280.json) |
| TC-077281 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Se | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077281.json](data_element_validation/test_data/TD-TC-077281.json) |
| TC-077282 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Se | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077282.json](data_element_validation/test_data/TD-TC-077282.json) |
| TC-077283 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ap | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077283.json](data_element_validation/test_data/TD-TC-077283.json) |
| TC-077284 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ap | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077284.json](data_element_validation/test_data/TD-TC-077284.json) |
| TC-077285 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ap | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077285.json](data_element_validation/test_data/TD-TC-077285.json) |
| TC-077286 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ap | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077286.json](data_element_validation/test_data/TD-TC-077286.json) |
| TC-077287 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Lo | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077287.json](data_element_validation/test_data/TD-TC-077287.json) |
| TC-077288 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Lo | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077288.json](data_element_validation/test_data/TD-TC-077288.json) |
| TC-077289 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Lo | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077289.json](data_element_validation/test_data/TD-TC-077289.json) |
| TC-077290 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Pa | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077290.json](data_element_validation/test_data/TD-TC-077290.json) |
| TC-077291 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Pa | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077291.json](data_element_validation/test_data/TD-TC-077291.json) |
| TC-077292 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Pa | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077292.json](data_element_validation/test_data/TD-TC-077292.json) |
| TC-077293 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077293.json](data_element_validation/test_data/TD-TC-077293.json) |
| TC-077294 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Pa | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077294.json](data_element_validation/test_data/TD-TC-077294.json) |
| TC-077295 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Se | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-077295.json](data_element_validation/test_data/TD-TC-077295.json) |
| TC-077296 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Se | Data element & segment validation | Negative | needs_client_input | p. | 1 |  | [TD-TC-077296.json](data_element_validation/test_data/TD-TC-077296.json) |
| TC-077297 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Ca | Data element & segment validation | Negative | ready | p.372 | 1 |  | [TD-TC-077297.json](data_element_validation/test_data/TD-TC-077297.json) |
| TC-077298 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077298.json](data_element_validation/test_data/TD-TC-077298.json) |
| TC-077299 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077299.json](data_element_validation/test_data/TD-TC-077299.json) |
| TC-077300 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077300.json](data_element_validation/test_data/TD-TC-077300.json) |
| TC-077301 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077301.json](data_element_validation/test_data/TD-TC-077301.json) |
| TC-077302 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077302.json](data_element_validation/test_data/TD-TC-077302.json) |
| TC-077303 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077303.json](data_element_validation/test_data/TD-TC-077303.json) |
| TC-077304 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-077304.json](data_element_validation/test_data/TD-TC-077304.json) |
| TC-077305 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-077305.json](data_element_validation/test_data/TD-TC-077305.json) |
| TC-077306 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-077306.json](data_element_validation/test_data/TD-TC-077306.json) |
| TC-077307 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077307.json](data_element_validation/test_data/TD-TC-077307.json) |
| TC-077308 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077308.json](data_element_validation/test_data/TD-TC-077308.json) |
| TC-077309 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077309.json](data_element_validation/test_data/TD-TC-077309.json) |
| TC-077310 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077310.json](data_element_validation/test_data/TD-TC-077310.json) |
| TC-077311 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077311.json](data_element_validation/test_data/TD-TC-077311.json) |
| TC-077312 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077312.json](data_element_validation/test_data/TD-TC-077312.json) |
| TC-077313 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077313.json](data_element_validation/test_data/TD-TC-077313.json) |
| TC-077314 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077314.json](data_element_validation/test_data/TD-TC-077314.json) |
| TC-077315 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Standard Message Data Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077315.json](data_element_validation/test_data/TD-TC-077315.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 102 – Product Code Data Segment @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077316 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Segmen | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077316.json](data_element_validation/test_data/TD-TC-077316.json) |
| TC-077317 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Servic | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077317.json](data_element_validation/test_data/TD-TC-077317.json) |
| TC-077318 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Servic | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077318.json](data_element_validation/test_data/TD-TC-077318.json) |
| TC-077319 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Servic | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077319.json](data_element_validation/test_data/TD-TC-077319.json) |
| TC-077320 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077320.json](data_element_validation/test_data/TD-TC-077320.json) |
| TC-077321 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Servic | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077321.json](data_element_validation/test_data/TD-TC-077321.json) |
| TC-077322 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Number | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077322.json](data_element_validation/test_data/TD-TC-077322.json) |
| TC-077323 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Number | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077323.json](data_element_validation/test_data/TD-TC-077323.json) |
| TC-077324 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Number | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077324.json](data_element_validation/test_data/TD-TC-077324.json) |
| TC-077325 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077325.json](data_element_validation/test_data/TD-TC-077325.json) |
| TC-077326 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Number | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077326.json](data_element_validation/test_data/TD-TC-077326.json) |
| TC-077327 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Number | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077327.json](data_element_validation/test_data/TD-TC-077327.json) |
| TC-077328 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Number | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077328.json](data_element_validation/test_data/TD-TC-077328.json) |
| TC-077329 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Number | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077329.json](data_element_validation/test_data/TD-TC-077329.json) |
| TC-077330 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Number | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077330.json](data_element_validation/test_data/TD-TC-077330.json) |
| TC-077331 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Produc | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077331.json](data_element_validation/test_data/TD-TC-077331.json) |
| TC-077332 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Produc | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077332.json](data_element_validation/test_data/TD-TC-077332.json) |
| TC-077333 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Produc | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077333.json](data_element_validation/test_data/TD-TC-077333.json) |
| TC-077334 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077334.json](data_element_validation/test_data/TD-TC-077334.json) |
| TC-077335 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Produc | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077335.json](data_element_validation/test_data/TD-TC-077335.json) |
| TC-077336 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Unit o | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077336.json](data_element_validation/test_data/TD-TC-077336.json) |
| TC-077337 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Unit o | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077337.json](data_element_validation/test_data/TD-TC-077337.json) |
| TC-077338 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Unit o | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077338.json](data_element_validation/test_data/TD-TC-077338.json) |
| TC-077339 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077339.json](data_element_validation/test_data/TD-TC-077339.json) |
| TC-077340 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Unit o | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077340.json](data_element_validation/test_data/TD-TC-077340.json) |
| TC-077341 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Quanti | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077341.json](data_element_validation/test_data/TD-TC-077341.json) |
| TC-077342 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Quanti | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077342.json](data_element_validation/test_data/TD-TC-077342.json) |
| TC-077343 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Quanti | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077343.json](data_element_validation/test_data/TD-TC-077343.json) |
| TC-077344 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077344.json](data_element_validation/test_data/TD-TC-077344.json) |
| TC-077345 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Quanti | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077345.json](data_element_validation/test_data/TD-TC-077345.json) |
| TC-077346 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Unit P | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077346.json](data_element_validation/test_data/TD-TC-077346.json) |
| TC-077347 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Unit P | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077347.json](data_element_validation/test_data/TD-TC-077347.json) |
| TC-077348 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Unit P | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077348.json](data_element_validation/test_data/TD-TC-077348.json) |
| TC-077349 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077349.json](data_element_validation/test_data/TD-TC-077349.json) |
| TC-077350 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Unit P | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077350.json](data_element_validation/test_data/TD-TC-077350.json) |
| TC-077351 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Produc | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077351.json](data_element_validation/test_data/TD-TC-077351.json) |
| TC-077352 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Produc | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077352.json](data_element_validation/test_data/TD-TC-077352.json) |
| TC-077353 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Produc | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077353.json](data_element_validation/test_data/TD-TC-077353.json) |
| TC-077354 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077354.json](data_element_validation/test_data/TD-TC-077354.json) |
| TC-077355 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Produc | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077355.json](data_element_validation/test_data/TD-TC-077355.json) |
| TC-077356 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Produc | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077356.json](data_element_validation/test_data/TD-TC-077356.json) |
| TC-077357 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Produc | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077357.json](data_element_validation/test_data/TD-TC-077357.json) |
| TC-077358 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Segmen | Data element & segment validation | Negative | blocked_no_test_data | p. | 1 |  | - |
| TC-077359 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Segmen | Data element & segment validation | Negative | blocked_no_test_data | p. | 1 |  | - |
| TC-077360 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077360.json](data_element_validation/test_data/TD-TC-077360.json) |
| TC-077361 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077361.json](data_element_validation/test_data/TD-TC-077361.json) |
| TC-077362 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077362.json](data_element_validation/test_data/TD-TC-077362.json) |
| TC-077363 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077363.json](data_element_validation/test_data/TD-TC-077363.json) |
| TC-077364 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077364.json](data_element_validation/test_data/TD-TC-077364.json) |
| TC-077365 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077365.json](data_element_validation/test_data/TD-TC-077365.json) |
| TC-077366 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077366.json](data_element_validation/test_data/TD-TC-077366.json) |
| TC-077367 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077367.json](data_element_validation/test_data/TD-TC-077367.json) |
| TC-077368 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077368.json](data_element_validation/test_data/TD-TC-077368.json) |
| TC-077369 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077369.json](data_element_validation/test_data/TD-TC-077369.json) |
| TC-077370 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077370.json](data_element_validation/test_data/TD-TC-077370.json) |
| TC-077371 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077371.json](data_element_validation/test_data/TD-TC-077371.json) |
| TC-077372 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077372.json](data_element_validation/test_data/TD-TC-077372.json) |
| TC-077373 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077373.json](data_element_validation/test_data/TD-TC-077373.json) |
| TC-077374 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077374.json](data_element_validation/test_data/TD-TC-077374.json) |
| TC-077375 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077375.json](data_element_validation/test_data/TD-TC-077375.json) |
| TC-077376 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077376.json](data_element_validation/test_data/TD-TC-077376.json) |
| TC-077377 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077377.json](data_element_validation/test_data/TD-TC-077377.json) |
| TC-077378 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077378.json](data_element_validation/test_data/TD-TC-077378.json) |
| TC-077379 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077379.json](data_element_validation/test_data/TD-TC-077379.json) |
| TC-077380 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Product Code Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077380.json](data_element_validation/test_data/TD-TC-077380.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 104 – Purchase Card Data Segment @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077381 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: Segme | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077381.json](data_element_validation/test_data/TD-TC-077381.json) |
| TC-077382 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: Purch | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077382.json](data_element_validation/test_data/TD-TC-077382.json) |
| TC-077383 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: Purch | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077383.json](data_element_validation/test_data/TD-TC-077383.json) |
| TC-077384 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: Purch | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077384.json](data_element_validation/test_data/TD-TC-077384.json) |
| TC-077385 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: PC Ta | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077385.json](data_element_validation/test_data/TD-TC-077385.json) |
| TC-077386 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: PC Ta | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077386.json](data_element_validation/test_data/TD-TC-077386.json) |
| TC-077387 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: PC Ta | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077387.json](data_element_validation/test_data/TD-TC-077387.json) |
| TC-077388 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: PC Fr | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077388.json](data_element_validation/test_data/TD-TC-077388.json) |
| TC-077389 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: PC Fr | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077389.json](data_element_validation/test_data/TD-TC-077389.json) |
| TC-077390 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: PC Fr | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077390.json](data_element_validation/test_data/TD-TC-077390.json) |
| TC-077391 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: PC Du | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077391.json](data_element_validation/test_data/TD-TC-077391.json) |
| TC-077392 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: PC Du | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077392.json](data_element_validation/test_data/TD-TC-077392.json) |
| TC-077393 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: PC Du | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077393.json](data_element_validation/test_data/TD-TC-077393.json) |
| TC-077394 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: Ship- | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077394.json](data_element_validation/test_data/TD-TC-077394.json) |
| TC-077395 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: Ship- | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077395.json](data_element_validation/test_data/TD-TC-077395.json) |
| TC-077396 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: Ship- | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077396.json](data_element_validation/test_data/TD-TC-077396.json) |
| TC-077397 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: Ship- | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077397.json](data_element_validation/test_data/TD-TC-077397.json) |
| TC-077398 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: Ship- | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077398.json](data_element_validation/test_data/TD-TC-077398.json) |
| TC-077399 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: Ship- | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077399.json](data_element_validation/test_data/TD-TC-077399.json) |
| TC-077400 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: Ship- | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077400.json](data_element_validation/test_data/TD-TC-077400.json) |
| TC-077401 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: Ship- | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077401.json](data_element_validation/test_data/TD-TC-077401.json) |
| TC-077402 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: Ship- | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077402.json](data_element_validation/test_data/TD-TC-077402.json) |
| TC-077403 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: Ship- | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077403.json](data_element_validation/test_data/TD-TC-077403.json) |
| TC-077404 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: Ship- | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077404.json](data_element_validation/test_data/TD-TC-077404.json) |
| TC-077405 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: Ship- | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077405.json](data_element_validation/test_data/TD-TC-077405.json) |
| TC-077406 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: Direc | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077406.json](data_element_validation/test_data/TD-TC-077406.json) |
| TC-077407 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: Direc | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077407.json](data_element_validation/test_data/TD-TC-077407.json) |
| TC-077408 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: Direc | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077408.json](data_element_validation/test_data/TD-TC-077408.json) |
| TC-077409 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: Segme | Data element & segment validation | Negative | blocked_no_test_data | p. | 1 |  | - |
| TC-077410 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Purchase Card Data Segment: Segme | Data element & segment validation | Negative | blocked_no_test_data | p. | 1 |  | - |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 105 – Totals Data Segment @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077411 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Segment Type | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077411.json](data_element_validation/test_data/TD-TC-077411.json) |
| TC-077412 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Information  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077412.json](data_element_validation/test_data/TD-TC-077412.json) |
| TC-077413 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Information  | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077413.json](data_element_validation/test_data/TD-TC-077413.json) |
| TC-077414 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Information  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077414.json](data_element_validation/test_data/TD-TC-077414.json) |
| TC-077415 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Required fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077415.json](data_element_validation/test_data/TD-TC-077415.json) |
| TC-077416 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Information  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077416.json](data_element_validation/test_data/TD-TC-077416.json) |
| TC-077417 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Terminal Ide | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077417.json](data_element_validation/test_data/TD-TC-077417.json) |
| TC-077418 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Terminal Ide | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077418.json](data_element_validation/test_data/TD-TC-077418.json) |
| TC-077419 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Terminal Ide | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077419.json](data_element_validation/test_data/TD-TC-077419.json) |
| TC-077420 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Required fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077420.json](data_element_validation/test_data/TD-TC-077420.json) |
| TC-077421 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Prompt Code  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077421.json](data_element_validation/test_data/TD-TC-077421.json) |
| TC-077422 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Employee Num | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077422.json](data_element_validation/test_data/TD-TC-077422.json) |
| TC-077423 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Employee Num | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077423.json](data_element_validation/test_data/TD-TC-077423.json) |
| TC-077424 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Employee Num | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077424.json](data_element_validation/test_data/TD-TC-077424.json) |
| TC-077425 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Employee Num | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077425.json](data_element_validation/test_data/TD-TC-077425.json) |
| TC-077426 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Password one | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077426.json](data_element_validation/test_data/TD-TC-077426.json) |
| TC-077427 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Password exa | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077427.json](data_element_validation/test_data/TD-TC-077427.json) |
| TC-077428 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Password con | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077428.json](data_element_validation/test_data/TD-TC-077428.json) |
| TC-077429 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Totals Date  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077429.json](data_element_validation/test_data/TD-TC-077429.json) |
| TC-077430 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Totals Date  | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077430.json](data_element_validation/test_data/TD-TC-077430.json) |
| TC-077431 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Totals Date  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077431.json](data_element_validation/test_data/TD-TC-077431.json) |
| TC-077432 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Required fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077432.json](data_element_validation/test_data/TD-TC-077432.json) |
| TC-077433 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Totals Date  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077433.json](data_element_validation/test_data/TD-TC-077433.json) |
| TC-077434 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Totals Date  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077434.json](data_element_validation/test_data/TD-TC-077434.json) |
| TC-077435 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Totals Date  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077435.json](data_element_validation/test_data/TD-TC-077435.json) |
| TC-077436 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Hardware Ver | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077436.json](data_element_validation/test_data/TD-TC-077436.json) |
| TC-077437 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Hardware Ver | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077437.json](data_element_validation/test_data/TD-TC-077437.json) |
| TC-077438 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Hardware Ver | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077438.json](data_element_validation/test_data/TD-TC-077438.json) |
| TC-077439 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Required fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077439.json](data_element_validation/test_data/TD-TC-077439.json) |
| TC-077440 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Software Ver | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077440.json](data_element_validation/test_data/TD-TC-077440.json) |
| TC-077441 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Software Ver | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077441.json](data_element_validation/test_data/TD-TC-077441.json) |
| TC-077442 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Software Ver | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077442.json](data_element_validation/test_data/TD-TC-077442.json) |
| TC-077443 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Required fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077443.json](data_element_validation/test_data/TD-TC-077443.json) |
| TC-077444 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Software Ver | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077444.json](data_element_validation/test_data/TD-TC-077444.json) |
| TC-077445 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Firmware Ver | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077445.json](data_element_validation/test_data/TD-TC-077445.json) |
| TC-077446 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Firmware Ver | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077446.json](data_element_validation/test_data/TD-TC-077446.json) |
| TC-077447 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Firmware Ver | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077447.json](data_element_validation/test_data/TD-TC-077447.json) |
| TC-077448 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Required fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077448.json](data_element_validation/test_data/TD-TC-077448.json) |
| TC-077449 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Firmware Ver | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077449.json](data_element_validation/test_data/TD-TC-077449.json) |
| TC-077450 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Sequence Num | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077450.json](data_element_validation/test_data/TD-TC-077450.json) |
| TC-077451 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Sequence Num | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077451.json](data_element_validation/test_data/TD-TC-077451.json) |
| TC-077452 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Sequence Num | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077452.json](data_element_validation/test_data/TD-TC-077452.json) |
| TC-077453 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Required fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077453.json](data_element_validation/test_data/TD-TC-077453.json) |
| TC-077454 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Sequence Num | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077454.json](data_element_validation/test_data/TD-TC-077454.json) |
| TC-077455 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Sequence Num | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077455.json](data_element_validation/test_data/TD-TC-077455.json) |
| TC-077456 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Sequence Num | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077456.json](data_element_validation/test_data/TD-TC-077456.json) |
| TC-077457 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Sequence Num | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077457.json](data_element_validation/test_data/TD-TC-077457.json) |
| TC-077458 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Currency Cod | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077458.json](data_element_validation/test_data/TD-TC-077458.json) |
| TC-077459 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Currency Cod | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077459.json](data_element_validation/test_data/TD-TC-077459.json) |
| TC-077460 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Currency Cod | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077460.json](data_element_validation/test_data/TD-TC-077460.json) |
| TC-077461 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Currency Cod | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077461.json](data_element_validation/test_data/TD-TC-077461.json) |
| TC-077462 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Grand Total  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077462.json](data_element_validation/test_data/TD-TC-077462.json) |
| TC-077463 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Grand Total  | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077463.json](data_element_validation/test_data/TD-TC-077463.json) |
| TC-077464 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Grand Total  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077464.json](data_element_validation/test_data/TD-TC-077464.json) |
| TC-077465 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Required fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077465.json](data_element_validation/test_data/TD-TC-077465.json) |
| TC-077466 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Grand Total  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077466.json](data_element_validation/test_data/TD-TC-077466.json) |
| TC-077467 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Grand Total  | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077467.json](data_element_validation/test_data/TD-TC-077467.json) |
| TC-077468 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Grand Total  | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077468.json](data_element_validation/test_data/TD-TC-077468.json) |
| TC-077469 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Label o | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077469.json](data_element_validation/test_data/TD-TC-077469.json) |
| TC-077470 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Label e | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077470.json](data_element_validation/test_data/TD-TC-077470.json) |
| TC-077471 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Label c | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077471.json](data_element_validation/test_data/TD-TC-077471.json) |
| TC-077472 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Required fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077472.json](data_element_validation/test_data/TD-TC-077472.json) |
| TC-077473 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Label v | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077473.json](data_element_validation/test_data/TD-TC-077473.json) |
| TC-077474 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Label o | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077474.json](data_element_validation/test_data/TD-TC-077474.json) |
| TC-077475 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Type To | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077475.json](data_element_validation/test_data/TD-TC-077475.json) |
| TC-077476 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Type To | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077476.json](data_element_validation/test_data/TD-TC-077476.json) |
| TC-077477 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Type To | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077477.json](data_element_validation/test_data/TD-TC-077477.json) |
| TC-077478 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Required fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077478.json](data_element_validation/test_data/TD-TC-077478.json) |
| TC-077479 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Type To | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077479.json](data_element_validation/test_data/TD-TC-077479.json) |
| TC-077480 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Type To | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077480.json](data_element_validation/test_data/TD-TC-077480.json) |
| TC-077481 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Type To | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077481.json](data_element_validation/test_data/TD-TC-077481.json) |
| TC-077482 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Type To | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077482.json](data_element_validation/test_data/TD-TC-077482.json) |
| TC-077483 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Type To | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077483.json](data_element_validation/test_data/TD-TC-077483.json) |
| TC-077484 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Type To | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077484.json](data_element_validation/test_data/TD-TC-077484.json) |
| TC-077485 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Type To | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077485.json](data_element_validation/test_data/TD-TC-077485.json) |
| TC-077486 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Required fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077486.json](data_element_validation/test_data/TD-TC-077486.json) |
| TC-077487 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Type To | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077487.json](data_element_validation/test_data/TD-TC-077487.json) |
| TC-077488 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Type To | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077488.json](data_element_validation/test_data/TD-TC-077488.json) |
| TC-077489 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Card Type To | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077489.json](data_element_validation/test_data/TD-TC-077489.json) |
| TC-077490 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Segment Leng | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077490.json](data_element_validation/test_data/TD-TC-077490.json) |
| TC-077491 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals Data Segment: Segment Leng | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077491.json](data_element_validation/test_data/TD-TC-077491.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 108 – Loyalty Card Data Segment @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077492 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Segmen | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077492.json](data_element_validation/test_data/TD-TC-077492.json) |
| TC-077493 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Loyalt | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077493.json](data_element_validation/test_data/TD-TC-077493.json) |
| TC-077494 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Loyalt | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077494.json](data_element_validation/test_data/TD-TC-077494.json) |
| TC-077495 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Loyalt | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077495.json](data_element_validation/test_data/TD-TC-077495.json) |
| TC-077496 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077496.json](data_element_validation/test_data/TD-TC-077496.json) |
| TC-077497 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Loyalt | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077497.json](data_element_validation/test_data/TD-TC-077497.json) |
| TC-077498 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Loyalt | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077498.json](data_element_validation/test_data/TD-TC-077498.json) |
| TC-077499 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Loyalt | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077499.json](data_element_validation/test_data/TD-TC-077499.json) |
| TC-077500 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Points | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077500.json](data_element_validation/test_data/TD-TC-077500.json) |
| TC-077501 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Points | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077501.json](data_element_validation/test_data/TD-TC-077501.json) |
| TC-077502 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Points | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077502.json](data_element_validation/test_data/TD-TC-077502.json) |
| TC-077503 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Coupon | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077503.json](data_element_validation/test_data/TD-TC-077503.json) |
| TC-077504 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Coupon | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077504.json](data_element_validation/test_data/TD-TC-077504.json) |
| TC-077505 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Coupon | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077505.json](data_element_validation/test_data/TD-TC-077505.json) |
| TC-077506 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Coupon | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077506.json](data_element_validation/test_data/TD-TC-077506.json) |
| TC-077507 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Coupon | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077507.json](data_element_validation/test_data/TD-TC-077507.json) |
| TC-077508 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Coupon | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077508.json](data_element_validation/test_data/TD-TC-077508.json) |
| TC-077509 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Update | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077509.json](data_element_validation/test_data/TD-TC-077509.json) |
| TC-077510 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Update | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077510.json](data_element_validation/test_data/TD-TC-077510.json) |
| TC-077511 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Update | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077511.json](data_element_validation/test_data/TD-TC-077511.json) |
| TC-077512 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Update | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077512.json](data_element_validation/test_data/TD-TC-077512.json) |
| TC-077513 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Street | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077513.json](data_element_validation/test_data/TD-TC-077513.json) |
| TC-077514 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Street | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077514.json](data_element_validation/test_data/TD-TC-077514.json) |
| TC-077515 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Street | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077515.json](data_element_validation/test_data/TD-TC-077515.json) |
| TC-077516 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Phone  | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077516.json](data_element_validation/test_data/TD-TC-077516.json) |
| TC-077517 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Phone  | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077517.json](data_element_validation/test_data/TD-TC-077517.json) |
| TC-077518 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Phone  | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077518.json](data_element_validation/test_data/TD-TC-077518.json) |
| TC-077519 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Expira | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077519.json](data_element_validation/test_data/TD-TC-077519.json) |
| TC-077520 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Expira | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077520.json](data_element_validation/test_data/TD-TC-077520.json) |
| TC-077521 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Expira | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077521.json](data_element_validation/test_data/TD-TC-077521.json) |
| TC-077522 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Expira | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077522.json](data_element_validation/test_data/TD-TC-077522.json) |
| TC-077523 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Paymen | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077523.json](data_element_validation/test_data/TD-TC-077523.json) |
| TC-077524 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Paymen | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077524.json](data_element_validation/test_data/TD-TC-077524.json) |
| TC-077525 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Paymen | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077525.json](data_element_validation/test_data/TD-TC-077525.json) |
| TC-077526 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077526.json](data_element_validation/test_data/TD-TC-077526.json) |
| TC-077527 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Paymen | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077527.json](data_element_validation/test_data/TD-TC-077527.json) |
| TC-077528 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Paymen | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077528.json](data_element_validation/test_data/TD-TC-077528.json) |
| TC-077529 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Loyalt | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077529.json](data_element_validation/test_data/TD-TC-077529.json) |
| TC-077530 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Loyalt | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077530.json](data_element_validation/test_data/TD-TC-077530.json) |
| TC-077531 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Loyalt | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077531.json](data_element_validation/test_data/TD-TC-077531.json) |
| TC-077532 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Loyalt | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077532.json](data_element_validation/test_data/TD-TC-077532.json) |
| TC-077533 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Loyalt | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077533.json](data_element_validation/test_data/TD-TC-077533.json) |
| TC-077534 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Loyalt | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077534.json](data_element_validation/test_data/TD-TC-077534.json) |
| TC-077535 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Loyalt | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077535.json](data_element_validation/test_data/TD-TC-077535.json) |
| TC-077536 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Unit o | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077536.json](data_element_validation/test_data/TD-TC-077536.json) |
| TC-077537 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Unit o | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077537.json](data_element_validation/test_data/TD-TC-077537.json) |
| TC-077538 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Unit o | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077538.json](data_element_validation/test_data/TD-TC-077538.json) |
| TC-077539 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Unit o | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077539.json](data_element_validation/test_data/TD-TC-077539.json) |
| TC-077540 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Segmen | Data element & segment validation | Negative | blocked_no_test_data | p. | 1 |  | - |
| TC-077541 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Segmen | Data element & segment validation | Negative | blocked_no_test_data | p. | 1 |  | - |
| TC-077542 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077542.json](data_element_validation/test_data/TD-TC-077542.json) |
| TC-077543 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077543.json](data_element_validation/test_data/TD-TC-077543.json) |
| TC-077544 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077544.json](data_element_validation/test_data/TD-TC-077544.json) |
| TC-077545 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077545.json](data_element_validation/test_data/TD-TC-077545.json) |
| TC-077546 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077546.json](data_element_validation/test_data/TD-TC-077546.json) |
| TC-077547 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Loyalty Card Data Segment: Requir | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077547.json](data_element_validation/test_data/TD-TC-077547.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 109 – Electronic Mail Data Segment @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077548 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Seg | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077548.json](data_element_validation/test_data/TD-TC-077548.json) |
| TC-077549 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Inf | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077549.json](data_element_validation/test_data/TD-TC-077549.json) |
| TC-077550 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Inf | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077550.json](data_element_validation/test_data/TD-TC-077550.json) |
| TC-077551 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Inf | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077551.json](data_element_validation/test_data/TD-TC-077551.json) |
| TC-077552 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Req | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077552.json](data_element_validation/test_data/TD-TC-077552.json) |
| TC-077553 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Inf | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077553.json](data_element_validation/test_data/TD-TC-077553.json) |
| TC-077554 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Ter | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077554.json](data_element_validation/test_data/TD-TC-077554.json) |
| TC-077555 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Ter | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077555.json](data_element_validation/test_data/TD-TC-077555.json) |
| TC-077556 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Ter | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077556.json](data_element_validation/test_data/TD-TC-077556.json) |
| TC-077557 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Req | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077557.json](data_element_validation/test_data/TD-TC-077557.json) |
| TC-077558 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Pro | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077558.json](data_element_validation/test_data/TD-TC-077558.json) |
| TC-077559 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Pro | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077559.json](data_element_validation/test_data/TD-TC-077559.json) |
| TC-077560 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Req | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077560.json](data_element_validation/test_data/TD-TC-077560.json) |
| TC-077561 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Blo | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077561.json](data_element_validation/test_data/TD-TC-077561.json) |
| TC-077562 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Blo | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077562.json](data_element_validation/test_data/TD-TC-077562.json) |
| TC-077563 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Blo | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077563.json](data_element_validation/test_data/TD-TC-077563.json) |
| TC-077564 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Req | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077564.json](data_element_validation/test_data/TD-TC-077564.json) |
| TC-077565 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Blo | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077565.json](data_element_validation/test_data/TD-TC-077565.json) |
| TC-077566 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Blo | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077566.json](data_element_validation/test_data/TD-TC-077566.json) |
| TC-077567 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Emp | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077567.json](data_element_validation/test_data/TD-TC-077567.json) |
| TC-077568 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Emp | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077568.json](data_element_validation/test_data/TD-TC-077568.json) |
| TC-077569 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Emp | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077569.json](data_element_validation/test_data/TD-TC-077569.json) |
| TC-077570 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Emp | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077570.json](data_element_validation/test_data/TD-TC-077570.json) |
| TC-077571 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Pas | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077571.json](data_element_validation/test_data/TD-TC-077571.json) |
| TC-077572 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Pas | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077572.json](data_element_validation/test_data/TD-TC-077572.json) |
| TC-077573 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Pas | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077573.json](data_element_validation/test_data/TD-TC-077573.json) |
| TC-077574 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Seq | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077574.json](data_element_validation/test_data/TD-TC-077574.json) |
| TC-077575 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Seq | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077575.json](data_element_validation/test_data/TD-TC-077575.json) |
| TC-077576 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Seq | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077576.json](data_element_validation/test_data/TD-TC-077576.json) |
| TC-077577 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Req | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077577.json](data_element_validation/test_data/TD-TC-077577.json) |
| TC-077578 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Seq | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077578.json](data_element_validation/test_data/TD-TC-077578.json) |
| TC-077579 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Seq | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077579.json](data_element_validation/test_data/TD-TC-077579.json) |
| TC-077580 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Seq | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077580.json](data_element_validation/test_data/TD-TC-077580.json) |
| TC-077581 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Seq | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077581.json](data_element_validation/test_data/TD-TC-077581.json) |
| TC-077582 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Loc | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077582.json](data_element_validation/test_data/TD-TC-077582.json) |
| TC-077583 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Loc | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077583.json](data_element_validation/test_data/TD-TC-077583.json) |
| TC-077584 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Loc | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077584.json](data_element_validation/test_data/TD-TC-077584.json) |
| TC-077585 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Loc | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077585.json](data_element_validation/test_data/TD-TC-077585.json) |
| TC-077586 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Loc | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077586.json](data_element_validation/test_data/TD-TC-077586.json) |
| TC-077587 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Ext | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077587.json](data_element_validation/test_data/TD-TC-077587.json) |
| TC-077588 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Ext | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077588.json](data_element_validation/test_data/TD-TC-077588.json) |
| TC-077589 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Ext | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077589.json](data_element_validation/test_data/TD-TC-077589.json) |
| TC-077590 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Ext | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077590.json](data_element_validation/test_data/TD-TC-077590.json) |
| TC-077591 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Ext | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077591.json](data_element_validation/test_data/TD-TC-077591.json) |
| TC-077592 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Ext | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077592.json](data_element_validation/test_data/TD-TC-077592.json) |
| TC-077593 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Ext | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077593.json](data_element_validation/test_data/TD-TC-077593.json) |
| TC-077594 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Ext | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077594.json](data_element_validation/test_data/TD-TC-077594.json) |
| TC-077595 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Ext | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077595.json](data_element_validation/test_data/TD-TC-077595.json) |
| TC-077596 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Ext | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077596.json](data_element_validation/test_data/TD-TC-077596.json) |
| TC-077597 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Tex | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077597.json](data_element_validation/test_data/TD-TC-077597.json) |
| TC-077598 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Tex | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077598.json](data_element_validation/test_data/TD-TC-077598.json) |
| TC-077599 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Tex | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077599.json](data_element_validation/test_data/TD-TC-077599.json) |
| TC-077600 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Tex | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077600.json](data_element_validation/test_data/TD-TC-077600.json) |
| TC-077601 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Tex | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077601.json](data_element_validation/test_data/TD-TC-077601.json) |
| TC-077602 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Tex | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077602.json](data_element_validation/test_data/TD-TC-077602.json) |
| TC-077603 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Tex | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077603.json](data_element_validation/test_data/TD-TC-077603.json) |
| TC-077604 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Tex | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077604.json](data_element_validation/test_data/TD-TC-077604.json) |
| TC-077605 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Tex | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077605.json](data_element_validation/test_data/TD-TC-077605.json) |
| TC-077606 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Tex | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077606.json](data_element_validation/test_data/TD-TC-077606.json) |
| TC-077607 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Seg | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077607.json](data_element_validation/test_data/TD-TC-077607.json) |
| TC-077608 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Electronic Mail Data Segment: Seg | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077608.json](data_element_validation/test_data/TD-TC-077608.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 111 – Variable Information Data Segment @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077609 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077609.json](data_element_validation/test_data/TD-TC-077609.json) |
| TC-077610 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077610.json](data_element_validation/test_data/TD-TC-077610.json) |
| TC-077611 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077611.json](data_element_validation/test_data/TD-TC-077611.json) |
| TC-077612 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077612.json](data_element_validation/test_data/TD-TC-077612.json) |
| TC-077613 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077613.json](data_element_validation/test_data/TD-TC-077613.json) |
| TC-077614 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077614.json](data_element_validation/test_data/TD-TC-077614.json) |
| TC-077615 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077615.json](data_element_validation/test_data/TD-TC-077615.json) |
| TC-077616 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077616.json](data_element_validation/test_data/TD-TC-077616.json) |
| TC-077617 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077617.json](data_element_validation/test_data/TD-TC-077617.json) |
| TC-077618 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077618.json](data_element_validation/test_data/TD-TC-077618.json) |
| TC-077619 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077619.json](data_element_validation/test_data/TD-TC-077619.json) |
| TC-077620 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077620.json](data_element_validation/test_data/TD-TC-077620.json) |
| TC-077621 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077621.json](data_element_validation/test_data/TD-TC-077621.json) |
| TC-077622 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077622.json](data_element_validation/test_data/TD-TC-077622.json) |
| TC-077623 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077623.json](data_element_validation/test_data/TD-TC-077623.json) |
| TC-077624 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077624.json](data_element_validation/test_data/TD-TC-077624.json) |
| TC-077625 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077625.json](data_element_validation/test_data/TD-TC-077625.json) |
| TC-077626 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077626.json](data_element_validation/test_data/TD-TC-077626.json) |
| TC-077627 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | blocked_no_test_data | p. | 1 |  | - |
| TC-077628 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | requires_review | p.575; p.575 | 1 |  | [TD-TC-077628.json](data_element_validation/test_data/TD-TC-077628.json) |
| TC-077629 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | requires_review | p.589; p.589 | 1 |  | [TD-TC-077629.json](data_element_validation/test_data/TD-TC-077629.json) |
| TC-077630 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-077630.json](data_element_validation/test_data/TD-TC-077630.json) |
| TC-077631 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-077631.json](data_element_validation/test_data/TD-TC-077631.json) |
| TC-077632 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-077632.json](data_element_validation/test_data/TD-TC-077632.json) |
| TC-077633 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-077633.json](data_element_validation/test_data/TD-TC-077633.json) |
| TC-077634 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-077634.json](data_element_validation/test_data/TD-TC-077634.json) |
| TC-077635 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-077635.json](data_element_validation/test_data/TD-TC-077635.json) |
| TC-077636 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-077636.json](data_element_validation/test_data/TD-TC-077636.json) |
| TC-077637 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-077637.json](data_element_validation/test_data/TD-TC-077637.json) |
| TC-077638 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Variable Information Data Segment | Data element & segment validation | Negative | blocked_verification | p. | 1 |  | [TD-TC-077638.json](data_element_validation/test_data/TD-TC-077638.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 114 – SKU Data Segment @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077639 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – SKU Data Segment: Segment Type di | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077639.json](data_element_validation/test_data/TD-TC-077639.json) |
| TC-077640 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – SKU Data Segment: SKU Data one by | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077640.json](data_element_validation/test_data/TD-TC-077640.json) |
| TC-077641 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – SKU Data Segment: SKU Data exactl | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077641.json](data_element_validation/test_data/TD-TC-077641.json) |
| TC-077642 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – SKU Data Segment: SKU Data contai | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077642.json](data_element_validation/test_data/TD-TC-077642.json) |
| TC-077643 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – SKU Data Segment: Required field  | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077643.json](data_element_validation/test_data/TD-TC-077643.json) |
| TC-077644 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – SKU Data Segment: Segment Length  | Data element & segment validation | Negative | blocked_no_test_data | p. | 1 |  | - |
| TC-077645 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – SKU Data Segment: Segment Length  | Data element & segment validation | Negative | blocked_no_test_data | p. | 1 |  | - |
| TC-077646 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – SKU Data Segment: Required field  | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077646.json](data_element_validation/test_data/TD-TC-077646.json) |
| TC-077647 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – SKU Data Segment: Required field  | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077647.json](data_element_validation/test_data/TD-TC-077647.json) |
| TC-077648 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – SKU Data Segment: Required field  | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077648.json](data_element_validation/test_data/TD-TC-077648.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 116 – TransArmor Load Data Segment @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077649 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Seg | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077649.json](data_element_validation/test_data/TD-TC-077649.json) |
| TC-077650 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Seq | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077650.json](data_element_validation/test_data/TD-TC-077650.json) |
| TC-077651 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Seq | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077651.json](data_element_validation/test_data/TD-TC-077651.json) |
| TC-077652 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Seq | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077652.json](data_element_validation/test_data/TD-TC-077652.json) |
| TC-077653 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Req | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077653.json](data_element_validation/test_data/TD-TC-077653.json) |
| TC-077654 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Seq | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077654.json](data_element_validation/test_data/TD-TC-077654.json) |
| TC-077655 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Seq | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077655.json](data_element_validation/test_data/TD-TC-077655.json) |
| TC-077656 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Seq | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077656.json](data_element_validation/test_data/TD-TC-077656.json) |
| TC-077657 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Seq | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077657.json](data_element_validation/test_data/TD-TC-077657.json) |
| TC-077658 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Ter | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077658.json](data_element_validation/test_data/TD-TC-077658.json) |
| TC-077659 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Ter | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077659.json](data_element_validation/test_data/TD-TC-077659.json) |
| TC-077660 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Ter | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077660.json](data_element_validation/test_data/TD-TC-077660.json) |
| TC-077661 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Req | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077661.json](data_element_validation/test_data/TD-TC-077661.json) |
| TC-077662 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Loa | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077662.json](data_element_validation/test_data/TD-TC-077662.json) |
| TC-077663 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Har | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077663.json](data_element_validation/test_data/TD-TC-077663.json) |
| TC-077664 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Har | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077664.json](data_element_validation/test_data/TD-TC-077664.json) |
| TC-077665 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Har | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077665.json](data_element_validation/test_data/TD-TC-077665.json) |
| TC-077666 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Req | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077666.json](data_element_validation/test_data/TD-TC-077666.json) |
| TC-077667 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Sof | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077667.json](data_element_validation/test_data/TD-TC-077667.json) |
| TC-077668 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Sof | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077668.json](data_element_validation/test_data/TD-TC-077668.json) |
| TC-077669 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Sof | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077669.json](data_element_validation/test_data/TD-TC-077669.json) |
| TC-077670 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Req | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077670.json](data_element_validation/test_data/TD-TC-077670.json) |
| TC-077671 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Sof | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077671.json](data_element_validation/test_data/TD-TC-077671.json) |
| TC-077672 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Fir | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077672.json](data_element_validation/test_data/TD-TC-077672.json) |
| TC-077673 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Fir | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077673.json](data_element_validation/test_data/TD-TC-077673.json) |
| TC-077674 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Fir | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077674.json](data_element_validation/test_data/TD-TC-077674.json) |
| TC-077675 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Req | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077675.json](data_element_validation/test_data/TD-TC-077675.json) |
| TC-077676 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Fir | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077676.json](data_element_validation/test_data/TD-TC-077676.json) |
| TC-077677 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Key | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077677.json](data_element_validation/test_data/TD-TC-077677.json) |
| TC-077678 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Key | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077678.json](data_element_validation/test_data/TD-TC-077678.json) |
| TC-077679 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Key | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077679.json](data_element_validation/test_data/TD-TC-077679.json) |
| TC-077680 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Key | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077680.json](data_element_validation/test_data/TD-TC-077680.json) |
| TC-077681 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Seg | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077681.json](data_element_validation/test_data/TD-TC-077681.json) |
| TC-077682 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – TransArmor Load Data Segment: Seg | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077682.json](data_element_validation/test_data/TD-TC-077682.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 118 – Proprietary Data Load Segment @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077683 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Se | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077683.json](data_element_validation/test_data/TD-TC-077683.json) |
| TC-077684 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Se | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077684.json](data_element_validation/test_data/TD-TC-077684.json) |
| TC-077685 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Se | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077685.json](data_element_validation/test_data/TD-TC-077685.json) |
| TC-077686 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Se | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077686.json](data_element_validation/test_data/TD-TC-077686.json) |
| TC-077687 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077687.json](data_element_validation/test_data/TD-TC-077687.json) |
| TC-077688 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Se | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077688.json](data_element_validation/test_data/TD-TC-077688.json) |
| TC-077689 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Se | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077689.json](data_element_validation/test_data/TD-TC-077689.json) |
| TC-077690 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Se | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077690.json](data_element_validation/test_data/TD-TC-077690.json) |
| TC-077691 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Se | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077691.json](data_element_validation/test_data/TD-TC-077691.json) |
| TC-077692 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: In | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077692.json](data_element_validation/test_data/TD-TC-077692.json) |
| TC-077693 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: In | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077693.json](data_element_validation/test_data/TD-TC-077693.json) |
| TC-077694 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: In | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077694.json](data_element_validation/test_data/TD-TC-077694.json) |
| TC-077695 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077695.json](data_element_validation/test_data/TD-TC-077695.json) |
| TC-077696 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: In | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077696.json](data_element_validation/test_data/TD-TC-077696.json) |
| TC-077697 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Te | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077697.json](data_element_validation/test_data/TD-TC-077697.json) |
| TC-077698 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Te | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077698.json](data_element_validation/test_data/TD-TC-077698.json) |
| TC-077699 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Te | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077699.json](data_element_validation/test_data/TD-TC-077699.json) |
| TC-077700 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077700.json](data_element_validation/test_data/TD-TC-077700.json) |
| TC-077701 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Pr | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077701.json](data_element_validation/test_data/TD-TC-077701.json) |
| TC-077702 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Pr | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077702.json](data_element_validation/test_data/TD-TC-077702.json) |
| TC-077703 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077703.json](data_element_validation/test_data/TD-TC-077703.json) |
| TC-077704 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Pr | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077704.json](data_element_validation/test_data/TD-TC-077704.json) |
| TC-077705 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Pr | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077705.json](data_element_validation/test_data/TD-TC-077705.json) |
| TC-077706 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Pr | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077706.json](data_element_validation/test_data/TD-TC-077706.json) |
| TC-077707 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077707.json](data_element_validation/test_data/TD-TC-077707.json) |
| TC-077708 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Pr | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077708.json](data_element_validation/test_data/TD-TC-077708.json) |
| TC-077709 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Pr | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077709.json](data_element_validation/test_data/TD-TC-077709.json) |
| TC-077710 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: De | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077710.json](data_element_validation/test_data/TD-TC-077710.json) |
| TC-077711 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: De | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077711.json](data_element_validation/test_data/TD-TC-077711.json) |
| TC-077712 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: De | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077712.json](data_element_validation/test_data/TD-TC-077712.json) |
| TC-077713 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077713.json](data_element_validation/test_data/TD-TC-077713.json) |
| TC-077714 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: De | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077714.json](data_element_validation/test_data/TD-TC-077714.json) |
| TC-077715 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Ca | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077715.json](data_element_validation/test_data/TD-TC-077715.json) |
| TC-077716 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Ca | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077716.json](data_element_validation/test_data/TD-TC-077716.json) |
| TC-077717 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Ca | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077717.json](data_element_validation/test_data/TD-TC-077717.json) |
| TC-077718 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077718.json](data_element_validation/test_data/TD-TC-077718.json) |
| TC-077719 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Ca | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077719.json](data_element_validation/test_data/TD-TC-077719.json) |
| TC-077720 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Ca | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077720.json](data_element_validation/test_data/TD-TC-077720.json) |
| TC-077721 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Ca | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077721.json](data_element_validation/test_data/TD-TC-077721.json) |
| TC-077722 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Ca | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077722.json](data_element_validation/test_data/TD-TC-077722.json) |
| TC-077723 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077723.json](data_element_validation/test_data/TD-TC-077723.json) |
| TC-077724 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Ca | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077724.json](data_element_validation/test_data/TD-TC-077724.json) |
| TC-077725 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Ca | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077725.json](data_element_validation/test_data/TD-TC-077725.json) |
| TC-077726 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Lo | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077726.json](data_element_validation/test_data/TD-TC-077726.json) |
| TC-077727 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Lo | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077727.json](data_element_validation/test_data/TD-TC-077727.json) |
| TC-077728 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Lo | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077728.json](data_element_validation/test_data/TD-TC-077728.json) |
| TC-077729 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077729.json](data_element_validation/test_data/TD-TC-077729.json) |
| TC-077730 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Lo | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077730.json](data_element_validation/test_data/TD-TC-077730.json) |
| TC-077731 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Ho | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077731.json](data_element_validation/test_data/TD-TC-077731.json) |
| TC-077732 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Ho | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077732.json](data_element_validation/test_data/TD-TC-077732.json) |
| TC-077733 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Ho | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077733.json](data_element_validation/test_data/TD-TC-077733.json) |
| TC-077734 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Re | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077734.json](data_element_validation/test_data/TD-TC-077734.json) |
| TC-077735 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Ho | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077735.json](data_element_validation/test_data/TD-TC-077735.json) |
| TC-077736 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Bl | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077736.json](data_element_validation/test_data/TD-TC-077736.json) |
| TC-077737 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Bl | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077737.json](data_element_validation/test_data/TD-TC-077737.json) |
| TC-077738 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Bl | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077738.json](data_element_validation/test_data/TD-TC-077738.json) |
| TC-077739 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Bl | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077739.json](data_element_validation/test_data/TD-TC-077739.json) |
| TC-077740 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Bl | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077740.json](data_element_validation/test_data/TD-TC-077740.json) |
| TC-077741 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Se | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077741.json](data_element_validation/test_data/TD-TC-077741.json) |
| TC-077742 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Proprietary Data Load Segment: Se | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077742.json](data_element_validation/test_data/TD-TC-077742.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 119 – Totals with Proprietary Data Load Data Segment @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077743 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077743.json](data_element_validation/test_data/TD-TC-077743.json) |
| TC-077744 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077744.json](data_element_validation/test_data/TD-TC-077744.json) |
| TC-077745 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077745.json](data_element_validation/test_data/TD-TC-077745.json) |
| TC-077746 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077746.json](data_element_validation/test_data/TD-TC-077746.json) |
| TC-077747 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077747.json](data_element_validation/test_data/TD-TC-077747.json) |
| TC-077748 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077748.json](data_element_validation/test_data/TD-TC-077748.json) |
| TC-077749 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077749.json](data_element_validation/test_data/TD-TC-077749.json) |
| TC-077750 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077750.json](data_element_validation/test_data/TD-TC-077750.json) |
| TC-077751 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077751.json](data_element_validation/test_data/TD-TC-077751.json) |
| TC-077752 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077752.json](data_element_validation/test_data/TD-TC-077752.json) |
| TC-077753 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077753.json](data_element_validation/test_data/TD-TC-077753.json) |
| TC-077754 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077754.json](data_element_validation/test_data/TD-TC-077754.json) |
| TC-077755 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077755.json](data_element_validation/test_data/TD-TC-077755.json) |
| TC-077756 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077756.json](data_element_validation/test_data/TD-TC-077756.json) |
| TC-077757 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077757.json](data_element_validation/test_data/TD-TC-077757.json) |
| TC-077758 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077758.json](data_element_validation/test_data/TD-TC-077758.json) |
| TC-077759 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077759.json](data_element_validation/test_data/TD-TC-077759.json) |
| TC-077760 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077760.json](data_element_validation/test_data/TD-TC-077760.json) |
| TC-077761 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077761.json](data_element_validation/test_data/TD-TC-077761.json) |
| TC-077762 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077762.json](data_element_validation/test_data/TD-TC-077762.json) |
| TC-077763 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077763.json](data_element_validation/test_data/TD-TC-077763.json) |
| TC-077764 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077764.json](data_element_validation/test_data/TD-TC-077764.json) |
| TC-077765 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077765.json](data_element_validation/test_data/TD-TC-077765.json) |
| TC-077766 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077766.json](data_element_validation/test_data/TD-TC-077766.json) |
| TC-077767 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077767.json](data_element_validation/test_data/TD-TC-077767.json) |
| TC-077768 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077768.json](data_element_validation/test_data/TD-TC-077768.json) |
| TC-077769 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077769.json](data_element_validation/test_data/TD-TC-077769.json) |
| TC-077770 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077770.json](data_element_validation/test_data/TD-TC-077770.json) |
| TC-077771 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077771.json](data_element_validation/test_data/TD-TC-077771.json) |
| TC-077772 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077772.json](data_element_validation/test_data/TD-TC-077772.json) |
| TC-077773 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077773.json](data_element_validation/test_data/TD-TC-077773.json) |
| TC-077774 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077774.json](data_element_validation/test_data/TD-TC-077774.json) |
| TC-077775 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077775.json](data_element_validation/test_data/TD-TC-077775.json) |
| TC-077776 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077776.json](data_element_validation/test_data/TD-TC-077776.json) |
| TC-077777 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077777.json](data_element_validation/test_data/TD-TC-077777.json) |
| TC-077778 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077778.json](data_element_validation/test_data/TD-TC-077778.json) |
| TC-077779 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077779.json](data_element_validation/test_data/TD-TC-077779.json) |
| TC-077780 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077780.json](data_element_validation/test_data/TD-TC-077780.json) |
| TC-077781 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077781.json](data_element_validation/test_data/TD-TC-077781.json) |
| TC-077782 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077782.json](data_element_validation/test_data/TD-TC-077782.json) |
| TC-077783 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077783.json](data_element_validation/test_data/TD-TC-077783.json) |
| TC-077784 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077784.json](data_element_validation/test_data/TD-TC-077784.json) |
| TC-077785 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077785.json](data_element_validation/test_data/TD-TC-077785.json) |
| TC-077786 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077786.json](data_element_validation/test_data/TD-TC-077786.json) |
| TC-077787 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077787.json](data_element_validation/test_data/TD-TC-077787.json) |
| TC-077788 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077788.json](data_element_validation/test_data/TD-TC-077788.json) |
| TC-077789 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077789.json](data_element_validation/test_data/TD-TC-077789.json) |
| TC-077790 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077790.json](data_element_validation/test_data/TD-TC-077790.json) |
| TC-077791 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077791.json](data_element_validation/test_data/TD-TC-077791.json) |
| TC-077792 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077792.json](data_element_validation/test_data/TD-TC-077792.json) |
| TC-077793 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077793.json](data_element_validation/test_data/TD-TC-077793.json) |
| TC-077794 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077794.json](data_element_validation/test_data/TD-TC-077794.json) |
| TC-077795 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077795.json](data_element_validation/test_data/TD-TC-077795.json) |
| TC-077796 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077796.json](data_element_validation/test_data/TD-TC-077796.json) |
| TC-077797 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077797.json](data_element_validation/test_data/TD-TC-077797.json) |
| TC-077798 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077798.json](data_element_validation/test_data/TD-TC-077798.json) |
| TC-077799 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077799.json](data_element_validation/test_data/TD-TC-077799.json) |
| TC-077800 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077800.json](data_element_validation/test_data/TD-TC-077800.json) |
| TC-077801 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077801.json](data_element_validation/test_data/TD-TC-077801.json) |
| TC-077802 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077802.json](data_element_validation/test_data/TD-TC-077802.json) |
| TC-077803 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077803.json](data_element_validation/test_data/TD-TC-077803.json) |
| TC-077804 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077804.json](data_element_validation/test_data/TD-TC-077804.json) |
| TC-077805 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077805.json](data_element_validation/test_data/TD-TC-077805.json) |
| TC-077806 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077806.json](data_element_validation/test_data/TD-TC-077806.json) |
| TC-077807 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077807.json](data_element_validation/test_data/TD-TC-077807.json) |
| TC-077808 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077808.json](data_element_validation/test_data/TD-TC-077808.json) |
| TC-077809 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077809.json](data_element_validation/test_data/TD-TC-077809.json) |
| TC-077810 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077810.json](data_element_validation/test_data/TD-TC-077810.json) |
| TC-077811 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077811.json](data_element_validation/test_data/TD-TC-077811.json) |
| TC-077812 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077812.json](data_element_validation/test_data/TD-TC-077812.json) |
| TC-077813 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077813.json](data_element_validation/test_data/TD-TC-077813.json) |
| TC-077814 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077814.json](data_element_validation/test_data/TD-TC-077814.json) |
| TC-077815 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077815.json](data_element_validation/test_data/TD-TC-077815.json) |
| TC-077816 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077816.json](data_element_validation/test_data/TD-TC-077816.json) |
| TC-077817 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077817.json](data_element_validation/test_data/TD-TC-077817.json) |
| TC-077818 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077818.json](data_element_validation/test_data/TD-TC-077818.json) |
| TC-077819 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077819.json](data_element_validation/test_data/TD-TC-077819.json) |
| TC-077820 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077820.json](data_element_validation/test_data/TD-TC-077820.json) |
| TC-077821 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077821.json](data_element_validation/test_data/TD-TC-077821.json) |
| TC-077822 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077822.json](data_element_validation/test_data/TD-TC-077822.json) |
| TC-077823 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077823.json](data_element_validation/test_data/TD-TC-077823.json) |
| TC-077824 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077824.json](data_element_validation/test_data/TD-TC-077824.json) |
| TC-077825 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077825.json](data_element_validation/test_data/TD-TC-077825.json) |
| TC-077826 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077826.json](data_element_validation/test_data/TD-TC-077826.json) |
| TC-077827 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077827.json](data_element_validation/test_data/TD-TC-077827.json) |
| TC-077828 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077828.json](data_element_validation/test_data/TD-TC-077828.json) |
| TC-077829 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077829.json](data_element_validation/test_data/TD-TC-077829.json) |
| TC-077830 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077830.json](data_element_validation/test_data/TD-TC-077830.json) |
| TC-077831 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077831.json](data_element_validation/test_data/TD-TC-077831.json) |
| TC-077832 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077832.json](data_element_validation/test_data/TD-TC-077832.json) |
| TC-077833 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – Totals with Proprietary Data Load | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077833.json](data_element_validation/test_data/TD-TC-077833.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 123 – NFC Payment Tokenization Data Segment @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077834 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Negative | pending_sme | p. | 1 |  | [TD-TC-077834.json](data_element_validation/test_data/TD-TC-077834.json) |
| TC-077835 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Negative | pending_sme | p. | 1 |  | [TD-TC-077835.json](data_element_validation/test_data/TD-TC-077835.json) |
| TC-077836 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Boundary | pending_sme | p. | 1 |  | [TD-TC-077836.json](data_element_validation/test_data/TD-TC-077836.json) |
| TC-077837 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Negative | pending_sme | p. | 1 |  | [TD-TC-077837.json](data_element_validation/test_data/TD-TC-077837.json) |
| TC-077838 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Negative | pending_sme | p. | 1 |  | [TD-TC-077838.json](data_element_validation/test_data/TD-TC-077838.json) |
| TC-077839 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Negative | pending_sme | p. | 1 |  | [TD-TC-077839.json](data_element_validation/test_data/TD-TC-077839.json) |
| TC-077840 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Boundary | pending_sme | p. | 1 |  | [TD-TC-077840.json](data_element_validation/test_data/TD-TC-077840.json) |
| TC-077841 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Negative | pending_sme | p. | 1 |  | [TD-TC-077841.json](data_element_validation/test_data/TD-TC-077841.json) |
| TC-077842 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Negative | pending_sme | p. | 1 |  | [TD-TC-077842.json](data_element_validation/test_data/TD-TC-077842.json) |
| TC-077843 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Negative | pending_sme | p. | 1 |  | [TD-TC-077843.json](data_element_validation/test_data/TD-TC-077843.json) |
| TC-077844 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Boundary | pending_sme | p. | 1 |  | [TD-TC-077844.json](data_element_validation/test_data/TD-TC-077844.json) |
| TC-077845 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Negative | pending_sme | p. | 1 |  | [TD-TC-077845.json](data_element_validation/test_data/TD-TC-077845.json) |
| TC-077846 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Negative | pending_sme | p. | 1 |  | [TD-TC-077846.json](data_element_validation/test_data/TD-TC-077846.json) |
| TC-077847 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Negative | pending_sme | p. | 1 |  | [TD-TC-077847.json](data_element_validation/test_data/TD-TC-077847.json) |
| TC-077848 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Boundary | pending_sme | p. | 1 |  | [TD-TC-077848.json](data_element_validation/test_data/TD-TC-077848.json) |
| TC-077849 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Negative | pending_sme | p. | 1 |  | [TD-TC-077849.json](data_element_validation/test_data/TD-TC-077849.json) |
| TC-077850 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Negative | pending_sme | p. | 1 |  | [TD-TC-077850.json](data_element_validation/test_data/TD-TC-077850.json) |
| TC-077851 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Boundary | pending_sme | p. | 1 |  | [TD-TC-077851.json](data_element_validation/test_data/TD-TC-077851.json) |
| TC-077852 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Negative | pending_sme | p. | 1 |  | [TD-TC-077852.json](data_element_validation/test_data/TD-TC-077852.json) |
| TC-077853 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Negative | pending_sme | p. | 1 |  | [TD-TC-077853.json](data_element_validation/test_data/TD-TC-077853.json) |
| TC-077854 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Negative | pending_sme | p. | 1 |  | [TD-TC-077854.json](data_element_validation/test_data/TD-TC-077854.json) |
| TC-077855 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Boundary | pending_sme | p. | 1 |  | [TD-TC-077855.json](data_element_validation/test_data/TD-TC-077855.json) |
| TC-077856 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Negative | pending_sme | p. | 1 |  | [TD-TC-077856.json](data_element_validation/test_data/TD-TC-077856.json) |
| TC-077857 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Negative | pending_sme | p. | 1 |  | [TD-TC-077857.json](data_element_validation/test_data/TD-TC-077857.json) |
| TC-077858 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Negative | pending_sme | p. | 1 |  | [TD-TC-077858.json](data_element_validation/test_data/TD-TC-077858.json) |
| TC-077859 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Boundary | pending_sme | p. | 1 |  | [TD-TC-077859.json](data_element_validation/test_data/TD-TC-077859.json) |
| TC-077860 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Negative | pending_sme | p. | 1 |  | [TD-TC-077860.json](data_element_validation/test_data/TD-TC-077860.json) |
| TC-077861 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Negative | pending_sme | p. | 1 |  | [TD-TC-077861.json](data_element_validation/test_data/TD-TC-077861.json) |
| TC-077862 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Negative | pending_sme | p. | 1 |  | [TD-TC-077862.json](data_element_validation/test_data/TD-TC-077862.json) |
| TC-077863 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Boundary | pending_sme | p. | 1 |  | [TD-TC-077863.json](data_element_validation/test_data/TD-TC-077863.json) |
| TC-077864 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Negative | pending_sme | p. | 1 |  | [TD-TC-077864.json](data_element_validation/test_data/TD-TC-077864.json) |
| TC-077865 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Negative | pending_sme | p. | 1 |  | [TD-TC-077865.json](data_element_validation/test_data/TD-TC-077865.json) |
| TC-077866 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Negative | blocked_no_test_data | p. | 1 |  | - |
| TC-077867 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – NFC Payment Tokenization Data Seg | Data element & segment validation | Negative | blocked_no_test_data | p. | 1 |  | - |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 130 – EMV Request Data Segment @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077868 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Segment | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077868.json](data_element_validation/test_data/TD-TC-077868.json) |
| TC-077869 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: CA Publ | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077869.json](data_element_validation/test_data/TD-TC-077869.json) |
| TC-077870 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: CA Publ | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077870.json](data_element_validation/test_data/TD-TC-077870.json) |
| TC-077871 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: CA Publ | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077871.json](data_element_validation/test_data/TD-TC-077871.json) |
| TC-077872 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: CA Publ | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077872.json](data_element_validation/test_data/TD-TC-077872.json) |
| TC-077873 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Car | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077873.json](data_element_validation/test_data/TD-TC-077873.json) |
| TC-077874 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Car | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077874.json](data_element_validation/test_data/TD-TC-077874.json) |
| TC-077875 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Car | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077875.json](data_element_validation/test_data/TD-TC-077875.json) |
| TC-077876 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Car | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077876.json](data_element_validation/test_data/TD-TC-077876.json) |
| TC-077877 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Car | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077877.json](data_element_validation/test_data/TD-TC-077877.json) |
| TC-077878 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Chi | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077878.json](data_element_validation/test_data/TD-TC-077878.json) |
| TC-077879 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Chi | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077879.json](data_element_validation/test_data/TD-TC-077879.json) |
| TC-077880 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Chi | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077880.json](data_element_validation/test_data/TD-TC-077880.json) |
| TC-077881 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077881.json](data_element_validation/test_data/TD-TC-077881.json) |
| TC-077882 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Chi | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077882.json](data_element_validation/test_data/TD-TC-077882.json) |
| TC-077883 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Chi | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077883.json](data_element_validation/test_data/TD-TC-077883.json) |
| TC-077884 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Chi | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077884.json](data_element_validation/test_data/TD-TC-077884.json) |
| TC-077885 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Chi | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077885.json](data_element_validation/test_data/TD-TC-077885.json) |
| TC-077886 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077886.json](data_element_validation/test_data/TD-TC-077886.json) |
| TC-077887 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Add | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077887.json](data_element_validation/test_data/TD-TC-077887.json) |
| TC-077888 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Add | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077888.json](data_element_validation/test_data/TD-TC-077888.json) |
| TC-077889 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Add | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077889.json](data_element_validation/test_data/TD-TC-077889.json) |
| TC-077890 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077890.json](data_element_validation/test_data/TD-TC-077890.json) |
| TC-077891 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Add | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077891.json](data_element_validation/test_data/TD-TC-077891.json) |
| TC-077892 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Add | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077892.json](data_element_validation/test_data/TD-TC-077892.json) |
| TC-077893 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Add | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077893.json](data_element_validation/test_data/TD-TC-077893.json) |
| TC-077894 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Add | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077894.json](data_element_validation/test_data/TD-TC-077894.json) |
| TC-077895 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077895.json](data_element_validation/test_data/TD-TC-077895.json) |
| TC-077896 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Add | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077896.json](data_element_validation/test_data/TD-TC-077896.json) |
| TC-077897 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Add | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077897.json](data_element_validation/test_data/TD-TC-077897.json) |
| TC-077898 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Add | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077898.json](data_element_validation/test_data/TD-TC-077898.json) |
| TC-077899 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Add | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077899.json](data_element_validation/test_data/TD-TC-077899.json) |
| TC-077900 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Add | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077900.json](data_element_validation/test_data/TD-TC-077900.json) |
| TC-077901 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Additio | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077901.json](data_element_validation/test_data/TD-TC-077901.json) |
| TC-077902 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Additio | Data element & segment validation | Boundary | ready | p. | 1 |  | [TD-TC-077902.json](data_element_validation/test_data/TD-TC-077902.json) |
| TC-077903 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Additio | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077903.json](data_element_validation/test_data/TD-TC-077903.json) |
| TC-077904 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077904.json](data_element_validation/test_data/TD-TC-077904.json) |
| TC-077905 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Segment | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077905.json](data_element_validation/test_data/TD-TC-077905.json) |
| TC-077906 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Segment | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077906.json](data_element_validation/test_data/TD-TC-077906.json) |
| TC-077907 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: EMV Chi | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077907.json](data_element_validation/test_data/TD-TC-077907.json) |
| TC-077908 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077908.json](data_element_validation/test_data/TD-TC-077908.json) |
| TC-077909 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077909.json](data_element_validation/test_data/TD-TC-077909.json) |
| TC-077910 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077910.json](data_element_validation/test_data/TD-TC-077910.json) |
| TC-077911 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077911.json](data_element_validation/test_data/TD-TC-077911.json) |
| TC-077912 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077912.json](data_element_validation/test_data/TD-TC-077912.json) |
| TC-077913 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077913.json](data_element_validation/test_data/TD-TC-077913.json) |
| TC-077914 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077914.json](data_element_validation/test_data/TD-TC-077914.json) |
| TC-077915 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077915.json](data_element_validation/test_data/TD-TC-077915.json) |
| TC-077916 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077916.json](data_element_validation/test_data/TD-TC-077916.json) |
| TC-077917 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077917.json](data_element_validation/test_data/TD-TC-077917.json) |
| TC-077918 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077918.json](data_element_validation/test_data/TD-TC-077918.json) |
| TC-077919 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077919.json](data_element_validation/test_data/TD-TC-077919.json) |
| TC-077920 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077920.json](data_element_validation/test_data/TD-TC-077920.json) |
| TC-077921 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077921.json](data_element_validation/test_data/TD-TC-077921.json) |
| TC-077922 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – EMV Request Data Segment: Require | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077922.json](data_element_validation/test_data/TD-TC-077922.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 132 – CA Public Key File Data Segment @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077923 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077923.json](data_element_validation/test_data/TD-TC-077923.json) |
| TC-077924 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077924.json](data_element_validation/test_data/TD-TC-077924.json) |
| TC-077925 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077925.json](data_element_validation/test_data/TD-TC-077925.json) |
| TC-077926 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077926.json](data_element_validation/test_data/TD-TC-077926.json) |
| TC-077927 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077927.json](data_element_validation/test_data/TD-TC-077927.json) |
| TC-077928 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077928.json](data_element_validation/test_data/TD-TC-077928.json) |
| TC-077929 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077929.json](data_element_validation/test_data/TD-TC-077929.json) |
| TC-077930 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077930.json](data_element_validation/test_data/TD-TC-077930.json) |
| TC-077931 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077931.json](data_element_validation/test_data/TD-TC-077931.json) |
| TC-077932 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077932.json](data_element_validation/test_data/TD-TC-077932.json) |
| TC-077933 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077933.json](data_element_validation/test_data/TD-TC-077933.json) |
| TC-077934 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077934.json](data_element_validation/test_data/TD-TC-077934.json) |
| TC-077935 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077935.json](data_element_validation/test_data/TD-TC-077935.json) |
| TC-077936 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077936.json](data_element_validation/test_data/TD-TC-077936.json) |
| TC-077937 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077937.json](data_element_validation/test_data/TD-TC-077937.json) |
| TC-077938 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077938.json](data_element_validation/test_data/TD-TC-077938.json) |
| TC-077939 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077939.json](data_element_validation/test_data/TD-TC-077939.json) |
| TC-077940 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077940.json](data_element_validation/test_data/TD-TC-077940.json) |
| TC-077941 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077941.json](data_element_validation/test_data/TD-TC-077941.json) |
| TC-077942 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077942.json](data_element_validation/test_data/TD-TC-077942.json) |
| TC-077943 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077943.json](data_element_validation/test_data/TD-TC-077943.json) |
| TC-077944 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077944.json](data_element_validation/test_data/TD-TC-077944.json) |
| TC-077945 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077945.json](data_element_validation/test_data/TD-TC-077945.json) |
| TC-077946 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077946.json](data_element_validation/test_data/TD-TC-077946.json) |
| TC-077947 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077947.json](data_element_validation/test_data/TD-TC-077947.json) |
| TC-077948 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077948.json](data_element_validation/test_data/TD-TC-077948.json) |
| TC-077949 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077949.json](data_element_validation/test_data/TD-TC-077949.json) |
| TC-077950 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077950.json](data_element_validation/test_data/TD-TC-077950.json) |
| TC-077951 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077951.json](data_element_validation/test_data/TD-TC-077951.json) |
| TC-077952 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077952.json](data_element_validation/test_data/TD-TC-077952.json) |
| TC-077953 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077953.json](data_element_validation/test_data/TD-TC-077953.json) |
| TC-077954 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077954.json](data_element_validation/test_data/TD-TC-077954.json) |
| TC-077955 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077955.json](data_element_validation/test_data/TD-TC-077955.json) |
| TC-077956 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077956.json](data_element_validation/test_data/TD-TC-077956.json) |
| TC-077957 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077957.json](data_element_validation/test_data/TD-TC-077957.json) |
| TC-077958 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077958.json](data_element_validation/test_data/TD-TC-077958.json) |
| TC-077959 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077959.json](data_element_validation/test_data/TD-TC-077959.json) |
| TC-077960 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077960.json](data_element_validation/test_data/TD-TC-077960.json) |
| TC-077961 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077961.json](data_element_validation/test_data/TD-TC-077961.json) |
| TC-077962 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077962.json](data_element_validation/test_data/TD-TC-077962.json) |
| TC-077963 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS – CA Public Key File Data Segment:  | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-077963.json](data_element_validation/test_data/TD-TC-077963.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Segment 153 – Network Token Data Request @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-077964 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Network Token Data Request: Segme | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077964.json](data_element_validation/test_data/TD-TC-077964.json) |
| TC-077965 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Network Token Data Request: Enhan | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077965.json](data_element_validation/test_data/TD-TC-077965.json) |
| TC-077966 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Network Token Data Request: Enhan | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-077966.json](data_element_validation/test_data/TD-TC-077966.json) |
| TC-077967 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Network Token Data Request: Enhan | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077967.json](data_element_validation/test_data/TD-TC-077967.json) |
| TC-077968 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Network Token Data Request: Requi | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077968.json](data_element_validation/test_data/TD-TC-077968.json) |
| TC-077969 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Network Token Data Request: Segme | Data element & segment validation | Negative | blocked_no_test_data | p. | 1 |  | - |
| TC-077970 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Network Token Data Request: Segme | Data element & segment validation | Negative | blocked_no_test_data | p. | 1 |  | - |
| TC-077971 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Network Token Data Request: Requi | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077971.json](data_element_validation/test_data/TD-TC-077971.json) |
| TC-077972 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Network Token Data Request: Requi | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077972.json](data_element_validation/test_data/TD-TC-077972.json) |
| TC-077973 BUYPASS ATL105 Data Element Validation MERCHANT 02 POS – Network Token Data Request: Requi | Data element & segment validation | Negative | requires_review | p. | 1 |  | [TD-TC-077973.json](data_element_validation/test_data/TD-TC-077973.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Software Load Request – Message fields @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-078009 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078009.json](data_element_validation/test_data/TD-TC-078009.json) |
| TC-078010 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078010.json](data_element_validation/test_data/TD-TC-078010.json) |
| TC-078011 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-078011.json](data_element_validation/test_data/TD-TC-078011.json) |
| TC-078012 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078012.json](data_element_validation/test_data/TD-TC-078012.json) |
| TC-078013 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078013.json](data_element_validation/test_data/TD-TC-078013.json) |
| TC-078014 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078014.json](data_element_validation/test_data/TD-TC-078014.json) |
| TC-078015 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078015.json](data_element_validation/test_data/TD-TC-078015.json) |
| TC-078016 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-078016.json](data_element_validation/test_data/TD-TC-078016.json) |
| TC-078017 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078017.json](data_element_validation/test_data/TD-TC-078017.json) |
| TC-078018 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078018.json](data_element_validation/test_data/TD-TC-078018.json) |
| TC-078019 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078019.json](data_element_validation/test_data/TD-TC-078019.json) |
| TC-078020 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-078020.json](data_element_validation/test_data/TD-TC-078020.json) |
| TC-078021 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078021.json](data_element_validation/test_data/TD-TC-078021.json) |
| TC-078022 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078022.json](data_element_validation/test_data/TD-TC-078022.json) |
| TC-078023 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078023.json](data_element_validation/test_data/TD-TC-078023.json) |
| TC-078024 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078024.json](data_element_validation/test_data/TD-TC-078024.json) |
| TC-078025 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-078025.json](data_element_validation/test_data/TD-TC-078025.json) |
| TC-078026 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078026.json](data_element_validation/test_data/TD-TC-078026.json) |
| TC-078027 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078027.json](data_element_validation/test_data/TD-TC-078027.json) |
| TC-078028 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message fie | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078028.json](data_element_validation/test_data/TD-TC-078028.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Software Load Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-078241 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message str | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078241.json](data_element_validation/test_data/TD-TC-078241.json) |
| TC-078242 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message str | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078242.json](data_element_validation/test_data/TD-TC-078242.json) |
| TC-078243 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message str | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078243.json](data_element_validation/test_data/TD-TC-078243.json) |
| TC-078244 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message str | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078244.json](data_element_validation/test_data/TD-TC-078244.json) |
| TC-078245 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message str | Data element & segment validation | Positive | requires_review | p. | 1 |  | [TD-TC-078245.json](data_element_validation/test_data/TD-TC-078245.json) |
| TC-078246 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Software Load Request – Message str | Data element & segment validation | Positive | requires_review | p. | 1 |  | [TD-TC-078246.json](data_element_validation/test_data/TD-TC-078246.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Table Load Request – Message fields @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-078029 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078029.json](data_element_validation/test_data/TD-TC-078029.json) |
| TC-078030 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078030.json](data_element_validation/test_data/TD-TC-078030.json) |
| TC-078031 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-078031.json](data_element_validation/test_data/TD-TC-078031.json) |
| TC-078032 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078032.json](data_element_validation/test_data/TD-TC-078032.json) |
| TC-078033 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078033.json](data_element_validation/test_data/TD-TC-078033.json) |
| TC-078034 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078034.json](data_element_validation/test_data/TD-TC-078034.json) |
| TC-078035 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078035.json](data_element_validation/test_data/TD-TC-078035.json) |
| TC-078036 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-078036.json](data_element_validation/test_data/TD-TC-078036.json) |
| TC-078037 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078037.json](data_element_validation/test_data/TD-TC-078037.json) |
| TC-078038 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078038.json](data_element_validation/test_data/TD-TC-078038.json) |
| TC-078039 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078039.json](data_element_validation/test_data/TD-TC-078039.json) |
| TC-078040 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-078040.json](data_element_validation/test_data/TD-TC-078040.json) |
| TC-078041 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078041.json](data_element_validation/test_data/TD-TC-078041.json) |
| TC-078042 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078042.json](data_element_validation/test_data/TD-TC-078042.json) |
| TC-078043 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078043.json](data_element_validation/test_data/TD-TC-078043.json) |
| TC-078044 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078044.json](data_element_validation/test_data/TD-TC-078044.json) |
| TC-078045 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Boundary | requires_review | p. | 1 |  | [TD-TC-078045.json](data_element_validation/test_data/TD-TC-078045.json) |
| TC-078046 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078046.json](data_element_validation/test_data/TD-TC-078046.json) |
| TC-078047 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078047.json](data_element_validation/test_data/TD-TC-078047.json) |
| TC-078048 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message fields | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078048.json](data_element_validation/test_data/TD-TC-078048.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Table Load Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-078229 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message struct | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078229.json](data_element_validation/test_data/TD-TC-078229.json) |
| TC-078230 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message struct | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078230.json](data_element_validation/test_data/TD-TC-078230.json) |
| TC-078231 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message struct | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078231.json](data_element_validation/test_data/TD-TC-078231.json) |
| TC-078232 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message struct | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078232.json](data_element_validation/test_data/TD-TC-078232.json) |
| TC-078233 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message struct | Data element & segment validation | Positive | requires_review | p. | 1 |  | [TD-TC-078233.json](data_element_validation/test_data/TD-TC-078233.json) |
| TC-078234 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Table Load Request – Message struct | Data element & segment validation | Positive | requires_review | p. | 1 |  | [TD-TC-078234.json](data_element_validation/test_data/TD-TC-078234.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Totals Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-078156 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals Request – Message structure: | Data element & segment validation | Positive | ready | p. | 1 |  | [TD-TC-078156.json](data_element_validation/test_data/TD-TC-078156.json) |
| TC-078157 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals Request – Message structure: | Data element & segment validation | Positive | ready | p. | 1 |  | [TD-TC-078157.json](data_element_validation/test_data/TD-TC-078157.json) |
| TC-078158 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals Request – Message structure: | Data element & segment validation | Negative | blocked_no_test_data | BR-179-3 p.179; BR-235-2 p.235; BR-395-4 p.395 | 1 |  | - |
| TC-078159 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals Request – Message structure: | Data element & segment validation | Negative | ready | BR-179-3 p.179; BR-235-2 p.235 | 1 |  | [TD-TC-078159.json](data_element_validation/test_data/TD-TC-078159.json) |
| TC-078160 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals Request – Message structure: | Data element & segment validation | Negative | ready | p.[219, 395] | 1 |  | [TD-TC-078160.json](data_element_validation/test_data/TD-TC-078160.json) |
| TC-078161 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals Request – Message structure: | Data element & segment validation | Negative | ready | BR-179-3 p.179; BR-235-2 p.235 | 1 |  | [TD-TC-078161.json](data_element_validation/test_data/TD-TC-078161.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation Totals with Proprietary Data Load Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-078195 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078195.json](data_element_validation/test_data/TD-TC-078195.json) |
| TC-078196 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078196.json](data_element_validation/test_data/TD-TC-078196.json) |
| TC-078197 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078197.json](data_element_validation/test_data/TD-TC-078197.json) |
| TC-078198 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078198.json](data_element_validation/test_data/TD-TC-078198.json) |
| TC-078199 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078199.json](data_element_validation/test_data/TD-TC-078199.json) |
| TC-078200 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078200.json](data_element_validation/test_data/TD-TC-078200.json) |
| TC-078201 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078201.json](data_element_validation/test_data/TD-TC-078201.json) |
| TC-078202 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078202.json](data_element_validation/test_data/TD-TC-078202.json) |
| TC-078203 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078203.json](data_element_validation/test_data/TD-TC-078203.json) |
| TC-078204 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078204.json](data_element_validation/test_data/TD-TC-078204.json) |
| TC-078205 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078205.json](data_element_validation/test_data/TD-TC-078205.json) |
| TC-078206 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078206.json](data_element_validation/test_data/TD-TC-078206.json) |
| TC-078207 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078207.json](data_element_validation/test_data/TD-TC-078207.json) |
| TC-078208 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Positive | ready | p. | 1 |  | [TD-TC-078208.json](data_element_validation/test_data/TD-TC-078208.json) |
| TC-078209 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Positive | ready | p. | 1 |  | [TD-TC-078209.json](data_element_validation/test_data/TD-TC-078209.json) |
| TC-078210 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | ready | BR-180-3 p.180; BR-263-2 p.263; BR-34-8 p.34 | 1 |  | [TD-TC-078210.json](data_element_validation/test_data/TD-TC-078210.json) |
| TC-078211 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | ready | BR-180-3 p.180; BR-263-2 p.263 | 1 |  | [TD-TC-078211.json](data_element_validation/test_data/TD-TC-078211.json) |
| TC-078212 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | ready | p.[219, 395] | 1 |  | [TD-TC-078212.json](data_element_validation/test_data/TD-TC-078212.json) |
| TC-078213 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS Totals with Proprietary Data Load R | Data element & segment validation | Negative | ready | BR-180-3 p.180; BR-263-2 p.263 | 1 |  | [TD-TC-078213.json](data_element_validation/test_data/TD-TC-078213.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Data Element Validation TransArmor Key Load Request – Message structure @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-078168 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS TransArmor Key Load Request – Messa | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078168.json](data_element_validation/test_data/TD-TC-078168.json) |
| TC-078169 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS TransArmor Key Load Request – Messa | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078169.json](data_element_validation/test_data/TD-TC-078169.json) |
| TC-078170 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS TransArmor Key Load Request – Messa | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078170.json](data_element_validation/test_data/TD-TC-078170.json) |
| TC-078171 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS TransArmor Key Load Request – Messa | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078171.json](data_element_validation/test_data/TD-TC-078171.json) |
| TC-078172 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS TransArmor Key Load Request – Messa | Data element & segment validation | Negative | ready | p. | 1 |  | [TD-TC-078172.json](data_element_validation/test_data/TD-TC-078172.json) |
| TC-078173 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS TransArmor Key Load Request – Messa | Data element & segment validation | Positive | requires_review | p. | 1 |  | [TD-TC-078173.json](data_element_validation/test_data/TD-TC-078173.json) |
| TC-078174 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS TransArmor Key Load Request – Messa | Data element & segment validation | Positive | requires_review | p. | 1 |  | [TD-TC-078174.json](data_element_validation/test_data/TD-TC-078174.json) |
| TC-078175 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS TransArmor Key Load Request – Messa | Data element & segment validation | Negative | ready | BR-81-7 p.81; BR-ATL105-TRANSARMOR-41-1 p.41; BR-ATL105-TRANSARMOR-41-2 p.41 | 1 |  | [TD-TC-078175.json](data_element_validation/test_data/TD-TC-078175.json) |
| TC-078176 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS TransArmor Key Load Request – Messa | Data element & segment validation | Negative | ready | BR-81-7 p.81; BR-ATL105-TRANSARMOR-41-1 p.41 | 1 |  | [TD-TC-078176.json](data_element_validation/test_data/TD-TC-078176.json) |
| TC-078177 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS TransArmor Key Load Request – Messa | Data element & segment validation | Negative | ready | p.[395] | 1 |  | [TD-TC-078177.json](data_element_validation/test_data/TD-TC-078177.json) |
| TC-078178 BUYPASS ATL105 Data Element Validation MERCHANT 01 POS TransArmor Key Load Request – Messa | Data element & segment validation | Negative | ready | BR-81-7 p.81; BR-ATL105-TRANSARMOR-41-1 p.41 | 1 |  | [TD-TC-078178.json](data_element_validation/test_data/TD-TC-078178.json) |

## BUYPASS ATL105 Terminal & Host Operations (`BR-A0098BFB`)

Supporting citations: p.77; p.522

### SC-ATL105-074DD2CC - BUYPASS ATL105 Terminal & Host Operations CA Public Key File Load Request @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-078290 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS CA Public Key File Load Request: | Terminal & host operation | Positive | needs_client_input | p.274; p.404; p.511 | 1 | OP_CA_PUBLIC_KEY_LOAD | [TD-TC-078290.json](terminal_and_host_operations/test_data/TD-TC-078290.json) |
| TC-078291 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS CA Public Key File Load Request: | Terminal & host operation | Negative | needs_client_input | p.274; p.404; p.404 | 1 | OP_CA_PUBLIC_KEY_LOAD | [TD-TC-078291.json](terminal_and_host_operations/test_data/TD-TC-078291.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Terminal & Host Operations Communications Test (Transaction Type 900) @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-078292 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Communications Test (Transaction | Terminal & host operation | Positive | ready | p.187; p.59; p.404 | 1 | OP_COMMUNICATIONS_TEST | [TD-TC-078292.json](terminal_and_host_operations/test_data/TD-TC-078292.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Terminal & Host Operations Device & terminal behaviour @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-078253 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Sto | Device-only | Negative | requires_review | p.520; p.335; p.335 | 1 |  | - |
| TC-078254 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Ret | Device-only | Negative | ready | p.520; p.511; p.83 | 1 |  | - |
| TC-078255 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Voi | Device-only | Negative | ready | p.520; p.511; p.83 | 1 |  | - |
| TC-078256 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Pre | Device-only | Negative | ready | p.520; p.511; p.83 | 1 |  | - |
| TC-078257 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Bef | Terminal & host operation | Negative | ready | p.189; p.189; p.511 | 1 |  | - |
| TC-078258 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Device & terminal behaviour: Loc | Terminal & host operation | Positive | ready | p.71; p.72; p.66 | 1 |  | - |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Terminal & Host Operations Downloads (Downline Load Request) @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-078293 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Downloads (Downline Load Request | Terminal & host operation | Positive | ready | p.190; p.189; p.511 | 1 | OP_TABLE_LOAD | [TD-TC-078293.json](terminal_and_host_operations/test_data/TD-TC-078293.json) |
| TC-078294 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Downloads (Downline Load Request | Terminal & host operation | Positive | ready | p.193; p.189; p.511 | 1 | OP_PHONE_LOAD | [TD-TC-078294.json](terminal_and_host_operations/test_data/TD-TC-078294.json) |
| TC-078295 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Downloads (Downline Load Request | Terminal & host operation | Positive | ready | p.195; p.189; p.511 | 1 | OP_DATE_TIME_LOAD | [TD-TC-078295.json](terminal_and_host_operations/test_data/TD-TC-078295.json) |
| TC-078296 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Downloads (Downline Load Request | Terminal & host operation | Positive | needs_client_input | p.197; p.189; p.511 | 1 | OP_SOFTWARE_LOAD | [TD-TC-078296.json](terminal_and_host_operations/test_data/TD-TC-078296.json) |
| TC-078297 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Downloads (Downline Load Request | Terminal & host operation | Positive | needs_client_input | p.202; p.189; p.511 | 1 | OP_MONERIS_KEY_LOAD | [TD-TC-078297.json](terminal_and_host_operations/test_data/TD-TC-078297.json) |
| TC-078298 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Downloads (Downline Load Request | Terminal & host operation | Negative | needs_client_input | p.190; p.511; p.83 | 1 | OP_TABLE_LOAD | [TD-TC-078298.json](terminal_and_host_operations/test_data/TD-TC-078298.json) |
| TC-078299 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Downloads (Downline Load Request | Terminal & host operation | Positive | needs_client_input | p.189; p.189; p.511 | 1 | Purchase/Capture | [TD-TC-078299.step1.purchase.json](terminal_and_host_operations/test_data/TD-TC-078299.step1.purchase.json) |
|  |  |  |  |  | 2 | OP_TABLE_LOAD | [TD-TC-078299.step2.table-load.json](terminal_and_host_operations/test_data/TD-TC-078299.step2.table-load.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Terminal & Host Operations Electronic Mail Request @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-078270 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Electronic Mail Request: Retriev | Terminal & host operation | Positive | ready | p.522; p.511; p.83 | 1 | OP_EMAIL_RETRIEVE | [TD-TC-078270.json](terminal_and_host_operations/test_data/TD-TC-078270.json) |
| TC-078271 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Electronic Mail Request: Retriev | Terminal & host operation | Positive | ready | p.522; p.511; p.83 | 1 | OP_EMAIL_PROPRIETARY | [TD-TC-078271.json](terminal_and_host_operations/test_data/TD-TC-078271.json) |
| TC-078272 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Electronic Mail Request: Reset t | Terminal & host operation | Positive | ready | p.522; p.511; p.83 | 1 | OP_EMAIL_RESET | [TD-TC-078272.json](terminal_and_host_operations/test_data/TD-TC-078272.json) |
| TC-078273 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Electronic Mail Request: Submit  | Terminal & host operation | Positive | needs_client_input | p.522; p.511; p.83 | 1 | OP_EMAIL_SUBMIT | [TD-TC-078273.json](terminal_and_host_operations/test_data/TD-TC-078273.json) |
| TC-078274 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Electronic Mail Request: Retriev | Terminal & host operation | Negative | needs_client_input | p.522; p.404; p.511 | 1 | OP_EMAIL_RETRIEVE | [TD-TC-078274.json](terminal_and_host_operations/test_data/TD-TC-078274.json) |
| TC-078275 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Electronic Mail Request: Retriev | Terminal & host operation | Negative | needs_client_input | p.522; p.404; p.511 | 1 | OP_EMAIL_PROPRIETARY | [TD-TC-078275.json](terminal_and_host_operations/test_data/TD-TC-078275.json) |
| TC-078276 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Electronic Mail Request: Reset t | Terminal & host operation | Negative | needs_client_input | p.522; p.404; p.511 | 1 | OP_EMAIL_RESET | [TD-TC-078276.json](terminal_and_host_operations/test_data/TD-TC-078276.json) |
| TC-078277 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Electronic Mail Request: Submit  | Terminal & host operation | Negative | needs_client_input | p.522; p.404; p.511 | 1 | OP_EMAIL_SUBMIT | [TD-TC-078277.json](terminal_and_host_operations/test_data/TD-TC-078277.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Terminal & Host Operations Proprietary Data Load Request @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-078278 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: R | Terminal & host operation | Positive | ready | p.522; p.257; p.511 | 1 | OP_DATA_RECEIPT_TEXT | [TD-TC-078278.json](terminal_and_host_operations/test_data/TD-TC-078278.json) |
| TC-078279 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: R | Terminal & host operation | Positive | ready | p.522; p.258; p.511 | 1 | OP_DATA_CARD_TABLE | [TD-TC-078279.json](terminal_and_host_operations/test_data/TD-TC-078279.json) |
| TC-078280 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: S | Terminal & host operation | Positive | needs_client_input | p.522; p.258; p.511 | 1 | OP_DATA_SITE_CONFIG | [TD-TC-078280.json](terminal_and_host_operations/test_data/TD-TC-078280.json) |
| TC-078281 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: R | Terminal & host operation | Positive | needs_client_input | p.260; p.511; p.83 | 1 | OP_DATA_HOST_DISCOUNT | [TD-TC-078281.json](terminal_and_host_operations/test_data/TD-TC-078281.json) |
| TC-078282 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: S | Terminal & host operation | Positive | needs_client_input | p.522; p.262; p.511 | 1 | OP_DATA_FUEL_VOLUME | [TD-TC-078282.json](terminal_and_host_operations/test_data/TD-TC-078282.json) |
| TC-078283 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: R | Terminal & host operation | Negative | needs_client_input | p.522; p.257; p.404 | 1 | OP_DATA_RECEIPT_TEXT | [TD-TC-078283.json](terminal_and_host_operations/test_data/TD-TC-078283.json) |
| TC-078284 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: R | Terminal & host operation | Negative | needs_client_input | p.522; p.258; p.404 | 1 | OP_DATA_CARD_TABLE | [TD-TC-078284.json](terminal_and_host_operations/test_data/TD-TC-078284.json) |
| TC-078285 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: S | Terminal & host operation | Negative | needs_client_input | p.522; p.258; p.404 | 1 | OP_DATA_SITE_CONFIG | [TD-TC-078285.json](terminal_and_host_operations/test_data/TD-TC-078285.json) |
| TC-078286 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: R | Terminal & host operation | Negative | needs_client_input | p.260; p.404; p.511 | 1 | OP_DATA_HOST_DISCOUNT | [TD-TC-078286.json](terminal_and_host_operations/test_data/TD-TC-078286.json) |
| TC-078287 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Proprietary Data Load Request: S | Terminal & host operation | Negative | needs_client_input | p.522; p.262; p.404 | 1 | OP_DATA_FUEL_VOLUME | [TD-TC-078287.json](terminal_and_host_operations/test_data/TD-TC-078287.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Terminal & Host Operations Totals Request (Prompt Code 990) @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-078259 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Totals for the c | Terminal & host operation | Positive | ready | p.419; p.522; p.237 | 1 | OP_TOTALS | [TD-TC-078259.json](terminal_and_host_operations/test_data/TD-TC-078259.json) |
| TC-078260 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Totals for the m | Terminal & host operation | Positive | ready | p.419; p.522; p.237 | 1 | OP_TOTALS | [TD-TC-078260.json](terminal_and_host_operations/test_data/TD-TC-078260.json) |
| TC-078261 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Totals for the s | Terminal & host operation | Positive | ready | p.419; p.522; p.237 | 1 | OP_TOTALS | [TD-TC-078261.json](terminal_and_host_operations/test_data/TD-TC-078261.json) |
| TC-078262 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Totals for the t | Terminal & host operation | Positive | ready | p.419; p.522; p.237 | 1 | OP_TOTALS | [TD-TC-078262.json](terminal_and_host_operations/test_data/TD-TC-078262.json) |
| TC-078263 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Totals since the | Terminal & host operation | Positive | ready | p.419; p.522; p.237 | 1 | OP_TOTALS | [TD-TC-078263.json](terminal_and_host_operations/test_data/TD-TC-078263.json) |
| TC-078264 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Clear all totals | Terminal & host operation | Positive | ready | p.419; p.522; p.237 | 1 | OP_TOTALS | [TD-TC-078264.json](terminal_and_host_operations/test_data/TD-TC-078264.json) |
| TC-078265 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Totals for a dat | Terminal & host operation | Negative | needs_client_input | p.419; p.511; p.83 | 1 | OP_TOTALS | [TD-TC-078265.json](terminal_and_host_operations/test_data/TD-TC-078265.json) |
| TC-078266 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Totals with an e | Terminal & host operation | Negative | needs_client_input | p.77; p.511; p.83 | 1 | OP_TOTALS | [TD-TC-078266.json](terminal_and_host_operations/test_data/TD-TC-078266.json) |
| TC-078267 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals Request: Totals for the c | Terminal & host operation | Negative | needs_client_input | p.419; p.522; p.237 | 1 | OP_TOTALS | [TD-TC-078267.json](terminal_and_host_operations/test_data/TD-TC-078267.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Terminal & Host Operations Totals with Proprietary Data Load Request (Prompt Code 990) @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-078268 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals with Proprietary Data Loa | Terminal & host operation | Positive | needs_client_input | p.263; p.263; p.511 | 1 | OP_TOTALS_PROPRIETARY | [TD-TC-078268.json](terminal_and_host_operations/test_data/TD-TC-078268.json) |
| TC-078269 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS Totals with Proprietary Data Loa | Terminal & host operation | Negative | needs_client_input | p.263; p.263; p.404 | 1 | OP_TOTALS_PROPRIETARY | [TD-TC-078269.json](terminal_and_host_operations/test_data/TD-TC-078269.json) |

### SC-ATL105-074DD2CC - BUYPASS ATL105 Terminal & Host Operations TransArmor Key Load Request @ General
- Status: valid; context status: None

| Test case | Technique | Polarity | Status | Rules (pages) | Step | Message | Test data file |
|---|---|---|---|---|---:|---|---|
| TC-078288 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS TransArmor Key Load Request: Tra | Terminal & host operation | Positive | needs_client_input | p.253; p.404; p.511 | 1 | OP_TRANSARMOR_KEY_LOAD | [TD-TC-078288.json](terminal_and_host_operations/test_data/TD-TC-078288.json) |
| TC-078289 BUYPASS ATL105 Terminal & Host Operations MERCHANT 01 POS TransArmor Key Load Request: Tra | Terminal & host operation | Negative | needs_client_input | p.253; p.404; p.404 | 1 | OP_TRANSARMOR_KEY_LOAD | [TD-TC-078289.json](terminal_and_host_operations/test_data/TD-TC-078289.json) |

## Reverse index - context -> scenarios

| Context | Scenarios |
|---|---:|
| Credential on File · customer present; card not present | 4 |
| General | 44 |
| General Retail · attended POS | 28 |
| General Retail · unattended CAT (awaiting SME) | 10 |
| Hospitality · attended POS | 14 |
| Hospitality · unattended CAT (awaiting SME) | 10 |
| Mail Order · mail and/or telephone order | 4 |
| Merchant category 5542 (Automated Fuel Dispensers) · unattended CAT | 5 |
| Merchant category 6051 (cryptocurrency purchase) · attended POS | 7 |
| Merchant category 6211 (high risk security) · attended POS | 7 |
| Petroleum · attended POS | 21 |
| Petroleum · unattended CAT (fuel machine) | 10 |
| Supermarket · attended POS (awaiting SME) | 28 |
| Supermarket · unattended CAT (awaiting SME) | 10 |
| Utilities · attended POS (awaiting SME) | 28 |
| Utilities · unattended CAT (awaiting SME) | 10 |

## Reverse index - rule -> test cases

| Rule (page) | Test cases | Examples |
|---|---:|---|
| BR-102-5 p.102 | 2 | TC-078057, TC-078113 |
| BR-112-2 p.112 | 7 | TC-077179, TC-078063, TC-078066, TC-078068, TC-078118 |
| BR-138-1 p.138 | 1 | TC-075968 |
| BR-138-10 p.138 | 1 | TC-075968 |
| BR-139-1 p.139 | 12 | TC-076537, TC-076549, TC-076558, TC-076568, TC-076581 |
| BR-139-3 p.139 | 4 | TC-076537, TC-076568, TC-076718, TC-076756 |
| BR-139-4 p.139 | 10 | TC-076537, TC-076558, TC-076568, TC-076584, TC-076718 |
| BR-139-6 p.139 | 4 | TC-076537, TC-076568, TC-076718, TC-076756 |
| BR-145-2 p.145 | 1 | TC-077219 |
| BR-146-2 p.146 | 2 | TC-077220, TC-077221 |
| BR-147-1 p.147 | 2 | TC-077220, TC-077221 |
| BR-148-1 p.148 | 1 | TC-077219 |
| BR-149-1 p.149 | 10 | TC-075656, TC-075757, TC-075859, TC-076077, TC-076175 |
| BR-152-1 p.152 | 10 | TC-075656, TC-075757, TC-075859, TC-076077, TC-076175 |
| BR-153-1 p.153 | 1 | TC-076326 |
| BR-154-2 p.154 | 2 | TC-077220, TC-077221 |
| BR-156-4 p.156 | 2 | TC-077220, TC-077221 |
| BR-156-7 p.156 | 7 | TC-075864, TC-076082, TC-076182, TC-076285, TC-076392 |
| BR-157-1 p.157 | 2 | TC-078061, TC-078064 |
| BR-157-5 p.157 | 1 | TC-076004 |
| BR-160-10 p.160 | 1 | TC-075970 |
| BR-160-9 p.160 | 1 | TC-075970 |
| BR-162-2 p.162 | 1 | TC-077179 |
| BR-162-5 p.162 | 1 | TC-077180 |
| BR-166-3 p.166 | 2 | TC-078058, TC-078114 |
| BR-168-4 p.168 | 2 | TC-078058, TC-078114 |
| BR-168-6 p.168 | 2 | TC-078061, TC-078064 |
| BR-171-1 p.171 | 1 | TC-075971 |
| BR-172-2 p.172 | 1 | TC-075971 |
| BR-174-1 p.174 | 2 | TC-078060, TC-078116 |
| BR-179-3 p.179 | 3 | TC-078158, TC-078159, TC-078161 |
| BR-179-5 p.179 | 1 | TC-078191 |
| BR-180-3 p.180 | 6 | TC-078191, TC-078192, TC-078194, TC-078210, TC-078211 |
| BR-181-1 p.181 | 84 | TC-075678, TC-075679, TC-075680, TC-075778, TC-075779 |
| BR-183-1 p.183 | 84 | TC-075678, TC-075679, TC-075680, TC-075778, TC-075779 |
| BR-184-1 p.184 | 2 | TC-076560, TC-076749 |
| BR-185-2 p.185 | 3 | TC-078164, TC-078165, TC-078167 |
| BR-188-3 p.188 | 1 | TC-076003 |
| BR-189-2 p.189 | 1 | TC-075972 |
| BR-189-3 p.189 | 8 | TC-075638, TC-075739, TC-075840, TC-076060, TC-076158 |
| BR-190-4 p.190 | 3 | TC-078223, TC-078224, TC-078226 |
| BR-193-1 p.193 | 3 | TC-078223, TC-078224, TC-078226 |
| BR-193-4 p.193 | 1 | TC-078223 |
| BR-195-2 p.195 | 1 | TC-078223 |
| BR-199-2 p.199 | 1 | TC-075892 |
| BR-200-1 p.200 | 1 | TC-075972 |
| BR-200-4 p.200 | 1 | TC-078191 |
| BR-204-4 p.204 | 2 | TC-078058, TC-078114 |
| BR-206-6 p.206 | 2 | TC-078061, TC-078064 |
| BR-206-7 p.206 | 1 | TC-078065 |
| BR-207-1 p.207 | 2 | TC-078062, TC-078117 |
| BR-209-3 p.209 | 7 | TC-075683, TC-075783, TC-076921, TC-076933, TC-077036 |
| BR-210-3 p.210 | 1 | TC-076000 |
| BR-217-3 p.217 | 1 | TC-075991 |
| BR-235-2 p.235 | 3 | TC-078158, TC-078159, TC-078161 |
| BR-235-7 p.235 | 84 | TC-075678, TC-075679, TC-075680, TC-075778, TC-075779 |
| BR-240-1 p.240 | 21 | TC-075699, TC-075799, TC-075930, TC-076118, TC-076219 |
| BR-240-3 p.240 | 3 | TC-078164, TC-078165, TC-078167 |
| BR-251-2 p.251 | 4 | TC-078060, TC-078069, TC-078116, TC-078122 |
| BR-251-3 p.251 | 2 | TC-078060, TC-078116 |
| BR-251-4 p.251 | 2 | TC-078060, TC-078116 |
| BR-252-1 p.252 | 1 | TC-075984 |
| BR-252-2 p.252 | 1 | TC-075984 |
| BR-252-3 p.252 | 22 | TC-075699, TC-075799, TC-075930, TC-075984, TC-076118 |
| BR-252-4 p.252 | 1 | TC-075984 |
| BR-252-6 p.252 | 1 | TC-075984 |
| BR-254-6 p.254 | 1 | TC-075988 |
| BR-257-3 p.257 | 1 | TC-075988 |
| BR-258-1 p.258 | 1 | TC-075988 |
| BR-259-1 p.259 | 1 | TC-075988 |
| BR-260-1 p.260 | 1 | TC-075988 |
| BR-260-2 p.260 | 1 | TC-075988 |
| BR-262-1 p.262 | 1 | TC-075988 |
| BR-263-2 p.263 | 3 | TC-078210, TC-078211, TC-078213 |
| BR-266-1 p.266 | 1 | TC-075981 |
| BR-266-2 p.266 | 1 | TC-075981 |
| BR-266-3 p.266 | 22 | TC-075699, TC-075799, TC-075930, TC-075981, TC-076118 |
| BR-266-5 p.266 | 1 | TC-075981 |
| BR-267-1 p.267 | 4 | TC-076392, TC-077181, TC-078061, TC-078064 |
| BR-310-1 p.310 | 2 | TC-078062, TC-078117 |
| BR-310-3 p.310 | 3 | TC-077182, TC-078062, TC-078117 |
| BR-310-4 p.310 | 2 | TC-078062, TC-078117 |
| BR-312-1 p.312 | 9 | TC-075663, TC-075763, TC-075865, TC-076083, TC-076183 |
| BR-312-2 p.312 | 21 | TC-075661, TC-075663, TC-075763, TC-075865, TC-076083 |
| BR-313-1 p.313 | 1 | TC-075870 |
| BR-313-2 p.313 | 1 | TC-075870 |
| BR-313-3 p.313 | 9 | TC-075663, TC-075763, TC-075865, TC-076083, TC-076183 |
| BR-315-4 p.315 | 17 | TC-075657, TC-075758, TC-075860, TC-076078, TC-076176 |
| BR-315-5 p.315 | 6 | TC-076387, TC-076388, TC-076435, TC-076436, TC-076437 |
| BR-315-6 p.315 | 6 | TC-076387, TC-076388, TC-076435, TC-076436, TC-076437 |
| BR-317-5 p.317 | 8 | TC-075686, TC-075786, TC-075917, TC-076105, TC-076206 |
| BR-317-9 p.317 | 8 | TC-075686, TC-075786, TC-075917, TC-076105, TC-076206 |
| BR-330-4 p.330 | 1 | TC-075876 |
| BR-339-4 p.339 | 8 | TC-075684, TC-075784, TC-075915, TC-076103, TC-076204 |
| BR-34-1 p.34 | 1 | TC-075976 |
| BR-34-2 p.34 | 1 | TC-075976 |
| BR-34-4 p.34 | 1 | TC-075976 |
| BR-34-8 p.34 | 1 | TC-078210 |
| BR-340-2 p.340 | 8 | TC-075684, TC-075784, TC-075915, TC-076103, TC-076204 |
| BR-340-3 p.340 | 8 | TC-075684, TC-075784, TC-075915, TC-076103, TC-076204 |
| BR-343-1 p.343 | 1 | TC-075958 |
| BR-343-2 p.343 | 1 | TC-075958 |
| BR-343-3 p.343 | 1 | TC-075958 |
| BR-344-1 p.344 | 1 | TC-075958 |
| BR-346-1 p.346 | 1 | TC-075958 |
| BR-346-2 p.346 | 1 | TC-075958 |
| BR-352-1 p.352 | 1 | TC-075958 |
| BR-352-2 p.352 | 1 | TC-075958 |
| BR-364-3 p.364 | 7 | TC-075658, TC-075759, TC-075861, TC-076079, TC-076177 |
| BR-364-4 p.364 | 1 | TC-075959 |
| BR-365-5 p.365 | 7 | TC-075658, TC-075759, TC-075861, TC-076079, TC-076177 |
| BR-368-5 p.368 | 30 | TC-075666, TC-075678, TC-075685, TC-075766, TC-075778 |
| BR-368-6 p.368 | 35 | TC-076593, TC-076597, TC-076601, TC-076610, TC-076614 |
| BR-368-7 p.368 | 18 | TC-075668, TC-075768, TC-075879, TC-076088, TC-076189 |
| BR-370-6 p.370 | 1 | TC-075959 |
| BR-371-2 p.371 | 1 | TC-076186 |
| BR-372-1 p.372 | 7 | TC-075658, TC-075759, TC-075861, TC-076079, TC-076177 |
| BR-374-3 p.374 | 1 | TC-075959 |
| BR-374-4 p.374 | 1 | TC-075959 |
| BR-377-2 p.377 | 1 | TC-075959 |
| BR-379-1 p.379 | 1 | TC-075959 |
| BR-380-2 p.380 | 1 | TC-075959 |
| BR-380-3 p.380 | 1 | TC-075959 |
| BR-39-1 p.39 | 38 | TC-075669, TC-075670, TC-075672, TC-075769, TC-075770 |
| BR-391-7 p.391 | 6 | TC-076558, TC-076584, TC-076718, TC-076738, TC-076756 |
| BR-395-4 p.395 | 1 | TC-078158 |
| BR-395-5 p.395 | 7 | TC-076558, TC-076584, TC-076718, TC-076738, TC-076756 |
| BR-396-3 p.396 | 84 | TC-075678, TC-075679, TC-075680, TC-075778, TC-075779 |
| BR-40-13 p.40 | 2 | TC-076180, TC-076389 |
| BR-40-2 p.40 | 142 | TC-075648, TC-075666, TC-075685, TC-075749, TC-075766 |
| BR-40-4 p.40 | 2 | TC-076180, TC-076389 |
| BR-400-4 p.400 | 1 | TC-075960 |
| BR-400-5 p.400 | 1 | TC-075960 |
| BR-401-1 p.401 | 1 | TC-075960 |
| BR-401-2 p.401 | 1 | TC-075960 |
| BR-401-3 p.401 | 1 | TC-075960 |
| BR-401-4 p.401 | 27 | TC-076586, TC-076603, TC-076620, TC-076637, TC-076653 |
| BR-401-5 p.401 | 1 | TC-075960 |
| BR-401-6 p.401 | 2 | TC-075661, TC-077027 |
| BR-401-8 p.401 | 1 | TC-075960 |
| BR-401-9 p.401 | 1 | TC-075960 |
| BR-402-1 p.402 | 45 | TC-076589, TC-076593, TC-076597, TC-076601, TC-076606 |
| BR-402-3 p.402 | 72 | TC-075647, TC-075666, TC-075678, TC-075685, TC-075748 |
| BR-403-3 p.403 | 6 | TC-076543, TC-076545, TC-076550, TC-076574, TC-076576 |
| BR-404-10 p.404 | 4 | TC-076558, TC-076584, TC-076738, TC-076771 |
| BR-404-11 p.404 | 2 | TC-076551, TC-076731 |
| BR-404-12 p.404 | 2 | TC-076552, TC-076732 |
| BR-404-3 p.404 | 29 | TC-076602, TC-076619, TC-076636, TC-076652, TC-076668 |
| BR-404-7 p.404 | 2 | TC-076553, TC-076733 |
| BR-404-8 p.404 | 2 | TC-076554, TC-076734 |
| BR-404-9 p.404 | 4 | TC-076558, TC-076584, TC-076738, TC-076771 |
| BR-409-2 p.409 | 38 | TC-075647, TC-075748, TC-075850, TC-076068, TC-076166 |
| BR-409-4 p.409 | 8 | TC-075671, TC-075771, TC-075882, TC-076091, TC-076192 |
| BR-416-2 p.416 | 1 | TC-075961 |
| BR-416-3 p.416 | 1 | TC-075961 |
| BR-416-4 p.416 | 1 | TC-075961 |
| BR-428-8 p.428 | 8 | TC-075610, TC-075711, TC-075811, TC-076032, TC-076130 |
| BR-43-2 p.43 | 10 | TC-075672, TC-075772, TC-075883, TC-076092, TC-076193 |
| BR-437-10 p.437 | 1 | TC-075961 |
| BR-44-1 p.44 | 12 | TC-075672, TC-075772, TC-075883, TC-076092, TC-076180 |
| BR-44-2 p.44 | 1 | TC-075977 |
| BR-440-3 p.440 | 1 | TC-077177 |
| BR-45-2 p.45 | 38 | TC-075647, TC-075748, TC-075850, TC-076068, TC-076166 |
| BR-45-5 p.45 | 2 | TC-076180, TC-076389 |
| BR-45-7 p.45 | 1 | TC-075977 |
| BR-460-3 p.460 | 1 | TC-075961 |
| BR-466-3 p.466 | 1 | TC-077183 |
| BR-468-1 p.468 | 1 | TC-075961 |
| BR-468-2 p.468 | 1 | TC-075961 |
| BR-469-1 p.469 | 1 | TC-075961 |
| BR-469-3 p.469 | 3 | TC-078191, TC-078192, TC-078194 |
| BR-47-1 p.47 | 8 | TC-075673, TC-075773, TC-075884, TC-076093, TC-076194 |
| BR-47-3 p.47 | 27 | TC-076602, TC-076619, TC-076636, TC-076652, TC-076668 |
| BR-47-6 p.47 | 2 | TC-075661, TC-077027 |
| BR-47-7 p.47 | 1 | TC-075897 |
| BR-47-8 p.47 | 1 | TC-075977 |
| BR-47-9 p.47 | 2 | TC-076180, TC-076389 |
| BR-470-1 p.470 | 1 | TC-075963 |
| BR-470-4 p.470 | 1 | TC-075963 |
| BR-471-1 p.471 | 1 | TC-075963 |
| BR-471-2 p.471 | 1 | TC-075963 |
| BR-474-1 p.474 | 1 | TC-075963 |
| BR-475-1 p.475 | 1 | TC-075963 |
| BR-475-2 p.475 | 1 | TC-075963 |
| BR-475-3 p.475 | 1 | TC-075963 |
| BR-475-6 p.475 | 4 | TC-076537, TC-076568, TC-076718, TC-076756 |
| BR-476-2 p.476 | 1 | TC-075983 |
| BR-476-3 p.476 | 1 | TC-075983 |
| BR-48-3 p.48 | 1 | TC-075995 |
| BR-481-3 p.481 | 1 | TC-075983 |
| BR-483-3 p.483 | 2 | TC-075661, TC-077027 |
| BR-484-3 p.484 | 1 | TC-075983 |
| BR-486-4 p.486 | 1 | TC-077184 |
| BR-49-4 p.49 | 1 | TC-075875 |
| BR-49-5 p.49 | 1 | TC-075902 |
| BR-503-1 p.503 | 2 | TC-076017, TC-077185 |
| BR-506-4 p.506 | 47 | TC-075600, TC-075701, TC-075801, TC-076022, TC-076120 |
| BR-518-5 p.518 | 1 | TC-075987 |
| BR-518-6 p.518 | 1 | TC-075987 |
| BR-519-1 p.519 | 1 | TC-075987 |
| BR-520-2 p.520 | 2 | TC-076537, TC-076568 |
| BR-521-1 p.521 | 1 | TC-075987 |
| BR-522-1 p.522 | 1 | TC-075987 |
| BR-522-2 p.522 | 1 | TC-075987 |
| BR-53-2 p.53 | 7 | TC-075658, TC-075759, TC-075861, TC-076079, TC-076177 |
| BR-545-1 p.545 | 5 | TC-075661, TC-077027, TC-077179, TC-078063, TC-078118 |
| BR-545-2 p.545 | 3 | TC-077179, TC-078063, TC-078118 |
| BR-545-5 p.545 | 25 | TC-075691, TC-075791, TC-075922, TC-075932, TC-075934 |
| BR-545-6 p.545 | 1 | TC-075993 |
| BR-547-1 p.547 | 1 | TC-075974 |
| BR-548-1 p.548 | 1 | TC-075974 |
| BR-552-2 p.552 | 8 | TC-075694, TC-075794, TC-075925, TC-076113, TC-076214 |
| BR-552-6 p.552 | 8 | TC-075694, TC-075794, TC-075925, TC-076113, TC-076214 |
| BR-554-4 p.554 | 10 | TC-075657, TC-075758, TC-075860, TC-076078, TC-076176 |
| BR-555-3 p.555 | 1 | TC-076326 |
| BR-559-1 p.559 | 1 | TC-076390 |
| BR-56-5 p.56 | 1 | TC-076433 |
| BR-56-8 p.56 | 7 | TC-075658, TC-075759, TC-075861, TC-076079, TC-076177 |
| BR-561-1 p.561 | 1 | TC-076332 |
| BR-561-2 p.561 | 8 | TC-075604, TC-075705, TC-075805, TC-076026, TC-076124 |
| BR-562-2 p.562 | 8 | TC-075606, TC-075707, TC-075807, TC-076028, TC-076126 |
| BR-562-3 p.562 | 8 | TC-075694, TC-075794, TC-075925, TC-076113, TC-076214 |
| BR-564-1 p.564 | 8 | TC-075607, TC-075708, TC-075808, TC-076029, TC-076127 |
| BR-569-1 p.569 | 20 | TC-075609, TC-075710, TC-075810, TC-076031, TC-076129 |
| BR-572-1 p.572 | 6 | TC-076387, TC-076388, TC-076435, TC-076436, TC-076437 |
| BR-572-3 p.572 | 8 | TC-075602, TC-075703, TC-075803, TC-076024, TC-076122 |
| BR-575-1 p.575 | 2 | TC-078059, TC-078115 |
| BR-575-4 p.575 | 2 | TC-078059, TC-078115 |
| BR-575-5 p.575 | 2 | TC-078059, TC-078115 |
| BR-577-1 p.577 | 8 | TC-075642, TC-075743, TC-075845, TC-076063, TC-076161 |
| BR-577-2 p.577 | 8 | TC-075643, TC-075744, TC-075846, TC-076064, TC-076162 |
| BR-577-3 p.577 | 1 | TC-075904 |
| BR-577-5 p.577 | 1 | TC-076007 |
| BR-581-1 p.581 | 8 | TC-075610, TC-075711, TC-075811, TC-076032, TC-076130 |
| BR-581-2 p.581 | 17 | TC-075610, TC-075711, TC-075811, TC-076032, TC-076130 |
| BR-583-1 p.583 | 13 | TC-075613, TC-075714, TC-075814, TC-076035, TC-076133 |
| BR-583-2 p.583 | 13 | TC-075613, TC-075714, TC-075814, TC-076035, TC-076133 |
| BR-583-3 p.583 | 21 | TC-075613, TC-075614, TC-075714, TC-075715, TC-075814 |
| BR-588-1 p.588 | 1 | TC-075899 |
| BR-59-2 p.59 | 1 | TC-076002 |
| BR-590-3 p.590 | 2 | TC-076396, TC-076397 |
| BR-590-4 p.590 | 8 | TC-075617, TC-075718, TC-075818, TC-076039, TC-076137 |
| BR-592-2 p.592 | 17 | TC-075932, TC-075934, TC-075935, TC-075936, TC-075937 |
| BR-594-4 p.594 | 2 | TC-076396, TC-076397 |
| BR-597-1 p.597 | 8 | TC-075644, TC-075745, TC-075847, TC-076065, TC-076163 |
| BR-600-2 p.600 | 16 | TC-075619, TC-075620, TC-075720, TC-075721, TC-075820 |
| BR-600-3 p.600 | 8 | TC-075620, TC-075721, TC-075821, TC-076042, TC-076140 |
| BR-602-1 p.602 | 16 | TC-075622, TC-075697, TC-075723, TC-075797, TC-075823 |
| BR-607-3 p.607 | 13 | TC-075689, TC-075789, TC-075920, TC-076108, TC-076209 |
| BR-61-2 p.61 | 11 | TC-076390, TC-077031, TC-077032, TC-077045, TC-077046 |
| BR-61-5 p.61 | 1 | TC-077076 |
| BR-610-3 p.610 | 2 | TC-078059, TC-078115 |
| BR-614-4 p.614 | 8 | TC-075625, TC-075726, TC-075826, TC-076047, TC-076145 |
| BR-614-8 p.614 | 8 | TC-075625, TC-075726, TC-075826, TC-076047, TC-076145 |
| BR-616-4 p.616 | 2 | TC-076179, TC-076892 |
| BR-616-6 p.616 | 2 | TC-076179, TC-076892 |
| BR-62-1 p.62 | 1 | TC-077076 |
| BR-62-10 p.62 | 1 | TC-077076 |
| BR-62-6 p.62 | 13 | TC-075689, TC-075789, TC-075920, TC-076108, TC-076209 |
| BR-62-7 p.62 | 7 | TC-077179, TC-078063, TC-078066, TC-078068, TC-078118 |
| BR-620-2 p.620 | 8 | TC-075627, TC-075728, TC-075828, TC-076049, TC-076147 |
| BR-622-1 p.622 | 17 | TC-075657, TC-075758, TC-075860, TC-076078, TC-076176 |
| BR-622-2 p.622 | 6 | TC-076387, TC-076388, TC-076435, TC-076436, TC-076437 |
| BR-622-5 p.622 | 8 | TC-075628, TC-075729, TC-075829, TC-076050, TC-076148 |
| BR-623-1 p.623 | 8 | TC-075628, TC-075729, TC-075829, TC-076050, TC-076148 |
| BR-623-5 p.623 | 8 | TC-075629, TC-075730, TC-075830, TC-076051, TC-076149 |
| BR-625-2 p.625 | 8 | TC-075657, TC-075758, TC-075860, TC-076078, TC-076176 |
| BR-625-3 p.625 | 8 | TC-075657, TC-075758, TC-075860, TC-076078, TC-076176 |
| BR-625-4 p.625 | 6 | TC-076387, TC-076388, TC-076435, TC-076436, TC-076437 |
| BR-625-5 p.625 | 4 | TC-076435, TC-076436, TC-076437, TC-076438 |
| BR-628-2 p.628 | 8 | TC-075632, TC-075733, TC-075833, TC-076054, TC-076152 |
| BR-629-1 p.629 | 8 | TC-075634, TC-075735, TC-075835, TC-076056, TC-076154 |
| BR-63-2 p.63 | 10 | TC-077033, TC-077034, TC-077047, TC-077048, TC-077064 |
| BR-63-4 p.63 | 2 | TC-075661, TC-077027 |
| BR-63-6 p.63 | 1 | TC-077066 |
| BR-63-7 p.63 | 1 | TC-077076 |
| BR-632-2 p.632 | 8 | TC-075657, TC-075758, TC-075860, TC-076078, TC-076176 |
| BR-637-2 p.637 | 10 | TC-075626, TC-075727, TC-075827, TC-076048, TC-076146 |
| BR-639-2 p.639 | 1 | TC-077186 |
| BR-639-6 p.639 | 1 | TC-075872 |
| BR-639-7 p.639 | 1 | TC-075872 |
| BR-64-1 p.64 | 101 | TC-075600, TC-075690, TC-075701, TC-075790, TC-075801 |
| BR-64-6 p.64 | 1 | TC-076888 |
| BR-648-11 p.648 | 1 | TC-076432 |
| BR-648-12 p.648 | 1 | TC-076432 |
| BR-648-14 p.648 | 1 | TC-076432 |
| BR-650-4 p.650 | 8 | TC-075681, TC-075781, TC-075913, TC-076101, TC-076202 |
| BR-656-1 p.656 | 8 | TC-075694, TC-075794, TC-075925, TC-076113, TC-076214 |
| BR-656-3 p.656 | 10 | TC-075693, TC-075793, TC-075910, TC-075924, TC-076112 |
| BR-657-2 p.657 | 3 | TC-076519, TC-077001, TC-077106 |
| BR-657-5 p.657 | 3 | TC-076519, TC-077001, TC-077106 |
| BR-658-1 p.658 | 1 | TC-075994 |
| BR-66-1 p.66 | 1 | TC-075978 |
| BR-66-2 p.66 | 27 | TC-076586, TC-076603, TC-076620, TC-076637, TC-076653 |
| BR-66-3 p.66 | 27 | TC-076586, TC-076603, TC-076620, TC-076637, TC-076653 |
| BR-66-4 p.66 | 1 | TC-075978 |
| BR-66-5 p.66 | 1 | TC-075978 |
| BR-663-1 p.663 | 25 | TC-075691, TC-075791, TC-075922, TC-075932, TC-075934 |
| BR-663-2 p.663 | 25 | TC-075691, TC-075791, TC-075922, TC-075932, TC-075934 |
| BR-663-4 p.663 | 25 | TC-075691, TC-075791, TC-075922, TC-075932, TC-075934 |
| BR-664-4 p.664 | 25 | TC-075691, TC-075791, TC-075922, TC-075932, TC-075934 |
| BR-669-3 p.669 | 8 | TC-075700, TC-075800, TC-075931, TC-076119, TC-076220 |
| BR-669-4 p.669 | 8 | TC-075700, TC-075800, TC-075931, TC-076119, TC-076220 |
| BR-671-6 p.671 | 10 | TC-075692, TC-075792, TC-075923, TC-075933, TC-075950 |
| BR-688-2 p.688 | 7 | TC-075864, TC-076082, TC-076182, TC-076285, TC-076392 |
| BR-701-3 p.701 | 1 | TC-075996 |
| BR-71-1 p.71 | 1 | TC-075985 |
| BR-712-2 p.712 | 8 | TC-075610, TC-075711, TC-075811, TC-076032, TC-076130 |
| BR-713-12 p.713 | 1 | TC-076390 |
| BR-713-9 p.713 | 1 | TC-076421 |
| BR-714-8 p.714 | 1 | TC-076390 |
| BR-715-19 p.715 | 1 | TC-077219 |
| BR-718-3 p.718 | 1 | TC-075895 |
| BR-718-6 p.718 | 1 | TC-075898 |
| BR-72-1 p.72 | 1 | TC-078210 |
| BR-72-2 p.72 | 1 | TC-076633 |
| BR-72-3 p.72 | 1 | TC-075985 |
| BR-72-4 p.72 | 80 | TC-075600, TC-075690, TC-075701, TC-075790, TC-075801 |
| BR-72-6 p.72 | 33 | TC-076773, TC-076780, TC-076781, TC-076783, TC-076784 |
| BR-728-1 p.728 | 2 | TC-076391, TC-077188 |
| BR-728-2 p.728 | 1 | TC-076355 |
| BR-730-1 p.730 | 2 | TC-076179, TC-076892 |
| BR-730-4 p.730 | 2 | TC-076179, TC-076892 |
| BR-730-5 p.730 | 2 | TC-076179, TC-076892 |
| BR-76-1 p.76 | 1 | TC-075985 |
| BR-76-2 p.76 | 1 | TC-075985 |
| BR-76-3 p.76 | 1 | TC-075985 |
| BR-81-10 p.81 | 1 | TC-075965 |
| BR-81-11 p.81 | 1 | TC-075965 |
| BR-81-12 p.81 | 1 | TC-075965 |
| BR-81-2 p.81 | 1 | TC-075965 |
| BR-81-3 p.81 | 1 | TC-076186 |
| BR-81-7 p.81 | 3 | TC-078175, TC-078176, TC-078178 |
| BR-81-9 p.81 | 1 | TC-075965 |
| BR-83-1 p.83 | 20 | TC-076323, TC-076412, TC-076422, TC-076567, TC-076577 |
| BR-83-2 p.83 | 128 | TC-075600, TC-075660, TC-075680, TC-075690, TC-075701 |
| BR-84-3 p.84 | 54 | TC-075666, TC-075668, TC-075685, TC-075766, TC-075768 |
| BR-84-4 p.84 | 40 | TC-075667, TC-075679, TC-075687, TC-075767, TC-075779 |
| BR-84-5 p.84 | 117 | TC-075600, TC-075680, TC-075690, TC-075701, TC-075780 |
| BR-84-8 p.84 | 2 | TC-076180, TC-076389 |
| BR-85-11 p.85 | 27 | TC-076586, TC-076603, TC-076620, TC-076637, TC-076653 |
| BR-85-2 p.85 | 63 | TC-075674, TC-075675, TC-075676, TC-075677, TC-075774 |
| BR-85-3 p.85 | 4 | TC-076406, TC-076407, TC-076408, TC-076409 |
| BR-85-4 p.85 | 8 | TC-075604, TC-075705, TC-075805, TC-076026, TC-076124 |
| BR-87-3 p.87 | 18 | TC-075668, TC-075768, TC-075879, TC-076088, TC-076189 |
| BR-89-10 p.89 | 2 | TC-077176, TC-077177 |
| BR-89-3 p.89 | 13 | TC-075664, TC-075764, TC-075866, TC-076084, TC-076184 |
| BR-89-5 p.89 | 2 | TC-077176, TC-077177 |
| BR-90-1 p.90 | 1 | TC-075965 |
| BR-90-2 p.90 | 1 | TC-075965 |
| BR-90-3 p.90 | 1 | TC-075965 |
| BR-91-2 p.91 | 1 | TC-076433 |
| BR-91-5 p.91 | 1 | TC-075966 |
| BR-91-6 p.91 | 1 | TC-075966 |
| BR-91-9 p.91 | 1 | TC-075966 |
| BR-92-10 p.92 | 1 | TC-075966 |
| BR-92-11 p.92 | 8 | TC-075603, TC-075704, TC-075804, TC-076025, TC-076123 |
| BR-92-2 p.92 | 1 | TC-075966 |
| BR-92-4 p.92 | 1 | TC-075966 |
| BR-92-5 p.92 | 1 | TC-075966 |
| BR-92-6 p.92 | 1 | TC-075966 |
| BR-93-2 p.93 | 1 | TC-075986 |
| BR-93-3 p.93 | 1 | TC-075986 |
| BR-93-4 p.93 | 1 | TC-075986 |
| BR-94-2 p.94 | 1 | TC-075986 |
| BR-94-3 p.94 | 1 | TC-075986 |
| BR-94-5 p.94 | 13 | TC-075689, TC-075789, TC-075920, TC-076108, TC-076209 |
| BR-94-6 p.94 | 1 | TC-076186 |
| BR-95-3 p.95 | 1 | TC-076186 |
| BR-ATL105-TRANSARMOR-12-2 p.12 | 1 | TC-076005 |
| BR-ATL105-TRANSARMOR-12-3 p.12 | 1 | TC-077189 |
| BR-ATL105-TRANSARMOR-13-6 p.13 | 1 | TC-076186 |
| BR-ATL105-TRANSARMOR-13-7 p.13 | 16 | TC-075651, TC-075653, TC-075752, TC-075754, TC-075854 |
| BR-ATL105-TRANSARMOR-14-2 p.14 | 1 | TC-075911 |
| BR-ATL105-TRANSARMOR-16-6 p.16 | 1 | TC-076019 |
| BR-ATL105-TRANSARMOR-17-7 p.17 | 1 | TC-077189 |
| BR-ATL105-TRANSARMOR-19-1 p.19 | 1 | TC-076013 |
| BR-ATL105-TRANSARMOR-20-2 p.20 | 1 | TC-075998 |
| BR-ATL105-TRANSARMOR-21-1 p.21 | 31 | TC-076773, TC-076780, TC-076783, TC-076784, TC-076791 |
| BR-ATL105-TRANSARMOR-23-3 p.23 | 1 | TC-076001 |
| BR-ATL105-TRANSARMOR-26-1 p.26 | 1 | TC-076014 |
| BR-ATL105-TRANSARMOR-27-1 p.27 | 1 | TC-075973 |
| BR-ATL105-TRANSARMOR-27-2 p.27 | 1 | TC-075973 |
| BR-ATL105-TRANSARMOR-28-3 p.28 | 1 | TC-077190 |
| BR-ATL105-TRANSARMOR-28-4 p.28 | 8 | TC-075665, TC-075765, TC-075867, TC-076085, TC-076185 |
| BR-ATL105-TRANSARMOR-32-2 p.32 | 1 | TC-075997 |
| BR-ATL105-TRANSARMOR-33-2 p.33 | 1 | TC-076012 |
| BR-ATL105-TRANSARMOR-35-9 p.35 | 1 | TC-076018 |
| BR-ATL105-TRANSARMOR-40-5 p.40 | 1 | TC-076016 |
| BR-ATL105-TRANSARMOR-40-7 p.40 | 1 | TC-077180 |
| BR-ATL105-TRANSARMOR-41-1 p.41 | 3 | TC-078175, TC-078176, TC-078178 |
| BR-ATL105-TRANSARMOR-41-2 p.41 | 1 | TC-078175 |
| BR-ATL105-TRANSARMOR-46-5 p.46 | 1 | TC-077191 |
| BR-ATL105-TRANSARMOR-46-6 p.46 | 2 | TC-076008, TC-077189 |
| BR-ATL105-TRANSARMOR-47-5 p.47 | 9 | TC-075663, TC-075763, TC-075865, TC-076083, TC-076183 |
| BR-ATL105-TRANSARMOR-48-5 p.48 | 1 | TC-077191 |
| BR-ATL105-TRANSARMOR-49-5 p.49 | 1 | TC-077191 |
| BR-ATL105-TRANSARMOR-52-3 p.52 | 12 | TC-075699, TC-075799, TC-075930, TC-076118, TC-076219 |
| BR-ATL105-TRANSARMOR-52-4 p.52 | 12 | TC-075699, TC-075799, TC-075930, TC-076118, TC-076219 |
| BR-ATL105-TRANSARMOR-54-3 p.54 | 1 | TC-077191 |
| BR-ATL105-TRANSARMOR-57-10 p.57 | 13 | TC-075689, TC-075789, TC-075920, TC-076108, TC-076209 |
| BR-ATL105-TRANSARMOR-57-4 p.57 | 1 | TC-075989 |
| BR-ATL105-TRANSARMOR-57-6 p.57 | 1 | TC-076186 |
| BR-ATL105-TRANSARMOR-62-1 p.62 | 1 | TC-075989 |
| BR-ATL105-TRANSARMOR-62-2 p.62 | 1 | TC-075989 |
| BR-ATL105-TRANSARMOR-62-5 p.62 | 1 | TC-075989 |
| BR-ATL105-TRANSARMOR-62-7 p.62 | 1 | TC-075989 |
| BR-ATL105-TRANSARMOR-66-1 p.66 | 1 | TC-078175 |
| BR-ATL105-TRANSARMOR-66-5 p.66 | 1 | TC-078158 |
| BR-ATL105-TRANSARMOR-67-1 p.67 | 3 | TC-076555, TC-076735, TC-078164 |
| BR-ATL105-TRANSARMOR-67-4 p.67 | 1 | TC-075896 |
| BR-ATL105-TRANSARMOR-76-3 p.76 | 8 | TC-075671, TC-075771, TC-075882, TC-076091, TC-076192 |
| BR-ATL105-TRANSARMOR-76-5 p.76 | 6 | TC-076686, TC-076698, TC-076701, TC-076757, TC-076768 |
| BR-ATL105-TRANSARMOR-77-2 p.77 | 1 | TC-075989 |
| BR-ATL105-TRANSARMOR-77-3 p.77 | 1 | TC-075989 |
| BR-BUYPASS-DCT-11-1 p.11 | 1 | TC-075979 |
| BR-BUYPASS-DCT-11-2 p.11 | 1 | TC-075979 |
| BR-BUYPASS-DCT-18-2 p.18 | 1 | TC-075957 |
| BR-BUYPASS-DCT-18-4 p.18 | 1 | TC-075957 |
| BR-BUYPASS-DCT-21-2 p.21 | 1 | TC-075957 |
| BR-BUYPASS-DCT-21-3 p.21 | 1 | TC-075957 |
| BR-BUYPASS-DCT-21-4 p.21 | 1 | TC-075957 |
| BR-BUYPASS-DCT-25-1 p.25 | 1 | TC-075957 |
| BR-BUYPASS-DCT-25-2 p.25 | 1 | TC-075957 |
| BR-BUYPASS-DCT-28-2 p.28 | 1 | TC-075957 |
| BR-BUYPASS-DCT-39-3 p.39 | 1 | TC-075990 |
| BR-BUYPASS-DCT-39-4 p.39 | 1 | TC-075990 |
| BR-BUYPASS-DCT-39-5 p.39 | 1 | TC-075990 |
| BR-BUYPASS-DCT-39-6 p.39 | 1 | TC-075990 |
| BR-BUYPASS-DCT-41-1 p.41 | 1 | TC-075990 |
| BR-BUYPASS-DCT-41-2 p.41 | 1 | TC-075990 |
| BR-BUYPASS-DCT-41-3 p.41 | 1 | TC-075990 |
| BR-BUYPASS-DCT-43-1 p.43 | 1 | TC-075990 |
| BR-BUYPASS-DCT-44-8 p.44 | 1 | TC-075992 |
| BR-BUYPASS-DCT-8-3 p.8 | 1 | TC-075979 |
| BR-BUYPASS-IIN-4-3 p.4 | 1 | TC-076015 |
| BR-BUYPASS-MTAP-44-2 p.44 | 1 | TC-076006 |
| BR-BUYPASS-PIPS-130-1 p.130 | 12 | TC-076923, TC-076935, TC-076949, TC-076969, TC-076980 |
| BR-BUYPASS-PIPS-130-3 p.130 | 2 | TC-077193, TC-077194 |
| BR-BUYPASS-PIPS-131-10 p.131 | 2 | TC-076952, TC-077074 |
| BR-BUYPASS-PIPS-131-11 p.131 | 2 | TC-076952, TC-077074 |
| BR-BUYPASS-PIPS-131-2 p.131 | 2 | TC-077001, TC-077106 |
| BR-BUYPASS-PIPS-131-4 p.131 | 1 | TC-077195 |
| BR-BUYPASS-PIPS-131-6 p.131 | 2 | TC-076952, TC-077074 |
| BR-BUYPASS-PIPS-131-9 p.131 | 2 | TC-076952, TC-077074 |
| BR-BUYPASS-PIPS-135-1 p.135 | 1 | TC-077195 |
| BR-BUYPASS-PIPS-136-1 p.136 | 2 | TC-076938, TC-077056 |
| BR-BUYPASS-PIPS-140-1 p.140 | 2 | TC-077193, TC-077194 |
| BR-BUYPASS-PIPS-140-6 p.140 | 2 | TC-076956, TC-077079 |
| BR-BUYPASS-PIPS-16-10 p.16 | 2 | TC-076954, TC-077077 |
| BR-BUYPASS-PIPS-16-11 p.16 | 2 | TC-076954, TC-077077 |
| BR-BUYPASS-PIPS-16-3 p.16 | 2 | TC-077203, TC-077204 |
| BR-BUYPASS-PIPS-16-5 p.16 | 2 | TC-076954, TC-077077 |
| BR-BUYPASS-PIPS-16-6 p.16 | 2 | TC-076954, TC-077077 |
| BR-BUYPASS-PIPS-16-8 p.16 | 2 | TC-076954, TC-077077 |
| BR-BUYPASS-PIPS-16-9 p.16 | 2 | TC-076954, TC-077077 |
| BR-BUYPASS-PIPS-17-1 p.17 | 2 | TC-076957, TC-077080 |
| BR-BUYPASS-PIPS-17-2 p.17 | 24 | TC-076916, TC-076917, TC-076928, TC-076929, TC-076943 |
| BR-BUYPASS-PIPS-173-1 p.173 | 2 | TC-076955, TC-077078 |
| BR-BUYPASS-PIPS-174-1 p.174 | 2 | TC-076955, TC-077078 |
| BR-BUYPASS-PIPS-174-2 p.174 | 2 | TC-076955, TC-077078 |
| BR-BUYPASS-PIPS-175-3 p.175 | 2 | TC-076955, TC-077078 |
| BR-BUYPASS-PIPS-179-1 p.179 | 2 | TC-077196, TC-077197 |
| BR-BUYPASS-PIPS-179-2 p.179 | 2 | TC-076955, TC-077078 |
| BR-BUYPASS-PIPS-18-4 p.18 | 2 | TC-076939, TC-077057 |
| BR-BUYPASS-PIPS-180-1 p.180 | 2 | TC-076955, TC-077078 |
| BR-BUYPASS-PIPS-181-2 p.181 | 3 | TC-077198, TC-077199, TC-077200 |
| BR-BUYPASS-PIPS-182-3 p.182 | 12 | TC-076922, TC-076934, TC-076948, TC-076968, TC-076979 |
| BR-BUYPASS-PIPS-183-1 p.183 | 2 | TC-076955, TC-077078 |
| BR-BUYPASS-PIPS-183-2 p.183 | 2 | TC-076955, TC-077078 |
| BR-BUYPASS-PIPS-185-2 p.185 | 1 | TC-077201 |
| BR-BUYPASS-PIPS-185-5 p.185 | 12 | TC-076913, TC-076925, TC-076937, TC-076960, TC-076971 |
| BR-BUYPASS-PIPS-186-1 p.186 | 1 | TC-077202 |
| BR-BUYPASS-PIPS-21-2 p.21 | 24 | TC-076918, TC-076919, TC-076930, TC-076931, TC-076945 |
| BR-BUYPASS-PIPS-23-6 p.23 | 12 | TC-077004, TC-077006, TC-077008, TC-077010, TC-077012 |
| BR-BUYPASS-PIPS-24-2 p.24 | 12 | TC-076923, TC-076935, TC-076949, TC-076969, TC-076980 |
| BR-BUYPASS-PIPS-24-3 p.24 | 24 | TC-076923, TC-076935, TC-076949, TC-076969, TC-076980 |
| BR-BUYPASS-PIPS-24-6 p.24 | 12 | TC-077004, TC-077006, TC-077008, TC-077010, TC-077012 |
| BR-BUYPASS-PIPS-24-7 p.24 | 24 | TC-076912, TC-076924, TC-076936, TC-076959, TC-076970 |
| BR-BUYPASS-PIPS-25-1 p.25 | 1 | TC-077192 |
| BR-BUYPASS-PIPS-27-10 p.27 | 12 | TC-077005, TC-077007, TC-077009, TC-077011, TC-077013 |
| BR-BUYPASS-PIPS-27-11 p.27 | 1 | TC-077071 |
| BR-BUYPASS-PIPS-27-4 p.27 | 1 | TC-077071 |
| BR-BUYPASS-PIPS-28-6 p.28 | 1 | TC-077081 |
| BR-BUYPASS-PIPS-30-3 p.30 | 14 | TC-076914, TC-076915, TC-076926, TC-076927, TC-076941 |
| BR-BUYPASS-PIPS-33-1 p.33 | 2 | TC-076950, TC-077072 |
| BR-BUYPASS-PIPS-33-4 p.33 | 2 | TC-076950, TC-077072 |
| BR-BUYPASS-PIPS-34-2 p.34 | 2 | TC-076951, TC-077073 |
| BR-BUYPASS-PIPS-34-6 p.34 | 2 | TC-076951, TC-077073 |
| BR-BUYPASS-PIPS-35-2 p.35 | 2 | TC-076953, TC-077075 |
| BR-BUYPASS-PIPS-35-3 p.35 | 2 | TC-076953, TC-077075 |
| BR-BUYPASS-PIPS-35-4 p.35 | 2 | TC-076953, TC-077075 |
| BR-BUYPASS-PIPS-35-5 p.35 | 2 | TC-076953, TC-077075 |
| BR-FISERV-EMV-GUIDE-100-2 p.100 | 1 | TC-076426 |
| BR-FISERV-EMV-GUIDE-100-6 p.100 | 1 | TC-075953 |
| BR-FISERV-EMV-GUIDE-100-7 p.100 | 1 | TC-075953 |
| BR-FISERV-EMV-GUIDE-100-8 p.100 | 1 | TC-075953 |
| BR-FISERV-EMV-GUIDE-100-9 p.100 | 1 | TC-075953 |
| BR-FISERV-EMV-GUIDE-101-10 p.101 | 1 | TC-076426 |
| BR-FISERV-EMV-GUIDE-101-11 p.101 | 1 | TC-075953 |
| BR-FISERV-EMV-GUIDE-101-3 p.101 | 1 | TC-075953 |
| BR-FISERV-EMV-GUIDE-101-4 p.101 | 1 | TC-075953 |
| BR-FISERV-EMV-GUIDE-101-5 p.101 | 1 | TC-075953 |
| BR-FISERV-EMV-GUIDE-102-1 p.102 | 1 | TC-075954 |
| BR-FISERV-EMV-GUIDE-102-2 p.102 | 1 | TC-075954 |
| BR-FISERV-EMV-GUIDE-102-4 p.102 | 1 | TC-075954 |
| BR-FISERV-EMV-GUIDE-102-5 p.102 | 1 | TC-075954 |
| BR-FISERV-EMV-GUIDE-103-3 p.103 | 1 | TC-075954 |
| BR-FISERV-EMV-GUIDE-103-4 p.103 | 1 | TC-075954 |
| BR-FISERV-EMV-GUIDE-103-5 p.103 | 1 | TC-075954 |
| BR-FISERV-EMV-GUIDE-104-1 p.104 | 1 | TC-075954 |
| BR-FISERV-EMV-GUIDE-107-5 p.107 | 1 | TC-075980 |
| BR-FISERV-EMV-GUIDE-107-7 p.107 | 1 | TC-075980 |
| BR-FISERV-EMV-GUIDE-108-1 p.108 | 1 | TC-075980 |
| BR-FISERV-EMV-GUIDE-108-2 p.108 | 1 | TC-075980 |
| BR-FISERV-EMV-GUIDE-110-1 p.110 | 1 | TC-075868 |
| BR-FISERV-EMV-GUIDE-111-1 p.111 | 1 | TC-075868 |
| BR-FISERV-EMV-GUIDE-111-2 p.111 | 1 | TC-075868 |
| BR-FISERV-EMV-GUIDE-111-3 p.111 | 1 | TC-075868 |
| BR-FISERV-EMV-GUIDE-112-4 p.112 | 1 | TC-076326 |
| BR-FISERV-EMV-GUIDE-113-1 p.113 | 1 | TC-076326 |
| BR-FISERV-EMV-GUIDE-115-10 p.115 | 1 | TC-075868 |
| BR-FISERV-EMV-GUIDE-115-13 p.115 | 1 | TC-075868 |
| BR-FISERV-EMV-GUIDE-115-16 p.115 | 1 | TC-075868 |
| BR-FISERV-EMV-GUIDE-115-17 p.115 | 1 | TC-075868 |
| BR-FISERV-EMV-GUIDE-115-20 p.115 | 8 | TC-075641, TC-075742, TC-075842, TC-076062, TC-076160 |
| BR-FISERV-EMV-GUIDE-115-21 p.115 | 1 | TC-076426 |
| BR-FISERV-EMV-GUIDE-115-27 p.115 | 1 | TC-075869 |
| BR-FISERV-EMV-GUIDE-115-6 p.115 | 1 | TC-075869 |
| BR-FISERV-EMV-GUIDE-120-11 p.120 | 1 | TC-075873 |
| BR-FISERV-EMV-GUIDE-120-3 p.120 | 47 | TC-075600, TC-075701, TC-075801, TC-076022, TC-076120 |
| BR-FISERV-EMV-GUIDE-125-1 p.125 | 1 | TC-075912 |
| BR-FISERV-EMV-GUIDE-126-1 p.126 | 8 | TC-075691, TC-075791, TC-075922, TC-076110, TC-076211 |
| BR-FISERV-EMV-GUIDE-130-1 p.130 | 1 | TC-075893 |
| BR-FISERV-EMV-GUIDE-135-1 p.135 | 1 | TC-075894 |
| BR-FISERV-EMV-GUIDE-138-2 p.138 | 20 | TC-075688, TC-075788, TC-075919, TC-076107, TC-076208 |
| BR-FISERV-EMV-GUIDE-138-4 p.138 | 20 | TC-075688, TC-075788, TC-075919, TC-076107, TC-076208 |
| BR-FISERV-EMV-GUIDE-141-3 p.141 | 1 | TC-075969 |
| BR-FISERV-EMV-GUIDE-141-4 p.141 | 20 | TC-075688, TC-075788, TC-075919, TC-076107, TC-076208 |
| BR-FISERV-EMV-GUIDE-142-2 p.142 | 1 | TC-075969 |
| BR-FISERV-EMV-GUIDE-142-3 p.142 | 3 | TC-075844, TC-076264, TC-077222 |
| BR-FISERV-EMV-GUIDE-143-4 p.143 | 1 | TC-077208 |
| BR-FISERV-EMV-GUIDE-146-3 p.146 | 8 | TC-075682, TC-075782, TC-075914, TC-076102, TC-076203 |
| BR-FISERV-EMV-GUIDE-152-5 p.152 | 7 | TC-075660, TC-075761, TC-075862, TC-076080, TC-076178 |
| BR-FISERV-EMV-GUIDE-156-1 p.156 | 7 | TC-075660, TC-075761, TC-075862, TC-076080, TC-076178 |
| BR-FISERV-EMV-GUIDE-156-8 p.156 | 9 | TC-075654, TC-075755, TC-075857, TC-076075, TC-076173 |
| BR-FISERV-EMV-GUIDE-157-10 p.157 | 1 | TC-077218 |
| BR-FISERV-EMV-GUIDE-157-11 p.157 | 1 | TC-077218 |
| BR-FISERV-EMV-GUIDE-161-10 p.161 | 1 | TC-075955 |
| BR-FISERV-EMV-GUIDE-161-11 p.161 | 1 | TC-075955 |
| BR-FISERV-EMV-GUIDE-161-12 p.161 | 1 | TC-075955 |
| BR-FISERV-EMV-GUIDE-161-13 p.161 | 1 | TC-075955 |
| BR-FISERV-EMV-GUIDE-161-14 p.161 | 1 | TC-075955 |
| BR-FISERV-EMV-GUIDE-161-15 p.161 | 1 | TC-075955 |
| BR-FISERV-EMV-GUIDE-161-3 p.161 | 1 | TC-075955 |
| BR-FISERV-EMV-GUIDE-161-4 p.161 | 1 | TC-075955 |
| BR-FISERV-EMV-GUIDE-161-9 p.161 | 1 | TC-075956 |
| BR-FISERV-EMV-GUIDE-162-2 p.162 | 1 | TC-075956 |
| BR-FISERV-EMV-GUIDE-163-11 p.163 | 1 | TC-075956 |
| BR-FISERV-EMV-GUIDE-163-13 p.163 | 1 | TC-075956 |
| BR-FISERV-EMV-GUIDE-163-14 p.163 | 1 | TC-075956 |
| BR-FISERV-EMV-GUIDE-163-15 p.163 | 1 | TC-075956 |
| BR-FISERV-EMV-GUIDE-163-3 p.163 | 1 | TC-075956 |
| BR-FISERV-EMV-GUIDE-163-4 p.163 | 1 | TC-075956 |
| BR-FISERV-EMV-GUIDE-165-1 p.165 | 9 | TC-075663, TC-075763, TC-075865, TC-076083, TC-076183 |
| BR-FISERV-EMV-GUIDE-173-2 p.173 | 1 | TC-077214 |
| BR-FISERV-EMV-GUIDE-175-3 p.175 | 2 | TC-076940, TC-077058 |
| BR-FISERV-EMV-GUIDE-177-2 p.177 | 12 | TC-076920, TC-076932, TC-076947, TC-076967, TC-076978 |
| BR-FISERV-EMV-GUIDE-178-3 p.178 | 2 | TC-076958, TC-077082 |
| BR-FISERV-EMV-GUIDE-178-4 p.178 | 1 | TC-077201 |
| BR-FISERV-EMV-GUIDE-186-8 p.186 | 2 | TC-077215, TC-077216 |
| BR-FISERV-EMV-GUIDE-195-2 p.195 | 1 | TC-075874 |
| BR-FISERV-EMV-GUIDE-24-2 p.24 | 1 | TC-076009 |
| BR-FISERV-EMV-GUIDE-26-1 p.26 | 1 | TC-076009 |
| BR-FISERV-EMV-GUIDE-26-2 p.26 | 1 | TC-076009 |
| BR-FISERV-EMV-GUIDE-26-3 p.26 | 1 | TC-076009 |
| BR-FISERV-EMV-GUIDE-26-4 p.26 | 1 | TC-076009 |
| BR-FISERV-EMV-GUIDE-26-5 p.26 | 1 | TC-076009 |
| BR-FISERV-EMV-GUIDE-27-1 p.27 | 1 | TC-076009 |
| BR-FISERV-EMV-GUIDE-27-2 p.27 | 1 | TC-076009 |
| BR-FISERV-EMV-GUIDE-33-5 p.33 | 1 | TC-076010 |
| BR-FISERV-EMV-GUIDE-33-7 p.33 | 1 | TC-076010 |
| BR-FISERV-EMV-GUIDE-34-1 p.34 | 1 | TC-076010 |
| BR-FISERV-EMV-GUIDE-34-2 p.34 | 1 | TC-076010 |
| BR-FISERV-EMV-GUIDE-34-3 p.34 | 1 | TC-076010 |
| BR-FISERV-EMV-GUIDE-34-5 p.34 | 1 | TC-076010 |
| BR-FISERV-EMV-GUIDE-34-7 p.34 | 1 | TC-076010 |
| BR-FISERV-EMV-GUIDE-34-8 p.34 | 1 | TC-076010 |
| BR-FISERV-EMV-GUIDE-36-7 p.36 | 1 | TC-076011 |
| BR-FISERV-EMV-GUIDE-37-10 p.37 | 8 | TC-075646, TC-075747, TC-075849, TC-076067, TC-076165 |
| BR-FISERV-EMV-GUIDE-37-2 p.37 | 1 | TC-075982 |
| BR-FISERV-EMV-GUIDE-37-8 p.37 | 1 | TC-075982 |
| BR-FISERV-EMV-GUIDE-37-9 p.37 | 1 | TC-075982 |
| BR-FISERV-EMV-GUIDE-38-2 p.38 | 9 | TC-075655, TC-075756, TC-075858, TC-076076, TC-076174 |
| BR-FISERV-EMV-GUIDE-38-6 p.38 | 8 | TC-075626, TC-075727, TC-075827, TC-076048, TC-076146 |
| BR-FISERV-EMV-GUIDE-39-5 p.39 | 1 | TC-075982 |
| BR-FISERV-EMV-GUIDE-42-1 p.42 | 1 | TC-077186 |
| BR-FISERV-EMV-GUIDE-42-2 p.42 | 2 | TC-075843, TC-076263 |
| BR-FISERV-EMV-GUIDE-42-3 p.42 | 1 | TC-075962 |
| BR-FISERV-EMV-GUIDE-42-4 p.42 | 1 | TC-075962 |
| BR-FISERV-EMV-GUIDE-42-6 p.42 | 1 | TC-075962 |
| BR-FISERV-EMV-GUIDE-43-4 p.43 | 10 | TC-075662, TC-075762, TC-075863, TC-076081, TC-076181 |
| BR-FISERV-EMV-GUIDE-43-5 p.43 | 1 | TC-077189 |
| BR-FISERV-EMV-GUIDE-43-7 p.43 | 1 | TC-075907 |
| BR-FISERV-EMV-GUIDE-45-1 p.45 | 1 | TC-077180 |
| BR-FISERV-EMV-GUIDE-45-6 p.45 | 1 | TC-075962 |
| BR-FISERV-EMV-GUIDE-46-1 p.46 | 1 | TC-075962 |
| BR-FISERV-EMV-GUIDE-46-3 p.46 | 1 | TC-075901 |
| BR-FISERV-EMV-GUIDE-46-4 p.46 | 1 | TC-075962 |
| BR-FISERV-EMV-GUIDE-48-2 p.48 | 13 | TC-075689, TC-075789, TC-075920, TC-076108, TC-076209 |
| BR-FISERV-EMV-GUIDE-48-5 p.48 | 1 | TC-075962 |
| BR-FISERV-EMV-GUIDE-49-2 p.49 | 10 | TC-075659, TC-075760, TC-076599, TC-076616, TC-076781 |
| BR-FISERV-EMV-GUIDE-49-5 p.49 | 1 | TC-077206 |
| BR-FISERV-EMV-GUIDE-49-6 p.49 | 1 | TC-077206 |
| BR-FISERV-EMV-GUIDE-50-1 p.50 | 1 | TC-077207 |
| BR-FISERV-EMV-GUIDE-50-2 p.50 | 10 | TC-075659, TC-075760, TC-076599, TC-076616, TC-076781 |
| BR-FISERV-EMV-GUIDE-50-3 p.50 | 10 | TC-075659, TC-075760, TC-076599, TC-076616, TC-076781 |
| BR-FISERV-EMV-GUIDE-50-4 p.50 | 1 | TC-075962 |
| BR-FISERV-EMV-GUIDE-50-5 p.50 | 7 | TC-075660, TC-075761, TC-075862, TC-076080, TC-076178 |
| BR-FISERV-EMV-GUIDE-51-1 p.51 | 1 | TC-076426 |
| BR-FISERV-EMV-GUIDE-53-2 p.53 | 1 | TC-077190 |
| BR-FISERV-EMV-GUIDE-53-3 p.53 | 10 | TC-075662, TC-075762, TC-075863, TC-076081, TC-076181 |
| BR-FISERV-EMV-GUIDE-53-4 p.53 | 10 | TC-075662, TC-075762, TC-075863, TC-076081, TC-076181 |
| BR-FISERV-EMV-GUIDE-54-2 p.54 | 13 | TC-075689, TC-075789, TC-075920, TC-076108, TC-076209 |
| BR-FISERV-EMV-GUIDE-54-3 p.54 | 13 | TC-075689, TC-075789, TC-075920, TC-076108, TC-076209 |
| BR-FISERV-EMV-GUIDE-54-6 p.54 | 1 | TC-075905 |
| BR-FISERV-EMV-GUIDE-55-1 p.55 | 7 | TC-075683, TC-075783, TC-076921, TC-076933, TC-077036 |
| BR-FISERV-EMV-GUIDE-55-12 p.55 | 2 | TC-077207, TC-077208 |
| BR-FISERV-EMV-GUIDE-55-13 p.55 | 2 | TC-077207, TC-077208 |
| BR-FISERV-EMV-GUIDE-55-14 p.55 | 2 | TC-077207, TC-077208 |
| BR-FISERV-EMV-GUIDE-55-15 p.55 | 2 | TC-077207, TC-077208 |
| BR-FISERV-EMV-GUIDE-56-10 p.56 | 7 | TC-075683, TC-075783, TC-076921, TC-076933, TC-077036 |
| BR-FISERV-EMV-GUIDE-56-14 p.56 | 1 | TC-077177 |
| BR-FISERV-EMV-GUIDE-56-5 p.56 | 13 | TC-075689, TC-075789, TC-075920, TC-076108, TC-076209 |
| BR-FISERV-EMV-GUIDE-58-10 p.58 | 12 | TC-075699, TC-075799, TC-075930, TC-076118, TC-076219 |
| BR-FISERV-EMV-GUIDE-58-11 p.58 | 7 | TC-075683, TC-075783, TC-076921, TC-076933, TC-077036 |
| BR-FISERV-EMV-GUIDE-58-8 p.58 | 7 | TC-075683, TC-075783, TC-076921, TC-076933, TC-077036 |
| BR-FISERV-EMV-GUIDE-59-1 p.59 | 1 | TC-077209 |
| BR-FISERV-EMV-GUIDE-59-3 p.59 | 1 | TC-075871 |
| BR-FISERV-EMV-GUIDE-59-4 p.59 | 1 | TC-075871 |
| BR-FISERV-EMV-GUIDE-63-4 p.63 | 7 | TC-075683, TC-075783, TC-076921, TC-076933, TC-077036 |
| BR-FISERV-EMV-GUIDE-63-6 p.63 | 1 | TC-075964 |
| BR-FISERV-EMV-GUIDE-63-7 p.63 | 7 | TC-075683, TC-075783, TC-076921, TC-076933, TC-077036 |
| BR-FISERV-EMV-GUIDE-63-8 p.63 | 7 | TC-075683, TC-075783, TC-076921, TC-076933, TC-077036 |
| BR-FISERV-EMV-GUIDE-64-2 p.64 | 8 | TC-075607, TC-075708, TC-075808, TC-076029, TC-076127 |
| BR-FISERV-EMV-GUIDE-64-7 p.64 | 2 | TC-075659, TC-075760 |
| BR-FISERV-EMV-GUIDE-64-8 p.64 | 2 | TC-075659, TC-075760 |
| BR-FISERV-EMV-GUIDE-64-9 p.64 | 2 | TC-075659, TC-075760 |
| BR-FISERV-EMV-GUIDE-66-4 p.66 | 1 | TC-077179 |
| BR-FISERV-EMV-GUIDE-67-1 p.67 | 1 | TC-075964 |
| BR-FISERV-EMV-GUIDE-67-5 p.67 | 1 | TC-075964 |
| BR-FISERV-EMV-GUIDE-67-7 p.67 | 1 | TC-075964 |
| BR-FISERV-EMV-GUIDE-69-3 p.69 | 8 | TC-075645, TC-075746, TC-075848, TC-076066, TC-076164 |
| BR-FISERV-EMV-GUIDE-69-4 p.69 | 1 | TC-076326 |
| BR-FISERV-EMV-GUIDE-70-10 p.70 | 16 | TC-075650, TC-075652, TC-075751, TC-075753, TC-075853 |
| BR-FISERV-EMV-GUIDE-70-4 p.70 | 1 | TC-076268 |
| BR-FISERV-EMV-GUIDE-70-5 p.70 | 1 | TC-077207 |
| BR-FISERV-EMV-GUIDE-70-6 p.70 | 1 | TC-077210 |
| BR-FISERV-EMV-GUIDE-71-3 p.71 | 1 | TC-076433 |
| BR-FISERV-EMV-GUIDE-71-4 p.71 | 8 | TC-075640, TC-075741, TC-075841, TC-076061, TC-076159 |
| BR-FISERV-EMV-GUIDE-71-5 p.71 | 8 | TC-075637, TC-075738, TC-075839, TC-076059, TC-076157 |
| BR-FISERV-EMV-GUIDE-71-6 p.71 | 4 | TC-075639, TC-075740, TC-076260, TC-077211 |
| BR-FISERV-EMV-GUIDE-71-7 p.71 | 1 | TC-075837 |
| BR-FISERV-EMV-GUIDE-72-10 p.72 | 8 | TC-075636, TC-075737, TC-075838, TC-076058, TC-076156 |
| BR-FISERV-EMV-GUIDE-72-2 p.72 | 1 | TC-077208 |
| BR-FISERV-EMV-GUIDE-72-4 p.72 | 1 | TC-077211 |
| BR-FISERV-EMV-GUIDE-72-5 p.72 | 1 | TC-077180 |
| BR-FISERV-EMV-GUIDE-72-6 p.72 | 1 | TC-077211 |
| BR-FISERV-EMV-GUIDE-72-7 p.72 | 1 | TC-077180 |
| BR-FISERV-EMV-GUIDE-72-9 p.72 | 1 | TC-077211 |
| BR-FISERV-EMV-GUIDE-73-3 p.73 | 1 | TC-077180 |
| BR-FISERV-EMV-GUIDE-74-4 p.74 | 1 | TC-075906 |
| BR-FISERV-EMV-GUIDE-74-6 p.74 | 1 | TC-075909 |
| BR-FISERV-EMV-GUIDE-74-7 p.74 | 1 | TC-077179 |
| BR-FISERV-EMV-GUIDE-74-9 p.74 | 1 | TC-077212 |
| BR-FISERV-EMV-GUIDE-75-2 p.75 | 1 | TC-075964 |
| BR-FISERV-EMV-GUIDE-75-3 p.75 | 16 | TC-075651, TC-075653, TC-075752, TC-075754, TC-075854 |
| BR-FISERV-EMV-GUIDE-75-4 p.75 | 1 | TC-075964 |
| BR-FISERV-EMV-GUIDE-75-5 p.75 | 1 | TC-075964 |
| BR-FISERV-EMV-GUIDE-76-3 p.76 | 1 | TC-075964 |
| BR-FISERV-EMV-GUIDE-76-5 p.76 | 13 | TC-075613, TC-075714, TC-075814, TC-076035, TC-076133 |
| BR-FISERV-EMV-GUIDE-76-6 p.76 | 1 | TC-076326 |
| BR-FISERV-EMV-GUIDE-77-2 p.77 | 13 | TC-075613, TC-075714, TC-075814, TC-076035, TC-076133 |
| BR-FISERV-EMV-GUIDE-78-2 p.78 | 13 | TC-075613, TC-075714, TC-075814, TC-076035, TC-076133 |
| BR-FISERV-EMV-GUIDE-79-3 p.79 | 13 | TC-075613, TC-075714, TC-075814, TC-076035, TC-076133 |
| BR-FISERV-EMV-GUIDE-79-4 p.79 | 1 | TC-075900 |
| BR-FISERV-EMV-GUIDE-81-2 p.81 | 1 | TC-075975 |
| BR-FISERV-EMV-GUIDE-81-4 p.81 | 1 | TC-077209 |
| BR-FISERV-EMV-GUIDE-84-4 p.84 | 2 | TC-077176, TC-077177 |
| BR-FISERV-EMV-GUIDE-84-5 p.84 | 1 | TC-077176 |
| BR-FISERV-EMV-GUIDE-84-6 p.84 | 1 | TC-075975 |
| BR-FISERV-EMV-GUIDE-87-2 p.87 | 7 | TC-075660, TC-075761, TC-075862, TC-076080, TC-076178 |
| BR-FISERV-EMV-GUIDE-87-6 p.87 | 1 | TC-077179 |
| BR-FISERV-EMV-GUIDE-87-8 p.87 | 1 | TC-075903 |
| BR-FISERV-EMV-GUIDE-88-14 p.88 | 1 | TC-075908 |
| BR-FISERV-EMV-GUIDE-94-4 p.94 | 1 | TC-077213 |
| BR-FISERV-EMV-GUIDE-95-2 p.95 | 1 | TC-075952 |
| BR-FISERV-EMV-GUIDE-96-1 p.96 | 1 | TC-075951 |
| BR-FISERV-EMV-GUIDE-98-1 p.98 | 1 | TC-075967 |
| BR-FISERV-EMV-GUIDE-98-2 p.98 | 1 | TC-075967 |
| BR-FISERV-EMV-GUIDE-98-3 p.98 | 1 | TC-075967 |
| BR-FISERV-EMV-GUIDE-98-4 p.98 | 1 | TC-075967 |
| BR-FISERV-EMV-GUIDE-98-5 p.98 | 1 | TC-075967 |
| BR-FISERV-EMV-GUIDE-99-1 p.99 | 1 | TC-075967 |
| BR-FISERV-EMV-GUIDE-99-2 p.99 | 1 | TC-075967 |
| BR-FISERV-EMV-GUIDE-99-3 p.99 | 1 | TC-075967 |

## Reverse index - test data file -> test case

3066 files; the full list is in `traceability/test_data_index.csv`.

